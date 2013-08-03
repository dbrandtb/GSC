/**
 * 
 */
package mx.com.aon.portal.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author CIMA_USR
 *
 */

public class RequisitoCancelacionVO {

/*	EL CURSOR
 * "CDRAZON"  "CDADVERT"  "DSADVERT"   
*/
	private String cdRazon;
	private String cdAdvert;
	private String dsAdvert;
	
	/**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
	/**
	 * @return the cdRazon
	 */
	public String getCdRazon() {
		return cdRazon;
	}
	/**
	 * @param cdRazon the cdRazon to set
	 */
	public void setCdRazon(String cdRazon) {
		this.cdRazon = cdRazon;
	}
	/**
	 * @return the cdAdvert
	 */
	public String getCdAdvert() {
		return cdAdvert;
	}
	/**
	 * @param cdAdvert the cdAdvert to set
	 */
	public void setCdAdvert(String cdAdvert) {
		this.cdAdvert = cdAdvert;
	}
	/**
	 * @return the dsAdvert
	 */
	public String getDsAdvert() {
		return dsAdvert;
	}
	/**
	 * @param dsAdvert the dsAdvert to set
	 */
	public void setDsAdvert(String dsAdvert) {
		this.dsAdvert = dsAdvert;
	}


    
}



