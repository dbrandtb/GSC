Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";		
	
	var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmAseguradoraId',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('cmAseguradoraId',helpMap,'Aseguradora'),
        dataIndex: 'dsUniEco',
        width: 85,
        sortable: true
        },
        {
        header: getLabelFromMap('cmProductoId',helpMap,'Producto'),
        tooltip: getToolTipFromMap('cmProductoId',helpMap,'Producto'),
        dataIndex: 'dsRamo',
        width: 75,
        sortable: true
        },
        {
        header: getLabelFromMap('cmGrupoId',helpMap,'Grupo'),
        tooltip: getToolTipFromMap('cmGrupoId',helpMap,'Grupo'),
        dataIndex: 'dsElemento',
        width: 110,
        sortable: true
        },
		{       
        header: getLabelFromMap('cmGuionId',helpMap,'Guion'),
        tooltip: getToolTipFromMap('cmGuionId',helpMap,'Guion'),
        dataIndex: 'dsGuion',
        width: 100,
        sortable: true
        },
        {        
        header: getLabelFromMap('cmTipoGuionId',helpMap,'Tipo de Guion'),
        tooltip: getToolTipFromMap('cmTipoGuionId',helpMap,'Tipo de Guion'),
        dataIndex: 'dsTipGuion',
        width: 95,
        sortable: true
        },
        {        
        header: getLabelFromMap('cmOperacionId',helpMap,'Operaci&oacute;n'),
        tooltip: getToolTipFromMap('cmOperacionId',helpMap,'Operaci&oacute;n'),
        dataIndex: 'dsProceso',
        width: 120,
        sortable: true
        },
        {        
        header: getLabelFromMap('cmEstadoId',helpMap,'Estado'),
        tooltip: getToolTipFromMap('cmEstadoId',helpMap,'Estado'),
        dataIndex: 'estado',
        width: 120,
        sortable: true
        },
        {
        dataIndex:'cdUniEco',hidden:true
        },
        {
        dataIndex:'cdRamo',hidden:true
        },
        {
        dataIndex:'cdElemen',hidden:true
        },
        {
        dataIndex:'cdGuion',hidden:true
        },
        {
        dataIndex:'cdTipGuion',hidden:true
        },
        {
        dataIndex:'cdProceso',hidden:true
        }
	]);

	var grid = new Ext.grid.GridPanel({
       		id: 'gridId',
       		el:'gridListado',
            store: storeGridGuion,
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
                  text:getLabelFromMap('gridBtnAgregarId',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('gridBtnAgregarId',helpMap,'Agregar'),
                  handler:function(){
                  agregarGuionLlamadas();/*
						if (getSelectedRecord(grid) != "") {
                        		
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }}*/
                  }},
                  {
                  text:getLabelFromMap('gridBtnEditarId',helpMap,'Editar'),
                  tooltip: getToolTipFromMap('gridBtnEditarId',helpMap,'Editar'),
                  handler:function(){
						editarGuionLlamadas ();
						/*if (getSelectedRecord(grid)) {
                        		window.location = _ACTION_IR_CONSULTA_CASOS+'?cdperson='+getSelectedRecord(grid).get('cdperson');
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }*/
                  }
                  },
                  {
                  text:getLabelFromMap('gridBtnBorrarId',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('gridBtnBorrarId',helpMap,'Eliminar')/*,
                  handler:function(){
						if (getSelectedRecord(gridClientes) != "") {                        		
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                	}*/
                  }, {
                  text:getLabelFromMap('gridBtnExportarId',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('gridBtnExportarId',helpMap,'Exportar'),
                  handler:function(){
                  	/*
						if (getSelectedRecord(grid) != "") {
                        		
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }*/
                  	//consultarCliente();
                        }
                  }, {
                  text:getLabelFromMap('gridBtnRegresarId',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('gridBtnRegresarId',helpMap,'Regresar'),
                  handler:function(){
                  	/*
						if (getSelectedRecord(grid) != "") {
                        		
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }*/
                  	//consultarCliente();
                        }
                  }
                  ],          
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: storeGridGuion,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });          
	
	var cdPerson = new Ext.form.Hidden({id:'cdPersonId', name:'cdPerson'});
	
	//FORMULARIO PRINCIPAL ************************************
	var formPanel = new Ext.FormPanel({			
	        el:'formBusqueda',
	        id: 'acFormPanelId',	        
	        title: '<span style="color:black;font-size:12px;">Guion Llamadas de Entrada/Salida</span>',
	        iconCls:'logo',
	        url: _ACTION_BUSCAR_GUION_LLAMADAS,
	        bodyStyle:'background: white',	              
	        frame:true,
	        width: 700,
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
	           				/*{
	           				   xtype: 'combo',
	           				   tpl: '<tpl for="."><div ext:qtip="{cdElemento}. {cdPerson}. {dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
		                       id:'cmbEmpresaId',
		                       fieldLabel: getLabelFromMap('cmbEmpresaId',helpMap,'Empresa'),
	        				   tooltip: getToolTipFromMap('cmbEmpresaId',helpMap,'Empresa'),
		                       store: storeComboEmpresas,
		                       displayField:'dsElemen',
		                       valueField:'cdElemento',
		                       hiddenName: 'cdElemento',
		                       typeAhead: true,
		                       mode: 'local',
		                       triggerAction: 'all',
		                       width: 210,
		                       emptyText:'Seleccione Empresa...',
		                       selectOnFocus:true,
		                       forceSelection:true,
		                       onSelect: function(record){
		                       			this.setValue(record.get("cdElemento"));
		                       			this.collapse();
		                       			Ext.getCmp("cdPersonId").setValue(record.get("cdPerson"));
		                       		}
						     }*/
						    {
	           					xtype:'textfield',
			            		id: 'txtAseguradoraId',
						        fieldLabel: getLabelFromMap('txtAseguradoraId',helpMap,'Aseguradora'),
						        tooltip:getToolTipFromMap('txtAseguradoraId',helpMap,'Aseguradora'), 	        
						        name: 'dsUniEco',
						        width: 210
						     } 
	           				]
	            		},
	            		{
	            		layout: 'form',
	            		labelWidth: 100,
	            		items: [
	           				{
	           					xtype:'textfield',
			            		id: 'txtOperacionId',
						        fieldLabel: getLabelFromMap('txtOperacionId',helpMap,'Operaci&oacute;n'),
						        tooltip:getToolTipFromMap('txtOperacionId',helpMap,'Operaci&oacute;n'), 	        
						        name: 'dsProceso',
						        width: 200
						     }
	           				]
	            		},
						{
							layout: 'form'
							
						},
						{
	            		layout: 'form', 
	            		labelWidth: 100,         			
	           			items: [
	           				{
	           					xtype:'textfield',
			            		id: 'txtGrupoId',
						        fieldLabel: getLabelFromMap('txtGrupoId',helpMap,'Grupo'),
						        tooltip:getToolTipFromMap('txtGrupoId',helpMap,'Grupo'), 	        
						        name: 'dsElemento',
						        width: 210
						     }
	           				]
	            		}, 
	            		/*{
	            		layout: 'form',              		
	            		colspan:3,        			
	           			items: [
	           				{
	           				   xtype: 'combo',
	           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
		                       id:'cmbGrupoId',
		                       fieldLabel: getLabelFromMap('cmbGrupoId',helpMap,'Grupo'),
	        				   tooltip: getToolTipFromMap('cmbGrupoId',helpMap,'Grupo'),
		                       store: storeComboGrupos,
		                       displayField:'descripcion',
		                       valueField:'codigo',
		                       hiddenName: 'cdGrupo',
		                       typeAhead: true,
		                       mode: 'local',
		                       triggerAction: 'all',
		                       width: 150,
		                       allowBlank: false,
		                       emptyText:'Seleccione Grupo...',
		                       selectOnFocus:true,
		                       forceSelection:true
						     }
	           				]
	            		},*/
	            		{
	            		layout: 'form',
	            		labelWidth: 100,          			
	           			items: [
	           				{
	           					xtype:'textfield',
			            		id: 'txtTipoGuionId',
						        fieldLabel: getLabelFromMap('txtTipoGuionId',helpMap,'Tipo de guion'),
						        tooltip:getToolTipFromMap('txtTipoGuionId',helpMap,'Tipo de guion'), 	        
						        name: 'dsTipGuion',
						        width: 200
						     }
	           				]
	            		},            		
	            		{
							layout: 'form'
							
						},
	            		{            		
	           			layout: 'form',
	           			labelWidth: 100,         			            		
	           			items: [
	           				{
	           					xtype: 'textfield',
	           					id: 'txtGuionId',
						        fieldLabel: getLabelFromMap('txtGuionId',helpMap,'Guion'),
						        tooltip:getToolTipFromMap('txtGuionId',helpMap,'Guion'), 	        
						        name: 'dsGuion',
						        width: 210
						     }
	           				]
	            		},         												            		
	            		{
	            		layout: 'form',
	            		labelWidth: 100,          			
	           			items: [
	           				{
	           					xtype:'textfield',
			            		id: 'txtProductoId',
						        fieldLabel: getLabelFromMap('txtProductoId',helpMap,'Producto'),
						        tooltip:getToolTipFromMap('txtProductoId',helpMap,'Producto'), 	        
						        name: 'dsRamo',
						        width: 200
						     }
	           				]
	            		}
	            		
            		
            		],
            		 buttonAlign: "center",	
            		buttons:[{
    							text:getLabelFromMap('txtBtnBuscarId',helpMap,'Buscar'),
    							tooltip: getToolTipFromMap('txtBtnBuscarId',helpMap,'Busca guiones de llamadas'),
    							handler: function() {
               					if (formPanel.form.isValid()) {
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
    							tooltip: getToolTipFromMap('txtBtnCancelarId',helpMap,'Cancela la b&uacute;squeda de guiones de llamadas'),                              
    							handler: function() {
    								formPanel.form.reset();
    							}
    						}]
    				}]  
	});  
	
	function reloadGrid(){
		var _params = {
       		dsUnieco: Ext.getCmp('txtAseguradoraId').getValue(),
       		dsRamo: Ext.getCmp('txtOperacionId').getValue(),
       		dsElemento: Ext.getCmp('txtGrupoId').getValue(),
       		dsProceso: Ext.getCmp('txtOperacionId').getValue(),
       		dsGuion: Ext.getCmp('txtGuionId').getValue(),
       		dsTipGuion: Ext.getCmp('txtTipoGuionId').getValue()
       		
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
	//console.log(formPanel);
	formPanel.render();
	grid.render();
	//storeComboEmpresas.load();

});