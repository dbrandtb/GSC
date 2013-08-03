package mx.com.aon.configurador.pantallas.model.master;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;



public class PropertyVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1154596139394016429L;
	
    private String cdPropiedad;
	private String dsPropiedad;
	private String otvalor;
	private String swModificable;
	
    /**
     * @return the cdPropiedad
     */
    public String getCdPropiedad() {
        return cdPropiedad;
    }



    /**
     * @param cdPropiedad the cdPropiedad to set
     */
    public void setCdPropiedad(String cdPropiedad) {
        this.cdPropiedad = cdPropiedad;
    }



    /**
     * @return the dsPropiedad
     */
    public String getDsPropiedad() {
        return dsPropiedad;
    }



    /**
     * @param dsPropiedad the dsPropiedad to set
     */
    public void setDsPropiedad(String dsPropiedad) {
        this.dsPropiedad = dsPropiedad;
    }



    /**
     * @return the otvalor
     */
    public String getOtvalor() {
        return otvalor;
    }



    /**
     * @param otvalor the otvalor to set
     */
    public void setOtvalor(String otvalor) {
        this.otvalor = otvalor;
    }



    /**
     * @return the swModificable
     */
    public String getSwModificable() {
        return swModificable;
    }



    /**
     * @param swModificable the swModificable to set
     */
    public void setSwModificable(String swModificable) {
        this.swModificable = swModificable;
    }
    
    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
