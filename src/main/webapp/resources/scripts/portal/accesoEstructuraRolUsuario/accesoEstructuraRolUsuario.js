// PANTALLA EMERGENTE
function agregar(_record){
	var formWindow = new Ext.FormPanel({
	        id: 'formWindowId',
	        iconCls:'logo',
	        bodyStyle:'background: white',	              
	        frame:true,
	        width: 500,
	        autoHeight:true,	        
            items:[
            		{            		
	        		layout: 'table',
            		layoutConfig: { columns: 1},
            		labelAlign: 'right',
            		autoWidth:true,
            		items:[
            			
	            		{
	            		html: '<br/><span class="x-form-item" style="font-weight:bold">Agregar Acceso</span>'
	            		},
	            		{
	            		html: '<br/>',
	            		width:400
	            		},
	            		{
	            		layout: 'form',  
	            		labelWidth: 80,   			
	           			items: [{
	           				   xtype: 'combo',
	           				   tpl: '<tpl for="."><div ext:qtip="{cdElemento}. {cdPerson}. {dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
		                       id:'cmbNivelId',
		                       fieldLabel:getLabelFromMap('cmbNivelId',helpMap,'Nivel'),
	    					   tooltip: getToolTipFromMap('cmbNivelId',helpMap,'Nivel'),
		                       store: storeComboNivel,
		                       displayField:'dsElemen',
		                       valueField:'cdElemento',
		                       hiddenName: 'cdElemento',
		                       typeAhead: true,
		                       mode: 'local',
		                       triggerAction: 'all',
		                       //width: 200,
		                       anchor: '80%',
		                       allowBlank: false,
		                       emptyText:'Seleccione Nivel...',
		                       selectOnFocus:true,
		                       forceSelection:true
					     	}]
            			},
            			{
	            		layout: 'form',  
	            		labelWidth: 80,   			
	           			items: [{
	           				   xtype: 'combo',
	           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {codigo2}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
		                       id:'cmbRolId',
		                       fieldLabel:getLabelFromMap('cmbRolId',helpMap,'Rol'),
	    					   tooltip: getToolTipFromMap('cmbRolId',helpMap,'Rol'),
		                       store: storeComboRol,
		                       displayField:'descripcion',
		                       valueField:'codigo',
		                       hiddenName: 'cdRol',
		                       typeAhead: true,
		                       mode: 'local',
		                       triggerAction: 'all',
		                       //width: 200,
		                       anchor: '80%',
		                       allowBlank: false,
		                       emptyText:'Seleccione Rol...',
		                       selectOnFocus:true,
		                       forceSelection:true,
		                       onSelect:function(_record){
		                       		this.setValue(_record.get("codigo"));
		                       		this.collapse();
		                       		storeComboUsuario.load({
		                       			params:{cdsisrol: Ext.getCmp("cmbRolId").getValue()}
		                       		});
		                       }
					     	}]
            			},
            			{
	            		layout: 'form',  
	            		labelWidth: 80,   			
	           			items: [{
	           				   xtype: 'combo',
	           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
		                       id:'cmbUsuarioId',
		                       fieldLabel:getLabelFromMap('cmbUsuarioId',helpMap,'Usuario'),
	    					   tooltip: getToolTipFromMap('cmbUsuarioId',helpMap,'Usuario'),
		                       store: storeComboUsuario,
		                       displayField:'descripcion',
		                       valueField:'codigo',
		                       hiddenName: 'cdUsuario',
		                       typeAhead: true,
		                       mode: 'local',
		                       triggerAction: 'all',
		                       //width: 200,
		                       anchor: '80%',
		                       allowBlank: false,
		                       emptyText:'Seleccione Usuario...',
		                       selectOnFocus:true,
		                       forceSelection:true
					     	}]
            			},
            			{
	            		layout: 'form',  
	            		labelWidth: 80,  			
	           			items: [{
	           				   xtype: 'combo',
	           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
		                       id:'cmbEstadoId',
		                       fieldLabel:getLabelFromMap('cmbEstadoId',helpMap,'Estado'),
	    					   tooltip: getToolTipFromMap('cmbEstadoId',helpMap,'Estado'),
		                       store: storeComboEstado,
		                       displayField:'descripcion',
		                       valueField:'codigo',
		                       hiddenName: 'cdEstado',
		                       typeAhead: true,
		                       mode: 'local',
		                       triggerAction: 'all',
		                       //width: 200,
		                       anchor: '80%',
		                       //allowBlank: false,
		                       emptyText:'Seleccione Estado...',
		                       selectOnFocus:true,
		                       forceSelection:true
					     	}]
            			},
            			{
	            		html: '<br/>'
	            		}]
	    			}]  
	});
	
	
	var ventana = new Ext.Window ({
	    //title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('',helpMap,'Consultar o Ingresar Movimiento')+'</span>',
	    width: 500,
	    modal: true,
	    items: formWindow,
	    buttonAlign: "center",
		buttons:[
			{
			text:getLabelFromMap('btnGuardarId',helpMap,'Guardar'),
			tooltip: getToolTipFromMap('btnGuardarId',helpMap,'Guardar un nuevo acceso'),
			handler: function() {
	   			if(formWindow.form.isValid()){guardarDatos();
				}else{
					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
					}
				}
			},
			{
			text:getLabelFromMap('btnRegresarId',helpMap,'Regresar'),
			tooltip: getToolTipFromMap('btnRegresarId',helpMap,'Regresar a la pantalla anterior'),                              
			handler: function(){ventana.close();}
			}
		]
	});
	
	
	storeComboNivel.load({
		callback:function(){
			storeComboRol.load({
				callback:function(){
					storeComboEstado.load({
								params:{cdTabla:"TACTIVO"}
							});
					/*storeComboUsuario.load({
						params:{cdsisrol:Ext.getCmp("cmbRolId").getValue()},
						callback:function(){							
						}
					});*/					
				}
			});
		}
	});	

	ventana.show();
	
	function guardarDatos(){
			var  _params = "";	
			_params += "&cdNivel="+Ext.getCmp("cmbNivelId").getValue();
			_params += "&cdRol="+Ext.getCmp("cmbRolId").getValue();
			_params += "&cdUsuario="+Ext.getCmp("cmbUsuarioId").getValue();
			_params += "&cdEstado="+Ext.getCmp("cmbEstadoId").getValue();
			 			  
			execConnection(_ACTION_GUARDAR_ACCESO_ESTRUCTURA_ROL_USUARIO, _params, cbkGuardar);			
	}
	
	function cbkGuardar (_success, _message, _response){
			if(!_success){Ext.Msg.alert('Error', _message);
			}else{Ext.Msg.alert('Aviso', _message,function(){ventana.close();reloadGrid()});}
	}		
	
};

