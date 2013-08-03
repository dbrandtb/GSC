/**
 * 
 */
package mx.com.aon.procesos.emision.model;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Alejandro Garcia
 *
 */
public class IncisosVO {

	private String cdGarant;
	private String dsGarant;
	private String dsSumaAsegurada;
	private String dsDeducible;
    private String dsSumaAseguradaCapita;
	
	
    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
	/**
	 * @return the cdGarant
	 */
	public String getCdGarant() {
		return cdGarant;
	}
	/**
	 * @param cdGarant the cdGarant to set
	 */
	public void setCdGarant(String cdGarant) {
		this.cdGarant = cdGarant;
	}
	/**
	 * @return the dsGarant
	 */
	public String getDsGarant() {
		return dsGarant;
	}
	/**
	 * @param dsGarant the dsGarant to set
	 */
	public void setDsGarant(String dsGarant) {
		this.dsGarant = dsGarant;
	}
	/**
	 * @return the dsSumaAsegurada
	 */
	public String getDsSumaAsegurada() {
        if (StringUtils.isNotBlank(dsSumaAsegurada)) {
            if (StringUtils.contains(dsSumaAsegurada, '$')) {
                return dsSumaAsegurada;
            } else {
                String sumaAseguradaNumerica = null;
                
                sumaAseguradaNumerica = StringUtils.remove(dsSumaAsegurada, ',');
                sumaAseguradaNumerica = StringUtils.remove(sumaAseguradaNumerica, '.');
                
                if(StringUtils.isNumeric(sumaAseguradaNumerica)){
                    return new StringBuilder().append("$").append(dsSumaAsegurada).toString();
                } else {
                    return sumaAseguradaNumerica;
                }
                //return obtieneCantidadEnNumero(dsSumaAsegurada);
            }
        }
        
        return ""; //new StringBuilder().append("$").append(dsSumaAsegurada).toString();
	}
	/**
	 * @param dsSumaAsegurada the dsSumaAsegurada to set
	 */
	public void setDsSumaAsegurada(String dsSumaAsegurada) {
		this.dsSumaAsegurada = dsSumaAsegurada;
	}
	/**
	 * @return the dsDeducible
	 */
	public String getDsDeducible() {
		return dsDeducible;
	}
	/**
	 * @param dsDeducible the dsDeducible to set
	 */
	public void setDsDeducible(String dsDeducible) {
		this.dsDeducible = dsDeducible;
	}
	
    /**
     * Obtiene la suma asegurada en formato numérico
     * @param sumaAsegurada2
     * @return
     */
    private String obtieneCantidadEnNumero(String sumaAsegurada2) {
        String sumaAseguradaNumerica = null;
        int index = 0;
        
        sumaAseguradaNumerica = StringUtils.remove(sumaAsegurada2, ',');
        //sumaAseguradaNumerica = StringUtils.remove(sumaAseguradaNumerica, '$');
        
        if(StringUtils.isNumeric(sumaAseguradaNumerica)){
	        index = sumaAseguradaNumerica.indexOf('.');
	        
	        if (index > 0) {
	            sumaAseguradaNumerica = sumaAseguradaNumerica.substring(0, index); 
	        }
        }
        
        return sumaAseguradaNumerica;
    }
    /**
     * @return the dsSumaAseguradaCapita
     */
    public String getDsSumaAseguradaCapita() {
        return obtieneCantidadEnNumero(dsSumaAseguradaCapita);
    }
    /**
     * @param dsSumaAseguradaCapita the dsSumaAseguradaCapita to set
     */
    public void setDsSumaAseguradaCapita(String dsSumaAseguradaCapita) {
        this.dsSumaAseguradaCapita = dsSumaAseguradaCapita;
    }
}
