<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Consultar Casos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
  
    var _ACTION_BUSCAR_CASOS_SOLICITUDES = "<s:url action='buscarCasosSolicitudes' namespace='/administracionCasos'/>";
    var _ACTION_IR_AGREGAR_CASO = "<s:url action='irAgregarCaso' namespace='/administracionCasos'/>";
    var _ACTION_IR_CONSULTAR_CASO_DETALLE = "<s:url action='irConsultarDetalle' namespace='/administracionCasos'/>";
    var _ACTION_ELIMINAR_CASO = "<s:url action='eliminarCaso' namespace='/administracionCasos'/>";    
    var _ACTION_EXPORTAR_LISTADO = "<s:url action='exportarListado' namespace='/administracionCasos'/>";    
	var _ACTION_OBTENER_USUARIOS_POR_MODULO = "<s:url action='obtenerUsuariosPorModulo' namespace='/administracionCasos'/>";
	var _ACTION_GUARDAR_REASIGNACION_CASO = "<s:url action='guardarReasignacionCaso' namespace='/administracionCasos'/>";
	
	//FALTA LA CONFIGURACION DE LOS COMBOS EN STRUTS
	var _ACTION_COMBO_MODULOS_DEL_CASO = "<s:url action='comboModulosDelCaso' namespace='/combos-catbo'/>";
	var _ACTION_COMBO_ROLES_USUARIOS = "<s:url action='comboRolesUsuarios' namespace='/combos-catbo'/>";
	
	var _VALIDA_SATUS_CASO = "<s:url action='validaStatusCaso' namespace='/compraTiempo'/>";

    var itemsPerPage=10;
    var helpMap= new Map();

</script>

<script type="text/javascript" src="${ctx}/resources/scripts/reasignarCaso/reasignarCaso_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/reasignarCaso/reasignarCaso.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/consultarCasos/consultarCasos_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/consultarCasos/consultarCasos.js"></script>

</head>
<body>

   <table>
        <tr><td><div id="formBusqueda" /></td></tr>
        <tr><td><div id="gridListado" /></td></tr>
   </table>
</body>
</html>
