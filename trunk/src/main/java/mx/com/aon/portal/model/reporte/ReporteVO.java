package mx.com.aon.portal.model.reporte;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Oscar parales
 * Date: 23-jun-2008
 * Time: 17:00:22
 * To change this template use File | Settings | File Templates.
 */
public class ReporteVO implements Serializable {

    private static final long serialVersionUID = 3781183579334410222L;
    private int cdReporte;
    private String dsReporte;
    private String nmReporte;
    private String numAtributos;



    public int getCdReporte() {
        return cdReporte;
    }

    public void setCdReporte(int cdReporte) {
        this.cdReporte = cdReporte;
    }

    public String getDsReporte() {
        return dsReporte;
    }

    public void setDsReporte(String dsReporte) {
        this.dsReporte = dsReporte;
    }

    public String getNmReporte() {
        return nmReporte;
    }

    public void setNmReporte(String nmReporte) {
        this.nmReporte = nmReporte;
    }

    public String getNumAtributos() {
        return numAtributos;
    }

    public void setNumAtributos(String numAtributos) {
        this.numAtributos = numAtributos;
    }

    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
