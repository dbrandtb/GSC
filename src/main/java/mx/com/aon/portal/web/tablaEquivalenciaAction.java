/*
 * $Id: PageNumbersWatermark.java,v 1.3 2005/05/09 11:52:50 blowagie Exp $
 * $Name:  $
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://itextdocs.lowagie.com/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * itext-questions@lists.sourceforge.net
 */

package mx.com.aon.portal.web;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

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


import com.opensymphony.xwork2.ActionSupport;

import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.pdfgenerator.services.PDFServices;
import mx.com.aon.portal.service.AdministracionEquivalenciaManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.Tabla_EquivalenciaVO;
/**
 *   Action que atiende las peticiones de la pantalla administracion de equivalencia.
 * 
 */
@SuppressWarnings("serial")
public class tablaEquivalenciaAction extends AbstractListAction{
	private transient AdministracionEquivalenciaManager administracionEquivalenciaManager;

	private List <Tabla_EquivalenciaVO> equivalenciaList;
	private List <Tabla_EquivalenciaVO> equivalenciaTextList;

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(tablaEquivalenciaAction.class);
	
	private boolean success;

	private String cdsistema;
	private String cdtablaacw;
	private String country_code;
	private String nmtabla;
	private String otclave01acw;
	private String otclave01ext;
	private String otclave02acw;
	private String otclave02ext;
	private String otclave03acw;
	private String otclave03ext;
	private String otclave04acw;
	private String otclave04ext;
	private String otclave05acw;
	private String otclave05ext;
	private String otclave01;
	private String otvalor;
	private String cdTablaExt;
	private String nmcolumna;
	private String nmcolumnatcataext;
	

	private InputStream inputStream;

	private String formato;
	
	private String filename;
	
	private String contentType;
	

	public String getNmcolumnatcataext() {
		return nmcolumnatcataext;
	}


	public void setNmcolumnatcataext(String nmcolumnatcataext) {
		this.nmcolumnatcataext = nmcolumnatcataext;
	}



	public String getNmcolumna() {
		return nmcolumna;
	}


	public void setNmcolumna(String nmcolumna) {
		this.nmcolumna = nmcolumna;
	}


