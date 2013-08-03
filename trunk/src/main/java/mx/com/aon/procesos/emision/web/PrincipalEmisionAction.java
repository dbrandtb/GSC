/**
 * 
 */
package mx.com.aon.procesos.emision.web;

import java.util.Map;

import org.apache.log4j.Logger;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;


/**
 * Clase Action padre de Emision
 * 
 * @author Cesar Hernandez
 * 
 */
public class PrincipalEmisionAction extends ActionSupport implements SessionAware, Preparable, RequestAware {

    /**
     * 
     */
    private static final long serialVersionUID = 6935792701037483712L;

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    protected Map session;
    @SuppressWarnings("unchecked")
    protected Map request;
    
    protected int start = 0;
	protected int limit = 20;
	protected int totalCount;
    
    protected final transient Logger logger = Logger.getLogger(PrincipalEmisionAction.class);
    
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
	 * @return the session
	 */
    @SuppressWarnings("unchecked")
	public Map getSession() {
		return session;
	}
	
	/**
	 * @param session the session to set
	 */
    @SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
	}

	/**
	 * @return the request
	 */
    @SuppressWarnings("unchecked")
	public Map getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
    @SuppressWarnings("unchecked")
	public void setRequest(Map request) {
		this.request = request;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}
	
	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
     * 
     */
    public void prepare() throws Exception {
       
    }
}
