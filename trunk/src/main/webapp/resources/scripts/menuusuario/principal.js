Ext.onReady(function(){ 
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
	function menuusuario(){
		url='menuUsuariosJson.action'; //+'?cdRol='+rolForm.getValue()+'&dsElemento='+clienteForm.getValue()+'&dsMenu='+nombreForm.getValue()+'&dsUsuario='+usuarioForm.getValue();
		store= new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url: url
	        }),
			reader: new Ext.data.JsonReader({
				root:'menuList',
				totalProperty:'totalCount',
				id:'cdMenu'
			},[
			{name:'cdMenu',	        type:'string', 		mapping:'cdMenu'},
	        {name:'dsMenu',         type:'string',      mapping:'dsMenu'},
	        {name:'cdElemento',     type:'string',      mapping:'cdElemento'},
	        {name:'dsElemento',     type:'string',      mapping:'dsElemento'},
	        {name:'cdPerson',       type:'string',      mapping:'cdPerson'},
	        {name:'dsPerson',       type:'string',      mapping:'dsPerson'},
			{name:'cdRol', 			type:'string', 		mapping:'cdRol'},
			{name:'dsRol', 			type:'string', 		mapping:'dsRol'},		
			{name:'cdUsuario', 		type:'string', 		mapping:'cdUsuario'},
			{name:'dsUsuario', 		type:'string', 		mapping:'dsUsuario'},
			{name:'cdEstado', 		type:'string', 		mapping:'cdEstado'},
	        {name:'dsEstado',       type:'string',      mapping:'dsEstado'},
	        {name:'cdTipoMenu',     type:'string',      mapping:'cdTipoMenu'},
	        {name:'dsTipoMenu',     type:'string',      mapping:'dsTipoMenu'}
	        		
			])//,
			});
			return store;
	}//Fin menuusuario

	var store;
	var dataStoreCliente = new Ext.data.Store({
        	proxy: new Ext.data.HttpProxy({
				url: 'clienteAction.action'
        	}),
        	reader: new Ext.data.JsonReader({
            	root: 'clientes',
            	id: 'listas'
	        },[
           		{name: 'claveCliente',  type: 'string',  mapping:'claveCliente'},
           		{name: 'descripcion',   type: 'string',  mapping:'descripcion'},
           		{name: 'clavePersona',  type: 'string',  mapping:'clavePersona'}    
        	]),
			remoteSort: true
    	});
	dataStoreCliente.setDefaultSort('listas', 'desc');
    
    var dataStoreUsuarios = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'usuariosAction.action'
            }),
            reader: new Ext.data.JsonReader({
                root: 'usuarios',
                id: 'usu'
            },[
                {name: 'cdUsuario',  type: 'string',  mapping:'cdUsuario'},
                {name: 'dsUsuario',  type: 'string',  mapping:'dsUsuario'}  
            ]),
            remoteSort: true
        });
    dataStoreUsuarios.setDefaultSort('usu', 'desc');
    
    var dataStoreUsuariosNivelRol = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'usuariosNivelRolAction.action'
            }),
            reader: new Ext.data.JsonReader({
                root: 'usuariosNivelRol',
                id: 'usu'
            },[
                {name: 'cdUsuario',  type: 'string',  mapping:'cdUsuario'},
                {name: 'dsUsuario',  type: 'string',  mapping:'dsUsuario'}  
            ]),
            remoteSort: true
        });
    dataStoreUsuariosNivelRol.setDefaultSort('usu', 'desc');
    
	var dataStoreTipo = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url:'tipoMenuAction.action'
		}),
		reader: new Ext.data.JsonReader({
			root:'tiposMenu',
			id:'tip'
		},[
			{name:'clave', 	type:'string', 	mapping:'clave'},
			{name:'valor', 	type:'string', 	mapping:'valor'}
		]),
		remoteSort:true
	});	
	dataStoreTipo.setDefaultSort('tip','desc');
        
    var dataStoreEstado = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url:'estadoAction.action'
        }),
        reader: new Ext.data.JsonReader({
            root:'estados',
            id:'edo'
        },[
            {name:'clave',  type:'string',  mapping:'clave'},
            {name:'valor',  type:'string',  mapping:'valor'}
        ]),
        remoteSort:true
    });    
    dataStoreEstado.setDefaultSort('edo','desc');
    
    var dataStoreRol = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url:'rolAction.action'
        }),
        reader: new Ext.data.JsonReader({
            root:'rolesLista',
            id:'comboRol'
        },[
            {name:'cdRol',  type:'string',  mapping:'cdRol'},
            {name:'dsRol',  type:'string',  mapping:'dsRol'}
        ]),
        remoteSort:true
    });    
    dataStoreRol.setDefaultSort('cdRol','desc');
	
	/******VENTANA COPIAR***********/
    var copiarde = new Ext.form.TextField({
				fieldLabel:'Copiar de',
				hidden:true			
	});
	var claveMenu = new Ext.form.TextField({
            fieldLabel: '',
            allowBlank: false,   
            name:'cdMenu',
            width: 300,
            hiddeParent:true,
            labelSeparator:'',
            hidden:true
    });
	var dsMenu = new Ext.form.TextField({
				fieldLabel:'Nombre',                  
				allowBlank:false,
				name:'dsMenu',
				disabled:false,
				width: 300
		});
	var clienteCombo = new Ext.form.ComboBox({
				tpl: '<tpl for="."><div ext:qtip="{descripcion}. {claveCliente}" class="x-combo-list-item">{descripcion}</div></tpl>',
    			store: dataStoreCliente,
    			disabled:false,
				width: 300,
    			mode: 'local',
				name: 'dsElemento',
				tooltip:'Nivel',
    			typeAhead: true,
				//labelSeparator:'',			
    			triggerAction: 'all',    		
    			displayField:'descripcion',
				forceSelection: true,
				fieldLabel: "Nivel",
				emptyText:'Seleccione ...',
				selectOnFocus:true
		});  
    var usuarioCombo = new Ext.form.ComboBox({
                tpl: '<tpl for="."><div ext:qtip="{dsUsuario}. {cdUsuario}" class="x-combo-list-item">{dsUsuario}</div></tpl>',
                store: dataStoreUsuarios,
                disabled:true,
                width: 300,
                mode: 'local',
                name: 'dsUsuario',
            //  tooltip:'Usuario',
                typeAhead: true,
                //labelSeparator:'',          
                triggerAction: 'all',           
                displayField:'dsUsuario',
                forceSelection: true,
                fieldLabel: "Usuario::",
                emptyText:'Seleccionar un Usuario...',
                selectOnFocus:true
        });  
    var cdRol = new Ext.form.ComboBox({
                tpl: '<tpl for="."><div ext:qtip="{cdRol}.{dsRol}" class="x-combo-list-item">{dsRol}</div></tpl>',
                store: dataStoreRol,
                width: 300,
                mode: 'local',
                name: 'cdRol',
             // tootip:'Rol',
                hiddenName: 'cdRol',
                typeAhead: true,
                //labelSeparator:'',          
                triggerAction: 'all',           
                displayField:'dsRol',
                forceSelection: true,
                fieldLabel: 'Rol',
                valueField:'cdRol',
                emptyText:'Seleccionar un Rol...',
                selectOnFocus:true
        }); 
        
    //Valores de apoyo para editar
    var idClienteCombo = new Ext.form.Hidden({
	    name:'dsElemento'
	});
	//se agrego la clave de cliente porque no es adecuado usar la descripcion y existen problemas... 
	var cdCliente = new Ext.form.Hidden({
	    name:'cdCliente'
	});
	//se agrego la clave de usuario porque no es adecuado usar la descripcion y existen problemas... 
	var idCdUsuario = new Ext.form.Hidden({
	    name:'cdUsuario'
	});
	var idCdRol = new Ext.form.Hidden({
	    name:'cdRol'                                       /////////////
	});
    var idUsuarioCombo = new Ext.form.Hidden({
	    name:'dsUsuario'
	});
	var idTipoCombo = new Ext.form.Hidden({
	    name:'dsTipoMenu'
	});
        
        /******/
    var dsMenuCopia = new Ext.form.TextField({
				fieldLabel:'Nombre',
				allowBlank:false,
				name:'dsMenuCopia',
			//	tooltip:'Nombre',
				anchor:'80%'
		});
	var clienteComboCopia = new Ext.form.ComboBox({
				tpl: '<tpl for="."><div ext:qtip="{descripcion}. {claveCliente}" class="x-combo-list-item">{descripcion}</div></tpl>',
    			store: dataStoreCliente,
				width: 300,
    			mode: 'local',
				name: 'dsElementoCopia',
    			typeAhead: true,
				//labelSeparator:'',			
    			triggerAction: 'all',    		
    			displayField:'descripcion',
				forceSelection: true,
				fieldLabel: "Nivel",
				emptyText:'Seleccione ...',
				selectOnFocus:true,
				allowBlank:false
		});  
    var usuarioComboCopia = new Ext.form.ComboBox({
                tpl: '<tpl for="."><div ext:qtip="{dsUsuario}. {cdUsuario}" class="x-combo-list-item">{dsUsuario}</div></tpl>',
                store: dataStoreUsuarios,
                width: 300,
                mode: 'local',
                name: 'dsUsuarioCopia',
                typeAhead: true,
                //labelSeparator:'',          
                triggerAction: 'all',           
                displayField:'dsUsuario',
                forceSelection: true,
                fieldLabel: "Usuario",
                emptyText:'Seleccionar un Usuario...',
                selectOnFocus:true
        });  
    var cdRolCopia = new Ext.form.ComboBox({
                tpl: '<tpl for="."><div ext:qtip="{cdRol}.{dsRol}" class="x-combo-list-item">{dsRol}</div></tpl>',
                store: dataStoreRol,
                width: 300,
                mode: 'local',
                name: 'cdRolCopia',
                hiddenName: 'cdRolCopia',
                typeAhead: true,
                //labelSeparator:'',          
                triggerAction: 'all',           
                displayField:'dsRol',
                forceSelection: true,
                fieldLabel: 'Rol',
                valueField:'cdRol',
                emptyText:'Seleccionar un Usuario...',
                selectOnFocus:true,
                allowBlank:false
        });
	var copiara = new Ext.form.TextField({
				fieldLabel:'Copiar a',
				hidden:true			
	});
	var copiarForm = new Ext.form.FormPanel({
				id:'copiarForm',
				url:'copiarAction.action',
				boder:false,            	            
        		width: 490,
        		buttonAlign: "center",
				baseCls:'x-plain',
				labelWidth:75,
				bodyStyle:'background: white',
				labelAlign:'right',
				items:[
					claveMenu,
					copiarde,
					dsMenu,
					clienteCombo,
					cdRol,
					usuarioCombo,
					copiara,
					dsMenuCopia,
					clienteComboCopia,
					cdRolCopia,
					usuarioComboCopia
				]
		});	
	var windowCopy = new Ext.Window({
        title: 'Copiar Menú',
        width: 500,
        height: 400,
        minWidth: 450,
        minHeight: 300,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        closeAction:'hide',
        items: copiarForm,
        buttons: [{
            text: 'Copiar', 
            handler: function() {
                if (copiarForm.form.isValid()) {
	 		        copiarForm.form.submit({			      
			            waitMsg:'Procesando...',
			            failure: function(form, action) {
			            	var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    Ext.MessageBox.alert('Estado',mensajeRes);
						    windowCopy.hide();
						    store.load();
						},
						success: function(form, action) {
							var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    Ext.MessageBox.alert('Estado', mensajeRes);
						    windowCopy.hide();
						    store.load();
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Error', 'Complete todos los campos Requeridos');
				}             
	        }
        },{
            text: 'Regresar',
            handler: function(){windowCopy.hide()}               //
        }]
    });	
	/******TERMINA COPIAR********/

	/*****EDITAR CONFIGURACION********/
    //TODO: ver porque estan repetidas las declaraciones de estos controles
    var claveMenu = new Ext.form.TextField({
            fieldLabel: '',
            allowBlank: false,   
            name:'cdMenu',
            anchor:'90%',
            hiddeParent:true,
            labelSeparator:'',
            hidden:true
    });
	var dsMenu = new Ext.form.TextField({
				fieldLabel:'Nombre',
				allowBlank:false,
				name:'dsMenu',
				tooltip:'Nombre',
				anchor:'80%'
		});
	var clienteCombo = new Ext.form.ComboBox({
				tpl: '<tpl for="."><div ext:qtip="{descripcion}. {claveCliente}" class="x-combo-list-item">{descripcion}</div></tpl>',
    			store: dataStoreCliente,
				width: 300,
    			mode: 'local',
				name: 'dsElemento',
				//hiddenName: 'dsElemento',
    			typeAhead: true,
				//labelSeparator:'',			
    			triggerAction: 'all',    		
    			displayField:'descripcion',
				forceSelection: true,
				fieldLabel: "Nivel",
				valueField:'claveCliente',
				emptyText:'Seleccione ...',
				selectOnFocus:true
		});  
    var usuarioCombo = new Ext.form.ComboBox({
                tpl: '<tpl for="."><div ext:qtip="{dsUsuario}. {cdUsuario}" class="x-combo-list-item">{dsUsuario}</div></tpl>',
                store: dataStoreUsuarios,
                width: 300,
                mode: 'local',
                name: 'dsUsuario',
                tooltip:'Usuario',
                hiddenName: 'dsUsuario',
                typeAhead: true,
               //labelSeparator:'',          
                triggerAction: 'all',           
                displayField:'dsUsuario',
                forceSelection: true,
                fieldLabel: "Usuario",
                //valueField:'cdUsuario',
                emptyText:'Seleccionar un Usuario...',
                selectOnFocus:true
        });  
    var cdRol = new Ext.form.ComboBox({
                tpl: '<tpl for="."><div ext:qtip="{cdRol}.{dsRol}" class="x-combo-list-item">{dsRol}</div></tpl>',
                store: dataStoreRol,
                width: 300,
                mode: 'local',
                name: 'cdRol',
                tooltip:'Rol',
                //hiddenName: 'cdRol',
                typeAhead: true,
                //labelSeparator:'',          
                triggerAction: 'all',           
                displayField:'dsRol',
                forceSelection: true,
                fieldLabel: 'Rol',
                //valueField:'cdRol',
                emptyText:'Seleccionar un Rol...',
                selectOnFocus:true
        }); 
    var tipoCombo = new Ext.form.ComboBox({ 
				tpl: '<tpl for="."><div ext:qtip="{valor}. {clave}" class="x-combo-list-item">{valor}</div></tpl>',
    			store: dataStoreTipo,
				width: 300,
    			mode: 'local',
				name: 'dsTipoMenu',
				tooltip:'tipo',
				//hiddenName: 'dsTipoMenu',
    			typeAhead: true,
				//labelSeparator:'',			
    			triggerAction: 'all',    		
    			displayField:'valor',
				fieldLabel: 'Tipo',
				//valueField:'clave',
				emptyText:'Seleccionar tipo...',
				selectOnFocus:true
		});
    var estadoCombo = new Ext.form.ComboBox({ 
                tpl: '<tpl for="."><div ext:qtip="{valor}. {clave}" class="x-combo-list-item">{valor}</div></tpl>',
                store: dataStoreEstado,
                width: 300,
                mode: 'local',
                name: 'dsEstado',
                tooltip:'Estado',
                //hiddenName: 'dsEstado',
                typeAhead: true,
                //labelSeparator:'',          
                triggerAction: 'all',           
                displayField:'valor',
                fieldLabel: 'Estado',
                //valueField:'clave',
                emptyText:'Seleccionar estado...',
                selectOnFocus:true
        });
        
	var editarForm = new Ext.form.FormPanel({
				id:'editarForm',
				url:'editarAction.action',
				boder:false,            	            
        		width: 470,
        		buttonAlign: "center",
				baseCls:'x-plain',
				labelWidth:75,
				labelAlign:'right',
				bodyStyle:'background: white',
		
				items:[
                    claveMenu,
					dsMenu,
					clienteCombo, idClienteCombo,
					cdCliente,idCdUsuario,cdRol, idCdRol,
                    usuarioCombo, idUsuarioCombo,
					tipoCombo, idTipoCombo,
                    estadoCombo
				]
			});
	var window = new Ext.Window({
        title: 'Editar Configuración Opciones del Menú',
        width: 500,
        height:270,
        minWidth: 300,
        minHeight: 250,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        closeAction:'hide', 
        items: editarForm,

        buttons: [{
            text: 'Guardar', 
            handler: function() {
                if (editarForm.form.isValid()) {
                	
	 		        editarForm.form.submit({
	 		       		waitMsg:'Procesando...',
			            failure: function(form, action) {
			            	var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    Ext.MessageBox.alert('Error',mensajeRes);
						    editarForm.form.reset();
						    window.hide();
						    store.load();
						},
						success: function(form, action) {
							var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    Ext.MessageBox.alert('Configuracion Editada', mensajeRes);
						    editarForm.form.reset();
						    window.hide();
						    store.load();						    
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Error!', 'Por favor verifique');
				}             
	        }
        },{
            text: 'Regresar',
            handler: function(){window.hide();}
        }]
    });
	/*******TERMINA EDITAR**********/
    
    /*****se crea la ventana de borrar un menu***/
    var msgBorrar = new Ext.form.TextField({
        fieldLabel:'¿Esta seguro que desea Eliminar el Menu?',
        labelSeparator:'',
        anchor:'90%',
        hidden:true
    });                   
   
    var cdMenu = new Ext.form.NumberField({
        fieldLabel: '',   
        name:'cdMenu',
        anchor:'90%',
        hiddeParent:true,
        //labelSeparator:'',
        hidden:true
    });   
    var borrarForm= new Ext.FormPanel({
        id:'borrarForm',
        labelAlign:'top',
        baseCls: 'x-plain',
        anchor:'100%',
        url:'borrarMenuAction.action',
        items:[ msgBorrar, cdMenu]
    });

    var windowDel = new Ext.Window({
        title: 'Eliminar Opcion',
        minHeight: 50,
        minWidth: 250,
        width: 250,
        height:120,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        closeAction:'hide',
        items: borrarForm,
        buttons: [{
                text: 'Eliminar', 
                handler: function() {
                    if (borrarForm.form.isValid()) {
                            borrarForm.form.submit({          
                                waitMsg:'Procesando...',
                                failure: function(form, action) {
                                    //Ext.MessageBox.alert('Error Borrando Menu', action.result.errorInfo);
                                	var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    		Ext.MessageBox.alert('Aviso',mensajeRes);
                                    windowDel.hide();
                                },
                                success: function(form, action) {
                                    //Ext.MessageBox.alert('Menu Borrado', action.result.info);
                                	var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    		Ext.MessageBox.alert('Aviso',mensajeRes);
                                    windowDel.hide();
                                    gridConf.destroy();
                                    createGrid();
                                    store.load();                                      
                                }
                            });                   
                    }else{
                        Ext.MessageBox.alert('Error', 'Verifique!');
                    }             
                }
            },{
                text: 'Cancelar',
                handler: function(){windowDel.hide();}
            }]
        });
/*****/
	
	var nombreForm = new Ext.form.TextField({
        fieldLabel:'Nombre',                                              ///////////
        name:'dsMenu',
        tooltip:'Nombre',
        maxLength: 120,
		maxLengthText: "El tama&ntilde;o m&aacute;ximo para este campo es 120",
        width:250
    }); 
    var clienteForm = new Ext.form.ComboBox({
                tpl: '<tpl for="."><div ext:qtip="{descripcion}. {claveCliente}" class="x-combo-list-item">{descripcion}</div></tpl>',
                store: dataStoreCliente,
                width: 250,
                mode: 'local',
                name: 'dsElemento',
                tooltip:'Nivel',               
                typeAhead: true,
                //labelSeparator:'',          
                triggerAction: 'all',           
                displayField:'descripcion',
                forceSelection: true,
                fieldLabel: "Nivel",
                emptyText:'Seleccione un Nivel...',
                selectOnFocus:true
    }); 
	var rolForm= new Ext.form.TextField({
        fieldLabel:'Rol',
        name: 'cdRol',
        tooltip:'Rol',
        maxLength: 120,
		maxLengthText: "El tama&ntilde;o m&aacute;ximo para este campo es 120",
        width:250
    });
    var usuarioForm = new Ext.form.ComboBox({
                tpl: '<tpl for="."><div ext:qtip="{dsUsuario}. {cdUsuario}" class="x-combo-list-item">{dsUsuario}</div></tpl>',
                store: dataStoreUsuarios,
                width: 250,
                mode: 'local',
                name: 'dsUsuario',
                tooltip:'Usuario',
                typeAhead: true,
                //labelSeparator:'',          
                triggerAction: 'all',           
                displayField:'dsUsuario',
                forceSelection: true,
                fieldLabel: "Usuario",
                emptyText:'Seleccione un Usuario...',
                selectOnFocus:true
    });
   	
	var menuUsuarioForm = new Ext.form.FormPanel({
		el:'formMenu',
		title:'<span style="color:black;font-size:14px;">Men&uacute; de Usuario</span>',
		//iconCls:'logo',
		bodyStyle:'background:white',
		buttonAlign:'center',
		labelAlign:'right',
		frame:true,
		url:_ACTION_PRINCIPAL,
		width:600,
		height:220,
		items:[{
				layout:'form',
				border:false,
				items:[{
					title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">B&uacute;squeda</span>',
					bodyStyle:'background:white',
					labelWidth:50,
					layout:'form',
					frame:true,
					baseCls:'',
					buttonAlign:'center',
					items:[{
							layout:'column',
							border:false,
							columnWidth:1.1,
							html:'&nbsp',
							items:[{
									columnWidth:.6,
									layout:'form',
									border:false,
									items:[
                                            nombreForm,
											clienteForm,
											rolForm,    
                                            usuarioForm
										   ]
									},{
										columnWidth:.3,
										layout:'form'
										}]
							}],
									buttons:[{
											text:'Buscar',
											handler:function(){
												if(menuUsuarioForm.form.isValid()){
														var _params = {
															dsMenu: nombreForm.getValue(),
															dsElemento: clienteForm.getValue(),
															cdRol: rolForm.getValue(),
															dsUsuario: usuarioForm.getValue()
														};

														reloadComponentStore(gridConf, _params, cbkReload);
													
													}else{
															Ext.MessageBox.alert('Error','Inserte Par&aacute;metros de B&uacute;squeda!');
															}
														}
													},{
													text:'Cancelar',
													handler:function(){
														menuUsuarioForm.form.reset();
													}
											}]
						}]
			 }]
		});						
	menuUsuarioForm.render();
	function toggleDetails(btn, pressed){
		var view = grid.getView();
		view.showPreview=pressed;
		view.refresh();
	}
	
	function cbkReload (_r, _options, _success, _store) {
		_success = eval (_store.reader.jsonData.exito);
		if (!_success) {
			//Ext.Msg.alert('Error', _store.reader.jsonData.mensajeRespuesta);
			Ext.Msg.alert('Aviso', _store.reader.jsonData.mensajeRespuesta);
			_store.removeAll();
		}
	}
	
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
        {header:'Nombre',       dataIndex:'dsMenu',      width:120,  sortable:true},
		{header:'Cliente', 	    dataIndex:'dsElemento',  width:140,  sortable:true},
        {header:'Rol',          dataIndex:'dsRol',       width:150,  sortable:true},
        {header:'Usuario',      dataIndex:'dsUsuario',   width:150,  sortable:true},
        {header:'Tipo',         dataIndex:'dsTipoMenu',  width:150,  sortable:true},
		{header:'Estado', 	    dataIndex:'dsEstado', 	 width:150,	 sortable:true}
	]);
	cm.defaultSortable=true;
	var gridConf;
	var selectedId;
	var recGridConf;
	function createGrid(){
		gridConf = new Ext.grid.GridPanel({
		store: menuusuario(),
		id:'lista-lista',
		border:true,
		buttonAlign:'center',
		cm: cm,
		buttons:[
				{text:'Agregar',
            	tooltip:'Agrega nueva configuraci&oacute;n del Men&uacute;',
            	handler: function(){
            		agregarMenuAplicacion(store);
        		}
            	},{
            	id:'editar',
            	text:'Editar',
            	tooltip:'Editar Men&uacute; seleccionado',
                handler:function(){
                   if(!gridConf.getSelectionModel().getSelected()){
                   	  Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                     }
                   }
            	},{
            	text:'Eliminar',
            	id:'borrar',
            	tooltip:'Elimina Men&uacute; seleccionado',
                handler:function(){
                   if(!gridConf.getSelectionModel().getSelected()){
                   	  Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                     }
                   }
            	},{
            	id:'copiar',
            	text:'Copiar',
            	tooltip:'Copia Men&uacute; seleccionado',
                handler:function(){
                   if(!gridConf.getSelectionModel().getSelected()){
                   	  Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                     }
                   }
            	},{
            	text:'Exportar',
            	tooltip:'Exporta Men&uacute; Usuarios',
            	handler: exportButton(_ACTION_EXPORT_MENU_USUARIO)
            	},{
                text:'Configurar',
                id:'configuraOpciones',
                tooltip:'Configura Opciones',
                handler:function(){
                   if(!gridConf.getSelectionModel().getSelected()){
                   	  Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                     }
                   }
            	}],            	
        width:600,
        frame:true,
        height:590,
        title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
        //renderTo:document.body,
    	sm:new Ext.grid.RowSelectionModel({
    	singleSelect:true,
    	listeners: {                            
                        rowselect: function(sm, row, rec) {
                        		selectedId = store.data.items[row].id;
                        		recGridConf = rec;
                        		Ext.getCmp('borrar').on('click', function(){
                                	if(!gridConf.getSelectionModel().getSelected()){
				                   	  Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
				                    } else {
	                                	if(selectedId==null){
	                                		Ext.MessageBox.alert('Informaci&oacute;n','No se ha seleccionado ningun registro');
	                                	}else{
	                                		windowDel.show();
	                                		Ext.getCmp('borrarForm').getForm().loadRecord(rec);
	                                	}
				                    }
                                });                                                                                                                           
                                Ext.getCmp('editar').on('click',function(){
                                	if(!gridConf.getSelectionModel().getSelected()){
				                   	    Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
				                    } else {
	                                	//Se cargan valores a las variables de apoyo para enviarlas con el form
	                                	dataStoreEstado.load();
	                                	
	                                	idClienteCombo.setValue(clienteCombo.getValue());
	                                	idCdRol.setValue(rec.get('dsRol'));
	                    				idUsuarioCombo.setValue(usuarioCombo.getValue());
	                    				idTipoCombo.setValue(rec.get('dsTipoMenu'));
										cdCliente.setValue(rec.get('cdElemento'));
										idCdUsuario.setValue(rec.get('cdUsuario'));
	                                	
	                        			window.show();
	                                    Ext.getCmp('editarForm').getForm().loadRecord(rec);
	                                    clienteCombo.disable();
										cdRol.disable();
	                    				usuarioCombo.disable();
										tipoCombo.disable();
				                    }
                                });
                                Ext.getCmp('configuraOpciones').on('click',function(){
                                	if(!gridConf.getSelectionModel().getSelected()){
				                   	  Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
				                    } else {
                                      OpcionesVentana(store, rec, row);
                                	}
                                });
                                Ext.getCmp('copiar').on('click',function(){
                                	if(!gridConf.getSelectionModel().getSelected()){
				                   	    Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
				                    } else {
				                    	copiarForm.form.reset();
	                                	dataStoreRol.load();
	                                	dataStoreEstado.load();
	                        			windowCopy.show();
	                                    Ext.getCmp('copiarForm').getForm().loadRecord(rec);
				                    }
                                });                                  
                        }
                }
    	}),
    bbar: new Ext.PagingToolbar({
		pageSize: 20,
		store: store,									            
		displayInfo: true,
		displayMsg: 'Registros mostrados {0} - {1} de {2}',
		emptyMsg: 'No hay registros para mostrar',
		beforePageText: 'P&aacute;gina',
		afterPageText: 'de {0}'
		})
		});
		gridConf.render('gridConfig');
	}
	
agregarMenuAplicacion = function(store){
	
	var dsMenu = new Ext.form.TextField({
				fieldLabel:'Nombre',
				allowBlank:false,
				name:'dsMenu',
				tooltip:'Nombre',
				anchor:'80%'
		});
		
	var clienteComboAgregar = new Ext.form.ComboBox({
				tpl: '<tpl for="."><div ext:qtip="{descripcion}. {claveCliente}" class="x-combo-list-item">{descripcion}</div></tpl>',
    			store: dataStoreCliente,
    			allowBlank:false,
				width: 300,
    			mode: 'local',
				name: 'dsPerson',
				tooltip:'Nivel',
				hiddenName :'dsPerson',
    			typeAhead: true,
				//labelSeparator:'',			
    			triggerAction: 'all',    		
    			displayField:'descripcion',
				forceSelection: true,
				fieldLabel: "Nivel",
				valueField:'claveCliente',
				emptyText:'Seleccione...',
				selectOnFocus:true
		});  
		
	clienteComboAgregar.on('select', function(){    
	    dataStoreUsuariosNivelRol.removeAll();
	  	usuarioComboNivelRol.emptyText='Seleccionar un usuario...';
	    usuarioComboNivelRol.reset();
	    dataStoreUsuariosNivelRol.baseParams['claveCliente'] = clienteComboAgregar.getValue();	    
	});
	
    var usuarioComboNivelRol = new Ext.form.ComboBox({
                tpl: '<tpl for="."><div ext:qtip="{dsUsuario}. {cdUsuario}" class="x-combo-list-item">{dsUsuario}</div></tpl>',
                store: dataStoreUsuariosNivelRol,
                width: 300,
                mode: 'local',
                name: 'dsUsuario',
                tooltip:'Usuario',
                hiddenName: 'dsUsuario',
                typeAhead: true,
                //labelSeparator:'',          
                triggerAction: 'all',           
                displayField:'dsUsuario',
                forceSelection: true,
                fieldLabel: "Usuario",
                valueField:'cdUsuario', 
                emptyText:'Seleccionar un Usuario...',
                selectOnFocus:true
        });
       
    var cdRol = new Ext.form.ComboBox({
                tpl: '<tpl for="."><div ext:qtip="{cdRol}.{dsRol}" class="x-combo-list-item">{dsRol}</div></tpl>',
                store: dataStoreRol,
                allowBlank:false,
                width: 300,
                mode: 'local',
                name: 'cdRol',
                tooltip:'Rol',
                hiddenName: 'cdRol',
                typeAhead: true,
                //labelSeparator:'',          
                triggerAction: 'all',           
                displayField:'dsRol',
                forceSelection: true,
                fieldLabel: 'Rol',
                valueField:'cdRol',
                emptyText:'Seleccionar un Rol...',
                selectOnFocus:true
        }); 
        
    cdRol.on('select', function(){
	  	usuarioComboNivelRol.emptyText='Seleccionar un Usuario...';
	    usuarioComboNivelRol.reset();
	    dataStoreUsuariosNivelRol.baseParams['cdRol'] =  cdRol.getValue();
	    dataStoreUsuariosNivelRol.load({
	                  callback : function(r, options, success) {
	                      if (!success) {
	                         Ext.MessageBox.alert('Aviso','No se encontraron registros para el rol seleccionado');  
	                         dataStoreUsuariosNivelRol.removeAll();
	                      }
	                  }
	
	              }
	            );
    });  
    var tipoCombo = new Ext.form.ComboBox({ 
				tpl: '<tpl for="."><div ext:qtip="{valor}. {clave}" class="x-combo-list-item">{valor}</div></tpl>',
    			store: dataStoreTipo,
    			allowBlank:false,
				width: 300,
    			mode: 'local',
				name: 'dsTipoMenu',
				tooltip:'Tipo',
				hiddenName: 'dsTipoMenu',
    			typeAhead: true,
				//labelSeparator:'',			
    			triggerAction: 'all',    		
    			displayField:'valor',
				fieldLabel: 'Tipo',
				valueField:'clave',
				emptyText:'Seleccionar tipo...',
				selectOnFocus:true
		});
    var estadoCombo = new Ext.form.ComboBox({ 
                tpl: '<tpl for="."><div ext:qtip="{valor}. {clave}" class="x-combo-list-item">{valor}</div></tpl>',
                store: dataStoreEstado,
                allowBlank:false,
                width: 300,
                mode: 'local',
                name: 'dsEstado',
                tooltip:'Estado',
                hiddenName: 'dsEstado',
                typeAhead: true,
                //labelSeparator:'',          
                triggerAction: 'all',           
                displayField:'valor',
                fieldLabel: 'Estado',
                valueField:'clave',
                emptyText:'Seleccionar estado...',
                selectOnFocus:true
        });
	var agregarForm = new Ext.form.FormPanel({
				url:'guardarAction.action',
				boder:false,
				frame:true,
				method:'post',            	            
				//fileUpload: true,
        		width: 470,
        		buttonAlign: "center",
				baseCls:'x-plain',
				labelWidth:75,
				labelAlign:'right',
				bodyStyle:'background: white',
				
				items:[
					dsMenu,
					clienteComboAgregar,
					cdRol,
                    usuarioComboNivelRol,
					tipoCombo,
                    estadoCombo
				]
			});
	var window = new Ext.Window({
        title: 'Configurar Opciones del Men&uacute;',
        width: 500,
        height:250,
        minWidth: 400,
        minHeight: 250,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: agregarForm,
        buttons: [{
            text: 'Guardar', 
 	        tooltip:'Guarda configuraci&oacute;n del Men&uacute;',
            handler: function() {
                if (agregarForm.form.isValid()) {
                	//alert(fileLoad.getValue());
	 		        agregarForm.form.submit({
	 		       		url:'guardarAction.action',			      
			            waitMsg:'Procesando...',
			            failure: function(form, action) {
			            	var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    Ext.MessageBox.alert('Error',mensajeRes);
						    window.close();
						    store.load();
						},
						success: function(form, action) {
							var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    Ext.MessageBox.alert('Aviso', mensajeRes);
						    window.close();
						    store.load();
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Aviso', 'Debe completar los campos requeridos.');
				}             
	        }
        },{
            text: 'Cancelar',
  	        tooltip:'Cancela configuraci&oacute;n del Men&uacute;', 	
            handler: function(){window.hide();}
        }]
    });    
    dataStoreRol.load();
    dataStoreTipo.load();    
    dataStoreEstado.load();
    window.show();
}
	createGrid();
	dataStoreCliente.load();
    dataStoreUsuarios.load();

}); 