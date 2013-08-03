<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>

<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Script Atencion</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
		var _CONTEXT = "${ctx}";
		<%=session.getAttribute("helpMap")%>
		var _ACTION_LOGIN = "<s:url action='login' namespace='/login'/>";
		var _ACTION_OBTENER_DATOS_TREE = "<s:url action='obtenerDatosTreeView' namespace='/treeView'/>";
	
		var _ACTION_OBTENER_TIPO_GUION = "<s:url action='comboTipoGuion' namespace='/combos-catbo'/>";
		var _ACTION_BUSCAR_DIALOGOS_GUION = "<s:url action='buscarDialogosGuion' namespace='/operacionesCat'/>";
		var _ACTION_OBTENER_DIALOGO_GUION_INICIAL = "<s:url action='obtenerDialogoGuionInicial' namespace='/operacionesCat'/>";
		var _ACTION_GUARDAR_DIALOGO_OBSERVACION = "<s:url action='guardarDialogoScriptObservacion' namespace='/operacionesCat'/>"; 
		var _ACTION_OBTENER_ENCUESTA_PENDIENTES = "<s:url action='obtenerEncuestasPendientes' namespace='/operacionesCat'/>"; 
		var _ACTION_OBTENER_FUNCION_NOMBRE = "<s:url action='obtenerFuncionNombre' namespace='/operacionesCat'/>";
		
		var itemsPerPage=10; 
		
		var CODIGO_PERSONA = "<s:property value='cdperson'/>";

    	var helpMap = new Map();		

    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("510")%>    

	</script>
	
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>

<script type="text/javascript" src="${ctx}/resources/scripts/operacionesCat/catScriptAtencion.js"></script>

</head>
<body>
			<div id="div_form">
				<div id="tree-div" style="overflow:auto; height:300px;width:250px;border:1px solid #c3daf9;"></div>
			</div>
</body>
</html>