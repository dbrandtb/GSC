package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.PeriodoGraciaClienteVO;

/**
 * Interface con servicios de abm para la pantalla de Periodos de gracias Cliente
 *
 */
public interface PeriodosGraciaClienteManager {
	
	/**
	 *  Obtiene un conjunto de periodos de gracia por cliente
	 * 
	 *  @param dsElemen
	 *  @param dsRamo	 
	 *  @param dsUniEco
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos PeriodosGraciaVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList buscarPeriodosGraciaCliente(String dsElemen, String dsRamo, String dsUniEco, int start, int limit )throws ApplicationException;
   
	/**
	 *  Inserta o actualiza datos de los periodos de gracia por cliente
	 * 
	 *  @param periodoGraciaClienteVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String insertarPeriodoGraciaCliente(PeriodoGraciaClienteVO periodoGraciaClienteVO) throws ApplicationException;
	
	/**
	 *  Elimina datos de un periodo de gracia por cliente
	 * 
	 *  @param periodoGraciaClienteVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String borrarPeriodoGraciaCliente(PeriodoGraciaClienteVO periodoGraciaClienteVO) throws ApplicationException;
    
	/**
	 *  Obtiene una lista de periodos de gracia por cliente para la exportar a un formato predeterminado.
	 * 
	 *  @param dsElemen
	 *  @param dsRamo
	 *  @param dsUniEco
	 *      
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	public TableModelExport getModel(String dsElemen, String dsRamo, String dsUniEco) throws ApplicationException;

}
