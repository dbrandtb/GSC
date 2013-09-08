package mx.com.gseguros.portal.consultas.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author ricardo.bautista
 *
 */
public class ConsultaDatosSuplementoVO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8555353864912795413L;

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
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
