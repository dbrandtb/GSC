<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Carga de Archivos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<!-- Algunos css para el UploadPanel:   -->
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/icons.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/filetree.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/filetype.css" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
     
	var _ACTION_BUSCAR_ARCHIVOS = "<s:url action='obtenerArchivos' namespace='/cargaArchivos'/>"; 
	
	var _ACTION_BAJAR_ARCHIVOS = "<s:url action='downloadArchivos' namespace='/cargaArchivos'/>";

    var _ACTION_AGREGAR_ARCHIVO = "<s:url action='agregarArchivos' namespace='/cargaArchivos'/>";
    
    var _ACTION_AGREGAR_ARCHIVOJSON = "<s:url action='agregarArchivosJSON' namespace='/cargaArchivos'/>";

    var _ACTION_COMBO_TIPO_ARCHIVO = "<s:url action='comboCargaArchivos' namespace='/combos-catbo'/>";
    
    var _ACTION_COMBO_TIPO_ARCHIVO2 = "<s:url action='comboCargaArchivos2' namespace='/combos-catbo'/>";
    
  	       
    var itemsPerPage=10;
    var helpMap= new Map();
   <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("810")%>
</script>

<!-- Archivos js para el UploadPanel:   -->
<script src="${ctx}/resources/scripts/util/FileUploader.js" type="text/javascript"></script>
<script src="${ctx}/resources/scripts/configurador/designer/fileTree/Ext.ux.form.BrowseButton.js" type="text/javascript"></script>
<script src="${ctx}/resources/scripts/util/UploadPanel.js" type="text/javascript"></script>


<script type="text/javascript" src="${ctx}/resources/scripts/consultarCargaDeArchivos/consultarCargaDeArchivos.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/consultarCargaDeArchivos/AnexarArchivoDigitalizado.js"></script>

</head>
<body>
    <table cellspacing="10">
        <tr valign="top">
            <td colspan="2">
                <div id="formBusqueda" />
            </td>
        </tr>
       <tr valign="top">
           <td colspan="2">
               <div id="gridSecciones" />
           </td>
       </tr>
       <tr><td><div id="encabezadoFijo" /></td></tr>
       <tr><td><div id="formDocumentos" /></td></tr>
    </table>
</body>
</html>