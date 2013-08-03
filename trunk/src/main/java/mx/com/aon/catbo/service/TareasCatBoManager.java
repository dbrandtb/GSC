package mx.com.aon.catbo.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.TareaVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;



/**
 * Interface de servicios para Tareas CAT-BO.
 *
 */
public interface TareasCatBoManager{
	
	/**
	 *  Obtiene un conjunto de Tarea Cat-bo
	 *   
	 * @param dsProceso
	 * @param dsModulo
	 * @param cdPriord
	 * 
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 *  
	 */
	public PagedList buscarTareasCatBo(String dsProceso, String dsModulo, String cdPriord, int start, int limit )throws ApplicationException;

	
	/**
	 *  Obtiene un conjunto de Tarea Cat-bo para validar comprar tiempo
	 *   
	 * @param cdproceso
	 
	 * 
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 *  
	 */
	public WrapperResultados buscarTareasCatBoValidar(String cdproceso )throws ApplicationException;

	/**
	/**
	 *  Elimina una Tarea Cat-bo
	 * 
	 *  @param cdProceso
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String borrarTareasCatBo(String cdProceso) throws ApplicationException;
	
	/**
	 *  Obtiene Tarea Cat-bo
	 * 
	 *  @param cdStatus
	 *  
	 *  @return Objeto StatusCasoVO
	 *  
	 *  @throws ApplicationException
	 */	    
	public TareaVO getTareasCatBo(String cdProceso) throws ApplicationException;
	
	/**
	 *  Inserta o Actualiza Tarea Cat-bo
	 * 
	 *  @param tareaVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String guardarTareasCatBo(TareaVO tareaVO) throws ApplicationException;	

	/**
	 *  Obtiene una lista de Estatus de Caso para la exportacion a un formato predeterminado.
	 * 
	 *  @param dsProceso: parametro con el que se realiza la busqueda.
	 *  @param dsModulo
	 *  @param cdPriord
	 *  
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	public TableModelExport getModel(String dsProceso, String dsModulo, String cdPriord) throws ApplicationException;

}
