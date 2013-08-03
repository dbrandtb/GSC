package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener atributos de forma de calculo
 * 
 * @param cdIden                codigo identificacion
 * @param cdElemento            codigo de cliente  
 * @param cdTipSit              codigo tipo situacion
 * @param cdUnieco              codigo aseguradora
 * @param dsUnieco              nombre aseguradora    
 * @param cdRamo                codigo producto
 * @param cdTabla               codigo tabla
 * @param dsElemen              nombre cliente
 * @param swFormaCalculo        codigo de forma de calculo    
 */

public class FormaDeCalculoAtributoVO {

	private String cdIden;
	private String cdElemento;
	private String cdTipSit;
	private String cdUnieco;
	private String cdRamo;
	private String cdTabla;
	private String swFormaCalculo;
	
	
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
	public String getCdTipSit() {
		return cdTipSit;
	}
	public void setCdTipSit(String cdTipSit) {
		this.cdTipSit = cdTipSit;
	}
	public String getCdUnieco() {
		return cdUnieco;
	}
	public void setCdUnieco(String cdUnieco) {
		this.cdUnieco = cdUnieco;
	}
	public String getCdRamo() {
		return cdRamo;
	}
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
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

}



