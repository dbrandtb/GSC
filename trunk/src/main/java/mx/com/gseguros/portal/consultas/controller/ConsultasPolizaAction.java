package mx.com.gseguros.portal.consultas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosCoberturasVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosPolizaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSituacionVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSuplementoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosTarifaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPolizaAseguradoVO;
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

    private List<ConsultaDatosTarifaVO> datosTarifa;
    
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
    public String obtienePolizasAsegurado(){
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


    //Getters and setters:
    
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
    
}