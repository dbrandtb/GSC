<%--
  Created by IntelliJ IDEA.
  User: Ricardo Possamai
  Date: 10/07/2008
  Time: 12:00:25 PM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>Asociar Plantilla a Reporte</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
    <script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
    <script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
    <%-- <script type="text/javascript" src="${ctx}/resources/scripts/util/PagingToolbar.js"></script>--%>
    <script type="text/javascript" src="${ctx}/resources/scripts/reportes/reportesAsociarPlantillas.js"></script>


    <script type="text/javascript" language="javascript">
        var _PRINCIPAL = "${ctx}/portal.action";
        var _ACTION_BUSCAR = "<s:url action='obtenerAsociarPlantillas' namespace='/reportes'/>";
        var _ACTION_EXPORT = "<s:url action='exportPlantillaAsociar' namespace='/reportes'/>";
        //var itemsPerPage= 100;
        var helpMap = new Map();
        <%--
        var codigoPlantilla='<s:property value="cdPlantilla" />';
        var descripcionPlantilla='<s:property value="dsPlantilla" />';
        --%>

    </script>

</head>
<body>
<table cellspacing="1">
    <tr valign="top">
        <td>
            <div id="formBusqueda"></div>
        </td>
    </tr>
    <td>
        <div id="gridElementos"></div>
    </td>

</table>
</body>
</html>