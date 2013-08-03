// Funcion de Agregar Atributos Variables

function configurar() {

    var dsClientesCorp = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({
               url: _ACTION_OBTENER_CLIENTE_CORPO
           }),
           reader: new Ext.data.JsonReader({
           root: 'clientesCorp',
           id: 'clientesCorps'
           },[
           {name: 'cdElemento', type: 'string',mapping:'cdElemento'},
           {name: 'cdPerson', type: 'string',mapping:'cdPerson'},
           {name: 'dsElemen', type: 'string',mapping:'dsElemen'}
       ])
       });

	 var dsAseguradora = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({
							url: _ACTION_OBTENER_ASEGURADORAS_CLIENTE
						}),
						reader: new Ext.data.JsonReader({
								root: 'aseguradoraComboBox',
								id: 'cdUniEco',
								successProperty: '@success'
							}, [
								{name: 'cdUniEco', type: 'string', mapping: 'cdUniEco'},
								{name: 'dsUniEco', type: 'string', mapping: 'dsUniEco'} 
							])
				});

    var dsProductos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PRODUCTOS_ASEGURADORA_CLIENTE
            }),
            reader: new Ext.data.JsonReader({
            root: 'productosAseguradoraCliente',
            id: 'cdRamo'  //'codigo'
            },[
           {name: 'cdRamo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ])
    });
    
     var dsTipoSituacion = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_SITUACION_PRODUCTOS
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboTipoSituacionProducto'
          
            },[
           {name: 'cdTipSit', type: 'string',mapping:'cdTipSit'},
           {name: 'dsTipSit', type: 'string',mapping:'dsTipSit'}
        ])
    });

//**********  de la tabla de apoyo  CTABLACALCULO  **********//          
     var campoAtributo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_COMBO_ATRIBUTO
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboFormaAtributo'
          
            },[
           {name: 'cdTabla', type: 'string',mapping:'codigo'},
           {name: 'texto', type: 'string',mapping:'descripcionLarga'}
        ])
    });

//**********  de la tabla de apoyo  CFORMACALCULO  **********//    
     var campoCalculo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_COMBO_CALCULO
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboFormaCalculo'
          
            },[
           {name: 'swFormaCalculo', type: 'string',mapping:'codigo'},
           {name: 'texto', type: 'string',mapping:'descripcionLarga'}
        ])
    });


