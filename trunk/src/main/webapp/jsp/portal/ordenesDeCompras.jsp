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
    var _ACTION_BUSCAR_ORDEN_DE_COMPRAS = "<s:url action='buscarOrdenesDeCompras' namespace='/ordenesDeCompras'/>";
    var _ACTION_FINALIZAR_ORDEN_DE_COMPRAS = "<s:url action='finalizarOrdenesDeCompras' namespace='/ordenesDeCompras'/>";
    var _ACTION_IR_DETALLE_ORDENES_DE_COMPRAS = "<s:url action='irDetalleOrdenesDeCompras' namespace='/ordenesDeCompras'/>";
    
//variables a detalleOrdenesDeCompras



    var itemsPerPage=10;
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("33")%>
    
    _URL_AYUDA = "/catweb/historialDeOrdenesDeCompra.html"; 
    
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script> 
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/OrdenesDeCompras/ordenesDeCompras.js"></script>
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