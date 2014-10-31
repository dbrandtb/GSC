<%@ page language="java" %>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Consulta de p&oacute;lizas</title>
        
        <script type="text/javascript">
            var _CONTEXT = '${ctx}';
            
            var _URL_TIPOS_CONSULTA               = _CONTEXT + '/resources/scripts/consultaPolizas/tiposConsulta.json';
            var _URL_CONSULTA_DATOS_POLIZA        = '<s:url namespace="/consultasPoliza" action="consultaDatosPoliza" />';
            var _URL_CONSULTA_DATOS_COMPLEMENTARIOS = '<s:url namespace="/consultasPoliza" action="consultaDatosComplementarios" />';
            var _URL_CONSULTA_DATOS_SUPLEMENTO    = '<s:url namespace="/consultasPoliza" action="consultaDatosSuplemento" />';
            var _URL_CONSULTA_DATOS_TARIFA_POLIZA = '<s:url namespace="/consultasPoliza" action="consultaDatosTarifaPoliza" />';
            var _URL_CONSULTA_DATOS_ASEGURADO     = '<s:url namespace="/consultasPoliza" action="consultaDatosAsegurado" />';
            var _URL_CONSULTA_DATOS_CONTRATANTE   = '<s:url namespace="/consultasPoliza" action="consultaDatosContratante" />';  
            var _URL_CONSULTA_POLIZAS_ASEGURADO   = '<s:url namespace="/consultasPoliza" action="consultaPolizasAsegurado" />';
            var _URL_CONSULTA_AGENTES_POLIZA      = '<s:url namespace="/consultasPoliza" action="consultaAgentesPoliza" />';
            var _URL_CONSULTA_RECIBOS_AGENTE      = '<s:url namespace="/consultasPoliza" action="consultaRecibosAgente" />';
            var _URL_CONSULTA_COPAGOS_POLIZA      = '<s:url namespace="/consultasPoliza" action="consultaCopagosPoliza" />';
            var _URL_CONSULTA_COBERTURAS_POLIZA   = '<s:url namespace="/consultasPoliza" action="consultaCoberturasPoliza" />';
            var _URL_CONSULTA_COBERTURAS_BASICAS  = '<s:url namespace="/consultasPoliza" action="consultaCoberturasBasicas" />';
            var _URL_CONSULTA_DATOS_PLAN         = '<s:url namespace="/consultasPoliza" action="consultaDatosPlan" />';
            var _URL_CONSULTA_DOCUMENTOS          = '<s:url namespace="/documentos"      action="ventanaDocumentosPoliza" />';
            var _URL_LOADER_RECIBOS               = '<s:url namespace="/general"         action="includes/loadRecibos" />';
            var _URL_LOADER_VER_EXCLUSIONES       = '<s:url namespace="/consultasPoliza" action="includes/verClausulas" />';
            var _URL_LOADER_VER_FARMACIA          = '<s:url namespace="/consultasPoliza" action="includes/verFarmacia" />';
            var _URL_LOADER_VER_VIGENCIA          = '<s:url namespace="/consultasPoliza" action="includes/verVigencia" />';
            var _URL_LOADER_VER_TATRISIT          = '<s:url namespace="/consultasPoliza" action="includes/verDatosTatrisit" />';
            var _URL_LOADER_VER_TATRISIT2          = '<s:url namespace="/consultasPoliza" action="includes/verDatosTatrisit2" />';
            var _URL_LOADER_HISTORICO             = '<s:url namespace="/consultasPoliza" action="consultaDatosHistorico" />';
            var _URL_LOADER_AVISO_HOSPITALIZACION = '<s:url namespace="/consultasPoliza" action="includes/avisoHospitalizacion" />';
            
            var _MSG_ERROR                       = 'Error';
            var _MSG_INFO                        = 'Info';
            var _MSG_ERROR_HISTORICO_MOVIMIENTOS = 'Ocurri\u00F3 un error al obtener el hist\u00F3rico de movimientos';
            var _MSG_SIN_DATOS                   = 'No hay datos';
            var _MSG_BUSQUEDA_SIN_DATOS          = 'No se encontraron datos. Intente cambiar los filtros de b\u00FAsqueda.';
            var _MSG_RFC_INVALIDO                = 'Ingrese un RFC v\u00E1lido';
            var _MSG_NMPOLIEX_INVALIDO           = 'Ingrese un numero de poliza';
            var _MSG_CDPERSON_INVALIDO           = 'Ingrese un c\u00F3digo de persona';
            var _MSG_NOMBRE_INVALIDO             = 'Ingrese el nombre';
            
            var _IS_USUARIO_CALL_CENTER          = <s:property value="usuarioCallCenter"/>;
        </script>
        <script type="text/javascript" src="${ctx}/resources/scripts/consultaPolizas/consultasPolizas.js?${now}"></script>
        
    </head>
    <body>
        <div id="dvConsultasPolizas" style="height:710px"></div>
    </body>
</html>