package mx.com.aon.portal.model;

/**
 * Clase VO con la estructura de datos para Seleccion de polizas para renovacion.
 *
 *@vars cdElemento,cdPerson,cdRamo,cdUniEco,cdTipRam,rpDesde,rpHasta,diasVencim
 */
public class PolizasRenovacionVO {

	public String cdElemento;
	public String cdPerson;
	public String cdRamo;
	public String cdUniEco;
	public String cdTipRam;    
	public String rpDesde;
	public String rpHasta;
	public String diasVencim;
	
	
	public String getCdPerson() {
		return cdPerson;
	}
	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}
	public String getCdRamo() {
		return cdRamo;
	}
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}
	public String getCdUniEco() {
		return cdUniEco;
	}
	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}
	public String getCdTipRam() {
		return cdTipRam;
	}
	public void setCdTipRam(String cdTipRam) {
		this.cdTipRam = cdTipRam;
	}
	public String getRpDesde() {
		return rpDesde;
	}
	public void setRpDesde(String rpDesde) {
		this.rpDesde = rpDesde;
	}
	public String getRpHasta() {
		return rpHasta;
	}
	public void setRpHasta(String rpHasta) {
		this.rpHasta = rpHasta;
	}
	public String getDiasVencim() {
		return diasVencim;
	}
	public void setDiasVencim(String diasVencim) {
		this.diasVencim = diasVencim;
	}
	public String getCdElemento() {
		return cdElemento;
	}
	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	} 
	
}
