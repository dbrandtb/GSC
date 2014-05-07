/**
 * 
 */
package mx.com.aon.flujos.cotizacion.web;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Clase Action padre de cotizacion
 * 
 * @author Leopoldo
 *
 */
public abstract class PrincipalCotizacionAction extends ActionSupport implements  SessionAware, Preparable {

    
    
    /**
     * 
     */
    @SuppressWarnings("unchecked")
    protected Map session;
    
    protected final transient Logger logger = Logger.getLogger(PrincipalCotizacionAction.class);
    
    protected String modificaStore(String store) {
        if (logger.isDebugEnabled()) {
            logger.debug("-> modificaStore");
            logger.debug("::: store :: " + store);
        }
        String storeAdaptado = StringUtils.replace(store, "\"", "'");
        return StringUtils.remove(storeAdaptado, ";");
    }
    /**
     * 
     */
    @SuppressWarnings("unchecked")
    public void setSession(Map session) {
        this.session = session;
    }

    /**
     * 
     */
    public void prepare() throws Exception {
       
    }

}
