package mx.com.gseguros.portal.renovacion.controller;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.renovacion.service.RenovacionManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;

public class RenovacionAction extends PrincipalCoreAction
{
	private static final long        serialVersionUID = 6666057023524182296L;
	private static final Logger      logger           = Logger.getLogger(RenovacionAction.class);
	private boolean                  success          = true;
	private boolean                  exito            = false;
	private Map<String,String>       smap1            = null;
	private String                   respuesta        = null;
	private String                   respuestaOculta  = null;
	private Map<String,Item>         imap             = null;
	private List<Map<String,String>> slist1           = null;
	
	//Dependencias inyectadas
	private RenovacionManager renovacionManager;
	
	public String pantallaRenovacion()
	{
		logger.info(
				new StringBuilder()
				.append("\n################################")
				.append("\n###### pantallaRenovacion ######")
				.toString()
				);
		
		exito = true;
		
		String cdsisrol = null;
		
		//sesion
		try
		{
			if(session==null)
			{
				throw new ApplicationException("No hay sesion");
			}
			UserVO usuario = (UserVO)session.get("USUARIO");
			if(usuario==null)
			{
				throw new ApplicationException("No hay usuario en sesion");
			}
			cdsisrol = usuario.getRolActivo().getObjeto().getValue();
			if(StringUtils.isBlank(cdsisrol))
			{
				throw new ApplicationException("No hay rol en la sesion");
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
			respuesta       = new StringBuilder("Error al obtener datos de sesion #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		//proceso
		if(exito)
		{
			ManagerRespuestaImapVO managerResponse = renovacionManager.pantallaRenovacion(cdsisrol);
			exito           = managerResponse.isExito();
			respuesta       = managerResponse.getRespuesta();
			respuestaOculta = managerResponse.getRespuestaOculta();
			if(exito)
			{
				imap = managerResponse.getImap();
			}
		}
			
		logger.info(
				new StringBuilder()
				.append("\n###### pantallaRenovacion ######")
				.append("\n################################")
				.toString()
				);
		
		String result = SUCCESS;
		if(!exito)
		{
			result = ERROR;
		}
		return result;
	}
	
	/**
	 * Busca polizas renovables
	 * @param smap1.anio
	 * @param smap1.mes
	 * @return success
	 * @return slist1
	 */
	public String buscarPolizasRenovables()
	{
		logger.info(
				new StringBuilder()
				.append("\n#####################################")
				.append("\n###### buscarPolizasRenovables ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		success = true;
		
		String cdunieco = null;
		String cdramo   = null;
		String anio     = null;
		String mes      = null;
		
		//datos completos
		try
		{
			if(smap1==null)
			{
				throw new ApplicationException("No se recibieron datos");
			}
			cdunieco = smap1.get("cdunieco");
			cdramo   = smap1.get("cdramo");
			anio     = smap1.get("anio");
			mes      = smap1.get("mes");
			if(StringUtils.isBlank(anio))
			{
				throw new ApplicationException("No se recibio el año");
			}
			if(StringUtils.isBlank(mes))
			{
				throw new ApplicationException("No se recibio el mes");
			}
		}
		catch(ApplicationException ax)
		{
			long timestamp  = System.currentTimeMillis();
			success         = false;
			respuesta       = new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString();
			respuestaOculta = ax.getMessage();
			logger.error(respuesta,ax);
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			success         = false;
			respuesta       = new StringBuilder("Error al obtener datos para buscar polizas #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		//proceso
		if(success)
		{
			ManagerRespuestaSlistVO managerResp = renovacionManager.buscarPolizasRenovables(cdunieco,cdramo,anio,mes);
			success         = managerResp.isExito();
			respuesta       = managerResp.getRespuesta();
			respuestaOculta = managerResp.getRespuestaOculta();
			if(success)
			{
				slist1 = managerResp.getSlist();
			}
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### slist1=").append(slist1)
				.append("\n###### buscarPolizasRenovables ######")
				.append("\n#####################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String renovarPolizas()
	{
		logger.info(
				new StringBuilder()
				.append("\n############################")
				.append("\n###### renovarPolizas ######")
				.append("\n###### slist1=").append(slist1)
				.toString()
				);
		
		exito = true;
		
		//datos completos
		try
		{
			session=ActionContext.getContext().getSession();
			if(session==null)
			{
				throw new ApplicationException("No hay sesion");
			}
			if(session.get("USUARIO")==null)
			{
				throw new ApplicationException("No hay usuario en la sesion");
			}
			if(slist1==null||slist1.size()==0)
			{
				throw new ApplicationException("No se recibieron polizas");
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
			respuesta       = new StringBuilder("Error inesperado al obtener datos #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		//proceso
		if(exito)
		{
			ManagerRespuestaVoidVO resp = renovacionManager.renovarPolizas(slist1);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### renovarPolizas ######")
				.append("\n############################")
				.toString()
				);
		return SUCCESS;
	}

	//Getters y setters
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
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

	public void setRenovacionManager(RenovacionManager renovacionManager) {
		this.renovacionManager = renovacionManager;
	}

	public Map<String, Item> getImap() {
		return imap;
	}

	public void setImap(Map<String, Item> imap) {
		this.imap = imap;
	}

	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}
}