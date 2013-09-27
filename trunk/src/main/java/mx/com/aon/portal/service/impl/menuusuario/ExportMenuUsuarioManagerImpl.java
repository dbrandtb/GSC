package mx.com.aon.portal.service.impl.menuusuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mx.com.aon.tmp.Endpoint;
import mx.com.aon.tmp.BackboneApplicationException;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportModel;
import mx.com.aon.export.model.TableModelExport;
/**
 * 
 * @author sergio.ramirez
 *
 */
public class ExportMenuUsuarioManagerImpl implements ExportModel {

	private static final transient Log log = LogFactory.getLog(ExportMenuUsuarioManagerImpl.class);
	private Map<String, Endpoint> endpoints;

	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}
	/**
	 * Metodo que realiza el servicio de exportacion y formato del archivo.
	 */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel() throws ApplicationException {
		TableModelExport model = new TableModelExport();
		List lista = null;
		try {
			Endpoint manager = endpoints.get("EXPORT_CONFIGURACIONES");
			lista = (ArrayList) manager.invoke(null);
			model.setInformation(lista);
			model.setColumnName(new String[] { "ROL", "CLIENTE", "SECCION", "NOMBRE" , "VISIBLE" });

		} catch (BackboneApplicationException e) {
			log.error("Excepcion en invoke 'Consulta Configuracion' para exportar",e);
			throw new ApplicationException( "Excepcion al consultarse las configuraciones");
		}

		return model;
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
