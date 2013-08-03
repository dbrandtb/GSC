<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
    <%=session.getAttribute("helpMap")%>
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/portal/login/login.js"></script>


</head>
<body>

   <table class="headlines" cellspacing="10">
        <tr valign="top">
           <td><img src= "${ctx}/resources/images/aon/login.jpg" width="400"/>&nbsp;</td>
           <td class="headlines" colspan="1">
               <div id="formLogin"></div>
               <!--  <img src="${ctx}/resources/images/aon/titLogin3.jpg" width="150" height="300" />-->
           </td>
        </tr>
        <tr> 
            
		    <td colspan="5" class="textologin"><br>
            CATweb es un modelo integral que ofrece servicio r&aacute;pido, eficiente 
            y de calidad, las 24 hrs. del d&iacute;a todos los d&iacute;as del 
            a&ntilde;o en cualquier parte del mundo.<br> <br>AON ha creado este modelo de servicio, para satisfacer las necesidades de 
			sus clientes, apoyado por tres &aacute;reas: CATweb centro de atenci&oacute;n en l&iacute;nea, CAT Centro de atenci&oacute;n telef&oacute;nica y Back Office un &aacute;rea de trabajo dedicada a dar soporte a las operaciones.<br><br>
            <br><br>  
		    </td>
		</tr>
    </table>
</body>
</html>