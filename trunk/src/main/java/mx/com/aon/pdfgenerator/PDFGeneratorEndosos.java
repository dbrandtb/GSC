package mx.com.aon.pdfgenerator;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import mx.com.aon.pdfgenerator.dao.PDFServicesEndososJDBC;
import mx.com.aon.pdfgenerator.dao.PDFServicesJDBC;
import mx.com.aon.pdfgenerator.services.PDFServices;
import mx.com.aon.pdfgenerator.services.PDFServicesEndosos;
import mx.com.aon.pdfgenerator.vo.AseguradoVO;
import mx.com.aon.pdfgenerator.vo.DataAdicionalVO;
import mx.com.aon.pdfgenerator.vo.DataPolizaVO;
import mx.com.aon.pdfgenerator.vo.DataVehiculoVO;
import mx.com.aon.pdfgenerator.vo.TituloVO;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PDFGeneratorEndosos {

	private static Logger logger = Logger.getLogger(PDFGeneratorEndosos.class);
	
    public PDFGeneratorEndosos() {
    } // Constructor

    // Variables globales para pruebas
    //private static String pathPdf = "C:\\ing_pdf\\endosos\\";
    //private static String pathImage = "C:\\ing_pdf\\ING\\"+ "ing_caratula_poliza.JPG"; 
    private static String pathPdf = "/opt/ice/acw/pdf/endosos/";
    private static final String pathResources = "/opt/ice/acw/images/";
    private static String pathImage = pathResources + "ing_caratula_poliza.JPG";
    
    private static String pathImageApoderado =pathResources+ "apoderado.JPG";
    
    /**
     * @param cdUnieco
     * @param cdRamo
     * @param cdEstado
     * @param nmPoliza
     * @return
     */
    public static String generaCaratulaEndosos(String cdUnieco, String cdRamo, String cdEstado, 
    										   String nmPoliza, String nmSuplem, String nmSituac){
    	
    	//String opcionCaratula = PDFServicesJDBC.obtenOpcionCaratula(cdUnieco, cdRamo, cdEstado, nmPoliza);
    	String opcionCaratula = "";
    	boolean creaPdfEndosos = false; 
    	String respuesta = "";
    	
    	if(!StringUtils.isNotBlank(opcionCaratula)){
    		opcionCaratula = PDFServicesEndososJDBC.obtenTipoEndoso(cdUnieco, cdRamo, cdEstado, nmPoliza, nmSuplem);
    	}
    	//opcionCaratula="D";
    	if(opcionCaratula==null)opcionCaratula="B";
    	creaPdfEndosos = genCaratulaEndososPdf(cdUnieco, cdRamo, cdEstado, nmPoliza, opcionCaratula,nmSuplem,nmSituac);	
    	
    	
    	if(creaPdfEndosos){
    		if(logger.isDebugEnabled()){
    			logger.debug("Caratula Endosos " + opcionCaratula + " creada.");
    		}
    		respuesta = opcionCaratula;
    	}else{
    		if(logger.isDebugEnabled()){
    			logger.debug("Caratula Endosos " + opcionCaratula + " no creada.");
    		}
    		respuesta = null;
    	}

    	return respuesta;
    }
    
    
    /**
     * Clase que genera el reporte PDF del Formato de Caratula de Poliza
     * 
     * @param cdUnieco
     * @param cdRamo
     * @param cdEstado
     * @param nmPoliza
     * @return
     */

	public static boolean genCaratulaEndososPdf(String cdUnieco, String cdRamo, String cdEstado, String nmPoliza, String opcionCaratula, String nmSuplem, String nmSituac) {
    	
    	logger.debug(">>>>>>> Entrando en genCaratulaEndADPdf()...");
    	
    	
    		String nameFile = "reporte_caratula_endoso_"+cdUnieco+cdRamo+nmPoliza +"_"+opcionCaratula+".pdf";
    	
        
        PdfPTable tableGral = new PdfPTable(1);
        tableGral.setWidthPercentage(100);
        PdfPTable tableRow = null;
        PdfPTable tableHead = null;
        PdfPTable tableFoot = null;
        PdfPTable table = null;
        PdfPCell cellGral = null;
        PdfPCell cell = null;
        Paragraph parrafo = null;

		
    	// step 1: creation of a document-object & we create a writer
        float [] size ={ 57f, 45f, 42f, 50f};
    	Document document = PDFServices.createPDF(pathPdf, nameFile, size, null);
    	
    	try {
    		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pathPdf + nameFile));
    		
			// step 3: we open the document
			document.open();
			
			// step 4: we add a paragraph to the document
			
			//   se agrega encabezado
			logger.debug(">>> Obteniendo el encabezado...");
			TituloVO titulo = PDFServicesJDBC.obtenDataTitulo(cdUnieco, cdRamo, cdEstado, nmPoliza, nmSuplem);
			String  [] encabezados= {"AXA AUTOS", "TARJETA DE IDENTIFICACION "+opcionCaratula, 
					                 "Automóviles/Auto Flotilla"};
			
			
			tableHead = PDFServices.createHead( pathImage, titulo.getDsMoneda(), titulo.getNmpoliex(), "caratula", encabezados);
			
			//tableHead.setSpacingBefore(1f);
			tableHead.setSpacingAfter(0f);
			tableHead.setWidthPercentage(100);

			cellGral = new PdfPCell();
			cellGral.setBorderColor(Color.WHITE);
			cellGral.addElement(tableHead);
			tableGral.setHeaderRows(1);
			tableGral.addCell(cellGral);
		
			//**********************   Se agregan los datos del asegurado   ****************************
			logger.debug(">>> Obteniendo datos del asegurado...");		
				
			String [] tags = {"Nombre:", "Y/O:", "Domicilio:", "Benef. Pref:", 
							  "", "", "", "", "",
							  "R.F.C.:", "Teléfono:", "I.D.:", "U.A.:", "R.Tel.:", ""};
			
			AseguradoVO asegurado_vo = PDFServicesJDBC.obtenDataAsegurado(cdUnieco, cdRamo, cdEstado, nmPoliza, nmSuplem);			
			String []data = asegurado_vo.getArrayDataAsegurado_CP();
				
			/*
			 *	indicesData: son los indices para los arreglos de datos  que se envian, 
			 *	dependen de las tablas internas, se agregan los datos del asegurado, 
			 *  desde 0 inclusivo a 5 exclusivo, 3 inclusivo a 8 exclusivo
			 */
			//int [] indicesDataAseg = {0, 5, 10};
			int [] indicesDataAseg = {0, 4, 9, 14};
			
			/*
			 *	width: ancho de las columnas de la tabla general, su numero de elementos
			 *	debe de ser el mismo que el numero de columnas de la tabla principal
			 */
			float [] widthTableAseg= {3f, 2f, 2f};
			
			/*
			 *	width: ancho de las tablas interiores, cada tabla maneja dos columnas (valores),
			 *	el numero de filas debe ser el mismo que el de las columnas de la tabla principal.
			 */
			float [][] indWidthsAseg = {{1.2f, 4f},
										{1f, 1f},
				                        {1f, 2f}};
			
			int [] aligDataAseg = {Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_LEFT};
			tableRow = PDFServices.createTableDataCVCV( "Datos del Asegurado", tags, data,  aligDataAseg,
					   					3, indicesDataAseg, widthTableAseg, indWidthsAseg, null);
						
			tableRow.setSpacingBefore(0f);
			tableRow.setSpacingAfter(0f);
			tableRow.setWidthPercentage(95);
			tableRow.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellGral = new PdfPCell();
			cellGral.setPadding(0f);
			cellGral.setBorderColor(Color.WHITE);
			cellGral.addElement(tableRow);
			tableGral.addCell(cellGral);
			
			
			
			
			if("A".equalsIgnoreCase(opcionCaratula) || "D".equalsIgnoreCase(opcionCaratula)){
				//**********************   Se agregan los datos del vehiculo  A/D ****************************
				
				logger.debug(">>> Obteniendo datos del vehículo...");
		    	/*
		    	 *	indicesData: son los indices para los arreglos de datos  que se envian, 
		    	 *	dependen de las tablas internas
		    	 * 	se agregan los datos del vehículo, desde 0 inclusivo a 3 exclusivo, 3 inclusivo a 8 exclusivo
		    	 */
				//se modifica el primer valor debido a nuevo formato
				int [] indicesData = {1, 6, 11, 15};
				
		    	/*
		    	 *	widthTable: ancho de las columnas de la tabla general, su numero de elementos
		    	 *	debe de ser el mismo que el numeor de columnas de la tabla principal
		    	 */
		    	float [] widthTable= {2.9f, 2.6f, 1.5f};
		    	
		    	/*
				 *	indWidths: ancho de las tablas interiores, cada tabla maneja dos columnas (valores),
		    	 *	el numero de filas debe ser el mismo que el de las columnas de la tabla principal.
		    	 */
		    	float [][] indWidths = {{2.1f, 1.9f},
					                    {2.4f, 3f},
					                    {3f, 2f}};				    	
		    	
		    	String [] tagsV = {"Vehículo:", "Motor:", "Serie:", "Placas:", "Uso:", "Servicio:", "Modelo:", 
		    					  "Capacidad:", "Carga:", "Remolque:", "Tarifa:", "", "", "", "2do. Remolque:", ""};
				
		    	logger.debug("obtenDataVehiculo");
		    	DataVehiculoVO vehiculo = PDFServicesJDBC.obtenDataVehiculo(cdUnieco, cdRamo, cdEstado, nmPoliza, nmSuplem);	    	
		    	String []dataV = vehiculo.getArrayDataVehiculo();	    	
				int [] aligDataV = {Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_LEFT};
				
				tableRow = PDFServices.createTableDataCVCV( "Datos del Vehículo",tagsV, dataV, aligDataV,
						                    3, indicesData, widthTable, indWidths, null);
				tableRow.setWidthPercentage(95);
				tableRow.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellGral = new PdfPCell();
				cellGral.setPadding(0f);
				cellGral.setBorderColor(Color.WHITE);
				//cellGral.setPadding(1f);
				cellGral.addElement(tableRow);
				tableGral.addCell(cellGral);
			}
			
			
			//**********************   Se agregan los datos de  la póliza   ****************************	 
			logger.debug(">>> Obteniendo datos de la póliza...");
			
			int [] indicesDataPoliza = {0, 3, 6, 9};
			
	    	//width tabla general
	    	float [] widthTablePoliza= {3f, 1.5f, 2f};
			//width tablas interiores
	    	float [][] indWidthsPoliza = {{3f, 3f},
				                          {1f, 5f},
				                          {1f, 1f}};
	    		    	
	    	String [] tagsPoliza = {"Vigencia a las 12 hrs. del:", "Forma de pago:", "Fecha de emisión:", "al:", "",
	    				      "", "", "", "Endoso:", ""};
			
	    	DataPolizaVO poliza = PDFServicesJDBC.obtenDataPoliza(cdUnieco, cdRamo, cdEstado, nmPoliza, nmSuplem);
	    	String []dataPoliza = poliza.getArrayDataPoliza();	    	
			int [] aligDataPoliza = {Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_LEFT};
			
			tableRow = PDFServices.createTableDataCVCV( "Datos de la Póliza", tagsPoliza, dataPoliza, aligDataPoliza, 
					                    3, indicesDataPoliza, widthTablePoliza, indWidthsPoliza, null);
			tableRow.setWidthPercentage(95);
			tableRow.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellGral = new PdfPCell();
			cellGral.setPadding(0f);
			cellGral.setBorderColor(Color.WHITE);
			cellGral.addElement(tableRow);
			tableGral.addCell(cellGral);

	    	//--------------   Se agregan los datos adicionales   --------------
			int [] indicesDataAdi = {0, 4, 9};
			
	    	//width tabla general
	    	float [] widthTableAdi= {13.4f, 6.6f};
			//width tablas interiores
	    	float [][] indWidthsAdi = {{2f, 4f},
				                       {3f, 2f}};
				                          
	    	logger.debug(">>> Obteniendo datos adicionales...");
	    	
	    	String [] tagsDataAdi = {"Agente:", "Orden de Trabajo:", "Contrato:",  
	    							 "OT. Agente:", "Prima Neta:", "Tasa de Financiamiento:", 
	    							 "Gastos por Expedición:", "I.V.A.:", "Prima Total:",""};
			
	    	
			DataAdicionalVO dataAdicional = PDFServicesJDBC.obtenDataAdiAgenteEndosos(cdUnieco, cdRamo, cdEstado, nmPoliza, nmSuplem);
			String []dataAdi = dataAdicional.getArrayDataAdicional();			
			int [] aligDataAdic = {Element.ALIGN_LEFT, Element.ALIGN_RIGHT};
			
			tableRow = PDFServices.createTableDataCVCV( "Datos Adicionales", tagsDataAdi, dataAdi, aligDataAdic,  
					                    2, indicesDataAdi, widthTableAdi, indWidthsAdi, null);
	    	
			tableRow.setWidthPercentage(100);
			tableRow.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableRow.setSpacingAfter(7f);
			cellGral = new PdfPCell();
			cellGral.setPadding(0f);
			cellGral.setBorderColor(Color.WHITE);
			cellGral.addElement(tableRow);
			tableGral.addCell(cellGral);
			
			
			if("A".equalsIgnoreCase(opcionCaratula) || "D".equalsIgnoreCase(opcionCaratula)){
		    	//--------------   Se agrega texto   --------------
		    	logger.debug(">>> Obteniendo texto1...");
		    	//StringBuffer  texto1 = new StringBuffer();
		    	
		    	String texto1 = "Por el presente endoso se hace constar que las coberturas del vehículo abajo mencionado quedan AMPARADAS (AMP),\n" +
		    			        "INCREMENTADAS (INC), CANCELADAS (CAN), REDUCIDAS (RED), como a continuación se describe y por la vigencia\n" +
		    			        "señalada, en el entendido que la columna de PRIMAS REFLEJA UNICAMENTE EL IMPORTE PRODUCTO DE LOS CAMBIOS.";
		    			     		    	
		    	parrafo = new Paragraph(texto1, PDFServices.getFormat("helvetica", 8f, Font.NORMAL));
		    	cell = new PdfPCell(parrafo);
		    	cell.setBorderColor(Color.WHITE);
		    	tableRow = new PdfPTable(1);
		    	tableRow.addCell(cell);
		    	tableRow.setSpacingAfter(4f);
		    	tableRow.setWidthPercentage(100);
		    	
		    	cellGral = new PdfPCell();
		    	cellGral.setBorderColor(Color.WHITE);
				cellGral.addElement(tableRow);
				tableGral.addCell(cellGral);
			
				//**********************   Se agrega data Coberturas Amparadas  ***********************************
		    	logger.debug(">>> Obteniendo Coberturas, data en columna...");
		    	
		    	String [] titulos = {"Coberturas Amparadas", "Límite Máximo de Responsabilidad", 
		    							 "Deducible", "Prima"};
		    	
		    	float [] widthsTable= {7f, 2f, 5f, 5f, 2f};
		    	int [] headAlign = { Element.ALIGN_LEFT, Element.ALIGN_RIGHT, Element.ALIGN_CENTER, Element.ALIGN_RIGHT};
		    	int [] dataAlign = { Element.ALIGN_LEFT, Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, Element.ALIGN_CENTER, Element.ALIGN_RIGHT};
		    	
		    	//ArrayList<HashMap<String, String>> dataCoberturasArray = PDFServicesJDBC.obtenDataCoberturas("1", "2", "W", "370");
		    	ArrayList<HashMap<String, String>> dataCoberturasArray = PDFServicesEndososJDBC.obtieneEndososAD(cdUnieco, cdRamo, cdEstado, nmPoliza, nmSuplem, nmSituac);
		    	logger.debug(">>> Obteniendo data ... "+dataCoberturasArray);
		    	
		    	
		    		
		    		table = PDFServicesEndosos.createTableDataArrayList( widthsTable.length, widthsTable, titulos, PDFServices.getFormat("helvetica", 10f, Font.BOLD), headAlign, 
		    		dataCoberturasArray, PDFServices.getFormat("helvetica", 8.5f, Font.NORMAL), dataAlign, Color.WHITE);
		    	
		    		table.setSpacingAfter(5f);
		    		table.setWidthPercentage(96);
		    		table.setHorizontalAlignment(Element.ALIGN_LEFT);
				
					cellGral = new PdfPCell();
					cellGral.setBorderColor(Color.WHITE);
					//cellGral.setPaddingLeft(0f);
					cellGral.addElement(table);
					tableGral.addCell(cellGral);							
		    	
		    	
				//********************************************************************************************
				logger.debug(">>> Obteniendo data APLICACION DE DECUCIBLES... )");
				
				PdfPTable tableLeyenda = null;
				
				tableLeyenda = PDFServicesEndosos.createTableAplicacionDeducibles();
					
				
				tableLeyenda.setSpacingAfter(0f);
				tableLeyenda.setWidthPercentage(100);
				tableLeyenda.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				cellGral = new PdfPCell();
				cellGral.setBorderColor(Color.WHITE);
				//cellGral.setPaddingLeft(0f);
				cellGral.addElement(tableLeyenda);
				tableGral.addCell(cellGral);
			
		    	//********************************************************************************************
				logger.debug(">>> Obteniendo data LADA...");
				
			
				tableRow= PDFServices.createTableDataLada();
				tableRow.addCell("");
				tableRow.setTotalWidth(550f);		
				//tableRow.writeSelectedRows(fila inicio, fila ya no incluida, x pos, y pos, writer.getDirectContent());
				tableRow.writeSelectedRows(0, 4, 60, 140, writer.getDirectContent());
				
    		}else{
    			logger.debug(" Entrando en tipo de Caratula B");
    			
    			ArrayList<HashMap<String,String>> endososB=PDFServicesEndososJDBC.obtenEndososB( cdUnieco, cdRamo, cdEstado, nmPoliza, nmSuplem, nmSituac);
    			
    			logger.debug("Datos obtenidos de PDFServicesEndososJDBC.obtenEndososB: "+endososB);
    			
    			String [] titulos = {"Cambio realizado ", "Descripción"};

    			//float [] widthsTable= {4f, 3f, 3f, 3f, 3f}; //para el caso de que se tengan q poner dos descripciones
    			//float [] widthsTable= {6f, 4f, 4f, 4f, 4f}; 
    			//float [] widthsTable= {4f, 3f, 5f};
    			float [] widthsTable= {5f, 4f, 8f};
				int [] headAlign = { Element.ALIGN_LEFT, Element.ALIGN_CENTER};
				int [] dataAlign = { Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_LEFT};
				
				table = PDFServicesEndosos.createTableEndososB( widthsTable.length, widthsTable, titulos, PDFServices.getFormat("helvetica", 10f, Font.BOLD), headAlign, 
						endososB, PDFServices.getFormat("helvetica", 8f, Font.NORMAL), dataAlign, Color.WHITE);
				
				table.setSpacingAfter(4f);
				table.setWidthPercentage(96);
				table.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				cellGral = new PdfPCell();
				cellGral.setBorderColor(Color.WHITE);
				//cellGral.setPaddingLeft(0f);
				cellGral.addElement(table);
				tableGral.addCell(cellGral);
    			
/*
    			//--------------    --------------
		    	logger.debug(">>> Obteniendo createTableCambiaDataTitular...");
		    	
		    	tableRow = PDFServicesEndosos.createTableCambiaDataTitular();
		    	
		    	cellGral = new PdfPCell();
		    	cellGral.setBorderColor(Color.WHITE);
				cellGral.addElement(tableRow);
				tableGral.addCell(cellGral);
    		
	*/		
    		
    		}
			
			//************************  Footer   *************************************
			logger.debug(">>> Obteniendo pie de pagina...");
			
			
						
			tableFoot = PDFServices.createFoot(pathImageApoderado, 1);
			
			tableFoot.addCell("");
			tableFoot.setTotalWidth(520f);			
			tableFoot.writeSelectedRows(0, 1, 60, 100, writer.getDirectContent());
			
			document.add(tableGral);

    	} catch (FileNotFoundException fne) {
    		logger.debug(">>> error FileNotFoundException...");
			logger.debug(fne.getMessage());
    	} catch (DocumentException de) {
    		logger.debug(">>> error DocumentException...");
			logger.debug(de.getMessage());
		}finally{
			logger.debug(">>> en finally...");
			try{
				document.close();
				}catch(Exception e){
					logger.debug("ERROR No se pudieron obtener todos los datos para el pdf (valores nulos) para Endoso "+e);
					return false;
				}
        }
    	return true;
    	    
    }// end method
        	    
    
   
 

	//-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	public static void main(String [] args){
	
		String resultado = PDFGeneratorEndosos.generaCaratulaEndosos("1", "500", "M", "6998", "1","1");
		if("exito".equalsIgnoreCase(resultado)){
			logger.debug(">>>>>>> Caratula de Poliza Endosos ok...");
		}else{
			logger.debug(">>>>>>> Caratula de Poliza Endosos no ok...");
		}				
	}
	
}// Fin de la clase

