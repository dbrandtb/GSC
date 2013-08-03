package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.SeccionVO;


/**
 * Interface de servicios para la configuracion de secciones.
 *
 */
public interface SeccionesManager {


	/**
	 *  Obtiene un conjunto de seciones.
	 *  
	 *  @param seccion
	 *  @param start
	 *  @param limit
	 *  
	 *  @return Objeto PagedList 
	 */	
	public PagedList buscarSecciones(String seccion, int start, int limit ) throws ApplicationException;

    
	/**
	 *  Agrega una nueva seccion.
	 * 
	 *  @param seccionVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String agregarGuardarSeccion(SeccionVO seccionVO) throws ApplicationException;


	/**
	 *  Realiza la baja de una seccion.
	 * 
	 *  @param cdSeccion
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String borrarSeccion(String cdSeccion) throws ApplicationException;


	/**
	 *  Obtiene una seccion.
	 * 
	 *  @param cdSeccion
	 *  
	 *  @return SeccionVO
	 */		
	public SeccionVO getSeccion(String cdSeccion) throws ApplicationException;

    
	/**
	  * Obtiene un conjunto de seciones y exporta el resultado en Formato PDF, Excel, CSV, etc.
	  *
	  * @param seccion
	  * 
	  * @return TableModelExport
	  */
	public TableModelExport getModel(String seccion) throws ApplicationException;

 
	/**
	 *  Hace editable un bloque en secciones
	 * 
	 *  @param cdSeccion
	 *  @param cdBloque
	 *  
	 *  @return boolean
	 */		
	public boolean isBloqueEditable(String cdSeccion,String cdBloque) throws ApplicationException;
}

