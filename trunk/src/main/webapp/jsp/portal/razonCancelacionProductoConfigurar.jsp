<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Configurar Razones de Cancelacion de Productos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_OBTENER_PRODUCTOS = "<s:url action='obtenerProductosAyuda' namespace='/combos'/>";
    var _ACTION_OBTENER_RAZONES_CANCELACION_PRODUCTO = "<s:url action='comboRazones' namespace='/combos'/>";
    var _ACTION_OBTENER_METODOS_CANCELACION_PRODUCTO = "<s:url action='comboMetodos' namespace='/combos'/>";
	var _ACTION_GUARDAR_RAZON_CANCELACION_PRODUCTO = "<s:url action='guardarRazonCancelacionProducto' namespace='/razonesCancelacionProducto'/>";
	var _ACTION_REGRESAR_RAZONES_CANCELACION_PRODUCTO = "<s:url action='irRazonesCancelacionProducto' namespace='/razonesCancelacionProducto'/>";
	
    var itemsPerPage=10;
    var helpMap=new Map();
  <%=session.getAttribute("helpMap")%>
        
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/razonesCancelacionProducto/razonesCancelacionProductoConfigurar.js"></script>

</head>
<body>

   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formCombosRCP" />
            </td>
        </tr>
    </table>
</body>
</html>