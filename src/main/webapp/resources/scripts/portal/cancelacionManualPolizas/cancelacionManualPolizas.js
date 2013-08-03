Ext.onReady(function(){ 
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

		 var readerPoliza = new Ext.data.JsonReader({
            	root:'listaDatosPoliza',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
		        {name: 'ds_Aseguradora',  type: 'string',  mapping:'cdAseguradora'},
		        {name: 'ds_Asegurado',  type: 'string',  mapping:'dsAsegurado'},
		        {name: 'ds_Producto', type: 'string', mapping: 'cdProducto'},
		        {name: 'ds_Poliza', type: 'string', mapping: 'nmPoliza'},
		        {name: 'ds_FechaInciso', type: 'string', mapping: 'feEfecto'},
		        {name: 'ds_FechaVencimiento', type: 'string', mapping: 'fechaVencimiento'},
		        {name: 'cdUniAge', type: 'string', mapping: 'cdUniAge'}
			]);
         var storePoliza = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_OBTENER_ENCABEZADO
                }),

                reader: readerPoliza
        });

         var storeIncisos = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_OBTENER_INCISOS
                }),

                reader: new Ext.data.JsonReader({
            	root:'listaIncisos',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
		        {name: 'dsNombre',  type: 'string',  mapping:'dsNombre'},
		        {name: 'nmInciso',  type: 'string',  mapping:'nmInciso'},
		        {name: 'nmSubInciso', type: 'string', mapping: 'nmSubInciso'},
		        {name: 'nmSituac', type: 'string', mapping: 'nmSituac'}
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
            	root:'comboPolizasCancManualPolizas',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
		        {name: 'codigo',  type: 'string',  mapping:'nmPoliza'},
		        {name: 'descripcion',  type: 'string',  mapping:'nmPoliex'}
			])
        });

         var storeGrilla = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_OBTENER_INCISOS
                }),

                reader: new Ext.data.JsonReader({
            	root:'regionesComboBox',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
		        {name: 'codigo',  type: 'string',  mapping:'codigo'},
		        {name: 'descripcion',  type: 'string',  mapping:'descripcion'}
			])
        });

         /*
         var storeRazonCancelacion = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_COMBO_RAZON_CANCELACION
                }),

                reader: new Ext.data.JsonReader({
            	root:'comboRazones',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
		        {name: 'codigo',  type: 'string',  mapping:'cdRazon'},
		        {name: 'descripcion',  type: 'string',  mapping:'dsRazon'}
			])
        });
        */
        
           var storeRazonCancelacion = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_COMBO_RAZON_CANCELACION_PRODUCTO
                }),

                reader: new Ext.data.JsonReader({
            	root:'comboRazones'//,
            	//totalProperty: 'totalCount',
            	//successProperty : '@success'
	        },[
		        {name: 'codigo',  type: 'string',  mapping:'cdRazon'},
		        {name: 'descripcion',  type: 'string',  mapping:'dsRazon'}
			])
        });
        storeRazonCancelacion.load({
		  params:{cdRamo: CODIGO_PRODUCTO }
	    });
	    
       // alert('CODIGO_PRODUCTO=' + CODIGO_PRODUCTO );
        
        
        
    //TextField Aseguradora    
     var aseguradora = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('Id_Aseguradora',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('Id_Aseguradora',helpMap,'Aseguradora'), 
        id: 'Id_Aseguradora', 
        //id: 'aseguradoraId',
        name: 'aseguradora',
		disabled: true,
        anchor: '100%'
    });
     //TextFieldPoliza
     
        var poliza = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('Id_Poliza',helpMap,'P&oacute;liza'),
        tooltip:getToolTipFromMap('Id_Poliza',helpMap,'P&oacute;liza'), 
        id: 'Id_Poliza',
        //id: 'polizaId', 
        name: 'poliza',
        disabled: true,
        anchor: '100%'
    });


	var cm = new Ext.grid.ColumnModel ([
			{
		        header: getLabelFromMap('cmNombre',helpMap,'Nombre'),
		        tooltip: getToolTipFromMap('cmNombre',helpMap,'Nombre'),
		        dataIndex: 'dsNombre',
		        width: 80,
		        sortable: true
			},
			{
		        header: getLabelFromMap('cmInciso',helpMap,'Inciso'),
		        tooltip: getToolTipFromMap('cmInciso',helpMap,'Inciso'),
		        dataIndex: 'nmInciso',
		        width: 80,
		        sortable: true
			},
			{
		        header: getLabelFromMap('cmSubInciso',helpMap,'Subinciso'),
		        tooltip: getToolTipFromMap('cmSubInciso',helpMap,'Subinciso'),
		        dataIndex: 'nmSubInciso',
		        width: 80,
		        sortable: true
			},
			{
				header: '',
				dataIndex: 'nmSituac',
				hidden: true
			}
	]);

	var grid2= new Ext.grid.GridPanel({
        el:'gridElementos',
        id: 'grid2',
        store: storeIncisos,
        
		border:true,
		title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Cancelaci&oacute;n de Incisos</span>',
        cm: cm,
        loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},            	
    	width:270,
    	frame:true,
		height:140,
		sm: new Ext.grid.RowSelectionModel({multiSelect: true})/*,
		bbar: new Ext.PagingToolbar({
				pageSize:10,
				store: storeGrilla,
				displayInfo: true,
				displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
		    })*/
		});



