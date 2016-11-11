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
import mx.com.gseguros.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

public class CotizacionAutoAction extends PrincipalCoreAction
{
	private static final long   serialVersionUID = -5890606583100529056L;
	private static final Logger logger           = Logger.getLogger(CotizacionAutoAction.class);
	
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

	/**
	 * Constructor que se asegura de que el action tenga sesion
	 */
	public CotizacionAutoAction()
	{
		logger.debug("new CotizacionAutoAction");
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
	
	public String cotizacionAutoIndividual()
	{
		long stamp = System.currentTimeMillis();
		logger.info(Utils.log(
				 "\n######################################"
				,"\n###### cotizacionAutoIndividual ######"
				,"\n###### stamp=" , stamp
				,"\n###### smap1=" , smap1
				,"\n###### flujo=" , flujo
				));
		
		String result = ERROR;
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			if(flujo!=null)
			{
				logger.debug(Utils.log(stamp, "se va a crear el smap1 porque se entra desde flujo=", smap1));
				
				smap1 = new HashMap<String,String>();
				smap1.put("ntramite" , flujo.getNtramite());
				smap1.put("cdunieco" , flujo.getCdunieco());
				smap1.put("cdramo"   , flujo.getCdramo());
				
				logger.debug(Utils.log(stamp, "recuperando tramite"));
				
				StringBuilder sb = new StringBuilder();
				Map<String,Object> datosFlujo = flujoMesaControlManager.recuperarDatosTramiteValidacionCliente(sb, flujo);
				logger.debug(sb.toString());
				
				Map<String,String> tramite = (Map<String,String>)datosFlujo.get("TRAMITE");
				logger.debug(Utils.log(stamp, "tramite=", tramite));
				
				smap1.put("cdtipsit" , tramite.get("CDTIPSIT"));
				
				logger.debug(Utils.log(stamp, "smap1 creado=", smap1));
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
					cdramo    , "No se recibió el producto"
					,cdtipsit , "No se recibió la modalidad"
					);
			
			ManagerRespuestaImapSmapVO resp = cotizacionAutoManager.cotizacionAutoIndividual(
					ntramite
					,cdunieco
					,cdramo
					,cdtipsit
					,cdusuari
					,cdsisrol
					);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			
			if(!exito)
			{
				throw new ApplicationException(respuesta);
			}
			
			smap1.putAll(resp.getSmap());
			imap = resp.getImap();
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.log(
				 "\n###### stamp="  , stamp
				,"\n###### result=" , result
				,"\n###### cotizacionAutoIndividual ######"
				,"\n######################################"
				));
		return result;
	}
	
