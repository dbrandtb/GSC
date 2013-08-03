Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";		
	
	var storePolizasCliente = new Ext.data.Store({
			proxy:  new Ext.data.HttpProxy({url: _ACTION_OBTENER_POLIZAS}),
			reader: new Ext.data.JsonReader({
						root:'MListPolizas',
						totalProperty: 'totalCount',
						successProperty : '@success'
					},
					[
						{name: 'cdunieco',  type: 'string'},
						{name: 'dsunieco',  type: 'string'},
						{name: 'cdramo',  type: 'string'},
						{name: 'dsramo',  type: 'string'},
						{name: 'estado',  type: 'string'},
						{name: 'nmpoliza',  type: 'string'},
						{name: 'nmpoliex',  type: 'string'},
						{name: 'nmsituaext',  type: 'string'},
						{name: 'nmsbsitext',  type: 'string'},
						{name: 'nombrePersona',  type: 'string'},
						{name: 'cdrfc',  type: 'string'},
						{name: 'dsforpag',  type: 'string'},
						{name: 'suma', type: 'string'},
						{name: 'feinival', type:'string'},
						{name: 'fefinval', type:'string'}
					]
			)				
	});
	
	
	
	var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmEmpresaId',helpMap,'Empresa'),
        tooltip: getToolTipFromMap('cmEmpresaId',helpMap,'Empresa'),
        dataIndex: 'dselemen',
        width: 75,
        sortable: true
        },{
        header: getLabelFromMap('cmNombreId',helpMap,'Nombre'),
        tooltip: getToolTipFromMap('cmNombreId',helpMap,'Nombre'),
        dataIndex: 'dsnombre',
        width: 75,
        sortable: true
        },
        {
        header: getLabelFromMap('cmNominaId',helpMap,'Nomina'),
        tooltip: getToolTipFromMap('cmNominaId',helpMap,'Nomina'),
        dataIndex: 'cdideper',
        width: 75,
        sortable: true
        },
        {
        header: getLabelFromMap('cmClaveCWId',helpMap,'Clave CatWeb'),
        tooltip: getToolTipFromMap('cmClaveCWId',helpMap,'Clave CatWeb'),
        dataIndex: 'cdperson',
        width: 110,
        sortable: true
        },
		{       
        header: getLabelFromMap('cmEmailCId',helpMap,'Email Casa'),
        tooltip: getToolTipFromMap('cmEmailCId',helpMap,'Email Casa'),
        dataIndex: 'mailpersonal',
        width: 100,
        sortable: true
        },
        {        
        header: getLabelFromMap('cmTelCId',helpMap,'Tel. casa'),
        tooltip: getToolTipFromMap('cmTelCId',helpMap,'Tel. casa'),
        dataIndex: 'telefonocasa',
        width: 95,
        sortable: true
        },
        {        
        header: getLabelFromMap('cmEmailEId',helpMap,'Email Empresa'),
        tooltip: getToolTipFromMap('cmEmailEId',helpMap,'Email Empresa'),
        dataIndex: 'mailoficina',
        width: 120,
        sortable: true
        },
        {        
        header: getLabelFromMap('cmTelEId',helpMap,'Tel. Empresa'),
        tooltip: getToolTipFromMap('cmTelEId',helpMap,'Tel. Empresa'),
        dataIndex: 'telefonoofic',
        width: 120,
        sortable: true
        },
        {
        dataIndex:'cdtipper',hidden:true
        },
        {
        dataIndex:'otfisjur',hidden:true
        },
        {
        dataIndex:'otsexo',hidden:true
        },
        {
        dataIndex:'fenacimi',hidden:true
        },
        {
        dataIndex:'cdrfc',hidden:true
        },
        {
        dataIndex:'cdelemen',hidden:true
        }
	]);

	var grillita = new Ext.grid.GridPanel({
       		id: 'grillitaId',
       		el:'gridListado',
            store: storeGridClientes,
            border:true,
            cm: cm,
	        successProperty: 'success', 
	        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',           
            width:700,
            loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
            buttonAlign:'center',
            frame:true,
            height: 320,  
            frame: true,
            buttons:[
                  {
                  text:getLabelFromMap('gridBtn1',helpMap,'Actualizar Datos'),
                  tooltip: getToolTipFromMap('gridBtn1',helpMap,'Actualizar Datos'),
                 /* handler:function(){
						if (getSelectedRecord(grid)) {
						   window.location = _ACTION_IR_PERSONAS+'?cdperson='+getSelectedRecord(grid).get('cdperson');
                        		
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }}*/
                  handler:function(){var codigo=1;
		            			            				
			            			if(getSelectedKey(grillita, "cdperson")!=""){
			                			window.location.href = _ACTION_IR_EDITAR_PERSONA + "?codigoPersona=" + getSelectedKey(grillita, "cdperson")+ "&codigoTipoPersona=" + getSelectedKey(grillita,"otfisjur") + "&cod=" + codigo;
			                		}
			                		else{
			                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));			                			
			                		}
			                	
		                	}
                  
                  
                  },
                  {
                  text:getLabelFromMap('gridBtn2',helpMap,'Consulta de Casos'),
                  tooltip: getToolTipFromMap('gridBtn2',helpMap,'Ir a la pantalla de Consulta de Casos'),
                  handler:function(){
						if (getSelectedRecord(grillita)) {
                        		window.location = _ACTION_IR_CONSULTA_CASOS+'?cdperson='+getSelectedRecord(grillita).get('cdperson')+'&vengoConsClient='+true+'&cdElemento='+getSelectedRecord(grillita).get('cdelemen');
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                  }
                  },
                  /*{
                  text:getLabelFromMap('gridBtn3',helpMap,'Script de Atenci&oacute;n'),
                  tooltip: getToolTipFromMap('gridBtn3',helpMap,'Script de Atenci&oacute;n'),
                  
                	 handler:function(){
						if (getSelectedRecord(grid)) {
                        		window.location = _ACTION_SCRIPT_ATENCION+'?cdperson='+getSelectedRecord(grid).get('cdperson');
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                  }               	
                	
                  },*/
                  
                  	{
                  text:getLabelFromMap('gridBtn3',helpMap,'Script de Atenci&oacute;n'),
                  tooltip: getToolTipFromMap('gridBtn3',helpMap,'Script de Atenci&oacute;n'),
                   handler:function(){                  	
						if (getSelectedRecord(grillita)) {						
						 /*consultarCliente();*/						 			 
						 scriptAtencion(getSelectedRecord(grillita).get("cdperson"),getSelectedRecord(grillita).get("cdelemen"));  
						
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                  	
                        }
                  },
                  		
                  /* handler:function(){                  	
						if (getSelectedRecord(grid)) {						
						 //consultarCliente();						 			 
						 obtenerPolizas(getSelectedRecord(grid).get("cdperson"));                        	
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                  	
                        }*/
                  
                  			
                  				{
                  text:getLabelFromMap('gridBtnEncuesta',helpMap,'Encuesta'),
                  tooltip: getToolTipFromMap('gridBtnEncuesta',helpMap,'Encuesta'),                  
                  handler:function(){                  	
						if (getSelectedRecord(grillita)) {						
						 //consultarCliente();						 			 
						 //obtenerPolizas(getSelectedRecord(grid).get("cdperson"));
						 //obtenerPolizasEncuestas(getSelectedRecord(grid).get("cdperson"));
						 obtenerPolizasEncuestas(getSelectedRecord(grillita));
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                  	
                        }
                  }
                  ],          
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize: itemsPerPage,
					store: storeGridClientes,
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
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('acFormPanelId',helpMap,'Consulta de Cliente')+'</span>',
	        //title: '<span style="color:black;font-size:12px;">Consulta de Cliente</span>',
	        iconCls:'logo',
	        bodyStyle:'background: white',	              
	        frame:true,
	        width: 700,
	        autoHeight:true,	        
            items:[
            		{            		
	        		layout: 'table',
            		layoutConfig: { columns: 3},
            		//columnWidth: .33
            		labelAlign: 'right',
            		items:[
	            		{
	            		html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
	            		colspan:3
	            		},
	            		{
	            		layout: 'form',
	            		labelWidth: 100,  
	            		width: 340,
	           			items: [
	           				/*{
	           				   xtype: 'combo',
	           				   tpl: '<tpl for="."><div ext:qtip="{cdElemento}. {cdPerson}. {dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
		                       id:'cmbEmpresaId',
		                       fieldLabel: getLabelFromMap('cmbEmpresaId',helpMap,'Empresa'),
	        				   tooltip: getToolTipFromMap('cmbEmpresaId',helpMap,'Empresa'),
			                   hasHelpIcon:getHelpIconFromMap('cmbEmpresaId',helpMap),
			                   Ayuda:getHelpTextFromMap('cmbEmpresaId',helpMap),
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
		                      // forceSelection:true,
		                       onSelect: function(record){
		                       			this.setValue(record.get("cdElemento"));
		                       			this.collapse();
		                       			Ext.getCmp("cdPersonId").setValue(record.get("cdPerson"));
		                       		}
						     },*/
						     {
	           					xtype:'textfield',
			            		id: 'txtEmpresaId',
						        fieldLabel: getLabelFromMap('cmbEmpresaId',helpMap,'Empresa'),
						        tooltip:getToolTipFromMap('cmbEmpresaId',helpMap,'Empresa'), 	        
			                    hasHelpIcon:getHelpIconFromMap('cmbEmpresaId',helpMap),
			                    Ayuda:getHelpTextFromMap('cmbEmpresaId',helpMap),
						        name: 'dsEmpresa',
						        width: 210
						     }
	           				]
	            		},
	            		{
	            		layout: 'form',
	            		labelWidth: 100,
	            		width: 340,
	            		items: [
	           				{
	           					xtype:'textfield',
			            		id: 'txtNombreId',
						        fieldLabel: getLabelFromMap('txtNombreId',helpMap,'Nombre'),
						        tooltip:getToolTipFromMap('txtNombreId',helpMap,'Nombre'), 	        
			                    hasHelpIcon:getHelpIconFromMap('txtNombreId',helpMap),
			                    Ayuda:getHelpTextFromMap('txtNombreId',helpMap),
						        name: 'dsnombre',
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
			            		id: 'txtNumNomId',
						        fieldLabel: getLabelFromMap('txtNumNomId',helpMap,'N&uacute;mero de N&oacute;mina'),
						        tooltip:getToolTipFromMap('txtNumNomId',helpMap,'N&uacute;mero de N&oacute;mina'), 	        
			                    hasHelpIcon:getHelpIconFromMap('txtNumNomId',helpMap),
			                    Ayuda:getHelpTextFromMap('txtNumNomId',helpMap),
						        name: 'cdideper',
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
			            		id: 'txtApePatId',
						        fieldLabel: getLabelFromMap('txtApePatId',helpMap,'Apellido Paterno'),
						        tooltip:getToolTipFromMap('txtApePatId',helpMap,'Apellido Paterno'), 	        
			                    hasHelpIcon:getHelpIconFromMap('txtApePatId',helpMap),
			                    Ayuda:getHelpTextFromMap('txtApePatId',helpMap),
						        name: 'dsapellido2',
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
	           					id: 'txtClaveCWClieId',
						        fieldLabel: getLabelFromMap('txtClaveCWClieId',helpMap,'Clave CatWeb para el Cliente'),
						        tooltip:getToolTipFromMap('txtClaveCWClieId',helpMap,'Clave CatWeb para el Cliente'), 	        
			                    hasHelpIcon:getHelpIconFromMap('txtClaveCWClieId',helpMap),
			                    Ayuda:getHelpTextFromMap('txtClaveCWClieId',helpMap),
						        name: 'cdperson',
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
			            		id: 'txtApeMatId',
						        fieldLabel: getLabelFromMap('txtApeMatId',helpMap,'Apellido Materno'),
						        tooltip:getToolTipFromMap('txtApeMatId',helpMap,'Apellido Materno'), 	        
			                    hasHelpIcon:getHelpIconFromMap('txtApeMatId',helpMap),
			                    Ayuda:getHelpTextFromMap('txtApeMatId',helpMap),
						        name: 'dsapellido',
						        width: 200	           					
						     }
	           				]
	            		}
	            		
            		
            		],
            		 buttonAlign: "center",	
            		buttons:[{
    							text:getLabelFromMap('txtBtnBuscarId',helpMap,'Buscar'),
    							tooltip: getToolTipFromMap('txtBtnBuscarId',helpMap,'Busca clientes'),
    							handler: function() {
               					if (formPanel.form.isValid()) {
                                           if (grillita!=null) {reloadGrid();}
            						}
            						else
            						{
            							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
									}
								}
    							},
    							{
    							text:getLabelFromMap('txtBtnCancelarId',helpMap,'Cancelar'),
    							tooltip: getToolTipFromMap('txtBtnCancelarId',helpMap,'Cancela la b&uacute;squeda de clientes'),                              
    							handler: function() {
    								formPanel.form.reset();
    							}
    						}]
    				}]  
	});  
	function obtenerPolizasEncuestas(record){
			if(record != null){
				//console.log(storePolizasCliente);
				//storePolizasCliente.load()
				
				
				storePolizasCliente.load({
											params:{cdElemento: '', 
													cdPerson: record.get("cdperson")
													},
											
											callback: function(r, options, success){
												if(success){
													if(storePolizasCliente.getTotalCount() == 1){
														//alert('Se fue a la Pantalla del PUMA');
														//alert('valor: '+ storePolizasCliente.data.items[0].json.cdunieco);
														//console.log(storePolizasCliente);//.get('cdunieco'));
														window.location= IR_INGRESAR_ENCUESTAS + '?cdunieco='+ storePolizasCliente.data.items[0].json.cdunieco +
																								 '&cdramo='+storePolizasCliente.data.items[0].json.cdramo +
																								 '&nmpoliza='+storePolizasCliente.data.items[0].json.nmpoliza+
																								 '&estado='+storePolizasCliente.data.items[0].json.estado+
																								 '&nmpoliex='+storePolizasCliente.data.items[0].json.nmpoliex+
																								 '&dsunieco='+storePolizasCliente.data.items[0].json.dsunieco+
																								 '&dsramo='+storePolizasCliente.data.items[0].json.dsramo+
																								 '&nombrePersona='+record.get("dsnombre")+
																								 '&cdperson='+record.get("cdperson");
																								  
													}
													else{
														if(storePolizasCliente.getTotalCount() > 1){
																obtenerPolizas(record.get("cdperson"));}
														else{
																Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400088', helpMap,'Cliente no tiene Polizas asociadas.'));
															}
													}
												}else{
													Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400088', helpMap,'Cliente no tiene Polizas asociadas.'));
													}
												}
												}
											);
			}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400088', helpMap,'Cliente no tiene Polizas asociadas.'));
			}
	}
	
	function reloadGrid(){
		var _params = {
       		cdelemento: Ext.getCmp('txtEmpresaId').getValue(),
       		//grupo: Ext.getCmp('cmbGrupoId').getValue(),
       		cdideper: Ext.getCmp('txtNumNomId').getValue(),
       		cdperson: Ext.getCmp('txtClaveCWClieId').getValue(),
       		dsnombre: Ext.getCmp('txtNombreId').getValue(),
       		dsapellido:Ext.getCmp('txtApePatId').getValue(),
       		dsapellido1: Ext.getCmp('txtApeMatId').getValue()
       		};
	
		reloadComponentStore(Ext.getCmp('grillitaId'), _params, cbkReload);
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
	grillita.render();
	//storeComboEmpresas.load();

});