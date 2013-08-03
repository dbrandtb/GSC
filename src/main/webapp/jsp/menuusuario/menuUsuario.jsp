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
<title>Menu Usuarios</title>
<meta http-equiv="Expires" content="Tue, 20 Aug 2010 14:25:27 GMT"/> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">



<!-- Se agregaron temporalmente los archivos js (para que se vea la pantalla en OAS, la cual no respeta el decorator) -->
<script type="text/javascript" src="${ctx}/resources/extjs/adapter/ext/ext-base.js"></script>

<script type="text/javascript" src="${ctx}/resources/extjs/ext-all.js"></script>
<link href="${ctx}/resources/css/template_css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/header_light.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/toolbar_magenta.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/footer_magenta.css" rel="stylesheet" type="text/css" />
<!-- Estilos para extJs -->
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/${ctx}/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/${ctx}/resources/css/xtheme-gray.css" />



<script type="text/javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_PRINCIPAL="<s:url action='menuUsuariosJson' namespace='/menuusuario'/>";
    var _ACTION_EXPORT_MENU_USUARIO = "<s:url action='exportGenericoParams' namespace='/principal'/>";
    var _ACTION_OPCIONES_MENU_USUARIO = "<s:url action='opcMenuUsuario' namespace='/opcmenuusuario'/>";

	var itemsPerPage = 20;
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/menuusuario/principal.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formMenu" />
            </td>
        </tr>
       <tr valign="top">
           <td class="headlines" colspan="2">
                <div id="gridConfig" />
           </td>
       </tr>
    </table>
</body>
</html>