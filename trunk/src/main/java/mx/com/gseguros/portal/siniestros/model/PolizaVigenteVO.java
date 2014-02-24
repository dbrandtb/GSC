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
	
	private String dssucursal;
	
	private String dsramo;

	private String dsestatus;
	
	private String estatus;
	
	private String nmsuplem;
	
	private String nmsolici;
	
	private String cdtipsit;
	
	
	public String getEstatus() {
		return estatus;
	}


	public String getNmsuplem() {
		return nmsuplem;
	}



	public void setNmsuplem(String nmsuplem) {
		this.nmsuplem = nmsuplem;
	}



	public String getNmsolici() {
		return nmsolici;
	}



	public void setNmsolici(String nmsolici) {
		this.nmsolici = nmsolici;
	}



	public String getCdtipsit() {
		return cdtipsit;
	}



	public void setCdtipsit(String cdtipsit) {
		this.cdtipsit = cdtipsit;
	}



	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getDssucursal() {
		return dssucursal;
	}



	public void setDssucursal(String dssucursal) {
		this.dssucursal = dssucursal;
	}



	public String getDsramo() {
		return dsramo;
	}



	public void setDsramo(String dsramo) {
		this.dsramo = dsramo;
	}



	public String getDsestatus() {
		return dsestatus;
	}



	public void setDsestatus(String dsestatus) {
		this.dsestatus = dsestatus;
	}



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
