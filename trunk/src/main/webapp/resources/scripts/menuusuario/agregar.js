agregarMenu = function(store){
	
	/***Store que carga el combo de clientes***/
	
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
    
    /***Store que carga el combo de usuarios***/
    
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
       		
	/*Store que carga el combo de tipos*/
	
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
    
    /*Store que carga el combo de tipos*/
    
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

    /*Store que carga el combo de tipos*/
    
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
/*    var cdRol = new Ext.form.TextField({
                fieldLabel:'Rol',
                allowBlank:false,
                name:'cdRol',
                anchor:'90%'
        });*/
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
					clienteCombo,
					cdRol,
                    usuarioCombo,
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
					Ext.MessageBox.alert('Aviso', 'Debe Seleccionar los campos requeridos.');
				}             
	        }
        },{
            text: 'Cancelar',
  	        tooltip:'Cancela configuraci&oacute;n del Men&uacute;', 	
            handler: function(){window.hide();}
        }]
    });
    window.show();
    dataStoreCliente.load();
    dataStoreUsuarios.load();
    dataStoreTipo.load();
    dataStoreEstado.load(); 
    dataStoreRol.load();
};