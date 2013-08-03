<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Mantenimiento de Estructuras</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_ESTRUCTURAS = "<s:url action='buscarEstructuras' namespace='/estructuras'/>";
    var _ACTION_GET_ESTRUCTURA = "<s:url action='getEstructura' namespace='/estructuras'/>";
    var _ACTION_GUARDAR_NUEVO_ESTRUCTURA = "<s:url action='guardarNuevaEstructura' namespace='/estructuras'/>";
    var _ACTION_GUARDAR_ESTRUCTURA = "<s:url action='guardarEstructura' namespace='/estructuras'/>";
    var _ACTION_BORRAR_ESTRUCTURA = "<s:url action='borrarEstructura' namespace='/estructuras'/>";
    var _ACTION_COPIAR_ESTRUCTURA = "<s:url action='copiarEstructura' namespace='/estructuras'/>";
    var _ACTION_IR_CONFIGURAR_ESTRUCTURA = "<s:url action='irConfigurarEstructura' namespace='/estructuras'/>";
    var _ACTION_EXPORT = "<s:url action='exportarBusquedaEstructuras' namespace='/estructuras'/>";
    
    var itemsPerPage = _NUMROWS;

    var _ACTION_COMBO_REGION = "<s:url action='confAlertasAutoRegion' namespace='/combos'/>";
	var _ACTION_COMBO_IDIOMAS_REGION = "<s:url action='confAlertasAutoIdiomasRegion' namespace='/combos'/>";
	
	var CODIGO_ESTRUCTURA = "<s:property value='codigoEstructura'/>";
	
    var helpMap = new Map();
<%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("7")%>


	_URL_AYUDA = "/catweb/estructuras.html";
	
</script>


<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/estructuras/editarEstructura.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/estructuras/agregarEstructura.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/estructuras/estructuras.js"></script>


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
               <div id="gridEstructuras" />
           </td>
       </tr>
    </table>
</body>
</html>