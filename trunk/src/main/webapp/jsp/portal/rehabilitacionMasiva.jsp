<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Rehabilitacion Masiva</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_POLIZAS_A_REHABILITAR = "<s:url action='buscarPolizasARehabilitar' namespace='/rehabilitacionMasiva'/>";
    var _ACTION_EXPORTAR = "<s:url action='exportarPolizasARehabilitar' namespace='/rehabilitacionMasiva'/>";
    var _ACTION_REHABILITAR_POLIZA = "<s:url action='rehabilitarPoliza' namespace='/rehabilitacionMasiva'/>";
    var _ACTION_DETALLE_POLIZA = "<s:url action='detallePoliza' namespace='/procesoemision'/>";

	var _COMBO_PRODUCTO= "<s:url action='getComboProducto' namespace='/flujorenovacion'/>";
	var _COMBO_ASEGURADORA = "<s:url action='getComboAsegradora' namespace='/flujorenovacion'/>";

    var _SESSION_PARAMETROS_REGRESAR = null;
    <s:if test="%{#session.containsKey('PARAMETROS_REGRESAR')}">
    	if ( "<s:property value='#session.PARAMETROS_REGRESAR.clicBotonRegresar' />" == "S" ) {
    		_SESSION_PARAMETROS_REGRESAR = {
    			idRegresar: "<s:property value='#session.PARAMETROS_REGRESAR.idRegresar' />",
    			asegurado: "<s:property value='#session.PARAMETROS_REGRESAR.dsAsegurado' />",
    			aseguradora: "<s:property value='#session.PARAMETROS_REGRESAR.dsAseguradora' />",
    			producto: "<s:property value='#session.PARAMETROS_REGRESAR.dsProducto' />",
    			poliza: "<s:property value='#session.PARAMETROS_REGRESAR.nmPoliza' />",
    			inciso: "<s:property value='#session.PARAMETROS_REGRESAR.nmInciso' />"
    		};
    	}
    </s:if>

    // var itemsPerPage=10;
     var itemsPerPage= _NUMROWS;
     var helpMap = new Map();
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("222")%>

      _URL_AYUDA = "/catweb/rehabilitacionMasiva.html"; 
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/pluginCheckColumn.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/rehabilitacionMasiva/rehabilitacionMasiva.js"></script>

</head>
<body>
   <table cellspacing="10">
       <tr valign="top">
           <td>
               <div id="formulario" />
           </td>
       </tr>
       <tr valign="top">
           <td>
               <div id="grillaResultados" />
           </td>
       </tr>
       <tr valign="top">
           <td>
               <div id="formInsideTab" />
           </td>
       </tr>
       <tr valign="top">
           <td>
               <div id="form2InsideTab" />
           </td>
       </tr>
    </table>
   <!-- div id="formularioUpd"></div-->
</body>
</html>