package mx.com.gseguros.portal.endosos.controller;

import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.endosos.service.EndososGrupoManager;

import org.apache.log4j.Logger;

public class EndososGrupoAction extends PrincipalCoreAction
{
	private static final long   serialVersionUID = -6325542558530438412L;
	private static final Logger logger           = Logger.getLogger(EndososGrupoAction.class);
	
	private boolean            success         = true;
	private boolean            exito           = false;
	private String             respuesta       = null;
	private String             respuestaOculta = null;
	private Map<String,String> smap1           = null;
	private Map<String,Item>   imap            = null;
	
	private EndososGrupoManager endososGrupoManager;

	public String endososGrupo()
	{
		logger.info(
				new StringBuilder()
				.append("\n##########################")
				.append("\n###### endososGrupo ######")
				.toString());
		endososGrupoManager.test();
		logger.info(
				new StringBuilder()
				.append("\n###### endososGrupo ######")
				.append("\n##########################")
				.toString());
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

	public Map<String, String> getSmap1() {
		return smap1;
	}

	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}

	public Map<String, Item> getImap() {
		return imap;
	}

	public void setImap(Map<String, Item> imap) {
		this.imap = imap;
	}

	public void setEndososGrupoManager(EndososGrupoManager endososGrupoManager) {
		this.endososGrupoManager = endososGrupoManager;
	}
}