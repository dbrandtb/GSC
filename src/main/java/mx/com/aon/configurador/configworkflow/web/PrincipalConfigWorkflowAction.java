package mx.com.aon.configurador.configworkflow.web;

import java.util.List;
import java.util.Map;

import mx.com.aon.portal.service.CatalogService;
import mx.com.aon.portal.service.PagerManager;
import mx.com.aon.portal.service.configworkflow.ConfigWorkFlowManager;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author eflores, Alejandro Garcia
 * @date 18/07/2008
 */
public class PrincipalConfigWorkflowAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 1L;
    protected final transient Logger logger = Logger.getLogger(PrincipalConfigWorkflowAction.class);
    private static final int LIMIT = 20;    
    /**
     * Atributo de respuesta interpretado por Struts2 con la lista de beans con los valores de la consulta 
     * (en este caso son de tipo ConjuntoPantallaVO).
     */
    @SuppressWarnings("unchecked")
    protected List pagedDataList;    
    /**
     * Atributo agregado como parametro de la petición por struts que indica
     * el inicio de el número de linea en cual iniciar
     */
    protected int start;    
    /**
     * Atributo agregado como parametro de la petición por struts que indica la cantidad
     * de registros a ser consultados
     */
    protected int limit = LIMIT;    
    /**
     * Atributo de respuesta interpretado por strust con el número de registros totales
     * que devuelve la consulta.
     */
    protected int totalCount;    
    /**
     * Atributo para el mapeo de session.
     */
    @SuppressWarnings("unchecked")
    protected Map session;    
    /**
     * Interface generica con implementacion de Endpoints
     */
    protected CatalogService catalogManager;    
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
    protected PagerManager pagerManager;
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
    protected ConfigWorkFlowManager configWorkFlowManager;


    
    /**
     * @param pagerManager the pagerManager to set
     */
    public void setPagerManager(PagerManager pagerManager) {
        this.pagerManager = pagerManager;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Map session) {
        this.session = session;
    }

    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * @return the pagedDataList
     */
    public List getPagedDataList() {
        return pagedDataList;
    }

    /**
     * @param pagedDataList the pagedDataList to set
     */
    public void setPagedDataList(List pagedDataList) {
        this.pagedDataList = pagedDataList;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @return the totalCount
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @param catalogManager the catalogManager to set
     */
    public void setCatalogManager(CatalogService catalogManager) {
        this.catalogManager = catalogManager;
    }

	/**
	 * @param configWorkFlowManager the configWorkFlowManager to set
	 */
	public void setConfigWorkFlowManager(ConfigWorkFlowManager configWorkFlowManager) {
		this.configWorkFlowManager = configWorkFlowManager;
	}
}
