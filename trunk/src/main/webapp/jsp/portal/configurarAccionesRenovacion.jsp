<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Configurar Forma Calculo Atributos Variables</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_CONFIGURAR_ACCIONES_RENOVACION = "<s:url action='buscarConfigurarAccionesRenovacion' namespace='/configurarAccionesRenovacion'/>";
    var _ACTION_OBTENER_CONFIGURAR_ACCIONES_RENOVACION = "<s:url action='getConfigurarAccionesRenovacion' namespace='/configurarAccionesRenovacion'/>";    
    var _ACTION_OBTENER_CONFIGURAR_ACCIONES_RENOVACION_ACCIONES = "<s:url action='getConfigurarAccionesRenovacionAccion' namespace='/configurarAccionesRenovacion'/>";    
    var _ACTION_BORRAR_CONFIGURAR_ACCIONES_RENOVACION = "<s:url action='borrarConfigurarAccionesRenovacion' namespace='/configurarAccionesRenovacion'/>";
    var _ACTION_GUARDAR_CONFIGURAR_ACCIONES_RENOVACION = "<s:url action='guardarConfigurarAccionesRenovacion' namespace='/configurarAccionesRenovacion'/>";
    var _ACTION_EXPORTAR_CONFIGURAR_ACCIONES_RENOVACION = "<s:url action='exportarConfigurarAccionesRenovacion' namespace='/configurarAccionesRenovacion'/>";    
 	var _ACTION_REGRESAR_A_CONSULTA_CONFIGURARCION_RENOVACION = "<s:url action='regresarConsultaConfiguracionRenovacion' namespace='/configurarAccionesRenovacion'/>";
  
 
    
    var itemsPerPage= 20;
    <%=session.getAttribute("helpMap")%>
    
    //VARIABLES PARA COMBOS
    var _ACTION_OBTENER_ACCIONES_RENOVACION_ROLES = "<s:url action='obtenerAccionesRenovacionRoles' namespace='/combos'/>";
    var _ACTION_OBTENER_ACCIONES_RENOVACION_PANTALLA = "<s:url action='obtenerAccionesRenovacionPantalla' namespace='/combos'/>";
    var _ACTION_OBTENER_ACCIONES_RENOVACION_CAMPO = "<s:url action='obtenerAccionesRenovacionCampo' namespace='/combos'/>";
    var _ACTION_OBTENER_ACCIONES_RENOVACION_ACCION = "<s:url action='obtenerAccionesRenovacionAccion' namespace='/combos'/>";    
    
	//Seteo de valores de la estructura obtenidos
    var CODIGO_CDRENOVA = "<s:property value='cdRenova'/>"; //"15"    
   
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap = new Map();</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configurarAccionesRenovacion/configurarAccionesRenovacion.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configurarAccionesRenovacion/guardarConfigurarAccionesRenovacion.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configurarAccionesRenovacion/editarConfigurarAccionesRenovacion.js"></script>

</head>
<body>

   <table class="headlines" cellspacing="4">
        <tr valign="top" >
            <td class="headlines" colspan="2">
                <div align="left" id="formBusqueda" />
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