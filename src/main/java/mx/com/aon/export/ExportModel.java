/*
 * AON
 * 
 * Creado el 26/02/2008 12:20:08 p.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.export;

import mx.com.aon.export.model.TableModelExport;
import mx.com.gseguros.exception.ApplicationException;

/**
 * ExportModel
 * 
 * <pre>
 *     Interfaz con la cual se indica a una clase que se espera extraer el modelo de datos
 *     de la exportacion para una vista
 * <Pre>
 * 
 * @author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&aacute;rez&lt;/a&gt;
 * @version	 1.0
 * 
 * @since	 1.0
 * 
 */
public interface ExportModel {
	
	/**
	 * Extrae el modelo de datos a ser exportados
	 * @return TableModelExport
	 * @throws ApplicationException
	 */
	TableModelExport getModel() throws ApplicationException;
	
	
	/**
	 * Extrae el modelo de datos a ser exportados
	 * @return TableModelExport
	 * @throws ApplicationException
	 */
	TableModelExport getModel(String endpointExportName, String[] columnas) throws ApplicationException ;
	
	
	// TODO Metodo creado para mandar a llamar PLs que necesiten parametros para realizar la exportacion
	/**
	 * Extrae el modelo de datos a ser exportados
	 * @param  endpointExportName, columnas, parameters
	 * @return TableModelExport
	 * @throws ApplicationException
	 */
	TableModelExport getModel(String endpointExportName, String[] columnas, Object parameters) throws ApplicationException ;
}
