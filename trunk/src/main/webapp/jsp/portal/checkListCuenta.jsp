<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Editar Estructuras</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>


<script language="javascript">
	var _CONTEXT = "${ctx}";
	var _ACTION_OBTENER_SECCION = "<s:url action='chkListObtenerSecciones' namespace='/checkListCuenta'/>";
	var _ACTION_OBTENER_TAREAS_SECCION = "<s:url action='chkListObtenerTareas' namespace='/checkListCuenta'/>";
	var _ACTION_OBTENER_CLIENTES = "<s:url action='editarEstructura' namespace='/checkListCuenta'/>";
	var _ACTION_OBTENER_ENCABEZADOS = "<s:url action='chkListObtenerEncabezados' namespace='/checkListCuenta'/>";
	var _ACTION_GUARDA_TAREA_SECCION = "<s:url action='chkListGuardarTarea' namespace='/checkListCuenta'/>";
	var _ACTION_IS_CONFIGURACION_COMPLETA = "<s:url action='chkListIsConfiguracionCompleta' namespace='/checkListCuenta'/>";
    var _ACTION_OBTENER_CLIENTES_CORP = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
    var _ACTION_IR_CHECKLIST_CONFIGURA_CUENTA = "<s:url action='irCheckListConfiguraCuenta' namespace='/checkListCuenta'/>";

	var _REDIRECT = "<s:url action='configurarEstructura' includeParams='none'/>";

	var _ACTION_OBTENER_LINEA_OPERACION = "<s:url action='obtenerLineasOperacion' namespace='/combos'/>";
	var CODIGO_CONFIGURACION = "<s:property value='codigoConfiguracion'/>";
	
	var helpMap = new Map();
    var itemsPerPage=10;
	<%=session.getAttribute("helpMap")%>  
 
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("10")%>    
    
</script>

<!--  <script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>-->
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/portal/checkListCuenta/checkListCuenta.js"></script>

</head>
<body>
                	<div id="formulario" ></div>
                	<div id="formBusqueda" ></div>
                	<div id="formBusquedaAlign" ></div>
                	<div id="formTareasSeccion" ></div>
                	
                    <!-- table style="width:100%">
                        <tr>
                            <td id="formulario" colspan="2">&nbsp;</td>
                        </tr>
				        <tr valign="top">
				            <td class="headlines" colspan="2">
				                <div id="formBusqueda" />
				            </td>
				        </tr>
				        <tr valign="top">
				            <td class="headlines" colspan="2">
				                <div id="formBusquedaAlign" />
				            </td>
				        </tr>
				        <tr valign="top">
				            <td class="headlines" colspan="2">
				                <div id="formTareasSeccion" />
				            </td>
				        </tr>
                    </table-->
</body>
</html>