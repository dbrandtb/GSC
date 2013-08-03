<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Mantenimiento Planes Por Cliente</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_PLANES = "<s:url action='listarDetallePlanes' namespace='/detallePlanes'/>";
    var _ACTION_GUARDAR_NUEVO_DETALLEPLAN = "<s:url action='agregarNuevoDetallePlan' namespace='/detallePlanes'/>";
    var _ACTION_GET_DETALLEPLAN = "<s:url action='getDetallePlan' namespace='/detallePlanes'/>";
    var _ACTION_GUARDAR_DETALLEPLAN = "<s:url action='editarDetallePlan' namespace='/detallePlanes'/>";
    var _ACTION_BORRAR_DETALLEPLAN = "<s:url action='borrarDetallePlan' namespace='/detallePlanes'/>";
    var _ACTION_CONFIGURAR_PLAN_CLIENTE = "<s:url action='irBuscarPlanesCliente' namespace='/planesCliente'/>";
    var _REGRESAR_MANTENIMIENTO_PLAN ="<s:url namespace='/planes' action='manttoPlanes.action'/>";
    var _ACTION_EXPORT = "<s:url action='exportarBusquedaPlanes' namespace='/detallePlanes'/>";

    //url de los combos
    var _ACTION_OBTENER_TIPOS_SITUACION = "<s:url action='obtenerTiposSituacion' namespace='/combos'/>";
    var _ACTION_OBTENER_TIPOS_COBERTURAS = "<s:url action='obtenerTiposCobertura' namespace='/combos'/>";

    //Seteo de valores del Plan obtenido
    var CODIGO_PRODUCTO = "<s:property value='codigoProducto'/>";
    var CODIGO_PLAN = "<s:property value='codigoPlan'/>";
    var DESCRIPCION_PRODUCTO = "<s:property value='descripcionProducto'/>";
    var DESCRIPCION_PLAN = "<s:property value='descripcionPlan'/>";
    var itemsPerPage=10;
    
     /*<%=session.getAttribute("helpMap")%>*/

</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/planes/editarDetallePlan.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/planes/agregarDetallePlan.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/planes/detallePlanes.js"></script>

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