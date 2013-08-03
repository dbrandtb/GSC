<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Consulta de Cliente</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<%@page import="mx.com.aon.portal.model.UserVO"%>

<script language="javascript">
    var _CONTEXT = "${ctx}";
  
    var _ACTION_BUSCAR_CLIENTES = "<s:url action='buscarClientes' namespace='/administracionCasos'/>";
    var _ACTION_IR_CONSULTA_CASOS = "<s:url action='irConsultarCasos' namespace='/administracionCasos'/>";
    var _ACTION_OBTENER_POLIZAS = "<s:url action='obtenerPolizas' namespace='/consultaPolizas'/>"; 
   
   
         
    var _ACTION_COMBO_EMPRESAS = "<s:url action='comboClientesCorpBO' namespace='/combos-catbo'/>";   
  
    var _ACTION_IR_EDITAR_PERSONA = "<s:url action='irEditarPersona' namespace='/personas'/>";
    
    var _ACTION_VALIDA_TCONFIGURAENCUESTA = "<s:url action='validaConfiguracionEncuesta' namespace='/encuestas'/>";
    
	var CONSULTA_CLIENTE_SCREEN=true;
	
	var _IR_DETALLE_POLIZA =  "<s:url action='detallePoliza' namespace='/procesoemision'/>";
	
	var _IR_BUSQUEDA_POLIZA =  "<s:url action='busquedaPoliza' namespace='/procesoemision'/>";
	
	var _ACTION_LOGIN = "<s:url action='login' namespace='/login'/>";
		var _ACTION_OBTENER_DATOS_TREE = "<s:url action='obtenerDatosTreeView' namespace='/treeView'/>";
	
		var _ACTION_OBTENER_TIPO_GUION = "<s:url action='comboTipoGuion' namespace='/combos-catbo'/>";
		var _ACTION_BUSCAR_DIALOGOS_GUION = "<s:url action='buscarDialogosGuion' namespace='/operacionesCat'/>";
		var _ACTION_OBTENER_DIALOGO_GUION_INICIAL = "<s:url action='obtenerDialogoGuionInicial' namespace='/operacionesCat'/>";
		var _ACTION_GUARDAR_DIALOGO_OBSERVACION = "<s:url action='guardarDialogoScriptObservacion' namespace='/operacionesCat'/>"; 
		var _ACTION_OBTENER_ENCUESTA_PENDIENTES = "<s:url action='obtenerEncuestasPendientes' namespace='/operacionesCat'/>"; 
		var _ACTION_OBTENER_FUNCION_NOMBRE = "<s:url action='obtenerFuncionNombre' namespace='/operacionesCat'/>";
		
		var IR_INGRESAR_ENCUESTAS = "<s:url action='irIngresarEncuestas' namespace='/ingresarEncuesta'/>";		
		
		var itemsPerPage = _NUMROWS; 
		
		var CODIGO_PERSONA = "<s:property value='cdperson'/>";
	
	
  
    var helpMap= new Map();
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("303")%>

 	_URL_AYUDA = "/backoffice/consulCliente.html";

<% 	UserVO userVO = (UserVO)session.getAttribute("USUARIO");
    	
			%>
	
	var CDELEMENTO = '<%=userVO.getEmpresa().getElementoId()%>' 

</script>

<script type="text/javascript" src="${ctx}/resources/scripts/consultarEncuestas/encuesta_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/operacionesCat/catScriptAtencion.js"></script>
<!-- <script type="text/javascript" src="${ctx}/resources/scripts/consultarEncuestas/consultaPolizaEncuesta.js"></script> -->
<script type="text/javascript" src="${ctx}/resources/scripts/consultaCliente/consultaPolizas.js"></script> 
<!--<script type="text/javascript" src="${ctx}/resources/scripts/consultaPolizas/consultaPolizas.js"></script>-->
<script type="text/javascript" src="${ctx}/resources/scripts/consultaCliente/consultaCliente_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/consultaCliente/consultaCliente.js"></script>

</head>
<body>

   <table>
        <tr><td><div id="formBusqueda" /></td></tr>
        <tr><td><div id="gridListado" /></td></tr>
   </table>
   
  <!--<div id="div_form">
				<div id="tree-div" style="overflow:auto; height:300px;width:250px;border:1px solid #c3daf9;"></div>
			</div>  -->
   
</body>
</html>