package mx.com.aon.pdfgenerator.vo;

import java.io.Serializable;

public class AseguradoVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8865344998712696819L;
	
	private String nombre;
	private String cdRFC;
	private String asegSecundario;
	private String direccion;
	private String telefono;
	private String benefPref;
	private String cuenta;
	
	
	public String getNombre() {
		return nombre != null ? nombre : "";
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCdRFC() {
		return cdRFC != null ? cdRFC : "";
	}
	public void setCdRFC(String cdRFC) {
		this.cdRFC = cdRFC;
	}
	public String getDireccion() {
		return direccion != null ? direccion : "";
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono != null ? telefono : "";
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}	
	public String getBenefPref() {
		return benefPref != null ? benefPref : "";
	}
	public void setBenefPref(String benefPref) {
		this.benefPref = benefPref;
	}
	public String getCuenta() {
		return cuenta != null ? cuenta : "";
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	
	public String [] getArrayDataAsegurado_CP(){
		
		String [] data = {
						  getNombre(), 
						  getAsegSecundario(), 
						  getDireccion(), 
						  getBenefPref(),		  						  
						  "", 
						  "", 
				          "", 
				          "", 
						  "",
						  getCdRFC(), 
						  getTelefono(), 
						  "  ", 
						  "  ", 
				          "  ", 				          
				          ""
				          };
		return data;		
	}

	public String [] getArrayDataAsegurado_Pago(){
		
		String [] dataPago = {
						  getNombre(), 
						  getAsegSecundario(), 
						  getDireccion(), 		
						  "",	  
						  getCdRFC(), 
						  getTelefono(), 						 				         
				          ""
				          };
		return dataPago;		
	}
	public String getAsegSecundario() {
		return asegSecundario != null ? asegSecundario : "*";
	}
	public void setAsegSecundario(String asegSecundario) {
		this.asegSecundario = asegSecundario;
	}
}
