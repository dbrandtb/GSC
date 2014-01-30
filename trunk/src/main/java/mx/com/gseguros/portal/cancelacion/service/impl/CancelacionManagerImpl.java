package mx.com.gseguros.portal.cancelacion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cancelacion.dao.CancelacionDAO;
import mx.com.gseguros.portal.cancelacion.service.CancelacionManager;

public class CancelacionManagerImpl implements CancelacionManager
{

	private static org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger(CancelacionManagerImpl.class);
	private CancelacionDAO cancelacionDAO;
	
	@Override
	public List<Map<String, String>> buscarPolizas(Map<String, String> params) throws Exception {
		log.debug("CancelacionManager buscarPolizas params: "+params);
		List<Map<String,String>> lista=cancelacionDAO.buscarPolizas(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("CancelacionManager buscarPolizas lista size: "+lista.size());
		return lista;
	}
	
	public void setCancelacionDAO(CancelacionDAO cancelacionDAO)
	{
		this.cancelacionDAO=cancelacionDAO;
	}

	@Override
	public Map<String, String> obtenerDetalleCancelacion(Map<String, String> params) throws Exception
	{
		log.debug("CancelacionManager obtenerDetalleCancelacion params: "+params);
		Map<String,String> res=cancelacionDAO.obtenerDetalleCancelacion(params);
		log.debug("CancelacionManager obtenerDetalleCancelacion: "+res);
		return res;
	}
	
	@Override
	public List<Map<String, String>> obtenerPolizasCandidatas(Map<String, String> params) throws Exception
	{
		log.debug("CancelacionManager obtenerPolizasCandidatas params: "+params);
		
		List<Map<String,String>> lista = cancelacionDAO.obtenerPolizasCandidatas(params);
		lista                          = lista!=null?lista:new ArrayList<Map<String,String>>(0);
		
		log.debug("CancelacionManager obtenerPolizasCandidatas lista size: "+lista.size());
		
		return lista;
	}
	
	@Override
	public void seleccionaPolizas (Map<String,Object> params) throws Exception
	{
		log.debug("CancelacionManager seleccionaPolizas params: "+params);
		cancelacionDAO.seleccionaPolizas(params);
		log.debug("CancelacionManager seleccionaPolizas end");
	}
	
	//pkg_cancela.p_cancela_poliza
	@Override
	public void cancelaPoliza (Map<String,String> params) throws Exception
	{
		log.debug("CancelacionManager cancelaPoliza params: "+params);
		cancelacionDAO.cancelaPoliza(params);
		log.debug("CancelacionManager cancelaPoliza end");
	}
	
	//pkg_cancela.p_selecciona_poliza_unica
	@Override
	public void seleccionaPolizaUnica (Map<String,Object> params) throws Exception
	{
		log.debug("CancelacionManager seleccionaPolizaUnica params: "+params);
		cancelacionDAO.seleccionaPolizaUnica(params);
		log.debug("CancelacionManager seleccionaPolizaUnica end");
	}
	
	//pkg_cancela.p_selecciona_poliza_unica
	@Override
	public void seleccionaPolizaUnica (
			String cdunieco
			,String cdramo
			,String nmpoliza
			,String agencia
			,Date   fecha) throws Exception
	{
		Map<String,Object>params=new HashMap<String,Object>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_agencia_i"  , agencia);
		params.put("pv_fechapro_i" , fecha);
		this.seleccionaPolizaUnica(params);
	}
	
	@Override
	public void actualizarTagrucan (Map<String,String> params) throws Exception
	{
		log.debug("CancelacionManager actualizarTagrucan params: "+params);
		cancelacionDAO.actualizarTagrucan(params);
		log.debug("CancelacionManager actualizarTagrucan end");
	}
	
	@Override
	public void cancelacionMasiva (Map<String,String> params) throws Exception
	{
		log.debug("CancelacionManager cancelacionMasiva params: "+params);
		cancelacionDAO.cancelacionMasiva(params);
		log.debug("CancelacionManager cancelacionMasiva end");
	}
	
	//pkg_cancela.p_cancela_poliza
	@Override
	public void                     cancelaPoliza             (
			String cdunieco
			,String cdramo
			,String cduniage
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdrazon
			,String comenta
			,String feefecto
			,String fevencim
			,String fecancel
			,String cdusuari) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_cduniage_i" , cduniage);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_nmsituac_i" , nmsituac);
		params.put("pv_cdrazon_i"  , cdrazon);
		params.put("pv_comenta_i"  , comenta);
		params.put("pv_feefecto_i" , feefecto);
		params.put("pv_fevencim_i" , fevencim);
		params.put("pv_fecancel_i" , fecancel);
		params.put("pv_usuario_i"  , cdusuari);
		this.cancelaPoliza(params);
	}
	
	/**
	 * PKG_CONSULTA.P_IMP_DOC_CANCELACION
	 * @return nmsolici,nmsituac,descripc,descripl,ntramite,nmsuplem
	 */
	@Override
	public List<Map<String, String>> reimprimeDocumentos(String cdunieco,String cdramo,String estado,String nmpoliza,String tipmov) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("PV_CDUNIECO_I" , cdunieco);
		params.put("PV_CDRAMO_I"   , cdramo);
		params.put("PV_ESTADO_I"   , estado);
		params.put("PV_NMPOLIZA_I" , nmpoliza);
		params.put("PV_TIPMOV_I"   , tipmov);
		log.debug("CancelacionManager reimprimeDocumentos params: "+params);
		
		List<Map<String,String>> lista = cancelacionDAO.reimprimeDocumentos(params);
		lista                          = lista!=null?lista:new ArrayList<Map<String,String>>(0);
		
		log.debug("CancelacionManager reimprimeDocumentos lista size: "+lista.size());
		
		return lista;
	}

}