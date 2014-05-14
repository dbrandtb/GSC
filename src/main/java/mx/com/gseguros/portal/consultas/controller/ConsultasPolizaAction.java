package mx.com.gseguros.portal.consultas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosAgenteVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosAseguradoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosCoberturasVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosPolizaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSituacionVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSuplementoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosTarifaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPolizaAgenteVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPolizaAseguradoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaReciboAgenteVO;
import mx.com.gseguros.portal.consultas.model.CopagoVO;
import mx.com.gseguros.portal.consultas.service.ConsultasPolizaManager;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;

/**
 *
 * @author HMLT
 */
public class ConsultasPolizaAction extends PrincipalCoreAction{
    
    private static final long serialVersionUID = -6321288906841302337L;

	private org.apache.log4j.Logger logger =org.apache.log4j.Logger.getLogger(ConsultasPolizaAction.class);
	
    /**
     * Success property
     */
    private boolean success;
    
    private String mensajeRes;
    
    private ConsultasPolizaManager consultasPolizaManager;
    
    private KernelManagerSustituto kernelManager;
    
    private HashMap<String,String> params;
    
    private ConsultaDatosPolizaVO datosPoliza;
    
    private List<ConsultaDatosSuplementoVO> datosSuplemento;
    
    private List<ConsultaDatosSituacionVO> datosSituacion;
    
    private List<ConsultaDatosCoberturasVO> datosCoberturas;

    private List<ConsultaPolizaAseguradoVO> polizasAsegurado;

    private List<ConsultaPolizaAgenteVO> polizasAgente;
    
    private List<ConsultaReciboAgenteVO> recibosAgente;

    private List<ConsultaDatosTarifaVO> datosTarifa;
    
    private ConsultaDatosAgenteVO datosAgente;
    
    private List<ConsultaDatosAseguradoVO> datosAsegurados;
    
    private List<CopagoVO> datosCopagosPoliza;

    private Map<String,Item> itemMap;

    public String execute() throws Exception {
    	return SUCCESS;
    }
    
