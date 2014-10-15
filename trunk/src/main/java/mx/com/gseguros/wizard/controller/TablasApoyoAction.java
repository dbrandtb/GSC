package mx.com.gseguros.wizard.controller;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.wizard.service.TablasApoyoManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("tablasApoyoAction")
public class TablasApoyoAction extends PrincipalCoreAction {

	private static final long serialVersionUID = 3547571651089343357L;

	@Autowired
	private TablasApoyoManager tablasApoyoManager;
	
	
	private boolean success;
	
	
	/**
     * Parametros enviados a los catalogos
     */
    private Map<String, String> params;
	
	/**
     * Lista de elementos a cargar en grid por tipo Map
     */
    private List<Map<String, String>> loadList;
    
    /**
     * Lista para guardar varios elementos
     */
    private List<Map<String, String>> saveList;

    /**
     * Lista para guardar varios elementos
     */
    private List<Map<String, String>> deleteList;
    
    /**
     * Lista para guardar varios elementos
     */
    private List<Map<String, String>> updateList;
    
    /**
     * Mensaje de respuesta del servicio
     */
    private String msgRespuesta;

    
    public String execute() throws Exception {
    	return SUCCESS;
    }
	
	public String obtieneValoresTablaApoyo5claves()throws Exception{
    	
    	try{
    		params.put("PV_OTCLAVE1_I", null);
    		params.put("PV_OTCLAVE2_I", null);
    		params.put("PV_OTCLAVE3_I", null);
    		params.put("PV_OTCLAVE4_I", null);
    		params.put("PV_OTCLAVE5_I", null);
    		params.put("PV_FEDESDE_I", null);
    		params.put("PV_FEHASTA_I", null);
    		params.put("PV_LIMITE_I" , "1000");
    		
    		logger.debug("Parametros para obtener valores de Tabla de Apoyo: " + params);
    		loadList = tablasApoyoManager.obtieneValoresTablaApoyo5claves(params);
    	}catch(Exception ex){
    		logger.error("Error al obtieneValoresTablaApoyo5claves",ex);
    		msgRespuesta = ex.getMessage();
    		success = false;
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    }
	
	public String guardaValoresTablaApoyo()throws Exception{
    	
    	try{
    		logger.debug("Guardando Valores de la Tabla de Apoyo...");
    		logger.debug("Parametros: " + params);
    		logger.debug("DeleteList: " + deleteList);
    		logger.debug("SaveList:   " + saveList);
    		logger.debug("UdateList:  " + updateList);
    		
    		tablasApoyoManager.guardaValoresTablaApoyo(params,deleteList,saveList,updateList);
    		
    		logger.debug("Valores de Tabla Guardados... ");
    		
    	}catch(Exception ex){
    		logger.error("Error al guardaValoresTablaApoyo",ex);
    		msgRespuesta = ex.getMessage();
    		success = false;
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    }


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public Map<String, String> getParams() {
		return params;
	}


	public void setParams(Map<String, String> params) {
		this.params = params;
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


	public String getMsgRespuesta() {
		return msgRespuesta;
	}


	public void setMsgRespuesta(String msgRespuesta) {
		this.msgRespuesta = msgRespuesta;
	}

	public List<Map<String, String>> getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(List<Map<String, String>> deleteList) {
		this.deleteList = deleteList;
	}

	public List<Map<String, String>> getUpdateList() {
		return updateList;
	}

	public void setUpdateList(List<Map<String, String>> updateList) {
		this.updateList = updateList;
	}
}