// PANTALLA PRINCIPAL
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";		
	
	var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmNivelId',helpMap,'Nivel'),
        tooltip: getToolTipFromMap('cmNivelId',helpMap,'Nivel'),
        dataIndex: 'dsElemen',
        width: 125,
        sortable: true
        },
        {
        header: getLabelFromMap('cmRolId',helpMap,'Rol'),
        tooltip: getToolTipFromMap('cmRolId',helpMap,'Rol'),
        dataIndex: 'dsRol',
        width: 125,
        sortable: true
        },
        {
        header: getLabelFromMap('cmClaveId',helpMap,'Clave'),
        tooltip: getToolTipFromMap('cmClaveId',helpMap,'Clave'),
        dataIndex: 'cdUsuario',
        width: 125,
        sortable: true
        },
        {
        header: getLabelFromMap('cmUsuarioId',helpMap,'Usuario'),
        tooltip: getToolTipFromMap('cmUsuarioId',helpMap,'Usuario'),
        dataIndex: 'dsNombre',
        width: 125,
        sortable: true
        },
		{       
        header: getLabelFromMap('cmEstadoId',helpMap,'Estado'),
        tooltip: getToolTipFromMap('cmEstadoId',helpMap,'Estado'),
        dataIndex: 'dsEstado',
        width: 125,
        sortable: true
        },
        {
        dataIndex:'cdElemento',hidden:true
        },
        {
        dataIndex:'cdEstado',hidden:true
        },
        {
        dataIndex:'cdRol',hidden:true
        }
	]);

	var grid = new Ext.grid.GridPanel({
       		id: 'gridId',
       		el:'grilla',
            store: storeGrilla,
            border:true,
            cm: cm,
	        successProperty: 'success', 
	        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',           
            width:500,
            loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
            buttonAlign:'center',
            frame:true,
            height:320,  
            frame: true,
            buttons:[
                  {
                  text:getLabelFromMap('gridBtn1',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('gridBtn1',helpMap,'Agrega una nueva estructura por rol de usuario'),
                  handler:function(){agregar(getSelectedRecord(grid))}
                  },
                  {
                  text:getLabelFromMap('gridBtn2',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('gridBtn2',helpMap,'Elimina una estructura por rol de usuario seleccionada del grid'),
                  handler:function(){
						if (getSelectedRecord(grid)) {
                        		borrar(getSelectedRecord(grid));
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                  }
                  },
                  {
                  text:getLabelFromMap('gridBtn3',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('gridBtn3',helpMap,'Exporta los datos del listado en un determinado formato'),
                   handler:function(){
                        var url = _ACTION_EXPORTAR_ACCESO_ESTRUCTURA_ROL_USUARIO + '?dsNivel=' + formPanel.form.findField('dsNivel').getValue() + '&dsRol=' + formPanel.form.findField('dsRol').getValue() + '&dsUsuario=' + formPanel.form.findField('dsUsuario').getValue() + '&dsEstado=' + formPanel.form.findField('dsEstado').getValue();
                        //Ext.getCmp('incisosForm').form.findField('dsNumCaso').getValue(),
       		            showExportDialog( url );
                    }
                  }/*,
                  {
                  text:getLabelFromMap('gridBtn4',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('gridBtn4',helpMap,'Regresa a la Pantalla Anterior'),
                  handler: function(){
                  		window.location = _VOLVER_A_ALGUN_LADO;
                  }
                  }*/
                  ],          
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize: itemsPerPage,
					store: storeGrilla,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });          
		
	//FORMULARIO PRINCIPAL ************************************
	var formPanel = new Ext.FormPanel({			
	        el:'formulario',
	        id: 'formPanelId',	        
	        title: '<span style="color:black;font-size:12px;">Acceso a la Estructura por Rol - Usuario</span>',
	        iconCls:'logo',	        	             
	        frame:true,
	        width: 500,
	        autoHeight:true,	        
            items:[{
            	bodyStyle:'background: white', 
            	items:[
            		{            		
	        		layout: 'table',
            		layoutConfig: { columns: 3, columnWidth: .33},
            		labelAlign: 'right',
            		labelWidth: 70,
            		items:[
	            		{
	            		html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
	            		colspan:3
	            		},
	            		{
	            		layout: 'form',
	            		items: [{
	           					xtype:'textfield',
			            		id: 'txtNivelId',
						        fieldLabel: getLabelFromMap('txtNivelId',helpMap,'Nivel'),
						        tooltip:getToolTipFromMap('txtNivelId',helpMap,'Nivel del usuario'), 	        
						        name: 'dsNivel',
						        width: 150
						 }]
		            	},
	            		{
	            		layout: 'form',
	            		items: [{
	           					xtype:'textfield',
			            		id: 'txtRolId',
						        fieldLabel: getLabelFromMap('txtRolId',helpMap,'Rol'),
						        tooltip:getToolTipFromMap('txtRolId',helpMap,'Rol del usuario'), 	        
						        name: 'dsRol',
						        width: 150
						     }]
	            		},
						{
							layout: 'form'							
						},
						{
	            		layout: 'form',         			
	           			items: [{
	           					xtype:'textfield',
			            		id: 'txtUsuarioId',
						        fieldLabel: getLabelFromMap('txtUsuarioId',helpMap,'Usuario'),
						        tooltip:getToolTipFromMap('txtUsuarioId',helpMap,'Usuario'), 	        
						        name: 'dsUsuario',
						        width: 150
						     }]
	            		}, 	            		
	            		{
	            		layout: 'form',          			
	           			items: [{
	           					xtype:'textfield',
			            		id: 'txtEstadoId',
						        fieldLabel: getLabelFromMap('txtEstadoId',helpMap,'Estado'),
						        tooltip:getToolTipFromMap('txtEstadoId',helpMap,'Estado'), 	        
						        name: 'dsEstado',
						        width: 150
						     }]
	            		},            		
	            		{
							layout: 'form'								
						}
					  ],
					  buttonAlign: "center",	
	           		  buttons:[
	           				{
  							text:getLabelFromMap('txtBtnBuscarId',helpMap,'Buscar'),
  							tooltip: getToolTipFromMap('txtBtnBuscarId',helpMap,'Busca Acceso a la estructura por rol de usuario'),
  							handler: function(){
              					if (formPanel.form.isValid()){if(grid!=null) {reloadGrid();}}
           						else{
           							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
									}
								}
  							},
  							{
  							text:getLabelFromMap('txtBtnCancelarId',helpMap,'Cancelar'),
  							tooltip: getToolTipFromMap('txtBtnCancelarId',helpMap,'Cancela la b&uacute;squeda'),                              
  							handler: function(){formPanel.form.reset();}
  							}
	  					]
					 }
            		]           		
            	}]           		
   		});  		
	
	function borrar(_record) {
		if(_record){		   
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn){
		        if (btn == "yes"){
         			var _params = {cdNivel: _record.get("cdElemento"), cdUsuario: _record.get("cdUsuario"), cdsisrol: _record.get("cdRol")};

         			execConnection(_ACTION_BORRAR_ACCESO_ESTRUCTURA_ROL_USUARIO, _params, cbkConnection);
               }
			})
		}
		else{
			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		}
	}
	
	function cbkConnection(_success, _message, _response){
			if(!_success){Ext.Msg.alert('Error', _message);
			}else{Ext.Msg.alert('Aviso', _message,function(){reloadGrid()});}
	}
	
	formPanel.render();
	grid.render();
	
});

function reloadGrid(){
		var _params = {
       		dsNivel: Ext.getCmp('txtNivelId').getValue(),       		
       		dsRol: Ext.getCmp('txtRolId').getValue(),
       		dsUsuario: Ext.getCmp('txtUsuarioId').getValue(),
       		dsEstado: Ext.getCmp('txtEstadoId').getValue()
       		};
	
		reloadComponentStore(Ext.getCmp('gridId'), _params, cbkReload);
	};
	
	function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
		}
	}