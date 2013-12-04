package mx.com.gseguros.portal.consultas.model;

import java.io.Serializable;

public class CopagoVO implements Serializable {
	
	private static final long serialVersionUID = -57318621458532287L;

	public CopagoVO() {
		super();
	}
	
	public CopagoVO(int orden, String descripcion, String valor) {
		super();
		this.orden = orden;
		this.descripcion = descripcion;
		this.valor = valor;
	}

	private int orden;
	
	private String descripcion;
	
	private String valor;

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
}
