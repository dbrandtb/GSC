// Funcion de Agregar per&iacute;odo de gracia

function agregar() {

 var dsAseguradora = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_ASEGURADORAS_CLIENTE}),
						reader: new Ext.data.JsonReader({
								root: 'aseguradoraComboBox',
								id: 'cdUniEco',
								successProperty: '@success'
							}, [
								{name: 'cdUniEco', type: 'string', mapping: 'cdUniEco'},
								{name: 'dsUniEco', type: 'string', mapping: 'dsUniEco'} 
							]),
						remoteSort: true
				});


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
       ]),
       remoteSort: true
       });



    var dsProductos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PRODUCTOS_ASEGURADORA_CLIENTE
            }),
            reader: new Ext.data.JsonReader({
            root: 'productosAseguradoraCliente',
            id: 'codigo'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ]),
        remoteSort: true
    });
    
     var dsPeriodosGracia = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_PERIODOS_GRACIA_OBTENER_PERIODOS_GRACIA
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboPeriodosGracia'
          
            },[
           {name: 'cdTramo', type: 'string',mapping:'cdTramo'},
           {name: 'dsTramo', type: 'string',mapping:'dsTramo'}
        ]),
        remoteSort: true
    });
    
    

        //se define el formulario
        var formPanel = new Ext.FormPanel( {

            labelWidth : 100,

            url : _ACTION_INSERTAR_PERIODOS_GRACIA_CLIENTE,

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
                    width: 300,
                    emptyText:'Seleccione Cliente ...',
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
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
                        this.setValue(record.get("cdElemento"))
                        }
                },
                {
                    xtype: 'combo', 
                    labelWidth: 70, 
                    tpl: '<tpl for="."><div ext:qtip="{cdUniEco}. {dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
	                store: dsAseguradora,
	                displayField:'dsUniEco', 
	                valueField:'cdUniEco', 
	                hiddenName: 'cveAseguradora', 
	                typeAhead: true,
	                mode: 'local', 
	                triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cveAseguradoraId',helpMap,'Aseguradora'),
                    tooltip:getToolTipFromMap('cveAseguradoraId',helpMap,'Elija Aseguradora'),
                    hasHelpIcon:getHelpIconFromMap('cveAseguradoraId',helpMap),
            		Ayuda: getHelpTextFromMap('cveAseguradoraId',helpMap),
	                width: 300, 
	                emptyText:'Seleccione Aseguradora...',
	                selectOnFocus:true, 
	                forceSelection:true,
	                //labelSeparator:'', 
	                allowBlank : false,
	                id: 'cveAseguradoraId',
	                onSelect: function (record) {
	                            				
	                            				dsProductos.removeAll();
	                            				dsProductos.load({
	                            						params: {cdElemento: formPanel.findById(('cdElementoId')).getValue() ,cdunieco: record.get('cdUniEco')}
	                            					});
	                            				formPanel.findById("cveProductoId").setValue('');
	                            				this.setValue(record.get('cdUniEco'));
	                            				this.collapse()	
	                            		        }
	             },

                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: dsProductos,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'cveProducto',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cveProductoId',helpMap,'Producto'),
                    tooltip:getToolTipFromMap('cveProductoId',helpMap,'Elija Producto'),
                    hasHelpIcon:getHelpIconFromMap('cveProductoId',helpMap),
            		Ayuda: getHelpTextFromMap('cveProductoId',helpMap),
                    width: 300,
                    emptyText:'Seleccione Producto...',
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    allowBlank : false,
                    id:'cveProductoId'
	                            		        
                },
                 {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{cdTramo}. {dsTramo}" class="x-combo-list-item">{dsTramo}</div></tpl>',
                    store: dsPeriodosGracia,
                    displayField:'dsTramo',
                    valueField:'cdTramo',
                    hiddenName: 'cdTramo',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cdTramoId',helpMap,'Per&iacute;odos de Gracia'),
                    tooltip:getToolTipFromMap('cdTramoId',helpMap,'Elija Per&iacute;odos de Gracia'),
                    hasHelpIcon:getHelpIconFromMap('cdTramoId',helpMap),
            		Ayuda: getHelpTextFromMap('cdTramoId',helpMap),
                    width: 300,
                    emptyText:'Seleccione Periodo de Gracia...',
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    allowBlank : false,
                    id:'cdTramoId'
	                            		        
                }
                ]

        });


//Windows donde se van a visualizar la pantalla

        var window = new Ext.Window({
        	id:'winAgrPerClien',
        	title: getLabelFromMap('winAgrPerClien', helpMap,'Crear Per&iacute;odos de Gracia por Cliente'),
        	//title:'<span style="color:black;font-size:12px;font-family:Arial,Helvetica,sans-serif;">'+getLabelFromMap('107',helpMap,'Crear Per&iacute;odos de Gracia por Cliente')+'</span>',
        	width: 500,
            height:250,
        	minWidth: 300,
        	minHeight: 100,
        	layout: 'fit',
        	plain:true,
        	modal: true,
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	labelAlign:'right',
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {

                text:getLabelFromMap('perGraClieBtnSave',helpMap,'Guardar'),
                
                tooltip: getToolTipFromMap('perGraClieBtnSave',helpMap,'Guarda per&iacute;odo de gracia por cliente'),

                disabled : false,

                handler : function() {

                    if (formPanel.form.isValid()) {

                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_INSERTAR_PERIODOS_GRACIA_CLIENTE,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close()
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0])
                            },

                            //mensaje a mostrar mientras se guardan los datos
						  //waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
						  waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')

                        });

                    } else {

                        Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'))

                    }

                }

            }, {

                text:getLabelFromMap('perGraClieBtnCanc',helpMap,'Cancelar'),
                
                tooltip: getToolTipFromMap('perGraClieBtnCanc',helpMap,'Cancela operaci&oacute;n de per&iacute;odo de gracia por cliente'),

                handler : function() {
                    window.close()
                }

            }]

    	});
    	
        dsClientesCorp.load();
        dsPeriodosGracia.load();
    	window.show()
}