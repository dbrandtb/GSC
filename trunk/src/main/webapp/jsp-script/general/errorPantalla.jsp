<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Error</title>
<script>
Ext.onReady(function()
{
	var respuestaOculta = '<s:property value="respuestaOculta" />';
	mensajeError('<s:property value="respuesta" />');
});
</script>
</head>
<body></body>
</html>