    /**
     * Obtiene los datos generales de una p&oacute;liza
     * @return String result
     */
    public String consultaDatosPoliza(){
    	logger.debug(" **** Entrando a Consulta de Poliza ****");
        try {
			WrapperResultados result = consultasPolizaManager.consultaPoliza(
					params.get("cdunieco"), params.get("cdramo"),
					params.get("estado"), params.get("nmpoliza"));
			
        	ArrayList<ConsultaDatosPolizaVO> lista = (ArrayList<ConsultaDatosPolizaVO>) result.getItemList();
        	
        	if(lista!=null && !lista.isEmpty())	datosPoliza = lista.get(0);
        	
        	logger.debug("Resultado de la consulta de poliza:" + datosPoliza);
        	
        }catch( Exception e){
            logger.error("Error al obtener los datos de la poliza ",e);
            return SUCCESS;
        }
        
        success = true;
        return SUCCESS;
    }
    
    
    /**
     * Obtiene los datos de los suplementos de la poliza
     * @return String result
     */
    public String consultaDatosSuplemento(){
    	logger.debug(" **** Entrando a consultaDatosSuplemento ****");
    	mensajeRes = "";
    	try {
    		
    		WrapperResultados result = consultasPolizaManager.consultaSuplemento(params.get("nmpoliex"));
    		datosSuplemento = (ArrayList<ConsultaDatosSuplementoVO>) result.getItemList();
    		
    		logger.debug("Resultado de la consultaDatosSuplemento:" + datosSuplemento);
    		
    		if(datosSuplemento != null && !datosSuplemento.isEmpty()){
    			try{
    				mensajeRes = consultasPolizaManager.consultaMensajeAgente(datosSuplemento.get(0).getCdunieco(), datosSuplemento.get(0).getCdramo(),
							  datosSuplemento.get(0).getEstado(), datosSuplemento.get(0).getNmpoliza());
    				logger.debug("Mensaje para Agente: "+ mensajeRes);
    			}catch(Exception e){
    				logger.error("Error!! no se pudo obtener el mensaje para el Agente de esta poliza!",e);
    			}
    		}
    		
    	}catch( Exception e){
    		success = false;
    		logger.error("Error al obtener los consultaDatosSuplemento ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }
    
    
    /**
     * Obtiene los datos de las situaciones de la poliza
     * @return String result
     */
    public String consultaDatosSituacion(){
    	logger.debug(" **** Entrando a consultaDatosSituacion ****");
    	try {
    		
    		WrapperResultados result = consultasPolizaManager.consultaSituacion(
					params.get("cdunieco"), params.get("cdramo"),
					params.get("estado"), params.get("nmpoliza"),
					params.get("suplemento"), params.get("nmsituac"));
    		
    		datosSituacion = (ArrayList<ConsultaDatosSituacionVO>) result.getItemList();
    		
    		logger.debug("Resultado de la consultaDatosSituacion:" + datosSituacion);
    		
    	}catch( Exception e){
    		logger.error("Error al obtener los consultaDatosSituacion ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }
    
    
    /**
     * Obtiene los datos de las coberturas de la poliza
     * @return String result
     */
    public String consultaDatosCoberturas(){
    	logger.debug(" **** Entrando a consultaDatosCoberturas ****");
    	try {
    		
    		WrapperResultados result = consultasPolizaManager.consultaCoberturas(
					params.get("cdunieco"), params.get("cdramo"),
					params.get("estado"), params.get("nmpoliza"),
					params.get("suplemento"), params.get("nmsituac"));
    		
    		datosCoberturas = (ArrayList<ConsultaDatosCoberturasVO>) result.getItemList();
    		
    		logger.debug("Resultado de la consultaDatosCoberturas:" + datosCoberturas);
    		
    	}catch( Exception e){
    		logger.error("Error al obtener los consultaDatosCoberturas ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }

    /**
     * Obtiene las polizas del asegurado por rfc
     * @return String result
     */
    public String consultaPolizasAsegurado(){
    	logger.debug(" **** Entrando a obtienePolizasAsegurado ****");
    	try {
    		
    		WrapperResultados result = consultasPolizaManager.obtienePolizasAsegurado(params.get("rfc"), params.get("cdperson"), params.get("nombre"));
    		
    		polizasAsegurado = (ArrayList<ConsultaPolizaAseguradoVO>) result.getItemList();
    		
    		logger.debug("Resultado de la obtienePolizasAsegurado:" + polizasAsegurado);
    		
    	}catch( Exception e){
    		logger.error("Error al obtener los obtienePolizasAsegurado ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }
    
    /**
     * Obtiene los datos de la tarifa de la poliza
     * @return String result
     */
    public String consultaDatosTarifaPoliza(){
    	logger.debug(" **** Entrando a consultaDatosTarifaPoliza ****");
    	try {
    		
    		WrapperResultados result = consultasPolizaManager.consultaDatosTarifa(
					params.get("cdunieco"), params.get("cdramo"),
					params.get("estado"), params.get("nmpoliza"),
					params.get("suplemento"));
    		
    		datosTarifa = (ArrayList<ConsultaDatosTarifaVO>) result.getItemList();
    		
    		logger.debug("Resultado de la consultaDatosTarifa:" + datosTarifa);
    		
    	}catch( Exception e){
    		logger.error("Error al obtener los consultaDatosTarifaPoliza ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }
    
    
    /**
     * Obtiene las polizas de un Agente
     * @return String result
     */
    public String consultaPolizasAgente(){
    	logger.debug(" **** Entrando a consultaPolizasAgente ****");
    	try {
    		
    		WrapperResultados result = consultasPolizaManager.consultaPolizasAgente(
    				params.get("cdagente"));
    		
    		polizasAgente = (ArrayList<ConsultaPolizaAgenteVO>) result.getItemList();
    		
    		logger.debug("Resultado de la consultaPolizasAgente:" + polizasAgente);
    		
    	}catch( Exception e){
    		logger.error("Error al obtener los consultaPolizasAgente ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }
    
    
    /**
     * Obtiene recibos de la poliza de un agente
     * @return String result
     */
    public String consultaRecibosAgente(){
    	logger.debug(" **** Entrando a consultaRecibosAgente ****");
    	try {
    		
    		WrapperResultados result = consultasPolizaManager.consultaRecibosAgente(
    				params.get("cdunieco"), params.get("cdramo"),
    				params.get("estado"), params.get("nmpoliza"));
    		
    		recibosAgente = (ArrayList<ConsultaReciboAgenteVO>) result.getItemList();
    		
    		logger.debug("Resultado de la consultaRecibosAgente:" + recibosAgente);
    		
    	}catch( Exception e){
    		logger.error("Error al obtener los consultaRecibosAgente ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }

    /**
     * Obtiene los datos generales de una p&oacute;liza
     * @return String result
     */
    public String consultaDatosAgente(){
    	logger.debug(" **** Entrando a Consulta de Agente ****");
        try {
        	WrapperResultados result = consultasPolizaManager.consultaAgente(params.get("cdagente"));
        	ArrayList<ConsultaDatosAgenteVO> lista = (ArrayList<ConsultaDatosAgenteVO>) result.getItemList();
        	
        	if(lista!=null && !lista.isEmpty())	datosAgente = lista.get(0);
        	
        	logger.debug("Resultado de la consulta de Agente:" + datosAgente);
        	
        }catch( Exception e){
            logger.error("Error al obtener los datos del Agente ",e);
            return SUCCESS;
        }
        
        success = true;
        return SUCCESS;
    }
    
    
    /**
     * Obtiene los datos del asegurado
     * @return String result
     */
    public String consultaDatosAsegurado(){
    	logger.debug(" **** Entrando a consultaDatosAsegurado ****");
    	try {
    		
    		WrapperResultados result = consultasPolizaManager.consultaDatosAsegurado(
    				params.get("cdunieco"), params.get("cdramo"),
    				params.get("estado"), params.get("nmpoliza"), params.get("suplemento"));
    		
    		datosAsegurados = (ArrayList<ConsultaDatosAseguradoVO>) result.getItemList();
    		
    		logger.debug("Resultado de la consultaDatosAsegurado:" + datosAsegurados);
    		
    	}catch( Exception e){
    		logger.error("Error al obtener los datos del Asegurado ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }    

    
    /**
     * Obtiene los copagos de una poliza
     * @return String result
     */
    public String consultaCopagosPoliza(){
    	logger.debug(" **** Entrando a consultaCopagosPoliza ****");
    	try {
    		
    		WrapperResultados result = consultasPolizaManager.consultaCopagosPoliza(
    				params.get("cdunieco"), params.get("cdramo"),
    				params.get("estado"), params.get("nmpoliza"), params.get("suplemento"));
    		
    		datosCopagosPoliza = (ArrayList<CopagoVO>) result.getItemList();
    		
    		logger.debug("Resultado de consultaCopagosPoliza:" + datosCopagosPoliza);
    		
    	}catch( Exception e){
    		logger.error("Error al obtener los copagos de la poliza ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }
    
    
    /**
     * Funcion para la visualizacion de las coberturas 
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
     * Funcion para la visualizacion de las coberturas 
     * @return params con los valores para hacer las consultas
     */
	public String verExclusiones(){
		logger.debug(" **** Entrando a verExclusiones ****");
		try {
			//logger.debug("params=" + params);
		}catch( Exception e){
			logger.error(e.getMessage(), e);
		}
		success = true;
		return SUCCESS;
    }
    
	public String obtenerDatosTatrisit()
	{
		logger.info(""
				+ "\n######################################"
				+ "\n###### pantallaConsultaTatrisit ######"
				);
		logger.info("params: "+params);
		try
		{
			String cdtipsit = params.get("cdtipsit");
			List<ComponenteVO> camposTatrisit = kernelManager.obtenerTatrisit(cdtipsit);
			List<ComponenteVO> tatrisitTemp   = new ArrayList<ComponenteVO>();
			//buscar cp
			for(ComponenteVO t:camposTatrisit) if(t.getNameCdatribu().equals("3")) tatrisitTemp.add(t);
			//buscar estado
			for(ComponenteVO t:camposTatrisit) if(t.getNameCdatribu().equals("4")) tatrisitTemp.add(t);
			//buscar municipio
			for(ComponenteVO t:camposTatrisit) if(t.getNameCdatribu().equals("17")) tatrisitTemp.add(t);
			//agregar todos los demas
			for(ComponenteVO comp : camposTatrisit)
			{
				comp.setSoloLectura(true);
				comp.setObligatorio(false);
				comp.setMinLength(0);
				if(!comp.getNameCdatribu().equals("3")
						&&!comp.getNameCdatribu().equals("4")
						&&!comp.getNameCdatribu().equals("17")
						)
				{
					tatrisitTemp.add(comp);
				}
			}
			camposTatrisit=tatrisitTemp;
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.setCdtipsit(cdtipsit);
			gc.generaComponentes(camposTatrisit, true, true, true, false, false, false);
			itemMap=new HashMap<String,Item>();
			itemMap.put("fieldsModelo",gc.getFields());
			itemMap.put("itemsFormulario",gc.getItems());
		}
		catch(Exception ex)
		{
			logger.error("error al generar pantalla de consulta de tatrisit",ex);
		}
		logger.info(""
				+ "\n###### pantallaConsultaTatrisit ######"
				+ "\n######################################"
				);
		return SUCCESS;
	}
    
    //Getters and setters:
    
	public List<ConsultaDatosAseguradoVO> getDatosAsegurados() {
		return datosAsegurados;
	}

	public void setDatosAsegurados(List<ConsultaDatosAseguradoVO> datosAsegurados) {
		this.datosAsegurados = datosAsegurados;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	public ConsultaDatosPolizaVO getDatosPoliza() {
		return datosPoliza;
	}

	public void setDatosPoliza(ConsultaDatosPolizaVO datosPoliza) {
		this.datosPoliza = datosPoliza;
	}

	public List<ConsultaDatosSuplementoVO> getDatosSuplemento() {
		return datosSuplemento;
	}

	public void setDatosSuplemento(List<ConsultaDatosSuplementoVO> datosSuplemento) {
		this.datosSuplemento = datosSuplemento;
	}

	public List<ConsultaDatosSituacionVO> getDatosSituacion() {
		return datosSituacion;
	}

	public void setDatosSituacion(List<ConsultaDatosSituacionVO> datosSituacion) {
		this.datosSituacion = datosSituacion;
	}

	public List<ConsultaDatosCoberturasVO> getDatosCoberturas() {
		return datosCoberturas;
	}

	public void setDatosCoberturas(List<ConsultaDatosCoberturasVO> datosCoberturas) {
		this.datosCoberturas = datosCoberturas;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setConsultasPolizaManager(
			ConsultasPolizaManager consultasPolizaManager) {
		this.consultasPolizaManager = consultasPolizaManager;
	}

	public List<ConsultaPolizaAseguradoVO> getPolizasAsegurado() {
		return polizasAsegurado;
	}

	public void setPolizasAsegurado(List<ConsultaPolizaAseguradoVO> polizasAsegurado) {
		this.polizasAsegurado = polizasAsegurado;
	}

	public List<ConsultaDatosTarifaVO> getDatosTarifa() {
		return datosTarifa;
	}

	public void setDatosTarifa(List<ConsultaDatosTarifaVO> datosTarifa) {
		this.datosTarifa = datosTarifa;
	}

	public List<ConsultaPolizaAgenteVO> getPolizasAgente() {
		return polizasAgente;
	}

	public void setPolizasAgente(List<ConsultaPolizaAgenteVO> polizasAgente) {
		this.polizasAgente = polizasAgente;
	}

	public List<ConsultaReciboAgenteVO> getRecibosAgente() {
		return recibosAgente;
	}

	public void setRecibosAgente(List<ConsultaReciboAgenteVO> recibosAgente) {
		this.recibosAgente = recibosAgente;
	}

	public ConsultaDatosAgenteVO getDatosAgente() {
		return datosAgente;
	}

	public void setDatosAgente(ConsultaDatosAgenteVO datosAgente) {
		this.datosAgente = datosAgente;
	}

	public List<CopagoVO> getDatosCopagosPoliza() {
		return datosCopagosPoliza;
	}

	public void setDatosCopagosPoliza(List<CopagoVO> datosCopagosPoliza) {
		this.datosCopagosPoliza = datosCopagosPoliza;
	}

	public Map<String, Item> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<String, Item> itemMap) {
		this.itemMap = itemMap;
	}

	public void setKernelManager(KernelManagerSustituto kernelManager) {
		this.kernelManager = kernelManager;
	}

	public String getMensajeRes() {
		return mensajeRes;
	}

	public void setMensajeRes(String mensajeRes) {
		this.mensajeRes = mensajeRes;
	}

}