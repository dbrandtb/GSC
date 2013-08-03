<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla Polizas a Cancelar</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
    
    var _ACTION_BUSCAR_POLIZAS_A_CANCELAR="<s:url action='buscarPolizasCancelar' namespace='/polizas'/>";
    var _ACTION_GUARDAR_POLIZAS_A_CANCELAR="<s:url action='guardarPolizas' namespace='/polizas'/>";
    
    var _ACTION_COMBO_TIPO_CANCELACION="<s:url action='obtenerComboTipoActividad' namespace='/combos'/>";
    
    var _ACTION_EXPORTAR_POLIZAS_A_CANCELAR="<s:url action='exportPolizasACancelar' namespace='/polizas'/>";

    var itemsPerPage=10;
    var vistaTipo=1;
    <%=session.getAttribute("helpMap")%>
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap= new Map()</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/pluginCheckColumn.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/polizasCancelar/polizasCancelar_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/polizasCancelar/polizasCancelar.js"></script>



</head>
<body>

   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formBusqueda" />
            </td>
        </tr>
       <tr valign="top">
           <td class="headlines" colspan="2">
               <div id="gridElementos" />
           </td>
       </tr>
    </table>
</body>
</html>