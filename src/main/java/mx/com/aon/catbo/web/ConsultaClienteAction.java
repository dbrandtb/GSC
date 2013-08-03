package mx.com.aon.catbo.web;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class ConsultaClienteAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 13635457844846L;
	
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ConsultaClienteAction.class);
	
	private boolean success;
	
	/*
	 * 
	 * ESTE ACTION DEBERA BORRARSE SI HASTA EL 10 DE SEPTIEMBRE NO SE LE CODIFICAN
	 * METODOS
	 * 
	 * 
	 */

	public boolean getSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}
}
