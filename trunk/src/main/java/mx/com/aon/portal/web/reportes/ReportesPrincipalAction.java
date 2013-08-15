package mx.com.aon.portal.web.reportes;


import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportModel;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.reporte.ComboGraficoVo;
import mx.com.aon.portal.model.reporte.ReporteVO;
import mx.com.aon.portal.service.reportes.ReportesManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.SessionAware;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Cesar
 * Date: 12-jun-2008
 * Time: 16:28:54
 * To change this template use File | Settings | File Templates.
 */
public class ReportesPrincipalAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = -6654001282882848522L;
    private static final transient Log log = LogFactory.getLog(ReportesPrincipalAction.class);
    private transient ReportesManager reportesManager;

    private Map session;
    private boolean success;
    private int cdReporte;
    private String dsReporte;
    private String nmReporte;
    private String graficoReporte;
    private String descripcion;
    private List<ReporteVO> reportesAux;
    private List<ComboGraficoVo> combo;
    private List<ComboGraficoVo> comboProductos;

    private ReporteVO reporteVo;

    private String nombreRepo;
    private String ejecutable;
    private String CDRAMO;
    private ReporteVO errores;

    private String dsPlantilla;
    private String cdPlantilla;

    private String formato;
    private InputStream inputStream;
    private String filename;
    private transient ExportModel exportModel;
    private transient ExportMediator exportMediator;
    private int limit;
    private int start;
    private int totalCount;

    List<ReporteVO> reportes = new ArrayList<ReporteVO>();
    // Beans

    public List<ReporteVO> getReportesAux() {
        return reportesAux;
    }

    public void setReportesAux(List<ReporteVO> reportesAux) {
        this.reportesAux = reportesAux;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * *********************************************************************
     */


    public String getEjecutable() {
        return ejecutable;
    }

    public void setEjecutable(String ejecutable) {
        this.ejecutable = ejecutable;
    }

    public String getNombreRepo() {
        return nombreRepo;
    }

    public void setNombreRepo(String nombreRepo) {
        this.nombreRepo = nombreRepo;
    }

    public ReporteVO getReporteVo() {
        return reporteVo;
    }

    public void setReporteVo(ReporteVO reporteVo) {
        this.reporteVo = reporteVo;
    }

    public ReportesManager obtenReportesManager() {
        return reportesManager;
    }

    public void setReportesManager(ReportesManager reportesManager) {
        this.reportesManager = reportesManager;
    }

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

    public String getGraficoReporte() {
        return graficoReporte;
    }

    public void setGraficoReporte(String graficoReporte) {
        this.graficoReporte = graficoReporte;
    }

    public List<ReporteVO> getReportes() {
        return reportes;
    }

    public void setReportes(List<ReporteVO> reportes) {
        this.reportes = reportes;
    }

    public List<ComboGraficoVo> getCombo() {
        return combo;
    }

    public void setCombo(List<ComboGraficoVo> combo) {
        this.combo = combo;
    }

    public ReporteVO getErrores() {
        return errores;
    }

    public void setErrores(ReporteVO errores) {
        this.errores = errores;
    }

    public List<ComboGraficoVo> getComboProductos() {
        return comboProductos;
    }

    public void setComboProductos(List<ComboGraficoVo> comboProductos) {
        this.comboProductos = comboProductos;
    }

    public String getDsPlantilla() {
        return dsPlantilla;
    }

    public void setDsPlantilla(String dsPlantilla) {
        this.dsPlantilla = dsPlantilla;
    }

    public String getCdPlantilla() {
        return cdPlantilla;
    }

    public void setCdPlantilla(String cdPlantilla) {
        this.cdPlantilla = cdPlantilla;
    }

    public String input() throws Exception {
        return INPUT;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public String reportesPrincipal() throws ApplicationException {

        return SUCCESS;
    }

    public String reportesGenerador() throws ApplicationException {

        session.put("CDRAMO", CDRAMO);
        return SUCCESS;
    }

    public String reportesAdministracionAtributos() throws ApplicationException {

        return SUCCESS;
    }

    ////////////////////////////////////////////////////////
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getCDRAMO() {
        return CDRAMO;
    }

    public void setCDRAMO(String CDRAMO) {
        this.CDRAMO = CDRAMO;
    }

////////////////////FIN DE BEANS/////////////////////////////////////////////////

    public String obtenerReporteJson() throws ApplicationException {
        // System.out.print("dsreporte = "+ dsReporte);
        log.debug("Entro a la lista Json");
        log.debug("dsreporte-----" + dsReporte);
        if (limit == 0)
            limit = 50;

        int fila = start;
        ReporteVO reporteObj = null;
        try {
            reportesAux = reportesManager.getReportes(dsReporte);
            totalCount = reportesAux.size();
            while ((limit > 0) && (fila < totalCount)) {
                reporteObj = new ReporteVO();
                reporteObj = reportesAux.get(fila);
                reportes.add(reporteObj);
                limit--;
                fila++;
                start = fila;
            }
        } catch (Exception e) {
            log.debug(" ERROR-->obtenerReporteEjecutarJson en Administracion de Reporte");
            success = false;

        }

        session.put("reportes", reportes);
        success = true;

        return SUCCESS;
    }

    public String agregarReporteJson() throws ApplicationException {

        log.debug("Entro a la lista Json");
        log.debug("nombreRepo-----" + nombreRepo);
        log.debug("ejecutable---------" + ejecutable);

        reporteVo = new ReporteVO();
        reporteVo.setDsReporte(nombreRepo);
        reporteVo.setNmReporte(ejecutable);

        errores = reportesManager.insertaReporte(reporteVo);

        if (errores.getCdReporte() == 0)      // Manejo de error si el reporte ya existe
            success = false;
        else
            success = true;
        return SUCCESS;
    }

    public String editarReportesJson() throws ApplicationException {
        /* System.out.print("nombreRepo = "+ nombreRepo);
    System.out.print("ejecutable = "+ dsReporte); */
        log.debug("Entro Editar Reporte");
        log.debug("cdRepeorte-----" + cdReporte);
        log.debug("dsReporte---------" + dsReporte);
        log.debug("nmReporte---------" + nmReporte);
        log.debug("nmReporte---------" + graficoReporte);

        reporteVo = new ReporteVO();
        reporteVo.setCdReporte(cdReporte);
        reporteVo.setDsReporte(dsReporte);
        reporteVo.setNmReporte(nmReporte);
        reportesManager.editarReporte(reporteVo);
        success = true;

        return SUCCESS;
    }

    public String borrarReportesJson() throws ApplicationException {
        /* System.out.print("nombreRepo = "+ nombreRepo);
    System.out.print("ejecutable = "+ dsReporte); */
        log.debug("Entro Editar Reporte");
        log.debug("cdReporte-----" + cdReporte);

        String validar = reportesManager.borrarReporte(cdReporte);
        if (validar.equals("0")) success = false;
        else
            success = true;

        return SUCCESS;
    }

    //********************************************************************


    public String desplegarAtributosJson() throws ApplicationException {

        session.put("dsReporte", dsReporte);
        session.put("cdReporte", cdReporte);
        success = true;
        return SUCCESS;

    }

    public String desplegarProductosJson() throws ApplicationException {

        session.put("dsReporte", dsReporte);
        session.put("cdReporte", cdReporte);
        success = true;
        return SUCCESS;

    }

    public String desplegarProductosPlantillasJson() throws ApplicationException {

        session.put("dsPlantilla", dsPlantilla);
        session.put("cdPlantilla", cdPlantilla);
        success = true;
        return SUCCESS;

    }

    public String desplegarAtributosPlantillasJson() throws ApplicationException {

        session.put("dsReporte", dsReporte);
        session.put("cdReporte", cdReporte);
        success = true;
        return SUCCESS;

    }

    public String desplegarGraficoJson() throws ApplicationException {

        session.put("dsReporte", dsReporte);
        session.put("cdReporte", cdReporte);
        success = true;
        return SUCCESS;

    }


    public String reportesAdministracionPlantillasAtributos() throws ApplicationException {

        session.put("dsPlantilla", dsPlantilla);
        session.put("cdPlantilla", cdPlantilla);
        success = true;
        return SUCCESS;
    }


    public String exportarPrincipal() throws ApplicationException {

        if (log.isDebugEnabled()) {
            log.debug("Formato:" + formato);
        }
        try {
            ExportView exportFormat = (ExportView) exportMediator.getView(formato);
            filename = descripcion + "." + exportFormat.getExtension();
            TableModelExport model = reportesManager.getModelPrincipal(dsReporte);
            inputStream = exportFormat.export(model);
        } catch (Exception e) {
            log.error("Error al generar documento", e);
        }
        //success = true;
        return SUCCESS;
    }

}