package mx.com.aon.portal.model;

import java.util.List;

public class DescuentoVolumenVO {
	
	private String cdDscto;
	private String dsNombre;
	private String cdTipo;
	private String cdElemento;
	private String cdPerson;
	private String fgAcumul;
	private String cdEstado;
	
	private List<DescuentoDetVolumenVO> detallesVolumen;

	public String getCdDscto() {
		return cdDscto;
	}

	public void setCdDscto(String cdDscto) {
		this.cdDscto = cdDscto;
	}
	
	public List<DescuentoDetVolumenVO> getDetallesVolumen() {
		return detallesVolumen;
	}

	public void setDetallesVolumen(List<DescuentoDetVolumenVO> detallesVolumen) {
		this.detallesVolumen = detallesVolumen;
	}

	public String getDsNombre() {
		return dsNombre;
	}

	public void setDsNombre(String dsNombre) {
		this.dsNombre = dsNombre;
	}

	public String getCdTipo() {
		return cdTipo;
	}

	public void setCdTipo(String cdTipo) {
		this.cdTipo = cdTipo;
	}

	public String getCdPerson() {
		return cdPerson;
	}

	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

	public String getFgAcumul() {
		return fgAcumul;
	}

	public void setFgAcumul(String fgAcumul) {
		this.fgAcumul = fgAcumul;
	}

	public String getCdEstado() {
		return cdEstado;
	}

	public void setCdEstado(String cdEstado) {
		this.cdEstado = cdEstado;
	}

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}
}
