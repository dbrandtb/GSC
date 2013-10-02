package mx.com.aon.portal.service.reportes;

import mx.com.gseguros.exception.ApplicationException;

/**
 * Created by IntelliJ IDEA.
 * User: Edgar Perez
 * Date: 17/07/2009
 * Time: 02:29:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ReportesAgregarManager {

    public String insertaReporte (String varNombreRepo, String varEjecutable) throws ApplicationException;

    public String editarReporte (String cdReporte, String dsReporte,
                                 String nmReporte) throws ApplicationException;

    public String borrarReportes(String cdReporte) throws ApplicationException;

    public String insertarAtributos (String cdReporte, String dsAtributo,
                                     String swFormat, String nmLmin, String nmLmax,
                                     String otTabval, String cdExpres) throws ApplicationException;


    public String editarAtributo(String cdReporte, String cdAtributo,String dsAtributo,
                                 String swFormat, String nmLmin, String nmLmax,
                                 String otTabval, String cdExpres) throws ApplicationException;

    public String insertarPlantilla (String cdPlantilla, String dsPlantilla, String status) throws ApplicationException;

    public String insertarPlantillasAtributos (String cdPlantilla, String dsAtributo,
                                               String swFormat, String nmLmin, String nmLmax,
                                               String otTabval) throws ApplicationException;

    public String editarPlantilla (String cdPlantilla, String dsPlantilla, String status) throws ApplicationException;

    public String borrarPlantillas (String cdPlantilla) throws ApplicationException;

    public String editarPlantillasAtributos(String cdPlantilla, String cdAtributo,
                                            String dsAtributo, String swFormat, String nmLmin,
                                            String nmLmax, String otTabval) throws ApplicationException;

    public String agregarGrafico(String cdReporte, String nmGrafico,String grafico) throws ApplicationException;

    public String editarGrafico(String cdReporte, String nmGrafico,String grafico) throws ApplicationException;

    public String borrarGrafico (String cdReporte,String nombreGrafico) throws ApplicationException;

    String insertarProducto (String cdReporte,String codProducto) throws ApplicationException;


}