package mx.com.gseguros.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.general.procesoarchivo.ProcesadorArchivosContext;
import mx.com.gseguros.portal.general.validacionformato.CampoVO;
import mx.com.gseguros.portal.general.validacionformato.ValidadorFormatoContext;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.Utils;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
@ParentPackage(value="default")
@Namespace("/annotations")
public class TestArchivosAction extends PrincipalCoreAction {
	
	private static final long serialVersionUID = 3417469798703366675L;
	
	private static Logger logger = LoggerFactory.getLogger(TestArchivosAction.class);
	
	private Map<String, String> params;
	
	private GenericVO resultado;
	
    private String fileName;
    
    private String fileNameError;
    
    private boolean success;
	
	@Autowired
	private ValidadorFormatoContext validadorFormatoContext;
	
	@Autowired
	private ProcesadorArchivosContext procesadorArchivosContext; 
	
	@Action(value="validaArchivoExcel",
			results={@Result(name="success", type="json")}
	)
	public String validaArchivoExcel() throws Exception {
		
		try {
			//Parametros:
			Utils.validate(params, "No hay parametros");
			Utils.validate(params.get("fileName"), "El parametro 'params.fileName' no puede ser nulo");
			String fileNameIn = params.get("fileName");
			
			// Campos a validar:
			List<CampoVO> campos = new ArrayList<CampoVO>();
			campos.add(new CampoVO(CampoVO.NUMERICO, 1, 4, false));
			campos.add(new CampoVO(CampoVO.NUMERICO, 1, 2, true));
			campos.add(new CampoVO(CampoVO.ALFANUMERICO, 1, 1, true));
			campos.add(new CampoVO(CampoVO.FECHA, null, null, false));
			campos.add(new CampoVO(CampoVO.PORCENTAJE, 0, 20, true));
			
			// VALIDACION DE FORMATO:
			logger.info("Se valida el formato de los campos: " + campos);
			
			// Nombre del archivo de errores (si los hay):
			String fullNameArchErrValida = getText("ruta.documentos.temporal") + Constantes.SEPARADOR_ARCHIVO+"conversion_" + System.currentTimeMillis() + "_err.txt";
			
			File archErrVal = validadorFormatoContext.ejecutaValidacionesFormato(new File(fileNameIn), campos, fullNameArchErrValida, ValidadorFormatoContext.Strategy.VALIDACION_EXCEL);
			if(archErrVal != null && archErrVal.length() > 0) {
				String msjeError = "Archivo tiene errores de formato";
				resultado = new GenericVO("1", msjeError);
				fileNameError = archErrVal.getName();
				throw new ApplicationException(msjeError);
			}
			
			
			/*
			// PROCESAMIENTO DEL ARCHIVO:
			logger.info("Se ejecuta proceso de archivo: " + this.getText("directorio.server.layouts")+Constantes.SEPARADOR_ARCHIVO+fileFileName);
			
			mx.com.gseguros.portal.general.procesoarchivo.ProcesadorArchivosContext.Strategy estrategiaProcesaArchivo = null;
			if(tipoTabla == TipoTabla.UNA.getCodigo()) {
				estrategiaProcesaArchivo = mx.com.gseguros.portal.general.procesoarchivo.ProcesadorArchivosContext.Strategy.ARCHIVO_TABLA_1_CLAVE;
			} else if(tipoTabla == TipoTabla.CINCO.getCodigo()) {
				estrategiaProcesaArchivo = mx.com.gseguros.portal.general.procesoarchivo.ProcesadorArchivosContext.Strategy.ARCHIVO_TABLA_5_CLAVES;
			}
			RespuestaVO resp = procesadorArchivosContext.ejecutaProcesamientoArchivo(
					file,
					campos,
					Integer.parseInt(params.get("pi_nmtabla")),
					estrategiaProcesaArchivo);
			if(!resp.isSuccess()) {
				String msjeError = "Error en el guardado";
				resultado = new GenericVO("2", resp.getMensaje());
				throw new ApplicationException(msjeError);
			}
			*/
			
			logger.info("Termina proceso de archivo " + fileNameIn + " exitosamente");
			success = true;
			
		} catch (Exception e) {
			Utils.manejaExcepcion(e);
			//logger.error("Error en la carga masiva ", e);
		}
		
		return SUCCESS;
	}
	
	
	//Getters and setters:

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public GenericVO getResultado() {
		return resultado;
	}

	public void setResultado(GenericVO resultado) {
		this.resultado = resultado;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileNameError() {
		return fileNameError;
	}

	public void setFileNameError(String fileNameError) {
		this.fileNameError = fileNameError;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}