package mx.com.aon.catbo.model;


/**
 * 
 * Clase VO usada para obtener un Formato Documento.
 * 
 * @param cdStatus
 * @param dsStatus 
 * @param indAviso 
 * 
 */

public class StatusCasoVO {

	 private String cdStatus;
	 private String dsStatus; 
	 private String indAviso;
	 private String dsIndAviso;

	public String getCdStatus() {
		return cdStatus;
	}

	public void setCdStatus(String cdStatus) {
		this.cdStatus = cdStatus;
	}

	public String getDsStatus() {
		return dsStatus;
	}

	public void setDsStatus(String dsStatus) {
		this.dsStatus = dsStatus;
	}

	public String getIndAviso() {
		return indAviso;
	}

	public void setIndAviso(String indAviso) {
		this.indAviso = indAviso;
	}

	public String getDsIndAviso() {
		return dsIndAviso;
	}

	public void setDsIndAviso(String dsIndAviso) {
		this.dsIndAviso = dsIndAviso;
	} 

	

}



