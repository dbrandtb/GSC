package mx.com.aon.flujos.cotizacion.model;

import java.io.Serializable;

public class RolVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1864595350520460917L;
	private String identificador;
	private String codigoRol;			//cdRol
	private String descripcionRol;		//dsRol
	private String codigoNivel;			//cdNivel
	private String codigoCompo;			//cdCompo
	private String numeroMaximo;		//nMaximo
	private String switchDomicilio;		//swDomici
	private String codigoPersona;		//cdPerson
	private String descripcionNombre;	//dsNombre
	private String codigoRFC;			//cdRFC
	private String fechaNacimiento;		//feNacim
	private String otFisJur;			//otFisJur
	private String dsFisJur;			//dsFisJur
	private String value;
	private String code;
	private String requerido;		    //SWOBLIGA
	
	//Getters && Setters
	public String getCodigoRol() { return codigoRol; }
	public void setCodigoRol(String codigoRol) { this.codigoRol = codigoRol; }
	public String getDescripcionRol() { return descripcionRol; }
	public void setDescripcionRol(String descripcionRol) { this.descripcionRol = descripcionRol; }
	public String getCodigoNivel() { return codigoNivel; }
	public void setCodigoNivel(String codigoNivel) { this.codigoNivel = codigoNivel; }
	public String getCodigoCompo() { return codigoCompo; }
	public void setCodigoCompo(String codigoCompo) { this.codigoCompo = codigoCompo; }
	public String getNumeroMaximo() { return numeroMaximo; }
	public void setNumeroMaximo(String numeroMaximo) { this.numeroMaximo = numeroMaximo; }
	public String getSwitchDomicilio() { return switchDomicilio; }
	public void setSwitchDomicilio(String switchDomicilio) { this.switchDomicilio = switchDomicilio; }
	public String getCodigoPersona() { return codigoPersona; }
	public void setCodigoPersona(String codigoPersona) { this.codigoPersona = codigoPersona; }
	public String getDescripcionNombre() { return descripcionNombre; }
	public void setDescripcionNombre(String descripcionNombre) { this.descripcionNombre = descripcionNombre; }
	public String getCodigoRFC() { return codigoRFC; }
	public void setCodigoRFC(String codigoRFC) { this.codigoRFC = codigoRFC; }
	public String getFechaNacimiento() { return fechaNacimiento; }
	public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
	public String getOtFisJur() { return otFisJur; }
	public void setOtFisJur(String otFisJur) { this.otFisJur = otFisJur; }
	public String getDsFisJur() { return dsFisJur; }
	public void setDsFisJur(String dsFisJur) { this.dsFisJur = dsFisJur; }
	public String getValue() { return value; }
	public void setValue(String value) { this.value = value; }
	public String getIdentificador() { return identificador; }
	public void setIdentificador(String identificador) { this.identificador = identificador; }
	public String getCode() { return code; }
	public void setCode(String code) { this.code = code; }
	public String getRequerido() { return requerido; }
	public void setRequerido(String requerido) { this.requerido = requerido; }
}
