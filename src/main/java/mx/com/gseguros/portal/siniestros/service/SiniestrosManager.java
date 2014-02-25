package mx.com.gseguros.portal.siniestros.service;

import java.util.HashMap;
import java.util.List;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.siniestros.model.AutorizaServiciosVO;
import mx.com.gseguros.portal.siniestros.model.AutorizacionServicioVO;
import mx.com.gseguros.portal.siniestros.model.CoberturaPolizaVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaManteniVO;
import mx.com.gseguros.portal.siniestros.model.ListaFacturasVO;
import mx.com.gseguros.portal.siniestros.model.PolizaVigenteVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaPorcentajeVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaProveedorVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTDETAUTSVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTTAPVAATVO;
import mx.com.gseguros.portal.siniestros.model.DatosSiniestroVO;

public interface SiniestrosManager {
	
	/**
	 * Obtiene la información de la Autorización de servicio en especifico
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
	 * Obtiene la información de la lista de autorizaciones
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

	public List<HashMap<String, String>> loadListaIncisosRechazos(HashMap<String, String> params) throws ApplicationException;

	public boolean guardaEstatusDocumentos(HashMap<String, String> params, List<HashMap<String, String>> saveList) throws ApplicationException;
	
	public List<HashMap<String, String>> loadListaRechazos() throws ApplicationException;

	public boolean rechazarTramite(HashMap<String, String> params) throws ApplicationException;

	public boolean solicitarPago(HashMap<String, String> params) throws ApplicationException;

	public List<ConsultaTTAPVAATVO> getConsultaListaTTAPVAAT(HashMap<String, Object> paramTTAPVAAT) throws ApplicationException;

	public List<ConsultaPorcentajeVO> getConsultaListaPorcentaje(String cdcpt, String cdtipmed,String mtobase) throws ApplicationException;

	public List<GenericVO> getConsultaListaPlaza() throws ApplicationException;

	public String guardaListaFacMesaControl(HashMap<String, Object> paramsFacMesaCtrl) throws ApplicationException;

	public String guardaListaTworkSin(HashMap<String, Object> paramsTworkSin) throws ApplicationException;

	public String getAltaSiniestroAutServicio(String nmautser) throws ApplicationException;

	public String getAltaSiniestroAltaTramite(String ntramite) throws ApplicationException;

	public String getAltaMsinival(HashMap<String, Object> paramMsinival) throws ApplicationException;

	public List<ListaFacturasVO> getConsultaListaFacturas(HashMap<String, Object> paramFact) throws ApplicationException;

	public String getBajaMsinival(HashMap<String, Object> paramBajasinival) throws ApplicationException;
	
	public List<GenericVO> obtieneListadoCobertura(String cdramo,String cdtipsit) throws ApplicationException;
	
	public String actualizaOTValorMesaControl(HashMap<String, Object> params) throws ApplicationException;
}