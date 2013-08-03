Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";


/*************************************************************
** Store combos
**************************************************************/ 

var storeProceso = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'confpantallas/obtenerListasConfPantallas.action'
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
                url: 'confpantallas/obtenerListasConfPantallas.action'
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



/*************************************************************
** Store grid
**************************************************************/
var storeGrid;
function test(){
url='confpantallas/buscaConjuntos.action'+'?nombreConjunto='+nombreConjunto.getValue()+'&proceso='+comboProceso.getValue()+'&cliente='+comboCliente.getValue();
storeGrid = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
           url: url
          
        }),
        reader: new Ext.data.JsonReader({
            root: 'resultadoGrid',
            totalProperty: 'totalCount',
            id: 'cdConjunto'
            },[
                {name: 'cdConjunto',  type: 'string',  mapping:'cdConjunto'},
                {name: 'nombreConjunto',  type: 'string',  mapping:'nombreConjunto'},
                {name: 'descripcion',  type: 'string',  mapping:'descripcion'},
                {name: 'proceso',  type: 'string',  mapping:'proceso'},
                {name: 'cliente',  type: 'string',  mapping:'cliente'}
            ]),
        remoteSort: true
    });

    storeGrid.setDefaultSort('cdConjunto', 'desc');
    storeGrid.load();
    return storeGrid;
 }
   
   
var nombreConjunto = new Ext.form.TextField({
    fieldLabel: 'Nombre Conjunto de Pantallas',
    name:'nombreConjunto',
    width:270
});



 var comboProceso = new Ext.form.ComboBox({
     name: 'proceso',
     id: 'idProceso',
     fieldLabel: 'Proceso',
     store: storeProceso,
     displayField:'label',
     hiddenName: 'proceso',  
     valueField:'value',
     typeAhead: true,
     mode: 'local',
     allowBlank: false,
     triggerAction: 'all',
     width:270,
     emptyText:'Seleccione...',
     //editable:false,
     labelSeparator:'',  
     selectOnFocus:true
});


var comboCliente = new Ext.form.ComboBox({
    name: 'cliente',
    id: 'idCliente',
    fieldLabel: 'Cliente',
    store: storeCliente,
    allowBlank: false,
    displayField:'dsCliente',
    hiddenName: 'cliente',  
    valueField:'cdCliente',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    width:270,
    emptyText:'Seleccione...',
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
        url:'confpantallas/buscaConjuntos.action',                        
        layout:'column',
        id:'filterArea',
        border:false,
        items: [{
               
                xtype:'fieldset',
                style:'margin:5px',
                bodyStyle:'padding:5px',
                title: '<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Area de Filtros de Busqueda</span>',  
                autoHeight:true, 
                width:400, 
                defaultType: 'textfield',  
               items :[nombreConjunto,
                       comboProceso,
                       comboCliente,
                       cdConjunto 
                                        
                      ],////end items FieldSet
                  buttons:[{                                  
                        text:'Buscar',  
                        handler: function() {                                            
                                if (filterAreaForm.form.isValid()) {
                                         filterAreaForm.form.submit({                 
                                        waitMsg:'Procesando...',
                                         
                                             failure: function(form, action) {
                                                    Ext.MessageBox.alert('Busqueda Finalizada', 'No se encontró registros');
                                                    
                                                   },
                                             success: function(form, action) {
                                                    gridConjuntos.destroy();
                                                    createGrid();
                                                    storeGrid.reload();
                                               
                                                  }
                                        });                   
                                 } else{
                                            Ext.MessageBox.alert('Error', 'Se produjo error');
                                        }  
                              }      //end handler                                                 
                         },{
                         text:'Limpiar Campos',
                             handler: function() {
                                     filterAreaForm.form.reset();
                                    }
                        },{
                         text:'Crear nuevo Conjunto de Pantalla',
                        
                         handler: function() { 
                                 window.location  = '/AON/confalfa/entrarConfigurador.action'+'?id='+cdConjunto.getValue();
                                                   
                      }      //end handler     
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
        title: 'PARAMETROS DE ENTRADA',
        iconCls:'logo',
        split:true,
        height:230,
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
            {header: "ClaveConjunto",      dataIndex:'cdConjunto',  width: 120, sortable:true,  locked:false,   id:'cdConjunto', hidden:true},          
            {header: "Nombre del conjunto",   dataIndex:'nombreConjunto',  width: 150, sortable:true},
            {header: "Descripción",dataIndex:'descripcion',  width: 150, sortable:true},
            {header: "Proceso",       dataIndex:'proceso',  width: 150, sortable:true},
            {header: "Cliente",       dataIndex:'cliente',  width: 150, sortable:true}
                                
        ]);


////// crear el Grid

var gridConjuntos;
 var selectedId;
function createGrid(){
    gridConjuntos = new Ext.grid.GridPanel({
        store: test(),
        title:'<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Busqueda de Registros</span>',
        cls: 'alinear',
        cm: cm,
        stripeRows: true,
        height:250,
        width:600,
        //renderTo:document.body,
        viewConfig: {autoFill: true,forceFit:true},
        
        sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
            rowselect: function(sm, row, rec) { 
             selectedId = storeGrid.data.items[row].id;
            // Ext.MessageBox.alert('selectedID', selectedId);
            cdConjunto.setValue(selectedId);
            window.location.href = '/AON/confalfa/entrarConfigurador.action'+'?id='+cdConjunto.getValue();
                    }
                    }
        }),
        bbar: new Ext.PagingToolbar({
            pageSize: 25,
            store: storeGrid,
            displayInfo: true,
            displayMsg: 'Displaying rows {0} - {1} of {2}',
            emptyMsg: "No rows to display"
                  })
       
    });
    gridConjuntos.render('pantalla');
}
createGrid();
storeGrid.load();

});
                              