package mx.com.gseguros.portal.cotizacion.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaBaseVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.cotizacion.model.ParametroCotizacion;
import mx.com.gseguros.portal.cotizacion.service.CotizacionAutoManager;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.ws.autosgs.tractocamiones.service.TractoCamionService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

public class CotizacionAutoManagerImpl implements CotizacionAutoManager
{
	private static final Logger logger           = Logger.getLogger(CotizacionAutoManagerImpl.class);
	private static final DateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
	
	private static final String RECUPERAR_DESCUENTO_RECARGO_RAMO_5 = "RECUPERAR_DESCUENTO_RECARGO_RAMO_5";
	private static final String RECUPERAR_DATOS_VEHICULO_RAMO_5    = "RECUPERAR_DATOS_VEHICULO_RAMO_5"   ;
	
	private CotizacionDAO cotizacionDAO;
	private PantallasDAO  pantallasDAO;
	
	@Autowired
	private transient TractoCamionService tractoCamionService;
	
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
		
		if(ex.getClass().equals(ApplicationException.class))
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
					,"W"
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
			setCheckpoint("Recuperando suma asegurada");
			resp.setSmap(cotizacionDAO.cargarSumaAseguradaRamo5(cdtipsit,clave,modelo,cdsisrol));
			
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
			List<ComponenteVO>tatripol=cotizacionDAO.cargarTatripol(cdramo,cdtipsit);
			
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
	public Map<String,String>obtenerMapaProcedimientosSimples()
	{
		Map<String,String>procedimientos=new LinkedHashMap<String,String>();
		procedimientos.put(RECUPERAR_DESCUENTO_RECARGO_RAMO_5 , null);
		procedimientos.put(RECUPERAR_DATOS_VEHICULO_RAMO_5    , null);
		return procedimientos;
	}
	
