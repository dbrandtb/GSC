package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.com.aon.portal.model.MensajeErrorVO;
import mx.com.aon.portal.service.MensajesErrorManager;
import mx.com.gseguros.exception.ApplicationException;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Action que atiende las peticiones de abm del cliente para la configuracion de Mensajes de Error.
 * 
 * @extends ActionSupport
 *
 */
public class MensajesErrorAction extends ActionSupport {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(MensajesErrorAction.class);
	
	private static final long serialVersionUID = 1L;

	private transient MensajesErrorManager mensajesErrorManager;

	private String cdError;

	private String dsMensaje;

	private String cdTipo;

	private boolean success;
	
	private List<MensajeErrorVO> mensajeError;
	
	/**
	 * Metodo que obtiene un unico registro con datos de un mensaje de error.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGetMensajeError () throws Exception {
		try {
			MensajeErrorVO mensajeErrorVO = mensajesErrorManager.getMensajeError(cdError);
			mensajeError = new ArrayList<MensajeErrorVO>();
			mensajeError.add(mensajeErrorVO);
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
	
	/**
	 * Metodo que inserta un nuevo registro o actualiza un registro editado en pantalla
	 * de un mensaje de error.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGuardarMensajeError () throws Exception {
		String messageResult = "";
		try {
			messageResult = mensajesErrorManager.guardarMensajeError(cdError, dsMensaje, cdTipo);
			success = true;
			addActionMessage(messageResult);
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}

	public String getCdError() {
		return cdError;
	}

	public void setCdError(String cdError) {
		this.cdError = cdError;
	}

	public String getDsMensaje() {
		return dsMensaje;
	}

	public void setDsMensaje(String dsMensaje) {
		this.dsMensaje = dsMensaje;
	}

	public String getCdTipo() {
		return cdTipo;
	}

	public void setCdTipo(String cdTipo) {
		this.cdTipo = cdTipo;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<MensajeErrorVO> getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(List<MensajeErrorVO> mensajeError) {
		this.mensajeError = mensajeError;
	}

	public void setMensajesErrorManager(MensajesErrorManager mensajesErrorManager) {
		this.mensajesErrorManager = mensajesErrorManager;
	}
}