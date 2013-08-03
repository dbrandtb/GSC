
// Funcion de Copiar Ayuda Cobertura
function copiar(key) {
//alert(key);
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

    var desProducto = new Ext.data.Store({
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
            id: 'langCode'							//codigo
            },[
           {name: 'codigoI', type: 'string', mapping:'langCode'},
           {name: 'descripcionI', type: 'string', mapping:'langName'}
        ])
    });
    



var _jsonFormReader = new Ext.data.JsonReader( {
        root : 'MEstructuraList',
        totalProperty: 'total',
        successProperty : 'success'
    }, [ 
    {
        name : 'cdUniecoC',
        mapping : 'cdUnieco',
        type : 'string'
    }
    , 
    {
        name : 'cdTipramC',
        mapping : 'cdTipram',
        type : 'string'
    }, 
   {
        name : 'cdSubramC',
        mapping : 'cdSubram',
        type : 'string'
    }
    , 
    {
        name : 'codigoProductoH',
        mapping : 'cdRamo',
        type : 'string'
    }, 
    {
        name : 'cdGarantH',
        mapping : 'cdGarant',
        type : 'string'
    },
	{
        name : 'langCodeC',
        mapping : 'langCode',
        type : 'string'
    },
    {
        name : 'dsUnieco',
        mapping : 'dsUnieco',
        type : 'string'
    }, 
    {
        name : 'dsTipram',
        mapping : 'dsTipram',
        type : 'string'
    }, 
    {
        name : 'dsSubram',
        mapping : 'dsSubram',
        type : 'string'
    }, 
    {
        name : 'dsRamo',
        mapping : 'dsRamo',
        type : 'string'
    }
    , 
    {
        name : 'dsGarant',
        mapping : 'dsGarant',
        type : 'string'
    }
   
    , 
    {
        name : 'langName',
        mapping : 'langName',
        type : 'string'
    }
    ]);


