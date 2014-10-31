package mx.com.gseguros.portal.cotizacion.controller;

import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;
import mx.com.gseguros.portal.cotizacion.service.CotizacionAutoManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;

public class CotizacionAutoAction extends PrincipalCoreAction
{
	private static final long serialVersionUID = -5890606583100529056L;
	private static final Logger logger         = Logger.getLogger(CotizacionAutoAction.class);
	
	private CotizacionAutoManager cotizacionAutoManager;
	
	private Map<String,String> smap1           = null;
	private String             respuesta       = null;
	private String             respuestaOculta = null;
	private boolean            exito           = false;
	private Map<String,Item>   imap            = null;

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
			cdsisrol = usuario.getRolActivo().getObjeto().getValue();
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
}