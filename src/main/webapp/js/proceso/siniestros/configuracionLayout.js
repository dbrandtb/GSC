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
		fields:['claveAtributo','claveFormatoAtributo','valorMinimo','valorMaximo','columnaExcel', 'claveFormatoFecha']
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
		autoLoad:false,
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
		store : storeAplicaIVARet,			readOnly : true
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
		displayField: 'value',		valueField: 'key',				editable:false,		allowBlank:false
	});
	
    /*////////////////////////////////////////////////////////////////
    ////////////////   DECLARACION DE EDITOR DE INCISOS  ////////////
    ///////////////////////////////////////////////////////////////*/
	//1.- GRID´S
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
					{	header: 'Atributo', 			dataIndex: 'claveAtributo',			flex:3,			editor : cmbAtributos
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
					{	header: 'Formato', 				dataIndex: 'claveFormatoAtributo',	flex:2,			editor : cmbTipoFormato
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
					{	header: 'Valor Minimo',			dataIndex: 'valorMinimo',			flex:2, 		allowBlank: false
						,editor: {		
							xtype: 'numberfield',		decimalSeparator :'.',			allowBlank: false
							//xtype: 'textfield',		editable : true,		allowBlank: false
						}
					},
					{	header: 'Valor M&aacute;ximo',	dataIndex: 'valorMaximo',			flex:2,			allowBlank: false
						,editor: {		
							xtype: 'numberfield',		decimalSeparator :'.',			allowBlank: false
							//xtype: 'textfield',		editable : true,		allowBlank: false
						}
					},
					{	header: 'Columna', 				dataIndex: 'columnaExcel',			flex:2,			editor : cmbCveColumna
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
					{	header: 'Formato Fecha', 		dataIndex: 'claveFormatoFecha',			flex:2,			editor : cmbFormFecha
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
					},
					{	xtype: 'actioncolumn',		width: 30,		sortable: false,		menuDisabled: true,
						items: [{
							icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
							tooltip: 'Quitar inciso',
							scope: this,
							handler: this.onRemoveClick
						}]
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
		}
	});
	gridLayoutProveedor = new EditorLayoutProveedor();
	
	/* PANEL PARA LA BUSQUEDA DE LA INFORMACIÓN DEL ASEGURADO PARA LA BUSQUEDA DE LAS POLIZAS */
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
			id:'botonCotizar',
			icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
			text: 'Guardar Configuraci&oacute;n',
			handler: function() {
				var form = this.up('form').getForm();
				//myMask.show();
				if (form.isValid()){
					//
					var submitValues={};
    				var formulario=panelInicialPral.form.getValues();
    				submitValues['params']=formulario;
    				var datosTablas = [];
    				
    				storeLayoutProveedor.each(function(record,index){
    					debug("Valores del Store --> ",record);
    					datosTablas.push({
							claveAtributo:record.get('claveAtributo'),
							claveFormatoAtributo:record.get('claveFormatoAtributo'),
							valorMinimo:record.get('valorMinimo'),
							valorMaximo:record.get('valorMaximo'),
							columnaExcel:record.get('columnaExcel'),
							claveFormatoFecha:record.get('claveFormatoFecha')
						});
					});
					submitValues['datosTablas'] = datosTablas;
					panelInicialPral.setLoading(true);
					debug("VALORES A ENVIAR A GUARDAR --->");
					debug(submitValues);
					Ext.Ajax.request({
						url: _URL_GUARDA_CONFIGURACION,
						jsonData:Ext.encode(submitValues), // convierte a estructura JSON
						
						success:function(response,opts){
							panelInicialPral.setLoading(false);
							var jsonResp = Ext.decode(response.responseText);
							/*if(jsonResp.success==true){
								var etiqueta;
								var mensaje;
								if(valorAction.ntramite == null) {
									etiqueta = "Guardado";
									mensaje = "Se gener&oacute; el n&uacute;mero de tr&aacute;mite "+ Ext.decode(response.responseText).msgResult; 
								}else{
									etiqueta = "Modificaci&oacute;n";
									mensaje = "Se modific&oacute; el n&uacute;mero de tr&aacute;mite "+ valorAction.ntramite;
								}
								mensajeCorrecto(etiqueta,mensaje,function() {
									Ext.create('Ext.form.Panel').submit( {
										url             : _p12_urlMesaControl
										,standardSubmit : true
										,params         : {
											'smap1.gridTitle'      : 'Siniestros en espera'
											,'smap2.pv_cdtiptra_i' : 16
										}
									});
								});
								panelInicialPral.getForm().reset();
								storeFacturaDirecto.removeAll();
								windowLoader.close();
							}else{
								Ext.Msg.show({
									title:'Error',
									msg: 'Error en el guardado del alta de tr&aacute;mite',
									buttons: Ext.Msg.OK,
									icon: Ext.Msg.ERROR
								});
								respuesta= false;
							}*/
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
				}else {
					Ext.Msg.show({
						title:'Datos incompletos',
						msg: 'Favor de introducir todos los campos requeridos',
						buttons: Ext.Msg.OK,
						icon: Ext.Msg.WARNING
					});
				}
			}
		}]
	});
	
	if(valorAction.ntramite != null){
		debug("Valores de recuperacion", valorAction);
		storeProveedor.load();
		storeSecuenciaIVA.load();
		storeAplicaIVARet.load();
		panelInicialPral.down('combo[name=cmbProveedor]').setValue(valorAction.cdpresta);
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
					debug("VALOR DEL JSON GRID ====> : ", json);
						for(var i = 0; i < json.length; i++){
							var rec = new modeLayoutProveedor({
								claveAtributo: json[i].CVEATRI,
								claveFormatoAtributo: json[i].CVEFORMATO,
								valorMinimo: json[i].VALORMIN,
								valorMaximo: json[i].VALORMAX,
								columnaExcel: json[i].CVEEXCEL,
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