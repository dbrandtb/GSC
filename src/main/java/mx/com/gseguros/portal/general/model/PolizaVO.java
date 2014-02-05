package mx.com.gseguros.portal.general.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class PolizaVO implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private String cdUnieco;
	
	private String cdRamo;
	
	private String estado;
	
	private String nmPoliza;
	
	private String nmSuplem;
	
	private String cdTipSit;
	
	
	//Constructors:
	
	public PolizaVO() {
		super();
	}

	public PolizaVO(String cdUnieco, String cdRamo, String estado, String nmPoliza) {
		super();
		this.cdUnieco = cdUnieco;
		this.cdRamo = cdRamo;
		this.estado = estado;
		this.nmPoliza = nmPoliza;
	}

	public PolizaVO(String cdUnieco, String cdRamo, String estado,
			String nmPoliza, String nmSuplem, String cdTipSit) {
		super();
		this.cdUnieco = cdUnieco;
		this.cdRamo = cdRamo;
		this.estado = estado;
		this.nmPoliza = nmPoliza;
		this.nmSuplem = nmSuplem;
		this.cdTipSit = cdTipSit;
	}
	
	
	//Getters and setters:

	public String getCdUnieco() {
		return cdUnieco;
	}

	public void setCdUnieco(String cdUnieco) {
		this.cdUnieco = cdUnieco;
	}

	public String getCdRamo() {
		return cdRamo;
	}

	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNmPoliza() {
		return nmPoliza;
	}

	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}

	public String getNmSuplem() {
		return nmSuplem;
	}

	public void setNmSuplem(String nmSuplem) {
		this.nmSuplem = nmSuplem;
	}

	public String getCdTipSit() {
		return cdTipSit;
	}

	public void setCdTipSit(String cdTipSit) {
		this.cdTipSit = cdTipSit;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
