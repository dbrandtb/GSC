package mx.com.aon.portal.web;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@SuppressWarnings("unchecked")
	protected Map session;

	public String execute() throws Exception {
		logger.debug("Entrando a execute");
		return INPUT;
	}

	public String autenticaUsuario() throws Exception {
		try {
			//boolean existeUsuario = validaUsuarioLdap(user, password);
			boolean existeUsuario = true;
			if (!existeUsuario) {
				//insertaRegistroLdap(user, password);
				errorMessage = "El usuario no existe o la clave es incorrecta";
			} else {
				success = creaSesionDeUsuario(user);
			}
			return SUCCESS;

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			errorMessage = ex.getMessage();
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
			errorMessage = "EXITO";
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

	// m�todo para insertar el registro en ldap
	private boolean insertaRegistroLdap(String user, String password) throws Exception {
		DirContext ctx;
		Hashtable env = obtenerDatosConexionLDAP(this.getText("ldap.security.principal"), this.getText("ldap.security.credentials"));
		logger.debug(env);
		try {
			ctx = new InitialLdapContext(env, null);
			Attributes matchAttrs = new BasicAttributes(true);
			matchAttrs.put(new BasicAttribute("uid", user));
			matchAttrs.put(new BasicAttribute("cn", user));
			matchAttrs.put(new BasicAttribute("sn", user));
			matchAttrs.put(new BasicAttribute("userpassword", password.getBytes("UTF8")));
			matchAttrs.put(new BasicAttribute("objectclass", "top"));
			matchAttrs.put(new BasicAttribute("objectclass", "person"));
			matchAttrs.put(new BasicAttribute("objectclass", "organizationalPerson"));
			matchAttrs.put(new BasicAttribute("objectclass", "inetorgperson"));
			String name = "cn=" + user +","+this.getText("ldap.base.search");
			InitialLdapContext iniDirContext = (InitialLdapContext) ctx;
			iniDirContext.bind(name, ctx, matchAttrs);
			return true;
		} catch (NamingException e) {
			throw new Exception("Error en el proceso de validaci&oacute;n de usuario",e);
		}
    }

	public boolean validaUsuarioLdap(String user, String password) throws Exception {
		DirContext ctx;
		logger.debug("URLLDAP=" +        this.getText("ldap.provider.url"));
		logger.debug("ContextoLDAP=" +   this.getText("ldap.factory.initial"));
		logger.debug("TipoAuthLDAP=" +   this.getText("ldap.security.authentication"));
		logger.debug("UsuarioLDAP=" +    this.getText("ldap.security.principal"));
		logger.debug("PasswordLDAP=" +   this.getText("ldap.security.credentials"));
		logger.debug("SearchBaseLDAP=" + this.getText("ldap.base.search"));
		
		Hashtable env = obtenerDatosConexionLDAP(this.getText("ldap.security.principal"), this.getText("ldap.security.credentials"));
		boolean existeUsuario = false;
		try {
			 ctx = new InitialLdapContext(env, null);
		} catch (NamingException e) {
			throw new Exception("Error de conexi&oacute;n",e);
			//logger.error("Error en el proceso de validacion de usuario", e);
		}
			
		try{
				SearchControls searchCtls = new SearchControls();
				String returnedAtts[] = { "uid", "sn", "givenName", "mail", "cn" };
				searchCtls.setReturningAttributes(returnedAtts);
				searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
				String searchFilter = "(cn="+user+")";
				// String searchBase = "cn=Users,dc=biosnettcs,dc=com";
				NamingEnumeration<SearchResult> results = ctx.search(
						this.getText("ldap.base.search"), searchFilter, searchCtls);
				while (results.hasMoreElements()) {
					SearchResult searchResult = (SearchResult) results.next();
					Attributes attrs = searchResult.getAttributes();
					// OBTENEMOS LA UNIDAD ORGANIZATIVA DEL UID BUSCADO CON SU UID Y
					// LO COMPLETAMOS CON LA BASE
					String dn = searchResult.getName() + ","
							+ this.getText("ldap.base.search");
	
					if (attrs != null) {
						// EL UID EXISTE AHORA VALIDAR PASSWORD
						existeUsuario = validarAuth(dn, password);
						// SI VALIDO ES false PASSWORD INCORRECTO, SI ES true
						// PASSWORD CORRECTO
					}
				}
				ctx.close();
			}
			catch(Exception err)
			{
				logger.error("Error en el proceso de validacion de usuario", err);
			}
		return existeUsuario;
	}

	private boolean validarAuth(String dn, String password) {
		boolean validadausuario = false;
		Hashtable env1 = obtenerDatosConexionLDAP(dn, password);
		try {
			DirContext ctx1 = new InitialLdapContext(env1, null);
			validadausuario = true;
			ctx1.close();
		} catch (NamingException e) {
			logger.error("Error en la validacion LDAP", e);
		}
		return validadausuario;
	}

	private Hashtable obtenerDatosConexionLDAP(String user, String pass) {
		Hashtable<String, String> env = new Hashtable<String, String>();
		try {
			env.put(Context.INITIAL_CONTEXT_FACTORY, this.getText("ldap.factory.initial"));
			env.put(Context.SECURITY_AUTHENTICATION, this.getText("ldap.security.authentication"));
			env.put(Context.SECURITY_PRINCIPAL, user);
			env.put(Context.SECURITY_CREDENTIALS, pass);
			env.put(Context.PROVIDER_URL, this.getText("ldap.provider.url"));

		} catch (Exception e) {
			logger.error("Error en la creacion de conexion LDAP", e);
		}
		return env;
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

}