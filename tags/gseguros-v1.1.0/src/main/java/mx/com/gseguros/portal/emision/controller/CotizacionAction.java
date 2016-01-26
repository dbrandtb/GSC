package mx.com.gseguros.portal.emision.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
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

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.externo.service.StoredProceduresManager;
import mx.com.gseguros.mesacontrol.model.FlujoVO;
import mx.com.gseguros.mesacontrol.service.FlujoMesaControlManager;
import mx.com.gseguros.portal.consultas.model.RecuperacionSimple;
import mx.com.gseguros.portal.consultas.service.ConsultasManager;
import mx.com.gseguros.portal.consultas.service.RecuperacionSimpleManager;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.cotizacion.model.ParametroCotizacion;
import mx.com.gseguros.portal.cotizacion.service.CotizacionManager;
import mx.com.gseguros.portal.documentos.service.DocumentosManager;
import mx.com.gseguros.portal.emision.service.EmisionManager;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.model.Reporte;
import mx.com.gseguros.portal.general.service.CatalogosManager;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.service.ReportesManager;
import mx.com.gseguros.portal.general.service.ServiciosManager;
import mx.com.gseguros.portal.general.util.EstatusTramite;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.ObjetoBD;
import mx.com.gseguros.portal.general.util.Ramo;
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.portal.general.util.TipoArchivo;
import mx.com.gseguros.portal.general.util.TipoEndoso;
import mx.com.gseguros.portal.general.util.TipoSituacion;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.mesacontrol.service.MesaControlManager;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.FTPSUtils;
import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.utils.Utils;
import mx.com.gseguros.ws.folioserviciopublico.client.axis2.FolioWSServiceStub.EmAdmfolId;
import mx.com.gseguros.ws.folioserviciopublico.service.AgentePorFolioService;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteGeneral;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteGeneralRespuesta;
import mx.com.gseguros.ws.ice2sigs.service.Ice2sigsService;
import mx.com.gseguros.ws.nada.client.axis2.VehicleStub.VehicleValue_Struc;
import mx.com.gseguros.ws.nada.service.NadaService;
import mx.com.gseguros.ws.tipocambio.client.axis2.TipoCambioWSServiceStub.ResponseTipoCambio;
import mx.com.gseguros.ws.tipocambio.service.TipoCambioDolarGSService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

public class CotizacionAction extends PrincipalCoreAction
{

	private static final long       serialVersionUID = 3237792502541753915L;
	private static final Logger     logger           = LoggerFactory.getLogger(CotizacionAction.class);
	private static SimpleDateFormat renderFechas     = new SimpleDateFormat("dd/MM/yyyy"); 
	private static SimpleDateFormat renderHora       = new SimpleDateFormat  ("HH:mm");
	
	private transient CatalogosManager       catalogosManager;
	private ConsultasManager                 consultasManager;
	private String                           error;
	private Map<String,Item>                 imap;
	private transient KernelManagerSustituto kernelManager;
	private PantallasManager                 pantallasManager;
	private List<Map<String,String>>         slist1;
	private List<Map<String,String>>         slist2;
	private Map<String,String>               smap1;
	private Map<String,String>               params;
	private StoredProceduresManager          storedProceduresManager;
	private NadaService          			 nadaService;
	private TipoCambioDolarGSService         tipoCambioService;
	private transient Ice2sigsService        ice2sigsService;
	private AgentePorFolioService            agentePorFolioService;
	private boolean                          success;
	private String                           respuesta;
	private String                           respuestaOculta = null;
	private boolean                          exito           = false;
	private File                             censo;
	private String                           censoFileName;
	private String                           censoContentType;
	private List<Map<String,Object>>         olist1;
	private CotizacionManager                cotizacionManager;
	private SiniestrosManager                siniestrosManager;
	private FlujoVO                          flujo;
	
	@Autowired
	private EmisionManager emisionManager;
	
	@Autowired
	private ServiciosManager serviciosManager;
	
	@Autowired
	private ReportesManager reportesManager;
	
	@Autowired
	private DocumentosManager documentosManager;
	
	@Autowired
	private MesaControlManager mesaControlManager;
	
	@Autowired
	private FlujoMesaControlManager flujoMesaControlManager;
	
	@Autowired
	private RecuperacionSimpleManager recuperacionSimpleManager;
	
	public CotizacionAction()
	{
		logger.debug("new CotizacionAction");
		this.session=ActionContext.getContext().getSession();
	}
	
	/**
	 * Guarda el estado actual en sesion
	 * @param checkpoint
	 */
	private void setCheckpoint(String checkpoint)
	{
		logger.debug(new StringBuilder("checkpoint-->").append(checkpoint).toString());
		session.put("checkpoint",checkpoint);
	}
	
	/**
	 * Obtiene el estado actual de sesion
	 * @return checkpoint
	 */
	private String getCheckpoint()
	{
		return (String)session.get("checkpoint");
	}
	
	/**
	 * Da valor a las variables exito, respuesta y respuestaOculta.
	 * Tambien guarda el checkpoint en 0
	 * @param ex
	 */
	private void manejaException(Exception ex)
	{
		long timestamp  = System.currentTimeMillis();
		exito           = false;
		respuestaOculta = ex.getMessage();
		
		if(ex instanceof ApplicationException)
		{
			respuesta = new StringBuilder(ex.getMessage()).append(" #").append(timestamp).toString();
		}
		else
		{
			respuesta = new StringBuilder("Error ").append(getCheckpoint().toLowerCase()).append(" #").append(timestamp).toString();
		}
		
		logger.error(respuesta,ex);
		setCheckpoint("0");
	}
	
	/**
	 * Revisa cadena vacia y arroja ApplicationException
	 */
	private void checkBlank(String cadena,String mensaje)throws ApplicationException
	{
		if(StringUtils.isBlank(cadena))
		{
			throw new ApplicationException(mensaje);
		}
	}
	
	/**
	 * Revisa nulo y arroja ApplicationException
	 */
	private void checkNull(Object objeto,String mensaje)throws ApplicationException
	{
		if(objeto==null)
		{
			throw new ApplicationException(mensaje);
		}
	}
	
	/**
	 * Revisa boolean y arroja ApplicationException
	 */
	private void checkBool(boolean bool,String mensaje)throws ApplicationException
	{
		if(bool==false)
		{
			throw new ApplicationException(mensaje);
		}
	}
	
	/**
	 * Revisa null y lista vacia
	 */
	private void checkList(List<?> lista,String mensaje)throws ApplicationException
	{
		checkNull(lista,mensaje);
		if(lista.size()==0)
		{
			throw new ApplicationException(mensaje);
		}
	}
	
