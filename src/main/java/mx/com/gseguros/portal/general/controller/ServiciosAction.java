package mx.com.gseguros.portal.general.controller;

import java.util.Date;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.general.service.ServiciosManager;
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
		sb.append(Utils.join(
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
			
			logger.debug(Utils.log(
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
	
	@Action(value   = "grabarEvento",
		    results = {
		        @Result(name="success" , location="/jsp-script/servicios/respuesta.jsp")
		    })
	public String grabarEvento()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(Utils.join(
				 "\n##########################"
				,"\n###### grabarEvento ######"
				,"\n###### params=" , params
				));
		try
		{
			Utils.validate(sb,params,"No se recibieron datos");
			
			String cdmodulo    = params.get("cdmodulo");
			String cdevento    = params.get("cdevento");
			String ntramite    = params.get("ntramite");
			String cdunieco    = params.get("cdunieco");
			String cdramo      = params.get("cdramo");
			String estado      = params.get("estado");
			String nmpoliza    = params.get("nmpoliza");
			String nmsolici    = params.get("nmsolici");
			String cdagente    = params.get("cdagente");
			String cdusuariDes = params.get("cdusuariDes");
			String cdsisrolDes = params.get("cdsisrolDes");
			
			Utils.validate(sb
					,cdmodulo , "No se recibio el modulo"
					,cdevento , "No se recibio el evento"
					);
			
			String cdusuari = null;
			String cdsisrol = null;
			if(session!=null && session.get("USUARIO")!=null)
			{
				try
				{
					UserVO user = (UserVO)session.get("USUARIO");
					cdusuari = user.getUser();
					cdsisrol = user.getRolActivo().getClave();
				}
				catch(Exception ex)
				{
					sb.append("\nException al obtener usuario, no afecta el flujo");
				}
			}
			
			Date fecha = new Date();
			
			serviciosManager.grabarEvento(
					sb
					,cdmodulo
					,cdevento
					,fecha
					,cdusuari
					,cdsisrol
					,ntramite
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsolici
					,cdagente
					,cdusuariDes
					,cdsisrolDes
					);
			
			logger.debug(Utils.log(
					 "\n##########################"
					,"\n@@@*** grabarEvento ***@@@"
					,"\n@@@*** cdmodulo=" , cdmodulo , ", cdevento=" , cdevento , ", fecha="    , fecha
					,"\n@@@*** cdusuari=" , cdusuari , ", cdsisrol=" , cdsisrol , ", ntramite=" , ntramite
					,"\n@@@*** cdunieco=" , cdunieco , ", cdramo="   , cdramo   , ", estado="   , estado
					,"\n@@@*** nmpoliza=" , nmpoliza , ", nmsolici=" , nmsolici , ", cdagente=" , cdagente
					,"\n##########################"
					));
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		return SUCCESS;
	}
	
	@Action(value   = "recibosSubsecuentes",
		    results = {
	        @Result(name="input"   , location="/jsp-script/servicios/input.jsp"),
	        @Result(name="success" , location="/jsp-script/servicios/respuesta.jsp")
	    })
	public String recibosSubsecuentes()
	{
		logger.debug(Utils.log(
				 "\n#################################"
				,"\n###### recibosSubsecuentes ######"
				));
		
		try
		{
			serviciosManager.recibosSubsecuentes(getText("ruta.documentos.temporal"),false);
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### recibosSubsecuentes ######"
				,"\n#################################"
				));
		return SUCCESS;
	}
	
	@Action(value   = "recibosSubsecuentesTest",
		    results = {
	        @Result(name="input"   , location="/jsp-script/servicios/input.jsp"),
	        @Result(name="success" , location="/jsp-script/servicios/respuesta.jsp")
	    })
	public String recibosSubsecuentesTest()
	{
		logger.debug(Utils.log(
				 "\n#####################################"
				,"\n###### recibosSubsecuentesTest ######"
				));
		
		try
		{
			serviciosManager.recibosSubsecuentes(getText("ruta.documentos.temporal"),true);
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### recibosSubsecuentesTest ######"
				,"\n#####################################"
				));
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