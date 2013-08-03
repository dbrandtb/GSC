<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Motivos de Cancelacion</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_MOTIVOS_CANCELACION = "<s:url action='buscarMotivosCancelacion' namespace='/motivosCancelacion'/>";
    var _ACTION_BORRAR_MOTIVOS_CANCELACION = "<s:url action='borrarMotivosCancelacion' namespace='/motivosCancelacion'/>";
    var _ACTION_IR_AGREGAR_MOTIVOS_CANCELACION = "<s:url action='irConfigurarMotivosCancelacion' namespace='/motivosCancelacion'/>";
    var _ACTION_EXPORTAR_MOTIVOS_CANCELACION = "<s:url action='exportarMotivosCancelacion' namespace='/motivosCancelacion'/>";
    
     var itemsPerPage=_NUMROWS;
    var helpMap = new Map();
    
//Seteo de valores usados en la búsqueda
	var FILTRO_CODIGO_RAZON = "<s:property value='codRazon'/>";
    var DESCRIPCION_RAZON = "<s:property value='dsRazon'/>";

    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("24")%>
 
     _URL_AYUDA = "/catweb/motivosCancelacion.html";
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/motivosCancelacion/motivosCancelacion.js"></script>
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