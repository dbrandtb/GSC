<script type="text/javascript">

Ext.onReady(function(){

    Ext.QuickTips.init();
    Ext.QuickTips.enable();
    
    // turn on validation errors beside the field globally
    Ext.form.Field.prototype.msgTarget = 'side';
   
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    var storeAseguradoras = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'procesoemision/obtenerAseguradoras.action'
            }),
            reader: new Ext.data.JsonReader({
            root: 'listAseguradoras',
            id: 'value'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}  
             ]),
            remoteSort: true
        });
        storeAseguradoras.setDefaultSort('value', 'desc');
        storeAseguradoras.load();
        
        var storeProducto = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'procesoemision/obtenerProductos.action'
            }),
            reader: new Ext.data.JsonReader({
            root: 'listProductos',
            id: 'value'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}  
             ]),
            remoteSort: true
        });
        storeProducto.setDefaultSort('value', 'desc');
        storeProducto.load();
        
        var storeStatusPoliza = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'procesoemision/obtenerStatusPoliza.action'
            }),
            reader: new Ext.data.JsonReader({
            root: 'listStatusPoliza',
            id: 'value'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}  
             ]),
            remoteSort: true
        });
        storeStatusPoliza.setDefaultSort('value', 'desc');
        storeStatusPoliza.load();
     
   
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
/*************************************************************
** Store Grid
**************************************************************/ 
   
    
    // el var es un atributo ingresado al componente
    // cada fila un objeto que se iterara...
    var lasFilas=new Ext.data.Record.create([
        {name: 'cdConjunto',  type: 'string',  mapping:'cdConjunto'},
        {name: 'nombreConjunto',  type: 'string',  mapping:'nombreConjunto'},
        {name: 'descripcion',  type: 'string',  mapping:'descripcion'},
        {name: 'proceso',  type: 'string',  mapping:'proceso'},
        {name: 'cliente',  type: 'string',  mapping:'cliente'}                    
    ]);
    
    var jsonGrilla= new Ext.data.JsonReader({    
        root:'dataGrid',
        totalProperty: 'totalCount',
        successProperty : '@success',
        id: 'cdConjunto'
        },
        lasFilas
    );
    
    var cm = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
            {header: "ClaveConjunto",      dataIndex:'cdConjunto',     width:20,     sortable:true, locked:false,   id:'cdConjunto', hidden:true},          
            {header: "Aseguradora",        dataIndex:'proceso',        width:80,     sortable:true},
            {header: "Producto",           dataIndex:'descripcion',    width:300,    sortable:true},
            {header: "P&oacute;liza",      dataIndex:'proceso',        width:100,    sortable:true},
            {header: "Estado",             dataIndex:'cliente',        width:70,     sortable:true},
            //{header: "Forma pago",       dataIndex:'descripcion',    width:100,    sortable:true},
            {header: "Periocidad",         dataIndex:'descripcion',    width:100,    sortable:true},
            //{header: "Nombre persona",   dataIndex:'descripcion',    width:135,    sortable:true},
            {header: "Asegurado",          dataIndex:'descripcion',    width:300,    sortable:true},
            {header: "R.F.C.",             dataIndex:'descripcion',    width:100,    sortable:true},
            {header: "Vigencia Desde - ",  dataIndex:'descripcion',    width:130,    sortable:true},
            {header: "Hasta",              dataIndex:'descripcion',    width:80,     sortable:true},
            //{header: "Conducto de Cobro",dataIndex:'descripcion',    width:150,    sortable:true},
            {header: "Inst. de Pago",      dataIndex:'descripcion',    width:170,    sortable:true},
            {header: "Prima Total",        dataIndex:'descripcion',    width:100,    sortable:true, renderer:Ext.util.Format.usMoney}
    ]);
    
   var storeGrid;
    
   function getResultado(){
       // filterAreaForm.getForm().submit();
        url='flujoendoso/obtenerDatos.action';
        storeGrid = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
            url: url          
            }),
            reader: jsonGrilla,
            remoteSort: true
           
        });

        storeGrid.setDefaultSort('cdConjunto', 'desc');
        return storeGrid;
    }
 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       
    var storeFecha = new Ext.data.SimpleStore({
        fields: ['id', 'texto'],
        data: [
            ['I', 'Inicio'],
            ['F', 'Fin']
        ]
    });

