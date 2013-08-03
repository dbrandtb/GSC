<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Estatus de Casos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_STATUS_CASOS = "<s:url action='buscarStatusCasos' namespace='/statusCasos'/>";
    var _ACTION_BORRAR_STATUS_CASOS = "<s:url action='borrarStatusCasos' namespace='/statusCasos'/>";
    var _ACTION_OBTENER_STATUS_CASOS = "<s:url action='obtenerStatusCasos' namespace='/statusCasos'/>";
    var _ACTION_GUARDAR_STATUS_CASOS = "<s:url action='guardarStatusCasos' namespace='/statusCasos'/>";
    var _ACTION_EXPORTAR_STATUS_CASOS = "<s:url action='exportarStatusCasos' namespace='/statusCasos'/>";
    var _ACTION_BUSCAR_STATUS_CASOS_TAREAS = "<s:url action='buscarStatusCasosTareas' namespace='/statusCasos'/>";
    var _ACTION_GUARDAR_STATUS_CASOS_TAREAS = "<s:url action='guardarStatusCasosTareas' namespace='/statusCasos'/>";
    var _ACTION_BORRAR_STATUS_CASOS_TAREAS = "<s:url action='borrarStatusCasosTareas' namespace='/statusCasos'/>";
    var _ACTION_BUSCAR_STATUS_CASOS_TAREAS_PROCESOS = "<s:url action='buscarStatusCasosTareasProcesos' namespace='/statusCasos'/>";
    
// de los combos
    var _ACTION_OBTENER_STATUS_INDICADOR_AVISO= "<s:url action='comboStatusIndicadorAviso' namespace='/combos-catbo'/>";

    var helpMap = new Map();
    var itemsPerPage=10;
    var vistaTipo=1;
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("319")%>    
    
    _URL_AYUDA = "/backoffice/adminEstadoCasos.html";
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/Multiselect.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/statusCasos/statusCasos_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/statusCasos/statusCasos.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/statusCasos/statusCasosEditar_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/statusCasos/statusCasosEditar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/statusCasos/statusCasosTareas_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/statusCasos/statusCasosTareas.js"></script>



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