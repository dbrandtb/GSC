package mx.com.aon.catbo.web;

import java.awt.Color;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.CalendarioVO;
import mx.com.aon.catbo.service.CalendarioManager;
import mx.com.aon.portal.service.PagedList;
import org.apache.log4j.Logger;
import java.util.Iterator;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import mx.com.aon.portal.util.Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.Counter;

public class ListaCalendarioAction extends AbstractListAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1137878786546L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaCalendarioAction.class);
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient CalendarioManager calendarioManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo VO
	 * con los valores de la consulta.
	 */
	private List<CalendarioVO> mCalendarioList;
	
	/**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
	 */
	private String formato;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado 
	 */
	private String filename;
		
	/**
	 * Atributo inyectado por spring el cual direcciona a travez del tipo de formato para generar 
	 * el archivo a ser exportado
	 */
	private ExportMediator exportMediator;
	
	
	private String codPais;
	private String yearCabecera;
	private String codMes;
	private String desPais;
	private String desMes;
	
	private Map myList;
	private List<CalendarioVO> listaValoresDiaDescripcion;
	
	
	
	/**
	 * Metodo que realiza una búsqueda en base a criterios de búsquedas
	 *  
	 * @param 
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarCalendarClick() throws Exception{
		try{
            PagedList pagedList = calendarioManager.buscarCalendario(codPais, yearCabecera, codMes, start, limit);
            mCalendarioList = pagedList.getItemsRangeList();
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

	
	/**
	 * Metodo que busca un conjunto de formatos documentos  
	 * y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarCalendarClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Calendario : " + formato );
		}
		
		try {
		     contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }

			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "Calendario." + exportFormat.getExtension();
			TableModelExport model =  calendarioManager.getModel(codPais, yearCabecera, codMes);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}

	/**
	 * Metodo que busca un conjunto de dias junto con
	 * el pais mes y año y exporta el resultado en Formato PDF, Excel, etc.
	 * @return succes
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String showPDF () throws Exception {
		formato = "PDF";
		filename = "CALENDARIO_BO.PDF";
		contentType = "application/pdf";

		Document document = new Document();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		HttpServletResponse response = ServletActionContext.getResponse();
		PdfWriter.getInstance(document, outputStream);
		document.open();

		TreeMap camposMap = new TreeMap();
		camposMap.put("02YEAR", "Año: ");
		camposMap.put("03MES", "Mes: ");
		camposMap.put("01PAIS", "Pais: ");
		


		//valores que vendrian del la pantalla de calendario.js seria la descripcion de los combos pais y mes
		myList = new HashMap();
		myList.put("02YEAR", yearCabecera);
		myList.put("03MES", desMes);
		myList.put("01PAIS", desPais);

	
		//valores de las cabecera del
		TreeMap headerTabla = new TreeMap();
		headerTabla.put("columna01", "Dias");
		headerTabla.put("columna02", "Descripcion");
		
		
		/*
		@SuppressWarnings("unused")
		HashMap<String, CalendarioVO> mapaAtributos = new HashMap<String, CalendarioVO>();
		

		try {
		mapaAtributos = administracionCasosManager.obtenerAtributosVariablesCasoParaExportar((String)myList.get("descripcionPais"),(String)myList.get("descripcionAnhio"),(String)myList.get("descripcionMes"));
		} catch (ApplicationException ae) {
			mapaAtributos = null;
		}*/
		
		try {
			PagedList pagedList = calendarioManager.buscarCalendario(codPais,yearCabecera,codMes, 0, 9999);
			listaValoresDiaDescripcion = pagedList.getItemsRangeList();
		} catch (ApplicationException ae) {
			listaValoresDiaDescripcion = null;
		}
		formarDocumento(camposMap, myList, headerTabla, listaValoresDiaDescripcion, document);
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
	private void formarDocumento (TreeMap labelsEncabezado, Map datosEncabezado, TreeMap headerTabla, List<CalendarioVO> listaValoresDiaDescripcion, Document documento) throws Exception {
		//Comienza recorrido de datos comunes al caso parte del encabezado
		String lineaBlanco=" ";
		int totalLinea=2;
		Iterator iterator = labelsEncabezado.keySet().iterator();
		while (iterator.hasNext()) {
			//String keyLoca=
			String keyName = (String)iterator.next();
			StringBuffer sb = new StringBuffer();
			sb.append(labelsEncabezado.get(keyName)).append(" ").append(datosEncabezado.get(keyName));
			Chunk chunk = new Chunk(sb.toString());
			documento.add(new Paragraph(chunk));
		}
		for(int i=0;i<totalLinea; i++)
		{
			StringBuffer sb = new StringBuffer();
			sb.append(lineaBlanco);
			Chunk chunk = new Chunk(sb.toString());
			documento.add(new Paragraph(chunk));
		}
		//comieza parte de la table con encabezado y cuerpo
		
		PdfPTable tablita=new PdfPTable(2);
		Iterator iterator02 = headerTabla.keySet().iterator();
		while (iterator02.hasNext()) {
			String keyColumn=(String)iterator02.next();
			String valColumn=(String)headerTabla.get(keyColumn);
			PdfPCell headCell= new PdfPCell();
			headCell.setBorder(Rectangle.BOX);
			headCell.setBorderWidth(1);
			headCell.setBorderColor(Color.black);
			headCell.setBackgroundColor(Color.red);			
			Paragraph p = new Paragraph(valColumn);
			headCell.addElement(p);
			tablita.addCell(headCell);
			//tablita.addCell(valColumn);
		}
		if (listaValoresDiaDescripcion != null )
		{
			for(Iterator i=listaValoresDiaDescripcion.iterator(); i.hasNext();){
				CalendarioVO calendarioVO= (CalendarioVO)i.next();
				tablita.addCell(calendarioVO.getDia());
				tablita.addCell(calendarioVO.getDescripcionDia());
			}
		}
		documento.add(tablita);
		
	}

	public static Logger getLogger() {
		return logger;
	}


	public static void setLogger(Logger logger) {
		ListaCalendarioAction.logger = logger;
	}


	public CalendarioManager obtenCalendarioManager() {
		return calendarioManager;
	}


	public void setCalendarioManager(CalendarioManager calendarioManager) {
		this.calendarioManager = calendarioManager;
	}


	public List<CalendarioVO> getMCalendarioList() {
		return mCalendarioList;
	}


	public void setMCalendarioList(List<CalendarioVO> calendarioList) {
		mCalendarioList = calendarioList;
	}

	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public ExportMediator getExportMediator() {
		return exportMediator;
	}


	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}


	public String getCodPais() {
		return codPais;
	}


	public void setCodPais(String codPais) {
		this.codPais = codPais;
	}


	public String getYearCabecera() {
		return yearCabecera;
	}


	public void setYearCabecera(String yearCabecera) {
		this.yearCabecera = yearCabecera;
	}


	public String getCodMes() {
		return codMes;
	}


	public void setCodMes(String codMes) {
		this.codMes = codMes;
	}


	public Map getMyList() {
		return myList;
	}


	public void setMyList(Map myList) {
		this.myList = myList;
	}


	public List<CalendarioVO> getListaValoresDiaDescripcion() {
		return listaValoresDiaDescripcion;
	}


	public void setListaValoresDiaDescripcion(
			List<CalendarioVO> listaValoresDiaDescripcion) {
		this.listaValoresDiaDescripcion = listaValoresDiaDescripcion;
	}


	public String getDesPais() {
		return desPais;
	}


	public void setDesPais(String desPais) {
		this.desPais = desPais;
	}


	public String getDesMes() {
		return desMes;
	}


	public void setDesMes(String desMes) {
		this.desMes = desMes;
	}
	
	

}
