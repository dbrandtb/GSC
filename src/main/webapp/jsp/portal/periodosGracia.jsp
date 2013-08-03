<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Periodos de Gracia</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_PERIODOS_GRACIA   = "<s:url action='buscarPeriodosGracia' namespace='/periodosGracia'/>";
	var _ACTION_EXPORTAR_PERIODOS_GRACIA = "<s:url action='getModel' namespace='/periodosGracia'/>";    
    var _ACTION_BORRAR_PERIODOS_GRACIA   = "<s:url action='borrarPeriodosGracia' namespace='/periodosGracia'/>";

    var _ACTION_OBTENER_PERIODOS_GRACIA   = "<s:url action='getPeriodosGracia' namespace='/periodosGracia'/>";
    var _ACTION_AGREGAR_GUARDAR_PERIODOS_GRACIA   = "<s:url action='agregarGuardarPeriodosGracia' namespace='/periodosGracia'/>";    
    var itemsPerPage=_NUMROWS;
 	
     var helpMap = new Map();
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("27")%>

	_URL_AYUDA = "/catweb/periodoGracia.html";

</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/periodosGracia/periodosGracia.js"></script>


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
               <div id="gridPeriodosGracia" />
           </td>
       </tr>
    </table>
</body>
</html>