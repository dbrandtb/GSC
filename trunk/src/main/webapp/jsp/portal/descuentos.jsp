<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Descuentos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_DESCUENTOS = "<s:url action='buscarDescuentos' namespace='/descuentos'/>";
    var _ACTION_IR_AGREGAR_DESCUENTO = "<s:url action='irAgregarDescuento' namespace='/descuentos'/>";
    var _ACTION_IR_EDITAR_DESCUENTO = "<s:url action='irEditarDescuento' namespace='/descuentos'/>";
    var _ACTION_COPIAR_DESCUENTO = "<s:url action='copiarDescuento' namespace='/descuentos'/>";
    var _ACTION_BORRAR_DESCUENTO = "<s:url action='borrarDescuento' namespace='/descuentos'/>";
    var _ACTION_EXPORT_DESCUENTOS = "<s:url action='exportarDescuentos' namespace='/descuentos'/>";
    
    var helpMap = new Map();
    var itemsPerPage= _NUMROWS;

    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("15")%>
    
    _URL_AYUDA = "/catweb/descuentos.html";
    
</script>


<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>

<script type="text/javascript" src="${ctx}/resources/scripts/portal/descuentos/descuentos.js"></script>


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
               <div id="gridDescuentos" />
           </td>
       </tr>
    </table>
</body>
</html>