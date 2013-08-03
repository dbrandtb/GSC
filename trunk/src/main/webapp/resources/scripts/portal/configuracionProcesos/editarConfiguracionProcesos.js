
function editar (record) {
	var procesos_store  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_PROCESOS}),
						reader: new Ext.data.JsonReader({
								root: 'comboGenerico',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'}
							]),
						remoteSort: true
				});

	var indicador_store  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_INDICADOR}),
						reader: new Ext.data.JsonReader({
								root: 'comboGenerico',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'}
							]),
						remoteSort: true
				});

	var clientes_store  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_CLIENTES}),
						reader: new Ext.data.JsonReader({
								root: 'clientesCorp',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'cdElemento'},
								{name: 'descripcion', type: 'string', mapping: 'dsElemen'}
							]),
						remoteSort: true
				});


	var aseguradoras_store  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_ASEGURADORAS}),
						reader: new Ext.data.JsonReader({
								root: 'aseguradoraComboBox',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'cdUniEco'},
								{name: 'descripcion', type: 'string', mapping: 'dsUniEco'}
							]),
						remoteSort: true
				});

	var productos_store  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_PRODUCTOS}),
						reader: new Ext.data.JsonReader({
								root: 'comboGenerico',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'}
							]),
						remoteSort: true
				});

	var proceso_reader = new Ext.data.JsonReader({
			root: 'listaProceso',
			successProperty: '@success',
			totalProperty: 'totalCount'
	}, [
			{name: 'cd_Elemento', type: 'string', mapping: 'cdElemento'},
			{name: 'cd_Proceso', type: 'string', mapping: 'cdProceso'},
			{name: 'cd_Ramo', type: 'string', mapping: 'cdRamo'},
			{name: 'cd_UniEco', type: 'string', mapping: 'cdUniEco'},
			{name: 'cd_Indicador', type: 'string', mapping: 'swEstado'}
	]);
	
	var proceso_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_PROCESO}),
						reader: proceso_reader
	});

	var form_edit = new Ext.FormPanel ({
        labelWidth : 75,
        url : _ACTION_OBTENER_PROCESO,
        frame : true,
        bodyStyle : 'padding:5px 5px 0',
        bodyStyle: 'background:white',
        width : 400,
        autoHeight: true,
        waitMsgTarget : true,
        layout: 'form',
        buttonAlign: "center",
        labelAlign: 'right',
        store: proceso_store,
        reader: proceso_reader,
        successProperty: 'success',
        items: [
                {
                xtype: 'combo', 
                //name: 'cd_UniEco', 
                id: 'cd_UniEco', 
                labelWidth: 50, 
                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	            store: aseguradoras_store, 
	            fieldLabel: getLabelFromMap('edtProcesoComboCdUniEco',helpMap,'Aseguradora'),
                tooltip: getToolTipFromMap('edtProcesoComboCdUniEco',helpMap,'Aseguradora'),			          		
	            displayField:'descripcion', 
	            valueField:'codigo', 
	            hiddenName: 'cdUniEco', 
	            typeAhead: true,
	            mode: 'local', 
	            triggerAction: 'all', 
	            //fieldLabel: "Bloque", 
	            width: 200,
	            allowBlank:false,
	            blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	            emptyText:'Seleccione Aseguradora...',
	            selectOnFocus:true,
	            forceSelection:true 
	            //labelSeparator:''
	           	},
                {
                xtype: 'combo', 
                //name: 'cd_Ramo', 
                id: 'cd_Ramo', 
                labelWidth: 50, 
                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	            store: productos_store, 
	            fieldLabel: getLabelFromMap('edtProcesoComboCdRamo',helpMap,'Producto'),
                tooltip: getToolTipFromMap('edtProcesoComboCdRamo',helpMap,'Producto'),			          		
	            displayField:'descripcion', 
	            valueField:'codigo', 
	            hiddenName: 'cdRamo', 
	            typeAhead: true,
	            mode: 'local', 
	            triggerAction: 'all', 
	            //fieldLabel: "Bloque", 
	            width: 200,
	            allowBlank:false,
	            blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	            emptyText:'Seleccione Producto...',
	            selectOnFocus:true,
	            forceSelection:true 
	            //labelSeparator:''
	           	},
                {
                xtype: 'combo', 
                //name: 'cd_Proceso', 
                id: 'cd_Proceso', 
                labelWidth: 50, 
                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	            store: procesos_store, 
	            fieldLabel: getLabelFromMap('edtProcesoComboCdProceso',helpMap,'Proceso'),
                tooltip: getToolTipFromMap('edtProcesoComboCdProceso',helpMap,'Proceso'),			          		
	            displayField:'descripcion', 
	            valueField:'codigo', 
	            hiddenName: 'cdProceso', 
	            typeAhead: true,
	            mode: 'local', 
	            triggerAction: 'all', 
	            //fieldLabel: "Bloque", 
	            width: 200,
	            allowBlank:false,
	            blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	            emptyText:'Seleccione Proceso...',
	            selectOnFocus:true,
	            forceSelection:true 
	            //labelSeparator:''
	           	},
                {
                xtype: 'combo', 
                //name: 'cd_Elemento', 
                id: 'cd_Elemento', 
                labelWidth: 50, 
                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	            store: clientes_store, 
	            fieldLabel: getLabelFromMap('edtProcesoComboCdElemento',helpMap,'Nivel'),
                tooltip: getToolTipFromMap('edtProcesoComboCdElemento',helpMap,'Nivel'),			          		
	            displayField:'descripcion', 
	            valueField:'codigo', 
	            hiddenName: 'cdElemento', 
	            typeAhead: true,
	            mode: 'local', 
	            triggerAction: 'all', 
	            //fieldLabel: "Bloque", 
	            width: 200,
	            allowBlank:false,
	            blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	            emptyText:'Seleccione Cuenta/Grupo...',
	            selectOnFocus:true,
	            forceSelection:true 
	            //labelSeparator:''
	           	},
                {
                xtype: 'combo', 
                name: 'cd_Indicador',
                id: 'cd_Indicador',
                labelWidth: 50, 
                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	            store: indicador_store, 
	            fieldLabel: getLabelFromMap('edtProcesoComboCdIndicador',helpMap,'Indicador'),
                tooltip: getToolTipFromMap('edtProcesoComboCdIndicador',helpMap,'Indicador'),			          		
	            displayField:'descripcion', 
	            valueField:'codigo', 
	            hiddenName: 'swEstado', 
	            typeAhead: true,
	            mode: 'local', 
	            triggerAction: 'all', 
	            //fieldLabel: "Bloque", 
	            width: 200,
	            allowBlank:false,
	            blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	            emptyText:'Seleccione Indicador...',
	            selectOnFocus:true,
	            forceSelection:true 
	            //labelSeparator:''
	           	}
        	]
    });


	var win = new Ext.Window ({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('95', helpMap,'Editar Proceso')+'</span>',
			width: 400,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	modal: true,
        	items: form_edit,
            //se definen los botones del formulario
            buttons : [ {
				text:getLabelFromMap('edtProcesoButtonGuardar', helpMap,'Guardar'),
				tooltip:getToolTipFromMap('edtProcesoButtonGuardar', helpMap,'Guarda el proceso'),
                disabled : false,
                handler : function() {
                    if (form_edit.form.isValid()) {
                        form_edit.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_ACTUALIZAR_PROCESO,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid(Ext.getCmp('grilla'));});
                                win.close();
                            },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                           },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                        });
                    } else {
                       Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                    }
                }
            }, {
				text:getLabelFromMap('edtProcesoButtonCancelar', helpMap,'Cancelar'),
				tooltip:getToolTipFromMap('edtProcesoButtonCancelar', helpMap, 'Cancela la operaci&oacute;n de guardar'),
                handler : function() {
                    win.close();
                }
            }]
	});
    win.show();
    /*aseguradoras_store.load({
    	callback: function () {
		    form_edit.load({
		    	params: {}
		    });
		}
    });*/
	procesos_store.load({
		callback: function () {
			indicador_store.load({
					params: {cdTabla: 'CFPRONEG'},
					callback: function () {
							aseguradoras_store.load({
									callback: function () {
											clientes_store.load({
												callback: function () {
													form_edit.load({
														params: {
															cdUniEco: record.get('cdUniEco'),
															cdRamo: record.get('cdRamo'),
															cdElemento: record.get('cdElemento'), 
															cdProceso: record.get('cdProceso')
														},
														success: function (form, action) {
															form_edit.form.findField('cd_Elemento').setValue(record.get('cdElemento'));

															productos_store.reload({
									            				params: {cdUniEco: action.reader.jsonData.cdUniEco},
									            				callback: function () {
									            						form_edit.form.findField('cd_Ramo').setValue(action.reader.jsonData.cdRamo);
									            						Ext.getCmp('cd_UniEco').setDisabled(true);
									            						Ext.getCmp('cd_Ramo').setDisabled(true);
									            						Ext.getCmp('cd_Proceso').setDisabled(true);
									            						Ext.getCmp('cd_Elemento').setDisabled(true);
									            				}
									            			});
														}
													});

												}
											});
									}
							});
					}
			});
		}
	});

}