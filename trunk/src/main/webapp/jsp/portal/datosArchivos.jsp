<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Datos de Archivos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_DATOS_ARCHIVO = "<s:url action='buscarDatosDeArchivos' namespace='/datosDeArchivos'/>";
    
    // var _ACTION_OBTENER_STATUS_CASOS = "<s:url action='obtenerStatusCasos' namespace='/statusCasos'/>";
    
    var _ACTION_EXPORTAR_DATOS_ARCHIVOS = "<s:url action='exportarDatosArchivos' namespace='/datosDeArchivos'/>";
    // var _ACTION_BUSCAR_STATUS_CASOS_TAREAS = "<s:url action='buscarStatusCasosTareas' namespace='/statusCasos'/>";
     
    var vistaTipo=1;
    var helpMap = new Map();
      var itemsPerPage=10;
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("517")%>
    
      _URL_AYUDA = "/backoffice/datosArchivo.html"; 
      
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/Multiselect.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/datosArchivos/datosArchivos_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/datosArchivos/datosArchivos.js"></script>


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
       <tr valign="top">
           <td>
               <div id="formMultiselect" />
           </td>
       </tr>
    </table>
</body>
</html>