/**
 * 
 */
package mx.com.aon.flujos.renovacion.model;

/**
 * @author Alejandro García
 */
public class RenovacionVO {
	
	private String asegurado;
	private String aseguradora;
	private String producto;
	private String poliza;
	private String inciso;
	private String cdCia;
	private String cdRamo;
	private String cdCliente;
	private String cliente;
	private String fechaRenova;
	private String estado;
	private String nmPoliza;
	private String nmSituac;
	//Poliza Renovada
	private String polizaAnterior;
	private String polizaRenovacion;
	private String prima;
	private boolean aRenovar;
	private boolean seleccionar;
	private String nmanno;
	private String nmmes;
	private String cdUnieco;
	private String swAprobada;
	private String renovar;
	private String nmPolizaAnterior;
	
	
	/**
	 * @return the cdUnieco
	 */
	public String getCdUnieco() {
		return cdUnieco;
	}
	/**
	 * @param cdUnieco the cdUnieco to set
	 */
	public void setCdUnieco(String cdUnieco) {
		this.cdUnieco = cdUnieco;
	}
	/**
	 * @return the asegurado
	 */
	public String getAsegurado() {
		return asegurado;
	}
	/**
	 * @param asegurado the asegurado to set
	 */
	public void setAsegurado(String asegurado) {
		this.asegurado = asegurado;
	}
	/**
	 * @return the aseguradora
	 */
	public String getAseguradora() {
		return aseguradora;
	}
	/**
	 * @param aseguradora the aseguradora to set
	 */
	public void setAseguradora(String aseguradora) {
		this.aseguradora = aseguradora;
	}
	/**
	 * @return the producto
	 */
	public String getProducto() {
		return producto;
	}
	/**
	 * @param producto the producto to set
	 */
	public void setProducto(String producto) {
		this.producto = producto;
	}
	/**
	 * @return the poliza
	 */
	public String getPoliza() {
		return poliza;
	}
	/**
	 * @param poliza the poliza to set
	 */
	public void setPoliza(String poliza) {
		this.poliza = poliza;
	}
	/**
	 * @return the inciso
	 */
	public String getInciso() {
		return inciso;
	}
	/**
	 * @param inciso the inciso to set
	 */
	public void setInciso(String inciso) {
		this.inciso = inciso;
	}
	/**
	 * @return the cdCia
	 */
	public String getCdCia() {
		return cdCia;
	}
	/**
	 * @param cdCia the cdCia to set
	 */
	public void setCdCia(String cdCia) {
		this.cdCia = cdCia;
	}
	/**
	 * @return the cdRamo
	 */
	public String getCdRamo() {
		return cdRamo;
	}
	/**
	 * @param cdRamo the cdRamo to set
	 */
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}
	/**
	 * @return the cdCliente
	 */
	public String getCdCliente() {
		return cdCliente;
	}
	/**
	 * @param cdCliente the cdCliente to set
	 */
	public void setCdCliente(String cdCliente) {
		this.cdCliente = cdCliente;
	}
	/**
	 * @return the cliente
	 */
	public String getCliente() {
		return cliente;
	}
	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	/**
	 * @return the fechaRenova
	 */
	public String getFechaRenova() {
		return fechaRenova;
	}
	/**
	 * @param fechaRenova the fechaRenova to set
	 */
	public void setFechaRenova (String fechaRenova) {
		this.fechaRenova = fechaRenova;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the nmPoliza
	 */
	public String getNmPoliza() {
		return nmPoliza;
	}
	/**
	 * @param nmPoliza the nmPoliza to set
	 */
	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}
	/**
	 * @return the nmSituac
	 */
	public String getNmSituac() {
		return nmSituac;
	}
	/**
	 * @param nmSituac the nmSituac to set
	 */
	public void setNmSituac(String nmSituac) {
		this.nmSituac = nmSituac;
	}
	/**
	 * @return the polizaAnterior
	 */
	public String getPolizaAnterior() {
		return polizaAnterior;
	}
	/**
	 * @param polizaAnterior the polizaAnterior to set
	 */
	public void setPolizaAnterior(String polizaAnterior) {
		this.polizaAnterior = polizaAnterior;
	}
	/**
	 * @return the polizaRenovacion
	 */
	public String getPolizaRenovacion() {
		return polizaRenovacion;
	}
	/**
	 * @param polizaRenovacion the polizaRenovacion to set
	 */
	public void setPolizaRenovacion(String polizaRenovacion) {
		this.polizaRenovacion = polizaRenovacion;
	}
	/**
	 * @return the prima
	 */
	public String getPrima() {
		return prima;
	}
	/**
	 * @param prima the prima to set
	 */
	public void setPrima(String prima) {
		this.prima = prima;
	}
	/**
	 * @return the aRenovar
	 */
	public boolean getARenovar() {
		return aRenovar;
	}
	/**
	 * @param renovar the aRenovar to set
	 */
	public void setARenovar(boolean renovar) {
		aRenovar = renovar;
	}
	/**
	 * @return the seleccionar
	 */
	public boolean getSeleccionar() {
		return seleccionar;
	}
	/**
	 * @param seleccionar the seleccionar to set
	 */
	public void setSeleccionar(boolean seleccionar) {
		this.seleccionar = seleccionar;
	}
	/**
	 * @return the nmanno
	 */
	public String getNmanno() {
		return nmanno;
	}
	/**
	 * @param nmanno the nmanno to set
	 */
	public void setNmanno(String nmanno) {
		this.nmanno = nmanno;
	}
	/**
	 * @return the nmmes
	 */
	public String getNmmes() {
		return nmmes;
	}
	/**
	 * @param nmmes the nmmes to set
	 */
	public void setNmmes(String nmmes) {
		this.nmmes = nmmes;
	}
	/**
	 * @return the swAprobada
	 */
	public String getSwAprobada() {
		return swAprobada;
	}
	/**
	 * @param swAprobada the swAprobada to set
	 */
	public void setSwAprobada(String swAprobada) {
		this.swAprobada = swAprobada;
	}
	public String getRenovar() {
		return renovar;
	}
	public void setRenovar(String renovar) {
		this.renovar = renovar;
	}
	/**
	 * @return the nmPolizaAnterior
	 */
	public String getNmPolizaAnterior() {
		return nmPolizaAnterior;
	}
	/**
	 * @param nmPolizaAnterior the nmPolizaAnterior to set
	 */
	public void setNmPolizaAnterior(String nmPolizaAnterior) {
		this.nmPolizaAnterior = nmPolizaAnterior;
	}

}