var formPanel = new Ext.FormPanel ({
			renderTo: 'formBusqueda',
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('cancManual',helpMap,'Cancelaci&oacute;n Manual')+'</span>',
            labelAlign:'right',
            bodyStyle : {padding: '0px 5px 0px 0px', background: 'white'},
            width : 650,
            //height:350,
            frame: true,
            url: _ACTION_OBTENER_ENCABEZADO,
            //store: storePoliza,
            reader: readerPoliza,
            successProperty: '@success',
            items: [{
		            bodyStyle : {padding: '0px 5px 0px 0px', background: 'white'},
		            layout: 'table',
		            layoutConfig: {columns: 3, width: 750},
		            items : [
		            	{
		            		layout: 'form',
		            		//colspan: 1,
		            		//columnWidth: .7,
		            		items: [
		            				{
		            					xtype: 'hidden',
		            					name: 'cdUniAge'
		            				},
									{
					                    fieldLabel: getLabelFromMap('ds_AseguradoId',helpMap,'Asegurado'),
					                    tooltip:getToolTipFromMap('ds_AseguradoId',helpMap,'Asegurado'),
					                   // anchor: '50%',
					                    width: 180,
					                    xtype: 'textfield',
					                    name : 'ds_Asegurado',
					                    id : 'ds_AseguradoId',
					                    disabled: true
					                    //readOnly: true
					                }
		            		]
		            	},
		            	{
		            		layout: 'form',
		            		colspan: 2,
		            		items: [
		            		         aseguradora
									/*{
									
									
					                    fieldLabel: getLabelFromMap('txtAseguradora',helpMap,'Aseguradora'),
					                    tooltip:getToolTipFromMap('txtAseguradora',helpMap,''),
					                    xtype: 'textfield',
					                    name : 'ds_Aseguradora',
					                    readOnly: true
					                
					                }/*
					                
					                
									/*{/*
					                    xtype: 'combo', 
					                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
						                id: 'ds_Aseguradora',
						                fieldLabel: getLabelFromMap('cmbAseguradora',helpMap,'Aseguradora'),
					                    tooltip: getToolTipFromMap('cmbAseguradora',helpMap,'Aseguradora'),
					                    store: storeAseguradoras,
						                displayField:'descripcion', 
						                valueField:'codigo', 
						                //hiddenName: 'ds_Producto', 
						                typeAhead: true,
					                    labelWidth: 70, 
						                mode: 'local', 
						                triggerAction: 'all', 
						                labelAlign: 'top',
						                width: 180, 
						                emptyText:'Seleccione ...',
						                forceSelection:true,
						                selectOnFocus:true, 
						                name: 'ds_Aseguradora',
						                disabled: true,
						                onSelect: function (record) {
						                		this.setValue(record.get('codigo'));
						                		this.collapse();
						                		Ext.getCmp('ds_Producto').clearValue();
						                		Ext.getCmp('ds_Producto').setRawValue('');
						                		storeProducto.load({
						                			params: {
						                				cdUniEco: record.get('codigo')
						                			},
						                			callback: function () {
						                				if (storePoliza.reader.jsonData) {
						                					Ext.getCmp('ds_Producto').setValue(storePoliza.reader.jsonData.cdRamo);
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
		            		//colspan: 3,
		            		items: [
									{
					                    fieldLabel: getLabelFromMap('ds_ProductoId',helpMap,'Producto'),
					                    tooltip:getToolTipFromMap('ds_ProductoId',helpMap,'Producto'),
					                    xtype: 'textfield',
					                    disabled: true,
					                    name : 'ds_Producto',
					                    id: 'ds_ProductoId'
					                }
									/*{
										//labelSeparator: '',
					                    xtype: 'combo', 
					                    labelWidth: 30, 
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
						                width: 180, 
						                emptyText:'Seleccione ...',
						                selectOnFocus:true, 
						                forceSelection:true,
						                id: 'ds_Producto',
						                name: 'ds_Producto',
						                disabled: true,
						                onSelect: function (record) {
						                	this.setValue(record.get('codigo'));
						                	this.collapse();
						                	storeComboPolizas.load({
						                		params: {
						                			cdUniEco: Ext.getCmp('ds_Aseguradora').getValue(),
						                			cdRamo: record.get('codigo')
						                		},
						                		callback: function(r, o, success) {
						                			if (!success) {
						                				storeComboPolizas.removeAll();
						                				formPanel.form.findField('ds_Poliza').setValue('');
						                				formPanel.form.findField('ds_Poliza').setRawValue('');
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
		            		colspan: 2,
		            		items: [
		            		         poliza
		            		         
									/*{
					                    fieldLabel: getLabelFromMap('txtPoliza',helpMap,'P&oacute;liza'),
					                    tooltip:getToolTipFromMap('txtPoliza',helpMap,''),
					                    xtype: 'textfield',
					                    name : 'ds_Poliza'
					                }*/
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
						                width: 180, 
						                emptyText:'Seleccione ...',
						                selectOnFocus:true, 
						                forceSelection:true,
						                disabled: true,
						                id: 'ds_Poliza',
						                name: 'ds_Poliza'
						                //allowBlank : false,
						                //blankText: getMsgBlankTextFromMap('400013', helpMap, 'Este campo es requerido'),
						             }*/
						             
		            		]
		            	},
		            	{
		            		layout: 'form',
		            		//colspan: 3,
		            		items: [
									{
					                    fieldLabel: getLabelFromMap('ds_FechaIncisoId',helpMap,'Fecha Inicio'),
					                    tooltip:getToolTipFromMap('ds_FechaIncisoId',helpMap,'Fecha Inicio'),
					                    xtype: 'datefield',
					                    format: 'd/m/Y',
					                    name : 'ds_FechaInciso',
					                    id :  'ds_FechaIncisoId',
					                    altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
					                    disabled: true
					                }
		            		]
		            	},
		            	{
		            		layout: 'form',
		            		colspan: 2,
		            		items: [
									{
					                    fieldLabel: getLabelFromMap('ds_FechaVencimientoId',helpMap,'Fecha Vencimiento'),
					                    tooltip:getToolTipFromMap('ds_FechaVencimientoId',helpMap,'Fecha Vencimiento'),
					                    xtype: 'datefield',
					                    format: 'd/m/Y',
					                    name : 'ds_FechaVencimiento', 
					                    id : 'ds_FechaVencimientoId', 
					                    altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
					                    disabled: true
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
				                 				//calcularPrima();
				                 				CODIGO_PRODUCTO = formPanel.form.findField('ds_Producto').getValue();
				                 				CODIGO_ASEGURADORA = formPanel.form.findField('ds_Aseguradora').getValue();
				                 				CODIGO_ESTADO = '';
				                 				NUMERO_POLIZA = formPanel.form.findField('ds_Poliza').getValue();
				                 				//alert(CODIGO_PRODUCTO + '\n' + CODIGO_ASEGURADORA + '\n' + CODIGO_ESTADO + '\n' + NUMERO_POLIZA);
				                 				cargarDatosForm();
				                 		}
				            		}
				            ]
		            	},*/
		            	{
		            		layout: 'form',
		            		colspan: 3,
		            				html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Datos de la Cancelaci&oacute;n</span>'
		            	},
		
		            	{
		            		layout: 'form',
		            		width: 330, //180,
		            		//colspan: 1,
		            		labelAlign: 'top',
		            		items: [
									{
										//labelSeparator: '',
					                    xtype: 'combo', 
                                        autoHeight:true,					                    
					                    labelWidth: 70, 
					                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
						                store: storeRazonCancelacion,
						                displayField:'descripcion', 
						                valueField:'codigo', 
						                hiddenName: 'cd_Razon', 
						                typeAhead: true,
						                mode: 'local', 
						                triggerAction: 'all', 
						                labelAlign: 'top',
						                fieldLabel: getLabelFromMap('comboRazonCancelacion',helpMap,'Raz&oacute;n de Cancelaci&oacute;n'),
					                    tooltip:getToolTipFromMap('comboRazonCancelacion',helpMap,'Raz&oacute;n de Cancelaci&oacute;n'), 
						                width: 310, //180,
						                maxHeight: 140, 
						                emptyText:'Seleccione ...',
						                selectOnFocus:true, 
						                forceSelection:true,
						                allowBlank: false,
						                id: 'comboRazonCancelacion'
						                //allowBlank : false,
						                //blankText: getMsgBlankTextFromMap('400013', helpMap, 'Este campo es requerido'),
						             }
		            		]
		            	},
		            	{
		            		layout: 'form',
		            		width: 150,
		            		//colspan: 2,
		            		labelAlign: 'top',
		            		align: 'left',
		            		//columnWidth: .3,
		            		items: [
									{
										//labelSeparator: '',
										labelAlign: 'top',
										//labelWidth: 70,
					                    fieldLabel: getLabelFromMap('ds_FechaCancelacionId',helpMap,'Fecha Cancelaci&oacute;n'),
					                    tooltip:getToolTipFromMap('ds_FechaCancelacionId',helpMap,'Fecha Cancelaci&oacute;n'),
					                    xtype: 'datefield',
					                    format: 'd/m/Y',
					                    allowBlank: false,
					                    width: 120,
					                    altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
					                    id : 'ds_FechaCancelacionId',
					                    name : 'ds_FechaCancelacion'
					                }
		            		]
		            	},
		            	{
		            		layout: 'form',
		            		//colspan: 3,
		            		labelAlign: 'top',
		            		items: [
									{
										labelAlign: 'top',
										labelSeparator: '',
					                    fieldLabel: getLabelFromMap('ds_PrimaNoDevengadaId',helpMap,'Prima No Devengada'),
					                    tooltip:getToolTipFromMap('ds_PrimaNoDevengadaId',helpMap,'Prima No Devengada'),
					                    xtype: 'textfield',
					                    width: 140,
					                    readOnly: true,
					                    id: 'ds_PrimaNoDevengadaId',
					                    name : 'ds_PrimaNoDevengada'
					                }
		            		]
		            	},
		            	/*{
		            		layout: 'form',
		            		rowspan: 2,
		            		style: 'padding: 0px 0px 0px 5px',
		            		items: [
		            				grid2
		            		]
		            	},*/
		            	{
		            		layout: 'form',
		            		labelAlign: 'top',
		            		colspan: 3,
		            		//rowspan: 2,
		            		style: 'padding: 0px 0px 0px 5px',
		            		valign: 'top',
		            		items: [
									{
										labelSeparator: '',
										labelAlign: 'top',
					                    fieldLabel: getLabelFromMap('ds_ComentariosId',helpMap,'Comentarios'),
					                    tooltip:getToolTipFromMap('ds_ComentariosId',helpMap,'Comentarios'),
					                    xtype: 'textarea',
					                    anchor: '100%',
					                    id: 'ds_ComentariosId',
					                    name : 'ds_Comentarios'
					                }
		            		]
		            	}
		            ],
		            buttonAlign: 'center',
		            buttons: [
		            		{
		            			text: getLabelFromMap('BtnCalcular',helpMap,'Calcular prima'),
		                 		tooltip:getToolTipFromMap('BtnCalcular',helpMap,'Calcular Prima'),
		                 		handler: function () {
		                 			if (formPanel.form.isValid()) {
		                 				calcularPrima();
		                 			}else{
		                 				 Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
		                 			}
		                 				
		                 		}
		            		},
		            		{
		            			text: getLabelFromMap('BtnConfirmar',helpMap,'Confirmar'),
		                 		tooltip:getToolTipFromMap('BtnConfirmar',helpMap,'Confirmar'),
		                 		handler: function () {
		                 			confirmaPassword(guardarDatos);
		                 		}
		            		},
		            		{
		            			text: getLabelFromMap('BtnRegresar',helpMap,'Regresar'),
		                 		tooltip:getToolTipFromMap('BtnRegresar',helpMap,'Regresar'),
		                 		handler: function () {
		                 			window.location.href = _ACTION_REGRESAR_CONSULTA_POLIZAS;
		                 		}
		            		}
		            ]
            }]

        });






