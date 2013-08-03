<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Mantenimiento de Calendario para días no Laborables</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- css para el Spinner:   -->
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/Spinner.css" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap= new Map()</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<%@page import="mx.com.aon.portal.model.UserVO"%>
<%@page import="mx.com.aon.portal.model.BaseObjectVO"%>
<script language="javascript">
    var _CONTEXT = "${ctx}";    
    var _ACTION_BUSCAR_CALENDARIO = "<s:url action='traerCalendario' namespace='/calendario'/>";
    var _ACTION_BORRAR_CALENDARIO = "<s:url action='delCalendario' namespace='/calendario'/>";
    var _ACTION_OBTENER_CALENDARIO = "<s:url action='getElCalendario' namespace='/calendario'/>";
    var _ACTION_AGREGAR_GUARDAR_CALENDARIO = "<s:url action='salvaCalendario' namespace='/calendario'/>";
    var _ACTION_EXPORTAR_CALENDARIO = "<s:url action='exportarCalendario' namespace='/calendario'/>";
    var _ACTION_EDITAR_CALENDARIO = "<s:url action='editarCalendario' namespace='/calendario'/>";
// COMBOS  - url de los combos -
    var _ACTION_OBTENER_PAIS = "<s:url action='obtenerPais' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_MES = "<s:url action='obtenerMes' namespace='/combos-catbo'/>";
    
    var helpMap=new Map();
    var itemsPerPage= _NUMROWS;
     
    <% 	UserVO userVO = (UserVO)session.getAttribute("USUARIO");
        BaseObjectVO baseObjectVO;
		if (userVO != null)
			{baseObjectVO = (BaseObjectVO)userVO.getPais();
			String cdPais = baseObjectVO.getValue();
			%>
	
	var cdPaisUsuarioLogueado = '<%=baseObjectVO.getValue()%>' 
	
     <%
     }
     %>
     
   //  var itemsPerPage=20;
    var vistaTipo=1;
    
</script>

<!-- Archivos js para el Spinner:   -->
<script type="text/javascript" src="${ctx}/resources/scripts/util/Spinner.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/SpinnerStrategy.js"></script>
<!--script type="text/javascript" src="${ctx}/resources/scripts/util/Spinner/ext-spinner.js"></script-->

<script type="text/javascript" src="${ctx}/resources/scripts/calendario/editarCalendario.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/calendario/agregarCalendario.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/calendario/calendario.js"></script>

</head>

<body> 

	 <table>
        <tr><td><div id="formDocumentos" /></td></tr>
       <tr><td><div id="gridElementos" /></td></tr>
    </table>
    <div id="formularioExport" />
    
    <!--h1>Ext Spinner Widget for ExtJS 2.x</h1-->
<!--
<p>Go to <a href="http://extjs.com/forum/showthread.php?t=16352">the forum</a> for more information on its usage and support.</p>

<fieldset style="margin-top:2em">
	<legend>Example</legend>
	<table>
		<tr>
			<td style="padding: 2em;">
				<form id="frm" name="frm">

					<fieldset>
						<legend>Spinner applied to textfield</legend>
						<input type="text" id="t" />
					</fieldset>
				</form>
				
				<ul>
					<li>Click the control to spin.</li>
					<li>Hold "Shift" for alternate spinning.</li>

					<li>Drag the divider with your mouse to spin.</li>
					<li>Put focus on field and press "up" or "down" to spin.</li>
					<li>When focused press "pageUp" or "pageDown" for alternate spinning.</li>
					<li>Hover the control and use your scrollwheel on your mouse to spin.</li>
				</ul>
			</td>
			<td>

				<div id="container"></div>		
			</td>
		</tr>
	</table>
</fieldset>

<fieldset style="padding:1.5em">
	<legend>Spinner in a Basic Form</legend>
	<div id="form-ct" style="margin-top:1em"></div>
</fieldset>

<fieldset style="padding:1.5em">
	<legend>Textfield plugin</legend>
	<div id="plugin-ct"></div>
</fieldset-->

    
</body>
</html>