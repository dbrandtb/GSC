Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
Ext.onReady(function() {
	var valorIndexSeleccionado= null;
	var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"loading..."});
	
	Ext.selection.CheckboxModel.override( {
		mode: 'SINGLE',
		allowDeselect: true
	});
	//Models:
	Ext.define('modelFacturaSiniestro', {
		extend:'Ext.data.Model',
		fields:['noFactura','fechaFactura','tipoServicio',
				'tipoServicioName','proveedor','proveedorName',
				'importe','tipoMoneda','tipoMonedaName','tasaCambio','importeFactura']
	});
	
	Ext.define('modeLayoutProveedor', {
		extend:'Ext.data.Model',
		fields:['claveAtributo','claveFormatoAtributo','valorMinimo','valorMaximo','columnaExcel', 'claveFormatoFecha', "atributoRequerido"]
	});
	
	Ext.define('modelListadoProvMedico',{
		extend: 'Ext.data.Model',
			fields: [
				{type:'string', 	name:'cdpresta'},				{type:'string',		name:'nombre'},
				{type:'string',		name:'cdespeci'},				{type:'string',		name:'descesp'}
			]
	});
	
	var storeFormatoAtributo = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_TFORMATOS},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeFormatoAtributo.load();

	var storeAtributos = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_ATRIBUTO_LAYOUT},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeAtributos.load();
	
	var storeCveColumna = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_CVECOLUMNA},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeCveColumna.load();
	
	var storeFormFecha = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_FORMATOFECHA},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeFormFecha.load();
	
	var storeFacturaDirecto =new Ext.data.Store({
		autoDestroy: true,
		model: 'modelFacturaSiniestro'
	});
	
	var storeLayoutProveedor =new Ext.data.Store({
		autoDestroy: true,
		model: 'modeLayoutProveedor'
	});
	
	var storeProveedor = Ext.create('Ext.data.Store', {
		model:'modelListadoProvMedico',
		autoLoad:true,
		proxy: {
			type: 'ajax',
			url : _URL_CATALOGOS,
			extraParams:{
				catalogo         : _CATALOGO_PROVEEDORES,
				catalogoGenerico : true
			},
			reader: {
				type: 'json',
				root: 'listaGenerica'
			}
		}
	});

	var storeAplicaIVA = Ext.create('Ext.data.Store', {
		model:'Generic',
		autoLoad:true,
		proxy: {
			type	: 'ajax',
			url: _URL_CATALOGOS,
			extraParams	: {catalogo:_SINO},
			reader: {
				type	:	'json',
				root	:	'lista'
			}
		}
	});
	storeAplicaIVA.load();
	
	var storeSecuenciaIVA = Ext.create('Ext.data.Store', {
		model:'Generic',
		autoLoad:true,
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_SECUENCIA},
			reader: {
				type:	'json',
				root:	'lista'
			}
		}
	});
	storeSecuenciaIVA.load();
	
	var storeAplicaIVARet = Ext.create('Ext.data.Store', {
		model:'Generic',
		autoLoad:true,
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_SINO},
			reader: {
				type:	'json',
				root:	'lista'
			}
		}
	});
	storeAplicaIVARet.load();
	
	var storeCampoRequerido= Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_SINO},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeCampoRequerido.load();
	
	var storeTipoLayout = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_ConfLayout},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeTipoLayout.load();
	
	//Informacion proveedor
	cmbProveedor = Ext.create('Ext.form.field.ComboBox', {
		fieldLabel : 'Proveedor',			displayField : 'nombre',			name:'cmbProveedor',			valueField : 'cdpresta',
		forceSelection : true,				matchFieldWidth: false,				queryMode :'remote',			queryParam : 'params.cdpresta',
		minChars	: 2,					store : storeProveedor,				triggerAction: 'all',			hideTrigger:true,
		width		: 400,					readOnly : true
	}); 
	//Aplica IVA
	aplicaIVADesc = Ext.create('Ext.form.field.ComboBox',{
		fieldLabel   : 'Aplica IVA',		displayField : 'value',				name:'idaplicaIVA',				valueField:'key',
		forceSelection  : true,				queryMode    :'local',				editable   : false,
		store : storeAplicaIVA,				readOnly : true
	});
	//Secuencia IVA
	secuenciaIVADesc = Ext.create('Ext.form.field.ComboBox',{
		fieldLabel   : 'Secuencia IVA',		displayField : 'value',				name:'secuenciaIVA',			valueField:'key',
		forceSelection  : true,				queryMode    :'local',				editable   : false,
		store : storeSecuenciaIVA,			readOnly : true
	});
	//Aplica IVA Retenido
	aplicaIVARETDesc = Ext.create('Ext.form.field.ComboBox',{
		fieldLabel   : 'Aplica IVA Retenido',	displayField : 'value',			name:'idaplicaIVARET',			valueField:'key',
		forceSelection  : true,				queryMode    :'local',				editable   : false,
		store : storeAplicaIVARet,			readOnly : true,					colspan	   :2
	});
	
	tipoLayout= Ext.create('Ext.form.field.ComboBox',{
		fieldLabel   : 'Layout',			displayField : 'value',				name:'tipoLayout',				valueField: 'key',
		forceSelection  : true,				queryMode    :'local',				editable   : false,
		store : storeTipoLayout,			readOnly : true,					colspan	   :2
	});
	
	//Datos informacion Grid
	cmbAtributos = Ext.create('Ext.form.ComboBox',{
		id:'cmbAtributos',			store: storeAtributos,			queryMode:'local',
		displayField: 'value',		valueField: 'key',				editable:false,		allowBlank:false
	});
	
	cmbTipoFormato = Ext.create('Ext.form.ComboBox',{
		id:'cmbTipoFormato',		store: storeFormatoAtributo,	queryMode:'local',
		displayField: 'value',		valueField: 'key',				editable:false,		allowBlank:false
	});
	
	cmbCveColumna = Ext.create('Ext.form.ComboBox',{
		id:'cmbCveColumna',			store: storeCveColumna,			queryMode:'local',
		displayField: 'value',		valueField: 'key',				editable:false,		allowBlank:false
	});
	
	cmbFormFecha = Ext.create('Ext.form.ComboBox',{
		id:'cmbFormFecha',			store: storeFormFecha,			queryMode:'local',
		displayField: 'value',		valueField: 'key',				editable:false
	});
	
	cmbAplicaRequerido = Ext.create('Ext.form.ComboBox', {
		name:'idAplicaRequerido',		store: storeCampoRequerido,		queryMode:'local',
		displayField: 'value',		valueField: 'key',				editable:false,				allowBlank :false
	});
	
    /*////////////////////////////////////////////////////////////////
    ////////////////   DECLARACION DE EDITOR DE INCISOS  ////////////
    ///////////////////////////////////////////////////////////////*/
	//1.- GRID�S
	Ext.define('EditorLayoutProveedor',{
		extend: 'Ext.grid.Panel',
		name:'editorLayoutProveedor',
		title: 'Configuraci&oacute;n Atributos',
		frame: true,
		selType  : 'rowmodel',
		initComponent: function(){
			Ext.apply(this, {
				width: 750,
				height: 350,
				plugins  : [
					Ext.create('Ext.grid.plugin.CellEditing', {
						clicksToEdit: 1
						,listeners : {
							beforeedit : function() {
								valorIndexSeleccionado = gridLayoutProveedor.getView().getSelectionModel().getSelection()[0];
							}
						}
					})
				],
				store: storeLayoutProveedor,
				columns: 
				[
					{	xtype: 'actioncolumn',		width: 30,		sortable: false,		menuDisabled: true,
						items: [{
							icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
							tooltip: 'Quitar inciso',
							scope: this,
							handler: this.onRemoveClick
						}]
					},
					{	header: 'Atributo', 			dataIndex: 'claveAtributo',			width : 150 ,			editor : cmbAtributos,			allowBlank: false
						,renderer : function(v) {
							var leyenda = '';
							if (typeof v == 'string'){
								storeAtributos.each(function(rec) {
									if (rec.data.key == v) {
										leyenda = rec.data.value;
									}
								});
							}else{
								if (v.key && v.value){
									leyenda = v.value;
								} else {
									leyenda = v.data.value;
								}
							}
							return leyenda;
						}
					},
					{	header: 'Formato', 				dataIndex: 'claveFormatoAtributo',	width : 150,			editor : cmbTipoFormato,		allowBlank: false
						,renderer : function(v) {
							var leyenda = '';
							if (typeof v == 'string'){
								storeFormatoAtributo.each(function(rec) {
									if (rec.data.key == v) {
										leyenda = rec.data.value;
									}
								});
							}else{
								if (v.key && v.value){
									leyenda = v.value;
								} else {
									leyenda = v.data.value;
								}
							}
							return leyenda;
						}
					},
					{	header: 'Valor Minimo',			dataIndex: 'valorMinimo',			width : 100
						,editor: {		
							xtype: 'numberfield',		decimalSeparator :'.'//,			allowBlank: false
						}
					},
					{	header: 'Valor M&aacute;ximo',	dataIndex: 'valorMaximo',			width : 100//,			allowBlank: false
						,editor: {		
							xtype: 'numberfield',		decimalSeparator :'.'//,			allowBlank: false
						}
					},
					{	header: 'Columna', 				dataIndex: 'columnaExcel',			width : 100,			editor : cmbCveColumna
						,renderer : function(v) {
							var leyenda = '';
								if (typeof v == 'string'){
									storeCveColumna.each(function(rec) {
										if (rec.data.key == v) {
											leyenda = rec.data.value;
										}
									});
								}else{
									if (v.key && v.value){
										leyenda = v.value;
									} else {
										leyenda = v.data.value;
									}
								}
								return leyenda;
						}
					},
					{
						header: 'Requerido', 				dataIndex: 'atributoRequerido',	width : 100		,  allowBlank: false
						,editor : cmbAplicaRequerido
						,renderer : function(v) {
						var leyenda = '';
							if (typeof v == 'string') {
								storeCampoRequerido.each(function(rec) {
									if (rec.data.key == v) {
										leyenda = rec.data.value;
									}
								});
							}else {
								if (v.key && v.value) {
									leyenda = v.value;
								} else {
									leyenda = v.data.value;
								}
							}
							return leyenda;
						}
					},
					{	header: 'Formato Fecha', 		dataIndex: 'claveFormatoFecha',			width : 120,			editor : cmbFormFecha
						,renderer : function(v) {
							var leyenda = '';
							if (typeof v == 'string'){
								storeFormFecha.each(function(rec) {
									if (rec.data.key == v) {
										leyenda = rec.data.value;
									}
								});
							}else{
								if (v.key && v.value){
									leyenda = v.value;
								} else {
									leyenda = v.data.value;
								}
							}
							return leyenda;
						}
					}
				],
				selModel: {
					selType: 'cellmodel'
				},
				tbar: [{
					text     : 'Agregar Atributo'
					,icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/book.png'
					,handler : _p21_agregarGrupoClic
				}]
			});
			this.callParent();
		},
		onRemoveClick: function(grid, rowIndex){
			var record=this.getStore().getAt(rowIndex);
			this.getStore().removeAt(rowIndex);
		}
	});
	gridLayoutProveedor = new EditorLayoutProveedor();
	
	/* PANEL PARA LA BUSQUEDA DE LA INFORMACI�N DEL ASEGURADO PARA LA BUSQUEDA DE LAS POLIZAS */
	var panelInicialPral= Ext.create('Ext.form.Panel',{
		border		: 0,
		id			: 'panelInicialPral',
		renderTo	: 'div_clau21',
		bodyPadding	: 5,
		width		: 758,
		layout		: {
			type	: 'table'
			,columns: 2
		},
		defaults 	: {
			style	: 'margin:5px;'
		},
		items		:[
			cmbProveedor,
			tipoLayout,
			aplicaIVADesc,
			secuenciaIVADesc,
			aplicaIVARETDesc,
			{	colspan:2
				,border: false
				,items	:
				[
					gridLayoutProveedor
				]
			}
		],
		buttonAlign:'center',
		buttons: [{
			id:'botonGuardar',
			icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
			text: 'Guardar Configuraci&oacute;n',
			handler: function() {
				var form = this.up('form').getForm();
				if (form.isValid()){
					//myMask.show();
					var submitValues={};
    				var formulario=panelInicialPral.form.getValues();
    				submitValues['params']=formulario;
    				var datosTablas = [];
    				
    				storeLayoutProveedor.each(function(record,index){
    					debug("Valor del record ==>",record);
    					datosTablas.push({
							claveAtributo:record.get('claveAtributo'),
							claveFormatoAtributo:record.get('claveFormatoAtributo'),
							valorMinimo:record.get('valorMinimo'),
							valorMaximo:record.get('valorMaximo'),
							columnaExcel:record.get('columnaExcel'),
							atributoRequerido : record.get('atributoRequerido'),
							claveFormatoFecha:record.get('claveFormatoFecha'),
							tipoLayout : valorAction.tipoLayout
						});
					});
					
					claveAtributo = 0;
					bandera = 0;
					columnaExcel = 0;
					for(var i = 0; i < datosTablas.length; i++){
						for (var j = i + 1; j < datosTablas.length; j++){
							if(datosTablas[i].claveAtributo == datosTablas[j].claveAtributo){
								claveAtributo++;
							}
							
							if(datosTablas[i].columnaExcel == datosTablas[j].columnaExcel){
								columnaExcel++;
							}
						}
					}
					
					if(claveAtributo > 0){
						bandera =1;
						Ext.Msg.show({
							title: 'Aviso',
							msg: 'Existen atributos duplicados, favor de verificar',
							buttons: Ext.Msg.OK,
							icon: Ext.Msg.WARNING
						});
					}
					
					if(columnaExcel > 0){
						bandera =1;
						Ext.Msg.show({
							title: 'Aviso',
							msg: 'Existen columnas duplicados, favor de verificar',
							buttons: Ext.Msg.OK,
							icon: Ext.Msg.WARNING
						});
					}
					
					if(bandera != '1'){
						submitValues['datosTablas'] = datosTablas;
						panelInicialPral.setLoading(true);
						Ext.Ajax.request({
							url: _URL_GUARDA_CONFIGURACION,
							jsonData:Ext.encode(submitValues),
							success:function(response,opts){
								panelInicialPral.setLoading(false);
								var jsonResp = Ext.decode(response.responseText);
								if(jsonResp.success==true){
									mensajeCorrecto("Guardado","La configuraci&oacute;n se guardo correctamente.",null);
									panelInicialPral.getForm().reset();
									storeFacturaDirecto.removeAll();
									windowLoader.close();
								}else{
									Ext.Msg.show({
										title:'Error',
										msg: 'Error en el guardado de la configuraci&oacute;n',
										buttons: Ext.Msg.OK,
										icon: Ext.Msg.ERROR
									});
									respuesta = false;
								}
							},
							failure:function(response,opts){
								panelInicialPrincipal.setLoading(false);
								Ext.Msg.show({
									title:'Error',
									msg: 'Error de comunicaci&oacute;n',
									buttons: Ext.Msg.OK,
									icon: Ext.Msg.ERROR
								});
							}
						});
					}
				}else {
					Ext.Msg.show({
						title:'Datos incompletos',
						msg: 'Favor de introducir todos los campos requeridos',
						buttons: Ext.Msg.OK,
						icon: Ext.Msg.WARNING
					});
				}
			}
		}
		]
	});
	
	if(valorAction.ntramite != null){
		storeProveedor.load();
		storeSecuenciaIVA.load();
		storeAplicaIVARet.load();
		
		storeProveedor.load({
			params:{
				'params.cdpresta': valorAction.cdpresta
			}
		});
		panelInicialPral.down('combo[name=cmbProveedor]').setValue(valorAction.cdpresta);
		panelInicialPral.down('combo[name=tipoLayout]').setValue(valorAction.tipoLayout);
		panelInicialPral.down('combo[name=idaplicaIVA]').setValue(valorAction.idaplicaIVA);
		panelInicialPral.down('combo[name=secuenciaIVA]').setValue(valorAction.secuenciaIVA);
		panelInicialPral.down('combo[name=idaplicaIVARET]').setValue(valorAction.idaplicaIVARET);
		
		Ext.Ajax.request({
			url		: _URL_CONSULTA_CONF_LAYOUT
			,params	:{
				'params.cdpresta': valorAction.cdpresta
			}
			,success : function (response)
			{
				debug("<=== Valor de Respuesta ===> ",Ext.decode(response.responseText));
				if(Ext.decode(response.responseText).datosInformacionAdicional != null)
				{
					var json=Ext.decode(response.responseText).datosInformacionAdicional;
					for(var i = 0; i < json.length; i++){
						var rec = new modeLayoutProveedor({
							claveAtributo: json[i].CVEATRI,
							claveFormatoAtributo: json[i].CVEFORMATO,
							valorMinimo: json[i].VALORMIN,
							valorMaximo: json[i].VALORMAX,
							columnaExcel: json[i].CVEEXCEL,
							atributoRequerido : json[i].SWOBLIGA,
							claveFormatoFecha: json[i].FORMATFECH
							
						});
						storeLayoutProveedor.add(rec);
					}
				}
			},
			failure : function (){
				me.up().up().setLoading(false);
				Ext.Msg.show({
					title:'Error',
					msg: 'Error de comunicaci&oacute;n',
					buttons: Ext.Msg.OK,
					icon: Ext.Msg.ERROR
				});
			}
		});
	}
	function _p21_agregarGrupoClic(){
		storeLayoutProveedor.add(new modeLayoutProveedor({atributo:''}));
	}
});