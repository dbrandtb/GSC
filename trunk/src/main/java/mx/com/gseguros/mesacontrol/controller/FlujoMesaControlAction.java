package mx.com.gseguros.mesacontrol.controller;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.mesacontrol.model.AgrupadorMC;
import mx.com.gseguros.mesacontrol.model.FlujoVO;
import mx.com.gseguros.mesacontrol.service.FlujoMesaControlManager;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.utils.Utils;

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

import com.opensymphony.xwork2.ActionContext;

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
	private FlujoVO                  flujo;
	private Map<String,String>       params;
	private Map<String,Object>       datosTramite;
	private List<Map<String,String>> list;
	private int                      start
	                                 ,limit
	                                 ,total;
	
	public FlujoMesaControlAction()
	{
		this.session=ActionContext.getContext().getSession();
	}
	
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
			
			String cdentidad = flujoMesaControlManager.registrarEntidad(
					sb
					,cdtipflu
					,cdflujomc
					,tipo
					,clave
					,webid
					,xpos
					,ypos
					);
			
			params.put("cdentidad" , cdentidad);
			
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
			       ,clave     = params.get("clave")
			       ,webid     = params.get("webid");
			
			Utils.validate(
					cdtipflu   , "No se recibi\u00f3 el tipo de flujo"
					,cdflujomc , "No se recibi\u00f3 el flujo"
					,tipo      , "No se recibi\u00f3 el tipo de entidad"
					,clave     , "No se recibi\u00f3 la clave de entidad"
					,webid     , "No se recibi\u00f3 el id de entidad"
					);
			
			flujoMesaControlManager.borrarEntidad(
					sb
					,cdtipflu
					,cdflujomc
					,tipo
					,clave
					,webid
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
			
			String cdaccion = flujoMesaControlManager.registrarConnection(
					sb
					,cdtipflu
					,cdflujomc
					,idorigen
					,iddestin
					);
			
			params.put("cdaccion" , cdaccion);
			
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
			       ,cdaccion  = params.get("cdaccion");
			
			Utils.validate(
					cdtipflu   , "No se recibi\u00f3 el tipo de flujo"
					,cdflujomc , "No se recibi\u00f3 el flujo"
					,cdaccion  , "No se recibi\u00f3 la clave"
					);
			
			flujoMesaControlManager.borrarConnection(
					sb
					,cdtipflu
					,cdflujomc
					,cdaccion
					);
			
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
	
	@Action(value   = "movimientoTtipflumc",
			results = { @Result(name="success", type="json") }
			)
	public String movimientoTtipflumc()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n#################################"
				,"\n###### movimientoTtipflumc ######"
				,"\n###### params=",params
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String accion      = params.get("ACCION")
			       ,cdtipflu   = params.get("CDTIPFLU")
			       ,dstipflu   = params.get("DSTIPFLU")
			       ,cdtiptra   = params.get("CDTIPTRA")
			       ,swreqpol   = params.get("SWREQPOL")
			       ,swmultipol = params.get("SWMULTIPOL")
			       ,cdtipsup   = params.get("CDTIPSUP");
			
			Utils.validate(
					accion     , "No se recibi\u00f3 la acci\u00f3n"
					,dstipflu  , "No se recibi\u00f3 el nombre"
					,cdtiptra  , "No se recibi\u00f3 el tipo de tr\u00e1mite"
					);
			
			flujoMesaControlManager.movimientoTtipflumc(
					sb
					,accion
					,cdtipflu
					,dstipflu
					,cdtiptra
					,swreqpol
					,swmultipol
					,cdtipsup
					);
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### movimientoTtipflumc ######"
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
	
	@Action(value   = "movimientoTflujomc",
			results = { @Result(name="success", type="json") }
			)
	public String movimientoTflujomc()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n################################"
				,"\n###### movimientoTflujomc ######"
				,"\n###### params=",params
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String accion     = params.get("ACCION")
			       ,cdtipflu  = params.get("CDTIPFLU")
			       ,cdflujomc = params.get("CDFLUJOMC")
			       ,dsflujomc = params.get("DSFLUJOMC")
			       ,swfinal   = params.get("SWFINAL")
			       ,cdtipram  = params.get("CDTIPRAM");
			
			Utils.validate(
					accion     , "No se recibi\u00f3 la acci\u00f3n"
					,cdtipflu  , "No se recibi\u00f3 el padre"
					,dsflujomc , "No se recibi\u00f3 el nombre"
					,cdtipram  , "No se recibi\u00f3 el tipo de ramo"
					);
			
			flujoMesaControlManager.movimientoTflujomc(
					sb
					,accion
					,cdtipflu
					,cdflujomc
					,dsflujomc
					,swfinal
					,cdtipram
					);
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### movimientoTflujomc ######"
					,"\n################################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return SUCCESS;
	}
	
	@Action(value   = "movimientoCatalogo",
			results = { @Result(name="success", type="json") }
			)
	public String movimientoCatalogo()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n################################"
				,"\n###### movimientoCatalogo ######"
				,"\n###### params=",params
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String accion = params.get("ACCION")
			       ,tipo  = params.get("tipo");
			
			Utils.validate(
					accion , "No se recibi\u00f3 la acci\u00f3n"
					,tipo  , "No se recibi\u00f3 el tipo"
					);
			
			flujoMesaControlManager.movimientoCatalogo(
					sb
					,accion
					,tipo
					,params
					);
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### movimientoCatalogo ######"
					,"\n################################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return SUCCESS;
	}
	
	@Action(value   = "cargarModelado",
			results = { @Result(name="success", type="json") }
			)
	public String cargarModelado()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n############################"
				,"\n###### cargarModelado ######"
				,"\n###### params=",params
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String cdtipflu   = params.get("cdtipflu")
				   ,cdflujomc = params.get("cdflujomc");
			
			Utils.validate(
					cdtipflu   , "No se recibi\u00f3 el tipo de flujo"
					,cdflujomc , "No se recibi\u00f3 la clave de flujo"
					);
			
			list = flujoMesaControlManager.cargarModelado(
					sb
					,cdtipflu
					,cdflujomc
					);
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### cargarModelado ######"
					,"\n############################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return SUCCESS;
	}
	
	@Action(value   = "cargarDatosEstado",
			results = { @Result(name="success", type="json") }
			)
	public String cargarDatosEstado()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n###############################"
				,"\n###### cargarDatosEstado ######"
				,"\n###### params=",params
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String cdtipflu    = params.get("cdtipflu")
				   ,cdflujomc  = params.get("cdflujomc")
				   ,cdestadomc = params.get("cdestadomc");
			
			Utils.validate(
					cdtipflu    , "No se recibi\u00f3 el tipo de flujo"
					,cdflujomc  , "No se recibi\u00f3 la clave de flujo"
					,cdestadomc , "No se recibi\u00f3 la clave de status"
					);
			
			Map<String,Object> res = flujoMesaControlManager.cargarDatosEstado(
					sb
					,cdtipflu
					,cdflujomc
					,cdestadomc
					);
			
			params = (Map<String,String>)res.get("mapa");
			list   = (List<Map<String,String>>)res.get("lista");
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### cargarDatosEstado ######"
					,"\n###############################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return SUCCESS;
	}
	
	@Action(value           = "guardarDatosEstado",
			results         = { @Result(name="success", type="json") },
            interceptorRefs = {
			    @InterceptorRef(value = "json", params = {"enableSMD", "true", "ignoreSMDMethodInterfaces", "false" })
			})
	public String guardarDatosEstado()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n################################"
				,"\n###### guardarDatosEstado ######"
				,"\n###### params=" , params
				,"\n###### list="   , list
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			Utils.validate(list , "No se recibieron permisos ni avisos");
			
			String cdtipflu    = params.get("CDTIPFLU")
			       ,cdflujomc  = params.get("CDFLUJOMC")
			       ,cdestadomc = params.get("CDESTADOMC")
			       ,accion     = params.get("ACCION")
			       ,webid      = params.get("WEBID")
			       ,xpos       = params.get("XPOS")
			       ,ypos       = params.get("YPOS")
			       ,timemaxh   = params.get("TIMEMAXH")
			       ,timemaxm   = params.get("TIMEMAXM")
			       ,timewrn1h  = params.get("TIMEWRN1H")
			       ,timewrn1m  = params.get("TIMEWRN1M")
			       ,timewrn2h  = params.get("TIMEWRN2H")
			       ,timewrn2m  = params.get("TIMEWRN2M")
			       ,cdtipasig  = params.get("CDTIPASIG")
			       ,swescala   = params.get("SWESCALA")
			       ;
			
			Utils.validate(
					cdtipflu    , "No se recibi\u00f3 el tipo de flujo"
					,cdflujomc  , "No se recibi\u00f3 la clave de flujo"
					,cdestadomc , "No se recibi\u00f3 la clave de status"
					,accion     , "No se recibi\u00f3 la operaci\u00f3n"
					,webid      , "No se recibi\u00f3 el id"
					,xpos       , "No se recibi\u00f3 x"
					,ypos       , "No se recibi\u00f3 y"
					,timemaxh   , "No se recibi\u00f3 horas max"
					,timemaxm   , "No se recibi\u00f3 minutos max"
					,timewrn1h  , "No se recibi\u00f3 horas max alerta 1"
					,timewrn1m  , "No se recibi\u00f3 minutos max alerta 1"
					,timewrn2h  , "No se recibi\u00f3 horas max alerta 2"
					,timewrn2m  , "No se recibi\u00f3 minutos max alerta 2"
					,cdtipasig  , "No se recibi\u00f3 tipo de asignaci\u00f3n"
					);
			
			flujoMesaControlManager.guardarDatosEstado(
					sb
					,cdtipflu
					,cdflujomc
					,cdestadomc
					,accion
					,webid
					,xpos
					,ypos
					,timemaxh
					,timemaxm
					,timewrn1h
					,timewrn1m
					,timewrn2h
					,timewrn2m
					,cdtipasig
					,swescala
					,list
					);
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### guardarDatosEstado ######"
					,"\n################################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return SUCCESS;
	}
	
	@Action(value   = "cargarDatosValidacion",
			results = { @Result(name="success", type="json") }
			)
	public String cargarDatosValidacion()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n###################################"
				,"\n###### cargarDatosValidacion ######"
				,"\n###### params=",params
				));
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String cdtipflu   = params.get("cdtipflu")
				   ,cdflujomc = params.get("cdflujomc")
				   ,cdvalida  = params.get("cdvalida");
			
			Utils.validate(
					cdtipflu   , "No se recibi\u00f3 el tipo de flujo"
					,cdflujomc , "No se recibi\u00f3 la clave de flujo"
					,cdvalida  , "No se recibi\u00f3 la clave de validaci\u00f3n"
					);
			
			Map<String,String> res = flujoMesaControlManager.cargarDatosValidacion(
					sb
					,cdtipflu
					,cdflujomc
					,cdvalida
					);
			
			params.putAll(res);
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### cargarDatosValidacion ######"
					,"\n###################################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return SUCCESS;
	}
	
	@Action(value   = "guardarDatosValidacion",
			results = { @Result(name="success", type="json") }
			)
	public String guardarDatosValidacion()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n####################################"
				,"\n###### guardarDatosValidacion ######"
				,"\n###### params=",params
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String cdtipflu    = params.get("CDTIPFLU")
			       ,cdflujomc  = params.get("CDFLUJOMC")
			       ,cdvalida   = params.get("CDVALIDA")
			       ,webid      = params.get("WEBID")
			       ,xpos       = params.get("XPOS")
			       ,ypos       = params.get("YPOS")
			       ,dsvalida   = params.get("DSVALIDA")
			       ,cdvalidafk = params.get("CDVALIDAFK")
			       ,jsvalida   = params.get("JSVALIDA")
			       ,accion     = params.get("ACCION");
			
			Utils.validate(
					cdtipflu    , "No se recibi\u00f3 el tipo de flujo"
					,cdflujomc  , "No se recibi\u00f3 la clave de flujo"
					,cdvalida   , "No se recibi\u00f3 la clave de validaci\u00f3n"
					,webid      , "No se recibi\u00f3 el id"
					,xpos       , "No se recibi\u00f3 x"
					,ypos       , "No se recibi\u00f3 y"
					,dsvalida   , "No se recibi\u00f3 el nombre"
					,cdvalidafk , "No se recibi\u00f3 la validaci\u00f3n"
					,accion     , "No se recibi\u00f3 el tipo de operaci\u00f3n"
					);
			
			flujoMesaControlManager.guardarDatosValidacion(
					sb
					,cdtipflu
					,cdflujomc
					,cdvalida
					,webid
					,xpos
					,ypos
					,dsvalida
					,cdvalidafk
					,jsvalida
					,accion
					);
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### guardarDatosValidacion ######"
					,"\n####################################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return SUCCESS;
	}
	
	@Action(value           = "guardarCoordenadas",
			results         = { @Result(name="success", type="json") },
            interceptorRefs = {
			    @InterceptorRef(value = "json", params = {"enableSMD", "true", "ignoreSMDMethodInterfaces", "false" })
			})
	public String guardarCoordenadas()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n################################"
				,"\n###### guardarCoordenadas ######"
				,"\n###### params=" , params
				,"\n###### list="   , list
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params, "No se recibieron datos");
			
			String cdtipflu   = params.get("cdtipflu")
			       ,cdflujomc = params.get("cdflujomc");
			
			Utils.validate(list, "No se recibieron entidades");
			
			flujoMesaControlManager.guardarCoordenadas(
					sb
					,cdtipflu
					,cdflujomc
					,list
					);
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### guardarCoordenadas ######"
					,"\n################################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return SUCCESS;
	}
	
	@Action(value   = "ejecutaValidacion",
			results = { @Result(name="success", type="json") }
			)
	public String ejecutaValidacion()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n###############################"
				,"\n###### ejecutaValidacion ######"
				,"\n###### flujo="  , flujo
				,"\n###### params=" , params
				));
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(flujo  , "No se recibieron datos del flujo");
			Utils.validate(params , "No se recibieron par\u00e1metros");
			
			String cdvalidafk = params.get("cdvalidafk");
			
			Utils.validate(cdvalidafk , "No se recibi\u00f3 clave de validaci\u00f3n");
			
			params.put("salida" , flujoMesaControlManager.ejecutaValidacion(
					sb
					,flujo
					,cdvalidafk
					));
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### params=" , params
					,"\n###### ejecutaValidacion ######"
					,"\n###############################"
					));
			
			logger.debug(sb.toString());
			
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		return SUCCESS;
	}
	
	@Action(value   = "cargarDatosRevision",
			results = { @Result(name="success", type="json") }
			)
	public String cargarDatosRevision()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n#################################"
				,"\n###### cargarDatosRevision ######"
				,"\n###### params=",params
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String cdtipflu   = params.get("cdtipflu")
				   ,cdflujomc = params.get("cdflujomc")
				   ,cdrevisi  = params.get("cdrevisi");
			
			Utils.validate(
					cdtipflu   , "No se recibi\u00f3 el tipo de flujo"
					,cdflujomc , "No se recibi\u00f3 la clave de flujo"
					,cdrevisi  , "No se recibi\u00f3 la clave de revisi\u00f3n"
					);
			
			Map<String,Object> res = flujoMesaControlManager.cargarDatosRevision(
					sb
					,cdtipflu
					,cdflujomc
					,cdrevisi
					);
			
			params = (Map<String,String>)res.get("mapa");
			list   = (List<Map<String,String>>)res.get("lista");
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### cargarDatosRevision ######"
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
	
	@Action(value           = "guardarDatosRevision",
			results         = { @Result(name="success", type="json") },
            interceptorRefs = {
			    @InterceptorRef(value = "json", params = {"enableSMD", "true", "ignoreSMDMethodInterfaces", "false" })
			})
	public String guardarDatosRevision()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n##################################"
				,"\n###### guardarDatosRevision ######"
				,"\n###### params=" , params
				,"\n###### list="   , list
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			Utils.validate(list , "No se recibieron documentos");
			
			String cdtipflu   = params.get("CDTIPFLU")
			       ,cdflujomc = params.get("CDFLUJOMC")
			       ,cdrevisi  = params.get("CDREVISI")
			       ,dsrevisi  = params.get("DSREVISI")
			       ,accion    = params.get("ACCION")
			       ,webid     = params.get("WEBID")
			       ,xpos      = params.get("XPOS")
			       ,ypos      = params.get("YPOS")
			       ;
			
			Utils.validate(
					cdtipflu   , "No se recibi\u00f3 el tipo de flujo"
					,cdflujomc , "No se recibi\u00f3 la clave de flujo"
					,cdrevisi  , "No se recibi\u00f3 la clave de revisi\u00f3n"
					,dsrevisi  , "No se recibi\u00f3 el nombre de revisi\u00f3n"
					,accion    , "No se recibi\u00f3 la operaci\u00f3n"
					,webid     , "No se recibi\u00f3 el id"
					,xpos      , "No se recibi\u00f3 x"
					,ypos      , "No se recibi\u00f3 y"
					);
			
			flujoMesaControlManager.guardarDatosRevision(
					sb
					,cdtipflu
					,cdflujomc
					,cdrevisi
					,dsrevisi
					,accion
					,webid
					,xpos
					,ypos
					,list
					);
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### guardarDatosRevision ######"
					,"\n##################################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return SUCCESS;
	}
	
	@Action(value   = "movimientoTdocume",
			results = { @Result(name="success", type="json") }
			)
	public String movimientoTdocume()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n###############################"
				,"\n###### movimientoTdocume ######"
				,"\n###### params=",params
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String accion    = params.get("ACCION")
			       ,cddocume = params.get("CDDOCUME")
			       ,dsdocume = params.get("DSDOCUME")
			       ,cdtiptra = params.get("CDTIPTRA");
			
			Utils.validate(
					accion    , "No se recibi\u00f3 la acci\u00f3n"
					,dsdocume , "No se recibi\u00f3 el nombre"
					,cdtiptra , "No se recibi\u00f3 el tipo de tr\u00e1mite"
					);
			
			flujoMesaControlManager.movimientoTdocume(
					sb
					,accion
					,cddocume
					,dsdocume
					,cdtiptra
					);
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### movimientoTdocume ######"
					,"\n###############################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return SUCCESS;
	}
	
	@Action(value   = "cargarDatosAccion",
			results = { @Result(name="success", type="json") }
			)
	public String cargarDatosAccion()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n###############################"
				,"\n###### cargarDatosAccion ######"
				,"\n###### params=",params
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String cdtipflu   = params.get("cdtipflu")
				   ,cdflujomc = params.get("cdflujomc")
				   ,cdaccion  = params.get("cdaccion");
			
			Utils.validate(
					cdtipflu   , "No se recibi\u00f3 el tipo de flujo"
					,cdflujomc , "No se recibi\u00f3 la clave de flujo"
					,cdaccion  , "No se recibi\u00f3 la clave de acci\u00f3n"
					);
			
			Map<String,Object> res = flujoMesaControlManager.cargarDatosAccion(
					sb
					,cdtipflu
					,cdflujomc
					,cdaccion
					);
			
			params = (Map<String,String>)res.get("mapa");
			list   = (List<Map<String,String>>)res.get("lista");
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### cargarDatosAccion ######"
					,"\n###############################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return SUCCESS;
	}
	
	@Action(value           = "guardarDatosAccion",
			results         = { @Result(name="success", type="json") },
            interceptorRefs = {
			    @InterceptorRef(value = "json", params = {"enableSMD", "true", "ignoreSMDMethodInterfaces", "false" })
			})
	public String guardarDatosAccion()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n################################"
				,"\n###### guardarDatosAccion ######"
				,"\n###### params=" , params
				,"\n###### list="   , list
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			Utils.validate(list , "No se recibieron permisos");
			
			String cdtipflu   = params.get("CDTIPFLU")
			       ,cdflujomc = params.get("CDFLUJOMC")
			       ,cdaccion  = params.get("CDACCION")
			       ,dsaccion  = params.get("DSACCION")
			       ,accion    = params.get("ACCION")
			       ,idorigen  = params.get("IDORIGEN")
			       ,iddestin  = params.get("IDDESTIN")
			       ,cdvalor   = params.get("CDVALOR")
			       ,cdicono   = params.get("CDICONO")
			       ,swescala  = params.get("SWESCALA")
			       ,aux       = params.get("AUX");
			
			Utils.validate(
					cdtipflu   , "No se recibi\u00f3 el tipo de flujo"
					,cdflujomc , "No se recibi\u00f3 la clave de flujo"
					,cdaccion  , "No se recibi\u00f3 la clave de acci\u00f3n"
					,dsaccion  , "No se recibi\u00f3 el nombre de acci\u00f3n"
					,accion    , "No se recibi\u00f3 la operaci\u00f3n"
					,idorigen  , "No se recibi\u00f3 el origen"
					,iddestin  , "No se recibi\u00f3 el destino"
					);
			
			flujoMesaControlManager.guardarDatosAccion(
					sb
					,cdtipflu
					,cdflujomc
					,cdaccion
					,dsaccion
					,accion
					,idorigen
					,iddestin
					,cdvalor
					,cdicono
					,swescala
					,aux
					,list
					);
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### guardarDatosAccion ######"
					,"\n################################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return SUCCESS;
	}
	
	@Action(value   = "debugScreen",
	        results = {
			    @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
                @Result(name="success" , location="/jsp-script/proceso/flujoMesaControl/debugScreen.jsp")
            }
	)
	public String debugScreen()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n#########################"
				,"\n###### debugScreen ######"
				));
		String result = ERROR;
		try
		{
			items = flujoMesaControlManager.debugScreen(sb);
			
			result = SUCCESS;
			
			sb.append(Utils.log(
					 "\n###### debugScreen ######"
					,"\n#########################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		return result;
	}
	
	@Action(value   = "mesaControl",
	        results = {
			    @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
                @Result(name="success" , location="/jsp-script/proceso/flujoMesaControl/mesaControl.jsp")
            }
	)
	public String mesaControl()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n#########################"
				,"\n###### mesaControl ######"
				));
		String result = ERROR;
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String agrupamc = params.get("AGRUPAMC");
			Utils.validate(agrupamc, "No se recibi\u00f3 el agrupador");
			
			try
			{
				AgrupadorMC agrupador = AgrupadorMC.valueOf(agrupamc);
			}
			catch(Exception ex)
			{
				throw new ApplicationException("No se reconoce el agrupador");
			}
			
			Map<String,Object> manRes = flujoMesaControlManager.mesaControl(
					sb
					,usuario.getRolActivo().getClave()
					,agrupamc
					,usuario.getUser()
					);
			
			items = (Map<String,Item>)manRes.get("items");
			params.putAll((Map<String,String>)manRes.get("mapa"));
			
			params.put("CDUSUARI" , usuario.getUser());
			params.put("CDSISROL" , usuario.getRolActivo().getClave());
			
			result = SUCCESS;
			
			sb.append(Utils.log(
					 "\n###### mesaControl ######"
					,"\n#########################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		return result;
	}
	
	@Action(value   = "recuperarTramites",
			results = { @Result(name="success", type="json") }
			)
	public String recuperarTramites()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n###############################"
				,"\n###### recuperarTramites ######"
				,"\n###### params=" , params
				,"\n###### start="  , start
				,"\n###### limit="  , limit
				));
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			Utils.validate(params, "No se recibieron datos");
			
			//obligatorios
			String agrupamc = params.get("AGRUPAMC")
			       ,status  = params.get("STATUS");
			
			//opcionales
			String cdunieco  = params.get("CDUNIECO")
			       ,cdramo   = params.get("CDRAMO")
			       ,cdtipsit = params.get("CDTIPSIT")
			       ,estado   = params.get("ESTADO")
			       ,nmpoliza = params.get("NMPOLIZA")
			       ,cdagente = params.get("CDAGENTE")
			       ,ntramite = params.get("NTRAMITE")
			       ,fedesde  = params.get("FEDESDE")
			       ,fehasta  = params.get("FEHASTA");			
			
			Utils.validate(
					agrupamc , "No se recibi\u00f3n el agrupador"
					,status  , "No se recibi\u00f3n el status"
					);
			
			try
			{
				AgrupadorMC agrupador = AgrupadorMC.valueOf(agrupamc);
			}
			catch(Exception ex)
			{
				throw new ApplicationException("No se reconoce el agrupador");
			}
			
			Map<String,Object> manRes = flujoMesaControlManager.recuperarTramites(
					sb
					,agrupamc
					,status
					,usuario.getUser()
					,usuario.getRolActivo().getClave()
					,cdunieco
					,cdramo
					,cdtipsit
					,estado
					,nmpoliza
					,cdagente
					,ntramite
					,fedesde
					,fehasta
					,start
					,limit
					);
			
			list  = (List<Map<String,String>>)manRes.get("lista");
			total = (Integer)manRes.get("total");
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### list=",list
					,"\n###### recuperarTramites ######"
					,"\n###############################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		return SUCCESS;
	}
	
	@Action(value   = "recuperarPolizaUnica",
			results = { @Result(name="success", type="json") }
			)
	public String recuperarPolizaUnica()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n##################################"
				,"\n###### recuperarPolizaUnica ######"
				,"\n###### params=",params
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params, "No se recibieron datos");
			
			String cdunieco  = params.get("CDUNIECO")
			       ,cdramo   = params.get("CDRAMO")
			       ,estado   = params.get("ESTADO")
			       ,nmpoliza = params.get("NMPOLIZA");
			
			Utils.validate(
					cdunieco  , "No se recibi\u00f3n la sucursal"
					,cdramo   , "No se recibi\u00f3n el producto"
					,estado   , "No se recibi\u00f3n el estado"
					,nmpoliza , "No se recibi\u00f3n la p\u00f3nliza"
					);
			
			params.putAll(flujoMesaControlManager.recuperarPolizaUnica(sb,cdunieco,cdramo,estado,nmpoliza));
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### params=",params
					,"\n###### recuperarPolizaUnica ######"
					,"\n##################################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return SUCCESS;
	}
	
	@Action(value   = "registrarTramite",
			results = { @Result(name="success", type="json") }
			)
	public String registrarTramite()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n##############################"
				,"\n###### registrarTramite ######"
				,"\n###### params=",params
				));
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			Utils.validate(params, "No se recibieron datos");
			
			String cdtiptra    = params.get("CDTIPTRA")
			       ,cdtipsup   = params.get("CDTIPSUP")
			       ,cdtipflu   = params.get("CDTIPFLU")
			       ,cdflujomc  = params.get("CDFLUJOMC")
			       ,cdsucadm   = params.get("CDSUCADM")
			       ,cdsucdoc   = params.get("CDSUCDOC")
			       ,cdramo     = params.get("CDRAMO")
			       ,cdtipsit   = params.get("CDTIPSIT")
			       ,nmpoliza   = params.get("NMPOLIZA")
			       ,cdagente   = params.get("CDAGENTE")
			       ,referencia = params.get("REFERENCIA")
			       ,nombre     = params.get("NOMBRE")
			       ,status     = params.get("STATUS")
			       ,comments   = params.get("COMMENTS")
			       ,ferecepc   = params.get("FERECEPC")
			       ,festatus   = params.get("FESTATUS")
			       ,estado     = params.get("ESTADO");
			       
			Utils.validate(
					cdtiptra   , "No se recibi\u00f3 el tipo de tr\u00e1imte"
					,cdtipflu  , "No se recibi\u00f3 el flujo"
					,cdflujomc , "No se recibi\u00f3 el proceso"
					,status    , "No se recibi\u00f3 el status"
					);
			
			String ntramite = flujoMesaControlManager.registrarTramite(
					sb
					,cdsucdoc
					,cdramo
					,estado
					,nmpoliza
					,null //nmsuplem
					,cdsucadm
					,cdsucdoc
					,cdtiptra
					,ferecepc != null ? Utils.parse(ferecepc) : null
					,cdagente
					,referencia
					,nombre
					,festatus != null ? Utils.parse(festatus) : null
					,status
					,comments
					,null //nmsolici
					,cdtipsit
					,usuario.getUser()
					,usuario.getRolActivo().getClave()
					,null //swimpres
					,cdtipflu
					,cdflujomc
					,null
					,cdtipsup
					);
			
			params.put("ntramite" , ntramite);
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### params=",params
					,"\n###### registrarTramite ######"
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
	
	@Action(value   = "cargarAccionesEntidad",
			results = { @Result(name="success", type="json") }
			)
	public String cargarAccionesEntidad()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n###################################"
				,"\n###### cargarAccionesEntidad ######"
				,"\n###### params=",params
				));
		try
		{
			UserVO usuario   = Utils.validateSession(session);
			String cdusuari  = usuario.getUser()
			       ,cdsisrol = usuario.getRolActivo().getClave();
			
			Utils.validate(params, "No se recibieron datos");
			
			String cdtipflu   = params.get("cdtipflu")
			       ,cdflujomc = params.get("cdflujomc")
			       ,tipoent   = params.get("tipoent")
			       ,cdentidad = params.get("cdentidad")
			       ,webid     = params.get("webid");
			       
			Utils.validate(
					cdtipflu   , "No se recibi\u00f3 el flujo"
					,cdflujomc , "No se recibi\u00f3 el proceso"
					,tipoent   , "No se recibi\u00f3 la entidad"
					,cdentidad , "No se recibi\u00f3 la clave"
					);
			
			list = flujoMesaControlManager.cargarAccionesEntidad(
					sb
					,cdtipflu
					,cdflujomc
					,tipoent
					,cdentidad
					,webid
					,cdsisrol
					);
			
			params.put("cdusuari" , cdusuari);
			params.put("cdsisrol" , cdsisrol);
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### cargarAccionesEntidad ######"
					,"\n###################################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		return SUCCESS;
	}
	
	@Action(value   = "pantallaExterna",
	        results = {
			    @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
                @Result(name="success" , location="/jsp-script/proceso/flujoMesaControl/pantallaExterna.jsp")
            }
	)
	public String pantallaExterna()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n#############################"
				,"\n###### pantallaExterna ######"
				,"\n###### params=" , params
				,"\n###### flujo="  , flujo
				));
		
		String result = ERROR;
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			Utils.validate(params, "No se recibieron datos");
			
			String urlExterna = params.get("url");
			
			Utils.validate(urlExterna , "No hay url");
			
			String parametros = Utils.join(
					"?flujo.cdtipflu="   , flujo.getCdtipflu()
					,"&flujo.cdflujomc=" , flujo.getCdflujomc()
					,"&flujo.tipoent="   , flujo.getTipoent()
					,"&flujo.claveent="  , flujo.getClaveent()
					,"&flujo.webid="     , flujo.getWebid()
					,"&flujo.ntramite="  , flujo.getNtramite()
					,"&flujo.status="    , flujo.getStatus()
					,"&flujo.cdunieco="  , flujo.getCdunieco()
					,"&flujo.cdramo="    , flujo.getCdramo()
					,"&flujo.estado="    , flujo.getEstado()
					,"&flujo.nmpoliza="  , flujo.getNmpoliza()
					,"&flujo.nmsituac="  , flujo.getNmsituac()
					,"&flujo.nmsuplem="  , flujo.getNmsuplem()
					);
			
			urlExterna = Utils.join(urlExterna,parametros.replace("flujo.",""),"&cdusuari=",usuario.getUser(),"&cdsisrol=",usuario.getRolActivo().getClave());
			
			params.put("urlInterna" , parametros);
			params.put("urlExterna" , urlExterna);
			
			result = SUCCESS;
			
			sb.append(Utils.log(
					 "\n###### result=",result
					,"\n###### pantallaExterna ######"
					,"\n#############################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return result;
	}
	
	@Action(value   = "controladorExterno",
	        results = {
			    @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
                @Result(name="success" , location="/jsp-script/proceso/flujoMesaControl/controladorExterno.jsp")
            }
	)
	public String controladorExterno()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n#################################"
				,"\n###### controladorExterno ######"
				,"\n###### flujo="  , flujo
				));
		
		String result = ERROR;
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			Utils.validate(flujo , "No se recibieron datos");
			
			result = SUCCESS;
			
			sb.append(Utils.log(
					 "\n###### result=",result
					,"\n###### controladorExterno ######"
					,"\n################################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return result;
	}
	
	@Action(value   = "pantallaDiagnosticoFlujo",
	        results = {
			    @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
                @Result(name="success" , location="/jsp-script/proceso/flujoMesaControl/diagnosticoFlujo.jsp")
            }
	)
	public String pantallaDiagnosticoFlujo()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n######################################"
				,"\n###### pantallaDiagnosticoFlujo ######"
				,"\n###### flujo=", flujo
				));
		
		String result = ERROR;
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			Utils.validate(flujo , "No se recibieron datos");
			
			result = SUCCESS;
			
			sb.append(Utils.log(
					 "\n###### result=",result
					,"\n###### pantallaDiagnosticoFlujo ######"
					,"\n######################################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		return result;
	}
	
	@Action(value   = "procesoDemo",
			results = { @Result(name="success", type="json") }
			)
	public String cambiarFechaRecepcion()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n#########################"
				,"\n###### procesoDemo ######"
				,"\n###### flujo=",flujo
				));
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			Utils.validate(flujo, "No se recibieron datos");
			
			flujoMesaControlManager.procesoDemo(
					sb
					,flujo
					,usuario.getUser()
					,usuario.getRolActivo().getClave()
					);
			
			message = "Se agreg\u00f3 un nuevo registro de detalle para el tr\u00e1mite";
			success = true;
			
			sb.append(Utils.log(
					 "\n###### procesoDemo ######"
					,"\n#########################"
					));
			
			logger.debug(sb.toString());
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		return SUCCESS;
	}
	
	@Action(value   = "ejecutaRevision",
			results = { @Result(name="success", type="json") }
			)
	public String ejecutaRevision()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n#############################"
				,"\n###### ejecutaRevision ######"
				,"\n###### flujo="  , flujo
				,"\n###### params=" , params
				));
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(flujo  , "No se recibieron datos del flujo");
			
			list = flujoMesaControlManager.ejecutaRevision(
					sb
					,flujo
					);
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### params=" , params
					,"\n###### ejecutaRevision ######"
					,"\n#############################"
					));
			
			logger.debug(sb.toString());
			
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		return SUCCESS;
	}
	
	@Action(value   = "turnarTramite",
			results = { @Result(name="success", type="json") }
			)
	public String turnarTramite()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n###########################"
				,"\n###### turnarTramite ######"
				,"\n###### params=" , params
				));
		try
		{
			UserVO user        = Utils.validateSession(session);
			String cdusuariSes = user.getUser();
			String cdsisrolSes = user.getRolActivo().getClave();
			
			Utils.validate(params, "No se recibieron datos");
			
			String ntramite         = params.get("ntramite")
			       ,statusOld       = params.get("statusOld")
			       ,cdtipasigOld    = params.get("cdtipasigOld")
			       ,statusNew       = params.get("statusNew")
			       ,cdtipasigNew    = params.get("cdtipasigNew")
			       ,comments        = params.get("comments");
			
			Utils.validate(
					ntramite      , "No se recibi\u00f3 el tr\u00e1mite"
					,statusOld    , "No se recibi\u00f3 el status anterior"
					,cdtipasigOld , "No se recibi\u00f3 el tipo de status anterior"
					,statusNew    , "No se recibi\u00f3 el status nuevo"
					,cdtipasigNew , "No se recibi\u00f3 el tipo de status nuevo"
					);
			
			message = flujoMesaControlManager.turnarTramite(
					sb
					,ntramite
					,statusOld
					,cdtipasigOld
					,statusNew
					,cdtipasigNew
					,cdusuariSes
					,cdsisrolSes
					,comments
					);
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### message=" , message
					,"\n###### turnarTramite ######"
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
	
	@Action(value   = "recuperarDatosTramiteValidacionCliente",
			results = { @Result(name="success", type="json") }
			)
	public String recuperarDatosTramiteValidacionCliente()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n####################################################"
				,"\n###### recuperarDatosTramiteValidacionCliente ######"
				,"\n###### flujo=" , flujo
				));
		try
		{
			UserVO user     = Utils.validateSession(session);
			String cdusuari = user.getUser();
			String cdsisrol = user.getRolActivo().getClave();
			
			Utils.validate(flujo, "No se recibieron datos de flujo");
			
			datosTramite = flujoMesaControlManager.recuperarDatosTramiteValidacionCliente(sb,flujo);
			
			datosTramite.put("CDUSUARI" , cdusuari);
			datosTramite.put("CDSISROL" , cdsisrol);
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### recuperarDatosTramiteValidacionCliente ######"
					,"\n####################################################"
					));
			
			logger.debug(sb.toString());
			
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		return SUCCESS;
	}
	
	@Action(value   = "turnarDesdeComp",
			results = { @Result(name="success", type="json") }
			)
	public String turnar()
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n#############################"
				,"\n###### turnarDesdeComp ######"
				,"\n###### params=" , params
				));
		try
		{
			UserVO user     = Utils.validateSession(session);
			String cdusuari = user.getUser();
			String cdsisrol = user.getRolActivo().getClave();
			
			Utils.validate(params, "No se recibieron datos");
			
			String cdtipflu   = params.get("CDTIPFLU")
			       ,cdflujomc = params.get("CDFLUJOMC")
			       ,ntramite  = params.get("NTRAMITE")
			       ,statusOld = params.get("STATUSOLD")
			       ,statusNew = params.get("STATUSNEW")
			       ,swagente  = params.get("SWAGENTE")
			       ,comments  = params.get("COMMENTS");
			
			Utils.validate(
					cdtipflu   , "No se recibi\u00f3 el tipo de flujo"
					,cdflujomc , "No se recibi\u00f3 el proceso"
					,ntramite  , "No se recibi\u00f3 el tr\u00e1mite"
					,statusOld , "No se recibi\u00f3 el status anterior"
					,statusNew , "No se recibi\u00f3 el status nuevo"
					);
			
			message = flujoMesaControlManager.turnarDesdeComp(
					sb
					,cdusuari
					,cdsisrol
					,cdtipflu
					,cdflujomc
					,ntramite
					,statusOld
					,statusNew
					,swagente
					,comments
					);
			
			success = true;
			
			sb.append(Utils.log(
					 "\n###### turnarDesdeComp ######"
					,"\n#############################"
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
	public FlujoVO getFlujo() {                           //
		return flujo;                                     //
	}                                                     //
	                                                      //
	public void setFlujo(FlujoVO flujo) {                 //
		this.flujo = flujo;                               //
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
	public int getStart() {                               //
		return start;                                     //
	}                                                     //
                                                          //
	public void setStart(int start) {                     //
		this.start = start;                               //
	}                                                     //
                                                          //
	public int getLimit() {                               //
		return limit;                                     //
	}                                                     //
                                                          //
	public void setLimit(int limit) {                     //
		this.limit = limit;                               //
	}                                                     //
                                                          //
	public int getTotal() {                               //
		return total;                                     //
	}                                                     //
                                                          //
	public void setTotal(int total) {                     //
		this.total = total;                               //
	}                                                     //
                                                          //
	public Map<String, Object> getDatosTramite() {        //
		return datosTramite;                              //
	}                                                     //
                                                          //
	public void setDatosTramite(Map<String, Object>       //
	                            datosTramite) {           //
		this.datosTramite = datosTramite;                 //
	}                                                     //
                                                          //
    ////////////////////////////////////////////////////////
	
}