	public String cargarRetroactividadSuplemento()
	{
		logger.info(
				new StringBuilder()
				.append("\n############################################")
				.append("\n###### cargarRetroactividadSuplemento ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		exito = true;
		
		setCheckpoint("Validando datos de entrada");
		try
		{
			checkNull(smap1, "No se recibieron datos");
			
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String cdtipsup = smap1.get("cdtipsup");
			String cdusuari = smap1.get("cdusuari");
			String cdtipsit = smap1.get("cdtipsit");
			
			checkBlank(cdunieco , "No se recibió la sucursal");
			checkBlank(cdramo   , "No se recibió el producto");
			checkBlank(cdtipsup , "No se recibió el tipo de suplemento");
			checkBlank(cdusuari , "No se recibió el nombre de usuario");
			checkBlank(cdtipsit , "No se recibió la modalidad");
			
			ManagerRespuestaSmapVO resp=cotizacionAutoManager.cargarRetroactividadSuplemento(cdunieco,cdramo,cdtipsup,cdusuari,cdtipsit);
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
		
		logger.info(
				new StringBuilder()
				.append("\n###### cargarRetroactividadSuplemento ######")
				.append("\n############################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String cargarSumaAseguradaRamo5()
	{
		logger.info(
				new StringBuilder()
				.append("\n######################################")
				.append("\n###### cargarSumaAseguradaRamo5 ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		setCheckpoint("Validando datos de entrada");
		try
		{
			checkNull(smap1, "No se recibieron datos");
			
			String cdtipsit = smap1.get("cdtipsit");
			String clave    = smap1.get("clave");
			String modelo   = smap1.get("modelo");
			String cdsisrol = smap1.get("cdsisrol");
			
			checkBlank(cdtipsit , "No se recibio la modalidad");
			checkBlank(clave    , "No se recibio la clave del auto");
			checkBlank(modelo   , "No se recibio el modelo del auto");
			checkBlank(cdsisrol , "No se recibio el rol del usuario");
			
			ManagerRespuestaSmapVO resp = cotizacionAutoManager.cargarSumaAseguradaRamo5(cdtipsit,clave,modelo,cdsisrol);
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
		
		logger.info(
				new StringBuilder()
				.append("\n###### cargarSumaAseguradaRamo5 ######")
				.append("\n######################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String emisionAutoIndividual()
	{
		long stamp = System.currentTimeMillis();
		logger.info(Utils.log(stamp
				,"\n###################################"
				,"\n###### emisionAutoIndividual ######"
				,"\n###### flujo=" , flujo
				,"\n###### smap1=" , smap1
				));
		
		String result = ERROR;
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			if(flujo!=null)
			{
				smap1 = new HashMap<String,String>();
				smap1.put("cdunieco" , flujo.getCdunieco());
				smap1.put("cdramo"   , flujo.getCdramo());
				smap1.put("estado"   , flujo.getEstado());
				
				logger.debug(Utils.log(stamp, "recuperando tramite"));
				
				StringBuilder sb = new StringBuilder();
				Map<String,Object> datosFlujo = flujoMesaControlManager.recuperarDatosTramiteValidacionCliente(sb, flujo);
				logger.debug(sb.toString());
				
				Map<String,String> tramite = (Map<String,String>)datosFlujo.get("TRAMITE");
				logger.debug(Utils.log(stamp, "tramite=", tramite));
				
				smap1.put("cdtipsit" , tramite.get("CDTIPSIT"));
				
				smap1.put("nmpoliza" , tramite.get("NMSOLICI"));
				
				smap1.put("ntramite" , flujo.getNtramite());
				
				logger.debug(Utils.log(stamp, "smap1 creado=", smap1));
			}
			
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdunieco = smap1.get("cdunieco")
			       ,cdramo   = smap1.get("cdramo")
			       ,cdtipsit = smap1.get("cdtipsit")
			       ,estado   = smap1.get("estado")
			       ,nmpoliza = smap1.get("nmpoliza")
			       ,ntramite = smap1.get("ntramite");
			
			Utils.validate(
					cdunieco , "No se recibio la sucursal"
					,cdramo   , "No se recibio el ramo"
					,cdtipsit , "No se recibio la modalidad"
					,estado   , "No se recibio el estado de la poliza"
					,nmpoliza , "No se recibio el numero de poliza"
					,ntramite , "No se recibio el numero de tramite"
					);
			
			String cdusuari  = usuario.getUser()
			       ,cdsisrol = usuario.getRolActivo().getClave();
			
			ManagerRespuestaImapSmapVO resp=cotizacionAutoManager.emisionAutoIndividual(cdunieco,cdramo,cdtipsit,estado,nmpoliza,ntramite,cdusuari);
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
		
		logger.info(Utils.log(stamp
				,"\n###### result=", result
				,"\n###### emisionAutoIndividual ######"
				,"\n###################################"
				));
		return result;
	}
	
	public String cargarDatosComplementariosAutoInd()
	{
		logger.info(
				new StringBuilder()
				.append("\n###############################################")
				.append("\n###### cargarDatosComplementariosAutoInd ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		exito = true;
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos");
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String estado   = smap1.get("estado");
			String nmpoliza = smap1.get("nmpoliza");
			checkBlank(cdunieco , "No se recibio la sucursal");
			checkBlank(cdramo   , "No se recibio el producto");
			checkBlank(estado   , "No se recibio el estado");
			checkBlank(nmpoliza , "No se recibio la poliza");
			
			ManagerRespuestaSmapVO resp = cotizacionAutoManager.cargarDatosComplementariosAutoInd(cdunieco,cdramo,estado,nmpoliza);
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
		
		logger.info(
				new StringBuilder()
				.append("\n###### cargarDatosComplementariosAutoInd ######")
				.append("\n###############################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String cargarValoresSituacion()
	{
		logger.info(
				new StringBuilder()
				.append("\n####################################")
				.append("\n###### cargarValoresSituacion ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		exito = true;
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos");
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String estado   = smap1.get("estado");
			String nmpoliza = smap1.get("nmpoliza");
			String nmsituac = smap1.get("nmsituac");
			checkBlank(cdunieco, "No se recibio la sucursal");
			checkBlank(cdramo   , "No se recibio el producto");
			checkBlank(estado   , "No se recibio el estado");
			checkBlank(nmpoliza , "No se recibio la poliza");
			checkBlank(nmsituac , "No se recibio el numero de situacion");
			
			ManagerRespuestaSmapVO resp = cotizacionAutoManager.cargarValoresSituacion(cdunieco,cdramo,estado,nmpoliza,nmsituac);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			
			if(exito)
			{
				smap1=resp.getSmap();
			}
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### cargarValoresSituacion ######")
				.append("\n####################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String movimientoMpoliper()
	{
		logger.info(
				new StringBuilder()
				.append("\n################################")
				.append("\n###### movimientoMpoliper ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		exito = true;
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos de entrada");
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String estado   = smap1.get("estado");
			String nmpoliza = smap1.get("nmpoliza");
			String nmsituac = smap1.get("nmsituac");
			String cdrol    = smap1.get("cdrol");
			String cdperson = smap1.get("cdperson");
			String nmsuplem = smap1.get("nmsuplem");
			String status   = smap1.get("status");
			String nmorddom = smap1.get("nmorddom");
			String swreclam = smap1.get("swreclam");
			String accion   = smap1.get("accion");
			String swexiper = smap1.get("swexiper");
			
			checkBlank(cdunieco , "No se recibio la sucursal");
			checkBlank(cdramo   , "No se recibio el producto");
			checkBlank(estado   , "No se recibio el estado");
			checkBlank(nmpoliza , "No se recibio el numero de poliza");
			checkBlank(nmsituac , "No se recibio el numero de situacion");
			checkBlank(cdrol    , "No se recibio el rol");
			checkBlank(cdperson , "No se recibio la clave de persona");
			checkBlank(nmsuplem , "No se recibio el suplemento");
			checkBlank(status   , "No se recibio el status");
			checkBlank(nmorddom , "No se recibio el numero ordinal");
			checkBlank(accion   , "No se recibio la accion");
			checkBlank(swexiper , "No se recibio el estado de persona existente");
			
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
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### movimientoMpoliper ######")
				.append("\n################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String guardarComplementariosAutoIndividual()
	{
		logger.info(
				new StringBuilder()
				.append("\n##################################################")
				.append("\n###### guardarComplementariosAutoIndividual ######")
				.append("\n###### smap1=").append(smap1)
				.append("\n###### smap2=").append(smap2)
				.toString()
				);
		
		exito = true;
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos de poliza");
			checkNull(smap2, "No se recibieron datos adicionales de poliza");
			String cdunieco    = smap1.get("cdunieco");
			String cdramo      = smap1.get("cdramo");
			String estado      = smap1.get("estado");
			String nmpoliza    = smap1.get("nmpoliza");
			String agenteSec   = smap1.get("agente_sec");
			String porpartiSec = smap1.get("porparti");
			String feini       = smap1.get("feini");
			String fefin       = smap1.get("fefin");
			String ntramite    = smap1.get("ntramite");
			String cdagente    = smap1.get("cdagente");
			checkBlank(cdunieco , "No se recibio la sucursal");
			checkBlank(cdramo   , "No se recibio el producto");
			checkBlank(estado   , "No se recibio el estado de la poliza");
			checkBlank(nmpoliza , "No se recibio el numero de poliza");
			checkBlank(feini    , "No se recibio la fecha de inicio");
			checkBlank(fefin    , "No se recibio la fecha de fin");
			checkBlank(ntramite , "No se recibio el numero de tramite");
			checkBlank(cdagente , "No se recibio el agente");
			
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
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### guardarComplementariosAutoIndividual ######")
				.append("\n##################################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String guardarConfigCotizacion()
	{
		logger.info(
				new StringBuilder()
				.append("\n#####################################")
				.append("\n###### guardarConfigCotizacion ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		exito = true;
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos");
			String cdramo   = smap1.get("cdramo");
			String cdtipsit = smap1.get("cdtipsit");
			String cdusuari = smap1.get("cdusuari");
			checkBlank(cdramo   , "No se recibio el producto");
			checkBlank(cdtipsit , "No se recibio la modalidad");
			checkBlank(cdusuari , "No se recibio el nombre de usuario");
			
			ManagerRespuestaVoidVO resp = cotizacionAutoManager.guardarConfiguracionCotizacion(cdramo,cdtipsit,cdusuari,smap1);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### guardarConfigCotizacion ######")
				.append("\n#####################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String cargarConfigCotizacion()
	{
		logger.info(
				new StringBuilder()
				.append("\n####################################")
				.append("\n###### cargarConfigCotizacion ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		exito = true;
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos de entrada");
			String cdramo   = smap1.get("cdramo");
			String cdtipsit = smap1.get("cdtipsit");
			String cdusuari = smap1.get("cdusuari");
			checkBlank(cdramo   , "No se recibio el producto");
			checkBlank(cdtipsit , "No se recibio la modalidad");
			checkBlank(cdusuari , "No se recibio el nombre de usuario");
			
			ManagerRespuestaSmapVO resp = cotizacionAutoManager.cargarConfiguracionCotizacion(cdramo,cdtipsit,cdusuari);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			if(exito)
			{
				smap1 = resp.getSmap();
			}
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### cargarConfigCotizacion ######")
				.append("\n####################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String cargarParamerizacionConfiguracionCoberturas()
	{
		logger.info(
				new StringBuilder()
				.append("\n#########################################################")
				.append("\n###### cargarParamerizacionConfiguracionCoberturas ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos");
			String cdtipsit     = smap1.get("cdtipsit");
			String cdsisrol     = smap1.get("cdsisrol");
			String negocio      = smap1.get("negocio");
			String tipoServicio = smap1.get("tipoServicio");
			String modelo       = smap1.get("modelo");
			String tipoPersona  = smap1.get("tipoPersona");
			String submarca     = smap1.get("submarca");
			String clavegs      = smap1.get("clavegs");
			
			checkBlank(cdtipsit     , "No se recibio la modalidad");
			checkBlank(cdsisrol     , "No se recibio el rol");
			checkBlank(negocio      , "No se recibio el negocio");
			checkBlank(tipoServicio , "No se recibio el tipo de servicio");
			checkBlank(modelo       , "No se recibio el modelo");
			checkBlank(tipoPersona  , "No se recibio el tipo de persona");
			checkBlank(submarca     , "No se recibio la submarca");
			checkBlank(clavegs      , "No se recibio la clave gs");
			
			ManagerRespuestaSlistVO resp = cotizacionAutoManager.cargarParamerizacionConfiguracionCoberturas(
					cdtipsit,cdsisrol,negocio,tipoServicio,modelo,tipoPersona,submarca,clavegs);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			if(exito)
			{
				slist1 = resp.getSlist();
			}
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### cargarParamerizacionConfiguracionCoberturas ######")
				.append("\n#########################################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String cotizacionAutoFlotilla()
	{
		long stamp = System.currentTimeMillis();
		logger.info(Utils.log(stamp
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
			
			if(flujo!=null)
			{
				logger.debug(Utils.log(stamp, "se creara el mapa porque viene de flujo"));
				
				smap1 = new HashMap<String,String>();
				
				smap1.put("cdunieco" , flujo.getCdunieco());
				smap1.put("cdramo"   , flujo.getCdramo());
				smap1.put("cdtipsit" , "AR");
				smap1.put("ntramite" , flujo.getNtramite());
				smap1.put("tipoflot" , flujo.getAux());
				
				logger.debug(Utils.log(stamp, "el mapa creado desde flujo es=", smap1));
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
					cdramo    , "No se recibio el producto"
					,cdtipsit , "No se recibio la modalidad"
					,tipoflot , "No se recibio el tipo de cotizacion"
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
		
		logger.info(Utils.log(stamp
				,"\n###### result=", result
				,"\n###### cotizacionAutoFlotilla ######"
				,"\n####################################"
				));
		return result;
	}
	
	public String cotizarAutosFlotilla()
	{
		logger.info(
				new StringBuilder()
				.append("\n##################################")
				.append("\n###### cotizarAutosFlotilla ######")
				.append("\n###### smap1=") .append(smap1)
				.append("\n###### slist1=").append(Utils.size(slist1))
				.append("\n###### slist2=").append(Utils.size(slist2))
				.append("\n###### slist3=").append(Utils.size(slist3))
				.toString()
				);
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			
			checkNull(session, "No hay sesion");
			checkNull(session.get("USUARIO"), "No hay usuario en la sesion");
			UserVO usuario  = (UserVO)session.get("USUARIO");
			String cdusuari = usuario.getUser();
			String cdsisrol = usuario.getRolActivo().getClave();
			String cdelemen = usuario.getEmpresa().getElementoId();
			
			checkNull(smap1, "No se recibieron datos de poliza");
			String cdunieco    = smap1.get("cdunieco");
			String cdramo      = smap1.get("cdramo");
			String cdtipsit    = smap1.get("cdtipsit");
			String estado      = smap1.get("estado");
			String nmpoliza    = smap1.get("nmpoliza");
			String feini       = smap1.get("feini");
			String fefin       = smap1.get("fefin");
			String cdagente    = smap1.get("cdagente");
			String cdpersonCli = smap1.get("cdpersonCli");
			String cdideperCli = smap1.get("cdideperCli");
			String tipoflot    = smap1.get("tipoflot");
			checkBlank(cdunieco , "No se recibio la sucursal");
			checkBlank(cdramo   , "No se recibio el producto");
			checkBlank(cdtipsit , "No se recibio la modalidad");
			checkBlank(estado   , "No se recibio el estado");
			checkBlank(feini    , "No se recibio el inicio de vigencia");
			checkBlank(fefin    , "No se recibio el fin de vigencia");
			checkBlank(cdagente , "No se recibio el agente");
			checkBlank(tipoflot , "No se recibio el tipo de cotizacion");
			
			checkList(slist1, "No se recibieron las situaciones mixtas");
			checkList(slist2, "No se recibieron las situaciones base");
			checkList(slist3, "No se recibieron las configuraciones de plan");
			
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
					,cdideperCli
					,slist1
					,slist2
					,slist3
					,noTarificar
					,tipoflot
					,tvalopol
					);
			
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			if(exito)
			{
				smap1.putAll(resp.getSmap());
				slist1 = resp.getSlist();
			}
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### cotizarAutosFlotilla ######")
				.append("\n##################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String cargarValidacionTractocamionRamo5()
	{
		logger.info(
				new StringBuilder()
				.append("\n###############################################")
				.append("\n###### cargarValidacionTractocamionRamo5 ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1,"No se recibieron datos para el web service");
			String poliza = smap1.get("poliza");
			String rfc    = smap1.get("rfc");
			checkBlank(poliza,"No se recibio la poliza tractocamion para el webservice");
			ManagerRespuestaVoidVO resp = cotizacionAutoManager.cargarValidacionTractocamionRamo5(poliza,rfc);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### cargarValidacionTractocamionRamo5 ######")
				.append("\n###############################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String procesarCargaMasivaFlotilla()
	{
		logger.info(
				new StringBuilder()
				.append("\n#########################################")
				.append("\n###### procesarCargaMasivaFlotilla ######")
				.append("\n###### smap1=")           .append(smap1)
				.append("\n###### excel=")           .append(excel)
				.append("\n###### excelFileName=")   .append(excelFileName)
				.append("\n###### excelContentType=").append(excelContentType)
				.toString()
				);
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos");
			String cdramo   = smap1.get("cdramo");
			String cdtipsit = smap1.get("cdtipsit");
			String respetar = smap1.get("tomarMasiva");
			checkBlank(cdramo   , "No se recibio el producto");
			checkBlank(cdtipsit , "No se recibio la modalidad");
			checkNull(excel, "No se recibio el archivo");
			
			ManagerRespuestaSlistVO resp = cotizacionAutoManager.procesarCargaMasivaFlotilla(cdramo,cdtipsit,respetar,excel);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			if(exito)
			{
				slist1 = resp.getSlist();
			}
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### procesarCargaMasivaFlotilla ######")
				.append("\n#########################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String cargarCotizacionAutoFlotilla()
	{
		logger.debug(Utils.log(
				 "\n##########################################"
				,"\n###### cargarCotizacionAutoFlotilla ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos");
			String cdramo   = smap1.get("cdramo");
			String nmpoliza = smap1.get("nmpoliza");
			checkBlank(cdramo   , "No se recibio el producto");
			checkBlank(nmpoliza , "No se recibio el numero de poliza");
			
			checkNull(session                , "No hay sesion");
			checkNull(session.get("USUARIO") , "No hay usuario en la sesion");
			String cdusuari = ((UserVO)session.get("USUARIO")).getUser();
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			
			ManagerRespuestaSlist2SmapVO resp = cotizacionAutoManager.cargarCotizacionAutoFlotilla(cdramo,nmpoliza,cdusuari,cdsisrol);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			if(exito)
			{
				smap1.putAll(resp.getSmap());
				slist1 = resp.getSlist1();
				slist2 = resp.getSlist2();
			}
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### cargarCotizacionAutoFlotilla ######"
				,"\n##########################################"
				));
		return SUCCESS;
	}
	
	public String emisionAutoFlotilla()
	{
		long stamp = System.currentTimeMillis();
		logger.info(Utils.log(stamp
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
			
			if(flujo!=null)
			{
				logger.debug(Utils.log(stamp, "se creara el mapa porque viene de flujo"));
				
				smap1 = new HashMap<String,String>();
				
				smap1.put("cdunieco" , flujo.getCdunieco());
				smap1.put("cdramo"   , flujo.getCdramo());
				smap1.put("cdtipsit" , "AR");
				smap1.put("estado"   , flujo.getEstado());
				
				logger.debug(Utils.log(stamp, "recuperando tramite"));
				
				StringBuilder sb = new StringBuilder();
				Map<String,Object> datosFlujo = flujoMesaControlManager.recuperarDatosTramiteValidacionCliente(sb, flujo);
				logger.debug(sb.toString());
				
				Map<String,String> tramite = (Map<String,String>)datosFlujo.get("TRAMITE");
				logger.debug(Utils.log(stamp, "tramite=", tramite));
				
				smap1.put("nmpoliza" , tramite.get("NMSOLICI"));
				
				smap1.put("ntramite" , flujo.getNtramite());
				smap1.put("tipoflot" , flujo.getAux());
				
				logger.debug(Utils.log(stamp, "el mapa creado desde flujo es=", smap1));
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
		
		logger.info(Utils.log(stamp
				,"\n###### result=", result
				,"\n###### emisionAutoFlotilla ######"
				,"\n#################################"
				));
		return result;
	}
	
	public String guardarComplementariosAutoFlotilla()
	{
		logger.debug(Utils.log(
				 "\n################################################"
				,"\n###### guardarComplementariosAutoFlotilla ######"
				,"\n###### smap1="  , smap1
				,"\n###### slist1=" , slist1
				));
		
		exito = true;
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1  , "No se recibieron datos de poliza");
			checkList(slist1 , "No se recibieron los incisos");
			String cdunieco    = smap1.get("cdunieco");
			String cdramo      = smap1.get("cdramo");
			String estado      = smap1.get("estado");
			String nmpoliza    = smap1.get("nmpoliza");
			String agenteSec   = smap1.get("agente_sec");
			String porpartiSec = smap1.get("porparti");
			String feini       = smap1.get("feini");
			String fefin       = smap1.get("fefin");
			String ntramite    = smap1.get("ntramite");
			checkBlank(cdunieco , "No se recibio la sucursal");
			checkBlank(cdramo   , "No se recibio el producto");
			checkBlank(estado   , "No se recibio el estado de la poliza");
			checkBlank(nmpoliza , "No se recibio el numero de poliza");
			checkBlank(feini    , "No se recibio la fecha de inicio");
			checkBlank(fefin    , "No se recibio la fecha de fin");
			checkBlank(ntramite , "No se recibio el numero de tramite");
			
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
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### guardarComplementariosAutoFlotilla ######"
				,"\n################################################"
				));
		return SUCCESS;
	}
	
	public String recotizarAutoFlotilla()
	{
		logger.debug(Utils.log(
				 "\n###################################"
				,"\n###### recotizarAutoFlotilla ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos");
			String cdunieco   = smap1.get("cdunieco");
			String cdramo     = smap1.get("cdramo");
			String cdtipsit   = smap1.get("cdtipsit");
			String estado     = smap1.get("estado");
			String nmpoliza   = smap1.get("nmpoliza");
			String notarifica = smap1.get("notarifica");
			String cdperpag   = smap1.get("cdperpag");
			checkBlank(cdunieco , "No se recibio la sucursal");
			checkBlank(cdramo   , "No se recibio el producto");
			checkBlank(estado   , "No se recibio el estado");
			checkBlank(nmpoliza , "No se recibio el numero de poliza");
			checkBlank(cdtipsit , "No se recibio la modalidad");
			checkBlank(cdperpag , "No se recibio la forma de pago");
			
			checkNull(session, "No hay sesion");
			checkNull(session.get("USUARIO"), "No hay usuario en la sesion");
			String cdusuari = ((UserVO)session.get("USUARIO")).getUser();
			String cdelemen = ((UserVO)session.get("USUARIO")).getEmpresa().getElementoId();
			
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
			
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			if(exito)
			{
				slist1 = resp.getSlist();
			}
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### recotizarAutoFlotilla ######"
				,"\n###################################"
				));
		return SUCCESS;
	}
	
	public String cargarObligatorioTractocamionRamo5()
	{
		logger.debug(Utils.log(
				 "\n################################################"
				,"\n###### cargarObligatorioTractocamionRamo5 ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos");
			
			String clave = smap1.get("clave");
			checkBlank(clave, "No se recibio la clave del vehiculo");
			
			ManagerRespuestaSmapVO resp = cotizacionAutoManager.cargarObligatorioTractocamionRamo5(clave);
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
				 "\n###### cargarObligatorioTractocamionRamo5 ######"
				,"\n################################################"
				));
		return SUCCESS;
	}
	
	public String cargarDetalleNegocioRamo5()
	{
		logger.debug(Utils.log(
				 "\n#######################################"
				,"\n###### cargarDetalleNegocioRamo5 ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos para cargar detalle de negocio");
			checkNull(session,"No hay sesion");
			checkNull(session.get("USUARIO"),"No hay usuario en la sesion");
			
			String negocio  = smap1.get("negocio");
			String cdramo   = smap1.get("cdramo");
			String cdtipsit = smap1.get("cdtipsit");
			
			UserVO usuario = (UserVO)session.get("USUARIO");
			String cdusuari = usuario.getUser();
			String cdsisrol = usuario.getRolActivo().getClave();
			checkBlank(negocio, "No se recibio clave de negocio");
			
			ManagerRespuestaSmapVO resp = cotizacionAutoManager.cargarDetalleNegocioRamo5(negocio, cdramo, cdtipsit, cdsisrol, cdusuari);
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
				 "\n###### cargarDetalleNegocioRamo5 ######"
				,"\n#######################################"
				));
		return SUCCESS;
	}
	
	public String guardarPantallaBeneficiarios()
	{
		logger.debug(Utils.log(
				 "\n##########################################"
				,"\n###### guardarPantallaBeneficiarios ######"
				,"\n###### smap1="  , smap1
				,"\n###### slist1=" , slist1
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
			String nmsituac = smap1.get("nmsituac");
			
			checkBlank(cdunieco , "No se recibio la sucursal");
			checkBlank(cdramo   , "No se recibio el producto");
			checkBlank(estado   , "No se recibio el estado de la poliza");
			checkBlank(nmpoliza , "No se recibio el numero de poliza");
			checkBlank(nmsuplem , "No se recibio el numero de suplemento");
			checkBlank(nmsituac , "No se recibio el numero de situacion");
			
			ManagerRespuestaVoidVO resp=cotizacionAutoManager.guardarPantallaBeneficiarios(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplem
					,nmsituac
					,slist1);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### guardarPantallaBeneficiarios ######"
				,"\n##########################################"
				));
		return SUCCESS;
	}
	
	public String cargarParamerizacionConfiguracionCoberturasRol()
	{
		logger.info(
				new StringBuilder()
				.append("\n############################################################")
				.append("\n###### cargarParamerizacionConfiguracionCoberturasRol ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos");
			String cdtipsit     = smap1.get("cdtipsit");
			String cdsisrol     = smap1.get("cdsisrol");
			
			checkBlank(cdtipsit     , "No se recibio la modalidad");
			checkBlank(cdsisrol     , "No se recibio el rol");
			
			ManagerRespuestaSlistVO resp = cotizacionAutoManager.cargarParamerizacionConfiguracionCoberturasRol(cdtipsit,cdsisrol);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			if(exito)
			{
				slist1 = resp.getSlist();
			}
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### cargarParamerizacionConfiguracionCoberturasRol ######")
				.append("\n############################################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String obtieneValNumeroSerie()
	{
		logger.info(
				new StringBuilder()
				.append("\n###################################")
				.append("\n###### obtieneValNumeroSerie ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		exito = true;
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos de poliza");
			String feini    = smap1.get("feini");
			String numSerie      = smap1.get("numSerie");
			checkBlank(feini , "No se recibio la fecha inicial");
			checkBlank(numSerie   , "No se recibio el n�mero de serie");
			String iCodAviso = "exito";
			String feAutorizacion= feini.substring(8,10)+"/"+feini.substring(5,7)+"/"+feini.substring(0,4);
			iCodAviso = cotizacionAutoManager.obtieneValidacionRetroactividad(numSerie, renderFechas.parse(feAutorizacion));
			exito           = StringUtils.isNotBlank(iCodAviso)?false:true;
			respuesta       = iCodAviso;
			respuestaOculta = iCodAviso;
			logger.debug("respuesta--->"+respuesta+" : "+iCodAviso.length());
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### obtieneValNumeroSerie ######")
				.append("\n###################################")
				.toString()
				);
		return SUCCESS;
	}

	/*
	 * Getters y setters
	 */
	public void setCotizacionAutoManager(CotizacionAutoManager cotizacionAutoManager) {
		logger.debug("setCotizacionAutoManager");
		cotizacionAutoManager.setSession(this.session);
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