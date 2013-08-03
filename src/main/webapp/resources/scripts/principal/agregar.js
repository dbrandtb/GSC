agregarConfig = function(store){
	
	/***Store que carga el combo de clientes**ok*/
	
	var dataStoreCliente = new Ext.data.Store({
        	proxy: new Ext.data.HttpProxy({
				url: 'principal/clienteAction.action'
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
	
	/*Store que carga el combo de roles*ok*/
	
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
	
/*Ok*/
	var configNombre = new Ext.form.TextField({
				fieldLabel:'Nombre',
				allowBlank:false,
				name:'paginaNombre',
				anchor:'78%'
		});
	
/*ok*/				
	var clienteCombo = new Ext.form.ComboBox({
				tpl: '<tpl for="."><div ext:qtip="{descripcion}. {claveCliente}" class="x-combo-list-item">{descripcion}</div></tpl>',
    			store: dataStoreCliente,
				width: 300,
    			mode: 'local',
				name: 'claveClientePagina',
    			typeAhead: true,
				//labelSeparator:'',
				allowBlank:false,
    			triggerAction: 'all',    		
    			displayField:'descripcion',
				forceSelection: true,
				fieldLabel: "Nivel",
				emptyText:'Seleccione ...',
				selectOnFocus:true
		});  
/*ok*/		
	var rolCombo = new Ext.form.ComboBox({
				tpl: '<tpl for="."><div ext:qtip="{dsRol}. {cdRol}" class="x-combo-list-item">{dsRol}</div></tpl>',
    			store: dataStoreRol,
				width: 300,
    			mode: 'local',
				name: 'claveRolPagina',
    			typeAhead: true,
    			allowBlank:false,
				//labelSeparator:'',			
    			triggerAction: 'all', 
    			forceSelection: true,   		
    			displayField:'dsRol',
    			fieldLabel: "Rol",
				emptyText:'Seleccionar rol...',
				selectOnFocus:true
		});
		
			var oculto = new Ext.form.TextField({
				name:'cualquiera',
				anchor:'90%',
				hiddeParent:true,
      			labelSeparator:'',
      			hidden:true
	});
	
	var agregarForm = new Ext.form.FormPanel({
				url:'principal/guarda.action',
				boder:false,
				frame:true,
				method:'post',            	            
        		width: 470,
        		buttonAlign: "center",
				baseCls:'x-plain',
				labelWidth:75,
				labelAlign:'right',
				bodyStyle:'background:white',
				
				items:[		
				        oculto,			
					configNombre,
					clienteCombo,
					rolCombo
				]
			});
	var window = new Ext.Window({
        title: 'Agregar P&aacute;gina Principal',
        width: 500,
        height:200,
        minWidth: 300,
        minHeight: 200,
        layout: 'fit',
        modal:true,
        plain:true,
        resizable  : false,
        bodyStyle:'padding:5px;',
        bodyStyle:'background:white',
        buttonAlign:'center',
        items: agregarForm,
        buttons: [{
            text: 'Guardar', 
            tooltip:'Guardar P&aacute;gina',
            handler: function() {
                if (agregarForm.form.isValid()) {
	 		        agregarForm.form.submit({
	 		       		url:'principal/guarda.action',
	 		       		waitTitle:'Espere',
			            waitMsg:'Procesando',
			            failure: function(form, action) {
						    /*Ext.MessageBox.alert('Estado','El registro no se guard&oacute');*/
						    Ext.MessageBox.alert('Error',action.result.actionErrors[0], function(btn) {
           							if(btn == 'ok'){
         							}
    								});
						},
						success: function(form, action) {
						    /*Ext.MessageBox.alert('Estado','Registro Guardado');
						    window.close();
						    //store.load();*/
						    Ext.MessageBox.alert('Aviso',action.result.actionMessages[0], function(btn) {
           							if(btn == 'ok'){
           							window.close();
           							store.load();
         							};	
 								});				    
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Aviso', 'Por favor complete la informaci&oacute;n requerida');
				}             
	        }
        },{
            text: 'Cancelar',
            tooltip:'Cancelar',
            handler: function(){window.close();}
        }]
    });
    window.show();
    dataStoreCliente.load();
    dataStoreRol.load();
   
};