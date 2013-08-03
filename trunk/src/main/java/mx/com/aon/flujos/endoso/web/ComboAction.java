/**
 * 
 */
package mx.com.aon.flujos.endoso.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal.service.CatalogService;

/**
 * 
 * Clase Action para la obtencion de datos de un elemento de tipo combo
 * 
 * @author aurora.lozada
 * 
 */
public class ComboAction extends PrincipalEndosoAction {

 

    /**
     * 
     */
    private static final long serialVersionUID = -3244942452453066220L;

    /**
     * 
     */
    protected CatalogService catalogManager;

    @SuppressWarnings("unchecked")
    private List itemList;

    private String endPointName;

    private String comboId;
    
    /**
     * Metodo que obtiene los datos del combo sin paso de parametros.
     * 
     * @return Cadena SUCCESS
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String load() throws Exception {

        boolean isDebugueable = logger.isDebugEnabled();

        if (isDebugueable) {
            logger.debug("######################################################");
            logger.debug("### METODO load en ComboAction...");
            logger.debug("######################################################");
        }
        logger.debug("### endPointName ---" + endPointName);

      
        itemList = catalogManager.getItemList(endPointName);

        return SUCCESS;
    }
    
    /**
     * Metodo que obtiene los datos del combo pasando el identificador de un combo asociado para la busqueda.
     * 
     * @return Cadena SUCCESS
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String loadWithId() throws Exception {

        boolean isDebugueable = logger.isDebugEnabled();

        if (isDebugueable) {
            logger.debug("######################################################");
            logger.debug("### METODO loadWithId en ComboAction...");
            logger.debug("######################################################");
        }
        logger.debug("### endPointName ---" + endPointName);
        logger.debug("### comboId ---" + comboId);
        
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ID_CLIENTE", comboId);

        
        itemList = catalogManager.getItemList(endPointName, parameters);

        return SUCCESS;
    }

    /**
     * @param catalogManager the catalogManager to set
     */
    public void setCatalogManager(CatalogService catalogManager) {
        this.catalogManager = catalogManager;
    }

    /**
     * @return the itemList
     */
    @SuppressWarnings("unchecked")
    public List getItemList() {
        return itemList;
    }

    /**
     * @param itemList the itemList to set
     */
    @SuppressWarnings("unchecked")
    public void setItemList(List itemList) {
        this.itemList = itemList;
    }

    /**
     * @return the endPointName
     */
    public String getEndPointName() {
        return endPointName;
    }

    /**
     * @param endPointName the endPointName to set
     */
    public void setEndPointName(String endPointName) {
        this.endPointName = endPointName;
    }

    /**
     * @return the comboId
     */
    public String getComboId() {
        return comboId;
    }

    /**
     * @param comboId the comboId to set
     */
    public void setComboId(String comboId) {
        this.comboId = comboId;
    }
}
