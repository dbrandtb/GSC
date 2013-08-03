/*
 * AON
 * 
 * Creado el 26/02/2008 12:22:53 p.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.export.impl;

import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;

import java.util.Map;

/**
 * ExportMediatorImpl
 * 
 * <pre>
 *     Implementacion del mediador con el cual se obtiene la vista apropiada para exportar la informacion
 *     a partir del formato
 * <Pre>
 * 
 * @author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&aacute;rez&lt;/a&gt;
 * @version	 1.0
 * 
 * @since	 1.0
 * 
 */
public class ExportMediatorImpl implements ExportMediator {

	/**
	 * Mapa en el cual son agregados los formatos por inyeccion desde spring
	 */
	@SuppressWarnings("unchecked")
	private Map export;

	/**
	 * implementacion que extrae de las vistas registrados la indicada por el formato
	 */
	public ExportView getView(String formato){
		ExportView exportFormat = (ExportView)export.get(formato);
		return exportFormat;
	}
	
	/**
	 * Se agrega el mapa con los formatos y las vistas asociadas
	 * @param export
	 */
	@SuppressWarnings("unchecked")
	public void setExport(Map export) {this.export = export;}


}
