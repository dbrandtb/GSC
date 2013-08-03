//var helpMap = new Map();

Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

    var cm = new Ext.grid.ColumnModel([
			{
			id:'cmDsElemenChecklistConfigurarCuentaId',
           	header: getLabelFromMap('cmDsElemenChecklistConfigurarCuentaId',helpMap,'Cliente'),
           	tooltip: getToolTipFromMap('cmDsElemenChecklistConfigurarCuentaId', helpMap, 'Cliente'),
           	//header: 'Cliente',
           	dataIndex: 'dsElemen',
           	sortable: true,
           	width: 180
        	},{
			id:'cmDsConfigChecklistConfigurarCuentaId',
           	header: getLabelFromMap('cmDsConfigChecklistConfigurarCuentaId',helpMap,'Nombre'),
           	tooltip: getToolTipFromMap('cmDsConfigChecklistConfigurarCuentaId', helpMap, 'Nombre de la configuraci&oacute;n de cuenta'),
           	//header: 'Nombre',
           	dataIndex: 'dsConfig',
           	sortable: true,
           	width: 80
        	},{
			id:'cmDsLinopeChecklistConfigurarCuentaId',
           	header: getLabelFromMap('cmDsLinopeChecklistConfigurarCuentaId',helpMap,'L&iacute;nea de operaci&oacute;n'),
           	tooltip: getToolTipFromMap('cmDsLinopeChecklistConfigurarCuentaId', helpMap,'L&iacute;nea de operaci&oacute;n'),
           	//header: 'Línea de operación',
           	dataIndex: 'dsLinope',
           	sortable: true,
           	width: 140
           	},{
			id:'cmDsEstadoChecklistConfigurarCuentaId',
           	header: getLabelFromMap('cmDsEstadoChecklistConfigurarCuentaId',helpMap,'Estado'),           	
           	tooltip: getToolTipFromMap('cmDsEstadoChecklistConfigurarCuentaId', helpMap,'Estado de la configuraci&oacute;n de cuenta'),
           	//header: 'Estado',
           	dataIndex: 'dsEstado',
           	sortable: true,
           	width: 80
           	},{
           	dataIndex: 'cdConfig',
           	hidden: true
           	}
           	]);

    var cliente = new Ext.form.TextField({
        //fieldLabel: 'Cliente',
        id:'cdElemenId',
        fieldLabel: getLabelFromMap('txtFldChkLstCnfCdElemenId', helpMap,'Cliente'), 
        tooltip: getToolTipFromMap('txtFldChkLstCnfCdElemenId', helpMap,'Cliente'),   
        hasHelpIcon:getHelpIconFromMap('cdElemenId',helpMap),
		Ayuda:getHelpTextFromMap('cdElemenId',helpMap),
        name:'cdElemen',      
        width: 150
    });
    
    var nombre = new Ext.form.TextField({
        //fieldLabel: 'Nombre',
        id:'dsConfigId',
        fieldLabel: getLabelFromMap('txtFldChkLstCnfDsConfigId', helpMap,'Nombre'), 
        tooltip: getToolTipFromMap('txtFldChkLstCnfDsConfigId', helpMap,'Nombre'),
        hasHelpIcon:getHelpIconFromMap('dsConfigId',helpMap),
		Ayuda:getHelpTextFromMap('dsConfigId',helpMap),
        name:'dsConfig',              
        width: 150
    });


//var store;

 //function test(){
 		var	_store = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_CHECKLIST_CONFIGURA_CUENTA
                }),
                reader: new Ext.data.JsonReader({
            	root:'MEstructuraList',
            	totalProperty: 'totalCount',
            	id: 'cdConfig',
	            successProperty : '@success'
	        },[
	        {name: 'dsElemen',  type: 'string',  mapping:'dsElemen'},
	        {name: 'dsConfig',  type: 'string',  mapping:'dsConfig'},
	        {name: 'dsLinope',  type: 'string',  mapping:'dsLinope'},
	        {name: 'dsEstado',  type: 'string',  mapping:'dsEstado'},
	        {name: 'cdConfig',  type: 'string',  mapping:'cdConfig'}
			])
            //baseParams: {dsElemen:cliente.getValue(), dsConfig: nombre.getValue()}
        });
		/*return store;
 	}*/


var grid2;

