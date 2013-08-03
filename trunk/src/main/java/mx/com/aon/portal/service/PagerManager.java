package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;

public interface PagerManager {
	
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
	 */
	PagedList getPagedData(Object arg, String endpointName, int start, int limit) throws ApplicationException;
}
