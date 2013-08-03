package mx.com.aon.portal.web;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.ConfiguracionVO;
import mx.com.aon.portal.service.ManagerCuentaChecklist;
import mx.com.aon.portal.service.PagedList;
import org.apache.log4j.Logger;

import java.util.List;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla CheckList de la cuenta.
 *   
 *   @extends AbstractListAction
 * 
 */
public class ListaChecklistCuentaAction extends AbstractListAction {

	private static final long serialVersionUID = 1454547787L;

	/**
     * Logger de la clase para monitoreo y registro de comportamiento
     */
    private static Logger logger = Logger.getLogger(ListaChecklistCuentaAction.class);
    private String dsElemen;
    private String dsConfig; 
    /**
     * Manager con implementacion de Endpoint para la consulta a BD
     */
    private transient ManagerCuentaChecklist  managerCuentaChecklist;
    private List<ConfiguracionVO> mEstructuraList;
    
    /**
	 * Metodo que realiza la busqueda para el llenado del grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick()throws Exception {
        try {
            PagedList  pagedList =  this.managerCuentaChecklist.getConfiguraciones(dsElemen, dsConfig, start,limit);
            this.mEstructuraList = pagedList.getItemsRangeList();
            this.totalCount = pagedList.getTotalItems();
            //logger.debug("entro a la lista Json");
            //logger.debug("cdPersona-----" + dsElemen);
            //logger.debug("dsPersona---------" + dsConfig);
         
            success = true;
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

	public void setManagerCuentaChecklist(
			ManagerCuentaChecklist managerCuentaChecklist) {
		this.managerCuentaChecklist = managerCuentaChecklist;
	}

	public String getDsElemen() {
		return dsElemen;
	}

	public void setDsElemen(String dsElemen) {
		this.dsElemen = dsElemen;
	}

	public List<ConfiguracionVO> getMEstructuraList() {
		return mEstructuraList;
	}

	public void setMEstructuraList(List<ConfiguracionVO> estructuraList) {
		mEstructuraList = estructuraList;
	}

	public String getDsConfig() {
		return dsConfig;
	}

	public void setDsConfig(String dsConfig) {
		this.dsConfig = dsConfig;
	}
}