<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Ext JS 4 Dashboard</title>
        
        <script type="text/javascript">
            var _CONTEXT = '${ctx}';
            
            var _URL_CONSULTA_DATOS_SUPLEMENTO =    '<s:url namespace="/consultasPoliza" action="consultaDatosSuplemento" />';
            
            var _MSG_ERROR =                           'Error';
            var _MSG_INFO  =                           'Info';
            var _MSG_ERROR_HISTORICO_MOVIMIENTOS =     'Ocurri\u00F3 un error al obtener el hist\u00F3rico de movimientos';
            var _MSG_SIN_DATOS =                       'No hay datos';
            var _MSG_BUSQUEDA_SIN_DATOS = 'No se encontraron datos. Intente cambiar los filtros de b\u00FAsqueda.';
            var _MSG_RFC_INVALIDO = 'Ingrese un RFC v\u00E1lido';
            var _MSG_NMPOLIEX_INVALIDO = 'Ingrese un numero de poliza';
            var _MSG_CDPERSON_INVALIDO = 'Ingrese un c\u00F3digo de persona';
            var _MSG_NOMBRE_INVALIDO   = 'Ingrese el nombre';
            
            
            
            var _TIPOPAGO;
            var _CDUNIECO;
            var _CDRAMO;
            var _ESTADO;
            var _NMPOLIZA;
            var _NMSITUAC;
            var _NMSUPLEM;
            var _STATUS;
            var _AAAPERTU;
            var _NMSINIES;
            var _NTRAMITE;
            
            var _URL_LISTADO_ASEGURADO          	= '<s:url namespace="/siniestros"  action="consultaListaAsegurado" />';
            var _URL_CONSULTA_LIST_ASEG_REEMBOLSO   = '<s:url namespace="/consultasSiniestro" action="consultaAseguradosPagoReembolso" />';
            var _URL_BUSQUEDA_DIRECTO				= _CONTEXT + '/resources/scripts/consultaSiniestros/busquedaPagoDirecto.json';
            var _URL_BUSQUEDA_REEMBOLSO				= _CONTEXT + '/resources/scripts/consultaSiniestros/busquedaPagoReembolso.json';
            
            var _URL_CARGA_INFORMACION			  = 		'<s:url namespace="/consultasSiniestro" action="detalleSiniestrosInicial" />';
            var _URL_INFO_GRAL_SINIESTRO    = 				'<s:url namespace="/siniestros"      action="obtieneDatosGeneralesSiniestro" />';
            
            var _URL_LOADER_INFO_GRAL_RECLAMACION = '<s:url namespace="/consultasSiniestro" action="includes/loadInfoGeneralReclamacion" />';
            var _URL_LOADER_REV_ADMIN             = '<s:url namespace="/consultasSiniestro" action="includes/revAdmin" />';
            var _URL_LOADER_CALCULOS              = '<s:url namespace="/siniestros" action="includes/calculoSiniestros" />';
            
            
            
        </script>
        <script type="text/javascript" src="${ctx}/resources/scripts/consultaSiniestros/consultasSiniestros.js"></script>
        
    </head>
    <body>
        <div id="dvConsultasPolizas" style="height:710px"></div>
    </body>
</html>