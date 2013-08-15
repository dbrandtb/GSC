/**
 * 
 */
package mx.com.aon.portal.web;

import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.portal.model.ConfigurarEstructuraVO;
import mx.com.aon.portal.service.ManagerCuentaChecklist;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.core.ApplicationException;
import org.apache.log4j.Logger;
import java.util.ArrayList;

/**
 * 
 *   Action que atiende la peticion del usuario para realizar
 *   la copia de una configuracion a un cliente sin configuracion.
 * 
 */
public class CopiaConfiguraCuentaAction extends ActionSupport{

	private static final long serialVersionUID = 16542221111L;
	boolean success;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(CopiaConfiguraCuentaAction.class);
	private String cdConfig;
	private String cdElemento;
	private String cdPerson;
	private transient ManagerCuentaChecklist managerCuentaChecklist;
    /**
	 * Respuesta interpretada por strust con los valores de la consulta.
	 * 
	 */
	ArrayList<ConfigurarEstructuraVO> comboClientes;

	/**
	 * Metodo que realiza la copia de una configuracion a otra seleccionada por el usuario.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
/*	public String cmdCopiarConfiguraCuentaClick()throws Exception
	{
		try{
			@SuppressWarnings("unused")
			WrapperResultados wrapperResultados = null;
			wrapperResultados = managerCuentaChecklist.copiarConfiguracionCuenta(cdConfig,cdElemento,cdPerson);
            success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
*/	
	/**
	 * Metodo que realiza la copia de una configuracion a otra seleccionada por el usuario.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	public String cmdCopiarConfiguraCuentaClick()throws Exception
	{
		String messageResult = "";
		try{
			messageResult =  managerCuentaChecklist.copiarConfiguracionCuenta(cdConfig,cdElemento,cdPerson);		
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}	
	
	
	
	
	
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public void setManagerCuentaChecklist(ManagerCuentaChecklist managerCuentaChecklist) {
        this.managerCuentaChecklist = managerCuentaChecklist;
    }

	public String getCdConfig() {
		return cdConfig;
	}

	public void setCdConfig(String cdConfig) {
		this.cdConfig = cdConfig;
	}

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}

	public String getCdPerson() {
		return cdPerson;
	}

	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

	public ArrayList<ConfigurarEstructuraVO> getComboClientes() {
		return comboClientes;
	}

	public void setComboClientes(ArrayList<ConfigurarEstructuraVO> comboClientes) {
		this.comboClientes = comboClientes;
	}

	public ManagerCuentaChecklist obtenManagerCuentaChecklist() {
		return managerCuentaChecklist;
	}
}