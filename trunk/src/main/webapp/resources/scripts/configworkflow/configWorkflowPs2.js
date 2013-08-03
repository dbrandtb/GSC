Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//******************************************
//Stores Grid
//******************************************
function createOptionGrid(){
url='configworkflow/obtenerPasos.action';	
    store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
        url: url
        }),
        reader: new Ext.data.JsonReader({
        root:'pasosXctaList',
        id:'procesoPasos',
        totalProperty: 'totalCount'             
        },[
                {name: 'cdprocxcta',	type: 'string', mapping:'cdprocxcta'},
                {name: 'cdpaso',		type: 'string', mapping:'cdpaso'},
                {name: 'cdestado',		type: 'string', mapping:'cdestado'},
                {name: 'cdpasoexito',	type: 'string', mapping:'cdpasoexito'},
                {name: 'cdpasofracaso', type: 'string', mapping:'cdpasofracaso'},
                {name: 'cdcondici',		type: 'string', mapping:'cdcondici'},
                {name: 'cdtitulo',		type: 'string', mapping:'cdtitulo'},
                {name: 'nmorden',		type: 'string', mapping:'nmorden'},
                {name: 'dspaso',		type: 'string', mapping:'dspaso'},
                {name: 'dspasoexito',   type: 'string', mapping:'dspasoexito'},
                {name: 'dspasofracaso',	type: 'string', mapping:'dspasofracaso'},
                {name: 'dstitulo',		type: 'string', mapping:'dstitulo'},
                {name: 'dspasoEdit',	type: 'string', mapping:'dspaso'},
                {name: 'swfinal',		type: 'string', mapping:'swfinal'}
            ]),
        remoteSort: true
    });

    store.setDefaultSort('procesoPasos', 'desc');
    store.load({params:{start:0, limit:20}});
    return store;
 }

 	var storePantallas = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
		    url: 'configworkflow/comboPantallas.action'
		}),
		reader: new Ext.data.JsonReader({
		root: 'pantallasList',
		id: 'pantallas'
		    },[
		   {name: 'value', type: 'string',mapping:'value'},
		   {name: 'label', type: 'string',mapping:'label'}    
		    ]),
		    remoteSort: true
		});
	storePantallas.setDefaultSort('pantallas', 'desc');
//******************************************
//Filtros de Busqueda
//******************************************
var idprocxcta = new Ext.form.Hidden({
    name:'cdprocxcta',
    valueField:PASOS_PROCESOID
});
var nombreProceso = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_NOMBRE,
    name:'dsproceso',
    value: PASOS_DSPROCESO,
    disabled:true,
    width: 200
});
var proceso = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_PROCESO,
    name: 'dsprocesoflujo',
    value: PASOS_DSPROCESOFLUJO,
    disabled:true,
    width: 200
});
    var nombreCliente = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_CLIENTE,
    name:'dselemen',
    value:PASOS_DSELEMEN,
    disabled:true,
    width: 200
});
    var nombreAseguradora = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_ASEGURADORA,
    name:'dsunieco',
    value:PASOS_DSUNIECO,
    disabled:true,
    width: 200
});
    var nombreProducto = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_PRODUCTO,
    name:'dsramo',
    value:PASOS_DSRAMO,
    disabled:true,
    width: 200
});


