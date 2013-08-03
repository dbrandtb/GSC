<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Avisos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<script language="javascript">
    var _CONTEXT = "${ctx}";
   
    var _OBTENER_MENSAJES_ALERTA = "<s:url action='buscarMensajesAlerta' namespace='/mecanismoAlerta'/>";
    var itemsPerPage=1;
    /*<%=session.getAttribute("helpMap")%>*/
</script>
  
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/mecanismoAlerta/mecanismoAlertaJsp.js"></script>

</head>
<body>

   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formMensajes" />
            </td>
            
        </tr>
        <tr valign="top">
           <td class="headlines" colspan="2">
               <div id="gridMensajes" />
           </td>
       </tr>
    </table>
</body>
</html>