/**
 * 
 */
package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener una configuracion de seccion orden. 
 * 
 * @param cdFormatoOrden 
 * @param dsFormatoOrden 
 * @param cdSeccion 
 * @param dsSeccion
 * @param cdTipSit
 * @param dsTipSit
 * @param cdTipObj
 * @param dsTipObj
 * @param nmOrden
 */
public class ConfigurarSeccionOrdenVO {

	private String cdFormatoOrden;
	private String dsFormatoOrden;
	private String cdSeccion;
	private String dsSeccion;    
    private String cdTipSit;
    private String dsTipSit;
    private String cdTipObj;
    private String dsTipObj;
    private String nmOrden;

    public String getDsFormatoOrden() {
		return dsFormatoOrden;
	}
	public void setDsFormatoOrden(String dsFormatoOrden) {
		this.dsFormatoOrden = dsFormatoOrden;
	}
	public String getNmOrden() {
		return nmOrden;
	}
	public void setNmOrden(String nmOrden) {
		this.nmOrden = nmOrden;
	}
	public String getCdTipSit() {
		return cdTipSit;
	}
	public void setCdTipSit(String cdTipSit) {
		this.cdTipSit = cdTipSit;
	}
	public String getDsTipSit() {
		return dsTipSit;
	}
	public void setDsTipSit(String dsTipSit) {
		this.dsTipSit = dsTipSit;
	}
	public String getCdTipObj() {
		return cdTipObj;
	}
	public void setCdTipObj(String cdTipObj) {
		this.cdTipObj = cdTipObj;
	}
	public String getDsTipObj() {
		return dsTipObj;
	}
	public void setDsTipObj(String dsTipObj) {
		this.dsTipObj = dsTipObj;
	}
	public String getCdFormatoOrden() {
		return cdFormatoOrden;
	}
	public void setCdFormatoOrden(String cdFormatoOrden) {
		this.cdFormatoOrden = cdFormatoOrden;
	}
	public String getCdSeccion() {
		return cdSeccion;
	}
	public void setCdSeccion(String cdSeccion) {
		this.cdSeccion = cdSeccion;
	}
	public String getDsSeccion() {
		return dsSeccion;
	}
	public void setDsSeccion(String dsSeccion) {
		this.dsSeccion = dsSeccion;
	}

}