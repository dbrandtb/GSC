<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Configuracion de Endosos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_OBTENER_TIPOS_ENDOSOS = "<s:url action='obtenerTiposEndosos' namespace='/configuracionEndosos'/>";
    var _ACTION_EXPORT_TIPOS_ENDOSOS = "<s:url action='exportarTiposEndosos' namespace='/configuracionEndosos'/>"; 
    var _ACTION_BORRAR_TIPO_ENDOSO = "<s:url action='borrarTipoEndoso' namespace='/configuracionEndosos'/>";
	var _ACTION_GUARDAR_TIPO_ENDOSO = "<s:url action='guardarTipoEndoso' namespace='/configuracionEndosos'/>";	
	//var _ACTION_BACK_TO_SOMEWHERE = "<s:url action='' namespace='/'/>";
	
	var _ACTION_OBTENER_INDICADORES_SI_NO = "<s:url action='obtenerIndicadorSINO'   namespace='/combos'/>";
    var _ACTION_OBTENER_P_LISTA_TCATALOG  = "<s:url action='comboIndicadoresSINO'   namespace='/combos'/>";

	var helpMap = new Map();	
    // var itemsPerPage=20;
    var itemsPerPage= _NUMROWS;
    
    <%=session.getAttribute("helpMap")%>
    
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("89")%>
    
    _URL_AYUDA = "/catweb/configurarTiposDeEndosos.html";
    
	
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configuracionEndosos/configuracionEndosos_editar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configuracionEndosos/configuracionEndosos_guardar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configuracionEndosos/configuracionEndosos.js"></script>

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
               <div id="grid" />
           </td>
       </tr>
    </table>
</body>
</html>