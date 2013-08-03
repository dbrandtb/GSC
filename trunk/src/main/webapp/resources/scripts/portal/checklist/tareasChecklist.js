//var helpMap = new Map();



Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

    var cm = new Ext.grid.ColumnModel([
			{
           	id:'cmDsSeccionTareasChecklistId',
           	header: getLabelFromMap('cmDsSeccionTareasChecklistId',helpMap,'Secci&oacute;n'),
           	tooltip: getToolTipFromMap('cmDsSeccionTareasChecklistId', helpMap,'Secci&oacute;n'),
           	//header: "Secci&oacute;n",
           	dataIndex: 'dsSeccion',
           	sortable: true,
           	width: 117
        	},{
           	id:'cmDsTareaTareasChecklistId',
           	header: getLabelFromMap('cmDsTareaTareasChecklistId',helpMap,'Tarea'),
           	tooltip: getToolTipFromMap('cmDsTareaTareasChecklistId', helpMap,'Tarea del Checklist'),
           	//header: "Tarea",
           	dataIndex: 'dsTarea',
           	sortable: true,
           	width: 120
        },{
           	id:'cmDsTareaPadreTareasChecklistId',
           	header: getLabelFromMap('cmDsTareaPadreTareasChecklistId',helpMap,'Tarea Padre'),
           	tooltip: getToolTipFromMap('cmDsTareaPadreTareasChecklistId', helpMap,'Tarea Padre del Checklist'),
           	//header: "Tarea Padre",
           	dataIndex: 'dsTareapadre',
           	sortable: true,
           	width: 120
        },{
           	id:'cmDsEstadoTareasChecklistId',
           	header: getLabelFromMap('cmDsEstadoTareasChecklistId',helpMap,'Estado'),
           	tooltip: getToolTipFromMap('cmDsEstadoTareasChecklistId', helpMap,'Estado de la tarea del Checklist'),
           	//header: "Estado",
           	dataIndex: 'dsEstado',
           	sortable: true,
           	width: 120
        },{        
           	dataIndex: 'cdTarea',
           	hidden: true
        	},{
           	dataIndex: 'cdSeccion',
           	hidden: true
        }]);



    var seccion = new Ext.form.TextField({ 
        id:'seccionId',   
        fieldLabel: getLabelFromMap('seccionId', helpMap,'Secci&oacute;n'), 
        tooltip: getToolTipFromMap('seccionId', helpMap,'Secci&oacuten de tareas del Checklist'), 
        hasHelpIcon:getHelpIconFromMap('seccionId',helpMap),
		Ayuda:getHelpTextFromMap('seccionId',helpMap),
        name:'seccion',
		hasHelpIcon: true,       
        anchor: '95%'
    });
    var tarea = new Ext.form.TextField({
        id:'tareaId',   
        fieldLabel: getLabelFromMap('tareaId', helpMap,'Tarea'), 
        tooltip: getToolTipFromMap('tareaId', helpMap, 'Tarea del Checklist'),
        hasHelpIcon:getHelpIconFromMap('tareaId',helpMap),
		Ayuda:getHelpTextFromMap('tareaId',helpMap),
        name:'tarea',
		hasHelpIcon: true,       
        anchor: '95%'
    });
    var estado = new Ext.form.TextField({
        id:'estadoId',   
        fieldLabel: getLabelFromMap('estadoId', helpMap,'Estado'), 
        tooltip: getToolTipFromMap('estadoId', helpMap, 'Estado de tarea del Checklist'),
        hasHelpIcon:getHelpIconFromMap('estadoId',helpMap),
		Ayuda:getHelpTextFromMap('estadoId',helpMap),
        name:'estado',
		hasHelpIcon: true,       
        anchor: '95%'
    });
var store;

 function test(){
 			store = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_TAREAS_CHECKLIST,
				waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...')
                }),

                reader: new Ext.data.JsonReader({
            	root:'MEstructuraList',
            	totalProperty: 'totalCount',
            	id: 'cdTarea',
	            successProperty : '@success'
	        },[
	        {name: 'cdTarea',  type: 'int',  mapping:'cdTarea'},
	        {name: 'cdSeccion',  type: 'int',  mapping:'cdSeccion'},
	        {name: 'dsSeccion',  type: 'string',  mapping:'dsSeccion'},
	        {name: 'dsTarea',  type: 'string',  mapping:'dsTarea'},
	        {name: 'dsTareapadre',  type: 'string',  mapping:'dsTareapadre'},
	        {name: 'dsEstado',  type: 'string',  mapping:'dsEstado'}
			])
        });
		return store;
 	}

    var grid2;

