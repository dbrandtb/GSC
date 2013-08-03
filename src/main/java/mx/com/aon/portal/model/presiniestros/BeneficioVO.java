package mx.com.aon.portal.model.presiniestros;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BeneficioVO implements Serializable {

	private static final long serialVersionUID = 8084384548976436969L;
	
	
	private String noSiniestroAseg;
	private String folio;
	private String folioAA;
	private String fecha;
	private String cdUnieco;
	private String dsUnieco;
	private String cdAseguradora;
	private String dsAseguradora;
	private String cdRamo;
	private String dsRamo;
	private String cdTipoRamo;
	private String dsTipoRamo;
	private String poliza;
	private String polizaExt;
	private String inciso;
	private String subinciso;
	private String cdTipoTramite;
	private String dsTipoTramite;
	private String fechaPrimerGasto;
	private String cdPadecimiento;
	private String dsPadecimiento;
	private String titular;
	private String empresa;
	private String dsCorporativo;
	private String cdCorporativo;
	private String asegurado;
	private String reportadoPor;
	private String telefonoRep;
	private String descripcionTramite;
	private String observaciones;
	
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public String getNoSiniestroAseg() {
		return noSiniestroAseg;
	}
	public void setNoSiniestroAseg(String noSiniestroAseg) {
		this.noSiniestroAseg = noSiniestroAseg;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getFolioAA() {
		return folioAA;
	}
	public void setFolioAA(String folioAA) {
		this.folioAA = folioAA;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getCdAseguradora() {
		return cdAseguradora;
	}
	public void setCdAseguradora(String cdAseguradora) {
		this.cdAseguradora = cdAseguradora;
	}
	public String getDsAseguradora() {
		return dsAseguradora;
	}
	public void setDsAseguradora(String dsAseguradora) {
		this.dsAseguradora = dsAseguradora;
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
	public String getPoliza() {
		return poliza;
	}
	public void setPoliza(String poliza) {
		this.poliza = poliza;
	}
	public String getInciso() {
		return inciso;
	}
	public void setInciso(String inciso) {
		this.inciso = inciso;
	}
	public String getSubinciso() {
		return subinciso;
	}
	public void setSubinciso(String subinciso) {
		this.subinciso = subinciso;
	}
	public String getCdTipoTramite() {
		return cdTipoTramite;
	}
	public void setCdTipoTramite(String cdTipoTramite) {
		this.cdTipoTramite = cdTipoTramite;
	}
	public String getDsTipoTramite() {
		return dsTipoTramite;
	}
	public void setDsTipoTramite(String dsTipoTramite) {
		this.dsTipoTramite = dsTipoTramite;
	}
	public String getFechaPrimerGasto() {
		return fechaPrimerGasto;
	}
	public void setFechaPrimerGasto(String fechaPrimerGasto) {
		this.fechaPrimerGasto = fechaPrimerGasto;
	}
	public String getCdPadecimiento() {
		return cdPadecimiento;
	}
	public void setCdPadecimiento(String cdPadecimiento) {
		this.cdPadecimiento = cdPadecimiento;
	}
	public String getDsPadecimiento() {
		return dsPadecimiento;
	}
	public void setDsPadecimiento(String dsPadecimiento) {
		this.dsPadecimiento = dsPadecimiento;
	}
	public String getTitular() {
		return titular;
	}
	public void setTitular(String titular) {
		this.titular = titular;
	}
	public String getDsCorporativo() {
		return dsCorporativo;
	}
	public void setDsCorporativo(String dsCorporativo) {
		this.dsCorporativo = dsCorporativo;
	}
	public String getAsegurado() {
		return asegurado;
	}
	public void setAsegurado(String asegurado) {
		this.asegurado = asegurado;
	}
	public String getReportadoPor() {
		return reportadoPor;
	}
	public void setReportadoPor(String reportadoPor) {
		this.reportadoPor = reportadoPor;
	}
	public String getTelefonoRep() {
		return telefonoRep;
	}
	public void setTelefonoRep(String telefonoRep) {
		this.telefonoRep = telefonoRep;
	}
	public String getDescripcionTramite() {
		return descripcionTramite;
	}
	public void setDescripcionTramite(String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getCdUnieco() {
		return cdUnieco;
	}
	public void setCdUnieco(String cdUnieco) {
		this.cdUnieco = cdUnieco;
	}
	public String getDsUnieco() {
		return dsUnieco;
	}
	public void setDsUnieco(String dsUnieco) {
		this.dsUnieco = dsUnieco;
	}
	public String getCdTipoRamo() {
		return cdTipoRamo;
	}
	public void setCdTipoRamo(String cdTipoRamo) {
		this.cdTipoRamo = cdTipoRamo;
	}
	public String getDsTipoRamo() {
		return dsTipoRamo;
	}
	public void setDsTipoRamo(String dsTipoRamo) {
		this.dsTipoRamo = dsTipoRamo;
	}
	public String getPolizaExt() {
		return polizaExt;
	}
	public void setPolizaExt(String polizaExt) {
		this.polizaExt = polizaExt;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCdCorporativo() {
		return cdCorporativo;
	}

	public void setCdCorporativo(String cdCorporativo) {
		this.cdCorporativo = cdCorporativo;
	}
	
		
}
