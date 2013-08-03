// Funcion de Editar Ayuda Cobertura
//Ventana emergente de edicion
function editar(key) {

var desAseguradora = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_OBTENER_ASEGURADORAS
        }),
        reader: new Ext.data.JsonReader({
        root: 'aseguradoraComboBox',
        id: 'cdUniEco'
        },[
       {name: 'cdUniEcoCombo', type: 'string',mapping:'cdUniEco'},
       {name: 'dsUniEcoCombo', type: 'string',mapping:'dsUniEco'}
    ])
});

var desRamo = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_OBTENER_RAMOS
        }),
        reader: new Ext.data.JsonReader({
        root: 'ramosComboBox',
        id: 'codigo'
        },[
       {name: 'codigo', type: 'string',mapping:'codigo'},
       {name: 'descripcion', type: 'string',mapping:'descripcion'}
    ])
});

var desSubramo = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_OBTENER_SUBRAMOS
        }),
        reader: new Ext.data.JsonReader({
        root: 'subRamosComboBox',
        id: 'codigo'
        },[
       {name: 'codigoSR', type: 'string',mapping:'codigo'},
       {name: 'descripcionSR', type: 'string',mapping:'descripcion'}
    ])
});

var desProductoC = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_OBTENER_PRODUCTOS_AYUDA
        }),
        reader: new Ext.data.JsonReader({
        root: 'productosAseguradoraRamoSubRamo',
        id: 'codigo'
        },[
       {name: 'codigoP', type: 'string',mapping:'codigo'},
       {name: 'descripcionP', type: 'string',mapping:'descripcion'}
    ])
});

var desCobertura = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_OBTENER_TIPOS_COBERTURA
        }),
        reader: new Ext.data.JsonReader({
        root: 'tiposCoberturaComboBox',
        id: 'cdGarant'
        },[
       {name: 'cdGarantCombo', type: 'string',mapping:'cdGarant'},
       {name: 'dsGarantCombo', type: 'string',mapping:'dsGarant'}
    ])
});

var desIdioma = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_IDIOMAS_AYUDA}),
        reader: new Ext.data.JsonReader({
        root: 'idiomasComboBox',
        id: 'langCode'
        },[
       {name: 'codigoI', type: 'string', mapping:'langCode'},
       {name: 'descripcionI', type: 'string', mapping:'langName'}
    ])
});

var _jsonFormReader = new Ext.data.JsonReader( {
        root : 'MEstructuraList',
        totalProperty: 'total',
        successProperty : 'success'
    }, [ {
        name : 'cdUnieco',
        mapping : 'cdUnieco',
        type : 'string'
    },{
    	name : 'dsUnieco',
        mapping : 'dsUnieco',
        type : 'string'
    },{	
    	name : 'dsRamo',
        mapping : 'dsRamo',
        type : 'string'
    },{
        name : 'cdTipram',
        mapping : 'cdTipram',
        type : 'string'
    },{
    	name:'dsTipram',
    	mapping:'dsTipram',
    	type: 'string'
    },{
        name : 'cdSubramId',
        mapping : 'cdSubram',
        type : 'string'
    },{
        name : 'dsSubram',
        mapping : 'dsSubram',
        type : 'string'
    },{
        name : 'codigoProductoH',
        mapping : 'cdRamo',
        type : 'string'
    },{
        name : 'cdGarantH',
        mapping : 'cdGarant',
        type : 'string'
    },{
        name : 'langCodeC',
        mapping : 'langCode',
        type : 'string'
    },{
    	name: 'langName',
    	mapping: 'langName',
    	type: 'string'
    },{
        name : 'dsAyuda',
        mapping : 'dsAyuda',
        type : 'string'
    }
]);


