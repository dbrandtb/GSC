package mx.com.gseguros.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.portal.general.model.CampoVO;
import mx.com.gseguros.portal.general.model.FormatoArchivoCSV;
import mx.com.gseguros.portal.general.service.CatalogosManager;
import mx.com.gseguros.portal.general.service.impl.ExcelValidacionesFormatoStrategyImpl;
import mx.com.gseguros.utils.ValidadorFormatoContext;
import mx.com.gseguros.wizard.dao.TablasApoyoDAO;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
@ParentPackage(value="default")
@Namespace("/cargamasiva")
public class CargaMasivaAction extends PrincipalCoreAction {

	private static final long serialVersionUID = -3861435458381281429L;
	
	private static Logger logger = Logger.getLogger(CargaMasivaAction.class);
	
	@Autowired
	private TablasApoyoDAO tablasApoyoDAO;
	
	
	@Autowired
	private transient CatalogosManager catalogosManager; 
	
	
	private Map<String, String> params;
	
	private String mensaje;
	
	@Autowired
	private ValidadorFormatoContext validadorFormatoContext;
	
	
	@Action(value="invocaCargaMasiva",
	results={@Result(name="success", type="json")}
	)
	public String invocaCargaMasiva() throws Exception {
		
		Integer tipoTabla = Integer.parseInt(params.get("tipotabla"));//5;//Tabla de 1 o 5 claves
		
		// Archivo a validar:
		String ruta = params.get("ruta");//"C:\\Users\\Ricardo\\Desktop\\Tablas de apoyo\\3737_ok.xlsx";
		File archivo = new File(ruta);
		
		// Formato del archivo (para tabla de 1 o 5 claves):		
		List<CampoVO> campos = new ArrayList<CampoVO>();
		for (int i = 0, totalClaves = tipoTabla; i < totalClaves; i++) {
			campos.add(new CampoVO(CampoVO.ALFANUMERICO, null, null, null, true)); 
		}
		
		logger.debug("Se obtienen claves...");
		
		logger.debug("catalogosManager:"+ catalogosManager);
		logger.debug("params:"+params);
		
		try {
			List<Map<String, String>> claves = catalogosManager.obtieneClavesTablaApoyo(params);
			for (Map<String, String> cve : claves) {
				campos.set(new Integer(cve.get("NUMCLAVE"))-1, 
						new CampoVO(
								cve.get("SWFORMA1"), 
								null, 
								NumberUtils.createInteger(cve.get("NMLMIN1")), 
								NumberUtils.createInteger(cve.get("NMLMAX1")), 
								false));
			}
		} catch (Exception e) {
			logger.error("Las claves no están correctamente parametrizadas", e);
		}
		
		FormatoArchivoCSV formatoArchivoCSV = new FormatoArchivoCSV(campos);
		//Archivo separado por un caracter
		formatoArchivoCSV.setNombreCompleto("C:\\Users\\Ricardo\\Desktop\\conversion.txt");
		formatoArchivoCSV.setSeparador("|");
		
		//logger.debug("campos:" + campos);
		logger.debug("Se validan campos de excel...");
		
		ValidadorFormatoContext validadorFormatoContext = new ValidadorFormatoContext(new ExcelValidacionesFormatoStrategyImpl());
		
		//TODO: agregar fecha como nombre de archivo
		String nombreCompletoArchivoErrores = getText("ruta.documentos.temporal") + (File.separator) + "conversion" + ".csv";
		
		//File file = validadorFormato.ejecutaValidacionesFormato(archivo, new FormatoArchivoVO(campos), nombreCompletoArchivoErrores);
		File file = validadorFormatoContext.ejecutaValidacionesFormato(archivo, formatoArchivoCSV, nombreCompletoArchivoErrores, "EXCEL");
		
		/*
		ProcesadorArchivos procArchivos = new ProcesadorArchivos(new ProcesamientoArchivoTabla5Claves("C:\\Users\\Ricardo\\Desktop\\conversion.txt", 4444, ProcesamientoArchivoTabla5Claves.TipoTabla.CINCO));
		
		File archErr2 = procArchivos.ejecutaProcesamientoArchivo(archivo);
		*/
		
		logger.debug("file:::" + file);
		if(file != null && file.length() > 0) {
			mensaje = "Archivo tiene errores";
		} else {
			mensaje = "Archivo sin errores";
		}
		
		logger.debug(mensaje);
		
		/*
		logger.debug("Archivo de errores de guardado:" + archErr2);
		if(archErr2 != null && archErr2.length() > 0) {
			logger.debug("Guardado con errores");
		} else {
			logger.debug("Guardado correcto");
		}
		*/
		
		logger.debug("Fin del proceso");
		
		return SUCCESS;
	}
	
	
	@Action(value="testCargaMasiva",
			results={@Result(name="success", type="json")}
	)
	public String testCargaMasiva() throws Exception {
		
		//String ruta = "C:\\Users\\Ricardo\\Desktop\\conversion.txt";
		String ruta = "conversion.txt";
		//File archivo = new File(ruta);
		
		tablasApoyoDAO.cargaMasiva(4444, 5, ruta, "|");
		
		return SUCCESS;
	}
	
	
	//Getters and setters:
	
	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}