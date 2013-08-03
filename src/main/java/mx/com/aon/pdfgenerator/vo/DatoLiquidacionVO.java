package mx.com.aon.pdfgenerator.vo;

public class DatoLiquidacionVO {

	private String folioFiscal;
	private String folio;
	private String Periodo;
	private String serieLiquidacion;
	private String fechaLimitePago;
	
	private String primaNeta;
	private String tasaFinanciamiento;
	private String gastosExpedicion;
	private String iva;
	private String totalPagar;
	private String primaTotalNum;
	
	
	
	public String getPrimaTotalNum() {
		return primaTotalNum != null ? primaTotalNum : "";
	}
	public void setPrimaTotalNum(String primaTotalNum) {
		this.primaTotalNum = primaTotalNum;
	}
	public String getFolio() {
		return folio != null ? folio : "";
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getPeriodo() {
		return Periodo != null ? Periodo : "";
	}
	public void setPeriodo(String periodo) {
		Periodo = periodo;
	}
	public String getSerieLiquidacion() {
		return serieLiquidacion != null ? serieLiquidacion : "";
	}
	public void setSerieLiquidacion(String serieLiquidacion) {
		this.serieLiquidacion = serieLiquidacion;
	}
	
	public String getPrimaNeta() {		
		return primaNeta != null ? primaNeta : "";
	}
	public void setPrimaNeta(String primaNeta) {
		this.primaNeta = primaNeta;
	}
	public String getTasaFinanciamiento() {
		return tasaFinanciamiento != null ? tasaFinanciamiento : "";
	}
	public void setTasaFinanciamiento(String tasaFinanciamiento) {
		this.tasaFinanciamiento = tasaFinanciamiento;
	}
	public String getGastosExpedicion() {
		return gastosExpedicion != null ? gastosExpedicion : "";
	}
	public void setGastosExpedicion(String gastosExpedicion) {
		this.gastosExpedicion = gastosExpedicion;
	}
	public String getIva() {
		return iva != null ? iva : "";
	}
	public void setIva(String iva) {
		this.iva = iva;
	}
	public String getTotalPagar() {
		return totalPagar != null ? totalPagar : "";
	}
	public void setTotalPagar(String totalPagar) {
		this.totalPagar = totalPagar;
	}
	public String getFolioFiscal() {
		return folioFiscal != null ? folioFiscal : "";
	}
	public void setFolioFiscal(String folioFiscal) {
		this.folioFiscal = folioFiscal;
	}
	public String getFechaLimitePago() {
		return fechaLimitePago != null ? fechaLimitePago : "";
	}
	public void setFechaLimitePago(String fechaLimitePago) {
		this.fechaLimitePago = fechaLimitePago;
	}
	
	
}
