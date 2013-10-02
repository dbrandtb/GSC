package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.PlanesMPlanProVO;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Interface de servicios para Mantenimiento de planes.
 *
 */
public interface MantenimientoPlanesManager {

	/**
	 *  Obtiene un conjunto de mantenimiento de planes.
	 * 
	 *  @param start
	 *  @param limit
	 *  @param planesMPlanProVO
	 *  
	 *  @return Lista de Objetos PagedList
	 *  
	 *  @throws ApplicationException
	 */
	PagedList buscarPlanes (int start, int limit, PlanesMPlanProVO planesMPlanProVO) throws ApplicationException;
	
	/**
	 *  Metodo que realiza la insercion de un nuevo plan.
	 * 
	 *  @param planesMPlanProVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String insertarPlan(PlanesMPlanProVO planesMPlanProVO) throws ApplicationException;
	
	/**
	 *  Metodo que realiza la actualizacion de un plan modificado.
	 * 
	 *  @param planesMPlanProVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String actualizarPlan(PlanesMPlanProVO planesMPlanProVO) throws ApplicationException;
	
	/**
	 *  Metodo que realiza la eliminacion de un plan seleccionado.
	 * 
	 *  @param planesMPlanProVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String borrarPlan(PlanesMPlanProVO planesMPlanProVO) throws ApplicationException;
	
	/**
	 *  Metodo que obtiene la eliminacion de un plan seleccionado.
	 * 
	 *  @param planesMPlanProVO
	 *  
	 *  @return Objeto PlanesMPlanProVO conteniendo el registro encontrado.
	 *  
	 *  @throws ApplicationException
	 */
	public PlanesMPlanProVO getPlan(PlanesMPlanProVO planesMPlanProVO) throws ApplicationException;
	
	/**
	 *
	 * Obtiene un conjunto de planes y exporta el resultado en Formato PDF, Excel, CSV, etc.
	 *
	 * @param codigoProducto
	 * @param codigoPlan
	 * @param tipoSituacion
	 * @param garantia
	 * 
	 * @return TableModelExport
	 * 
	 * @throws ApplicationException
	 */
	public TableModelExport getModel(String codigoProducto,String codigoPlan,String tipoSituacion,String garantia ) throws ApplicationException;
}
