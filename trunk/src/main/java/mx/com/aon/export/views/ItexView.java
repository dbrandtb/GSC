package mx.com.aon.export.views;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;

public abstract class ItexView implements ExportView {

	private static final String EMPTY_STRING = "Página ";

	private static Logger logger = Logger.getLogger(ItexView.class); 
	
	private static Font SMALL_FONT = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new Color(50, 50, 50));
	
	abstract void initDocument(Document document, OutputStream out) throws DocumentException;
	
	/**
	 * Inicializa la tabla general del documento
	 * @param TableModelExport modelo de datos con la informacion a exportar
	 * @return Tabla principal del documento
	 * @throws BadElementException
	 */
	private Table initTable( TableModelExport model ) throws BadElementException{
	    Table tablePDF = new Table( model.getColumnCount() );
	    tablePDF.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        //tablePDF.setDefaultVerticalAlignment(Element.ALIGN_TOP);
        tablePDF.setCellsFitPage(true);
        tablePDF.setWidth(100);
        tablePDF.setPadding(2);
        tablePDF.setSpacing(0);
        return tablePDF;
	}
	
	/**
	 * Escribe la informacion en la tabla principal
	 * @param TableModelExport Model con la informacion a exportar
	 * @param Table tabla principal en donde ser escrita la informacion
	 * @throws BadElementException
	 */
	private void generateTable(TableModelExport model, Table table) throws BadElementException{
		generateHeader(model, table);
		table.endHeaders();
		boolean sombra = false;
		for (int i = 0; i < model.getRowCount(); i++) {
			
			ArrayList<?> listCol = model.getRow(i);

			for (int j = 0; j < listCol.size(); j++) {
				
				Cell cell = getCell(ObjectUtils.toString( listCol.get(j) ), sombra);
                table.addCell(cell);
				
			}
			sombra = !sombra;
			
		}
		
	}
	
	/**
	 * Genera la cabecera con los nombres de las columnas en la tabla
	 * @param TableModelExport Model con la informacion a exportar
	 * @param Table tabla principal en donde ser escrita la informacion
	 * @throws BadElementException
	 */
	private void generateHeader(TableModelExport model, Table table) throws BadElementException{
		for (int i = 0; i < model.getColumnCount(); i++) {
	        Cell cell = new Cell(new Chunk(StringUtils.trimToEmpty( model.getColumnName(i)), SMALL_FONT));
	        cell.setVerticalAlignment(Element.ALIGN_TOP);
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell.setLeading(8);
	        cell.setBackgroundColor(new Color(206,115,131));
	        cell.setHeader(true);
	        table.addCell(cell);
		}
	}
	
	/**
	 * Escribe la celda con la informacion correspondiente
	 * @param value informacion de la celda a ser escrita
	 * @param sombra si la celda utiliza sombra
	 * @return celda construida con la informacion
	 * @throws BadElementException
	 */
    private Cell getCell(String value, boolean sombra) throws BadElementException
    {
        Cell cell = new Cell(new Chunk(StringUtils.trimToEmpty(value), SMALL_FONT));
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        if( sombra ){
        	cell.setGrayFill(0.95f);
        }
        cell.setLeading(8);
        return cell;
    }

    /**
     * Exporta la informacion a componentes iText
     */
	public InputStream export(TableModelExport model) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			Table table = initTable(model);
            Document document = new Document(PageSize.LETTER.rotate(), 60, 60, 40, 40);
            document.addCreationDate();
            HeaderFooter footer = new HeaderFooter(new Phrase(EMPTY_STRING, SMALL_FONT), true);
            footer.setBorder(Rectangle.NO_BORDER);
            footer.setAlignment(Element.ALIGN_CENTER);

            initDocument(document, out);

            generateTable(model,table);
            
            document.open();
            document.setFooter(footer);
            document.add(table);
            document.close();
			
		} catch (BadElementException bee) {
			logger.error("Exception in create table", bee);
		} catch (DocumentException de) {
			logger.error("Exception in create document", de);
		}
		return new ByteArrayInputStream( out.toByteArray() );
	}
}
