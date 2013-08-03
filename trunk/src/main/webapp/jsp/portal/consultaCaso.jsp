<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Matriz de Asignacion</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
  
    var _ACTION_BUSCAR_NOTIFICACIONES = "<s:url action='buscarNotificaciones' namespace='/notificaciones'/>";
    var _ACTION_BUSCAR_NOTIFICACIONES_PROCESOS = "<s:url action='buscarNotificacionesProcesos' namespace='/notificaciones'/>";
    var _ACTION_BUSCAR_PROCESOS_NOTIFICACIONES = "<s:url action='buscarProcesosNotificaciones' namespace='/notificaciones'/>";
    var _ACTION_BORRAR_NOTIFICACIONES = "<s:url action='borrarNotificaciones' namespace='/notificaciones'/>";
    var _ACTION_OBTENER_NOTIFICACIONES = "<s:url action='getNotificaciones' namespace='/notificaciones'/>";
    var _ACTION_GUARDAR_NOTIFICACIONES = "<s:url action='guardarNotificaciones' namespace='/notificaciones'/>";
    var _ACTION_GUARDAR_NOTIFICACIONES_PROCESO = "<s:url action='guardarNotificacionesProcesos' namespace='/notificaciones'/>";
    
    var _ACTION_OBTENER_NOTIFICACIONES_FORMATO= "<s:url action='comboNotificacionesFormato' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_NOTIFICACIONES_TIPO_METODO_ENVIO= "<s:url action='comboTipoMetodoEnvio' namespace='/combos-catbo'/>";
    
    var itemsPerPage=10;
    <%=session.getAttribute("helpMap")%>
    var _ACTION_BUSCAR_FORMATOS_DOCUMENTOS = "<s:url action='buscarFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_BORRAR_FORMATOS_DOCUMENTOS = "<s:url action='borrarFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_GET_FORMATO_DOCUMENTOS = "<s:url action='getFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_GUARDAR_FORMATO_DOCUMENTOS = "<s:url action='guardarFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_EXPORTAR_FORMATO_DOCUMENTOS = "<s:url action='exportarFormatosDocumentos' namespace='/formatosDocumentos'/>";
    
  
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap= new Map()</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/Multiselect.js"></script>



<script type="text/javascript" src="${ctx}/resources/scripts/consultarCasos/consultarCasos.js"></script>

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
               <div id="gridMatrizAsignacion" />
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
    </table>
</body>
</html>
