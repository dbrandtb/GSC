package mx.com.gseguros.portal.consultas.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author hector.lopez
 *
 */
public class ConsultaDatosPolizaVO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8555353864912795413L;

	private String nmsolici;
	private String feefecto;
	private String feinival;
	private String nsuplogi;
	private String feemisio;
	private String nlogisus;
	private String dstipsup;
	private String cdmoneda;
	private String dsmoneda;
	private String ottempot;
	private String dstempot;
	private String feproren;
	private String fevencim;
	private String nmrenova;
	private String cdperpag;
	private String dsperpag;
	private String swtarifi;
	private String dstarifi;
	private String cdtipcoa;
	private String dstipcoa;
	private String nmcuadro;
	private String dscuadro;
	private String porredau;
	private String ptpritot;
	private String cdmotanu;
	private String dsmotanu;
	private String cdperson;
	private String titular;
	private String cdrfc;
	public String getNmsolici() {
		return nmsolici;
	}
	public void setNmsolici(String nmsolici) {
		this.nmsolici = nmsolici;
	}
	public String getFeefecto() {
		return feefecto;
	}
	public void setFeefecto(String feefecto) {
		this.feefecto = feefecto;
	}
	public String getFeinival() {
		return feinival;
	}
	public void setFeinival(String feinival) {
		this.feinival = feinival;
	}
	public String getNsuplogi() {
		return nsuplogi;
	}
	public void setNsuplogi(String nsuplogi) {
		this.nsuplogi = nsuplogi;
	}
	public String getFeemisio() {
		return feemisio;
	}
	public void setFeemisio(String feemisio) {
		this.feemisio = feemisio;
	}
	public String getNlogisus() {
		return nlogisus;
	}
	public void setNlogisus(String nlogisus) {
		this.nlogisus = nlogisus;
	}
	public String getDstipsup() {
		return dstipsup;
	}
	public void setDstipsup(String dstipsup) {
		this.dstipsup = dstipsup;
	}
	public String getCdmoneda() {
		return cdmoneda;
	}
	public void setCdmoneda(String cdmoneda) {
		this.cdmoneda = cdmoneda;
	}
	public String getDsmoneda() {
		return dsmoneda;
	}
	public void setDsmoneda(String dsmoneda) {
		this.dsmoneda = dsmoneda;
	}
	public String getOttempot() {
		return ottempot;
	}
	public void setOttempot(String ottempot) {
		this.ottempot = ottempot;
	}
	public String getDstempot() {
		return dstempot;
	}
	public void setDstempot(String dstempot) {
		this.dstempot = dstempot;
	}
	public String getFeproren() {
		return feproren;
	}
	public void setFeproren(String feproren) {
		this.feproren = feproren;
	}
	public String getFevencim() {
		return fevencim;
	}
	public void setFevencim(String fevencim) {
		this.fevencim = fevencim;
	}
	public String getNmrenova() {
		return nmrenova;
	}
	public void setNmrenova(String nmrenova) {
		this.nmrenova = nmrenova;
	}
	public String getCdperpag() {
		return cdperpag;
	}
	public void setCdperpag(String cdperpag) {
		this.cdperpag = cdperpag;
	}
	public String getDsperpag() {
		return dsperpag;
	}
	public void setDsperpag(String dsperpag) {
		this.dsperpag = dsperpag;
	}
	public String getSwtarifi() {
		return swtarifi;
	}
	public void setSwtarifi(String swtarifi) {
		this.swtarifi = swtarifi;
	}
	public String getDstarifi() {
		return dstarifi;
	}
	public void setDstarifi(String dstarifi) {
		this.dstarifi = dstarifi;
	}
	public String getCdtipcoa() {
		return cdtipcoa;
	}
	public void setCdtipcoa(String cdtipcoa) {
		this.cdtipcoa = cdtipcoa;
	}
	public String getDstipcoa() {
		return dstipcoa;
	}
	public void setDstipcoa(String dstipcoa) {
		this.dstipcoa = dstipcoa;
	}
	public String getNmcuadro() {
		return nmcuadro;
	}
	public void setNmcuadro(String nmcuadro) {
		this.nmcuadro = nmcuadro;
	}
	public String getDscuadro() {
		return dscuadro;
	}
	public void setDscuadro(String dscuadro) {
		this.dscuadro = dscuadro;
	}
	public String getPorredau() {
		return porredau;
	}
	public void setPorredau(String porredau) {
		this.porredau = porredau;
	}
	public String getPtpritot() {
		return ptpritot;
	}
	public void setPtpritot(String ptpritot) {
		this.ptpritot = ptpritot;
	}
	public String getCdmotanu() {
		return cdmotanu;
	}
	public void setCdmotanu(String cdmotanu) {
		this.cdmotanu = cdmotanu;
	}
	public String getDsmotanu() {
		return dsmotanu;
	}
	public void setDsmotanu(String dsmotanu) {
		this.dsmotanu = dsmotanu;
	}
	public String getCdperson() {
		return cdperson;
	}
	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}
	public String getTitular() {
		return titular;
	}
	public void setTitular(String titular) {
		this.titular = titular;
	}
	public String getCdrfc() {
		return cdrfc;
	}
	public void setCdrfc(String cdrfc) {
		this.cdrfc = cdrfc;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}	
	
}
