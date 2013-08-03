<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Matriz de Asignacion</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
  
    var _ACTION_BUSCAR_MATRICES = "<s:url action='buscarMatricesAsignacion' namespace='/matrizAsignacion'/>";
    var _ACTION_BUSCAR_RESPONSABLES = "<s:url action='buscarResponsables' namespace='/matrizAsignacion'/>";
    var _ACTION_BORRAR_MATRIZ = "<s:url action='borrarMatrizAsignacion' namespace='/matrizAsignacion'/>";
    var _ACTION_GUARDAR_MATRIZ = "<s:url action='guardarNotificaciones' namespace='/matrizAsignacion'/>";
    var _ACTION_EXPORTAR_MATRICES = "<s:url action='guardarNotificaciones' namespace='/matrizAsignacion'/>";
    
    var _ACTION_IR_CONFIGURA_MATRIZ_TAREA = "<s:url action='irConfiguraMatrizTarea' namespace='/matrizAsignacion'/>";
    var _ACTION_IR_CONFIGURA_MATRIZ_TAREA_EDITAR = "<s:url action='irConfiguraMatrizTareaEditar' namespace='/matrizAsignacion'/>";
     
    
    var _ACTION_EXPORT_MATRICES_ASIGNACION = "<s:url action='exportarMatricesAsignacion' namespace='/matrizAsignacion'/>";
      
      var helpMap = new Map();
    
    var itemsPerPage=_NUMROWS;
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("302")%>
    
    _URL_AYUDA = "/backoffice/matrizAsignacion.html";
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/Multiselect.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/matrizAsignacion/matrizAsignacion_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/matrizAsignacion/matrizAsignacionAgregarResponsable.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/matrizAsignacion/matrizAsignacion.js"></script>

</head>
<body>

   <table class="headlines" cellspacing="10" border="0">
        <tr valign="top" border="0">
            <td class="headlines" colspan="2">
                <div id="formBusqueda" />
            </td>
        </tr>
       <tr valign="top" >
           <td class="headlines" colspan="2">
               <div id="gridMatrizAsignacion" border="0" />
           </td>
       </tr>
       <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formDocumentos" />
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
