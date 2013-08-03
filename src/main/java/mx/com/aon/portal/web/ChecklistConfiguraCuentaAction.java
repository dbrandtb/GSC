package mx.com.aon.portal.web;

import org.apache.log4j.Logger;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.ManagerCuentaChecklist;

import com.opensymphony.xwork2.ActionSupport;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Checklist de configuracion de la cuenta.
 * 
 */
public class ChecklistConfiguraCuentaAction extends ActionSupport{

	private static final long serialVersionUID = 165478789964L;
	private String cdConfig;
	private transient ManagerCuentaChecklist managerCuentaChecklist;
	private boolean success;
	private String flag;
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ChecklistConfiguraCuentaAction.class);
	
	/**
	 * Método que elimina una configuración de la cuenta seleccionada del grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	public String cmdBorrarClick()throws Exception{		
		@SuppressWarnings("unused")
		String messageResult = "";
		try
		{			
			messageResult = managerCuentaChecklist.borraConfiguracion(cdConfig);
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
        }catch(ApplicationException e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	/**
	 * Metodo que redirecciona a la pantalla de CheckList de la cuenta para insertar.
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 */
	public String cmdIrAgregarConfiguraCuentaClick()throws Exception{
		return "agregarConfiguraCuenta";
	}
	
	/**
	 * Metodo que redirecciona a la pantalla de CheckList de la cuenta para editar y actualizar.
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 */
	public String cmdIrEditarConfiguraCuentaClick()throws Exception{
		return "editarConfiguraCuenta";
	}
	
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setManagerCuentaChecklist(
			ManagerCuentaChecklist managerCuentaChecklist) {
		this.managerCuentaChecklist = managerCuentaChecklist;
	}

	public String getCdConfig() {
		return cdConfig;
	}

	public void setCdConfig(String cdConfig) {
		this.cdConfig = cdConfig;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}