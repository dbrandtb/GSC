package mx.com.gseguros.portal.siniestros.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal2.web.GenericVO;
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

	public List<HashMap<String, String>> loadListaIncisosRechazos(HashMap<String, String> params) throws DaoException;

	public List<HashMap<String, String>> loadListaRechazos() throws DaoException;
	
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

	public String actualizaOTValorMesaControl(HashMap<String, Object> params) throws DaoException;

}
