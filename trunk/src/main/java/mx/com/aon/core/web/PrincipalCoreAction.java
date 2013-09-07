package mx.com.aon.core.web;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;


public abstract class PrincipalCoreAction extends ActionSupport implements SessionAware, Preparable {

	protected final transient Logger logger = Logger.getLogger(PrincipalCoreAction.class);
	
	@SuppressWarnings("unchecked")
	protected Map session;
	
	@SuppressWarnings("unchecked")
	protected Map request;
	/**
	 * Constante que se utiliza para redireccionar a la pagina del portal.
	 */
	public static final String PORTAL = "portal";
		
    public void prepare() throws Exception {
        // TODO Auto-generated method stub

    }
    
    @SuppressWarnings("unchecked")
    public void setRequest(Map request) {
        this.request = request;
    }
    
	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;		
	}

	public Map<String,Object> getSession()
	{
		return session;
	}
	
	


}
