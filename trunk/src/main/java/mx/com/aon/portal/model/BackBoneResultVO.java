package mx.com.aon.portal.model;

/**
 * 
 * @author CIMA_USR
 *
 *	Usada para tomar el resultado de una operación en la BD
 *	y devolver el mensaje asociado, como también el valor de
 *	un parámetro IN/OUT del SP.
 *
 */
public class BackBoneResultVO {
	private String msgText;
	private String outParam;

	public String getMsgText() {
		return msgText;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	public String getOutParam() {
		return outParam;
	}
	public void setOutParam(String outParam) {
		this.outParam = outParam;
	}
}