//**************************************************************************//    

        //se define el formulario
        var formPanel = new Ext.FormPanel( {

            labelWidth : 100,

            url : _ACTION_GUARDAR_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS,

            frame : true,

            renderTo: Ext.get('formulario'),

            bodyStyle : 'padding:5px 5px 0',
            
            bodyStyle:'background: white',

            width : 350,

            waitMsgTarget : true,
            
            labelAlign:'right',

            defaults : {

                width : 230

            },

            defaultType : 'textfield',

            //se definen los campos del formulario

           items : [
                {
                    xtype: 'hidden',
                    name : 'cdPerson',
                    id: 'cdPerson'
                },
                {
                    xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    store: dsClientesCorp,
                    displayField:'dsElemen',
                    valueField: 'cdElemento',
                    hiddenName: 'cdElemento',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
		            fieldLabel: getLabelFromMap('cdElementoId',helpMap,'Cliente'),
		            tooltip:getToolTipFromMap('cdElementoId',helpMap,'Elija Cliente'),
		            hasHelpIcon:getHelpIconFromMap('cdElementoId',helpMap),								 
    				Ayuda: getHelpTextFromMap('cdElementoId',helpMap),
                    width: 200,
                    emptyText:'Seleccione Cliente ...',
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    id:'cdElementoId',
                    onSelect: function (record) {
                    	
                        formPanel.findById(('cdPerson')).setValue(record.get("cdPerson"));
                        formPanel.findById(('cveProductoId')).setValue('');
                        dsProductos.removeAll();
	                    dsProductos.load({
	                                    	params: {cdElemento: record.get("cdElemento") ,cdunieco: formPanel.findById(('cveAseguradoraId')).getValue()},
	                         	            waitMsg: 'Espere por favor....'
	                            		 });
	                    formPanel.findById(('cveAseguradoraId')).setValue('');
                        dsAseguradora.removeAll();
                        dsAseguradora.load({
	                                    	params: {cdElemento: record.get("cdElemento")},
	                         	            waitMsg: 'Espere por favor....'
	                            		 });
                        this.collapse();
                        this.setValue(record.get("cdElemento"));
                        }
                },
                {
                    xtype: 'combo', 
                    labelWidth: 70, 
                    tpl: '<tpl for="."><div ext:qtip="{cdUniEco}. {dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
	                store: dsAseguradora,
	                displayField:'dsUniEco', 
	                valueField:'cdUniEco', 
	                hiddenName: 'cdUnieco', 
	                typeAhead: true,
	                mode: 'local', 
	                triggerAction: 'all', 
		            fieldLabel: getLabelFromMap('cveAseguradoraId',helpMap,'Aseguradora'),
		            tooltip:getToolTipFromMap('cveAseguradoraId',helpMap,'Elija aseguradora '),
		            hasHelpIcon:getHelpIconFromMap('cveAseguradoraId',helpMap),								 
    				Ayuda: getHelpTextFromMap('cveAseguradoraId',helpMap),
	                width: 200, 
	                emptyText:'Seleccione Aseguradora...',
	                selectOnFocus:true, 
	                forceSelection:true,
	                allowBlank : false,
	                id: 'cveAseguradoraId',
	                onSelect: function (record) {
	                            				
	                            				dsProductos.removeAll();
	                            				dsProductos.load({
	                            					params: {cdElemento: formPanel.findById(('cdElementoId')).getValue() ,cdunieco: record.get('cdUniEco')}
	                            					});
	                            				formPanel.findById("cveProductoId").setValue('');
	                            				this.setValue(record.get('cdUniEco'));
	                            				this.collapse();	
	                            		        }
	             },

                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{cdRamo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: dsProductos,
                    displayField:'descripcion',
                    valueField:'cdRamo', //'codigo',
                    hiddenName: 'cdRamo',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
		            fieldLabel: getLabelFromMap('cveProductoId',helpMap,'Producto'),
		            tooltip:getToolTipFromMap('cveProductoId',helpMap,'Elija producto'),
		            hasHelpIcon:getHelpIconFromMap('cveProductoId',helpMap),								 
    				Ayuda: getHelpTextFromMap('cveProductoId',helpMap),
                    width: 300,
                    emptyText:'Seleccione Producto...',
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    id:'cveProductoId',
	                onSelect: function (record) {
	                            				
                       				dsTipoSituacion.removeAll();
                       				dsTipoSituacion.load({
                       						params: {cdRamo: record.get('cdRamo'), cdtipsit: formPanel.findById('cdTipSit')}
                       					});
                       				formPanel.findById("cveTipoSituacionId").setValue('');
                       				this.setValue(record.get('cdRamo'));
                       				this.collapse();	
                       		        }
                },
                {
                    xtype: 'combo', 
                    labelWidth: 70, 
                    tpl: '<tpl for="."><div ext:qtip="{cdTipSit}. {dsTipSit}" class="x-combo-list-item">{dsTipSit}</div></tpl>',
	                store: dsTipoSituacion,	                
	                displayField:'dsTipSit', 
	                valueField:'cdTipSit', 
	                hiddenName: 'cdTipSit', 
	                typeAhead: true,
	                mode: 'local', 
	                triggerAction: 'all', 
		            fieldLabel: getLabelFromMap('cveTipoSituacionId',helpMap,'Tipo de Situaci&oacute;n'),
		            tooltip:getToolTipFromMap('cveTipoSituacionId',helpMap,'Elija tipo de situaci&oacute;n'),
		            hasHelpIcon:getHelpIconFromMap('cveTipoSituacionId',helpMap),								 
    				Ayuda: getHelpTextFromMap('cveTipoSituacionId',helpMap),
	                width: 200, 
	                emptyText:'Seleccione Tipo de situaci&oacute;n...',
	                selectOnFocus:true, 
	                forceSelection:true,
	                allowBlank : false,
	                id: 'cveTipoSituacionId'
	             },
                 {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{cdTabla}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: campoAtributo,
                    displayField:'texto',
                    valueField:'cdTabla',
                    hiddenName: 'cdTabla',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
		            fieldLabel: getLabelFromMap('atributoId',helpMap,'Atributo'),
		            tooltip:getToolTipFromMap('atributoId',helpMap,'Elija Atributo'),
		            hasHelpIcon:getHelpIconFromMap('atributoId',helpMap),								 
    				Ayuda: getHelpTextFromMap('atributoId',helpMap),
                    width: 300,
                    emptyText:'Seleccione Atributo...',
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    id:'atributoId'
	                            		        
                },
                 {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{swFormaCalculo}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: campoCalculo,
                    displayField:'texto',
                    valueField:'swFormaCalculo',
                    hiddenName: 'swFormaCalculo',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
		            fieldLabel: getLabelFromMap('calculoId',helpMap,'C&aacute;lculo'),
		            tooltip:getToolTipFromMap('calculoId',helpMap,'Elija c&aacute;lculo'),
		            hasHelpIcon:getHelpIconFromMap('calculoId',helpMap),								 
    				Ayuda: getHelpTextFromMap('calculoId',helpMap),
                    width: 300,
                    emptyText:'Seleccione Calculo',
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    id:'calculoId'
                }
                ]
        });

