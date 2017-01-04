package mx.com.gseguros.portal.despachador.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.mesacontrol.model.FlujoVO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.despachador.model.RespuestaTurnadoVO;
import mx.com.gseguros.portal.despachador.service.DespachadorManager;
import mx.com.gseguros.utils.Utils;

@Controller
@Scope("prototype")
@ParentPackage(value="default")
@Namespace("/despachador")
public class DespachadorAction extends PrincipalCoreAction {
	private static final long serialVersionUID = -5407887845208850332L;
	private static final Logger logger = LoggerFactory.getLogger(DespachadorAction.class);
	private Map<String, String> params;
	private List<Map<String, String>> list;
	private String message;
	private boolean success;
	private FlujoVO flujo;
	private Map<String, Item> items;
	
	@Autowired
	private DespachadorManager despachadorManager;
	
	public DespachadorAction () {
		this.session = ActionContext.getContext().getSession();
	}
	
	@Action(value   = "despacharTest",
			results = { @Result(name="success", type="json") }
			)
	public String despacharTest () {
		logger.debug(Utils.log(
				"\n###########################",
				"\n###### despacharTest ######",
				"\n###### params = ", params));
		try {
			Utils.validate(params , "No hay datos");
			String ntramite = params.get("ntramite"),
			       status   = params.get("status");
			Utils.validate(ntramite , "Falta ntramite",
			               status   , "Falta status");
			message = despachadorManager.despachar(ntramite, status).toString();
			success = true;
		} catch (Exception ex) {
			message = Utils.manejaExcepcion(ex);
		}
		logger.debug(Utils.log(
				"\n###### success = " , success,
				"\n###### message = " , message,
				"\n###### despacharTest ######",
				"\n###########################"));
		return SUCCESS;
	}
	
	/**
	 * SE TURNA/RECHAZA/REASIGNA UN TRAMITE
	 * @return boolean success, String message
	 */
	@Action(value   = "turnarTramite",
            results = { @Result(name="success", type="json") }
            )
    public String turnarTramite() {
	    logger.debug(Utils.log(
	            "\n###########################",
	            "\n###### turnarTramite ######",
	            "\n###### params = ", params));
	    try {
	        UserVO usuario = Utils.validateSession(session);
	        Utils.validate(params, "No se recibieron par\u00e1metros");
	        String ntramite    = params.get("numtramite"),
	               status      = params.get("status"),
	               comments    = params.get("comments"),
	               cdrazrecha  = params.get("cdrazrecha"),
	               cdusuariDes = params.get("cdusuariDes"),
	               cdsisrolDes = params.get("cdsisrolDes"),
	               swagente    = params.get("swagente");
	        Utils.validate(ntramite, "Falta ntramite");
	        Date fechaHoy = new Date();
	        RespuestaTurnadoVO respuestaTurnado = despachadorManager.turnarTramite(usuario.getUser(), usuario.getRolActivo().getClave(),
	                ntramite, status, comments, cdrazrecha, cdusuariDes, cdsisrolDes, "S".equals(swagente), false, fechaHoy, false);
	        message = respuestaTurnado.getMessage();
	        params.put("encolado", respuestaTurnado.isEncolado() ? "S" : "N");
	        success = true;
	    } catch (Exception ex) {
	        message = Utils.manejaExcepcion(ex);
	    }
        logger.debug(Utils.log(
                "\n###### success = " , success,
                "\n###### message = " , message,
                "\n###### turnarTramite ######",
                "\n###########################"));
	    return SUCCESS;
	}
	
	@Action(value   = "pantallaDatos",
            results = {
                @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
                @Result(name="success" , location="/jsp-script/proceso/flujoMesaControl/pantallaDatosDespachador.jsp")
            })
	public String pantallaDatos () {
	    String result = ERROR;
	    try {
	        items = despachadorManager.pantallaDatos();
	        result = SUCCESS;
	    } catch (Exception ex) {
	        message = Utils.manejaExcepcion(ex);
	    }
	    return result;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////// GETTERS Y SETTERS /////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

    public Map<String, Item> getItems() {
        return items;
    }

    public void setItems(Map<String, Item> items) {
        this.items = items;
    }
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////// GETTERS Y SETTERS /////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
}