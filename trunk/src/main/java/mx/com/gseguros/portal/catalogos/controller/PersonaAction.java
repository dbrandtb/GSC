package mx.com.gseguros.portal.catalogos.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal2.web.GenericVO;
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

	private static final long serialVersionUID = 2211193078664526450L;

	private static final Logger logger = LoggerFactory.getLogger(PersonaAction.class);
	
	private boolean                  success;
	private String                   message;
	private Map<String,String>       params;
	private List<Map<String,String>> list;
	private Map<String,Item>         items;
	private List<GenericVO> 		genericVO;
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
		logger.debug(Utils.log(
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
		
		logger.debug(Utils.log(
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
		logger.debug(Utils.log(
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
		
		logger.debug(Utils.log(
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
		logger.debug(Utils.log(
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
		
		logger.debug(Utils.log(
				 "\n###### params=",params
				,"\n###### recuperarEspPersona ######"
				,"\n#################################"
				));
		return SUCCESS;
	}
	
	@Action(value   = "guardaClienteNonGratos",
		results = { @Result(name="success", type="json") }
	)
	public String guardaClienteNonGratos()
	{
		logger.debug(Utils.log(
				 "\n#######################################"
				,"\n###### guardaClienteNonGratos ######"
				,"\n###### params=",params
				));
		
		try
		{
			Utils.validateSession(session);
			Utils.validate(params                 , "No se recibieron datos");
			String cduser = ((UserVO)session.get("USUARIO")).getUser();
			Date   fechaProcesamiento = new Date();
			//message = personasManager.guardarClienteNonGratos(paramsCliente, params.get("cdrfc"));
			message = personasManager.guardarClienteNonGratos(params.get("cdrfc"),params.get("status"),params.get("cdtipper"),params.get("agente"),
					params.get("dsnombre"),params.get("dsdomicil"), params.get("obsermot"), cduser, fechaProcesamiento, params.get("accion"));
			success = true;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### guardaClienteNonGratos ######"
				,"\n#######################################"
				));
		return SUCCESS;
	}
	
	@Action(value = "obtieneListaClientesNonGratos", results = { @Result(name = "success", type = "json", params = {
			"ignoreHierarchy", "false", "includeProperties",
			"list.*,success" }) })
		public String obtieneListaClientesNonGratos()
		{
			logger.debug(Utils.log(
					 "\n###########################################"
					,"\n###### obtieneListaClientesNonGratos ######"
					,"\n###### params=",params
					));
			
			try
			{
				//Utils.validateSession(session);
				//Utils.validate(params                 , "No se recibieron datos");
				list = personasManager.obtieneListaClientesNonGratos(params.get("cdrfc"));
				success = true;
			}
			catch(Exception ex)
			{
				message = Utils.manejaExcepcion(ex);
			}
			
			logger.debug(Utils.log(
					 "\n###### obtieneListaClientesNonGratos ######"
					,"\n###########################################"
					));
			return SUCCESS;
		}
	
	@Action(value = "consultaClientesNonGratos", results = { @Result(name = "success", type = "json", params = {
			"ignoreHierarchy", "false", "includeProperties",
			"genericVO.*,success" }) })
		public String consultaClientesNonGratos()
		{
			logger.debug(Utils.log(
					 "\n###########################################"
					,"\n###### obtieneListaClientesNonGratos ######"
					,"\n###### params=",params
					));
			
			try
			{
				genericVO = personasManager.consultaClientesNonGratos(params.get("cdperson"));
				success = true;
			}
			catch(Exception ex)
			{
				message = Utils.manejaExcepcion(ex);
			}
			
			logger.debug(Utils.log(
					 "\n###### obtieneListaClientesNonGratos ######"
					,"\n###########################################"
					));
			return SUCCESS;
		}
	
	@Actions({
		@Action(value   = "administracionClientes",
	            results = {
	            @Result(name="success" , location="/jsp-script/proceso/siniestros/administracionClientes.jsp")
	        })
	})
	public String administracionClientes()
	{
		logger.debug(Utils.log(
				 "\n#############################"
				,"\n###### pantallaPersona ######"
				,"\n###### params=",params
				));
		
		String result = ERROR;
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### result=",result
				,"\n###### pantallaPersona ######"
				,"\n#############################"
				));
		return result;
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

	public List<GenericVO> getGenericVO() {
		return genericVO;
	}

	public void setGenericVO(List<GenericVO> genericVO) {
		this.genericVO = genericVO;
	}
	
}