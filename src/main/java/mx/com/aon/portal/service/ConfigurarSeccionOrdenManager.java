package mx.com.aon.portal.service;

import mx.com.gseguros.exception.ApplicationException;


/**
 * Interface de servicios para configurar seccion orden.
 *
 */
public interface ConfigurarSeccionOrdenManager {
	
	
	/**
	 * Obtiene un conjunto de configurar seccion orden.
	 *
	 * @param cdFormatoOrden 
	 * @param start
	 * @param limit
	 * 
	 * @return Objeto PagedList 
	 *			
	 */
	public PagedList obtenerSeccionesFormato(String cdFormatoOrden, int start, int limit) throws ApplicationException;
	

	/**
	 * Salva la informacion de configurar seccion orden.
	 *  
	 *  @param cdFormatoOrden
	 *  @param cdSeccion
	 *  @param nmOrden
	 *  @param cdTipSit
	 *  @param cdTipObj
	 *  
	 *	@return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */	
	public String guardarSeccionFormato(String cdFormatoOrden, String cdSeccion, String nmOrden, String cdTipSit, String cdTipObj) throws ApplicationException;
	

	/**
	 *  Da de baja a informacion de configurar seccion orden.
	 *  
	 *  @param cdFormatoOrden
	 *	@param cdSeccion 
	 *
	 *	@return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */
	public String borraSeccionFormato(String cdFormatoOrden, String cdSeccion) throws ApplicationException;
}
