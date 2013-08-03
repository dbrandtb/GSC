package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener los datos correspondientes al telefono de la persona
 * 
 * @param codigoPersona           codigo de persona
 * @param numeroOrden             numero de orden
 * @param codTipTel               codigo tipo de telefono 
 * @param dsTipTel                descripcion tipo de telefono
 * @param codArea                 codigo de area del telefono
 * @param numTelef                numero de telefono de la persona
 * @param numExtens               numero de extension
 * 
 */
public class TelefonoVO {
	private String codigoPersona;
	private String numeroOrden;
	private String codTipTel;
	private String dsTipTel;
	private String codArea;
	private String numTelef;
	private String numExtens;
	public String getNumeroOrden() {
		return numeroOrden;
	}
	public void setNumeroOrden(String numeroOrden) {
		this.numeroOrden = numeroOrden;
	}
	public String getCodTipTel() {
		return codTipTel;
	}
	public void setCodTipTel(String codTipTel) {
		this.codTipTel = codTipTel;
	}
	public String getDsTipTel() {
		return dsTipTel;
	}
	public void setDsTipTel(String dsTipTel) {
		this.dsTipTel = dsTipTel;
	}
	public String getCodArea() {
		return codArea;
	}
	public void setCodArea(String codArea) {
		this.codArea = codArea;
	}
	public String getNumTelef() {
		return numTelef;
	}
	public void setNumTelef(String numTelef) {
		this.numTelef = numTelef;
	}
	public String getNumExtens() {
		return numExtens;
	}
	public void setNumExtens(String numExtens) {
		this.numExtens = numExtens;
	}
	public String getCodigoPersona() {
		return codigoPersona;
	}
	public void setCodigoPersona(String codigoPersona) {
		this.codigoPersona = codigoPersona;
	}

}
