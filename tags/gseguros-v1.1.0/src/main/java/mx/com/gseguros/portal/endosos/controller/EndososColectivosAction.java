package mx.com.gseguros.portal.endosos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.endosos.service.EndososManager;
import mx.com.gseguros.portal.general.util.TipoEndoso;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
@ParentPackage(value="default")
@Namespace("/endosos")
public class EndososColectivosAction extends PrincipalCoreAction
{
	private final static Logger logger = LoggerFactory.getLogger(EndososColectivosAction.class);
	
	private boolean                  success;
	private String                   message;
	private Map<String,String>       params;
	private List<Map<String,String>> list;
	private Map<String,Item>         items;
	
	public EndososColectivosAction()
	{
		this.session = ActionContext.getContext().getSession();
	}
	
	@Autowired
	private EndososManager endososManager;
	
	@Value("${ruta.documentos.poliza}")
	private String rutaDocumentosPoliza;
	
	@Value("${ruta.servidor.reports}")
	private String rutaServidorReports;
	
	@Value("${pass.servidor.reports}")
	private String passServidorReports;

	@Action(value   = "includes/pantallaEndosoAltaBajaFamilia",
	        results = {
			    @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
                @Result(name="success" , location="/jsp-script/proceso/endosos/endosoFamilia.jsp")
            },
            interceptorRefs = {
                @InterceptorRef(value = "json", params = {"enableSMD", "true", "ignoreSMDMethodInterfaces", "false" })
            }
	)
	public String pantallaEndosoAltaBajaFamilia()
	{
		logger.debug(Utils.log(
				 "\n###########################################"
				,"\n###### pantallaEndosoAltaBajaFamilia ######"
				,"\n###### params=",params
				));
		String result = ERROR;
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			Utils.validate(params, "No se recibieron datos");
			
			String cdunieco = params.get("CDUNIECO");
			String cdramo   = params.get("CDRAMO");
			String estado   = params.get("ESTADO");
			String nmpoliza = params.get("NMPOLIZA");
			String tipoflot = params.get("TIPOFLOT");
			String cdtipsup = params.get("cdtipsup");
			if(StringUtils.isBlank(tipoflot))
			{
				tipoflot = "I";
			}
			
			Utils.validate(
					cdunieco  , "No se recibi\u00F3 la sucursal"
					,cdramo   , "No se recibi\u00F3 el producto"
					,estado   , "No se recibi\u00F3 el estado de p\u00F3liza"
					,nmpoliza , "No se recibi\u00F3 el n\u00FCmero de p\u00F3liza"
					,cdtipsup , "No se recibi\u00F3 el c\u00F3digo de endoso"
					);
			
			Map<String,Object> res = endososManager.pantallaEndosoAltaBajaFamilia(
					usuario.getUser()
					,usuario.getRolActivo().getClave()
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,tipoflot
					,"FAMILIA"
					,cdtipsup
					,ServletActionContext.getServletContext().getServletContextName()
					);
			items = (Map<String,Item>)res.get("items");
			params.put("nmsuplem_endoso" , (String)res.get("nmsuplem_endoso"));
			params.put("nsuplogi"        , (String)res.get("nsuplogi"));
			
			if(TipoEndoso.ALTA_ASEGURADOS.getCdTipSup().toString().equals(cdtipsup))
			{
				params.put("operacion" , "alta");
			}
			else if(TipoEndoso.BAJA_ASEGURADOS.getCdTipSup().toString().equals(cdtipsup))
			{
				params.put("operacion" , "baja");
			}
			else
			{
				throw new ApplicationException("Tipo de endoso mal definido");
			}
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		return result;
	}
	
