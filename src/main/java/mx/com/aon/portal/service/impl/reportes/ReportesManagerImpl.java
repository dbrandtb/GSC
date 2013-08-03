package mx.com.aon.portal.service.impl.reportes;


import static mx.com.aon.portal.dao.ReporteDAO.BUSCAR_REPORTES;
import static mx.com.aon.portal.dao.ReporteDAO.BUSCAR_REPORTES_ADMINISTRACION;
import static mx.com.aon.portal.dao.ReporteDAO.BUSCAR_PLANTILLAS;
import static mx.com.aon.portal.dao.ReporteDAO.COMBO_ASEGURADORA;
import static mx.com.aon.portal.dao.ReporteDAO.COMBO_PRODUCTO;
import static mx.com.aon.portal.dao.ReporteDAO.COMBO_CUENTA;

// TODO cambio hecho porque no compilaba en SVN
//import static mx.com.aon.portal.dao.ReporteDAO.CARGAR_CART0001; 


import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.service.reportes.ReportesManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.model.reporte.*;
import mx.com.aon.catweb.configuracion.producto.expresiones.model.RamaVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;

/**
 * Created by IntelliJ IDEA.
 * User: Cesar
 * Date: 12-jun-2008
 * Time: 16:26:13
 * To change this template use File | Settings | File Templates.
 */

public class ReportesManagerImpl extends AbstractManagerJdbcTemplateInvoke implements ReportesManager {

    private static Logger logger = Logger.getLogger(ReportesManagerImpl.class);
    private Map endpoints;

    public void setEndpoints(Map endpoints) {
        this.endpoints = endpoints;
    }

    public List<ReporteVO> getReportes(String dsReporteB)
            throws ApplicationException {
        Map params = new HashMap();
        params.put("p_vdsreporte", ConvertUtil.nvl(dsReporteB));
        return getAllBackBoneInvoke(params, BUSCAR_REPORTES_ADMINISTRACION);


    }

    public List<ReporteVO> getReportesEjecutar(String dsReporteB,String ramos)
            throws ApplicationException {

        Map params = new HashMap();
        params.put("p_vdsreporte", ConvertUtil.nvl(dsReporteB));
        params.put("pv_cdramo_i", ramos);

        return getAllBackBoneInvoke(params, BUSCAR_REPORTES);

        /*Map params = new HashMap<String, String>();
      params.put("dsReporte", dsReporteB);
      params.put("cdRamo", ramos);
      List<ReporteVO> reportes = null;
      try {
          Endpoint endpoint = (Endpoint) endpoints.get("P_OBTIENE_MREPORTE_EJECUTAR");
          reportes = (ArrayList<ReporteVO>) endpoint.invoke(params);
          logger.debug(".. Reportes : " + reportes);

      } catch (BackboneApplicationException e) {
          logger.error("Backbone exception", e);
          throw new ApplicationException("Error al recuperar los datos");

      }
      return (ArrayList<ReporteVO>) reportes;*/
    }

    public ReporteVO insertaReporte(ReporteVO reporte) throws ApplicationException {
        /* System.out.print("nombreRepo implemente = "+ reporte.getDsReporte());
 System.out.print("NmReporte  implemente = "+ reporte.getNmReporte());  */
        Map params = new HashMap<String, String>();
        ReporteVO captura =new ReporteVO();
        params.put("dsReporte", reporte.getDsReporte());
        params.put("nmReporte", reporte.getNmReporte());
        Endpoint endpoint = (Endpoint) endpoints.get("P_INSERTA_MREPORTE");
        try {
            captura=(ReporteVO)endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando insertar un nuevo reporte");
        }
        return captura;
    }

