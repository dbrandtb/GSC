<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
	    <title>ICE ${ctx}</title>
	    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
	    <script language="javascript">
	        var _CONTEXT = "${ctx}";
	        var _URL_VALIDA_USUARIO = '<s:url namespace="/seguridad" action="autenticaUsuario" />';
	        var _URL_VALIDA_EXISTE_USUARIO = '<s:url namespace="/seguridad" action="existeUsuarioLDAP" />';
	        
	        var _MODO_AGREGAR_USUARIOS_A_LDAP = <s:text name="login.modo.agregar.usuarios.ldap"/>;
	        
	    </script>
	    <link href="${ctx}/resources/extjs4/resources/my-custom-theme/my-custom-theme-all.css" rel="stylesheet" type="text/css" />
	    <link href="${ctx}/resources/extjs4/extra-custom-theme.css" rel="stylesheet" type="text/css" />
	    <script type="text/javascript" src="${ctx}/resources/extjs4/ext-all.js"></script>
	    <script type="text/javascript" src="${ctx}/resources/extjs4/locale/ext-lang-es.js"></script>
	    <script type="text/javascript" src="${ctx}/resources/scripts/portal/login/login.js"></script>

    </head>
    <body>

        <table class="headlines" cellspacing="10">
	        <tr valign="top">
	           <td><img src= "${ctx}/resources/images/aon/login.jpg" width="400"/>&nbsp;</td>
	           <td class="headlines" colspan="1">
	               <div id="formLogin"></div>
	           </td>
	        </tr>       
	        <tr> 
			    <td colspan="5" class="textologin">
                    <br>INFORMACI&Oacute;N DE GENERAL DE SEGUROS.<br><br><br><br>  
			    </td>
			</tr>
	    </table>
	</body>
</html>