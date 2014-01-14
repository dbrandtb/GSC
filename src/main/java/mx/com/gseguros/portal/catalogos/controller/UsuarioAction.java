package mx.com.gseguros.portal.catalogos.controller;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.general.model.RolVO;
import mx.com.gseguros.portal.general.model.UsuarioVO;
import mx.com.gseguros.portal.general.service.UsuarioManager;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class UsuarioAction extends ActionSupport {

	private static final long serialVersionUID = -7264510862184393230L;
	
	private Logger logger = Logger.getLogger(UsuarioAction.class);
	
	private boolean success;
	
	private String errorMessage;
	
	private UsuarioManager usuarioManager;
	
	private Map<String,String> params;

    public String guardaUsuario() throws Exception {
    	try{
        	usuarioManager.guardaUsuario(params);
        	success=true;
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		errorMessage = "Error al guardar el usuario. Intente más tarde";
    	}
    	return SUCCESS;
    }
    public String obtieneUsuarios() throws Exception {
    	
    	List<UsuarioVO> usuarios = null;
    	try{
    		usuarios = usuarioManager.obtieneUsuarios(params);
    		success=true;
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		errorMessage = "Error al guardar obtieneUsuarios. Intente más tarde";
    	}
    	
    	logger.debug("Resultado de usuarios para la busqueda: "+ usuarios);
    	return SUCCESS;
    }
    public String obtieneRolesUsuario() throws Exception {
    	List<RolVO> roles = null;
    	try{
    		roles = usuarioManager.obtieneRolesUsuario(params);
    		success=true;
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		errorMessage = "Error al guardar el usuario. Intente más tarde";
    	}
    	
    	logger.debug("Resultado de roles  de usuario: "+ roles);
    	return SUCCESS;
    }


    // getters and setters
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Map<String,String> getParams() {
		return params;
	}

	public void setParams(Map<String,String> params) {
		this.params = params;
	}
	
	public void setUsuarioManager(UsuarioManager usuarioManager) {
		this.usuarioManager = usuarioManager;
	}
	
}