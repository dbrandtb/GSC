Ext.onReady(function(){  //alert('principal');
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
function test(){
	url='principal/paginas.action'+'?rolPagina='+rolForm.getValue()+'&clientePagina='+ clienteForm.getValue()+'&nombrePagina='+nombreForm.getValue();
	store= new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: url
        }),
		reader: new Ext.data.JsonReader({
			root:'paginasConfiguradas',
			totalProperty:'totalCount',
			id:'claveConfiguracion',
            successProperty : '@success'
		},[
		{name:'claveConfiguracion',	type:'string', 		mapping:'claveConfiguracion'},
		{name:'claveRol', 			type:'string', 		mapping:'claveRol'},
		{name:'claveElemento', 		type:'string', 		mapping:'claveElemento'},
		{name:'dsConfiguracion', 	type:'string', 		mapping:'dsConfiguracion'},
		{name:'dsElemento', 		type:'string', 		mapping:'dsElemento'},
		{name:'claveSistemaRol', 	type:'string', 		mapping:'claveSistemaRol'},
		{name:'dsSistemaRol', 		type:'string', 		mapping:'dsSistemaRol'},
		{name:'claveEstado', 		type:'string', 		mapping:'claveEstado'}
		
		])
		});
		store.setDefaultSort('dsConfiguracion','ASC');
		//store.load();
		return store;
	}
	
	//var store;
	
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
			remoteSort: true
    	});
	dataStoreCliente.setDefaultSort('listas', 'desc');
	
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
			remoteSort:true
		});
	dataStoreRol.setDefaultSort('rol','desc');
	
	/******VENTANA COPIAR***********/
