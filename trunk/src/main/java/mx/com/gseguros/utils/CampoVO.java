package mx.com.gseguros.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CampoVO {

	/**
	 * Valor alfanumerico
	 */
	public static final String ALFANUMERICO = "A";
	/**
	 * Valor numerico entero
	 */
	public static final String NUMERICO = "N";
	/**
	 * Valor con punto decimal
	 */
	public static final String PORCENTAJE = "P";
	/**
	 * 
	 */
	public static final String FECHA = "F";
	
	
	public CampoVO() {
		super();
	}
	
	public CampoVO(String type, Integer length, Integer minValue, Integer maxValue, boolean nullable) {
		super();
		this.type = type;
		this.length = length;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.nullable = nullable;
	}


	private String type;
	
	private Integer length;
	
	private Integer minValue;
	
	private Integer maxValue;
	
	private boolean nullable;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	
}