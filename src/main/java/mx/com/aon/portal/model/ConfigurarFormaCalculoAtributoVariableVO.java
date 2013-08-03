package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener atributos de forma de calculo variable
 * 
 * @param cdIden                codigo identificacion
 * @param cdElemento            codigo cliente  
 * @param dsElemen              nombre cliente
 * @param cdTipSit              codigo tipo situacion
 * @param dsTipSit              descripcion tipo situacion
 * @param cdUnieco              codigo aseguradora
 * @param dsUnieco              nombre aseguradora
 * @param cdRamo                codigo producto
 * @param dsRamo                nombre producto    
 * @param cdTabla               codigo tabla    
 * @param swFormaCalculo        codigo forma calculo
 * @param atributo              atributo
 * @param calculo               calculo
 */

public class ConfigurarFormaCalculoAtributoVariableVO {
	
	private String cdIden;
    private String cdElemento;
    private String dsElemen;
    private String cdTipSit;
    private String dsTipSit;
    private String cdUnieco;
    private String dsUnieco;
    private String cdRamo;
    private String dsRamo;
    private String cdTabla;
    private String swFormaCalculo;
    private String atributo;
    private String calculo;
	
    public String getCdIden() {
		return cdIden;
	}
	public void setCdIden(String cdIden) {
		this.cdIden = cdIden;
	}
	public String getCdElemento() {
		return cdElemento;
	}
	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}
	public String getDsElemen() {
		return dsElemen;
	}
	public void setDsElemen(String dsElemen) {
		this.dsElemen = dsElemen;
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
	public String getCdUnieco() {
		return cdUnieco;
	}
	public void setCdUnieco(String cdUnieco) {
		this.cdUnieco = cdUnieco;
	}
	public String getDsUnieco() {
		return dsUnieco;
	}
	public void setDsUnieco(String dsUnieco) {
		this.dsUnieco = dsUnieco;
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
	public String getCdTabla() {
		return cdTabla;
	}
	public void setCdTabla(String cdTabla) {
		this.cdTabla = cdTabla;
	}
	public String getSwFormaCalculo() {
		return swFormaCalculo;
	}
	public void setSwFormaCalculo(String swFormaCalculo) {
		this.swFormaCalculo = swFormaCalculo;
	}
	public String getAtributo() {
		return atributo;
	}
	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}
	public String getCalculo() {
		return calculo;
	}
	public void setCalculo(String calculo) {
		this.calculo = calculo;
	}
    

    
}
	
