package mx.com.gseguros.portal.consultas.controller;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.consultas.service.ExplotacionDocumentosManager;
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

@Controller
@Scope("prototype")
@ParentPackage(value="default")
@Namespace("/consultas")
public class ExplotacionDocumentosAction extends PrincipalCoreAction
{
	private final static Logger logger = LoggerFactory.getLogger(ExplotacionDocumentosAction.class);
	
	private Map<String,String>       params;
	private List<Map<String,String>> list;
	private boolean                  success;
	private String                   message;
	private Map<String,Item>         items;
	
	@Autowired
	private ExplotacionDocumentosManager explotacionDocumentosManager;
	
	@Action(value   = "pantallaExplotacionDocumentos",
	        results = {
			    @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
                @Result(name="success" , location="/jsp-script/consultas/pantallaExplotacionDocumentos.jsp")
            }
	)
	public String pantallaExplotacionDocumentos()
	{
		logger.debug(Utils.log(
				 "\n###########################################"
				,"\n###### pantallaExplotacionDocumentos ######"
				));
		
		String result = ERROR;
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			items = explotacionDocumentosManager.pantallaExplotacionDocumentos(usuario.getUser(),usuario.getRolActivo().getClave());
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### pantallaExplotacionDocumentos ######"
				,"\n###########################################"
				));
		return result;
	}
	
	@Action(value           = "imprimirLote",
			results         = { @Result(name="success", type="json") },
            interceptorRefs = {
			    @InterceptorRef(value = "json", params = {"enableSMD", "true", "ignoreSMDMethodInterfaces", "false" })
			})
	public String imprimirLote()
	{
		logger.debug(Utils.log(
				 "\n##########################"
				,"\n###### imprimirLote ######"
				,"\n###### params=" , params
				,"\n###### list="   , list
				));
		
		try
		{
			success = true;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### imprimirLote ######"
				,"\n##########################"
				));
		
		return SUCCESS;
	}
	
	////////////////// Getters y setters ///////////////////
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
	////////////////////////////////////////////////////////
}