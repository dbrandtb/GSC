package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener el acciones de renovacion
 * 
 * @param cdRenova              codigo renovacion
 * @param cdRango               codigo rango  
 * @param cdInicioRango         codigo inicio rango
 * @param cdFinRango            codigo fin rango    
 * @param dsRango               descripcion rango
 * @param dsElemen              nombre cliente
 * @param dsUniEco              nombre aseguradora
 * @param dsRamo                nombre producto
 * @param tipo                  descripcion tipo  
 */
public class RangoRenovacionReporteVO {

    private String cdRenova;
    private String cdRango;
    private String cdInicioRango;
    private String cdFinRango;
    private String dsRango;
    
    private String dsElemen;
    private String dsUniEco;
    private String dsRamo;
    private String tipo;
    
	public String getCdRenova() {
		return cdRenova;
	}
	public void setCdRenova(String cdRenova) {
		this.cdRenova = cdRenova;
	}
	public String getCdRango() {
		return cdRango;
	}
	public void setCdRango(String cdRango) {
		this.cdRango = cdRango;
	}
	public String getCdInicioRango() {
		return cdInicioRango;
	}
	public void setCdInicioRango(String cdInicioRango) {
		this.cdInicioRango = cdInicioRango;
	}
	public String getCdFinRango() {
		return cdFinRango;
	}
	public void setCdFinRango(String cdFinRango) {
		this.cdFinRango = cdFinRango;
	}
	public String getDsRango() {
		return dsRango;
	}
	public void setDsRango(String dsRango) {
		this.dsRango = dsRango;
	}
	public String getDsElemen() {
		return dsElemen;
	}
	public void setDsElemen(String dsElemen) {
		this.dsElemen = dsElemen;
	}
	public String getDsUniEco() {
		return dsUniEco;
	}
	public void setDsUniEco(String dsUniEco) {
		this.dsUniEco = dsUniEco;
	}
	public String getDsRamo() {
		return dsRamo;
	}
	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
    
    
    
    
 
}
