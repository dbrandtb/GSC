package mx.com.aon.flujos.endoso.model;

import java.io.Serializable;

/**
 * @author ricardo.bautista
 *
 */
public class PolizaCancelVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7588500634327948238L;

	/**
	 * Descripcion del motivo de anulacion
	 */
	private String descMotivoAnulacion;
	
	/**
	 * Fecha de cancelacion
	 */
	private String fechaCancelacion;
	
	private String msgId;
	
	private String title;

	public String getDescMotivoAnulacion() {
		return descMotivoAnulacion;
	}

	public void setDescMotivoAnulacion(String descMotivoAnulacion) {
		this.descMotivoAnulacion = descMotivoAnulacion;
	}

	public String getFechaCancelacion() {
		return fechaCancelacion;
	}

	public void setFechaCancelacion(String fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
