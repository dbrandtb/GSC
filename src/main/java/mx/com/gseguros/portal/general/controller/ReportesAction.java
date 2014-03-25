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
	
	private Map<String,String> strMapIn;
	private Map<String,String> strMapOut;
	
	private InputStream fileInputStream;
	private String filename;
	protected String contentType;

	protected boolean success;
	
	private String cdreporte;
	
	private Integer orden;
	
	private String valor;
	
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
			logger.debug("paramReporte=" + paramRep);
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

		String username = ((UserVO)session.get("USUARIO")).getName();
		
		// Se arman los parametros de entrada del reporte:
		List<ParamReporteVO> paramsReporte = new ArrayList<ParamReporteVO>();
		for(Map.Entry<String, String> entry : params.entrySet()){
		    ParamReporteVO paramReporte = new ParamReporteVO();
		    paramReporte.setNombre(entry.getKey());
		    paramReporte.setValor(entry.getValue());
		    paramsReporte.add(paramReporte);
		}
		
		logger.debug("params=" + params);
		logger.debug("paramsReporte=" + paramsReporte);
		
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
	
	
	/**
	 * Metodo para la descarga de los archivos de los Movimientos en los casos
	 * de BO
	 * 
	 * @return
	 */
	@Deprecated
	public String obtenerReporteExcel() {
		
		logger.debug(">>>>>>>>>>>>>>  Obtiene reporte Excel <<<<<<<<<<<<<");

		contentType = "application/vnd.ms-excel";
		filename = "Prueba.xls";
		logger.debug("filename: " + filename);
		logger.debug("contentType: " + contentType);
		
		try {
		
			HashMap<String,Object> params = new HashMap<String, Object>();
			params.put("pv_idreporte_i", "Prueba");
			params.put("pv_codusr_i", "ICE");
			
			fileInputStream = reportesManager.obtenerReporteExcel(params);
			//logger.debug("Este es el InputStream: "+ fileInputStream);
			
		} catch (Exception e) {
			logger.error("Error al ejecutar obtieneReporte",e);
		}

		success = true;
		return SUCCESS;
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

	public void setReportesManager(ReportesManager reportesManager) {
		this.reportesManager = reportesManager;
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

	public Map<String, String> getStrMapIn() {
		return strMapIn;
	}

	public void setStrMapIn(Map<String, String> strMapIn) {
		this.strMapIn = strMapIn;
	}

	public Map<String, String> getStrMapOut() {
		return strMapOut;
	}


	public void setStrMapOut(Map<String, String> strMapOut) {
		this.strMapOut = strMapOut;
	}


	public String getCdreporte() {
		return cdreporte;
	}

	public void setCdreporte(String cdreporte) {
		this.cdreporte = cdreporte;
	}


	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}