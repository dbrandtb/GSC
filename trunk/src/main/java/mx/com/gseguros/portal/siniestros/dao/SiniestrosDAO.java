package mx.com.gseguros.portal.siniestros.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.siniestros.model.AutorizaServiciosVO;
import mx.com.gseguros.portal.siniestros.model.AutorizacionServicioVO;
import mx.com.gseguros.portal.siniestros.model.CoberturaPolizaVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaManteniVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaPorcentajeVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaProveedorVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTDETAUTSVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTTAPVAATVO;
import mx.com.gseguros.portal.siniestros.model.DatosSiniestroVO;
import mx.com.gseguros.portal.siniestros.model.HistorialSiniestroVO;
import mx.com.gseguros.portal.siniestros.model.ListaFacturasVO;
import mx.com.gseguros.portal.siniestros.model.PolizaVigenteVO;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.Reclamo;

public interface SiniestrosDAO {

	public List<AutorizacionServicioVO> obtieneDatosAutorizacionEsp(String nmautser) throws DaoException;

	public List<GenericVO> obtieneListadoAsegurado(String cdperson) throws DaoException;

	public List<AutorizaServiciosVO> obtieneListadoAutorizaciones(String tipoAut,String cdperson) throws DaoException;
	
	public List<ConsultaProveedorVO> obtieneListadoProvMedico(String tipoprov,String cdpresta) throws DaoException;
	
	
	//public List<GenericVO> obtieneListadoCausaSiniestro(String cdcausa) throws DaoException;
	
	public List<CoberturaPolizaVO> obtieneListadoCoberturaPoliza(HashMap<String, Object> paramCobertura) throws DaoException;

	public List<DatosSiniestroVO> obtieneListadoDatSubGeneral(HashMap<String, Object> paramDatSubGral) throws DaoException;
	
	public List<GenericVO> obtieneListadoSubcobertura(String cdgarant, String cdsubcob) throws DaoException;
	
	public List<GenericVO> obtieneListadoCPTICD(String cdtabla, String otclave) throws DaoException;

	public List<HashMap<String, String>> loadListaDocumentos(HashMap<String, String> params) throws DaoException;

	public String generaContraRecibo(HashMap<String, Object> params) throws DaoException;

	public List<Map<String, String>> loadListaIncisosRechazos(Map<String, String> params) throws DaoException;

	public List<Map<String, String>> loadListaRechazos() throws DaoException;
	
	public String guardaEstatusDocumento(HashMap<String, String> params) throws DaoException;

	public String rechazarTramite(HashMap<String, String> params) throws DaoException;

	//public List<GenericVO> obtieneListadoMovRechazo(String cdmotRechazo) throws DaoException;
	
	public List<ConsultaTDETAUTSVO> obtieneListadoTDeTauts(String nmautser) throws DaoException;
	
	public String guardarListaTDeTauts(HashMap<String, Object> paramsTDeTauts) throws DaoException;
	
	public List<PolizaVigenteVO> obtieneListadoPoliza(String cdperson) throws DaoException;
	
	/* ############################################################################## 
	 * ##################################### VERIFICAR ##############################*/
	public List<AutorizacionServicioVO> guardarAutorizacionServicio(Map<String, Object> paramsR) throws DaoException;

	public List<ConsultaTTAPVAATVO> obtieneListadoTTAPVAAT(HashMap<String, Object> paramTTAPVAAT) throws DaoException;

	public List<ConsultaManteniVO> obtieneListadoManteni(String cdtabla, String codigo) throws DaoException;

	public List<ConsultaPorcentajeVO> obtieneListadoPorcentaje(String cdcpt, String cdtipmed,String mtobase) throws DaoException;

	public void eliminacionRegistrosTabla(String nmautser) throws DaoException;

	public List<GenericVO> obtieneListadoPlaza() throws DaoException;

	public String guardaFacMesaControl(HashMap<String, Object> paramsFacMesaCtrl) throws DaoException;

	public String guardaListaTworkSin(HashMap<String, Object> paramsTworkSin) throws DaoException;

	public String guardaAltaSiniestroAutServicio(String nmautser) throws DaoException;

	public String guardaAltaSiniestroAltaTramite(String ntramite) throws DaoException;

	public String guardaAltaMsinival(HashMap<String, Object> paramMsinival) throws DaoException;

	public List<ListaFacturasVO> obtieneListadoFacturas(HashMap<String, Object> paramFact) throws DaoException;

	public String bajaMsinival(HashMap<String, Object> paramBajasinival) throws DaoException;
	
