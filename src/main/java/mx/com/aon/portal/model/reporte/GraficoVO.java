package mx.com.aon.portal.model.reporte;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: jORGE mAGUIÑA
 * Date: 01-oct-2008
 * Time: 14:50:06
 * To change this template use File | Settings | File Templates.
 */
public class GraficoVO implements Serializable {
  private static final long serialVersionUID = 3781183579354410233L;
   private String cdReporte;
   private String nmGrafico;
   private String descripcion;


    public String getCdReporte() {
        return cdReporte;
    }

    public void setCdReporte(String cdReporte) {
        this.cdReporte = cdReporte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNmGrafico() {
        return nmGrafico;
    }

    public void setNmGrafico(String nmGrafico) {
        this.nmGrafico = nmGrafico;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
