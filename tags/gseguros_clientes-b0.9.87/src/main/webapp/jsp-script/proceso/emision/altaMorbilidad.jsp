<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Consulta Clausulas</title>
			<script type="text/javascript">
				var _CONTEXT = '${ctx}';
				var _p21_urlExisteMorbilidadExistente    = '<s:url namespace="/emision"         action="existeMorbilidadNueva" />';
                var _p21_urlSubirCensoMorbilidadArchivo  = '<s:url namespace="/emision"         action="subirCensoMorbilidadArchivo"      />';
                var _p21_urlSubirCensoMorbilidad         = '<s:url namespace="/emision"         action="subirCensoMorbilidad"             />';
                var _p21_urlRefrescarPantalla            = '<s:url namespace="/emision"         action="altaMorbilidad"                  />';
                var _p21_urlRefrescarPantallaCotizacion  = '<s:url namespace="/emision"         action="cotizacionGrupo"                  />';
                var valorAction = <s:property value='paramsJson' escapeHtml='false'/>;
                debug("valorAction ====>", valorAction);
			</script>
			<script type="text/javascript" src="${ctx}/js/proceso/emision/altaMorbilidad.js"></script>
	</head>
	<body>
		<div style="height:1000px;">
			<div id="div_clau"></div>
		</div>
 	</body>
</html>