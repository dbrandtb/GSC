<%--
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
--%>
<%@ include file="/taglibs.jsp"%>
<%--
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Agentes</title>
        --%>
        
        <script type="text/javascript">
            var _CONTEXT = '${ctx}';
            var _URL_VALIDA_DATOS_SITUACION =    '<s:url namespace="/flujocotizacion" 	action="consultaDatosPolizaAgente" />'; 
            var _URL_GENERAL_AGENTES        =    '<s:url namespace="/flujocotizacion"   action="consultaGeneralAgente" />';
            var _URL_CATALOGOS          	=	 '<s:url namespace="/catalogos"         action="obtieneCatalogo" />';
            var _URL_GUARDA_PORCENTAJE      =    '<s:url namespace="/flujocotizacion" 	action="guardaPorcentajeAgentes" />';
            
            <%--
            Estas variables se toman del padre, complementarios.jsp
            var inputCdunieco='<s:property value="cdunieco" />';
            var inputCdramo='<s:property value="cdramo" />';
            var inputEstado='<s:property value="estado" />';
            var inputNmpoliza='<s:property value="nmpoliza" />';
            --%>
        </script>
        <script type="text/javascript" src="${ctx}/js/consultas/agentes/consultaAgentes.js"></script>
        <%--
    </head>
    <body>
    --%>
    <div style="height:700px;">
            <div id="div_clau"></div>
   </div>
   <%--
    </body>
</html>
--%>