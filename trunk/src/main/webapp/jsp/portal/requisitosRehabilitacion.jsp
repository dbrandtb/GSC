<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Requisitos de Rehabilitacion</title>
<meta http-equiv="Content-Type" content = "text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>  
<script language="javascript">
    
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_REQUISITOS_REHABILITACION = "<s:url action='buscarRequisitosRehabilitacion' namespace='/requisitosRehabilitacion'/>";
    var _ACTION_GET_REQUISITOS_REHABILITACION = "<s:url action='getRequisitosRehabilitacion' namespace='/requisitosRehabilitacion'/>";
    var _ACTION_INSERTAR_GUARDAR_REQUISITOS_REHABILITACION = "<s:url action='insertarGuardarRequisitosRehabilitacion' namespace='/requisitosRehabilitacion'/>";
    var _ACTION_BORRAR_REQUISITOS_REHABILITACION = "<s:url action='borrarRequisitosRehabilitacion' namespace='/requisitosRehabilitacion'/>";
    var _ACTION_EXPORTAR_REQUISITOS_REHABILITACION =  "<s:url action='exportarRequisitosRehabilitacion' namespace='/requisitosRehabilitacion'/>";
    
    <!-- Combos utilizados -->
    var _ACTION_OBTENER_CLIENTES_CORPORATIVO = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
    var _ACTION_OBTENER_ASEGURADORAS_CLIENTE = "<s:url action='obtenerAseguradorasCliente' namespace='/combos'/>";
    var _ACTION_OBTENER_PRODUCTOS_ASEGURADORA_CLIENTE = "<s:url action='obtenerProductosAseguradoraCliente' namespace='/combos'/>";
	var _ACTION_OBTENER_TIPOS_DOCUMENTO = "<s:url action='obtenerTiposDocumento' namespace='/combos'/>";
	
    // var itemsPerPage=20;
    var itemsPerPage= _NUMROWS;
    var helpMap = new Map();
    
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("30")%>

	_URL_AYUDA = "/catweb/requisitosDeRehabilitacion.html";    

</script>
 
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>

 
<script type="text/javascript" src="${ctx}/resources/scripts/portal/requisitosRehabilitacion/requisitosRehabilitacion.js"></script>
<!-- 
<script type="text/javascript" src="${ctx}/resources/scripts/portal/requisitosRehabilitacion/editarRequisitosRehabilitacion.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/requisitosRehabilitacion/agregarRequisitosRehabilitacion.js"></script> 
 -->

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
               <div id="gridRequisitos" />
           </td>
       </tr>
    </table>
</body>
</html>