//////FORM PARA LOS FILTROS//////////////////////////

 var filterAreaForm = new Ext.form.FormPanel({  
        url:'procesoemision/buscaPolizas.action',                        
        layout:'form',
        id:'filterArea',
        border: false,
        width: 860,
        bodyStyle:'padding:5px 5px 0',
        autoHeight : true,   
        items: [{
        	xtype:'fieldset',
        	style:'margin:5px',
        	bodyStyle:'padding:5px',
        	title: '<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Par&aacute;metros de B&uacute;squeda</span>',  
        	autoHeight: true,
        	width: 850,
        	border: true,
        	buttonAlign : 'center',
        	items :[{
               layout:'column',
               border:false, 
               items :[{
                        columnWidth:.4,
                        layout: 'form',
                        border: false,
                        labelWidth : 60, 
                        items: [{
                                    xtype:"combo",
                                    emptyText:"Seleccione...",
                                    store: storeProducto,
                                    hiddenName:"producto",
                                    fieldLabel: "Producto",
                                    name: "producto",
                                    width: "100", 
                                    allowBlank:true,
                                    mode:'local',
                                    editable: true,
                                    triggerAction: "all",
                                    valueField: "value",
                                    displayField:"label",
                               },{
                                 xtype:"textfield",
                                 fieldLabel: 'P&oacute;liza',
                                 anchor:'75%',
                                 name:'poliza'
                               },{
                                 xtype:"textfield",
                                 fieldLabel: 'Asegurado',
                                 anchor:'75%',
                                 name:'nombreAsegurado' 
                                
                               }]//items columna1
                     },{
                        columnWidth:.45,
                        layout: 'form',
                        bodyStyle:'padding:5px 5px 0',
                        border: false,
                        labelWidth : 100,
                        items: [{
                                 fieldLabel:"Aseguradora",
                                 emptyText:"Seleccione...",
                                 xtype:"combo",   
                                 hiddenName:"aseguradora",     
                                 name:"aseguradora",
                                 //hiddenName:"haseguradora",
                                 id:"idAseguradora",
                                 store: storeAseguradoras,
                                 displayField:"label",
                                 valueField:"value", 
                                 triggerAction: "all",
                                 anchor:'100%', 
                                 mode:'local',
                                 editable:true
                              },{
                                    xtype:"combo",
                                    emptyText:"Seleccione...",
                                    store: storeFecha,
                                    name: "fechaInicioFin",
                                    hiddenName:"fechaInicioFin",
                                    valueField: "id",
                                    displayField:"texto",
                                    fieldLabel: "Vigencia",
                                    width:"50", 
                                    anchor:"100%",
                                    allowBlank:true,
                                    id: "inicioFin",
                                    mode:'local',
                                    editable:true,
                                    triggerAction: "all"
                                },
                              {
                                border:false,
                                layout:'column',
                                items:[{
                                        columnWidth:.58,
                                        layout: 'form',
                                        border: false,
                                        labelWidth : 100,
                                        items:[{
                                            xtype:'datefield',
                                            fieldLabel: 'Desde',
                                            name: 'inicioVigencia',
                                            width:'120', 
                                            anchor:'100%'
                                        }]
                                    },{
                                        columnWidth:.42,
                                        layout: 'form',
                                        border:false,
                                        labelWidth : 40,
                                        items: [{
                                                xtype:'datefield',
                                                fieldLabel: ' Hasta',
                                                name: 'finVigencia',
                                                allowBlank:true,
                                                width:'95', 
                                                anchor:'100%',
                                                id: 'finVigencia-idUnico'
                                        }]
                                }]   //items fecha
                               },{ 
                                 fieldLabel:"Status de Poliza",
                                 emptyText:"Seleccione...",
                                 xtype:"combo",        
                                 name:"statusPoliza",
                                 hiddenName:"statusPoliza",
                                 id:"idStatusPoliza",
                                 store: storeStatusPoliza,
                                 displayField:"label",
                                 valueField:"value", 
                                 triggerAction: "all",
                                 mode:'local',
                                 anchor:'100%', 
                                 editable:true
                               }]//items columna2
                }]//items layout column                   
        }],////end items FieldSet
        buttons:[{                                  
                 text:'Buscar',  
                 handler: function() {
                     if (filterAreaForm.form.isValid()) {
                             filterAreaForm.form.submit({                 
                             waitMsg:'Procesando...',
                                         
                             failure: function(form, action) {
                              Ext.MessageBox.alert('Busqueda Finalizada', 'No se encontraron registros');
                                    
                             },
                             success: function(form, action) {
                             storeGrid.load();
                           }
                         });                   
                         } else{
                                Ext.MessageBox.alert('Error', 'Se produjo error');
                        }  
                  }      //end handler                                                 
              },{
                 text:'Cancelar',
                 handler: function() {
                                     filterAreaForm.form.reset();
                                    }
             }]//end buttons FieldSet
  }]//end items
       
/*/////////////////////////////////////////////////////////////////
    ELEMENTOS TOMADOS DEL EXT BUILDER....
*///////
            
                   
        //// ELEMENTOS OBTENIDOS DEL EXT-BUILDER
    });

