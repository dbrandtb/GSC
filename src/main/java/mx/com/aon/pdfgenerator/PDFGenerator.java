package mx.com.aon.pdfgenerator;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.PageSize;
import org.apache.log4j.Logger;
	
import mx.com.aon.pdfgenerator.dao.PDFServicesJDBC;
import mx.com.aon.pdfgenerator.services.PDFServices;
import mx.com.aon.pdfgenerator.util.Campo;
//import mx.com.aon.pdfgenerator.util.PDFUtil;
//import mx.com.aon.pdfgenerator.util.PdfMontoUtils;
//import mx.com.aon.pdfgenerator.util.UtilCampo;
import mx.com.aon.pdfgenerator.vo.AseguradoVO;
import mx.com.aon.pdfgenerator.vo.DataAdicionalVO;
import mx.com.aon.pdfgenerator.vo.DataPolizaVO;
import mx.com.aon.pdfgenerator.vo.DataVehiculoVO;
import mx.com.aon.pdfgenerator.vo.DatoLiquidacionVO;
import mx.com.aon.pdfgenerator.vo.TituloVO;
import mx.com.ice.kernel.core.PropertyReader;
//import mx.com.  royalsun.kernel.core.PropertyReader;

public class PDFGenerator {
   
	private static Logger logger = Logger.getLogger(PDFGenerator.class);
	
    public static final String MONEDA = "moneda";
    public static final String POLIZA = "poliza";
    public static final String NAME = "name";
    public static final String ING_AUTOS = "ING AUTOS";
    public static final String CARATULA = "CARATULA DE POLIZA";
    public static final String AUT_COLEC = "Automóviles/Auto Colectiva";
    
    // TODO esta variable debe ser eliminada
    
    private static final String pathPdf = PropertyReader.readProperty("pdf.url.emision");
    private static final String pathPdfRecibosEndoso = PropertyReader.readProperty("pdf.url.endoso");
    private static final String pathResources = PropertyReader.readProperty("img.url.resources.pdf");
    	
    	//"/opt/oracle/product/1013/soa/j2ee/acwintegracion/applications/AON_INTEGRACION/AON-INTEGRADO-qa/resources/";
    ///opt/oracle/product/1013/soa/j2ee/acwintegracion/applications/AON_INTEGRACION/AON-INTEGRADO-qa/resources

    //private static final String pathPdf = "C:\\apache-tomcat-5.5.26\\webapps\\AON-INTEGRADO-local\\resources\\";
    	//"/opt/oracle/product/1013/soa/j2ee/acwintegracion/applications/AON_INTEGRACION/AON-INTEGRADO-qa/resources/";
    	//"";
    
    public PDFGenerator() {
    	
    } // Constructor


