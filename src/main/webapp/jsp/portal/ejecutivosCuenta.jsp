<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Mantenimiento de Ejecutivos por Cuenta</title>
<meta http-equiv="Content-Type" content = "text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_EJECUTIVOS_CUENTA = "<s:url action='buscarEjecutivosCuenta' namespace='/ejecutivosCuenta'/>";
    var _ACTION_EXPORTAR_EJECUTIVOS_CUENTA = "<s:url action='exportarEjecutivosCuenta' namespace='/ejecutivosCuenta'/>";
    var _ACTION_GET_EJECUTIVOS_CUENTA = "<s:url action='getEjecutivosCuenta' namespace='/ejecutivosCuenta'/>";
    var _ACTION_INSERTAR_GUARDAR_EJECUTIVOS_CUENTA = "<s:url action='guardarEjecutivosCuenta' namespace='/ejecutivosCuenta'/>";
    var _ACTION_BORRAR_EJECUTIVOS_CUENTA = "<s:url action='borrarEjecutivosCuenta' namespace='/ejecutivosCuenta'/>";
    
     
    var _ACTION_OBTENER_CLIENTES_CORPORATIVO = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
	var _ACTION_OBTENER_LINEAS_ATENCION = "<s:url action='obtenerLineasAtencion' namespace='/combos'/>";
	var _ACTION_OBTENER_GRUPOS_PERSONA = "<s:url action='obtenerGrupoPersonas' namespace='/combos'/>";
	var _ACTION_OBTENER_TIPO_RAMO = "<s:url action='obtenerTiposRamoClienteEjecutivoCuenta' namespace='/combos'/>";
	var _ACTION_OBTENER_PRODUCTOS = "<s:url action='obtenerProductosEjecutivosCuenta' namespace='/combos'/>";
	var _ACTION_OBTENER_EJECUTIVOS = "<s:url action='obtenerEjecutivosAon' namespace='/combos'/>";
	var _ACTION_OBTENER_TIPO_EJECUTIVOS = "<s:url action='obtenerTipoEjecutivosAon' namespace='/combos'/>";
	var _ACTION_OBTENER_ESTADO_EJECUTIVOS = "<s:url action='obtenerEstadosEjecutivo' namespace='/combos'/>";
	
	var helpMap = new Map();
    var itemsPerPage=_NUMROWS;
    
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("17")%>
    
    _URL_AYUDA = "/catweb/ejecutivosPorCuenta.html";
</script>
 
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script> 
<script type="text/javascript" src="${ctx}/resources/scripts/portal/ejecutivosCuenta/editarEjecutivosCuenta.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/ejecutivosCuenta/agregarEjecutivosCuenta.js"></script> 
<script type="text/javascript" src="${ctx}/resources/scripts/portal/ejecutivosCuenta/ejecutivosCuenta.js"></script>

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
               <div id="gridEjecutivosCuenta" />
           </td>
       </tr>
    </table>
</body>
</html>