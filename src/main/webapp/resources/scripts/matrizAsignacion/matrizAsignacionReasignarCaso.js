/*
Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";


//VALORES DE INGRESO DE LA BUSQUEDA
var txtDatoUsuario= new Ext.form.TextField({
   	fieldLabel: getLabelFromMap('txtFldDatoUsuario', helpMap,'Usuario'), 
	tooltip: getToolTipFromMap('txtFldDatoUsuario', helpMap,'Usuario'),  				                    	
    id: 'txtDatoUsuarioId', 
    name: 'txtDatoUsuario',
    width:80
});
       
 
var txtBuscaUsuario= new Ext.form.TextField({
   	fieldLabel: getLabelFromMap('txtFldBuscaUsuario', helpMap,'Usuario'), 
	tooltip: getToolTipFromMap('txtFldBuscaUsuario', helpMap,'Nombre del Usuario'),  				                    	
    id: 'txtBuscaUsuarioId', 
    name: 'txtBuscaUsuario',
    width:80
});   
   
   

   
var incisosFormBuscaUsuarios = new Ext.FormPanel({
		id: 'incisosFormBuscaUsuarios',
        //el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('24',helpMap,'Usuarios Responsables')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        //url: _ACTION_BUSCAR_USUARIOS,
        width: 200,
        height:200,
        items: [{
        		layout:'form',
        		border: false,
				items:[{
        		bodyStyle:'background: white',
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
        		        				 txtBuscaUsuario        				
                                       ]
								},
								{
								columnWidth:.4,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
        							text:getLabelFromMap('ntfcnButtonBuscar',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('ntfcnButtonBuscar',helpMap,'Busca un Grupo de Asignaciones'),
        							handler: function() {/*
				               			if (incisosFormMatrizAsignacion.form.isValid()) {
                                               if (grid2!=null) {
                                                reloadGrid();
                                               } else {
                                                createGrid();
                                               }
                						} else{
                							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
										}
									}
        							},{
        							text:getLabelFromMap('ntfcnButtonCancelar',helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('ntfcnButtonCancelar',helpMap,'Cancelar busqueda de Asignaciones'),                              
        							handler: function() {
        								//incisosFormMatrizAsignacion.form.reset();
        							}
        						}]
        					}]
        				}]	            
        
});   */


   
    function storeCasos(){

                store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_CASOS
                }),

                reader: new Ext.data.JsonReader({
            	root:'MOperacionCatList',
            	totalProperty: 'totalCount',
	            successProperty : '@success'
	        },[
	        {name: 'cdDialogo',  type: 'string',  mapping:'cdDialogo'},
	        {name: 'dsDialogo',  type: 'string',  mapping:'dsDialogo'}
			])
        });

       return store;
 	}


    function storeUsuarios(){

                store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_USUARIOS
                }),

                reader: new Ext.data.JsonReader({
            	root:'MOperacionCatList',
            	totalProperty: 'totalCount',
	            successProperty : '@success'
	        },[
	        {name: 'cdDialogo',  type: 'string',  mapping:'cdDialogo'},
	        {name: 'dsDialogo',  type: 'string',  mapping:'dsDialogo'}
			])
        });

       return store;
 	}



function storeCasosAsignados(){

                store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_CASOS_ASIGNADOS
                }),

                reader: new Ext.data.JsonReader({
            	root:'MOperacionCatList',
            	totalProperty: 'totalCount',
	            successProperty : '@success'
	        },[
	        {name: 'cdDialogo',  type: 'string',  mapping:'cdDialogo'},
	        {name: 'dsDialogo',  type: 'string',  mapping:'dsDialogo'}
			])
        });

       return store;
 	}

// Definicion de las columnas de la grilla Casos
var cmCasos = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmNumeroCaso',helpMap,'Numero de Caso'),
        tooltip: getToolTipFromMap('cmNumeroCaso',helpMap,'Columna Numero de Caso'),
        dataIndex: 'nmCaso',
        width: 115,
        sortable: true,
        align: 'center'
        
        },{
        
        header: getLabelFromMap('cmTareaAsoc',helpMap,'Tarea Asociada'),
        tooltip: getToolTipFromMap('cmTareaAsoc',helpMap,'Columna Tarea Asociada'),
        dataIndex: 'nmTarea',
        width: 115,
        sortable: true,
        align: 'center'
        
         },{
        
        header: getLabelFromMap('cmVigencia',helpMap,'Vigencia'),
        tooltip: getToolTipFromMap('cmVigencia',helpMap,'Columna Vigencia'),
        dataIndex: 'nmTarea',
        width: 100,
        sortable: true,
        align: 'center'
        
         },{
        
        header: getLabelFromMap('cmRolCaso',helpMap,'Rol en Caso'),
        tooltip: getToolTipFromMap('cmRolCaso',helpMap,'Columna Rol en Caso'),
        dataIndex: 'nmTarea',
        width: 115,
        sortable: true,
        align: 'center'
        
        },{
        
        dataIndex: 'cdNmCaso',
        hidden :true
        }
]);




