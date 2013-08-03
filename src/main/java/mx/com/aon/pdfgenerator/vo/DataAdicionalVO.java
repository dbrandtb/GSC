package mx.com.aon.pdfgenerator.vo;

import java.io.Serializable;

public class DataAdicionalVO implements Serializable{

	private static final long serialVersionUID = 510408042321841515L;
	
	private String agente;
	private String ordenTrabajo;
	private String contrato;
	private String otAgente;
	private String primaNeta;
	private String tasaFinan;	
	private String gastosExp;
	private String iva;
	private String primaTotal;
	private String primaTotalCalculo;
	
	public String getAgente() {
		return agente != null ? agente : "";
	}
	public void setAgente(String agente) {
		this.agente = agente;
	}
	public String getOrdenTrabajo() {
		return ordenTrabajo != null ? ordenTrabajo : "";
	}
	public void setOrdenTrabajo(String ordenTrabajo) {
		this.ordenTrabajo = ordenTrabajo;
	}
	public String getContrato() {
		return contrato != null ? contrato : "";
	}
	public void setContrato(String contrato) {
		this.contrato = contrato;
	}
	public String getOtAgente() {
		return otAgente != null ? otAgente : "";
	}
	public void setOtAgente(String otAgente) {
		this.otAgente = otAgente;
	}
	public String getPrimaNeta() {
		return primaNeta != null ? primaNeta : "";
	}
	public void setPrimaNeta(String primaNeta) {
		this.primaNeta = primaNeta;
	}
	public String getTasaFinan() {
		return tasaFinan != null ? tasaFinan : "";
	}
	public void setTasaFinan(String tasaFinan) {
		this.tasaFinan = tasaFinan;
	}
	public String getGastosExp() {
		return gastosExp != null ? gastosExp : "";
	}
	public void setGastosExp(String gastosExp) {
		this.gastosExp = gastosExp;
	}
	public String getIva() {
		return iva != null ? iva : "";
	}
	public void setIva(String iva) {
		this.iva = iva;
	}
	public String getPrimaTotal() {
		return primaTotal != null ? primaTotal : "";
	}
	public void setPrimaTotal(String primaTotal) {
		this.primaTotal = primaTotal;
	}
		
	public String [] getArrayDataAdicional(){
		String [] data = {
				getAgente(), 
				getOrdenTrabajo(), 
				getContrato(), 				
				getOtAgente(),
				getPrimaNeta(), 
				getTasaFinan(), 
				getGastosExp(), 
				getIva(), 
				getPrimaTotal(),
				""
				};
		return data;
	}
	public String getPrimaTotalCalculo() {
		return primaTotalCalculo != null ? primaTotalCalculo : "";
	}
	public void setPrimaTotalCalculo(String primaTotalCalculo) {
		this.primaTotalCalculo = primaTotalCalculo;
	}
	
}
