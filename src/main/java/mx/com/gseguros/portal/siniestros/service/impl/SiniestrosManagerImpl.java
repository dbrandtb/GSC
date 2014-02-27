package mx.com.gseguros.portal.siniestros.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.siniestros.dao.SiniestrosDAO;
import mx.com.gseguros.portal.siniestros.model.AutorizaServiciosVO;
import mx.com.gseguros.portal.siniestros.model.AutorizacionServicioVO;
import mx.com.gseguros.portal.siniestros.model.CoberturaPolizaVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaManteniVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaPorcentajeVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaProveedorVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTDETAUTSVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTTAPVAATVO;
import mx.com.gseguros.portal.siniestros.model.DatosSiniestroVO;
import mx.com.gseguros.portal.siniestros.model.ListaFacturasVO;
import mx.com.gseguros.portal.siniestros.model.PolizaVigenteVO;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;

public class SiniestrosManagerImpl implements SiniestrosManager {
	private SiniestrosDAO siniestrosDAO;
	
	private static org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger(SiniestrosManagerImpl.class);
	
	
	@Override
	public List<AutorizacionServicioVO> getConsultaAutorizacionesEsp(String nmautser) throws ApplicationException {
		try {
			return siniestrosDAO.obtieneDatosAutorizacionEsp(nmautser);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	public List<GenericVO> getConsultaListaAsegurado(String cdperson) throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoAsegurado(cdperson);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	public List<AutorizaServiciosVO> getConsultaListaAutorizaciones(
			String tipoAut, String cdperson) throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoAutorizaciones(tipoAut,cdperson);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	public List<ConsultaProveedorVO> getConsultaListaProveedorMedico(String tipoprov,String cdpresta)
			throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoProvMedico(tipoprov,cdpresta);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	public List<CoberturaPolizaVO> getConsultaListaCoberturaPoliza(
			HashMap<String, Object> paramCobertura) throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoCoberturaPoliza(paramCobertura);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	public List<DatosSiniestroVO> getConsultaListaDatSubGeneral(
			HashMap<String, Object> paramDatSubGral)
			throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoDatSubGeneral(paramDatSubGral);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	public List<GenericVO> getConsultaListaSubcobertura(String cdgarant,
			String cdsubcob) throws ApplicationException {
		try {
			log.debug("getConsultaListaSubcobertura cdgarant: "+cdgarant+", cdsubcob: "+cdsubcob);
			List<GenericVO> lista = siniestrosDAO.obtieneListadoSubcobertura(cdgarant,cdsubcob);
			if(lista==null)
			{
				lista= new ArrayList<GenericVO>();
			}
			log.debug("getConsultaListaSubcobertura lista size: "+lista.size());
			return lista;
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	public List<GenericVO> getConsultaListaCPTICD(String cdtabla, String otclave)
			throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoCPTICD(cdtabla,otclave);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public List<HashMap<String, String>> loadListaDocumentos(HashMap<String, String> params)
			throws ApplicationException {
		try {
			return siniestrosDAO.loadListaDocumentos(params);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public String generaContraRecibo(HashMap<String, Object> params)
			throws ApplicationException {
		try {
			return siniestrosDAO.generaContraRecibo(params);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public List<HashMap<String, String>> loadListaIncisosRechazos(HashMap<String, String> params)
			throws ApplicationException {
		try {
			return siniestrosDAO.loadListaIncisosRechazos(params);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public boolean guardaEstatusDocumentos(HashMap<String, String> params, List<HashMap<String, String>> saveList)
			throws ApplicationException {
		
		boolean allUpdated = true;
		
		for(HashMap<String, String> doc : saveList){
			try {
				params.put("pv_accion_i", doc.get("listo"));
				params.put("pv_cddocume_i", doc.get("id"));
				siniestrosDAO.guardaEstatusDocumento(params);
			} catch (DaoException daoExc) {
				allUpdated = false;
			}
		}
		
		return allUpdated;
	}
	
	@Override
	public List<HashMap<String, String>> loadListaRechazos()
			throws ApplicationException {
		try {
			return siniestrosDAO.loadListaRechazos();
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public boolean rechazarTramite(HashMap<String, String> params)
			throws ApplicationException {
		try {
			siniestrosDAO.rechazarTramite(params);
		} catch (DaoException daoExc) {
			log.error("Error al rechazar tramite: " + daoExc.getMessage(), daoExc);	
			return false;
		}
		return true;
	}

	@Override
	public boolean solicitarPago(HashMap<String, String> params)
			throws ApplicationException {
		try {
			//siniestrosDAO.turnarOperadorAR(params);
		} catch (Exception daoExc) {
			log.error("Error al solicitarPago: " + daoExc.getMessage(), daoExc);	
			return false;
		}
		return true;
	}

	
	public void setSiniestrosDAO(SiniestrosDAO siniestrosDAO) {
		this.siniestrosDAO = siniestrosDAO;
	}
	
	@Override
	public List<ConsultaTDETAUTSVO> getConsultaListaTDeTauts(String nmautser)
			throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoTDeTauts(nmautser);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public void getEliminacionRegistros(String nmautser)
			throws ApplicationException {
			try {
				siniestrosDAO.eliminacionRegistrosTabla(nmautser);
			} catch (DaoException daoExc) {
				throw new ApplicationException(daoExc.getMessage(), daoExc);
			}
	}
	
	@Override
	public List<AutorizacionServicioVO> guardarAutorizacionServicio(HashMap<String, Object> paramsR)throws ApplicationException {
		try {
			return siniestrosDAO.guardarAutorizacionServicio(paramsR);			
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public String guardaListaTDeTauts(HashMap<String, Object> paramsTDeTauts)
			throws ApplicationException {
		try {
			return siniestrosDAO.guardarListaTDeTauts(paramsTDeTauts);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public List<ConsultaTTAPVAATVO> getConsultaListaTTAPVAAT(
			HashMap<String, Object> paramTTAPVAAT) throws ApplicationException {
		 try {
		        return siniestrosDAO.obtieneListadoTTAPVAAT(paramTTAPVAAT);
		    } catch (DaoException daoExc) {
		        throw new ApplicationException(daoExc.getMessage(), daoExc);
		    }
	}

	@Override
	public List<ConsultaManteniVO> getConsultaListaManteni(String cdtabla, String codigo) throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoManteni(cdtabla,codigo);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public List<ConsultaPorcentajeVO> getConsultaListaPorcentaje(String cdcpt, String cdtipmed,String mtobase) throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoPorcentaje(cdcpt,cdtipmed,mtobase);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public List<PolizaVigenteVO> getConsultaListaPoliza(String cdperson) throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoPoliza(cdperson);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public List<GenericVO> getConsultaListaPlaza() throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoPlaza();
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	
	@Override
	public String guardaListaFacMesaControl(
			String ntramite,
			String nfactura,
			String fefactura,
			String cdtipser,
			String cdpresta,
			String ptimport,
			String cdgarant,
			String descporc,
			String descnume) throws ApplicationException {
		// TODO Auto-generated method stub
		try {
			HashMap<String,Object> paramsFacMesaCtrl=new HashMap<String,Object>();
			paramsFacMesaCtrl.put("pv_ntramite_i",ntramite);
			paramsFacMesaCtrl.put("pv_nfactura_i",nfactura);
			paramsFacMesaCtrl.put("pv_ffactura_i",fefactura);
			paramsFacMesaCtrl.put("pv_cdtipser_i",cdtipser);
			paramsFacMesaCtrl.put("pv_cdpresta_i",cdpresta);
			paramsFacMesaCtrl.put("pv_ptimport_i",ptimport);
			paramsFacMesaCtrl.put("pv_cdgarant_i",cdgarant);
			paramsFacMesaCtrl.put("pv_descporc_i",descporc);
			paramsFacMesaCtrl.put("pv_descnume_i",descnume);
			log.debug("guardaListaFacMesaControl params: "+paramsFacMesaCtrl);
			return siniestrosDAO.guardaFacMesaControl(paramsFacMesaCtrl);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public String guardaListaTworkSin(HashMap<String, Object> paramsTworkSin) throws ApplicationException {
		// TODO Auto-generated method stub
		try {
			return siniestrosDAO.guardaListaTworkSin(paramsTworkSin);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public String getAltaSiniestroAutServicio(String nmautser) throws ApplicationException {
		// TODO Auto-generated method stub
		try {
			return siniestrosDAO.guardaAltaSiniestroAutServicio(nmautser);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public String getAltaSiniestroAltaTramite(String ntramite) throws ApplicationException {
		// TODO Auto-generated method stub
		try {
			return siniestrosDAO.guardaAltaSiniestroAltaTramite(ntramite);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public String getAltaMsinival(HashMap<String, Object> paramMsinival) throws ApplicationException {
		// TODO Auto-generated method stub
		try {
			return siniestrosDAO.guardaAltaMsinival(paramMsinival);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public List<ListaFacturasVO> getConsultaListaFacturas(HashMap<String, Object> paramFact) throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoFacturas(paramFact);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public String getBajaMsinival(HashMap<String, Object> paramBajasinival) throws ApplicationException {
		try {
			return siniestrosDAO.bajaMsinival(paramBajasinival);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	//public List<GenericVO> obtieneListadoCobertura(String cdramo,String cdtipsit) throws DaoException;
	
	public List<GenericVO> obtieneListadoCobertura(String cdramo,String cdtipsit) throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoCobertura(cdramo,cdtipsit);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	public String actualizaOTValorMesaControl(Map<String, Object> params) throws ApplicationException {
		try
		{
			String[] keys = new String[]{
					"pv_cdramo_i","pv_cdtipsit_i",
					"pv_otvalor01_i","pv_otvalor02_i","pv_otvalor03_i","pv_otvalor04_i","pv_otvalor05_i","pv_otvalor06_i","pv_otvalor07_i","pv_otvalor08_i","pv_otvalor09_i","pv_otvalor10_i",
					"pv_otvalor11_i","pv_otvalor12_i","pv_otvalor13_i","pv_otvalor14_i","pv_otvalor15_i","pv_otvalor16_i","pv_otvalor17_i","pv_otvalor18_i","pv_otvalor19_i","pv_otvalor20_i",
					"pv_otvalor21_i","pv_otvalor22_i","pv_otvalor23_i","pv_otvalor24_i","pv_otvalor25_i","pv_otvalor26_i","pv_otvalor27_i","pv_otvalor28_i","pv_otvalor29_i","pv_otvalor30_i",
					"pv_otvalor31_i","pv_otvalor32_i","pv_otvalor33_i","pv_otvalor34_i","pv_otvalor35_i","pv_otvalor36_i","pv_otvalor37_i","pv_otvalor38_i","pv_otvalor39_i","pv_otvalor40_i",
					"pv_otvalor41_i","pv_otvalor42_i","pv_otvalor43_i","pv_otvalor44_i","pv_otvalor45_i","pv_otvalor46_i","pv_otvalor47_i","pv_otvalor48_i","pv_otvalor49_i","pv_otvalor50_i",
			};
			if(params!=null)
			{
				for(String key : keys)
				{
					if(!params.containsKey(key))
					{
						params.put(key,null);
					}
				}
			}
			return siniestrosDAO.actualizaOTValorMesaControl(params);
		}
		catch (DaoException daoExc)
		{
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	/**
	 * PKG_SINIESTRO.P_LISTA_SINIESTROSXTRAMITE
	 * 6969 NMSINIES,
	 * 500 NMAUTSER,
	 * 510918 CDPERSON,
	 * 'JUAN PEREZ' NOMBRE,
	 * SYSDATE FEOCURRE,
	 * 1009 CDUNIECO,
	 * 'SALUD CAMPECHE' DSUNIECO,
	 * 2 CDRAMO,
	 * 'SALUD VITAL' DSRAMO,
	 * 'SL' CDTIPSIT,
	 * 'SALUD VITAL' DSTIPSIT,
	 * status,
	 * 'M' ESTADO,
	 * 500 NMPOLIZA,
	 * 'S' VOBOAUTO,
	 * '65' CDICD,
	 * 'GRIPE' DSICD,
	 * '66' ICD2,
	 * 'FIEBRE' DSICD2,
	 * 12.5 DESCPORC,
	 * 300 DESCNUME,
	 * 15 COPAGO,
	 * 3500 PTIMPORT,
	 * 'S' AUTRECLA,
	 * 54647 NMRECLAM,
	 * aaapertu
	 */
	@Override
	public List<Map<String,String>> listaSiniestrosTramite(String ntramite) throws Exception
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("pv_ntramite_i" , ntramite);
		log.debug("listaSiniestrosTramite params: "+params);
		List<Map<String,String>> lista = siniestrosDAO.listaSiniestrosTramite(params);
		if(lista == null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		log.debug("listaSiniestrosTramite lista size: "+lista.size());
		return lista;
	}
	
	/**
	 * PKG_PRESINIESTRO.P_GET_TRAMITE_COMPLETO
	 */
	@Override
	public Map<String,String> obtenerTramiteCompleto(String ntramite) throws Exception
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("pv_ntramite_i" , ntramite);
		log.debug("obtenerTramiteCompleto params: "+params);
		Map<String,String> tramite = siniestrosDAO.obtenerTramiteCompleto(params);
		log.debug("obtenerTramiteCompleto tramite: "+tramite);
		return tramite;
	}
	
	/**
	 * PKG_SATELITES.P_OBT_TFACMESCTRL
	 * ntramite,
		nfactura,
		ffactura,
		cdtipser,
		DescServicio,
		cdpresta,
		NombreProveedor,
		ptimport,
		cdgarant,
		DSGARANT,
		DESCPORC,
		DESCNUME
	 */
	@Override
	public List<Map<String,String>> obtenerFacturasTramite(String ntramite) throws Exception
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("pv_ntramite_i" , ntramite);
		log.debug("obtenerFacturasTramite params: "+params);
		List<Map<String,String>> lista = siniestrosDAO.obtenerFacturasTramite(params);
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		log.debug("obtenerFacturasTramite lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public void actualizarAutorizacionTworksin(String ntramite, String nmpoliza, String cdperson,String nmautser) throws Exception
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("pv_ntramite_i",ntramite);
		params.put("pv_nmpoliza_i",nmpoliza);
		params.put("pv_cdperson_i",cdperson);
		params.put("pv_nmautser_i",nmautser);
		log.debug("actualizarAutorizacionTworksin params: "+params);
		siniestrosDAO.actualizarAutorizacionTworksin(params);
		log.debug("actualizarAutorizacionTworksin end");
	}
	
	@Override
	public void actualizaMsinies(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsituac,
			String nmsuplem,
			String status,
			String aaapertu,
			String nmsinies,
			Date feocurre,
			String cdicd,
			String cdicd2,
			String nreclamo) throws Exception
	{
		siniestrosDAO.actualizaMsinies(cdunieco,cdramo,estado,nmpoliza,nmsituac,nmsuplem,status,aaapertu,nmsinies,feocurre,cdicd,cdicd2,nreclamo);
	}
	
	@Override
	public void P_MOV_MAUTSINI(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsuplem,
			String nmsituac,
			String aaapertu,
			String status,
			String nmsinies,
			String nfactura,
			String areaauto,
			String swautori,
			String tipautor,
			String comments,
			String accion) throws Exception
	{
		siniestrosDAO.P_MOV_MAUTSINI(cdunieco,cdramo,estado,nmpoliza,nmsuplem,nmsituac,aaapertu,status,nmsinies,nfactura,areaauto,swautori,tipautor,comments,accion);
	}
}