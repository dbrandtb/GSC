<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Rangos Renovacion para Reporte</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_RANGOS_RENOVACION = "<s:url action='buscarRangosRenovacion' namespace='/rangosRenovacion'/>";
    var _ACTION_GET_RANGO_RENOVACION = "<s:url action='getRangoRenovacion' namespace='/rangosRenovacion'/>";
    var _ACTION_GET_ENCABEZADO_RANGO_RENOVACION = "<s:url action='getEncabezadoRangoRenovacion' namespace='/rangosRenovacion'/>";
    var _ACTION_AGREGAR_GUARDAR_RANGOS_RENOVACION = "<s:url action='agregarGuardarRangosRenovacion' namespace='/rangosRenovacion'/>";
    var _ACTION_BORRAR_RANGO_RENOVACION = "<s:url action='borrarRangoRenovacion' namespace='/rangosRenovacion'/>";
    var _ACTION_EXPORT = "<s:url action='exportarRangosRenovacion' namespace='/rangosRenovacion'/>";
    var _ACTION_IR_CONFIGURACION_RANGOS_DE_RENOVACION = "<s:url action='irConfiguracionRenovacion' namespace='/rangosRenovacion'/>";
  
  
  
    var itemsPerPage=10;
    <%=session.getAttribute("helpMap")%>
    
    //var CDCLIENTE = "<s:property value='codigoPersona'/>";
    var CDCLIENTE = "1";
	var CDASEGURADORA = "1";
	var CDTIPO="1";
	var CDPRODUCTO="RZ";
	var CDRENOVA="<s:property value='cdRenova'/>";
	var CDRANGO="2";
	
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap = new Map();</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
 
<script type="text/javascript" src="${ctx}/resources/scripts/portal/rangoRenovacion/editarRangoRenovacion.js"></script> 
<script type="text/javascript" src="${ctx}/resources/scripts/portal/rangoRenovacion/agregarRangoRenovacion.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/rangoRenovacion/rangoRenovacion.js"></script>


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
               <div id="gridRangosRenovacion" />
           </td>
       </tr>
    </table>
</body>
</html>