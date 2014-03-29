package mx.com.gseguros.portal.siniestros.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.ApplicationException;
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

public interface SiniestrosManager {
	
	/**
	 * Obtiene la informaci�n de la Autorizaci�n de servicio en especifico
	 * @param nmautser
	 * @return List AutorizacionServicioVO
	 * @throws ApplicationException
	 */
	public List<AutorizacionServicioVO> getConsultaAutorizacionesEsp(String nmautser) throws ApplicationException;

	/**
	 * Obtiene la lista de los asegurados
	 * @return List GenericVO
	 * @throws ApplicationException
	 */
	public List<GenericVO> getConsultaListaAsegurado(String cdperson) throws ApplicationException;
	
	/**
	 * Obtiene la informaci�n de la lista de autorizaciones
	 * @param cdperson
	 * @return List AutorizaServiciosVO  
	 * @throws ApplicationException
	 */
	public List<AutorizaServiciosVO> getConsultaListaAutorizaciones(String tipoAut,String cdperson) throws ApplicationException;
	
	/**
	 * Obtiene la lista de los Proveedores y Medicos
	 * @param tipoprov
	 * @param cdpresta
	 * @return List GenericVO
	 * @throws ApplicationException
	 */
	public List<ConsultaProveedorVO> getConsultaListaProveedorMedico(String tipoprov,String cdpresta) throws ApplicationException;
	
	
	//public List<GenericVO> getConsultaListaCausaSiniestro(String cdcausa) throws ApplicationException;
	
	
	public List<CoberturaPolizaVO> getConsultaListaCoberturaPoliza(HashMap<String, Object> paramCobertura) throws ApplicationException;
	
	public List<DatosSiniestroVO> getConsultaListaDatSubGeneral(HashMap<String, Object> paramDatSubGral) throws ApplicationException;
	
	public List<GenericVO> getConsultaListaSubcobertura(String cdgarant, String cdsubcob) throws ApplicationException;
	
	public List<GenericVO> getConsultaListaCPTICD(String cdtabla, String otclave) throws ApplicationException;
	
	//public List<GenericVO> getConsultaListaMotivoRechazo(String cdmotRechazo) throws ApplicationException;
	
	public List<ConsultaTDETAUTSVO> getConsultaListaTDeTauts(String nmautser) throws ApplicationException;
	
	public List<ConsultaManteniVO> getConsultaListaManteni(String cdtabla, String codigo) throws ApplicationException;
	
	public String guardaListaTDeTauts(HashMap<String, Object> paramsTDeTauts) throws ApplicationException;
	
	public List<PolizaVigenteVO> getConsultaListaPoliza(String cdperson) throws ApplicationException;
	
	public void getEliminacionRegistros(String nmautser) throws ApplicationException;
	
	/* ############################################################################## 
	 * ##################################### VERIFICAR ##############################*/
	
	public List<AutorizacionServicioVO> guardarAutorizacionServicio(HashMap<String, Object> paramsR) throws ApplicationException;
	
	public List<HashMap<String, String>> loadListaDocumentos(HashMap<String, String> params) throws ApplicationException;

	public String generaContraRecibo(HashMap<String, Object> params) throws ApplicationException;

	public List<Map<String, String>> loadListaIncisosRechazos(Map<String, String> params) throws ApplicationException;

	public boolean guardaEstatusDocumentos(HashMap<String, String> params, List<HashMap<String, String>> saveList) throws ApplicationException;

	public List<Map<String, String>> loadListaRechazos() throws ApplicationException;

	public boolean rechazarTramite(HashMap<String, String> params) throws ApplicationException;

	public List<SiniestroVO> solicitudPagoEnviada(Map<String, String> params) throws ApplicationException;

	public List<ConsultaTTAPVAATVO> getConsultaListaTTAPVAAT(HashMap<String, Object> paramTTAPVAAT) throws ApplicationException;

	public List<ConsultaPorcentajeVO> getConsultaListaPorcentaje(String cdcpt, String cdtipmed,String mtobase) throws ApplicationException;

	public List<GenericVO> getConsultaListaPlaza() throws ApplicationException;

	public String guardaListaFacMesaControl(
			String ntramite,
			String nfactura,
			String fefactura,
			String cdtipser,
			String cdpresta,
			String ptimport,
			String cdgarant,
			String cdconval,
			String descporc,
			String descnume
			) throws ApplicationException;

	public String movFacMesaControl(
			String ntramite,
			String nfactura,
			String fefactura,
			String cdtipser,
			String cdpresta,
			String ptimport,
			String cdgarant,
			String cdconval,
			String descporc,
			String descnume,
			String operacion
			) throws ApplicationException;

	public String guardaListaTworkSin(HashMap<String, Object> paramsTworkSin) throws ApplicationException;

	public String getAltaSiniestroAutServicio(String nmautser) throws ApplicationException;

	public String getAltaSiniestroAltaTramite(String ntramite) throws ApplicationException;

	public String getAltaMsinival(HashMap<String, Object> paramMsinival) throws ApplicationException;

	public List<ListaFacturasVO> getConsultaListaFacturas(HashMap<String, Object> paramFact) throws ApplicationException;

	public String getBajaMsinival(HashMap<String, Object> paramBajasinival) throws ApplicationException;
	
	public List<GenericVO> obtieneListadoCobertura(String cdramo,String cdtipsit) throws ApplicationException;
	
	public String actualizaOTValorMesaControl(Map<String, Object> params) throws ApplicationException;
	
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
	public List<Map<String,String>> listaSiniestrosTramite(String ntramite) throws Exception;
	
