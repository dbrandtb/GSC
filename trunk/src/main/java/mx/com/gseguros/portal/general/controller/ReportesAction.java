package mx.com.gseguros.portal.general.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.model.ParamReporteVO;
import mx.com.gseguros.portal.general.model.ReporteVO;
import mx.com.gseguros.portal.general.service.ReportesManager;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.utils.Constantes;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class ReportesAction extends PrincipalCoreAction {

	private static final long serialVersionUID = 5866297387639852014L;

	private static Logger logger = Logger.getLogger(ReportesAction.class);

	private ReportesManager reportesManager;
	
	private List<ReporteVO> reportes;
	
	private List<ParamReporteVO> paramsReporte;
	
	private Map<String, String> params;
	
	private InputStream fileInputStream;
	
	private String filename;
	
	protected String contentType;

	protected boolean success;
	
	private String cdreporte;
	
	
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	
	public String obtenerListaReportes() throws Exception {
		reportes = reportesManager.obtenerListaReportes();
		success = true;
		return SUCCESS;
	}
	
	
	public String obtenerComponentesReporte() throws Exception {
		
		// Obtenemos los parametros del reporte solicitado:
		paramsReporte = reportesManager.obtenerParametrosReportes(cdreporte);
		
		// Generamos los campos/items para presentarlos:
		GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
		List<ComponenteVO> lstCmp = new ArrayList<ComponenteVO>();
		
		for(ParamReporteVO paramRep : paramsReporte) {
			ComponenteVO cmp = new ComponenteVO();
			cmp.setType(ComponenteVO.TIPO_GENERICO);
			cmp.setLabel(paramRep.getDescripcion());
			cmp.setTipoCampo(paramRep.getTipo());
			// Se asigna el valor por default si lo tiene:
			cmp.setValue(org.springframework.util.StringUtils.quote(paramRep.getValor()));
			// Se asigna el nombre del campo:
			cmp.setNameCdatribu("params." + paramRep.getNombre());
			cmp.setObligatorio(paramRep.isObligatorio());
			lstCmp.add(cmp);
		}
        gc.generaComponentes(lstCmp, true, false, true, false, false, false);
        params = new HashMap<String, String>();
        params.put("items", gc.getItems().toString());
        logger.debug("items=" + params.get("items"));
		
		success = true;
		return SUCCESS;
	}
 
	
	public String procesoObtencionReporte() throws Exception {
		
		String username = ((UserVO)session.get("USUARIO")).getUser();
		
		// Se arman los parametros de entrada del reporte:
		List<ParamReporteVO> paramsReporte = new ArrayList<ParamReporteVO>();
		for(Map.Entry<String, String> entry : params.entrySet()){
		    ParamReporteVO paramReporte = new ParamReporteVO();
		    paramReporte.setNombre(entry.getKey());
		    paramReporte.setValor(entry.getValue());
		    paramsReporte.add(paramReporte);
		}
		
		try {
			contentType = "application/vnd.ms-excel";
			filename = cdreporte+"."+Constantes.FORMAT_XLS;
			fileInputStream = reportesManager.obtenerDatosReporte(cdreporte, username, paramsReporte);
		} catch (Exception e) {
			logger.error("Error en la obtención del reporte", e);
		}
		
		success = true;
		return SUCCESS;
	}
	
	
	//Getters and setters:

	public void setReportesManager(ReportesManager reportesManager) {
		this.reportesManager = reportesManager;
	}
	
	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public List<ReporteVO> getReportes() {
		return reportes;
	}

	public void setReportes(List<ReporteVO> reportes) {
		this.reportes = reportes;
	}
	
	public List<ParamReporteVO> getParamsReporte() {
		return paramsReporte;
	}

	public void setParamsReporte(List<ParamReporteVO> paramsReporte) {
		this.paramsReporte = paramsReporte;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public String getCdreporte() {
		return cdreporte;
	}

	public void setCdreporte(String cdreporte) {
		this.cdreporte = cdreporte;
	}

}