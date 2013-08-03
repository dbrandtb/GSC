/**
 * 
 */
package mx.com.aon.procesos.emision.model;

import org.apache.commons.lang.StringUtils;

/**
 * @author Cesar Hernandez
 *
 */
public class PolizaDetVO {
	private String vigenciaDesde;
	private String vigenciaHasta;
	private String tipoPoliza;
	private String moneda;
	private String fechaEfectividad;
	private String fechaRenovacion;
	private String tipoCoaseguro;
	private String periocidad;
	private String numeroEndoso;
	
	/**
	 * @return the vigenciaDesde
	 */
	public String getVigenciaDesde() {
		return vigenciaDesde;
	}
	
	/**
	 * @param vigenciaDesde the vigenciaDesde to set
	 */
	public void setVigenciaDesde(String vigenciaDesde) {
		if (StringUtils.isNotBlank(vigenciaDesde)) {
			this.vigenciaDesde = aplicaFormatoFecha(vigenciaDesde);
		} else {
			this.vigenciaDesde = vigenciaDesde;
		}
	}
	
	/**
	 * @return the vigenciaHasta
	 */
	public String getVigenciaHasta() {
		return vigenciaHasta;
	}
	
	/**
	 * @param vigenciaHasta the vigenciaHasta to set
	 */
	public void setVigenciaHasta(String vigenciaHasta) {
		this.vigenciaHasta = vigenciaHasta;
	}
	/**
	 * @return the tipoPoliza
	 */
	public String getTipoPoliza() {
		return tipoPoliza;
	}
	
	/**
	 * @param tipoPoliza the tipoPoliza to set
	 */
	public void setTipoPoliza(String tipoPoliza) {
		this.tipoPoliza = tipoPoliza;
	}
	
	/**
	 * @return the moneda
	 */
	public String getMoneda() {
		return moneda;
	}
	
	/**
	 * @param moneda the moneda to set
	 */
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	
	/**
	 * @return the fechaEfectividad
	 */
	public String getFechaEfectividad() {
		return fechaEfectividad;
	}
	
	/**
	 * @param fechaEfectividad the fechaEfectividad to set
	 */
	public void setFechaEfectividad(String fechaEfectividad) {
		if (StringUtils.isNotBlank(fechaEfectividad)) {
			this.fechaEfectividad = aplicaFormatoFecha(fechaEfectividad);
		} else {
			this.fechaEfectividad = fechaEfectividad;
		}
	}
	
	/**
	 * @return the fechaRenovacion
	 */
	public String getFechaRenovacion() {
		return fechaRenovacion;
	}
	
	/**
	 * @param fechaRenovacion the fechaRenovacion to set
	 */
	public void setFechaRenovacion(String fechaRenovacion) {
		this.fechaRenovacion = fechaRenovacion;
	}
	
	/**
	 * @return the tipoCoaseguro
	 */
	public String getTipoCoaseguro() {
		return tipoCoaseguro;
	}
	
	/**
	 * @param tipoCoaseguro the tipoCoaseguro to set
	 */
	public void setTipoCoaseguro(String tipoCoaseguro) {
		this.tipoCoaseguro = tipoCoaseguro;
	}
	
	/**
	 * @return the periocidad
	 */
	public String getPeriocidad() {
		return periocidad;
	}
	
	/**
	 * @param periocidad the periocidad to set
	 */
	public void setPeriocidad(String periocidad) {
		this.periocidad = periocidad;
	}
	
	/**
	 * @return the numeroEndoso
	 */
	public String getNumeroEndoso() {
		return numeroEndoso;
	}
	
	/**
	 * @param numeroEndoso the numeroEndoso to set
	 */
	public void setNumeroEndoso(String numeroEndoso) {
		this.numeroEndoso = numeroEndoso;
	}
	
	/**
	 * método encargado de dar formato fecha
	 * @param fechaEfectividad2
	 * @return
	 */
	private String aplicaFormatoFecha(String fechaEfectividad2) {
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(fechaEfectividad2.substring(8, 10))
			.append("/")
			.append(fechaEfectividad2.substring(5, 7))
			.append("/")
			.append(fechaEfectividad2.substring(0, 4));
		return sbuilder.toString();
	}
}
