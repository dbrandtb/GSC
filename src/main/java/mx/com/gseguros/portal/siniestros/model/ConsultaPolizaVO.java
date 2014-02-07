package mx.com.gseguros.portal.siniestros.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Alberto
 *
 */
public class ConsultaPolizaVO implements Serializable{

	private static final long serialVersionUID = -8555353864912795413L;
	private String cdunieco;
	private String estado;
	private String cdramo;
	private String nmpoliza;
	private String nmsituac;
	
	
	public String getCdunieco() {
		return cdunieco;
	}


	public void setCdunieco(String cdunieco) {
		this.cdunieco = cdunieco;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getCdramo() {
		return cdramo;
	}


	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}


	public String getNmpoliza() {
		return nmpoliza;
	}


	public void setNmpoliza(String nmpoliza) {
		this.nmpoliza = nmpoliza;
	}


	public String getNmsituac() {
		return nmsituac;
	}


	public void setNmsituac(String nmsituac) {
		this.nmsituac = nmsituac;
	}


	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}	
	
}
