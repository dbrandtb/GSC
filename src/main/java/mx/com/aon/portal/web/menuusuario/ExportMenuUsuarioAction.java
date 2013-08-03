package mx.com.aon.portal.web.menuusuario;

import java.io.InputStream;

import com.opensymphony.xwork2.ActionSupport;

import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportModel;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 
 * @author sergio.ramirez
 *
 */
public class ExportMenuUsuarioAction extends ActionSupport{

	/**
	 *Serial Version 
	 */
	private static final long serialVersionUID = 5321518684242248834L;
	private static final transient Log log= LogFactory.getLog(ExportMenuUsuarioAction.class);
	private String formato;
	private InputStream inputStream;
	private String filename;
	private String contentType;
	private ExportModel exportModel;
	private ExportMediator exportMediator;
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
			filename = "Configuraciones."+ exportFormat.getExtension();
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
