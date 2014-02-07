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
            var _URL_LISTA_COBERTURAPOL 				= '<s:url namespace="/siniestros" 		action="consultaListaCoberturaPoliza" />';
            var _7_smap1 = <s:property value='%{getParams().toString().replace("=",":\'").replace(",","\',").replace("}","\'}")}' />;
            //var _CATALOGO_AGENTES			= 	 '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@AGENTES"/>';
           //debug('<s:property value="%{getParams().toString()}" />');
        </script>
        
        <script type="text/javascript" src="${ctx}/js/proceso/siniestros/verCoberturas.js"></script>
    <%--    
    </head>
    <body>--%>
    <div style="height:100px;">
            <div id="div_clau2"></div>
   </div>
   <%-- </body>
</html>--%>