/*
 * AON
 * 
 * Creado el 22/02/2008 07:14:45 p.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.test.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.test.model.ReciboVO;
import mx.com.aon.test.service.PagingManager;

import org.apache.log4j.Logger;

import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;

/**
 * PagingManagerImpl
 * 
 * <pre>
 *    Implementacion de servicio para consulta de informacion para paginacion de la tabla
 *    de los recibos.
 * <Pre>
 * 
 * @author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&aacute;rez&lt;/a&gt;
 * @version	 1.0
 * 
 * @since	 1.0
 * 
 */
public class PagingManagerImpl implements PagingManager{

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(PagingManagerImpl.class);
	
	/**
	 * Mapa en el cual se introducen los Manager's para ser extraidos y utilizados como servicios
	 */
	private Map<String, Endpoint> endpoints;
	
	/**
	 * Se consultan los recibos a partir de una seria de atributos para poder limitar la 
	 * consulta a el tamaño y posición en la tabla.
	 * 
	 * @param start linea inicial de consulta
	 * @param limit cantidad de registros a ser consultados
	 * @param order atributo por el cual se ordenara la consulta
	 * @param dir direccion ascendente o descendente de la consulta
	 * @return Lista con los bean's generados a partir de la consulta
	 * @throws ApplicationException Excepcion con la informacion y descripción del problema en la ejecución
	 */
	@SuppressWarnings("unchecked") // Manejo de ArrayList Controlado
	public List<ReciboVO> getRecibos(String start, String limit, String order, String dir) throws ApplicationException {
		List<ReciboVO> lista = null;
		
		try {
			// Se extrae el endpoint
			Endpoint manager = endpoints.get("CONSULTA_PAGINADA");
			
			// Se crea un mapa para pasar los parametros de ejecucion al endpoint
			HashMap map = new HashMap();
			map.put("start",start);
			map.put("limit",limit);
			map.put("order",order);
			map.put("dir",dir);
			
			// Se hace la petición y dada la transformación se espera una lista de ReciboVO 
			ArrayList<ReciboVO> invoke = (ArrayList<ReciboVO>)manager.invoke( map );
			
			lista = invoke;
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'Consulta Recibos'",bae);
			throw new ApplicationException("Excepcion al consultarse los recibos");
		}
		
		return lista;
	}

	// GETTERS AND SETTERS 
	
	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}

}
