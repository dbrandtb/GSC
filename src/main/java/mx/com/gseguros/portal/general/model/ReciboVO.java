package mx.com.gseguros.portal.general.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ReciboVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public ReciboVO() {
		super();
	}

	/**
	 * N&uacute;mero de recibo
	 */
	private String nmrecibo;
	
	/**
	 * Fecha inicio del recibo
	 */
	private String feinicio;
	
	/**
	 * Fecha final del recibo
	 */
	private String fefinal;
	
	/**
	 * Fecha de emisi&oacute;n del recibo
	 */
	private String feemisio;
	
	/**
	 * Clave del estado del recibo
	 */
	private String cdestado;
	
	/**
	 * Descripci&oacute;n del estado del recibo
	 */
	private String dsestado;
	
	/**
	 * Importe del recibo
	 */
	private String ptimport;
	
	/**
	 * Clave del tipo de recibo
	 */
	private String tiporeci;
	
	/**
	 * Descripci&oacute;n del tipo de recibo
	 */
	private String dstipore;

	
	//Getters and setters:
	
	public String getNmrecibo() {
		return nmrecibo;
	}

	public void setNmrecibo(String nmrecibo) {
		this.nmrecibo = nmrecibo;
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

	public String getFeemisio() {
		return feemisio;
	}

	public void setFeemisio(String feemisio) {
		this.feemisio = feemisio;
	}

	public String getCdestado() {
		return cdestado;
	}

	public void setCdestado(String cdestado) {
		this.cdestado = cdestado;
	}

	public String getDsestado() {
		return dsestado;
	}

	public void setDsestado(String dsestado) {
		this.dsestado = dsestado;
	}

	public String getPtimport() {
		return ptimport;
	}

	public void setPtimport(String ptimport) {
		this.ptimport = ptimport;
	}

	public String getTiporeci() {
		return tiporeci;
	}

	public void setTiporeci(String tiporeci) {
		this.tiporeci = tiporeci;
	}

	public String getDstipore() {
		return dstipore;
	}

	public void setDstipore(String dstipore) {
		this.dstipore = dstipore;
	}
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
