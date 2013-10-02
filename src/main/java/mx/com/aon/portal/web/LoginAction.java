/*
 * AON
 * 
 * Creado el 24/01/2008 11:33:52 a.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.portal.web;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.LoginManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * LoginAction
 * 
 * <pre>
 *  Action para validar usuario y consultar su perfil
 * &lt;Pre&gt;
 * 
 * &#064;author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&amp;aacuterez&lt;/a&gt;
 * &#064;version	 1.0
 * 
 * &#064;since	 1.0
 * 
 */
public class LoginAction extends ActionSupport implements SessionAware {

	/**
	 * UID por defecto
	 */
	private static final long serialVersionUID = 8106870262330598802L;
	private static final transient Log log = LogFactory.getLog(LoginAction.class);
	private String user;
	private String password;

	private LoginManager loginManager;

	// Respuesta para JSON
	private boolean success;

	@SuppressWarnings("unchecked")
	// Manejo de Map controlado
	private Map session;

	@SuppressWarnings("unchecked")
	// Manejo de Map controlado
	public String execute() throws Exception {
		UserVO userVO = new UserVO();

/*
		try {
			userVO = loginManager.validaUsuario(user, password);
			userVO.setUser(userVO.getName());
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
*/

/*
		try {
			//Agregado para obetner usuario de OID
			//@author rruiz CIMA			
			HttpServletRequest request = ServletActionContext.getRequest();
			Principal principal = request.getUserPrincipal();
			userVO.setUser(principal.getName());
			//userVO = loginManager.validaUsuario(user, password);
			
			
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
*/
		/**
		 * Esta session debe ser borrada.
		 */
//		session.put("userVO", userVO);
		/**/
		
		
/*
		session.put("USUARIO", userVO);
		UserVO userVO2 = (UserVO) session.get("userVO");
*/
		
		success = true;
		return SUCCESS;
	}

	public String loginUser() throws Exception{
		
		UserVO userVO = null;
		try {
			userVO = loginManager.validaUsuario(user, password);
			userVO.setUser(userVO.getName());
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}		
		session.put("USER", userVO);
		
		success = true;
		return SUCCESS;
	}
	/**
	 * Metodo que elimina la session del usuario
	 * 
	 * @return success
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")// Manejo de Map controlado
	public String logOff() throws Exception {
		session.put("userVO", null);
		return SUCCESS;
	}

	// Get ters and Setters
	
	public String getUser() {
		return user;
	}
	/**
	 * 
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 
	 * @return
	 */
	public boolean getSuccess() {
		return success;
	}
	/**
	 * 
	 * @param success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * Agregado al implementar SessionAware para manejo de atributos de session
	 */
	@SuppressWarnings("unchecked")// Manejo de Map controlado
	public void setSession(Map session) {
		this.session = session;
	}

	/**
	 * Insercion del Manager para consulta y validacion de usuario
	 * 
	 * @param loginManager
	 */
	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

}
