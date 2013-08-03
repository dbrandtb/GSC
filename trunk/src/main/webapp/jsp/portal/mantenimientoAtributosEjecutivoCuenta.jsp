<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Mantenimiento Ejecutivos de Cuenta</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
	var _ACTION_EXPORTAR_ATRIBUTOS_EJECUTIVO = "<s:url action='exportarAtributosEjecutivos' namespace='/mantenimientoEjecutivosCuenta'/>";    

    var _ACTION_OBTENER_EJECUTIVO = "<s:url action='obtenerEjecutivo' namespace='/mantenimientoEjecutivosCuenta'/>";
    var _ACTION_OBTENER_ATRIBUTOS = "<s:url action='obtenerAtributosEjecutivo' namespace='/mantenimientoEjecutivosCuenta'/>";
    var _ACTION_GUARDAR_ATRIBUTOS = "<s:url action='guardarAtributos' namespace='/mantenimientoEjecutivosCuenta'/>";
    var _ACTION_REGRESAR = "<s:url action='mantenimientoEjecutivosCuenta' namespace='/mantenimientoEjecutivosCuenta'/>";

// ACTION COMBOS
	var _ACTION_OBTENER_COMBO = "<s:url action='obtenerComboGenerico' namespace='/combos'/>";

	var CODIGO_EJECUTIVO = "<s:property value='cdEjecutivo'/>";
    var itemsPerPage=10;
    <%=session.getAttribute("helpMap")%>
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap = new Map()</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/mantenimientoAtributosEjecutivoCuenta/mantenimientoAtributosEjecutivoCuenta.js"></script>


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