package mx.com.gseguros.portal.catalogos.controller;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.catalogos.service.PersonasManager;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.utils.Utils;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
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
@Namespace("/persona")
public class PersonaAction extends PrincipalCoreAction
{

	private static final Logger logger = LoggerFactory.getLogger(PersonaAction.class);
	
	private boolean                  success;
	private String                   message;
	private Map<String,String>       params;
	private List<Map<String,String>> list;
	private Map<String,Item>         items;
	
	@Autowired
	private PersonasManager personasManager;
	
	@Actions({
			@Action(value   = "pantallaEspPersona",
		            results = {
		            @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
		            @Result(name="success" , location="/jsp-script/catalogos/pantallaEspPersona.jsp")
		        }),
		    @Action(value   = "includes/pantallaEspPersona",
				    results = {
			        @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
			        @Result(name="success" , location="/jsp-script/catalogos/pantallaEspPersona.jsp")
			})
	})
	public String pantallaPersona()
	{
		logger.debug(Utils.join(
				 "\n#############################"
				,"\n###### pantallaPersona ######"
				,"\n###### params=",params
				));
		
		String result = ERROR;
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			Utils.validate(params                 , "No se recibieron datos");
			Utils.validate(
					params.get("cdperson") , "No se recibi\u00F3 la clave de persona"
					,params.get("origen")  , "No se recibi\u00F3 la clave de origen"
					);
			
			items = personasManager.pantallaPersona(
					params.get("origen")
					,usuario.getRolActivo().getClave()
					,ServletActionContext.getServletContext().getServletContextName()
					);
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.join(
				 "\n###### result=",result
				,"\n###### pantallaPersona ######"
				,"\n#############################"
				));
		return result;
	}
	
	@Action(value   = "guardarPantallaEspPersona",
			results = { @Result(name="success", type="json") }
			)
	public String guardarPantallaEspPersona()
	{
		logger.debug(Utils.join(
				 "\n#######################################"
				,"\n###### guardarPantallaEspPersona ######"
				,"\n###### params=",params
				));
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String[] keys =  new String[]{
					"cdperson"
					,"cdtipide"
					,"cdideper"
					,"dsnombre"
					,"cdtipper"
					,"otfisjur"
					,"otsexo"
					,"fenacimi"
					,"cdrfc"
					,"dsemail"
					,"dsnombre1"
					,"dsapellido"
					,"dsapellido1"
					,"cdnacion"
					,"dscomnom"
					,"dsrazsoc"
					,"feingreso"
					,"feactual"
					,"dsnomusu"
					,"cdestciv"
					,"cdgrueco"
					,"cdstippe"
					,"nmnumnom"
					,"curp"
					,"canaling"
					,"conducto"
					,"ptcumupr"
					,"status"
					,"residencia"
					,"nongrata"
					,"cdideext"
					,"cdsucemi"
			};
			StringBuilder faltantes = new StringBuilder("Faltan los siguientes valores:<br/>");
			boolean       faltan    = false;
			for(String c:keys)
			{
				if(!params.containsKey(c))
				{
					faltantes.append(c).append("<br/>");
					faltan = true;
				}
			}
			if(faltan)
			{
				throw new ApplicationException(faltantes.toString());
			}
			
			message = personasManager.guardarPantallaEspPersona(params);
			
			success = true;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.join(
				 "\n###### guardarPantallaEspPersona ######"
				,"\n#######################################"
				));
		return SUCCESS;
	}
	
	@Action(value   = "recuperarEspPersona",
			results = { @Result(name="success", type="json") }
			)
	public String recuperarEspPersona()
	{
		logger.debug(Utils.join(
				 "\n#################################"
				,"\n###### recuperarEspPersona ######"
				,"\n###### params=",params
				));
		try
		{
			Utils.validateSession(session);
			Utils.validate(params                 , "No se recibieron datos");
			Utils.validate(params.get("cdperson") , "No se recibi\u00F3 la clave de persona");
			
			params.putAll(personasManager.recuperarEspPersona(params.get("cdperson")));
			
			success = true;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.join(
				 "\n###### params=",params
				,"\n###### recuperarEspPersona ######"
				,"\n#################################"
				));
		return SUCCESS;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public Map<String, Item> getItems() {
		return items;
	}

	public void setItems(Map<String, Item> items) {
		this.items = items;
	}
	
}