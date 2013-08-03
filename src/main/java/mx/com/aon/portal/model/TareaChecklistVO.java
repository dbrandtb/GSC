package mx.com.aon.portal.model;

public class TareaChecklistVO{
	private String cdSeccion;
	private String dsSeccion;	
	private String cdTarea;
	private String dsTarea;
	private String cdTareapadre;
	private String dsTareapadre;
	private String cdEstado;
	private String dsEstado;
	private String dsUrl;
	private String cdCopia;
	private String dsAyuda;


    public String getCdSeccion() {
        return cdSeccion;
    }

    public void setCdSeccion(String cdSeccion) {
        this.cdSeccion = cdSeccion;
    }

    public String getDsSeccion() {
        return dsSeccion;
    }

    public void setDsSeccion(String dsSeccion) {
        this.dsSeccion = dsSeccion;
    }

    public String getCdTarea() {
        return cdTarea;
    }

    public void setCdTarea(String cdTarea) {
        this.cdTarea = cdTarea;
    }

    public String getDsTarea() {
        return dsTarea;
    }

    public void setDsTarea(String dsTarea) {
        this.dsTarea = dsTarea;
    }

    public String getCdTareapadre() {
        return cdTareapadre;
    }

    public void setCdTareapadre(String cdTareapadre) {
        this.cdTareapadre = cdTareapadre;
    }

    public String getDsTareapadre() {
        return dsTareapadre;
    }

    public void setDsTareapadre(String dsTareapadre) {
        this.dsTareapadre = dsTareapadre;
    }

    public String getCdEstado() {
        return cdEstado;
    }

    public void setCdEstado(String cdEstado) {
        this.cdEstado = cdEstado;
    }

    public String getDsEstado() {
        return dsEstado;
    }

    public void setDsEstado(String dsEstado) {
        this.dsEstado = dsEstado;
    }

	public String getDsUrl() {
		return dsUrl;
	}

	public void setDsUrl(String dsUrl) {
		this.dsUrl = dsUrl;
	}

	public String getCdCopia() {
		return cdCopia;
	}

	public void setCdCopia(String cdCopia) {
		this.cdCopia = cdCopia;
	}

	public String getDsAyuda() {
		return dsAyuda;
	}

	public void setDsAyuda(String dsAyuda) {
		this.dsAyuda = dsAyuda;
	}
}
