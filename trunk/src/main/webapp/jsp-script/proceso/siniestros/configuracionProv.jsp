<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Consulta Clausulas</title>
			<script type="text/javascript">
				var _CONTEXT = '${ctx}';
				var _URL_CATALOGOS 				=	'<s:url namespace="/catalogos" 		action="obtieneCatalogo" />';
				var _CATALOGO_PROVEEDORES		=	'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@PROVEEDORES"/>';
				var _SINO						=	'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SINO" />';
				var _SECUENCIA					=	'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SECUENCIA_IVA" />';
				var _URL_CONSULTA_PROVEEDOR		=	'<s:url namespace="/siniestros" action="consultaDatosConfiguracionProveedor" />';
				var _URL_MOV_PROVEEDOR			=	'<s:url namespace="/siniestros" action="guardarConfiguracionProveedor" />';
				
				var _VER_CONFIG_LAYOUT 			= '<s:url namespace="/siniestros"		action="includes/configuracionLayout" />';
			</script>
			<script type="text/javascript" src="${ctx}/js/proceso/siniestros/configuracionProv.js"></script>
	</head>
	<body>
		<div style="height:500px;">
			<div id="div_clau"></div>
		</div>
 	</body>
</html>