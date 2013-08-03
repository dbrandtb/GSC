/*
 * AON
 * 
 * Creado el 26/02/2008 11:39:05 a.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.export.views;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * XmlDomView
 * 
 * <pre>
 *    Implementacion de vista de exportacion en XML construido en DOM.
 * <Pre>
 * 
 * @author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&aacute;rez&lt;/a&gt;
 * @version	 1.0
 * 
 * @since	 1.0
 * 
 */
public class XmlDomView implements ExportView {

	private static Logger logger = Logger.getLogger(XmlDomView.class);
	
	private static final String EXTENSION = "dom.xml";
	private static final String ENCODING = "ISO-8859-1";
	private static final String ROOT = "export";
	private static final String ROW = "row";

	/**
	 * Regresa la extension del archivo generado
	 */
	public String getExtension() {
		return EXTENSION;
	}

	/**
	 * Genera el stream con el archivo generado a partir del model pasado por parametro
	 * @param TableModelExport Modelo de la informacion a ser exportada
	 * @return InputStream con el archivo generado
	 */
	public InputStream export(TableModelExport model) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// Se inicializa la fabrica para generar el Documento en DOM
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = factory.newDocumentBuilder();
			// Se crea el documento nuevo en DOM
			Document doc = parser.newDocument();
			Element root = doc.createElement(ROOT);
			// Se crea la informacion con el modelo
			createDocument(doc, root ,model);
			doc.appendChild(root);
			// Se transforma la salida a un stream
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.ENCODING, ENCODING);
	        DOMSource source = new DOMSource(doc);
	        StreamResult result =  new StreamResult(baos);
	        transformer.transform(source, result);
	        
		} catch (ParserConfigurationException pce) {
			logger.error("Error al generar el documento XML con DOM",pce);
		} catch (TransformerConfigurationException tce) {
			logger.error("Error al configurar la transformacion del XML",tce);
		} catch (TransformerException te) {
			logger.error("Error al transformar el XML",te);
		}
		// Se transforma el stream para el stream esperado
		return new ByteArrayInputStream( baos.toByteArray() );
	}

	/**
	 * Genera el documento con la informacion a partir de los elementos DOM y el modelo. 
	 * @param doc Documento principal DOM
	 * @param root Elemento raiz del documento
	 * @param model modelo de la informacion a exportar
	 */
	private void createDocument(Document doc, Element root, TableModelExport model) {

		String[] names = new String[ model.getColumnCount() ];
		for (int i = 0; i < model.getColumnCount(); i++) {
			names[i] = model.getColumnName(i).toLowerCase();
		}
		for (int i = 0; i < model.getRowCount(); i++) {
			ArrayList<?> rowList = model.getRow(i);
			Element row = doc.createElement(ROW);
			for (int j = 0; j < rowList.size(); j++) {
				Element cell = doc.createElement(names[j]);
				cell.appendChild(doc.createTextNode(rowList.get(j).toString()));
				row.appendChild(cell);
			}
			root.appendChild(row);
		}
	}

}
