package mx.com.gseguros.portal.despachador.service;

import java.util.Date;

import mx.com.gseguros.portal.despachador.model.RespuestaDespachadorVO;
import mx.com.gseguros.portal.despachador.model.RespuestaTurnadoVO;

public interface DespachadorManager {
    
    public RespuestaDespachadorVO despachar (String ntramite, String status) throws Exception;
    
    /**
     * SE TURNA/RECHAZA/REASIGNA UN TRAMITE. SE MODIFICA TMESACONTROL (STATUS, FECSTATU, CDUSUARI, CDUNIDSPCH, CDRAZRECHA),
     * THMESACONTROL (SE CIERRA EL HISTORIAL ANTERIOR, SE ABRE EL HISTORIAL NUEVO),
     * TDMESACONTROL (SE INSERTA DETALLE). SE ENVIAN CORREOS DE AVISOS Y SE RECHAZA EN SIGS 
     * @param sinGrabarDetalle TODO
     * @return String message, boolean encolado
     */
    public RespuestaTurnadoVO turnarTramite (String cdusuariSes, String cdsisrolSes, String ntramite, String status, String comments,
            String cdrazrecha, String cdusuariDes, String cdsisrolDes, boolean permisoAgente, boolean porEscalamiento, Date fechaHoy,
            boolean sinGrabarDetalle) throws Exception;

}