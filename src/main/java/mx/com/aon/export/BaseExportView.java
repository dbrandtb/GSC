/*
 * AON
 * 
 * Creado el 26/02/2008 11:34:55 a.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.export;

import mx.com.aon.export.model.TableModelExport;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;

/**
 * BaseExportView
 * 
 * <pre>
 *    Clase base para la implementacion de las vistas de exportacion basadas en texto plano
 * <Pre>
 * 
 * @author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&aacute;rez&lt;/a&gt;
 * @version	 1.0
 * 
 * @since	 1.0
 * 
 */
public abstract class BaseExportView implements ExportView {

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(BaseExportView.class);

	/**
	 * Funcion con la que se pide la extension del tipo de archivo a exportar implementada
	 */
	public abstract String getExtension();

	/**
	 * Funcion con la cual se pide el inicio del renglon
	 * @return String con el inicio de cada renglon
	 */
	public abstract String getRowStart();

	/**
	 * Funcion con la cual se pide el final de cada renglon
	 * @return String con el final de cada renglon
	 */
	public abstract String getRowEnd();

	/**
	 * Funcion con la cual se pide el inicio de cada columna o celda
	 * @return String con el inicio de celda
	 */
	public abstract String getCellStart();

	/**
	 * Funcion con la cual se pide el final de cada columna o celda
	 * @return String con el final de celda
	 */
	public abstract String getCellEnd();

	/**
	 * Funcion con la cual se pide el inicio del documento
	 * @return String con el inicio del documento
	 */
	public abstract String getDocumentStart();

	/**
	 * Funcion con la cual se pide el final del documento generado
	 * @return String con el final de documento
	 */
	public abstract String getDocumentEnd();

	/**
	 * Funcion con la cual se construye la cabecera del documento
	 * @param model con la descripcion de las cabeceras de las columnas del documento
	 * @return String con la cabecera construida del documento
	 */
	public abstract String getDocumentHeader( TableModelExport model);

	/**
	 * Funcion que regresa si el documento debe de llevar un inicio
	 * @return boolean
	 */
	protected abstract boolean isAppendDocumentStart();

	/**
	 * Funcion que regresa si el documento debe de llevar un final
	 * @return boolean
	 */
	protected abstract boolean isAppendDocumentEnd();

	/**
	 * Funcion que regresa si la primer celda debe de tener comienzo
	 * @return boolean
	 */
	protected abstract boolean isAppendFisrtCellStart();
	
	/**
	 * Funcion que regresa si la celda debe de llevar inicio
	 * @return boolean
	 */
	protected abstract boolean isAppendCellStart();

	/**
	 * Fincion que regresa si la celda debe de llevar final
	 * @return boolean
	 */
	protected abstract boolean isAppendCellEnd();

	/**
	 * Funcion que regresa si la ultima celda debe de llevar final
	 * @return boolean
	 */
	protected abstract boolean isAppendLastCellEnd();
	
	/**
	 * Funcion que regresa si el renglon debe de llevar inicio
	 * @return boolean
	 */
	protected abstract boolean isAppendRowStart();

	/**
	 * Funcion que regresa si el renglon debe de llevar final
	 * @return boolean
	 */
	protected abstract boolean isAppendRowEnd();

	/**
	 * Funcion que regresa si el documento debe de llevar cabecera
	 * @return boolean
	 */
	protected abstract boolean isAppendDocumentHeader();

	/**
	 * Implementacion de exportacion para los archivos construidos en texto plano implementando
	 * en cada subclase los metodos que describen a cada uno de ellos
	 * @param TableModelExport modelo de los datos a ser exportados
	 */
	public InputStream export(TableModelExport table) {
		// Se inicializa el buffer
		StringWriter out = new StringWriter();
		try {
			// Se inicializa el documento
			if (this.isAppendDocumentStart()) {
				writeOut(out, this.getDocumentStart());
			}
			// Se agrega la cabecera
			if (this.isAppendDocumentHeader()) {
				writeOut(out, this.getDocumentHeader( table ));
			}
			// Se itera a partir de los renglones
			for (int i = 0; i < table.getRowCount(); i++) {
				// Se genera el inicio de el renglon
				if (this.isAppendRowStart()) {
					writeOut(out, this.getRowStart());
				}
				ArrayList<?> row = table.getRow(i);
				// Se itera a partir de las columnas
				for (int j = 0; j < row.size(); j++) {
					// Se agrega el inicio de la celda
					if ( this.isAppendCellStart()) {
						if( j == 0 ){
							if( this.isAppendFisrtCellStart() ){
								writeOut(out, this.getCellStart());
							}
						}else{
							writeOut(out, this.getCellStart());
						}
					}
					// Se escribe la informacion de la celda
					writeOut(out, (row.get(j)!=null)?row.get(j).toString():"");
					// Se agrega el final de la celda
					if (this.isAppendCellEnd()) {
						if( j == row.size()-1 ){
							if( this.isAppendLastCellEnd() ){
								writeOut(out, this.getCellEnd());
							}
						}else{
							writeOut(out, this.getCellEnd());
						}
					}
				}
				// Se agrega el final del renglon
				if (this.isAppendRowEnd()) {
					writeOut(out, this.getRowEnd());
				}
			}
			// Se agrega el final del documento
			if (this.isAppendDocumentEnd()) {
				writeOut(out, this.getDocumentEnd());
			}
		} catch (IOException ioe) {
			logger.error("Error al generar el archivo a ser exportado", ioe);
		}
		// Se transforma el buffer en un stream esperado
		return new ByteArrayInputStream(out.toString().getBytes());
	}

	/**
	 * Se utiliza para poder escribir en el buffer
	 * @param writer buffer de escritura
	 * @param out cadena a ser escrita
	 * @throws IOException posible excepcion lanzada por escritura en Writer
	 */
	private void writeOut(Writer writer, String out) throws IOException {
		if (out != null) {
			writer.write(out);
		}
	}

}
