/**
 * 
 */
package mx.com.aon.flujos.cotizacion.web;

import java.util.ArrayList;
import java.util.List;


import mx.com.aon.configurador.pantallas.model.ConjuntoPantallaVO;

/**
 * 
 * Clase Action ejemplo para la obtencion de datos de un elemento de tipo grid
 * 
 * @author aurora.lozada
 * 
 */

public class GridTestingAction extends PrincipalCotizacionAction{

    /**
     * 
     */
    private static final long serialVersionUID = -8196720698657146382L;
    
    private boolean success;
    
    
    
    private List <ConjuntoPantallaVO> dataGrid;
    
    
    public String load() throws Exception {
        
        logger.debug("### llenando lista del grid... ");
        
        ConjuntoPantallaVO item = new ConjuntoPantallaVO();
        item.setCdConjunto("1");
        item.setProceso("Cotizacion");
        item.setCliente("ACEREX");
        item.setDescripcion("Descripcion");
        item.setNombreConjunto("Nombre conjunto");
        
        ConjuntoPantallaVO item2 = new  ConjuntoPantallaVO();
        item2.setCdConjunto("2");
        item2.setProceso("Cotizacion");
        item2.setCliente("ACEREX");
        item2.setDescripcion("Descripcion 2");
        item2.setNombreConjunto("Nombre conjunto 2");
        
        dataGrid = new ArrayList <ConjuntoPantallaVO>();
        
        dataGrid.add(item);
        dataGrid.add(item2);
        
        
        
        return SUCCESS;
    }

    /**
     * @return the dataGrid
     */
    public List <ConjuntoPantallaVO>  getDataGrid() {
        return dataGrid;
    }

    /**
     * @param dataGrid the dataGrid to set
     */
    public void setDataGrid(List <ConjuntoPantallaVO>  dataGrid) {
        this.dataGrid = dataGrid;
    }

    /**
     * @return the success
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

}