// opcionesForm, dentro del js, para pintar las variables de session en los campos
var opcionesForm = new Ext.form.FormPanel({
    	el:'opcionesForm',
        title: '<span style="color:black;font-size:14px;">Configurar Pasos Por Cuenta</span>',
        //iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: 'center',
        labelAlign: 'right',   
        frame:true,   
        url:'configworkflow/obtenerPasos.action',                         
        width: 550,
        height:200,
        items: [{
                layout:'form',
                border: false,
                items:[{
                    title :'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;"></span>',
                    bodyStyle:'background: white',
                labelWidth: 150,             
                layout: 'form',
                frame:true,
                baseCls: '',
                buttonAlign: 'center',
                        items: [{
                            layout:'column',
                            border:false,
                            columnWidth: 1,
                            items:[{
                                columnWidth:1,
                                layout: 'form',                     
                                border: false,                      
                                items:[ 
                                		idprocxcta,
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
                            }]
                        }]
                }]  
        });
        
        opcionesForm.render();

//******************************************
//Agregar
//******************************************

function agregar() {

	var nombrePaso = new Ext.form.TextField({
	    name:'dspaso',
	    fieldLabel: CONFIGWORKFLOW_PS2_PASO,
	    width: 200
	});

	var comboPantallas = new Ext.form.ComboBox({
		name: 'cdtitulo',
	    fieldLabel: CONFIGWORKFLOW_PS2_PANTALLA,
	    store: storePantallas,
	    allowBlank: false,
	    displayField:'label',
	    hiddenName: 'cdtitulo',
	    valueField:'value',
	    typeAhead: true,
	    mode: 'local',
	    triggerAction: 'all',
	    width:200,
	    emptyText: CONFIGWORKFLOW_COMBO_EMPTY,
	    //editable:false,
	    selectOnFocus:true
	});
        
    
	var formPanel = new Ext.form.FormPanel({
    url:'configworkflow/insertaPasos.action',
    baseCls: 'x-plain',
    labelWidth: 100,
    defaultType: 'textfield',
    labelAlign: 'right',
    items: [           
    		nombrePaso,
            comboPantallas
        ]
    });

    var window = new Ext.Window({
        title: CONFIGWORKFLOW_PS2_AGREGAR_TITLE,
        plain:true,
        width: 380,
        height:150,
        minWidth: 380,
        minHeight: 150,
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
                        waitMsg:CONFIGWORKFLOW_PROCESS_TITLE,
                        failure: function(form, action) {
                                Ext.MessageBox.alert(CONFIGWORKFLOW_PS2_AGREGAR_TITLE,CONFIGWORKFLOW_PS2_AGREGAR_FAILURE);
                                window.hide();
                        },
                        success: function(form, action) {
                            Ext.MessageBox.alert(CONFIGWORKFLOW_PS2_AGREGAR_TITLE,CONFIGWORKFLOW_PS2_AGREGAR_SUCCESS);
                            window.hide();
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
        handler: function(){
                window.hide();
                }
        }]
    });

    window.show();	
}


//******************************************
//Editar
//******************************************

	var idprocxcta = new Ext.form.Hidden({
        fieldLabel: '',   
        name:'cdprocxcta',
        valueField:'cdprocxcta'
    });
    
	var idpaso = new Ext.form.Hidden({
        fieldLabel: '',   
        name:'cdpaso',
        valueField:'cdpaso'
    });
    
	var descPaso = new Ext.form.Hidden({
        fieldLabel: '',   
        name:'dspaso',
        valueField: 'dspaso'
    });
    
	var idpasoExito = new Ext.form.Hidden({
        fieldLabel: '',   
        name:'cdpasoexito',
        valueField:'cdpasoexito'
    });
    
	var idpasoFracaso = new Ext.form.Hidden({
        fieldLabel: '',   
        name:'cdpasofracaso',
        valueField:'cdpasofracaso'
    });
    
	var nombrePaso = new Ext.form.TextField({
	    fieldLabel: CONFIGWORKFLOW_PS2_PASO,
	    name:'dspasoEdit',	    
	    width: 200,
	    disabled:true
	});

	var comboPantallas = new Ext.form.ComboBox({
		name: 'cdtitulo',
	    fieldLabel: CONFIGWORKFLOW_PS2_PANTALLA,
	    store: storePantallas,
	    allowBlank: false,
	    displayField:'label',
	    hiddenName: 'cdtitulo',
	    valueField:'value',
	    typeAhead: true,
	    mode: 'local',
	    triggerAction: 'all',
	    width:200,
	    emptyText: CONFIGWORKFLOW_COMBO_EMPTY,
	    //editable:false,
	    selectOnFocus:true
	});
    
	var editForm = new Ext.form.FormPanel({
    id:'editForm',
    url:'configworkflow/editaPasos.action',
    baseCls: 'x-plain',
    labelWidth: 100,
    defaultType: 'textfield',
    labelAlign: 'right',
    items: [   
    		idprocxcta,
    		idpaso,
    		idpasoExito,
    		idpasoFracaso,
            nombrePaso,
            descPaso,
            comboPantallas
           ]
    });

    var windowEdit = new Ext.Window({
        title: CONFIGWORKFLOW_PS2_EDITAR_TITLE,
        plain:true,
        width: 380,
        height:150,
        minWidth: 380,
        minHeight: 150,
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
                                Ext.MessageBox.alert(CONFIGWORKFLOW_PS2_EDITAR_TITLE,CONFIGWORKFLOW_PS2_EDITAR_FAILURE);
                                windowEdit.hide();
                        },
                        success: function(form, action) {
                            Ext.MessageBox.alert(CONFIGWORKFLOW_PS2_EDITAR_TITLE,CONFIGWORKFLOW_PS2_EDITAR_SUCCESS);
                            windowEdit.hide();
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
        handler: function(){
                windowEdit.hide();
                }
        }]
    });
    

