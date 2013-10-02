package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import mx.com.aon.portal.model.ConsultaConfiguracionRenovacionVO;
import mx.com.aon.portal.model.RolRenovacionVO;
import mx.com.aon.portal.service.RolesRenovacionManager;
import mx.com.gseguros.exception.ApplicationException;

/**
 *   Action que atiende las peticiones de que vienen de la pantalla Roles de Renovacion
 * 
 */
@SuppressWarnings("serial")
public class RolesRenovacionAction extends ActionSupport{

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 * Este objeto no es serializable
	 */
	private transient RolesRenovacionManager rolesRenovacionManager;
	
	private List<ConsultaConfiguracionRenovacionVO> encabezado;

	private String cdRenova;
	private String cdRol;

	private boolean success;
	
	
    /**
     * Metodo que atiende una peticion de obtener un encabezado de roles de renovacion
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdGet()throws Exception
	{
		try
		{
			encabezado=new ArrayList<ConsultaConfiguracionRenovacionVO>();
			ConsultaConfiguracionRenovacionVO consultaConfiguracionRenovacionVO=rolesRenovacionManager.getEncabezadoRolesRenovacion(cdRenova);
			encabezado.add(consultaConfiguracionRenovacionVO);
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
     * Metodo que atiende una peticion de insertar o actualizar un rol de renovacion
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdAgregarGuardarClick()throws Exception
	{
		String messageResult = "";
		try
		{
			RolRenovacionVO rolRenovacionVO = new RolRenovacionVO();
            
			rolRenovacionVO.setCdRenova(cdRenova);
			rolRenovacionVO.setCdRol(cdRol);

            messageResult = rolesRenovacionManager.agregarGuardarRolRenovacion(rolRenovacionVO);
            addActionMessage(messageResult);
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
	 * Elimina un registro de la grilla previamente seleccionado
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = obtenRolesRenovacionManager().borrarRolRenovacion(cdRenova,cdRol);	
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
	public String cmdRegresarClick(){
		return "consultaConfiguracionRenovacion";
	}

    public boolean getSuccess() {return success;}

	public void setSuccess(boolean success) {this.success = success;}
	
	public RolesRenovacionManager obtenRolesRenovacionManager() {
		return rolesRenovacionManager;
	}


	public String getCdRenova() {
		return cdRenova;
	}

	public void setCdRenova(String cdRenova) {
		this.cdRenova = cdRenova;
	}


	public List<ConsultaConfiguracionRenovacionVO> getEncabezado() {
		return encabezado;
	}


	public void setEncabezado(List<ConsultaConfiguracionRenovacionVO> encabezado) {
		this.encabezado = encabezado;
	}


	public String getCdRol() {
		return cdRol;
	}


	public void setCdRol(String cdRol) {
		this.cdRol = cdRol;
	}


    public void setRolesRenovacionManager(RolesRenovacionManager rolesRenovacionManager) {
        this.rolesRenovacionManager = rolesRenovacionManager;
    }
}