// Definicion de las columnas de la grilla Usuarios
var cmUsuarios = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmUsuario',helpMap,'Usuario'),
        tooltip: getToolTipFromMap('cmUsuario',helpMap,'Columna Usuario'),
        dataIndex: 'nmUsuario',
        width: 100,
        sortable: true
        },
        {
        header: getLabelFromMap('cmCasosAsignados',helpMap,'Casos Asignados'),
        tooltip: getToolTipFromMap('cmCasosAsignados',helpMap,'Columna Casos Asignados'),
        dataIndex: 'nmCasos',
        width: 100,
        sortable: true
        },
        {
        dataIndex: 'cmCdusuario',
        hidden :true
        }
                
]);


 
// Definicion de las columnas de la grilla casos Asignados
var cmCasosAsignados = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmNroCaso',helpMap,'Numero de Caso'),
        tooltip: getToolTipFromMap('cmNroCaso',helpMap,'Columna Numero de Caso'),
        dataIndex: 'cmNroCaso',
        width: 120,
        sortable: true
        },
        {
        header: getLabelFromMap('cmTareaAsociada',helpMap,'Tarea Asociada'),
        tooltip: getToolTipFromMap('cmTareaAsociada',helpMap,'Columna Tarea Asociada'),
        dataIndex: 'tareaAsoc',
        width: 100,
        sortable: true
        },
        {
        header: getLabelFromMap('cmRolCaso',helpMap,'Rol en Caso'),
        tooltip: getToolTipFromMap('cmRolCaso',helpMap,'Columna Rol en Caso'),
        dataIndex: 'rolCaso',
        width: 115,
        sortable: true,
        align: 'center'
        },
        {
        dataIndex: 'cdProceso',
        hidden :true
        }        
]);


var gridCasos;

function createGridCasos(){
       gridCasos= new Ext.grid.EditorGridPanel({
       		id: 'gridResponsables',
            store:storeCasos(),
            title: '<span style="height:10">Casos</span>',
            border:true,
            cm: cmCasos,
            width:450,
            frame:true,
            height:400,
            //sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			//stripeRows: true,
			//collapsible: true
			
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store:storeCasos(),
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })     
        });
 //gridResp.render();
}

var gridUsuarios;
function createGridUsuarios(){
       gridUsuarios = new Ext.grid.EditorGridPanel({
       		id: 'gridNivAtencion',
            //el:'gridNivelesAtencion',
            store:storeUsuarios(),
            title: '<span style="height:10">Usuarios Responsables</span>',
            //reader:jsonGrillaNoti,
            border:true,
            cm: cmUsuarios,
           //clicksToEdit:1,
	        //successProperty: 'success',
            width:250,
            frame:true,
            height:200,
            //sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			//stripeRows: true,
			//collapsible: true
			
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: storeUsuarios(),
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })    
        });
//gridNivAtencion.render();
}
 
 
 var gridCasosAsignados;
 function createGridCasosAsignados(){
       gridCasosAsignados = new Ext.grid.EditorGridPanel({
       		id: 'gridTiempos',
            //el:'gridTiempos',
            store:storeCasosAsignados(),
            title: '<span style="height:10">Casos Asignados</span>',
            //reader:jsonGrillaNoti,
            border:true,
            cm: cmCasosAsignados,
           //clicksToEdit:1,
	        //successProperty: 'success',
            width:250,
            frame:true,
            height:200,
            //sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			//stripeRows: true,
			//collapsible: true
			
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: storeCasosAsignados(),
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })   
        });
// gridTiempos.render();
}

 createGridCasos();
 createGridUsuarios();
createGridCasosAsignados();
 
 
 var incisosFormReasignarCaso = new Ext.FormPanel({
		id: 'incisosFormReasignarCaso',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('24',helpMap,'Reasignacion de Caso')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true, 
        border:true,  
        url: _ACTION_BUSCAR_RESPONSABLES,
        layout: 'table',
		layoutConfig: {columns: 5},
        width: 750,
        height:600,
        items: [{
                
		            			layout: 'form',colspan: 5,
		            			items: [
		            				     txtDatoUsuario
		            			       ]
		            		},
		            			       {
		            			layout: 'form',colspan: 2,rowspan:3,
		            			items: [
		            				     gridCasos
		            			       ]
		            			       },{
		            			layout: 'form',colspan: 3,labelAlign:'top',
		            			items: [
		            				     txtBuscaUsuario
		            				    
		            				     //incisosFormBuscaUsuarios
		            			       ]
		            			       },
		            			        
		            			        {
		            			layout: 'form',colspan: 2,
		            			items: [
		            				       gridUsuarios
		            			       ]
		            			       },
		            			    
		            			       
		            			       {
		            		    layout: 'form',colspan: 2,
		            			items: [
		            				     gridCasosAsignados
		            			       ]
		            			       
		            		
               
        				}],
        		buttons:[{
        							text:getLabelFromMap('ntfcnButtonBuscar',helpMap,'Guardar'),
        							tooltip: getToolTipFromMap('ntfcnButtonBuscar',helpMap,'Busca un Grupo de Asignaciones'),
        							handler: function() {
				               			alert("guardar");
									}
        							
        							
        						},{
        							text:getLabelFromMap('ntfcnButtonCancelar',helpMap,'Regresar'),
        							tooltip: getToolTipFromMap('ntfcnButtonCancelar',helpMap,'Cancelar busqueda de Asignaciones'),                              
        							handler: function() {
        								window.location = _ACTION_IR_CONFIGURA_MATRIZ_TAREA;
        							}
        						}]	            
        
});
 
 
 
incisosFormReasignarCaso.render();



/*
function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()});
	}
}      
});
*/

/*
function reloadGrid(){
	var _params = {
       		dsNotificacion: Ext.getCmp('incisosFormNoti').form.findField('desNotificacion').getValue(),
       		dsFormatoOrden: Ext.getCmp('incisosFormNoti').form.findField('desFormato').getValue(),
       		dsMetEnv: Ext.getCmp('incisosFormNoti').form.findField('desMetodoEnvio').getValue()
	};
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
}
function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
	}
}
*/
