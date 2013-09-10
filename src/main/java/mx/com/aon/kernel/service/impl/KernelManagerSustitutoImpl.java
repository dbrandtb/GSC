/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.aon.kernel.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.flujos.cotizacion.model.AyudaCoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.CoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.ResultadoCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.SituacionVO;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.dao.ProcesoDAO;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.Tatrisit;
import org.apache.log4j.Logger;

/**
 *
 * @author Jair
 */
public class KernelManagerSustitutoImpl extends AbstractManagerJdbcTemplateInvoke implements KernelManagerSustituto
{
    
    protected final transient Logger log = Logger.getLogger(KernelManagerSustitutoImpl.class);
    
    public WrapperResultados calculaNumeroPoliza(String pv_cdunieco_i, String pv_cdramo_i, String pv_estado_i) throws ApplicationException
    {
        log.debug("### kernel sustituto calculaNumeroPoliza param: "+pv_cdunieco_i+", "+pv_cdramo_i+", "+pv_estado_i);
        Map parametros=new HashMap<String,String>(0);
        parametros.put("pv_cdunieco_i", pv_cdunieco_i);
        parametros.put("pv_cdramo_i", pv_cdramo_i);
        parametros.put("pv_estado_i", pv_estado_i);
        WrapperResultados res=this.returnBackBoneInvoke(parametros, ProcesoDAO.CALCULA_NUMERO_POLIZA);
        log.debug("### kernel sustituto calculaNumeroPoliza numero de poliza calculado:"+res.getItemMap().get("NUMERO_POLIZA"));
        log.debug("### kernel sustituto calculaNumeroPoliza id:"+res.getMsgId());
        log.debug("### kernel sustituto calculaNumeroPoliza mesage:"+res.getMsgText());
        return res;
    }
    
    public WrapperResultados insertaMaestroPolizas(Map<String, String> parameters) throws ApplicationException
    {
        log.debug("### kernel sustituto insertaMaestroPolizas map: "+parameters);
        WrapperResultados res=this.returnBackBoneInvoke(parameters, ProcesoDAO.P_MOV_MPOLIZAS);
        log.debug("### kernel sustituto insertaMaestroPolizas id:"+res.getMsgId());
        log.debug("### kernel sustituto insertaMaestroPolizas mesage:"+res.getMsgText());
        return res;
    }
    
    public WrapperResultados insertaPolisit(Map<String, Object> parameters) throws ApplicationException
    {
        log.debug("### kernel sustituto insertaPolisit map: "+parameters);
        WrapperResultados res=this.returnBackBoneInvoke(parameters, ProcesoDAO.P_MOV_MPOLISIT);
        log.debug("### kernel sustituto insertaPolisit id:"+res.getMsgId());
        log.debug("### kernel sustituto insertaPolisit mesage:"+res.getMsgText());
        return res;
    }
    
    public WrapperResultados insertaValoresSituaciones(Map<String, String> parameters) throws ApplicationException
    {
        log.debug("### kernel sustituto insertaValoresSituaciones map: "+parameters);
        WrapperResultados res=this.returnBackBoneInvoke(parameters, ProcesoDAO.P_MOV_TVALOSIT);
        log.debug("### kernel sustituto insertaValoresSituaciones id:"+res.getMsgId());
        log.debug("### kernel sustituto insertaValoresSituaciones mesage:"+res.getMsgText());
        return res;
    }
    
    public List<SituacionVO> clonaSituaciones(Map<String,String> parameters) throws ApplicationException
    {
        log.debug("### kernel sustituto clonaSituaciones map: "+parameters);
        List<SituacionVO> lista= this.getAllBackBoneInvoke(parameters, ProcesoDAO.CLONAR_SITUACION);
        lista=lista!=null?lista:new ArrayList<SituacionVO>(0);
        log.debug("### kernel sustituto clonaSituaciones lista size: "+lista.size());
        return lista;
    }
    