var formPanel = new Ext.FormPanel ({
            labelWidth : 100,
            url : _ACTION_GET_AYUDA_COBERTURA,
            baseParams : {
                 cdGarantiaxCia: key
             },
            bodyStyle : 'padding:5px 5px 0',
            bodyStyle:'background: white',
            width : 550,
            reader : _jsonFormReader,
            defaults : {width : 200 },
            labelAlign:'right',
            defaultType : 'textfield',
            items : [
            				new Ext.form.Hidden ({
            						name: 'codigoProductoH',
            						id: 'codigoProductoH'
            				}),
            				new Ext.form.Hidden ({
            						name: 'cdGarantH',
            						id: 'cdGarantH'
            				}),
                              /* new Ext.form.ComboBox({
                                tpl: '<tpl for="."><div ext:qtip="{cdUniEcoCombo}.{dsUniEcoCombo}" class="x-combo-list-item">{dsUniEcoCombo}</div></tpl>',
                                store: desAseguradora,
                                forceSelection:true,
                                displayField:'dsUniEcoCombo',
                                valueField:'cdUniEcoCombo',
                                hiddenName: 'cdUnieco',
                                typeAhead: true,
                                mode: 'local',
                                triggerAction: 'all',
                                fieldLabel: getLabelFromMap('editAyCobCmbAseg',helpMap,'Aseguradora'),
                                tooltip: getToolTipFromMap('editAyCobCmbAseg',helpMap,'Aseguradora'),
                                width: 350,
                                emptyText:'Seleccione Aseguradora...',
                                selectOnFocus:true,
                                   id:'cdUniecoId',
                                   disabled: true,
                                   onSelect: function (record) {
                                      this.setValue(record.get('cdUniEcoCombo'));
                                      formPanel.findById('cdRamoId').setValue('');
                                      desProductoC.removeAll();
                                      formPanel.findById('cdGarantId').setValue('');
                                      desCobertura.removeAll();
                                      desProductoC.load({
                                              params: {
                                                  cdunieco: record.get('cdUniEcoCombo'),
                                                  cdtipram: formPanel.findById('cdTipramId').getValue(),
                                                  cdsubram: formPanel.findById('cdSubramId').getValue()
                                              }
                                          }

                                      );
                                      this.collapse();
                                          }
                                   }),*/
                new Ext.form.Hidden ({
            						name: 'cdUnieco',
            						id: 'cdUnieco'
            				        }),                  
                                  
               new Ext.form.TextField({
                                   id: 'dsUnieco', 
				   				   fieldLabel: getLabelFromMap('txtAseguradoraId',helpMap,'Aseguradora'),
                   				   tooltip: getToolTipFromMap('txtAseguradoraId',helpMap, 'Aseguradora'),    
                   				   hasHelpIcon:getHelpIconFromMap('txtAseguradoraId',helpMap),
		                           Ayuda: getHelpTextFromMap('txtAseguradoraId',helpMap,''),                
                                   name: 'dsUnieco',
                                   value:'dsUnieco',
                                   disabled: true
                                   }),

               /*new Ext.form.ComboBox({
                                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                                store: desRamo,
                                displayField:'descripcion',
                                forceSelection:true,
                                valueField:'codigo',
                                hiddenName: 'cdTipram',
                                typeAhead: true,
                                mode: 'local',
                                triggerAction: 'all',
                                fieldLabel: getLabelFromMap('editAyCobCmbRamo',helpMap,'Ramo'),
                                tooltip: getToolTipFromMap('editAyCobCmbRamo',helpMap,'Ramo'),
                                width: 350,
                                emptyText:'Seleccione Ramo...',
                                selectOnFocus:true,
                                id:'cdTipramId',
                                disabled: true,
                                onSelect: function (record) {
                                  this.setValue(record.get('codigo'));
                                  formPanel.findById('cdRamoId').setValue('');
                                  desProductoC.removeAll();
                                  desProductoC.load({
                                          params: {
                                              cdunieco: formPanel.findById('cdUnieco').getValue(),
                                              cdtipram: record.get('codigo'),
                                              cdsubram: formPanel.findById('cdSubramId').getValue()
                                          }
                                      }

                                  );
                                  this.collapse();
                                      }
                                }),*/
               new Ext.form.Hidden ({
            						name: 'cdTipram',
            						id: 'cdTipram'
            				        }),                  
                                  
               new Ext.form.TextField({
                                   id: 'dsTipram', 
				   				   fieldLabel: getLabelFromMap('txtFldRamoId',helpMap,'Ramo'),
                   				   tooltip: getToolTipFromMap('txtFldRamoId',helpMap, 'Ramo'), 
                   				   hasHelpIcon:getHelpIconFromMap('txtFldRamoId',helpMap),
		                           Ayuda: getHelpTextFromMap('txtFldRamoId',helpMap,''),                        
                                   name: 'dsTipram',
                                   value:'dsTipram',
                                   disabled: true
                                   }),

              /* new Ext.form.ComboBox({
                                tpl: '<tpl for="."><div ext:qtip="{codigoSR}. {descripcionSR}" class="x-combo-list-item">{descripcionSR}</div></tpl>',
                                store: desSubramo,
                                displayField:'descripcionSR',
                                forceSelection:true,
                                valueField:'codigoSR',
                                hiddenName: 'cdSubram',
                                typeAhead: true,
                                mode: 'local',
                                triggerAction: 'all',
                                fieldLabel: getLabelFromMap('editAyCobCmbSRamo',helpMap,'SubRamo'),
                                tooltip: getToolTipFromMap('editAyCobCmbSRamo',helpMap,'Subramo'),
                                width: 350,
                                emptyText:'Seleccione Subramo...',
                                selectOnFocus:true,
                                id:'cdSubramId',
                                disabled: true,
                                onSelect: function (record) {
                                   this.setValue(record.get('codigoSR'));
                                   formPanel.findById('cdRamoId').setValue('');
                                   desProductoC.removeAll();
                                   desProductoC.load({
                                           params: {
                                               cdunieco: formPanel.findById('cdUnieco').getValue(),
                                               cdtipram: formPanel.findById('cdTipramId').getValue(),
                                               cdsubram: record.get('codigoSR')								//ojo puede ser sin SR
                                           }
                                       }

                                   );
                                   this.collapse();
                                       }
                                }),*/
                                
               new Ext.form.Hidden ({
            						name: 'cdSubramId',
            						id: 'cdSubramId'
            				        }),                  
                                  
               new Ext.form.TextField({
                                   id: 'dsSubram', 
				   				   fieldLabel: getLabelFromMap('txtFldSubramoId',helpMap,'SubRamo'),
                   				   tooltip: getToolTipFromMap('txtFldSubramoId',helpMap, 'SubRamo'), 
                   				     hasHelpIcon:getHelpIconFromMap('txtFldSubramoId',helpMap),
		                           Ayuda: getHelpTextFromMap('txtFldSubramoId',helpMap,''),                    
                                   name: 'dsSubram',
                                   value:'dsSubram',
                                   disabled: true
                                   }),

               /*new Ext.form.ComboBox({
                                tpl: '<tpl for="."><div ext:qtip="{codigoP}. {descripcionP}" class="x-combo-list-item">{descripcionP}</div></tpl>',
                                store: desProductoC,
                                forceSelection:true,
                                displayField:'descripcionP',
                                valueField:'codigoP',
                                hiddenName: 'cdRamo',
                                typeAhead: true,
                                mode: 'local',
                                triggerAction: 'all',
                                fieldLabel: getLabelFromMap('editAyCobCmbProd',helpMap,'Producto'),
                                tooltip: getToolTipFromMap('editAyCobCmbProd',helpMap,'Producto'),
                                width: 350,
                                emptyText:'Seleccione Producto...',
                                selectOnFocus:true,
                                id:'cdRamoId',
                                disabled: true,
                                onSelect: function (record) {
                                   this.setValue(record.get('codigoP'));
                                   formPanel.findById('cdGarantId').setValue('');
                                   desCobertura.removeAll();
                                   desCobertura.load({
                                           params: {
                                               cdtipram: record.get('codigoP')
                                           }
                                       }

                                   );
                                   this.collapse();
                                       }
                               }),*/
              /* new Ext.form.Hidden ({
            						name: 'cdRamo',
            						id: 'cdRamo'
            				        }),  */                
                                  
               new Ext.form.TextField({
                                   id: 'dsRamo', 
				   				   fieldLabel: getLabelFromMap('txtFldDescrProductoId',helpMap,'Producto'),
                   				   tooltip: getToolTipFromMap('txtFldDescrProductoId',helpMap, 'Producto'),    
                   				     hasHelpIcon:getHelpIconFromMap('txtFldDescrProductoId',helpMap),
		                           Ayuda: getHelpTextFromMap('txtFldDescrProductoId',helpMap,''),                 
                                   name: 'dsRamo',
                                   value:'dsRamo',
                                   disabled: true
                                   }),

               new Ext.form.ComboBox({
                                tpl: '<tpl for="."><div ext:qtip="{cdGarantCombo}. {dsGarantCombo}" class="x-combo-list-item">{dsGarantCombo}</div></tpl>',
                                store: desCobertura,
                                forceSelection:true,
                                displayField:'dsGarantCombo',
                                valueField:'cdGarantCombo',
                                hiddenName: 'cdGarant',
                                typeAhead: true,
                                mode: 'local',
                                triggerAction: 'all',
                                fieldLabel: getLabelFromMap('editAyCobCmbCobertura',helpMap,'Cobertura'),
                                tooltip: getToolTipFromMap('editAyCobCmbCobertura',helpMap,'Seleccione Cobertura'),
                                  hasHelpIcon:getHelpIconFromMap('editAyCobCmbCobertura',helpMap),
		                           Ayuda: getHelpTextFromMap('editAyCobCmbCobertura',helpMap,''), 
                                width: 350,
                                emptyText:'Seleccione Cobertura...',
                                selectOnFocus:true,
                                id:'cdGarantId'
                                }),
                                
               /*new Ext.form.ComboBox({
                                tpl: '<tpl for="."><div ext:qtip="{codigoI}.{descripcionI}" class="x-combo-list-item">{descripcionI}</div></tpl>',
                                store: desIdioma,
                                forceSelection:true,
                                displayField:'descripcionI',
                                valueField:'codigoI',
                                hiddenName: 'langCode',
                                typeAhead: true,
                                mode: 'local',
                                triggerAction: 'all',
                                fieldLabel: getLabelFromMap('editAyCobCmbIdio',helpMap,'Idioma'),
                                tooltip: getToolTipFromMap('editAyCobCmbIdio',helpMap,'Idioma'),
                                width: 350,
                                emptyText:'Seleccione Idioma...',
                                selectOnFocus:true,
                                disabled: true,
                                id:'langCodeC'
                                }),*/
               new Ext.form.Hidden ({
            						name: 'langCodeC',
            						id: 'langCodeC'
            				        }),                  
                                  
               new Ext.form.TextField({
                                   id: 'langName', 
				   				   fieldLabel: getLabelFromMap('txtFldDescrIdiomaId',helpMap,'Idioma'),
                   				   tooltip: getToolTipFromMap('txtFldDescrIdiomaId',helpMap, 'Idioma'),   
                   				   hasHelpIcon:getHelpIconFromMap('txtFldDescrIdiomaId',helpMap),
		                           Ayuda: getHelpTextFromMap('txtFldDescrIdiomaId',helpMap,''),                  
                                   name: 'langName',
                                   value:'langName',
                                   disabled: true
                                   }),
               
               new Ext.form.HtmlEditor({
               	                fieldLabel : getLabelFromMap('editAyCobTxtText',helpMap,'Texto'),
               	                tooltip: getToolTipFromMap('editAyCobTxtText',helpMap,'Texto Libre'),
               	                name : 'dsAyuda',
               	                allowBlank : true, 
               	                anchor: '100%',
               	                height: 250,
               	                emptyText: 'Texto de Ayuda [opcional]...'               	
               	                })
            ]
        });






