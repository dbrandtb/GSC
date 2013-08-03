package mx.com.aon.portal.web;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import mx.com.aon.pdfgenerator.services.PDFServices;
import mx.com.aon.portal.model.Tabla_EquivalenciaVO;
import mx.com.aon.portal.util.WrapperResultados;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPageEvent;
import com.lowagie.text.pdf.PdfWriter;


import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfTemplate;

public class PageNumbersWatermark implements PdfPageEvent {

	public PdfGState gstate;
    /** A template that will hold the total number of pages. */
    public PdfTemplate tpl;
    /** The font that will be used. */
    public BaseFont helv;

    public PdfWriter writer;
    
    Document document = new Document();
    
 

    public PageNumbersWatermark(PdfWriter writer, Document doc){
    	this.writer=writer;
    	this.document=doc;
    }
	public void onChapter(PdfWriter arg0, Document arg1, float arg2,
			Paragraph arg3) {
		// TODO Auto-generated method stub

	}

	public void onChapterEnd(PdfWriter arg0, Document arg1, float arg2) {
		// TODO Auto-generated method stub

	}

	public void onCloseDocument(PdfWriter writer, Document doc) {
		   tpl.beginText();
	       tpl.setFontAndSize(helv, 12);
	       tpl.setTextMatrix(0, 0);
	       tpl.showText("" + (writer.getPageNumber() - 1));
	       tpl.endText();


	}

	public void onEndPage(PdfWriter writer, Document doc) {
		PdfContentByte cb = writer.getDirectContent();
        cb.saveState();
        // compose the footer
        String text = "Página " + writer.getPageNumber() + " de ";
        float textSize = helv.getWidthPoint(text, 12);
        float textBase = document.bottom() - 20;
        cb.beginText();
        cb.setFontAndSize(helv, 12);
        // for odd pagenumbers, show the footer at the left
        if ((writer.getPageNumber() & 1) == 1) {
            cb.setTextMatrix(document.left(), textBase);
            cb.showText(text);
            cb.endText();
            cb.addTemplate(tpl, document.left() + textSize, textBase);
        }
        // for even numbers, show the footer at the right
        else {
            float adjust = helv.getWidthPoint("0", 12);
            cb.setTextMatrix(document.right() - textSize - adjust, textBase);
            cb.showText(text);
            cb.endText();
            cb.addTemplate(tpl, document.right() - adjust, textBase);
        }
        cb.saveState();



	}

	public void onGenericTag(PdfWriter arg0, Document arg1, Rectangle arg2,
			String arg3) {
		// TODO Auto-generated method stub

	}

	public void onOpenDocument(PdfWriter writer, Document doc) {
		try {

		// initialization of the template
        tpl = writer.getDirectContent().createTemplate(100, 100);
        tpl.setBoundingBox(new Rectangle(-20, -20, 100, 100));
        // initialization of the font
        helv = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false); 
		}
        catch(Exception e) {
            throw new ExceptionConverter(e);
        }


	}

	public void onParagraph(PdfWriter arg0, Document arg1, float arg2) {
		// TODO Auto-generated method stub

	}

	public void onParagraphEnd(PdfWriter arg0, Document arg1, float arg2) {
		// TODO Auto-generated method stub

	}

	public void onSection(PdfWriter arg0, Document arg1, float arg2, int arg3,
			Paragraph arg4) {
		// TODO Auto-generated method stub

	}

	public void onSectionEnd(PdfWriter arg0, Document arg1, float arg2) {
		// TODO Auto-generated method stub

	}

	public void onStartPage(PdfWriter arg0, Document arg1) {
		if (writer.getPageNumber() < 3) {
            PdfContentByte cb = writer.getDirectContentUnder();
            cb.saveState();
            cb.setColorFill(Color.pink);
            cb.beginText();
            cb.setFontAndSize(helv, 48);
            //cb.showTextAligned(Element.ALIGN_CENTER, "AON CatWeb " + writer.getPageNumber(), document.getPageSize().width() / 2, document.getPageSize().height() / 2, 45);
            cb.endText();
            cb.restoreState();
        }


	}
	

	}
	
	


