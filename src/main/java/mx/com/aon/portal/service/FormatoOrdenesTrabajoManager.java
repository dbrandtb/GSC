/**
 * 
 */
package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.FormatoOrdenesTrabajoVO;

/**
 * Interface de servicios para formatos de ordenes de trabajo.
 *
 */
public interface FormatoOrdenesTrabajoManager {
	

	/**
	 *  Realiza la baja de de formato de ordenes de trabajo .
	 * 
	 *  @param cdFormatoOrden
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String borrarFormatoOrdenesTrabajo(String cdFormatoOrden) throws ApplicationException;
	

	/**
	 *  Salva la informacion de formato de ordenes de trabajo.
	 * 
	 *  @param formatoOrdenesTrabajoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String guardarFormatoOrdenesTrabajo(FormatoOrdenesTrabajoVO formatoOrdenesTrabajoVO) throws ApplicationException;
		

	/**
	 *  Obtiene el formato de ordenes de trabajo.
	 * 
	 *  @param cdFormatoOrden
	 *  
	 *  @return FormatoOrdenesTrabajoVO
	 */		
	public FormatoOrdenesTrabajoVO getFormatoOrdenesTrabajo (String cdFormatoOrden) throws ApplicationException;

	
	/**
	 *  Realiza la copia de formato de ordenes de trabajo
	 * 
	 *  @param cdFormatoOrden
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String copiarFormatoOrdenesTrabajo(String cdFormatoOrden)throws ApplicationException;
	
	
	/**
	 *  Obtiene un conjunto de formato de ordenes de trabajo.
	 *  
	 *  @param dsFormatoOrden
	 *  @param start
	 *  @param limit
	 *  
	 *  @return Conjunto de objetos FormatoOrdenesTrabajoVO
	 *  
	 *  @throws ApplicationException
	 */	
	public PagedList buscarFormatoOrdenesTrabajo(String dsFormatoOrden, int start, int limit) throws ApplicationException;	
	
}
