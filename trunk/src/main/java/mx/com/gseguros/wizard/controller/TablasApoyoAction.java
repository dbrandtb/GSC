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
     * Mensaje de respuesta del servicio
     */
    private String msgRespuesta;

    
    public String execute() throws Exception {
    	return SUCCESS;
    }
	
	public String obtieneValoresTablaApoyo5claves()throws Exception{
    	
    	try{
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
}