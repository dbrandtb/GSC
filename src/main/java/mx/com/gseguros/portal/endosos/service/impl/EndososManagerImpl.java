package mx.com.gseguros.portal.endosos.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import mx.com.gseguros.portal.endosos.service.EndososManager;

import org.apache.log4j.Logger;

public class EndososManagerImpl implements EndososManager
{
    private static Logger log = Logger.getLogger(EndososManagerImpl.class);
    
	private EndososDAO endososDAO;

	@Override
	public List<Map<String, String>> obtenerEndosos(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager obtenerEndosos params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerEndosos(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtenerEndosos lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public Map<String, String> guardarEndosoNombres(Map<String, Object> params) throws Exception
	{
		log.debug("EndososManager guardarEndosoNombres params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoNombres(params);
		log.debug("EndososManager guardarEndosoNombres response map: "+mapa);
        return mapa;
	}
	
	@Override
	public Map<String, String> confirmarEndosoB(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager confirmarEndosoB params: "+params);
		Map<String,String> mapa=endososDAO.confirmarEndosoB(params);
		log.debug("EndososManager confirmarEndosoB response map: "+mapa);
        return mapa;
	}
	
	@Override
	public Map<String, String> guardarEndosoDomicilio(Map<String, Object> params) throws Exception
	{
		log.debug("EndososManager guardarEndosoDomicilio params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoNombres(params);
		log.debug("EndososManager guardarEndosoDomicilio response map: "+mapa);
        return mapa;
	}
	
	@Override
	public List<Map<String, String>> reimprimeDocumentos(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager reimprimeDocumentos params: "+params);
		List<Map<String,String>> lista=endososDAO.reimprimeDocumentos(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager reimprimeDocumentos lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public List<Map<String, String>> obtieneCoberturasDisponibles(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager obtieneCoberturasDisponibles params: "+params);
		List<Map<String,String>> lista=endososDAO.obtieneCoberturasDisponibles(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtieneCoberturasDisponibles lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public Map<String, String> guardarEndosoCoberturas(Map<String, Object> params) throws Exception
	{
		log.debug("EndososManager guardarEndosoCoberturas params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoCoberturas(params);
		log.debug("EndososManager guardarEndosoCoberturas response map: "+mapa);
        return mapa;
	}
	
	@Override
	public List<Map<String, String>> obtenerAtributosCoberturas(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager obtenerAtributosCoberturas params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerAtributosCoberturas(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtenerAtributosCoberturas lista size: "+lista.size());
		return lista;
	}
	
	//PKG_COTIZA.P_EJECUTA_SIGSVALIPOL_END
	@Override
	public Map<String,Object> sigsvalipolEnd(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager sigsvalipolEnd params: "+params);
		Map<String,Object> mapa=endososDAO.sigsvalipolEnd(params);
		log.debug("EndososManager sigsvalipolEnd response map: "+mapa);
        return mapa;
	}
	
	@Override
	public Map<String,Object> sigsvalipolEnd(
			String cdusuari
			,String cdelemento
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdtipsit
			,String cdtipsup
			) throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>(0);
		params.put("pv_cdusuari_i" , cdusuari);
		params.put("pv_cdelemen_i" , cdelemento);
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_nmsituac_i" , nmsituac);
		params.put("pv_nmsuplem_i" , nmsuplem);
		params.put("pv_cdtipsit_i" , cdtipsit);
		params.put("pv_cdtipsup_i" , cdtipsup);
		return this.sigsvalipolEnd(params);
	}
	
	@Override
	public Map<String, String> guardarEndosoClausulas(Map<String, Object> params) throws Exception
	{
		log.debug("EndososManager guardarEndosoClausulas params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoClausulas(params);
		log.debug("EndososManager guardarEndosoClausulas response map: "+mapa);
        return mapa;
	}
	
	//PKG_ENDOSOS.P_CALC_VALOR_ENDOSO
	@Override
	public Map<String,String> calcularValorEndoso(Map<String,Object>params) throws Exception
	{
		log.debug("EndososManager calcularValorEndoso params: "+params);
		Map<String,String> mapa=endososDAO.calcularValorEndoso(params);
		log.debug("EndososManager calcularValorEndoso response map: "+mapa);
        return mapa;
	}
	
	@Override
	public Map<String,String> calcularValorEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,Date   feinival
			,String cdtipsup) throws Exception
	{
		Map<String,Object>params=new HashMap<String,Object>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_nmsituac_i" , nmsituac);
		params.put("pv_nmsuplem_i" , nmsuplem);
		params.put("pv_feinival_i" , feinival);
		params.put("pv_cdtipsup_i" , cdtipsup);
        return this.calcularValorEndoso(params);
	}
	
	//PKG_ENDOSOS.P_ENDOSO_INICIA
	@Override
	public Map<String,String> iniciarEndoso(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager iniciarEndoso params: "+params);
		Map<String,String> mapa=endososDAO.iniciarEndoso(params);
		log.debug("EndososManager iniciarEndoso response map: "+mapa);
        return mapa;
	}
	
	//PKG_ENDOSOS.P_ENDOSO_INICIA
	@Override
	public Map<String,String> iniciarEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String fecha
			,String cdelemento
			,String cdusuari
			,String proceso
			,String cdtipsup) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_fecha_i"    , fecha);
		params.put("pv_cdelemen_i" , cdelemento);
		params.put("pv_cdusuari_i" , cdusuari);
		params.put("pv_proceso_i"  , proceso);
		params.put("pv_cdtipsup_i" , cdtipsup);
        return this.iniciarEndoso(params);
	}
	
	@Override
	public void insertarTworksupEnd(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager insertarTworksupEnd params: "+params);
		endososDAO.insertarTworksupEnd(params);
		log.debug("EndososManager insertarTworksupEnd end");
	}
	
	@Override
	public void insertarTworksupEnd(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			,String nmsuplem
			,String nmsituac) throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>(0);
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_cdtipsup_i" , cdtipsup);
		params.put("pv_nmsuplem_i" , nmsuplem);
		params.put("pv_nmsituac_i" , nmsituac);
        this.insertarTworksupEnd(params);
	}
	
	//PKG_SATELITES.P_INSERTA_TWORKSUP_SIT_TODAS
	@Override
	public void insertarTworksupSitTodas(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager insertarTworksupSitTodas params: "+params);
		endososDAO.insertarTworksupSitTodas(params);
		log.debug("EndososManager insertarTworksupSitTodas end");
	}
	
	//PKG_SATELITES.P_OBTIENE_DATOS_MPOLISIT
	@Override
	public Map<String, String> obtieneDatosMpolisit(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager obtieneDatosMpolisit params: "+params);
		Map<String,String> mapa=endososDAO.obtieneDatosMpolisit(params);
		log.debug("EndososManager obtieneDatosMpolisit response map: "+mapa);
        return mapa;
	}
	
	//PKG_SATELITES.P_OBTIENE_DATOS_MPOLISIT
	@Override
	public Map<String, String> obtieneDatosMpolisit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
        return this.obtieneDatosMpolisit(params);
	}
	
	@Override
	public List<Map<String, String>> obtenerNombreEndosos() throws Exception
	{
		log.debug("EndososManager obtenerNombreEndosos");
		List<Map<String,String>> lista=endososDAO.obtenerNombreEndosos();
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtenerNombreEndosos lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public void actualizarFenacimi(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager actualizarFenacimi params: "+params);
		endososDAO.actualizarFenacimi(params);
		log.debug("EndososManager actualizarFenacimi end");
	}
	
	@Override
	public void actualizarSexo(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager actualizarSexo params: "+params);
		endososDAO.actualizarSexo(params);
		log.debug("EndososManager actualizarSexo end");
	}
	
	@Override
	public List<Map<String, String>> obtenerCdpersonMpoliper(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager obtenerCdpersonMpoliper params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerCdpersonMpoliper(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtenerCdpersonMpoliper lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public String obtenerNtramiteEmision(String cdunieco,String cdramo,String estado,String nmpoliza) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		log.debug("EndososManager obtenerNtramiteEmision params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerNtramiteEmision(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtenerNtramiteEmision lista: "+lista);
		String ntramite=lista.size()>0?lista.get(0).get("NTRAMITE"):"";
		log.debug("EndososManager obtenerNtramiteEmision ntramite: "+ntramite);
		return ntramite;
	}
	
	@Override
	public void validaEndosoAnterior(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager validaEndosoAnterior params: "+params);
		endososDAO.validaEndosoAnterior(params);
		log.debug("EndososManager validaEndosoAnterior end");
	}
	
	//PKG_ENDOSOS.P_INS_NEW_DEDUCIBLE_TVALOSIT
	@Override
	public void actualizaDeducibleValosit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String deducible) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i"  , cdunieco);
		params.put("pv_cdramo_i"    , cdramo);
		params.put("pv_estado_i"    , estado);
		params.put("pv_nmpoliza_i"  , nmpoliza);
		params.put("pv_nmsuplem_i"  , nmsuplem);
		params.put("pv_deducible_i" , deducible);
		log.debug("EndososManager actualizaDeducibleValosit params: "+params);
		endososDAO.actualizaDeducibleValosit(params);
		log.debug("EndososManager actualizaDeducibleValosit end");
	}
	
	//PKG_ENDOSOS.P_INS_NEW_DEDUCIBLE_TVALOSIT
	@Override
	public void actualizaCopagoValosit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String deducible) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i"  , cdunieco);
		params.put("pv_cdramo_i"    , cdramo);
		params.put("pv_estado_i"    , estado);
		params.put("pv_nmpoliza_i"  , nmpoliza);
		params.put("pv_nmsuplem_i"  , nmsuplem);
		params.put("pv_deducible_i" , deducible);
		log.debug("EndososManager actualizaCopagoValosit params: "+params);
		endososDAO.actualizaCopagoValosit(params);
		log.debug("EndososManager actualizaCopagoValosit end");
	}
	
	//P_CLONAR_POLIZA_REEXPED
	@Override
	public Map<String,String> pClonarPolizaReexped(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String fecha) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_feinival"   , fecha);
		log.debug("EndososManager pClonarPolizaReexped params: "+params);
		Map<String,String> mapa=endososDAO.pClonarPolizaReexped(params);
		log.debug("EndososManager pClonarPolizaReexped response map: "+mapa);
        return mapa;
	}
	
	//PKG_CONSULTA.P_OBT_VALOSIT_POR_NMSUPLEM
	/*
	CDUNIECO,CDRAMO,ESTADO,NMPOLIZA,NMSITUAC,NMSUPLEM,STATUS,CDTIPSIT,OTVALOR01,OTVALOR02
	,OTVALOR03,OTVALOR04,OTVALOR05,OTVALOR06,OTVALOR07,OTVALOR08,OTVALOR09,OTVALOR10,OTVALOR11
	,OTVALOR12,OTVALOR13,OTVALOR14,OTVALOR15,OTVALOR16,OTVALOR17,OTVALOR18,OTVALOR19,OTVALOR20
	,OTVALOR21,OTVALOR22,OTVALOR23,OTVALOR24,OTVALOR25,OTVALOR26,OTVALOR27,OTVALOR28,OTVALOR29
	,OTVALOR30,OTVALOR31,OTVALOR32,OTVALOR33,OTVALOR34,OTVALOR35,OTVALOR36,OTVALOR37,OTVALOR38
	,OTVALOR39,OTVALOR40,OTVALOR41,OTVALOR42,OTVALOR43,OTVALOR44,OTVALOR45,OTVALOR46,OTVALOR47
	,OTVALOR48,OTVALOR49,OTVALOR50
	*/
	@Override
	public List<Map<String, String>> obtenerValositPorNmsuplem(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("PV_CDUNIECO_I" , cdunieco);
		params.put("PV_CDRAMO_I"   , cdramo);
		params.put("PV_ESTADO_I"   , estado);
		params.put("PV_NMPOLIZA_I" , nmpoliza);
		params.put("PV_NMSUPLEM_I" , nmsuplem);
		log.debug("EndososManager obtenerValositPorNmsuplem params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerValositPorNmsuplem(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtenerValositPorNmsuplem lista size: "+lista.size());
		return lista;
	}
	
	/////////////////////////////////
	////// getters and setters //////
	/*/////////////////////////////*/
	public void setEndososDAO(EndososDAO endososDAO) {
		this.endososDAO = endososDAO;
	}
	
	//PKG_ENDOSOS.P_INS_NEW_EXTRAPRIMA_TVALOSIT
	@Override
	public void actualizaExtraprimaValosit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String extraprima) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i"   , cdunieco);
		params.put("pv_cdramo_i"     , cdramo);
		params.put("pv_estado_i"     , estado);
		params.put("pv_nmpoliza_i"   , nmpoliza);
		params.put("pv_nmsituac_i"   , nmsituac);
		params.put("pv_nmsuplem_i"   , nmsuplem);
		params.put("pv_extraprima_i" , extraprima);
		log.debug("EndososManager actualizaExtraprimaValosit params: "+params);
		endososDAO.actualizaExtraprimaValosit(params);
		log.debug("EndososManager actualizaExtraprimaValosit end");
	}
	
}