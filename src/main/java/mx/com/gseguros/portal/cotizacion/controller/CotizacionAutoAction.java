package mx.com.gseguros.portal.cotizacion.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.mesacontrol.model.FlujoVO;
import mx.com.gseguros.mesacontrol.service.FlujoMesaControlManager;
import mx.com.gseguros.portal.consultas.service.ConsultasManager;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlist2SmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.cotizacion.service.CotizacionAutoManager;
import mx.com.gseguros.portal.cotizacion.service.CotizacionManager;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

public class CotizacionAutoAction extends PrincipalCoreAction
{
	private static final long   serialVersionUID = -5890606583100529056L;
	private static final Logger logger           = LoggerFactory.getLogger(CotizacionAutoAction.class);
	
	private CotizacionAutoManager cotizacionAutoManager;
	
	private Map<String,String>       smap1            = null;
	private Map<String,String>       smap2            = null;
	private String                   respuesta;
	private String                   respuestaOculta  = null;
	private boolean                  exito            = false;
	private Map<String,Item>         imap             = null;
	private List<Map<String,String>> slist1           = null;
	private List<Map<String,String>> slist2           = null;
	private List<Map<String,String>> slist3           = null;
	private File                     excel            = null;
	private String                   excelFileName    = null;
	private String                   excelContentType = null;
	private DateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
	
	@Autowired
	private ConsultasManager consultasManager;
	
	private FlujoVO flujo;
	
	@Autowired
	private FlujoMesaControlManager flujoMesaControlManager;
	
	@Autowired
	private CotizacionManager cotizacionManager;

	/**
	 * Constructor que se asegura de que el action tenga sesion
	 */
	public CotizacionAutoAction()
	{
		logger.debug("new CotizacionAutoAction");
		this.session=ActionContext.getContext().getSession();
	}
	
