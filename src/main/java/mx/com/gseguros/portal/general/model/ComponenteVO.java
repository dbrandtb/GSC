package mx.com.gseguros.portal.general.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ComponenteVO
{
	public final static int TIPO_GENERICO = 0;
	public final static int TIPO_TATRISIT = 1;
	public final static int TIPO_TATRIPOL = 2;
	public final static int TIPO_TATRIGAR = 3;
	public final static int TIPO_TATRIPER = 4;
	
	public final static String TIPOCAMPO_ALFANUMERICO = "A";
	public final static String TIPOCAMPO_NUMERICO     = "N";
	public final static String TIPOCAMPO_FECHA        = "F";
	public final static String TIPOCAMPO_TEXTAREA     = "T";
	public final static String TIPOCAMPO_PORCENTAJE   = "P";
	
	public final static String RENDERER_MONEY = "MONEY";
	public final static String RENDERER_MONEY_EXT = "Ext.util.Format.usMoney";
	
	public final static String COLUMNA_OCULTA = "H"; 
	
	private int     type          = -1;
	private String  label         = null;
	private String  tipoCampo     = null;
	private String  catalogo      = null;
	private boolean dependiente   = false;
	private int     minLength     = -1;
	private boolean flagMinLength = false;
	private int     maxLength     = -1;
	private boolean flagMaxLength = false;
	private boolean obligatorio   = false;
	private String  columna       = null;
	private String  renderer      = null;
	private String  nameCdatribu  = null;
	private boolean flagEsAtribu  = false;
	private boolean soloLectura   = false;
	private String  queryParam    = null;
	private String  value         = null;
	private boolean oculto        = false;
	private String  paramName1    = null;
	private String  paramValue1   = null;
	private String  paramName2    = null;
	private String  paramValue2   = null;
	private String  paramName3    = null;
	private String  paramValue3   = null;
	private String  paramName4    = null;
	private String  paramValue4   = null;
	private String  paramName5    = null;
	private String  paramValue5   = null;
	private boolean comboVacio    = false;
	private String  defaultValue  = null;
	
	private String swsuscri = null;
	private String swtarifi = null;
	private String swpresen = null;

	private String icon = null;
	private String handler = null;
	
	private int width = 0;
	
	public ComponenteVO(){}
	
	public ComponenteVO(
			int      type
			,String  label
			,String  tipoCampo
			,String  catalogo
			,boolean isDependiente
			,int     minLength
			,boolean flagMinLength
			,int     maxLength
			,boolean flagMaxLength
			,boolean isObligatorio
			,String  columna
			,String  renderer
			,String  nameCdatribu
			,boolean flagEsAtribu
			,boolean isSoloLectura
			,String  queryParam
			,String  value
			,boolean isOculto
			,String  paramName1
			,String  paramValue1
			,String  paramName2
			,String  paramValue2
			,String  paramName3
			,String  paramValue3
			,String  paramName4
			,String  paramValue4
			,String  paramName5
			,String  paramValue5
			,boolean isComboVacio
			,String icon
			,String handler
			)
	{
		this.type          = type;
		this.label         = label;
		this.tipoCampo     = tipoCampo;
		this.catalogo      = catalogo;
		this.dependiente   = isDependiente;
		this.minLength     = minLength;
		this.flagMinLength = flagMinLength;
		this.maxLength     = maxLength;
		this.flagMaxLength = flagMaxLength;
		this.obligatorio   = isObligatorio;
		this.columna       = columna;
		this.renderer      = renderer;
		this.nameCdatribu  = nameCdatribu;
		this.flagEsAtribu  = flagEsAtribu;
		this.soloLectura   = isSoloLectura;
		this.queryParam    = queryParam;
		this.value         = value;
		this.oculto        = isOculto;
		this.paramName1    = paramName1;
		this.paramValue1   = paramValue1;
		this.paramName2    = paramName2;
		this.paramValue2   = paramValue2;
		this.paramName3    = paramName3;
		this.paramValue3   = paramValue3;
		this.paramName4    = paramName4;
		this.paramValue4   = paramValue4;
		this.paramName5    = paramName5;
		this.paramValue5   = paramValue5;
		this.comboVacio    = isComboVacio;
		this.icon          = icon;
		this.handler       = handler;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getTipoCampo() {
		return tipoCampo;
	}
	
	public void setTipoCampo(String tipoCampo) {
		this.tipoCampo = tipoCampo;
	}
	
	public String getCatalogo() {
		return catalogo;
	}
	
	public void setCatalogo(String catalogo) {
		this.catalogo = catalogo;
	}
	
	public boolean isDependiente() {
		return dependiente;
	}
	
	public void setDependiente(boolean dependiente) {
		this.dependiente = dependiente;
	}
	
	public int getMinLength() {
		return minLength;
	}
	
	public void setMinLength(int minLenght) {
		this.minLength = minLenght;
	}
	
	public int getMaxLength() {
		return maxLength;
	}
	
	public void setMaxLength(int maxLenght) {
		this.maxLength = maxLenght;
	}
	
	public boolean isObligatorio() {
		return obligatorio;
	}
	
	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}
	
	public String getColumna() {
		return columna;
	}
	
	public void setColumna(String columna) {
		this.columna = columna;
	}
	
	public String getRenderer() {
		return renderer;
	}
	
	public void setRenderer(String renderer) {
		this.renderer = renderer;
	}
	
	public String getNameCdatribu() {
		return nameCdatribu;
	}
	
	public void setNameCdatribu(String nameCdatribu) {
		this.nameCdatribu = nameCdatribu;
	}
	
	public boolean isSoloLectura() {
		return soloLectura;
	}
	
	public void setSoloLectura(boolean soloLectura) {
		this.soloLectura = soloLectura;
	}
	
	public String getQueryParam() {
		return queryParam;
	}
	
	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isOculto() {
		return oculto;
	}
	
	public void setOculto(boolean oculto) {
		this.oculto = oculto;
	}
	
	public String getParamName1() {
		return paramName1;
	}
	
	public void setParamName1(String paramName1) {
		this.paramName1 = paramName1;
	}
	
	public String getParamValue1() {
		return paramValue1;
	}
	
	public void setParamValue1(String paramValue1) {
		this.paramValue1 = paramValue1;
	}
	
	public String getParamName2() {
		return paramName2;
	}
	
	public void setParamName2(String paramName2) {
		this.paramName2 = paramName2;
	}
	
	public String getParamValue2() {
		return paramValue2;
	}
	
	public void setParamValue2(String paramValue2) {
		this.paramValue2 = paramValue2;
	}
	
	public String getParamName3() {
		return paramName3;
	}
	
	public void setParamName3(String paramName3) {
		this.paramName3 = paramName3;
	}
	
	public String getParamValue3() {
		return paramValue3;
	}
	
	public void setParamValue3(String paramValue3) {
		this.paramValue3 = paramValue3;
	}
	
	public String getParamName4() {
		return paramName4;
	}
	
	public void setParamName4(String paramName4) {
		this.paramName4 = paramName4;
	}
	
	public String getParamValue4() {
		return paramValue4;
	}
	
	public void setParamValue4(String paramValue4) {
		this.paramValue4 = paramValue4;
	}
	
	public String getParamName5() {
		return paramName5;
	}
	
	public void setParamName5(String paramName5) {
		this.paramName5 = paramName5;
	}
	
	public String getParamValue5() {
		return paramValue5;
	}
	
	public void setParamValue5(String paramValue5) {
		this.paramValue5 = paramValue5;
	}
	
	public boolean isFlagMinLength() {
		return flagMinLength;
	}
	
	public void setFlagMinLength(boolean flagMinLength) {
		this.flagMinLength = flagMinLength;
	}
	
	public boolean isFlagMaxLength() {
		return flagMaxLength;
	}
	
	public void setFlagMaxLength(boolean flagMaxLength) {
		this.flagMaxLength = flagMaxLength;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isFlagEsAtribu() {
		return flagEsAtribu;
	}

	public void setFlagEsAtribu(boolean flagEsAtribu) {
		this.flagEsAtribu = flagEsAtribu;
	}

	public String getSwsuscri() {
		return swsuscri;
	}

	public void setSwsuscri(String swsuscri) {
		this.swsuscri = swsuscri;
	}

	public String getSwtarifi() {
		return swtarifi;
	}

	public void setSwtarifi(String swtarifi) {
		this.swtarifi = swtarifi;
	}

	public String getSwpresen() {
		return swpresen;
	}

	public void setSwpresen(String swpresen) {
		this.swpresen = swpresen;
	}
	
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public boolean isComboVacio() {
		return comboVacio;
	}

	public void setComboVacio(boolean comboVacio) {
		this.comboVacio = comboVacio;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}