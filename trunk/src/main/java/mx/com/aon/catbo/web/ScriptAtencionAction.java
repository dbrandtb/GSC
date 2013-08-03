package mx.com.aon.catbo.web;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.catbo.service.FormatosDocumentosManager;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class ScriptAtencionAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 13656468489L;
	
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ScriptAtencionAction.class);
	
	private boolean success;
	
	private String cdperson;
	
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient AdministracionCasosManager administracionCasosManager;
	
	/**
	 * Metodo que elimina un registro.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	
	
	public String cmdIrScriptAtencionClick()throws Exception{
		return "irPantallaScriptAtencion";
	}
	
	
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setAdministracionCasosManager(
			AdministracionCasosManager administracionCasosManager) {
		this.administracionCasosManager = administracionCasosManager;
	}
	

	public String getCdperson() {
		return cdperson;
	}

	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}
	
	
}
