package mx.com.aon.portal.model.plan;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author sergio.ramirez
 * 
 */
public class PlanVO implements Serializable {

	/**
	 *Objeto Java con atributos propios de un plan.
	 */
	private static final long serialVersionUID = 3781183579334410289L;

	private String dsPlan;
	private String cdRamo;
	private String cdPlan;
	private String dsRamo;
	
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

	/**
	 * 
	 * @return
	 */

	public String getCdPlan() {
		return cdPlan;
	}

	public void setCdPlan(String cdPlan) {
		this.cdPlan = cdPlan;
	}

	/**
	 * 
	 * @return
	 */
	public String getDsPlan() {
		return dsPlan;
	}

	/**
	 * 
	 * @param dsPlan
	 */
	public void setDsPlan(String dsPlan) {
		this.dsPlan = dsPlan;
	}

	/**
	 * 
	 * @return
	 */

	public String getCdRamo() {
		return cdRamo;
	}

	/**
	 * 
	 * @param cdRamo
	 */
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

	/**
	 * 
	 * @return
	 */

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("cdPlan", cdPlan)
				.append("cdRamo", cdRamo)
				.append("dsRamo", dsRamo)
				.append("dsPlan", dsPlan).toString();

	}

}