    public WrapperResultados coberturas(Map<String,String> parameters) throws ApplicationException
    {
        log.debug("### kernel sustituto coberturas map: "+parameters);
        WrapperResultados res=this.returnBackBoneInvoke(parameters, ProcesoDAO.EJECUTA_P_EXEC_SIGSVDEF);
        log.debug("### kernel sustituto coberturas id:"+res.getMsgId());
        log.debug("### kernel sustituto coberturas mesage:"+res.getMsgText());
        return res;
    }
    
    public WrapperResultados ejecutaASIGSVALIPOL(Map<String,String> parameters) throws ApplicationException
    {
        log.debug("### kernel sustituto ejecuta asigsvalipol map: "+parameters);
        WrapperResultados res=this.returnBackBoneInvoke(parameters, ProcesoDAO.EJECUTA_SIGSVALIPOL);
        log.debug("### kernel sustituto ejecuta asigsvalipol id:"+res.getMsgId());
        log.debug("### kernel sustituto ejecuta asigsvalipol mesage:"+res.getMsgText());
        return res;
    }
    
    public WrapperResultados clonaPersonas(Map<String,Object> parameters) throws ApplicationException
    {
        log.debug("### kernel sustituto clonaPersonas map: "+parameters);
        WrapperResultados res=this.returnBackBoneInvoke(parameters, ProcesoDAO.P_CLONAR_PERSONAS);
        log.debug("### kernel sustituto clonaPersonas id:"+res.getMsgId());
        log.debug("### kernel sustituto clonaPersonas mesage:"+res.getMsgText());
        return res;
    }
    
    public List<ResultadoCotizacionVO> obtenerResultadosCotizacion(Map<String,String> parameters) throws ApplicationException
    {
        log.debug("### kernel sustituto obtenerResultadosCotizacion map: "+parameters);
        List<ResultadoCotizacionVO> lista= this.getAllBackBoneInvoke(parameters, ProcesoDAO.OBTENER_RESULTADOS_COTIZACION);
        lista=lista!=null?lista:new ArrayList<ResultadoCotizacionVO>(0);
        log.debug("### kernel sustituto obtenerResultadosCotizacion lista size: "+lista.size());
        return lista;
    }
    
    public List<CoberturaCotizacionVO> obtenerCoberturas(Map<String,String> parameters) throws ApplicationException
    {
        log.debug("### kernel sustituto obtenerCoberturas map: "+parameters);
        List<CoberturaCotizacionVO> lista= this.getAllBackBoneInvoke(parameters, ProcesoDAO.OBTENER_COBERTURAS);
        lista=lista!=null?lista:new ArrayList<CoberturaCotizacionVO>(0);
        log.debug("### kernel sustituto obtenerCoberturas lista size: "+lista.size());
        return lista;
    }
    
    public AyudaCoberturaCotizacionVO obtenerAyudaCobertura(String idCobertura,String idRamo,String idCiaAsegurador) throws ApplicationException
    {
        Map<String,Object>parameters=new HashMap<String,Object>(0);
        parameters.put("pv_garant_i",idCobertura);
        parameters.put("pv_cdramo_i",idRamo);
        parameters.put("pv_ciaaseg_i",idCiaAsegurador);
        log.debug("### kernel sustituto obtenerAyudaCobertura map: "+parameters);
        AyudaCoberturaCotizacionVO res=(AyudaCoberturaCotizacionVO) this.getBackBoneInvoke(parameters, ProcesoDAO.OBTENER_AYUDA_COBERTURA);
        log.debug("### kernel sustituto obtenerAyudaCobertura return: "+res);
        return res;
    }
    
    public DatosUsuario obtenerDatosUsuario(String cdusuario) throws ApplicationException
    {
        Map<String,Object>parameters=new HashMap<String,Object>(0);
        parameters.put("pv_cdusuari_i",cdusuario);
        log.debug("### kernel sustituto obtenerDatosUsuario map: "+parameters);
        DatosUsuario res=(DatosUsuario) this.getBackBoneInvoke(parameters, ProcesoDAO.OBTENER_DATOS_USUARIO);
        log.debug("### kernel sustituto obtenerDatosUsuario return: "+res);
        return res;
    }
    
