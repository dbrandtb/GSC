package mx.com.aon.configurador.pantallas.web;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.configurador.pantallas.model.ClienteCorpoVO;
import mx.com.aon.configurador.pantallas.model.MasterWrapperVO;
import mx.com.aon.configurador.pantallas.service.ConfiguradorPantallaService;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.service.CatalogService;
import mx.com.aon.portal.service.PagerManager;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
//TODO: CLIENTE CORPO, DEBE MOVERSE A OTRO PAQUET

/**
 * Clase Action padre del configurador
 * 
 */

public abstract class PrincipalConfPantallaAction extends ActionSupport implements
        SessionAware, Preparable {
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 30936553055118671L;

	/**
     * 
     */
    public static final String COMBO_PROCESOS_LIST = "combo_procesos_list";
    
    /**
     * 
     */
    public static final String COMBO_CORPORATIVO_LIST = "combo_corporativo_list";
    
    
    
    protected MasterWrapperVO masterWrapper;
    
    /**
     * 
     */
   
    protected final transient Logger logger = Logger.getLogger(PrincipalConfPantallaAction.class);
    
    /**
     * 
     */
    @SuppressWarnings("unchecked")
    protected Map session;
    
    
    /**
     * Manager con implementacion de Endpoint para la consulta a BD
     */
    protected PagerManager pagerManager;
    
    
    /**
     * 
     */
    protected CatalogService catalogManager;
    
    
    /**
     * 
     */
    protected ConfiguradorPantallaService configuradorManager;
    
    /**
     * 
     */
    protected ConfiguradorPantallaService configuradorManagerJdbcTemplate;


    /**
     * Atributo inyectado v�a Spring
     * @param pagerManager
     */
    public void setPagerManager(PagerManager pagerManager) {
		this.pagerManager = pagerManager;
	}
    
    /**
     * @param catalogManager the catalogManager to set
     */
    public void setCatalogManager(CatalogService catalogManager) {
        this.catalogManager = catalogManager;
    }
    
    
    /**
   * @param configuradorManager the configuradorManager to set
   */
    public void setConfiguradorManager(ConfiguradorPantallaService configuradorManager) {
    	this.configuradorManager = configuradorManager;
    }

    
    
    public void setConfiguradorManagerJdbcTemplate(
			ConfiguradorPantallaService configuradorManagerJdbcTemplate) {
		this.configuradorManagerJdbcTemplate = configuradorManagerJdbcTemplate;
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
    @SuppressWarnings("unchecked")
    public void prepare() throws Exception {
        
        boolean isDebug = logger.isDebugEnabled();
        
        
//        if(  session.get("elementos_proceso_master") == null  || masterWrapper == null ){
//        	masterWrapper = configuradorManager.getAtributosPropiedades( "2", "1", "3", "777");
//        	session.put("elementos_proceso_master", masterWrapper );        	
//        	logger.debug("### obteniendo elementos master... " + masterWrapper);
//        	
//        }
        
        
        if(isDebug){
            logger.debug("Enterintg to prepare in Principal..");
        }
        
        if(  session.get(COMBO_PROCESOS_LIST) == null ){
            List<BaseObjectVO> procesosList  = new ArrayList();        
            procesosList = catalogManager.getItemList("OBTIENE_PROCESOS");
            
            /*for (BaseObjectVO baseObjectVO : procesosList) {
                logger.debug("####procesos" + baseObjectVO);
            }*/
            
            session.put(COMBO_PROCESOS_LIST, procesosList);
        }
        
        if(  session.get(COMBO_CORPORATIVO_LIST) == null ){
            List<ClienteCorpoVO> corporativoList = new ArrayList();  
            corporativoList = catalogManager.getItemList("OBTIENE_CLIENTES_CORPO_CATALOGS");
            
          //  logger.debug("####corporativos" + corporativoList);
            /*for (ClienteCorpoVO clienteCorpoVO : corporativoList) {
                logger.debug("####cliente" + clienteCorpoVO);
            }*/
            
            session.put(COMBO_CORPORATIVO_LIST, corporativoList);           
        }

    }

	public MasterWrapperVO getMasterWrapper() {
		return masterWrapper;
	}

	public void setMasterWrapper(MasterWrapperVO masterWrapper) {
		this.masterWrapper = masterWrapper;
	}

}
