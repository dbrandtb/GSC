<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Roles de Ejecucion de Renovacion</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>


<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_GET_ENCABEZADO_ROLES_RENOVACION = "<s:url action='getEncabezadoRolesRenovacion' namespace='/rolesRenovacion'/>";
    var _ACTION_OBTENER_ROLES_RENOVACION = "<s:url action='obtenerRolesRenovacion' namespace='/rolesRenovacion'/>";    
    var _ACTION_AGREGAR_GUARDAR_ROL_RENOVACION = "<s:url action='agregarGuardarRolRenovacion' namespace='/rolesRenovacion'/>";
    var _ACTION_BORRAR_ROL_RENOVACION = "<s:url action='borrarRolRenovacion' namespace='/rolesRenovacion'/>";
    var _ACTION_EXPORTAR_ROLES_RENOVACION = "<s:url action='getModel' namespace='/rolesRenovacion'/>";    
    var _ACTION_OBTENER_ACCIONES_RENOVACION_ROL = "<s:url action='obtenerAccionesRenovacionRol' namespace='/combos'/>";
	var _ACTION_REGRESAR_A_CONSULTA_CONFIGURARCION_RENOVACION =  "<s:url action='irConfiguracionRenovacion' namespace='/rehabilitacionManual'/>";
    var _CodRenovacion= "<s:property value='cdRenova'/>";
    
   
    var itemsPerPage= 20;
    
    var helpMap = new Map();

	<%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("127","1")%>
	<%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("128","1")%>
    
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>


<script type="text/javascript" src="${ctx}/resources/scripts/portal/rolesRenovacion/configurarRolRenovacion.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/rolesRenovacion/agregarRolRenovacion.js"></script>

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