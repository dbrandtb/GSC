package mx.com.aon.export.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.export.ExportModel;
import mx.com.aon.export.model.TableModelExport;

import org.apache.log4j.Logger;

import mx.com.aon.tmp.Endpoint;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.gseguros.exception.ApplicationException;


public class ExportModelImpl implements ExportModel{

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ExportModelImpl.class);
	
	/**
	 * Atributo inyectado por Spring para la utilizacion del Manager de la consulta 
	 */
	private Map<String, Endpoint> endpoints;
	
	
	public TableModelExport getModel() throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * Implementación del metodo de extracción del model de datos
	 * @return TableModelExport
	 * @throws ApplicationException Excepcion con la informacion y descripción del error de ejecución
	 */
	@SuppressWarnings("unchecked") // Manejo de ArrayList Controlado
	public TableModelExport getModel(String endpointExportName, String[] columnas) throws ApplicationException{
		
		// Se crea el objeto de respuesta
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		
		if(logger.isDebugEnabled()){
			logger.debug("Nombre del Endpoint=" + endpointExportName);
			logger.debug("Columnas=" + columnas);
			logger.debug("Num columnas=" + columnas.length);
		}
		
		try {
			// Se extrae el manager a ser utilizado
			Endpoint manager = endpoints.get(endpointExportName);
			
			// Se hace la petición
			lista = (ArrayList)manager.invoke(null);
			
			// Se inserta la información al objeto de respuesta
			model.setInformation(lista);
			
			// Se agregan los nombre de las columnas del modelo de datos
			model.setColumnName(columnas);
			
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke '" + endpointExportName + "' para exportar", bae);
			throw new ApplicationException("Excepcion al consultarse los recibos");
		}
		
		return model;
	}
	
	
	// TODO Metodo creado para mandar a llamar PLs que necesiten parametros para realizar la exportacion
	/**
	 * Implementación del metodo de extracción del model de datos
	 * @param  endpointExportName, columnas, parameters
	 * @return TableModelExport
	 * @throws ApplicationException Excepcion con la informacion y descripción del error de ejecución
	 */
	@SuppressWarnings("unchecked") // Manejo de ArrayList Controlado
	public TableModelExport getModel(String endpointExportName, String[] columnas, Object parameters) throws ApplicationException{
		
		// Se crea el objeto de respuesta
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		
		if(logger.isDebugEnabled()){
			logger.debug("Nombre del Endpoint=" + endpointExportName);
			logger.debug("Columnas=" + columnas);
			logger.debug("Num columnas=" + columnas.length);
			logger.debug("Parametros=" + parameters.toString());
		}
		
		try {
			// Se extrae el manager a ser utilizado
			Endpoint manager = endpoints.get(endpointExportName);
			
			// Se hace la petición enviandole los parametros
			lista = (ArrayList)manager.invoke(parameters);
			
			// Se inserta la información al objeto de respuesta
			model.setInformation(lista);
			
			// Se agregan los nombre de las columnas del modelo de datos
			model.setColumnName(columnas);
			
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke '" + endpointExportName + "' para exportar", bae);
			throw new ApplicationException("Excepcion al consultarse los recibos");
		}
		
		return model;
	}
	
	
	// GETTERS AND SETTERS 
	
	/**
	 * Metodo utilizado por Spring para inyectar el atributo endpoints
	 */
	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}


}
