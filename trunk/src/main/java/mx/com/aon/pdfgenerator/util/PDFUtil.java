package mx.com.aon.pdfgenerator.util;

import java.io.FileOutputStream;
import java.io.IOException;

import mx.com.aon.pdfgenerator.PDFGenerator;
import mx.com.aon.pdfgenerator.services.PDFServices;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PDFUtil {
	
	private static Logger logger = Logger.getLogger(PDFUtil.class);
	
	public static void imprimeFuentes(){	
	
		Font font = null;
		
		System.out.println(">>>>>>> Entrando en imprimeFuentes()...");
    	String pathPdf = "C:\\ing_pdf\\";//salida donde genera pdf
    	String nameFile = "fuentes_caratulas.pdf";
          
        PdfPTable tableGral = new PdfPTable(1);
        tableGral.setWidthPercentage(100);
		    
		// step 1: creation of a document-object & we create a writer
        float [] pageSize ={ 40f, 40f, 30f, 30f};
    	Document document = PDFServices.createPDF(pathPdf, nameFile, pageSize, null);
    	
    	Font []fonts = new Font[10]; 
    	float fontSize = 7f;
    	
    	
    	
    	try {
    		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pathPdf + nameFile));
    		
			// step 3: we open the document
			document.open();
		
			// step 4: we add a table to the document
			
			BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA,  BaseFont.CP1252, BaseFont.EMBEDDED);
    		    		
    		
    		for (int i = 0; i < 10; i++) {
    			font = new Font(helvetica, fontSize, Font.BOLD);
    			document.add(new Paragraph(fontSize+"	"+"Datos del Asegurado		-		Nombre		-		Coberturas Amparadas", font));
    			fontSize = fontSize + 0.5f;
    		}
			
    		fontSize = 7f;
    		for (int j = 0; j < 10; j++) {
    			font = new Font(helvetica, fontSize, Font.NORMAL);
    			document.add(new Paragraph(fontSize+"	"+"JOSE LUIS DUARTE	-	HECHO EN BRASIL		-		29831AON RISK", font));
    			document.add(new Paragraph(fontSize+"	"+"Daños Materiales	-	VALOR COMERCIAL		-		MÉXICO", font));
    			fontSize = fontSize + 0.5f;
    		}
	    	
		}catch(DocumentException e){
			logger.error("DocumentExceptionError ::: ",e);
		}catch(IOException e){
			logger.error("IOExceptionError ::: ",e);
		}finally{
			
			document.close();
		}
	}

}
