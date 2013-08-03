Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//******************************************
//Stores Grid
//******************************************
function createOptionGrid(){
url='configworkflow/obtenerProcesos.action'+'?dsproceso='+nombreProceso.getValue()+'&dsprocesoflujo='+proceso.getValue()+'&dselemen='+nombreCliente.getValue()+'&dsunieco='+nombreAseguradora.getValue()+'&dsramo='+nombreProducto.getValue();	
    store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
        url: url
        }),
        reader: new Ext.data.JsonReader({
        root:'procesosXctaList',
        id:'listaDatos',
        totalProperty: 'totalCount'             
        },[
                {name: 'cdprocxcta',   type: 'string', mapping:'cdprocxcta'},
                {name: 'cdproceso',  type: 'string', mapping:'cdproceso'},
                {name: 'cdunieco',   type: 'string', mapping:'cdunieco'},
                {name: 'cdramo',     type: 'string', mapping:'cdramo'},
                {name: 'cdelemento', type: 'string', mapping:'cdelemento'},
                {name: 'dsproceso',  type: 'string', mapping:'dsproceso'},
                {name: 'dsprocesoflujo', type: 'string', mapping:'dsprocesoflujo'},
                {name: 'dsunieco',   type: 'string', mapping:'dsunieco'},
                {name: 'dsramo',     type: 'string', mapping:'dsramo'},
                {name: 'dselemen',   type: 'string', mapping:'dselemen'}
            ]),
        remoteSort: true
    });

    store.setDefaultSort('listaDatos', 'desc');
    store.load({params:{start:0, limit:20}});
    return store;
 }

//******************************************
//Stores de Combos
//******************************************
var storeProceso = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({
	    url: 'configworkflow/obtenerComboProceso.action'
	}),
	reader: new Ext.data.JsonReader({
	root: 'procesosList',
	id: 'procesosL'
	    },[
	   {name: 'value', type: 'string', mapping:'value'},
	   {name: 'label', type: 'string', mapping:'label'}  
	     ]),
	    remoteSort: true
	});
storeProceso.setDefaultSort('procesosL', 'desc');



var storeCliente = new Ext.data.Store({
	        proxy: new Ext.data.HttpProxy({
	            url: 'configworkflow/obtenerComboCliente.action'
	}),
	reader: new Ext.data.JsonReader({
	root: 'clientesList',
	id: 'clienteL'
	    },[
	   {name: 'cdCliente', type: 'string',mapping:'cdCliente'},
	   {name: 'dsCliente', type: 'string',mapping:'dsCliente'}
	    ]),
	    remoteSort: true
	});
storeCliente.setDefaultSort('clienteL', 'desc');

//******************************************
//Editar
//******************************************

	var idprocxcta = new Ext.form.Hidden({
        name:'cdprocxcta',
        valueField:'cdprocxcta'
    });
    
	var editNomProceso = new Ext.form.TextField({
	    fieldLabel: CONFIGWORKFLOW_PS1_NOMBRE,
	    name:'dsproceso',
	    width: 200
	});
	
	var comboProceso = new Ext.form.TextField({
		name: 'dsprocesoflujo',
	 	fieldLabel: CONFIGWORKFLOW_PS1_PROCESO,
	 	width:200,
	 	disabled:true
	});
	
	var comboCliente = new Ext.form.TextField({
	    name: 'dselemen',
	    fieldLabel: CONFIGWORKFLOW_PS1_CLIENTE,
	 	width:200,
	 	disabled:true
	});

	var comboAseguradora = new Ext.form.TextField({
		name: 'dsunieco',
	    fieldLabel: CONFIGWORKFLOW_PS1_ASEGURADORA,
	 	width:200,
	 	disabled:true
	});
        
	var comboProducto = new Ext.form.TextField({
		name: 'dsramo',
		fieldLabel:CONFIGWORKFLOW_PS1_PRODUCTO,
	 	width:200,
	 	disabled:true
	});
    
	var editForm = new Ext.form.FormPanel({
    id:'editForm',
    url:'configworkflow/insertaProceso.action',
    baseCls: 'x-plain',
    labelWidth: 100,
    defaultType: 'textfield',
    labelAlign: 'right',
    items: [   
    		idprocxcta,
    		editNomProceso,
            comboProceso,
            comboCliente,
            comboAseguradora,
            comboProducto
        ]
    });

    var windowEdit = new Ext.Window({
        title: CONFIGWORKFLOW_PS1_EDITAR_TITLE,
        plain:true,
        width: 380,
        height:240,
        minWidth: 380,
        minHeight: 240,
        layout: 'fit',
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        modal:true,
        items: editForm,
        buttons: [{
            text: CONFIGWORKFLOW_BTN_GUARDAR, 
            handler: function() {                
                if (editForm.form.isValid()) {
                    editForm.form.submit({               
                        waitMsg:CONFIGWORKFLOW_PROCESS_TITLE,
                        failure: function(form, action) {
                                Ext.MessageBox.alert(CONFIGWORKFLOW_PS1_EDITAR_TITLE,CONFIGWORKFLOW_PS1_EDITAR_FAILURE);
                                windowEdit.hide();
                        },
                        success: function(form, action) {
                            Ext.MessageBox.alert(CONFIGWORKFLOW_PS1_EDITAR_TITLE,CONFIGWORKFLOW_PS1_EDITAR_SUCCESS);
                            windowEdit.hide();
							grid2.destroy();
							createGrid();
							store.load();
                        }
                    });                   
                } else{
                    Ext.MessageBox.alert(CONFIGWORKFLOW_EXCEPTION_TITLE);
                      }             
                }
        },{
        text: CONFIGWORKFLOW_BTN_CANCELAR,
        handler: function(){
                windowEdit.hide();
                }
        }]
    });

