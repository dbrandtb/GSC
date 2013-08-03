<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Catalogos Logicos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_OBTENER_ENCABEZADO = "<s:url action='obtenerDatosPoliza' namespace='/cancelacionManualPolizas'/>";
    var _ACTION_OBTENER_DATOS_CANCELACION = "<s:url action='obtenerDatosCancelacion' namespace='/cancelacionManualPolizas'/>";
    var _ACTION_OBTENER_INCISOS = "<s:url action='obtenerDatosIncisos' namespace='/cancelacionManualPolizas'/>";
    var _ACTION_GUARDAR_DATOS = "<s:url action='guardarDatos' namespace='/cancelacionManualPolizas'/>";

    var _ACTION_CALCULAR_PRIMA = "<s:url action='calcularPrima' namespace='/cancelacionManualPolizas'/>";
    var _ACTION_EXPORT = "<s:url action='exportarRegistros' namespace='/cancelacionManualPolizas'/>"; 
    var _ACTION_REGRESAR_CONSULTA_POLIZAS = "<s:url action='busquedaPoliza' namespace='/procesoemision'/>"; 

	var _ACTION_COMBO_RAZON_CANCELACION = "<s:url action='comboRazones' namespace='/combos'/>";
	var _ACTION_COMBO_IDIOMA = "<s:url action='obtenerIdiomas' namespace='/combos'/>";
	var _ACTION_COMBO_POLIZAS = "<s:url action='obtenerPolizasCancManual' namespace='/combos'/>";
	var _ACTION_COMBO_PRODUCTOS = "<s:url action='obtenerProductosPorAseguradora' namespace='/combos'/>";
	var _ACTION_COMBO_ASEGURADORAS = "<s:url action='obtenerPolizasCancManualAeguradoras' namespace='/combos'/>";

    var _ACTION_COMBO_RAZON_CANCELACION_PRODUCTO = "<s:url action='comboRazonesPorProducto' namespace='/combos'/>";

	//var CODIGO_ASEGURADORA = "<s:property value='cdUniEco'/>";
	//var CODIGO_PRODUCTO = "<s:property value='cdRamo'/>";
	//var CODIGO_ESTADO = "<s:property value='cdEstado'/>";
	//var NUMERO_POLIZA = "<s:property value='nmPoliza'/>";
	
	var NOMBRE_ASEGURADO = "<s:property value='nombreAsegurado'/>";
	var CODIGO_ASEGURADORA = "<s:property value='cdUnieco'/>";
	var DESCRIPCION_ASEGURADORA = "<s:property value='dsUnieco'/>";
	var CODIGO_PRODUCTO = "<s:property value='cdRamo'/>";
	var DESCRIPCION_PRODUCTO = "<s:property value='dsRamo'/>";
	var NUMERO_POLIZA = "<s:property value='nmPoliza'/>";
	var POLIZA_EXTERNA = "<s:property value='poliza'/>";
	var FECHA_EFECTO = "<s:property value='feEfecto'/>";
	var FECHA_VENCIMIENTO = "<s:property value='feVencim'/>";
	var CODIGO_ESTADO = "<s:property value='cdEstado'/>";	
	var CODIGO_UNIAGE = "<s:property value='cdUniAge'/>";	
	
	
	var helpMap = new Map();

    var itemsPerPage=10;
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("200")%>
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/confirmaPassword.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/cancelacionManualPolizas/cancelacionManualPolizas.js"></script>

</head>
<body>

   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formBusqueda"></div>
            </td>
        </tr>
       <tr valign="top">
           <td class="headlines" colspan="2">
               <div id="gridElementos"></div>
           </td>
       </tr>
    </table>
</body>
</html>