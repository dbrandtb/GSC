<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

   <!--  <style type="text/css">    
        #button-grid .x-panel-body {
            border:1px solid #99bbe8;
            border-top:0 none;
        }
        .logo{
            background-image:url(../resources/images/aon/bullet_titulo.gif) !important;
        }        
    </style>-->
<title>Configuración del Menú</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" /><!-- charset=utf-8 -->
<script type="text/javascript" src="${ctx}/resources/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctx}/resources/extjs/ext-all.js"></script>

<!-- Estilos para extJs sin Modificar-->
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/ext-all.css" />
<link href="${ctx}/resources/css/template_css.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript">
        var _CONTEXT = "${ctx}";
        var _ACTION_CONFIGMENU = "<s:url action='opciones' namespace='/configmenu'/>";
        var _ACTION_EXPORT_CONFIG = "<s:url action='exportGenericoParams' namespace='/principal'/>";
        var _cdTitulo = '<s:property value="CDTITULO" />';
        var itemsPerPage=20;
        var helpMap;
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/PagingToolbar.js"></script> 
<script type="text/javascript" src="${ctx}/resources/scripts/configmenu/opciones.js"></script>  
<script type="text/javascript">
        
</script>

</head>
<body>
    <table width="100%" align="center">
        <tr valign="top">
            <td>
                <div id="opcionesForm" />
            </td>
        </tr>
       <tr valign="top">
           <td>
                <div id="gridOpciones" />
           </td>
       </tr>
    </table>
</body>
</html>