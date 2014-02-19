<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Documento</title>
        <script type="text/javascript">
            var _CONTEXT = '${ctx}';
            
            var _URL_ListaRechazos =      '<s:url namespace="/siniestros" action="loadListaRechazos" />';
            var _URL_ListaIncisosRechazos =      '<s:url namespace="/siniestros" action="loadListaIncisosRechazos" />';
            var _URL_ActualizaStatusTramite =      '<s:url namespace="/mesacontrol" action="actualizarStatusTramite" />';
            
        </script>
        <!-- <script type="text/javascript" src="${ctx}/resources/scripts/util/extjs4_utils.js"></script>-->
        <script type="text/javascript" src="${ctx}/js/proceso/siniestros/rechazoReclamaciones.js"></script>
   </head>
    <body>
            <div id="maindiv"></div>
    </body>
</html>