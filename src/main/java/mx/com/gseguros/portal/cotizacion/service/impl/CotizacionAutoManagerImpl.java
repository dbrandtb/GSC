package mx.com.gseguros.portal.cotizacion.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.catalogos.dao.PersonasDAO;
import mx.com.gseguros.portal.consultas.dao.ConsultasDAO;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaBaseVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlist2SmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.cotizacion.model.PInsertaTbasvalsitDTO;
import mx.com.gseguros.portal.cotizacion.model.PInsertaTconvalsitDTO;
import mx.com.gseguros.portal.cotizacion.model.PMovMpolisitDTO;
import mx.com.gseguros.portal.cotizacion.model.PMovTvalositDTO;
import mx.com.gseguros.portal.cotizacion.model.ParametroCotizacion;
import mx.com.gseguros.portal.cotizacion.service.CotizacionAutoManager;
import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.Utils;
import mx.com.gseguros.ws.autosgs.infovehiculo.client.axis2.VehiculoWSServiceStub.ResponseValor;
import mx.com.gseguros.ws.autosgs.infovehiculo.service.InfoVehiculoService;
import mx.com.gseguros.ws.autosgs.tractocamiones.service.TractoCamionService;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteGeneral;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteGeneralRespuesta;
import mx.com.gseguros.ws.ice2sigs.service.Ice2sigsService;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

public class CotizacionAutoManagerImpl implements CotizacionAutoManager
{
	private static final Logger logger           = Logger.getLogger(CotizacionAutoManagerImpl.class);
	private static final DateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
	
	private CotizacionDAO  cotizacionDAO;
	private PantallasDAO   pantallasDAO;
	private ConsultasDAO   consultasDAO;
	private PersonasDAO    personasDAO;
	private MesaControlDAO mesaControlDAO;
	private EndososDAO     endososDAO;
	
	@Autowired
	private transient TractoCamionService tractoCamionService;

	@Autowired
	private transient InfoVehiculoService valorComercialService;
	
	@Autowired
	private transient Ice2sigsService ice2sigsService;
	
	private Map<String,Object> session;
	
	/**
	 * Guarda el estado actual en sesion
	 */
	private void setCheckpoint(String checkpoint)
	{
		logger.debug(new StringBuilder("checkpoint-->").append(checkpoint).toString());
		session.put("checkpoint",checkpoint);
	}
	
	/**
	 * Obtiene el estado actual de sesion
	 */
	private String getCheckpoint()
	{
		return (String)session.get("checkpoint");
	}
	
	/**
	 * Da valor a los atributos exito, respuesta y respuestaOculta de resp.
	 * Tambien guarda el checkpoint en 0
	 */
	private void manejaException(Exception ex,ManagerRespuestaBaseVO resp)
	{
		long timestamp = System.currentTimeMillis();
		resp.setExito(false);
		resp.setRespuestaOculta(ex.getMessage());
		
		if(ex instanceof ApplicationException)
		{
			resp.setRespuesta(
					new StringBuilder()
					.append(ex.getMessage())
					.append(" #")
					.append(timestamp)
					.toString()
					);
		}
		else
		{
			resp.setRespuesta(
					new StringBuilder()
					.append("Error ")
					.append(getCheckpoint().toLowerCase())
					.append(" #")
					.append(timestamp)
					.toString()
					);
		}
		
		logger.error(resp.getRespuesta(),ex);
		setCheckpoint("0");
	}
	
	/**
	 * Atajo a StringUtils.isBlank
	 */
	private boolean isBlank(String mensaje)
	{
		return StringUtils.isBlank(mensaje);
	}
	
	/**
	 * Arroja una ApplicationException
	 */
	private void throwExc(String mensaje) throws ApplicationException
	{
		throw new ApplicationException(mensaje);
	}
	
	private void checkBlank(String cadena,String mensaje)throws ApplicationException
	{
		if(isBlank(cadena))
		{
			throwExc(mensaje);
		}
	}
	
