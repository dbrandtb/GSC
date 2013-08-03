
package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;

/**
 * Interface de servicios para asociar formatos
 *
 */

public interface AsociarFormatosManager {
	
	/**
	 *  Elimina datos de asociar formatos
	 * 
	 *  @param cdAsocia
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
    public String borrarAsociarFormatos(String cdAsocia) throws ApplicationException;

	/**
	 *  Obtiene un conjunto de asociaciar formato
	 * 
	 *  @param dsProceso
	 *  @param dsElemen
	 *  @param dsRamo
	 *  @param dsFormatoOrden
	 *  @param dsUnieco
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos 
	 *  
	 *  @throws ApplicationException
	 */
    public PagedList buscarAsociarFormatos(String dsProceso, String dsElemen, String dsRamo, String dsFormatoOrden, String dsUnieco, int start, int limit ) throws ApplicationException;

    /**
	 *  Obtiene una lista de asociar formatos para la exportar a un formato predeterminado.
	 * 
	 *  @param dsProceso
	 *  @param dsElemen
	 *  @param dsRamo
	 *  @param dsFormatoOrden
	 *  @param dsUnieco
	 *      
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
    public TableModelExport getModel(String dsProceso, String dsElemen, String dsRamo, String dsFormatoOrden, String dsUnieco) throws ApplicationException;
	
}
