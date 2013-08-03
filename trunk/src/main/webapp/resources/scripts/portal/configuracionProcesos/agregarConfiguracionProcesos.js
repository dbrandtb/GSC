
function agregar () {

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

	var form_add = new Ext.FormPanel ({
        labelWidth : 75,
        url : _ACTION_OBTENER_PROCESO,
        frame : true,
        bodyStyle : 'padding:5px 5px 0',
        width : 400,
        autoHeight: true,
        waitMsgTarget : true,
        layout: 'form',
        buttonAlign: "center",
        labelAlign: 'right',
        bodyStyle: 'background:white',
        //reader: seccion_reg, 
        items: [
                {
                xtype: 'combo', 
                name: 'cd_UniEco', 
                labelWidth: 50, 
                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	            store: aseguradoras_store, 
	            fieldLabel: getLabelFromMap('edtProcesoComboCdUniEco',helpMap,'Aseguradora'),
                tooltip: getToolTipFromMap('edtProcesoComboCdUniEco',helpMap),			          		
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
	            forceSelection:true,
	            onSelect: function (record) {
	            			this.setValue(record.get('codigo'));
	            			this.collapse();
	            			productos_store.reload({
	            				params: {cdUniEco: record.get('codigo')}
	            			});
	            }
	            //labelSeparator:''
	           	},
                {
                xtype: 'combo', 
                name: 'cd_Ramo', 
                labelWidth: 50, 
                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	            store: productos_store, 
	            fieldLabel: getLabelFromMap('edtProcesoComboCdRamo',helpMap,'Producto'),
                tooltip: getToolTipFromMap('edtProcesoComboCdRamo',helpMap, ''),			          		
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
                name: 'cd_Proceso', 
                labelWidth: 50, 
                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	            store: procesos_store, 
	            fieldLabel: getLabelFromMap('edtProcesoComboCdProceso',helpMap,'Proceso'),
                tooltip: getToolTipFromMap('edtProcesoComboCdProceso',helpMap),			          		
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
                name: 'cd_Elemento', 
                labelWidth: 50, 
                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	            store: clientes_store, 
	            fieldLabel: getLabelFromMap('edtProcesoComboCdElemento',helpMap,'Nivel'),
                tooltip: getToolTipFromMap('edtProcesoComboCdElemento',helpMap),			          		
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
                labelWidth: 50, 
                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	            store: indicador_store, 
	            fieldLabel: getLabelFromMap('edtProcesoComboCdIndicador',helpMap,'Indicador'),
                tooltip: getToolTipFromMap('edtProcesoComboCdIndicador',helpMap),			          		
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
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('95', helpMap,'Agregar Proceso')+'</span>',
			//title: '<span style="color:black;font-size:14px;">Editar Secci&oacute;n</span>',
			width: 400,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	modal: true,
        	items: form_add,
            //se definen los botones del formulario
            buttons : [ {
				text:getLabelFromMap('edtProcesoButtonGuardar', helpMap,'Guardar'),
				tooltip:getToolTipFromMap('edtProcesoButtonGuardar', helpMap,'Guarda el proceso'),
                disabled : false,
                handler : function() {
                    if (form_add.form.isValid()) {
                        form_add.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_PROCESO,
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
                       Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
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
	procesos_store.load({
		callback: function () {
			indicador_store.load({
					params: {cdTabla: 'CFPRONEG'},
					callback: function () {
							aseguradoras_store.load({
									callback: function () {
											clientes_store.load();
									}
							});
					}
			});
		}
	});
    win.show();
}