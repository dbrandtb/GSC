package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.ObtienetareaVO;
import mx.com.aon.portal.util.WrapperResultados;

/**
 * Interface de servicios para Estructura Tareas Checklist
 *
 *
 */
public interface EstructuraManagerTareasChecklist {

		
	/**
	 *  Obtiene listado de tareas especifica en base a un parametro de entrada.
	 * 
	 *  @param seccion 
	 *  @param tarea
	 *  @param estado
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos TareaChecklistVO
	 *  
	 *  @throws ApplicationException

	 */
	public  PagedList getTareas(String seccion, String tarea, String estado, int start, int limit) throws ApplicationException;
	
	/**
	 *  Obtiene listado tareas especifica en base a los parametro de entrada.
	 * 
	 *  @param seccion
	 *  @param tarea
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos TareaChecklistVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList obtenerListaTareasChecklist(String seccion, String tarea, int start, int limit) throws ApplicationException;

	/**
	 *  Indica si existe una tarea especifica en base a los parametro de entrada.
	 * 
	 *  @param codSeccion
	 *  @param codTarea
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 */	
    public boolean existeTarea(String codSeccion, String codTarea) throws ApplicationException;    

    /**
	 *  Borra una tarea especifica en base a los parametro de entrada.
	 * 
	 *  @param codSeccion
	 *  @param codTarea
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 */
    public String borrarTarea(String codSeccion, String codTarea) throws ApplicationException; 

    /**
	 *  guarda una tarea especifica en base a los parametro de entrada.
	 * 
	 *  @param codSeccion
	 *  @param tarea
	 *  @param dsTarea
	 *  @param tareaPadre
	 *  @param estado
	 *  @param url
	 *  @param copia
	 *  @param ayuda
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 */
	public String guardarTarea(String codSeccion,String tarea, String dsTarea, String tareaPadre, String estado, String url, String copia,String ayuda) throws ApplicationException;

	/**
	 *  Obtiene una tarea especifica en base a los parametro de entrada.
	 * 
	 *  @param seccion
	 *  @param tarea
	 *  
	 *  @return Objeto ObtienetareaVO
	 *  
	 *  @throws ApplicationException
	 */
	public ObtienetareaVO getTarea(String seccion, String tarea) throws ApplicationException;
	
	/**
	 * Valida si se puede borrar o no una tarea
	 * 
	 * @param codSeccion
	 * @param codTarea
	 * 
	 * @return  Mensaje asociado en respuesta a la ejecucion del servicio
	 * 
	 */
	public WrapperResultados validaBorraTarea(String codSeccion, String codTarea) throws ApplicationException;

}