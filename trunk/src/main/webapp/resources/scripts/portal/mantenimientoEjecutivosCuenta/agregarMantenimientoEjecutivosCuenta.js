
function agregar() {

	var persona_store_add  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_PERSONAS}),
						reader: new Ext.data.JsonReader({
								root: 'confAlertasAutoClientes',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'cdPerson'},
								{name: 'descripcion', type: 'string', mapping: 'dsElemen'}
							]),
						remoteSort: true
				});

	var status_store_add  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_STATUS}),
						reader: new Ext.data.JsonReader({
								root: 'comboEstadosEjecutivoCuenta',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'}
							]),
						remoteSort: true
				});

	var form_add = new Ext.FormPanel ({
        labelWidth : 80,
        url : _ACTION_OBTENER_EJECUTIVO,
        frame : true,
        labelAlign: 'right',
        bodyStyle : 'padding:5px 5px 0',
        bodyStyle: 'background:white',
        width : 400,
        autoHeight: true,
        waitMsgTarget : true,
        layout: 'form',
        buttonAlign: "center",
        items: [
               /* {
                        xtype: 'textfield',
                    	fieldLabel: getLabelFromMap('cdEjecutivo', helpMap,'C&oacute;digo'), 
						tooltip: getToolTipFromMap('cdEjecutivo', helpMap,'C&oacute;digo'),  				                    	
						allowBlank: false,
						blankText: getMsgBlankTextFromMap('cdEjecutivo', helpMap, 'Campo requerido'),
                        //id: 'codEjecutivo', 
                        name: 'codEjecutivo'
                        },*/
						/*{
                        xtype: 'textfield', 
                        id: 'tstfldCdgId', 
                    	fieldLabel: getLabelFromMap('cdEjecutivo', helpMap,'C&oacute;digo'), 
						tooltip: getToolTipFromMap('cdEjecutivo', helpMap,'C&oacute;digo'),
						//blankText: getMsgBlankTextFromMap('cdEjecutivo', helpMap, 'Campo requerido'),
						//allowBlank: false,
                        name: 'codEjecutivo',
                        readOnly: true
                        }, */
			            {xtype: 'hidden',
			            id:'tstfldCdgId',
			            name:'codEjecutivo'
			            },
			            {   xtype: 'combo', 
			                //id: 'codPersona',
			                name: 'codPersona',
			                labelWidth: 50, 
			                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
				            store: persona_store_add, 
				            fieldLabel: getLabelFromMap('cdPersona',helpMap,'Persona'),
			                tooltip: getToolTipFromMap('cdPersona',helpMap,'Persona'),			          		
				            displayField:'descripcion', 
				            valueField:'codigo', 
				            hiddenName: 'codPersona', 
				            typeAhead: true,
				            mode: 'local', 
				            triggerAction: 'all', 
				            width: 200, 
				            emptyText:'Seleccionar Persona...',
				            selectOnFocus:true,
							allowBlank: false,
							blankText: getMsgBlankTextFromMap('cdEjecutivo', helpMap, 'Campo requerido'),
				            forceSelection:true,
				            listeners: {
				            	blur: function () {
				            		if (this.getRawValue() == "") {
				            			this.setValue("");
				            			this.setRawValue("");
				            		}
				            	}
				            }
			           	},
			           	{
			           		xtype: 'datefield',
							allowBlank: false,
			           		name: 'feInicio',
				            fieldLabel: getLabelFromMap('fechaInicial',helpMap,'Fecha Inicial'),
			                tooltip: getToolTipFromMap('fechaInicial',helpMap,'Fecha Inicial'),
			                altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
							blankText: getMsgBlankTextFromMap('cdEjecutivo', helpMap, 'Campo requerido'),
			                format: 'd/m/Y'			          		
			           	},
			           	{
			           		xtype: 'datefield',
			           		//id: 'fechaFin',
			           		name: 'fechaFin',
				            fieldLabel: getLabelFromMap('fechaFinal',helpMap,'Fecha Final'),
			                tooltip: getToolTipFromMap('fechaFinal',helpMap,'Fecha Final'),
			                altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
			                format: 'd/m/Y'
			           	},
			            {   xtype: 'combo', 
			                //id: 'estatus',
			                name: 'estatus',
			                labelWidth: 50, 
			                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
				            store: status_store_add, 
				            fieldLabel: getLabelFromMap('status',helpMap,'Estatus'),
			                tooltip: getToolTipFromMap('status',helpMap,'Estatus'),			          		
				            displayField:'descripcion', 
				            valueField:'codigo', 
				            hiddenName: 'estatus', 
				            typeAhead: true,
				            mode: 'local', 
				            triggerAction: 'all', 
				            width: 200, 
				            emptyText:'Seleccionar Estatus...',
				            selectOnFocus:true,
				            forceSelection:true,
				            listeners: {
				            	blur: function () {
				            		if (this.getRawValue() == "") {
				            			this.setValue("");
				            			this.setRawValue("");
				            		}
				            	}
				            }
			           	}
        	]
    });

	var win = new Ext.Window ({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('windowEjctCuentasEdtId', helpMap,'Agregar Ejecutivos por Cuenta')+'</span>',
			width: 400,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	modal: true,
        	items: [form_add],
            buttons : [ {
				text:getLabelFromMap('edtSeccionButtonGuardar', helpMap,'Guardar'),
				tooltip:getToolTipFromMap('edtSeccionButtonGuardar', helpMap,'Guardar'),
                //text : 'Guardar',
                disabled : false,
                handler : function() {
                    if (form_add.form.isValid()) {
                        form_add.form.submit( {
                            url : _ACTION_AGREGAR_GUARDAR_EJECUTIVO,
                            success : function(from, action) {
                                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), ' Registro Creado ', function(){reloadGrid();});
                                win.close();
                            },
                            failure : function(form, action) {
                                Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                           },
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                        });
                    } else {
                       Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                    }
                }
            }, {
				text:getLabelFromMap('edtSeccionButtonCancelar', helpMap,'Cancelar'),
				tooltip:getToolTipFromMap('edtSeccionButtonCancelar', helpMap,'Cancelar'),
                handler : function() {
                    win.close();
                }
            }]
	});

					Ext.getCmp('tstfldCdgId').setDisabled(false);
				    persona_store_add.load({
				    	callback: function () {
				    		status_store_add.load();
				    	}
				    });
    win.show();
}