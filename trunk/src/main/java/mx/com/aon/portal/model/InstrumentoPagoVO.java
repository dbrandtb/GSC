package mx.com.aon.portal.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class InstrumentoPagoVO implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4685320601164792587L;
	
	private String cdForPag;
	private String dsForPag;
	private String cdMuestra;
	
	public String getCdForPag() {
		return cdForPag;
	}
	public void setCdForPag(String cdForPag) {
		this.cdForPag = cdForPag;
	}
	public String getDsForPag() {
		return dsForPag;
	}
	public void setDsForPag(String dsForPag) {
		this.dsForPag = dsForPag;
	}
	public String getCdMuestra() {
		return cdMuestra;
	}
	public void setCdMuestra(String cdMuestra) {
		this.cdMuestra = cdMuestra;
	}
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	

}
