package mx.com.aon.portal.model;

/**
 * Clase VO con la estructura de datos a usarse en Configuracion de la Renovacion.
 *
 * @param cdRenova
 * @param cdRenovacdElemento
 * @param cdRenovadsElemen
 * @param cdRenovacdUniEco
 * @param cdRenovadsUniEco
 * @param cdRenovacdRamo
 * @param cdRenovadsRamo
 * @param cdRenovacdTipoRenova
 * @param cdRenovadsTipoRenova
 * @param cdRenovacdDiasAnticipacion
 * @param swConNum
 */
public class ConfiguracionClienteVO {
	
	private String cdRenova;
	private String cdElemento;
	private String dsElemen;
	private String cdUniEco;
	private String dsUniEco;
	private String cdRamo;
	private String dsRamo;
	private String cdTipoRenova;
	private String dsTipoRenova;
	private String cdDiasAnticipacion;
	private String swConNum; //Determina si se continua la numeración automaticamnte o si esta costumizada desde la ventana de numeracion de polizas
	
	
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
	public String getCdTipoRenova() {
		return cdTipoRenova;
	}
	public void setCdTipoRenova(String cdTipoRenova) {
		this.cdTipoRenova = cdTipoRenova;
	}
	public String getDsTipoRenova() {
		return dsTipoRenova;
	}
	public void setDsTipoRenova(String dsTipoRenova) {
		this.dsTipoRenova = dsTipoRenova;
	}
	public String getCdDiasAnticipacion() {
		return cdDiasAnticipacion;
	}
	public void setCdDiasAnticipacion(String cdDiasAnticipacion) {
		this.cdDiasAnticipacion = cdDiasAnticipacion;
	}
	public String getCdRenova() {
		return cdRenova;
	}
	public void setCdRenova(String cdRenova) {
		this.cdRenova = cdRenova;
	}
	public String getSwConNum() {
		return swConNum;
	}
	public void setSwConNum(String swConNum) {
		this.swConNum = swConNum;
	}

	
}