<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Seleccion de Polizas para Renovacion </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
	<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_EJECUTAR_SELECCION_POLIZAS = "<s:url action='ejecutarSeleccionPolizas' namespace='/seleccionPolizasRenovacion'/>";
    var _ACTION_IR_CONSULTAR_POLIZAS = "<s:url action='renovacionPolizas' namespace='/flujorenovacion'/>";
    
	//var _ACTION_IR_ = "<s:url action='' namespace='/'/>";	
	 	
	// Combos url de los combos
    var _ACTION_OBTENER_CLIENTES = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
    var _ACTION_OBTENER_CLIENTES_REN = "<s:url action='obtenerClientesCorpRen' namespace='/combos'/>";
    var _ACTION_OBTENER_TIPOS_RAMO = "<s:url action='obtenerRamosPorCliente' namespace='/combos'/>";
    var _ACTION_OBTENER_TIPOS_RAMO_REN = "<s:url action='obtenerRamosPorClienteRen' namespace='/combos'/>";
    var _ACTION_OBTENER_ASEGURADORA = "<s:url action='comboAseguradorasPorCliente' namespace='/combos'/>";
    var _ACTION_OBTENER_ASEGURADORA_REN = "<s:url action='comboAseguradorasPorClienteRen' namespace='/combos'/>";
    var _ACTION_OBTENER_PRODUCTOS = "<s:url action='obtenerProductosAseguradoraCliente' namespace='/combos'/>";
    var _ACTION_OBTENER_PRODUCTOS_REN = "<s:url action='obtenerProductosAseguradoraClienteRen' namespace='/combos'/>";
    
    var helpMap = new Map();
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("203")%>
    
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/seleccionPolizasRenovacion/seleccionPolizasRenovacion.js"></script>
</head>
<body>

   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formSeleccionPolizas" />
            </td>
        </tr>
    </table>
</body>
</html>