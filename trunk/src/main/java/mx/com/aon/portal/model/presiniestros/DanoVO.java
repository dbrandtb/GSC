package mx.com.aon.portal.model.presiniestros;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DanoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8602664744995763033L;
	
	private String folio;
	private String producto;
	private String fecha;
	private String poliza;
	private String inciso;
	private String aseguradora;
	private String ramo;
	private String nombreAsegurado;
	private String personaRecibeReporte;
	private String embarqueBienesDanados;
	private String descripcionDano;
	private String fechaDano;
	private String lugarBienesAfectados;
	private String personaEntrevista;
	private String telefono1;
	private String estimacionDano;
	private String personaReporto;
	private String telefono2;

	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
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
	public String getInciso() {
		return inciso;
	}
	public void setInciso(String inciso) {
		this.inciso = inciso;
	}
	public String getAseguradora() {
		return aseguradora;
	}
	public void setAseguradora(String aseguradora) {
		this.aseguradora = aseguradora;
	}
	public String getRamo() {
		return ramo;
	}
	public void setRamo(String ramo) {
		this.ramo = ramo;
	}
	public String getNombreAsegurado() {
		return nombreAsegurado;
	}
	public void setNombreAsegurado(String nombreAsegurado) {
		this.nombreAsegurado = nombreAsegurado;
	}
	public String getPersonaRecibeReporte() {
		return personaRecibeReporte;
	}
	public void setPersonaRecibeReporte(String personaRecibeReporte) {
		this.personaRecibeReporte = personaRecibeReporte;
	}
	public String getEmbarqueBienesDanados() {
		return embarqueBienesDanados;
	}
	public void setEmbarqueBienesDanados(String embarqueBienesDanados) {
		this.embarqueBienesDanados = embarqueBienesDanados;
	}
	public String getDescripcionDano() {
		return descripcionDano;
	}
	public void setDescripcionDano(String descripcionDano) {
		this.descripcionDano = descripcionDano;
	}

	public String getFechaDano() {
		return fechaDano;
	}

	public void setFechaDano(String fechaDano) {
		this.fechaDano = fechaDano;
	}

	public String getLugarBienesAfectados() {
		return lugarBienesAfectados;
	}

	public void setLugarBienesAfectados(String lugarBienesAfectados) {
		this.lugarBienesAfectados = lugarBienesAfectados;
	}

	public String getPersonaEntrevista() {
		return personaEntrevista;
	}

	public void setPersonaEntrevista(String personaEntrevista) {
		this.personaEntrevista = personaEntrevista;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getEstimacionDano() {
		return estimacionDano;
	}

	public void setEstimacionDano(String estimacionDano) {
		this.estimacionDano = estimacionDano;
	}

	public String getPersonaReporto() {
		return personaReporto;
	}

	public void setPersonaReporto(String personaReporto) {
		this.personaReporto = personaReporto;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}
	

}
