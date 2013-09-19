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
 * Interceptor que proporciona autenticacion para acciones seguras de la aplicacion.
 * <br/><br/>
 * Primero, verifica en la sesion web si existe el usuario (si ya está firmado).
 * Si no está presente, el interceptor altera el flujo de la petición regresando una
 * cadena de control que causa que la peticion se redirija a la pagina de login.
 * <br/><br/>
 * Si el objeto del usuario está presente en el mapa de sesion, entonces el interceptor
 * inyecta el objeto usuario dentro del action a traves de setUser
 * y permite que continue el procesamiento de la peticion.
 *
 */
public class AuthenticationInterceptor implements Interceptor {

	private static final long serialVersionUID = 4074812194873811352L;

	protected final transient Logger logger = Logger.getLogger(AuthenticationInterceptor.class);
	
	public void init() {
	}

	public String intercept(ActionInvocation actionInvocation) throws Exception {

		logger.debug("Action intercepted="+ actionInvocation.getProxy().getActionName());
		
		//Obtenemos la sesion por medio del ActionInvocation
		Map session = actionInvocation.getInvocationContext().getSession();
		
		limpiarTokensViejos(session);
		
		UserVO user = (UserVO) session.get(Constantes.USER);
		
		//Si el usuario no existe en sesion, detenemos el action y redirigimos al result global INPUT
		if (user == null) {
		    return Action.LOGIN;
		} else { //Si el usuario ya está en sesion (logged) se continua el flujo de ejecucion a otros interceprotes y luego al action
				
			/* TODO: Implementar en los actions que necesiten al usuario
		    Action action = ( Action ) actionInvocation.getAction();
		    if (action instanceof UserAware) {
		        ((UserAware)action).setUser(user);
		    }
		    */
			if(Action.LOGIN.equalsIgnoreCase(actionInvocation.getInvocationContext().getName())){
				
				boolean rolAsignado = false;
				
				if(user.getRolActivo()!=null){
					logger.debug("Rol Activo: "+user.getRolActivo().getObjeto());
					if(user.getRolActivo().getObjeto()!=null && StringUtils.isNotBlank(user.getRolActivo().getObjeto().getValue()))
						rolAsignado = true;
				}
				
				if(rolAsignado){
					logger.debug("-->Ya Contiene un Rol Asignado<--");
					return "load";
				}else{
					return "tree";
				}
				
			}
			
			
			/**TODO: agregar logica para redireccion del usuario al login, arbol, menu o pagina solicitada **/
//			if(rolAsignado){
//				
//				
//			}else {
//				
//			}
			
		    
		    /*
		     * We just return the control string from the invoke method.  If we wanted, we could hold the string for
		     * a few lines and do some post processing.  When we do return the string, execution climbs back out of the 
		     * recursive hole, through the higher up interceptors, and finally arrives back at the actionInvocation itself,
		     * who then fires the result based upon the result string returned.
		     */
		    return actionInvocation.invoke();
		}

	}
	
	public void destroy() {
	}
	
	
	/**
	 * Eliminamos de sesion la variable del usuario si el valor de esta
	 * no corresponde a una instancia del objeto UserVO
	 * @param session
	 */
	private void limpiarTokensViejos(Map session){
		Object userToken = session.get(Constantes.USER);
		if ( !( userToken instanceof UserVO ) )session.remove(Constantes.USER);		
	}
	
}