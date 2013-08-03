package mx.com.aon.portal.web;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.service.PaginaPrincipalManager;

/**
 * 
 * @author sergio.ramirez
 *
 */
public class AlertasAction extends PrincipalCoreAction{
	/**
	 *Serial Version 
	 */
	private static final long serialVersionUID = 8276444738312293918L;
	private boolean success;
	@SuppressWarnings("unused")
	private PaginaPrincipalManager paginaPrincipalManager;
	
	public String execute()throws Exception{
		return INPUT;
	}

	
	public String alertasPrincipal() throws Exception{
		
		
		
		
		
		
		return SUCCESS;
	}
	
	
	
	
	
	public void setPaginaPrincipalManager(
			PaginaPrincipalManager paginaPrincipalManager) {
		this.paginaPrincipalManager = paginaPrincipalManager;
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 
	 *Implementacion del metodo Padre (unused)
	 */
	public void prepare() throws Exception {
	}
	
	
	

}
