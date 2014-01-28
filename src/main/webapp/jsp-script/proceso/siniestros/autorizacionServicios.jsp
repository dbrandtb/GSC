<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Consulta Clausulas</title>
        
        <script type="text/javascript">
            var _CONTEXT = '${ctx}';
            var mesConUrlLoadCatalo    				= '<s:url namespace="/catalogos"       action="obtieneCatalogo" />';
            var _URL_TIPOS_PENALIZACION				= _CONTEXT + '/js/proceso/siniestros/tiposPenalizacion.json';
            var _CAT_AUTORIZACION				    = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MC_SUCURSALES_ADMIN"/>';
            var _URL_TIPO_AUTORIZACION				= _CONTEXT + '/js/proceso/siniestros/tiposAutorizacion.json';
            var _HISTORIAL_RECLAMACIONES    		= '<s:url namespace="/siniestros"      	action="historialReclamaciones" />';
            var _VER_COBERTURAS		    			= '<s:url namespace="/siniestros"      	action="verCoberturas" />';
            var _URL_LISTADO_ASEGURADO          	= '<s:url namespace="/siniestros"       action="consultaListaAsegurado" />';
            var _URL_CONSULTA_LISTADO_AUTORIZACION 	= '<s:url namespace="/siniestros" 		action="consultaListaAutorizacion" />';
            var _URL_CONSULTA_AUTORIZACION_ESP 		= '<s:url namespace="/siniestros" 		action="consultaAutorizacionServicio" />';
            var _URL_CONSULTA_PROVEEDOR_MEDICO		= '<s:url namespace="/siniestros" 		action="consultaListaProvMedico" />';
            var _URL_CAUSA_SINIESTRO 				= '<s:url namespace="/siniestros" 		action="consultaListaCausuaSiniestro" />';
            var _URL_LISTA_COBERTURA 				= '<s:url namespace="/siniestros" 		action="consultaListaCoberturaPoliza" />';
            var _URL_LISTA_SUBCOBERTURA				= '<s:url namespace="/siniestros" action="consultaListaSubcobertura" />';
            var _URL_LISTA_CPTICD					= '<s:url namespace="/siniestros" action="consultaListaCPTICD" />';
            
            
        </script>
        <!-- <script type="text/javascript" src="${ctx}/resources/scripts/util/extjs4_utils.js"></script>-->
        <script type="text/javascript" src="${ctx}/js/proceso/siniestros/autorizacionServicios.js"></script>
        
    </head>
    <body>
    <!-- <div style="height:500px;">
            <div id="div_clau"></div>
   </div>-->
    	<div style="height:10px;">
            <div id="div_clau"></div>
            <!-- <div id="divResultados" style="margin-top:10px;"></div>-->
        </div> 
   </body>
</html>