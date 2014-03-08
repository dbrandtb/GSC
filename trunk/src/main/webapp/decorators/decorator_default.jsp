<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<!-- pagina para decorator "default": -->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <link href="${ctx}/resources/extjs4/resources/my-custom-theme/my-custom-theme-all.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/resources/extjs4/extra-custom-theme.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="${ctx}/resources/extjs4/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="${ctx}/resources/extjs4/ext-all.js"></script>
        <script type="text/javascript" src="${ctx}/resources/extjs4/locale/ext-lang-es.js"></script>
        <%@ include file="/resources/jsp-script/util/catalogos.jsp"%>
        <script type="text/javascript" src="${ctx}/resources/extjs4/base_extjs4.js"></script>
        <script type="text/javascript" src="${ctx}/resources/scripts/util/extjs4_utils.js"></script>
        <decorator:head />
        <script type="text/javascript" src="${ctx}/resources/scripts/util/custom_overrides.js"></script>
    </head>
    <body>
        <decorator:body />
    </body>
</html>