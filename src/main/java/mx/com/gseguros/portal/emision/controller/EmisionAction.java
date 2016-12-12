package mx.com.gseguros.portal.emision.controller;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.emision.service.EmisionManager;
import mx.com.gseguros.utils.Utils;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;

public class EmisionAction extends PrincipalCoreAction
{
	private static Logger logger = Logger.getLogger(EmisionAction.class);
	
	private EmisionManager emisionManager;
	
	private boolean                  success   = true;
	private boolean                  exito     = false;
	private String                   respuesta = null;
	private Map<String,String>       smap1     = null;
	private Map<String,Item>         imap      = null;
	private List<Map<String,String>> slist1    = null;
	
	public EmisionAction()
	{
		logger.debug("new EmisionAction");
		this.session=ActionContext.getContext().getSession();
	}
	
	public String pantallaClausulasPoliza()
	{
		logger.debug(Utils.log(
				 "\n#####################################"
				,"\n###### pantallaClausulasPoliza ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			logger.debug("Validando datos de entrada");
			Utils.validate(smap1, "No se recibieron datos de entrada");
			
			Utils.validate(smap1.get("cdunieco") , "No se recibio la sucursal");
			Utils.validate(smap1.get("cdramo")   , "No se recibio el producto");
			Utils.validate(smap1.get("estado")   , "No se recibio el estado");
			Utils.validate(smap1.get("nmpoliza") , "No se recibio el numero de poliza");
			Utils.validate(smap1.get("nmsuplem") , "No se recibio el numero de suplemento");
			
			ManagerRespuestaImapVO resp=emisionManager.construirPantallaClausulasPoliza();
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			imap      = resp.getImap();
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		String result = SUCCESS;
		if(!exito)
		{
			result=ERROR;
		}
		
		logger.debug(Utils.log(
				 "\n###### result=",result
				,"\n###### pantallaClausulasPoliza ######"
				,"\n#####################################"
				));
		return result;
	}
	
	public String guardarClausulasPoliza()
	{
		logger.debug(Utils.log(
				 "\n####################################"
				,"\n###### guardarClausulasPoliza ######"
				,"\n###### slist1=",slist1
				));
		
		try
		{
			logger.debug("Validando datos de entrada");
			Utils.validate(slist1, "No se recibieron las clausulas");
			
			ManagerRespuestaVoidVO resp=emisionManager.guardarClausulasPoliza(slist1);
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### guardarClausulasPoliza ######"
				,"\n####################################"
				));
		return SUCCESS;
	}
	
	/**
	 * Getters y setters
	 */

	public Map<String, String> getSmap1() {
		return smap1;
	}

	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}

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

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}

	public void setEmisionManager(EmisionManager emisionManager) {
		emisionManager.setSession(session);
		this.emisionManager = emisionManager;
	}

	public Map<String, Item> getImap() {
		return imap;
	}

	public void setImap(Map<String, Item> imap) {
		this.imap = imap;
	}
	
}
