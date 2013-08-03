package mx.com.aon.portal.web;

import java.io.InputStream;
import java.util.Map;

import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportModel;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class ExportadorAction extends ActionSupport implements SessionAware{

	private static final long serialVersionUID = -107774606522827478L;

	private static Logger logger = Logger.getLogger(ExportadorAction.class);

	@SuppressWarnings("unchecked")
	private Map session;

	/**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
	 */
	private String formato;

	/**
	 * Atributo agregado por struts que contiene el nombre del archivo que sera exportado
	 */
	private String nombreArchivo;

	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;

	/**
	 * Atributo de respuesta interpretado por Struts2 con el nombre del archivo generado
	 */
	private String filename;

	/**
	 * Atributo inyectado por spring con el servicio de consulta de datos para exportar
	 */
	private ExportModel exportModel;
	private String contentType;

	/**
	 * Atributo inyectado por spring el cual direcciona a traves del tipo de formato para generar
	 * el archivo a ser exportado
	 */
	private ExportMediator exportMediator;
	/**
	 * Atributo inyectado por struts el cual contiene los parametros para invocar al endpoint
	 */
	private Map<String,String> parameters;

	/**
	 * Servicio que atiende las peticiones del action
	 */
	@SuppressWarnings("unchecked")
	public String execute(){

		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
			logger.debug( "nombreArchivo : " + nombreArchivo );
		}

		try {
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }
			// Se extrae la vista con la cual se exportara el archivo a partir de su formato
			ExportView exportFormat = (ExportView)exportMediator.getView(formato);

			// Se genera el nombre del archivo con la extension correspondiente al tipo de formato
			if(StringUtils.isBlank(nombreArchivo)){
				filename = "export." + exportFormat.getExtension();
			}else{
				filename = nombreArchivo + "." + exportFormat.getExtension();
			}
			logger.debug( "NOMBRE_COLUMNAS : " + session.get("NOMBRE_COLUMNAS") );
			logger.debug( "ENDPOINT_EXPORT_NAME : " + session.get("ENDPOINT_EXPORT_NAME") );
			if(  session.get("NOMBRE_COLUMNAS") != null && session.get("ENDPOINT_EXPORT_NAME") != null){
				logger.debug("inicia export model");
				// Se extrae el modelo de datos para ser exportado
				TableModelExport model = exportModel.getModel( (String)session.get("ENDPOINT_EXPORT_NAME"), (String[])session.get("NOMBRE_COLUMNAS") );

				logger.debug("model=" + model);
				// Se transforma por el mecanismo de exportacion el modelo a el flujo de datos de respuesta
				inputStream = exportFormat.export(model);
				logger.debug("inputStream=" + inputStream);
				if (logger.isDebugEnabled()) {
					logger.debug("model num renglones=" + model.getRowCount());
					logger.debug("model num columnas=" + model.getColumnCount());
					logger.debug("inputStream=" + inputStream);
				}
			}

		} catch (Exception e) {
			logger.error("Exception en ExportadorAction ",e);
		}
		// Se finaliza el flujo del action.
		return SUCCESS;
	}
	
	/**
	 * Metodo que se ejecuta para garantizar que el RedirectFilter funcione adecuadamente al Exportar
	 */
	public String checkSession(){
		//Este metodo se hizo para checar si la session ya esta expirada antes de Exportar, ya que la forma en la que se ha programado el RedirectFilter
		//impide saber si la session ya expiró.
		
		if(logger.isDebugEnabled())logger.debug("Verifying session before Export");
	return SUCCESS; 
	}


	// TODO Metodo creado para mandar a llamar PLs que necesiten parametros para realizar la exportacion
	// TODO Se dejara que los parametros se manden por sesion, despues se implementara para que se manden por request
	@SuppressWarnings("unchecked")
	public String generaArchivo(){
		try {
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }

			// Se extrae la vista con la cual se exportara el archivo a partir de su formato
			ExportView exportFormat = (ExportView)exportMediator.getView(formato);

			// Se genera el nombre del archivo con la extension correspondiente al tipo de formato
			if(StringUtils.isBlank(nombreArchivo)){
				filename = "export." + exportFormat.getExtension();
			}else{
				filename = nombreArchivo + "." + exportFormat.getExtension();
			}

			if(  session.get("NOMBRE_COLUMNAS") != null && session.get("ENDPOINT_EXPORT_NAME") != null && session.get("PARAMETROS_EXPORT") != null){

				// Se extrae el modelo de datos para ser exportado
				TableModelExport model = exportModel.getModel( (String)session.get("ENDPOINT_EXPORT_NAME"), (String[])session.get("NOMBRE_COLUMNAS"), (Map<String,Object>)session.get("PARAMETROS_EXPORT") );

				// Se transforma por el mecanismo de exportacion el modelo a el flujo de datos de respuesta
				inputStream = exportFormat.export(model);

				if (logger.isDebugEnabled()) {
					logger.debug("model num renglones=" + model.getRowCount());
					logger.debug("model num columnas=" + model.getColumnCount());
					logger.debug("inputStream=" + inputStream);
				}
			}

		} catch (Exception e) {
			logger.error("Exception en ExportadorAction ",e);
		}

		// Se finaliza el flujo del action.
		return SUCCESS;
	}
	//Getters and setters

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

	public ExportModel getExportModel() {
		return exportModel;
	}

	public void setExportModel(ExportModel exportModel) {
		this.exportModel = exportModel;
	}

	public ExportMediator getExportMediator() {
		return exportMediator;
	}

	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}


	public String getNombreArchivo() {
		return nombreArchivo;
	}


	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
	}


	/**
	 * @return the parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}


	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}


	public String getContentType() {
		return contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
