<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Consulta de Cliente</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
  
    var _ACTION_BUSCAR_GUION_LLAMADAS = "<s:url action='buscarGuionLlamadas' namespace='/guionLlamadas'/>";
    var _ACTION_BUSCAR_DIALOGO_LLAMADA = "<s:url action='buscarDialogoLlamadas' namespace='/guionLlamadas'/>";
    var _ACTION_GUARDAR_GUION_LLAMADA = "<s:url action='guardarGuionLlamadas' namespace='/guionLlamadas'/>";
    var _ACTION_GUARDAR_DIALOGO_LLAMADA =  "<s:url action='guardarDialogoLlamadas' namespace='/guionLlamadas'/>";
    
    var _ACTION_OBTENER_DIALOGO = "";
    
    var _ACTION_COMBO_ASEGURADORA = "<s:url action='obtenerAseguradoras' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_GRUPOS_PERSONA = "<s:url action='obtenerGrupoPersonas' namespace='/combos'/>";
    var _ACTION_COMBO_GRUPO = "<s:url action='comboClientesCorpBO' namespace='/combos'/>";
    var _ACTION_COMBO_ESTADO = "<s:url action='obtenerEstadosEjecutivo' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_PROCESOS = "<s:url action='obtenerComboProcesosCat' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_RAMOS = "<s:url action='obtenerRamos' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_TIPO_GUION = "<s:url action='obtenerCompraTiempo' namespace='/combos-catbo'/>"; 
    
   // var _ACTION_COMBO_EMPRESAS = "<s:url action='comboClientesCorpBO' namespace='/combos-catbo'/>";          

    var itemsPerPage=10;
    var helpMap= new Map();

</script>

<script type="text/javascript" src="${ctx}/resources/scripts/guionLlamadas/guionLlamadas_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/guionLlamadas/agregarGuionLlamadas.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/guionLlamadas/editarGuionLlamadas.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/guionLlamadas/guionLlamadas.js"></script>

</head>
<body>

   <table>
        <tr><td><div id="formBusqueda" /></td></tr>
        <tr><td><div id="gridListado" /></td></tr>
   </table>
</body>
</html>