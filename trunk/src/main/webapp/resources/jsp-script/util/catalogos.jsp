<%@ page language="java"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<script type="text/javascript">
/* ********** VARIABLES GLOBALES ************** */
var _global_urlConsultaDinamica = '<s:url namespace="/consultas" action="consultaDinamica" />';

/* ********** CATALOGOS JS ******************** */

// Catalogo de ACCIONES PERSISTENTES:
var Accion = {
    ObtieneDatosFacturas      : '<s:property value="@mx.com.gseguros.portal.general.util.ObjetoBD@OBTIENE_DATOS_FACTURAS" />',
    ObtieneDatosProveedores   : '<s:property value="@mx.com.gseguros.portal.general.util.ObjetoBD@OBTIENE_DATOS_PROVEEDORES" />',
    ObtieneDatosReexpDoc      : '<s:property value="@mx.com.gseguros.portal.general.util.ObjetoBD@OBTIENE_DATOS_REEXPED_DOC" />',
    ObtieneEmail              : '<s:property value="@mx.com.gseguros.portal.general.util.ObjetoBD@OBTIENE_EMAIL" />',
    ValidaCancelacionProrrata : '<s:property value="@mx.com.gseguros.portal.general.util.ObjetoBD@VALIDA_CANC_A_PRORRATA" />',
    ValidaEdadAsegurados      : '<s:property value="@mx.com.gseguros.portal.general.util.ObjetoBD@VALIDA_EDAD_ASEGURADOS" />'
};

// Nombre de catálogos:
var Cat = {
    Agentes                   : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@AGENTES" />',
    CausaSiniestro            : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CAUSA_SINIESTRO" />',
    Coberturas                : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@COBERTURAS" />',
    CoberturasxTramite        : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@COBERTURASXTRAMITE" />',
    CodigosMedicos            : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CODIGOS_MEDICOS" />',
    Colonias                  : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@COLONIAS" />',
    Endosos                   : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@ENDOSOS" />',
    ICD                       : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@ICD" />',
    EstatusTramiteMC          : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MC_ESTATUS_TRAMITE" />',
    SucursalesAdminMC         : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MC_SUCURSALES_ADMIN" />',
    SucursalesDocumentoMC     : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MC_SUCURSALES_DOCUMENTO" />',
    TiposTramiteMC            : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MC_TIPOS_TRAMITE" />',
    Medicos                   : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MEDICOS" />',
    MotivosCancelacion        : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MOTIVOS_CANCELACION" />',
    MotivosRechazoSiniestro   : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MOTIVOS_RECHAZO_SINIESTRO" />',
    MotivosReexpedicion       : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MOTIVOS_REEXPEDICION" />',
    Nacionalidad              : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@NACIONALIDAD" />',
    Penalizaciones            : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@PENALIZACIONES" />',
    Planes                    : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@PLANES" />',
    Proveedores               : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@PROVEEDORES" />',
    Ramos                     : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@RAMOS" />',
    RolesPoliza               : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@ROLES_POLIZA" />',
    RolesSistema              : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@ROLES_SISTEMA" />',
    Sexo                      : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SEXO" />',
    Sino                      : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SINO" />',
    StatusPoliza              : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@STATUS_POLIZA" />',
    Subcoberturas             : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SUBCOBERTURAS" />',
    SubmotivosRechazoSiniestro: '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SUBMOTIVOS_RECHAZO_SINIESTRO" />',
    Tatrigar                  : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TATRIGAR" />',
    Tatriper                  : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TATRIPER" />',
    Tatripol                  : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TATRIPOL" />',
    Tatrisit                  : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TATRISIT" />',
    TipoAtencionSiniestros    : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_ATENCION_SINIESTROS" />',
    TipoConceptoSiniestros    : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_CONCEPTO_SINIESTROS" />',
    TipoPagoSiniestros        : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_PAGO_SINIESTROS" />',
    TiposPagoPoliza           : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPOS_PAGO_POLIZA" />',
    TiposPagoPolizaSinDxN     : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPOS_PAGO_POLIZA_SIN_DXN" />',
    TiposPersona              : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPOS_PERSONA" />',
    TiposPoliza               : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPOS_POLIZA" />',
    TipSit                    : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPSIT" />',
    Tratamientos              : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TRATAMIENTOS" />',
    TipoMoneda                : '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_MONEDA" />'
};


