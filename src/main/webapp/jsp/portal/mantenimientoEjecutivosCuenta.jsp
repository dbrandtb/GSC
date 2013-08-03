<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Mantenimiento Ejecutivos de Cuenta</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_EJECUTIVOS   = "<s:url action='buscarEjecutivos' namespace='/mantenimientoEjecutivosCuenta'/>";
	var _ACTION_EXPORTAR_EJECUTIVOS = "<s:url action='exportarEjecutivos' namespace='/mantenimientoEjecutivosCuenta'/>";    
    var _ACTION_BORRAR_EJECUTIVO = "<s:url action='borrarEjecutivo' namespace='/mantenimientoEjecutivosCuenta'/>";

    var _ACTION_OBTENER_EJECUTIVO = "<s:url action='obtenerEjecutivo' namespace='/mantenimientoEjecutivosCuenta'/>";
    var _ACTION_AGREGAR_GUARDAR_EJECUTIVO = "<s:url action='guardarEjecutivo' namespace='/mantenimientoEjecutivosCuenta'/>";    
    var _ACTION_CONFIGURAR_ATRIBUTOS = "<s:url action='irMantenimientoAtributosEjecutivo' namespace='/mantenimientoEjecutivosCuenta'/>";    

    var _ACTION_COMBO_PERSONAS = "<s:url action='confAlertasAutoClientes' namespace='/combos'/>";    
    var _ACTION_OBTENER_STATUS = "<s:url action='comboEstadosEjecutivo' namespace='/combos'/>";    

	var helpMap = new Map();
    var itemsPerPage=20;
    <%=session.getAttribute("helpMap")%>
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/portal/mantenimientoEjecutivosCuenta/agregarMantenimientoEjecutivosCuenta.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/mantenimientoEjecutivosCuenta/editarMantenimientoEjecutivosCuenta.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/mantenimientoEjecutivosCuenta/mantenimientoEjecutivosCuenta.js"></script>


</head>
<body>
			<table>
				<tr><td>
                <div id="formBusqueda" />
                </td></tr>
                <tr><td>
               <div id="grid" />
               </td></tr>
           </table>
</body>
</html>