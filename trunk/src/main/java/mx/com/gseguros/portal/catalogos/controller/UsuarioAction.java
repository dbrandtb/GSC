package mx.com.gseguros.portal.catalogos.controller;

import java.util.HashMap;
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
import org.apache.struts2.json.JSONUtil;

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
	
	private List<Map<String, String>> loadList;
    private List<Map<String, String>> saveList;

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
	
	public String creaEditaRolSistema() throws Exception {
		try{
			usuarioManager.creaEditaRolSistema(params);
			success=true;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			errorMessage = "Error al guardar el Rol.";
		}
		return SUCCESS;
	}
	
	public String guardaUsuario() throws Exception {
    	try{
        	usuarioManager.guardaUsuario(params);
        	success=true;
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		errorMessage = "Error al guardar el usuario. Intente m&aacute;s tarde";
    	}
    	return SUCCESS;
    }
    public String obtieneUsuarios() throws Exception {
    	
    	try{
    		usuarios = usuarioManager.obtieneUsuarios(params);
    		success=true;
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		errorMessage = "Error al obtener usuarios. Intente m&aacute;s tarde";
    	}
    	
    	logger.debug("Resultado de usuarios para la busqueda: "+ usuarios);
    	return SUCCESS;
    }
    public String obtieneRolesUsuario() throws Exception {
       	try {
       		loadList = usuarioManager.obtieneRolesUsuario(params);
       	}catch( Exception e){
       		logger.error("Error en obtieneRolesUsuario",e);
       		success =  false;
       		return SUCCESS;
       	}
       	success = true;
       	return SUCCESS;
       }
    
       public String guardaRolesUsuario(){
       	
       	try {
       		logger.debug("guardaRolesUsuario SaveList: "+ saveList);
       		usuarioManager.guardaRolesUsuario(params, saveList);
       	}catch( Exception e){
       		logger.error("Error en guardaRolesUsuario",e);
       		success =  false;
       		return SUCCESS;
       	}
       	success = true;
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
	
	public String getParamsJson() {
		try {
			return JSONUtil.serialize(params);
		} catch (Exception e) {
			logger.error("Error al generar JSON de params",e);
			return null;
		}
	}

	public List<Map<String, String>> getLoadList() {
		return loadList;
	}

	public void setLoadList(List<Map<String, String>> loadList) {
		this.loadList = loadList;
	}

	public List<Map<String, String>> getSaveList() {
		return saveList;
	}

	public void setSaveList(List<Map<String, String>> saveList) {
		this.saveList = saveList;
	}
}