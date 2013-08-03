
<%--
  Created by IntelliJ IDEA.
  User: Ricardo Possamai
  Date: 12/08/2008
  Time: 10:12:10 AM
  To change this template use File | Settings | File Templates.
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

    <title>Administración de Atributos</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
     <script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
    <script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
    <script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
    <script type="text/javascript" src="${ctx}/resources/scripts/util/PagingToolbar.js"></script>
    <script type="text/javascript" src="${ctx}/resources/scripts/reportes/reportesAdministracionPlantillasAtributos.js"></script>

    <script type="text/javascript" language="javascript">
        var _REGRESAR = "${ctx}/reportes/reportesAdministracionPlantillas.action";
        var _ACTION_EXPORT = "<s:url action='exportPlantillaAtributos' namespace='/reportes'/>";
        var _ACTION_CARGAR_COMBO = "<s:url action='obtenerComboFormato' namespace='/reportes'/>";
        var _ACTION_CARGAR_COMBO_APOYO = "<s:url action='obtenerTapoyo' namespace='/reportes'/>";
        var codigoPlantilla='<s:property value="cdPlantilla" />';
        var descripcionPlantilla='<s:property value="dsPlantilla" />';
        var itemsPerPage= 50;
        var helpMap = new Map();
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