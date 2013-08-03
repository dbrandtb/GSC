<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla Atributos Arhivos</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap= new Map()</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script language="javascript">


    var _CONTEXT = "${ctx}";

    var _ACTION_AGREGAR_ATRIBUTOS_ARCHIVOS = "<s:url action='agregarAtributoArchivo' namespace='/atributosArchivos'/>";  
    var _ACTION_BUSCAR_ATRIBUTOS_ARCHIVOS = "<s:url action='buscarAtributoArchivo' namespace='//atributosArchivos'/>";
    var _ACTION_OBTENER_REG_ATRIBUTOS_ARCHIVOS = "<s:url action='editarAtributoArchivo' namespace='/atributosArchivos'/>";
    var _ACTION_BORRAR_ATRIBUTOS_ARCHIVOS = "<s:url action='borrarAtributoArchivo' namespace='/atributosArchivos'/>";  
    var _ACTION_EXPORT_ATRIBUTOS_ARCHIVOS = "<s:url action='exportarAtributoArchivo' namespace='/atributosArchivos'/>";  

    var helpMap=new Map();

    var itemsPerPage=10;
    var vistaTipo=1;
    
    <%=session.getAttribute("helpMap")%>
    
    
// COMBOS  - url de los combos - 
    var _ACTION_OBTENER_FORMATO = "<s:url action='obtenerFormatos' namespace='/combos-catbo'/>";           
    var _ACTION_OBTENER_STATUS = "<s:url action='comboEstatusTareasCatBo' namespace='/combos-catbo'/>";
    
      
</script>
 
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap= new Map()</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/atributosArchivos/agregarAtributosArchivos.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/atributosArchivos/agregarAtributosArchivos_stores.js"></script>  
<script type="text/javascript" src="${ctx}/resources/scripts/atributosArchivos/editarAtributosArchivos.js"></script>  
<script type="text/javascript" src="${ctx}/resources/scripts/atributosArchivos/atributosarchivos_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/atributosArchivos/atributosarchivos.js"></script>  


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
               <div id="gridBusqueda" />
           </td>
       </tr>
    </table>
</body>
</html>
