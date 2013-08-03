package mx.com.aon.flujos.cotizacion.model;

import java.io.Serializable;

public class ReciboVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5655794428579425652L;
	
	private String origen;
	private String cdunieco;
	private String cdramo;
	private String estado;
	private String nmpoliza;
	private String nmsituac;
	private String nmsuplem;
	private String importeTotal;
	
	//Getters && Setters
	public String getOrigen() { return origen; }
	public void setOrigen(String origen) { this.origen = origen; }
	public String getCdunieco() { return cdunieco; }
	public void setCdunieco(String cdunieco) { this.cdunieco = cdunieco; }
	public String getCdramo() { return cdramo; }
	public void setCdramo(String cdramo) { this.cdramo = cdramo; }
	public String getEstado() { return estado; }
	public void setEstado(String estado) { this.estado = estado; }
	public String getNmpoliza() { return nmpoliza; }
	public void setNmpoliza(String nmpoliza) { this.nmpoliza = nmpoliza; }
	public String getNmsituac() { return nmsituac; }
	public void setNmsituac(String nmsituac) { this.nmsituac = nmsituac; }
	public String getNmsuplem() { return nmsuplem; }
	public void setNmsuplem(String nmsuplem) { this.nmsuplem = nmsuplem; }
	public String getImporteTotal() { return importeTotal; }
	public void setImporteTotal(String importeTotal) { this.importeTotal = importeTotal; }
	
}