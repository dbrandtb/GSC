package mx.com.aon.configurador.pantallas.model.master;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Deprecated
public class MasterVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7435043084546864050L;
	
	private List<SectionVo> seccionList;

	public List<SectionVo> getSeccionList() {
		return seccionList;
	}

	public void setSeccionList(List<SectionVo> seccionList) {
		this.seccionList = seccionList;
	}
    
    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
