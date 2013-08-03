Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";


/*************************************************************
** Store combos
**************************************************************/ 

var storeProceso = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'configworkflow/obtenerListasConfPasosPantallas.action'
            }),
            reader: new Ext.data.JsonReader({
            root: 'procesoList',
            id: 'value'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}  
             ]),
            remoteSort: true
        });
        storeProceso.setDefaultSort('value', 'desc');
        storeProceso.load();

    var storeCliente = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'configworkflow/obtenerListasConfPasosPantallas.action'
            }),
            reader: new Ext.data.JsonReader({
            root: 'clienteList',
            id: 'cdCliente'
            },[
           {name: 'cdCliente', type: 'string',mapping:'cdCliente'},
           {name: 'dsCliente', type: 'string',mapping:'dsCliente'}    
            ]),
            remoteSort: true
        });
        storeCliente.setDefaultSort('cdCliente', 'desc');
        storeCliente.load();

    var storeAseguradora = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'configworkflow/getComboAseguradora.action'
            }),
            reader: new Ext.data.JsonReader({
            root: 'aseguradorasList',
            id: 'value'
            },[
           {name: 'value', type: 'string',mapping:'value'},
           {name: 'label', type: 'string',mapping:'label'}    
            ]),
            remoteSort: true
        });
        storeAseguradora.setDefaultSort('value', 'desc');
        storeAseguradora.load();
        
    var storeProducto = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'configworkflow/getComboProducto.action'
            }),
            reader: new Ext.data.JsonReader({
            root: 'productosList',
            id: 'value'
            },[
           {name: 'value', type: 'string',mapping:'value'},
           {name: 'label', type: 'string',mapping:'label'}    
            ]),
            remoteSort: true
        });
        storeProducto.setDefaultSort('value', 'desc');
        storeProducto.load();


/*************************************************************
** Store grid
**************************************************************/
var storeGrid;
function test(){
url='configuradorpantallas/pasosPantalla.action'+'?nombreConjunto='+nombreConjunto.getValue()+'&proceso='+comboProceso.getValue()+'&cliente='+comboCliente.getValue()+'&aseguradora='+comboAseguradora.getValue();	
storeGrid = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
           url: url
          
        }),
        reader: new Ext.data.JsonReader({
            root: 'pagedDataList',
            totalProperty: 'totalCount',
            id: 'cdConjunto'
            },[
                {name: 'cdConjunto',  type: 'string',  mapping:'cdConjunto'},
                {name: 'proceso',  type: 'string',  mapping:'proceso'},
                {name: 'cliente',  type: 'string',  mapping:'cliente'},
                {name: 'aseguradora',  type: 'string',  mapping:'aseguradora'}
            ]),
        remoteSort: true
    });

    storeGrid.setDefaultSort('cdConjunto', 'desc');
    storeGrid.load({params:{start:0, limit:20}});
    return storeGrid;
 }
   
   
var nombreConjunto = new Ext.form.TextField({
    fieldLabel: CONFIG_WORKFLOW_FORM_1_NOMBRE,
    name:'nombreConjunto',
    width:270
});



 var comboProceso = new Ext.form.ComboBox({
     name: 'proceso',
     id: 'idProceso',
     fieldLabel: CONFIG_WORKFLOW_FORM_1_PROCESO,
     store: storeProceso,
     displayField:'label',
     hiddenName: 'proceso',  
     valueField:'value',
     typeAhead: true,
     mode: 'local',
     allowBlank: false,
     triggerAction: 'all',
     width:270,
     emptyText: CONFIG_WORKFLOW_FORM_1_PROCESO_EMPTY,
     //editable:false,
     labelSeparator:'',  
     selectOnFocus:true
});


var comboCliente = new Ext.form.ComboBox({
    name: 'cliente',
    id: 'idCliente',
    fieldLabel: CONFIG_WORKFLOW_FORM_1_CLIENTE,
    store: storeCliente,
    allowBlank: false,
    displayField:'dsCliente',
    hiddenName: 'cliente',  
    valueField:'cdCliente',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    width:270,
    emptyText: CONFIG_WORKFLOW_FORM_1_CLIENTE_EMPTY,
    //editable:false,
    selectOnFocus:true
});

var comboAseguradora = new Ext.form.ComboBox({
    name: 'aseguradora',
    id: 'idAseguradora',
    fieldLabel: CONFIG_WORKFLOW_FORM_1_ASEGURADORA,
    store: storeAseguradora,
    allowBlank: false,
    displayField:'label',
    hiddenName: 'aseguradora',  
    valueField:'value',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    width:270,
    emptyText: CONFIG_WORKFLOW_FORM_1_ASEGURADORA_EMPTY,
    //editable:false,
    selectOnFocus:true
});

var comboProducto = new Ext.form.ComboBox({
    name: 'producto',
    id: 'idProducto',
    store: storeProducto,
    allowBlank: false,
    displayField:'label',
    hiddenName: 'producto',  
    valueField:'value',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    width:270,
    //editable:false,
    selectOnFocus:true
});

