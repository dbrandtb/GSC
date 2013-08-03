/**
 * 
 */
package mx.com.aon.procesos.emision.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Cesar Hernandex
 *
 */
public class PolizaMaestraVO {

	private String cdpolmtra;
	private String cdelemento;
	private String cdcia;
	private String cdramo;
	private String cdtipo;
	private String nmpoliex;
	private String nmpoliza;
	private String feinicio;
	private String fefin;
	private String dselemen;
	private String dsramo;
	private String dsunieco;
	private String dstipo;
	private String cdnumpol;
	private String cdnumren;
	private String cdtipsit;
	private String dstipsit;
    
	
	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }


	/**
	 * @return the cdpolmtra
	 */
	public String getCdpolmtra() {
		return cdpolmtra;
	}


	/**
	 * @param cdpolmtra the cdpolmtra to set
	 */
	public void setCdpolmtra(String cdpolmtra) {
		this.cdpolmtra = cdpolmtra;
	}


	/**
	 * @return the cdelemento
	 */
	public String getCdelemento() {
		return cdelemento;
	}


	/**
	 * @param cdelemento the cdelemento to set
	 */
	public void setCdelemento(String cdelemento) {
		this.cdelemento = cdelemento;
	}


	/**
	 * @return the cdcia
	 */
	public String getCdcia() {
		return cdcia;
	}


	/**
	 * @param cdcia the cdcia to set
	 */
	public void setCdcia(String cdcia) {
		this.cdcia = cdcia;
	}


	/**
	 * @return the cdramo
	 */
	public String getCdramo() {
		return cdramo;
	}


	/**
	 * @param cdramo the cdramo to set
	 */
	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}


	/**
	 * @return the cdtipo
	 */
	public String getCdtipo() {
		return cdtipo;
	}


	/**
	 * @param cdtipo the cdtipo to set
	 */
	public void setCdtipo(String cdtipo) {
		this.cdtipo = cdtipo;
	}


	/**
	 * @return the nmpoliex
	 */
	public String getNmpoliex() {
		return nmpoliex;
	}


	/**
	 * @param nmpoliex the nmpoliex to set
	 */
	public void setNmpoliex(String nmpoliex) {
		this.nmpoliex = nmpoliex;
	}


	/**
	 * @return the nmpoliza
	 */
	public String getNmpoliza() {
		return nmpoliza;
	}


	/**
	 * @param nmpoliza the nmpoliza to set
	 */
	public void setNmpoliza(String nmpoliza) {
		this.nmpoliza = nmpoliza;
	}


	/**
	 * @return the feinicio
	 */
	public String getFeinicio() {
		return feinicio;
	}


	/**
	 * @param feinicio the feinicio to set
	 */
	public void setFeinicio(String feinicio) {
		this.feinicio = feinicio;
	}


	/**
	 * @return the fefin
	 */
	public String getFefin() {
		return fefin;
	}


	/**
	 * @param fefin the fefin to set
	 */
	public void setFefin(String fefin) {
		this.fefin = fefin;
	}


	/**
	 * @return the dselemen
	 */
	public String getDselemen() {
		return dselemen;
	}


	/**
	 * @param dselemen the dselemen to set
	 */
	public void setDselemen(String dselemen) {
		this.dselemen = dselemen;
	}


	/**
	 * @return the dsramo
	 */
	public String getDsramo() {
		return dsramo;
	}


	/**
	 * @param dsramo the dsramo to set
	 */
	public void setDsramo(String dsramo) {
		this.dsramo = dsramo;
	}


	/**
	 * @return the dsunieco
	 */
	public String getDsunieco() {
		return dsunieco;
	}


	/**
	 * @param dsunieco the dsunieco to set
	 */
	public void setDsunieco(String dsunieco) {
		this.dsunieco = dsunieco;
	}


	/**
	 * @return the dstipo
	 */
	public String getDstipo() {
		return dstipo;
	}


	/**
	 * @param dstipo the dstipo to set
	 */
	public void setDstipo(String dstipo) {
		this.dstipo = dstipo;
	}


	public String getCdnumpol() {
		return cdnumpol;
	}


	public void setCdnumpol(String cdnumpol) {
		this.cdnumpol = cdnumpol;
	}


	public String getCdnumren() {
		return cdnumren;
	}


	public void setCdnumren(String cdnumren) {
		this.cdnumren = cdnumren;
	}

	public String getCdtipsit() {
		return cdtipsit;
	}


	public void setCdtipsit(String cdtipsit) {
		this.cdtipsit = cdtipsit;
	}


	public String getDstipsit() {
		return dstipsit;
	}


	public void setDstipsit(String dstipsit) {
		this.dstipsit = dstipsit;
	}
	
}
