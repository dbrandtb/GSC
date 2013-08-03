<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla Mantenimiento de Atributos Variables del Agente</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_ATRIBUTOS_VARIABLES_AGENTE = "<s:url action='buscarAtributosVariablesAgente' namespace='/atributosVariablesAgente'/>";
    var _ACTION_BORRAR_ATRIBUTOS_VARIABLES_AGENTE = "<s:url action='borrarAtributosVariablesAgente' namespace='/atributosVariablesAgente'/>";
    var _ACTION_GUARDAR_ATRIBUTOS_VARIABLES_AGENTE = "<s:url action='guardarAtributosVariablesAgente' namespace='/atributosVariablesAgente'/>";

//combos
    var _ACTION_OBTENER_FORMATO_ATRIBUTOS_VARIABLES_AGENTE = "<s:url action='obtenerComboFormatosCampo' namespace='/combos'/>";
    var _ACTION_OBTENER_LISTA_VALORES_ATRIBUTOS_VARIABLES_AGENTE = "<s:url action='obtenerListaValores' namespace='/combos'/>";

    var itemsPerPage=20;
    <%=session.getAttribute("helpMap")%>
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap = new Map();</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script src="${ctx}/resources/scripts/ValidacionGrilla/Ext.ux.plugins.GridValidator.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/atributosVariablesAgente/atributosVariablesAgente.js"></script>

</head>
<body>

   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formBusqueda"></div>
            </td>
        </tr>
       <tr valign="top">
           <td class="headlines" colspan="2">
               <div id="gridElementos"></div>
           </td>
       </tr>
    </table>
</body>
</html>