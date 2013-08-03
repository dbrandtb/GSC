package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener el perido de gracia
 * 
 * @param cdTramo                   codigo del tramo
 * @param dsTramo                   descripcion del tramo  
 * @param nmMinimo                  cota inferior del recibo
 * @param nmMaximo                  cota superior del recibo
 * @param diasGrac                  dias de gracia otorgados    
 *
 */
public class PeriodosGraciaVO {
	
	private String cdTramo;
	private String dsTramo;
	private String nmMinimo;
    private String nmMaximo;
    private String diasGrac;
    private String diasCanc;
    
	public String getCdTramo() {
		return cdTramo;
	}
	public void setCdTramo(String cdTramo) {
		this.cdTramo = cdTramo;
	}
	public String getDsTramo() {
		return dsTramo;
	}
	public void setDsTramo(String dsTramo) {
		this.dsTramo = dsTramo;
	}
	public String getNmMinimo() {
		return nmMinimo;
	}
	public void setNmMinimo(String nmMinimo) {
		this.nmMinimo = nmMinimo;
	}
	public String getNmMaximo() {
		return nmMaximo;
	}
	public void setNmMaximo(String nmMaximo) {
		this.nmMaximo = nmMaximo;
	}
	public String getDiasGrac() {
		return diasGrac;
	}
	public void setDiasGrac(String diasGrac) {
		this.diasGrac = diasGrac;
	}
	public String getDiasCanc() {
		return diasCanc;
	}
	public void setDiasCanc(String diasCanc) {
		this.diasCanc = diasCanc;
	} 
}
