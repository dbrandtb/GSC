package mx.com.aon.portal.web;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class AutenticacionAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 0L;
	
	protected final transient Logger logger = Logger.getLogger(AutenticacionAction.class);
	
	private String user;
	
	private String password;

	@SuppressWarnings("unchecked")
	protected Map session;

	public String execute() throws Exception {
		logger.debug("Entrando a execute");
		return INPUT;
	}
	
	
	public String autenticaUsuario() throws Exception {
		logger.debug("ENtrando a autenticaUsuario");
		
		return INPUT;
	}
	
	
	
	
	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;		
	}
	
}