/**
 * 
 */
package mx.com.aon.portal.model;

/**
 * Estructura VO usada para obtener un modelo de Configuracion de alertas automaticos.
 	* @param cdIdUnico
	* @param dsAlerta
	* @param dsUsuario
	* @param cdRol
	* @param cdNivCorp
	* @param cdCliente
	* @param cdTipRam
	* @param cdUniEco
	* @param dsRegion
	* @param cdProducto
	* @param cdProceso
	* @param cdTemporalidad
	* @param dsMensaje
	* @param fgMandaEmail
	* @param fgMandaPantalla
	* @param fgPermPantalla
	* @param nmDiasAnt
	* @param nmDuracion
	* @param nmFrecuencia
	* @param feInicio
	* @param dsTablaAlerta
	* @param dsColumnaAlerta
	
	* @param dsNombre
	* @param dsProceso
	* @param dsRamo
	* @param dsUniEco
	* @param dsProducto
	* @param dsRol
	* @param cdRegion
 */
public class ConfiguracionAlertasAutomaticoVO {

	private String cdIdUnico;
	private String dsAlerta;
	private String dsUsuario;
	private String cdRol;
	private String cdNivCorp;
	private String cdCliente;
	private String cdTipRam;
	private String cdUniEco;
	private String dsRegion;
	private String cdProducto;
	private String cdProceso;
	private String cdTemporalidad;
	private String dsTemporalidad;
	private String dsMensaje;
	private String fgMandaEmail;
	private String fgMandaPantalla;
	private String fgPermPantalla;
	private String nmDiasAnt;
	private String nmDuracion;
	private String nmFrecuencia;
	private String dsFrecuencia;
	private String feInicio;
	private String dsTablaAlerta;
	private String dsColumnaAlerta;
	
	private String dsNombre;
	private String dsProceso;
	private String dsRamo;
	private String dsUniEco;
	private String dsProducto;
	private String dsRol;
	private String cdRegion;
	private String cdUsuario;
	
	public String getCdIdUnico() {
		return cdIdUnico;
	}
	public void setCdIdUnico(String cdIdUnico) {
		this.cdIdUnico = cdIdUnico;
	}
	public String getDsAlerta() {
		return dsAlerta;
	}
	public void setDsAlerta(String dsAlerta) {
		this.dsAlerta = dsAlerta;
	}
	public String getDsUsuario() {
		return dsUsuario;
	}
	public void setDsUsuario(String dsUsuario) {
		this.dsUsuario = dsUsuario;
	}
	public String getCdRol() {
		return cdRol;
	}
	public void setCdRol(String cdRol) {
		this.cdRol = cdRol;
	}
	public String getCdNivCorp() {
		return cdNivCorp;
	}
	public void setCdNivCorp(String cdNivCorp) {
		this.cdNivCorp = cdNivCorp;
	}
	public String getCdCliente() {
		return cdCliente;
	}
	public void setCdCliente(String cdCliente) {
		this.cdCliente = cdCliente;
	}
	public String getCdTipRam() {
		return cdTipRam;
	}
	public void setCdTipRam(String cdTipRam) {
		this.cdTipRam = cdTipRam;
	}
	public String getCdUniEco() {
		return cdUniEco;
	}
	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}
	public String getDsRegion() {
		return dsRegion;
	}
	public void setDsRegion(String dsRegion) {
		this.dsRegion = dsRegion;
	}
	public String getCdProducto() {
		return cdProducto;
	}
	public void setCdProducto(String cdProducto) {
		this.cdProducto = cdProducto;
	}
	public String getCdProceso() {
		return cdProceso;
	}
	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}
	public String getCdTemporalidad() {
		return cdTemporalidad;
	}
	public void setCdTemporalidad(String cdTemporalidad) {
		this.cdTemporalidad = cdTemporalidad;
	}
	public String getDsMensaje() {
		return dsMensaje;
	}
	public void setDsMensaje(String dsMensaje) {
		this.dsMensaje = dsMensaje;
	}
	public String getFgMandaEmail() {
		return fgMandaEmail;
	}
	public void setFgMandaEmail(String fgMandaEmail) {
		this.fgMandaEmail = fgMandaEmail;
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
	public String getNmDiasAnt() {
		return nmDiasAnt;
	}
	public void setNmDiasAnt(String nmDiasAnt) {
		this.nmDiasAnt = nmDiasAnt;
	}
	public String getNmDuracion() {
		return nmDuracion;
	}
	public void setNmDuracion(String nmDuracion) {
		this.nmDuracion = nmDuracion;
	}
	public String getNmFrecuencia() {
		return nmFrecuencia;
	}
	public void setNmFrecuencia(String nmFrecuencia) {
		this.nmFrecuencia = nmFrecuencia;
	}
	public String getFeInicio() {
		return feInicio;
	}
	public void setFeInicio(String feInicio) {
		this.feInicio = feInicio;
	}
	public String getDsTablaAlerta() {
		return dsTablaAlerta;
	}
	public void setDsTablaAlerta(String dsTablaAlerta) {
		this.dsTablaAlerta = dsTablaAlerta;
	}
	public String getDsColumnaAlerta() {
		return dsColumnaAlerta;
	}
	public void setDsColumnaAlerta(String dsColumnaAlerta) {
		this.dsColumnaAlerta = dsColumnaAlerta;
	}
	public String getDsNombre() {
		return dsNombre;
	}
	public void setDsNombre(String dsNombre) {
		this.dsNombre = dsNombre;
	}
	public String getDsProceso() {
		return dsProceso;
	}
	public void setDsProceso(String dsProceso) {
		this.dsProceso = dsProceso;
	}
	public String getDsRamo() {
		return dsRamo;
	}
	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}
	public String getDsUniEco() {
		return dsUniEco;
	}
	public void setDsUniEco(String dsUniEco) {
		this.dsUniEco = dsUniEco;
	}
	public String getDsProducto() {
		return dsProducto;
	}
	public void setDsProducto(String dsProducto) {
		this.dsProducto = dsProducto;
	}
	public String getDsRol() {
		return dsRol;
	}
	public void setDsRol(String dsRol) {
		this.dsRol = dsRol;
	}
	public String getCdRegion() {
		return cdRegion;
	}
	public void setCdRegion(String cdRegion) {
		this.cdRegion = cdRegion;
	}
	public String getCdUsuario() {
		return cdUsuario;
	}
	public void setCdUsuario(String cdUsuario) {
		this.cdUsuario = cdUsuario;
	}
	public String getDsTemporalidad() {
		return dsTemporalidad;
	}
	public void setDsTemporalidad(String dsTemporalidad) {
		this.dsTemporalidad = dsTemporalidad;
	}
	public String getDsFrecuencia() {
		return dsFrecuencia;
	}
	public void setDsFrecuencia(String dsFrecuencia) {
		this.dsFrecuencia = dsFrecuencia;
	}
	
		
		
		// GETTERS AS SETTERS
		
		
}



