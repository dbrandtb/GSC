package mx.com.gseguros.portal.siniestros.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.portal.siniestros.model.AutorizaServiciosVO;
import mx.com.gseguros.portal.siniestros.model.AutorizacionServicioVO;
import mx.com.gseguros.portal.siniestros.model.CoberturaPolizaVO;
import mx.com.gseguros.portal.siniestros.model.DatosSiniestroVO;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class SiniestrosAction extends ActionSupport{
    
    private static final long serialVersionUID = -6321288906841302337L;
	private Logger logger = Logger.getLogger(SiniestrosAction.class);	
	private boolean success;
    private SiniestrosManager siniestrosManager;
    private HashMap<String,String> params;
    private AutorizacionServicioVO datosAutorizacionEsp;
    private AutorizacionServicioVO numeroAutorizacion;
    private List<GenericVO> listaAsegurado;
    private List<GenericVO> listaProvMedico;
    private List<GenericVO> listaCausaSiniestro;
    private List<AutorizaServiciosVO> listaAutorizacion;
    private List<CoberturaPolizaVO> listaCoberturaPoliza;
    private List<DatosSiniestroVO> listaDatosSiniestro;
    private List<GenericVO> listaSubcobertura;
    private List<GenericVO> listaCPTICD;
    
    private boolean esHospitalario;
    private HashMap<String, String> loadForm;
    private List<HashMap<String, String>> loadList;
    
    public String execute() throws Exception {
    	return SUCCESS;
    }
    
    /**
     * Función que realiza la busqueda de la consulta de Autorización de servicio en especifico
     * @param String nmautser
     * @return String autorización de Servicio
     */
    public String consultaAutorizacionServicio(){
    		logger.debug(" **** Entrando a Consulta de Autorización de Servicio en Especifico****");
    		try {
    				List<AutorizacionServicioVO> lista = siniestrosManager.getConsultaAutorizacionesEsp(params.get("nmautser"));
    				if(lista!=null && !lista.isEmpty())	datosAutorizacionEsp = lista.get(0);
    		}catch( Exception e){
    			logger.error("Error al obtener los datos de Autorización de Servicio en Especifico",e);
            return SUCCESS;
        }
        success = true;
        return SUCCESS;
    }

    public String entradaCalculos(){
    	
    	
    	esHospitalario = false;
    	loadForm =  new HashMap<String, String>();
    	
    	if(esHospitalario){
        	loadForm.put("asegurado", "Manuel");
        	loadForm.put("deducible", "5");
        	loadForm.put("copago", "54");
    	}else{
    		loadForm.put("proveedor","Nombre Proveedor");
    		loadForm.put("isrProveedor","Isr");
    		loadForm.put("impuestoCedular","Imp ced");
    		loadForm.put("iva","17.5");
    	}
    	
    	success = true;
    	return SUCCESS;
    }
    
    public String loadListaCalculos(){
    	loadList = new ArrayList<HashMap<String, String>>();
    	HashMap<String, String> elements =  new HashMap<String, String>();
		try {
			elements.put("cpt", "1");
			elements.put("cantidad", "1111");
			elements.put("arancel", "11111111");
			elements.put("subtotalArancel", "11");
			elements.put("descuento", "111111111");
			elements.put("subtotalDescuento", "11111111111");
			elements.put("porcentajeCopago", "11111111");
			elements.put("copago", "11111111111");
			elements.put("copagoAplicado", "1111111111111");
			elements.put("subtotal", "1111111");
			elements.put("isr", "11111111");
			elements.put("cedular", "111");
			elements.put("subtotalImpuestos", "1111");
			elements.put("iva", "1111");
			elements.put("total", "11111");
			elements.put("facturado", "11111");
			elements.put("autorizado", "11111");
			elements.put("valorUtilizar", "11111111111");
			
			loadList.add(elements);
			
			elements =  new HashMap<String, String>();
			elements.put("cpt", "2");
			elements.put("cantidad", "222222");
			elements.put("arancel", "22222222");
			elements.put("subtotalArancel", "22");
			elements.put("descuento", "2222222");
			elements.put("subtotalDescuento", "2222222222");
			elements.put("porcentajeCopago", "222222222");
			elements.put("copago", "2222222222");
			elements.put("copagoAplicado", "222222222222");
			elements.put("subtotal", "222222222222");
			elements.put("isr", "22222222222");
			elements.put("cedular", "222");
			elements.put("subtotalImpuestos", "2222");
			elements.put("iva", "2222");
			elements.put("total", "22222");
			elements.put("facturado", "2222");
			elements.put("autorizado", "22222");
			elements.put("valorUtilizar", "2222222");
			
			loadList.add(elements);
			//List<AutorizacionServicioVO> lista = siniestrosManager.getConsultaAutorizacionesEsp(params.get("nmautser"));
			//if(lista!=null && !lista.isEmpty())	datosAutorizacionEsp = lista.get(0);
		}catch( Exception e){
			logger.error("Error al obtener los datos de Autorización de Servicio en Especifico",e);
			success =  false;
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
    }
    
    /**
     * Función que obtiene la lista del asegurado
     * @param void sin parametros de entrada
     * @return Lista GenericVO con la información de los asegurados
     */    
    public String consultaListaAsegurado(){
    	logger.debug(" **** Entrando al método de Lista de Asegurado ****");
	   	try {
	   		listaAsegurado= siniestrosManager.getConsultaListaAsegurado(params.get("cdperson"));
	   	}catch( Exception e){
	   		logger.error("Error al consultar la Lista de los asegurados ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
   }
	/**
	 * Función que obtiene el listado de  de Autorización de Servicio
	 * @param String cdperson
	 * @return Lista AutorizaServiciosVO con la información de los asegurados
	 */
	public String consultaListaAutorizacion(){
		logger.debug(" **** Entrando a consulta de lista de Autorización por CDPERSON ****");
		try {
				
				List<AutorizaServiciosVO> lista = siniestrosManager.getConsultaListaAutorizaciones(params.get("tipoAut"),params.get("cdperson"));
				if(lista!=null && !lista.isEmpty())	listaAutorizacion = lista;
		}catch( Exception e){
			logger.error("Error al obtener los datos de la poliza ",e);
			return SUCCESS;
		}
	success = true;
	return SUCCESS;
    }
    
     /**
     * Función que obtiene la lista del Medico y proveedor por medio del identificador
     * @param String sin parametros de entrada
     * @return Lista GenericVO con la información de los asegurados
     */    
    public String consultaListaProvMedico(){
    	logger.debug(" **** Entrando al método de Lista de Asegurado ****");
	   	try {
	   		listaProvMedico= siniestrosManager.getConsultaListaProveedorMedico(params.get("tipoprov"),params.get("cdpresta"));
	   	}catch( Exception e){
	   		logger.error("Error al consultar la Lista de los asegurados ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;   	
   }
    
    /**
     * Función que obtiene la lista del asegurado
     * @param void sin parametros de entrada
     * @return Lista GenericVO con la información de los asegurados
     */    
    public String consultaListaCausuaSiniestro(){
    	logger.debug(" **** Entrando al método de Lista de Asegurado ****");
	   	try {
	   		listaCausaSiniestro= siniestrosManager.getConsultaListaCausaSiniestro(params.get("cdcausa"));
	   	}catch( Exception e){
	   		logger.error("Error al consultar la Lista de los asegurados ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
   }
    
    /**
	 * Función que obtiene el listado de  de Autorización de Servicio
	 * @param String cdperson
	 * @return Lista AutorizaServiciosVO con la información de los asegurados
	 */
	public String consultaListaCoberturaPoliza(){
		logger.debug(" **** Entrando a consulta de lista de Cobertura de poliza ****");
		try {
			HashMap<String, Object> paramCobertura = new HashMap<String, Object>();
			paramCobertura.put("pv_cdunieco_i",params.get("cdunieco"));
			paramCobertura.put("pv_estado_i",params.get("estado"));
			paramCobertura.put("pv_cdramo_i",params.get("cdramo"));
			paramCobertura.put("pv_nmpoliza_i",params.get("nmpoliza"));
			paramCobertura.put("pv_nmsituac_i",params.get("nmsituac"));
			paramCobertura.put("pv_cdgarant_i",params.get("cdgarant"));
			
			List<CoberturaPolizaVO> lista = siniestrosManager.getConsultaListaCoberturaPoliza(paramCobertura);
			if(lista!=null && !lista.isEmpty())	listaCoberturaPoliza = lista;
		}catch( Exception e){
			logger.error("Error al obtener los datos de la poliza ",e);
			return SUCCESS;
		}
	success = true;
	return SUCCESS;
    }
	
	/**
	 * Función que obtiene el listado de  de Autorización de Servicio
	 * @param String cdperson
	 * @return Lista AutorizaServiciosVO con la información de los asegurados
	 */
	public String consultaListaDatSubGeneral(){
		logger.debug(" **** Entrando a consulta de lista de Cobertura de poliza ****");
		try {
			HashMap<String, Object> paramDatSubGral = new HashMap<String, Object>();
			paramDatSubGral.put("pv_cdunieco_i",params.get("cdunieco"));
			paramDatSubGral.put("pv_estado_i",params.get("estado"));
			paramDatSubGral.put("pv_cdramo_i",params.get("cdramo"));
			paramDatSubGral.put("pv_nmpoliza_i",params.get("nmpoliza"));
			paramDatSubGral.put("pv_nmsituac_i",params.get("nmsituac"));
			paramDatSubGral.put("pv_cdgarant_i",params.get("cdgarant"));
			paramDatSubGral.put("pv_subcober_i",params.get("subcober"));
			
			List<DatosSiniestroVO> lista = siniestrosManager.getConsultaListaDatSubGeneral(paramDatSubGral);
			if(lista!=null && !lista.isEmpty())	listaDatosSiniestro = lista;
		}catch( Exception e){
			logger.error("Error al obtener los datos de la poliza ",e);
			return SUCCESS;
		}
	success = true;
	return SUCCESS;
    }
    
    /**
    * Función que obtiene la lista del Medico y proveedor por medio del identificador
    * @param String sin parametros de entrada
    * @return Lista GenericVO con la información de los asegurados
    */    
   public String consultaListaSubcobertura(){
   	logger.debug(" **** Entrando al método de Lista de Subcobertura ****");
	   	try {
	   		listaSubcobertura= siniestrosManager.getConsultaListaSubcobertura(params.get("cdgarant"),params.get("cdsubcob"));
	   	}catch( Exception e){
	   		logger.error("Error al consultar la Lista de subcoberturas ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;   	
  }
    
   /**
   * Función que obtiene la lista del Medico y proveedor por medio del identificador
   * @param String sin parametros de entrada
   * @return Lista GenericVO con la información de los asegurados
   */    
  public String consultaListaCPTICD(){
  	logger.debug(" **** Entrando al método de Lista de Subcobertura ****");
	   	try {	   		
	   		listaCPTICD= siniestrosManager.getConsultaListaCPTICD(params.get("cdtabla"),params.get("otclave"));
	   	}catch( Exception e){
	   		logger.error("Error al consultar la Lista de subcoberturas ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;   	
 }
   
    
    
    public List<GenericVO> getListaCPTICD() {
    	return listaCPTICD;
	}
	
	public void setListaCPTICD(List<GenericVO> listaCPTICD) {
		this.listaCPTICD = listaCPTICD;
	}

	public List<GenericVO> getListaSubcobertura() {
		return listaSubcobertura;
	}

	public void setListaSubcobertura(List<GenericVO> listaSubcobertura) {
		this.listaSubcobertura = listaSubcobertura;
	}

	public List<DatosSiniestroVO> getListaDatosSiniestro() {
		return listaDatosSiniestro;
	}

	public void setListaDatosSiniestro(List<DatosSiniestroVO> listaDatosSiniestro) {
		this.listaDatosSiniestro = listaDatosSiniestro;
	}

	public List<CoberturaPolizaVO> getListaCoberturaPoliza() {
		return listaCoberturaPoliza;
	}

	public void setListaCoberturaPoliza(List<CoberturaPolizaVO> listaCoberturaPoliza) {
		this.listaCoberturaPoliza = listaCoberturaPoliza;
	}

	public List<GenericVO> getListaCausaSiniestro() {
		return listaCausaSiniestro;
	}

	public void setListaCausaSiniestro(List<GenericVO> listaCausaSiniestro) {
		this.listaCausaSiniestro = listaCausaSiniestro;
	}

	public List<GenericVO> getListaProvMedico() {
		return listaProvMedico;
	}

	public void setListaProvMedico(List<GenericVO> listaProvMedico) {
		this.listaProvMedico = listaProvMedico;
	}

	
	
	
	/*	GETTER 	*/
	public boolean isSuccess() {
		return success;
	}
	
	public SiniestrosManager getSiniestrosManager() {
		return siniestrosManager;
	}

	public HashMap<String, String> getParams() {
		return params;
	}
    public AutorizacionServicioVO getDatosAutorizacionEsp() {
		return datosAutorizacionEsp;
	}
    
    public List<GenericVO> getListaAsegurado() {
		return listaAsegurado;
	}
    
    public List<AutorizaServiciosVO> getListaAutorizacion() {
		return listaAutorizacion;
	}

    
    /*	SETTER 	*/    
    public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setSiniestrosManager(SiniestrosManager siniestrosManager) {
		this.siniestrosManager = siniestrosManager;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}
	
	public void setDatosAutorizacionEsp(AutorizacionServicioVO datosAutorizacionEsp) {
		this.datosAutorizacionEsp = datosAutorizacionEsp;
	}

	public void setListaAsegurado(List<GenericVO> listaAsegurado) {
		this.listaAsegurado = listaAsegurado;
	}

	public void setListaAutorizacion(List<AutorizaServiciosVO> listaAutorizacion) {
		this.listaAutorizacion = listaAutorizacion;
	}
	
	
	
	
	
	
	
	
	
	
	
	/* ############################################################################## 
	 * ##################################### VERIFICAR ##############################*/
	
	public String guardaAutorizacionServicio(){
		logger.debug(" **** Entrando a guardado de Autorización de Servicio ****");
		try {
				// Recibirá una lista con los valores y de ahi guardarlos en forma de mapas
				HashMap<String, Object> paramsR = new HashMap<String, Object>();
				paramsR.put("pv_nmautser_i",params.get("nmautser"));
				paramsR.put("pv_nmautant_i",params.get("nmautant"));
				paramsR.put("pv_cdperson_i",params.get("cdperson"));
				paramsR.put("pv_fesolici_i",params.get("fesolici"));
				paramsR.put("pv_feautori_i",params.get("feautori"));
				paramsR.put("pv_fevencim_i",params.get("fevencim"));
				paramsR.put("pv_feingres_i",params.get("feingres"));
				paramsR.put("pv_cdunieco_i",params.get("cdunieco"));
				paramsR.put("pv_estado_i",params.get("estado"));
				paramsR.put("pv_cdramo_i",params.get("cdramo"));
				paramsR.put("pv_nmpoliza_i",params.get("nmpoliza"));
				paramsR.put("pv_nmsituac_i",params.get("nmsituac"));
				paramsR.put("pv_cduniecs_i",params.get("cduniecs"));
				paramsR.put("pv_cdgarant_i",params.get("cdgarant"));
				paramsR.put("pv_cdconval_i",params.get("cdconval"));
				paramsR.put("pv_cdprovee_i",params.get("cdprovee"));
				paramsR.put("pv_cdmedico_i",params.get("cdmedico"));
				paramsR.put("pv_mtsumadp_i",params.get("mtsumadp"));
				paramsR.put("pv_porpenal_i",params.get("porpenal"));
				paramsR.put("pv_cdicd_i",params.get("cdicd"));
				paramsR.put("pv_cdcausa_i",params.get("cdcausa"));
				paramsR.put("pv_aaapertu_i",params.get("aaapertu"));
				paramsR.put("pv_status_i",params.get("status"));
				paramsR.put("pv_dstratam_i",params.get("dstratam"));
				paramsR.put("pv_dsobserv_i",params.get("dsobserv"));
				paramsR.put("pv_dsnotas_i",params.get("dsnotas"));
				paramsR.put("pv_fesistem_i",params.get("fesistem"));
				paramsR.put("pv_cduser_i",params.get("cduser"));
				
				List<AutorizacionServicioVO> lista = siniestrosManager.guardarAutorizacionServicio(paramsR);
				if(lista!=null && !lista.isEmpty())	numeroAutorizacion = lista.get(0);
					logger.debug("Resultado de la respuesta del llamado al guardado:" + lista);
		}catch( Exception e){
			logger.error("Error al guardado",e);
        return SUCCESS;
    }
    
    success = true;
    return SUCCESS;
}
    
	public AutorizacionServicioVO getNumeroAutorizacion() {
		return numeroAutorizacion;
	}

	public void setNumeroAutorizacion(AutorizacionServicioVO numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}

	public HashMap<String, String> getLoadForm() {
		return loadForm;
	}

	public void setLoadForm(HashMap<String, String> loadForm) {
		this.loadForm = loadForm;
	}

	public List<HashMap<String, String>> getLoadList() {
		return loadList;
	}

	public void setLoadList(List<HashMap<String, String>> loadList) {
		this.loadList = loadList;
	}

	public boolean isEsHospitalario() {
		return esHospitalario;
	}

	public void setEsHospitalario(boolean esHospitalario) {
		this.esHospitalario = esHospitalario;
	}




}