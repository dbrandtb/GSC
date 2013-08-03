<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Configurar Elementos de Estructura</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_SECCIONES = "<s:url action='buscarSecciones' namespace='/secciones'/>";
    var _ACTION_GET_SECCION = "<s:url action='getSeccion' namespace='/secciones'/>";
 	var _ACTION_AGREGAR_NUEVA_SECCION = "<s:url action='guardarNuevaSeccion' namespace='/secciones'/>";
    var _ACTION_GUARDAR_SECCION = "<s:url action='guardarSeccion' namespace='/secciones'/>";
    var _ACTION_EXPORTAR_SECCIONES = "<s:url action='exportarBusquedaSecciones' namespace='/secciones'/>";
    var _BORRAR_SECCION = "<s:url action='borrarSeccion' namespace='/secciones'/>";

    var _ACTION_OBTENER_BLOQUES = "<s:url action='comboBloques' namespace='/combos'/>";
    
   
    var helpMap=new Map();
    var itemsPerPage=_NUMROWS;
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("22")%>    
    
    _URL_AYUDA = "/catweb/configuracionSecciones.html";

</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/portal/secciones/secciones.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/secciones/editarSeccion.js"></script>
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
               <div id="gridSecciones" />
           </td>
       </tr>
    </table>
</body>
</html>