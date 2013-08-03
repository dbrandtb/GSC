<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Notificaciones</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
  
    var _ACTION_BUSCAR_NOTIFICACIONES = "<s:url action='buscarNotificaciones' namespace='/notificaciones'/>";
    var _ACTION_BUSCAR_NOTIFICACIONES_PROCESOS = "<s:url action='buscarNotificacionesProcesos' namespace='/notificaciones'/>";
    var _ACTION_BUSCAR_PROCESOS_NOTIFICACIONES = "<s:url action='buscarProcesosNotificaciones' namespace='/notificaciones'/>";
    var _ACTION_BUSCAR_ESTADOS_NOTIFICACIONES = "<s:url action='buscarEstadosNotificaciones' namespace='/notificaciones'/>";
    var _ACTION_BUSCAR_ESTADOS_CASO = "<s:url action='buscarEstadosCaso' namespace='/notificaciones'/>";
    
    var _ACTION_BORRAR_NOTIFICACIONES = "<s:url action='borrarNotificaciones' namespace='/notificaciones'/>";
    var _ACTION_OBTENER_NOTIFICACIONES = "<s:url action='getNotificaciones' namespace='/notificaciones'/>";
    var _ACTION_GUARDAR_NOTIFICACIONES = "<s:url action='guardarNotificaciones' namespace='/notificaciones'/>";
    var _ACTION_GUARDAR_NOTIFICACIONES_PROCESO = "<s:url action='guardarNotificacionesProcesos' namespace='/notificaciones'/>";
    var _ACTION_BUSCAR_VARS_NOTIFICACIONES = "<s:url action='obtenerVarNotificaciones' namespace='/notificaciones'/>";
    
    
    var _ACTION_OBTENER_NOTIFICACIONES_REGION= "<s:url action='comboNotificacionesRegion' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_NOTIFICACIONES_TIPO_METODO_ENVIO= "<s:url action='comboTipoMetodoEnvio' namespace='/combos-catbo'/>";
   var helpMap= new Map() 
    var itemsPerPage=_NUMROWS;
    <%=session.getAttribute("helpMap")%>
    var _ACTION_BUSCAR_FORMATOS_DOCUMENTOS = "<s:url action='buscarFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_BORRAR_FORMATOS_DOCUMENTOS = "<s:url action='borrarFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_GET_FORMATO_DOCUMENTOS = "<s:url action='getFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_GUARDAR_FORMATO_DOCUMENTOS = "<s:url action='guardarFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_EXPORTAR_FORMATO_DOCUMENTOS = "<s:url action='exportarFormatosDocumentos' namespace='/formatosDocumentos'/>";
    
      <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("501")%>
      
    _URL_AYUDA = "/backoffice/consultaNotificaciones.html";  
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/Multiselect.css"/>
<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/Multiselect.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/notificaciones/notificaciones_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/notificaciones/notificacionesEditar_stores.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/formatosDocumentos/formatosDocumentos_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/notificaciones/notificacionesEditar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/formatosDocumentos/formatosDocumentosComun.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/formatosDocumentos/formatosDocumentosWindow.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/formatosDocumentos/formatosDocumentosEditar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/notificaciones/notificaciones.js"></script>

</head>
<body>

   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formBusqueda" />
            </td>
        </tr>
       <tr valign="top">
           <td class="headlines" colspan="2">
               <div id="gridNotificaciones" />
           </td>
       </tr>
       <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formDocumentos" />
            </td>
        </tr>
       
       <tr valign="top">
           <td class="headlines" colspan="2">
               <div id="gridElementos" />
           </td>
       </tr>
       <tr valign="top">
           <td>
               <div id="formMultiselect" />
           </td>
       </tr>
       <tr valign="top">
           <td>
               <div id="formMultiselectEdos" />
           </td>
       </tr>
       
    </table>
</body>
</html>
