<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Tareas CatBo</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_TAREAS_CAT_BO = "<s:url action='buscarTareasCatBo' namespace='/tareasCatBo'/>";
    var _ACTION_BORRAR_TAREAS_CAT_BO = "<s:url action='borrarTareasCatBo' namespace='/tareasCatBo'/>";
    var _ACTION_OBTENER_TAREAS_CAT_BO = "<s:url action='obtenerTareasCatBo' namespace='/tareasCatBo'/>";
    var _ACTION_GUARDAR_TAREAS_CAT_BO = "<s:url action='guardarTareasCatBo' namespace='/tareasCatBo'/>";
    var _ACTION_EXPORTAR_TAREAS_CAT_BO = "<s:url action='exportarTareasCatBo' namespace='/tareasCatBo'/>";
    var _ACTION_BUSCAR_TAREAS_CAT_BO_VALIDA = "<s:url action='buscarTareasCatBoValidar' namespace='/tareasCatBo'/>";
    
     var _ACTION_OBTENER_COMPRA_TIEMPO = "<s:url action='obtieneConfigCompraTiempo' namespace='/configurarCompraTiempo'/>";
     var _ACTION_AGREGAR_COMPRA_TIEMPO = "<s:url action='agregarCompraTiempo' namespace='/configurarCompraTiempo'/>";
     var _ACTION_GUARDAR_COMPRA_TIEMPO = "<s:url action='guardarCompraTiempo' namespace='/configurarCompraTiempo'/>";
     var _ACTION_BORRAR_COMPRA_TIEMPO = "<s:url action='borrarCompraTiempo' namespace='/configurarCompraTiempo'/>";
    
// de los combos
    var _ACTION_OBTENER_TAREAS_TAREAS_CAT_BO= "<s:url action='obtenerComboProcesosCat' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_ESTATUS_TAREAS_CAT_BO = "<s:url action='comboEstatusTareasCatBo' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_PRIORIDAD_TAREAS_CAT_BO = "<s:url action='comboPrioridadTareasCatBo' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_MODULO_TAREAS_CAT_BO = "<s:url action='traerDatosModulos' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_UNIDAD = "<s:url action='obtenerUnidad' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_NIVEL = "<s:url action='obtenerNivel' namespace='/combos-catbo'/>";

	var helpMap=new Map();
    var itemsPerPage=_NUMROWS;
    var vistaTipo=1;
    <%=session.getAttribute("helpMap")%>
      
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("316")%>  
    
    _URL_AYUDA = "/backoffice/configTareas.html";
      
</script>
 
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/Multiselect.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/configurarCompraTiempo/configurarCompraTiempo.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/configurarCompraTiempo/configurarCompraTiempo_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/tareasCatBo/tareasCatBoEditar_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/tareasCatBo/tareasCatBoEditar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/tareasCatBo/tareasCatBo_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/tareasCatBo/tareasCatBo.js"></script>

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
    </table>
</body>
</html>