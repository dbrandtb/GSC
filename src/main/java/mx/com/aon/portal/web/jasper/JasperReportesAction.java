/**
 * Created by IntelliJ IDEA.
 * User: Oscar Parales
 * Date: 03-jul-2008
 * Time: 16:16:52
 * To change this template use File | Settings | File Templates.
 */

package mx.com.aon.portal.web.jasper;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.reportes.ReportesManager;
import mx.com.aon.portal.model.reporte.JasperVO;
import mx.com.aon.portal.model.UserVO;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;





public class JasperReportesAction extends ActionSupport  implements SessionAware, ServletContextAware, ServletRequestAware {       //

    private static final long serialVersionUID = -6654001282882848525L;
    private static final transient Log log = LogFactory.getLog(JasperReportesAction.class);
    private ReportesManager reportesManager; //  Para Traer valores de los PL
    private List<JasperVO> reportesList;
    JasperVO jasperVO;
    UserVO userVO;
    private javax.sql.DataSource dataSource;
    //   private EstructuraDAO estructura;
    private String dsReporte;
    private HttpServletRequest httpServletRequest;
    private  ServletContext servletContext;
    private Map parametros ;
    private boolean success;
    private Map session;


    ///pruebas

    private String asegurador;
    private String producto;
    private String cliente;
    private String fechIni;
    private String fechFin;
    private String cdReporte;
    private String descAsegurador;
    private String descProducto;
    private String descCliente;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /*public EstructuraDAO getEstructura() {
        return estructura;
    }

    public void setEstructura(EstructuraDAO estructura) {
        this.estructura = estructura;
    }*/

    public String getCdReporte() {
        return cdReporte;
    }

    public void setCdReporte(String cdReporte) {
        this.cdReporte = cdReporte;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFechIni() {
        return fechIni;
    }

    public void setFechIni(String fechIni) {
        this.fechIni = fechIni;
    }

    public String getFechFin() {
        return fechFin;
    }

    public void setFechFin(String fechFin) {
        this.fechFin = fechFin;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getAsegurador() {
        return asegurador;
    }

    public void setAsegurador(String asegurador) {
        this.asegurador = asegurador;
    }

    public String getDescAsegurador() {
        return descAsegurador;
    }

    public void setDescAsegurador(String descAsegurador) {
        this.descAsegurador = descAsegurador;
    }

    public String getDescProducto() {
        return descProducto;
    }

    public void setDescProducto(String descProducto) {
        this.descProducto = descProducto;
    }

    public String getDescCliente() {
        return descCliente;
    }

    public void setDescCliente(String descCliente) {
        this.descCliente = descCliente;
    }


    public List<JasperVO> getReportesList() {
        return reportesList;
    }

    public void setReportesList(List<JasperVO> reportes) {
        this.reportesList = reportes;
    }

    public ReportesManager getReportesManager() {
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


    public Map getParametros() {
        return parametros;
    }

    public void setParametros(Map parametros) {
        this.parametros = parametros;
    }


    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;//To change body of implemented methods use File | Settings | File Templates.
    }
    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest= httpServletRequest;//To change body of implemented methods use File | Settings | File Templates.
    }

    public String getDsReporte() {
        return dsReporte;
    }

    public void setDsReporte(String dsReporte) {
        this.dsReporte = dsReporte;
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

    public String jasperReportes() throws ApplicationException{

        return SUCCESS;
    }

    public String reportesPrincipal() throws ApplicationException{

        return SUCCESS;
    }

    public String reportesGenerador() throws ApplicationException{

        return SUCCESS;
    }

    public String reportesAdministracionAtributos() throws ApplicationException{

        return SUCCESS;
    }

    public String reportesAdministracionPlantillas() throws ApplicationException{

        return SUCCESS;
    }

    /*
    public String reportesAsociarPlantillas() throws ApplicationException{

        return SUCCESS;
    }*/

    public String reportesAsociarPlantillasAtributos() throws ApplicationException{

        return SUCCESS;
    }


    public String generadorJasper() throws ApplicationException {

        UserVO user = (UserVO) session.get("USUARIO");

        log.debug("aseguradora-----" + asegurador);
        log.debug("producto-----" + producto);
        log.debug("Fecha Ini-----" + fechIni);
        log.debug("Fecha fin-----" + fechFin);
        log.debug("Descripcion Aseguradora-----" + descAsegurador);
        log.debug("Descripcion Producto-----" + descProducto);
        log.debug("Descripcion Cliente-----" + descCliente);

        if(asegurador.equals(""))asegurador="0";
        if(producto.equals(""))producto="0";
        if(cliente.equals(""))cliente="0";
        if(fechIni.equals(""))fechIni="0";
        if(fechFin.equals(""))fechFin="0";

        if(dsReporte.equalsIgnoreCase("CART0001")){
         reportesManager.cargarCART001(cdReporte,user.getEmpresa().getElementoId(),fechIni,fechFin);
        }


        parametros = new HashMap();

       // System.out.println("aaaaaaaaaaaaaaaaaa"+user.getUser());

        parametros.put("CDUSUARIO", user.getUser());
        parametros.put("wCDUSUARIO", user.getUser());
        parametros.put("OTVALOR01", Integer.parseInt(asegurador));
        parametros.put("OTVALOR02",Integer.parseInt(producto));
        parametros.put("OTVALOR03",Integer.parseInt(cliente));
        parametros.put("OTVALOR04",fechIni);
        parametros.put("OTVALOR05",fechFin);
        parametros.put("OTVALOR06",descAsegurador);
        parametros.put("OTVALOR07",descProducto);
        parametros.put("OTVALOR08",descCliente);
        parametros.put("wCDREPORTE",Integer.parseInt(cdReporte));

        String location = "/jasper/"+dsReporte+".jasper";
        httpServletRequest.setAttribute("location",location);
        httpServletRequest.setAttribute("parametros",parametros);
        httpServletRequest.setAttribute("dataSource",getDataSource());

        return SUCCESS;
    }

    /*public DriverManagerDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }*/

    //******************************Reportes con PL********************************

    /* public String sinr0019Jasper() throws ApplicationException {

    jasperVO = new JasperVO();  // esto lo debo llenar con el valor de los ortvalores
    jasperVO.setOtValor01(30);
    reportesList = reportesManager.getJasperValores(jasperVO);
    *//*session.put("REPORTES", reportesList);
        //success = true;

         parametros = new HashMap();

        parametros.put("usuario","pepe");
        parametros.put("OTVALOR01", 30);
        parametros.put("OTVALOR02",115);
        parametros.put("wCDREPORTE",3);

        httpServletRequest.setAttribute("parametros",parametros);
        httpServletRequest.setAttribute("dataSource",getDataSource());*//*

        return SUCCESS;

    }*/
}