<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Descuentos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_GUIONES = "<s:url action='buscarGuiones' namespace='/operacionesCat'/>";
    var _ACTION_BUSCAR_DIALOGOS_GUION = "<s:url action='buscarDialogosPaginado' namespace='/operacionesCat'/>";
    var _ACTION_GUARDAR_GUION = "<s:url action='guardarGuion' namespace='/operacionesCat'/>";
    var _ACTION_GUARDAR_DIALOGO_GUION = "<s:url action='guardarDialogoGuion' namespace='/operacionesCat'/>";
    var _ACTION_BORRAR_GUION = "<s:url action='borrarGuion' namespace='/operacionesCat'/>";
    var _ACTION_BORRAR_DIALOGO = "<s:url action='borrarDialogo' namespace='/operacionesCat'/>";
    var _ACTION_GET_GUION = "<s:url action='getGuion' namespace='/operacionesCat'/>";
    var _ACTION_EXPORTAR_GUION = "<s:url action='exportarGuion' namespace='/operacionesCat'/>";
    var _ACTION_EXPORTAR_DIALOGOS_GUION = "<s:url action='exportarDialogosGuion' namespace='/operacionesCat'/>";
    
     
    var _OBTENER_ASEGURADORAS = "<s:url action='obtenerAseguradoras' namespace='/combos-catbo'/>";
   // var _OBTENER_PRODUCTOS = "<s:url action='obtenerRamos' namespace='/combos-catbo'/>";
    var _OBTENER_PRODUCTOS_2 = "<s:url action='obtenerRamosNuevo' namespace='/combos-catbo'/>";
    var _OBTENER_ESTADOS = "<s:url action='obtenerEstadoEjecutivo' namespace='/combos-catbo'/>";
    var _OBTENER_PROCESOS = "<s:url action='obtenerComboProcesosCat' namespace='/combos-catbo'/>";
    var _OBTENER_CLIENTES_CORPO = "<s:url action='comboClientesCorpBO' namespace='/combos-catbo'/>";
    var _OBTENER_TIPO_GUION = "<s:url action='comboTipoGuion' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_TABLA = "<s:url action='obtenerTabla' namespace='/combos-catbo'/>";
    
    var helpMap= new Map();
   //  var itemsPerPage=10;
   
     var itemsPerPage = _NUMROWS;
     
    <%=session.getAttribute("helpMap")%>

    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("807")%>    
    
     _URL_AYUDA = "/backoffice/guionllamadaEntrada.html";
     
    
    
</script>

<!--  <script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
 <script type="text/javascript">var helpMap= new Map()</script>  -->
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<!-- 
<script type="text/javascript" src="${ctx}/resources/scripts/operacionesCat/catScriptAtencion.js"></script>

 -->
<script src="${ctx}/resources/scripts/ValidacionGrilla/Ext.ux.plugins.GridValidator.js" type="text/javascript"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/operacionesCat/operacioneCatEditar.js"></script> 
<script type="text/javascript" src="${ctx}/resources/scripts/operacionesCat/operacionesCatAgregar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/operacionesCat/operacionesCat_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/operacionesCat/operacionesCat.js"></script>



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
               <div id="gridGuiones" />
           </td>
       </tr>
    </table>
</body>
</html>
