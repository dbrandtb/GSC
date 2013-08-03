<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Tareas del Checklist</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_TAREAS_CHECKLIST = "<s:url action='buscarTareasClick' namespace='/tareas-checklist'/>";
    var _ACTION_GET_TAREA_CHECKLIST = "<s:url action='getTareaChecklist' namespace='/tareas-checklist'/>";
    var _ACTION_GUARDAR_NUEVO_TAREA_CHECKLIST = "<s:url action='guardarNuevaTareaChecklist' namespace='/tareas-checklist'/>";
    var _ACTION_VALIDA_BORRAR_TAREA_CHECKLIST = "<s:url action='validaBorraTareaChecklist' namespace='/tareas-checklist'/>";
    var _ACTION_BORRA_TAREA_CHECKLIST = "<s:url action='borraTareaChecklist' namespace='/tareas-checklist'/>";
    var _ACTION_GUARDAR_TAREA_CHECKLIST = "<s:url action='guardarTareaChecklist' namespace='/tareas-checklist'/>";


	//url de los comboBox
	var _ACTION_OBTENER_SECCIONES = "<s:url action='obtenerSecciones' namespace='/combos'/>";
	//var _ACTION_OBTENER_TAREAS_PADRE = "<s:url action='obtenerTareasPadre' namespace='/combos'/>";
	var _ACTION_OBTENER_ESTADOS = "<s:url action='obtenerEstados' namespace='/combos'/>";
    var _ACTION_LISTA_TAREAS = "<s:url action='listaTareasCombo' namespace='/tareas-checklist'/>";
   //  var itemsPerPage=20;
   
    var itemsPerPage = _NUMROWS;
    var helpMap = new Map();
    
	<%=session.getAttribute("helpMap")%>
	<%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("9")%>
	
	  _URL_AYUDA = "/catweb/tareasChecklist.html";
	
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<!-- script type="text/javascript" src="${ctx}/resources/extjs/ext-all-debug.js"></script-->

<script type="text/javascript" src="${ctx}/resources/scripts/portal/checklist/editarTareaChecklist.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/checklist/agregarTareaChecklist.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/checklist/borrarTareaChecklist.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/checklist/tareasChecklist.js"></script>

</head>
<body>

   <table>
        <tr>
            <td>
                <div id="formBusqueda" />
            </td>
        </tr>
       <tr>
           <td>
               <div id="gridTareas" />
           </td>
       </tr>
    </table>
</body>
</html>