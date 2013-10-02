package mx.com.aon.portal.web.reportes;

import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportModel;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.reporte.PlantillaReporteVO;
import mx.com.aon.portal.model.reporte.PlantillaVO;
import mx.com.aon.portal.model.reporte.ReporteVO;
import mx.com.aon.portal.service.reportes.ReportesManager;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.SessionAware;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: jorge
 * Date: 24/02/2009
 * Time: 02:06:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReportesPlantillasAsociarAction extends ActionSupport implements SessionAware {

    private static final transient Log log = LogFactory.getLog(ReportesPrincipalAction.class);
    private ReportesManager reportesManager;

    private Map session;
    private boolean success;
    private PlantillaReporteVO plantilaReporte;
    private PlantillaReporteVO inputp = new PlantillaReporteVO();
    private int cdCorporativo;
    private int cdAseguradora;
    private int cdProducto;
    private String dsCorporativo;
    private String dsAseguradora;
    private String dsProducto;
    private String dsPlantilla;
    private String dsReporte;
    private int cdReporte;
    private String cdPlantilla;
    private List<PlantillaReporteVO> plantillas_reportes;
    private List<ReporteVO> reportes;
    private String formato;
    private InputStream inputStream;
    private String filename;
    private ExportModel exportModel;
    private ExportMediator exportMediator;
    private String descripcion;
    private int start;
    private int limit;
    private int totalCount;

    List<PlantillaReporteVO> plantillas_reportes2 = new ArrayList<PlantillaReporteVO>();

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<ReporteVO> getReportes() {
        return reportes;
    }

    public void setReportes(List<ReporteVO> reportes) {
        this.reportes = reportes;
    }

    public List<PlantillaReporteVO> getPlantillas_reportes() {
        return plantillas_reportes;
    }

    public void setPlantillas_reportes(List<PlantillaReporteVO> plantillas_reportes) {
        this.plantillas_reportes = plantillas_reportes;
    }

    public ReportesManager obtenReportesManager() {
        return reportesManager;
    }

    public void setReportesManager(ReportesManager reportesManager) {
        this.reportesManager = reportesManager;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public PlantillaReporteVO getPlantilaReporte() {
        return plantilaReporte;
    }

    public void setPlantilaReporte(PlantillaReporteVO plantilaReporte) {
        this.plantilaReporte = plantilaReporte;
    }

    public int getCdCorporativo() {
        return cdCorporativo;
    }

    public void setCdCorporativo(int cdCorporativo) {
        this.cdCorporativo = cdCorporativo;
    }

    public int getCdAseguradora() {
        return cdAseguradora;
    }

    public void setCdAseguradora(int cdAseguradora) {
        this.cdAseguradora = cdAseguradora;
    }

    public int getCdProducto() {
        return cdProducto;
    }

    public void setCdProducto(int cdProducto) {
        this.cdProducto = cdProducto;
    }

    public String getDsCorporativo() {
        return dsCorporativo;
    }

    public void setDsCorporativo(String dsCorporativo) {
        this.dsCorporativo = dsCorporativo;
    }

    public String getDsAseguradora() {
        return dsAseguradora;
    }

    public void setDsAseguradora(String dsAseguradora) {
        this.dsAseguradora = dsAseguradora;
    }

    public String getDsProducto() {
        return dsProducto;
    }

    public void setDsProducto(String dsProducto) {
        this.dsProducto = dsProducto;
    }

    public String getDsPlantilla() {
        return dsPlantilla;
    }

    public void setDsPlantilla(String dsPlantilla) {
        this.dsPlantilla = dsPlantilla;
    }

    public String getDsReporte() {
        return dsReporte;
    }

    public void setDsReporte(String dsReporte) {
        this.dsReporte = dsReporte;
    }

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

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public ExportModel getExportModel() {
        return exportModel;
    }

    public void setExportModel(ExportModel exportModel) {
        this.exportModel = exportModel;
    }

    public ExportMediator getExportMediator() {
        return exportMediator;
    }

    public void setExportMediator(ExportMediator exportMediator) {
        this.exportMediator = exportMediator;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String obtenerAsociarPlantillasJson() throws ApplicationException {
        int fila = 0;
        int registros = 0;
        log.debug("Entro obtenerAsociarPlantillas");
        log.debug("dsplantilla-----" + dsPlantilla);
        log.debug("dsReporte-----" + dsReporte);
        log.debug("dsAseguradora-----" + dsAseguradora);
        log.debug("dsCorporativo-----" + dsCorporativo);
        log.debug("dsProducto-----" + dsProducto);
        log.debug("start-----" + start);
        log.debug("limit-----" + limit);

        if (limit == start) limit = start + limit;


        inputp.setDsAseguradora(dsAseguradora);
        inputp.setDsCorporativo(dsCorporativo);
        inputp.setDsPlantilla(dsPlantilla);
        inputp.setDsProducto(dsProducto);
        inputp.setDsReporte(dsReporte);

        List<PlantillaReporteVO> plantillas_reportesAux = new ArrayList<PlantillaReporteVO>();
        PlantillaReporteVO obto = null;

        try {
            //plantillas_reportes = reportesManager.getPlantillaReporte(input);
            plantillas_reportesAux = reportesManager.getPlantillaReporte(inputp);
        } catch (Exception e) {
            log.debug("FALLA-obtenerAsociarPlantillasJson" + e);
            addActionError("ERROR" + e);
            success = false;
            return SUCCESS;

        }
        if (plantillas_reportesAux.size() > 0) {
            fila = start;
            registros = 100;
            while ((registros > 0) && (fila < plantillas_reportesAux.size())) {
                obto = new PlantillaReporteVO();
                obto = plantillas_reportesAux.get(fila);
                plantillas_reportes2.add(obto);
                fila++;
                registros--;
            }

            totalCount = plantillas_reportesAux.size();
            session.put("plantillas_reportes2", plantillas_reportes2);
            success = true;
            return SUCCESS;

        } else {
            log.debug("No se encontro datos");
            addActionError("NO SE ENCONTRO DATOS");
            success = false;
            return SUCCESS;

        }
        /*session.put("PLANTILLAS_REPORTES", plantillas_reportes);
        success = true;

        return SUCCESS;*/

    }


    public String asociarPlantillaReporteJson() throws ApplicationException {
        String index;
        String cod = "";

        /**
         *busca los codigos correspondientes a las descripciones e indices que proporciono el usuario
         **/
       //
        /*
        **********
        */

        log.debug("Entro asociarPlantillaReporte");
        log.debug("cdPlantilla-----" + cdPlantilla);
        log.debug("cdAseguradora-----" + cdAseguradora);
        log.debug("cdCorporativo-----" + cdCorporativo);
        log.debug("cdProducto-----" + cdProducto);
        log.debug("cdReporte-----" + cdReporte);
        StringTokenizer  codigoPlantilla = new StringTokenizer (cdPlantilla,",");
        cdPlantilla=codigoPlantilla.nextToken();

        log.debug("cdPlantilla-----" + cdPlantilla);

        plantilaReporte = new PlantillaReporteVO();
        plantilaReporte.setCdPlantilla(Integer.parseInt(cdPlantilla));
        plantilaReporte.setCdProducto(cdProducto);
        plantilaReporte.setCdAseguradora(cdAseguradora);
        plantilaReporte.setCdCorporativo(cdCorporativo);
        plantilaReporte.setCdReporte(cdReporte);

        reportesManager.asociarPlantillaReporte(plantilaReporte);
        success = true;

        return SUCCESS;
    }


    public String editarAsociarPlantillaReporteJson() throws ApplicationException {


        log.debug("Entro editarAsociarPlantillaReporte");
        log.debug("cdPlantilla-----" + cdPlantilla);
        log.debug("cdAseguradora-----" + cdAseguradora);
        log.debug("cdCorporativo-----" + cdCorporativo);
        log.debug("cdProducto-----" + cdProducto);
        log.debug("cdReporte-----" + cdReporte);


        /**
         *busca los codigos correspondientes a las descripciones e indices que proporciono el usuario
         **/

        List<PlantillaReporteVO> aux_plantillas_reportes;
        aux_plantillas_reportes = (List<PlantillaReporteVO>) session.get("plantillas_reportes2");
        PlantillaReporteVO plantillaViejaCod = buscarCodigosPlantilla(Integer.parseInt(cdPlantilla), aux_plantillas_reportes );

        /*
        **********
        */
             // En el editar necesito conservar los valores antiguos para hacer las comparciones en el PL
        log.debug("cdAseguradora anterior-----" + plantillaViejaCod.getCdProductoAnterior());
        log.debug("cdCorporativo anterior-----" + plantillaViejaCod.getCdAseguradoraAnterior());
        log.debug("cdProducto anterior-----" + plantillaViejaCod.getCdCorporativoAnterior());
        log.debug("cdReporte anterior-----" + plantillaViejaCod.getCdReporteAnterior());

         // Valida si se deja los mismos campos en el editar
        if (cdProducto==0)
                cdProducto=plantillaViejaCod.getCdProductoAnterior();
        if (cdAseguradora==0)
                cdProducto=plantillaViejaCod.getCdAseguradoraAnterior();
        if (cdCorporativo==0)
                cdProducto=plantillaViejaCod.getCdCorporativoAnterior();
        if (cdReporte==0)
                cdProducto=plantillaViejaCod.getCdReporteAnterior();

         // Codigos de las asociacion plantilla/reporte NUEVOS
        plantilaReporte = new PlantillaReporteVO();
        plantilaReporte.setCdPlantilla(Integer.parseInt(cdPlantilla));
        plantilaReporte.setCdProducto(cdProducto);
        plantilaReporte.setCdAseguradora(cdAseguradora);
        plantilaReporte.setCdCorporativo(cdCorporativo);
        plantilaReporte.setCdReporte(cdReporte);

        // Codigos de las asociacion plantilla/reporte ANTERIOR
        plantilaReporte.setCdPlantillaAnterior(Integer.parseInt(cdPlantilla));
        plantilaReporte.setCdProductoAnterior(plantillaViejaCod.getCdProductoAnterior());
        plantilaReporte.setCdAseguradoraAnterior(plantillaViejaCod.getCdAseguradoraAnterior());
        plantilaReporte.setCdCorporativoAnterior(plantillaViejaCod.getCdCorporativoAnterior());
        plantilaReporte.setCdReporteAnterior(plantillaViejaCod.getCdReporteAnterior());

        reportesManager.editarAsociarPlantillaReporte(plantilaReporte);
        success = true;

        return SUCCESS;
    }


    public String borrarAsociarPlantillaReporteJson() throws ApplicationException {


        log.debug("Entro borrarAsociarPlantillaReporte");
        log.debug("cdPlantilla-----" + cdPlantilla);


        List<PlantillaReporteVO> aux_plantillas_reportes;
        aux_plantillas_reportes = (List<PlantillaReporteVO>) session.get("plantillas_reportes2");
        PlantillaReporteVO plantillaBorrarCod = buscarCodigosPlantilla(Integer.parseInt(cdPlantilla), aux_plantillas_reportes );

        log.debug("cdAseguradora-----" + plantillaBorrarCod.getCdAseguradoraAnterior());
        log.debug("cdCorporativo-----" + plantillaBorrarCod.getCdCorporativoAnterior());
        log.debug("cdProducto-----" + plantillaBorrarCod.getCdProductoAnterior());
        log.debug("cdReporte-----" + plantillaBorrarCod.getCdReporteAnterior());

        plantilaReporte = new PlantillaReporteVO();
        plantilaReporte.setCdPlantilla(Integer.parseInt(cdPlantilla));
        plantilaReporte.setCdProducto(plantillaBorrarCod.getCdProductoAnterior());
        plantilaReporte.setCdAseguradora(plantillaBorrarCod.getCdAseguradoraAnterior());
        plantilaReporte.setCdCorporativo(plantillaBorrarCod.getCdCorporativoAnterior());
        plantilaReporte.setCdReporte(plantillaBorrarCod.getCdReporteAnterior());



        reportesManager.borrarAsociarPlantillaReporte(plantilaReporte);
        success = true;

        return SUCCESS;
    }

    /*
    **busca el codigo de la platnilla que se encuentra en sesion para obtener los codigos viejos de la asociacion
    */
    

    private PlantillaReporteVO buscarCodigosPlantilla(int codPlantilla, List<PlantillaReporteVO> lista_plantillas_reportes) {
        PlantillaReporteVO plantillaCod = new PlantillaReporteVO();

        for (int i = 0; i < lista_plantillas_reportes.size(); i++) {


            if (lista_plantillas_reportes.get(i).getCdPlantilla() == codPlantilla) {
                plantillaCod = new PlantillaReporteVO();
                plantillaCod.setCdAseguradoraAnterior(lista_plantillas_reportes.get(i).getCdAseguradora());
                plantillaCod.setCdCorporativoAnterior(lista_plantillas_reportes.get(i).getCdCorporativo());
                plantillaCod.setCdProductoAnterior(lista_plantillas_reportes.get(i).getCdProducto());
                plantillaCod.setCdReporteAnterior(lista_plantillas_reportes.get(i).getCdReporte());
                break;
            }
        }

        return plantillaCod;
    }



       /*
    *busca el codigo por indice en la lista de PlantillaReportes
    */

    public PlantillaVO obtenerCodigoPlantilla(int index, List<PlantillaVO> plantillas) {
        //busca en la lista que fue obtenida del la variable de sesion el codigo correspondiente
        PlantillaVO aux;
        aux = plantillas.get(index);
        return aux;
    }

    public ReporteVO obtenerCodigoReporte(int index, List<ReporteVO> reportes) {
        //busca en la lista que fue obtenida del la variable de sesion el codigo correspondiente
        ReporteVO aux;
        aux = reportes.get(index);
        return aux;
    }

    public String obtenerListaCorporativoJson() throws ApplicationException {

        log.debug("Entro btenerListaCorporativo");
        log.debug("dscorporativo-----" + dsCorporativo);

        plantillas_reportes = reportesManager.getListaCorporativo("");
        session.put("LISTA_CORPORATIVO", plantillas_reportes);
        success = true;

        return SUCCESS;
    }

    public String obtenerListaPlantillasJson() throws ApplicationException {

        log.debug("Entro obtenerListaPlantillas");

        plantillas_reportes = reportesManager.getListaPlantilla("");
        session.put("LISTA_PLANTILLA", plantillas_reportes);
        success = true;

        return SUCCESS;
    }

    public String obtenerListaAseguradoraJson() throws ApplicationException {

        log.debug("Entro obtenerListaAseguradora");

        plantillas_reportes = reportesManager.getListaAseguradora("");
        session.put("LISTA_ASEGURADORA", plantillas_reportes);
        success = true;

        return SUCCESS;
    }


    public String obtenerListaProductoJson() throws ApplicationException {

        log.debug("Entro obtenerListaProducto");

        plantillas_reportes = reportesManager.getListaProducto("");
        session.put("LISTA_PRODUCTO", plantillas_reportes);
        success = true;

        return SUCCESS;
    }


    public String obtenerListaReporteJson() throws ApplicationException {

        log.debug("Entro obtenerListaReporte");

        reportes = reportesManager.getListaReporte("");
        session.put("LISTA_REPORTE", reportes);
        success = true;

        return SUCCESS;
    }

    public String exportPlantillaAsociar() throws ApplicationException {

        PlantillaReporteVO input = new PlantillaReporteVO();
        input.setDsAseguradora(dsAseguradora);
        input.setDsCorporativo(dsCorporativo);
        input.setDsPlantilla(dsPlantilla);
        input.setDsProducto(dsProducto);
        input.setDsReporte(dsReporte);

        if (log.isDebugEnabled()) {
            log.debug("Formato:" + formato);
        }
        try {
            ExportView exportFormat = (ExportView) exportMediator.getView(formato);
            filename = descripcion + "." + exportFormat.getExtension();
            TableModelExport model = reportesManager.getModelPlantillasAsociar(input);
            inputStream = exportFormat.export(model);
        } catch (Exception e) {
            log.error("Error al generar documento", e);
        }
        return SUCCESS;
    }

    public List<PlantillaReporteVO> getPlantillas_reportes2() {
        return plantillas_reportes2;
    }

    public void setPlantillas_reportes2(List<PlantillaReporteVO> plantillas_reportes2) {
        this.plantillas_reportes2 = plantillas_reportes2;
    }

    public PlantillaReporteVO getInputp() {
        return inputp;
    }

    public void setInputp(PlantillaReporteVO inputp) {
        this.inputp = inputp;
    }
}
