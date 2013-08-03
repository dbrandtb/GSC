<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Asociar Formatos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_ASOCIAR_FORMATOS = "<s:url action='buscarAsociarFormatos' namespace='/asociarFormatos'/>";
    var _ACTION_BORRAR_ASOCIAR_FORMATOS = "<s:url action='borrarAsociarFormatos' namespace='/asociarFormatos'/>";
    var _ACTION_EXPORTAR_ASOCIAR_FORMATOS = "<s:url action='exportarAsociarFormatos' namespace='/asociarFormatos'/>";
    var _ACTION_IR_ASOCIAR_FORMATOS_ORDENES_TRABAJO = "<s:url action='irAsociarFormatosOrdenesTrabajo' namespace='/asociarFormatos'/>";
    
    //Lista de combos
    _ACTION_OBTENER_PROCESOS = "<s:url action='obtenerProcesos' namespace='/combos'/>";
    _ACTION_OBTENER_FORMATOS = "<s:url action='obtenerFormatos' namespace='/combos'/>";
    _ACTION_OBTENER_CLIENTES_CORPORATIVOS = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
    _ACTION_OBTENER_ASEGURADORAS = "<s:url action='obtenerAseguradorasCliente' namespace='/combos'/>";
    _ACTION_OBTENER_PRODUCTOS = "<s:url action='comboProductosPorAseguradorYCliente' namespace='/combos'/>";
    _ACTION_OBTENER_FORMA_CALCULO_FOLIOS = "<s:url action='comboObtenerFormasCalculoFolio' namespace='/combos-catbo'/>";
    //variables
    var _ACTION_OBTENER_FORMATO_X_CUENTA = "<s:url action='obtenerFormatoxCuenta' namespace='/asociarFormatos'/>";
    var _ACTION_GUARDAR_ASOCIAR_ORDEN_TRABAJO = "<s:url action='guardarAsociarOrdenTrabajo' namespace='/asociarFormatos'/>";
    
	//EXPRESIONES
	var ACTION_TABLA_EXPRESIONES ="<s:url namespace='expresiones' action='ComboTabla' includeParams='none'/>";
	var ACTION_COLUMNA_EXPRESIONES = "<s:url namespace='expresiones' action='ComboColumna' includeParams='none'/>";
	var ACTION_CLAVE_EXPRESIONES = "<s:url namespace='expresiones' action='ComboClave' includeParams='none'/>";

   	var helpMap = new Map();		
    var itemsPerPage=10;
    <%=session.getAttribute("helpMap")%>
    
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("29")%>    
    
</script>
<!-- <script type="text/javascript">var helpMap = new Map();</script> -->
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<!-- Para usar lo de Expresiones:  -->
<script type="text/javascript" src="${ctx}/resources/scripts/utilities/checkColumnPlugin.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/productos/tablaApoyo5Claves/tablaApoyo5Claves.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/expresiones/expresiones.js"></script>

<!--
<script type="text/javascript" src="${ctx}/resources/scripts/portal/asociarFormatos/agregarAsociarFormatos.js"></script>
-->
<script type="text/javascript" src="${ctx}/resources/scripts/portal/asociarFormatos/editarAsociarFormatos.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/asociarFormatos/asociarFormatos.js"></script>
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