package mx.com.aon.portal.service.impl.reportes;

import mx.com.aon.portal.service.reportes.ReportesAgregarManager;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.core.ApplicationException;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.HashMap;

import com.wittyconsulting.backbone.endpoint.Endpoint;

/**
 * Created by IntelliJ IDEA.
 * User: Edgar Perez
 * Date: 17/07/2009
 * Time: 02:30:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReportesAgregarManagerImpl extends AbstractManager implements ReportesAgregarManager {

    private static Logger logger = Logger.getLogger(ReportesManagerImpl.class);
    //private Map endpoints;

    public void setEndpoints(Map endpoints) {
        this.endpoints = endpoints;
    }


    public String insertaReporte(String varNombreRepo, String varEjecutable) throws ApplicationException {

        WrapperResultados res = new WrapperResultados();
        Map params = new HashMap();
        params.put("dsReporte", varNombreRepo);
        params.put("nmReporte", varEjecutable);

        res = returnBackBoneInvoke(params, "P_INSERTA_MREPORTE");

        return res.getMsgText();
    }

    public String editarReporte(String cdReporte, String dsReporte,
                                String nmReporte) throws ApplicationException {

        WrapperResultados res = new WrapperResultados();
        Map map = new HashMap();
        map.put("cdReporte", cdReporte);
        map.put("dsReporte", dsReporte);
        map.put("nmReporte", nmReporte);


        res = returnBackBoneInvoke(map, "P_EDITA_MREPORTE");

        return res.getResultado();
    }

    public String borrarReportes(String cdReporte) throws ApplicationException {

        WrapperResultados res = new WrapperResultados();
        Map map = new HashMap();
        map.put("cdReporte", cdReporte);


        res = returnBackBoneInvoke(map, "P_BORRA_MREPORTE");

        return res.getResultado();
    }

    public String insertarAtributos(String cdReporte, String dsAtributo,
                                    String swFormat, String nmLmin, String nmLmax,
                                    String otTabval, String cdExpres) throws ApplicationException {

        WrapperResultados res = new WrapperResultados();
        Map map = new HashMap();
        map.put("cdReporte", cdReporte);
        map.put("dsAtributo", dsAtributo);
        map.put("formato", swFormat);
        map.put("minimo", nmLmin);
        map.put("maximo", nmLmax);
        map.put("apoyo", otTabval);
        map.put("cdExpres", cdExpres);


        res = returnBackBoneInvoke(map, "P_INSERTA_TATRIREP");

        return res.getResultado();
    }

    public String editarAtributo(String cdReporte, String cdAtributo,String dsAtributo,
                                 String swFormat, String nmLmin, String nmLmax,
                                 String otTabval, String cdExpres) throws ApplicationException {
        WrapperResultados res = new WrapperResultados();


        Map map = new HashMap<String, String>();

        map.put("cdReporte", cdReporte);
        map.put("cdAtributo", cdAtributo);
        map.put("dsAtributo", dsAtributo);
        map.put("formato", swFormat);
        map.put("minimo", nmLmin);
        map.put("maximo", nmLmax);
        map.put("apoyo", otTabval);
        map.put("cdExpres", cdExpres);


        res = returnBackBoneInvoke(map, "P_EDITA_ATRIBUTO");

        return res.getResultado();
    }


    public String insertarPlantilla(String cdPlantilla, String dsPlantilla,
                                    String status) throws ApplicationException {

        WrapperResultados res = new WrapperResultados();
        Map map = new HashMap();
        map.put("cdPlantilla", 0);
        map.put("dsPlantilla", dsPlantilla);
        if (status.equals("SI")) {
            map.put("status", 1);
        } else {
            map.put("status", 0);
        }
        map.put("insertar", 1);

        res = returnBackBoneInvoke(map, "P_GUARDA_MPLANTIL");

        return res.getMsg();

    }

    public String insertarPlantillasAtributos(String cdPlantilla, String dsAtributo,
                                              String swFormat, String nmLmin, String nmLmax,
                                              String otTabval) throws ApplicationException {

        WrapperResultados res = new WrapperResultados();
        Map map = new HashMap();
        map.put("cdPlantilla", cdPlantilla);
        map.put("dsAtributo", dsAtributo);
        map.put("formato", swFormat);
        map.put("minimo", nmLmin);
        map.put("maximo", nmLmax);
        map.put("apoyo", otTabval);


        res = returnBackBoneInvoke(map, "P_INSERTA_TATRIPLA");

        logger.debug("retorno de datos de P_INSERTA_TATRIPLA");
        logger.debug("Resultado-----" + res.getResultado());
        logger.debug("msgText-----" + res.getMsgText());
        logger.debug("msg-----" + res.getMsg());

        if( res.getMsg() != null){
            if(!res.getMsg().equals("")){
                return res.getMsg();
            }else
                 return res.getResultado();
        }else
           return res.getResultado();

    }

    public String borrarPlantillas(String cdPlantilla) throws ApplicationException {

        WrapperResultados res = new WrapperResultados();
        Map map = new HashMap();
        map.put("cdPlantilla", cdPlantilla);


        res = returnBackBoneInvoke(map, "P_BORRA_MPLANTIL");

        return res.getResultado();

    }

    public String editarPlantilla(String cdPlantilla, String dsPlantilla,
                                  String status) throws ApplicationException {

        WrapperResultados res = new WrapperResultados();
        Map map = new HashMap();
        map.put("cdPlantilla", cdPlantilla);
        map.put("dsPlantilla", dsPlantilla);
        if (status.equals("SI")) {
            map.put("status", 1);
        } else {
            map.put("status", 0);
        }
        map.put("insertar", 0);

        res = returnBackBoneInvoke(map, "P_GUARDA_MPLANTIL");

       return res.getMsg();

    }

    public String editarPlantillasAtributos(String cdPlantilla, String cdAtributo, String dsAtributo,
                                            String swFormat, String nmLmin, String nmLmax,
                                            String otTabval) throws ApplicationException {

        WrapperResultados res = new WrapperResultados();
        Map map = new HashMap();
        map.put("cdPlantilla", cdPlantilla);
        map.put("cdAtributo", cdAtributo);
        map.put("dsAtributo", dsAtributo);
        map.put("formato", swFormat);
        map.put("minimo", nmLmin);
        map.put("maximo", nmLmax);
        map.put("apoyo", otTabval);


        res = returnBackBoneInvoke(map, "P_GUARDA_TATRIPLA");

        return res.getMsg();
    }

    public String agregarGrafico(String cdReporte, String nmGrafico, String grafico) throws ApplicationException {

        WrapperResultados res = new WrapperResultados();
        Map map = new HashMap();
        map.put("cdReporte", cdReporte);
        map.put("nombreGrafico", nmGrafico);
        map.put("tipo", grafico);


        res = returnBackBoneInvoke(map, "P_INSERTA_GRAFICO");

        return res.getMsg();
    }

    public String editarGrafico(String cdReporte, String nmGrafico, String grafico) throws ApplicationException {

        WrapperResultados res = new WrapperResultados();
        Map map = new HashMap();
        map.put("cdReporte", cdReporte);
        map.put("nombreGrafico", nmGrafico);
        map.put("tipo", grafico);


        res = returnBackBoneInvoke(map, "P_EDITA_GRAFICO");

        return res.getMsg();
    }


    public String borrarGrafico (String cdReporte,String nombreGrafico) throws ApplicationException {
        /* System.out.print("nombreRepo implemente = "+ reporte.getDsReporte());
 System.out.print("NmReporte  implemente = "+ reporte.getNmReporte());  */
        WrapperResultados res = new WrapperResultados();
        Map map = new HashMap<String, String>();
        map.put("cdReporte", cdReporte);
        map.put("nombreGrafico", nombreGrafico);


        res = returnBackBoneInvoke(map, "P_EDITA_GRAFICO");

        return res.getMsg();



    }

    public String insertarProducto(String cdReporte,String codProducto) throws ApplicationException {
        
        Map params = new HashMap<String, String>();
        //en el caso de insertar no importa el codigo ya que es gemerado por un secuencia en la BD
        WrapperResultados res = new WrapperResultados();
        params.put("cdReporte", cdReporte);
        params.put("cdProducto", codProducto);


        res = returnBackBoneInvoke(params, "P_INSERTAR_PRODUCTO");

        return res.getMsg();
    }

}
