package mx.com.gseguros.portal.consultas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.core.web.PrincipalCoreAction;
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
import mx.com.gseguros.portal.consultas.service.ConsultasPolizaManager;

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
    
    private ConsultasPolizaManager consultasPolizaManager;
    
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
					params.get("estado"), params.get("nmpoliza"),
					params.get("idper"), params.get("nmclient"));
			
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
    	try {
    		
    		WrapperResultados result = consultasPolizaManager.consultaSuplemento(
					params.get("cdunieco"), params.get("cdramo"),
					params.get("estado"), params.get("nmpoliza"));
    		
    		datosSuplemento = (ArrayList<ConsultaDatosSuplementoVO>) result.getItemList();
    		
    		logger.debug("Resultado de la consultaDatosSuplemento:" + datosSuplemento);
    		
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
    		
    		WrapperResultados result = consultasPolizaManager.obtienePolizasAsegurado(params.get("rfc"));
    		
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
    
}