	@Action(value   = "recuperarComponentesAltaAsegurado",
			results = { @Result(name="success", type="json") }
			)
	public String recuperarComponentesAltaAsegurado()
	{
		logger.debug(Utils.log(
				 "\n###############################################"
				,"\n###### recuperarComponentesAltaAsegurado ######"
				,"\n###### params=",params
				));
		
		try
		{
			UserVO usuario  = Utils.validateSession(session);
			String cdsisrol = usuario.getRolActivo().getClave();
					
			Utils.validate(params , "No se recibieron datos");
			String cdramo   = params.get("cdramo");
			String cdtipsit = params.get("cdtipsit");
			String depFam   = params.get("depFam");
			Utils.validate(
					cdramo    , "No se recibi\u00F3 el producto"
					,cdtipsit , "No se recibi\u00F3 la clave de situaci\u00F3n"
					,depFam   , "No se recibi\u00F3 el tipo de formulario"
					);
			
			params.putAll(endososManager.recuperarComponentesAltaAsegurado(
					cdramo
					,cdtipsit
					,depFam
					,cdsisrol
					,ServletActionContext.getServletContext().getServletContextName()
					));
			
			success = true;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### recuperarComponentesAltaAsegurado ######"
				,"\n###############################################"
				));
		return SUCCESS;
	}
	
	@Action(value           = "confirmarEndosoFamilias",
			results         = { @Result(name="success", type="json") },
            interceptorRefs = {
			    @InterceptorRef(value = "json", params = {"enableSMD", "true", "ignoreSMDMethodInterfaces", "false" })
			})
	public String confirmarEndosoFamilias()
	{
		logger.debug(Utils.log(
				 "\n#####################################"
				,"\n###### confirmarEndosoFamilias ######"
				,"\n###### params=" , params
				,"\n###### list="   , list
				));
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String cdunieco             = params.get("cdunieco"),
			       cdramo               = params.get("cdramo"),
			       estado               = params.get("estado"),
			       nmpoliza             = params.get("nmpoliza"),
			       cdtipsup             = params.get("cdtipsup"),
			       nmsuplem             = params.get("nmsuplem"),
			       nsuplogi             = params.get("nsuplogi"),
			       fecha                = params.get("fecha"),
			       cdtipsitPrimerInciso = params.get("cdtipsitPrimerInciso"),
			       nmsolici             = params.get("nmsolici");
			
			Utils.validate(
					cdunieco              , "No se recibi\u00F3 la sucursal"
					,cdramo               , "No se recibi\u00F3 el producto"
					,estado               , "No se recibi\u00F3 el estado de p\u00F3liza"
					,nmpoliza             , "No se recibi\u00F3 la p\u00F3liza"
					,cdtipsup             , "No se recibi\u00F3 la clave de endoso"
					,nmsuplem             , "No se recibi\u00F3 el suplemento"
					,nsuplogi             , "No se recibi\u00F3 el consecutivo de endoso"
					,fecha                , "No se recibi\u00F3 la fecha de efecto"
					,cdtipsitPrimerInciso , "No se recibi\u00F3 la modalidad del primer inciso"
					,nmsolici             , "No se recibi\u00F3 la cotizaci\u00F3n"
					);
			
			if(TipoEndoso.ALTA_ASEGURADOS.getCdTipSup()==Integer.parseInt(cdtipsup))
			{
				/*
				endososManager.confirmarEndosoAltaFamilia(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,cdtipsup
						,list
						);
				*/
			}
			else
			{
				List<String> incisos = new ArrayList<String>();
				for(Map<String,String> inciso : list)
				{
					incisos.add(inciso.get("nmsituac"));
				}
				
				message = endososManager.confirmarEndosoBajaFamilia(
						usuario.getUser()
						,usuario.getRolActivo().getClave()
						,usuario.getEmpresa().getElementoId()
						,cdunieco
						,cdramo
						,estado
						,nmpoliza
						,cdtipsup
						,nmsuplem
						,nsuplogi
						,Utils.parse(fecha)
						,rutaDocumentosPoliza
						,rutaServidorReports
						,passServidorReports
						,usuario
						,incisos
						,cdtipsitPrimerInciso
						,nmsolici
						);
			}
			
			success = true;
			
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### message=",message
				,"\n###### confirmarEndosoFamilias ######"
				,"\n#####################################"
				));
		return SUCCESS;
	}
	
	/**
	 * Getters y setters
	 * @return
	 */
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