package mx.com.aon.pdfgenerator.services;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class PDFServicesEndosos {
	
	private static Logger logger = Logger.getLogger(PDFServices.class);
	
	private static String CAMBIO_SITUACION="110";
	private static String CAMBIO_GARANTIA="111";
	private static String CAMBIO_ACCESORIO="112";
	private static String CAMBIO_POLIZA="113";
	
	private static String ALTA_ASEGURADO_ADICIONAL="114";
	private static String BAJA_ASEGURADO_ADICIONAL="115";
	
	private static String AUMENTO_VALOR_ACCESORIO_ADAPTACION="116";
	private static String DISMINUCION_VALOR_ACCESORIO_ADAPTACION="117";
	
	
	private static String CAMBIO_DOMICILIO="119";
	private static String CAMBIO_PERSONA="120";
	
	/**
	 * @return
	 */
	public static PdfPTable createTableAplicacionDeducibles(){
			
			PdfPTable table = new PdfPTable(1);			
			PdfPCell cell = null;
			Chunk chunk = null;
			Phrase phrase = null;
			Paragraph parrafo = null;			
			float leading = 7f;
			Color colorLinea = Color.WHITE;
			
			Font fontHSieteNormal = PDFServices.getFormat("helvetica", 7f, Font.NORMAL);	
			Font fontHSieteBold = PDFServices.getFormat("helvetica", 7f, Font.BOLD);
			
			//parrafo = new Paragraph("APLICACION DE DEDUCIBLES", fontHSieteBold);
			parrafo = new Paragraph("", fontHSieteBold);
			parrafo.setLeading(leading);
			cell = new PdfPCell();
			cell.addElement(parrafo);
			cell.setBorderColor(colorLinea);
			table.addCell(cell);
			
			
			//Font fontHSieteBold = PDFServices.getFormat("helvetica", 7f, Font.BOLD);
			phrase = new Phrase("\n\n", fontHSieteNormal); 
			chunk = new Chunk("(A) ", fontHSieteBold);//2
			phrase.add(chunk);
			chunk = new Chunk("En pérdidas totales se indemnizará el valor comercial del vehículo"+
							  " en el momento del siniestro.", fontHSieteNormal);//2
			
			phrase.add(chunk);
						
			parrafo = new Paragraph();
			parrafo.add(phrase);
			parrafo.setLeading(leading);
			cell = new PdfPCell();
			cell.addElement(parrafo);
			cell.setBorderColor(colorLinea);
			table.addCell(cell);
			
			
			phrase = new Phrase("", fontHSieteNormal); 
			chunk = new Chunk("(B) ", fontHSieteBold);//2
			phrase.add(chunk);
			chunk = new Chunk("Porcentajes que se aplicarán en pérdidas parciales y totales" + 
			         " sobre el valor comercial del vehículo al momento del siniestro.", fontHSieteNormal);//2
			
			phrase.add(chunk);
						
			parrafo = new Paragraph();
			parrafo.add(phrase);
			parrafo.setLeading(leading);
			cell = new PdfPCell();
			cell.addElement(parrafo);
			cell.setBorderColor(colorLinea);
			table.addCell(cell);
			
			
			/*
				//********** Daños Materiales *****************************			
			phrase = new Phrase("", fontHSieteNormal); // 0
								
				chunk = new Chunk("Daños Materiales.", fontHSieteBold);			
				phrase.add(chunk);										
				chunk = new Chunk(" En pérdidas parciales o totales se aplicará el porcentaje del", fontHSieteNormal);//2
				phrase.add(chunk);											
				chunk = new Chunk(" 5%", fontHSieteBold);
				phrase.add(chunk);								
				chunk = new Chunk(" sobre el", fontHSieteNormal);			
				phrase.add(chunk);								
				chunk = new Chunk(" VALOR COMERCIAL\n", fontHSieteBold);
				phrase.add(chunk);												
				chunk = new Chunk("del vehículo a la fecha del siniestro ", fontHSieteNormal);			
				phrase.add(chunk);
					
				parrafo = new Paragraph();
				parrafo.add(phrase);
				parrafo.setLeading(leading);
				cell = new PdfPCell();
				cell.addElement(parrafo);
				cell.setBorderColor(colorLinea);
				table.addCell(cell);
				
				//********** Robo total *****************************
				phrase = new Phrase("", fontHSieteNormal); // 0
				
				chunk = new Chunk("Robo Total. ", fontHSieteBold); // 1
				phrase.add(chunk);				
				chunk = new Chunk("En pérdidas parciales o totales se aplicará el porcentaje del ", fontHSieteNormal);//2
				phrase.add(chunk);								
				chunk = new Chunk("10% ", fontHSieteBold);//2
				phrase.add(chunk);								
				chunk = new Chunk("sobre el ", fontHSieteNormal);			
				phrase.add(chunk);								
				chunk = new Chunk("VALOR COMERCIAL\n", fontHSieteBold);
				phrase.add(chunk);								
				chunk = new Chunk("del vehículo a la fecha del siniestro ", fontHSieteNormal);//2				
				phrase.add(chunk);
							
				parrafo = new Paragraph();
				parrafo.add(phrase);
				parrafo.setLeading(leading);
				cell = new PdfPCell();
				cell.addElement(parrafo);
				cell.setBorderColor(colorLinea);
				table.addCell(cell);
				
				//********** Límite Máximo de Responsabilidad *****************************
				phrase = new Phrase("", fontHSieteNormal); // 0
				
				chunk = new Chunk("Límite Máximo de Responsabilidad. Valor Comercial. ", fontHSieteBold); // 1
				phrase.add(chunk);					
				chunk = new Chunk("Valor de venta al público con base al promedio de\n" + 
						          "publicaciones especializadas como Guía EBC o Autométrica vigentes al momento del siniestro.", fontHSieteNormal);//2
				phrase.add(chunk);				
				
				parrafo = new Paragraph();
				parrafo.add(phrase);
				parrafo.setLeading(leading);
				cell = new PdfPCell();
				cell.addElement(parrafo);
				cell.setBorderColor(colorLinea);
				table.addCell(cell);
			*/	
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(95);
		
			return table;
			
		}
	
	 /**
	 * @return
	 */
	public static PdfPTable createTableCambiaDataTitular( ){
	    	
		logger.debug(">>> createTableCambiaDataTitular...");
        Font font =  PDFServices.getFormat("helvetica", 8f, Font.NORMAL);
		
        // codigo para simular los cambios de datos del titular
        String titularAnterior = "JOSE LUIS FLORES MURILLO";
		String titularNuevo = "MARIA DE JESUS GUZMAN DIAZ";
		String [] titularAnteriorArray = titularAnterior.split(" ");
		String [] titularNuevoArray = titularNuevo.split(" ");
		
		int sizeNombreTA = titularAnteriorArray.length - 2;
		int sizeNombreTN = titularNuevoArray.length - 2;
		StringBuffer nombreTA = new StringBuffer();
		StringBuffer nombreTN = new StringBuffer();
		
		for(int i=0; i < sizeNombreTA; i++){
			nombreTA.append(titularAnteriorArray[i]+ " ");
		}
				
		for(int i=0; i < sizeNombreTN; i++){
			nombreTN.append(titularNuevoArray[i] + " ");			
		}
		
    	String apPatTA = titularAnteriorArray[sizeNombreTA];    	
    	String apMatTA = titularAnteriorArray[sizeNombreTA + 1];    	    	
    	String apPatTN = titularNuevoArray[sizeNombreTN];    	
    	String apMatTN = titularNuevoArray[sizeNombreTN + 1];
    	
    	List anterior = new ArrayList();
    	anterior.add(0, titularAnterior);
    	anterior.add(1, nombreTA.toString());
    	anterior.add(2, apPatTA);
    	anterior.add(3, apMatTA);
	    	
    	List nuevo = new ArrayList();
    	nuevo.add(0, titularNuevo);
    	nuevo.add(1, nombreTN.toString());
    	nuevo.add(2, apPatTN);
    	nuevo.add(3, apMatTN);
	    
    	//Inicia la creación de la tabla
		PdfPTable tableRow = new PdfPTable(1);								
		Paragraph parrafo = null;
		PdfPCell cell = null;		
		HashMap mapa = null;
		Color colorLinea = Color.WHITE;
		
		//inicia el encabezado de la tabla
		
		String texto1 = "A partir de la vigencia arriba mencionada;";
		parrafo = new Paragraph(texto1, font);
		parrafo.setLeading(9);
		parrafo.setAlignment(Element.ALIGN_LEFT);
		
		cell = new PdfPCell();
		cell.setBorderColor(colorLinea);
		cell.addElement(parrafo);
		cell.setPaddingBottom(5f);
		tableRow.addCell(cell);
		
		 logger.debug(">>> for...");
		for(int i=0; i<4; i++){
			//----------------------   Etiqueta --------------------------------
			parrafo = new Paragraph("Cambia el nombre del titular de:", font);
			parrafo.setLeading(9);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			
			cell = new PdfPCell();
			cell.setBorderColor(colorLinea);
			cell.addElement(parrafo);
			cell.setPaddingBottom(1f);
			tableRow.addCell(cell);
			
			//----------------------   dato   ----------------------------------
			parrafo = new Paragraph((String)anterior.get(i), font);
			parrafo.setLeading(9);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			
			cell = new PdfPCell();
			cell.setBorderColor(colorLinea);
			cell.addElement(parrafo);
			cell.setPaddingBottom(1f);
			tableRow.addCell(cell);
			//----------------------   etiqueta   ------------------------------
			parrafo = new Paragraph(" a: ", font);
			parrafo.setLeading(9);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			
			cell = new PdfPCell();
			cell.setBorderColor(colorLinea);
			cell.addElement(parrafo);
			cell.setPaddingBottom(1f);
			tableRow.addCell(cell);
			//----------------------   dato   ----------------------------------
			parrafo = new Paragraph((String)nuevo.get(i), font);
			parrafo.setLeading(9);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			
			cell = new PdfPCell();
			cell.setBorderColor(colorLinea);
			cell.addElement(parrafo);
			cell.setPaddingBottom(10f);
			tableRow.addCell(cell);
		}
			
		tableRow.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableRow.setWidthPercentage(100);				
		return tableRow;
    }
	
	 public static PdfPTable createTableDataArrayList( int columnas, float [] widths, String [] encabezado, Font encFont, 
	    		int [] encAlign, ArrayList lista, Font dataFont, int [] dataAlign, Color colorLinea){
	    	
		 logger.debug("Entrando a createTableDataArrayList con el valor de la lista de coberturas: "+lista);
		 
			PdfPTable tableRow = new PdfPTable(columnas);
			
			try {
				tableRow.setWidths(widths);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			
			Paragraph parrafo = null;
			PdfPCell cell = null;
			
			HashMap mapa = null;
			
			//inicia el encabezado de la tabla
			//tableRow =  new PdfPTable(1);
			
			for(int i=0; i<encabezado.length; i++){
				parrafo = new Paragraph(encabezado[i], encFont);
				parrafo.setLeading(9);
				parrafo.setAlignment(encAlign[i]);
				
				cell = new PdfPCell();
				cell.setBorderColor(colorLinea);
				cell.addElement(parrafo);
				cell.setPaddingBottom(3f);
				if(i==1){
					cell.setColspan(2);
				}
				tableRow.addCell(cell);
			}
			
			
			String valor = "";
			
			if(lista != null && lista.size()>0){
				
				//saca los mapas del Array
				for(int j=0; j<lista.size(); j++){
					
					mapa = (HashMap)lista.get(j);
					logger.debug("El mapa numero '"+j+"' de la lista de coberturas es: "+mapa);
					//for interno, coloca los valores
					for(int k=0; k<columnas; k++){
						
						valor = (String)mapa.get(Integer.toString(k+1));
						if( k+1 == 4 && "PRIMA NETA".equalsIgnoreCase(valor)){
							dataFont = PDFServices.getFormat("helvetica", 8.5f, Font.BOLD);
							
						}else{
							dataFont = PDFServices.getFormat("helvetica", 8, Font.NORMAL);
						}
						
						parrafo = new Paragraph(valor, dataFont);
						parrafo.setLeading(9);
						parrafo.setAlignment(dataAlign[k]);
						
						cell = new PdfPCell();
						cell.setBorderColor(colorLinea);
						cell.addElement(parrafo);
						cell.setPaddingTop(1f);
						tableRow.addCell(cell);
						logger.debug("para la columna: '"+(k+1)+"' se ha agregado el valor de: "+valor);
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
	    
	 public static PdfPTable createTableEndososB( int columnas, float [] widths, String [] encabezado, Font encFont, 
	    		int [] encAlign, ArrayList lista, Font dataFont, int [] dataAlign, Color colorLinea){
	    	logger.debug("Entrando a createTableEndososB con el valor de la lista de endosos: "+lista);
	    	
			PdfPTable tableRow = new PdfPTable(columnas);
			
			try {
				tableRow.setWidths(widths);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			
			Paragraph parrafo = null;
			PdfPCell cell = null;
			
			HashMap mapa = null;
			
			//inicia el encabezado de la tabla
			//tableRow =  new PdfPTable(1);
			Font font =  PDFServices.getFormat("helvetica", 8.7f, Font.BOLD);
			String texto1 = "A partir de la vigencia arriba mencionada:";
			parrafo = new Paragraph(texto1, font);
			parrafo.setLeading(9);
			parrafo.setAlignment(Element.ALIGN_LEFT);
			
			cell = new PdfPCell();
			cell.setBorderColor(colorLinea);
			cell.addElement(parrafo);
			cell.setColspan(columnas);
			cell.setPaddingBottom(6);
			cell.setPaddingTop(3);
			cell.setPaddingBottom(5f);
			tableRow.addCell(cell);
			
			
			for(int i=0; i<encabezado.length ; i++){
				parrafo = new Paragraph(encabezado[i], encFont);
				parrafo.setLeading(9);
				parrafo.setAlignment(encAlign[i]);
				
				cell = new PdfPCell();
				cell.setBorderColor(colorLinea);
				cell.addElement(parrafo);
				cell.setPaddingBottom(6f);
				if(i==1){
					cell.setColspan(columnas-1);
				}
				tableRow.addCell(cell);
			}
			
			
			
			
			if(lista != null && lista.size()>0){
				
				//saca los mapas del Array
				for(int j=0; j<lista.size(); j++){
					
					mapa = (HashMap)lista.get(j);
					
					//logger.debug("El mapa numero '"+j+"' de la lista de endosos es: "+mapa);
					String nmtipsup;
					dataFont = PDFServices.getFormat("helvetica", 8f, Font.NORMAL);
					String valorCelda=" ";
					int k=0;
					//for interno, coloca los valores
					
						nmtipsup=(String)mapa.get("CDTIPSUP");
//						ALTA_ASEGURADO_ADICIONAL="114";
//						private static String BAJA_ASEGURADO_ADICIONAL="115";
//						private static String CAMBIO_DOMICILIO="119";
//						private static String CAMBIO_PERSONA="120";
						
						if(nmtipsup!=null ){
							nmtipsup=nmtipsup.trim();
							
							if(CAMBIO_SITUACION.equals(nmtipsup) || CAMBIO_GARANTIA.equals(nmtipsup) || CAMBIO_ACCESORIO.equals(nmtipsup)
							
							|| CAMBIO_POLIZA.equals(nmtipsup) || AUMENTO_VALOR_ACCESORIO_ADAPTACION.equals(nmtipsup) || DISMINUCION_VALOR_ACCESORIO_ADAPTACION.equals(nmtipsup)){
								
								
								if(CAMBIO_SITUACION.equals(nmtipsup) || CAMBIO_GARANTIA.equals(nmtipsup) || CAMBIO_ACCESORIO.equals(nmtipsup) || CAMBIO_POLIZA.equals(nmtipsup)){
									
									valorCelda=new String("Modificación de Atributo");
									
								} else if(AUMENTO_VALOR_ACCESORIO_ADAPTACION.equals(nmtipsup)){
									
									valorCelda=new String("Aumento Valor Accesorio/Adaptación");
									
								} else if(DISMINUCION_VALOR_ACCESORIO_ADAPTACION.equals(nmtipsup)){
									
									valorCelda=new String("Disminución Valor Accesorio/Adaptación");
									
								}
								
								
								parrafo = new Paragraph(valorCelda, dataFont);
								parrafo.setLeading(9);
								parrafo.setAlignment(dataAlign[k++]);
								cell = new PdfPCell();
								cell.setBorderColor(colorLinea);
								cell.addElement(parrafo);
								cell.setPaddingTop(1f);
								tableRow.addCell(cell);
								
								
								valorCelda=new String((String)mapa.get("DSATRIBU"));
								if(valorCelda==null)valorCelda=new String(" ");
								parrafo = new Paragraph(valorCelda+":", dataFont);
								parrafo.setLeading(9);
								parrafo.setAlignment(dataAlign[k++]);
								cell = new PdfPCell();
								cell.setBorderColor(colorLinea);
								//cell.setColspan(2);
								cell.addElement(parrafo);
								cell.setPaddingTop(1f);
								tableRow.addCell(cell);
								
								valorCelda=new String((String)mapa.get("OTVALOR"));
								if(valorCelda==null)valorCelda=new String(" ");
								parrafo = new Paragraph(valorCelda, dataFont);
								parrafo.setLeading(9);
								parrafo.setAlignment(dataAlign[k++]);
								cell = new PdfPCell();
								cell.setBorderColor(colorLinea);
								//cell.setColspan(2);
								cell.addElement(parrafo);
								cell.setPaddingTop(1f);
								tableRow.addCell(cell);
								
								
								
								
								
							}
							else if(ALTA_ASEGURADO_ADICIONAL.equals(nmtipsup) || BAJA_ASEGURADO_ADICIONAL.equals(nmtipsup)){
								
								if(ALTA_ASEGURADO_ADICIONAL.equals(nmtipsup)){
									valorCelda=new String("Alta del Asegurado Adicional");
								}else{
									valorCelda=new String("Baja del Asegurado Adicional");
								}
								
								parrafo = new Paragraph(valorCelda, dataFont);
								parrafo.setLeading(9);
								parrafo.setAlignment(dataAlign[k++]);
								cell = new PdfPCell();
								cell.setBorderColor(colorLinea);
								cell.addElement(parrafo);
								cell.setPaddingTop(1f);
								tableRow.addCell(cell);
								
								
								valorCelda=new String((String)mapa.get("DSROL"));
								if(valorCelda==null)valorCelda=new String(" ");
								parrafo = new Paragraph(valorCelda+":", dataFont);
								parrafo.setLeading(9);
								parrafo.setAlignment(dataAlign[k++]);
								cell = new PdfPCell();
								cell.setBorderColor(colorLinea);
								//cell.setColspan(2);
								cell.addElement(parrafo);
								cell.setPaddingTop(1f);
								tableRow.addCell(cell);
								
								valorCelda=new String((String)mapa.get("DSNOMBRE"));
								if(valorCelda==null)valorCelda=new String(" ");
								parrafo = new Paragraph(valorCelda, dataFont);
								parrafo.setLeading(9);
								parrafo.setAlignment(dataAlign[k++]);
								cell = new PdfPCell();
								cell.setBorderColor(colorLinea);
								//cell.setColspan(2);
								cell.addElement(parrafo);
								cell.setPaddingTop(1f);
								tableRow.addCell(cell);
								
							}else if(CAMBIO_PERSONA.equals(nmtipsup)){
								int filas =j;
								for(;filas<j+2;filas++){
									
									mapa = (HashMap)lista.get(filas);
									logger.debug("el valor mapa para la fila "+j+" es: "+mapa);
									valorCelda=new String("Cambio de persona");
									parrafo = new Paragraph(valorCelda, dataFont);
									parrafo.setLeading(9);
									parrafo.setAlignment(dataAlign[k++]);
									cell = new PdfPCell();
									cell.setBorderColor(colorLinea);
									cell.addElement(parrafo);
									cell.setPaddingTop(1f);
									tableRow.addCell(cell);
									
									valorCelda=new String((String)mapa.get("DSROL"));
									if(valorCelda==null)valorCelda=new String(" ");
									parrafo = new Paragraph(valorCelda+":", dataFont);
									parrafo.setLeading(9);
									parrafo.setAlignment(dataAlign[k++]);
									cell = new PdfPCell();
									cell.setBorderColor(colorLinea);
									//cell.setColspan(2);
									cell.addElement(parrafo);
									cell.setPaddingTop(1f);
									tableRow.addCell(cell);
									
									valorCelda=new String((String)mapa.get("DSNOMBRE"));
									if(valorCelda==null)valorCelda=new String(" ");
									parrafo = new Paragraph(valorCelda, dataFont);
									parrafo.setLeading(9);
									parrafo.setAlignment(dataAlign[k++]);
									cell = new PdfPCell();
									cell.setBorderColor(colorLinea);
									//cell.setColspan(2);
									cell.addElement(parrafo);
									cell.setPaddingTop(1f);
									tableRow.addCell(cell);
									
									k=0;
								}
								j++;
							}else if(CAMBIO_DOMICILIO.equals(nmtipsup)){
								
								//TODO Programar este caso, cuando se pueda modificar estos datos desde la aplicacion
							}else {
								
								logger.debug("ADVERTENCIA! NO SE HA CONSIDERADO LA OPCION PARA ESTE NMTIPSUT: '"+nmtipsup+"' Y POR CONSIGUIENTE LA FORMA DE IMPRIMIR SUS DATOS EN EL PDF");
							}
							
						}else {
							logger.debug("Error en la congruencia de los datos  NO HAY UN TIPSUP para este cambio"+j);
						}
						
					//fin del for para cada una de las columnas para la tabla de las coberturas (5 columnas una de la descripcion y 4 para los valores de antes y despues, con sus respectivas descripciones y valores), por el momento solo se ocupan dos para la Descripcion 
						
					
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
}
