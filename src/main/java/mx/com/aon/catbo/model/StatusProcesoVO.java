package mx.com.aon.catbo.model;

/**
 * 
 * Clase VO usada para obtener un Status Procesos.
 * 
 * @param cdProceso
 * @param dsProceso 
 * 
 */


public class StatusProcesoVO  {
	

	private String cdStatus;
	private String cdProceso;

	public String getCdProceso() {
		return cdProceso;
	}

	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}

	public String getCdStatus() {
		return cdStatus;
	}

	public void setCdStatus(String cdStatus) {
		this.cdStatus = cdStatus;
	}





}
