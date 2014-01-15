/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.aon.kernel.service.impl;

import static mx.com.gseguros.portal.consultas.dao.ConsultasPolizaDAO.OBTIENE_DATOS_ASEGURADO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.flujos.cotizacion.model.AyudaCoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.CoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.ResultadoCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.SituacionVO;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.dao.ProcesoDAO;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.Tatri;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlParameter;

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
        Map<String,String> parametros=new HashMap<String,String>(0);
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
    
    public WrapperResultados actualizaValoresSituaciones(Map<String, String> parameters) throws ApplicationException
    {
        log.debug("### kernel sustituto actualizaValoresSituaciones map: "+parameters);
        WrapperResultados res=this.returnBackBoneInvoke(parameters, ProcesoDAO.P_UPD_TVALOSIT);
        log.debug("### kernel sustituto actualizaValoresSituaciones id:"+res.getMsgId());
        log.debug("### kernel sustituto actualizaValoresSituaciones mesage:"+res.getMsgText());
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
    
    public WrapperResultados ejecutaASIGSVALIPOL_EMI(Map<String,String> parameters) throws ApplicationException
    {
        log.debug("### kernel sustituto ejecuta asigsvalipol_emi map: "+parameters);
        WrapperResultados res=this.returnBackBoneInvoke(parameters, ProcesoDAO.EJECUTA_SIGSVALIPOL_EMI);
        log.debug("### kernel sustituto ejecuta asigsvalipol_emi id:"+res.getMsgId());
        log.debug("### kernel sustituto ejecuta asigsvalipol_emi mesage:"+res.getMsgText());
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
        log.debug("### kernel sustituto obtenerResultadosCotizacion lista="+lista);
        log.debug("### kernel sustituto obtenerResultadosCotizacion lista size: "+lista.size());
        return lista;
    }
    
    public List<Map<String,String>> obtenerResultadosCotizacion2(Map<String,String> params) throws ApplicationException
    {
        log.debug("### kernel sustituto obtenerResultadosCotizacion2 map: "+params);
        List<Map<String,String>> lista= this.getAllBackBoneInvoke(params, ProcesoDAO.OBTENER_RESULTADOS_COTIZACION2);
        lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
        log.debug("### kernel sustituto obtenerResultadosCotizacion2 lista size: "+lista.size());
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
    
    public List<Tatri> obtenerTatrisit(String cdtipsit) throws ApplicationException
    {
        Map<String,Object> parameters=new HashMap<String,Object>(0);
        parameters.put("pv_cdtipsit_i",cdtipsit);
        log.debug("### kernel sustituto obtenerTatrisit map: "+parameters);
        List<Tatri> lista= this.getAllBackBoneInvoke(parameters, ProcesoDAO.OBTENER_TATRISIT);
        lista=lista!=null?lista:new ArrayList<Tatri>(0);
        log.debug("### kernel sustituto obtenerTatrisit lista size: "+lista.size());
        return lista;
    }
    
    public List<Tatri> obtenerTatripol(String args[]) throws ApplicationException
    {
        Map<String,Object> parameters=new HashMap<String,Object>(0);
        parameters.put("pv_cdramo",args[0]);
        log.debug("### kernel sustituto obtenerTatripol map: "+parameters);
        List<Tatri> lista= this.getAllBackBoneInvoke(parameters, ProcesoDAO.OBTENER_TATRIPOL);
        lista=lista!=null?lista:new ArrayList<Tatri>(0);
        log.debug("### kernel sustituto obtenerTatripol lista size: "+lista.size());
        return lista;
    }
    
    public List<Tatri> obtenerTatrigar(Map<String,String>parameters) throws ApplicationException
    {
        log.debug("### kernel sustituto obtenerTatrigar map: "+parameters);
        List<Tatri> lista= this.getAllBackBoneInvoke(parameters, ProcesoDAO.OBTENER_TATRIGAR);
        lista=lista!=null?lista:new ArrayList<Tatri>(0);
        log.debug("### kernel sustituto obtenerTatrigar lista size: "+lista.size());
        return lista;
    }
    
    public List<Tatri> obtenerTatriper(Map<String,String>parameters) throws ApplicationException
    {
        log.debug("### kernel sustituto obtenerTatriper map: "+parameters);
        List<Tatri> lista= this.getAllBackBoneInvoke(parameters, ProcesoDAO.OBTENER_TATRIPER);
        lista=lista!=null?lista:new ArrayList<Tatri>(0);
        log.debug("### kernel sustituto obtenerTatriper lista size: "+lista.size());
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
    
    public WrapperResultados movDetalleSuplemento(Map<String,Object> parameters) throws ApplicationException
    {
    	log.debug("### kernel sustituto movDetalleSuplemento map: "+parameters);
        WrapperResultados res=this.returnBackBoneInvoke(parameters, ProcesoDAO.INSERTAR_DETALLE_SUPLEMEN);
        log.debug("### kernel sustituto movDetalleSuplemento id:"+res.getMsgId());
        log.debug("### kernel sustituto movDetalleSuplemento mesage:"+res.getMsgText());
        return res;
    }
    
    public WrapperResultados movBitacobro(String cdunieco,String cdramo,String estado,String poliza,String codigo,String mensaje, String usuario) throws ApplicationException
    {
    	Map<String,String>parameters=new LinkedHashMap<String,String>(0);
    	parameters.put("pv_cdunieco_i" , cdunieco);
    	parameters.put("pv_cdramo_i"   , cdramo);
    	parameters.put("pv_estado_i"   , estado);
    	parameters.put("pv_nmpoliza_i" , poliza);
    	parameters.put("pv_cdcodigo_i" , codigo);
    	parameters.put("pv_mensaje_i"  , mensaje);
    	parameters.put("pv_usuario_i"  , usuario);
    	log.debug("### kernel sustituto movBitacobro map: "+parameters);
        WrapperResultados res=this.returnBackBoneInvoke(parameters, ProcesoDAO.P_MOV_TBITACOBROS);
        log.debug("### kernel sustituto movBitacobro id:"+res.getMsgId());
        log.debug("### kernel sustituto movBitacobro mesage:"+res.getMsgText());
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
    
    public WrapperResultados pMovTvalopol(Map<String, String> parameters) throws ApplicationException
    {
    	String[] inputKeys=new String[]{
    			"pv_cdunieco",
        		"pv_cdramo",
        		"pv_estado",
        		"pv_nmpoliza",
        		"pv_nmsuplem",
        		"pv_status",
        		"pv_otvalor01",
        		"pv_otvalor02",
        		"pv_otvalor03",
        		"pv_otvalor04",
        		"pv_otvalor05",
        		"pv_otvalor06",
        		"pv_otvalor07",
        		"pv_otvalor08",
        		"pv_otvalor09",
        		"pv_otvalor10",
        		"pv_otvalor11",
        		"pv_otvalor12",
        		"pv_otvalor13",
        		"pv_otvalor14",
        		"pv_otvalor15",
        		"pv_otvalor16",
        		"pv_otvalor17",
        		"pv_otvalor18",
        		"pv_otvalor19",
        		"pv_otvalor20",
        		"pv_otvalor21",
        		"pv_otvalor22",
        		"pv_otvalor23",
        		"pv_otvalor24",
        		"pv_otvalor25",
        		"pv_otvalor26",
        		"pv_otvalor27",
        		"pv_otvalor28",
        		"pv_otvalor29",
        		"pv_otvalor30",
        		"pv_otvalor31",
        		"pv_otvalor32",
        		"pv_otvalor33",
        		"pv_otvalor34",
        		"pv_otvalor35",
        		"pv_otvalor36",
        		"pv_otvalor37",
        		"pv_otvalor38",
        		"pv_otvalor39",
        		"pv_otvalor40",
        		"pv_otvalor41",
        		"pv_otvalor42",
        		"pv_otvalor43",
        		"pv_otvalor44",
        		"pv_otvalor45",
        		"pv_otvalor46",
        		"pv_otvalor47",
        		"pv_otvalor48",
        		"pv_otvalor49",
        		"pv_otvalor50",
    	};
    	for(String key:inputKeys)
    	{
    		if(!parameters.containsKey(key))
    		{
    			parameters.put(key, null);
    		}
    	}
    	log.debug("### kernel sustituto pMovTvalopol map: "+parameters);
        WrapperResultados res=this.returnBackBoneInvoke(parameters, ProcesoDAO.P_MOV_TVALOPOL);
        log.debug("### kernel sustituto pMovTvalopol id:"+res.getMsgId());
        log.debug("### kernel sustituto pMovTvalopol mesage:"+res.getMsgText());
        return res;
    }
    
    public WrapperResultados pMovTvalogar(Map<String, String> parameters) throws ApplicationException
    {
    	String[] inputKeys=new String[]{
				"pv_cdunieco","pv_cdramo","pv_estado","pv_nmpoliza","pv_nmsituac","pv_cdgarant","pv_nmsuplem","pv_status",
                "pv_otvalor01","pv_otvalor02","pv_otvalor03","pv_otvalor04","pv_otvalor05","pv_otvalor06","pv_otvalor07","pv_otvalor08","pv_otvalor09","pv_otvalor10",
                "pv_otvalor11","pv_otvalor12","pv_otvalor13","pv_otvalor14","pv_otvalor15","pv_otvalor16","pv_otvalor17","pv_otvalor18","pv_otvalor19","pv_otvalor20",
                "pv_otvalor21","pv_otvalor22","pv_otvalor23","pv_otvalor24","pv_otvalor25","pv_otvalor26","pv_otvalor27","pv_otvalor28","pv_otvalor29","pv_otvalor30",
                "pv_otvalor31","pv_otvalor32","pv_otvalor33","pv_otvalor34","pv_otvalor35","pv_otvalor36","pv_otvalor37","pv_otvalor38","pv_otvalor39","pv_otvalor40",
                "pv_otvalor41","pv_otvalor42","pv_otvalor43","pv_otvalor44","pv_otvalor45","pv_otvalor46","pv_otvalor47","pv_otvalor48","pv_otvalor49","pv_otvalor50"
		};
    	for(String key:inputKeys)
    	{
    		if(!parameters.containsKey(key))
    		{
    			parameters.put(key, null);
    		}
    	}
    	log.debug("### kernel sustituto pMovTvalogar map: "+parameters);
        WrapperResultados res=this.returnBackBoneInvoke(parameters, ProcesoDAO.P_MOV_TVALOGAR);
        log.debug("### kernel sustituto pMovTvalogar id:"+res.getMsgId());
        log.debug("### kernel sustituto pMovTvalogar mesage:"+res.getMsgText());
        return res;
    }
    
    public WrapperResultados pMovTvaloper(Map<String, String> parameters) throws ApplicationException
    {
    	String[] inputKeys=new String[]{
				"pv_cdunieco","pv_cdramo","pv_estado","pv_nmpoliza","pv_nmsituac","pv_nmsuplem","pv_status","pv_cdrol","pv_cdperson","pv_cdatribu","pv_cdtipsit",
                "pv_otvalor01","pv_otvalor02","pv_otvalor03","pv_otvalor04","pv_otvalor05","pv_otvalor06","pv_otvalor07","pv_otvalor08","pv_otvalor09","pv_otvalor10",
                "pv_otvalor11","pv_otvalor12","pv_otvalor13","pv_otvalor14","pv_otvalor15","pv_otvalor16","pv_otvalor17","pv_otvalor18","pv_otvalor19","pv_otvalor20",
                "pv_otvalor21","pv_otvalor22","pv_otvalor23","pv_otvalor24","pv_otvalor25","pv_otvalor26","pv_otvalor27","pv_otvalor28","pv_otvalor29","pv_otvalor30",
                "pv_otvalor31","pv_otvalor32","pv_otvalor33","pv_otvalor34","pv_otvalor35","pv_otvalor36","pv_otvalor37","pv_otvalor38","pv_otvalor39","pv_otvalor40",
                "pv_otvalor41","pv_otvalor42","pv_otvalor43","pv_otvalor44","pv_otvalor45","pv_otvalor46","pv_otvalor47","pv_otvalor48","pv_otvalor49","pv_otvalor50"
		};
    	for(String key:inputKeys)
    	{
    		if(!parameters.containsKey(key))
    		{
    			parameters.put(key, null);
    		}
    	}
    	log.debug("### kernel sustituto pMovTvaloper map: "+parameters);
        WrapperResultados res=this.returnBackBoneInvoke(parameters, ProcesoDAO.P_MOV_TVALOPER);
        log.debug("### kernel sustituto pMovTvaloper id:"+res.getMsgId());
        log.debug("### kernel sustituto pMovTvaloper mesage:"+res.getMsgText());
        return res;
    }
    
    //requiere de su propio catch si no hay datos:
    public Map<String,Object> pGetTvalopol(Map<String,String> parameters) throws ApplicationException {
        log.debug("### kernel sustituto pGetTvalopol map: "+parameters);
        Map<String,Object>map=(Map<String,Object>) this.getBackBoneInvoke(parameters, ProcesoDAO.P_GET_TVALOPOL);
        log.debug("### kernel sustituto pGetTvalopol response map: "+map);
        return map;
    }
    
    public String generaCdperson() throws ApplicationException
    {
        log.debug("### kernel sustituto generaCdperson");
        WrapperResultados res=this.returnBackBoneInvoke(new HashMap<String,String>(0), ProcesoDAO.GENERA_MPERSON);
        log.debug("### kernel sustituto generaCdperson cdperson generado:"+res.getItemMap().get("CDPERSON"));
        log.debug("### kernel sustituto generaCdperson id:"+res.getMsgId());
        log.debug("### kernel sustituto generaCdperson mesage:"+res.getMsgText());
        return (String)res.getItemMap().get("CDPERSON");
    }
    
    public WrapperResultados movMpersona(Map<String,Object> parameters) throws ApplicationException
    {
    	log.debug("### kernel sustituto movMpersona map: "+parameters);
        WrapperResultados res=this.returnBackBoneInvoke(parameters, ProcesoDAO.P_MOV_MPERSONA);
        log.debug("### kernel sustituto movMpersona id:"+res.getMsgId());
        log.debug("### kernel sustituto movMpersona mesage:"+res.getMsgText());
        return res;
    }
    
    public WrapperResultados movMpoliper(Map<String,Object> parameters) throws ApplicationException
    {
    	log.debug("### kernel sustituto movMpoliper map: "+parameters);
        WrapperResultados res=this.returnBackBoneInvoke(parameters, ProcesoDAO.P_MOV_MPOLIPER);
        log.debug("### kernel sustituto movMpoliper id:"+res.getMsgId());
        log.debug("### kernel sustituto movMpoliper mesage:"+res.getMsgText());
        return res;
    }
    
    public WrapperResultados borraMpoliper(Map<String,Object> parameters) throws ApplicationException
    {
    	log.debug("### kernel sustituto borraMpoliper map: "+parameters);
        WrapperResultados res=this.returnBackBoneInvoke(parameters, ProcesoDAO.P_BORRA_MPOLIPER);
        log.debug("### kernel sustituto borraMpoliper id:"+res.getMsgId());
        log.debug("### kernel sustituto borraMpoliper mesage:"+res.getMsgText());
        return res;
    }
    
    public List<Map<String,String>> obtenerCoberturasUsuario(Map<String,String> parametros) throws ApplicationException
    {
    	log.debug("### kernel sustituto obtenerCoberturasUsuario parametros: "+parametros);
        List<Map<String,String>> lista= this.getAllBackBoneInvoke(parametros, ProcesoDAO.OBTENER_COBERTURAS_USUARIO);
        lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
        log.debug("### kernel sustituto obtenerCoberturasUsuario lista size: "+lista.size());
        return lista;
    }
    
    public WrapperResultados movPoligar(Map<String, String> param) throws ApplicationException
    {
    	log.debug("### kernel sustituto movPoligar map: "+param);
        WrapperResultados res=this.returnBackBoneInvoke(param, ProcesoDAO.P_MOV_MPOLIGAR);
        log.debug("### kernel sustituto movPoligar id:"+res.getMsgId());
        log.debug("### kernel sustituto movPoligar mesage:"+res.getMsgText());
        return res;
    }
    
	public WrapperResultados movPolicap(Map<String, String> param) throws ApplicationException
	{
		log.debug("### kernel sustituto movPolicap map: "+param);
        WrapperResultados res=this.returnBackBoneInvoke(param, ProcesoDAO.P_MOV_MPOLICAP);
        log.debug("### kernel sustituto movPolicap id:"+res.getMsgId());
        log.debug("### kernel sustituto movPolicap mesage:"+res.getMsgText());
        return res;
	}
	
	public List<Map<String, String>> obtenerDetallesCotizacion(Map<String, String> params) throws ApplicationException
	{
		log.debug("### kernel sustituto obtenerDetallesCotizacion parametros: "+params);
        List<Map<String,String>> lista= this.getAllBackBoneInvoke(params, ProcesoDAO.OBTENER_DETALLES_COTIZACION);
        lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
        log.debug("### kernel sustituto obtenerDetallesCotizacion lista size: "+lista.size());
        return lista;
	}
    
	public Map<String, Object> obtenerValoresTatrigar(Map<String, String> parameters) throws ApplicationException
	{
		log.debug("### kernel sustituto obtenerValoresTatrigar map: "+parameters);
        Map<String,Object>map=(Map<String,Object>) this.getBackBoneInvoke(parameters, ProcesoDAO.P_GET_TVALOGAR);
        log.debug("### kernel sustituto obtenerValoresTatrigar response map: "+map);
        return map;
	}
	
	public Map<String, Object> obtenerValoresTatriper(Map<String, String> parameters) throws ApplicationException
	{
		log.debug("### kernel sustituto obtenerValoresTatriper map: "+parameters);
        Map<String,Object>map=(Map<String,Object>) this.getBackBoneInvoke(parameters, ProcesoDAO.P_GET_TVALOPER);
        log.debug("### kernel sustituto obtenerValoresTatriper response map: "+map);
        return map;
	}
	
	public Map<String, String> obtenerDomicilio(Map<String, String> params) throws ApplicationException
	{
		log.debug("### kernel sustituto obtenerDomicilio map: "+params);
        Map<String,String>map=(Map<String,String>) this.getBackBoneInvoke(params, ProcesoDAO.P_GET_DOMICIL);
        log.debug("### kernel sustituto obtenerDomicilio response map: "+map);
        return map;
	}
	
	public WrapperResultados pMovMdomicil(Map<String, String> param) throws ApplicationException
	{
		/*
		pv_cdperson_i smap1.pv_cdperson
		pv_nmorddom_i smap1.NMORDDOM
		pv_msdomici_i smap1.DSDOMICI qwe
		pv_nmtelefo_i smap1.NMTELEFO
		pv_cdpostal_i smap1.CODPOSTAL
		pv_cdedo_i    smap1.CDEDO
		pv_cdmunici_i smap1.CDMUNICI
		pv_cdcoloni_i smap1.CDCOLONI
		pv_nmnumero_i smap1.NMNUMERO
		pv_nmnumint_i smap1.NMNUMINT
		pv_accion_i   #U
		pv_msg_id_o   -
		pv_title_o    -
		*/
		log.debug("### kernel sustituto pMovMdomicil map: "+param);
        WrapperResultados res=this.returnBackBoneInvoke(param, ProcesoDAO.P_MOV_MDOMICIL);
        log.debug("### kernel sustituto pMovMdomicil id:"+res.getMsgId());
        log.debug("### kernel sustituto pMovMdomicil mesage:"+res.getMsgText());
        return res;
	}
	
	public WrapperResultados emitir(Map<String, Object> param) throws ApplicationException
	{
		log.debug("### kernel sustituto emitir map: "+param);
        WrapperResultados res=this.returnBackBoneInvoke(param, ProcesoDAO.EMITIR);
        log.debug("### kernel sustituto emitir id:"+res.getMsgId());
        log.debug("### kernel sustituto emitir mesage:"+res.getMsgText());
        return res;
	}

	public WrapperResultados guardarArchivo(Map<String, Object> param) throws ApplicationException
	{
		log.debug("### kernel sustituto guardarArchivo map: "+param);
        WrapperResultados res=this.returnBackBoneInvoke(param, ProcesoDAO.GUARDAR_ARCHIVO_POLIZA);
        log.debug("### kernel sustituto guardarArchivo id:"+res.getMsgId());
        log.debug("### kernel sustituto guardarArchivo mesage:"+res.getMsgText());
        return res;
	}
	
	public List<Map<String,String>>obtenerDocumentosPoliza(Map<String,Object>parameters) throws ApplicationException
	{
		log.debug("### kernel sustituto obtenerDocumentosPoliza parameters: "+parameters);
        List<Map<String,String>> lista= this.getAllBackBoneInvoke(parameters, ProcesoDAO.OBTENER_DOCUMENTOS_POLIZA);
        lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
        log.debug("### kernel sustituto obtenerDocumentosPoliza lista size: "+lista.size());
        return lista;
	}
	
	public List<Map<String, String>> obtenerListaDocumentos(Map<String, String> parameters) throws ApplicationException
	{
		log.debug("### kernel sustituto obtenerListaDocumentos parameters: "+parameters);
        List<Map<String,String>> lista= this.getAllBackBoneInvoke(parameters, ProcesoDAO.OBTENER_LISTA_DOC_POLIZA_NUEVA);
        lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
        log.debug("### kernel sustituto obtenerListaDocumentos lista size: "+lista.size());
        return lista;
	}
	
	public WrapperResultados insertaMaestroHistoricoPoliza(Map<String, Object> param) throws ApplicationException
	{
		log.debug("### kernel sustituto insertaMaestroHistoricoPoliza map: "+param);
        WrapperResultados res=this.returnBackBoneInvoke(param, ProcesoDAO.INSERTA_MAESTRO_HISTORICO_POLIZAS);
        log.debug("### kernel sustituto insertaMaestroHistoricoPoliza id:"+res.getMsgId());
        log.debug("### kernel sustituto insertaMaestroHistoricoPoliza mesage:"+res.getMsgText());
        return res;
	}
	
	public WrapperResultados movMPoliage(Map<String, Object> param) throws ApplicationException
	{
		log.debug("### kernel Mov Poliage map: "+param);
        WrapperResultados res=this.returnBackBoneInvoke(param, ProcesoDAO.MOV_MPOLIAGE);
        log.debug("### kernel sustituto guardMov PoliagearArchivo id:"+res.getMsgId());
        log.debug("### kernel sustituto Mov Poliage mesage:"+res.getMsgText());
        return res;
	}
	
	public List<Map<String, String>> cargarTiposClausulasExclusion() throws ApplicationException
	{
		log.debug("### kernel sustituto cargarTiposClausulasExclusion sin parametros");
        List<Map<String,String>> lista= this.getAllBackBoneInvoke(new HashMap<String,String>(0), ProcesoDAO.OBTENER_TIPOS_CLAUSULAS_EXCLUSION);
        lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
        log.debug("### kernel sustituto cargarTiposClausulasExclusion lista size: "+lista.size());
        return lista;
	}
	
	public List<Map<String, String>> obtenerExclusionesPorTipo(Map<String, String> params) throws ApplicationException
	{
		log.debug("### kernel sustituto obtenerExclusionesPorTipo params: "+params);
        List<Map<String,String>> lista= this.getAllBackBoneInvoke(params, ProcesoDAO.OBTENER_EXCLUSIONES_X_TIPO);
        lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
        log.debug("### kernel sustituto obtenerExclusionesPorTipo lista size: "+lista.size());
        return lista;
	}
	
	public Map<String, String> obtenerHtmlClausula(Map<String, String> params) throws ApplicationException
	{
        log.debug("### kernel sustituto obtenerHtmlClausula map: "+params);
        Map<String,String>map=(Map<String,String>) this.getBackBoneInvoke(params, ProcesoDAO.OBTENER_HTML_CLAUSULA);
        log.debug("### kernel sustituto obtenerHtmlClausula response map: "+map);
        return map;
	}
	
	public WrapperResultados PMovMpolicot(Map<String, String> param) throws ApplicationException
	{
		log.debug("### kernel PMovMpolicot map: "+param);
        WrapperResultados res=this.returnBackBoneInvoke(param, ProcesoDAO.P_MOV_MPOLICOT);
        log.debug("### kernel sustituto PMovMpolicot id:"+res.getMsgId());
        log.debug("### kernel sustituto PMovMpolicot mesage:"+res.getMsgText());
        return res;
	}

	public List<Map<String, String>> obtenerPolicot(Map<String, String> params) throws ApplicationException {
		
		log.debug("### kernel sustituto obtenerPolicot params: "+params);
        List<Map<String,String>> lista= this.getAllBackBoneInvoke(params, ProcesoDAO.OBTENER_POLICOT);
        lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
        log.debug("### kernel sustituto obtenerPolicot lista size: "+lista.size());
        return lista;
	}
	
	public WrapperResultados PMovMesacontrol(Map<String, Object> param) throws ApplicationException
	{
		log.debug("### kernel PMovMesacontrol map: "+param);
        WrapperResultados res=this.returnBackBoneInvoke(param, ProcesoDAO.P_MOV_MESACONTROL);
        log.debug("### kernel sustituto PMovMesacontrol id:"+res.getMsgId());
        log.debug("### kernel sustituto PMovMesacontrol mesage:"+res.getMsgText());
        return res;
	}
	
	public WrapperResultados movDmesacontrol(Map<String, Object> params) throws ApplicationException
	{
		log.debug("### kernel movDmesacontrol map: "+params);
        WrapperResultados res=this.returnBackBoneInvoke(params, ProcesoDAO.P_MOV_DMESACONTROL);
        log.debug("### kernel sustituto movDmesacontrol id:"+res.getMsgId());
        log.debug("### kernel sustituto movDmesacontrol mesage:"+res.getMsgText());
        return res;
	}
	
	public List<Map<String, String>> loadMesaControl(Map<String,String> param) throws ApplicationException
	{
		log.debug("### kernel sustituto loadMesaControl map: "+param);
		/////////////////////////////////////////////////////////////////
		////// transformacion de mapa de strings a mapa de objetos //////
		/*/////////////////////////////////////////////////////////////*
		Map<String,Object>omap=new LinkedHashMap<String,Object>(0);
		SimpleDateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
		Iterator it=param.entrySet().iterator();
		while(it.hasNext())
		{
			Entry en=(Entry)it.next();
			if(en.getKey().equals("pv_fedesde_i")||en.getKey().equals("pv_fehasta_i"))
			{
				if(en.getValue()!=null&&((String)en.getValue()).length()>0)
				{
					try
					{
						omap.put(en.getKey()+"",renderFechas.parse((String)en.getValue()));//poner fecha
					}
					catch(Exception ex)
					{
						log.error("error al convertir cadena a fecha",ex);
						omap.put(en.getKey()+"",null);
					}
				}
				else
				{
					omap.put(en.getKey()+"",null);//poner el nulo o cadena vacia
				}
			}
			else
			{
				omap.put(en.getKey()+"",en.getValue());//poner cadena
			}
		}
		/*/////////////////////////////////////////////////////////////*/
		////// transformacion de mapa de strings a mapa de objetos //////
		/////////////////////////////////////////////////////////////////
        List<Map<String,String>> lista= this.getAllBackBoneInvoke(param, ProcesoDAO.LOAD_MESA_CONTROL);
        lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
        log.debug("### kernel sustituto loadMesaControl lista size: "+lista.size());
        return lista;
	}
	
	public List<Map<String, String>> loadMesaControlSuper(Map<String,String>params) throws ApplicationException
	{
		log.debug("### kernel sustituto loadMesaControlSuper map: "+params);
        List<Map<String,String>> lista= this.getAllBackBoneInvoke(params, ProcesoDAO.P_OBTIENE_MESACONTROL_SUPER);
        lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
        log.debug("### kernel sustituto loadMesaControlSuper lista size: "+lista.size());
        return lista;
	}
	
	public List<Map<String, String>> obtenerDetalleMC(Map<String, String> param) throws ApplicationException
	{
		log.debug("### kernel sustituto obtenerDetalleMC map: "+param);
        List<Map<String,String>> lista= this.getAllBackBoneInvoke(param, ProcesoDAO.LOAD_DETALLE_MESA_CONTROL);
        lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
        log.debug("### kernel sustituto obtenerDetalleMC lista size: "+lista.size());
        return lista;
	}

	public WrapperResultados obtenDatosRecibos(HashMap<String,Object> params) throws ApplicationException
	{
		WrapperResultados result = this.returnBackBoneInvoke(params,
				ProcesoDAO.OBTIENE_DATOS_RECIBOS);
		return result;
	}

	public WrapperResultados obtenDatosRecibosDxN(HashMap<String,Object> params) throws ApplicationException
	{
		WrapperResultados result = this.returnBackBoneInvoke(params,
				ProcesoDAO.OBTIENE_DATOS_RECIBOS_DxN);
		return result;
	}

	public WrapperResultados obtenDatosClienteWS(HashMap<String,Object> params) throws ApplicationException
	{
		WrapperResultados result = this.returnBackBoneInvoke(params,
				ProcesoDAO.OBTIENE_DATOS_CLIENTE);
		return result;
	}
	
	public WrapperResultados cargaColonias(String codigoPostal) throws ApplicationException
	{
		HashMap<String,Object> params =  new HashMap<String, Object>();
		params.put("pv_codpostal_i", codigoPostal);
		WrapperResultados result = this.returnBackBoneInvoke(params,
				ProcesoDAO.OBTIENE_CATALOGO_COLONIAS);
		return result;
	}

	public WrapperResultados mesaControlUpdateSolici(String ntramite,String nmsolici) throws ApplicationException
	{
		HashMap<String,Object> params =  new HashMap<String, Object>();
		params.put("pv_ntramite_i", ntramite);
		params.put("pv_nmsolici_i", nmsolici);
		log.debug("### kernel mesaControlUpdateSolici map: "+params);
		WrapperResultados res = this.returnBackBoneInvoke(params,ProcesoDAO.MESACONTROL_UPDATE_SOLICI);
        log.debug("### kernel sustituto mesaControlUpdateSolici id:"+res.getMsgId());
        log.debug("### kernel sustituto mesaControlUpdateSolici mesage:"+res.getMsgText());
		return res;
		
	}
	
	public WrapperResultados mesaControlUpdateStatus(String ntramite,String status) throws ApplicationException
	{
		HashMap<String,Object> params =  new HashMap<String, Object>();
		params.put("pv_ntramite_i", ntramite);
		params.put("pv_status_i", status);
		log.debug("### kernel mesaControlUpdateStatus map: "+params);
		WrapperResultados res = this.returnBackBoneInvoke(params,ProcesoDAO.MESACONTROL_UPDATE_STATUS);
        log.debug("### kernel sustituto mesaControlUpdateStatus id:"+res.getMsgId());
        log.debug("### kernel sustituto mesaControlUpdateStatus mesage:"+res.getMsgText());
		return res;
	}
	
	public WrapperResultados mesaControlFinalizarDetalle(Map<String, String> smap1) throws ApplicationException
	{
		Iterator it=smap1.entrySet().iterator();
		HashMap<String,Object> params =  new HashMap<String, Object>();
		while(it.hasNext())
		{
			Entry en=(Entry) it.next();
			params.put((String)en.getKey(),(String)en.getValue());
		}
		params.put("pv_fechafin_i",new Date());
		log.debug("### kernel mesaControlFinalizarDetalle map: "+params);
		WrapperResultados res = this.returnBackBoneInvoke(params,ProcesoDAO.MESACONTROL_FINALIZAR_DETALLE);
        log.debug("### kernel sustituto mesaControlFinalizarDetalle id:"+res.getMsgId());
        log.debug("### kernel sustituto mesaControlFinalizarDetalle mesage:"+res.getMsgText());
		return res;
	}
	
	public WrapperResultados preparaContrarecibo(Map<String, String> params) throws ApplicationException
	{
		log.debug("### kernel preparaContrarecibo map: "+params);
		WrapperResultados res = this.returnBackBoneInvoke(params,ProcesoDAO.DOCUMENTOS_PREPARAR_CONTRARECIBO);
        log.debug("### kernel sustituto preparaContrarecibo id:"+res.getMsgId());
        log.debug("### kernel sustituto preparaContrarecibo mesage:"+res.getMsgText());
		return res;
	}
	
	public Map<String, Object> obtieneValositSituac(Map<String, String> params) throws ApplicationException
	{
		log.debug("### kernel sustituto obtieneValositSituac map: "+params);
        Map<String,Object> map=(Map<String,Object>) this.getBackBoneInvoke(params, ProcesoDAO.OBTIENE_VALOSIT_SITUAC);
        log.debug("### kernel sustituto obtieneValositSituac response map: "+map);
        return map;
	}
	
	public List<Map<String, String>> buscarRFC(Map<String,String> params) throws ApplicationException
	{
		log.debug("### kernel sustituto buscarRFC map: "+params);
        List<Map<String,String>> lista= this.getAllBackBoneInvoke(params, ProcesoDAO.BUSCAR_RFC);
        lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
        log.debug("### kernel sustituto buscarRFC lista size: "+lista.size());
        return lista;
	}
	
	public WrapperResultados borrarMpoliper(Map<String, String> param) throws ApplicationException
	{
		log.debug("### kernel borrarMpersona map: "+param);
		WrapperResultados res = this.returnBackBoneInvoke(param,ProcesoDAO.BORRAR_MPERSONA);
        log.debug("### kernel borrarMpersona id:"+res.getMsgId());
        log.debug("### kernel borrarMpersona mesage:"+res.getMsgText());
		return res;
	}
	
	public List<Map<String, String>> obtenerRamos(String cdunieco) throws ApplicationException
	{
		Map<String,String>params=new LinkedHashMap<String,String>(0);
		params.put("pv_cdunieco_i",cdunieco);
		log.debug("### kernel sustituto obtenerRamos map: "+params);
        List<Map<String,String>> lista= this.getAllBackBoneInvoke(params, ProcesoDAO.OBTENER_RAMOS);
        lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
        log.debug("### kernel sustituto obtenerRamos lista size: "+lista.size());
        return lista;
	}
	
	public List<Map<String, String>> obtenerTipsit(String cdramo) throws ApplicationException
	{
		Map<String,String>params=new LinkedHashMap<String,String>(0);
		params.put("pv_cdramo_i",cdramo);
		log.debug("### kernel sustituto obtenerTipsit map: "+params);
        List<Map<String,String>> lista= this.getAllBackBoneInvoke(params, ProcesoDAO.OBTENER_TIPSIT);
        lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
        log.debug("### kernel sustituto obtenerTipsit lista size: "+lista.size());
        return lista;
	}
	
	public List<Map<String, String>> PValInfoPersonas(Map<String,String> params) throws ApplicationException
	{
		log.debug("### kernel sustituto PValInfoPersonas map: "+params);
        List<Map<String,String>> lista= this.getAllBackBoneInvoke(params, ProcesoDAO.P_VAL_INFO_PERSONAS);
        lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
        log.debug("### kernel sustituto PValInfoPersonas lista size: "+lista.size());
        return lista;
	}


	public WrapperResultados obtenerAgentePoliza(String cdunieco,
			String cdramo, String estado, String nmpoliza)
			throws ApplicationException {		
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("pi_CDUNIECO", cdunieco);
			params.put("pi_CDRAMO", cdramo);
			params.put("pi_ESTADO", estado);
			params.put("pi_NMPOLIZA", nmpoliza);
	
			WrapperResultados result = this.returnBackBoneInvoke(params,
					ProcesoDAO.OBTIENE_DATOS_POLIZA_AGENTE);
			return result;
	}

	public WrapperResultados obtenerTiposAgente() throws ApplicationException {
		HashMap<String, Object> params = new HashMap<String, Object>();		
		WrapperResultados result = this.returnBackBoneInvoke(params,
				ProcesoDAO.OBTIENE_DATOS_GENERAL_AGENTE);
		return result;
	}

	public WrapperResultados guardarPorcentajeAgentes(Map<String, Object> params)
			throws ApplicationException {
		WrapperResultados result = this.returnBackBoneInvoke(params,
				ProcesoDAO.GUARDA_PORCENTAJE_POLIZA);
		return result;
	}

	public WrapperResultados guardaPeriodosDxN(Map<String, Object> params)
			throws ApplicationException {
		WrapperResultados result = this.returnBackBoneInvoke(params,
				ProcesoDAO.GUARDA_PERIODOS_DXN);
		return result;
	}

	public WrapperResultados lanzaProcesoDxN(Map<String, Object> params)
			throws ApplicationException {
		WrapperResultados result = this.returnBackBoneInvoke(params,
				ProcesoDAO.LANZA_PROCESO_DXN);
		return result;
	}
	
	public WrapperResultados validarExtraprima(Map<String, String> params) throws ApplicationException
	{
		log.debug("### kernel validarExtraprima map: "+params);
		WrapperResultados res = this.returnBackBoneInvoke(params,ProcesoDAO.VALIDAR_EXTRAPRIMA);
		log.debug("### kernel sustituto validarExtraprima status:"+res.getItemMap().get("status"));
        log.debug("### kernel sustituto validarExtraprima id:"+res.getMsgId());
        log.debug("### kernel sustituto validarExtraprima mesage:"+res.getMsgText());
		return res;
	}
	
}