package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.portal.model.ConfiguracionClienteVO;
import mx.com.aon.portal.service.ConfiguracionRenovacionManager;
import mx.com.gseguros.exception.ApplicationException;

/**
 * 
 * Clase Action que atiende los requerimientos que vienen de la pantalla Configuracion de la renovacion 
 *
 */
public class ConsultaConfiguracionRenovacionAction extends ActionSupport{

	private static final long serialVersionUID = 1698979785657147844L;

	 private String cdRenova;
	 private String cdPerson;
	 private String cdElemento;
	 private String cdUniEco;
	 private String cdRamo;
	 private String cdTipoRenova;
	 private String cdDiasAnticipacion;
	 private String continuaNum;
	 private String codeResult;
	 @SuppressWarnings("unused")
	 private static Logger logger = Logger.getLogger(ConsultaConfiguracionRenovacionAction.class);
	 
	private transient ConfiguracionRenovacionManager configuracionRenovacionManager;
	
	private List<ConfiguracionClienteVO> regConfiguraCliente;

	private boolean success;
	
	/**
	 * Metodo que realiza la insercion de un nuevo registro o la actualizacion de un registro editado
	 * para la pantalla Configuracion de la renovacion.
	 *  
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGuardarClick() throws Exception
	{
		String messageResult = "";
		try
		{			
			messageResult = configuracionRenovacionManager.guardaConfiguracion(cdRenova, cdPerson, cdElemento, cdUniEco, cdRamo, cdTipoRenova, cdDiasAnticipacion, continuaNum);
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
	
	/**
	 * Metodo que realiza la eliminacion de un registro seleccionado 
	 * en la pantalla Configuracion de la renovacion.
	 *  
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBorrarClick() throws Exception
	{
		String messageResult = "";
		try
		{		
			messageResult = configuracionRenovacionManager.eliminarConfiguracion(cdRenova);
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
	
	/**
	 * Metodo que busca y obtiene un unico registro para la pantalla configuracion de la renovacion.
	 *  
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGetClick()throws Exception{
		try
		{
			regConfiguraCliente = new ArrayList<ConfiguracionClienteVO>();
			ConfiguracionClienteVO registro = configuracionRenovacionManager.getConfiguracionCliente(cdRenova);
			regConfiguraCliente.add(registro);
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

	/**
	 * Metodo que redirecciona a la pantalla de configurar roles de renovacion
	 *  
	 * @return String
	 * 
	 * @throws Exception
	 */
	public String cmdIrRolesRenovacionReporte(){
		return "rolesRenovacion";
	}
	
	/**
	 * Metodo que realiza una validacion sobre la existencia de roles para configurar acciones
	 *  
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String validarRolesParaIrAcciones()throws Exception
	{
		try
		{		
			codeResult = configuracionRenovacionManager.getValidacionRoles(cdRenova);
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
	
	/**
	 * Metodo que redirecciona a la pantalla de configurar acciones de renovacion
	 *  
	 * @return String
	 * 
	 * @throws Exception
	 */
	public String cmdIrAccionesRenovacionReporte(){
		return "accionesRenovacionReporte";
	}
	
	/**
	 * Metodo que redirecciona a la pantalla de configurar rangos de renovacion
	 *  
	 * @return String
	 * 
	 * @throws Exception
	 */
	public String cmdIrRangosRenovacionReporte(){
		return "rangosRenovacionReporte";
	}
	
	
    public boolean getSuccess() {return success;}

	public void setSuccess(boolean success) {this.success = success;}

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}

	public String getCdUniEco() {
		return cdUniEco;
	}

	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}

	public String getCdRamo() {
		return cdRamo;
	}

	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

	public String getCdRenova() {
		return cdRenova;
	}

	public void setCdRenova(String cdRenova) {
		this.cdRenova = cdRenova;
	}

	public String getCdPerson() {
		return cdPerson;
	}

	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

	public String getCdTipoRenova() {
		return cdTipoRenova;
	}

	public void setCdTipoRenova(String cdTipoRenova) {
		this.cdTipoRenova = cdTipoRenova;
	}

	public String getCdDiasAnticipacion() {
		return cdDiasAnticipacion;
	}

	public void setCdDiasAnticipacion(String cdDiasAnticipacion) {
		this.cdDiasAnticipacion = cdDiasAnticipacion;
	}

	public void setConfiguracionRenovacionManager(
			ConfiguracionRenovacionManager configuracionRenovacionManager) {
		this.configuracionRenovacionManager = configuracionRenovacionManager;
	}

	public List<ConfiguracionClienteVO> getRegConfiguraCliente() {
		return regConfiguraCliente;
	}

	public void setRegConfiguraCliente(
			List<ConfiguracionClienteVO> regConfiguraCliente) {
		this.regConfiguraCliente = regConfiguraCliente;
	}

	public String getCodeResult() {
		return codeResult;
	}

	public void setCodeResult(String codeResult) {
		this.codeResult = codeResult;
	}

	public String getContinuaNum() {
		return continuaNum;
	}

	public void setContinuaNum(String continuaNum) {
		this.continuaNum = continuaNum;
	}
}
