<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Mantenimiento de mensajes de error</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var ACTION_BUSCAR_MENSAJES = "<s:url action='buscarMensajesError' namespace='/mensajesError'/>";
    var ACTION_EXPORTAR_MENSAJES = "<s:url action='exportarMensajesError' namespace='/mensajesError'/>";
	var _ACTION_GUARDAR_MENSAJE_ERROR = "<s:url action='guardarMensajeError' namespace='/mensajesError'/>";
	var _ACTION_GET_MENSAJE_ERROR = "<s:url action='getMensajeError' namespace='/mensajesError'/>";
// Combos url de los combos
    var _ACTION_OBTENER_TIPO_ERROR = "<s:url action='comboTipoError' namespace='/combos'/>";
      var helpMap=new Map();
      var itemsPerPage= _NUMROWS;
    // var itemsPerPage=10;
  
    <%=session.getAttribute("helpMap")%>
 <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("31")%>
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/mantenimientoErrores/agregarMantenimientoErrores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/mantenimientoErrores/editarMantenimientoErrores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/mantenimientoErrores/mantenimientoErrores.js"></script>
</head>
<body>
   <table class="headlines" cellspacing="10">
       <tr valign="top">
           <td class="headlines">
               <div id="formulario" />
           </td>
       </tr>
       <tr valign="top">
           <td class="headlines">
               <div id="grillaResultados" />
           </td>
       </tr>
    </table>
   <!-- div id="formularioUpd"></div-->
</body>
</html>