	@Override
	public ManagerRespuestaImapSmapVO cotizacionAutoIndividual(
			String ntramite
			,String cdunieco
			,String cdramo
			,String cdtipsit
			,String cdusuari
			,String cdsisrol
			)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cotizacionAutoIndividual @@@@@@")
				.append("\n@@@@@@ ntramite=").append(ntramite)
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.append("\n@@@@@@ cdusuari=").append(cdusuari)
				.append("\n@@@@@@ cdsisrol=").append(cdsisrol)
				.toString()
				);
		
		ManagerRespuestaImapSmapVO resp = new ManagerRespuestaImapSmapVO(true);
		resp.setSmap(new LinkedHashMap<String,String>());
		resp.setImap(new LinkedHashMap<String,Item>());
		
		try
		{
			String cdagente = null;
			
			setCheckpoint("Obteniendo trámite y sucursal");//////
			//cuando no hay tramite es porque cotiza un agente desde afuera
			if(isBlank(ntramite))
			{
				try
				{
					DatosUsuario datUsu = cotizacionDAO.cargarInformacionUsuario(cdusuari,cdtipsit);
					cdunieco            = datUsu.getCdunieco();
					resp.getSmap().put("cdunieco" , cdunieco);
					resp.getSmap().put("ntramite" , "");
					
					if(cdsisrol.equals(RolSistema.AGENTE.getCdsisrol()))
					{
						cdagente = datUsu.getCdagente();
						resp.getSmap().put("cdagente" , cdagente);
					}
				}
				catch(Exception ex)
				{
					throwExc("Usted no puede cotizar este producto");
				}
			}
			
			setCheckpoint("Recuperando tipo de situación");//////
			Map<String,String>tipoSituacion=cotizacionDAO.cargarTipoSituacion(cdramo,cdtipsit);
			if(tipoSituacion!=null)
			{
				resp.getSmap().putAll(tipoSituacion);
			}
			else
			{
				throwExc("No se ha parametrizado la situación en TTIPRAM");
			}
			
			setCheckpoint("Recuperando atributos variables");//////
			List<ComponenteVO>panel1=new ArrayList<ComponenteVO>();
			List<ComponenteVO>panel2=new ArrayList<ComponenteVO>();
			List<ComponenteVO>panel3=new ArrayList<ComponenteVO>();
			List<ComponenteVO>panel4=new ArrayList<ComponenteVO>();
			List<ComponenteVO>panel5=new ArrayList<ComponenteVO>();
			List<ComponenteVO>panel6=new ArrayList<ComponenteVO>();
			
			List<ComponenteVO>tatrisit = cotizacionDAO.cargarTatrisit(cdtipsit, cdusuari);
			List<ComponenteVO>aux      = new ArrayList<ComponenteVO>();
			
			//obtener los que se muestran
			for(ComponenteVO tatri:tatrisit)
			{
				if(tatri.getSwpresen().equals("S"))
				{
					tatri.setComboVacio(true);
					aux.add(tatri);
				}
			}
			
			tatrisit = aux;
			
			setCheckpoint("Obteniendo componentes sustitutos");//////
			List<ComponenteVO>sustitutos = pantallasDAO.obtenerComponentes(
					TipoTramite.POLIZA_NUEVA.getCdtiptra()
					,cdunieco
					,cdramo
					,cdtipsit
					,"I"
					,cdsisrol
					,"COTIZACION_CUSTOM"
					,"SUSTITUTOS"
					,null
					);
			if(sustitutos.size()>0)
			{
				aux=new ArrayList<ComponenteVO>();
				for(ComponenteVO tatri : tatrisit)
				{
					String cdatribuTatri = tatri.getNameCdatribu();
					boolean sustituido   = false;
					for(ComponenteVO sustituto : sustitutos)
					{
						String cdatribuSustituto = sustituto.getNameCdatribu();
						logger.debug(new StringBuilder("tatri=").append(cdatribuTatri).append(" vs susti=").append(cdatribuSustituto).toString());
						if(cdatribuSustituto.equals(cdatribuTatri))
						{
							sustituto.setNmpanelcoti(tatri.getNmpanelcoti());
							sustituido = true;
							aux.add(sustituto);
						}
					}
					if(!sustituido)
					{
						aux.add(tatri);
					}
				}
				tatrisit = aux;
			}
			
			//separar por panel
			for(ComponenteVO tatri : tatrisit)
			{
				String nmpanelcoti = tatri.getNmpanelcoti();
				if(nmpanelcoti.equals("1"))
				{
					panel1.add(tatri);
				}
				else if(nmpanelcoti.equals("2"))
				{
					panel2.add(tatri);
				}
				else if(nmpanelcoti.equals("3"))
				{
					panel3.add(tatri);
				}
				else if(nmpanelcoti.equals("4"))
				{
					panel4.add(tatri);
				}
				else if(nmpanelcoti.equals("5"))
				{
					panel5.add(tatri);
				}
				else if(nmpanelcoti.equals("6"))
				{
					panel6.add(tatri);
				}
			}
			
			setCheckpoint("Construyendo componentes");//////
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.setCdramo(cdramo);
			gc.setCdtipsit(cdtipsit);
			
			gc.generaComponentes(panel1, true, false, true, false, false, false);
			resp.getImap().put("panel1Items"  , gc.getItems());
			
			gc.generaComponentes(panel2, true, false, true, false, false, false);
			resp.getImap().put("panel2Items"  , gc.getItems());
			
			gc.generaComponentes(panel3, true, false, true, false, false, false);
			resp.getImap().put("panel3Items"  , gc.getItems());
			
			gc.generaComponentes(panel4, true, false, true, false, false, false);
			resp.getImap().put("panel4Items"  , gc.getItems());
			
			gc.generaComponentes(panel5, true, false, true, false, false, false);
			resp.getImap().put("panel5Items"  , gc.getItems());
			
			gc.generaComponentes(panel6, true, false, true, false, false, false);
			resp.getImap().put("panel6Items"  , gc.getItems());
			
			gc.generaComponentes(tatrisit, true, true, false, false, false, false);
			resp.getImap().put("formFields" , gc.getFields());
			
			setCheckpoint("Recuperando atributos de poliza");
			List<ComponenteVO>tatripol    = cotizacionDAO.cargarTatripol(cdramo, null, null);
			List<ComponenteVO>tatripolAux = new ArrayList<ComponenteVO>();
			for(ComponenteVO tatri:tatripol)
			{
				if("S".equals(tatri.getSwpresen()))
				{
					tatripolAux.add(tatri);
				}
			}
			tatripol=tatripolAux;
			resp.getSmap().put("tatripolItemsLength",String.valueOf(tatripol.size()));
			GeneradorCampos gcTatripol = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gcTatripol.setAuxiliar(true);
			gcTatripol.setCdramo(cdramo);
			gcTatripol.generaComponentes(tatripol, true, false, true, false, false, false);
			resp.getImap().put("tatripolItems" , gcTatripol.getItems());
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex,resp);
		}

		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cotizacionAutoIndividual @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO cargarRetroactividadSuplemento(
			String cdunieco
			,String cdramo
			,String cdtipsup
			,String cdusuari
			,String cdtipsit)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarRetroactividadSuplemento @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=").append(cdramo)
				.append("\n@@@@@@ cdtipsup=").append(cdtipsup)
				.append("\n@@@@@@ cdusuari=").append(cdusuari)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.toString()
				);
		
		ManagerRespuestaSmapVO resp = new ManagerRespuestaSmapVO(true);
		
		try
		{
			setCheckpoint("Recuperando retroactividad");//////
			resp.setSmap(cotizacionDAO.cargarRetroactividadSuplemento(cdunieco,cdramo,cdtipsup,cdusuari,cdtipsit));
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarRetroactividadSuplemento @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO cargarSumaAseguradaRamo5(
			String cdtipsit
			,String clave
			,String modelo
			,String cdsisrol)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarSumaAseguradaRamo5 @@@@@@")
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.append("\n@@@@@@ clave=")   .append(clave)
				.append("\n@@@@@@ modelo=")  .append(modelo)
				.append("\n@@@@@@ cdsisrol=").append(cdsisrol)
				.toString()
				);
		
		ManagerRespuestaSmapVO resp=new ManagerRespuestaSmapVO(true);
		
		try
		{
			setCheckpoint("Invocando servicio web");
			ResponseValor wsResp = valorComercialService.obtieneDatosVehiculoGS(Integer.valueOf(clave), Integer.valueOf(modelo));
			boolean wsExito      = true;
			if(wsResp==null)
			{
				resp.setRespuesta("Hubo un error al invocar el servicio web para recuperar el valor");
				wsExito = false;
			}
			if(wsExito&&wsResp.getExito()==false)
			{
				resp.setRespuesta("El servicio web para recuperar el valor tuvo un error interno");
				wsExito = false;
			}
			
			setCheckpoint("Recuperando suma asegurada");
			resp.setSmap(cotizacionDAO.cargarSumaAseguradaRamo5(cdtipsit,clave,modelo,cdsisrol));
			
			if(wsExito&&wsResp.getValor_comercial()>0d)
			{
				resp.getSmap().put("sumaseg" , String.format("%.2f", wsResp.getValor_comercial()));
			}
			if(wsExito)
			{
				resp.getSmap().put("sumasegWS" , Double.toString(wsResp.getValor_comercial()));
			}
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarSumaAseguradaRamo5 @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaImapSmapVO emisionAutoIndividual(
			String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String ntramite
			,String cdusuari
			)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ emisionAutoIndividual @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ ntramite=").append(ntramite)
				.append("\n@@@@@@ cdusuari=").append(cdusuari)
				.toString()
				);
		
		ManagerRespuestaImapSmapVO resp=new ManagerRespuestaImapSmapVO(true);
		resp.setImap(new HashMap<String,Item>());
		resp.setSmap(new HashMap<String,String>());
		
		try
		{
			setCheckpoint("Procesando datos de entrada");
			resp.getSmap().put("cdusuari" , cdusuari);
			
			setCheckpoint("Recuperando tipo de situacion");
			resp.getSmap().putAll(cotizacionDAO.cargarTipoSituacion(cdramo, cdtipsit));
			
			setCheckpoint("Recuperando atributos variables de poliza");
			List<ComponenteVO>tatripol = cotizacionDAO.cargarTatripol(cdramo,null,null);
			for(ComponenteVO tatri:tatripol)
			{
				if(!"S".equals(tatri.getSwpresemi()))
				{
					tatri.setOculto(true);
				}
			}
			
			setCheckpoint("Recuperando atributos variables de situacion");
			List<ComponenteVO>tatrisit=cotizacionDAO.cargarTatrisit(cdtipsit, cdusuari);
			
			setCheckpoint("Filtrando atributos de datos complementarios");
			List<ComponenteVO>aux=new ArrayList<ComponenteVO>();
			for(ComponenteVO tatri:tatrisit)
			{
				if(isBlank(tatri.getSwtarifi())||tatri.getSwtarifi().equalsIgnoreCase("N"))
				{
					aux.add(tatri);
				}
			}
			tatrisit=aux;
			
			setCheckpoint("Recuperando componentes de pantalla");
			List<ComponenteVO>polizaComp=pantallasDAO.obtenerComponentes(null, null, null, null, null, null, "EMISION_AUTO_IND", "POLIZA", null);
			List<ComponenteVO>agenteComp=pantallasDAO.obtenerComponentes(null, null, null, null, null, null, "EMISION_AUTO_IND", "AGENTE", null);
			
			setCheckpoint("Generando componentes");
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.setCdramo(cdramo);
			gc.setCdtipsit(cdtipsit);
			
			gc.generaComponentes(tatripol, true, true, true, false, false, false);
			resp.getImap().put("polizaAdicionalesFields" , gc.getFields());
			resp.getImap().put("polizaAdicionalesItems"  , gc.getItems());
			
			gc.generaComponentes(tatrisit, true, true, true, false, false, false);
			resp.getImap().put("adicionalesFields" , gc.getFields());
			resp.getImap().put("adicionalesItems"  , gc.getItems());
			
			gc.generaComponentes(polizaComp, true, true, true, false, false, false);
			resp.getImap().put("polizaFields" , gc.getFields());
			resp.getImap().put("polizaItems"  , gc.getItems());
			
			gc.generaComponentes(agenteComp, true, true, true, false, false, false);
			resp.getImap().put("agenteFields" , gc.getFields());
			resp.getImap().put("agenteItems"  , gc.getItems());
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ emisionAutoIndividual @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO cargarDatosComplementariosAutoInd(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarDatosComplementariosAutoInd @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.toString()
				);
		
		ManagerRespuestaSmapVO resp = new ManagerRespuestaSmapVO(true);
		try
		{
			resp.setSmap(cotizacionDAO.cargarDatosComplementariosAutoInd(cdunieco,cdramo,estado,nmpoliza));
			
			resp.getSmap().putAll(cotizacionDAO.cargarTvalopol(cdunieco,cdramo,estado,nmpoliza));
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarDatosComplementariosAutoInd @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO cargarValoresSituacion(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarValoresSituacion @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ nmsituac=").append(nmsituac)
				.toString()
				);
		
		ManagerRespuestaSmapVO resp = new ManagerRespuestaSmapVO(true);
		
		try
		{
			resp.setSmap(cotizacionDAO.cargarTvalosit(cdunieco, cdramo, estado, nmpoliza, nmsituac));
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarValoresSituacion @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaVoidVO movimientoMpoliper(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdrol
			,String cdperson
			,String nmsuplem
			,String status
			,String nmorddom
			,String swreclam
			,String accion
			,String swexiper)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ movimientoMpoliper @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ nmsituac=").append(nmsituac)
				.append("\n@@@@@@ cdrol=")   .append(cdrol)
				.append("\n@@@@@@ cdperson=").append(cdperson)
				.append("\n@@@@@@ nmsuplem=").append(nmsuplem)
				.append("\n@@@@@@ status=")  .append(status)
				.append("\n@@@@@@ nmorddom=").append(nmorddom)
				.append("\n@@@@@@ swreclam=").append(swreclam)
				.append("\n@@@@@@ accion=")  .append(accion)
				.append("\n@@@@@@ swexiper=").append(swexiper)
				.toString()
				);
		
		ManagerRespuestaVoidVO resp = new ManagerRespuestaVoidVO(true);
		try
		{
			cotizacionDAO.borrarMpoliperTodos(cdunieco, cdramo, estado, nmpoliza);
			cotizacionDAO.movimientoMpoliper(cdunieco, cdramo, estado, nmpoliza, nmsituac, cdrol, cdperson, nmsuplem, status, nmorddom, swreclam, accion, swexiper);
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ movimientoMpoliper @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaVoidVO guardarComplementariosAutoIndividual(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String agenteSec
			,String porpartiSec
			,String feini
			,String fefin
			,Map<String,String>tvalopol
			,Map<String,String>tvalosit
			,String ntramite
			,String cdagente
			)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ guardarComplementariosAutoIndividual @@@@@@")
				.append("\n@@@@@@ cdunieco=")   .append(cdunieco)
				.append("\n@@@@@@ cdramo=")     .append(cdramo)
				.append("\n@@@@@@ estado=")     .append(estado)
				.append("\n@@@@@@ nmpoliza=")   .append(nmpoliza)
				.append("\n@@@@@@ agenteSec=")  .append(agenteSec)
				.append("\n@@@@@@ porpartiSec=").append(porpartiSec)
				.append("\n@@@@@@ feini=")      .append(feini)
				.append("\n@@@@@@ fefin=")      .append(fefin)
				.append("\n@@@@@@ tvalopol=")   .append(tvalopol)
				.append("\n@@@@@@ tvalosit=")   .append(tvalosit)
				.append("\n@@@@@@ ntramite=")   .append(ntramite)
				.append("\n@@@@@@ cdagente=")   .append(cdagente)
				.toString()
				);
		
		ManagerRespuestaVoidVO resp = new ManagerRespuestaVoidVO(true);
		
		try
		{
			setCheckpoint("Actualizando poliza");
			cotizacionDAO.actualizaMpolizas(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,"0"  //nmsuplem
					,null //swestado
					,null //nmsolici
					,null //feautori
					,null //cdmotanu
					,null //feanulac
					,null //swautori
					,null //cdmoneda
					,null //feinisus
					,null //fefinsus
					,null //ottempot
					,renderFechas.parse(feini)
					,null //hhefecto
					,renderFechas.parse(fefin)
					,null //fevencim
					,null //nmrenova
					,null //ferecibo
					,null //feultsin
					,null //nmnumsin
					,null //cdtipcoa
					,null //swtarifi
					,null //swabrido
					,null //feemisio
					,null //cdperpag
					,null //nmpoliex
					,null //nmcuadro
					,null //porredau
					,null //swconsol
					,null //nmpolant
					,null //nmpolnva
					,null //fesolici
					,null //cdramant
					,null //cdmejred
					,null //nmpoldoc
					,null //nmpoliza2
					,null //nmrenove
					,null //nmsuplee
					,null //ttipcamc
					,null //ttipcamv
					,null //swpatent
					,null //nmpolmst
					,null //pcpgocte
					);
			
			if(!isBlank(agenteSec))
			{
				setCheckpoint("Desligando agentes anteriores");
				cotizacionDAO.borrarAgentesSecundarios(cdunieco, cdramo, estado, nmpoliza, "0");
				
				setCheckpoint("Recuperando cuadros de agentes");
				String nmcuadro    = cotizacionDAO.obtenerDatosAgente(cdagente  , cdramo).get("NMCUADRO");
    			String nmcuadroSec = cotizacionDAO.obtenerDatosAgente(agenteSec , cdramo).get("NMCUADRO");
				
				setCheckpoint("Guardando agente nuevo");
				cotizacionDAO.movimientoMpoliage(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,cdagente
						,"0"
						,"V"
						,"1"
						,"0"
						,nmcuadro
						,null
						,Constantes.INSERT_MODE
						,ntramite
						,String.format("%.2f",100d-Double.valueOf(porpartiSec))
						);
				
				if(Double.valueOf(porpartiSec)>0d)
				{
					cotizacionDAO.movimientoMpoliage(
							cdunieco
							,cdramo
							,estado
							,nmpoliza
							,agenteSec
							,"0"
							,"V"
							,"2"
							,"0"
							,nmcuadroSec
							,null
							,Constantes.INSERT_MODE
							,null//ntramite
							,porpartiSec
							);
				}
			}
				
			setCheckpoint("Guardando datos adicionales de poliza");
			Map<String,String>tvalopolAux=new HashMap<String,String>();
			for(Entry<String,String>en:tvalopol.entrySet())
			{
				String key = en.getKey();
				if(!isBlank(key)
						&&key.length()>"parametros.pv_otvalor".length()
						&&key.substring(0,"parametros.pv_otvalor".length()).equals("parametros.pv_otvalor"))
				{
					tvalopolAux.put(key.substring("parametros.pv_".length()),en.getValue());
				}
			}
			cotizacionDAO.movimientoTvalopol(cdunieco,cdramo,estado,nmpoliza,"0","V",tvalopolAux);
			
			setCheckpoint("Guardando datos adicionales de situacion");
			Map<String,String>tvalositAux=new HashMap<String,String>();
			for(Entry<String,String>en:tvalosit.entrySet())
			{
				String key = en.getKey();
				if(!isBlank(key)
						&&key.length()>"parametros.pv_otvalor".length()
						&&key.substring(0,"parametros.pv_otvalor".length()).equals("parametros.pv_otvalor"))
				{
					tvalositAux.put(key.substring("parametros.pv_".length()),en.getValue());
				}
			}
			cotizacionDAO.actualizaValoresSituacion(cdunieco,cdramo,estado,nmpoliza,"0","1",tvalositAux);
			
			resp.setRespuesta("Datos guardados");
			resp.setRespuestaOculta("Datos guardados");
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ guardarComplementariosAutoIndividual @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO cargarConfiguracionCotizacion(
			String cdramo
			,String cdtipsit
			,String cdusuari)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarConfiguracionCotizacion @@@@@@")
				.append("\n@@@@@@ cdramo=")     .append(cdramo)
				.append("\n@@@@@@ cdtipsit=")   .append(cdtipsit)
				.append("\n@@@@@@ cdusuari=")   .append(cdusuari)
				.toString()
				);
		
		ManagerRespuestaSmapVO resp = new ManagerRespuestaSmapVO(true);
		
		try
		{
			setCheckpoint("Recuperando configuracio de cotizacion");
			resp.setSmap(cotizacionDAO.cargarConfiguracionCotizacion(cdramo, cdtipsit, cdusuari));
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarConfiguracionCotizacion @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaVoidVO guardarConfiguracionCotizacion(
			String cdramo
			,String cdtipsit
			,String cdusuari
			,Map<String,String>valores)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ guardarConfiguracionCotizacion @@@@@@")
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.append("\n@@@@@@ cdusuari=").append(cdusuari)
				.append("\n@@@@@@ valores=") .append(valores)
				.toString()
				);
		
		ManagerRespuestaVoidVO resp=new ManagerRespuestaVoidVO(true);
		
		try
		{
			setCheckpoint("Guardando configuracion");
			cotizacionDAO.guardarConfiguracionCotizacion(cdramo, cdtipsit, cdusuari, valores);
			
			resp.setRespuesta("Se han guardado las configuraciones");
			resp.setRespuestaOculta("Todo OK");
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ guardarConfiguracionCotizacion @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSlistVO cargarParamerizacionConfiguracionCoberturas(
			String cdtipsit
			,String cdsisrol
			,String negocio
			,String tipoServicio
			,String modelo
			,String tipoPersona
			,String submarca
			,String clavegs
			)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarParamerizacionConfiguracionCoberturas @@@@@@")
				.append("\n@@@@@@ cdtipsit=")    .append(cdtipsit)
				.append("\n@@@@@@ cdsisrol=")    .append(cdsisrol)
				.append("\n@@@@@@ negocio=")     .append(negocio)
				.append("\n@@@@@@ tipoServicio=").append(tipoServicio)
				.append("\n@@@@@@ modelo=")      .append(modelo)
				.append("\n@@@@@@ tipoPersona=") .append(tipoPersona)
				.append("\n@@@@@@ submarca=")    .append(submarca)
				.append("\n@@@@@@ clavegs=")     .append(clavegs)
				.toString()
				);
		
		ManagerRespuestaSlistVO resp = new ManagerRespuestaSlistVO(true);
		
		try
		{
			setCheckpoint("Recuperando parametrizacion");
			List<List<Map<String,String>>>listas = cotizacionDAO.cargarParamerizacionConfiguracionCoberturas(
					cdtipsit , cdsisrol    , negocio  , tipoServicio
					,modelo  , tipoPersona , submarca , clavegs);
			
			List<Map<String,String>>ltatrisit = listas.get(0);
			List<Map<String,String>>latrixrol = listas.get(1);
			List<Map<String,String>>latrixant = listas.get(2);
			List<Map<String,String>>latrixper = listas.get(3);
			List<Map<String,String>>latrixcam = listas.get(4);
			List<Map<String,String>>latrirang = listas.get(5);
			
			setCheckpoint("Inicializando atributos a procesar");
			Map<String,Map<String,String>>atributos = new LinkedHashMap<String,Map<String,String>>();
			for(Map<String,String>tatrisit:ltatrisit)
			{
				Map<String,String>mapa=new LinkedHashMap<String,String>();
				mapa.put("aplica" , "1");
				atributos.put(tatrisit.get("cdatribu"),mapa);
			}
			logger.debug(new StringBuilder("atributos=").append(atributos).toString());
			
			setCheckpoint("Asignando rangos");
			for(Map<String,String>rango:latrirang)
			{
				String cdatribu = rango.get("cdatribu");
				if(atributos.containsKey(cdatribu))
				{
					atributos.get(cdatribu).put("minimo",rango.get("minimo"));
					atributos.get(cdatribu).put("maximo",rango.get("maximo"));
				}
			}
			logger.debug(new StringBuilder("atributos=").append(atributos).toString());
			
			setCheckpoint("Procesando parametrizacion por rol");
			for(Map<String,String>atrixrol:latrixrol)
			{
				String cdatribu = atrixrol.get("cdatribu");
				String aplica   = atrixrol.get("aplica");
				String valor    = atrixrol.get("valor");
				if(atributos.containsKey(cdatribu)&&aplica.equals("0"))
				{
					atributos.get(cdatribu).put("aplica" , aplica);
					atributos.get(cdatribu).put("valor"  , valor);
				}
			}
			logger.debug(new StringBuilder("atributos=").append(atributos).toString());
			
			boolean esCamion = latrixcam.size()>0;
			
			if(esCamion)
			{
				setCheckpoint("Procesando parametrizacion para tractocamion");
				for(Map<String,String>atrixcam:latrixcam)
				{
					String cdatribu = atrixcam.get("cdatribu");
					String aplica   = atrixcam.get("aplica");
					String valor    = atrixcam.get("valor");
					if(atributos.containsKey(cdatribu)&&aplica.equals("0"))
					{
						atributos.get(cdatribu).put("aplica" , aplica);
						atributos.get(cdatribu).put("valor"  , valor);
					}
				}
				logger.debug(new StringBuilder("atributos=").append(atributos).toString());
			}
			else
			{
				/*comodin 1/2
				setCheckpoint("Buscando comodin por modelo");
				boolean comodin = false;
				for(Map<String,String>atrixant:latrixant)
				{
					if(atrixant.get("cdatribu").equals("-1"))
					{
						comodin = true;
					}
				}
				logger.debug(new StringBuilder("comodin=").append(comodin).toString());
				
				if(!comodin||true)
				{
				*/
				setCheckpoint("Procesando parametrizacion por modelo");
				for(Map<String,String>atrixant:latrixant)
				{
					String cdatribu = atrixant.get("cdatribu");
					String aplica   = atrixant.get("aplica");
					String valor    = atrixant.get("valor");
					if(atributos.containsKey(cdatribu)&&aplica.equals("0"))
					{
						atributos.get(cdatribu).put("aplica" , aplica);
						atributos.get(cdatribu).put("valor"  , valor);
					}
				}
				logger.debug(new StringBuilder("atributos=").append(atributos).toString());
				/*comodin 2/2
				}
				*/
				setCheckpoint("Procesando parametrizacion por tipo de persona");
				for(Map<String,String>atrixper:latrixper)
				{
					String cdatribu = atrixper.get("cdatribu");
					String aplica   = atrixper.get("aplica");
					String valor    = atrixper.get("valor");
					if(atributos.containsKey(cdatribu)&&aplica.equals("0"))
					{
						atributos.get(cdatribu).put("aplica" , aplica);
						atributos.get(cdatribu).put("valor"  , valor);
					}
				}
				logger.debug(new StringBuilder("atributos=").append(atributos).toString());
			}
			
			setCheckpoint("Transformando parametrizacion");
			List<Map<String,String>>lista = new ArrayList<Map<String,String>>();
			resp.setSlist(lista);
			
			for(Entry<String,Map<String,String>>atributo:atributos.entrySet())
			{
				Map<String,String>nuevo = new LinkedHashMap<String,String>();
				lista.add(nuevo);
				
				String cdatribu = atributo.getKey();
				nuevo.put("cdatribu" , cdatribu);
				
				Map<String,String>props = atributo.getValue();
				nuevo.put("aplica" , props.get("aplica"));
				nuevo.put("valor"  , props.get("valor"));
				nuevo.put("minimo" , props.get("minimo"));
				nuevo.put("maximo" , props.get("maximo"));
			}
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarParamerizacionConfiguracionCoberturas @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaImapSmapVO cotizacionAutoFlotilla(
			String cdusuari
			,String cdsisrol
			,String cdunieco
			,String cdramo
			,String cdtipsit
			,String ntramite
			,String tipoflot
			,boolean endoso
			)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cotizacionAutoFlotilla @@@@@@")
				.append("\n@@@@@@ cdusuari=").append(cdusuari)
				.append("\n@@@@@@ cdsisrol=").append(cdsisrol)
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.append("\n@@@@@@ ntramite=").append(ntramite)
				.append("\n@@@@@@ endoso=")  .append(endoso)
				.toString()
				);
		
		ManagerRespuestaImapSmapVO resp = new ManagerRespuestaImapSmapVO(true);
		resp.setSmap(new LinkedHashMap<String,String>());
		resp.setImap(new LinkedHashMap<String,Item>());
		
		try
		{
			String cdagente = null;
			
			setCheckpoint("Obteniendo trámite y sucursal");//////
			//cuando no hay tramite es porque cotiza un agente desde afuera
			if(isBlank(ntramite))
			{
				try
				{
					DatosUsuario datUsu = cotizacionDAO.cargarInformacionUsuario(cdusuari,cdtipsit);
					cdunieco            = datUsu.getCdunieco();
					resp.getSmap().put("cdunieco" , cdunieco);
					resp.getSmap().put("ntramite" , "");
					
					if(cdsisrol.equals(RolSistema.AGENTE.getCdsisrol()))
					{
						cdagente = datUsu.getCdagente();
						resp.getSmap().put("cdagente" , cdagente);
					}
				}
				catch(Exception ex)
				{
					throwExc("Usted no puede cotizar este producto");
				}
			}
			
			setCheckpoint("Recuperando tipo de situación");//////
			Map<String,String>tipoSituacion=cotizacionDAO.cargarTipoSituacion(cdramo,cdtipsit);
			if(tipoSituacion!=null)
			{
				resp.getSmap().putAll(tipoSituacion);
				resp.getSmap().put("AGRUPACION" , "GRUPO");
			}
			else
			{
				throwExc("No se ha parametrizado la situación en TTIPRAM");
			}
			
			setCheckpoint("Recuperando atributos variables");//////
			List<ComponenteVO>panel1   = new ArrayList<ComponenteVO>();
			//List<ComponenteVO>panel2   = new ArrayList<ComponenteVO>();
			List<ComponenteVO>panel3   = new ArrayList<ComponenteVO>();
			List<ComponenteVO>panel5   = new ArrayList<ComponenteVO>();
			List<ComponenteVO>panel6   = new ArrayList<ComponenteVO>();
			
			List<ComponenteVO>tatrisit = cotizacionDAO.cargarTatrisit(cdtipsit, cdusuari);
			
			setCheckpoint("Recuperando editor de situacion");
			List<ComponenteVO>auxEditorSit = pantallasDAO.obtenerComponentes(
					TipoTramite.POLIZA_NUEVA.getCdtiptra(), null, cdramo
					, cdtipsit, null, cdsisrol
					, "COTIZACION_FLOTILLA", "EDITOR_SITUACION", null);
			
			setCheckpoint("Recuperando columnas");
			List<ComponenteVO>gridCols = pantallasDAO.obtenerComponentes(
					TipoTramite.POLIZA_NUEVA.getCdtiptra(), null, cdramo
					, cdtipsit, null, cdsisrol
					, "COTIZACION_FLOTILLA", "COLUMNAS_RENDER", null);
			
			setCheckpoint("Filtrando atributos");
			List<ComponenteVO>aux      = new ArrayList<ComponenteVO>();
			for(ComponenteVO tatri:tatrisit)
			{
				if(tatri.getSwpresenflot().equals("S"))
				{
					tatri.setComboVacio(true);
					aux.add(tatri);
				}
			}
			tatrisit = aux;
			
			setCheckpoint("Obteniendo componentes sustitutos");
			List<ComponenteVO>sustitutos = pantallasDAO.obtenerComponentes(
					TipoTramite.POLIZA_NUEVA.getCdtiptra()
					,cdunieco
					,cdramo
					,cdtipsit
					,"C"
					,cdsisrol
					,"COTIZACION_CUSTOM"
					,"SUSTITUTOS"
					,null
					);
			if(sustitutos.size()>0)
			{
				aux=new ArrayList<ComponenteVO>();
				for(ComponenteVO tatri : tatrisit)
				{
					String cdatribuTatri = tatri.getNameCdatribu();
					boolean sustituido   = false;
					for(ComponenteVO sustituto : sustitutos)
					{
						String cdatribuSustituto = sustituto.getNameCdatribu();
						logger.debug(new StringBuilder("tatri=").append(cdatribuTatri).append(" vs susti=").append(cdatribuSustituto).toString());
						if(cdatribuSustituto.equals(cdatribuTatri))
						{
							sustituto.setNmpanelflot(tatri.getNmpanelflot());
							sustituto.setCotflotrol(tatri.getCotflotrol());
							sustituto.setColumna(tatri.getColumna());
							sustituto.setSwpresenflot(tatri.getSwpresenflot());
							sustituido = true;
							aux.add(sustituto);
						}
					}
					if(!sustituido)
					{
						aux.add(tatri);
					}
				}
				tatrisit = aux;
			}
			
			setCheckpoint("Organizando atributos");
			/*gridCols.add(editorSit);*/
			for(ComponenteVO tatri:tatrisit)
			{
				/*if(tatri.getColumna().equals("S")
						&&tatri.getSwpresenflot().equals("S")
						)
				{
					if(tatri.getCotflotrol().equals("*")
							|| tatri.getCotflotrol().lastIndexOf(new StringBuilder("|").append(cdsisrol).append("|").toString())!=-1)
					{
						gridCols.add(tatri);
					}
				}*/
				
				if(tatri.getNmpanelflot().equals("1"))
				{
					panel1.add(tatri);
				}
				/*
				else if(tatri.getNmpanelflot().equals("2"))
				{
					panel2.add(tatri);
				}
				*/
				else if(tatri.getNmpanelflot().equals("3"))
				{
					panel3.add(tatri);
				}
				else if(tatri.getNmpanelflot().equals("5"))
				{
					panel5.add(tatri);
				}
				else if(tatri.getNmpanelflot().equals("6"))
				{
					panel6.add(tatri);
				}
			}
			/*gridCols.add(editorPlan);*/
			
			setCheckpoint("Construyendo componentes");
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.setCdramo(cdramo);
			gc.setCdtipsit(cdtipsit);
			
			gc.generaComponentes(panel1, true, false, true, false, false, false);
			resp.getImap().put("panel1Items"  , gc.getItems());
			
			/*
			gc.generaComponentes(panel2, true, false, true, false, false, false);
			resp.getImap().put("panel2Items"  , gc.getItems());
			*/
			
			gc.generaComponentes(panel3, true, false, true, false, false, false);
			resp.getImap().put("panel3Items"  , gc.getItems());
			
			gc.generaComponentes(panel5, true, false, true, false, false, false);
			resp.getImap().put("panel5Items"  , gc.getItems());
			
			gc.generaComponentes(panel6, true, false, true, false, false, false);
			resp.getImap().put("panel6Items"  , gc.getItems());

			/*for(ComponenteVO tatri:gridCols)
			{
				if(StringUtils.isNotBlank(tatri.getNmpanelflot())&&tatri.getNmpanelflot().equals("2"))
				{
					tatri.setSoloLectura(true);
					tatri.setObligatorio(false);
				}
			}*/
			gc.generaComponentes(gridCols, true, false, false, true, false, false);
			resp.getImap().put("gridCols" , gc.getColumns());
			
			gc.generaComponentes(
					auxEditorSit
					,true  //esParcial
					,false //conField
					,true  //conItem
					,false //conColumn
					,false //conEditor
					,false //conButton
					);
			resp.getImap().put("cdtipsitItem" , gc.getItems());
			
			setCheckpoint("Recuperando situaciones");
			List<Map<String,String>>situaciones=consultasDAO.cargarTiposSituacionPorRamo(cdramo);
			String situacionesCSV = "";
			for(Map<String,String>situacion:situaciones)
			{
				
				setCheckpoint("Recuperando atributos de situaciones");
				String cdtipsitIte = situacion.get("CDTIPSIT");
				situacionesCSV=Utils.join(situacionesCSV,",",cdtipsitIte);
				List<ComponenteVO>tatrisitSitIte        = cotizacionDAO.cargarTatrisit(cdtipsitIte, cdusuari);
				List<ComponenteVO>tatrisitSitIteParcial = new ArrayList<ComponenteVO>();
				List<ComponenteVO>tatrisitSitIteAuto    = new ArrayList<ComponenteVO>();
				
				setCheckpoint("Recuperando sustitutos de atributos de situaciones");
				//sustitutos
				List<ComponenteVO>sustitutosSituacion = pantallasDAO.obtenerComponentes(
						TipoTramite.POLIZA_NUEVA.getCdtiptra()
						,cdunieco
						,cdramo
						,cdtipsitIte
						,"C"
						,cdsisrol
						,"COTIZACION_CUSTOM"
						,"SUSTITUTOS"
						,null
						);
				if(sustitutosSituacion.size()>0)
				{
					aux=new ArrayList<ComponenteVO>();
					for(ComponenteVO tatri : tatrisitSitIte)
					{
						String cdatribuTatri = tatri.getNameCdatribu();
						boolean sustituido   = false;
						for(ComponenteVO sustituto : sustitutosSituacion)
						{
							String cdatribuSustituto = sustituto.getNameCdatribu();
							logger.debug(new StringBuilder("tatri=").append(cdatribuTatri).append(" vs susti=").append(cdatribuSustituto).toString());
							if(cdatribuSustituto.equals(cdatribuTatri))
							{
								sustituto.setNmpanelflot(tatri.getNmpanelflot());
								sustituto.setCotflotrol(tatri.getCotflotrol());
								sustituto.setColumna(tatri.getColumna());
								sustituto.setSwpresenflot(tatri.getSwpresenflot());
								sustituto.setNmordenFlot(tatri.getNmordenFlot());
								sustituto.setObligatorioFlot(tatri.isObligatorioFlot());
								sustituido = true;
								aux.add(sustituto);
							}
						}
						if(!sustituido)
						{
							aux.add(tatri);
						}
					}
					tatrisitSitIte = aux;
				}
				//sustitutos
				
				setCheckpoint("Aislando atributos de situaciones parciales");
				for(ComponenteVO tatri:tatrisitSitIte)
				{
					if(tatri.getColumna().equals("S")
							&&tatri.getSwpresenflot().equals("S"))
					{
						tatrisitSitIteParcial.add(tatri);
					}
					else if(endoso&&StringUtils.isNotBlank(tatri.getSwCompFlot())
								&&tatri.getSwCompFlot().equals("S"))
					{
						tatrisitSitIteParcial.add(tatri);
					}
				}
				tatrisitSitIteParcial=ComponenteVO.ordenarPorNmordenFlot(tatrisitSitIteParcial);
				
				setCheckpoint("Aislando atributos de auto de situaciones");
				for(ComponenteVO tatri:tatrisitSitIte)
				{
					if(tatri.getSwpresenflot().equals("S")
							&&tatri.getNmpanelflot().equals("2")
							)
					{
						tatrisitSitIteAuto.add(tatri);
					}
				}
				tatrisitSitIteAuto=ComponenteVO.ordenarPorNmordenFlot(tatrisitSitIteAuto);
				
				setCheckpoint("Recuperando editor de planes de situacion");
				List<ComponenteVO>auxEditorPlan = pantallasDAO.obtenerComponentes(
						TipoTramite.POLIZA_NUEVA.getCdtiptra(), null, cdramo
						,cdtipsitIte, null, cdsisrol
						,"COTIZACION_FLOTILLA", "EDITOR_PLANES", null);
				tatrisitSitIteParcial.add(auxEditorPlan.get(0));
				
				setCheckpoint("Construyendo componentes de situaciones");
				GeneradorCampos gcIte = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				gcIte.setCdramo(cdramo);
				gcIte.setCdtipsit(cdtipsitIte);
				
				gcIte.generaComponentes(tatrisitSitIte, true, false, true, false, false, false);
				resp.getImap().put(Utils.join("tatrisit_full_items_",cdtipsitIte),gcIte.getItems());
				
				for(ComponenteVO tatri:tatrisitSitIteParcial)
				{
					tatri.setObligatorio(tatri.isObligatorioFlot());
					tatri.setLabelTop(true);
					tatri.setWidth(150);
					tatri.setComboVacio(true);
				}
				
				gcIte.generaComponentes(tatrisitSitIteParcial,true,false,true,false,false,false);
				resp.getImap().put(Utils.join("tatrisit_parcial_items_",cdtipsitIte),gcIte.getItems());
				
				for(ComponenteVO tatri:tatrisitSitIteAuto)
				{
					tatri.setComboVacio(true);
				}
				
				gcIte.generaComponentes(tatrisitSitIteAuto,true,false,true,false,false,false);
				resp.getImap().put(Utils.join("tatrisit_auto_items_",cdtipsitIte),gcIte.getItems());
				
			}
			situacionesCSV=situacionesCSV.substring(1);
			logger.debug(Utils.join("situacionesCSV=",situacionesCSV));
			resp.getSmap().put("situacionesCSV",situacionesCSV);
			
			setCheckpoint("Recuperando agrupacion de situaciones");
			Map<String,String>agrupAux = cotizacionDAO.obtenerParametrosCotizacion(
					ParametroCotizacion.FLOTILLA_AGRUPACION_SITUACION, cdramo, cdtipsit, cdsisrol, tipoflot);
			
			Map<String,String>botones    = new LinkedHashMap<String,String>();
			Map<String,String>agrupacion = new LinkedHashMap<String,String>();
			List<String>situacionesPanel = new ArrayList<String>();
			
			for(int i=1;i<=13;i++)
			{
				String claveKey = new StringBuilder("P").append(i).append("CLAVE").toString();
				String valorKey = new StringBuilder("P").append(i).append("VALOR").toString();
				String clave    = agrupAux.get(claveKey);
				if(!isBlank(clave))
				{
					String cdtipsitOrigen = clave.split("_")[0];
					String textoBoton     = clave.split("_")[1];
					String cdtipsitDestin = agrupAux.get(valorKey);
					if(!botones.containsKey(textoBoton))
					{
						botones.put(textoBoton,cdtipsitOrigen);
						situacionesPanel.add(cdtipsitOrigen);
						resp.getSmap().put(new StringBuilder("boton_").append(textoBoton).toString(),cdtipsitOrigen);
					}
					agrupacion.put(cdtipsitDestin,cdtipsitOrigen);
					resp.getSmap().put(new StringBuilder("destino_").append(cdtipsitDestin).toString(),cdtipsitOrigen);
				}
			}
			logger.debug(new StringBuilder("\nbotones=").append(botones)
					.append("\nagrupacion=").append(agrupacion)
					.append("\nsituacionesPanel=").append(situacionesPanel)
					.toString());
			
			for(String situacionPanel:situacionesPanel)
			{
				setCheckpoint(new StringBuilder("Recuperando atributos de panel dinamico ").append(situacionPanel).toString());
				List<ComponenteVO>tatrisitPanel    = cotizacionDAO.cargarTatrisit(situacionPanel, cdusuari);
				List<ComponenteVO>tatrisitPanelAux = new ArrayList<ComponenteVO>();
				for(ComponenteVO tatri:tatrisitPanel)
				{
					if(tatri.getSwpresenflot().equals("S")
							&&tatri.getNmpanelflot().equals("4")
							&&tatri.getSwtarifi().equals("S"))
					{
						tatrisitPanelAux.add(tatri);
					}
				}
				tatrisitPanel=tatrisitPanelAux;
				
				setCheckpoint(Utils.join("Ordenando atributos de panel dinamico ",situacionPanel));
				ComponenteVO auxOrd = null;
				for(int i=0;i<tatrisitPanel.size()-1;i++)
				{
					int nmordenflotI = tatrisitPanel.get(i).getNmordenFlot();
					if(nmordenflotI==0)
					{
						nmordenflotI=99;
					}
					for(int j=i+1;j<tatrisitPanel.size();j++)
					{
						int nmordenflotJ = tatrisitPanel.get(j).getNmordenFlot();
						if(nmordenflotJ==0)
						{
							nmordenflotJ=99;
						}
						if(nmordenflotI>nmordenflotJ)
						{
							auxOrd = tatrisitPanel.get(i);
							tatrisitPanel.set(i , tatrisitPanel.get(j));
							tatrisitPanel.set(j , auxOrd);
							nmordenflotI = nmordenflotJ;
						}
					}
				}
				
				setCheckpoint("Recuperando componentes adicionales para configuracion");
				List<ComponenteVO>listaAdicionales=pantallasDAO.obtenerComponentes(
						null                   //cdtiptra
						,null                  //cdunieco
						,cdramo
						,situacionPanel        //cdtipsit
						,null                  //estado
						,cdsisrol
						,"COTIZACION_FLOTILLA" //pantalla
						,"CONFIG_ADICIONALES"  //seccion
						,null                  //orden
						);
				for(ComponenteVO tatri:listaAdicionales)
				{
					logger.debug("se puso adicional");
					tatri.setAuxiliar("adicional");
					tatrisitPanel.add(tatri);
				}
				
				List<Map<String,String>> listaAtriPorRol = consultasDAO.recuperarAtributosPorRol(situacionPanel,cdsisrol);
				for(ComponenteVO tatri : tatrisitPanel)
				{
					String cdatribu1 = tatri.getNameCdatribu();
					boolean quitar = false;
					String  valor  = null;
					for(Map<String,String> atri : listaAtriPorRol)
					{
						String cdatribu2 = atri.get("CDATRIBU");
						if(cdatribu1.equals(cdatribu2)&&"0".equals(atri.get("APLICA")))
						{
							quitar = true;
							valor  = atri.get("VALOR");
						}
					}
					if(quitar)
					{
						tatri.setOculto(true);
						tatri.setValue("'"+valor+"'");
					}
				}
				
				setCheckpoint(new StringBuilder("Construyendo panel dinamico ").append(situacionPanel).toString());
				GeneradorCampos gcPanel = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				gcPanel.setCdramo(cdramo);
				gcPanel.setCdtipsit(situacionPanel);
				gcPanel.generaComponentes(tatrisitPanel, true, false, true, false, false, false);
				logger.debug(new StringBuilder("\nelementos del panel=").append(gcPanel.getItems()).toString());
				resp.getImap().put(new StringBuilder("paneldin_").append(situacionPanel).toString(),gcPanel.getItems());
			}
			
			setCheckpoint("Recuperando mapeos de situaciones");
			try
			{
				Map<String,String>mapeo= cotizacionDAO.obtenerParametrosCotizacion(
						ParametroCotizacion.MAPEO_TVALOSIT_FORMS_FLOTILLAS, cdramo, cdtipsit, null, null);
				resp.getSmap().put("mapeo" , mapeo.get("P1VALOR")+mapeo.get("P2VALOR"));
			}
			catch(Exception ex)
			{
				resp.getSmap().put("mapeo" , "DIRECTO");
			}
			
			setCheckpoint("Recuperando atributos de poliza");
			List<ComponenteVO>tatripol    = cotizacionDAO.cargarTatripol(cdramo,null,null);
			List<ComponenteVO>tatripolAux = new ArrayList<ComponenteVO>();
			for(ComponenteVO tatri:tatripol)
			{
				if("S".equals(tatri.getSwpresen()))
				{
					tatripolAux.add(tatri);
				}
			}
			tatripol=tatripolAux;
			resp.getSmap().put("tatripolItemsLength" , String.valueOf(tatripol.size()));
			logger.debug(Utils.join("tatripolItems=",tatripol));
			
			if(tatripol.size()>0)
			{
				GeneradorCampos gcTatripol = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				gcTatripol.setCdramo(cdramo);
				gcTatripol.setAuxiliar(true);
				gcTatripol.generaComponentes(tatripol, true, false, true, false, false, false);
				resp.getImap().put("tatripolItems" , gcTatripol.getItems());
			}
			else
			{
				resp.getImap().put("tatripolItems" , null);
			}
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cotizacionAutoFlotilla @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSlistSmapVO cotizarAutosFlotilla(
			String cdusuari
			,String cdsisrol
			,String cdelemen
			,String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String feini
			,String fefin
			,String cdagente
			,String cdpersonCli
			,String cdideperCli
			,List<Map<String,String>> tvalosit
			,List<Map<String,String>> baseTvalosit
			,List<Map<String,String>> confTvalosit
			,boolean noTarificar
			,String tipoflot
			,Map<String,String>tvalopol
			)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cotizarAutosFlotilla @@@@@@")
				.append("\n@@@@@@ cdusuari=")     .append(cdusuari)
				.append("\n@@@@@@ cdsisrol=")     .append(cdsisrol)
				.append("\n@@@@@@ cdunieco=")     .append(cdunieco)
				.append("\n@@@@@@ cdramo=")       .append(cdramo)
				.append("\n@@@@@@ cdtipsit=")     .append(cdtipsit)
				.append("\n@@@@@@ estado=")       .append(estado)
				.append("\n@@@@@@ nmpoliza=")     .append(nmpoliza)
				.append("\n@@@@@@ fechaInicio=")  .append(feini)
				.append("\n@@@@@@ fechaFin=")     .append(fefin)
				.append("\n@@@@@@ cdagente=")     .append(cdagente)
				.append("\n@@@@@@ cdpersonCli=")  .append(cdpersonCli)
				.append("\n@@@@@@ cdideperCli=")  .append(cdideperCli)
				.append("\n@@@@@@ tvalosit=")     .append(tvalosit)
				.append("\n@@@@@@ baseTvalosit=") .append(baseTvalosit)
				.append("\n@@@@@@ confTvalosit=") .append(confTvalosit)
				.append("\n@@@@@@ noTarificar=")  .append(noTarificar)
				.append("\n@@@@@@ tipoflot=")     .append(tipoflot)
				.append("\n@@@@@@ tvalopol=")     .append(tvalopol)
				.toString()
				);
		
		ManagerRespuestaSlistSmapVO resp=new ManagerRespuestaSlistSmapVO(true);
		resp.setSmap(new HashMap<String,String>());
		
		try
		{
			if(noTarificar==false)
			{
				
				Date fechaHoy = new Date();
				
				if(isBlank(nmpoliza))
				{
					setCheckpoint("Generando numero de poliza");
					nmpoliza = cotizacionDAO.calculaNumeroPoliza(cdunieco, cdramo, estado);
					resp.getSmap().put("nmpoliza" , nmpoliza);
				}
				
				setCheckpoint("Insertando maestro de poliza");
				cotizacionDAO.movimientoPoliza(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,"0"      //nmsuplem
						,"V"      //status
						,"0"      //swestado
						,null     //nmsolici
			            ,null     //feautori
			            ,null     //cdmotanu
			            ,null     //feanulac
			            ,"N"      //swautori
			            ,"001"    //cdmoneda
			            ,null     //feinisus
			            ,null     //fefinsus
			            ,"R"      //ottempot
			            ,feini
			            ,"12:00"  //hhefecto
			            ,fefin
			            ,null     //fevencim
			            ,"0"      //nmrenova
			            ,null     //ferecibo
			            ,null     //feultsin
			            ,"0"      //nmnumsin
			            ,"N"      //cdtipcoa
			            ,"A"      //swtarifi
			            ,null     //swabrido
			            ,renderFechas.format(fechaHoy) //feemisio
			            ,"12"     //cdperpag
			            ,null     //nmpoliex
			            ,"P1"     //nmcuadro
			            ,"100"    //porredau
			            ,"S"      //swconsol
			            ,null     //nmpolant
			            ,null     //nmpolnva
			            ,renderFechas.format(fechaHoy) //fesolici
			            ,cdagente //cdramant
			            ,null     //cdmejred
			            ,null     //nmpoldoc
			            ,null     //nmpoliza2
			            ,null     //nmrenove
			            ,null     //nmsuplee
			            ,null     //ttipcamc
			            ,null     //ttipcamv
			            ,null     //swpatent
			            ,"100"    //pcpgocte
			            ,tipoflot
			            ,"U"      //accion
						);
				
				setCheckpoint("Insertando atributos adicionales de poliza");
				cotizacionDAO.movimientoTvalopol(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,"0" //nmsuplem
						,"V" //status
						,tvalopol);
				
				setCheckpoint("Insertando maestro de agrupadores de poliza");
				cotizacionDAO.movimientoMpoliagr(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,"1"  //cdagrupa
						,"0"  //nmsuplem
						,"V"  //status
						,"0"  //cdperson
						,"1"  //nmorddom
						,"1"  //cdforpag
						,null //cdbanco
						,null //cdsucurs
						,null //cdcuenta
						,null //cdrazon
						,null //swregula
						,null //cdperreg
						,null //feultreg
						,null //cdgestor
						,null //cdrol
						,null //cdbanco2
						,null //cdsucurs2
						,null //cdcuenta2
						,null //cdtipcta
						,null //cdtipcta2
						,"1"  //cdpagcom
						,null //nmpresta
						,null //nmpresta2
						,null //cdbanco3
						,null //cdsucurs3
						,null //cdcuenta3
						,null //cdtipcta3
						,null //nmpresta3
						,null //nmcuenta
						,"I"  //accion
						);
				
				setCheckpoint("Construyendo lote de maestros de situacion");
				long                  inicioMpolisit    = System.currentTimeMillis();
				List<PMovMpolisitDTO> listaPMovMpolisit = new ArrayList<PMovMpolisitDTO>();
				for(Map<String,String>tvalositIte:tvalosit)
				{
					listaPMovMpolisit.add(new PMovMpolisitDTO(
							cdunieco
							,cdramo
							,estado
							,nmpoliza
							,tvalositIte.get("nmsituac") //nmsituac
							,"0"                         //nmsuplem
							,"V"                         //status
							,tvalositIte.get("cdtipsit") //cdtipsit
							,null                        //swreduci
							,"1"                         //cdagrupa
							,"0"                         //cdestado
							,feini                       //fefecsit
							,feini                       //fecharef
							,null                        //cdgrupo
							,null                        //nmsituaext
							,null                        //nmsitaux
							,null                        //nmsbsitext
							,tvalositIte.get("cdplan")   //cdplan
							,"30"                        //cdasegur
							,"I"                         //accion
							));
				}
				setCheckpoint("Insertando maestros de situacion en lote");
				cotizacionDAO.movimientoMpolisitLote(listaPMovMpolisit);
				logger.debug(Utils.join("Tiempo en mpolisit=",(System.currentTimeMillis()-inicioMpolisit)/1000d));
			}
				
			setCheckpoint("Construyendo lote de atributos de situacion");
			long                  inicioTvalosit    = System.currentTimeMillis();
			List<PMovTvalositDTO> listaPMovTvalosit = new ArrayList<PMovTvalositDTO>();
			for(Map<String,String>tvalositIte:tvalosit)
			{
				PMovTvalositDTO pMovTvalosit = new PMovTvalositDTO(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,tvalositIte.get("nmsituac") //nmsituac
						,"0"                         //nmsuplem
						,"V"                         //status
						,tvalositIte.get("cdtipsit") //cdtipsit
						,"I"                         //accion
						);
				for(Entry<String,String>valosit:tvalositIte.entrySet())
				{
					String key = valosit.getKey();
					if(!isBlank(key)
							&&key.length()>"parametros.pv_".length()
							&&key.substring(0, "parametros.pv_".length()).equals("parametros.pv_"))
					{
						pMovTvalosit.setOtvalor(Integer.parseInt(key.substring("parametros.pv_otvalor".length())),valosit.getValue());
					}
				}
				listaPMovTvalosit.add(pMovTvalosit);
			}
			setCheckpoint("Insertando atributos de situacion en lote");
			cotizacionDAO.movimientoTvalositLote(listaPMovTvalosit);
			logger.debug(Utils.join("Tiempo en tvalosit=",(System.currentTimeMillis()-inicioTvalosit)/1000d));
				
			setCheckpoint("Borrando situaciones base anteriores");
			cotizacionDAO.borrarTbasvalsit(cdunieco, cdramo, estado, nmpoliza);
		
			setCheckpoint("Construyendo lote de situaciones base");
			long                        inicioTbasvalsit        = System.currentTimeMillis();
			List<PInsertaTbasvalsitDTO> listaPInsertaTbasvalsit = new ArrayList<PInsertaTbasvalsitDTO>();
			for(Map<String,String>baseTvalositIte:baseTvalosit)
			{
				PInsertaTbasvalsitDTO pInsertaTBasvalsit = new PInsertaTbasvalsitDTO(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,String.valueOf(baseTvalositIte.get("nmsituac"))
						,"0" //nmsuplem
						,"V" //status
						,baseTvalositIte.get("cdtipsit")
						);
				for(Entry<String,String>valosit:baseTvalositIte.entrySet())
				{
					String key = valosit.getKey();
					if(!isBlank(key)
							&&key.length()>"parametros.pv_".length()
							&&key.substring(0, "parametros.pv_".length()).equals("parametros.pv_"))
					{
						pInsertaTBasvalsit.setOtvalor(Integer.parseInt(key.substring("parametros.pv_otvalor".length())),valosit.getValue());
					}
				}
				listaPInsertaTbasvalsit.add(pInsertaTBasvalsit);
			}
			setCheckpoint("Insertando situaciones base en lote");
			cotizacionDAO.movimientoTbasvalsitLote(listaPInsertaTbasvalsit);
			logger.debug(Utils.join("Tiempo en tbasvalsit=",(System.currentTimeMillis()-inicioTbasvalsit)/1000d));
			
			if(noTarificar==false)
			{
				setCheckpoint("Borrando configuracion de situaciones anteriores");
				cotizacionDAO.borrarTconvalsit(cdunieco, cdramo, estado, nmpoliza);
				
				setCheckpoint("Construyendo lote de configuraciones de situacion");
				long                        inicioTconvalsit        = System.currentTimeMillis();
				List<PInsertaTconvalsitDTO> listaPInsertaTconvalsit = new ArrayList<PInsertaTconvalsitDTO>();
				int aux=1;
				for(Map<String,String>confTvalositIte:confTvalosit)
				{
					PInsertaTconvalsitDTO pInsertaTconvalsit = new PInsertaTconvalsitDTO(
							cdunieco
							,cdramo
							,estado
							,nmpoliza
							,String.valueOf(aux) //nmsituac
							,"0"                 //nmsuplem
							,"V"                 //status
							,confTvalositIte.get("cdtipsit")
							);
					for(Entry<String,String>valosit:confTvalositIte.entrySet())
					{
						String key = valosit.getKey();
						if(!isBlank(key)
								&&key.length()>"parametros.pv_".length()
								&&key.substring(0, "parametros.pv_".length()).equals("parametros.pv_"))
						{
							pInsertaTconvalsit.setOtvalor(Integer.parseInt(key.substring("parametros.pv_otvalor".length())),valosit.getValue());
						}
					}
					listaPInsertaTconvalsit.add(pInsertaTconvalsit);
					
					aux=aux+1;
				}
				setCheckpoint("Insertando configuracion de situaciones en lote");
				cotizacionDAO.movimientoTconvalsitLote(listaPInsertaTconvalsit);
				logger.debug(Utils.join("Tiempo en tconvalsit=",(System.currentTimeMillis()-inicioTconvalsit)/1000d));
				
				
				/**
				 * TODO: EVALUAR E IMPLEMENTAR CDPERSON TEMPORAL
				 */

				if (StringUtils.isBlank(cdpersonCli) && StringUtils.isNotBlank(cdideperCli)) {
					logger.debug("Persona proveniente de WS, Se importar�, Valor de cdperson en blanco, valor de cdIdeper: " + cdideperCli);
					
					
					/**
					 * TODO: EVALUAR E IMPLEMENTAR CDPERSON TEMPORAL
					 * PARA GUARDAR CLIENTE EN BASE DE DATOS DEL WS, se traslada codigo de comprar cotizacion a cotizacion pues pierde el mpersona cuando se recuperan cotizaciones
					 */

					String cdtipsitGS = consultasDAO.obtieneSubramoGS(cdramo, cdtipsit);

					ClienteGeneral clienteGeneral = new ClienteGeneral();
					// clienteGeneral.setRfcCli((String)aseg.get("cdrfc"));
					clienteGeneral.setRamoCli(Integer
							.parseInt(cdtipsitGS));
					clienteGeneral.setNumeroExterno(cdideperCli);

					ClienteGeneralRespuesta clientesRes = ice2sigsService
							.ejecutaWSclienteGeneral(
									null,
									null,
									null,
									null,
									null,
									null,
									null,
									Ice2sigsService.Operacion.CONSULTA_GENERAL,
									clienteGeneral, null, false);

					if (clientesRes != null
							&& ArrayUtils.isNotEmpty(clientesRes
									.getClientesGeneral())) {
						ClienteGeneral cli = null;

						if (clientesRes.getClientesGeneral().length == 1) {
							logger.debug("Cliente unico encontrado en WS, guardando informacion del WS...");
							cli = clientesRes.getClientesGeneral()[0];
						} else {
							logger.error("Error, No se pudo obtener el cliente del WS. Se ha encontrado mas de Un elemento!");
						}

						if (cli != null) {

							/**
							 * TODO: EVALUAR E IMPLEMENTAR CDPERSON TEMPORAL
							 */
							
							// IR POR NUEVO CDPERSON:
							String newCdPerson = personasDAO.obtenerNuevoCdperson();

							logger.debug("Insertando nueva persona, cdperson generado: " +newCdPerson);
				    		
				    		String apellidoPat = "";
					    	if(StringUtils.isNotBlank(cli.getApellidopCli()) && !cli.getApellidopCli().trim().equalsIgnoreCase("null")){
					    		apellidoPat = cli.getApellidopCli();
					    	}
					    	
					    	String apellidoMat = "";
					    	if(StringUtils.isNotBlank(cli.getApellidomCli()) && !cli.getApellidomCli().trim().equalsIgnoreCase("null")){
					    		apellidoMat = cli.getApellidomCli();
					    	}
					    	
				    		Calendar calendar =  Calendar.getInstance();
				    		
				    		String sexo = "H"; //Hombre
					    	if(cli.getSexoCli() > 0){
					    		if(cli.getSexoCli() == 2) sexo = "M";
					    	}
					    	
					    	String tipoPersona = "F"; //Fisica
					    	if(cli.getFismorCli() > 0){
					    		if(cli.getFismorCli() == 2){
					    			tipoPersona = "M";
					    		}else if(cli.getFismorCli() == 3){
					    			tipoPersona = "S";
					    		}
					    	}
					    	
					    	if(cli.getFecnacCli()!= null){
					    		calendar.set(cli.getFecnacCli().get(Calendar.YEAR), cli.getFecnacCli().get(Calendar.MONTH), cli.getFecnacCli().get(Calendar.DAY_OF_MONTH));
					    	}
					    	
					    	
					    	Calendar calendarIngreso =  Calendar.getInstance();
					    	if(cli.getFecaltaCli() != null){
					    		calendarIngreso.set(cli.getFecaltaCli().get(Calendar.YEAR), cli.getFecaltaCli().get(Calendar.MONTH), cli.getFecaltaCli().get(Calendar.DAY_OF_MONTH));
					    	}
					    	
					    	String nacionalidad = "001";// Nacional
					    	if(StringUtils.isNotBlank(cli.getNacCli()) && !cli.getNacCli().equalsIgnoreCase("1")){
					    		nacionalidad = "002";
					    	}
					    	
				    		//GUARDAR MPERSONA
				    		
							personasDAO.movimientosMpersona(newCdPerson, "1", cli.getNumeroExterno(), (cli.getFismorCli() == 1) ? cli.getNombreCli() : cli.getRazSoc()
									, "1", tipoPersona, sexo, calendar.getTime(), cli.getRfcCli(), cli.getMailCli(), null
									, apellidoPat, apellidoMat, calendarIngreso.getTime(), nacionalidad, cli.getCanconCli() <= 0 ? "0" : (Integer.toString(cli.getCanconCli()))
									, null, null, null, null, null, null, Integer.toString(cli.getSucursalCli()), "I");
							
							String edoAdosPos2 = Integer.toString(cli.getEstadoCli());
			    			if(edoAdosPos2.length() ==  1){
			    				edoAdosPos2 = "0"+edoAdosPos2;
			    			}
				    		
				    		//GUARDAR DOMICILIO
			    			
			    			personasDAO.movimientosMdomicil(newCdPerson, "1", cli.getCalleCli(), cli.getTelefonoCli()
				    				, cli.getCodposCli(), cli.getCodposCli()+edoAdosPos2, null/*cliDom.getMunicipioCli()*/, null/*cliDom.getColoniaCli()*/
				    				, cli.getNumeroCli(), null, "I");

			    			//GUARDAR TVALOPER
			    			
			    			personasDAO.movimientosTvaloper("1", newCdPerson, cli.getCveEle(), cli.getPasaporteCli(), null, null, null,
			    				null, null, cli.getOrirecCli(), null, null,
			    				cli.getNacCli(), null, null, null, null, 
			    				null, null, null, null, (cli.getOcuPro() > 0) ? Integer.toString(cli.getOcuPro()) : "0", 
			    				null, null, null, null, cli.getCurpCli(), 
			    				null, null, null, null, null, 
			    				null, null, null, null, null, 
			    				null, null, cli.getTelefonoCli(), cli.getMailCli(), null, 
			    				null, null, null, null, null, 
			    				null, null, null, null, null);
			    			
			    			
			    			cdpersonCli = newCdPerson;

						}
					}
				
				}
				
				
				if(!isBlank(cdpersonCli))
				{
					setCheckpoint("Guardando contratante");
					cotizacionDAO.movimientoMpoliper(
							cdunieco
							,cdramo
							,estado
							,nmpoliza
							,"0"  //nmsituac
							,"1"  //cdrol
							,cdpersonCli
							,"0"  //nmsuplem
							,"V"  //status
							,"1"  //nmorddom
							,null //swreclam
							,"I"  //accion
							,"S"  //swexiper
							);
				}
				
				setCheckpoint("Aplicando ajustes de cotizacion");
				cotizacionDAO.aplicarAjustesCotizacionPorProducto(cdunieco, cdramo, estado, nmpoliza, cdtipsit, tipoflot);
				
				setCheckpoint("Generando tarificacion concurrente");
				cotizacionDAO.ejecutaValoresDefectoTarificacionConcurrente(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,"0" //nmsuplem
						,"0" //nmsituac
						,"1" //tipotari
						,"1" //cdperpag
						);
			}
			
			setCheckpoint("Recuperando tarificacion");
			resp.setSlist(cotizacionDAO.cargarResultadosCotizacionAutoFlotilla(cdunieco, cdramo, estado, nmpoliza));
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cotizarAutosFlotilla @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaVoidVO cargarValidacionTractocamionRamo5(String poliza,String rfc)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarValidacionTractocamionRamo5 @@@@@@")
				.append("\n@@@@@@ poliza=").append(poliza)
				.append("\n@@@@@@ rfc=")   .append(rfc)
				.toString()
				);
		
		ManagerRespuestaVoidVO resp = new ManagerRespuestaVoidVO(true);
		
		try
		{
			setCheckpoint("Invocando web service");
			Object polizaValida = tractoCamionService.validarPolizaTractoCamion(poliza, rfc); 
			resp.setExito(polizaValida.getClass().equals(Boolean.class)&&polizaValida.equals(Boolean.TRUE));
			resp.setRespuesta(String.valueOf(polizaValida));
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarValidacionTractocamionRamo5 @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSlistVO procesarCargaMasivaFlotilla(String cdramo,String cdtipsit,String respetar,File excel)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ procesarCargaMasivaFlotilla @@@@@@")
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.append("\n@@@@@@ respetar=").append(respetar)
				.append("\n@@@@@@ excel=")   .append(excel)
				.toString()
				);
		
		ManagerRespuestaSlistVO resp = new ManagerRespuestaSlistVO(true);
		resp.setSlist(new ArrayList<Map<String,String>>());
		
		try
		{
			setCheckpoint("Recuperando parametrizacion de excel para COTIFLOT");
			List<Map<String,String>>config=cotizacionDAO.cargarParametrizacionExcel("COTIFLOT",cdramo,cdtipsit);
			logger.debug(config);
			
			setCheckpoint("Instanciando mapa buffer de tablas de apoyo");
			Map<String,List<Map<String,String>>> buffer        = new HashMap<String,List<Map<String,String>>>();
			Map<String,String>                   bufferTiposit = new HashMap<String,String>();
			
			setCheckpoint("Iniciando procesador de hoja de calculo");
			FileInputStream input       = new FileInputStream(excel);
			XSSFWorkbook    workbook    = new XSSFWorkbook(input);
			XSSFSheet       sheet       = workbook.getSheetAt(0);
			Iterator<Row>   rowIterator = sheet.iterator();
			StringBuilder   sb;
			
			setCheckpoint("Iterando filas");
			int fila = 1;
			String[] columnas=new String[]{
					  "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
					,"AA","AB","AC","AD","AE","AF","AG","AH","AI","AJ","AK","AL","AM","AN","AO","AP","AQ","AR","AS","AT","AU","AV","AW","AX","AY","AZ"
					,"BA","BB","BC","BD","BE","BF","BG","BH","BI","BJ","BK","BL","BM","BN","BO","BP","BQ","BR","BS","BT","BU","BV","BW","BX","BY","BZ"
			};
			while (rowIterator.hasNext()) 
            {
				fila = fila + 1;
				setCheckpoint(new StringBuilder("Iterando fila ").append(fila).toString());
				Row row = rowIterator.next();
				
				if(fila==2)
				{
					row = rowIterator.next();
				}
				
				sb = new StringBuilder();
				
				Map<String,String>record=new LinkedHashMap<String,String>();
				resp.getSlist().add(record);
				
				for(Map<String,String>conf:config)
				{
					int      col         = Integer.valueOf(conf.get("COLUMNA"));
					String   cdtipsitCol = conf.get("CDTIPSIT");
					String   propiedad   = conf.get("PROPIEDAD");
					String   tipo        = conf.get("TIPO");
					boolean  requerido   = !isBlank(conf.get("REQUERIDO"))&&conf.get("REQUERIDO").equals("S");
					String   decode      = conf.get("DECODE");
					String[] splited     = null;
					String   cdtabla1    = conf.get("CDTABLA1");
					String   tipoatri    = conf.get("TIPOATRI");
					String   valorStat   = conf.get("VALOR");
					String   orCdtipsit  = conf.get("ORIGEN_CDTIPSIT");
					String   otraProp1   = conf.get("OTRA_PROP1");
					String   otroVal1    = conf.get("OTRO_VAL1");
					if(!isBlank(decode))
					{
						splited = decode.split(",");
					}
					
					sb.append("@").append(propiedad)
					.append("[").append(cdtipsitCol).append("]")
					.append("*").append(tipo)
					.append("#").append(tipoatri)
					.append("~").append(valorStat)
					.append("{").append(orCdtipsit).append("}")
					.append("&").append(otraProp1).append("=").append(otroVal1);
					
					Cell cell = row.getCell(col);
					if(propiedad.equals("cdtipsit"))
					{
						String valor = null;
						try
						{
						    valor = cell.getStringCellValue();
						}
						catch(NullPointerException ex)
						{
							throwExc(Utils.join("La fila ",fila," tiene valores pero no tiene TIPO VEHICULO"));
						}
						sb.append(">cdtipsit!").append(valor);						
						for(int i=0;i<splited.length/2;i++)
						{
							String splitedUsado=splited[i*2];
							//logger.debug(Utils.join("valor=",valor,", contra=",splitedUsado,", lastIndexOf=",valor.lastIndexOf(splitedUsado)));
							if(valor.lastIndexOf(splitedUsado)!=-1)
							{
								valor=splited[(i*2)+1];
								sb.append("==").append(valor);
								break;
							}
						}
						if(valor.equals(cell.getStringCellValue()))
						{
							sb.append("==").append("ERROR");
							throw new ApplicationException(
									new StringBuilder("El tipo de vehiculo ")
									.append(valor)
									.append(" no viene dentro de ")
									.append(decode)
									.append(" en la fila ")
									.append(fila)
									.toString()
									); 
						}
						//logger.debug(new StringBuilder("valor=").append(valor).toString());
						record.put("cdtipsit",valor);
					}
					else if("S".equals(respetar)||tipoatri.equals("SITUACION"))
					{
						//nuevo para recuperar cdtipsit
						if(StringUtils.isNotBlank(orCdtipsit))
						{
							setCheckpoint(Utils.join("Recuperando tipo de situacion de la fila ",fila));
							
							sb.append(">").append(orCdtipsit);
							String cellValue         = null;
							Cell   nextCell          = row.getCell(col+1);
							String nextCellValue     = null;
							Cell   nextNextCell      = row.getCell(col+2);
							String nextNextCellValue = null;
							try
							{
								cellValue = cell.getStringCellValue();
							}
							catch(Exception ex)
							{
								sb.append("(E)");
								try
								{
									double num = cell.getNumericCellValue();
									cellValue  = String.format("%.0f",num);
								}
								catch(Exception ex2)
								{
									sb.append("(E)");
									throwExc(Utils.join("La columna ",columnas[col]," es requerida en la fila ",fila));
								}
							}
							try
							{
								nextCellValue = nextCell.getStringCellValue();
							}
							catch(Exception ex)
							{
								sb.append("(E)");
								try
								{
									double num    = nextCell.getNumericCellValue();
									nextCellValue = String.format("%.0f",num);
								}
								catch(Exception ex2)
								{
									sb.append("(E)");
									nextCellValue = "";
								}
							}
							try
							{
								nextNextCellValue = nextNextCell.getStringCellValue();
							}
							catch(Exception ex)
							{
								sb.append("(E)");
								try
								{
									double num    = nextNextCell.getNumericCellValue();
									nextNextCellValue = String.format("%.0f",num);
								}
								catch(Exception ex2)
								{
									sb.append("(E)");
									nextNextCellValue = "";
								}
							}
							String llaveBufferTiposit = Utils.join(orCdtipsit,"-",cellValue,"-",nextCellValue,"-",nextNextCellValue);
							String cdtipsitProc       = null;
							if(bufferTiposit.containsKey(llaveBufferTiposit))
							{
								sb.append("(buffer)");
								cdtipsitProc = bufferTiposit.get(llaveBufferTiposit);
							}
							else
							{
								sb.append("(sin buffer)");
								cdtipsitProc = consultasDAO.recuperarCdtipsitExtraExcel(
										fila
										,orCdtipsit
										,cellValue
										,nextCellValue
										,nextNextCellValue
										);
								bufferTiposit.put(llaveBufferTiposit,cdtipsitProc);
							}
							sb.append("==").append(cdtipsitProc);
							record.put("cdtipsit" , cdtipsitProc);
							
							setCheckpoint(Utils.join("Iterando fila ",fila));
						}
						//nuevo para recuperar cdtipsit
						
						String cdtipsitRecord = record.get("cdtipsit");
						if(cdtipsitCol.equals("*")||("|"+cdtipsitCol+"|").lastIndexOf("|"+cdtipsitRecord+"|")!=-1)
						{
							sb.append(">").append(cdtipsitRecord);
							if(StringUtils.isNotBlank(valorStat))
							{
								sb.append(">valorStat");
								record.put(propiedad , valorStat);
							}
							else if(isBlank(decode)&&isBlank(cdtabla1))
							{
							    if(tipo.equals("string"))
								{
									sb.append(">string");
									String valor = null;
									try
									{
										valor=cell.getStringCellValue();
									}
									catch(Exception ex)
									{
										sb.append("(E)");
										try
										{
											double num = cell.getNumericCellValue();
											valor = String.format("%.0f",num);
										}
										catch(Exception ex2)
										{
											sb.append("(E)");
											valor="";
										}
									}
									sb.append("==").append(valor);
									if(requerido&&isBlank(valor))
									{
										throwExc(Utils.join("La columna ",columnas[col]," es requerida en la fila ",fila));
									}
									record.put(propiedad,valor);
								}
								else if(tipo.equals("int"))
								{
									sb.append(">int");
									Double num = null;
									try
									{
										num=cell.getNumericCellValue();
									}
									catch(Exception ex)
									{
										sb.append("(E)");
										num=null;
									}
									if(requerido&&num==null)
									{
										throwExc(Utils.join("La columna ",columnas[col]," es requerida en la fila ",fila));
									}
									String valor="";
									if(num!=null)
									{
										valor=String.format("%d",num.intValue());
									}
									sb.append("==").append(valor);
									record.put(propiedad,valor);
								}
								else if(tipo.equals("double"))
								{
									sb.append(">double");
									Double num = null;
									try
									{
										num=cell.getNumericCellValue();
									}
									catch(Exception ex)
									{
										sb.append("(E)");
										num=null;
									}
									if(requerido&&num==null)
									{
										throwExc(Utils.join("La columna ",columnas[col]," es requerida en la fila ",fila));
									}
									String valor="";
									if(num!=null)
									{
										valor=String.format("%.2f",num);
									}
									sb.append("==").append(valor);
									record.put(propiedad,valor);
								}
								else if(tipo.length()>"int-string_".length()
										&&tipo.substring(0,"int-string_".length()).equals("int-string_")
										)
								{
									sb.append(">int-string");
									Double num = null;
									try
									{
										num=cell.getNumericCellValue();
									}
									catch(Exception ex)
									{
										sb.append("(E)");
										num=null;
									}
									if(requerido&&num==null)
									{
										throwExc(Utils.join("La columna ",columnas[col]," es requerida en la fila ",fila));
									}
									String valor="";
									if(num!=null)
									{
										int len = Integer.valueOf(tipo.split("_")[1]);
										valor=String.format(Utils.join("%0",len,"d"),num.intValue());
									}
									sb.append("==").append(valor);
									record.put(propiedad,valor);
									
									//nuevo para "OTRA PROPIEDAD ESTATICA"
									if(!isBlank(otraProp1)&&!isBlank(otroVal1)&&!isBlank(valor))
									{
										sb.append("(>otraProp1");
										String[] val1Splited = otroVal1.split(","); //B+,S,C,S,N
										String valorEncontrado = null;
										for(int i=0;i<val1Splited.length-1;i=i+2)
										{
											if(valor.equals(val1Splited[i]))
											{
												valorEncontrado=val1Splited[i+1];
												break;
											}
										}
										if(valorEncontrado==null)
										{
											valorEncontrado=val1Splited[val1Splited.length-1];
										}
										sb.append("&").append(otraProp1).append("==").append(valorEncontrado).append(")");
										record.put(otraProp1,valorEncontrado);
									}
									//nuevo para "OTRA PROPIEDAD ESTATICA"
								}
								else
								{
									throwExc(Utils.join("Error de parametrizacion: tipo de valor incorrecto para la columna ",col));
								}
							}
							else if(!isBlank(cdtabla1))
							{
								sb.append(">tabla");
								String valor = null;
								try
								{
									valor=cell.getStringCellValue();
								}
								catch(Exception ex)
								{
									sb.append("(E)");
									try
									{
										Double num = cell.getNumericCellValue();
										valor      = String.format("%d",num.intValue());
									}
									catch(Exception ex2)
									{
										sb.append("(E)");
										valor="";
									}
								}
								sb.append("!").append(valor);
								if(requerido&&isBlank(valor))
								{
									throwExc(Utils.join("La columna ",columnas[col]," es requerida en la fila ",fila));
								}
								String clave = "";
								if(!isBlank(valor))
								{
									try
									{
									    clave=cotizacionDAO.cargarClaveTtapvat1(cdtabla1, valor, buffer);
									    if(StringUtils.isNotBlank(clave)
									    		&&clave.length()>"num".length()
									    		&&clave.substring(0, "num".length()).equals("num")
									    		)
									    {
									    	//rebanamos cuando viene "num1" a "1"
									    	clave=clave.substring("num".length());
									    }
									}
									catch(Exception ex)
									{
										if(ex instanceof ApplicationException)
										{
											throwExc(Utils.join(columnas[col],fila,": ",ex.getMessage()));
										}
										else
										{
											throw ex;
										}
									}
									
									//nuevo para "OTRA PROPIEDAD ESTATICA"
									if(!isBlank(otraProp1)&&!isBlank(otroVal1))
									{
										sb.append("(>otraProp1");
										String[] val1Splited = otroVal1.split(","); //B+,S,C,S,N
										String valorEncontrado = null;
										for(int i=0;i<val1Splited.length-1;i=i+2)
										{
											if(valor.equals(val1Splited[i]))
											{
												valorEncontrado=val1Splited[i+1];
												break;
											}
										}
										if(valorEncontrado==null)
										{
											valorEncontrado=val1Splited[val1Splited.length-1];
										}
										sb.append("&").append(otraProp1).append("==").append(valorEncontrado).append(")");
										record.put(otraProp1,valorEncontrado);
									}
									//nuevo para "OTRA PROPIEDAD ESTATICA"
								}
								sb.append("==").append(clave);
								record.put(propiedad,clave);
							}
							else if(!isBlank(decode))
							{
								sb.append(">decode");
								String original = null;
								String valor    = null;
								try
								{
									valor    = cell.getStringCellValue();
									original = cell.getStringCellValue();
								}
								catch(Exception ex)
								{
									sb.append("(E)");
									try
									{
										Double num = cell.getNumericCellValue();
										valor      = String.format("%d",num.intValue());
										original   = String.format("%d",num.intValue());
									}
									catch(Exception ex2)
									{
										sb.append("(E)");
										valor    = "";
										original = "";
									}
								}
								sb.append("!").append(valor);
								if(requerido&&isBlank(valor))
								{
									throwExc(Utils.join("La columna ",columnas[col]," es requerida en la fila ",fila));
								}
								if(!isBlank(valor))
								{
									for(int i=0;i<splited.length/2;i++)
									{
										String splitedUsado=splited[i*2];
										//logger.debug(Utils.join("valor=",valor,", contra=",splitedUsado,", lastIndexOf=",valor.lastIndexOf(splitedUsado)));
										if(valor.lastIndexOf(splitedUsado)!=-1)
										{
											valor=splited[(i*2)+1];
											sb.append("==").append(valor);
											break;
										}
									}
									if(valor.equals(original))
									{
										throw new ApplicationException(Utils.join(
												"La descripcion "
												,valor
												," no viene dentro de '"
												,decode
												,"' en la fila "
												,fila
												," en la columna "
												,columnas[col]
												));
									}
								}
								record.put(propiedad,valor);
							}
						}
						else
						{
							sb.append(">NOTIPSIT[").append(cdtipsitRecord).append("]");
						}
					}
					else
					{
						sb.append(">NOCOBER");
					}
				}
				
				logger.debug(sb.toString());
            }
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}

		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ procesarCargaMasivaFlotilla @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSlist2SmapVO cargarCotizacionAutoFlotilla(
			String cdramo
			,String nmpoliza
			,String cdusuari
			,String cdsisrol
			)
	{
		logger.info(Utils.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ cargarCotizacionAutoFlotilla @@@@@@"
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				));
		
		ManagerRespuestaSlist2SmapVO resp = new ManagerRespuestaSlist2SmapVO(true);
		resp.setSmap(new LinkedHashMap<String,String>());
		
		try
		{
			setCheckpoint("Buscando cotizacion");
			List<Map<String,String>>listaCotizaciones=consultasDAO.cargarMpolizasPorParametrosVariables(
					null      //cdunieco
					,cdramo
					,"W"      //estado
					,nmpoliza
					,null     //nmsuplem
					,null     //nmsolici
					,null     //cdramant
					);
			List<Map<String,String>>listaEmisiones=consultasDAO.cargarMpolizasPorParametrosVariables(
					null      //cdunieco
					,cdramo
					,"M"      //estado
					,null     //nmpoliza
					,null     //nmsuplem
					,nmpoliza //nmsolici
					,null     //cdramant
					);
			if(listaCotizaciones.size()+listaEmisiones.size()==0)
			{
				throwExc("No existe la cotizacion");
			}
			
			setCheckpoint("Buscando cotizacion del usuario");
			Map<String,String>cotizacionEmision = consultasDAO.recuperarCotizacionFlotillas(
					cdramo
					,nmpoliza
					,cdusuari
					,cdsisrol
					);
			
			String cdunieco = cotizacionEmision.get("cdunieco");
			String estado   = cotizacionEmision.get("estado");
			       nmpoliza = cotizacionEmision.get("nmpoliza");
			String nmsuplem = cotizacionEmision.get("nmsuplem");
			String tipoflot = cotizacionEmision.get("tipoflot");
			String fesolici = cotizacionEmision.get("fesolici");
			String feini    = cotizacionEmision.get("feini");
			String fefin    = cotizacionEmision.get("fefin");
			
			boolean maestra = "M".equals(estado);
			
			checkBlank(cdunieco , "No se recupero la sucursal de la cotizacion");
			checkBlank(estado   , "No se recupero el estado de la cotizacion");
			checkBlank(nmpoliza , "No se recupero el numero de cotizacion");
			checkBlank(nmsuplem , "No se recupero el suplemento de la cotizacion");
			
			resp.getSmap().put("CDUNIECO" , cdunieco);
			resp.getSmap().put("ESTADO"   , estado);
			resp.getSmap().put("NMPOLIZA" , nmpoliza);
			resp.getSmap().put("TIPOFLOT" , tipoflot);
			resp.getSmap().put("FESOLICI" , fesolici);
			resp.getSmap().put("FEINI"    , feini);
			resp.getSmap().put("FEFIN"    , fefin);
			
			setCheckpoint("Recuperando configuracion de incisos");
			resp.setSlist1(Utils.concatenarParametros(consultasDAO.cargarTconvalsit(cdunieco,cdramo,estado,nmpoliza,nmsuplem),false));
			
			setCheckpoint("Recuperando incisos base");
			List<Map<String,String>>incisosBase    = consultasDAO.cargarTbasvalsit(cdunieco,cdramo,estado,nmpoliza,nmsuplem);
			Map<String,String>incisoPoliza         = new HashMap<String,String>();
			List<Map<String,String>>incisosBaseAux = new ArrayList<Map<String,String>>();
			for(Map<String,String>incisoBase:incisosBase)
			{
				String nmsituac    = incisoBase.get("NMSITUAC");
				String cdtipsitAnt = incisoBase.get("CDTIPSIT");
				//YA NO ES NECESARIO PORQUE YA VIENE EN P_GET_TBASVALSIT
				/*if(!nmsituac.equals("-1"))
				{
				    incisoBase.putAll(consultasDAO.cargarMpolisitSituac(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac));
				}*/
				incisoBase.put("CDTIPSIT" , cdtipsitAnt);
				incisoBase.put("cdtipsit" , cdtipsitAnt);
				incisoBase.put("cdplan"   , incisoBase.get("CDPLAN"));
				incisoBase.put("nmsituac" , incisoBase.get("NMSITUAC"));
				if(incisoBase.get("CDTIPSIT").equals("XPOLX"))
				{
					logger.debug(Utils.join("Es XPOLX",incisoBase));
					for(Entry<String,String>en:incisoBase.entrySet())
					{
						incisoPoliza.put("parametros.pv_"+en.getKey().toLowerCase(),en.getValue());
					}
				}
				else
				{
					logger.debug(Utils.join("No es XPOLX",incisoBase));
					incisosBaseAux.add(incisoBase);
				}
			}
			incisosBase=incisosBaseAux;
			resp.setSlist2(Utils.concatenarParametros(incisosBase,false));
			
			setCheckpoint("Recuperando datos generales");
			resp.getSmap().putAll(incisoPoliza);
			
			String cdperson = "";
			String cdideper = "";
			String ntramite = "";
			if(!maestra||true)
			{
				setCheckpoint("Recuperando relacion poliza-contratante");
				Map<String,String>relContratante0=consultasDAO.cargarMpoliperSituac(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,nmsuplem
						,"0"//nmsituac
						);
				
				if(relContratante0!=null)
				{
					setCheckpoint("Recuperando contratante");
					cdperson = relContratante0.get("CDPERSON");
					Map<String,String>contratante = personasDAO.cargarPersonaPorCdperson(cdperson);
					cdideper = contratante.get("CDIDEPER");
				}
				
				setCheckpoint("Recuperando tramite");
				List<Map<String,String>>tramites=mesaControlDAO.cargarTramitesPorParametrosVariables(
						TipoTramite.POLIZA_NUEVA.getCdtiptra()
						,null     //ntramite
						,cdunieco
						,cdramo
						,estado
						,null     //nmpoliza
						,nmsuplem
						,nmpoliza //nmsolici
						);
				if(tramites.size()>1)
				{
					throwExc("Tramites duplicados para la cotizacion");
				}
				if(tramites.size()==1)
				{
					ntramite=tramites.get(0).get("NTRAMITE");
				}
				
			}
			resp.getSmap().put("CDPERSON" , cdperson);
			resp.getSmap().put("CDIDEPER" , cdideper);
			resp.getSmap().put("NTRAMITE" , ntramite);
			
			try
			{
				Map<String,String>dias=cotizacionDAO.obtenerParametrosCotizacion(
						ParametroCotizacion.DIAS_VALIDOS_COTIZACION
						,cdramo
						,"*"
						,null
						,null
						);
				resp.getSmap().put("diasValidos" , dias.get("P1VALOR"));
			}
			catch(Exception ex)
			{
				logger.warn("error sin impacto funcional al recuperar dias validos de cotizacion, se cargan 15 dias",ex);
				resp.getSmap().put("diasValidos" , "15");
			}
			
			setCheckpoint("Recuperando atributos adicionales de poliza");
			Map<String,String>tvalopol=cotizacionDAO.cargarTvalopol(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					);
			for(Entry<String,String>en:tvalopol.entrySet())
			{
				resp.getSmap().put(Utils.join("aux.",en.getKey().substring("parametros.pv_".length())),en.getValue());
			}
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(Utils.join(
				 "\n@@@@@@ ",resp
				,"\n@@@@@@ cargarCotizacionAutoFlotilla @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	@Override
	public ManagerRespuestaImapSmapVO emisionAutoFlotilla(
			String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String ntramite
			,String cdusuari
			)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ emisionAutoFlotilla @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ ntramite=").append(ntramite)
				.append("\n@@@@@@ cdusuari=").append(cdusuari)
				.toString()
				);
		
		ManagerRespuestaImapSmapVO resp=new ManagerRespuestaImapSmapVO(true);
		resp.setImap(new HashMap<String,Item>());
		resp.setSmap(new HashMap<String,String>());
		
		try
		{
			setCheckpoint("Procesando datos de entrada");
			resp.getSmap().put("cdusuari" , cdusuari);
			
			setCheckpoint("Recuperando tipo de situacion");
			resp.getSmap().putAll(cotizacionDAO.cargarTipoSituacion(cdramo, cdtipsit));
			
			setCheckpoint("Recuperando atributos variables de poliza");
			List<ComponenteVO>tatripol = cotizacionDAO.cargarTatripol(cdramo,null,null);
			for(ComponenteVO tatri:tatripol)
			{
				if(!tatri.getSwpresemiflot().equals("S"))
				{
					tatri.setOculto(true);
				}
			}
			
			setCheckpoint("Recuperando situaciones");
			List<Map<String,String>>situaciones=consultasDAO.cargarTiposSituacionPorRamo(cdramo);
			for(Map<String,String>situacionIte:situaciones)
			{
				setCheckpoint("Recuperando atributos de situaciones");
				String cdtipsitIte             = situacionIte.get("CDTIPSIT");
				List<ComponenteVO>tatrisitIte  = cotizacionDAO.cargarTatrisit(cdtipsitIte, cdusuari);
				List<ComponenteVO>editablesIte = new ArrayList<ComponenteVO>();
				
				setCheckpoint("Filtrando atributos editables de situacion");
				for(ComponenteVO tatri:tatrisitIte)
				{
					if(StringUtils.isNotBlank(tatri.getSwCompFlot())
							&&tatri.getSwCompFlot().equals("S"))
					{
						editablesIte.add(tatri);
					}
				}
				editablesIte = ComponenteVO.ordenarPorNmordenFlot(editablesIte);
				
				GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				gc.setCdramo(cdramo);
				gc.setCdtipsit(cdtipsitIte);
				
				gc.generaComponentes(tatrisitIte, true, false, true, false, false, false);
				resp.getImap().put(Utils.join("tatrisit_full_items_",cdtipsitIte),gc.getItems());
				
				for(ComponenteVO comp:editablesIte)
				{
					comp.setLabelTop(true);
					comp.setWidth(150);
					comp.setComboVacio(true);
					comp.setObligatorio(comp.isObligatorioEmiFlot());
				}
				
				gc.generaComponentes(editablesIte, true, false, true, false, false, false);
				resp.getImap().put(Utils.join("tatrisit_parcial_items_",cdtipsitIte),gc.getItems());
			}
			
			setCheckpoint("Recuperando componentes de pantalla");
			List<ComponenteVO>polizaComp  = pantallasDAO.obtenerComponentes(null, null, null, null, null, null
					,"EMISION_AUTO_FLOT", "POLIZA", null);
			List<ComponenteVO>agenteComp  = pantallasDAO.obtenerComponentes(null, null, null, null, null, null
					,"EMISION_AUTO_FLOT", "AGENTE", null);
			List<ComponenteVO>gridColumns = pantallasDAO.obtenerComponentes(null, null, null, null, null, null
					,"EMISION_AUTO_FLOT", "COLUMNAS_RENDER", null);
			
			setCheckpoint("Generando componentes");
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.setCdramo(cdramo);
			gc.setCdtipsit(cdtipsit);
			
			gc.generaComponentes(tatripol, true, true, true, false, false, false);
			resp.getImap().put("polizaAdicionalesFields" , gc.getFields());
			resp.getImap().put("polizaAdicionalesItems"  , gc.getItems());
			
			gc.generaComponentes(gridColumns, true, false, false, true, false, false);
			resp.getImap().put("gridColumns" , gc.getColumns());
			
			gc.generaComponentes(polizaComp, true, true, true, false, false, false);
			resp.getImap().put("polizaFields" , gc.getFields());
			resp.getImap().put("polizaItems"  , gc.getItems());
			
			gc.generaComponentes(agenteComp, true, true, true, false, false, false);
			resp.getImap().put("agenteFields" , gc.getFields());
			resp.getImap().put("agenteItems"  , gc.getItems());
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ emisionAutoFlotilla @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaVoidVO guardarComplementariosAutoFlotilla(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String agenteSec
			,String porpartiSec
			,String feini
			,String fefin
			,Map<String,String>tvalopol
			,List<Map<String,String>>tvalosit
			,String ntramite
			)
	{
		logger.info(Utils.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarComplementariosAutoFlotilla @@@@@@"
				,"\n@@@@@@ cdunieco="    , cdunieco
				,"\n@@@@@@ cdramo="      , cdramo
				,"\n@@@@@@ estado="      , estado
				,"\n@@@@@@ nmpoliza="    , nmpoliza
				,"\n@@@@@@ agenteSec="   , agenteSec
				,"\n@@@@@@ porpartiSec=" , porpartiSec
				,"\n@@@@@@ feini="       , feini
				,"\n@@@@@@ fefin="       , fefin
				,"\n@@@@@@ tvalopol="    , tvalopol
				,"\n@@@@@@ tvalosit="    , tvalosit
				,"\n@@@@@@ ntramite="    , ntramite
				));
		
		ManagerRespuestaVoidVO resp = new ManagerRespuestaVoidVO(true);
		
		try
		{
			setCheckpoint("Actualizando poliza");
			cotizacionDAO.actualizaMpolizas(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,"0"  //nmsuplem
					,null //swestado
					,null //nmsolici
					,null //feautori
					,null //cdmotanu
					,null //feanulac
					,null //swautori
					,null //cdmoneda
					,null //feinisus
					,null //fefinsus
					,null //ottempot
					,renderFechas.parse(feini)
					,null //hhefecto
					,renderFechas.parse(fefin)
					,null //fevencim
					,null //nmrenova
					,null //ferecibo
					,null //feultsin
					,null //nmnumsin
					,null //cdtipcoa
					,null //swtarifi
					,null //swabrido
					,null //feemisio
					,null //cdperpag
					,null //nmpoliex
					,null //nmcuadro
					,null //porredau
					,null //swconsol
					,null //nmpolant
					,null //nmpolnva
					,null //fesolici
					,null //cdramant
					,null //cdmejred
					,null //nmpoldoc
					,null //nmpoliza2
					,null //nmrenove
					,null //nmsuplee
					,null //ttipcamc
					,null //ttipcamv
					,null //swpatent
					,null //nmpolmst
					,null //pcpgocte
					);
			
			if(!isBlank(agenteSec))
			{
				setCheckpoint("Recuperando agentes");
				List<Map<String,String>>agentes = consultasDAO.cargarMpoliage(cdunieco, cdramo, estado, nmpoliza);
				String cdagente = null;
				for(Map<String,String>agente:agentes)
				{
					if(agente.get("CDTIPOAG").equals("1"))
					{
						cdagente=agente.get("CDAGENTE");
					}
				}
				
				setCheckpoint("Desligando agentes anteriores");
				cotizacionDAO.borrarAgentesSecundarios(cdunieco, cdramo, estado, nmpoliza, "0");
				
				setCheckpoint("Recuperando cuadros de agentes");
				String nmcuadro    = cotizacionDAO.obtenerDatosAgente(cdagente  , cdramo).get("NMCUADRO");
    			String nmcuadroSec = cotizacionDAO.obtenerDatosAgente(agenteSec , cdramo).get("NMCUADRO");
				
				setCheckpoint("Guardando agente nuevo");
				cotizacionDAO.movimientoMpoliage(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,cdagente
						,"0"
						,"V"
						,"1"
						,"0"
						,nmcuadro
						,null
						,Constantes.INSERT_MODE
						,ntramite
						,String.format("%.2f",100d-Double.valueOf(porpartiSec))
						);
				
				if(Double.valueOf(porpartiSec)>0d)
				{
					cotizacionDAO.movimientoMpoliage(
							cdunieco
							,cdramo
							,estado
							,nmpoliza
							,agenteSec
							,"0"
							,"V"
							,"2"
							,"0"
							,nmcuadroSec
							,null
							,Constantes.INSERT_MODE
							,null//ntramite
							,porpartiSec
							);
				}
			}
				
			setCheckpoint("Guardando datos adicionales de poliza");
			Map<String,String>tvalopolAux=new HashMap<String,String>();
			for(Entry<String,String>en:tvalopol.entrySet())
			{
				String key = en.getKey();
				if(!isBlank(key)
						&&key.length()>"parametros.pv_otvalor".length()
						&&key.substring(0,"parametros.pv_otvalor".length()).equals("parametros.pv_otvalor"))
				{
					tvalopolAux.put(key.substring("parametros.pv_".length()),en.getValue());
				}
			}
			cotizacionDAO.movimientoTvalopol(cdunieco,cdramo,estado,nmpoliza,"0","V",tvalopolAux);
			
			setCheckpoint("Guardando datos adicionales de situacion");
			for(Map<String,String>tvalositIte:tvalosit)
			{
				Map<String,String>tvalositAux=new HashMap<String,String>();
				for(Entry<String,String>en:tvalositIte.entrySet())
				{
					String key = en.getKey();
					if(!isBlank(key)
							&&key.length()>"parametros.pv_otvalor".length()
							&&key.substring(0,"parametros.pv_otvalor".length()).equals("parametros.pv_otvalor"))
					{
						tvalositAux.put(key.substring("parametros.pv_".length()),en.getValue());
					}
				}
				cotizacionDAO.actualizaValoresSituacion(cdunieco,cdramo,estado,nmpoliza,"0",tvalositIte.get("nmsituac"),tvalositAux);
			}
			
			resp.setRespuesta("Datos guardados");
			resp.setRespuestaOculta("Datos guardados");
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(Utils.join(
				 "\n@@@@@@ " , resp
				,"\n@@@@@@ guardarComplementariosAutoIndividual @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	@Override
	public ManagerRespuestaSlistVO recotizarAutoFlotilla(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,boolean notarifica
			,String cdusuari
			,String cdelemen
			,String cdtipsit
			,String cdperpag
			)
	{
		logger.info(Utils.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recotizarAutoFlotilla @@@@@@"
				,"\n@@@@@@ cdunieco="   , cdunieco
				,"\n@@@@@@ cdramo="     , cdramo
				,"\n@@@@@@ estado="     , estado
				,"\n@@@@@@ nmpoliza="   , nmpoliza
				,"\n@@@@@@ nmsuplem="   , nmsuplem
				,"\n@@@@@@ notarifica=" , notarifica
				,"\n@@@@@@ cdusuari="   , cdusuari
				,"\n@@@@@@ cdelemen="   , cdelemen
				,"\n@@@@@@ cdtipsit="   , cdtipsit
				,"\n@@@@@@ cdperpag="   , cdperpag
				));

		ManagerRespuestaSlistVO resp = new ManagerRespuestaSlistVO(true);
		
		try
		{
			setCheckpoint("Validando datos obligatorios de PREVEX");
			//consultasDAO.validarDatosObligatoriosPrevex(cdunieco, cdramo, estado, nmpoliza);
			
			setCheckpoint("Validando datos de descuento por nomina");
			//consultasDAO.validarAtributosDXN(cdunieco, cdramo, estado, nmpoliza, nmsuplem);
			
			setCheckpoint("Tarificando");
			if(!notarifica)
			{
				cotizacionDAO.sigsvdefEnd(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,"0"    //nmsituac
						,"0"    //nmsuplem
						,"TODO" //cdgarant
						,"1"    //cdtipsup
						);
				
				cotizacionDAO.tarificaEmi(
						cdusuari
						,cdelemen
						,cdunieco
						,cdramo
						,estado
						,nmpoliza
						,"0" //nmsituac
						,"0" //nmsuplem
						,cdtipsit
						);
			}
			
			setCheckpoint("Ejecutando validaciones");
			String errores = consultasDAO.validacionesSuplemento(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem, "1");
			if(StringUtils.isNotBlank(errores))
			{
				throw new ApplicationException(errores);
			}
			
			resp.setSlist(cotizacionDAO.cargarDetallesCotizacionAutoFlotilla(cdunieco, cdramo, estado, nmpoliza, cdperpag));
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(Utils.join(
				 "\n@@@@@@ ",resp
				,"\n@@@@@@ recotizarAutoFlotilla @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO cargarObligatorioTractocamionRamo5(String clave)
	{
		logger.info(Utils.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ cargarObligatorioTractocamionRamo5 @@@@@@"
				,"\n@@@@@@ clave=",clave
				));
		
		ManagerRespuestaSmapVO resp = new ManagerRespuestaSmapVO(true);
		resp.setSmap(new LinkedHashMap<String,String>());
		
		try
		{
			setCheckpoint("Recuperando tipo de vehiculo");
			resp.getSmap().put("tipo",cotizacionDAO.cargarTipoVehiculoRamo5(clave));
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(Utils.join(
				 "\n@@@@@@ ",resp
				,"\n@@@@@@ cargarObligatorioTractocamionRamo5 @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}

	@Override
	public ManagerRespuestaSmapVO cargarDetalleNegocioRamo5(String negocio, String cdramo, String cdtipsit, String cdsisrol, String cdusuari)
	{
		logger.info(Utils.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ cargarDetalleNegocioRamo5 @@@@@@"
				,"\n@@@@@@ negocio=",negocio
				));
		
		ManagerRespuestaSmapVO resp = new ManagerRespuestaSmapVO(true);
		
		try
		{
			setCheckpoint("Recuperando detalle de negocio");
			
			resp.setSmap(cotizacionDAO.cargarDetalleNegocioRamo5(negocio, cdramo, cdtipsit, cdsisrol, cdusuari));
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(Utils.join(
				 "\n@@@@@@ cargarDetalleNegocioRamo5 @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	@Override
	public ManagerRespuestaVoidVO guardarPantallaBeneficiarios(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,List<Map<String,String>>mpoliperMpersona)
	{
		logger.info(Utils.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarPantallaBeneficiarios @@@@@@"
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				,"\n@@@@@@ nmsuplem=" , nmsuplem
				,"\n@@@@@@ nmsituac=" , nmsituac
				));
		
		ManagerRespuestaVoidVO resp=new ManagerRespuestaVoidVO(true);
		
		try
		{
			setCheckpoint("Iterando registros");
			for(Map<String,String>rec:mpoliperMpersona)
			{
				String mov    = rec.get("mov");
				int agregar   = 1;
				int eliminar  = 2;
				int operacion = 0;
				if(StringUtils.isNotBlank(mov))
				{
					if(mov.equals("+"))
					{
						operacion=agregar;
					}
					else if(mov.equals("-"))
					{
						operacion=eliminar;
					}
				}
				
				if(operacion==agregar)
				{
					personasDAO.movimientosMpersona(
							rec.get("CDPERSON")
							,rec.get("CDTIPIDE")
							,rec.get("CDIDEPER")
							,rec.get("DSNOMBRE")
							,rec.get("CDTIPPER")
							,rec.get("OTFISJUR")
							,rec.get("OTSEXO")
							,StringUtils.isNotBlank(rec.get("FENACIMI"))?
									renderFechas.parse(rec.get("FENACIMI"))
									:null
							,rec.get("CDRFC")
							,rec.get("DSEMAIL")
							,rec.get("DSNOMBRE1")
							,rec.get("DSAPELLIDO")
							,rec.get("DSAPELLIDO1")
							,new Date()
							,rec.get("CDNACION")
							,rec.get("CANALING")
							,rec.get("CONDUCTO")
							,rec.get("PTCUMUPR")
							,rec.get("RESIDENCIA")
							,rec.get("NONGRATA")
							,rec.get("CDIDEEXT")
							,rec.get("CDESTCIV")
							,rec.get("CDSUCEMI")
							,"I");
					
					endososDAO.movimientoMpoliperBeneficiario(
							cdunieco
							,cdramo
							,estado
							,nmpoliza
							,nmsituac
							,"3"
							,rec.get("CDPERSON")
							,nmsuplem
							,"V"
							,rec.get("NMORDDOM")
							,rec.get("SWRECLAM")
							,"N" //swexiper
							,rec.get("CDPARENT")
							,rec.get("PORBENEF")
							,"I"
							);
				}
				else if(operacion==eliminar)
				{
					endososDAO.movimientoMpoliperBeneficiario(
							cdunieco
							,cdramo
							,estado
							,nmpoliza
							,nmsituac
							,rec.get("CDROL")
							,rec.get("CDPERSON")
							,nmsuplem
							,rec.get("STATUS")
							,rec.get("NMORDDOM")
							,rec.get("SWRECLAM")
							,rec.get("SWEXIPER")
							,rec.get("CDPARENT")
							,rec.get("PORBENEF")
							,"B"
							);
					
					personasDAO.movimientosMpersona(
							rec.get("CDPERSON")
							,rec.get("CDTIPIDE")
							,rec.get("CDIDEPER")
							,rec.get("DSNOMBRE")
							,rec.get("CDTIPPER")
							,rec.get("OTFISJUR")
							,rec.get("OTSEXO")
							,StringUtils.isNotBlank(rec.get("FENACIMI"))?
									renderFechas.parse(rec.get("FENACIMI"))
									:null
							,rec.get("CDRFC")
							,rec.get("DSEMAIL")
							,rec.get("DSNOMBRE1")
							,rec.get("DSAPELLIDO")
							,rec.get("DSAPELLIDO1")
							,new Date()
							,rec.get("CDNACION")
							,rec.get("CANALING")
							,rec.get("CONDUCTO")
							,rec.get("PTCUMUPR")
							,rec.get("RESIDENCIA")
							,rec.get("NONGRATA")
							,rec.get("CDIDEEXT")
							,rec.get("CDESTCIV")
							,rec.get("CDSUCEMI")
							,"B");
				}
				else
				{
					endososDAO.movimientoMpoliperBeneficiario(
							cdunieco
							,cdramo
							,estado
							,nmpoliza
							,nmsituac
							,rec.get("CDROL")
							,rec.get("CDPERSON")
							,nmsuplem
							,rec.get("STATUS")
							,rec.get("NMORDDOM")
							,rec.get("SWRECLAM")
							,rec.get("SWEXIPER")
							,rec.get("CDPARENT")
							,rec.get("PORBENEF")
							,"U"
							);
					
					personasDAO.movimientosMpersona(
							rec.get("CDPERSON")
							,rec.get("CDTIPIDE")
							,rec.get("CDIDEPER")
							,rec.get("DSNOMBRE")
							,rec.get("CDTIPPER")
							,rec.get("OTFISJUR")
							,rec.get("OTSEXO")
							,StringUtils.isNotBlank(rec.get("FENACIMI"))?
									renderFechas.parse(rec.get("FENACIMI"))
									:null
							,rec.get("CDRFC")
							,rec.get("DSEMAIL")
							,rec.get("DSNOMBRE1")
							,rec.get("DSAPELLIDO")
							,rec.get("DSAPELLIDO1")
							,new Date()
							,rec.get("CDNACION")
							,rec.get("CANALING")
							,rec.get("CONDUCTO")
							,rec.get("PTCUMUPR")
							,rec.get("RESIDENCIA")
							,rec.get("NONGRATA")
							,rec.get("CDIDEEXT")
							,rec.get("CDESTCIV")
							,rec.get("CDSUCEMI")
							,"U");
				}
			}
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}	
		
		logger.info(Utils.join(
				 "\n@@@@@@ ",resp
				,"\n@@@@@@ guardarPantallaBeneficiarios @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	@Override
	public ManagerRespuestaSlistVO cargarParamerizacionConfiguracionCoberturasRol(
			String cdtipsit
			,String cdsisrol
			)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarParamerizacionConfiguracionCoberturasRol @@@@@@")
				.append("\n@@@@@@ cdtipsit=")    .append(cdtipsit)
				.append("\n@@@@@@ cdsisrol=")    .append(cdsisrol)
				.toString()
				);
		
		ManagerRespuestaSlistVO resp = new ManagerRespuestaSlistVO(true);
		
		try
		{
			setCheckpoint("Recuperando parametrizacion");
			List<List<Map<String,String>>>listas = cotizacionDAO.cargarParamerizacionConfiguracionCoberturasRol(cdtipsit,cdsisrol);
			
			List<Map<String,String>>ltatrisit = listas.get(0);
			List<Map<String,String>>latrixrol = listas.get(1);
			
			setCheckpoint("Inicializando atributos a procesar");
			Map<String,Map<String,String>>atributos = new LinkedHashMap<String,Map<String,String>>();
			for(Map<String,String>tatrisit:ltatrisit)
			{
				Map<String,String>mapa=new LinkedHashMap<String,String>();
				mapa.put("aplica" , "1");
				atributos.put(tatrisit.get("cdatribu"),mapa);
			}
			logger.debug(new StringBuilder("atributos=").append(atributos).toString());
			
			setCheckpoint("Procesando parametrizacion por rol");
			for(Map<String,String>atrixrol:latrixrol)
			{
				String cdatribu = atrixrol.get("cdatribu");
				String aplica   = atrixrol.get("aplica");
				String valor    = atrixrol.get("valor");
				if(atributos.containsKey(cdatribu)&&aplica.equals("0"))
				{
					atributos.get(cdatribu).put("aplica" , aplica);
					atributos.get(cdatribu).put("valor"  , valor);
				}
			}
			logger.debug(new StringBuilder("atributos=").append(atributos).toString());
			
			setCheckpoint("Transformando parametrizacion");
			List<Map<String,String>>lista = new ArrayList<Map<String,String>>();
			resp.setSlist(lista);
			
			for(Entry<String,Map<String,String>>atributo:atributos.entrySet())
			{
				Map<String,String>nuevo = new LinkedHashMap<String,String>();
				lista.add(nuevo);
				
				String cdatribu = atributo.getKey();
				nuevo.put("cdatribu" , cdatribu);
				
				Map<String,String>props = atributo.getValue();
				nuevo.put("aplica" , props.get("aplica"));
				nuevo.put("valor"  , props.get("valor"));
			}
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarParamerizacionConfiguracionCoberturasRol @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	/*
	 * Getters y setters
	 */
	public void setCotizacionDAO(CotizacionDAO cotizacionDAO) {
		this.cotizacionDAO = cotizacionDAO;
	}

	public void setPantallasDAO(PantallasDAO pantallasDAO) {
		this.pantallasDAO = pantallasDAO;
	}
	
	@Override
	public void setSession(Map<String,Object>session){
		logger.debug("setSession");
		this.session=session;
	}

	public void setConsultasDAO(ConsultasDAO consultasDAO) {
		this.consultasDAO = consultasDAO;
	}

	public void setPersonasDAO(PersonasDAO personasDAO) {
		this.personasDAO = personasDAO;
	}

	public void setMesaControlDAO(MesaControlDAO mesaControlDAO) {
		this.mesaControlDAO = mesaControlDAO;
	}

	public void setEndososDAO(EndososDAO endososDAO) {
		this.endososDAO = endososDAO;
	}
}