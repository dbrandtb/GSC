package mx.com.gseguros.wizard.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.general.model.RespuestaVO;
import mx.com.gseguros.portal.general.procesoarchivo.ProcesadorArchivosContext;
import mx.com.gseguros.portal.general.procesoarchivo.Tabla5ClavesProcesamientoArchivoStrategyImpl.TipoTabla;
import mx.com.gseguros.portal.general.service.CatalogosManager;
import mx.com.gseguros.portal.general.validacionformato.CampoVO;
import mx.com.gseguros.portal.general.validacionformato.ValidadorFormatoContext;
import mx.com.gseguros.utils.Constantes;

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
	private transient CatalogosManager catalogosManager; 
	
	private Map<String, String> params;
	
	private GenericVO resultado;
	
	private File file;
    private String fileFileName;
    private String fileContentType;
    
    private boolean success;
	
	@Autowired
	private ValidadorFormatoContext validadorFormatoContext;
	
	@Autowired
	private ProcesadorArchivosContext procesadorArchivosContext;
	
	
	@Action(value="cargaTablaApoyo",
	results={@Result(name="success", type="json")}
	)
	public String cargaTablaApoyo() throws Exception {
		
		try {
			logger.info(">>>>>  Parametros para la carga masiva de Tablas de Apoyo: " + params);
			
			// Tipo de Tabla (1 o 5 claves):
			Integer tipoTabla = Integer.parseInt(params.get("tipotabla"));
			
			// Formato del archivo (para tabla de 1 o 5 claves):		
			List<CampoVO> campos = new ArrayList<CampoVO>();
			
			try {
				logger.info("Se agregan campos, claves y atributos...");
				
				// Dependiendo el tipo de tabla creamos el numero de campos llave por default :
				for (int i = 0, totalClaves = tipoTabla; i < totalClaves; i++) {
					campos.add(new CampoVO(CampoVO.ALFANUMERICO, null, null, true)); 
				}
				
				//Agregamos las claves:
				List<Map<String, String>> claves = catalogosManager.obtieneClavesTablaApoyo(params);
				for (Map<String, String> cve : claves) {
					campos.set(new Integer(cve.get("NUMCLAVE"))-1, 
							new CampoVO(
									cve.get("SWFORMA1"),
									NumberUtils.createInteger(cve.get("NMLMIN1")),
									NumberUtils.createInteger(cve.get("NMLMAX1")),
									false));
				}
				
				// Agregamos 2 campos de fecha si es tipo tabla de 5 claves:
				if(tipoTabla == TipoTabla.CINCO.getCodigo()) {
					for (int i = 0, totalClaves = 2; i < totalClaves; i++) {
						campos.add(new CampoVO(CampoVO.FECHA, null, null, true));
					}
				}
				
				// Agregamos los atributos de la tabla:
				List<Map<String, String>> atributos = catalogosManager.obtieneAtributosTablaApoyo(params);
				for (Map<String, String> atr : atributos) {
					campos.add( 
							new CampoVO(
									atr.get("SWFORMAT"),
									NumberUtils.createInteger(atr.get("NMLMIN")), 
									NumberUtils.createInteger(atr.get("NMLMAX")), 
									true));
				}
				
			} catch (Exception e) {
				logger.error(e);
				throw new ApplicationException("Las claves y atributos de la tabla de apoyo no están correctamente parametrizados");
			}
			
			
			// VALIDACION DE FORMATO:
			logger.info("Se valida el formato de los campos: " + campos);
			
			String fullNameArchErrValida = getText("ruta.documentos.temporal") + Constantes.SEPARADOR_ARCHIVO+"conversion_" + System.currentTimeMillis() + "_err.txt";
			File archErrVal = validadorFormatoContext.ejecutaValidacionesFormato(file, campos, fullNameArchErrValida, mx.com.gseguros.portal.general.validacionformato.ValidadorFormatoContext.Strategy.VALIDACION_EXCEL);
			if(archErrVal != null && archErrVal.length() > 0) {
				String msjeError = "Archivo tiene errores de formato";
				resultado = new GenericVO("1", msjeError);
				fileFileName = archErrVal.getName();
				throw new ApplicationException(msjeError);
			}
			
			
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
					Integer.parseInt(params.get("tipoproceso")),
					params.get("feCierre"),
					estrategiaProcesaArchivo);
			if(!resp.isSuccess()) {
				String msjeError = "Error en el guardado";
				resultado = new GenericVO("2", resp.getMensaje());
				throw new ApplicationException(msjeError);
			}
			
			logger.info("Termina carga masiva de " + fileFileName + " exitosamente");
			success = true;
			
		} catch (ApplicationException appExc) {
			logger.error("Error en la carga masiva ", appExc);
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

	public GenericVO getResultado() {
		return resultado;
	}

	public void setResultado(GenericVO resultado) {
		this.resultado = resultado;
	}

}