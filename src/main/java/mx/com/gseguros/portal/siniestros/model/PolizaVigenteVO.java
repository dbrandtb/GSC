package mx.com.gseguros.portal.siniestros.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class PolizaVigenteVO implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private String cdunieco;
	
	private String cdramo;
	
	private String estado;
	
	private String nmpoliza;
	
	private String nmsituac;
	
	private String mtoBase;
	
	private String feinicio;
	
	private String fefinal;

	
	
	public String getFeinicio() {
		return feinicio;
	}



	public void setFeinicio(String feinicio) {
		this.feinicio = feinicio;
	}



	public String getFefinal() {
		return fefinal;
	}



	public void setFefinal(String fefinal) {
		this.fefinal = fefinal;
	}



	public String getCdunieco() {
		return cdunieco;
	}



	public void setCdunieco(String cdunieco) {
		this.cdunieco = cdunieco;
	}



	public String getCdramo() {
		return cdramo;
	}



	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
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



	public String getMtoBase() {
		return mtoBase;
	}



	public void setMtoBase(String mtoBase) {
		this.mtoBase = mtoBase;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
