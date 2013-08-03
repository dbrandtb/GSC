package mx.com.aon.portal.model;

public class CheckListXCuentaSeccion {
	private String cdSeccion;
	private String dsSeccion;
	/**
	 * Atributos necesarios para crear tabs en ExtJS
	 */
	private String title;
	private String id;

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
}
