function editar (record) {
    var seccion_reg = new Ext.data.JsonReader({
						root: 'listaSecciones',
						totalProperty: 'totalCount',
						successProperty: '@success'
						}, [
							{name: 'cdSeccion', type: 'string', mapping: 'cdSeccion'},
							{name: 'cdBloque', type: 'string', mapping: 'cdBloque'},
                            {name: 'dsSeccion', type: 'string', mapping: 'dsSeccion'},
                            {name: 'bloqueEditable', type: 'string', mapping: 'bloqueEditable'}
						]
		);
	var bloques_store  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_BLOQUES}),
						reader: new Ext.data.JsonReader({
								root: 'bloques',
								id: 'codigo',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'}
							]),
						remoteSort: true
				});

	var form_edit = new Ext.FormPanel ({
        labelWidth : 50,
        url : _ACTION_GET_SECCION,
        frame : true,
        bodyStyle : 'padding:5px 5px 0',
        width : 400,
        autoHeight: true,
        bodyStyle:'background: white',
        waitMsgTarget : true,
        layout: 'form',
        buttonAlign: "center",
        labelAlign: 'left',
        reader: seccion_reg, 
        items: [
                {
                xtype: 'hidden', 
                id: 'cdSeccion', 
                name: 'cdSeccion'
                },
                {
                xtype: 'hidden', 
                id: 'bloqueEditable', 
                name: 'bloqueEditable'
                },
                {
                xtype: 'textfield', 
               	fieldLabel: getLabelFromMap('dsSeccion', helpMap,'Secci&oacute;n'), 
				tooltip: getToolTipFromMap('dsSeccion', helpMap, 'Secci&oacute;n'), 	
                hasHelpIcon:getHelpIconFromMap('dsSeccion',helpMap),								 
                Ayuda: getHelpTextFromMap('dsSeccion',helpMap),
                name: 'dsSeccion',
                allowBlank:false,
                blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
                id: 'dsSeccion'
                },
                {
                xtype: 'combo', 
                id: 'idCdBloque', 
                labelWidth: 50, 
                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	            store: bloques_store, 
	            fieldLabel: getLabelFromMap('idCdBloque',helpMap,'Bloque'),
                tooltip: getToolTipFromMap('idCdBloque',helpMap,'Bloque'),	
                hasHelpIcon:getHelpIconFromMap('idCdBloque',helpMap),								 
                Ayuda: getHelpTextFromMap('idCdBloque',helpMap),
	            displayField:'descripcion', 
	            valueField:'codigo', 
	            hiddenName: 'cdBloque', 
	            typeAhead: true,
	            mode: 'local', 
	            triggerAction: 'all', 
	            //fieldLabel: "Bloque", 
	            width: 200,
	            allowBlank:false,
	            blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
	            emptyText:'Seleccione Bloque...',
	            selectOnFocus:true,
	            forceSelection:true 
	            //labelSeparator:''
	           	}
        	]
    });
	var titulo;
	titulo = '<span style="color:black;font-size:12px;">'+getLabelFromMap('EditaSec', helpMap,'Editar Secci&oacute;n')+'</span>';
	if(record == undefined ){
		titulo='<span style="color:black;font-size:12px;">'+getLabelFromMap('AgregSec', helpMap,'Agregar Secci&oacute;n')+'</span>';
	}
	var window = new Ext.Window ({
			title: titulo,
			//title: '<span style="color:black;font-size:14px;">Editar Secci&oacute;n</span>',
			width: 400,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	modal: true,
        	items: form_edit,
            //se definen los botones del formulario
            buttons : [ {
				text:getLabelFromMap('edtSeccionButtonGuardar', helpMap,'Guardar'),
				tooltip:getToolTipFromMap('edtSeccionButtonGuardar', helpMap,'Guarda la nueva secci&oacute;n creada'),
                //text : 'Guardar',
                disabled : false,
                handler : function() {
                    if (form_edit.form.isValid()) {
                        form_edit.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_SECCION,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                            },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                           },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400027',helpMap,'Guardando Datos...')
                            //waitMsg : 'guardando datos ...'
                        });
                    } else {
                       Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                       //Ext.Msg.alert('Informacion', 'Por favor complete la informacion requerida!');
                    }
                }
            }, {
				text:getLabelFromMap('edtSeccionButtonCancelar', helpMap,'Cancelar'),
				tooltip:getToolTipFromMap('edtSeccionButtonCancelar', helpMap, 'Cancela la operaci&oacute;n de guardar'),
                //text : 'Cancelar',
                handler : function() {
                    window.close();
                }
            }]
	});
	if (record != null && record.get('cdSeccion') != "") {
        form_edit.findById('cdSeccion').setValue(record.get('cdSeccion'));
        form_edit.form.load ({
                params: {cdSeccion: record.get('cdSeccion')},
                success: function() {
                    var bloqueEditable = form_edit.findById('bloqueEditable').getValue();
                    if (bloqueEditable == 'false') {
                        form_edit.findById('idCdBloque').setDisabled(true);
                    } else {
                        form_edit.findById('idCdBloque').setDisabled(false);
                    }
                }
        });
	}
    bloques_store.load();
    window.show();
}