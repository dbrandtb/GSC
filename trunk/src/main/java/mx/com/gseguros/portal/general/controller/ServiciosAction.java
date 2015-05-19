package mx.com.gseguros.portal.general.controller;

import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.portal.general.service.ServiciosManager;
import mx.com.gseguros.utils.Utilerias;
import mx.com.gseguros.utils.Utils;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
@ParentPackage(value="struts-default")
@Namespace("/servicios")
public class ServiciosAction extends PrincipalCoreAction
{
	
	private static final long serialVersionUID = 7996363816495572103L;
	private static Logger     logger           = Logger.getLogger(ServiciosAction.class);
	
	private Map<String,String> params;
	private boolean            success;
	private String             respuesta;
	
	@Autowired
	private ServiciosManager serviciosManager;

	@Action(value   = "reemplazarDocumentoCotizacion",
		    results = {
		        @Result(name="input"   , location="/jsp-script/servicios/input.jsp"),
		        @Result(name="success" , location="/jsp-script/servicios/respuesta.jsp")
		    })
	public String reemplazarDocumentoCotizacion()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(Utilerias.join(
				 "\n###########################################"
				,"\n###### reemplazarDocumentoCotizacion ######"
				,"\n###### params=",params
				));
		
		try
		{
			Utils.validate(sb, params , "No se recibieron datos");
			
			String cdunieco  = params.get("cdunieco")
			       ,cdramo   = params.get("cdramo")
			       ,estado   = params.get("estado")
			       ,nmpoliza = params.get("nmpoliza");
			
			Utils.validate(sb
					,cdunieco , "No se recibio la sucursal"
					,cdramo   , "No se recibio el producto"
					,estado   , "No se recibio el estado de poliza"
					,nmpoliza , "No se recibio el numero de poliza");
			
			respuesta = serviciosManager.reemplazarDocumentoCotizacion(sb,cdunieco, cdramo, estado, nmpoliza);
			
			logger.info(Utilerias.join(
					 "\n###########################################"
					,"\n@@@*** reemplazarDocumentoCotizacion ***@@@"
					,"\n###########################################"
					));
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		return SUCCESS;
	}
	
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
	
}