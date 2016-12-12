package mx.com.gseguros.portal.general.controller;

import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.portal.general.service.ProcesoEmisionManager;
import mx.com.gseguros.portal.general.service.ServiciosManager;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.convention.annotation.Action;
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
@Namespace("/emisionDirecta")
public class EmisionDirectaAction extends PrincipalCoreAction
{
	
	private static final long serialVersionUID = 7996363816495572103L;
	private static Logger     logger           = LoggerFactory.getLogger(EmisionDirectaAction.class);
	
	private Map<String,String> params;
	private boolean            success;
	private String             respuesta;
	private String             detalleRespuesta;
	@Autowired
	private ServiciosManager serviciosManager;
	
	@Autowired
	private ProcesoEmisionManager procesoEmisionServiceImpl;
	
	///////////////////
	@Action(value="emitir",
		results = {
			@Result(name="success", type="json")
	    }
	)
	public String emitir() {
		
		logger.debug("Inicio de emitirIndividual Directa params={}", params);
		
		try {
			// Se validan datos:
        	Utils.validate(params, "No hay parámetros");
        	Utils.validate(params.get("ntramite"),   "No existe el par\u00E1metro params.ntramite");
        	Utils.validate(params.get("cdusuari"),   "No existe el par\u00E1metro params.cdusuari");
        	Utils.validate(params.get("cdsisrol"),   "No existe el par\u00E1metro params.cdsisrol");
        	Utils.validate(params.get("cdelemento"), "No existe el par\u00E1metro params.cdelemento");
        	Utils.validate(params.get("cdunieco"),   "No existe el par\u00E1metro params.cdunieco");
        	Utils.validate(params.get("cdramo"),     "No existe el par\u00E1metro params.cdramo");
        	Utils.validate(params.get("cdtipsit"),   "No existe el par\u00E1metro params.cdtipsit");
        	Utils.validate(params.get("nmpoliza"),   "No existe el par\u00E1metro params.nmpoliza");
        	
        	String ntramite = params.get("ntramite");
        	String cdunieco = params.get("cdunieco");
        	String cdramo   = params.get("cdramo");
        	String cdtipsit = params.get("cdtipsit");
			String nmpoliza = params.get("nmpoliza");
			String estado   = "W";
			boolean esFlotilla = StringUtils.isNotBlank(params.get("flotilla"))
					&&params.get("flotilla").equalsIgnoreCase("si");
			String tipoGrupoInciso = "I";
			if(StringUtils.isNotBlank(params.get("tipoGrupoInciso")) && params.get("tipoGrupoInciso").equals("C")) {
				tipoGrupoInciso = "C";
			}
			// Opcional, clave de usuario de captura:
			String cveusuariocaptura = params.get("cveusuariocaptura");
			
			Map<String, String> result = procesoEmisionServiceImpl.emitir(cdunieco, cdramo, estado, nmpoliza, 
					cdtipsit, ntramite, params.get("cdusuari"), params.get("cdsisrol"), params.get("cdelemento"), cveusuariocaptura, esFlotilla, tipoGrupoInciso);
			
			logger.debug("Respuesta emision individual {}", result);
			
			params.putAll(result);
			
			success = true;
			respuesta= "Emisi\u00F3n completa no. de p\u00F3liza : "+params.get("nmpoliza")+" , no. de p\u00F3liza externo : "+params.get("nmpoliex");
			
		} catch(Exception e) {
			respuesta = Utils.manejaExcepcion(e);
			detalleRespuesta = ExceptionUtils.getStackTrace(e);
		}
		
		logger.debug("Fin de emitirIndividual Directa");
		
		return SUCCESS;
	}
	///////////////////
	
	/*
	 * Getters y setters
	 */
	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

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

	public void setServiciosManager(ServiciosManager serviciosManager) {
		this.serviciosManager = serviciosManager;
	}
	
	public String getDetalleRespuesta() {
		return detalleRespuesta;
	}
	
	public void setDetalleRespuesta(String detalleRespuesta) {
		this.detalleRespuesta = detalleRespuesta;
	}
}