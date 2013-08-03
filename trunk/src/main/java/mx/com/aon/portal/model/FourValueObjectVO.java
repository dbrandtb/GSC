package mx.com.aon.portal.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class FourValueObjectVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5294130829640106666L;

	/**
	 * Atributo que es un valor 
	 */
	private String value;
	
	/**
	 * Atributo que es visible,
	 */
	private String label;
	
	/**
	 * Atributo extra
	 */
	private String extra;
	
	
	private String extra2;
	
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * 
	 * @return the extra property
	 */
	public String getExtra() {
		return extra;
	}

	/**
	 * 
	 * @param extra the extra value to set
	 */
	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public String getExtra2() {
		return extra2;
	}

	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}
}
