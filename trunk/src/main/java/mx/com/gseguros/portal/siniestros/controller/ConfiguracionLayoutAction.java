package mx.com.gseguros.portal.siniestros.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.general.service.CatalogosManager;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;
import mx.com.gseguros.ws.ice2sigs.service.Ice2sigsService;

public class ConfiguracionLayoutAction extends PrincipalCoreAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1637743812712245272L;
	static final Logger logger = LoggerFactory.getLogger(ConfiguracionLayoutAction.class);
	private DateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
	private HashMap<String,String> 	params;
	private Map<String,String>      smap1;
	private String                  error;
	private String                  respuesta;
	private String                  respuestaOculta = null;
	private boolean                 exito           = false;
	private File                    censo;
	private List<Map<String,Object>> olist1;
	private SiniestrosManager 		 siniestrosManager;
	private KernelManagerSustituto kernelManagerSustituto;
	private PantallasManager       pantallasManager;
	private transient CatalogosManager catalogosManager;
	private transient Ice2sigsService ice2sigsService;
	

    


	private List<Map<String,String>> datosValidacion;
	private boolean success;

	/**
	* Funcion donde obtenemos los datos de las validaciones del siniestro
	* @param params
	* @return List<Map<String, String>> datosValidacion
	*/ 
	public String consultaDatosConfiguracionProveedor(){
		logger.debug("Entra a consultaDatosProveedorSiniestro params de entrada :{} ",params);
		try {
			datosValidacion = siniestrosManager.getConsultaConfiguracionProveedor(params.get("cdpresta"));
			logger.debug("Respuesta datosValidacion : {}",datosValidacion);
		}catch( Exception e){
			logger.error("Error al obtener consultaDatosProveedorSiniestro : {}", e.getMessage(), e);
			return SUCCESS;
		}
		setSuccess(true);
		return SUCCESS;
	}
	
	public String  guardarConfiguracionProveedor(){
		logger.debug("Entra a guardarConfiguracionProveedor params de entrada :{}",params);
		try {
			Date   fechaProcesamiento = new Date();
			this.session=ActionContext.getContext().getSession();
			UserVO usuario=(UserVO) session.get("USUARIO");
			
			
			String respuesta = siniestrosManager.guardaConfiguracionProveedor(params.get("cmbProveedorMod"),params.get("tipoLayout"),params.get("idaplicaIVA"),
					params.get("secuenciaIVA"),params.get("idaplicaIVARET"),usuario.getUser(), fechaProcesamiento, params.get("proceso"));
		}catch( Exception e){
			logger.error("Error al obtener el monto del arancel : {}", e.getMessage(), e);
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}
	
	
	public Map<String, String> getSmap1() {
		return smap1;
	}
	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public String getRespuestaOculta() {
		return respuestaOculta;
	}
	public void setRespuestaOculta(String respuestaOculta) {
		this.respuestaOculta = respuestaOculta;
	}
	public boolean isExito() {
		return exito;
	}
	public void setExito(boolean exito) {
		this.exito = exito;
	}
	public File getCenso() {
		return censo;
	}
	public void setCenso(File censo) {
		this.censo = censo;
	}
	public List<Map<String, Object>> getOlist1() {
		return olist1;
	}
	public void setOlist1(List<Map<String, Object>> olist1) {
		this.olist1 = olist1;
	}
	public List<Map<String, String>> getDatosValidacion() {
		return datosValidacion;
	}
	
	public void setDatosValidacion(List<Map<String, String>> datosValidacion) {
		this.datosValidacion = datosValidacion;
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}

	public SiniestrosManager getSiniestrosManager() {
		return siniestrosManager;
	}


	public void setSiniestrosManager(SiniestrosManager siniestrosManager) {
		this.siniestrosManager = siniestrosManager;
	}


	public HashMap<String, String> getParams() {
		return params;
	}


	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}


	public KernelManagerSustituto getKernelManagerSustituto() {
		return kernelManagerSustituto;
	}


	public void setKernelManagerSustituto(KernelManagerSustituto kernelManagerSustituto) {
		this.kernelManagerSustituto = kernelManagerSustituto;
	}


	public PantallasManager getPantallasManager() {
		return pantallasManager;
	}


	public void setPantallasManager(PantallasManager pantallasManager) {
		this.pantallasManager = pantallasManager;
	}


	public CatalogosManager getCatalogosManager() {
		return catalogosManager;
	}


	public void setCatalogosManager(CatalogosManager catalogosManager) {
		this.catalogosManager = catalogosManager;
	}


	public Ice2sigsService getIce2sigsService() {
		return ice2sigsService;
	}


	public void setIce2sigsService(Ice2sigsService ice2sigsService) {
		this.ice2sigsService = ice2sigsService;
	}
}
