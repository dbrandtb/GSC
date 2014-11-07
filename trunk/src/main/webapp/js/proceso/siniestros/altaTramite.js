Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
Ext.onReady(function() {
	Ext.selection.CheckboxModel.override( {
		mode: 'SINGLE',
		allowDeselect: true
	});

	//MODELOS
	Ext.define('modelFacturaSiniestro', {
		extend:'Ext.data.Model',
		fields:[
			{type:'string',		name:'noFactura'},			{type:'date',		name:'fechaFactura'},		{type:'string',		name:'tipoServicio'},
			{type:'string',		name:'tipoServicioName'},	{type:'string',		name:'proveedor'},			{type:'string',		name:'proveedorName'},
			{type:'string',		name:'importe'},			{type:'string',		name:'tipoMoneda'},			{type:'string',		name:'tipoMonedaName'},
			{type:'string',		name:'tasaCambio'},			{type:'string',		name:'importeFactura'}
		]
	});
	
	Ext.define('modelListadoProvMedico',{
		extend: 'Ext.data.Model',
			fields: [
					{type:'string',		name:'cdpresta'},	{type:'string', name:'nombre'},		{type:'string', name:'cdespeci'},
					{type:'string',		name:'descesp'}
		]
	});
	
	Ext.define('modelListadoPoliza',{
		extend: 'Ext.data.Model',
		fields: [	{type:'string',		name:'cdramo'},				{type:'string',		name:'cdunieco'},				{type:'string',		name:'estado'},
					{type:'string',		name:'nmpoliza'},			{type:'string',		name:'nmsituac'},				{type:'string',		name:'mtoBase'},
					{type:'string',		name:'feinicio'},			{type:'string',		name:'fefinal'},				{type:'string',		name:'dssucursal'},
					{type:'string',		name:'dsramo'},				{type:'string',		name:'estatus'},				{type:'string',		name:'dsestatus'},
					{type:'string',		name:'nmsolici'},			{type:'string',		name:'nmsuplem'},				{type:'string',		name:'cdtipsit'},
					{type:'string',		name:'dsestatus'},			{type:'string',		name:'vigenciaPoliza'},			{type:'string',		name:'faltaAsegurado'},
					{type:'string',		name:'fcancelacionAfiliado'},{type:'string',	name:'desEstatusCliente'},		{type:'string',		name:'numPoliza'}]
	});
	
	Ext.define('modelListAsegPagDirecto',{
		extend: 'Ext.data.Model',
		fields: [
					{type:'string',		name:'modUnieco'},			{type:'string',		name:'modEstado'},				{type:'string',		name:'modRamo'},
					{type:'string',		name:'modNmsituac'},		{type:'string',		name:'modPolizaAfectada'},		{type:'string',		name:'modCdpersondesc'},
					{type:'string',		name:'modNmsolici'},		{type:'string',		name:'modNmsuplem'},			{type:'string',		name:'modCdtipsit'},
					{type:'string',		name:'modNmautserv'},		{type:'string',		name:'modFechaOcurrencia'},		{type:'string',		name:'modCdperson'},
					{type:'string',		name:'modnumPoliza'},		{type:'string',		name:'modFactura'}
				]
	});
	
	//STORES:
	oficinaReceptora = Ext.create('Ext.data.Store', {
		model:'Generic',
		autoLoad:true,
		proxy:
		{
			type: 'ajax',
			url:_URL_CATALOGOS,
			extraParams : {
				catalogo:_CATALOGO_OFICINA_RECEP,
				'params.idPadre' : '1000'
			},
			reader:
			{
				type: 'json',
				root: 'lista'
			}
		}
	});
	
	var storeTipoMoneda = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_TipoMoneda},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeTipoMoneda.load();

	cmbTipoMoneda = Ext.create('Ext.form.ComboBox',
	{
		id:'cmbTipoMoneda',			store: storeTipoMoneda,		value:'001',		queryMode:'local',  
		displayField: 'value',		valueField: 'key',			editable:false,		allowBlank:false
	});

	oficinaEmisora = Ext.create('Ext.data.Store', {
		model:'Generic',
		autoLoad:true,
		proxy:
		{
			type: 'ajax',
			url:_URL_CATALOGOS,
			extraParams : {
				catalogo:_CATALOGO_OFICINA_RECEP,
				'params.idPadre' : '1000'
			},
			reader:
			{
				type: 'json',
				root: 'lista'
			}
		}
	});

	var storeFacturaDirecto =new Ext.data.Store(
	{
		autoDestroy: true,			model: 'modelFacturaSiniestro'
	});
	
	var storeFacturaReembolso =new Ext.data.Store(
	{
		autoDestroy: true,			model: 'modelFacturaSiniestro'
	});

	var storeListAsegPagDirecto = new Ext.data.Store(
	{
		autoDestroy: true,
		model: 'modelListAsegPagDirecto',
		proxy: {
			type: 'ajax',
			url: _URL_ASEGURADO_FACTURA,
			reader: {
				type: 'json',
				root: 'slist1'
			}
		}
	});
	
	var storeTipoAtencion = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_TipoAtencion},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeTipoAtencion.load();

	var storeAsegurados = Ext.create('Ext.data.Store', {
		model:'Generic',
		autoLoad:false,
		proxy: {
			type: 'ajax',
			url : _URL_LISTADO_ASEGURADO,
			reader: {
				type: 'json',
				root: 'listaAsegurado'
			}
		}
	});
	
	var storeAsegurados2 = Ext.create('Ext.data.Store', {
		model:'Generic',
		autoLoad:false,
		proxy: {
			type: 'ajax',
			url : _URL_LISTADO_ASEGURADO,
			reader: {
				type: 'json',
				root: 'listaAsegurado'
			}
		}
	});

	var storeTipoPago = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_TipoPago},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeTipoPago.load();

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

	var storeListadoPoliza = new Ext.data.Store(
	{
		pageSize	: 5
		,model		: 'modelListadoPoliza'
		,autoLoad	: false
		,proxy		:
		{
			enablePaging	: true,
			reader			: 'json',
			type			: 'memory',
			data			: []
		}
	});

    storeRamos = Ext.create('Ext.data.Store', {
		model:'Generic',
		autoLoad:true,
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CAT_RAMO_SALUD},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	
	storeRamos.load();

	var cmbOficinaReceptora = Ext.create('Ext.form.field.ComboBox',
	{
		fieldLabel	: 'Oficina receptora',	name			:'cmbOficReceptora',		allowBlank		: false,
		editable	: true,					displayField	:'value',					width			: 350,
		emptyText	:'Seleccione...',		valueField		:'key',						forceSelection	: true,
		queryMode	:'local',				store 			:oficinaReceptora
	});
   
    var cmbOficinaEmisora = Ext.create('Ext.form.field.ComboBox',
	{
	    fieldLabel : 'Oficina emisora',		name      : 'cmbOficEmisora',
	    allowBlank : false,					editable   : true,					displayField : 'value',
	    width		 : 350,					emptyText:'Seleccione...',			valueField   : 'key',
	    forceSelection : true,				queryMode      :'local',			store : oficinaEmisora
	});
	
    cmbRamos = Ext.create('Ext.form.field.ComboBox',
	{
		fieldLabel   : 'Producto',			allowBlank     : false,				editable: false,
	    displayField : 'value',				valueField: 'key',					forceSelection : false,
	    width		 : 350,					queryMode :'local',					name           :'cmbRamos'
	    ,store : storeRamos
	});
	
	var comboTipoAte= Ext.create('Ext.form.ComboBox',
    {
        name:'cmbTipoAtencion',				fieldLabel: 'Tipo atenci&oacute;n',	queryMode:'local',
        displayField: 'value',				valueField: 'key',					editable:false,
        allowBlank : false,					width		 : 350,					emptyText:'Seleccione...',
        store: storeTipoAtencion
    });
    
    var tipoPago= Ext.create('Ext.form.ComboBox',
    {
    	name:'cmbTipoPago',					fieldLabel: 'Tipo pago',			queryMode:'local',
    	displayField: 'value',				valueField: 'key',					allowBlank:false,
    	editable:false,						width		 : 350,					emptyText:'Seleccione...',
    	store: storeTipoPago,
    	listeners : {
    		'select':function(e){
	    		panelInicialPral.down('combo[name=cmbTipoAtencion]').setValue(null);
	    		if(e.getValue() == _TIPO_PAGO_DIRECTO){
    				limpiarRegistrosTipoPago(e.getValue());
					panelInicialPral.down('combo[name=cmbOficEmisora]').setValue("1000");
					panelInicialPral.down('combo[name=cmbTipoAtencion]').show();
    				
    			}else{
    				limpiarRegistrosTipoPago(e.getValue());
    				panelInicialPral.down('combo[name=cmbOficEmisora]').setValue("1104");
    				panelInicialPral.down('combo[name=cmbTipoAtencion]').setValue("8");
    				panelInicialPral.down('combo[name=cmbTipoAtencion]').hide();
    			}
    		}
    	}
	});
	
	aseguradoAfectado = Ext.create('Ext.form.field.ComboBox',
	{
		fieldLabel : 'Asegurado afectado',	displayField : 'value',				name:'cmbAseguradoAfectado',
		width		 : 350,					valueField   : 'key',				queryMode :'remote',
		forceSelection : true,				matchFieldWidth: false,				queryParam: 'params.cdperson',
		minChars  : 2,						store : storeAsegurados,			triggerAction: 'all',
		hideTrigger:true,					allowBlank:false,
		listeners : {
			'select' : function(combo, record) {
				obtieneCDPerson = this.getValue();
				panelInicialPral.down('[name=idnombreAsegurado]').setValue(aseguradoAfectado.rawValue);
				var params = {
						'params.cdperson'	:	obtieneCDPerson,
						'params.cdramo'		:	panelInicialPral.down('combo[name=cmbRamos]').getValue()
				};
				cargaStorePaginadoLocal(storeListadoPoliza, _URL_CONSULTA_LISTADO_POLIZA, 'listaPoliza', params, function(options, success, response){
					if(success){
						var jsonResponse = Ext.decode(response.responseText);
						if(jsonResponse.listaPoliza == null) {
							Ext.Msg.show({
								title: 'Aviso',
								msg: 'No existen p&oacute;lizas para el asegurado elegido.',
								buttons: Ext.Msg.OK,
								icon: Ext.Msg.WARNING
							});
							panelInicialPral.down('combo[name=cmbAseguradoAfectado]').setValue('');
							modPolizasAltaTramite.hide();
							return;
						}
					}else{
						Ext.Msg.show({
							title: 'Aviso',
							msg: 'Error al obtener los datos.',
							buttons: Ext.Msg.OK,
							icon: Ext.Msg.ERROR
							});
					}
				});
				modPolizasAltaTramite.show();
			}
		}
	});

	aseguradoAfectadoPDirecto = Ext.create('Ext.form.field.ComboBox',
	{
		displayField : 'value',				name:'cmbAseguradoAfectadoPdirecto',width		 : 350,
		valueField   : 'key',				forceSelection : true,				matchFieldWidth: false,
		queryMode :'remote',				queryParam: 'params.cdperson',		minChars  : 2,
		store : storeAsegurados,			triggerAction: 'all',				hideTrigger:true,
		allowBlank:false,
		listeners : {
			'select' : function(combo, record) {
				obtieneCDPerson = this.getValue();
				panelInicialPral.down('[name=idnombreAsegurado]').setValue(aseguradoAfectadoPDirecto.rawValue);
				var params = {
					'params.cdperson' : obtieneCDPerson,
					'params.cdramo' : panelInicialPral.down('combo[name=cmbRamos]').getValue()
				};
				
				cargaStorePaginadoLocal(storeListadoPoliza, _URL_CONSULTA_LISTADO_POLIZA, 'listaPoliza', params, function(options, success, response){
				if(success){
					var jsonResponse = Ext.decode(response.responseText);
					if(jsonResponse.listaPoliza == null) {
						Ext.Msg.show({
							title: 'Aviso',
							msg: 'No existen p&oacute;lizas para el asegurado elegido.',
							buttons: Ext.Msg.OK,
							icon: Ext.Msg.WARNING
						});
						panelInicialPral.down('combo[name=cmbAseguradoAfectado]').setValue('');
						modPolizasAltaTramite.hide();
						return;
					}
					}else{
				 		Ext.Msg.show({
							title: 'Aviso',
							msg: 'Error al obtener los datos.',
							buttons: Ext.Msg.OK,
							icon: Ext.Msg.ERROR
						});
					}
				});
				modPolizasAltaTramite.show();
			}
		}
	});

	var cmbBeneficiario= Ext.create('Ext.form.ComboBox',
	{
		name:'cmbBeneficiario',				fieldLabel: 'Beneficiario',			queryMode:'remote',
		displayField: 'value',				valueField: 'key',					editable:true,
		width		 : 350,					forceSelection : true,				matchFieldWidth: false,
		queryParam: 'params.cdperson',		minChars  : 2,						triggerAction: 'all',
		hideTrigger:true,					allowBlank:false,					store : storeAsegurados2,
		listeners : {
			'select' : function(combo, record) {
				panelInicialPral.down('[name=idnombreBeneficiarioProv]').setValue(cmbBeneficiario.rawValue);
			}
		}
	});

	cmbProveedor = Ext.create('Ext.form.field.ComboBox',
	{
		fieldLabel : 'Proveedor',			displayField : 'nombre',			name:'cmbProveedor',
		width		 : 350,					valueField   : 'cdpresta',			forceSelection : true,
		matchFieldWidth: false,				queryMode :'remote',				queryParam: 'params.cdpresta',
		minChars  : 2,						store : storeProveedor,				triggerAction: 'all',
		hideTrigger:true,					allowBlank:false,
		listeners : {
			'select' : function(combo, record) {
				if(this.getValue() =='0'){
					panelInicialPral.down('[name=idnombreBeneficiarioProv]').setValue('');
					panelInicialPral.down('[name=idnombreBeneficiarioProv]').show();
				}else{
					panelInicialPral.down('[name=idnombreBeneficiarioProv]').setValue(cmbProveedor.rawValue);
					panelInicialPral.down('[name=idnombreBeneficiarioProv]').hide();
					//Se tendra que realizar la validacion  con el Proveedot y la factura
					//validarFacturaPagada(panelInicialPral.down('combo[name=cmbProveedor]').getValue(), Ext.getCmp('txtNoFactura').getValue());
				}
			}
		}
	});
	
	cmbProveedorReembolso = Ext.create('Ext.form.field.ComboBox',
	{
		displayField : 'nombre',			name:'cmbProveedorReembolso',				valueField   : 'cdpresta',
		forceSelection : true,				matchFieldWidth: false,				queryMode :'remote',
		queryParam: 'params.cdpresta',		minChars  : 2,						store : storeProveedor,
		triggerAction: 'all',				hideTrigger:true,					allowBlank:false
	});

    /*//////////////////////////////////////////////////////////////////
    ////////////////   DECLARACION DE FACTURAS PAGO DIRECTO ////////////
    /////////////////////////////////////////////////////////////////*/
	Ext.define('EditorFacturaDirecto', {
		extend: 'Ext.grid.Panel',
		name:'editorFacturaDirecto',
		title: 'Alta de facturas Pago Directo',
		frame: true,
		selModel: { selType: 'checkboxmodel', mode: 'SINGLE', checkOnly: true },
		initComponent: function(){
			Ext.apply(this, {
			width: 750,
			height: 250,
			plugins  :
			[
				Ext.create('Ext.grid.plugin.CellEditing',{	clicksToEdit: 1	})
			],
			store: storeFacturaDirecto,
			columns: 
			[
				{
					header: 'No. de Factura',			dataIndex: 'noFactura',			flex:2
					,editor: {
						xtype: 'textfield',
						allowBlank: false
					}
				},
				{
					header: 'Fecha de Factura',			dataIndex: 'fechaFactura',		flex:2,		renderer: Ext.util.Format.dateRenderer('d/m/Y')
					,editor : {
						xtype : 'datefield',
						format : 'd/m/Y',
						editable : true
					}
				},
				{
					header: 'Moneda', 					dataIndex: 'tipoMonedaName',	flex:2
					,editor : cmbTipoMoneda
					,renderer : function(v) {
						var leyenda = '';
						if (typeof v == 'string'){
							storeTipoMoneda.each(function(rec){
								if (rec.data.key == v){
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
				,
				{
				 	header: 'Tasa cambio', 				dataIndex: 'tasaCambio',		flex:2,				renderer: Ext.util.Format.usMoney
					,editor: {
						xtype: 'textfield',
						allowBlank: false
					}
				},
				{
					header: 'Importe Factura', 			dataIndex: 'importeFactura',	flex:2,				renderer: Ext.util.Format.usMoney
					,editor: {
						xtype: 'textfield',
						allowBlank: false
				    }
				},
				{
					 header: 'Importe MXN', 				dataIndex: 'importe',			flex:2,				renderer: Ext.util.Format.usMoney
					,editor: {
							xtype: 'textfield',
							allowBlank: false
						}
				},
				{
				 	xtype: 'actioncolumn',
					width: 30,
					sortable: false,
					menuDisabled: true,
					items: [{
						icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
						tooltip: 'Quitar inciso',
					 	scope: this,
					 	handler: this.onRemoveClick
				 	}]
				 }
			],
			tbar:[
				{
					text	: 'Agregar Factura'
					,icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/book.png'
					,handler : _p21_agregarFactura
				},
				{
					/*1.- MANDAMOS A GUARDAR LA INFORMACIÓN DE LAS FACTURAS UNICAMENTE EN EL TRAMITE*/
					text	: 'Guardar Factura'
					,icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/accept.png'
					,handler : function() {
						var obtener = [];
						storeFacturaDirecto.each(function(record) {
							obtener.push(record.data);
						});
						if(obtener.length <= 0){
							Ext.Msg.show({
								title:'Error',
								msg: 'Se requiere al menos una factura en el tr&aacute;mite',
								buttons: Ext.Msg.OK,
								icon: Ext.Msg.ERROR
							});
							return false;
						}else{
							var submitValues={};
							var formulario=panelInicialPral.form.getValues();
							submitValues['params']=formulario;
							var datosTablas = [];
							storeFacturaDirecto.each(function(record,index){
								datosTablas.push({
									nfactura:record.get('noFactura'),
									ffactura:record.get('fechaFactura'),
									cdtipser:panelInicialPral.down('combo[name=cmbTipoAtencion]').getValue(),
									cdpresta:panelInicialPral.down('combo[name=cmbProveedor]').getValue(),
									ptimport:record.get('importe'),
									cdmoneda:record.get('tipoMonedaName'),
									tasacamb:record.get('tasaCambio'),
									ptimporta:record.get('importeFactura')
								});
							});
							submitValues['datosTablas']=datosTablas;
							panelInicialPral.setLoading(true);
							Ext.Ajax.request(
							{
								url: _URL_GUARDA_FACTURA_TRAMITE,
								jsonData:Ext.encode(submitValues),
								success:function(response,opts){
									panelInicialPral.setLoading(false);
									var jsonResp = Ext.decode(response.responseText);
									if(jsonResp.success==true){
										panelInicialPral.setLoading(false);
									}else{
										Ext.Msg.show({
											title:'Error',
											msg: 'Error en el guardado de la factura',
											buttons: Ext.Msg.OK,
											icon: Ext.Msg.ERROR
										});
										respuesta= false;
									}
								},
								failure:function(response,opts)
								{
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
						storeListAsegPagDirecto.removeAll();
					}
				},
				{
					/*MOSTRAMOS LA INFORMACIÓN INICIAL DEL STORE DE LAS FACTURAS*/
					text		:'Cancelar'
					,icon		:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png'
					,handler	:function() {
						Ext.Ajax.request(
						{
							url		:	_URL_CONSULTA_FACTURAS
							,params	:	{
									'smap.ntramite' : panelInicialPral.down('[name=idNumTramite]').getValue()
							}
							,success : function (response)
							{
								storeFacturaDirecto.removeAll();
								var json = Ext.decode(response.responseText).slist1;
								for(var i = 0; i < json.length; i++){
									var fechaFacturaM = json[i].FFACTURA.match(/\d+/g); 
									var dateFec = new Date(fechaFacturaM[2], fechaFacturaM[1]-1,fechaFacturaM[0]);
									var rec = new modelFacturaSiniestro({
										noFactura: json[i].NFACTURA,
										fechaFactura: dateFec,
										tipoServicio: json[i].CDTIPSER,
										proveedor: json[i].CDPRESTA,
										importe: json[i].PTIMPORT,
										proveedorName: json[i].CDPRESTA,
										tipoServicioName: json[i].CDTIPSER,
										tipoMoneda: json[i].CDMONEDA,
										tipoMonedaName: json[i].CDMONEDA,
										tasaCambio: json[i].TASACAMB,
										importeFactura:json[i].PTIMPORTA
									});
									storeFacturaDirecto.add(rec);
								}
							},
							failure : function ()
							{
								me.up().up().setLoading(false);
								centrarVentanaInterna(Ext.Msg.show({
									title:'Error',
									msg: 'Error de comunicaci&oacute;n',
									buttons: Ext.Msg.OK,
									icon: Ext.Msg.ERROR
								}));
							}
						});
						storeListAsegPagDirecto.removeAll();
					}
			}],
			listeners: {
				itemclick: function(dv, record, item, index, e) {
					/*OBTENEMOS LA INFORMACIÓN DE LOS ASEGURADOS*/
					storeListAsegPagDirecto.removeAll();
					if(panelInicialPral.down('[name=editorFacturaDirecto]').getSelectionModel().hasSelection()){
						var rowSelected = panelInicialPral.down('[name=editorFacturaDirecto]').getSelectionModel().getSelection()[0];
						var noFactura= rowSelected.get('noFactura');
						storeListAsegPagDirecto.removeAll();
						//REALIZAMOS EL LLAMADO POR EL NUMERO DE FACTURA Y TRÁMITE
						Ext.Ajax.request(
						{
							url		: _URL_ASEGURADO_FACTURA
							,params	: {
								'params.nfactura'  : rowSelected.get('noFactura'),
								'params.ntramite'  : panelInicialPral.down('[name=idNumTramite]').getValue()
							}
							,success : function (response)
							{
								var json=Ext.decode(response.responseText).slist1;
								if(Ext.decode(response.responseText).slist1 != null)
								{
									for(var i = 0; i < json.length; i++){
										var fechaFacturaM = json[i].MODFECHAOCURRENCIA.match(/\d+/g); 
										var rec = new modelListAsegPagDirecto({
											modUnieco: json[i].MODUNIECO,
											modEstado: json[i].MODESTADO,
											modFechaOcurrencia:fechaFacturaM[2]+"/"+fechaFacturaM[1]+"/"+fechaFacturaM[0] ,
											modCdtipsit: json[i].MODCDTIPSIT,
											modCdperson: json[i].MODCDPERSON,
											modCdpersondesc: json[i].MODCDPERSON+" "+json[i].MODCDPERSONDESC,
											modNmsituac: json[i].MODNMSITUAC,
											modNmsolici: json[i].MODNMSOLICI,
											modNmsuplem: json[i].MODNMSUPLEM,
											modPolizaAfectada: json[i].MODPOLIZAAFECTADA,
											modRamo: json[i].MODRAMO,
											modFactura: json[i].NOFACTURAINT,
											modnumPoliza: json[i].NMPOLIEX,
											modNmautserv: json[i].MODNMAUTSERV
										});
										storeListAsegPagDirecto.add(rec);
									}
								}
							},
							failure : function ()
							{
								me.up().up().setLoading(false);
								Ext.Msg.show({
									title:'Error',
									msg: 'Error de comunicaci&oacute;n',
									buttons: Ext.Msg.OK,
									icon: Ext.Msg.ERROR
								});
							}
						});
					}else{
						storeListAsegPagDirecto.removeAll();
					}
				}
			}
		 });
			this.callParent();
		},
		onRemoveClick: function(grid, rowIndex){
			/*Eliminamos el record del store*/
			var record=this.getStore().getAt(rowIndex);
			this.getStore().removeAt(rowIndex);
		}
	});
	gridFacturaDirecto=new EditorFacturaDirecto();

	/*DEFINIMOS LA TABLA DE PAGO REEMBOLSO*/
	Ext.define('EditorFacturaReembolso', {
		extend: 'Ext.grid.Panel',
		name:'editorFacturaReembolso',
		title: 'Alta de facturas Pago Reembolso',
		frame: true,
		selType  : 'rowmodel',
		initComponent: function(){
 			Ext.apply(this, {
 				width: 750,
 				height: 250
 				,plugins  :
				[
					Ext.create('Ext.grid.plugin.CellEditing',
					{
						clicksToEdit: 1
					})
				],
				store: storeFacturaReembolso,
 				columns: 
	 			[
					{	
						header: 'No. de Factura',			dataIndex: 'noFactura',			flex:2
						,editor: {
							xtype: 'textfield',
							allowBlank: false
						}
					},
					{
						header: 'Fecha de Factura',			dataIndex: 'fechaFactura',		flex:2,			 	renderer: Ext.util.Format.dateRenderer('d/m/Y')
						,editor : {
							xtype : 'datefield',
							format : 'd/m/Y',
							editable : true
						}
				 	},
				 	{
						header: 'Proveedor', 				dataIndex: 'proveedorName',	flex:2
						,editor : cmbProveedorReembolso
						,renderer : function(v) {
							var leyenda = '';
							if (typeof v == 'string')// tengo solo el indice
							{
								storeProveedor.load();
								storeProveedor.each(function(rec) {
									if (rec.data.cdpresta == v) {
										leyenda = rec.data.nombre;
									}
								});
							}else // tengo objeto que puede venir como Generic u otro mas complejo
							{
								if (v.key && v.value)
								{
									leyenda = v.value;
								} else {
									leyenda = v.data.value;
								}
							}
							return leyenda;
						}
					},
					{
						header: 'Moneda', 				dataIndex: 'tipoMonedaName',	flex:2
						,editor : cmbTipoMoneda
						,renderer : function(v) {
							var leyenda = '';
							if (typeof v == 'string')// tengo solo el indice
							{
								storeTipoMoneda.each(function(rec) {
									if (rec.data.key == v) {
										leyenda = rec.data.value;
									}
								});
							}else // tengo objeto que puede venir como Generic u otro mas complejo
							{
								if (v.key && v.value)
								{
									leyenda = v.value;
								} else {
									leyenda = v.data.value;
								}
							}
							return leyenda;
						}
					},
				 	{
						header: 'Tasa cambio', 				dataIndex: 'tasaCambio',	flex:2,				renderer: Ext.util.Format.usMoney
						,editor: {
							xtype: 'textfield',
							allowBlank: false
						}
					},
					{
						header: 'Importe Factura', 				dataIndex: 'importeFactura',		 	flex:2,				renderer: Ext.util.Format.usMoney
						,editor: {
							xtype: 'textfield',
							allowBlank: false
						}
					},
					{
						header: 'Importe MXN', 					dataIndex: 'importe',		 	flex:2,				renderer: Ext.util.Format.usMoney
						,editor: {
							xtype: 'textfield',
							allowBlank: false
						}
					},
					{
						xtype: 'actioncolumn',
						width: 30,
						sortable: false,
						menuDisabled: true,
						items: [{
							icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
							tooltip: 'Quitar inciso',
							scope: this,
							handler: this.onRemoveClick
				 		}]
				 	}
				],
				tbar: [
					{
						text		:	'Agregar Factura'
						,icon		:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/add.png'
						,handler	: _p21_agregarFactura
					}
				]
			});
			this.callParent();
		},
	 	onRemoveClick: function(grid, rowIndex){
			var record=this.getStore().getAt(rowIndex);
			this.getStore().removeAt(rowIndex);
		}
	});
	gridFacturaReembolso =new EditorFacturaReembolso();
	
	/*GENERACION DE LA TABLA PARA LOS ASEGURADOS*/
	Ext.define('EditorAsegPagDirecto', {
 		extend: 'Ext.grid.Panel',
		name:'EditorAsegPagDirecto',
 		title: 'Asegurados',
 		frame: true,
		//selType  : 'rowmodel',
		initComponent: function(){
				Ext.apply(this, {
				width: 750,
				height: 250,
				store: storeListAsegPagDirecto,
				columns: 
				[
					{
						header: 'Fecha Ocurrencia',	dataIndex: 'modFechaOcurrencia',			width:150
					},
					{
						header: 'Asegurado',		dataIndex: 'modCdpersondesc',			width:350//,		hidden:	true
					},
					{
						header: 'N&uacute;mero P&oacute;liza',	dataIndex: 'modnumPoliza',			width:200//,		hidden:true
					},
					{
						xtype: 'actioncolumn',
						width: 30,
						sortable: false,
						menuDisabled: true,
						items: [{
							icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
							tooltip: 'Quitar inciso',
							scope: this,
							handler: this.onRemoveClick
						}]
					}
				],
				tbar: [
					{
						text		:	'Agregar Asegurado'
						,icon		:	_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/user_add.png'
						,handler	:	_p21_agregarAseguradoClic
					},
					{
						text     : 'Guardar Asegurados'
						,icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/accept.png'
						,handler : function() {
							var obtener = [];
							/*OBTENEMOS LA INFORMACION DE LOS ASEGURADOS*/
							storeListAsegPagDirecto.each(function(record) {
								obtener.push(record.data);
							});
							
							if(obtener.length <= 0){
								Ext.Msg.show({
									title:'Error',
									msg: 'Se requiere al menos un asegurado',
									buttons: Ext.Msg.OK,
									icon: Ext.Msg.ERROR
								});
								return false;
							}else{
								var submitValues={};
								var formulario=panelInicialPral.form.getValues();
								submitValues['params']=formulario;
								var datosTablas = [];
								storeListAsegPagDirecto.each(function(record,index){
									datosTablas.push({
										modUnieco: record.get('modUnieco'),
										modEstado: record.get('modEstado'),
										modFechaOcurrencia: record.get('modFechaOcurrencia'),
										modCdtipsit: record.get('modCdtipsit'),
										modCdperson: record.get('modCdperson'),
										modCdpersondesc: record.get('modCdpersondesc'),
										modNmsituac: record.get('modNmsituac'),
										modNmsolici: record.get('modNmsolici'),
										modNmsuplem: record.get('modNmsuplem'),
										modPolizaAfectada: record.get('modPolizaAfectada'),
										modRamo: record.get('modRamo'),
										modFactura: record.get('modFactura'),
										modnumPoliza: record.get('modnumPoliza'),
										modNmautserv: record.get('modNmautserv')
									});
								});
								submitValues['datosTablas']=datosTablas;
								debug("VALOR A ENVIAR");
								debug(submitValues);
								panelInicialPral.setLoading(true);
								Ext.Ajax.request(
								{
									url: _URL_GUARDA_ASEGURADO,
									jsonData:Ext.encode(submitValues),
									success:function(response,opts){
										panelInicialPral.setLoading(false);
										var jsonResp = Ext.decode(response.responseText);
										if(jsonResp.success==true){
											panelInicialPral.setLoading(false);
										}
										else{
											Ext.Msg.show({
												title:'Error',
												msg: 'Error en el guardado de asegurados',
												buttons: Ext.Msg.OK,
												icon: Ext.Msg.ERROR
											});
											respuesta= false;
										}
									},
									failure:function(response,opts)
									{
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
						}
					}
				]
			});
			this.callParent();
		},
		/*REMOVEMOS LOS ASEGURADOS*/
		onRemoveClick: function(grid, rowIndex){
			var record=this.getStore().getAt(rowIndex);
			this.getStore().removeAt(rowIndex);
		}
	});
	gridAsegPagDirecto=new EditorAsegPagDirecto();

	/* PANEL PARA LA BUSQUEDA DE LA INFORMACIÓN DEL ASEGURADO PARA LA BUSQUEDA DE LAS POLIZAS */
	gridPolizasAltaTramite= Ext.create('Ext.grid.Panel',
	{
		id			:	'polizaGridAltaTramite',
		store		:	storeListadoPoliza,
		selType		:	'checkboxmodel',
		width		:	700,
		height		:	200,
		columns		:
		[
			{
				header		: 'N&uacute;mero de P&oacute;liza',			dataIndex : 'numPoliza',		width		: 200
			},
			{
				header		: 'Estatus p&oacute;liza ',							dataIndex : 'dsestatus',	width	 	: 100
			},
			{
				header		: 'Vigencia p&oacute;liza <br/> Fecha inicio \t\t  |  \t\t Fecha fin  ',		dataIndex : 'vigenciaPoliza',		width	    : 200
			},
			{
				header		: 'Fecha alta <br/> asegurado',		dataIndex : 'faltaAsegurado',		width	    : 100
			},
			{
				header		: 'Fecha cancelaci&oacute;n <br/> asegurado',		dataIndex : 'fcancelacionAfiliado',		width	    : 150
			},
			{
				header		: 'Estatus<br/> asegurado',				dataIndex : 'desEstatusCliente',		width	    : 100
			},
			{
				header		: 'Producto',							dataIndex : 'dsramo',		width       : 150
			},
			{
				header		: 'Sucursal',							dataIndex : 'dssucursal',	width       : 150
			},
			{
				header		: 'Estado',								dataIndex : 'estado',		width	    : 100
			},
			{
				header		: 'N&uacute;mero de Situaci&oacute;n',	dataIndex : 'nmsituac',		width	    : 150
			}
		],
		bbar :
		{
			displayInfo	: true,
			store		: storeListadoPoliza,
			xtype		: 'pagingtoolbar'
		},
		listeners: {
			itemclick: function(dv, record, item, index, e){
				//1.- Validamos que el asegurado este vigente
				if(record.get('desEstatusCliente')=="Vigente"){
					var valorFechaOcurrencia;
					if(panelInicialPral.down('combo[name=cmbTipoPago]').getValue() == _TIPO_PAGO_DIRECTO){
						var valorFechaOcu = panelListadoAsegurado.query('datefield[name=dtfechaOcurrencias]')[0].rawValue;
						valorFechaOcurrencia = new Date(valorFechaOcu.substring(6,10)+"/"+valorFechaOcu.substring(3,5)+"/"+valorFechaOcu.substring(0,2));
					}else{
						valorFechaOcurrencia = panelInicialPral.down('[name=dtFechaOcurrencia]').getValue();
					}
					var valorFechaInicial = new Date(record.get('feinicio').substring(6,10)+"/"+record.get('feinicio').substring(3,5)+"/"+record.get('feinicio').substring(0,2));
					var valorFechaFinal =   new Date(record.get('fefinal').substring(6,10)+"/"+record.get('fefinal').substring(3,5)+"/"+record.get('fefinal').substring(0,2));
					var valorFechaAltaAsegurado = new Date(record.get('faltaAsegurado').substring(6,10)+"/"+record.get('faltaAsegurado').substring(3,5)+"/"+record.get('faltaAsegurado').substring(0,2));
	
					if( (valorFechaOcurrencia <= valorFechaFinal) && (valorFechaOcurrencia >= valorFechaInicial)){
						if( valorFechaOcurrencia >= valorFechaAltaAsegurado ){
							//cumple la condición la fecha de ocurrencia es menor igual a la fecha de alta de tramite
							panelInicialPral.down('[name="cdunieco"]').setValue(record.get('cdunieco'));
							panelInicialPral.down('[name="estado"]').setValue(record.get('estado'));
							panelInicialPral.down('[name="cdramo"]').setValue(record.get('cdramo'));
							panelInicialPral.down('[name="nmsituac"]').setValue(record.get('nmsituac'));
							panelInicialPral.down('[name="polizaAfectada"]').setValue(record.get('nmpoliza'));
							panelInicialPral.down('[name="idNmsolici"]').setValue(record.get('nmsolici'));
							panelInicialPral.down('[name="idNmsuplem"]').setValue(record.get('nmsuplem'));
							panelInicialPral.down('[name="idCdtipsit"]').setValue(record.get('cdtipsit'));
							panelInicialPral.down('[name="idNumPolizaInt"]').setValue(record.get('numPoliza'));
							modPolizasAltaTramite.hide();
						}else{
							// No se cumple la condición la fecha de ocurrencia es mayor a la fecha de alta de tramite
							Ext.Msg.show({
								title:'Error',
								msg: 'La fecha de ocurrencia es mayor a la fecha de alta del asegurado',
								buttons: Ext.Msg.OK,
								icon: Ext.Msg.ERROR
							});
							modPolizasAltaTramite.hide();
							limpiarRegistros();
							if(panelInicialPral.down('combo[name=cmbTipoPago]').getValue() == _TIPO_PAGO_DIRECTO){
								panelListadoAsegurado.query('combo[name=cmbAseguradoAfect]')[0].setValue('');
							}else{
								panelInicialPral.down('combo[name=cmbAseguradoAfectado]').setValue('');
							}
						}
					}else{
					// La fecha de ocurrencia no se encuentra en el rango de la poliza vigente
						Ext.Msg.show({
							title:'Error',
							msg: 'La fecha de ocurrencia no se encuentra en el rango de la p&oacute;liza vigente',
							buttons: Ext.Msg.OK,
							icon: Ext.Msg.ERROR
						});
						modPolizasAltaTramite.hide();
						limpiarRegistros();
						if(panelInicialPral.down('combo[name=cmbTipoPago]').getValue() == _TIPO_PAGO_DIRECTO){
							panelListadoAsegurado.query('combo[name=cmbAseguradoAfect]')[0].setValue('');
						}else{
							panelInicialPral.down('combo[name=cmbAseguradoAfectado]').setValue('');
						}
					}
				}else{
					// El asegurado no se encuentra vigente
					Ext.Msg.show({
						title:'Error',
						msg: 'El asegurado de la p&oacute;liza seleccionado no se encuentra vigente',
						buttons: Ext.Msg.OK,
						icon: Ext.Msg.ERROR
					});
					modPolizasAltaTramite.hide();
					limpiarRegistros();
					if(panelInicialPral.down('combo[name=cmbTipoPago]').getValue() == _TIPO_PAGO_DIRECTO){
						panelListadoAsegurado.query('combo[name=cmbAseguradoAfect]')[0].setValue('');
					}else{
						panelInicialPral.down('combo[name=cmbAseguradoAfectado]').setValue('');
					}
				}
			}
		}
	});
	gridPolizasAltaTramite.store.sort([
		{
			property    : 'nmpoliza',			direction   : 'DESC'
		}
	]);

	var modPolizasAltaTramite = Ext.create('Ext.window.Window',
	{
		title			: 'Listado de P&oacute;liza'
		,modal			: true
		,resizable		: false
		,buttonAlign	: 'center'
		,closable		: false
		,width			: 710
		,minHeight		: 100 
		,maxheight		: 400
		,items			:
		[
			gridPolizasAltaTramite
		]
	});

	/* PANEL PARA EL PAGO DIRECTO */
	var panelListadoAsegurado= Ext.create('Ext.form.Panel',{
		border  : 0
		,startCollapsed : true
		,bodyStyle:'padding:5px;'
		,items :
		[
			{
				xtype      : 'datefield',			name       : 'dtfechaOcurrencias',		fieldLabel : 'Fecha ocurrencia',
				allowBlank: false,					maxValue   :  new Date(),				format		: 'd/m/Y'
			},{
				xtype: 'combo',						name:'cmbAseguradoAfect',				fieldLabel : 'Asegurado',
				displayField : 'value',				valueField   : 'key',					allowBlank: false,
				width:500,							minChars  : 2,							forceSelection : true,
				matchFieldWidth: false,				queryMode :'remote',					queryParam: 'params.cdperson',
				store : storeAsegurados,			triggerAction: 'all',					hideTrigger:true,
				listeners : {
					'select' : function(combo, record) {
						var params = {	'params.cdperson' : this.getValue(),
										'params.cdramo' : panelInicialPral.down('combo[name=cmbRamos]').getValue()};
						cargaStorePaginadoLocal(storeListadoPoliza, _URL_CONSULTA_LISTADO_POLIZA, 'listaPoliza', params, function(options, success, response){
							if(success){
								var jsonResponse = Ext.decode(response.responseText);
								if(jsonResponse.listaPoliza == null) {
									Ext.Msg.show({
										title: 'Aviso',
										msg: 'No existen p&oacute;lizas para el asegurado elegido.',
										buttons: Ext.Msg.OK,
										icon: Ext.Msg.WARNING
									});
									combo.clearValue();
									modPolizasAltaTramite.hide();
									return;
								}
							}else{
								Ext.Msg.show({
									title: 'Aviso',
									msg: 'Error al obtener los datos.',
									buttons: Ext.Msg.OK,
									icon: Ext.Msg.ERROR
								});
							}
						});
						modPolizasAltaTramite.show();
					}
				}
			}
		]
	});
	/*PANTALLA EMERGENTE PARA EL PAGO DIRECTO */
	var ventanaAgregarAsegurado= Ext.create('Ext.window.Window', {
		title: 'Asegurados',
		closeAction: 'hide',
		modal: true, 
		resizable: false,
		items:[panelListadoAsegurado],
		buttonAlign : 'center',
		buttons:[
			{
				text: 'Aceptar',
				icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
				handler: function() {
					if (panelListadoAsegurado.form.isValid()) {
						var datos=panelListadoAsegurado.form.getValues();
						var rowSelected = panelInicialPral.down('[name=editorFacturaDirecto]').getSelectionModel().getSelection()[0];
						var noFactura= rowSelected.get('noFactura');
						var rec = new modelListAsegPagDirecto({
							modUnieco: panelInicialPral.down('[name="cdunieco"]').getValue(),
							modEstado: panelInicialPral.down('[name="estado"]').getValue(),
							modRamo: panelInicialPral.down('[name="cdramo"]').getValue(),
							modNmsituac: panelInicialPral.down('[name="nmsituac"]').getValue(),
							modPolizaAfectada: panelInicialPral.down('[name="polizaAfectada"]').getValue(),
							modNmsolici: panelInicialPral.down('[name="idNmsolici"]').getValue(),
							modNmsuplem: panelInicialPral.down('[name="idNmsuplem"]').getValue(),
							modCdtipsit: panelInicialPral.down('[name="idCdtipsit"]').getValue(),
							modNmautserv: "",
							modFechaOcurrencia: datos.dtfechaOcurrencias,
							modCdperson: datos.cmbAseguradoAfect,
							modCdpersondesc: panelListadoAsegurado.query('combo[name=cmbAseguradoAfect]')[0].rawValue,
							modnumPoliza:panelInicialPral.down('[name="idNumPolizaInt"]').getValue(),
							modFactura:noFactura
						});
						storeListAsegPagDirecto.add(rec);
						limpiarRegistros();
						panelListadoAsegurado.getForm().reset();
						ventanaAgregarAsegurado.close();
					} else {
						Ext.Msg.show({
							title: 'Aviso',
							msg: 'Complete la informaci&oacute;n requerida',
							buttons: Ext.Msg.OK,
							icon: Ext.Msg.WARNING
						});
					}
				}
			},
			{
				text: 'Cancelar',
				icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
				handler: function(){
					panelListadoAsegurado.getForm().reset();
					ventanaAgregarAsegurado.close();
				}
			}
		]
	});

	var modPolizasAltaTramite = Ext.create('Ext.window.Window',
	{
		title			: 'Listado de P&oacute;liza'
		,modal			: true
		,resizable		: false
		,buttonAlign	: 'center'
		,closable		: false
		,width			: 710
		,minHeight		: 100 
		,maxheight		: 400
		,items	:
		[
			gridPolizasAltaTramite
		]
	});
    
    var panelInicialPral= Ext.create('Ext.form.Panel',
	{
		border    : 0
		//,title: 'Alta de Tr&aacute;mite'
		,id : 'panelInicialPral'
		,renderTo : 'div_clau'
		,bodyPadding: 10
		,width: 800
		,layout     :
		{
			type     : 'table'
			,columns : 2
		}
		,defaults 	:
		{
			style : 'margin:5px;'
		}
		,items    :
		[
			{
				xtype	: 'textfield',		fieldLabel : 'Unieco',				name		: 'cdunieco',			labelWidth: 170,	hidden:true
			},
			{
				xtype	: 'textfield',		fieldLabel : 'estado',				name		: 'estado'	,			labelWidth: 170,	hidden:true
			},
			{
				xtype	: 'textfield',		fieldLabel : 'Ramo',				name		: 'cdramo'	,			labelWidth: 170,	hidden:true
			},	 			
			{
				xtype	: 'textfield',		fieldLabel : 'nmsituac',			name		: 'nmsituac',			labelWidth: 170,	hidden:true
			},
			{
				xtype	: 'textfield',		fieldLabel : 'P&oacute;liza afectada',name		:'polizaAfectada',		labelWidth: 170,	hidden:true
			},
			{
				xtype	: 'textfield',		fieldLabel : 'nmsolici',			name		: 'idNmsolici',			labelWidth: 170,	hidden:true
			},
			{
				xtype	: 'textfield',		fieldLabel : 'nmsuplem',			name		: 'idNmsuplem',			labelWidth: 170,	hidden:true
			},
			{
				xtype	: 'textfield',		fieldLabel : 'cdtipsit',			name		: 'idCdtipsit',			labelWidth: 170,	hidden:true
			},
			{
				xtype	: 'textfield',		fieldLabel : 'numPolizaInt',		name		: 'idNumPolizaInt',		labelWidth: 170,	hidden:true
			},
			{
				xtype	: 'textfield',		fieldLabel : 'Asegurado',			name		: 'idnombreAsegurado',	labelWidth: 170,	hidden:true
			},
			{
				xtype	: 'textfield',		fieldLabel : 'NumTramite',			name		: 'idNumTramite',		labelWidth: 170,	hidden:true
			},
			{
				xtype	: 'textfield',		fieldLabel : 'ImporteFactura',		name		: 'ImporteIndFactura',	labelWidth: 170,	hidden:true
			},
			{
				xtype	: 'datefield'		,fieldLabel : 'fechaIndFactura',	name		: 'fechaIndFactura',	format: 'd/m/Y',	hidden		:true
			},
			{
				xtype	: 'textfield',		fieldLabel : 'No.Factura',			name		: 'numIndFactura',		labelWidth: 170,	hidden:true
			},
			{
				xtype	: 'textfield',		fieldLabel: 'Estado',				name		: 'txtEstado',			readOnly   : true,	
				width	: 350,				value:'PENDIENTE'
			},
			cmbRamos,
			tipoPago,
			comboTipoAte,
			cmbOficinaReceptora,
			cmbOficinaEmisora,
			{
				xtype	: 'datefield',		width		: 350,			fieldLabel	: 'Fecha recepci&oacute;n',	name		: 'dtFechaRecepcion'	,
				value	: new Date(),		format		: 'd/m/Y',		readOnly	: true,						allowBlank	: false
			},
			{
				xtype	: 'datefield',		fieldLabel	: 'Fecha ocurrencia',		name		: 'dtFechaOcurrencia',			format	: 'd/m/Y',
				editable: true,				width		: 350,						maxValue	:  new Date(),					allowBlank : false
			},
			aseguradoAfectado,
			cmbBeneficiario,
			cmbProveedor,
			{
				xtype	: 'textfield',		fieldLabel : 'Nombre Proveedor',		name       : 'idnombreBeneficiarioProv',colspan:2,		width	: 350,
				listeners:{
					afterrender: function(){
						this.hide();
					}
				}
			},
			{
				colspan:2
				,border: false
				,items    :
				[
					gridFacturaDirecto 
				]
			},
			{
				colspan:2
				,border: false
				,items    :
				[
					gridFacturaReembolso
				]
			},
			{
				colspan:2
				,border: false
				,items    :
				[
					gridAsegPagDirecto
				]
			}
		],
		buttonAlign:'center',
		buttons: [{
			id:'botonCotizar',
			icon:_CONTEXT+'/resources/fam3icons/icons/calculator.png',
			text: 'Generar Tr&aacute;mite',
			handler: function() {
				var form = this.up('form').getForm();
				if (form.isValid()){
					if(panelInicialPral.down('combo[name=cmbTipoPago]').getValue() == _TIPO_PAGO_DIRECTO){ //PARA PAGO DIRECTO
						panelInicialPral.down('combo[name=cmbOficEmisora]').setValue("1000");
						var obtener = [];
						storeFacturaDirecto.each(function(record) {
							obtener.push(record.data);
						});
						/*VERIFICAMOS QUE EXISTA AL MENOS UNA FACTURA*/
						if(obtener.length <= 0){
							Ext.Msg.show({
								title:'Error',
								msg: 'Se requiere ingresar al menos una factura',
								buttons: Ext.Msg.OK,
								icon: Ext.Msg.ERROR
							});
							return false;
						}else{
							/*VERIFICAMOS SI ES UNA FACTURA Y EL NUMERO DE ASEGURADO*/
							if(obtener.length == 1){
								panelInicialPral.down('[name=ImporteIndFactura]').setValue(obtener[0].importe);
								panelInicialPral.down('[name=fechaIndFactura]').setValue(obtener[0].fechaFactura);
								panelInicialPral.down('[name=numIndFactura]').setValue(obtener[0].noFactura);
								Ext.Ajax.request(
								{
									url     : _URL_ASEGURADO_FACTURA
									,params: {
										'params.nfactura'  : obtener[0].noFactura,
										'params.ntramite'  : panelInicialPral.down('[name=idNumTramite]').getValue()
									}
									,success : function (response)
									{
										var listadoAsegurado=Ext.decode(response.responseText).slist1;
										/*	SI ASEGURADO ES IGUAL A 1	*/
										if(listadoAsegurado.length == "1"){
											var fecOcurrencia = listadoAsegurado[0].MODFECHAOCURRENCIA.match(/\d+/g);
											panelInicialPral.down('[name="cdunieco"]').setValue(listadoAsegurado[0].MODUNIECO);
											panelInicialPral.down('[name="estado"]').setValue(listadoAsegurado[0].MODESTADO);
											panelInicialPral.down('[name="polizaAfectada"]').setValue(listadoAsegurado[0].MODPOLIZAAFECTADA);
											panelInicialPral.down('[name="idNmsuplem"]').setValue(listadoAsegurado[0].MODNMSUPLEM);
											panelInicialPral.down('[name="idNmsolici"]').setValue(listadoAsegurado[0].MODNMSOLICI);
											panelInicialPral.down('[name="nmsituac"]').setValue(listadoAsegurado[0].MODNMSITUAC);
											panelInicialPral.down('[name="idCdtipsit"]').setValue(listadoAsegurado[0].MODCDTIPSIT);
											panelInicialPral.down('[name=dtFechaOcurrencia]').setValue(fecOcurrencia[2]+"/"+fecOcurrencia[1]+"/"+fecOcurrencia[0]);//(listadoAsegurado[0].MODFECHAOCURRENCIA);
											storeAsegurados.load({
												params:{
													'params.cdperson':listadoAsegurado[0].MODCDPERSON
												}
											});
											panelInicialPral.down('combo[name=cmbAseguradoAfectado]').setValue(listadoAsegurado[0].MODCDPERSON);//listadoAsegurado[0].MODCDPERSON);
										}else{
												/*	SI ASEGURADO ES MAYOR A 1	*/
												panelInicialPral.down('[name="cdunieco"]').setValue('');
												panelInicialPral.down('[name="estado"]').setValue('');
												panelInicialPral.down('[name="polizaAfectada"]').setValue('');
												panelInicialPral.down('[name="idNmsuplem"]').setValue('');
												panelInicialPral.down('[name="idNmsolici"]').setValue('');
												panelInicialPral.down('[name="nmsituac"]').setValue('');
												panelInicialPral.down('[name="idCdtipsit"]').setValue('');
												panelInicialPral.down('combo[name=cmbAseguradoAfectado]').setValue('');
												panelInicialPral.down('[name=dtFechaOcurrencia]').setValue('');
										}
										var submitValues={};
										var formulario=panelInicialPral.form.getValues();
										submitValues['params']=formulario;
										procesaGuardaAltaTramite(submitValues);
									},
									failure : function (){
										Ext.Msg.show({
											title:'Error',
											msg: 'Error de comunicaci&oacute;n',
											buttons: Ext.Msg.OK,
											icon: Ext.Msg.ERROR
										});
									}
								});
							}else{
								//panelInicialPral.down('[name=ImporteIndFactura]').setValue('');
								panelInicialPral.down('[name=fechaIndFactura]').setValue('');
								panelInicialPral.down('[name=numIndFactura]').setValue('');
								var sumaTotal= 0;
								for(i=0;i < obtener.length;i++){
									sumaTotal =sumaTotal + (+ obtener[i].importe);
									panelInicialPral.down('[name=ImporteIndFactura]').setValue(sumaTotal);
								}
								
								var submitValues={};
								var formulario=panelInicialPral.form.getValues();
								submitValues['params']=formulario;
								procesaGuardaAltaTramite(submitValues);
							}
						}
				
					}else{
						var obtener = [];
						/*Verificamos el numero total de facturas*/
						storeFacturaReembolso.each(function(record) {
							obtener.push(record.data);
						});
						if(obtener.length <= 0){
							Ext.Msg.show({
								title:'Error',
								msg: 'Se requiere ingresar al menos una factura',
								buttons: Ext.Msg.OK,
								icon: Ext.Msg.ERROR
							});
							return false;
						}else{
							if(obtener.length == 1){
								panelInicialPral.down('combo[name=cmbProveedor]').setValue(obtener[0].proveedorName);
								panelInicialPral.down('[name=ImporteIndFactura]').setValue(obtener[0].importe);
								panelInicialPral.down('[name=fechaIndFactura]').setValue(obtener[0].fechaFactura);
								panelInicialPral.down('[name=numIndFactura]').setValue(obtener[0].noFactura);
							
							}else{
								panelInicialPral.down('combo[name=cmbProveedor]').setValue('');
								//panelInicialPral.down('[name=ImporteIndFactura]').setValue('');
								panelInicialPral.down('[name=fechaIndFactura]').setValue('');
								panelInicialPral.down('[name=numIndFactura]').setValue('');
								var sumaTotal= 0;
								for(i=0;i < obtener.length;i++){
									sumaTotal =sumaTotal + (+ obtener[i].importe);
									panelInicialPral.down('[name=ImporteIndFactura]').setValue(sumaTotal);
								}
							}
						}
						var submitValues={};
						var datosTablas = [];
						var formulario=panelInicialPral.form.getValues();
						submitValues['params']=formulario;
						
						storeFacturaReembolso.each(function(record,index){
							datosTablas.push({
								nfactura:record.get('noFactura'),
								ffactura:record.get('fechaFactura'),
								cdtipser:panelInicialPral.down('combo[name=cmbTipoAtencion]').getValue(),
								cdpresta:record.get('proveedorName'),
								ptimport:record.get('importe'),
								cdmoneda:record.get('tipoMonedaName'),
								tasacamb:record.get('tasaCambio'),
								ptimporta:record.get('importeFactura')
							});
						});
						
						submitValues['datosTablas']=datosTablas;
						panelInicialPral.setLoading(true);
						procesaGuardaAltaTramite(submitValues);
					}
				}
				else{
					Ext.Msg.show({
						title:'Datos incompletos',
						msg: 'Favor de introducir todos los campos requeridos',
						buttons: Ext.Msg.OK,
						icon: Ext.Msg.WARNING
					});
				}
			}
		},
		{
			text:'Generar Contra-Recibo',
			icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/page_white_edit.png',
			handler:function()
			{
				Ext.Ajax.request({
					url: _UrlGenerarContrarecibo,
					params: {
						'paramsO.pv_cdunieco_i' : panelInicialPral.down('combo[name=cmbOficReceptora]').getValue(),
						'paramsO.pv_cdramo_i'   : panelInicialPral.down('combo[name=cmbRamos]').getValue(),
						'paramsO.pv_estado_i'   : panelInicialPral.down('[name="estado"]').getValue(),
						'paramsO.pv_nmpoliza_i' : panelInicialPral.down('[name="polizaAfectada"]').getValue(),
						'paramsO.pv_nmsuplem_i' : panelInicialPral.down('[name="idNmsuplem"]').getValue(),
						'paramsO.pv_ntramite_i' : panelInicialPral.down('[name="idNumTramite"]').getValue(),
						'paramsO.pv_nmsolici_i' : panelInicialPral.down('[name="idNmsolici"]').getValue(),
						'paramsO.pv_cdtippag_i' : panelInicialPral.down('combo[name=cmbTipoPago]').getValue(),
						'paramsO.pv_cdtipate_i' : panelInicialPral.down('combo[name=cmbTipoAtencion]').getValue(),
						'paramsO.pv_tipmov_i'   : panelInicialPral.down('combo[name=cmbTipoPago]').getValue()
					},
					success: function(response, opt) {
						var jsonRes=Ext.decode(response.responseText);
						if(jsonRes.success == true){
							loadMcdinStore();
							var numRand=Math.floor((Math.random()*100000)+1);
							debug('numRand a: ',numRand);
							var windowVerDocu=Ext.create('Ext.window.Window',
							{
								title          : 'Contrarecibo de Documentos del Siniestro'
								,width         : 700
								,height        : 500
								,collapsible   : true
								,titleCollapse : true
								,html          : '<iframe innerframe="'+numRand+'" frameborder="0" width="100" height="100"'
													+'src="'+panDocUrlViewDoc+'?idPoliza=' + panelInicialPral.down('[name="idNumTramite"]').getValue() + '&filename=' + '<s:text name="siniestro.contrarecibo.nombre"/>' +'">'
													+'</iframe>'
								,listeners     :
								{
									resize : function(win,width,height,opt){
										debug(width,height);
										$('[innerframe="'+numRand+'"]').attr({'width':width-20,'height':height-60});
									}
								}
							}).show();
							windowVerDocu.center();
						}else {
								mensajeError(jsonRes.msgResult);
						}
					},
					failure: function(){
						mensajeError('No se pudo generar contrarecibo.');
					}
				});
			}
		}]
	});

	if(valorAction.ntramite == null){
		/*Oficina Receptora*/
		oficinaReceptora.load();
		panelInicialPral.down('combo[name=cmbOficReceptora]').setValue(valorAction.cdunieco);
		/*Tipo de pago*/
		panelInicialPral.down('combo[name=cmbTipoPago]').setValue(_TIPO_PAGO_DIRECTO);
		limpiarRegistrosTipoPago(panelInicialPral.down('combo[name=cmbTipoPago]').getValue());
		/*Oficina Emisora*/
		oficinaEmisora.load();
		panelInicialPral.down('combo[name=cmbOficEmisora]').setValue("1000");
	}else{
		storeProveedor.load();
		Ext.Ajax.request(
		{
			url     : _URL_CONSULTA_ALTA_TRAMITE
			,params:{
				'params.ntramite': valorAction.ntramite
			}
			,success : function (response)
			{
				if(Ext.decode(response.responseText).listaMesaControl != null){
					var json=Ext.decode(response.responseText).listaMesaControl[0];
					//ASIGNACION DE VALORES GENERALES PARA PAGO DIRECTO Y REEMBOLSO
					panelInicialPral.down('[name=idNumTramite]').setValue(valorAction.ntramite);
					panelInicialPral.down('[name=txtEstado]').setValue('PENDIENTE');
					panelInicialPral.down('combo[name=cmbOficReceptora]').setValue(json.cdsucdocmc);
					oficinaEmisora.load();
					panelInicialPral.down('combo[name=cmbOficEmisora]').setValue(json.cdsucadmmc);
					panelInicialPral.down('[name=dtFechaRecepcion]').setValue(json.ferecepcmc);
					panelInicialPral.down('combo[name=cmbTipoAtencion]').setValue(json.otvalor07mc);
					
					panelInicialPral.down('combo[name=cmbTipoPago]').setValue(json.otvalor02mc);
					
					if(panelInicialPral.down('combo[name=cmbTipoPago]').getValue() == _TIPO_PAGO_DIRECTO){
						panelInicialPral.down('combo[name=cmbTipoAtencion]').show();
					}else{
						panelInicialPral.down('combo[name=cmbTipoAtencion]').hide();
					}
					storeRamos.load({
						params:{
							'params.idPadre':panelInicialPral.down('combo[name=cmbOficEmisora]').getValue()
						}
					});
					limpiarRegistrosTipoPago(panelInicialPral.down('combo[name=cmbTipoPago]').getValue());
					if(json.otvalor20mc == null || json.otvalor20mc==''){
						panelInicialPral.down('combo[name=cmbRamos]').setValue("2");
					}else{
						panelInicialPral.down('combo[name=cmbRamos]').setValue(json.otvalor20mc);
					}
					//VALORES DE PAGO DIRECTO
					if(panelInicialPral.down('combo[name=cmbTipoPago]').getValue() == _TIPO_PAGO_DIRECTO){
						panelInicialPral.down('combo[name=cmbProveedor]').setValue(json.otvalor11mc);
						panelInicialPral.down('[name=ImporteIndFactura]').setValue(json.otvalor03mc);
						panelInicialPral.down('[name=fechaIndFactura]').setValue(json.otvalor06mc);
						panelInicialPral.down('[name=numIndFactura]').setValue(json.otvalor08mc);
						panelInicialPral.down('[name=idnombreBeneficiarioProv]').setValue(json.otvalor15mc);
						if(json.otvalor11mc =='0'){
							panelInicialPral.down('[name=idnombreBeneficiarioProv]').show();
						}else{
							panelInicialPral.down('[name=idnombreBeneficiarioProv]').hide();
						}
					}else{
						//VALORES DE PAGO POR REEMBOLSO
						panelInicialPral.down('[name="cdunieco"]').setValue(json.cduniecomc);
						panelInicialPral.down('[name="estado"]').setValue(json.estadomc);
						panelInicialPral.down('[name="cdramo"]').setValue(json.cdramomc);
						panelInicialPral.down('[name="polizaAfectada"]').setValue(json.nmpolizamc);
						panelInicialPral.down('[name="idNmsolici"]').setValue(json.nmsolicimc);
						panelInicialPral.down('[name="idNmsuplem"]').setValue(json.nmsuplemmc);
						panelInicialPral.down('[name="idCdtipsit"]').setValue(json.cdtipsitmc);
						panelInicialPral.down('[name=dtFechaOcurrencia]').setValue(json.otvalor10mc);
						panelInicialPral.down('[name=idnombreBeneficiarioProv]').setValue(json.otvalor15mc);
						panelInicialPral.down('[name=idnombreAsegurado]').setValue(json.nombremc);
						storeAsegurados.load({
							params:{
								'params.cdperson':json.otvalor09mc
							}
						});
						panelInicialPral.down('combo[name=cmbAseguradoAfectado]').setValue(json.otvalor09mc);
						storeAsegurados2.load({
							params:{
								'params.cdperson':json.otvalor04mc
							}
						});
						panelInicialPral.down('combo[name=cmbBeneficiario]').setValue(json.otvalor04mc);
					}
					
					/*COMO SEGUNDO PUNTO ES OBTENER LA INFORMACION DE LOS GRIDS*/
					Ext.Ajax.request({
						url     : _URL_CONSULTA_GRID_ALTA_TRAMITE
						,params:{
							'params.ntramite': valorAction.ntramite
						}
						,success : function (response)
						{
							if(Ext.decode(response.responseText).listaAltaTramite != null)
							{
								var json=Ext.decode(response.responseText).listaAltaTramite;
								if(panelInicialPral.down('combo[name=cmbTipoPago]').getValue() == _TIPO_PAGO_DIRECTO){
									//PAGO DIRECTO
									for(var i = 0; i < json.length; i++){
										var fechaFacturaM = json[i].ffactura.match(/\d+/g); 
										var date = new Date(fechaFacturaM[2], fechaFacturaM[1]-1,fechaFacturaM[0]);
										var rec = new modelFacturaSiniestro({
											noFactura: json[i].nfactura,
											fechaFactura: date,
											tipoServicio: json[i].cdtipser,
											proveedor: json[i].cdpresta,
											importe: json[i].ptimport,
											proveedorName: json[i].cdpresta,
											tipoServicioName: json[i].dstipser,
											tipoMoneda: json[i].cdmoneda,
											tipoMonedaName: json[i].cdmoneda,
											tasaCambio: json[i].tasacamb,
											importeFactura:json[i].ptimporta
										});
										storeFacturaDirecto.add(rec);
									}
								}else{
									//PAGO POR REEMBOLSO
									storeProveedor.load();
									panelInicialPral.down('[name="nmsituac"]').setValue(json[0].nmsituac);
									for(var i = 0; i < json.length; i++){
										var fechaFacturaM = json[i].ffactura.match(/\d+/g); 
										var dateFac = new Date(fechaFacturaM[2], fechaFacturaM[1]-1,fechaFacturaM[0]);
										var rec = new modelFacturaSiniestro({
											noFactura: json[i].nfactura,
											fechaFactura: dateFac,
											tipoServicio: json[i].cdtipser,
											proveedor: json[i].cdpresta,
											importe: json[i].ptimport,
											proveedorName: json[i].cdpresta,
											tipoServicioName: json[i].dstipser,
											tipoMoneda: json[i].cdmoneda,
											tipoMonedaName: json[i].cdmoneda,
											tasaCambio: json[i].tasacamb,
											importeFactura:json[i].ptimporta
										});
										storeFacturaReembolso.add(rec);
									}
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

	function procesaGuardaAltaTramite(submitValues){
		panelInicialPral.setLoading(true);
		Ext.Ajax.request(
		{
			url: _URL_GUARDA_ALTA_TRAMITE,
			jsonData:Ext.encode(submitValues), // convierte a estructura JSON
			success:function(response,opts){
				panelInicialPral.setLoading(false);
				var jsonResp = Ext.decode(response.responseText);
				if(jsonResp.success==true){
					var etiqueta;
					var mensaje;
					if(valorAction.ntramite == null){
						etiqueta = "Guardado";
						mensaje = "Se gener&oacute; el n&uacute;mero de tr&aacute;mite "+ Ext.decode(response.responseText).msgResult; 
					}else{
						etiqueta = "Modificaci&oacute;n";
						mensaje = "Se modific&oacute; el n&uacute;mero de tr&aacute;mite "+ valorAction.ntramite;
					}
					mensajeCorrecto(etiqueta,mensaje,function(){
						Ext.create('Ext.form.Panel').submit(
						{
							url		: _p12_urlMesaControl
							,standardSubmit : true
							,params         :
							{
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

	function limpiarRegistros()
	{
		panelInicialPral.down('[name="cdunieco"]').setValue('');
		panelInicialPral.down('[name="estado"]').setValue('');
		panelInicialPral.down('[name="cdramo"]').setValue('');
		panelInicialPral.down('[name="nmsituac"]').setValue('');
		panelInicialPral.down('[name="polizaAfectada"]').setValue('');
		panelInicialPral.down('[name="idNmsolici"]').setValue('');
		panelInicialPral.down('[name="idNmsuplem"]').setValue('');
		panelInicialPral.down('[name="idCdtipsit"]').setValue('');
		panelInicialPral.down('[name="idNumPolizaInt"]').setValue('');
    	return true;
	}
	
	function limpiarRegistrosTipoPago(tipoPago)
	{
		var pagoDirecto = true;
		var pagoReembolso = true;
		if(tipoPago == _TIPO_PAGO_DIRECTO){
			pagoDirecto = false;
			pagoReembolso = true;
			panelInicialPral.down('[name=editorFacturaDirecto]').show();
			panelInicialPral.down('[name=EditorAsegPagDirecto]').show();
			panelInicialPral.down('[name=editorFacturaReembolso]').hide();
			panelInicialPral.down('combo[name=cmbBeneficiario]').hide();
			panelInicialPral.down('combo[name=cmbAseguradoAfectado]').hide();
			panelInicialPral.down('[name=dtFechaOcurrencia]').hide();
			panelInicialPral.down('combo[name=cmbBeneficiario]').setValue('');
			panelInicialPral.down('combo[name=cmbAseguradoAfectado]').setValue('');
			panelInicialPral.down('[name=dtFechaOcurrencia]').setValue('');
			panelInicialPral.down('combo[name=cmbProveedor]').show();
		}else{
			pagoDirecto = true;
			pagoReembolso = false;
			panelInicialPral.down('[name=editorFacturaDirecto]').hide();
			panelInicialPral.down('[name=EditorAsegPagDirecto]').hide();
			panelInicialPral.down('[name=editorFacturaReembolso]').show();
			panelInicialPral.down('combo[name=cmbProveedor]').hide();
			panelInicialPral.down('combo[name=cmbProveedor]').setValue('');
			panelInicialPral.down('combo[name=cmbBeneficiario]').show();
			panelInicialPral.down('combo[name=cmbAseguradoAfectado]').show();
			panelInicialPral.down('[name=dtFechaOcurrencia]').show();
		}
		panelInicialPral.down('combo[name=cmbBeneficiario]').allowBlank = pagoReembolso;
		panelInicialPral.down('combo[name=cmbAseguradoAfectado]').allowBlank = pagoReembolso;
		panelInicialPral.down('[name=dtFechaOcurrencia]').allowBlank = pagoReembolso;
		panelInicialPral.down('combo[name=cmbProveedor]').allowBlank = pagoDirecto;
		return true;
	}
	
	function _p21_agregarFactura()
	{
		if(panelInicialPral.down('combo[name=cmbTipoPago]').getValue() == _TIPO_PAGO_DIRECTO){
			storeFacturaDirecto.add(new modelFacturaSiniestro({tasaCambio:'0.00',importeFactura:'0.00',tipoMonedaName:'001'}));
		}else{
			storeFacturaReembolso.add(new modelFacturaSiniestro({tasaCambio:'0.00',importeFactura:'0.00',tipoMonedaName:'001'}));
		}
	}
	
	function _p21_agregarAseguradoClic()
	{
		if(panelInicialPral.down('[name=editorFacturaDirecto]').getSelectionModel().hasSelection()){
			limpiarRegistros();
			var rowSelected = panelInicialPral.down('[name=editorFacturaDirecto]').getSelectionModel().getSelection()[0];
			var noFactura= rowSelected.get('noFactura');
			ventanaAgregarAsegurado.show();
		}else{
			centrarVentanaInterna(mensajeWarning("Debe seleccionar una factura para poder agregar los asegurados."));
		}
	}
	
	function validarFacturaPagada(cdpresta,nfactura){
		Ext.Ajax.request(
		{
			url     : _URL_CONSULTA_FACTURA_PAGADA
			,params:{
				'params.nfactura' : nfactura,
				'params.cdpresta' : cdpresta
			}
			,success : function (response)
			{
				if(Ext.decode(response.responseText).factPagada !=null)
				{
					centrarVentanaInterna(Ext.Msg.show({
						title: 'Aviso',
						msg: 'La factura '+ nfactura +' ya se encuentra pagada en el tr&aacute;mite '+Ext.decode(response.responseText).factPagada,
						buttons: Ext.Msg.OK,
						icon: Ext.Msg.WARNING
					}));
				}
			},
			failure : function ()
			{
				me.up().up().setLoading(false);
				centrarVentanaInterna(Ext.Msg.show({
					title:'Error',
					msg: 'Error de comunicaci&oacute;n',
					buttons: Ext.Msg.OK,
					icon: Ext.Msg.ERROR
				}));
			}
		});
	}
});