<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Periodos de Gracia por Cliente</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_PERIODOS_GRACIA_CLIENTE = "<s:url action='buscarPeriodosGraciaCliente' namespace='/periodosGraciaCliente'/>";
    var _ACTION_EXPORT_PERIODOS_GRACIA_CLIENTE = "<s:url action='exportarPeriodosGraciaCliente' namespace='/periodosGraciaCliente'/>";
    var _ACTION_BORRAR_PERIODOS_GRACIA_CLIENTE = "<s:url action='borrarPeriodosGraciaCliente' namespace='/periodosGraciaCliente'/>";
    var _ACTION_INSERTAR_PERIODOS_GRACIA_CLIENTE = "<s:url action='insertarPeriodosGraciaCliente' namespace='/periodosGraciaCliente'/>";
   
    var _ACTION_OBTENER_CLIENTE_CORPO = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
    var _ACTION_OBTENER_ASEGURADORAS_CLIENTE = "<s:url action='obtenerAseguradorasCliente' namespace='/combos'/>";
    var _ACTION_OBTENER_PRODUCTOS_ASEGURADORA_CLIENTE = "<s:url action='obtenerProductosAseguradoraCliente' namespace='/combos'/>";
    var _ACTION_PERIODOS_GRACIA_OBTENER_PERIODOS_GRACIA = "<s:url action='obtenerPeriodosGracia' namespace='/combos'/>";
    

	
	
    var helpMap = new Map();
    // var itemsPerPage=20;
    var itemsPerPage= _NUMROWS;
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("28")%>
    
    _URL_AYUDA = "/catweb/periodoGraciaCliente.html";
    
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/periodosGraciaCliente/periodosGraciaCliente.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/periodosGraciaCliente/agregarPeriodoGraciaCliente.js"></script>
 
 
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
               <div id="gridPeriodosGraciaCliente" />
           </td>
       </tr>
    </table>
</body>
</html>