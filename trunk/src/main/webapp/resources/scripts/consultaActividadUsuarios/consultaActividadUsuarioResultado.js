Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";		
	//alert(FECHA_INICIAL);
	//alert(FECHA_FINAL);
	//alert(HORA_INICIAL);
	//alert(HORA_FINAL);
	

	var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmNombreId',helpMap,'Nombre'),
        tooltip: getToolTipFromMap('cmNombreId',helpMap,'Nombre'),
        dataIndex: 'dsUsuari',
        width: 180,
        sortable: true
        },
        {
        header: getLabelFromMap('cmNominaId',helpMap,'Fecha'),
        tooltip: getToolTipFromMap('cmNominaId',helpMap,'Fecha'),
        dataIndex: 'timeStamp',
        width: 150,
        sortable: true
        },
        {
        header: getLabelFromMap('cmClaveCWId',helpMap,'Hora'),
        tooltip: getToolTipFromMap('cmClaveCWId',helpMap,'Hora'),
        dataIndex: 'dsHora',
        width: 150,
        sortable: true,
        hidden:true
        },
		{       
        header: getLabelFromMap('cmEmailCId',helpMap,'Actividad o Evento'),
        tooltip: getToolTipFromMap('cmEmailCId',helpMap,'Actividad o Evento'),
        dataIndex: 'url',
        width: 400,
        sortable: true
        }
	]);

	var grid = new Ext.grid.GridPanel({
       		id: 'gridId',
       		el:'gridElementos',
            store: storeGrillaActividadUsuario,
            border:true,
            cm: cm,
	        successProperty: 'success', 
	        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',           
            width:700,
            loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
            buttonAlign:'center',
            frame:true,
            height:320,  
            frame: true,
            buttons:[
                  {
                  text:getLabelFromMap('cmdExportar',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('cmdExportar',helpMap,'Exportar Datos'),
                    handler:function(){
                    	//alert(grid.getBottomToolbar().cursor);
                        var url = _ACTION_BUSCAR_ACTIVIDAD_USUARIO_EXPORTAR + '?pv_dsusuari_i=' + usr 
                        													+ '&pv_url_i=' + Ext.getCmp('cmbActEvId').getValue()
                        													+ '&feInicial='+FECHA_INICIAL 
                        													+ '&feFinal=' + FECHA_FINAL
                        													+ '&hrInicial='+ HORA_INICIAL
																			+ '&hrFinal='+ HORA_FINAL;
																			//+ '&inicioExport='+ grid.getBottomToolbar().cursor;
																			
                        showExportDialog( url );
                    } 
                  },{
                  text:getLabelFromMap('cmdRegresar',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('cmdRegresar',helpMap,'Regresar a la pantalla anterior'),
    			  handler: function() {
               					
                                           //ir a pantalla de activfidad de usuario
               						  window.location = _ACTION_IR_ACTIVIDAD_USUARIO;
            				
            						
								}
                  }
                  ],          
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store: storeGrillaActividadUsuario,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });          
	
	var cdPerson = new Ext.form.Hidden({id:'cdPersonId', name:'cdPerson'});
	//alert(FECHA_INICIAL);
	//alert(FECHA_FINAL);
	//FORMULARIO PRINCIPAL ************************************
	var formPanel = new Ext.FormPanel({			
	        el:'formBusqueda',
	        id: 'acFormPanelId',	        
	        title: '<span style="color:black;font-size:12px;">Consulta Actividad de Usuarios</span>',
	        iconCls:'logo',
	        bodyStyle:'background: white',	              
	        frame:true,
	        width: 700,
	        url: _ACTION_BUSCAR_ACTIVIDAD_USUARIO,
	        baseParams:{
                          pv_dsusuari_i: usr,
					      pv_url_i: "",
					      feInicial:FECHA_INICIAL,
					      feFinal:FECHA_FINAL
                       },
            reader:jsonGrillaActividad,
	        autoHeight:true,	        
            items:[
            		{            		
	        		layout: 'table',
            		layoutConfig: { columns: 3, columnWidth: .33},
            		labelAlign: 'right',
            		items:[
	            		{
	            		html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
	            		colspan:3
	            		},
	            		{
	            		layout: 'form',
	            		labelWidth: 100,   			
	           			items: [
	           				{
	           				   xtype: 'textfield',
	           				   id:'cmbActEvId',
		                       fieldLabel: getLabelFromMap('cmbEmpresaId',helpMap,'Actividad o Evento'),
	        				   tooltip: getToolTipFromMap('cmbEmpresaId',helpMap,'Actividad o Evento'),
		                       hiddenName: 'dsActEv',
		                       typeAhead: true,
		                       width: 300
		                       
						     }
	           				]
	            		}],
            		 buttonAlign: "center",	
            		buttons:[{
    							text:getLabelFromMap('txtBtnBuscarId',helpMap,'Buscar'),
    							tooltip: getToolTipFromMap('txtBtnBuscarId',helpMap,'Busca Actividad o eventos'),
    							handler: function() {
               					if (formPanel.form.isValid()) {
                                           if (grid!=null) {reloadGrid();}
            						}
            						else
            						{
            							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
									}
								}
    							},{
        							text:getLabelFromMap('ntfcnButtonCancelar',helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('ntfcnButtonCancelar',helpMap,'Cancelar busqueda de Actividad o Evento'),                              
        							handler: function() {
        								formPanel.form.reset();
        							}
        						}]
    				}]  
	});  
	//console.log(grid.getBottomToolbar().cursor);
	function reloadGrid(){
	var _params = {
		    		pv_dsusuari_i: usr,
					pv_url_i: Ext.getCmp('cmbActEvId').getValue(),
					feInicial:FECHA_INICIAL,
					feFinal:FECHA_FINAL,
					hrInicial:HORA_INICIAL,
					hrFinal:HORA_FINAL
					
       		      };
       		   
	
		reloadComponentStore(Ext.getCmp('gridId'), _params, cbkReload);
		
	};
	
	function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert('Aviso', _store.reader.jsonData.actionErrors[0]);
		}
	}
	//console.log(formPanel);
	formPanel.render();
	grid.render();
	reloadGrid();
   //grid.load();
});