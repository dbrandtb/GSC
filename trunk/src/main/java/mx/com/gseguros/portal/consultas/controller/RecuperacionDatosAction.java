package mx.com.gseguros.portal.consultas.controller;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.consultas.model.RecuperacionSimple;
import mx.com.gseguros.portal.consultas.service.RecuperacionSimpleManager;
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
@Namespace("/recuperacion")
public class RecuperacionDatosAction extends PrincipalCoreAction
{
	private final static Logger logger = LoggerFactory.getLogger(RecuperacionDatosAction.class);
	
	private boolean                  success;
	private String                   message;
	private Map<String,String>       params;
	private List<Map<String,String>> list;
	
	@Autowired
	private RecuperacionSimpleManager recuperacionSimpleManager;
	
	@Action(value   = "recuperar",
			results = { @Result(name="success", type="json") }
	)
	public String recuperar()
	{
		logger.debug(Utils.log(
				 "\n#######################"
				,"\n###### recuperar ######"
				,"\n###### params=",params
				));
		try
		{
			UserVO usuario  = Utils.validateSession(session);
			String cdusuari = usuario.getUser();
			String cdsisrol = usuario.getRolActivo().getClave();
			
			Utils.validate(params , "No se recibieron datos");
			String consulta = params.get("consulta");
			Utils.validate(consulta , "No se recibi\u00F3 la consulta");
			
			RecuperacionSimple rec = null;
			try
			{
				rec = RecuperacionSimple.valueOf(consulta);
			}
			catch(Exception ex)
			{
				throw new ApplicationException("La consulta no existe");
			}
			
			if("M".equals(rec.getTipo()))
			{
				params.putAll(recuperacionSimpleManager.recuperarMapa(cdusuari,cdsisrol,rec,params,usuario));
			}
			else if("L".equals(rec.getTipo()))
			{
				list=recuperacionSimpleManager.recuperarLista(cdusuari,cdsisrol,rec,params,usuario);
			}
			else
			{
				throw new ApplicationException("Tipo de consulta mal definido");
			}
			
			success = true;
		}
		catch(Exception ex)
		{
			logger.error(Utils.join("Error en la recuperacion {",params,"}"),ex);
			message = Utils.manejaExcepcion(ex);
		}
		logger.debug(Utils.log(
				 "\n###### recuperar ######"
				,"\n#######################"
				));
		return SUCCESS;
	}

	////////////////////////////////////////////////////////
	// GETTERS Y SETTERS ///////////////////////////////////
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
	////////////////////////////////////////////////////////
	
}