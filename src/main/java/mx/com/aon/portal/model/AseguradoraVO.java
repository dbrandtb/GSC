package mx.com.aon.portal.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AseguradoraVO {
	private String cdUniEco;
	private String dsUniEco;


    public String getCdUniEco() {
        return cdUniEco;
    }

    public void setCdUniEco(String cdUniEco) {
        this.cdUniEco = cdUniEco;
    }

    public String getDsUniEco() {
		return dsUniEco;
	}
	public void setDsUniEco(String dsUniEco) {
		this.dsUniEco = dsUniEco;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
