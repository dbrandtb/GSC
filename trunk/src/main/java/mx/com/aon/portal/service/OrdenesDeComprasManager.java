package mx.com.aon.portal.service;

import mx.com.aon.portal.model.OrdenDeCompraEncOrdenVO;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Interface de servicios para Historial Orden de Compras
 *
 */
public interface OrdenesDeComprasManager {
	/**
	 *  Obtiene detalle de persona de orden de compra
	 * 
	 *  @param cdCarro
	 *  
	 *  @return Objeto OrdenDeCompraEncOrdenVO
	 *  
	 *  @throws ApplicationException
	 */	    
	public OrdenDeCompraEncOrdenVO getObtenerOrdenesDetallePersonas(String cdCarro) throws ApplicationException;
	
	/**
	 *  Da por finalizada una orden de compras
	 * 
	 *  @param cdCarro
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String finalizarOrdenesDeCompras(String cdCarro) throws ApplicationException;
	
	/**
	 *  Obtiene un conjunto de ordenes de compras
	 * 
	 *  @param dsCarro
	 *  @param feInicioDe
	 *  @param feInicioA
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos OrdenDeCompraVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList buscarOrdenesDeCompras(String dsCarro, String feInicioDe, String feInicioA, int start, int limit ) throws ApplicationException;
        	
}
