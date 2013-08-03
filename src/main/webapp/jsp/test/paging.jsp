<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Paging</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- Estilos para extJs -->
<link rel="stylesheet" type="text/css"
 	href="${ctx}/resources/extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" 
	href="${ctx}/resources/extjs/resources/css/xtheme-gray.css" />
<!--[if lte IE 6]>
<link href="${ctx}/resources/css/template_ie.css" rel="stylesheet" type="text/css" />
<![endif]-->
<script language="javascript">
	var _CONTEXT = "${ctx}";
	var _ACTION_PAGING = "<s:url action='paging' namespace='/test'/>";
	var _ACTION_EXPORT = "<s:url action='export' namespace='/test'/>";
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/test/paging.js"></script>
</head>
<body>
<h3>Paging</h3>
<div id="gridPaging"/>
</body>
</html>
