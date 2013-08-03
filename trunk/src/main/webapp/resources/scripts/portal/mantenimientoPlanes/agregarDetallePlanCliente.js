function agregar() {
		// Se cambió los combos por pedido de oslen 12 - Sept - 2008 15:17 hs
        var storeProductoPlan = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_PRODUCTOS_X_PLAN}),
            reader: new Ext.data.JsonReader({
		            root: 'comboProductoPlan',
		            id: 'codigo'
            	},
            	[
		           {name: 'codigo', type: 'string',mapping:'codigo'},
		           {name: 'descripcion', type: 'string',mapping:'descripcion'}
       			 ])
        });
        
        var storePlanes = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_PLANES_PRODUCTO}),
            reader: new Ext.data.JsonReader({
		            root: 'comboPlanProducto',
		            id: 'codigo'
	            },
	            [
	           		{name: 'codigo', type: 'string',mapping:'codigo'},
	           		{name: 'descripcion', type: 'string',mapping:'descripcion'}
       			 ])
        });
        
        // Se cambió los combos por pedido de oslen 12 - Sept - 2008 15:17 hs
        var storeSituacionPlan = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_SITUACION_X_PLAN}),
            reader: new Ext.data.JsonReader({
            		root: 'comboSituacionPlan',
            		id: 'codigo'
            	},
            	[
           			{name: 'codSituacion', type: 'string',mapping:'codigo'},
           			{name: 'descripcionSituacion', type: 'string',mapping:'descripcion'}
        		])
        });

		// Se cambió los combos por pedido de oslen 12 - Sept - 2008 15:17 hs
        var storeCoberturasPlan = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_COBERTURAS_X_PLAN
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboCoberturaPlan',
            id: 'codigo'
            },[
           {name: 'codGarantia', type: 'string',mapping:'codigo'},
           {name: 'descripcionGarantia', type: 'string',mapping:'descripcion'}
        ])
        });

        var dsAseguradoras = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_ASEGURADORAS
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboAseguradoraPorProducto',
            id: 'codigo'
            },[
           {name: 'codAseguradora', type: 'string',mapping:'codigo'},
           {name: 'descripcionAseguradora', type: 'string',mapping:'descripcion'}
        ])
        });
   			 

        var dsClientesCorp = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_CLIENTES_CORP
            }),
            reader: new Ext.data.JsonReader({
            root: 'clientesCorp',
            id: 'cdElemento'
            },[
            {name: 'cdElemento', type: 'string',mapping:'cdElemento'},
            {name: 'cdPerson', type: 'string',mapping:'cdPerson'},
            {name: 'dsElemen', type: 'string',mapping:'dsElemen'}
        ])
        });

        var codigoClienteHidden = new Ext.form.Hidden( {

                                        name : 'codigoCliente'

                                    });


		var cboGarant=new Ext.form.ComboBox({
							xtype: 'comboBox',
                            tpl: '<tpl for="."><div ext:qtip="{codGarantia}. {descripcionGarantia}" class="x-combo-list-item">{descripcionGarantia}</div></tpl>',
                            store: storeCoberturasPlan,
                            displayField:'descripcionGarantia',
                            
                          	//forceSelection: true,
                            valueField:'codGarantia',
                            hiddenName: 'codigoGarantia',
                            typeAhead: true,
                            mode: 'remote',
                            //allowBlank:false,
                          //  blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
                            blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),                            
                            triggerAction: 'all',
                            fieldLabel: getLabelFromMap('AcomboCoberturas',helpMap,'Tipo de Cobertura'),
                            tooltip: getToolTipFromMap('AcomboCoberturas',helpMap, 'Tipo de Cobertura'),
		                    hasHelpIcon:getHelpIconFromMap('AcomboCoberturas',helpMap),								 
		                    Ayuda: getHelpTextFromMap('AcomboCoberturas',helpMap),
                            width: 300,
                            emptyText:'Seleccione Tipo de Cobertura...',
                            selectOnFocus:true,
                            lazyRender:true,
                            clearValue:true,
                            id: 'AcomboCoberturas'
                            });


   			
        //se define el formulario
        var formPanel = new Ext.FormPanel( {
            labelWidth : 100,
            url : _ACTION_GUARDAR_NUEVO_PLANCLIENTE,
            frame : true,
            bodyStyle:'background: white',
			labelAlign:'right',
            renderTo: Ext.get('formulario'),
            bodyStyle : ('padding: 5px, 0, 0, 0;','background: white'),
            width : 350,
            waitMsgTarget : true,
            defaults : {
                width : 230
            },
            defaultType : 'textfield',
            //se definen los campos del formulario
            items : [
            	new Ext.form.ComboBox({
            				id:'cmbAddDetProductoId',
                            tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}.{dsElemen}" class="x-combo-list-item">{descripcion}</div></tpl>',
                            store: storeProductoPlan,
                          	forceSelection: true,
                            displayField:'descripcion',
                            valueField:'codigo',
                            hiddenName: 'codigoProducto',
                            typeAhead: true,
                            mode: 'local',
                            triggerAction: 'all',
                            fieldLabel: getLabelFromMap('cmbAddDetProductoId',helpMap,'Producto'),
                            tooltip: getToolTipFromMap('cmbAddDetProductoId',helpMap, 'Productos'),
		                    hasHelpIcon:getHelpIconFromMap('cmbAddDetProductoId',helpMap),								 
		                    Ayuda: getHelpTextFromMap('cmbAddDetProductoId',helpMap),
                            width: 300,
                            allowBlank:false,
                            blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),                            
                            emptyText:'Seleccione Producto...',
                            selectOnFocus:true,
                            onSelect: function (record) {
                            	//console.log(cboGarant);
                            	//cboGarant.clearValue();                            	
                                this.setValue(record.get("codigo"));
                                this.collapse();
                                storePlanes.removeAll();
                                dsAseguradoras.removeAll();
                                storeCoberturasPlan.removeAll();   

                                storePlanes.reload({params: {cdRamo: record.get("codigo")}});
                                dsAseguradoras.reload({params: {cdRamo: record.get("codigo")}});
                                storeSituacionPlan.reload({
                            				params: {
                            					cdRamo: record.get("codigo"),                            					
                            					cdPlan: formPanel.findById('cmbAddDetPlanProductoId').getValue()
                            				}
                            		});                                					
                           		storeCoberturasPlan.reload({params:{
                           										cdRamo: record.get("codigo"),
                           										cdTipSit: formPanel.findById('cmbAddDetPlanSituacId').getValue(),
																cdPlan: formPanel.findById('cmbAddDetPlanProductoId').getValue()
                           										}
                           									});
                           									
                                Ext.getCmp('cmbAddDetPlanProductoId').clearValue();
                            	Ext.getCmp('comboAseguradoras').clearValue();
                            	Ext.getCmp('cmbAddDetPlanSituacId').clearValue();
                            	Ext.getCmp('cmbAddGarantPlanClienteId').clearValue();
                            	//cmbAddGarantPlanClienteId
                            	//cboGarant.setValue('');
                            }
                            
                }),
            	
               /*new Ext.form.TextField( {
               	id:'txtAddDetPlanProductoId',
                fieldLabel : getLabelFromMap('txtAddDetPlanProductoId',helpMap,'Producto') ,
                tooltip : getToolTipFromMap('txtAddDetPlanProductoId',helpMap, 'Producto del Plan') ,
                name : 'descripcionProducto',
                allowBlank : false,
                value: DESCRIPCION_PRODUCTO,
                disabled : true,
                width: 200
            }),*/
            
            
            new Ext.form.ComboBox({
            				id:'cmbAddDetPlanProductoId',
                            tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}.{dsElemen}" class="x-combo-list-item">{descripcion}</div></tpl>',
                            store: storePlanes,
                          	forceSelection: true,
                            displayField:'descripcion',
                            valueField:'codigo',
                            hiddenName: 'codigoPlan',
                            typeAhead: true,
                            mode: 'local',
                            triggerAction: 'all',
                            fieldLabel: getLabelFromMap('cmbAddDetPlanProductoId',helpMap,'Plan'),
                            tooltip: getToolTipFromMap('cmbAddDetPlanProductoId',helpMap, 'Planes'),
		                    hasHelpIcon:getHelpIconFromMap('cmbAddDetPlanProductoId',helpMap),								 
		                    Ayuda: getHelpTextFromMap('cmbAddDetPlanProductoId',helpMap),
                            width: 300,
                            allowBlank:false,
                            //blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
                            blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),                            
                            emptyText:'Seleccione Plan...',
                            selectOnFocus:true,
                            onSelect: function (record) {
                                this.setValue(record.get("codigo"));
                                this.collapse();
                                storeSituacionPlan.reload({
                            				params: {
                            					cdRamo: formPanel.findById('cmbAddDetProductoId').getValue(),                            					
                            					cdPlan: record.get("codigo")
                            				}
                            		});                                					
                           		storeCoberturasPlan.reload({params:{
                           										cdRamo: formPanel.findById('cmbAddDetProductoId').getValue(),
                           										cdTipSit: formPanel.findById('cmbAddDetPlanSituacId').getValue(),
																cdPlan: record.get("codigo")
                           										}
                           									});
                               	Ext.getCmp('comboAseguradoras').clearValue();
                            	Ext.getCmp('cmbAddDetPlanSituacId').clearValue();
                            	Ext.getCmp('cmbAddGarantPlanClienteId').clearValue();
                            	//cboGarant.setValue('');
                            }
             }),
             
            /*new Ext.form.TextField( {
               	id:'txtAddDetPlanPlanId',
                fieldLabel : getLabelFromMap('txtAddDetPlanPlanId',helpMap,'Plan') ,
                tooltip : getToolTipFromMap('txtAddDetPlanPlanId',helpMap, 'Plan del Cliente') ,
                name : 'descripcionPlan',
                allowBlank : false,
                value: DESCRIPCION_PLAN,
                disabled : true,
                width: 200
            }),*/
            
            new Ext.form.Hidden({name : 'codigoCliente',id : 'idCodigoCliente'}),
                                    
            new Ext.form.ComboBox({
            				id:'cmbAddDetPlanClienteId',
                            tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                            store: dsClientesCorp,
                          	forceSelection: true,
                            displayField:'dsElemen',
                            valueField:'cdElemento',
                            hiddenName: 'codigoElemento',
                            typeAhead: true,
                            mode: 'local',
                            allowBlank:false,
                            blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),                            
                            triggerAction: 'all',
                            fieldLabel: getLabelFromMap('cmbAddDetPlanClienteId',helpMap,'Cliente'),
                            tooltip: getToolTipFromMap('cmbAddDetPlanClienteId',helpMap, 'Cliente del Plan'),
		                    hasHelpIcon:getHelpIconFromMap('cmbAddDetPlanClienteId',helpMap),								 
		                    Ayuda: getHelpTextFromMap('cmbAddDetPlanClienteId',helpMap),
                            width: 300,
                            emptyText:'Seleccionar Cliente',
                            selectOnFocus:true
             }),


            new Ext.form.ComboBox({
                            tpl: '<tpl for="."><div ext:qtip="{codAseguradora}. {descripcionAseguradora}" class="x-combo-list-item">{descripcionAseguradora}</div></tpl>',
                            store: dsAseguradoras,
                          	forceSelection: true,
                            displayField:'descripcionAseguradora',
                            valueField:'codAseguradora',
                            hiddenName: 'codigoAseguradora',
                            typeAhead: true,
                            mode: 'local',
                            allowBlank:false,
                            blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),                            
                            triggerAction: 'all',
                            fieldLabel: getLabelFromMap('comboAseguradoras',helpMap,'Aseguradora'),
                            tooltip: getToolTipFromMap('comboAseguradoras',helpMap, 'Aseguradora del Plan'),
		                    hasHelpIcon:getHelpIconFromMap('comboAseguradoras',helpMap),								 
		                    Ayuda: getHelpTextFromMap('comboAseguradoras',helpMap),
                            width: 300,
                            emptyText:'Seleccionar Aseguradora...',
                            selectOnFocus:true,
                            id: 'comboAseguradoras'
                            }),



            new Ext.form.ComboBox({
            				id:'cmbAddDetPlanSituacId',            				
                            tpl: '<tpl for="."><div ext:qtip="{codSituacion}. {descripcionSituacion}" class="x-combo-list-item">{descripcionSituacion}</div></tpl>',
                            store: storeSituacionPlan,
                            displayField:'descripcionSituacion',
                            valueField:'codSituacion',
	                        forceSelection: true,
                            hiddenName: 'codigoSituacion',
                            typeAhead: true,
                            mode: 'local',
                            allowBlank:false,
                            //blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
                            blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),                            
                            triggerAction: 'all',
                            fieldLabel: getLabelFromMap('cmbAddDetPlanSituacId',helpMap,'Riesgo'),
                            tooltip: getToolTipFromMap('cmbAddDetPlanSituacId',helpMap, 'Riesgo'),
		                    hasHelpIcon:getHelpIconFromMap('cmbAddDetPlanSituacId',helpMap),								 
		                    Ayuda: getHelpTextFromMap('cmbAddDetPlanSituacId',helpMap),
                            width: 300,
                            lazyRender:true,
                            emptyText:'Seleccionar Riesgo...',
                            selectOnFocus:true,
                            onSelect: function(record) {
                            		this.setValue(record.get('codSituacion'));
                            		this.collapse();
                            		Ext.getCmp('cmbAddGarantPlanClienteId').clearValue();
                            		//cboGarant.setRawValue('');
                            		storeCoberturasPlan.removeAll();
                            		
                            		storeCoberturasPlan.reload({params:{
                           										cdRamo: formPanel.findById('cmbAddDetProductoId').getValue(),
                           										cdTipSit: record.get("codSituacion"),
																cdPlan: formPanel.findById('cmbAddDetPlanProductoId').getValue()
                           										}
                           									});
                            }
             }),
             
             new Ext.form.ComboBox({
            				id:'cmbAddGarantPlanClienteId',
                            tpl: '<tpl for="."><div ext:qtip="{codGarantia}. {descripcionGarantia}" class="x-combo-list-item">{descripcionGarantia}</div></tpl>',
                            store: storeCoberturasPlan,
                          	forceSelection: true,
                            displayField:'descripcionGarantia',
                            valueField:'codGarantia',
                            hiddenName: 'codigoGarantia',
                            typeAhead: true,
                            mode: 'local',
                           // allowBlank:false,
                            blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),                            
                            triggerAction: 'all',
                            fieldLabel: getLabelFromMap('cmbAddGarantPlanClienteId',helpMap,'Cobertura'),
                            tooltip: getToolTipFromMap('cmbAddGarantPlanClienteId',helpMap, 'Cobertura del Plan'),
		                    hasHelpIcon:getHelpIconFromMap('cmbAddGarantPlanClienteId',helpMap),								 
		                    Ayuda: getHelpTextFromMap('cmbAddGarantPlanClienteId',helpMap),
                            width: 300,
                            emptyText:'Seleccionar Tipo de Cobertura',
                            selectOnFocus:true
             }),
             new Ext.form.Checkbox( {
             	id:'cbAddPlanObligId',
                fieldLabel: getLabelFromMap('cbAddPlanObligId',helpMap,'Oblig'),
                tooltip: getToolTipFromMap('cbAddPlanObligId',helpMap,'Tilde si es obligatorio'),
                hasHelpIcon:getHelpIconFromMap('cbAddPlanObligId',helpMap),								 
                Ayuda: getHelpTextFromMap('cbAddPlanObligId',helpMap),
                name : 'codigoObligatorio',
                style: 'marginTop: 5px',
                width: 200
            })
            ]

        });



   			 
//Windows donde se van a visualizar la pantalla


        var window = new Ext.Window({
        	id:'wAddDetPlanId',
            title: getLabelFromMap('wAddDetPlanId', helpMap,'Agregar productos por cliente'),        	
        	width: 500,
        	height:295,
        	modal: true,
        	layout: 'fit',
        	plain:true,
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {
                text : getLabelFromMap('wAddSaveDetPlanId',helpMap,'Guardar'),
				tooltip : getToolTipFromMap('wAddSaveDetPlanId',helpMap,'Guarda el Plan'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_NUEVO_PLANCLIENTE,
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
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                    }
                }
            }, {
                text : getLabelFromMap('wAddCancelDetPlanId',helpMap,'Cancelar'),
				tooltip : getToolTipFromMap('wAddCancelDetPlanId',helpMap, 'Cancela el ingreso de datos'),
                handler : function() {
                    window.close();
                }
            }]
    	});
 
 	storeProductoPlan.load();
 	dsClientesCorp.load();
    
    
     window.show();
};