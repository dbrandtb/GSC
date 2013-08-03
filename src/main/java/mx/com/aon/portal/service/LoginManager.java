/*
 * AON
 * 
 * Creado el 24/01/2008 11:35:11 a.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.UserVO;

/**
 * LoginManager
 * 
 * <pre>
 *  Interfaz Manager para la validacion y consulta de acceso a la aplicacion
 * <Pre>
 * 
 * @author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&aacute;rez&lt;/a&gt;
 * @version	 1.0
 * 
 * @since	 1.0
 * 
 */
public interface LoginManager {
	
	/**
	 * Autentifica y extrae la informacion correspondiente al usuario a validar
	 * @param user
	 * @param password
	 * @return
	 * @throws ApplicationException
	 */
	UserVO validaUsuario(String user, String password) throws ApplicationException;


    /**
     *   extrae la informacion correspondiente al usuario a validar
     * @param user
     * @return
     * @throws ApplicationException
     */
    UserVO obtenerDatosUsuario(String user) throws ApplicationException;

}
