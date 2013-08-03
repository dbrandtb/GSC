package mx.com.aon.portal.web.reportes;

import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportModel;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.reporte.AtributosVO;
import mx.com.aon.portal.model.reporte.ComboGraficoVo;
import mx.com.aon.portal.model.MensajeErrorVO;
import mx.com.aon.portal.service.reportes.ReportesManager;
import mx.com.aon.portal.service.MensajesErrorManager;
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
 * Time: 01:26:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReportesProductosAction extends ActionSupport implements SessionAware {

    private static final transient Log log = LogFactory.getLog(ReportesPrincipalAction.class);
    private ReportesManager reportesManager;
    private MensajesErrorManager mensajesErrorManager;
    private int cdReporte;
    private String cdPlantilla;
    private String dsProducto;
    private String codProducto;

    public MensajesErrorManager getMensajesErrorManager() {
    return mensajesErrorManager;
}

    public void setMensajesErrorManager(MensajesErrorManager mensajesErrorManager) {
        this.mensajesErrorManager = mensajesErrorManager;
    }

    public String getWrongMsg() {
        return wrongMsg;
    }

    public void setWrongMsg(String wrongMsg) {
        this.wrongMsg = wrongMsg;
    }

    private String formato;
    private InputStream inputStream;
    private String filename;
    private ExportModel exportModel;
    private ExportMediator exportMediator;
    private String descripcion;
    private List<ComboGraficoVo> comboProductos;
    private List<AtributosVO> atributosAux;

    private Map session;
    private boolean success;
    private String validarAux;
    private int limit;
    private int start;
    private int totalCount;

    List<AtributosVO> atributos = new ArrayList<AtributosVO>();

    private MensajeErrorVO msgError;
    private String wrongMsg;

    public MensajeErrorVO getMsgError() {
        return msgError;
    }

    public void setMsgError(MensajeErrorVO msgError) {
        this.msgError = msgError;
    }

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

    public String getValidarAux() {
        return validarAux;
    }

    public void setValidarAux(String validarAux) {
        this.validarAux = validarAux;
    }

    public ReportesManager getReportesManager() {
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

    public String getDsProducto() {
        return dsProducto;
    }

    public void setDsProducto(String dsProducto) {
        this.dsProducto = dsProducto;
    }

    public String getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(String codProducto) {
        this.codProducto = codProducto;
    }

    public Map getSession() {
        return session;
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

    public List<ComboGraficoVo> getComboProductos() {
        return comboProductos;
    }

    public void setComboProductos(List<ComboGraficoVo> comboProductos) {
        this.comboProductos = comboProductos;
    }

    public List<AtributosVO> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<AtributosVO> atributos) {
        this.atributos = atributos;
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

    public String obtenerProductosJson() throws ApplicationException {

        log.debug("Entro obtener  Productos");
        if (limit == 0)
            limit = 50;

        int fila = start;
        AtributosVO atributosObj = null;
        try {
            atributosAux = reportesManager.getProductos(cdReporte);
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
            log.debug(" ERROR-->obtenerProductosEjecutarJson");
            success = false;

        }

        success = true;

        return SUCCESS;
    }

    public String obtenerProductosPlantillasJson() throws ApplicationException {

        log.debug("Entro obtener  Productos Plantillas");
        if (limit == 0)
            limit = 50;

        int fila = start;
        AtributosVO atributosObj = null;
        try {
            atributosAux = reportesManager.getProductosPlantillas(cdPlantilla);
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
            log.debug(" ERROR-->obtenerProductosPlantillasEjecutarJson");
            success = false;

        }

        success = true;

        return SUCCESS;
    }

    public String obtenerComboProductoJson() throws ApplicationException {

        log.debug("Entro   Combo Productos Reporte");
        comboProductos = reportesManager.getComboReportesProductos();
        success = true;

        return SUCCESS;
    }

    public String obtenerComboProductoPlantillasJson() throws ApplicationException {

        log.debug("Entro   Combo Productos Reporte plantillas");
        comboProductos = reportesManager.getComboReportesProductosPlantillas();
        success = true;

        return SUCCESS;
    }


    public String insertarProductoJson() throws ApplicationException {

        log.debug("Cod-----" + codProducto);
        log.debug("Cod-----" + cdReporte);

        String validar = reportesManager.insertarProducto(cdReporte, codProducto);

        if (validar.equals("0")) success = false;
        else
            success = true;

        return SUCCESS;
    }

    public String insertarProductoPlantillasJson() throws ApplicationException {

        log.debug("Cod-----" + codProducto);
        log.debug("Cod-----" + cdPlantilla);
        String validar = reportesManager.insertarProductoPlantillas(cdPlantilla, codProducto);
        msgError= mensajesErrorManager.getMensajeError(validar);
        wrongMsg=msgError.getMsgText();
        if (validar.equals("200010")) success = true;
        else
            success = false;

        return SUCCESS;
    }

    static boolean isNumeric(String cad) {
           try {
               int num = Integer.parseInt(cad);
               return true;
           } catch (NumberFormatException nfe) {
               return false;
           }
       }

    public String editarProductoJson() throws ApplicationException {



        log.debug("Cod-----" + codProducto);
        log.debug("Cod-----" + dsProducto); // Prodcuto  nuevo
        log.debug("Cod-----" + cdReporte);

        if (!isNumeric(dsProducto)){
             dsProducto =codProducto;
        }

        String validar = reportesManager.editarProducto(cdReporte, dsProducto, codProducto);
        msgError= mensajesErrorManager.getMensajeError(validar);
        wrongMsg=msgError.getMsgText();

        if (validar.equals("200011")) success = true;
        else
            success = false;

        return SUCCESS;
    }

    public String editarProductoPlantillasJson() throws ApplicationException {

        log.debug("Cod-----" + codProducto); //prod viejo
        log.debug("Cod-----" + dsProducto); // Prodcuto  nuevo
        log.debug("Cod-----" + cdPlantilla);

        String validar = reportesManager.editarProductoPlantillas(cdPlantilla, dsProducto, codProducto);
        msgError= mensajesErrorManager.getMensajeError(validar);
        wrongMsg=msgError.getMsgText();

        if (validar.equals("200011")) success = true;
        else
            success = false;

        return SUCCESS;

    }


    public String borrarProductoJson() throws ApplicationException {

        log.debug("Cod-----" + codProducto);
        log.debug("Cod-----" + cdReporte);
        reportesManager.borrarProducto(cdReporte, codProducto);
        success = true;

        return SUCCESS;
    }

    public String borrarProductoPlantillasJson() throws ApplicationException {

        log.debug("Cod-----" + codProducto);
        log.debug("Cod-----" + cdPlantilla);
        String validar = reportesManager.borrarProductoPlantillas(cdPlantilla, codProducto);
        msgError= mensajesErrorManager.getMensajeError(validar);
        wrongMsg=msgError.getMsgText();
        if (validar.equals("200012")) success = true;
        else
            success = false;

        return SUCCESS;
    }

    public String exportPlanProductos() throws ApplicationException {

        if (log.isDebugEnabled()) {
            log.debug("Formato:" + formato);
        }
        try {
            ExportView exportFormat = (ExportView) exportMediator.getView(formato);
            filename = descripcion + "." + exportFormat.getExtension();
            TableModelExport model = reportesManager.getModelProductos(cdReporte);
            inputStream = exportFormat.export(model);
        } catch (Exception e) {
            log.error("Error al generar documento", e);
        }
        return SUCCESS;
    }

    public String exportPlanProductosPlantillas() throws ApplicationException {

        if (log.isDebugEnabled()) {
            log.debug("Formato:" + formato);
        }
        try {
            ExportView exportFormat = (ExportView) exportMediator.getView(formato);
            filename = descripcion + "." + exportFormat.getExtension();
            TableModelExport model = reportesManager.getModelProductosPlantillas(cdPlantilla);
            inputStream = exportFormat.export(model);
        } catch (Exception e) {
            log.error("Error al generar documento", e);
        }
        return SUCCESS;
    }

    public String getCdPlantilla() {
        return cdPlantilla;
    }

    public void setCdPlantilla(String cdPlantilla) {
        this.cdPlantilla = cdPlantilla;
    }


}
