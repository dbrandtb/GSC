package mx.com.gseguros.portal.consultas.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author hector.lopez
 *
 */
public class ConsultaDatosSuplementoVO implements Serializable{

	
	private static final long serialVersionUID = -8555353864912795413L;

	
	private String cdunieco;
	private String cdramo;
	private String estado;
	private String nmpoliza;
	private String nmsuplem;
	private String feinival;
	private String nsuplogi;
	private String feemisio;
	private String nlogisus;
	private String dstipsup;
	private String ptpritot;
	
	
	public String getNmsuplem() {
		return nmsuplem;
	}
	public void setNmsuplem(String nmsuplem) {
		this.nmsuplem = nmsuplem;
	}
	public String getFeinival() {
		return feinival;
	}
	public void setFeinival(String feinival) {
		this.feinival = feinival;
	}
	public String getNsuplogi() {
		return nsuplogi;
	}
	public void setNsuplogi(String nsuplogi) {
		this.nsuplogi = nsuplogi;
	}
	public String getFeemisio() {
		return feemisio;
	}
	public void setFeemisio(String feemisio) {
		this.feemisio = feemisio;
	}
	public String getNlogisus() {
		return nlogisus;
	}
	public void setNlogisus(String nlogisus) {
		this.nlogisus = nlogisus;
	}
	public String getDstipsup() {
		return dstipsup;
	}
	public void setDstipsup(String dstipsup) {
		this.dstipsup = dstipsup;
	}
	public String getPtpritot() {
		return ptpritot;
	}
	public void setPtpritot(String ptpritot) {
		this.ptpritot = ptpritot;
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
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
