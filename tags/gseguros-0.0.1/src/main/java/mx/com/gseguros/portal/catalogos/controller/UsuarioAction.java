package mx.com.gseguros.portal.catalogos.controller;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.RolVO;
import mx.com.gseguros.portal.general.model.UsuarioVO;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.service.UsuarioManager;
import mx.com.gseguros.portal.general.util.GeneradorCampos;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class UsuarioAction extends PrincipalCoreAction{

	private static final long serialVersionUID = -7264510862184393230L;
	
	private Logger logger = Logger.getLogger(UsuarioAction.class);
	
	private boolean success;
	
	private String errorMessage;
	
	private UsuarioManager usuarioManager;
	private PantallasManager pantallasManager;
	
	private Map<String,String> params;
	
	private Item fields;
	private Item columns;
	private Item items;
	
	private List<UsuarioVO> usuarios;

	public String cargaPantallaUsuarios() throws Exception{
		try
		{
			
			logger.debug("... generando pantalla para gestion de usuarios");
			UserVO usuario=(UserVO)session.get("USUARIO");
			
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			
			gc.generaParcial(pantallasManager.obtenerComponentes(
					null, null, null,
					null, null, usuario.getRolActivo().getObjeto().getValue(),
					"PANTALLA_USUARIOS", "BUSQUEDA", null));
			
			items   = gc.getItems();
			
			gc.generaParcial(pantallasManager.obtenerComponentes(
					null, null, null,
					null, null, usuario.getRolActivo().getObjeto().getValue(),
					"PANTALLA_USUARIOS", "MODEL", null));
			
			fields  = gc.getFields();
			
			gc.generaParcial(pantallasManager.obtenerComponentes(
					null, null, null,
					null, null, usuario.getRolActivo().getObjeto().getValue(),
					"PANTALLA_USUARIOS", "COLUMNMODEL", null));
			
			columns = gc.getColumns();
			
		}
		catch(Exception ex)
		{
			logger.error("Error en pantalla de Usuarios",ex);
		}
		return SUCCESS;
	}
	
	public String guardaUsuario() throws Exception {
    	try{
        	usuarioManager.guardaUsuario(params);
        	success=true;
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		errorMessage = "Error al guardar el usuario. Intente m�s tarde";
    	}
    	return SUCCESS;
    }
    public String obtieneUsuarios() throws Exception {
    	
    	try{
    		usuarios = usuarioManager.obtieneUsuarios(params);
    		success=true;
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		errorMessage = "Error al guardar obtieneUsuarios. Intente m�s tarde";
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
    		errorMessage = "Error al guardar el usuario. Intente m�s tarde";
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

	public void setPantallasManager(PantallasManager pantallasManager) {
		this.pantallasManager = pantallasManager;
	}

	public Item getFields() {
		return fields;
	}

	public void setFields(Item fields) {
		this.fields = fields;
	}

	public Item getColumns() {
		return columns;
	}

	public void setColumns(Item columns) {
		this.columns = columns;
	}

	public Item getItems() {
		return items;
	}

	public void setItems(Item items) {
		this.items = items;
	}

	public List<UsuarioVO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioVO> usuarios) {
		this.usuarios = usuarios;
	}
	
}