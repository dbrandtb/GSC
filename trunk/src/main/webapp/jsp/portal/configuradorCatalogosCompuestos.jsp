<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Configuracion de Procesos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap = new Map();</script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_OBENER_CAMPOS_BUSQUEDA = "<s:url action='obtenerDatosGrilla' namespace='/configurarCatalogosCompuestos'/>";
    var _ACTION_GUARDAR_REGISTRO = "<s:url action='guardarRegistro' namespace='/configurarCatalogosCompuestos'/>";
    var _ACTION_ACTUALIZAR_REGISTRO = "<s:url action='actualizarRegistro' namespace='/configurarCatalogosCompuestos'/>";
    var _ACTION_BORRAR_REGISTRO = "<s:url action='borrarRegistro' namespace='/configurarCatalogosCompuestos'/>";
    var _ACTION_EXPORTAR = "<s:url action='exportarCatalogosCompuestos' namespace='/configurarCatalogosCompuestos'/>";

	//var _ACTION_COMBO_GENERICO = "<s:url action='comboCatalogosDinamicos' namespace='/combos'/>";
	var _ACTION_COMBO_GENERICO = "<s:url action='comboCatalogosCompuestos' namespace='/combos'/>";

/*	var _ACTION_OBTENER_PROCESO = "<s:url action='obtenerProceso' namespace='/configuracionProcesos'/>";	
	var _ACTION_ACTUALIZAR_PROCESO = "<s:url action='actualizarProceso' namespace='/configuracionProcesos'/>";	
	var _ACTION_GUARDAR_PROCESO = "<s:url action='guardarProceso' namespace='/configuracionProcesos'/>";	
    var _ACTION_BORRAR_PROCESO = "<s:url action='borrarProceso' namespace='/configuracionProcesos'/>";
    var _ACTION_EXPORT_PROCESOS = "<s:url action='exportarProcesos' namespace='/configuracionProcesos'/>"; 
	//var _ACTION_BACK_TO_SOMEWHERE = "<s:url action='' namespace='/'/>";

	var _ACTION_COMBO_ASEGURADORAS = "<s:url action='obtenerAseguradoras' namespace='/combos'/>";
	var _ACTION_COMBO_PROCESOS = "<s:url action='obtenerTiposProcesos' namespace='/combos'/>";
	var _ACTION_COMBO_CLIENTES = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
	var _ACTION_COMBO_INDICADOR = "<s:url action='obtenerCatalogo' namespace='/combos'/>";
	var _ACTION_COMBO_PRODUCTOS = "<s:url action='obtenerProductosPorAseguradora' namespace='/combos'/>";
*/
    //var itemsPerPage=20;
    var itemsPerPage = _NUMROWS;
    var vari= 0;
    var CODIGO_PANTALLA = "<s:property value='cdPantalla'/>";
    var TITULO_PANTALLA = '<%=session.getAttribute("dsTitulo")%>';
    var indexStore = 0;
    //var itemsFormBusqueda = <%=session.getAttribute("camposForm")%>;//[{xtype: 'hidden'}];
    //var gridColumModel = <%=session.getAttribute("gridColumnModel")%>;
    
    	var poolStores = new Map();
		var readerDatosGenericos = new Ext.data.JsonReader({
				root: 'comboGenerico',
				totalProperty: 'totalCount',
				successProperty: 'success'
				}, [
					{name: 'codigo', type: 'string', mapping: 'codigo'},
					{name: 'descripcion', type: 'string', mapping: 'descripcion'}
				]
		);

		function crearStoreGenerico (_idColumna, _comboId, _value) {
			var dsGenerico;
			dsGenerico = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
							url: _ACTION_COMBO_GENERICO
				}),
				reader: readerDatosGenericos
			});

			poolStores.put(indexStore, {id: _comboId, store: dsGenerico});
			indexStore++;
			/*dsGenerico.load({
				params: {
					cdColumna: _idColumna
				},
				callback: function(r, opt, success) {
					//Ext.getCmp(_comboId).setValue(_value);
				}
			});*/
			return dsGenerico;
		}	

	/**
	*	Ejecuta secuencialmente el load del store de
	*	cada combo definido en la pantalla.
	*/
	function processStores () {
		if (indexStore < 0) return;

		var obj = poolStores.get(indexStore); 
		if (obj != null && obj != undefined) {
			var _store = obj.store;
			_store.load({
				params: {
					cdColumna: obj.id //_idColumna
				},
				callback: function(r, opt, success) {
					//Ext.getCmp(_comboId).setValue(_value);
					indexStore--;
					processStores();
				}
			});
		}/*else {
			indexStore--;
			processStores();
		}*/
	}
    var itemsFormBusqueda = <%if (session.getAttribute("camposForm") != null) {%> <%=session.getAttribute("camposForm")%> <%;} else {%>[{xtype: 'hidden'}]; <%}%>

    var itemsFormABM = <%if (session.getAttribute("camposFormEdit") != null) {%> <%=session.getAttribute("camposFormEdit")%> <%;} else {%>[{xtype: 'hidden'}]; <%}%>
    var itemsFormAdd = <%if (session.getAttribute("camposFormAdd") != null) {%> <%=session.getAttribute("camposFormAdd")%> <%;} else {%>[{xtype: 'hidden'}]; <%}%>
    var gridColumModel = <%if (session.getAttribute("gridColumnModel") != null) {%> <%=session.getAttribute("gridColumnModel")%> <%;} else {%>[{header: ''}]; <%}%>
    var recordsReader = <%=session.getAttribute("recordsReader")%>;
    var dynaClass = <%=session.getAttribute("dynaClass")%>
    
    var hiddenBtnEditar = <%if (session.getAttribute("valorHiddenBtnEditar") != null) {%> <%=session.getAttribute("valorHiddenBtnEditar")%> <%;} else {%>[{xtype: 'hidden'}]; <%}%>
    var hiddenBtnAgregar = <%if (session.getAttribute("valorHiddenBtnAgregar") != null) {%> <%=session.getAttribute("valorHiddenBtnAgregar")%> <%;} else {%>[{xtype: 'hidden'}]; <%}%>
    var hiddenBtnBorrar = <%if (session.getAttribute("valorHiddenBtnBorrar") != null) {%> <%=session.getAttribute("valorHiddenBtnBorrar")%> <%;} else {%>[{xtype: 'hidden'}]; <%}%>
        
    /*
    	<%=session.getAttribute("camposForm")%>
    */
    <%=session.getAttribute("helpMap")%>
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configuradorCatalogos/agregarRegistrosCatalogosCompuestos.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configuradorCatalogos/editarRegistrosCatalogosCompuestos.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configuradorCatalogos/configuradorCatalogosCompuestos.js"></script>

</head>
<body>

   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formBusqueda"></div>
            </td>
        </tr>
       <tr valign="top">
           <td class="headlines" colspan="2">
               <div id="gridElementos"></div>
           </td>
       </tr>
    </table>
</body>
</html>