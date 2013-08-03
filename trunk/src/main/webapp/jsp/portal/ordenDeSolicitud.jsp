<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Orden de Solicitud</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";  
    
   var _ACTION_OBTENER_ATRIBUTOS_VARIABLES = "<s:url action='obtenerAtributosVariablesCasos' namespace='/administracionCasos'/>";
    var _ACTION_OBTENER_USUARIOS_RESPONSABLES = "<s:url action='obtenerListaUsuariosResponsables' namespace='/administracionCasos'/>";
    var _ACTION_GUARDAR_NUEVO_CASO = "<s:url action='guardarNuevoCaso' namespace='/administracionCasos'/>";
    
    var _ACTION_VOLVER_CONSULTA_CASO = "<s:url action='irConsultaCaso' namespace='/administracionCasos'/>";     
    
    var _ACTION_OBTENER_COMBO_TAREAS = "<s:url action='obtenerComboTareas' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_COMBO_PRIORIDADES = "<s:url action='obtenerComboPrioridades' namespace='/combos-catbo'/>";
    
    var _ACTION_OBTENER_COMBO_DATOS_CATALOGOS_DEP = "<s:url action='obtenerComboDatosCatalogosDep' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_COMBO_PLAN = "<s:url action='obtenerComboPlanesProducto' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_COMBO_FORMA_PAGO = "<s:url action='getComboInstrumentoPago' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_COMBO_MONEDA = "<s:url action='obtenerComboMonedas' namespace='/combos-catbo'/>";
    var _ACTION_VOLVER_CAPTURA_COTIZACION = "<s:url action='irCapturaCotizacion' namespace='/administracionCasos'/>";
    
    var _ACTION_OBTENER_COMBO_ASEGURADORAS_PRODCORP = "<s:url action='obtenerComboAseguradorasProdCorp' namespace='/combos-catbo'/>";
    
    var itemsPerPage=10;
    var helpMap= new Map();
    
    var CDPERSON = "<s:property value='cdperson'/>";
    var CDUSUARIO = "";
    var wORIGEN = "<s:property value='wORIGEN'/>";
    
    var CDPROCESO= "<s:property value='cdProceso'/>";
    var CDELEMENTO= "<s:property value='cdElemento'/>";
    var CDRAMO= "<s:property value='cdRamo'/>";
	
	<%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("315")%>

	_URL_AYUDA = "/backoffice/ordenSolicitud.html";
	
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/ordenDeSolicitud/atributosVariablesOrdSlct.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/ordenDeSolicitud/ordenDeSolicitud_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/ordenDeSolicitud/ordenDeSolicitud.js"></script>
</head>
<body>
   <table>
        <tr>
        	<td>
        		<div id="encabezadoEstOrdSlct" />
        	</td>
        </tr>
        <tr>
        	<td>
        		<iframe id="atributosVariables" align="middle" frameBorder="NO" name="atributosVariables" width="600" scrolling="NO" height="1">
        		</iframe>
        	</td>
        </tr>
        <tr>
        	<td>
        		<div id="botonesVrblOrdSlct"/>
        	</td>
        </tr>
   </table>
</body>




<!--
<body>

   <table>
   	<tr valign="top">
            <td class="headlines" colspan="2">
                <div id="encabezadoEstOrdSlct" />
            </td>
        </tr>
   </table>
</body>
-->
</html>