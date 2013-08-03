<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Pantalla de Alta de Caso</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";  
    
    var _ACTION_OBTENER_ENCABEZADO = "<s:url action='obtenerEncabezado' namespace='/administracionCasos'/>";
    var _ACTION_OBTENER_POLIZAS = "<s:url action='obtenerPolizas' namespace='/consultaPolizas'/>";
    var _ACTION_OBTENER_ATRIBUTOS_VARIABLES = "<s:url action='obtenerAtributosVariablesCasosDos' namespace='/administracionCasos'/>";
    var _ACTION_OBTENER_USUARIOS_RESPONSABLES = "<s:url action='obtenerListaUsuariosResponsables' namespace='/administracionCasos'/>";
    var _ACTION_GUARDAR_NUEVO_CASO = "<s:url action='guardarNuevoCaso' namespace='/administracionCasos'/>";
    
    var _ACTION_VOLVER_CONSULTA_CASO = "<s:url action='irConsultaCaso' namespace='/administracionCasos'/>";    	
	
	var _ACTION_OBTENER_COMBO_TAREAS = "<s:url action='obtenerComboTareas' namespace='/combos-catbo'/>";
	var _ACTION_OBTENER_COMBO_PRIORIDADES = "<s:url action='obtenerComboPrioridades' namespace='/combos-catbo'/>";
	
	var _ACTION_OBTENER_COMBO_DATOS_CATALOGOS_DEP = "<s:url action='obtenerComboDatosCatalogosDep' namespace='/combos-catbo'/>";
	var _ACTION_OBTENER_COMBO_PLAN = "<s:url action='obtenerComboPlanesProducto' namespace='/combos-catbo'/>";
	var _ACTION_OBTENER_COMBO_FORMA_PAGO = "<s:url action='getComboInstrumentoPago' namespace='/combos-catbo'/>";
	var _ACTION_OBTENER_COMBO_MONEDA = "<s:url action='obtenerComboMonedas' namespace='/combos-catbo'/>";
	
	var _ACTION_OBTENER_PERSONA_CASO = "<s:url action='obtenerPersonaCaso' namespace='/altaCasos'/>";
	
	var _ACTION_ENVIAR_CORREO = "<s:url action='enviaMail' namespace='/mail'/>";
	
	var _IR_BUSQUEDA_POLIZA =  "<s:url action='busquedaPoliza' namespace='/procesoemision'/>";
	
	var _IR_DETALLE_POLIZA =  "<s:url action='detallePoliza' namespace='/procesoemision'/>";
	
	var _ACTION_VALIDAR_TIEMPOS_MATRIZ = "<s:url action='validarTiemposMatriz' namespace='/administracionCasos'/>";

    
    var itemsPerPage=_NUMROWS;
    var helpMap= new Map();
    
    var CDPERSON = "<s:property value='cdperson'/>";
    var CDELEMEN = "<s:property value='cdElemento'/>";
    
    CDELEMEN = ( CDELEMEN == null )? '' : CDELEMEN;
     var _BANDERA_OK = 0;
    
   // var CDPERSON = 341;
    var CDUSUARIO = "";
    //var TIPOENVIO = "M";
  
    
    //Codigo que valida si me encuentro en la pantalla de Consulta de Clientes
    var CONSULTA_CLIENTE_SCREEN=false;
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("804")%>
    
    _URL_AYUDA = "/backoffice/altaCaso.html";
        
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/consultarCasoDetalle/enviarCorreo.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/altaDeCaso/atributosVariables.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/consultaPolizas/consultaPolizas.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/altaDeCaso/altaCaso_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/altaDeCaso/altaCaso.js"></script>

</head>
<body>
   <table>
        <tr><td><div id="encabezadoFijo" /></td></tr>
        <tr><td>
        		<iframe id="atributosVariables" align="middle" frameBorder="NO" name="atributosVariables" width="630" scrolling="NO" height="1">
        		</iframe>
        	</td>
        </tr>
        <tr><td><div id="grillaResponsables"/></td></tr>
   </table>
</body>
</html>