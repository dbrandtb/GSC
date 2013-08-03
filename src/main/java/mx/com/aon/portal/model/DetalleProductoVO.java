package mx.com.aon.portal.model;

/**
 * Clase VO con la estructura de datos para detalle de detalle de Descuento por producto
 	* @param cdDscto
 	* @param cdDsctod
	* @param mnVolIni
	* @param mnVolFin
	* @param prDescto
	* @param mnDescto
 * 
 *
 */
public class DetalleProductoVO {
	private String cdDscto;
	private String cdDsctod;
	private String cdUniEco;
	private String dsUniEco;
	private String cdRamo;
	private String dsRamo;
	private String cdTipSit;
	private String dsTipSit;
	private String cdPlan;
	private String cdAtribu;
	private String dsPlan;
	private String otValor;
	
	public String getOtValor() {
		return otValor;
	}
	public void setOtValor(String otValor) {
		this.otValor = otValor;
	}
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
	public String getCdUniEco() {
		return cdUniEco;
	}
	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}
	public String getDsUniEco() {
		return dsUniEco;
	}
	public void setDsUniEco(String dsUniEco) {
		this.dsUniEco = dsUniEco;
	}
	public String getCdRamo() {
		return cdRamo;
	}
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}
	public String getDsRamo() {
		return dsRamo;
	}
	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}
	public String getCdTipSit() {
		return cdTipSit;
	}
	public void setCdTipSit(String cdTipSit) {
		this.cdTipSit = cdTipSit;
	}
	public String getDsTipSit() {
		return dsTipSit;
	}
	public void setDsTipSit(String dsTipSit) {
		this.dsTipSit = dsTipSit;
	}
	public String getCdAtribu() {
		return cdAtribu;
	}
	public void setCdAtribu(String cdAtribu) {
		this.cdAtribu = cdAtribu;
	}
	public String getDsPlan() {
		return dsPlan;
	}
	public void setDsPlan(String dsPlan) {
		this.dsPlan = dsPlan;
	}
	public String getCdPlan() {
		return cdPlan;
	}
	public void setCdPlan(String cdPlan) {
		this.cdPlan = cdPlan;
	}

}
