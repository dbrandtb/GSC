package mx.com.gseguros.portal.estadisticas.controller;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.estadisticas.service.EstadisticasManager;
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
@ParentPackage(value="default")
@Namespace("/estadisticas")
public class EstadisticasAction extends PrincipalCoreAction
{
	private static Logger logger = Logger.getLogger(EstadisticasAction.class);
	
	private Map<String,String>       params;
	private boolean                  success;
	private String                   respuesta;
	private List<Map<String,String>> list;
	private Map<String,Item>         items;
	private Map<String,Object>       objetos;
	
	@Autowired
	private EstadisticasManager estadisticasManager;
	
	@Action(value   = "cotizacionEmision",
		    results = {
		        @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
		        @Result(name="success" , location="/jsp-script/proceso/estadisticas/cotizacionEmision.jsp")
		    })
	public String cotizacionEmision()
	{
		StringBuilder sb = new StringBuilder(Utils.join(
				 "\n###############################"
				,"\n###### cotizacionEmision ######"
				));
		try
		{
			UserVO user = Utils.validateSession(session);
			
			items = estadisticasManager.cotizacionEmision(sb,user.getRolActivo().getClave());
			
			logger.info(Utils.join(
					 "\n###############################"
					,"\n@@@*** cotizacionEmision ***@@@"
					,"\n###############################"
					));
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		return SUCCESS;
	}
	
	@Action(value   = "recuperarCotizacionesEmisiones",
			results = { @Result(name="success", type="json") }
			)
	public String recuperarCotizacionesEmisiones()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(Utils.join(
				 "\n############################################"
				,"\n###### recuperarCotizacionesEmisiones ######"
				,"\n###### params=",params
				));
		
		try
		{
			Utils.validate(sb, params , "No se recibieron datos");
			
			String feinicio = params.get("feinicio");
			String fefin    = params.get("fefin");
			String cdunieco = params.get("cdunieco");
			String cdramo   = params.get("cdramo");
			String cdusuari = params.get("cdusuari");
			String cdagente = params.get("cdagente");
			
			Utils.validate(sb
					,feinicio , "No se recibio la fecha de inicio"
					,fefin    , "No se recibio la fecha de fin"
					);
			
			objetos = estadisticasManager.recuperarCotizacionesEmisiones(
					sb
					,Utils.render(feinicio)
					,Utils.render(fefin)
					,cdunieco
					,cdramo
					,cdusuari
					,cdagente
					);
			
			success = true;
			
			logger.info(Utils.join(
					 "\n############################################"
					,"\n@@@*** recuperarCotizacionesEmisiones ***@@@"
					,"\n############################################"
					));
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		return SUCCESS;
	}

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

	public List<Map<String, String>> getList() {
		return list;
	}

	public void setList(List<Map<String, String>> list) {
		this.list = list;
	}

	public void setEstadisticasManager(EstadisticasManager estadisticasManager) {
		this.estadisticasManager = estadisticasManager;
	}

	public Map<String, Item> getItems() {
		return items;
	}

	public void setItems(Map<String, Item> items) {
		this.items = items;
	}

	public Map<String, Object> getObjetos() {
		return objetos;
	}

	public void setObjetos(Map<String, Object> objetos) {
		this.objetos = objetos;
	}
	
}