	/////////////////////////////////
	////// cotizacion dinamica //////
	/*/////////////////////////////*/
	public String pantallaCotizacion()
	{
		this.session=ActionContext.getContext().getSession();
		logger.info("\n"
				+ "\n################################"
				+ "\n###### pantallaCotizacion ######"
				+ "\n###### smap1: "+smap1
				+ "\n###### flujo: "+flujo
				+ "\n###### session!=null: "+(session!=null)
				);
		
		success = true;
		exito   = true;
		
		UserVO usuario     = null;
		String cdunieco    = null;
		String cdramo      = null;
		String cdtipsit    = null;
		String situacion   = null;
		String agrupacion  = null;
		String cdagente    = null;
		GeneradorCampos gc = null;
		String cdusuari    = null;
		String cdsisrol    = null;
		
		if(flujo!=null)
		{
			try
			{
				smap1 = new HashMap<String,String>();
				smap1.put("ntramite" , flujo.getNtramite());
				smap1.put("cdunieco" , flujo.getCdunieco());
				smap1.put("cdramo"   , flujo.getCdramo());
				smap1.put("cdtipsit" ,
						((Map<String,String>)(flujoMesaControlManager.recuperarDatosTramiteValidacionCliente(new StringBuilder(), flujo))
						    .get("TRAMITE"))
						    .get("CDTIPSIT")
						);
				logger.debug(Utils.log("\nsmap1 recuperado de flujo=",smap1));
			}
			catch(Exception ex)
			{
				success = false;
				exito   = false;
				logger.error("Error al recuperar datos de flujo",ex);
				return ERROR;
			}
		}
		
		//instanciar el generador de campos
    	gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
		gc.setEsMovil(session!=null&&session.containsKey("ES_MOVIL")&&((Boolean)session.get("ES_MOVIL"))==true);
		if(!gc.isEsMovil() && smap1.containsKey("movil"))
		{
			gc.setEsMovil(true);
		}
		
		//datos de entrada
		if(exito)
		{
		    try
		    {
		    	usuario  = (UserVO) session.get("USUARIO");
		    	cdusuari = usuario.getUser();
		    	cdsisrol = usuario.getRolActivo().getClave();
		        cdramo   = smap1.get("cdramo");
		        cdtipsit = smap1.get("cdtipsit");
		        
		        smap1.put("cdusuari" , cdusuari);
		        smap1.put("cdsisrol" , cdsisrol);
				gc.setCdtipsit(cdtipsit);
		    }
		    catch(Exception ex)
		    {
		    	long timestamp  = System.currentTimeMillis();
		    	exito           = false;
		    	respuesta       = "No hay datos suficientes #"+timestamp;
		    	respuestaOculta = ex.getMessage();
		    	logger.error(respuesta,ex);
		    }
		}
		
		//poner valores a ntramite, cdunieco y cdramo
		if(exito)
		{
			try
			{
				//cuando viene ntramite tambien vienen cdunieco y cdramo 
		        if(StringUtils.isNotBlank(smap1.get("ntramite")))
		        {
		        	cdunieco = smap1.get("cdunieco");
		        }
		        //cuando no hay ntramite es porque esta cotizando un agente por fuera,
		        //y se obtiene cdunieco por medio de ese agente
		        else
		        {
	        		DatosUsuario datUsu=kernelManager.obtenerDatosUsuario(usuario.getUser(),cdtipsit);
	        		cdunieco=datUsu.getCdunieco();
	        		smap1.put("ntramite" , "");
	        		smap1.put("cdunieco" , cdunieco);
	        		
	        		//recuperamos agente de ser el que esta en sesion
	        		if(cdsisrol.equals(RolSistema.AGENTE.getCdsisrol()))
	        		{
	        			cdagente = datUsu.getCdagente();
	        		}
	        		smap1.put("cdagente" , cdagente);
		        }
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Usted no puede cotizar este producto #"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
        
        //obtener tipo situacion
		if(exito)
		{
	        try
	        {
	            Map<String,String>tipoSituacion=cotizacionManager.cargarTipoSituacion(cdramo,cdtipsit);
	            if(tipoSituacion!=null)
	            {
	            	smap1.putAll(tipoSituacion);
	            }
	            else
	            {
	            	throw new Exception("No se ha parametrizado la situacion en ttipram");
	            }
	        }
	        catch(Exception ex)
	        {
	        	long timestamp  = System.currentTimeMillis();
	        	respuesta       = "Error al cargar tipo de situacion #"+timestamp;
	        	respuestaOculta = ex.getMessage();
	        	logger.error(respuesta,ex);
	        	
	        	this.addActionError("No se ha parametrizado el tipo de situación para el producto #"+timestamp);
	        	smap1.put("SITUACION"  , "PERSONA");
	        	smap1.put("AGRUPACION" , "SOLO");
	        }
	        finally
	        {
	        	situacion  = smap1.get("SITUACION");
	        	agrupacion = smap1.get("AGRUPACION");
	        }
		}
		
		// obtener campos de tatrisit
        if(exito)
        {	
	        List<ComponenteVO>camposAgrupados    = new ArrayList<ComponenteVO>(0);
	        List<ComponenteVO>camposIndividuales = new ArrayList<ComponenteVO>(0);
	        
	        imap  = new HashMap<String,Item>();
	        
	        try
	        {
		        List<ComponenteVO>tatrisit = kernelManager.obtenerTatrisit(cdtipsit,usuario.getUser());
		        List<ComponenteVO>temp     = new ArrayList<ComponenteVO>();
		        
		        //iteracion para descartar componentes
		        for(ComponenteVO tatriIte:tatrisit)
				{
		        	//para permitir edicion cuando no tienen valor
		        	if(tatriIte.getValue()==null&&tatriIte.getDefaultValue()==null)
		        	{
		        		tatriIte.setComboVacio(true);
		        	}
		        	
		        	//para presentar en pantalla
		        	if(tatriIte.getSwpresen().equalsIgnoreCase("S"))
		        	{
		        		temp.add(tatriIte);
		        	}
		        	//cuanto no van en pantalla
		        	else
		        	{
		        		//[parche] para poner para AF y PU
		        		if(cdtipsit.equalsIgnoreCase(TipoSituacion.AUTOS_FRONTERIZOS.getCdtipsit())||
		        				cdtipsit.equalsIgnoreCase(TipoSituacion.AUTOS_PICK_UP.getCdtipsit()))
		        		{
		        			if(tatriIte.getNameCdatribu().equalsIgnoreCase("26"))
		        			{
		        				tatriIte.setOculto(true);
		        				temp.add(tatriIte);
		        			}
		        		}
		        	}
		        	
		        	//[parche] para AF y PU
		        	if(cdtipsit.equalsIgnoreCase(TipoSituacion.AUTOS_FRONTERIZOS.getCdtipsit())
		        			||cdtipsit.equalsIgnoreCase(TipoSituacion.AUTOS_PICK_UP.getCdtipsit()))
		        	{
		        		if(tatriIte.getNameCdatribu().equalsIgnoreCase("24"))
		        		{
			        		ResponseTipoCambio rtc=tipoCambioService.obtieneTipoCambioDolarGS(2);
			        		if(rtc!=null&&rtc.getTipoCambio()!=null&&rtc.getTipoCambio().getVenCam()!=null)
			        		{
			        			tatriIte.setOculto(true);
			        			tatriIte.setValue(rtc.getTipoCambio().getVenCam().doubleValue()+"");
			        		}
		        		}
		        	}
					//[parche] para ramo 6
					else if(cdramo.equals(Ramo.SERVICIO_PUBLICO.getCdramo()))
					{
						//agente
						if(tatriIte.getNameCdatribu().equalsIgnoreCase("17"))
						{
							//valor inicial si es agente
							if(cdsisrol.equals(RolSistema.AGENTE.getCdsisrol())
									&&StringUtils.isNotBlank(cdagente))
							{
								tatriIte.setValue(cdagente);
								tatriIte.setSoloLectura(true);
								logger.debug(
										new StringBuilder()
										.append("\n@@@@@@ parche pone cdagente=")
										.append(cdagente)
										.append(" @@@@@@")
										.toString()
										);
							}
							//sustituir componente si es promotor o suscriptor
							else if(cdsisrol.equalsIgnoreCase(RolSistema.PROMOTOR_AUTO.getCdsisrol())
									||cdsisrol.equalsIgnoreCase(RolSistema.SUSCRIPTOR_AUTO.getCdsisrol()))
							{
								List<ComponenteVO>componenteSustitutoListaAux=pantallasManager.obtenerComponentes(
										TipoTramite.POLIZA_NUEVA.getCdtiptra() , null         , cdramo
										,cdtipsit                              ,  "W"         , cdsisrol
										,"COTIZACION_CUSTOM"                   , "SUSTITUTOS" , "17");
								temp.remove(tatriIte);
								componenteSustitutoListaAux.get(0).setSwsuscri("N");
								temp.add(componenteSustitutoListaAux.get(0));
							}
						}
						//clave gs
						else if(tatriIte.getNameCdatribu().equalsIgnoreCase("22"))
						{
							if(cdtipsit.equals(TipoSituacion.SERVICIO_PUBLICO_AUTO.getCdtipsit()))
							{
								temp.remove(tatriIte);
								//agregar combo
								List<ComponenteVO>listaAuxComboAutos=pantallasManager.obtenerComponentes(
										TipoTramite.POLIZA_NUEVA.getCdtiptra()
										, null                   
										,cdramo
										,cdtipsit
										,"W"
										,cdsisrol
										,"COTIZACION_CUSTOM"
										,"COMBO_SERV_PUBL_AUTO"
										,null);
								listaAuxComboAutos.get(0).setSwsuscri("N");
								temp.add(listaAuxComboAutos.get(0));
							}
						}
						//descuento
						else if(tatriIte.getNameCdatribu().equalsIgnoreCase("21"))
						{
							List<ComponenteVO>componenteSustitutoListaAux=pantallasManager.obtenerComponentes(
									TipoTramite.POLIZA_NUEVA.getCdtiptra() , null         , cdramo
									,cdtipsit                              ,  "W"         , cdsisrol
									,"COTIZACION_CUSTOM"                   , "SUSTITUTOS" , "21");
							temp.remove(tatriIte);
							componenteSustitutoListaAux.get(0).setSwsuscri("N");
							temp.add(componenteSustitutoListaAux.get(0));
						}
					}
		        	
		        	//SERV PUBL MICRO
					if(cdtipsit.equalsIgnoreCase(TipoSituacion.SERVICIO_PUBLICO_MICRO.getCdtipsit()))
					{
						//NEGOCIO
						if(tatriIte.getNameCdatribu().equalsIgnoreCase("30"))
						{
							List<ComponenteVO>componenteSustitutoListaAux=pantallasManager.obtenerComponentes(
									TipoTramite.POLIZA_NUEVA.getCdtiptra() , null         , cdramo
									,cdtipsit                              ,  "W"         , cdsisrol
									,"COTIZACION_CUSTOM"                   , "SUSTITUTOS" , "30");
							temp.remove(tatriIte);
							componenteSustitutoListaAux.get(0).setSwsuscri("N");
							temp.add(componenteSustitutoListaAux.get(0));
						}
					}
		        	//SERV PUBL AUTO
					else if(cdtipsit.equalsIgnoreCase(TipoSituacion.SERVICIO_PUBLICO_AUTO.getCdtipsit()))
					{
						//NEGOCIO
						if(tatriIte.getNameCdatribu().equalsIgnoreCase("31"))
						{
							List<ComponenteVO> componenteSustitutoListaAux = pantallasManager.obtenerComponentes(
									TipoTramite.POLIZA_NUEVA.getCdtiptra() , null         , cdramo
									,cdtipsit                              ,  "W"         , cdsisrol
									,"COTIZACION_CUSTOM"                   , "SUSTITUTOS" , "31");
							temp.remove(tatriIte);
							componenteSustitutoListaAux.get(0).setSwsuscri("N");
							temp.add(componenteSustitutoListaAux.get(0));
						}
					}
					
					//[parche] para fronterizos
					if(cdramo.equals(Ramo.AUTOS_FRONTERIZOS.getCdramo()))
					{
						logger.debug("tatriIte=" + tatriIte);
						//agente
						if(tatriIte.getNameCdatribu().equalsIgnoreCase("32"))
						{
							logger.debug("tatriIte==" + tatriIte);
							//valor inicial si es agente
							if(cdsisrol.equals(RolSistema.AGENTE.getCdsisrol())
									&&StringUtils.isNotBlank(cdagente))
							{
								tatriIte.setValue(cdagente);
								tatriIte.setSoloLectura(true);
								logger.debug(
										new StringBuilder()
										.append("\n@@@@@@ parche pone cdagente=")
										.append(cdagente)
										.append(" @@@@@@")
										.toString()
										);
							}
							//sustituir componente si es promotor o suscriptor
							else if(cdsisrol.equalsIgnoreCase(RolSistema.PROMOTOR_AUTO.getCdsisrol())
									||cdsisrol.equalsIgnoreCase(RolSistema.SUSCRIPTOR_AUTO.getCdsisrol()))
							{
								List<ComponenteVO>componenteSustitutoListaAux=pantallasManager.obtenerComponentes(
										TipoTramite.POLIZA_NUEVA.getCdtiptra() , null         , cdramo
										,cdtipsit                              ,  "W"         , cdsisrol
										,"COTIZACION_CUSTOM"                   , "SUSTITUTOS" , "32");
								temp.remove(tatriIte);
								componenteSustitutoListaAux.get(0).setSwsuscri("N");
								temp.add(componenteSustitutoListaAux.get(0));
							}
						}
					}
	        		
					//[parche] para ramo 16 y sucriptor auto, sustituir campo Numero de serie:
					if(cdramo.equals(Ramo.AUTOS_FRONTERIZOS.getCdramo())) {
						if(tatriIte.getNameCdatribu().equalsIgnoreCase("3")) {
							List<ComponenteVO> componenteSustitutoListaAux = pantallasManager.obtenerComponentes(
									TipoTramite.POLIZA_NUEVA.getCdtiptra() , null         , cdramo
									,cdtipsit                              ,  "W"         , cdsisrol
									,"COTIZACION_CUSTOM"                   , "SUSTITUTOS" , "3");
							// Si se encuentra el componente lo reemplazamos:
							if(componenteSustitutoListaAux != null && componenteSustitutoListaAux.size() > 0) {
								temp.remove(tatriIte);
								componenteSustitutoListaAux.get(0).setSwsuscri("N");
								temp.add(componenteSustitutoListaAux.get(0));
							}
						}
					}
				}
		        tatrisit=temp;
		        
		        //segunda ronda para separar los individuales de los agrupados
				for(ComponenteVO tatriIte:tatrisit)
				{
					if(tatriIte.getSwsuscri().equalsIgnoreCase("S"))//S=individual
					{
						tatriIte.setColumna(Constantes.SI);
						camposIndividuales.add(tatriIte);
					}
					else
					{
						camposAgrupados.add(tatriIte);
					}
					
					//[parche] para SL y SN
					if(cdtipsit.equalsIgnoreCase("SL")||cdtipsit.equalsIgnoreCase("SN"))
					{
						if(tatriIte.getCatalogo()!=null&&
								(tatriIte.getCatalogo().equalsIgnoreCase("2CODPOS")
										||tatriIte.getCatalogo().equalsIgnoreCase("2CODPOSN")))//codigo postal
						{
							tatriIte.setCatalogo("");
						}
					}
				}
	
				gc.generaComponentes(camposAgrupados,true,true,true,false,false,false);
				imap.put("fieldsAgrupados" , gc.getFields());
				imap.put("camposAgrupados" , gc.getItems());
				
				if(camposIndividuales.size()>0)
				{
					gc.generaComponentes(camposIndividuales,true,true,true,true,true,false);
					imap.put("camposIndividuales" , gc.getColumns());
					imap.put("fieldsIndividuales" , gc.getFields());
					imap.put("itemsIndividuales"  , gc.getItems());
				}
				else
				{
					imap.put("camposIndividuales" , null);
					imap.put("fieldsIndividuales" , null);
					imap.put("itemsIndividuales"  , null);
				}
				
				logger.debug("camposAgrupados: "+camposAgrupados);
				logger.debug("camposIndividuales: "+camposIndividuales);
	        }
	        catch(Exception ex)
	        {
	        	long timestamp  = System.currentTimeMillis();
	        	exito           = false;
	        	respuesta       = "Error al obtener componentes #"+timestamp;
	        	respuestaOculta = ex.getMessage();
	        	logger.error(respuesta,ex);
	        }
        }
        
        //obtener validaciones de situaciones
        if(exito)
        {
        	try
        	{
        		List<ComponenteVO>validaciones=pantallasManager.obtenerComponentes(
						null, null, cdramo, cdtipsit, null, null, "VALIDACIONES_COTIZA", gc.isEsMovil()?"MOVIL":"DESKTOP", null);
				if(validaciones.size()>0)
				{
					gc.generaComponentes(validaciones, true, false, false, false, false, true);
					imap.put("validacionCustomButton" , gc.getButtons());
				}
				else
				{
					throw new Exception(
							new StringBuilder()
							.append("No se han definido las validaciones en VALIDACIONES_COTIZA>")
							.append(gc.isEsMovil()?"MOVIL":"DESKTOP")
							.toString()
							);
				}
        	}
        	catch(Exception ex)
        	{
        		long timestamp  = System.currentTimeMillis();
        		respuesta       = "Error al cargar las validaciones para el producto #"+timestamp;
        		respuestaOculta = ex.getMessage();
        		logger.error(respuesta,ex);
        		
        		this.addActionError("No se han definido las validaciones para el producto #"+timestamp);
        		imap.put("validacionCustomButton" , null);
        	}
        }
        
        //obtener atributos extras de situacion
        if(exito)
        {
        	try
        	{
        		List<ComponenteVO>modeloExtra = pantallasManager.obtenerComponentes(
						null, null, cdramo, cdtipsit, null, null, "VALIDACIONES_COTIZA", "MODELO", null);
				gc.generaComponentes(modeloExtra, true, true, true, true, true, false);
				if(modeloExtra.size()>0)
				{
					imap.put("modeloExtraFields"  , gc.getFields());
					imap.put("modeloExtraColumns" , gc.getColumns());
					imap.put("modeloExtraItems"   , gc.getItems());
				}
				else
				{
					imap.put("modeloExtraFields"  , null);
					imap.put("modeloExtraColumns" , null);
					imap.put("modeloExtraItems"   , null);
				}
	        }
	        catch(Exception ex)
	        {
	        	long timestamp  = System.currentTimeMillis();
	        	exito           = false;
	        	respuesta       = "Error al obtener los atributos extras de la situaci&oacute;n #"+timestamp;
	        	respuestaOculta = ex.getMessage();
	        	logger.error(respuesta,ex);
	        }
        }
        
        //atributos derechos para auto
		if(exito&&situacion.equals("AUTO"))
		{
			try
			{
				List<ComponenteVO>cdatribusDerechos=pantallasManager.obtenerComponentes(
						null, null, cdramo, cdtipsit, null, null, "COTIZACION_CUSTOM", "CDATRIBU_DERECHO", null);
				if(cdatribusDerechos.size()>0)
				{
					String cdatribusConcatenados="";
					for(int i=0;i<cdatribusDerechos.size();i++)
					{
						cdatribusConcatenados=cdatribusConcatenados+cdatribusDerechos.get(i).getNameCdatribu();
						if(i<cdatribusDerechos.size()-1)
						{
							cdatribusConcatenados=cdatribusConcatenados+",";
						}
					}
					smap1.put("CDATRIBU_DERECHO",cdatribusConcatenados);
				}
				else
				{
					throw new Exception("No se han definido atributos en COTIZACION_CUSTOM>CDATRIBU_DERECHO");
				}
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				respuesta       = "Error al cargar los CDATRIBU_DERECHO #"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
				
				this.addActionError("No se han definido los atributos de coberturas para el producto #"+timestamp);
				smap1.put("CDATRIBU_DERECHO",null);
			}
		}
		
		//respuesta
		String respuesta = null;
		if(exito)
		{
			if(gc.isEsMovil())
			{
				respuesta = "success_mobile";
			}
			else
			{
				respuesta = SUCCESS;
			}
		}
		else
		{
			if(gc.isEsMovil())
			{
				respuesta = "error_mobile";
			}
			else
			{
				respuesta = ERROR;
			}
		}
		
		logger.info(""
				+ "\nrespuesta: "+respuesta
				+ "\n###### pantallaCotizacion ######"
				+ "\n################################"
				);
		
		return respuesta;
	}
	
	public String webServiceNada()
	{
		logger.info(""
				+ "\n############################"
				+ "\n###### webServiceNada ######"
				);
		logger.info("smap1: "+smap1);
		
		String  vim                  = null;
		success                      = true;
		VehicleValue_Struc datosAuto = null;
		String cdramo                = null;
		String cdtipsit              = null;
		String tipoVehiculo          = null;
		String codigoPostal          = null;
		
		//revisar numero de serie
		if(success)
		{
			
			success = smap1!=null&&StringUtils.isNotBlank(vim=smap1.get("vim"))
					&&StringUtils.isNotBlank(cdramo=smap1.get("cdramo"))
					&&StringUtils.isNotBlank(cdtipsit=smap1.get("cdtipsit"))
					&&StringUtils.isNotBlank(tipoVehiculo=smap1.get("tipoveh"))
					&&StringUtils.isNotBlank(codigoPostal=smap1.get("codpos"))
					;
			if(!success)
			{
				error="No se recibi&oacute; el n&uacute;mero de serie";
				logger.error(error);
			}
		}
		
		//obtener factor convenido
		if(success)
		{
			try
			{
				LinkedHashMap<String,Object>params=new LinkedHashMap<String,Object>();
				params.put("param1",cdramo);
				params.put("param2",cdtipsit);
				Map<String,String>factor=storedProceduresManager.procedureMapCall(
						ObjetoBD.OBTIENE_FACTOR_CONVENIDO.getNombre(), params, null);
				smap1.putAll(factor);
			}
			catch(Exception ex)
			{
				logger.warn("error SIN IMPACTO FUNCIONAL al obtener factor convenido o el factor no se encuentra",ex);
				smap1.put("FACTOR_MIN","0");
				smap1.put("FACTOR_MAX","0");
			}
		}
		
		//llamar web service
		if(success)
		{
			datosAuto = nadaService.obtieneDatosAutomovilNADA(vim);
			success   = datosAuto!=null;
			if(!success)
			{
				error="No se encontr&oacute; informaci&oacute;n para el n&uacute;mero de serie";
				logger.error(error);
				/*parche
				datosAuto = new VehicleValue_Struc();
				datosAuto.setVehicleYear(1);
				datosAuto.setSeriesDescr("a");
				datosAuto.setBodyDescr("b");
				datosAuto.setAvgTradeIn(BigDecimal.valueOf(123d));
				datosAuto.setMakeDescr("c");
				success=true;*/
			}
		}
		
		int tipoValorVehiculo = 3;
		
		if(success){
			
			try{
				tipoValorVehiculo = cotizacionManager.obtieneTipoValorAutomovil(codigoPostal,tipoVehiculo);
				logger.debug("Tipo de Valor de auto a tomar: "+ tipoValorVehiculo);
			}catch(Exception ex){
				logger.error("Error al consultar el tipo de valor del automovil segun CP y tipo auto",ex);
				success = false;
			}
		}
		
		//datos regresados
		if(success)
		{
			smap1.put("AUTO_ANIO"        , datosAuto.getVehicleYear()+"");
			smap1.put("AUTO_DESCRIPCION" , datosAuto.getSeriesDescr()+" "+datosAuto.getBodyDescr());
			
			logger.debug("AvgTradeIn:" + datosAuto.getAvgTradeIn().toString());
			logger.debug("TradeIn:"    + datosAuto.getAvgTradeIn().toString());
			
			String precioAuto = null;
			if(tipoValorVehiculo == 1 || tipoValorVehiculo == 0){
				precioAuto = datosAuto.getAvgTradeIn().toString();
			} else if(tipoValorVehiculo == 2){
				precioAuto = datosAuto.getTradeIn().toString();				
			}
				
			smap1.put("AUTO_PRECIO", precioAuto);
			smap1.put("AUTO_MARCA", datosAuto.getMakeDescr());
		}
		
		logger.info(""
				+ "\n###### webServiceNada ######"
				+ "\n############################"
				);
		return SUCCESS;
	}
	
	public String emitirColectivo()
	{
		logger.debug("\n"
				+ "\n#############################"
				+ "\n###### emitirColectivo ######"
				+ "\nsmap1 "+smap1
				);
		
		exito   = true;
		success = true;

		String cdperson = null;
		
		UserVO usuario  = (UserVO)session.get("USUARIO");
		String cdusuari = usuario.getUser();
		String cdsisrol = usuario.getRolActivo().getClave();
		String cdelemen = usuario.getEmpresa().getElementoId();
		String cdunieco = smap1.get("cdunieco");
		String cdramo   = smap1.get("cdramo");
		String cdtipsit = smap1.get("cdtipsit");
		String estado   = smap1.get("estado");
		String nmpoliza = smap1.get("nmpoliza");
		String cdperpag = smap1.get("cdperpag");
		String ntramite = smap1.get("ntramite");
		String fechaIni = smap1.get("feini");
		String fechaFin = smap1.get("fefin");
		
		//obtener cdperson sesion
		if(exito)
		{
			try
			{
				DatosUsuario datUs = kernelManager.obtenerDatosUsuario(cdusuari,cdtipsit);
				cdperson           = datUs.getCdperson();
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Error al obtener datos del usuario #"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		//validar direccion
		if(exito)
		{
			try
			{
				List<Map<String,String>>lisUsuSinDir=null;
				try
				{
					Map<String,String>paramValidar=new LinkedHashMap<String,String>(0);
					paramValidar.put("pv_cdunieco" , cdunieco);
					paramValidar.put("pv_cdramo"   , cdramo);
					paramValidar.put("pv_estado"   , "W");
					paramValidar.put("pv_nmpoliza" , nmpoliza);
					lisUsuSinDir=kernelManager.PValInfoPersonas(paramValidar);
				}
				catch(Exception ex)
				{
					logger.warn("Error sin impacto funcional al validar domicilios: ",ex);
					lisUsuSinDir=null;
				}
				
				if(lisUsuSinDir!=null&&lisUsuSinDir.size()>0)
				{
					exito           = false;
					respuestaOculta = "Faltan direcciones";
					respuesta       = "Favor de verificar la direcci&oacute;n de los siguientes asegurados:<br/>";
					// f a v o r
					//0 1 2 3 4 5
					if(lisUsuSinDir.get(0).get("nombre").substring(0,5).equalsIgnoreCase("favor"))
					{
						respuesta=lisUsuSinDir.get(0).get("nombre");
					}
					else
					{
						for(int i=0;i<lisUsuSinDir.size();i++)
						{
							respuesta+=lisUsuSinDir.get(i).get("nombre")+"<br/>";
						}					
					}
					logger.debug("Se va a terminar el proceso porque faltan direcciones");
				}
			}
			catch(Exception ex)
			{
				long  timestamp = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Error al revisar domicilios #"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		//maestro historico
		if(exito)
		{
			try
			{
	            Map<String,Object>map2=new LinkedHashMap<String,Object>(0);
	            map2.put("pv_cdunieco_i"  , cdunieco);
	            map2.put("pv_cdramo_i"    , cdramo);
	            map2.put("pv_estado_i"    , estado);
	            map2.put("pv_nmpoliza_i"  , nmpoliza);
	            map2.put("pv_nmsuplem_i"  , "0");
	            map2.put("pv_feINival_i"  , renderFechas.parse(fechaIni));
	            map2.put("pv_hhinival_i"  , renderHora.format(new Date()));
	            map2.put("pv_fefINval_i"  , renderFechas.parse(fechaFin));
	            map2.put("pv_hhfinval_i"  , renderHora.format(new Date()));
	            map2.put("pv_swanula_i"   , null);
	            map2.put("pv_nsuplogi_i"  , "0");
	            map2.put("pv_nsupusua_i"  , null);
	            map2.put("pv_nsupsess_i"  , null);
	            map2.put("pv_fesessio_i"  , null);
	            map2.put("pv_swconfir_i"  , null);
	            map2.put("pv_nmrenova_i"  , null);
	            map2.put("pv_nsuplori_i"  , null);
	            map2.put("pv_cdorddoc_i"  , null);
	            map2.put("pv_swpolfro_i"  , null);
	            map2.put("pv_pocofron_i"  , null);
	            map2.put("pv_swpoldec_i"  , null);
	            map2.put("pv_tippodec_i"  , null);
	            map2.put("pv_accion_i"    , "I");
	            kernelManager.insertaMaestroHistoricoPoliza(map2);
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Error al insertar historico #"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		//tdescsup
		if(exito)
		{
			try
			{
				cotizacionManager.movimientoTdescsup(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,"0"//nsuplogi
						,TipoTramite.POLIZA_NUEVA.getCdtiptra()//cdtipsup
						,renderFechas.parse(fechaIni)
						,nmpoliza//nmsolici
						,new Date()//fesolici
						,new Date()//ferefere
						,null//cdseqpol
						,cdusuari
						,null//nusuasus
						,null//nlogisus
						,cdperson
						,Constantes.INSERT_MODE
						);
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Error al guardar descripcion de emision #"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		String nmpolizaEmi = null;
		String nmpoliexEmi = null;
		String nmsuplemEmi = null;
		
		//emitir
		if(exito)
		{
			try
			{
				Map<String,Object>paramEmi=new LinkedHashMap<String,Object>(0);
				paramEmi.put("pv_cdusuari"  , cdusuari);
				paramEmi.put("pv_cdunieco"  , cdunieco);
				paramEmi.put("pv_cdramo"    , cdramo);
				paramEmi.put("pv_estado"    , estado);
				paramEmi.put("pv_nmpoliza"  , nmpoliza);
				paramEmi.put("pv_nmsituac"  , "1");
				paramEmi.put("pv_nmsuplem"  , "0");
				paramEmi.put("pv_cdelement" , cdelemen); 
				paramEmi.put("pv_cdcia"     , cdunieco);
				paramEmi.put("pv_cdplan"    , null);
				paramEmi.put("pv_cdperpag"  , cdperpag);
				paramEmi.put("pv_cdperson"  , cdperson);
				paramEmi.put("pv_fecha"     , new Date());
				paramEmi.put("pv_ntramite"  , ntramite);
				WrapperResultados wr=kernelManager.emitir(paramEmi);
				
				nmpolizaEmi = (String)wr.getItemMap().get("nmpoliza");
				nmpoliexEmi = (String)wr.getItemMap().get("nmpoliex");
				nmsuplemEmi = (String)wr.getItemMap().get("nmsuplem");
				
				smap1.put("nmpolizaEmi" , nmpolizaEmi);
				smap1.put("nmpoliexEmi" , nmpoliexEmi);
				smap1.put("nmsuplemEmi" , nmsuplemEmi);
				
				try
	            {
	            	serviciosManager.grabarEvento(new StringBuilder("\nEmision")
	            	    ,"EMISION"  //cdmodulo
	            	    ,"EMISION"  //cdevento
	            	    ,new Date() //fecha
	            	    ,cdusuari
	            	    ,((UserVO)session.get("USUARIO")).getRolActivo().getClave()
	            	    ,ntramite
	            	    ,cdunieco
	            	    ,cdramo
	            	    ,"M"
	            	    ,nmpolizaEmi
	            	    ,nmpoliza
	            	    ,null
	            	    ,null
	            	    ,null);
	            }
	            catch(Exception ex)
	            {
	            	logger.error("Error al grabar evento, sin impacto",ex);
	            }
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Error al emitir #"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		String rutaCarpeta = this.getText("ruta.documentos.poliza")+"/"+ntramite;
		//documentos
		if(exito)
		{
			try
			{
				documentosManager.generarDocumentosParametrizados(
						cdunieco
	            		,cdramo
	            		,"M"
	            		,nmpolizaEmi
	            		,"0"
	            		,nmsuplemEmi
	            		,DocumentosManager.PROCESO_EMISION //proceso
	            		,ntramite
	            		,nmpoliza //nmsolici
	            		);
				
				/*
				File   carpeta     = new File(rutaCarpeta);
	            if(!carpeta.exists())
	            {
	            	logger.info("Se va a crear la carpeta "+carpeta);
	            	if(!carpeta.mkdir())
	            	{
	            		throw new Exception("No se pudo crear la carpeta para los documentos");
	            	}
	            }
	            
	            String cdorddoc = emisionManager.insercionDocumentosParametrizados(
	            		cdunieco
	            		,cdramo
	            		,"M"
	            		,nmpolizaEmi
	            		,"0"
	            		,nmsuplemEmi
	            		,"EMISION"
	            		);
	            logger.debug("cdorddoc: {}",cdorddoc);
	            
	            List<Map<String,String>> docsATransferir = cotizacionManager.generarDocumentosBaseDatos(
	            		cdorddoc
	            		,nmpoliza
	            		,ntramite
	            		);
	            
	            String rutaDocsBaseDatos = consultasManager.recuperarTparagen(ParametroGeneral.DIRECTORIO_REPORTES);
	            logger.debug(Utils.join("\nrutaDocsBaseDatos:",rutaDocsBaseDatos));
	            
	            for(Map<String,String>doc:docsATransferir)
	            {
	            	try
	            	{
	            		String origen  = Utils.join(rutaDocsBaseDatos,doc.get("CDDOCUME"));
	            		String destino = Utils.join(getText("ruta.documentos.poliza"),"/",ntramite,"/",doc.get("CDDOCUME"));
	            		logger.debug(Utils.log("\nIntentando mover desde:",origen,",hacia:",destino));
	            		FileUtils.moveFile(
	            				new File(origen)
	            				,new File(destino)
	            				);
	            	}
	            	catch(Exception ex)
	            	{
	            		logger.error("Error al transferir archivo ",ex);
	            	}
	            }
	            */
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Error al crear los documentos #"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		//detalle
		if(exito)
		{
			try
			{
				logger.debug("se inserta detalle nuevo para emision");
	        	/*Map<String,Object>parDmesCon=new LinkedHashMap<String,Object>(0);
	        	parDmesCon.put("pv_ntramite_i"   , ntramite);
	        	parDmesCon.put("pv_feinicio_i"   , new Date());
	        	parDmesCon.put("pv_cdclausu_i"   , null);
	        	parDmesCon.put("pv_comments_i"   , "El tr&aacute;mite se emiti&oacute;");
	        	parDmesCon.put("pv_cdusuari_i"   , cdusuari);
	        	parDmesCon.put("pv_cdmotivo_i"   , null);
	        	parDmesCon.put("pv_cdsisrol_i"   , cdsisrol);
	        	kernelManager.movDmesacontrol(parDmesCon);*/
				mesaControlManager.movimientoDetalleTramite(
						ntramite
						,new Date()
						,null
						,"El tr\u00e1mite se emiti\u00f3"
						,cdusuari
						,null
						,cdsisrol
						,"S"
						);
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Error al guardar detalle #"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		if(exito){
			try
			{
				// Ejecutamos el Web Service de Recibos:
				ice2sigsService.ejecutaWSrecibos(cdunieco, cdramo,
						"M", nmpolizaEmi, 
						nmsuplemEmi, rutaCarpeta,
						cdunieco, nmpoliza,ntramite, 
						true, TipoEndoso.EMISION_POLIZA.getCdTipSup().toString(),
						usuario);
			
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Error al lanzar ws recibos para colectivos"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		//exito
		if(exito)
		{
			respuesta       = new StringBuilder().append("Se ha emitido la p&oacute;liza ")
					                  .append(nmpolizaEmi)
					                  .append(" [")
					                  .append(nmpoliexEmi)
					                  .append("]")
					                  .toString();
			respuestaOculta = "Todo OK";
		}
		
		logger.debug(""
				+ "\n###### emitirColectivo ######"
				+ "\n#############################"
				);
		return SUCCESS;
	}

	public String pantallaCotizacionDemo() {
		this.session=ActionContext.getContext().getSession();
		logger.debug("\n"
				+ "\n####################################"
				+ "\n####################################"
				+ "\n###### pantallaCotizacionDemo ######"
				+ "\n######                        ######"
				);
		logger.debug("smap1: "+smap1);
		logger.debug("session: "+session);
		
		GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
		gc.setEsMovil(session!=null&&session.containsKey("ES_MOVIL")&&((Boolean)session.get("ES_MOVIL"))==true);
		if(!gc.isEsMovil() && smap1.containsKey("movil"))
		{
			gc.setEsMovil(true);
		}
		
		UserVO usuario    = (UserVO) session.get("USUARIO");
		String cdtipsit   = smap1.get("cdtipsit");
		String cdpantalla = smap1.get("cdpantalla");
		
		String ntramite;
		String cdunieco=null;
		String cdramo=null;
		
		smap1.put("user",usuario.getUser());
		
		/////////////////////////////////////////////////////////
		////// poner valores a ntramite, cdunieco y cdramo //////
        if(smap1.get("ntramite")!=null)
        //cuando viene ntramite tambien vienen cdunieco y cdramo
        {
        	ntramite = smap1.get("ntramite");
        	cdunieco = smap1.get("cdunieco");
        	cdramo   = smap1.get("cdramo");
        }
        else
        //cuando no hay ntramite es porque esta cotizando un agente por fuera,
        //y se obtiene cdunieco y cdramo por medio de ese agente
        {
        	try
        	{
        		DatosUsuario datUsu=kernelManager.obtenerDatosUsuario(usuario.getUser(),cdtipsit);//cdramo
        		ntramite="";
        		cdunieco=datUsu.getCdunieco();
        		if(StringUtils.isBlank(smap1.get("cdramo")))
        		{
        			cdramo=datUsu.getCdramo();
        		}
        		else
        		{
        			cdramo=smap1.get("cdramo");
        		}
        		smap1.put("ntramite","");
        		smap1.put("cdunieco",cdunieco);
        		smap1.put("cdramo",cdramo);
        	}
        	catch(Exception ex)
        	{
        		logger.error("error al obtener los datos del agente",ex);
        	}
        }
		////// poner valores a ntramite, cdunieco y cdramo //////
        /////////////////////////////////////////////////////////
        
        ////////////////////////////////////////
        ////// obtener campos de tatrisit //////
        gc.setCdtipsit(cdtipsit);
        
        List<ComponenteVO>camposAgrupados    = new ArrayList<ComponenteVO>(0);
        List<ComponenteVO>camposIndividuales = new ArrayList<ComponenteVO>(0);
        
        imap = new HashMap<String,Item>();
        
		params =  new HashMap<String,String>();
		params.put("PV_CDPANTALLA_I", cdpantalla);
		params.put("PV_CDRAMO_I", cdramo);
		params.put("PV_CDTIPSIT_I", cdtipsit);
		
		Map<String,String> result = null;
		try {
			result = pantallasManager.obtienePantalla(params);
		} catch (Exception e) {
			logger.error("Error al obtener codigo de pantalla para pantalla: " + params, e);
		}
		
		smap1.put("variablesGeneradas", result.get("COMPONENTES"));
		smap1.put("panelGenerado", result.get("DATOS"));
        try
        {
        	List<ComponenteVO>tatrisit=kernelManager.obtenerTatrisit(cdtipsit,usuario.getUser());
        	
	        List<ComponenteVO>temp=new ArrayList<ComponenteVO>();
	        for(ComponenteVO tatriIte:tatrisit)
			{
	        	if(tatriIte.getValue()==null&&tatriIte.getDefaultValue()==null)
	        	{
	        		tatriIte.setComboVacio(true);
	        	}
	        	if(tatriIte.getSwpresen().equalsIgnoreCase("S"))
	        	{
	        		temp.add(tatriIte);
	        	}
	        	else
	        	{
	        		if(cdtipsit.equalsIgnoreCase(TipoSituacion.AUTOS_FRONTERIZOS.getCdtipsit())||
	        				cdtipsit.equalsIgnoreCase(TipoSituacion.AUTOS_PICK_UP.getCdtipsit()))
	        		{
	        			if(tatriIte.getNameCdatribu().equalsIgnoreCase("26"))
	        			{
	        				tatriIte.setOculto(true);
	        				temp.add(tatriIte);
	        			}
	        		}
	        	}
	        	if(
	        			(cdtipsit.equalsIgnoreCase(TipoSituacion.AUTOS_FRONTERIZOS.getCdtipsit())
	        			||cdtipsit.equalsIgnoreCase(TipoSituacion.AUTOS_PICK_UP.getCdtipsit())
	        			)
	        			&&tatriIte.getNameCdatribu().equalsIgnoreCase("24")
	        			)
	        	{
	        		ResponseTipoCambio rtc=tipoCambioService.obtieneTipoCambioDolarGS(2);
	        		if(rtc!=null&&rtc.getTipoCambio()!=null&&rtc.getTipoCambio().getVenCam()!=null)
	        		{
	        			tatriIte.setOculto(true);
	        			tatriIte.setValue(rtc.getTipoCambio().getVenCam().doubleValue()+"");
	        		}
	        	}
			}
	        tatrisit=temp;
	        
			for(ComponenteVO tatriIte:tatrisit)
			{
				////////////////////
				////// custom //////
				if(cdtipsit.equalsIgnoreCase("SL")||cdtipsit.equalsIgnoreCase("SN"))
				{
					if(tatriIte.getCatalogo()!=null&&
							(tatriIte.getCatalogo().equalsIgnoreCase("2CODPOS")
									||tatriIte.getCatalogo().equalsIgnoreCase("2CODPOSN")))//codigo postal
					{
						tatriIte.setCatalogo("");
					}
				}
				////// custom //////
				////////////////////
					
				if(tatriIte.getSwsuscri().equalsIgnoreCase("S"))//S=individual
				{
					tatriIte.setColumna(Constantes.SI);
					camposIndividuales.add(tatriIte);
				}
				else
				{
					camposAgrupados.add(tatriIte);
				}
			}

			gc.generaParcial(camposAgrupados);
			//imap.put("camposAgrupados" , gc.getItems());
			imap.put("fieldsAgrupados" , gc.getFields());
        	
			if(camposIndividuales.size()>0)
			{
				gc.generaParcialConEditor(camposIndividuales);
				imap.put("itemsIndividuales"  , gc.getItems());
				imap.put("camposIndividuales" , gc.getColumns());
				imap.put("fieldsIndividuales" , gc.getFields());
			}
			else
			{
				imap.put("itemsIndividuales"  , null);
				imap.put("camposIndividuales" , null);
				imap.put("fieldsIndividuales" , null);
			}
			
			List<ComponenteVO>validaciones=pantallasManager.obtenerComponentes(
					null, null, cdramo, cdtipsit, null, null, "VALIDACIONES_COTIZA", gc.isEsMovil()?"MOVIL":"DESKTOP", null);
			if(validaciones.size()>0)
			{
				gc.generaComponentes(validaciones, true, false, false, false, false, true);
				imap.put("validacionCustomButton" , gc.getButtons());
			}
			else
			{
				imap.put("validacionCustomButton" , null);
			}
			
			List<ComponenteVO>modeloExtra = pantallasManager.obtenerComponentes(
					null, null, cdramo, cdtipsit, null, null, "VALIDACIONES_COTIZA", "MODELO", null);
			gc.generaComponentes(modeloExtra, true, true, true, true, true, false);
			if(modeloExtra.size()>0)
			{
				imap.put("modeloExtraFields"  , gc.getFields());
				imap.put("modeloExtraColumns" , gc.getColumns());
				imap.put("modeloExtraItems"   , gc.getItems());
			}
			else
			{
				imap.put("modeloExtraFields"  , null);
				imap.put("modeloExtraColumns" , null);
				imap.put("modeloExtraItems"   , null);
			}
        }
        catch(Exception ex)
        {
        	logger.error("error al obtener los campos de cotizacion",ex);
        }
        
		logger.debug("camposAgrupados: "+camposAgrupados);
		logger.debug("camposIndividuales: "+camposIndividuales);
        ////// obtener campos de tatrisit //////
        ////////////////////////////////////////
        
		logger.debug("\n"
				+ "\n######                        ######"
				+ "\n###### pantallaCotizacionDemo ######"
				+ "\n####################################"
				+ "\n####################################"
				);
		return gc.isEsMovil() ? "success_mobile" : SUCCESS;
	}
	/*/////////////////////////////*/
	////// cotizacion dinamica //////
	/////////////////////////////////

	/////////////////////
	////// cotizar //////
	/*/////////////////*/
	public String cotizar()
	{
		logger.debug(Utils.log(
				 "\n#####################"
				,"\n###### cotizar ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			setCheckpoint("Validando datos para cotizar");
			
			checkNull(smap1, "No se recibieron datos para cotizar");
			
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String cdtipsit = smap1.get("cdtipsit");
			
			String cdagente = smap1.get("cdagenteAux");
			
			checkNull(session                , "No hay sesion");
			checkNull(session.get("USUARIO") , "No hay usuario en la sesion");
			UserVO usuario  = (UserVO)session.get("USUARIO");
			String cdusuari = usuario.getUser();
			String cdelemen = usuario.getEmpresa().getElementoId();
			
			checkList(slist1, "No se recibieron datos de incisos");
			String nmpoliza = slist1.get(0).get("nmpoliza");
			String feini    = slist1.get(0).get("feini");
			String fefin    = slist1.get(0).get("fefin");
			String fesolici = slist1.get(0).get("FESOLICI");
			
			String cdpersonCli = smap1.get("cdpersonCli");
			String cdideperCli = smap1.get("cdideperCli");
			
			boolean noTarificar = StringUtils.isNotBlank(smap1.get("notarificar"))&&smap1.get("notarificar").equals("si");
			
			boolean conIncisos = StringUtils.isNotBlank(smap1.get("conincisos"))&&smap1.get("conincisos").equals("si");
			
			Map<String,String>tvalopol=new HashMap<String,String>();
			for(Entry<String,String>en:slist1.get(0).entrySet())
			{
				String key=en.getKey();
				if(key.length()>"aux.".length()
						&&key.substring(0,"aux.".length()).equals("aux.")
						)
				{
					tvalopol.put(key.substring("aux.".length()),en.getValue());
				}
			}
			
			ManagerRespuestaSlistSmapVO resp=cotizacionManager.cotizar(
					cdunieco
					,cdramo
					,cdtipsit
					,cdusuari
					,cdelemen
					,nmpoliza
					,feini
					,fefin
					,fesolici
					,cdpersonCli
					,cdideperCli
					,noTarificar
					,conIncisos
					,slist1
					,smap1.containsKey("movil")
					,tvalopol
					,cdagente
					);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			if(exito)
			{
				smap1.putAll(resp.getSmap());
				slist2=resp.getSlist();
				
				for (int i = 0; i < slist2.size(); i++) {
					if (Integer.parseInt(slist2.get(i).get("CDPERPAG").trim()) == 1 && Integer.parseInt(cdramo.trim())==5) {
						slist2.get(i).put("MNPRIMA4L","0");
						slist2.get(i).put("MNPRIMA3A","0");
						slist2.get(i).put("MNPRIMA5B","0");
						break;}
					}
			}
			
			success = exito;
			error   = respuesta;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
			success   = false;
			error     = respuesta;
		}
		
		logger.debug(Utils.log(
				 "\n###### cotizar ######"
				,"\n#####################"
				));
		return SUCCESS;
	}
	
	/*
	public String cotizarOld()
	{
		logger.debug("\n"
				+ "\n###############################"
				+ "\n###############################"
				+ "\n######      cotizar      ######"
				+ "\n######                   ######"
				);
		logger.debug("smap1: "+smap1);
		logger.debug("slist1: "+slist1);
		
		this.session=ActionContext.getContext().getSession();
		
		try
		{
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String cdtipsit = smap1.get("cdtipsit");
			
			UserVO usuario    = (UserVO)session.get("USUARIO");
			String user       = usuario.getUser();
			String cdelemento = usuario.getEmpresa().getElementoId();
			
			String nmpoliza = slist1.get(0).get("nmpoliza");
			String feini    = slist1.get(0).get("feini");
			String fefin    = slist1.get(0).get("fefin");
			
			String cdpersonCli = smap1.get("cdpersonCli");
			String cdideperCli = smap1.get("cdideperCli");
			
			boolean noTarificar = StringUtils.isNotBlank(smap1.get("notarificar"))&&smap1.get("notarificar").equals("si");
			Date fechaHoy = new Date();
			
			boolean conIncisos = StringUtils.isNotBlank(smap1.get("conincisos"))&&smap1.get("conincisos").equals("si");
			
			smap1.put("nmpoliza",nmpoliza);//salida
			
			if(noTarificar==false)
			{
				////////////////////////////////
				////// si no hay nmpoliza //////
				if(nmpoliza==null||nmpoliza.length()==0)
				{
					try
					{
						WrapperResultados wrapperNumeroPoliza = kernelManager.calculaNumeroPoliza(cdunieco,cdramo,"W");
						nmpoliza = (String)wrapperNumeroPoliza.getItemMap().get("NUMERO_POLIZA");
					}
					catch(Exception ex)
					{
						throw new ApplicationException("Falta parametrizar la numeraci&oacute;n de p&oacute;liza");
					}
				}
				////// si no hay nmpoliza //////
				////////////////////////////////
				
				smap1.put("nmpoliza",nmpoliza);//salida
				
				//////////////////////
	            ////// mpolizas //////
	            Map<String,String>mapaMpolizas=new HashMap<String,String>(0);
	            mapaMpolizas.put("pv_cdunieco"  , cdunieco);
	            mapaMpolizas.put("pv_cdramo"    , cdramo);
	            mapaMpolizas.put("pv_estado"    , "W");
	            mapaMpolizas.put("pv_nmpoliza"  , nmpoliza);
	            mapaMpolizas.put("pv_nmsuplem"  , "0");
	            mapaMpolizas.put("pv_status"    , "V");
	            mapaMpolizas.put("pv_swestado"  , "0");
	            mapaMpolizas.put("pv_nmsolici"  , null);
	            mapaMpolizas.put("pv_feautori"  , null);
	            mapaMpolizas.put("pv_cdmotanu"  , null);
	            mapaMpolizas.put("pv_feanulac"  , null);
	            mapaMpolizas.put("pv_swautori"  , "N");
	            mapaMpolizas.put("pv_cdmoneda"  , "001");
	            mapaMpolizas.put("pv_feinisus"  , null);
	            mapaMpolizas.put("pv_fefinsus"  , null);
	            mapaMpolizas.put("pv_ottempot"  , "R");
	            mapaMpolizas.put("pv_feefecto"  , feini);
	            mapaMpolizas.put("pv_hhefecto"  , "12:00");
	            mapaMpolizas.put("pv_feproren"  , fefin);
	            mapaMpolizas.put("pv_fevencim"  , null);
	            mapaMpolizas.put("pv_nmrenova"  , "0");
	            mapaMpolizas.put("pv_ferecibo"  , null);
	            mapaMpolizas.put("pv_feultsin"  , null);
	            mapaMpolizas.put("pv_nmnumsin"  , "0");
	            mapaMpolizas.put("pv_cdtipcoa"  , "N");
	            mapaMpolizas.put("pv_swtarifi"  , "A");
	            mapaMpolizas.put("pv_swabrido"  , null);
	            mapaMpolizas.put("pv_feemisio"  , renderFechas.format(fechaHoy));
	            mapaMpolizas.put("pv_cdperpag"  , "12");
	            mapaMpolizas.put("pv_nmpoliex"  , cdideperCli);
	            mapaMpolizas.put("pv_nmcuadro"  , "P1");
	            mapaMpolizas.put("pv_porredau"  , "100");
	            mapaMpolizas.put("pv_swconsol"  , "S");
	            mapaMpolizas.put("pv_nmpolant"  , null);
	            mapaMpolizas.put("pv_nmpolnva"  , null);
	            mapaMpolizas.put("pv_fesolici"  , renderFechas.format(fechaHoy));
	            mapaMpolizas.put("pv_cdramant"  , user);
	            mapaMpolizas.put("pv_cdmejred"  , null);
	            mapaMpolizas.put("pv_nmpoldoc"  , null);
	            mapaMpolizas.put("pv_nmpoliza2" , null);
	            mapaMpolizas.put("pv_nmrenove"  , null);
	            mapaMpolizas.put("pv_nmsuplee"  , null);
	            mapaMpolizas.put("pv_ttipcamc"  , null);
	            mapaMpolizas.put("pv_ttipcamv"  , null);
	            mapaMpolizas.put("pv_swpatent"  , null);
	            mapaMpolizas.put("pv_pcpgocte"  , "100");
	            mapaMpolizas.put("pv_tipoflot"  , null);
	            mapaMpolizas.put("pv_accion"    , "U");
	            kernelManager.insertaMaestroPolizas(mapaMpolizas);
	            ////// mpolizas //////
	            //////////////////////
				
	            String llaveRol="";
	            String llaveSexo="";
	            String llaveFenacimi="DATE";
	            String llaveCodPostal="";
	            
	            if(conIncisos)
	            {
		            ////////////////////////////////
		            ////// ordenar al titular //////
		            
		            ////// 1. indicar para la situacion el indice //////
		            try {
		            	LinkedHashMap<String,Object>p=new LinkedHashMap<String,Object>();
		            	p.put("cdtipsit",cdtipsit);
		            	Map<String,String>atributos=consultasManager.consultaDinamica(ObjetoBD.OBTIENE_ATRIBUTOS, p).get(0);
		            	if(atributos.get("PARENTESCO") != null) {
		            		llaveRol=atributos.get("PARENTESCO");
		                	if(llaveRol.length()==1) {
		                		llaveRol="0"+llaveRol;
		                	}
		                	llaveRol="parametros.pv_otvalor"+llaveRol;
		            	}
		            	if(atributos.get("SEXO") != null) {
		            		llaveSexo=atributos.get("SEXO");
		            		if(llaveSexo.length()==1) {
		                		llaveSexo="0"+llaveSexo;
		                	}
		                	llaveSexo="parametros.pv_otvalor"+llaveSexo;
		            	}
		            	if(atributos.get("FENACIMI") != null) {
		            		llaveFenacimi=atributos.get("FENACIMI");
		                	if(llaveFenacimi.length()==1) {
		                		llaveFenacimi="0"+llaveFenacimi;
		                	}
		                	llaveFenacimi="parametros.pv_otvalor"+llaveFenacimi;
		            	}
		            	if(atributos.get("CODPOSTAL") != null) {
		            		llaveCodPostal=atributos.get("CODPOSTAL");
		                	if(llaveCodPostal.length()==1) {
		                		llaveCodPostal="0"+llaveCodPostal;
		                	}
		                	llaveCodPostal="parametros.pv_otvalor"+llaveCodPostal;
		            	}
		            } catch(Exception ex){
		            	logger.error("error al obtener atributos", ex);
		            }
		            ////// 1. indicar para la situacion el indice //////
		            
		            ////// parche. Validar codigo postal //////
		            if(StringUtils.isNotBlank(llaveCodPostal)&&StringUtils.isNotBlank(slist1.get(0).get(llaveCodPostal)))
		            {
		            	LinkedHashMap<String,Object>paramsValues=new LinkedHashMap<String,Object>();
		            	paramsValues.put("param1",slist1.get(0).get(llaveCodPostal));
		            	paramsValues.put("param2",cdtipsit);
		            	storedProceduresManager.procedureVoidCall(ObjetoBD.VALIDA_CODPOSTAL_TARIFA.getNombre(), paramsValues, null);
		            }
		            //// parche. Validar codigo postal //////
		            
		            ////// 2. ordenar //////
		            int indiceTitular=-1;
		            for(int i=0;i<slist1.size();i++)
		            {
		            	if(slist1.get(i).get(llaveRol).equalsIgnoreCase("T"))
		            	{
		            		indiceTitular=i;
		            	}
		            }
		            List<Map<String,String>> temp    = new ArrayList<Map<String,String>>(0);
		            Map<String,String>       titular = slist1.get(indiceTitular);
		            temp.add(titular);
		            slist1.remove(indiceTitular);
		            temp.addAll(slist1);
		            slist1=temp;
		            ////// 2. ordenar //////
		            
		            ////// ordenar al titular //////
		            ////////////////////////////////
	            }
	            
	            //////////////////////////////////////////
	            ////// mpolisit y tvalosit iterados //////
	            int contador=1;
	            for(Map<String,String>inciso:slist1)
	            {
	            	//////////////////////////////
	            	////// mpolisit iterado //////
	            	Map<String,Object>mapaPolisitIterado=new HashMap<String,Object>(0);
	                mapaPolisitIterado.put("pv_cdunieco_i"   , cdunieco);
	                mapaPolisitIterado.put("pv_cdramo_i"     , cdramo);
	                mapaPolisitIterado.put("pv_estado_i"     , "W");
	                mapaPolisitIterado.put("pv_nmpoliza_i"   , nmpoliza);
	                mapaPolisitIterado.put("pv_nmsituac_i"   , contador+"");
	                mapaPolisitIterado.put("pv_nmsuplem_i"   , "0");
	                mapaPolisitIterado.put("pv_status_i"     , "V");
	                mapaPolisitIterado.put("pv_cdtipsit_i"   , cdtipsit);
	                mapaPolisitIterado.put("pv_swreduci_i"   , null);
	                mapaPolisitIterado.put("pv_cdagrupa_i"   , "1");
	                mapaPolisitIterado.put("pv_cdestado_i"   , "0");
	                mapaPolisitIterado.put("pv_fefecsit_i"   , renderFechas.parse(feini));
	                mapaPolisitIterado.put("pv_fecharef_i"   , renderFechas.parse(feini));
	                mapaPolisitIterado.put("pv_cdgrupo_i"    , null);
	                mapaPolisitIterado.put("pv_nmsituaext_i" , null);
	                mapaPolisitIterado.put("pv_nmsitaux_i"   , null);
	                mapaPolisitIterado.put("pv_nmsbsitext_i" , null);
	                mapaPolisitIterado.put("pv_cdplan_i"     , "1");
	                mapaPolisitIterado.put("pv_cdasegur_i"   , "30");
	                mapaPolisitIterado.put("pv_accion_i"     , "I");
	                kernelManager.insertaPolisit(mapaPolisitIterado);
	            	////// mpolisit iterado //////
	            	//////////////////////////////
	                
	                //////////////////////////////
	                ////// tvalosit iterado //////
	                
	                ////// 1. tvalosit base //////
	                Map<String,String>mapaValositIterado=new HashMap<String,String>(0);
	                mapaValositIterado.put("pv_cdunieco" , cdunieco);
	                mapaValositIterado.put("pv_cdramo"   , cdramo);
	                mapaValositIterado.put("pv_estado"   , "W");
	                mapaValositIterado.put("pv_nmpoliza" , nmpoliza);
	                mapaValositIterado.put("pv_nmsituac" , contador+"");
	                mapaValositIterado.put("pv_nmsuplem" , "0");
	                mapaValositIterado.put("pv_status"   , "V");
	                mapaValositIterado.put("pv_cdtipsit" , cdtipsit);
	                mapaValositIterado.put("pv_accion_i" , "I");
	                ////// 1. tvalosit base //////
	                
	                ////// 2. tvalosit desde form //////
	                for(Entry<String,String>en:inciso.entrySet())
	                {
	                	// p a r a m e t r o s . p v _ o t v a l o r 
	                	//0 1 2 3 4 5 6 7 8 9 0 1
	                	String key=en.getKey();
	                	String value=en.getValue();
	                	if(key.length()>11&&key.substring(0,11).equalsIgnoreCase("parametros."))
	                	{
	                		mapaValositIterado.put(key.substring(11),value);
	                	}
	                }
	                ////// 2. tvalosit desde form //////
	                
	                ////// 3. completar faltantes //////
	                for(int i=1;i<=99;i++)
	                {
	                	String key="pv_otvalor"+i;
	                	if(i<10)
	                	{
	                		key="pv_otvalor0"+i;
	                	}
	                	if(!mapaValositIterado.containsKey(key))
	                	{
	                		mapaValositIterado.put(key,null);
	                	}
	                }
	                ////// 3. completar faltantes //////
	                
	                ////// 4. custom //////
                	ManagerRespuestaSmapVO respTvalositConst=cotizacionManager.obtenerParametrosCotizacion(
                			ParametroCotizacion.TVALOSIT_CONSTANTE
                			,cdramo
                			,cdtipsit
                			,null
                			,null);
                	if(respTvalositConst.isExito())
                	{
                		for(int i=1;i<=13;i++)
                		{
                			String key = respTvalositConst.getSmap().get(Utils.join("P",i,"CLAVE"));
                			String val = respTvalositConst.getSmap().get(Utils.join("P",i,"VALOR"));
                			if(StringUtils.isNotBlank(key)&&StringUtils.isNotBlank(val))
                			{
	                			mapaValositIterado.put
	                			(
	                					Utils.join
	                					(
	                							"pv_otvalor"
	                							,StringUtils.leftPad(key,2,"0")
	                					)
	                					,val
	                			);
                			}
                		}
                	}
	                
                	if(cdtipsit.equals(TipoSituacion.GASTOS_MEDICOS_INDIVIDUAL.getCdtipsit()))
                	{
                		mapaValositIterado.put("pv_otvalor22",
                				cotizacionManager.cargarTabuladoresGMIParche(mapaValositIterado.get("pv_otvalor16"), "22")
                		);
                		mapaValositIterado.put("pv_otvalor23",
                				cotizacionManager.cargarTabuladoresGMIParche(mapaValositIterado.get("pv_otvalor16"), "23")
                		);
                	}
	                ////// 4. custom //////
	                
	                kernelManager.insertaValoresSituaciones(mapaValositIterado);
	                ////// tvalosit iterado //////
	                //////////////////////////////
	                
	                contador++;
	            }
	            ////// mpolisit y tvalosit iterados //////
	            //////////////////////////////////////////
		        
	            /////////////////////////////
	            ////// clonar personas //////
	            contador=1;
	            for(Map<String,String> inciso : slist1)
	            {
	                Map<String,Object> mapaClonPersonaIterado=new HashMap<String,Object>(0);
	                mapaClonPersonaIterado.put("pv_cdelemen_i"  , cdelemento);
	                mapaClonPersonaIterado.put("pv_cdunieco_i"  , cdunieco);
	                mapaClonPersonaIterado.put("pv_cdramo_i"    , cdramo);
	                mapaClonPersonaIterado.put("pv_estado_i"    , "W");
	                mapaClonPersonaIterado.put("pv_nmpoliza_i"  , nmpoliza);
	                mapaClonPersonaIterado.put("pv_nmsituac_i"  , contador+"");
	                mapaClonPersonaIterado.put("pv_cdtipsit_i"  , cdtipsit);
	                mapaClonPersonaIterado.put("pv_fecha_i"     , fechaHoy);
	                mapaClonPersonaIterado.put("pv_cdusuario_i" , user);
	                mapaClonPersonaIterado.put("pv_p_nombre"    , inciso.get("nombre"));
	                mapaClonPersonaIterado.put("pv_s_nombre"    , inciso.get("nombre2"));
	                mapaClonPersonaIterado.put("pv_apellidop"   , inciso.get("apat"));
	                mapaClonPersonaIterado.put("pv_apellidom"   , inciso.get("amat"));
	                mapaClonPersonaIterado.put("pv_sexo"        , inciso.containsKey(llaveSexo)?inciso.get(llaveSexo):llaveSexo);
	                mapaClonPersonaIterado.put("pv_fenacimi"    , inciso.containsKey(llaveFenacimi)?
	                		renderFechas.parse(inciso.get(llaveFenacimi)):(
	                				llaveFenacimi.equalsIgnoreCase("DATE")?
	                						fechaHoy :
	                							renderFechas.parse(llaveFenacimi)));
	                mapaClonPersonaIterado.put("pv_parentesco"  , inciso.containsKey(llaveRol)?inciso.get(llaveRol):llaveRol);
	                kernelManager.clonaPersonas(mapaClonPersonaIterado);
	                contador++;
	            }
	            ////// clonar personas //////
	            /////////////////////////////
	
	            ////// mpoliper contratante recuperado //////
	            if(StringUtils.isNotBlank(cdpersonCli))
	            {
	            	LinkedHashMap<String,Object> paramsMpoliper=new LinkedHashMap<String,Object>(0);
	    			paramsMpoliper.put("pv_cdunieco_i" , cdunieco);
	    			paramsMpoliper.put("pv_cdramo_i"   , cdramo);
	    			paramsMpoliper.put("pv_estado_i"   , "W");
	    			paramsMpoliper.put("pv_nmpoliza_i" , nmpoliza);
	    			paramsMpoliper.put("pv_nmsituac_i" , "0");
					paramsMpoliper.put("pv_cdrol_i"    , "1");
					paramsMpoliper.put("pv_cdperson_i" , cdpersonCli);
					paramsMpoliper.put("pv_nmsuplem_i" , "0");
					paramsMpoliper.put("pv_status_i"   , "V");
					paramsMpoliper.put("pv_nmorddom_i" , "1");
					paramsMpoliper.put("pv_swreclam_i" , null);
					paramsMpoliper.put("pv_accion_i"   , "I");
					paramsMpoliper.put("pv_swexiper_i" , "S");
					kernelManager.movMpoliper(paramsMpoliper);
	            }
	            ////// mpoliper contratante recuperado //////
	            
	            ////////////////////////
	            ////// coberturas //////
	            /* ////////////////////* /
	            Map<String,String> mapCoberturas=new HashMap<String,String>(0);
	            mapCoberturas.put("pv_cdunieco_i" , cdunieco);
	            mapCoberturas.put("pv_cdramo_i"   , cdramo);
	            mapCoberturas.put("pv_estado_i"   , "W");
	            mapCoberturas.put("pv_nmpoliza_i" , nmpoliza);
	            mapCoberturas.put("pv_nmsituac_i" , "0");
	            mapCoberturas.put("pv_nmsuplem_i" , "0");
	            mapCoberturas.put("pv_cdgarant_i" , "TODO");
	            mapCoberturas.put("pv_cdtipsup_i" , "1");
	            kernelManager.coberturas(mapCoberturas);
	            /* ////////////////////* /
	            ////// coberturas //////
	            ////////////////////////
	            
	            //////////////////////////
	            ////// TARIFICACION //////
	            /* //////////////////////* /
	            Map<String,String> mapaTarificacion=new HashMap<String,String>(0);
	            mapaTarificacion.put("pv_cdusuari_i" , user);
	            mapaTarificacion.put("pv_cdelemen_i" , cdelemento);
	            mapaTarificacion.put("pv_cdunieco_i" , cdunieco);
	            mapaTarificacion.put("pv_cdramo_i"   , cdramo);
	            mapaTarificacion.put("pv_estado_i"   , "W");
	            mapaTarificacion.put("pv_nmpoliza_i" , nmpoliza);
	            mapaTarificacion.put("pv_nmsituac_i" , "0");
	            mapaTarificacion.put("pv_nmsuplem_i" , "0");
	            mapaTarificacion.put("pv_cdtipsit_i" , cdtipsit);
	            kernelManager.ejecutaASIGSVALIPOL(mapaTarificacion);
	            /* //////////////////////* /
	            ////// TARIFICACION //////
	            //////////////////////////
		    }
            
            ///////////////////////////////////
            ////// Generacion cotizacion //////
            /* ///////////////////////////////* /
            Map<String,String> mapaDuroResultados=new HashMap<String,String>(0);
            mapaDuroResultados.put("pv_cdusuari_i" , user);
            mapaDuroResultados.put("pv_cdunieco_i" , cdunieco);
            mapaDuroResultados.put("pv_cdramo_i"   , cdramo);
            mapaDuroResultados.put("pv_estado_i"   , "W");
            mapaDuroResultados.put("pv_nmpoliza_i" , nmpoliza);
            mapaDuroResultados.put("pv_cdelemen_i" , cdelemento);
            mapaDuroResultados.put("pv_cdtipsit_i" , cdtipsit);
            List<Map<String,String>> listaResultados=kernelManager.obtenerResultadosCotizacion2(mapaDuroResultados);
            logger.debug("listaResultados: "+listaResultados);
            /* ///////////////////////////////* /
            ////// Generacion cotizacion //////
            ///////////////////////////////////
            
            ////////////////////////////////
            ////// Agrupar resultados //////
            /*
            NMSUPLEM=0,
			FEFECSIT=13/01/2014,
			NMPOLIZA=3853,
			MNPRIMA=4571.92,           <--2
			CDPERPAG=7,                <--1
			DSPLAN=Plus 500,           <--3
			FEVENCIM=13/01/2015,
			STATUS=V,
			NMSITUAC=3,
			ESTADO=W,
			DSPERPAG=DXN Catorcenal,   <--(1)
			CDCIAASEG=20,
			CDIDENTIFICA=2,
			CDTIPSIT=SL,
			FEEMISIO=13/01/2014,
			CDUNIECO=1,
			CDRAMO=2,
			CDPLAN=M,                  <--(3)
			DSUNIECO=PUEBLA
             * /
            
            ////// 1. encontrar planes, formas de pago y algun nmsituac//////
            Map<String,String>formasPago = new LinkedHashMap<String,String>();
            Map<String,String>planes     = new LinkedHashMap<String,String>();
            String nmsituac="";
            for(Map<String,String>res:listaResultados)
            {
            	String cdperpag = res.get("CDPERPAG");
            	String dsperpag = res.get("DSPERPAG");
            	String cdplan   = res.get("CDPLAN");
            	String dsplan   = res.get("DSPLAN");
            	if(!formasPago.containsKey(cdperpag))
            	{
            		formasPago.put(cdperpag,dsperpag);
            	}
            	if(!planes.containsKey(cdplan))
            	{
            		planes.put(cdplan,dsplan);
            	}
            	nmsituac=res.get("NMSITUAC");
            }
            logger.debug("formas de pago: "+formasPago);
            logger.debug("planes: "+planes);
            ////// 1. encontrar planes y formas de pago //////
            
            ////// 2. crear formas de pago //////
            List<Map<String,String>>tarifas=new ArrayList<Map<String,String>>();
            for(Entry<String,String>formaPago:formasPago.entrySet())
            {
            	Map<String,String>tarifa=new HashMap<String,String>();
            	tarifa.put("CDPERPAG",formaPago.getKey());
            	tarifa.put("DSPERPAG",formaPago.getValue());
            	tarifa.put("NMSITUAC",nmsituac);
            	tarifas.add(tarifa);
            }
            logger.debug("tarifas despues de formas de pago: "+tarifas);
            ////// 2. crear formas de pago //////
            
            ////// 3. crear planes //////
            for(Map<String,String>tarifa:tarifas)
            {
            	for(Entry<String,String>plan:planes.entrySet())
                {
                	tarifa.put("CDPLAN"+plan.getKey(),plan.getKey());
                	tarifa.put("DSPLAN"+plan.getKey(),plan.getValue());
                }
            }
            logger.debug("tarifas despues de planes: "+tarifas);
            ////// 3. crear planes //////
            
            ////// 4. crear primas //////
            for(Map<String,String>res:listaResultados)
            {
            	String cdperpag = res.get("CDPERPAG");
            	String mnprima  = res.get("MNPRIMA");
            	String cdplan   = res.get("CDPLAN");
            	for(Map<String,String>tarifa:tarifas)
                {
            		if(tarifa.get("CDPERPAG").equals(cdperpag))
            		{
            			if(tarifa.containsKey("MNPRIMA"+cdplan))
            			{
            				logger.debug("ya hay prima para "+cdplan+" en "+cdperpag+": "+tarifa.get("MNPRIMA"+cdplan));
            				tarifa.put("MNPRIMA"+cdplan,((Double)Double.parseDouble(tarifa.get("MNPRIMA"+cdplan))+(Double)Double.parseDouble(mnprima))+"");
            				logger.debug("nueva: "+tarifa.get("MNPRIMA"+cdplan));
            			}
            			else
            			{
            				logger.debug("primer prima para "+cdplan+" en "+cdperpag+": "+mnprima);
            				tarifa.put("MNPRIMA"+cdplan,mnprima);
            			}
            		}
                }
            }
            logger.debug("tarifas despues de primas: "+tarifas);
            
            slist2=tarifas;
            ////// 4. crear primas //////
            
            ////// Agrupar resultados //////
            ////////////////////////////////
            
            ///////////////////////////////////
            ////// columnas para el grid //////
            List<ComponenteVO>tatriPlanes=new ArrayList<ComponenteVO>();
            
            ////// 1. forma de pago //////
            ComponenteVO tatriCdperpag=new ComponenteVO();
        	tatriCdperpag.setType(ComponenteVO.TIPO_GENERICO);
        	tatriCdperpag.setLabel("CDPERPAG");
        	tatriCdperpag.setTipoCampo(ComponenteVO.TIPOCAMPO_NUMERICO);
        	tatriCdperpag.setNameCdatribu("CDPERPAG");
        	
        	/*Map<String,String>mapaCdperpag=new HashMap<String,String>();
        	mapaCdperpag.put("OTVALOR10","CDPERPAG");
        	tatriCdperpag.setMapa(mapaCdperpag);* /
        	tatriPlanes.add(tatriCdperpag);
        	
        	ComponenteVO tatriDsperpag=new ComponenteVO();
        	tatriDsperpag.setType(ComponenteVO.TIPO_GENERICO);
        	tatriDsperpag.setLabel("Forma de pago");
        	tatriDsperpag.setTipoCampo(ComponenteVO.TIPOCAMPO_ALFANUMERICO);
        	tatriDsperpag.setNameCdatribu("DSPERPAG");
        	tatriDsperpag.setColumna(Constantes.SI);
        	
        	/*Map<String,String>mapaDsperpag=new HashMap<String,String>();
        	mapaDsperpag.put("OTVALOR08","S");
        	mapaDsperpag.put("OTVALOR10","DSPERPAG");
        	tatriDsperpag.setMapa(mapaDsperpag);* /
        	tatriPlanes.add(tatriDsperpag);
        	////// 1. forma de pago //////
        	
        	////// 2. nmsituac //////
        	ComponenteVO tatriNmsituac=new ComponenteVO();
        	tatriNmsituac.setType(ComponenteVO.TIPO_GENERICO);
        	tatriNmsituac.setLabel("NMSITUAC");
        	tatriNmsituac.setTipoCampo(ComponenteVO.TIPOCAMPO_NUMERICO);
        	tatriNmsituac.setNameCdatribu("NMSITUAC");
        	
        	/*Map<String,String>mapaNmsituac=new HashMap<String,String>();
        	mapaNmsituac.put("OTVALOR10","NMSITUAC");
        	tatriNmsituac.setMapa(mapaNmsituac);* /
        	tatriPlanes.add(tatriNmsituac);
        	////// 2. nmsituac //////
        	
        	////// 2. planes //////
            for(Entry<String,String>plan:planes.entrySet())
            {
            	////// prima
            	ComponenteVO tatriPrima=new ComponenteVO();
            	tatriPrima.setType(ComponenteVO.TIPO_GENERICO);
            	tatriPrima.setLabel(plan.getValue());
            	tatriPrima.setTipoCampo(ComponenteVO.TIPOCAMPO_PORCENTAJE);
            	tatriPrima.setColumna(Constantes.SI);
            	tatriPrima.setNameCdatribu("MNPRIMA"+plan.getKey());
            	tatriPrima.setRenderer("function(v)"
            			+ "{"
            			+ "    debug('valor:',v);"
            			+ "    v=v.toFixed(2);"
            			+ "    debug('valor fixed:',v);"
            			+ "    var v2='';"
            			+ "    var ultimoPunto=-3;"
            			+ "    for(var i=(v+'').length-1;i>=0;i--)"
            			+ "    {"
            			+ "        var digito=(v+'').charAt(i);"
            			+ "        if(digito=='.')"
            			+ "        {"
            			+ "            ultimoPunto=-2;"
            			+ "        }"
            			+ "        if(ultimoPunto>-3)"
            			+ "        {"
            			+ "            ultimoPunto=ultimoPunto+1;"
            			+ "        }"
            			+ "        if(ultimoPunto%3==0&&ultimoPunto>0)"
            			+ "        {"
            			+ "            digito=digito+',';"
            			+ "        }"
            			+ "        v2=digito+v2;"
            			+ "        if(i==0)"
            			+ "        {"
            			+ "            v2='$ '+v2;"
            			+ "        }"
            			+ "    }"
            			+ "    return v2;"
            			+ "}");
            	
            	/*Map<String,String>mapaPlan=new HashMap<String,String>();
            	mapaPlan.put("OTVALOR08","S");
            	mapaPlan.put("OTVALOR09","MONEY");
            	mapaPlan.put("OTVALOR10","MNPRIMA"+plan.getKey());
            	tatriPrima.setMapa(mapaPlan);* /
            	tatriPlanes.add(tatriPrima);
            	
            	////// cdplan
            	ComponenteVO tatriCdplan=new ComponenteVO();
             	tatriCdplan.setType(ComponenteVO.TIPO_GENERICO);
             	tatriCdplan.setLabel("CDPLAN"+plan.getKey());
             	tatriCdplan.setTipoCampo(ComponenteVO.TIPOCAMPO_ALFANUMERICO);
             	tatriCdplan.setNameCdatribu("CDPLAN"+plan.getKey());
             	tatriCdplan.setColumna(ComponenteVO.COLUMNA_OCULTA);
             	
             	/*Map<String,String>mapaCdplan=new HashMap<String,String>();
             	//mapaCdplan.put("OTVALOR08","H");
             	mapaCdplan.put("OTVALOR10","CDPLAN"+plan.getKey());
             	tatriCdplan.setMapa(mapaCdplan);* /
             	tatriPlanes.add(tatriCdplan);
             	
             	////// dsplan
             	ComponenteVO tatriDsplan=new ComponenteVO();
             	tatriDsplan.setType(ComponenteVO.TIPO_GENERICO);
             	tatriDsplan.setLabel("DSPLAN"+plan.getKey());
             	tatriDsplan.setTipoCampo(ComponenteVO.TIPOCAMPO_ALFANUMERICO);
             	tatriDsplan.setNameCdatribu("DSPLAN"+plan.getKey());
             	
             	/*Map<String,String>mapaDsplan=new HashMap<String,String>();
             	//mapaDsplan.put("OTVALOR08","H");
             	mapaDsplan.put("OTVALOR10","DSPLAN"+plan.getKey());
             	tatriDsplan.setMapa(mapaDsplan);* /
             	tatriPlanes.add(tatriDsplan);
            }
            ////// 2. planes //////
            
            GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
            gc.setEsMovil(session!=null&&session.containsKey("ES_MOVIL")&&((Boolean)session.get("ES_MOVIL"))==true);
            if(!gc.isEsMovil()&&smap1.containsKey("movil"))
            {
            	gc.setEsMovil(true);
            }
            gc.genera(tatriPlanes);
            
            String columnas = gc.getColumns().toString();
            // c o l u m n s : [
            //0 1 2 3 4 5 6 7 8
            smap1.put("columnas",columnas.substring(8));
            
            String fields = gc.getFields().toString();
            // f i e l d s : [
            //0 1 2 3 4 5 6 7
            smap1.put("fields",fields.substring(7));
            ////// columnas para el grid //////
            ///////////////////////////////////
            
			success=true;
		}
		catch(Exception ex)
		{
			logger.debug("error al cotizar",ex);
			success=false;
			error=ex.getMessage();
		}
		
		logger.debug("\n"
				+ "\n######                   ######"
				+ "\n######      cotizar      ######"
				+ "\n###############################"
				+ "\n###############################"
				);
		return SUCCESS;
	}
	/*/////////////////*/
	////// cotizar //////
	/////////////////////
	
	public String cargarCotizacion()
	{
		logger.info(""
				+ "\n##############################"
				+ "\n###### cargarCotizacion ######"
				);
		logger.info("smap1: "+smap1);
		success = true;
		
		String cdunieco = smap1.get("cdunieco");
		String cdramo   = smap1.get("cdramo");
		String estado   = "W";
		String cdtipsit = smap1.get("cdtipsit");
		String nmpoliza = smap1.get("nmpoliza");
		logger.info("cdramo: "+cdramo);
		logger.info("cdtipsit: "+cdtipsit);
		logger.info("nmpoliza: "+nmpoliza);
		
		UserVO usuario  = (UserVO)session.get("USUARIO");
		String cdusuari = usuario.getUser();
		String cdsisrol = usuario.getRolActivo().getClave();
		
		//validar nmpoliza contra producto y situacion
		if(success)
		{
			try
			{
				Map<String,String>tipoSituacion=cotizacionManager.cargarTipoSituacion(cdramo,cdtipsit);
				
				LinkedHashMap<String,Object>paramsValidaCargarCotizacion=new LinkedHashMap<String,Object>();
				paramsValidaCargarCotizacion.put("param1" , cdramo);
				paramsValidaCargarCotizacion.put("param2" , cdtipsit);
				paramsValidaCargarCotizacion.put("param3" , nmpoliza);
				Map<String,String>datosParaComplementar=storedProceduresManager.procedureMapCall(ObjetoBD.VALIDA_CARGAR_COTIZACION.getNombre(), paramsValidaCargarCotizacion, null);
				
				/*
				 * cuando se encuentra cdunieco y ntramite para esa cotizacion y no es auto:
				 */
				if(tipoSituacion.get("SITUACION").equals("PERSONA")
						&&datosParaComplementar.containsKey("NTRAMITE"))
				{
					throw new Exception("La cotizaci&oacute;n ya se encuentra en tr&aacute;mite de emisi&oacute;n");
				}
				/*
				 * cuando se encuentra cdunieco y ntramite y es auto, se mandara a datos complementarios
				 */
				else
				{
					if(datosParaComplementar.containsKey("ESTADO")
							&&datosParaComplementar.containsKey("NMPOLIZA")
							)//para clonar emitidas
					{
						cdunieco = datosParaComplementar.get("CDUNIECO");
						estado   = datosParaComplementar.get("ESTADO");
						nmpoliza = datosParaComplementar.get("NMPOLIZA");
					}
					else if(datosParaComplementar.containsKey("CDUNIECO_RECUPERADO"))//para normales
					{
						cdunieco = datosParaComplementar.get("CDUNIECO_RECUPERADO");
						smap1.put("CDUNIECO" , cdunieco);
					}
					else if(datosParaComplementar.containsKey("NTRAMITE")
							&&datosParaComplementar.containsKey("CDUNIECO")
							)//para complementar/clonar tramite
					{
						cdunieco = datosParaComplementar.get("CDUNIECO");
					}
					smap1.putAll(datosParaComplementar);
				}
			}
			catch(Exception ex)
			{
				logger.error("error obtenido al validar carga de cotizacion",ex);
				error=ex.getMessage();
				success = false;
			}
		}
		//validar nmpoliza contra producto y situacion
		
		//recupera tvalosit
		if(success)
		{
			try
			{
				LinkedHashMap<String,Object>paramsObtenerTvalosit=new LinkedHashMap<String,Object>();
				paramsObtenerTvalosit.put("param1" , cdunieco);
				paramsObtenerTvalosit.put("param2" , cdramo);
				paramsObtenerTvalosit.put("param3" , estado);
				paramsObtenerTvalosit.put("param4" , cdtipsit);
				paramsObtenerTvalosit.put("param5" , nmpoliza);
				paramsObtenerTvalosit.put("param6" , cdusuari);
				paramsObtenerTvalosit.put("param7" , cdsisrol);
				slist1 = storedProceduresManager.procedureListCall(ObjetoBD.OBTIENE_TVALOSIT_COTIZACION.getNombre(), paramsObtenerTvalosit, null);
				if(slist1==null||slist1.size()==0)
				{
					throw new Exception("No se puede cargar la cotizaci&oacute;n");
				}
				for(Map<String,String>iInciso:slist1)
				{
					String iCdunieco = iInciso.get("CDUNIECO");
					String iEstado   = iInciso.get("ESTADO");
					String iNmsituac = iInciso.get("NMSITUAC");
					logger.info("iCdunieco: "+iCdunieco);
					logger.info("iEstado: "+iEstado);
					logger.info("iNmsituac: "+iNmsituac);
					LinkedHashMap<String,Object>paramsObtenerMpersonaCotizacion=new LinkedHashMap<String,Object>();
					paramsObtenerMpersonaCotizacion.put("param1",iCdunieco);
					paramsObtenerMpersonaCotizacion.put("param2",cdramo);
					paramsObtenerMpersonaCotizacion.put("param3",iEstado);
					paramsObtenerMpersonaCotizacion.put("param4",nmpoliza);
					paramsObtenerMpersonaCotizacion.put("param5",iNmsituac);
					iInciso.putAll(storedProceduresManager.procedureMapCall(ObjetoBD.OBTIENE_MPERSONA_COTIZACION.getNombre(),
							paramsObtenerMpersonaCotizacion, null));
					//copiar OTVALORXX a PARAMETROS.PV_OTVALORXX
					Map<String,String>iIncisoOtvalor=new HashMap<String,String>();
					for(Entry<String,String>en:iInciso.entrySet())
					{
						String key   = en.getKey();
						String value = en.getValue();
						if(key.length()>"otvalor".length()
								&&key.substring(0, "otvalor".length()).equalsIgnoreCase("otvalor"))
						{
							iIncisoOtvalor.put(("PARAMETROS.PV_"+key).toLowerCase(),value);
						}
						else
						{
							iIncisoOtvalor.put(key.toLowerCase(),value);
						}
					}
					iInciso.putAll(iIncisoOtvalor);
				}
			}
			catch(Exception ex)
			{
				logger.error("error al recuperar tvalosit",ex);
				error   = ex.getMessage();
				success = false;
			}
		}
		//recupera tvalosit
		
		//recupera mpolizas
		if(success)
		{
			try
			{
				List<Map<String,String>>polizas=consultasManager.cargarMpolizasPorParametrosVariables(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,null
						,null
						,null);
				smap1.put("FESOLICI" , polizas.get(0).get("FESOLICI"));
				smap1.put("FEEFECTO" , polizas.get(0).get("FEEFECTO"));
				smap1.put("FEPROREN" , polizas.get(0).get("FEPROREN"));
			}
			catch(Exception ex)
			{
				logger.error("error al obtener datos de poliza",ex);
				error   = ex.getMessage();
				success = false;
			}
		}
		//recupera mpolizas
		
		//recuperar dias validos cotizacion
		if(success)
		{
			ManagerRespuestaSmapVO dias=cotizacionManager.obtenerParametrosCotizacion(ParametroCotizacion.DIAS_VALIDOS_COTIZACION,cdramo,cdtipsit,null,null);
			if(dias.isExito())
			{
				smap1.put("diasValidos" , dias.getSmap().get("P1VALOR"));
			}
			else
			{
				logger.error("error al obtener dias validos de cotizacion, se cargan 15 por defecto, no impacta el flujo: "+dias.getRespuestaOculta());
				smap1.put("diasValidos" , "15");
			}
		}
		//recuperar dias validos cotizacion
		
		//recuperar tvalopol
		if(success)
		{
			try
			{
				Map<String,String>tvalopol=cotizacionManager.cargarTvalopol(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						);
				for(Entry<String,String>en:tvalopol.entrySet())
				{
					smap1.put(Utils.join("aux.",en.getKey().substring("parametros.pv_".length())),en.getValue());
				}
			}
			catch(Exception ex)
			{
				logger.error("Error al recuperar tvlopol",ex);
				error   = ex.getMessage();
				success = false;
			}
		}
		//recuperar tvalopol
		
		logger.info(""
				+ "\n###### cargarCotizacion ######"
				+ "\n##############################"
				);
		return SUCCESS;
	}
	
	public String guardarSituacionesAuto()
	{
		logger.info(""
				+ "\n####################################"
				+ "\n###### guardarSituacionesAuto ######"
				);
		logger.info("smap1: "+smap1);
		
		String cdunieco = null;
		String cdramo   = null;
		String cdtipsit = null;
		String estado   = null;
		String nmpoliza = null;
		success = true;
		
		//obtener parametros
		if(success)
		{
			try
			{
				cdunieco = smap1.get("cdunieco");
				cdramo   = smap1.get("cdramo");
				cdtipsit = smap1.get("cdtipsit");
				estado   = smap1.get("estado");
				nmpoliza = smap1.get("nmpoliza");
				logger.info("cdunieco: " + cdunieco);
				logger.info("cdramo: "   + cdramo);
				logger.info("cdtipsit: " + cdtipsit);
				logger.info("estado: "   + estado);
				logger.info("nmpoliza: " + nmpoliza);
			}
			catch(Exception ex)
			{
				logger.error("error al obtener parametros",ex);
				error   = "No se recibieron los datos necesarios";
				success = false;
			}
		}
		
		//poner cdperson restantes
		if(success)
		{
			try
			{
				for(Map<String,String>iAsegu:slist1)
				{
					if(StringUtils.isBlank(iAsegu.get("cdperson")))
					{
						Map<String,Object>cdperson=storedProceduresManager.procedureParamsCall(
								ObjetoBD.GENERAR_CDPERSON.getNombre(),
								new LinkedHashMap<String,Object>(),
								null,
								new String[]{"pv_cdperson_o"},
								null);
						iAsegu.put("cdperson",(String)cdperson.get("pv_cdperson_o"));
					}
				}
			}
			catch(Exception ex)
			{
				logger.error("error al completar cdperson de asegurados",ex);
				error   = ex.getMessage();
				success = false;
			}
		}
		
		//ordenar nmsituac
		if(success)
		{
			try
			{
				List<Map<String,String>>listaAux=new ArrayList<Map<String,String>>();
				
				//agregar titular
				for(Map<String,String>aseg:slist1)
				{
					if(aseg.get("cdrol").equals("1"))
					{
						listaAux.add(aseg);
					}
				}
				
				//agregar demas
				for(Map<String,String>aseg:slist1)
				{
					if(!aseg.get("cdrol").equals("1"))
					{
						listaAux.add(aseg);
					}
				}
				slist1=listaAux;
				
				//poner nmsituac
				int i=0;
				for(Map<String,String>aseg:slist1)
				{
					i=i+1;
					aseg.put("nmsituac",i+"");
				}
			}
			catch(Exception ex)
			{
				logger.error("error al ordenar nmsituac",ex);
				error   = ex.getMessage();
				success = false;
			}
		}
		
		//borrar mpoliper
		if(success)
		{
			try
			{
				LinkedHashMap<String,Object>paramsBorrarMpoliper=new LinkedHashMap<String,Object>();
				paramsBorrarMpoliper.put("param1" , cdunieco);
				paramsBorrarMpoliper.put("param2" , cdramo);
				paramsBorrarMpoliper.put("param3" , estado);
				paramsBorrarMpoliper.put("param4" , nmpoliza);
				storedProceduresManager.procedureVoidCall(ObjetoBD.BORRAR_MPOLIPER.getNombre(), paramsBorrarMpoliper, null);
			}
			catch(Exception ex)
			{
				logger.error("error al borrar mpoliper",ex);
				error   = ex.getMessage();
				success = false;
			}
		}
		
		//insertar mpersona y mpoliper
		if(success)
		{
			for(Map<String,String>aseg:slist1)
			{
				//insertar mpersona
				if(success)
				{
					try
					{
						LinkedHashMap<String,Object> parametros=new LinkedHashMap<String,Object>(0);
						parametros.put("param01_pv_cdperson_i"    , aseg.get("cdperson"));
						parametros.put("param02_pv_cdtipide_i"    , "1");
						parametros.put("param03_pv_cdideper_i"    , aseg.get("cdideper"));
						parametros.put("param04_pv_dsnombre_i"    , aseg.get("nombre"));
						parametros.put("param05_pv_cdtipper_i"    , "1");
						parametros.put("param06_pv_otfisjur_i"    , aseg.get("tpersona"));
						parametros.put("param07_pv_otsexo_i"      , aseg.get("sexo"));
						parametros.put("param08_pv_fenacimi_i"    , renderFechas.parse((String)aseg.get("fenacimi")));
						parametros.put("param09_pv_cdrfc_i"       , aseg.get("cdrfc"));
						parametros.put("param10_pv_dsemail_i"     , "");
						parametros.put("param11_pv_dsnombre1_i"   , aseg.get("segundo_nombre"));
						parametros.put("param12_pv_dsapellido_i"  , aseg.get("Apellido_Paterno"));
						parametros.put("param13_pv_dsapellido1_i" , aseg.get("Apellido_Materno"));
						parametros.put("param14_pv_feingreso_i"   , new Date());
						parametros.put("param15_pv_cdnacion_i"    , aseg.get("nacional"));
						parametros.put("param16"                  , null);
						parametros.put("param17"                  , null);
						parametros.put("param18"                  , null);
						parametros.put("param19"                  , null);
						parametros.put("param18"                  , null);
						parametros.put("param19"                  , null);
						parametros.put("param20"                  , null);
						parametros.put("param21"                  , null);
						parametros.put("param22"                  , null);
						parametros.put("param23"                  , null);
						parametros.put("param24_pv_accion_i"      , "I");
						String[] tipos=new String[]{
								"VARCHAR","VARCHAR","VARCHAR","VARCHAR",
								"VARCHAR","VARCHAR","VARCHAR","DATE",
								"VARCHAR","VARCHAR","VARCHAR","VARCHAR",
								"VARCHAR","DATE"   ,"VARCHAR","VARCHAR",
								"VARCHAR","VARCHAR","VARCHAR","VARCHAR",
								"VARCHAR","VARCHAR","VARCHAR","VARCHAR"
						};
						storedProceduresManager.procedureVoidCall(ObjetoBD.MOV_MPERSONA.getNombre(), parametros, tipos);
					}
					catch(Exception ex)
					{
						logger.error("error al insertar mpersona "+aseg,ex);
						error   = ex.getMessage();
						success = false;
					}
				}
				
				//insertar mpoliper
				if(success)
				{
					try
					{
						LinkedHashMap<String,Object> parametros=new LinkedHashMap<String,Object>(0);
						parametros.put("param01_pv_cdunieco_i" , cdunieco);
						parametros.put("param02_pv_cdramo_i"   , cdramo);
						parametros.put("param03_pv_estado_i"   , estado);
						parametros.put("param04_pv_nmpoliza_i" , nmpoliza);
						parametros.put("param05_pv_nmsituac_i" , aseg.get("nmsituac"));
						parametros.put("param06_pv_cdrol_i"    , aseg.get("cdrol"));
						parametros.put("param07_pv_cdperson_i" , aseg.get("cdperson"));
						parametros.put("param08_pv_nmsuplem_i" , "0");
						parametros.put("param09_pv_status_i"   , "V");
						parametros.put("param10_pv_nmorddom_i" , "1");
						parametros.put("param11_pv_swreclam_i" , null);
						parametros.put("param12_pv_accion_i"   , "I");
						parametros.put("param13_pv_swexiper_i" , aseg.get("swexiper"));
						storedProceduresManager.procedureVoidCall(ObjetoBD.MOV_MPOLIPER.getNombre(), parametros, null);
					}
					catch(Exception ex)
					{
						logger.error("error al insertar mpoliper "+aseg,ex);
						error   = ex.getMessage();
						success = false;
					}
				}
				
				/**
				 * SOLO PARA EL CONTRATANTE
				 */
				if("1".equalsIgnoreCase(aseg.get("cdrol")))
				{
					String cdIdeperAseg = aseg.get("cdideper");
					/**
					 * En caso  de que si tenga un cdIdeper y el domicilio aun no este guardado en BD, se buscan los datos en el WS y se insertan en BD
					 */
					if(StringUtils.isNotBlank(cdIdeperAseg) && !"0".equalsIgnoreCase(cdIdeperAseg) && !"0L".equalsIgnoreCase(cdIdeperAseg)){
						try{
							WrapperResultados result = kernelManager.existeDomicilioContratante(cdIdeperAseg, aseg.get("cdperson"));
							
							if(result != null && result.getItemMap() != null && result.getItemMap().containsKey("EXISTE_DOMICILIO")){
								if(StringUtils.isBlank((String)result.getItemMap().get("EXISTE_DOMICILIO")) || !Constantes.SI.equalsIgnoreCase((String)result.getItemMap().get("EXISTE_DOMICILIO"))){
									/**
									 *  Si no existe Domicilio, Se va al WS por la informacion del mismo
									 */
									HashMap<String, Object> parametros =  new HashMap<String, Object>();
									parametros.put("pv_cdunieco_i", cdunieco);
									parametros.put("pv_cdramo_i",   cdramo);
									parametros.put("pv_estado_i",   estado);
									parametros.put("pv_nmpoliza_i", nmpoliza);
									
							    	String cdtipsitGS = kernelManager.obtenCdtipsitGS(parametros);
							    	
							    	ClienteGeneral clienteGeneral = new ClienteGeneral();
							    	//clienteGeneral.setRfcCli((String)aseg.get("cdrfc"));
							    	clienteGeneral.setRamoCli(Integer.parseInt(cdtipsitGS));
							    	clienteGeneral.setNumeroExterno(cdIdeperAseg);
							    	
							    	ClienteGeneralRespuesta clientesRes = ice2sigsService.ejecutaWSclienteGeneral(null, null, null, null, null, null, null, Ice2sigsService.Operacion.CONSULTA_GENERAL, clienteGeneral, null, false);
							    	
							    	if(clientesRes !=null && ArrayUtils.isNotEmpty(clientesRes.getClientesGeneral())){
							    		ClienteGeneral cliDom = null;
							    		
							    		if(clientesRes.getClientesGeneral().length == 1){
							    			logger.debug("Cliente unico encontrado en WS, guardando informacion del WS...");
							    			cliDom = clientesRes.getClientesGeneral()[0];
							    		}else {
							    			logger.error("Error, No se pudo obtener el domicilio del WS. Se ha encontrado mas de Un Domicilio!");
							    		}
							    		
							    		/*Cuando se encontraba el cliente de una lista
							    		 * for(ClienteGeneral cliIter : clientesRes.getClientesGeneral()){
							    			if(cdIdeperAseg.equalsIgnoreCase(cliIter.getNumeroExterno())){
							    				cliDom = cliIter;
							    			}
							    		}*/
							    		
							    		if(cliDom != null){
							    			HashMap<String,String> paramDomicil = new HashMap<String, String>();
							    			paramDomicil.put("pv_cdperson_i", (String)aseg.get("cdperson"));
							    			paramDomicil.put("pv_nmorddom_i", "1");
							    			paramDomicil.put("pv_msdomici_i", cliDom.getCalleCli());
							    			paramDomicil.put("pv_nmtelefo_i", cliDom.getTelefonoCli());
							    			paramDomicil.put("pv_cdpostal_i", cliDom.getCodposCli());
							    			
							    			String edoAdosPos = Integer.toString(cliDom.getEstadoCli());
							    			if(edoAdosPos.length() ==  1){
							    				edoAdosPos = "0"+edoAdosPos;
							    			}
							    			
							    			paramDomicil.put("pv_cdedo_i",    cliDom.getCodposCli()+edoAdosPos);
							    			paramDomicil.put("pv_cdmunici_i", null/*cliDom.getMunicipioCli()*/);
							    			paramDomicil.put("pv_cdcoloni_i", null/*cliDom.getColoniaCli()*/);
							    			paramDomicil.put("pv_nmnumero_i", cliDom.getNumeroCli());
							    			paramDomicil.put("pv_nmnumint_i", null);
							    			paramDomicil.put("pv_accion_i", "I");

							    			kernelManager.pMovMdomicil(paramDomicil);
							    			
							    			HashMap<String,String> paramValoper = new HashMap<String, String>();
							    			paramValoper.put("pv_cdunieco", "0");
							    			paramValoper.put("pv_cdramo",   "0");
							    			paramValoper.put("pv_estado",   null);
							    			paramValoper.put("pv_nmpoliza", "0");
							    			paramValoper.put("pv_nmsituac", null);
							    			paramValoper.put("pv_nmsuplem", null);
							    			paramValoper.put("pv_status",   null);
							    			paramValoper.put("pv_cdrol",    "1");
							    			paramValoper.put("pv_cdperson", (String)aseg.get("cdperson"));
							    			paramValoper.put("pv_cdatribu", null);
							    			paramValoper.put("pv_cdtipsit", null);
							    			
							    			paramValoper.put("pv_otvalor01", cliDom.getCveEle());
							    			paramValoper.put("pv_otvalor02", cliDom.getPasaporteCli());
							    			paramValoper.put("pv_otvalor03", null);
							    			paramValoper.put("pv_otvalor04", null);
							    			paramValoper.put("pv_otvalor05", null);
							    			paramValoper.put("pv_otvalor06", null);
							    			paramValoper.put("pv_otvalor07", null);
							    			paramValoper.put("pv_otvalor08", cliDom.getOrirecCli());
							    			paramValoper.put("pv_otvalor09", null);
							    			paramValoper.put("pv_otvalor10", null);
							    			paramValoper.put("pv_otvalor11", cliDom.getNacCli());
							    			paramValoper.put("pv_otvalor12", null);
							    			paramValoper.put("pv_otvalor13", null);
							    			paramValoper.put("pv_otvalor14", null);
							    			paramValoper.put("pv_otvalor15", null);
							    			paramValoper.put("pv_otvalor16", null);
							    			paramValoper.put("pv_otvalor17", null);
							    			paramValoper.put("pv_otvalor18", null);
							    			paramValoper.put("pv_otvalor19", null);
							    			paramValoper.put("pv_otvalor20", (cliDom.getOcuPro() > 0) ? Integer.toString(cliDom.getOcuPro()) : "0");
							    			paramValoper.put("pv_otvalor21", null);
							    			paramValoper.put("pv_otvalor22", null);
							    			paramValoper.put("pv_otvalor23", null);
							    			paramValoper.put("pv_otvalor24", null);
							    			paramValoper.put("pv_otvalor25", cliDom.getCurpCli());
							    			paramValoper.put("pv_otvalor26", null);
							    			paramValoper.put("pv_otvalor27", null);
							    			paramValoper.put("pv_otvalor28", null);
							    			paramValoper.put("pv_otvalor29", null);
							    			paramValoper.put("pv_otvalor30", null);
							    			paramValoper.put("pv_otvalor31", null);
							    			paramValoper.put("pv_otvalor32", null);
							    			paramValoper.put("pv_otvalor33", null);
							    			paramValoper.put("pv_otvalor34", null);
							    			paramValoper.put("pv_otvalor35", null);
							    			paramValoper.put("pv_otvalor36", null);
							    			paramValoper.put("pv_otvalor37", null);
							    			paramValoper.put("pv_otvalor38", cliDom.getTelefonoCli());
							    			paramValoper.put("pv_otvalor39", cliDom.getMailCli());
							    			paramValoper.put("pv_otvalor40", null);
							    			paramValoper.put("pv_otvalor41", null);
							    			paramValoper.put("pv_otvalor42", null);
							    			paramValoper.put("pv_otvalor43", null);
							    			paramValoper.put("pv_otvalor44", null);
							    			paramValoper.put("pv_otvalor45", null);
							    			paramValoper.put("pv_otvalor46", null);
							    			paramValoper.put("pv_otvalor47", null);
							    			paramValoper.put("pv_otvalor48", null);
							    			paramValoper.put("pv_otvalor49", null);
							    			paramValoper.put("pv_otvalor50", null);
							    			
							    			kernelManager.pMovTvaloper(paramValoper);
							    			
							    		}else{
							    			logger.error("Error. Cliente no encontrado en WS !");
							    		}
							    	}else{
							    		logger.error("Error, No se pudo obtener el domicilio del WS.");
							    	}
							    	
								}else{
									logger.debug("Ya Existe el domicilio del cdiper.");
								}
							}else{
								logger.error("Error al verificar si hay datos en mdomicil!!");
							}
						}catch(Exception eWS){
							logger.error("Error en obtencion de Domicilio para contratante.",eWS);
						}
					}
					
				}
			}
		}
		
		logger.info("slist1: "+slist1);
		logger.info(""
				+ "\n###### guardarSituacionesAuto ######"
				+ "\n####################################"
				);
		return SUCCESS;
	}
	
	public String pantallaCotizacionGrupo2()
	{
		logger.info(
				new StringBuilder()
				.append("\n######################################")
				.append("\n###### pantallaCotizacionGrupo2 ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		success = true;
		exito   = true;
		
		String cdramo        = null;
		String cdtipsit      = null;
		String ntramite      = null;
		String ntramiteVacio = null;
		String status        = null;
		String cdusuari      = null;
		String cdsisrol      = null;
		String nombreUsuario = null;
		String cdagente      = null;
		
		//datos completos
		try
		{
			if(smap1==null)
			{
				throw new ApplicationException("No se recibieron datos");
			}
			cdramo   = smap1.get("cdramo");
			cdtipsit = smap1.get("cdtipsit");
			if(StringUtils.isBlank(cdramo))
			{
				throw new ApplicationException("No se recibio el ramo");
			}
			if(StringUtils.isBlank(cdtipsit))
			{
				throw new ApplicationException("No se recibio la modalidad");
			}
			ntramite      = smap1.get("ntramite");
			ntramiteVacio = smap1.get("ntramiteVacio");
			status        = smap1.get("status");
			cdagente      = smap1.get("cdagente");
			if(StringUtils.isBlank(status))
			{
				status = "0";
			}
			
			if(session==null)
			{
				throw new ApplicationException("No hay sesion");
			}
			UserVO usuario = (UserVO)session.get("USUARIO");
			if(usuario==null)
			{
				throw new ApplicationException("No hay usuario en la sesion");
			}
			cdusuari      = usuario.getUser();
			cdsisrol      = usuario.getRolActivo().getClave();
			nombreUsuario = usuario.getName();
			
			smap1.put("cdsisrol" , cdsisrol);
		}
		catch(ApplicationException ax)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString();
			respuestaOculta = ax.getMessage();
			logger.error(respuesta,ax);
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder("Error al validar datos #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		//proceso
		if(exito)
		{
			ManagerRespuestaImapSmapVO resp = cotizacionManager.pantallaCotizacionGrupo(
					cdramo
					,cdtipsit
					,ntramite
					,ntramiteVacio
					,status
					,cdusuari
					,cdsisrol
					,nombreUsuario
					,cdagente
					);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			if(exito)
			{
				smap1.putAll(resp.getSmap());
				imap=resp.getImap();
			}
		}
		
		String result = SUCCESS;
		if(!exito)
		{
			result = ERROR;
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### result=").append(result)
				.append("\n###### pantallaCotizacionGrupo2 ######")
				.append("\n######################################")
				.toString()
				);
		return result;
	}
	
	public String pantallaCotizacionGrupo()
	{
		logger.info(
				new StringBuilder()
				.append("\n#####################################")
				.append("\n###### pantallaCotizacionGrupo ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		success = true;
		exito   = true;
		imap    = new HashMap<String,Item>();
		
		String cdramo        = null;
		String cdtipsit      = null;
		String ntramite      = null;
		String ntramiteVacio = null;
		String status        = null;
		
		//datos completos
		if(exito)
		{
			try
			{
				cdramo        = smap1.get("cdramo");
				cdtipsit      = smap1.get("cdtipsit");
				if(StringUtils.isBlank(cdramo))
				{
					throw new Exception("No hay cdramo");
				}
				if(StringUtils.isBlank(cdtipsit))
				{
					throw new Exception("No hay cdtipsit");
				}
				ntramite      = smap1.get("ntramite");
				ntramiteVacio = smap1.get("ntramiteVacio");
				status        = smap1.get("status");
				if(StringUtils.isBlank(status))
				{
					status = "0";
				}
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = new StringBuilder().append("Datos incompletos #").append(timestamp).toString();
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		String cdusuari      = null;
		String cdsisrol      = null;
		String nombreUsuario = null;
		
		//datos sesion
		if(exito)
		{
			try
			{
				UserVO usuario  = (UserVO) session.get("USUARIO");
				cdusuari        = usuario.getUser();
				cdsisrol        = usuario.getRolActivo().getClave();
				nombreUsuario   = usuario.getName();
				
				smap1.put("cdsisrol",cdsisrol);
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = new StringBuilder().append("Error al obtener datos del usuario #").append(timestamp).toString();
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}

		String nombreAgente  = null;
		String cdAgente      = null;
		
		//datos del agente
		if(exito)
		{
			try
			{
				//si entran por agente
				if(StringUtils.isBlank(ntramite)&&StringUtils.isBlank(ntramiteVacio))
				{
					DatosUsuario datUsu=kernelManager.obtenerDatosUsuario(cdusuari,cdtipsit);
	        		String cdunieco = datUsu.getCdunieco();
	        		smap1.put("cdunieco",cdunieco);
	        		
	        		cdAgente     = datUsu.getCdagente();
	        		nombreAgente = nombreUsuario;
				}
				//si entran por tramite o tramite vacio
				else if(StringUtils.isNotBlank(ntramite)||StringUtils.isNotBlank(ntramiteVacio))
				{
					cdAgente     = smap1.get("cdagente");
					nombreAgente = cotizacionManager.cargarNombreAgenteTramite(StringUtils.isNotBlank(ntramite)?ntramite:ntramiteVacio);
				}
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = new StringBuilder().append("Error al obtener datos del agente #").append(timestamp).toString();
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
			
		GeneradorCampos gc = null;
		
		//generar componentes
		if(exito)
		{
			try
			{
				gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				
				List<ComponenteVO>columnaEditorPlan=pantallasManager.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "EDITOR_PLANES", null);
				gc.generaComponentes(columnaEditorPlan, true, false, false, true, true, false);
				imap.put("editorPlanesColumn",gc.getColumns());
				
				List<ComponenteVO>columnaEditorSumaAseg=pantallasManager.obtenerComponentes(
						null, null, cdramo,
						null, null, null,
						"COTIZACION_GRUPO", "EDITOR_SUMA_ASEG", null);
				gc.generaComponentes(columnaEditorSumaAseg, true, false, false, true, true, false);
				imap.put("editorSumaAsegColumn",gc.getColumns());
				
				List<ComponenteVO>columnaEditorPAquete=pantallasManager.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "EDITOR_PAQUETE", null);
				gc.generaComponentes(columnaEditorPAquete, true, false, false, true, true, false);
				imap.put("editorPaqueteColumn",gc.getColumns());
				
				List<ComponenteVO>columnaEditorAyudaMater=pantallasManager.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "EDITOR_AYUDAMATER", null);
				gc.generaComponentes(columnaEditorAyudaMater, true, false, false, true, true, false);
				imap.put("editorAyudaMaterColumn",gc.getColumns());
				
				List<ComponenteVO>columnaEditorAsisInterMater=pantallasManager.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "EDITOR_ASISINTE", null);
				gc.generaComponentes(columnaEditorAsisInterMater, true, false, false, true, true, false);
				imap.put("editorAsisInterColumn",gc.getColumns());
				
				List<ComponenteVO>columnaEditorEmerextr=pantallasManager.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "EDITOR_EMEREXTR", null);
				gc.generaComponentes(columnaEditorEmerextr, true, false, false, true, true, false);
				imap.put("editorEmerextrColumn",gc.getColumns());
				
				List<ComponenteVO>columnaEditorDeducible=pantallasManager.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "EDITOR_DEDUCIBLE", null);
				gc.generaComponentes(columnaEditorDeducible, true, false, false, true, true, false);
				imap.put("editorDeducibleColumn",gc.getColumns());
				
				List<ComponenteVO>componentesContratante=pantallasManager.obtenerComponentes(
						null               , "|"+cdramo+"|" , "|"+status+"|" ,
						null               , null           , cdsisrol       ,
						"COTIZACION_GRUPO" , "CONTRATANTE"  , null);
				gc.generaComponentes(componentesContratante, true,true,true,false,false,false);
				imap.put("itemsContratante"  , gc.getItems());
				imap.put("fieldsContratante" , gc.getFields());
				
				List<ComponenteVO>componentesRiesgo=pantallasManager.obtenerComponentes(
						null, null, null,
						null, null, cdsisrol,
						"COTIZACION_GRUPO", "RIESGO", null);
				gc.generaComponentes(componentesRiesgo, true,true,true,false,false,false);
				imap.put("itemsRiesgo"  , gc.getItems());
				imap.put("fieldsRiesgo" , gc.getFields());
				
				List<ComponenteVO>componentesAgente=pantallasManager.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "AGENTE", null);
				componentesAgente.get(0).setDefaultValue(nombreAgente);
				componentesAgente.get(1).setDefaultValue(cdAgente);
				gc.generaComponentes(componentesAgente, true,false,true,false,false,false);
				imap.put("itemsAgente"  , gc.getItems());
				
				List<ComponenteVO>comboFormaPago=pantallasManager.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "COMBO_FORMA_PAGO", null);
				gc.generaComponentes(comboFormaPago, true,false,true,false,false,false);
				imap.put("comboFormaPago"  , gc.getItems());
				
				List<ComponenteVO>comboRepartoPago=pantallasManager.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "COMBO_REPARTO_PAGO", null);
				gc.generaComponentes(comboRepartoPago, true,false,true,false,false,false);
				imap.put("comboRepartoPago"  , gc.getItems());
				
				List<ComponenteVO>comboPool = pantallasManager.obtenerComponentes(
						null, null, null,
						null, null, cdsisrol,
						"COTIZACION_GRUPO", "COMBO_POOL", null);
				gc.generaComponentes(comboPool, true,false,true,false,false,false);
				imap.put("comboPool"  , gc.getItems());
				
				List<ComponenteVO>datosPoliza = pantallasManager.obtenerComponentes(
						null, null, null,
						null, null, cdsisrol,
						"COTIZACION_GRUPO", "DATOS_POLIZA", null);
				gc.generaComponentes(datosPoliza, true,false,true,false,false,false);
				imap.put("datosPoliza"  , gc.getItems());
				
				List<ComponenteVO>botones=pantallasManager.obtenerComponentes(
						null, null, "|"+status+"|",
						null, null, cdsisrol,
						"COTIZACION_GRUPO", "BOTONES", null);
				if(botones!=null&&botones.size()>0)
				{
					gc.generaComponentes(botones, true, false, false, false, false, true);
					imap.put("botones" , gc.getButtons());
				}
				else
				{
					imap.put("botones" , null);
				}
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = new StringBuilder().append("Error al generar componentes #").append(timestamp).toString();
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		//obtener permisos
		if(exito)
		{
			try
			{
				smap1.put("status",status);
				smap1.putAll(cotizacionManager.cargarPermisosPantallaGrupo(cdsisrol, status));
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = new StringBuilder().append("Error al obtener permisos #").append(timestamp).toString();
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		//obtener configuraciones
		/*
		if(exito)
		{
			try
			{
				List<Map<String,String>>cols=cotizacionManager.cargarConfiguracionGrupo(cdramo, cdtipsit);
				
				List<ComponenteVO>compsColBase   = new ArrayList<ComponenteVO>();
				List<ComponenteVO>compsColNoBase = new ArrayList<ComponenteVO>();
				
				Item baseCols     = new Item("basecols"    ,null,Item.ARR);
				Item noBaseCols   = new Item("nobasecols"  ,null,Item.ARR);
				Item baseFields   = new Item("basefields"  ,null,Item.ARR);
				Item noBaseFields = new Item("nobasefields",null,Item.ARR);
				
				for(Map<String,String>iCol:cols)
				{
					String tipo   = iCol.get("TIPO");
					String base   = iCol.get("BASE");
					String nombre = iCol.get("NOMBRE");
					if(StringUtils.isBlank(nombre))
					{
						throw new ApplicationException(
								new StringBuilder()
								.append("El componente no tiene nombre")
								.toString()
								);
					}
					if(StringUtils.isBlank(tipo))
					{
						throw new ApplicationException(
								new StringBuilder()
								.append("El componente no tiene tipo ")
								.append(nombre)
								.toString()
								);
					}
					if(StringUtils.isBlank(base))
					{
						throw new ApplicationException(
								new StringBuilder()
								.append("El componente no tiene base ")
								.append(nombre)
								.toString()
								);
					}
					ComponenteVO comp = null;
					if(tipo.equals(ConfiguracionGrupo.TIPO_ATRIBUTO)
							||tipo.equals(ConfiguracionGrupo.TIPO_DOBLE)
							)
					{
						String cdatrisit = iCol.get("CDATRISIT");
						if(StringUtils.isBlank(cdatrisit))
						{
							throw new ApplicationException(
									new StringBuilder()
									.append("El componente no esta ligado a un atributo de situacion  ")
									.append(nombre)
									.toString()
									);
						}
						comp=cotizacionManager.cargarComponenteTatrisit(cdtipsit,cdusuari,cdatrisit);
						comp.setColumna(Constantes.SI);
						
						GeneradorCampos gec=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
						gec.setCdtipsit(cdtipsit);
						List<ComponenteVO>aux=new ArrayList<ComponenteVO>();
						aux.add(comp);
						gec.generaComponentes(aux, true, true, false, true, true, false);
						if(base.equals(Constantes.SI))
						{
							baseFields.add(new Item(null,gec.getFields(),Item.OBJ));
							baseCols.add(new Item(null,gec.getColumns(),Item.OBJ));
							compsColBase.add(comp);
						}
						else
						{
							noBaseFields.add(new Item(null,gec.getFields(),Item.OBJ));
							noBaseCols.add(new Item(null,gec.getColumns(),Item.OBJ));
							compsColNoBase.add(comp);
						}
					}
					else if(tipo.equals(ConfiguracionGrupo.TIPO_COBERTURA))
					{
						String tipogar = iCol.get("TIPOGAR");
						if(StringUtils.isBlank(tipogar))
						{
							throw new ApplicationException(
									new StringBuilder()
									.append("El componente no tiene tipo de cobertura ")
									.append(nombre)
									.toString()
									);
						}
						if(tipogar.equals(ConfiguracionGrupo.TIPOGAR_VALOR))
						{
							String cdgarant  = iCol.get("CDGARANT");
							String cdatrigar = iCol.get("CDATRIGAR");
							if(StringUtils.isBlank(cdgarant))
							{
								throw new ApplicationException(
										new StringBuilder()
										.append("El componente no tiene codigo de cobertura ")
										.append(nombre)
										.toString()
										);
							}
							if(StringUtils.isBlank(cdatrigar))
							{
								throw new ApplicationException(
										new StringBuilder()
										.append("El componente no tiene codigo de atributo ")
										.append(nombre)
										.toString()
										);
							}
							comp=cotizacionManager.cargarComponenteTatrigar(cdramo,cdtipsit,cdgarant,cdatrigar);
							comp.setColumna(Constantes.SI);
							
							GeneradorCampos gec=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
							gec.setCdtipsit(cdtipsit);
							gec.setCdgarant(cdgarant);
							gec.setCdramo(cdramo);
							List<ComponenteVO>aux=new ArrayList<ComponenteVO>();
							aux.add(comp);
							gec.generaComponentes(aux, true, true, false, true, true, false);
							if(base.equals(Constantes.SI))
							{
								baseFields.add(new Item(null,gec.getFields(),Item.OBJ));
								baseCols.add(new Item(null,gec.getColumns(),Item.OBJ));
								compsColBase.add(comp);
							}
							else
							{
								noBaseFields.add(new Item(null,gec.getFields(),Item.OBJ));
								noBaseCols.add(new Item(null,gec.getColumns(),Item.OBJ));
								compsColNoBase.add(comp);
							}
						}
						else if(tipogar.equals(ConfiguracionGrupo.TIPOGAR_INSERCION))
						{
							String etiqueta = iCol.get("ETIQUETA");
							if(StringUtils.isBlank(etiqueta))
							{
								throw new ApplicationException(
										new StringBuilder()
										.append("El componente no tiene etiqueta ")
										.append(nombre)
										.toString()
										);
							}
							List<ComponenteVO>listaAux=pantallasManager.obtenerComponentes(
									null                , null         , null
									,null               , null         , null
									,"COTIZACION_GRUPO" , "COMBO_SINO" , null);
							comp=listaAux.get(0);
							comp.setLabel(etiqueta);
							comp.setColumna(Constantes.SI);
							
							GeneradorCampos gec=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
							List<ComponenteVO>aux=new ArrayList<ComponenteVO>();
							aux.add(comp);
							gec.generaComponentes(aux, true, true, false, true, true, false);
							if(base.equals(Constantes.SI))
							{
								baseFields.add(new Item(null,gec.getFields(),Item.OBJ));
								baseCols.add(new Item(null,gec.getColumns(),Item.OBJ));
								compsColBase.add(comp);
							}
							else
							{
								noBaseFields.add(new Item(null,gec.getFields(),Item.OBJ));
								noBaseCols.add(new Item(null,gec.getColumns(),Item.OBJ));
								compsColNoBase.add(comp);
							}
						}
						else
						{
							throw new ApplicationException(
									new StringBuilder()
									.append("El tipo de cobertura no es correcto ")
									.append(nombre)
									.toString()
									);
						}
					}
					else
					{
						throw new ApplicationException(
								new StringBuilder()
								.append("El tipo de componente no es correcto ")
								.append(nombre)
								.toString()
								);
					}
				}
				
				if(compsColBase.size()>0)
				{
					gc.generaComponentes(compsColBase, true, true, false, true, true, false);
					imap2.put("colsBaseFields"  , baseFields);
					imap2.put("colsBaseColumns" , baseCols);
				}
				else
				{
					imap2.put("colsBaseFields"  , null);
					imap2.put("colsBaseColumns" , null);
				}
				
				if(compsColNoBase.size()>0)
				{
					gc.generaComponentes(compsColNoBase, true, true, false, true, true, false);
					imap2.put("colsNoBaseFields"  , noBaseFields);
					imap2.put("colsNoBaseColumns" , noBaseCols);
				}
				else
				{
					imap2.put("colsNoBaseFields"  , null);
					imap2.put("colsNoBaseColumns" , null);
				}
				
				logger.debug(
						new StringBuilder()
						.append("\nColumnas base=").append(compsColBase)
						.append("\nColumnas no base=").append(compsColNoBase)
						.toString()
						);
			}
			catch(ApplicationException aex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = new StringBuilder().append(aex.getMessage()).append(" #").append(timestamp).toString();
				respuestaOculta = aex.getMessage();
				logger.error(respuesta,aex);
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = new StringBuilder().append("Error al obtener configuraciones #").append(timestamp).toString();
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		*/
		
		//campos para asegurados
		if(exito && smap1.containsKey("ASEGURADOS")
				 && StringUtils.isNotBlank(smap1.get("ASEGURADOS"))
				 && smap1.get("ASEGURADOS").equals("S"))
		{
			try
			{
				List<ComponenteVO>componentesExtraprimas=pantallasManager.obtenerComponentes(
						null  , null , null
						,null , null , cdsisrol
						,"COTIZACION_GRUPO", "ASEGURADOS", null);
				gc.generaComponentes(componentesExtraprimas, true, true, false, true, false, false);
				imap.put("aseguradosColumns" , gc.getColumns());
				imap.put("aseguradosFields"  , gc.getFields());
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = new StringBuilder().append("Error al obtener componentes de asegurados #").append(timestamp).toString();
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		//campos para extraprimas
		if(exito && smap1.containsKey("EXTRAPRIMAS")
				 && StringUtils.isNotBlank(smap1.get("EXTRAPRIMAS"))
				 && smap1.get("EXTRAPRIMAS").equals("S"))
		{
			try
			{
				List<ComponenteVO>componentesExtraprimas=pantallasManager.obtenerComponentes(
						null  , null , null
						,null , null , cdsisrol
						,"COTIZACION_GRUPO", "EXTRAPRIMAS", null);
				gc.generaComponentes(componentesExtraprimas, true, true, false, true, true, false);
				imap.put("extraprimasColumns" , gc.getColumns());
				imap.put("extraprimasFields"  , gc.getFields());
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = new StringBuilder().append("Error al obtener componentes de extraprimas #").append(timestamp).toString();
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		//campos para recuperados (asegurados)
		if(exito && smap1.containsKey("ASEGURADOS_EDITAR")
				 && StringUtils.isNotBlank(smap1.get("ASEGURADOS_EDITAR"))
				 && smap1.get("ASEGURADOS_EDITAR").equals("S"))
		{
			try
			{
				List<ComponenteVO>componentesRecuperados=pantallasManager.obtenerComponentes(
						null  , null , null
						,null , null , cdsisrol
						,"COTIZACION_GRUPO", "RECUPERADOS", null);
				gc.generaComponentes(componentesRecuperados, true, true, false, true, true, false);
				imap.put("recuperadosColumns" , gc.getColumns());
				imap.put("recuperadosFields"  , gc.getFields());
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = new StringBuilder().append("Error al obtener componentes de recuperados #").append(timestamp).toString();
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		if(exito)
		{
			respuesta       = "Todo OK";
			respuestaOculta = "Todo OK";
		}
		
		String result = SUCCESS;
		if(!exito)
		{
			result = ERROR;
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### result=").append(result)
				.append("\n###### pantallaCotizacionGrupo ######")
				.append("\n#####################################")
				.toString()
				);
		return result;
	}
	
	public String obtenerCoberturasPlan()
	{
		logger.info(""
				+ "\n###################################"
				+ "\n###### obtenerCoberturasPlan ######"
				+ "\nsmap1: "+smap1
				);
		try
		{
			success = true;
			exito   = true;
			LinkedHashMap<String,Object>params=new LinkedHashMap<String,Object>();
			params.put("param1" , smap1.get("cdramo"));
			params.put("param2" , smap1.get("cdtipsit"));
			params.put("param3" , smap1.get("cdplan"));
			slist1=storedProceduresManager.procedureListCall(ObjetoBD.OBTIENE_COBERTURAS_X_PLAN.getNombre(), params, null);
		}
		catch(Exception ex)
		{
			long timestamp=System.currentTimeMillis();
			logger.error(timestamp+" error al obtener coberturas plan");
			respuesta       = "Error inesperado #"+timestamp;
			respuestaOculta = ex.getMessage();
			exito           = false;
		}
		logger.info(""
				+ "\n###### obtenerCoberturasPlan ######"
				+ "\n###################################"
				);
		return SUCCESS;
	}
	
	public String obtenerTatrigarCoberturas()
	{
		logger.info(""
				+ "\n#######################################"
				+ "\n###### obtenerTatrigarCoberturas ######"
				+ "\nsmap1: "+smap1
				);
		try
		{
			success = true;
			exito   = true;
			Map<String,String>params=new HashMap<String,String>();
			params.put("pv_cdramo_i"    , smap1.get("cdramo"));
			params.put("pv_cdtipsit_i"  , smap1.get("cdtipsit"));
			params.put("pv_cdgarant_i"  , smap1.get("cdgarant"));
			params.put("pv_cdatrivar_i" , smap1.get("cdatrivar"));
			List<ComponenteVO>componentesTatrigar=kernelManager.obtenerTatrigar(params);
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.setCdramo(smap1.get("cdramo"));
			gc.setCdtipsit(smap1.get("cdtipsit"));
			gc.setCdgarant(smap1.get("cdgarant"));
			gc.generaComponentes(componentesTatrigar, false, true, true, false, false, false);
			respuesta       = gc.getItems().toString();
			respuestaOculta = gc.getFields().toString();
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			logger.error(timestamp+" error al obtener tatrigar de coberturas",ex);
			respuesta       = "Error inesperado #"+timestamp;
			respuestaOculta = ex.getMessage();
			exito           = false;
		}
		logger.info(""
				+ "\n###### obtenerTatrigarCoberturas ######"
				+ "\n#######################################"
				);
		return SUCCESS;
	}
	
	public String subirCenso()
	{
		logger.info(""
				+ "\n########################"
				+ "\n###### subirCenso ######"
				+ "\n censo "+censo+""
				+ "\n censoFileName "+censoFileName+""
				+ "\n censoContentType "+censoContentType+""
				+ "\n smap1 "+smap1
				);
		
		success = true;
		exito   = true;
		
		String ntramite=smap1.get("ntramite");
		if(StringUtils.isBlank(ntramite))
		{
			String timestamp = smap1.get("timestamp");
			//censo.renameTo(new File(this.getText("ruta.documentos.temporal")+"/censo_"+timestamp));
			try {
            	FileUtils.copyFile(censo, new File(this.getText("ruta.documentos.temporal")+"/censo_"+timestamp));
            	logger.info("archivo movido");
			} catch (Exception e) {
				logger.error("archivo NO movido", e);
			}
			
			logger.info("censo renamed to: "+this.getText("ruta.documentos.temporal")+"/censo_"+timestamp);
		}
		
		logger.info(""
				+ "\n###### subirCenso ######"
				+ "\n########################"
				);
		return SUCCESS;
	}
	
	public String subirCensoCompleto2()
	{
		this.session=ActionContext.getContext().getSession();
		logger.info(
				new StringBuilder()
				.append("\n#################################")
				.append("\n###### subirCensoCompleto2 ######")
				.append("\n###### smap1=").append(smap1)
				.append("\n###### olist1=").append(olist1)
				.toString()
				);
		
		exito   = true;
		success = true;
		
		String censoTimestamp  = null;
		String clasif          = null;
		String LINEA_EXTENDIDA = null;
		String cdunieco        = null;
		String cdramo          = null;
		String cdtipsit        = null;
		String nmpoliza        = null;
		String cdperpag        = null;
		String pcpgocte        = null;
		String cdusuari        = null;
		String cdelemen        = null;
		String rutaDocsTemp    = null;
		String feini           = null;
		String fefin           = null;
		String cdsisrol        = null;
		String cdagente        = null;
		String codpostalCli    = null;
		String cdedoCli        = null;
		String cdmuniciCli     = null;
		String ntramite        = null;
		String ntramiteVacio   = null;
		String cdpersonCli     = null;
		String nombreCli       = null;
		String rfcCli          = null;
		String dsdomiciCli     = null;
		String nmnumeroCli     = null;
		String nmnumintCli     = null;
		String cdideper_       = null;
		String cdideext_       = null;
		String nmpolant        = null;
		String nmrenova        = null;
		
		String nombreCensoConfirmado = null;
		
		//datos completos
		try
		{
			if(smap1==null)
			{
				throw new ApplicationException("No se recibieron datos");
			}
			censoTimestamp  = smap1.get("timestamp");
			clasif          = smap1.get("clasif");
			LINEA_EXTENDIDA = smap1.get("LINEA_EXTENDIDA");
			cdunieco        = smap1.get("cdunieco");
			cdramo          = smap1.get("cdramo");
			cdtipsit        = smap1.get("cdtipsit");
			nmpoliza        = smap1.get("nmpoliza");
			cdperpag        = smap1.get("cdperpag");
			pcpgocte        = smap1.get("pcpgocte");
			feini           = smap1.get("feini");
			fefin           = smap1.get("fefin");
			cdagente        = smap1.get("cdagente");
			codpostalCli    = smap1.get("codpostal");
			cdedoCli        = smap1.get("cdedo");
			cdmuniciCli     = smap1.get("cdmunici");
			ntramite        = smap1.get("ntramite");
			ntramiteVacio   = smap1.get("ntramiteVacio");
			cdpersonCli     = smap1.get("cdperson");
			nombreCli       = smap1.get("nombre");
			rfcCli          = smap1.get("cdrfc");
			dsdomiciCli     = smap1.get("dsdomici");
			nmnumeroCli     = smap1.get("nmnumero");
			nmnumintCli     = smap1.get("nmnumint");
			cdideper_       = smap1.get("cdideper_");
			cdideext_       = smap1.get("cdideext_");
			nmpolant        = smap1.get("nmpolant");
			nmrenova        = smap1.get("nmrenova");
			
			nombreCensoConfirmado = smap1.get("nombreCensoConfirmado");
			
			if(session==null)
			{
				throw new ApplicationException("No hay sesion");
			}
			if(session.get("USUARIO")==null)
			{
				throw new ApplicationException("No usuario en la sesion");
			}
			UserVO user = (UserVO)session.get("USUARIO");
			cdusuari = user.getUser();
			cdelemen = user.getEmpresa().getElementoId();
			cdsisrol = user.getRolActivo().getClave();
			
			rutaDocsTemp = getText("ruta.documentos.temporal");
		}
		catch(ApplicationException ax)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString();
			respuestaOculta = ax.getMessage();
			logger.error(respuesta,ax);
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder("Error al validar datos #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		//proceso
		if(exito)
		{
			ManagerRespuestaSmapVO resp = cotizacionManager.subirCensoCompleto(
					cdunieco
					,cdramo
					,nmpoliza
					,feini
					,fefin
					,cdperpag
					,pcpgocte
					,rutaDocsTemp
					,censoTimestamp
					,getText("dominio.server.layouts")
					,getText("user.server.layouts")
					,getText("pass.server.layouts")
					,getText("directorio.server.layouts")
					,cdtipsit
					,cdusuari
					,cdsisrol
					,cdagente
					,codpostalCli
					,cdedoCli
					,cdmuniciCli
					,olist1
					,clasif
					,LINEA_EXTENDIDA
					,cdpersonCli
					,nombreCli
					,rfcCli
					,dsdomiciCli
					,nmnumeroCli
					,nmnumintCli
					,ntramite
					,ntramiteVacio
					,cdelemen
					,nombreCensoConfirmado
					,cdideper_
					,cdideext_
					,nmpolant
					,nmrenova
					);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			if(exito)
			{
				smap1.putAll(resp.getSmap());
			}
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### subirCensoCompleto2 ######")
				.append("\n#################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String subirCensoCompleto()
	{
		this.session=ActionContext.getContext().getSession();
		logger.info(Utils.log(
				 "\n################################"
				,"\n###### subirCensoCompleto ######"
				,"\n###### smap1="  , smap1
				,"\n###### olist1=" , olist1
				));
		
		success = true;
		exito   = true;
		
		String censoTimestamp   = smap1.get("timestamp");
		String clasif           = smap1.get("clasif");
		String LINEA_EXTENDIDA  = smap1.get("LINEA_EXTENDIDA");
		String cdunieco         = smap1.get("cdunieco");
		String cdtipsit         = smap1.get("cdtipsit");
		String cdramo           = smap1.get("cdramo");
		String nmpoliza         = smap1.get("nmpoliza");
		String cdperpag         = smap1.get("cdperpag");
		String pcpgocte         = smap1.get("pcpgocte");
		UserVO usuario          = (UserVO)session.get("USUARIO");
		String user             = usuario.getUser();
		String cdelemento       = usuario.getEmpresa().getElementoId();
		String cdsisrol         = usuario.getRolActivo().getClave();
		final String LINEA      = "1";
		String ntramite         = smap1.get("ntramite");
		boolean hayTramite      = StringUtils.isNotBlank(ntramite);
		String ntramiteVacio    = smap1.get("ntramiteVacio");
		boolean hayTramiteVacio = StringUtils.isNotBlank(ntramiteVacio);
		Date fechaHoy           = new Date();
		String feini            = smap1.get("feini");
		String fefin            = smap1.get("fefin");
		String nmpolant         = smap1.get("nmpolant");
		String nmrenova         = smap1.get("nmrenova");
		
		censo = new File(this.getText("ruta.documentos.temporal")+"/censo_"+censoTimestamp);
		
		String nombreCensoConfirmado = smap1.get("nombreCensoConfirmado");
		
		//mpolizas
		if(exito)
		{
			try
			{
				Map<String,String>mapaMpolizas=new HashMap<String,String>(0);
	            mapaMpolizas.put("pv_cdunieco"  , cdunieco);
	            mapaMpolizas.put("pv_cdramo"    , cdramo);
	            mapaMpolizas.put("pv_estado"    , "W");
	            mapaMpolizas.put("pv_nmpoliza"  , nmpoliza);
	            mapaMpolizas.put("pv_nmsuplem"  , "0");
	            mapaMpolizas.put("pv_status"    , "V");
	            mapaMpolizas.put("pv_swestado"  , "0");
	            mapaMpolizas.put("pv_nmsolici"  , null);
	            mapaMpolizas.put("pv_feautori"  , null);
	            mapaMpolizas.put("pv_cdmotanu"  , null);
	            mapaMpolizas.put("pv_feanulac"  , null);
	            mapaMpolizas.put("pv_swautori"  , "N");
	            mapaMpolizas.put("pv_cdmoneda"  , "001");
	            mapaMpolizas.put("pv_feinisus"  , null);
	            mapaMpolizas.put("pv_fefinsus"  , null);
	            mapaMpolizas.put("pv_ottempot"  , "R");
	            mapaMpolizas.put("pv_feefecto"  , feini);
	            mapaMpolizas.put("pv_hhefecto"  , "12:00");
	            mapaMpolizas.put("pv_feproren"  , fefin);
	            mapaMpolizas.put("pv_fevencim"  , null);
	            mapaMpolizas.put("pv_nmrenova"  , StringUtils.isBlank(nmrenova) ? "0" : nmrenova);
	            mapaMpolizas.put("pv_ferecibo"  , null);
	            mapaMpolizas.put("pv_feultsin"  , null);
	            mapaMpolizas.put("pv_nmnumsin"  , "0");
	            mapaMpolizas.put("pv_cdtipcoa"  , "N");
	            mapaMpolizas.put("pv_swtarifi"  , "A");
	            mapaMpolizas.put("pv_swabrido"  , null);
	            mapaMpolizas.put("pv_feemisio"  , renderFechas.format(fechaHoy));
	            mapaMpolizas.put("pv_cdperpag"  , cdperpag);
	            mapaMpolizas.put("pv_nmpoliex"  , null);
	            mapaMpolizas.put("pv_nmcuadro"  , "P1");
	            mapaMpolizas.put("pv_porredau"  , "100");
	            mapaMpolizas.put("pv_swconsol"  , "S");
	            mapaMpolizas.put("pv_nmpolant"  , nmpolant);
	            mapaMpolizas.put("pv_nmpolnva"  , null);
	            mapaMpolizas.put("pv_fesolici"  , renderFechas.format(fechaHoy));
	            mapaMpolizas.put("pv_cdramant"  , null);
	            mapaMpolizas.put("pv_cdmejred"  , null);
	            mapaMpolizas.put("pv_nmpoldoc"  , null);
	            mapaMpolizas.put("pv_nmpoliza2" , null);
	            mapaMpolizas.put("pv_nmrenove"  , null);
	            mapaMpolizas.put("pv_nmsuplee"  , null);
	            mapaMpolizas.put("pv_ttipcamc"  , null);
	            mapaMpolizas.put("pv_ttipcamv"  , null);
	            mapaMpolizas.put("pv_swpatent"  , null);
	            mapaMpolizas.put("pv_pcpgocte"  , pcpgocte);
	            mapaMpolizas.put("pv_tipoflot"  , "F");
	            mapaMpolizas.put("pv_agrupador" , null);
	            mapaMpolizas.put("pv_accion"    , "U");
	            kernelManager.insertaMaestroPolizas(mapaMpolizas);
			}
			catch(Exception ex)
			{
				long etimestamp = System.currentTimeMillis();
				logger.error(etimestamp+" error mpolizas",ex);
				respuesta       = "Error al cotizar #"+etimestamp;
				respuestaOculta = ex.getMessage();
				exito           = false;
			}
		}
		
		boolean pagoRepartido = false;
		if(exito)
		{
			try
			{
				pagoRepartido = cotizacionManager.validaPagoPolizaRepartido(cdunieco,cdramo,"W",nmpoliza);
			}
			catch(Exception ex)
			{
				respuesta       = Utils.join("Error al recuperar reparto de pago #",System.currentTimeMillis());
				respuestaOculta = ex.getMessage();
				exito           = false;
				logger.error(respuesta,ex);
			}
		}
		
		boolean pideNumCliemte = false;
		if(exito)
		{
			try
			{
				pideNumCliemte = consultasManager.validaClientePideNumeroEmpleado(cdunieco,cdramo,"W",nmpoliza);
			}
			catch(Exception ex)
			{
				respuesta       = Utils.join("Error al recuperar parametrizacion de numero de empleado por cliente");
				respuestaOculta = ex.getMessage();
				exito           = false;
				logger.error(respuesta,ex);
			}
		}
		
		String nombreCenso = null;
		
		if(exito&&StringUtils.isBlank(nombreCensoConfirmado))
		{
			FileInputStream input       = null;
			Workbook        workbook    = null;
			Sheet           sheet       = null;
			Long            inTimestamp = null;
			File            archivoTxt  = null;
			PrintStream     output      = null;
			
			try
			{	
				input       = new FileInputStream(censo);
				workbook    = WorkbookFactory.create(input);
				sheet       = workbook.getSheetAt(0);
				inTimestamp = System.currentTimeMillis();
				nombreCenso = "censo_"+inTimestamp+"_"+nmpoliza+".txt";
				archivoTxt  = new File(this.getText("ruta.documentos.temporal")+"/"+nombreCenso);
				output      = new PrintStream(archivoTxt);
			}
			catch(Exception ex)
			{
				long etimestamp = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Error al procesar censo #"+etimestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
			
			if(exito&&workbook.getNumberOfSheets()!=1)
			{
				long etimestamp = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Favor de revisar el n\u00famero de hojas del censo #"+etimestamp;
				logger.error(respuesta);
			}
			
			if(exito)
			{
				//Iterate through each rows one by one
				logger.info(""
						+ "\n##############################################"
						+ "\n###### "+archivoTxt.getAbsolutePath()+" ######"
						);
				
	            Iterator<Row> rowIterator        = sheet.iterator();
	            int           fila               = 0;
	            int           nFamilia           = 0;
	            StringBuilder bufferErroresCenso = new StringBuilder();
	            int           filasLeidas        = 0;
	            int           filasProcesadas    = 0;
	            int           filasError         = 0;
	            
	            Map<Integer,String>  familias       = new LinkedHashMap<Integer,String>();
				Map<Integer,Boolean> estadoFamilias = new LinkedHashMap<Integer,Boolean>();
				Map<Integer,Integer> errorFamilia   = new LinkedHashMap<Integer,Integer>();
				Map<Integer,String>  titulares      = new LinkedHashMap<Integer,String>();
	            
				boolean[] gruposValidos = new boolean[olist1.size()];
				
	            while (rowIterator.hasNext()&&exito) 
	            {
	                Row           row            = rowIterator.next();
	                Date          auxDate        = null;
	                Cell          auxCell        = null;
	                StringBuilder bufferLinea    = new StringBuilder();
	                StringBuilder bufferLineaStr = new StringBuilder();
	                boolean       filaBuena      = true;
	                
	                if(Utils.isRowEmpty(row))
	                {
	                	break;
	                }
	                
	                fila        = fila + 1;
	                filasLeidas = filasLeidas + 1;
	                
	                String parentesco = null;
	                String nombre     = "";
	                double cdgrupo    = -1d;
	                
	                try
                	{
	                	cdgrupo = row.getCell(0).getNumericCellValue();
		                logger.info("GRUPO: "+(
		                		String.format("%.0f",row.getCell(0).getNumericCellValue())+"|"
		                		));
		                bufferLinea.append(
		                		String.format("%.0f",row.getCell(0).getNumericCellValue())+"|"
		                		);
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'Grupo' (A) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(0)),"-"));
	                }
	                
	                try
                	{
	                	parentesco = row.getCell(1).getStringCellValue();
	                	if(!"T".equalsIgnoreCase(parentesco)
	                			&&!"C".equalsIgnoreCase(parentesco)
	                			&&!"H".equalsIgnoreCase(parentesco)
	                			&&!"P".equalsIgnoreCase(parentesco)
	                			&&!"D".equalsIgnoreCase(parentesco)
	                			)
	                	{
	                		throw new ApplicationException("El parentesco no se reconoce [T,C,H,P,D]");
	                	}
		                logger.info("PARENTESCO: "+(
		                		parentesco+"|"
		                		));
		                bufferLinea.append(
		                		parentesco+"|"
		                		);
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'Parentesco' (B) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(1)),"-"));
	                }
	                
	                try
                	{
		                logger.info("PATERNO: "+(
		                		row.getCell(2).getStringCellValue()+"|"
		                		));
		                bufferLinea.append(
		                		row.getCell(2).getStringCellValue()+"|"
		                		);
		                
		                nombre = Utils.join(nombre,row.getCell(2).getStringCellValue()," ");
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'Apellido paterno' (C) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(2)),"-"));
	                }
	                
	                try
                	{
		                logger.info("MATERNO: "+(
		                		row.getCell(3).getStringCellValue()+"|"
		                		));
		                bufferLinea.append(
		                		row.getCell(3).getStringCellValue()+"|"
		                		);
		                nombre = Utils.join(nombre,row.getCell(3).getStringCellValue()," ");
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'Apellido materno' (D) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(3)),"-"));
	                }
	                
	                try
                	{
		                logger.info("NOMBRE: "+(
		                		row.getCell(4).getStringCellValue()+"|"
		                		));
		                bufferLinea.append(
		                		row.getCell(4).getStringCellValue()+"|"
		                		);
		                nombre = Utils.join(nombre,row.getCell(4).getStringCellValue()," ");
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'Nombre' (E) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(4)),"-"));
	                }
	                
	                try
                	{
		                auxCell=row.getCell(5);
		                logger.info("SEGUNDO NOMBRE: "+(
		                		auxCell!=null?auxCell.getStringCellValue()+"|":"|"
		                		));
		                bufferLinea.append(
		                		auxCell!=null?auxCell.getStringCellValue()+"|":"|"
		                		);
		                nombre = Utils.join(nombre,auxCell!=null?auxCell.getStringCellValue():"");
		                
		                if("T".equals(parentesco))
		                {
		                	nFamilia++;
		                	familias.put(nFamilia,"");
		                	estadoFamilias.put(nFamilia,true);
		                	titulares.put(nFamilia,nombre);
		                }
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'Segundo nombre' (F) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(5)),"-"));
	                }
	                
	                try
                	{
	                	String sexo = row.getCell(6).getStringCellValue();
	                	if(!"H".equalsIgnoreCase(sexo)
	                			&&!"M".equalsIgnoreCase(sexo)
	                	)
	                	{
	                		throw new ApplicationException("No se reconoce el sexo [H,M]");
	                	}
		                logger.info("SEXO: "+(
		                		sexo+"|"
		                		));
		                bufferLinea.append(
		                		sexo+"|"
		                		);
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'Sexo' (G) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(6)),"-"));
	                }
	                
	                try
                	{
		                auxDate=row.getCell(7).getDateCellValue();
		                if(auxDate!=null)
		                {
		                	Calendar cal = Calendar.getInstance();
		                	cal.setTime(auxDate);
		                	if(cal.get(Calendar.YEAR)>2100
		                			||cal.get(Calendar.YEAR)<1900
		                			)
		                	{
		                		throw new ApplicationException("El anio de la fecha no es valido");
		                	}
		                }
		                logger.info("FECHA NACIMIENTO: "+(
		                		auxDate!=null?renderFechas.format(auxDate)+"|":"|"
		                			));
		                bufferLinea.append(
		                		auxDate!=null?renderFechas.format(auxDate)+"|":"|"
		                			);
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'Fecha de nacimiento' (H) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(7)),"-"));
	                }
	                
	                try
                	{
		                logger.info("COD POSTAL: "+(
		                		String.format("%.0f",row.getCell(8).getNumericCellValue())+"|"
		                		));
		                bufferLinea.append(
		                		String.format("%.0f",row.getCell(8).getNumericCellValue())+"|"
		                		);
                	}
	                catch(Exception ex2)
	                {
	                	logger.error("error al leer codigo postal como numero, se intentara como string:",ex2);
	                	try
	                	{
	                		logger.info("COD POSTAL: "+row.getCell(8).getStringCellValue()+"|");
			                bufferLinea.append(row.getCell(8).getStringCellValue()+"|");
	                	}
		                catch(Exception ex)
		                {
		                	filaBuena = false;
		                	bufferErroresCenso.append(Utils.join("Error en el campo 'Codigo postal' (I) de la fila ",fila," "));
		                }
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(8)),"-"));
	                }
	                
	                try
                	{
		                logger.info("ESTADO: "+(
		                		row.getCell(9).getStringCellValue()+"|"
		                		));
		                bufferLinea.append(
		                		row.getCell(9).getStringCellValue()+"|"
		                		);
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'Estado' (J) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(9)),"-"));
	                }
	                
	                try
                	{
		                logger.info("MUNICIPIO: "+(
		                		row.getCell(10).getStringCellValue()+"|"
		                		));
		                bufferLinea.append(
		                		row.getCell(10).getStringCellValue()+"|"
		                		);
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'Municipio' (K) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(10)),"-"));
	                }
	                
	                try
                	{
		                logger.info("COLONIA: "+(
		                		row.getCell(11).getStringCellValue()+"|"
		                		));
		                bufferLinea.append(
		                		row.getCell(11).getStringCellValue()+"|"
		                		);
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'Colonia' (L) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(11)),"-"));
	                }
	                
	                try
                	{
		                logger.info("CALLE: "+(
		                		row.getCell(12).getStringCellValue()+"|"
		                		));
		                bufferLinea.append(
		                		row.getCell(12).getStringCellValue()+"|"
		                		);
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'Calle' (M) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(12)),"-"));
	                }
	                
	                try
                	{
	                	String numExt = extraerStringDeCelda(row.getCell(13));
	                	if(StringUtils.isBlank(numExt))
	                	{
	                		throw new ApplicationException("Falta numero exterior");
	                	}
		                logger.info("NUM EXT: "+numExt);
		                bufferLinea.append(Utils.join(numExt,"|"));
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'Numero exterior' (N) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(13)),"-"));
	                }
	                
	                try
                	{
	                	String numInt = extraerStringDeCelda(row.getCell(14));
		                logger.info("NUM INT: "+numInt);
		                bufferLinea.append(Utils.join(numInt,"|"));
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'Numero interior' (O) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(14)),"-"));
	                }
	                
	                try
                	{
	                	auxCell=row.getCell(15);
	                	
		                logger.info("RFC: "+(auxCell!=null?auxCell.getStringCellValue()+"|":"|"));
		                
		                bufferLinea.append(auxCell!=null?auxCell.getStringCellValue()+"|":"|");
		                
		                if(
		                		(auxCell==null||StringUtils.isBlank(auxCell.getStringCellValue()))
		                		&&pagoRepartido
		                		&&"T".equals(parentesco)
		                )
		                {
		                	throw new ApplicationException("Sin rfc para un titular en pago repartido");
		                }
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'RFC' (P) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(15)),"-"));
	                }
	                
	                try
                	{
		                auxCell=row.getCell(16);
		                logger.info("CORREO: "+(
		                		auxCell!=null?auxCell.getStringCellValue()+"|":"|"
		                		));
		                bufferLinea.append(
		                		auxCell!=null?auxCell.getStringCellValue()+"|":"|"
		                		);
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'Correo' (Q) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(16)),"-"));
	                }
	                
	                try
                	{
		                auxCell=row.getCell(17);
		                logger.info("TELEFONO: "+(
		                		auxCell!=null?String.format("%.0f",auxCell.getNumericCellValue())+"|":"|"
		                		));
		                bufferLinea.append(
		                		auxCell!=null?String.format("%.0f",auxCell.getNumericCellValue())+"|":"|"
		                		);
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'Telefono' (R) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(17)),"-"));
	                }
	                
	                try
                	{
		                auxCell=row.getCell(18);
		                if(pideNumCliemte&&
		                		(auxCell==null||auxCell.getStringCellValue()==null||StringUtils.isBlank(auxCell.getStringCellValue()))
		                )
		                {
		                	throw new ApplicationException("Necesito el numero de empleado");
		                }
		                logger.info("IDENTIDAD: "+(
		                		auxCell!=null?auxCell.getStringCellValue()+"|":"|"
		                		));
		                bufferLinea.append(
		                		auxCell!=null?auxCell.getStringCellValue()+"|":"|"
		                		);
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'Identidad' (S) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(18)),"-"));
	                }
	                
	                try
                	{
		                auxDate=row.getCell(19)!=null?row.getCell(19).getDateCellValue():null;
		                if(auxDate!=null)
		                {
		                	Calendar cal = Calendar.getInstance();
		                	cal.setTime(auxDate);
		                	if(cal.get(Calendar.YEAR)>2100
		                			||cal.get(Calendar.YEAR)<1900
		                			)
		                	{
		                		throw new ApplicationException("El anio de la fecha no es valido");
		                	}
		                }
		                logger.info("FECHA RECONOCIMIENTO ANTIGUEDAD: "+(
		                		auxDate!=null?renderFechas.format(auxDate)+"|":"|"
		                			));
		                bufferLinea.append(
		                		auxDate!=null?renderFechas.format(auxDate)+"|":"|"
		                			);
                	}
	                catch(Exception ex)
	                {
	                	filaBuena = false;
	                	bufferErroresCenso.append(Utils.join("Error en el campo 'Fecha de reconocimiento antiguedad' (T) de la fila ",fila," "));
	                }
	                finally
	                {
	                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(19)),"-"));
	                }
	                
	                logger.debug(Utils.log("** NUEVA_FILA (filaBuena=",filaBuena,",cdgrupo=",cdgrupo,") **"));
	                
	                if(filaBuena)
	                {
	                	familias.put(nFamilia,Utils.join(familias.get(nFamilia),bufferLinea.toString(),"\n"));
	                	filasProcesadas = filasProcesadas + 1;
	                	gruposValidos[((int)cdgrupo)-1]=true;
	                }
	                else
	                {
	                	filasError = filasError + 1;
	                	bufferErroresCenso.append(Utils.join(": ",bufferLineaStr.toString(),"\n"));
	                	estadoFamilias.put(nFamilia,false);
	                	
	                	if(!errorFamilia.containsKey(nFamilia))
	                	{
	                		errorFamilia.put(nFamilia,fila);
	                	}
	                }
	                
	                if(cdgrupo>0d)
	                {
	                	logger.debug(Utils.log("cdgrupo=",cdgrupo,", valido=",gruposValidos[((int)cdgrupo)-1]));
	                }
	            }
	            
	            if(exito)
	            {
	            	for(int i=0;i<gruposValidos.length;i++)
	            	{
	            		logger.debug(Utils.log("gruposValidos[i]=",gruposValidos[i]));
	            	}
	            }
	            
	            if(exito)
	            {
	            	boolean       sonGruposValidos = true;
	            	StringBuilder errorGrupos      = new StringBuilder();
	            	for(int i=0;i<gruposValidos.length;i++)
	            	{
	            		if(!gruposValidos[i])
	            		{
	            			sonGruposValidos = false;
	            			errorGrupos.append("Debe haber al menos un asegurado v\u00E1lido para el grupo ").append(i+1).append("\n");
	            		}
	            	}
	            	if(!sonGruposValidos)
	            	{
	            		exito           = false;
	            		respuesta       = errorGrupos.append("\n")
	            				.append(bufferErroresCenso.toString())
	            				.append("\nError #").append(System.currentTimeMillis()).toString();
	            		respuestaOculta = respuesta;
	            		logger.error(bufferErroresCenso.toString());
	            		logger.error(respuesta);
	            	}
	            }
	            
	            if(exito)
				{
	            	logger.debug("\nFamilias: {}\nEstado familias: {}\nErrorFamilia: {}\nTitulares: {}"
		            		,familias,estadoFamilias,errorFamilia,titulares);
		            
		            for(Entry<Integer,Boolean>en:estadoFamilias.entrySet())
		            {
		            	int     n = en.getKey();
		            	boolean v = en.getValue();
		            	if(v)
		            	{
		            		output.print(familias.get(n));
		            	}
		            	else
		            	{
		            		bufferErroresCenso.append(Utils.join("La familia ",n," del titular '",titulares.get(n),"' no fue incluida por error en la fila ",errorFamilia.get(n),"\n"));
		            	}
		            }
		            
					smap1.put("erroresCenso"    , bufferErroresCenso.toString());
					smap1.put("filasLeidas"     , Integer.toString(filasLeidas));
					smap1.put("filasProcesadas" , Integer.toString(filasProcesadas));
					smap1.put("filasErrores"    , Integer.toString(filasError));
				}
	            
	            if(exito)
	            {
	            	try
	            	{
	            		input.close();
	            		output.close();
	            	}
	            	catch(Exception ex)
	            	{
	            		long etimestamp = System.currentTimeMillis();
	            		exito           = false;
	            		respuesta       = "Error al transformar el archivo #"+etimestamp;
	            		respuestaOculta = ex.getMessage();
	            		logger.error(respuesta,ex);
	            	}
	            }
	            
	            logger.info(""
	            		+ "\n###### "+archivoTxt.getAbsolutePath()+" ######"
						+ "\n##############################################"
						);
				
	            if(exito)
	            {
					exito = FTPSUtils.upload
							(
								this.getText("dominio.server.layouts"),
								this.getText("user.server.layouts"),
								this.getText("pass.server.layouts"),
								archivoTxt.getAbsolutePath(),
								this.getText("directorio.server.layouts")+"/"+nombreCenso
						    )
							&&FTPSUtils.upload
							(
								this.getText("dominio.server.layouts2"),
								this.getText("user.server.layouts"),
								this.getText("pass.server.layouts"),
								archivoTxt.getAbsolutePath(),
								this.getText("directorio.server.layouts")+"/"+nombreCenso
							);
					
					if(!exito)
					{
						long etimestamp = System.currentTimeMillis();
						exito           = false;
						respuesta       = "Error al transferir archivo al servidor #"+etimestamp;
						respuestaOculta = respuesta;
						logger.error(respuesta);
					}
	            }
				
				if(exito)
				{
					try
					{
						String cdedo         = smap1.get("cdedo");
						String cdmunici      = smap1.get("cdmunici");
						String cdplanes[]    = new String[5];
						
						for(Map<String,Object>iGrupo:olist1)
						{
							String  cdgrupo      = (String)iGrupo.get("letra");
							String  cdplan       = (String)iGrupo.get("cdplan");
							Integer indGrupo     = Integer.valueOf(cdgrupo);
							cdplanes[indGrupo-1] = cdplan;
						}
						
						cotizacionManager.guardarCensoCompletoMultisalud(nombreCenso
								,cdunieco    , cdramo      , "W"
								,nmpoliza    , cdedo       , cdmunici
								,cdplanes[0] , cdplanes[1] , cdplanes[2]
								,cdplanes[3] , cdplanes[4] , "N"
								);
					}
					catch(Exception ex)
					{
						long etimestamp = System.currentTimeMillis();
						exito           = false;
						respuesta       = "Error al guardar los datos #"+etimestamp;
						respuestaOculta = ex.getMessage();
						logger.error(respuesta,ex);
						
					}
				}
			}
		}
		
		if(exito&&StringUtils.isBlank(nombreCensoConfirmado))
		{
			smap1.put("nombreCensoParaConfirmar", nombreCenso);
			exito     = true;
			respuesta = Utils.join("Se ha revisado el censo [REV. ",System.currentTimeMillis(),"]");
			logger.info(respuesta);
			return SUCCESS;
		}
		
		if(exito)
		{
			tvalositSigsvdefTvalogarContratanteTramiteSigsvalipolObject aux=this.tvalositSigsvdefTvalogarContratanteTramiteSigsvalipol(
					clasif    , LINEA      , LINEA_EXTENDIDA
					,cdunieco , cdramo     , nmpoliza
					,cdtipsit , hayTramite , hayTramiteVacio
					,user     , cdelemento , ntramiteVacio
					,true     , null/*ntramite*/, null/*cdagente*/
					,false //sincenso
					,false //censoAtrasado
					,false //resubirCenso
					,cdperpag
					,cdsisrol
					,false
					,false //asincrono
					,null
					,null
					,null
					);
			exito           = aux.exito;
			respuesta       = aux.respuesta;
			respuestaOculta = aux.respuestaOculta;
		}
		
		if(exito)
		{
			respuesta       = "Se han complementado los asegurados";
			respuestaOculta = "Todo OK";
		}
		
		logger.info(""
				+ "\n###### subirCensoCompleto ######"
				+ "\n################################"
				);
		return SUCCESS;
	}
	
	private class tvalositSigsvdefTvalogarContratanteTramiteSigsvalipolObject
	{
		public boolean exito           = false;
		public String  respuesta       = null;
		public String  respuestaOculta = null;
	}
	
	public String generarTramiteGrupo2()
	{
		logger.info(
				new StringBuilder()
				.append("\n##################################")
				.append("\n###### generarTramiteGrupo2 ######")
				.append("\n###### smap1=").append(smap1)
				.append("\n###### olist1=").append(olist1)
				.toString()
				);
		
		this.session=ActionContext.getContext().getSession();
		
		exito   = true;
		success = true;
		
		String cdunieco                = null;
		String cdramo                  = null;
		String nmpoliza                = null;
		String feini                   = null;
		String fefin                   = null;
		String cdperpag                = null;
		String pcpgocte                = null;
		String ntramite                = null;
		String ntramiteVacio           = null;
		String miTimestamp             = null;
		String rutaDocumentosTemporal  = null;
		String tipoCenso               = null;
		String dominioServerLayouts    = null;
		String userServerLayouts       = null;
		String passServerLayouts       = null;
		String directorioServerLayouts = null;
		String cdtipsit                = null;
		String codpostal               = null;
		String cdedo                   = null;
		String cdmunici                = null;
		String cdagente                = null;
		String cdusuari                = null;
		String cdsisrol                = null;
		String clasif                  = null;
		String LINEA_EXTENDIDA         = null;
		String cdpersonCli             = null;
		String nombreCli               = null;
		String rfcCli                  = null;
		String dsdomiciCli             = null;
		String nmnumeroCli             = null;
		String nmnumintCli             = null;
		String cdelemen                = null;
		String cdpool                  = null;
		String nombreCensoConfirmado   = null;
		String cdideper_               = null;
		String cdideext_               = null;
		String nmpolant                = null;
		String nmrenova                = null;
		
		boolean sincenso      = false;
		boolean censoAtrasado = false;
		boolean resubirCenso  = false;
		boolean complemento   = false;
		boolean asincrono     = false;
		
		//datos de entrada
		try
		{
			if(session==null)
			{
				throw new ApplicationException("No hay sesion");
			}
			if(session.get("USUARIO")==null)
			{
				throw new ApplicationException("No hay usuario en la sesion"); 
			}
			UserVO usuario = (UserVO)session.get("USUARIO");
			cdusuari       = usuario.getUser();
			cdsisrol       = usuario.getRolActivo().getClave();
			cdelemen       = usuario.getEmpresa().getElementoId();
			
			if(smap1==null)
			{
				throw new ApplicationException("No se recibieron datos");
			}
			cdunieco        = smap1.get("cdunieco");
			cdramo          = smap1.get("cdramo");
			nmpoliza        = smap1.get("nmpoliza");
			feini           = smap1.get("feini");
			fefin           = smap1.get("fefin");
			cdperpag        = smap1.get("cdperpag");
			pcpgocte        = smap1.get("pcpgocte");
			ntramite        = smap1.get("ntramite");
			ntramiteVacio   = smap1.get("ntramiteVacio");
			miTimestamp     = smap1.get("timestamp");
			tipoCenso       = smap1.get("tipoCenso");
			cdtipsit        = smap1.get("cdtipsit");
			codpostal       = smap1.get("codpostal");
			cdedo           = smap1.get("cdedo");
			cdmunici        = smap1.get("cdmunici");
			cdagente        = smap1.get("cdagente");
			clasif          = smap1.get("clasif");
			LINEA_EXTENDIDA = smap1.get("LINEA_EXTENDIDA");
			cdpersonCli     = smap1.get("cdperson");
			nombreCli       = smap1.get("nombre");
			rfcCli          = smap1.get("cdrfc");
			dsdomiciCli     = smap1.get("dsdomici");
			nmnumeroCli     = smap1.get("nmnumero");
			nmnumintCli     = smap1.get("nmnumint");
			cdpool          = smap1.get("cdpool");
			cdideper_       = smap1.get("cdideper_");
			cdideext_       = smap1.get("cdideext_");
			nmpolant        = smap1.get("nmpolant");
			nmrenova        = smap1.get("nmrenova");
			
			rutaDocumentosTemporal  = getText("ruta.documentos.temporal");
			dominioServerLayouts    = getText("dominio.server.layouts");
			userServerLayouts       = getText("user.server.layouts");
			passServerLayouts       = getText("pass.server.layouts");
			directorioServerLayouts = getText("directorio.server.layouts");
			
			String sincensoS      = smap1.get("sincenso");
			sincenso              = StringUtils.isNotBlank(sincensoS)&&sincensoS.equals("S");
			String censoAtrasadoS = smap1.get("censoAtrasado");
			censoAtrasado         = StringUtils.isNotBlank(censoAtrasadoS)&&censoAtrasadoS.equals("S");
			String resubirCensoS  = smap1.get("resubirCenso");
			resubirCenso          = StringUtils.isNotBlank(resubirCensoS)&&resubirCensoS.equals("S");
			
			complemento = "S".equals(smap1.get("complemento"));
			
			nombreCensoConfirmado = smap1.get("nombreCensoConfirmado");
			
			asincrono = StringUtils.isNotBlank(smap1.get("asincrono"))&&smap1.get("asincrono").equalsIgnoreCase("si");
		}
		catch(ApplicationException ax)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString();
			respuestaOculta = ax.getMessage();
			logger.error(respuesta,ax);
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder("Error al validar datos #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		//proceso
		if(exito)
		{
			ManagerRespuestaSmapVO resp=cotizacionManager.generarTramiteGrupo(
					cdunieco
					,cdramo
					,nmpoliza
					,feini
					,fefin
					,cdperpag
					,pcpgocte
					,smap1
					,ntramite
					,ntramiteVacio
					,miTimestamp
					,rutaDocumentosTemporal
					,tipoCenso
					,dominioServerLayouts
					,userServerLayouts
					,passServerLayouts
					,directorioServerLayouts
					,cdtipsit
					,olist1
					,codpostal
					,cdedo
					,cdmunici
					,cdagente
					,cdusuari
					,cdsisrol
					,clasif
					,LINEA_EXTENDIDA
					,cdpersonCli
					,nombreCli
					,rfcCli
					,dsdomiciCli
					,nmnumeroCli
					,nmnumintCli
					,cdelemen
					,sincenso
					,censoAtrasado
					,resubirCenso
					,complemento
					,cdpool
					,nombreCensoConfirmado
					,asincrono
					,cdideper_
					,cdideext_
					,nmpolant
					,StringUtils.isBlank(nmrenova) ? "0" : nmrenova
					);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			if(exito)
			{
				smap1.putAll(resp.getSmap());
			}
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### generarTramiteGrupo2 ######")
				.append("\n##################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String generarTramiteGrupo()
	{
		this.session=ActionContext.getContext().getSession();
		logger.info(""
				+ "\n#################################"
				+ "\n###### generarTramiteGrupo ######"
				+ "\nsmap1 "+smap1
				+ "\nolist1 "+olist1
				);
		try
		{
			success = true;
			exito   = true;
			
			String inTimestamp      = smap1.get("timestamp");
			String clasif           = smap1.get("clasif");
			String LINEA_EXTENDIDA  = smap1.get("LINEA_EXTENDIDA");
			String cdunieco         = smap1.get("cdunieco");
			String cdramo           = smap1.get("cdramo");
			String cdtipsit         = smap1.get("cdtipsit");
			String nmpoliza         = smap1.get("nmpoliza");
			Date   fechaHoy         = new Date();
			String feini            = smap1.get("feini");
			String fefin            = smap1.get("fefin");
			String cdpool           = smap1.get("cdpool");
			String cdperpag         = smap1.get("cdperpag");
			final String LINEA      = "1";
			UserVO usuario          = (UserVO)session.get("USUARIO");
			String user             = usuario.getUser();
			String cdelemento       = usuario.getEmpresa().getElementoId();
			String cdsisrol         = usuario.getRolActivo().getClave();
			String nombreCenso      = null;
			String ntramite         = smap1.get("ntramite");
			String cdagente         = smap1.get("cdagente");
			String ntramiteVacio    = smap1.get("ntramiteVacio");
			String tipoCenso        = smap1.get("tipoCenso");
			//String ptajepar         = smap1.get("cdreppag");
			String pcpgocte         = smap1.get("pcpgocte");
			boolean esCensoSolo     = StringUtils.isNotBlank(tipoCenso)&&tipoCenso.equalsIgnoreCase("solo");
			boolean hayTramite      = StringUtils.isNotBlank(ntramite);
			boolean hayTramiteVacio = StringUtils.isNotBlank(ntramiteVacio);
			
			String sincensoS      = smap1.get("sincenso");
			boolean sincenso      = StringUtils.isNotBlank(sincensoS)&&sincensoS.equals("S");
			String censoAtrasadoS = smap1.get("censoAtrasado");
			boolean censoAtrasado = StringUtils.isNotBlank(censoAtrasadoS)&&censoAtrasadoS.equals("S");
			String resubirCensoS  = smap1.get("resubirCenso");
			boolean resubirCenso  = StringUtils.isNotBlank(resubirCensoS)&&resubirCensoS.equals("S");
			
			boolean complemento = "S".equals(smap1.get("complemento"));
			
			censo = new File(this.getText("ruta.documentos.temporal")+"/censo_"+inTimestamp);
			
			String nombreCensoConfirmado = smap1.get("nombreCensoConfirmado");
			
			boolean asincrono = StringUtils.isNotBlank(smap1.get("asincrono"))&&smap1.get("asincrono").equalsIgnoreCase("si");
			
			String cdedo         = smap1.get("cdedo");
			String cdmunici      = smap1.get("cdmunici");
			String codpostal      = smap1.get("codpostal");
			
			String nmpolant  = smap1.get("nmpolant")
			       ,nmrenova = smap1.get("nmrenova");
			
			logger.info(Utils.log(
					"\ninTimestamp: " , inTimestamp
					,"\nclasif: "     , clasif
					,"\ncenso: "      , censo
					));
			
			//nmpoliza
			if(exito && StringUtils.isBlank(nmpoliza))
			{
				nmpoliza = (String)kernelManager.calculaNumeroPoliza(cdunieco,cdramo,"W").getItemMap().get("NUMERO_POLIZA");
				smap1.put("nmpoliza",nmpoliza);
			}
			
			if(exito)
			{
				if(StringUtils.isNotBlank(nombreCensoConfirmado))
				{
					nombreCenso = nombreCensoConfirmado;
				}
				else
				{
					nombreCenso = "censo_"+inTimestamp+"_"+nmpoliza+".txt";
				}
			}
			
			//mpolizas
			if(exito)
			{
				try
				{
					Map<String,String>mapaMpolizas=new HashMap<String,String>(0);
		            mapaMpolizas.put("pv_cdunieco"  , cdunieco);
		            mapaMpolizas.put("pv_cdramo"    , cdramo);
		            mapaMpolizas.put("pv_estado"    , "W");
		            mapaMpolizas.put("pv_nmpoliza"  , nmpoliza);
		            mapaMpolizas.put("pv_nmsuplem"  , "0");
		            mapaMpolizas.put("pv_status"    , "V");
		            mapaMpolizas.put("pv_swestado"  , "0");
		            mapaMpolizas.put("pv_nmsolici"  , null);
		            mapaMpolizas.put("pv_feautori"  , null);
		            mapaMpolizas.put("pv_cdmotanu"  , null);
		            mapaMpolizas.put("pv_feanulac"  , null);
		            mapaMpolizas.put("pv_swautori"  , "N");
		            mapaMpolizas.put("pv_cdmoneda"  , "001");
		            mapaMpolizas.put("pv_feinisus"  , null);
		            mapaMpolizas.put("pv_fefinsus"  , null);
		            mapaMpolizas.put("pv_ottempot"  , "R");
		            mapaMpolizas.put("pv_feefecto"  , feini);
		            mapaMpolizas.put("pv_hhefecto"  , "12:00");
		            mapaMpolizas.put("pv_feproren"  , fefin);
		            mapaMpolizas.put("pv_fevencim"  , null);
		            mapaMpolizas.put("pv_nmrenova"  , StringUtils.isBlank(nmrenova) ? "0" : nmrenova);
		            mapaMpolizas.put("pv_ferecibo"  , null);
		            mapaMpolizas.put("pv_feultsin"  , null);
		            mapaMpolizas.put("pv_nmnumsin"  , "0");
		            mapaMpolizas.put("pv_cdtipcoa"  , "N");
		            mapaMpolizas.put("pv_swtarifi"  , "A");
		            mapaMpolizas.put("pv_swabrido"  , null);
		            mapaMpolizas.put("pv_feemisio"  , renderFechas.format(fechaHoy));
		            mapaMpolizas.put("pv_cdperpag"  , cdperpag);
		            mapaMpolizas.put("pv_nmpoliex"  , null);
		            mapaMpolizas.put("pv_nmcuadro"  , "P1");
		            mapaMpolizas.put("pv_porredau"  , "100");
		            mapaMpolizas.put("pv_swconsol"  , "S");
		            mapaMpolizas.put("pv_nmpolant"  , nmpolant);
		            mapaMpolizas.put("pv_nmpolnva"  , null);
		            mapaMpolizas.put("pv_fesolici"  , renderFechas.format(fechaHoy));
		            mapaMpolizas.put("pv_cdramant"  , null);
		            mapaMpolizas.put("pv_cdmejred"  , null);
		            mapaMpolizas.put("pv_nmpoldoc"  , null);
		            mapaMpolizas.put("pv_nmpoliza2" , null);
		            mapaMpolizas.put("pv_nmrenove"  , null);
		            mapaMpolizas.put("pv_nmsuplee"  , null);
		            mapaMpolizas.put("pv_ttipcamc"  , null);
		            mapaMpolizas.put("pv_ttipcamv"  , null);
		            mapaMpolizas.put("pv_swpatent"  , null);
		            mapaMpolizas.put("pv_pcpgocte"  , pcpgocte);
		            mapaMpolizas.put("pv_tipoflot"  , "F");
		            mapaMpolizas.put("pv_agrupador" , cdpool);
		            mapaMpolizas.put("pv_accion"    , "U");
		            kernelManager.insertaMaestroPolizas(mapaMpolizas);
				}
				catch(Exception ex)
				{
					long etimestamp = System.currentTimeMillis();
					logger.error(etimestamp+" error mpolizas",ex);
					respuesta       = "Error al cotizar #"+etimestamp;
					respuestaOculta = ex.getMessage();
					exito           = false;
				}
			}
			
			//tvalopol
			if(exito)
			{
				Map<String,String>params=new HashMap<String,String>();
				params.put("pv_cdunieco"  , cdunieco);
				params.put("pv_cdramo"    , cdramo);
				params.put("pv_estado"    , "W");
				params.put("pv_nmpoliza"  , nmpoliza);
				params.put("pv_nmsuplem"  , "0");
				params.put("pv_status"    , "V");
				params.put("pv_otvalor09" , smap1.get("dctocmer"));
				params.put("pv_otvalor10" , smap1.get("tipoDerPol"));
				params.put("pv_otvalor11" , smap1.get("montoDerPol"));
				params.put("pv_otvalor12" , smap1.get("recargoPers"));
				params.put("pv_otvalor13" , smap1.get("recargoPago"));
				params.put("pv_otvalor14" , smap1.get("cdgiro"));
				params.put("pv_otvalor15" , smap1.get("cdrelconaseg"));
				params.put("pv_otvalor16" , smap1.get("cdformaseg"));
				params.put("pv_otvalor17" , smap1.get("cdperpag"));
				kernelManager.pMovTvalopol(params);
			}
			
			//enviar archivo
			if(exito&&(!hayTramite||hayTramiteVacio||censoAtrasado||resubirCenso)&&!sincenso&&!complemento&&StringUtils.isBlank(nombreCensoConfirmado))
			{
				
				FileInputStream input      = null;
				Workbook        workbook   = null;
				Sheet           sheet      = null;
				File            archivoTxt = null;
				PrintStream     output     = null;
				
				StringBuilder bufferErroresCenso = new StringBuilder("");
				int filasLeidas     = 0;
				int filasProcesadas = 0;
				int filasErrores    = 0;

				int nGrupos       = olist1.size();
				boolean[] bGrupos = new boolean[nGrupos];
				
				try
				{	
					input    = new FileInputStream(censo);
					workbook = WorkbookFactory.create(input);
					sheet    = workbook.getSheetAt(0);
					
					archivoTxt = new File(this.getText("ruta.documentos.temporal")+"/"+nombreCenso);
					output     = new PrintStream(archivoTxt);
				}
				catch(Exception ex)
				{
					long etimestamp = System.currentTimeMillis();
					exito           = false;
					respuesta       = "Error inesperado al procesar censo #"+etimestamp;
					respuestaOculta = ex.getMessage();
					logger.error(respuesta,ex);
				}
				
				if(exito&&workbook.getNumberOfSheets()!=1)
				{
					long etimestamp = System.currentTimeMillis();
					exito           = false;
					respuesta       = "Favor de revisar el n\u00famero de hojas del censo #"+etimestamp;
					logger.error(respuesta);
				}
				
				if(exito)
				{
					int nSituac = 0;
					
					if(esCensoSolo)
					{
						
						Map<Integer,String>  familias       = new LinkedHashMap<Integer,String>();
						Map<Integer,Boolean> estadoFamilias = new LinkedHashMap<Integer,Boolean>();
						Map<Integer,Integer> errorFamilia   = new LinkedHashMap<Integer,Integer>();
						Map<Integer,String>  titulares      = new LinkedHashMap<Integer,String>();
						
						//Iterate through each rows one by one
						logger.info(""
								+ "\n##############################################"
								+ "\n###### "+archivoTxt.getAbsolutePath()+" ######"
								);
			            Iterator<Row> rowIterator = sheet.iterator();
			            int           fila        = 0;
			            int           nFamilia    = 0;
			            while (rowIterator.hasNext()&&exito) 
			            {
			            	boolean       filaBuena      = true;
			            	StringBuilder bufferLinea    = new StringBuilder();
			            	StringBuilder bufferLineaStr = new StringBuilder();
			            	
			                Row  row     = rowIterator.next();
			                Date auxDate = null;
			                Cell auxCell = null;
			                
			                if(Utils.isRowEmpty(row))
			                {
			                	break;
			                }
			                
			                fila        = fila + 1;
			                nSituac     = nSituac + 1;
			                filasLeidas = filasLeidas + 1;
			                
			                String nombre = "";
			                
		                    try
			                {
				                auxCell=row.getCell(0);
				                logger.info("NOMBRE: "+(auxCell!=null?auxCell.getStringCellValue()+"|":"|"));
				                bufferLinea.append(Utils.join(auxCell!=null?auxCell.getStringCellValue()+"|":"|"));
				                nombre = Utils.join(nombre,auxCell!=null?auxCell.getStringCellValue():""," ");
			                }
			                catch(Exception ex)
			                {
			                	filaBuena          = false;
			                	bufferErroresCenso.append(Utils.join("Error en el campo 'Nombre' (A) de la fila ",fila," "));
			                }
		                    finally
		                    {
		                    	bufferLineaStr.append(Utils.join("",this.extraerStringDeCelda(row.getCell(0)),"-"));
		                    }
		                    
			                try
			                {
				                auxCell=row.getCell(1);
				                logger.info("APELLIDO: "+(auxCell!=null?auxCell.getStringCellValue()+"|":"|"));
				                bufferLinea.append(auxCell!=null?auxCell.getStringCellValue()+"|":"|");
				                nombre = Utils.join(nombre,auxCell!=null?auxCell.getStringCellValue():""," ");
			                }
			                catch(Exception ex)
			                {
			                	filaBuena          = false;
			                	bufferErroresCenso.append(Utils.join("Error en el campo 'Apellido paterno' (B) de la fila ",fila," "));
			                }
		                    finally
		                    {
		                    	bufferLineaStr.append(Utils.join("",this.extraerStringDeCelda(row.getCell(1)),"-"));
		                    }
			                
		                	try
		                	{
				                auxCell=row.getCell(2);
				                logger.info("APELLIDO 2: "+(auxCell!=null?auxCell.getStringCellValue()+"|":"|"));
				                bufferLinea.append(auxCell!=null?auxCell.getStringCellValue()+"|":"|");
				                nombre = Utils.join(nombre,auxCell!=null?auxCell.getStringCellValue():"");
			                }
			                catch(Exception ex)
			                {
			                	filaBuena          = false;
			                	bufferErroresCenso.append(Utils.join("Error en el campo 'Apellido materno' (C) de la fila ",fila," "));
			                }
		                    finally
		                    {
		                    	bufferLineaStr.append(Utils.join("",this.extraerStringDeCelda(row.getCell(2)),"-"));
		                    }
		                	
		                	try
		                	{
				                auxCell=row.getCell(3);
				                logger.info("EDAD: "+(auxCell!=null?String.format("%.0f",auxCell.getNumericCellValue())+"|":"|"));
				                bufferLinea.append(auxCell!=null?String.format("%.0f",auxCell.getNumericCellValue())+"|":"|");
				                
				                if(row.getCell(4)!=null) {
					                auxDate=row.getCell(4).getDateCellValue();
					                if(auxDate!=null)
					                {
					                	Calendar cal = Calendar.getInstance();
					                	cal.setTime(auxDate);
					                	if(cal.get(Calendar.YEAR)>2100
					                			||cal.get(Calendar.YEAR)<1900
					                			)
					                	{
					                		throw new ApplicationException("El anio de la fecha no es valido");
					                	}
					                }
					                logger.info("FENACIMI: "+(auxDate!=null?renderFechas.format(auxDate)+"|":"|"));
					                bufferLinea.append(auxDate!=null?renderFechas.format(auxDate)+"|":"|");
				                } else {
				                	logger.info("FENACIMI: "+"|");
				                	bufferLinea.append("|");
				                }
			                }
			                catch(Exception ex)
			                {
			                	filaBuena          = false;
			                	bufferErroresCenso.append(Utils.join("Error en el campo 'Edad' o 'Fecha de nacimiento' (D) de la fila ",fila," "));
			                }
		                    finally
		                    {
		                    	bufferLineaStr.append(Utils.join("",this.extraerStringDeCelda(row.getCell(3)),"-"));
		                    	bufferLineaStr.append(Utils.join("",this.extraerStringDeCelda(row.getCell(4)),"-"));
		                    }
		                	
		                	try
		                	{
		                		String sexo = row.getCell(5).getStringCellValue();
		                		if(StringUtils.isBlank(sexo)
		                				||(!sexo.equals("H")&&!sexo.equals("M")))
		                		{
		                			throw new ApplicationException("El sexo no se reconoce [H,M]");
		                		}
				                logger.info("SEXO: "+sexo+"|");
				                bufferLinea.append(sexo+"|");
			                }
			                catch(Exception ex)
			                {
			                	filaBuena          = false;
			                	bufferErroresCenso.append(Utils.join("Error en el campo 'Sexo' (F) de la fila ",fila," "));
			                }
		                    finally
		                    {
		                    	bufferLineaStr.append(Utils.join("",this.extraerStringDeCelda(row.getCell(5)),"-"));
		                    }
			                
			                try
		                	{
		                		String parentesco = row.getCell(6).getStringCellValue();
		                		if(StringUtils.isBlank(parentesco)
		                				||(!parentesco.equals("T")
		                						&&!parentesco.equals("H")
		                						&&!parentesco.equals("P")
		                						&&!parentesco.equals("C")
		                						&&!parentesco.equals("D")
		                						)
		                						)
		                		{
		                			throw new ApplicationException("El parentesco no se reconoce [T,C,P,H,D]");
		                		}
				                logger.info("PARENTESCO: "+parentesco+"|");
				                bufferLinea.append(parentesco+"|");
				                
				                if("T".equals(parentesco))
				                {
				                	nFamilia++;
				                	familias.put(nFamilia,"");
				                	estadoFamilias.put(nFamilia,true);
				                	titulares.put(nFamilia,nombre);
				                }
			                }
			                catch(Exception ex)
			                {
			                	filaBuena          = false;
			                	bufferErroresCenso.append(Utils.join("Error en el campo 'Parentesco' (G) de la fila ",fila," "));
			                }
		                    finally
		                    {
		                    	bufferLineaStr.append(Utils.join("",this.extraerStringDeCelda(row.getCell(6)),"-"));
		                    }
			                
		                	try
		                	{
				                auxCell=row.getCell(7);
				                logger.info("OCUPACION: "+(auxCell!=null?auxCell.getStringCellValue()+"|":"|"));
				                bufferLinea.append(auxCell!=null?auxCell.getStringCellValue()+"|":"|");
			                }
			                catch(Exception ex)
			                {
			                	filaBuena          = false;
			                	bufferErroresCenso.append(Utils.join("Error en el campo 'Ocupacion' (H) de la fila ",fila," "));
			                }
		                    finally
		                    {
		                    	bufferLineaStr.append(Utils.join("",this.extraerStringDeCelda(row.getCell(7)),"-"));
		                    }
			                
		                	try
		                	{
				                auxCell=row.getCell(8);
				                logger.info("EXTRAPRIMA OCUPACION: "+(auxCell!=null?String.format("%.2f",auxCell.getNumericCellValue())+"|":"|"));
				                bufferLinea.append(auxCell!=null?String.format("%.2f",auxCell.getNumericCellValue())+"|":"|");
			                }
			                catch(Exception ex)
			                {
			                	filaBuena          = false;
			                	bufferErroresCenso.append(Utils.join("Error en el campo 'Extraprima de ocupacion' (I) de la fila ",fila," "));
			                }
		                    finally
		                    {
		                    	bufferLineaStr.append(Utils.join("",this.extraerStringDeCelda(row.getCell(8)),"-"));
		                    }
			                
		                	try
		                	{
				                auxCell=row.getCell(9);
				                logger.info("PESO: "+(auxCell!=null?String.format("%.2f",auxCell.getNumericCellValue())+"|":"|"));
				                bufferLinea.append(auxCell!=null?String.format("%.2f",auxCell.getNumericCellValue())+"|":"|");
			                }
			                catch(Exception ex)
			                {
			                	filaBuena          = false;
			                	bufferErroresCenso.append(Utils.join("Error en el campo 'Peso' (J) de la fila ",fila," "));
			                }
		                    finally
		                    {
		                    	bufferLineaStr.append(Utils.join("",this.extraerStringDeCelda(row.getCell(9)),"-"));
		                    }
			                
			                try
		                	{
				                auxCell=row.getCell(10);
				                logger.info("ESTATURA: "+(auxCell!=null?String.format("%.2f",auxCell.getNumericCellValue())+"|":"|"));
				                bufferLinea.append(auxCell!=null?String.format("%.2f",auxCell.getNumericCellValue())+"|":"|");
			                }
			                catch(Exception ex)
			                {
			                	filaBuena          = false;
			                	bufferErroresCenso.append(Utils.join("Error en el campo 'Estatura' (K) de la fila ",fila," "));
			                }
		                    finally
		                    {
		                    	bufferLineaStr.append(Utils.join("",this.extraerStringDeCelda(row.getCell(10)),"-"));
		                    }
			                
			                try
		                	{
				                auxCell=row.getCell(11);
				                logger.info("EXTRAPRIMA SOBREPESO: "+(auxCell!=null?String.format("%.2f",auxCell.getNumericCellValue())+"|":"|"));
				                bufferLinea.append(auxCell!=null?String.format("%.2f",auxCell.getNumericCellValue())+"|":"|");
			                }
			                catch(Exception ex)
			                {
			                	filaBuena          = false;
			                	bufferErroresCenso.append(Utils.join("Error en el campo 'Extraprima de sobrepeso' (L) de la fila ",fila," "));
			                }
		                    finally
		                    {
		                    	bufferLineaStr.append(Utils.join("",this.extraerStringDeCelda(row.getCell(11)),"-"));
		                    }
			                
			                try
		                	{
				                logger.info("GRUPO: "+String.format("%.0f",row.getCell(12).getNumericCellValue())+"|");
				                bufferLinea.append(String.format("%.0f",row.getCell(12).getNumericCellValue())+"|");
				                
				                double cdgrupo=row.getCell(12).getNumericCellValue();
				                if(cdgrupo>nGrupos||cdgrupo<1d)
				                {
				                	filaBuena = false;
				                	bufferErroresCenso.append(Utils.join("No existe el grupo (M) de la fila ",fila," "));
				                }
				                else
				                {
				                	bGrupos[new Double(cdgrupo).intValue()-1]=true;
				                }
			                }
			                catch(Exception ex)
			                {
			                	filaBuena          = false;
			                	bufferErroresCenso.append(Utils.join("Error en el campo 'Grupo' (M) de la fila ",fila," "));
			                }
		                    finally
		                    {
		                    	bufferLineaStr.append(Utils.join("",this.extraerStringDeCelda(row.getCell(12)),"-"));
		                    }
			                
			                bufferLinea.append("\n");
			                logger.info("** NUEVA_FILA **");
			                
			                if(filaBuena)
			                {
			                	familias.put(nFamilia,Utils.join(familias.get(nFamilia),bufferLinea.toString()));
			                	filasProcesadas = filasProcesadas + 1;
			                }
			                else
			                {
			                	filasErrores = filasErrores + 1;
			                	bufferErroresCenso.append(Utils.join(": ",bufferLineaStr.toString(),"\n"));
			                	estadoFamilias.put(nFamilia,false);
			                	
			                	if(!errorFamilia.containsKey(nFamilia))
			                	{
			                		errorFamilia.put(nFamilia,fila);
			                	}
			                }
			            }
			            
			            logger.debug("\nFamilias: {}\nEstado familias: {}\nErrorFamilia: {}\nTitulares: {}"
			            		,familias,estadoFamilias,errorFamilia,titulares);
			            
			            for(Entry<Integer,Boolean>en:estadoFamilias.entrySet())
			            {
			            	int     n = en.getKey();
			            	boolean v = en.getValue();
			            	if(v)
			            	{
			            		output.print(familias.get(n));
			            	}
			            	else
			            	{
			            		bufferErroresCenso.append(Utils.join("La familia ",n," del titular '",titulares.get(n),"' no fue incluida por error en la fila ",errorFamilia.get(n),"\n"));
			            	}
			            }
			            
			            if(exito&&!clasif.equals(LINEA))
			            {
				            ManagerRespuestaSmapVO familiasMinimas = cotizacionManager.obtenerParametrosCotizacion(
				            		ParametroCotizacion.NUMERO_FAMILIAS_COTI_COLECTIVO
				            		,cdramo
				            		,cdtipsit
				            		,null
				            		,null
				            		);
				            int nMin = 0;
				            try
				            {
				            	nMin = Integer.parseInt(familiasMinimas.getSmap().get("P1VALOR"));
				            }
				            catch(Exception ex)
				            {
				            	exito     = false;
				            	respuesta = Utils.join("Error al validar el n\u00FAmero de titulares #",System.currentTimeMillis());
				            	logger.error(respuesta,ex);
				            }
				            
				            if(exito&&nFamilia<nMin)
				            {
				            	exito     = false;
				            	respuesta = Utils.join("El n\u00FAmero de titulares debe ser por lo menos "
				            			,nMin
				            			,", se encontraron "
				            			,nFamilia
				            			," #",System.currentTimeMillis());
				            	logger.error(respuesta);
				            }
			            }
			            
			            input.close();
			            output.close();
			            logger.info(""
			            		+ "\n###### "+archivoTxt.getAbsolutePath()+" ######"
								+ "\n##############################################"
								);
					}
					else //censo agrupado
					{
						//Iterate through each rows one by one
						logger.info(""
								+ "\n##############################################"
								+ "\n###### "+archivoTxt.getAbsolutePath()+" ######"
								);
			            Iterator<Row> rowIterator = sheet.iterator();
			            int fila = 0;
			            while (rowIterator.hasNext()&&exito) 
			            {
			                Row row = rowIterator.next();
			                
			                if(Utils.isRowEmpty(row))
			                {
			                	break;
			                }
			                
			                boolean       filaBuena      = true;
			                StringBuilder bufferLinea    = new StringBuilder("");
			                StringBuilder bufferLineaStr = new StringBuilder("");
			                
			                fila        = fila + 1;
			                filasLeidas = filasLeidas + 1;
			                
		                	try
		                	{
				                logger.info("EDAD: "+String.format("%.0f",row.getCell(0).getNumericCellValue())+"|");
				                bufferLinea.append(String.format("%.0f",row.getCell(0).getNumericCellValue())+"|");
			                }
			                catch(Exception ex)
			                {
			                	filaBuena = false;
			                	bufferErroresCenso.append(Utils.join("Error en el campo 'Edad' (A) de la fila ",fila," "));
			                }
		                    finally
		                    {
		                    	bufferLineaStr.append(Utils.join("",this.extraerStringDeCelda(row.getCell(0)),"-"));
		                    }
			                
			                try
		                	{
			                	String sexo = row.getCell(1).getStringCellValue();
			                	if(!"H".equals(sexo)
			                			&&!"M".equals(sexo))
			                	{
			                		throw new ApplicationException("Genero (sexo) incorrecto");
			                	}
				                logger.info("SEXO: "+sexo+"|");
				                bufferLinea.append(sexo+"|");
			                }
			                catch(Exception ex)
			                {
			                	filaBuena = false;
			                	bufferErroresCenso.append(Utils.join("Error en el campo 'Sexo' (B) de la fila ",fila," "));
			                }
		                    finally
		                    {
		                    	bufferLineaStr.append(Utils.join("",this.extraerStringDeCelda(row.getCell(1)),"-"));
		                    }
			                
			                try
		                	{
				                logger.info("CUANTOS: "+String.format("%.0f",row.getCell(2).getNumericCellValue())+"|");
				                bufferLinea.append(String.format("%.0f",row.getCell(2).getNumericCellValue())+"|");
				                
				                nSituac = nSituac + (int)row.getCell(2).getNumericCellValue();
			                }
			                catch(Exception ex)
			                {
			                	filaBuena = false;
			                	bufferErroresCenso.append(Utils.join("Error en el campo 'Cantidad' (C) de la fila ",fila," "));
			                }
		                    finally
		                    {
		                    	bufferLineaStr.append(Utils.join("",this.extraerStringDeCelda(row.getCell(2)),"-"));
		                    }
			                
			                try
		                	{
				                logger.info("GRUPO: "+String.format("%.0f",row.getCell(3).getNumericCellValue())+"|");
				                bufferLinea.append(String.format("%.0f",row.getCell(3).getNumericCellValue())+"|");
				                
				                double cdgrupo=row.getCell(3).getNumericCellValue();
				                if(cdgrupo>nGrupos||cdgrupo<1d)
				                {
				                	filaBuena = false;
				                	bufferErroresCenso.append(Utils.join("No existe el grupo (D) de la fila ",fila," "));
				                }
				                else
				                {
				                	bGrupos[new Double(cdgrupo).intValue()-1]=true;
				                }
			                }
			                catch(Exception ex)
			                {
			                	filaBuena = false;
			                	bufferErroresCenso.append(Utils.join("Error en el campo 'Grupo' (D) de la fila ",fila," "));
			                }
		                    finally
		                    {
		                    	bufferLineaStr.append(Utils.join("",this.extraerStringDeCelda(row.getCell(3)),"-"));
		                    }
			                
			                bufferLinea.append("\n");
			                logger.info("** NUEVA_FILA **");
			                
			                if(filaBuena)
			                {
			                	output.print(bufferLinea.toString());
			                	filasProcesadas = filasProcesadas + 1;
			                }
			                else
			                {
			                	filasErrores = filasErrores + 1;
			                	bufferErroresCenso.append(Utils.join(": ",bufferLineaStr.toString(),"\n"));
			                }
			            }
			            input.close();
			            output.close();
			            logger.info(""
			            		+ "\n###### "+archivoTxt.getAbsolutePath()+" ######"
								+ "\n##############################################"
								);
					}
					
					if(exito)
					{
						smap1.put("erroresCenso"    , bufferErroresCenso.toString());
						smap1.put("filasLeidas"     , Integer.toString(filasLeidas));
						smap1.put("filasProcesadas" , Integer.toString(filasProcesadas));
						smap1.put("filasErrores"    , Integer.toString(filasErrores));
					}
					
					if(exito)
					{
						if(clasif.equals(LINEA)&&nSituac>49)
						{
							long timestamp  = System.currentTimeMillis();
							exito           = false;
							respuesta       = Utils.join("No se permiten mas de 49 asegurados #",timestamp);
							respuestaOculta = respuesta;
							logger.error(respuesta);
						}
						else if(!clasif.equals(LINEA)&&nSituac<50)
						{
							long timestamp  = System.currentTimeMillis();
							exito           = false;
							respuesta       = Utils.join("No se permiten menos de 50 asegurados #",timestamp);
							respuestaOculta = respuesta;
							logger.error(respuesta);
						}
					}
					
					if(exito)
					{
						int cdgrupoVacio=0;
						for(int i=0;i<nGrupos;i++)
						{
							if(!bGrupos[i])
							{
								cdgrupoVacio=i+1;
								break;
							}
						}
						if(cdgrupoVacio>0)
						{
							long etimestamp = System.currentTimeMillis();
		                	exito           = false;
		                	respuesta       = Utils.join("No hay asegurados para el grupo ",cdgrupoVacio," #"+etimestamp);
		                	logger.error(respuesta);
						}
					}
					
					if(exito)
					{
						exito = FTPSUtils.upload
								(
									this.getText("dominio.server.layouts"),
									this.getText("user.server.layouts"),
									this.getText("pass.server.layouts"),
									archivoTxt.getAbsolutePath(),
									this.getText("directorio.server.layouts")+"/"+nombreCenso
								)
								&&FTPSUtils.upload
								(
									this.getText("dominio.server.layouts2"),
									this.getText("user.server.layouts"),
									this.getText("pass.server.layouts"),
									archivoTxt.getAbsolutePath(),
									this.getText("directorio.server.layouts")+"/"+nombreCenso
								);
						
						if(!exito)
						{
							long etimestamp = System.currentTimeMillis();
							exito           = false;
							respuesta       = "Error al transferir archivo al servidor #"+etimestamp;
							respuestaOculta = respuesta;
							logger.error(respuesta);
						}
					}
				}
			}
			
			//pl censo
			if(exito&&(!hayTramite||hayTramiteVacio||censoAtrasado||resubirCenso))
			{
				try
				{
					
					String cdplanes[]    = new String[5];
					
					int nGru=0;
					for(Map<String,Object>iGrupo:olist1)
					{
						String  cdgrupo      = (String)iGrupo.get("letra");
						String  cdplan       = (String)iGrupo.get("cdplan");
						Integer indGrupo     = Integer.valueOf(cdgrupo);
						cdplanes[indGrupo-1] = cdplan;
						nGru=nGru+1;
					}
					
					if(esCensoSolo||sincenso)
					{
						LinkedHashMap<String,Object>params=new LinkedHashMap<String,Object>();
						params.put("param01",sincenso?"layout_censo"+nGru+".txt":nombreCenso);
						params.put("param02",cdunieco);
						params.put("param03",cdramo);
						params.put("param04","W");
						params.put("param05",nmpoliza);
						params.put("param06",cdedo);
						params.put("param07",cdmunici);
						params.put("param08",cdplanes[0]);
						params.put("param09",cdplanes[1]);
						params.put("param10",cdplanes[2]);
						params.put("param11",cdplanes[3]);
						params.put("param12",cdplanes[4]);
						params.put("param13","N");
						storedProceduresManager.procedureVoidCall(
								ObjetoBD.CARGAR_CENSO.getNombre(), params, null);
					}
					else
					{
						LinkedHashMap<String,Object>params=new LinkedHashMap<String,Object>();
						params.put("param01",nombreCenso);
						params.put("param02",cdunieco);
						params.put("param03",cdramo);
						params.put("param04","W");
						params.put("param05",nmpoliza);
						params.put("param06",cdedo);
						params.put("param07",cdmunici);
						params.put("param08",cdplanes[0]);
						params.put("param09",cdplanes[1]);
						params.put("param10",cdplanes[2]);
						params.put("param11",cdplanes[3]);
						params.put("param12",cdplanes[4]);
						storedProceduresManager.procedureVoidCall(
								ObjetoBD.CARGAR_CENSO_AGRUPADO.getNombre(), params, null);
					}
				}
				catch(Exception ex)
				{
					long etimestamp = System.currentTimeMillis();
					logger.error(etimestamp+" error al ejecutar pl de censo",ex);
					respuesta       = "Error al cotizar #"+etimestamp;
					respuestaOculta = ex.getMessage();
					exito           = false;
				}
			}
			
			if((exito&&(!hayTramite||hayTramiteVacio||censoAtrasado||resubirCenso))
					&&StringUtils.isBlank(nombreCensoConfirmado)
			)
			{
				smap1.put("nombreCensoParaConfirmar" , nombreCenso);
				exito     = true;
				respuesta = Utils.join("Se ha revisado el censo [REV. ",System.currentTimeMillis(),"]");
				logger.info(respuesta);
				return SUCCESS;
			}
			
			if(exito)
			{
				tvalositSigsvdefTvalogarContratanteTramiteSigsvalipolObject aux = this.tvalositSigsvdefTvalogarContratanteTramiteSigsvalipol(
						clasif    , LINEA         , LINEA_EXTENDIDA
						,cdunieco , cdramo        , nmpoliza
						,cdtipsit , hayTramite    , hayTramiteVacio
						,user     , cdelemento    , ntramiteVacio
						,false    , ntramite      , cdagente
						,sincenso , censoAtrasado , resubirCenso
						,cdperpag , cdsisrol      , complemento
						,asincrono,cdmunici,cdedo,codpostal
						);
				exito           = aux.exito;
				respuesta       = aux.respuesta;
				respuestaOculta = aux.respuestaOculta;
			}
			
			if(exito)
			{
				if(StringUtils.isBlank(respuesta))
				{
					respuesta       = "Se gener&oacute; el tr&aacute;mite "+smap1.get("ntramite");
				    respuestaOculta = "Todo OK";
				}
			}
			
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			logger.error(timestamp+" error inesperado al cotizar grupo",ex);
			respuesta       = "Error inesperado al cotizar #"+timestamp;
			respuestaOculta = ex.getMessage();
			exito           = false;
		}
		logger.info(""
				+ "\n###### generarTramiteGrupo ######"
				+ "\n#################################"
				);
		return SUCCESS;
	}
	
	private tvalositSigsvdefTvalogarContratanteTramiteSigsvalipolObject tvalositSigsvdefTvalogarContratanteTramiteSigsvalipol(
			String clasif
			,String LINEA
			,String LINEA_EXTENDIDA
			,String cdunieco
			,String cdramo
			,String nmpoliza
			,String cdtipsit
			,boolean hayTramite
			,boolean hayTramiteVacio
			,String cdusuari
			,String cdelemento
			,String ntramiteVacio
			,boolean reinsertaContratante
			,String ntramite
			,String cdagente
			,boolean sincenso
			,boolean censoAtrasado
			,boolean resubirCenso
			,String cdperpag
			,String cdsisrol
			,boolean complemento
			,boolean asincrono
			,String cdmunici
			,String cdedo
			,String codpostal
			)
	{
		logger.debug(
				new StringBuilder()
				.append("\n###########################################################")
				.append("\n## tvalositSigsvdefTvalogarContratanteTramiteSigsvalipol ##")
				.append("\n## clasif: ")              .append(clasif)
				.append("\n## LINEA: ")               .append(LINEA)
				.append("\n## LINEA_EXTENDIDA: ")     .append(LINEA_EXTENDIDA)
				.append("\n## cdunieco: ")            .append(cdunieco)
				.append("\n## cdramo: ")              .append(cdramo)
				.append("\n## nmpoliza: ")            .append(nmpoliza)
				.append("\n## cdtipsit: ")            .append(cdtipsit)
				.append("\n## hayTramite: ")          .append(hayTramite)
				.append("\n## hayTramiteVacio: ")     .append(hayTramiteVacio)
				.append("\n## cdusuari: ")            .append(cdusuari)
				.append("\n## cdelemento: ")          .append(cdelemento)
				.append("\n## ntramiteVacio: ")       .append(ntramiteVacio)
				.append("\n## reinsertaContratante: ").append(reinsertaContratante)
				.append("\n## ntramite: ")            .append(ntramite)
				.append("\n## cdagente: ")            .append(cdagente)
				.append("\n## sincenso: ")            .append(sincenso)
				.append("\n## censoAtrasado: ")       .append(censoAtrasado)
				.append("\n## resubirCenso: ")        .append(resubirCenso)
				.append("\n## cdperpag: ")            .append(cdperpag)
				.append("\n## cdsisrol: ")            .append(cdsisrol)
				.append("\n## complemento: ")         .append(complemento)
				.append("\n## asincrono: ")           .append(asincrono)
				.toString()
				);
		
		tvalositSigsvdefTvalogarContratanteTramiteSigsvalipolObject resp =
				new tvalositSigsvdefTvalogarContratanteTramiteSigsvalipolObject();
		resp.exito = true;
		
		//tvalosit
		if(resp.exito)
		{
			try
			{
				if(clasif.equals(LINEA)&&LINEA_EXTENDIDA.equals("S"))
				{
					for(Map<String,Object>iGrupo:olist1)
					{
						String cdgrupo = (String)iGrupo.get("letra");
						
						//SUMA ASEGURADA Y MATERNIDAD
						String ptsumaaseg = (String)iGrupo.get("ptsumaaseg");
						String ayudamater = (String)iGrupo.get("ayudamater");
						Object incrinflL  = iGrupo.get("incrinfl");
						String incrinfl   = incrinflL!=null? incrinflL.toString() : "";
						Object extrrenoL  = iGrupo.get("extrreno");
						String extrreno   = extrrenoL!=null? extrrenoL.toString() : "";
						Object cesicomiL  = iGrupo.get("cesicomi");
						String cesicomi   = cesicomiL!=null? cesicomiL.toString() : "";
						Object pondubicL  = iGrupo.get("pondubic");
						String pondubic   = pondubicL!=null? pondubicL.toString() : "";
						Object descbonoL  = iGrupo.get("descbono");
						String descbono   = descbonoL!=null? descbonoL.toString() : "";
						Object porcgastL  = iGrupo.get("porcgast");
						String porcgast   = porcgastL!=null? porcgastL.toString() : "";
						cotizacionManager.movimientoMpolisitTvalositGrupo(
								cdunieco, cdramo, "W", nmpoliza,
								cdgrupo, ptsumaaseg, incrinfl, extrreno,
								cesicomi, pondubic, descbono, porcgast,
								(String)iGrupo.get("nombre"),ayudamater);
						
						cotizacionManager.actualizaValoresDefectoSituacion(cdunieco,cdramo,"W",nmpoliza,"0");
					}
				}
				else
				{
					for(Map<String,Object>iGrupo:olist1)
					{
						String cdgrupo = (String)iGrupo.get("letra");
						
						//SUMA ASEGURADA y ayuda maternidad
						String ptsumaaseg = (String)iGrupo.get("ptsumaaseg");
						String ayudamater = "0";
						Object incrinflL  = iGrupo.get("incrinfl");
						String incrinfl   = incrinflL!=null? incrinflL.toString() : "0";
						Object extrrenoL  = iGrupo.get("extrreno");
						String extrreno   = extrrenoL!=null? extrrenoL.toString() : "0";
						Object cesicomiL  = iGrupo.get("cesicomi");
						String cesicomi   = cesicomiL!=null? cesicomiL.toString() : "0";
						Object pondubicL  = iGrupo.get("pondubic");
						String pondubic   = pondubicL!=null? pondubicL.toString() : "0";
						Object descbonoL  = iGrupo.get("descbono");
						String descbono   = descbonoL!=null? descbonoL.toString() : "0";
						Object porcgastL  = iGrupo.get("porcgast");
						String porcgast   = porcgastL!=null? porcgastL.toString() : "0";
						
						List<Map<String,String>>tvalogars=(List<Map<String,String>>)iGrupo.get("tvalogars");
						for(Map<String,String>iTvalogar:tvalogars)
						{
							String cdgarant  = iTvalogar.get("cdgarant");
							boolean amparada = "S".equals(iTvalogar.get("amparada"));
							if("4AYM".equalsIgnoreCase(cdgarant) && amparada )
							{
								ayudamater = iTvalogar.get("parametros.pv_otvalor001");
							}
						}
						
						cotizacionManager.movimientoMpolisitTvalositGrupo(
								cdunieco, cdramo, "W", nmpoliza,
								cdgrupo, ptsumaaseg, incrinfl, extrreno,
								cesicomi, pondubic, descbono, porcgast,
								(String)iGrupo.get("nombre"),ayudamater);
					}
				}
			}
			catch(Exception ex)
			{
				long timestamp       = System.currentTimeMillis();
				resp.exito           = false;
				resp.respuesta       = "Error al guardar grupos #"+timestamp;
				resp.respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		
		if(exito && StringUtils.isNotBlank(codpostal)){
			try {
				cotizacionManager.actualizaDomicilioAseguradosColectivo(cdunieco, cdramo, "W", nmpoliza, "0", codpostal, cdedo, cdmunici);
			} catch (Exception ex) {
				long timestamp       = System.currentTimeMillis();
				resp.exito           = false;
				resp.respuesta       = "Error al guardar domiciio tvalopol #"+timestamp;
				resp.respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		
		if(resp.exito
				&&(!hayTramite||hayTramiteVacio)
				&&
				(
					RolSistema.AGENTE.getCdsisrol().equals(cdsisrol)
					||RolSistema.EJECUTIVO_INTERNO.getCdsisrol().equals(cdsisrol)
					||RolSistema.MESA_DE_CONTROL.getCdsisrol().equals(cdsisrol)
					||RolSistema.SUSCRIPTOR.getCdsisrol().equals(cdsisrol)
				)
		)
		{
			asincrono = true;
		}
		
		//sigsvdef
		if(resp.exito&&(!hayTramite||hayTramiteVacio||censoAtrasado||resubirCenso||complemento)&&asincrono==false)
		{
			try
			{
				/*
				Map<String,String> mapCoberturas=new HashMap<String,String>(0);
	            mapCoberturas.put("pv_cdunieco_i" , cdunieco);
	            mapCoberturas.put("pv_cdramo_i"   , cdramo);
	            mapCoberturas.put("pv_estado_i"   , "W");
	            mapCoberturas.put("pv_nmpoliza_i" , nmpoliza);
	            mapCoberturas.put("pv_nmsituac_i" , "0");
	            mapCoberturas.put("pv_nmsuplem_i" , "0");
	            mapCoberturas.put("pv_cdgarant_i" , "TODO");
	            mapCoberturas.put("pv_cdtipsup_i" , "1");
	            kernelManager.coberturas(mapCoberturas);
	            */
	            
	            cotizacionManager.ejecutaValoresDefectoConcurrente(
	            		cdunieco
	            		,cdramo
	            		,"W"
	            		,nmpoliza
	            		,"0"
	            		,"0"
	            		,"1"
	            		,cdperpag
	            		);
	            
	            try
	            {
	            	serviciosManager.grabarEvento(new StringBuilder("\nCotizacion grupo")
	            	    ,"COTIZACION" //cdmodulo
	            	    ,"COTIZA"     //cdevento
	            	    ,new Date()   //fecha
	            	    ,cdusuari
	            	    ,((UserVO)session.get("USUARIO")).getRolActivo().getClave()
	            	    ,ntramite
	            	    ,cdunieco
	            	    ,cdramo
	            	    ,"W"
	            	    ,nmpoliza
	            	    ,nmpoliza
	            	    ,cdagente
	            	    ,null
	            	    ,null);
	            }
	            catch(Exception ex)
	            {
	            	logger.error("Error al grabar evento, sin impacto",ex);
	            }
			}
			catch(Exception ex)
			{
				long timestamp       = System.currentTimeMillis();
				resp.exito           = false;
				resp.respuesta       = "Error al insertar valores por defecto para las coberturas #"+timestamp;
				resp.respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		//tvalogar
		if(resp.exito&&asincrono==false)
		{
			try
			{
				if(clasif.equals(LINEA)&&LINEA_EXTENDIDA.equals("S"))
				{
					for(Map<String,Object>iGrupo:olist1)
					{
						String cdgrupo = (String)iGrupo.get("letra");
						
						//HOSPITALIZACION (DEDUCIBLE)
						String cdgarant = "4HOS";
						String cdatribu = "001";
						String valor    = (String)iGrupo.get("deducible");
						cotizacionManager.movimientoTvalogarGrupo(cdunieco, cdramo, "W", nmpoliza, "0", cdtipsit, cdgrupo, cdgarant, "V", cdatribu, valor);
						
						//ASISTENCIA INTERNACIONAL VIAJES --AHORA MEDICAMENTOS
						String asisinte = (String)iGrupo.get("asisinte");
						cdgarant = "4MED";
						if(Integer.parseInt(asisinte)>0)
						{
							cotizacionManager.movimientoMpoligarGrupo(
									cdunieco
									,cdramo
									,"W"
									,nmpoliza
									,"0"
									,cdtipsit
									,cdgrupo
									,cdgarant
									,"V"
									,"001"
									,Constantes.INSERT_MODE
									,"S"
									);
							
							cotizacionManager.movimientoTvalogarGrupo(
									cdunieco
									,cdramo
									,"W" //estado
									,nmpoliza
									,"0" //nmsuplem
									,cdtipsit
									,cdgrupo
									,cdgarant
									,"V" //status
									,"001"
									,asisinte
									);
						}
						else
						{
							cotizacionManager.movimientoMpoligarGrupo(
									cdunieco, cdramo, "W", nmpoliza, "0", cdtipsit, cdgrupo, cdgarant, "V", "001", Constantes.DELETE_MODE, null);
						}
						
						//EMERGENCIA EXTRANJERO
						String emerextr = (String)iGrupo.get("emerextr");
						cdgarant = "4EE";
						if(emerextr.equalsIgnoreCase("S"))
						{
							cotizacionManager.movimientoMpoligarGrupo(
									cdunieco, cdramo, "W", nmpoliza, "0", cdtipsit, cdgrupo, cdgarant, "V", "001", Constantes.INSERT_MODE, null);
						}
						else
						{
							cotizacionManager.movimientoMpoligarGrupo(
									cdunieco, cdramo, "W", nmpoliza, "0", cdtipsit, cdgrupo, cdgarant, "V", "001", Constantes.DELETE_MODE, null);
						}
					}
				}
				else
				{
					for(Map<String,Object>iGrupo:olist1)
					{
						String cdgrupo = (String)iGrupo.get("letra");
						
						List<Map<String,String>>tvalogars=(List<Map<String,String>>)iGrupo.get("tvalogars");
						for(Map<String,String>iTvalogar:tvalogars)
						{
							String cdgarant  = iTvalogar.get("cdgarant");
							boolean amparada = StringUtils.isNotBlank(iTvalogar.get("amparada"))
									&&iTvalogar.get("amparada").equalsIgnoreCase("S");
							
							//if(!cdgarant.equalsIgnoreCase("4AYM"))
							//{
								if(amparada)
								{
									cotizacionManager.movimientoMpoligarGrupo(
											cdunieco, cdramo, "W", nmpoliza, "0", cdtipsit, cdgrupo, cdgarant, "V", "001", Constantes.INSERT_MODE, null);
									//buscar cdatribus
									boolean hayAtributos=false;
									Map<String,String>listaCdatribu=new HashMap<String,String>();
									for(Entry<String,String>iAtribTvalogar:iTvalogar.entrySet())
									{
										String key=iAtribTvalogar.getKey();
										if(key!=null
												&&key.length()>"parametros.pv_otvalor".length()
												&&key.substring(0, "parametros.pv_otvalor".length()).equalsIgnoreCase("parametros.pv_otvalor"))
										{
											hayAtributos=true;
											listaCdatribu.put(key.substring("parametros.pv_otvalor".length(), key.length()),iAtribTvalogar.getValue());
										}
									}
									if(hayAtributos)
									{
										for(Entry<String,String>atributo:listaCdatribu.entrySet())
										{
											if(StringUtils.isNotBlank(atributo.getValue()))
											{
											    cotizacionManager.movimientoTvalogarGrupo(
													cdunieco, cdramo, "W", nmpoliza, "0", cdtipsit, cdgrupo, cdgarant, "V",
													atributo.getKey(), atributo.getValue());
											}
										}
									}
								}
								else
								{
									cotizacionManager.movimientoMpoligarGrupo(
											cdunieco, cdramo, "W", nmpoliza, "0", cdtipsit, cdgrupo, cdgarant, "V", "001", Constantes.DELETE_MODE, null);
								}
							//}
						}
					}
				}
			}
			catch(Exception ex)
			{
				long timestamp       = System.currentTimeMillis();
				resp.exito           = false;
				resp.respuesta       = "Error al guardar las coberturas #"+timestamp; 
				resp.respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		//contratante
		if(resp.exito)
		{
			try
			{
				String cdperson  = smap1.get("cdperson");
				String exiper    = "N";
				String cdideper_ = smap1.get("cdideper_");
				String cdideext_ = smap1.get("cdideext_");
				
				boolean nuevoCdperson = StringUtils.isBlank(cdperson);
				if(nuevoCdperson)
				{
					Map<String,Object>cdpersonMap=storedProceduresManager.procedureParamsCall(
							ObjetoBD.GENERAR_CDPERSON.getNombre(),
							new LinkedHashMap<String,Object>(),
							null,
							new String[]{"pv_cdperson_o"},
							null);
					cdperson = (String)cdpersonMap.get("pv_cdperson_o");
				}else{
					Map<String,String> datosCont = cotizacionManager.obtieneDatosContratantePoliza(cdunieco, cdramo, "W", nmpoliza, "0");
					if(datosCont != null && !datosCont.isEmpty() && Constantes.SI.equalsIgnoreCase(datosCont.get("SWEXIPER"))){
						exiper = "S";
					}
				}
				
				if(nuevoCdperson||reinsertaContratante||censoAtrasado||resubirCenso)
				{
					if(!Constantes.SI.equalsIgnoreCase(exiper)){
						LinkedHashMap<String,Object> parametros=new LinkedHashMap<String,Object>(0);
						parametros.put("param01_pv_cdperson_i"    , cdperson);
						parametros.put("param02_pv_cdtipide_i"    , "1");
						parametros.put("param03_pv_cdideper_i"    , cdideper_);
						parametros.put("param04_pv_dsnombre_i"    , smap1.get("nombre"));
						parametros.put("param05_pv_cdtipper_i"    , "1");
						parametros.put("param06_pv_otfisjur_i"    , "M");
						parametros.put("param07_pv_otsexo_i"      , "H");
						parametros.put("param08_pv_fenacimi_i"    , new Date());
						parametros.put("param09_pv_cdrfc_i"       , smap1.get("cdrfc"));
						parametros.put("param10_pv_dsemail_i"     , "");
						parametros.put("param11_pv_dsnombre1_i"   , null);
						parametros.put("param12_pv_dsapellido_i"  , null);
						parametros.put("param13_pv_dsapellido1_i" , null);
						parametros.put("param14_pv_feingreso_i"   , new Date());
						parametros.put("param15_pv_cdnacion_i"    , "001");
						parametros.put("param16"                  , null);
						parametros.put("param17"                  , null);
						parametros.put("param18"                  , null);
						parametros.put("param19"                  , null);
						parametros.put("param20"                  , null);
						parametros.put("param21"                  , cdideext_);
						parametros.put("param22"                  , null);
						parametros.put("param23"                  , null);
						parametros.put("param24_pv_accion_i"      , "I");
						String[] tipos=new String[]{
								"VARCHAR","VARCHAR","VARCHAR","VARCHAR",
								"VARCHAR","VARCHAR","VARCHAR","DATE",
								"VARCHAR","VARCHAR","VARCHAR","VARCHAR",
								"VARCHAR","DATE"   ,"VARCHAR","VARCHAR",
								"VARCHAR","VARCHAR","VARCHAR","VARCHAR",
								"VARCHAR","VARCHAR","VARCHAR","VARCHAR"
						};
						storedProceduresManager.procedureVoidCall(ObjetoBD.MOV_MPERSONA.getNombre(), parametros, tipos);
					}
				}
				
				LinkedHashMap<String,Object> parametros=new LinkedHashMap<String,Object>(0);
				parametros.put("param01_pv_cdunieco_i" , cdunieco);
				parametros.put("param02_pv_cdramo_i"   , cdramo);
				parametros.put("param03_pv_estado_i"   , "W");
				parametros.put("param04_pv_nmpoliza_i" , nmpoliza);
				parametros.put("param05_pv_nmsituac_i" , "0");
				parametros.put("param06_pv_cdrol_i"    , "1");
				parametros.put("param07_pv_cdperson_i" , cdperson);
				parametros.put("param08_pv_nmsuplem_i" , "0");
				parametros.put("param09_pv_status_i"   , "V");
				parametros.put("param10_pv_nmorddom_i" , "1");
				parametros.put("param11_pv_swreclam_i" , null);
				parametros.put("param12_pv_accion_i"   , "I");
				parametros.put("param13_pv_swexiper_i" , exiper);
				storedProceduresManager.procedureVoidCall(ObjetoBD.MOV_MPOLIPER.getNombre(), parametros, null);
				
				
				logger.debug("VALOR DE SWEXIPER : "+ exiper);
				
				if(!Constantes.SI.equalsIgnoreCase(exiper)){
					Map<String,String> paramDomicil = new HashMap<String, String>();
					paramDomicil.put("pv_cdperson_i" , cdperson);
					paramDomicil.put("pv_nmorddom_i" , "1");
					paramDomicil.put("pv_msdomici_i" , smap1.get("dsdomici"));
					paramDomicil.put("pv_nmtelefo_i" , null);
					paramDomicil.put("pv_cdpostal_i" , smap1.get("codpostal"));
					paramDomicil.put("pv_cdedo_i"    , smap1.get("cdedo"));
					paramDomicil.put("pv_cdmunici_i" , smap1.get("cdmunici"));
					paramDomicil.put("pv_cdcoloni_i" , null);
					paramDomicil.put("pv_nmnumero_i" , smap1.get("nmnumero"));
					paramDomicil.put("pv_nmnumint_i" , smap1.get("nmnumint"));
					paramDomicil.put("pv_accion_i"   , Constantes.INSERT_MODE);
					kernelManager.pMovMdomicil(paramDomicil);
				}
				
			}
			catch(Exception ex)
			{
				long timestamp       = System.currentTimeMillis();
				resp.exito           = false;
				resp.respuesta       = "Error al guardar el contratante #"+timestamp;
				resp.respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		//tramite
		if(resp.exito&&(!hayTramite||hayTramiteVacio||censoAtrasado))
		{
			try
			{
				if(!hayTramite&&!hayTramiteVacio)//es agente
				{
					/*Map<String,Object>params=new HashMap<String,Object>();
					params.put("pv_cdunieco_i"   , cdunieco);
					params.put("pv_cdramo_i"     , cdramo);
					params.put("pv_estado_i"     , "W");
					params.put("pv_nmpoliza_i"   , "0");
					params.put("pv_nmsuplem_i"   , "0");
					params.put("pv_cdsucadm_i"   , cdunieco);
					params.put("pv_cdsucdoc_i"   , cdunieco);
					params.put("pv_cdtiptra_i"   , TipoTramite.POLIZA_NUEVA.getCdtiptra());
					params.put("pv_ferecepc_i"   , new Date());
					params.put("pv_cdagente_i"   , cdagente);
					params.put("pv_referencia_i" , null);
					params.put("pv_nombre_i"     , null);
					params.put("pv_festatus_i"   , new Date());
					params.put("pv_status_i"     , EstatusTramite.EN_ESPERA_DE_COTIZACION.getCodigo());
					params.put("pv_comments_i"   , null);
					params.put("pv_nmsolici_i"   , nmpoliza);
					params.put("pv_cdtipsit_i"   , cdtipsit);
					params.put("pv_otvalor01"    , clasif);
					params.put("pv_otvalor02"    , sincenso ? "S" : "N");
					params.put("cdusuari"        , cdusuari);
					params.put("cdsisrol"        , cdsisrol);
					WrapperResultados wr=kernelManager.PMovMesacontrol(params);
					String ntramiteNew = (String)wr.getItemMap().get("ntramite");*/
					
					Map<String,String> valores = new LinkedHashMap<String,String>();
					valores.put("otvalor01" , clasif);
					valores.put("otvalor02" , sincenso ? "S" : "N");
					
					String ntramiteNew = mesaControlManager.movimientoTramite(
							cdunieco
							,cdramo
							,"W"
							,"0"
							,"0"
							,cdunieco
							,cdunieco
							,TipoTramite.POLIZA_NUEVA.getCdtiptra()
							,new Date()
							,cdagente
							,null
							,null
							,new Date()
							,EstatusTramite.EN_ESPERA_DE_COTIZACION.getCodigo()
							,null
							,nmpoliza
							,cdtipsit
							,cdusuari
							,cdsisrol
							,null //swimpres
							,null //cdtipflu
							,null //cdflujomc
							,valores, null
							);
					smap1.put("ntramite",ntramiteNew);
					
					/*Map<String,Object>parDmesCon=new LinkedHashMap<String,Object>(0);
	            	parDmesCon.put("pv_ntramite_i"   , ntramiteNew);
	            	parDmesCon.put("pv_feinicio_i"   , new Date());
	            	parDmesCon.put("pv_cdclausu_i"   , null);
	            	parDmesCon.put("pv_comments_i"   , "Se guard\u00f3 un nuevo tr\u00e1mite en mesa de control desde cotizaci\u00f3n de agente");
	            	parDmesCon.put("pv_cdusuari_i"   , cdusuari);
	            	parDmesCon.put("pv_cdmotivo_i"   , null);
	            	parDmesCon.put("pv_cdsisrol_i"   , cdsisrol);
	            	kernelManager.movDmesacontrol(parDmesCon);*/
					mesaControlManager.movimientoDetalleTramite(
							ntramiteNew
							,new Date()
							,null
							,"Se guard\u00f3 un nuevo tr\u00e1mite en mesa de control desde cotizaci\u00f3n de agente"
							,cdusuari
							,null
							,cdsisrol
							,"S"
							);
	            	
	            	smap1.put("nombreUsuarioDestino"
	            			,cotizacionManager.turnaPorCargaTrabajo(ntramiteNew,"COTIZADOR",EstatusTramite.EN_ESPERA_DE_COTIZACION.getCodigo())
	            			);
	            	
	            	try
		            {
		            	serviciosManager.grabarEvento(new StringBuilder("\nNuevo tramite grupo")
		            	    ,"EMISION"    //cdmodulo
		            	    ,"GENTRAGRUP" //cdevento
		            	    ,new Date()   //fecha
		            	    ,cdusuari
		            	    ,((UserVO)session.get("USUARIO")).getRolActivo().getClave()
		            	    ,ntramiteNew
		            	    ,cdunieco
		            	    ,cdramo
		            	    ,"W"
		            	    ,nmpoliza
		            	    ,nmpoliza
		            	    ,cdagente
		            	    ,null
		            	    ,null);
		            }
		            catch(Exception ex)
		            {
		            	logger.error("Error al grabar evento, sin impacto",ex);
		            }
				}
				else
				{
					Map<String,Object>params=new HashMap<String,Object>();
					String ntramiteActualiza = ntramite;
					if(hayTramiteVacio)
					{
						ntramiteActualiza = ntramiteVacio;
					}
					kernelManager.mesaControlUpdateSolici(ntramiteActualiza, nmpoliza);
					params.put("pv_ntramite_i"  , ntramiteActualiza);
					params.put("pv_otvalor01_i" , clasif);
					params.put("pv_otvalor02_i" , sincenso ? "S" : "N");
					siniestrosManager.actualizaOTValorMesaControl(params);
				}
			}
			catch(Exception ex)
			{
				long timestamp       = System.currentTimeMillis();
				resp.exito           = false;
				resp.respuesta       = "Error al guardar tr&aacute;mite #"+timestamp;
				resp.respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		//mpoliage
		if(resp.exito&&(!hayTramite||hayTramiteVacio))
		{
			try
			{
				Map<String,String>datosAgenteExterno=cotizacionManager.obtenerDatosAgente(cdagente,cdramo);
    			String nmcuadro=datosAgenteExterno.get("NMCUADRO");
				
				Map<String,Object>map3=new LinkedHashMap<String,Object>(0);
		        map3.put("pv_cdunieco_i" , cdunieco);
		        map3.put("pv_cdramo_i"   , cdramo);
		        map3.put("pv_estado_i"   , "W");
		        map3.put("pv_nmpoliza_i" , nmpoliza);
		        map3.put("pv_cdagente_i" , cdagente);
		        map3.put("pv_nmsuplem_i" , "0");
		        map3.put("pv_status_i"   , "V");
		        map3.put("pv_cdtipoag_i" , "1");
		        map3.put("pv_porredau_i" , "0");
		        map3.put("pv_nmcuadro_i" , nmcuadro);
		        map3.put("pv_cdsucurs_i" , null);
		        map3.put("pv_accion_i"   , "I");
		        if(!hayTramite)
		        {
		        	map3.put("pv_ntramite_i" , smap1.get("ntramite"));
		        }
		        else if(hayTramiteVacio)
		        {
		        	map3.put("pv_ntramite_i" , ntramiteVacio);
		        }
		        else
		        {
		        	map3.put("pv_ntramite_i" , ntramite);//nunca deberia ir por aqui 
		        }
		        map3.put("pv_porparti_i" , "100");
		        kernelManager.movMPoliage(map3);
			}
			catch(Exception ex)
			{
				long timestamp       = System.currentTimeMillis();
				resp.exito           = false;
				resp.respuesta       = "Error al insertar datos del agente #"+timestamp;
				resp.respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		//TDESCSUP
		if(resp.exito&&(!hayTramite||hayTramiteVacio))
		{
			
		}
		
		//sigsvalipol
		if(resp.exito&&asincrono==false)
		{
			try
			{
				/*
				Map<String,String> mapaTarificacion=new HashMap<String,String>(0);
	            mapaTarificacion.put("pv_cdusuari_i" , cdusuari);
	            mapaTarificacion.put("pv_cdelemen_i" , cdelemento);
	            mapaTarificacion.put("pv_cdunieco_i" , cdunieco);
	            mapaTarificacion.put("pv_cdramo_i"   , cdramo);
	            mapaTarificacion.put("pv_estado_i"   , "W");
	            mapaTarificacion.put("pv_nmpoliza_i" , nmpoliza);
	            mapaTarificacion.put("pv_nmsituac_i" , "0");
	            mapaTarificacion.put("pv_nmsuplem_i" , "0");
	            mapaTarificacion.put("pv_cdtipsit_i" , cdtipsit);
	            kernelManager.ejecutaASIGSVALIPOL_EMI(mapaTarificacion);
	            */
				
				cotizacionManager.ejecutaTarificacionConcurrente(
	            		cdunieco
	            		,cdramo
	            		,"W"
	            		,nmpoliza
	            		,"0"
	            		,"0"
	            		,"1"
	            		,cdperpag
	            		);
			}
			catch(Exception ex)
			{
				long timestamp       = System.currentTimeMillis();
				resp.exito           = false;
				resp.respuesta       = "Error al cotizar #"+timestamp;
				resp.respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		if(resp.exito&&asincrono==true)
		{
			cotizacionManager.procesoColectivoAsincrono(
					hayTramite
					,hayTramiteVacio
					,censoAtrasado
					,complemento
					,cdunieco
					,cdramo
					,nmpoliza
					,cdperpag
					,clasif
					,LINEA
					,LINEA_EXTENDIDA
					,olist1
					,cdtipsit
					);
		}
		
		logger.debug(Utils.log(
				 "\n###### resp=",resp
				,"\n###### tvalositSigsvdefTvalogarContratanteTramiteSigsvalipol ######"
				,"\n###################################################################"
				));
		return resp;
	}
	
	public String obtenerDetalleCotizacionGrupo()
	{
		logger.info(""
				+ "\n###########################################"
				+ "\n###### obtenerDetalleCotizacionGrupo ######"
				+ "\nsmap1: "+smap1
				);
		try
		{
			LinkedHashMap<String,Object>params=new LinkedHashMap<String,Object>();
			params.put("param1" , smap1.get("cdunieco"));
			params.put("param2" , smap1.get("cdramo"));
			params.put("param3" , smap1.get("estado"));
			params.put("param4" , smap1.get("nmpoliza"));
			params.put("param5" , smap1.get("cdplan"));
			params.put("param6" , smap1.get("cdperpag"));
			slist1=storedProceduresManager.procedureListCall(ObjetoBD.OBTIENE_DETALLE_COTI_GRUPO.getNombre(),
					params, null);
			for(Map<String,String>detalle:slist1)
			{
				String header = detalle.get("NOMBRE")+ " (" + detalle.get("PARENTESCO") +")";
				String nmsitaux3 = detalle.get("NMSITAUX");
				if(nmsitaux3.length()==1)
				{
					nmsitaux3="00"+nmsitaux3;
				}
				else if(nmsitaux3.length()==2)
				{
					nmsitaux3="0"+nmsitaux3;
				}
				String nmsituac3 = detalle.get("NMSITUAC");
				if(nmsituac3.length()==1)
				{
					nmsituac3="00"+nmsituac3;
				}
				else if(nmsituac3.length()==2)
				{
					nmsituac3="0"+nmsituac3;
				}
				detalle.put("GRUPO",nmsitaux3+"_"+nmsituac3+"_"+header);
			}
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			logger.error(timestamp+" error inesperado al obtener detalle de cotizacion",ex);
			exito           = false;
			respuesta       = "Error al obtener detalle #"+timestamp;
			respuestaOculta = ex.getMessage();
		}
		logger.info(""
				+ "\n###### obtenerDetalleCotizacionGrupo ######"
				+ "\n###########################################"
				);
		return SUCCESS;
	}
	
	public String cargarDatosCotizacionGrupo()
	{
		logger.info(""
				+ "\n########################################"
				+ "\n###### cargarDatosCotizacionGrupo ######"
				+ "\nsmap1 "+smap1
				);
		success = true;
		try
		{
			params=cotizacionManager.cargarDatosCotizacionGrupo(
					smap1.get("cdunieco"), smap1.get("cdramo"),
					smap1.get("cdtipsit"), smap1.get("estado"),
					smap1.get("nmpoliza"), smap1.get("ntramite"));
		    respuesta       = "Todo OK";
		    respuestaOculta = "Todo OK";
		    exito           = true;
		}
		catch(Exception ex)
		{
			long timestamp=System.currentTimeMillis();
			logger.error("Error al cargar datos de cotizacion grupo "+timestamp,ex);
			exito           = false;
			respuesta       = "Error inesperado #"+timestamp;
			respuestaOculta = ex.getMessage();
		}
		logger.info(""
				+ "\n###### cargarDatosCotizacionGrupo ######"
				+ "\n########################################"
				);
		return SUCCESS;
	}
	
	public String cargarDatosCotizacionGrupo2()
	{
		logger.info(""
				+ "\n#########################################"
				+ "\n###### cargarDatosCotizacionGrupo2 ######"
				+ "\n###### smap1 "+smap1
				);
		
		exito   = true;
		success = true;
		
		String cdunieco = null;
		String cdramo   = null;
		String cdtipsit = null;
		String estado   = null;
		String nmpoliza = null;
		String ntramite = null;
		
		//datos completos
		try
		{
			if(smap1==null)
			{
				throw new Exception("No se recibieron datos");
			}
			cdunieco = smap1.get("cdunieco");
			cdramo   = smap1.get("cdramo");
			cdtipsit = smap1.get("cdtipsit");
			estado   = smap1.get("estado");
			nmpoliza = smap1.get("nmpoliza");
			ntramite = smap1.get("ntramite");
			if(StringUtils.isBlank(cdunieco))
			{
				throw new ApplicationException("No se recibio la sucursal");
			}
			if(StringUtils.isBlank(cdramo))
			{
				throw new ApplicationException("No se recibio el producto");
			}
			if(StringUtils.isBlank(cdtipsit))
			{
				throw new ApplicationException("No se recibio la situacion");
			}
			if(StringUtils.isBlank(estado))
			{
				throw new ApplicationException("No se recibio el estado");
			}
			if(StringUtils.isBlank(nmpoliza))
			{
				throw new ApplicationException("No se recibio el numero de cotizacion");
			}
			if(StringUtils.isBlank(ntramite))
			{
				throw new ApplicationException("No se recibio el numero de tramite");
			}
		}
		catch(ApplicationException ax)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString();
			respuestaOculta = ax.getMessage();
			logger.error(respuesta,ax);
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder("Error al validar datos para cargar cotizacion #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		//proceso
		if(exito)
		{
			ManagerRespuestaSmapVO resp=cotizacionManager.cargarDatosCotizacionGrupo2(
					cdunieco
					,cdramo
					,cdtipsit
					,estado
					,nmpoliza
					,ntramite);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			if(exito)
			{
				params = resp.getSmap();
			}
		}
		
		logger.info(""
				+ "\n###### cargarDatosCotizacionGrupo2 ######"
				+ "\n#########################################"
				);
		return SUCCESS;
	}
	
	public String cargarGruposCotizacion2()
	{
		logger.info(
				new StringBuilder()
				.append("\n#####################################")
				.append("\n###### cargarGruposCotizacion2 ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		exito   = true;
		success = true;
		
		String cdunieco = null;
		String cdramo   = null;
		String estado   = null;
		String nmpoliza = null;
		
		//datos completos
		try
		{
			if(smap1==null)
			{
				throw new Exception("No se recibieron datos");
			}
			cdunieco = smap1.get("cdunieco");
			cdramo   = smap1.get("cdramo");
			estado   = smap1.get("estado");
			nmpoliza = smap1.get("nmpoliza");
			if(StringUtils.isBlank(cdunieco))
			{
				throw new ApplicationException("No se recibio la sucursal");
			}
			if(StringUtils.isBlank(cdramo))
			{
				throw new ApplicationException("No se recibio el producto");
			}
			if(StringUtils.isBlank(estado))
			{
				throw new ApplicationException("No se recibio el estado");
			}
			if(StringUtils.isBlank(nmpoliza))
			{
				throw new ApplicationException("No se recibio la poliza");
			}
		}
		catch(ApplicationException ax)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString();
			respuestaOculta = ax.getMessage();
			logger.error(respuesta,ax);
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder("Error al validar datos para cargar grupos #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		//proceso
		if(exito)
		{
			ManagerRespuestaSlistVO resp = cotizacionManager.cargarGruposCotizacion2(cdunieco,cdramo,estado,nmpoliza);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			if(exito)
			{
				slist1 = resp.getSlist();
			}
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### cargarGruposCotizacion2 ######")
				.append("\n#####################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String cargarGruposCotizacion()
	{
		logger.info(""
				+ "\n####################################"
				+ "\n###### cargarGruposCotizacion ######"
				+ "\n smap1: "+smap1
				);
		success = true;
		try
		{
			slist1=cotizacionManager.cargarGruposCotizacion(smap1.get("cdunieco"),smap1.get("cdramo"),smap1.get("estado"),smap1.get("nmpoliza"));
			exito           = true;
			respuesta       = "Todo OK";
			respuestaOculta = "Todo OK";
		}
		catch(Exception ex)
		{
			long timestamp=System.currentTimeMillis();
			logger.error("error al cargar grupos de cotizacion "+timestamp,ex);
			exito           = false;
			respuesta       = "Error inesperado #"+timestamp;
			respuestaOculta = ex.getMessage();
		}
		logger.info(""
				+ "\n###### cargarGruposCotizacion ######"
				+ "\n####################################"
				);
		return SUCCESS;
	}
	
	public String cargarDatosGrupoLinea()
	{
		logger.info(""
				+ "\n###################################"
				+ "\n###### cargarDatosGrupoLinea ######"
				+ "\n smap1: "+smap1
				);
		success = true;
		try
		{
			params=cotizacionManager.cargarDatosGrupoLinea(
					smap1.get("cdunieco")
					,smap1.get("cdramo")
					,smap1.get("estado")
					,smap1.get("nmpoliza")
					,smap1.get("letra")
					);
			exito           = true;
			respuesta       = "Todo OK";
			respuestaOculta = "Todo OK";
		}
		catch(Exception ex)
		{
			long timestamp=System.currentTimeMillis();
			logger.error("error al obtener datos de grupo de linea "+timestamp,ex);
			exito           = false;
			respuesta       = "Error inesperado #"+timestamp;
			respuestaOculta = ex.getMessage();
		}
		logger.info(""
				+ "\n###### cargarDatosGrupoLinea ######"
				+ "\n###################################"
				);
		return SUCCESS;
	}
	
	public String cargarTvalogarsGrupo()
	{
		logger.info(""
				+ "\n##################################"
				+ "\n###### cargarTvalogarsGrupo ######"
				+ "\nsmap1: "+smap1
				);
		success = true;
		try
		{
			slist1 = cotizacionManager.cargarTvalogarsGrupo(
					smap1.get("cdunieco")
					,smap1.get("cdramo")
					,smap1.get("estado")
					,smap1.get("nmpoliza")
					,smap1.get("letra"));
			exito           = true;
			respuesta       = "Todo OK";
			respuestaOculta = "Todo OK";
		}
		catch(Exception ex)
		{
			long timestamp=System.currentTimeMillis();
			logger.error("error al obtener tvalogars grupo #"+timestamp,ex);
			exito           = false;
			respuesta       = "Error inesperado #"+timestamp;
			respuestaOculta = ex.getMessage();
		}
		logger.info(""
				+ "\n###### cargarTvalogarsGrupo ######"
				+ "\n##################################"
				);
		return SUCCESS;
	}
	
	public String cargarTarifasPorEdad()
	{
		logger.info(""
				+ "\n##################################"
				+ "\n###### cargarTarifasPorEdad ######"
				+ "\nsmap1: "+smap1
				);
		success = true;
		try
		{
			slist1=cotizacionManager.cargarTarifasPorEdad(
					smap1.get("cdunieco")
					,smap1.get("cdramo")
					,smap1.get("estado")
					,smap1.get("nmpoliza")
					,smap1.get("nmsuplem")
					,smap1.get("cdplan")
					,smap1.get("cdgrupo")
					,smap1.get("cdperpag"));
			exito           = true;
			respuesta       = "Todo OK";
			respuestaOculta = "Todo OK";
		}
		catch(Exception ex)
		{
			long timestamp=System.currentTimeMillis();
			logger.error("Error al obtener tarifas por edad #"+timestamp,ex);
			exito           = false;
			respuesta       = "Error inesperado #"+timestamp;
			respuestaOculta = ex.getMessage();
		}
		logger.info(""
				+ "\n###### cargarTarifasPorEdad ######"
				+ "\n##################################"
				);
		return SUCCESS;
	}
	
	public String cargarTarifasPorCobertura()
	{
		logger.info(""
				+ "\n#######################################"
				+ "\n###### cargarTarifasPorCobertura ######"
				+ "\nsmap1: "+smap1
				);
		success = true;
		try
		{
			slist1=cotizacionManager.cargarTarifasPorCobertura(
					smap1.get("cdunieco")
					,smap1.get("cdramo")
					,smap1.get("estado")
					,smap1.get("nmpoliza")
					,smap1.get("nmsuplem")
					,smap1.get("cdplan")
					,smap1.get("cdgrupo")
					,smap1.get("cdperpag"));
			exito           = true;
			respuesta       = "Todo OK";
			respuestaOculta = "Todo OK";
		}
		catch(Exception ex)
		{
			long timestamp=System.currentTimeMillis();
			logger.error("Error al obtener tarifas por cobertura #"+timestamp,ex);
			exito           = false;
			respuesta       = "Error inesperado #"+timestamp;
			respuestaOculta = ex.getMessage();
		}
		logger.info(""
				+ "\n###### cargarTarifasPorCobertura ######"
				+ "\n#######################################"
				);
		return SUCCESS;
	}
	
	public String cargarAseguradosExtraprimas()
	{
		logger.debug(""
				+ "\n#########################################"
				+ "\n###### cargarAseguradosExtraprimas ######"
				+ "\nsmap1: "+smap1
				);
		success = true;
		exito   = true;
		
		if(exito)
		{
			try
			{
			    slist1=cotizacionManager.cargarAseguradosExtraprimas(
			    		smap1.get("cdunieco")
			    		,smap1.get("cdramo")
			    		,smap1.get("estado")
			    		,smap1.get("nmpoliza")
			    		,smap1.get("nmsuplem")
			    		,smap1.get("cdgrupo")
			    		);
			    for(Map<String,String>iAsegurado:slist1)
			    {
			    	String tpl = null;
			    	if(StringUtils.isBlank(iAsegurado.get("TITULAR")))
			    	{
			    		tpl = "Asegurados";
			    	}
			    	else
			    	{
			    		tpl = new StringBuilder()
	    	                    .append("Familia (")
	    	                    .append(iAsegurado.get("FAMILIA"))
	    	                    .append(") de ")
	    	                    .append(iAsegurado.get("TITULAR"))
	    	            		.toString();
			    	}
			    	iAsegurado.put("AGRUPADOR",
			    			new StringBuilder()
			    	            .append(StringUtils.leftPad(iAsegurado.get("FAMILIA"),3,"0"))
			    	            .append("_")
			    	            .append(tpl)
			    	            .toString());
			    }
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Error al cargar extraprimas #"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		logger.debug(""
				+ "\n###### cargarAseguradosExtraprimas ######"
				+ "\n#########################################"
				);
		return SUCCESS;
	}
	
	public String cargarAseguradosExtraprimas2()
	{
		logger.debug(
				new StringBuilder()
				.append("\n##########################################")
				.append("\n###### cargarAseguradosExtraprimas2 ######")
				.append("\n######smap1=").append(smap1)
				.toString()
				);
		
		success = true;
		exito   = true;
		
		String cdunieco = null;
		String cdramo   = null;
		String estado   = null;
		String nmpoliza = null;
		String nmsuplem = null;
		String cdgrupo  = null;
		
		//datos completos
		try
		{
			if(smap1==null)
			{
				throw new ApplicationException("No se recibieron datos");
			}
			cdunieco = smap1.get("cdunieco");
			cdramo   = smap1.get("cdramo");
			estado   = smap1.get("estado");
			nmpoliza = smap1.get("nmpoliza");
			nmsuplem = smap1.get("nmsuplem");
			cdgrupo  = smap1.get("cdgrupo");
			
			if(StringUtils.isBlank(cdunieco))
			{
				throw new ApplicationException("No se recibio la sucursal");
			}
			if(StringUtils.isBlank(cdramo))
			{
				throw new ApplicationException("No se recibio el producto");
			}
			if(StringUtils.isBlank(estado))
			{
				throw new ApplicationException("No se recibio el estado");
			}
			if(StringUtils.isBlank(nmpoliza))
			{
				throw new ApplicationException("No se recibio el numero de cotizacion");
			}
			if(StringUtils.isBlank(nmpoliza))
			{
				throw new ApplicationException("No se recibio el numero de cotizacion");
			}
			if(StringUtils.isBlank(nmpoliza))
			{
				throw new ApplicationException("No se recibio el numero de cotizacion");
			}
			if(StringUtils.isBlank(nmsuplem))
			{
				throw new ApplicationException("No se recibio el suplemento");
			}
			if(StringUtils.isBlank(cdgrupo))
			{
				throw new ApplicationException("No se recibio la clave de grupo");
			}
		}
		catch(ApplicationException ax)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString();
			respuestaOculta = ax.getMessage();
			logger.error(respuesta,ax);
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder("Error al validar datos para cargar extraprimas #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		//proceso
		if(exito)
		{
		    ManagerRespuestaSlistVO resp = cotizacionManager.cargarAseguradosExtraprimas2(
		    		cdunieco
		    		,cdramo
		    		,estado
		    		,nmpoliza
		    		,nmsuplem
		    		,cdgrupo
		    		);
		    exito           = resp.isExito();
		    respuesta       = resp.getRespuesta();
		    respuestaOculta = resp.getRespuestaOculta();
		    if(exito)
		    {
		    	slist1 = resp.getSlist();
		    }
		}
		
		success = exito;
		
		logger.debug(
				new StringBuilder()
				.append("\n###### cargarAseguradosExtraprimas2 ######")
				.append("\n##########################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String guardarExtraprimasAsegurados()
	{
		logger.info(""
				+ "\n##########################################"
				+ "\n###### guardarExtraprimasAsegurados ######"
				+ "\nslist1: "+slist1
				);
		success = true;
		exito   = true;
		if(exito)
		{
			try
			{
				for(Map<String,String>iAsegurado:slist1)
				{
					cotizacionManager.guardarExtraprimaAsegurado(
							iAsegurado.get("cdunieco")
							,iAsegurado.get("cdramo")
							,iAsegurado.get("estado")
							,iAsegurado.get("nmpoliza")
							,iAsegurado.get("nmsuplem")
							,iAsegurado.get("nmsituac")
							,iAsegurado.get("ocupacion")
							,iAsegurado.get("extpri_ocupacion")
							,iAsegurado.get("peso")
							,iAsegurado.get("estatura")
							,iAsegurado.get("extpri_estatura")
							);
				}
				respuesta       = "Se guardaron todos los datos";
				respuestaOculta = "Todo OK";
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Error al guardar extraprimas #"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		logger.info(""
				+ "\n###### guardarExtraprimasAsegurados ######"
				+ "\n##########################################"
				);
		return SUCCESS;
	}
	
	public String ejecutaSigsvalipol()
	{
		logger.info(""
				+ "\n################################"
				+ "\n###### ejecutaSigsvalipol ######"
				+ "\nsmap1: "+smap1
				);
		exito   = true;
		success = true;
		if(exito)
		{
			try
			{
				UserVO usuario  = (UserVO)session.get("USUARIO");
				String cdusuari = usuario.getUser();
				String cdelemen = usuario.getEmpresa().getElementoId();
				Map<String,String> mapaTarificacion=new HashMap<String,String>(0);
	            mapaTarificacion.put("pv_cdusuari_i" , cdusuari);
	            mapaTarificacion.put("pv_cdelemen_i" , cdelemen);
	            mapaTarificacion.put("pv_cdunieco_i" , smap1.get("cdunieco"));
	            mapaTarificacion.put("pv_cdramo_i"   , smap1.get("cdramo"));
	            mapaTarificacion.put("pv_estado_i"   , smap1.get("estado"));
	            mapaTarificacion.put("pv_nmpoliza_i" , smap1.get("nmpoliza"));
	            mapaTarificacion.put("pv_nmsituac_i" , smap1.get("nmsituac"));
	            mapaTarificacion.put("pv_nmsuplem_i" , smap1.get("nmsuplem"));
	            mapaTarificacion.put("pv_cdtipsit_i" , smap1.get("cdtipsit"));
	            kernelManager.ejecutaASIGSVALIPOL_EMI(mapaTarificacion);
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Error al tarificar #"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		logger.info(""
				+ "\n###### ejecutaSigsvalipol ######"
				+ "\n################################"
				);
		return SUCCESS;
	}
	
	public String cargarAseguradosGrupo()
	{
		logger.info(""
				+ "\n###################################"
				+ "\n###### cargarAseguradosGrupo ######"
				+ "\nsmap1 "+smap1
				);
		success = true;
		exito   = true;
		if(exito)
		{
			try
			{
				slist1=cotizacionManager.cargarAseguradosGrupo(
						smap1.get("cdunieco")
						,smap1.get("cdramo")
						,smap1.get("estado")
						,smap1.get("nmpoliza")
						,smap1.get("nmsuplem")
						,smap1.get("cdgrupo")
						);
				for(Map<String,String>iAsegurado:slist1)
			    {
			    	String tpl = null;
			    	if(StringUtils.isBlank(iAsegurado.get("TITULAR")))
			    	{
			    		tpl = "Asegurados";
			    	}
			    	else
			    	{
			    		tpl = new StringBuilder()
			    		        .append("Familia (")
			    		        .append(iAsegurado.get("FAMILIA"))
			    		        .append(") de ")
    			    		    .append(iAsegurado.get("TITULAR"))
			    		        .toString();
			    	}
			    	iAsegurado.put("AGRUPADOR",
			    			new StringBuilder()
			    	            .append(StringUtils.leftPad(iAsegurado.get("FAMILIA"),3,"0"))
			    	            .append("_")
			    	            .append(tpl)
			    	            .toString());
			    }
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Error al cargar los asegurados del grupo #"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		logger.info(""
				+ "\n###### cargarAseguradosGrupo ######"
				+ "\n###################################"
				);
		return SUCCESS;
	}
	
	public String guardarAseguradosCotizacion()
	{
		this.session=ActionContext.getContext().getSession();
		logger.info(""
				+ "\n#########################################"
				+ "\n###### guardarAseguradosCotizacion ######"
				+ "\nsmap1 "+smap1
				+ "\nslist1 "+slist1
				);
		success = true;
		exito   = true;
		
		if(exito)
		{
			try
			{
				String cdunieco = smap1.get("cdunieco");
				String cdramo   = smap1.get("cdramo");
				String estado   = smap1.get("estado");
				String nmpoliza = smap1.get("nmpoliza");
				String cdgrupo  = smap1.get("cdgrupo");
				
				cotizacionManager.borrarMpoliperGrupo(cdunieco,cdramo,estado,nmpoliza,cdgrupo);
				
				for(Map<String,String>aseg:slist1)
				{
					Map<String,Object> parametros=new LinkedHashMap<String,Object>(0);
					parametros=new LinkedHashMap<String,Object>(0);
					parametros.put("pv_cdunieco_i",	cdunieco);
					parametros.put("pv_cdramo_i",	cdramo);
					parametros.put("pv_estado_i",	estado);
					parametros.put("pv_nmpoliza_i",	nmpoliza);
					parametros.put("pv_nmsituac_i",	(String)aseg.get("nmsituac"));
					parametros.put("pv_cdrol_i", 	(String)aseg.get("cdrol"));
					parametros.put("pv_cdperson_i",	(String)aseg.get("cdperson"));
					parametros.put("pv_nmsuplem_i",	"0");
					parametros.put("pv_status_i",	"V");
					parametros.put("pv_nmorddom_i",	"1");
					parametros.put("pv_swreclam_i",	null);
					parametros.put("pv_accion_i",	"I");
					parametros.put("pv_swexiper_i", (String)aseg.get("swexiper"));
					kernelManager.movMpoliper(parametros);
				}
				
				respuesta       = "Se han guardado los asegurados";
				respuestaOculta = "Todo OK";
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Error al guardar las personas #"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		logger.info(""
				+ "\n###### guardarAseguradosCotizacion ######"
				+ "\n#########################################"
				);
		return SUCCESS;
	}
	
	public String guardarReporteCotizacionGrupo()
	{
		long stamp = System.currentTimeMillis();
		logger.debug(Utils.log(stamp
				,"\n###########################################"
				,"\n###### guardarReporteCotizacionGrupo ######"
				,"\n###### smap1=", smap1
				));
		success = true;
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			String cdusuari = usuario.getUser();
			
			String cdunieco  = smap1.get("cdunieco")
			       ,cdramo   = smap1.get("cdramo")
			       ,estado   = smap1.get("estado")
			       ,nmpoliza = smap1.get("nmpoliza")
			       ,cdperpag = smap1.get("cdperpag")
			       ,cdtipsit = smap1.get("cdtipsit")
			       ,ntramite = smap1.get("ntramite")
			       ,nGrupos  = smap1.get("nGrupos");
			
			int bloqueos = 0;
			do
			{
				logger.debug(Utils.log("Recuperando conteo de tbloqueo de primer ciclo"));
				ManagerRespuestaSmapVO resp = recuperacionSimpleManager.recuperacionSimple(
						RecuperacionSimple.RECUPERAR_CONTEO_BLOQUEO
						,smap1
						,usuario.getRolActivo().getClave()
						,cdusuari
						);
				
				bloqueos = Integer.parseInt(resp.getSmap().get("CONTEO"));
				
				logger.debug(Utils.log(stamp,"Conteo recuperado=",bloqueos));
				
				logger.debug(Utils.log(stamp,"Esperando 30 segundos del primer ciclo..."));
				Thread.sleep(1000l*30l);
				
			}
			while(bloqueos>0);
			
			do
			{
				logger.debug(Utils.log("Recuperando conteo de tbloqueo de segundo ciclo"));
				ManagerRespuestaSmapVO resp = recuperacionSimpleManager.recuperacionSimple(
						RecuperacionSimple.RECUPERAR_CONTEO_BLOQUEO
						,smap1
						,usuario.getRolActivo().getClave()
						,cdusuari
						);
				
				bloqueos = Integer.parseInt(resp.getSmap().get("CONTEO"));
				
				logger.debug(Utils.log(stamp,"Conteo recuperado=",bloqueos));
				
				logger.debug(Utils.log(stamp,"Esperando 30 segundos de segundo ciclo..."));
				Thread.sleep(1000l*30l);
				
			}
			while(bloqueos>0);
			
			logger.debug(Utils.log(stamp,"Se termino el bloqueo"));
			
			String urlReporteCotizacion=Utils.join(
					  getText("ruta.servidor.reports")
					, "?p_unieco="      , cdunieco
					, "&p_ramo="        , cdramo
					, "&p_estado="      , estado
					, "&p_poliza="      , nmpoliza
					, "&p_cdperpag="    , cdperpag
					, "&p_perpag="      , cdperpag
					, "&p_suplem=0"
					, "&p_cdplan="
                    , "&destype=cache"
                    , "&desformat=PDF"
                    , "&userid="        , getText("pass.servidor.reports")
                    , "&ACCESSIBLE=YES"
                    , "&report="        , getText("rdf.cotizacion.nombre."+cdtipsit)
                    , "&paramform=no"
                    );
			String nombreArchivoCotizacion="cotizacion.pdf";
			String pathArchivoCotizacion=""
					+ getText("ruta.documentos.poliza")
					+ "/"+ntramite
					+ "/"+nombreArchivoCotizacion
					;
			
			logger.debug(Utils.log(stamp,"Se va a ejecutar el reporte=",urlReporteCotizacion));
			
			HttpUtil.generaArchivo(urlReporteCotizacion, pathArchivoCotizacion);

			//Map<String,Object>mapArchivo=new LinkedHashMap<String,Object>(0);
			//mapArchivo.put("pv_cdunieco_i"  , cdunieco);
			//mapArchivo.put("pv_cdramo_i"    , cdramo);
			//mapArchivo.put("pv_estado_i"    , estado);
			//mapArchivo.put("pv_nmpoliza_i"  , "0");
			//mapArchivo.put("pv_nmsuplem_i"  , "0");
			//mapArchivo.put("pv_feinici_i"   , new Date());
			//mapArchivo.put("pv_cddocume_i"  , nombreArchivoCotizacion);
			//mapArchivo.put("pv_dsdocume_i"  , "COTIZACI&Oacute;N EN RESUMEN");
			//mapArchivo.put("pv_ntramite_i"  , ntramite);
			//mapArchivo.put("pv_nmsolici_i"  , nmpoliza);
			//mapArchivo.put("pv_tipmov_i"    , "1");
			//mapArchivo.put("pv_swvisible_i" , null);
			//kernelManager.guardarArchivo(mapArchivo);
			
			documentosManager.guardarDocumento(
					cdunieco
					,cdramo
					,estado
					,"0"
					,"0"
					,new Date()
					,nombreArchivoCotizacion
					,"COTIZACI\u00d3N EN RESUMEN"
					,nmpoliza
					,ntramite
					,"1"
					,null
					,null
					,TipoTramite.POLIZA_NUEVA.getCdtiptra()
					,null
					,null
					);
			
			String urlReporteCotizacion2=Utils.join(
					  getText("ruta.servidor.reports")
					, "?p_unieco="      , cdunieco
					, "&p_ramo="        , cdramo
					, "&p_estado="      , estado
					, "&p_poliza="      , nmpoliza
					, "&p_cdperpag="    , cdperpag
					, "&p_suplem=0"
					, "&p_cdplan="
                  , "&destype=cache"
                  , "&desformat=PDF"
                  , "&userid="        , getText("pass.servidor.reports")
                  , "&ACCESSIBLE=YES"
                  , "&report="        , getText("rdf.cotizacion2.nombre."+cdtipsit)
                  , "&paramform=no"
                  );
			String nombreArchivoCotizacion2="cotizacion2.pdf";
			String pathArchivoCotizacion2=""
					+ getText("ruta.documentos.poliza")
					+ "/"+ntramite
					+ "/"+nombreArchivoCotizacion2
					;
			
			logger.debug(Utils.log(stamp,"Se va a ejecutar el reporte2=",urlReporteCotizacion2));
			
			HttpUtil.generaArchivo(urlReporteCotizacion2, pathArchivoCotizacion2);

			//Map<String,Object>mapArchivo2=new LinkedHashMap<String,Object>(0);
			//mapArchivo2.put("pv_cdunieco_i"  , cdunieco);
			//mapArchivo2.put("pv_cdramo_i"    , cdramo);
			//mapArchivo2.put("pv_estado_i"    , estado);
			//mapArchivo2.put("pv_nmpoliza_i"  , "0");
			//mapArchivo2.put("pv_nmsuplem_i"  , "0");
			//mapArchivo2.put("pv_feinici_i"   , new Date());
			//mapArchivo2.put("pv_cddocume_i"  , nombreArchivoCotizacion2);
			//mapArchivo2.put("pv_dsdocume_i"  , "COTIZACI&Oacute;N A DETALLE");
			//mapArchivo2.put("pv_ntramite_i"  , ntramite);
			//mapArchivo2.put("pv_nmsolici_i"  , nmpoliza);
			//mapArchivo2.put("pv_tipmov_i"    , "1");
			//mapArchivo2.put("pv_swvisible_i" , null);
			//kernelManager.guardarArchivo(mapArchivo2);
			
			documentosManager.guardarDocumento(
					cdunieco
					,cdramo
					,estado
					,"0"
					,"0"
					,new Date()
					,nombreArchivoCotizacion2
					,"COTIZACI\u00d3N A DETALLE"
					,nmpoliza
					,ntramite
					,"1"
					,null
					,null
					,TipoTramite.POLIZA_NUEVA.getCdtiptra()
					,null
					,null
					);
			
			if(Ramo.MULTISALUD.getCdramo().equals(cdramo))
			{
				//excel resumen
				Map<String,String> paramsResumen = new LinkedHashMap<String,String>();
				paramsResumen.put("pv_cdunieco_i" , cdunieco);
				paramsResumen.put("pv_cdramo_i"   , cdramo);
				paramsResumen.put("pv_estado_i"   , estado);
				paramsResumen.put("pv_nmpoliza_i" , nmpoliza);
				paramsResumen.put("pv_nmsuplem_i" , "0");
				paramsResumen.put("pv_cdperpag_i" , cdperpag);
				paramsResumen.put("pv_cdusuari_i" , cdusuari);
				
				InputStream excel = reportesManager.obtenerDatosReporte(Reporte.SALUD_COLECTIVO_RESUMEN_COTIZACION.getCdreporte()
						,cdusuari
						,paramsResumen
						);
				
				String nombreResumen = Utils.join("RESUMEN_COTIZACION",TipoArchivo.XLS.getExtension());
				
				FileUtils.copyInputStreamToFile(excel, new File(Utils.join(
								getText("ruta.documentos.poliza"),"/",ntramite,"/",nombreResumen
				)));
				
				//Map<String,Object>mapaResumen = new LinkedHashMap<String,Object>(0);
				//mapaResumen.put("pv_cdunieco_i"  , cdunieco);
				//mapaResumen.put("pv_cdramo_i"    , cdramo);
				//mapaResumen.put("pv_estado_i"    , estado);
				//mapaResumen.put("pv_nmpoliza_i"  , "0");
				//mapaResumen.put("pv_nmsuplem_i"  , "0");
				//mapaResumen.put("pv_feinici_i"   , new Date());
				//mapaResumen.put("pv_cddocume_i"  , nombreResumen);
				//mapaResumen.put("pv_dsdocume_i"  , "RESUMEN DE COTIZACI&Oacute;N (XLS)");
				//mapaResumen.put("pv_ntramite_i"  , ntramite);
				//mapaResumen.put("pv_nmsolici_i"  , nmpoliza);
				//mapaResumen.put("pv_tipmov_i"    , "1");
				//mapaResumen.put("pv_swvisible_i" , null);
				//kernelManager.guardarArchivo(mapaResumen);
				
				documentosManager.guardarDocumento(
						cdunieco
						,cdramo
						,estado
						,"0"
						,"0"
						,new Date()
						,nombreResumen
						,"RESUMEN DE COTIZACI\u00d3N (EXCEL)"
						,nmpoliza
						,ntramite
						,"1"
						,null
						,null
						,TipoTramite.POLIZA_NUEVA.getCdtiptra()
						,null
						,null
						);
				
				//pdf resumen
				String urlReporteResumenCotizacion=Utils.join(
						  getText("ruta.servidor.reports")
						, "?p_unieco="      , cdunieco
						, "&p_ramo="        , cdramo
						, "&p_estado="      , estado
						, "&p_poliza="      , nmpoliza
						, "&p_perpag="      , cdperpag
						, "&p_suplem=0"
	                    , "&destype=cache"
	                    , "&desformat=PDF"
	                    , "&userid="        , getText("pass.servidor.reports")
	                    , "&ACCESSIBLE=YES"
	                    , "&report="        , getText("rdf.resumen.cotizacion.col."+cdramo)
	                    , "&paramform=no"
	                    );
				String nombreArchivoResumenCotizacion = "resumen_cotizacion_col.pdf";
				String pathArchivoResumenCotizacion=""
						+ getText("ruta.documentos.poliza")
						+ "/"+ntramite
						+ "/"+nombreArchivoResumenCotizacion
						;
				HttpUtil.generaArchivo(urlReporteResumenCotizacion, pathArchivoResumenCotizacion);

				//Map<String,Object>mapArchivoResumen=new LinkedHashMap<String,Object>(0);
				//mapArchivoResumen.put("pv_cdunieco_i"  , cdunieco);
				//mapArchivoResumen.put("pv_cdramo_i"    , cdramo);
				//mapArchivoResumen.put("pv_estado_i"    , estado);
				//mapArchivoResumen.put("pv_nmpoliza_i"  , "0");
				//mapArchivoResumen.put("pv_nmsuplem_i"  , "0");
				//mapArchivoResumen.put("pv_feinici_i"   , new Date());
				//mapArchivoResumen.put("pv_cddocume_i"  , nombreArchivoResumenCotizacion);
				//mapArchivoResumen.put("pv_dsdocume_i"  , "RESUMEN DE COTIZACI&Oacute;N (PDF)");
				//mapArchivoResumen.put("pv_ntramite_i"  , ntramite);
				//mapArchivoResumen.put("pv_nmsolici_i"  , nmpoliza);
				//mapArchivoResumen.put("pv_tipmov_i"    , "1");
				//mapArchivoResumen.put("pv_swvisible_i" , null);
				//kernelManager.guardarArchivo(mapArchivoResumen);
				
				documentosManager.guardarDocumento(
						cdunieco
						,cdramo
						,estado
						,"0"
						,"0"
						,new Date()
						,nombreArchivoResumenCotizacion
						,"RESUMEN DE COTIZACI\u00d3N (PDF)"
						,nmpoliza
						,ntramite
						,"1"
						,null
						,null
						,TipoTramite.POLIZA_NUEVA.getCdtiptra()
						,null
						,null
						);
				
				//exceles grupos
				for(int i=1 ; i<=Integer.parseInt(nGrupos) ; i++)
				{
					Map<String,String> paramsGrupo = new LinkedHashMap<String,String>();
					paramsGrupo.put("pv_cdunieco_i" , cdunieco);
					paramsGrupo.put("pv_cdramo_i"   , cdramo);
					paramsGrupo.put("pv_estado_i"   , estado);
					paramsGrupo.put("pv_nmpoliza_i" , nmpoliza);
					paramsGrupo.put("pv_nmsuplem_i" , "0");
					paramsGrupo.put("pv_cdperpag_i" , cdperpag);
					paramsGrupo.put("pv_cdgrupo_i"  , i+"");
					paramsGrupo.put("pv_cdusuari_i" , cdusuari);
					
					InputStream excelGrupo = reportesManager.obtenerDatosReporte(Reporte.SALUD_COLECTIVO_COTIZACION_GRUPO.getCdreporte()
							,cdusuari
							,paramsGrupo
							);
					
					String nombreCotGrupo = Utils.join("COTIZACION_GRUPO_",i,TipoArchivo.XLS.getExtension());
					
					FileUtils.copyInputStreamToFile(excelGrupo, new File(Utils.join(
									getText("ruta.documentos.poliza"),"/",ntramite,"/",nombreCotGrupo
					)));
					
					//Map<String,Object>mapaGrupo = new LinkedHashMap<String,Object>(0);
					//mapaGrupo.put("pv_cdunieco_i"  , cdunieco);
					//mapaGrupo.put("pv_cdramo_i"    , cdramo);
					//mapaGrupo.put("pv_estado_i"    , estado);
					//mapaGrupo.put("pv_nmpoliza_i"  , "0");
					//mapaGrupo.put("pv_nmsuplem_i"  , "0");
					//mapaGrupo.put("pv_feinici_i"   , new Date());
					//mapaGrupo.put("pv_cddocume_i"  , nombreCotGrupo);
					//mapaGrupo.put("pv_dsdocume_i"  , Utils.join("COTIZACI&Oacute;N GRUPO ",i));
					//mapaGrupo.put("pv_ntramite_i"  , ntramite);
					//mapaGrupo.put("pv_nmsolici_i"  , nmpoliza);
					//mapaGrupo.put("pv_tipmov_i"    , "1");
					//mapaGrupo.put("pv_swvisible_i" , null);
					//kernelManager.guardarArchivo(mapaGrupo);
					
					documentosManager.guardarDocumento(
							cdunieco
							,cdramo
							,estado
							,"0"
							,"0"
							,new Date()
							,nombreCotGrupo
							,Utils.join("COTIZACI\u00d3N GRUPO ",i)
							,nmpoliza
							,ntramite
							,"1"
							,null
							,null
							,TipoTramite.POLIZA_NUEVA.getCdtiptra()
							,null
							,null
							);
				}
			}
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = "Error al guardar reporte #"+timestamp;
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		logger.debug(Utils.log(stamp
				,"\n###### guardarReporteCotizacionGrupo ######"
				,"\n###########################################"
				));
		
		return SUCCESS;
	}
	
	public String cargarCduniecoAgenteAuto()
	{
		logger.info(
				new StringBuilder()
				.append("\n######################################")
				.append("\n###### cargarCduniecoAgenteAuto ######")
				.append("\nsmap1 ")
				.append(smap1)
				.toString()
				);
		success = true;
		exito   = true;
		try
		{
			smap1.put("cdunieco",cotizacionManager.cargarCduniecoAgenteAuto(smap1.get("cdagente")));
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = "Error al obtener sucursal del agente #"+timestamp;
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		logger.info(
				new StringBuilder()
				.append("\nsmap1 ")
				.append(smap1)
				.append("\n###### cargarCduniecoAgenteAuto ######")
				.append("\n######################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String cargarCdagentePorFolio()
	{
		logger.info(new StringBuilder()
		      .append("\n####################################")
		      .append("\n###### cargarCdagentePorFolio ######")
		      .append("\nsmap1").append(smap1)
		      .toString()
		      );
		
		success = true;
		exito   = true;
		
		int folio    = -1;
		int cdunieco = -1;
		
		//checar datos
		if(exito)
		{
			try
			{
				folio    = Integer.valueOf(smap1.get("folio"));
				cdunieco = Integer.valueOf(smap1.get("cdunieco"));
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = new StringBuilder().append("Datos incompletos #").append(timestamp).toString();
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		//recuperar cdagente
		if(exito)
		{
			try
			{
				EmAdmfolId agente = agentePorFolioService.obtieneAgentePorFolioSucursal(folio,cdunieco);
				if(agente==null)
				{
					throw new Exception("No existe el agente");
				}
				smap1.put("cdagente",String.valueOf(agente.getNumAge()));
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = new StringBuilder().append("Este folio no pertenece a un agente #").append(timestamp).toString();
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		logger.info(new StringBuilder()
		      .append("\nsmap1").append(smap1)
		      .append("\n###### cargarCdagentePorFolio ######")
		      .append("\n####################################")
		      .toString()
		      );
		return SUCCESS;
	}
	
	public String obtenerParametrosCotizacion()
	{
		logger.info(
				new StringBuilder()
				.append("\n#########################################")
				.append("\n###### obtenerParametrosCotizacion ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		success = true;
		exito   = true;
		
		ParametroCotizacion parametro  = null;
		String              sParametro = null;
		String              cdramo     = null;
		String              cdtipsit   = null;
		String              clave4     = null;
		String              clave5     = null;
		
		//datos completos
		if(exito)
		{
			try
			{
				sParametro = smap1.get("parametro");
				cdramo     = smap1.get("cdramo");
				cdtipsit   = smap1.get("cdtipsit");
				clave4     = smap1.get("clave4");
				clave5     = smap1.get("clave5");
				if(StringUtils.isBlank(sParametro))
				{
					throw new Exception("No se especifica el parametro");
				}
				if(StringUtils.isBlank(cdramo))
				{
					throw new Exception("No se especifica el ramo");
				}
				if(StringUtils.isBlank(cdtipsit))
				{
					throw new Exception("No se especifica la situacion");
				}
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Datos incompletos para obtener par&aacute;metros #"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		//parametro existente
		if(exito)
		{
			try
			{
				parametro = ParametroCotizacion.valueOf(sParametro);
				if(parametro==null)
				{
					throw new Exception("El parametro no se encuentra en el enum");
				}
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = new StringBuilder("Par&aacute;metro no definido #").append(timestamp).toString();
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		//obtener parametro
		if(exito)
		{
			try
			{
				ManagerRespuestaSmapVO resp=cotizacionManager.obtenerParametrosCotizacion(parametro, cdramo, cdtipsit, clave4, clave5);
				exito           = resp.isExito();
				respuesta       = resp.getRespuesta();
				respuestaOculta = resp.getRespuestaOculta();
				if(exito)
				{
				    smap1.putAll(resp.getSmap());
				}
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Error inesperado al obtener par&aacute;metros #"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### smap1=").append(smap1)
				.append("\n###### obtenerParametrosCotizacion ######")
				.append("\n#########################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String cargarAutoPorClaveGS()
	{
		logger.info(
				new StringBuilder()
				.append("\n##################################")
				.append("\n###### cargarAutoPorClaveGS ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		success = true;
		exito   = true;
		
		String cdramo   = null;
		String clavegs  = null;
		String cdtipsit = null;
		String cdsisrol = null;
		String tipoUnidad = null;
		
		//datos
		try
		{
			if(session==null)
			{
				throw new ApplicationException("No hay sesion");
			}
			if(session.get("USUARIO")==null)
			{
				throw new ApplicationException("No hay usuario en la sesion");
			}
			UserVO usuario=(UserVO)session.get("USUARIO");
			cdsisrol=usuario.getRolActivo().getClave();
			if(StringUtils.isBlank(cdsisrol))
			{
				throw new ApplicationException("El usuario no tiene rol");
			}
			
			cdramo   = smap1.get("cdramo");
			clavegs  = smap1.get("clavegs");
			cdtipsit = smap1.get("cdtipsit");
			tipoUnidad = smap1.get("tipounidad");
			if(StringUtils.isBlank(clavegs))
			{
				throw new ApplicationException("No se recibio el ramo");
			}
			if(StringUtils.isBlank(clavegs))
			{
				throw new ApplicationException("No se recibio la clave gs");
			}
			if(StringUtils.isBlank(cdtipsit))
			{
				throw new ApplicationException("No se recibio la situacion");
			}
		}
		catch(ApplicationException ax)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString();
			respuestaOculta = ax.getMessage();
			logger.error(respuesta,ax);
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = "Error al validar datos #"+timestamp;
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		//obtener datos auto
		if(exito)
		{
			try
			{
				ManagerRespuestaSmapVO resp = cotizacionManager.cargarAutoPorClaveGS(cdramo,clavegs,cdtipsit,cdsisrol,tipoUnidad);
				
				exito           = resp.isExito();
				respuesta       = resp.getRespuesta();
				respuestaOculta = resp.getRespuestaOculta();
				
				if(exito)
				{
					smap1.putAll(resp.getSmap());
				}
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Error inesperado al obtener datos del auto #"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### smap1=").append(smap1)
				.append("\n###### cargarAutoPorClaveGS ######")
				.append("\n##################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String cargarClaveGSPorAuto()
	{
		logger.info(
				new StringBuilder()
				.append("\n##################################")
				.append("\n###### cargarClaveGSPorAuto ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		success = true;
		exito   = true;
		
		String cdramo = null;
		String modelo = null;
		
		//datos
		try
		{
			cdramo = smap1.get("cdramo");
			modelo = smap1.get("modelo");
			if(StringUtils.isBlank(cdramo))
			{
				throw new Exception("No se recibio el ramo");
			}
			if(StringUtils.isBlank(modelo))
			{
				throw new Exception("No se recibio el modelo");
			}
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = "Datos incompletos #"+timestamp;
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		//obtener datos auto
		if(exito)
		{
			try
			{
				ManagerRespuestaSmapVO resp = cotizacionManager.cargarClaveGSPorAuto(cdramo,modelo);
				
				exito           = resp.isExito();
				respuesta       = resp.getRespuesta();
				respuestaOculta = resp.getRespuestaOculta();
				
				if(exito)
				{
					smap1.putAll(resp.getSmap());
				}
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Error inesperado al obtener clave GS del auto #"+timestamp;
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### smap1=").append(smap1)
				.append("\n###### cargarClaveGSPorAuto ######")
				.append("\n##################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String cargarSumaAseguradaAuto()
	{
		logger.info(
				new StringBuilder()
				.append("\n#####################################")
				.append("\n###### cargarSumaAseguradaAuto ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		success = true;
		exito   = true;
		
		String cdsisrol = null;
		String modelo   = null;
		String version  = null;
		String cdramo   = null;
		String cdtipsit = null;
		
		//datos
		try
		{
			cdsisrol = smap1.get("cdsisrol");
			modelo   = smap1.get("modelo");
			version  = smap1.get("version");
			cdramo   = smap1.get("cdramo");
			cdtipsit = smap1.get("cdtipsit");
			if(StringUtils.isBlank(cdsisrol))
			{
				throw new Exception("No se recibio el rol");
			}
			if(StringUtils.isBlank(modelo)||modelo.length()!=4)
			{
				throw new Exception("No se recibio el modelo");
			}
			if(StringUtils.isBlank(version))
			{
				throw new Exception("No se recibio la version");
			}
			if(StringUtils.isBlank(cdramo))
			{
				throw new Exception("No se recibio el ramo");
			}
			if(StringUtils.isBlank(cdtipsit))
			{
				throw new Exception("No se recibio la situacion");
			}
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder().append("Datos incompletos para obtener valor comercial #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		if(exito)
		{
			try
			{
				ManagerRespuestaSmapVO resp = cotizacionManager.cargarSumaAseguradaAuto(cdsisrol,modelo,version,cdramo,cdtipsit);
				
				exito           = resp.isExito();
				respuesta       = resp.getRespuesta();
				respuestaOculta = resp.getRespuestaOculta();
				
				if(exito)
				{
					smap1.putAll(resp.getSmap());
				}
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = new StringBuilder().append("Error inesperado al obtener valor comercial #").append(timestamp).toString();
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### cargarSumaAseguradaAuto ######")
				.append("\n#####################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String agregarClausulaICD()
	{
		logger.info(
				new StringBuilder()
				.append("\n################################")
				.append("\n###### agregarClausulaICD ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		success = true;
		exito   = true;
		
		String cdunieco = null;
		String cdramo   = null;
		String estado   = null;
		String nmpoliza = null;
		String nmsituac = null;
		String cdclausu = null;
		String nmsuplem = null;
		String icd      = null;
		
		//datos completos
		try
		{
			cdunieco = smap1.get("cdunieco");
			cdramo   = smap1.get("cdramo");
			estado   = smap1.get("estado");
			nmpoliza = smap1.get("nmpoliza");
			nmsituac = smap1.get("nmsituac");
			cdclausu = smap1.get("cdclausu");
			nmsuplem = smap1.get("nmsuplem");
			icd      = smap1.get("icd");
			if(StringUtils.isBlank(cdunieco)
					||StringUtils.isBlank(cdramo)
					||StringUtils.isBlank(estado)
					||StringUtils.isBlank(nmpoliza)
					||StringUtils.isBlank(nmsituac)
					||StringUtils.isBlank(cdclausu)
					||StringUtils.isBlank(nmsuplem)
					||StringUtils.isBlank(icd)
					)
			{
				throw new Exception("Dato requerido no encontrado");
			}
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder().append("Datos incompletos para guardar ICD #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		if(exito)
		{
			try
			{
				ManagerRespuestaVoidVO resp = cotizacionManager.agregarClausulaICD(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,nmsituac
						,cdclausu
						,nmsuplem
						,icd);
				exito           = resp.isExito();
				respuesta       = resp.getRespuesta();
				respuestaOculta = resp.getRespuestaOculta();
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = new StringBuilder().append("Error inesperado al guardar ICD #").append(timestamp).toString();
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### agregarClausulaICD ######")
				.append("\n################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String cargarClausulaICD()
	{
		logger.info(
				new StringBuilder()
				.append("\n###############################")
				.append("\n###### cargarClausulaICD ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		success = true;
		exito   = true;
		
		String cdunieco = null;
		String cdramo   = null;
		String estado   = null;
		String nmpoliza = null;
		String nmsituac = null;
		String cdclausu = null;
		String nmsuplem = null;
		
		//datos completos
		try
		{
			cdunieco = smap1.get("cdunieco");
			cdramo   = smap1.get("cdramo");
			estado   = smap1.get("estado");
			nmpoliza = smap1.get("nmpoliza");
			nmsituac = smap1.get("nmsituac");
			cdclausu = smap1.get("cdclausu");
			nmsuplem = smap1.get("nmsuplem");
			if(StringUtils.isBlank(cdunieco)
					||StringUtils.isBlank(cdramo)
					||StringUtils.isBlank(estado)
					||StringUtils.isBlank(nmpoliza)
					||StringUtils.isBlank(nmsituac)
					||StringUtils.isBlank(cdclausu)
					||StringUtils.isBlank(nmsuplem)
					)
			{
				throw new Exception("Dato requerido no encontrado");
			}
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder().append("Datos incompletos para cargar ICD #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
			
			slist1 = new ArrayList<Map<String,String>>();
		}
		
		if(exito)
		{
			try
			{
				ManagerRespuestaSlistVO resp=cotizacionManager.cargarClausulaICD(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,nmsituac
						,cdclausu
						,nmsuplem);
				exito           = resp.isExito();
				respuesta       = resp.getRespuesta();
				respuestaOculta = resp.getRespuestaOculta();
				slist1          = resp.getSlist();
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = new StringBuilder().append("Error inesperado al cargar ICD #").append(timestamp).toString();
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
				
				slist1 = new ArrayList<Map<String,String>>();
			}
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### slist1=").append(slist1)
				.append("\n###### cargarClausulaICD ######")
				.append("\n###############################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String borrarClausulaICD()
	{
		logger.info(
				new StringBuilder()
				.append("\n###############################")
				.append("\n###### borrarClausulaICD ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		success = true;
		exito   = true;
		
		String cdunieco = null;
		String cdramo   = null;
		String estado   = null;
		String nmpoliza = null;
		String nmsituac = null;
		String cdclausu = null;
		String nmsuplem = null;
		String icd      = null;
		
		//datos completos
		try
		{
			cdunieco = smap1.get("cdunieco");
			cdramo   = smap1.get("cdramo");
			estado   = smap1.get("estado");
			nmpoliza = smap1.get("nmpoliza");
			nmsituac = smap1.get("nmsituac");
			cdclausu = smap1.get("cdclausu");
			nmsuplem = smap1.get("nmsuplem");
			icd      = smap1.get("icd");
			if(StringUtils.isBlank(cdunieco)
					||StringUtils.isBlank(cdramo)
					||StringUtils.isBlank(estado)
					||StringUtils.isBlank(nmpoliza)
					||StringUtils.isBlank(nmsituac)
					||StringUtils.isBlank(cdclausu)
					||StringUtils.isBlank(nmsuplem)
					||StringUtils.isBlank(icd)
					)
			{
				throw new Exception("Dato requerido no encontrado");
			}
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder().append("Datos incompletos para borrar ICD #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		if(exito)
		{
			try
			{
				ManagerRespuestaVoidVO resp = cotizacionManager.borrarClausulaICD(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,nmsituac
						,cdclausu
						,nmsuplem
						,icd);
				exito           = resp.isExito();
				respuesta       = resp.getRespuesta();
				respuestaOculta = resp.getRespuestaOculta();
			}
			catch(Exception ex)
			{
				long timestamp  = System.currentTimeMillis();
				exito           = false;
				respuesta       = new StringBuilder().append("Error inesperado al borrar ICD #").append(timestamp).toString();
				respuestaOculta = ex.getMessage();
				logger.error(respuesta,ex);
			}
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### borrarClausulaICD ######")
				.append("\n###############################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String validarDescuentoAgente()
	{
		logger.info(
				new StringBuilder()
				.append("\n####################################")
				.append("\n###### validarDescuentoAgente ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		success = true;
		exito   = true;
		
		String tipoUnidad = null;
		String cdagente   = null;
		String uso        = null;
		String descuento  = null;
		
		String zona       = "9";
		String promotoria = "13";
		
		//datos completos
		try
		{
			if(smap1==null)
			{
				throw new ApplicationException("No hay parametros");
			}
			tipoUnidad = smap1.get("tipoUnidad");
			cdagente   = smap1.get("cdagente");
			uso        = smap1.get("uso");
			descuento  = smap1.get("descuento");
			if(StringUtils.isBlank(tipoUnidad))
			{
				throw new ApplicationException("No se recibio el tipo de unidad");
			}
			if(StringUtils.isBlank(cdagente))
			{
				throw new ApplicationException("No se recibio el codigo de agente");
			}
			if(StringUtils.isBlank(uso))
			{
				throw new ApplicationException("No se recibio el tipo de uso");
			}
			if(StringUtils.isBlank(descuento))
			{
				throw new ApplicationException("No se recibio el descuento");
			}
		}
		catch(ApplicationException ax)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString();
			respuestaOculta = ax.getMessage();
			logger.error(respuesta,ax);
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder("Error al validar datos para el descuento #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		//proceso
		if(exito)
		{
			ManagerRespuestaVoidVO resp = cotizacionManager.validarDescuentoAgente(tipoUnidad,uso,zona,promotoria,cdagente,descuento);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### validarDescuentoAgente ######")
				.append("\n####################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String cargarClienteCotizacion()
	{
		logger.info(
				new StringBuilder()
				.append("\n#####################################")
				.append("\n###### cargarClienteCotizacion ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		success = true;
		exito   = true;
		
		String cdunieco = null;
		String cdramo   = null;
		String estado   = null;
		String nmpoliza = null;
		
		//datos completos
		try
		{
			if(smap1==null)
			{
				throw new ApplicationException("No se recibieron datos");
			}
			cdunieco = smap1.get("cdunieco");
			cdramo   = smap1.get("cdramo");
			estado   = smap1.get("estado");
			nmpoliza = smap1.get("nmpoliza");
			if(StringUtils.isBlank(cdunieco))
			{
				throw new ApplicationException("No se recibio la sucursal");
			}
			if(StringUtils.isBlank(cdramo))
			{
				throw new ApplicationException("No se recibio el producto");
			}
			if(StringUtils.isBlank(estado))
			{
				throw new ApplicationException("No se recibio el estado");
			}
			if(StringUtils.isBlank(nmpoliza))
			{
				throw new ApplicationException("No se recibio la poliza");
			}
		}
		catch(ApplicationException ax)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString();
			respuestaOculta = ax.getMessage();
			logger.error(respuesta,ax);
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder("Error al validar datos #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		//proceso
		if(exito)
		{
			ManagerRespuestaSmapVO resp = cotizacionManager.cargarClienteCotizacion(cdunieco,cdramo,estado,nmpoliza);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			if(exito)
			{
				smap1.putAll(resp.getSmap());
			}
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### cargarClienteCotizacion ######")
				.append("\n#####################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String cargarConceptosGlobalesGrupo()
	{
		logger.info(
				new StringBuilder()
				.append("\n##########################################")
				.append("\n###### cargarConceptosGlobalesGrupo ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		exito   = true;
		success = true;
		
		String cdunieco = null;
		String cdramo   = null;
		String estado   = null;
		String nmpoliza = null;
		String nmsuplem = null;
		String cdperpag = null;
		
		//datos completos
		try
		{
			if(smap1==null)
			{
				throw new ApplicationException("No se recibieron datos");
			}
			cdunieco = smap1.get("cdunieco");
			cdramo   = smap1.get("cdramo");
			estado   = smap1.get("estado");
			nmpoliza = smap1.get("nmpoliza");
			nmsuplem = smap1.get("nmsuplem");
			cdperpag = smap1.get("cdperpag");
			if(StringUtils.isBlank(cdunieco))
			{
				throw new ApplicationException("Falta la sucursal");
			}
			if(StringUtils.isBlank(cdramo))
			{
				throw new ApplicationException("Falta el producto");
			}
			if(StringUtils.isBlank(estado))
			{
				throw new ApplicationException("Falta el estado");
			}
			if(StringUtils.isBlank(nmpoliza))
			{
				throw new ApplicationException("Falta la poliza");
			}
			if(StringUtils.isBlank(nmsuplem))
			{
				throw new ApplicationException("Falta el suplemento");
			}
			if(StringUtils.isBlank(cdperpag))
			{
				throw new ApplicationException("Falta el tipo de pago");
			}
		}
		catch(ApplicationException ax)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString();
			respuestaOculta = ax.getMessage();
			logger.error(respuesta,ax);
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder("Error al validar datos #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		//proceso
		if(exito)
		{
			ManagerRespuestaSmapVO resp = cotizacionManager.cargarConceptosGlobalesGrupo(cdunieco,cdramo,estado,nmpoliza,nmsuplem,cdperpag);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			if(exito)
			{
				smap1.putAll(resp.getSmap());
			}
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### smap1=").append(smap1)
				.append("\n###### cargarConceptosGlobalesGrupo ######")
				.append("\n##########################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String obtenerTiposSituacion()
	{
		logger.info(
				new StringBuilder()
				.append("\n###################################")
				.append("\n###### obtenerTiposSituacion ######")
				.toString()
				);
		
		exito   = true;
		success = true;
		
		ManagerRespuestaSlistVO resp=cotizacionManager.obtenerTiposSituacion();
		exito           = resp.isExito();
		respuesta       = resp.getRespuesta();
		respuestaOculta = resp.getRespuestaOculta();
		if(exito)
		{
			slist1 = resp.getSlist();
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### obtenerTiposSituacion ######")
				.append("\n###################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String guardarValoresSituaciones()
	{
		logger.info(
				new StringBuilder()
				.append("\n#######################################")
				.append("\n###### guardarValoresSituaciones ######")
				.append("\n###### slist1=").append(slist1)
				.toString()
				);

		exito   = true;
		success = true;
		
		//datos completos
		try
		{
			if(slist1==null)
			{
				throw new ApplicationException("No se recibieron datos");
			}
		}
		catch(ApplicationException ax)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString();
			respuestaOculta = ax.getMessage();
			logger.error(respuesta,ax);
		}
		
		//proceso
		if(exito)
		{
			ManagerRespuestaVoidVO resp = cotizacionManager.guardarValoresSituaciones(slist1);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### guardarValoresSituaciones ######")
				.append("\n#######################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String validarCambioZonaGMI()
	{
		logger.info(
				new StringBuilder()
				.append("\n##################################")
				.append("\n###### validarCambioZonaGMI ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		exito   = true;
		success = true;
		
		String cdramo    = null;
		String cdtipsit	 = null;
		String codpostal = null;
		
		//datos completos
		try
		{
			if(smap1==null)
			{
				throw new ApplicationException("No se recibieron datos");
			}
			cdramo    = smap1.get("cdramo");
			cdtipsit  = smap1.get("cdtipsit");
			codpostal = smap1.get("codpostal");
			if(StringUtils.isBlank(cdramo))
			{
				throw new ApplicationException("No se recibio el producto");
			}
			if(StringUtils.isBlank(cdtipsit))
			{
				throw new ApplicationException("No se recibio la modalidad");
			}
			if(StringUtils.isBlank(codpostal))
			{
				throw new ApplicationException("No se recibio el codigo postal");
			}
		}
		catch(ApplicationException ax)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString();
			respuestaOculta = ax.getMessage();
			logger.error(respuesta,ax);
		}
		
		//proceso
		if(exito)
		{
			ManagerRespuestaVoidVO resp=cotizacionManager.validarCambioZonaGMI(
					null //cdunieco
					,cdramo
					,cdtipsit
					,null //estado
					,null //nmpoliza
					,"0"  //nmsuplem
					,null //nmsituac
					,codpostal);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### validarCambioZonaGMI ######")
				.append("\n##################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String validarEnfermedadCatastGMI()
	{
		logger.info(
				new StringBuilder()
				.append("\n########################################")
				.append("\n###### validarEnfermedadCatastGMI ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		exito   = true;
		success = true;
		
		String cdramo   = null;
		String circHosp = null;
		
		//datos completos
		try
		{
			if(smap1==null)
			{
				throw new ApplicationException("No se recibieron datos");
			}
			cdramo   = smap1.get("cdramo");
			circHosp = smap1.get("circHosp");
			if(StringUtils.isBlank(cdramo))
			{
				throw new ApplicationException("No se recibio el producto");
			}
			if(StringUtils.isBlank(circHosp))
			{
				throw new ApplicationException("No se recibio el circulo hospitalario");
			}
		}
		catch(ApplicationException ax)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString();
			respuestaOculta = ax.getMessage();
			logger.error(respuesta,ax);
		}
		
		//proceso
		if(exito)
		{
			ManagerRespuestaVoidVO resp=cotizacionManager.validarEnfermedadCatastGMI(
					null //cdunieco
					,cdramo
					,null //estado
					,null //nmpoliza
					,"0"  //nmsuplem
					,null //nmsituac
					,circHosp);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### validarEnfermedadCatastGMI ######")
				.append("\n########################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String guardarContratanteColectivo()
	{
		logger.debug(Utils.log(
				 "\n#########################################"
				,"\n###### guardarContratanteColectivo ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos");
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String estado   = smap1.get("estado");
			String nmpoliza = smap1.get("nmpoliza");
			String nmsuplem = smap1.get("nmsuplem");
			String rfc      = smap1.get("cdrfc");
			String cdperson = smap1.get("cdperson");
			String nombre   = smap1.get("nombre");
			String cdpostal = smap1.get("codpostal");
			String cdedo    = smap1.get("cdedo");
			String cdmunici = smap1.get("cdmunici");
			String dsdomici = smap1.get("dsdomici");
			String nmnumero = smap1.get("nmnumero");
			String nmnumint = smap1.get("nmnumint");
			String confirmaEmision = smap1.get("confirmaEmision");
			boolean esConfirmaEmision = (StringUtils.isNotBlank(confirmaEmision) && Constantes.SI.equalsIgnoreCase(confirmaEmision));
			
			checkBlank(cdunieco , "No se recibio la sucursal");
			checkBlank(cdramo   , "No se recibio el ramo");
			checkBlank(estado   , "No se recibio el estado");
			checkBlank(nmpoliza , "No se recibio el numero de poliza");
			if(!esConfirmaEmision) checkBlank(nombre   , "No se recibio el nombre");
			if(!esConfirmaEmision) checkBlank(cdpostal , "No se recibio el codigo postal");
			if(!esConfirmaEmision) checkBlank(cdedo    , "No se recibio el estado");
			if(!esConfirmaEmision) checkBlank(cdmunici , "No se recibio el municipio");
			if(esConfirmaEmision)  checkBlank(cdperson , "No se recibio el cdperson");
			
			ManagerRespuestaVoidVO resp = cotizacionManager.guardarContratanteColectivo(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplem
					,rfc
					,cdperson
					,nombre
					,cdpostal
					,cdedo
					,cdmunici
					,dsdomici
					,nmnumero
					,nmnumint
					,esConfirmaEmision);
			
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### guardarContratanteColectivo ######"
				,"\n#########################################"
				));
		return SUCCESS;
	}
	
	public String cargarTramite()
	{
		logger.debug(Utils.log(
				 "\n###########################"
				,"\n###### cargarTramite ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			setCheckpoint("Validando datos");
			checkNull(smap1, "No se recibieron datos");
			String ntramite = smap1.get("ntramite");
			checkBlank(ntramite, "No se recibio el numero de tramite");
			
			ManagerRespuestaSmapVO resp = cotizacionManager.cargarTramite(ntramite);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			if(exito)
			{
				smap1.putAll(resp.getSmap());
			}
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### cargarTramite ######"
				,"\n###########################"
				));
		return SUCCESS;
	}
	
	public String cargarTipoCambioWS()
	{
		logger.debug(Utils.log(
				 "\n################################"
				,"\n###### cargarTipoCambioWS ######"
				));
		
		smap1=new HashMap<String,String>();
		smap1.put("dolar" , "0");
		
		ResponseTipoCambio rtc=tipoCambioService.obtieneTipoCambioDolarGS(2);
		if(rtc!=null&&rtc.getTipoCambio()!=null&&rtc.getTipoCambio().getVenCam()!=null)
		{
			smap1.put("dolar" , rtc.getTipoCambio().getVenCam().toString());
		}
		
		logger.debug(Utils.log(
				 "\n###### smap1=",smap1
				,"\n###### cargarTipoCambioWS ######"
				,"\n################################"
				));
		return SUCCESS;
	}
	
	private String extraerStringDeCelda(Cell cell)
	{
		try
		{
			cell.setCellType(Cell.CELL_TYPE_STRING);
			String cadena = cell.getStringCellValue();
			return cadena==null?"":cadena;
		}
		catch(Exception ex)
		{
			return "";
		}
	}
	
	public String complementoSaludGrupo()
	{
		logger.debug(Utils.log(
				 "\n###################################"
				,"\n###### complementoSaludGrupo ######"
				,"\n###### smap1="         , smap1
				,"\n###### censo="         , censo
				,"\n###### censoFileName=" , censoFileName
				));
		try
		{
			success = true;
			exito   = false;
			
			UserVO user = Utils.validateSession(session);
			
			Utils.validate(smap1 , "No se recibieron datos");
			
			String cdunieco     = smap1.get("cdunieco");
			String cdramo       = smap1.get("cdramo");
			String cdtipsit     = smap1.get("cdtipsit");
			String estado       = smap1.get("estado");
			String nmpoliza     = smap1.get("nmpoliza");
			String ntramite     = smap1.get("ntramite");
			String complemento  = smap1.get("complemento");
			String cdagente     = smap1.get("cdagente");
			String codpostalCli = smap1.get("codpostal");
			String cdestadoCli  = smap1.get("cdestado");
			String cdmuniciCli  = smap1.get("cdmunici");
			String cdplan1      = smap1.get("cdplan1");
			String cdplan2      = smap1.get("cdplan2");
			String cdplan3      = smap1.get("cdplan3");
			String cdplan4      = smap1.get("cdplan4");
			String cdplan5      = smap1.get("cdplan5");
			
			Utils.validate(
					cdunieco      , "No se recibio la sucursal"
					,cdramo       , "No se recibio el producto"
					,cdtipsit     , "No se recibio la modalidad"
					,estado       , "No se recibio el estado"
					,nmpoliza     , "No se recibio el estado"
					,ntramite     , "No se recibio el tramite"
					,complemento  , "No se recibio el complemento"
					,cdagente     , "No se recibio la clave de agente"
					,codpostalCli , "No se recibio el codigo postal"
					,cdestadoCli  , "No se recibio el estado"
					,cdmuniciCli  , "No se recibio el municipio"
					,cdplan1      , "No se recibio el plan 1"
					);
			
			Map<String,Object> managerResp =cotizacionManager.complementoSaludGrupo(
					ntramite
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,complemento
					,censo
					,getText("ruta.documentos.temporal")
					,getText("dominio.server.layouts")
					,getText("user.server.layouts")
					,getText("pass.server.layouts")
					,getText("directorio.server.layouts")
					,cdtipsit
					,user.getUser()
					,user.getRolActivo().getClave()
					,cdagente
					,codpostalCli
					,cdestadoCli
					,cdmuniciCli
					,cdplan1
					,cdplan2
					,cdplan3
					,cdplan4
					,cdplan5
					);
			
			smap1.put("erroresCenso"    , (String)managerResp.get("erroresCenso"));
			smap1.put("filasLeidas"     , (String)managerResp.get("filasLeidas"));
			smap1.put("filasProcesadas" , (String)managerResp.get("filasProcesadas"));
			smap1.put("filasErrores"    , (String)managerResp.get("filasErrores"));
			
			slist1 = (List<Map<String,String>>)managerResp.get("registros");
			
			exito = true;
			
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		logger.debug(Utils.log(
				 "\n###### complementoSaludGrupo ######"
				,"\n###################################"
				));
		return SUCCESS;
	}
	
	public String guardarConfiguracionGarantias()
	{
		logger.debug(Utils.log(
				 "\n###########################################"
				,"\n###### guardarConfiguracionGarantias ######"
				,"\n###### smap1="  , smap1
				,"\n###### slist1=" , slist1!=null
				));
		
		try
		{
			Utils.validate(smap1  , "No se recibieron datos");
			Utils.validate(slist1 , "No se recibieron datos de configuraci\u00F3n");
			
			String cdramo   = smap1.get("cdramo");
			String cdtipsit = smap1.get("cdtipsit");
			String cdplan   = smap1.get("cdplan");
			String cdpaq    = smap1.get("cdpaq");
			String dspaq    = smap1.get("dspaq");
			String derpol   = smap1.get("derpol");
			
			Utils.validate(
					cdramo    , "No se recibi\u00F3 el producto"
					,cdtipsit , "No se recibi\u00F3 la modalidad"
					,cdplan   , "No se recibi\u00F3 la clave de plan"
					,cdpaq    , "No se recibi\u00F3 la clave de paquete"
					,dspaq    , "No se recibi\u00F3 el nombre de paquete"
					,derpol   , "No se recibieron los derechos de p\u00F3liza"
					);
			
			smap1.put("cdPaqueteNuevo",cotizacionManager.guardarConfiguracionGarantias(
					cdramo
					,cdtipsit
					,cdplan
					,cdpaq
					,dspaq
					,derpol
					,slist1
					));
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### guardarConfiguracionGarantias ######"
				,"\n###########################################"
				));
		return SUCCESS;
	}
	
	public String obtenerCoberturasPlanColec()
	{
		logger.info(""
				+ "\n########################################"
				+ "\n###### obtenerCoberturasPlanColec ######"
				+ "\nsmap1: "+smap1
				);
		try
		{
			success = true;
			exito   = true;
			slist1 = cotizacionManager.obtenerCoberturasPlanColec(smap1.get("cdramo"),smap1.get("cdtipsit"),smap1.get("cdplan"));
		}
		catch(Exception ex)
		{
			long timestamp=System.currentTimeMillis();
			logger.error(timestamp+" error al obtener coberturas plan");
			respuesta       = "Error inesperado #"+timestamp;
			respuestaOculta = ex.getMessage();
			exito           = false;
		}
		logger.info(""
				+ "\n###### obtenerCoberturasPlanColec ######"
				+ "\n########################################"
				);
		return SUCCESS;
	}
	
	///////////////////////////////
	////// getters y setters //////
	/*///////////////////////////*/
	public void setKernelManager(KernelManagerSustituto kernelManager) {
		this.kernelManager = kernelManager;
	}

	public void setCatalogosManager(CatalogosManager catalogosManager) {
		this.catalogosManager = catalogosManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Map<String, String> getSmap1() {
		return smap1;
	}

	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}

	public Map<String, Item> getImap() {
		return imap;
	}

	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<Map<String, String>> getSlist2() {
		return slist2;
	}

	public void setSlist2(List<Map<String, String>> slist2) {
		this.slist2 = slist2;
	}

	public void setConsultasManager(ConsultasManager consultasManager) {
		this.consultasManager = consultasManager;
	}

	public void setStoredProceduresManager(
			StoredProceduresManager storedProceduresManager) {
		this.storedProceduresManager = storedProceduresManager;
	}
	public void setPantallasManager(PantallasManager pantallasManager) {
		this.pantallasManager = pantallasManager;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public void setNadaService(NadaService nadaService) {
		this.nadaService = nadaService;
	}

	public void setIce2sigsService(Ice2sigsService ice2sigsService) {
		this.ice2sigsService = ice2sigsService;
	}

	public void setTipoCambioService(TipoCambioDolarGSService tipoCambioService) {
		this.tipoCambioService = tipoCambioService;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getRespuestaOculta() {
		return respuestaOculta;
	}

	public void setRespuestaOculta(String respuestaOculta) {
		this.respuestaOculta = respuestaOculta;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public File getCenso() {
		return censo;
	}

	public void setCenso(File censo) {
		this.censo = censo;
	}

	public String getCensoFileName() {
		return censoFileName;
	}

	public void setCensoFileName(String censoFileName) {
		this.censoFileName = censoFileName;
	}

	public String getCensoContentType() {
		return censoContentType;
	}

	public void setCensoContentType(String censoContentType) {
		this.censoContentType = censoContentType;
	}

	public List<Map<String, Object>> getOlist1() {
		return olist1;
	}

	public void setOlist1(List<Map<String, Object>> olist1) {
		this.olist1 = olist1;
	}

	public void setCotizacionManager(CotizacionManager cotizacionManager) {
		this.cotizacionManager = cotizacionManager;
		this.cotizacionManager.setSession(session);
	}

	public void setSiniestrosManager(SiniestrosManager siniestrosManager) {
		this.siniestrosManager = siniestrosManager;
	}

	public void setAgentePorFolioService(AgentePorFolioService agentePorFolioService) {
		this.agentePorFolioService = agentePorFolioService;
	}

	public void setServiciosManager(ServiciosManager serviciosManager) {
		this.serviciosManager = serviciosManager;
	}
	
	public static void main(String[] args)
	{
		FTPSUtils.downloadChildrenFiles("10.1.1.134", "weblogic", "weblogic123", "/home/jtezva/Escritorio/destino", "/export/home/weblogic/origen");
	}

	public FlujoVO getFlujo() {
		return flujo;
	}

	public void setFlujo(FlujoVO flujo) {
		this.flujo = flujo;
	}

}