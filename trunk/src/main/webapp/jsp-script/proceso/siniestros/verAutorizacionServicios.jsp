<%--<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
--%>
<%@ include file="/taglibs.jsp"%>
<%-- 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Historial de Reclamaciones</title>
        --%>
        <script type="text/javascript">
            var _CONTEXT = '${ctx}';
            var _URL_CONSULTA_AUTORIZACION_ESP1 		= '<s:url namespace="/siniestros" 		action="consultaAutorizacionServicio" />';
            var _URL_LISTADO_CONCEP_EQUIP1    		= '<s:url namespace="/siniestros" action="consultaListaTDeTauts" />';
            var _URL_LISTA_SUBCOBERTURA1				= '<s:url namespace="/siniestros" action="consultaListaSubcobertura" />';
            var _URL_LISTA_TMANTENI1					= '<s:url namespace="/siniestros" action="consultaListaManteni" />';
            var _URL_CONSULTA_PROVEEDOR_MEDICO1		= '<s:url namespace="/siniestros" 		action="consultaListaProvMedico" />';
            var _URL_CONSULTA_DEDUCIBLE_COPAGO1		= '<s:url namespace="/siniestros" 		action="consultaListaDatSubGeneral" />';
            var mesConUrlLoadCatalo1    				= '<s:url namespace="/catalogos"       action="obtieneCatalogo" />';
            var _CAT_AUTORIZACION1				    = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MC_SUCURSALES_ADMIN"/>';
            
            
            var valorAction = <s:property value='%{getParams().toString().replace("=",":\'").replace(",","\',").replace("}","\'}")}' />;
            
            
        </script>
        
        <script type="text/javascript" src="${ctx}/js/proceso/siniestros/verAutorizacionServicios.js"></script>
    <%--    
    </head>
    <body>--%>
    <div style="height:100px;">
            <div id="div_clau21"></div>
   </div>
   <%-- </body>
</html>--%>