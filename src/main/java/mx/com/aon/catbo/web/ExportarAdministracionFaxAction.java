package mx.com.aon.catbo.web;
import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import mx.com.aon.catbo.model.AtributosVblesVO;
import mx.com.aon.catbo.model.CalendarioVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.ExtJSAtributosFaxVO;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.model.ItemVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.catbo.service.AdministracionFaxManager;
import mx.com.aon.catbo.service.ArchivosFaxesManager;
import mx.com.aon.catbo.service.CalendarioManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.pdfgenerator.services.PDFServices;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;

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

public class ExportarAdministracionFaxAction extends ActionSupport{
	private static final long serialVersionUID = 1137878786546L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaCalendarioAction.class);
	protected boolean success;
	
	private Map myList;
	private Map session;
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient AdministracionFaxManager administracionFaxManager;
	private transient ArchivosFaxesManager archivosFaxesManager;
	
	private List<FaxesVO> mListExportarAdministracionFaxes;

	private static final String EXTENSION = "csv";
	private static final String CELL_END = ",";
	private static final String ROW_END = "\n";
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
	
	private String numDeCaso;
	private String tipoArchivo;
	private String feRegistro;
	private String feRecepcion;
	private String numPoliza;
	private String archivoNombre;
	private String dsarchivo;
	private String nmcaso;
	private String nmfax;
	private String nmpoliex;
	private String cdtipoar;
	
	private String cdUsuario;
	private String totalUsuario;
	
	
	public String showExportar () throws Exception {
		if(formato.equals("PDF")){
			showPDF();
		}else{showFormatos();};
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String showPDF () throws Exception {
		formato = "PDF";
		filename = "ADMINISTRACIO_FAX.PDF";
		contentType = "application/pdf";
		
		Document document = new Document();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		HttpServletResponse response = ServletActionContext.getResponse();
		PdfWriter.getInstance(document, outputStream);
		document.open();

		TreeMap camposMap = new TreeMap();
		camposMap.put("01NRO_CASO", "Nro. Caso: ");
		camposMap.put("02TIPOARCH", "Tipo de Archivo: ");
		camposMap.put("03NUMFAX", "Número de Fax: ");
		camposMap.put("04FECHARECEPCION", "Fecha de Recepción: ");
		camposMap.put("05NUMPOLIZA", "Póliza: ");
		camposMap.put("06CODDESCUSUARIO", "Usuario: ");
		camposMap.put("07DESCARCHIVO", "Archivo Adicionado: ");
		
		
		myList = new HashMap();
		myList = archivosFaxesManager.obtenerDetalleFaxParaExportar(tipoArchivo, numDeCaso, nmfax, numPoliza,archivoNombre);

		@SuppressWarnings("unused")
		HashMap<String, ItemVO> mapaAtributos = new HashMap<String, ItemVO>();
		
		try {
			mapaAtributos = administracionFaxManager.obtenerAtributosVariablesFaxParaExportar(cdtipoar, numDeCaso, nmfax);
			
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
		if (datosAtributosVariables != null) {
					iterator = datosAtributosVariables.keySet().iterator();
				
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
						
						if (itemVO.getId() != null /*&& itemVO.getModulo().equals(secciones.get(i))*/) {
							StringBuffer sb = new StringBuffer();
							sb.append(itemVO.getId()).append(": ").append(itemVO.getTexto());
							Chunk chunk1 = new Chunk(sb.toString());
							documento.add(new Paragraph(chunk1));
						}
					}
		
		}
		
	}
	
	
	public String getCellEnd() {
		return CELL_END;
	}
	
	public String getRowEnd() {
		return ROW_END;
	}
	
	@SuppressWarnings("unchecked")
	public String showFormatos ()throws Exception{
		
		
		List lista = new ArrayList();
		//formato = "XLS";
		//filename = "ADMINISTRACIO_FAX.CSV";
		//contentType = "application/csv";
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		
		TreeMap camposMap = new TreeMap();
		camposMap.put("01NRO_CASO", "Número Caso");
		camposMap.put("02TIPOARCH", "Tipo de Archivo");
		camposMap.put("03NUMFAX", "Número de Fax");
		camposMap.put("04FECHARECEPCION", "Fecha de Recepción");
		camposMap.put("05NUMPOLIZA", "Póliza");
		camposMap.put("06CODDESCUSUARIO", "Usuario");
		camposMap.put("07DESCARCHIVO", "Archivo Adicionado");
		
		myList = new HashMap();
		myList = archivosFaxesManager.obtenerDetalleFaxParaExportar(tipoArchivo, numDeCaso, nmfax, numPoliza,archivoNombre);
		
		HashMap<String, ItemVO> mapaAtributos = new HashMap<String, ItemVO>();
		try {
			mapaAtributos = administracionFaxManager.obtenerAtributosVariablesFaxParaExportar(cdtipoar, numDeCaso, nmfax);
			
		} catch (ApplicationException ae) {
				mapaAtributos = null;
			}
		
		
		TableModelExport model  = new TableModelExport();
		
		//StringBuffer sb = new StringBuffer();
		
		/*************************Construyo un nuevo HasMap con la parte Fija*****************************/
		Iterator iteratorFijo = camposMap.keySet().iterator();
		HashMap<String, ItemVO> mapaAtributosFijos = new HashMap<String, ItemVO>();
		
		while (iteratorFijo.hasNext()) {
			
			String key = (String)iteratorFijo.next();
			ItemVO itemVO = new ItemVO();
			//itemVO.setModulo(atributosVO.getDsSeccion());
			itemVO.setId(camposMap.get(key).toString());
			itemVO.setTexto(myList.get(key).toString());
			mapaAtributosFijos.put(key, itemVO);
			
		}
		int cantidad = mapaAtributosFijos.size()+ mapaAtributos.size();
		/**********Coloco los labels en un StringBuffer***********/
		/*Iterator iteratorFijoCompleto = mapaAtributosFijos.keySet().iterator();
		while ( iteratorFijoCompleto.hasNext()) {
			String keyName = (String)iteratorFijoCompleto.next();
			sb.append(mapaAtributosFijos.get(keyName).getId().toString());
			if( iteratorFijoCompleto.hasNext() != false || mapaAtributos.size() != 0){
				sb.append(getCellEnd());
			}
		}
		Iterator iteratorVar = mapaAtributos.keySet().iterator();
		while ( iteratorVar.hasNext()) {
			String keyName = (String)iteratorVar.next();
			sb.append(mapaAtributos.get(keyName).getId().toString());
			if( iteratorVar.hasNext() != false){
				sb.append(getCellEnd());
			}//else{sb.append(getRowEnd());}
		}
		String a[] = new String[cantidad];
		StringTokenizer tokens = new StringTokenizer(sb.toString(),",");
		while (tokens.hasMoreTokens()){
			for (int i = 0; i < a.length; i++) {
				a[i]=tokens.nextToken();
				if( i < cantidad ){a[i]= a[i];}
			}
			
		}
		
		model.setColumnName(a);*/
		int indx = 0;
		String encabezado[] = new String[cantidad];
		
		Iterator iteratorFijoCompleto = mapaAtributosFijos.keySet().iterator();
		while ( iteratorFijoCompleto.hasNext()) {
			String keyName = (String)iteratorFijoCompleto.next();
			encabezado[indx]=mapaAtributosFijos.get(keyName).getId().toString();
			indx++;
		}
		Iterator iteratorVar = mapaAtributos.keySet().iterator();
		while ( iteratorVar.hasNext()) {
			String keyName = (String)iteratorVar.next();
			encabezado[indx]=mapaAtributos.get(keyName).getId().toString();
			indx++;
		}
		
		
		model.setColumnName(encabezado);
		
		/*********Coloco los valores debajo de los labels en el StringBuffer*******/
		/*StringBuffer sbVal = new StringBuffer();
		Iterator iteratorValFij = mapaAtributosFijos.keySet().iterator();
		
		while ( iteratorValFij.hasNext()) {
			String keyName = (String)iteratorValFij.next();
			sbVal.append(" ").append(mapaAtributosFijos.get(keyName).getTexto().toString());
			if( iteratorValFij.hasNext() != false || mapaAtributos.size() != 0){
				sbVal.append(getCellEnd());
			}
			
		}
		Iterator iteratorValVar = mapaAtributos.keySet().iterator();
		while ( iteratorValVar.hasNext()) {
			String keyName = (String)iteratorValVar.next();
			sbVal.append(" ").append(mapaAtributos.get(keyName).getTexto().toString());
			if( iteratorValVar.hasNext() != false){
				sbVal.append(getCellEnd());
			}
		}
		
		
		ArrayList arreList = new ArrayList();
		
		StringTokenizer tokensDos = new StringTokenizer(sbVal.toString(),",");
		while(tokensDos.hasMoreTokens()){
			String aux;
			for (int i = 0; i < cantidad; i++) {
				aux = tokensDos.nextToken();
				if(aux.equals(""))
				{arreList.add(i, "");}
				else{
					arreList.add(i, aux);
				}
			}
		}*/
		
		int indx2 = 0;
		ArrayList cuerpo = new ArrayList();
		Iterator iteratorValFij = mapaAtributosFijos.keySet().iterator();
		
		while ( iteratorValFij.hasNext()) {
			String keyName = (String)iteratorValFij.next();
			cuerpo.add(indx2, mapaAtributosFijos.get(keyName).getTexto().toString());
			indx2++;
			
		}
		Iterator iteratorValVar = mapaAtributos.keySet().iterator();
		while ( iteratorValVar.hasNext()) {
			String keyName = (String)iteratorValVar.next();
			cuerpo.add(indx2, mapaAtributos.get(keyName).getTexto().toString());
			indx2++;
			
		}
		
		try {
			
		lista.add(cuerpo);
		model.setInformation(lista);
		contentType = Util.getContentType(formato);
		ExportView exportFormat = (ExportView)exportMediator.getView(formato);
		filename = "ADMINISTRACIO_FAX." + exportFormat.getExtension();
		model.setColumnCount(cantidad);
		inputStream = exportFormat.export(model);
		
		} catch (Exception exc) {
			logger.error("Exception en Action Export",exc);
		}
		return SUCCESS;
		
		
	}
	
	
	public void setAdministracionFaxManager(
			AdministracionFaxManager administracionFaxManager) {
		this.administracionFaxManager = administracionFaxManager;
	}

	public void setArchivosFaxesManager(ArchivosFaxesManager archivosFaxesManager) {
		this.archivosFaxesManager = archivosFaxesManager;
	}

	public List<FaxesVO> getMListExportarAdministracionFaxes() {
		return mListExportarAdministracionFaxes;
	}

	public void setMListExportarAdministracionFaxes(
			List<FaxesVO> listExportarAdministracionFaxes) {
		mListExportarAdministracionFaxes = listExportarAdministracionFaxes;
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

	public String getNumDeCaso() {
		return numDeCaso;
	}

	public void setNumDeCaso(String numDeCaso) {
		this.numDeCaso = numDeCaso;
	}

	public String getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(String tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	public String getFeRegistro() {
		return feRegistro;
	}

	public void setFeRegistro(String feRegistro) {
		this.feRegistro = feRegistro;
	}

	public String getFeRecepcion() {
		return feRecepcion;
	}

	public void setFeRecepcion(String feRecepcion) {
		this.feRecepcion = feRecepcion;
	}

	public String getNumPoliza() {
		return numPoliza;
	}

	public void setNumPoliza(String numPoliza) {
		this.numPoliza = numPoliza;
	}

	public String getArchivoNombre() {
		return archivoNombre;
	}

	public void setArchivoNombre(String archivoNombre) {
		this.archivoNombre = archivoNombre;
	}

	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public String getDsarchivo() {
		return dsarchivo;
	}

	public void setDsarchivo(String dsarchivo) {
		this.dsarchivo = dsarchivo;
	}

	public String getNmcaso() {
		return nmcaso;
	}

	public void setNmcaso(String nmcaso) {
		this.nmcaso = nmcaso;
	}

	public String getNmfax() {
		return nmfax;
	}

	public void setNmfax(String nmfax) {
		this.nmfax = nmfax;
	}

	public String getNmpoliex() {
		return nmpoliex;
	}

	public void setNmpoliex(String nmpoliex) {
		this.nmpoliex = nmpoliex;
	}

	public String getTotalUsuario() {
		return totalUsuario;
	}

	public void setTotalUsuario(String totalUsuario) {
		this.totalUsuario = totalUsuario;
	}

	public String getCdtipoar() {
		return cdtipoar;
	}

	public void setCdtipoar(String cdtipoar) {
		this.cdtipoar = cdtipoar;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