//Windows donde se van a visualizar la pantalla

        var window = new Ext.Window({
        	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('121',helpMap,'Configurar Forma C&aacute;lculo Atributos Variables')+'</span>',
        	width: 500,
            height:300,
            modal: true,
        	layout: 'fit',
        	plain:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {

                text:getLabelFromMap('saveForCalAtrVarBtnAdd',helpMap,'Guardar'),
                tooltip: getToolTipFromMap('saveForCalAtrVarBtnAdd',helpMap,'Guarda nuevos atributos de configuraci&oacute;n'),

                disabled : false,

                handler : function() {

                    if (formPanel.form.isValid()) {

                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                            },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')

                        });

                    } else {
                    		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'))
                    }

                }

            }, 
            {
                text:getLabelFromMap('saveForCalAtrVarBtnAddAg',helpMap,'Guardar Agregar'),
                tooltip: getToolTipFromMap('saveForCalAtrVarBtnAddAg',helpMap,'Guarda y agrega nuevos atributos de Configuraci&oacute;n'),

                disabled : false,

                handler : function() {
                    if (formPanel.form.isValid()) {

                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                formPanel.form.reset();
                                
                                //Limpiamos los combos
                                dsClientesCorp.removeAll();
								dsAseguradora.removeAll();
								dsProductos.removeAll();
								dsTipoSituacion.removeAll();
								campoAtributo.removeAll();
								campoCalculo.removeAll();
								
								//Volvemos a cargar los combos
								dsTipoSituacion.load();    	
						        dsClientesCorp.load();
						        campoAtributo.load();
						        campoCalculo.load();
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                            },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')

                        });

                    } else {
                    		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'))
                    }
				
				}            
            },
            {
			    text:getLabelFromMap('saveForCalAtrVarBtnCanc',helpMap,'Cancelar'),
			    tooltip: getToolTipFromMap('saveForCalAtrVarBtnCanc',helpMap,'Cancela guardar forma de c&aacute;lculos de atributos'),

                handler : function() {
                    window.close();
                }

            }]

    	});
    	dsTipoSituacion.load();    	
        dsClientesCorp.load();
        campoAtributo.load();
        campoCalculo.load();
    	window.show();
};