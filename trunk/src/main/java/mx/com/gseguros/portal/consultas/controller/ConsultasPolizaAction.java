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
    
    private HashMap<String,String> parametros;
    
    private ConsultaDatosPolizaVO datosPoliza;
    
    private List<ConsultaDatosSuplementoVO> datosSuplemento;
    
    private List<ConsultaDatosSituacionVO> datosSituacion;
    
    private List<ConsultaDatosCoberturasVO> datosCoberturas;

    private List<ConsultaPolizaAseguradoVO> polizasAsegurado;
    
    /**
     * Obtiene los datos generales de una p&oacute;liza
     * @return String result
     */
    public String consultaDatosPoliza(){
    	logger.debug(" **** Entrando a Consulta de Poliza ****");
        try {
			WrapperResultados result = consultasPolizaManager.consultaPoliza(
					parametros.get("cdunieco"), parametros.get("cdramo"),
					parametros.get("estado"), parametros.get("nmpoliza"),
					parametros.get("idper"), parametros.get("nmclient"));
			
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
					parametros.get("cdunieco"), parametros.get("cdramo"),
					parametros.get("estado"), parametros.get("nmpoliza"));
    		
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
					parametros.get("cdunieco"), parametros.get("cdramo"),
					parametros.get("estado"), parametros.get("nmpoliza"),
					parametros.get("suplemento"), parametros.get("nmsituac"));
    		
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
					parametros.get("cdunieco"), parametros.get("cdramo"),
					parametros.get("estado"), parametros.get("nmpoliza"),
					parametros.get("suplemento"), parametros.get("nmsituac"));
    		
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
    		
    		WrapperResultados result = consultasPolizaManager.obtienePolizasAsegurado(parametros.get("rfc"));
    		
    		polizasAsegurado = (ArrayList<ConsultaPolizaAseguradoVO>) result.getItemList();
    		
    		logger.debug("Resultado de la consultaDatosCoberturas:" + polizasAsegurado);
    		
    	}catch( Exception e){
    		logger.error("Error al obtener los obtienePolizasAsegurado ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }


    //Getters and setters:
    
	public HashMap<String, String> getParametros() {
		return parametros;
	}

	public void setParametros(HashMap<String, String> parametros) {
		this.parametros = parametros;
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
    
}