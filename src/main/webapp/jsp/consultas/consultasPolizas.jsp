<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Ext JS 4 Dashboard</title>
        
        <script type="text/javascript">
            var _CONTEXT = '${ctx}';
            
            var _URL_TIPOS_CONSULTA         =       _CONTEXT + '/resources/scripts/consultaPolizas/tiposConsulta.json';
            var _URL_CONSULTA_DATOS_POLIZA =        '<s:url namespace="/consultasPoliza" action="consultaDatosPoliza" />';
            var _URL_CONSULTA_DATOS_SUPLEMENTO =    '<s:url namespace="/consultasPoliza" action="consultaDatosSuplemento" />';
            var _URL_CONSULTA_DATOS_TARIFA_POLIZA = '<s:url namespace="/consultasPoliza" action="consultaDatosTarifaPoliza" />';
            var _URL_CONSULTA_DATOS_ASEGURADO =     '<s:url namespace="/consultasPoliza" action="consultaDatosAsegurado" />';
            var _URL_CONSULTA_POLIZAS_ASEGURADO =   '<s:url namespace="/consultasPoliza" action="consultaPolizasAsegurado" />';
            var _URL_CONSULTA_DATOS_AGENTE =        '<s:url namespace="/consultasPoliza" action="consultaDatosAgente" />';
            var _URL_CONSULTA_RECIBOS_AGENTE =      '<s:url namespace="/consultasPoliza" action="consultaRecibosAgente" />';
            var _URL_CONSULTA_COPAGOS_POLIZA =      '<s:url namespace="/consultasPoliza" action="consultaCopagosPoliza" />';
            var _URL_CONSULTA_DOCUMENTOS =          '<s:url namespace="/documentos" action="ventanaDocumentosPoliza" />';
            var _URL_LOADER_RECIBOS      =          '<s:url namespace="/general" action="loadRecibos" />';
            var _URL_LOADER_VER_EXCLUSIONES =       '<s:url namespace="/consultasPoliza" action="includes/verExclusiones" />';
            var _URL_LOADER_VER_TATRISIT    =       '<s:url namespace="/consultasPoliza" action="includes/verDatosTatrisit" />';
            
            var _MSG_ERROR =                           'Error';
            var _MSG_INFO  =                           'Info';
            var _MSG_ERROR_HISTORICO_MOVIMIENTOS =     'Ocurri\u00F3 un error al obtener el hist\u00F3rico de movimientos';
            var _MSG_SIN_DATOS =                       'No hay datos';
            var _MSG_BUSQUEDA_SIN_DATOS = 'No se encontraron datos. Intente cambiar los filtros de b\u00FAsqueda.';
            var _MSG_RFC_INVALIDO = 'Ingrese un RFC v\u00E1lido';
            var _MSG_NMPOLIEX_INVALIDO = 'Ingrese un numero de poliza';
            var _MSG_CDPERSON_INVALIDO = 'Ingrese un c\u00F3digo de persona';
            var _MSG_NOMBRE_INVALIDO   = 'Ingrese el nombre';
        </script>
        <script type="text/javascript" src="${ctx}/resources/scripts/consultaPolizas/consultasPolizas.js"></script>
        
    </head>
    <body>
        <div id="dvConsultasPolizas" style="height:710px"></div>
    </body>
</html>