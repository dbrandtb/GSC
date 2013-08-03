/*
 * AON
 * 
 * Creado el 07/04/2008 14:48:45 p.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.portal.web;

import java.io.InputStream;
import java.util.List;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.EstructuraVO;
import mx.com.aon.portal.service.EstructuraManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Mantenimiento de Estructuras.
 *   
 *   @extends AbstractListAction
 * 
 */
public class ListaManttoEstructuraAction extends AbstractListAction{
	
	private static final long serialVersionUID = 1313135445454545L;

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento.
	 */
	private static Logger logger = Logger.getLogger(ListaManttoEstructuraAction.class);
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD.
	 * Este objeto no es serializable.
	 */
	private transient EstructuraManager estructuraManager;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo EstructuraVO
	 * con los valores de la consulta.
	 */
	private List<EstructuraVO> mEstructuraList;
	
	/**
	 * Atributo de requerimiento para busqueda.
	 */
	private String codigo;
	
	private String descripcion;
	private String descripcionEscapedJavaScript;
	
	/**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado.
	 */
	private String formato;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado.
	 */
	private String filename;
		
	/**
	 * Atributo inyectado por spring el cual direcciona a traves del tipo de formato para generar 
	 * el archivo a ser exportado.
	 */
	private ExportMediator exportMediator;
	
	/**
	 * Metodo que realiza la busqueda de un conjunto de estructuras.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick()throws Exception
	{
		try
		{
			PagedList pagedList  = estructuraManager.buscarEstructuras(start, limit, descripcion);
			mEstructuraList = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
            success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = true;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	/**
	 * Metodo usado para redireccionar a otra pagina.
	 * 
	 * @param
	 * 
	 * @return string que es usado en struts para redireccionar a otra pagina.
	 * 
	 */
	public String cmdIrConfigurarEstructuraClick(){
		return "configurarEstructura";
	}
	
	/**
	 * Obtiene un conjunto de estructuras y exporta el resultado en Formato PDF, Excel, CSV, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportEstructuraClick()throws Exception{		
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}		
		try {
            contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }
            ExportView exportFormat = (ExportView)exportMediator.getView(formato);
			filename = "Recibos." + exportFormat.getExtension();			
			TableModelExport model = estructuraManager.getModel(descripcion);			
			inputStream = exportFormat.export(model);			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}		
		return SUCCESS;
	}	
	
	public void setEstructuraManager(EstructuraManager pagingManager)
	{
		this.estructuraManager = pagingManager;
	}
	
	public String getCodigo() {return codigo;}
	public void setCodigo(String codigo) {this.codigo = codigo;}
	

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		this.descripcionEscapedJavaScript = StringEscapeUtils.escapeJavaScript(descripcion);
	}
	
	public List<EstructuraVO> getMEstructuraList() {return mEstructuraList;}
	public void setMEstructuraList(List<EstructuraVO> mEstructuraList) {this.mEstructuraList = mEstructuraList;}
	
	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}

	public String getDescripcionEscapedJavaScript() {
		return descripcionEscapedJavaScript;
	}

}