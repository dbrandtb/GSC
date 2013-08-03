<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pagina Principal Carrito de Compras</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_OBTENER_PRODUCTOS_CARRITO_COMPRAS = "<s:url action='obtenerProductosCarritoCompras' namespace='/carritoCompras'/>";
    var _ACTION_OBTENER_MONTOS_CARRITO_COMPRAS = "<s:url action='calculaDescuento' namespace='/carritoCompras'/>";
    var _ACTION_OBTENER_ROLES_CARRITO_COMPRAS = "<s:url action='obtenerRolesCarritoCompras' namespace='/carritoCompras'/>";
    var _ACTION_OBTENER_DOMICILIOS = "<s:url action='obtenerDireccionCarritoCompras' namespace='/carritoCompras'/>";
    var _ACTION_GUARDAR_DATOS_DOMICILIO = "<s:url action='guardarDatosDomicilio' namespace='/personas'/>";
    var _ACTION_OBTENER_ENC_ORDEN = "<s:url action='obtenerEncOrdenCarritoCompras' namespace='/carritoCompras'/>";
    var _ACTION_OBTENER_DET_ORDEN = "<s:url action='obtenerDetOrdenCarritoCompras' namespace='/carritoCompras'/>";
    var _ACTION_GUARDAR_CARRITO_COMPRAS = "<s:url action='guardarCarrito' namespace='/carritoCompras'/>";
    var _ACTION_GUARDAR_FORMA_PAGO = "<s:url action='guardarCarritoFormaPago' namespace='/carritoCompras'/>";
    
  	// Combos url de los combos
   	var _ACTION_OBTENER_TIPO_DOMICILIO = "<s:url action='comboTipoDomicilio' namespace='/combos'/>";
	var _ACTION_OBTENER_PAISES = "<s:url action='comboPaises' namespace='/combos'/>";
	var _ACTION_OBTENER_ESTADOS = "<s:url action='comboEstadosPais' namespace='/combos'/>";
	var _ACTION_OBTENER_MUNICIPIOS = "<s:url action='comboMunicipios' namespace='/combos'/>";
	var _ACTION_OBTENER_COLONIAS = "<s:url action='comboColonias' namespace='/combos'/>";
	var _ACTION_OBTENER_INSTRUMENTOS_PAGO = "<s:url action='comboInstrumentosPago' namespace='/combos'/>";
	var _ACTION_OBTENER_TIPOS_TARJETAS = "<s:url action='comboTiposTarjetas' namespace='/combos'/>";
	var _ACTION_OBTENER_BANCOS = "<s:url action='comboBancos' namespace='/combos'/>";
	
	var CODIGO_PERSONA = "<s:property value='codigoPersona'/>";
	var TIPO_PERSONA = "<s:property value='codigoTipoPersona'/>";
	
	var CDCARRO="1";
	var CDUSUARI="RZ";
	var CDPERSON="14038"; 
	var CDCONTRA="90365";
	var CDASEGUR="1"; 		
	var CDCLIENT="100";
	var NMPOLIZA="2";
	var NMSUPLEM="2";    
    var CDTIPSIT="2";
    var FGDSCAPLI="2";
    var CDESTADOD="2";
    var FEINGRES ="";	  
    var CDTIPDOM="1"; 
    var DEBCRED="C";    
	var itemsPerPage=10;
	
    <%=session.getAttribute("helpMap")%>
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/carritoCompras/carritoComprasFinalizarCompra.js"></script>


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