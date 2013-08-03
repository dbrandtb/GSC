/**
 * 
 */
package mx.com.aon.flujos.cotizacion.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.flujos.model.TatriParametrosVO;
import mx.com.aon.portal.service.CatalogService;
import mx.com.aon.portal.service.CatalogServiceJdbcTemplate;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * Clase Action para la obtencion de datos de un elemento de tipo combo
 * 
 * @author aurora.lozada
 * 
 */
public class ComboAction extends PrincipalCotizacionAction {

    /**
     * 
     */
    private static final long serialVersionUID = -7727952087890950476L;

    /**
     * 
     */
    protected CatalogService catalogManager;
    
    private CatalogServiceJdbcTemplate catalogServiceJdbcTemplate;
    
    @SuppressWarnings("unchecked")
    private List itemList;

    private String endPointName;

    private String comboId;

    private String ottabval;

    private String valAnterior;

    private String valAntAnt;

    private TatriParametrosVO tatriParametros;
    
    

   /**
     * Metodo que obtiene los datos del combo sin paso de parametros.
     * 
     * @return Cadena SUCCESS
     * @throws Exception
     */
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

        if (StringUtils.isBlank(comboId) || comboId.equals("undefined")) {
            logger.debug("### Entrando sin comboId.....");
            itemList = new ArrayList();

        } else {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("ID_CLIENTE", comboId);
            itemList = catalogManager.getItemList(endPointName, parameters);
        }
        return SUCCESS;
    }

    /**
     * Metodo que obtiene los datos del combo a traves de parametros asociados al componente y a los valores de los combos
     * antecesores anidados.
     * 
     * @return Cadena SUCCESS
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public synchronized String loadOttabval() throws Exception {

        boolean isDebugueable = logger.isDebugEnabled();
        if (isDebugueable) {
            logger.debug("### METODO loadOttabval en ComboAction...");
            logger.debug("### ottabval ---" + ottabval);
            logger.debug("### valAnterior ---" + valAnterior);
            logger.debug("### valAntAnt ---" + valAntAnt);
        }
         
        Map parametrosDatosVariables = new HashMap();
        if (isDebugueable) {
        	logger.debug("before PARAMS_DATOS_VARIABLES=="+ session.get("PARAMS_DATOS_VARIABLES"));
        }
        if(session.get("PARAMS_DATOS_VARIABLES")!= null){
        	parametrosDatosVariables = (HashMap)session.get("PARAMS_DATOS_VARIABLES");
        }
        
        Map<String, Object> parameters = new HashMap<String, Object>();
        //parameters.put("OTTABVAL", ottabval);
        //parameters.put("VAL_ANTERIOR", valAnterior);
        //parameters.put("VAL_ANTANT", valAntAnt);
        //itemList = catalogManager.getItemList("OBTIENE_LISTA", parameters); con backbone
        parameters.put("pv_cdtabla_i", ottabval);
        parameters.put("pv_valanter_i", valAnterior);
        parameters.put("pv_valantant_i", valAntAnt);
        itemList = catalogServiceJdbcTemplate.getItemList("COMBO_OBTIENE_LISTA", parameters);    
            
        //Subir a sesion mapa (key = nombreTablaBD, values=parametros enviados al procedure) para cargar stores al "Regresar"
        List params = new ArrayList();
        params.add(valAnterior);
        params.add(valAntAnt);
        parametrosDatosVariables.put(ottabval, params);
        session.put("PARAMS_DATOS_VARIABLES", parametrosDatosVariables);
        if (isDebugueable) {
        	logger.debug("after PARAMS_DATOS_VARIABLES==="+ session.get("PARAMS_DATOS_VARIABLES"));
        }
        
        return SUCCESS;
    }

    /**
     * @param catalogManager the catalogManager to set
     */
    public void setCatalogManager(CatalogService catalogManager) {
        this.catalogManager = catalogManager;
    }
    
    /**
     * 
     * @param catalogServiceJdbcTemplate
     */
    public void setCatalogServiceJdbcTemplate(
			CatalogServiceJdbcTemplate catalogServiceJdbcTemplate) {
		this.catalogServiceJdbcTemplate = catalogServiceJdbcTemplate;
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

    /**
     * @return the valAnterior
     */
    public String getValAnterior() {
        return valAnterior;
    }

    /**
     * @param valAnterior the valAnterior to set
     */
    public void setValAnterior(String valAnterior) {
        this.valAnterior = valAnterior;
    }

    /**
     * @return the tatriParametros
     */
    public TatriParametrosVO getTatriParametros() {
        return tatriParametros;
    }

    /**
     * @param tatriParametros the tatriParametros to set
     */
    public void setTatriParametros(TatriParametrosVO tatriParametros) {
        this.tatriParametros = tatriParametros;
    }

    /**
     * @return the ottabval
     */
    public String getOttabval() {
        return ottabval;
    }

    /**
     * @param ottabval the ottabval to set
     */
    public void setOttabval(String ottabval) {
        this.ottabval = ottabval;
    }

    /**
     * @return the valAntAnt
     */
    public String getValAntAnt() {
        return valAntAnt;
    }

    /**
     * @param valAntAnt the valAntAnt to set
     */
    public void setValAntAnt(String valAntAnt) {
        this.valAntAnt = valAntAnt;
    }

}
