/**
 * 
 */
package mx.com.aon.flujos.cancelacion.web;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Leopoldo
 *
 */
public abstract class PrincipalCancelacionAction extends ActionSupport implements Preparable, RequestAware, SessionAware {

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
     * 
     */
    public void prepare() throws Exception {
       
    }

}
