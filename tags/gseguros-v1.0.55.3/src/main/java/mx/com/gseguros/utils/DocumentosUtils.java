package mx.com.gseguros.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.RectangleReadOnly;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

public class DocumentosUtils
{
	private final static Logger logger = Logger.getLogger(DocumentosUtils.class);
	
	public static File fusionarDocumentosPDF(List<File>origen,File destino)
	{
		return DocumentosUtils.fusionarDocumentosPDF(origen,destino,false);
	}
	
	public static File fusionarDocumentosPDF(List<File>origen,File destino,boolean credencial)
	{
		File resp=null;
		try
		{
			List<InputStream>listaInput=new ArrayList<InputStream>();
			for(File iOrigen:origen)
			{
				listaInput.add(new FileInputStream(iOrigen));
			}
			DocumentosUtils.concatPDFs(listaInput,new FileOutputStream(destino),false,credencial);
			resp=new File(destino.getCanonicalPath());
		}catch(Exception ex)
		{
			logger.error(ex);
		}
		return resp;
	}
	
	public static void concatPDFs(List<InputStream> streamOfPDFFiles, OutputStream outputStream, boolean paginate)
	{
		DocumentosUtils.concatPDFs(streamOfPDFFiles, outputStream, paginate, false);
	}
	
	public static void concatPDFs(List<InputStream> streamOfPDFFiles, OutputStream outputStream, boolean paginate, boolean credencial) {

	    Document document = null;
	    
	    /*
	     * Si es credencial, se configura el tamanio ID_1 que
	     * mide 85.60 x 53.98 mm
	     */
	    if(credencial)
	    {
	    	document = new Document(PageSize.ID_1);
	    }
	    else
	    {
	    	document = new Document(new RectangleReadOnly(625,842));
	    }
	    
	    try {
	      List<InputStream> pdfs = streamOfPDFFiles;
	      List<PdfReader> readers = new ArrayList<PdfReader>();
	      int totalPages = 0;
	      Iterator<InputStream> iteratorPDFs = pdfs.iterator();

	      // Create Readers for the pdfs.
	      while (iteratorPDFs.hasNext()) {
	        InputStream pdf = iteratorPDFs.next();
	        PdfReader pdfReader = new PdfReader(pdf);
	        readers.add(pdfReader);
	        totalPages += pdfReader.getNumberOfPages();
	      }
	      // Create a writer for the outputstream
	      PdfWriter writer = PdfWriter.getInstance(document, outputStream);

	      document.open();
	      BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
	      PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
	      // data

	      PdfImportedPage page;
	      int currentPageNumber = 0;
	      int pageOfCurrentReaderPDF = 0;
	      Iterator<PdfReader> iteratorPDFReader = readers.iterator();

	      // Loop through the PDF files and add to the output.
	      while (iteratorPDFReader.hasNext()) {
	        PdfReader pdfReader = iteratorPDFReader.next();

	        // Create a new page in the target for each source page.
	        while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
	          document.newPage();
	          pageOfCurrentReaderPDF++;
	          currentPageNumber++;
	          page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
	          cb.addTemplate(page, 0, 0);

	          // Code for pagination.
	          if (paginate) {
	            cb.beginText();
	            cb.setFontAndSize(bf, 9);
	            cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "" + currentPageNumber + " of " + totalPages, 520, 5, 0);
	            cb.endText();
	          }
	        }
	        pageOfCurrentReaderPDF = 0;
	      }
	      outputStream.flush();
	      document.close();
	      outputStream.close();
	    } catch (Exception e) {
	      logger.error(e);
	    } finally {
	      if (document.isOpen())
	        document.close();
	      try {
	        if (outputStream != null)
	          outputStream.close();
	      } catch (IOException ioe) {
	        logger.error(ioe);
	      }
	    }
	  }
	
	public static File pdfBlanco(File output){
		
		try {
			FileOutputStream outputStream=new FileOutputStream(output);;
			Document doc=new Document();
			doc.open();
			doc.newPage();
			PdfWriter writer = PdfWriter.getInstance(doc,outputStream);
			writer.open();
			doc.open();
			doc.add(new Paragraph(" "));
			writer.flush();
			doc.close();
			writer.close();
			outputStream.flush();
			outputStream.close();
			
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (DocumentException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		return output;

	}
	
	
	
	/**
	 * Si es un archivo con numero impar de hojas le agrega una hoja blanca para la impresion duplex
	 * 
	 * @param input archivo a analizar 
	 * @param blanco ruta para guardar temporalmente el pdf blanco que se le agrega al archivo
	 * @return el mismo input pero con una hoja blanca si es el caso
	 */
	public static File blancoParaDuplex(File input,File blanco){
		try {
			PdfReader reader=new PdfReader(input.getAbsolutePath());
			int nPaginas=reader.getNumberOfPages();
			List<File> fus=new ArrayList<File>();
			fus.add(input);
			if(nPaginas%2!=0){
				fus.add(pdfBlanco(blanco));
			}
			
			long timestamp = System.currentTimeMillis();
			long rand = new Double(1000d*Math.random()).longValue();
			
			File ot=new File(blanco.getAbsoluteFile().getParent()+"\\"+
					input.getName().substring(
							0, input.getName().indexOf(".")
							)+"_dplx_"+timestamp+rand+".pdf");
			
			
			return mixPdf(fus,ot); 
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e);
		}  catch (IOException e) {
			logger.error(e);
		}
		return null;
	}
	
	public static File mixPdf(List<File> pdfs,File salida) {
		
		Document document= new Document();
		PdfCopy copy;
		try {
			copy = new PdfCopy(document, new FileOutputStream(salida));
		
			document.open();
			PdfReader reader;
			int n;
			for(File doc: pdfs){
				reader=new PdfReader(doc.getAbsolutePath());
				n=reader.getNumberOfPages();
				for(int page=0;page<n;){
					copy.addPage(copy.getImportedPage(reader, ++page));
				}
				copy.freeReader(reader);
				reader.close();
			}
			document.close();
		
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (DocumentException e) {
			
			logger.error(e);
		} catch (IOException e) {
		
			logger.error(e);
		}catch(ExceptionConverter e){
			logger.error(e);
		}catch(Exception e){
			logger.error(e);
		}
		
		return salida;
		
	}
	
//	public static void main(String []args)throws Exception{
//		
//		List<File> l=new ArrayList();
//		l.add(new File("c:\\RES\\horizontal.pdf"));
//		l.add(new File("c:\\RES\\lo.pdf"));
//		mixPdf(l, new File("c:\\RES\\slm.pdf"));
//		
//	}
}