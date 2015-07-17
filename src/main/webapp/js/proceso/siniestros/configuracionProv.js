Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
Ext.onReady(function() {
	var panelProveedor;
	Ext.selection.CheckboxModel.override( {
		mode: 'SINGLE',
		allowDeselect: true
	});

	Ext.define('modeloProveedores',{
		extend:'Ext.data.Model',
		fields:['CLAVEPROVEEDOR','NOMBPROVEEDOR','APLICAIVA','APLICAIVADESC','SECUENCIAIVA','SECIVADESC','APLICAIVARET','IVARETDESC']
	});
	
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
	
	Ext.define('modelListadoProvMedico',{
		extend: 'Ext.data.Model',
		fields: [
			{type:'string',		name:'cdpresta'},
			{type:'string',		name:'nombre'},
			{type:'string',		name:'cdespeci'},
			{type:'string',		name:'descesp'}
		]
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
	
	var storeProveedorMod = Ext.create('Ext.data.Store', {
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
		labelWidth : 170,					hideTrigger:true
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
				width	:350,			name   :'params.proceso', 		hidden: true
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
							mensajeCorrecto('&Eacute;XITO','Se ha guardado con &eacute;xito.',function(){});
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
				,collapsible   : true
				,titleCollapse : true
				,style         : 'margin:5px'
				,height        : 400
				,columns       : [
					{	xtype: 'actioncolumn',			width: 90,		sortable: false,		menuDisabled: true,
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
		debug("VALOR DEL RECORD  =====>",record)
		windowLoader = Ext.create('Ext.window.Window',{
			title         : 'Configuraci&oacute;n Layout'
			,buttonAlign  : 'center'
			,width        : 800
			,height       : 530
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
			}/*,
			listeners:{
				 close:function(){
					 if(true){
						Ext.create('Ext.form.Panel').submit(
						{
							standardSubmit :true
							,params		:
							{
								'params.ntramite' : _11_params.NTRAMITE
							}
						});
						 panelInicialPral.getForm().reset();
						storeAseguradoFactura.removeAll();
						storeConceptos.removeAll();
					 }
				 }
			}*/
		}).show();
		centrarVentanaInterna(windowLoader);
	}
	
	
	function eliminarProveedor(grid,rowIndex){
		var record = grid.getStore().getAt(rowIndex);
		centrarVentanaInterna(Ext.Msg.show({
			title: 'Aviso',
			msg: '&iquest;Esta seguro que desea eliminar el asegurado?',
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