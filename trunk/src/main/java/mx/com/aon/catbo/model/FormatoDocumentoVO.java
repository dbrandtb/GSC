package mx.com.aon.catbo.model;


/**
 * 
 * Clase VO usada para obtener un Formato Documento.
 * 
 * @param cdFormato
 * @param dsNomFormato 
 * @param dsFormato 
 * 
 */
public class FormatoDocumentoVO {

    private String cdFormato;
    private String dsNomFormato;
    private String dsFormato;

    public String getCdFormato() {
		return cdFormato;
	}

	public void setCdFormato(String cdFormato) {
		this.cdFormato = cdFormato;
	}

	public String getDsNomFormato() {
		return dsNomFormato;
	}

	public void setDsNomFormato(String dsNomFormato) {
		this.dsNomFormato = dsNomFormato;
	}

	public String getDsFormato() {
		return dsFormato;
	}

	public void setDsFormato(String dsFormato) {
		this.dsFormato = dsFormato;
	}
}



