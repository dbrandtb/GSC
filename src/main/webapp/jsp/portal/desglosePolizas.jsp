<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Descuentos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_DESGLOSE_POLIZAS = "<s:url action='buscarDesglosePolizas' namespace='/desglosePolizas'/>";
    var _ACTION_AGREGAR_DESGLOSE_POLIZAS = "<s:url action='guardarDesglosePolizas' namespace='/desglosePolizas'/>";
    var _ACTION_EDITAR_DESGLOSE_POLIZAS = "<s:url action='guardarDesglosePolizas' namespace='/desglosePolizas'/>";
    var _ACTION_COPIAR_DESGLOSE_POLIZAS = "<s:url action='copiarDesglosePolizas' namespace='/desglosePolizas'/>";
    var _ACTION_BORRAR_DESGLOSE_POLIZAS = "<s:url action='borrarDesglosePolizas' namespace='/desglosePolizas'/>";
    var _ACTION_EXPORT = "<s:url action='exportarBusquedaDesglosePolizas' namespace='/desglosePolizas'/>";
    var _ACTION_OBTENER_CLIENTE_CORPO = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
    var _ACTION_OBTENER_PRODUCTOS_AYUDA = "<s:url action='obtenerProductosAyuda' namespace='/combos'/>";
    var _ACTION_OBTENER_PRODUCTOS_AYUDA_CLIENTE = "<s:url action='obtenerProductosAyudaCliente' namespace='/combos'/>";
    
    var _ACTION_OBTENER_CONCEPTOS_PRODUCTO = "<s:url action='obtenerConceptosProducto' namespace='/combos'/>";
    var _ACTION_OBTENER_CONCEPTOS_POR_PRODUCTO = "<s:url action='obtenerConceptosPorProducto' namespace='/combos'/>"
    
    var helpMap = new Map();
    var itemsPerPage=_NUMROWS;
    <%=session.getAttribute("helpMap")%>
    
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("16")%>
    
    _URL_AYUDA = "/catweb/desglosePoliza.html";
        
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/desglosePolizas/desglosePolizas.js"></script>
<!-- 
<script type="text/javascript" src="${ctx}/resources/scripts/portal/desglosePolizas/editarDesglosePolizas.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/desglosePolizas/agregarDesglosePolizas.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/desglosePolizas/copiarDesglosePolizas.js"></script>
 -->
 
 
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
               <div id="gridDesgloses" />
           </td>
       </tr>
    </table>
</body>
</html>