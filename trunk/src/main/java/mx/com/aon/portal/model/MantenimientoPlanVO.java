
package mx.com.aon.portal.model;

public class MantenimientoPlanVO {
	/**
	 * Atributos según la tabla MPLANES
	 *  
	 */
	private String codigoRamo;
	private String codigoPlan;
	private String descripcionPlan;
	
	//Parámetros OutPut del SP PKG_CONFG_CUENTA.p_inserta_plan
	private String outTitle;
	private String msgId;

	public void setCodigoPlan (String codigoPlan) {
		this.codigoPlan = codigoPlan;
	}
	public String getCodigoPlan () {
		return this.codigoPlan;
	}
	public void setDescripcionPlan (String descripcionPlan) {
		this.descripcionPlan = descripcionPlan;
	}
	public String getDescripcionPlan () {
		return this.descripcionPlan;
	}
	public void setOutTitle (String outTitle) {this.outTitle = outTitle;}
	public String getOutTitle () {return this.outTitle;}


    public String getCodigoRamo() {
        return codigoRamo;
    }

    public void setCodigoRamo(String codigoRamo) {
        this.codigoRamo = codigoRamo;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
