package mx.com.aon.portal.model;


/**
 * Clase VO usada para obtener el requisitos de rehabilitacion
 * 
 * @param cdCarroD              codigo carrito
 * @param cdUniEco              codigo aseguradora  
 * @param dsUniEco              nombre aseguradora
 * @param cdRamo                codigo producto
 * @param dsRamo                nombre producto    
 * @param cdPlan                codigo plan
 * @param dsPlan                descripcion plan
 * @param feInicio              fecha inicio
 * @param feEstado              fecha estado    
 * @param mnTotalp              monto toral a pagar
 * @param dsRazSoc              descripcion razon social
 * @param feIniSus              fecha Inicio suspencion   
 * @param feFinSus              fecha fin suspencion    
 */
public class CarritoComprasProductosVO {

	private String cdCarroD;
    private String cdUniEco;
    private String dsUniEco;
    private String cdRamo;
    private String dsRamo;
    private String cdPlan;
    private String dsPlan;
    private String feInicio;
    private String feEstado;
    private String mnTotalp;
    private String dsRazSoc;
    private String feIniSus;
    private String feFinSus;
    
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
	public String getCdRamo() {
		return cdRamo;
	}
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}
	public String getDsRamo() {
		return dsRamo;
	}
	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}
	public String getCdPlan() {
		return cdPlan;
	}
	public void setCdPlan(String cdPlan) {
		this.cdPlan = cdPlan;
	}
	public String getDsPlan() {
		return dsPlan;
	}
	public void setDsPlan(String dsPlan) {
		this.dsPlan = dsPlan;
	}
	public String getFeInicio() {
		return feInicio;
	}
	public void setFeInicio(String feInicio) {
		this.feInicio = feInicio;
	}
	public String getFeEstado() {
		return feEstado;
	}
	public void setFeEstado(String feEstado) {
		this.feEstado = feEstado;
	}
	public String getMnTotalp() {
		return mnTotalp;
	}
	public void setMnTotalp(String mnTotalp) {
		this.mnTotalp = mnTotalp;
	}
	public String getDsRazSoc() {
		return dsRazSoc;
	}
	public void setDsRazSoc(String dsRazSoc) {
		this.dsRazSoc = dsRazSoc;
	}
	public String getFeIniSus() {
		return feIniSus;
	}
	public void setFeIniSus(String feIniSus) {
		this.feIniSus = feIniSus;
	}
	public String getFeFinSus() {
		return feFinSus;
	}
	public void setFeFinSus(String feFinSus) {
		this.feFinSus = feFinSus;
	}
	public String getCdCarroD() {
		return cdCarroD;
	}
	public void setCdCarroD(String cdCarroD) {
		this.cdCarroD = cdCarroD;
	}
	
	
   
	
    
 
}
