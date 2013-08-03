package mx.com.aon.portal.model;

/**
 * Clase VO que modela la estructura de datos para usarse por Alertas de usuarios.
 * 
 * @param cdIdUnico
 * @param dsNombre
	* @param cdProceso
	* @param dsProceso
	* @param cdUsuario
	* @param dsUsuario
	* @param cdUniEco
	* @param dsUniEco
	* @param nmPoliza
	* @param nmRecibo
	* @param feUltimoEvento
	* @param feSiguienteEnvio
	* @param nmFrecuencia
	* @param cdTemporalidad
	* @param feVencimiento
	* @param dsCorreo
	* @param dsMensaje
	* @param fgMandaPantalla
	* @param fgPermPantalla
	* @param dsAlerta
	* @param codigoConfAlerta
 *
 */
public class AlertaUsuarioVO {
	private String cdIdUnico;
	private String dsNombre;
	private String cdProceso;
	private String dsProceso;
	private String cdUsuario;
	private String dsUsuario;
	private String cdUniEco;
	private String dsUniEco;
	private String nmPoliza;
	private String nmRecibo;
	private String feUltimoEvento;
	private String feSiguienteEnvio;
	private String nmFrecuencia;
	private String cdTemporalidad;
	private String feVencimiento;
	private String dsCorreo;
	private String dsMensaje;
	private String fgMandaPantalla;
	private String fgPermPantalla;
	private String dsAlerta;
	private String codigoConfAlerta;
	private String cdIdConfAlerta;
	private String cdElemento;
	private String cdProducto;
	private String dsProducto;
	private String dsIsRol;
	private String dsRamo;
	
	
	private String dsTemporalidad;
	
	public String getDsTemporalidad() {
		return dsTemporalidad;
	}
	public void setDsTemporalidad(String dsTemporalidad) {
		this.dsTemporalidad = dsTemporalidad;
	}
	public String getCdProducto() {
		return cdProducto;
	}
	public void setCdProducto(String cdProducto) {
		this.cdProducto = cdProducto;
	}
	public String getDsProducto() {
		return dsProducto;
	}
	public void setDsProducto(String dsProducto) {
		this.dsProducto = dsProducto;
	}
	public String getNmFrecuencia() {
		return nmFrecuencia;
	}
	public void setNmFrecuencia(String nmFrecuencia) {
		this.nmFrecuencia = nmFrecuencia;
	}
	public String getCdTemporalidad() {
		return cdTemporalidad;
	}
	public void setCdTemporalidad(String cdTemporalidad) {
		this.cdTemporalidad = cdTemporalidad;
	}
	public String getFeVencimiento() {
		return feVencimiento;
	}
	public void setFeVencimiento(String feVencimiento) {
		this.feVencimiento = feVencimiento;
	}
	public String getDsCorreo() {
		return dsCorreo;
	}
	public void setDsCorreo(String dsCorreo) {
		this.dsCorreo = dsCorreo;
	}
	public String getDsMensaje() {
		return dsMensaje;
	}
	public void setDsMensaje(String dsMensaje) {
		this.dsMensaje = dsMensaje;
	}
	public String getFgMandaPantalla() {
		return fgMandaPantalla;
	}
	public void setFgMandaPantalla(String fgMandaPantalla) {
		this.fgMandaPantalla = fgMandaPantalla;
	}
	public String getFgPermPantalla() {
		return fgPermPantalla;
	}
	public void setFgPermPantalla(String fgPermPantalla) {
		this.fgPermPantalla = fgPermPantalla;
	}
	public String getCdIdUnico() {
		return cdIdUnico;
	}
	public void setCdIdUnico(String cdIdUnico) {
		this.cdIdUnico = cdIdUnico;
	}
	public String getDsNombre() {
		return dsNombre;
	}
	public void setDsNombre(String dsNombre) {
		this.dsNombre = dsNombre;
	}
	public String getCdProceso() {
		return cdProceso;
	}
	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}
	public String getDsProceso() {
		return dsProceso;
	}
	public void setDsProceso(String dsProceso) {
		this.dsProceso = dsProceso;
	}
	public String getDsUsuario() {
		return dsUsuario;
	}
	public void setDsUsuario(String dsUsuario) {
		this.dsUsuario = dsUsuario;
	}
	public String getCdUniEco() {
		return cdUniEco;
	}
	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}
	public String getDsUniEco() {
		return dsUniEco;
	}
	public void setDsUniEco(String dsUniEco) {
		this.dsUniEco = dsUniEco;
	}
	public String getNmPoliza() {
		return nmPoliza;
	}
	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}
	public String getNmRecibo() {
		return nmRecibo;
	}
	public void setNmRecibo(String nmRecibo) {
		this.nmRecibo = nmRecibo;
	}
	public String getFeUltimoEvento() {
		return feUltimoEvento;
	}
	public void setFeUltimoEvento(String feUltimoEvento) {
		this.feUltimoEvento = feUltimoEvento;
	}
	public String getFeSiguienteEnvio() {
		return feSiguienteEnvio;
	}
	public void setFeSiguienteEnvio(String feSiguienteEnvio) {
		this.feSiguienteEnvio = feSiguienteEnvio;
	}
	public String getDsAlerta() {
		return dsAlerta;
	}
	public void setDsAlerta(String dsAlerta) {
		this.dsAlerta = dsAlerta;
	}
	public String getCodigoConfAlerta() {
		return codigoConfAlerta;
	}
	public void setCodigoConfAlerta(String codigoConfAlerta) {
		this.codigoConfAlerta = codigoConfAlerta;
	}
	public String getCdUsuario() {
		return cdUsuario;
	}
	public void setCdUsuario(String cdUsuario) {
		this.cdUsuario = cdUsuario;
	}
	public String getCdIdConfAlerta() {
		return cdIdConfAlerta;
	}
	public void setCdIdConfAlerta(String cdIdConfAlerta) {
		this.cdIdConfAlerta = cdIdConfAlerta;
	}
	public String getCdElemento() {
		return cdElemento;
	}
	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}
	public String getDsIsRol() {
		return dsIsRol;
	}
	public void setDsIsRol(String dsIsRol) {
		this.dsIsRol = dsIsRol;
	}
	public String getDsRamo() {
		return dsRamo;
	}
	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}
	
}
