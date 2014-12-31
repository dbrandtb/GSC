package mx.com.gseguros.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
//import mx.com.gseguros.portal.general.model.CampoVO;
//import mx.com.gseguros.portal.general.model.FormatoArchivoCSV;
import mx.com.gseguros.portal.general.service.CatalogosManager;
import mx.com.gseguros.utils.FTPSUtils;
//import mx.com.gseguros.portal.general.service.impl.ExcelValidacionesFormatoStrategyImpl;
//import mx.com.gseguros.utils.ValidadorFormatoContext;
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
	
	private File file;
    private String fileFileName;
    private String fileContentType;
    
    private boolean success;
	
//	@Autowired
//	private ValidadorFormatoContext validadorFormatoContext;
	
	
	@Action(value="invocaCargaMasiva",
	results={@Result(name="success", type="json")}
	)
	public String invocaCargaMasiva() throws Exception {
		
		logger.debug(">>>>>  Parametros para la carga masiva de Tablas de Apoyo: " + params);
		
		Integer tipoTabla = Integer.parseInt(params.get("tipotabla"));//5;//Tabla de 1 o 5 claves
		
		try{
        	success = FTPSUtils.upload(
        			this.getText("dominio.server.layouts"), 
        			this.getText("user.server.layouts"), 
        			this.getText("pass.server.layouts"), 
        			file.getAbsolutePath(),
        			this.getText("directorio.server.layouts")+"/"+fileFileName);
        	
        	if(!success) {
        		mensaje = "Error al subir archivo.";
        		return SUCCESS;
        	}
        }catch(Exception ex) {
        	logger.error("Error al subir el archivo al servidor " + this.getText("dominio.server.layouts"), ex);
        	mensaje = "Error al subir archivo.";
        	success= false;
        	return SUCCESS;
        }
		
		// Archivo a validar:
		//String ruta = params.get("ruta");//"C:\\Users\\Ricardo\\Desktop\\Tablas de apoyo\\3737_ok.xlsx";
		//File archivo = new File(ruta);
		
		
		logger.debug("Fin del proceso");
		
		success = true;
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


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public String getFileFileName() {
		return fileFileName;
	}


	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}


	public String getFileContentType() {
		return fileContentType;
	}


	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}