//******************************************
//Borrar
//******************************************
    var msgBorrar = new Ext.form.TextField({
        fieldLabel:CONFIGWORKFLOW_PS1_BORRAR_MSG,
        labelSeparator:'',
        anchor:'90%',
        hidden:true
    });                   
   
	var idprocxcta = new Ext.form.Hidden({
        fieldLabel: '',   
        name:'cdprocxcta',
        valueField:'cdprocxcta'
    });
    
    var borrarForm= new Ext.FormPanel({
        id:'borrarForm',
        labelAlign:'top',
        baseCls: 'x-plain',
        anchor:'100%',
        url:'configworkflow/borraProceso.action',
        items:[ 
        		msgBorrar,
        		idprocxcta
        ]
    });

    var windowDel = new Ext.Window({
        title: CONFIGWORKFLOW_PS1_BORRAR_TITLE,
        minHeight: 100,
        minWidth: 280,
        width: 280,
        height:100,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: borrarForm,
        buttons: [{
                text: CONFIGWORKFLOW_BTN_BORRAR, 
                handler: function() {
                    if (borrarForm.form.isValid()) {
                            borrarForm.form.submit({          
                                waitMsg:CONFIGWORKFLOW_PROCESS_TITLE,
                                failure: function(form, action) {
                                    Ext.MessageBox.alert(CONFIGWORKFLOW_PS1_BORRAR_TITLE, CONFIGWORKFLOW_PS1_BORRAR_FAILURE);
                                    windowDel.hide();
                                },
                                success: function(form, action) {
                                    Ext.MessageBox.alert(CONFIGWORKFLOW_PS1_BORRAR_TITLE, CONFIGWORKFLOW_PS1_BORRAR_SUCCESS);
                                    windowDel.hide();
                                    grid2.destroy();
                                    createGrid();
                                    store.load();                                      
                                }
                            });                   
                    }else{
                        Ext.MessageBox.alert(CONFIGWORKFLOW_EXCEPTION_TITLE);
                    }             
                }
            },{
                text: CONFIGWORKFLOW_BTN_CANCELAR,
                handler: function(){windowDel.hide();}
            }]
        });
        
//******************************************
//Filtros de Busqueda
//******************************************
var nombreProceso = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_NOMBRE,
    name:'dsproceso',
    width: 200
});
var proceso = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_PROCESO,
    name:'dsprocesoflujo',
    width: 200
});
    var nombreCliente = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_CLIENTE,
    name:'dselemen',
    width: 200
});
    var nombreAseguradora = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_ASEGURADORA,
    name:'dsunieco',
    width: 200
});
    var nombreProducto = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_PRODUCTO,
    name:'dsramo',
    width: 200
});
    
