package mx.com.gseguros.portal.siniestros.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.portal.siniestros.model.AltaTramiteVO;
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
import mx.com.gseguros.portal.siniestros.model.MesaControlVO;
import mx.com.gseguros.portal.siniestros.model.PolizaVigenteVO;
import mx.com.gseguros.portal.siniestros.model.SiniestroVO;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.Reclamo;

public interface SiniestrosDAO {

	public List<AutorizacionServicioVO> obtieneDatosAutorizacionEsp(String nmautser) throws Exception;

	public List<GenericVO> obtieneListadoAsegurado(String cdperson) throws Exception;

	public List<AutorizaServiciosVO> obtieneListadoAutorizaciones(String tipoAut,String cdperson) throws Exception;
	
	public List<ConsultaProveedorVO> obtieneListadoProvMedico(String tipoprov,String cdpresta) throws Exception;
	
	public List<CoberturaPolizaVO> obtieneListadoCoberturaPoliza(HashMap<String, Object> paramCobertura) throws Exception;

	public List<DatosSiniestroVO> obtieneListadoDatSubGeneral(HashMap<String, Object> paramDatSubGral) throws Exception;
	
	public List<GenericVO> obtieneListadoSubcobertura(String cdgarant, String cdsubcob) throws Exception;
	
	public List<GenericVO> obtieneListadoCPTICD(String cdtabla, String otclave) throws Exception;

	public List<HashMap<String, String>> loadListaDocumentos(HashMap<String, String> params) throws Exception;

	public String generaContraRecibo(HashMap<String, Object> params) throws Exception;

	public List<Map<String, String>> loadListaIncisosRechazos(Map<String, String> params) throws Exception;

	public List<Map<String, String>> loadListaRechazos() throws Exception;
	
	public String guardaEstatusDocumento(HashMap<String, String> params) throws Exception;

	public String rechazarTramite(HashMap<String, String> params) throws Exception;
	
	public List<ConsultaTDETAUTSVO> obtieneListadoTDeTauts(String nmautser) throws Exception;
	
	public String guardarListaTDeTauts(HashMap<String, Object> paramsTDeTauts) throws Exception;
	
	public List<PolizaVigenteVO> obtieneListadoPoliza(String cdperson,String cdramo) throws Exception;
	
	/* ############################################################################## 
	 * ##################################### VERIFICAR ##############################*/
	public List<AutorizacionServicioVO> guardarAutorizacionServicio(Map<String, Object> paramsR) throws Exception;

	public List<ConsultaTTAPVAATVO> obtieneListadoTTAPVAAT(HashMap<String, Object> paramTTAPVAAT) throws Exception;

	public List<ConsultaManteniVO> obtieneListadoManteni(String cdtabla, String codigo) throws Exception;

	public List<ConsultaPorcentajeVO> obtieneListadoPorcentaje(String cdcpt, String cdtipmed,String mtobase) throws Exception;

	public void eliminacionRegistrosTabla(String nmautser) throws Exception;

	public List<GenericVO> obtieneListadoPlaza() throws Exception;

	public String guardaFacMesaControl(HashMap<String, Object> paramsFacMesaCtrl) throws Exception;

	public String guardaListaTworkSin(HashMap<String, Object> paramsTworkSin) throws Exception;

	public String guardaAltaSiniestroAutServicio(String nmautser,String nfactura) throws Exception;

	public String guardaAltaSiniestroAltaTramite(String ntramite) throws Exception;
	
	public String guardaAltaSiniestroSinAutorizacion(String ntramite,String cdunieco,String cdramo, String estado,String nmpoliza,
			  String nmsuplem,String nmsituac, String cdtipsit, String fechaOcurrencia,String nfactura) throws Exception;
	
	public String guardaAltaMsinival(HashMap<String, Object> paramMsinival) throws Exception;

	public List<ListaFacturasVO> obtieneListadoFacturas(HashMap<String, Object> paramFact) throws Exception;

