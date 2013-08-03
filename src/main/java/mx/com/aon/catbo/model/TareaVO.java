package mx.com.aon.catbo.model;

/**
 * 
 * Clase VO usada para obtener una Tarea.
 * 
 * @param cdProceso
 * @param dsProceso
 * @param estatus
 * @param cdPriord
 * @param dsPriord
 * @param indSemaforo
 * @param cdModulo
 * @param dsModulo
 
 * 
 */


public class TareaVO  {
	

	private String cdProceso;
	private String dsProceso;
	private String estatus;
	private String dsEstatus;
	private String cdPriord;
	private String dsPriord;
	private String indSemaforo;
	private String cdModulo;
	private String dsModulo;
	private String frmAb;
	private String cdStatus;


	

	public String getCdProceso() {
		return cdProceso;
	}

	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getCdPriord() {
		return cdPriord;
	}

	public void setCdPriord(String cdPriord) {
		this.cdPriord = cdPriord;
	}

	public String getIndSemaforo() {
		return indSemaforo;
	}

	public void setIndSemaforo(String indSemaforo) {
		this.indSemaforo = indSemaforo;
	}

	public String getCdModulo() {
		return cdModulo;
	}

	public void setCdModulo(String cdModulo) {
		this.cdModulo = cdModulo;
	}

	public String getDsProceso() {
		return dsProceso;
	}

	public void setDsProceso(String dsProceso) {
		this.dsProceso = dsProceso;
	}

	public String getDsPriord() {
		return dsPriord;
	}

	public void setDsPriord(String dsPriord) {
		this.dsPriord = dsPriord;
	}

	public String getDsModulo() {
		return dsModulo;
	}

	public void setDsModulo(String dsModulo) {
		this.dsModulo = dsModulo;
	}

	public String getDsEstatus() {
		return dsEstatus;
	}

	public void setDsEstatus(String dsEstatus) {
		this.dsEstatus = dsEstatus;
	}

	public String getFrmAb() {
		return frmAb;
	}

	public void setFrmAb(String frmAb) {
		this.frmAb = frmAb;
	}

	public String getCdStatus() {
		return cdStatus;
	}

	public void setCdStatus(String cdStatus) {
		this.cdStatus = cdStatus;
	} 
	

}
