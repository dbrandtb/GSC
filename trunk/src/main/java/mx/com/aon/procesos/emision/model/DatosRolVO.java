/**
 * 
 */
package mx.com.aon.procesos.emision.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Alejandro Garcia
 */
public class DatosRolVO{
	
	private String nmsituac;
	private String cdrol;
	private String cdperson;
	private String cdtipsit;
	private String nmsuplem;
	private String status;
	private String swformat;
	private String swobliga;
	private String nmlmax;
	private String nmlmin;
	private String ottabval;
	private String swproduc;
	private String swsuplem;
	private String gb_swformat;
	private String cdatribu;
	private String dsatribu;
	private String otvalor;
	private String dsnombre;
	private String dsdescripcion;
	private String tipoobjeto;
	
	
    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
	/**
	 * @return the nmsituac
	 */
	public String getNmsituac() {
		return nmsituac;
	}
	/**
	 * @param nmsituac the nmsituac to set
	 */
	public void setNmsituac(String nmsituac) {
		this.nmsituac = nmsituac;
	}
	/**
	 * @return the cdrol
	 */
	public String getCdrol() {
		return cdrol;
	}
	/**
	 * @param cdrol the cdrol to set
	 */
	public void setCdrol(String cdrol) {
		this.cdrol = cdrol;
	}
	/**
	 * @return the cdperson
	 */
	public String getCdperson() {
		return cdperson;
	}
	/**
	 * @param cdperson the cdperson to set
	 */
	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}
	/**
	 * @return the cdtipsit
	 */
	public String getCdtipsit() {
		return cdtipsit;
	}
	/**
	 * @param cdtipsit the cdtipsit to set
	 */
	public void setCdtipsit(String cdtipsit) {
		this.cdtipsit = cdtipsit;
	}
	/**
	 * @return the nmsuplem
	 */
	public String getNmsuplem() {
		return nmsuplem;
	}
	/**
	 * @param nmsuplem the nmsuplem to set
	 */
	public void setNmsuplem(String nmsuplem) {
		this.nmsuplem = nmsuplem;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the swformat
	 */
	public String getSwformat() {
		return swformat;
	}
	/**
	 * @param swformat the swformat to set
	 */
	public void setSwformat(String swformat) {
		this.swformat = swformat;
	}
	/**
	 * @return the swobliga
	 */
	public String getSwobliga() {
		return swobliga;
	}
	/**
	 * @param swobliga the swobliga to set
	 */
	public void setSwobliga(String swobliga) {
		this.swobliga = swobliga;
	}
	/**
	 * @return the nmlmax
	 */
	public String getNmlmax() {
		return nmlmax;
	}
	/**
	 * @param nmlmax the nmlmax to set
	 */
	public void setNmlmax(String nmlmax) {
		this.nmlmax = nmlmax;
	}
	/**
	 * @return the nmlmin
	 */
	public String getNmlmin() {
		return nmlmin;
	}
	/**
	 * @param nmlmin the nmlmin to set
	 */
	public void setNmlmin(String nmlmin) {
		this.nmlmin = nmlmin;
	}
	/**
	 * @return the ottabval
	 */
	public String getOttabval() {
		return ottabval;
	}
	/**
	 * @param ottabval the ottabval to set
	 */
	public void setOttabval(String ottabval) {
		this.ottabval = ottabval;
	}
	/**
	 * @return the swproduc
	 */
	public String getSwproduc() {
		return swproduc;
	}
	/**
	 * @param swproduc the swproduc to set
	 */
	public void setSwproduc(String swproduc) {
		this.swproduc = swproduc;
	}
	/**
	 * @return the swsuplem
	 */
	public String getSwsuplem() {
		return swsuplem;
	}
	/**
	 * @param swsuplem the swsuplem to set
	 */
	public void setSwsuplem(String swsuplem) {
		this.swsuplem = swsuplem;
	}
	/**
	 * @return the gb_swformat
	 */
	public String getGb_swformat() {
		return gb_swformat;
	}
	/**
	 * @param gb_swformat the gb_swformat to set
	 */
	public void setGb_swformat(String gb_swformat) {
		this.gb_swformat = gb_swformat;
	}
	/**
	 * @return the cdatribu
	 */
	public String getCdatribu() {
		return cdatribu;
	}
	/**
	 * @param cdatribu the cdatribu to set
	 */
	public void setCdatribu(String cdatribu) {
		this.cdatribu = cdatribu;
	}
	/**
	 * @return the dsatribu
	 */
	public String getDsatribu() {
		return dsatribu;
	}
	/**
	 * @param dsatribu the dsatribu to set
	 */
	public void setDsatribu(String dsatribu) {
		this.dsatribu = dsatribu;
	}
	/**
	 * @return the otvalor
	 */
	public String getOtvalor() {
		return otvalor;
	}
	/**
	 * @param otvalor the otvalor to set
	 */
	public void setOtvalor(String otvalor) {
		this.otvalor = otvalor;
	}
	/**
	 * @return the dsnombre
	 */
	public String getDsnombre() {
		return dsnombre;
	}
	/**
	 * @param dsnombre the dsnombre to set
	 */
	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
	}
	/**
	 * @return the dsdescripcion
	 */
	public String getDsdescripcion() {
		return dsdescripcion;
	}
	/**
	 * @param dsdescripcion the dsdescripcion to set
	 */
	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}
	/**
	 * @return the tipoobjeto
	 */
	public String getTipoobjeto() {
		return tipoobjeto;
	}
	/**
	 * @param tipoobjeto the tipoobjeto to set
	 */
	public void setTipoobjeto(String tipoobjeto) {
		this.tipoobjeto = tipoobjeto;
	}
	
}
