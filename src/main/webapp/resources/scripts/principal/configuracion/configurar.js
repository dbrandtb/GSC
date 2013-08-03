Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

	var itemsPerPage=10;	

	var swHabilitado = new Ext.grid.CheckColumn({
       header: 'Habilitado',
       dataIndex: 'habilitado',
       width: 55,
       align:'center' 
    });

	var cm = new Ext.grid.ColumnModel([
	{
		header:'Seccion',
		dataIndex:'dsSeccion',
		width:100		
	},{
		header:'Nombre',
		dataIndex:'dsConfigura',
		width:100
	},
		swHabilitado
	]);

	var url = _ACTION_CONFIGURACION
	var _store=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:url
		}),
		reader: new Ext.data.JsonReader({
			root:'roles',
			totalProperty:'totalCount',
			id:'cdSeccion',
			successProperty : '@success'
		},[
		{name:'cdConfigura',	type:'string', 		mapping:'cdConfigura'},
		{name:'cdRol', 			type:'string', 		mapping:'cdRol'},
		{name:'dsRol', 			type:'string', 		mapping:'dsRol'},
		{name:'cdElemento', 	type:'string', 		mapping:'cdElemento'},
		{name:'dsElemen', 		type:'string', 		mapping:'dsElemen'},
		{name:'cdSeccion', 		type:'string', 		mapping:'cdSeccion'},
		{name:'dsSeccion', 		type:'string', 		mapping:'dsSeccion'},
		{name:'dsTipo', 		type:'string', 		mapping:'dsTipo'},
		{name:'swHabilitado', 	type:'string', 		mapping:'swHabilitado'},
		{name:'dsArchivo', 		type:'string', 		mapping:'dsArchivo'},
		{name:'dsConfigura', 	type:'string', 		mapping:'dsConfigura'},
		{name:'especificacion', type:'string', 		mapping:'especificacion'},
		{name:'habilitado', 	type:'bool', 		mapping:'habilitado'},
		{name:'contenidoDato', 	type:'bool', 		mapping:'contenidoDato'},
		{name:'contenidoDatoSec',	type:'bool', 	mapping:'contenidoDatoSec'}
		]),
			remoteSort:false,
			baseParams:{
				claveConfiguracion:'',
				claveSistemaRol:'',
				claveElemento:'',
				seccionForm:''			
			}
	});
	_store.setDefaultSort('dsSeccion','desc');

	var grid2;
	var claveDeConfiguracion;
	var claveDeSeccion;
	var recData;
	function getSelectedRecord(){
             var m = grid2.getSelections();
             if (m.length == 1 ) {
                return m[0];
             }
        }
	grid2 = new Ext.grid.GridPanel({
		store: _store,
		id:'lista-lista',
		border:true,
		buttonAlign:'center',
		cm:cm,
		buttons:[
				{text:'Agregar',
            	tooltip:'Agregar una nueva configuraci&oacute;n',
            	handler: function(){
            		agregarConfig(_store);
        		}
            	},{
            	id:'editar',
            	text:'Editar',
            	tooltip:'Editar configuraci&oacute;n seleccionada'
            	},{
            	text:'Eliminar',
            	id:'borrar-grid',
            	tooltip:'Elimina configuraci&oacute;n seleccionada'
            	},{
            	text:'Exportar',
            	tooltip:'Exporta Configuraciones',
            	handler: exportButton( _ACTION_EXPORT_CONFIG)
            	},{
            	text:'Regresar',
            	tooltip:'Regresar a la pagina anterior',
            	handler:function(){
			    	window.location.replace("obtieneRoles.action");
            	}
            	}],
		width:600,
		frame:true,
		height:578,
        title:'<span class="x-form-item" style="color:#98012E; font-size:14px; font-family:Arial,Helvetica, Sans-serif;">Listado</span>',
        sm:new Ext.grid.RowSelectionModel({
    	singleSelect:true,
    	listeners: {                            
                        rowselect: function(sm, row, rec) {
                        		selectedId = _store.data.items[row].id;
                        		recData = rec;
                                Ext.getCmp('borrar-grid').on('click',function(){                                	
                                	claveDeConfiguracion = rec.get('cdConfigura');
                                	claveDeSeccion = rec.get('cdSeccion');                                
                                	borrarConfig(_store, claveDeConfiguracion,claveDeSeccion, configuraForm );                                	
                                });
                        }
                }    	
    	}),
    viewConfig: {autoFill: true,forceFit:true},                
	bbar: new Ext.PagingToolbar({
		pageSize:20,
		store: _store,									            
		displayInfo: true,
		displayMsg:'<span style="color:white;">Mostrando registros {0} - {1} de {2}</span>',
        emptyMsg:'<span style="color:white;">No hay registros para visualizar</span>' 
		})        		
	});
	grid2.render('gridConfigura');	
	
	Ext.getCmp('editar').on('click',function(){
		if (getSelectedRecord() != null) {
			editarConfiguracion(recData, _store);
        } else {
            Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operacion');
        }
	});

	var claveConfiguracion = new Ext.form.Hidden({	
		name:'claveConfiguracion',
		width:200,
		labelSeparator:'',
		value:_CVECONF
	});
	var claveSistemaRol= new Ext.form.Hidden({
		name: 'claveSistemaRol',
		width:200,
		labelSeparator:'',
		value:_CVESISROL
	});
	var claveElemento = new Ext.form.Hidden({
		name:'claveElemento',
		width:200,
		labelSeparator:'',
		value:_CVEELEM
	});

	var dsConfiguracion = new Ext.form.TextField({
		fieldLabel:'Nombre',
		name:'dsConfiguracion',
		value:_DESCONF,
		width:200,
		readOnly:true
	});
	var dsSistemaRol= new Ext.form.TextField({
		fieldLabel:'Rol',
		name: 'dsSistemaRol',
		value:_DESSISROL,
		width:200,
		readOnly:true
	});
	var dsElemento = new Ext.form.TextField({
		fieldLabel:'Nivel',
		name:'dsElemento',
		value:_DESELEM,
		width:200,
		readOnly:true
	});
		
	var busqueda = new Ext.form.TextField({
		fieldLabel:'<span class="x-form-item" style="color:#98012E; font-size:14px; font-family:Arial,Helvetica,sans-serif;">B&uacute;squedas</span>',
		//labelSeparator:'',	
		bodyStyle:'background:white',
		labelWidth:200,
		hidden:true			
	});
	
	var seccionForm = new Ext.form.TextField({
		fieldLabel:'Secci&oacute;n',
		name:'seccionForm',
		width:200
	});
	
	function reloadGrid(){
		var _params = {
			claveConfiguracion: claveConfiguracion.getValue(),
			claveSistemaRol: claveSistemaRol.getValue(),
			claveElemento: claveElemento.getValue(),
			seccionForm: seccionForm.getValue()
		};
		reloadComponentStore (grid2, _params, cbkReload);
	}	

    function cbkReload (_r, _options, _success, _store) {
    	if (!_success) {
    		//Ext.Msg.alert('Error', _store.reader.jsonData.actionErrors[0]);
    		Ext.Msg.alert('Aviso', _store.reader.jsonData.actionErrors[0]);
    		grid2.store.removeAll();
    	}
    }

    var configuraForm = new Ext.form.FormPanel({
		el:'formConfig',
		id:'carga-valores',
		title: '<span style="color:black;font-size:12px;">Configuraci&oacute;n P&aacute;gina Principal</span>',
		bodyStyle:'background:white',
		buttonAlign:'center',
		labelAlign:'right',
		frame:true,
		url:_ACTION_CONFIGURACION,
		width:600,
		height:200,
		items:[{
				layout:'form',
				border:false,				
				items:[{
				        title:'<span style="color:#98012E; font-size:14px; font-family:Arial,Helvetica,sans-serif;">B&uacute;squeda</span>',
					    bodyStyle:'background:white',
					    //labelWidth:200,
						layout:'form',
						frame:true,
						baseCls:'',
						buttonAlign:'center',
						items:[{
								layout:'column',
								border:false,
								//columnwidth:1,
								items:[{
										//columnWidth:.2,
										layout:'form'
										},{
										//columnWidth:.8,
										layout:'form',
										border:false,
										items:[																																
												claveConfiguracion,
												claveSistemaRol,
												claveElemento,												
												dsConfiguracion,
												dsSistemaRol,
												dsElemento,
												seccionForm	
										   		]
										}]
									}
								],
							buttons:[{
										text:'Buscar',
										tooltip:'Busca una Configuraci&oacute;n',
										handler:function(){
											if(configuraForm.form.isValid()){
												if(grid2 !=null){
													reloadGrid();	
												}else{
													createGrid();
												}										
											}else{
												Ext.MessageBox.alert('Error','Inserte Parametros de B&uacute;squeda!');
											}
										}
									},{
										text:'Cancelar',
										tooltip:'Cancela Operaci&oacute;n',
										handler:function(){
											seccionForm.reset();
										}
									}]
							}]
					}]
			});						
	configuraForm.render();
	
	function toggleDetails(btn, pressed){
		var view = grid.getView();
		view.showPreview=pressed;
		view.refresh();
	}
	var store;	
		
	
	var dataStoreCliente = new Ext.data.Store({
    	proxy: new Ext.data.HttpProxy({
			url: 'principal/clienteAction.action'
        }),
        reader: new Ext.data.JsonReader({
        	root: 'clientes',
            id: 'listas'
		},[
        	{name: 'claveCliente',  type: 'string',  mapping:'claveCliente'},
           	{name: 'dsElemen',   	type: 'string',  mapping:'descripcion'},
           	{name: 'clavePersona',  type: 'string',  mapping:'clavePersona'}    
        ]),
		remoteSort: false
    });
	dataStoreCliente.setDefaultSort('dsElemen', 'desc');
	
	var dataStoreRol = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
		url:'principal/rolAction.action'
		}),
		reader: new Ext.data.JsonReader({
			root:'rolesLista',
			id:'rol'
		},[
			{name:'dsRol', 	type:'string', 	mapping:'dsRol'},
			{name:'cdRol', 	type:'string', 	mapping:'cdRol'}
		]),
		remoteSort:false
	});
	dataStoreRol.setDefaultSort('dsRol','desc');
	
	
	var dataStoreSeccion = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url:'principal/seccionAction.action'
		}),
		reader: new Ext.data.JsonReader({
			root:'secciones',
			id:'sec'
		},[
			{name:'dsSeccion', 	type:'string', 	mapping:'dsSeccion'},
			{name:'cdSeccion', 	type:'string', 	mapping:'cdSeccion'}
		]),
		remoteSort:false
	});
	dataStoreSeccion.setDefaultSort('dsSeccion','desc');
	
	var dataStoreTipo = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url:'principal/tipoAction.action'
		}),
		reader: new Ext.data.JsonReader({
			root:'tipos',
			id:'tip'
		},[
			{name:'clave', 	type:'string', 	mapping:'clave'},
			{name:'dsTipo', 	type:'string', 	mapping:'valor'}
		]),
			remoteSort:false
	});
	dataStoreTipo.setDefaultSort('dsTipo','desc');

	dataStoreCliente.load();
    dataStoreRol.load();
    dataStoreSeccion.load();
    dataStoreTipo.load();
    //reloadGrid();
});// FIN Ext.onReady




Ext.grid.CheckColumn = function(config){
    Ext.apply(this, config);
    if(!this.id){
        this.id = Ext.id();
    }
    this.renderer = this.renderer.createDelegate(this);
};

Ext.grid.CheckColumn.prototype ={
    init : function(grid){
        this.grid = grid;
        this.grid.on('render', function(){
            var view = this.grid.getView();
            view.mainBody.on('mousedown', this.onMouseDown, this);
        }, this);
    },
	
    onMouseDown : function(e, t){
        if(t.className && t.className.indexOf('x-grid3-cc-'+this.id) != -1){
            e.stopEvent();
            var index = this.grid.getView().findRowIndex(t);
            var record = this.grid.store.getAt(index);
            record.set(this.dataIndex, !record.data[this.dataIndex]);
        }
    },

    renderer : function(v, p, record){
        p.css += ' x-grid3-check-col-td'; 
        return '<div class="x-grid3-check-col'+(v?'-on':'')+' x-grid3-cc-'+this.id+'">&#160;</div>';
    }
};
