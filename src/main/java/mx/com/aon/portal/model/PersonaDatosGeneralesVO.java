package mx.com.aon.portal.model;

import org.apache.commons.lang.StringEscapeUtils;
/**
 * Clase VO usada para obtener los datos generales de la persona
 * 
 * @param codigoPersona             codigo de persona
 * @param codigoTipoPersonaJ        codigo tipo persona
 * @param Nombre                    nombre de la persona 
 * @param ApellidoPaterno           apellido parteno de la persona
 * @param ApellidoMaterno           apellido materno de la persona
 * @param Sexo                      sexo de la persona
 * @param EstadoCivil               estado civil de la persona
 * @param FechaNacimiento           fecha de nacimiento
 * @param Nacionalidad              nacionalidad 
 * @param tipoIdentificador         tipo de identificador
 * @param nroIdentificador          numero de identificador
 * @param tipoEntidad               tipo de entidad
 * @param fechaIngreso              fecha ingreso
 *
 */

public class PersonaDatosGeneralesVO {
	private String codigoPersona;
	private String codigoTipoPersonaJ;
	private String Nombre;
	private String ApellidoPaterno; 
	private String ApellidoMaterno;
	private String Sexo;
	private String EstadoCivil;
	private String FechaNacimiento;
	private String Nacionalidad;
	private String tipoIdentificador;
	private String nroIdentificador;
	private String tipoEntidad;
	private String fechaIngreso;
	private String email;
	private String curp;
	private String rfc;

	public String getTipoIdentificador() {
		return tipoIdentificador;
	}
	public void setTipoIdentificador(String tipoIdentificador) {
		this.tipoIdentificador = tipoIdentificador;
	}
	public String getNroIdentificador() {
		return nroIdentificador;
	}
	public void setNroIdentificador(String nroIdentificador) {
		this.nroIdentificador = nroIdentificador;
	}
	public String getTipoEntidad() {
		return tipoEntidad;
	}
	public void setTipoEntidad(String tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}
	public String getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public String getCodigoPersona() {
		return codigoPersona;
	}
	public void setCodigoPersona(String codigoPersona) {
		this.codigoPersona = codigoPersona;
	}
	public String getCodigoTipoPersonaJ() {
		return codigoTipoPersonaJ;
	}
	public void setCodigoTipoPersonaJ(String codigoTipoPersonaJ) {
		this.codigoTipoPersonaJ = codigoTipoPersonaJ;
	}
	public String getNombre() {
		return StringEscapeUtils.unescapeHtml(Nombre);
	}
	public void setNombre(String nombre) {
		Nombre = StringEscapeUtils.escapeHtml(nombre);
	}
	public String getApellidoPaterno() {
		return StringEscapeUtils.unescapeHtml(ApellidoPaterno);
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		ApellidoPaterno = StringEscapeUtils.escapeHtml(apellidoPaterno);
	}
	public String getApellidoMaterno() {
		return StringEscapeUtils.unescapeHtml(ApellidoMaterno);
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		ApellidoMaterno = StringEscapeUtils.escapeHtml(apellidoMaterno);
	}
	public String getSexo() {
		return Sexo;
	}
	public void setSexo(String sexo) {
		Sexo = sexo;
	}
	public String getEstadoCivil() {
		return EstadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		EstadoCivil = estadoCivil;
	}
	public String getFechaNacimiento() {
		return FechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		FechaNacimiento = fechaNacimiento;
	}
	public String getNacionalidad() {
		return Nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		Nacionalidad = nacionalidad;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}	
}