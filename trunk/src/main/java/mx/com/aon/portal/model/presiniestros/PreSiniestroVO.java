package mx.com.aon.portal.model.presiniestros;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PreSiniestroVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1996017791081343120L;

    private String folio;
	private String poliza;
    private String inciso;
    private String subinciso;
    private String cdEmpresaOCorporativo;
    private String empresaOCorporativo;
    private String cdAseguradora;
    private String aseguradora;
    private String asegurado;
    private String ramo;
    private String inicioVigencia;
    private String finVigencia;
    private String formaPago;
    private String instrumentoPago;
    private String primaTotal;
    private String tipoPresiniestro; //Codigo del Tipo ramo (cdTipoRa)
    private String cdTipRam;		 //Codigo de la subclasificacion de los tipos de ramo; es decir cada cdTipRam pertenece a un cdTipoRa
    private String numeroSuplemento;
    
    private String cdunieco;
    private String cdramo;
    private String estado;
    private String nmpoliza;
    

	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
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
	public String getEmpresaOCorporativo() {
		return empresaOCorporativo;
	}
	public void setEmpresaOCorporativo(String empresaOCorporativo) {
		this.empresaOCorporativo = empresaOCorporativo;
	}
	public String getAseguradora() {
		return aseguradora;
	}
	public void setAseguradora(String aseguradora) {
		this.aseguradora = aseguradora;
	}
	public String getAsegurado() {
		return asegurado;
	}
	public void setAsegurado(String asegurado) {
		this.asegurado = asegurado;
	}
	public String getRamo() {
		return ramo;
	}
	public void setRamo(String ramo) {
		this.ramo = ramo;
	}
	public String getInicioVigencia() {
		return inicioVigencia;
	}
	public void setInicioVigencia(String inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}
	public String getFinVigencia() {
		return finVigencia;
	}
	public void setFinVigencia(String finVigencia) {
		this.finVigencia = finVigencia;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public String getInstrumentoPago() {
		return instrumentoPago;
	}
	public void setInstrumentoPago(String instrumentoPago) {
		this.instrumentoPago = instrumentoPago;
	}
	public String getCdunieco() {
		return cdunieco;
	}
	public void setCdunieco(String cdunieco) {
		this.cdunieco = cdunieco;
	}
	public String getCdramo() {
		return cdramo;
	}
	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNmpoliza() {
		return nmpoliza;
	}
	public void setNmpoliza(String nmpoliza) {
		this.nmpoliza = nmpoliza;
	}
	public String getCdAseguradora() {
		return cdAseguradora;
	}
	public void setCdAseguradora(String cdAseguradora) {
		this.cdAseguradora = cdAseguradora;
	}
	public String getCdEmpresaOCorporativo() {
		return cdEmpresaOCorporativo;
	}
	public void setCdEmpresaOCorporativo(String cdEmpresaOCorporativo) {
		this.cdEmpresaOCorporativo = cdEmpresaOCorporativo;
	}
	public String getPrimaTotal() {
		return primaTotal;
	}
	public void setPrimaTotal(String primaTotal) {
		this.primaTotal = primaTotal;
	}
	public String getTipoPresiniestro() {
		return tipoPresiniestro;
	}
	public void setTipoPresiniestro(String tipoPresiniestro) {
		this.tipoPresiniestro = tipoPresiniestro;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getCdTipRam() {
		return cdTipRam;
	}

	public void setCdTipRam(String cdTipRam) {
		this.cdTipRam = cdTipRam;
	}

	public String getNumeroSuplemento() {
		return numeroSuplemento;
	}

	public void setNumeroSuplemento(String numeroSuplemento) {
		this.numeroSuplemento = numeroSuplemento;
	}
	
}
