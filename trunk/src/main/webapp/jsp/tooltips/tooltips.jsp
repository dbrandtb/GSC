<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Prueba de Tooltips</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script language="javascript">
    var itemsPerPage=10;

<%=session.getAttribute("helpMap")%>
 _URL_AYUDA = "/catweb/mecanismoDeTooltip.html";
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/tooltips/tooltips.js"></script>
<!-- script type="text/javascript" src="${ctx}/resources/scripts/util/abmtooltips.js"></script-->

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