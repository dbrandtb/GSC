package mx.com.aon.portal.model;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Clase VO usada para obtener los datos generales de la persona
 * 
 * @param otFisJur             codigo de fisica o jurica
 * @param dsFisJur             descripcion de fisica o juridica
 * @param dsNombre             nombre de la persona 
 * @param dsApellido           apellido parteno de la persona
 * @param dsApellido1          apellido materno de la persona
 * @param otSexo               sexo de la persona
 * @param cdEstCiv             estado civil de la persona
 * @param feNacimi             fecha de nacimiento
 * @param cdNacion             nacionalidad 
 * @param cdTipPer             codigo de tipo de persona
 * @param dsTipPer             descripcion del tipo de persona fisica juridica
 * @param cdTipIde             codigo del tipo de identificacion
 * @param cdIdePer             codigo de identificacion persona
 * @param feIngreso            fecha ingreso
 */
public class PersonaVO {

	private String otFisJur;
	private String dsFisJur;
	private String dsNombre;
	private String dsApellido;
	private String dsApellido1;
	private String otSexo;
	private String cdEstCiv;
	private String feNacimi;
	private String cdNacion;

	//Para persona jurídica
	private String cdTipPer;
	private String dsTipPer;
	private String cdTipIde;
	private String cdIdePer;
	private String feIngreso;
	
	private String curp;
	private String cdRfc;
	private String dsEmail;

	public String getOtFisJur() {
		return otFisJur;
	}
	public void setOtFisJur(String otFisJur) {
		this.otFisJur = otFisJur;
	}
	public String getDsFisJur() {
		return dsFisJur;
	}
	public void setDsFisJur(String dsFisJur) {
		this.dsFisJur = dsFisJur;
	}
	public String getDsNombre() {
		return StringEscapeUtils.unescapeHtml(dsNombre);
	}
	public void setDsNombre(String dsNombre) {
		this.dsNombre = StringEscapeUtils.escapeHtml(dsNombre);
	}
	public String getDsApellido() {
		return StringEscapeUtils.unescapeHtml(dsApellido);
	}
	public void setDsApellido(String dsApellido) {
		this.dsApellido = StringEscapeUtils.escapeHtml(dsApellido);
	}
	public String getDsApellido1() {
		return StringEscapeUtils.unescapeHtml(dsApellido1);
	}
	public void setDsApellido1(String dsApellido1) {
		this.dsApellido1 = StringEscapeUtils.escapeHtml(dsApellido1);
	}
	public String getOtSexo() {
		return otSexo;
	}
	public void setOtSexo(String otSexo) {
		this.otSexo = otSexo;
	}
	public String getCdEstCiv() {
		return cdEstCiv;
	}
	public void setCdEstCiv(String cdEstCiv) {
		this.cdEstCiv = cdEstCiv;
	}
	public String getFeNacimi() {
		return feNacimi;
	}
	public void setFeNacimi(String feNacimi) {
		this.feNacimi = feNacimi;
	}
	public String getCdNacion() {
		return cdNacion;
	}
	public void setCdNacion(String cdNacion) {
		this.cdNacion = cdNacion;
	}
	public String getCdTipPer() {
		return cdTipPer;
	}
	public void setCdTipPer(String cdTipPer) {
		this.cdTipPer = cdTipPer;
	}
	public String getDsTipPer() {
		return dsTipPer;
	}
	public void setDsTipPer(String dsTipPer) {
		this.dsTipPer = dsTipPer;
	}
	public String getCdTipIde() {
		return cdTipIde;
	}
	public void setCdTipIde(String cdTipIde) {
		this.cdTipIde = cdTipIde;
	}
	public String getCdIdePer() {
		return cdIdePer;
	}
	public void setCdIdePer(String cdIdePer) {
		this.cdIdePer = cdIdePer;
	}
	public String getFeIngreso() {
		return feIngreso;
	}
	public void setFeIngreso(String feIngreso) {
		this.feIngreso = feIngreso;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getCdRfc() {
		return cdRfc;
	}
	public void setCdRfc(String cdRfc) {
		this.cdRfc = cdRfc;
	}
	public String getDsEmail() {
		return dsEmail;
	}
	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}

}
