package mx.com.aon.configurador.builder.web;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@Deprecated
public abstract class PrincipalBuilderAction extends ActionSupport implements
		Preparable, SessionAware {
	
	
	protected final transient Logger logger = Logger.getLogger(PrincipalBuilderAction.class);
	
	@SuppressWarnings("unchecked")
	protected Map session;

	/**
	 * 
	 */
	public void prepare() throws Exception {

	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session  = session;
	}

}
