package mx.com.aon.pdfgenerator.vo;

import java.io.Serializable;

public class DataCoberturasVO implements Serializable {

	private static final long serialVersionUID = 3149294729465222064L;
	
	private String cobertura;
	private String limiteMaxRespon;
	private String deducible;
	private String  prima;
	
	public String getCobertura() {
		return cobertura != null ? cobertura : "";
	}
	public void setCobertura(String cobertura) {
		this.cobertura = cobertura;
	}
	public String getLimiteMaxRespon() {
		return limiteMaxRespon != null ? limiteMaxRespon : "";
	}
	public void setLimiteMaxRespon(String limiteMaxRespon) {
		this.limiteMaxRespon = limiteMaxRespon;
	}
	public String getDeducible() {
		return deducible != null ? deducible : "";
	}
	public void setDeducible(String deducible) {
		this.deducible = deducible;
	}
	public String getPrima() {
		return prima != null ? prima : "";
	}
	public void setPrima(String prima) {
		this.prima = prima;
	}
		
	
}