var opcionesForm = new Ext.form.FormPanel({
    	el:'opcionesForm',
        title: '<span style="color:black;font-size:14px;">Workflow</span>',
        //iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',   
        frame:true,   
        url:'configworkflow/obtenerProcesos.action',                         
        width: 500,
        height:240,
        items: [{
                layout:'form',
                border: false,
                items:[{
                    title :'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">B&uacute;squeda</span>',
                    bodyStyle:'background: white',
                labelWidth: 150,             
                layout: 'form',
                frame:true,
                baseCls: '',
                buttonAlign: "center",
                        items: [{
                            layout:'column',
                            border:false,
                            columnWidth: 1,
                            items:[{
                                columnWidth:1,
                                layout: 'form',                     
                                border: false,                      
                                items:[ 
                                        nombreProceso,
                                        proceso,
                                        nombreCliente,
                                        nombreAseguradora,
                                        nombreProducto
                                    ]
                                },{
                                columnWidth:.4,
                                layout: 'form'
                                }]
                            }],
                            buttons:[{                                  
                                    text:CONFIGWORKFLOW_BTN_BUSCAR,  
                                    handler: function() {                                            
                                        if (opcionesForm.form.isValid()) {
                                                opcionesForm.form.submit({                 
                                                    waitMsg:CONFIGWORKFLOW_PROCESS_TITLE,
                                                    failure: function(form, action) {
                                                        Ext.MessageBox.alert(CONFIGWORKFLOW_LIST_TITLE,CONFIGWORKFLOW_BUSCAR_FAILURE);
                                                        grid2.destroy();
                                                        createGrid();
                                                        store.load();
                                                    },
                                                    success: function(form, action) {
                                                        grid2.destroy();
                                                        createGrid();
                                                        store.load();                           
                                                    }
                                                });                   
                                        } else{
                                            Ext.MessageBox.alert(CONFIGWORKFLOW_LIST_TITLE,CONFIGWORKFLOW_EXCEPTION_TITLE);
                                        }  
                                    }                                                       
                                    },{
                                    text:CONFIGWORKFLOW_BTN_CANCELAR,
                                    handler: function() {
                                        opcionesForm.form.reset();
                                    }
                                }]
                        }]
                }]  
        });
        
        opcionesForm.render();

function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
        }
//        var store;
        var cm = new Ext.grid.ColumnModel([
            new Ext.grid.RowNumberer(),
            {header: "cdprocxcta", dataIndex:'cdprocxcta',     width: 5, sortable:true, locked:false, id:'cdprocxcta', hidden:true},
            {header: "cdproceso",  dataIndex:'cdproceso',      width: 5, sortable:true, locked:false, id:'cdproceso', hidden:true},
            {header: "cdunieco",   dataIndex:'cdunieco',       width: 5, sortable:true, locked:false, id:'cdunieco', hidden:true},
            {header: "cdramo",     dataIndex:'cdramo',         width: 5, sortable:true, locked:false, id:'cdramo', hidden:true},
            {header: "cdelemento", dataIndex:'cdelemento',     width: 5, sortable:true, locked:false, id:'cdelemento', hidden:true},
            {header: "Nombre",     dataIndex:'dsproceso',      width: 150, sortable:true},
           	{header: "Proceso",    dataIndex:'dsprocesoflujo', width: 150, sortable:true},
          	{header: "Cliente",    dataIndex:'dselemen',       width: 150, sortable:true},
       		{header: "Aseguradora",dataIndex:'dsunieco',       width: 150, sortable:true},
          	{header: "Producto",   dataIndex:'dsramo',         width: 150, sortable:true}
        ]);
//******************************************
//Agregar
//******************************************

