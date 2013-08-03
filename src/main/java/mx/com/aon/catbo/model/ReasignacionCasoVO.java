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

public class ReasignacionCasoVO {
	
	 private String numCaso;
	 private String codUsuMov; 
	 private String codUsuario;
	 private String codRolMat;
	 private String cdModulo;
	 
	public String getNumCaso() {
		return numCaso;
	}
	public void setNumCaso(String numCaso) {
		this.numCaso = numCaso;
	}
	public String getCodUsuMov() {
		return codUsuMov;
	}
	public void setCodUsuMov(String codUsuMov) {
		this.codUsuMov = codUsuMov;
	}
	public String getCodUsuario() {
		return codUsuario;
	}
	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}
	public String getCodRolMat() {
		return codRolMat;
	}
	public void setCodRolMat(String codRolMat) {
		this.codRolMat = codRolMat;
	}
	public String getCdModulo() {
		return cdModulo;
	}
	public void setCdModulo(String cdModulo) {
		this.cdModulo = cdModulo;
	}
}



