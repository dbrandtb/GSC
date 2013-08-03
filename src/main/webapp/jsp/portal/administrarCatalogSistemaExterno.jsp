<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Administracion </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap= new Map()</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script language="javascript">

    var _CONTEXT = "${ctx}";
     
    var _ACTION_BUSCAR_TCATAEXT = "<s:url action='buscarDatosCataex' namespace='/administrarCatalogSistemaExterno'/>";
    
    var _ACTION_GUARDAR_TCATAEXT = "<s:url action='guardarDatosTcataex' namespace='/administrarCatalogSistemaExterno'/>";
    
    var _BORRA_REGISTRO = "<s:url action='borraRegTcataex' namespace='/administrarCatalogSistemaExterno'/>";
    
    var _EXPORTAR_TCATAEX = "<s:url action= 'exportarTcataex' namespace= '/administrarCatalogSistemaExterno'/>";
    
    var _ACTION_IR_ADMINISTRA_EQUIVALENCIA_CATALOGO = "<s:url action= 'regresar' namespace= '/administrarCatalogSistemaExterno'/>";
    
    var _CODPAIS = "<s:property value='cdPais'/>";
    var _NUMTABLA = "<s:property value='nmTabla'/>";
    
     var _CODSISTEMA = "<s:property value='cdSistema'/>";
     var _CDTABLA = "<s:property value='cdTablaExt'/>";
     var _OTVALOREXT = "<s:property value='otvalorExt'/>";
     var _COLUMNA = "<s:property value='nmColumna'/>";
     
    // var _SIST_EXTERNO = "<s:property value='sistExterno'/>";
    
    
    var itemsPerPage=10;
   //  var vistaTipo=1;
    <%=session.getAttribute("helpMap")%>
    
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/administrarCatalogSistemaExterno/administrarCatalogSistemExterno_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/administrarCatalogSistemaExterno/administrarCatalogSistemaExterno.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/administrarCatalogSistemaExterno/administrar_Catalog_SistExterno_Agregar.js"></script>



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
               <div id="gridNotificaciones" />
           </td>
       </tr>
          
     
      
    </table>
</body>
</html>