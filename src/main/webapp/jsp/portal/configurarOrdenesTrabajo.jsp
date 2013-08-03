<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Configurar Secciones de Ordenes de Trabajo</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_OBTENER_SECCIONES_FORMATO = "<s:url action='obtenerSeccionesFormato' namespace='/configurarOrdenesTrabajo'/>";
	var _ACTION_GUARDAR_SECCIONES_FORMATO = "<s:url action='guardarSeccionFormato' namespace='/configurarOrdenesTrabajo'/>";
	var _ACTION_ELIMINAR_SECCIONES_FORMATO = "<s:url action='borrarSeccionFormato' namespace='/configurarOrdenesTrabajo'/>";
	var _ACTION_REGRESAR = "<s:url action='irFormatoOrdenesTrabajo' namespace='/configurarOrdenesTrabajo'/>";
	var _ACTION_IR_A_VALORES_POR_DEFECTO = "<s:url action='irValoresxDefecto' namespace='/configurarOrdenesTrabajo'/>";
	
	
    var _ACTION_OBTENER_FORMATO = "<s:url action='obtenerAtributosFormatoOrdenTrabajo' namespace='/configurarOrdenesTrabajo'/>";
	var _ACTION_GUARDAR__FORMATO = "<s:url action='insertarGuardarAtributosFormatoOrdenTrabajo' namespace='/configurarOrdenesTrabajo'/>";
	var _ACTION_ELIMINAR_FORMATO = "<s:url action='borrarAtributosFormatoOrdenTrabajo' namespace='/configurarOrdenesTrabajo'/>";
	
	
	// Combos url de los combos
    var _ACTION_OBTENER_COMBO_SECCIONES_FORMATO = "<s:url action='obtenerSeccionesFormato' namespace='/combos'/>";
    var _ACTION_OBTENER_COMBO_SECCIONES_ORDEN = "<s:url action='comboSeccionesFormatoOrden' namespace='/combos'/>";    
    var _ACTION_OBTENER_COMBO_TIPOS_OBJETOS = "<s:url action='comboTiposObjetos' namespace='/combos'/>";
 	var _ACTION_OBTENER_COMBO_TIPOS_SITUACION = "<s:url action='obtenerTiposSituacion' namespace='/combos'/>";
 	
	// dato que viene de la pantalla Formato de ordenes de trabajo
	var CODIGO_FORMATO_ORDEN = "<s:property value='cdFormatoOrden'/>";
	//var DESCRIPCION_FORMATO_ORDEN = "<s:property value='dsFormatoOrden'/>";
	var DESCRIPCION_FORMATO_ORDEN = "<s:property value='descripcionEscapedJavaScript'/>";
	var CODIGO_SECCION = "<s:property value='cdSeccion'/>";
	var CODIGO_ATRIBUTO = "<s:property value='cdAtribu'/>";
	
	// dato que sirve de bandera para cuando vuelva de la pantalla de valores por defecto, si esta vacia es porque vengo de la pantalla
	// de formato de ordenes de trabajo, si tiene valor, vengo de valores por defecto
	var FLAG = "<s:property value='flag'/>";
	
	var _ACTION_OBTENER_COMBO_BLOQUES = "<s:url action='obtenerBloquesConfAtributosFormatoOrdenTrabajo' namespace='/combos'/>";
    var _ACTION_OBTENER_COMBO_CAMPO_BLOQUES = "<s:url action='obtenerComboCampoBloques' namespace='/combos'/>";
    var _ACTION_OBTENER_COMBO_FORMATO = "<s:url action='obtenerComboFormatosCampo' namespace='/combos'/>";
 	
    var helpMap= new Map();
    var itemsPerPage=10;
    <%=session.getAttribute("helpMap")%>
      <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("514")%>
</script>


<!-- <script type="text/javascript">var helpMap = new Map();</script> -->
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script src="${ctx}/resources/scripts/ValidacionGrilla/Ext.ux.plugins.GridValidator.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configurarOrdenesTrabajo/configurarOrdenesTrabajo.js"></script>
</head>
<body>

   <table class="headlines" cellspacing="9">
   		<tr valign="top">
           <td class="headlines" colspan="3">
               <div id="formTab" />
           </td>
       </tr>
       <tr valign="top">
           <td class="headlines" colspan="3">
               <div id="gridSecciones" />
           </td>
       </tr>
       <tr valign="top">
           <td class="headlines" colspan="3">
               <div id="gridAtributos" />
           </td>
       </tr>
    </table>
</body>
</html>