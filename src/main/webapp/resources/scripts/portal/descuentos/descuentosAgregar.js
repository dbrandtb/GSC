function cargarDescuentosAgregar(){
		var itemsPerPage = 10;
		
		
		//*************** DEFINICION DE COLUMN MODEL DE GRILLA DE DETALLES DE VOLUMEN ********************
		var cm2 = new Ext.grid.ColumnModel([
	          {
	          	header: getLabelFromMap('cm2MnVolIniPrimaInicialDescuentoAgr',helpMap,'Prima Inicial'),
        		tooltip: getToolTipFromMap('cm2MnVolIniPrimaInicialDescuentoAgr', helpMap,'Prima Inicial'),	
        		tooltip: getToolTipFromMap('cm2MnVolIniPrimaInicialDescuentoAgr', helpMap,'Descripci&oacute;n'),
		        hasHelpIcon:getHelpIconFromMap('cm2MnVolIniPrimaInicialDescuentoAgr',helpMap,''),        		   	
		         //header: "Prima Inicial",
		         dataIndex: 'mnVolIni',
		         width: 130,
		         sortable: true
			      },
		      {
	          	header: getLabelFromMap('cm2MnVolFinPrimaFinalDescuentoAgr',helpMap,'Prima Final'),
        		tooltip: getToolTipFromMap('cm2MnVolFinPrimaFinalDescuentoAgr', helpMap,'Prima Final'),	   	
        		tooltip: getToolTipFromMap('cm2MnVolFinPrimaFinalDescuentoAgr', helpMap,'Descripci&oacute;n'),
		        hasHelpIcon:getHelpIconFromMap('cm2MnVolFinPrimaFinalDescuentoAgr',helpMap,''),
        		
	          //header: "Prima Final",
	          dataIndex: 'mnVolFin', 
	          width: 130,
	          sortable: true       
	          },
		      {
	          	header: getLabelFromMap('cm2PrDesctoPorcentajeDescuentoAgr',helpMap,'Porcentaje'),
        		tooltip: getToolTipFromMap('cm2PrDesctoPorcentajeDescuentoAgr', helpMap,'Porcentaje'),
        		tooltip: getToolTipFromMap('cm2PrDesctoPorcentajeDescuentoAgr', helpMap,'Descripci&oacute;n'),
		       hasHelpIcon:getHelpIconFromMap('cm2PrDesctoPorcentajeDescuentoAgr',helpMap,''),
        			   	
	          //header: "Porcentaje",
	          dataIndex: 'prDescto',      
	          width: 120,
	          sortable: true       
	          },
		      {
	          	header: getLabelFromMap('cm2MnDesctoMontoDescuentoAgr',helpMap,'Monto'),
        		tooltip: getToolTipFromMap('cm2MnDesctoMontoDescuentoAgr', helpMap,'Monto'),	
        		tooltip: getToolTipFromMap('cm2MnDesctoMontoDescuentoAgr', helpMap,'Descripci&oacute;n'),
		        hasHelpIcon:getHelpIconFromMap('cm2MnDesctoMontoDescuentoAgr',helpMap,''),
        		   	
	          //header: "Monto",
	          dataIndex: 'mnDescto',	             
	          width: 100,
	          sortable: true          
	          }
	         ]);
	
	//*************** STORE Y READER DE DETALLES DE VOLUMEN ********************
	var _reader = new Ext.data.JsonReader({
		            		root:'detalleVolumen',
			            	successProperty : '@success',
			            	totalProperty: 'totalCount'
		     			},
		     		[
		     		{name: 'cdDscto',   mapping:'cdDscto',   type: 'string'},
		        	{name: 'cdDsctod',  mapping:'cdDsctod',  type: 'string'},
			        {name: 'mnVolIni',  mapping:'mnVolIni',  type: 'string'},
			        {name: 'mnVolFin',  mapping:'mnVolFin',  type: 'string'},
			        {name: 'prDescto',  mapping:'prDescto',  type: 'string'},
			        {name: 'mnDescto',  mapping:'mnDescto',  type: 'string'}
			        ]
		     	);
		 
		 var storeGrid2 = new Ext.data.Store({
			    		proxy: new Ext.data.HttpProxy({
							url: _ACTION_OBTIENE_DETALLE_VOLUMEN
		                }),
		                reader: _reader,
		                id:'stGrid2'
		        });
			storeGrid2.load({params: {cdDscto: CODIGO_DESCUENTO}});
	
	//*************** DEFINICION DE GRILLA DE DETALLES DE VOLUMEN ********************
	var	grid2= new Ext.grid.GridPanel({
	        el: 'gridDescuentosDetalles',
	        store: storeGrid2,
	        id:'grid2',
			border:true,
			title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
	        cm: cm2,
	        frame:true,
	        loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
	        buttonAlign:'center',
			buttons:[
	        		{
      				text:getLabelFromMap('grid2DescuentosAgrButtonAgregar', helpMap,'Agregar'),
           			tooltip:getToolTipFromMap('grid2DescuentosAgrButtonAgregar', helpMap,'Agregar'),			        			
		            	//text: 'Agregar',
		            	//tooltip: 'Agregar detalle de volumen',
		            	handler:function(){
		            				if(CODIGO_DESCUENTO == null || CODIGO_DESCUENTO == ""){
		            					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400090', helpMap,'Debe guardar primero el nuevo descuento'));
		            					//Ext.Msg.alert('Advertencia','Debe guardar primero el descuento por volumen');
		            				}
			                    	else{agregarNuevoDetalle(2);}
								}
	            	},{
      				text:getLabelFromMap('grid2DescuentosAgrButtonGuardar', helpMap,'Guardar'),
           			tooltip:getToolTipFromMap('grid2DescuentosAgrButtonGuardar', helpMap,'Guardar'),			        			
		        		//text:'Guardar',
		            	//tooltip:'Guardar descuento por volumen',
		            	handler:function(){
		            		if (form.form.isValid()) {
		            			guardarVolumen(form);
		            		}else{
                    				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                  			}
		            	}		
		            	
					},
	            	{
      				text:getLabelFromMap('grid2DescuentosAgrButtonBorrar', helpMap,'Eliminar'),
           			tooltip:getToolTipFromMap('grid2DescuentosAgrButtonBorrar', helpMap,'Eliminar'),			        			
		            	//text:'Borrar',
		            	//tooltip:'Borra un detalle de volumen seleccionado',
		            	handler: function () {
		            				if(getSelectedRecord(2)!=null){borrarVolumen(getSelectedRecord(2));}
		            				else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400041', helpMap,'Debe seleccionar un vol&uacute;men'));
		    							//Ext.Msg.alert('Advertencia', 'Debe seleccionar un volumen');
		    							}
		            			}
	            	},					
	            	{
      				text:getLabelFromMap('grid2DescuentosAgrButtonRegresar', helpMap,'Regresar'),
           			tooltip:getToolTipFromMap('grid2DescuentosAgrButtonRegresar', helpMap,'Regresar'),			        			
		            	//text:'Regresar',
		            	//tooltip:'Regresa a la página anterior',
		            	handler:function(){regresar();}
	            	}
	            	],
	    	width:505,
	    	frame:true,
			height:320,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
    		collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: storeGrid2,
					displayInfo: true,
					displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
	                //displayMsg: 'Mostrando registros {0} - {1} of {2}',
	                //emptyMsg: "No hay registros para visualizar"
			    })
			});
	
	//************ STORES Y READERS DE COMBOS DE DESCUENTOS POR PRODUCTO ***********************
	dsAseguradora = new Ext.data.Store({
	     proxy: new Ext.data.HttpProxy({
	         url: _ACTION_COMBO_ASEGURADORAS
	     }),
	     reader: new Ext.data.JsonReader({
	     	root: 'aseguradoraComboBox',
	     	totalProperty: 'totalCount',
	     	id: 'cdUniEco'
	     },
	     [
		    {name: 'cdUniEco', type: 'string',mapping:'cdUniEco'},
		    {name: 'dsUniEco', type: 'string',mapping:'dsUniEco'}
	 	 ]
	 	)
	 });
	 dsAseguradora.load();
	 
	 dsProducto = new Ext.data.Store({
	     proxy: new Ext.data.HttpProxy({
	         url: _ACTION_COMBO_PRODUCTOS
	     }),
	     reader: new Ext.data.JsonReader({
	     	root: 'productosAseguradoraCliente',
	     	totalProperty: 'totalCount',
	     	id: 'codigo'
	     },[
	    {name: 'codigo', type: 'string',mapping:'codigo'},
	    {name: 'descripcion', type: 'string',mapping:'descripcion'}
	 	])
	 });
	 dsProducto.load();
	 
	 dsTipoSituacion = new Ext.data.Store({
	     proxy: new Ext.data.HttpProxy({
	         url: _ACTION_COMBO_TIPO_SITUACION
	     }),
	     reader: new Ext.data.JsonReader({
	     	root: 'comboTipoSituacionProducto',
	     	totalProperty: 'totalCount',
	     	id: 'cdTipSit'
	     },[
	    {name: 'cdTipSit', type: 'string',mapping:'cdTipSit'},
	    {name: 'dsTipSit', type: 'string',mapping:'dsTipSit'}
	 	])
	 });
	 dsTipoSituacion.load();
	
	dataStorePlan = new Ext.data.Store({
	     proxy: new Ext.data.HttpProxy({
	         url: _ACTION_COMBO_PLANES
	     }),
	     reader: new Ext.data.JsonReader({
	     	root: 'planesComboBox',
	     	totalProperty: 'totalCount',
	     	id: 'cdPlan'
	     },[
	    {name: 'cdPlan', type: 'string',mapping:'cdPlan'},
	    {name: 'dsPlan', type: 'string',mapping:'dsPlan'}
	 	])
	 });
	 
	//****************** DEFINICION DE COLUMN MODEL PARA DETALLES DE PRODUCTOS ***********
	var cm1 = new Ext.grid.ColumnModel([
		   {
		   dataIndex:'cdDscto',
		   hidden:true
		   },
		   {
		   dataIndex:'cdDsctod',
		   hidden:true
		   },
          {
	          	header: getLabelFromMap('cm1DsUniEcoAseguradoraDescuentoAgr',helpMap,'Aseguradora'),
        		tooltip: getToolTipFromMap('cm1DsUniEcoAseguradoraDescuentoAgr', helpMap,'Aseguradora'),
        		hasHelpIcon:getHelpIconFromMap('cm1DsUniEcoAseguradoraDescuentoAgr',helpMap),
		        Ayuda: getHelpTextFromMap('cm1DsUniEcoAseguradoraDescuentoAgr',helpMap,''),
        		
        			   	
	          //header: "Aseguradora",
	          dataIndex: 'dsUniEco',
	          width: 130,
	          sortable: true
	      },
	      {
	          	header: getLabelFromMap('cm1DsRamoProductoDescuentoAgr',helpMap,'Producto'),
        		tooltip: getToolTipFromMap('cm1DsRamoProductoDescuentoAgr', helpMap,'Producto'),	
        		hasHelpIcon:getHelpIconFromMap('cm1DsRamoProductoDescuentoAgr',helpMap),
		        Ayuda: getHelpTextFromMap('cm1DsRamoProductoDescuentoAgr',helpMap,''),   	
	          //header: "Producto",
	          dataIndex: 'dsRamo',
	          width: 130,
	          sortable: true
          },
	      {
	          	header: getLabelFromMap('cm1DsTipSitTipoSituacionDescuentoAgr',helpMap,'Riesgo'),
        		tooltip: getToolTipFromMap('cm1DsTipSitTipoSituacionDescuentoAgr', helpMap,'Riesgo'),
        		hasHelpIcon:getHelpIconFromMap('cm1DsTipSitTipoSituacionDescuentoAgr',helpMap),
		        Ayuda: getHelpTextFromMap('cm1DsTipSitTipoSituacionDescuentoAgr',helpMap,''),   		   	
	          //header: "Tipo Situaci&oacute;n",
	          dataIndex: 'dsTipSit',
	          width: 130,
	          sortable: true
          },
	      {
	          	header: getLabelFromMap('cm1DsPlanPlanDescuentoAgr',helpMap,'Plan'),
        		tooltip: getToolTipFromMap('cm1DsPlanPlanDescuentoAgr', helpMap,'Plan'),	   	
        		hasHelpIcon:getHelpIconFromMap('cm1DsPlanPlanDescuentoAgr',helpMap),
		        Ayuda: getHelpTextFromMap('cm1DsPlanPlanDescuentoAgr',helpMap,''),   	
	          //header: "Plan",
	          dataIndex: 'dsPlan',
	          width: 90,
	          sortable: true
          }
    ]);
	
	//****************** DEFINICION DE STORE Y READERS PARA DETALLES DE PRODUCTOS ***********
	
	var reader =  new Ext.data.JsonReader({
	           	root:'detalleProducto',
	           	totalProperty: 'totalCount',
	            successProperty : '@success'
     				},
     			[
           		{name: 'cdDscto', mapping:'cdDscto', type:'string'},
				{name: 'cdDsctod', mapping:'cdDsctod', type:'string'},
				{name: 'cdUniEco', mapping:'cdUniEco', type:'string'},
				{name: 'dsUniEco', mapping:'dsUniEco', type:'string'},
				{name: 'cdRamo', mapping:'cdRamo', type:'string'},
				{name: 'dsRamo', mapping:'dsRamo', type:'string'},
				{name: 'cdTipSit', mapping:'cdTipSit', type:'string'},
				{name: 'dsTipSit', mapping:'dsTipSit', type:'string'},
				{name: 'cdPlan', mapping:'cdPlan', type:'string'},
				{name: 'dsPlan', mapping:'dsPlan', type:'string'}
      			]
      		);
	
	var storeGrid1 = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url: _ACTION_OBTIENE_DETALLE_PRODUCTO
            }),
            reader: reader
      	});
	
	//****************** DEFINICION DE GRILLA PARA DETALLES DE PRODUCTOS ***********
	var	grid1 = new Ext.grid.GridPanel({
	        el: 'gridDescuentosDetalles',
	        store: storeGrid1,
	        id:'grid1',
			border:true,
			title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
	        cm: cm1,
	        frame:true,
	        buttonAlign:'center',
			buttons:[	        		
	            	{
					text:getLabelFromMap('grid1DescuentosAgrButtonAgregar', helpMap,'Agregar'),
					tooltip:getToolTipFromMap('grid1DescuentosAgrButtonAgregar', helpMap,'Agregar'),	
	            	//text: 'Agregar',
	            	//tooltip: 'Agregar detalle de producto',
	            	handler:function(){
	            				if(CODIGO_DESCUENTO == null || CODIGO_DESCUENTO == ""){
		            				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400090', helpMap,'Debe guardar primero el nuevo descuento'));
	            					//Ext.Msg.alert('Advertencia','Debe guardar primero el descuento');
	            				}
		                    	else{agregarNuevoDetalle(1);}
							}
	            	},{
					text:getLabelFromMap('grid1DescuentosAgrButtonGuardar', helpMap,'Guardar'),
					tooltip:getToolTipFromMap('grid1DescuentosAgrButtonGuardar', helpMap,'Guardar'),	
					
	            	//text:'Guardar',
	            	//tooltip:'Guardar descuento por producto',
	            	handler:function(){
	            			if (form.form.isValid()) {
	            			 	if ((form.findById("porcentaje").getValue()!="")||(form.findById("monto").getValue()!="")){
                    				if(form.findById("porcentaje").getValue() > 100 ){
            							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400038', helpMap,'No es válido el Porcentaje indicado. Favor de revisar'));
	            						//Ext.Msg.alert('Advertencia','El porcentaje debe ser de 2 cifras');
	            					}else{
	            						guardarProducto(form);
	            					}
	            			 	}else{
                    				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Ingrese Monto o Porcentaje'));	  
                    		 	}          			 	
                  			} else{
                    			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
                  			}	                  	
                  		}
	            	},
	            	{
					text:getLabelFromMap('grid1DescuentosAgrButtonBorrar', helpMap,'Eliminar'),
					tooltip:getToolTipFromMap('grid1DescuentosAgrButtonBorrar', helpMap,'Eliminar'),	
	            	//text:'Borrar',
	            	//tooltip:'Borra un detalle de producto seleccionado',
	            	handler: function () {
	            				if(getSelectedRecord(1)!= null){borrarProducto(getSelectedRecord(1));}
	            				else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400041', helpMap,'Debe seleccionar un vol&uacute;men'));}
	            			}
	            	},
	            	{
					text:getLabelFromMap('grid1DescuentosAgrButtonRegresar', helpMap,'Regresar'),
					tooltip:getToolTipFromMap('grid1DescuentosAgrButtonRegresar', helpMap,'Regresar'),		            	
	            	//text:'Regresar',
	            	//tooltip:'Regresa a la página anterior',
	            	handler:function(){regresar();}
	            	}],
	    	width:505,
	    	frame:true,
			height:320,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: storeGrid1,
					displayInfo: true,
					displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
	                //displayMsg: 'Mostrando registros {0} - {1} of {2}',
	                //emptyMsg: "No hay registros para visualizar"
			    })
			});
	
	//*************** DEFINICION DE FORMULARIOS Y VENTANAS ******************************************

	function agregarNuevoDetalle(tipo){		
		if(tipo == 1){		
	         dsAseguradora.load({
	         			params: {cdElemento: form.findById('comboClientesId').getValue()},
						waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...')
						//waitMsg: 'Espere por favor....'
						});
			dsProducto.removeAll();
			dsTipoSituacion.removeAll();
			dataStorePlan.removeAll();
	        
	        //*************** DEFINICION DE FORMULARIO PARA DETALLE DE PRODUCTO*********************************
	        
	     var formAgregarDP = new Ext.FormPanel( {
	            labelWidth : 100,
	            url : _ACTION_AGREGAR_PRODUCTO_DETALLE,
	            frame : true,
	            bodyStyle:'background: white',
	            labelAlign:'right',
	            //bodyStyle : 'padding:5px 5px 0',
	            width : 520,
	            height: 250,
	       	    items: [
	       	    		new Ext.form.ComboBox({
							id:'comboAseguradoraId',
							tpl: '<tpl for="."><div ext:qtip="{cdUniEco}.{dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
			          		fieldLabel: getLabelFromMap('descuentosAgregarComboAseguradoraId',helpMap,'Aseguradora'),
                            tooltip: getToolTipFromMap('descuentosAgregarComboAseguradoraId',helpMap,'Combo Aseguradora'),
                            hasHelpIcon:getHelpIconFromMap('descuentosAgregarComboAseguradoraId',helpMap),
		                    Ayuda: getHelpTextFromMap('descuentosAgregarComboAseguradoraId',helpMap,''),                            			          		
			          		typeAhead: true,
			          		//fieldLabel: 'Aseguradora',
			            	triggerAction: 'all',
			            	mode: 'local',
			            	store: dsAseguradora,
			            	allowBlank: false,
			            	displayField:'dsUniEco',
			                valueField:'cdUniEco',
			                hiddenName: 'cdUniEco',
			            	lazyRender:true,
			            	selectOnFocus:true,
			            	//labelSeparator:'',
			            	forceSelection: true,
			            	width:'200',
			            	listClass: 'x-combo-list-small',
			            	emptyText: 'Seleccione Aseguradora...',
			            	onSelect: function (record){
						                        formAgregarDP.findById('comboProductoId').clearValue();
						                        dsProducto.removeAll();
						                        //dataStorePlan.removeAll();
							                    dsProducto.reload({
							                                    	params: {cdElemento: form.findById('comboClientesId').getValue() ,
							                                    			 cdunieco: record.get('cdUniEco')
							                                    			 },
							                         	            waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...')
							                         	            //waitMsg: 'Espere por favor....'
							                            		 });
	                            		        this.collapse();
						                        this.setValue(record.get("cdUniEco"));
	                            		        }
	           			}),
	       	    		new Ext.form.ComboBox({
							id:'comboProductoId',
							tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			          		fieldLabel: getLabelFromMap('descuentosAgregarComboProductoId',helpMap,'Producto'),
                            tooltip: getToolTipFromMap('descuentosAgregarComboProductoId',helpMap,'Combo Producto'),	
                             hasHelpIcon:getHelpIconFromMap('descuentosAgregarComboProductoId',helpMap),
		                    Ayuda: getHelpTextFromMap('descuentosAgregarComboProductoId',helpMap,''),     		          		
				            typeAhead: true,
				            //fieldLabel: 'Producto',
				            triggerAction: 'all',
				            mode: 'local',
				            store: dsProducto,
				            allowBlank: false,
				            displayField:'descripcion',
			                valueField:'codigo',
			                hiddenName: 'cdRamo',
				            lazyRender:true,
				            selectOnFocus:true,
			            	//labelSeparator:'',
			            	width:'200',
				            listClass: 'x-combo-list-small',
				            emptyText: 'Seleccione producto...',
				            forceSelection: true,
				            onSelect: function (record){
						                        formAgregarDP.findById(('comboTipoSituacionId')).clearValue();
						                        dsTipoSituacion.removeAll();
							                    dsTipoSituacion.reload({
							                                    	params: {cdRamo: record.get("codigo")},
							                         	            waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...')
							                         	            //waitMsg: 'Espere por favor....'
							                            		 });
							                            		 
							                    /*formAgregarDP.findById(('comboPlanesId')).clearValue();
						                        dataStorePlan.removeAll();
						                        dataStorePlan.reload({
							                                    	params: {cdRamo: record.get("codigo"),
							                                    			 cdTipSit: formAgregarDP.findById('comboTipoSituacionId').getValue()
							                                    			 },
							                                    	//alert(),		 
							                         	            waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...')
							                         	            //waitMsg: 'Espere por favor....'
							                            		 });*/
							                           		 
						              			this.collapse();
						              			this.setValue(record.get("codigo"));
						              			}
		      	     	}),
		      	     	
	       	    		new Ext.form.ComboBox({
							id:'comboTipoSituacionId',
			          		tpl: '<tpl for="."><div ext:qtip="{cdTipSit}.{dsTipSit}" class="x-combo-list-item">{dsTipSit}</div></tpl>',
			          		fieldLabel: getLabelFromMap('descuentosAgregarComboTipoSituacionId',helpMap,'Riesgo'),             
                            tooltip: getToolTipFromMap('descuentosAgregarComboTipoSituacionId',helpMap,'Combo Riesgo'),		
                             hasHelpIcon:getHelpIconFromMap('descuentosAgregarComboTipoSituacionId',helpMap),
		                    Ayuda: getHelpTextFromMap('descuentosAgregarComboTipoSituacionId',helpMap,''),     	          		
				            typeAhead: true,
				            //fieldLabel: 'Tipo Situaci&oacute;n',
				            triggerAction: 'all',
				            mode: 'local',
				            store: dsTipoSituacion,
				            allowBlank: false,
				            displayField:'dsTipSit',
			                valueField:'cdTipSit',
			                hiddenName:'cdTipSit',
				            lazyRender:true,
				            selectOnFocus:true,
				            width:'200',
			            	//labelSeparator:'',
				            listClass: 'x-combo-list-small',
				            emptyText: 'Seleccione Situacion...',
				            forceSelection: true,
				            onSelect: function (record){
						                        formAgregarDP.findById('comboPlanesId').clearValue();
						                        dataStorePlan.removeAll();
							                    dataStorePlan.reload({
							                                    	params: {cdRamo: formAgregarDP.findById('comboProductoId').getValue(),
							                                    			 cdTipSit: record.get("cdTipSit")
							                                    			 },
							                         	            waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...')
							                         	            //waitMsg: 'Espere por favor....'
							                            		 });
	                            		        this.collapse();
						                        this.setValue(record.get("cdTipSit"));
	                            		        }
		      	     	}),
	       	    		new Ext.form.ComboBox({
			      			id:'comboPlanesId',
			          		tpl: '<tpl for="."><div ext:qtip="{cdPlan}.{dsPlan}" class="x-combo-list-item">{dsPlan}</div></tpl>',
			          		fieldLabel: getLabelFromMap('descuentosAgregarComboPlanesId',helpMap,'Plan'),
                            tooltip: getToolTipFromMap('descuentosAgregarComboPlanesId',helpMap,'Combo Plan'),	
                             hasHelpIcon:getHelpIconFromMap('descuentosAgregarComboPlanesId',helpMap),
		                    Ayuda: getHelpTextFromMap('descuentosAgregarComboPlanesId',helpMap,''),     		          		
				            typeAhead: true,
				            triggerAction: 'all',
				            mode: 'local',
				            //fieldLabel: 'Plan',
				            store: dataStorePlan,
				            allowBlank: false,
				            displayField:'dsPlan',
			                valueField:'cdPlan',
			                hiddenName:'cdPlan',
				            lazyRender:true,
				            selectOnFocus:true,
			            	//labelSeparator:'',
			            	width:'200',
			            	forceSelection: true,
				            listClass: 'x-combo-list-small',
			            	emptyText: 'Seleccione Plan...'
			  			})
		       		]
	        });
			
			//********VENTANA PARA GUARDAR UN DETALLE DE DESCUENTO POR PRODUCTO
	        var ventana = new Ext.Window({
	        	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('ventanaId', helpMap,'Agregar nuevo detalle de producto')+'</span>',
	        	width: 500,
	        	height: 200,
	        	layout: 'fit',
	        	plain:true,
	        	modal: true,
	        	//bodyStyle:'background: white',	
	        	items:[
	        	{
		        	items: formAgregarDP,
		        	buttonAlign:'center',
		            buttons : [ {
		                text:getLabelFromMap('windowDtllPrdctDescuentosAgrButtonGuardar', helpMap,'Guardar'),
	           			tooltip:getToolTipFromMap('windowDtllPrdctDescuentosAgrButtonGuardar', helpMap,'Guardar'),			        			
		                //text : 'Guardar',
		                disabled : false,
		                handler : function(){
		                    if (formAgregarDP.form.isValid()){	                    	
		                        formAgregarDP.form.submit({
		                            url : _ACTION_AGREGAR_PRODUCTO_DETALLE,
		                            params:{
			                            		cdDscto: CODIGO_DESCUENTO,
			                            		cdDsctod: ""
			                            	   },
		                            success : function(from, action) {
		                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),'Registro Creado');
		                                reloadGrid(1);
		                                ventana.close();
		                            },
		                            failure : function(form, action) {
		                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),action.result.errorMessages[0]);
		                            },
		                            waitTitle: 'Espere',
		                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
		                            //waitMsg : 'Guardando datos ...'
		                        });
		                    } else {
		                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
		                    }
		                }
		            }, {
		                text:getLabelFromMap('windowDtllPrdctDescuentosAgrButtonCancelar', helpMap,'Cancelar'),
	           			tooltip:getToolTipFromMap('windowDtllPrdctDescuentosAgrButtonCancelar', helpMap,'Cancelar'),			        			
		                //text : 'Cancelar',
		                handler : function() {
		                ventana.close();
		                }
		            }]
		       }]
	    	});
	    	ventana.show();
    	}
    	else{
    		//*************** DEFINICION DE FORMULARIO PARA DETALLE DE VOLUMEN*********************************
    		var formAgregarDV = new Ext.FormPanel( {
	            labelWidth : 100,
	            url : _ACTION_AGREGAR_VOLUMEN_DETALLE,
	            frame : true,
	            bodyStyle:'background: white',
	            labelAlign: 'right',
	            //bodyStyle : 'padding:5px 5px 0',
	            width : 500,
	            height: 250,
	       	    items: [
	       	    		new Ext.form.NumberField
	       	    		({fieldLabel: getLabelFromMap('agrNewDetVolNbFdPI',helpMap,'Prima Inicial'),
						  tooltip:getToolTipFromMap('agrNewDetVolNbFdPI',helpMap,'Prima Inicial'),   
						  hasHelpIcon:getHelpIconFromMap('agrNewDetVolNbFdPI',helpMap),
		                  Ayuda: getHelpTextFromMap('agrNewDetVolNbFdPI',helpMap,''),
						      	    		
		       	    	  name: 'mnVolIni',
		       	    	  width:150,
                             allowBlank: false,
                             id:'primaInicialId'}),
	       	    		
	       	    		new Ext.form.NumberField
	       	    		({fieldLabel: getLabelFromMap('agrNewDetVolNbFdPf',helpMap,'Prima Final'),
	       	    		  tooltip:getToolTipFromMap('agrNewDetVolNbFdPF',helpMap,'Prima Final'),
	       	    		  hasHelpIcon:getHelpIconFromMap('agrNewDetVolNbFdPF',helpMap),
		                  Ayuda: getHelpTextFromMap('agrNewDetVolNbFdPF',helpMap,''),
	       	    		  
	       	    		  name: 'mnVolFin',
	       	    		  width:150,
                          allowBlank: false,
	       	    		  id:'primaFinalId'}),
	       	    		
	       	    		new Ext.form.NumberField
	       	    		({fieldLabel: getLabelFromMap('agrNewDetVolNbFdPor',helpMap,'Porcentaje'),
	       	    		  tooltip:getToolTipFromMap('agrNewDetVolNbFdPor',helpMap,'Porcentaje del descuento'),
	       	    		  hasHelpIcon:getHelpIconFromMap('agrNewDetVolNbFdPor',helpMap),
	                      Ayuda: getHelpTextFromMap('agrNewDetVolNbFdPor',helpMap,''),
	       	    		  
	       	    		  name: 'prDescto',
	       	    		  width:150,
	       	    		  maxLength: 6,
	       	    		  decimalPrecision: 2,
	       	    		  id:'porcentajeId'}),
	       	    		
	       	    		new Ext.form.NumberField
	       	    		({fieldLabel: getLabelFromMap('agrNewDetVolNbFdMon',helpMap,'Monto'),
	       	    		  tooltip:getToolTipFromMap('agrNewDetVolNbFdMon',helpMap,'Monto de descuento'),
	       	    		  hasHelpIcon:getHelpIconFromMap('agrNewDetVolNbFdMon',helpMap),
	                      Ayuda: getHelpTextFromMap('agrNewDetVolNbFdMon',helpMap,''),
	       	    		  
	       	    		  name: 'mnDescto',
	       	    	 	  width:150,
	       	    	 	  maxLength: 12,
	       	    	 	  decimalPrecision: 2,
	       	    		  id:'montoId'})
		       		   ]
	        });
			
			//********VENTANA PARA GUARDAR UN DETALLE DE DESCUENTO POR VOLUMEN
	        var ventana = new Ext.Window({
	        	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('ventaId', helpMap,'Agregar nuevo detalle')+'</span>',
	        	width: 500,
	        	height: 200,
	        	layout: 'fit',
	        	plain:true,
	        	modal: true,
	        	//bodyStyle:'background: white',	        	
	        	//bodyStyle:'padding:5px;',
	            items: [
	            	{
			            items: formAgregarDV,
			            buttonAlign:'center',
			            buttons : [
			            	{
			                text:getLabelFromMap('windowDtllVlmDescuentosAgrButtonGuardar', helpMap,'Guardar'),
		           			tooltip:getToolTipFromMap('windowDtllVlmDescuentosAgrButtonGuardar', helpMap,'Guardar'),			        			
			                //text : 'Guardar',
			                disabled : false,
			                handler : function(){
			                if(formAgregarDV.form.isValid()){
			                  if ((formAgregarDV.findById("porcentajeId").getValue() == "")&&(formAgregarDV.findById("montoId").getValue()== ""))
	                	      {
							     Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400061', helpMap,'Ingrese porcentaje o monto'));
							     return;
	                	       }
			                
			                	if(formAgregarDV.findById("primaInicialId").getValue() >0 && formAgregarDV.findById("primaFinalId").getValue() >0 &&formAgregarDV.findById("primaInicialId").getValue() >= formAgregarDV.findById("primaFinalId").getValue()){
		            				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400039', helpMap,'La prima inicial no puede ser mayor que la prima final'));
			                		//Ext.MessageBox.alert('Aviso','La prima inicial no puede ser mayor que la prima final');
			                		
			                	}
			                	else if(formAgregarDV.findById("porcentajeId").getValue() != "" && formAgregarDV.findById("montoId").getValue() != ""){
		            					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400040', helpMap,'El descuento solo puede tener porcentaje o monto'));
			                			//Ext.MessageBox.alert('Aviso','El descuento solo puede tener porcentaje o monto, introduzca solo un campo');
			                		}
			                		else{
			                			if(formAgregarDV.findById("porcentajeId").getValue()>100){
		            						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400038', helpMap,'No es válido el Porcentaje indicado. Favor de revisar'));	          
			                				//Ext.MessageBox.alert('Aviso','El porcentaje no puede ser mayor que dos cifras');
			                				}
			                				else{
				                				formAgregarDV.form.submit({
						                            url : _ACTION_AGREGAR_VOLUMEN_DETALLE,
						                            params:{
					                            		cdDscto: CODIGO_DESCUENTO,
					                            		cdDsctod: ""
				                            	   },
						                            success : function(from, action) {				                           
						                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),'Registro Creado');
						                                reloadGrid(2);
						                                ventana.close();
						                            },
						                            failure : function(form, action) {
						                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),action.result.errorMessages[0]);
						                            },
						                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
						                            //waitMsg : 'guardando datos ...'
			                					});
			                		
			                    	   		 }
			                		}
			                	}else{
			                	  Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
			                	}
			                }	
			            	},
			             	{
			                text:getLabelFromMap('windowDtllVlmDescuentosAgrButtonCancelar', helpMap,'Cancelar'),
		           			tooltip:getToolTipFromMap('windowDtllVlmDescuentosAgrButtonCancelar', helpMap,'Cancelar'),			        			
			                //text : 'Cancelar',
			                handler : function(){ventana.close();}
			            	}
			            ]
	    		}
	    	]
	    	});
	    	ventana.show();
    	}
    
	}
	
	function reloadGrid(nroGrilla){
	    var mStore;
	    mStore = (nroGrilla == 1)?grid1.store:grid2.store;
	    var o = {start: 0};
	    mStore.baseParams = mStore.baseParams || {};
	    mStore.baseParams['cdDscto'] = CODIGO_DESCUENTO;
	    mStore.reload({
	                  params:{start:0,limit:itemsPerPage},
	                  callback : function(r,options,success) {
	                      if (!success) {
	                      		//Ext.MessageBox.alert('Aviso','No se encontraron registros');
	                         	mStore.removeAll();
	                      	}
	                  }
	
	              	});
	}
	
	function getSelectedRecord(tipo){
				var m;
				if(tipo == 1) {
	             		m = grid1.getSelections();
	             		if (m.length == 1 ) {
	                		return m[0];
	             		}
	            }else
	            {
	            	if(tipo == 2){
	            		m = grid2.getSelections();
	             		if (m.length == 1 ) {
	                		return m[0];
	             		}
	            	}
	            }
	        }
	
	var dsTiposDescuentos = new Ext.data.Store({
	     proxy: new Ext.data.HttpProxy({
	         url: _ACTION_COMBO_TIPO_DSCTO
	     }),
	     reader: new Ext.data.JsonReader({
	     	root: 'tiposDsctoComboBox',
	     	totalProperty: 'totalCount',
	     	id: 'codigo'
	     },[
	    {name: 'codigo', type: 'string',mapping:'codigo'},
	    {name: 'descripcion', type: 'string',mapping:'descripcion'}
	 	])
	 });
	 dsTiposDescuentos.load({callback:function(record,opt,success){valida();}});

	var dsClientes = new Ext.data.Store({
	    proxy: new Ext.data.HttpProxy({
	        url: _ACTION_COMBO_CLIENTES_CORPO
	    }),
	    reader: new Ext.data.JsonReader({
	    root: 'comboClientesCarrito',
	    totalProperty: 'totalCount'
	    },[
	   {name: 'cdElemento', type: 'string',mapping:'codigo'},
	   {name: 'dsElemen', type: 'string',mapping:'descripcion'}
		]),
		sortInfo: {field :'dsElemen', direction :'ASC'}
	});
	dsClientes.load();
	
	 var codigoPerson = new Ext.form.Hidden({
	        name:'cdPerson',
	        id:'codigoPersonaId'
	    });
	
    var dsAcumula = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_COMBO_ACUMULA
        }),
        reader: new Ext.data.JsonReader({
        	root: 'estadosDescuentosComboBox',
        	totalProperty: 'totalCount',
        	id: 'codigo'
        },[
       {name: 'codigo', type: 'string',mapping:'codigo'},
       {name: 'descripcion', type: 'string',mapping:'descripcion'}
	    ])
    });
    dsAcumula.load();
    
    var dsEstado = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_COMBO_ESTADO
        }),
        reader: new Ext.data.JsonReader({
        	root: 'estadosComboBox',
        	totalProperty: 'totalCount',
        	id: 'cdEstado'
        },[
       {name: 'cdEstado', type: 'string',mapping:'cdEstado'},
       {name: 'dsEstado', type: 'string',mapping:'dsEstado'}
	    ])
    });
    dsEstado.load();
	
    var form = new Ext.form.FormPanel({
        renderTo:'formDescuentos',
       	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formId', helpMap,'Configurar Descuentos')+'</span>',
		//title: '<span style="color:black;font-size:14px;">Configurar Descuentos</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        width: 510,
        autoHeight: true,
       // items: [{
        		//html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>'
	//},
        	items:[
		        new Ext.form.TextField( {
		        	id:"descripcionId",
				    fieldLabel: getLabelFromMap('txtFldAgregarDescrDescripcionId',helpMap,'Nombre'),
                    tooltip: getToolTipFromMap('txtFldAgregarDescrDescripcionId',helpMap,'Nombre'),
                    hasHelpIcon:getHelpIconFromMap('txtFldAgregarDescrDescripcionId',helpMap),
		            Ayuda: getHelpTextFromMap('txtFldAgregarDescrDescripcionId',helpMap,''),
                    
	                //fieldLabel : 'Nombre' ,
	                name : 'dsDscto',
	                allowBlank : false,
	                width: 250
		            }),
		        new Ext.form.ComboBox({
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    id:"comboTiposDescuentoId",
	          		fieldLabel: getLabelFromMap('descuentosAgregarComboTiposDescuentoId',helpMap,'Tipo'),
                    tooltip: getToolTipFromMap('descuentosAgregarComboTiposDescuentoId',helpMap,'Tipo'),
                     hasHelpIcon:getHelpIconFromMap('descuentosAgregarComboTiposDescuentoId',helpMap),
		            Ayuda: getHelpTextFromMap('descuentosAgregarComboTiposDescuentoId',helpMap,''),			          		
                    store: dsTiposDescuentos,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'codigo',
	                width: 250,
                    typeAhead: true,
                    allowBlank : false,
                    mode: 'local',
                    triggerAction: 'all',
                    //fieldLabel: "Tipo",
                    emptyText:'Seleccione Tipo...',
                    selectOnFocus:true,
                    forceSelection: true//,
                   // labelSeparator:'',
                   /* onSelect: function(record){
                    	this.setValue(record.get("codigo"));
                    	this.collapse();
            			if(record.get("descripcion") == "Producto"){
            				grid1.show();
            				form.findById('porcentaje').show();
            				form.findById('monto').show();
            				form.findById('porcentaje').setFieldLabel("Porcentaje:");
            				form.findById('monto').setFieldLabel("Monto:");
            				if (grid2 != null) grid2.hide(this);
            			}else{
            				grid2.show();
            				if (grid1 != null) {
            					grid1.hide();
            					form.findById('').setFieldLabel("");
            					form.findById('monto').setFieldLabel("");
            					form.findById('porcentaje').hide();
            					form.findById('monto').hide();
            				}       				
            			}
        			}*/
                    }),
		        new Ext.form.ComboBox({
                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}. {cdPerson}. {dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    id:"comboClientesId",
	          		fieldLabel: getLabelFromMap('descuentosAgregarComboClientesId',helpMap,'Nivel'),
                    tooltip: getToolTipFromMap('descuentosAgregarComboClientesId',helpMap,'Nivel'),
                     hasHelpIcon:getHelpIconFromMap('descuentosAgregarComboClientesId',helpMap),
		            Ayuda: getHelpTextFromMap('descuentosAgregarComboClientesId',helpMap,''),			          		
                    store: dsClientes,
                    displayField:'dsElemen',
                    valueField:'cdElemento',
                    hiddenName: 'cdElemento',
                    typeAhead: true,
                    mode: 'local',
                    allowBlank : false,
                    triggerAction: 'all',
                    //fieldLabel: "Cliente",
                    width: 250,
                    emptyText:'Seleccione Nivel...',
                    forceSelection: true,
                    selectOnFocus:true,
                 //   labelSeparator:'',
                    onSelect: function(record){
                    				/*
                    					Se pidió en el issue ACW-688 que el cdPerson viaje en null para guardar
                    				*/
                    				codigoPerson.setValue("");//record.get("cdPerson"));
                    				this.setValue(record.get("cdElemento"));
                    				this.collapse();
                    			}
                    }),
		        new Ext.form.ComboBox({
                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    id:"comboAcumulaId",
	          		fieldLabel: getLabelFromMap('descuentosAgregarComboAcumulaId',helpMap,'¿Acumula sobre otros descuentos?'),
                    tooltip: getToolTipFromMap('descuentosAgregarComboAcumulaId',helpMap,'¿Acumula sobre otros descuentos?'),	
                     hasHelpIcon:getHelpIconFromMap('descuentosAgregarComboAcumulaId',helpMap),
		            Ayuda: getHelpTextFromMap('descuentosAgregarComboAcumulaId',helpMap,''),		          		
                    store: dsAcumula,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'codigo',
                    typeAhead: true,
                    mode: 'local',
                    allowBlank : false,
                    triggerAction: 'all',
                    //fieldLabel: "¿Acumula sobre otros descuentos?",
                    width: 250,
                    emptyText:'Seleccione Si/No...',
                    forceSelection: true,
                    selectOnFocus:true
                    }),
		        new Ext.form.ComboBox({
                    tpl: '<tpl for="."><div ext:qtip="{cdEstado}.{dsEstado}" class="x-combo-list-item">{dsEstado}</div></tpl>',
                    id:"comboEstadosId",
	          		fieldLabel: getLabelFromMap('descuentosAgregarComboEstadosId',helpMap,'Estado'),
                    tooltip: getToolTipFromMap('descuentosAgregarComboEstadosId',helpMap,'Estado'),		
                     hasHelpIcon:getHelpIconFromMap('descuentosAgregarComboEstadosId',helpMap),
		            Ayuda: getHelpTextFromMap('descuentosAgregarComboEstadosId',helpMap,''),	          		
                    store: dsEstado,
                    displayField:'dsEstado',
                    valueField:'cdEstado',
                    hiddenName: 'cdEstado',
                    typeAhead: true,
                    mode: 'local',
                    allowBlank : false,
                    triggerAction: 'all',
                    //fieldLabel: "Estado",
                    forceSelection: true,
                    width: 250,
                    emptyText:'Seleccion estado...',
                    selectOnFocus:true
                   // labelSeparator:''
                    }),
                new Ext.form.NumberField( {
			        	id: 'porcentaje',
				    	fieldLabel: getLabelFromMap('nmbrFldAgregarPorcentajeId',helpMap,'Porcentaje'),
                    	tooltip: getToolTipFromMap('nmbrFldAgregarPorcentajeId',helpMap,'Porcentaje'),
                    	 hasHelpIcon:getHelpIconFromMap('nmbrFldAgregarPorcentajeId',helpMap),
		                 Ayuda: getHelpTextFromMap('nmbrFldAgregarPorcentajeId',helpMap,''),
		                //fieldLabel : 'Porcentaje' ,
		                name : 'prDscto',
		                maxLength: 6,
		                decimalPrecision: 2,
		                width: 100
			            }),
			    new Ext.form.NumberField( {
			        	id: 'monto',
				    	fieldLabel: getLabelFromMap('nmbrFldAgregarMontoId',helpMap,'Monto'),
                    	tooltip: getToolTipFromMap('nmbrFldAgregarMontoId',helpMap,'Monto'),
                    	 hasHelpIcon:getHelpIconFromMap('nmbrFldAgregarMontoId',helpMap),
		                  Ayuda: getHelpTextFromMap('nmbrFldAgregarMontoId',helpMap,''),
		                //fieldLabel : 'Monto' ,
		                name : 'mnDscto',
		                width: 100,
		                maxLength: 12,
		                decimalPrecision: 2
			            })
			]
		//]
		});
		
		form.render();
		
		function getSelectedRecordGrid(n){
				var m;
	             		m = grid1.getSelectionModel().getSelections();
	             		if (m.length == 1 ) {
	                		return m[n];
	             		}
	        }
    	    	
	    //form.findById('grid1').hide();
	    //form.findById('grid2').hide();
	    
	    form.findById('porcentaje').setFieldLabel("");
        form.findById('monto').setFieldLabel("");
	    
	    form.findById('porcentaje').hide();
        form.findById('monto').hide();
        
        function renderComboEditor (combo) {
			return function (value) {
				var idx = combo.store.find(combo.valueField, value);
				var rec = combo.store.getAt(idx);
				return (rec == null)?value:rec.get(combo.displayField);
			}
		}
		
		function borrarProducto(record){
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'), function(btn) {	
			if (btn == "yes") {
				var conn = new Ext.data.Connection();
	                 conn.request({
					    url: _ACTION_BORRAR_PRODUCTO_DETALLE,
					    method: 'POST',
					    params: {cdDscto: record.get("cdDscto"),
					    		 cdDsctod: record.get("cdDsctod")
					    },
					    success: function() {
					    		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('200012', helpMap,'Registro eliminado'),function(){reloadGrid(1);});
						    },
						 failure: function() {
						         Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),getLabelFromMap('400059', helpMap,'No se borro el registro seleccionado'));//'No se pudo borrar');
						         
						     }
					})
				   }//si presiona no, no hace nada
				});//end msg
	}

	function borrarVolumen(record){
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'), function(btn) {
			 if (btn == "yes") {
				var conn = new Ext.data.Connection();
	                 conn.request({
					    url: _ACTION_BORRAR_VOLUMEN_DETALLE,
					    method: 'POST',
					    params: {cdDscto: record.get("cdDscto"),
					    		 cdDsctod: record.get("cdDsctod")
					    },
					    success: function() {
					    		//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),'Registro Borrado');
					    		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('200012', helpMap,'Registro eliminado'),function(){reloadGrid(2);});
					    		//reloadGrid(2);
					    		},
						 failure: function() {
						         Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),'No se pudo eliminar');
						          Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),getLabelFromMap('400059', helpMap,'No se borro el registro seleccionado'));//'No se pudo borrar');
						         
						         }
						})
						}//Si presiona no, no hace nada         
						});
	}
	
	function guardarVolumen(form){
		startMask(form.id);
		var conn = new Ext.data.Connection();
	    conn.request({
		   	url: _ACTION_GUARDAR_VOLUMEN,
		   	method: 'POST',
		   	params: {
	   				cdDscto: CODIGO_DESCUENTO,
	   				dsNombre: form.findById("descripcionId").getValue(),
		    		cdElemento: form.findById("comboClientesId").getValue(),
		    		cdTipo: form.findById("comboTiposDescuentoId").getValue(),
		    		cdPerson: codigoPerson.getValue(),
		    		fgAcumul: form.findById("comboAcumulaId").getValue(),
		    		cdEstado: form.findById("comboEstadosId").getValue()
	   			},
	 			waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
	 			//waitMsg: 'Espere por favor...',
           		callback: function (options, success, response) {
           				if (Ext.util.JSON.decode(response.responseText).success == false) {
           				//	Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'No se pudo guardar los datos');
           				    Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),getLabelFromMap('400062', helpMap,'No se pudo guardar los datos'));//'No se pudo borrar');
           					
           				}else {
           					Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Registro Creado', 
           					// Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('200010', helpMap,'Registro Creado'));
    								function(){
    									if (CODIGO_DESCUENTO == "" || CODIGO_DESCUENTO == null) {
	           								if ((Ext.util.JSON.decode(response.responseText).cdDscto != null) && (Ext.util.JSON.decode(response.responseText).cdDscto != "")) {
	           									CODIGO_DESCUENTO = Ext.util.JSON.decode(response.responseText).cdDscto;	           									
	           								}	           								
           								}	 
    								}
           					);
           				}
           				endMask();
           			}
		});
	}
	
	function guardarProducto(form){
			startMask(form.id);
			if(form.findById("porcentaje").getValue()=="" || form.findById("monto").getValue()==""){
				var conn = new Ext.data.Connection();
                conn.request({
			    url: _ACTION_GUARDAR_PRODUCTO,
			    method: 'POST',
			    params: {
				    		cdDscto: CODIGO_DESCUENTO,
				    		dsNombre: form.findById("descripcionId").getValue(),
				    		cdElemento: form.findById("comboClientesId").getValue(),
				    		cdTipo: form.findById("comboTiposDescuentoId").getValue(),
				    		cdPerson: codigoPerson.getValue(),
				    		fgAcumul: form.findById("comboAcumulaId").getValue(),
				    		cdEstado: form.findById("comboEstadosId").getValue(),
				    		prDescto: form.findById("porcentaje").getValue(),
				    		mnDescto: form.findById("monto").getValue()
			    		 },
			    waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
			    //waitMsg: 'Espere por favor...',
               	callback: function (options, success, response) {
               				if (Ext.util.JSON.decode(response.responseText).success == false) {
               					Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'No se pudo guardar los datos');
               				}else {
               					form.findById('comboTiposDescuentoId').setDisabled(true);
	           					form.findById('comboClientesId').setDisabled(true);
               					Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Registro Creado', 
        								function(){
        									if (CODIGO_DESCUENTO == "" || CODIGO_DESCUENTO == null) {
	               								if ((Ext.util.JSON.decode(response.responseText).cdDscto != null) && (Ext.util.JSON.decode(response.responseText).cdDscto != "")) {
	               									CODIGO_DESCUENTO = Ext.util.JSON.decode(response.responseText).cdDscto;
	               								}		
               								} 
        								}
               					);
               				}
							endMask(); 
               			}
				});
			}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400091', helpMap,'No es posible tener Porcentaje y Monto al mismo tiempo'));	
         		endMask(); 
				//Ext.Msg.alert('Aviso','El descuento solo puede tener porcentaje o monto, introduzca solo un campo');
				return;
			}

	}

