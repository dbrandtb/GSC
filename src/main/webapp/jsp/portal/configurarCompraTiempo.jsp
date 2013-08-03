<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Configurar Compra de Tiempo</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap= new Map()</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
    
   // var _ACTION_BUSCAR_CALENDARIO = "<s:url action='traerCalendario' namespace='/calendario'/>";
     var _ACTION_AGREGAR_COMPRA_TIEMPO = "<s:url action='agregarCompraTiempo' namespace='/configurarCompraTiempo'/>";
     var _ACTION_GUARDAR_COMPRA_TIEMPO = "<s:url action='guardarCompraTiempo' namespace='/configurarCompraTiempo'/>";
     var _ACTION_BORRAR_COMPRA_TIEMPO = "<s:url action='borrarCompraTiempo' namespace='/configurarCompraTiempo'/>";
     var _ACTION_OBTENER_COMPRA_TIEMPO = "<s:url action='obtieneConfigCompraTiempo' namespace='/configurarCompraTiempo'/>";
     
// COMBOS  - url de los combos -

    var _ACTION_OBTENER_UNIDAD = "<s:url action='obtenerUnidad' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_NIVEL = "<s:url action='obtenerNivel' namespace='/combos-catbo'/>";
          
    var helpMap=new Map();
     
    var itemsPerPage=10;
        
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/configurarCompraTiempo/configurarCompraTiempo.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/configurarCompraTiempo/configurarCompraTiempo_stores.js"></script>


</head>

<body>

   <table>
        <tr valign="top">
            <td>
                <div id="formDocumentos" />
            </td>
        </tr>
   </table>
</body>
</html>