<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Configurar Matriz Tarea</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/Multiselect.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
  
    var _ACTION_BUSCAR_RESPONSABLES = "<s:url action='buscarResponsables' namespace='/matrizAsignacion'/>";
    var _ACTION_BUSCAR_NIVELES_ATENCION = "<s:url action='buscarNivelesAtencion' namespace='/matrizAsignacion'/>";
    var _ACTION_BORRAR_RESPONSABLE = "<s:url action='borrarResponsable' namespace='/matrizAsignacion'/>";
    var _ACTION_BORRAR_TIEMPO = "<s:url action='borrarTiempo' namespace='/matrizAsignacion'/>";
      
      
    
    var _ACTION_GET_MATRIZ_ASIGNACION = "<s:url action='getMatrizAsignacion' namespace='/matrizAsignacion'/>";
    var _ACTION_GET_TIEMPO = "<s:url action='getTiempoResolucion' namespace='/matrizAsignacion'/>";
    var _ACTION_GET_RESPONSABLE = "<s:url action='getResponsable' namespace='/matrizAsignacion'/>";  
      
      
    var _ACTION_BUSCAR_TIEMPOS = "<s:url action='buscarTiempo' namespace='/matrizAsignacion'/>";
    var _ACTION_BUSCAR_CASOS = "<s:url action='buscarNotificacionesProcesos' namespace='/notificaciones'/>";
    var _ACTION_BUSCAR_USUARIOS = "<s:url action='buscarNotificacionesProcesos' namespace='/notificaciones'/>";
    var _ACTION_BUSCAR_CASOS_ASIGNADOS = "<s:url action='buscarNotificacionesProcesos' namespace='/notificaciones'/>";
     
     
    var _ACTION_GUARDAR_RESPONSABLE = "<s:url action='guardarResponsable' namespace='/matrizAsignacion'/>";
    var _ACTION_GUARDAR_TIEMPO = "<s:url action='guardarTiempo' namespace='/matrizAsignacion'/>";
    var _ACTION_GUARDAR_USUARIO = "<s:url action='buscarNotificacionesProcesos' namespace='/notificaciones'/>";
     
    var _ACTION_GUARDAR_MATRIZ = "<s:url action='guardarMatrizAsignacion' namespace='/matrizAsignacion'/>";
    
    var _ACTION_COMBO_ASEGURADORA =  "<s:url action='comboAseguradorasPorCliente' namespace='/combos-catbo'/>";
    
    var _ACTION_OBTENER_CLIENTE_CORPO = "<s:url action='comboClientesCorpBO' namespace='/combos-catbo'/>";
   
    var _ACTION_OBTENER_PRODUCTOS = "<s:url action='obtenerRamos' namespace='/combos-catbo'/>";
    
    
    var _ACTION_OBTENER_FORMATO= "<s:url action='obtenerFormatos' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_TAREAS= "<s:url action='comboTipoMetodoEnvio' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_USUARIOS_REEMPLAZO ="<s:url action='comboUsuariosReemplazantes' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_RESPONSABLES = "<s:url action='obtenerResponsables' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_NIVELES_ATENCION = "<s:url action='obtenerComboNivelesAtencion' namespace='/combos-catbo'/>";
     var _ACTION_COMBO_NIVELES_ATENCION_MATRIZ = "<s:url action='obtenerComboNivelAtencionMatriz' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_ROLES = "<s:url action='obtenerComboRoles' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_STATUS = "<s:url action='obtenerEstadoEjecutivo' namespace='/combos-catbo'/>";
    
     var _ACTION_COMBO_UNIDAD_TIEMPO =  "<s:url action='obtenerComboUnidadTiempoComprar' namespace='/combos-catbo'/>";
    
     var _ACTION_IR_MATRIZ_ASIGNACION = "<s:url action='irMatrizAsignacion' namespace='/matrizAsignacion'/>";
     var _ACTION_IR_MATRIZ_ASIGNACION_REASIGNA_CASO = "<s:url action='irMatrizAsignacionReasignarCaso' namespace='/matrizAsignacion'/>";
     
     var _ACTION_VALIDAR_SUPLENTE_RESPONSABLE = "<s:url action='validarSuplentesResponsables' namespace='/matrizAsignacion'/>";
     
     var _ACTION_GUARDAR_REEMPLAZANTE = "<s:url action='guardarSuplente' namespace='/matrizAsignacion'/>";
    
     var _OBTENER_PROCESOS = "<s:url action='obtenerComboProcesosCat' namespace='/combos-catbo'/>";
     
     var _ACTION_IR_REASIGNAR_CASO_USR = "<s:url action='irReasignarCasoUsr' namespace='/matrizAsignacion'/>";
     
     var cdMatrizEdita = "<s:property value='cdmatriz'/>";
    
     var cdNivelAtencionEdita; 
     var itemsPerPage=10;
     var helpMap= new Map();
     <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("808")%>
     
     var FLAG = 0;
     
     _URL_AYUDA = "/backoffice/configMatriz.html";
     
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/matrizAsignacion/matrizAsignacionReemplazarUsuario.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/matrizAsignacion/matrizAsignacionAgregarTiempo.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/matrizAsignacion/matrizAsignacionEditarTiempos.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/matrizAsignacion/matrizAsignacionAgregarResponsable.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/matrizAsignacion/matrizAsignacionEditarResponsable.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/configuraMatrizTarea/configurarMatrizTareaEditar.js"></script>


</head>
<body>

   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="4">
                <div id="formBusqueda" />
            </td>
        </tr>
       <tr valign="top">
           <td class="headlines">
               <div id="gridResponsables" />
           </td>
       </tr>
       <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formDocumentos" />
            </td>
        </tr>
       
       <tr valign="top">
           <td class="headlines" colspan="2">
               <div id="gridTiempos" />
           </td>
       </tr>
       <tr valign="top">
           <td>
               <div id="formMultiselect" />
           </td>
       </tr>
    </table>
</body>
</html>
