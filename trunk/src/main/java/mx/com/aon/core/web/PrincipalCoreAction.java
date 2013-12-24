package mx.com.aon.core.web;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;


public abstract class PrincipalCoreAction extends ActionSupport implements SessionAware, Preparable {

	private static final long serialVersionUID = -7975229967003917194L;

	protected final transient Logger logger = Logger.getLogger(PrincipalCoreAction.class);
	
	protected Map<String,Object> session;
	
	/*
	@SuppressWarnings("rawtypes")
	protected Map request;
    
    @SuppressWarnings("rawtypes")
    public void setRequest(Map request) {
        this.request = request;
    }
    */
	
	@Override
	public void prepare() throws Exception {
			
	}

	public Map<String,Object> getSession() {
		return session;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}
	
}