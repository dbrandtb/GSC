<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Consulta de configuracion de renovacion</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>


<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_OBTENER_CLIENTES_TIPO_RENOVACION = "<s:url action='obtenerClientesYTiposDeRenovacion' namespace='/consultaConfiguracionRenovacion'/>";
    var _ACTION_EXPORTAR_CLIENTES_TIPO_RENOVACION = "<s:url action='exportarClientesYTiposDeRenovacion' namespace='/consultaConfiguracionRenovacion'/>"; 
    var _ACTION_BORRAR_CONFIGURACION_RENOVACION = "<s:url action='borrarConfiguracionRenovacion' namespace='/consultaConfiguracionRenovacion'/>";
    var _ACTION_OBTENER_CONFIGURACION_CLIENTE = "<s:url action='obtenerConfiguracionCliente' namespace='/consultaConfiguracionRenovacion'/>";
	var _ACTION_GUARDAR_CONFIGURACION = "<s:url action='guardarConfiguracionCliente' namespace='/consultaConfiguracionRenovacion'/>";
	
	var _ACTION_IR_DETALLE_ROLES_DE_RENOVACION_REPORTE = "<s:url action='irRolesRenovacionReporte' namespace='/consultaConfiguracionRenovacion'/>";
	var _ACTION_VALIDAR_ROL_PARA_IR_A_CONF_ACCION = "<s:url action='validarRolesParaIrAcciones' namespace='/consultaConfiguracionRenovacion'/>";
	var _ACTION_IR_DETALLE_ACCIONES_DE_RENOVACION_REPORTE = "<s:url action='irAccionesRenovacionReporte' namespace='/consultaConfiguracionRenovacion'/>";
	var _ACTION_IR_DETALLE_RANGOS_DE_RENOVACION_REPORTE = "<s:url action='irRangosRenovacionReporte' namespace='/consultaConfiguracionRenovacion'/>";
	
	//VARIABLES PARA COMBOS
	var _ACTION_OBTENER_CLIENTES_CORPO = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
	var _ACTION_OBTENER_ASEGURADORAS = "<s:url action='obtenerAseguradorasCliente' namespace='/combos'/>";
	var _ACTION_OBTENER_PRODUCTOS_ASEG_CORPO = "<s:url action='obtenerProductosAseguradoraCliente' namespace='/combos'/>";
	var _ACTION_OBTENER_TIPOS_RENOVA = "<s:url action='obtenerTiposRenova' namespace='/combos'/>";	
	
	var _ACTION_COMBO_SINO= "<s:url action='obtenerSiNo' namespace='/combos'/>";
	
	var _ACTION_OBTENER_ASEGURADORAS_CLIENTE = "<s:url action='obtenerAseguradorasCliente' namespace='/combos'/>";
	var _ACTION_OBTENER_CLIENTE_CORPO = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
	var _ACTION_OBTENER_PRODUCTOS_ASEGURADORA_CLIENTE = "<s:url action='obtenerProductosAseguradoraCliente' namespace='/combos'/>";
	var _ACTION_COMBO_FORMA_CALCULO_FOLIO = "<s:url action='comboFormaCalculoFolioNroIncisos' namespace='/combos'/>";
	var _ACTION_COMBO_INDICADOR_SP= "<s:url action='comboIndicador_SP_NroIncisos' namespace='/combos'/>";
	var _ACTION_COMBO_PROCESO_POLIZA= "<s:url action='comboProcesoPoliza' namespace='/combos'/>";
	var _ACTION_COMBO_USA_AGRUPACION= "<s:url action='obtenerSiNo' namespace='/combos'/>";
	var _ACTION_COMBO_TIPO_POLIZA= "<s:url action='obtenerTipoPoliza' namespace='/combos'/>";
	var _ACTION_GET_NUMERO_POLIZA = "<s:url action='getNumeroPoliza' namespace='/numeroPolizas'/>";
	var _ACTION_GUARDAR_NUMERO_POLIZA = "<s:url action='guardarNumeroPolizas' namespace='/numeroPolizas'/>";
	
	var _ACTION_INSERTAR_NUMERO_POLIZA = "<s:url action='insertarNumeroPolizas' namespace='/numeroPolizas'/>";
	
	//VARIABLE QUE SE USA COMO BANDERA PARA RECARGAR LA GRILLA CUANDO SE VIENE DE CONFIGURAR (ROLES, ACCIONES O RANGOS)
	var _FLAG = "<s:property value='cdRenova'/>";
	
	var helpMap=new Map();
    // var itemsPerPage= 20;
    
     var itemsPerPage= _NUMROWS;
     
     
     var existCustomNum = false;
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("91")%>    
    
    //EXPRESIONES
	var ACTION_TABLA_EXPRESIONES ="<s:url namespace='expresiones' action='ComboTabla' includeParams='none'/>";
	var ACTION_COLUMNA_EXPRESIONES = "<s:url namespace='expresiones' action='ComboColumna' includeParams='none'/>";
	var ACTION_CLAVE_EXPRESIONES = "<s:url namespace='expresiones' action='ComboClave' includeParams='none'/>";
	
</script>
<!--  <script type="text/javascript">var helpMap = new Map();</script>-->
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>

<!-- Para usar lo de Expresiones:  -->
<script type="text/javascript" src="${ctx}/resources/scripts/utilities/checkColumnPlugin.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/productos/tablaApoyo5Claves/tablaApoyo5Claves.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/expresiones/expresiones.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configuracionRenovacion/consultaConfiguracionRenovacion_abm.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configuracionRenovacion/consultaConfiguracionRenovacion.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configuracionRenovacion/configRenovNumeracionPolizas.js"></script>

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
               <div id="grilla" />
           </td>
       </tr>
    </table>
</body>
</html>