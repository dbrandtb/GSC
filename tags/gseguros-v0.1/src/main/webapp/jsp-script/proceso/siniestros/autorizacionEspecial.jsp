<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Autorizaci&oacute;n Especial</title>
			<script type="text/javascript">
				var	_CONTEXT = '${ctx}';
				var _CAT_RAMO_SALUD					= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@RAMOSALUD"/>';
				var _URL_CATALOGOS					= '<s:url namespace="/catalogos" 		action="obtieneCatalogo" />';
				var _URL_CONSULTA_LISTADO_POLIZA	= '<s:url namespace="/siniestros" 		action="consultaListaPoliza" />';
				var _URL_LISTADO_ASEGURADO_ESP		= '<s:url namespace="/siniestros"       action="consultaListaAsegAutEspecial" />';
				var _URL_LISTA_COBERTURA 			= '<s:url namespace="/siniestros"  		action="consultaListaCoberturaProducto" />';
				var _URL_GUARDA_AUT_ESPECIAL     	= '<s:url namespace="/siniestros"       action="guardaAutorizacionEspecial" />';
				var _URL_CONF_AUTESPECIAL    		= '<s:url namespace="/siniestros"       action="obtenerConfiguracionAutEspecial" />';
				var _URL_LISTADO_CONTRARECIBO    	= '<s:url namespace="/siniestros"       action="consultaListaContrareciboAutEsp" />';
				var _URL_LISTADO_FACTNTRAMITE    	= '<s:url namespace="/siniestros"       action="consultaListaFacturaTramite" />';
			</script>
			<script type="text/javascript" src="${ctx}/js/proceso/siniestros/autorizacionEspecial.js"></script>
	</head>
	<body>
		<div style="height:500px;">
			<div id="div_clau"></div>
		</div>
 	</body>
</html>