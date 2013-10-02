package mx.com.aon.portal.web.reportes;

import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportModel;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.reporte.AtributosVO;
import mx.com.aon.portal.model.reporte.ComboGraficoVo;
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

/**
 * Created by IntelliJ IDEA.
 * User: jorge
 * Date: 24/02/2009
 * Time: 01:13:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReportesAtributosAction extends ActionSupport implements SessionAware {

    private static final transient Log log = LogFactory.getLog(ReportesPrincipalAction.class);
    private ReportesManager reportesManager;
    private int cdReporte;
    private AtributosVO atributoVo;
    private String cdAtributo;
    private String dsAtributo;
    private String swFormat;
    private String nmLmin;
    private String nmLmax;
    private String otTabval;
    private String cdExpres;
    private ReporteVO errores;
    private List<AtributosVO> atributosAux;
    private List<ComboGraficoVo> combo;
    private String formato;
    private InputStream inputStream;
    private String filename;
    private ExportModel exportModel;
    private ExportMediator exportMediator;
    private String descripcion;
    private Map session;
    private boolean success;
    private int limit;
    private int start;
    private int totalCount;

    List<AtributosVO> atributos = new ArrayList<AtributosVO>();

    public List<AtributosVO> getAtributosAux() {
        return atributosAux;
    }

    public void setAtributosAux(List<AtributosVO> atributosAux) {
        this.atributosAux = atributosAux;
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

    public AtributosVO getAtributoVo() {
        return atributoVo;
    }

    public void setAtributoVo(AtributosVO atributoVo) {
        this.atributoVo = atributoVo;
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

    public String getCdExpres() {
        return cdExpres;
    }

    public void setCdExpres(String cdExpres) {
        this.cdExpres = cdExpres;
    }

    public ReporteVO getErrores() {
        return errores;
    }

    public void setErrores(ReporteVO errores) {
        this.errores = errores;
    }

    public List<AtributosVO> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<AtributosVO> atributos) {
        this.atributos = atributos;
    }

    public List<ComboGraficoVo> getCombo() {
        return combo;
    }

    public void setCombo(List<ComboGraficoVo> combo) {
        this.combo = combo;
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

    public String obtenerAtributosJson() throws ApplicationException {
        // System.out.print("dsreporte = "+ dsReporte);
        log.debug("Entro a la lista Json");
        log.debug("dsreporte-----" + cdReporte);
        if (limit == 0)
            limit = 50;

        int fila = start;
        AtributosVO atributosObj = null;
        try {
            atributosAux = reportesManager.getAtributos(cdReporte);
            totalCount = atributosAux.size();
            while ((limit > 0) && (fila < totalCount)) {
                atributosObj = new AtributosVO();
                atributosObj = atributosAux.get(fila);
                atributos.add(atributosObj);
                limit--;
                fila++;
                start = fila;
            }
        } catch (Exception e) {
            log.debug(" ERROR-->obtenerAtributosJson");
            success = false;

        }

        success = true;

        return SUCCESS;
    }

    public String comboFormatoReporte() throws ApplicationException {

        log.debug("Entro   Combo Grafico Reporte");

        combo = reportesManager.getComboGrafico("TFORMATO");
        success = true;

        return SUCCESS;
    }


    public String insertarAtributosJson() throws ApplicationException {
        /* System.out.print("nombreRepo = "+ nombreRepo);
    System.out.print("ejecutable = "+ ejecutable); */
        log.debug("Entro a la lista Json");
        log.debug("Cod-----" + cdReporte);
        log.debug("atributo-----" + dsAtributo);
        log.debug("Formato-----" + swFormat);
        log.debug("Minimo -----" + nmLmin);
        log.debug("Maximo-----" + nmLmax);
        log.debug("Apoya-----" + otTabval);
        log.debug("Cexpress-----" + cdExpres);


        atributoVo = new AtributosVO();
        atributoVo.setCdReporte(cdReporte);
        atributoVo.setDsAtributo(dsAtributo);
        atributoVo.setSwFormat(swFormat);
        atributoVo.setNmLmin(nmLmin);
        atributoVo.setNmLmax(nmLmax);
        atributoVo.setOtTabval(otTabval);
        atributoVo.setCdExpres(cdExpres);

        reportesManager.insertaAtributo(atributoVo);
        success = true;

        return SUCCESS;
    }


    public String borrarAtributosJson() throws ApplicationException {
        /* System.out.print("nombreRepo = "+ nombreRepo);
    System.out.print("ejecutable = "+ dsReporte); */
        log.debug("Entro Editar Reporte");
        log.debug("cdRepeorte-----" + cdReporte);
        log.debug("cdAtributo-----" + cdAtributo);
        atributoVo = new AtributosVO();
        atributoVo.setCdReporte(cdReporte);
        atributoVo.setCdAtributo(cdAtributo);

        reportesManager.borrarAtributo(atributoVo);
        success = true;

        return SUCCESS;
    }


    public String editarAtributosJson() throws ApplicationException {
        /* System.out.print("nombreRepo = "+ nombreRepo);
    System.out.print("ejecutable = "+ ejecutable); */
        log.debug("Entro a la lista Json");
        log.debug("Cod-----" + cdReporte);
        log.debug("atributo-----" + cdAtributo);
        log.debug("dsatributo-----" + dsAtributo);
        log.debug("Formato-----" + swFormat);
        log.debug("Minimo -----" + nmLmin);
        log.debug("Maximo-----" + nmLmax);
        log.debug("Apoya-----" + otTabval);
        log.debug("Cexpress-----" + cdExpres);


        atributoVo = new AtributosVO();
        atributoVo.setCdReporte(cdReporte);
        atributoVo.setCdAtributo(cdAtributo);
        atributoVo.setDsAtributo(dsAtributo);
        atributoVo.setSwFormat(swFormat);
        atributoVo.setNmLmin(nmLmin);
        atributoVo.setNmLmax(nmLmax);
        atributoVo.setOtTabval(otTabval);
        atributoVo.setCdExpres(cdExpres);

        reportesManager.editarAtributo(atributoVo);
        success = true;

        return SUCCESS;
    }

    public String exportarPrincipalAtributos() throws ApplicationException {

        if (log.isDebugEnabled()) {
            log.debug("Formato:" + formato);
        }
        try {
            ExportView exportFormat = (ExportView) exportMediator.getView(formato);
            filename = descripcion + "." + exportFormat.getExtension();
            TableModelExport model = reportesManager.getModelAtributos("" + cdReporte);
            inputStream = exportFormat.export(model);
        } catch (Exception e) {
            log.error("Error al generar documento", e);
        }
        return SUCCESS;
    }

}
