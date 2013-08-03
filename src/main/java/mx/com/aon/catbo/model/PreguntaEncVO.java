package mx.com.aon.catbo.model;


/**
 * 
 * Clase VO usada para obtener una Pregunta de la encuesta.
 * 
 * @param cdPregunta
 * @param dsPregunta 
 * @param otTapVal 
 * @param swObliga
 * @param cdSecuencia 
 * @param cdDefault
 */
public class PreguntaEncVO {

    private String cdPregunta;
    private String dsPregunta;
    private String otTapVal;
    private String swObliga;
    private String cdSecuencia;
    private String cdDefault;

    
    
	public String getCdPregunta() {
		return cdPregunta;
	}
	public void setCdPregunta(String cdPregunta) {
		this.cdPregunta = cdPregunta;
	}
	public String getDsPregunta() {
		return dsPregunta;
	}
	public void setDsPregunta(String dsPregunta) {
		this.dsPregunta = dsPregunta;
	}
	public String getOtTapVal() {
		return otTapVal;
	}
	public void setOtTapVal(String otTapVal) {
		this.otTapVal = otTapVal;
	}
	public String getSwObliga() {
		return swObliga;
	}
	public void setSwObliga(String swObliga) {
		this.swObliga = swObliga;
	}
	public String getCdSecuencia() {
		return cdSecuencia;
	}
	public void setCdSecuencia(String cdSecuencia) {
		this.cdSecuencia = cdSecuencia;
	}
	public String getCdDefault() {
		return cdDefault;
	}
	public void setCdDefault(String cdDefault) {
		this.cdDefault = cdDefault;
	}

  
}



