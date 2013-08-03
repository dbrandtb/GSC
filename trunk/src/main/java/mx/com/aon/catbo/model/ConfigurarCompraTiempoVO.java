package mx.com.aon.catbo.model;

/**
 * Created by IntelliJ IDEA.
 * User: gabrielforradellas
 * Date: Sep 2, 2008
 * Time: 11:49:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurarCompraTiempoVO {
   
private String cdProceso;
private String dsProceso; 
private String nivAtn;
private String nmcantDesde;
private String nmcant_Hasta;
private String tUnidad;
private String nmvecesCompra;
public String getCdProceso() {
	return cdProceso;
}
public void setCdProceso(String cdProceso) {
	this.cdProceso = cdProceso;
}
public String getNivAtn() {
	return nivAtn;
}
public void setNivAtn(String nivAtn) {
	this.nivAtn = nivAtn;
}
public String getNmcantDesde() {
	return nmcantDesde;
}
public void setNmcantDesde(String nmcantDesde) {
	this.nmcantDesde = nmcantDesde;
}
public String getNmcant_Hasta() {
	return nmcant_Hasta;
}
public void setNmcant_Hasta(String nmcant_Hasta) {
	this.nmcant_Hasta = nmcant_Hasta;
}
public String getTUnidad() {
	return tUnidad;
}
public void setTUnidad(String unidad) {
	tUnidad = unidad;
}
public String getNmvecesCompra() {
	return nmvecesCompra;
}
public void setNmvecesCompra(String nmvecesCompra) {
	this.nmvecesCompra = nmvecesCompra;
}
public String getDsProceso() {
	return dsProceso;
}
public void setDsProceso(String dsProceso) {
	this.dsProceso = dsProceso;
}

	
}
