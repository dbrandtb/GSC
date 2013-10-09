package mx.com.aon.configurador.pantallas.model.master;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Deprecated
public class SectionVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -143759424265034410L;

	private String dsBloque;
	
	private String cdBloque;
	
	private List<FieldVo> fieldList;
	
	public List<FieldVo> getFieldList() {
		return fieldList;
	}
	public void setFieldList(List<FieldVo> fieldList) {
		this.fieldList = fieldList;
	}
    /**
     * @return the cdBloque
     */
    public String getCdBloque() {
        return cdBloque;
    }
    /**
     * @param cdBloque the cdBloque to set
     */
    public void setCdBloque(String cdBloque) {
        this.cdBloque = cdBloque;
    }
    /**
     * @return the dsBloque
     */
    public String getDsBloque() {
        return dsBloque;
    }
    /**
     * @param dsBloque the dsBloque to set
     */
    public void setDsBloque(String dsBloque) {
        this.dsBloque = dsBloque;
    }
    
    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
