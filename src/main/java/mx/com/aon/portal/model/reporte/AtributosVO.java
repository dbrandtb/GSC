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
public class AtributosVO implements Serializable {

    private static final long serialVersionUID = 3781183579334410222L;
    private String cdPlantilla;
    private int cdReporte;
    private String cdAtributo;
    private String dsAtributo;
    private String swFormat;
    private String nmLmin;
    private String nmLmax;
    private String otTabval;
    private String cdotTabval;
    private String cdExpres;
    private String entradaAtributo;


    public int getCdReporte() {
        return cdReporte;
    }

    public void setCdReporte(int cdReporte) {
        this.cdReporte = cdReporte;
    }

    public String getCdPlantilla() {
        return cdPlantilla;
    }

    public void setCdPlantilla(String cdPlantilla) {
        this.cdPlantilla = cdPlantilla;
    }

    public String getCdAtributo() {
        return cdAtributo;
    }

    public void setCdAtributo(String cdAtributo) {
        this.cdAtributo = cdAtributo;
    }

    public String getDsAtributo() {
        return dsAtributo;
    }

    public void setDsAtributo(String dsAtributo) {
        this.dsAtributo = dsAtributo;
    }

    public String getSwFormat() {
        return swFormat;
    }

    public void setSwFormat(String swFormat) {
        this.swFormat = swFormat;
    }

    public String getNmLmin() {
        return nmLmin;
    }

    public void setNmLmin(String nmLmin) {
        this.nmLmin = nmLmin;
    }

    public String getNmLmax() {
        return nmLmax;
    }

    public void setNmLmax(String nmLmax) {
        this.nmLmax = nmLmax;
    }

    public String getOtTabval() {
        return otTabval;
    }

    public void setOtTabval(String otTabval) {
        this.otTabval = otTabval;
    }

    public String getCdotTabval() {
        return cdotTabval;
    }

    public void setCdotTabval(String cdotTabval) {
        this.cdotTabval = cdotTabval;
    }

    public String getCdExpres() {
        return cdExpres;
    }

    public void setCdExpres(String cdExpres) {
        this.cdExpres = cdExpres;
    }

    public String getEntradaAtributo() {
        return entradaAtributo;
    }

    public void setEntradaAtributo(String entradaAtributo) {
        this.entradaAtributo = entradaAtributo;
    }       

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}