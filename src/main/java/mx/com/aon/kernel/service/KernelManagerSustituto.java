/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.aon.kernel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.flujos.cotizacion.model.AyudaCoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.CoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.ResultadoCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.SituacionVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.Tatri;

/**
 *
 * @author Jair
 */
public interface KernelManagerSustituto {
    public WrapperResultados calculaNumeroPoliza(String pv_cdunieco_i, String pv_cdramo_i, String pv_estado_i) throws ApplicationException;
    public WrapperResultados insertaMaestroPolizas(Map<String, String> parameters) throws ApplicationException;
    public WrapperResultados insertaPolisit(Map<String, Object> parameters) throws ApplicationException;
    public WrapperResultados insertaValoresSituaciones(Map<String, String> parameters) throws ApplicationException;
    public List<SituacionVO> clonaSituaciones(Map<String,String> parameters) throws ApplicationException;
    public WrapperResultados coberturas(Map<String,String> parameters) throws ApplicationException;
    public WrapperResultados ejecutaASIGSVALIPOL(Map<String,String> parameters) throws ApplicationException;
    public WrapperResultados ejecutaASIGSVALIPOL_EMI(Map<String,String> parameters) throws ApplicationException;
    public WrapperResultados clonaPersonas(Map<String,Object> parameters) throws ApplicationException;
    public List<ResultadoCotizacionVO> obtenerResultadosCotizacion(Map<String,String> parameters) throws ApplicationException;
    public List<CoberturaCotizacionVO> obtenerCoberturas(Map<String,String> parameters) throws ApplicationException;
    public AyudaCoberturaCotizacionVO obtenerAyudaCobertura(String idCobertura,String idRamo,String idCiaAsegurador) throws ApplicationException;
    public List<Tatri> obtenerTatrisit(String cdtipsit) throws ApplicationException;
    public List<Tatri> obtenerTatripol(String[] args) throws ApplicationException;
    public DatosUsuario obtenerDatosUsuario(String cdusuario) throws ApplicationException;
    public WrapperResultados movDetalleSuplemento(Map<String,Object> parameters) throws ApplicationException;
    public WrapperResultados comprarCotizacion(Map<String,String> parameters) throws ApplicationException;
    public Map<String,Object> getInfoMpolizas(Map<String,Object>parameters) throws ApplicationException;
    public List<GenericVO> getTmanteni(String tabla) throws ApplicationException;
	public List<Map<String, Object>> obtenerAsegurados(Map<String, String> map1) throws ApplicationException;
	public Map<String,Object> getInfoMpolizasCompleta(Map<String,String> parameters) throws ApplicationException;
	public WrapperResultados pMovTvalopol(Map<String, String> parameters) throws ApplicationException;
	public WrapperResultados pMovTvalogar(Map<String, String> parameters) throws ApplicationException;
	public WrapperResultados pMovTvaloper(Map<String, String> parameters) throws ApplicationException;
	//requiere de su propio catch si no hay datos:
	public Map<String,Object> pGetTvalopol(Map<String,String> parameters) throws ApplicationException;
	public String generaCdperson() throws ApplicationException;
	public WrapperResultados movMpersona(Map<String,Object> parameters) throws ApplicationException;
	public WrapperResultados movMpoliper(Map<String,Object> parameters) throws ApplicationException;
	public WrapperResultados borraMpoliper(Map<String,Object> parameters) throws ApplicationException;
	public List<Map<String,String>> obtenerCoberturasUsuario(Map<String,String> parametros) throws ApplicationException;
	public WrapperResultados movPoligar(Map<String, String> param) throws ApplicationException;
	public WrapperResultados movPolicap(Map<String, String> param) throws ApplicationException;
	public List<Map<String, String>> obtenerDetallesCotizacion(Map<String, String> params) throws ApplicationException;
	public List<Tatri> obtenerTatrigar(Map<String, String> smap1) throws ApplicationException;
	//requiere de su propio catch si no hay datos:
	public Map<String, Object> obtenerValoresTatrigar(Map<String, String> param) throws ApplicationException;
	public List<Tatri> obtenerTatriper(Map<String, String> smap1) throws ApplicationException;
	//requiere de su propio catch si no hay datos:
	public Map<String, Object> obtenerValoresTatriper(Map<String, String> smap1) throws ApplicationException;
	public Map<String, String> obtenerDomicilio(Map<String, String> params) throws ApplicationException;
	public WrapperResultados pMovMdomicil(Map<String, String> paramDomicil) throws ApplicationException;
	public WrapperResultados emitir(Map<String, Object> paramEmi) throws ApplicationException;
	public WrapperResultados guardarArchivo(Map<String, Object> params) throws ApplicationException;
	public List<Map<String,String>>obtenerDocumentosPoliza(Map<String,Object>params) throws ApplicationException;
	public List<Map<String, String>> obtenerListaDocumentos(Map<String, String> paramsGetDoc) throws ApplicationException;
	public WrapperResultados insertaMaestroHistoricoPoliza(Map<String, Object> param) throws ApplicationException;
	public WrapperResultados movMPoliage(Map<String, Object> param) throws ApplicationException;
	public List<Map<String, String>> cargarTiposClausulasExclusion() throws ApplicationException;
	public List<Map<String, String>> obtenerExclusionesPorTipo(Map<String, String> smap1) throws ApplicationException;
	public Map<String, String> obtenerHtmlClausula(Map<String, String> paramObtenerHtml) throws ApplicationException;
	public WrapperResultados PMovMpolicot(Map<String, String> smap1) throws ApplicationException;
	public List<Map<String, String>> obtenerPolicot(Map<String, String> smap1) throws ApplicationException;
	public WrapperResultados PMovMesacontrol(Map<String, Object> parMesCon) throws ApplicationException;
	public List<Map<String, String>> loadMesaControl(String dsrol) throws ApplicationException;
	public WrapperResultados obtenDatosRecibos(HashMap<String,Object> params) throws ApplicationException;
	public WrapperResultados cargaColonias(String codigoPostal) throws ApplicationException;
	public WrapperResultados obtenDatosClienteWS(HashMap<String,Object> params) throws ApplicationException;
	public WrapperResultados mesaControlUpdateSolici(String ntramite,String nmsolici) throws ApplicationException;
	public WrapperResultados mesaControlUpdateStatus(String ntramite,String status) throws ApplicationException;
	public WrapperResultados movDmesacontrol(Map<String, Object> params) throws ApplicationException;
	public List<Map<String, String>> obtenerDetalleMC(Map<String, String> smap1) throws ApplicationException;
	public WrapperResultados mesaControlFinalizarDetalle(Map<String, String> smap1) throws ApplicationException;
	public WrapperResultados preparaContrarecibo(Map<String, String> docu) throws ApplicationException;
}