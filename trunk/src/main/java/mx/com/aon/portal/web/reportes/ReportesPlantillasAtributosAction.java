package mx.com.aon.portal.web.reportes;

import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportModel;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.reporte.AtributosVO;
import mx.com.aon.portal.model.reporte.TablaApoyoVO;
import mx.com.aon.portal.model.MensajeErrorVO;
import mx.com.aon.portal.service.reportes.ReportesManager;
import mx.com.aon.portal.service.reportes.ReportesAgregarManager;
import mx.com.aon.portal.service.MensajesErrorManager;
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
 * Time: 01:57:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReportesPlantillasAtributosAction extends ActionSupport implements SessionAware {


    private static final transient Log log = LogFactory.getLog(ReportesPrincipalAction.class);
    private ReportesManager reportesManager;
    private Map session;
    private boolean success;
    private String cdPlantilla;
    private String dsAtributo;
    private String swFormat;
    private String nmLmin;
    private String nmLmax;
    private String otTabval;
    private String cdExpres;
    private AtributosVO atributoVo;
    private String cdAtributo;
    private List<AtributosVO> atributos;
    private List<TablaApoyoVO> tapoyoAux;
    private String formato;
    private InputStream inputStream;
    private String filename;
    private ExportModel exportModel;
    private ExportMediator exportMediator;
    private String descripcion;
    private String codigoPlantilla;
    private int limit;
    private int start;
    private int totalCount;
    private ReportesAgregarManager reportesAgregarManager;
    private MensajesErrorManager mensajesErrorManager;
    private MensajeErrorVO msgError;
    private String wrongMsg;

    public ReportesAgregarManager obtenReportesAgregarManager() {
        return reportesAgregarManager;
    }

    public void setReportesAgregarManager(ReportesAgregarManager reportesAgregarManager) {
        this.reportesAgregarManager = reportesAgregarManager;
    }

    public MensajesErrorManager obtenMensajesErrorManager() {
        return mensajesErrorManager;
    }

    public void setMensajesErrorManager(MensajesErrorManager mensajesErrorManager) {
        this.mensajesErrorManager = mensajesErrorManager;
    }

    public MensajeErrorVO getMsgError() {
        return msgError;
    }

    public void setMsgError(MensajeErrorVO msgError) {
        this.msgError = msgError;
    }

    public String getWrongMsg() {
        return wrongMsg;
    }

    public void setWrongMsg(String wrongMsg) {
        this.wrongMsg = wrongMsg;
    }

    List<TablaApoyoVO> tapoyo = new ArrayList<TablaApoyoVO>();

    public List<TablaApoyoVO> getTapoyoAux() {
        return tapoyoAux;
    }

    public void setTapoyoAux(List<TablaApoyoVO> tapoyoAux) {
        this.tapoyoAux = tapoyoAux;
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

    public String getCodigoPlantilla() {
        return codigoPlantilla;
    }

    public void setCodigoPlantilla(String codigoPlantilla) {
        this.codigoPlantilla = codigoPlantilla;
    }

    public List<TablaApoyoVO> getTapoyo() {
        return tapoyo;
    }

    public void setTapoyo(List<TablaApoyoVO> tapoyo) {
        this.tapoyo = tapoyo;
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

    public String getCdPlantilla() {
        return cdPlantilla;
    }

    public void setCdPlantilla(String cdPlantilla) {
        this.cdPlantilla = cdPlantilla;
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

    public List<AtributosVO> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<AtributosVO> atributos) {
        this.atributos = atributos;
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

    public String obtenerPlantillasAtributosJson() throws ApplicationException {

        log.debug("Entro btenerPlantillasAtributos");
        log.debug("codigoPlantilla-----" + codigoPlantilla);
        atributos = reportesManager.getPlantillasAtributos(codigoPlantilla);
        for (int i = 0; i < atributos.size(); i++) {
            if (!atributos.get(i).getCdotTabval().equalsIgnoreCase(""))
                atributos.get(i).setOtTabval(atributos.get(i).getCdotTabval() + " - " + atributos.get(i).getOtTabval());
        }
        session.put("ATRIBUTOS", atributos);
        success = true;
        cdPlantilla = null;
        return SUCCESS;
    }

    public String insertarPlantillasAtributosJson() throws ApplicationException {

        log.debug("Entro insertarPlantillasAtributos");
        log.debug("Cod-----" + cdPlantilla);
        log.debug("atributo-----" + dsAtributo);
        log.debug("Formato-----" + swFormat);
        log.debug("Minimo -----" + nmLmin);
        log.debug("Maximo-----" + nmLmax);
        log.debug("Apoya-----" + otTabval);

        atributoVo = new AtributosVO();
        atributoVo.setCdPlantilla(cdPlantilla);
        atributoVo.setDsAtributo(dsAtributo);
        atributoVo.setSwFormat(swFormat);
        atributoVo.setNmLmin(nmLmin);
        atributoVo.setNmLmax(nmLmax);
        atributoVo.setOtTabval(otTabval);

        reportesManager.insertaPlantillaAtributo(atributoVo);
        success = true;

        return SUCCESS;
    }


    public String borrarPlantillasAtributosJson() throws ApplicationException {

        log.debug("Entro borrarPlantillasAtributos");
        log.debug("cdPlantilla-----" + cdPlantilla);
        log.debug("cdAtributo-----" + cdAtributo);
        atributoVo = new AtributosVO();
        atributoVo.setCdPlantilla(cdPlantilla);
        atributoVo.setCdAtributo(cdAtributo);

        String validar=reportesManager.borrarPlantillaAtributo(atributoVo);
        msgError= mensajesErrorManager.getMensajeError(validar);
        wrongMsg=msgError.getMsgText();
        if(validar.equals("200012"))success=true;
        else
            success = false;
        return SUCCESS;
    }


    public String editarPlantillasAtributosJson() throws ApplicationException {
        log.debug("Entro editarPlantillasAtributos");
        log.debug("Cod-----" + cdPlantilla);
        log.debug("atributo-----" + cdAtributo);
        log.debug("dsatributo-----" + dsAtributo);
        log.debug("Formato-----" + swFormat);
        log.debug("Minimo -----" + nmLmin);
        log.debug("Maximo-----" + nmLmax);
        log.debug("Apoya-----" + otTabval);

        atributoVo = new AtributosVO();
        atributoVo.setCdPlantilla(cdPlantilla);
        atributoVo.setCdAtributo(cdAtributo);
        atributoVo.setDsAtributo(dsAtributo);
        atributoVo.setSwFormat(swFormat);
        atributoVo.setNmLmin(nmLmin);
        atributoVo.setNmLmax(nmLmax);
        atributoVo.setOtTabval(otTabval);
        atributoVo.setCdExpres(cdExpres);

        reportesManager.editarPlantillaAtributo(atributoVo);
        success = true;

        return SUCCESS;
    }

    public String exportPlantillaAtributos() throws ApplicationException {

        if (log.isDebugEnabled()) {
            log.debug("Formato:" + formato);
        }
        try {
            ExportView exportFormat = (ExportView) exportMediator.getView(formato);
            filename = descripcion + "." + exportFormat.getExtension();
            TableModelExport model = reportesManager.getModelPlantillasAtributos(cdPlantilla);
            inputStream = exportFormat.export(model);
        } catch (Exception e) {
            log.error("Error al generar documento", e);
        }
        return SUCCESS;
    }

    public String obtenerPlantillasTApoyoJson() throws ApplicationException {

        log.debug("obtenerPlantillasTApoyoJson-->init");
        log.debug("obtenerPlantillasTApoyoJson-->cdPlantilla-----" + cdPlantilla);

        if (limit == 0)
            limit = 50;

        int fila = start;
        TablaApoyoVO tablaObj = null;
        try {
            tapoyoAux = reportesManager.getPlantAtribTablaApoyo(cdPlantilla);
            totalCount = tapoyoAux.size();
            while ((limit > 0) && (fila < totalCount)) {
                tablaObj = new TablaApoyoVO();
                tablaObj = tapoyoAux.get(fila);
                tapoyo.add(tablaObj);
                limit--;
                fila++;
                start = fila;
            }
        } catch (Exception e) {
            log.debug("ERROR->>>obtenerPlantillasTApoyoJson-->cdPlantilla-----" + cdPlantilla);
            success = false;
        }
        session.put("tapoyo", tapoyo);
        success = true;

        return SUCCESS;
    }

}
