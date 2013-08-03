package mx.com.aon.portal.model.plan;

import java.io.Serializable;
/**
 * 
 * @author sergio.ramirez
 *
 */
public class ProductoVO implements Serializable{

	/**
	 *Serial version
	 */
	private static final long serialVersionUID = -4568059501815599444L;
	
	private String cdRamoP;
	private String dsRamo;
	
	/**
	 * 
	 * @return
	 */
	public String getCdRamoP() {
		return cdRamoP;
	}
	/**
	 * 
	 * @param cdRamoP
	 */
	public void setCdRamoP(String cdRamoP) {
		this.cdRamoP = cdRamoP;
	}
	/**
	 * 
	 * @return
	 */
	public String getDsRamo() {
		return dsRamo;
	}
	/**
	 * 
	 * @param dsRamo
	 */
	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}
	
}