//reloadGrid(grid2);
	formPanel.render();
	storeAseguradoras.load({
			callback: function () {
					storeRazonCancelacion.load({
					       params: {
									cdRamo: CODIGO_PRODUCTO
								   }
					/*{
							callback: function () {
													if (CODIGO_ASEGURADORA != "" || CODIGO_PRODUCTO != "" || CODIGO_ESTADO != "" || NUMERO_POLIZA != "") {
														cargarDatosForm();
													}

							}
					}*/});
			}
	});

	Ext.getCmp('ds_AseguradoId').setValue(NOMBRE_ASEGURADO);
	Ext.getCmp('Id_Aseguradora').setValue(DESCRIPCION_ASEGURADORA);
	Ext.getCmp('ds_ProductoId').setValue(DESCRIPCION_PRODUCTO);
	Ext.getCmp('Id_Poliza').setValue(POLIZA_EXTERNA);
	Ext.getCmp('ds_FechaIncisoId').setValue(FECHA_EFECTO);
	Ext.getCmp('ds_FechaVencimientoId').setValue(FECHA_VENCIMIENTO);


	function cargarDatosForm() {
			startMask(formPanel.id, getLabelFromMap('400017', helpMap,'Espere por favor...'));
			formPanel.form.load({
				url: _ACTION_OBTENER_ENCABEZADO,
				params: {
						cdUniEco: CODIGO_ASEGURADORA,
						cdRamo: CODIGO_PRODUCTO,
						cdEstado: CODIGO_ESTADO,
						nmPoliza: NUMERO_POLIZA
				},
				success: function (form, action) {
						var cdUniEco = action.reader.jsonData.cdUniEco;
						var cdRamo = action.reader.jsonData.cdRamo;
						var nmPoliza = action.reader.jsonData.nmPoliza;
						storeProducto.load({
									params: {
										cdUniEco: cdUniEco
									},
									callback: function () {
										Ext.getCmp('ds_Producto').setValue(cdRamo);
										storeComboPolizas.load({
												params: {
													cdRamo: cdRamo,
													cdUniEco: cdUniEco //Según JIRA ACW-629
												},
												callback: function () {
													Ext.getCmp('ds_Poliza').setValue(nmPoliza);
												}
										});
									}
						});
						endMask();
						reloadGrid(grid2);
				},
				failure: function (form, action) {
							endMask();
							Ext.Msg.alert('Error', action.reader.jsonData.actionErrors[0]);
							limpiarCamposForm();
				}
			});
	}
	function limpiarCamposForm () {
	        //formPanel.form.findField('ds_Aseguradora').setValue('');
	        formPanel.form.findField('ds_Asegurado').setValue('');
	        formPanel.form.findField('ds_FechaInciso').setValue('');
	        formPanel.form.findField('ds_FechaVencimiento').setValue('');
	        formPanel.form.findField('cdUniAge').setValue('');
	        formPanel.form.findField('comboRazonCancelacion').setValue('');
	        formPanel.form.findField('ds_FechaCancelacion').setValue('');
	        formPanel.form.findField('ds_PrimaNoDevengada').setValue('');
	        formPanel.form.findField('ds_Comentarios').setValue('');
	        
	        grid2.store.removeAll();
	}
	function guardarDatos () {
		//TODO Implementar funcionalidad de acuerdo a los sp's
		var _params = "";
		var recs = grid2.getSelectionModel().getSelections();
		if (recs.length > 0) {
			for (var i=0; i<recs.length; i++) {
				_params += "listaDatosPoliza[" + i + "].cdAseguradora=" + CODIGO_ASEGURADORA + "&" + 
							"listaDatosPoliza[" + i + "].cdProducto=" + CODIGO_PRODUCTO + "&" +
							"listaDatosPoliza[" + i + "].cdUniAge=" + CODIGO_UNIAGE + "&" +
							"listaDatosPoliza[" + i + "].estado=M&" +
							"listaDatosPoliza[" + i + "].nmPoliza=" + NUMERO_POLIZA + "&" +
							"listaDatosPoliza[" + i + "].nmSituac=" + recs[i].get('nmSituac') + "&" +
							"listaDatosPoliza[" + i + "].cdRazonCancelacion=" + formPanel.form.findField('comboRazonCancelacion').getValue() + "&" +
							"listaDatosPoliza[" + i + "].comentariosCancelacion=" + formPanel.form.findField('ds_Comentarios').getValue() + "&" +
							"listaDatosPoliza[" + i + "].feEfecto=" + formPanel.form.findField('ds_FechaInciso').getRawValue() + "&" +
							"listaDatosPoliza[" + i + "].fechaVencimiento=" + formPanel.form.findField('ds_FechaVencimiento').getRawValue() + "&" +
							"listaDatosPoliza[" + i + "].fechaCancelacion=" + formPanel.form.findField('ds_FechaCancelacion').getRawValue() + "&";
			
			}
		} else {
				var i = 0;
				_params = "listaDatosPoliza[" + i + "].cdAseguradora=" + CODIGO_ASEGURADORA + "&" + 
							"listaDatosPoliza[" + i + "].cdProducto=" + CODIGO_PRODUCTO + "&" +
							"listaDatosPoliza[" + i + "].cdUniAge=" + CODIGO_UNIAGE + "&" +
							"listaDatosPoliza[" + i + "].estado=M&" +
							"listaDatosPoliza[" + i + "].nmPoliza=" + NUMERO_POLIZA + "&" +
							"listaDatosPoliza[" + i + "].nmSituac=&" +
							"listaDatosPoliza[" + i + "].cdRazonCancelacion=" + formPanel.form.findField('comboRazonCancelacion').getValue() + "&" +
							"listaDatosPoliza[" + i + "].comentariosCancelacion=" + formPanel.form.findField('ds_Comentarios').getValue() + "&" +
							"listaDatosPoliza[" + i + "].feEfecto=" + formPanel.form.findField('ds_FechaInciso').getRawValue() + "&" +
							"listaDatosPoliza[" + i + "].fechaVencimiento=" + formPanel.form.findField('ds_FechaVencimiento').getRawValue() + "&" +
							"listaDatosPoliza[" + i + "].fechaCancelacion=" + formPanel.form.findField('ds_FechaCancelacion').getRawValue() + "&";
		}
		startMask(formPanel.id, getLabelFromMap('400017', helpMap,'Espere por favor...'));
		execConnection (_ACTION_GUARDAR_DATOS, _params, cbkGuardar);
	}
	function cbkGuardar (_success, _message) {
		endMask();
	 	if (_success) {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message);
			window.location.href = _ACTION_REGRESAR_CONSULTA_POLIZAS;
	 	}else {
	 		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	 	}
	}

	function calcularPrima () {
	//alert(Ext.getCmp('comboRazonCancelacion').getValue())
		var _params = {
			cdUniEco: CODIGO_ASEGURADORA,
			cdRamo: CODIGO_PRODUCTO,
			nmPoliza: NUMERO_POLIZA,
			//feEfecto: formPanel.form.findField('ds_FechaInciso').getRawValue(),
			feEfecto: FECHA_EFECTO,
			feCancel: formPanel.form.findField('ds_FechaCancelacion').getRawValue(),
			feVencim: FECHA_VENCIMIENTO, //formPanel.form.findField('ds_FechaVencimiento').getRawValue(),
			cdRazon: formPanel.form.findField('comboRazonCancelacion').getValue()
		};
		startMask(formPanel.id, getLabelFromMap('400017', helpMap,'Espere por favor...'));
		execConnection (_ACTION_CALCULAR_PRIMA, _params, cbkCalcularPrima);
	}
	function cbkCalcularPrima (_success, _message) {
		endMask();
	 	if (_success) {
	 		formPanel.form.findField('ds_PrimaNoDevengada').setValue(_message);
	 	}else {
	 		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	 	}
	}
});

function reloadGrid(grid){
	var _params = {
			cdUniEco: CODIGO_ASEGURADORA,
			cdRamo: CODIGO_PRODUCTO,
			cdEstado: 'M',
			nmPoliza: NUMERO_POLIZA
	}

	reloadComponentStore(grid, _params, cbkReload);
}
function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}