//////FORM PARA RESULTADOS//////////////////////////

 var resultAreaForm = new Ext.form.FormPanel({  
        //url:'confpantallas/obtenerDatos.action',                        
        layout:'form',
        id:'resultArea',
        border: false,
        bodyStyle:'padding:5px 5px 0',
        autoHeight : true,
        width:840,
       
       
/*/////////////////////////////////////////////////////////////////
    ELEMENTOS TOMADOS DEL EXT BUILDER....
*///////
        items:[{
        xtype: 'grid',
        id:'gridResultado',
            store: getResultado(),
            title: '<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Resultados de Busqueda</span>',
            stripeRows: true,
            cm: cm,
            cls: 'alinear',
            height:250,
            width:830,
            //viewConfig: {autoFill: true},
        
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                listeners: {                            
                    rowselect: function(sm, row, rec) { 
                    var selectedId = storeGrid.data.items[row].id;
                    window.location.href = 'endoso.action'+'?id='+selectedId;
                    }
                }
            }),
             buttonAlign: 'center',
             buttons:[{
                    id:'editar',
                    text:'Editar',
                    tooltip:'Edita opcion seleccionada'                           
                    },{
                    text:'Regresar',
                    id: 'copiar',
                    tooltip:'Copia opcion seleccionada'                
                    },{
                    text:'Reportes',
                    id:'borrar',
                    tooltip:'Elimina opcion seleccionada'
                    },{
                    text:'Exportar',
                    tooltip:'Exporta opciones'
                }],
            bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: storeGrid,
            displayInfo: true,
    		displayMsg: 'Registros mostrados {0} - {1} de {2}',
    		emptyMsg: 'No hay registros para mostrar',
    		beforePageText: 'P&aacute;gina',
    		afterPageText: 'de {0}'
                  })
        }]//items
         
        //// ELEMENTOS OBTENIDOS DEL EXT-BUILDER
 });
  
  
/*************************************************************
** Panel
**************************************************************/ 
  var panelPrincipal = new Ext.Panel({
        region: 'north',
        title: 'Consulta de P&oacute;lizas',
        autoHeight : true ,
        width: 860,
        id:'panelPrincipal',
        bodyStyle:'padding:5px',
        items: [filterAreaForm, resultAreaForm ] 
    
    });

panelPrincipal.render('items');
  
});



</script>