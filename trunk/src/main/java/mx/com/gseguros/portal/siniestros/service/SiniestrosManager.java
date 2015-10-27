package mx.com.gseguros.portal.siniestros.service;

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
	 * @throws Exception
	 */
	public List<AutorizacionServicioVO> getConsultaAutorizacionesEsp(String nmautser) throws Exception;

	/**
	 * Obtiene la lista de los asegurados
	 * @return List GenericVO
	 * @throws Exception
	 */
	public List<GenericVO> getConsultaListaAsegurado(String cdperson) throws Exception;
	
	/**
	 * Obtiene la informaci�n de la lista de autorizaciones
	 * @param cdperson
	 * @return List AutorizaServiciosVO  
	 * @throws Exception
	 */
	public List<AutorizaServiciosVO> getConsultaListaAutorizaciones(String tipoAut,String cdperson) throws Exception;
	
	/**
	 * Obtiene la lista de los Proveedores y Medicos
	 * @param tipoprov
	 * @param cdpresta
	 * @return List GenericVO
	 * @throws Exception
	 */
	public List<ConsultaProveedorVO> getConsultaListaProveedorMedico(String tipoprov,String cdpresta) throws Exception;
	
	public List<CoberturaPolizaVO> getConsultaListaCoberturaPoliza(HashMap<String, Object> paramCobertura) throws Exception;
	
	public List<CoberturaPolizaVO> getConsultaCoberturaAsegurado(HashMap<String, Object> paramCobertura) throws Exception;
	
	public List<DatosSiniestroVO> getConsultaListaDatSubGeneral(HashMap<String, Object> paramDatSubGral) throws Exception;
	
	public List<GenericVO> getConsultaListaSubcobertura(String cdunieco, String cdramo, String estado, String nmpoliza,
			String nmsituac, String cdtipsit, String cdgarant, String cdsubcob) throws Exception;
	
	public List<GenericVO> getConsultaListaSubcoberturaTotales() throws Exception;
	
	public List<GenericVO> getConsultaListaSubcoberturaRecupera() throws Exception;
	
	public List<GenericVO> getConsultaListaCPTICD(String cdtabla, String otclave) throws Exception;
	
	public List<GenericVO> getConsultaListaTipoPago(String cdramo) throws Exception;
	
	public List<ConsultaTDETAUTSVO> getConsultaListaTDeTauts(String nmautser) throws Exception;
	
	public List<ConsultaManteniVO> getConsultaListaTipoMedico(String codigo) throws Exception;
	
	public String guardaListaTDeTauts(HashMap<String, Object> paramsTDeTauts) throws Exception;
	
	public List<PolizaVigenteVO> getConsultaListaPoliza(String cdperson,String cdramo) throws Exception;
	
	public void getEliminacionRegistros(String nmautser) throws Exception;
	
	public List<AutorizacionServicioVO> guardarAutorizacionServicio(HashMap<String, Object> paramsR) throws Exception;
	
	public List<HashMap<String, String>> loadListaDocumentos(Map<String, String> params) throws Exception;

	public String generaContraRecibo(HashMap<String, Object> params) throws Exception;

	public List<Map<String, String>> loadListaIncisosRechazos(Map<String, String> params) throws Exception;

	public boolean guardaEstatusDocumentos(HashMap<String, String> params, List<HashMap<String, String>> saveList) throws Exception;

	public List<Map<String, String>> loadListaRechazos() throws Exception;

	public boolean rechazarTramite(HashMap<String, String> params) throws Exception;

	public List<SiniestroVO> solicitudPagoEnviada(Map<String, String> params) throws Exception;

	public List<ConsultaTTAPVAATVO> getConsultaListaTTAPVAAT(HashMap<String, Object> paramTTAPVAAT) throws Exception;

	public List<ConsultaPorcentajeVO> getConsultaListaPorcentaje(String cdcpt, String cdtipmed,String mtobase) throws Exception;

	public List<GenericVO> getConsultaListaPlaza() throws Exception;
	
	public String guardaListaFacturaSiniestro(
			String ntramite,
			String nfactura,
			Date fefactura,
			String cdtipser,
			String cdpresta,
			String ptimport,
			String cdgarant,
			String cdconval,
			String descporc,
			String descnume,
			String cdmoneda,
			String tasacamb,
			String ptimporta,
			String dctonuex,
			Date feegreso,
			String diasdedu,
			String nombProv,
			String tipoAccion
	) throws Exception;
	
	
	/*public String guardaListaFacMesaControl(
			String ntramite,
			String nfactura,
			Date fefactura,
			String cdtipser,
			String cdpresta,
			String ptimport,
			String cdgarant,
			String cdconval,
			String descporc,
			String descnume,
			String cdmoneda,
			String tasacamb,
			String ptimporta,
			String dctonuex,
			Date feegreso,
			String diasdedu,
			String tipoAccion
			) throws Exception;
	
	public String guardaListaFacMesaControl2(
			String ntramite,
			String nfactura,
			Date fefactura,
			String cdtipser,
			String cdpresta,
			String ptimport,
			String cdgarant,
			String cdconval,
			String descporc,
			String descnume,
			String cdmoneda,
			String tasacamb,
			String ptimporta,
			String dctonuex,
			Date feegreso,
			String diasdedu,
			String tipoAccion,
			String nombProv
			) throws Exception;*/

	/*public String movFacMesaControl(
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
			String operacion,
			String cdmoneda,
			String tasacamb,
			String ptimporta,
			String dctonuex
			) throws Exception;*/

	public String guardaListaTworkSin(HashMap<String, Object> paramsTworkSin) throws Exception;

	public String getAltaSiniestroAutServicio(String nmautser,String nfactura) throws Exception;

	public String getAltaSiniestroAltaTramite(String ntramite) throws Exception;

	public String getAltaSiniestroSinAutorizacion(String ntramite,String cdunieco,String cdramo, String estado,String nmpoliza,
			  									  String nmsuplem,String nmsituac, String cdtipsit, Date fechaOcurrencia,String nfactura) throws Exception;
	
	public String getAltaMsinival(HashMap<String, Object> paramMsinival) throws Exception;

	public List<ListaFacturasVO> getConsultaListaFacturas(HashMap<String, Object> paramFact) throws Exception;

	public String getBajaMsinival(HashMap<String, Object> paramBajasinival) throws Exception;
	
	public List<GenericVO> obtieneListadoCobertura(String cdramo,String cdtipsit) throws Exception;
	
	public List<GenericVO> obtieneListadoCoberturaTotales() throws Exception;
	
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
	public List<Map<String,String>> listaSiniestrosTramite(String ntramite,String procesoInterno) throws Exception;
	
	public List<Map<String,String>> listaSiniestrosMsiniesTramite(String ntramite,String factura,String procesoInterno) throws Exception;
	
	public List<Map<String,String>> listaAseguradosTramite(String ntramite, String nfactura, String tipoProceso) throws Exception;
	
	public List<Map<String,String>> listaSiniestrosTramite2(String ntramite,String nfactura,String procesoInterno) throws Exception;
	
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
	
	public List<Map<String,String>> obtenerAseguradosTramite(String ntramite,String nfactura) throws Exception;
	
	public List<Map<String,String>> obtenerInfAseguradosTramite(String ntramite) throws Exception;
	
	public List<HashMap<String,String>> obtenerFacturasTramiteSiniestro(String ntramite, String siniestro) throws Exception;

	/**
	 * PKG_PRESINIESTRO.P_UPD_NMAUTSER_TWORKSIN
	 */
	
	public void actualizarAutorizacionTworksin(String ntramite, String nmpoliza, String cdperson,String nmautser,String nfactura,Date feocurrencia) throws Exception;
	
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
			String accion,
			String ptpcioex,
			String dctoimex,
			String ptimpoex,
			String mtoArancel,
			String aplicIVA) throws Exception;
	
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
			String nmsituac,String aaapertu,String status,String nmsinies,String cdtipsit) throws Exception;

	public List<Map<String,String>> cargaHistorialSiniestros(Map<String,String> params) throws Exception;

	public List<GenericVO>obtenerCodigosMedicos(String idconcep, String subcaden) throws Exception;
	
	public List<GenericVO>obtenerCodigosMedicosTotales() throws Exception;
	
	public Map<String,String>obtenerLlaveSiniestroReembolso(String ntramite) throws Exception;
	
	public List<Map<String,String>> obtieneDatosGeneralesSiniestro(String cdunieco, String cdramo, String estado, String nmpoliza, 
			String nmsituac, String nmsuplem, String status, String aaapertu, String nmsinies, String ntramite) throws Exception;
	
	public Map<String, Object> actualizaDatosGeneralesSiniestro(String cdunieco, String cdramo, String estado, 
			String nmpoliza, String nmsuplem, String aaapertu, String nmsinies, Date feocurre,
			String nmreclamo, String cdicd, String cdicd2, String cdcausa,String cdgarant, 
			String cdconval, String nmautser, String cdperson, String tipoProceso, String complemento
			) throws Exception;
	
	public Map<String, Object> actualizaMsiniestroReferenciado(String cdunieco, String cdramo, String estado, String nmpoliza, String nmsuplem,
			String nmsituac, String aaapertu, String status, String nmsinies, String nmsinref) throws Exception;
	
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
			String nfactura,
			String cdtipsit) throws Exception;
	
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
			String nfactura,
			String tipoPago,
			String cdtipsit) throws Exception;
	
	public Map<String,String>obtenerRentaDiariaxHospitalizacion(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsituac,
			String nmsuplem) throws Exception;
	
	public String validaPorcentajePenalizacion(String zonaContratada, String zonaAtencion, String cdRamo) throws Exception;

	public String validaAutorizacionProceso(String nmAutSer) throws Exception;

	public String validaDocumentosCargados(HashMap<String, String> params) throws Exception;

	public List<Reclamo> obtieneDatosReclamoWS(Map<String, Object> params) throws Exception;

	public void getCambiarEstatusMAUTSERV(String nmautser,String status) throws Exception;

	public List<AltaTramiteVO> getConsultaListaAltaTramite(String ntramite) throws Exception;

	public List<MesaControlVO> getConsultaListaMesaControl(String ntramite) throws Exception;

	public void getEliminacionAsegurado(String ntramite, String factura, String valorAccion) throws Exception;

	public void getEliminacionFacturaTramite(String ntramite, String nfactura, String valorAccion) throws Exception;
	
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
			,boolean enviado
			,String nmsecsin) throws Exception;
	
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
	
	public void guardarTotalProcedenteFactura(String ntramite,String nfactura,String importe, String nmsecsin)throws Exception;
	
	public void turnarTramite(String ntramite,String cdsisrol,String cdusuari) throws Exception;
	
	public List<Map<String,String>> obtenerUsuariosPorRol(String cdsisrol)throws Exception;
	
	public Map<String,Object> moverTramite(
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
	
	public void turnarAutServicio(
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
	
	public String obtieneMontoArancelCPT(String tipoConcepto, String idProveedor, String idConceptoTipo)throws Exception;

	public String porcentajeQuirurgico(String tipoMedico, String feAutorizacion)throws Exception;
	
	public void eliminaDocumentosxTramite(String ntramite) throws Exception;

	public String obtieneMesesTiempoEspera(String valorICDCPT, String nomTabla) throws Exception;
	
	public String obtieneUsuarioTurnadoSiniestro(String ntramite, String rolDestino) throws Exception;
	//public String requiereAutorizacionServ(String cobertura, String subcobertura)throws Exception;
	
	public List<GenericVO> getConsultaListaRamoSalud() throws Exception;

	public List<Map<String, String>> requiereInformacionAdicional(String cobertura, String subcobertura, String cdramo, String cdtipsit) throws Exception;
	
	public List<Map<String, String>> listaConsultaCirculoHospitalarioMultisalud(String cdpresta, String cdramo, Date feautori) throws Exception;
	
	public String penalizacionCirculoHospitalario(HashMap<String, Object> paramPenalizacion) throws Exception;

	public String eliminarAsegurado(HashMap<String, Object> paramsTworkSin) throws Exception;

	public List<Map<String, String>> obtenerDatosAdicionalesCobertura(String ntramite) throws Exception;
	
    public String obtieneTramiteEnProceso(String nfactura, String cdpresta, String ptimport) throws Exception;

	public String actualizaValorMC(HashMap<String, Object> modMesaControl) throws Exception;

	public List<GenericVO> getconsultaListaTipoAtencion(String cdramo,String tipoPago) throws Exception;

	public  List<Map<String, String>> getConsultaListaAutServicioSiniestro(String cdperson)throws Exception;
	
	public  List<Map<String, String>> getConsultaListaMSiniestMaestro(String cdunieco,String cdramo, String estado, String nmpoliza, 
																	  String nmsuplem,String nmsituac,String status)throws Exception;

	public List<Map<String, String>> getConsultaDatosValidacionSiniestro(String ntramite,String nfactura,String tipoPago)throws Exception;
	
	public List<Map<String, String>> getConsultaDatosValidacionAjustadorMed(String ntramite)throws Exception;
	
	public String validaCdTipsitAltaTramite(HashMap<String, Object> paramTramite) throws Exception;

	public List<Map<String, String>> getConsultaDatosSumaAsegurada(String cdunieco, String cdramo,String estado,String nmpoliza, String cdperson, String nmsinref) throws Exception;

	public List<Map<String, String>> listaSumaAseguradaPeriodoEsperaRec(String  cdramo, String cobertura, String subcobertura, Date feEfecto) throws Exception;
	
	public List<Map<String, String>> listaEsquemaSumaAseguradaRec(String cdunieco,String cdramo, String estado, String nmpoliza, String nmsituac) throws Exception;

	public List<Map<String, String>> listaPeriodoEsperaAsegurado(String cdunieco,String cdramo, String estado, String nmpoliza, String nmsituac,  Date feOcurre) throws Exception;

	public List<Map<String, String>> obtieneMontoPagoSiniestro(String ntramite) throws Exception;
	
	public List<GenericVO> getConsultaListaConceptoPago(String cdramo) throws Exception;
	
	public List<GenericVO> getConsultaListaAseguradoPoliza(String cdunieco, String cdramo, String estado, String nmpoliza) throws Exception;

	public List<Map<String, String>> obtieneDatosBeneficiario(String cdunieco,String cdramo, String estado, String nmpoliza, String cdperson) throws Exception;
	
	public List<Map<String, String>> obtenerDatoMsiniper(String ntramite) throws Exception;
	
	public List<Map<String, String>> getConsultaConfiguracionProveedor(String cdpresta)throws Exception;
	
	public String obtieneAplicaConceptoIVA(String idConcepto)throws Exception;
	
	public String guardaConfiguracionProveedor(String cdpresta, String aplicaIVA,String secuenciaIVA, String aplicaIVARET, String proceso) throws Exception;
	
	public List<GenericVO>obtenerAtributosLayout(String descripcion) throws Exception;
	
	public String guardaLayoutProveedor(String cdpresta, String claveAtributo, String claveFormatoAtributo,
			String valorMinimo, String valorMaximo, String columnaExcel, String claveFormatoFecha, String nmordina, String tipoAccion) throws Exception;

	public List<Map<String, String>> consultaConfiguracionLayout(String cdpresta) throws Exception;
	
	public List<Map<String, String>> guardaHistorialSiniestro(String ntramite, String nfactura) throws Exception;
	
	public String guardaListaRecupera(String ntramite, String nfactura, String cdgarant, String cdconval, String cantporc, String ptimport, String tipoAccion) throws Exception;
	
	public List<Map<String, String>> obtieneInformacionRecupera(String cdunieco,String cdramo, String estado, String nmpoliza, String nmsuplem,
			String nmsituac, Date feEfecto, String ntramite, String nfactura) throws Exception;
	
	public List<Map<String, String>> obtieneEsquemaSumAseguradaRecupera(String cdunieco,String cdramo, String estado, String nmpoliza, String nmsuplem,
			String nmsituac, Date feEfecto, String cdgarant, String cdconval) throws Exception;
	
	public void P_MOV_MRECUPERA(String ntramite,String nfactura, String cdgarant, String cdconval,
			String cantporc,String ptimport, String accion) throws Exception;
	
	public String actualizaTelefonoEmailAsegurado(HashMap<String, Object> paramsAsegurado) throws Exception;
	
	public void guardaAutorizacionConceptos(String cdunieco, String cdramo, String estado, String nfactura, String nmautser, String cdpresta, String cdperson) throws Exception;
	
	public List<Map<String,String>> cargaHistorialCPTPagados(Map<String,String> params) throws Exception;
}
