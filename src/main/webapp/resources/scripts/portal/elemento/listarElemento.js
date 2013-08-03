Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";


var proxyUrl = new Ext.data.HttpProxy({
    url: _ACTION_ELEMENTO
});

//  Add the HTTP parameter searchTerm to the request
proxyUrl.on('beforeload', function(p, params) {
    params.codigo = codigo.getValue;
    params.descripcion = descripcion.getValue();
});

 //cantidad a estructuras a mostrar por pagina
 var itemsPerPage=10;

    /*
        ComboBox de Productos
    */

        var ds = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PRODUCTOS
            }),
            reader: new Ext.data.JsonReader({
            root: 'elementosComboBox',
            id: 'codigoComboProducto'
            },[
           {name: 'codigoComboProducto', type: 'string',mapping:'codigo'},         f
           {name: 'descripcionComboProducto', type: 'string',mapping:'descripcion'}
        ]),
        remoteSort: true
        });

        var comboBoxProductos = new Ext.form.ComboBox({
                tpl: '<tpl for="."><div ext:qtip="{codigoComboProducto}. {descripcionComboProducto}" class="x-combo-list-item">{descripcionComboProducto}</div></tpl>',
                store: ds,
                displayField:'descripcionComboProducto',
                typeAhead: true,
                mode: 'local',
                triggerAction: 'all',
                name: "codigoComboProducto",
                fieldLabel: "Producto*:",
                width: 300,
                emptyText:'Seleccionar producto...',
                selectOnFocus:true,
                forceSelection:true,
                labelSeparator:''
                });
    


 function test(){
 			//url='elemento/elementos.action'+'?codigo='+codigo.getValue()+'&descripcion='+descripcion.getValue();
 			//alert(_ACTION_ELEMENTO);
 			store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_ELEMENTO
                }),

                //proxy: proxyUrl,
                reader: new Ext.data.JsonReader({
            	root:'MElementoList',
            	totalProperty: 'totalCount',
            	id: 'codigo',
	            successProperty : '@success'
	        },[
	        {name: 'codigo',  type: 'string',  mapping:'codigo'},
	        {name: 'descripcion',  type: 'string',  mapping:'descripcion'}
			])
        });
    	//store.setDefaultSort('totales', 'desc');
        //store.load({params:{start:0,limit:itemsPerPage}});
		return store;
 	}
    /*
        var ds = new Ext.data.Store({
        	proxy: new Ext.data.HttpProxy({
				url: 'mantto/ProductosLista.action'
        	}),
        	reader: new Ext.data.JsonReader({
            root: 'productos',
            id: 'listas'
	        },[
           {name: 'cdRamoP', type: 'string',mapping:'cdRamoP'},
           {name: 'dsRamo', type: 'string',mapping:'dsRamo'}
        	]),
			remoteSort: true
    	});
		ds.setDefaultSort('listas', 'desc');
     */


