package mx.com.aon.catbo.model;

public class PreguntaEncuestaVO {
	 
	 private String cdEncuesta;
	 private String cdPregunta;
	 private String dsPregunta;
	 private String ottApval;
	 private String swObliga;
	 private String cdDefault;
	 private String cdSecuencia;
	 private String dsLabel;
	 
	 
	public String getCdEncuesta() {
		return cdEncuesta;
	}
	public void setCdEncuesta(String cdEncuesta) {
		this.cdEncuesta = cdEncuesta;
	}
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
	/*public String getOttApval() {
		return ottApval;
	}
	public void setOttApval(String ottApval) {
		this.ottApval = ottApval;
	}*/
	public String getSwObliga() {
		return swObliga;
	}
	public void setSwObliga(String swObliga) {
		//this.swObliga = swObliga;
		if (swObliga.equals("S")) {
			this.swObliga = "true";
			return;
		};
		if (swObliga.equals("N")) {
			this.swObliga = "false";
			return;
		};
		this.swObliga = swObliga;
	}
	
	public void _setSwObliga(String swObliga) {
		this.swObliga = swObliga;
	}
	
	
	public String getCdDefault() {
		return cdDefault;
	}
	public void setCdDefault(String cdDefault) {
		this.cdDefault = cdDefault;
	}
	public String getCdSecuencia() {
		return cdSecuencia;
	}
	public void setCdSecuencia(String cdSecuencia) {
		this.cdSecuencia = cdSecuencia;
	}
	public String getOttApval() {
		return ottApval;
	}
	public void setOttApval(String ottApval) {
		this.ottApval = ottApval;
	}
	public String getDsLabel() {
		return dsLabel;
	}
	public void setDsLabel(String dsLabel) {
		this.dsLabel = dsLabel;
	}
	 	 	 
}
	 
	 
