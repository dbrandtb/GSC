<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Configurar Elementos de Estructura</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_PAPELES = "<s:url action='buscarPapeles' namespace='/agrupacionPapel'/>";
    var _ACTION_GET_AGRUPACIONES = "<s:url action='getAgrupacionPapel' namespace='/agrupacionPapel'/>";
 	var _ACTION_GUARDAR_DATOS_GRILLA = "<s:url action='guardarAgrupacionPapel' namespace='/agrupacionPapel'/>";
 	var _ACTION_IR_AGRUPACION_POLIZAS = "<s:url action='irAgrupacionPolizas' namespace='/agrupacionPapel'/>";
 	var _ACTION_BORRAR_ROL = "<s:url action='borrarRol' namespace='/agrupacionPapel'/>";
 	var _ACTION_IR_ASIGNAR_POLIZAS = "<s:url action='irAsignarPolizas' namespace='/agrupacionPapel'/>";
 	var _ACTION_ACTUALIZAR_DATOS_GRILLA = "<s:url action='actualizarAgrupacionPapel' namespace='/agrupacionPapel'/>";

 	
// Combos url de los combos
    var _ACTION_OBTENER_PAPELES = "<s:url action='comboAgrupacionPapelesPapel' namespace='/combos'/>";
    var _ACTION_OBTENER_ASEGURADORA = "<s:url action='comboAseguradorasPorCliente' namespace='/combos'/>";
    var _ACTION_OBTENER_PRODUCTOS = "<s:url action='comboProductosPorAseguradorYCliente' namespace='/combos'/>";    

	var CODIGO_CONFIGURACION = "<s:property value='codigoConfiguracion'/>";
	var CODIGO_POLMTRA = "<s:property value='cdPolMtra'/>";
	var CODIGO_AGRUPACION = "<s:property value='codigoAgrupacion'/>";

    var itemsPerPage=_NUMROWS;
    
    /* <%=session.getAttribute("helpMap")%>*/
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("18")%>
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">
	var helpMap = new Map();
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/agrupacionPapel/agrupacionPapel.js"></script>
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

