<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Administracion de Usuarios por Modulo</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
  
      var _ACTION_BUSCAR_USUARIOS = "<s:url action='buscarUsuarios' namespace='/administrarUsuariosModulo'/>";       
      var _ACTION_AGREGAR_USUARIO = "<s:url action='agregarUsuarios' namespace='/administrarUsuariosModulo'/>";            
      var _ACTION_COMBO_MODULO = "<s:url action='traerDatosModulos' namespace='/combos-catbo'/>";
      var _ACTION_BORRAR_MODULO = "<s:url action='borrarUsuarios' namespace='/administrarUsuariosModulo'/>";
    
      var _ACTION_EXPORTAR_USUARIO_MODULO = "<s:url action='exportarUsuarioModulo' namespace='/administrarUsuariosModulo'/>";      
      
      var _ACTION_BUSCAR_USUARIOS_ASIGNAR = "<s:url action='buscarUsuariosAsignar' namespace='/administrarUsuariosModulo'/>";  
    
   //  var _ACTION_COMBO_EMPRESAS = "<s:url action='comboClientesCorpBO' namespace='/combos-catbo'/>";          

    var itemsPerPage=10;
    var helpMap= new Map();
    
	_URL_AYUDA = "/backoffice/adminUsuariModulo.html";

</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap= new Map()</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/Multiselect.css"/>
<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/Multiselect.js"></script>



<script type="text/javascript" src="${ctx}/resources/scripts/administrarUsuariosModulo/administrarUsuariosModulo_store.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/administrarUsuariosModulo/administrarUsuariosModulo.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/administrarUsuariosModulo/AdministrarUsuariosModuloEmergente.js"></script>
</head>
<body>

   <table>
        <tr><td><div id="formBusqueda" /></td></tr>
        <tr><td><div id="gridListado" /></td></tr>
         <tr valign="top">
           <td>
               <div id="formMultiselect" />
           </td>
       </tr>
   </table>
</body>
</html>