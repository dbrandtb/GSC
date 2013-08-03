	//READER Y STORE PARA EL ENCABEZADO DE LA PANTALLA
	var readerEncMovimientosCaso = new Ext.data.JsonReader({
					root:'MListEncMovimientosCaso',
					totalProperty: 'totalCount',
					successProperty : '@success'
					},
					[
					{name: 'cdProceso', type: 'string'},
					{name: 'desProceso', type: 'string'},
					{name: 'indSemafColor', type: 'string'},
					{name: 'cdMatriz', type: 'string'},
					{name: 'cdNivatn', type: 'string'},
					{name: 'nmCaso', type: 'string'},
					{name: 'cdOrdenTrabajo', type: 'string'},
					{name: 'cdFormatoOrden', type: 'string'},
					{name: 'cdModulo', type: 'string'},
					{name: 'desModulo', type: 'string'},
					{name: 'dsNivatn', type: 'string'},
					{name:'desPrioridad', type:'string'},
					{name:'cdPrioridad',type:'string'}
					]
				);

	var storeEncMovimientosCaso = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_ENCABEZADO}),
	reader: readerEncMovimientosCaso
	});
	
	
	
	//STORE DE LA GRILLA
	var storeGridMovimientos = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_LISTADO_MOVIMIENTOS_CASO}),
	reader: new Ext.data.JsonReader({
					root:'MListMovimientosCaso',
					totalProperty: 'totalCount',
					successProperty : '@success'
				},
				[
				{name: 'nmovimiento', type: 'string'},
				{name: 'cdStatus', type: 'string'},
				{name: 'desStatus', type: 'string'},
				{name: 'dsObservacion', mapping: 'observacion',type: 'string'},
				{name: 'feRegistro', type: 'string'}				
				]
			)
	});
	
	//STORE DEL BLOQUE ESTATICO
	var storeDatosMovimiento = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_DATOS_DEL_MOVIMIENTO}),
	reader: new Ext.data.JsonReader({
					root:'MListDatosMovimientoCaso',
					totalProperty: 'totalCount',
					successProperty : '@success'
				},
				[
				{name: 'cdPrioridad', type: 'string'},
				{name: 'desPrioridad', type: 'string'},
				{name: 'nmovimiento', type: 'string'},
				{name: 'cdModulo', type: 'string'},
				{name: 'desModulo', type: 'string'},
				{name: 'cdStatus', type: 'string'},
				{name: 'desStatus', type: 'string'},
				{name: 'cdFormatoOrden', type: 'string'},
				{name: 'feRegistro', type: 'string'},
				{name: 'cdNivatn', type: 'string'},
				{name: 'dsNivatn', type: 'string'},
				{name: 'dsObservacion', type: 'string'},
				{name: 'cdUsuario', type: 'string'},
				{name: 'desUsuario', type: 'string'}
				]
			)
	});
	
	//STORE PARA LAS SECCIONES DINAMICAS
	var storeAtributosVariables = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_ATRIBUTOS_VARIABLES}),
	reader: new Ext.data.JsonReader(
		{
			root:'MListAtributosVariables',
			totalProperty: 'totalCount',
			successProperty : '@success'
		},
		[
			{name: 'cdFormatoOrden', type: 'string'},
			{name: 'dsFomatoOrden', type: 'string'},
			{name: 'cdSeccion', type: 'string'},
			{name: 'dsSeccion', type: 'string'},
			{name: 'cdAtribu', type: 'string'},
			{name: 'dsAtribu', type: 'string'},
			{name: 'cdBloque', type: 'string'},
			{name: 'dsBloque', type: 'string'},
			{name: 'cdcampo', type: 'string'},
			{name: 'dsCampo', type: 'string'},
			{name: 'otTabval', type: 'string'},
			{name: 'swFormat', type: 'string'},
			{name: 'nmLmax', type: 'string'},
			{name: 'nmlmin', type: 'string'},
			{name: 'cdexpres', type: 'string'},
			{name: 'nmorden', type: 'string'}
		]
		)
	});
	
	//STORE PARA LOS DATOS DEL BLOQUE DE DATOS DINAMICOS
	var storeDatosAtributosVariables = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_DATOS_ATRIBUTOS_VARIABLES}),
	reader: new Ext.data.JsonReader(
		{
			root:'MListaSeccionesOrden',
			totalProperty: 'totalCount',
			successProperty : '@success'
		},
		[
			{name: 'cdformatoorden', type: 'string'},
			{name: 'cdseccion', type: 'string'},
			{name: 'cdatribu', type: 'string'},
			{name: 'cdordentrabajo', type: 'string'},
			{name: 'otvalor', type: 'string'}
		]
		)
	});  
	
	
	//STORE DE COMBOS
	function obtenerStoreParaCombo(_tableId){
		var store = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_COMBO}),
			reader: new Ext.data.JsonReader(
				{
					root:'comboDatosCatalogo',
					totalProperty: 'totalCount',
					successProperty : '@success'
				},
				[
					{name: 'codigo',mapping:'id', type: 'string'},
					{name: 'descripcion',mapping:'texto', type: 'string'}
				]
				)
		});
		store.load({
					params:{cdTabla:_tableId},
					callback:function(){
									if(_tableId=='CATBOPRIOR'){
											//alert(storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].desPrioridad);
											Ext.getCmp('cmbPrioridadId').setValue(storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].cdPrioridad);
											
										}
							}
					});
		return store;
	};
	
	var storeComboStatus = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_COMBO_STATUS}),
			reader: new Ext.data.JsonReader(
				{
					root:'elementosComboBox',
					totalProperty: 'totalCount',
					successProperty : '@success'
				},
				[
					{name: 'codigo', type: 'string'},
					{name: 'descripcion', type: 'string'}
				]
				)
		});
		storeComboStatus.load();	 
	
	//STORE DE LA GRILLA DE USUARIOS RESPONSABLES
	var storeGridUsrMCaso = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_USR_RES_MCASO}),
	reader: new Ext.data.JsonReader(
		{
			root:'MUsuariosResponsablesList',
			totalProperty: 'totalCount',
			successProperty : '@success'
		},
		[
			{name: 'cdUsuario', type: 'string'},
			{name: 'desUsuario', type: 'string'},
			{name: 'cdRolmat', type: 'string'},
			{name: 'desRolmat', type: 'string'}			
		]
		)
		
		
		
		
});

	//STORE DE LA GRILLA DE USUARIOS RESPONSABLES MOVIMIENTO
	var storeGridUsrMConsulta = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_USR_RES_MCONSULTA}),
	reader: new Ext.data.JsonReader(
		{
			root:'MListResponsablesMCaso',
			totalProperty: 'totalCount',
			successProperty : '@success'
		},
		[
			{name: 'cdUsuario', type: 'string'},
			{name: 'desUsuario', type: 'string'},
			{name: 'cdRolmat', type: 'string'},
			{name: 'desRolmat', type: 'string'},
			{name: 'email', type: 'string'}			
		]
		)
		

		
		
});