function valida(){
	if(CODIGO_TIPO_DESCUENTO == "P"){
		form.findById('comboTiposDescuentoId').setValue(1);
	}
	else{
		form.findById('comboTiposDescuentoId').setValue(2);
	}
}
	

	var titulo = '<span style="color:black;font-size:12px;">'+getLabelFromMap('formId2', helpMap,'Configurar Descuentos por Volumen')+'</span>';
	if(CODIGO_TIPO_DESCUENTO == "P"){
		                    titulo='<span style="color:black;font-size:12px;">'+getLabelFromMap('formId3', helpMap,'Configurar Descuentos por Producto')+'</span>';  
		                    form.setTitle(titulo);
		                    //grid2.hide();	
		                    //grid1.show();	
                    		grid1.render();	
            				//form.findById('comboTiposDescuentoId').setValue(1);
            				form.findById('porcentaje').show();
            				form.findById('monto').show();
            				form.findById('porcentaje').setFieldLabel("Porcentaje:");
            				form.findById('monto').setFieldLabel("Monto:");            				
    }else{        			
    	if(CODIGO_TIPO_DESCUENTO == "V"){            				            				  
		                    form.setTitle(titulo);
           					//grid1.hide();
           					//grid2.show();
           					grid2.render();	
           					//form.findById('comboTiposDescuentoId').setValue(2);
           					form.findById('porcentaje').setFieldLabel("");
           					form.findById('monto').setFieldLabel("");
           					form.findById('porcentaje').hide();
           					form.findById('monto').hide();      				
         }
     }
     
   form.findById('comboTiposDescuentoId').setDisabled(true);         			



}