<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
  <style type="text/css">    
        #button-grid .x-panel-body {
            border:1px solid #99bbe8;
            border-top:0 none;
        }
        .logo{
            background-image:url(../resources/images/aon/bullet_titulo.gif) !important;
        }
        
    
        .alinear {
        margin-top:20px; margin-left: auto; margin-right: auto; 
        }
                
    </style>
<title>Búsqueda de conjuntos de Pantalla</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"
    src="${ctx}/resources/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript"
    src="${ctx}/resources/extjs/ext-all.js"></script>

<!-- Estilos para extJs sin Modificar-->
<link rel="stylesheet" type="text/css"
    href="${ctx}/resources/extjs/resources/css/ext-all.css" />
<link href="${ctx}/resources/css/template_css.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${ctx}/resources/scripts/configurador/pantallas/busqueda.js"></script>



<script language="javascript">
	var _CONTEXT = "${ctx}";
	var _ACTION_PAGING = "<s:url action='paging' namespace='/confpantallas'/>";
	var _ACTION_EXPORT = "<s:url action='export' namespace='/confpantallas'/>";
  
</script>

</head>
<body>

<div id="pantalla"></div>
</body>
</html>