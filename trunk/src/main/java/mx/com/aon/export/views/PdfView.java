package mx.com.aon.export.views;

import java.io.OutputStream;

import mx.com.aon.export.ExportView;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;

public class PdfView extends ItexView implements ExportView {

	private static final String EXTENSION = "pdf";

	/*
	 * (non-Javadoc)
	 * @see mx.com.aon.export.ExportView#getExtension()
	 */
	public String getExtension() {
		return EXTENSION;
	}

	/**
	 * Se utiliza para generar el tipo de documento utilizado para la exportacion
	 * @param Document documento general para se escrita la informacion
	 * @param OutputStream a ser utilizado para escritura
	 * 
	 */
	@Override
	void initDocument(Document document, OutputStream out) throws DocumentException {
		PdfWriter.getInstance(document, out);		
	}
}
