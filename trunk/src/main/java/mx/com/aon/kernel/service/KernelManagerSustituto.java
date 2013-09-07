/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.aon.kernel.service;

import java.util.List;
import java.util.Map;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.flujos.cotizacion.model.AyudaCoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.CoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.ResultadoCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.SituacionVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.Tatrisit;

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
    public WrapperResultados clonaPersonas(Map<String,Object> parameters) throws ApplicationException;
    public List<ResultadoCotizacionVO> obtenerResultadosCotizacion(Map<String,String> parameters) throws ApplicationException;
    public List<CoberturaCotizacionVO> obtenerCoberturas(Map<String,String> parameters) throws ApplicationException;
    public AyudaCoberturaCotizacionVO obtenerAyudaCobertura(String idCobertura,String idRamo,String idCiaAsegurador) throws ApplicationException;
    public List<Tatrisit> obtenerTatrisit(String cdtipsit) throws ApplicationException;
    public DatosUsuario obtenerDatosUsuario(String cdusuario) throws ApplicationException;
    public WrapperResultados comprarCotizacion(Map<String,String> parameters) throws ApplicationException;
}