package mx.com.aon.portal.model;

/**
 * Clase VO que modela la estructura de datos para ser usado por Mensajes de Error
 * 
 * @param msgId
 * @param msgText
 * @param msgTitle
 * @param dsMsgTitle
 *
 */
public class MensajeErrorVO {
	private String msgId;
	private String msgText;
	private String msgTitle;
	private String dsMsgTitle;

	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getMsgText() {
		return msgText;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getDsMsgTitle() {
		return dsMsgTitle;
	}
	public void setDsMsgTitle(String dsMsgTitle) {
		this.dsMsgTitle = dsMsgTitle;
	}
}
