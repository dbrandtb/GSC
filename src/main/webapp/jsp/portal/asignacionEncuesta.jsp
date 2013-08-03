<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Notificaciones</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>


<script language="javascript">
    var _CONTEXT = "${ctx}";
  
  //   var _ACTION_BUSCAR_ASIGNACION_ENCUESTAS = "<s:url action='obtenerAsignacionEncuesta' namespace='/configuracionEncuestas'/>";
    
    var _ACTION_BUSCAR_ASIGNACION_ENCUESTAS_2 = "<s:url action='buscarAsignacionEncuesta' namespace='/configuracionEncuestas'/>";
    
    var _ACTION_GET_ASIGNACION_ENCUESTAS = "<s:url action='getAsignacionEncuesta' namespace='/configuracionEncuestas'/>";
    
    
    var _ACTION_BUSCAR_ENCUESTAS_EXPORTAR = "<s:url action='exportarEncuestas' namespace='/configuracionEncuestas'/>";
    var _ACTION_BUSCAR_PREGUNTAS_ENCUESTA = "<s:url action='buscarProcesosNotificaciones' namespace='/configuracionEncuestas'/>";
    
    var _ACTION_BORRAR_ASIGNACION_ENCUESTA = "<s:url action='borrarAsignacionEncuesta' namespace='/configuracionEncuestas'/>";
    var _ACTION_OBTENER_ASIGNACION_ENCUESTA_EXPORT = "<s:url action='exportarAsignacionEncuesta' namespace='/configuracionEncuestas'/>";
    
    var _ACTION_OBTENER_ASIGNACION_ENCUESTA_EXPORTAR = "<s:url action='exportarAsignacionEncuestaNueva' namespace='/configuracionEncuestas'/>";


    
    var _ACTION_GUARDAR_ASIGNACION_ENCUESTA = "<s:url action='guardarAsignacionEncuesta' namespace='/configuracionEncuestas'/>";
    var _ACTION_GUARDAR_NOTIFICACIONES_PROCESO = "<s:url action='guardarNotificacionesProcesos' namespace='/notificaciones'/>";
    
   //  var _ACTION_COMBO_USUARIOS = "<s:url action='comboUsuariosEncuestas' namespace='/combos-catbo'/>";
   
   var _ACTION_COMBO_USUARIOS_2 = "<s:url action='comboUsuarioResponsableNuevo' namespace='/combos-catbo'/>";

   
    
    var itemsPerPage=_NUMROWS;
  
    var _ACTION_BUSCAR_FORMATOS_DOCUMENTOS = "<s:url action='buscarFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_BORRAR_FORMATOS_DOCUMENTOS = "<s:url action='borrarFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_GET_FORMATO_DOCUMENTOS = "<s:url action='getFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_GUARDAR_FORMATO_DOCUMENTOS = "<s:url action='guardarFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_EXPORTAR_FORMATO_DOCUMENTOS = "<s:url action='exportarFormatosDocumentos' namespace='/formatosDocumentos'/>";
    
     var _ACTION_COMBO_CLIENTES_CORPO = "<s:url action='comboClientesAseguradoraEncuestas' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_ASEGURADORAS_CLIENTE = "<s:url action='comboClientesCorpBO' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_MODULOS_ENCUESTAS = "<s:url action='comboModulosEncuestas' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_ENCUESTAS = "<s:url action='comboEncuestas' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_CAMPAN_ENCUESTAS = "<s:url action='comboCampanEncuestas' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_ASEGURADORAS = "<s:url action='obtenerAseguradoras' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_UNIDAD_TIEMPO =  "<s:url action='obtenerComboUnidadTiempoComprar' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_PROCESOS =  "<s:url action='obtenerComboProcesosCat' namespace='/combos-catbo'/>";
    
    var _ACTION_COMBO_PRODUCTOS_ASEG_ENCUESTA=  "<s:url action='comboProductosAseguradoraEncuestas' namespace='/combos-catbo'/>";
    
    var helpMap= new Map();
    
     <%=session.getAttribute("helpMap")%>
    
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("812")%>
    
    _URL_AYUDA = "/backoffice/consultaAsignaEncuesta.html";
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/Multiselect.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/asignacionEncuesta/asignacion_encuesta_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/asignacionEncuesta/encuesta_stores_agrega.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/asignacionEncuesta/asignacionEncuestaAgregar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/asignacionEncuesta/asignacionEncuesta.js"></script>

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
               <div id="gridEncuestas" />
           </td>
       </tr>
       
    </table>
</body>
</html>
