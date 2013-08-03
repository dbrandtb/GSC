<%--
  Created by IntelliJ IDEA.
  User: Edgar P�rez
  Date: 12-jun-2008
  Time: 14:23:30
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

    <title>Administracion de Reportes</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
    <script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
    <%--<script type="text/javascript" src="${ctx}/resources/extjs/ext-all-debug.js"></script>--%>
    <script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
    <%--<script type="text/javascript" src="${ctx}/resources/scripts/util/PagingToolbar.js"></script>--%>
    <script type="text/javascript" src="${ctx}/resources/scripts/reportes/reportesPrincipal.js"></script>

    <script type="text/javascript" language="javascript">
        var _ACTION_BUSCAR_REPORTES = "<s:url action='obtenerReportes' namespace='/reportes'/>";
        var _ACTION_EXPORT="<s:url action='exportPlanPrincipal' namespace='/reportes'/>";
        var itemsPerPage= 50;
        var helpMap = new Map();
<%----%>
    </script>

</head>
<body>

<table cellspacing="1">
    <tr valign="top">
        <td>
            <div id="formBusqueda"></div>
        </td>
    </tr>
    <tr valign="top">
        <td>
            <div id="gridElementos"></div>
        </td>
    </tr>
</table>

</body>
</html>