function guardar(_record,accion){
    	//CONFIGURACION PROXY PARA LOS COMBOS *********************************	
   		var _proxy_clientesCorpo = new Ext.data.HttpProxy({url: _ACTION_OBTENER_CLIENTES_CORPO});
   		var _proxy_aseguradoras = new Ext.data.HttpProxy({url: _ACTION_OBTENER_ASEGURADORAS});
   		var _proxy_prod_aseg_corpo = new Ext.data.HttpProxy({url: _ACTION_OBTENER_PRODUCTOS_ASEG_CORPO});
   		var _proxy_planes = new Ext.data.HttpProxy({url: _ACTION_OBTENER_PLANES});
   		var _proxy_nroPolizas = new Ext.data.HttpProxy({url: _ACTION_OBTENER_NRO_POLIZAS});
   		var _proxy_PolizasMaestras = new Ext.data.HttpProxy({url: _ACTION_OBTENER_POLIZAS_MAESTRAS});
   		var _proxy_valores = new Ext.data.HttpProxy({url: _ACTION_OBTENER_VALORES});   		

   		//CONFIGURACION READERS PARA LOS COMBOS *****************************
   		var _reader_clientesCorpo = new Ext.data.JsonReader({
	     		root: 'clientesCorp',
	     		totalProperty: 'totalCount',
	     		id: 'cdElemento'
	     		},
	     		[
    			{name: 'cdElemento', type: 'string',mapping:'cdElemento'},
    			{name: 'dsElemen', type: 'string',mapping:'dsElemen'},
    			{name: 'cdPerson', type: 'string',mapping:'cdPerson'}
	 			]
	 		);
	 	
	 	var _reader_aseguradoras = new Ext.data.JsonReader({
	     		root: 'aseguradoraComboBox',
	     		totalProperty: 'totalCount',
	     		id: 'cdUniEco'
	     		},
	     		[
    			{name: 'cdUniEco', type: 'string',mapping:'cdUniEco'},
    			{name: 'dsUniEco', type: 'string',mapping:'dsUniEco'}
	 			]
	 		);
	 	
	 	var _reader_prod_aseg_corpo = new Ext.data.JsonReader({
	     		root: 'productosAseguradoraCliente',
	     		totalProperty: 'totalCount',
	     		id: 'codigo'
	     		},
	     		[
    			{name: 'codigo', type: 'string',mapping:'codigo'},
    			{name: 'descripcion', type: 'string',mapping:'descripcion'}
	 			]
	 		);

	 	var _reader_planes = new Ext.data.JsonReader({
	     		root: 'comboPlanProducto',
	     		totalProperty: 'totalCount',
	     		id: 'codigo'
	     		},
	     		[
    			{name: 'codigo', type: 'string',mapping:'codigo'},
    			{name: 'descripcion', type: 'string',mapping:'descripcion'}
	 			]
	 		);

	 	var _reader_nroPolizas = new Ext.data.JsonReader({
	     		root: 'comboNrosPolizas',
	     		totalProperty: 'totalCount',
	     		id: 'codigo'
	     		},
	     		[{name: 'id', type: 'string',mapping:'id'}]
	 		);
	 		
	 	var _reader_PolizasMaestras = new Ext.data.JsonReader({
	    	root: 'comboPolizasMaestras',
	     	totalProperty: 'totalCount',
	     	id: 'codigo'
	     	},
	     	[{name: 'id', type: 'string',mapping:'id'}]
	 	);

	 	var _reader_valores = new Ext.data.JsonReader({
	     		root: 'comboIndNumEndosos',
	     		totalProperty: 'totalCount',
	     		id: 'codigo'
	     		},
	     		[
    			{name: 'codigo', type: 'string',mapping:'codigo'},
    			{name: 'descripcion', type: 'string',mapping:'descripcion'}
	 			]
	 		);
	 	
	 	//DEFINICION DE STORES DE LOS COMBOS *****************************	
    	var dsClientesCorpo = new Ext.data.Store({proxy: _proxy_clientesCorpo, reader: _reader_clientesCorpo});		 	
    	var dsAseguradoras = new Ext.data.Store({proxy: _proxy_aseguradoras, reader: _reader_aseguradoras});
    	var dsProductos = new Ext.data.Store({proxy: _proxy_prod_aseg_corpo, reader: _reader_prod_aseg_corpo});
    	var dsPlanes = new Ext.data.Store({proxy: _proxy_planes, reader: _reader_planes});
    	var dsNroPolizas = new Ext.data.Store({proxy: _proxy_nroPolizas, reader: _reader_nroPolizas});
    	var dsPolizasMaestras = new Ext.data.Store({proxy: _proxy_PolizasMaestras, reader: _reader_PolizasMaestras});
    	var dsValores = new Ext.data.Store({proxy: _proxy_valores, reader: _reader_valores});

		//DEFINICION DE LOS COMBOS ********************************************************
		var comboCuentasClientes = new Ext.form.ComboBox({
				                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
				                    id:'comboCuentasClientesId',
				                    store: dsClientesCorpo,
				                    displayField:'dsElemen',
				                    valueField:'cdElemento',
				                    hiddenName: 'cdElemento',
				                    typeAhead: true,
				                    mode: 'local',
				                    triggerAction: 'all',
						            fieldLabel: getLabelFromMap('comboCuentasClientesId',helpMap,'Cuenta/Cliente'),
					                tooltip: getToolTipFromMap('comboCuentasClientesId',helpMap,'Elija Cuenta/Cliente'),			          		
				                    hasHelpIcon:getHelpIconFromMap('comboCuentasClientesId',helpMap),
				 					Ayuda:getHelpTextFromMap('comboCuentasClientesId',helpMap),
				                    forceSelection: true,
				                    width: 200,
				                    selectOnFocus:true,
				                    allowBlank : false,
				                    emptyText:'Seleccione Cliente/Cuenta',
				                    //labelSeparator:'',
						            onSelect: function (record){
						  						this.setValue(record.get("cdElemento"));
												Ext.getCmp("cdPersonId").setValue('cdPerson');
						          				
						          				comboAseguradoras.setRawValue('');
						          				comboAseguradoras.clearValue();
						          				dsAseguradoras.removeAll();
						          				dsAseguradoras.load({
						          						params:{cdElemento: record.get("cdElemento")}	
						          					});
						          				
						          				comboProductos.setRawValue('');
						          				comboProductos.clearValue();
						          				dsProductos.removeAll();
						          				dsProductos.load({
						          						params: {cdUniEco: formWindowEdit.findById('comboAseguradorasId').getValue(),
						          								 cdElemento: record.get("cdPerson")}
						          					});
												this.collapse();	
						          		        }       
		    					});	
		    					
		var fieldCuentaCliente = new Ext.form.TextField({
									id:'fieldCuentaClienteId',
									name: 'dsElemen',
								    fieldLabel: getLabelFromMap('fieldCuentaClienteId',helpMap,'Cuenta/Cliente'),
								    tooltip:getToolTipFromMap('fieldCuentaClienteId',helpMap,'Cuenta/Cliente para numeraci&oacute;n de endosos'), 
				                    hasHelpIcon:getHelpIconFromMap('fieldCuentaClienteId',helpMap),
				 					Ayuda:getHelpTextFromMap('fieldCuentaClienteId',helpMap),
									readOnly: true,
									disabled:true,
									width: 200
								});
								
		var hiddenCuentaCliente = new Ext.form.Hidden({
									id: 'hiddenCuentaClienteId',
									name: 'cdElemento'
								});
				            		  		              		
		 var comboAseguradoras = new Ext.form.ComboBox({
				                    tpl: '<tpl for="."><div ext:qtip="{cdUniEco}.{dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
				                    id:'comboAseguradorasId',
				                    store: dsAseguradoras,
				                    displayField:'dsUniEco',
				                    valueField:'cdUniEco',
				                    hiddenName: 'cdUniEco',
				                    typeAhead: true,
				                    mode: 'local',
						            fieldLabel: getLabelFromMap('comboAseguradorasId',helpMap,'Aseguradora'),
					                tooltip: getToolTipFromMap('comboAseguradorasId',helpMap,'Elija Aseguradora'),			          		
				                    hasHelpIcon:getHelpIconFromMap('comboAseguradorasId',helpMap),
				 					Ayuda:getHelpTextFromMap('comboAseguradorasId',helpMap),
				                    triggerAction: 'all',
				                    forceSelection: true,
				                    width: 200,
				                    selectOnFocus:true,
				                    allowBlank : false,
				                    emptyText:'Seleccione Aseguradora',
				                    //labelSeparator:'',
						            onSelect: function (record){
						  						this.setValue(record.get("cdUniEco"));
						  						comboProductos.setRawValue('');
						  						comboProductos.clearValue();
						          				dsProductos.removeAll();
						          				dsProductos.load({
						          						params: {cdElemento: formWindowEdit.findById('comboCuentasClientesId').getValue(),
						          								 cdUniEco: record.get("cdUniEco")}
						          					});
						          					
						          				comboPolizasMaestras.setRawValue('');
						          				comboPolizasMaestras.clearValue();
						          				dsPolizasMaestras.removeAll();
						          				/*dsPolizasMaestras.load({
						          						params: {cdUniEco: record.get("cdUniEco"),
						          								 cdRamo: formWindowEdit.findById("comboProductosId").getValue(),
						          								 //cdPlan: formWindowEdit.findById("comboPlanesId").getValue(),
						          								 cdElemento: formWindowEdit.findById("comboCuentasClientesId").getValue()
						          								 }
						          					});*/
												this.collapse();	
						          		        }
		    					});

		var fieldAseguradora = new Ext.form.TextField({
									id:'fieldAseguradoraId',
									name: 'dsUniEco',
								    fieldLabel: getLabelFromMap('fieldAseguradoraId',helpMap,'Aseguradora'),
								    tooltip:getToolTipFromMap('fieldAseguradoraId',helpMap,'Aseguradora para numeraci&oacute;n de endosos'), 
				                    hasHelpIcon:getHelpIconFromMap('fieldAseguradoraId',helpMap),
				 					Ayuda:getHelpTextFromMap('fieldAseguradoraId',helpMap),
									readOnly: true,
									disabled:true,
									width: 200
								});

		var hiddenAseguradora = new Ext.form.Hidden({
									id: 'hiddenAseguradoraId',
									name: 'cdUniEco'
								});
		
		var comboProductos = new Ext.form.ComboBox({
				                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
				                    id:'comboProductosId',
				                    store: dsProductos,
				                    displayField:'descripcion',
				                    valueField:'codigo',
				                    hiddenName: 'cdRamo',
				                    typeAhead: true,
				                    mode: 'local',
				                    triggerAction: 'all',
						            fieldLabel: getLabelFromMap('comboProductosId',helpMap,'Producto'),
					                tooltip: getToolTipFromMap('comboProductosId',helpMap,'Elija Producto'),			          		
				                    hasHelpIcon:getHelpIconFromMap('comboProductosId',helpMap),
				 					Ayuda:getHelpTextFromMap('comboProductosId',helpMap),
				                    forceSelection: true,
				                    width: 200,
				                    selectOnFocus:true,
				                    allowBlank : false,
				                    emptyText:'Seleccione Producto',
				                    //labelSeparator:'',
						            onSelect: function (record){
						  						this.setValue(record.get("codigo"));
						          				comboPlanes.setRawValue('');
						          				comboPlanes.clearValue();
						          				dsPlanes.removeAll();
						          				dsPlanes.load({
						          						params: {cdRamo: record.get("codigo")}
						          					});
						          				
						          				comboPolizasMaestras.setRawValue('');
						          				comboPolizasMaestras.clearValue();
						          				dsPolizasMaestras.removeAll();
						          				dsPolizasMaestras.load({
						          						params: {cdUniEco: formWindowEdit.findById("comboAseguradorasId").getValue(),
						          								 cdRamo: record.get("codigo"),
						          								 //cdPlan: formWindowEdit.findById("comboPlanesId").getValue(),
						          								 cdElemento: formWindowEdit.findById("comboCuentasClientesId").getValue()
						          								 }
						          					});
												this.collapse();	
						          		        }
		    					});
		
		var fieldProducto = new Ext.form.TextField({
									id:'fieldProductoId',
									name: 'dsRamo',
								    fieldLabel: getLabelFromMap('fieldProductoId',helpMap,'Producto'),
								    tooltip:getToolTipFromMap('fieldProductoId',helpMap,'Producto para numeraci&oacute;n de endosos'), 
				                    hasHelpIcon:getHelpIconFromMap('fieldProductoId',helpMap),
				 					Ayuda:getHelpTextFromMap('fieldProductoId',helpMap),
									readOnly: true,
									disabled:true,
									width: 200
								});

		var hiddenProducto = new Ext.form.Hidden({
									id: 'hiddenProductoId',
									name: 'cdRamo'
								});

		var comboPlanes = new Ext.form.ComboBox({
				                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
				                    id:'comboPlanesId',
				                    store: dsPlanes,
				                    displayField:'descripcion',
				                    valueField:'codigo',
				                    hiddenName: 'cdPlan',
				                    typeAhead: true,
				                    mode: 'local',
				                    triggerAction: 'all',
						            fieldLabel: getLabelFromMap('comboPlanesId',helpMap,'Plan'),
					                tooltip: getToolTipFromMap('comboPlanesId',helpMap,'Elija Plan'),			          		
				                    hasHelpIcon:getHelpIconFromMap('comboPlanesId',helpMap),
				 					Ayuda:getHelpTextFromMap('comboPlanesId',helpMap),
				                    forceSelection: true,
				                    width: 200,
				                    selectOnFocus:true,
				                    allowBlank : false,
				                    emptyText:'Seleccione Codigo',
				                    //labelSeparator:'',
						            onSelect: function (record){
						  						this.setValue(record.get("codigo"));
						          				/*comboPolizasMaestras.setRawValue('');
						          				 * comboPolizasMaestras.clearValue();
						          				dsPolizasMaestras.removeAll();
						          				dsPolizasMaestras.load({
						          						params: {cdUniEco: (_record)?Ext.getCmp('hiddenAseguradoraId').getValue():formWindowEdit.findById("comboAseguradorasId").getValue(),
						          								 cdRamo: (_record)?Ext.getCmp('hiddenProductoId').getValue():formWindowEdit.findById("comboProductosId").getValue(),						          								 
						          								 cdPlan: record.get("codigo"),
						          								 cdElemento: (_record)?Ext.getCmp('hiddenCuentaClienteId').getValue():formWindowEdit.findById("comboCuentasClientesId").getValue()
						          								 }
						          					});
						          				*/
												this.collapse();	
						          		        }
		    					});
		    					
		var fieldPlanes = new Ext.form.TextField({
									id:'fieldPlanesId',
									name: 'dsPlan',
								    fieldLabel: getLabelFromMap('fieldPlanesId',helpMap,'Plan'),
								    tooltip:getToolTipFromMap('fieldPlanesId',helpMap,'Elija Plan'), 
				                    hasHelpIcon:getHelpIconFromMap('fieldPlanesId',helpMap),
				 					Ayuda:getHelpTextFromMap('fieldPlanesId',helpMap),
									readOnly: true,
									disabled:true,
									width: 200
								});
		var hiddenPlanes = new Ext.form.Hidden({
									id: 'hiddenPlanesId',
									name: 'cdPlan'
								});
		
		var comboPolizasMaestras = new Ext.form.ComboBox({
				                    tpl: '<tpl for="."><div ext:qtip="{id}.{id}" class="x-combo-list-item">{id}</div></tpl>',
				                    id:'comboPolizasMaestrasId',
				                    store: dsPolizasMaestras,
				                    displayField:'id',
				                    valueField:'id',
				                    hiddenName: 'nmPoliEx',
				                    typeAhead: true,
				                    mode: 'local',
				                    triggerAction: 'all',
						            fieldLabel: getLabelFromMap('comboPolizasMaestrasId',helpMap,'P&oacute;liza Maestra'),
					                tooltip: getToolTipFromMap('comboPolizasMaestrasId',helpMap,'Elija P&oacute;liza Maestra'),			          		
				                    hasHelpIcon:getHelpIconFromMap('comboPolizasMaestrasId',helpMap),
				 					Ayuda:getHelpTextFromMap('comboPolizasMaestrasId',helpMap),
				                    forceSelection: true,
				                    width: 200,
				                    selectOnFocus:true,
				                    emptyText:'Seleccione Poliza Maestra'
		});
		
		var fieldPolizasMaestras = new Ext.form.TextField({
									id:'fieldPolizasMaestrasId',
									name: 'nmPoliEx',
								    fieldLabel: getLabelFromMap('fieldPolizasMaestrasId',helpMap,'P&oacute;liza Maestra'),
								    tooltip:getToolTipFromMap('fieldPolizasMaestrasId',helpMap,'Elija P&oacute;liza Maestra'), 
				                    hasHelpIcon:getHelpIconFromMap('fieldPolizasMaestrasId',helpMap),
				 					Ayuda:getHelpTextFromMap('fieldPolizasMaestrasId',helpMap),
									readOnly: true,
									disabled:true,
									width: 200
								});
								
		var hiddenPolizasMaestras = new Ext.form.Hidden({
									id: 'hiddenPolizasMaestrasId',
									name: 'nmPoliEx'
								});

		var comboIndicadorNumeracion = new Ext.form.ComboBox({
				                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
				                    id:'comboIndicadorNumeracionId',
				                    store: dsValores,
				                    displayField:'descripcion',
				                    valueField:'codigo',
				                    hiddenName: 'indCalc',
				                    typeAhead: true,
				                    mode: 'local',
				                    triggerAction: 'all',
						            fieldLabel: getLabelFromMap('comboIndicadorNumeracionId',helpMap,'Indicador Numeraci&oacute;n'),
					                tooltip: getToolTipFromMap('comboIndicadorNumeracionId',helpMap,'Elija Indicador Numeraci&oacute;n'),			          		
				                    hasHelpIcon:getHelpIconFromMap('comboIndicadorNumeracionId',helpMap),
				 					Ayuda:getHelpTextFromMap('comboIndicadorNumeracionId',helpMap),
				                    forceSelection: true,
				                    width: 200,
				                    selectOnFocus:true,
				                    allowBlank : false,
				                    emptyText:'Seleccione Indicador Numeracion',
				                    //labelSeparator:'',
						            onSelect: function (record){
												this.collapse();
						  						this.setValue(record.get("codigo"));
						  						//alert(record.get("codigo"));
						  						if(record.get("codigo")==1){
						  							rangoDesde.setDisabled(false);						  							
						  							
						  							rangoHasta.setDisabled(false);
						  							
						  							algoritmo.setDisabled(true);
						  							algoritmo.setValue('');
						  							Ext.getCmp('btnExpresion').setDisabled(true);
						  						}else if (record.get("codigo")==2){					  									
						  									rangoDesde.setDisabled(true);
						  									rangoDesde.setRawValue('');
						  									
						  									rangoHasta.setDisabled(true);
						  									rangoHasta.setRawValue('');
						  									
						  									nroActual.setRawValue('');
						  														  									
						  									algoritmo.setDisabled(false);
						  									Ext.getCmp('btnExpresion').setDisabled(false);
						  								}	
						          		        }
		    					});

		var rangoDesde = new Ext.form.NumberField( {
			        	id:'rangoDesdeId',
					    fieldLabel: getLabelFromMap('rangoDesdeId',helpMap,'Rango Desde'),
					    tooltip:getToolTipFromMap('rangoDesdeId',helpMap,'Rango Desde... para numeraci&oacute;n de endosos'), 
	                    hasHelpIcon:getHelpIconFromMap('rangoDesdeId',helpMap),
	 					Ayuda:getHelpTextFromMap('rangoDesdeId',helpMap),
		                name : 'nmInicial',
		                width: 85,
		                allowBlank:false,
		                disabled: true,
		                listeners: {
						    'change': function()
						    		{
								    	 validarRangos();
								         actualizarNroActual();
						    		}
						  }
		            });
		            
		var rangoHasta = new Ext.form.NumberField( {
			        	id:'rangoHastaId',
					    fieldLabel: getLabelFromMap('rangoHastaId',helpMap,'Hasta'),
					    tooltip:getToolTipFromMap('rangoHastaId',helpMap,'Hasta... para numeraci&oacute;n de endosos'), 
	                    hasHelpIcon:getHelpIconFromMap('rangoHastaId',helpMap),
	 					Ayuda:getHelpTextFromMap('rangoHastaId',helpMap),
		                name : 'nmFinal',
		                width: 85,
		                allowBlank:false,
		                disabled: true,
		                listeners: {'change': function(){validarRangos();}}
		            });
		            
		var nroActual = new Ext.form.NumberField( {
			        	id:'nroActualId',
					    fieldLabel: getLabelFromMap('nroActualId',helpMap,'N&uacute;mero Actual'),
					    tooltip:getToolTipFromMap('nroActualId',helpMap,'N&uacute;mero Actual para numeraci&oacute;n de endosos'), 
	                    hasHelpIcon:getHelpIconFromMap('nroActualId',helpMap),
	 					Ayuda:getHelpTextFromMap('nroActualId',helpMap),
		                name : 'nmActual',
		                readOnly: true,
		                width: 85
		            });
		            
		var algoritmo = new Ext.form.TextField( {
			        	id:'algoritmoId',
					    fieldLabel: getLabelFromMap('algoritmoId',helpMap,'Expresi&oacute;n'),
					    tooltip:getToolTipFromMap('algoritmoId',helpMap,'Expresi&oacute;n para numeraci&oacute;n de endosos'), 
	                    hasHelpIcon:getHelpIconFromMap('algoritmoId',helpMap),
	 					Ayuda:getHelpTextFromMap('algoritmoId',helpMap),
		                name : 'otExpres',
		                //heigth: 150,
		                allowBlank:false,
		                blankText: 'Debe elegir una Expresi&oacute;n',
		                disabled: true,
		                width: 100,
		                readOnly: true
		            });
		            
		//VARIABLE OCULTA PARA ALMACENAR CDPERSON DE CLIENTES_CORPO	
	 	var cdPerson = new Ext.form.Hidden ({
			        	id:'cdPersonId',
		                name:'cdPerson'
		            });            		            		            
		
		//CONFIGURACION PARA EL GET DEL REGISTRO SELECCIONADO ****************************
		var _proxy = new Ext.data.HttpProxy({url: _ACTION_GET_NUMERACION_ENDOSOS});			
		var _reader = new Ext.data.JsonReader({
	         	root:'regNumeracionEndosos',
	          	successProperty : '@success'
	       		},
	       		[
			        {name: 'cdElemento',   type: 'string',  mapping:'cdElemento'},
			        {name: 'dsElemen',   type: 'string',  mapping:'dsElemen'},
					{name: 'cdUniEco',  type: 'string',  mapping:'cdUniEco'},
			        {name: 'dsUniEco',  type: 'string',  mapping:'dsUniEco'},
			        {name: 'cdRamo', type: 'string',  mapping:'cdRamo'},
			        {name: 'dsRamo', type: 'string',  mapping:'dsRamo'},
			        {name: 'cdPlan', type: 'string',  mapping:'cdPlan'},
			        {name: 'dsPlan', type: 'string',  mapping:'dsPlan'},
			        {name: 'nmPoliEx', type: 'string',  mapping:'nmPoliEx'},
			        {name: 'indCalc', type: 'string',  mapping:'indCalc'},
			        {name: 'nmInicial', type: 'string',  mapping:'nmInicial'},
			        {name: 'nmFinal', type: 'string',  mapping:'nmFinal'},
			        {name: 'nmActual', type: 'string',  mapping:'nmActual'},
			        {name: 'otExpres', type: 'string',  mapping:'otExpres'}			        
				]
			);
		
		var _store  = new Ext.data.Store({proxy: _proxy,reader: _reader});
		
		//DEFINICION DEL LAYOUT PARA MOSTRAR EN EL FORMULARIO
		
		rangod=new Ext.Panel({
			  layout:'form',
			  borderStyle:false,
			  items:[rangoDesde]
		});
		
		rangoh=new Ext.Panel({
			  layout:'form',
			  borderStyle:false,
			  items:[rangoHasta]
		});
		
		nroa=new Ext.Panel({
			  layout:'form',
			  borderStyle:false,
			  items:[nroActual]
		});
		
		algor=new Ext.Panel({
			layout:'column',
			borderStyle:false,
			//column items:
			items:[
				{
					columnWidth:.4,
                	layout: 'form',
                	border: false,
					items:[algoritmo]
			  	},{
			  		columnWidth:.6,
                	layout: 'form',
                	border: false,
                	items:[//Componentes agregados para las Expresiones
                		{
			  				xtype:'button',
			  				text:getLabelFromMap('btnExpresion',helpMap,'Expresi&oacute;n'),
			   				tooltip: getToolTipFromMap('btnExpresion',helpMap,'Expresi&oacute;n'),
                			//text: 'Expresi&oacute;n',
                			id: 'btnExpresion',
                			name: 'btnExpresion',
                			buttonAlign: "left",
                			handler: function() {
                				//Si el campo Formula ya tiene valor, mostraremos la expresión con ése código, para que sea editada:
                				if(algoritmo.getValue() != ""){
                					ExpresionesVentana2(algoritmo.getValue(), Ext.getCmp('hidden-codigo-expresion-session').getValue());
                				}else{
                				
                					var connect = new Ext.data.Connection();
									connect.request ({
										url:'atributosVariables/ObtenerCodigoExpresion.action',
										callback: function (options, success, response) {
											try{
												//alert(Ext.util.JSON.decode(response.responseText).codigoExpresion);
												Ext.getCmp('hidden-codigo-expresion-atributos-variables').setValue(Ext.util.JSON.decode(response.responseText).codigoExpresion);
												ExpresionesVentana2(Ext.getCmp('hidden-codigo-expresion-atributos-variables').getValue(),Ext.getCmp('hidden-codigo-expresion-session').getValue());
												algoritmo.setValue( Ext.getCmp('hidden-codigo-expresion-atributos-variables').getValue() );
											}catch(e){
											}
										}
									});
                				}
							}
                		},{
							xtype:'hidden',
							id:'hidden-codigo-expresion-session',
							name:'codigoExpresionSession', 
							value:'EXPRESION_ATRIBUTOS_VARIABLES'
						},{
                			xtype:'hidden',                	
                			id:'hidden-codigo-expresion-atributos-variables',
		            		name: "codigoExpresion"
                		}
                	]
				}
			]//end column items
		});
		
		var tabla = new Ext.Panel({
			/*layout:'column',
			border: false,			
			layoutConfig:
			{
				columns:3,
				defaults:{labelWidth:60}
			},*/
			layout: 'table',
			layoutConfig:{columns:3},
			//defaults: {width:194},
			baseCls:'',
			width: 580,
			items:[
				{
				//width: 160,
				layout:'form',
				items:[rangod]
				},
				{
				layout:'form',
				//width: 168,
				items:[rangoh]
				},
				{
				//width: 180,
				layout:'form',
				items:[nroa]
				}
				]
		});
		
		var hidden = new Ext.form.Hidden();
		//CONFIGURACION PARA EL FORMULARIO *****************************
		var formWindowEdit = new Ext.FormPanel({
				id:'formWindowEditId',
		        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formWindowEditId',helpMap,'Configurar Numeraci&oacute;n de Endosos')+'</span>',
		        iconCls:'logo',
		        bodyStyle:'background: white',
		        buttonAlign: "center",
		        labelAlign: 'right',
		        frame:true,
		        url: _ACTION_GET_NUMERACION_ENDOSOS,
		        reader:_reader,
		        width: 600,
		        height:400,
		        items: [		        		
		        		(_record)?fieldCuentaCliente:comboCuentasClientes,
		        		cdPerson,
   		   				(_record)?fieldAseguradora:comboAseguradoras,
   		   				(_record)?fieldProducto:comboProductos,
   		   				(_record)?fieldPlanes:comboPlanes,
   		   				(_record)?fieldPolizasMaestras:comboPolizasMaestras,
   		   				comboIndicadorNumeracion,
   		   				tabla,
   		   				algor,
   		   				hiddenCuentaCliente,
   		   				hiddenAseguradora,
   		   				hiddenProducto,
   		   				hiddenPlanes,
   		   				hiddenPolizasMaestras
   		   				]
			});	

		 var window = new Ext.Window({
	       	width: 600,
	       	height:400,
	       	layout: 'fit',	 
	       	modal: true,      	
	       	bodyStyle:'padding:5px;',
	       	plain:true,
	       	buttonAlign:'center',
	       	items: [formWindowEdit],
	        buttons: [{
			   text:getLabelFromMap('confNumEndBtnSave',helpMap,'Guardar'),
			   tooltip: getToolTipFromMap('confNumEndBtnSave',helpMap,'Guarda numeraci&oacute;n de endosos'),
               handler : function(){
                   if(formWindowEdit.form.isValid()){guardar();}
                   else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));}
               }
	        },
	           {
			   text:getLabelFromMap('confNumEndBtnBack',helpMap,'Regresar'),
			   tooltip: getToolTipFromMap('confNumEndBtnBack',helpMap,'Regresa a la pantalla anterior'),
	           handler : function(){window.close();}
	           }]
	   	});
	   	window.show();
	   	
	   	function actualizarNroActual(){
	   		nroActual.setValue('');	
	   		nroActual.setValue(rangoDesde.getValue());
		}
   
		function validarRangos(){  		 
			if(rangoDesde.getValue()!= "" && rangoHasta.getValue()!="")
			{
				if(eval(rangoDesde.getValue()) >= eval(rangoHasta.getValue()))
				{
			       Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400109', helpMap,'El rango inicial debe ser menor que el final') );
			       var actual = eval(rangoDesde.getValue()) + 1;
			       nroActual.setValue(actual);
			       rangoHasta.setValue(actual);
				}
			}
 		}
	   	
	   	//LOAD DEL FORMULARIO Y DE LOS COMBOS *************************************
		if(_record)
		{
			formWindowEdit.form.load(
			{
				waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
				waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),

				params:{cdElemento: _record.get('cdElemento'),
						cdUniEco: _record.get('cdUniEco'),
						cdRamo: _record.get('cdRamo')
						},
				success:function(){
                        var mStore = dsPlanes;
				        mStore.baseParams = mStore.baseParams || {},
						mStore.baseParams['cdRamo'] = _record.get('cdRamo'),
						mStore.reload({
							callback: function(r,options,success)
								{
								if(success)
									{
									if (formWindowEdit.findById('comboPlanesId')) {
										formWindowEdit.findById('comboPlanesId').setValue(formWindowEdit.findById('comboPlanesId').getValue());
									}
									
									if (formWindowEdit.findById('comboPolizasMaestrasId')) {
										formWindowEdit.findById('comboPolizasMaestrasId').setValue(formWindowEdit.findById('comboPolizasMaestrasId').getValue());
									}
	                         		
	                         		comboCuentasClientes.setValue(_store.reader.jsonData.regNumeracionEndosos[0].cdElemento);
	                         		comboCuentasClientes.setRawValue(_store.reader.jsonData.regNumeracionEndosos[0].dsElemen);
	                         		
	                         		comboAseguradoras.setValue(_store.reader.jsonData.regNumeracionEndosos[0].cdUniEco);
	                         		comboAseguradoras.setRawValue(_store.reader.jsonData.regNumeracionEndosos[0].dsUniEco);
	                         		
	                         		comboProductos.setValue(_store.reader.jsonData.regNumeracionEndosos[0].cdRamo);
	                         		comboProductos.setRawValue(_store.reader.jsonData.regNumeracionEndosos[0].dsRamo);	
									}
								}	
								
							});
						 if(_store.reader.jsonData.regNumeracionEndosos[0].indCalc == 1)
                       		{
                       			//alert(_store.reader.jsonData.regNumeracionEndosos[0].indCalc);
                       			algoritmo.setDisabled(true);
                       			Ext.getCmp('btnExpresion').setDisabled(true);
                       			Ext.getCmp('rangoDesdeId').setDisabled(false);
                       			//rangoDesde.setDisabled(false);
                       			rangoHasta.setDisabled(false);
                       			//rangoDesde.setDisabled(false);
                       		}
                       		else
                       		{
                       			//alert(_store.reader.jsonData.regNumeracionEndosos[0].indCalc);
                       			algoritmo.setDisabled(false);
                       			Ext.getCmp('btnExpresion').setDisabled(false);
                       			
                       			rangoDesde.setDisabled(true);
                       			rangoHasta.setDisabled(true);	                         			
                       			rangoDesde.setRawValue('');
                       			rangoHasta.setRawValue('');
                       		}
						}
		})
		}
		else
		{
			dsClientesCorpo.load();	   		
		}
		dsValores.load();

	   	function guardar()
	   	{
	   		var _params =  {cdElemento: (_record)?Ext.getCmp('hiddenCuentaClienteId').getValue():Ext.getCmp('comboCuentasClientesId').getValue(),
							cdUniEco: (_record)?Ext.getCmp('hiddenAseguradoraId').getValue():Ext.getCmp('comboAseguradorasId').getValue(),
							cdRamo: (_record)?Ext.getCmp('hiddenProductoId').getValue():Ext.getCmp('comboProductosId').getValue(),
							cdPlan: (_record)?Ext.getCmp('hiddenPlanesId').getValue():Ext.getCmp('comboPlanesId').getValue(),
							//nmPoliEx: Ext.getCmp('comboPolizasId').getValue(),
							nmPoliEx: (_record)?Ext.getCmp('hiddenPolizasMaestrasId').getValue():Ext.getCmp('comboPolizasMaestrasId').getValue(),
							indCalc:Ext.getCmp('comboIndicadorNumeracionId').getValue(),
							nmInicial:Ext.getCmp('rangoDesdeId').getValue(),
							nmFinal:Ext.getCmp('rangoHastaId').getValue(),
							nmActual:Ext.getCmp('nroActualId').getValue(),
							otExpres:Ext.getCmp('algoritmoId').getValue(),
							accion:accion
							};
							
							
							
				
			execConnection(_ACTION_GUARDAR_NUMERACION_ENDOSO, _params, cbkSaveConfig);
		}
	
		function cbkSaveConfig(_success, _message)
		{
			if(_success){Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid(),window.close()});}
			else{Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);}
		}

};

function borrar(record) {
               Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'), function(btn) {
                   if (btn == "yes") {
                   		var conn = new Ext.data.Connection();
               			conn.request({
						    url: _ACTION_BORRAR_NUMERACION_ENDOSO,
						    method: 'POST',
						    params: {"cdElemento": record.get('cdElemento'),
						    		 "cdUniEco": record.get('cdUniEco'),
						    		 "cdRamo": record.get('cdRamo')
						    		 },			    		 			 
						    callback: function(options, success, response) {
								if (Ext.util.JSON.decode(response.responseText).success != false) {
									//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), "Borrado exitoso");
									Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400081', helpMap,'Los datos se eliminaron con éxito'));
									reloadGrid(); 	
								}else {
									//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), "No se pudo borrar la informaci&oacute;n "+ Ext.util.JSON.decode(response.responseText).errorMessages[0]);
									Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
								}
							}
						});						
             		}
              });
 };