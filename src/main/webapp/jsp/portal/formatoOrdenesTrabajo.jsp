<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Configurar Elementos de Estructura</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_FORMATO_ORDENES_TRABAJO = "<s:url action='buscarFormatoOrdenesTrabajo' namespace='/formatoOrdenesTrabajo'/>";
    var _ACTION_BORRAR_FORMATO_ORDENES_TRABAJO = "<s:url action='borrarFormatoOrdenesTrabajo' namespace='/formatoOrdenesTrabajo'/>";
    var _ACTION_INSERTAR_FORMATO_ORDENES_TRABAJO = "<s:url action='agregarFormatoOrdenesTrabajo' namespace='/formatoOrdenesTrabajo'/>";
    var _ACTION_GUARDAR_FORMATO_ORDENES_TRABAJO = "<s:url action='guardarFormatoOrdenesTrabajo' namespace='/formatoOrdenesTrabajo'/>";
    var _ACTION_GET_FORMATO_ORDENES_TRABAJO = "<s:url action='obtenerFormatoOrdenesTrabajo' namespace='/formatoOrdenesTrabajo'/>";
    var _ACTION_COPIAR_FORMATO_ORDENES_TRABAJO = "<s:url action='copiarFormatoOrdenesTrabajo' namespace='/formatoOrdenesTrabajo'/>";
    var _ACTION_IR_CONFIGURAR_ORDENES_TRABAJO = "<s:url action='irConfigurarOrdenesTrabajo' namespace='/formatoOrdenesTrabajo'/>";

    var helpMap = new Map();
    
    var itemsPerPage=_NUMROWS;
	<%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("23")%>
       
    _URL_AYUDA = "/catweb/formatoOrdenTrabajo.html"; 
	
</script>



<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>

<script type="text/javascript" src="${ctx}/resources/scripts/portal/formatoOrdenesTrabajo/formatoOrdenesTrabajo.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/formatoOrdenesTrabajo/editarFormatoOrdenesTrabajo.js"></script>

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