package mx.com.aon.portal.web.plan;

import java.io.InputStream;

import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportModel;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import mx.com.aon.portal.util.Util;


import com.opensymphony.xwork2.ActionSupport;
/**
 * 
 * @author sergio.ramirez
 *
 */
public class ExportPlanAction extends ActionSupport{

	/**
	 *Serial Version. 
	 */
	private static final long serialVersionUID = 2381478646121283015L;
	private static final transient Log logger= LogFactory.getLog(ExportPlanAction.class);
	private String formato;
	private String contentType;
	private InputStream inputStream;
	private String filename;
	private ExportModel exportModel;
	private ExportMediator exportMediator;
	/**
	 * Metodo que se encarga de asignar el formato a exportar y el nombre del archivo generado.
	 */
	public String execute(){
		if(logger.isDebugEnabled()){
			logger.debug("Formato:" + formato);
		}
		try{

            contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato);
			filename = "Planes."+ exportFormat.getExtension();
			TableModelExport model = exportModel.getModel();
			inputStream = exportFormat.export(model); 
		}catch (Exception e) {
			logger.error("Error al generar documento", e);
		}
	return SUCCESS;	
	}
	/**
	 * 
	 * @return
	 */
	public String getFormato() {
		return formato;
	}
	/**
	 * 
	 * @param formato
	 */
	public void setFormato(String formato) {
		this.formato = formato;
	}
	/**
	 * 
	 * @return
	 */
	public InputStream getInputStream() {
		return inputStream;
	}
	/**
	 * 
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	/**
	 * 
	 * @return
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * 
	 * @param filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * 
	 * @param exportModel
	 */
	public void setExportModel(ExportModel exportModel) {
		this.exportModel = exportModel;
	}
	/**
	 * 
	 * @param exportMediator
	 */
	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}
	

}
