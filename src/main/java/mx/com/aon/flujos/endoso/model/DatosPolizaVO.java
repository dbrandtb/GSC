package mx.com.aon.flujos.endoso.model;

import java.io.Serializable;

/**
 * @author ricardo.bautista
 *
 */
public class DatosPolizaVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4115327458342844371L;
	
	/**
	 * Periodicidad de pago
	 */
	private String cdperpag;
	
	private String cdmoneda;
	
	private String ottempot;
	
	private String nmrenova;
	
	/**
	 * Numero de poliza externo
	 */
	private String nmpoliex;
	
	
	public String getCdperpag() {
		return cdperpag;
	}
	public void setCdperpag(String cdperpag) {
		this.cdperpag = cdperpag;
	}
	public String getCdmoneda() {
		return cdmoneda;
	}
	public void setCdmoneda(String cdmoneda) {
		this.cdmoneda = cdmoneda;
	}
	public String getOttempot() {
		return ottempot;
	}
	public void setOttempot(String ottempot) {
		this.ottempot = ottempot;
	}
	public String getNmrenova() {
		return nmrenova;
	}
	public void setNmrenova(String nmrenova) {
		this.nmrenova = nmrenova;
	}
	public String getNmpoliex() {
		return nmpoliex;
	}
	public void setNmpoliex(String nmpoliex) {
		this.nmpoliex = nmpoliex;
	}
	
}
