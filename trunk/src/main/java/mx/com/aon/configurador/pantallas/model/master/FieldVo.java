package mx.com.aon.configurador.pantallas.model.master;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Deprecated
public class FieldVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4889426963563281837L;

	private String dsCampo;
	
	private String cdCampo;
	
	private List<PropertyVo> propertyList;
	
	public List<PropertyVo> getPropertyList() {
		return propertyList;
	}
	public void setPropertyList(List<PropertyVo> propertyList) {
		this.propertyList = propertyList;
	}
    /**
     * @return the dsCampo
     */
    public String getDsCampo() {
        return dsCampo;
    }
    /**
     * @param dsCampo the dsCampo to set
     */
    public void setDsCampo(String dsCampo) {
        this.dsCampo = dsCampo;
    }

    /**
     * @return the cdCampo
     */
    public String getCdCampo() {
        return cdCampo;
    }
    /**
     * @param cdCampo the cdCampo to set
     */
    public void setCdCampo(String cdCampo) {
        this.cdCampo = cdCampo;
    }   

    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
