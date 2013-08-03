/**
 * 
 */
package mx.com.aon.flujos.emision.web;

import java.util.Map;

import mx.com.aon.flujos.endoso.web.PrincipalEndosoAction;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import org.apache.log4j.Logger;

/**
 * @author Leopoldo
 *
 */
public abstract class PrincipalEmisionAction extends ActionSupport implements Preparable, RequestAware, SessionAware {
 
    
    /**
     * 
     */
    protected Map request;
    
    protected final transient Logger logger = Logger.getLogger(PrincipalEndosoAction.class);
    
    /**
     * 
     */
    protected Map session;
    
    
    public void prepare() throws Exception {
        // TODO Auto-generated method stub

    }

    
    public void setRequest(Map request) {
        this.request = request;
    }

    
    public void setSession(Map session) {
        this.session = session;
    }

}
