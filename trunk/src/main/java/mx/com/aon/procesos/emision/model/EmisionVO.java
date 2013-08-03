/**
 * 
 */
package mx.com.aon.procesos.emision.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Cesar Hernandez
 *
 */
public class EmisionVO {
	private String aseguradora;
	private String producto;
	private String poliza;
	private String estado;
	private String periocidad;
	private String nombrePersona;
	private String rfc;
	private String vigencia;
	private String instPago;
	private String primaTotal;
	private String cdUnieco;
	private String cdRamo;
	private String nmPoliza;
	private String inciso;
	private String feEfecto;
	private String feVencim;
	private String cdUniAge;
	private String status;
	private String swEstado;
	private String swCancel;
	
    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSwEstado() {
		return swEstado;
	}
	public void setSwEstado(String swEstado) {
		this.swEstado = swEstado;
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
	 * @return the nombrePersona
	 */
	public String getNombrePersona() {
		return nombrePersona;
	}
	/**
	 * @param nombrePersona the nombrePersona to set
	 */
	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}
	/**
	 * @return the rfc
	 */
	public String getRfc() {
		return rfc;
	}
	/**
	 * @param rfc the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	/**
	 * @return the vigencia
	 */
	public String getVigencia() {
		return vigencia;
	}
	/**
	 * @param vigencia the vigencia to set
	 */
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	/**
	 * @return the instPago
	 */
	public String getInstPago() {
		return instPago;
	}
	/**
	 * @param instPago the instPago to set
	 */
	public void setInstPago(String instPago) {
		this.instPago = instPago;
	}
	/**
	 * @return the primaTotal
	 */
	public String getPrimaTotal() {
		return primaTotal;
	}
	/**
	 * @param primaTotal the primaTotal to set
	 */
	public void setPrimaTotal(String primaTotal) {
		this.primaTotal = primaTotal;
	}
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
	public String getFeEfecto() {
		return feEfecto;
	}
	public void setFeEfecto(String feEfecto) {
		this.feEfecto = feEfecto;
	}
	public String getFeVencim() {
		return feVencim;
	}
	public void setFeVencim(String feVencim) {
		this.feVencim = feVencim;
	}
	public String getCdUniAge() {
		return cdUniAge;
	}
	public void setCdUniAge(String cdUniAge) {
		this.cdUniAge = cdUniAge;
	}

	public String getSwCancel() {
		return swCancel;
	}

	public void setSwCancel(String swCancel) {
		this.swCancel = swCancel;
	}

}
