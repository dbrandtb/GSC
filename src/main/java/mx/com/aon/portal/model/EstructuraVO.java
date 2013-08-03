 package mx.com.aon.portal.model;

/**
 * 
 * Clase VO usada para obtener una estructura.
 * 
 * @param codigo
 * @param descripcion
 *
 */
 public class EstructuraVO  {

	private String descripcion;
	private String codigo;

	private String codigoRegion;
	private String codigoIdioma;
	


	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

	public String getCodigoRegion() {
		return codigoRegion;
	}

	public void setCodigoRegion(String codigoRegion) {
		this.codigoRegion = codigoRegion;
	}

	public String getCodigoIdioma() {
		return codigoIdioma;
	}

	public void setCodigoIdioma(String codigoIdioma) {
		this.codigoIdioma = codigoIdioma;
	}
}

