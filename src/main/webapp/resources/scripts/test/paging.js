Ext.onReady(function(){

    var store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: _ACTION_PAGING
        }),
        reader: new Ext.data.JsonReader({
            root: 'mreciboList',
            totalProperty: 'totalCount',
            id: 'nmrecibo'
	        },[
	            {name: 'nmpoliza',  type: 'string',  mapping:'nmpoliza'},
	            {name: 'nmsuplem',  type: 'string',  mapping:'nmsuplem'},
	            {name: 'nmrecibo',  type: 'string',  mapping:'nmrecibo'},
	            {name: 'feinicio',  type: 'string',  mapping:'feinicio'}
			]),
		remoteSort: true
    });

    store.setDefaultSort('nmrecibo', 'desc');

    var cm = new Ext.grid.ColumnModel([{
           header: "Número Poliza",
           dataIndex: 'nmpoliza',
           width: 130
        },{
           header: "Número Suplemento",
           dataIndex: 'nmsuplem',
           width: 130
        },{
           header: "Número Recibo",
           dataIndex: 'nmrecibo',
           width: 80
        },{
           header: "Fecha Inicio",
           dataIndex: 'feinicio',
           width: 80
        }]);

    cm.defaultSortable = true;

    var grid = new Ext.grid.GridPanel({
        el:'gridPaging',
        width:600,
        height:400,
        title:'Tabla paginada',
        store: store,
        cm: cm,
        loadMask: {msg:'Cargando...'},
		trackMouseOver:false,
        sm: new Ext.grid.RowSelectionModel({selectRow:Ext.emptyFn}),
        viewConfig: {autoFill: true,forceFit:true},
        bbar: new Ext.PagingToolbar({
            pageSize: 25,
            store: store,
            displayInfo: true,
            displayMsg: 'Displaying rows {0} - {1} of {2}',
            emptyMsg: "No rows to display",
            items:['-', {
                text: 'Export',
				handler: exportButton( _ACTION_EXPORT )
            }]
        })
    });

    grid.render();

    store.load({params:{start:0, limit:25}});

    Ext.get('main').createChild({tag: 'br', html: ''});
    
});