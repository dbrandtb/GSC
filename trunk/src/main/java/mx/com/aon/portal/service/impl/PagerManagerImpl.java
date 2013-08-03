package mx.com.aon.portal.service.impl;

import org.apache.log4j.Logger;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.PagerManager;

/**
 * PagerManagerImpl
 * <pre>
 *    Implementacion de servicio para consulta de informacion para paginacion de la tabla
 * <Pre> 
 */
public class PagerManagerImpl extends AbstractManager implements PagerManager {

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(PagerManagerImpl.class);
	
	/**
	 * Se consultan los datos a partir de una serie de atributos
	 * para poder limitar la consulta al tamaño y posición en la tabla.
	 *
	 * @param arg objeto que contiene los parametros para la busqueda
	 * @param endpointName nombre del endpoint al que se va a consultar
	 * @param start linea inicial de consulta
	 * @param limit cantidad de registros a ser consultados
	 * @return Lista con los bean's obtenidos de la consulta
	 * @throws ApplicationException Excepcion con la informacion y descripción del problema en la ejecución
	 * @throws mx.com.aon.core.ApplicationException 
	 */
	public PagedList getPagedData(Object arg, String endpointName, int start, int limit) throws ApplicationException{
		
		PagedList pagedList;
		pagedList = pagedBackBoneInvoke(arg, endpointName, start, limit);
		
		if(logger.isDebugEnabled()){
			logger.debug("pagedList=" + pagedList);
		}
		
        return pagedList;
	}



}
