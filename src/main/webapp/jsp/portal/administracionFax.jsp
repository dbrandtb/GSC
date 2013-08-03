<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Administracion de Fax</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<!-- Algunos css para el UploadPanel:   -->
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/icons.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/filetree.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/filetype.css" />

<script language="javascript">
     var _CONTEXT = "${ctx}";  
     var _ACTION_OBTENER_POLIZAS = "<s:url action='obtenerPolizas' namespace='/consultaPolizas'/>";
     
     var _ACTION_OBTENER_CASO = "<s:url action='obtenerCasoFax' namespace='/administracionFax'/>";
     
     var _ACTION_OBTENER_ADMNISTRACION_FAX = "<s:url action='obtenerAdministracionFaxClick' namespace='/administracionFax'/>";
     var _ACTION_OBTENER_ADMNISTRACION_VALOR_FAX = "<s:url action='obtenerAdministracionValorFaxClick' namespace='/administracionFax'/>";
     var _ACTION_GUARDAR_ADMINISTRACION_FAX = "<s:url action='guardarAdministracionFax' namespace='/administracionFax'/>";
     var _ACTION_GUARDAR_ADMINISTRACION_VALOR_FAX = "<s:url action='guardarAdministracionValorFax' namespace='/administracionFax'/>";
     var _ACTION_OBTENER_ATRIBUTOS_VARIABLES = "<s:url action='obtenerAtributosVariablesFax' namespace='/administracionFax'/>"; 
     var _ACTION_VALIDA_NMCASO_FAX = "<s:url action='validaNmcasoFax' namespace='/administracionFax'/>"; 
    
     var _ACTION_COMBO_TIPO_ARCHIVO2 = "<s:url action='comboCargaArchivos2' namespace='/combos-catbo'/>";
    
     var _ACTION_GUARDAR_NUEVO_CASO_FAX = "<s:url action='guardarNuevoCasoFax' namespace='/administracionFax'/>";
    
     var _ACTION_OBTENER_FUNCION_NOMBRE = "<s:url action='obtenerFuncionNombre' namespace='/administracionFax'/>";
     var _ACTION_OBTENER_FUNCION_CODIGO = "<s:url action='obtenerFuncionCodigo' namespace='/administracionFax'/>";
    
     var _ACTION_AGREGAR_ARCHIVO = "<s:url action='agregarArchivosFax' namespace='/administracionFax'/>";
    
     var _ACTION_COMBO_TIPO_FAX = "<s:url action='comboObtenerTipoFax' namespace='/combos-catbo'/>";
    
    
     var _IR_A_CONSULTA_FAX= "<s:url action='irConsultaFax' namespace='/consultaFax'/>";
     var _ACTION_REGRESAR_A_CONSULTAR_CASO_DETALLE = "<s:url action='irConsultaCasoDetalle' namespace='/consultarCasosSolicitud'/>";
     
     var _IR_BUSQUEDA_POLIZA =  "<s:url action='busquedaPoliza' namespace='/procesoemision'/>";     
    
    var _NMFAX = "<s:property value='nmfax'/>";
    var _NMCASO = "<s:property value='nmcaso'/>";
    var _CDTIPOAR = "<s:property value='cdtipoar'/>";
     
     
      
	var _CDPERSON = "<s:property value='cdperson'/>";
    var _CDMATRIZ  = "<s:property value='cdmatriz'/>";
    var FLAG = "<s:property value='flag'/>";
    
   var _CDFORMATOORDEN = "<s:property value='cdformatoorden'/>";
   
     
      // var _IR_A_CONSULTA_CASOS= "<s:url action='irConsultaCasos' namespace='/consultaFax'/>";    
      
       
  	 
    var itemsPerPage=10;
   <%=session.getAttribute("helpMap")%>
    var helpMap= new Map();
    
    var CDPERSON = "<s:property value='cdperson'/>";
    var CDPERSON = "<s:property value='cdperson'/>";
     <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("814")%>
     
      _URL_AYUDA = "/backoffice/agregarAdministracionFax.html"; 
      
      
   // var CDUSUARIO = "SLIZARDI";
</script>

<script src="${ctx}/resources/scripts/util/FileUploader.js" type="text/javascript"></script>
<script src="${ctx}/resources/scripts/configurador/designer/fileTree/Ext.ux.form.BrowseButton.js" type="text/javascript"></script>
<script src="${ctx}/resources/scripts/util/UploadPanel.js" type="text/javascript"></script>


<!-- <script type="text/javascript" src="${ctx}/resources/scripts/consultaPolizas/consultaPolizas.js"></script>-->
<script type="text/javascript" src="${ctx}/resources/scripts/AdministracionFax/administracionFaxConsultaPoliza.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/AdministracionFax/administracionFaxConsultaPoliza.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/AdministracionFax/AnexarArchivoDigitalizadoFax.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/AdministracionFax/administracionFax_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/AdministracionFax/administracionFax.js"></script>



</head>
<body>
   
   <table class="headlines" cellspacing="5">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="encabezadoFijo" />
            </td>
        </tr>
       <tr valign="top">
            <td class="headlines" colspan="2">
                <iframe id="atributosVariablesFax" align="top" frameBorder="SI" name="atributosVariablesFax" width="700" scrolling="SI" height="1">
        		</iframe>
        		

            </td>
        </tr>
        <tr valign="top">
           <td class="headlines" colspan="2">
               <div id="formBotones" />
           </td>
       </tr>
    </table>
   
   
      
</body>
</html>