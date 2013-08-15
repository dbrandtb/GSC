package mx.com.aon.portal.web.reportes;

import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.model.reporte.ComboGraficoVo;
import mx.com.aon.portal.model.reporte.GraficoVO;
import mx.com.aon.portal.model.reporte.ReporteVO;
import mx.com.aon.portal.model.reporte.AtributosVO;
import mx.com.aon.portal.service.reportes.ReportesManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.SessionAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jorge
 * Date: 23/02/2009
 * Time: 11:42:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReportesGeneradorAction extends ActionSupport implements SessionAware {

    private static final transient Log log = LogFactory.getLog(ReportesGeneradorAction.class);
    private ReportesManager reportesManager;
    private ReporteVO errores;
    private List<ReporteVO> reportesAux;
    private List<ComboGraficoVo> combo;
    private List<ComboGraficoVo> comboProductos;
    private List<ComboGraficoVo> comboCuenta;
    private List<GraficoVO> graficoTablaAux;
    private String CDRAMO;
    private String dsReporte;
    private String grafico;
    private int cdReporte;
    private Map session;
    private boolean success;
    private int cuenta;
    private int limit;
    private int start;
    private int totalCount;
    private int totalCountAtributos;
    List<GraficoVO> graficoTabla = new ArrayList<GraficoVO>();
    List<ReporteVO> reportes = new ArrayList<ReporteVO>();
    List<AtributosVO> atributos = new ArrayList<AtributosVO>();

    public int getTotalCountAtributos() {
        return totalCountAtributos;
    }

    public void setTotalCountAtributos(int totalCountAtributos) {
        this.totalCountAtributos = totalCountAtributos;
    }

    public List<AtributosVO> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<AtributosVO> atributos) {
        this.atributos = atributos;
    }

    public List<GraficoVO> getGraficoTablaAux() {
        return graficoTablaAux;
    }

    public void setGraficoTablaAux(List<GraficoVO> graficoTablaAux) {
        this.graficoTablaAux = graficoTablaAux;
    }

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

    public ReportesManager obtenReportesManager() {
        return reportesManager;
    }

    public void setReportesManager(ReportesManager reportesManager) {
        this.reportesManager = reportesManager;
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

    public List<ComboGraficoVo> getComboProductos() {
        return comboProductos;
    }

    public void setComboProductos(List<ComboGraficoVo> comboProductos) {
        this.comboProductos = comboProductos;
    }

    public List<ComboGraficoVo> getComboCuenta() {
        return comboCuenta;
    }

    public void setComboCuenta(List<ComboGraficoVo> comboCuenta) {
        this.comboCuenta = comboCuenta;
    }

    public List<GraficoVO> getGraficoTabla() {
        return graficoTabla;
    }

    public void setGraficoTabla(List<GraficoVO> graficoTabla) {
        this.graficoTabla = graficoTabla;
    }

    public String getCDRAMO() {
        return CDRAMO;
    }

    public void setCDRAMO(String CDRAMO) {
        this.CDRAMO = CDRAMO;
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

    public String getGrafico() {
        return grafico;
    }

    public void setGrafico(String grafico) {
        this.grafico = grafico;
    }

    public ReporteVO getErrores() {
        return errores;
    }

    public void setErrores(ReporteVO errores) {
        this.errores = errores;
    }

    public String obtenerReporteEjecutarJson() throws ApplicationException {
        // System.out.print("dsreporte = "+ dsReporte);
        if (limit == 0)
            limit = 50;

        int fila = start;
        ReporteVO reporteObj = null;
        log.debug("Entro a la lista Json");
        log.debug("dsreporte-----" + dsReporte);
            //TODO SE REALIZARON ESTAS MODIFICACIONES SEGUN EL DT VERSION 11   
//        UserVO prueba = new UserVO();  //deberia capturarlo de ssesion
//        String cdRamo = (String) session.get("CDRAMO");
//        log.debug("CDRAMO en Obtenerejecutarreportes-----" + cdRamo);

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
              //TODO SE REALIZARON ESTAS MODIFICACIONES SEGUN EL DT VERSION 11
//            reportesAux = reportesManager.getReportesEjecutar(dsReporte, cdRamo); // CDRAMO 2 ES LA PRUEBA
//            //reportes = reportesManager.getReportesEjecutar(dsReporte,cdRamo); // CDRAMO 2 ES LA PRUEBA
//            totalCount = reportesAux.size();
//            while ((limit > 0) && (fila < totalCount)) {
//                reporteObj = new ReporteVO();
//                reporteObj = reportesAux.get(fila);
//                reportes.add(reporteObj);
//                limit--;
//                fila++;
//                start = fila;
            }


        } catch (Exception e) {
            log.debug(" ERROR-->obtenerReporteEjecutarJson");
            cuenta = 0;
            success = false;


        }
        if (reportes.size() < 1)
            success = false;

        session.put("REPORTES", reportes);
        success = true;

        return SUCCESS;
    }

    public String validarGraficoJson() throws ApplicationException {

        log.debug("Entro a la lista Json");
        log.debug("cdReporte-----" + cdReporte);
        totalCountAtributos=0;
        atributos = new ArrayList<AtributosVO>();
        atributos = reportesManager.getAtributos(cdReporte);
        totalCountAtributos = atributos.size();
        reportes = new ArrayList<ReporteVO>();
        errores = reportesManager.validarGrafico("" + cdReporte);
        reportes.add(errores);
        success = true;
        return SUCCESS;
    }

    public String comboAseguradora() throws ApplicationException {

        log.debug("Entro   Combo Aseguradora Reporte");
        UserVO user = (UserVO) session.get("USUARIO");        //Obtengo usuario de session
        String cdRamo = (String) session.get("CDRAMO");

        log.debug("El CDRAMO -----" + CDRAMO);

        combo = reportesManager.getComboAseguradora(user.getEmpresa().getElementoId(), cdRamo);   //Priemro cdElemento segundo cdRamo se sacan de userVO
        // combo=reportesManager.getComboAseguradora("3004","2");   //Priemro cdElemento segundo cdRamo se sacan de userVO

        success = true;

        return SUCCESS;
    }

    public String comboProductos() throws ApplicationException {

        log.debug("Entro   Combo Productos Reporte");
        log.debug("id aseguradora-----" + dsReporte);
        UserVO user = (UserVO) session.get("USUARIO");        //Obtengo usuario de session
        String cdRamo = (String) session.get("CDRAMO");
        log.debug("El CDRAMO -----" + CDRAMO);

        //   comboProductos=reportesManager.getComboProductos(""+cdReporte,"3004","2");
        comboProductos = reportesManager.getComboProductos("" + cdReporte, user.getEmpresa().getElementoId(), cdRamo);
        success = true;

        return SUCCESS;
    }

    public String comboCuenta() throws ApplicationException {

        log.debug("Entro   Combo Productos Reporte");
        log.debug("id aseguradora-----" + cdReporte);
        log.debug("id producto-----" + grafico);
        UserVO user = (UserVO) session.get("USUARIO");        //Obtengo usuario de session
        String cdRamo = (String) session.get("CDRAMO");

        log.debug("El CDRAMO -----" + CDRAMO);

        comboCuenta = reportesManager.getComboCuenta("" + cdReporte, user.getEmpresa().getElementoId(), cdRamo);

        //  comboCuenta=reportesManager.getComboCuenta(""+cdReporte,"3004","500");
        success = true;

        return SUCCESS;
    }

    public String obtenerGraficoReporte() throws ApplicationException {

        log.debug("Entro   Combo Grafico Reporte");
        log.debug("cdRepeorte-----" + cdReporte);
        int fila = start;
        GraficoVO graficoObj = null;
        if (limit == 0)
            limit = 20;
        graficoTablaAux = reportesManager.getGrafico("" + cdReporte);
//            for(int i=0;i<graficoTabla.size();i++){
//                graficoTabla.get(i).setCdReporte("1");
//            }
        totalCount = graficoTablaAux.size();
        while ((limit > 0) && (fila < totalCount)) {
            graficoObj = new GraficoVO();
            graficoTablaAux.get(fila).setCdReporte("1");
            graficoObj = graficoTablaAux.get(fila);
            graficoTabla.add(graficoObj);
            limit--;
            fila++;
            start = fila;
        }

        session.put("REPORTESGRAFICO", graficoTabla);
        success = true;

        return SUCCESS;
    }

    public int getCuenta() {
        return cuenta;
    }

    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }
}
