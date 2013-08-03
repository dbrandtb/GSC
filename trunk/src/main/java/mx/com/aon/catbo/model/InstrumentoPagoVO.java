package mx.com.aon.catbo.model;

import java.io.Serializable;

public class InstrumentoPagoVO implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2582498353306904888L;
	
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

	

}
