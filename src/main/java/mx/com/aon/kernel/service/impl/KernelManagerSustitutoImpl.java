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
import mx.com.gseguros.portal.cotizacion.model.Tatri;

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
        log.debug("### kernel sustituto guardarArchivo id:"+res.getMsgId());
        log.debug("### kernel sustituto guardarArchivo mesage:"+res.getMsgText());
        return res;
	}
}