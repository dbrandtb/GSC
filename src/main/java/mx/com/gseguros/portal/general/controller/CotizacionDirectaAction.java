package mx.com.gseguros.portal.general.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistSmapVO;
import mx.com.gseguros.portal.cotizacion.service.CotizacionManager;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
@ParentPackage(value="seguridad")
@Namespace("/cotizacionDirecta")
public class CotizacionDirectaAction extends PrincipalCoreAction {
	
	private static final long serialVersionUID = 7996363816495572103L;
	
	private static Logger logger = LoggerFactory.getLogger(CotizacionDirectaAction.class);
	
	private boolean success;
	
	private String respuesta;
	
	private Map<String,String> params;
	
	private List<Map<String,String>> list;
	
	private List<Map<String,String>> tarifa;
	
	
	@Autowired
	private CotizacionManager cotizacionManager;
	
	
	@Action(value="cotizarIndividual",
		results = {
			@Result(name="success", type="json")
	    },
	    interceptorRefs = {
	            @InterceptorRef(value = "json", params = {"enableSMD", "true", "ignoreSMDMethodInterfaces", "false" })
	    }
	)
	public String cotizarIndividual() throws Exception {
		
		logger.debug(Utils.log(
				 "\n#####################"
				,"\n###### cotizarIndividual Directa ######"
				,"\n###### params=", params));
		
		try {
			
			Utils.validate(params, "No se recibieron datos para cotizar");
			Utils.validate(list, "No se recibieron datos de incisos");

			String cdusuari    = params.get("username");
			String cdelemen    = params.get("cdelemento");
			String cdunieco    = params.get("cdunieco");
			String cdramo      = params.get("cdramo");
			String cdtipsit    = params.get("cdtipsit");
			String cdagente    = params.get("cdagenteAux");
			String cdpersonCli = params.get("cdpersonCli");
			String cdideperCli = params.get("cdideperCli");
			
			String nmpoliza = list.get(0).get("nmpoliza");
			String feini    = list.get(0).get("feini");
			String fefin    = list.get(0).get("fefin");
			
			boolean noTarificar = StringUtils.isNotBlank(params.get("notarificar")) && params.get("notarificar").equals("si");			
			boolean conIncisos = StringUtils.isNotBlank(params.get("conincisos")) && params.get("conincisos").equals("si");
			
			Map<String,String>tvalopol=new HashMap<String,String>();
			for(Entry<String,String> en : list.get(0).entrySet()) {
				String key=en.getKey();
				if(key.length()>"aux.".length() && key.substring(0,"aux.".length()).equals("aux.")) {
					tvalopol.put(key.substring("aux.".length()),en.getValue());
				}
			}
			
			ManagerRespuestaSlistSmapVO resp = cotizacionManager.cotizar(
					cdunieco, cdramo, cdtipsit, cdusuari, cdelemen,
					nmpoliza, feini, fefin, cdpersonCli, cdideperCli,
					noTarificar, conIncisos, list, params.containsKey("movil"),
					tvalopol, cdagente);
			
			respuesta = resp.getRespuesta();
			
			if(resp.isExito()) {
				params.putAll(resp.getSmap());
				tarifa = resp.getSlist();
			}
			
			success = true;
			
		} catch(Exception e) {
			respuesta = Utils.manejaExcepcion(e);
		}
		
		logger.debug(Utils.log(
				 "\n###### cotizarIndividual Directa ######"
				,"\n#####################"));
		return SUCCESS;
	}
	
	
	
