<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pagina Principal Carrito de Compras</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_OBTENER_PRODUCTOS_CARRITO_COMPRAS = "<s:url action='obtenerProductosCarritoCompras' namespace='/carritoCompras'/>";
    var _ACTION_OBTENER_MONTOS_CARRITO_COMPRAS = "<s:url action='calculaDescuento' namespace='/carritoCompras'/>";
    var _ACTION_OBTENER_ROLES_CARRITO_COMPRAS = "<s:url action='obtenerRolesCarritoCompras' namespace='/carritoCompras'/>";
    var _ACTION_OBTENER_ENC_ORDEN = "<s:url action='obtenerEncOrdenCarritoCompras' namespace='/carritoCompras'/>";
    var _ACTION_BORRAR_REG_CARRITO_COMPRAS = "<s:url action='borrarRegCarritoCompras' namespace='/carritoCompras'/>";
    
    
    var _ACTION_ENVIAR_CORREO= "<s:url action='enviaMail' namespace='/mail'/>";
   
   <!-- Combos utilizados -->
   var _ACTION_OBTENER_PERSONAS= "<s:url action='comboPersonas' namespace='/combos'/>";
   

    var itemsPerPage=10;
	var helpMap = new Map();
    //<%=session.getAttribute("helpMap")%>
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/carritoCompras/carritoComprasConsulta.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/carritoCompras/enviarCorreoCarritoCompras.js"></script>


</head>
<body>

   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formDatosPersonas" />
            </td>
        </tr>
       <tr valign="top">
           <td class="headlines" colspan="2">
               <div id="gridProductos" />
           </td>
       </tr>
    </table>
</body>
</html>