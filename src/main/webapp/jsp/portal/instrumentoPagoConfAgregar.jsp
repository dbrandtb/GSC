<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Configuracion de Instrumento de Pago</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script language="javascript">
	var _CONTEXT = "${ctx}";
	   
    var _ACTION_COMBO_ISTRUMENTO_PAGO  =  "<s:url action='obtenerComboProcesosCat' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_NIVEL_CORPO = "<s:url action='comboClientesCorpBO' namespace='/combos-catbo'/>";    
    var _ACTION_COMBO_ASEGURADORA =  "<s:url action='comboAseguradorasPorCliente' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_PRODUCTOS = "<s:url action='comboProductosPorAseguradorYCliente' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_LISTA_VALORES = "<s:url action='obtenerListaValoresInstPago' namespace='/combos'/>";
     var _ACTION_COMBO_CONDICION = "<s:url action='obtenerCondicionInstPago' namespace='/combos'/>";
    
      
     var helpMap = new Map();
     var itemsPerPage = _NUMROWS;
     <%=session.getAttribute("helpMap")%>
     <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("14")%> 
     
</script>


<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/instrumentoPagoConfAgregar/ExtCheckBoxGroupDefinicion.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/instrumentoPagoConfAgregar/instrumentoPagoConfAgregar.js"></script>

</head>
<body>
   
   <table>
        <tr>
            <td>
                <div id="formulario" />
            </td>
        </tr>
                   
       
    </table>
   
   
      
</body>
</html>