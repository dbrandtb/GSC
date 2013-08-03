/**
 * 
 */
package mx.com.aon.portal.service.impl.opcmenuusuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.MensajesVO;
import mx.com.aon.portal.model.menuusuario.MenuVO;
import mx.com.aon.portal.model.opcmenuusuario.ConfigOpcionMenuVO;
import mx.com.aon.portal.model.opcmenuusuario.NivelMenuVO;
import mx.com.aon.portal.model.opcmenuusuario.OpcionesVO;
import mx.com.aon.portal.model.opcmenuusuario.ProductoClienteVO;
import mx.com.aon.portal.model.opcmenuusuario.UsuarioVO;
import mx.com.aon.portal.model.principal.ClienteVO;
import mx.com.aon.portal.model.principal.RolVO;
import mx.com.aon.portal.model.principal.TipoVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.service.opcmenuusuario.OpcMenuUsuarioManager;

import org.apache.log4j.Logger;

import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;

/**
 * @author eflores
 * @date 22/05/2008
 *
 */
public class OpcMenuUsuarioManagerImpl extends AbstractManager implements OpcMenuUsuarioManager {
	
    private static Logger logger = Logger.getLogger(OpcMenuUsuarioManagerImpl.class);

    /**
     * Metodo que regresa la lista de configuracion Menu Usuario existente en la base de Datos.
     */
    @SuppressWarnings("unchecked")// Map Control
    public ArrayList<MenuVO> obtieneMenu(String cdMenu) 
            throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("-> OpcMenuUsuarioManagerImpl.obtieneMenu");
        }
        
        ArrayList<MenuVO> menuList = null;

        try {
            Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_MENU");
            menuList = (ArrayList<MenuVO>) endpoint.invoke(cdMenu);
        } catch (BackboneApplicationException e) {
            logger.error("obtieneMenu -> Backbone exception", e);
            //throw new ApplicationException("Error al recuperar los datos");

        }
        return menuList;
    }
    
    /**
     * Metodo que regresa la lista de configuracion Menu Usuario existente en la base de Datos.
     */
    public PagedList obtieneOpcionesMenu(Map<String,String> params, int start, int limit) throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("-> OpcMenuUsuarioManagerImpl.obtieneOpcionesMenu");
            logger.debug("-> params=" + params);
            logger.debug("-> start=" + start);
            logger.debug("-> limit=" + limit);
        }
                
        return pagedBackBoneInvoke(params, "OBTIENE_OPCIONES_MENU", start, limit);
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
            //throw new ApplicationException("Error al recuperar los datos en OBTIENE_ROLES");

        }
        return (ArrayList<RolVO>) roles;
    }
    /**
     * Metodo que regresa lista de usuarios existentes en la base.
     */
    @SuppressWarnings("unchecked")// Map Control
    public ArrayList<UsuarioVO> getUsuarios() throws ApplicationException {
        Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_USUARIOS");
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
            //throw new ApplicationException("Error regresando lista OBTIENE_USUARIOS");
        }
        return (ArrayList<UsuarioVO>) usuarios;
    }
    /**
     * Metodo que regresa lista de opciones existentes en la base.
     */
    @SuppressWarnings("unchecked")// Map Control
    public ArrayList<OpcionesVO> getOpciones() throws ApplicationException {
        Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_OPCIONES");
        ArrayList<OpcionesVO> opciones = null;
        try {
            opciones = (ArrayList<OpcionesVO>) endpoint.invoke(null);
            if (opciones != null && !opciones.isEmpty()) {
                logger.debug("lista de opciones llena");
            } else {
                logger.debug("lista de opciones vacia");
            }
        } catch (BackboneApplicationException e) {
            logger.error("getOpciones -> Backbone exception", e);
            //throw new ApplicationException("Error regresando lista OBTIENE_OPCIONES");
        }
        return (ArrayList<OpcionesVO>) opciones;
    }
    /**
     * Metodo que regresa lista de productos existentes en la base.
     * @param cdElemento
     * @exception ApplicationException
     */
    @SuppressWarnings("unchecked")// Map Control
    public ArrayList<ProductoClienteVO> getProductoCliente(String cdElemento) throws ApplicationException {
        Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_PRODUCTO_CLIENTES");
        ArrayList<ProductoClienteVO> prods = null;
        Map params = new HashMap<String, String>();
        params.put("unieco", "");
        params.put("elemento", cdElemento);
        params.put("ramo", "");
        try {
            prods = (ArrayList<ProductoClienteVO>) endpoint.invoke(params);
            if (prods != null && !prods.isEmpty()) {
                logger.debug("lista de prods llena");
            } else {
                logger.debug("lista de prods vacia");
            }
        } catch (BackboneApplicationException e) {
            logger.error("getProductoCliente -> Backbone exception", e);
            //throw new ApplicationException("Error regresando lista OBTIENE_PRODUCTO_CLIENTES");
        }
        return (ArrayList<ProductoClienteVO>) prods;
    }
    /**
     * Metodo que regresa lista de productos existentes en la base.
     * @param cdTitulo
     * @exception ApplicationException
     */
    @SuppressWarnings("unchecked")// Map Control
    public ArrayList<NivelMenuVO> getNivelMenu(String cdMenu) throws ApplicationException {
        Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_NIVEL_MENU");
        ArrayList<NivelMenuVO> nivelMenuLista = null;
        Map params = new HashMap<String, String>();
        params.put("cdMenu", cdMenu);
        try {
            nivelMenuLista = (ArrayList<NivelMenuVO>) endpoint.invoke(params);
            if (nivelMenuLista != null && !nivelMenuLista.isEmpty()) {
                logger.debug("lista de nivel menu llena");
            } else {
                logger.debug("lista de nivel menu vacia");
            }
        } catch (BackboneApplicationException e) {
            logger.error("getNivelMenu -> Backbone exception", e);
            //throw new ApplicationException("Error regresando lista OBTIENE_NIVEL_MENU");
        }
        return (ArrayList<NivelMenuVO>) nivelMenuLista;
    }
    /**
     * Metodo que regresa lista de clientes existentes en la base.
     */
    @SuppressWarnings("unchecked")// Map Control
    public ArrayList<ClienteVO> getListaCliente() throws ApplicationException {
        Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_CLIENTES");
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
            //throw new ApplicationException("Error regresando lista OBTIENE_CLIENTES");
        }
        return (ArrayList<ClienteVO>) clientes;
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
            logger.debug("getListaTipo -> Error al recuperar los datos");
            //throw new ApplicationException("Error regresando lista Tipos");
        }
        return (ArrayList<TipoVO>) tipos;
    }
    
    /**
     *Metodo que regresa lista de tipos existentes en la base de datos.
     */
    @SuppressWarnings("unchecked")//Map Control
    public ArrayList<TipoVO> getListaTipoSituacion(String idRamo)
            throws ApplicationException {
        Map params = new HashMap<String, String>();
        params.put("idRamo", idRamo);
        ArrayList<TipoVO> tipos = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_TIPOS_SITUACION");
            tipos = (ArrayList<TipoVO>) endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            logger.debug("getListaTipoSituacion -> Error al recuperar los datos");
            //throw new ApplicationException("Error regresando lista Tipos");
        }
        return (ArrayList<TipoVO>) tipos;
    }
    
    /**
     * Metodo que se encarga de agregar una nueva configuracion a la base.
     */
    public MensajesVO agregarMenu(ConfigOpcionMenuVO configOpcionMenuVO)
            throws ApplicationException {
    	MensajesVO msg = null;
        Endpoint endpoint = (Endpoint) endpoints.get("GUARDA_CONFIG_OPCION_MENU");
        try {
            msg = (MensajesVO) endpoint.invoke(configOpcionMenuVO);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error insertando una nueva configuracion de Opciones Menu");
        }
        return msg;
    }
    
    /**
     * Metodo que se encarga de agregar una nueva configuracion a la base.
     */
    public void configurarOpcionMenu(ConfigOpcionMenuVO configOpcionMenuVO)
            throws ApplicationException {
        Endpoint endpoint = (Endpoint) endpoints.get("GUARDA_CONFIG_OPCION_MENU");
        try {
            endpoint.invoke(configOpcionMenuVO);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error insertando una nueva configuracion");
        }
    }

    @SuppressWarnings("unchecked")
    /**
     * Metodo que se encarga de copiar una configuracion existente en la base.
     */
    public void copiarConfig(String idCliente, String idRol, String idClienteNvo, String idRolNvo) throws ApplicationException {
        Map params =  new HashMap<String, String>();
        params.put("idCliente", idCliente);
        params.put("idRol", idRol);
        params.put("idClienteNvo",idClienteNvo);
        params.put("idRolNvo", idRolNvo);
        Endpoint endpoint = (Endpoint) endpoints.get("COPIA_CONFIGURACION");
        try{
            endpoint.invoke(params);
        }catch (BackboneApplicationException e) {
            throw new ApplicationException("Error copiando una configuracion");
        }
    }
    /**
     *Metodo que se encarga de borrar una configuracion de Menu existente. 
     */
    @SuppressWarnings("unchecked")
    public MensajesVO borrarConfigOpcionMenu(String cdMenu, String cdNivel) throws ApplicationException {
    	
    	MensajesVO msg = null;
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("menu", cdMenu);
    	params.put("nivel", cdNivel);
    	Endpoint endpoint = (Endpoint) endpoints.get("BORRA_CONFIG_OPCION_MENU");
        try{
        	msg = (MensajesVO) endpoint.invoke(params);
        }catch (BackboneApplicationException e) {
            throw new ApplicationException("Error tratando de borrar una configuracion de la Opción Menu");
        }
        return msg;
    }

}


