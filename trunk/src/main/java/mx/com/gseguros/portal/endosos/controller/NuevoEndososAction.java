package mx.com.gseguros.portal.endosos.controller;

import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.mesacontrol.model.FlujoVO;
import mx.com.gseguros.portal.endosos.service.EndososAutoManager;
import mx.com.gseguros.utils.Utils;

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
@ParentPackage(value="default")
@Namespace("/endosos")
public class NuevoEndososAction extends PrincipalCoreAction
{
	private static final Logger logger = LoggerFactory.getLogger(NuevoEndososAction.class);
	
	private boolean success;
	
	private String message;
	
	private Map<String,String> params;
	
	private FlujoVO flujo;
	
	@Autowired
	private EndososAutoManager endososAutoManager;
	
	@Action(value   = "sacaEndosoFlujo",
			results = { @Result(name="success", type="json") }
			)
	public String sacaEndosoFlujo()
	{
		logger.debug(Utils.log(
				 "\n#############################"
				,"\n###### sacaEndosoFlujo ######"
				,"\n###### flujo = " , flujo
				));
		
		try
		{
			Utils.validate(flujo , "No se recibieron datos del flujo");
			
			endososAutoManager.sacaEndosoFlujo(flujo);
			
			message = "Se ha revertido el endoso";
			success = true;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### sacaEndosoFlujo ######"
				,"\n#############################"
				));
		return SUCCESS;
	}

	@Action(value   = "endosoAltaAsegurados",
		    results = {
		        @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
		        @Result(name="success" , location="/jsp-script/proceso/endosos/endosoAltaAsegurados.jsp")
		    })
	public String endosoAltaAsegurados()
	{
		logger.debug(Utils.log(
				 "\n##################################"
				,"\n###### endosoAltaAsegurados ######"
				,"\n###### params = " , params
				,"\n###### flujo  = " , flujo
				));
		
		String result = ERROR;
		
		try
		{
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### result  = " , result
				,"\n###### message = " , message
				,"\n###### endosoAltaAsegurados ######"
				,"\n##################################"
				));
		
		return result;
	}
	
	@Action(value   = "validacionSigsAgente",
			results = { @Result(name="success", type="json") }
			)
	public String validacionSigsAgente()
	{
		logger.debug(Utils.log(
				 "\n###################################"
				,"\n###### validacionSigsAgente ######"
				,"\n###### params = ", params
				));
		
		try
		{
			Utils.validate(params, "No se recibieron datos");
			
			String cdagente  = params.get("cdagente"),
					cdramo   = params.get("cdramo"),
					cdtipsit = params.get("cdtipsit"),
					cdtipend = params.get("cdtipend");
			
			Utils.validate(
					cdagente , "Falta cdagente",
					cdramo   , "Falta cdramo",
					cdtipsit , "Falta cdtipsit",
					cdtipend , "Falta cdtipend"
					);
			
			if (!"B".equals(cdtipend)) {
				endososAutoManager.validacionSigsAgente(cdagente, cdramo, cdtipsit, cdtipend);
			} else {
				logger.debug("No aplica validacion de agente para tipo B");
			}
			
			success = true;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		logger.debug(Utils.log(
				"\n###### success = ", success,
				"\n###### message = ", message,
				"\n###### validacionSigsAgente ######",
				"\n##################################"
				));
		return SUCCESS;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////// GETTERS Y SETTERS //////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean isSuccess() {
		return success;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setSuccess(boolean success) {
		this.success = success;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getMessage() {
		return message;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setMessage(String message) {
		this.message = message;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	public Map<String, String> getParams() {
		return params;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	public FlujoVO getFlujo() {
		return flujo;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setFlujo(FlujoVO flujo) {
		this.flujo = flujo;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////// GETTERS Y SETTERS //////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
}