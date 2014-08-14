package mx.com.gseguros.interceptors;

import java.util.Map;

import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.utils.Constantes;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * <p>Interceptor que proporciona autenticaci&oacute;n para acciones seguras de la aplicaci&oacute;n.<p/>
 * 
 * <p>Primero, verifica en la sesion web si existe el usuario (si ya est&aacute; firmado).
 * Si no est&aacute; presente, el interceptor altera el flujo de la petici&oacute;n regresando una
 * cadena de control que causa que la peticion se redirija a la p&aacute;gina de login.</p>
 * 
 * <p>Si el objeto del usuario est&aacute; presente en el mapa de sesi&oacute;n, entonces el interceptor
 * inyecta el objeto usuario dentro del action a traves de setUser
 * y permite que contin&uacute;e el procesamiento de la petici&oacute;n.<p/>
 */
public class AuthenticationInterceptor implements Interceptor {

	private static final long serialVersionUID = 4074812194873811352L;

	protected final transient Logger logger = Logger.getLogger(AuthenticationInterceptor.class);
	
	public void init() {
	}

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		
		logger.info(new StringBuilder("Intercepted[Namespace=").append(actionInvocation.getProxy().getNamespace())
				.append(" ActionName=").append(actionInvocation.getProxy().getActionName()).append("]"));
		
		//Obtenemos la sesion por medio del ActionInvocation
		Map session = actionInvocation.getInvocationContext().getSession();
		
		UserVO user = (UserVO) session.get(Constantes.USER);
		if(user != null) {
			logger.info(new StringBuilder("Usuario en sesion: ").append(user.getUser()));
		} else {
			logger.info("No hay usuario en sesion");
		}
		
		boolean esMovil     = false;
		String  sufijoMovil = "";
		if(session.get("ES_MOVIL")!=null&&(Boolean)session.get("ES_MOVIL")) {
			esMovil     = true;
			sufijoMovil = "_movil";
		}
		
		if(Action.LOGIN.equalsIgnoreCase(actionInvocation.getInvocationContext().getName())) {
			logger.info("LOGIN");
			if (user == null) {
				//no hay usuario en sesion, se va al formulario
				logger.info("return login loginform;");
			    return "loginform"+sufijoMovil;
			} else {
				boolean rolAsignado = false;
				if(user.getRolActivo()!=null) {
					if(user.getRolActivo().getObjeto()!=null && StringUtils.isNotBlank(user.getRolActivo().getObjeto().getValue())) {
						rolAsignado = true;
						logger.info(new StringBuilder("Rol Activo: ").append(user.getRolActivo().getObjeto().getValue())
								.append(" - ").append(user.getRolActivo().getObjeto().getLabel()));
					}
				}
				if(!rolAsignado) {
					//entro al login pero ya tenia usuario en sesion sin rol
					logger.info("return login tree;");
					return "tree"+sufijoMovil;
				} else {
					//entro al login pero ya tenia usuario y rol
					logger.info("-->Ya Contiene un Rol Asignado<--");
					logger.info("return login load;");
				    return "load"+sufijoMovil;
				}
			}
		} else if(actionInvocation.getInvocationContext().getName().equalsIgnoreCase("seleccionaRolCliente")) {
			//se mando al arbol o quiere entrar desde el arbol
			logger.info("TREE (Arbol de seleccion de rol)");
			if (user == null) {
				//no hay usuario en sesion, se redirige a login (no al loginform)
				logger.info("return tree login");
			    return Action.LOGIN;
			} else {
				//boolean rolAsignado = false;
				if (user.getRolActivo() != null && user.getRolActivo().getObjeto() != null
						&& StringUtils.isNotBlank(user.getRolActivo().getObjeto().getValue())) {
					//rolAsignado = true;
					logger.info("-->Ya Contiene un Rol Asignado<--");
					logger.info(new StringBuilder("Rol Activo: ").append(user.getRolActivo().getObjeto().getValue())
							.append(" - ").append(user.getRolActivo().getObjeto().getLabel()));
				}
				logger.info("return tree invoke (jsp)");
				if(!esMovil) {
					return actionInvocation.invoke();
				} else {
					return "tree_movil";
				}
			}
		} else { //quiere acceder a otra url
			logger.info("INVOKE");
			if (user == null) {
				//no hay usuario en sesion, se redirige a login (no al loginform)
				logger.info("return invoke login");
			    return Action.LOGIN;
			} else {
				boolean rolAsignado = false;
				if(user.getRolActivo()!=null) {
					if(user.getRolActivo().getObjeto()!=null && StringUtils.isNotBlank(user.getRolActivo().getObjeto().getValue())) {
						rolAsignado = true;
						logger.info(new StringBuilder("Rol Activo: ").append(user.getRolActivo().getObjeto().getValue())
								.append(" - ").append(user.getRolActivo().getObjeto().getLabel()));
					}
				}
				if(!rolAsignado) {
					//quiere acceder pero no selecciono rol
					logger.info("return invoke tree");
					return "tree"+sufijoMovil;
				} else {
					//tiene usuario y rol correctos
					logger.info("return invoke invoke");
					return actionInvocation.invoke();
				}
			}
		}
	}
	
	public void destroy() {
	}
	
}