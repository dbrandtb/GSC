package mx.com.aon.catbo.model;


/**
 * 
 * Clase VO usada para obtener una Notificaciones.
 * 
 * @param cdNotificacion
 * @param dsNotificacion 
 * @param dsMensaje 
 * @param cdFormatoOrden
 * @param dsFormatoOrden 
 * @param cdMetEnv
 * @param dsMetEnv
 * @param cdProceso
 * @param dsProceso
 */
public class NotificacionVO {

    private String cdNotificacion;
    private String dsNotificacion;
    private String dsMensaje;
    private String cdFormatoOrden;
    private String dsFormatoOrden;
    private String cdMetEnv;
	private String dsMetEnv;
	private String cdProceso;
	private String dsProceso;
	private String cdRegion;
	private String dsRegion;
	private String cdEstado;
	private String dsEstado;
    
    public String getCdNotificacion() {
		return cdNotificacion;
	}
	public void setCdNotificacion(String cdNotificacion) {
		this.cdNotificacion = cdNotificacion;
	}
	public String getDsNotificacion() {
		return dsNotificacion;
	}
	public void setDsNotificacion(String dsNotificacion) {
		this.dsNotificacion = dsNotificacion;
	}
	public String getDsMensaje() {
		return dsMensaje;
	}
	public void setDsMensaje(String dsMensaje) {
		this.dsMensaje = dsMensaje;
	}

	public String getCdFormatoOrden() {
		return cdFormatoOrden;
	}
	public void setCdFormatoOrden(String cdFormatoOrden) {
		this.cdFormatoOrden = cdFormatoOrden;
	}
	
	public String getCdMetEnv() {
		return cdMetEnv;
	}
	public void setCdMetEnv(String cdMetEnv) {
		this.cdMetEnv = cdMetEnv;
	}
	public String getCdProceso() {
		return cdProceso;
	}
	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}

	public String getDsFormatoOrden() {
		return dsFormatoOrden;
	}

	public void setDsFormatoOrden(String dsFormatoOrden) {
		this.dsFormatoOrden = dsFormatoOrden;
	}
	public String getDsMetEnv() {
		return dsMetEnv;
	}
	public void setDsMetEnv(String dsMetEnv) {
		this.dsMetEnv = dsMetEnv;
	}
	public String getDsProceso() {
		return dsProceso;
	}
	public void setDsProceso(String dsProceso) {
		this.dsProceso = dsProceso;
	}
	public String getDsRegion() {
		return dsRegion;
	}
	public void setDsRegion(String dsRegion) {
		this.dsRegion = dsRegion;
	}
	public String getDsEstado() {
		return dsEstado;
	}
	public void setDsEstado(String dsEstado) {
		this.dsEstado = dsEstado;
	}
	public String getCdRegion() {
		return cdRegion;
	}
	public void setCdRegion(String cdRegion) {
		this.cdRegion = cdRegion;
	}
	public String getCdEstado() {
		return cdEstado;
	}
	public void setCdEstado(String cdEstado) {
		this.cdEstado = cdEstado;
	}
  
}



