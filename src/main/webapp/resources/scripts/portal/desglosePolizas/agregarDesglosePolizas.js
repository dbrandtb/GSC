// Funcion de Agregar Desglose de Polizas
function agregar() {

    var dsTipoCancela = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({
               url: _ACTION_COMBO_TIPO_CANCELACION
           }),
           reader: new Ext.data.JsonReader({
           root: 'clientesCorp',
           id: 'clientesCorps'
           },[
           {name: 'cdElemento', type: 'string',mapping:'cdElemento'},
           {name: 'cdPerson2', type: 'string',mapping:'cdPerson'}
       ])
       });

    var desProducto = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PRODUCTOS_AYUDA
            }),
            reader: new Ext.data.JsonReader({
            root: 'productosComboBox',
            id: 'productos'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ])
    });


    var desConceptoProducto = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_CONCEPTOS_PRODUCTO
            }),
            reader: new Ext.data.JsonReader({
            root: 'conceptosProducto',
            id: 'codigo'
            },[
           {name: 'codTipoCon', type: 'string',mapping:'codigo'},
           {name: 'desTipoCon', type: 'string',mapping:'descripcion'}
        ])
    });


//se define el formulario
var formPanel = new Ext.FormPanel ({
            labelWidth : 100,
            bodyStyle : 'padding:5px 5px 0',
            bodyStyle:'background: white',
            width : 350,
            heigth: 200,
            defaults : {width : 200 },
            defaultType : 'textfield',
            //se definen los campos del formulario
            items : [
                {
                    xtype: 'hidden',
                    name : 'cdElemento',
                    id: 'cdElemento'
                },

                {
                    xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson2}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    store: dsClientesCorp,
                    displayField:'dsElemen',
                    valueField: 'cdElemento',
                    hiddenName: 'cdPerson',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: "Cliente",
                    forceSelection: true,
                    width: 200,
                    emptyText:'Seleccione Cliente ...',
                    selectOnFocus:true,
                    labelSeparator:'',
                    id:'cdClienteId',
                    onSelect: function (record) {
                    	this.setValue(record.get("cdElemento"));
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
                    forceSelection: true,
                    width: 200,
                    emptyText:'Seleccione Producto...',
                    selectOnFocus:true,
                    labelSeparator:''
                } ,
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codTipoCon}. {desTipoCon}" class="x-combo-list-item">{desTipoCon}</div></tpl>',
                    store: desConceptoProducto,
                    displayField:'desTipoCon',
                    valueField:'codTipoCon',
                    hiddenName: 'cdTipCon',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: "Concepto",
                    forceSelection: true,
                    width: 200,
                    emptyText:'Seleccione Concepto...',
                    selectOnFocus:true,
                    labelSeparator:''
                }


            ]
        });


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        title: 'Agregar Desglose de Conceptos',
        width: 500,
        height:200,
        layout: 'fit',
        modal: true,
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
        //se definen los botones del formulario
            buttons : [ {
                text : 'Guardar',
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_AGREGAR_DESGLOSE_POLIZAS,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert('Aviso','Guardado satisfactoriamente');
                                window.close();
                                },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert('Error','Problemas al Guardar: ' + action.result.errorMessages[0]);
                                },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : 'Guardando Actualizaci&oacute;n de Datos ...'
                        });
                     } else {
                        Ext.Msg.alert('Informacion', 'Por favor complete la informaci&oacute;n requerida!');
                     }
                }
            },
             {
                 text : 'Cancelar',
                 handler : function() {
                 window.close();
                    }
            }]
    	});




    dsClientesCorp.load();
    desProducto.load();
    desConceptoProducto.load();



    window.show();

};

