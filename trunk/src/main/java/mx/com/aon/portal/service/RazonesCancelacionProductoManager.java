package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.gseguros.exception.ApplicationException;


/**
 * Interface de servicios para razones de cancelacion de productos.
 *
 */
public interface RazonesCancelacionProductoManager {
	
	/**
	 * Obtiene un conjunto de razones de cancelación de productos.
	 *
	 * @param dsRamo 
	 * @param dsRazon
	 * @param dsMetodo 
	 * @param start
	 * @param limit
	 * 
	 * @return Objeto PagedList 
	 *			
	 */
	public PagedList obtenerRazonesCancelacionProducto(String dsRamo, String dsRazon, String dsMetodo, int start, int limit) throws ApplicationException;
	

	/**
	 *  Da de baja a informacion de razones de cancelación de productos.
	 *  
	 *  @param cdRazon
	 *	@param cdRamo 
	 *	@param cdMetodo
	 *
	 *	@return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */
	public String borrarRazonesCancelacionProducto(String cdRazon, String cdRamo, String cdMetodo) throws ApplicationException;
	

	/**
	 * Salva la informacion de razones de cancelación de producto.
	 *  
	 *  @param cdRamo
	 *  @param cdRazon
	 *  @param cdMetodo
	 *  
	 *	@return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */
	public String guardarConfiguracionRazonCancelacionProducto(String cdRamo, String cdRazon, String cdMetodo) throws ApplicationException;
	

	/**
	 * Obtiene un conjunto de razones de cancelacion y los exporta en Formato PDF, Excel, CSV, etc.
	 *  
	 *  @param dsRamo
	 *  @param dsRazon
	 *  @param dsMetodo
	 *	
	 *	@return TableModelExport
	 */
	public TableModelExport getModel(String dsRamo, String dsRazon, String dsMetodo) throws ApplicationException;
}
