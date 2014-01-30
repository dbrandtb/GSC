<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Inicio</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script language="javascript">
    var _CONTEXT = "${ctx}";    
</script>
<link href="${ctx}/resources/extjs4/resources/my-custom-theme/my-custom-theme-all.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/resources/extjs4/extra-custom-theme.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/resources/extjs4/ext-all-debug.js"></script>
    <script type="text/javascript" src="${ctx}/resources/extjs4/locale/ext-lang-es.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/login/login.js"></script>

</head>
<body>

   <><table class="headlines" cellspacing="10">
        <tr valign="top">
           <td><img src= "${ctx}/resources/images/aon/login.jpg" width="400"/>&nbsp;</td>
           <td class="headlines" colspan="1">
               <div id="formLogin"></div>
               <!--  <img src="${ctx}/resources/images/aon/titLogin3.jpg" width="150" height="300" />-->
           </td>
        </tr>       
        <tr> 
            
		    <td colspan="5" class="textologin"><br>
            INFORMACI&Oacute;N DE GENERAL DE SEGUROS.<br><br>
            <br><br>  
		    </td>
		</tr>
    </table>
</body>
</html>