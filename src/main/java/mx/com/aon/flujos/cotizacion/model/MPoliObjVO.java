package mx.com.aon.flujos.cotizacion.model;

import java.io.Serializable;

public class MPoliObjVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7939700433289479835L;
	
	private String cdunieco;
	private String cdramo;
	private String estado;
	private String nmpoliza;
	private String nmsituac;
	private String cdtipobj;
	private String dstipobj;
	private String nmsuplem;
	private String status;
	private String nmobjeto;
	private String dsobjeto;
	private String ptobjeto;
	private String cdagrupa;
	private String nmvalor;
	private String dsdescripcion;
	
	//Getters && Setters
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
	public String getCdtipobj() { return cdtipobj; }
	public void setCdtipobj(String cdtipobj) { this.cdtipobj = cdtipobj; }
	public String getDstipobj() { return dstipobj; }
	public void setDstipobj(String dstipobj) { this.dstipobj = dstipobj; }
	public String getNmsuplem() { return nmsuplem; }
	public void setNmsuplem(String nmsuplem) { this.nmsuplem = nmsuplem; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	public String getNmobjeto() { return nmobjeto; }
	public void setNmobjeto(String nmobjeto) { this.nmobjeto = nmobjeto; }
	public String getDsobjeto() { return dsobjeto; }
	public void setDsobjeto(String dsobjeto) { this.dsobjeto = dsobjeto; }
	public String getPtobjeto() { return ptobjeto; }
	public void setPtobjeto(String ptobjeto) { this.ptobjeto = ptobjeto; }
	public String getCdagrupa() { return cdagrupa; }
	public void setCdagrupa(String cdagrupa) { this.cdagrupa = cdagrupa; }
	public String getNmvalor() { return nmvalor; }
	public void setNmvalor(String nmvalor) { this.nmvalor = nmvalor; }
	public void setDsdescripcion(String dsdescripcion) {this.dsdescripcion = dsdescripcion;}
	public String getDsdescripcion() { return dsdescripcion;}
	
}