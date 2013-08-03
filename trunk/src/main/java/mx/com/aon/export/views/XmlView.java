/*
 * AON
 * 
 * Creado el 26/02/2008 11:37:05 a.m. (dd/mm/aaaa hh:mm:ss)
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
 * XmlView
 * 
 * <pre>
 *     implementacion de vista de exportacion en xml basada en texto plano
 * <Pre>
 * 
 * @author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&aacute;rez&lt;/a&gt;
 * @version	 1.0
 * 
 * @since	 1.0
 * 
 */
public class XmlView extends BaseExportView {

	private static final String EXTENSION = "xml";
	private static final String CELL_START = "<cell><![CDATA[";
	private static final String CELL_END = "]]></cell>";
	private static final String ROW_START = "<row>";
	private static final String ROW_END = "</row>\n";
	private static final String HEADER_START = "<header>";
	private static final String HEADER_END = "</header>\n";
	private static final String HEADER_CELL_START = "<name>";
	private static final String HEADER_CELL_END = "</name>";
	private static final String DOC_START = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<table>\n";
	private static final String DOC_END = "</table>";
	
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
		return CELL_START;
	}

	@Override
	public String getDocumentEnd() {
		return DOC_END;
	}

	@Override
	public String getDocumentStart() {
		return DOC_START;
	}

	@Override
	public String getRowEnd() {
		return ROW_END;
	}

	@Override
	public String getRowStart() {
		return ROW_START;
	}

	@Override
	protected boolean isAppendCellEnd() {
		return true;
	}

	@Override
	protected boolean isAppendCellStart() {
		return true;
	}

	@Override
	protected boolean isAppendDocumentEnd() {
		return true;
	}

	@Override
	protected boolean isAppendDocumentStart() {
		return true;
	}

	@Override
	protected boolean isAppendRowEnd() {
		return true;
	}

	@Override
	protected boolean isAppendRowStart() {
		return true;
	}

	@Override
	public String getDocumentHeader(TableModelExport model) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(HEADER_START);
		for (int i = 0; i < model.getColumnCount(); i++) {
			buffer.append(HEADER_CELL_START);
			buffer.append(model.getColumnName(i).toString());
			buffer.append(HEADER_CELL_END);
		}
		buffer.append(HEADER_END);
		return buffer.toString();
	}

	@Override
	protected boolean isAppendDocumentHeader() {
		return true;
	}

	@Override
	protected boolean isAppendFisrtCellStart() {
		return true;
	}

	@Override
	protected boolean isAppendLastCellEnd() {
		return true;
	}

}