var cdConjunto = new Ext.form.TextField({
    name:'cdConjunto',
    type:'hidden',
    labelSeparator:'',
    hidden:true
 });


/*************************************************************
** Forma de busqueda
**************************************************************/ 
   var filterAreaForm = new Ext.form.FormPanel({  
        //url:'confpantallas/buscaConjuntos.action',
   	url:'configuradorpantallas/pasosPantalla.action',
        layout:'column',
        id:'filterArea',
        border:false,
        items: [{
               
                xtype:'fieldset',
                style:'margin:5px',
                bodyStyle:'padding:5px',
                title: '<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">' + CONFIG_WORKFLOW_FORM_1_FIELDSET_TITLE + '</span>',  
                autoHeight:true, 
                width:410, 
                defaultType: 'textfield',  
               items :[nombreConjunto,
                       comboProceso,
                       comboCliente,
                       comboAseguradora,
//                       comboProducto,
                       cdConjunto 
                                        
                      ],////end items FieldSet
                  buttons:[{
                        text: CONFIG_WORKFLOW_FORM_1_BOTON_BUSCAR,  
                        handler: function() {                                            
                                if (filterAreaForm.form.isValid()) {
                                         filterAreaForm.form.submit({                 
                                        waitMsg: CONFIG_WORKFLOW_FORM_1_BOTON_BUSCAR_WAIT_MSG,
                                         
                                             failure: function(form, action) {
                                                    Ext.MessageBox.alert(CONFIG_WORKFLOW_FORM_1_BOTON_BUSCAR_FAILURE, CONFIG_WORKFLOW_FORM_1_BOTON_BUSCAR_FAILURE_DESC);
                                                    
                                                   },
                                             success: function(form, action) {
                                                    gridConjuntos.destroy();
                                                    createGrid();
                                                    storeGrid.reload();
                                               
                                                  }
                                        });                   
                                 } else{
                                            Ext.MessageBox.alert(CONFIG_WORKFLOW_FORM_1_BOTON_BUSCAR_ERROR, CONFIG_WORKFLOW_FORM_1_BOTON_BUSCAR_ERROR_DESC);
                                        }  
                              }      //end handler                                                 
                         },{
                         text: CONFIG_WORKFLOW_FORM_1_BOTON_LIMPIAR,
                             handler: function() {
                                     filterAreaForm.form.reset();
                                    }
                        }]//end buttons FieldSet
              }]//items panelForm
              
    });

function submitForm(){
        cargaForm.getForm().submit();
    }


/*************************************************************
** Paneles
**************************************************************/ 

    var parametros = new Ext.Panel({
        region: 'north',
        title: CONFIG_WORKFLOW_PANEL_1_TITLE,
        iconCls:'logo',
        split:true,
        height:280,
        id:'filterArea',
        bodyStyle:'padding:5px',
        collapsible: true,
        layoutConfig:{
             animate:true
        },
        items: [filterAreaForm]      
    });

parametros.render('pantalla');


var cm = new Ext.grid.ColumnModel([
            new Ext.grid.RowNumberer(),
            {header: CONFIG_WORKFLOW_GRID_1_HEADER_CLAVE,      dataIndex:'cdConjunto',  width: 120, sortable:true,  locked:false,   id:'cdConjunto', hidden:true},          
            {header: CONFIG_WORKFLOW_GRID_1_HEADER_PROCESO,       dataIndex:'proceso',  width: 150, sortable:true},
            {header: CONFIG_WORKFLOW_GRID_1_HEADER_CLIENTE,       dataIndex:'cliente',  width: 150, sortable:true}
                                
        ]);


////// crear el Grid

var gridConjuntos;
 var selectedId;
function createGrid(){
    gridConjuntos = new Ext.grid.GridPanel({
        store: test(),
        title:'<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">' + CONFIG_WORKFLOW_GRID_1_TITLE + '</span>',
        cls: 'alinear',
        cm: cm,
        stripeRows: true,
        height:250,
        width:500,
        // renderTo:document.body,
        viewConfig: {autoFill: true,forceFit:true},
        
        sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {      
        	target:'_top',
            rowselect: function(sm, row, rec) { 
            selectedId = storeGrid.data.items[row].id;
            // Ext.MessageBox.alert('selectedID', selectedId);
            cdConjunto.setValue(selectedId);
            //window.location.href = '/catweb-configuracion/configworkflow/entrarConfigurador.action'+'?id='+cdConjunto.getValue();
            alert('Redirigir');
                    }
                    }
        }),
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: storeGrid,
            displayInfo: true,
            displayMsg: CONFIG_WORKFLOW_GRID_1_HEADER_DISPLAY_MSG,
            emptyMsg: CONFIG_WORKFLOW_GRID_1_HEADER_EMPTY_MSG
                  })
       
    });


gridConjuntos.render('pantalla');
 
}
createGrid();
storeGrid.load();

});
                              