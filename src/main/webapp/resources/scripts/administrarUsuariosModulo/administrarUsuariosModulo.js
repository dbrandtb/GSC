Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";			
	var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmTipoArId',helpMap,'M&oacute;dulo'),
        tooltip: getToolTipFromMap('cmTipoArId',helpMap,'M&oacute;dulo'),
        dataIndex: 'dsModulo',
        width: 250,
        sortable: true
        },
        {
        header: getLabelFromMap('cmNumeroFaxId',helpMap,'Usuarios'),
        tooltip: getToolTipFromMap('cmNumeroFaxId',helpMap,'Usuarios'),
        dataIndex: 'dsUsuario',
        width: 300,
        sortable: true
        },
               
       {
        dataIndex:'cdModulo',hidden:true
        },
        {
        dataIndex:'cdUsuario',hidden:true
        }
       /* {
        dataIndex:'dsusuari',hidden:true
        },
        {
        dataIndex:'ferecepcion',hidden:true
        },
         
         {
        dataIndex:'blarchivo',hidden:true
        },  
         {
        dataIndex:'otvalor',hidden:true
        },       
        {
        dataIndex:'dstipoar',hidden:true
        }*/
	]);
	
	
	
	var grid = new Ext.grid.GridPanel({
       		id: 'gridId',
       		el:'gridListado',
            store: storeGridFax,
            border:true,
            cm: cm,
	        successProperty: 'success', 
	        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',           
            width:554,
            loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
            buttonAlign:'center',
            frame:true,
            height:320,  
            frame: true,
           buttons:[
                  {
                  text:getLabelFromMap('gridBtn1',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('gridBtn1',helpMap,'Agrega Usuario'),
                  handler:function(){administrarUsuarioModuloEmergente()}
                  },                  
                                                 
                    {
                  text:getLabelFromMap('gridBtn4',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('gridBtn4',helpMap,'Regresa al menu principal '),
                  handler:function(){window.close}
                  },
                  
                  {
                  text:getLabelFromMap('gridBtn2',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('gridBtn2',helpMap,'Elimina un usuario seleccionado del grid'),                  
                  handler:function(){
						if (getSelectedKey(grid, "cdModulo") != "") {
                                borrar(getSelectedKey(grid, "cdModulo"),getSelectedKey(grid, "cdUsuario"));
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }}
                  },
                  
                 
                
                 {
                  text:getLabelFromMap('gridTrsCtBoButtonExportar',helpMap,'Exportar'),
                  tooltip:getToolTipFromMap('gridTrsCtBoButtonExportar',helpMap,'Exportar un Usuario'),
                  handler:function(){
                        //alert(comboCodiPrioridad.getValue());
                        var url = _ACTION_EXPORTAR_USUARIO_MODULO + '?pv_dsmodulo_i=' + Ext.getCmp('txtArchivoId').getValue() + '&pv_dsusuario_i='+ Ext.getCmp('txtNumCasoId').getValue();
                        showExportDialog( url );        
                    }                 
                  }//,
                  
                  ],          
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: storeGridFax,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });          
	
	grid.render();
	
	//FORMULARIO PRINCIPAL ************************************
	var formPanelFax = new Ext.FormPanel({			
	        el:'formBusqueda',
	        id: 'formPanelFax',	        
	        title: '<span style="color:black;font-size:12px;">Administrar Usuarios por M&oacute;dulo</span>',
	        iconCls:'logo',
	        bodyStyle:'background: white',	
	        buttonAlign: "center",              
	        frame:true,
	        width: 554,
	        autoHeight:true,	        
            items:[
            		{            		
	        		layout: 'table',
            		layoutConfig: { columns: 1, columnWidth: .33},
            		labelAlign: 'right',
            		items:[
	            		{
	            		html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
	            		colspan:3
	            		},
	            								
						{
	            		layout: 'form', 
	            		labelWidth: 120,         			
	           			items: [
	           				{
	           					xtype:'textfield',
			            		id: 'txtArchivoId',
						        fieldLabel: getLabelFromMap('txtArchivoId',helpMap,'M&oacute;dulo'),
						        tooltip:getToolTipFromMap('txtArchivoId',helpMap,'M&oacute;dulo'), 	        
						        name: 'dsModulo',
						        width: 200
						     }
	           				]
	            		}, 
	            		
	            		{
	            		layout: 'form', 
	            		labelWidth: 120,         			
	           			items: [
	           				{
	           					xtype:'textfield',
			            		id: 'txtNumCasoId',
						        fieldLabel: getLabelFromMap('txtNumCasoId',helpMap,'Usuario'),
						        tooltip:getToolTipFromMap('txtNumCasoId',helpMap,'Usuario'), 	        
						        name: 'dsUsuario',
						        width: 200
						     }
	           				]
	            		}//, 
	            		
	                		           		
            		
            		],
            		 buttonAlign: "center",	
            		 buttons:[{
    							text:getLabelFromMap('txtBtnBuscarId',helpMap,'Buscar'),
    							tooltip: getToolTipFromMap('txtBtnBuscarId',helpMap,'Busca Usuario'),
    							handler: function() {
               					if (formPanelFax.form.isValid()) {
                                           if (grid!=null) {reloadGrid();}
            						}
            						else
            						{
            							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
									}
								}
    							},
    							{
    							text:getLabelFromMap('txtBtnCancelarId',helpMap,'Cancelar'),
    							tooltip: getToolTipFromMap('txtBtnCancelarId',helpMap,'Cancela la b&uacute;squeda de usuario'),                              
    							handler: function() {
    						    formPanelFax.form.reset();
    							}
    						}]
    				}]  
	});  
	
	
	//console.log(formPanelFax);
	formPanelFax.render();
	//grid.render();
	
	
	function borrar(cdModulo,cdUsuario) {
		if(cdModulo != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						cdUsuario: cdUsuario,
         						cdModulo: cdModulo
         			};
         			execConnection( _ACTION_BORRAR_MODULO, _params, cbkConnection);
               }
			})
		}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		}
};
function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()});
	}
}     
   
   function reloadGrid(){
		var _params = {
       		dsModulo: Ext.getCmp('txtArchivoId').getValue(),
       		dsUsuario: Ext.getCmp('txtNumCasoId').getValue()   
       		   		
       		
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
  
        
   	
});

 
