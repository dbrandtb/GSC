/**
 * 
 */
package mx.com.aon.portal.service.impl.configmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


import mx.com.aon.portal.model.MensajesVO;
import mx.com.aon.portal.model.configmenu.OpcionVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.configmenu.ConfigMenuManager;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.service.principal.PrincipalManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mx.com.aon.tmp.Endpoint;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.gseguros.exception.ApplicationException;

/**
 * @author eflores
 * @date 19/05/2008
 *
 */
//public class ConfigMenuManagerImpl implements ConfigMenuManager {

public class ConfigMenuManagerImpl extends AbstractManager implements ConfigMenuManager {
	
    private static final transient Log logger = LogFactory.getLog(ConfigMenuManagerImpl.class);
/*
    @SuppressWarnings("unchecked")// Map Controlled
    private Map endpoints;

    @SuppressWarnings("unchecked")// Map Controlled
    public void setEndpoints(Map endpoints) {
        this.endpoints = endpoints;
    }*/

    @SuppressWarnings("unchecked")// Map Controlled
    public List<OpcionVO> getOpciones(String cdTitulo)
            throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("-> ConfigMenuManagerImpl.getOpciones");
        }
        List<OpcionVO> opciones = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_OPCIONES_CONFIGMENU");
            opciones = (ArrayList<OpcionVO>) endpoint.invoke(cdTitulo);
            logger.debug(".. opciones : " + opciones);
        } catch (BackboneApplicationException e) {
            logger.error("Backbone exception", e);
            throw new ApplicationException("Error al recuperar los datos");

        }
        return (ArrayList<OpcionVO>) opciones;
    }
    
    public PagedList getOpciones(String cdTitulo,int start, int limit) throws ApplicationException {
    	Map<String, String> map = new HashMap<String, String>(); // HashMap map = new Hashmap();
    	
		map.put("cdTitulo",cdTitulo);
		
		return pagedBackBoneInvoke(map, "OBTIENE_OPCIONES_CONFIGMENU", start, limit);
    }
    
/*
    private PagedList pagedBackBoneInvoke(HashMap map, String string,
			int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}
*/
	/**
     * @exception ApplicationException
     */
    public String insertaOpcion(OpcionVO opcion) throws ApplicationException {

    	if (logger.isDebugEnabled()) {
            logger.debug("-> ConfigMenuManagerImpl.insertaOpcion");
        }
		
    	String resultado = "";
		MensajesVO msg = new MensajesVO();
		
        Endpoint endpoint = (Endpoint) endpoints.get("INSERTA_OPCION");
        try {
            msg = (MensajesVO) endpoint.invoke(opcion);
            resultado = msg.getMsgId();
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando insertar una nueva opcion");
        }
        return resultado;
    }

    /**
     * @exception ApplicationException
     */
    public String editaOpcion(OpcionVO opcion) throws ApplicationException {

        if (logger.isDebugEnabled()) {
            logger.debug("-> ConfigMenuManagerImpl.editaOpcion");
        }
        
    	String resultado = "";
		MensajesVO msg = new MensajesVO();
		
        Endpoint endpoint = (Endpoint) endpoints.get("EDITA_OPCION");
        try {
        	msg = (MensajesVO) endpoint.invoke(opcion);
        	resultado = msg.getMsgId();
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando editar la opcion");
        }
        return resultado;
    }
    
    /**
     * validaBorrado
     * @param cdTitulo
     * @return String
     * @throws ApplicationException
     */
    public String validaBorrado(String cdTitulo)throws ApplicationException {
        
        if (logger.isDebugEnabled()) {
            logger.debug("-> ConfigMenuManagerImpl.validaBorrado");
        }
        OpcionVO opcion = null;
        
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("VALIDA_BORRADO");
            opcion = (OpcionVO) endpoint.invoke(cdTitulo);
            if (logger.isDebugEnabled()) {
                logger.debug(".. opcion : " + opcion);
            }
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando validar la opcion a borrar");

        }
        return opcion.getOutParamText();
    }
    
    /**
     * 
     * @throws ApplicationException
     */
    public String borrarOpcion(String cdTitulo) throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("-> ConfigMenuManagerImpl.borrarOpcion");
        }
        
    	String resultado = "";
		MensajesVO msg = new MensajesVO();
		
        Endpoint endpoint = (Endpoint) endpoints.get("BORRA_OPCION");
        try{
        	msg = (MensajesVO) endpoint.invoke(cdTitulo);
        	resultado = msg.getMsgId();
        }catch (BackboneApplicationException e) {
            throw new ApplicationException("Error tratando de borrar una configuracion");
        }
        return resultado;
    }
}

