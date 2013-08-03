/**
 * 
 */
package mx.com.aon.portal.web.configmenu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.configmenu.OpcionVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.configmenu.ConfigMenuManager;
import mx.com.aon.portal.web.AbstractListAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.SessionAware;

/**
 * @author eflores
 * @date 19/05/2008
 *
 */
/*public class ConfiguraMenuAction extends ActionSupport {*/
public class ConfiguraMenuAction extends AbstractListAction implements SessionAware{	
    /**
     * Serial Version
     */
    private static final long serialVersionUID = -6654001282882848583L;
    private static final transient Log log= LogFactory.getLog(ConfiguraMenuAction.class);
    private ConfigMenuManager configMenuManager;
    private boolean success;
    private String dsTitulo;
    private List<OpcionVO> opciones;
    private OpcionVO opcionVO;
    //private int start = 0;
    //private int limit = 10;
    private String cdTitulo;
    //private int totalCount;
    @SuppressWarnings("unchecked")//Map control.
    private Map session;
    
    public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getCdTitulo() {
		return cdTitulo;
	}

	public void setCdTitulo(String cdTitulo) {
		this.cdTitulo = cdTitulo;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
     * Metodo que carga las opciones.
     * @return
     * @throws ApplicationException
     */
    public String opcionesJson() throws ApplicationException{
        if (log.isDebugEnabled()) {
            log.debug("-> ConfiguraMenuAction.opcionesJson");
            log.debug(".. dsTitulo : " + dsTitulo);
        }
        opciones = configMenuManager.getOpciones(dsTitulo);
        if (log.isDebugEnabled()) {
            log.debug(".. opciones : " + opciones);
        }
        success = true;
        return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
	public String cmdGetOpciones()throws ApplicationException{
    	try{
    		log.debug("***cmdGetOpciones***");
    		log.debug("cmdGetOpciones	dsTitulo:"+dsTitulo);
    		
    		PagedList pagedList= this.configMenuManager.getOpciones(dsTitulo, start, limit);
    		opciones = pagedList.getItemsRangeList();
            if (log.isDebugEnabled()) {
                log.debug(".. opciones : " + opciones);
            }
    		totalCount = pagedList.getTotalItems();
    		
            String [] NOMBRE_COLUMNAS = {"TITULO","URL"};
            Map params = new HashMap<String, String>();
            params.put("dsTitulo", dsTitulo);
            session.put("PARAMETROS_EXPORT", params);
            session.put("NOMBRE_COLUMNAS", NOMBRE_COLUMNAS);
            session.put("ENDPOINT_EXPORT_NAME", "EXPORT_OPCIONES_CONFIGMENU");
    		
    		success=true;
    		return SUCCESS;
    	
    	}catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
    	
    }
        
    /**
     * 
     */

    public String input() throws Exception{
        return INPUT;       
    }
        
    /**
     * @return the dsTitulo
     */
    public String getDsTitulo() {
        return dsTitulo;
    }

    /**
     * @param dsTitulo the dsTitulo to set
     */
    public void setDsTitulo(String dsTitulo) {
        this.dsTitulo = dsTitulo;
    }

    /**
     * @return the opciones
     */
    public List<OpcionVO> getOpciones() {
        return opciones;
    }
    /**
     * @param opciones the opciones to set
     */
    public void setOpciones(List<OpcionVO> opciones) {
        this.opciones = opciones;
    }
    /**
     * @return the opcionVO
     */
    public OpcionVO getOpcionVO() {
        return opcionVO;
    }
    /**
     * @param opcionVO the opcionVO to set
     */
    public void setOpcionVO(OpcionVO opcionVO) {
        this.opcionVO = opcionVO;
    }
    /**
     * 
     * @return
     */

    public boolean isSuccess() {
        return success;
    }
    /**
     * 
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
    /**
     * @param configMenuManager the configMenuManager to set
     */
    public void setConfigMenuManager(ConfigMenuManager configMenuManager) {
        this.configMenuManager = configMenuManager;
    }
    
    @SuppressWarnings("unchecked")//Map control
    public void setSession(Map session) {
        this.session = session;
    }
}

