/**
 * 
 */
package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.MetodoCancelacionVO;
import mx.com.gseguros.exception.ApplicationException;


/**
 * Interface de servicios para la configuracion de metodos de cancelacion.
 *
 */
public interface MetodosCancelacionManager {
	

	/**
	 *  Obtiene un conjunto de metodos de cancelacion.
	 *  
	 *  @param cdMetodo
	 *  @param dsMetodo
	 *  @param start
	 *  @param limit
	 *  
	 *  @return Objeto PagedList 
	 */	
	public PagedList buscarMetodosCancelacion(String cdMetodo, String dsMetodo, int start, int limit ) throws ApplicationException;
	
	
	/**
	 *  Agrega metodos de cancelacion.
	 * 
	 *  @param metodoCancelacionVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String agregarGuardarMetodoCancelacion(MetodoCancelacionVO metodoCancelacionVO) throws ApplicationException;
	
	/**
	 *  Borra metodos de cancelacion.
	 * 
	 *  @param metodoCancelacionVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */	
	
	public String borrarMetodoCancelacion(MetodoCancelacionVO metodoCancelacionVO)throws ApplicationException;
	/**
	 *  Obtiene metodos de cancelacion.
	 * 
	 *  @param cdMetodo
	 *  
	 *  @return MetodoCancelacionVO
	 */		
	public MetodoCancelacionVO getMetodoCancelacion (String cdMetodo) throws ApplicationException;
	

	/**
	  * Obtiene un conjunto de metodos de cancelacion y los exporta en Formato PDF, Excel, CSV, etc.
	  *
	  * @param cdMetodo
	  * @param dsMetodo
	  * 
	  * @return TableModelExport
	  */	
	public TableModelExport getModel(String cdMetodo, String dsMetodo) throws ApplicationException;
	 
}
