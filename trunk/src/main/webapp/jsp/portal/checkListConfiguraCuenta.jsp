<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Checklist de la configuración de la cuenta</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script language="javascript">
	var _CONTEXT = "${ctx}";

	var _ACTION_BUSCAR_CHECKLIST_CONFIGURA_CUENTA = "<s:url action='buscarConfiguraCuenta' namespace='/checklistConfiguraCuenta'/>";
	//var _ACTION_IR_AGREGAR_CHECKLIST_CONFIGURA_CUENTA = "<s:url action='irAgregarConfiguraCuenta' namespace='/checklistConfiguraCuenta'/>";
	//var _ACTION_IR_EDITAR_CHECKLIST_CONFIGURA_CUENTA = "<s:url action='irEditarConfiguraCuenta' namespace='/checklistConfiguraCuenta'/>";

	var _ACTION_IR_AGREGAR_CHECKLIST_CONFIGURA_CUENTA = "<s:url action='irAgregarCheckListCuenta' namespace='/checkListCuenta'/>";
	var _ACTION_IR_EDITAR_CHECKLIST_CONFIGURA_CUENTA = "<s:url action='irEditarCheckListCuenta' namespace='/checkListCuenta'/>";

	var _ACTION_BORRAR_CHECKLIST_CONFIGURA_CUENTA = "<s:url action='borrarConfiguraCuenta' namespace='/checklistConfiguraCuenta'/>";
	var _ACTION_OBTENER_CLIENTE_CORPO = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
	var _ACTION_COPIAR_CONFIGURA_CUENTA = "<s:url action='copiarConfiguraCuenta' namespace='/checklistConfiguraCuenta'/>";
    var helpMap = new Map();

    // var itemsPerPage= 20;
    var itemsPerPage= _NUMROWS;
    
    var FLAG = "<s:property value='flag'/>";
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("10")%>
   
     _URL_AYUDA = "/catweb/checklistConfCuenta.html";
    
</script>
<!--[if lte IE 6]>
<link href="${ctx}/resources/css/template_ie.css" rel="stylesheet" type="text/css" />
<![endif]-->
<style type="text/css">
    #logo {
    	background: url(${ctx}/resources/images/login/logo.png) no-repeat;
        position: relative;
        top: 0px;
        left: 0px;
        display: block;
        width:200px;
        height: 66px;
    }
    #message {
        position: relative;
        top: 5px;
        left: 15px;
        bottom: 5px;
        width: 280px;
    }
    #messageError {
    	color: #840C2C;
    }
    
</style>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/checkListCuenta/checkListConfiguraCuentaCopiar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/checkListCuenta/checkListConfiguraCuentaBorrar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/checkListCuenta/checkListConfiguraCuenta.js"></script>


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
               <div id="gridConfiguraCuenta" />
           </td>
       </tr>
    </table>

</body>
</html>