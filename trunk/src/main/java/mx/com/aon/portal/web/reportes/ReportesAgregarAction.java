package mx.com.aon.portal.web.reportes;

import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.portal.model.MensajeErrorVO;
import mx.com.aon.portal.model.reporte.ComboGraficoVo;
import mx.com.aon.portal.service.MensajesErrorManager;
import mx.com.aon.portal.service.reportes.ReportesAgregarManager;
import mx.com.aon.portal.service.reportes.ReportesManager;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: Edgar Perez
 * Date: 17/07/2009
 * Time: 02:28:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReportesAgregarAction extends ActionSupport {

    private static final transient Log log = LogFactory.getLog(ReportesPrincipalAction.class);
    private String nombreRepo;
    private String ejecutable;
    private ReportesAgregarManager reportesAgregarManager;
    private MensajesErrorManager mensajesErrorManager;
    private boolean success;
    private String cdReporte;
    private String dsReporte;
    private String nmReporte;
    private String editReporte;
    private String codProducto;
    private Map session;
    private String cdPlantillaAux;
    private String cdAtributoAux;
    private MensajeErrorVO msgError;
    private String wrongMsg;
    private List<ComboGraficoVo> combo;
    private ReportesManager reportesManager;

    public String getWrongMsg() {
        return wrongMsg;
    }

    public void setWrongMsg(String wrongMsg) {
        this.wrongMsg = wrongMsg;
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

    public String getCdPlantillaAux() {
        return cdPlantillaAux;
    }

    public void setCdPlantillaAux(String cdPlantillaAux) {
        this.cdPlantillaAux = cdPlantillaAux;
    }

    public String getCdAtributoAux() {
        return cdAtributoAux;
    }

    public void setCdAtributoAux(String cdAtributoAux) {
        this.cdAtributoAux = cdAtributoAux;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public String getEditReporte() {
        return editReporte;
    }

    public void setEditReporte(String editReporte) {
        this.editReporte = editReporte;
    }

    public String getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(String codProducto) {
        this.codProducto = codProducto;
    }

    private String graficoReporte;
    private String cdPlantilla;
    private String dsPlantilla;
    private String status;
    //private String cdPlantilla;
    private String cdAtributo;
    private String dsAtributo;
    private String swFormat;
    private String nmLmin;
    private String nmLmax;
    private String otTabval;
    private String otTabvalAux;
    private String cdExpres;
    private String statusPlantilla;
    private String nmGrafico;
    private String grafico;
    private String pv_msg_id_o;
    private int existe;

    public String getOtTabvalAux() {
        return otTabvalAux;
    }

    public void setOtTabvalAux(String otTabvalAux) {
        this.otTabvalAux = otTabvalAux;
    }

    public int getExiste() {
        return existe;
    }

    public void setExiste(int existe) {
        this.existe = existe;
    }

    public String getPv_msg_id_o() {
        return pv_msg_id_o;
    }

    public void setPv_msg_id_o(String pv_msg_id_o) {
        this.pv_msg_id_o = pv_msg_id_o;
    }

    public String getNmGrafico() {
        return nmGrafico;
    }

    public void setNmGrafico(String nmGrafico) {
        this.nmGrafico = nmGrafico;
    }

    public String getGrafico() {
        return grafico;
    }

    public void setGrafico(String grafico) {
        this.grafico = grafico;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCdReporte() {
        return cdReporte;
    }

    public void setCdReporte(String cdReporte) {
        this.cdReporte = cdReporte;
    }

    public String getGraficoReporte() {
        return graficoReporte;
    }

    public void setGraficoReporte(String graficoReporte) {
        this.graficoReporte = graficoReporte;
    }

    public String getNmReporte() {
        return nmReporte;
    }

    public void setNmReporte(String nmReporte) {
        this.nmReporte = nmReporte;
    }

    public String getDsReporte() {
        return dsReporte;
    }

    public void setDsReporte(String dsReporte) {
        this.dsReporte = dsReporte;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getNombreRepo() {
        return nombreRepo;
    }

    public void setNombreRepo(String nombreRepo) {
        this.nombreRepo = nombreRepo;
    }

    public String getEjecutable() {
        return ejecutable;
    }

    public void setEjecutable(String ejecutable) {
        this.ejecutable = ejecutable;
    }

    public String getStatusPlantilla() {
        return statusPlantilla;
    }

    public void setStatusPlantilla(String statusPlantilla) {
        this.statusPlantilla = statusPlantilla;
    }

    public ReportesAgregarManager obtenReportesAgregarManager() {
        return reportesAgregarManager;
    }

    public void setReportesAgregarManager(ReportesAgregarManager reportesAgregarManager) {
        this.reportesAgregarManager = reportesAgregarManager;
    }

    /*
    Administracion de Reportes
     */

    public String agregarReporteJson() throws ApplicationException {

        log.debug("Entro a la lista Json");
        log.debug("nombreRepo-----" + nombreRepo);
        log.debug("ejecutable---------" + ejecutable);

        String validar = reportesAgregarManager.insertaReporte(nombreRepo, ejecutable);
        msgError = mensajesErrorManager.getMensajeError(validar);
        wrongMsg = msgError.getMsgText();
        if (validar.equals("200010")) success = true;
        else
            success = false;
        return SUCCESS;

    }

    public String editarReportesJson() throws ApplicationException {

        log.debug("Entro Editar Reporte");
        log.debug("cdRepeorte-----" + cdReporte);
        log.debug("dsReporte---------" + dsReporte);
        log.debug("nmReporte---------" + editReporte);
        log.debug("nmReporte---------" + graficoReporte);


        try {
            String validar = reportesAgregarManager.editarReporte(cdReporte, dsReporte, editReporte);
            if (validar.equals("1"))
                success = true;
            else
                success = false;
            addActionMessage(validar);
            return SUCCESS;
        } catch (ApplicationException ae) {
            success = false;
            addActionError(ae.getMessage());
            return SUCCESS;
        } catch (Exception e) {
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
    }

    public String borrarReportesJson() throws ApplicationException {
        /* System.out.print("nombreRepo = "+ nombreRepo);
    System.out.print("ejecutable = "+ dsReporte); */
        log.debug("Entro Editar Reporte");
        log.debug("cdRepeorte-----" + cdReporte);

        try {
            String msg = reportesAgregarManager.borrarReportes(cdReporte);
            addActionMessage(msg);
            if (existe == 1)
                success = true;
            else
                success = false;
        } catch (ApplicationException ae) {
            success = false;
            addActionError(ae.getMessage());
        } catch (Exception e) {
            success = false;
            addActionError(e.getMessage());
        }
        return SUCCESS;
    }


    public String insertarAtributosJson() throws ApplicationException {

        log.debug("Entro insertarPlantillasAtributos");
        log.debug("Cod-----" + cdReporte);
        log.debug("atributo-----" + dsAtributo);
        log.debug("Formato-----" + swFormat);
        log.debug("Minimo -----" + nmLmin);
        log.debug("Maximo-----" + nmLmax);
        log.debug("Apoya-----" + otTabval);
        log.debug("Cexpress-----" + cdExpres);


        try {
            String msg = reportesAgregarManager.insertarAtributos(cdReporte, dsAtributo,
                    swFormat, nmLmin, nmLmax, otTabval, cdExpres);

            if (msg.equals("0")) success = false;
            else
                success = true;
            addActionMessage(msg);
            return SUCCESS;
        } catch (ApplicationException ae) {
            success = false;
            addActionError(ae.getMessage());
            return SUCCESS;
        } catch (Exception e) {
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
    }

    public String editarAtributosJson() throws ApplicationException {

        log.debug("Entro insertarPlantillasAtributos");
        log.debug("Cod-----" + cdReporte);
        log.debug("Cod-----" + cdAtributo);
        log.debug("atributo-----" + dsAtributo);
        log.debug("Formato-----" + swFormat);
        log.debug("Minimo -----" + nmLmin);
        log.debug("Maximo-----" + nmLmax);
        log.debug("Apoya-----" + otTabval);
        log.debug("Cexpress-----" + cdExpres);


        try {
            String msg = reportesAgregarManager.editarAtributo(cdReporte, cdAtributo, dsAtributo,
                    swFormat, nmLmin, nmLmax, otTabval, cdExpres);
            addActionMessage(msg);
            if (msg.equals("0")) success = false;
            else
                success = true;
            return SUCCESS;
        } catch (ApplicationException ae) {
            success = false;
            addActionError(ae.getMessage());
            return SUCCESS;
        } catch (Exception e) {
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
    }


    /*
    Fin Administracion de Reportes
     */

    /**
     * *********************************************************************************
     */

    /*
    Adminstracion de Plantillas
     */
    public String insertarPlantillasJson() throws ApplicationException {

        log.debug("insertarPlantillas");
        log.debug("Cod-----" + cdPlantilla);
        log.debug("descripcion-----" + dsPlantilla);
        log.debug("Status-----" + status);

        String desc = dsPlantilla.trim();
        String validar = reportesAgregarManager.insertarPlantilla(cdPlantilla, desc, status);
        msgError = mensajesErrorManager.getMensajeError(validar);
        wrongMsg = msgError.getMsgText();
        if (validar.equals("200010")) success = true;
        else
            success = false;
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


        String validar = reportesAgregarManager.insertarPlantillasAtributos(cdPlantilla, dsAtributo,
                swFormat, nmLmin, nmLmax, otTabval);
        if (validar.equals("1")) {
            msgError = mensajesErrorManager.getMensajeError("200010");
            wrongMsg = msgError.getMsgText();
        } else if (validar.equals("0")) {
            msgError = mensajesErrorManager.getMensajeError("100021");
            wrongMsg = msgError.getMsgText();
        } else {
            msgError = mensajesErrorManager.getMensajeError(validar);
            wrongMsg = msgError.getMsgText();
        }
        if (validar.equals("200010") || validar.equals("1")) success = true;
        else
            success = false;


        return SUCCESS;

    }


    public String editarPlantillasJson() throws ApplicationException {
        log.debug("Entro editarPlantillas");
        log.debug("Cod-----" + cdPlantilla);
        log.debug("descripcion-----" + dsPlantilla);
        log.debug("Status-----" + statusPlantilla);


        try {
            String validar = reportesAgregarManager.editarPlantilla(cdPlantilla, dsPlantilla, statusPlantilla);
            msgError = mensajesErrorManager.getMensajeError(validar);
            wrongMsg = msgError.getMsgText();
            if (validar.equals("200011")) success = true;
            else
                success = false;

        } catch (ApplicationException ae) {
            success = false;
            addActionError(ae.getMessage());
        } catch (Exception e) {
            success = false;
            addActionError(e.getMessage());
        }
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
        log.debug("Apoya-----" + otTabvalAux);
        if (!otTabvalAux.equals("")) {
            StringTokenizer tokens = new StringTokenizer(otTabvalAux, " ");
            otTabvalAux = tokens.nextToken();
        }

        try {
            String validar = reportesAgregarManager.editarPlantillasAtributos(cdPlantillaAux, cdAtributoAux, dsAtributo, swFormat, nmLmin, nmLmax, otTabvalAux);
            msgError = mensajesErrorManager.getMensajeError(validar);
            wrongMsg = msgError.getMsgText();
            if (validar.equals("200011")) success = true;
            else
                success = false;

        } catch (ApplicationException ae) {
            success = false;
            addActionError(ae.getMessage());
        } catch (Exception e) {
            success = false;
            addActionError(e.getMessage());
        }
        return SUCCESS;

    }

    /*
    Fin de Administracion Plantillas
     */


    /**
     * ***** Inicio metodos de agragar y editar graficos modulo reportesPrincipal ***********
     */
    public String agregarGraficoJson() throws ApplicationException {
        /* System.out.print("nombreRepo = "+ nombreRepo);
    System.out.print("ejecutable = "+ ejecutable); */
        log.debug("Entro a la lista Json");
        log.debug("cdReporte-----" + cdReporte);
        log.debug("nombreGrafico---------" + nmGrafico);
        log.debug("Tipo---------" + grafico);

        try {
            String msg = reportesAgregarManager.agregarGrafico(cdReporte, nmGrafico, grafico);
            if (msg.equals("200010")) success = true;
            else
                success = false;

            return SUCCESS;
            //if(pv_msg_id_o.equals("200010"))

            /*else
          success = false;*/
        } catch (ApplicationException ae) {
            success = false;
            addActionError(ae.getMessage());
        } catch (Exception e) {
            success = false;
            addActionError(e.getMessage());
        }
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

    public String editarGraficoJson() throws ApplicationException {
        /* System.out.print("nombreRepo = "+ nombreRepo);
    System.out.print("ejecutable = "+ ejecutable); */
        log.debug("Entro a la lista Json");
        log.debug("cdReporte-----" + cdReporte);
        log.debug("nombreGrafico---------" + nmGrafico);
        log.debug("Tipo---------" + grafico);

        if (!isNumeric(grafico)) {
//            int i = 0;  TODO REVISAR, QUEDA PENDIENTE SI GRAFICO ES STRING, CONSEGUIR EL CODIGO DEL TIPO DE GRAFICO
//            combo = reportesManager.getComboGrafico("TTIPOGRAF");
//            while (!grafico.equals(combo.get(i).getDescripC()))
//                i++;
//            grafico = combo.get(i).getCodigo();
        }

        try {
            String msg = reportesAgregarManager.editarGrafico(cdReporte, nmGrafico, grafico);
            if (msg.equals("200010")) success = true;
            else
                success = false;

            return SUCCESS;
        } catch (ApplicationException ae) {
            success = false;
            addActionError(ae.getMessage());
            return SUCCESS;
        } catch (Exception e) {
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
    }


    public String borrarGraficoJson() throws ApplicationException {
        /* System.out.print("nombreRepo = "+ nombreRepo);
    System.out.print("ejecutable = "+ ejecutable); */
        log.debug("Entro a la lista Json");
        log.debug("cdReporte-----" + cdReporte);
        log.debug("nmGrafico---------" + nmGrafico);


        String msg = reportesAgregarManager.borrarGrafico("" + cdReporte, nmGrafico);
        if (msg.equals("200010")) success = true;
        else
            success = false;

        return SUCCESS;
    }

    /**
     * ***** Fin metodos de agragar ,borrar y editar graficos modulo reportesPrincipal ***********
     */
    public String insertarProductoJson() throws ApplicationException {

        log.debug("Cod-----" + codProducto);
        log.debug("Cod-----" + cdReporte);

        String validar = reportesAgregarManager.insertarProducto(cdReporte, codProducto);

        msgError = mensajesErrorManager.getMensajeError(validar);
        wrongMsg = msgError.getMsgText();
        if (validar.equals("200010")) success = true;
        else
            success = false;


        return SUCCESS;
    }
}
