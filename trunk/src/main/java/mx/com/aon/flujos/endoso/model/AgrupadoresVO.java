/**
 * 
 */
package mx.com.aon.flujos.endoso.model;

/**
 * @author Cesar Hernandez
 *
 */
public class AgrupadoresVO {
	private String nombre;
	private String domicilio;
	private String banco;
	private String instrumento;
	private String fechaVencimiento;
	private String tipoTarjeta;
	private String numCuenta;
	private String sucursal;
	
	private String cdPerson;
	private String cdDomicilio;
	private String cdInstrumentoPago;
	private String cdBanco;
	private String cdSucursal;
	
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return the domicilio
	 */
	public String getDomicilio() {
		return domicilio;
	}
	
	/**
	 * @param domicilio the domicilio to set
	 */
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	
	/**
	 * @return the banco
	 */
	public String getBanco() {
		return banco;
	}
	/**
	 * @param banco the banco to set
	 */
	public void setBanco(String banco) {
		this.banco = banco;
	}
	
	/**
	 * @return the instrumento
	 */
	public String getInstrumento() {
		return instrumento;
	}
	
	/**
	 * @param instrumento the instrumento to set
	 */
	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}

	/**
	 * @return the fechaVencimiento
	 */
	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	/**
	 * @param fechaVencimiento the fechaVencimiento to set
	 */
	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	/**
	 * @return the tipoTarjeta
	 */
	public String getTipoTarjeta() {
		return tipoTarjeta;
	}

	/**
	 * @param tipoTarjeta the tipoTarjeta to set
	 */
	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	/**
	 * @return the numCuenta
	 */
	public String getNumCuenta() {
		return numCuenta;
	}

	/**
	 * @param numCuenta the numCuenta to set
	 */
	public void setNumCuenta(String numCuenta) {
		this.numCuenta = numCuenta;
	}

	/**
	 * @return the sucursal
	 */
	public String getSucursal() {
		return sucursal;
	}

	/**
	 * @param sucursal the sucursal to set
	 */
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getCdPerson() {
		return cdPerson;
	}

	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

	public String getCdDomicilio() {
		return cdDomicilio;
	}

	public void setCdDomicilio(String cdDomicilio) {
		this.cdDomicilio = cdDomicilio;
	}
	
	public String getCdInstrumentoPago() {
		return cdInstrumentoPago;
	}

	public void setCdInstrumentoPago(String cdInstrumentoPago) {
		this.cdInstrumentoPago = cdInstrumentoPago;
	}

	public String getCdBanco() {
		return cdBanco;
	}

	public void setCdBanco(String cdBanco) {
		this.cdBanco = cdBanco;
	}

	public String getCdSucursal() {
		return cdSucursal;
	}

	public void setCdSucursal(String cdSucursal) {
		this.cdSucursal = cdSucursal;
	}
	
	
}