var formPanel = new Ext.FormPanel ({
            labelWidth : 10,
            bodyStyle:{position:'relative'},
            url : _ACTION_GET_AYUDA_COBERTURA,
            baseParams : {
                 cdGarantiaxCia: key
             },
            reader : _jsonFormReader,
            frame : true,
            bodyStyle : 'padding:5px 5px 0',
            bodyStyle:'background: white',
            width : 680,
            autoHeight: true,
            labelAlign:'right',
            waitMsgTarget : true,
            defaults: {labelWidth: 70},
            layout: 'column',
            layoutConfig: {columns: 2, align: 'left'},
            items : [
                new Ext.form.Hidden ({
            						name: 'codigoProductoH',
            						id: 'codigoProductoH'
            				}),
            	new Ext.form.Hidden ({
            						name: 'cdGarantIdh',     //H
            						id: 'cdGarantH'     	//H
            				}),
                 {
                   layout:'form',
                   //align: 'right',
                   bodyStyle:{position:'relative'},
                   columnWidth: .38,
                   items:[
                   {
                     xtype: 'textfield', 
                     fieldLabel: getLabelFromMap('cpyAlerTfAseg',helpMap,'Aseguradora'),
					 tooltip: getToolTipFromMap('cpyAlerTfAseg',helpMap,'Aseguradora'),
					 hasHelpIcon:getHelpIconFromMap('cpyAlerTfAseg',helpMap),
		             Ayuda: getHelpTextFromMap('cpyAlerTfAseg',helpMap,''),  
					 
                     name: 'dsUnieco', 
                     width: 130,      		
                     readOnly:true
                   }
                   ]
                 },
                 {
                   layout: 'form',
                   //align: 'right',
                   bodyStyle:{position:'relative'},
                   columnWidth: .38, 
                   items: [
                   {
                      xtype: 'combo',
                       tpl: '<tpl for="."><div ext:qtip="{cdUniEcoCombo}. {dsUniEcoCombo}" class="x-combo-list-item">{dsUniEcoCombo}</div></tpl>',
                       store: desAseguradora,
                       forceSelection:true,
                       displayField:'dsUniEcoCombo',
                       valueField:'cdUniEcoCombo',
                       hiddenName: 'cdUnieco',
                       typeAhead: true,
                       mode: 'local',
                       triggerAction: 'all',
                       fieldLabel: getLabelFromMap('cpyAlerCmbAseg',helpMap,'Aseguradora'),
					   tooltip: getToolTipFromMap('cpyAlerCmbAseg',helpMap,'Seleccione Aseguradora'),
					    hasHelpIcon:getHelpIconFromMap('cpyAlerCmbAseg',helpMap),
		                Ayuda: getHelpTextFromMap('cpyAlerCmbAseg',helpMap,''), 
                       width: 130,
                       emptyText:'Seleccione Aseguradora...',
                       selectOnFocus:true,
                       id: 'cdUniecoC',
	                    onSelect: function (record) {
	                       				this.setValue(record.get('cdUniEcoCombo'));
	                       				this.collapse();
	                       				desProducto.removeAll();
	                       				formPanel.findById('cdRamoId').setValue('');
	                       				desProducto.load({
	                       					params: {
	                       								cdunieco: record.get('cdUniEcoCombo'),
	                       								cdtipram: formPanel.findById('cdTipramC').getValue(),
	                       								cdsubram: formPanel.findById('cdSubramC').getValue()
	                       							}
	                       				});
	                     }
            		}
            		]
   			     },
                 {
                   layout:'form',
                   columnWidth: .38,
                   items:[
                   {
                     xtype: 'textfield',
                     fieldLabel: getLabelFromMap('cpyAlerTfRamo',helpMap,'Ramo'),
					 tooltip: getToolTipFromMap('cpyAlerTfRamo',helpMap,'Ramo'), 
					  hasHelpIcon:getHelpIconFromMap('cpyAlerTfRamo',helpMap),
		             Ayuda: getHelpTextFromMap('cpyAlerTfRamo',helpMap,''), 
                     name: 'dsTipram',
                     width: 130,      		
                     readOnly:true                     
                   }
                   ]
                 },
                 {
                   layout: 'form',
                   columnWidth: .38, 
                   items: [
                   {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: desRamo,
                    forceSelection:true,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'cdTipram',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cpyAlerCmbRamo',helpMap,'Ramo'),
				    tooltip: getToolTipFromMap('cpyAlerCmbRamo',helpMap,'Seleccione Ramo'),
				     hasHelpIcon:getHelpIconFromMap('cpyAlerCmbRamo',helpMap),
		             Ayuda: getHelpTextFromMap('cpyAlerCmbRamo',helpMap,''), 
                    width: 130,
                    emptyText:'Seleccione Ramo...',
                    selectOnFocus:true,
                    id: 'cdTipramC',
                    onSelect: function (record) {
                       				this.setValue(record.get('codigo'));
                       				this.collapse();
                       				desProducto.removeAll();
	                       				formPanel.findById('cdRamoId').setValue('');
                       				desProducto.load({
                       					params: {
                       								cdunieco: formPanel.findById('cdUniecoC').getValue(),
                       								cdtipram: record.get('codigo'),
                       								cdsubram: formPanel.findById('cdSubramC').getValue()
                       							}
                       				});
                     }
                   }
            		]
   			     },
                 {
                   layout:'form',
                   columnWidth: .38,
                   items:[
                   {
                     xtype: 'textfield',
                    fieldLabel: getLabelFromMap('cpyAlerTfSRamo',helpMap,'SubRamo'),
				    tooltip: getToolTipFromMap('cpyAlerTfSRamo',helpMap,'Subramo'),
				     hasHelpIcon:getHelpIconFromMap('cpyAlerTfSRamo',helpMap),
		             Ayuda: getHelpTextFromMap('cpyAlerTfSRamo',helpMap,''), 
                     name: 'dsSubram',
                     width: 130,      		
                     readOnly:true          
                   }
                   ]
                 },
                 {
                   layout: 'form',
                   columnWidth: .38, 
                   items: [
                   {
                       xtype: 'combo',
                       tpl: '<tpl for="."><div ext:qtip="{codigoSR}. {descripcionSR}" class="x-combo-list-item">{descripcionSR}</div></tpl>',
                       store: desSubramo,
                       forceSelection:true,
                       displayField:'descripcionSR',
                       valueField:'codigoSR',
                       hiddenName: 'cdSubram',
                       typeAhead: true,
                       mode: 'local',
                       triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cpyAlerCmbSRamo',helpMap,'SubRamo'),
				    tooltip: getToolTipFromMap('cpyAlerCmbSRamo',helpMap,'seleccione Subramo'),
				     hasHelpIcon:getHelpIconFromMap('cpyAlerCmbSRamo',helpMap),
		             Ayuda: getHelpTextFromMap('cpyAlerCmbSRamo',helpMap,''), 
                       width: 130,
                       emptyText:'Seleccione Subramo...',
                       selectOnFocus:true,
                       id: 'cdSubramC',
                       onSelect: function (record) {
                       				this.setValue(record.get('codigoSR'));
                       				this.collapse();
                       				desProducto.removeAll();
	                       				formPanel.findById('cdRamoId').setValue('');
                       				desProducto.load({
                       					params: {
                       								cdunieco: formPanel.findById('cdUniecoC').getValue(),
                       								cdtipram: formPanel.findById('cdTipramC').getValue(),
                       								cdsubram: record.get('codigoSR')
                       							}
                       				});
                       }
            		}
            		]
   			     },
                 {
                   layout:'form',
                   columnWidth: .38,
                   items:[
                   {
                     xtype: 'textfield',
                    fieldLabel: getLabelFromMap('cpyAlerTfProd',helpMap,'Producto'),
				    tooltip: getToolTipFromMap('cpyAlerTfProd',helpMap,'Producto'), 
				     hasHelpIcon:getHelpIconFromMap('cpyAlerTfProd',helpMap),
		             Ayuda: getHelpTextFromMap('cpyAlerTfProd',helpMap,''), 
                     name: 'dsRamo',
                     width: 130,      		
                     readOnly:true                     
                   }
                   ]
                 },
                 {
                   layout: 'form',
                   columnWidth: .38, 
                   items: [
                   {
                       xtype: 'combo',
                       tpl: '<tpl for="."><div ext:qtip="{codigoP}. {descripcionP}" class="x-combo-list-item">{descripcionP}</div></tpl>',
                       store: desProducto,
                       forceSelection:true,
                       displayField:'descripcionP',
                       valueField:'codigoP',
                       hiddenName: 'cdRamo',
                       typeAhead: true,
                       mode: 'local',
                       triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cpyAlerCmbProd',helpMap,'Producto'),
				    tooltip: getToolTipFromMap('cpyAlerCmbProd',helpMap,'Seleccione Producto'), 
				     hasHelpIcon:getHelpIconFromMap('cpyAlerCmbProd',helpMap),
		             Ayuda: getHelpTextFromMap('cpyAlerCmbProd',helpMap,''), 
                       //labelWidth: 70,
                       width: 130,
                       emptyText:'Seleccione Producto...',
                       selectOnFocus:true,
                       labelSeparator:'',
                       id: 'cdRamoId',
                       onSelect: function(record){
                       	                   desCobertura.removeAll();
	                       				   formPanel.findById('cdGarantId').setValue(''); 
                                           desCobertura.load({
                                          						 params: {
                                               								cdtipram:record.get("codigoP")
                                           						 },
                                           						 callback: function (r, optiones, success) {
                                           		                          // formPanel.findById('cdGarantId').setValue(formPanel.findById('cdGarantH').getValue());
										                         }
                                           });
                                           this.setValue(record.get("codigoP"));
                                           this.collapse();
                       }
                       
            		}
            		]
   			     },
   			     {
                   layout:'form',
                   columnWidth: .38,
                   items:[
                   {
                     xtype: 'textfield',
                    fieldLabel: getLabelFromMap('cpyAlerTfCob',helpMap,'Cobertura'),
				    tooltip: getToolTipFromMap('cpyAlerTfCob',helpMap,'Cobertura'), 
				     hasHelpIcon:getHelpIconFromMap('cpyAlerTfCob',helpMap),
		             Ayuda: getHelpTextFromMap('cpyAlerTfCob',helpMap,''), 
                     name: 'dsGarant', 
                     width: 130,      		
                     readOnly:true
                     
                   }
                   ]
                 },
                 {
                   layout: 'form',
                   columnWidth: .38, 
                   items: [
                   {
                      xtype: 'combo',
                       tpl: '<tpl for="."><div ext:qtip="{cdGarantCombo}. {dsGarantCombo}" class="x-combo-list-item">{dsGarantCombo}</div></tpl>',
                       store: desCobertura,
                       forceSelection:true,
                       displayField:'dsGarantCombo',
                       valueField:'cdGarantCombo',
                       hiddenName: 'cdGarant',
                       typeAhead: true,
                       //name:'cdGarant',
                       mode: 'local',
                       triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cpyAlerCmbCob',helpMap,'Cobertura'),
				    tooltip: getToolTipFromMap('cpyAlerCmbCob',helpMap,'Seleccione Cobertura'),
				     hasHelpIcon:getHelpIconFromMap('cpyAlerCmbCob',helpMap),
		             Ayuda: getHelpTextFromMap('cpyAlerCmbCob',helpMap,''), 
                       width: 130,
                       emptyText:'Seleccione Cobertura...',
                       selectOnFocus:true,
                       id: 'cdGarantId'				//cdGarantId    
            		}
            		]
   			     },
   			     {
                   layout:'form',
                   columnWidth: .38,
                   items:[
                   {
                     xtype: 'textfield',
                    fieldLabel: getLabelFromMap('cpyAlerTfIdio',helpMap,'Idioma'),
				    tooltip: getToolTipFromMap('cpyAlerTfIdio',helpMap,'Idioma'),
				     hasHelpIcon:getHelpIconFromMap('cpyAlerTfIdio',helpMap),
		             Ayuda: getHelpTextFromMap('cpyAlerTfIdio',helpMap,''),  
                     name: 'langName',
                     //id:'langName',
                     width: 130,       		
                     readOnly:true
                   }
                   ]
                 },
                 {
                   // este es obligatorio (que siempre lleve un VALOR)
                   layout: 'form',
                   columnWidth: .38, 
                   items: [
                   {
                       xtype: 'combo',
                       tpl: '<tpl for="."><div ext:qtip="{codigoI}. {descripcionI}" class="x-combo-list-item">{descripcionI}</div></tpl>',
                       store: desIdioma,
                       forceSelection:true,
                       displayField:'descripcionI',
                       //value: key.get('langCode'),
                       valueField:'codigoI',
                       hiddenName: 'langCode',
                       typeAhead: true,
                       mode: 'local',
                       triggerAction: 'all',
	                    fieldLabel: getLabelFromMap('cpyAlerCmbIdio',helpMap,'Idioma'),
					    tooltip: getToolTipFromMap('cpyAlerCmbIdio',helpMap,'Idioma'),
					     hasHelpIcon:getHelpIconFromMap('cpyAlerCmbIdio',helpMap),
			             Ayuda: getHelpTextFromMap('cpyAlerCmbIdio',helpMap,''), 
                       //labelWidth: 70,
                       width: 130,
                       emptyText:'Seleccione Idioma...',
                       selectOnFocus:true,
                       id:'langCodeC'
                       //id:'langNameC'
            		}
            		]
   			     }                                                    
            ]

});


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        	title: '<span style="color:black;font-size:14px;">'+getLabelFromMap('70', helpMap,'Copiar Ayuda de Coberturas')+'</span>',
        	//title: getLabelFromMap('70',helpMap,'Copiar Ayuda de Coberturas'),
            width: 500,
            autoHeight: true,
            buttonAlign:'center',
            modal:true,
        	items: formPanel,
            buttons : [ {
                text : getLabelFromMap('cpyAlerBtnCopy',helpMap,'Copiar'),
				tooltip:getToolTipFromMap('cpyAlerBtnCopy',helpMap,'Copia Ayuda Cobertura'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            url : _ACTION_COPIAR_AYUDA_COBERTURA,
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function() {reloadGrid(Ext.getCmp('grid2'));});
                                window.close();                                     
                            },
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                           },
                            waitTitle: getLabelFromMap('400021',helpMap,'Espere...'),
                            waitMsg : getLabelFromMap('400034',helpMap,'Copiando Datos ...')
                        });
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                    }
                }
            }, {
                text : getLabelFromMap('cpyAlerBtnCancel',helpMap,'Cancelar'),
				tooltip:getToolTipFromMap('cpyAlerBtnCancel',helpMap,'Regresa a la pantalla anterior'),
                handler : function() {
                window.close();
                }

            }]

});



desAseguradora.load({
	callback:function(){
		desRamo.load({
			callback:function(r, optiones, success){
				formPanel.findById('cdTipramC').setValue(formPanel.findById('cdTipramC').getValue())
					desSubramo.load({
						callback:function(r, optiones, success){
							formPanel.findById('cdSubramC').setValue(formPanel.findById('cdSubramC').getValue())
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
});


window.show();

   //se carga el formulario con los datos de la estructura a editar
   formPanel.form.load( {

               waitMsg : 'leyendo datos...',
               
               
               
             success: function () {
                    			
								desProducto.load({
										params: {
											cdunieco: formPanel.findById('cdUniecoC').getValue(),
											cdtipram: formPanel.findById('cdTipramC').getValue(),
											cdsubram: formPanel.findById('cdSubramC').getValue()
										},
										callback: function (r, optiones, success) {
												formPanel.findById('cdRamoId').setValue(formPanel.findById('codigoProductoH').getValue());
										}
								});
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

