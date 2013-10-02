package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.AgrupacionPolizaVO;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Interface de servicios para Agrupacion de Polizas.
 *
 */
public interface AgrupacionPolizasManager{

	/**
	 *  Obtiene un conjunto de agrupaciones de polizas.
	 * 
	 *  @param start, limit: marcan el rango de la lista a obtener para la paginacion del grid.
	 *  @param cliente
	 *  @param tipoRamo
	 *  @param aseguradora
	 *  @param producto
	 *  
	 *  @return un objeto PagedLis con un conjunto de registros.
	 *  
	 *  @throws ApplicationException
	 */
    public PagedList buscarAgrupacionPolizas(String cliente, String tipoRamo, String tipoAgrupacion, String aseguradora, String producto, int start, int limit ) throws ApplicationException;
    
    /**
	 *  Inserta una nuava agrupacion de poliza.
	 * 
	 *  @param agrupacionPolizaVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
    public String agregarAgrupacionPoliza(AgrupacionPolizaVO agrupacionPolizaVO) throws ApplicationException;
    
    /**
	 *  Actualiza una agrupacion de polizas modificada.
	 * 
	 *  @param agrupacionPolizaVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
    public String guardarAgrupacionPoliza(AgrupacionPolizaVO agrupacionPolizaVO) throws ApplicationException;
    
    /**
	 *  Elimina una agrupacion de polizas seleccionada.
	 * 
	 *  @param cveAgrupa
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
    public String borrarAgrupacionPoliza(String cveAgrupa) throws ApplicationException;
    
    /**
	 *  Obtiene una agrupacion de polizas seleccionada.
	 * 
	 *  @param cveAgrupa
	 *  
	 *  @return Objeto AgrupacionPolizaVO
	 *  
	 *  @throws ApplicationException
	 */
    public AgrupacionPolizaVO getAgrupacionPoliza(String cveAgrupa) throws ApplicationException;
    
    /**
	 *  Obtiene una lista de registros de agrupacion de polizas para la exportacion a un formato predeterminado.
	 * 
	 *  @param cliente
	 *  @param tipoRamo
	 *  @param tipoAgrupacion
	 *  @param aseguradora
	 *  @param producto
	 *  
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
    public TableModelExport getModel(String cliente, String tipoRamo, String tipoAgrupacion, String aseguradora, String producto) throws ApplicationException;
}