	public List<GenericVO> obtieneListadoCobertura(String cdramo,String cdtipsit) throws DaoException;

	public String actualizaOTValorMesaControl(Map<String, Object> params) throws DaoException;
	
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
	public List<Map<String,String>> listaSiniestrosTramite(Map<String, String> params) throws Exception;
	
	/**
	 * PKG_PRESINIESTRO.P_GET_TRAMITE_COMPLETO
	 */
	public Map<String,String> obtenerTramiteCompleto(Map<String, String> params) throws Exception;
	
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
	public List<Map<String,String>> obtenerFacturasTramite(Map<String, String> params) throws Exception;

	public List<HashMap<String,String>> obtenerFacturasTramiteSiniestro(HashMap<String, String> params) throws Exception;

	/**
	 * PKG_PRESINIESTRO.P_UPB_NMAUTSER_TWORKSIN
	 */
	public void actualizarAutorizacionTworksin(Map<String,String>params) throws Exception;

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
			String nreclamo) throws Exception;
	
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
			String cdgarant,
			String cdconval,
			String cdconcep,
			String idconcep,
			String nmordina,
			String areaauto,
			String swautori,
			String tipautor,
			String comments,
			String accion) throws Exception;
	
	public void P_MOV_MSINIVAL(
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
			String cdgarant,
			String cdconval,
			String cdconcep,
			String idconcep,
			String cdcapita,
			String nmordina,
			Date   femovimi,
			String cdmoneda,
			String ptprecio,
			String cantidad,
			String destopor,
			String destoimp,
			String ptimport,
			String ptrecobr,
			String nmanno,
			String nmapunte,
			String userregi,
			Date   feregist,
			String accion) throws Exception;
	
	public List<Map<String,String>>P_GET_MSINIVAL(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsuplem,
			String nmsituac,
			String aaapertu,
			String status,
			String nmsinies,
			String nfactura) throws Exception;
	
	public List<PolizaVigenteVO> consultaPolizaUnica(HashMap<String, Object> paramPolUnica) throws Exception;

	public String validaExclusionPenalizacion(HashMap<String, Object> paramExclusion) throws Exception;
	
	public void P_MOV_TDSINIVAL(
			String cdunieco,String cdramo,String estado,String nmpoliza,String nmsuplem,
			String nmsituac,String aaapertu,String status,String nmsinies,String nfactura,
			String cdgarant,String cdconval,String cdconcep,String idconcep,String nmordina,
			String nmordmov,String ptimport,String comments,String userregi,Date feregist,String accion) throws Exception;
	
	public List<Map<String,String>>P_GET_TDSINIVAL(
			String cdunieco,String cdramo,String estado,String nmpoliza,String nmsuplem,
			String nmsituac,String aaapertu,String status,String nmsinies,String nfactura,
			String cdgarant,String cdconval,String cdconcep,String idconcep,String nmordina) throws Exception;
	
	public List<Map<String,String>>P_GET_FACTURAS_SINIESTRO(
			String cdunieco,String cdramo,String estado,String nmpoliza,String nmsuplem,
			String nmsituac,String aaapertu,String status,String nmsinies) throws Exception;

	public List<Map<String,String>>cargaHistorialSiniestros(Map<String,String> params) throws Exception;
	
	public List<GenericVO>obtenerCodigosMedicos(String idconcep, String subcaden) throws Exception;
	
	public Map<String,String>obtenerLlaveSiniestroReembolso(String ntramite) throws Exception;
	
	public List<Map<String,String>> obtieneDatosGeneralesSiniestro(Map<String, Object> params) throws Exception;
	
	public Map<String, Object> actualizaDatosGeneralesSiniestro(Map<String, Object> params) throws Exception;
	
	public List<HistorialSiniestroVO> obtieneHistorialReclamaciones(Map<String, Object> params) throws Exception;
	
	
	public List<Map<String,String>>P_GET_CONCEPTOS_FACTURA(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsuplem,
			String nmsituac,
			String aaapertu,
			String status,
			String nmsinies,
			String nfactura) throws Exception;
	
	public Map<String,String>obtenerCopagoDeducible(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsuplem,
			String nmsituac,
			String aaapertu,
			String status,
			String nmsinies,
			String nfactura) throws Exception;
	
	public String validaPorcentajePenalizacion(String zonaContratada,String zonaAtencion) throws Exception;

	public String obtieneAutorizacionProceso(String nmAutSer) throws Exception;
	
	public String validaDocumentosCargados(HashMap<String, String> params) throws Exception;
	
	public List<Reclamo> obtieneDatosReclamoWS(Map<String, Object> params) throws Exception;
}