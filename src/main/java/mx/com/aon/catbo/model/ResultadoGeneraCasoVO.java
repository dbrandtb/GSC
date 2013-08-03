package mx.com.aon.catbo.model;

public class ResultadoGeneraCasoVO {
	
	private String numCaso;
	private String cdOrdenTrabajo;
	private String cdUsuario;
    private String cdFolcia;
    public String getNumCaso() {
		return numCaso;
	}
	public void setNumCaso(String numCaso) {
		this.numCaso = numCaso;
	}
	public String getCdOrdenTrabajo() {
		return cdOrdenTrabajo;
	}
	public void setCdOrdenTrabajo(String cdOrdenTrabajo) {
		this.cdOrdenTrabajo = cdOrdenTrabajo;
	}
	public String getCdUsuario() {
		return cdUsuario;
	}
	public void setCdUsuario(String cdUsuario) {
		this.cdUsuario = cdUsuario;
	}


    public String getCdFolcia() {
        return cdFolcia;
    }

    public void setCdFolcia(String cdFolcia) {
        this.cdFolcia = cdFolcia;
    }
}
