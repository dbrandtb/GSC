// Funcion de Agregar Desglose de Polizas
function agregar() {

    var dsClientesCorporativo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_CLIENTES_CORPORATIVO
            }),
            reader: new Ext.data.JsonReader({
            root: 'clientesCorp'
            },[
           {name: 'cdElemento', type: 'string',mapping:'cdElemento'},
           {name: 'cdPerson', type: 'string',mapping:'cdPerson'},
           {name: 'dsElemen', type: 'string',mapping:'dsElemen'}
        ])
    });
    
    
    var dsAseguradora = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_ASEGURADORA_CLIENTES
            }),
            reader: new Ext.data.JsonReader({
            root: 'aseguradoraComboBox',
            id: 'cdUniEco'
            },[
           {name: 'cdUniEco', type: 'string',mapping:'cdUniEco'},
           {name: 'dsUniEco', type: 'string',mapping:'dsUniEco'}
        ])
    });
    
    
   var dsTipoRamo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_RAMOS_CLIENTE
            }),
            reader: new Ext.data.JsonReader({
            root: 'tiposRamo',
            id: 'codigo'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ])
    });


   var dsProductos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PRODUCTOS_TRAMO_CLIENTE
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboProductosTramoCliente',
            id: 'codigo'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ])
    });
    
     var dsTipoAgrupacion = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPO_AGRUPACION
            }),
            reader: new Ext.data.JsonReader({
            root: 'tiposAgrupacion',
            id: 'id'
            },[
           {name: 'id', type: 'string',mapping:'codigo'},
           {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
    
    var dsEstado = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_ESTADOS
            }),
            reader: new Ext.data.JsonReader({
            root: 'estadosAgrupacion',
            id: 'id'
            },[
           {name: 'id', type: 'string',mapping:'codigo'},
           {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
    
//se define el formulario
var formPanel = new Ext.FormPanel({
            labelWidth : 200,
            bodyStyle : 'padding:5px 5px 0',
            width : 350,
            //defaults : {width : 250 },
            defaultType : 'textfield',
            labelAlign:'right',
            labelWidth:120,
            plain: true,
            waitMsgTarget:true,
            align:'left',
            //se definen los campos del formulario
            items : [
                     new Ext.form.Hidden({
                    name : 'cdPerson',
                    id : 'cdPersonId'
                    }),
                    {
                	xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{cdPerson}.{cdElemento}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    store: dsClientesCorporativo,
                    displayField:'dsElemen',
                    valueField: 'cdElemento',
                    hiddenName: 'cdElemento',
                    typeAhead: true,
                    allowBlank:false,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cmbAddAgrPolCli',helpMap,'Cliente'),
                    tooltip: getToolTipFromMap('cmbAddAgrPolCli',helpMap,'Cliente'),
                    hasHelpIcon:getHelpIconFromMap('cmbAddAgrPolCli',helpMap),
					Ayuda: getHelpTextFromMap('cmbAddAgrPolCli',helpMap),
                    width: 200,
                    emptyText:'Seleccione Cliente ...',
                    selectOnFocus:true,
                    id:'cmbAddAgrPolCli',
			        forceSelection:true,
                    onSelect: function (record) {
                    
                                  
                                   formPanel.findById("cdPersonId").setValue(record.get("cdPerson"));
                                   formPanel.findById("cmbAddAgrPolAseg").setValue('');
                                   dsAseguradora.removeAll();
                                   dsAseguradora.load({
                                           params: {
                                                     cdElemento: record.get("cdElemento")
                                                   }
                                   });
                                   
                                   formPanel.findById("cmbAddAgrPolTipRam").setValue('');
                                   dsTipoRamo.removeAll();
                                   dsTipoRamo.load({
                                           params: {
                                                     cdElemento: record.get("cdElemento")
                                                   }
                                   });
                                   
                                   formPanel.findById("cmbAddAgrPolProd").setValue('');
                                   dsProductos.removeAll();
                                   dsProductos.load({
                                           params: {
                                                     cdElemento: record.get("cdElemento"),
                                                     cdtipram: formPanel.findById("cmbAddAgrPolTipRam").getValue()
                                                   }
                                   });
                                  
                                   this.setValue(record.get("cdElemento"));
                                   this.collapse();
                                  
                                 }
                    
                },{
                
                	xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{cdUniEco}.{dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
                    store: dsAseguradora,
                    displayField:'dsUniEco',
                    valueField: 'cdUniEco',
                    hiddenName: 'cdUnieco',
                    typeAhead: true,
                    mode: 'local',
                    allowBlank:false,
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cmbAddAgrPolAseg',helpMap,'Aseguradora'),
                    tooltip: getToolTipFromMap('cmbAddAgrPolAseg',helpMap,'Aseguradora'),
                    hasHelpIcon:getHelpIconFromMap('cmbAddAgrPolAseg',helpMap),
					Ayuda: getHelpTextFromMap('cmbAddAgrPolAseg',helpMap),
                    width: 200,
                    emptyText:'Seleccione Aseguradora ...',
                    selectOnFocus:true,
                    forceSelection:true,
                    onSelect: function (record) {
                               this.setValue(record.get("cdUniEco"));
                               formPanel.findById("cmbAddAgrPolProd").setValue('');
                               dsProductos.removeAll();
                               dsProductos.load({
                                       params: {
                                                 cdElemento: formPanel.findById("cmbAddAgrPolCli").getValue(),
                                                 cdtipram: formPanel.findById("cmbAddAgrPolAseg").getValue()
                                               }
                               });
                               this.collapse();
                             },

                    id:'cmbAddAgrPolAseg'
                    
                },{
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: dsTipoRamo,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'cdTipram',
                    typeAhead: true,
                    mode: 'local',
                    allowBlank:false,
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cmbAddAgrPolTipRam',helpMap,'Tipo de Ramo'),
                    tooltip: getToolTipFromMap('cmbAddAgrPolTipRam',helpMap,'Tipo de Ramo'),
                     hasHelpIcon:getHelpIconFromMap('cmbAddAgrPolTipRam',helpMap),
					Ayuda: getHelpTextFromMap('cmbAddAgrPolTipRam',helpMap),
                    width: 200,
                    emptyText:'Seleccione Tipo de Ramo...',
                    selectOnFocus:true,
                    id:'cmbAddAgrPolTipRam',
                    forceSelection:true
 /*                 ,  onSelect: function (record) {
                                   this.setValue(record.get("codigo"));
                                   formPanel.findById("cdRamoId").setValue('');
                                   dsProductos.removeAll();
                                   dsProductos.load({
                                           params: {
                                                     cdElemento: formPanel.findById("cdElementoId").getValue(),
                                                     cdtipram: formPanel.findById("cdTipRamId").getValue()
                                                   }
                                   });
                                   this.collapse();
                                 }*/
                    },
                    {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: dsProductos,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'cdRamo',
                    typeAhead: true,
                    allowBlank:false,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cmbAddAgrPolProd',helpMap,'Producto'),
                    tooltip: getToolTipFromMap('cmbAddAgrPolProd',helpMap,'Producto'),
                    hasHelpIcon:getHelpIconFromMap('cmbAddAgrPolProd',helpMap),
					Ayuda: getHelpTextFromMap('cmbAddAgrPolProd',helpMap),
                    width: 200,
                    emptyText:'Seleccione Producto ...',
                    selectOnFocus:true,
                    forceSelection:true,
                    id:'cmbAddAgrPolProd'
                },{
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsTipoAgrupacion,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'cdTipo',
                    typeAhead: true,
                    allowBlank:false,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cmbAddAgrTipAgrup',helpMap,'Tipo de Agrupaci&oacute;n'),
                    tooltip: getToolTipFromMap('cmbAddAgrTipAgrup',helpMap,'Tipo de Agrupaci&oacute;n'),
                    hasHelpIcon:getHelpIconFromMap('cmbAddAgrTipAgrup',helpMap),
					Ayuda: getHelpTextFromMap('cmbAddAgrTipAgrup',helpMap),
                    width: 200,
                    emptyText:'Seleccione Tipo de Agrupacion ...',
                    selectOnFocus:true,
                    forceSelection:true,
                    id:'cmbAddAgrTipAgrup'
                },{
                	xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsEstado,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'cdEstado',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cmbAddAgrPolEst',helpMap,'Estado'),
                    tooltip: getToolTipFromMap('cmbAddAgrPolEst',helpMap,'Estado'),
                    hasHelpIcon:getHelpIconFromMap('cmbAddAgrPolEst',helpMap),
					Ayuda: getHelpTextFromMap('cmbAddAgrPolEst',helpMap),
                    width: 200,
                    emptyText:'Seleccione Estado ...',
                    forceSelection:true,
                    selectOnFocus:true,
                    id:'cmbAddAgrPolEst'
                   
                  }            

            ]});


//Windows donde se van a visualizar la pantalla
var ventana = new Ext.Window({
        title: getLabelFromMap('85',helpMap,'Agrupaci&oacute;n por P&oacute;lizas'),
        width: 500,
        height:280,
        layout: 'fit',
        align:'left',
        plain:true,
        modal:true,
        //bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: [formPanel],
        //se definen los botones del formulario
            buttons : [ {
                text : getLabelFromMap('addAgrPolSave',helpMap,'Guardar'),
                tooltip: getToolTipFromMap('addAgrPolSave',helpMap,'Guardar'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            url : _ACTION_INSERTAR_AGRUPACION_POLIZAS,
                            success : function(from, action) {
                               Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),action.result.actionMessages[0], 
                               function(){reloadGrid(Ext.getCmp('grid2'));});
                               ventana.close();
                                },
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                                },
                          //  waitTitle: getLabelFromMap('400017', helpMap,'Guardando datos...'),
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                        });
                     } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                     }
                }
            },{
                 text : getLabelFromMap('addAgrPolBtnCancel',helpMap,'Cancelar'),
                 tooltip: getToolTipFromMap('addAgrPolBtnCancel',helpMap,'Cancelar'),
                 handler : function() {
                 ventana.close();
                    }
            }]
    	});

   dsTipoAgrupacion.load({
   		callback:function(){
   			dsEstado.load({
   				callback:function(){
   					dsClientesCorporativo.load();
   				}
   			});
   		}
   });   

   ventana.show();

};

