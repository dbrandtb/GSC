/*
 * AON
 * 
 * Creado el 24/01/2008 11:36:24 a.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import mx.com.aon.portal.model.PerfilVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.LoginManager;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

/**
 * LoginManagerImpl
 * 
 * <pre>
 *  Implementacion del manager para la autentificacion del usuario y consulta de perfil
 *  de la pantalla principal
 * <Pre>
 * 
 * @author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&aacute;rez&lt;/a&gt;
 * @version	 1.0
 * 
 * @since	 1.0
 * 
 */
public class LoginManagerImpl implements LoginManager {

	private static Logger logger = Logger.getLogger(LoginManagerImpl.class);
	
	private Map<String, Endpoint> endpoints;
	
	/**
	 * Funcion que valida el usuario y password pasados por parametro y en caso de ser valido
	 * consulta el perfil de la pagina principal para agregarlo a la sesion.
	 * 
	 * @param user - Identificador del usuario
	 * @param password - Constrasenia del usuario
	 * @return UserVO Bean con la informacion del usuario
	 * @throws ApplicationException Es lanzada en errores de configuracion de aplicacion error en las consultas a BD
	 */
	public UserVO validaUsuario(String user, String password) throws ApplicationException{
		
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("user", user);
		mapa.put("password", password);

		UserVO userVO = null;
		try {

			Endpoint manager = (Endpoint)endpoints.get("VALIDA_USUARIO");
			userVO = (UserVO)manager.invoke(mapa);
			
			if( userVO == null ){
				throw new ApplicationException("Usuario y/o password no validos");
			}
			
			manager = (Endpoint)endpoints.get("CONSULTA_PERFIL");
			userVO.setFuentesPerfil( (PerfilVO)manager.invoke(userVO.getPerfil()) );

		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'Validacion usuario'",bae);
			throw new ApplicationException("Error al validar al usuario en el sistema");
		}
		
		return userVO;
	}

	// Getters and Setters
	
	public void setEndpoints(Map<String, Endpoint> endpoints) {this.endpoints = endpoints;}


    /**
     * Funcion que valida el usuario y password pasados por parametro y en caso de ser valido
     * consulta el perfil de la pagina principal para agregarlo a la sesion.
     *
     * @param user - Identificador del usuario
     * @return UserVO Bean con la informacion del usuario
     * @throws ApplicationException Es lanzada en errores de configuracion de aplicacion error en las consultas a BD
     */
    public UserVO obtenerDatosUsuario(String user) throws ApplicationException{

        Map<String, String> mapa = new HashMap<String, String>();
        mapa.put("user", user);

        UserVO userVO = null;
        //try {
            if( user == null || user.equals("") ){
                throw new ApplicationException("Usuario y/o password no validos");
            }


            userVO = new UserVO();
            //todo revisar despues esto como queda
            userVO.setUser(user);
            userVO.setCodigoPersona(user);
            userVO.setName(user);
            userVO.setLastName(user);
            userVO.setPerfil("DEFAULT");

            //TODO: Probar quitando el siguiente codigo para intentar mejorar el performance: 
            PerfilVO perfil = new PerfilVO();
            perfil.setFooterCenter("/resources/static/infoTit.html");
            perfil.setFooterLeft("/resources/static/infoTit.html");
            perfil.setFooterRight("/resources/static/infoTit.html");
            perfil.setKnewthat("/resources/static/infoTit.html");
            perfil.setLeft_1("/resources/static/navTit.html");
            perfil.setLeft_2("/resources/static/infoTit.html");
            perfil.setLeft_3("/resources/static/infoTit.html");
            perfil.setLeft_4("/resources/static/infoTit.html");
            perfil.setLeft_5("/resources/static/infoTit.html");
            perfil.setMain("/resources/static/infoTit.html");
            perfil.setMainDown("/resources/static/infoTit.html");
            perfil.setNav("");
            perfil.setNews("/resources/static/infoTit.html");
            perfil.setOtherLeft("/resources/static/infoTit.html");
            perfil.setOtherRight("/resources/static/infoTit.html");
            perfil.setOthers("/resources/static/infoTit.html");
            perfil.setRight_1("/resources/static/infoTit.html");
            perfil.setRight_2("/resources/static/infoTit.html");
            perfil.setRight_3("/resources/static/infoTit.html");
            perfil.setRight_4("/resources/static/infoTit.html");
            perfil.setRight_5("/resources/static/infoTit.html");
            perfil.setTop("/resources/static/info.html");
            perfil.setTopCenter("/resources/static/infoTit.html");
            perfil.setTopLeft("/resources/static/infoTit.html");
            perfil.setTopRight("/resources/static/infoTit.html");
            

            //Endpoint manager = (Endpoint)endpoints.get("CONSULTA_PERFIL");
            userVO.setFuentesPerfil(perfil);

        //} catch (BackboneApplicationException bae) {
          //  logger.error("Exception in invoke 'Validacion usuario'",bae);
           // throw new ApplicationException("Error al validar al usuario en el sistema");
        //}

        return userVO;
    }


}
