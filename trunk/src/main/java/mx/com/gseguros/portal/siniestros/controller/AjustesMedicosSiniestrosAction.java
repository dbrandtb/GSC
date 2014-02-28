package mx.com.gseguros.portal.siniestros.controller;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.gseguros.portal.general.service.CatalogosManager;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;

import org.apache.log4j.Logger;

public class AjustesMedicosSiniestrosAction extends PrincipalCoreAction {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(AjustesMedicosSiniestrosAction.class);

	private transient SiniestrosManager siniestrosManager;
	
	private transient KernelManagerSustituto kernelManager;
	
	private transient PantallasManager  pantallasManager;
	
    private transient CatalogosManager catalogosManager;

	private boolean success;
	
	
	
	
	public String execute() throws Exception {
    	success = true;
    	return SUCCESS;
    }


	
	//Getters and setters:

	public void setSiniestrosManager(SiniestrosManager siniestrosManager) {
		this.siniestrosManager = siniestrosManager;
	}

	public void setKernelManager(KernelManagerSustituto kernelManager) {
		this.kernelManager = kernelManager;
	}
	
	public void setPantallasManager(PantallasManager pantallasManager) {
		this.pantallasManager = pantallasManager;
	}

	public void setCatalogosManager(CatalogosManager catalogosManager) {
		this.catalogosManager = catalogosManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	
}