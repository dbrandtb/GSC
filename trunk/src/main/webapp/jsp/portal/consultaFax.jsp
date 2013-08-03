<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Consulta de Fax</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
  
    var _ACTION_BUSCAR_FAX = "<s:url action='buscarFax' namespace='/consultaFax'/>";
    var _ACTION_BORRAR_DETALLE_FAX = "<s:url action='borrarDetalleFax' namespace='/consultaFax'/>";
    var _ACTION_EXPORTAR_FAX = "<s:url action='exportarFax' namespace='/consultaFax'/>";
    
    var _IR_A_ADMINISTRAR_FAX = "<s:url action='irAdministracionFax' namespace='/administracionFax'/>";
    
    var _IR_A_ADMINISTRAR_FAX_EDITAR = "<s:url action='irAdministracionFaxEditar' namespace='/administracionFax'/>";
    
     var _VAR = "<s:property value='cdVariable'/>";
     
    
    
   //  var itemsPerPage=10;
   
    var itemsPerPage = _NUMROWS;
   
    var helpMap= new Map();
    
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("813")%>
    
    _URL_AYUDA = "/backoffice/consultaFax.html"; 
    
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/consultarFax/consultaFax_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/consultarFax/consultaFax.js"></script>

</head>
<body>

   <table>
        <tr><td><div id="formBusqueda" /></td></tr>
        <tr><td><div id="gridListado" /></td></tr>
   </table>
</body>
</html>