/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.aon.kernel.service;

import java.util.List;
import java.util.Map;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.flujos.cotizacion.model.CoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.ResultadoCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.SituacionVO;
import mx.com.aon.portal.util.WrapperResultados;

/**
 *
 * @author Jair
 */
public interface KernelManagerSustituto {
    public WrapperResultados calculaNumeroPoliza(String pv_cdunieco_i, String pv_cdramo_i, String pv_estado_i) throws ApplicationException;
    public WrapperResultados insertaMaestroPolizas(Map<String, String> parameters) throws ApplicationException;
    public WrapperResultados insertaPolisit(Map<String, String> parameters) throws ApplicationException;
    public WrapperResultados insertaValoresSituaciones(Map<String, String> parameters) throws ApplicationException;
    public List<SituacionVO> clonaSituaciones(Map<String,String> parameters) throws ApplicationException;
    public WrapperResultados coberturas(Map<String,String> parameters) throws ApplicationException;
    public WrapperResultados ejecutaASIGSVALIPOL(Map<String,String> parameters) throws ApplicationException;
    public WrapperResultados clonaPersonas(Map<String,String> parameters) throws ApplicationException;
    public List<ResultadoCotizacionVO> obtenerResultadosCotizacion(Map<String,String> parameters) throws ApplicationException;
    public List<CoberturaCotizacionVO> obtenerCoberturas(Map<String,String> parameters) throws ApplicationException;
}