package mx.com.gseguros.mesacontrol.controller;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.mesacontrol.model.TramiteVO;
import mx.com.gseguros.mesacontrol.service.FlujoMesaControlManager;
import mx.com.gseguros.portal.cotizacion.model.Item;
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
@Namespace("/flujomesacontrol")
public class FlujoMesaControlAction extends PrincipalCoreAction
{
	private static final long serialVersionUID = 4896753376957054283L;
	private static Logger     logger           = LoggerFactory.getLogger(FlujoMesaControlAction.class);
	
	private boolean                  success;
	private String                   message;
	private Map<String,Item>         items;
	private TramiteVO                tramite;
	private Map<String,String>       params;
	private List<Map<String,String>> list;
	
	@Autowired
	private FlujoMesaControlManager flujoMesaControlManager;
	
	@Action(value   = "workflow",
	        results = {
			    @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
                @Result(name="success" , location="/jsp-script/proceso/flujoMesaControl/workflow.jsp")
            }
	)
	public String workflow()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n######################"
				,"\n###### workflow ######"
				));
		
		String result = ERROR;
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			items = flujoMesaControlManager.workflow(
					sb
					,usuario.getRolActivo().getClave()
					);
			
			result = SUCCESS;
			
			sb.append(Utils.log(
					 "\n###### result=",result
					,"\n###### workflow ######"
					,"\n######################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return result;
	}
	
	@Action(value   = "jsplumb",
	        results = {
			    @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
                @Result(name="success" , location="/jsp-script/proceso/flujoMesaControl/jsplumb.jsp")
            }
	)
	public String jsplumb()
	{
		logger.debug(Utils.log(
				 "\n#####################"
				,"\n###### jsplumb ######"
				,"\n#####################"
				));
		return SUCCESS;
	}
	
	@Action(value   = "registrarEntidad",
			results = { @Result(name="success", type="json") }
			)
	public String registrarEntidad()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n##############################"
				,"\n###### registrarEntidad ######"
				,"\n###### params=",params
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String cdtipflu   = params.get("cdtipflu")
			       ,cdflujomc = params.get("cdflujomc")
			       ,tipo      = params.get("tipo")
			       ,clave     = params.get("clave")
			       ,webid     = params.get("webid")
			       ,xpos      = params.get("xpos")
			       ,ypos      = params.get("ypos");
			
			Utils.validate(
					cdtipflu   , "No se recibi\u00f3 el tipo de flujo"
					,cdflujomc , "No se recibi\u00f3 el flujo"
					,tipo      , "No se recibi\u00f3 el tipo de entidad"
					,clave     , "No se recibi\u00f3 la clave de entidad"
					,webid     , "No se recibi\u00f3 el id de entidad"
					,xpos      , "No se recibi\u00f3 x de entidad"
					,ypos      , "No se recibi\u00f3 y de entidad"
					);
			
			params.put("consecutivo" , "9");
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### registrarEntidad ######"
					,"\n##############################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return SUCCESS;
	}
	
	@Action(value   = "borrarEntidad",
			results = { @Result(name="success", type="json") }
			)
	public String borrarEntidad()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n###########################"
				,"\n###### borrarEntidad ######"
				,"\n###### params=",params
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String cdtipflu   = params.get("cdtipflu")
			       ,cdflujomc = params.get("cdflujomc")
			       ,tipo      = params.get("tipo")
			       ,webid     = params.get("webid");
			
			Utils.validate(
					cdtipflu   , "No se recibi\u00f3 el tipo de flujo"
					,cdflujomc , "No se recibi\u00f3 el flujo"
					,tipo      , "No se recibi\u00f3 el tipo de entidad"
					,webid     , "No se recibi\u00f3 el id de entidad"
					);
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### borrarEntidad ######"
					,"\n###########################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return SUCCESS;
	}
	
	@Action(value   = "registrarConnection",
			results = { @Result(name="success", type="json") }
			)
	public String registrarConnection()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n#################################"
				,"\n###### registrarConnection ######"
				,"\n###### params=",params
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String cdtipflu   = params.get("cdtipflu")
			       ,cdflujomc = params.get("cdflujomc")
			       ,idorigen  = params.get("idorigen")
			       ,iddestin  = params.get("iddestin");
			
			Utils.validate(
					cdtipflu   , "No se recibi\u00f3 el tipo de flujo"
					,cdflujomc , "No se recibi\u00f3 el flujo"
					,idorigen  , "No se recibi\u00f3 el origen"
					,iddestin  , "No se recibi\u00f3 el destino"
					);
			
			params.put("consecutivo" , "9");
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### registrarConnection ######"
					,"\n#################################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return SUCCESS;
	}
	
	@Action(value   = "borrarConnection",
			results = { @Result(name="success", type="json") }
			)
	public String borrarConnection()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n##############################"
				,"\n###### borrarConnection ######"
				,"\n###### params=",params
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String cdtipflu   = params.get("cdtipflu")
			       ,cdflujomc = params.get("cdflujomc")
			       ,idorigen  = params.get("idorigen")
			       ,iddestin  = params.get("iddestin");
			
			Utils.validate(
					cdtipflu   , "No se recibi\u00f3 el tipo de flujo"
					,cdflujomc , "No se recibi\u00f3 el flujo"
					,idorigen  , "No se recibi\u00f3 el origen"
					,iddestin  , "No se recibi\u00f3 el destino"
					);
			
			params.put("consecutivo" , "9");
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### borrarConnection ######"
					,"\n##############################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return SUCCESS;
	}

	////////////////////////////////////////////////////////
	// GETTERS Y SETTERS                                  //
	                                                      //
	public boolean isSuccess() {                          //
		return success;                                   //
	}                                                     //
                                                          //
	public void setSuccess(boolean success) {             //
		this.success = success;                           //
	}                                                     //
                                                          //
	public String getMessage() {                          //
		return message;                                   //
	}                                                     //
                                                          //
	public void setMessage(String message) {              //
		this.message = message;                           //
	}                                                     //
                                                          //
	public Map<String, Item> getItems() {                 //
		return items;                                     //
	}                                                     //
                                                          //
	public void setItems(Map<String, Item> items) {       //
		this.items = items;                               //
	}                                                     //
                                                          //
	public TramiteVO getTramite() {                       //
		return tramite;                                   //
	}                                                     //
	                                                      //
	public void setTramite(TramiteVO tramite) {           //
		this.tramite = tramite;                           //
	}                                                     //
	                                                      //
	public Map<String, String> getParams() {              //
		return params;                                    //
	}                                                     //
	                                                      //
	public void setParams(Map<String, String> params) {   //
		this.params = params;                             //
	}                                                     //
	                                                      //
	public List<Map<String, String>> getList() {          //
		return list;                                      //
	}                                                     //
	                                                      //
	public void setList(List<Map<String, String>> list) { //
		this.list = list;                                 //
	}                                                     //
	                                                      //
	////////////////////////////////////////////////////////
	
}