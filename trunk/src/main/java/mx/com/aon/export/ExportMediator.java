/*
 * AON
 * 
 * Creado el 26/02/2008 12:21:26 p.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.export;

/**
 * ExportMediator
 * 
 * <pre>
 *     Interfaz para utilizar un mediador de exportacion a partir del formato a ser exportado
 * <Pre>
 * 
 * @author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&aacute;rez&lt;/a&gt;
 * @version	 1.0
 * 
 * @since	 1.0
 * 
 */
public interface ExportMediator {
	
	/**
	 * Construccion de la vista a partir del formato a ser exportado 
	 * @param formato
	 * @return ExportView
	 */
	ExportView getView(String formato);
	
}