	/**
	 * PKG_PRESINIESTRO.P_GET_TRAMITE_COMPLETO
	 */
	public Map<String,String> obtenerTramiteCompleto(String ntramite) throws Exception;
	
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
	public List<Map<String,String>> obtenerFacturasTramite(String ntramite) throws Exception;

	public List<HashMap<String,String>> obtenerFacturasTramiteSiniestro(String ntramite, String siniestro) throws Exception;

	/**
	 * PKG_PRESINIESTRO.P_UPD_NMAUTSER_TWORKSIN
	 */
	public void actualizarAutorizacionTworksin(String ntramite, String nmpoliza, String cdperson,String nmautser) throws Exception;

	public void actualizaMsinies(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsitauc,
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
	
	/**
	 * CDUNIECO,CDRAMO,ESTADO,NMPOLIZA,NMSUPLEM,
		NMSITUAC,AAAPERTU,STATUS,NMSINIES,NFACTURA,
		CDGARANT,CDCONVAL,CDCONCEP,IDCONCEP,CDCAPITA,
		NMORDINA,FEMOVIMI,CDMONEDA,PTPRECIO,CANTIDAD,
		DESTOPOR,DESTOIMP,PTIMPORT,PTRECOBR,NMANNO,
		NMAPUNTE,USERREGI,FEREGIST
	 */
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
	
	public List<PolizaVigenteVO> getConsultaPolizaUnica(HashMap<String, Object> paramPolUnica) throws Exception;

	public String validaExclusionPenalizacion(HashMap<String, Object> paramExclusion) throws Exception;
	
	public void P_MOV_TDSINIVAL(
			String cdunieco,String cdramo,String estado,String nmpoliza,String nmsuplem,
			String nmsituac,String aaapertu,String status,String nmsinies,String nfactura,
			String cdgarant,String cdconval,String cdconcep,String idconcep,String nmordina,
			String nmordmov,String ptimport,String comments,String userregi,Date feregist,String accion) throws Exception;
	
	/**
	 * CDUNIECO,CDRAMO,ESTADO,NMPOLIZA,NMSUPLEM,
		NMSITUAC,AAAPERTU,STATUS,NMSINIES,NFACTURA,
		CDGARANT,CDCONVAL,CDCONCEP,IDCONCEP,NMORDINA,
		NMORDMOV,PTIMPORT,COMMENTS,USERREGI,FEREGIST
	 */
	public List<Map<String,String>>P_GET_TDSINIVAL(
			String cdunieco,String cdramo,String estado,String nmpoliza,String nmsuplem,
			String nmsituac,String aaapertu,String status,String nmsinies,String nfactura,
			String cdgarant,String cdconval,String cdconcep,String idconcep,String nmordina) throws Exception;
	
	public List<Map<String,String>>P_GET_FACTURAS_SINIESTRO(
			String cdunieco,String cdramo,String estado,String nmpoliza,String nmsuplem,
			String nmsituac,String aaapertu,String status,String nmsinies) throws Exception;

	public List<Map<String,String>> cargaHistorialSiniestros(Map<String,String> params) throws Exception;

	public List<GenericVO>obtenerCodigosMedicos(String idconcep, String subcaden) throws Exception;
	
	public Map<String,String>obtenerLlaveSiniestroReembolso(String ntramite) throws Exception;
	
	public List<Map<String,String>> obtieneDatosGeneralesSiniestro(String cdunieco, String cdramo, String estado, String nmpoliza, 
			String nmsituac, String nmsuplem, String status, String aaapertu, String nmsinies, String ntramite) throws Exception;
	
	public Map<String, Object> actualizaDatosGeneralesSiniestro(String cdunieco, String cdramo, String estado, 
			String nmpoliza, String nmsuplem, String aaapertu, String nmsinies, Date feocurre,
			String nmreclamo, String cdicd, String cdicd2, String cdcausa) throws Exception;
	
	public List<HistorialSiniestroVO> obtieneHistorialReclamaciones(String cdunieco, String cdramo, String estado, String nmpoliza, 
			String nmsituac, String nmsuplem, String status, String aaapertu, String nmsinies, String ntramite) throws Exception;
	
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
	
	public Map<String,String> obtenerDatosProveedor(String cdpresta) throws Exception;
	
	/**
	 * PKG_PRESINIESTRO.P_GET_DATOS_SUBG
	 * {CDGARANT=18SD, OTCLAVE2=18SD003, CDCAPITA=4,
 LUC=SI, DEDUCIBLE=NA, COPAGO=25, BENEFMAX=NA, ICD=SI, CPT=SI, LIMITES=NA, TIPOCOPAGO=%, UNIDAD=null, :B11=0}
	 */
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

	public String validaPorcentajePenalizacion(String zonaContratada, String zonaAtencion) throws Exception;

	public String validaAutorizacionProceso(String nmAutSer) throws Exception;

	public String validaDocumentosCargados(HashMap<String, String> params) throws Exception;

	public List<Reclamo> obtieneDatosReclamoWS(Map<String, Object> params) throws Exception;

	public void getCambiarEstatusMAUTSERV(String nmautser,String status) throws Exception;

	public List<AltaTramiteVO> getConsultaListaAltaTramite(String ntramite) throws Exception;

	public List<MesaControlVO> getConsultaListaMesaControl(String ntramite) throws Exception;

	public void getEliminacionTworksin(String ntramite) throws Exception;

	public void getEliminacionTFacMesaControl(String ntramite) throws Exception;
	
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

	public String validaDocumentosAutServicio(String ntramite) throws Exception;
	
}