    /**
     * Clase que genera el reporte PDF del Formato de Caratula de Poliza
     * 
     * @param cdUnieco
     * @param cdRamo
     * @param cdEstado
     * @param nmPoliza
     * @return
     */
    public static int genCaratulaPolizaPdf(String cdUnieco, String cdRamo, String cdEstado, 
    										String nmPoliza, String [] encabezado, String inciso, String nmsuplem) {
    	
    	logger.debug(">>>>>>> Entrando en genCaratulaPolizaTemplate()...");
    	String [] encabezado2=encabezado;
    	
    	String nameFile = "reporte_caratula_poliza"+cdUnieco+cdRamo+nmPoliza + ".pdf"; //"reporte_caratula_poliza.pdf";
    	String pathImage = pathResources + "ing_caratula_poliza.JPG"; //imagen va a cambiar
        
        PdfPTable tableGral = new PdfPTable(1);
        tableGral.setWidthPercentage(100);
        PdfPTable tableRow = null;
        PdfPTable tableHead = null;
        PdfPTable tableFoot = null;
        PdfPTable table = null;
        PdfPCell cellGral = null;
        PdfPCell cell = null;
        Paragraph parrafo = null;

		//Se obtienen los datos para el reporte		
		//String [] values = {"NACIONAL","645017810200"};
		
		
        
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
			TituloVO titulo = PDFServicesJDBC.obtenDataTitulo(cdUnieco, cdRamo, cdEstado, nmPoliza,nmsuplem);
			//String  [] encabezados_1= {"Moneda:", "ING AUTOS", "CARATULA DE POLIZA", "Automóviles/Auto Colectiva","Póliza:"};
			
			encabezado2[2] = titulo.getTituloCaratula() != "" ? titulo.getTituloCaratula() : "Automóviles/Auto Colectiva";
			tableHead = PDFServices.createHead( pathImage, titulo.getDsMoneda(), titulo.getNmpoliex(), "caratula", encabezado2);
			
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
			
			AseguradoVO asegurado_vo = PDFServicesJDBC.obtenDataAsegurado(cdUnieco, cdRamo, cdEstado, nmPoliza,nmsuplem);			
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
			
			
			//**********************   Se agregan los datos del vehiculo   ****************************
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
	    	DataVehiculoVO vehiculo = PDFServicesJDBC.obtenDataVehiculo(cdUnieco, cdRamo, cdEstado, nmPoliza,nmsuplem);	    	
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
			
	    	DataPolizaVO poliza = PDFServicesJDBC.obtenDataPoliza(cdUnieco, cdRamo, cdEstado, nmPoliza,nmsuplem);
	    	String []dataPoliza = poliza.getArrayDataPoliza();
	    	
	    	//Modificación solicitada por C. Aldana
	    	dataPoliza[8]= titulo.getNmpoliex();
	    	
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
			
	    	
			DataAdicionalVO dataAdicional = PDFServicesJDBC.obtenDataAdiAgente(cdUnieco, cdRamo, cdEstado, nmPoliza,nmsuplem);
			String []dataAdi = dataAdicional.getArrayDataAdicional();			
			int [] aligDataAdic = {Element.ALIGN_LEFT, Element.ALIGN_RIGHT};
			
			tableRow = PDFServices.createTableDataCVCV( "Datos Adicionales", tagsDataAdi, dataAdi, aligDataAdic,  
					                    2, indicesDataAdi, widthTableAdi, indWidthsAdi, null);
	    	
			tableRow.setWidthPercentage(100);
			tableRow.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableRow.setSpacingAfter(4f);
			cellGral = new PdfPCell();
			cellGral.setPadding(0f);
			cellGral.setBorderColor(Color.WHITE);
			cellGral.addElement(tableRow);
			tableGral.addCell(cellGral);

	    	//--------------   Se agrega texto   --------------
	    	logger.debug(">>> Obteniendo texto1...");
	    	//StringBuffer  texto1 = new StringBuffer();
	    	
	    	String texto1 = "\"AXA Seguros, S.A. de C.V.\"    Que en lo sucesivo se llamará la compañía," +
	    			      " asegura de conformidad con las cláusulas de esta póliza y durante\n" +
	    			      " la vigencia establecida, el vehículo arriba descrito contra los riesgos" +
	    			      " que enseguida aparecen con límite máximo de responsabilidad.\n" +
	    			      "Se anexan condiciones generales y/o particulares que forman parte integrante" +
	    			      " de la póliza y se entregan.";
	    	
	    	parrafo = new Paragraph(texto1, PDFServices.getFormat("helvetica", 7.7f, Font.NORMAL));
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

	
			//**********************   Se agrega data Coberturas   ***********************************
	    	logger.debug(">>> Obteniendo Coberturas, data en columna...");
	    	
	    	String [] encabezados = {"Coberturas Amparadas", "Límite Máximo de Responsabilidad", 
	    							 "Deducible", "Prima"};
	    	
	    	float [] widthsTable= {7f, 6f, 5f, 2f};
	    	int [] headAlign = { Element.ALIGN_LEFT, Element.ALIGN_RIGHT, Element.ALIGN_CENTER, Element.ALIGN_CENTER};
	    	int [] dataAlign = { Element.ALIGN_LEFT, Element.ALIGN_RIGHT, Element.ALIGN_CENTER, Element.ALIGN_RIGHT};
	    	
	    	//ArrayList<HashMap<String, String>> dataCoberturasArray = PDFServicesJDBC.obtenDataCoberturas("1", "2", "W", "370");
	    	ArrayList<HashMap<String, String>> dataCoberturasArray = PDFServicesJDBC.obtenDataCoberturas(cdUnieco, cdRamo, cdEstado, nmPoliza,nmsuplem);
	    	logger.debug(">>> Obteniendo data ... "+dataCoberturasArray);
	    	
	    	table = PDFServices.createTableDataArrayList( 4, widthsTable, encabezados, PDFServices.getFormat("helvetica", 8.5f, Font.BOLD), headAlign, 
	    			dataCoberturasArray, PDFServices.getFormat("helvetica", 8.5f, Font.NORMAL), dataAlign, Color.WHITE);
	    	
	    	table.setSpacingAfter(4f);
			table.setWidthPercentage(96);
	    	table.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			cellGral = new PdfPCell();
			cellGral.setBorderColor(Color.WHITE);
			//cellGral.setPaddingLeft(0f);
			cellGral.addElement(table);
			tableGral.addCell(cellGral);

			//********************************************************************************************
			logger.debug(">>> Obteniendo data APLICACION DE DECUCIBLES...  (A) (B)");
			
			PdfPTable tableLeyenda = null;
			PdfPTable tableLeyendaDos = null;
			//String [] apliDeducibles = new String [3];
			String [] automoviles = encabezado2[2].split("/");
			automoviles[1] = automoviles[1].trim();
			
			logger.debug("automoviles[0]" + automoviles[0]);
			logger.debug("automoviles[1]" + automoviles[1]);

			
			if(automoviles[1].startsWith("Auto") || automoviles[1].startsWith("Camión") || automoviles[1].startsWith("Moto") ){
				tableLeyenda = PDFServices.createTableLeyenda("A");
				//tableLeyenda.addCell("");
				//tableLeyenda.setTotalWidth(550f);		
				//tableLeyenda.writeSelectedRows(0, 3, 60, 220, writer.getDirectContent());						 
			}else{				
				tableLeyenda = PDFServices.createTableLeyenda("B");
				//tableLeyenda.addCell("");				
				//tableLeyenda.setTotalWidth(550f);	
				//tableLeyenda.writeSelectedRows(0, 4, 60, 220, writer.getDirectContent());
			}
			
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
			
			
			
			//************************  Footer   *************************************
			logger.debug(">>> Obteniendo pie de pagina...");
			
			//String pathImageApoderado = "C:\\apache-tomcat-5.5.26\\webapps\\AON\\resources\\apoderado.JPG";
			String pathImageApoderado = pathResources + "apoderado.JPG";
			//String pathImageApoderado ="C:\\ing_pdf\\apoderado.JPG";
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
				logger.debug("ERROR No se pudieron obtener todos los datos para el pdf (valores nulos) "+e);
				return -1;
			}
        }
    	return 0;
    	    
    }// end method
        	    
    
    /*****************************************************************************************************
     *    REPORTE PDF BENEFICIARIO PREFERENTE OK  -------   Este reporte queda suspendido
     * @return
     ****************************************************************************************************/
    /*
    public static int genBeneficiarioPrefPdf() {
    	
    	logger.debug(">>>>>>> Entrando en genBeneficiarioPrefPDF()...");
    	
    	String pathPdf = "C:\\ing_pdf\\";
    	String nameFile = "reporte_beneficiario_preferente.pdf";
        String pathImage = "C:\\icefiles\\imagenes\\ING\\ing_caratula_poliza.JPG";
        
        PdfPTable tableGral = new PdfPTable(1);
        tableGral.setWidthPercentage(100);        
        PdfPTable tableRow = null;
        PdfPTable tableHead = null;
        PdfPTable tableFoot = null;
        PdfPCell cellGral = null;       
        PdfPCell cell = null;        
        Paragraph parrafo = null;
		
		//Se obtienen los datos para la cabecera del reporte		
		String [] values = {"NACIONAL","645016600200"};
        
    	// step 1: creation of a document-object & we create a writer
		                                                // l,  r,  t, b
		Document document = new Document(PageSize.LETTER, 40, 40, 28,40);
    	
    	try {
    		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pathPdf + nameFile));

			// step 3: we open the document
			document.open();
			
			//   se agrega encabezado
			logger.debug(">>> Obteniendo el encabezado...");
			
			String  [] encabezados_1= {"Moneda:", "", "CARATULA DE POLIZA", "Automóviles/Auto Colectiva","Póliza"};
			
			tableHead = PDFServices.createHead( pathImage, values[0], values[1], "1", "1");
			tableHead.setSpacingBefore(1f);
			tableHead.setSpacingAfter(20f);
			
			cellGral = new PdfPCell();
			cellGral.setBorderColor(Color.WHITE);
			cellGral.addElement(tableHead);
			tableGral.setHeaderRows(1);
			tableGral.addCell(cellGral);//==================================   0

			//-------------   Se agregan los datos del asegurado   -------------
			logger.debug(">>> Obteniendo datos del asegurado...");
						
			String [] tags = {"Nombre:", "", "Domicilio:", "", "Benef. Pref.:", "Cuenta",
					          "", "", "",  "","Empleado", "", "R.F.C.:", "Téléfono", "I.D.:", 
					          "U.A.:", "R. Tel.:", ""};
	
			String []data = {"GUILLERMO RUBIO DE SILVA", "", "ARISTOTELES #105 Col.: RESIDENCIAL" +
					         "CHIPINQUE C.P: 66297 SAN PEDRO GARZA GCIA, SAN PEDRO GARZA GARCIA", 
					         "", "ARRENDADORA BANORTE, S.A. DE C.V. ORGANIZACIÓN AUXILIAR DE CREDITO" +
					         " , GRUPO FINANCIERO BANORTE", "cuenta", "", "","", "", "100077",  
			                 "", "RUSG600603BU7", "-", "-", "-", "-", "-"};
			*/
			/*
	    	 *	indicesData: son los indices para los arreglos de datos  que se envian, 
	    	 *	dependen de las tablas internas
	    	 * 	se agregan los datos del vehículo, desde 0 inclusivo a 3 exclusivo, 3 inclusivo a 8 exclusivo
	    	 */
			//int [] indicesDataAseg = { 0, 6, 12, 17};
			
	    	/*
	    	 *	width: ancho de las columnas de la tabla general, su numero de elementos
	    	 *	debe de ser el mismo que el numeor de columnas de la tabla principal
	    	 */
	    	//float [] widthTableAseg= {8f, 2f, 3f};
	    	
	    	/*
			 *	width: ancho de las tablas interiores, cada tabla maneja dos columnas (valores),
	    	 *	el numero de filas debe ser el mismo que el de las columnas de la tabla principal.
	    	 */
    
	    	//float [][] indWidthsAseg = {{1f, 5f},
			//	                        {2f, 2f},
			//	                        {2f, 3f}};
			/*
	    	int [] aligDataLada = {Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_RIGHT};
			tableRow = PDFServices.createTableDataCVCV( "Datos del Asegurado",tags, data, aligDataLada, 
					                    3, indicesDataAseg, widthTableAseg, indWidthsAseg, null);
						
			tableRow.setSpacingBefore(1f);
			tableRow.setSpacingAfter(1f);
			
			cellGral = new PdfPCell();
			cellGral.setBorderColor(Color.WHITE);			
			cellGral.addElement(tableRow);
			tableGral.addCell(cellGral);//==================================   1
	  	    	
	    	//---------------   Se agregan los datos de póliza   ---------------
			logger.debug(">>> Obteniendo datos de la póliza...");	
			
			int [] indicesDataPoliza = { 0, 3, 6, 9};
	    	//width tabla general
	    	float [] widthTablePoliza= {3f, 2f, 2f};
			//width tablas interiores
	    	float [][] indWidthsPoliza = {{3f, 3f},
				                          {1f, 1f},
				                          {1f, 1f}};
	    		    	
	    	String [] tagsPoliza = {"Vigencia a las 12 hrs. del:", "Forma de pago:", "Fecha de emisión:", "", "",
	    				      "Póliza Ant.:", "al:", "", "Endoso", ""};
			
			String []dataPoliza = {"30/ABR/2008", "Mensual", "07/JUL/2008", "", "",
				      " - ", "30/abr/2009", "", "endoso", ""};
	    	
			int [] aligDataPoliza = {Element.ALIGN_LEFT,Element.ALIGN_LEFT, Element.ALIGN_LEFT};
			tableRow = PDFServices.createTableDataCVCV( "Datos de la Póliza", tagsPoliza, dataPoliza, aligDataPoliza,
					                    3, indicesDataPoliza, widthTablePoliza, indWidthsPoliza, null);
	    	
			tableRow.setSpacingAfter(5f);
			cellGral = new PdfPCell();
			cellGral.setBorderColor(Color.WHITE);
			cellGral.addElement(tableRow);
			tableGral.addCell(cellGral);//==================================   2
						    	
	    	//--------------   Se agregan los datos adicionales   --------------
			logger.debug(">>> Obteniendo datos adicionales...");
	    	
			int [] indicesDataAdi = {0, 5, 11};
			
	    	//width tabla general
	    	float [] widthTableAdi= {3f, 2f};
			//width tablas interiores
	    	float [][] indWidthsAdi = {{2f, 4f},
				                       {3f, 2f}};
				                          
	    	String [] tagsDataAdi = {"Agente:", "Orden de Trabajo:", "Contrato:", "", 
	    							 "OT.Agente:", "Prima Neta:", "Tasa de Financiamiento:", 
	    							 "Gastos por Expedición:", "", "I.V.A.:", "Prima Total",""};
			
			String []dataAdi = {"29831 AON RISK SERVICES, AGENTES DE SEGUROS Y DE FIANZAS, S.A. DE", "", 
								"", "",	"", "0.00", "0.00", "0.00", "", "0.00", "0.00",""};
	    	
			int [] aligDataAdic = {Element.ALIGN_LEFT,Element.ALIGN_RIGHT};
			tableRow = PDFServices.createTableDataCVCV( "Datos Adicionales", tagsDataAdi, dataAdi, aligDataAdic,
					                    2, indicesDataAdi, widthTableAdi, indWidthsAdi, null);
	    	
			tableRow.setSpacingAfter(9f);
			cellGral = new PdfPCell();
			cellGral.setBorderColor(Color.WHITE);
			cellGral.addElement(tableRow);
			tableGral.addCell(cellGral);//==================================   3
			
	    	//--------------   Se agrega texto 1  --------------
	    	logger.debug(">>> Obteniendo texto1...");
	    	String texto1 = "En caso de siniestro parcial o total que amerite indemnización" + 
		                    " del vehículo amparado bajo la póliza y/o inciso arriba citada (o).";
	    	
	    	
	    	parrafo = new Paragraph(texto1, PDFServices.getFormat("times", 8, Font.NORMAL));
	    	cell = new PdfPCell(parrafo);
	    	cell.setBorderColor(Color.WHITE);
	    	cell.setPaddingBottom(30f);
	    	tableRow = new PdfPTable(1);
	    	tableRow.addCell(cell);
	    	
			//--------------   Se agrega texto 2  --------------
	    	logger.debug(">>> Obteniendo texto 2...");
	    	
	    	String beneficiarioPreferente = "ARRENDADORA BANORTE, S.A. DE C.V." + 
	    	                                " ORGANIZACIÓN AUXILIAR DE CREDITO , GRUPO FINANCIERO BANORTE";
	    	
	    	String texto2 = "El beneficiario preferente e irrevocable hasta por el" + 
	                        " interés que le corresponda será:";
	    	
	    	
	    	
	    	parrafo = new Paragraph(texto2, PDFServices.getFormat("times", 8, Font.NORMAL));
	    	cell = new PdfPCell(parrafo);
	    	cell.setBorderColor(Color.WHITE);	
	    	cell.setPaddingBottom(33f);	    	
	    	tableRow.addCell(cell);
	    	
	    	parrafo = new Paragraph(beneficiarioPreferente, PDFServices.getFormat("times", 8, Font.BOLD));
	    	cell = new PdfPCell(parrafo);
	    	cell.setBorderColor(Color.WHITE);
	    	cell.setPaddingBottom(20f);
	    	tableRow.addCell(cell);
			
			//--------------   Se agrega texto 3  --------------
	    	logger.debug(">>> Obteniendo texto 3...");
	    	
			String  texto3_1 = "Dicho pago, de preferencia no será cancelado ni" +
			                   " modificado sin previo aviso de consentimiento del asegurado y la citada\n" +
			                   "institución."; 
			String  texto3_2 = "Asimismo, la póliza arriba mencionada no podrá ser" +
			                   " cancelada por ningún motivo a menos que sea por convenir a los\n" +
			                   " intereses de la compañía y/o por falta de pago.";
			String  texto3_3 = "Anotada en los libros de esta compañía.";
			String  texto3_4 = "El asegurado firma de conformidad la copia del presente endoso.";
					
			parrafo = new Paragraph(texto3_1, PDFServices.getFormat("times", 8, Font.NORMAL));
			cell = new PdfPCell(parrafo);
			cell.setBorderColor(Color.WHITE);	
			cell.setPaddingBottom(25f);			
			tableRow.addCell(cell);
			
			parrafo = new Paragraph(texto3_2, PDFServices.getFormat("times", 8, Font.NORMAL));
			cell = new PdfPCell(parrafo);
			cell.setBorderColor(Color.WHITE);
			cell.setPaddingBottom(10f);
			tableRow.addCell(cell);
			
			parrafo = new Paragraph(texto3_3, PDFServices.getFormat("times", 8, Font.NORMAL));
			cell = new PdfPCell(parrafo);
			cell.setBorderColor(Color.WHITE);
			cell.setPaddingBottom(10f);
			tableRow.addCell(cell);
			
			parrafo = new Paragraph(texto3_4, PDFServices.getFormat("times", 8, Font.NORMAL));
			cell = new PdfPCell(parrafo);
			cell.setBorderColor(Color.WHITE);
			cell.setPaddingBottom(15f);
			tableRow.addCell(cell);
			
			tableRow.setWidthPercentage(100);
			
			tableRow.setSpacingAfter(10f);
			
			cellGral = new PdfPCell();
			cellGral.setBorderColor(Color.WHITE);
			cellGral.addElement(tableRow);
			tableGral.addCell(cellGral);//==================================   4
	    	
	    	//document.add(tableRow);
			
	    	//**  Footer
			logger.debug(">>> Obteniendo pie de pagina...");
			String pathImageApoderado = "C:\\icefiles\\imagenes\\ING\\apoderado.JPG";
			tableFoot = PDFServices.createFoot(pathImageApoderado, 1);

			tableFoot.addCell("");
			tableFoot.setTotalWidth(555f);			
			tableFoot.writeSelectedRows(0, 1, 40, 70, writer.getDirectContent());
			
			document.add(tableGral);
 
    	} catch (FileNotFoundException fne) {
			System.err.println(fne.getMessage());
    	} catch (DocumentException de) {
			System.err.println(de.getMessage());
		}finally{
			document.close();
        }
    	return 0;
    	    
    }// end method genBeneficiarioPrefPdf
    */
    
    /*****************************************************************************************************  
     *  REPORTE PDF RECIBO DE PAGO
     * @return
     ****************************************************************************************************/
    public static int generaReciboPagoPdf(String proceso , String cdUnieco, String cdRamo, String cdEstado, 
														String nmPoliza,String nmsuplem) {
    	
    	logger.debug("--------------------------------------------");
    	logger.debug(">>>>>>> Entrando en generaReciboPagoPdf()...");
    	logger.debug("--------------------------------------------");
    	//String pathPdf = "C:\\ing_pdf\\";
    	String nameFile = "reporte_recibo_pago"+proceso+cdUnieco+cdRamo+nmPoliza + ".pdf";
        
    	//String pathPdf = "C:\\apache-tomcat-5.5.26\\webapps\\AON\\resources\\";///"C:\\ing_pdf\\";
    	//String pathPdf = "/opt/oracle/product/1013/soa/j2ee/acw/applications/AON_MEXICO/AON_MEXICO/resources/";///"C:\\ing_pdf\\";
        //String pathImage = "C:\\ing_pdf\\ING\\ing_caratula_poliza.JPG"; //imagen va a cambiar
    	String pathImage = pathResources + "ing_caratula_poliza.JPG"; //imagen va a cambiar

        
        
        PdfPTable tableGral = null;
        //tableGral.setWidthPercentage(100);        
        PdfPTable tableRow = null;
        PdfPTable tableHead = null;
        PdfPTable tableFoot = null;
        PdfPTable table = null;        
        PdfPCell cellGral = null;       
        PdfPCell cell = null;        
        Paragraph parrafo = null;
		        
        //***********************   PAGO REFERENCIADO **********************
		String metodoPago = PDFServicesJDBC.obtenMetodoPago(cdUnieco, cdRamo, cdEstado, nmPoliza,nmsuplem); 
		logger.debug(">>>>>>> metodo pago "+metodoPago);
		
        //si no tiene pago referenciado, no se crea la caratula		
		if("0".equalsIgnoreCase(metodoPago)){
			logger.debug(">>>>>>> Recibo de Pago sin pagoreferenciado...");
			return -1;
		}
		
		Campo [] numRecibosAsociados = PDFServicesJDBC.obtenCantidadRecibos(cdUnieco, cdRamo, cdEstado, nmPoliza); 
		
    	// step 1: creation of a document-object & we create a writer
        float [] size ={ 33f, 33f, 8f, 20f};
    	Document document = PDFServices.createPDF("_endoso".equals(proceso)?pathPdfRecibosEndoso:pathPdf, nameFile, size, null);
    	
    	try {
    		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(("_endoso".equals(proceso)?pathPdfRecibosEndoso:pathPdf) + nameFile));
    	
			// step 3: we open the document
			document.open();
			
			// step 4: we add a paragraph to the document
			
			//INICIA LA GENERACION  DE CARATULA DE RECIBO DE PAGO POR CADA PAGO REF. 
			
			
			logger.debug(">>>>>>> numRecibosAsociados ::: "+numRecibosAsociados.length);
			for(int i=0; i<numRecibosAsociados.length; i++){
				
				logger.debug(">>>>>>> PARA EL RECIBO: "+ numRecibosAsociados[i].getNombre() + " con el numero de recibo: "+ numRecibosAsociados[i].getValor());
				
				document.newPage();
				
				tableGral = new PdfPTable(1);
		        tableGral.setWidthPercentage(100);    
		        //   se agrega encabezado
				//logger.debug(">>> Obteniendo el encabezado...");
		        TituloVO titulo = PDFServicesJDBC.obtenDataTitulo(cdUnieco, cdRamo, cdEstado, nmPoliza,nmsuplem);
		        String moneda=titulo.getCdMoneda().trim();
				//logger.debug("moneda="+moneda);
		        //String  [] encabezados_1= {"Moneda:", "", "LIQUIDACION DE PRIMAS", "Automóviles/Auto Colectiva",""};
			
				tableHead = PDFServices.createHead( pathImage, titulo.getDsMoneda(), titulo.getNmpoliex(), "recibo_pago", null);
				tableHead.setSpacingBefore(1f);
				tableHead.setSpacingAfter(15f);
				tableHead.setWidthPercentage(100);
				
				cellGral = new PdfPCell();
				cellGral.setBorderColor(Color.WHITE);
				cellGral.addElement(tableHead);
				tableGral.setHeaderRows(1);
				tableGral.addCell(cellGral);

				//-------------   Se agregan los datos del asegurado   -------------
				//logger.debug(">>> Obteniendo datos del asegurado...");
			
			

				String [] tags = {"Nombre:", "Y/O", "Domicilio:", "", "R.F.C.:", "Teléfono:", ""};
				/*
				String []data = {"EDGAR ROMEROLL GARCIA", "DORA ELISA VILLARREAL RODRIGUEZ", 
						         "VALLE DEL VERGEL 323 Col. VALLE DEL CONTRY C.P.: 67174 GUADALUPE, NUEVO LEON", 
						         "", "rfc", "81-83173426", ""};
				*/
				/*
		    	 *	indicesData: son los indices para dividir el arreglo de datos  que se envia, 
		    	 *	dependen de las tablas internas
		    	 * 	se agregan los datos del vehículo, desde 0 inclusivo a 3 exclusivo, 3 inclusivo a 8 exclusivo
		    	 */
				int [] indicesDataAseg = { 0, 3, 6};
			
		    	/*
		    	 *	width: ancho de las columnas de la tabla general, su numero de elementos
		    	 *	debe de ser el mismo que el numeor de columnas de la tabla principal
		    	 */
		    	float [] widthTableAseg= {3f, 1f};
		    	
		    	/*
				 *	width: ancho de las tablas interiores, cada tabla maneja dos columnas (valores),
		    	 *	el numero de filas debe ser el mismo que el de las columnas de la tabla principal.
		    	 */
		    	float [][] indWidthsAseg = {{1f, 6f},
					                        {1f, 2f}};
		    	
		    	AseguradoVO asegurado_vo = PDFServicesJDBC.obtenDataAsegurado(cdUnieco, cdRamo, cdEstado, nmPoliza,nmsuplem);			
				String []data = asegurado_vo.getArrayDataAsegurado_Pago();
				int [] aligDataAseg = {Element.ALIGN_LEFT, Element.ALIGN_RIGHT};
				
				tableRow = PDFServices.createTableDataCVCV( "Datos del Asegurado", tags, data, aligDataAseg, 2, indicesDataAseg, 
						                    widthTableAseg, indWidthsAseg, null);
							
				tableRow.setSpacingBefore(1f);
				tableRow.setSpacingAfter(1f);
				tableRow.setWidthPercentage(100);
				cellGral = new PdfPCell();
				cellGral.setBorderColor(Color.WHITE);
				cellGral.addElement(tableRow);
				tableGral.addCell(cellGral);

				//---------------   Se agregan los datos de póliza   ---------------	    		    	
				
				float [] widthsTable = { 2.2f, 3.3f, 2f, 2f, 3.5f, 2f, 1f, 2f};
				
				
				//width tablas interiores
		    	/*
				float [][] indWidthsPoliza = {{2f, 2f},
					                          {2f, 2f},
					                          {4f, 3f},
					                          {1f, 3f}};
		    	*/
				
			    	//logger.debug(">>> Obteniendo datos de la póliza...");
		    	
		    	String [] tagsPoliza = {"Número:       ", "Póliza Ant.:       ", "Vigencia a las 12 hrs. del:   ", "   al:   ", 
						"Forma de Pago:       ", "Endoso:       ", "Fecha de Expedición de la Liquidación:   "};
				
				
				cellGral = new PdfPCell();
				cellGral.setBorderColor(Color.WHITE);
				
		
				DataPolizaVO poliza = PDFServicesJDBC.obtenDataPoliza(cdUnieco, cdRamo, cdEstado, nmPoliza,nmsuplem);
			
				String []dataPoliza = {titulo.getNmpoliex(), poliza.getPolizaAnt(), poliza.getVigencia_del(), 
								   poliza.getVigencia_al(), poliza.getFormaPago(), poliza.getEndoso(),
								   poliza.getFechaEmision()};	 
			
			
			
				tableRow = PDFServices.creaTablaDataPolizaReciboPago( "Datos de la  Póliza",tagsPoliza, dataPoliza, 
						widthsTable , Element.ALIGN_LEFT, Element.ALIGN_CENTER, 5f);
				
				tableRow.setSpacingAfter(5f);
				cellGral.addElement(tableRow);
				tableGral.addCell(cellGral);
				
				//--------------   Se agregan los datos del Agente   --------------
				//logger.debug(">>> Obteniendo datos del agente...");

				int [] indicesDataAgente = {0, 1, 2};
		  
		    						          
		    	//width tabla general
		    	float [] widthTableAgente= {4f, 2f};
				//width tablas interiores
		    	float [][] indWidthsAgente = {{1.5f, 10f},
		    								  {6f, 2f}};
	
		    	String [] tagsDataAgente = {"Agente:", "Centro de Contribución:", ""};
				
		    	DataAdicionalVO dataAdicional = PDFServicesJDBC.obtenDataAdiAgente(cdUnieco, cdRamo, cdEstado, nmPoliza,nmsuplem);
		    	dataAdicional.getAgente();
				//String []dataAgente = {"29831 AON RISK SERVICES, AGENTES DE SEGUROS Y DE FIANZAS , S", "072151",""};
		    	String []dataAgente = {dataAdicional.getAgente(), "072151",""};
		    	
				int [] aligDataAgente = {Element.ALIGN_LEFT, Element.ALIGN_LEFT};
				tableRow = PDFServices.createTableDataCVCV( "Datos del Agente", tagsDataAgente, dataAgente, aligDataAgente, 
						                    2, indicesDataAgente, widthTableAgente, indWidthsAgente, null);
		    	
				tableRow.setSpacingAfter(5f);
				tableRow.setWidthPercentage(95);
				tableRow.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellGral = new PdfPCell();
				cellGral.setBorderColor(Color.WHITE);
				cellGral.addElement(tableRow);
				tableGral.addCell(cellGral);
	
		    	//--------------   Se agrega datos de liquidación...  --------------
				logger.debug(">>> Obteniendo datos de liquidación...");
				
				String [] tagsDataLiquid = {"Folio Fiscal:", "Folio:", "Período Cubierto:", 
						                    "Serie de la Liquidación:", "","", "Prima Neta:",
						                    "Tasa de Financiamiento :", "Gastos por Expedición :", 
						                    "", "I.V.A :", "Total a Pagar :", ""};
				
				//DataAdicionalVO dataAdicionalVO = PDFServicesJDBC.conceptoRecibo(cdUnieco, cdRamo, cdEstado, nmPoliza);
			
				//String []dataLiquid = {"-", "6450178102006450178102000112:", "Desde 30 de Abril de 2008 Hasta 30 de Mayo de 2008.",
				//	               "01/12", "","", "325.16", "0.00", "100.00", "", "63.77", "488.93", ""};

				DatoLiquidacionVO datoLiquidacionVO = PDFServicesJDBC.datoLiquidacion(cdUnieco, cdRamo, cdEstado, 
														nmPoliza, numRecibosAsociados[i].getNombre(),numRecibosAsociados[i].getValor(),
														moneda);
				
				datoLiquidacionVO.setSerieLiquidacion(numRecibosAsociados[i].getValor());
				
				logger.debug("despues de obtener liquidacion");
				String []dataLiquid = {"", datoLiquidacionVO.getFolio(), datoLiquidacionVO.getPeriodo(),
							datoLiquidacionVO.getSerieLiquidacion(), "","", datoLiquidacionVO.getPrimaNeta(), 
							datoLiquidacionVO.getTasaFinanciamiento(), datoLiquidacionVO.getGastosExpedicion(),
								               "", datoLiquidacionVO.getIva(), datoLiquidacionVO.getTotalPagar(), ""};
	
				int [] indicesDataLiquid = { 0, 6, 12};
				
				//width tabla general
				float [] widthTableLiquid= {4f, 2f};
				//width tablas interiores
				float [][] indWidthsLiquid = {{2f, 6f},
				                              {2.2f, 1.8f}};
			                    
	
				int [] aligDataLiquid = {Element.ALIGN_LEFT, Element.ALIGN_RIGHT};
				
				logger.debug("voy a crear createTableDataCVCV");
				tableRow = PDFServices.createTableDataCVCV( "Datos de Liquidación", tagsDataLiquid, dataLiquid, aligDataLiquid, 
				                  2, indicesDataLiquid, widthTableLiquid, indWidthsLiquid, null);
				
				cellGral = new PdfPCell();
				cellGral.setBorderColor(Color.WHITE);
					//cellGral.setPaddingBottom(10f);	
				cellGral.addElement(tableRow);
				tableGral.addCell(cellGral);
				
				//******************** Se crea la tabla para el monto escrito, calculado en PDFServicesJDBC.datoLiquidacion
				table = new PdfPTable(1);
				table.setWidthPercentage(100);
					
					logger.debug("total de  de monto escrito = "+datoLiquidacionVO.getPrimaTotalNum());
					parrafo = new Paragraph(datoLiquidacionVO.getPrimaTotalNum(), PDFServices.getFormat("helvetica", 8, Font.BOLD));
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell(parrafo);
				cell.setBorderColor(Color.WHITE);			
				table.addCell(cell);
				
				cellGral = new PdfPCell();
				cellGral.setBorderColor(Color.WHITE);
				cellGral.setPaddingBottom(5f);	
				cellGral.addElement(table);
				tableGral.addCell(cellGral);
			
				//******************** lugar de expedicion ****************************************************
				table = new PdfPTable(2);
				table.setWidthPercentage(100);
				float [] widths = {1f, 5f};
			
				table.setWidths(widths);
				
				
				String lugarExp = "México D.F.";
				
				parrafo = new Paragraph("Lugar de Expedición:", PDFServices.getFormat("helvetica", 8, Font.BOLD));
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell(parrafo);
				cell.setBorderColor(Color.WHITE);			
				table.addCell(cell);
				
				parrafo = new Paragraph( lugarExp, PDFServices.getFormat("helvetica", 8, Font.NORMAL));
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell(parrafo);
				cell.setBorderColor(Color.WHITE);			
				table.addCell(cell);
				table.setSpacingAfter(4f);
				//table.setWidthPercentage(40f);
				table.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				cellGral = new PdfPCell();
				cellGral.setBorderColor(Color.WHITE);
				//cellGral.setPaddingBottom(10f);	
				cellGral.addElement(table);
				
				tableGral.addCell(cellGral);
			
				//******************** leyenda ****************************************************
				table = new PdfPTable(2);
				float [] ancho = {12f, 2f};
				table.setWidths(ancho);
				
				
				String  leyenda = "Para que esta liquidación haga prueba de su pago," + 
				                  " deberá estar firmado y sellado por el banco."; 
	
				parrafo = new Paragraph(leyenda, PDFServices.getFormat("helvetica", 8, Font.BOLD));
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell(parrafo);
				cell.setBorderColor(Color.WHITE);			
				table.addCell(cell);
			
				parrafo = new Paragraph("________________", PDFServices.getFormat("helvetica", 8, Font.BOLD));
				parrafo.setAlignment(Element.ALIGN_RIGHT);
				cell = new PdfPCell();
				cell.addElement(parrafo);
				cell.setBorderColor(Color.WHITE);			
				table.addCell(cell);
				
				parrafo = new Paragraph("", PDFServices.getFormat("helvetica", 8, Font.BOLD));
				parrafo.setAlignment(Element.ALIGN_LEFT);
				cell = new PdfPCell(parrafo);
				cell.setBorderColor(Color.WHITE);			
				table.addCell(cell);
				                        
				parrafo = new Paragraph("Sello del Banco", PDFServices.getFormat("helvetica", 6, Font.BOLD));
				parrafo.setAlignment(Element.ALIGN_CENTER);
				cell = new PdfPCell();
				cell.addElement(parrafo);
				cell.setBorderColor(Color.WHITE);			
				table.addCell(cell);
				table.setSpacingAfter(0f);
				table.setWidthPercentage(100);
						
				cellGral = new PdfPCell();
				cellGral.setBorderColor(Color.WHITE);
				cellGral.addElement(table);
				tableGral.addCell(cellGral);
				
				//********************   FORMATO DE PAGO   *****************************
				logger.debug(">>> Obteniendo datos de FORMATO DE PAGO...");
				
					//ArrayList formatoPago = PDFServices.llenaArrayListDummy();
					ArrayList formatoPago = PDFServicesJDBC.obtenArrayFormatoPago(cdUnieco, cdRamo, cdEstado, 
																			nmPoliza, numRecibosAsociados[i].getNombre());
				tableRow = PDFServices.creaTablaFormatoPago( formatoPago);
				tableRow.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellGral = new PdfPCell();
				cellGral.setBorderColor(Color.WHITE);				
				cellGral.addElement(tableRow);
				
				tableGral.addCell(cellGral);
			
				//******************   DETALLE DE DOCUMENTOS  **********************			
				logger.debug(">>> Obteniendo datos de Detalle de Documentos...");
				
				ArrayList detalleDocumentos = PDFServices.llenaArrayListDetalleDummy();
					
					tableRow = PDFServices.creaTablaDetalleDocs(datoLiquidacionVO.getFechaLimitePago(), detalleDocumentos, datoLiquidacionVO.getTotalPagar());
				tableRow.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellGral = new PdfPCell();
				cellGral.setBorderColor(Color.WHITE);				
				cellGral.addElement(tableRow);
				
				tableGral.addCell(cellGral);
			
				//******************   Nom y Firma asegurado  **********************
				logger.debug(">>> Obteniendo tabla nombre y Firma...");
							
				tableRow = PDFServices.creaTablaNombreFirma();
				tableRow.setHorizontalAlignment(Element.ALIGN_LEFT);
				tableRow.setSpacingAfter(40f);
				cellGral = new PdfPCell();
				cellGral.setBorderColor(Color.WHITE);			
				cellGral.addElement(tableRow);
				
				tableGral.addCell(cellGral);
	
				//**  Footer
				logger.debug(">>> Obteniendo pie de pagina...");
				//String pathImageApoderado = "C:\\apache-tomcat-5.5.26\\webapps\\AON\\resources\\apoderado.JPG";
				String pathImageApoderado = pathResources + "apoderado.JPG";
				
				tableFoot = PDFServices.createFoot(pathImageApoderado, 2);
				tableFoot.addCell("");
				tableFoot.setTotalWidth(555f);			
				tableFoot.writeSelectedRows(0, 1, 40, 70, writer.getDirectContent());
				
				document.add(tableGral);
			}
    	} catch (FileNotFoundException fne) {
			logger.debug(fne.getMessage());
    	} catch (DocumentException de) {
			logger.debug(de.getMessage());
		}finally{
			try{
				document.close();
				}catch(Exception e){
					logger.debug("ERROR No se pudieron obtener todos los datos para el pdf (valores nulos) "+e);
					return -1;
				}
        }
    	return 0;
    	    
    }// end method generaReciboPago

	//-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	public static void main(String [] args){
		//PDFGenerator prueba = new PDFGenerator();
		
		String numeroSituacion = "1";
		
		String []encabezado = {"AUTOS", "TARJETA DE IDENTIFICACION", "Automóviles/Auto Colectiva"};
		int res = 0;//PDFGenerator.genCaratulaPolizaPdf("1", "500", "M", "364", encabezado, numeroSituacion);
		if(res == 0){
			logger.debug(">>>>>>> Caratula de Poliza ok...");
		}else{
			logger.debug(">>>>>>> Caratula de Poliza no ok...");
		}
		
		
		//Double.parseDouble("7,560.20");
		
		/*int res3 = PDFGenerator.generaReciboPagoPdf("1", "500", "M", "320");
		if(res3 == 0){
			logger.debug(">>>>>>> Recibo de Pago ok...");
		}
		
		/*
		double monto = 2101001.99;
		logger.debug(">>>>>>> monto gen ::: "+ monto);
		
		String montoString = String.valueOf(monto);    	
		logger.debug(">>>>>>> montoString ::: "+ montoString);
		
		String resultado = PdfMontoUtils.convierteMontoALetra(montoString);	
		logger.debug(">>>>>>> resultado gen ::: "+ resultado);
		*/
	}
	
}// Fin de la clase