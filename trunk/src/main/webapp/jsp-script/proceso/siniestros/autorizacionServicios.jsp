<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Consulta Clausulas</title>
        
        <script type="text/javascript">
            var _CONTEXT = '${ctx}';
            var _URL_CONSULTA_DATOS_TARIFA_POLIZA = '<s:url namespace="/consultasPoliza" action="consultaDatosTarifaPoliza" />';
            //var _URL_TIPOS_CPT= _CONTEXT + '/js/proceso/siniestros/tiposCPT.json';
            //var _URL_TIPOS_HCPT= _CONTEXT + '/js/proceso/siniestros/tiposHCPT.json';
            //var _URL_TIPOS_ICD= _CONTEXT + '/js/proceso/siniestros/tiposICD.json';
            //var _URL_TIPOS_UB= _CONTEXT + '/js/proceso/siniestros/tiposUB.json';
            
            
            //var _URL_CARGA_CLAVES_CLAU =    '<s:url namespace="/catalogos" action="cargaClausulas" />';
            //var _URL_CONSULTA_CLAUSU =      '<s:url namespace="/catalogos" action="consultaClausulas" />';
            //var _URL_CONSULTA_CLAUSU_DETALLE =      '<s:url namespace="/catalogos" action="consultaClausulaDetalle" />';
            //var _URL_INSERTA_CLAUSU =      '<s:url namespace="/catalogos" action="insertaClausula" />';
            //var _URL_ACTUALIZA_CLAUSU =      '<s:url namespace="/catalogos" action="actualizaClausula" />';
            
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
            <div id="divResultados" style="margin-top:10px;"></div>
        </div> 
   </body>
</html>