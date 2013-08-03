<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Estatus de Casos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>


<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_REASIGNACION_CASO = "<s:url action='buscarReasignacionCaso' namespace='/administracionCasos'/>";
    var _ACTION_GUARDAR_REASIGNACION_CASO = "<s:url action='guardarReasignacionCaso' namespace='/administracionCasos'/>";
    var _ACTION_GUARDAR_MOVIMIENTO_CASO = "<s:url action='guardarMovimientoCaso' namespace='/administracionCasos'/>";
    var _ACTION_REGRESAR_A_CONSULTAR_CASO = "<s:url action='regresarConsultarCaso' namespace='/administracionCasos'/>";
    var _ACTION_REGRESAR_A_CONSULTAR_CASO_DETALLE = "<s:url action='irConsultaCasoDetalle' namespace='/consultarCasosSolicitud'/>";
    
// de lo combos
    var _ACTION_OBTENER_MODULO_TAREAS_CAT_BO = "<s:url action='traerDatosModulos' namespace='/combos-catbo'/>";
	var _ACTION_COMBO_ROLES = "<s:url action='obtenerComboRoles' namespace='/combos-catbo'/>";
//valida el status del caso
   var _VALIDA_SATUS_CASO = "<s:url action='validaStatusCaso' namespace='/administracionCasos'/>";
   

     
    var itemsPerPage=2;
    var vistaTipo=1;
    <%=session.getAttribute("helpMap")%>
    
    var NUMERO_CASO = "<s:property value='nmcaso'/>";
    var FLAG = "<s:property value='flag'/>";
    var CDMATRIZ = "<s:property value='cdmatriz'/>";
    var CDPERSON = "<s:property value='cdperson'/>";
    var CDFORMATOORDEN = "<s:property value='cdformatoorden'/>";
    
    var helpMap= new Map();
    
    
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("806")%>    
    _URL_AYUDA = "/backoffice/reasignacionCaso.html";
    

</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/reasignacionDeCaso/reasignacionDeCaso_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/reasignacionDeCaso/reasignacionDeCaso.js"></script>


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
	           <table>
	        		<tr>
	            		<td>
	                		<div id="grillaIzqUsrPrMdl" />
	            		</td>
	            		<td>
	                		<div id="formBtnChc" />
	            		</td>
	            		<td>
	                		<div id="gridElementos" />
	            		</td>
	        		</tr>
	        		<tr>
	           			<td colspan=3>
	               			<div id="formBtnGrd" />
	           			</td>
       				</tr>
   	          </table>
        	</td>
      </tr>
    </table>
</body>
</html>