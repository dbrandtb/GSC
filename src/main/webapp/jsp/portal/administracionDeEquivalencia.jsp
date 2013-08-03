<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla Administracion de Equivalencia</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";


    var _ACTION_GUARDAR_TABLA= "<s:url action='guardarTablaEquivalencia' namespace='/administracionEquivalencia'/>";
    var _ACTION_CAT_EXTERNO= "<s:url action='reporteTablaEquivalencia' namespace='/administracionEquivalencia'/>";
    
    var _ACTION_REPORTE_TABLA= "<s:url action='exportaReporteTablasNoConciliadas' namespace='/administracionEquivalencia'/>";


  //  var _ACTION_OBTIENE_EXTERNO= "<s:url action='obtieneCatExterno' namespace='/administracionEquivalencia'/>";
    var _ACTION_OBTIENE_CATALOGO= "<s:url action='obtieneTablaEquivalencia' namespace='/administracionEquivalencia'/>";
    var _ACTION_OBTIENE_TABLA= "<s:url action='obtieneEquivalenciaTabla' namespace='/administracionEquivalencia'/>";

    
    var _ACTION_OBTENER_PAIS= "<s:url action='obtenerPais' namespace='/combos'/>";
    var _ACTION_COMBO_CATA_AON= "<s:url action='obtenerCatalogoAon' namespace='/combos'/>";
//    var _ACTION_COMBO_CATA_EXT= "<s:url action='obtenerCatalogoExterno' namespace='/combos'/>";
    var _ACTION_COMBO_COD_SIST= "<s:url action='comboObtieneSistemaExterno' namespace='/combos'/>";
    
// de lo combos


    var helpMap= new Map();
    var itemsPerPage=10;
    var vistaTipo=1;
    <%=session.getAttribute("helpMap")%>
    
     <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("825")%>
    
    
</script>


<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include> 
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/AdministracionEquivalencia/administracionDeEquivalencia.js"></script> 


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
				<!--   	<tr>
	            		<td rowspan=3>
	                		<div id="gridIzq" />   
	            		</td>
	        		</tr> --> 
					<tr>
						<td>
							<div id="gridDer" />
						</td>
					</tr>
					<tr>
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