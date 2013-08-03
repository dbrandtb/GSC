package mx.com.aon.catbo.model;

public class TareasAltaCasoVO {

	
	private String descripcion;
	private String cdmatriz;
	private String cdformatoorden;
	private String cdunieco;
	private String cdramo;
	private String cdproceso;
	private String dsramo;
	private String dsunieco;
	//Agregado para resolver incidencia de combo en pantalla de alta de caso
	private String cdformatoMatriz;
	
	
	public String getCdformatoMatriz() {
		return cdformatoMatriz;
	}
	public void setCdformatoMatriz(String cdformatoMatriz) {
		this.cdformatoMatriz = cdformatoMatriz;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCdmatriz() {
		return cdmatriz;
	}
	public void setCdmatriz(String cdmatriz) {
		this.cdmatriz = cdmatriz;
	}
	public String getCdformatoorden() {
		return cdformatoorden;
	}
	public void setCdformatoorden(String cdformatoorden) {
		this.cdformatoorden = cdformatoorden;
		this.cdformatoMatriz = this.getCdformatoorden().concat("_").concat(this.getCdmatriz());
	}
	public String getCdunieco() {
		return cdunieco;
	}
	public void setCdunieco(String cdunieco) {
		this.cdunieco = cdunieco;
	}
	public String getCdramo() {
		return cdramo;
	}
	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}
	public String getCdproceso() {
		return cdproceso;
	}
	public void setCdproceso(String cdproceso) {
		this.cdproceso = cdproceso;
	}
	public String getDsunieco() {
		return dsunieco;
	}
	public void setDsunieco(String dsunieco) {
		this.dsunieco = dsunieco;
	}
	public String getDsramo() {
		return dsramo;
	}
	public void setDsramo(String dsramo) {
		this.dsramo = dsramo;
	}
}
