<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Reasignar Caso</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
  
    var _ACTION_BUSCAR_RESPONSABLES = "<s:url action='buscarNotificaciones' namespace='/notificaciones'/>";
    var _ACTION_BUSCAR_NIVELES_ATENCION = "<s:url action='buscarNotificacionesProcesos' namespace='/notificaciones'/>";
    var _ACTION_BUSCAR_TIEMPOS = "<s:url action='buscarNotificacionesProcesos' namespace='/notificaciones'/>";
    var _ACTION_BUSCAR_CASOS = "<s:url action='buscarNotificacionesProcesos' namespace='/notificaciones'/>";
    var _ACTION_BUSCAR_USUARIOS = "<s:url action='buscarNotificacionesProcesos' namespace='/notificaciones'/>";
    var _ACTION_BUSCAR_CASOS_ASIGNADOS = "<s:url action='buscarNotificacionesProcesos' namespace='/notificaciones'/>";
     
    var _ACTION_COMBO_RESPONSABLES = "<s:url action='buscarNotificaciones' namespace='/notificaciones'/>";
    var _ACTION_COMBO_NIVELES_ATENCION = "<s:url action='buscarNotificacionesProcesos' namespace='/notificaciones'/>";
    var _ACTION_COMBO_ROLES = "<s:url action='buscarNotificacionesProcesos' namespace='/notificaciones'/>";
    var _ACTION_COMBO_STATUS = "<s:url action='buscarNotificacionesProcesos' namespace='/notificaciones'/>";
     var _ACTION_IR_CONFIGURA_MATRIZ_TAREA = "<s:url action='irConfiguraMatrizTarea' namespace='/matrizAsignacion'/>";
        var itemsPerPage=10;
    <%=session.getAttribute("helpMap")%>
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap= new Map()</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/Multiselect.js"></script>



<script type="text/javascript" src="${ctx}/resources/scripts/matrizAsignacion/matrizAsignacionReasignarCaso.js"></script>

</head>
<body>

   <table class="headlines" cellspacing="10" border:"2">
        <tr valign="top">
            <td class="headlines" colspan="4">
                <div id="formBusqueda" />
            </td>
        </tr>
       <tr valign="top">
           <td class="headlines">
               <div id="gridResponsables" />
           </td>
       </tr>
       <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formDocumentos" />
            </td>
        </tr>
       
       <tr valign="top">
           <td class="headlines" colspan="2">
               <div id="gridTiempos" />
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
