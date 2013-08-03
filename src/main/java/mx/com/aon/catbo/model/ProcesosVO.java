package mx.com.aon.catbo.model;

public class ProcesosVO {
	private String nivelOld;
	private String nivelNew;
	private String validaO;
	private String valor;
	private String descripcion;
	private String cdUsuario;
	private String nmCaso;
	
	public String getNmCaso() {
		return nmCaso;
	}

	public void setNmCaso(String nmCaso) {
		this.nmCaso = nmCaso;
	}

	public String getCdUsuario() {
		return cdUsuario;
	}

	public void setCdUsuario(String cdUsuario) {
		this.cdUsuario = cdUsuario;
	}

	public String getNivelOld() {
		return nivelOld;
	}
	
	public void setNivelOld(String nivelOld) {
		this.nivelOld = nivelOld;
	}
	public String getNivelNew() {
		return nivelNew;
	}
	public void setNivelNew(String nivelNew) {
		this.nivelNew = nivelNew;
	}
	public String getValidaO() {
		return validaO;
	}
	public void setValidaO(String validaO) {
		this.validaO = validaO;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}