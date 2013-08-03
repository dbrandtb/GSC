<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Pantalla de Acceso a la Estructura por Rol de Usuario</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_ACCESO_ESTRUCTURA_ROL_USUARIO = "<s:url action='buscarAccesos' namespace='/accesoEstructuraRolUsuario'/>";
    var _ACTION_BORRAR_ACCESO_ESTRUCTURA_ROL_USUARIO = "<s:url action='borrarAcceso' namespace='/accesoEstructuraRolUsuario'/>";
    var _ACTION_GUARDAR_ACCESO_ESTRUCTURA_ROL_USUARIO = "<s:url action='guardarAcceso' namespace='/accesoEstructuraRolUsuario'/>";
    var _ACTION_EXPORTAR_ACCESO_ESTRUCTURA_ROL_USUARIO = "<s:url action='exportarAcceso' namespace='/accesoEstructuraRolUsuario'/>"; 
    
    var _ACTION_COMBO_NIVEL = "<s:url action='comboClientesCorpBO' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_ROL = "<s:url action='comboRolesUsuarios' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_USUARIO = "<s:url action='comboUsuarios' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_ESTADO = "<s:url action='obtenerDatosCatalogo' namespace='/combos-catbo'/>";
     
    var itemsPerPage=20;
    var helpMap= new Map();
    
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/accesoEstructuraRolUsuario/accesoEstructuraRolUsuario_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/accesoEstructuraRolUsuario/accesoEstructuraRolUsuario.js"></script>
</head>
<body>
   <table>
   		<tr><td><div id="formulario" /></td></tr>
		<tr><td><div id="grilla" /></td></tr>
    </table>
</body>
</html>