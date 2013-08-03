/*
 * AON
 * 
 * Creado el 26/02/2008 10:52:31 a.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.export;

import mx.com.aon.export.model.TableModelExport;

import java.io.InputStream;

/**
 * ExportView
 * 
 * <pre>
 *  Interfaz que es utilizada en comun por las vistas para exportacion 
 * <Pre>
 * 
 * @author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&aacute;rez&lt;/a&gt;
 * @version	 1.0
 * 
 * @since	 1.0
 * 
 */
/**
 * @author Administrator
 *
 */
public interface ExportView {

	/**
	 * Función que regresa el tipo de extension utilizada por la vista que es el formato que utiliza para
	 * generación del archivo
	 * @return extension del archivo a ser generado
	 */
	String getExtension();
	
	/**
	 * Metodo que genera a partir de la implementacion la exportación al archivo implementado 
	 * en cada vista
	 * @param tableModelExport Modelo de datos con a ser exportados con la descripción de los mismos
	 * @return InputStream con el flujo de datos generado.
	 */
	InputStream export(TableModelExport tableModelExport);
	
}
