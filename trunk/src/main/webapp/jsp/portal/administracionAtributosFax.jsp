<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Administracion de Atributos de Fax</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap= new Map()</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script language="javascript">

    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_ADMINISTRACION_ATRIBUTOS_FAX = "<s:url action='buscarAdministracionAtributosFax' namespace='/administracionAtributosFax'/>";
    var _ACTION_BORRAR_ADMINISTRACION_ATRIBUTOS_FAX = "<s:url action='borrarAdministracionAtributosFax' namespace='/administracionAtributosFax'/>";
    var _ACTION_OBTENER_ADMINISTRACION_ATRIBUTOS_FAX = "<s:url action='getAdministracionAtributosFax' namespace='/administracionAtributosFax'/>";
    var _ACTION_GUARDAR_ADMINISTRACION_ATRIBUTOS_FAX = "<s:url action='guardarAdministracionAtributosFax' namespace='/administracionAtributosFax'/>";
    var _ACTION_EXPORTAR_ADMINISTRACION_ATRIBUTOS_FAX = "<s:url action='exportarAdministracionAtributosFax' namespace='/administracionAtributosFax'/>";
     
    var itemsPerPage=10;
    var vistaTipo=1;
    <%=session.getAttribute("helpMap")%>
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/administracionAtributosFax/administracionAtributosFax_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/administracionAtributosFax/administracionAtributosFax.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/administracionAtributosFax/editarAdministracionAtributosFax_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/administracionAtributosFax/editarAdministracionAtributosFax.js"></script>


</head>
<body>

   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formDocumentos" />
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