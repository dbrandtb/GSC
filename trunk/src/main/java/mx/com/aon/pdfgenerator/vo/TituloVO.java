package mx.com.aon.pdfgenerator.vo;
import java.io.Serializable;


/**
 *  VO para los reportes en PDF de las Caratulas
 *  
 * @author 
 *
 */
public class TituloVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7556636276953925177L;
	
	private String cdRamo;
	private String dsRamo;
	private String cdMoneda;
	private String dsMoneda;
	private String nmpoliex;
	private String tituloCaratula;
	
	public String getTituloCaratula() {
		return tituloCaratula != null ? tituloCaratula : "" ;
	}
	public void setTituloCaratula(String tituloCaratula) {
		this.tituloCaratula = tituloCaratula;
	}
	public String getCdRamo() {
		return cdRamo != null ? cdRamo : "";
	}
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}
	public String getDsRamo() {
		return dsRamo != null ? dsRamo : "";
	}
	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}
	public String getCdMoneda() {
		return cdMoneda != null ? cdMoneda : "";
	}
	public void setCdMoneda(String cdMoneda) {
		this.cdMoneda = cdMoneda;
	}
	public String getDsMoneda() {
		return dsMoneda != null ? dsMoneda : "";
	}
	public void setDsMoneda(String dsMoneda) {
		this.dsMoneda = dsMoneda;
	}
	public String getNmpoliex() {
		return nmpoliex != null ? nmpoliex : "";
	}
	public void setNmpoliex(String nmpoliex) {
		this.nmpoliex = nmpoliex;
	}
}


