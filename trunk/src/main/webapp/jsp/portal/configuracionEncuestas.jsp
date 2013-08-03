<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Configuracion de Encuestas</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
  
    var _ACTION_BUSCAR_CONFIGURACION_ENCUESTAS = "<s:url action='buscarConfiguracionEncuestas' namespace='/configuracionEncuestas'/>";
     var _ACTION_GET_CONFIGURACION_ENCUESTAS = "<s:url action='getConfigEncuesta' namespace='/configuracionEncuestas'/>";
    
    
    var _ACTION_COMBO_CLIENTES_CORPO = "<s:url action='comboClientesAseguradoraEncuestas' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_ASEGURADORAS_CLIENTE = "<s:url action='comboClientesCorpBO' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_MODULOS_ENCUESTAS = "<s:url action='comboModulosEncuestas' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_ENCUESTAS = "<s:url action='comboEncuestas' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_CAMPAN_ENCUESTAS = "<s:url action='comboCampanEncuestas' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_ASEGURADORAS = "<s:url action='obtenerAseguradoras' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_UNIDAD_TIEMPO =  "<s:url action='obtenerComboUnidadTiempoComprar' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_PROCESOS =  "<s:url action='obtenerComboProcesosCat' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_USUARIOS = "<s:url action='comboUsuariosEncuestas' namespace='/combos-catbo'/>";
    
    var _ACTION_COMBO_PRODUCTOS_ASEG_ENCUESTA=  "<s:url action='comboProductosAseguradoraEncuestas' namespace='/combos-catbo'/>";
   
    var _ACTION_GUARDAR_CONFIGURACION_ENCUESTAS = "<s:url action='guardarConfiguracionEncuesta' namespace='/configuracionEncuestas'/>";
    var _ACTION_GUARDAR_CONFIGURACION_ENCUESTAS_EDITAR = "<s:url action='guardarConfiguracionEncuestaEditar' namespace='/configuracionEncuestas'/>";
    
    var _ACTION_BORRAR_CONFIGURACION_ENCUESTAS = "<s:url action='borrarConfigEncuesta' namespace='/configuracionEncuestas'/>";
   
    var _ACTION_GUARDAR_CONFIGURACION_ENCUESTAS_TIEMPO = "<s:url action='guardarTiempoConfigEncuesta' namespace='/configuracionEncuestas'/>";
    var _ACTION_BORRAR_CONFIGURACION_ENCUESTAS_TIEMPO = "<s:url action='borrarTiempoConfigEncuesta' namespace='/configuracionEncuestas'/>";
   
    var _ACTION_GET_CONFIGURACION_ENCUESTAS_TIEMPO = "<s:url action='getTiempoConfigEncuesta' namespace='/configuracionEncuestas'/>";
    var _ACTION_EXPORTAR_CONFIGURACION_ENCUESTAS = "<s:url action='exportarConfigEncuesta' namespace='/configuracionEncuestas'/>";
   
    var helpMap= new Map();
   
      var itemsPerPage=_NUMROWS;
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("511")%>
    
    _URL_AYUDA = "/backoffice/consulConfigEncuesta.html";
    
</script>



<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>

<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/Multiselect.js"></script>


<script type="text/javascript" src="${ctx}/resources/scripts/configurarEncuestas/configuracionEncuestasEditar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/configurarEncuestas/configuracionEncuestasTiempo.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/configurarEncuestas/configuracionEncuestasAgregar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/configurarEncuestas/configuracionEncuestas_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/configurarEncuestas/configuracionEncuestas.js"></script>

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
               <div id="gridConfiguracionEncuestas" />
           </td>
       </tr>
       
    </table>
</body>
</html>
