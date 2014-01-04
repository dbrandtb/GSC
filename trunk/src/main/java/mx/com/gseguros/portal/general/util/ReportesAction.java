package mx.com.gseguros.portal.general.util;

import java.io.InputStream;
import java.util.HashMap;

import mx.com.gseguros.portal.general.service.ReportesManager;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class ReportesAction extends ActionSupport {

	private static final long serialVersionUID = 5866297387639852014L;

	private static Logger logger = Logger.getLogger(ReportesAction.class);

	private ReportesManager reportesManager;

	private InputStream fileInputStream;
	private String filename;
	protected String contentType;

	protected boolean success;
	
	/**
	 * Metodo para la descarga de los archivos de los Movimientos en los casos
	 * de BO
	 * 
	 * @return
	 */
	public String obtieneReporteExcel() {
		
		logger.debug(">>>>>>>>>>>>>>  Obtiene reporte Excel <<<<<<<<<<<<<");

		contentType = "application/vnd.ms-excel";
		filename = "Prueba.xls";
		logger.debug("filename: " + filename);
		logger.debug("contentType: " + contentType);
		
		try {
		
			HashMap<String,Object> params = new HashMap<String, Object>();
			params.put("pv_idreporte_i", "Prueba");
			params.put("pv_codusr_i", "ICE");
			
			fileInputStream = reportesManager.obtieneReporteExcel(params);
			logger.debug("Este es el InputStream: "+ fileInputStream);
			
		} catch (Exception e) {
			logger.error("Error al ejecutar obtieneReporte",e);
		}

		success = true;
		return SUCCESS;
	}
	
	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setReportesManager(ReportesManager reportesManager) {
		this.reportesManager = reportesManager;
	}

}
