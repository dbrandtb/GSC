<%--
  Created by IntelliJ IDEA.
  User: ricardo Possamai
  Date: 19/06/2008
  Time: 10:26:48 AM
  To change this template use File | Settings | File Templates.
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>Administracion de Productos</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
    <script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
    <script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
    <script type="text/javascript" src="${ctx}/resources/scripts/util/PagingToolbar.js"></script>
    <script type="text/javascript" src="${ctx}/resources/scripts/reportes/reportesAdministracionProductos.js"></script>
    <script type="text/javascript" language="javascript">

        var _REGRESAR = "${ctx}/reportes/reportesPrincipal.action";
        var _ACTION_BUSCAR_PRODUCTOS = "<s:url action='obtenerProductos' namespace='/reportes'/>";
        var _ACTION_CARGAR_COMBO = "<s:url action='obtenerComboProducto' namespace='/reportes'/>";
        var _ACTION_EXPORT = "<s:url action='exportPlanProductos' namespace='/reportes'/>";
        var codRep = '<s:property value="cdReporte" />';
        var descripcionAtri = '<s:property value="dsReporte" />';
        var ejecutableAtri = '<s:property value="nmReporte" />';
        var itemsPerPage = 50;
        var helpMap = new Map();
    </script>

</head>
<body>
</body>
</html>
