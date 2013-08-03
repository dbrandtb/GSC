<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla Consultar Caso Solicitud</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap= new Map()</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
    
    var _ACTION_BUSCAR_CASOS_SOLICITUD = "<s:url action='buscarCasosSolicitud' namespace='/consultarCasosSolicitud'/>";
    var _ACTION_EXPORTAR_CASOS_SOLICITUD = "<s:url action='exportarCasosSolicitud' namespace='/consultarCasosSolicitud'/>";
    var _ACTION_BORRAR_CASO_SOLICITUD = "<s:url action='borrarCasosSolicitud' namespace='/consultarCasosSolicitud'/>";    
    var _ACTION_IR_ALTA_CASO = "<s:url action='irAltaCaso' namespace='/consultarCasosSolicitud'/>";
    var _ACTION_IR_CONSULTA_CASO_DETALLE = "<s:url action='irConsultaCasoDetalle' namespace='/consultarCasosSolicitud'/>";
    var _ACTION_VOLVER_CONSULTA_CLIENTE = "<s:url action='irConsultaCliente' namespace='/consultarCasosSolicitud'/>";
  
  	var _ACTION_COMBO_PRIORIDAD = "<s:url action='comboObtenerPrioridades' namespace='/combos-catbo'/>";
  	
  	var _ACTION_IR_REASIGNAR_CASO = "<s:url action='irReasignarCaso' namespace='/consultarCasosSolicitud'/>";
  	
  	var CDPERSON = "<s:property value='cdperson'/>";
  	var VENGOCLIENT = "<s:property value='vengoConsClient'/>";
  	var CDELEMEN = "<s:property value='cdElemento'/>";
  	
  	var CDUSUARIO = "";
    var itemsPerPage=_NUMROWS;
    var vistaTipo=1;
      <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("304")%>
    
     _URL_AYUDA = "/backoffice/consulCasosSolicitudes.html";
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>

<script type="text/javascript" src="${ctx}/resources/scripts/consultarCasoSolicitud/consultarCasoSolicitud_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/consultarCasoSolicitud/consultarCasoSolicitud.js"></script>

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
               <div id="gridCasos" />
           </td>
       </tr>
    </table>
</body>
</html>