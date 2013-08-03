/*
 * AON
 * 
 * Creado el 26/02/2008 11:36:28 a.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.export.views;

import mx.com.aon.export.BaseExportView;
import mx.com.aon.export.model.TableModelExport;

/**
 * CsvView
 * 
 * <pre>
 *    Implementacion de vista de exportacion de formato texto sepadado por comas
 * <Pre>
 * 
 * @author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&aacute;rez&lt;/a&gt;
 * @version	 1.0
 * 
 * @since	 1.0
 * 
 */
public class CsvView extends BaseExportView {

	private static final String EXTENSION = "csv";
	private static final String CELL_END = ",";
	private static final String ROW_END = "\n";
	
	@Override
	public String getExtension() {
		return EXTENSION;
	}

	@Override
	public String getCellEnd() {
		return CELL_END;
	}

	@Override
	public String getCellStart() {
		return null;
	}

	@Override
	public String getDocumentEnd() {
		return null;
	}

	@Override
	public String getDocumentStart() {
		return null;
	}

	@Override
	public String getRowEnd() {
		return ROW_END;
	}

	@Override
	public String getRowStart() {
		return null;
	}

	@Override
	protected boolean isAppendCellEnd() {
		return true;
	}

	@Override
	protected boolean isAppendCellStart() {
		return false;
	}

	@Override
	protected boolean isAppendDocumentEnd() {
		return false;
	}

	@Override
	protected boolean isAppendDocumentStart() {
		return false;
	}

	@Override
	protected boolean isAppendRowEnd() {
		return true;
	}

	@Override
	protected boolean isAppendRowStart() {
		return false;
	}

	@Override
	public String getDocumentHeader(TableModelExport model) {
		StringBuffer buffer = new StringBuffer();
		int countCol = model.getColumnCount();
		for (int i = 0; i < countCol; i++) {
			buffer.append(model.getColumnName(i).toString());
			if( i != countCol -1 ){
				buffer.append(getCellEnd());
			}
		}
		buffer.append(getRowEnd());
		return buffer.toString();
	}

	@Override
	protected boolean isAppendDocumentHeader() {
		return true;
	}

	@Override
	protected boolean isAppendFisrtCellStart() {
		return false;
	}

	@Override
	protected boolean isAppendLastCellEnd() {
		return false;
	}

}
