<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Configuracion de Numeracion de Endosos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_OBTENER_NUMERACION_ENDOSOS = "<s:url action='obtenerNumeracionEndosos' namespace='/configurarNumeracionEndosos'/>";
    var _ACTION_GET_NUMERACION_ENDOSOS = "<s:url action='getNumeracionEndosos' namespace='/configurarNumeracionEndosos'/>";
    var _ACTION_EXPORT_NUMERACION_ENDOSOS = "<s:url action='exportarNumeracionEndosos' namespace='/configurarNumeracionEndosos'/>"; 
    var _ACTION_BORRAR_NUMERACION_ENDOSO = "<s:url action='borrarNumeracionEndoso' namespace='/configurarNumeracionEndosos'/>";
	var _ACTION_GUARDAR_NUMERACION_ENDOSO = "<s:url action='guardarNumeracionEndoso' namespace='/configurarNumeracionEndosos'/>";	
	//var _ACTION_BACK_TO_SOMEWHERE = "<s:url action='' namespace='/'/>";
	
	//COMBOS
	var _ACTION_OBTENER_CLIENTES_CORPO = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
	var _ACTION_OBTENER_ASEGURADORAS = "<s:url action='obtenerAseguradorasCliente' namespace='/combos'/>";
	var _ACTION_OBTENER_PRODUCTOS_ASEG_CORPO = "<s:url action='obtenerProductosAseguradoraCliente' namespace='/combos'/>";
	var _ACTION_OBTENER_PLANES = "<s:url action='obtenerPlanesProducto' namespace='/combos'/>";
	var _ACTION_OBTENER_NRO_POLIZAS = "<s:url action='obtenerNrosPolizasAseguradoraProductoPlanCorpo' namespace='/combos'/>";
	var _ACTION_OBTENER_POLIZAS_MAESTRAS = "<s:url action='obtenerPolizasMaestrasAseguradoraProductoCorpo' namespace='/combos'/>";
	var _ACTION_OBTENER_VALORES = "<s:url action='comboObtenerIndNumEndosos' namespace='/combos'/>";
	
	//EXPRESIONES
	var ACTION_TABLA_EXPRESIONES ="<s:url namespace='expresiones' action='ComboTabla' includeParams='none'/>";
	var ACTION_COLUMNA_EXPRESIONES = "<s:url namespace='expresiones' action='ComboColumna' includeParams='none'/>";
	var ACTION_CLAVE_EXPRESIONES = "<s:url namespace='expresiones' action='ComboClave' includeParams='none'/>";
	
    //VARIABLE PARA LA PAGINACIÓN DE LA GRILLA
	var helpMap = new Map();
    var itemsPerPage=20;

    
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("104")%>
    
	_URL_AYUDA = "/catweb/configurarNumerosDeEndosos.html";
	
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>


<!-- Para usar lo de Expresiones:  -->
<script type="text/javascript" src="${ctx}/resources/scripts/utilities/checkColumnPlugin.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/productos/tablaApoyo5Claves/tablaApoyo5Claves.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/expresiones/expresiones.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/portal/configurarNumeracionEndosos/configurarNumeracionEndosos_abm.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configurarNumeracionEndosos/configurarNumeracionEndosos.js"></script>

</head>
<body>

   <table cellspacing="4">
        <tr valign="top">
            <td colspan="2">
                <div id="formBusqueda" />
            </td>
        </tr>
       <tr valign="top">
           <td colspan="2">
               <div id="grid" />
           </td>
       </tr>
    </table>
</body>
</html>