// Funcion de Agregar requisito de rehabilitacion

function agregar() {

   var dsClientesCorp = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({
               url: _ACTION_OBTENER_CLIENTES_CORPORATIVO
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
						proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_ASEGURADORAS_CLIENTE}),
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
            id: 'codigo'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ])
    });
    
     var dsTipoDocumento = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPOS_DOCUMENTO
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboTiposDocumento'
            },[
           {name: 'cdDocXcta', type: 'string',mapping:'cdDocXcta'},
           {name: 'dsFormato', type: 'string',mapping:'dsFormato'}
        ])
    });
    
    

        //se define el formulario
        var formPanel = new Ext.FormPanel( {

            labelWidth : 100,

            url : _ACTION_INSERTAR_GUARDAR_REQUISITOS_REHABILITACION,

            frame : true,

            renderTo: Ext.get('formulario'),

            bodyStyle : 'padding:5px 5px 0',
            
            bodyStyle:'background: white',

            width : 350,

            waitMsgTarget : true,

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
		            fieldLabel: getLabelFromMap('agrReqRehCboCli',helpMap,'Cliente'),
	                tooltip: getToolTipFromMap('agrReqRehCboCli',helpMap,'Elija Cliente'),			          		
                    forceSelection: true,
                    width: 200,
                    emptyText:'Seleccione Cliente ...',
                    selectOnFocus:true,
                    forceSelection:true,
                    labelSeparator:'',
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
		            fieldLabel: getLabelFromMap('agrReqRehCboAseg',helpMap,'Aseguradora'),
	                tooltip: getToolTipFromMap('agrReqRehCboAseg',helpMap,'Elija Aseguradora'),			          		
	                forceSelection: true,
	                width: 200, 
	                emptyText:'Seleccione Aseguradora...',
	                selectOnFocus:true, 
	                forceSelection:true,
	                labelSeparator:'', 
	                allowBlank : false,
	                id: 'cveAseguradoraId',
	                onSelect: function (record) {
	                            				
	                            				dsProductos.removeAll();
	                            				dsProductos.load({
	                            						params: {cdElemento: formPanel.findById(('cdElementoId')).getValue() ,cdunieco: record.get('cdUniEco')},
	                            					});
	                            				formPanel.findById("cveProductoId").setValue('');
	                            				this.setValue(record.get('cdUniEco'));
	                            				this.collapse();	
	                            		        }
	             },

                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: dsProductos,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'cdRamo',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
		            fieldLabel: getLabelFromMap('agrReqRehCboProd',helpMap,'Producto'),
	                tooltip: getToolTipFromMap('agrReqRehCboProd',helpMap,'Elija Producto'),			          		
                    forceSelection: true,
                    width: 300,
                    emptyText:'Seleccione Producto...',
                    selectOnFocus:true,
                    forceSelection:true,
                    labelSeparator:'',
                    allowBlank : false,
                    id:'cveProductoId',
	                onSelect: function (record) {
	                            				
	                            				dsTipoDocumento.removeAll();
	                            				dsTipoDocumento.load({
	                            						params: {cdElemento: formPanel.findById(('cdElementoId')).getValue() ,cdUniEco: formPanel.findById(('cveAseguradoraId')).getValue(),cdRamo: record.get('codigo')},
	                            					});
	                            				formPanel.findById("cdDocXctaId").setValue('');
	                            				this.setValue(record.get('codigo'));
	                            				this.collapse();	
	                            		        }
	                            		        
                },
                
                {xtype: 'textfield', 
               	fieldLabel: getLabelFromMap('agrReqRehTxtNom', helpMap,'Nombre'), 
				tooltip: getToolTipFromMap('agrReqRehTxtNom', helpMap, 'Texto nombre'), 			                    	
                name: 'dsRequisito', 
                id: 'dsRequisitoId'},
                 
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{cdDocXcta}. {dsFormato}" class="x-combo-list-item">{dsFormato}</div></tpl>',
                    store: dsTipoDocumento,
                    displayField:'dsFormato',
                    valueField:'cdDocXcta',
                    hiddenName: 'cdDocXcta',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
		            fieldLabel: getLabelFromMap('agrReqRehCboTipDoc',helpMap,'Tipo Documento'),
	                tooltip: getToolTipFromMap('agrReqRehCboTipDoc',helpMap,'Elija Tipo Documento'),			          		
                    forceSelection: true,
                    width: 300,
                    emptyText:'Seleccione Tipo Documento...',
                    selectOnFocus:true,
                    forceSelection:true,
                    labelSeparator:'',
                    allowBlank : false,
                    id:'cdDocXctaId'
	                            		        
                },
                ]

        });


//Windows donde se van a visualizar la pantalla

        var window = new Ext.Window({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('110', helpMap,'Crear Requisito de Rehabilitaci&oacute;n')+'</span>',
        	width: 500,
            height:100,
        	layout: 'fit',
        	modal: true,
        	plain:true,
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {

				text:getLabelFromMap('agrReqRehBtnSave', helpMap,'Guardar'),
				tooltip:getToolTipFromMap('agrReqRehBtnSave', helpMap, 'Guarda requisito de rehabilitaci&oacute;n'),

                disabled : false,

                handler : function() {

                    if (formPanel.form.isValid()) {

                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_INSERTAR_GUARDAR_REQUISITOS_REHABILITACION,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400027', helpMap,'Guardando datos...'));
                                window.close();
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),'Problemas al Insertar : ' + action.result.errorMessages[0]);
                            },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')

                        });

                    } else {
						
						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));

                    }

                }

            }, {

				text:getLabelFromMap('agrReqRehBtnSave', helpMap,'Cancelar'),
				tooltip:getToolTipFromMap('agrReqRehBtnSave', helpMap, 'Cancela Guardar Requisito de rehabilitaci&oacute;n'),

                handler : function() {
                    window.close();
                }

            }]

    	});
    	
        dsClientesCorp.load();
        dsTipoDocumento.load();
    	window.show();
};