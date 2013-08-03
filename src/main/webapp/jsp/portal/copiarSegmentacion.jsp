<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<title>Copiar Segmentacion</title>

<meta http-equiv= "content type" content="text/html; charset utf=8">
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
  var _CONTEXT = "${ctx}";

  //  var _ACTION_OBTENER_COPIA_CLIENTES = "<s:url action='buscarAsignacionEncuesta' namespace='/configuracionEncuestas'/>";
    var _ACTION_COPIAR_SEGMENTA      ="<s:url action= 'copiarSegmentacion' namespace= '/copiarSegmentacion'/>";

    var helpMap = new Map();
    
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("")%>
    
    _URL_AYUDA = "";
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>

<script type="text/javascript" src="${ctx}/resources/scripts/portal/segmentacion/copySegmentacion.js"></script>

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
               <div id="gridSegmentacion" />
           </td>
       </tr>
    </table>
</body>
</html>