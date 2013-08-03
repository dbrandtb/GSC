Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//******************************************
//Stores Grid
//******************************************
function createOptionGrid(){
url='configworkflow/vistaCompletaWF.action';	
    store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
        url: url
        }),
        reader: new Ext.data.JsonReader({
        root:'pasosXctaList',
        id:'procesoPasos',
        totalProperty: 'totalCount'             
        },[
                {name: 'cdprocxcta',   type: 'string', mapping:'cdprocxcta'},
                {name: 'cdpaso',  type: 'string', mapping:'cdpaso'},
                {name: 'cdestado',     type: 'string', mapping:'cdestado'},
                {name: 'cdpasoexito', type: 'string', mapping:'cdpasoexito'},
                {name: 'cdpasofracaso',  type: 'string', mapping:'cdpasofracaso'},
                {name: 'cdcondici', type: 'string', mapping:'cdcondici'},
                {name: 'cdtitulo',   type: 'string', mapping:'cdtitulo'},
                {name: 'nmorden',     type: 'string', mapping:'nmorden'},
                {name: 'dspaso',   type: 'string', mapping:'dspaso'},
                {name: 'dspasoexito',   type: 'string', mapping:'dspasoexito'},
                {name: 'dspasofracaso',   type: 'string', mapping:'dspasofracaso'},
                {name: 'dstitulo',   type: 'string', mapping:'dstitulo'},
                {name: 'dspasoEdit',   type: 'string', mapping:'dspaso'}
            ]),
        remoteSort: true
    });

    store.setDefaultSort('procesoPasos', 'desc');
    store.load({params:{start:0, limit:20}});
    return store;
 }

//******************************************
//Filtros de Busqueda
//******************************************
var idprocxcta = new Ext.form.Hidden({
    name:'cdprocxcta',
    value:_CDPROCESO
});
var nombreProceso = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_NOMBRE,
//    name:'parameters.dsproceso',
    name:'dsproceso',
    value:_DSPROCESO,
    disabled:true,
    width: 200
});
var proceso = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_PROCESO,
//    name:'parameters.dsprocesoflujo',
    name:'dsprocesoflujo',
    value:_DSPROCESOFLUJO,
    disabled:true,
    width: 200
});
    var nombreCliente = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_CLIENTE,
//    name:'parameters.dselemen',
    name:'dselemen',
    value:_DSELEMEN,
    disabled:true,
    width: 200
});
    var nombreAseguradora = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_ASEGURADORA,
//	name:'parameters.dsunieco',
    name:'dsunieco',
    value:_DSUNIECO,
    disabled:true,
    width: 200
});
    var nombreProducto = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_PRODUCTO,
//	name:'parameters.dsramo',
    name:'dsramo',
    value:_DSRAMO,
    disabled:true,
    width: 200
});


// opcionesForm, dentro del js, para pintar las variables de session en los campos
var opcionesForm = new Ext.form.FormPanel({
    	el:'opcionesForm',
        title: '<span style="color:black;font-size:14px;">Configurar Pasos Por Cuenta</span>',
        //iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',   
        frame:true,   
        url:'configworkflow/obtenerPasos.action',                         
        width: 500,
        height:190,
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

/***************************************
*Se crea la ventana de agregar opciones.  
****************************************/
        
    agregar = function() {

var nombreProceso = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_NOMBRE,
    name:'parameters.dsproceso',
//    name:'dsproceso',
    value:_DSPROCESO,
//    disabled:true,
    width: 200
});
var proceso = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_PROCESO,
    name:'parameters.dsprocesoflujo',
//    name:'dsprocesoflujo',
    value:_DSPROCESOFLUJO,
//    disabled:true,
    width: 200
});
    var nombreCliente = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_CLIENTE,
    name:'parameters.dselemen',
//    name:'dselemen',
    value:_DSELEMEN,
//    disabled:true,
    width: 200
});
    var nombreAseguradora = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_ASEGURADORA,
	name:'parameters.dsunieco',
//    name:'dsunieco',
    value:_DSUNIECO,
//    disabled:true,
    width: 200
});
    var nombreProducto = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS1_PRODUCTO,
	name:'parameters.dsramo',
//    name:'dsramo',
    value:_DSRAMO,