    public List<Tatrisit> obtenerTatrisit(String cdtipsit) throws ApplicationException
    {
        Map<String,Object> parameters=new HashMap<String,Object>(0);
        parameters.put("pv_cdtipsit_i",cdtipsit);
        log.debug("### kernel sustituto obtenerTatrisit map: "+parameters);
        List<Tatrisit> lista= this.getAllBackBoneInvoke(parameters, ProcesoDAO.OBTENER_TATRISIT);
        lista=lista!=null?lista:new ArrayList<Tatrisit>(0);
        log.debug("### kernel sustituto obtenerTatrisit lista size: "+lista.size());
        return lista;
    }
    
    public WrapperResultados comprarCotizacion(Map<String,String> parameters) throws ApplicationException
    {
    	log.debug("### kernel sustituto comprarCotizacion map: "+parameters);
        WrapperResultados res=this.returnBackBoneInvoke(parameters, ProcesoDAO.COMPRAR_COTIZACION);
        log.debug("### kernel sustituto comprarCotizacion id:"+res.getMsgId());
        log.debug("### kernel sustituto comprarCotizacion mesage:"+res.getMsgText());
        return res;
    }
    
    public WrapperResultados movDetalleSuplemento(Map<String,String> parameters) throws ApplicationException
    {
    	log.debug("### kernel sustituto movDetalleSuplemento map: "+parameters);
        WrapperResultados res=this.returnBackBoneInvoke(parameters, ProcesoDAO.INSERTAR_DETALLE_SUPLEMEN);
        log.debug("### kernel sustituto movDetalleSuplemento id:"+res.getMsgId());
        log.debug("### kernel sustituto movDetalleSuplemento mesage:"+res.getMsgText());
        return res;
    }

    public Map<String,Object> getInfoMpolizas(Map<String, Object> parameters) throws ApplicationException {
        log.debug("### kernel sustituto getInfoMpolizas map: "+parameters);
        Map<String,Object> map=(Map<String,Object>) this.getBackBoneInvoke(parameters, ProcesoDAO.GET_INFO_MPOLIZAS);
        log.debug("### kernel sustituto response map: "+map);
        return map;
    }
    
    public List<GenericVO> getTmanteni(String tabla) throws ApplicationException
    {
        log.debug("### kernel sustituto getTmanteni tabla: "+tabla);
        Map<String,String> parameters=new HashMap<String,String>(0);
        parameters.put("pv_cdtabla",tabla);
        List<GenericVO> lista= this.getAllBackBoneInvoke(parameters, ProcesoDAO.OBTENER_TMANTENI);
        lista=lista!=null?lista:new ArrayList<GenericVO>(0);
        log.debug("### kernel sustituto obtenerTatrisit lista size: "+lista.size());
        return lista;
    }
    
    public List<Map<String, Object>> obtenerAsegurados(Map<String, String> parameters) throws ApplicationException
    {
    	log.debug("### kernel sustituto obtenerAsegurados parameters: "+parameters);
        List<Map<String,Object>> lista= this.getAllBackBoneInvoke(parameters, ProcesoDAO.OBTENER_ASEGURADOS);
        lista=lista!=null?lista:new ArrayList<Map<String,Object>>(0);
        log.debug("### kernel sustituto obtenerAsegurados lista size: "+lista.size());
        return lista;
    }
    
    public Map<String,Object> getInfoMpolizasCompleta(Map<String,String> parameters) throws ApplicationException {
        log.debug("### kernel sustituto getInfoMpolizasCompleta map: "+parameters);
        Map<String,Object> map=(Map<String,Object>) this.getBackBoneInvoke(parameters, ProcesoDAO.OBTENER_POLIZA_COMPLETA);
        log.debug("### kernel sustituto getInfoMpolizasCompleta response map: "+map);
        return map;
    }
    
}