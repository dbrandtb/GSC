package mx.com.gseguros.portal.emision.controller;

import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.mesacontrol.model.FlujoVO;
import mx.com.gseguros.utils.Utils;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
@ParentPackage(value="default")
@Namespace("/emision")
public class ClienteTramiteAction extends PrincipalCoreAction
{
	private final static Logger logger = LoggerFactory.getLogger(ClienteTramiteAction.class);
	
	private Map<String,String> params;
	
	private boolean success;
	
	private FlujoVO flujo;
	
	private String message;
	
	@Action(value   = "contratanteTramite",
		    results = {
		        @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
		        @Result(name="success" , location="/jsp-script/proceso/emision/pantallaClienteTramite.jsp")
		    })
	public String contratanteTramite()
	{
		logger.debug(Utils.log(
				 "\n################################"
				,"\n###### contratanteTramite ######"
				,"\n###### flujo=" , flujo
				));
		
		String result = ERROR;
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(flujo, "No se recibieron datos del tr\u00e1mite");
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### result=" , result
				,"\n###### contratanteTramite ######"
				,"\n################################"
				));
		return result;
	}
	
	///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////
	//////////////////////                   //////////////////////
	////////////////////// GETTERS Y SETTERS //////////////////////
	//////////////////////                   //////////////////////
	///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////

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

	public FlujoVO getFlujo() {
		return flujo;
	}

	public void setFlujo(FlujoVO flujo) {
		this.flujo = flujo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}