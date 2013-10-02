package mx.com.aon.portal.service.impl.reportes;

import java.util.Map;

import mx.com.aon.export.ExportModel;
import mx.com.aon.export.model.TableModelExport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mx.com.aon.tmp.Endpoint;
import mx.com.gseguros.exception.ApplicationException;

public class ExportReportesManagerImpl implements ExportModel {

	private static final transient Log log = LogFactory.getLog(ExportReportesManagerImpl.class);
	private Map<String, Endpoint> endpoints;

	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}
	/**
	 * Metodo que realiza el servicio de exportacion y formato del archivo.
	 */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel() throws ApplicationException {
		log.debug("ExportReportesManagerImpl.getModel() dummy");
		return null;
	}
	//TODO: Quitar metodo dummy y reemplazar el uso de esta clase por ExportModelImpl
	public TableModelExport getModel(String endpointExportName,
			String[] columnas) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
	//TODO: Quitar metodo dummy y reemplazar el uso de esta clase por ExportModelImpl
	public TableModelExport getModel(String endpointExportName,
			String[] columnas, Object parameters) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
