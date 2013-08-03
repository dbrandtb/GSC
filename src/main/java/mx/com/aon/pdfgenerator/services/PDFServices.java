package mx.com.aon.pdfgenerator.services;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.pdfgenerator.util.Campo;

import org.apache.log4j.Logger;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;


public class PDFServices {
	
	private static Logger logger = Logger.getLogger(PDFServices.class);

    /**
     * Servicio que genera el archivo PDF con el nombre y ruta especificada.
     * 
     * @author 
     * @version 1.0 
     * @param backgroundColor color de fondo, array tamanio 3, por default es blanco
     * @param size  tamanio de la hoja, array de tamanio 2, por default es letter
     * @param nameFile
     * @param path
     * @return docPDF
     */
    public static Document createPDF(String path, String nameFile, float size[], int backgroundColor[]) {
        
    	logger.debug("El lugar donde será creado el pdf de recibos es: "+path+nameFile);
    	Document documentPDF = null;
        Rectangle pageSize = null;
        Color colorFondo = null;
        
        int r = 0;
        int g = 0;
        int b = 0;

        if (backgroundColor != null && backgroundColor.length == 3) {
            r = backgroundColor[0];
            g = backgroundColor[1];
            b = backgroundColor[2];
            colorFondo = new Color(r, g, b);
        } else {
            r = 255;
            g = 255;
            b = 255;
            colorFondo = new Color(r, g, b);
        }

        try {
            if (size != null) {
                pageSize = new Rectangle(PageSize.LETTER);
                pageSize.setBackgroundColor(colorFondo);
                documentPDF = new Document(pageSize, size[0], size[1], size[2], size[3]);
            } else {
                pageSize = new Rectangle(PageSize.LETTER);
                pageSize.setBackgroundColor(colorFondo);
                documentPDF = new Document(pageSize);
            }
            
            
            //PdfWriter.getInstance(documentPDF, new FileOutputStream(path + nameFile));
        /*    
        } catch (DocumentException e) {
            logger.error("DocumentException: createPDF() " , e);            
        } catch (FileNotFoundException e) {            
            logger.error("FileNotFoundException: createPDF() ", e);
        */            
        } catch (Exception e) {            
           logger.error("Exception: createPDF() ", e);
        }
        return documentPDF;
    }// Fin método createPDF();
    
    
    /**
	 *
	 * @return PdfPTable
	 */
	public static PdfPTable createHead(String pathImage, String dsMoneda, String poliza, 
										String reporte, String [] encabezado){
		String  [] encabezados = null;
		
		if("caratula".equalsIgnoreCase(reporte)){
			encabezados = new String []{"Moneda:", encabezado[0], encabezado[1], 
										encabezado[2], "         Póliza:"};
			
		}else if("recibo_pago".equalsIgnoreCase(reporte)){
			encabezados = new String []{"Moneda:", "", "LIQUIDACION DE PRIMAS", 
										"Automóviles/Auto Colectiva","", ""};
		}
		
		float [] widthsTableHead = {1f, 1f, 1.3f};
		PdfPTable tableHead = new PdfPTable(widthsTableHead);
		PdfPTable table = null;
		PdfPTable tableOneColumn = null;		
		PdfPCell cellLeft = null;
		PdfPCell cellCenter = null;
		PdfPCell cellRight = null;
		PdfPCell cell = null;
		
		Image logo = null;
		Paragraph parrafo = null;		
		Font font = null;
		
		try{
			logo = Image.getInstance(pathImage);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(logo== null){
			byte data[] = new byte[100*100*3];
			for (int k = 0; k < 100; ++k) {
				for (int j = 0; j < 300; j += 3) {
	    			data[k * 300 + j] =
	    			(byte)(255 * Math.sin(j * .5 * Math.PI / 300));
	    			data[k * 300 + j + 1] =
	    			(byte)(256 - j * 256 / 300);
	    			data[k * 300 + j + 2] =
	    			(byte)(255 * Math.cos(k * .5 * Math.PI / 100));
				}
			}
			try{
				logo = Image.getInstance(145, 26, 3, 8, data);
			}catch(BadElementException e){
				e.printStackTrace();
			}
		}
		
		logo.scaleToFit(145f,26f);
		cellLeft = new PdfPCell(logo);
		cellLeft.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellLeft.setVerticalAlignment(Element.ALIGN_TOP);
		cellLeft.setBorderColor(Color.WHITE);
		//cellLeft.setPaddingTop(0);
		tableHead.addCell(cellLeft);
			
		//************** inicia el parrafo para el titulo Moneda
		
		dsMoneda = dsMoneda != "" ? dsMoneda : " ";
		table = new PdfPTable(2);	
		
		font = PDFServices.getFormat("helvetica", 8.5f, Font.BOLD);						
		parrafo = new Paragraph(encabezados[0], font);
		parrafo.setAlignment(Element.ALIGN_RIGHT);			
		cell = new PdfPCell();
		cell.setFixedHeight(10f);		
		cell.addElement(parrafo);					
		cell.setBorderColor(Color.WHITE);										
		table.addCell(cell);
								
		font = PDFServices.getFormat("helvetica", 8.5f, Font.NORMAL);						
		parrafo = new Paragraph(dsMoneda, font);
		parrafo.setAlignment(Element.ALIGN_CENTER);			
		cell = new PdfPCell();		
		cell.addElement(parrafo);		
		cell.setBorderColor(Color.WHITE);			
		table.addCell(cell);
		
		
		cellCenter = new PdfPCell();
	   	cellCenter.setBorderColor(Color.WHITE);	   	
	   	cellCenter.addElement(table);
	   	cellCenter.setHorizontalAlignment(Element.ALIGN_MIDDLE);
		cellCenter.setVerticalAlignment(Element.ALIGN_BOTTOM);
   		    	
		tableHead.addCell(cellCenter);	    	
   		
		//**************************************************
		
		tableOneColumn = new PdfPTable(1);
		/*
	   	font = PDFServices.getFormat("helvetica", 8.5f, Font.BOLD);
	   	parrafo = new Paragraph("", font);
	   	parrafo.setSpacingBefore(0f);
		parrafo.setAlignment(Element.ALIGN_RIGHT);			
		parrafo.setLeading(8);
		cellRight = new PdfPCell();
		cellRight.setBorderColor(Color.WHITE);
		cellRight.addElement(parrafo);   	
		tableOneColumn.addCell(cellRight);
		*/
	   	//******* inicia el parrafo para el titulo ING AUTOS
	   	//tableOneColumn = new PdfPTable(1);
	   	font = PDFServices.getFormat("helvetica", 8.5f, Font.BOLD);
	   	parrafo = new Paragraph(encabezados[1], font);
	   	parrafo.setSpacingBefore(0f);
		parrafo.setAlignment(Element.ALIGN_RIGHT);			
		parrafo.setLeading(9);
		cellRight = new PdfPCell();
		cellRight.setBorderColor(Color.WHITE);
		cellRight.addElement(parrafo);   	
		tableOneColumn.addCell(cellRight);
		
		//******* inicia el parrafo para el titulo CARATULA DE POLIZA		
	   	font = PDFServices.getFormat("helvetica", 8.5f, Font.BOLD);    	
	   	parrafo = new Paragraph(encabezados[2], font);
	   	parrafo.setSpacingBefore(0f);
		parrafo.setAlignment(Element.ALIGN_RIGHT);
		parrafo.setLeading(9);
		cellRight = new PdfPCell();
		cellRight.addElement(parrafo);
		cellRight.setBorderColor(Color.WHITE);
	   	cellRight.setBorder(PdfPCell.NO_BORDER);
	   	cellRight.setPaddingTop(0);
		tableOneColumn.addCell(cellRight);

		//******* inicia el parrafo para el titulo Automoviles/Auto Colectiva
		//String valorAutomoviles = encabezados[3]!= "" ? encabezados[3]: "Automóviles/Auto Colectiva";

		font = PDFServices.getFormat("helvetica", 8.5f, Font.BOLD);	
	   	parrafo = new Paragraph(encabezados[3], font);
	   	parrafo.setSpacingBefore(0f);
		parrafo.setAlignment(Element.ALIGN_RIGHT);
		parrafo.setLeading(9f);
		cellRight = new PdfPCell();
		cellRight.setBorderColor(Color.WHITE);
		cellRight.addElement(parrafo);
	   	cellRight.setBorder(PdfPCell.NO_BORDER);
	   	cellRight.setPaddingTop(0);
		tableOneColumn.addCell(cellRight);
		
		
		//******* inicia el parrafo para el titulo Poliza
		table = new PdfPTable(2);
		float [] widthPoliza = {1f, 1f};
		
		try {
			table.setWidths(widthPoliza);
		} catch (DocumentException e) {
			
			e.printStackTrace();
		}
		
		Color colorLinea = Color.WHITE;
		font = PDFServices.getFormat("helvetica", 8.5f, Font.BOLD);			
		parrafo = new Paragraph(encabezados[4], font);		
		parrafo.setAlignment(Element.ALIGN_RIGHT);
		parrafo.setLeading(9);
		cell = new PdfPCell();	
		cell.setBorderColor(colorLinea);
		cell.addElement(parrafo);
		//cell.setBorder(PdfPCell.NO_BORDER);
		
		table.addCell(cell);
						
		parrafo = new Paragraph(poliza, font);	
		parrafo.setAlignment(Element.ALIGN_RIGHT);
		parrafo.setLeading(9);
		cell = new PdfPCell();
		cell.setBorderColor(colorLinea);
		cell.addElement(parrafo);
		//cell.setBorder(PdfPCell.NO_BORDER);	
				
		table.addCell(cell);
		table.setWidthPercentage(70);
		table.setHorizontalAlignment(Element.ALIGN_RIGHT);

		//..................................................................

		cellRight = new PdfPCell();
		cellRight.setBorderColor(Color.WHITE);
		cellRight.addElement(table);
		tableOneColumn.addCell(cellRight);
		
		
		//*************************************  Inciso
		/*table = new PdfPTable(2);
	   	
		font = PDFServices.getFormat("times", 9.5f, Font.BOLD);			
		parrafo = new Paragraph(encabezados[5], font);		
		parrafo.setAlignment(Element.ALIGN_RIGHT);
		parrafo.setLeading(6);
		cell = new PdfPCell();		
		cell.addElement(parrafo);
		cell.setBorder(PdfPCell.NO_BORDER);
		
		table.addCell(cell);
						
		parrafo = new Paragraph(inciso, font);	
		parrafo.setAlignment(Element.ALIGN_RIGHT);
		parrafo.setLeading(6);
		cell = new PdfPCell();
		cell.addElement(parrafo);
		cell.setBorder(PdfPCell.NO_BORDER);	
				
		table.addCell(cell);
		table.setWidthPercentage(100);
		
		
		cellRight = new PdfPCell();
		cellRight.setBorderColor(Color.WHITE);
		cellRight.addElement(table);
		tableOneColumn.addCell(cellRight);
		*/
		//******************************************
		
		tableOneColumn.setWidthPercentage(100);
		
		cellRight = new PdfPCell();
		cellRight.setBorderColor(Color.WHITE);
		cellRight.addElement(tableOneColumn);
		
	   	tableHead.addCell(cellRight);	    	
	   	tableHead.setWidthPercentage(100);
	   	tableHead.setSpacingAfter(7f);
	    return tableHead;	    
	}
	
	/**
	 * @param pathImageFoot
	 * @param numberReport dependiendo del numero de reporte cambia el pie de pagina
	 * @return
	 */
	public static PdfPTable createFoot(String pathImageFoot, int numberReport){
		
		PdfPTable tableFoot= new PdfPTable(3);
		float [] widths={1f, 2f, 1f};
		
		try {
			tableFoot.setWidths(widths);
		} catch (DocumentException e1) {
			
			e1.printStackTrace();
		}
		
		PdfPTable tableOneColumn = new PdfPTable(1);
		PdfPTable table = null;		
		PdfPCell cellLeft = null;
		PdfPCell cellCenter = null;
		PdfPCell cellRight = null;
		PdfPCell cell = null;
		
		Image logoApoderado = null;
		Paragraph parrafo = null;		
		Font font = null;
		Color colorLinea = Color.WHITE;
		
		//*
		//******************************** inicia el parrafo para el 1er titulo 
    	tableOneColumn = new PdfPTable(1);
    	
    	String [] pieIzq = {"AXA Seguros, S.A. de C.V.", "Periférico Sur 3325, Piso 11,",
    			            "Col. San Jerónimo Aculco", "10400, México, D. F. Tels: 51 69 10","00,", "División: CENTRAL"};
    	int bold = Font.BOLD;
    	
    	for(int i=0; i<pieIzq.length; i++){
    		if(i==0 || i == pieIzq.length-1 ){
        		bold = Font.BOLD;
        	}else{
        		bold = Font.NORMAL;
        	}
    		
	    	font = PDFServices.getFormat("helvetica", 7.5f, bold);
	    	parrafo = new Paragraph(pieIzq[i], font);
	    	parrafo.setSpacingBefore(0f);
			parrafo.setAlignment(Element.ALIGN_LEFT);			
			parrafo.setLeading(8);
			cellLeft = new PdfPCell();
			cellLeft.setBorderColor(colorLinea);
			cellLeft.setPaddingTop(0);		
			cellLeft.addElement(parrafo);
			
			tableOneColumn.addCell(cellLeft);
    	}
		
		tableOneColumn.setWidthPercentage(100);				
		
		cell = new PdfPCell();
		cell.setBorderColor(colorLinea);
		cell.addElement(tableOneColumn);
		
		tableFoot.addCell(cell);
		
		//******************************** inicia el parrafo para el 2o titulo		
		String fecha = "";
		String leyenda = "";
		float size = 8;
		int alineacion = Element.ALIGN_CENTER; 
		int style = Font.NORMAL;
		
		if(1 == numberReport){
		
			//fecha = getCurrentDateMx();
			size = 9f;
			style = Font.NORMAL;
			alineacion = Element.ALIGN_CENTER;
			leyenda = "ESTE DOCUMENTO NO ES VÁLIDO COMO RECIBO DE PAGO";
		}else{
			//fecha = getCurrentDateMx();			
			//size = 9.5f;
			//style = Font.NORMAL;
			//alineacion = Element.ALIGN_CENTER;
			leyenda = "";
		}
		fecha = getCurrentDateMx();
    	tableOneColumn = new PdfPTable(1);
    	//--- linea en blanco
    	font = PDFServices.getFormat("helvetica", size, style);
    	parrafo = new Paragraph("", font);			
		parrafo.setLeading(16);
    	cellCenter = new PdfPCell();
    	cellCenter.setPadding(5);
    	cellCenter.addElement(parrafo);
		cellCenter.setBorderColor(colorLinea);
    	tableOneColumn.addCell(cellCenter);
    	
    	font = PDFServices.getFormat("helvetica", 8.5f, style);
    	parrafo = new Paragraph(fecha, font);
    	parrafo.setSpacingBefore(0f);
		parrafo.setAlignment(alineacion);			
		parrafo.setLeading(10);
		cellCenter = new PdfPCell();
		cellCenter.setBorderColor(colorLinea);
		cellCenter.setPaddingTop(0);		
		cellCenter.addElement(parrafo);
		
		table = new PdfPTable(1);
		table.addCell(cellCenter);
		table. setSpacingBefore(3f);
		table. setSpacingAfter(5f);
		
		cell= new PdfPCell();
		cell.setBorderColor(colorLinea);
		cell.addElement(table);
		
		tableOneColumn.addCell(cell);		
		
    	font = PDFServices.getFormat("times", size, Font.BOLD);  
    	parrafo = new Paragraph(leyenda, font);
    	parrafo.setSpacingBefore(0f);
		parrafo.setAlignment(Element.ALIGN_CENTER);
		parrafo.setLeading(9);
		cellCenter = new PdfPCell();
		cellCenter.setBorderColor(colorLinea);
		cellCenter.setPaddingTop(0);
		cellCenter.addElement(parrafo);

		tableOneColumn.addCell(cellCenter);
		tableOneColumn.setWidthPercentage(100);
		
		cell = new PdfPCell();
		cell.setBorderColor(colorLinea);
		cell.addElement(tableOneColumn);
		
		tableFoot.addCell(cell);		
    	
		//***************************  inicia imagen firma apoderado	
		tableOneColumn = new PdfPTable(1);
		try{
			logoApoderado = Image.getInstance(pathImageFoot);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(logoApoderado== null){
			byte data[] = new byte[100*100*3];
			for (int k = 0; k < 100; ++k) {
				for (int j = 0; j < 300; j += 3) {
	    			data[k * 300 + j] =
	    			(byte)(255 * Math.sin(j * .5 * Math.PI / 300));
	    			data[k * 300 + j + 1] =
	    			(byte)(256 - j * 256 / 300);
	    			data[k * 300 + j + 2] =
	    			(byte)(255 * Math.cos(k * .5 * Math.PI / 100));
				}
			}
			try{
				logoApoderado = Image.getInstance(93, 61, 3, 8, data);
			}catch(BadElementException e){
				e.printStackTrace();
			}
		}
		
		logoApoderado.scaleToFit(93f, 61f);
		cellRight = new PdfPCell(logoApoderado);
		cellRight.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellRight.setVerticalAlignment(Element.ALIGN_TOP);
		cellRight.setBorderColor(colorLinea);
		cellRight.setPaddingTop(0);
		table = new PdfPTable(1);
		table.addCell(cellRight);
		
		cell = new PdfPCell();
		cell.setBorderColor(colorLinea);
		cell.addElement(table);
		tableOneColumn.addCell(cell);
		
		font = PDFServices.getFormat("times", 8, Font.NORMAL);  
    	
    	parrafo = new Paragraph("Apoderado", font);
    	parrafo.setSpacingBefore(0f);
		parrafo.setAlignment(Element.ALIGN_CENTER);
		parrafo.setLeading(8);
		cellRight = new PdfPCell();
		cellRight.setBorderColor(colorLinea);
		cellRight.setPaddingTop(0);
		cellRight.addElement(parrafo);
    	
		tableOneColumn.addCell(cellRight);
		
		cell = new PdfPCell();
		cell.setBorderColor(colorLinea);
		cell.addElement(tableOneColumn);
		
		tableFoot.addCell(cell);			
		tableFoot.setWidthPercentage(95);
    	tableFoot.setSpacingAfter(7f);

	    return tableFoot;	    
	}
	
    
    /**
     * Servicio para obtener una fuente con formato.
     * 
     * @param i
     * @return
     */
    public static Font getFormat(String base, float size, int style) {
    	
    	Font font = null;
    	
    	try{
    	    
	    	if("times".equalsIgnoreCase(base)){
	    		BaseFont timesNewRoman = BaseFont.createFont(BaseFont.TIMES_ROMAN,  BaseFont.CP1252, BaseFont.EMBEDDED);
	    		font = new Font(timesNewRoman, size, style);
	    	}else if("courier".equalsIgnoreCase(base)){
	    		BaseFont courier = BaseFont.createFont(BaseFont.COURIER,  BaseFont.CP1252, BaseFont.EMBEDDED);
	    		font = new Font(courier, size, style);
	    	}else if("helvetica".equalsIgnoreCase(base)){
	    		BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA,  BaseFont.CP1252, BaseFont.EMBEDDED);
	    		font = new Font(helvetica, size, style);
	    	}else if("arial".equalsIgnoreCase(base)){
	    		
	    		BaseFont arial;
				//Font font;
	    		arial = BaseFont.createFont("C:\\ing_pdf\\ING\\arial.ttf",
						BaseFont.CP1252, BaseFont.EMBEDDED);
				font = new Font(arial, size, style);

	    		
	    	}
    		else if("arial_bold".equalsIgnoreCase(base)){
    		
	    		BaseFont arialbold;
				//Font font;
	    		arialbold = BaseFont.createFont("C:\\ing_pdf\\ING\\arialbd.ttf",
						BaseFont.CP1252, BaseFont.EMBEDDED);
				font = new Font(arialbold, size, style);

    		
    		}
	    	
	    	else{
	    		BaseFont timesNewRoman = BaseFont.createFont(BaseFont.TIMES_ROMAN,  BaseFont.CP1252, BaseFont.EMBEDDED);
	    		font = new Font(timesNewRoman, 8, Font.NORMAL);	              	                
	    	}
	    	
    	}catch(DocumentException e){
    		logger.error("DocumentExceptionError ::: ",e);
    	}catch(IOException e){
    		logger.error("IOExceptionError ::: ",e);
    	}

        return font;
                   
    }// Fin método getFormat();
    
   
    
    /**	
	 * Crea una tabla con titulo
	 * 	La tabla general puede contener varias tablas internas (campo-valor), 
	 *  Las tablas internas pueden tener varias filas.
	 * 	El numero de tablas internas se define mediante el numero de columnas (col),
	 * 	Se supone que en la tabla general solamente debe de existir una fila.
	 * 
	 * @param titulo		titulo de la tabla.
	 * @param tags			arreglo que contiene las etiquetas de los campos (todos).
	 * @param data			arreglo que contiene los valores de los campos (todos).
	 * @param aligData		arreglo que contiene la alineación de los datos,
	 * 						esta alineación de datos es por tabla interna. 
	 * @param col			numero de columnas que tendrá la tabla general,
	 * 						numero de tablas internas (campo-valor).
	 * @param indicesData	indices para obtener los arreglos de datos para las tablas
	 * 						internas.
	 * @param widthTable	ancho de las columnas (tablas internas) de la tabla general
	 * @param indWidths		ancho de las celdas campo-valor de las tablas internas.
	 * @param invierteBold		invierte el bold entre el campo y el valor.
	 * @return
	 */
	public static PdfPTable createTableDataCVCV(String titulo, String [] tags, String []data, int [] aligData, 
											int col, int []indicesData, float [] widthTable, 
											float [][] indWidths, String invierteBold){
		
		//----   Validaciones
		if(titulo== null)
			titulo="";
		if(data== null){
			data = new String[tags.length];
			for(int i=0; i<tags.length; i++){
				data[i] = ""; 
			}
		}
		
		float[] widthsTInsuredLeft = null;
				
		PdfPTable tableInsured = new PdfPTable(col);
		PdfPTable table = null;
		PdfPCell cell = null;
		Paragraph parrafo = null;
		Color colorLinea = Color.WHITE;
		
		try {
			tableInsured.setWidths(widthTable);
		} catch (DocumentException e) {			
			e.printStackTrace();
		}
		
		if(!titulo.equalsIgnoreCase("")){
			parrafo = new Paragraph(titulo ,PDFServices.getFormat("helvetica", 10.5f, Font.BOLD));
			parrafo.setLeading(11);
			cell = new PdfPCell();
			cell.addElement(parrafo);
			cell.setPadding(1f);
			cell.setPaddingBottom(1f);
			cell.setColspan(col);
			cell.addElement(table);
			cell.setBorderColor(colorLinea);
			tableInsured.addCell(cell);		
		}
		//Modificación al formato, en vehiculo
		if(titulo.startsWith("Datos del Veh")){

			String [] tagVehiculo = {tags[0]};
			String [] dataVehiculo = {data[0]};
			float [] widthsVehiculo = {1f, 10f};
			
			table = creaTablaCampoValor(tagVehiculo, PDFServices.getFormat("helvetica", 8.7f, Font.BOLD), Element.ALIGN_LEFT, colorLinea, 
					dataVehiculo, PDFServices.getFormat("helvetica", 8.7f, Font.NORMAL) , Element.ALIGN_LEFT, colorLinea, widthsVehiculo, 0f);
			
			table.setSpacingAfter(0f);
			cell = new PdfPCell();			
			cell.setPadding(1f);
			cell.setPaddingBottom(1.5f);
			cell.setColspan(col);
			cell.addElement(table);
			cell.setBorderColor(colorLinea);
			tableInsured.addCell(cell);		
			
		}
		
		
		
		int k=0;
		for(int i=0; i<col ; i++){
			//********  Inicia creacion de tabla 	
			
			int a =  indicesData[k++];//0  3
			int b =  indicesData[k];//3  8
			int bmenosa = b-a;
						
			String [] tagsEnvio = new String[bmenosa];
			String [] dataEnvio = new String[bmenosa];
			
			for(int j=0; j<bmenosa; j++){
				tagsEnvio[j]= tags[a];
				dataEnvio[j]= data[a++];
			}
			
			widthsTInsuredLeft = indWidths[i];
			
			if(invierteBold == null){
			  table = creaTablaCampoValor(tagsEnvio, PDFServices.getFormat("helvetica", 8, Font.BOLD), Element.ALIGN_LEFT, colorLinea, 
				      dataEnvio, PDFServices.getFormat("helvetica", 8, Font.NORMAL) , aligData[i], colorLinea, widthsTInsuredLeft, 1f);			  
			}else{
				table = creaTablaCampoValor(tagsEnvio, PDFServices.getFormat("helvetica", 8, Font.NORMAL), Element.ALIGN_LEFT, colorLinea, 
						dataEnvio, PDFServices.getFormat("helvetica", 8, Font.BOLD) , aligData[i], colorLinea, widthsTInsuredLeft, 1f);				
			}
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell = new PdfPCell();
			cell.setPadding(0f);
			cell.setBorderColor(Color.WHITE);
			cell.addElement(table);
			
			tableInsured.addCell(cell);			
		}

		tableInsured.setWidthPercentage(100);	
		tableInsured.setSpacingAfter(0f);
		
		return tableInsured;
	}
        
    /**
	 * crea una tabla con dos columnas campo-valor, la cantidad de renglones está
	 * dada por el tamanio del arreglo de String campo
	 * 
	 * @param campo			arreglo de String, contiene las etiquetas de la tabla
	 * @param fontCampo		tipo de fuente para la etiqueta
	 * @param alignCampo	alineación de la etiquet 
	 * @param colorBordeCampo	color de borde de las celdas que contienen el campo
	 * @param valor			arreglo de String, es el valor del campo
	 * @param fontValor		tipo de fuente para el valor
	 * @param alignValor	alineación del valor
	 * @param colorBordeValor	color de borde de la celdas que contienen el valor
	 * @param widths		ancho de las columnas de la tabla, su tamanio debe coincidir 
	 * 						con el numero de columnas de la tabla (en este caso 2). 
	 * @param spacingAfter
	 * @return
	 */
    public static PdfPTable creaTablaCampoValor(String [] campo, Font fontCampo , int alignCampo, Color colorBordeCampo, 
								String [] valor, Font fontValor , int alignValor, Color colorBordeValor, 
								float [] widths ,float spacingAfter){
    	PdfPTable campoValor = null;
    	Paragraph parrafo = null;
    	PdfPCell celdaCampo = null;
    	PdfPCell celdaValor = null;
    	
    	campoValor = new PdfPTable(2);
    	
    	try {
			campoValor.setWidths(widths);
		} catch (DocumentException e) {			
			e.printStackTrace();
		}

		for(int i=0; i<campo.length; i++ ){
			//*******************************************
	        parrafo = new Paragraph(campo[i], fontCampo);	        
	        parrafo.setLeading(9);
	        parrafo.setAlignment(alignCampo);
	        
	        celdaCampo = new PdfPCell();  	    
	        celdaCampo.setBorderColor(colorBordeCampo);
	        celdaCampo.addElement(parrafo);                	
	        celdaCampo.setPadding(1.5f);
	
	        campoValor.addCell(celdaCampo);
	        
	        //*******************************************
	        parrafo = new Paragraph(valor[i], fontValor);            
	        parrafo.setAlignment(alignValor);
	        //parrafo.setSpacingBefore(0f);
	        parrafo.setLeading(9);
	        celdaValor = new PdfPCell();
	        celdaValor.setBorderColor(colorBordeValor);
	        celdaValor.addElement(parrafo);	    
	        celdaValor.setPadding(1.8f);
	        
	        if("".equalsIgnoreCase(valor[i])){
	        	 celdaValor.setFixedHeight(12.6f);
	        }

	        campoValor.addCell(celdaValor);
	        
		}            
		
        campoValor.setSpacingAfter(spacingAfter);
        campoValor.setWidthPercentage(100);
    	return campoValor;
    	
    }
    
    /**
     * Genera una tabla con los titulo en la primera fila y la data en los siguientes renglones 
     * 
     * @param encabezado
     * @param encFont
     * @param encAlign
     * @param data
     * @param dataFont
     * @param dataAlign
     * @return
     */
    public static PdfPTable createTableDataColumn( String encabezado, Font encFont, int encAlign, 
			   String [] data, Font dataFont, int dataAlign){

		PdfPTable tableRow = null;
		Paragraph parrafo = null;
		PdfPCell cell = null;
		
		//inicia el encabezado de la tabla
		tableRow =  new PdfPTable(1);
		
		parrafo = new Paragraph(encabezado, encFont);
		parrafo.setLeading(9);
		parrafo.setAlignment(encAlign);
		
		cell = new PdfPCell();
		cell.setBorderColor(Color.WHITE);
		cell.addElement(parrafo);
		cell.setPaddingTop(0);
		tableRow.addCell(cell);
		
		if(data!= null){
			for(int i = 0; i< data.length; i++){
				parrafo = new Paragraph(data[i], dataFont);			
				parrafo.setLeading(8);
				parrafo.setAlignment(dataAlign);
				cell = new PdfPCell();
				cell.setBorderColor(Color.WHITE);
				cell.addElement(parrafo);
				if("".equalsIgnoreCase(data[i])){
					cell.setFixedHeight(10f);
				}
				cell.setPaddingTop(0);
				tableRow.addCell(cell);
			}
		}
		tableRow.setWidthPercentage(100);				
		return tableRow;
    }
    
    
    public static PdfPTable creaTablaFormatoPago(ArrayList data){
    	
    	HashMap mapa = null;
    	PdfPTable tablaFormato = new PdfPTable(4);
    	float [] widths = {4f, 3f, 6f, 4f};

    	PdfPCell cell = null;    	
    	Paragraph parrafo = null;
    	
    	Font helveticaOnceB = getFormat("helvetica", 11, Font.BOLD);
    	Font helveticaNueveB = getFormat("helvetica", 9, Font.BOLD);
    	Font helveticaOchoB = getFormat("helvetica", 8, Font.BOLD);
    	Font helveticaSieteB = getFormat("helvetica", 7, Font.BOLD);
    	Font helveticaSeisB = getFormat("helvetica", 6, Font.BOLD);
    	
    	Font helveticaNueveN = getFormat("helvetica", 9, Font.NORMAL);
    	Font helveticaOchoN = getFormat("helvetica", 8, Font.NORMAL);
    	Font helveticaSieteN = getFormat("helvetica", 7, Font.NORMAL);
    	Font helveticaSeisN = getFormat("helvetica", 6, Font.NORMAL);
    	
    	Font fontTimesNueveB = getFormat("times", 9, Font.BOLD);
    	Font fontTimesOchoB = getFormat("times", 8, Font.BOLD);
    	Font fontTimesSieteB = getFormat("times", 7, Font.BOLD);
    	Font fontTimesSieteN = getFormat("times", 7, Font.NORMAL);
    	Font fontTimesSeisB = getFormat("times", 6, Font.BOLD);
    	Font font = null;
    	
    	String tituloTabla = "Formato de Pago"; 
    	String [] encabezado = {"Banco", "Convenio", "Línea de Captura Bancos \nReferencia", "Concepto"};    	    
    	String [] parametros = {"po_banco", "po_convenio", "po_linea", "po_concepto"};
    	String [] leyenda = {"- Pago en una sola exhibición", "- Si efectúa su pago con cheque, deberá ser para abono en cuenta" +
    	                 " y a favor de \"AXA Seguros, S.A. de C.V.\"."};
    	
    	try {
			tablaFormato.setWidths(widths);
			
			parrafo = new Paragraph(tituloTabla, helveticaOnceB);
			parrafo.setLeading(8f);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			cell = new PdfPCell();
			cell.addElement(parrafo);
			cell.setBorderColor(Color.WHITE);
			cell.setColspan(4);
			
			tablaFormato.addCell(cell);
			
			for(int k=0; k< encabezado.length; k++){	
				parrafo = new Paragraph(encabezado[k], helveticaSeisB);
				parrafo.setLeading(8f);
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell();
				cell.addElement(parrafo);
				cell.setBorderColor(Color.WHITE);
				tablaFormato.addCell(cell);
			}
			
			
			for(int i=0; i< data.size(); i++){
				mapa = (HashMap)data.get(i);
				font = helveticaSieteB;
				
				for(int j=0; j<parametros.length; j++){
					if(j==1)
			    		font = fontTimesSieteN;
			    						
					String valor = (String)mapa.get(parametros[j]);
					parrafo = new Paragraph(valor, font);
					parrafo.setLeading(7f);
			    	parrafo.setAlignment(Element.ALIGN_LEFT);
			    	cell = new PdfPCell();
			    	cell.addElement(parrafo);
			    	cell.setBorderColor(Color.WHITE);
			    	tablaFormato.addCell(cell);	
				}												
			}
			
			//---------------  Leyenda
			parrafo = new Paragraph(leyenda[0], helveticaOchoB);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			cell = new PdfPCell();
			cell.setColspan(4);
			cell.setPaddingTop(7f);
			cell.addElement(parrafo);
			cell.setBorderColor(Color.WHITE);
			tablaFormato.addCell(cell);
			
			parrafo = new Paragraph(leyenda[1], helveticaOchoB);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			cell = new PdfPCell();
			cell.setColspan(4);
			cell.setPaddingTop(3f);
			cell.addElement(parrafo);
			cell.setBorderColor(Color.WHITE);
			tablaFormato.addCell(cell);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}

    	tablaFormato.setWidthPercentage(75);
    	tablaFormato.setSpacingAfter(4f);
    	return tablaFormato;    
    }
    
    public static PdfPTable creaTablaDetalleDocs(String fechaLimite, ArrayList data, String totalAPagar){
    	
    	HashMap mapa = null;
    	PdfPTable tablaDetalleTotal = new PdfPTable(1);
    	tablaDetalleTotal.setWidthPercentage(100f);
    	PdfPCell cellDetalleTotal = null;
    	
    	
    	
    	PdfPTable tablaDetalle = new PdfPTable(4);
    	//tablaDetalle.setWidthPercentage(100f);
    	
    	PdfPTable tablaVaciaBlanca = new PdfPTable(4);
    	float [] widths = {4f, 3f, 6f, 4f};

    	PdfPCell cell = null;    	
    	Paragraph parrafo = null;
    	
    	
    	Font helveticaOnceB = getFormat("helvetica", 11, Font.BOLD);
    	Font helveticaNueveB = getFormat("helvetica", 9, Font.BOLD);
    	Font helveticaOchoB = getFormat("helvetica", 8, Font.BOLD);
    	Font helveticaSieteB = getFormat("helvetica", 7, Font.BOLD);
    	Font helveticaSeisB = getFormat("helvetica", 6, Font.BOLD);
    	
    	
    	Font helveticaOchoN = getFormat("helvetica", 8, Font.NORMAL);
    	Font helveticaSieteN = getFormat("helvetica", 7, Font.NORMAL);
    	Font helveticaSeisN = getFormat("helvetica", 6, Font.NORMAL);
    	
   
    	Font timesSeisB = getFormat("helvetica", 6, Font.BOLD);
    	Font font = null;
    	
    	String tituloTabla = "Detalle de Documentos";
    	String fechaLimPago = "- Fecha límite de pago:";
    	String [] encabezado1 = {"Mismo Banco", "Otros Bancos"};
    	String [] encabezado2 = {"No. de Cheque", "Importe", "No. de Cheque", "Importe"};
    	
    	String [] parametros = {"po_cheques1", "po_importes1", "po_cheques2", "po_importes2"};
    	String [] leyenda = {"- El pago con cheque se recibirá salvo buen cobro.", 
    	                     "- El pago de esta liquidación no lo exime de adeudos anteriores."};
    	
    	String [] cantidadPagar = {"", "", "Cantidad a Pagar:", totalAPagar};
    	
    	if(fechaLimite ==null)
    		fechaLimite = "-";
    	
    	try {
			tablaDetalle.setWidths(widths);
			
			//************************* titulo *********************************
			parrafo = new Paragraph(tituloTabla, helveticaOnceB);
			parrafo.setLeading(8);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			cell = new PdfPCell();
			cell.addElement(parrafo);
			cell.setBorderColor(Color.WHITE);
			cell.setColspan(2);
			tablaDetalle.addCell(cell);
			
			parrafo = new Paragraph(fechaLimPago, helveticaNueveB);
			parrafo.setLeading(8);
			parrafo.setAlignment(Element.ALIGN_RIGHT);
			cell = new PdfPCell();
			cell.addElement(parrafo);
			cell.setBorderColor(Color.WHITE);
			tablaDetalle.addCell(cell);
			
			parrafo = new Paragraph(fechaLimite, helveticaNueveB);
			parrafo.setLeading(8);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			cell = new PdfPCell();
			cell.addElement(parrafo);
			cell.setBorderColor(Color.WHITE);
			tablaDetalle.addCell(cell);
			
			//************************** enc 1 *********************************
			for(int i=0; i<encabezado1.length; i++){
				parrafo = new Paragraph(encabezado1[i], helveticaSieteN);
				parrafo.setLeading(8);
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell();
				cell.setColspan(2);
				cell.addElement(parrafo);
				cell.setBorderColor(Color.BLACK);

				tablaDetalle.addCell(cell);
			}

			//************************** enc 2 *********************************
			
			for(int i=0; i<encabezado2.length; i++){
				parrafo = new Paragraph(encabezado2[i], helveticaSieteN);
				parrafo.setLeading(8);
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell();
				cell.addElement(parrafo);
				cell.setBorderColor(Color.BLACK);
	
				tablaDetalle.addCell(cell);
			}
			
			//************************** data 1 ********************************
			font = helveticaSieteN;
			for(int i=0; i< data.size(); i++){
				mapa = (HashMap)data.get(i);

				for(int j=0; j<parametros.length; j++){
	    						
					String valor = (String)mapa.get(parametros[j]);
					parrafo = new Paragraph(valor, font);
					parrafo.setLeading(8);
			    	parrafo.setAlignment(Element.ALIGN_LEFT);
			    	cell = new PdfPCell();
			    	cell.addElement(parrafo);
			    	cell.setBorderColor(Color.BLACK);
			    	
			    	tablaDetalle.addCell(cell);	
				}												
			}
			//************************** data 2 efectivo, mismo banco
			parrafo = new Paragraph(encabezado1[0], helveticaSieteN);
			parrafo.setLeading(8);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			cell = new PdfPCell();
			
			cell.addElement(parrafo);
			cell.setBorderColor(Color.BLACK);
			
			tablaDetalle.addCell(cell);
			
			for(int i =0; i<3; i++){
				parrafo = new Paragraph("", helveticaSieteN);
				parrafo.setLeading(8);
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell();
				
				cell.addElement(parrafo);
				cell.setBorderColor(Color.BLACK);
				
				tablaDetalle.addCell(cell);
			}			
			
			//-------------- efectivo
			parrafo = new Paragraph("Efectivo", helveticaSieteN);
			parrafo.setLeading(8);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			cell = new PdfPCell();
			//cell.setColspan(2);
			cell.addElement(parrafo);
			cell.setBorderColor(Color.BLACK);
			
			tablaDetalle.addCell(cell);
			
			for(int i =0; i<3; i++){
				parrafo = new Paragraph("", helveticaSieteN);
				parrafo.setLeading(8);
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell();
				
				cell.addElement(parrafo);
				cell.setBorderColor(Color.BLACK);
				
				tablaDetalle.addCell(cell);
			} 
						
			//-------------- Total
			
			double totalMismoBanco= 0.00;
			double totalOtrosBancos= 0.00;
			//String totalMB = String.valueOf(totalMismoBanco);
			//String totalOB = String.valueOf(totalOtrosBancos);
			String totalMB = "";
			String totalOB = "";
			
			parrafo = new Paragraph("Total", helveticaSieteB);
			parrafo.setLeading(8);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			cell = new PdfPCell();
			//cell.setColspan(2);
			cell.addElement(parrafo);
			cell.setBorderColor(Color.BLACK);
			
			tablaDetalle.addCell(cell);
						
			parrafo = new Paragraph(totalMB, helveticaSieteN);
			parrafo.setLeading(8);
			parrafo.setAlignment(Element.ALIGN_RIGHT);
			cell = new PdfPCell();				
			cell.addElement(parrafo);
			cell.setBorderColor(Color.BLACK);
			
			tablaDetalle.addCell(cell);
					
			parrafo = new Paragraph("Total", helveticaSieteB);
			parrafo.setLeading(8);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			cell = new PdfPCell();
			//cell.setColspan(2);
			cell.addElement(parrafo);
			cell.setBorderColor(Color.BLACK);
			
			tablaDetalle.addCell(cell);
						
			parrafo = new Paragraph(totalOB, helveticaSieteN);
			parrafo.setLeading(8);
			parrafo.setAlignment(Element.ALIGN_RIGHT);
			cell = new PdfPCell();				
			cell.addElement(parrafo);
			cell.setBorderColor(Color.BLACK);
			
			tablaDetalle.addCell(cell);
			tablaDetalle.setSpacingAfter(1f);
			
			//******************************************************************
			tablaDetalle.setWidthPercentage(85f);
			tablaDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			cellDetalleTotal = new PdfPCell();
			
			cellDetalleTotal.setBorderColor(Color.WHITE);
			cellDetalleTotal.addElement(tablaDetalle);
			
			tablaDetalleTotal.addCell(cellDetalleTotal);
			
			
			
			
			//************   Fila Blancos   ************************************
			//for(int i =0; i<4; i++){
			/*
				parrafo = new Paragraph("", helveticaSieteN);
				parrafo.setLeading(1);
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell();				
				cell.addElement(parrafo);
				cell.setColspan(4);
				cell.setBorderColor(Color.WHITE);
								
				tablaVaciaBlanca.addCell(cell);
				
				cellDetalleTotal = new PdfPCell();
				cellDetalleTotal.addElement(tablaVaciaBlanca);
				tablaDetalleTotal.addCell(cellDetalleTotal);
				*/
			//}
			//******************************************************************
			tablaDetalle = new PdfPTable(4);
			tablaDetalle.setWidthPercentage(85f);
			
			for(int i =0; i< cantidadPagar.length; i++){
				parrafo = new Paragraph(cantidadPagar[i], helveticaOchoB);
				parrafo.setLeading(8);
				parrafo.setAlignment(Element.ALIGN_RIGHT);
				cell = new PdfPCell();
				cell.addElement(parrafo);
				cell.setPaddingBottom(15f);
				cell.setBorderColor(Color.WHITE);
				
				//cell.setBorderColorTop(Color.BLACK);

				tablaDetalle.addCell(cell);
				
			}
			
			//---------------   leyenda
			
			for(int k=0; k<leyenda.length; k++){
				parrafo = new Paragraph(leyenda[k], getFormat("helvetica", 9f, Font.NORMAL));
				parrafo.setLeading(8);
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell();
				cell.addElement(parrafo);
				cell.setColspan(4);				
				cell.setPaddingTop(0f);
				
				cell.setBorderColor(Color.WHITE);
				tablaDetalle.addCell(cell);
			}
			
			tablaDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellDetalleTotal = new PdfPCell();
			
			cellDetalleTotal.setBorderColor(Color.WHITE);
			cellDetalleTotal.addElement(tablaDetalle);
			
			tablaDetalleTotal.addCell(cellDetalleTotal);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}

    	//tablaDetalle.setWidthPercentage(85);
    	
		
    	
    	return tablaDetalleTotal;    
    }
    
    public static PdfPTable creaTablaNombreFirma(){
    	PdfPTable table = new PdfPTable(2);
    	float [] widths = {3,9};
    	PdfPCell cell = null;
    	Paragraph parrafo = null;
    	Font timesOchoN = getFormat("times", 8f, Font.NORMAL);
    	Font timesNueveN = getFormat("times", 9f, Font.NORMAL);
    	
    	try {
			table.setWidths(widths);
						
			parrafo = new Paragraph("Nombre y Firma del asegurado:", timesNueveN);
			parrafo.setLeading(6);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			cell = new PdfPCell();
			cell.addElement(parrafo);						
			cell.setBorderColor(Color.WHITE);
			
			table.addCell(cell);
			parrafo = new Paragraph("______________________________________________" + 
					                "__________________", timesOchoN);
			parrafo.setLeading(6);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			cell = new PdfPCell();
			cell.addElement(parrafo);						
			cell.setBorderColor(Color.WHITE);
			
			table.addCell(cell);			
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		table.setSpacingBefore(0f);
		
		table.setWidthPercentage(85);
    	return table; 
    }
    
    //*---------------------------------   Inicia   ---------------------------------------------------
    
  
    /**
     * Clase que crea una tabla  con titulos horizontales e información 
     * recibe un ArrayList de HashMap
     * 
     * ejem:    titulo_1   titulo_2   titulo_3   titulo_4
     *            inf_1      inf_2      inf_3      inf_4
     *            
     * @param columnas
     * @param widths		ancho de las columnas de la tabla general.
     * @param encabezado	titulos de la tabla. 
     * @param encFont		fuente de los titulos.
     * @param encAlign		alineación de los titulos.
     * @param lista  		Array de HashMap, cada HashMap contiene los datos a colocar en la tabla.
     * @param dataFont		fuente de los datos.
     * @param dataAlign 	alineación de los datos.
     * @param colorLinea	color de linea las celdas que contienen titulos y datos. 
     * @return
     */
    public static PdfPTable createTableDataArrayList( int columnas, float [] widths, String [] encabezado, Font encFont, 
    		int [] encAlign, ArrayList lista, Font dataFont, int [] dataAlign, Color colorLinea){
    	
    	
		PdfPTable tableRow = new PdfPTable(columnas);
		
		try {
			tableRow.setWidths(widths);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Paragraph parrafo = null;
		PdfPCell cell = null;
		
		HashMap mapa = null;
		
		//inicia el encabezado de la tabla
		//tableRow =  new PdfPTable(1);
		
		for(int i=0; i<columnas; i++){
			parrafo = new Paragraph(encabezado[i], encFont);
			parrafo.setLeading(9);
			parrafo.setAlignment(encAlign[i]);
			
			cell = new PdfPCell();
			cell.setBorderColor(colorLinea);
			cell.addElement(parrafo);
			cell.setPaddingBottom(1f);
			tableRow.addCell(cell);
		}
		
		String [] clave = {"cobertura", "lmaxRespon", "deducible", "prima"};
		String valor = "";
		
		if(lista != null && lista.size()>0){
			
			//saca los mapas del Array
			for(int j=0; j<lista.size(); j++){
				
				mapa = (HashMap)lista.get(j);
				
				//for interno, coloca los valores
				for(int k=0; k<clave.length; k++){
					
					valor = (String)mapa.get(clave[k]);
					if( k == 2 && "PRIMA NETA".equalsIgnoreCase(valor)){
						dataFont = PDFServices.getFormat("helvetica", 8.5f, Font.BOLD);
					}else{
						dataFont = PDFServices.getFormat("helvetica", 7, Font.NORMAL);
					}
					
					parrafo = new Paragraph(valor, dataFont);
					parrafo.setLeading(9);
					parrafo.setAlignment(dataAlign[k]);
					
					cell = new PdfPCell();
					cell.setBorderColor(colorLinea);
					cell.addElement(parrafo);
					cell.setPaddingTop(1f);
					tableRow.addCell(cell);
				}
					
				
			}
		}else{
			
			
			for(int i=0; i<columnas; i++){
				parrafo = new Paragraph("  ", dataFont);
				parrafo.setLeading(11);
				parrafo.setAlignment(dataAlign[i]);
				
				cell = new PdfPCell();
				cell.setFixedHeight(30f);
				cell.setBorderColor(colorLinea);
				cell.addElement(parrafo);
				//cell.setPaddingTop(0);
				tableRow.addCell(cell);
				tableRow.setSpacingAfter(20f);
			}
		}
		
		
		tableRow.setWidthPercentage(100);				
		return tableRow;
    }
    
    //*--------------------------------   Finaliza   --------------------------------------------------
    
    //******************************** metodo dummy para pruebas
    public static ArrayList llenaArrayListDummy(){
    	ArrayList lista = new ArrayList();
        
        String [] bancos ={"Banamex","BBVA Bancomer","Bital","ScotiaBank Inverlat", "Serfin", "Banorte"};
        String [] convenios ={"B:125422","CIE-602671","RAP 4257","2256", "0729", "EM20:09846"};
        String [] lineas ={"01712964501781020075944256 $488.93",
        				   "64501781020075944236 $488.93",
			  			   "64501781020075944236 $488.93",
			  			   "64501781020075944E71 $488.93", 
			  			   "64501781020075944236 $488.93", 
			  			   "06450178102007594426 $488.93"};
    	String [] conceptos ={"","","","", "", ""};
    	
        for(int i=0; i<bancos.length; i++){
        	HashMap map = new HashMap();	  
        	map.put("po_banco", bancos[i]);
        	map.put("po_convenio", convenios[i]);
        	map.put("po_linea", lineas[i]);
  	      	map.put("po_concepto", conceptos[i]);
  	        
  	      	lista.add(map);
        }
   
        return lista;
    }
    
    //--------   metodo dummy para pruebas   --------
    public static ArrayList llenaArrayListDetalleDummy(){
    	ArrayList lista = new ArrayList();
        
        String [] cheques1 ={"No. de Cheque","No. de Cheque","No. de Chequel"};
        String [] importes1 ={"","",""};
        String [] cheques2 ={"","",""};
        String [] importes2 ={"","",""};
    	
        for(int i=0; i<cheques1.length; i++){
        	HashMap map = new HashMap();	  
        	map.put("po_cheques1", cheques1[i]);
        	map.put("po_importes1", importes1[i]);
        	map.put("po_cheques2", cheques2[i]);
  	      	map.put("po_importes2", importes2[i]);
  	        
  	      	lista.add(map);
        }
   
        return lista;
    }
    
    /**
     * Método que regresa el año actual en número
     * 
     * @return int Año Actual
     */
    public static int getCurrentYear() {
        Calendar cal = new GregorianCalendar();
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    /**
     * Método que regresa el mes actual en String
     * 
     * @return int Mes Actual
     */
    public static String getCurrentMonth() {    	
    	String [] meses = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", 
    					   "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE" };
        Calendar cal = new GregorianCalendar();
        int month = cal.get(Calendar.MONTH);
        return meses[month];
    }

    /**
     * Método que regresa el dia actual en número
     * 
     * @return int Dia Actual
     */
    public static int getCurrentDay() {
        Calendar cal = new GregorianCalendar();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day;
    }
    
    /**
     * Método que regresa la fecha actual en formato
     * MÉXICO D.F. A XX DE XXXX DE XXXX
     * 
     * @return int Dia Actual
     */
    public static String getCurrentDateMx() {
        int day = getCurrentDay();
        
        String month = getCurrentMonth();
        int year = getCurrentYear();
        
        String dia = String.valueOf(day);
        if(1 == dia.length()){
        	if("1".equalsIgnoreCase(dia)){
        		dia = "1ERO";
        	}else{
        		dia = "0" + dia;
        	}
        }
        
        return "MÉXICO D.F. A " + dia + " DE " + month + " DE " + year;
    }
    
    //**************************************************************************
    
    public static PdfPTable createTableLeyenda(String opcion){
    	    				
		
		
		//opcion ="B";		
		PdfPTable table = new PdfPTable(1);
		
		PdfPCell cell = null;
		Chunk chunk = null;
		Phrase phrase = null;
		Paragraph parrafo = null;
		Font font = null;
		float tamanioFont = 7.3f;
		float leading = 7f;
		Color colorLinea = Color.WHITE;
		
		if("A".equalsIgnoreCase(opcion)){
			//********** A 1 *****************************
			font = getFormat("helvetica", 7f, Font.NORMAL);		
			phrase = new Phrase("", font); // 1
			
			font = getFormat("helvetica", 7f, Font.BOLD);
			chunk = new Chunk("(A) ", font);//2
			phrase.add(chunk);
			font = getFormat("helvetica", 7f, Font.NORMAL);
			chunk = new Chunk("En pérdidas totales se indemnizará el valor comercial del vehículo"+
							  " en el momento del siniestro.", font);//2
			
			phrase.add(chunk);
						
			parrafo = new Paragraph();
			parrafo.add(phrase);
			parrafo.setLeading(leading);
			cell = new PdfPCell();
			cell.addElement(parrafo);
			cell.setBorderColor(colorLinea);
			table.addCell(cell);
			
			//********************** A 2 *********************************
			
			font = getFormat("helvetica", 7, Font.NORMAL);		
			phrase = new Phrase("", font); // 1	
			font = getFormat("helvetica", 7, Font.BOLD);
			chunk = new Chunk("(B) ", font);//2
			phrase.add(chunk);
			font = getFormat("helvetica", 7, Font.NORMAL);
			chunk = new Chunk("Porcentajes que se aplicarán en pérdidas parciales y totales" + 
					         " sobre el valor comercial del vehículo al momento del siniestro.", font);//2
			phrase.add(chunk);
			
			
			parrafo = new Paragraph();
			parrafo.add(phrase);
			parrafo.setLeading(leading);
			cell = new PdfPCell();
			cell.addElement(parrafo);
			cell.setBorderColor(colorLinea);
			table.addCell(cell);
			
			
			//********************** A 3, se anexa para que sea del mismo tamaño que la 2a opcion, debiso al writer			
			font = getFormat("helvetica", 7, Font.NORMAL);		
			Phrase phraseTres = new Phrase("", font);
			font = getFormat("helvetica", 7, Font.NORMAL);			
						
			parrafo = new Paragraph();
			parrafo.add(phraseTres);
			parrafo.setLeading(leading);
			cell = new PdfPCell();
			cell.addElement(parrafo);
			cell.setBorderColor(colorLinea);
			table.addCell(cell);
			
		}else{
			//********** B 1 *****************************
			font = getFormat("helvetica", 7f, Font.NORMAL);		
			phrase = new Phrase("", font); 
			
			font = getFormat("helvetica", 7f, Font.BOLD);
			chunk = new Chunk("(A) ", font);
			phrase.add(chunk);
			
			font = getFormat("helvetica", 7f, Font.NORMAL);
			chunk = new Chunk("En pérdidas totales se indemnizará invariablemente este valor.", font);//0			
			phrase.add(chunk);
						
			parrafo = new Paragraph();
			parrafo.add(phrase);
			parrafo.setLeading(leading);
			cell = new PdfPCell();
			cell.addElement(parrafo);
			cell.setBorderColor(colorLinea);
			table.addCell(cell);
			
			//********************** B 2 *********************************
			
			font = getFormat("helvetica", 7, Font.NORMAL);		
			phrase = new Phrase("", font); 
			
			font = getFormat("helvetica", 7, Font.BOLD);
			chunk = new Chunk("(B) ", font);
			phrase.add(chunk);
			
			font = getFormat("helvetica", 7, Font.NORMAL);
			chunk = new Chunk("Porcentaje aplicable en pérdidas parciales y totales.", font);//1
			phrase.add(chunk);
			
			parrafo = new Paragraph();
			parrafo.add(phrase);
			parrafo.setLeading(leading);
			cell = new PdfPCell();
			cell.addElement(parrafo);
			cell.setBorderColor(colorLinea);
			table.addCell(cell);
			
			//********************** B 3 *********************************
			
			font = getFormat("helvetica", 7, Font.NORMAL);		
			Phrase phraseTres = new Phrase("", font);
			font = getFormat("helvetica", 7, Font.NORMAL);
			chunk = new Chunk("      03% en daños materiales y el 05% en robo total sobre el valor determinado en (A).", font);//2
			phraseTres.add(chunk);
						
			parrafo = new Paragraph();
			parrafo.add(phraseTres);
			parrafo.setLeading(leading);
			cell = new PdfPCell();
			cell.addElement(parrafo);
			cell.setBorderColor(colorLinea);
			table.addCell(cell);
		}
	
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setWidthPercentage(95);
	
		return table;
		
	}
    
   
    
    
    /**
     * @return
     */
    public static PdfPTable createTableDataLada(){
    	    				
		
		//********** Accidente o Robo de Automóvil *****************************
				
		PdfPTable table = new PdfPTable(1);
		
		PdfPCell cell = null;
		Chunk chunk = null;
		Phrase phrase = null;
		Paragraph parrafo = null;
		Font font = null;
		float tamanioFont = 8.5f;
		float leading = 6f;
		Color colorLinea = Color.WHITE;
		
		font = getFormat("helvetica", tamanioFont, Font.NORMAL);		
		phrase = new Phrase("Accidente o Robo de Automóvil"+"      ", font); // 1			
		font = getFormat("helvetica", tamanioFont, Font.BOLD);
		chunk = new Chunk("01 800 911 1 AXA ", font);//2		
		phrase.add(chunk);
		
		font = getFormat("helvetica", tamanioFont, Font.NORMAL);
		chunk = new Chunk("- (01-800-911-1292)", font);//2
		phrase.add(chunk);
		
		
		parrafo = new Paragraph();
		parrafo.add(phrase);
		parrafo.setLeading(leading);
		cell = new PdfPCell();
		cell.addElement(parrafo);
		cell.setBorderColor(colorLinea);
		table.addCell(cell);
		
		//********************** Defensa Legal *********************************
		
		font = getFormat("helvetica", tamanioFont, Font.NORMAL);		
		phrase = new Phrase("Defensa Legal"+"                                 ", font); // 1	
		font = getFormat("helvetica", tamanioFont, Font.NORMAL);
		chunk = new Chunk("01-800-02-172-22, (01)55-64-80-58, (01)55-65-61-10", font);//2
		
		phrase.add(chunk);
				
		parrafo = new Paragraph();
		parrafo.add(phrase);
		parrafo.setLeading(leading);
		cell = new PdfPCell();
		cell.addElement(parrafo);
		cell.setBorderColor(colorLinea);
		table.addCell(cell);
		
		
		//********************** Servicio de Asistencia *********************************
	
		float telServAsis = 7f;
		
		font = getFormat("helvetica", tamanioFont, Font.NORMAL);		
		phrase = new Phrase("Servicio de Asistencia", font); // 1			
		
		font = getFormat("helvetica", telServAsis, Font.NORMAL);		
		chunk = new Chunk("                          México     ", font); // 1
		phrase.add(chunk);
		font = getFormat("helvetica", telServAsis, Font.BOLD);
		chunk = new Chunk("01 800 908 4641,", font);//2		
		phrase.add(chunk);
		
		font = getFormat("helvetica", telServAsis, Font.NORMAL);
		chunk = new Chunk(" U.S.A.", font);//2		
		phrase.add(chunk);
		
		font = getFormat("helvetica", telServAsis, Font.BOLD);
		chunk = new Chunk(" 1 866 433 3231,", font);//2		
		phrase.add(chunk);
		
		font = getFormat("helvetica", telServAsis, Font.NORMAL);
		chunk = new Chunk(" Canadá y Guatemala (Por Cobrar a", font);//2		
		phrase.add(chunk);
		
		parrafo = new Paragraph();
		parrafo.add(phrase);
		parrafo.setLeading(leading);
		cell = new PdfPCell();
		cell.addElement(parrafo);
		cell.setBorderColor(colorLinea);
		table.addCell(cell);
		
		
		font = getFormat("helvetica", telServAsis, Font.NORMAL);
		phrase = new Phrase("                                      ", font); 		
		
		chunk = new Chunk("                               México )", font);//2		
		phrase.add(chunk);
		
		font = getFormat("helvetica", telServAsis, Font.BOLD);
		chunk = new Chunk(" (52 55) 5169 3026", font);//2		
		phrase.add(chunk);
		
		
		parrafo = new Paragraph();
		parrafo.add(phrase);
		parrafo.setLeading(leading);
		cell = new PdfPCell();
		cell.addElement(parrafo);
		cell.setBorderColor(colorLinea);
		table.addCell(cell);
		
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setWidthPercentage(95);

		return table;
		}
    
    
    public static PdfPTable creaTablaDataPolizaReciboPago( String titulo, String [] campo, String [] valor, 
    													  float [] widthsTable , int alignTag, int alignData, float spacingAfter){
    	
		PdfPTable dataTable = new PdfPTable(8);
	
		
		Paragraph parrafo = null;
	
		Font fontTag = getFormat("helvetica", 8, Font.BOLD);
		Font fontValor = getFormat("helvetica", 8, Font.NORMAL);
		PdfPCell celda = null;
				
		
		int [] aligDataPoliza = {Element.ALIGN_LEFT, Element.ALIGN_CENTER, 
								 Element.ALIGN_CENTER, Element.ALIGN_RIGHT, 
								 Element.ALIGN_LEFT, Element.ALIGN_CENTER,
								 Element.ALIGN_RIGHT};
		
		
		
		try {
			dataTable.setWidths(widthsTable);
		} catch (DocumentException e) {			
		e.printStackTrace();
		}
				
		
		PdfPTable table = null;
		PdfPCell cell = null;
		
		Color colorLinea = Color.WHITE;
		
		if(titulo== null)
			titulo="";
		if(valor == null){
			valor = new String[campo.length];
			for(int i=0; i<campo.length; i++){
				valor[i] = ""; 
			}
		}
		
		if(!titulo.equalsIgnoreCase("")){
			parrafo = new Paragraph(" "+titulo ,PDFServices.getFormat("helvetica", 11, Font.BOLD));
			parrafo.setLeading(8);
			cell = new PdfPCell();
			cell.addElement(parrafo);
			cell.setPadding(0f);
			cell.setColspan(8);
			cell.addElement(table);
			cell.setBorderColor(colorLinea);
			dataTable.addCell(cell);		
		}
		
		//******************** Primer renglón de datos poliza
		
		//---------------------------- 1er columkna
		
		for(int i = 0; i<campo.length; i++){
			
			if( i== campo.length-1 ){
				
				parrafo = new Paragraph(campo[i], fontTag);
				parrafo.setAlignment(Element.ALIGN_CENTER);
				parrafo.setLeading(8);
				celda = new PdfPCell();
				celda.setColspan(3);
				celda.addElement(parrafo);
				celda.setBorderColor(colorLinea);
				dataTable.addCell(celda);
				
				
				parrafo = new Paragraph(valor[i], fontValor);
				parrafo.setAlignment(aligDataPoliza[i]);
				parrafo.setLeading(8);
				celda = new PdfPCell();
				celda.addElement(parrafo);
				celda.setBorderColor(colorLinea);
				dataTable.addCell(celda);
			}else{
				

				
				parrafo = new Paragraph(campo[i], fontTag);
				parrafo.setAlignment(alignTag);
				parrafo.setLeading(8);
				celda = new PdfPCell();
				celda.addElement(parrafo);
				celda.setBorderColor(colorLinea);
				dataTable.addCell(celda);
				
				
				parrafo = new Paragraph(valor[i], fontValor);
				parrafo.setAlignment(aligDataPoliza[i]);
				parrafo.setLeading(8);
				celda = new PdfPCell();
				celda.addElement(parrafo);
				celda.setBorderColor(colorLinea);
				dataTable.addCell(celda);
				
											
				}
		}
		//dataTable.setSpacingAfter(spacingAfter);
		dataTable.setWidthPercentage(100f);

		return dataTable;

}
}// Finde la clase
