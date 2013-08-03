package mx.com.aon.catbo.web;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import mx.com.aon.catbo.model.AtributosVblesVO;
import mx.com.aon.catbo.model.CalendarioVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.ConfigurarEncuestaVO;
import mx.com.aon.catbo.model.ExtJSAtributosFaxVO;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.model.ItemVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.catbo.service.AdministracionFaxManager;
import mx.com.aon.catbo.service.ArchivosFaxesManager;
import mx.com.aon.catbo.service.CalendarioManager;
import mx.com.aon.catbo.service.ConfigurarEncuestaManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.pdfgenerator.services.PDFServices;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.PagedList;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionSupport;

public class ExportarIngresarEncuestasEditarAction extends ActionSupport{
	private static final long serialVersionUID = 1137878786546L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaCalendarioAction.class);
	
	private Map myList;
	private Map session;
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	
	private transient ConfigurarEncuestaManager configurarEncuestaManager;
	
	private List<ConfigurarEncuestaVO> mListExportarEncuestasEditar;

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
	
	private String contentType;
	
	
	/*******encuestas********/
	private String nmconfig;
	private String cdunieco;
	private String cdramo;
	private String estado;
	private String nmpoliza;
	private String cdperson;
	private String cdelemento;
	
	@SuppressWarnings("unchecked")
	public String showPDF () throws Exception {
		formato = "PDF";
		filename = "INGRESAR_ENCUESTAS.PDF";
		contentType = "application/pdf";
		
		
		Document document = new Document();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		HttpServletResponse response = ServletActionContext.getResponse();
		PdfWriter.getInstance(document, outputStream);
		document.open();

		TreeMap camposMap = new TreeMap();
		camposMap.put("01NOMBRE_CLIENTE", "Nombre: ");
		camposMap.put("02CLAVE_CATWEB", "Clave CatWeb: ");
		camposMap.put("03CLIENTE_CUENTA", "Cliente/Cuenta: ");
		camposMap.put("04ASEGURADORA", "Aseguradora: ");
		camposMap.put("05PRODUCTO", "Producto: ");
		camposMap.put("06MODULO", "Módulo: ");
		//camposMap.put("06MODULO", "Cliente: ");
		camposMap.put("07CAMPANIA", "Campaña: ");
		camposMap.put("08PROCESO", "Tema: ");
		camposMap.put("09ENCUESTA", "Encuesta: ");
		camposMap.put("10NUMERO_POLIZA", "Número de Póliza: ");
		
		
		myList = new HashMap();
		/*myList.put("01NRO_CASO", numDeCaso);
		myList.put("02TIPOARCH", tipoArchivo);
		myList.put("03FECHAREGISTRO", feRegistro);
		myList.put("04FECHARECEPCION", feRecepcion);
		myList.put("05NUMPOLIZA", numPoliza);
		myList.put("06CODDESCUSUARIO", totalUsuario);
		myList.put("07DESCARCHIVO", archivoNombre);*/
		myList = configurarEncuestaManager.obtenerIngresarEncuestasEditarParaExportar(nmconfig, cdunieco, cdramo, estado, nmpoliza, cdperson, cdelemento);

		@SuppressWarnings("unused")
		HashMap<String, ItemVO> mapaAtributos = new HashMap<String, ItemVO>();
		
		try {
			mapaAtributos = configurarEncuestaManager.obtenerIngresarEncuestasEditarVariablesParaExportar(nmconfig, cdunieco, cdramo, estado, nmpoliza, cdperson);
			
		} catch (ApplicationException ae) {
				mapaAtributos = null;
			}
		
		formarDocumento(camposMap, myList, mapaAtributos,  document);
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
	private void formarDocumento (TreeMap labelsEncabezado, Map datosEncabezado, HashMap<String ,ItemVO> datosAtributosVariables, Document documento) throws Exception {
		//Comienza recorrido de datos comunes al Fax
		Iterator iterator = labelsEncabezado.keySet().iterator();
		while (iterator.hasNext()) {
			String keyName = (String)iterator.next();
			StringBuffer sb = new StringBuffer();
			sb.append(labelsEncabezado.get(keyName)).append(" ").append(datosEncabezado.get(keyName));
			Chunk chunk = new Chunk(sb.toString());
			documento.add(new Paragraph(chunk));
		}
		//Comienza bucle para recorrer atributos variables
		if (datosAtributosVariables != null) {
			//String seccionActual = "";
			//ArrayList<String> secciones = obtenerListaSecciones(datosAtributosVariables);
		//	if (secciones != null) {
				//int i = 0;
				//while (i < secciones.size()) {
					iterator = datosAtributosVariables.keySet().iterator();
					//Agrega la sección
				//	seccionActual = secciones.get(i);
					//Chunk chunkSeccion = new Chunk(seccionActual);
					//chunkSeccion.setUnderline(+1f, -2f);
					//documento.add(new Paragraph(chunkSeccion));
					for (int i = 0; i < 2; i++) {
						StringBuffer sb = new StringBuffer();
						sb.append(" ");
						Chunk chunk = new Chunk(sb.toString());
						documento.add(new Paragraph(chunk));
							
					}
					
					
					while (iterator.hasNext()) {
						String keyName = (String)iterator.next();
						ItemVO itemVO = new ItemVO();
						itemVO = (ItemVO)datosAtributosVariables.get(keyName);
						
						
						//Muestra los atributos de acuerdo con la sección
						if (itemVO.getId() != null /*&& itemVO.getModulo().equals(secciones.get(i))*/) {
							//String value = (itemVO.getTexto() == null)?"" : itemVO.getTexto();
							//String[] claveAtributo = keyName.split("_");
							//String valorAtributo = obtieneValorAtributo(claveAtributo[1], claveAtributo[2], claveAtributo[3]/*,datosAtributosVariables*/);
							StringBuffer sb = new StringBuffer();
							sb.append(itemVO.getId()).append(": ").append(itemVO.getTexto());
							Chunk chunk1 = new Chunk(sb.toString());
							documento.add(new Paragraph(chunk1));
						}
					}
					//i++;
			//	}
			//}
		}
		//Comienza bucle para mostrar usuarios
		/*Chunk chunkUsuarios = new Chunk("Usuarios Responsables");
		chunkUsuarios.setUnderline(+1f, -2f);
		documento.add(new Paragraph(chunkUsuarios));*/

		/*Font SMALL_FONT = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new Color(50, 50, 50));
		
		documento.add(new Paragraph(new Chunk("  ")));
		PdfPTable table = new PdfPTable(3);
		int i = 0;
		Paragraph paragraph = new Paragraph("Código", PDFServices.getFormat("helvetica", 10.5f, Font.BOLD));
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(206,115,131));
		cell.addElement(paragraph);
		table.addCell(cell);*/
		
		/*paragraph = new Paragraph("Nombre", PDFServices.getFormat("helvetica", 10.5f, Font.BOLD));
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(206,115,131));
		cell.addElement(paragraph);
		table.addCell(cell);*/

		/*paragraph = new Paragraph("Rol", PDFServices.getFormat("helvetica", 10.5f, Font.BOLD));
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(206,115,131));
		cell.addElement(paragraph);
		table.addCell(cell);*/

	/*	while (i < listaUsuarios.size()) {
			CasoVO casoVO = (CasoVO)listaUsuarios.get(i);
			table.addCell(casoVO.getCdUsuario());
			table.addCell(casoVO.getDesUsuario());
			table.addCell(casoVO.getDesRolmat());
			i++;
		}*/
		//documento.add(table);
	}
	
	

	public void setConfigurarEncuestaManager(
			ConfigurarEncuestaManager configurarEncuestaManager) {
		this.configurarEncuestaManager = configurarEncuestaManager;
	}

	

	public List<ConfigurarEncuestaVO> getMListExportarEncuestasEditar() {
		return mListExportarEncuestasEditar;
	}

	public void setMListExportarEncuestasEditar(
			List<ConfigurarEncuestaVO> listExportarEncuestasEditar) {
		mListExportarEncuestasEditar = listExportarEncuestasEditar;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public ExportMediator getExportMediator() {
		return exportMediator;
	}

	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}
	
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Map getMyList() {
		return myList;
	}

	public void setMyList(Map myList) {
		this.myList = myList;
	}

	

	/*public Map getSession() {
		return session;
	}*/

	public void setSession(Map session) {
		this.session = session;
	}

	public String getNmconfig() {
		return nmconfig;
	}

	public void setNmconfig(String nmconfig) {
		this.nmconfig = nmconfig;
	}

	public String getCdunieco() {
		return cdunieco;
	}

	public void setCdunieco(String cdunieco) {
		this.cdunieco = cdunieco;
	}

	public String getCdramo() {
		return cdramo;
	}

	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNmpoliza() {
		return nmpoliza;
	}

	public void setNmpoliza(String nmpoliza) {
		this.nmpoliza = nmpoliza;
	}

	public String getCdperson() {
		return cdperson;
	}

	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}

	public String getCdelemento() {
		return cdelemento;
	}

	public void setCdelemento(String cdelemento) {
		this.cdelemento = cdelemento;
	}

	
	
}