	/**
	 * Metodo que busca y obtiene un unico registro de Agrupacion por papel.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	
	
    public String cmdGuardarClick()throws Exception
    {
    	
    	    String messageResult = "";
        try
        {
        	Tabla_EquivalenciaVO tabla_EquivalenciaVO =new Tabla_EquivalenciaVO ();
        	
        	for(int i=0; i<equivalenciaList.size() ; i++){
        		
        	Tabla_EquivalenciaVO tabla_EquivalenciaVO_grid=equivalenciaList.get(i);
        	
        	tabla_EquivalenciaVO.setCountry_code(tabla_EquivalenciaVO_grid.getCountry_code());
        	tabla_EquivalenciaVO.setCdsistema(tabla_EquivalenciaVO_grid.getCdsistema());
        	tabla_EquivalenciaVO.setCdtablaacw(tabla_EquivalenciaVO_grid.getCdtablaacw());
        	tabla_EquivalenciaVO.setNmtabla(tabla_EquivalenciaVO_grid.getNmtabla());

        	tabla_EquivalenciaVO.setOtclave01acw(tabla_EquivalenciaVO_grid.getOtclave01acw());
        	tabla_EquivalenciaVO.setOtclave01ext(tabla_EquivalenciaVO_grid.getOtclave01ext());

        	tabla_EquivalenciaVO.setOtclave02acw(tabla_EquivalenciaVO_grid.getOtclave02acw());
        	tabla_EquivalenciaVO.setOtclave02ext(tabla_EquivalenciaVO_grid.getOtclave02ext());

        	tabla_EquivalenciaVO.setOtclave03acw(tabla_EquivalenciaVO_grid.getOtclave03acw());
        	tabla_EquivalenciaVO.setOtclave03ext(tabla_EquivalenciaVO_grid.getOtclave03ext());

        	tabla_EquivalenciaVO.setOtclave04acw(tabla_EquivalenciaVO_grid.getOtclave04acw());
        	tabla_EquivalenciaVO.setOtclave04ext(tabla_EquivalenciaVO_grid.getOtclave04ext());

        	tabla_EquivalenciaVO.setOtclave05acw(tabla_EquivalenciaVO_grid.getOtclave05acw());
        	tabla_EquivalenciaVO.setOtclave05ext(tabla_EquivalenciaVO_grid.getOtclave05ext());
        	
        	
            messageResult = administracionEquivalenciaManager.GuardarEquiv(tabla_EquivalenciaVO);

        	}
        	
            success = true;
            addActionMessage(messageResult);
            success = true;
            return SUCCESS;            
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
    }

    
	public String cmdObtenerEquivalencia() throws ApplicationException{
		try{

			PagedList pagedList = this.administracionEquivalenciaManager.obtenerEquivalencia(country_code, cdsistema, cdtablaacw, start, limit);
			
			equivalenciaList = pagedList.getItemsRangeList();
            totalCount = pagedList.getTotalItems();
           
            success = true;
            return SUCCESS;            
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}

	
	public String cmdObtenerTablaEquivalencia() throws ApplicationException{
	try{
		
		equivalenciaTextList=new ArrayList<Tabla_EquivalenciaVO>();
		
		Tabla_EquivalenciaVO tabla_EquivalenciaVO= administracionEquivalenciaManager.getTablaEquivalente(country_code, cdsistema, cdtablaacw);
		equivalenciaTextList.add(tabla_EquivalenciaVO);
		success = true;
        return SUCCESS;
		
	}catch(ApplicationException e)
	{
        success = false;
        addActionError(e.getMessage());
        return SUCCESS;

	}catch( Exception e){
        success = false;
        addActionError(e.getMessage());
        return SUCCESS;
    }
}
	
	
	
	public String cmdObtenerCatExterno() throws ApplicationException{
		try{

			PagedList pagedList = this.administracionEquivalenciaManager.obtenerCatExterno(country_code,cdsistema,otclave01, otvalor, cdTablaExt, start, limit);

			equivalenciaList = pagedList.getItemsRangeList();
            totalCount = pagedList.getTotalItems();
           
            success = true;
            return SUCCESS;            
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	@SuppressWarnings("unchecked")
	public String showPDF () throws Exception {
		formato = "PDF";
		filename = "REPORTE_TABLAS_NO_CONCILIADAS.PDF";
		contentType = "application/pdf";

		Document document = new Document();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		HttpServletResponse response = ServletActionContext.getResponse();
		PdfWriter writer = PdfWriter.getInstance(document, outputStream);
		
        writer.setPageEvent(new PageNumbersWatermark(writer,document));        
              
		document.open();

		//**************************
		WrapperResultados wrapperResultados = administracionEquivalenciaManager.generarReporteTablasNoConciliadas();
		String key1="pv_registroacw_o";
		String key2="pv_registroext_o";
		List  listaTablasAA = (List)wrapperResultados.getItemMap().get(key1);
		List  listaTablasACW = (List)wrapperResultados.getItemMap().get(key2);
		//ACA DEBEMOS LLAMAR AL MANAGER Y OBTENER 2 LISTAS CON EL MAPEO DE LOS 
		//2 CURSORES QUE ME DEVUELVE EL PL 
		//**************************

		formarDocumento(listaTablasACW,listaTablasAA,document);
		document.close();
		inputStream = new ByteArrayInputStream(outputStream.toString().getBytes());//new ByteArrayInputStream(document.toString().getBytes());
		response.reset();
		response.setHeader("Content-Disposition","inline;filename=" + filename + "");
		response.setContentType("application/pdf");
		response.setContentLength(outputStream.size());
		ServletOutputStream out = response.getOutputStream();
		outputStream.writeTo(out);
		out.flush();
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private void formarDocumento (List<Tabla_EquivalenciaVO> listaTablasACW,List<Tabla_EquivalenciaVO> listaTablasAA, Document documento) throws Exception {
		
		String fdt = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(fdt);
		sdf.setLenient(false);
		Date hoy = new Date();
		Calendar feCalendar = new GregorianCalendar();
		
		feCalendar.setTime(hoy);
		Chunk chunkUsuarios = new Chunk("AON Risk Services");
		chunkUsuarios.setUnderline(+1f, -2f);
		documento.add(new Paragraph(chunkUsuarios)); //lo agrego al documento
		
		documento.add(new Paragraph(new Chunk("  ")));
		PdfPTable table = new PdfPTable(1);
		
		
		Paragraph paragraph = new Paragraph("Fecha: " + feCalendar.get(Calendar.DAY_OF_MONTH)+"/"+(feCalendar.get(Calendar.MONTH)+1)+"/"+feCalendar.get(Calendar.YEAR),PDFServices.getFormat("helvetica", 8.5f, Font.BOLD));	
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.addElement(paragraph);
		table.addCell(cell);
		table.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.setTotalWidth(2);
		table.setWidthPercentage(22);
		
		int minuto= feCalendar.get(Calendar.MINUTE);
		String min;
		if (minuto < 10)
			min ="0"+minuto;
		else min=""+minuto;
		
		int segundo=feCalendar.get(Calendar.SECOND);
		String seg;
		if (segundo < 10)
			seg ="0"+segundo;
		else seg=""+segundo;
		
		
		paragraph = new Paragraph("Hora: " + feCalendar.get(Calendar.HOUR_OF_DAY) + ":" + min + ":" + seg,PDFServices.getFormat("helvetica", 8.5f, Font.BOLD));
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.addElement(paragraph);
		table.addCell(cell);
		
		paragraph = new Paragraph("Reporte: CART0014" , PDFServices.getFormat("helvetica", 8.5f, Font.BOLD));
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.addElement(paragraph);
		table.addCell(cell);
		
		documento.add(table);
		
		documento.add(new Paragraph(new Chunk("  ")));
		documento.add(new Paragraph(new Chunk("  ")));
		documento.add(new Paragraph(new Chunk("  ")));
		
		chunkUsuarios = new Chunk("Homologación de Tablas AON - Aon Access");
		chunkUsuarios.setUnderline(+1f, -2f);
		documento.add(new Paragraph(chunkUsuarios)); //lo agrego al documento
		
		documento.add(new Paragraph(new Chunk("  ")));
		
		//Comienza bucle para Tablas registradas en Aon Catweb faltantes en Aon Access
		chunkUsuarios = new Chunk("Tablas registradas en Aon Catweb faltantes en Aon Access");
		chunkUsuarios.setUnderline(+1f, -2f);
		documento.add(new Paragraph(chunkUsuarios)); //lo agrego al documento
	 
		Font SMALL_FONT = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new Color(50, 50, 50));
		
		documento.add(new Paragraph(new Chunk("  ")));
		table = new PdfPTable(3);
		int i = 0;
		paragraph = new Paragraph("Nombre Tabla", PDFServices.getFormat("helvetica", 10.5f, Font.BOLD));
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(206,115,131));
		cell.addElement(paragraph);
		table.addCell(cell);
		
		paragraph = new Paragraph("Cantidad de Columnas", PDFServices.getFormat("helvetica", 10.5f, Font.BOLD));
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(206,115,131));
		cell.addElement(paragraph);
		table.addCell(cell);

		paragraph = new Paragraph("Indicador Uso", PDFServices.getFormat("helvetica", 10.5f, Font.BOLD));
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(206,115,131));
		cell.addElement(paragraph);
		table.addCell(cell);

		while (i < listaTablasACW.size()) {
			Tabla_EquivalenciaVO tablaEquivalenciaVO = (Tabla_EquivalenciaVO)listaTablasACW.get(i);
			table.addCell(tablaEquivalenciaVO.getCdtablaacw());
			table.addCell(tablaEquivalenciaVO.getNmcolumna());
			table.addCell(tablaEquivalenciaVO.getCdsistema());
			i++;
		}
		documento.add(table);
		
		documento.add(new Paragraph(new Chunk("  ")));
		//Comienza bucle para Tablas registradas en Aon Access faltantes en Aon Catweb
		chunkUsuarios = new Chunk("Tablas registradas en Aon Access faltantes en Aon Catweb");
		chunkUsuarios.setUnderline(+1f, -2f);
		documento.add(new Paragraph(chunkUsuarios)); //lo agrego al documento

		Font SMALL_FONT2 = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new Color(50, 50, 50));
		
		documento.add(new Paragraph(new Chunk("  ")));
		table = new PdfPTable(3);
		i = 0;
		paragraph = new Paragraph("Nombre Tabla", PDFServices.getFormat("helvetica", 10.5f, Font.BOLD));
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(206,115,131));
		cell.addElement(paragraph);
		table.addCell(cell);
		
		paragraph = new Paragraph("Cantidad de Columnas", PDFServices.getFormat("helvetica", 10.5f, Font.BOLD));
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(206,115,131));
		cell.addElement(paragraph);
		table.addCell(cell);

		paragraph = new Paragraph("Indicador Uso", PDFServices.getFormat("helvetica", 10.5f, Font.BOLD));
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(206,115,131));
		cell.addElement(paragraph);
		table.addCell(cell);
		//aca debe ser la otra lista
		while (i < listaTablasAA.size()) {
			Tabla_EquivalenciaVO tablaEquivalenciaVO = (Tabla_EquivalenciaVO)listaTablasAA.get(i);
			table.addCell(tablaEquivalenciaVO.getCdTablaExt());
			table.addCell(tablaEquivalenciaVO.getNmcolumna());
			table.addCell(tablaEquivalenciaVO.getCdsistema());
			i++;
		}
		documento.add(table);
	}
	
	
	public AdministracionEquivalenciaManager getAdministracionEquivalenciaManager() {
		return administracionEquivalenciaManager;
	}

	
	public void setAdministracionEquivalenciaManager(
			AdministracionEquivalenciaManager administracionEquivalenciaManager) {
		this.administracionEquivalenciaManager = administracionEquivalenciaManager;
	}


	public List<Tabla_EquivalenciaVO> getEquivalenciaList() {
		return equivalenciaList;
	}


	public void setEquivalenciaList(List<Tabla_EquivalenciaVO> equivalenciaList) {
		this.equivalenciaList = equivalenciaList;
	}

	
	public Logger getLogger() {
		return logger;
	}


	public void setLogger(Logger logger) {
		this.logger = logger;
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getCdsistema() {
		return cdsistema;
	}


	public void setCdsistema(String cdsistema) {
		this.cdsistema = cdsistema;
	}


	public String getCdtablaacw() {
		return cdtablaacw;
	}


	public void setCdtablaacw(String cdtablaacw) {
		this.cdtablaacw = cdtablaacw;
	}


	public String getCountry_code() {
		return country_code;
	}


	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}



	public String getNmtabla() {
		return nmtabla;
	}



	public void setNmtabla(String nmtabla) {
		this.nmtabla = nmtabla;
	}



	public String getOtclave01acw() {
		return otclave01acw;
	}



	public void setOtclave01acw(String otclave01acw) {
		this.otclave01acw = otclave01acw;
	}



	public String getOtclave01ext() {
		return otclave01ext;
	}



	public void setOtclave01ext(String otclave01ext) {
		this.otclave01ext = otclave01ext;
	}



	public String getOtclave02acw() {
		return otclave02acw;
	}



	public void setOtclave02acw(String otclave02acw) {
		this.otclave02acw = otclave02acw;
	}



	public String getOtclave02ext() {
		return otclave02ext;
	}



	public void setOtclave02ext(String otclave02ext) {
		this.otclave02ext = otclave02ext;
	}



	public String getOtclave03acw() {
		return otclave03acw;
	}



	public void setOtclave03acw(String otclave03acw) {
		this.otclave03acw = otclave03acw;
	}



	public String getOtclave03ext() {
		return otclave03ext;
	}



	public void setOtclave03ext(String otclave03ext) {
		this.otclave03ext = otclave03ext;
	}



	public String getOtclave04acw() {
		return otclave04acw;
	}



	public void setOtclave04acw(String otclave04acw) {
		this.otclave04acw = otclave04acw;
	}



	public String getOtclave04ext() {
		return otclave04ext;
	}



	public void setOtclave04ext(String otclave04ext) {
		this.otclave04ext = otclave04ext;
	}



	public String getOtclave05acw() {
		return otclave05acw;
	}



	public void setOtclave05acw(String otclave05acw) {
		this.otclave05acw = otclave05acw;
	}



	public String getOtclave05ext() {
		return otclave05ext;
	}



	public void setOtclave05ext(String otclave05ext) {
		this.otclave05ext = otclave05ext;
	}


	public String getOtclave01() {
		return otclave01;
	}


	public void setOtclave01(String otclave01) {
		this.otclave01 = otclave01;
	}


	public String getOtvalor() {
		return otvalor;
	}


	public void setOtvalor(String otvalor) {
		this.otvalor = otvalor;
	}

	public String getCdTablaExt() {
		return cdTablaExt;
	}


	public void setCdTablaExt(String cdTablaExt) {
		this.cdTablaExt = cdTablaExt;
	}


	public InputStream getInputStream() {
		return inputStream;
	}


	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}


	public String getFormato() {
		return formato;
	}


	public void setFormato(String formato) {
		this.formato = formato;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public String getContentType() {
		return contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	public List<Tabla_EquivalenciaVO> getEquivalenciaTextList() {
		return equivalenciaTextList;
	}


	public void setEquivalenciaTextList(
			List<Tabla_EquivalenciaVO> equivalenciaTextList) {
		this.equivalenciaTextList = equivalenciaTextList;
	}
	
}
