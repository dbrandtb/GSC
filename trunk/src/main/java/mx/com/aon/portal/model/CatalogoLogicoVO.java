package mx.com.aon.portal.model;

public class CatalogoLogicoVO {
	
	private String cdtabla;
	private String cdregion;
	private String dsregion;
	private String cdidioma;
	private String dsidioma;
	private String codigo;
	private String descripcion;
	private String descripcionLarga;
	private String etiqueta;
	
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getCdtabla() {
		return cdtabla;
	}
	public void setCdtabla(String cdtabla) {
		this.cdtabla = cdtabla;
	}
	public String getCdregion() {
		return cdregion;
	}
	public void setCdregion(String cdregion) {
		this.cdregion = cdregion;
	}
	public String getDsregion() {
		return dsregion;
	}
	public void setDsregion(String dsregion) {
		this.dsregion = dsregion;
	}
	public String getCdidioma() {
		return cdidioma;
	}
	public void setCdidioma(String cdidioma) {
		this.cdidioma = cdidioma;
	}
	public String getDsidioma() {
		return dsidioma;
	}
	public void setDsidioma(String dsidioma) {
		this.dsidioma = dsidioma;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDescripcionLarga() {
		return descripcionLarga;
	}
	public void setDescripcionLarga(String descripcionLarga) {
		this.descripcionLarga = descripcionLarga;
	}
	public String getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

}