//******************************************
//Borrar
//******************************************
    var msgBorrar = new Ext.form.TextField({
        fieldLabel:CONFIGWORKFLOW_PS2_BORRAR_MSG,
        labelSeparator:'',
        anchor:'90%',
        hidden:true
    });                   
   
	var idprocxcta = new Ext.form.Hidden({
        name:'cdprocxcta',
        valueField:'cdprocxcta'
    });
    
	var idpaso = new Ext.form.Hidden({
        name:'cdpaso',
        valueField:'cdpaso'
    });
    
    var borrarForm= new Ext.FormPanel({
        id:'borrarForm',
        labelAlign:'top',
        baseCls: 'x-plain',
        anchor:'100%',
        url:'configworkflow/borraPasos.action',
        items:[ 
        		msgBorrar,
        		idprocxcta,
        		idpaso
        ]
    });

    var windowDel = new Ext.Window({
        title: CONFIGWORKFLOW_PS2_BORRAR_TITLE,
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
                                    Ext.MessageBox.alert(CONFIGWORKFLOW_PS2_BORRAR_TITLE,CONFIGWORKFLOW_PS2_BORRAR_FAILURE);
                                    windowDel.hide();
                                },
                                success: function(form, action) {
                                    Ext.MessageBox.alert(CONFIGWORKFLOW_PS2_BORRAR_TITLE,CONFIGWORKFLOW_PS2_BORRAR_SUCCESS);
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
//Opcion para configurar
//******************************************
	OpcionesVentana = function(storeCondiciones, rec, row) {

		var cdprocxcta;
		var cdpaso;
		var dspaso;
		var cdpasoexito;
		var dspasoexito;
		var cdtitulo;
		var dsFinal;
		var cdpasofracaso;
		var dspasofracaso;
	    
	    if(row!=null){

	        var record = storeCondiciones.getAt(row);
	        cdprocxcta = record.get('cdprocxcta');
	        cdpaso = record.get('cdpaso');
	        cdtitulo = record.get('cdtitulo');
	        dspaso = record.get('dspaso');
	        cdpasoexito = record.get('cdpasoexito');
   			dspasoexito = record.get('dspasoexito');
   			cdpasofracaso = record.get('cdpasofracaso');
   			dspasofracaso = record.get('dspasofracaso');
   			dsFinal = record.get('swfinal');
//			alert(_ACTION_OPCION);
	        window.location.href =_ACTION_OPCION +'?cdprocxcta='+cdprocxcta+'&cdpaso='+cdpaso+'&cdtitulo='+cdtitulo+'&dspaso='+dspaso+'&cdpasoexito='+cdpasoexito+'&cdpasofracaso='+cdpasofracaso+'&dspasoexito='+dspasoexito+'&dspasofracaso='+dspasofracaso+'&swfinal='+dsFinal;
	    }
	}

//******************************************
//Opcion para vista Completa
//******************************************
	vistaCompleta = function(store) {

		var cdprocxcta;
		
		if(store!=null){
			var record = store.getAt(0);				
			cdprocxcta = record.get('cdprocxcta');

//			alert(_ACTION_VISTAC);
	        window.location.href = _ACTION_VISTAC+'?cdprocxcta='+cdprocxcta;
//	        window.location.replace(_ACTION_VISTAC);
//	        document.forms[0].action = url; 
//	        document.forms[0].submit(); 
		}
	}
	

//******************************************
//Regresa a la pantalla principal
//******************************************
	regresar = function() {

			//alert(_ACTION_VISTAC);
	        window.location.href = _ACTION_HOME;
			//window.location.replace(_ACTION_VISTAC);
	}
	
	
function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
        }

        var cm = new Ext.grid.ColumnModel([
            new Ext.grid.RowNumberer(),
            {header: "cdprocxcta", dataIndex:'cdprocxcta', width: 5, sortable:true, locked:false, id:'cdprocxcta', hidden:true},
            {header: "cdpaso",     dataIndex:'cdpaso',     width: 5, sortable:true, locked:false, id:'cdpaso', hidden:true},
            {header: "cdtitulo",   dataIndex:'cdtitulo',   width: 5, sortable:true, locked:false, id:'cdtitulo', hidden:true},
            {header: "dspasoEdit", dataIndex:'dspaso',     width: 5, sortable:true, locked:false, id:'dspasoEdit', hidden:true},
            {header: "cdpasoexito", dataIndex:'cdpasoexito', width: 5, sortable:true, locked:false, id:'cdpasoexito', hidden:true},
           	{header: "dspasoexito", dataIndex:'dspasoexito', width: 5, sortable:true, locked:false, id:'dspasoexito', hidden:true},
            {header: "cdpasofracaso", dataIndex:'cdpasofracaso',  width: 5, sortable:true, locked:false, id:'cdpasofracaso', hidden:true},
            {header: "Paso",       dataIndex:'dspaso',     width: 260, sortable:true},
           	{header: "Pantalla",   dataIndex:'dstitulo',   width: 260, sortable:true}
        ]);

        
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
        buttonAlign: 'center',
        cm: cm,
        buttons:[
                {text:CONFIGWORKFLOW_BTN_AGREGAR,
                tooltip:'Agregar una nueva opcion',
                handler:function(){
                agregar();}
                },{
                text:CONFIGWORKFLOW_BTN_EDITAR,
                id:'editar',                
                tooltip:'Edita opcion seleccionada'                           
                },{
                text:CONFIGWORKFLOW_BTN_BORRAR,
                id:'borrar',
                tooltip:'Elimina opcion seleccionada'
                },{
                text:CONFIGWORKFLOW_BTN_CONFIGURAR,
                id:'configuraOpciones',
                tooltip:'Configura Opciones'    	
            	},{
                text:CONFIGWORKFLOW_VISTA_COMPLETA,
                handler:function(){
                	vistaCompleta(store);}
            	},{
                text:CONFIGWORKFLOW_BTN_REGRESAR,
                handler:function(){
                	regresar();}
            	}],                                                         
        width:550,
        frame:true,
        height:320,        
        title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
        
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
storePantallas.load();
});