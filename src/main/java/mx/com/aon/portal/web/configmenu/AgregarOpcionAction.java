/**
 * 
 */
package mx.com.aon.portal.web.configmenu;

import java.util.HashMap;
import java.util.Map;

import mx.com.aon.portal.model.MensajesVO;
import mx.com.aon.portal.model.configmenu.OpcionVO;
import mx.com.aon.portal.service.CatalogService;
import mx.com.aon.portal.service.configmenu.ConfigMenuManager;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author eflores
 * @date 19/05/2008
 *
 */
public class AgregarOpcionAction extends  ActionSupport{

    /**
     * 
     */
    private static final long serialVersionUID = -6244487101244801030L;
    private static final transient Log log= LogFactory.getLog(AgregarOpcionAction.class);
    private static final String OPCION_EN_USO = "La opcion ya se uso en algun menu";
    private static final String ON = "on";
    private static final String ENABLE = "E";
    
    private ConfigMenuManager configMenuManager;
    private CatalogService catalogManager;
    
    private boolean success;
    
    private String cdTitulo;
    private String dsTitulo;
    private String dsUrl;
    private String dsTipDes;
    private OpcionVO opcion;
    private String msgRespuesta;
    
    /**Metodo que carga inserta un plan.
     *
     * @return
     * @throws Exception
     */
    public String insertaOpcion() throws Exception{
                
        log.debug("-> AgregarOpcionAction.insertaOpcion");
        log.debug(".. dsTitulo : " + dsTitulo);
        log.debug(".. dsUrl    : " + dsUrl);
        log.debug(".. dsTipDes : " + dsTipDes);
        opcion = new OpcionVO();
        opcion.setDsTitulo(dsTitulo);
        opcion.setDsUrl(dsUrl);
        //Si el checkbox viene con el valor de on, le asignamos E
        if(StringUtils.isNotBlank(dsTipDes) && dsTipDes.equalsIgnoreCase(ON)){
        	opcion.setDsTipDes(ENABLE);
        }else{
            opcion.setDsTipDes(dsTipDes);
        }
        log.debug(":: opcion : " + opcion);
        String idMsg = configMenuManager.insertaOpcion(opcion);
        log.debug("idMsg		:"+idMsg);
        Map<String, String> params = new HashMap<String, String>();
        params.put("msg", idMsg);
        params.put("log", "");
        params.put("cdusuario", "");
        params.put("dsprograma", "configmenu");
        log.debug("params		:"+params);
        MensajesVO mensajes = catalogManager.getMensajes(params, "OBTIENE_MENSAJES");
        msgRespuesta = mensajes.getMsgText();
        log.debug("msgRespuesta	:"+msgRespuesta);
        return SUCCESS;
    }
        
    /**
     * Metodo que edita una opcion.
     * @return
     * @throws Exception
     */
    public String editaOpcion() throws Exception{
                
        if (log.isDebugEnabled()) {
            log.debug("-> AgregarOpcionAction.editaOpcion");
            log.debug(".. cdTitulo : " + cdTitulo);
            log.debug(".. dsTitulo : " + dsTitulo);
            log.debug(".. dsUrl : " + dsUrl);
            log.debug(".. dsTipDes : " + dsTipDes);
        }
                
        opcion = new OpcionVO();
    
        opcion.setCdTitulo(cdTitulo);
        opcion.setDsTitulo(dsTitulo);
        opcion.setDsUrl(dsUrl);
        //Si el checkbox viene con el valor de on, le asignamos E
        if(StringUtils.isNotBlank(dsTipDes) && dsTipDes.equalsIgnoreCase(ON)){
        	opcion.setDsTipDes(ENABLE);
        }else{
        	opcion.setDsTipDes(dsTipDes);
        }
        
        if (log.isDebugEnabled()) {
            log.debug(":: opcion : " + opcion);
        }
        String idMsg = configMenuManager.insertaOpcion(opcion);
        log.debug("idMsg		:"+idMsg);
        Map<String, String> params = new HashMap<String, String>();
        params.put("msg", idMsg);
        params.put("log", "");
        params.put("cdusuario", "");
        params.put("dsprograma", "configmenu");
        log.debug("params		:"+params);
        MensajesVO mensajes = catalogManager.getMensajes(params, "OBTIENE_MENSAJES");
        msgRespuesta = mensajes.getMsgText();
        log.debug("msgRespuesta	:"+msgRespuesta);
        success=true;
                
        return SUCCESS;
    }
    