	@Override
	public ManagerRespuestaSmapVO recuperacionSimple(
			String procedimiento
			,Map<String,String>parametros
			)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ recuperacionSimple @@@@@@")
				.append("\n@@@@@@ procedimiento=").append(procedimiento)
				.append("\n@@@@@@ parametros=")   .append(parametros)
				.toString()
				);
		
		ManagerRespuestaSmapVO resp=new ManagerRespuestaSmapVO(true);
		
		try
		{
			if(procedimiento.equals(RECUPERAR_DESCUENTO_RECARGO_RAMO_5))
			{
				setCheckpoint("Recuperando rango de descuento/recargo");
				String cdtipsit = parametros.get("cdtipsit");
				String cdagente = parametros.get("cdagente");
				String negocio  = parametros.get("negocio");
				resp.setSmap(cotizacionDAO.cargarRangoDescuentoRamo5(cdtipsit,cdagente,negocio));
			}
			else if(procedimiento.equals(RECUPERAR_DATOS_VEHICULO_RAMO_5))
			{
				setCheckpoint("Recuperando datos del vehiculo");
				String cdunieco = parametros.get("cdunieco");
				String cdramo   = parametros.get("cdramo");
				String estado   = parametros.get("estado");
				String nmpoliza = parametros.get("nmpoliza");
				resp.setSmap(cotizacionDAO.cargarDatosVehiculoRamo5(cdunieco,cdramo,estado,nmpoliza));
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
				.append("\n@@@@@@ recuperacionSimple @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
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
			List<ComponenteVO>panel2   = new ArrayList<ComponenteVO>();
			List<ComponenteVO>panel3   = new ArrayList<ComponenteVO>();
			List<ComponenteVO>panel5   = new ArrayList<ComponenteVO>();
			List<ComponenteVO>panel6   = new ArrayList<ComponenteVO>();
			List<ComponenteVO>gridCols = new ArrayList<ComponenteVO>();
			
			Map<String,String>mapPanel2 = new LinkedHashMap<String,String>();
			
			List<ComponenteVO>tatrisit = cotizacionDAO.cargarTatrisit(cdtipsit, cdusuari);
			
			setCheckpoint("Recuperando editor de planes");
			List<ComponenteVO>auxEditorPlan = pantallasDAO.obtenerComponentes(
					TipoTramite.POLIZA_NUEVA.getCdtiptra(), null, cdramo
					, cdtipsit, null, cdsisrol
					, "COTIZACION_FLOTILLA", "EDITOR_PLANES", null);
			ComponenteVO editorPlan = auxEditorPlan.get(0);
			
			setCheckpoint("Recuperando editor de situacion");
			List<ComponenteVO>auxEditorSit = pantallasDAO.obtenerComponentes(
					TipoTramite.POLIZA_NUEVA.getCdtiptra(), null, cdramo
					, cdtipsit, null, cdsisrol
					, "COTIZACION_FLOTILLA", "EDITOR_SITUACION", null);
			ComponenteVO editorSit = auxEditorSit.get(0);
			
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
					,"W"
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
			gridCols.add(editorSit);
			for(ComponenteVO tatri:tatrisit)
			{
				if(tatri.getColumna().equals("S")
						&&tatri.getSwpresenflot().equals("S")
						)
				{
					if(tatri.getCotflotrol().equals("*")
							|| tatri.getCotflotrol().lastIndexOf(new StringBuilder("|").append(cdsisrol).append("|").toString())!=-1)
					{
						gridCols.add(tatri);
					}
				}
				
				if(tatri.getNmpanelflot().equals("1"))
				{
					panel1.add(tatri);
				}
				else if(tatri.getNmpanelflot().equals("2"))
				{
					panel2.add(tatri);
					mapPanel2.put(tatri.getNameCdatribu(),null);
				}
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
			gridCols.add(editorPlan);
			
			setCheckpoint("Construyendo componentes");
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.setCdramo(cdramo);
			gc.setCdtipsit(cdtipsit);
			
			gc.generaComponentes(panel1, true, false, true, false, false, false);
			resp.getImap().put("panel1Items"  , gc.getItems());
			
			gc.generaComponentes(panel2, true, false, true, false, false, false);
			resp.getImap().put("panel2Items"  , gc.getItems());
			
			gc.generaComponentes(panel3, true, false, true, false, false, false);
			resp.getImap().put("panel3Items"  , gc.getItems());
			
			gc.generaComponentes(panel5, true, false, true, false, false, false);
			resp.getImap().put("panel5Items"  , gc.getItems());
			
			gc.generaComponentes(panel6, true, false, true, false, false, false);
			resp.getImap().put("panel6Items"  , gc.getItems());

			for(ComponenteVO tatri:gridCols)
			{
				if(StringUtils.isNotBlank(tatri.getNmpanelflot())&&tatri.getNmpanelflot().equals("2"))
				{
					tatri.setSoloLectura(true);
					tatri.setObligatorio(false);
				}
			}
			gc.generaComponentes(gridCols, true, false, true, true, true, false);
			resp.getImap().put("gridCols" , gc.getColumns());
			
			setCheckpoint("Recuperando agrupacion de situaciones");
			Map<String,String>agrupAux = cotizacionDAO.obtenerParametrosCotizacion(
					ParametroCotizacion.FLOTILLA_AGRUPACION_SITUACION, cdramo, cdtipsit, null, null);
			
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
				
				setCheckpoint(new StringBuilder("Construyendo panel dinamico ").append(situacionPanel).toString());
				GeneradorCampos gcPanel = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				gcPanel.setCdramo(cdramo);
				gcPanel.setCdtipsit(situacionPanel);
				gcPanel.generaComponentes(tatrisitPanel, true, false, true, false, false, false);
				logger.debug(new StringBuilder("\nelementos del panel=").append(gcPanel.getItems()).toString());
				resp.getImap().put(new StringBuilder("paneldin_").append(situacionPanel).toString(),gcPanel.getItems());
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
	public ManagerRespuestaVoidVO cotizarAutosFlotilla(
			String cdusuari
			,String cdsisrol
			,String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,Date fechaInicio
			,Date fechaFin
			,String cdagente
			,String cdpersonCli
			,String cdideperCli
			,List<Map<String,String>> tvalosit
			,List<Map<String,String>> baseTvalosit
			,List<Map<String,String>> configPlanes)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cotizarAutosFlotilla @@@@@@")
				.append("\n@@@@@@ cdusuari=")    .append(cdusuari)
				.append("\n@@@@@@ cdsisrol=")    .append(cdsisrol)
				.append("\n@@@@@@ cdunieco=")    .append(cdunieco)
				.append("\n@@@@@@ cdramo=")      .append(cdramo)
				.append("\n@@@@@@ cdtipsit=")    .append(cdtipsit)
				.append("\n@@@@@@ estado=")      .append(estado)
				.append("\n@@@@@@ nmpoliza=")    .append(nmpoliza)
				.append("\n@@@@@@ fechaInicio=") .append(fechaInicio)
				.append("\n@@@@@@ fechaFin=")    .append(fechaFin)
				.append("\n@@@@@@ cdagente=")    .append(cdagente)
				.append("\n@@@@@@ cdpersonCli=") .append(cdpersonCli)
				.append("\n@@@@@@ cdideperCli=") .append(cdideperCli)
				.append("\n@@@@@@ tvalosit=")    .append(tvalosit)
				.append("\n@@@@@@ baseTvalosit=").append(baseTvalosit)
				.append("\n@@@@@@ configPlanes=").append(configPlanes)
				.toString()
				);
		
		ManagerRespuestaVoidVO resp=new ManagerRespuestaVoidVO(true);
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cotizarAutosFlotilla @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
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
}