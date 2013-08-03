package mx.com.aon.portal.model.reporte;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Jorge Maguiña
 * Date: 26-sep-2008
 * Time: 17:46:39
 * To change this template use File | Settings | File Templates.
 */
public class ComboGraficoVo implements Serializable {
     private static final long serialVersionUID = 3781183579334410212L;
    private String cdTabla;
    private String cdRegion;
    private String cdIdioma;
    private String codigo;
    private String descripC;
    private String descripL;
    private String dsLabel;
    private String dsIdioma;

    public String getCdTabla() {
        return cdTabla;
    }

    public void setCdTabla(String cdTabla) {
        this.cdTabla = cdTabla;
    }

    public String getCdRegion() {
        return cdRegion;
    }

    public void setCdRegion(String cdRegion) {
        this.cdRegion = cdRegion;
    }

    public String getCdIdioma() {
        return cdIdioma;
    }

    public void setCdIdioma(String cdIdioma) {
        this.cdIdioma = cdIdioma;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripC() {
        return descripC;
    }

    public void setDescripC(String descripC) {
        this.descripC = descripC;
    }

    public String getDescripL() {
        return descripL;
    }

    public void setDescripL(String descripL) {
        this.descripL = descripL;
    }

    public String getDsLabel() {
        return dsLabel;
    }

    public void setDsLabel(String dsLabel) {
        this.dsLabel = dsLabel;
    }

    public String getDsIdioma() {
        return dsIdioma;
    }

    public void setDsIdioma(String dsIdioma) {
        this.dsIdioma = dsIdioma;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
