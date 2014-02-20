package mx.com.gseguros.portal.siniestros.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.portal.siniestros.model.AutorizaServiciosVO;
import mx.com.gseguros.portal.siniestros.model.AutorizacionServicioVO;
import mx.com.gseguros.portal.siniestros.model.CoberturaPolizaVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaManteniVO;
import mx.com.gseguros.portal.siniestros.model.PolizaVigenteVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaPorcentajeVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaProveedorVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTDETAUTSVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTTAPVAATVO;
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
    private List<ConsultaProveedorVO> listaProvMedico;
    private List<GenericVO> listaCausaSiniestro;
    private List<AutorizaServiciosVO> listaAutorizacion;
    private List<CoberturaPolizaVO> listaCoberturaPoliza;
    private List<DatosSiniestroVO> listaDatosSiniestro;
    private List<GenericVO> listaSubcobertura;
    private List<GenericVO> listaCPTICD;
    private List<ConsultaTDETAUTSVO> listaConsultaTablas;
    private List<ConsultaTTAPVAATVO> listaConsultaTTAPVAATV;
    private List<ConsultaManteniVO> listaConsultaManteni;
    private List<ConsultaPorcentajeVO> listaPorcentaje;
    private List<HashMap<String,String>> datosTablas;
    private List<PolizaVigenteVO> listaPoliza;
    
    private boolean esHospitalario;
    private HashMap<String, String> loadForm;
    private List<HashMap<String, String>> loadList;
    private List<HashMap<String, String>> saveList;
    private List<GenericVO> listaPlazas;
    
    /**
     * Función para la visualización de las coberturas 
     * @return params con los valores para hacer las consultas
     */
	public String verCoberturas(){
		logger.debug(" **** Entrando a verCoberturas ****");
		try {
			logger.debug("params=" + params);
		}catch( Exception e){
			logger.error(e.getMessage(), e);
		}
		success = true;
		return SUCCESS;
    }
	
	/**
     * Función para la visualización de la autorizacion de servicio 
     * @return params con los valores para hacer las consultas
     */
	public String verAutorizacionServicio(){
		logger.debug(" **** Entrando a ver Autorizacion de servicio ****");
		try {
			logger.debug("params=" + params);
		}catch( Exception e){
			logger.error(e.getMessage(), e);
		}
		success = true;
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
			logger.error("Error al obtener la lista de autorizaciones ",e);
			return SUCCESS;
		}
	success = true;
	return SUCCESS;
    }
    
	/**
	 * metodo para el guardado de la autorización de Servicio
	 * @param Json con todos los valores del formulario y los grid
	 * @return Lista AutorizaServiciosVO con la información de los asegurados
	 */
	public String guardaAutorizacionServicio(){
			logger.debug(" **** Entrando a guardado de Autorización de Servicio ****");
			try {
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
					
					//ELIMINACION DE LOS REGISTROS EN LA TABLA
					siniestrosManager.getEliminacionRegistros(params.get("nmautser"));
					
					List<AutorizacionServicioVO> lista = siniestrosManager.guardarAutorizacionServicio(paramsR);
					if(lista!=null && !lista.isEmpty())
					{
						numeroAutorizacion = lista.get(0);
						for(int i=0;i<datosTablas.size();i++)
				   		{
				   			HashMap<String, Object> paramsTDeTauts = new HashMap<String, Object>();
				   			paramsTDeTauts.put("pv_nmautser_i",lista.get(0).getNmautser());
							paramsTDeTauts.put("pv_cdtipaut_i",datosTablas.get(i).get("cdtipaut"));
							paramsTDeTauts.put("pv_cdmedico_i",datosTablas.get(i).get("cdmedico"));
							paramsTDeTauts.put("pv_cdtipmed_i",datosTablas.get(i).get("cdtipmed"));
							paramsTDeTauts.put("pv_cdctp_i",datosTablas.get(i).get("cdcpt"));
							paramsTDeTauts.put("pv_precio_i",datosTablas.get(i).get("precio"));
							paramsTDeTauts.put("pv_cantporc_i",datosTablas.get(i).get("cantporc"));
							paramsTDeTauts.put("pv_ptimport_i",datosTablas.get(i).get("ptimport"));
							//GUARDADO DE LOS DATOS PARA LAS TABLAS
							siniestrosManager.guardaListaTDeTauts(paramsTDeTauts);
				   		}
						
					}
			}catch( Exception e){
				logger.error("Error al guardar la autorización de servicio ",e);
	        return SUCCESS;
	    }
	    
	    success = true;
	    return SUCCESS;
	}

	
	/**
	 * metodo para el guardado de la autorización de Servicio
	 * @param Json con todos los valores del formulario y los grid
	 * @return Lista AutorizaServiciosVO con la información de los asegurados
	 */
	public String guardaAltaTramite(){
			logger.debug(" **** Entrando a guardado de Autorización de Servicio ****");
			try {
					//VALORES DE ENTRADA
					HashMap<String, Object> paramsAtramite = new HashMap<String, Object>();
					paramsAtramite.put("pv_contrarecibo_i",params.get("txtContraRecibo"));
					paramsAtramite.put("pv_estado_i",params.get("txtEstado"));
					paramsAtramite.put("pv_oficRecep_i",params.get("cmbOficReceptora"));
					paramsAtramite.put("pv_oficEmis_i",params.get("cmbOficEmisora"));
					paramsAtramite.put("pv_feRecepc_i",params.get("dtFechaRecepcion"));
					paramsAtramite.put("pv_tipoAten_i",params.get("cmbTipoAtencion"));
					paramsAtramite.put("pv_tipoPago_i",params.get("cmbTipoPago"));
					paramsAtramite.put("pv_aseguaAf_i",params.get("cmbAseguradoAfectado"));
					paramsAtramite.put("pv_benefici_i",params.get("cmbBeneficiario"));
					paramsAtramite.put("pv_proveedo_i",params.get("cmbProveedor"));
					paramsAtramite.put("pv_nmfactur_i",params.get("txtNoFactura"));
					paramsAtramite.put("pv_importes_i",params.get("txtImporte"));
					paramsAtramite.put("pv_fechaFac_i",params.get("dtFechaFactura"));
			}catch( Exception e){
				logger.error("Error al guardar la autorización de servicio ",e);
	        return SUCCESS;
	    }
	    
	    success = true;
	    return SUCCESS;
	}
    
    /**
    * Función que obtiene la lista del Medico y proveedor por medio del identificador
    * @param tipoprov
    * @param cdpresta
    * @return Lista ConsultaProveedorVO con la información de los asegurados
    */    
   public String consultaListaProvMedico(){
   	logger.debug(" **** Entrando al método de listado para el medico y proveedor****");
	   	try {
			
			List<ConsultaProveedorVO> lista = siniestrosManager.getConsultaListaProveedorMedico(params.get("tipoprov"),params.get("cdpresta"));
			if(lista!=null && !lista.isEmpty())	listaProvMedico = lista;
		}catch( Exception e){
			logger.error("Error al obtener la lista de Proveedor o medico ",e);
			return SUCCESS;
		}
	   	
	   	success = true;
	   	return SUCCESS;
   }
    
   /**
	 * metodo que obtiene el listado de las coberturas de poliza
	 * @param maps [cdunieco,estado,cdramo,nmpoliza,nmsituac,cdgarant]
	 * @return Lista CoberturaPolizaVO con la información de los asegurados
	 */
	public String consultaListaCoberturaPoliza(){
		logger.debug(" **** Entrando a consulta de lista de Cobertura de poliza ****");
		try {
			
			logger.error("VALORES DE ENTRADA");
			logger.error(params.get("cdunieco"));
			logger.error(params.get("estado"));
			logger.error(params.get("cdramo"));
			logger.error(params.get("nmpoliza"));
			logger.error(params.get("nmsituac"));
			logger.error(params.get("cdgarant"));
			
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
			logger.error("Error al obtener la lista de la cobertura de la poliza ",e);
			return SUCCESS;
		}
	success = true;
	return SUCCESS;
   }
	
    /*
    public String consultaListaCausuaSiniestro(){
    	logger.debug(" **** Entrando al método de Causa Siniestro ****");
	   	try {
	   		listaCausaSiniestro= siniestrosManager.getConsultaListaCausaSiniestro(params.get("cdcausa"));
	   	}catch( Exception e){
	   		logger.error("Error al consultar la Lista de causa de siniestro ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
   }*/
	
	/**
	 * metodo que obtiene la información de deducible y copago
	 * @param String params [cdunieco,estado,cdramo,nmpoliza,nmsituac,cdgarant,subcober]
	 * @return Lista DatosSiniestroVO con la información de los asegurados
	 */
	public String consultaListaDatSubGeneral(){
		logger.debug(" **** Entrando a consulta de lista de subcobertura **");
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
			logger.error("Error al obtener ls datos de deducible y copago ",e);
			return SUCCESS;
		}
	success = true;
	return SUCCESS;
    }
	
    /**
    * Función que obtiene la lista de Sbcobertura
    * @param cdgarant
    * @param cdsubcob
    * @return Lista GenericVO con la información de los asegurados
    */    
   public String consultaListaSubcobertura(){
   	logger.debug(" **** Entrando al método de Lista de Subcobertura ****");
	   	try {
	   		logger.error("VALOR DE ENTRADA");
	   		logger.error(params.get("cdgarant"));
	   		logger.error(params.get("cdsubcob"));
	   		listaSubcobertura= siniestrosManager.getConsultaListaSubcobertura(params.get("cdgarant"),params.get("cdsubcob"));
	   	}catch( Exception e){
	   		logger.error("Error al consultar la Lista de subcoberturas ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;   	
  }
	
   /**
    * Función que obtiene la lista de las poliza
    * @param cdperson
    * @return Lista GenericVO con la información de los asegurados
    */ 
   public String consultaListaPoliza(){
   	logger.debug(" **** Entrando al método de Lista de Poliza ****");
   	try {
				List<PolizaVigenteVO> lista = siniestrosManager.getConsultaListaPoliza(params.get("cdperson"));
				if(lista!=null && !lista.isEmpty())	listaPoliza = lista;
			}catch( Exception e){
				logger.error("Error al obtener los datos de la poliza ",e);
				return SUCCESS;
			}
	   	success = true;
	   	return SUCCESS;
   }
	
   /**
   * Función que obtiene el listado de las subcobertura
   * @param cdtabla
   * @param otclave
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
	
   	/**
	 * Función que obtiene el listado de las listas de los grids
	 * @param String nmautser
	 * @return Lista ConsultaTDETAUTSVO con la información
	 */
	public String consultaListaTDeTauts(){
		logger.debug(" **** Entrando a consulta de lista TDETAUTS ****");
		try {
				
				List<ConsultaTDETAUTSVO> lista = siniestrosManager.getConsultaListaTDeTauts(params.get("nmautser"));
				if(lista!=null && !lista.isEmpty())	listaConsultaTablas = lista;
		}catch( Exception e){
			logger.error("Error al obtener los datos para la información de las tablas iternas",e);
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}
	
	/**
	 * Función para eliminar la listas de los grids
	 * @param String nmautser
	 * @return void
	 */
	public void borraRegistrosTabla(){
		logger.debug(" **** Entrando a eliminación de registros de tablas ****");
		try {
				siniestrosManager.getEliminacionRegistros(params.get("nmautser"));
				
		}catch( Exception e){
			logger.error("Error al eliminar los registros de la tabla",e);
		}
   }
	
	/**
	 * Función para eliminar la listas de los grids
	 * @param params [nmautser,cdtipaut,cdmedico,cdtipmed,cdcpt,precio,cantporc,ptimport]
	 * @return void
	 */
	public String guardaListaTDeTauts(){
		logger.debug(" **** Entrando a guardado de Autorización de Servicio ****");
		try {
				// Recibirá una lista con los valores y de ahi guardarlos en forma de mapas
				HashMap<String, Object> paramsTDeTauts = new HashMap<String, Object>();
				paramsTDeTauts.put("pv_nmautser_i",params.get("nmautser"));
				paramsTDeTauts.put("pv_cdtipaut_i",params.get("cdtipaut"));
				paramsTDeTauts.put("pv_cdmedico_i",params.get("cdmedico"));
				paramsTDeTauts.put("pv_cdtipmed_i",params.get("cdtipmed"));
				paramsTDeTauts.put("pv_cdctp_i",params.get("cdcpt"));
				paramsTDeTauts.put("pv_precio_i",params.get("precio"));
				paramsTDeTauts.put("pv_cantporc_i",params.get("cantporc"));
				paramsTDeTauts.put("pv_ptimport_i",params.get("ptimport"));
				
				siniestrosManager.guardaListaTDeTauts(paramsTDeTauts);
		}catch( Exception e){
			logger.error("Error al guardado de la tablas internas",e);
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
	public String consultaListaTTAPVAATVO(){
		logger.debug(" **** Entrando a consulta de lista de Cobertura de poliza ****");
		try {
			HashMap<String, Object> paramTTAPVAAT = new HashMap<String, Object>();
			paramTTAPVAAT.put("pv_cdtabla_i",params.get("cdtabla"));
			paramTTAPVAAT.put("pv_otclave1_i",params.get("otclave1"));
			paramTTAPVAAT.put("pv_otclave2_i",params.get("otclave2"));
			paramTTAPVAAT.put("pv_otclave3_i",params.get("otclave3"));
			paramTTAPVAAT.put("pv_otclave4_i",params.get("otclave4"));
			paramTTAPVAAT.put("pv_otclave5_i",params.get("otclave5"));
			List<ConsultaTTAPVAATVO> lista = siniestrosManager.getConsultaListaTTAPVAAT(paramTTAPVAAT);
			logger.debug(lista);
			if(lista!=null && !lista.isEmpty())	listaConsultaTTAPVAATV = lista;
		}catch( Exception e){
			logger.error("Error al obtener los datos de la poliza ",e);
			return SUCCESS;
		}
	success = true;
	return SUCCESS;
   }	
	
	
   public String consultaListaManteni(){
		logger.debug(" **** Entrando a consulta de lista de Mantenimiento****");
		try {
				List<ConsultaManteniVO> lista = siniestrosManager.getConsultaListaManteni(params.get("cdtabla"),params.get("codigo"));
				if(lista!=null && !lista.isEmpty())	listaConsultaManteni = lista;
		}catch( Exception e){
			logger.error("Error al obtener los datos de la poliza ",e);
			return SUCCESS;
		}
	success = true;
	return SUCCESS;
   }
   
   //ConsultaPorcentajeVO> listaPorcentaje
   public String consultaListaPorcentaje(){
		logger.debug(" **** Entrando a consulta de lista de Mantenimiento****");
		try {
				logger.debug("VALORES DE ENTRADA");
				logger.debug(params.get("cdcpt"));
				logger.debug(params.get("cdtipmed"));
				
				List<ConsultaPorcentajeVO> lista = siniestrosManager.getConsultaListaPorcentaje(params.get("cdcpt"),params.get("cdtipmed"),params.get("mtobase"));
				if(lista!=null && !lista.isEmpty())	listaPorcentaje = lista;
		}catch( Exception e){
			logger.error("Error al obtener los datos de la poliza ",e);
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

   public String loadListaDocumentos(){
   	try {
   		loadList = siniestrosManager.loadListaDocumentos(params);
   	}catch( Exception e){
   		logger.error("Error en loadListaDocumentos",e);
   		success =  false;
   		return SUCCESS;
   	}
   	success = true;
   	return SUCCESS;
   }
   
   public String guardaListaDocumentos(){
   	
   	try {
   		logger.debug("SaveList: "+ saveList);
   		siniestrosManager.guardaEstatusDocumentos(params, saveList);
   	}catch( Exception e){
   		logger.error("Error en guardaListaDocumentos",e);
   		success =  false;
   		return SUCCESS;
   	}
   	success = true;
   	return SUCCESS;
   }

   public String loadListaRechazos(){
	   	try {
	   		loadList = siniestrosManager.loadListaRechazos();
	   	}catch( Exception e){
	   		logger.error("Error en loadListaRechazos",e);
	   		success =  false;
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
   }

   public String loadListaIncisosRechazos(){
	   try {
		   loadList = siniestrosManager.loadListaIncisosRechazos(params);
	   }catch( Exception e){
		   logger.error("Error en loadListaRechazos",e);
		   success =  false;
		   return SUCCESS;
	   }
	   success = true;
	   return SUCCESS;
   }
   
   public String rechazarTramite(){
	   
	   try {
		   logger.debug("RechazarTramite Siniestros");
		   siniestrosManager.rechazarTramite(params);
	   }catch( Exception e){
		   logger.error("Error en rechazarTramite",e);
		   success =  false;
		   return SUCCESS;
	   }
	   success = true;
	   return SUCCESS;
   }

   public String generarContrarecibo(){
	   
	   try {
		   logger.debug("generarContrarecibo Siniestros");
		   siniestrosManager.generarContrarecibo(params);
	   }catch( Exception e){
		   logger.error("Error en generarContrarecibo",e);
		   success =  false;
		   return SUCCESS;
	   }
	   success = true;
	   return SUCCESS;
   }

   public String turnarAreaReclamaciones(){
	   
	   try {
		   logger.debug("turnarAreaReclamaciones Siniestros");
		   siniestrosManager.turnarAreaReclamaciones(params);
	   }catch( Exception e){
		   logger.error("Error en turnarAreaReclamaciones",e);
		   success =  false;
		   return SUCCESS;
	   }
	   success = true;
	   return SUCCESS;
   }

   public String turnarAreaMedica(){
	   
	   try {
		   logger.debug("turnarAreaMedica Siniestros");
		   siniestrosManager.turnarAreaMedica(params);
	   }catch( Exception e){
		   logger.error("Error en turnarAreaMedica",e);
		   success =  false;
		   return SUCCESS;
	   }
	   success = true;
	   return SUCCESS;
   }

   public String turnarOperadorAR(){
	   
	   try {
		   logger.debug("turnarOperadorAR Siniestros");
		   siniestrosManager.turnarOperadorAR(params);
	   }catch( Exception e){
		   logger.error("Error en turnarOperadorAR",e);
		   success =  false;
		   return SUCCESS;
	   }
	   success = true;
	   return SUCCESS;
   }

   public String solicitarPago(){
	   
	   try {
		   logger.debug("solicitarPago Siniestros");
		   siniestrosManager.solicitarPago(params);
	   }catch( Exception e){
		   logger.error("Error en solicitarPago",e);
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
   public String consultaListaPlazas(){
   	logger.debug(" **** Entrando al método de Lista de Plazas ****");
	   	try {
	   		listaPlazas= siniestrosManager.getConsultaListaPlaza();
	   	}catch( Exception e){
	   		logger.error("Error al consultar la Lista de los asegurados ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
   }
	
    public List<HashMap<String, String>> getDatosTablas() {
		return datosTablas;
	}

	public void setDatosTablas(List<HashMap<String, String>> datosTablas) {
		this.datosTablas = datosTablas;
	}

	private List<GenericVO> listaMotivoRechazo;
    public String execute() throws Exception {
    	success = true;
    	return SUCCESS;
    }
    
    public List<GenericVO> getListaCPTICD() {
    	return listaCPTICD;
	}
	
	public List<ConsultaTDETAUTSVO> getListaConsultaTablas() {
		return listaConsultaTablas;
	}

	public void setListaConsultaTablas(List<ConsultaTDETAUTSVO> listaConsultaTablas) {
		this.listaConsultaTablas = listaConsultaTablas;
	}

	public List<GenericVO> getListaMotivoRechazo() {
		return listaMotivoRechazo;
	}

	public void setListaMotivoRechazo(List<GenericVO> listaMotivoRechazo) {
		this.listaMotivoRechazo = listaMotivoRechazo;
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
	
	
	public List<ConsultaProveedorVO> getListaProvMedico() {
		return listaProvMedico;
	}

	public void setListaProvMedico(List<ConsultaProveedorVO> listaProvMedico) {
		this.listaProvMedico = listaProvMedico;
	}
	
	

	public List<PolizaVigenteVO> getListaPoliza() {
		return listaPoliza;
	}

	public void setListaPoliza(List<PolizaVigenteVO> listaPoliza) {
		this.listaPoliza = listaPoliza;
	}

	
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
    
    public List<ConsultaPorcentajeVO> getListaPorcentaje() {
		return listaPorcentaje;
	}

	public void setListaPorcentaje(List<ConsultaPorcentajeVO> listaPorcentaje) {
		this.listaPorcentaje = listaPorcentaje;
	}

	public List<ConsultaManteniVO> getListaConsultaManteni() {
		return listaConsultaManteni;
	}

	public void setListaConsultaManteni(
			List<ConsultaManteniVO> listaConsultaManteni) {
		this.listaConsultaManteni = listaConsultaManteni;
	}

	public List<ConsultaTTAPVAATVO> getListaConsultaTTAPVAATV() {
		return listaConsultaTTAPVAATV;
	}

	public void setListaConsultaTTAPVAATV(
			List<ConsultaTTAPVAATVO> listaConsultaTTAPVAATV) {
		this.listaConsultaTTAPVAATV = listaConsultaTTAPVAATV;
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

	public void setSaveList(List<HashMap<String, String>> saveList) {
		this.saveList = saveList;
	}

	public List<GenericVO> getListaPlazas() {
		return listaPlazas;
	}

	public void setListaPlazas(List<GenericVO> listaPlazas) {
		this.listaPlazas = listaPlazas;
	}
	
}