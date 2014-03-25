package mx.com.gseguros.portal.consultas.model;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Jose Garcia
 *
 */
public class ConsultaDatosSiniestrosVO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8555353864912795413L;

	private String ntramite;
	private String nmautser;
	private String nmsinies;
	private String feocurre;
	private String feapertu;
	

	public String getNtramite() {
		return ntramite;
	}


	public void setNtramite(String ntramite) {
		this.ntramite = ntramite;
	}


	public String getNmautser() {
		return nmautser;
	}


	public void setNmautser(String nmautser) {
		this.nmautser = nmautser;
	}


	public String getNmsinies() {
		return nmsinies;
	}


	public void setNmsinies(String nmsinies) {
		this.nmsinies = nmsinies;
	}


	public String getFeocurre() {
		return feocurre;
	}


	public void setFeocurre(String feocurre) {
		this.feocurre = feocurre;
	}


	public String getFeapertu() {
		return feapertu;
	}


	public void setFeapertu(String feapertu) {
		this.feapertu = feapertu;
	}


	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}	
	
}
