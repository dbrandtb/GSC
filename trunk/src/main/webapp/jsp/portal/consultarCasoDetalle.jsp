<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Consultar Casos / Detalles</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include> 


<!-- Algunos css para el UploadPanel:   -->
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/icons.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/filetree.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/filetype.css" />

<script language="javascript">

    var _CONTEXT = "${ctx}";
  
    var _ACTION_GET_ENC_CASO_DETALLE = "<s:url action='obtenerCaso' namespace='/administracionCasos'/>";
    //var _ACTION_OBTENER_ATRIBUTOS_VARIABLES = "<s:url action='obtenerAtributosVariablesCasos' namespace='/administracionCasos'/>";
    var _ACTION_OBTENER_DATOS_ATRIBUTOS_VARIABLES = "<s:url action='obtenerDatosAtributosVariables' namespace='/administracionCasos'/>";
    var _ACTION_OBTENER_USUARIOS_DEL_CASO = "<s:url action='buscarUsuariosResponsables' namespace='/administracionCasos'/>";
    var _ACTION_IR_CONSULTA_MOVIMIENTO_CASO = "<s:url action='irConsultaMovimiento' namespace='/administracionCasos'/>";
    var _ACTION_EXPORTAR = "<s:url action='exportaDetalleCaso' namespace='/administracionCasos'/>";
    
    var _ACTION_OBTENER_COMBO_DATOS_CATALOGOS_DEP = "<s:url action='obtenerComboDatosCatalogosDep' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_COMBO_PLAN = "<s:url action='obtenerComboPlanesProducto' namespace='/combos-catbo'/>";
	var _ACTION_OBTENER_COMBO_FORMA_PAGO = "<s:url action='getComboInstrumentoPago' namespace='/combos-catbo'/>";
	var _ACTION_OBTENER_COMBO_MONEDA = "<s:url action='obtenerComboMonedas' namespace='/combos-catbo'/>";
    
    var _ACTION_VOLVER_CONSULTA_CASO = "<s:url action='irConsultaCaso' namespace='/consultarCasosSolicitud'/>";
    
    var _ACTION_OBTENER_COMPRA_TIEMPO = "<s:url action='obtieneCompraTiempo' namespace='/compraTiempo'/>";
    
    var _ACTION_OBTENER_ARCHIVOS = "<s:url action='obtieneCompraTiempo' namespace='/compraTiempo'/>";
    
    var _ACTION_DESCARGAR_ARCHIVO = "<s:url action='descargarArchivo' namespace='/archivos'/>";
    
	var _ACTION_COMBO_GENERICO = "<s:url action='obtenerComboGenerico' namespace='/combos-catbo'/>";
	
	var _ACTION_COMBO_UNIDAD_TIEMPO = "<s:url action='obtenerDatosCatalogo' namespace='/combos-catbo'/>"; 
	
	var _ACTION_OBTENER_ARCHIVOS = "<s:url action='obtenerArchivos' namespace='/archivos'/>"; 

    var _ACTION_AGREGAR_ARCHIVO = "<s:url action='agregarArchivos' namespace='/archivos'/>";
    
    var _ACTION_AGREGAR_ARCHIVOJSON = "<s:url action='agregarArchivosJSON' namespace='/archivos'/>";

    var _ACTION_COMBO_TIPO_ARCHIVO = "<s:url action='obtieneTiposArchivos' namespace='/combos-catbo'/>";
    
    var _ACTION_GUARDAR_COMPRA_TIEMPO = "<s:url action='guardarComTmpo' namespace='/compraTiempo'/>";
    
    var _ACTION_ENVIAR_CORREO = "<s:url action='enviaMail' namespace='/mail'/>";
     
    var _ACTION_IR_REASIGNAR_CASO = "<s:url action='irReasignarCaso' namespace='/consultarCasosSolicitud'/>";
    
    var _ACTION_VALIDACION_COMPRA_TIEMPO = "<s:url action='validacionCompraTiempo' namespace='/administracionCasos'/>";
     var CDFORMATOORDEN = "<s:property value='cdformatoorden'/>";
     
     var _ACTION_BORRAR_ARCHIVO_MOVIMIENTO ="<s:url action='borrarArchivosMovimiento' namespace='/archivos'/>"; 
     
     var VALIDA_SATUS_CASO = "<s:url action='validaStatusCaso' namespace='/compraTiempo'/>";
     
      var _VALIDA_SATUS_CASO = "<s:url action='validaStatusCaso' namespace='/administracionCasos'/>";
     
     
     var _IR_A_ADMINISTRAR_FAX_EDITAR = "<s:url action='irAdministracionFaxEditar' namespace='/administracionFax'/>";
     
     var _IR_A_ADMINISTRAR_FAX = "<s:url action='irAdministracionFax' namespace='/administracionFax'/>";
    
    var CDPERSON = "<s:property value='cdperson'/>";
    var CDMATRIZ  = "<s:property value='cdmatriz'/>";
    var NMCASO  = "<s:property value='nmcaso'/>";         
	var ATTS_DINAMICOS;	   
	var intervalo;
	var TIPOENVIO = "A";
	var vg_nmLoadFile = false;
	var vg_nmMovimiento = "";
    
	var readerComboAtributosVariables = new Ext.data.JsonReader({
			root: 'comboGenerico',
			totalProperty: 'totalCount',
			successProperty: 'success'
			}, [
				{name: 'codigo', type: 'string'},
				{name: 'descripcion', type: 'string'}
			]
	);

	function crearStoreComboAtributosVariables(_idTablaLogica, _comboId, _value) {
		var storeComboAtributosVariables;
		storeComboAtributosVariables = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_GENERICO}),
			reader: readerComboAtributosVariables
		});

		storeComboAtributosVariables.load({
			params: {idTablaLogica: _idTablaLogica},
			callback: function(r, opt, success){
						if (_value != null) {
							Ext.getCmp(_comboId).setValue(_value);							
						}
					}
		});
		return storeComboAtributosVariables;
	}
	

    <%if(session.getAttribute("modelControl") != null){%>
			ATTS_DINAMICOS = <%=session.getAttribute("modelControl")%>
		<%}else{%>
			ATTS_DINAMICOS = [{html: '<br/><span class="x-form-item" style="font-weight:bold">Error al cargar los atributos variables</span>'}]
			<%}
     %>   
     var helpMap= new Map();
     var itemsPerPage=_NUMROWS;
     <%=session.getAttribute("helpMap")%>
     <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("515")%>
     <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("805")%>
     
     _URL_AYUDA = "/backoffice/consulCasos.html";
    
    


</script>

<!-- Archivos js para el UploadPanel:   -->
<script src="${ctx}/resources/scripts/util/FileUploader.js" type="text/javascript"></script>
<script src="${ctx}/resources/scripts/configurador/designer/fileTree/Ext.ux.form.BrowseButton.js" type="text/javascript"></script>
<script src="${ctx}/resources/scripts/util/UploadPanel.js" type="text/javascript"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/consultarCasoDetalle/enviarCorreo.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/consultarCasoDetalle/consultarCasoDetalle_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/consultarCasoDetalle/consultarCasoDetalle.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/consultarCasoDetalle/consultarCasoDetalle_comprarTiempo.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/Archivos/ArchivoPorMovimientoDeUnCaso.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/Archivos/AnexarArchivoDigitalizado.js"></script>

</head>
<body >	
   <table>
   		  <tr><td><div id="encabezadoFijo" /></td></tr>
          <tr><td><div id="formDocumentos" /></td></tr>
   </table>
          <div id="formExportar" />
</body>
</html>