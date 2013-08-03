package mx.com.aon.catbo.model;

import java.util.List;

public class EncuestaPreguntaVO {
	
	 private String cdEncuesta;
	 private String dsEncuesta;
	 private String feRegistro;	 
	 private String swEstado;
	 private String ottapval;
	 
	 private List<PreguntaEncuestaVO> preguntaEncuestaVOs;
	 
	public String getCdEncuesta() {
		return cdEncuesta;
	}
	public void setCdEncuesta(String cdEncuesta) {
		this.cdEncuesta = cdEncuesta;
	}
	public String getDsEncuesta() {
		return dsEncuesta;
	}
	public void setDsEncuesta(String dsEncuesta) {
		this.dsEncuesta = dsEncuesta;
	}
	public String getFeRegistro() {
		return feRegistro;
	}
	public void setFeRegistro(String feRegistro) {
		this.feRegistro = feRegistro;
	}
	public String getSwEstado() {
		return swEstado;
	}
	public void setSwEstado(String swEstado) {
		this.swEstado = swEstado;
	}
	public List<PreguntaEncuestaVO> getPreguntaEncuestaVOs() {
		return preguntaEncuestaVOs;
	}
	public void setPreguntaEncuestaVOs(List<PreguntaEncuestaVO> preguntaEncuestaVOs) {
		this.preguntaEncuestaVOs = preguntaEncuestaVOs;
	}
	public String getOttapval() {
		return ottapval;
	}
	public void setOttapval(String ottapval) {
		this.ottapval = ottapval;
	}
	 



}
	 
	 
