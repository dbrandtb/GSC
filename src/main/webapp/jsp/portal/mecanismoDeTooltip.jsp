<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Mecanismo de Tooltip</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<%@page import="mx.com.aon.portal.model.UserVO"%>
<%@page import="mx.com.aon.portal.model.BaseObjectVO"%>
<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_MECANISMO_DE_TOOLTIP = "<s:url action='buscarMecanismoDeTooltip' namespace='/mecanismoDeTooltip'/>";
    var _ACTION_AGREGAR_GUARDAR_MECANISMO_DE_TOOLTIP = "<s:url action='agregarGuardarMecanismoDeTooltip' namespace='/mecanismoDeTooltip'/>";
    var _ACTION_OBTENER_REG_MECANISMO_DE_TOOLTIP = "<s:url action='getMecanismoDeTooltipVO' namespace='/mecanismoDeTooltip'/>";
    var _ACTION_COPIAR_MECANISMO_DE_TOOLTIP = "<s:url action='copiarMecanismoDeTooltipVO' namespace='/mecanismoDeTooltip'/>";
    var _ACTION_BORRAR_MECANISMO_DE_TOOLTIP = "<s:url action='borrarMecanismoDeTooltip' namespace='/mecanismoDeTooltip'/>";
    var _ACTION_EXPORTAR_MECANISMO_DE_TOOLTIP = "<s:url action='exportarMecanismoDeTooltip' namespace='/mecanismoDeTooltip'/>";
 
// COMBOS  - url de los combos -
    var _ACTION_OBTENER_TIPO_OBJETO = "<s:url action='obtenerTipoObjeto' namespace='/combos'/>";
    var _ACTION_OBTENER_IDIOMAS = "<s:url action='obtenerIdiomasAyuda' namespace='/combos'/>";
    var helpMap=new Map();

  //   var itemsPerPage=10;
   var itemsPerPage= _NUMROWS;
   
	var lang_code=1;
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("518")%>    
    
	   <% 	UserVO userVO = (UserVO)session.getAttribute("USUARIO");
        BaseObjectVO baseObjectVO;
		if (userVO != null)
			{baseObjectVO = (BaseObjectVO)userVO.getIdioma();
			String codIdioma = baseObjectVO.getValue();
			%>
	
	var IDIOMA_USR = '<%=baseObjectVO.getValue()%>' 
	
     <%
     }
     %>
	
	
	
	
	
	
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/mecanismoDeTooltip/agregarMecanismoDeTooltip.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/mecanismoDeTooltip/editarMecanismoDeTooltip.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/mecanismoDeTooltip/mecanismoDeTooltip.js"></script>
</head>
<body>

   <table class="headlines" cellspacing="10" border=0>
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formBusqueda" />
            </td>
        </tr>
       <tr valign="top">
           <td class="headlines" colspan="2">
               <div id="gridMecanismoDeTooltip" />
           </td>
       </tr>
    </table>
</body>
</html>