package mx.com.aon.portal.web;


import mx.com.aon.portal.model.FormatoOrdenesTrabajoVO;
import mx.com.aon.portal.service.FormatoOrdenesTrabajoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.core.ApplicationException;


import org.apache.log4j.Logger;

import java.util.List;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Formato de ordenes de trabajo.
 *   
 *   @extends AbstractListAction
 * 
 */

@SuppressWarnings("serial")
public class ListaFormatoOrdenesTrabajoAction extends AbstractListAction{

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaFormatoOrdenesTrabajoAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient FormatoOrdenesTrabajoManager formatoOrdenesTrabajoManager;

	private List<FormatoOrdenesTrabajoVO> mEstructuraList;

	private String dsFormatoOrden;
	
	
	/**
	 * Ejecuta la busqueda para el llenado del grid de la pantalla de formato ordenes de trabajo.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{

            PagedList pagedList = this.formatoOrdenesTrabajoManager.buscarFormatoOrdenesTrabajo(dsFormatoOrden, start, limit); 
            mEstructuraList = pagedList.getItemsRangeList();
            totalCount = pagedList.getTotalItems();                                                    
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

	public List<FormatoOrdenesTrabajoVO> getMEstructuraList() {return mEstructuraList;}
	public void setMEstructuraList(List<FormatoOrdenesTrabajoVO> mEstructuraList) {this.mEstructuraList = mEstructuraList;}

	public String getDsFormatoOrden() {
		return dsFormatoOrden;
	}

	public void setDsFormatoOrden(String dsFormatoOrden) {
		this.dsFormatoOrden = dsFormatoOrden;
	}

	public void setFormatoOrdenesTrabajoManager(
			FormatoOrdenesTrabajoManager formatoOrdenesTrabajoManager) {
		this.formatoOrdenesTrabajoManager = formatoOrdenesTrabajoManager;
	}

}
