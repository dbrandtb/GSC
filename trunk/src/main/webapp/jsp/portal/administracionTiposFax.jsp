<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Administracion de Tipos de Fax</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
   
   var _ACTION_BUSCAR_ATRIBUTOS_FAX = "<s:url action='buscarAtributosFax' namespace='/administracionTiposFax'/>";
   var _ACTION_GET_ATRIBUTOS_FAX_REG = "<s:url action='getAtributosFaxReg' namespace='/administracionTiposFax'/>";
   var _ACTION_BORRAR_ATRIBUTOS_FAX = "<s:url action='borrarAtributosFax' namespace='/administracionTiposFax'/>";
   var _ACTION_GUARDAR_ATRIBUTOS_FAX = "<s:url action='guardarAtributosFax' namespace='/administracionTiposFax'/>";
   var _ACTION_EXPORTAR_ATRIBUTOS_FAX = "<s:url action='exportarAtributosFax' namespace='/administracionTiposFax'/>";
   var _ACTION_ACTUALIZAR_ATRIBUTOS_FAX= "<s:url action='actualizarAtributosFax' namespace='/administracionTiposFax'/>";
// Combos url de los combos
   var _ACTION_OBTENER_FORMATO = "<s:url action='comboFormato' namespace='/combos-catbo'/>";
   var _ACTION_OBTENER_TABLAS = "<s:url action='comboTablas' namespace='/combos-catbo'/>";
   var _ACTION_OBTENER_STATUS = "<s:url action='comboEstatusTareasCatBo' namespace='/combos-catbo'/>"; 
   
   
    var DSARCHIVO = "<s:property value='dsArchivo'/>";
    var CDTIPOAR = "<s:property value='cdTipoar'/>";
    
   var _IR_A_ADMINISTRACION_TIPO_ARCHIVO = "<s:url action='irAdministracionArchivos' namespace='/administracionTipoArchivo'/>";
     
 	//var _ACTION_AGREGAR_NUEVA_SECCION = "<s:url action='guardarNuevaSeccion' namespace='/secciones'/>";
    //var _ACTION_GUARDAR_SECCION = "<s:url action='guardarSeccion' namespace='/secciones'/>";

    var helpMap = new Map();
    // var itemsPerPage=10;
    
      var itemsPerPage = _NUMROWS;

    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("516")%>
   
       _URL_AYUDA = "/backoffice/administracionAtributosTipoArchivo.html"; 
       
           
      
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>

<script type="text/javascript" src="${ctx}/resources/scripts/administracionTiposFax/agregarAtributosFax.js"></script>
<!--  <script type="text/javascript" src="${ctx}/resources/scripts/administracionTiposFax/editarAtributosFax.js"></script> -->
<script type="text/javascript" src="${ctx}/resources/scripts/administracionTiposFax/administracionTiposFax.js"></script>



</head>
<body>

   <table cellspacing="10">
        <tr valign="top">
            <td colspan="2">
                <div id="formBusqueda" />
            </td>
        </tr>
       <tr valign="top">
           <td colspan="2">
               <div id="gridSecciones" />
           </td>
       </tr>
    </table>
</body>
</html>