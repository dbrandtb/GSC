package mx.com.aon.catbo.service;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.StatusCasoVO;
import mx.com.aon.catbo.model.StatusProcesoVO;
import mx.com.aon.portal.service.PagedList;





/**
 * Interface de servicios para AyudaCoberturas.
 *
 */
public interface StatusCasosManager{
	
	/**
	 *  Obtiene un conjunto de Estatus de Caso
	 *   
	 * @param dsStatus
	 * 
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList buscarStatusCasos(String dsStatus, int start, int limit )throws ApplicationException;

	/**
	 *  Elimina un Estatus de Caso
	 * 
	 *  @param cdStatus
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String borrarStatusCasos(String cdStatus) throws ApplicationException;
	
	/**
	 *  Obtiene Estatus de Caso
	 * 
	 *  @param cdStatus
	 *  
	 *  @return Objeto StatusCasoVO
	 *  
	 *  @throws ApplicationException
	 */	    
	public StatusCasoVO getStatusCasos(String cdStatus) throws ApplicationException;
	
	/**
	 *  Inserta o Actualiza Estatus de Caso
	 * 
	 *  @param statusCasoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String guardarStatusCasos(StatusCasoVO statusCasoVO) throws ApplicationException;	

	/**
	 *  Obtiene un conjunto de procesos especifica en base a un parametro de entrada.
	 * 
	 *  @param cdStatus
	 *  
	 *  @return Objeto TareaVO
	 *  
	 *  @throws ApplicationException
	 *  
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List getStatusCasosTareas(String cdStatus) throws ApplicationException;
	
	/**
	 *  Salva la informacion de notificaciones por proceso.
	 * 
	 *  @param StatusCasoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String guardarStatusCasosTareas(StatusProcesoVO statusProcesoVO) throws ApplicationException;
	
	/**
	 *  Borra la informacion de notificaciones por proceso.
	 * 
	 *  @param StatusCasoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String borrarStatusCasosTareas(StatusProcesoVO statusProcesoVO) throws ApplicationException;
	
	
	
	/**
	 *  Obtiene una lista de Estatus de Caso para la exportacion a un formato predeterminado.
	 * 
	 *  @param dsStatus: parametro con el que se realiza la busqueda.
	 *  
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	public TableModelExport getModel(String dsStatus) throws ApplicationException;
	
	
	/**
	 *  Obtiene un conjunto de procesos especifica en base a un parametro de entrada.
	 * 
	 *  @param cdStatus
	 *  
	 *  @return Objeto TareaVO
	 *  
	 *  @throws ApplicationException
	 *  
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List getStatusCasosTareasProcesos(String cdStatus) throws ApplicationException;

}
