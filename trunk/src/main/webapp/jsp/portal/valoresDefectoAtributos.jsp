<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Valores por Defecto</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_OBTENER_VALOR_DEFECTOS_ATRIBUTO = "<s:url action='obtenerValoresDefectoAtributos' namespace='/configurarOrdenesTrabajo'/>";
    var _ACTION_GUARDAR_VALOR_DEFECTOS_ATRIBUTO = "<s:url action='guardarValorDefectoAtributos' namespace='/configurarOrdenesTrabajo'/>";
    var _ACTION_IR_CONFIGURAR_ORDENES_TRABAJO = "<s:url action='irConfigurarOrdenesTrabajo' namespace='/configurarOrdenesTrabajo'/>";
	
	var CODIGO_FORMATO_ORDEN = "<s:property value='cdFormatoOrden'/>";
	var DESCRIPCION_FORMATO_ORDEN = "<s:property value='dsFormatoOrden'/>";
	var CODIGO_SECCION = "<s:property value='cdSeccion'/>";
	var CODIGO_ATRIBUTO = "<s:property value='cdAtribu'/>";
    var itemsPerPage=10;
	
</script>
	
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/valoresDefectoAtributos/valoresDefectoAtributos.js"></script>


</head>
<body>
    <table class="headlines" cellspacing="6">
   		<tr valign="top">
           <td class="headlines" colspan="3">
               <div id="encabezado" />
           </td>
       </tr>
       <tr valign="top">
           <td class="headlines" colspan="3">
               <div id="grilla" />
           </td>
       </tr>
    </table>
</body>
</html>