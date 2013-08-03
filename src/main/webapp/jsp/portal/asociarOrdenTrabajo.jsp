<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Asociar Formatos de Orden de Trabajo</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_OBTENER_FORMATO_X_CUENTA = "<s:url action='obtenerFormatoxCuenta' namespace='/asociarOrdenTrabajo'/>";
    var _ACTION_GUARDAR_ASOCIAR_ORDEN_TRABAJO = "<s:url action='guardarAsociacionOrdenTrabajo' namespace='/asociarOrdenTrabajo'/>";
    var itemsPerPage=10;
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/asociarOrdenTrabajo/asociarOrdenTrabajo.js"></script>
</head>
<body>

   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formAFC" />
            </td>
        </tr>
    </table>
</body>
</html>