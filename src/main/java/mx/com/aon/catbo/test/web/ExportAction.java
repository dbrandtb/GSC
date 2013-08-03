/*
 * AON
 * 
 * Creado el 22/02/2008 07:23:13 p.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.catbo.test.web;

import com.opensymphony.xwork2.ActionSupport;

import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportModel;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;

import org.apache.log4j.Logger;

import java.io.InputStream;


/**
 * ExportAction
 * 
 * <pre>
 *    Action que atiende la petición individual a la exportacion de datos a distintos formatos 
 * <Pre>
 * 
 * @author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&aacute;rez&lt;/a&gt;
 * @version	 1.0
 * 
 * @since	 1.0
 * 
 */
public class ExportAction extends ActionSupport{

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ExportAction.class);
	
	/**
	 * UID por defecto
	 */
	private static final long serialVersionUID = 7055008332362027393L;

	/**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
	 */
	private String formato;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado 
	 */
	private String filename;
	
	/**
	 * Atributo inyectado por spring con el servicio de consulta de datos para esportar
	 */
	private ExportModel exportModel;
	
	private String contentType;
	
	/**
	 * Atributo inyectado por spring el cual direcciona a travez del tipo de formato para generar 
	 * el archivo a ser exportado
	 */
	private ExportMediator exportMediator;
	
	/**
	 * Servicio que atiende las peticiones del action 
	 */
	@SuppressWarnings("unchecked")
	public String execute(){
		
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
		     contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }

			// Se extrae la vista con la cual se exportara el archivo a partir de su formato
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			
			// Se genera el nombre del archivo con la extension correspondiente al tipo de formato
			filename = "Recibos." + exportFormat.getExtension();
			
			// Se extrae el modelo de datos para ser exportado
			TableModelExport model = exportModel.getModel();
			
			// Se transforma por el mecanismo de exportacion el modelo a el flujo de datos de respuesta 
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		// Se finaliza el flujo del action.
		return SUCCESS;
	}


	// Getters and Setters

	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}
	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportModel(ExportModel exportModel) {this.exportModel = exportModel;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}


	
}