function agregar() {

	var nombreProceso = new Ext.form.TextField({
	    name:'dsproceso',
	    fieldLabel: CONFIGWORKFLOW_PS1_NOMBRE,
	    width: 200
	});
	
	var comboProceso = new Ext.form.ComboBox({
		 name: 'cdproceso',
		 fieldLabel: CONFIGWORKFLOW_PS1_PROCESO,
		 store: storeProceso,
		 displayField:'label',
		 hiddenName: 'cdproceso',
		 valueField:'value',
		 typeAhead: true,
		 mode: 'local',
		 allowBlank: false,
		 triggerAction: 'all',
		 width:200,
		 emptyText: CONFIGWORKFLOW_COMBO_EMPTY,
		 //editable:false,
		 labelSeparator:'',  
		 selectOnFocus:true
	});
	
	var comboCliente = new Ext.form.ComboBox({
	    name: 'cdelemento',
	    fieldLabel: CONFIGWORKFLOW_PS1_CLIENTE,
	    store: storeCliente,
	    allowBlank: false,
	    displayField:'dsCliente',
	    hiddenName: 'cdelemento',  
	    valueField:'cdCliente',
	    typeAhead: true,
	    mode: 'local',
	    triggerAction: 'all',
	    width:200,
	    emptyText: CONFIGWORKFLOW_COMBO_EMPTY,
	    //editable:false,
	    selectOnFocus:true
	});

	var storeAseguradora = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
		    url: 'configworkflow/obtenerComboAseguradora.action'
		}),
		reader: new Ext.data.JsonReader({
		root: 'aseguradorasList',
		id: 'value'
		    },[
		   {name: 'value', type: 'string',mapping:'value'},
		   {name: 'label', type: 'string',mapping:'label'}    
		    ]),
		    remoteSort: true,
		    baseParams: {cdelemento: comboCliente.getValue()}
		});
	storeAseguradora.setDefaultSort('value', 'desc');

	var comboAseguradora = new Ext.form.ComboBox({
		name: 'cdunieco',
	    fieldLabel: CONFIGWORKFLOW_PS1_ASEGURADORA,
	    store: storeAseguradora,
	    allowBlank: false,
	    displayField:'label',
	    hiddenName: 'cdunieco',
	    valueField:'value',
	    typeAhead: true,
	    mode: 'local',
	    triggerAction: 'all',
	    width:200,
	    emptyText: CONFIGWORKFLOW_COMBO_EMPTY,
	    //editable:false,
	    selectOnFocus:true
	});
        
	var storeProducto = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
		    url: 'configworkflow/obtenerComboProducto.action'
		}),
		reader: new Ext.data.JsonReader({
		root: 'productosList',
		id: 'value'
		    },[
		   {name: 'value', type: 'string',mapping:'value'},
		   {name: 'label', type: 'string',mapping:'label'}    
		    ]),
		    remoteSort: true,
		    baseParams: {cdelemento: comboCliente.getValue(), cdunieco: comboAseguradora.getValue()}
		});
	storeProducto.setDefaultSort('value', 'desc');

	var comboProducto = new Ext.form.ComboBox({
		name: 'cdramo',
		fieldLabel:CONFIGWORKFLOW_PS1_PRODUCTO,
	    store: storeProducto,
	    allowBlank: false,
	    displayField:'label',
	    hiddenName: 'cdramo',
	    valueField:'value',
	    typeAhead: true,
	    mode: 'local',
	    triggerAction: 'all',
	    width:200,
	    emptyText: CONFIGWORKFLOW_COMBO_EMPTY,
	    //editable:false,
	    selectOnFocus:true
	});

	comboCliente.on('select', function(){
	comboAseguradora.clearValue();
//	alert('Valor :'+comboCliente.getValue());
	storeAseguradora.baseParams['cdelemento'] = comboCliente.getValue();
		storeAseguradora.load({
	      callback : function(r, options, success) {
	          if (!success) {
	            //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
	                 storeAseguradora.removeAll();
	              }
	          }
		});
	});

	comboAseguradora.on('select', function(){
	comboProducto.clearValue();
//	alert('Valor :'+comboCliente.getValue());
//	alert('Valor :'+comboAseguradora.getValue());
	storeProducto.baseParams['cdelemento'] = comboCliente.getValue();
	storeProducto.baseParams['cdunieco'] = comboAseguradora.getValue();
		storeProducto.load({
	      callback : function(r, options, success) {
	          if (!success) {
	            //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
	                 storeProducto.removeAll();
	              }
	          }
		});
	});
    
	var formPanel = new Ext.form.FormPanel({
    url:'configworkflow/insertaProceso.action',
    baseCls: 'x-plain',
    labelWidth: 100,
    defaultType: 'textfield',
    labelAlign: 'right',
    items: [           
    		nombreProceso,
            comboProceso,
            comboCliente,
            comboAseguradora,
            comboProducto
        ]
    });

    var window = new Ext.Window({
        title: CONFIGWORKFLOW_PS1_AGREGAR_TITLE,
        plain:true,
        width: 380,
        height:240,
        minWidth: 380,
        minHeight: 240,
        layout: 'fit',
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        modal:true,
        items: formPanel,
        buttons: [{
            text: CONFIGWORKFLOW_BTN_GUARDAR, 
            handler: function() {                
                if (formPanel.form.isValid()) {
                    formPanel.form.submit({               
                        waitMsg: CONFIGWORKFLOW_PROCESS_TITLE,
                        failure: function(form, action) {
                                Ext.MessageBox.alert(CONFIGWORKFLOW_PS1_AGREGAR_TITLE,CONFIGWORKFLOW_PS1_AGREGAR_FAILURE);
                                window.hide();
                        },
                        success: function(form, action) {
                            Ext.MessageBox.alert(CONFIGWORKFLOW_PS1_AGREGAR_TITLE,CONFIGWORKFLOW_PS1_AGREGAR_SUCCESS);
                            window.hide();
							grid2.destroy();
							createGrid();
							store.load();
                        }
                    });                   
                } else{
                    Ext.MessageBox.alert(CONFIGWORKFLOW_EXCEPTION_TITLE);
                      }             
                }
        },{
        text: CONFIGWORKFLOW_BTN_CANCELAR,
        handler: function(){
                window.hide();
                }
        }]
    });

    window.show();	
}

