package mx.com.aon.catbo.model;


/**
 * 
 * Clase VO usada para obtener un combo Tipo Guion.
 * 
 * @param cdFormato
 * @param dsNomFormato 
 * @param dsFormato 
 * 
 */
public class TipoGuionVO {

    private String codigo;
    private String descripCorta;
    private String descripLarga;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripCorta() {
		return descripCorta;
	}

	public void setDescripCorta(String descripCorta) {
		this.descripCorta = descripCorta;
	}

	public String getDescripLarga() {
		return descripLarga;
	}

	public void setDescripLarga(String descripLarga) {
		this.descripLarga = descripLarga;
	}


}



