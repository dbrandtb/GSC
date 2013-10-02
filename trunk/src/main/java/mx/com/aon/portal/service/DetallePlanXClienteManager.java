package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.DetallePlanXClienteVO;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Interface de servicios de abm para Detalle de Plan por cliente
 *
 */
public interface DetallePlanXClienteManager {

	/**
	 * Metodo que busca y obtiene un conjunto de registros para listar en el grid.
	 * 
	 * @param start
	 * @param limit
	 * @param codigoCliente
	 * @param codigoElemento
	 * @param codigoProducto
	 * @param codigoPlan
	 * @param codigoTipoSituacion
	 * @param garantia
	 * @param aseguradora
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	public PagedList buscarDetallePlanes (int start,
								int limit,
								String codigoElemento,
								String codigoProducto,
								String codigoPlan,
								String codigoTipoSituacion,
								String garantia,
								String aseguradora) throws ApplicationException;

	/**
	 * Obtiene un unico registro de datos de un plan por cliente
	 * 
	 * @param detallePlanXClienteVO
	 * 
	 * @return DetallePlanXClienteVO
	 * 
	 * @throws ApplicationException
	 */
	public DetallePlanXClienteVO getPlanXCliente (DetallePlanXClienteVO detallePlanXClienteVO) throws ApplicationException;
	
	/**
	 * Metodo que inserta un plan de un cliente
	 * 
	 * @param detallePlanXClienteVO Estructura de plan a modificar
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	public String addPlanXCliente (DetallePlanXClienteVO detallePlanXClienteVO) throws ApplicationException;

	/**
	 * Metodo que actualiza un plan de un cliente
	 * 
	 * @param detallePlanXClienteVO Estructura de plan a modificar
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	public String setPlanXCliente (DetallePlanXClienteVO detallePlanXClienteVO) throws ApplicationException;

	/**
	 * Metodo que elimina un plan de un cliente seleccionado en el grid de la pantalla
	 * 
	 * @param detallePlanXClienteVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	public String borrarPlanXCliente (DetallePlanXClienteVO detallePlanXClienteVO) throws ApplicationException ;
	
	/**
	 * Metodo que obtiene un conjunto de tipos de situacion para mostrar en un combo en pantalla	 
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
    public PagedList comboTipoSituacion () throws ApplicationException;
    
    /**
	 * Metodo que obtiene un conjunto de coberturas para mostrar en un combo en pantalla	 
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
    public PagedList comboCoberturas () throws ApplicationException;
    
    /**
	 * Metodo que obtiene un conjunto de aseguradoras para mostrar en un combo en pantalla	 
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
    public PagedList comboAseguradora () throws ApplicationException;
    
    /**
	 * Metodo que obtiene un conjunto de planes por cliente para
	 *  exportarlo en un formato pdf, xsl, txt, etc.
	 * 
	 * @return TableModelExport
	 * 
	 * @throws ApplicationException
	 */
    public TableModelExport getModel(
    		String codigoElemento,
    		String codigoProducto,
    		String codigoPlan,
    		String codigoTipoSituacion,
    		String garantia, 
    		String aseguradora) throws ApplicationException;

}
