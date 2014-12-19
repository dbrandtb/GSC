package mx.com.gseguros.utils;

import java.util.List;

public class FormatoArchivoCSV extends FormatoArchivoVO {
	
	private String separador;
	
	private String nombreCompleto;
	
	public FormatoArchivoCSV() {
		super();
	}
	
	public FormatoArchivoCSV(List<CampoVO> campos) {
		super(campos);
	}

	public String getSeparador() {
		return separador;
	}

	public void setSeparador(String separador) {
		this.separador = separador;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

}