//    disabled:true,
    width: 200
});
    
    var formPanel = new Ext.form.FormPanel({
    url:'configworkflow/dummy.action',
    baseCls: 'x-plain',
    labelWidth: 100,
    defaultType: 'textfield',
    labelAlign: 'right',
    items: [            
            nombreProceso,
            proceso,
            nombreCliente,
            nombreAseguradora,
            nombreProducto
        ]
    });

    var window = new Ext.Window({
        title: 'Agregar Opciones',
        plain:true,
        width: 350,
        height:200,
        minWidth: 350,
        minHeight: 200,
        layout: 'fit',
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        modal:true,
        items: formPanel,
        buttons: [{
            text: 'Guardar', 
            handler: function() {                
                if (formPanel.form.isValid()) {
                    formPanel.form.submit({               
                        waitMsg:'Procesando...',
                        failure: function(form, action) {
                                Ext.MessageBox.alert('Error', 'No se pudo agregar la opcion');
                                window.hide();
                        },
                        success: function(form, action) {
                            Ext.MessageBox.alert('Opcion Creada', 'Se agrego la opcion');
                            window.hide();
							grid2.destroy();
							createGrid();
							store.load();
                        }
                    });                   
                } else{
                    Ext.MessageBox.alert('Error', 'Por favor revise los errores!');
                      }             
                }
        },{
        text: 'Regresar',
        handler: function(){
                window.hide();
                }
        }]
    });

    window.show();
    };
//******************************************
//Opcion para configurar
//******************************************
	OpcionesVentana = function(store) {
		var cdprocxcta;
		
		if(store!=null){
			var record = store.getAt(0);			
			cdprocxcta = record.get('cdprocxcta');
		
//	        alert(_ACTION_CONFIG);
	        window.location.href = _ACTION_CONFIG+'?cdprocxcta='+cdprocxcta;
//			window.location.href = 'configworkflow/dummy.action';
		}
	}
	
function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
        }

        var cm = new Ext.grid.ColumnModel([
            new Ext.grid.RowNumberer(),
            {header: CONFIGWORKFLOW_PS2_PASO,         dataIndex:'dspaso',        width: 150, sortable:true},
           	{header: CONFIGWORKFLOW_PS2_PANTALLA,     dataIndex:'dstitulo',      width: 150, sortable:true},
           	{header: CONFIGWORKFLOW_PS3_PASO_EXITO,   dataIndex:'dspasoexito',   width: 150, sortable:true},
           	{header: CONFIGWORKFLOW_PS3_PASO_FRACASO, dataIndex:'dspasofracaso', width: 150, sortable:true}
        ]);

//******************************************
        
        //******************************************
//Agregar
//******************************************

function crearXml() {

	var msgCorroborar = new Ext.form.TextField({
	    fieldLabel:CONFIGWORKFLOW_CREAR_XML,
	    labelSeparator:'',
	    anchor:'90%',
	    hidden:true
	});
    
	var formWorkflow = new Ext.form.FormPanel({
	    url:_ACTION_WF_CREATE_XML,
	    baseCls: 'x-plain',
	    labelWidth: 180,
	    defaultType: 'textfield',
	    labelAlign: 'center',
	        items:[ 
	        		msgCorroborar
	        ]
	});
	
    var window = new Ext.Window({
        title: CONFIGWORKFLOW_CREAR_XML_TITLE,
        plain:true,
        width: 240,
        height:150,
        minWidth: 220,
        minHeight: 130,
        layout: 'fit',
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        modal:true,
        items: formWorkflow,
        buttons: [{
            text: CONFIGWORKFLOW_BTN_CREAR_XML, 
            handler: function() {                
                if (formWorkflow.form.isValid()) {
                    formWorkflow.form.submit({               
                        waitMsg: CONFIGWORKFLOW_PROCESS_TITLE,
                        failure: function(form, action) {
                                Ext.MessageBox.alert('Error', CONFIGWORKFLOW_MSG_ERROR);
                                window.hide();
                        },
                        success: function(form, action) {
                            Ext.MessageBox.alert('Exito', CONFIGWORKFLOW_MSG_EXITO);
                            window.hide();
                        }
                    });                   
                } else{
                    Ext.MessageBox.alert('Informacion incompleta', 'Por favor verifique');
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
        buttons:[/*{
                text: CONFIGWORKFLOW_BTN_CREAR_XML,
                tooltip:'Crear XML de Workflow',
                handler:function(){
                	crearXml();}
                },*/{
                text:CONFIGWORKFLOW_BTN_REGRESAR,
	                handler: function(){
						OpcionesVentana(store);
					}
            	},{
                text:CONFIGWORKFLOW_BTN_EXPORTAR,
                tooltip:'Exporta opciones',
                handler: exportButton(_ACTION_WF_EXPORT)
            	}
                ],                                                         
        width:500,
        frame:true,
        height:320,        
        title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Pasos</span>',
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
});