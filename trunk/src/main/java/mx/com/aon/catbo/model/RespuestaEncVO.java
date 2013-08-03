package mx.com.aon.catbo.model;


/**
 * 
 * Clase VO usada para la respuesta de una Pregunta de la encuesta.
 * 
 * @param nmConfig
 * @param cdUniEco 
 * @param cdRamo 
 * @param estado
 * @param nmPoliza 
 * @param cdPerson
 * @param cdEncuesta
 */
public class RespuestaEncVO {

    private String nmConfig;
    private String cdUniEco;
    private String cdRamo;
    private String estado;
    private String nmPoliza;
	private String cdPerson;
    private String cdEncuesta;
    
    
	public String getNmConfig() {
		return nmConfig;
	}
	public void setNmConfig(String nmConfig) {
		this.nmConfig = nmConfig;
	}
	public String getCdUniEco() {
		return cdUniEco;
	}
	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}
	public String getCdRamo() {
		return cdRamo;
	}
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNmPoliza() {
		return nmPoliza;
	}
	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}
	public String getCdPerson() {
		return cdPerson;
	}
	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}
	public String getCdEncuesta() {
		return cdEncuesta;
	}
	public void setCdEncuesta(String cdEncuesta) {
		this.cdEncuesta = cdEncuesta;
	}

  
}



