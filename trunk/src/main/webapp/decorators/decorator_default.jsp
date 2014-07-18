<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!-- pagina para decorator "default": -->
<!DOCTYPE html>
<html>
    <head>
    
    	<!-- TENER CUIDADO CON EL ORDEN EN QUE SE INCLUYEN LOS SIGUIENTES FICHEROS -->
    
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <link href="${ctx}/resources/extjs4/resources/my-custom-theme/my-custom-theme-all.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/resources/extjs4/extra-custom-theme.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="${ctx}/resources/extjs4/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="${ctx}/resources/extjs4/ext-all.js"></script>
        <script type="text/javascript" src="${ctx}/resources/extjs4/locale/ext-lang-es.js"></script>
        <%@ include file="/resources/jsp-script/util/catalogos.jsp"%>
        <script type="text/javascript" src="${ctx}/resources/extjs4/base_extjs4.js?${now}"></script>
        <script type="text/javascript" src="${ctx}/resources/scripts/util/extjs4_utils.js?${now}"></script>
        <decorator:head />
        <!-- EL custom_overrides.js DEBE SER INCLUIDO DESPUES DE LOS SCRIPTS PROPIOS DE CADA JSP -->
        <script type="text/javascript" src="${ctx}/resources/scripts/util/custom_overrides.js?${now}"></script>
    </head>
    <body>
        <decorator:body />
    </body>
</html>