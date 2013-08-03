<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Configuracion de Endosos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>



<script language="javascript">
    var _CONTEXT = "${ctx}";
      var _ACTION_OBTENER_TIPOS_ARCHIVOS = "<s:url action='obtenerTipoArchivo' namespace='/administracionTipoArchivo'/>";
      var _ACTION_BORRAR_TIPO_ARCHIVOS = "<s:url action='borrarTipoArchivo' namespace='/administracionTipoArchivo'/>";
	  var _ACTION_GUARDAR_TIPO_ARCHIVOS = "<s:url action='guardarTipoArchivo' namespace='/administracionTipoArchivo'/>";	
	  var _ACTION_EXPORTAR_TIPOS_ARCHIVOS = "<s:url action='exportarTipoArchivo' namespace='/administracionTipoArchivo'/>";
	  
	 var _ACTION_IR_ADMINISTRACION_TIPOS_FAX = "<s:url action='iradministraciontiposfax' namespace='/administracionTipoArchivo'/>";
		
	var _ACTION_OBTENER_TIPO_ARCHIVO = "<s:url action='obtenerDatosCatalogo' namespace='/combos-catbo'/>";
	
   
    
    var itemsPerPage = _NUMROWS;
    
    <%=session.getAttribute("helpMap")%>
    
   	var helpMap = new Map();		

    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("809")%>    
    
      _URL_AYUDA = "/backoffice/administracionTipoArchivo.html"; 
      
	
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/AdministrarTipoArchivo/administracionTipoArchivo.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/AdministrarTipoArchivo/administrar_Archivo_Agregar.js"></script>
<!-- <script type="text/javascript" src="${ctx}/resources/scripts/AdministrarTipoArchivo/administrar_Archivo_Editar.js"></script> -->


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
               <div id="grid" />
           </td>
       </tr>
    </table>
</body>
</html>