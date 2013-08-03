<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Administracion de Equivalencia de Catalogos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resourczees/scripts/util/hashMap.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
   
   var _ACTION_BUSCAR_EQUIVALENCIA_CATALOGOS = "<s:url action='buscarEquivalenciaCatalogos' namespace='/administrarEquivalenciaCatalogos'/>";
   var _ACTION_EXPORTAR_EQUIVALENCIA_CATALOGOS = "<s:url action='exportarAtributosFax' namespace='/administrarEquivalenciaCatalogos'/>";
   var _ACTION_BORRAR_EQUIVALENCIA_CATALOGO = "<s:url action='borrrarEquivalenciaCatalogo' namespace='/administrarEquivalenciaCatalogos'/>";
   var _ACTION_GUARDAR_EQUIVALENCIA_CATALOGOS = "<s:url action='agregarEquivalenciaCatalogo' namespace='/administrarEquivalenciaCatalogos'/>";  
   var _ACTION_ACTUALIZAR_EQUIVALENCIA_CATALOGOS= "<s:url action='agregarEquivalenciaCatalogo' namespace='/administrarEquivalenciaCatalogos'/>";
   
   var _ACTION_IR_ADMINISTRAR_CATALOG_SISTEMA_EXTERNO = "<s:url action='irAdministrarCatalogSistemaExterno' namespace='/administrarEquivalenciaCatalogos'/>";   
   
// Combos url de los combos
   var _ACTION_OBTENER_PAISES = "<s:url action='comboObtienePaises' namespace='/combos'/>";
   var _ACTION_OBTENER_COLUMNAS = "<s:url action='comboObtieneColumnas' namespace='/combos'/>";
   var _ACTION_OBTENER_USO = "<s:url action='comboObtieneUsos' namespace='/combos'/>"; 
   var _ACTION_OBTENER_SISTEMA_EXTERNO = "<s:url action='comboObtieneSistemaExterno' namespace='/combos'/>";
   
   
      var itemsPerPage=10;

    <%=session.getAttribute("helpMap")%>

</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/administrarEquivalenciaCatalogos/administrarEquivalenciaCatalogos.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/administrarEquivalenciaCatalogos/agregarEquivalenciaCatalogos.js"></script>


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
               <div id="gridSecciones" />
           </td>
       </tr>
    </table>
</body>
</html>