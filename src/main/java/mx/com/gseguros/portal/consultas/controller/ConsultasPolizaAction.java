package mx.com.gseguros.portal.consultas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosCoberturasVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosPolizaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSituacionVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSuplementoVO;
import mx.com.gseguros.portal.consultas.service.ConsultasPolizaManager;

/**
 *
 * @author HMLT
 */
public class ConsultasPolizaAction extends PrincipalCoreAction{
    
    private org.apache.log4j.Logger log =org.apache.log4j.Logger.getLogger(ConsultasPolizaAction.class);

    private ConsultasPolizaManager consultasPolizaManager;
    private HashMap<String,String> parametros;
    private String cdunieco;
    private String cdramo;
    private String estado;
    private String nmpoliza;
    private ConsultaDatosPolizaVO datosPoliza;
    private ConsultaDatosSuplementoVO datosSuplemento;
    private ConsultaDatosSituacionVO datosSituacion;
    private ConsultaDatosCoberturasVO datosCoberturas;
    
    
    private boolean success=true;
    
    public String consultaDatosPoliza(){
    	logger.debug(" **** Entrando a Consulta de Poliza ****");
        try {
        	
        	
        	//"1", "4", "M", "26", "", ""
			WrapperResultados result = consultasPolizaManager.consultaPoliza(
					parametros.get("cdunieco"), parametros.get("cdramo"),
					parametros.get("estado"), parametros.get("nmpoliza"),
					parametros.get("idper"), parametros.get("nmclient"));
			
        	ArrayList<ConsultaDatosPolizaVO> lista = (ArrayList<ConsultaDatosPolizaVO>) result.getItemList();
        	
        	if(lista!=null && !lista.isEmpty())	datosPoliza = lista.get(0);
        	
        	logger.debug("Resultado de la consulta de poliza:" + datosPoliza);
        	
        }catch( Exception e){
            success = false;
            logger.error("Error al obtener los datos de la poliza ",e);
            return SUCCESS;
        }
        
        success = true;
        return SUCCESS;
    
    }
    public String consultaDatosSuplemento(){
    	logger.debug(" **** Entrando a consultaDatosSuplemento ****");
    	try {
    		
    		WrapperResultados result = consultasPolizaManager.consultaSuplemento(
					parametros.get("cdunieco"), parametros.get("cdramo"),
					parametros.get("estado"), parametros.get("nmpoliza"));
    		
    		ArrayList<ConsultaDatosSuplementoVO> lista = (ArrayList<ConsultaDatosSuplementoVO>) result.getItemList();
    		
    		if(lista!=null && !lista.isEmpty())	datosSuplemento = lista.get(0);
    		
    		logger.debug("Resultado de la consultaDatosSuplemento:" + datosSuplemento);
    		
    	}catch( Exception e){
    		success = false;
    		logger.error("Error al obtener los consultaDatosSuplemento ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }
    public String consultaDatosSituacion(){
    	logger.debug(" **** Entrando a consultaDatosSituacion ****");
    	try {
    		
    		WrapperResultados result = consultasPolizaManager.consultaSituacion(
					parametros.get("cdunieco"), parametros.get("cdramo"),
					parametros.get("estado"), parametros.get("nmpoliza"),
					parametros.get("suplemento"), parametros.get("nmsituac"));
    		
    		ArrayList<ConsultaDatosSituacionVO> lista = (ArrayList<ConsultaDatosSituacionVO>) result.getItemList();
    		
    		if(lista!=null && !lista.isEmpty())	datosSituacion = lista.get(0);
    		
    		logger.debug("Resultado de la consultaDatosSituacion:" + datosSituacion);
    		
    	}catch( Exception e){
    		success = false;
    		logger.error("Error al obtener los consultaDatosSituacion ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }
    public String consultaDatosCoberturas(){
    	logger.debug(" **** Entrando a consultaDatosCoberturas ****");
    	try {
    		
    		WrapperResultados result = consultasPolizaManager.consultaCoberturas(
					parametros.get("cdunieco"), parametros.get("cdramo"),
					parametros.get("estado"), parametros.get("nmpoliza"),
					parametros.get("suplemento"), parametros.get("nmsituac"));
    		
    		ArrayList<ConsultaDatosCoberturasVO> lista = (ArrayList<ConsultaDatosCoberturasVO>) result.getItemList();
    		
    		if(lista!=null && !lista.isEmpty())	datosCoberturas = lista.get(0);
    		
    		logger.debug("Resultado de la consultaDatosCoberturas:" + datosCoberturas);
    		
    	}catch( Exception e){
    		success = false;
    		logger.error("Error al obtener los consultaDatosCoberturas ",e);
    		return SUCCESS;
    	}
    	
    	success = true;
    	return SUCCESS;
    	
    }
    

    public void setParametros(HashMap<String, String> parametros) {
        this.parametros = parametros;
    }

    public String getCdunieco() {
        return cdunieco;
    }

    public void setCdunieco(String cdunieco) {
        this.cdunieco = cdunieco;
    }

    public String getCdramo() {
        return cdramo;
    }

    public void setCdramo(String cdramo) {
        this.cdramo = cdramo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNmpoliza() {
        return nmpoliza;
    }

    public void setNmpoliza(String nmpoliza) {
        this.nmpoliza = nmpoliza;
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


	public ConsultaDatosPolizaVO getDatosPoliza() {
		return datosPoliza;
	}


	public void setDatosPoliza(ConsultaDatosPolizaVO datosPoliza) {
		this.datosPoliza = datosPoliza;
	}
	public ConsultaDatosSuplementoVO getDatosSuplemento() {
		return datosSuplemento;
	}
	public void setDatosSuplemento(ConsultaDatosSuplementoVO datosSuplemento) {
		this.datosSuplemento = datosSuplemento;
	}
	public ConsultaDatosSituacionVO getDatosSituacion() {
		return datosSituacion;
	}
	public void setDatosSituacion(ConsultaDatosSituacionVO datosSituacion) {
		this.datosSituacion = datosSituacion;
	}
	public ConsultaDatosCoberturasVO getDatosCoberturas() {
		return datosCoberturas;
	}
	public void setDatosCoberturas(ConsultaDatosCoberturasVO datosCoberturas) {
		this.datosCoberturas = datosCoberturas;
	}
    
}