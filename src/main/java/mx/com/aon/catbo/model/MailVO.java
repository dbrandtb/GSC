package mx.com.aon.catbo.model;

public class MailVO {
	private String proceso;
	private String numeroCaso;
	private String estado;
	private String prioridad;
	private String ingresadoPor;
	private String solicitante;
	private String fechaRegistro;
	private String fechaActualizacion;
	private String observacion;
	private String tiempoRestante;

	public String getTiempoRestante() {
		return tiempoRestante;
	}
	public void setTiempoRestante(String tiempoRestante) {
		this.tiempoRestante = tiempoRestante;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public String getNumeroCaso() {
		return numeroCaso;
	}
	public void setNumeroCaso(String numeroCaso) {
		this.numeroCaso = numeroCaso;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	public String getIngresadoPor() {
		return ingresadoPor;
	}
	public void setIngresadoPor(String ingresadoPor) {
		this.ingresadoPor = ingresadoPor;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

}
