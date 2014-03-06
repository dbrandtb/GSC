<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Autorizaci&oacute;n de servicio</title>
        
        <script type="text/javascript">
            var _CONTEXT = '${ctx}';
            var mesConUrlLoadCatalo    				= '<s:url namespace="/catalogos"       action="obtieneCatalogo" />';
            var _CAT_AUTORIZACION				    = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MC_SUCURSALES_ADMIN"/>';
            var _CAT_CAUSASINIESTRO				    = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TCAUSASSV"/>';
            var _CAT_TRATAMIENTO				    = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TTRATAMIENTO"/>';
            var _CAT_TPENALIZACIONES				    = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TPENALIZACIONES"/>';
            
            var panDocUrlViewDoc     = '<s:url namespace ="/documentos" action="descargaDocInline" />';
            var venDocUrlImpConrec   = '<s:url namespace ="/documentos" action="generarContrarecibo" />';
            
            var _URL_TIPO_AUTORIZACION				= _CONTEXT + '/js/proceso/siniestros/tiposAutorizacion.json';
            var _HISTORIAL_RECLAMACIONES    		= '<s:url namespace="/siniestros"      	action="historialReclamaciones" />';
            var _VER_COBERTURAS		    			= '<s:url namespace="/siniestros"      	action="verCoberturas" />';
            var _URL_LISTADO_ASEGURADO          	= '<s:url namespace="/siniestros"       action="consultaListaAsegurado" />';
            var _URL_CONSULTA_LISTADO_AUTORIZACION 	= '<s:url namespace="/siniestros" 		action="consultaListaAutorizacion" />';
            var _URL_CONSULTA_AUTORIZACION_ESP 		= '<s:url namespace="/siniestros" 		action="consultaAutorizacionServicio" />';
            var _URL_CONSULTA_PROVEEDOR_MEDICO		= '<s:url namespace="/siniestros" 		action="consultaListaProvMedico" />';
            var _URL_LISTA_COBERTURA 				= '<s:url namespace="/siniestros" 		action="consultaListaCoberturaPoliza" />';
            var _URL_LISTA_SUBCOBERTURA				= '<s:url namespace="/siniestros" action="consultaListaSubcobertura" />';
            var _URL_LISTA_CPTICD					= '<s:url namespace="/siniestros" action="consultaListaCPTICD" />';
            
            var _URL_CONSULTA_DEDUCIBLE_COPAGO		= '<s:url namespace="/siniestros" 		action="consultaListaDatSubGeneral" />';
            var _URL_CONSULTA_LISTADO_POLIZA		= '<s:url namespace="/siniestros" 		action="consultaListaPoliza" />';
            
            var _URL_LISTA_TABULADOR				= '<s:url namespace="/siniestros" action="consultaListaPorcentaje" />';
            var _URL_LISTA_TMANTENI					= '<s:url namespace="/siniestros" action="consultaListaManteni" />';
            var _URL_LISTADO_CONCEP_EQUIP    		= '<s:url namespace="/siniestros" action="consultaListaTDeTauts" />';
            
            var _URL_GUARDA_AUTORIZACION			= '<s:url namespace="/siniestros" action="guardaAutorizacionServicio" />';
            var _VER_AUTORIZACION_SERVICIO 			= '<s:url namespace="/siniestros"      	action="verAutorizacionServicio" />';
            var _UR_LISTA_PLAZAS 					= '<s:url namespace="/siniestros"      	action="consultaListaPlazas"/>';
            
        </script>
        <script type="text/javascript" src="${ctx}/js/proceso/siniestros/autorizacionServicios.js"></script>
        <!-- <script type="text/javascript" src="${ctx}/js/proceso/siniestros/verAutorizacionServicios.js"></script> -->
        
    </head>
    <body>
    <!-- <div style="height:500px;">
            <div id="div_clau"></div>
   </div>-->
    	<div style="height:1500px;">
            <div id="div_clau"></div>
            <!-- <div id="divResultados" style="margin-top:10px;"></div>-->
        </div> 
   </body>
</html>