copiarPrincipal = function(cdCli,cdRo){
	
	/*
		private String idClienteNvo;
	private String idRolNvo; 
	*/
	
	var idCliente = new Ext.form.Hidden({
				fieldLabel:'idCliente',
				value:cdCli,
				allowBlank:false,
				name:'idCliente'				
		});
	var idRol = new Ext.form.Hidden({
				fieldLabel:'idRol',
				value:cdRo,
				allowBlank:false,
				name:'idRol'
		});
	
	var dsElemento = new Ext.form.TextField({
				fieldLabel:'Nivel',
				allowBlank:false,
				name:'dsElemento',
				anchor:'90%',
				disabled:true
		});
	var dsSistemaRol = new Ext.form.TextField({
				fieldLabel:'Copiar de Rol',
				allowBlank:false,
				name:'dsSistemaRol',
				anchor:'90%',
				disabled:true
		});
	var letterA = new Ext.form.TextField({
				fieldLabel:'A',
				hidden:true			
	});
	var cliente = new Ext.form.ComboBox({
				tpl: '<tpl for="."><div ext:qtip="{dsElemen}. {claveCliente}" class="x-combo-list-item">{dsElemen}</div></tpl>',
    			store: dataStoreCliente,
				width: 300,
    			mode: 'local',
				name: 'clienteCopia',
    			typeAhead: true,
				labelSeparator:'',			
    			triggerAction: 'all',    		
    			displayField:'dsElemen',
				forceSelection: true,
				allowBlank:false,
				fieldLabel: "Nivel:",
				emptyText:'Seleccione ...',
				selectOnFocus:true
		});  
	var rol = new Ext.form.ComboBox({
				tpl: '<tpl for="."><div ext:qtip="{dsRol}. {cdRol}" class="x-combo-list-item">{dsRol}</div></tpl>',
    			store: dataStoreRol,
				width: 300,
    			mode: 'local',
				name: 'rolCopia',
    			typeAhead: true,
				labelSeparator:'',
    			triggerAction: 'all',
    			forceSelection: true,
    			allowBlank:false,
    			displayField:'dsRol',
				fieldLabel: 'Copiar a Rol:',
				emptyText:'Seleccionar rol...',
				selectOnFocus:true
		});
	var copiarForm = new Ext.form.FormPanel({
				id:'copiarForm',
				url:'principal/copiaPagina.action',
				boder:false,            	            
        		width: 490,
        		buttonAlign: "center",
				baseCls:'x-plain',
				labelWidth:75,
				items:[
					idCliente,
					idRol,
					dsElemento,
					dsSistemaRol,
					letterA,
					cliente,
					rol				
				]
		});	
	var windowCopy = new Ext.Window({
        title: 'Copiar Configuración P&aacute;gina Principal',
        width: 500,
        height:210,
        minWidth: 450,
        minHeight: 200,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: copiarForm,
        buttons: [{
            text: 'Copiar', 
            handler: function() {
                if (copiarForm.form.isValid()) {
	 		        copiarForm.form.submit({
	 		        	waitTitle:'Espere',
			            waitMsg:'Procesando',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('Estado','Error copiando configuraci&oacute;n');
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('Estado','Configuracion Copiada');
						    windowCopy.close();
						    store.load();
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Error', 'Complete todos los campos Requeridos');
				}             
	        }
        },{
            text: 'Regresar',
            handler: function(){windowCopy.close();}
        }]
    });
    windowCopy.show();
};
	/******TERMINA COPIAR********/

	/*****EDITAR CONFIGURACION********/
function editarPrincipal(record){		
	var claveConfiguracion = new Ext.form.TextField({
				name:'claveConfiguracion',
				value: record.get('claveConfiguracion'),
				anchor:'90%',
				hiddeParent:true,
      			labelSeparator:'',
      			hidden:true
	});
	
	var claveSistemaRol = new Ext.form.TextField({
				name:'claveSistemaRol',
				value: record.get('claveSistemaRol'),
				anchor:'90%',
				hiddeParent:true,
      			labelSeparator:'',
      			hidden:true
	});
	var claveElemento = new Ext.form.TextField({
				name:'claveElemento',
				value: record.get('claveElemento'),
				anchor:'90%',
				hiddeParent:true,
      			labelSeparator:'',
      			hidden:true
	});
	var dsConfiguracion = new Ext.form.TextField({
				fieldLabel:'Nombre',
				allowBlank:false,
				value: record.get('dsConfiguracion'),
				name:'dsConfiguracion',
				anchor:'78%'
		});
	var dsElemento = new Ext.form.ComboBox({
				tpl: '<tpl for="."><div ext:qtip="{dsElemen}. {claveCliente}" class="x-combo-list-item">{dsElemen}</div></tpl>',
    			store: dataStoreCliente,
				width: 300,
    			mode: 'local',
				name: 'dsElemento',
				value: record.get('dsElemento'),
    			typeAhead: true,
				//labelSeparator:'',			
    			triggerAction: 'all',    		
    			displayField:'dsElemen',
				forceSelection: true,
				fieldLabel: "Nivel",
				emptyText:'Seleccione ...',
				selectOnFocus:true,
				//readOnly:true
				disabled:true
				
		});  
	var dsSistemaRol = new Ext.form.ComboBox({
				tpl: '<tpl for="."><div ext:qtip="{dsRol}. {cdRol}" class="x-combo-list-item">{dsRol}</div></tpl>',
    			store: dataStoreRol,
				width: 300,
    			mode: 'local',
				name: 'dsSistemaRol',
				value: record.get('dsSistemaRol'),
    			typeAhead: true,
				//labelSeparator:'',			
    			triggerAction: 'all', 
    			forceSelection: true,   		
    			displayField:'dsRol',
				fieldLabel: "Rol",
				emptyText:'Seleccionar rol...',
				selectOnFocus:true,
				//readOnly:true
				disabled:true
			
		});

	var editarForm = new Ext.form.FormPanel({
				id:'editarForm',
				url:_ACTION_EDITAR,
				boder:false, 
				frame: true,           	            
        		width: 470,
        		buttonAlign: "center",
				baseCls:'x-plain',
				labelWidth:75,
				labelAlign:'right',
				bodyStyle:'background:white',
		
				items:[
					claveConfiguracion,
					dsConfiguracion,
					dsElemento,
					dsSistemaRol,
					claveElemento,
					claveSistemaRol
					
				]
			});
	var windowEdit = new Ext.Window({
        title: 'Editar Configuraci&oacute;n P&aacute;gina Principal',
        width: 500,
        height:200,
        minWidth: 300,
        minHeight: 200,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        //bodyStyle:'background:white',
        buttonAlign:'center',
        items: editarForm,

        buttons: [{
            text: 'Guardar', 
            handler: function() {
                if (editarForm.form.isValid()) {
                	
	 		        editarForm.form.submit({
	 		       		url:_ACTION_EDITAR,
	 		       		waitTitle:'Espere',
			            waitMsg:'Procesando..',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('Estado','Error editando p&aacute;gina');
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('Estado','Pagina Editada');
						    editarForm.form.reset();
						    windowEdit.close();
						    store.load();
						    
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Error!', 'Porfavor verifique');
				}             
	        }
        },{
            text: 'Regresar',
            handler: function(){windowEdit.close();}
        }]
    });
    windowEdit.show();
};
	/*******TERMINA EDITAR**********/
	
	var rolForm= new Ext.form.TextField({
		fieldLabel:'Rol',
		name: 'rolPagina',
		width:200
	});
	var clienteForm = new Ext.form.TextField({
		fieldLabel:'Nivel',
		name:'clientePagina',
		width:200
	});
	var nombreForm = new Ext.form.TextField({
		fieldLabel:'Nombre',
		name:'nombrePagina',
		width:200
	});
   	
	var configuraForm = new Ext.form.FormPanel({
		el:'formPag',
		title:'<span style="color:#000000; font-size:12px; font-family:Arial,Helvetica,sans-serif">P&aacute;ginas Principales</span>',
		//iconCls:'logo',
		bodyStyle:'background:white',
		buttonAlign:'center',
		labelAlign:'right',
		frame:true,
		url:_ACTION_PRINCIPAL,
		width:700,
		height:180,
		//border:true,
		items:[{
				layout:'form',
				border:false,
				items:[{
					title:'<span class="x-form-item" style="color:#98012e; font-size:14px; font-family:Arial,Helvetica,sans-serif;">B&uacute;squeda</span>',
					bodyStyle:'background:white',
					//labelWidth: 200,
					layout:'form',
					frame:true,
					baseCls:'',
					buttonAlign:'center',
					border:false,
					items:[
							{
							layout:'column',
							border:true,
							//columnwidth: 1,
							items:[
									{
										columnWidth:.6,
										layout:'form',
										border:false,
										items:[
												nombreForm,
												rolForm,									
												clienteForm
											   ]
									},
									{
										columnWidth:.3,
										layout:'form'
									}
									]
									}],
									buttons:[{
											text:'Buscar',
											tooltip:'Busca una P&aacute;gina',
											handler:function(){
												if(configuraForm.form.isValid()){
														configuraForm.form.submit({
															waitTitle:'Espere',
															waitMsg:'Procesando...',
															failure:function(form, action){
																Ext.MessageBox.alert('Aviso','No se encontraron datos');
																gridConf.destroy();
																createGrid();
																store.load();	
																
															},
															success:function(form, action){
																gridConf.destroy();
																createGrid();
																store.load();	
																}
														});
													}else{
															Ext.MessageBox.alert('Error','Inserte Parametros de B&uacute;squeda!');
															}
														}
													},{
													text:'Cancelar',
													tooltip:'Cancela la Operaci&oacute;n',
													handler:function(){
														configuraForm.form.reset();
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
	
	
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header:'Nombre',		dataIndex:'dsConfiguracion', 	width:180, 	sortable:true},
		{header:'Rol', 			dataIndex:'dsSistemaRol', 		width:180, 	sortable:true},
		{header:'Nivel', 		dataIndex:'dsElemento', 		width:180, 	sortable:true},
		{header:'Clave Rol', 	dataIndex:'claveRol', 		    width:10, 	hidden: true ,sortable:true},
		{header:'Clave Elemento',dataIndex:'claveElemento', 		width:10, hidden: true ,sortable:true}
				 
	]); 
	var gridConf;
	var selectedId;
	var confRow;
	var cidNombre="";
	var cidRol="";
	var cidCliente="";
	var cdsNombre="";
	var cdsRol="";
	var cdsCliente="";
	function getSelectedRecord(){
             var m = gridConf.getSelections();
             if (m.length == 1 ) {
                return m[0];
             }
        }
	function createGrid(){
		gridConf = new Ext.grid.GridPanel({
		store: test(),
		id:'lista-lista',
		border:true,
		buttonAlign:'center',
		//baseCls:' background:white',
		cm: cm,
		buttons:[
				{text:'Agregar',
            	tooltip:'Agregar una nueva P&aacute;gina',
            	handler: function(){
            		agregarConfig(store);
        		}
            	},/*{
            	text:'<< Regresar',
            	tooltip:'Regresar a la P&aacute;gina anterior'        	
            	},*/{
            	id:'editar',
            	text:'Editar',
            	tooltip:'Edita P&aacute;gina seleccionada',
            	handler: function(){
            	if (getSelectedRecord() != null) {
            			editarPrincipal(getSelectedRecord());                   
                        Ext.getCmp('editarForm').getForm().loadRecord(getSelectedRecord());                      	                                	                          
	                } else {
    	                Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
        	        }
            	}
            	},{
            	text:'Eliminar',
            	id:'borrar-grid',
            	tooltip:'Elimina P&aacute;gina seleccionada',
            	handler: function(){
            	if (getSelectedRecord() != null) {
            			borrarConfig(store, selectedId, configuraForm );	                                                      	                                	                          
	                } else {
    	                Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
        	        }
            	}
            	},{
            	id:'copiar',
            	text:'Copiar',
            	tooltip:'Copia P&aacute;gina seleccionada',
            	handler: function(){
            	if (getSelectedRecord() != null) {
            			copiarPrincipal(getSelectedRecord().data.claveElemento,getSelectedRecord().data.claveRol);              
                        Ext.getCmp('copiarForm').getForm().loadRecord(getSelectedRecord());
                        console.log(getSelectedRecord());
                                  	                                	                          
	                } else {
    	                Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
        	        }
            	}
            	},{
            	text:'Exportar',
            	tooltip:'Exporta P&aacute;ginas',
            	handler: exportButton( _ACTION_EXPORT_CONFIG)
            	},{
            	text:'Configurar',
            	id:'configPagina',
            	tooltip:'Configurar P&aacute;ginas'
            	}],
        width:700,
        frame:true,
        height:250,

        title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
        //renderTo:document.body,
    	sm:new Ext.grid.RowSelectionModel({
    	singleSelect:true,
    	listeners: {                            
                        rowselect: function(sm, row, rec) {
                        		selectedId = store.data.items[row].id;
                        		confRow = row;
								cidNombre=rec.get('claveConfiguracion');
								cidRol=rec.get('claveRol');
								cidCliente=rec.get('claveElemento');
								cdsNombre=rec.get('dsConfiguracion');
								cdsRol=rec.get('dsSistemaRol');
								cdsCliente=rec.get('dsElemento');
                        }
                }

    	}),
    //viewConfig: {autoFill: true,forceFit:true},                
	bbar: new Ext.PagingToolbar({
		pageSize: itemsPerPage,
		store: store,									            
		displayInfo: true,
		displayMsg: 'Registros mostrados {0} - {1} de {2}',
		emptyMsg: 'No hay registros para mostrar',
		beforePageText: 'P&aacute;gina',
		afterPageText: 'de {0}' 		    	
		})        		
          
	});
	gridConf.render('gridPag');
	
    Ext.getCmp('configPagina').on('click',function(){
    	//alert(confRow);
    	//var params= "numeroFila="+confRow;
    	if(confRow !=null){
/*		var conn = new Ext.data.Connection();
        conn.request ({                		
		        url: _ACTION_CONFIGURACION,
         		method: 'POST',
         		successProperty : '@success',
         		params : params,							                
			    waitMsg: 'Espere por favor....'
    	}); */
    		//alert('cidNombre-'+cidNombre+'-cidRol-'+cidRol+'-cidCliente-'+cidCliente+'-cdsNombre-'+cdsNombre+'-cdsNombre-'+cdsNombre+'-cdsRol-'+cdsRol+'-cdsCliente-'+cdsCliente);
			window.location.href = _ACTION_CONFIGPAG+"?claveConfiguracion="+cidNombre+"&claveSistemaRol="+cidRol+"&claveElemento="+cidCliente+"&dsConfiguracion="+cdsNombre+"&dsSistemaRol="+cdsRol+"&dsElemento="+cdsCliente;
    	}else{
    		Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
    	}
    });
     
	}
	createGrid();
	dataStoreCliente.load();
    dataStoreRol.load();
}); 