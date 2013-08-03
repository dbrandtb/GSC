// Funcion de Insertar Nueva Ayuda de Cobertura
//ventana emergente de insertar un nueva cobertura
function agregar() {

    var descAseguradora = new Ext.data.Store({
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

    var descRamo = new Ext.data.Store({
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

    var descSubramo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_SUBRAMOS
            }),
            reader: new Ext.data.JsonReader({
            root: 'subRamosComboBox',
            id: 'codigo'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ])
    });


    var descProductoC = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PRODUCTOS_AYUDA
            }),
            reader: new Ext.data.JsonReader({
            root: 'productosAseguradoraRamoSubRamo',
            id: 'codigo'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
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
            id: 'codigo'
            },[
           {name: 'codigo', type: 'string', mapping:'langCode'},
           {name: 'descripcion', type: 'string', mapping:'langName'}
        ])
    });

             
             
//se define el formulario
var formPanel = new Ext.FormPanel( {
            labelWidth : 100,
            url : _ACTION_INSERTAR_NUEVA_AYUDA_COBERTURA,
            frame : true,
            renderTo: Ext.get('formulario'),
            bodyStyle : 'padding:5px 5px 0',
            width : 600,
            bodyStyle:'background: white',
            waitMsgTarget : true,
            defaults : {width : 200},
            labelAlign:'right',
            defaultType : 'textfield',
            //se definen los campos del formulario
            items : [
               new Ext.form.ComboBox({
                                tpl: '<tpl for="."><div ext:qtip="{cdUniEcoCombo}. {dsUniEcoCombo}" class="x-combo-list-item">{dsUniEcoCombo}</div></tpl>',
                                store: descAseguradora,
                                forceSelection:true,
                                displayField:'dsUniEcoCombo',
                                valueField:'cdUniEcoCombo',
                                hiddenName: 'cdUnieco',
                                typeAhead: true,
                                mode: 'local',
                                triggerAction: 'all',
                                fieldLabel: getLabelFromMap('addAyCobCmbAseg',helpMap,'Aseguradora'),
                                tooltip: getToolTipFromMap('addAyCobCmbAseg',helpMap,'Seleccione Aseguradora '),
                                hasHelpIcon:getHelpIconFromMap('addAyCobCmbAseg',helpMap),
		                        Ayuda: getHelpTextFromMap('addAyCobCmbAseg',helpMap,''),
                                
                                width: 350,
                                emptyText:'Seleccione Aseguradora...',
                                selectOnFocus:true,
                                id:'cdUniecoIdIns',
                                allowBlank: false,
                                onSelect: function (record) {
                                   this.setValue(record.get("cdUniEcoCombo"));
                                   formPanel.findById("cdRamoIdIns").setValue('');
                                   descProductoC.removeAll();
                                   descProductoC.load({
                                           params: {
                                               cdunieco: record.get("cdUniEcoCombo"),
                                               cdtipram: formPanel.findById("cdTipramIdIns").getValue(),
                                               cdsubram: formPanel.findById("cdSubramIdIns").getValue()
                                           }
                                       }

                                   );
                                   this.collapse();
                                       }
                                }),

               new Ext.form.ComboBox({
                                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                                store: descRamo,
                                forceSelection:true,
                                displayField:'descripcion',
                                valueField:'codigo',
                                hiddenName: 'cdTipram',
                                typeAhead: true,
                                mode: 'local',
                                triggerAction: 'all',
                                fieldLabel: getLabelFromMap('addAyCobCmbRamo',helpMap,'Ramo'),
                                tooltip: getToolTipFromMap('addAyCobCmbRamo',helpMap,'Seleccione Ramo'),
                                hasHelpIcon:getHelpIconFromMap('addAyCobCmbRamo',helpMap),
		                        Ayuda: getHelpTextFromMap('addAyCobCmbRamo',helpMap,''),
                                width: 350,
                                emptyText:'Seleccione Ramo...',
                                selectOnFocus:true,
                                id:'cdTipramIdIns',
                                allowBlank: false,
                                onSelect: function (record) {
                                   this.setValue(record.get("codigo"));
                                   formPanel.findById("cdRamoIdIns").setValue('');
                                   descProductoC.removeAll();
                                   descProductoC.load({
                                           params: {
                                               cdunieco: formPanel.findById("cdUniecoIdIns").getValue(),
                                               cdtipram: record.get("codigo"),
                                               cdsubram: formPanel.findById("cdSubramIdIns").getValue()
                                           }
                                       }

                                   );
                                   this.collapse();
                                       }
                                }),

               new Ext.form.ComboBox({
                                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                                store: descSubramo,
                                forceSelection:true,
                                displayField:'descripcion',
                                valueField:'codigo',
                                hiddenName: 'cdSubram',
                                typeAhead: true,
                                mode: 'local',
                                triggerAction: 'all',
                                fieldLabel: getLabelFromMap('addAyCobCmbSRamo',helpMap,'SubRamo'),
                                tooltip: getToolTipFromMap('addAyCobCmbSRamo',helpMap,'Seleccione Subramo'),
                                   hasHelpIcon:getHelpIconFromMap('addAyCobCmbSRamo',helpMap),
		                        Ayuda: getHelpTextFromMap('addAyCobCmbSRamo',helpMap,''),
                                width: 350,
                                emptyText:'Seleccione Subramo...',
                                selectOnFocus:true,
                                id:'cdSubramIdIns',
                                onSelect: function (record) {
                                    this.setValue(record.get("codigo"));
                                    formPanel.findById("cdRamoIdIns").setValue('');
                                    descProductoC.removeAll();
                                    descProductoC.load({
                                            params: {
                                                cdunieco: formPanel.findById("cdUniecoIdIns").getValue(),
                                                cdtipram: formPanel.findById("cdTipramIdIns").getValue(),
                                                cdsubram: record.get("codigo")
                                            }
                                        }

                                    );
                                    this.collapse();
                                        }
                                }),

               new Ext.form.ComboBox({
                                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                                store: descProductoC,
                                forceSelection:true,
                                displayField:'descripcion',
                                valueField:'codigo',
                                hiddenName: 'cdRamo',
                                typeAhead: true,
                                mode: 'local',
                                triggerAction: 'all',
                                fieldLabel: getLabelFromMap('addAyCobCmbProd',helpMap,'Producto'),
                                tooltip: getToolTipFromMap('addAyCobCmbProd',helpMap,'Seleccione Producto'),
                                hasHelpIcon:getHelpIconFromMap('addAyCobCmbProd',helpMap),
		                        Ayuda: getHelpTextFromMap('addAyCobCmbProd',helpMap,''),
                                width: 350,
                                emptyText:'Seleccione Producto...',
                                selectOnFocus:true,
                                id:'cdRamoIdIns',
                                allowBlank: false,
                                onSelect: function (record) {
                                   this.setValue(record.get("codigo"));
                                   formPanel.findById("cdGarantIdIns").setValue('');
                                   desCobertura.removeAll();
                                   desCobertura.load({
                                           params: {
                                               cdtipram: record.get("codigo")
                                           }
                                       }
                                   );
                                   this.collapse();
                                       }
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
                                fieldLabel: getLabelFromMap('addAyCobCmbCob',helpMap,'Cobertura'),
                                tooltip: getToolTipFromMap('addAyCobCmbCob',helpMap,'Seleccione Cobertura'),
                                 hasHelpIcon:getHelpIconFromMap('addAyCobCmbCob',helpMap),
		                        Ayuda: getHelpTextFromMap('addAyCobCmbCob',helpMap,''),
                                width: 350,
                                emptyText:'Seleccione Cobertura...',
                                selectOnFocus:true,
                                id:'cdGarantIdIns',
                                allowBlank: false,
                                blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido')
                                }),

               new Ext.form.ComboBox({
                                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                                store: desIdioma,
                                forceSelection:true,
                                displayField:'descripcion',
                                valueField:'codigo',
                                hiddenName: 'langCode',
                                typeAhead: true,
                                mode: 'local',
                                triggerAction: 'all',
                                fieldLabel: getLabelFromMap('addAyCobCmbIdio',helpMap,'Idioma'),
                                tooltip: getToolTipFromMap('addAyCobCmbIdio',helpMap,'Seleccione Idioma'),
                                 hasHelpIcon:getHelpIconFromMap('addAyCobCmbIdio',helpMap),
		                        Ayuda: getHelpTextFromMap('addAyCobCmbIdio',helpMap,''),
                                width: 350,
                                emptyText:'Seleccione Idioma...',
                                allowBlank: false,
                                selectOnFocus:true
                                }),

                               /*  {
                xtype: 'htmleditor', 
                fieldLabel: getLabelFromMap('txtAreaDsFormatoEditar',helpMap,'Formato'), 
                 renderTo: Ext.getBody(),
               // tooltip: getToolTipFromMap('txtAreaDsFormatoEditar',helpMap,'Formato de orden de trabajo'),
                name: 'dsFormato'
            },*/
               new Ext.form.HtmlEditor({
                                fieldLabel: getLabelFromMap('addAyCobTxtTex',helpMap,'Texto'),
                                tooltip: getToolTipFromMap('addAyCobTxtTex',helpMap,'Texto Libre'),
                                 //hasHelpIcon:getHelpIconFromMap('addAyCobTxtTex',helpMap),
		                        //Ayuda: getHelpTextFromMap('addAyCobTxtTex',helpMap,'g'),
               	                name : 'dsAyuda',
               	                id: 'dsAyuda',
               	                //allowBlank : true,
               	                anchor: '100%',
               	                height:250,
               	                allowBlank: false
		           				//emptyText: 'Texto de Ayuda [opcional]...'
               	                })
            ]
          });