function createGrid(){
	grid2= new Ext.grid.GridPanel({
		id: 'grid2',
        el:'gridConfiguraCuenta',		
        store: _store,//test(),
        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>', 
		border:true,
        cm: cm,
        loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
        buttonAlign:'center',
        buttons:[
	        		{
	        			id:'grid2ButtonAgregarId',
        				text:getLabelFromMap('grid2ButtonAgregarId', helpMap,'Agregar'),
            			tooltip:getToolTipFromMap('grid2ButtonAgregarId', helpMap, 'Agrega una nueva configuraci&oacute;n de la cuenta'),			        			
	        			//text:'Agregar',
	            		//tooltip:'Agregar una nueva configuración de la cuenta',
	            		handler:function(){agregar()}
	            	},
	            	{
	            		id:'grid2ButtonEditarId',
        				text:getLabelFromMap('grid2ButtonEditarId', helpMap,'Editar'),
            			tooltip:getToolTipFromMap('grid2ButtonEditarId', helpMap, 'Edita una configuraci&oacute;n de la cuenta'),		            		
	            		//text:'Editar',
	            		//tooltip:'Edita una configuración de la cuenta',
	            		handler:function(){
	            			if(getSelectedRecord()!=null){
	                					editar(getSelectedRecord());
	                			}
	                			else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
	                		}
	            	},
	            	{
	            		id:'grid2ButtonBorrarId',
        				text: getLabelFromMap('grid2ButtonBorrarId', helpMap,'Eliminar'),
            			tooltip:getToolTipFromMap('grid2ButtonBorrarId', helpMap, 'Elimina una configuraci&oacute;n de la cuenta'),			
	            		//text:'Borrar',
	            		//tooltip:'Borra una configuración de la cuenta',
	                	handler:function(){
	                			if(getSelectedRecord()!=null){
	                					borrar(getSelectedRecord());
	                			}
	                			else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
	                		}
	            	},
	            	{
	            		id:'grid2ButtonCopiarId',
        				text:getLabelFromMap('grid2ButtonCopiarId', helpMap,'Copiar'),
            			tooltip:getToolTipFromMap('grid2ButtonCopiarId', helpMap,'Copia una configuraci&oacute;n de la cuenta'),
            			//text:'Copiar',
	            		//tooltip:'Copia una configuración de la cuenta',
	                	handler:function(){
	                			if(getSelectedRecord()!=null){
	                					copiar(getSelectedRecord());
	                					//reloadGrid(grid2);
	                			}
	                			else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'))}
	                		}
	            	}
/*	            	{
	            		id:'grid2ButtonRegresarId',
        				text:getLabelFromMap('grid2ButtonRegresarId', helpMap,'Regresar'),
            			tooltip:getToolTipFromMap('grid2ButtonRegresarId', helpMap, 'Regresa a la pantalla anterior')			
	            		
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
				store: _store,
				displayInfo: true,
                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
		    })
		});

    grid2.render();
}

    var incisosForm = new Ext.form.FormPanel({
    	id: 'incisosForm',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosForm', helpMap,'Checklist de Configuraci&oacute;n de la cuenta')+'</span>',
  		//title: '<span style="color:black;font-size:14px;">Checklist de Configuraci&oacute;n de la cuenta</span>',
        iconCls:'logo',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        url:_ACTION_BUSCAR_CHECKLIST_CONFIGURA_CUENTA,
        width: 500,
        height:175,
        bodyStyle:'background: white',
        border:false,
        items: [{
        		layout:'form',
				border: false,
				items:[{
		        labelWidth: 100,
                layout: 'form',
                bodyStyle:'background: white',
                title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
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
        		        				cliente, nombre
		       						]
								},{
								columnWidth:.4,
                				layout: 'form',
                				border: false
                				}]
                			}]}]}],
				buttons:[{
		               		id: 'incisosFormButtonsBuscarId', 
		   					text: getLabelFromMap('incisosFormButtonsBuscarId', helpMap,'Buscar'),
		   					tooltip: getToolTipFromMap('incisosFormButtonsBuscarId', helpMap,'Busca una configuraci&oacute;n'), 
		   					//text:'Buscar',
							handler: function() {
		               			if (incisosForm.form.isValid()) {
		                                           if (grid2!=null) {
		                                            reloadGrid(grid2);
		                                           } else {
		                                            createGrid(grid2);
		                                           }
		            					} else{
											Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
													}			
											}
						  },{
				id: 'incisosFormButtonsCancelarId', 
   				text: getLabelFromMap('incisosFormButtonsCancelarId', helpMap,'Cancelar'),
   				tooltip: getToolTipFromMap('incisosFormButtonsCancelarId', helpMap,'Cancela la b&uacute;squeda'), 
				//text:'Cancelar',
				handler: function() {
							incisosForm.form.reset();
						}
				}	
					 ]
		 
		});

		incisosForm.render();
        createGrid();
 
        function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
    	}
    	
	var store;

	function getSelectedRecord(){
             var m = grid2.getSelections();
             if (m.length == 1 ) {
                return m[0];
             }
        }
        
    function agregar(){
    	window.location=_ACTION_IR_AGREGAR_CHECKLIST_CONFIGURA_CUENTA;
    }
    
    function editar(record){
    	window.location=_ACTION_IR_EDITAR_CHECKLIST_CONFIGURA_CUENTA+"?codigoConfiguracion="+record.get("cdConfig");
    }
    
    if(FLAG==1)reloadGrid(grid2);
});

/*
function reloadGrid(grid2){
    var mStore = grid2.store;
    var o = {start: 0};
    mStore.baseParams = mStore.baseParams || {};
    mStore.baseParams['dsElemen'] = Ext.getCmp('incisosForm').form.findField('cdElemen').getValue();
    mStore.baseParams['dsConfig'] = Ext.getCmp('incisosForm').form.findField('dsConfig').getValue();
    mStore.reload(
              {
                  params:{start:0,limit:itemsPerPage},
                  callback : function(r,options,success) {
                      if (!success) {
                         Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), getLabelFromMap('400033', helpMap,'No se encontraron registros'));
                         mStore.removeAll();
                      }
                  }

              }
            );

}
*/


function reloadGrid(){
	var _params = {
    		dsElemen: Ext.getCmp('incisosForm').form.findField('cdElemen').getValue(),
    		dsConfig: Ext.getCmp('incisosForm').form.findField('dsConfig').getValue()
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
