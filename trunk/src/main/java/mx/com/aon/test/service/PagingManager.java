/*
 * AON
 * 
 * Creado el 22/02/2008 07:11:00 p.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.test.service;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.test.model.ReciboVO;

/**
 * PagingManager
 * 
 * <pre>
 *    Interfaz para petición de información paginada 
 * <Pre>
 * 
 * @author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&aacute;rez&lt;/a&gt;
 * @version	 1.0
 * 
 * @since	 1.0
 * 
 */
public interface PagingManager {
	
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
	List<ReciboVO> getRecibos(String start, String limit, String order, String dir) throws ApplicationException;

}
