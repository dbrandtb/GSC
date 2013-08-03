<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Catalogos Logicos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script language="javascript">

    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_REGISTROS = "<s:url action='buscarRegistros' namespace='/catalogosLogicos'/>";
    var _ACTION_OBTENER_REGISTRO = "<s:url action='obtenerRegistro' namespace='/catalogosLogicos'/>";
    var _ACTION_GUARDAR_REGISTROS = "<s:url action='guardarRegistro' namespace='/catalogosLogicos'/>";
    var _ACTION_BORRAR_REGISTRO = "<s:url action='borrarRegistro' namespace='/catalogosLogicos'/>";
    var _ACTION_EXPORT = "<s:url action='exportarRegistros' namespace='/catalogosLogicos'/>";

	var _ACTION_COMBO_REGION = "<s:url action='confAlertasAutoRegion' namespace='/combos'/>";
	var _ACTION_COMBO_IDIOMA = "<s:url action='obtenerComboIdiomas' namespace='/combos'/>";

    var helpMap= new Map();
    var itemsPerPage= 20;
    var helpMap = new Map();

    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("48")%>    
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/catalogosLogicos/agregarCatalogosLogicos.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/catalogosLogicos/editarCatalogosLogicos.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/catalogosLogicos/catalogosLogicos.js"></script>

</head>
<body>

   <table cellspacing="1">
        <tr valign="top">
            <td>
                <div id="formBusqueda"></div>
            </td>
        </tr>
       <tr valign="top">
           <td>
               <div id="gridElementos"></div>
           </td>
       </tr>
    </table>
</body>
</html>