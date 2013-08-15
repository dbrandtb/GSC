package mx.com.aon.portal.web.reportes;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.reporte.PlantillaVO;
import mx.com.aon.portal.model.reporte.PlantillaReporteVO;
import mx.com.aon.portal.model.MensajeErrorVO;
import mx.com.aon.portal.service.reportes.ReportesManager;
import mx.com.aon.portal.service.reportes.ReportesAgregarManager;
import mx.com.aon.portal.service.MensajesErrorManager;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.ExportModel;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.model.TableModelExport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by IntelliJ IDEA.
 * User: jorge
 * Date: 24/02/2009
 * Time: 01:43:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReportesPlantillasAction extends ActionSupport implements SessionAware {
    private static final transient Log log = LogFactory.getLog(ReportesPrincipalAction.class);
    private ReportesManager reportesManager;

    private Map session;
    private boolean success;
    private String cdPlantilla;
    private String dsPlantilla;
    private List<PlantillaVO> plantillasAux;
    private PlantillaVO plantillaVo;
    private String status;
    private String formato;
    private InputStream inputStream;
    private String filename;
    private ExportModel exportModel;
    private ExportMediator exportMediator;
    private String descripcion;
    private int totalCount;
    private int limit;
    private int start;
    private String validarAux;
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

    public String getValidarAux() {
            return validarAux;
        }

        public void setValidarAux(String validarAux) {
            this.validarAux = validarAux;
        }

    List <PlantillaVO> plantillas = new ArrayList<PlantillaVO>();

    public List<PlantillaVO> getPlantillasAux() {
        return plantillasAux;
    }

    public void setPlantillasAux(List<PlantillaVO> plantillasAux) {
        this.plantillasAux = plantillasAux;
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

    public String getDsPlantilla() {
        return dsPlantilla;
    }

    public void setDsPlantilla(String dsPlantilla) {
        this.dsPlantilla = dsPlantilla;
    }

    public List<PlantillaVO> getPlantillas() {
        return plantillas;
    }

    public void setPlantillas(List<PlantillaVO> plantillas) {
        this.plantillas = plantillas;
    }

    public PlantillaVO getPlantillaVo() {
        return plantillaVo;
    }

    public void setPlantillaVo(PlantillaVO plantillaVo) {
        this.plantillaVo = plantillaVo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String obtenerPlantillasJson() throws ApplicationException {
        //System.out.print("dsPlantilla = "+ dsPlantilla);
//        int pagi = 100;
        int fila = start;
        PlantillaVO plantiObj=null;
        if(dsPlantilla==null)
            dsPlantilla="";
        log.debug("Entro obtenerPlantillas");
        log.debug("dsplantilla-----" + dsPlantilla);
        try{
            plantillasAux = reportesManager.getPlantillas(dsPlantilla);
            totalCount = plantillasAux.size();
            while((limit>0)&&(fila<totalCount)){
              plantiObj = new PlantillaVO();
              plantiObj = plantillasAux.get(fila);
              plantillas.add(plantiObj);
              limit--;
              fila ++;
              start = fila;
            }
        }catch(Exception e){
            success = false;
            log.debug("ERROR-->dsplantilla-----" + e);
            addActionError(e.getMessage());
            return SUCCESS;

        }
        if(plantillas.size()<1){
            success = false;
            log.debug("ReportsPlantillasjson-->obtenerPlantillasJson-->.NO SE ENCONTRO DATA");
            addActionError("No se encontro datos");
            return SUCCESS;

        }
        session.put("PLANTILLAS", plantillas);
        success = true;

        return SUCCESS;
    }


    public String insertarPlantillasJson() throws ApplicationException {

        log.debug("insertarPlantillas");
        log.debug("Cod-----" + cdPlantilla);
        log.debug("descripcion-----" + dsPlantilla);
        log.debug("Status-----" + status);

        plantillaVo = new PlantillaVO();
        plantillaVo.setDsPlantilla(dsPlantilla);
        plantillaVo.setStatus(status);

        reportesManager.insertarPlantilla(plantillaVo);
        success = true;

        return SUCCESS;
    }



    public String  borrarPlantillasJson() throws ApplicationException {

        log.debug("Entro Borrar Plantilla");
        log.debug("cdPlantilla "+cdPlantilla);

        plantillaVo = new PlantillaVO();
        plantillaVo.setCdPlantilla(cdPlantilla);
        String validar=reportesManager.borrarPlantilla(cdPlantilla);
        msgError= mensajesErrorManager.getMensajeError(validar);
        wrongMsg=msgError.getMsgText();
        if(validar.equals("200012"))success=true;
        else
            success = false;
        return SUCCESS;
    }

    public String editarPlantillasJson() throws ApplicationException {
        log.debug("Entro editarPlantillas");
        log.debug("Cod-----" + cdPlantilla);
        log.debug("descripcion-----" + dsPlantilla);
        log.debug("Status-----" + status);

        plantillaVo = new PlantillaVO();
        plantillaVo.setCdPlantilla(cdPlantilla);
        plantillaVo.setDsPlantilla(dsPlantilla);
        plantillaVo.setStatus(status);

        reportesManager.editarPlantilla(plantillaVo);
        success=true;

        return SUCCESS;
    }

    public String exportPlantilla()throws ApplicationException {

        if(log.isDebugEnabled()){
            log.debug("Formato:" + formato);
        }
        try{
            ExportView exportFormat = (ExportView)exportMediator.getView(formato);
            filename = descripcion+"."+ exportFormat.getExtension();
            TableModelExport model = reportesManager.getModelPlantillas(dsPlantilla);
            inputStream = exportFormat.export(model);
        }catch (Exception e) {
            log.error("Error al generar documento", e);
        }
        return SUCCESS;
    }




}
