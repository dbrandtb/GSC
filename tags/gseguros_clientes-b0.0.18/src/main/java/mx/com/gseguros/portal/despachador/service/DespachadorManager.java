package mx.com.gseguros.portal.despachador.service;

import java.util.Date;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.despachador.model.RespuestaDespachadorVO;
import mx.com.gseguros.portal.despachador.model.RespuestaTurnadoVO;

public interface DespachadorManager {
    
    public RespuestaDespachadorVO despachar (String ntramite, String status) throws Exception;
    
    public RespuestaDespachadorVO despachar (String ntramite, String status, boolean sinBuscarRegreso) throws Exception;
    
    /**
     * SOBRECARGADO
     */
    public RespuestaTurnadoVO turnarTramite (String cdusuariSes, String cdsisrolSes, String ntramite, String status, String comments,
            String cdrazrecha, String cdusuariDes, String cdsisrolDes, boolean permisoAgente, boolean porEscalamiento, Date fechaHoy,
            boolean sinGrabarDetalle) throws Exception;
    
    /**
     * SE TURNA/RECHAZA/REASIGNA UN TRAMITE. SE MODIFICA TMESACONTROL (STATUS, FECSTATU, CDUSUARI, CDUNIDSPCH, CDRAZRECHA),
     * THMESACONTROL (SE CIERRA EL HISTORIAL ANTERIOR, SE ABRE EL HISTORIAL NUEVO),
     * TDMESACONTROL (SE INSERTA DETALLE). SE ENVIAN CORREOS DE AVISOS Y SE RECHAZA EN SIGS 
     * @param sinGrabarDetalle TODO
     * @return String message, boolean encolado
     */
    public RespuestaTurnadoVO turnarTramite (String cdusuariSes, String cdsisrolSes, String ntramite, String status, String comments,
            String cdrazrecha, String cdusuariDes, String cdsisrolDes, boolean permisoAgente, boolean porEscalamiento, Date fechaHoy,
            boolean sinGrabarDetalle, boolean sinBuscarRegreso) throws Exception;
    
    @Deprecated
    public String recuperarRolTrabajoEstatus (String cdtipflu, String cdflujomc, String estatus) throws Exception;

    /**
     * Metodo para despachar un tramite por zona.
     * @param ntramite
     * @param zona
     * @return String
     * @throws Exception
     */
    public String despacharPorZona (String ntramite, String zonaDespacho, String cdusuariSes,
            String cdsisrolSes) throws Exception;
    
    /**
     * Recupera el nombre del usuario desde la clave de usuario
     * @param cdusuari
     * @return
     * @throws Exception
     */
    public String recuperarNombreUsuario (String cdusuari) throws Exception;
    
    /**
     * Recupera la descipcion del estatus desde la clave de estatus de un tramite
     * @param status
     * @return
     * @throws Exception
     */
    public String recuperarDescripcionEstatus (String status) throws Exception;
    
    /**
     * Recupera la descripcion de un rol desde la clave de rol
     * @param cdsisrol
     * @return
     * @throws Exception
     */
    public String recuperarDescripcionRol (String cdsisrol) throws Exception;
 
    public Map<String, Item> pantallaDatos() throws Exception;
    
}