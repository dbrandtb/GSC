<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Uso del Carrito de Compras</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>


<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_CARRITO_COMPRAS = "<s:url action='buscarCarritoCompras' namespace='/carritoCompras'/>";
    var _ACTION_GET_CARRITO_COMPRAS = "<s:url action='getCarritoCompras' namespace='/carritoCompras'/>";
    var _ACTION_GUARDAR_NUEVO_CARRITO_COMPRAS = "<s:url action='guardarCarritoCompras' namespace='/carritoCompras'/>";
    var _ACTION_GUARDAR_CARRITO_COMPRAS = "<s:url action='guardarCarritoCompras' namespace='/carritoCompras'/>";
    var _ACTION_BORRAR_CARRITO_COMPRAS = "<s:url action='borrarCarritoCompras' namespace='/carritoCompras'/>";
    
   <!-- Combos utilizados -->
	var _ACTION_OBTENER_CLIENTES_CORP = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
	var _ACTION_OBTENER_SINO = "<s:url action='obtenerSiNo' namespace='/combos'/>";
     var helpMap = new Map();
    // var itemsPerPage=20;
    
      var itemsPerPage = _NUMROWS;
    
    <%=session.getAttribute("helpMap")%> 

    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("14")%>    
    
    _URL_AYUDA = "/catweb/usoCarritoCompras.html";
    
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

 
<script type="text/javascript" src="${ctx}/resources/scripts/portal/carritoCompras/editarCarritoCompras.js"></script> 
<script type="text/javascript" src="${ctx}/resources/scripts/portal/carritoCompras/agregarCarritoCompras.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/carritoCompras/carritoCompras.js"></script>


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
               <div id="gridCarritoCompras" />
           </td>
       </tr>
    </table>
</body>
</html>