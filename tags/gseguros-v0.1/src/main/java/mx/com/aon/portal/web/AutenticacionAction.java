package mx.com.aon.portal.web;

import java.util.List;

import javax.naming.AuthenticationException;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.IsoVO;
import mx.com.aon.portal.model.RamaVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.LoginManager;
import mx.com.aon.portal.service.NavigationManager;
import mx.com.gseguros.utils.Constantes;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

public class AutenticacionAction extends PrincipalCoreAction {

	private static final long serialVersionUID = 0L;

	protected final transient Logger logger = Logger.getLogger(AutenticacionAction.class);

	private String user;
	private String password;
	private String passwordNuevo;
	private String decimalSeparator;
	private String dateFormat;
	private LoginManager loginManager;
	private NavigationManager navigationManager;
	@Autowired
	private mx.com.gseguros.portal.general.service.NavigationManager navigationManagerNuevo;
	private List<RamaVO> listaRolCliente;
	private boolean success;
	private String errorMessage;

	public String execute() throws Exception {
		logger.debug("Entrando a execute");
		return INPUT;
	}

	
	public String existeUsuarioLDAP() throws Exception {
		try {
			success = loginManager.validaUsuarioLDAP(true, user, password);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			errorMessage = "Error al validar credenciales de usuario en el servidor: " + ex.getMessage();
		}
		return SUCCESS;
	}
	
	
	public String autenticaUsuario() throws Exception {
		/**
		 * Si es true agrega los usuarios en LDAP si ya existen previamente en BD (flujo temporal), <br/> false si solo autentica en LDAP y BD (flujo correcto)
		 */
		if(new Boolean(getText("login.modo.agregar.usuarios.ldap"))){
			logger.debug("Autentificacion,entrando a modo Agregar Usuarios a LDAP");
			return autenticaUsuarioAgregaLDAP();
		}
		
		logger.debug("Autentificacion,sin entrar a modo Agregar Usuarios a LDAP");
		
		try {
			
			boolean existeUsuario = true;
			if(new Boolean(getText("login.auth.ldap.activa"))){
				existeUsuario = loginManager.validaUsuarioLDAP(false, user, password); 
			}
			if (existeUsuario) {
				logger.info("Usuario "+user+" ha sido valido exitosamente en LDAP, creando sesion...");
				success = creaSesionDeUsuario(user);
			} else {
				logger.info("El usuario "+user+" no existe o la clave es incorrecta.");
				errorMessage = "El usuario no existe o la contraseña es incorrecta";
			}
			return SUCCESS;

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			//errorMessage = "Error en el proceso de validaci&oacute;n de usuario. Consulte a Soporte T&eacute;cnico.";
			errorMessage = "Error en el proceso de validaci&oacute;n de usuario, detalle: " + ex.getMessage(); //Se agrega el mensaje de la Excepción en Pantalla
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
						errorMessage = "El usuario no existe o la contraseña es incorrecta";
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

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			errorMessage = "Error en el proceso de validaci&oacute;n de usuario, detalle: " + ex.getMessage();
			return SUCCESS;
		}
	}
	
	
	private boolean creaSesionDeUsuario(String usuario) throws Exception {

		boolean exito = false;

		UserVO userVO = new UserVO();
		userVO.setUser(usuario);
		userVO = loginManager.obtenerDatosUsuario(usuario);

		userVO.setDecimalSeparator(decimalSeparator);
		IsoVO isoVO = navigationManagerNuevo.getVariablesIso(userVO.getUser());

		userVO.setClientFormatDate(isoVO.getClientDateFormat());
		userVO.setFormatDate(dateFormat);
		userVO.setDecimalSeparator(isoVO.getFormatoNumerico());

		session.put(Constantes.USER, userVO);
		session.put("userVO", userVO);

		//TODO: cambiar a nuevo manager *****
		listaRolCliente = navigationManager.getClientesRoles(userVO.getUser());
		
		logger.debug("listaRolCliente =====" + listaRolCliente);

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
	 * Petici&oacute;n para mantener la sesi&oacute;n del servidor 
	 * @return
	 * @throws Exception
	 */
	public String mantenerSesionActiva() throws Exception {
		return SUCCESS;
	}
	
	
	/*
	 * Action temporal para GSeguros, que redirigira a la pantalla de cotizacion
	 * Salud Vital
	 * 
	 * @return
	 * @throws Exception
	 */
	/*
	public String cotizacionSaludVital() throws Exception {
		
		try {
			creaSesionDeUsuario(user);
		} catch (Exception ex) {
			logger.error("Error en el proceso Interno", ex);
			errorMessage = "Error en el proceso Interno";
		}
		
		return SUCCESS;
	}
	*/
	
	
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

	
	public String getPasswordNuevo() {
		return passwordNuevo;
	}

	public void setPasswordNuevo(String passwordNuevo) {
		this.passwordNuevo = passwordNuevo;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public void setNavigationManagerNuevo(
			mx.com.gseguros.portal.general.service.NavigationManager navigationManagerNuevo) {
		this.navigationManagerNuevo = navigationManagerNuevo;
	}
	
	

}