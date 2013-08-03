<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Consultar Asignacion de Encuesta </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="javascript">
	var _CONTEXT = "${ctx}";
	var _ACTION_BUSCAR_ASIGNACION_ENCUESTA = "<s:url action='buscarAsignacionEncuesta' namespace='/consultarAsigEncuesta'/>";
	var _ACTION_GUARDAR_ASIGNACION_ENCUESTA = "<s:url action='guardarAsignacionEncuesta' namespace='/consultarAsigEncuesta'/>";
	var _ACTION_ELIMINA_ASIGNACION_ENCUESTA = "<s:url action='borrarAsignacionEncuesta' namespace='/consultarAsigEncuesta'/>";
	var _ACTION_ASIGNACION_ENCUESTA_EXPORTAR = "<s:url action='exportarAsignacionEncuesta' namespace='/consultarAsigEncuesta'/>";


    var _ACTION_COMBO_USUARIOS = "<s:url action='comboUsuarioResponsable' namespace='/combos'/>";
    
    var itemsPerPage=20;
    /*<%=session.getAttribute("helpMap")%>*/
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("816")%>
    
      
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript">var helpMap= new Map()</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/portal/consultarAsigEncuesta/consultarAsigEncuesta.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/consultarAsigEncuesta/agrega.js"></script>
</head>
<body>
                    <table>
                        <tr>
                            <td id="formulario"></td>
                        </tr>
				        <tr valign="top">
				            <td colspan="2">
				                <div id="gridConfiguraAlertas" />
				            </td>
				        </tr>
				        <tr>
				        	<td>
				        		<div id="accordion-div"></div>
				        	</td>
				        </tr>
                    </table>

</body>
</html>