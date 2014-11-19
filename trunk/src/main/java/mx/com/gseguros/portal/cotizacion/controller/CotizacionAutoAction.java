package mx.com.gseguros.portal.cotizacion.controller;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.cotizacion.service.CotizacionAutoManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;

public class CotizacionAutoAction extends PrincipalCoreAction
{
	private static final long serialVersionUID = -5890606583100529056L;
	private static final Logger logger         = Logger.getLogger(CotizacionAutoAction.class);
	
	private CotizacionAutoManager cotizacionAutoManager;
	
	private Map<String,String>       smap1           = null;
	private Map<String,String>       smap2           = null;
	private String                   respuesta       = null;
	private String                   respuestaOculta = null;
	private boolean                  exito           = false;
	private Map<String,Item>         imap            = null;
	private List<Map<String,String>> slist1          = null;

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
		
		if(ex.getClass().equals(ApplicationException.class))
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
	
	public String cotizacionAutoIndividual()
	{
		logger.info(
				new StringBuilder()
				.append("\n######################################")
				.append("\n###### cotizacionAutoIndividual ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		exito = true;
		
		String ntramite = null;
		String cdunieco = null;
		String cdramo   = null;
		String cdtipsit = null;
		String cdusuari = null;
		String cdsisrol = null;
		
		setCheckpoint("Validando datos de entrada");
		try
		{
			checkNull(session,"No hay sesión");
			checkNull(session.get("USUARIO"),"No hay usuario en la sesión");
			UserVO usuario = (UserVO)session.get("USUARIO");
			cdusuari = usuario.getUser();
			cdsisrol = usuario.getRolActivo().getClave();
			smap1.put("cdusuari" , cdusuari);
			smap1.put("cdsisrol" , cdsisrol);
			
			checkNull(smap1,"No se recibieron datos");
			ntramite = smap1.get("ntramite");
			cdunieco = smap1.get("cdunieco");
			cdramo   = smap1.get("cdramo");
			cdtipsit = smap1.get("cdtipsit");
			checkBlank(cdramo   ,"No se recibió el producto");
			checkBlank(cdtipsit ,"No se recibió la modalidad");
			
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
			respuestaOculta = resp.getRespuestaOculta();
			if(exito)
			{
				smap1.putAll(resp.getSmap());
				imap = resp.getImap();
			}
		}
		catch(ApplicationException ax)
		{
			manejaException(ax);
		}
		
		String result = SUCCESS;
		if(!exito)
		{
			result = ERROR;
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### result=").append(result)
				.append("\n###### cotizacionAutoIndividual ######")
				.append("\n######################################")
				.toString()
				);
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
		logger.info(
				new StringBuilder()
				.append("\n###################################")
				.append("\n###### emisionAutoIndividual ######")
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
			String cdtipsit = smap1.get("cdtipsit");
			String estado   = smap1.get("estado");
			String nmpoliza = smap1.get("nmpoliza");
			String ntramite = smap1.get("ntramite");
			checkBlank(cdunieco , "No se recibio la sucursal");
			checkBlank(cdramo   , "No se recibio el ramo");
			checkBlank(cdtipsit , "No se recibio la modalidad");
			checkBlank(estado   , "No se recibio el estado de la poliza");
			checkBlank(nmpoliza , "No se recibio el numero de poliza");
			checkBlank(ntramite , "No se recibio el numero de tramite");
			
			checkNull(session,"No hay sesion");
			checkNull(session.get("USUARIO"), "No hay usuario en la sesion");
			String cdusuari = ((UserVO)session.get("USUARIO")).getUser();
			
			ManagerRespuestaImapSmapVO resp=cotizacionAutoManager.emisionAutoIndividual(cdunieco,cdramo,cdtipsit,estado,nmpoliza,ntramite,cdusuari);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
			if(exito)
			{
				smap1.putAll(resp.getSmap());
				imap = resp.getImap();
			}
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		String result = SUCCESS;
		if(!exito)
		{
			result = ERROR;
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### result=").append(result)
				.append("\n###### emisionAutoIndividual ######")
				.append("\n###################################")
				.toString()
				);
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
	
	public String recuperacionSimple()
	{
		logger.info(
				new StringBuilder()
				.append("\n################################")
				.append("\n###### recuperacionSimple ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos");
			String procedimiento = smap1.get("procedimiento");
			checkNull(procedimiento, "No se recibio el procedimiento");
			checkBool(cotizacionAutoManager.obtenerMapaProcedimientosSimples().containsKey(procedimiento),"El procedimiento no existe");
			
			ManagerRespuestaSmapVO resp = cotizacionAutoManager.recuperacionSimple(procedimiento,smap1);
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
				.append("\n###### recuperacionSimple ######")
				.append("\n################################")
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
}