    public void editarReporte(ReporteVO reporte) throws ApplicationException {
        /* System.out.print("nombreRepo implemente = "+ reporte.getDsReporte());
 System.out.print("NmReporte  implemente = "+ reporte.getNmReporte());  */
        Map params = new HashMap<String, String>();

        params.put("cdReporte", reporte.getCdReporte());
        params.put("dsReporte", reporte.getDsReporte());
        params.put("nmReporte", reporte.getNmReporte());

        Endpoint endpoint = (Endpoint) endpoints.get("P_EDITA_MREPORTE");
        try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando editar reporte");
        }
    }

    public String borrarReporte(int cdReporte) throws ApplicationException {
        /* System.out.print("nombreRepo implemente = "+ reporte.getDsReporte());
 System.out.print("NmReporte  implemente = "+ reporte.getNmReporte());  */
        WrapperResultados res = new WrapperResultados();
        Map params = new HashMap<String, String>();

        params.put("cdReporte", cdReporte);

        Endpoint endpoint = (Endpoint) endpoints.get("P_BORRA_MREPORTE");
        try {
           res= (WrapperResultados)endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando borrar reporte");
        }
          return  res.getResultado();
    }

    public List<GraficoVO> getGrafico(String cdReporte)
            throws ApplicationException {
        Map params = new HashMap<String, String>();
        params.put("cdReporte", cdReporte);
        params.put("tipo", null);
        params.put("cdTabla", "TTIPOGRAF");
        params.put("cdRegion", 1);
        params.put("cdIdioma", 1);

        List<GraficoVO> grafico = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_GRAFICO");
            grafico = (ArrayList<GraficoVO>) endpoint.invoke(params);
            logger.debug("..Grafico : " + grafico);

        } catch (BackboneApplicationException e) {
            logger.error("Backbone exception", e);
            throw new ApplicationException("Error al recuperar los datos");

        }
        return grafico;
    }


    public List<ComboGraficoVo> getComboGrafico(String tipoTabla)
            throws ApplicationException {
        Map params = new HashMap<String, String>();
        params.put("comboGrafico", tipoTabla);

        List<ComboGraficoVo> combo = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_COMBO_GRAFICO");
            combo = (ArrayList<ComboGraficoVo>) endpoint.invoke(params);
            logger.debug(".. Reportes : " + combo);

        } catch (BackboneApplicationException e) {
            logger.error("Backbone exception", e);
            throw new ApplicationException("Error al recuperar los datos");

        }
        return combo;
    }

    public List<ComboGraficoVo> getComboAseguradora(String cdElemento,String cdRamo)
            throws ApplicationException {

        Map params = new HashMap();
        params.put("pv_cdelemento_i", cdElemento);
        params.put("pv_cdramo_i", cdRamo);

        return getAllBackBoneInvoke(params, COMBO_ASEGURADORA);

    }

    public List<ComboGraficoVo> getComboProductos(String idAseguradora,String cdElemento,String cdRamo)
            throws ApplicationException {
        Map params = new HashMap();
        params.put("pv_cdunieco_i", idAseguradora);
        params.put("pv_cdelemento_i", cdElemento);
        params.put("pv_cdramo_i", cdRamo);

        return getAllBackBoneInvoke(params, COMBO_PRODUCTO);

    }

    public List<ComboGraficoVo> getComboCuenta(String idAseguradora,String cdElemento,String cdRamo)
            throws ApplicationException {

        Map params = new HashMap();
        params.put("pv_cdunieco_i", idAseguradora);
        params.put("pv_cdelemento_i", cdElemento);
        params.put("pv_cdramo_i", cdRamo);
        return getAllBackBoneInvoke(params, COMBO_CUENTA);

    }

    public void cargarCART001 (String aseguradora,String producto,String fec_ini,String fec_fin)throws ApplicationException{


        Map params = new HashMap<String, String>();
        params.put("p_ncdreporte_i", aseguradora);
        params.put("p_ncdusuari_i", producto);
        params.put("p_vfedesde_i", fec_ini);
        params.put("p_vfehasta_i", fec_fin);
        Endpoint endpoint = (Endpoint) endpoints.get("P_GUARDAR_REPORTE_AUX");
        try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando insertar un nuevo reporte");
        }

    }

    public void insertarGrafico (String cdReporte,String nombreGrafico,String tipo) throws ApplicationException {
        /* System.out.print("nombreRepo implemente = "+ reporte.getDsReporte());
 System.out.print("NmReporte  implemente = "+ reporte.getNmReporte());  */
        Map params = new HashMap<String, String>();
        ReporteVO captura =new ReporteVO();
        params.put("cdReporte", cdReporte);
        params.put("nombreGrafico", nombreGrafico);
        params.put("tipo", tipo);
        Endpoint endpoint = (Endpoint) endpoints.get("P_INSERTA_GRAFICO");
        try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando insertar un nuevo reporte");
        }

    }

    public void editarGrafico (String cdReporte,String nombreGrafico,String tipo) throws ApplicationException {
        /* System.out.print("nombreRepo implemente = "+ reporte.getDsReporte());
 System.out.print("NmReporte  implemente = "+ reporte.getNmReporte());  */
        Map params = new HashMap<String, String>();
        ReporteVO captura =new ReporteVO();
        params.put("cdReporte", cdReporte);
        params.put("nombreGrafico", nombreGrafico);
        params.put("tipo", tipo);
        Endpoint endpoint = (Endpoint) endpoints.get("P_EDITA_GRAFICO");
        try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando insertar un nuevo reporte");
        }

    }
    public void borrarGrafico (String cdReporte,String nombreGrafico) throws ApplicationException {
        /* System.out.print("nombreRepo implemente = "+ reporte.getDsReporte());
 System.out.print("NmReporte  implemente = "+ reporte.getNmReporte());  */
        Map params = new HashMap<String, String>();
        params.put("cdReporte", cdReporte);
        params.put("nombreGrafico", nombreGrafico);

        Endpoint endpoint = (Endpoint) endpoints.get("P_BORRA_GRAFICO");
        try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando insertar un nuevo reporte");
        }

    }

    public ReporteVO validarGrafico(String reporte) throws ApplicationException {
        /* System.out.print("nombreRepo implemente = "+ reporte.getDsReporte());
 System.out.print("NmReporte  implemente = "+ reporte.getNmReporte());  */
        Map params = new HashMap<String, String>();
        ReporteVO captura =new ReporteVO();
        params.put("cdReporte", reporte);

        Endpoint endpoint = (Endpoint) endpoints.get("P_VALIDAR");
        try {
            captura=(ReporteVO)endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error validando grafico");
        }
        return captura;
    }


    /**
     * Atributos
     */

    public List<AtributosVO> getAtributos(int cdReporte)
            throws ApplicationException {
        Map params = new HashMap<String, String>();
        params.put("cdReporte", cdReporte);

        List<AtributosVO> atributos = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_OBTIENE_ATRIBUTOS");
            atributos= (ArrayList<AtributosVO>) endpoint.invoke(params);
            logger.debug(".. Atributos : " + atributos);

        } catch (BackboneApplicationException e) {
            logger.error("Backbone exception", e);
            throw new ApplicationException("Error al recuperar los datos");

        }
        return atributos ;
    }

    public void insertaAtributo(AtributosVO atributo) throws ApplicationException {
        /* System.out.print("nombreRepo implemente = "+ reporte.getDsReporte());
 System.out.print("NmReporte  implemente = "+ reporte.getNmReporte());  */
        Map params = new HashMap<String, String>();

        params.put("cdReporte", atributo.getCdReporte());
        params.put("dsAtributo", atributo.getDsAtributo());
        params.put("formato", atributo.getSwFormat());
        params.put("minimo", atributo.getNmLmin());
        params.put("maximo", atributo.getNmLmax());
        params.put("apoyo", atributo.getOtTabval());
        params.put("cdExpres", atributo.getCdExpres());


        Endpoint endpoint = (Endpoint) endpoints.get("P_INSERTA_ATRIBUTO");
        try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando insertar un nuevo reporte");
        }
    }


    public void borrarAtributo(AtributosVO atributo) throws ApplicationException {
        /* System.out.print("nombreRepo implemente = "+ reporte.getDsReporte());
 System.out.print("NmReporte  implemente = "+ reporte.getNmReporte());  */
        Map params = new HashMap<String, String>();

        params.put("cdReporte", atributo.getCdReporte());
        params.put("cdAtributo", atributo.getCdAtributo());

        Endpoint endpoint = (Endpoint) endpoints.get("P_BORRA_ATRIBUTO");
        try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando borrar atributo");
        }
    }

    public void editarAtributo(AtributosVO atributo) throws ApplicationException {
        /* System.out.print("nombreRepo implemente = "+ reporte.getDsReporte());
 System.out.print("NmReporte  implemente = "+ reporte.getNmReporte());  */
        Map params = new HashMap<String, String>();

        params.put("cdReporte", atributo.getCdReporte());
        params.put("cdAtributo", atributo.getCdAtributo());
        params.put("dsAtributo", atributo.getDsAtributo());
        params.put("formato", atributo.getSwFormat());
        params.put("minimo", atributo.getNmLmin());
        params.put("maximo", atributo.getNmLmax());
        params.put("apoyo", atributo.getOtTabval());
        params.put("cdExpres", atributo.getCdExpres());


        Endpoint endpoint = (Endpoint) endpoints.get("P_EDITA_ATRIBUTO");
        try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando insertar un nuevo reporte");
        }
    }


    ///  Metodos de Productos
    public List<AtributosVO> getProductos(int cdReporte) throws ApplicationException {
        Map params = new HashMap<String, String>();
        params.put("cdReporte", cdReporte);

        List<AtributosVO> atributo = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_OBTIENE_REPORTES_PRODUCTOS");
            atributo= (ArrayList<AtributosVO>) endpoint.invoke(params);
            logger.debug(".. Productos : " + atributo);

        } catch (BackboneApplicationException e) {
            logger.error("Backbone exception", e);
            throw new ApplicationException("Error al recuperar los datos");

        }
        return atributo ;
    }
    
    public List<AtributosVO> getProductosPlantillas(String cdPlantilla) throws ApplicationException {
        Map params = new HashMap<String, String>();
        params.put("cdPlantilla", cdPlantilla);

        List<AtributosVO> atributo = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_OBTIENE_REPORTES_PRODUCTOS_PLANTILLAS");
            atributo= (ArrayList<AtributosVO>) endpoint.invoke(params);
            logger.debug(".. Productos : " + atributo);

        } catch (BackboneApplicationException e) {
            logger.error("Backbone exception", e);
            throw new ApplicationException("Error al recuperar los datos");

        }
        return atributo ;
    }

    public List<ComboGraficoVo> getComboReportesProductos()
            throws ApplicationException {
        Map params = new HashMap<String, String>();


        List<ComboGraficoVo> combo = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_COMBO_REPORTES_PRODUCTOS");
            params.put("dsPlantilla", "");
            combo = (ArrayList<ComboGraficoVo>) endpoint.invoke(params);
            logger.debug(".. Aseguradora : " + combo);

        } catch (BackboneApplicationException e) {
            logger.error("Backbone exception", e);
            throw new ApplicationException("Error al recuperar los datos");

        }
        return combo;
    }
    
    public List<ComboGraficoVo> getComboReportesProductosPlantillas()
    throws ApplicationException {
		Map params = new HashMap<String, String>();
		
		
		List<ComboGraficoVo> combo = null;
		try {
		    Endpoint endpoint = (Endpoint) endpoints.get("P_COMBO_REPORTES_PRODUCTOS");
		    params.put("dsPlantilla", "");
		    combo = (ArrayList<ComboGraficoVo>) endpoint.invoke(params);
		    logger.debug(".. Aseguradora : " + combo);
		
		} catch (BackboneApplicationException e) {
		    logger.error("Backbone exception", e);
		    throw new ApplicationException("Error al recuperar los datos");
		
		}
		return combo;
	}


    public String insertarProducto(int cdReporte,String codProducto) throws ApplicationException {
        Map params = new HashMap<String, String>();
        //en el caso de insertar no importa el codigo ya que es gemerado por un secuencia en la BD
        WrapperResultados res = new WrapperResultados();
        params.put("cdReporte", cdReporte);
        params.put("cdProducto", codProducto);

        //Endpoint endpoint = (Endpoint) endpoints.get("P_INSERTAR_PRODUCTO");
        res = returnBackBoneInvoke(params, "P_INSERTAR_PRODUCTO");
        /*try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando insertar un producto");
        }*/
        return res.getMsgText();
    }
    
    public String insertarProductoPlantillas(String cdPlantilla,String codProducto) throws ApplicationException {
        Map params = new HashMap<String, String>();
        //en el caso de insertar no importa el codigo ya que es gemerado por un secuencia en la BD
        WrapperResultados res = new WrapperResultados();
        params.put("cdPlantilla", cdPlantilla);
        params.put("cdProducto", codProducto);

        Endpoint endpoint = (Endpoint) endpoints.get("P_INSERTAR_PRODUCTO_PLANTILLAS");
        try {
           res=(WrapperResultados)endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando insertar un producto plantillas");
        }
        return res.getMsgId();
    }

    public String editarProducto(int cdReporte,String codProducto,String cdProductoViejo) throws ApplicationException {
        WrapperResultados res = new WrapperResultados();
        Map params = new HashMap<String, String>();
        params.put("cdReporte",cdReporte);
        params.put("cdProductoViejo",cdProductoViejo);
        params.put("cdProducto",codProducto);


        Endpoint endpoint = (Endpoint) endpoints.get("P_EDITAR_PRODUCTO");
        try {
            res=(WrapperResultados)endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando insertar un producto");
        }
        return res.getMsgId();
    }
    
    public String editarProductoPlantillas(String cdPlantilla,String codProducto ,String codProductoAnt) throws ApplicationException {
        WrapperResultados res = new WrapperResultados();
        Map params = new HashMap<String, String>();
        params.put("cdPlantilla",cdPlantilla);
        params.put("cdProductoAnterior",codProductoAnt);
        params.put("cdProductoNuevo",codProducto);


        Endpoint endpoint = (Endpoint) endpoints.get("P_EDITAR_PRODUCTO_PLANTILLAS");
        try {
            res=(WrapperResultados)endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando insertar un producto plantillas");
        }
        return res.getMsgId();
    }



    public void borrarProducto(int cdReporte,String codProducto) throws ApplicationException {
        Map params = new HashMap<String, String>();
        //en el caso de insertar no importa el codigo ya que es gemerado por un secuencia en la BD
        params.put("cdReporte", cdReporte);
        params.put("cdProducto", codProducto);

        Endpoint endpoint = (Endpoint) endpoints.get("P_BORRAR_PRODUCTO");
        try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error BORRANDO insertar un producto");
        }catch (Exception e){

        }
    }
    
    public String borrarProductoPlantillas(String cdPlantilla,String codProducto) throws ApplicationException {
        WrapperResultados res = new WrapperResultados();
        Map params = new HashMap<String, String>();
        //en el caso de insertar no importa el codigo ya que es gemerado por un secuencia en la BD
        params.put("cdPlantilla", cdPlantilla);
        params.put("cdProducto", codProducto);

        Endpoint endpoint = (Endpoint) endpoints.get("P_BORRAR_PRODUCTO_PLANTILLAS");
        try {
            res=(WrapperResultados)endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error BORRANDO insertar un producto plantillas");
        }catch (Exception e){

        }
        return res.getMsgText();
    }

    public List<PlantillaVO> getPlantillas(String dsPlantilla) throws ApplicationException {

        Map params = new HashMap();
        params.put("p_vdsplantilla", ConvertUtil.nvl(dsPlantilla));


        return getAllBackBoneInvoke(params, BUSCAR_PLANTILLAS);


        /* Map params = new HashMap<String, String>();
      params.put("dsPlantilla", dsPlantilla);

      List<PlantillaVO> plantillas = null;
      try {
          Endpoint endpoint = (Endpoint) endpoints.get("P_OBTIENE_MPLANTIL");
          plantillas= (ArrayList<PlantillaVO>) endpoint.invoke(params);
          logger.debug(".. Plantillas : " + plantillas);

      } catch (BackboneApplicationException e) {
          logger.error("Backbone exception", e);
          throw new ApplicationException("Error al recuperar los datos");

      }
      return plantillas ;*/
    }


    public void insertarPlantilla(PlantillaVO plantilla) throws ApplicationException {
        Map params = new HashMap<String, String>();
        //en el caso de insertar no importa el codigo ya que es gemerado por un secuencia en la BD
        params.put("cdPlantilla", 0);
        params.put("dsPlantilla", plantilla.getDsPlantilla());
        if(plantilla.getStatus().equals("SI"))
            params.put("status", 1);
        else
            params.put("status", 0);
        /*si es insertar se manda como parametro al Pl "1" si es guardar es "0"*/
        params.put("insertar",1);

        Endpoint endpoint = (Endpoint) endpoints.get("P_GUARDA_MPLANTIL");
        try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando insertar una nueva plantilla");
        }
    }

    public String borrarPlantilla(String cdPlantilla) throws ApplicationException {
        WrapperResultados res = new WrapperResultados();
        Map params = new HashMap<String, String>();

        params.put("cdPlantilla", cdPlantilla);

        Endpoint endpoint = (Endpoint) endpoints.get("P_BORRA_MPLANTIL");
        try {
              res= (WrapperResultados)endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando borrar reporte");
        }
          return  res.getMsg();
     }

    public void editarPlantilla(PlantillaVO plantilla) throws ApplicationException {

        Map params = new HashMap<String, String>();

        params.put("cdPlantilla", plantilla.getCdPlantilla());
        params.put("dsPlantilla", plantilla.getDsPlantilla());
        if(plantilla.getStatus().equals("SI"))
            params.put("status", 1);
        else
            params.put("status", 0);

        params.put("insertar",0);

        Endpoint endpoint = (Endpoint) endpoints.get("P_GUARDA_MPLANTIL");
        try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando editar plantilla");
        }
    }


    public List<AtributosVO> getPlantillasAtributos(String cdPlantilla)
            throws ApplicationException {
        Map params = new HashMap<String, String>();
        params.put("cdPlantilla", cdPlantilla);

        List<AtributosVO> atributos = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_OBTIENE_TATRIPLA");
            atributos= (ArrayList<AtributosVO>) endpoint.invoke(params);
            logger.debug(".. Atributos : " + atributos);

        } catch (BackboneApplicationException e) {
            logger.error("Backbone exception", e);
            throw new ApplicationException("Error al recuperar los datos");

        }
        return atributos ;
    }

        public  List<TablaApoyoVO> getPlantAtribTablaApoyo(String cdPlantilla) throws ApplicationException{
         Map params = new HashMap<String, String>();
        params.put("cdPlantilla", cdPlantilla);

        List<TablaApoyoVO> tapoyo = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_LISVALATRIBVAR");
            tapoyo= (ArrayList<TablaApoyoVO>) endpoint.invoke(params);
            logger.debug(".. tapoyo : " + tapoyo);

        } catch (BackboneApplicationException e) {
            logger.error("Backbone exception", e);
            throw new ApplicationException("Error al recuperar los datos");

        }
        return tapoyo ;
        }

    public void insertaPlantillaAtributo(AtributosVO atributo) throws ApplicationException {
        Map params = new HashMap<String, String>();

        params.put("cdPlantilla", atributo.getCdPlantilla());
        params.put("dsAtributo", atributo.getDsAtributo());
        params.put("formato", atributo.getSwFormat());
        params.put("minimo", atributo.getNmLmin());
        params.put("maximo", atributo.getNmLmax());
        params.put("apoyo", atributo.getOtTabval());

        Endpoint endpoint = (Endpoint) endpoints.get("P_INSERTA_TATRIPLA");
        try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando insertar un nuevo TATRIPLA");
        }
    }


    public String borrarPlantillaAtributo(AtributosVO atributo) throws ApplicationException {
        Map params = new HashMap<String, String>();
        WrapperResultados res = new WrapperResultados();
        params.put("cdPlantilla", atributo.getCdPlantilla());
        params.put("cdAtributo", atributo.getCdAtributo());

        Endpoint endpoint = (Endpoint) endpoints.get("P_BORRA_TATRIPLA");
        try {
            res= (WrapperResultados)endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando borrar TATRIPLA");
        }
        return  res.getMsg();
    }

    public void editarPlantillaAtributo(AtributosVO atributo) throws ApplicationException {

        Map params = new HashMap<String, String>();

        params.put("cdPlantilla", atributo.getCdPlantilla());
        params.put("cdAtributo", atributo.getCdAtributo());
        params.put("dsAtributo", atributo.getDsAtributo());
        params.put("formato", atributo.getSwFormat());
        params.put("minimo", atributo.getNmLmin());
        params.put("maximo", atributo.getNmLmax());
        params.put("apoyo", atributo.getOtTabval());

        Endpoint endpoint = (Endpoint) endpoints.get("P_GUARDA_TATRIPLA");
        try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando editar la TATRIPLA");
        }
    }

    public List<PlantillaReporteVO> getPlantillaReporte(PlantillaReporteVO plantilla_reporte) throws ApplicationException {
    //public  PagedList getPlantillaReporte(PlantillaReporteVO plantilla_reporte,int start, int limit) throws ApplicationException {
        Map params = new HashMap<String, String>();
        //se setean estos codigos pero en el Pl no se usan para la busqueda, estos son usados en la edicion
  
         if ( plantilla_reporte.getDsCorporativo()=="")plantilla_reporte.setDsCorporativo(null);
        if(plantilla_reporte.getDsCorporativo()=="")plantilla_reporte.setDsCorporativo(null);
        if(plantilla_reporte.getDsPlantilla()=="")plantilla_reporte.setDsPlantilla(null);
        if(plantilla_reporte.getDsProducto()=="")plantilla_reporte.setDsProducto(null);
        if(plantilla_reporte.getDsReporte()=="")plantilla_reporte.setDsReporte(null);

        params.put("p_ncdelemento", "0");
        params.put("p_ncdramo", "0");
        params.put("p_ncdunieco", "0");
        params.put("p_ncdplantilla", "0");
        params.put("p_ncdreporte", "0");
        params.put("p_vdselemento", plantilla_reporte.getDsCorporativo());
        params.put("p_vdsramo", plantilla_reporte.getDsProducto());
        params.put("p_vdsunieco", plantilla_reporte.getDsAseguradora());
        params.put("p_vdsplantilla", plantilla_reporte.getDsPlantilla());
        params.put("p_vdsreporte", plantilla_reporte.getDsReporte());
        params.put("busca_por_codigo", 2);


        //HashMap map = new HashMap();
        //map.put("dsPlantilla", dsPlantilla);
      //String endpointName = "BUSCAR_PLANTILLAS";
        //return pagedBackBoneInvoke(params, endpointName, start, limit);

        //List<PlantillaReporteVO> output = null;
        List<PlantillaReporteVO> output = new  ArrayList<PlantillaReporteVO>();
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_OBTIENE_TPLANREP");
            output= (ArrayList<PlantillaReporteVO>) endpoint.invoke(params);
            logger.debug(".. Plantillas_Reportes : " + output);

        } catch (BackboneApplicationException e) {
            logger.error("Backbone exception", e);
            throw new ApplicationException("Error al recuperar los datos");

        }
        return output;
    }


    public List<PlantillaReporteVO> getListaCorporativo(String dsCorporativo) throws ApplicationException {
        Map params = new HashMap<String, String>();
        params.put("", ""); //no son necesarios parametros en este invoke

        List<PlantillaReporteVO> corporativos = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_CLIENTES_CORPO");
            corporativos = (ArrayList<PlantillaReporteVO>) endpoint.invoke(params);
            logger.debug(".. ListaCorporativos : " + corporativos);

        } catch (BackboneApplicationException e) {
            logger.error("Backbone exception", e);
            throw new ApplicationException("Error al recuperar los datos");
        }
        return corporativos;
    }
    public List<PlantillaReporteVO> getListaPlantilla(String dsPlantilla) throws ApplicationException{
        Map params =new HashMap<String,String>();
        params.put("","");
        List<PlantillaReporteVO> plantilla = null;
        try{
            Endpoint endpoint = (Endpoint) endpoints.get("P_OBTIENE_MPLANTIL");
            plantilla = (ArrayList<PlantillaReporteVO>) endpoint.invoke(params);
            logger.debug("..ListaPlantillas :" + plantilla);
        }catch (BackboneApplicationException e){
            logger.error("Backbone exception", e);
            throw new ApplicationException("Error al recuperar los datos");
        }
        return plantilla;
    }
    public List<PlantillaReporteVO> getListaAseguradora(String dsAseguradora) throws ApplicationException {
        Map params = new HashMap<String, String>();
        params.put("", ""); //no son necesarios parametros en este invoke

        List<PlantillaReporteVO> aseguradora = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_ASEGURADORA");
            aseguradora = (ArrayList<PlantillaReporteVO>) endpoint.invoke(params);
            logger.debug(".. ListaAseguradora : " + aseguradora);

        } catch (BackboneApplicationException e) {
            logger.error("Backbone exception", e);
            throw new ApplicationException("Error al recuperar los datos");
        }
        return aseguradora;
    }

    public List<PlantillaReporteVO> getListaProducto(String dsProducto) throws ApplicationException {
        Map params = new HashMap<String, String>();
        params.put("", ""); //no son necesarios parametros en este invoke

        List<PlantillaReporteVO> producto = null;
        try {                 
            Endpoint endpoint = (Endpoint) endpoints.get("P_PRODUCTOS");
            producto = (ArrayList<PlantillaReporteVO>) endpoint.invoke(params);
            logger.debug(".. ListaProductos : " + producto);

        } catch (BackboneApplicationException e) {
            logger.error("Backbone exception", e);
            throw new ApplicationException("Error al recuperar los datos");
        }
        return producto;
    }

    public List<ReporteVO> getListaReporte(String dsReporte) throws ApplicationException {
        Map params = new HashMap<String, String>();
        params.put("dsReporte", ""); //no son necesarios parametros en este invoke

        List<ReporteVO> reportes = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_OBTIENE_MREPORTE");
            reportes = (ArrayList<ReporteVO>) endpoint.invoke(params);
            logger.debug(".. ListaReportes : " + reportes);

        } catch (BackboneApplicationException e) {
            logger.error("Backbone exception", e);
            throw new ApplicationException("Error al recuperar los datos");
        }
        return reportes;
    }



    public void asociarPlantillaReporte(PlantillaReporteVO plantillaReporte) throws ApplicationException {
        Map params = new HashMap<String, String>();

        params.put("cdPlantilla",plantillaReporte.getCdPlantilla());
        params.put("cdAseguradora",plantillaReporte.getCdAseguradora());
        params.put("cdCorporativo",plantillaReporte.getCdCorporativo());
        params.put("cdProducto",plantillaReporte.getCdProducto());
        params.put("cdReporte",plantillaReporte.getCdReporte());

        params.put("insertar", 1);/*si es insertar se manda como parametro al Pl "1" si es guardar es "0"*/
        //Esto no se usa en insertar, es para la editarAsociarPlantillaReporte
        params.put("cdPlantillaAnterior",0);
        params.put("cdAseguradoraAnterior",0);
        params.put("cdCorporativoAnterior",0);
        params.put("cdProductoAnterior",0);
        params.put("cdReporteAnterior",0);

        Endpoint endpoint = (Endpoint) endpoints.get("P_GUARDA_TPLANREP1");
        try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            e.printStackTrace();
            throw new ApplicationException(
                    "Error intentando insertar una nueva plantilla-reporte");
        }
    }


    public void editarAsociarPlantillaReporte(PlantillaReporteVO plantillaReporte) throws ApplicationException {


        Map params = new HashMap<String, String>();

        params.put("cdPlantilla",plantillaReporte.getCdPlantilla());
        params.put("cdAseguradora",plantillaReporte.getCdAseguradora());
        params.put("cdCorporativo",plantillaReporte.getCdCorporativo());
        params.put("cdProducto",plantillaReporte.getCdProducto());
        params.put("cdReporte",plantillaReporte.getCdReporte());

        params.put("cdPlantillaAnterior",plantillaReporte.getCdPlantillaAnterior());
        params.put("cdAseguradoraAnterior",plantillaReporte.getCdAseguradoraAnterior());
        params.put("cdCorporativoAnterior",plantillaReporte.getCdCorporativoAnterior());
        params.put("cdProductoAnterior",plantillaReporte.getCdProductoAnterior());
        params.put("cdReporteAnterior",plantillaReporte.getCdReporteAnterior());

        params.put("insertar", 0);//si es insertar se manda como parametro al Pl "1" si es guardar es "0"

        Endpoint endpoint = (Endpoint) endpoints.get("P_GUARDA_TPLANREP1");

        try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando insertar una nueva plantilla");
        }

    }


    public void borrarAsociarPlantillaReporte(PlantillaReporteVO plantillaReporte) throws ApplicationException {

        Map params = new HashMap<String, String>();

        params.put("cdPlantilla",plantillaReporte.getCdPlantilla());
        params.put("cdAseguradora",plantillaReporte.getCdAseguradora());
        params.put("cdCorporativo",plantillaReporte.getCdCorporativo());
        params.put("cdProducto",plantillaReporte.getCdProducto());
        params.put("cdReporte",plantillaReporte.getCdReporte());

        Endpoint endpoint = (Endpoint) endpoints.get("P_BORRA_TPLANREP");

        try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException(
                    "Error intentando Borrar una asociacion");
        }

    }

    public TableModelExport getModelPrincipal(String dsReporteB) throws ApplicationException {
        TableModelExport model = new TableModelExport();
        Map params = new HashMap<String, String>();
        params.put("p_vdsreporte", dsReporteB);
        ArrayList lista = new ArrayList(); // lISTA A EXPORTAR
        List<ReporteVO> reportes = null;
                                    
           // lista = (ArrayList) super.getExporterAllBackBoneInvoke(params,"P_OBTIENE_MREPORTE");
            reportes=(ArrayList<ReporteVO>)getAllBackBoneInvoke(params, BUSCAR_REPORTES_ADMINISTRACION);
            for(int i=0;i<reportes.size();i++){
                ArrayList listaaux = new ArrayList();        //LISTA INTERNA AUXILIAR ES LA UNICA FORMA QUE EXPORT ENTIENDA LAS LISTAS UN ARRAYLIST DENTRO DE OTRO ARRAYLIST

                //lista.add(prueba);
                listaaux.add(reportes.get(i).getCdReporte());
                listaaux.add(reportes.get(i).getDsReporte());
                listaaux.add(reportes.get(i).getNmReporte());                
                lista.add(listaaux);
            }
            logger.debug(".. Reportes : " + lista);
            model.setInformation(lista);
            model.setColumnName(new String[] { "Código de Reporte ", "Descripción ", "Ejecutable " });
        return model;
    }

    public TableModelExport getModelAtributos(String codigoRep) throws ApplicationException {
        TableModelExport model = new TableModelExport();
        Map params = new HashMap<String, String>();
        params.put("cdReporte", codigoRep);
        ArrayList lista = new ArrayList(); // lISTA A EXPORTAR
        List<AtributosVO> atributos = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_OBTIENE_ATRIBUTOS");
            atributos= (ArrayList<AtributosVO>) endpoint.invoke(params);
            logger.debug(".. Atributos : " + atributos);
            for(int i=0;i<atributos.size();i++){
                ArrayList listaaux = new ArrayList();        //LISTA INTERNA AUXILIAR ES LA UNICA FORMA QUE EXPORT ENTIENDA LAS LISTAS UN ARRAYLIST DENTRO DE OTRO ARRAYLIST

                //lista.add(prueba);
                listaaux.add(atributos.get(i).getCdAtributo());
                listaaux.add(atributos.get(i).getDsAtributo());
                listaaux.add(atributos.get(i).getSwFormat());
                lista.add(listaaux);
            }
            logger.debug(".. atributos : " + lista);
            model.setInformation(lista);
            model.setColumnName(new String[] { "Código de Atributo", "Descripción", "Formato" });

        } catch (BackboneApplicationException e) {
            throw new ApplicationException( "Excepcion al consultarse las configuraciones");
        }

        return model;
    }

    public TableModelExport getModelProductos(int codigoRep) throws ApplicationException {
        TableModelExport model = new TableModelExport();
        Map params = new HashMap<String, String>();
        params.put("cdReporte", codigoRep);
        ArrayList lista = new ArrayList(); // lISTA A EXPORTAR
        List<AtributosVO> atributos = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_OBTIENE_REPORTES_PRODUCTOS");
            atributos= (ArrayList<AtributosVO>) endpoint.invoke(params);
            logger.debug(".. Atributos : " + atributos);
            for(int i=0;i<atributos.size();i++){
                ArrayList listaaux = new ArrayList();        //LISTA INTERNA AUXILIAR ES LA UNICA FORMA QUE EXPORT ENTIENDA LAS LISTAS UN ARRAYLIST DENTRO DE OTRO ARRAYLIST

                //lista.add(prueba);
                listaaux.add(atributos.get(i).getCdReporte());
                listaaux.add(atributos.get(i).getDsAtributo());
                lista.add(listaaux);
            }


            logger.debug(".. atributos : " + lista);
            model.setInformation(lista);
            model.setColumnName(new String[] { "Código de Producto", "Descripción" });

        } catch (BackboneApplicationException e) {
            throw new ApplicationException( "Excepcion al consultarse las configuraciones");
        }

        return model;
    }
    
    public TableModelExport getModelProductosPlantillas(String cdPlantilla) throws ApplicationException {
        TableModelExport model = new TableModelExport();
        Map params = new HashMap<String, String>();
        params.put("cdPlantilla", cdPlantilla);
        ArrayList lista = new ArrayList(); // lISTA A EXPORTAR
        List<AtributosVO> atributos = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_OBTIENE_REPORTES_PRODUCTOS_PLANTILLAS");
            atributos= (ArrayList<AtributosVO>) endpoint.invoke(params);
            logger.debug(".. Atributos : " + atributos);
            for(int i=0;i<atributos.size();i++){
                ArrayList listaaux = new ArrayList();        //LISTA INTERNA AUXILIAR ES LA UNICA FORMA QUE EXPORT ENTIENDA LAS LISTAS UN ARRAYLIST DENTRO DE OTRO ARRAYLIST

                //lista.add(prueba);
                listaaux.add(atributos.get(i).getCdAtributo());
                listaaux.add(atributos.get(i).getDsAtributo());
                lista.add(listaaux);
            }


            logger.debug(".. atributos : " + lista);
            model.setInformation(lista);
            model.setColumnName(new String[] { "Código de Producto", "Descripción" });

        } catch (BackboneApplicationException e) {
            throw new ApplicationException( "Excepcion al consultarse las configuraciones");
        }

        return model;
    }

    public TableModelExport getModelGraficos(int codigoRep) throws ApplicationException {
        TableModelExport model = new TableModelExport();
        Map params = new HashMap<String, String>();
        params.put("cdReporte", codigoRep);
        params.put("tipo", null);
        params.put("cdTabla", "TTIPOGRAF");
        params.put("cdRegion", 1);
        params.put("cdIdioma", 1);
        ArrayList lista = new ArrayList(); // lISTA A EXPORTAR
        List<GraficoVO> grafico = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_GRAFICO");
            grafico= (ArrayList<GraficoVO>) endpoint.invoke(params);

            for(int i=0;i<grafico.size();i++){
                ArrayList listaaux = new ArrayList();        //LISTA INTERNA AUXILIAR ES LA UNICA FORMA QUE EXPORT ENTIENDA LAS LISTAS UN ARRAYLIST DENTRO DE OTRO ARRAYLIST

                //lista.add(prueba);
                listaaux.add(grafico.get(i).getNmGrafico());
                listaaux.add(grafico.get(i).getDescripcion());
                lista.add(listaaux);
            }


            logger.debug(".. grafico : " + lista);
            model.setInformation(lista);
            model.setColumnName(new String[] { "Nombre del Grafico", "Tipo de Grafico" });

        } catch (BackboneApplicationException e) {
            throw new ApplicationException( "Excepcion al consultarse las configuraciones");
        }

        return model;
    }





    public TableModelExport getModelPlantillas(String desPlantilla) throws ApplicationException {
        TableModelExport model = new TableModelExport();
        Map params = new HashMap<String, String>();
        params.put("dsPlantilla", desPlantilla);
        ArrayList lista = new ArrayList(); // lISTA A EXPORTAR
        List<PlantillaVO> plantillas = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_OBTIENE_MPLANTIL");
            plantillas= (ArrayList<PlantillaVO>) endpoint.invoke(params);
            logger.debug(".. Plantillas : " + plantillas);
            for(int i=0;i<plantillas.size();i++){
                ArrayList listaaux = new ArrayList();        //LISTA INTERNA AUXILIAR ES LA UNICA FORMA QUE EXPORT ENTIENDA LAS LISTAS UN ARRAYLIST DENTRO DE OTRO ARRAYLIST

                listaaux.add(plantillas.get(i).getCdPlantilla());
                listaaux.add(plantillas.get(i).getDsPlantilla());
                listaaux.add(plantillas.get(i).getStatus());

                lista.add(listaaux);
            }
            logger.debug(".. atributos : " + lista);
            model.setInformation(lista);
            model.setColumnName(new String[] { "Código de Plantilla", "Descripción", "Status" });

        } catch (BackboneApplicationException e) {
            throw new ApplicationException( "Excepcion al consultarse las configuraciones");
        }

        return model;
    }


    public TableModelExport getModelPlantillasAsociar(PlantillaReporteVO plantillaReporte) throws ApplicationException {
        TableModelExport model = new TableModelExport();
        Map params = new HashMap<String, String>();

        params.put("cdPlantilla", "0");
        params.put("cdReporte", "0");
        params.put("cdAseguradora", "0");
        params.put("cdCorporativo", "0");
        params.put("cdProducto", "0");

        params.put("dsPlantilla", plantillaReporte.getDsPlantilla());
        params.put("dsReporte", plantillaReporte.getDsReporte());
        params.put("dsAseguradora", plantillaReporte.getDsAseguradora());
        params.put("dsCorporativo", plantillaReporte.getDsCorporativo());
        params.put("dsProducto", plantillaReporte.getDsProducto());

        params.put("busca_por_codigo", "0");

        ArrayList lista = new ArrayList(); // lISTA A EXPORTAR
        List<PlantillaReporteVO> plantillas = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_OBTIENE_TPLANREP");
            plantillas= (ArrayList<PlantillaReporteVO>) endpoint.invoke(params);
            logger.debug(".. Plantillas : " + plantillas);
            for(int i=0;i<plantillas.size();i++){
                ArrayList listaaux = new ArrayList();        //LISTA INTERNA AUXILIAR ES LA UNICA FORMA QUE EXPORT ENTIENDA LAS LISTAS UN ARRAYLIST DENTRO DE OTRO ARRAYLIST

                listaaux.add(plantillas.get(i).getDsPlantilla());
                listaaux.add(plantillas.get(i).getDsCorporativo());
                listaaux.add(plantillas.get(i).getDsAseguradora());
                listaaux.add(plantillas.get(i).getDsProducto());
                listaaux.add(plantillas.get(i).getDsReporte());

                lista.add(listaaux);
            }
            logger.debug(".. Plantillas Reportes : " + lista);
            model.setInformation(lista);
            model.setColumnName(new String[] { "Plantilla", "Corporativo", "Aseguradora", "Producto", "Reporte" });

        } catch (BackboneApplicationException e) {
            throw new ApplicationException( "Excepcion al consultarse las configuraciones");
        }

        return model;
    }

    public TableModelExport getModelPlantillasAtributos(String cdPlantilla) throws ApplicationException {
        TableModelExport model = new TableModelExport();
        Map params = new HashMap<String, String>();
        params.put("cdPlantilla", cdPlantilla);
        ArrayList lista = new ArrayList(); // lISTA A EXPORTAR
        List<AtributosVO> atributos = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("P_OBTIENE_TATRIPLA");
            atributos= (ArrayList<AtributosVO>) endpoint.invoke(params);
            logger.debug(".. Plantillas : " + atributos);
            for(int i=0;i<atributos.size();i++){
                ArrayList listaaux = new ArrayList();        //LISTA INTERNA AUXILIAR ES LA UNICA FORMA QUE EXPORT ENTIENDA LAS LISTAS UN ARRAYLIST DENTRO DE OTRO ARRAYLIST

                listaaux.add(atributos.get(i).getCdAtributo());
                listaaux.add(atributos.get(i).getDsAtributo());
                listaaux.add(atributos.get(i).getSwFormat());
                listaaux.add(atributos.get(i).getNmLmax());
                listaaux.add(atributos.get(i).getNmLmin());
                listaaux.add(atributos.get(i).getOtTabval());

                lista.add(listaaux);
            }
            logger.debug(".. atributos : " + lista);
            model.setInformation(lista);
            model.setColumnName(new String[] { "Código de Atributo", "Descripción", "Formato","máximo", "mínimo", "Tabla" });

        } catch (BackboneApplicationException e) {
            throw new ApplicationException( "Excepcion al consultarse las configuraciones");
        }

        return model;
    }




}