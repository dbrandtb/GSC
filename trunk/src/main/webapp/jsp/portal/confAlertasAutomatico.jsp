<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Checklist de la configuración de la cuenta</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>

<script language="javascript">
	var _CONTEXT = "${ctx}";
	var _ACTION_BUSCAR_CONFIGURACION_ALERTAS = "<s:url action='buscarConfiguracionAlertasAutomatico' namespace='/configuracionAlertasAutomatico'/>";
	var _ACTION_GUARDAR_CONFIGURACION_ALERTAS = "<s:url action='guardarConfiguracionAlertasAutomatico' namespace='/configuracionAlertasAutomatico'/>";
	var _ACTION_LISTA_CLIENTES = "<s:url action='irEditarConfiguraCuenta' namespace='/checklistConfiguraCuenta'/>";
	var _ACTION_ELIMINA_CONFIGURACION_ALERTAS = "<s:url action='borrarConfiguracionAlertasAutomatico' namespace='/configuracionAlertasAutomatico'/>";
	var _ACTION_CONF_ALERTAS_GET = "<s:url action='getConfiguracionAlertasAutomatico' namespace='/configuracionAlertasAutomatico'/>";
    var _ACTION_CONF_ALERTAS_EXPORTAR = "<s:url action='exportConfiguraAlertasAuto' namespace='/configuracionAlertasAutomatico'/>";

	<!-- Combos utilizados -->
	var _ACTION_COMBO_USUARIOS = "<s:url action='confAlertasAutoUsuarios' namespace='/combos'/>";
	var _ACTION_COMBO_CLIENTES = "<s:url action='confAlertasAutoClientes' namespace='/combos'/>";
	var _ACTION_COMBO_PROCESOS = "<s:url action='confAlertasAutoProcesos' namespace='/combos'/>";
	var _ACTION_COMBO_TEMPORALIDAD = "<s:url action='confAlertasAutoTemporalidad' namespace='/combos'/>";
	var _ACTION_COMBO_ROL = "<s:url action='confAlertasAutoRol' namespace='/combos'/>";
	var _ACTION_COMBO_TIPORAMO = "<s:url action='obtenerRamosCliente' namespace='/combos'/>";
	var _ACTION_COMBO_REGION = "<s:url action='confAlertasAutoRegion' namespace='/combos'/>";
	var _ACTION_COMBO_ASEGURADORA = "<s:url action='obtenerAseguradorasCliente' namespace='/combos'/>";
	var _ACTION_COMBO_PRODUCTOS = "<s:url action='obtenerProductosAseguradoraCliente' namespace='/combos'/>";
    var _ACTION_COMBO_CLIENTES_CORP_POR_USUARIO = "<s:url action='comboClientesPorUsuario' namespace='/combos'/>";
    //var _ACTION_COMBO_PRODUCTOS = "<s:url action='obtenerProductosDescuentos' namespace='/combos'/>";
    var _ACTION_COMBO_FRECUENCIAS = "<s:url action='obtenerFrecuencias' namespace='/combos'/>";
   
    var helpMap = new Map();
    
     // var itemsPerPage=20;
     
      var itemsPerPage = _NUMROWS;
     
    <%=session.getAttribute("helpMap")%>
	<%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("12")%>
	
	 _URL_AYUDA = "/catweb/confAlertas.html";

</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configuracionAlertasAutomatico/agregarConfAlertasAutomatico.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configuracionAlertasAutomatico/editarConfAlertasAutomatico.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configuracionAlertasAutomatico/confAlertasAutomatico.js"></script>
</head>
<body>
                    <table>
                        <tr>
                            <td id="formulario"></td>
                        </tr>
				        <tr valign="top">
				            <td colspan="2">
				                <div id="gridConfiguraAlertas" />
				            </td>
				        </tr>
				        <tr>
				        	<td>
				        		<div id="accordion-div"></div>
				        	</td>
				        </tr>
                    </table>

</body>
</html>