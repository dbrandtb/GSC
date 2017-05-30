<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Consulta Clausulas</title>
			<script type="text/javascript">
				var _CONTEXT = '${ctx}';
				var _URL_CATALOGOS 			=	'<s:url namespace="/catalogos" 	action="obtieneCatalogo" />';
				var _CAT_MORBILIDAD        =   '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TATRIPOL"/>';
				var _URL_CONSULTA_MORBILIDAD	=	'<s:url namespace="/emision" action="consultaDatosConfiguracionMorbilidad" />';
			</script>
			<script type="text/javascript" src="${ctx}/js/proceso/emision/configuracionMorbilidad.js"></script>
	</head>
	<body>
		<div style="height:1000px;">
			<div id="div_clau"></div>
		</div>
 	</body>
</html>