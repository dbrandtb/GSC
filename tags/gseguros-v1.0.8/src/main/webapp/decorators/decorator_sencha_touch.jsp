<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!-- pagina para decorator "default": -->
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${ctx}/resources/touch-2.3.1/resources/css/sencha-touch.css"/>
        <script src="${ctx}/resources/extjs4/jquery-1.10.2.min.js"></script>
        <script src="${ctx}/resources/touch-2.3.1/sencha-touch-all.js"></script>
        <script src="${ctx}/resources/touch-2.3.1/src/locale/ext-lang-es.js"></script>
        <script src="${ctx}/js/peritaje/utils/commons.js"></script>
        <decorator:head />
    </head>
    <body>
        <decorator:body />
    </body>
</html>