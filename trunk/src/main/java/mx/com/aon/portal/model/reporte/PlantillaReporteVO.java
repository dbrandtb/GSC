package mx.com.aon.portal.model.reporte;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Ricardo Possamai
 * Date: 20/08/2008
 * Time: 10:22:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlantillaReporteVO  implements Serializable {

        private int cdPlantilla;
        private int cdReporte;
        private int cdCorporativo;
        private int cdAseguradora;
        private int cdProducto;

        private String dsPlantilla;
        private String dsReporte;
        private String dsCorporativo;
        private String dsAseguradora;
        private String dsProducto;

        //Contienen los codigos antes de ser editado el registro, usado al momento de editar PlatillaReporte
        private int cdPlantillaAnterior;
        private int cdReporteAnterior;
        private int cdCorporativoAnterior;
        private int cdAseguradoraAnterior;
        private int cdProductoAnterior;

        private boolean seleccion=true;

    public boolean isSeleccion() {
        return seleccion;
    }

    public void setSeleccion(boolean seleccion) {
        this.seleccion = seleccion;
    }

    public int getCdPlantilla() {
        return cdPlantilla;
    }

    public void setCdPlantilla(int cdPlantilla) {
        this.cdPlantilla = cdPlantilla;
    }

    public int getCdProducto() {
        return cdProducto;
    }

    public void setCdProducto(int cdProducto) {
        this.cdProducto = cdProducto;
    }

    public int getCdAseguradora() {
        return cdAseguradora;
    }

    public void setCdAseguradora(int cdAseguradora) {
        this.cdAseguradora = cdAseguradora;
    }

    public int getCdCorporativo() {
        return cdCorporativo;
    }

    public void setCdCorporativo(int cdCorporativo) {
        this.cdCorporativo = cdCorporativo;
    }

    public int getCdReporte() {
        return cdReporte;
    }

    public void setCdReporte(int cdReporte) {
        this.cdReporte = cdReporte;
    }


        public String getDsPlantilla() {
        return dsPlantilla;
    }

    public void setDsPlantilla(String dsPlantilla) {
        this.dsPlantilla = dsPlantilla;
    }

    public String getDsProducto() {
        return dsProducto;
    }

    public void setDsProducto(String dsProducto) {
        this.dsProducto = dsProducto;
    }

    public String getDsAseguradora() {
        return dsAseguradora;
    }

    public void setDsAseguradora(String dsAseguradora) {
        this.dsAseguradora = dsAseguradora;
    }

    public String getDsCorporativo() {
        return dsCorporativo;
    }

    public void setDsCorporativo(String dsCorporativo) {
        this.dsCorporativo = dsCorporativo;
    }

    public String getDsReporte() {
        return dsReporte;
    }

    public void setDsReporte(String dsReporte) {
        this.dsReporte = dsReporte;
    }

    public int getCdPlantillaAnterior() {
         return cdPlantillaAnterior;
     }

     public void setCdPlantillaAnterior(int cdPlantillaAnterior) {
         this.cdPlantillaAnterior = cdPlantillaAnterior;
     }

     public int getCdReporteAnterior() {
         return cdReporteAnterior;
     }

     public void setCdReporteAnterior(int cdReporteAnterior) {
         this.cdReporteAnterior = cdReporteAnterior;
     }

     public int getCdCorporativoAnterior() {
         return cdCorporativoAnterior;
     }

     public void setCdCorporativoAnterior(int cdCorporativoAnterior) {
         this.cdCorporativoAnterior = cdCorporativoAnterior;
     }

     public int getCdAseguradoraAnterior() {
         return cdAseguradoraAnterior;
     }

     public void setCdAseguradoraAnterior(int cdAseguradoraAnterior) {
         this.cdAseguradoraAnterior = cdAseguradoraAnterior;
     }

     public int getCdProductoAnterior() {
         return cdProductoAnterior;
     }

     public void setCdProductoAnterior(int cdProductoAnterior) {
         this.cdProductoAnterior = cdProductoAnterior;
     }
    

}
