<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla Funcionalidades Privilegios</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
   
    var _ACTION_BUSCAR_FUNCIONALIDADES_PRIVILEGIOS="<s:url action='buscarFuncionalidadesPrivilegios' namespace='/funcionalidadesPrivilegios'/>";
    var _ACTION_BUSCAR_FUNCIONALIDADES_EXPORT="<s:url action='exportarFuncionalidades' namespace='/funcionalidadesPrivilegios'/>";
    var _ACTION_BORRAR_FUNCIONALIDAD_PRIVILEGIO="<s:url action='borrarFuncionalidadPrivilegio' namespace='/funcionalidadesPrivilegios'/>";
    var _ACTION_IR_CONFIGURA_FUNCIONALIDADES = "<s:url action='irConfiguraFuncionalidadesPrivilegios' namespace='/funcionalidadesPrivilegios'/>";
    var _ACTION_IR_CONFIGURA_FUNCIONALIDADES_EDITAR = "<s:url action='irConfiguraFuncionalidadesPrivilegiosEditar' namespace='/funcionalidadesPrivilegios'/>";
     
    var _ACTION_COMBO_FUNCIONALIDAD = "<s:url action='obtenerComboFuncionalidades' namespace='/funcionalidadesPrivilegios'/>";     
       
    var itemsPerPage=20;
    var vistaTipo=1;
    <%=session.getAttribute("helpMap")%>
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap= new Map()</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/funcionalidadesPrivilegios/funcionalidadesPrivilegios_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/funcionalidadesPrivilegios/funcionalidadesPrivilegios.js"></script>



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

