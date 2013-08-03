package mx.com.aon.portal.model;

/**
 * Clase VO con la estructura de datos para detalle de Descuento por volumen
 	* @param cdDscto
 	* @param cdDsctod
	* @param mnVolIni
	* @param mnVolFin
	* @param prDescto
	* @param mnDescto
 * 
 *
 */
public class DescuentoDetVolumenVO {
	private String cdDscto;
	private String cdDsctod;
	private String mnVolIni;
	private String mnVolFin;
	private String prDescto;
	private String mnDescto;
	
	
	public String getCdDscto() {
		return cdDscto;
	}
	public void setCdDscto(String cdDscto) {
		this.cdDscto = cdDscto;
	}
	public String getCdDsctod() {
		return cdDsctod;
	}
	public void setCdDsctod(String cdDsctod) {
		this.cdDsctod = cdDsctod;
	}
	public String getMnVolIni() {
		return mnVolIni;
	}
	public void setMnVolIni(String mnVolIni) {
		this.mnVolIni = mnVolIni;
	}
	public String getMnVolFin() {
		return mnVolFin;
	}
	public void setMnVolFin(String mnVolFin) {
		this.mnVolFin = mnVolFin;
	}
	public String getPrDescto() {
		return prDescto;
	}
	public void setPrDescto(String prDescto) {
		this.prDescto = prDescto;
	}
	public String getMnDescto() {
		return mnDescto;
	}
	public void setMnDescto(String mnDescto) {
		this.mnDescto = mnDescto;
	}
	
	
}
