<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Descuentos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>  
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script> 
<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_COMBO_TIPO_DSCTO = "<s:url action='obtenerTiposDscto' namespace='/combos'/>";
    var _ACTION_COMBO_CLIENTES_CORPO = "<s:url action='obtenerClientesCarrito' namespace='/combos'/>";
    var _ACTION_COMBO_ACUMULA = "<s:url action='obtenerEstadosDescuentos' namespace='/combos'/>";
    var _ACTION_COMBO_ESTADO = "<s:url action='obtenerEstados' namespace='/combos'/>";
    var _ACTION_COMBO_ASEGURADORAS = "<s:url action='obtenerAseguradorasCliente' namespace='/combos'/>";
    var _ACTION_COMBO_PRODUCTOS = "<s:url action='obtenerProductosDescuentos' namespace='/combos'/>";
    var _ACTION_COMBO_TIPO_SITUACION = "<s:url action='obtenerComboTipoSituacionProductos' namespace='/combos'/>";            
    var _ACTION_COMBO_PLANES = "<s:url action='obtenerPlanes' namespace='/combos'/>";
    var _ACTION_OBTIENE_ENCABEZADO_PRODUCTO = "<s:url action='getEncabezadoProducto' namespace='/descuentos'/>";
    var _ACTION_OBTIENE_DETALLE_PRODUCTO = "<s:url action='getDetalleProducto' namespace='/descuentos'/>";
    var _ACTION_OBTIENE_ENCABEZADO_VOLUMEN = "<s:url action='getEncabezadoVolumen' namespace='/descuentos'/>";
    var _ACTION_OBTIENE_DETALLE_VOLUMEN = "<s:url action='getDetalleVolumen' namespace='/descuentos'/>";
	var _ACTION_GUARDAR_PRODUCTO = "<s:url action='guardarProducto' namespace='/descuentos'/>";
	var _ACTION_AGREGAR_PRODUCTO_DETALLE = "<s:url action='guardarProductoDetalle' namespace='/descuentos'/>";
	var _ACTION_GUARDAR_VOLUMEN = "<s:url action='guardarVolumen' namespace='/descuentos'/>";
	var _ACTION_AGREGAR_VOLUMEN_DETALLE = "<s:url action='guardarVolumenDetalle' namespace='/descuentos'/>";
	var _ACTION_BORRAR_PRODUCTO_DETALLE = "<s:url action='borrarProductoDetalle' namespace='/descuentos'/>";
	var _ACTION_BORRAR_VOLUMEN_DETALLE = "<s:url action='borrarVolumenDetalle' namespace='/descuentos'/>";
	var _ACTION_REGRESAR_A_DESCUENTOS = "<s:url action='irDescuentos' namespace='/descuentos'/>";

	var CODIGO_DESCUENTO = "<s:property value='cdDscto'/>";
	var DESCRIPCION_DESCUENTO = "<s:property value='dsNombre'/>";
	var CODIGO_TIPO_DESCUENTO = "<s:property value='cdTipo'/>";
	
  var itemsPerPage=10;
  var helpMap=new Map();
  <%=session.getAttribute("helpMap")%>
  <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("512")%>      
</script>
	
 
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>

<jsp:include page="/resources/scripts/util/configuradorNumDecimal.jsp" flush="true"></jsp:include>

<script type="text/javascript" src="${ctx}/resources/scripts/portal/descuentos/descuentosEditar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/descuentos/descuentosAgregar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/descuentos/descuentosConfigurar.js"></script>


</head>
<body>
   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formDescuentos" />
            </td>
        </tr>
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="txtFieldsProductos" />
            </td>
        </tr>
       <tr valign="top">
           <td class="headlines" colspan="2">
               <div id="gridDescuentosDetalles" />
           </td>
       </tr>
    </table>
</body>
</html>