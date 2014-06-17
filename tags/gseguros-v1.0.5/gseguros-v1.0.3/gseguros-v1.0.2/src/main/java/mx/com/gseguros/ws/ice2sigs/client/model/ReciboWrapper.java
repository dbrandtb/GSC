/**
 * 
 */
package mx.com.gseguros.ws.ice2sigs.client.model;

import java.io.Serializable;

import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.Recibo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author  hector.lopez
 * 
 */

public class ReciboWrapper implements Serializable{

   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String operacion;
	private Recibo recibo;
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public Recibo getRecibo() {
		return recibo;
	}

	public void setRecibo(Recibo recibo) {
		this.recibo = recibo;
	}
}
