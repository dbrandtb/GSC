
package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener una tarea
 * 
 * @param cdTarea               codigo tarea
 * @param cdSeccion             codigo seccion
 * @param cdTareapadre          codigo tarea padre
 * @param cdEstado              codigo estado
 * @param dsEstado              descripcion estado
 * @param dsUrl			        descripcion url
 * @param cdCopia               codigo copia
 * @param dsAyuda               descripcion ayuda
 *
 */
public class ObtienetareaVO {
    private String cdTarea;
    private String cdSeccion;
    private String cdTareapadre;
	private String cdEstado;
	private String dsEstado; 
	private String dsUrl;
	private String cdCopia;
	private String dsAyuda;


    public String getCdTareapadre() {
        return cdTareapadre;
    }

    public void setCdTareapadre(String cdTareapadre) {
        this.cdTareapadre = cdTareapadre;
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


    public String getCdTarea() {
        return cdTarea;
    }

    public void setCdTarea(String cdTarea) {
        this.cdTarea = cdTarea;
    }

    public String getCdSeccion() {
        return cdSeccion;
    }

    public void setCdSeccion(String cdSeccion) {
        this.cdSeccion = cdSeccion;
    }
}