// Estatus de tramite:
var EstatusTramite = {
        
    Confirmado             : '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@CONFIRMADO.codigo" />',
    EnCaptura              : '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@EN_CAPTURA.codigo" />',
    EnCapturaCMM           : '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@EN_CAPTURA_CMM.codigo" />',
    EnEsperaDeAsignacion   : '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@EN_ESPERA_DE_ASIGNACION.codigo" />',
    EnEsperaDeAutorizacion : '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@EN_ESPERA_DE_AUTORIZACION.codigo" />',
    EnRevisionMedica       : '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@EN_REVISION_MEDICA.codigo" />',
    EndosoConfirmado       : '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@ENDOSO_CONFIRMADO.codigo" />',
    EndosoEnEspera         : '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@ENDOSO_EN_ESPERA.codigo" />',
    Pendiente              : '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@PENDIENTE.codigo" />',
    Rechazado              : '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@RECHAZADO.codigo" />',
    SolicitudMedica        : '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@SOLICITUD_MEDICA.codigo" />',
    VoBoMedico             : '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@VO_BO_MEDICO.codigo" />'
};


// Catalogo de cdramo:
var Ramo = {
    SaludVital : '<s:property value="@mx.com.gseguros.portal.general.util.Ramo@SALUD_VITAL.cdramo"/>'   
};


//Catalogo de Tipo de pago:
var TipoPago = {
    Directo   : '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@DIRECTO.codigo" />',
    Reembolso : '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@REEMBOLSO.codigo" />'
};


// Catalogo de cdtipsit:
var TipoSituacion = {
    SaludVital  : '<s:property value="@mx.com.gseguros.portal.general.util.TipoSituacion@SALUD_VITAL.cdtipsit" />',
    SaludNomina : '<s:property value="@mx.com.gseguros.portal.general.util.TipoSituacion@SALUD_NOMINA.cdtipsit" />'
};


// Catalogo de Tipos de tramite (cdtiptra):
var TipoTramite = {
    AltaAfiliado                   : '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@ALTA_AFILIADO.cdtiptra" />',
    AutorizacionServicios          : '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@AUTORIZACION_SERVICIOS.cdtiptra" />',
    BajaAfiliado                   : '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@BAJA_AFILIADO.cdtiptra" />',
    CambioAgente                   : '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@CAMBIO_AGENTE.cdtiptra" />',
    CambioContratante              : '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@CAMBIO_CONTRATANTE.cdtiptra" />',
    CambioFormaPago                : '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@CAMBIO_FORMA_PAGO.cdtiptra" />',
    CambioVigencia                 : '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@CAMBIO_VIGENCIA.cdtiptra" />',
    Cancelacion                    : '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@CANCELACION.cdtiptra" />',
    CancelacionPorPagoImprocedente : '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@CANCELACION_POR_PAGO_IMPROCEDENTE.cdtiptra" />',
    CancelacionProrrata            : '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@CANCELACION_PRORRATA.cdtiptra" />',
    EndosoParadoPorAutorizacion    : '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@ENDOSO_PARADO_POR_AUTORIZACION.cdtiptra" />',
    ModificatorioAlta              : '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@MODIFICATORIO_ALTA.cdtiptra" />',
    ModificatorioBaja              : '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@MODIFICATORIO_BAJA.cdtiptra" />',
    PolizaNueva                    : '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@POLIZA_NUEVA.cdtiptra" />',
    Rehabilitacion                 : '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@REHABILITACION.cdtiptra" />',
    Siniestro                      : '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@SINIESTRO.cdtiptra" />'
};

</script>