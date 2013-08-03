package mx.com.aon.portal.model;

/**
 * Clase VO que representa la estructura de datos a usarse en 
 * Configuracion de Numeracion de Endosos
 * 
 * @param cdElemento
* @param dsElemen
* @param cdUniEco
* @param dsUniEco
* @param cdRamo
* @param dsRamo 
* @param cdPlan
* @param dsPlan
* @param nmPoliEx
* @param indCalc
* @param nmInicial
* @param nmFinal
* @param nmActual
* @param otExpres
 *
 */
public class ConfiguracionNumeracionEndososVO {

	private String cdElemento;
	private String dsElemen;
	private String cdUniEco;
	private String dsUniEco;
	private String cdRamo;
	private String dsRamo; 
	private String cdPlan;
	private String dsPlan;
	private String nmPoliEx;
	private String indCalc;
	private String nmInicial;
	private String nmFinal;
	private String nmActual;
	private String otExpres;
	private String accion;
	
	
	
	public String getCdElemento() {
		return cdElemento;
	}
	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}
	public String getDsElemen() {
		return dsElemen;
	}
	public void setDsElemen(String dsElemen) {
		this.dsElemen = dsElemen;
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
	public String getCdPlan() {
		return cdPlan;
	}
	public void setCdPlan(String cdPlan) {
		this.cdPlan = cdPlan;
	}
	public String getDsPlan() {
		return dsPlan;
	}
	public void setDsPlan(String dsPlan) {
		this.dsPlan = dsPlan;
	}
	public String getNmPoliEx() {
		return nmPoliEx;
	}
	public void setNmPoliEx(String nmPoliEx) {
		this.nmPoliEx = nmPoliEx;
	}
	public String getIndCalc() {
		return indCalc;
	}
	public void setIndCalc(String indCalc) {
		this.indCalc = indCalc;
	}
	public String getNmInicial() {
		return nmInicial;
	}
	public void setNmInicial(String nmInicial) {
		this.nmInicial = nmInicial;
	}
	public String getNmFinal() {
		return nmFinal;
	}
	public void setNmFinal(String nmFinal) {
		this.nmFinal = nmFinal;
	}
	public String getNmActual() {
		return nmActual;
	}
	public void setNmActual(String nmActual) {
		this.nmActual = nmActual;
	}
	public String getOtExpres() {
		return otExpres;
	}
	public void setOtExpres(String otExpres) {
		this.otExpres = otExpres;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	
}