// COMIENZO AGREGAR

        function agregar () {
		var descripcionElemento = new Ext.form.TextField({
        	fieldLabel: 'Nombre Elemento',
        	allowBlank: false,
        	name: 'descripcion',
        	width: 300
    	});

        var codigoElemento = new Ext.form.TextField({
            fieldLabel: 'codigo',
            allowBlank: false,
            name: 'codigo',
            width: 30
        });

        var formPanel = new Ext.form.FormPanel({
  		url:_ACTION_CREATE_ELEMENTO,
        baseCls: 'x-plain',
        labelWidth: 75,
        defaultType: 'textfield',
        items: [
                new Ext.form.ComboBox({
                                tpl: '<tpl for="."><div ext:qtip="{codigoComboProducto}. {descripcionComboProducto}" class="x-combo-list-item">{descripcionComboProducto}</div></tpl>',
                                store: ds,
                                displayField:'descripcionComboProducto',
                                typeAhead: true,
                                mode: 'local',
                                triggerAction: 'all',
                                name: "codigoComboProducto2",
                                fieldLabel: "Producto*:",
                                width: 300,
                                emptyText:'Seleccionar producto...',
                                selectOnFocus:true,
                                labelSeparator:''
                                }),


            /*
            new Ext.form.ComboBox({
            	tpl: '<tpl for="."><div ext:qtip="{dsRamo}. {cdRamoP}" class="x-combo-list-item">{dsRamo}</div></tpl>',
    			store: ds,
    			displayField:'cdRamoP',
    			typeAhead: true,
    			mode: 'local',
    			triggerAction: 'all',
				name: "cdRamoP",
				fieldLabel: "Producto*:",
				width: 300,
				emptyText:'Seleccionar producto...',
				labelSeparator:'',
				selectOnFocus:true
			}),  */
            //comboBoxProductos,
            descripcionElemento             
            ]
    	});

    	var window = new Ext.Window({
        	title: 'Agregar Elemento',
        	width: 500,
        	height:150,
        	minWidth: 300,
        	minHeight: 100,
        	layout: 'fit',
        	plain:true,
        	modal: true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: formPanel,
        	buttons: [{
            	text: 'Guardar',
            	handler: function() {
                	if (formPanel.form.isValid()) {
	 		        	formPanel.form.submit({
			            	waitMsg:'Procesando...',
			            	failure: function(form, action) {
						    		Ext.MessageBox.alert('Error', action.result.actionErrors[0]);
						    		window.hide();
                                    reloadGrid();
                            },
							success: function(form, action) {
						    	Ext.MessageBox.alert('Elemento creado', action.result.actionMessages[0]);
						    	window.hide();
                                reloadGrid();
							}
			        	});
                		} else{
						Ext.MessageBox.alert('Error', 'Porfavor revise los errores!');
							}
	        			}
        	},{
            text: 'Regresar',
            handler: function(){
            		window.close();}
        	}]
    	});

    	window.show();
	};

//FIN AGREGAR

/*
	editar = function() {


	var nombrePlan = new Ext.form.TextField({
        fieldLabel: 'Nombre',
        allowBlank: false,
        name:'dsPlanLista',
        width: 300
    });

    var producto = new Ext.form.TextField({
        fieldLabel: "Producto",
        name:'cdRamo',
        width: 300
    });
    producto.hide();
    var formPanel = new Ext.form.FormPanel({
        labelWidth: 75,
     	url:'mantto/EditaProductos.action',
     	baseCls: 'x-plain',
        defaultType: 'textfield',
        items: [
			producto,
            nombrePlan
    	]
    });
    var window = new Ext.Window({
        title: 'Editar Planes',
        width: 500,
        height:150,
        minWidth: 300,
        minHeight: 100,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
        buttons: [{
            text: 'Guardar',
            handler: function() {
            		if (formPanel.form.isValid()) {
	 		        		formPanel.form.submit({
			            		waitMsg:'Procesando...',
			            		failure: function(form, action) {
						    		Ext.MessageBox.alert('Plan Editado', action.result.errorInfo);
						    		window.hide();
								},
							success: function(form, action) {
						    	Ext.MessageBox.alert('ok', action.result.info);
						    	window.hide();
							}
			        	});
                	} else{
							Ext.MessageBox.alert('Error', 'Porfavor revise los errores!');
					}
	        	}
        	},{
            	text: 'Regresar',
            	handler: function(){window.hide();}
        	}]
    	});
    window.show();
	};
*/

 

    var grid2;



function createGrid(){
	grid2= new Ext.grid.GridPanel({
        el:'gridElementos',
        store:test(),
		border:true,
		//baseCls:' background:white ',
        cm: cm,
		buttons:[
        		{text:'Agregar',
            	tooltip:'Agregar un nuevo elemento',
            	handler:function(){
            	//agregar(ds);
                agregar();}
            	},{
            	text:'Editar',
            	tooltip:'Edita elemento seleccionado',
            	handler:function(){
            	editar();}
            	},{
            	text:'Copiar',
            	tooltip:'Copia elemento seleccionado'
            	},{
            	text:'Configurar',
            	tooltip:'Configura elemento seleccionado'
            	},{
            	text:'Eliminar',
            	tooltip:'Elimina elemento seleccionado'
            	},{
            	text:'Exportar',
            	tooltip:'Exporta elementos'
            	}],
    	width:500,
    	frame:true,
		height:320,
		title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
		//renderTo:document.body,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		viewConfig: {autoFill: true,forceFit:true},
		bbar: new Ext.PagingToolbar({
				pageSize:20,
				store: store,
				displayInfo: true,
				displayMsg: 'Displaying rows {0} - {1} of {2}',
				emptyMsg: "No rows to display"
		    })
		});
            grid2.render()
}    

