<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<style type="text/css">    
        #button-grid .x-panel-body {border:1px solid #99bbe8; border-top:0 none;}.logo{background-image:url(../resources/images/aon/bullet_titulo.gif) !important;}        
    </style>
<title>Mantenimiento de Planes</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctx}/resources/extjs/ext-all.js"></script>

<link href="${ctx}/resources/css/template_css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/header_light.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/toolbar_magenta.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/footer_magenta.css" rel="stylesheet" type="text/css" />


<!-- Estilos para extJs sin Modificar-->
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/ext-all.css" />
<link href="${ctx}/resources/css/template_css.css" rel="stylesheet" type="text/css" /> 

<script type="text/javascript">
		var _CONTEXT = "${ctx}";
		var _ACTION_MANTTO = "<s:url action='planes' namespace='/mantto'/>";
		var _ACTION_EXPORT_PLAN="<s:url action='exportPlan' namespace='/mantto'/>";
	</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/PagingToolbar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/mantto/planes.js"></script>	
<script type="text/javascript">
</script>
</head>
<body>
<div id="gridPlanes" />
<div id="formPlanes"/>
</body>
</html>