/**
 * 
 */
package mx.com.aon.flujos.renovacion.web;

import java.util.Map;

import mx.com.ice.services.to.screen.GlobalVariableContainerVO;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Leopoldo
 *
 */
public abstract class PrincipalRenovacionAction extends ActionSupport implements Preparable, RequestAware, SessionAware {

    /**
     * 
     */
    @SuppressWarnings("unchecked")
	protected Map request;
    
    /**
     * 
     */
    @SuppressWarnings("unchecked")
	protected Map session;
    
    protected final transient Logger logger = Logger.getLogger(PrincipalRenovacionAction.class);
    
    protected GlobalVariableContainerVO globalVarVO;

    /**
     * Método que actualiza el valor de 'clicBotonRegresar' 
     * del mapa de session con llave 'PARAMETROS_REGRESAR'
     * @param void 
     * @return void 
     */
    @SuppressWarnings("unchecked")
    public void updateParametrosRegresar(){
		if ( session.containsKey("PARAMETROS_REGRESAR") ) {
			Map<String,String> pr = (Map<String,String>)session.get("PARAMETROS_REGRESAR");
			pr.put("clicBotonRegresar", "N");
			session.put("PARAMETROS_REGRESAR", pr);
		}
		return;
    }
    
    /**
     * @return the globalVarVO
     */
    public GlobalVariableContainerVO getGlobalVarVO() {
        return globalVarVO;
    }

    /**
     * @param globalVarVO the globalVarVO to set
     */
    public void setGlobalVarVO(GlobalVariableContainerVO globalVarVO) {
        this.globalVarVO = globalVarVO;
    }
    
    /**
     * 
     */
    @SuppressWarnings("unchecked")
	public void setRequest(Map request) {
        this.request = request;
    }

    /**
     * 
     */
    @SuppressWarnings("unchecked")
	public void setSession(Map session) {
        this.session = session;
    }
    
    /**
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * 
     */
    public void prepare() throws Exception {
       
    }

}
