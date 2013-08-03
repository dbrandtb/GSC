<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<style type="text/css">    
        #button-grid .x-panel-body {
        border:1px solid #99bbe8; border-top:0 none;
        }
        .logo{background-image:url(../resources/images/aon/bullet_titulo.gif) !important;
        }        
    </style>
<title>P&aacute;ginas Principales</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">



<link href="${ctx}/resources/css/template_css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/header_light.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/toolbar_magenta.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/footer_magenta.css" rel="stylesheet" type="text/css" />


<!-- Estilos para extJs sin Modificar-->
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/ext-all.css" />
<link href="${ctx}/resources/css/template_css.css" rel="stylesheet" type="text/css" /> 


<script type="text/javascript">
	var _CONTEXT = "${ctx}";
	var _ACTION_PRINCIPAL      = "principal/paginas.action";
	var _ACTION_EXPORT_CONFIG  = "<s:url action='exportPaginas' namespace='/principal'/>";
	//var _ACTION_CONFIGPAG    = "<s:url action='configurar' namespace='/principal'/>";
    var _ACTION_CONFIGPAG      = "principal/configurar.action";
	var _ACTION_CONFIGURACION  = "principal/roles.action";
	var _ACTION_EDITAR         = "principal/editaPagina.action";  //  "<s:url action='editaPagina' namespace='/principal'/>";   //agregado
    var itemsPerPage=20;
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/PagingToolbar.js"></script> 
<script type="text/javascript" src="${ctx}/resources/scripts/principal/principal.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/principal/agregar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/principal/borrar.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
<div id="gridPag"/>
<div id="formPag"/>
</body>
</html>