	@Action(value="comprarCotizacion",
		results = {
			@Result(name="success", type="json")
	    }
	)
    public String comprarCotizacion()
    /*pv_cdunieco   input
    --pv_cdramo     input
    --pv_estado     W
    --pv_nmpoliza   input
    --pv_nmsituac   0
    --pv_cdelement  Usuario sesion
    --pv_cdperson   Usuario sesion
    --pv_cdasegur   input
    --pv_cdplan     input
    --pv_cdperpag   input
    */
    {
    	
    	logger.debug(Utils.log(
   			 "\n################################"
   			,"\n###### comprar cotizacion ######"
   			,"\n###### params=",params));
    	
    	try {
        	
        	////////////
        	//TODO: borrar variables, pues vienen como parametros:
    		String cdunieco = params.get("cdunieco");
    		String cdramo = params.get("cdramo");
        	String cdtipsit = params.get("cdtipsit");
        	String nmpoliza = params.get("nmpoliza");
        	String cdciaaguradora = params.get("cdciaaguradora");
        	String cdplan = params.get("cdplan");
        	String cdperpag = params.get("cdperpag");
        	String fechaInicio = params.get("fechaInicio");
        	String fechaFin = params.get("fechaFin");
        	////////////
        	
        	
        	// Se validan datos:
        	Utils.validate(params, "No hay parámetros");
        	Utils.validate(params.get("cdusuari"), "No existe el parámetro params.cdusuari");
        	Utils.validate(params.get("cdsisrol"), "No existe el parámetro params.cdsisrol");
        	Utils.validate(params.get("cdelemento"), "No existe el parámetro params.cdelemento");
        	Utils.validate(cdunieco, "No existe el parámetro cdunieco");
        	Utils.validate(cdramo, "No existe el parámetro cdramo");
        	Utils.validate(cdtipsit, "No existe el parámetro cdtipsit");
        	Utils.validate(nmpoliza, "No existe el parámetro nmpoliza");
        	Utils.validate(cdciaaguradora, "No existe el parámetro cdciaaguradora");
        	Utils.validate(cdplan, "No existe el parámetro cdplan");
        	Utils.validate(cdperpag, "No existe el parámetro cdperpag");
        	Utils.validate(fechaInicio, "No existe el parámetro fechaInicio");
        	Utils.validate(fechaFin, "No existe el parámetro fechaFin");
        	Utils.validate(params.get("cdagenteExt"), "No existe el parámetro params.cdagenteExt");
        	
        	UserVO usuario     = null;
        	String cdusuari    = params.get("cdusuari"); //TODO: Enviar como parametro
        	String cdsisrol    = params.get("cdsisrol"); //TODO: Enviar como parametro
        	String cdperson    = null;
        	//String fechaInicio = null; //TODO: decomentar
        	//String fechaFin    = null; //TODO: decomentar
        	String ntramite    = null;
        	String cdagente    = null;
        	String nmcuadro    = null;
        	String cdelemen    = params.get("cdelemento"); //TODO: Enviar como parametro
        	String cdpersonCli = null;
        	String cdideperCli = null;
        	String cdagenteExt = null;
        	boolean esFlotilla = false;
        	String tipoflot    = null;
        	
        	// Se llenan datos:
        	ntramite    = params.get("ntramite");
    		cdpersonCli = params.get("cdpersonCli");
    		cdideperCli = params.get("cdideperCli");
    		cdagenteExt = params.get("cdagenteExt");
    		fechaInicio = params.get("fechaInicio");
    		fechaFin    = params.get("fechaFin");
    		esFlotilla  = StringUtils.isNotBlank(params.get("flotilla"))&&params.get("flotilla").equalsIgnoreCase("si");
    		tipoflot    = params.get("tipoflot");
    		
    		cotizacionManager.procesoComprarCotizacion(
    				cdunieco,
    				cdramo,
    				nmpoliza,
    				cdtipsit,
    				fechaInicio,
    				fechaFin,
    				ntramite,
    				cdagenteExt,
    				cdciaaguradora,
    				cdplan,
    				cdperpag,
    				cdusuari,
    				cdsisrol,
    				cdelemen,
    				esFlotilla,
    				tipoflot,
    				cdpersonCli,
    				cdideperCli,
    				getText("rdf.cotizacion.nombre."+cdtipsit),
    				getText("rdf.cotizacion.flot.nombre."+cdtipsit));
    		
    	} catch(Exception e) {
    		respuesta = Utils.manejaExcepcion(e);
    	}
    	
        logger.debug(Utils.log(
    			 "\n###### comprar cotizacion ######"
        		,"\n################################"));
    	
        return SUCCESS;
    }
	///////////////////////


	/*
	 * Getters y setters
	 */

	public boolean isSuccess() {
		return success;
	}



	public void setSuccess(boolean success) {
		this.success = success;
	}



	public String getRespuesta() {
		return respuesta;
	}



	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}



	public Map<String, String> getParams() {
		return params;
	}



	public void setParams(Map<String, String> params) {
		this.params = params;
	}



	public List<Map<String, String>> getList() {
		return list;
	}



	public void setList(List<Map<String, String>> list) {
		this.list = list;
	}



	public List<Map<String, String>> getTarifa() {
		return tarifa;
	}



	public void setTarifa(List<Map<String, String>> tarifa) {
		this.tarifa = tarifa;
	}
	
}