<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Configurar Motivos de Cancelacion</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_MOTIVOS_CANCELACION_REQUISITOS = "<s:url action='buscarMotivosCancelacionRequisitos' namespace='/motivosCancelacion'/>";
    var _ACTION_BORRAR_MOTIVOS_CANCELACION_REQUISITOS = "<s:url action='borrarMotivosCancelacionRequisitos' namespace='/motivosCancelacion'/>";
    var _ACTION_INSERTAR_MOTIVOS_CANCELACION = "<s:url action='agregarMotivosCancelacion' namespace='/motivosCancelacion'/>";
    var _ACTION_GUARDAR_MOTIVOS_CANCELACION = "<s:url action='guardarMotivosCancelacion' namespace='/motivosCancelacion'/>";
    var _ACTION_GET_MOTIVOS_CANCELACION = "<s:url action='obtenerMotivosCancelacion' namespace='/motivosCancelacion'/>";
 	var _ACTION_REGRESAR_A_MOTIVOS_CANCELACION = "<s:url action='regresarMotivosCancelacion' namespace='/motivosCancelacion'/>";

// Combos url de los combos
    var _ACTION_OBTENER_REHABILITACION_MOTIVOS_CANCELACION = "<s:url action='obtenerRehabilitacionMotivosCancelacion' namespace='/combos'/>";
    var _ACTION_OBTENER_VERPAGOS_MOTIVOS_CANCELACION = "<s:url action='obtenerVerpagosGrupoMotivosCancelacion' namespace='/combos'/>";

//Seteo de valores de la razon del Motivo
    var CODIGO_RAZON = "<s:property value='cdRazon'/>";

//Seteo de valores usados en la búsqueda
	var FILTRO_CODIGO_RAZON = "<s:property value='codRazon'/>";
    var DESCRIPCION_RAZON = "<s:property value='dsRazon'/>";
	var helpMap = new Map();
    var itemsPerPage=10;
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("801")%>
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/motivosCancelacion/configurarMotivosCancelacion.js"></script>

</head>
<body>

   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formBusqueda" />
            </td>
        </tr>
    </table>
</body>
</html>