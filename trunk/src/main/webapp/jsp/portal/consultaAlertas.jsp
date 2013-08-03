<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Checklist de la configuración de la cuenta</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="javascript">
	var _CONTEXT = "${ctx}";
	var _ACTION_BUSCAR_ALERTAS = "<s:url action='buscarAlertasUsuario' namespace='/alertasUsuario'/>";
	var _ACTION_GUARDAR_ALERTA = "<s:url action='guardarAlerta' namespace='/alertasUsuario'/>";
	var _ACTION_CONF_ALERTAS_GET = "<s:url action='getAlerta' namespace='/alertasUsuario'/>";
	var _ACTION_BORRAR_ALERTA = "<s:url action='borrarAlerta' namespace='/alertasUsuario'/>";

	//<!-- Combos utilizados -->
	/* var _ACTION_COMBO_ROL = "<s:url action='confAlertasAutoRol' namespace='/combos'/>";
	var _ACTION_COMBO_TIPORAMO = "<s:url action='confAlertasAutoTipoRamo' namespace='/combos'/>";
	var _ACTION_COMBO_REGION = "<s:url action='confAlertasAutoRegion' namespace='/combos'/>";
	var _ACTION_COMBO_PRODUCTOS = "<s:url action='obtenerProductos' namespace='/combos'/>";
    var _ACTION_COMBO_PRODUCTOS = "<s:url action='obtenerProductosDescuentos' namespace='/combos'/>";*/


	var _ACTION_COMBO_USUARIOS = "<s:url action='confAlertasAutoUsuarios' namespace='/combos'/>";
    var _ACTION_COMBO_CLIENTES_CORP = "<s:url action='comboClientesPorUsuario' namespace='/combos'/>";
	var _ACTION_COMBO_ASEGURADORA = "<s:url action='obtenerAseguradorasCliente' namespace='/combos'/>";
    var _ACTION_COMBO_POLIZAS = "<s:url action='obtenerPolizasPorAseguradora' namespace='/combos'/>";
    var _ACTION_COMBO_RECIBOS = "<s:url action='obtenerReciboPorPolizasPorAseguradora' namespace='/combos'/>";
	var _ACTION_COMBO_PROCESOS = "<s:url action='confAlertasAutoProcesos' namespace='/combos'/>";
	var _ACTION_COMBO_TEMPORALIDAD = "<s:url action='confAlertasAutoTemporalidad' namespace='/combos'/>";
    var _ACTION_COMBO_FRECUENCIAS = "<s:url action='obtenerFrecuencias' namespace='/combos'/>";
    
	var _ACTION_COMBO_POLIZAS2 = "<s:url action='obtenerPolizasPorAseguradora2' namespace='/combos-catbo'/>";
    
	var CODIGO_CONFIGURACION = "<s:property value='codigoConfAlerta'/>";
    var itemsPerPage=20;
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configuracionAlertasAutomatico/agregarAlerta.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configuracionAlertasAutomatico/editarAlerta.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configuracionAlertasAutomatico/consultaAlertas.js"></script>
</head>
<body>

	<div id="formulario"></div>
	<div id="gridConfiguraAlertas" />


</body>
</html>