package mx.com.aon.portal.model.presiniestros;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AutomovilVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4006519323683050446L;
	
	private String folio;
	private String fecha;
	private String poliza;
	private String certificado;
	private String aseguradora;
	private String asegurado;
	private String reportadoPor;
	private String conductor;
	private String telefono1;
	private String telefono2;
	private String telefono3;
	private String marca;
	private String modelo;
	private String numeroMotor;
	private String numeroSerie;
	private String numeroPlacas;
	private String color;
	private String lugarVehiculo;
	private String colonia;
	private String delegacion;
	private String descripcionAccidente;
	private String fechaAccidente;
	private String horaAccidente;
	private String tercero;
	private String taller;
	private String reportoAPersonal;
	private String fechaReportada;
	private String horaReportada;
	private String numeroReporte;
	private String fechaReporte;
	private String horaReporte;

	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getPoliza() {
		return poliza;
	}
	public void setPoliza(String poliza) {
		this.poliza = poliza;
	}
	public String getCertificado() {
		return certificado;
	}
	public void setCertificado(String certificado) {
		this.certificado = certificado;
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
	public String getReportadoPor() {
		return reportadoPor;
	}
	public void setReportadoPor(String reportadoPor) {
		this.reportadoPor = reportadoPor;
	}
	public String getConductor() {
		return conductor;
	}
	public void setConductor(String conductor) {
		this.conductor = conductor;
	}
	public String getTelefono1() {
		return telefono1;
	}
	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}
	public String getTelefono2() {
		return telefono2;
	}
	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}
	public String getTelefono3() {
		return telefono3;
	}
	public void setTelefono3(String telefono3) {
		this.telefono3 = telefono3;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getNumeroMotor() {
		return numeroMotor;
	}
	public void setNumeroMotor(String numeroMotor) {
		this.numeroMotor = numeroMotor;
	}
	public String getNumeroSerie() {
		return numeroSerie;
	}
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}
	public String getNumeroPlacas() {
		return numeroPlacas;
	}
	public void setNumeroPlacas(String numeroPlacas) {
		this.numeroPlacas = numeroPlacas;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getLugarVehiculo() {
		return lugarVehiculo;
	}
	public void setLugarVehiculo(String lugarVehiculo) {
		this.lugarVehiculo = lugarVehiculo;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public String getDelegacion() {
		return delegacion;
	}
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}
	public String getDescripcionAccidente() {
		return descripcionAccidente;
	}
	public void setDescripcionAccidente(String descripcionAccidente) {
		this.descripcionAccidente = descripcionAccidente;
	}
	public String getFechaAccidente() {
		return fechaAccidente;
	}
	public void setFechaAccidente(String fechaAccidente) {
		this.fechaAccidente = fechaAccidente;
	}
	public String getHoraAccidente() {
		return horaAccidente;
	}
	public void setHoraAccidente(String horaAccidente) {
		this.horaAccidente = horaAccidente;
	}
	public String getTercero() {
		return tercero;
	}
	public void setTercero(String tercero) {
		this.tercero = tercero;
	}
	public String getTaller() {
		return taller;
	}
	public void setTaller(String taller) {
		this.taller = taller;
	}
	public String getReportoAPersonal() {
		return reportoAPersonal;
	}
	public void setReportoAPersonal(String reportoAPersonal) {
		this.reportoAPersonal = reportoAPersonal;
	}
	public String getFechaReportada() {
		return fechaReportada;
	}
	public void setFechaReportada(String fechaReportada) {
		this.fechaReportada = fechaReportada;
	}
	public String getHoraReportada() {
		return horaReportada;
	}
	public void setHoraReportada(String horaReportada) {
		this.horaReportada = horaReportada;
	}
	public String getNumeroReporte() {
		return numeroReporte;
	}
	public void setNumeroReporte(String numeroReporte) {
		this.numeroReporte = numeroReporte;
	}
	public String getFechaReporte() {
		return fechaReporte;
	}
	public void setFechaReporte(String fechaReporte) {
		this.fechaReporte = fechaReporte;
	}
	public String getHoraReporte() {
		return horaReporte;
	}
	public void setHoraReporte(String horaReporte) {
		this.horaReporte = horaReporte;
	}
	
}
