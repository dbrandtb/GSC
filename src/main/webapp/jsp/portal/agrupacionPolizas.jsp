<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Agrupacion por Polizas</title>
<meta http-equiv="Content-Type" content = "text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_AGRUPACION_POLIZAS = "<s:url action='buscarAgrupaciones' namespace='/agrupacionPolizas'/>";
    var _ACTION_GET_AGRUPACION_POLIZAS = "<s:url action='getAgrupacion' namespace='/agrupacionPolizas'/>";
    var _ACTION_INSERTAR_AGRUPACION_POLIZAS = "<s:url action='guardarNuevaAgrupacion' namespace='/agrupacionPolizas'/>";
    var _ACTION_GUARDAR_AGRUPACION_POLIZAS = "<s:url action='guardarAgrupacion' namespace='/agrupacionPolizas'/>";
    var _ACTION_BORRAR_AGRUPACION_POLIZAS = "<s:url action='borrarAgrupacion' namespace='/agrupacionPolizas'/>";
    var _ACTION_IR_CONFIGURAR_AGRUPACION =  "<s:url action='irConfigurarAgrupacion' namespace='/agrupacionPolizas'/>";
    var _ACTION_EXPORTAR_CONFIGURAR_AGRUPACION =  "<s:url action='exportarAgrupacion' namespace='/agrupacionPolizas'/>";
    
    <!-- Combos utilizados -->
     
    var _ACTION_OBTENER_PRODUCTOS_ASEGURADORA_CLIENTES = "<s:url action='obtenerProductosAseguradoraCliente' namespace='/combos'/>";
	var _ACTION_OBTENER_PRODUCTOS_TRAMO_CLIENTE = "<s:url action='obtenerComboProductosTramoCliente' namespace='/combos'/>";
	var _ACTION_OBTENER_RAMOS_CLIENTE = "<s:url action='obtenerRamosCliente' namespace='/combos'/>";
	var _ACTION_OBTENER_CLIENTES_CORPORATIVO = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
	var _ACTION_OBTENER_ASEGURADORA_CLIENTES = "<s:url action='obtenerAseguradorasCliente' namespace='/combos'/>";
	var _ACTION_OBTENER_TIPO_AGRUPACION = "<s:url action='obtenerTiposAgrupacionPoliza' namespace='/combos'/>";
	var _ACTION_OBTENER_ESTADOS = "<s:url action='obtenerEstadosAgrupacionPoliza' namespace='/combos'/>";
	
  //   var itemsPerPage=20;
    var itemsPerPage = _NUMROWS;
    
    var helpMap = new Map();
    
	<%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("18")%>
	 
	 _URL_AYUDA = "/catweb/agrupacionPolizas.html";
	 
	
</script>
 

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/agrupacionPolizas/editarAgrupacionPolizas.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/agrupacionPolizas/agregarAgrupacionPolizas.js"></script> 
<script type="text/javascript" src="${ctx}/resources/scripts/portal/agrupacionPolizas/agrupacionPolizas.js"></script>

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
               <div id="gridAgrupacionPolizas" />
           </td>
       </tr>
    </table>
</body>
</html>