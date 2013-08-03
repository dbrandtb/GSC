
<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Estatus de Casos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@page import="mx.com.aon.portal.model.UserVO"%>
<%@page import="mx.com.aon.portal.model.BaseObjectVO"%>


<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap= new Map()</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/Multiselect.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_REASIGNACION_CASO_USUARIO = "<s:url action='buscarReasignacionCasoUsr' namespace='/administracionCasos'/>";
    var _ACTION_BUSCAR_REASIGNACION_CASO_USUARIO_RSPNSBL = "<s:url action='buscarReasignacionCasoUsrRspnsbl' namespace='/administracionCasos'/>";
    var _ACTION_GUARDAR_REASIGNACION_CASO_USUARIO = "<s:url action='guardarReasignacionCasoUsr' namespace='/administracionCasos'/>";
    var _ACTION_REGRESAR_A_CONFIGURAR_MATRIZ_TAREA = "<s:url action='irConfiguraMatrizTareaEditar' namespace='/matrizAsignacion'/>";
  
    // valida status
      var VALIDA_SATUS_CASO = "<s:url action='validaStatusCaso' namespace='/compraTiempo'/>";
 
     var itemsPerPage=10;
     var helpMap= new Map();
    var vistaTipo=1;
 
    
    var COD_USUARIO = "<s:property value='cdusuari'/>";
    var DES_USUARIO = "<s:property value='dsusuari'/>";
    var CODMATRIZ = "<s:property value='cdmatriz'/>";        //se agrego
    
    
    <%
	    UserVO userVO = (UserVO)session.getAttribute("USUARIO");
		if (userVO != null)
		{
			String userMov = userVO.getUser();
	%>
	
	var CDUSERMOV = '<%=userMov%>' 
	
     <%
     }
     %>
    
   <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("105")%>  
    
      _URL_AYUDA = "/backoffice/matrizReasignacionCaso.html";
</script>




<script type="text/javascript" src="${ctx}/resources/scripts/reasignacionDeCasoUsr/reasignacionDeCasoUsr_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/reasignacionDeCasoUsr/reasignacionDeCasoUsr.js"></script>





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
	            		<td rowspan=3>
	                		<div id="gridCasoUsr" />
	            		</td>
	            		<td rowspan=3>
	                		<div id="formBtnChc" />
	            		</td>
	            		<td>
	                		<div id="formBusquedaDer" />
	            		</td>
	        		</tr>
					<tr>
						<td>
							<div id="gridCasoUsrRspnsbl" />
						</td>
					</tr>
					<tr>
						<td>
							<div id="gridCasoAsignados" />
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