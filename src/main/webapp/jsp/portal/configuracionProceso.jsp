<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Configuracion de Procesos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_PROCESOS = "<s:url action='buscarProcesos' namespace='/configuracionProcesos'/>";
	var _ACTION_OBTENER_PROCESO = "<s:url action='obtenerProceso' namespace='/configuracionProcesos'/>";	
	var _ACTION_ACTUALIZAR_PROCESO = "<s:url action='actualizarProceso' namespace='/configuracionProcesos'/>";	
	var _ACTION_GUARDAR_PROCESO = "<s:url action='guardarProceso' namespace='/configuracionProcesos'/>";	
    var _ACTION_BORRAR_PROCESO = "<s:url action='borrarProceso' namespace='/configuracionProcesos'/>";
    var _ACTION_EXPORT_PROCESOS = "<s:url action='exportarProcesos' namespace='/configuracionProcesos'/>"; 
	//var _ACTION_BACK_TO_SOMEWHERE = "<s:url action='' namespace='/'/>";

	var _ACTION_COMBO_ASEGURADORAS = "<s:url action='obtenerAseguradoras' namespace='/combos'/>";
	var _ACTION_COMBO_PROCESOS = "<s:url action='obtenerTiposProcesos' namespace='/combos'/>";
	var _ACTION_COMBO_CLIENTES = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
	var _ACTION_COMBO_INDICADOR = "<s:url action='obtenerCatalogo' namespace='/combos'/>";
	var _ACTION_COMBO_PRODUCTOS = "<s:url action='obtenerProductosPorAseguradora' namespace='/combos'/>";

    var itemsPerPage=20;
    <%=session.getAttribute("helpMap")%>
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap = new Map();</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<!--
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configuracionProcesos/agregarConfiguracionProcesos.js"></script>
-->
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configuracionProcesos/editarConfiguracionProcesos.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configuracionProcesos/configuracionProcesos.js"></script>

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