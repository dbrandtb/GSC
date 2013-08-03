<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla Generacion Claves</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
    
    var _ACTION_BUSCAR_VALORES = "<s:url action='buscarValores' namespace='/generacionClaves'/>";
    // var _ACTION_BORRAR_ATRIBUTOS_VARIABLES_AGENTE = "<s:url action='borrarAtributosVariablesAgente' namespace='/generacionClaves'/>";
    var _ACTION_GUARDAR_VALORES = "<s:url action='guardarValores' namespace='/generacionClaves'/>";

//combos
    var _ACTION_OBTENER_ALGORITMOS = "<s:url action='comboAlgoritmos' namespace='/combos'/>";

    var itemsPerPage=10;
    <%=session.getAttribute("helpMap")%>
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap = new Map();</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/generacionClaves/generacionClaves.js"></script>

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