	public String bajaMsinival(HashMap<String, Object> paramBajasinival) throws Exception;
	
	public List<GenericVO> obtieneListadoCobertura(String cdramo,String cdtipsit) throws Exception;

	public String actualizaOTValorMesaControl(Map<String, Object> params) throws Exception;
	
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
	
	public List<Map<String,String>> listaSiniestrosTramite2(Map<String, String> params) throws Exception;
	
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
			String accion,
			String ptpcioex,
			String dctoimex,
			String ptimpoex,
			String mtoArancel) throws Exception;
	
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

	public void cambiarEstatusMAUTSERV(String nmautser,String status) throws Exception;

	public List<AltaTramiteVO> consultaListaAltaTramite(String ntramite) throws Exception;

	public List<MesaControlVO> consultaListaMesaControl(String ntramite) throws Exception;
	
	public void eliminacionTworksin(String ntramite) throws Exception;
	
	public void eliminacionTworksin(String ntramite,String factura) throws Exception;

	public void eliminacionTFacMesaControl(String ntramite) throws Exception;
	
	public Map<String,String> obtenerDatosProveedor(String cdpresta) throws Exception;
	
	public void movTimpsini(String accion
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String aaapertu
			,String status
			,String nmsinies
			,String ntramite
			,String ptimport
			,String iva
			,String ivr
			,String isr
			,String cedular
			,boolean enviado) throws Exception;
	
	public Map<String,String>obtenerAutorizacionesFactura(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String aaapertu
			,String status
			,String nmsinies
			,String nfactura) throws Exception;
	
	public List<SiniestroVO> solicitudPagoEnviada(Map params)throws Exception;
	
	public void guardarTotalProcedenteFactura(String ntramite,String nfactura,String importe)throws Exception;
	
	public String validaDocumentosAutServicio(String ntramite)throws Exception;
	
	public void turnarTramite(String ntramite,String cdsisrol,String cdusuari) throws Exception;
	
	public List<Map<String,String>> obtenerUsuariosPorRol(String cdsisrol)throws Exception;
	
	public void moverTramite(
			String ntramite
			,String nuevoStatus
			,String comments
			,String cdusuariSesion
			,String cdsisrolSesion
			,String cdusuariDestino
			,String cdsisrolDestino
			,String cdmotivo
			,String cdclausu
			) throws Exception;

	public String obtieneMontoArancelCPT(String tipoConcepto, String idProveedor, String idConceptoTipo) throws Exception;

	public void eliminacionDocumentosxTramite(String ntramite) throws Exception;
	
	public String obtieneMesesTiempoEsperaICDCPT(String valorICDCPT, String nomTabla) throws Exception;

	public List<Map<String,String>> obtieneFormatoCalculo(Map<String, Object> params) throws Exception;
	
	public List<GenericVO> obtieneListadoRamoSalud()  throws Exception;

	public List<Map<String, String>> obtieneDatosAdicionales(Map<String, Object> params) throws Exception;

	public String eliminarAsegurado(HashMap<String, Object> paramsTworkSin) throws Exception;

	public List<Map<String, String>> obtieneDatosAdicionalesCobertura(Map<String, Object> params) throws Exception;

	public String obtieneTramiteFacturaPagada(String nfactura, String cdpresta) throws Exception;
	
	public List<Map<String,String>> obtenerAseguradosTramite(Map<String, String> params) throws Exception;

	public String actualizaValorMC(HashMap<String, Object> modMesaControl) throws Exception;

	public List<GenericVO> obtieneListaTipoAtencion(String cdramo,String tipoPago) throws Exception;

	public List<Map<String, String>> obtieneListaAutirizacionServicio(Map<String, Object> params) throws Exception;

	public List<Map<String, String>> obtieneListaDatosValidacionSiniestro(HashMap<String, Object> params) throws Exception;
	
	public String validaCdTipsitAltaTramite(HashMap<String, Object> paramTramite) throws Exception;
}