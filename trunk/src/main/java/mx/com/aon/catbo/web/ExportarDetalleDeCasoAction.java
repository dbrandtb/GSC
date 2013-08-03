package mx.com.aon.catbo.web;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import mx.com.aon.catbo.model.AtributosVblesVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.ItemVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.export.views.CsvView;
import mx.com.aon.pdfgenerator.services.PDFServices;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionSupport;

public class ExportarDetalleDeCasoAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8546907284856379808L;
	@SuppressWarnings("unchecked")
	private Map myList;
	private List<AtributosVblesVO> listaValoresAtributos;
	private List<CasoVO> listaUsuariosResponsables;
	private javax.sql.DataSource dataSource;
	@SuppressWarnings("unused")
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ExportarDetalleDeCasoAction.class);
	
	private transient AdministracionCasosManager administracionCasosManager;
	private transient AdministracionCasosManager administracionCasosManager2;
	
	private String nroCaso;
	private String cdMatriz;
	
	private InputStream inputStream;
	
	private ExportMediator exportMediator;

	private String formato;
	
	private String filename;
	
	private String contentType;
	
	private static final String CELL_END = ",";
	private static final String ROW_END = "\n";

	public String showExportar () throws Exception {
		if(formato.equals("PDF")){
			showPDF();
		}else{showFormatos();};
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String showPDF () throws Exception {
		formato = "PDF";
		filename = "CASOS_BO.PDF";
		contentType = "application/pdf";

		Document document = new Document(PageSize.LETTER.rotate(), 60, 60, 40, 40);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		@SuppressWarnings("unused")
		HttpServletResponse response = ServletActionContext.getResponse();
		PdfWriter.getInstance(document, outputStream);
		
		document.open();

		HashMap camposMap = new HashMap();
		camposMap.put("NRO_CASO", "Nro. Caso: ");
		camposMap.put("DSPROCESO", "Tarea: ");
		camposMap.put("DSPRIORIDAD", "Prioridad: ");
		camposMap.put("DSNIVELATENCION", "Nivel Atención: ");
		//camposMap.put("CDUSUARIO", "Cd. Usuario: ");
		camposMap.put("DSUNIECO", "Aseguradora: ");
		camposMap.put("COLOR", "Indicador de Semáforo: ");
		camposMap.put("CDORDENTRABAJO", "Nro. Orden Trabajo: ");
		camposMap.put("TIEMPORESTANTEPARAATENDER", "Tiempo Para Atender: ");
		camposMap.put("DSMODULO", "Módulo: ");
		camposMap.put("TIEMPORESTANTEPARAESCALAR", "Tiempo Para Escalar: ");
		camposMap.put("DSSTATUS", "Status: ");
		//camposMap.put("DSUSUARIO", "Usuario: ");

		@SuppressWarnings("unused")
		HashMap<String, ItemVO> mapaAtributos = new HashMap<String, ItemVO>();
		myList = new HashMap();

		//nroCaso = "BO12008126";
		//cdMatriz = "13";
		myList = administracionCasosManager.obtenerDetalleCasoParaExportar(nroCaso, cdMatriz);

		try {
			PagedList pagedList = administracionCasosManager.obtenerListaUsuariosAExportar(nroCaso, cdMatriz, 0, 9999);
			listaUsuariosResponsables = pagedList.getItemsRangeList();
		} catch (ApplicationException ae) {
			listaUsuariosResponsables = null;
		}
		try {
		mapaAtributos = administracionCasosManager.obtenerAtributosVariablesCasoParaExportar((String)myList.get("CDFORMATOORDEN"), "");
		//PagedList pagedList = administracionCasosManager2.obtieneSeccionesOrden((String)myList.get("CDORDENTRABAJO"), 0, 9999);
		//List listaValoresAtributosVariables = pagedList.getItemsRangeList();
		} catch (ApplicationException ae) {
			mapaAtributos = null;
		}
		
		try {
			PagedList pagedList = administracionCasosManager2.obtieneSeccionesOrden((String)myList.get("CDORDENTRABAJO"), 0, 9999);
			listaValoresAtributos = pagedList.getItemsRangeList();
		} catch (ApplicationException ae) {
			listaValoresAtributos = null;
		}

		//getDataSet(camposMap, myList, mapaAtributos, designFieldMap, bandDetailList, designFieldList, 120, jasperDesign);

		/*Chunk chunk = new Chunk("Número de Caso: BO12008126");
		chunk.setUnderline(+1f, -2f);
		Chunk chunk1 = new Chunk("Generado con iText");
		chunk1.setUnderline(+4f, -2f);
		chunk1.setBackground(Color.red);
		document.add(chunk);
		document.add(new Paragraph(chunk1));*/
		formarDocumento(camposMap, myList, mapaAtributos, listaValoresAtributos, listaUsuariosResponsables, document);
		document.close();
		inputStream = new ByteArrayInputStream(outputStream.toByteArray());//new ByteArrayInputStream(document.toString().getBytes());
		//response.reset();
		contentType = Util.getContentType("PDF");
		filename = "Detalle_Caso_" + nroCaso + ".PDF";
		//response.setHeader("Content-Disposition","inline;filename=" + filename + "");
		//response.setContentType("application/pdf");
		//response.setContentLength(outputStream.size());
		//ServletOutputStream out = response.getOutputStream();
		//outputStream.writeTo(out);
		//out.flush();
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private void formarDocumento (HashMap labelsEncabezado, Map datosEncabezado, HashMap<String, ItemVO> datosAtributosVariables, List<AtributosVblesVO>listaValoresAtributos, List<CasoVO> listaUsuarios, Document documento) throws Exception {
		//Comienza recorrido de datos comunes al caso
		Iterator iterator = labelsEncabezado.keySet().iterator();
		while (iterator.hasNext()) {
			String keyName = (String)iterator.next();
			StringBuffer sb = new StringBuffer();
			sb.append(labelsEncabezado.get(keyName)).append(" ").append(datosEncabezado.get(keyName));
			Chunk chunk = new Chunk(sb.toString());
			if (keyName.toLowerCase().equals("color")) {
				sb = new StringBuffer();
				sb.append(labelsEncabezado.get(keyName)).append(" ");
				chunk = new Chunk(sb.toString());
				documento.add(chunk);

				sb = new StringBuffer();
				sb.append("     ");
				chunk = new Chunk(sb.toString());
				String color = (String)datosEncabezado.get(keyName);
				if (color != null && !color.equals("")) {
					if (color.toLowerCase().equals("red"))chunk.setBackground(Color.red);
					if (color.toLowerCase().equals("yellow")) chunk.setBackground(Color.yellow);
					if (color.toLowerCase().equals("green")) chunk.setBackground(Color.green);
				}
				documento.add(chunk);
			}else documento.add(new Paragraph(chunk));
		}
		//Comienza bucle para recorrer atributos variables
		if (datosAtributosVariables != null) {
			String seccionActual = "";
			ArrayList<String> secciones = obtenerListaSecciones(datosAtributosVariables);
			if (secciones != null) {
				int i = 0;
				while (i < secciones.size()) {
					iterator = datosAtributosVariables.keySet().iterator();
					//Agrega la sección
					seccionActual = secciones.get(i);
					Chunk chunkSeccion = new Chunk(seccionActual);
					chunkSeccion.setUnderline(+1f, -2f);
					documento.add(new Paragraph(chunkSeccion));
					while (iterator.hasNext()) {
						String keyName = (String)iterator.next();
						ItemVO itemVO = new ItemVO();
						itemVO = (ItemVO)datosAtributosVariables.get(keyName);

						//Muestra los atributos de acuerdo con la sección
						if (itemVO.getModulo() != null && itemVO.getModulo().equals(secciones.get(i))) {
							//String value = (itemVO.getTexto() == null)?"" : itemVO.getTexto();
							String[] claveAtributo = keyName.split("_");
							String valorAtributo = obtieneValorAtributo(claveAtributo[1], claveAtributo[2], claveAtributo[3], listaValoresAtributos);
							StringBuffer sb = new StringBuffer();
							String label = (itemVO.getId() != null)?itemVO.getId().trim():"";
							sb.append(label).append(": ").append(valorAtributo);
							Chunk chunk = new Chunk(sb.toString());
							documento.add(new Paragraph(chunk));
						}
					}
					i++;
				}
			}
		}
		//Comienza bucle para mostrar usuarios
		if (listaUsuarios != null) {
			Chunk chunkUsuarios = new Chunk("Usuarios Responsables");
			chunkUsuarios.setUnderline(+1f, -2f);
			documento.add(new Paragraph(chunkUsuarios));

			@SuppressWarnings("unused")
			Font SMALL_FONT = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new Color(50, 50, 50));
			
			documento.add(new Paragraph(new Chunk("  ")));
			PdfPTable table = new PdfPTable(3);
			int i = 0;
			Paragraph paragraph = new Paragraph("Código", PDFServices.getFormat("helvetica", 10.5f, Font.BOLD));
			PdfPCell cell = new PdfPCell();
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(206,115,131));
			cell.addElement(paragraph);
			table.addCell(cell);
			
			paragraph = new Paragraph("Nombre", PDFServices.getFormat("helvetica", 10.5f, Font.BOLD));
			cell = new PdfPCell();
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(206,115,131));
			cell.addElement(paragraph);
			table.addCell(cell);

			paragraph = new Paragraph("Rol", PDFServices.getFormat("helvetica", 10.5f, Font.BOLD));
			cell = new PdfPCell();
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(206,115,131));
			cell.addElement(paragraph);
			table.addCell(cell);

			while (i < listaUsuarios.size()) {
				CasoVO casoVO = (CasoVO)listaUsuarios.get(i);
				table.addCell(casoVO.getCdUsuario());
				table.addCell(casoVO.getDesUsuario());
				table.addCell(casoVO.getDesRolmat());
				i++;
			}
			documento.add(table);
		}
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<String> obtenerListaSecciones (HashMap<String, ItemVO> datosAtributosVariables) {
		ArrayList<String> secciones = new ArrayList<String>();

		Iterator iterator = datosAtributosVariables.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String)iterator.next();
			ItemVO atributosVblesVO = (ItemVO)datosAtributosVariables.get(key);
			if (!secciones.contains(atributosVblesVO.getModulo())) {
				secciones.add(atributosVblesVO.getModulo());
			}
		}
		return secciones;
	}
	private String obtieneValorAtributo (String cdFormatoOrden, String cdSeccion, String cdAtribu, List<AtributosVblesVO> listaValores) {
		int i = 0;
		if (listaValores != null) {
			while (i < listaValores.size()) {
				AtributosVblesVO atributosVblesVO = (AtributosVblesVO) listaValores.get(i);
				String key = atributosVblesVO.getCdformatoorden() + "_" + atributosVblesVO.getCdseccion() + "_" + atributosVblesVO.getCdatribu();
				String key2 = cdFormatoOrden + "_" + cdSeccion + "_" + cdAtribu;
				if (key2.equals(key)) {
					return atributosVblesVO.getOtvalor();
				}
				i++;
			}
		}
		return "";
	}

	
	/*public String showCSV ()throws Exception{
		
		File archivo = new File("CASOS_BO.CSV");
		FileWriter crear = new FileWriter(archivo); 
		

		String contenido = "algo separado por ; ";
		crear.write(contenido); 
		
		return SUCCESS;
	}*/
	
	public String getCellEnd() {
		return CELL_END;
	}
	
	public String getRowEnd() {
		return ROW_END;
	}
	
	@SuppressWarnings("unchecked")
	public String showFormatos ()throws Exception{
		
		
		List lista = new ArrayList();
		//formato = "CSV";
		//filename = "ADMINISTRACIO_FAX.CSV";
		//contentType = "application/csv";
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		
		HashMap camposMap = new HashMap();
		camposMap.put("NRO_CASO", "Número Caso");
		camposMap.put("DSPROCESO", "Tarea");
		camposMap.put("DSPRIORIDAD", "Prioridad");
		camposMap.put("DSNIVELATENCION", "Nivel Atención");
		//camposMap.put("CDUSUARIO", "Cd. Usuario: ");
		camposMap.put("DSUNIECO", "Aseguradora");
		camposMap.put("COLOR", "Indicador de Semáforo");
		camposMap.put("CDORDENTRABAJO", "Número Orden Trabajo");
		camposMap.put("TIEMPORESTANTEPARAATENDER", "Tiempo Para Atender");
		camposMap.put("DSMODULO", "Módulo");
		camposMap.put("TIEMPORESTANTEPARAESCALAR", "Tiempo Para Escalar");
		camposMap.put("DSSTATUS", "Status");
		//camposMap.put("CDFORMATOORDEN", "Codigo Formato Orden");
		
		HashMap<String, ItemVO> mapaAtributos = new HashMap<String, ItemVO>();
		myList = new HashMap();
		myList = administracionCasosManager.obtenerDetalleCasoParaExportar(nroCaso, cdMatriz);
		
		try {
			PagedList pagedList = administracionCasosManager.obtenerListaUsuariosAExportar(nroCaso, cdMatriz, 0, 9999);
			listaUsuariosResponsables = pagedList.getItemsRangeList();
		} catch (ApplicationException ae) {
			listaUsuariosResponsables = null;
		}
		
		try {
			mapaAtributos = administracionCasosManager.obtenerAtributosVariablesCasoParaExportar((String)myList.get("CDFORMATOORDEN"), "");
			//PagedList pagedList = administracionCasosManager2.obtieneSeccionesOrden((String)myList.get("CDORDENTRABAJO"), 0, 9999);
			//List listaValoresAtributosVariables = pagedList.getItemsRangeList();
			} catch (ApplicationException ae) {
				mapaAtributos = null;
			}
		
		try {
				PagedList pagedList = administracionCasosManager2.obtieneSeccionesOrden((String)myList.get("CDORDENTRABAJO"), 0, 9999);
				listaValoresAtributos = pagedList.getItemsRangeList();
			} catch (ApplicationException ae) {
				listaValoresAtributos = null;
			}
		
		
		TableModelExport model  = new TableModelExport();
		
		StringBuffer sb = new StringBuffer();
		
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
		//int cantidad = mapaAtributosFijos.size()+ mapaAtributos.size()+(listaUsuariosResponsables.size()*3);
		/**********Coloco los labels en un StringBuffer***********/
		/*Iterator iteratorFijoCompleto = mapaAtributosFijos.keySet().iterator();
		while ( iteratorFijoCompleto.hasNext()) {
			String keyName = (String)iteratorFijoCompleto.next();
			sb.append(" ").append(mapaAtributosFijos.get(keyName).getId().toString());
			if( iteratorFijoCompleto.hasNext() != false || mapaAtributos.size() != 0){
				sb.append(getCellEnd());
			}
		}
		/*Iterator iteratorVar = mapaAtributos.keySet().iterator();
		while ( iteratorVar.hasNext()) {
			String keyName = (String)iteratorVar.next();
			sb.append(mapaAtributos.get(keyName).getId().toString());
			if( iteratorVar.hasNext() != false){
				sb.append(getCellEnd());
			}else{sb.append(getRowEnd());}
		}*/
		/*if (mapaAtributos != null) {
			//StringBuffer sbLabelVar = new StringBuffer();
			String seccionActual = "";
			ArrayList<String> secciones = obtenerListaSecciones(mapaAtributos);
			if (secciones != null) {
				int i = 0;
				while (i < secciones.size()) {
					Iterator iterator = mapaAtributos.keySet().iterator();
					//Agrega la sección
					sb.append(" ").append(secciones.get(i).toString());
					cantidad = cantidad + 1; 
					if( iterator.hasNext() != false){
						sb.append(getCellEnd());
					}
					
					while (iterator.hasNext()) {
						String keyName = (String)iterator.next();
						ItemVO itemVO = new ItemVO();
						itemVO = (ItemVO)mapaAtributos.get(keyName);

						//Muestra los atributos de acuerdo con la sección
						if (itemVO.getModulo() != null && itemVO.getModulo().equals(secciones.get(i))) {
							//String value = (itemVO.getTexto() == null)?"" : itemVO.getTexto();
							String[] claveAtributo = keyName.split("_");
							String valorAtributo = obtieneValorAtributo(claveAtributo[1], claveAtributo[2], claveAtributo[3], listaValoresAtributos);
							String label = (itemVO.getId() != null)?itemVO.getId().trim():"";
							sb.append(" ").append(label);//.append(": ");//.append(valorAtributo);
							if( iterator.hasNext() != false){
								sb.append(getCellEnd());
							}	
							
						}
					
				 }
					//if(i+1 < secciones.size()){sb.append(" ").append(getCellEnd());};
					i++;
				}
			}
		}
		
		int j=0;
		if(listaUsuariosResponsables.size() > 0){
			sb.append(" , Usuarios Responsables").append(getCellEnd()); 
			cantidad = cantidad +1;
			};
		
		while (j < listaUsuariosResponsables.size()) {
			CasoVO casoVO = (CasoVO)listaUsuariosResponsables.get(j);
			sb.append("Código"+getCellEnd());
			sb.append("Nombre"+getCellEnd());
			sb.append("Rol");
			if(j+1 < listaUsuariosResponsables.size()){sb.append(getCellEnd());};
			j++;
		}
		//sb.append(getRowEnd());
		String a[] = new String[cantidad];
		StringTokenizer tokens = new StringTokenizer(sb.toString(),",");
		while (tokens.hasMoreTokens()){
			for (int i = 0; i < a.length; i++) {
				a[i]=tokens.nextToken();
				//if( i < cantidad ){a[i]= a[i]+ ",";}
			}
			
		}*/
		int cantidad = mapaAtributosFijos.size()+ mapaAtributos.size()+(listaUsuariosResponsables.size()*3)+obtenerListaSecciones(mapaAtributos).size()+1;
		int indx = 0;
		String encabezado[] = new String[cantidad];
		
		Iterator iteratorFijoCompleto = mapaAtributosFijos.keySet().iterator();
		
		while ( iteratorFijoCompleto.hasNext()) {
			String keyName = (String)iteratorFijoCompleto.next();
			encabezado[indx] = mapaAtributosFijos.get(keyName).getId().toString();
			indx++;
		}
		
		if (mapaAtributos != null) {
			String seccionActual = "";
			ArrayList<String> secciones = obtenerListaSecciones(mapaAtributos);
			if (secciones != null) {
				int i = 0;
				while (i < secciones.size()) {
					Iterator iterator = mapaAtributos.keySet().iterator();
					//Agrega la sección
					encabezado[indx]= secciones.get(i).toString();
					indx++; 
					while (iterator.hasNext()) {
						String keyName = (String)iterator.next();
						ItemVO itemVO = new ItemVO();
						itemVO = (ItemVO)mapaAtributos.get(keyName);

						//Muestra los atributos de acuerdo con la sección
						if (itemVO.getModulo() != null && itemVO.getModulo().equals(secciones.get(i))) {
							String[] claveAtributo = keyName.split("_");
							String valorAtributo = obtieneValorAtributo(claveAtributo[1], claveAtributo[2], claveAtributo[3], listaValoresAtributos);
							String label = (itemVO.getId() != null)?itemVO.getId().trim():"";
							encabezado[indx]= label;
							indx++; 
						}
					
				 }
					i++;
				}
			}
		}
		
		int j=0;
		if(listaUsuariosResponsables.size() > 0){
			encabezado[indx]= "Usuarios Responsables";
			indx++; 
			};
		
		while (j < listaUsuariosResponsables.size()) {
			CasoVO casoVO = (CasoVO)listaUsuariosResponsables.get(j);
			encabezado[indx]= "Código";
			indx++; 
			encabezado[indx]= "Nombre";
			indx++;
			encabezado[indx]= "Rol";
			indx++;
			j++;
		}
		
		model.setColumnName(encabezado);
		
		/*********Coloco los valores debajo de los labels en el StringBuffer*******/
	/*	StringBuffer sbVal = new StringBuffer();
		Iterator iteratorValFij = mapaAtributosFijos.keySet().iterator();
		int prim = 1;
		while ( iteratorValFij.hasNext()) {
			String keyName = (String)iteratorValFij.next();
			sbVal.append(" ").append(mapaAtributosFijos.get(keyName).getTexto().toString());
			if( iteratorValFij.hasNext() != false || mapaAtributos.size() != 0){
				sbVal.append(getCellEnd());
			}
			prim++;
		}
		/*Iterator iteratorValVar = mapaAtributos.keySet().iterator();
		while ( iteratorValVar.hasNext()) {
			String keyName = (String)iteratorValVar.next();
			sbVal.append(mapaAtributos.get(keyName).getTexto().toString());
			if( iteratorValVar.hasNext() != false){
				sbVal.append(getCellEnd());
			}
		}
		*/
		
		/*if (mapaAtributos != null) {
			//StringBuffer sbLabelVar = new StringBuffer();
			String seccionActual = "";
			ArrayList<String> secciones = obtenerListaSecciones(mapaAtributos);
			if (secciones != null) {
				int i = 0;
				while (i < secciones.size()) {
					Iterator iterator = mapaAtributos.keySet().iterator();
					//Agrega la sección
					sbVal.append(" ");
					
					if( iterator.hasNext() != false){
						sbVal.append(getCellEnd());
					}
					
					while (iterator.hasNext()) {
						String keyName = (String)iterator.next();
						ItemVO itemVO = new ItemVO();
						itemVO = (ItemVO)mapaAtributos.get(keyName);

						//Muestra los atributos de acuerdo con la sección
						if (itemVO.getModulo() != null && itemVO.getModulo().equals(secciones.get(i))) {
							//String value = (itemVO.getTexto() == null)?"" : itemVO.getTexto();
							String[] claveAtributo = keyName.split("_");
							String valorAtributo = obtieneValorAtributo(claveAtributo[1], claveAtributo[2], claveAtributo[3], listaValoresAtributos);
							String label = (itemVO.getId() != null)?itemVO.getId().trim():"";
							sbVal.append(" ").append(valorAtributo);//.append(": ");//.append(valorAtributo);
							if( iterator.hasNext() != false){
								sbVal.append(getCellEnd());
							}	
							
						}
					
				 }
					//if(i+1 < secciones.size()){sbVal.append(getCellEnd());};
					i++;
				}
			}
		}
		
		int k=0;
		if(listaUsuariosResponsables.size() > 0){sbVal.append(" , ").append(getCellEnd());};
		while (k < listaUsuariosResponsables.size()) {
			CasoVO casoVO = (CasoVO)listaUsuariosResponsables.get(k);
			sbVal.append(" ").append(casoVO.getCdUsuario()+ getCellEnd());
			sbVal.append(" ").append(casoVO.getDesUsuario()+ getCellEnd());
			sbVal.append(" ").append(casoVO.getDesRolmat());
			if(k+1 < listaUsuariosResponsables.size()){sbVal.append(getCellEnd());};
			k++;
		}
		//sbVal.append(getRowEnd());
		
		ArrayList  b = new ArrayList();
		StringTokenizer tokensDos = new StringTokenizer(sbVal.toString(),",");
		while (tokensDos.hasMoreTokens()){
			String aux;
			for (int i = 0; i < cantidad; i++) {
				aux = tokensDos.nextToken();
				if (aux.equals(""))
				{b.add(i,"");}else{b.add(i, aux);}
				//if( i < cantidad ){a[i]= a[i]+ ",";}
			}
			
		}*/
		
		//StringBuffer sbVal = new StringBuffer();
		int indx2 = 0;
		ArrayList cuerpo = new ArrayList();
		
		Iterator iteratorValFij = mapaAtributosFijos.keySet().iterator();
		while ( iteratorValFij.hasNext()) {
			String keyName = (String)iteratorValFij.next();
			cuerpo.add(indx2, mapaAtributosFijos.get(keyName).getTexto().toString());
			indx2++;
		}
		
		
		if (mapaAtributos != null) {
			String seccionActual = "";
			ArrayList<String> secciones = obtenerListaSecciones(mapaAtributos);
			if (secciones != null) {
				int i = 0;
				while (i < secciones.size()) {
					Iterator iterator = mapaAtributos.keySet().iterator();
					//Agrega la sección
					cuerpo.add(indx2, " ");
					indx2++;
					
					while (iterator.hasNext()) {
						String keyName = (String)iterator.next();
						ItemVO itemVO = new ItemVO();
						itemVO = (ItemVO)mapaAtributos.get(keyName);

						//Muestra los atributos de acuerdo con la sección
						if (itemVO.getModulo() != null && itemVO.getModulo().equals(secciones.get(i))) {
							//String value = (itemVO.getTexto() == null)?"" : itemVO.getTexto();
							String[] claveAtributo = keyName.split("_");
							String valorAtributo = obtieneValorAtributo(claveAtributo[1], claveAtributo[2], claveAtributo[3], listaValoresAtributos);
							cuerpo.add(indx2, valorAtributo);
							indx2++;	
							
						}
					
				 }
					
					i++;
				}
			}
		}
		
		int k=0;
		if(listaUsuariosResponsables.size() > 0){
			cuerpo.add(indx2, " ");
			indx2++;
			};
			
		while (k < listaUsuariosResponsables.size()) {
			CasoVO casoVO = (CasoVO)listaUsuariosResponsables.get(k);
			cuerpo.add(indx2, casoVO.getCdUsuario());
			indx2++;
			cuerpo.add(indx2, casoVO.getDesUsuario());
			indx2++;
			cuerpo.add(indx2, casoVO.getDesRolmat());
			indx2++;
			k++;
		}
		
		ArrayList arreList = new ArrayList();
		try {
		lista.add(0,cuerpo);
		model.setInformation(lista);
		contentType = Util.getContentType(formato);
		ExportView exportFormat = (ExportView)exportMediator.getView(formato);
		filename = "CASOS_BO." + exportFormat.getExtension();
		inputStream = exportFormat.export(model);
		
		} catch (Exception exc) {
			logger.error("Exception en Action Export",exc);
		}
		return SUCCESS;
		
		
	}

	
	public javax.sql.DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(javax.sql.DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getNroCaso() {
		return nroCaso;
	}

	public void setNroCaso(String nroCaso) {
		this.nroCaso = nroCaso;
	}

	public String getCdMatriz() {
		return cdMatriz;
	}

	public void setCdMatriz(String cdMatriz) {
		this.cdMatriz = cdMatriz;
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

	@SuppressWarnings("unchecked")
	public Map getMyList() {
		return myList;
	}

	public List<AtributosVblesVO> getListaValoresAtributos() {
		return listaValoresAtributos;
	}

	public void setAdministracionCasosManager(
			AdministracionCasosManager administracionCasosManager) {
		this.administracionCasosManager = administracionCasosManager;
	}

	public void setAdministracionCasosManager2(
			AdministracionCasosManager administracionCasosManager2) {
		this.administracionCasosManager2 = administracionCasosManager2;
	}

	/*public ExportMediator getExportMediator() {
		return exportMediator;
	}*/

	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}
	
}