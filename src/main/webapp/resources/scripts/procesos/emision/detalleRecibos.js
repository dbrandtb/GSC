Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//Datastore Grid
function createOptionGrid(){                                       
        url='procesoemision/recibos.action';
    	store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
        url: url
        }),
        reader: new Ext.data.JsonReader({
        root:'listRecibos',
        id:'opcionesStore',
        totalProperty: 'totalCount'             
        },[
            {name: 'feinicio',  type: 'string',    mapping:'feinicio'},
            {name: 'fefinal',   type: 'string',    mapping:'fefinal'},
           	{name: 'dsestado',  type: 'string',    mapping:'dsestado'},
            {name: 'ptimport',  type: 'string',    mapping:'ptimport'}
        ]),
        remoteSort: true
    });
    store.setDefaultSort('opcionesStore', 'desc');
    store.load();
    return store;
}


//Columnas Grid Recibos
function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
        }

        var cm = new Ext.grid.ColumnModel([
            new Ext.grid.RowNumberer(),
            {header: "Inicio Vigencia", dataIndex:'feinicio',   width: 120, sortable:true},
          	{header: "Fin Vigencia",    dataIndex:'fefinal',     width: 120, sortable:true},
            {header: "Estado",          dataIndex:'dsestado',   width: 120, sortable:true},
            {header: "Importe",         dataIndex:'ptimport',     width: 120, sortable:true}
        ]);


//Crear Grid Recibos
function gridRecibos(){
    recibosGrid= new Ext.grid.GridPanel({
    store:createOptionGrid(),
    border:true,
    baseCls:' background:white ',
    cm: cm,
    buttons:[
            {text:'Regresar',
            tooltip:'Regresar a la pantalla anterior'
            }],                                                         
    width:500,
    frame:true,
    height:320,        
    title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',      
    viewConfig: {autoFill: true,forceFit:true},                
    bbar: new Ext.PagingToolbar({
        pageSize:20,
        store: store,                                               
        displayInfo: true,
        displayMsg: 'Displaying rows {0} - {1} of {2}',
        emptyMsg: "No rows to display"                      
        })                                                                                               
    });
    
    recibosGrid.render('gridRecibos');
}
gridRecibos();
});