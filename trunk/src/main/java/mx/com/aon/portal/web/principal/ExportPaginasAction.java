package mx.com.aon.portal.web.principal;

import java.io.InputStream;

import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportModel;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;

public class ExportPaginasAction extends ActionSupport{

	/**
	 *@see
	 *Serial Version 
	 */
	private static final long serialVersionUID = 4933144116874509903L;
	private static final transient Log log= LogFactory.getLog(ExportConfiguracionAction.class);
	private String formato;
	private InputStream inputStream;
	private String filename;
	private ExportModel exportModel;
	private ExportMediator exportMediator;
	private String contentType;
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	/**
	 * Metodo que se encarga de asignar el formato a exportar y el nombre del archivo generado.
	 */
	public String execute(){
		if(log.isDebugEnabled()){
			log.debug("Formato:" + formato);
		}
		try{
			contentType = Util.getContentType(formato);
            if (log.isDebugEnabled()) {
                log.debug( "content-type : " + contentType );
            }
			
			ExportView exportFormat = (ExportView)exportMediator.getView(formato);
			filename = "Paginas."+ exportFormat.getExtension();
			TableModelExport model = exportModel.getModel();
			inputStream = exportFormat.export(model); 
		}catch (Exception e) {
			log.error("Error al generar documento", e);
		}
	return SUCCESS;	
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setExportModel(ExportModel exportModel) {
		this.exportModel = exportModel;
	}

	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}

}
