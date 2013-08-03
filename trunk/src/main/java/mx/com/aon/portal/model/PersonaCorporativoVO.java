package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener los datos de la persona corporativa
 * 
 * @param codigoPersona       codigo persona
 * @param cdElemen            codigo elemento  
 * @param dsElemen            descripcion elemento
 * @param cdGrupoPer          codigo grupo de personas
 * @param dsGrupo             descripcion grupo de personas
 * @param feInicio            fecha inicio
 * @param feFin               fecha finalizacion  
 * @param cdStatus            codigo de estatus
 * @param dsStatus            descripcion del estatus
 */
public class PersonaCorporativoVO {

	private String codigoPersona;
	private String cdElemen;
	private String dsElemen;
	private String cdGrupoPer;
	private String dsGrupo;
	private String feInicio;
	private String feFin;
	private String cdStatus;
	private String dsStatus;
	private String nmNomina;
	
	public String getNmNomina() {
		return nmNomina;
	}
	public void setNmNomina(String nmNomina) {
		this.nmNomina = nmNomina;
	}
	public String getCdElemen() {
		return cdElemen;
	}
	public void setCdElemen(String cdElemen) {
		this.cdElemen = cdElemen;
	}
	public String getDsElemen() {
		return dsElemen;
	}
	public void setDsElemen(String dsElemen) {
		this.dsElemen = dsElemen;
	}
	public String getCdGrupoPer() {
		return cdGrupoPer;
	}
	public void setCdGrupoPer(String cdGrupoPer) {
		this.cdGrupoPer = cdGrupoPer;
	}
	public String getDsGrupo() {
		return dsGrupo;
	}
	public void setDsGrupo(String dsGrupo) {
		this.dsGrupo = dsGrupo;
	}
	public String getFeInicio() {
		return feInicio;
	}
	public void setFeInicio(String feInicio) {
		this.feInicio = feInicio;
	}
	public String getFeFin() {
		return feFin;
	}
	public void setFeFin(String feFin) {
		this.feFin = feFin;
	}
	public String getCdStatus() {
		return cdStatus;
	}
	public void setCdStatus(String cdStatus) {
		this.cdStatus = cdStatus;
	}
	public String getDsStatus() {
		return dsStatus;
	}
	public void setDsStatus(String dsStatus) {
		this.dsStatus = dsStatus;
	}
	public String getCodigoPersona() {
		return codigoPersona;
	}
	public void setCodigoPersona(String codigoPersona) {
		this.codigoPersona = codigoPersona;
	}

}
