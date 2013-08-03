
<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Consulta de Movimientos del Caso</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- Algunos css para el UploadPanel:   -->
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/icons.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/filetree.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/filetype.css" />


<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
  
    var _ACTION_OBTENER_ENCABEZADO = "<s:url action='getEncabezadoMovimientosCaso' namespace='/administracionCasos'/>";    
    var _ACTION_OBTENER_LISTADO_MOVIMIENTOS_CASO = "<s:url action='obtenerListadoMovimientosCaso' namespace='/administracionCasos'/>";
    
    var _ACTION_EXPORTAR_LISTADO_MOVIMIENTOS_CASO = "<s:url action='exportarListadoMovimientosCaso' namespace='/administracionCasos'/>"; 
	var _ACTION_GET_MOVIMIENTO_CASO = "<s:url action='getMovimientoCaso' namespace='/administracionCasos'/>";
	var _ACTION_OBTENER_ATRIBUTOS_VARIABLES = "<s:url action='obtenerAtibutosVariables' namespace='/administracionCasos'/>";
    var _ACTION_OBTENER_DATOS_ATRIBUTOS_VARIABLES = "<s:url action='obtenerDatosAtributosVariables' namespace='/administracionCasos'/>";
	var _ACTION_OBTENER_DATOS_DEL_MOVIMIENTO = "<s:url action='obtenerDatosDelMovimiento' namespace='/administracionCasos'/>";
	var _ACTION_OBTENER_USR_RES_MCASO = "<s:url action='buscarUsuariosResponsables' namespace='/administracionCasos'/>";
	var _ACTION_GUARDAR_NUEVO_MOVIMIENTO = "<s:url action='guardarNuevoMovimientoCaso' namespace='/administracionCasos'/>";
	var _ACTION_BORRAR_MOVIMIENTO_CASO = "<s:url action='borrarMovimientoCaso' namespace='/administracionCasos'/>";
	var _ACTION_VALIDAR_INGRESO_ANEXO = "<s:url action='validarIngresoAnexo' namespace='/administracionCasos'/>";
	
	//var _ACTION_VOLVER_A_CONSULTA_CASO_DETALLE = "<s:url action='irConsultaCasoDetalle' namespace='/administracionCasos'/>";
	var _ACTION_VOLVER_A_CONSULTA_CASO_DETALLE = "<s:url action='irConsultaCasoDetalle' namespace='/consultarCasosSolicitud'/>";

	var _ACTION_OBTENER_USR_RES_MCONSULTA = "<s:url action='buscarUsuariosResponsablesMov' namespace='/administracionCasos'/>";

	
	var _ACTION_OBTENER_COMBO = "<s:url action='obtenerDatosCatalogo' namespace='/combos-catbo'/>";
	var _ACTION_OBTENER_COMBO_DATOS_CATALOGOS_DEP = "<s:url action='obtenerComboDatosCatalogosDep' namespace='/combos-catbo'/>";
	var _ACTION_OBTENER_COMBO_PRODUCTO = "<s:url action='obtenerProductos' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_COMBO_PLAN = "<s:url action='obtenerComboPlanesProducto' namespace='/combos-catbo'/>";
	var _ACTION_OBTENER_COMBO_FORMA_PAGO = "<s:url action='getComboInstrumentoPago' namespace='/combos-catbo'/>";
	var _ACTION_OBTENER_COMBO_MONEDA = "<s:url action='obtenerComboMonedas' namespace='/combos-catbo'/>";
	var _ACTION_OBTENER_COMBO_STATUS = "<s:url action='obtenerComboStatus' namespace='/combos-catbo'/>";
	
	var _ACTION_ENVIAR_CORREO = "<s:url action='enviaMail' namespace='/mail'/>";
	
	var _ACTION_OBTENER_ARCHIVOS = "<s:url action='obtenerArchivos' namespace='/archivos'/>";
	var _ACTION_AGREGAR_ARCHIVO = "<s:url action='agregarArchivos' namespace='/archivos'/>";
	var _ACTION_DESCARGAR_ARCHIVO = "<s:url action='descargarArchivo' namespace='/archivos'/>";
	var _ACTION_BORRAR_ARCHIVO_MOVIMIENTO ="<s:url action='borrarArchivosMovimiento' namespace='/archivos'/>";
	var _ACTION_COMBO_TIPO_ARCHIVO = "<s:url action='obtieneTiposArchivos' namespace='/combos-catbo'/>";
	var _ACTION_AGREGAR_ARCHIVOJSON = "<s:url action='agregarArchivosJSON' namespace='/archivos'/>";
	
	 var CDFORMATOORDEN = "<s:property value='cdformatoorden'/>";
	 
	 // valida status de un caso
	 
	 var _VALIDA_SATUS_CASO = "<s:url action='validaStatusCaso' namespace='/compraTiempo'/>";
	 
	 
	var CDPERSON = "<s:property value='cdperson'/>";
    var CDMATRIZ  = "<s:property value='cdmatriz'/>";
    var NMCASO  = "<s:property value='nmcaso'/>";
    var CDUSUARIO = "";
    var ATTS_DINAMICOS;	  
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
			proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_COMBO}),
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
			ATTS_DINAMICOS = [{html: '<br/><span class="x-form-item" style="font-weight:bold">No se pudo cargar los atributos variables</span>'}]
			<%}
     %>
     
    var itemsPerPage=_NUMROWS;
    var helpMap = new Map();

    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("802")%>    
    
     _URL_AYUDA = "/backoffice/consultaMoviCasos.html";
      
 </script>   
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/consultarCasoDetalle/enviarCorreo.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/consultarMovimientosCaso/consultarMovimientosCaso_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/consultarMovimientosCaso/atributosVariablesMovCaso.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/consultarMovimientosCaso/consultarOIngresarMovimiento.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/consultarMovimientosCaso/consultarMovimientosCaso.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/Archivos/ArchivoPorMovimientoDeUnCaso.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/Archivos/ArchivosPorMovimientoDeUnCasoAll.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/Archivos/AnexarArchivoDigitalizado.js"></script>


<!-- Archivos js para el UploadPanel:   -->
<script src="${ctx}/resources/scripts/util/FileUploader.js" type="text/javascript"></script>
<script src="${ctx}/resources/scripts/configurador/designer/fileTree/Ext.ux.form.BrowseButton.js" type="text/javascript"></script>
<script src="${ctx}/resources/scripts/util/UploadPanel.js" type="text/javascript"></script>

</head>
<body>

   <table>
        <tr><td><div id="formBusqueda" /></td></tr>
        <tr><td><div id="gridListado" /></td></tr>
   </table>
</body>
</html>