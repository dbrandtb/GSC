package mx.com.gseguros.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

public class DocumentosUtils
{
	private final static Logger logger = Logger.getLogger(DocumentosUtils.class);
	public static File fusionarDocumentosPDF(List<File>origen,File destino)
	{
		File resp=null;
		try
		{
			List<InputStream>listaInput=new ArrayList<InputStream>();
			for(File iOrigen:origen)
			{
				listaInput.add(new FileInputStream(iOrigen));
			}
			DocumentosUtils.concatPDFs(listaInput,new FileOutputStream(destino),false);
			resp=new File(destino.getCanonicalPath());
		}catch(Exception ex)
		{
			logger.error(ex);
		}
		return resp;
	}
	
	public static void concatPDFs(List<InputStream> streamOfPDFFiles, OutputStream outputStream, boolean paginate) {

	    Document document = new Document();
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
}