<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Detalle de la Reclamaci&oacute;n</title>
        
        <script type="text/javascript">
            var _CONTEXT = '${ctx}';
            
            var _URL_LOADER_INFO_GRAL_RECLAMACION = '<s:url namespace="/siniestros" action="includes/loadInfoGeneralReclamacion" />';
            var _URL_LOADER_REV_ADMIN             = '<s:url namespace="/siniestros" action="includes/revAdmin" />';
            var _URL_LOADER_CALCULOS              = '<s:url namespace="/siniestros" action="includes/calculosReclamaciones" />';
            
            var _CDUNIECO = '<s:property value="params.cdunieco" />';
            var _CDRAMO   = '<s:property value="params.cdramo" />';
            var _ESTADO   = '<s:property value="params.estado" />';
            var _NMPOLIZA = '<s:property value="params.nmpoliza" />';
            var _NMSITUAC = '<s:property value="params.nmsituac" />';
            var _NMSUPLEM = '<s:property value="params.nmsuplem" />';
            var _STATUS   = '<s:property value="params.status" />';// status del siniestro
            var _AAAPERTU = '<s:property value="params.aaapertu" />';
            var _NMSINIES = '<s:property value="params.nmsinies" />';
            var _NTRAMITE= '<s:property value="params.ntramite" />';
        </script>
        <!-- <script type="text/javascript" src="${ctx}/resources/scripts/util/extjs4_utils.js"></script>-->
        <script type="text/javascript" src="${ctx}/js/proceso/siniestros/detalleReclamacion/detalleReclamacion.js"></script>
    </head>
    <body>
    </body>
</html>