function reloadGrid(){
    var mStore = grid2.store;
    var o = {start: 0};
    var v = 'hola todos';
    var paramName = 'descripcion';
    mStore.baseParams = mStore.baseParams || {};
    mStore.baseParams['codigo'] = codigo.getValue();
    mStore.baseParams['descripcion'] = descripcion.getValue();
    mStore.reload({params:{start:0,limit:itemsPerPage}});

}

    var codigo = new Ext.form.TextField({
        fieldLabel: 'Codigo',
        name:'codigo',
        allowBlank: false,
        width: 200
    });
    var descripcion = new Ext.form.TextField({
        fieldLabel: 'Descripcion',
        name:'descripcion',
        //allowBlank: false,
        width: 200
    });


    var incisosForm = new Ext.form.FormPanel({
        el:'formBusqueda',
		title: '<span style="color:black;font-size:14px;">Mantenimiento de Elementos</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        url:_ACTION_ELEMENTO,
        width: 500,
        height:150,
        items: [{
        		layout:'form',
				border: false,
				items:[{
        		title :'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">B&uacute;squeda</span>',
                bodyStyle:'background: white',
		        labelWidth: 50,
                layout: 'form',
				frame:true,
		       	baseCls: '',
		       	buttonAlign: "center",
        		        items: [{
        		        	layout:'column',
		 				    border:false,
		 				    columnWidth: 1,
        		    		items:[{
						    	columnWidth:.6,
                				layout: 'form',
		                		border: false,
        		        		items:[
                                        codigo,
        		        				descripcion
		       						]
								},{
								columnWidth:.4,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
        							text:'Buscar',
        							handler: function() {
				               			if (incisosForm.form.isValid()) {

                                               if (grid2!=null) {
                                                reloadGrid();
                                               } else {
                                                createGrid();
                                               }


                                               /*
                                                 incisosForm.form.submit({
				        		    				waitMsg:'Procesando...',
							            			failure: function(form, action) {
											    		Ext.MessageBox.alert('Plan creado ', action.result.errorInfo);
													},
													success: function(form, action) {
										    			Ext.MessageBox.alert('Confirma Gaby ', action.result.info);
											    		//grid2.destroy();
											    		createGrid();
											    		//ds.load();
													}

									        	});*/
                						} else{
											Ext.MessageBox.alert('Error', 'Por Favor revise los errores!');
										}
									}
        							},{
        							text:'Cancelar',                              
        							handler: function() {
        								incisosForm.form.reset();
        							}
        						}]
        					}]
        				}]
				});

		incisosForm.render();




        function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
    	}
var store;
/*
		var store = new Ext.data.Store({
    		proxy: new Ext.data.HttpProxy({
				url: _ACTION_MANTTO
        		}),
        		reader: new Ext.data.JsonReader({
            	root:'planes',
            	id: 'totales'
	        },[
	        {name: 'dsPlan',  type: 'string',  mapping:'dsPlan'},
	        {name: 'cdPlan',  type: 'string',  mapping:'cdPlan'}
			]),
			remoteSort: true
    	});
    	store.setDefaultSort('totales', 'desc');
 */
		var cm = new Ext.grid.ColumnModel([
			{
           	header: "Codigo",
           	dataIndex: 'codigo',
           	width: 120
        	},{
           	header: "Descripcion",
           	dataIndex: 'descripcion',
           	width: 120
        }]);


/*
    agregar = function() {
        Ext.MessageBox.alert('Agregar', 'Accion de Agregar un elemento!');
    }
*/

    editar = function() {
        Ext.MessageBox.alert('Editar', 'Accion de Editar un elemento!');
    }


    //createGrid();
	ds.load();
});