function createGrid(){
	grid2= new Ext.grid.GridPanel({
		id: 'grid2',
        el:'gridTareas',
        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>', 
        store:test(),
		border:true,
        cm: cm,
        loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
        buttonAlign:'center',
		buttons:[
	        		{
	        			id:'grid2ButtonAgregarId',
        				text:getLabelFromMap('grid2ButtonAgregarId', helpMap,'Agregar'),
            			tooltip:getToolTipFromMap('grid2ButtonAgregarId', helpMap,'Agrega una nueva tarea'),			
	        			//text:'Agregar',
	            		//tooltip:'Agregar una nueva tarea',
	            		handler:function(){agregar()}
	            	},
	            	{
	        			id:'grid2ButtonEditarId',
        				text:getLabelFromMap('grid2ButtonEditarId', helpMap,'Editar'),
            			tooltip:getToolTipFromMap('grid2ButtonEditarId', helpMap, 'Edita una tarea'),	
	            		//text:'Editar',
	            		//tooltip:'Edita una tarea',
	            		handler:function(){
	            				if (getSelectedRecord(grid2) != null) {
	            					editar(getSelectedRecord(grid2));
	            				}else {
	            					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
	            				}
	            			}
	            	},
	            	{
	        			id:'grid2ButtonBorrarId',
        				text:getLabelFromMap('grid2ButtonBorrarId', helpMap,'Eliminar'),
            			tooltip:getToolTipFromMap('grid2ButtonBorrarId', helpMap,'Elimina una tarea'),
	            		//text:'Borrar',
	            		//tooltip:'Borra una tarea',
	                	handler:function(){
	                			if(getSelectedRecord(grid2)!=null){
	                					borrar(getSelectedRecord(grid2));
	                			}
	                			else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
	                		}
	            	}
/*	            	{
	        			id:'grid2ButtonRegresarId',
        				text:getLabelFromMap('grid2ButtonRegresarId', helpMap,'Regresar'),
            			tooltip:getToolTipFromMap('grid2ButtonRegresarId', helpMap,'Regresa a la pantalla anterior')	            		
	            		//text:'Regresar',
	            		//tooltip:'Regresa a la pantalla anterior'
	            	}*/
            	],
    	width:500,
    	frame:true,
		height:578,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		stripeRows: true,
		collapsible: true,
		bbar: new Ext.PagingToolbar({
				pageSize:itemsPerPage,
				store: store,
				displayInfo: true,
                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
		    })
		});

    grid2.render()
}


    var incisosForm = new Ext.form.FormPanel({
    	id: 'incisosForm',    	
        el:'formBusqueda',
        title: '<span style="color:black;font-size:14px;">'+getLabelFromMap('incisosForm', helpMap,'Tareas Checklist')+'</span>',
		//title: '<span style="color:black;font-size:14px;">Tareas Checklist</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        url:_ACTION_BUSCAR_TAREAS_CHECKLIST,
        width: 500,
        height:225,
        items: [{
        		layout:'form',
				border: false,
				items:[{
                bodyStyle:'background: white',
                title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
		        labelWidth: 100,
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
        		        				seccion,
        		        				tarea,
        		        				estado   
		       						]
								},{
								columnWidth:.4,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
                				id: 'incisosFormButtonsBuscarId', 
            					text: getLabelFromMap('incisosFormButtonsBuscarId', helpMap,'Buscar'),
            					tooltip: getToolTipFromMap('incisosFormButtonsBuscarId', helpMap, 'Busca una tarea del checklist'), 

        							//text:'Buscar',
        							handler: function() {
				               			if (incisosForm.form.isValid()) {
                                               if (grid2!=null) {
                                                reloadGrid();
                                               } else {
                                                createGrid();
                                               }
                						} else{
											Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
										}
									}
        							},{
        							
        							id:'incisosFormButtonCancelarId',
        							text: getLabelFromMap('incisosFormButtonCancelarId', helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('incisosFormButtonCancelarId', helpMap, 'Cancela la B&uacute;squeda'),
        							//text:'Cancelar',
        							handler: function() {
        								incisosForm.form.reset();
        							}
        						}]
        					}]
        				}]
				});

		incisosForm.render();
        createGrid();

        function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
    	}

});
function reloadGrid(){
	
	var _params = {
    		seccion: Ext.getCmp('incisosForm').form.findField('seccion').getValue(),
    		tarea: Ext.getCmp('incisosForm').form.findField('tarea').getValue(),
    		estado: Ext.getCmp('incisosForm').form.findField('estado').getValue()
	};
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
}
function cbkReload (_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}