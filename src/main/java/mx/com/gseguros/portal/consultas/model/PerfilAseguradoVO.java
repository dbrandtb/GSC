package mx.com.gseguros.portal.consultas.model;

import java.util.List;
import java.util.Map;

public class PerfilAseguradoVO {
	
	private String cdperson;
	private String cantIcd;
	private String maxPerfil;
	private String numPerfil;
	private String perfilFinal;
	private List<Map<String,String>> icds;
	

	/////// Getters y Setters //////
	
	public String getCdperson() {
		return cdperson;
	}
	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}
	public String getPerfilFinal() {
		return perfilFinal;
	}
	public void setPerfilFinal(String perfilFinal) {
		this.perfilFinal = perfilFinal;
	}
	public List<Map<String, String>> getIcds() {
		return icds;
	}
	public void setIcds(List<Map<String, String>> icds) {
		this.icds = icds;
	}
	public String getCantIcd() {
		return cantIcd;
	}
	public void setCantIcd(String cantIcd) {
		this.cantIcd = cantIcd;
	}
	public String getMaxPerfil() {
		return maxPerfil;
	}
	public void setMaxPerfil(String maxPerfil) {
		this.maxPerfil = maxPerfil;
	}
	public String getNumPerfil() {
		return numPerfil;
	}
	public void setNumPerfil(String numPerfil) {
		this.numPerfil = numPerfil;
	}

}
