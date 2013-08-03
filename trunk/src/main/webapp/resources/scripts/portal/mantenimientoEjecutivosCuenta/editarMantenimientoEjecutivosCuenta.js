function editar (record) {
    var form_editar_reg = new Ext.data.JsonReader({
						root: 'listaEjecutivo',
						totalProperty: 'totalCount',
						successProperty: '@success'
						}, [
							{name: 'codEjecutivo', type: 'string', mapping: 'cdAgente'},
							{name: 'codEjecutivo2', type: 'string', mapping: 'cdAgente'},
							{name: 'codPersona', type: 'string', mapping: 'cdPerson'},
							{name: 'dsPersona', type: 'string', mapping: 'dsPerson'},
                            {name: 'feInicio', type: 'string', mapping: 'feInicio'},
                            {name: 'fechaFin', type: 'string', mapping: 'feFin'},
                            {name: 'estatus', type: 'string', mapping: 'cdEstado'}
						]
		);
		
	var persona_store  = new Ext.data.Store ({
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

	var status_store  = new Ext.data.Store ({
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

	var form_edit = new Ext.FormPanel ({
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
        reader: form_editar_reg, 
        items: [
						{
                        xtype: 'textfield', 
                    	fieldLabel: getLabelFromMap('cdEjecutivo', helpMap,'C&oacute;digo'), 
						tooltip: getToolTipFromMap('cdEjecutivo', helpMap,'C&oacute;digo'),
						blankText: getMsgBlankTextFromMap('cdEjecutivo', helpMap, 'Campo requerido'),
						allowBlank: false,
                        name: 'codEjecutivo2',
                        readOnly: true,
                        disabled: true
                        }, 
                        
                        {
                        xtype: 'hidden', 
                    	name: 'codEjecutivo'
                        
                        }, 
                        
                        {
                        xtype: 'hidden', 
                        name: 'codPersona'
                        }, {
                        xtype: 'textfield', 
                    	fieldLabel: getLabelFromMap('cdEjecutivo', helpMap,'Persona'), 
						tooltip: getToolTipFromMap('cdEjecutivo', helpMap,'Persona'),
						blankText: getMsgBlankTextFromMap('cdEjecutivo', helpMap, 'Campo requerido'),
						allowBlank: false,
                        name: 'dsPersona',
			            width: 200, 
                        readOnly: true,
                        disabled: true
                        }
			            /*{   xtype: 'combo', 
			                name: 'codPersona', 
			                labelWidth: 50, 
			                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
				            store: persona_store, 
				            fieldLabel: getLabelFromMap('cdPersona',helpMap,'Persona'),
			                tooltip: getToolTipFromMap('cdPersona',helpMap, 'Persona'),			          		
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
				            forceSelection:true,
							blankText: getMsgBlankTextFromMap('cdEjecutivo', helpMap, 'Campo requerido'),
				            listeners: {
				            	blur: function () {
				            		if (this.getRawValue() == "") {
				            			this.setValue("");
				            			this.setRawValue("");
				            		}
				            	}
				            }
			           	}*/,
			           	{
			           		xtype: 'datefield',
			           		id:'fechaIni',
							allowBlank: false,
							blankText: getMsgBlankTextFromMap('cdEjecutivo', helpMap, 'Campo requerido'),
			           		name: 'feInicio',
				            fieldLabel: getLabelFromMap('fechaInicial',helpMap,'Fecha Inicial'),
			                tooltip: getToolTipFromMap('fechaInicial',helpMap, 'Fecha Inicial'),
			                format: 'd/m/Y',
			                altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g"			          		
			           	},
			           	{
			           		xtype: 'datefield',
			           		id: 'fechaFin',
			           		name: 'fechaFin',
				            fieldLabel: getLabelFromMap('fechaFinal',helpMap,'Fecha Final'),
			                tooltip: getToolTipFromMap('fechaFinal',helpMap, 'Fecha Final'),
			                format: 'd/m/Y',
			                altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g"
			           	},
			            {   xtype: 'combo', 
			                name: 'estatus', 
			                labelWidth: 50, 
			                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
				            store: status_store, 
				            fieldLabel: getLabelFromMap('status',helpMap,'Estatus'),
			                tooltip: getToolTipFromMap('status',helpMap, 'Estatus'),			          		
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
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('windowEjctCuentasEdtId', helpMap,'Editar Ejecutivos por Cuenta')+'</span>',
			//title: '<span style="color:black;font-size:14px;">Editar Secci&oacute;n</span>',
			width: 400,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: form_edit,
        	modal: true,
            //se definen los botones del formulario
            buttons : [ {
				text:getLabelFromMap('edtSeccionButtonGuardar', helpMap,'Guardar'),
				tooltip:getToolTipFromMap('edtSeccionButtonGuardar', helpMap,'Guardar'),
                //text : 'Guardar',
                disabled : false,
                handler : function() {
                    if (form_edit.form.isValid()) {
                        form_edit.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_AGREGAR_GUARDAR_EJECUTIVO,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                win.close();
                            },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                           },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400022', helpMap,'Guardando Actualizaciones...')
                            //waitMsg : 'guardando datos ...'
                        });
                    } else {
                       Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                       //Ext.Msg.alert('Informacion', 'Por favor complete la informacion requerida!');
                    }
                }
            }, {
				text: getLabelFromMap('edtSeccionButtonCancelar', helpMap,'Cancelar'),
				tooltip:getToolTipFromMap('edtSeccionButtonCancelar', helpMap,'Cancelar'),
                //text : 'Cancelar',
                handler : function() {
                    win.close();
                }
            }]
	});
	if (record != null) {
		status_store.load({
			callback: function () {
					persona_store.load({
							callback: function () {
								        form_edit.form.load ({
								                params: {codEjecutivo: record.get('cdEjecutivo')},
								       			//waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
								                //waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
								                success: function() {
								                },
								                failure: function() {
								                	Ext.Msg.alert('', 'Error');
								                }
								        });
							}					
					});									
			}
		});
	}

    win.show();
}