    /**
     * 
     * @return
     * @throws Exception
     */
    public String borrarOpcion() throws Exception{
        if (log.isDebugEnabled()) {
            log.debug("-> AgregarOpcionAction.borrarOpcion");
            log.debug(".. cdTitulo : " + cdTitulo);
        }
        
        String yaExisteCount = configMenuManager.validaBorrado(cdTitulo);
        if (log.isDebugEnabled()) {
            log.debug(".. yaExisteCount : " + yaExisteCount);
        }
        
        if(StringUtils.isNotBlank(cdTitulo)){
            if (OPCION_EN_USO.equalsIgnoreCase(yaExisteCount)) {
                msgRespuesta = yaExisteCount;
                success = false;
            } else {
            	String idMsg = configMenuManager.borrarOpcion(cdTitulo);
                log.debug("idMsg		:"+idMsg);
                Map<String, String> params = new HashMap<String, String>();
                params.put("msg", idMsg);
                params.put("log", "");
                params.put("cdusuario", "");
                params.put("dsprograma", "configmenu");
                log.debug("params		:"+params);
                MensajesVO mensajes = catalogManager.getMensajes(params, "OBTIENE_MENSAJES");
                msgRespuesta = mensajes.getMsgText();
                log.debug("msgRespuesta	:"+msgRespuesta);
                success = true;   
            }            
        }else{
            success = false;
        }
        
        return SUCCESS;        
    }
    
    /**
     * @param configMenuManager the configMenuManager to set
     */
    public void setConfigMenuManager(ConfigMenuManager configMenuManager) {
        this.configMenuManager = configMenuManager;
    }
    
    public String execute() throws Exception{
        log.debug("Entering to INPUT");
        return INPUT;        
    }
    
    public String obtieneOpciones() {
        return null;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    /**
     * @return the cdTitulo
     */
    public String getCdTitulo() {
        return cdTitulo;
    }
    /**
     * @param cdTitulo the cdTitulo to set
     */
    public void setCdTitulo(String cdTitulo) {
        this.cdTitulo = cdTitulo;
    }
    /**
     * @return the dsTitulo
     */
    public String getDsTitulo() {
        return dsTitulo;
    }
    /**
     * @param dsTitulo the dsTitulo to set
     */
    public void setDsTitulo(String dsTitulo) {
        this.dsTitulo = dsTitulo;
    }
    /**
     * @return the dsUrl
     */
    public String getDsUrl() {
        return dsUrl;
    }
    /**
     * @param dsUrl the dsUrl to set
     */
    public void setDsUrl(String dsUrl) {
        this.dsUrl = dsUrl;
    }
    /**
     * @return the opcion
     */
    public OpcionVO getOpcion() {
        return opcion;
    }
    /**
     * @param opcion the opcion to set
     */
    public void setOpcion(OpcionVO opcion) {
        this.opcion = opcion;
    }

	public String getMsgRespuesta() {
		return msgRespuesta;
	}

	public void setMsgRespuesta(String msgRespuesta) {
		this.msgRespuesta = msgRespuesta;
	}
	
	public void setCatalogManager(CatalogService catalogManager) {
		this.catalogManager = catalogManager;
	}

	public String getDsTipDes() {
		return dsTipDes;
	}

	public void setDsTipDes(String dsTipDes) {
		this.dsTipDes = dsTipDes;
	}

}
