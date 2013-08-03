var helpMap = new Map();
Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";   

	var _record = new Ext.data.Record.create([
			{name: 'cdElemento', type: 'string', mapping: 'cdElemento'},
			{name: 'cdDocumen', type: 'string', mapping: 'cdDocumen'},
			{name: 'cdDocXCta', type: 'string', mapping: 'cdDocXCta'},
			{name: 'dsFormato', type: 'string', mapping: 'dsFormato'},
       		{name: 'dsRequisito',   type: 'string',  mapping:'dsRequisito'},
       		{name: 'indEntrega',   type: 'string',  mapping:'indEntrega'}
	]);
	var _readerRequisitos = new Ext.data.JsonReader(
		{
			root: 'listaRequisitos',
			totalProperty: 'totalCount',
			successProperty: '@success'
		},
		//_record
		[
			{name: 'cdElemento', type: 'string', mapping: 'cdElemento'},
			{name: 'cdDocumen', type: 'string', mapping: 'cdDocumen'},
			{name: 'cdDocXCta', type: 'string', mapping: 'cdDocXCta'},
			{name: 'dsFormato', type: 'string', mapping: 'dsFormato'},
       		{name: 'dsRequisito',   type: 'string',  mapping:'dsRequisito'},
       		{name: 'indEntrega',   type: 'string',  mapping:'indEntrega'}
		]
	);
	var _storeRequisitos = new Ext.data.Store(
		{
		 proxy: new Ext.data.HttpProxy({url: _ACTION_BUSCAR_REQUISITOS}),
		 reader: _readerRequisitos
	});
	
	

		var storeProducto = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_COMBO_PRODUCTOS
                }),

                reader: new Ext.data.JsonReader({
            	root:'comboGenerico',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
		        {name: 'codigo',  type: 'string',  mapping:'codigo'},
		        {name: 'descripcion',  type: 'string',  mapping:'descripcion'}
			])
        });
		
		var storeComboPolizas = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_COMBO_POLIZAS
                }),

                reader: new Ext.data.JsonReader({
            	root:'comboPolizasCanceladas',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
		        {name: 'codigo',  type: 'string',  mapping:'nmPoliza'},
		        {name: 'descripcion',  type: 'string',  mapping:'nmPoliex'}
			])
        });

         var storeAseguradoras = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_COMBO_ASEGURADORAS
                }),

                reader: new Ext.data.JsonReader({
            	root:'comboPolizasCancManualAseguradoras',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
		        {name: 'codigo',  type: 'string',  mapping:'codigo'},
		        {name: 'descripcion',  type: 'string',  mapping:'descripcion'}
			])
        });

    var fgCumplio = new Ext.grid.CheckColumn({
			header: getLabelFromMap('cmCumplio', helpMap, 'Cumpli&oacute;'),
			tooltip: getToolTipFromMap('cmCumplio', helpMap,'Cumpli&oacute;'),
			dataIndex: 'indEntrega',
	      align: 'center',
	      sortable: true,
	      width: 80,
	      value:true
    });

	var columnModel = new Ext.grid.ColumnModel([
		{
			header: getLabelFromMap('cmDescripcion', helpMap, 'Descripci&oacute;n'),
			tooltip: getToolTipFromMap('cmDescripcion', helpMap,'Descripci&oacute;n'),
			dataIndex: 'dsRequisito',
			editor: new Ext.form.TextField({id: 'ReqDesc'})
		},
		{
			header: getLabelFromMap('cmDocumento', helpMap, 'Documento'),
			tooltip: getToolTipFromMap('cmDocumento', helpMap,'Documento'),
			renderer : crearHLink
		},
		fgCumplio
		/*{
			header: getLabelFromMap('cmCumplio', helpMap, 'Cumpli&oacute;'),
			tooltip: getToolTipFromMap('cmCumplio', helpMap,'Cumpli&oacute;'),
			dataIndex: 'indEntrega',
			editor: new Ext.form.Checkbox({id: 'chk'})
		}*/
	]);
	var grilla = new Ext.grid.GridPanel({
			title: '<center>Requisitos</center>',
	        el:'grillaRequisitos',
	        store: _storeRequisitos,
			border:true,
			frame: true,
			region: 'center',
			collapsible: true,
			stripeRows: true,
            //autoWidth : true,
            autoScroll: true,
			enableColumnResize: true,
	        clicksToEdit:1,
	        cm: columnModel,
	        loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
			height: 120,
			width: 260,
			plugins: [fgCumplio],	
			sm: new Ext.grid.RowSelectionModel({singleSelect: true})/*,
			bbar: new Ext.PagingToolbar({
					pageSize: itemsPerPage,
					store: _storeRequisitos,
					displayInfo: false,
	                displayMsg: '', //getLabelFromMap('400009', helpMap,'Registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros')
			    })*/
	});

	var _proxyPoliza = new Ext.data.HttpProxy({url: _ACTION_BUSCAR_POLIZA});
	
	var _readerPoliza = new Ext.data.JsonReader(
		{root: 'listaPoliza',totalProperty: 'totalCount',successProperty: 'success'},
		[
		
       		//{name: 'cdUniEco',   type: 'string',  mapping:'cdAseguradora'},
       		{name: 'dsUniEco',   type: 'string',  mapping:'dsUniEco'},
       		
       	//	{name: 'cdRamo',   type: 'string',  mapping:'cdProducto'},
       	//	{name: 'dsRamo', type: 'string',  mapping:'dsRamo'},
       		{name: 'fechaInciso', type: 'string',  mapping:'fechaInciso'},
       		{name: 'fechaVencimiento', type: 'string',  mapping:'fechaVencimiento'},
       		{name: 'nmInciso', type: 'string',  mapping:'nmInciso'},
       		{name: 'dsRazonCancelacion', type: 'string', mapping: 'dsRazonCancelacion'},
       		{name: 'fechaCancelacion', type: 'string', mapping: 'fechaCancelacion'},
       		{name: 'comentariosCancelacion', type: 'string', mapping: 'comentariosCancelacion'},
       		{name: 'cdRazonCancelacion', type: 'string', mapping: 'cdRazonCancelacion'},
       		{name: 'cdPerson', type: 'string', mapping: 'cdPerson'},
       		{name: 'cdMoneda', type: 'string', mapping: 'moneda'},
       		{name: 'cdRazonCancelacion', type: 'string', mapping: 'cdRazonCancelacion'},
       		{name: 'nmCancel', type: 'string', mapping: 'nmCancel'},
       		{name: 'dsRazonCancelacion', type: 'string', mapping: 'dsRazonCancelacion'},
       		{name: 'incisoExt', type:'string', mapping:'incisoExt'}
		]);

	
	function renderDate (value) {
		Ext.util.Format.dateRenderer('Y-m-d H:i:s.g');	
	}
	var _storePoliza = new Ext.data.Store(
		{
		 proxy: _proxyPoliza,
		 reader: _readerPoliza
		});

	var _storeDatosCancelacion = new Ext.data.Store({
		 proxy: new Ext.data.HttpProxy({url: _ACTION_BUSCAR_DATOS_CANCELACION}),
		 reader: _readerPoliza
	});

	var el_formpanel = new Ext.FormPanel ({
			renderTo: "formulario",
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formId',helpMap,'Rehabilitaci&oacute;n Manual')+'</span>',
            frame : true,
            width : 700,
            autoHeight: true,
            successProperty: '@success',
            bodyStyle: 'background:white',
            layout: 'table',
            layoutConfig: { columns: 3},
            labelWidth: 70,
            store: _storePoliza,
            //reader: _readerPoliza,
            url: _ACTION_BUSCAR_POLIZA,
            items: [ 
            		/*{
            			layout: 'form',
            			colspan: 3,
            			items: [{
            				html: '<b>Aerrrr</b>',
            				border: false,
                   			cls: 'x-form-item custom-label',
                   			id: 'texto',
                   			name: 'texto'
            			}]
            		},*/
            		{
            			layout: 'form',
            			width : 210,
            			items: [
            				{xtype: 'hidden', name: 'cdPerson', id: 'cdPerson'},
            				{xtype: 'hidden', name: 'cdMoneda', id: 'cdMoneda'},
            				{xtype: 'hidden', name: 'estado', id: 'estado'},
            				{xtype: 'hidden', name: 'nmsuplem', id: 'nmsuplem'},
            				{xtype: 'hidden', name: 'nmCancel', id: 'nmCancel'},
            				{
            				xtype: 'textfield', 
				            fieldLabel: getLabelFromMap('dsAsegurado',helpMap,'Asegurado'),
			                tooltip: getToolTipFromMap('dsAsegurado',helpMap,'Asegurado'),			          		
		                    hasHelpIcon:getHelpIconFromMap('dsAsegurado',helpMap),
		 					Ayuda:getHelpTextFromMap('dsAsegurado',helpMap),
            				//fieldLabel: 'Asegurado', 
		 					width: 110,
            				//anchor: '95%', 
            				id: 'dsAsegurado', 
            				name: 'dsAsegurado', 
            				disabled: true,
            				value:DES_ASEG
            				}
            			]
            		},
					{
						layout: 'form',
						width : 210,
						style: 'padding: 0px 0px 0px 5px',
						//colspan: 2,
						items: [
							{xtype: 'hidden', name: 'dsUniEco', id: 'dsUniEco'},
							{
							xtype: 'textfield', 
							//fieldLabel: 'Aseguradora', 
				            fieldLabel: getLabelFromMap('dsAseguradora',helpMap,'Aseguradora'),
			                tooltip: getToolTipFromMap('dsAsegurado',helpMap,'Aseguradora'),			          		
		                    hasHelpIcon:getHelpIconFromMap('dsAseguradora',helpMap),
		 					Ayuda:getHelpTextFromMap('dsAseguradora',helpMap),
		 					width: 110,
							//anchor: '95%', 
							id: 'dsAseguradora', 
							name: 'dsAseguradora', 
							disabled: true,
							value:DSUNIECO
							}
								/*	{
										//labelSeparator: '',
					                    xtype: 'combo', 
					                    labelWidth: 70, 
					                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
						                store: storeAseguradoras,
						                displayField:'descripcion', 
						                valueField:'codigo', 
						                //hiddenName: 'ds_Producto', 
						                typeAhead: true,
						                mode: 'local', 
						                triggerAction: 'all', 
						                labelAlign: 'top',
						                fieldLabel: getLabelFromMap('cmbAseguradora',helpMap,'Aseguradora'),
					                    tooltip: getToolTipFromMap('cmbAseguradora',helpMap,'Aseguradora'),
						                width: 150, 
						                emptyText:'Seleccione ...',
						                selectOnFocus:true,
						               // disabled: true, 
						                forceSelection:true,
						                id: 'ds_Aseguradora',
						                name: 'ds_Aseguradora',
						                onSelect: function (record) {
						                		this.setValue(record.get('codigo'));
						                		this.collapse();
						                	/*	Ext.getCmp('dsProducto').clearValue();
						                		Ext.getCmp('dsProducto').setRawValue('');*/
						                	/*	storeProducto.load({
						                			params: {
						                				cdUniEco: record.get('codigo')
						                			},
						                			callback: function () {
						                				/*if (storePoliza.reader.jsonData) {
						                					Ext.getCmp('dsProducto').setValue(storePoliza.reader.jsonData.cdRamo);
						                				}*//*
						                			}
						                		});
						                		Ext.getCmp('nmPoliza').clearValue();
						                		Ext.getCmp('nmPoliza').setValue('');
						                		Ext.getCmp('nmPoliza').setRawValue('');
						                		storeComboPolizas.removeAll();
						                } 
						                //allowBlank : false,
						                //blankText: getMsgBlankTextFromMap('400013', helpMap, 'Este campo es requerido'),
						             }*/
						]
					},
					{
						layout: 'form'
						
					},
					{
						layout: 'form',
						items: [
						 	{xtype: 'hidden', name: 'cdRamo', id: 'cdRamo'},
							{
							xtype: 'textfield', 
							//fieldLabel: 'Producto', 
				            fieldLabel: getLabelFromMap('dsRamo',helpMap,'Producto'),
			                tooltip: getToolTipFromMap('dsRamo',helpMap,'Producto'),			          		
		                    hasHelpIcon:getHelpIconFromMap('dsRamo',helpMap),
		 					Ayuda:getHelpTextFromMap('dsRamo',helpMap),
		 					width: 110,
							//anchor: '95%', 
							id: 'dsRamo', 
							name: 'dsRamo', 
							disabled: true,
							value:DSRAMO
							}
								/*	{
										//labelSeparator: '',
					                    xtype: 'combo', 
					                    labelWidth: 70, 
					                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
						                store: storeProducto,
						                displayField:'descripcion', 
						                valueField:'codigo', 
						                //hiddenName: 'ds_Producto', 
						                typeAhead: true,
						                mode: 'local', 
						                triggerAction: 'all', 
						                labelAlign: 'top',
						                fieldLabel: getLabelFromMap('cmbProducto',helpMap,'Producto'),
					                    tooltip: getToolTipFromMap('cmbProducto',helpMap,'Producto'), 
						                width: 120, 
						                emptyText:'Seleccione ...',
						                selectOnFocus:true,
						               // disabled: true, 
						                forceSelection:true,
						                id: 'dsProducto',
						                name: 'dsProducto',
						                onSelect: function (record) {
						                	this.setValue(record.get('codigo'));
						                	this.collapse();
						                	storeComboPolizas.load({
						                		params: {
						                			cdUniEco: Ext.getCmp('ds_Aseguradora').getValue(),
						                			cdRamo: record.get('codigo')
						                		},
						                		callback: function(r, o, success) {
					                				el_formpanel.form.findField('nmPoliza').setValue('');
					                				el_formpanel.form.findField('nmPoliza').setRawValue('');
						                			if (!success) {
						                				storeComboPolizas.removeAll();
						                			}
						                		}
						                	});
						                }
						                //allowBlank : false,
						                //blankText: getMsgBlankTextFromMap('400013', helpMap, 'Este campo es requerido'),
						             }*/
						]
					},
					{
						layout: 'form',
						style: 'padding: 0px 0px 0px 5px',
						items: [
							{
							xtype: 'textfield', 
							//fieldLabel: 'P&oacute;liza', 
				            fieldLabel: getLabelFromMap('nmPoliex',helpMap,'P&oacute;liza'),
			                tooltip: getToolTipFromMap('nmPoliex',helpMap,'P&oacute;liza'),			          		
		                    hasHelpIcon:getHelpIconFromMap('nmPoliex',helpMap),
		 					Ayuda:getHelpTextFromMap('nmPoliex',helpMap),
		 					width: 110,
							//anchor: '95%', 
							id: 'nmPoliex', 
							name: 'nmPoliex', 
							disabled: true,
							value:NM_POLIEX
							},
							{
							xtype: 'hidden', 
							//fieldLabel: 'P&oacute;liza', 
							id: 'nmPoliza', 
							name: 'nmPoliza', 
							disabled: true,
							value:NM_POLIZA
							}
									/*{
										//labelSeparator: '',
					                    xtype: 'combo', 
					                    labelWidth: 70, 
					                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',					                    
						                store: storeComboPolizas,
						                displayField:'descripcion', 
						                valueField:'codigo', 
						                //hiddenName: 'ds_Producto', 
						                typeAhead: true,
						                mode: 'local', 
						                triggerAction: 'all', 
						                labelAlign: 'top',
						                fieldLabel: getLabelFromMap('cmbPoliza',helpMap,'P&oacute;liza'),
					                    tooltip: getToolTipFromMap('cmbPoliza',helpMap,'P&oacute;liza'), 
						                width: 150, 
						                emptyText:'Seleccione ...',
						                selectOnFocus:true, 
						               // disabled: true,
						                forceSelection:true,
						                id: 'nmPoliza',
						                name: 'nmPoliza'
						                //allowBlank : false,
						                //blankText: getMsgBlankTextFromMap('400013', helpMap, 'Este campo es requerido'),
						             }*/
						]
					},
					{
						layout: 'form',
						style: 'padding: 0px 0px 0px 5px',
						items: [
							{
							xtype: 'textfield', 
							//fieldLabel: 'Inciso', 
				            fieldLabel: getLabelFromMap('incisoExt',helpMap,'Inciso'),
			                tooltip: getToolTipFromMap('incisoExt',helpMap,'Inciso'),			          		
		                    hasHelpIcon:getHelpIconFromMap('incisoExt',helpMap),
		 					Ayuda:getHelpTextFromMap('incisoExt',helpMap),
		 					width: 110,
							//anchor: '100%', 
							id: 'incisoExt', 
							name: 'incisoExt', 
							disabled: true
							},
						    {xtype: 'hidden', name: 'nmInciso', id: 'nmInciso'}
						]
					},
					
					{
						layout: 'form',
						items: [
							{
							xtype: 'datefield', 
							//fieldLabel: 'Fecha Inicio', 
				            fieldLabel: getLabelFromMap('fechaInciso',helpMap,'Fecha Inicio'),
			                tooltip: getToolTipFromMap('fechaInciso',helpMap,'Fecha Inicio'),			          		
		                    hasHelpIcon:getHelpIconFromMap('fechaInciso',helpMap),
		 					Ayuda:getHelpTextFromMap('fechaInciso',helpMap),
		 					width: 110,
							//anchor: '95%', 
							//format: 'd/m/Y', 
							id: 'fechaInciso', 
							name: 'fechaIncisoNm', 
							disabled: true, 
							altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g"/*, readOnly: true*/
							}
						]
					},
					{
						layout: 'form',
						colspan: 2,
						style: 'padding: 0px 0px 0px 5px',
						items: [
							{
							xtype: 'datefield', 
							//fieldLabel: 'Fecha Vencimiento', 
				            fieldLabel: getLabelFromMap('fechaVencimiento',helpMap,'Fecha Vencimiento'),
			                tooltip: getToolTipFromMap('fechaVencimiento',helpMap,'Fecha Vencimiento'),			          		
		                    hasHelpIcon:getHelpIconFromMap('fechaVencimiento',helpMap),
		 					Ayuda:getHelpTextFromMap('fechaVencimiento',helpMap),
		 					width: 110,
							//format: 'd/m/Y', 
							id: 'fechaVencimiento', 
							name: 'fechaVencimientoNm', 
							disabled: true, 
							altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g"/*, readOnly: true*/
							}
						]
					},
					/*{
						colspan: 3,
						buttonAlign: 'center',
				            buttons: [
				            		{
				            			text: getLabelFromMap('BtnBuscar',helpMap,'Buscar'),
				                 		tooltip:getToolTipFromMap('BtnBuscar',helpMap,'Buscar'),
				                 		handler: function () {
												DS_ASEGURADO = ''; //el_formpanel.form.findField('dsAsegurado'),
												DS_ASEGURADORA = ''; //el_formpanel.form.findField('dsAseguradora'), 
												NM_POLIZA = el_formpanel.form.findField('nmPoliza').getValue(); 
												ESTADO = 'M'; 
												NM_SUPLEM = ''; 
												NM_SITUAC = '';
												_storePoliza.proxy = _proxyPoliza;

												_storePoliza.reload({
														params: {
															cdUniEco: Ext.getCmp('ds_Aseguradora').getValue(), //dsAsegurado: DS_ASEGURADO, //el_formpanel.form.findField('dsAsegurado'),
															cdRamo: Ext.getCmp('dsProducto').getValue(), //dsAseguradora: DS_ASEGURADORA, //el_formpanel.form.findField('dsAseguradora'), 
															nmPoliza: NM_POLIZA, //el_formpanel.form.findField('nmPoliza'), 
															estado: ESTADO, 
															nmSuplem: NM_SUPLEM, 
															nmSituac: NM_SITUAC
														},
														callback: function (r, o, success) {
															if (!success) {
																Ext.Msg.alert('Error', _storePoliza.reader.jsonData.actionErrors[0]);
													       		//Ext.getCmp('cdUniEco').setValue('');
													       		//Ext.getCmp('cdRamo').setValue('');
													       		Ext.getCmp('dsAsegurado').setValue('');
													       		//Ext.getCmp('dsAseguradora').setValue('');
													       		//Ext.getCmp('dsProducto').setValue('');
													       		Ext.getCmp('fechaInciso').setValue('');
													       		Ext.getCmp('fechaVencimiento').setValue('');
													       		Ext.getCmp('nmInciso').setValue('');
													       		//Ext.getCmp('nmPoliza').setValue('');
													       		Ext.getCmp('dsRazonCancelacion').setValue('');
													       		Ext.getCmp('fechaCancelacion').setValue('');
													       		Ext.getCmp('comentariosCancelacion').setValue('');
													       		Ext.getCmp('cdRazonCancelacion').setValue('');
													       		Ext.getCmp('cdPerson').setValue('');
													       		Ext.getCmp('cdMoneda').setValue('');
													       		Ext.getCmp('estado').setValue('');
													       		Ext.getCmp('nmSuplem').setValue('');
													       		Ext.getCmp('cdRazonCancelacion').setValue('');
													       		Ext.getCmp('nmCancel').setValue('');
													       		Ext.getCmp('dsRazonCancelacion').setValue('');
													       		Ext.getCmp('incisoExt').setValue('');
															}else {
																var datosForm = _storePoliza.reader.jsonData.listaPoliza[0];
													       		//Ext.getCmp('cdUniEco').setValue(datosForm.cdAseguradora);
													       		//Ext.getCmp('cdRamo').setValue(datosForm.cdProducto);
													       		Ext.getCmp('dsAsegurado').setValue(datosForm.dsAsegurado);
													       		//Ext.getCmp('dsAseguradora').setValue(datosForm.dsAseguradora);
													       		//Ext.getCmp('dsProducto').setValue(datosForm.dsProducto);
													       		Ext.getCmp('fechaInciso').setValue(datosForm.fechaInciso);
													       		Ext.getCmp('fechaVencimiento').setValue(datosForm.fechaVencimiento);
													       		Ext.getCmp('nmInciso').setValue(datosForm.nmInciso);
													       		Ext.getCmp('incisoExt').setValue(datosForm.incisoExt);
													       		//Ext.getCmp('nmPoliza').setValue(datosForm.nmPoliza);
													       		//Ext.getCmp('dsRazonCancelacion').setValue(datosForm.dsRazonCancelacion);
													       		Ext.getCmp('fechaCancelacion').setValue(datosForm.fechaCancelacion);
													       		Ext.getCmp('comentariosCancelacion').setValue(datosForm.comentariosCancelacion);
													       		Ext.getCmp('cdRazonCancelacion').setValue(datosForm.cdRazonCancelacion);
													       		Ext.getCmp('cdPerson').setValue(datosForm.cdPerson);
													       		Ext.getCmp('cdMoneda').setValue(datosForm.moneda);
													       		Ext.getCmp('estado').setValue(datosForm.estado);
													       		Ext.getCmp('nmSuplem').setValue(datosForm.nmSuplem);
													       		Ext.getCmp('cdRazonCancelacion').setValue(datosForm.cdRazonCancelacion);
													       		Ext.getCmp('nmCancel').setValue(datosForm.nmCancel);
													       		Ext.getCmp('dsRazonCancelacion').setValue(datosForm.dsRazonCancelacion);
													       		obtenerRequisitos();
															}
														}
												});
												//_storePoliza.proxy = _proxyPoliza;
												//el_formpanel.store = _storePoliza;
												//cargarDatos();
				                 				//calcularPrima();
				                 				//CODIGO_PRODUCTO = formPanel.form.findField('ds_Producto').getValue();
				                 				//CODIGO_ASEGURADORA = '';
				                 				//CODIGO_ESTADO = '';
				                 				//NUMERO_POLIZA = formPanel.form.findField('ds_Poliza').getValue();
				                 				//alert(CODIGO_PRODUCTO + '\n' + CODIGO_ASEGURADORA + '\n' + CODIGO_ESTADO + '\n' + NUMERO_POLIZA);
				                 				//cargarDatosForm();
				                 		}
				            		}
				            ]
					},*/
					{
						colspan: 3,
						html: '<span class="x-form-item" style="font-weight:bold">Datos de la Cancelaci&oacute;n</span>'
					},
					{
						layout: 'form',
						labelAlign: 'top',
						items: [
							{
							xtype: 'hidden', 
							id: 'cdRazonCancelacion', 
							name: 'cdRazonCancelacion'
							},
							{
							xtype: 'textfield', 
							//fieldLabel: 'Raz&oacute;n de Cancelaci&oacute;n', 
				            fieldLabel: getLabelFromMap('dsRazonCancelacion',helpMap,'Raz&oacute;n de Cancelaci&oacute;n'),
			                tooltip: getToolTipFromMap('dsRazonCancelacion',helpMap,'Raz&oacute;n de Cancelaci&oacute;n'),			          		
		                    hasHelpIcon:getHelpIconFromMap('dsRazonCancelacion',helpMap),
		 					Ayuda:getHelpTextFromMap('dsRazonCancelacion',helpMap),
		 					width: 110,
							id: 'dsRazonCancelacion', 
							name: 'dsRazonCancelacion', 
							//anchor: '100%', 
							//readOnly: true,
							disabled: true
							}
						]
					},
					{
						layout: 'form',
						style: 'padding: 0px 0px 0px 5px',
						colspan: 2,
						labelAlign: 'top',
						items: [
							{
							xtype: 'datefield', 
							//fieldLabel: 'Fecha de Cancelaci&oacute;n', 
				            fieldLabel: getLabelFromMap('fechaCancelacion',helpMap,'Fecha de Cancelaci&oacute;n'),
			                tooltip: getToolTipFromMap('fechaCancelacion',helpMap,'Fecha de Cancelaci&oacute;n'),			          		
		                    hasHelpIcon:getHelpIconFromMap('fechaCancelacion',helpMap),
		 					Ayuda:getHelpTextFromMap('fechaCancelacion',helpMap),
		 					width: 110,
							id: 'fechaCancelacion', 
							name: 'fechaCancelacionNm', 
							//format: 'd/m/Y', 
							altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g", 
							disabled: true}
						]
					},
					{
						layout: 'form',
						colspan: 3,
						labelAlign: 'top',
						items: [
							{
							xtype: 'textarea', 
							//fieldLabel: 'Comentarios', 
				            fieldLabel: getLabelFromMap('comentariosCancelacion',helpMap,'Comentarios'),
			                tooltip: getToolTipFromMap('comentariosCancelacion',helpMap,'Comentarios'),			          		
		                    hasHelpIcon:getHelpIconFromMap('comentariosCancelacion',helpMap),
		 					Ayuda:getHelpTextFromMap('comentariosCancelacion',helpMap),
		 					width: 650,
		 					height: 70,
							id: 'comentariosCancelacion', 
							name: 'comentariosCancelacion', 
							//anchor: '100%', 
							readOnly: true,
							disabled: true
							}
						]
					},
					{
						colspan: 3,
						html: '<span class="x-form-item" style="font-weight:bold">Datos de la Rehabilitaci&oacute;n</span>'
					},
					{
						layout: 'form',
						labelAlign: 'top',
						items: [
							{
							xtype: 'textfield', 
							//fieldLabel: 'N&uacute;mero de Rehabilitaci&oacute;n', 
				            fieldLabel: getLabelFromMap('nmCancel',helpMap,'N&uacute;mero de Rehabilitaci&oacute;n'),
			                tooltip: getToolTipFromMap('nmCancel',helpMap,'N&uacute;mero de Rehabilitaci&oacute;n'),			          		
		                    hasHelpIcon:getHelpIconFromMap('nmCancel',helpMap),
		 					Ayuda:getHelpTextFromMap('nmCancel',helpMap),
		 					width: 110,
							id: 'nmCancel', 
							name: 'nmCancel', 
							readOnly: true,
							disabled: true
							}
						]
					},
					{
						layout: 'form',
						labelAlign: 'top',
						colspan: 2,
						items: [
							{
							xtype: 'datefield', 
							//fieldLabel: 'Fecha de Rehabilitaci&oacute;n', 
				            fieldLabel: getLabelFromMap('feReInst',helpMap,'Fecha de Rehabilitaci&oacute;n'),
			                tooltip: getToolTipFromMap('feReInst',helpMap,'Fecha de Rehabilitaci&oacute;n'),			          		
		                    hasHelpIcon:getHelpIconFromMap('feReInst',helpMap),
		 					Ayuda:getHelpTextFromMap('feReInst',helpMap),
		 					width: 110,
							name: 'feReInst', 
							id: 'feReInst',
							//format: 'd/m/Y', 
							altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
							allowBlank:false,
							disabled: true
							}
						]
					},
					{
						layout: 'form',
						colspan: 2,
						labelAlign: 'top',
						items: [
							{
							xtype: 'textarea', 
							//fieldLabel: 'Comentarios', 
				            fieldLabel: getLabelFromMap('comentariosRehab',helpMap,'Comentarios'),
			                tooltip: getToolTipFromMap('comentariosRehab',helpMap,'Comentarios'),			          		
		                    hasHelpIcon:getHelpIconFromMap('comentariosRehab',helpMap),
		 					Ayuda:getHelpTextFromMap('comentariosRehab',helpMap),
		 					width: 380,
		 					height:106,
							id: 'comentariosRehab', 
							name: 'comentariosRehab' 
							//anchor: '94%'
							}
						]
					},
					{
						style: 'padding: 0px 0px 0px 5px',
						//layout: 'form',
						items: [
							grilla
						]
					}
            ],
       		buttonAlign: 'center',
       		labelAlign:'right',
       		buttons: [
       					{
       						text: getLabelFromMap('rhbltMnlBtnCnfrmr',helpMap,'Confirmar'),
						    tooltip:getToolTipFromMap('rhbltMnlBtnCnfrmr',helpMap,'Confirma Rehabilitacion Manual'), 
       						//text: 'Confirmar', 
       						handler: function () {
       							if (el_formpanel.form.isValid()) {
       								
       								confirmaPassword(rehabilitarPoliza);
       								//rehabilitarPoliza();
       								
                				} else{
                						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
								}
       								
       							/*var newRecord = new _record({
							    	dsRequisito: '',
							    	dsElemen: '',
							    	cdDocXCta: ''
       							});
       							grilla.stopEditing();
       							grilla.store.insert(0, newRecord);
       							grilla.startEditing(0, 0);*/
							}
						},
       					{
       						text: getLabelFromMap('rhbltMnlBtnRgrsr',helpMap,'Regresar'),
						    tooltip:getToolTipFromMap('rhbltMnlBtnRgrsr',helpMap,'Regresa a pantalla anterior'), 
       						//text: 'Regresar',
       					 handler: function() {
       					 				window.location.href = _ACTION_REGRESAR;
       					 		}
       					 }
			]
	});
			el_formpanel.render();
			//el_formpanel.findById('dsAsegurado').setValue(DES_ASEG);

	//Ext.getCmp('nmInciso').setValue(NM_SITUAC);
	
	
	storeAseguradoras.load({
		callback: function () {
				if (CDUNIECO!=""){
			                    el_formpanel.findById('dsAseguradora').setValue(DSUNIECO);
			                    storeProducto.load({
						                			params: {
						                				dsUniEco: DSUNIECO
						                			},
						                			callback: function () {
						                				el_formpanel.findById('cdRamo').setValue(CDRAMO);
						                				el_formpanel.findById('dsAsegurado').setValue(DES_ASEG);
						                				//alert(COD_RAZON);
						                				storeComboPolizas.load({
									                		params: {
									                			cdUniEco: CDUNIECO,
									                			cdRamo: CDRAMO
									                		},
									                		callback: function(r, o, success) {
									                		    
								                				el_formpanel.findById('nmPoliza').setValue(NM_POLIZA);
								                				el_formpanel.findById('incisoExt').setValue(NM_SITUAC);
								                				el_formpanel.findById('fechaCancelacion').setValue(FE_CANCEL);
								                				el_formpanel.findById('feReInst').setValue(FE_CANCEL);
								                				el_formpanel.findById('dsRazonCancelacion').setValue(DES_RAZON);
								                				el_formpanel.findById('fechaInciso').setValue(FE_EFECTO);
								                				el_formpanel.findById('fechaVencimiento').setValue(FE_VENCIM);
								                				el_formpanel.findById('nmsuplem').setValue(NM_SUPLEM);
								                				el_formpanel.findById('cdRazonCancelacion').setValue(COD_RAZON);
								                				el_formpanel.findById('estado').setValue(ESTADO);
								                				el_formpanel.findById('comentariosCancelacion').setValue(_COMENTARIO);
								                				
								                				el_formpanel.findById('nmCancel').setValue(NOM_CANCEL);
								                				el_formpanel.findById('cdPerson').setValue(COD_PERSON);
								                				el_formpanel.findById('cdMoneda').setValue(COD_MONEDA);
								                				
								                				//cargarDatos ();
								                				obtenerRequisitos();
									                		}
									                	});
						                			}
						                		});
			                   }       
			

			/*storeProducto.load({
				callback: function () {
						storeComboPolizas.load({
							params: {
								cdUniEco: 1,
								cdRamo: ''
							},
							callback: function () {
								if (DS_ASEGURADO != "" && DS_ASEGURADORA != "" && NM_POLIZA != "" && ESTADO != "" && NM_SUPLEM != "" && NM_SITUAC != "") { 
									cargarDatos();
								}
							}
						});
				}
		
			});*/
		}
	});
	    