//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
 
        	title: '<span style="color:black;font-size:14px;">'+getLabelFromMap('windowId', helpMap,'Agregar Ayuda de Coberturas')+'</span>',
        	//title: getLabelFromMap('68',helpMap,'Agregar Ayuda de Coberturas'),
        	width: 650,
        	height:500,
        	layout: 'fit',
        	modal: true,
        	plain:true,
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	items: formPanel,
            buttons : [ {
                text : getLabelFromMap('addAyCobBtnSave',helpMap,'Guardar'),
                tooltip: getToolTipFromMap('addAyCobBtnSave',helpMap,'Guarda Ayuda Cobertura'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                    	if(formPanel.findById('dsAyuda').getValue()!= ""){
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_INSERTAR_NUEVA_AYUDA_COBERTURA,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert('Aviso', action.result.actionMessages[0], function(){reloadGrid(Ext.getCmp('grid2'));});
                                window.close();
                            },
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                            },

                            //mensaje a mostrar mientras se guardan los datos
                            waitTitle: getLabelFromMap('400021', helpMap,'Espere...'),
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos ...')
                        });
                    	}else{
                    	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400074', helpMap,'Ingrese un texto'));
						}                    
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                    }
                }
            },
            {
                text : getLabelFromMap('addAyCobBtnCancel',helpMap,'Cancelar'),
                tooltip: getToolTipFromMap('addAyCobBtnCancel',helpMap,'Regresa a la pantalla anterior'),
                handler : function() {
                    window.close();
                }
            }]
      });
 window.show();
 
  desIdioma.load({
   			callback:function(){
   				descAseguradora.load({
   					callback:function(){
   						descRamo.load({
   							callback:function(){
   								descSubramo.load();
   							}
   						});
   					}
   				});
   			}
   		});
};

//ventana emergente de insertar un nueva cobertura