package mx.com.aon.pdfgenerator.vo;

import java.io.Serializable;

public class DataPolizaVO implements Serializable {
	
	private static final long serialVersionUID = -5669904182165174910L;
	
	private String vigencia_del;
	private String formaPago;
	private String fechaEmision;
	private String polizaAnt;
	private String vigencia_al;
	private String endoso;

	public String getVigencia_del() {		
		return vigencia_del != null ? vigencia_del : "";
	}
	public void setVigencia_del(String vigencia_del) {
		this.vigencia_del = vigencia_del;
	}

	public String getFormaPago() {
		return formaPago != null ? formaPago : "";
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getFechaEmision() {
		return fechaEmision != null ? fechaEmision : "";
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getPolizaAnt() {
		return polizaAnt != null ? polizaAnt : "";
	}
	public void setPolizaAnt(String polizaAnt) {
		this.polizaAnt = polizaAnt;
	}

	public String getVigencia_al() {
		return vigencia_al != null ? vigencia_al : "";
	}
	public void setVigencia_al(String vigencia_al) {
		this.vigencia_al = vigencia_al;
	}

	public String getEndoso() {
		return endoso != null ? endoso : "";
	}
	public void setEndoso(String endoso) {
		this.endoso = endoso;
	}

	public String [] getArrayDataPoliza(){
		String [] data = {
				getVigencia_del(), 
				getFormaPago(), 
				getFechaEmision(), 
				getVigencia_al(), 
				"",
				getPolizaAnt(), 
				"", 
				"", 
				getEndoso(), 
				""
				};
		return data;
	}
	
	
}
