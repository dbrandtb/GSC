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
  
    var _ACTION_BUSCAR_ENCUESTAS = "<s:url action='obtenerEncuestas' namespace='/encuestas'/>";
    var _ACTION_BUSCAR_ENCUESTAS_EXPORTAR = "<s:url action='exportarEncuestas' namespace='/encuestas'/>";
    var _ACTION_BUSCAR_PREGUNTAS_ENCUESTA = "<s:url action='buscarProcesosNotificaciones' namespace='/notificaciones'/>";
    
    var _ACTION_GUARDAR_ENCUESTA_PREGUNTA = "<s:url action='guardarEncuestaPreguntas' namespace='/encuestas'/>";
	var _ACTION_BUSCAR_ENCUESTA_PREGUNTAS = "<s:url action='obtenerEncuestaPreguntas' namespace='/encuestas'/>";    
	var _ACTION_BUSCAR_ENCUESTAS_GET = "<s:url action='obtenerEncuestaPreguntasGet' namespace='/encuestas'/>";    
    var _ACTION_GUARDAR_ENCUESTA_PREGUNTA_AGREGAR = "<s:url action='guardarEncuestaPreguntasAgregar' namespace='/encuestas'/>";

    var _ACTION_ENCUESTAS_PREGUNTAS_EXPORTAR = "<s:url action='exportarEncuestaPreguntas' namespace='/encuestas'/>";
    
 
    
    var _ACTION_BORRAR_ENCUESTA = "<s:url action='borrarEncuesta' namespace='/encuestas'/>";
    
    var _ACTION_BORRAR_ENCUESTA_PREGUNTA = "<s:url action='borrarEncuestaPregunta' namespace='/encuestas'/>";
    

    
    var _ACTION_OBTENER_NOTIFICACIONES_FORMATO= "<s:url action='comboNotificacionesFormato' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_NOTIFICACIONES_TIPO_METODO_ENVIO= "<s:url action='comboTipoMetodoEnvio' namespace='/combos-catbo'/>";
    
    var _ACTION_COMBO_MODULOS_ENCUESTAS = "<s:url action='comboModulosEncuestas' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_ESTADO_ENCUESTAS = "<s:url action='obtenerEstadosEjecutivo' namespace='/combos-catbo'/>";
    
    
     var _ACTION_OBTENER_GET_LISTAS= "<s:url action='comboObtenerListas' namespace='/combos-catbo'/>";
     
     var _ACTION_OBTENER_TABLA = "<s:url action='obtenerTabla' namespace='/combos-catbo'/>";
    
     
     var helpMap = new Map();
    var itemsPerPage=_NUMROWS;
    <%=session.getAttribute("helpMap")%>
    var _ACTION_BUSCAR_FORMATOS_DOCUMENTOS = "<s:url action='buscarFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_BORRAR_FORMATOS_DOCUMENTOS = "<s:url action='borrarFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_GET_FORMATO_DOCUMENTOS = "<s:url action='getFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_GUARDAR_FORMATO_DOCUMENTOS = "<s:url action='guardarFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_EXPORTAR_FORMATO_DOCUMENTOS = "<s:url action='exportarFormatosDocumentos' namespace='/formatosDocumentos'/>";

  <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("502")%>
  
  _URL_AYUDA = "/backoffice/consultaformatoEncuesta.html";
    
</script>



<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/Multiselect.js"></script>

<script src="${ctx}/resources/scripts/ValidacionGrilla/Ext.ux.plugins.GridValidator.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/encuesta/encuesta_stores_agrega.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/encuesta/encuestaAgregar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/encuesta/encuesta_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/encuesta/encuesta.js"></script>

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
