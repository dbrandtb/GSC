<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!-- pagina para decorator "clean": -->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <link href="${ctx}/resources/extjs4/resources/my-custom-theme/my-custom-theme-all.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/resources/extjs4/extra-custom-theme.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="${ctx}/resources/extjs4/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="${ctx}/resources/extjs4/ext-all-debug.js"></script>
        <script type="text/javascript" src="${ctx}/resources/extjs4/locale/ext-lang-es.js"></script>
        <script type="text/javascript" src="${ctx}/resources/extjs4/base_extjs4.js"></script>
        <decorator:head />
    </head>
    <body>
        <decorator:body />
    </body>
</html>