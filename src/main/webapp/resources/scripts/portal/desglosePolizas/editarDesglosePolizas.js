// Funcion de Editar Desglose de Polizas
editar = function(record) {



//se define el formulario
var formPanel = new Ext.FormPanel ({
            labelWidth : 100,
            bodyStyle : 'padding:5px 5px 0',
            bodyStyle:'background: white',
            width : 350,
            defaults : {width : 200 },
            defaultType : 'textfield',
            //se definen los campos del formulario
            items : [
                {
                    xtype: 'textfield',
                     fieldLabel: "Cliente",
                    name : 'dsCliente',
                    readOnly:true,
                    id: 'dsCliente'
                },

                {
                    xtype: 'textfield',
                     fieldLabel: "Concepto",
                    name : 'dsTipCon',
                    readOnly:true,
                    id: 'dsTipCon'
                },
                
                 {
                    xtype: 'textfield',
                    fieldLabel: "Producto", 
                    name : 'dsRamo',
                    readOnly:true,
                    id: 'dsRamo'
                }
                /*{
                     xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson2}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    store: dsClientesCorp,
                    displayField:'dsElemen',
                    valueField: 'cdPerson2',
                    hiddenName: 'cdPerson',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: "Cliente",
                    width: 200,
                    emptyText:'Seleccionar Cliente ...',
                    selectOnFocus:true,
                    labelSeparator:'',
                    id:'cdClienteId',
                    onSelect: function (record) {
                    	this.setValue(record.get("cdPerson2"));
                        formPanel.findById(('cdElemento')).setValue(record.get("cdElemento"));
                        this.collapse();
                            }
                },

                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: desProducto,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'cdRamo',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: "Producto",
                    width: 300,
                    emptyText:'Seleccionar Producto...',
                    selectOnFocus:true,
                    labelSeparator:'',
                    id:'cdRamoId'
                },
            {
                xtype: 'combo',
                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                store: desProducto,
                displayField:'descripcion',
                valueField:'codigo',
                hiddenName: 'cdTipCon',
                typeAhead: true,
                mode: 'local',
                triggerAction: 'all',
                fieldLabel: "Concepto",
                width: 300,
                emptyText:'Seleccionar Concepto...',
                selectOnFocus:true,
                labelSeparator:'',
                id:'cdTipConId'
            }*/

            ]
        });



//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        title: 'Desglose de Polizas',
        width: 500,
        height:350,
        modal: true,
        minWidth: 300,
        minHeight: 100,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        bodyStyle:'background: white',
        buttonAlign:'center',
        items: formPanel,
        //se definen los botones del formulario
            buttons : [ {
                text : 'Guardar',
                disabled : true
                /*
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_EDITAR_DESGLOSE_POLIZAS,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert('Aviso','Guardado satisfactoriamente');
                                window.hide();                                     
                                },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert('Error','Problemas al Guardar: ' + action.result.errorMessages[0]);
                                },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : 'Guardando Actualizacion de Datos ...'
                        });
                     } else {
                        Ext.Msg.alert('Informacion', 'Por favor complete la informacion requerida!');
                     }
                }*/
            },
             {
                 text : 'Cancelar',
                 handler : function() {
                 window.close();
                    }
            }]
    	});

    formPanel.findById("dsCliente").setValue(record.get('dsCliente'));
    formPanel.findById("dsTipCon").setValue(record.get('dsTipCon'));
    formPanel.findById("dsRamo").setValue(record.get('dsRamo'));
    

    window.show();

};

