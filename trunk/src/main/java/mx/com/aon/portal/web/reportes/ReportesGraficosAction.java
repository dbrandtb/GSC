package mx.com.aon.portal.web.reportes;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import mx.com.aon.portal.service.reportes.ReportesManager;
import mx.com.aon.portal.model.reporte.GraficoVO;
import mx.com.aon.portal.model.reporte.ComboGraficoVo;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.ExportModel;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.model.TableModelExport;
import mx.com.gseguros.exception.ApplicationException;

import java.util.Map;
import java.util.List;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: jorge
 * Date: 24/02/2009
 * Time: 12:45:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReportesGraficosAction extends ActionSupport implements SessionAware {

    private static final transient Log log = LogFactory.getLog(ReportesGeneradorAction.class);
    private ReportesManager reportesManager;
    private int cdReporte;
    private String grafico;
    private String nmGrafico;
    private String graficoReporte;
    private String descripcion;
    private String formato;
    private InputStream inputStream;
    private String filename;
    private ExportModel exportModel;
    private ExportMediator exportMediator;
    private Map session;
    private boolean success;
    private List<ComboGraficoVo> combo;

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

    public String getGrafico() {
        return grafico;
    }

    public void setGrafico(String grafico) {
        this.grafico = grafico;
    }

    public String getNmGrafico() {
        return nmGrafico;
    }

    public void setNmGrafico(String nmGrafico) {
        this.nmGrafico = nmGrafico;
    }

    public String getGraficoReporte() {
        return graficoReporte;
    }

    public void setGraficoReporte(String graficoReporte) {
        this.graficoReporte = graficoReporte;
    }

    public List<ComboGraficoVo> getCombo() {
        return combo;
    }

    public void setCombo(List<ComboGraficoVo> combo) {
        this.combo = combo;
    }

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



    public String comboGraficoReporte()throws ApplicationException {

           log.debug("Entro   Combo Grafico Reporte");

           combo=reportesManager.getComboGrafico("TTIPOGRAF");
           success = true;

           return SUCCESS;
       }

    public String agregarGraficoJson() throws ApplicationException {
        /* System.out.print("nombreRepo = "+ nombreRepo);
    System.out.print("ejecutable = "+ ejecutable); */
        log.debug("Entro a la lista Json");
        log.debug("cdReporte-----" + cdReporte);
        log.debug("nmGrafico---------" + nmGrafico);
        log.debug("Tipo---------" + grafico);

        reportesManager.insertarGrafico(""+cdReporte,nmGrafico,grafico);
        success = true;
        return SUCCESS;
    }

    public String editarGraficoJson() throws ApplicationException {
        /* System.out.print("nombreRepo = "+ nombreRepo);
    System.out.print("ejecutable = "+ ejecutable); */
        log.debug("Entro a la lista Json");
        log.debug("cdReporte-----" + cdReporte);
        log.debug("nmGrafico---------" + graficoReporte);
        log.debug("Tipo---------" + descripcion);

        reportesManager.editarGrafico(""+cdReporte,graficoReporte,grafico);
        success = true;
        return SUCCESS;
    }


    //********************************************************************

    public String borrarGraficoJson() throws ApplicationException {
        /* System.out.print("nombreRepo = "+ nombreRepo);
    System.out.print("ejecutable = "+ ejecutable); */
        log.debug("Entro a la lista Json");
        log.debug("cdReporte-----" + cdReporte);
        log.debug("nmGrafico---------" + nmGrafico);


        reportesManager.borrarGrafico(""+cdReporte,nmGrafico);
        success = true;
        return SUCCESS;
    }

    //********************************************************************

     public String exportPlanGrafico()throws ApplicationException {

        if(log.isDebugEnabled()){
            log.debug("Formato:" + formato);
        }
        try{
            ExportView exportFormat = (ExportView)exportMediator.getView(formato);
            filename = descripcion+"."+ exportFormat.getExtension();
            TableModelExport model = reportesManager.getModelGraficos(cdReporte);
            inputStream = exportFormat.export(model);
        }catch (Exception e) {
            log.error("Error al generar documento", e);
        }
        return SUCCESS;
    }
}
