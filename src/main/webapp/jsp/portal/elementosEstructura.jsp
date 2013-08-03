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
    var _ACTION_BUSCAR_ELEMENTO_ESTRUCTURA = "<s:url action='buscarElementosEstructura' namespace='/estructuras'/>";
    var _ACTION_GET_ELEMENTO_ESTRUCTURA = "<s:url action='getElementoEstructura' namespace='/estructuras'/>";
    var _ACTION_GUARDAR_NUEVO_ELEMENTO_ESTRUCTURA = "<s:url action='guardarNuevoElementoEstructura' namespace='/estructuras'/>";
    var _ACTION_GUARDAR_ELEMENTO_ESTRUCTURA = "<s:url action='guardarElementoEstructura' namespace='/estructuras'/>";
    var _ACTION_BORRAR_ELEMENTO_ESTRUCTURA = "<s:url action='borrarElementoEstructura' namespace='/estructuras'/>";
    var _ACTION_EXPORTAR_ELEMENTO_ESTRUCTURA = "<s:url action='exportarElementoEstructura' namespace='/estructuras'/>";
    var _ACTION_COPIAR_ELEMENTO_ESTRUCTURA = "<s:url action='copiarElementoEstructura' namespace='/estructuras'/>";
 	var _ACTION_REGRESAR_A_ESTRUCTURA = "<s:url action='regresarEstructura' namespace='/estructuras'/>";

// Combos
    //url de los combos
    var _ACTION_OBTENER_CLIENTES = "<s:url action='obtenerClientes' namespace='/combos'/>";
    var _ACTION_TIPOS_NIVEL = "<s:url action='obtenerTiposNivel' namespace='/combos'/>";
    var _ACTION_VINCULOS_PADRE = "<s:url action='comboVinculosPadrePorEstructura' namespace='/combos'/>";
    var _ACTION_VINCULOS_PADRE_EDITAR = "<s:url action='obtenerVinculoPadreEditar' namespace='/combos'/>";



//Seteo de valores de la estructura obtenidos
    var CODIGO_ESTRUCTURA = "<s:property value='codigo'/>";
	var DESCRIPCION_ESTRUCTURA = "<s:property value='descripcionEscapedJavaScript'/>";
   //  var itemsPerPage=10;
   
     var itemsPerPage = _NUMROWS;
     
    var helpMap = new Map();

<%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("7")%>

_URL_AYUDA = "/catweb/confElementosEstructuras.html"; 

</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/estructuras/agregarElementoEstructura.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/estructuras/editarEementoEstructura.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/estructuras/elementosEstructuras.js"></script>


</head>
<body>

   <table>
        <tr valign="top">
            <td>
                <div id="formBusqueda" />
            </td>
        </tr>
       <tr valign="top">
           <td>
               <div id="gridElementos" />
           </td>
       </tr>
    </table>
</body>
</html>