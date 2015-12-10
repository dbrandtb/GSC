Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
Ext.onReady(function() {
	var panelProveedor;
	Ext.selection.CheckboxModel.override( {
		mode: 'SINGLE',
		allowDeselect: true
	});
	//Declaracion de los modelos 
	Ext.define('modeloProveedores',{
		extend:'Ext.data.Model',
		fields:['CLAVEPROVEEDOR','NOMBPROVEEDOR','APLICAIVA','APLICAIVADESC','SECUENCIAIVA','SECIVADESC','APLICAIVARET','IVARETDESC']
	});
	
	Ext.define('modelListadoProvMedico',{
		extend: 'Ext.data.Model',
		fields: [
			{type:'string',		name:'cdpresta'},
			{type:'string',		name:'nombre'},
			{type:'string',		name:'cdespeci'},
			{type:'string',		name:'descesp'}
		]
	});
	//Declaracion de los stores
	var storeGridProveedores = new Ext.data.Store({
		pageSize	: 10
		,model		: 'modeloProveedores'
		,autoLoad	: false
		,proxy		: {
			enablePaging	:	true,
			reader			:	'json',
			type			:	'memory',
			data			:	[]
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
	
	var storeProveedorMod = Ext.create('Ext.data.Store', {
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

	cmbProveedor = Ext.create('Ext.form.field.ComboBox', {
		fieldLabel : 'Proveedor',			displayField : 'nombre',			name:'params.cmbProveedor',
		width		 : 550,					valueField   : 'cdpresta',			forceSelection : true,
		matchFieldWidth: false,				queryMode :'remote',				queryParam: 'params.cdpresta',
		minChars  : 2,						store : storeProveedor,				triggerAction: 'all',
		hideTrigger:true
	});
	
	cmbProveedorModificable = Ext.create('Ext.form.field.ComboBox', {
		fieldLabel : 'Proveedor',			displayField : 'nombre',			name:'params.cmbProveedorMod',
		width		 : 550,					valueField   : 'cdpresta',			forceSelection : true,
		matchFieldWidth: false,				queryMode :'remote',				queryParam: 'params.cdpresta',
		minChars  : 2,						store : storeProveedorMod,			triggerAction: 'all',
		labelWidth : 170,					hideTrigger:true,					allowBlank:false,
		listeners : {
			'select':function(e){
				Ext.Ajax.request({
					url: _URL_CONSULTA_PROVEEDOR,
					params: {
						'params.cdpresta'  : e.getValue()
					},
					success: function(response) {
						var res = Ext.decode(response.responseText);
						if(res.datosValidacion &&res.datosValidacion.length == 0) {
							//Preguntamos que ya existe el proveedor dado de alta
						}else{
							var jsonRes = Ext.decode(response.responseText).datosValidacion[0];
							debug("Valor -->",jsonRes.APLICAIVA);
							centrarVentanaInterna(Ext.Msg.show({
								title: 'Aviso',
								msg: '&iquest;El proveedor ya se encuentra configurado. Desea cargar la informaci&oacute;n ?',
								buttons: Ext.Msg.YESNO,
								icon: Ext.Msg.QUESTION,
								fn: function(buttonId, text, opt){
									if(buttonId == 'yes'){
										panelProveedor.down('combo[name=params.idaplicaIVARET]').setValue(jsonRes.APLICAIVARET);
										panelProveedor.down('combo[name=params.idaplicaIVA]').setValue(jsonRes.APLICAIVA);
										panelProveedor.down('combo[name=params.secuenciaIVA]').setValue(jsonRes.SECUENCIAIVA);
										panelProveedor.down('[name=params.proceso]').setValue('U');
									}else{
										panelProveedor.down('[name=params.proceso]').setValue('U');
									}
								}
							}));
						}
					},
					failure: function(){
						centrarVentanaInterna(mensajeError('No se pudo obtener la informaci&oacute;n.'));
					}
				});
			}
		}
	});
	
	aplicaIVA = Ext.create('Ext.form.field.ComboBox',{
		colspan	   :2,				fieldLabel   : 'Aplica IVA',		allowBlank		: false,
		editable   : false,			displayField : 'value',				valueField:'key',			    		forceSelection  : true,
		labelWidth : 170,			queryMode    :'local',				editable  :false,						name			:'params.idaplicaIVA',
		width		 : 350,			store : storeAplicaIVA
	});

	aplicaIVARET = Ext.create('Ext.form.field.ComboBox',{
		colspan	   :2,				fieldLabel   : 'Aplica IVA Retenido',allowBlank		: false,
		editable   : false,			displayField : 'value',				valueField:'key',			    		forceSelection  : true,
		labelWidth : 170,			queryMode    :'local',				editable  :false,						name			:'params.idaplicaIVARET',
		width		 : 350,			store : storeAplicaIVARet
	});
	
	secuenciaIVA = Ext.create('Ext.form.field.ComboBox',{
		colspan	   :2,				fieldLabel   : 'Secuencia IVA',		allowBlank		: false,
		editable   : false,			displayField : 'value',				valueField:'key',			    		forceSelection  : true,
		labelWidth : 170,			queryMode    :'local',				editable  :false,						name			:'params.secuenciaIVA',
		width		 : 350,			store : storeSecuenciaIVA
	});
	
	panelProveedor = Ext.create('Ext.form.Panel', {
		id: 'panelProveedor',
		width: 700,
		url: _URL_MOV_PROVEEDOR,
		bodyPadding: 5,
		items: [
			cmbProveedorModificable,
			aplicaIVA,
			secuenciaIVA,
			aplicaIVARET,
			{	xtype  : 'textfield',	fieldLabel 	: 'Proceso',		labelWidth: 100,
				width	:350,			name   :'params.proceso',		hidden :true
			}
			
		],
		buttonAlign : 'center'
		,buttons: [{
			text: 'Guardar'
			,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
			,buttonAlign : 'center',
			handler: function() {
				if (panelProveedor.form.isValid()) {
					panelProveedor.form.submit({
						waitMsg:'Procesando...',
						failure: function(form, action) {
							//Se cambia el valor 
							Ext.Msg.show({
								title: 'ERROR',
								msg: action.result.errorMessage,
								buttons: Ext.Msg.OK,
								icon: Ext.Msg.ERROR
							});
						},
						success: function(form, action) {
							panelProveedor.form.reset();
							modificacionClausula.close();
							cargarPaginacion();
							mensajeCorrecto('&Eacute;XITO','La configuraci&oacute;n del proveedor fue exitoso.',function(){});
						}
					});
				} else {
					Ext.Msg.show({
						title: 'Aviso',
						msg: 'Complete la informaci&oacute;n requerida',
						buttons: Ext.Msg.OK,
						icon: Ext.Msg.WARNING
					});
				}
			}
		},{
			text: 'Cancelar',
			icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
			buttonAlign : 'center',
			handler: function() {
				modificacionClausula.close();
			}
		}]
	});
	
	modificacionClausula = Ext.create('Ext.window.Window',{
		title        : 'Datos Proveedor'
		,modal       : true
		,closeAction: 'hide'
		,buttonAlign : 'center'
		,width		 : 710
		,height      : 210
		,items       : [
			panelProveedor
		]
	});
	
	var panelInicialPral= Ext.create('Ext.panel.Panel',{
		border    : 0
		,renderTo : 'div_clau'
		,items    : [
			Ext.create('Ext.panel.Panel',{
				border  : 0
				,title: 'Listado de Proveedor'
				,style         : 'margin:5px'
				,layout : {
					type     : 'table'
					,columns : 2
				}
				,defaults : {
					style : 'margin:5px;'
				}
				,items : [
					cmbProveedor
				]
				,buttonAlign: 'center'
				,buttons : [{
					text: "Buscar"
					,icon:_CONTEXT+'/resources/fam3icons/icons/magnifier.png'
					,handler: function(){
						cargarPaginacion();
					}	
				},{
					text: "Limpiar"
					,icon:_CONTEXT+'/resources/fam3icons/icons/arrow_refresh.png'
					,handler: function(){
						panelInicialPral.down('combo[name=params.cmbProveedor]').reset();
						storeGridProveedores.removeAll();
					}	
				}] 
			})
			,Ext.create('Ext.grid.Panel',{
				id             : 'clausulasGridId'
				,title         : 'Proveedores'
				,store         :  storeGridProveedores
				//,collapsible   : true
				,titleCollapse : true
				,style         : 'margin:5px'
				,height        : 400
				,columns       : [
					{	xtype: 'actioncolumn',			width: 120,		sortable: false,		menuDisabled: true,
						items: [{
							icon: _CONTEXT+'/resources/fam3icons/icons/application_edit.png',
							tooltip: 'Editar Proveedor',
							scope: this,
							handler : editarProveedor
				 		},{
							icon: _CONTEXT+'/resources/fam3icons/icons/delete.png',
							tooltip: 'Eliminar Proveedor',
							scope: this,
							handler : eliminarProveedor
						},{
							icon: _CONTEXT+'/resources/fam3icons/icons/application_view_tile.png',
							tooltip: 'Configurar layout',
							scope: this,
							handler : configuracionLayoutProveedor
						}
						,{
							icon:_CONTEXT+'/resources/fam3icons/icons/database_lightning.png',
							tooltip: 'Subir Archivo Conf A',
							scope: this,
							handler : configuracionSubirCargaMasiva
						}
						,{
							icon:_CONTEXT+'/resources/fam3icons/icons/database_lightning.png',
							tooltip: 'Subir Archivo 2',
							scope: this,
							handler : configuracionSubirCargaMasiva2
						}]
					},
					{
						header     : 'Cve Proveedor',dataIndex : 'CLAVEPROVEEDOR',flex      : 1	, hidden   : true
					},
					{
						header     : 'Proveedor',dataIndex : 'NOMBPROVEEDOR',flex      : 1
					},
					{
						header     : 'Aplica IVA',dataIndex : 'APLICAIVA',flex      : 1	, hidden   : true
					},
					{
						header     : 'Aplica IVA'			,dataIndex : 'APLICAIVADESC'
						,flex      : 1
					},
					{
						header     : 'Secuencia IVA'
						,dataIndex : 'SECUENCIAIVA'
						,flex      : 1
						, hidden   : true
					},
					{
						header     : 'Secuencia IVA'
						,dataIndex : 'SECIVADESC'
						,flex      : 1
					},
					{
						header     : 'Aplica IVA Retenido'
						,dataIndex : 'APLICAIVARET'
						,flex      : 1
						, hidden   : true
					},
					{
						header     : 'Aplica IVA Retenido'
						,dataIndex : 'IVARETDESC'
						,flex      : 1
					}
				],
				bbar     :{
				displayInfo : true,
					store		: storeGridProveedores,
					xtype		: 'pagingtoolbar'
				}
				,tbar: [{
					text		: 'Agregar Proveedor'
					,icon		:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/book.png'
					,handler	: agregarProveedor
				}]
			})
		]
	});
	
	
	cargarPaginacion();
	
	function cargarPaginacion(){
		storeGridProveedores.removeAll();
		var params = {
			'params.cdpresta' : panelInicialPral.down('combo[name=params.cmbProveedor]').getValue()
		};
		cargaStorePaginadoLocal(storeGridProveedores, _URL_CONSULTA_PROVEEDOR, 'datosValidacion', params, function(options, success, response){
			if(success){
				var jsonResponse = Ext.decode(response.responseText);
				if(jsonResponse.datosValidacion &&jsonResponse.datosValidacion.length == 0) {
					showMessage("Aviso", "No existe configuraci&oacute;n del proveedor seleccionado.", Ext.Msg.OK, Ext.Msg.INFO);
					return;
				}
			}else{
				showMessage('Error', 'Error al obtener los datos', 
				Ext.Msg.OK, Ext.Msg.ERROR);
			}
		});
	}

	function editarProveedor(grid,rowIndex){
		var record = grid.getStore().getAt(rowIndex);
		storeProveedorMod.load({
			params:{
				'params.cdpresta': record.get('CLAVEPROVEEDOR')
			}
		});
		panelProveedor.down('combo[name=params.cmbProveedorMod]').setValue(record.get('CLAVEPROVEEDOR'));
		panelProveedor.down('combo[name=params.idaplicaIVARET]').setValue(record.get('APLICAIVARET'));
		panelProveedor.down('combo[name=params.idaplicaIVA]').setValue(record.get('APLICAIVA'));
		panelProveedor.down('combo[name=params.secuenciaIVA]').setValue(record.get('SECUENCIAIVA'));
		panelProveedor.down('[name=params.proceso]').setValue('U');
		modificacionClausula.show();
	}
	
	function agregarProveedor(){
		panelProveedor.down('combo[name=params.cmbProveedorMod]').setValue('');
		panelProveedor.down('combo[name=params.idaplicaIVARET]').setValue('S');
		panelProveedor.down('combo[name=params.idaplicaIVA]').setValue('S');
		panelProveedor.down('combo[name=params.secuenciaIVA]').setValue('A');
		panelProveedor.down('[name=params.proceso]').setValue('I');
		modificacionClausula.show();
	}
	
	function configuracionLayoutProveedor(grid,rowIndex){
		var record = grid.getStore().getAt(rowIndex);
		windowLoader = Ext.create('Ext.window.Window',{
			title         : 'Configuraci&oacute;n Layout'
			,buttonAlign  : 'center'
			,width        : 800
			,height       : 550
			,autoScroll   : true
			,loader       : {
				url       : _VER_CONFIG_LAYOUT
				,scripts  : true
				,autoLoad : true
				,params   : {
					'params.ntramite'		:	"10043",
					'params.cdpresta'		:	record.get('CLAVEPROVEEDOR'),
					'params.idaplicaIVA'	:	record.get('APLICAIVA'),
					'params.secuenciaIVA'	:	record.get('SECUENCIAIVA'),
					'params.idaplicaIVARET'	:	record.get('APLICAIVARET')
				}
			}
		}).show();
		centrarVentanaInterna(windowLoader);
	}
	
	
	function configuracionSubirCargaMasiva(grid,rowIndex){
		var record = grid.getStore().getAt(rowIndex);
		debug("Valor enviado ===>"+record.get('CLAVEPROVEEDOR'));
		
		Ext.Ajax.request({
			url     : _URL_EXISTE_CONF_PROV
			,params:{
				'params.cdpresta': record.get('CLAVEPROVEEDOR')
			}
			,success : function (response){
				debug(Ext.decode(response.responseText).validacionGeneral);
				if( Ext.decode(response.responseText).validacionGeneral =="S"){
					/*var _p22_windowAgregarDocu=Ext.create('Ext.window.Window',
					{
						title       : 'Subir Archivo de Carga Masiva'
						,closable    : false
						,modal       : true
						,width       : 500
						//,height   : 700
						,bodyPadding : 5
						,items       :
						[
							panelSeleccionDocumento= Ext.create('Ext.form.Panel',
							{
								border       : 0
								,url         : _URL_Carga_Masiva
								,timeout     : 600
								,buttonAlign : 'center'
								,items       :
								[
									{
										xtype       : 'filefield'
										,fieldLabel : 'Documento'
										,buttonText : 'Examinar...'
										,name       : 'fileName'
										,buttonOnly : false
										,width      : 450
										,name       : 'file'
										,cAccept    : ['xls','xlsx']
										,listeners  :
										{
											change : function(me)
											{
												var indexofPeriod = me.getValue().lastIndexOf("."),
												uploadedExtension = me.getValue().substr(indexofPeriod + 1, me.getValue().length - indexofPeriod).toLowerCase();
												if (!Ext.Array.contains(this.cAccept, uploadedExtension))
												{
													Ext.MessageBox.show(
													{
														title   : 'Error de tipo de archivo',
														msg     : 'Extensiones permitidas: ' + this.cAccept.join(),
														buttons : Ext.Msg.OK,
														icon    : Ext.Msg.WARNING
													});
													me.reset();
													Ext.getCmp('_p22_botGuaDoc').setDisabled(true);
												}
												else
												{
													Ext.getCmp('_p22_botGuaDoc').setDisabled(false);
												}
											}
										}
									}
								]
								,buttons     :
								[
									{
										id        : '_p22_botGuaDoc'
										,text     : 'Agregar'
										,icon     : '${ctx}/resources/fam3icons/icons/disk.png'
										,disabled : true
										,handler  : function (button,e)
										{
											button.setDisabled(true);
											Ext.getCmp('_p22_BotCanDoc').setDisabled(true);
											
											button.up().up().getForm().submit(
											{
												params        :
												{
													'params.pi_nmtabla': '4010'//_NMTABLA
													,'params.tipotabla': '1'//_TIPOTABLA
												},
												waitMsg: 'Ejecutando Carga Masiva...',
												success: function(form, action) {
													_p22_windowAgregarDocu.destroy();
													mensajeCorrecto('Exito', 'La carga masiva se ha ejecutado correctamente.');
													recargagridTabla5Claves();
												},
												failure: function(form, action) {
													
													manejaErrorSubmit(form, action, function() {
														
														Ext.getCmp('_p22_botGuaDoc').setDisabled(false);
														Ext.getCmp('_p22_BotCanDoc').setDisabled(false);
														
														if(action.result.resultado.key == 1) {
															// Error en validacion de formato:
															msgServer = 'Error en la validación ¿Desea descargar el archivo de errores?';
															Ext.Msg.show({
																title: 'Error', 
																msg: msgServer, 
																buttons: Ext.Msg.YESNO, 
																icon: Ext.Msg.ERROR,
																fn: function(btn){
																	if (btn == 'yes'){
																		//Ext.create('Ext.form.Panel').submit({
																			//url            : _URL_DESCARGA_DOCUMENTOS,
																			//standardSubmit : true,
																			//target         : '_blank',
																			//params         : {
																				//path     : _RUTA_DOCUMENTOS_TEMPORAL,
																				//filename : action.result.fileFileName 
																			//}
																		//});
																	}
																}
															 });
														} else if (action.result.resultado.key == 2) {
															// Error en carga masiva:
															Ext.Msg.show({title: 'Error', msg: action.result.resultado.value, buttons: Ext.Msg.OK, icon: Ext.Msg.ERROR});
														}
													});
												}
											});
										}
									}
									,{
										id       : '_p22_BotCanDoc'
										,text    : 'Cancelar'
										,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
										,handler : function (button,e)
										{
											_p22_windowAgregarDocu.destroy();
										}
									}
								]
							})
						]
					}).show();
					centrarVentanaInterna(_p22_windowAgregarDocu);*/
				}else{
					mensajeWarning('No se ha configurado el Layout del proveedor');
				}
			},
			failure : function (){
				me.up().up().setLoading(false);
				centrarVentanaInterna(Ext.Msg.show({
					title:'Error',
					msg: 'Error de comunicaci&oacute;n',
					buttons: Ext.Msg.OK,
					icon: Ext.Msg.ERROR
				}));
			}
		});
		
		
		
		//windowLoader = Ext.create('Ext.window.Window',{}).show();
		//centrarVentanaInterna(windowLoader);
	}
	
	function configuracionSubirCargaMasiva2(grid,rowIndex){
		Ext.Ajax.request({
			url: _URL_Carga_Masiva,
			params: {
				'params.fileName'  : 'C:\\Users\\Alberto\\Desktop\\libro1.xls'
			},
			success: function(response) {
				var res = Ext.decode(response.responseText);
				debug("VALOR DE RES ======> ", res);
			},
			failure: function(){
				centrarVentanaInterna(mensajeError('No se pudo eliminar.'));
			}
		});
	}
	
	
	function eliminarProveedor(grid,rowIndex){
		var record = grid.getStore().getAt(rowIndex);
		centrarVentanaInterna(Ext.Msg.show({
			title: 'Aviso',
			msg: '&iquest;Esta seguro que desea eliminar la configuraci&oacute; del proveedor?',
			buttons: Ext.Msg.YESNO,
			icon: Ext.Msg.QUESTION,
			fn: function(buttonId, text, opt){
				if(buttonId == 'yes'){
					Ext.Ajax.request({
						url: _URL_MOV_PROVEEDOR,
						params: {
							'params.cmbProveedorMod'  : record.get('CLAVEPROVEEDOR'),
							'params.idaplicaIVA'   : record.get('APLICAIVA'),
							'params.secuenciaIVA'   : record.get('SECUENCIAIVA'),
							'params.idaplicaIVARET'     : record.get('APLICAIVARET'),
							'params.proceso'     : "D"
						},
						success: function(response) {
							var res = Ext.decode(response.responseText);
							if(res.success){
								cargarPaginacion();
								mensajeCorrecto('Aviso','Se ha eliminado con &eacute;xito.',function(){});
							}else {
								centrarVentanaInterna(mensajeError('No se pudo eliminar.'));
							}
						},
						failure: function(){
							centrarVentanaInterna(mensajeError('No se pudo eliminar.'));
						}
					});
				}
			}
		}));
	}

});