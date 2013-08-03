<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>

<title>Configurador Pantallas</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<script type="text/javascript" src="${ctx}/resources/extjs/adapter/ext/ext-base.js"></script>

<script type="text/javascript" src="${ctx}/resources/extjs/ext-all.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/screenBuilder/main.css" />

<!-- Estilos para extJs sin Modificar-->
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/ext-all.css" />

<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/xtheme-gray.css" />


<jsp:include page="/resources/jsp-script/configurador/pantallas/setParametersTab-script.jsp" flush="true" />
<jsp:include page="/resources/jsp-script/configurador/pantallas/screenDataTab-script.jsp" flush="true" />
<jsp:include page="/resources/jsp-script/configurador/pantallas/activationTab-script.jsp" flush="true" />

<jsp:include page="/resources/jsp-script/configurador/pantallas/northZone-script.jsp" flush="true" />
<jsp:include page="/resources/jsp-script/configurador/pantallas/westZone-script.jsp" flush="true" />
<jsp:include page="/resources/jsp-script/configurador/pantallas/centerZone-script.jsp" flush="true" />


<jsp:include page="/resources/jsp-script/configurador/pantallas/screenDesignerViewPort-script.jsp" flush="true" />

</head>
<body>

 
</body>
</html>