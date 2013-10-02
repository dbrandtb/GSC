package mx.com.aon.portal.service.reportes;

import mx.com.aon.portal.model.reporte.*;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.export.model.TableModelExport;
import mx.com.gseguros.exception.ApplicationException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Cesar
 * Date: 12-jun-2008
 * Time: 16:22:07
 * To change this template use File | Settings | File Templates.
 */

public interface ReportesManager {

    List <ReporteVO> getReportes(String dsReporteB) throws ApplicationException;

    List <ReporteVO> getReportesEjecutar(String dsReporteB,String ramos) throws ApplicationException;

    ReporteVO insertaReporte (ReporteVO reporte) throws ApplicationException;

    void editarReporte (ReporteVO reporte) throws ApplicationException;

    public String borrarReporte (int cdReporte) throws ApplicationException;

    List <ComboGraficoVo> getComboGrafico(String tipoTabla) throws ApplicationException;

    List <ComboGraficoVo> getComboAseguradora(String param,String params2) throws ApplicationException;

    List <ComboGraficoVo> getComboProductos(String idAseguradora,String param,String params2) throws ApplicationException;

    List <ComboGraficoVo> getComboCuenta(String idAseguradora,String param,String params2) throws ApplicationException;

    void cargarCART001 (String aseguradora,String producto,String fec_ini,String fec_fin) throws ApplicationException;

    List <GraficoVO> getGrafico(String cdReporte) throws ApplicationException;

    void insertarGrafico (String cdReporte,String nombreGrafico,String tipo) throws ApplicationException;

    void editarGrafico (String cdReporte,String nombreGrafico,String tipo) throws ApplicationException;

    void borrarGrafico (String cdReporte,String nombreGrafico) throws ApplicationException;

    ReporteVO validarGrafico (String reporte) throws ApplicationException;

    List <AtributosVO> getAtributos(int cdReporte) throws ApplicationException;

    void insertaAtributo (AtributosVO atributo) throws ApplicationException;

    void borrarAtributo (AtributosVO atributo) throws ApplicationException;

    void editarAtributo (AtributosVO atributo) throws ApplicationException;

    List <AtributosVO> getProductos(int cdReporte) throws ApplicationException;
    
    List <AtributosVO> getProductosPlantillas(String cdPlantilla) throws ApplicationException;

    List <ComboGraficoVo> getComboReportesProductos() throws ApplicationException;
    
    List <ComboGraficoVo> getComboReportesProductosPlantillas() throws ApplicationException;

    String insertarProducto (int cdReporte,String codProducto) throws ApplicationException;
    
    String insertarProductoPlantillas (String cdPlantilla,String codProducto) throws ApplicationException;

    String editarProducto (int cdReporte,String codProducto,String cdProductoViejo) throws ApplicationException;
    
    String editarProductoPlantillas (String cdPlantilla,String codProducto, String codProductoAnt) throws ApplicationException;

    void borrarProducto (int cdReporte,String codProducto) throws ApplicationException;
    
    String borrarProductoPlantillas (String cdPlantilla,String codProducto) throws ApplicationException;

 List <PlantillaVO> getPlantillas(String dsPlantilla) throws ApplicationException;
    // public PagedList getPlantillas(String dsPlantilla,int start, int limit) throws ApplicationException;

    void insertarPlantilla (PlantillaVO plantilla) throws ApplicationException;

    public String borrarPlantilla (String cdPlantilla) throws ApplicationException;

    void editarPlantilla (PlantillaVO plantilla) throws ApplicationException;

    public void insertaPlantillaAtributo(AtributosVO atributo) throws ApplicationException;

    public String borrarPlantillaAtributo(AtributosVO atributo) throws ApplicationException;

    public void editarPlantillaAtributo(AtributosVO atributo) throws ApplicationException ;

    public List <AtributosVO> getPlantillasAtributos(String cdPlantilla) throws ApplicationException;
    public  List<TablaApoyoVO> getPlantAtribTablaApoyo(String cdPlantilla) throws ApplicationException;

    public List <PlantillaReporteVO> getPlantillaReporte(PlantillaReporteVO plantilla_repote) throws ApplicationException;

    //public PagedList getPlantillaReporte(PlantillaReporteVO plantilla_repote,int start, int limit) throws ApplicationException;
    public List <PlantillaReporteVO> getListaCorporativo(String dsCorporativo) throws ApplicationException;

    public List <PlantillaReporteVO> getListaAseguradora(String dsAseguradora) throws ApplicationException;

    public List <PlantillaReporteVO> getListaPlantilla(String dsPlantilla) throws ApplicationException;

    public List <PlantillaReporteVO> getListaProducto(String dsProducto) throws ApplicationException;

    public List <ReporteVO> getListaReporte(String dsReporte) throws ApplicationException;

    public void asociarPlantillaReporte(PlantillaReporteVO plantillaReporte) throws ApplicationException;

    public void editarAsociarPlantillaReporte(PlantillaReporteVO plantillaReporte) throws ApplicationException;

    public void borrarAsociarPlantillaReporte(PlantillaReporteVO plantillaReporte) throws ApplicationException;

    public TableModelExport getModelPrincipal(String dsReporteB) throws ApplicationException ;

    public TableModelExport getModelAtributos(String codigoRep) throws ApplicationException ;

    public TableModelExport getModelProductos(int codigoRep) throws ApplicationException ;
    
    public TableModelExport getModelProductosPlantillas(String cdPlantilla) throws ApplicationException ;

    public TableModelExport getModelGraficos(int codigoRep) throws ApplicationException ;

    public TableModelExport getModelPlantillas(String desPlantilla) throws ApplicationException;

    public TableModelExport getModelPlantillasAsociar(PlantillaReporteVO plantillaReporte) throws ApplicationException;

    public TableModelExport getModelPlantillasAtributos(String cdPlantilla) throws ApplicationException;
}
