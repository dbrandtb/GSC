package mx.com.aon.portal.model.reporte;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Ricardo Possamai
 * Date: 15/07/2008
 * Time: 11:28:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class PlantillaVO  implements Serializable {
    private String cdPlantilla;
    private String dsPlantilla;
    private String status;

    public String getCdPlantilla() {
        return cdPlantilla;
    }

    public void setCdPlantilla(String cdPlantilla) {
        this.cdPlantilla = cdPlantilla;
    }

    public String getDsPlantilla() {
        return dsPlantilla;
    }

    public void setDsPlantilla(String dsPlantilla) {
        this.dsPlantilla = dsPlantilla;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

      
}
