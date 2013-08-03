/*
 * AON
 * 
 * Creado el 22/02/2008 07:38:37 p.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.test.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportModel;
import mx.com.aon.export.model.TableModelExport;

import org.apache.log4j.Logger;

import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;

/**
 * exportReciboManagerImpl
 * 
 * <pre>
 *    Implementación para la extracción del modelo de datos para la exportación de los mismos a
 *    el formato necesitado 
 * <Pre>
 * 
 * @author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&aacute;rez&lt;/a&gt;
 * @version	 1.0
 * 
 * @since	 1.0
 * 
 */
public class ExportReciboManagerImpl implements ExportModel{

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ExportReciboManagerImpl.class);
	
	/**
	 * Atributo inyectado por Spring para la utilizacion del Manager de la consulta 
	 */
	private Map<String, Endpoint> endpoints;
	
	/**
	 * Implementación del metodo de extracción del model de datos
	 * @throws ApplicationException Excepcion con la informacion y descripción del error de ejecución
	 */
	@SuppressWarnings("unchecked") // Manejo de ArrayList Controlado
	public TableModelExport getModel() throws ApplicationException {
		
		// Se crea el objeto de respuesta
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		try {
			// Se extrae el manager a ser utilizado
			Endpoint manager = endpoints.get("EXPORT_RECIBO");
			
			// Se hace la petición
			lista = (ArrayList)manager.invoke(null);
			
			// Se inserta la información al objeto de respuesta
			model.setInformation(lista);
			
			// Se agregan los nombre de las columnas del modelo de datos
			model.setColumnName(new String[]{"NMPOLIZA","NMSUPLEM","NMRECIBO","FEINICIO"});
			
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'Consulta Recibos' para exportar",bae);
			throw new ApplicationException("Excepcion al consultarse los recibos");
		}
		
		return model;
	}

	// GETTERS AND SETTERS 
	
	public void setEndpoints(Map<String, Endpoint> endpoints) {this.endpoints = endpoints;}

	public TableModelExport getModel(String endpointExportName,
			String[] columnas) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public TableModelExport getModel(String endpointExportName,
			String[] columnas, Object parameters) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
