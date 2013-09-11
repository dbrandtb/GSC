package mx.com.gseguros.portal.consultas.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author hector.lopez
 *
 */
public class ConsultaPolizaAseguradoVO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8555353864912795413L;

	private String compania;
	private String descripcion;
	private String cdramo;
	private String dsramo;
	private String estado;
	private String nmpoliza;
	private String nombre;
	
	public String getCompania() {
		return compania;
	}
	public void setCompania(String compania) {
		this.compania = compania;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCdramo() {
		return cdramo;
	}
	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}
	public String getDsramo() {
		return dsramo;
	}
	public void setDsramo(String dsramo) {
		this.dsramo = dsramo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNmpoliza() {
		return nmpoliza;
	}
	public void setNmpoliza(String nmpoliza) {
		this.nmpoliza = nmpoliza;
	}

	
		
}