	public String cotizacionAutoIndividual()
	{
		logger.debug(Utils.log(""
				,"\n######################################"
				,"\n###### cotizacionAutoIndividual ######"
				,"\n###### smap1=" , smap1
				,"\n###### flujo=" , flujo
				));
		
		String result = ERROR;
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			boolean renovacion = false;
			
			if(flujo!=null)
			{
				logger.debug(Utils.log("", "se va a crear el smap1 porque se entra desde flujo=", smap1));
				
				smap1 = new HashMap<String,String>();
				smap1.put("ntramite" , flujo.getNtramite());
				smap1.put("cdunieco" , flujo.getCdunieco());
				smap1.put("cdramo"   , flujo.getCdramo());
				
				logger.debug(Utils.log("", "recuperando tramite"));
				
				Map<String,Object> datosFlujo = flujoMesaControlManager.recuperarDatosTramiteValidacionCliente(flujo);
				
				Map<String,String> tramite = (Map<String,String>)datosFlujo.get("TRAMITE");
				logger.debug(Utils.log("", "tramite=", tramite));
				
				smap1.put("cdtipsit" , tramite.get("CDTIPSIT"));
				
				logger.debug(Utils.log("", "smap1 creado=", smap1));
				
				renovacion = TipoTramite.RENOVACION.getCdtiptra().equals(tramite.get("CDTIPTRA"));
				logger.debug("Es renovacion = {}", renovacion);
			}
			
			String cdusuari  = usuario.getUser()
			       ,cdsisrol = usuario.getRolActivo().getClave();
			
			smap1.put("cdusuari" , cdusuari);
			smap1.put("cdsisrol" , cdsisrol);
			
			Utils.validate(smap1,"No se recibieron datos");
			
			String ntramite  = smap1.get("ntramite")
			       ,cdunieco = smap1.get("cdunieco")
			       ,cdramo   = smap1.get("cdramo")
			       ,cdtipsit = smap1.get("cdtipsit");
			
			Utils.validate(
					cdramo    , "No se recibi\u00f3 el producto"
					,cdtipsit , "No se recibi\u00f3 la modalidad"
					);
			
			Map<String,Object> resp = cotizacionAutoManager.cotizacionAutoIndividual(
					ntramite
					,cdunieco
					,cdramo
					,cdtipsit
					,cdusuari
					,cdsisrol
					,flujo
					,renovacion
					);
			
			smap1.putAll((Map<String,String>)resp.get("smap"));
			imap = (Map<String,Item>)resp.get("items");
			
			exito = true;
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### exito="  , exito
				,"\n###### result=" , result
				,"\n###### cotizacionAutoIndividual ######"
				,"\n######################################"
				));
		return result;
	}
	
	public String cargarRetroactividadSuplemento()
	{
		logger.debug(Utils.log(""
				,"\n############################################"
				,"\n###### cargarRetroactividadSuplemento ######"
				,"\n###### smap1=", smap1
				));
		
		logger.debug(Utils.log("","Validando datos de entrada"));
		
		try
		{
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdunieco = smap1.get("cdunieco")
			       ,cdramo   = smap1.get("cdramo")
			       ,cdtipsup = smap1.get("cdtipsup")
			       ,cdusuari = smap1.get("cdusuari")
			       ,cdtipsit = smap1.get("cdtipsit");
			
			Utils.validate(
					cdunieco  , "No se recibi\u00f3 la sucursal"
					,cdramo   , "No se recibi\u00f3 el producto"
					,cdtipsup , "No se recibi\u00f3 el tipo de suplemento"
					,cdusuari , "No se recibi\u00f3 el nombre de usuario"
					,cdtipsit , "No se recibi\u00f3 la modalidad"
					);
			
			ManagerRespuestaSmapVO resp=cotizacionAutoManager.cargarRetroactividadSuplemento(cdunieco,cdramo,cdtipsup,cdusuari,cdtipsit);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
			
			smap1.putAll(resp.getSmap());
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### cargarRetroactividadSuplemento ######"
				,"\n############################################"
				));
		return SUCCESS;
	}
	
	public String cargarSumaAseguradaRamo5()
	{
		logger.debug(Utils.log(""
				,"\n######################################"
				,"\n###### cargarSumaAseguradaRamo5 ######"
				,"\n###### smap1=", smap1
				));
		
		logger.debug(Utils.log("","Validando datos de entrada"));
		
		try
		{
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdtipsit = smap1.get("cdtipsit")
			       ,clave    = smap1.get("clave")
			       ,modelo   = smap1.get("modelo")
			       ,cdsisrol = smap1.get("cdsisrol");
			
			Utils.validate(
					cdtipsit  , "No se recibi\u00f3 la modalidad"
					,clave    , "No se recibi\u00f3 la clave del auto"
					,modelo   , "No se recibi\u00f3 el modelo del auto"
					,cdsisrol , "No se recibi\u00f3 el rol del usuario"
					);
			
			ManagerRespuestaSmapVO resp = cotizacionAutoManager.cargarSumaAseguradaRamo5(cdtipsit,clave,modelo,cdsisrol);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
			
			smap1.putAll(resp.getSmap());
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### exito=" , exito
				,"\n###### smap1=" , smap1
				,"\n###### cargarSumaAseguradaRamo5 ######"
				,"\n######################################"
				));
		return SUCCESS;
	}
	
	public String emisionAutoIndividual()
	{
		logger.debug(Utils.log(""
				,"\n###################################"
				,"\n###### emisionAutoIndividual ######"
				,"\n###### flujo=" , flujo
				,"\n###### smap1=" , smap1
				));
		
		String result = ERROR;
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			if(flujo!=null && smap1==null)
			{
				smap1 = new HashMap<String,String>();
				smap1.put("cdunieco" , flujo.getCdunieco());
				smap1.put("cdramo"   , flujo.getCdramo());
				smap1.put("estado"   , flujo.getEstado());
				
				logger.debug(Utils.log("", "recuperando tramite"));
				
				Map<String,Object> datosFlujo = flujoMesaControlManager.recuperarDatosTramiteValidacionCliente(flujo);
				
				Map<String,String> tramite = (Map<String,String>)datosFlujo.get("TRAMITE");
				logger.debug(Utils.log("", "tramite=", tramite));
				
				smap1.put("cdtipsit" , tramite.get("CDTIPSIT"));
				
				smap1.put("nmpoliza" , tramite.get("NMSOLICI"));
				
				smap1.put("ntramite" , flujo.getNtramite());
				
				smap1.put("swexiper" , cotizacionManager.recuperarOtvalorTramitePorDsatribu(flujo.getNtramite(), "SWEXIPER"));
				
				logger.debug(Utils.log("", "smap1 creado=", smap1));
				
			}
			
			//si viene de la pantalla de cotizacion hay que recuperar los atributos de la pantalla actual
			if(flujo!=null)
			{
				if("RECUPERAR".equals(flujo.getAux()))
				{
					//se recibieron 3 propiedades de una pantalla anterior, hay que actualizarlas
					logger.debug("flujo antes de actualizar sus 3 propiedades de pantalla nueva={}",flujo);
					flujoMesaControlManager.recuperarPropiedadesDePantallaComponenteActualPorConexionSinPermisos(flujo);
					
					logger.debug("flujo despues de actualizar sus 3 propiedades de pantalla nueva={}",flujo);
				}
			}
			
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdunieco = smap1.get("cdunieco")
			       ,cdramo   = smap1.get("cdramo")
			       ,cdtipsit = smap1.get("cdtipsit")
			       ,estado   = smap1.get("estado")
			       ,nmpoliza = smap1.get("nmpoliza")
			       ,ntramite = smap1.get("ntramite");
			
			Utils.validate(
					cdunieco , "No se recibi\u00f3 la sucursal"
					,cdramo   , "No se recibi\u00f3 el ramo"
					,cdtipsit , "No se recibi\u00f3 la modalidad"
					,estado   , "No se recibi\u00f3 el estado de la poliza"
					,nmpoliza , "No se recibi\u00f3 el numero de poliza"
					,ntramite , "No se recibi\u00f3 el numero de tramite"
					);
			
			String cdusuari  = usuario.getUser()
			       ,cdsisrol = usuario.getRolActivo().getClave();
			
			ManagerRespuestaImapSmapVO resp=cotizacionAutoManager.emisionAutoIndividual(cdunieco,cdramo,cdtipsit,estado,nmpoliza,ntramite,cdusuari,cdsisrol);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
			
			smap1.putAll(resp.getSmap());
			smap1.put("cdsisrol" , cdsisrol);
			imap = resp.getImap();
			
			HashMap<String,String> params = new HashMap<String, String>();
			params.put("cdunieco" , cdunieco);
			params.put("cdramo"   , cdramo);
			params.put("estado"   , estado);
			params.put("nmpoliza" , nmpoliza);
			params.put("nmsuplem" , "0");
			
			Map<String,String> fechas = consultasManager.consultaFeNacContratanteAuto(params);
			
			if(fechas != null && !fechas.isEmpty())
			{
				smap1.put("AplicaCobVida" , fechas.get("APLICA"));
				smap1.put("FechaMinEdad"  , fechas.get("FECHAMIN"));
				smap1.put("FechaMaxEdad"  , fechas.get("FECHAMAX"));
			}
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### result=", result
				,"\n###### emisionAutoIndividual ######"
				,"\n###################################"
				));
		return result;
	}
	
	public String cargarDatosComplementariosAutoInd()
	{
		logger.debug(Utils.log(""
				,"\n###############################################"
				,"\n###### cargarDatosComplementariosAutoInd ######"
				,"\n###### smap1=", smap1
				));
		
		try
		{
			logger.debug(Utils.log("","Validando datos de entrada"));
			
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdunieco  = smap1.get("cdunieco")
			       ,cdramo   = smap1.get("cdramo")
			       ,estado   = smap1.get("estado")
			       ,nmpoliza = smap1.get("nmpoliza");
			
			Utils.validate(
					cdunieco , "No se recibi\u00f3 la sucursal"
					,cdramo   , "No se recibi\u00f3 el producto"
					,estado   , "No se recibi\u00f3 el estado"
					,nmpoliza , "No se recibi\u00f3 la poliza"
					);
			
			ManagerRespuestaSmapVO resp = cotizacionAutoManager.cargarDatosComplementariosAutoInd(cdunieco,cdramo,estado,nmpoliza);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
			
			smap1.putAll(resp.getSmap());
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### exito=" , exito
				,"\n###### smap1=" , smap1
				,"\n###### cargarDatosComplementariosAutoInd ######"
				,"\n###############################################"
				));
		return SUCCESS;
	}
	
	public String cargarValoresSituacion()
	{
		logger.debug(Utils.log(""
				,"\n####################################"
				,"\n###### cargarValoresSituacion ######"
				,"\n###### smap1=", smap1
				));
		
		try
		{
			logger.debug(Utils.log("","Validando datos de entrada"));
			
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdunieco  = smap1.get("cdunieco")
			       ,cdramo   = smap1.get("cdramo")
			       ,estado   = smap1.get("estado")
			       ,nmpoliza = smap1.get("nmpoliza")
			       ,nmsituac = smap1.get("nmsituac");
			
			Utils.validate(
					cdunieco  , "No se recibi\u00f3 la sucursal"
					,cdramo   , "No se recibi\u00f3 el producto"
					,estado   , "No se recibi\u00f3 el estado"
					,nmpoliza , "No se recibi\u00f3 la poliza"
					,nmsituac , "No se recibi\u00f3 el numero de situacion"
					);
			
			ManagerRespuestaSmapVO resp = cotizacionAutoManager.cargarValoresSituacion(cdunieco,cdramo,estado,nmpoliza,nmsituac);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
			
			smap1=resp.getSmap();
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### exito=" , exito
				,"\n###### smap1=" , smap1
				,"\n###### cargarValoresSituacion ######"
				,"\n####################################"
				));
		return SUCCESS;
	}
	
	public String movimientoMpoliper()
	{
		logger.debug(Utils.log(""
				,"\n################################"
				,"\n###### movimientoMpoliper ######"
				,"\n###### smap1=", smap1
				));
		
		try
		{
			logger.debug(Utils.log("","Validando datos de entrada"));
			
			Utils.validate(smap1, "No se recibieron datos de entrada");
			
			String cdunieco = smap1.get("cdunieco")
			       ,cdramo   = smap1.get("cdramo")
			       ,estado   = smap1.get("estado")
			       ,nmpoliza = smap1.get("nmpoliza")
			       ,nmsituac = smap1.get("nmsituac")
			       ,cdrol    = smap1.get("cdrol")
			       ,cdperson = smap1.get("cdperson")
			       ,nmsuplem = smap1.get("nmsuplem")
			       ,status   = smap1.get("status")
			       ,nmorddom = smap1.get("nmorddom")
			       ,swreclam = smap1.get("swreclam")
			       ,accion   = smap1.get("accion")
			       ,swexiper = smap1.get("swexiper");
			
			Utils.validate(
					cdunieco , "No se recibi\u00f3 la sucursal"
					,cdramo   , "No se recibi\u00f3 el producto"
					,estado   , "No se recibi\u00f3 el estado"
					,nmpoliza , "No se recibi\u00f3 el numero de poliza"
					,nmsituac , "No se recibi\u00f3 el numero de situacion"
					,cdrol    , "No se recibi\u00f3 el rol"
					,cdperson , "No se recibi\u00f3 la clave de persona"
					,nmsuplem , "No se recibi\u00f3 el suplemento"
					,status   , "No se recibi\u00f3 el status"
					,nmorddom , "No se recibi\u00f3 el numero ordinal"
					,accion   , "No se recibi\u00f3 la accion"
					,swexiper , "No se recibi\u00f3 el estado de persona existente"
					);
			
			ManagerRespuestaVoidVO resp = cotizacionAutoManager.movimientoMpoliper(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsituac
					,cdrol
					,cdperson
					,nmsuplem
					,status
					,nmorddom
					,swreclam
					,accion
					,swexiper);
			
			exito = resp.isExito();
			
			if(!exito)
			{
				throw new ApplicationException(resp.getRespuesta());
			}
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### movimientoMpoliper ######"
				,"\n################################"
				));
		return SUCCESS;
	}
	
	public String guardarComplementariosAutoIndividual()
	{
		logger.debug(Utils.log(""
				,"\n##################################################"
				,"\n###### guardarComplementariosAutoIndividual ######"
				,"\n###### smap1=" , smap1
				,"\n###### smap2=" , smap2
				));
		
		try
		{
			logger.debug(Utils.log("","Validando datos de entrada"));
			
			Utils.validate(smap1, "No se recibieron datos de poliza");
			
			Utils.validate(smap2, "No se recibieron datos adicionales de poliza");
			
			String cdunieco     = smap1.get("cdunieco")
			       ,cdramo      = smap1.get("cdramo")
			       ,estado      = smap1.get("estado")
			       ,nmpoliza    = smap1.get("nmpoliza")
			       ,agenteSec   = smap1.get("agente_sec")
			       ,porpartiSec = smap1.get("porparti")
			       ,feini       = smap1.get("feini")
			       ,fefin       = smap1.get("fefin")
			       ,ntramite    = smap1.get("ntramite")
			       ,cdagente    = smap1.get("cdagente");
			
			Utils.validate(
					cdunieco  , "No se recibi\u00f3 la sucursal"
					,cdramo   , "No se recibi\u00f3 el producto"
					,estado   , "No se recibi\u00f3 el estado de la poliza"
					,nmpoliza , "No se recibi\u00f3 el numero de poliza"
					,feini    , "No se recibi\u00f3 la fecha de inicio"
					,fefin    , "No se recibi\u00f3 la fecha de fin"
					,ntramite , "No se recibi\u00f3 el numero de tramite"
					,cdagente , "No se recibi\u00f3 el agente"
					);
			
			ManagerRespuestaVoidVO resp = cotizacionAutoManager.guardarComplementariosAutoIndividual(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,agenteSec
					,porpartiSec
					,feini
					,fefin
					,smap1
					,smap2
					,ntramite
					,cdagente
					);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### guardarComplementariosAutoIndividual ######"
				,"\n##################################################"
				));
		return SUCCESS;
	}
	
	public String guardarConfigCotizacion()
	{
		logger.debug(Utils.log(""
				,"\n#####################################"
				,"\n###### guardarConfigCotizacion ######"
				,"\n###### smap1=", smap1
				));
		
		try
		{
			logger.debug(Utils.log("","Validando datos de entrada"));
			
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdramo    = smap1.get("cdramo")
			       ,cdtipsit = smap1.get("cdtipsit")
			       ,cdusuari = smap1.get("cdusuari");
			
			Utils.validate(
					cdramo    , "No se recibi\u00f3 el producto"
					,cdtipsit , "No se recibi\u00f3 la modalidad"
					,cdusuari , "No se recibi\u00f3 el nombre de usuario"
					);
			
			ManagerRespuestaVoidVO resp = cotizacionAutoManager.guardarConfiguracionCotizacion(cdramo,cdtipsit,cdusuari,smap1);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### exito="     , exito
				,"\n###### respuesta=" , respuesta
				,"\n###### guardarConfigCotizacion ######"
				,"\n#####################################"
				));
		return SUCCESS;
	}
	
	public String cargarConfigCotizacion()
	{
		logger.debug(Utils.log(""
				,"\n####################################"
				,"\n###### cargarConfigCotizacion ######"
				,"\n###### smap1=", smap1
				));
		
		try
		{
			logger.debug(Utils.log("","Validando datos de entrada"));
			
			Utils.validate(smap1, "No se recibieron datos de entrada");
			
			String cdramo    = smap1.get("cdramo")
			       ,cdtipsit = smap1.get("cdtipsit")
			       ,cdusuari = smap1.get("cdusuari");
			
			Utils.validate(
					cdramo    , "No se recibi\u00f3 el producto"
					,cdtipsit , "No se recibi\u00f3 la modalidad"
					,cdusuari , "No se recibi\u00f3 el nombre de usuario"
					);
			
			ManagerRespuestaSmapVO resp = cotizacionAutoManager.cargarConfiguracionCotizacion(cdramo,cdtipsit,cdusuari);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
			
			smap1 = resp.getSmap();
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### exito="     , exito
				,"\n###### respuesta=" , respuesta
				,"\n###### cargarConfigCotizacion ######"
				,"\n####################################"
				));
		return SUCCESS;
	}
	
	public String cargarParamerizacionConfiguracionCoberturas()
	{
		logger.debug(Utils.log(""
				,"\n#########################################################"
				,"\n###### cargarParamerizacionConfiguracionCoberturas ######"
				,"\n###### smap1=", smap1
				));
		
		try
		{
			logger.debug(Utils.log("","Validando datos de entrada"));
			
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdtipsit      = smap1.get("cdtipsit")
			       ,cdsisrol     = smap1.get("cdsisrol")
			       ,negocio      = smap1.get("negocio")
			       ,tipoServicio = smap1.get("tipoServicio")
			       ,modelo       = smap1.get("modelo")
			       ,tipoPersona  = smap1.get("tipoPersona")
			       ,submarca     = smap1.get("submarca")
			       ,clavegs      = smap1.get("clavegs");
			
			Utils.validate(
					cdtipsit      , "No se recibi\u00f3 la modalidad"
					,cdsisrol     , "No se recibi\u00f3 el rol"
					,negocio      , "No se recibi\u00f3 el negocio"
					,tipoServicio , "No se recibi\u00f3 el tipo de servicio"
					,modelo       , "No se recibi\u00f3 el modelo"
					,tipoPersona  , "No se recibi\u00f3 el tipo de persona"
					,submarca     , "No se recibi\u00f3 la submarca"
					,clavegs      , "No se recibi\u00f3 la clave gs"
					);
			
			ManagerRespuestaSlistVO resp = cotizacionAutoManager.cargarParamerizacionConfiguracionCoberturas(
					cdtipsit
					,cdsisrol
					,negocio
					,tipoServicio
					,modelo
					,tipoPersona
					,submarca
					,clavegs
					);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
			
			slist1 = resp.getSlist();
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### exito="     , exito
				,"\n###### respuesta=" , respuesta
				,"\n###### cargarParamerizacionConfiguracionCoberturas ######"
				,"\n#########################################################"
				));
		return SUCCESS;
	}
	
	public String cotizacionAutoFlotilla()
	{
		logger.debug(Utils.log(""
				,"\n####################################"
				,"\n###### cotizacionAutoFlotilla ######"
				,"\n###### smap1=", smap1
				,"\n###### flujo=", flujo
				));
		
		String result = ERROR;
		
		try
		{
			UserVO usuario  = Utils.validateSession(session);
			
			String cdusuari  = usuario.getUser()
			       ,cdsisrol = usuario.getRolActivo().getClave();
			
			boolean renovacion = false;
			
			if(flujo!=null
				&&
				(
					smap1==null
					||!"MARCO_ENDOSOS_GENERAL".equals(smap1.get("pantallaOrigen"))
				)
			)//si hay flujo y (o no hay mapa o hay mapa pero no vengo de endosos)
			{
				//EN ENDOSO NO SE BOORA PORQUE
				//TRAE DATOS DEL MARCO
				
				logger.debug(Utils.log("FLUJO: se creara el mapa porque viene de flujo y no es endoso (es emision)"));
				
				smap1 = new HashMap<String,String>();
				
				smap1.put("cdunieco" , flujo.getCdunieco());
				smap1.put("cdramo"   , flujo.getCdramo());
				smap1.put("cdtipsit" , "AR");
				smap1.put("ntramite" , flujo.getNtramite());
				smap1.put("tipoflot" , flujo.getAux().split(",")[0].split(":")[1]); //primer split= tipoflot:P onComprar:16', segundo split tipoflot P
				
				logger.debug(Utils.log("", "el mapa creado desde flujo es=", smap1));
				
				Map<String,Object> datosFlujo = flujoMesaControlManager.recuperarDatosTramiteValidacionCliente(flujo);
				
				Map<String,String> tramite = (Map<String,String>)datosFlujo.get("TRAMITE");
				logger.debug(Utils.log("", "tramite=", tramite));
				
				renovacion = TipoTramite.RENOVACION.getCdtiptra().equals(tramite.get("CDTIPTRA"));
				logger.debug("Es renovacion = {}", renovacion);
			}
			else
			{
				logger.debug("FLUJO: No entra porque no es flujo o porque es flujo de endoso");
			}
			
			Utils.validate(smap1, "No se recibieron datos");
			
			String endoso = "MARCO_ENDOSOS_GENERAL".equals(smap1.get("pantallaOrigen")) ? "S" : "N";
			
			smap1.put("endoso",endoso);
			
			if(endoso.equals("S"))
			{
				smap1.put("cdramo"   , smap1.get("CDRAMO"));
				smap1.put("tipoflot" , smap1.get("TIPOFLOT"));
				smap1.put("cdtipsit" , "AR");
			}
			
			String cdunieco = smap1.get("cdunieco")
			       ,cdramo  = smap1.get("cdramo")
			       ,cdtipsit = smap1.get("cdtipsit")
			       ,ntramite = smap1.get("ntramite")
			       ,tipoflot = smap1.get("tipoflot"); 
			
			smap1.put("cdsisrol" , cdsisrol);
			smap1.put("cdusuari" , cdusuari);
			
			Utils.validate(
					cdramo    , "No se recibi\u00f3 el producto"
					,cdtipsit , "No se recibi\u00f3 la modalidad"
					,tipoflot , "No se recibi\u00f3 el tipo de cotizacion"
					);
			
			ManagerRespuestaImapSmapVO resp=cotizacionAutoManager.cotizacionAutoFlotilla(
					cdusuari
					,cdsisrol
					,cdunieco
					,cdramo
					,cdtipsit
					,ntramite
					,tipoflot
					,"S".equals(endoso)
					,flujo
					,renovacion
					);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
			
			smap1.putAll(resp.getSmap());
			imap=resp.getImap();
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### result=", result
				,"\n###### cotizacionAutoFlotilla ######"
				,"\n####################################"
				));
		return result;
	}
	
	public String cotizarAutosFlotilla()
	{
		logger.debug(Utils.log(""
				,"\n##################################"
				,"\n###### cotizarAutosFlotilla ######"
				,"\n###### smap1="  , smap1
				,"\n###### slist1=" , slist1
				,"\n###### slist2=" , slist2
				,"\n###### slist3=" , slist3
				));
		
		try
		{
			logger.debug(Utils.log("","Validando datos de entrada"));
			
			
			UserVO usuario  = Utils.validateSession(session);
			
			String cdusuari  = usuario.getUser()
			       ,cdsisrol = usuario.getRolActivo().getClave()
			       ,cdelemen = usuario.getEmpresa().getElementoId();
			
			Utils.validate(smap1, "No se recibieron datos de poliza");
			
			String cdunieco     = smap1.get("cdunieco")
			       ,cdramo      = smap1.get("cdramo")
			       ,cdtipsit    = smap1.get("cdtipsit")
			       ,estado      = smap1.get("estado")
			       ,nmpoliza    = smap1.get("nmpoliza")
			       ,feini       = smap1.get("feini")
			       ,fefin       = smap1.get("fefin")
			       ,cdagente    = smap1.get("cdagente")
			       ,cdpersonCli = smap1.get("cdpersonCli")
			       ,nmorddomCli = smap1.get("nmorddomCli")
			       ,cdideperCli = smap1.get("cdideperCli")
			       ,tipoflot    = smap1.get("tipoflot");
			
			Utils.validate(
					cdunieco  , "No se recibi\u00f3 la sucursal"
					,cdramo   , "No se recibi\u00f3 el producto"
					,cdtipsit , "No se recibi\u00f3 la modalidad"
					,estado   , "No se recibi\u00f3 el estado"
					,feini    , "No se recibi\u00f3 el inicio de vigencia"
					,fefin    , "No se recibi\u00f3 el fin de vigencia"
					,cdagente , "No se recibi\u00f3 el agente"
					,tipoflot , "No se recibi\u00f3 el tipo de cotizacion"
					);
			
			Utils.validate(slist1, "No se recibieron las situaciones mixtas");
			Utils.validate(slist2, "No se recibieron las situaciones base");
			Utils.validate(slist3, "No se recibieron las configuraciones de plan");
			
			boolean noTarificar = StringUtils.isNotBlank(smap1.get("notarificar"))&&smap1.get("notarificar").equals("si");
			
			Map<String,String>tvalopol=new HashMap<String,String>();
			for(Entry<String,String>en:smap1.entrySet())
			{
				String key=en.getKey();
				if(key.length()>"tvalopol_".length()
						&&key.substring(0,"tvalopol_".length()).equals("tvalopol_")
						)
				{
					tvalopol.put(Utils.join("otvalor",StringUtils.leftPad(key.substring("tvalopol_".length()),2,"0")),en.getValue());
				}
			}
			
			ManagerRespuestaSlistSmapVO resp=cotizacionAutoManager.cotizarAutosFlotilla(
					cdusuari
					,cdsisrol
					,cdelemen
					,cdunieco
					,cdramo
					,cdtipsit
					,estado
					,nmpoliza
					,feini
					,fefin
					,cdagente
					,cdpersonCli
					,nmorddomCli
					,cdideperCli
					,slist1
					,slist2
					,slist3
					,noTarificar
					,tipoflot
					,tvalopol
					,usuario
					);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
			
			smap1.putAll(resp.getSmap());
			slist1 = resp.getSlist();
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### exito="     , exito
				,"\n###### respuesta=" , respuesta
				,"\n###### cotizarAutosFlotilla ######"
				,"\n##################################"
				));
		return SUCCESS;
	}
	
	public String cargarValidacionTractocamionRamo5()
	{
		logger.debug(Utils.log(""
				,"\n###############################################"
				,"\n###### cargarValidacionTractocamionRamo5 ######"
				,"\n###### smap1=", smap1
				));
		
		try
		{
			logger.debug(Utils.log("","Validando datos de entrada"));
			
			Utils.validate(smap1,"No se recibieron datos para el web service");
			
			String poliza = smap1.get("poliza");
			String rfc    = smap1.get("rfc");
			
			Utils.validate(poliza,"No se recibi\u00f3 la poliza tractocamion para el webservice");
			
			ManagerRespuestaVoidVO resp = cotizacionAutoManager.cargarValidacionTractocamionRamo5(poliza,rfc);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### cargarValidacionTractocamionRamo5 ######"
				,"\n###############################################"
				));
		return SUCCESS;
	}
	
	public String procesarCargaMasivaFlotilla()
	{
		logger.debug(Utils.log(""
				,"\n#########################################"
				,"\n###### procesarCargaMasivaFlotilla ######"
				,"\n###### smap1="            , smap1
				,"\n###### excel="            , excel
				,"\n###### excelFileName="    , excelFileName
				,"\n###### excelContentType=" , excelContentType
				));
		
		try
		{
			logger.debug(Utils.log("","Validando datos de entrada"));
			
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdramo    = smap1.get("cdramo")
			       ,cdtipsit = smap1.get("cdtipsit")
			       ,respetar = smap1.get("tomarMasiva")
			       ,tipoflot = smap1.get("tipoflot")
			       ,cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave()
			       ,negocio  = smap1.get("negocio");
			
			logger.debug(Utils.join(
					"\n VILS tipoflot="  , tipoflot
					,"\n VILS cdsisrol=" , cdsisrol
					,"\n VILS negociol=" , negocio
					));

			Utils.validate(
					cdramo    , "No se recibi\u00f3 el producto"
					,cdtipsit , "No se recibi\u00f3 la modalidad"
					,negocio, "No se recibio negocio"
					);
			
			Utils.validate(excel, "No se recibi\u00f3 el archivo");
			
			ManagerRespuestaSlistVO resp = cotizacionAutoManager.procesarCargaMasivaFlotilla(cdramo,cdtipsit,respetar,excel);//,tipoflot
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}

			//Pone vacio en los valores desc/rec de la lista
			resp.setSlist(cotizacionAutoManager.validaVacioDescRecg(resp.getSlist()));
			
			//Elimina incisos que no correspondan al negocio seleccionado
			resp.setSlist(cotizacionAutoManager.validaExcelCdtipsitXNegocio(tipoflot,negocio,resp.getSlist()));
			
			int lugarMensaje = resp.getSlist().size();
			Map<String, String> msn = resp.getSlist().get(lugarMensaje-1);
			
			if(msn.get("removidos") != null) 
			{
			respuestaOculta =msn.get("removidos");
			resp.getSlist().remove(lugarMensaje-1);
			}
			
			if(resp.getSlist().isEmpty())
			{	
				respuestaOculta="No se agregar�n los incisos por no corresponder al negocio seleccionado.";
				return SUCCESS;
			}
			
			//Para modificar solo PYMES ignorando el valor de vehiculo y haciendo consulta
			if(tipoflot.equals("P"))
			{
				String cdpost = smap1.get("codpos");
				String cambio = smap1.get("cambio");
				logger.debug(cambio);
				resp.setSlist( cotizacionAutoManager.modificadorValorVehPYME(resp.getSlist(),cdsisrol, cdpost, cambio));
			}
			
			slist1 = resp.getSlist();
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### exito="  , exito
				,"\n###### slist1=" , slist1
				,"\n###### procesarCargaMasivaFlotilla ######"
				,"\n#########################################"
				));
		return SUCCESS;
	}
	
	public String cargarCotizacionAutoFlotilla()
	{
		logger.debug(Utils.log(""
				,"\n##########################################"
				,"\n###### cargarCotizacionAutoFlotilla ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			logger.debug(Utils.log("","Validando datos de entrada"));
			
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdramo      = smap1.get("cdramo")
			       ,nmpoliza   = smap1.get("nmpoliza")
			       ,ntramiteIn = smap1.get("ntramiteIn");
			
			Utils.validate(
					cdramo    , "No se recibi\u00f3 el producto"
					,nmpoliza , "No se recibi\u00f3 el numero de poliza");
			
			UserVO usuario = Utils.validateSession(session);
			
			String cdusuari  = usuario.getUser()
			       ,cdsisrol = usuario.getRolActivo().getClave();
			
			ManagerRespuestaSlist2SmapVO resp = cotizacionAutoManager.cargarCotizacionAutoFlotilla(cdramo,nmpoliza,cdusuari,cdsisrol,ntramiteIn);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
			
			smap1.putAll(resp.getSmap());
			slist1 = resp.getSlist1();
			slist2 = resp.getSlist2();
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### exito="     , exito
				,"\n###### respuesta=" , respuesta
				,"\n###### cargarCotizacionAutoFlotilla ######"
				,"\n##########################################"
				));
		return SUCCESS;
	}
	
	public String emisionAutoFlotilla()
	{
		logger.debug(Utils.log(""
				,"\n#################################"
				,"\n###### emisionAutoFlotilla ######"
				,"\n###### smap1=", smap1
				,"\n###### flujo=", flujo
				));
		
		String result = ERROR;
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			String cdusuari = usuario.getUser()
			       ,cdsisrol = usuario.getRolActivo().getClave();
			
			if(flujo!=null  && smap1==null) //cuando viene desde la mesa de control
			{
				logger.debug(Utils.log("", "se creara el mapa porque viene de flujo"));
				
				smap1 = new HashMap<String,String>();
				
				smap1.put("cdunieco" , flujo.getCdunieco());
				smap1.put("cdramo"   , flujo.getCdramo());
				smap1.put("cdtipsit" , "AR");
				smap1.put("estado"   , flujo.getEstado());
				
				logger.debug(Utils.log("", "recuperando tramite"));
				
				Map<String,Object> datosFlujo = flujoMesaControlManager.recuperarDatosTramiteValidacionCliente(flujo);
				
				Map<String,String> tramite = (Map<String,String>)datosFlujo.get("TRAMITE");
				logger.debug(Utils.log("", "tramite=", tramite));
				
				smap1.put("nmpoliza" , tramite.get("NMSOLICI"));
				
				smap1.put("ntramite" , flujo.getNtramite());
				
				smap1.put("tipoflot" , flujo.getAux());
				
				smap1.put("swexiper" , cotizacionManager.recuperarOtvalorTramitePorDsatribu(flujo.getNtramite(), "SWEXIPER"));
				
				logger.debug(Utils.log("", "el mapa creado desde flujo es=", smap1));
				
			}
			
			//si viene de la pantalla de cotizacion hay que recuperar los atributos de la pantalla actual
			if(flujo!=null)
			{
				if("RECUPERAR".equals(flujo.getAux()))
				{
					//se recibieron 3 propiedades de una pantalla anterior, hay que actualizarlas
					logger.debug("flujo antes de actualizar sus 3 propiedades de pantalla nueva={}",flujo);
					flujoMesaControlManager.recuperarPropiedadesDePantallaComponenteActualPorConexionSinPermisos(flujo);
					
					logger.debug("flujo despues de actualizar sus 3 propiedades de pantalla nueva={}",flujo);
				}
			}
			
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdunieco = smap1.get("cdunieco")
			       ,cdramo   = smap1.get("cdramo")
			       ,cdtipsit = smap1.get("cdtipsit")
			       ,estado   = smap1.get("estado")
			       ,nmpoliza = smap1.get("nmpoliza")
			       ,ntramite = smap1.get("ntramite");
			
			Utils.validate(
					cdunieco  , "No se recibi\u00f3 la sucursal"
					,cdramo   , "No se recibi\u00f3 el ramo"
					,cdtipsit , "No se recibi\u00f3 la modalidad"
					,estado   , "No se recibi\u00f3 el estado de la poliza"
					,nmpoliza , "No se recibi\u00f3 el numero de poliza"
					,ntramite , "No se recibi\u00f3 el numero de tramite"
					);
			
			ManagerRespuestaImapSmapVO resp = cotizacionAutoManager.emisionAutoFlotilla(
					cdunieco
					,cdramo
					,cdtipsit
					,estado
					,nmpoliza
					,ntramite
					,cdusuari
					,cdsisrol
					);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
			
			smap1.putAll(resp.getSmap());
			smap1.put("cdsisrol" , cdsisrol);
			imap = resp.getImap();
				
			HashMap<String,String> params = new HashMap<String, String>();
			params.put("cdunieco" , cdunieco);
			params.put("cdramo"   , cdramo);
			params.put("estado"   , estado);
			params.put("nmpoliza" , nmpoliza);
			params.put("nmsuplem" , "0");
			
			Map<String,String> fechas = consultasManager.consultaFeNacContratanteAuto(params);
			
			if(fechas != null && !fechas.isEmpty()){
				smap1.put("AplicaCobVida" , fechas.get("APLICA"));
				smap1.put("FechaMinEdad"  , fechas.get("FECHAMIN"));
				smap1.put("FechaMaxEdad"  , fechas.get("FECHAMAX"));
			}
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### result=", result
				,"\n###### emisionAutoFlotilla ######"
				,"\n#################################"
				));
		return result;
	}
	
	public String guardarComplementariosAutoFlotilla()
	{
		logger.debug(Utils.log(""
				,"\n################################################"
				,"\n###### guardarComplementariosAutoFlotilla ######"
				,"\n###### smap1="  , smap1
				,"\n###### slist1=" , slist1
				));
		
		try
		{
			logger.debug(Utils.log("","Validando datos de entrada"));
			
			Utils.validate(smap1  , "No se recibieron datos de poliza");
			
			Utils.validate(slist1 , "No se recibieron los incisos");
			
			String cdunieco     = smap1.get("cdunieco")
			       ,cdramo      = smap1.get("cdramo")
			       ,estado      = smap1.get("estado")
			       ,nmpoliza    = smap1.get("nmpoliza")
			       ,agenteSec   = smap1.get("agente_sec")
			       ,porpartiSec = smap1.get("porparti")
			       ,feini       = smap1.get("feini")
			       ,fefin       = smap1.get("fefin")
			       ,ntramite    = smap1.get("ntramite");
			
			Utils.validate(
					cdunieco  , "No se recibi\u00f3 la sucursal"
					,cdramo   , "No se recibi\u00f3 el producto"
					,estado   , "No se recibi\u00f3 el estado de la poliza"
					,nmpoliza , "No se recibi\u00f3 el numero de poliza"
					,feini    , "No se recibi\u00f3 la fecha de inicio"
					,fefin    , "No se recibi\u00f3 la fecha de fin"
					,ntramite , "No se recibi\u00f3 el numero de tramite"
					);
			
			ManagerRespuestaVoidVO resp = cotizacionAutoManager.guardarComplementariosAutoFlotilla(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,agenteSec
					,porpartiSec
					,feini
					,fefin
					,smap1
					,slist1
					,ntramite
					);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
			
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### exito="     , exito
				,"\n###### respuesta=" , respuesta
				,"\n###### guardarComplementariosAutoFlotilla ######"
				,"\n################################################"
				));
		return SUCCESS;
	}
	
	public String recotizarAutoFlotilla()
	{
		logger.debug(Utils.log(""
				,"\n###################################"
				,"\n###### recotizarAutoFlotilla ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			logger.debug(Utils.log("","Validando datos de entrada"));
			
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdunieco    = smap1.get("cdunieco")
			       ,cdramo     = smap1.get("cdramo")
			       ,cdtipsit   = smap1.get("cdtipsit")
			       ,estado     = smap1.get("estado")
			       ,nmpoliza   = smap1.get("nmpoliza")
			       ,notarifica = smap1.get("notarifica")
			       ,cdperpag   = smap1.get("cdperpag");
			
			Utils.validate(
					cdunieco  , "No se recibi\u00f3 la sucursal"
					,cdramo   , "No se recibi\u00f3 el producto"
					,estado   , "No se recibi\u00f3 el estado"
					,nmpoliza , "No se recibi\u00f3 el numero de poliza"
					,cdtipsit , "No se recibi\u00f3 la modalidad"
					,cdperpag , "No se recibi\u00f3 la forma de pago"
					);
			
			
			UserVO usuario = Utils.validateSession(session);
			
			String cdusuari  = usuario.getUser()
			       ,cdelemen = usuario.getEmpresa().getElementoId();
			
			ManagerRespuestaSlistVO resp = cotizacionAutoManager.recotizarAutoFlotilla(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,"0"
					,StringUtils.isNotBlank(notarifica)&&notarifica.equalsIgnoreCase("si")
					,cdusuari
					,cdelemen
					,cdtipsit
					,cdperpag
					);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
			
			slist1 = resp.getSlist();
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### exito="  , exito
				,"\n###### slist1=" , slist1
				,"\n###### recotizarAutoFlotilla ######"
				,"\n###################################"
				));
		return SUCCESS;
	}
	
	public String cargarObligatorioTractocamionRamo5()
	{
		logger.debug(Utils.log(""
				,"\n################################################"
				,"\n###### cargarObligatorioTractocamionRamo5 ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			logger.debug(Utils.log("","Validando datos de entrada"));
			
			Utils.validate(smap1, "No se recibieron datos");
			
			String clave = smap1.get("clave");
			Utils.validate(clave, "No se recibi\u00f3 la clave del vehiculo");
			
			ManagerRespuestaSmapVO resp = cotizacionAutoManager.cargarObligatorioTractocamionRamo5(clave);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
			
			smap1.putAll(resp.getSmap());
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### cargarObligatorioTractocamionRamo5 ######"
				,"\n################################################"
				));
		return SUCCESS;
	}
	
	public String cargarDetalleNegocioRamo5()
	{
		logger.debug(Utils.log(""
				,"\n#######################################"
				,"\n###### cargarDetalleNegocioRamo5 ######"
				,"\n###### smap1=", smap1
				));
		
		try
		{
			logger.debug(Utils.log("","Validando datos de entrada"));
			
			Utils.validate(smap1, "No se recibieron datos para cargar detalle de negocio");
			
			UserVO usuario = Utils.validateSession(session);
			
			String negocio   = smap1.get("negocio")
			       ,cdramo   = smap1.get("cdramo")
			       ,cdtipsit = smap1.get("cdtipsit")
			       ,cdusuari = usuario.getUser()
			       ,cdsisrol = usuario.getRolActivo().getClave();
			
			Utils.validate(negocio, "No se recibi\u00f3 clave de negocio");
			
			ManagerRespuestaSmapVO resp = cotizacionAutoManager.cargarDetalleNegocioRamo5(negocio, cdramo, cdtipsit, cdsisrol, cdusuari);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta); 
			}
			
			smap1.putAll(resp.getSmap());
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### cargarDetalleNegocioRamo5 ######"
				,"\n#######################################"
				));
		return SUCCESS;
	}
	
	public String guardarPantallaBeneficiarios()
	{
		logger.debug(Utils.log(""
				,"\n##########################################"
				,"\n###### guardarPantallaBeneficiarios ######"
				,"\n###### smap1="  , smap1
				,"\n###### slist1=" , slist1
				));

		try
		{
			logger.debug(Utils.log("","Validando datos de entrada"));
			
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdunieco  = smap1.get("cdunieco")
			       ,cdramo   = smap1.get("cdramo")
			       ,estado   = smap1.get("estado")
			       ,nmpoliza = smap1.get("nmpoliza")
			       ,nmsuplem = smap1.get("nmsuplem")
			       ,nmsituac = smap1.get("nmsituac");
			
			Utils.validate(
					cdunieco  , "No se recibi\u00f3 la sucursal"
					,cdramo   , "No se recibi\u00f3 el producto"
					,estado   , "No se recibi\u00f3 el estado de la poliza"
					,nmpoliza , "No se recibi\u00f3 el numero de poliza"
					,nmsuplem , "No se recibi\u00f3 el numero de suplemento"
					,nmsituac , "No se recibi\u00f3 el numero de situacion"
					);
			
			UserVO usuarioSesion = Utils.validateSession(session);
			
			ManagerRespuestaVoidVO resp=cotizacionAutoManager.guardarPantallaBeneficiarios(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplem
					,nmsituac
					,slist1
					,usuarioSesion);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
			
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### exito="     , exito
				,"\n###### respuesta=" , respuesta
				,"\n###### guardarPantallaBeneficiarios ######"
				,"\n##########################################"
				));
		return SUCCESS;
	}
	
	public String cargarParamerizacionConfiguracionCoberturasRol()
	{
		logger.debug(Utils.log(""
				,"\n############################################################"
				,"\n###### cargarParamerizacionConfiguracionCoberturasRol ######"
				,"\n###### smap1=", smap1
				));
		
		try
		{
			logger.debug(Utils.log("","Validando datos de entrada"));
			
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdtipsit  = smap1.get("cdtipsit")
			       ,cdsisrol = smap1.get("cdsisrol");
			
			Utils.validate(
					cdtipsit  , "No se recibi\u00f3 la modalidad"
					,cdsisrol , "No se recibi\u00f3 el rol"
					);
			
			ManagerRespuestaSlistVO resp = cotizacionAutoManager.cargarParamerizacionConfiguracionCoberturasRol(cdtipsit,cdsisrol);
			
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
			
			slist1 = resp.getSlist();
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### exito="  , exito
				,"\n###### slist1=" , slist1
				,"\n###### cargarParamerizacionConfiguracionCoberturasRol ######"
				,"\n############################################################"
				));
		return SUCCESS;
	}
	
	public String obtieneValNumeroSerie()
	{
		logger.debug(Utils.log(""
				,"\n###################################"
				,"\n###### obtieneValNumeroSerie ######"
				,"\n###### smap1=", smap1
				));
		
		String paso = null;
		
		try
		{
			paso = "Validando datos de entrada";
			logger.debug(Utils.log("","paso=",paso));
			
			Utils.validate(smap1, "No se recibieron datos de poliza");
			
			String feini     = smap1.get("feini")
			       ,numSerie = smap1.get("numSerie");
			
			Utils.validate(
					feini     , "No se recibi\u00f3 la fecha inicial"
					,numSerie , "No se recibi\u00f3 el numero de serie"
					);
			
			String iCodAviso = null;
			String sSubCadena = null;
			
			
			String feAutorizacion = Utils.join(feini.substring(8,10),"/",feini.substring(5,7),"/",feini.substring(0,4));
			
			String[] value_split = numSerie.split("\\|");
			
		    for (String serie : value_split) {
		    	int contador = 0;
		        logger.debug("Serie==>> "+serie);
		        iCodAviso="";
		        iCodAviso +=cotizacionAutoManager.obtieneValidacionRetroactividad(serie, renderFechas.parse(feAutorizacion));
		        contador++;
		        
		        
		        	sSubCadena = iCodAviso.substring(0,60);
		        	System.out.println(sSubCadena);
		        	logger.debug(sSubCadena);
		        
		        logger.debug(String.valueOf(iCodAviso.length()));
		        sSubCadena += "".equals(iCodAviso)?"": "INCISO: "+contador+" VERIFIQUE SI PUEDE REALIZAR EL ENDOSO O BIEN SI NECESITA REEXPEDIR LA POLIZA INGRESE TRAMITE POR MESA DE CONTROL.";
		        
		        
		        numSerie="";

		    }
			//iCodAviso = cotizacionAutoManager.obtieneValidacionRetroactividad(numSerie, renderFechas.parse(feAutorizacion));
			
			exito           = StringUtils.isNotBlank(sSubCadena) ? false : true;
			respuesta       = sSubCadena;
			respuestaOculta = iCodAviso;
			
			logger.debug("respuesta--->"+respuesta+" : "+iCodAviso.length());
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(""
				,"\n###### obtieneValNumeroSerie ######"
				,"\n###################################"
				));
		return SUCCESS;
	}

	/*
	 * Getters y setters
	 */
	public void setCotizacionAutoManager(CotizacionAutoManager cotizacionAutoManager) {
		this.cotizacionAutoManager = cotizacionAutoManager;
	}
	
	public boolean isSuccess(){
		return true;
	}
	
	public Map<String, String> getSmap1() {
		return smap1;
	}

	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
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

	public Map<String, Item> getImap() {
		return imap;
	}

	public void setImap(Map<String, Item> imap) {
		this.imap = imap;
	}

	public Map<String, String> getSmap2() {
		return smap2;
	}

	public void setSmap2(Map<String, String> smap2) {
		this.smap2 = smap2;
	}

	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}

	public List<Map<String, String>> getSlist2() {
		return slist2;
	}

	public void setSlist2(List<Map<String, String>> slist2) {
		this.slist2 = slist2;
	}

	public List<Map<String, String>> getSlist3() {
		return slist3;
	}

	public void setSlist3(List<Map<String, String>> slist3) {
		this.slist3 = slist3;
	}

	public File getExcel() {
		return excel;
	}

	public void setExcel(File excel) {
		this.excel = excel;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public String getExcelContentType() {
		return excelContentType;
	}

	public void setExcelContentType(String excelContentType) {
		this.excelContentType = excelContentType;
	}

	public FlujoVO getFlujo() {
		return flujo;
	}

	public void setFlujo(FlujoVO flujo) {
		this.flujo = flujo;
	}
}