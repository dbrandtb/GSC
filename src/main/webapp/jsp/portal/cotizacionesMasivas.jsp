<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla Cotizaciones Masivas</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>

<!-- Algunos css para el UploadPanel:   -->
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/icons.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/filetree.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/filetype.css" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
    
    var _ACTION_BUSCAR_POLIZAS="";
      
    var _ACTION_BUSCAR_COTIZACIONES = "<s:url action='buscarCotizacionMasiva' namespace='/cotizacionesMasivas'/>";
    var _ACTION_APROBAR_COTIZACIONES = "<s:url action='aprobarCotizacion' namespace='/cotizacionesMasivas'/>";
    var _ACTION_BORRAR_COTIZACIONES = "<s:url action='borrarCotizacion' namespace='/cotizacionesMasivas'/>";
    var _ACTION_BUSCAR_TIPO_RAMO =  "<s:url action='buscarTipoRamo' namespace='/cotizacionesMasivas'/>";
    //var _ACTION_SUBIR_ARCHIVO = "<s:url action='fileTree' namespace='/cotizacionesMasivas'/>";
    var _ACTION_SUBIR_ARCHIVO = "<s:url action='cargarArchivo' namespace='/cotizacionesMasivas'/>";
   // variables para ventana Detalle
   
    var _ACTION_OBTIENE_COBERTURAS = "<s:url action='obtieneCoberturas' namespace='/flujocotizacion'/>";
    var _ACTION_COMPRAR_COTIZACIONES = "<s:url action='comprarCotizaciones' namespace='/flujocotizacion'/>";
    var _ACTION_OBTIENE_TVALOSIT_COTIZA = "<s:url action='obtieneTvalositCotiza' namespace='/flujocotizacion'/>";

    var helpMap= new Map();
    var itemsPerPage=10;
    var vistaTipo=1;
    <%=session.getAttribute("helpMap")%>
    
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("817")%>    
    
    
</script>

<!-- <script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>  -->
<!-- <script type="text/javascript">var helpMap= new Map()</script>  -->
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/pluginCheckColumn.js"></script>


<!-- Archivos js para el UploadPanel:   -->
<script type="text/javascript" src="${ctx}/resources/scripts/portal/cotizacionesMasivas/EditarcotizacionesMasivas.js"></script>
<script src="${ctx}/resources/scripts/configurador/designer/fileTree/Ext.ux.FileUploader.js" type="text/javascript"></script>
<script src="${ctx}/resources/scripts/configurador/designer/fileTree/Ext.ux.form.BrowseButton.js" type="text/javascript"></script>
<script src="${ctx}/resources/scripts/configurador/designer/fileTree/Ext.ux.UploadPanel.js" type="text/javascript"></script>


<script type="text/javascript" src="${ctx}/resources/scripts/portal/cotizacionesMasivas/cotizacionesMasivas_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/cotizacionesMasivas/cotizacionesMasivas.js"></script>






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
               <div id="gridElementos" />
           </td>
       </tr>
    </table>
</body>
</html>