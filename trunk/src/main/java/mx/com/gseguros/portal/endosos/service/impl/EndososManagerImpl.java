package mx.com.gseguros.portal.endosos.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapSmapVO;
import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import mx.com.gseguros.portal.endosos.service.EndososManager;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.model.RespuestaVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class EndososManagerImpl implements EndososManager
{
    private static final Logger logger = Logger.getLogger(EndososManagerImpl.class);
    
	private EndososDAO endososDAO;

	@Override
	public List<Map<String, String>> obtenerEndosos(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager obtenerEndosos params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerEndosos(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		logger.debug("EndososManager obtenerEndosos lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public Map<String, String> guardarEndosoNombres(Map<String, Object> params) throws Exception
	{
		logger.debug("EndososManager guardarEndosoNombres params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoNombres(params);
		logger.debug("EndososManager guardarEndosoNombres response map: "+mapa);
        return mapa;
	}
	
	@Override
	public Map<String, String> confirmarEndosoB(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager confirmarEndosoB params: "+params);
		Map<String,String> mapa=endososDAO.confirmarEndosoB(params);
		logger.debug("EndososManager confirmarEndosoB response map: "+mapa);
        return mapa;
	}
	
	@Override
	public Map<String, String> guardarEndosoDomicilio(Map<String, Object> params) throws Exception
	{
		logger.debug("EndososManager guardarEndosoDomicilio params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoNombres(params);
		logger.debug("EndososManager guardarEndosoDomicilio response map: "+mapa);
        return mapa;
	}
	
	/**
	 * PKG_CONSULTA.P_reImp_documentos
	 */
	@Override
	public List<Map<String, String>> reimprimeDocumentos(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String tipmov
			)throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_nmsuplem_i" , nmsuplem);
		params.put("pv_tipmov_i"   , tipmov);
		logger.debug("EndososManager reimprimeDocumentos params: "+params);
		List<Map<String,String>> lista=endososDAO.reimprimeDocumentos(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		logger.debug("EndososManager reimprimeDocumentos lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public List<Map<String, String>> obtieneCoberturasDisponibles(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager obtieneCoberturasDisponibles params: "+params);
		List<Map<String,String>> lista=endososDAO.obtieneCoberturasDisponibles(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		logger.debug("EndososManager obtieneCoberturasDisponibles lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public Map<String, String> guardarEndosoCoberturas(Map<String, Object> params) throws Exception
	{
		logger.debug("EndososManager guardarEndosoCoberturas params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoCoberturas(params);
		logger.debug("EndososManager guardarEndosoCoberturas response map: "+mapa);
        return mapa;
	}
	
	@Override
	public List<Map<String, String>> obtenerAtributosCoberturas(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager obtenerAtributosCoberturas params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerAtributosCoberturas(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		logger.debug("EndososManager obtenerAtributosCoberturas lista size: "+lista.size());
		return lista;
	}
	
	//PKG_COTIZA.P_EJECUTA_SIGSVALIPOL_END
	@Override
	public Map<String,Object> sigsvalipolEnd(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager sigsvalipolEnd params: "+params);
		Map<String,Object> mapa=endososDAO.sigsvalipolEnd(params);
		logger.debug("EndososManager sigsvalipolEnd response map: "+mapa);
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
		logger.debug("EndososManager guardarEndosoClausulas params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoClausulas(params);
		logger.debug("EndososManager guardarEndosoClausulas response map: "+mapa);
        return mapa;
	}
	
	//PKG_ENDOSOS.P_CALC_VALOR_ENDOSO
	@Override
	public Map<String,String> calcularValorEndoso(Map<String,Object>params) throws Exception
	{
		logger.debug("EndososManager calcularValorEndoso params: "+params);
		Map<String,String> mapa=endososDAO.calcularValorEndoso(params);
		logger.debug("EndososManager calcularValorEndoso response map: "+mapa);
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
		logger.debug("EndososManager iniciarEndoso params: "+params);
		Map<String,String> mapa=endososDAO.iniciarEndoso(params);
		logger.debug("EndososManager iniciarEndoso response map: "+mapa);
        return mapa;
	}
	
	/**
	 * PKG_ENDOSOS.P_ENDOSO_INICIA
	 */
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
		logger.debug("EndososManager insertarTworksupEnd params: "+params);
		endososDAO.insertarTworksupEnd(params);
		logger.debug("EndososManager insertarTworksupEnd end");
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
		logger.debug("EndososManager insertarTworksupSitTodas params: "+params);
		endososDAO.insertarTworksupSitTodas(params);
		logger.debug("EndososManager insertarTworksupSitTodas end");
	}
	
	//PKG_SATELITES.P_OBTIENE_DATOS_MPOLISIT
	@Override
	public Map<String, String> obtieneDatosMpolisit(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager obtieneDatosMpolisit params: "+params);
		Map<String,String> mapa=endososDAO.obtieneDatosMpolisit(params);
		logger.debug("EndososManager obtieneDatosMpolisit response map: "+mapa);
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
	public List<Map<String, String>> obtenerNombreEndosos(String cdsisrol) throws Exception
	{
		logger.debug("EndososManager obtenerNombreEndosos");
		List<Map<String,String>> lista=endososDAO.obtenerNombreEndosos(cdsisrol);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		logger.debug("EndososManager obtenerNombreEndosos lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public void actualizarFenacimi(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager actualizarFenacimi params: "+params);
		endososDAO.actualizarFenacimi(params);
		logger.debug("EndososManager actualizarFenacimi end");
	}
	
	@Override
	public void actualizarSexo(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager actualizarSexo params: "+params);
		endososDAO.actualizarSexo(params);
		logger.debug("EndososManager actualizarSexo end");
	}
	
	@Override
	public List<Map<String, String>> obtenerCdpersonMpoliper(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager obtenerCdpersonMpoliper params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerCdpersonMpoliper(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		logger.debug("EndososManager obtenerCdpersonMpoliper lista size: "+lista.size());
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
		logger.debug("EndososManager obtenerNtramiteEmision params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerNtramiteEmision(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		logger.debug("EndososManager obtenerNtramiteEmision lista: "+lista);
		String ntramite=lista.size()>0?lista.get(0).get("NTRAMITE"):"";
		logger.debug("EndososManager obtenerNtramiteEmision ntramite: "+ntramite);
		return ntramite;
	}
	
	
	@Override
	public RespuestaVO validaEndosoAnterior(String cdunieco, String cdramo, String estado, String nmpoliza, String cdtipsup) {
		
		RespuestaVO resp = new RespuestaVO();
		try {
			Map<String,String> params = new HashMap<String,String>();
			params.put("pv_cdunieco_i", cdunieco);
			params.put("pv_cdramo_i"  , cdramo);
			params.put("pv_estado_i"  , estado);
			params.put("pv_nmpoliza_i", nmpoliza);
			params.put("pv_cdtipsup_i", cdtipsup);
			logger.debug(new StringBuilder("EndososManager validaEndosoAnterior params: ").append(params).toString());
			endososDAO.validaEndosoAnterior(params);
			resp.setSuccess(true);
		} catch(Exception ex) {
			logger.error(new StringBuilder().append("Error tratando de acceder a pantalla de endoso: ").append(cdtipsup).toString(), ex);
			//resp.setSuccess(false); //No es necesario asignarle valor, un atributo boolean de una clase por default es false
			resp.setMensaje(ex.getMessage());
		}
		return resp;
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
		logger.debug("EndososManager actualizaDeducibleValosit params: "+params);
		endososDAO.actualizaDeducibleValosit(params);
		logger.debug("EndososManager actualizaDeducibleValosit end");
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
		logger.debug("EndososManager actualizaCopagoValosit params: "+params);
		endososDAO.actualizaCopagoValosit(params);
		logger.debug("EndososManager actualizaCopagoValosit end");
	}
	
	//P_CLONAR_POLIZA_REEXPED
	@Override
	public Map<String,String> pClonarPolizaReexped(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String fecha
			,String cdplan) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_feinival_i" , fecha);
		params.put("pv_cdplan_i"   , cdplan);
		logger.debug("EndososManager pClonarPolizaReexped params: "+params);
		Map<String,String> mapa=endososDAO.pClonarPolizaReexped(params);
		logger.debug("EndososManager pClonarPolizaReexped response map: "+mapa);
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
		logger.debug("EndososManager obtenerValositPorNmsuplem params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerValositPorNmsuplem(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		logger.debug("EndososManager obtenerValositPorNmsuplem lista size: "+lista.size());
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
		logger.debug("EndososManager actualizaExtraprimaValosit params: "+params);
		endososDAO.actualizaExtraprimaValosit(params);
		logger.debug("EndososManager actualizaExtraprimaValosit end");
	}
	
	@Override
	public void insertarPolizaCdperpag(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdperpag) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i",cdunieco);
		params.put("pv_cdramo_i",cdramo);
		params.put("pv_estado_i",estado);
		params.put("pv_nmpoliza_i",nmpoliza);
		params.put("pv_nmsuplem_i",nmsuplem);
		params.put("pv_cdperpag_i",cdperpag);
		logger.debug("EndososManager insertaPolizaCdperpag params: "+params);
		endososDAO.insertarPolizaCdperpag(params);
		logger.debug("EndososManager insertaPolizaCdperpag end");
	}
	
	/**
	 * PKG_ENDOSOS.P_GET_FEINIVAL_END_FP
	 */
	@Override
	public Date obtenerFechaEndosoFormaPago(String cdunieco,String cdramo,String estado,String nmpoliza) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		logger.debug("EndososManager obtenerFechaEndosoFormaPago params: "+params);
		Date fecha=endososDAO.obtenerFechaEndosoFormaPago(params);
		logger.debug("EndososManager obtenerFechaEndosoFormaPago fecha: "+fecha);
		return fecha;
	}
	
	/**
	 * P_CALC_RECIBOS_SUB_ENDOSO_FP
	 */
	@Override
	public void calcularRecibosEndosoFormaPago(String cdunieco,String cdramo,
			String estado,String nmpoliza,String nmsuplem) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco" , cdunieco);
		params.put("pv_cdramo"   , cdramo);
		params.put("pv_estado"   , estado);
		params.put("pv_nmpoliza" , nmpoliza);
		params.put("pv_nmsuplem" , nmsuplem);
		logger.debug("EndososManager calcularRecibosEndosoFormaPago params: "+params);
		endososDAO.calcularRecibosEndosoFormaPago(params);
		logger.debug("EndososManager calcularRecibosEndosoFormaPago fin");
	}
	
	/**
	 * P_CALCULA_COMISION_BASE
	 */
	@Override
	public void calcularComisionBase(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_nmsuplem_i" , nmsuplem);
		logger.debug("EndososManager calcularComisionBase params: "+params);
		endososDAO.calcularComisionBase(params);
		logger.debug("EndososManager calcularComisionBase fin");
	}
	
	/**
	 * PKG_CONSULTA.P_GET_AGENTE_POLIZA
	 * @return a.cdunieco,
			a.cdramo,
			a.estado,
			a.nmpoliza,
			a.cdagente,
			a.nmsuplem,
			a.status,
			a.cdtipoag,
			porredau,
			a.porparti,
			nombre,
			cdsucurs,
			nmcuadro
	 */
	@Override
	public List<Map<String,String>> obtenerAgentesEndosoAgente(
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
		logger.debug("EndososManager obtenerAgentesEndosoAgente params: "+params);
		List<Map<String,String>>lista=endososDAO.obtenerAgentesEndosoAgente(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>();
		logger.debug("EndososManager obtenerAgentesEndosoAgente lista size: "+lista.size());
		return lista;
	}
	
	/**
	 * PKG_SATELITES.P_MOV_MPOLIAGE
	 */
	@Override
	public void pMovMpoliage(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdagente
			,String nmsuplem
			,String status
			,String cdtipoag
			,String porredau
			,String nmcuadro
			,String cdsucurs
			,String accion
			,String ntramite
			,String porparti
			) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_cdagente_i" , cdagente);
		params.put("pv_nmsuplem_i" , nmsuplem);
		params.put("pv_status_i"   , status);
		params.put("pv_cdtipoag_i" , cdtipoag);
		params.put("pv_porredau_i" , porredau);
		params.put("pv_nmcuadro_i" , nmcuadro);
		params.put("pv_cdsucurs_i" , cdsucurs);
		params.put("pv_accion_i"   , accion);
		params.put("pv_ntramite_i" , ntramite);
		params.put("pv_porparti_i" , porparti);
		logger.debug("EndososManager pMovMpoliage params: "+params);
		endososDAO.pMovMpoliage(params);
		logger.debug("EndososManager pMovMpoliage fin");
	}
	
	/**
	 * PKG_SATELITES.P_GET_NMSUPLEM_EMISION
	 */
	@Override
	public String pGetSuplemEmision(String cdunieco,String cdramo,String estado,String nmpoliza) throws Exception
	{
		String nmsuplem = "";
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		logger.debug("EndososManager pGetSuplemEmision params: "+params);
		nmsuplem = endososDAO.pGetSuplemEmision(params);
		logger.debug("EndososManager pGetSuplemEmision nmsuplem: "+nmsuplem);
		return nmsuplem;
	}
	
	@Override
	public String obtieneFechaInicioVigenciaPoliza
	(
		String cdunieco,
		String cdramo,
		String estado,
		String nmpoliza
		) throws Exception
	{
		return endososDAO.obtieneFechaInicioVigenciaPoliza(cdunieco,cdramo,estado,nmpoliza);
	}
	
	@Override
	public boolean validaEndosoSimple
	(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza
			) throws Exception
	{
		return endososDAO.validaEndosoSimple(cdunieco,cdramo,estado,nmpoliza);
	}
	
	@Override
	public void validaNuevaCobertura(String cdgarant, Date fenacimi) throws Exception
	{
		logger.info(""
				+ "\n##################################"
				+ "\n###### validaNuevaCobertura ######"
				);
		endososDAO.validaNuevaCobertura(cdgarant,fenacimi);
		logger.info(""
				+ "\n###### validaNuevaCobertura ######"
				+ "\n##################################"
				);
	}
	
	@Override
	public void calcularRecibosCambioAgente(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdagente) throws Exception
	{
		logger.info(""
				+ "\n#########################################"
				+ "\n###### calcularRecibosCambioAgente ######"
				);
		endososDAO.calcularRecibosCambioAgente(cdunieco,cdramo,estado,nmpoliza,nmsuplem,cdagente);
		logger.info(""
				+ "\n###### calcularRecibosCambioAgente ######"
				+ "\n#########################################"
				);
	}
	
	@Override
	public List<Map<String,String>> habilitaRecibosSubsecuentes(
			Date fechaDeInicio
			,Date fechaDeFin
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza) throws Exception
	{
		return endososDAO.habilitaRecibosSubsecuentes(fechaDeInicio,fechaDeFin,cdunieco,cdramo,estado,nmpoliza);
	}
	
	@Override
	public void validaEstadoCodigoPostal(Map<String,String>params) throws Exception{
		endososDAO.validaEstadoCodigoPostal(params);
	}
	
	@Override
	public void actualizaTvalositCoberturasAdicionales(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdtipsit
			,String cdtipsup) throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ actualizaTvalositCoberturasAdicionales @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ nmsuplem=").append(nmsuplem)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.append("\n@@@@@@ cdtipsup=").append(cdtipsup)
				.toString()
				);
		
		endososDAO.actualizaTvalositCoberturasAdicionales(cdunieco,cdramo,estado,nmpoliza,nmsuplem,cdtipsit,cdtipsup);
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ actualizaTvalositCoberturasAdicionales @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
	}
	
	@Override
	public ManagerRespuestaImapSmapVO obtenerComponenteSituacionCobertura(String cdramo,String cdtipsit,String cdtipsup,String cdgarant)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ obtenerComponenteSituacionCobertura @@@@@@")
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.append("\n@@@@@@ cdtipsup=").append(cdtipsup)
				.append("\n@@@@@@ cdgarant=").append(cdgarant)
				.toString()
				);
		
		ManagerRespuestaImapSmapVO resp = new ManagerRespuestaImapSmapVO(true);
		resp.setSmap(new HashMap<String,String>());
		resp.setImap(new HashMap<String,Item>());
		
		ComponenteVO comp = null;
		
		//obtener componente
		try
		{
			comp = endososDAO.obtenerComponenteSituacionCobertura(cdramo,cdtipsit,cdtipsup,cdgarant);
		}
		catch(ApplicationException ax)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString());
			resp.setRespuestaOculta(ax.getMessage());
			logger.error(resp.getRespuesta(),ax);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al obtener componente #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		//transformarlo a item
		if(resp.isExito())
		{
			try
			{
				if(comp==null)
				{
					resp.getSmap().put("CONITEM" , "false");
				}
				else
				{
					resp.getSmap().put("CONITEM" , "true");
					
					GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
					gc.setCdramo(cdramo);
					gc.setCdtipsit(cdtipsit);
					
					List<ComponenteVO>lista=new ArrayList<ComponenteVO>();
					lista.add(comp);
					
					gc.generaComponentes(lista, true, false, true, false, false, false);
					
					resp.getImap().put("item",gc.getItems());
				}
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al construir componente #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ obtenerComponenteSituacionCobertura @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public void actualizaTvalositSitaucionCobertura(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdatribu
			,String otvalor)
	{
		try
		{
			endososDAO.actualizaTvalositSitaucionCobertura(cdunieco,cdramo,estado,nmpoliza,nmsuplem,cdatribu,otvalor);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			logger.error(new StringBuilder("Error al actualizar tvalosit situacion cobertura #").append(timestamp).toString(),ex);
		}
	}
}