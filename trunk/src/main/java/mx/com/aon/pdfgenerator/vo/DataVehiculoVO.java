package mx.com.aon.pdfgenerator.vo;

import java.io.Serializable;

public class DataVehiculoVO implements Serializable {
	
	private static final long serialVersionUID = -141575129829544616L;
	
	private String vehiculo;
	private String motor;
	private String serie;
	private String placas;
	private String uso;
	private String servicio;
	private String modelo;
	private String capacidad;
	private String carga;
	private String remolque;
	private String tarifa;
	private String segundoRemol;
	private String descripcion;
	
	
	public String getVehiculo() {
		return vehiculo != null ? vehiculo : "";
	}
	public void setVehiculo(String vehiculo) {
		this.vehiculo = vehiculo;
	}
	public String getMotor() {
		return motor != null ? motor : "";
	}
	public void setMotor(String motor) {
		this.motor = motor;
	}
	public String getSerie() {
		return serie != null ? serie : "";
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getPlacas() {
		return placas != null ? placas : "";
	}
	public void setPlacas(String placas) {
		this.placas = placas;
	}
	public String getUso() {
		return uso != null ? uso : "";
	}
	public void setUso(String uso) {
		this.uso = uso;
	}
	public String getServicio() {
		return servicio != null ? servicio : "";
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	public String getModelo() {
		return modelo != null ? modelo : "";
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getCapacidad() {
		return capacidad != null ? capacidad : "";
	}
	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}
	public String getCarga() {
		return carga != null ? carga : "";
	}
	public void setCarga(String carga) {
		this.carga = carga;
	}
	public String getRemolque() {
		return remolque != null ? remolque : "";
	}
	public void setRemolque(String remolque) {
		this.remolque = remolque;
	}
	public String getTarifa() {
		return tarifa != null ? tarifa : "";
	}
	public void setTarifa(String tarifa) {
		this.tarifa = tarifa;
	}
	public String getSegundoRemol() {
		return segundoRemol != null ? segundoRemol : "";
	}
	public void setSegundoRemol(String segundoRemol) {
		this.segundoRemol = segundoRemol;
	}
	public String getDescripcion() {
		return descripcion != null ? descripcion : "";
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String [] getArrayDataVehiculo(){
		
		String [] data = {
				          getVehiculo(), 
						  getMotor(), 
						  getSerie(), 
						  getPlacas(), 
						  getUso(), 
						  getServicio(), 
						  getModelo(), 
				          getCapacidad(), 
				          getCarga(), 
				          getRemolque(), 
				          getTarifa(), 
				          "", "", "", 
				          getSegundoRemol(), 
				          ""
				          };
		return data;		
	}
}
