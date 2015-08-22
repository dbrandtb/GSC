package mx.com.gseguros.portal.general.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.core.web.PrincipalCoreAction;
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
		
		logger.debug("Inicio de cotizarIndividual Directa params={}", params);
		
		try {
			// Se validan datos:
			Utils.validate(params, "No se recibieron datos para cotizar");
			Utils.validate(params.get("username"),    "No existe el parámetro params.username");
			Utils.validate(params.get("cdelemento"),  "No existe el parámetro params.cdelemento");
			Utils.validate(params.get("cdunieco"),    "No existe el parámetro params.cdunieco");
			Utils.validate(params.get("cdramo"),      "No existe el parámetro params.cdramo");
			Utils.validate(params.get("cdtipsit"),    "No existe el parámetro params.cdtipsit");
			Utils.validate(list, "No se recibieron datos de incisos (list)");
			
			String nmpoliza = list.get(0).get("nmpoliza");
			String feini    = list.get(0).get("feini");
			String fefin    = list.get(0).get("fefin");
			
			boolean noTarificar = StringUtils.isNotBlank(params.get("notarificar")) && params.get("notarificar").equals("si");			
			boolean conIncisos = StringUtils.isNotBlank(params.get("conincisos")) && params.get("conincisos").equals("si");
			// Datos para TVALOPOL:
			Map<String, String> tvalopol = new HashMap<String, String>();
			for(Entry<String, String> en : list.get(0).entrySet()) {
				String key = en.getKey();
				if(key.length() > "aux.".length() && key.substring(0,"aux.".length()).equals("aux.")) {
					tvalopol.put(key.substring("aux.".length()), en.getValue());
				}
			}
			
			ManagerRespuestaSlistSmapVO resp = cotizacionManager.cotizar(
					params.get("cdunieco"), params.get("cdramo"), params.get("cdtipsit"), 
					params.get("username"), params.get("cdelemento"),
					nmpoliza, feini, fefin, params.get("cdpersonCli"), params.get("cdideperCli"),
					noTarificar, conIncisos, list, params.containsKey("movil"),
					tvalopol, params.get("cdagenteAux"));
			
			respuesta = resp.getRespuesta();
			
			if(resp.isExito()) {
				params.putAll(resp.getSmap());
				tarifa = resp.getSlist();
			}
			
			success = true;
			
		} catch(Exception e) {
			respuesta = Utils.manejaExcepcion(e);
		}
		
		logger.debug("Fin de cotizarIndividual Directa");
		
		return SUCCESS;
	}
	
	
	
	@Action(value="comprarCotizacion",
		results = {
			@Result(name="success", type="json")
	    }
	)
    public String comprarCotizacion() {
    	
		logger.debug("Inicio de comprarCotizacion Directa params={}", params);
    	
    	try {
        	// Se validan datos:
        	Utils.validate(params, "No hay parámetros");
        	Utils.validate(params.get("cdusuari"),      "No existe el parámetro params.cdusuari");
        	Utils.validate(params.get("cdsisrol"),      "No existe el parámetro params.cdsisrol");
        	Utils.validate(params.get("cdelemento"),    "No existe el parámetro params.cdelemento");
        	Utils.validate(params.get("cdunieco"),      "No existe el parámetro params.cdunieco");
        	Utils.validate(params.get("cdramo"),        "No existe el parámetro params.cdramo");
        	Utils.validate(params.get("cdtipsit"),      "No existe el parámetro params.cdtipsit");
        	Utils.validate(params.get("nmpoliza"),      "No existe el parámetro params.nmpoliza");
        	Utils.validate(params.get("cdciaaguradora"),"No existe el parámetro params.cdciaaguradora");
        	Utils.validate(params.get("cdplan"),        "No existe el parámetro params.cdplan");
        	Utils.validate(params.get("cdperpag"),      "No existe el parámetro params.cdperpag");
        	Utils.validate(params.get("fechaInicio"),   "No existe el parámetro params.fechaInicio");
        	Utils.validate(params.get("fechaFin"),      "No existe el parámetro params.fechaFin");
        	Utils.validate(params.get("cdagenteExt"),   "No existe el parámetro params.cdagenteExt");
        	
        	// Se llenan datos:
    		boolean esFlotilla  = StringUtils.isNotBlank(params.get("flotilla"))&&params.get("flotilla").equalsIgnoreCase("si");
    		String tipoflot    = params.get("tipoflot");
    		
    		String ntramite = cotizacionManager.procesoComprarCotizacion(params.get("cdunieco"), params.get("cdramo"), params.get("nmpoliza"), 
    				params.get("cdtipsit"), params.get("fechaInicio"), params.get("fechaFin"), params.get("ntramite"), 
    				params.get("cdagenteExt"),
    				params.get("cdciaaguradora"), params.get("cdplan"), params.get("cdperpag"), 
    				params.get("cdusuari"), params.get("cdsisrol"), params.get("cdelemento"),
    				esFlotilla, tipoflot, params.get("cdpersonCli"), params.get("cdideperCli"),
    				getText("rdf.cotizacion.nombre."+params.get("cdtipsit")),
    				getText("rdf.cotizacion.flot.nombre."+params.get("cdtipsit")));
    		
    		params.put("ntramite", ntramite);
    		
    	} catch(Exception e) {
    		respuesta = Utils.manejaExcepcion(e);
    	}
    	
        logger.debug("Fin de comprarCotizacion Directa");
    	
        return SUCCESS;
    }
	
	
	// Getters y setters

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