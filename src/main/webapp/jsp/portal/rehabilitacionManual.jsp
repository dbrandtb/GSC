<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Prueba de Tooltips</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_POLIZA = "<s:url action='obtenerInformacionPoliza' namespace='/rehabilitacionManual'/>";
    var _ACTION_BUSCAR_REQUISITOS = "<s:url action='obtenerRequisitos' namespace='/rehabilitacionManual'/>";
    var _ACTION_BUSCAR_DATOS_CANCELACION = "<s:url action='obtenerDatosCancelacion' namespace='/rehabilitacionManual'/>";
    var _ACTION_REHABILITAR_POLIZA = "<s:url action='rehabilitarPoliza' namespace='/rehabilitacionManual'/>";
    var _ACTION_VALIDAR_RECIBOS_PAGADOS = "<s:url action='validarRecibosPagados' namespace='/rehabilitacionManual'/>";

	var _ACTION_COMBO_POLIZAS = "<s:url action='obtenerPolizasCanceladas' namespace='/combos'/>";
	//var _ACTION_COMBO_PRODUCTOS = "<s:url action='obtenerProductosAyuda' namespace='/combos'/>";
	var _ACTION_COMBO_PRODUCTOS = "<s:url action='obtenerProductosPorAseguradora' namespace='/combos'/>";
	var _ACTION_COMBO_ASEGURADORAS = "<s:url action='obtenerPolizasCancManualAeguradoras' namespace='/combos'/>";
	
	var _ACTION_REGRESAR  =  "<s:url action='polizasCanceladas' namespace='/'/>";

	
	

    var itemsPerPage=20;
    var helpMap = new Map();

	var NM_POLIZA = "<s:property value='nmPoliza'/>";
	var ESTADO = "<s:property value='estado'/>";
	var NM_SUPLEM = "<s:property value='nmsuplem'/>";
	var NM_SITUAC = "<s:property value='nmSituac'/>";
	
	var DES_ASEG = "<s:property value='asegurado'/>";
	var FE_CANCEL = "<s:property value='feCancel'/>";
	var DES_RAZON = "<s:property value='dsRazon'/>";
	var FE_EFECTO = "<s:property value='feEfecto'/>";
	var FE_VENCIM = "<s:property value='feVencim'/>";
	var _COMENTARIO = "<s:property value='comentarios'/>";
	
	var CDUNIECO = "<s:property value='cdUnieco'/>";
	var CDRAMO = "<s:property value='cdRamo'/>";
	var INCISO = "<s:property value='inciso'/>";
	var DSRAMO = "<s:property value='dsRamo'/>";
	var DSUNIECO = "<s:property value='dsUnieco'/>";
	var COD_RAZON = "<s:property value='cdRazon'/>";
	
	var NOM_CANCEL= "<s:property value='nmCancel'/>";
	var COD_PERSON= "<s:property value='cdPerson'/>";
	var COD_MONEDA= "<s:property value='cdMoneda'/>";

	var CDUNIAGE = "<s:property value='cdUniage'/>";
	
	var NM_POLIEX="<s:property value='nmPoliex'/>";
	
//	var SPLE = "<s:property value='dssuPle'/>";
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("223")%>

	_URL_AYUDA = "/catweb/rehabilitacionManual.html";
	
</script>
<script type="text/javascript">var helpMap = new Map();</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<!-- script type="text/javascript" src="${ctx}/resources/scripts/portal/rehabilitacionManual/rehabilitacionManual.js"></script-->
<script type="text/javascript" src="${ctx}/resources/scripts/util/pluginCheckColumn.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/confirmaPassword.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/rehabilitacionManual/rehabilitacionManual.js"></script>
<!-- script type="text/javascript" src="${ctx}/resources/scripts/util/abmtooltips.js"></script-->

</head>
<body>
               <div id="formulario" />
               <div id="grillaRequisitos" />
               <div id="formInsideTab" />
               <div id="form2InsideTab" />
   <!-- div id="formularioUpd"></div-->
</body>
</html>