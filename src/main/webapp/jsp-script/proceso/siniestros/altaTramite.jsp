<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Consulta Clausulas</title>
        
        <script type="text/javascript">
            var _CONTEXT = '${ctx}';
            
            var _CATALOGO_OFICINA_RECEP= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MC_SUCURSALES_ADMIN"/>';
            var _CAT_RAMOS		       = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@RAMOS"/>';
            var _CATALOGO_TipoAtencion = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_ATENCION_SINIESTROS"/>';
            var _CATALOGO_TipoPago     = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_PAGO_SINIESTROS"/>';
            var _CATALOGO_PROVEEDORES  = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@PROVEEDORES"/>';
            var _CATALOGO_TipoMoneda   = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_MONEDA"/>';
            
            var _URL_CATALOGOS = '<s:url namespace="/catalogos" action="obtieneCatalogo" />';
            
            var _URL_CONSULTA_LISTADO_POLIZA		= '<s:url namespace="/siniestros" 		action="consultaListaPoliza" />';
            
            var _URL_LISTADO_ASEGURADO          	= '<s:url namespace="/siniestros"       action="consultaListaAsegurado" />';
            var _URL_LISTADO_ALTATRAMITE          	= '<s:url namespace="/siniestros"       action="consultaListadoAltaTramite" />';
            
            var _URL_GUARDA_ALTA_TRAMITE     		= '<s:url namespace="/siniestros"       action="guardaAltaTramite" />';
            
            var _URL_CONSULTA_ALTA_TRAMITE     		= '<s:url namespace="/siniestros"       action="consultaListadoMesaControl" />';
            var _URL_CONSULTA_GRID_ALTA_TRAMITE     = '<s:url namespace="/siniestros"       action="consultaListadoAltaTramite" />';
            
            var _p12_urlMesaControl              = '<s:url namespace="/mesacontrol" action="mcdinamica"               />';
            var _UR_LISTA_RAMO_SALUD				= '<s:url namespace="/siniestros"  action="consultaRamosSalud"/>';
            
            var valorAction = <s:property value='paramsJson' escapeHtml='false'/>;
            
        </script>
        <!-- <script type="text/javascript" src="${ctx}/resources/scripts/util/extjs4_utils.js"></script>-->
        <script type="text/javascript" src="${ctx}/js/proceso/siniestros/altaTramite.js?${now}"></script>
        
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