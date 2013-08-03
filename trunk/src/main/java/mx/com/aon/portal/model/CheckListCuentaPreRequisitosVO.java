package mx.com.aon.portal.model;

/**
 * 
 * Clase VO usada para obtener una estructura.
 * 
 * @param cdPerson              codigo persona
 * @param dsConfig              descripcion configuracion  
 * @param cdConfig              codigo configuracion
 * @param cdEstado              codigo estado
 * @param cdLinOpe              codigo linea operatoria
 * @param cdSeccion             codigo seccion 
 * @param cdTarea               codigo tarea
 * @param cdCompletada          codigo completada
 * @param noRequerida           dicotomia de requerida 
 */

public class CheckListCuentaPreRequisitosVO {
	private String cdPerson;
	private String dsConfig;
	private String cdConfig;
	private String cdEstado;
	private String cdLinOpe;
	private String cdSeccion;
	private String cdTarea;
	private String cdCompletada;
	private String noRequerida;
	private String cdPendiente;


    public String getCdPerson() {
        return cdPerson;
    }

    public void setCdPerson(String cdPerson) {
        this.cdPerson = cdPerson;
    }

    public String getDsConfig() {
        return dsConfig;
    }

    public void setDsConfig(String dsConfig) {
        this.dsConfig = dsConfig;
    }

    public String getCdConfig() {
        return cdConfig;
    }

    public void setCdConfig(String cdConfig) {
        this.cdConfig = cdConfig;
    }

    public String getCdEstado() {
        return cdEstado;
    }

    public void setCdEstado(String cdEstado) {
        this.cdEstado = cdEstado;
    }

    public String getCdLinOpe() {
        return cdLinOpe;
    }

    public void setCdLinOpe(String cdLinOpe) {
        this.cdLinOpe = cdLinOpe;
    }

    public String getCdSeccion() {
        return cdSeccion;
    }

    public void setCdSeccion(String cdSeccion) {
        this.cdSeccion = cdSeccion;
    }

    public String getCdTarea() {
        return cdTarea;
    }

    public void setCdTarea(String cdTarea) {
        this.cdTarea = cdTarea;
    }

    public String getCdCompletada() {
        return cdCompletada;
    }

    public void setCdCompletada(String cdCompletada) {
        this.cdCompletada = cdCompletada;
    }

    public String getNoRequerida() {
        return noRequerida;
    }

    public void setNoRequerida(String noRequerida) {
        this.noRequerida = noRequerida;
    }

	public String getCdPendiente() {
		return cdPendiente;
	}

	public void setCdPendiente(String cdPendiente) {
		this.cdPendiente = cdPendiente;
	}
}
