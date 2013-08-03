package mx.com.aon.portal.web;

import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.portal.model.CheckListCuentaPreRequisitosVO;
import mx.com.aon.portal.service.CheckListCuentaPreRequisitosManager;
import org.apache.log4j.Logger;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Checklist de la cuenta.
 * 
 */
@SuppressWarnings("serial")
public class EditarCheckListCuentaAction extends ActionSupport{

	private static Logger logger = Logger.getLogger(EditarCheckListCuentaAction.class);
	private transient CheckListCuentaPreRequisitosManager checkListCuentaPreRequisitosManager;
	private boolean success;
	private String codigoCliente;
	private String codigoConfiguracion;
	private String dsConfiguracion;
	private String codigoSeccion;
	private String codigoTarea;
	private String linOpe;
	private String codigoCompletada;
	private String codigoRequerida;

	/**
	 * Metodo que actualiza o inserta una cuenta pre-requisitos.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGuardarClick () throws Exception{
		try {
			CheckListCuentaPreRequisitosVO checkListCuentaPreRequisitosVO = new CheckListCuentaPreRequisitosVO();
			checkListCuentaPreRequisitosVO.setCdConfig(codigoConfiguracion);
			checkListCuentaPreRequisitosVO.setDsConfig(dsConfiguracion);
			checkListCuentaPreRequisitosVO.setCdLinOpe(linOpe);
			checkListCuentaPreRequisitosVO.setCdPerson(codigoCliente);
			checkListCuentaPreRequisitosVO.setCdSeccion(codigoSeccion);
			checkListCuentaPreRequisitosVO.setCdTarea(codigoTarea);
			checkListCuentaPreRequisitosVO.setCdCompletada(codigoCompletada);
			checkListCuentaPreRequisitosVO.setNoRequerida(codigoRequerida);
			//checkListCuentaPreRequisitosManager.guardaPreRequisito(checkListCuentaPreRequisitosVO);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			success = false;
			addActionError("Error al guardar prerequisitos de Cuenta");
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}
	/**
	 * Valida si las tareas estan Completadas y si son o no Requeridas.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String ValidaEstadoTareas () throws Exception{
		try {
			if (checkListCuentaPreRequisitosManager.isConfiguracionTareasCompleta(codigoConfiguracion) ) {
                success = true;
            } else {
                success = false;
            }
        }catch (Exception e) {
			logger.error(e.getMessage(), e);
			success = false;
			addActionError("Error al comprobar estado de tareas");
			return SUCCESS;
		}
        return  SUCCESS;
	}

	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getCodigoCliente() {
		return codigoCliente;
	}
	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	public String getCodigoConfiguracion() {
		return codigoConfiguracion;
	}
	public void setCodigoConfiguracion(String codigoConfiguracion) {
		this.codigoConfiguracion = codigoConfiguracion;
	}
	public String getCodigoSeccion() {
		return codigoSeccion;
	}
	public void setCodigoSeccion(String codigoSeccion) {
		this.codigoSeccion = codigoSeccion;
	}
	public String getDsConfiguracion() {
		return dsConfiguracion;
	}
	public void setDsConfiguracion(String dsConfiguracion) {
		this.dsConfiguracion = dsConfiguracion;
	}
	public String getLinOpe() {
		return linOpe;
	}
	public void setLinOpe(String linOpe) {
		this.linOpe = linOpe;
	}
	public String getCodigoTarea() {
		return codigoTarea;
	}
	public void setCodigoTarea(String codigoTarea) {
		this.codigoTarea = codigoTarea;
	}
	public String getCodigoCompletada() {
		return codigoCompletada;
	}
	public void setCodigoCompletada(String codigoCompletada) {
		this.codigoCompletada = codigoCompletada;
	}
	public String getCodigoRequerida() {
		return codigoRequerida;
	}
	public void setCodigoRequerida(String codigoRequerida) {
		this.codigoRequerida = codigoRequerida;
	}
}