package mx.com.aon.portal.web;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;

import mx.com.aon.portal.model.IsoVO;
import mx.com.aon.portal.model.RamaVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.LoginManager;
import mx.com.aon.portal.service.NavigationManager;
import mx.com.gseguros.utils.Constantes;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AutenticacionAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 0L;

	protected final transient Logger logger = Logger.getLogger(AutenticacionAction.class);

	private String user;
	private String password;
	private String decimalSeparator;
	private String dateFormat;
	private LoginManager loginManager;
	private NavigationManager navigationManager;
	private List<RamaVO> listaRolCliente;
	private boolean success;
	private String errorMessage;

	@SuppressWarnings("unchecked")
	protected Map session;

	public String execute() throws Exception {
		logger.debug("Entrando a execute");
		return INPUT;
	}

	
	public String existeUsuarioLDAP() throws Exception {
		try {
			
			success = loginManager.validaUsuarioLDAP(true, user, password);
			//logger.debug(new StringBuilder("Existe usuario '").append(user).append("' en LDAP? ").append(success));
			return SUCCESS;

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			errorMessage = ex.getMessage();
			return SUCCESS;
		}
	}
	
	public String autenticaUsuario() throws Exception {
		/**
		 * Si es true agrega los usuarios en LDAP si ya existen previamente en BD (flujo temporal), <br/> false si solo autentica en LDAP y BD (flujo correcto)
		 */
		if(new Boolean(getText("login.modo.agregar.usuarios.ldap"))){
			return autenticaUsuarioAgregaLDAP();
		}
		
		try {
			/**
			 * TODO: descomentar cuado ya se vaya a validar el password de los usuarios
			 */
			boolean existeUsuario = true;//loginManager.validaUsuarioLDAP(false, user, password);
			if (existeUsuario) {
				logger.info("Usuario "+user+" ha sido valido exitosamente en LDAP, creando sesion...");
				success = creaSesionDeUsuario(user);
			} else {
				logger.info("El usuario "+user+" no existe o la clave es incorrecta.");
				errorMessage = "El usuario no existe o la clave es incorrecta";
			}
			return SUCCESS;

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			errorMessage = "Error en el proceso de validaci&oacute;n de usuario. Consulte a Soporte T&eacute;cnico.";
			return SUCCESS;
		}
	}

	
	public String autenticaUsuarioAgregaLDAP() throws Exception {
		try {
			boolean sesionUsuarioCreada = creaSesionDeUsuario(user);
			if (sesionUsuarioCreada) {
				boolean existeUsuarioLDAP = loginManager.validaUsuarioLDAP(true, user, password);
				
				if(existeUsuarioLDAP){
					logger.info("Usuario "+user+" si existe en LDAP, validando Password...");
					boolean validPass = loginManager.validaUsuarioLDAP(false, user, password);
					if(!validPass){
						((SessionMap) session).invalidate();
						logger.info("Password Incorrecto!!! "+user+"/"+password);
						errorMessage = "El usuario no existe o la clave es incorrecta";
					}else {
						logger.info("Password Correcto, redireccionando a menu de Roles...");
						success = true;
					}
				}else {
					logger.info("No existe usuario, Insertando usuario "+user+"/"+password+" en LDAP");
					loginManager.insertaUsuarioLDAP(user, password);
					logger.info("Usuario Creado en LDAP, redireccionando a menu de Roles...");
					success =  true;
				}
				
			} 
			return SUCCESS;

		} catch (AuthenticationException ax) {
			logger.info(ax.getMessage());
			errorMessage = "Error en el proceso de validaci&oacute;n de usuario. Consulte a Soporte T&eacute;cnico.";
			return SUCCESS;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			errorMessage = "Error en el proceso de validaci&oacute;n de usuario. Consulte a Soporte T&eacute;cnico.";
			return SUCCESS;
		}
	}

	private boolean creaSesionDeUsuario(String usuario) throws Exception {

		boolean exito = false;

		UserVO userVO = new UserVO();
		userVO.setUser(usuario);
		userVO = loginManager.obtenerDatosUsuario(usuario);

		userVO.setDecimalSeparator(decimalSeparator);
		IsoVO isoVO = navigationManager.getVariablesIso(userVO.getUser());

		userVO.setClientFormatDate(isoVO.getClientDateFormat());
		userVO.setFormatDate(dateFormat);
		userVO.setDecimalSeparator(isoVO.getFormatoNumerico());

		session.put(Constantes.USER, userVO);
		session.put("userVO", userVO);

		listaRolCliente = navigationManager.getClientesRoles(userVO.getUser());

		if (listaRolCliente == null || listaRolCliente.isEmpty()) {
			session.clear();
			errorMessage = "Usted no posee un rol asociado, por favor contacte al administrador";
		} else {
			exito = true;
		}
		return exito;
	}
	
	public String logoutUsuario() throws Exception {
		try {
			logger.debug(":X :X :X Terminando Session X: X: X:"+ ServletActionContext.getRequest().getSession().getId());
			
			//session.clear();
			((SessionMap) session).invalidate();
			logger.debug("session="+ ActionContext.getContext().getSession());
			return SUCCESS;
		} catch (Exception ex) {
			logger.error("Error al terminar la sesion", ex);
			errorMessage = "Error al terminar la sesion";
			return SUCCESS;
		}
	}


	/**
	 * Action temporal para GSeguros, que redirigira a la pantalla de cotizacion
	 * Salud Vital
	 * 
	 * @return
	 * @throws Exception
	 */
	public String cotizacionSaludVital() throws Exception {
		
		try {
			creaSesionDeUsuario(user);
		} catch (Exception ex) {
			logger.error("Error en el proceso Interno", ex);
			errorMessage = "Error en el proceso Interno";
		}
		
		return SUCCESS;
	}


	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	public void setNavigationManager(NavigationManager navigationManager) {
		this.navigationManager = navigationManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
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
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}