//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        title: '<span style="color:black;font-size:14px;">'+getLabelFromMap('windowId2', helpMap,'Editar Ayuda de Coberturas')+'</span>',
        //title: getLabelFromMap('69',helpMap,'Editar Ayuda de Coberturas'),
        width: 650,
        height:500,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
            buttons : [ {
                text : getLabelFromMap('editAyCobBtnSave',helpMap,'Guardar'),
                tooltip: getToolTipFromMap('editAyCobBtnSave',helpMap,'Guarda Ayuda Cobertura'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            url : _ACTION_GUARDAR_AYUDA_COBERTURA,
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function() {reloadGrid(Ext.getCmp('grid2'))});
                                window.close();                                     
                                },
                            failure : function(form, action) {
                                Ext.MessageBox.alert('Error', action.result.errorMessages[0]);
                                },
                            waitTitle: getLabelFromMap('400021',helpMap,'Espere...'),
                            waitMsg : getLabelFromMap('400022',helpMap,'Guardando Actualizacion de Datos...')
                        });
                     } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
                     }
                }
            },
             {
                 text : getLabelFromMap('editAyCobBtnCanc',helpMap,'Cancelar'),
                 tooltip: getToolTipFromMap('editAyCobBtnCanc',helpMap,'Regresa a la pantalla anterior'),
                 handler : function() {
                 window.close();
                    }
            }]
    	});

