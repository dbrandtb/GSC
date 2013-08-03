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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_PRINCIPAL="<s:url action='opcMenuUsuariosJson' namespace='/opcmenuusuario'/>";
    var _ACTION_EXPORT_MENU_USUARIO = "<s:url action='exportGenericoParams' namespace='/principal'/>";
    var _MENU_TITULO = '<s:property value="#session.MENU_TITULO" />';
    var _MENU_CLIENTE = '<s:property value="#session.MENU_CLIENTE" />';
    var _MENU_USUARIO = '<s:property value="#session.MENU_USUARIO" />';
    var _MENU_ROL = '<s:property value="#session.MENU_ROL" />';
    var _MENU_TIPO = '<s:property value="#session.MENU_TIPO" />';
    var itemsPerPage=20;
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/opcmenuusuario/principal.js"></script>
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
                <div id="formFiltro" />
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