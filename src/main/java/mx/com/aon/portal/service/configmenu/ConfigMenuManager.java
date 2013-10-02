/**
 * 
 */
package mx.com.aon.portal.service.configmenu;

import java.util.List;

import mx.com.aon.portal.model.configmenu.OpcionVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.gseguros.exception.ApplicationException;

/**
 * @author eflores
 * @date 19/05/2008
 *
 */
public interface ConfigMenuManager {

    /**
     * 
     * @param cdTitulo
     * @return
     * @throws ApplicationException
     */

    List<OpcionVO> getOpciones(String cdTitulo) throws ApplicationException;
    
    /**
     * 
     * @param opcion
     * @throws ApplicationException
     */

    public String insertaOpcion(OpcionVO opcion) throws ApplicationException;
    /**
     * 
     * @param opcion
     * @throws ApplicationException
     */
    
    public String editaOpcion(OpcionVO opcion) throws ApplicationException;
       
    /**
     * validaBorrado
     * @param cdTitulo
     * @return String
     * @throws ApplicationException
     */
    public String validaBorrado(String cdTitulo)throws ApplicationException;
    
    /**
     * 
     * @param id
     * @throws ApplicationException
     */
    public String borrarOpcion(String id)throws ApplicationException;
    
    /**
    *      sobrecargado por el metodo get List<OpcionVO>
    *
    */
    public PagedList getOpciones(String cdTitulo,int start, int limit) throws ApplicationException;

}