/*	if (DES_ASEG != "" && DSUNIECO != "" && NM_POLIZA != "" && ESTADO != "" && NM_SITUAC != "" && NM_SUPLEM != "") { 
	                    alert('entro');
						cargarDatos();
						}*/

	//_storePoliza.load();
	function cargarDatos () {
	//alert(12);
			_storePoliza.load({
				url: _ACTION_BUSCAR_POLIZA,
				params: {
					cdUniEco:CDUNIECO,
					cdRamo: CDRAMO,
					estado: ESTADO, 
					nmPoliza: NM_POLIZA, //el_formpanel.form.findField('nmPoliza'), 
					nmSituac: NM_SITUAC
				},
				//proxy: new Ext.data.HttpProxy({url: _ACTION_BUSCAR_POLIZA}),
				successProperty: '@success',
				callback: function (r, o, success) {
				   // alert(success);
					if (success) {
						var datosForm = _storePoliza.reader.jsonData.listaPoliza[0];
			       		//Ext.getCmp('cdUniEco').setValue(datosForm.cdAseguradora);
			       		//Ext.getCmp('cdRamo').setValue(datosForm.cdProducto);
			       		//Ext.getCmp('dsAsegurado').setValue(datosForm.dsAsegurado);
			       		//Ext.getCmp('dsAseguradora').setValue(datosForm.dsAseguradora);
			       		//Ext.getCmp('dsProducto').setValue(datosForm.dsProducto);
			       		//Ext.getCmp('fechaInciso').setValue(datosForm.fechaInciso);
			       		//Ext.getCmp('fechaVencimiento').setValue(datosForm.fechaVencimiento);
			       		//Ext.getCmp('nmInciso').setValue(datosForm.nmInciso);
			       		//Ext.getCmp('nmPoliza').setValue(datosForm.nmPoliza);
			       		//Ext.getCmp('dsRazonCancelacion').setValue(datosForm.dsRazonCancelacion);
			       		//Ext.getCmp('fechaCancelacion').setValue(datosForm.fechaCancelacion);
			       		//Ext.getCmp('comentariosCancelacion').setValue(datosForm.comentariosCancelacion);
			       		//Ext.getCmp('cdRazonCancelacion').setValue(datosForm.cdRazonCancelacion);
			       		Ext.getCmp('cdPerson').setValue(datosForm.cdPerson);
			       		Ext.getCmp('cdMoneda').setValue(datosForm.moneda);
			       		//Ext.getCmp('estado').setValue(datosForm.estado);
			       		//Ext.getCmp('nmsuplem').setValue(datosForm.nmsuplem);
			       		//Ext.getCmp('cdRazonCancelacion').setValue(datosForm.cdRazonCancelacion);
			       		Ext.getCmp('nmCancel').setValue(datosForm.nmCancel);
			       		//Ext.getCmp('dsRazonCancelacion').setValue(datosForm.dsRazonCancelacion);
						obtenerRequisitos();						
					}else {
					    //alert('por el else');
						
						//Ext.Msg.alert('Error', _storePoliza.reader.jsonData.actionErrors[0]);
						Ext.Msg.alert(getLabelFromMap('400010', helpMap, 'Error'), _storePoliza.reader.jsonData.actionErrors[0]);
		
			       		//Ext.getCmp('cdUniEco').setValue('');
			       		//Ext.getCmp('cdRamo').setValue('');
			       		//Ext.getCmp('dsAsegurado').setValue('');
			       		//Ext.getCmp('dsAseguradora').setValue('');
			       		//Ext.getCmp('dsProducto').setValue('');
			       		//Ext.getCmp('fechaInciso').setValue('');
			       		//Ext.getCmp('fechaVencimiento').setValue('');
			       		//Ext.getCmp('nmInciso').setValue('');
			       		//Ext.getCmp('nmPoliza').setValue('');
			       		//Ext.getCmp('dsRazonCancelacion').setValue('');
			       		//Ext.getCmp('fechaCancelacion').setValue('');
			       		//Ext.getCmp('comentariosCancelacion').setValue('');
			       		//Ext.getCmp('cdRazonCancelacion').setValue('');
			       		Ext.getCmp('cdPerson').setValue('');
			       		Ext.getCmp('cdMoneda').setValue('');
			       		//Ext.getCmp('estado').setValue('');
			       		//Ext.getCmp('nmsuplem').setValue('');
			       		//Ext.getCmp('cdRazonCancelacion').setValue('');
			       		Ext.getCmp('nmCancel').setValue('');
			       		//Ext.getCmp('dsRazonCancelacion').setValue('');
					}
				}
			});
		
	}
	//_storePoliza.load();
	
	//alert('Dato: '+ el_formpanel.form.findField('nmCancel').getRawValue());
	
	function obtenerRequisitos () {
		var params = {
				cdUniEco: CDUNIAGE, //el_formpanel.form.findField('CDUNIECO').getValue(), //
				nmPoliza: NM_POLIZA, ///el_formpanel.form.findField('nmPoliza').getValue(), 
				estado: ESTADO, //'M', 
				nmSuplem: NM_SUPLEM, //Ext.getCmp('nmsuplem').getValue(), 
				nmSituac: NM_SITUAC,
				limit: itemsPerPage
			};
		reloadComponentStore (grilla, params, cbkObtenerRequisitos);
		return;
		_storeRequisitos.load({
			params: {
				cdUniEco: CDUNIAGE, //el_formpanel.form.findField('CDUNIECO').getValue(), //
				nmPoliza: NM_POLIZA, ///el_formpanel.form.findField('nmPoliza').getValue(), 
				estado: ESTADO, //'M', 
				nmSuplem: NM_SUPLEM, //Ext.getCmp('nmsuplem').getValue(), 
				nmSituac: NM_SITUAC
			},
			callback: function (r, opt, _success) {
				if (!_success) {
					//Ext.Msg.alert('Error', 'No se encontraron requisitos');
					grilla.store.removeAll();
				}
			}
		});
	}

	function cbkObtenerRequisitos (_r, _o, _success, _response) {
		if (!_success) {
			grilla.store.removeAll();
		}
	}
	function obtenerDatosCancelacion () {
		_storeDatosCancelacion.reload ({
			params: {
					dsAsegurado: DS_ASEGURADO, 
					dsAseguradora: DS_ASEGURADORA,  
					nmPoliza: NM_POLIZA,  
					estado: el_formpanel.form.findField('estado').getValue(), 
					nmsuplem: el_formpanel.form.findField('nmsuplem').getValue(), 
					nmSituac: NM_SITUAC
			},
			callback: function (r, o, success) {
				if (success) {
					el_formpanel.form.findField('fechaCancelacion').setValue(_storeDatosCancelacion.reader.jsonData.listaPoliza[0].fechaCancelacion);
					el_formpanel.form.findField('cdRazonCancelacion').setValue(_storeDatosCancelacion.reader.jsonData.listaPoliza[0].cdRazonCancelacion);
					el_formpanel.form.findField('dsRazonCancelacion').setValue(_storeDatosCancelacion.reader.jsonData.listaPoliza[0].dsRazonCancelacion);
					el_formpanel.form.findField('comentariosCancelacion').setValue(_storeDatosCancelacion.reader.jsonData.listaPoliza[0].comentariosCancelacion);
					obtenerRequisitos();
				}else {
					el_formpanel.form.findField('fechaCancelacion').setValue('');
					el_formpanel.form.findField('cdRazonCancelacion').setValue('');
					el_formpanel.form.findField('dsRazonCancelacion').setValue('');
					el_formpanel.form.findField('comentariosCancelacion').setValue('');
					grilla.store.removeAll();
				}
			}
		});
		
	}
	
	function rehabilitarPoliza () {
     
        var validaRequisito = false; 
        validaRequisito = validaRequesitos( _storeRequisitos );
        
		var _params = {
		    
//			cdUniEco: el_formpanel.form.findField('cdUnieco').getValue(), 
			cdUniEco: CDUNIECO,  
			
			cdRamo: el_formpanel.form.findField('cdRamo').getValue(), 
			estado: el_formpanel.form.findField('estado').getValue(), 
	      
			nmPoliza: el_formpanel.form.findField('nmPoliza').getValue(), 
			feEfecto: el_formpanel.form.findField('fechaInciso').getRawValue(), 
			feVencim: el_formpanel.form.findField('fechaVencimiento').getRawValue(), 
			feCancel: el_formpanel.form.findField('fechaCancelacion').getRawValue(), 
			feReInst: el_formpanel.form.findField('feReInst').getRawValue(), 
			cdRazon: el_formpanel.form.findField('cdRazonCancelacion').getRawValue(), 
			cdPerson: el_formpanel.form.findField('cdPerson').getRawValue(), 
			cdMoneda: el_formpanel.form.findField('cdMoneda').getValue(),
			nmCancel: el_formpanel.form.findField('nmCancel').getRawValue(), 
			comentarios: el_formpanel.form.findField('comentariosRehab').getValue(), 
			nmsuplem: el_formpanel.form.findField('nmsuplem').getValue()
		};
		/*************************/
		//alert('Dato: '+ el_formpanel.form.findField('nmCancel').getRawValue());
		/*************************/
		if(validaRequisito){
		   execConnection(_ACTION_REHABILITAR_POLIZA, _params, cbkRehabilitarPoliza);
		}else{
		   Ext.Msg.alert(getLabelFromMap('400000', helpMap, 'Aviso'),'Para poder rehabilitar la p&oacute;liza deben cumplirse todos los requisitos se&ntilde;alados');
		}
	}
	
	function cbkRehabilitarPoliza (_success, _message) {
		if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap, 'Error'), _message);
			   
			//, function(){validarRecibosPagados();}
		} else {
		   
			Ext.Msg.alert(getLabelFromMap('400000', helpMap, 'Aviso'), _message);
			window.location.href = _ACTION_REGRESAR;
			//, function(){validarRecibosPagados();}
		}
	}
	
	function validarRecibosPagados() {
		var _params = {
			cdUniEco: el_formpanel.form.findField('cdUnieco').getValue(), 
			cdRamo: el_formpanel.form.findField('cdRamo').getValue(), 
			nmPoliza: el_formpanel.form.findField('nmPoliza').getValue(), 
			estado: el_formpanel.form.findField('estado').getValue(),
			nmsuplem: el_formpanel.form.findField('nmsuplem').getValue(),
			feReHab: el_formpanel.form.findField('feReInst').getRawValue()
		};
		execConnection (_ACTION_VALIDAR_RECIBOS_PAGADOS, _params, cbkValidarRecibosPagados);
	}
	
	function cbkValidarRecibosPagados (_success, _message) {
		if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400108', helpMap, 'Validar Recibos'), _message);
		} else {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap, 'Aviso'), _message);
		}
	}
	function crearHLink (value, metadata, record, rowIndex, colIndex, store) {
			return '<a target="_blank" href="">Descargar</a>';
	}
	
	
	function validaRequesitos(elStore) {
	  var _seleccionados=0; 
	  var _count = 0;
	  var _result = false;
	  _count = elStore.getCount();
	   
	  for (var i=0; i<elStore.getCount(); i++) {
	     	if ((elStore.getAt(i).data.indEntrega)== true){
		      	++_seleccionados;
		    }
	  }
	  
	  if ( _seleccionados == _count ) {
	     _result = true;
	  }
	  
	  return _result; 
	}
	
});