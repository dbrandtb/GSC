/**
 * 
 */
package mx.com.aon.portal.service.impl.menuusuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.MensajesVO;
import mx.com.aon.portal.model.menuusuario.ConfiguracionMenuVO;
import mx.com.aon.portal.model.menuusuario.MenuVO;
import mx.com.aon.portal.model.opcmenuusuario.UsuarioVO;
import mx.com.aon.portal.model.principal.ClienteVO;
import mx.com.aon.portal.model.principal.RolVO;
import mx.com.aon.portal.model.principal.RolesVO;
import mx.com.aon.portal.model.principal.SeccionVO;
import mx.com.aon.portal.model.principal.TipoVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.service.menuusuario.MenuUsuarioManager;

import org.apache.log4j.Logger;

import mx.com.aon.tmp.Endpoint;
import mx.com.aon.tmp.BackboneApplicationException;

/**
 * @author eflores
 * @date 20/05/2008
 *
 */
public class MenuUsuarioManagerImpl extends AbstractManager implements MenuUsuarioManager {
    private static Logger logger = Logger.getLogger(MenuUsuarioManagerImpl.class);

    /*@SuppressWarnings("unchecked")
    private Map endpoints;

    @SuppressWarnings("unchecked")
    public void setEndpoints(Map endpoints) {
        this.endpoints = endpoints;
    }*/
    /**
     * Metodo que regresa la lista de configuracion Menu Usuario existente en la base de Datos.
     */
    @SuppressWarnings("unchecked")// Map Control
    public PagedList getMenuUsuarios(String nombre, String cliente, String rol, String usuario,int start,int limit) 
            throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("-> MenuUsuarioManagerImpl.getMenuUsuarios");
        }
        Map params = new HashMap<String, String>();
        params.put("nombre", nombre);
        params.put("cliente", cliente);
        params.put("usuario", usuario);
        params.put("rol", rol);
        PagedList menuList = null;

        try {
            menuList = this.getPagedList(params, "OBTIENE_MENU_USUARIO", start, limit);
        }catch (Exception e) {
            logger.error("getMenuUsuarios -> Backbone exception", e);
        }
        return menuList;
    }
    /**
     * Metodo que regresa la lista de configuraciones existentes en la base de Datos.
     */
    @SuppressWarnings("unchecked")// Map Control
    public ArrayList<RolVO> getRoles(String rol, String cliente, String seccion) throws ApplicationException {
        Map params = new HashMap<String, String>();
        params.put("rol", rol);
        params.put("cliente", cliente);
        params.put("seccion", seccion);
        ArrayList<RolVO> roles = null;

        try {
            Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_ROLES");
            roles = (ArrayList<RolVO>) endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            logger.error("getRoles -> Backbone exception", e);
            //throw new ApplicationException("Error al recuperar los datos");

        }
        return (ArrayList<RolVO>) roles;
    }
    
    /**
     * Metodo que regresa lista de usuarios existentes en la base.
     */
    @SuppressWarnings("unchecked")// Map Control
    public ArrayList<UsuarioVO> getUsuarios() throws ApplicationException {
        Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_USUARIOS_MENU_USUARIO");
        ArrayList<UsuarioVO> usuarios = null;
        try {
            usuarios = (ArrayList<UsuarioVO>) endpoint.invoke(null);
            if (usuarios != null && !usuarios.isEmpty()) {
                logger.debug("lista de usuarios llena");
            } else {
                logger.debug("lista de usuarios vacia");
            }
        } catch (BackboneApplicationException e) {
            logger.error("getUsuarios -> Backbone exception", e);
            //throw new ApplicationException("Error regresando lista de usuarios: " + e);
        }
        return (ArrayList<UsuarioVO>) usuarios;
    }
    
    
    /* (non-Javadoc)
     * @see mx.com.aon.portal.service.menuusuario.MenuUsuarioManager#getUsuarios(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")// Map Control
    public ArrayList<UsuarioVO> getUsuariosNR(String cdElemento, String cdRol) throws ApplicationException {
    	
    	logger.debug("-------------------------");
    	logger.debug(">>>>>>> En getUsuariosNR()");
    	logger.debug(">>>>>>> cdElemento ::: "+cdElemento);
    	logger.debug(">>>>>>> rol ::: "+cdRol);
    	logger.debug("-------------------------");
    	
    	Map params = new HashMap<String, String>();
    	params.put("cdelemento", cdElemento);
    	params.put("cdrol", cdRol);
    	
    	logger.debug(">>>>>>> Se llama al endpoint OBTIENE_USUARIOS_X_NIVEL_ROL");
        Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_USUARIOS_X_NIVEL_ROL");
        ArrayList<UsuarioVO> usuarios = null;
        try {
            usuarios = (ArrayList<UsuarioVO>) endpoint.invoke(params);
            if (usuarios != null && !usuarios.isEmpty()) {
                logger.debug("lista de usuarios por nivel y rol, llena");
            } else {
                logger.debug("lista de usuarios por nivel y rol, vacia");
            }
        } catch (BackboneApplicationException e) {
            logger.error("getUsuariosNR -> Backbone exception", e);
            //throw new ApplicationException("Error regresando lista de usuarios x nivel rol: " + e);
        }
        return (ArrayList<UsuarioVO>) usuarios;
    }
    
    /**
     * Metodo que regresa lista de clientes existentes en la base.
     */
    @SuppressWarnings("unchecked")// Map Control
    public ArrayList<ClienteVO> getListaCliente() throws ApplicationException {
        Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_CLIENTES_MENU_USUARIO");
        ArrayList<ClienteVO> clientes = null;
        try {
            clientes = (ArrayList<ClienteVO>) endpoint.invoke(null);
            if (clientes != null && !clientes.isEmpty()) {
                logger.debug("lista de clientes llena");
            } else {
                logger.debug("lista de clientes vacia");
            }
        } catch (BackboneApplicationException e) {
            logger.error("getListaCliente -> Backbone exception", e);
            //throw new ApplicationException("Error regresando lista de Cliente: " + e);
        }
        return (ArrayList<ClienteVO>) clientes;
    }
    /**
     * Metodo que regresa lista de Secciones existentes en la base.
     */
    @SuppressWarnings("unchecked")// Map Control
    public ArrayList<SeccionVO> getListaSeccion() throws ApplicationException {
        Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_SECCIONES");
        ArrayList<SeccionVO> secciones = null;
        try {
            secciones = (ArrayList<SeccionVO>) endpoint.invoke(null);
            if (secciones != null && !secciones.isEmpty()) {
                logger.debug("lista de secciones llena");
            } else {
                logger.debug("lista de Secciones vacia");
            }
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error regresando lista de Secciones");
        }
        return (ArrayList<SeccionVO>) secciones;
    }

    /**
     * Metodo que regresa lista de Roles existentes en la base.
     */
    @SuppressWarnings("unchecked")// Map Control
    public ArrayList<RolesVO> getListaRol() throws ApplicationException {

        Endpoint endpoint = (Endpoint) endpoints.get("CARGA_ROLES");
        ArrayList<RolesVO> rolesLista = null;
        try {
            rolesLista = (ArrayList<RolesVO>) endpoint.invoke(null);
            if (rolesLista != null && !rolesLista.isEmpty()) {
                logger.debug("Lista de roles Llena");
            } else {
                logger.debug("Lista de roles vacia");
            }
        } catch (BackboneApplicationException e) {
            logger.error("getListaRol -> Error al recuperar los datos", e);
            //throw new ApplicationException("Error regresando lista de Roles");
        }
        return (ArrayList<RolesVO>) rolesLista;
    }
    /**
     *Metodo que regresa lista de tipos existentes en la base de datos.
     */
    @SuppressWarnings("unchecked")//Map Control
    public ArrayList<TipoVO> getListaTipo(String tabla)
            throws ApplicationException {
        Map params = new HashMap<String, String>();
        params.put("tabla", tabla);
        ArrayList<TipoVO> tipos = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_TIPOS");
            tipos = (ArrayList<TipoVO>) endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            logger.error("getListaTipo -> Error al recuperar los datos", e);
            //throw new ApplicationException("Error regresando lista Tipos");
        }
        return (ArrayList<TipoVO>) tipos;
    }
    /**
     * Metodo que se encarga de agregar una nueva configuracion a la base.
     */
    public MensajesVO agregarconfiguracion(ConfiguracionMenuVO configuracionMenuVo)
            throws ApplicationException {
    	MensajesVO msg = null;
        Endpoint endpoint = (Endpoint) endpoints.get("GUARDA_CONFIGURACION_MENU");
        try {
            msg = (MensajesVO) endpoint.invoke(configuracionMenuVo);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error insertando una nueva configuracion");
        }
        return msg;
    }

    @SuppressWarnings("unchecked")
    /**
     * Metodo que se encarga de copiar una configuracion existente en la base.
     */
    public MensajesVO copiarConfigMenu(ConfiguracionMenuVO configuracionMenuVo) throws ApplicationException {
    	MensajesVO msg = null;
    	
    	Map params =  new HashMap<String, String>();
        params.put("claveMenu", configuracionMenuVo.getClaveMenu());
        params.put("nombreMenu", configuracionMenuVo.getNombreMenu());
        params.put("claveCliente", configuracionMenuVo.getClaveCliente());
        params.put("claveUsuario", configuracionMenuVo.getClaveUsuario());
        params.put("claveRol", configuracionMenuVo.getClaveRol());
        Endpoint endpoint = (Endpoint) endpoints.get("COPIA_CONFIGURACION_MENU");
        try{
        	msg = (MensajesVO) endpoint.invoke(params);
        }catch (BackboneApplicationException e) {
            throw new ApplicationException("Error copiando una configuracion del Menu");
        }
        return msg;
    }
    /**
     *Metodo que se encarga de borrar una configuracion de Menu existente. 
     */
    @SuppressWarnings("unchecked")
    public MensajesVO borrarConfigMenu(String id) throws ApplicationException {
    	MensajesVO msg = null;
    	Endpoint endpoint = (Endpoint) endpoints.get("BORRA_CONFIGURACION_MENU");
        try{
        	msg = (MensajesVO) endpoint.invoke(id);
        }catch (BackboneApplicationException e) {
            throw new ApplicationException("Error tratando de borrar una configuracion del Menu");
        }
        return msg;
    }

    /**
     *Metodo para lalmar al metodo para devolver un PagedList del AbstractManager
     */
    public PagedList getPagedList(Map<String, String> params, String EndPointName, int start, int limit) throws ApplicationException{
        return pagedBackBoneInvoke(params, EndPointName, start, limit);
    }
}

