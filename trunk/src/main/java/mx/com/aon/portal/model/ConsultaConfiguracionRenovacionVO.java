package mx.com.aon.portal.model;


/**
 * Clase VO usada para obtener configuracion de la  renovacion
 * 
 * @param cdRenova              codigo renovacion
 * @param cdElemento            codigo cliente 
 * @param dsElemen              nombre cliente
 * @param cdPerson              codigo persona
 * @param cdTipoRenova          codigo tipo renovacion
 * @param dsTipoRenova          descripcion tipo renovacion
 * @param cdUniEco              codigo aseguradora
 * @param dsUniEco              nombre aseguradora
 * @param cdRamo                codigo producto    
 * @param dsRamo                nombre producto
 * @param cdDiasAnticipacion    codigo de dias de anticipacion    
 */

public class ConsultaConfiguracionRenovacionVO {
	private String cdRenova;
	private String cdElemento;
	private String dsElemen;
	private String cdPerson;
	private String cdTipoRenova;
	private String dsTipoRenova;
	private String cdUniEco;
	private String dsUniEco;
	private String cdRamo;
	private String dsRamo;
	private String cdDiasAnticipacion;
	
	
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
	public String getCdRenova() {
		return cdRenova;
	}
	public void setCdRenova(String cdRenova) {
		this.cdRenova = cdRenova;
	}
	public String getCdDiasAnticipacion() {
		return cdDiasAnticipacion;
	}
	public void setCdDiasAnticipacion(String cdDiasAnticipacion) {
		this.cdDiasAnticipacion = cdDiasAnticipacion;
	}
	public String getCdPerson() {
		return cdPerson;
	}
	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

}
