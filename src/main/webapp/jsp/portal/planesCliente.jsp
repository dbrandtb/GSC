<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Mantenimiento Planes Por Cliente</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";

    var _ACTION_BUSCAR_PLANCLIENTE = "<s:url action='buscarPlanesCliente' namespace='/planesCliente'/>";
    var _ACTION_GUARDAR_NUEVO_PLANCLIENTE = "<s:url action='guardarNuevoPlanCliente' namespace='/planesCliente'/>";
    var _ACTION_GET_PLANCLIENTE = "<s:url action='getPlanCliente' namespace='/planesCliente'/>";
    var _ACTION_GUARDAR_PLANCLIENTE = "<s:url action='guardarPlanCliente' namespace='/planesCliente'/>";
    var _ACTION_BORRAR_PLANCLIENTE = "<s:url action='borrarPlanCliente' namespace='/planesCliente'/>";
    var _ACTION_PLANESCLIENTE_EXPORT = "<s:url action='exportarBusquedaPlanesCliente' namespace='/planesCliente'/>";
    var _REGRESAR_BUSCAR_PLANES = "<s:url action='irBuscarPlanes' namespace='/detallePlanes'/>";
    
    //url de los combos
    //var _ACTION_OBTENER_PRODUCTOS = "<s:url action='obtenerProductosAyuda' namespace='/combos'/>";
    var _ACTION_OBTENER_PRODUCTOS_X_PLAN = "<s:url action='obtenerProductosPlan' namespace='/combos'/>";
    //var _ACTION_OBTENER_PLANES_PRODUCTO = "<s:url action='obtenerPlanesProducto' namespace='/combos'/>";
    var _ACTION_OBTENER_PLANES_PRODUCTO = "<s:url action='obtenerPlanesProducto' namespace='/combos'/>";
    //var _ACTION_OBTENER_TIPOS_SITUACION = "<s:url action='obtenerSituacionPorProductoYPlan' namespace='/combos'/>";
    var _ACTION_OBTENER_SITUACION_X_PLAN = "<s:url action='obtenerSituacionPlan' namespace='/combos'/>";
    //var _ACTION_OBTENER_TIPOS_COBERTURAS = "<s:url action='obtenerGarantiaPorProductoYSituacion' namespace='/combos'/>";
    var _ACTION_OBTENER_COBERTURAS_X_PLAN = "<s:url action='obtenerCoberturaPlan' namespace='/combos'/>";
    var _ACTION_OBTENER_ASEGURADORAS = "<s:url action='obtenerAseguradorasPorProducto' namespace='/combos'/>";
    var _ACTION_OBTENER_CLIENTES_CORP = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";

    //Seteo de valores del Plan obtenido
    var CODIGO_PRODUCTO = "<s:property value='codigoProducto'/>";
    var CODIGO_PLAN = "<s:property value='codigoPlan'/>";
    var DESCRIPCION_PRODUCTO = "<s:property value='descripcionProducto'/>";
    var DESCRIPCION_PLAN = "<s:property value='descripcionPlan'/>";

	var helpMap = new Map();
    var itemsPerPage=20;

    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("8")%>    

</script>

<script type="text/javascript" src="${ctx}/resources/scripts/portal/mantenimientoPlanes/detallePlanCliente.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/mantenimientoPlanes/agregarDetallePlanCliente.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/mantenimientoPlanes/editarDetallePlanCliente.js"></script>


</head>
<body>

   <table>
        <tr>
            <td>
                <div id="formBusqueda"/>
            </td>
        </tr>
       <tr>
           <td>
               <div id="gridElementos" />
           </td>
       </tr>
      
    </table>
</body>
</html>