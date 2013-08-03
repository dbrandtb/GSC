package mx.com.aon.portal.web;

import java.util.HashMap;
import java.util.Map;

import mx.com.aon.portal.model.MensajesVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.CatalogService;
import mx.com.aon.portal.service.ProcesosCriticosManager;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class ProcesosCriticosAction extends ActionSupport implements SessionAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4538858353380451860L;


	private static Logger logger = Logger.getLogger(ProcesosCriticosAction.class);
	
	
	private boolean success;
	
	private String password;
	
	private String mensajeRespuesta;
	
	@SuppressWarnings("unchecked")
	private Map session;
	
	private ProcesosCriticosManager procesosCriticosManager;
	
	/**
	 * Manager con implementacion de Endpoints para catalogos
	 */
	private CatalogService catalogService;
	
	public String confirmaPassword() throws Exception {
		
		UserVO usuario = (UserVO)session.get("USUARIO");
		MensajesVO mensajeVO = null;
		mensajeVO = procesosCriticosManager.confirmaPassword(usuario.getUser(), password);
		
		//Password correcto
		logger.debug("text=" + mensajeVO.getText());
		logger.debug("msgid=" + mensajeVO.getMsgId());
		if(mensajeVO.getText().equals("1")) {
			success = true;
		}else {
			success = false;
			
			//Si el password es incorrecto obtenemos el mensaje de error:
			Map<String, String> params = new HashMap<String, String>();
			params.put("msg", mensajeVO.getMsgId());
			mensajeVO = catalogService.getMensajes(params, "OBTIENE_MENSAJES");
			mensajeRespuesta = mensajeVO.getMsgText();
		}
		
		//Se pone password en null para que no se vea en la respuesta json 
		password = null;
		return SUCCESS;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}

	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}

	public void setProcesosCriticosManager(
			ProcesosCriticosManager procesosCriticosManager) {
		this.procesosCriticosManager = procesosCriticosManager;
	}

	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	@SuppressWarnings("unchecked")
    public void setSession(Map session) {
        this.session = session;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
