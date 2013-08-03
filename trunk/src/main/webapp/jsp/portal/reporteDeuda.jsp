<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Administracion de Equivalencia de Catalogos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resourczees/scripts/util/hashMap.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
   
   var _ACTION_BUSCAR_REPORTE_DEUDA = "<s:url action='buscarReporteDeuda' namespace='/reporteDeuda'/>";
   var _ACTION_EXPORTAR_REPORTE_DEUDA = "<s:url action='exportarReporteDeuda' namespace='/reporteDeuda'/>"
     
// Combos url de los combos
   var _ACTION_COMBO_ASEGURADO = "<s:url action='confAlertasAutoClientes' namespace='/combos'/>";
   var _ACTION_COMBO_ASEGURADORA = "<s:url action='obtenerAseguradorasXAsegurado' namespace='/combos'/>";

   var _ACTION_COMBO_PRODUCTO = "<s:url action='obtenerProductosDescuentos2' namespace='/combos'/>";
   var _ACTION_COMBO_POLIZA = "<s:url action='comboObtienePoliza' namespace='/combos'/>";
   
   
      var itemsPerPage=10;

    <%=session.getAttribute("helpMap")%>

</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/reporteDeuda/reporteDeuda.js"></script>



</head>
<body>

   <table cellspacing="10">
        <tr valign="top">
            <td colspan="2">
                <div id="formBusqueda" />
            </td>
        </tr>
       <tr valign="top">
           <td colspan="2">
               <div id="grid" />
           </td>
       </tr>
    </table>
</body>
</html>