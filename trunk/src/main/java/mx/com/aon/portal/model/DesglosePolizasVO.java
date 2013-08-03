package mx.com.aon.portal.model;

/**
 * Clase que modela la estructura de datos a utilizarse en la pantalla Desglose de Polizas.
 * 
 * @param  cdPerson
*  @param  dsCliente
*  @param  cdTipCon
*  @param  dsTipCon
*  @param  cdElemento
*  @param  cdRamo
*  @param  dsRamo
 *
 */
public class DesglosePolizasVO {

    private String cdPerson;
    private String dsCliente;
    private String cdTipCon;
    private String dsTipCon;
    private String cdElemento;
    private String cdRamo;
    private String dsRamo;   
	
	public String getCdElemento() {
		return cdElemento;
	}
	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}
	public String getCdPerson() {
		return cdPerson;
	}
	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}
	public String getCdTipCon() {
		return cdTipCon;
	}
	public void setCdTipCon(String cdTipCon) {
		this.cdTipCon = cdTipCon;
	}
	public String getCdRamo() {
		return cdRamo;
	}
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}
	public String getDsCliente() {
		return dsCliente;
	}
	public void setDsCliente(String dsCliente) {
		this.dsCliente = dsCliente;
	}
	public String getDsTipCon() {
		return dsTipCon;
	}
	public void setDsTipCon(String dsTipCon) {
		this.dsTipCon = dsTipCon;
	}
	public String getDsRamo() {
		return dsRamo;
	}
	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}
	
  

}