window.show();
/*
desAseguradora.load({
	callback:function(){
		desRamo.load({
			callback:function(r, optiones, success){
				formPanel.findById('codigoProductoH').setValue(formPanel.findById('codigoProductoH').getValue())
					desSubramo.load({
						callback:function(r, optiones, success){
							formPanel.findById('cdSubramId').setValue(formPanel.findById('cdSubramId').getValue())
							desIdioma.load({
								callback:function(r, optiones, success){
									formPanel.findById('langCodeC').setValue(formPanel.findById('langCodeC').getValue());
							}
						})
					}
				});
			}
		});
	}
});*/


formPanel.form.load( {
					waitTitle: getLabelFromMap('400021',helpMap,'Espere...'),
                    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos...'),
                    success: function () {
								/*desProductoC.load({
										params: {
											cdunieco: formPanel.findById('cdUnieco').getValue(),
											cdtipram: formPanel.findById('codigoProductoH').getValue(),
											cdsubram: formPanel.findById('cdSubramId').getValue()
										},
										callback: function (r, optiones, success) {
												formPanel.findById('codigoProductoH').setValue(formPanel.findById('codigoProductoH').getValue());
										}
								});*/
								desCobertura.load({
                                           params: {
                                               cdtipram:formPanel.findById('codigoProductoH').getValue()
                                           }
                                           ,
                                           callback: function (r, optiones, success) {
												formPanel.findById('cdGarantId').setValue(formPanel.findById('cdGarantH').getValue());
										   }
                                       });
                    }
});
};

// fin Ventana emergente de edicion
