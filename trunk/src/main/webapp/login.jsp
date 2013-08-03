<%@ page language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<c:set var="datePattern"><fmt:message key="date.format"/></c:set>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- Estilos para extJs -->
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/xtheme-gray.css" />
<script language="javascript">
	var _CONTEXT = "${ctx}";
	var _ACTION = "<s:url action='autenticaUsuario'/>";
	var _REDIRECT = "<s:url action='seleccionaRolCliente'/>";
</script>
<!--[if lte IE 6]>
<link href="${ctx}/resources/css/template_ie.css" rel="stylesheet" type="text/css" />
<![endif]-->
<style type="text/css">
    #logo {
    	background: url(${ctx}/resources/images/login/logo.png) no-repeat;
        position: relative;
        top: 0px;
        left: 0px;
        display: block;
        width:200px;
        height: 66px;
    }
    #message {
        position: relative;
        top: 5px;
        left: 15px;
        bottom: 5px;
        width: 280px;
    }
    #messageError {
    	color: #840C2C;
    }
    
</style>
<script type="text/javascript" src="${ctx}/resources/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctx}/resources/extjs/ext-all.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/login.js"></script>
</head>
<body>
    <br/><br/><br/><br/><br/>
    <div id="main" align="center">
        <div style="width:600px">
            <div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>
                <div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">
                    <table style="width:100%"/>
                        <tr>
                            <td align="left">
                                <span id="logo"></span>
                                <div id="message" align="center"></div>
                            </td>
                            <td>
                                &nbsp;
                                <div id="cuerpo" align="right"></div>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td id="messageError" align="center">&nbsp;</td>
                        </tr>
                    </table>
                </div></div></div>
            <div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>
        </div>
    </div>
</body>
</html>
