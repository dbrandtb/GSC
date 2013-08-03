<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Configurar Elementos de Estructura</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script> 

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_PERSONAS = "<s:url action='buscarPersonas' namespace='/personas'/>";
    var _ACTION_EXPORTAR_PERSONAS = "<s:url action='exportarPersonas' namespace='/personas'/>";
 	var _ACTION_BORRAR_PERSONA = "<s:url action='borrarPersona' namespace='/personas'/>";

// Combos url de los combos
    var _ACTION_OBTENER_TIPO_PERSONAS_J = "<s:url action='comboTipoPersonaJ' namespace='/combos'/>";
    var _ACTION_OBTENER_CORPORATIVO = "<s:url action='comboNivelesCorporativos' namespace='/combos'/>";
 	var _ACTION_IR_AGREGAR_PERSONA = "<s:url action='irAgregarPersona' namespace='/personas'/>";
 	var _ACTION_IR_EDITAR_PERSONA = "<s:url action='irEditarPersona' namespace='/personas'/>";
 	var _ACTION_BORRAR_PERSONA = "<s:url action='borrarPersona' namespace='/personas'/>";//COMPLETAR EL CODIGO DE AQUI EN ADELANTE
    var _ACTION_OBTENER_CLIENTES_CORP = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
    var _CDPERSON = "<s:property value='cdperson'/>";
	//var CODIGO_CONFIGURACION = "<s:property value='codigoConfiguracion'/>";
	var helpMap = new Map();
    // var itemsPerPage=20;
    var itemsPerPage= _NUMROWS;
    <%=session.getAttribute("helpMap")%>
   
    
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("19")%>
    
    _URL_AYUDA = "/catweb/consultaPersonas.html";
    
    
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/personas/personas.js"></script>
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
               <div id="gridElementos" />
           </td>
       </tr>
    </table>
</body>
</html>