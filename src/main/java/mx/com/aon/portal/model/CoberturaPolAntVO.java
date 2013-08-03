package mx.com.aon.portal.model;
/**
 * Clase VO que modela la estructura de datos para mostrar las coberturas
 * de la poliza anterior de la pantalla Consulta de Poliza Renovada - Cobertura de Poliza a Renovar
 * 
 * @vars ptCapita, cdGarant, dsGarant
 *
 */
public class CoberturaPolAntVO {

	private String ptCapita; 
	private String cdGarant;
	private String dsGarant;
	
	
	public String getPtCapita() {
		return ptCapita;
	}
	public void setPtCapita(String ptCapita) {
		this.ptCapita = ptCapita;
	}
	public String getCdGarant() {
		return cdGarant;
	}
	public void setCdGarant(String cdGarant) {
		this.cdGarant = cdGarant;
	}
	public String getDsGarant() {
		return dsGarant;
	}
	public void setDsGarant(String dsGarant) {
		this.dsGarant = dsGarant;
	}
}
