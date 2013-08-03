package mx.com.aon.portal.model;

import java.util.List;

public class DescuentoProductoEncDetVO {

	private String cdDscto;
	private String cdDsctod;
	private String dsDscto;
	private String cdTipo;
	private String cdElemento;
	private String cdPerson;
	private String prDescto;
	private String mnDescto;
	private String fgAcumul;
	private String cdEstado;
	
	private List<DetalleProductoVO> detalleProducto;

	
	public String getCdDscto() {
		return cdDscto;
	}

	public void setCdDscto(String cdDscto) {
		this.cdDscto = cdDscto;
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

	public String getPrDescto() {
		return prDescto;
	}

	public void setPrDescto(String prDescto) {
		this.prDescto = prDescto;
	}

	public String getMnDescto() {
		return mnDescto;
	}

	public void setMnDescto(String mnDescto) {
		this.mnDescto = mnDescto;
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

	public List<DetalleProductoVO> getDetalleProducto() {
		return detalleProducto;
	}

	public void setDetalleProducto(List<DetalleProductoVO> detalleProducto) {
		this.detalleProducto = detalleProducto;
	}

	public String getDsDscto() {
		return dsDscto;
	}

	public void setDsDscto(String dsDscto) {
		this.dsDscto = dsDscto;
	}

	public String getCdDsctod() {
		return cdDsctod;
	}

	public void setCdDsctod(String cdDsctod) {
		this.cdDsctod = cdDsctod;
	}

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}
	
}
