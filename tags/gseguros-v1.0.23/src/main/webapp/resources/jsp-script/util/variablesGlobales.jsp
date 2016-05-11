<%@ page language="java"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript">

	/* ********** VARIABLES GLOBALES ************** */
	
	var _global_urlConsultaDinamica        = '<s:url namespace="/consultas" action="consultaDinamica" />';
	var _GLOBAL_URL_LOGIN                  = '<s:url namespace="/seguridad" action="login" />';
	var _GLOBAL_URL_LOGOUT                 = '<s:url namespace="/seguridad" action="logout" />';
	var _GLOBAL_URL_MANTENER_SESION_ACTIVA = '<s:url namespace="/seguridad" action="mantenerSesionActiva" />';
	var _GLOBAL_MUESTRA_EXTENSION_TIMEOUT  = '<s:text name="sesion.muestra.extension.timeout"/>';
	var _GLOBAL_OCULTA_EXTENSION_TIMEOUT   = '<s:text name="sesion.oculta.extension.timeout"/>';
	var _GLOBAL_URL_GRABAR_EVENTO          = '<s:url namespace="/servicios" action="grabarEvento" />';
	var _GLOBAL_URL_TEST_SLEEP             = '<s:url namespace="/test" action="sleep" />';
	
	var _GLOBAL_DIRECTORIO_ICONOS         = '${icons}';
	var _GLOBAL_CONTEXTO                  = '${ctx}';
	var _GLOBAL_DIRECTORIO_DEFINES        = '${defines}'
	var _GLOBAL_URL_RECUPERACION          = '<s:url namespace="/recuperacion" action="recuperar"           />';
	var _GLOBAL_URL_IMPRIMIR_LOTE         = '<s:url namespace="/consultas"    action="imprimirLote"        />';
	var _GLOBAL_URL_DESCARGAR_LOTE        = '<s:url namespace="/consultas"    action="descargarLote"       />';
	var _GLOBAL_URL_ESPERAR_DESCARGA_LOTE = '<s:url namespace="/consultas"    action="esperarDescargaLote" />';
	
	var _GLOBAL_URL_CARGAR_ACCIONES_ENTIDAD = '<s:url namespace="/flujomesacontrol" action="cargarAccionesEntidad"                  />';
	var _GLOBAL_URL_PANTALLA_EXTERNA        = '<s:url namespace="/flujomesacontrol" action="pantallaExterna"                        />';
	var _GLOBAL_URL_VALIDACION              = '<s:url namespace="/flujomesacontrol" action="ejecutaValidacion"                      />';
	var _GLOBAL_URL_VALIDACION_MONTAR_DATOS = '<s:url namespace="/flujomesacontrol" action="recuperarDatosTramiteValidacionCliente" />';
    var _GLOBAL_URL_REVISION                = '<s:url namespace="/flujomesacontrol" action="ejecutaRevision"                        />';
    var _GLOBAL_URL_TURNAR                  = '<s:url namespace="/flujomesacontrol" action="turnarTramite"                          />';
    
    var _GLOBAL_COMP_URL_VENTANA_DOCS         = '<s:url namespace="/documentos"       action="ventanaDocumentosPoliza"   />';
    var _GLOBAL_COMP_URL_GET_HISTORIAL        = '<s:url namespace="/mesacontrol"      action="obtenerDetallesTramite"    />';
    var _GLOBAL_COMP_URL_FINAL_HIST           = '<s:url namespace="/mesacontrol"      action="finalizarDetalleTramiteMC" />';
    var _GLOBAL_COMP_URL_TURNAR               = '<s:url namespace="/flujomesacontrol" action="turnarDesdeComp"           />';
    var _GLOBAL_COMP_URL_CONS_CLAU            = '<s:url namespace="/catalogos"        action="consultaClausulas"         />';
    var _GLOBAL_COMP_URL_CONS_CLAU_DET        = '<s:url namespace="/catalogos"        action="consultaClausulaDetalle"   />';
    var _GLOBAL_COMP_URL_GUARDA_CARTA_RECHAZO = '<s:url namespace="/"                 action="guardarCartaRechazo"       />';
    
        <s:url namespace="/flujomesacontrol" action="mesaControl" var="urlMesaFlujo" includeParams="get">
        <s:param name="params.AGRUPAMC" value="%{'PRINCIPAL'}" />
    </s:url>
    
    var _GLOBAL_COMP_URL_MCFLUJO = '<s:property value="urlMesaFlujo" />';
</script>
