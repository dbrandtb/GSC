<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Oden de Compra</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_OBTENER_ORDEN_DE_COMPRAS_PERSONA = "<s:url action='obtenerOrdenesDeComprasPersonas' namespace='/ordenesDeCompras'/>";
    var _ACTION_OBTENER_ORDEN_DE_COMPRAS_DOMICILIO = "<s:url action='obtenerOrdenesDeComprasDireccion' namespace='/ordenesDeCompras'/>";
    var _ACTION_OBTENER_ORDEN_DE_COMPRAS_PAGAR = "<s:url action='obtenerOrdenesDeComprasPagar' namespace='/ordenesDeCompras'/>";
    var _ACTION_ORDEN_DE_COMPRAS_DETALLE = "<s:url action='obtenerOrdenesDeComprasDetalle' namespace='/ordenesDeCompras'/>";
    var _ACTION_ORDEN_DE_COMPRAS_PARCIAL_TOTAL = "<s:url action='obtenerOrdenesDeComprasParcialTotal' namespace='/ordenesDeCompras'/>";
    var _ACTION_FINALIZAR_ORDEN_DE_COMPRAS = "<s:url action='finalizarOrdenesDeCompras' namespace='/ordenesDeCompras'/>";
    var _ACTION_ORDEN_DE_COMPRAS_REGRESAR = "<s:url action='regresarOrdenesDeCompras' namespace='/ordenesDeCompras'/>";
    var _ACTION_OBTENER_MONTOS_CARRITO_COMPRAS = "<s:url action='obtenerOrdenesDeComprasParcialTotal' namespace='/ordenesDeCompras'/>";


//Seteo de valores de la estructura obtenidos
    var CODIGO_CARRO = "<s:property value='carro'/>";
    var CODIGO_CONTRATO = "<s:property value='contrato'/>";
    var CODIGO_TIPO_DOM = "<s:property value='tipoDomicilio'/>";


    var itemsPerPage=10;
    <%=session.getAttribute("helpMap")%>
     <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("33")%>
    
</script>
 
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/OrdenesDeCompras/detalleOrdenesDeCompras.js"></script>
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