//******************************************
//Opcion para configurar
//******************************************
	OpcionesVentana = function(storeCondiciones, rec, row) {
	    var cdprocxcta;
	
	    if(row!=null){
	    
	        var record = storeCondiciones.getAt(row);
	        cdprocxcta= record.get('cdprocxcta');

//	        alert(_ACTION_CONFIG);
	        window.location.href = _ACTION_CONFIG+'?cdprocxcta='+cdprocxcta;
//	        window.location.replace(_ACTION_CONFIG+'?cdprocxcta='+cdprocxcta);
	                        
	    }
	            
	}
    
//******************************************
//Crear Grid
//******************************************
var store;
var grid2;
var selectedId;
function createGrid(){
	
    grid2= new Ext.grid.GridPanel({
        store:createOptionGrid(),
        border:true,
        //baseCls:' background:white ',
        buttonAlign:'center',
        cm: cm,
        buttons:[{
                text: CONFIGWORKFLOW_BTN_AGREGAR,
                tooltip:'Agregar una nueva opcion',
                handler:function(){
                agregar();}
                },{
                text: CONFIGWORKFLOW_BTN_EDITAR,
                id:'editar',                
                tooltip:'Edita opcion seleccionada'                           
                },{
                text: CONFIGWORKFLOW_BTN_BORRAR,
                id:'borrar',
                tooltip:'Elimina opcion seleccionada'
                },{
                text: CONFIGWORKFLOW_BTN_CONFIGURAR,
                id:'configuraOpciones',
                tooltip:'Configura Opciones'
                }],                                                         
        width:500,
        frame:true,
        height:320,        
        title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">'+CONFIGWORKFLOW_LIST_TITLE+'</span>',
        //renderTo:document.body,
        
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
                        rowselect: function(sm, row, rec) { 
                                selectedId = store.data.items[row].id;                                                                                                                                    
                                Ext.getCmp('editar').on('click',function(){
                                    windowEdit.show();
                                    Ext.getCmp('editForm').getForm().loadRecord(rec);                                                                            
                                });
                                Ext.getCmp('borrar').on('click', function(){
                                	windowDel.show();
                                	Ext.getCmp('borrarForm').getForm().loadRecord(rec);
                                });
                                Ext.getCmp('configuraOpciones').on('click',function(){
                                    OpcionesVentana(store, rec, row);                                                                           
                                });
                        }
                }
        
        }),
        //viewConfig: {autoFill: true,forceFit:true},                
        bbar: new Ext.PagingToolbar({
            pageSize:20,
            store: store,                                               
            displayInfo: true,
			displayMsg: 'Registros mostrados {0} - {1} de {2}',
			emptyMsg: 'No hay registros para mostrar',
			beforePageText: 'P&aacute;gina',
			afterPageText: 'de {0}'                      
            })                                                                                               
        });     
    grid2.render('gridOpciones');
}

createGrid();
storeProceso.load();
storeCliente.load();
//storeAseguradora.load();
//storeProducto.load();
//storeGrid.load();
});