<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<style type="text/css">    
        #button-grid .x-panel-body {
        border:1px solid #99bbe8; border-top:0 none;
        }
        .logo{background-image:url(../resources/images/aon/bullet_titulo.gif)!important;
        }        
    </style>
<!-- Algunos css para el UploadPanel:   -->
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/icons.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/filetree.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/filetype.css" />
 


<title>P&aacute;ginas Principales</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
	var _CONTEXT = "${ctx}";
	var _ACTION_CONFIGURACION  = "principal/roles.action";
	var _ACTION_PAGINAS        = "principal/paginas.action";
	var _ACTION_EXPORT_CONFIG  = "<s:url action='exportGenericoParams' namespace='/principal'/>";
	var _ROW_SESSION= '<s:property value="%{#session.get('LLAVEROW')}" />';
    var _CVECONF    = '<s:property value="%{#session.get('CLAVE_CONF')}" />';
    var _CVESISROL  = '<s:property value="%{#session.get('CLAVE_ROL')}" />';
    var _CVEELEM    = '<s:property value="%{#session.get('CLAVE_ELEM')}" />';
    //var _DESCONF    = '<s:property value="%{#session.get('DESC_CONF')}" />';
    //var _DESSISROL  = '<s:property value="%{#session.get('DESC_ROL')}" />';
    //var _DESELEM    = '<s:property value="%{#session.get('DESC_ELEM')}" />';
    
    var _DESCONF    = "<s:property value='dsConfiguracion'/>";
    var _DESSISROL  = "<s:property value='dsSistemaRol'/>";
    var _DESELEM    = "<s:property value='dsElemento'/>";
    var _RUTA_PUB_IMG =  "<s:property value='rutaPubImage'/>";
    // alert('_RUTA_PUB_IMG='+ _RUTA_PUB_IMG );

    var _ACTION_GUARDAR = "<s:url action='editarConf' namespace='/principal'/>";
    var _ACTION_GUARDARJSON = "<s:url action='editarConfJSON' namespace='/principal'/>";

    var _ACTION_AGREGAR = "<s:url action='guardarAction' namespace='/principal'/>";
    var _ACTION_AGREGARJSON = "<s:url action='guardarActionJSON' namespace='/principal'/>";

    var _ACTION_BORRAR = "<s:url action='borrarAction' namespace='/principal'/>";

    var _ACTION_UPLOAD = "<s:url action='uploadFiles' namespace='/principal'/>";

	var itemsPerPage = 10;
	
	var nameFile1 = '';
	var popup_window = '';
	var popup_winUpload = '';  
	//var winUploadFile ='';
	var editor;
	var accionEdit;
	
</script>

<!-- Archivos js para el UploadPanel:   -->
<script src="${ctx}/resources/scripts/util/FileUploader.js" type="text/javascript"></script>
<script src="${ctx}/resources/scripts/configurador/designer/fileTree/Ext.ux.form.BrowseButton.js" type="text/javascript"></script>
<script src="${ctx}/resources/scripts/util/UploadPanel.js" type="text/javascript"></script>


<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/principal/configuracion/configurar.js"></script>
<script type="text/javascript"  src="${ctx}/resources/extjs/ux/plugins/HtmlEditorImageInsert.js" ></script>
<script type="text/javascript" src="${ctx}/resources/scripts/principal/configuracion/agregarConfig.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/principal/configuracion/editar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/principal/configuracion/borrarConfig.js"></script>


</head>
    <body>
        <table>
            <tr>
                <td><div id="formConfig"/></td>
            </tr>
            <tr>
                <td><div id="gridConfigura"/></td>
            </tr>
        </table>
    </body>
</html>
