function cargarDescuentosEditar(codigoTipoDescuento){
		var itemsPerPage = 10;
		
	    var _url;
	    var _root;
	    var _tipo;
	    if(codigoTipoDescuento.getValue() == "1"){
	    	_url = _ACTION_OBTIENE_ENCABEZADO_PRODUCTO;
	    	_root = 'encabezadoProducto';
	    	_tipo = 1;
	    }else{
	    	_url = _ACTION_OBTIENE_ENCABEZADO_VOLUMEN;
	    	_root = 'encabezadoVolumen';
	    	_tipo = 2;
	    };
	    
	    ////****************READERS Y STORE DE ENCABEZADO DE DESCUENTO ***************************************
	    var _jsonReader = new Ext.data.JsonReader(
					{root:_root,totalProperty: 'totalCount',successProperty : 'success'},
					[{name:'cdDscto',  mapping:'cdDscto',  type:'string'},
					{name: 'descripcionId',  mapping:'dsDscto',  type:'string'},
					{name: 'comboTiposDescuento',   mapping:'cdTipo',   type:'string'},
					{name: 'dsTipo',   mapping:'dsTipo',   type:'string'},
					{name: 'comboClientes', mapping:'cdElemento', type:'string'},
					{name: 'cdPerson', mapping:'cdPerson', type:'string'},
					{name: 'dsNombre', mapping:'dsNombre', type:'string'},
					{name: 'comboAcumula', mapping:'fgAcumul', type:'string'},
					{name: 'dsAcumul', mapping:'dsAcumul', type:'string'},
					{name: 'comboEstado', mapping:'cdEstado', type:'string'},
					{name: 'dsEstado', mapping:'dsEstado', type:'string'},
					{name: 'porcentaje', mapping:'prDescto', type:'string'},
					{name: 'monto', mapping:'mnDescto', type:'string'},
					{name: 'feRegist', mapping:'feRegist', type:'string'},
					{name: 'feInActi', mapping:'feInActi', type:'string'}]
				);
	    
	    var dsEncabezado = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
					url: _url
				}),
				reader: _jsonReader
		});
	    

		//**************** VARIABLE OCULTA CON EL CODIGO DE DESCUENTO ***************************************	    
	    var codigoDescuento = new Ext.form.Hidden({
	        name:'codDscto',
	        value: CODIGO_DESCUENTO 
	    });
	    
	    //**************** SI CODIGO DE DESCUENTO ES 2, STORE DE DETALLES DE VOLUMEN ***************************************
	    if(_tipo == "2"){
		 
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
		
		//*************** DEFINICION DE COLUMN MODEL DE GRILLA DE DETALLES DE VOLUMEN ********************
		var cm2 = new Ext.grid.ColumnModel([
	         {dataIndex:'cdDscto',hidden:true},{dataIndex:'cdDsctod',hidden:true},
	         {
	         header: getLabelFromMap('cm2MnVolIniPrimaInicialDescuentoEdt',helpMap,'Prima Inicial'),
        	 tooltip: getToolTipFromMap('cm2MnVolIniPrimaInicialDescuentoEdt', helpMap,'Prima Inicial'),	
        	 hasHelpIcon:getHelpIconFromMap('cm2MnVolIniPrimaInicialDescuentoEdt',helpMap),
		     Ayuda: getHelpTextFromMap('cm2MnVolIniPrimaInicialDescuentoEdt',helpMap,''),
        	    	
	         //header: "Prima Inicial",
	         dataIndex: 'mnVolIni',
	         width: 130,
	         sortable: true
	         },
	         {
	         header: getLabelFromMap('cm2MnVolFinPrimaInicialDescuentoEdt',helpMap,'Prima Final'),
        	 tooltip: getToolTipFromMap('cm2MnVolFinPrimaInicialDescuentoEdt', helpMap,'Prima Final'),
        	 hasHelpIcon:getHelpIconFromMap('cm2MnVolFinPrimaInicialDescuentoEdt',helpMap),
	       	 Ayuda: getHelpTextFromMap('cm2MnVolFinPrimaInicialDescuentoEdt',helpMap),
        	 	   	
	         //header: "Prima Final",
	         dataIndex: 'mnVolFin', 
	         width: 130,
	         sortable: true         
	         },
	         {
	         header: getLabelFromMap('cm2PrDesctoPorcentajeDescuentoEdt',helpMap,'Porcentaje'),
        	 tooltip: getToolTipFromMap('cm2PrDesctoPorcentajeDescuentoEdt', helpMap,'Porcentaje'),	   	
        	 hasHelpIcon:getHelpIconFromMap('cm2PrDesctoPorcentajeDescuentoEdt',helpMap),
		     Ayuda: getHelpTextFromMap('cm2PrDesctoPorcentajeDescuentoEdt',helpMap,''),
	         //header: "Porcentaje",
	         dataIndex: 'prDescto',
	         width: 120,
	         sortable: true
	         },
	         {
	         header: getLabelFromMap('cm2MnDesctoMontoDescuentoEdt',helpMap,'Montos'),
        	 tooltip: getToolTipFromMap('cm2MnDesctoMontoDescuentoEdt', helpMap,'Monto de descuento'),	   	
        	 hasHelpIcon:getHelpIconFromMap('cm2MnDesctoMontoDescuentoEdt',helpMap),
		     Ayuda: getHelpTextFromMap('cm2MnDesctoMontoDescuentoEdt',helpMap,''),
	         //header: "Monto",
	         dataIndex: 'mnDescto',	             
	         width: 100,
	         sortable: true         
	         }
	        ]);
	    
	    //*************DEFINICION DE LA GRILLA DE DETALLES POR VOLUMEN
		var grid2= new Ext.grid.GridPanel({
		        store: storeGrid2,
		        id:'grid2',
				border:true,
				title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
		        cm: cm2,
		        loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
		        buttonAlign:'center',
		        frame:true,
				buttons:[
		        		{
	      				text:getLabelFromMap('grid2DescuentosEdtButtonAgregar', helpMap,'Agregar'),
    	       			tooltip:getToolTipFromMap('grid2DescuentosEdtButtonAgregar', helpMap,'Agrega un detalle de descuento'),			        			
		            	//text: 'Agregar',
		            	//tooltip: 'Agregar detalle de volumen',
		            	handler:function(){agregarNuevoDetalle(2);}
		            	},
		        		{
						text:getLabelFromMap('grid2DescuentosEdtButtonGuardar', helpMap,'Guardar'),
						tooltip:getToolTipFromMap('grid2DescuentosEdtButtonGuardar', helpMap,'Guarda un nuevo detalle de descuento'),	
		        		//text:'Guardar',
		            	//tooltip:'Guardar descuento por volumen',
		            	handler:function(){guardarVolumen(formPanel);}
						},
		            	{
						text:getLabelFromMap('grid2DescuentosEdtButtonBorrar', helpMap,'Eliminar'),
						tooltip:getToolTipFromMap('grid2DescuentosEdtButtonBorrar', helpMap,'Elimina un detalle seleccionado'),			            	
		            	//text:'Borrar',
		            	//tooltip:'Borra un detalle de volumen seleccionado',
		            	handler: function () {
			            		if(getSelectedRecord(2)!= null){borrarVolumen(getSelectedRecord(2));}
			            			else{
			            				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400041', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
			            				//Ext.Msg.alert('Advertencia', 'Debe seleccionar un volumen');
			            				}
		            		}
		            	},						
		            	{
						text:getLabelFromMap('grid2DescuentosEdtButtonRegresar', helpMap,'Regresar'),
						tooltip:getToolTipFromMap('grid2DescuentosEdtButtonRegresar', helpMap,'Regresa a la pantalla anterior'),	
		            	//text:'Regresar',
		            	//tooltip:'Regresa a la página anterior',
		            	handler:function(){regresar();}
		            	}
		            	],
		    	width:500,
		    	frame:true,
				height:320,
				sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
				stripeRows: true,
				collasible: true,
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
				reloadGrid(2);
	    }else{	    	
	    //****************STORE Y READERS DE COMBOS DE PRODUCTOS ****************************************************************
	    var readerAseguradora = new Ext.data.JsonReader({
			     	root: 'aseguradoraComboBox',
			     	totalProperty: 'totalCount',
			     	id: 'cdUniEco'
				     },
				     [
				    {name: 'cdUniEco', type: 'string',mapping:'cdUniEco'},
				    {name: 'dsUniEco', type: 'string',mapping:'dsUniEco'}
				 	]
			 	);
	    
	   var dsAseguradora = new Ext.data.Store({
			     proxy: new Ext.data.HttpProxy({
			         url: _ACTION_COMBO_ASEGURADORAS
			     }),
			     reader: readerAseguradora
		 });
		 	
		 var dsProducto = new Ext.data.Store({
			     proxy: new Ext.data.HttpProxy({
			         url: _ACTION_COMBO_PRODUCTOS
			     }),
			     reader: new Ext.data.JsonReader({
				     root: 'productosAseguradoraCliente',
				     totalProperty: 'totalCount',
				     id: 'codigo'
				     },
				     [
				    {name: 'codigo', type: 'string',mapping:'codigo'},
				    {name: 'descripcion', type: 'string',mapping:'descripcion'}
				 	]
			 	)
		 });
		 
		 var dsTipoSituacion = new Ext.data.Store({
			     proxy: new Ext.data.HttpProxy({
			         url: _ACTION_COMBO_TIPO_SITUACION
			     }),
			     reader: new Ext.data.JsonReader({
				     root: 'comboTipoSituacionProducto',
				     totalProperty: 'totalCount',
				     id: 'cdTipSit'
			     	},
			     	[
				    {name: 'cdTipSit', type: 'string',mapping:'cdTipSit'},
				    {name: 'dsTipSit', type: 'string',mapping:'dsTipSit'}
				 	]
			 	)
		 });
		
		var dataStorePlan = new Ext.data.Store({
		     proxy: new Ext.data.HttpProxy({
		         url: _ACTION_COMBO_PLANES
		     }),
			     reader: new Ext.data.JsonReader({
				     root: 'planesComboBox',
				     totalProperty: 'totalCount',
				     id: 'cdPlan'
				     },
				     [
				    {name: 'cdPlan', type: 'string',mapping:'cdPlan'},
				    {name: 'dsPlan', type: 'string',mapping:'dsPlan'}
			 		]	
			 	)
		 });
		  
		  //*************** READER Y STORE DE GRILLA DE DETALLES DE PRODUCTO ********************
		  var _jsReader = new Ext.data.JsonReader({
		           		root:'detalleProducto',
		            	successProperty : '@success',
		            	totalProperty: 'totalCount'
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
	            reader: _jsReader
	 		});
	 		 	
		storeGrid1.load({params:{cdDscto: CODIGO_DESCUENTO}});
		
		//*************** DEFINICION DE COLUMN MODEL DE GRILLA DE DETALLES DE PRODUCTO ********************      	     
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
	          header: getLabelFromMap('cm1DsUniEcoAseguradoraDescuentoEdt',helpMap,'Aseguradora'),
        	  tooltip: getToolTipFromMap('cm1DsUniEcoAseguradoraDescuentoEdt', helpMap,'Aseguradora'),	   	
	          //header: "Aseguradora",
	          dataIndex: 'dsUniEco',
	          width: 130,
	          sortable: true
		      },
		      {
	          header: getLabelFromMap('cm1DsRamoProductoDescuentoEdt',helpMap,'Producto'),
        	  tooltip: getToolTipFromMap('cm1DsRamoProductoDescuentoEdt', helpMap,'Producto'),	   	
	          //header: "Producto",
	          dataIndex: 'dsRamo',
	          width: 130,
	          sortable: true
	          },
		      {
	          header: getLabelFromMap('cm1DsTipSitTipoSituacionDescuentoEdt',helpMap,'Riesgo'),   
        	  tooltip: getToolTipFromMap('cm1DsTipSitTipoSituacionDescuentoEdt', helpMap,'Riesgo'),	   	
	          //header: "Tipo Situación",
	          dataIndex: 'dsTipSit',
	          width: 130,
	          sortable: true
	          },
		      {
	          	header: getLabelFromMap('cm1DsPlanPlanDescuentoEdt',helpMap,'Plan'),
        		tooltip: getToolTipFromMap('cm1DsPlanPlanDescuentoEdt', helpMap,'Plan'),	   	
	          //header: "Plan",
	          dataIndex: 'dsPlan',
	          width: 90,
	          sortable: true
	          }
	    	])
		
		var grid1 = new Ext.grid.GridPanel({
		        store: storeGrid1,
		        id:'grid1',
				border:true,
				title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
		        cm: cm1,
		        frame:true,
		        buttonAlign:'center',
				buttons:[
						{
							text:getLabelFromMap('grid1DescuentosEdtButtonAgregar', helpMap,'Agregar'),
							tooltip:getToolTipFromMap('grid1DescuentosEdtButtonAgregar', helpMap,'Agrega un nuevo detalle'),	
			            	//text: 'Agregar',
			            	//tooltip: 'Agregar detalle de producto',
			            	handler:function(){agregarNuevoDetalle(1);}
		            	},
		        		{
							text:getLabelFromMap('grid1DescuentosEdtButtonGuardar', helpMap,'Guardar'),
							tooltip:getToolTipFromMap('grid1DescuentosEdtButtonGuardar', helpMap,'Guarda un detalle'),	
			        		//text:'Guardar',
			            	//tooltip:'Guardar descuento por producto',
			            	handler:function(){guardarProducto(formPanel);}
		            	},
		            	{
							text:getLabelFromMap('grid1DescuentosEdtButtonBorrar', helpMap,'Eliminar'),
							tooltip:getToolTipFromMap('grid1DescuentosEdtButtonBorrar', helpMap,'Elimina un detalle seleccionado'),	
			            	//text:'Borrar',
			            	//tooltip:'Borra un detalle de producto seleccionado',
			            	handler: function () {
			            				if(getSelectedRecord(1)!=null){borrarProducto(getSelectedRecord(1));}
			            				else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
			            			}
		            	},		            	
		            	{
							text:getLabelFromMap('grid1DescuentosEdtButtonRegresar', helpMap,'Regresar'),
							tooltip:getToolTipFromMap('grid1DescuentosEdtButtonRegresar', helpMap,'Regresa a la pantalla anterior'),	
			            	//text:'Regresar',
			            	//tooltip:'Regresa a la página anterior',
			            	handler:function(){regresar();}
		            	}
		            	],
		    	width:500,
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
	   			reloadGrid(1);
	    }//end else
	 
	 //*************** STORES READERS DE COMBOS DE DESCUENTOS *******************************************************************
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
	 	]
	 	)
	 });
	
		
	var dsClientes = new Ext.data.Store({
	    proxy: new Ext.data.HttpProxy({
	        url: _ACTION_COMBO_CLIENTES_CORPO
	    }),
	    reader: new Ext.data.JsonReader({
	    root: 'comboClientesCarrito',
	    totalProperty: 'totalCount'
	    },[
	   {name: 'cdElemento', type: 'string',mapping:'codigo'},
	   {name: 'dsElemen', type: 'string',mapping:'descripcion'},
	   {name: 'cdPerson', type: 'string',mapping:'cdPerson'} //Actualmente no viene en el sp
		])
	});
	
	

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
    
    
    var dsEstado1 = new Ext.data.Store({
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
       //{ name: 'cdEstado', type: 'string',mapping:'codigo'},
        //{ name: 'dsEstado', type: 'string',mapping:'descripcion'}
	    ])
    });
    
	
	//*************** DEFINICION DE COMBOS DE DESCUENTOS *******************************************************************
	var comboTiposDescuento = new Ext.form.ComboBox({
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    id:'comboTiposDescuentoId',
	          		fieldLabel: getLabelFromMap('descuentosEditarComboTiposDescuentoId',helpMap,'Tipo'),
                    tooltip: getToolTipFromMap('descuentosEditarComboTiposDescuentoId',helpMap,'Tipo de descuento'),		
                    hasHelpIcon:getHelpIconFromMap('descuentosEditarComboTiposDescuentoId',helpMap),
		            Ayuda: getHelpTextFromMap('descuentosEditarComboTiposDescuentoId',helpMap,''),                    	          		
                    store: dsTiposDescuentos,
                    displayField:'descripcion',
                    valueField:'codigo',
                    disabled: true,
                    hiddenName: 'comboTiposDescuento',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    //fieldLabel: "Tipo",
                    forceSelection: true,
                    width: 250,
                    selectOnFocus:true
	});
     
     var comboClientes = new Ext.form.ComboBox({
                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    id:'comboClientesId',
	          		fieldLabel: getLabelFromMap('descuentosEditarComboClientesId',helpMap,'Nivel'),
                    tooltip: getToolTipFromMap('descuentosEditarComboClientesId',helpMap,'Nivel de descuento'),
                     hasHelpIcon:getHelpIconFromMap('descuentosEditarComboClientesId',helpMap),
		            Ayuda: getHelpTextFromMap('descuentosEditarComboClientesId',helpMap,''),     
                    			          		
                    store: dsClientes,
                    displayField:'dsElemen',
                    valueField:'cdElemento',
                    disabled: true,
                    hiddenName: 'comboClientes',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    //fieldLabel: "Cliente",
                    forceSelection: true,
                    width: 250,
                    emptyText:'Seleccione Nivel...',
                    selectOnFocus:true,
                    onSelect: function(record){
                    				//Se deja en blanco, como en el agregar.
                    				formPanel.findById(('codigoPersonaId')).setValue("");//record.get("cdPerson"));
                    			}
                    });
                    
	 var comboAcumula = new Ext.form.ComboBox({
                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    id:'comboAcumulaId',
	          		fieldLabel: getLabelFromMap('descuentosEditarComboDescuentosId',helpMap,'¿Acumula sobre otros descuentos?'),
                    tooltip: getToolTipFromMap('descuentosEditarComboDescuentosId',helpMap,'¿Acumula sobre otros descuentos?'),		
                    hasHelpIcon:getHelpIconFromMap('descuentosEditarComboDescuentosId',helpMap),
		            Ayuda: getHelpTextFromMap('descuentosEditarComboDescuentosId',helpMap,''),     
                    			          		          		
                    store: dsAcumula,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'comboAcumula',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    //fieldLabel: "¿Acumula sobre otros descuentos?",
                    forceSelection: true,
                    width: 250,
                    emptyText:'Seleccione Si/No...',
                    selectOnFocus:true
                    });
	 
	 var comboEstados = new Ext.form.ComboBox({
                    tpl: '<tpl for="."><div ext:qtip="{cdEstado}.{dsEstado}" class="x-combo-list-item">{dsEstado}</div></tpl>',
                    id:'comboEstadosId',
	          		fieldLabel: getLabelFromMap('descuentosEditarComboEstadosId',helpMap,'Estado'),
                    tooltip: getToolTipFromMap('descuentosEditarComboEstadosId',helpMap,'Estado de descuento'),		
                     hasHelpIcon:getHelpIconFromMap('descuentosEditarComboEstadosId',helpMap),
		            Ayuda: getHelpTextFromMap('descuentosEditarComboEstadosId',helpMap,''),  	          		
                    store: dsEstado1,
                    displayField:'dsEstado',                    
                    valueField:'cdEstado',
                    hiddenName: 'comboEstado',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    //fieldLabel: "Estado",
                    width: 250,
                    emptyText:'Seleccione estado...',
                    forceSelection: true,
                    selectOnFocus:true
                    });
	
	//*************** DEFINICION DE NUMBER FIELDS Y HIDDEN DE DESCUENTOS ******************************************************
	var numberFieldPorcentaje = new Ext.form.NumberField( {
			        	id: 'porcentaje',
				    	fieldLabel: getLabelFromMap('nmbrFldEditarPorcentajeId',helpMap,'Porcentajes'),
                    	tooltip: getToolTipFromMap('nmbrFldEditarPorcentajeId',helpMap,'Porcentaje'),
                        hasHelpIcon:getHelpIconFromMap('nmbrFldEditarPorcentajeId',helpMap),
		                Ayuda: getHelpTextFromMap('nmbrFldEditarPorcentajeId',helpMap,''),  	
		                //fieldLabel : 'Porcentaje' ,
		                name : 'prDescto',
		                hiddenName: 'porcentaje', 
		                width: 50,
		                maxLength: 6,
		                decimalPrecision: 2
			            });
			            
    var numberFieldMonto = new Ext.form.NumberField( {
			        	id: "monto",
				    	fieldLabel: getLabelFromMap('nmbrFldEditarMontoId',helpMap,'Monto'),
                    	tooltip: getToolTipFromMap('nmbrFldEditarMontoId',helpMap,'Monto'),
                    	  hasHelpIcon:getHelpIconFromMap('nmbrFldEditarMontoId',helpMap),
		                Ayuda: getHelpTextFromMap('nmbrFldEditarPorcentajeId',helpMap,''),  
		                //fieldLabel : 'Monto' ,
		                name : 'mnDescto',
		                hiddenName: 'monto',
		                width: 150,
		                maxLength: 12,
		                decimalPrecision: 2
			            });
			            
	var hidden = new Ext.form.Hidden({
						name:'hidden'
						});
	
	//*************** DEFINICION DE FORMULARIO DE DESCUENTOS *******************************************************************
    var formPanel = new Ext.FormPanel({
        renderTo:'formDescuentos',
        url: _url,
        store: dsEncabezado,
        reader: _jsonReader,
        successProperty : 'success',
       	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formPanelId', helpMap,'Configurar Descuentos')+'</span>',
		//title: '<span style="color:black;font-size:14px;">Configurar Descuentos</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        width: 510,
        autoHeight: true,
        items: [
        		codigoPerson,
        		codigoDescuento,
		        new Ext.form.TextField( {
		        	id:'descripcionId',
				    fieldLabel: getLabelFromMap('txtFldEditarDescrDescripcionId',helpMap,'Nombre'),
                    tooltip: getToolTipFromMap('txtFldEditarDescrDescripcionId',helpMap,'Nombre'),
                    hasHelpIcon:getHelpIconFromMap('txtFldEditarDescrDescripcionId',helpMap),
		             Ayuda: getHelpTextFromMap('txtFldEditarDescrDescripcionId',helpMap,''),
                    
	                //fieldLabel : 'Nombre',
	                name : 'dsDscto',
	                hiddenName: 'descripcionId',
	                allowBlank : false,
	                width: 250
		            }),
		        comboTiposDescuento,
		        comboClientes,
		        comboAcumula,
		        comboEstados,
                (_tipo == 1)?numberFieldPorcentaje:hidden,
                (_tipo == 1)?numberFieldMonto:hidden,
                (_tipo == 2)?grid2:grid1
			]
		});
		
	formPanel.render();
		formPanel.load({
		waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
        waitMsg: getLabelFromMap('400028',helpMap,'Cargando datos ...'),
		params:{cdDscto: CODIGO_DESCUENTO},
		failure: function() {
								Ext.Msg.alert('Error', 'No se pudo leer los datos', function() {endMask();});
		},
		success: function(form, action) {


		if(_tipo==1){
		var _cdTipo = action.reader.jsonData.encabezadoProducto[0].cdTipo;
		var _cdElemento = action.reader.jsonData.encabezadoProducto[0].cdElemento;
		var _fgAcumul = action.reader.jsonData.encabezadoProducto[0].fgAcumul;
		var _cdEstado = action.reader.jsonData.encabezadoProducto[0].cdEstado;
		}
		else {
		  var _cdTipo = action.reader.jsonData.encabezadoVolumen[0].cdTipo;
		  var _cdElemento = action.reader.jsonData.encabezadoVolumen[0].cdElemento;
		  var _fgAcumul = action.reader.jsonData.encabezadoVolumen[0].fgAcumul;
		  var _cdEstado = action.reader.jsonData.encabezadoVolumen[0].cdEstado;
		  }

	dsTiposDescuentos.load({
			callback: function (r, options, success){
				if (success) {
					formPanel.findById('comboTiposDescuentoId').setValue(_cdTipo);
							}
				dsClientes.load({
					callback: function (r, options, success){
					if (success) {
								formPanel.findById('comboClientesId').setValue(_cdElemento);
							}
						dsAcumula.load({
						callback: function (r, options, success){
							if (success) {
								formPanel.findById('comboAcumulaId').setValue(_fgAcumul);
										}
									dsEstado1.load({
									callback: function (r, options, success){
									if (success) {
										formPanel.findById('comboEstadosId').setValue(_cdEstado);
												}
									
												}
										});
									}
								});
							}
	
					});
				}
			});
	  }
	});
		
		
		//*************** FUNCIONES DE UTILIDAD PARA DESCUENTOS **************************************************************
		
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
		
		function reloadGrid(nroGrilla){
		    var mStore;
		    mStore = (nroGrilla == 1)?grid1.store:grid2.store;
		    var o = {start: 0};
		    mStore.baseParams = mStore.baseParams || {};
		    mStore.baseParams['cdDscto'] = CODIGO_DESCUENTO;
		    mStore.reload(
		    			{
		                  params:{start:0,limit:itemsPerPage},
		                  callback : function(r,options,success) {
		                      if (!success) {
		                      		//Ext.MessageBox.alert('Aviso','No se encontraron registros');
		                         	mStore.removeAll();
		                      	}
		                  }
		             	}
		             );
	  	}
		
		function renderComboEditor (combo) {
			return function (value, meta, record) {
				var idx = combo.store.find(combo.valueField, value);
				var rec = combo.store.getAt(idx);
				var valor = (value != "")?record.get('dsPlan'):"";
				//combo.setDisabled((value != "")?true:false);
				return (rec == null)?valor:rec.get(combo.displayField);
			}
		}
	
	//*************** DEFINICION DE FORMULARIOS Y VENTANAS ******************************************
	
	 function agregarNuevoDetalle(tipo){
	  
		if(tipo == 1){		
	         dsAseguradora.load({
	         			params: {cdElemento: formPanel.findById('comboClientesId').getValue()},
						waitMsg: 'Espere por favor....'
						});
			dsProducto.removeAll();
			dsTipoSituacion.removeAll();
			dataStorePlan.removeAll();
	        
	        //*************** DEFINICION DE FORMULARIO PARA DETALLE DE PRODUCTO*********************************
	        var formAgregarDP = new Ext.FormPanel( {
	            labelWidth : 100,
	            url : _ACTION_AGREGAR_PRODUCTO_DETALLE,
	            frame : true,
	            bodyStyle : 'padding:5px 5px 0',
	            bodyStyle : 'background: white',	
	            labelAlign:'right',            
	            width : 510,
	            height:350,
	       	    items: [
	       	    		new Ext.form.ComboBox({
							id:'comboAseguradoraId',
							tpl: '<tpl for="."><div ext:qtip="{cdUniEco}.{dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
			          		fieldLabel: getLabelFromMap('descuentosEditarComboAseguradoraId',helpMap,'Aseguradora'),
        		            tooltip: getToolTipFromMap('descuentosEditarComboAseguradoraId',helpMap,'Aseguradora'),	
        		            hasHelpIcon:getHelpIconFromMap('txtFldEditarDescrDescripcionId',helpMap),
		                   Ayuda: getHelpTextFromMap('txtFldEditarDescrDescripcionId',helpMap,''),
                    		          		
			          		typeAhead: true,
			          		//fieldLabel: 'Aseguradora',
			            	triggerAction: 'all',
			            	store: dsAseguradora,
			            	mode: 'local',
			            	displayField:'dsUniEco',
			                valueField:'cdUniEco',
			                hiddenName: 'cdUniEco',
			            	lazyRender:true,
			            	selectOnFocus:true,
							allowBlank: false,
							blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
			            	width:'200',
			            	forceSelection: true,
			            	listClass: 'x-combo-list-small',
			            	emptyText: 'Seleccione Aseguradora...',
			            	onSelect: function (record){
						                        formAgregarDP.findById('comboProductoId').clearValue();
						                        dsProducto.removeAll();
						                        //dataStorePlan.removeAll();
							                    dsProducto.reload({
							                                    	params: {cdElemento: formPanel.findById('comboClientesId').getValue() ,
							                                    			 cdunieco: record.get('cdUniEco')
							                                    			 },
							                         	            waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...')
							                         	            //waitMsg: 'Espere por favor....'
							                            		 });
							                    //alert(1);
	                            		        this.collapse();
						                        this.setValue(record.get("cdUniEco"));
	                            		        }
	           			}),
	       	    		new Ext.form.ComboBox({
							id:'comboProductoId',
							tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			          		fieldLabel: getLabelFromMap('descuentosEditarComboProductoId',helpMap,'Producto'),
        		            tooltip: getToolTipFromMap('descuentosEditarComboProductoId',helpMap,'Producto'),			          		
				            typeAhead: true,
				            //fieldLabel: 'Producto',
							mode: 'local',
				            triggerAction: 'all',
				            store: dsProducto,
				            displayField:'descripcion',
			                valueField:'codigo',
			                hiddenName: 'cdRamo',
			                selectOnFocus:true,
				            lazyRender:true,
							allowBlank: false,
							blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
				            forceSelection: true,
				            listClass: 'x-combo-list-small',
				            emptyText: 'Seleccione producto...',
				            onSelect: function (record){
						                        formAgregarDP.findById(('comboTipoSituacionId')).clearValue();
						                        dsTipoSituacion.removeAll();
							                    dsTipoSituacion.reload({
							                                    	params: {cdRamo: record.get("codigo")},
							                         	            waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...')
							                         	            //waitMsg: 'Espere por favor....'
							                            		 });
							                           		 
							                    //alert(2);        		 
							                    /*formAgregarDP.findById(('comboPlanesId')).clearValue();
						                        dataStorePlan.removeAll();
						                        dataStorePlan.reload({
							                                    	params: {cdRamo: record.get("codigo"),
							                                    			 cdTipSit: formAgregarDP.findById('comboTipoSituacionId').getValue()
							                                    			 },
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
			          		fieldLabel: getLabelFromMap('descuentosEditarComboTipoSituacionId',helpMap,'Riesgo'),    
        		            tooltip: getToolTipFromMap('descuentosEditarComboTipoSituacionId',helpMap,'Riesgo'),			          		
				            typeAhead: true,
				            mode: 'local',
							allowBlank: false,
							blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
				            triggerAction: 'all',
				            store: dsTipoSituacion,
				            displayField:'dsTipSit',
			                valueField:'cdTipSit',
			                hiddenName:'cdTipSit',
				            lazyRender:true,
				            selectOnFocus:true,
				            forceSelection: true,
				            listClass: 'x-combo-list-small',
				            emptyText: 'Seleccione Situacion...',
				           
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
			          		fieldLabel: getLabelFromMap('descuentosEditarComboPlanesId',helpMap,'Plan'),
        		            tooltip: getToolTipFromMap('descuentosEditarComboPlanesId',helpMap,'Plan'),			          		
				            typeAhead: true,
				            mode: 'local',
				            triggerAction: 'all',
				            //fieldLabel: 'Plan',
				            store: dataStorePlan,
				            allowBlank: false,
				            displayField:'dsPlan',
			                valueField:'cdPlan',
			                hiddenName:'cdPlan',
			                forceSelection: true,
				            lazyRender:true,
				            listClass: 'x-combo-list-small',
			            	emptyText: 'Seleccione Plan...'
			  			})
		       		]
	        });
			
			//********VENTANA PARA GUARDAR UN DETALLE DE DESCUENTO POR PRODUCTO
	        var ventana = new Ext.Window({
	        	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('79', helpMap,'Agregar nuevo detalle de producto')+'</span>',
	        	//title: 'Agregar nuevo detalle de producto',
	        	width: 500,
	        	height:215,
	        	layout: 'fit',
	        	plain:true,
	        	modal: true,
	        	bodyStyle:'padding:5px;',
	        	buttonAlign:'center',
	        	items: formAgregarDP,
	            buttons : 
	            [
	            	{
	                text:getLabelFromMap('windowDtllPrdctDescuentosEdtButtonGuardar', helpMap,'Guardar'),
           			tooltip:getToolTipFromMap('windowDtllPrdctDescuentosEdtButtonGuardar', helpMap,'Guardar'),			        			
	                //text : 'Guardar',
	                disabled : false,
	                handler : function(){
	                		if(formAgregarDP.form.isValid()){
                        		formAgregarDP.form.submit({
		                            url : _ACTION_AGREGAR_PRODUCTO_DETALLE,
		                            params:{
		                            		cdDscto: CODIGO_DESCUENTO,
		                            		cdDsctod: ""
		                            	   },
		                            success : function(from, action) {
		                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),'Registro Creado',function(){
		                                	reloadGrid(1);
		                                	ventana.close();
		                                });
		                                
		                            },
		                            failure : function(form, action) {
		                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),action.result.errorMessages[0]);
		                            },
									waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
		                            //waitMsg : 'guardando datos ...'
                        		});
                        	}else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));}
	                		}
		            },
		            {
	                text:getLabelFromMap('windowDtllPrdctDescuentosEdtButtonCancelar', helpMap,'Cancelar'),
           			tooltip:getToolTipFromMap('windowDtllPrdctDescuentosEdtButtonCancelar', helpMap,'Cancelar'),			        			
		                //text : 'Cancelar',
		                handler : function(){ventana.close();}
		            }
	            ]
	    	});
	    	ventana.show();
    	}
    	else{
    		//*************** DEFINICION DE FORMULARIO PARA DETALLE DE VOLUMEN*********************************
    		var formAgregarDV = new Ext.FormPanel( {
	            labelWidth : 100,
	            url : _ACTION_AGREGAR_VOLUMEN_DETALLE,
	            frame : true,
	            bodyStyle : 'padding:5px 5px 0',
	            bodyStyle: 'background: white',
	            labelAlign:'right',
	            width : 300,
	            height:350,
	       	    items: [
	       	    		codigoDescuento,
	       	    		new Ext.form.NumberField
	       	    		({fieldLabel: getLabelFromMap('agrNewDetVolNbFdPI',helpMap,'Prima Inicial'),
						  tooltip:getToolTipFromMap('agrNewDetVolNbFdPI',helpMap,'Prima Inicial'),       	    		
		       	    	  name: 'mnVolIni',
		       	    	  width:150,
	       	    	 	  allowBlank: false,
		       	    	  id:'primaInicialId'}),
	       	    		
	       	    		new Ext.form.NumberField
	       	    		({fieldLabel: getLabelFromMap('agrNewDetVolNbFdPf',helpMap,'Prima Final'),
	       	    		  tooltip:getToolTipFromMap('agrNewDetVolNbFdPF',helpMap,'Prima Final'),
	       	    		  name: 'mnVolFin',
	       	    		  width:150,
	       	    	 	  allowBlank: false,
	       	    		  id:'primaFinalId'}),
	       	    		
	       	    		new Ext.form.NumberField
	       	    		({fieldLabel: getLabelFromMap('agrNewDetVolNbFdPor',helpMap,'Porcentaje'),
	       	    		  tooltip:getToolTipFromMap('agrNewDetVolNbFdPor',helpMap,'Porcentaje'),
	       	    		  name: 'prDescto',
	       	    		  //allowBlank: false,
	       	    		  width:150, 
	       	    		  maxLength: 6,
	       	    		  decimalPrecision: 2,
	       	    		  id:'porcentajeId'}),
	       	    		
	       	    		new Ext.form.NumberField
	       	    		({fieldLabel: getLabelFromMap('agrNewDetVolNbFdMon',helpMap,'Monto'),
	       	    		  tooltip:getToolTipFromMap('agrNewDetVolNbFdMon',helpMap,'Monto'),
	       	    		  name: 'mnDescto',
	       	    		  //allowBlank: false,
	       	    	 	  width:150,
	       	    	 	  maxLength: 12,
	       	    	 	  decimalPrecision: 2,
	       	    		  id:'montoId'})
		       		]
	        });
			
			//********VENTANA PARA GUARDAR UN DETALLE DE DESCUENTO POR VOLUMEN
	        var ventana = new Ext.Window({
	        	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('80', helpMap,'Agregar nuevo detalle de vol&uacute;men')+'</span>',
	        	//title: 'Agregar nuevo detalle de volumen',
	        	width: 500,
	        	height:215,
	        	layout: 'fit',
	        	plain:true,
	        	modal: true,
	        	bodyStyle:'padding:5px;',
	        	//bodyStyle:'background: white',
	        	labelAlign:'right',
	        	buttonAlign:'center',
	        	items: formAgregarDV,
	            buttons : 
	            [
	            	{
	                text:getLabelFromMap('windowDtllVlmDescuentosEdtButtonGuardar', helpMap,'Guardar'),
           			tooltip:getToolTipFromMap('windowDtllVlmDescuentosEdtButtonGuardar', helpMap,'Guardar'),			        			
	                //text : 'Guardar',
	                disabled : false,
	                handler : function(){
	                if (formAgregarDV.form.isValid()) {
	                
	                	if ((formAgregarDV.findById("porcentajeId").getValue() == "")&&(formAgregarDV.findById("montoId").getValue()== ""))
	                	{
							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400040', helpMap,'Ingrese porcentaje o monto'));
							return;
	                	}
	                	
	                	if(formAgregarDV.findById("primaInicialId").getValue() >= formAgregarDV.findById("primaFinalId").getValue()){
            				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400039', helpMap,'La prima inicial no puede ser mayor que la prima final'));
	                		//Ext.Msg.alert('Advertencia', 'La prima inicial no puede ser mayor que la prima final');
	                	}
	                	else if(formAgregarDV.findById("porcentajeId").getValue() != "" && formAgregarDV.findById("montoId").getValue() != ""){
            					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400040', helpMap,'El descuento solo puede tener porcentaje o monto, introduzca solo un campo'));
	                			//Ext.Msg.alert('Advertencia','El descuento solo puede tener porcentaje o monto, introduzca solo un campo');
	                		}
	                		else{
	                			if(formAgregarDV.findById("porcentajeId").getValue()>99){
            						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400038', helpMap,'El porcentaje no puede ser mayor que dos cifras'));
            						//Ext.Msg.alert('Advertencia','El porcentaje no puede ser mayor que dos cifras');
	                				}
	                				else{
		                				formAgregarDV.form.submit({
				                            url : _ACTION_AGREGAR_VOLUMEN_DETALLE,
				                            params:{
			                            		cdDscto: CODIGO_DESCUENTO,
			                            		cdDsctod: ""
		                            	   },
				                            success : function(from, action, response) {
				                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),action.result.actionMessages[0]/*Ext.util.JSON.decode(response.responseText).actionMessages[0]*/);
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
	                	}else {
	                       Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
	                	}
	            	}
	            	},
	             	{
	                text:getLabelFromMap('windowDtllVlmDescuentosEdtButtonCancelar', helpMap,'Cancelar'),
           			tooltip:getToolTipFromMap('windowDtllVlmDescuentosEdtButtonCancelar', helpMap,'Cancelar'),			        			
	                //text : 'Cancelar',
	                handler : function(){ventana.close();}
	            	}
	            ]
	    	});
	    	ventana.show();
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
					    success: function(form, action) {
					    		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),'Registro Eliminado');
					    		reloadGrid(1);
						    },
						 failure: function(form, action) {
						         Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),action.result.errorMessages[0]);
						         //Ext.Msg.alert('Aviso','No se pudo borrar');
						     }
					})
				   }//si presiona no, no hace nada
				});//end msg
	}

/************************
function borrarProducto(record) {
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se borrara el registro seleccionado'),function(btn)
		    {
		        if (btn == "yes")
		        {
         			var _params = {
         						cdDscto: record.get("cdDscto"),
					    		 cdDsctod: record.get("cdDsctod")
         			};
         			execConnection(_ACTION_BORRAR_PRODUCTO_DETALLE, _params, cbkConnectionProducto);
               }
            })
};

function cbkConnectionProducto (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid(1)});
	}
}; 
*****************************/


/*******************************
	function borrarVolumen(record){
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400031', helpMap,'Se borrar&aacute; el registro seleccionado'), function(btn) {
			 if (btn == "yes") {
				var conn = new Ext.data.Connection();
	                 conn.request({
					    url: _ACTION_BORRAR_VOLUMEN_DETALLE,
					    method: 'POST',
					    params: {cdDscto: record.get("cdDscto"),
					    		 cdDsctod: record.get("cdDsctod")
					    },
					    success: function(form, action) {
					    		console.log(action);
					    		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid(2);});
					    		//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),action.result.actionMessages[0]/*Ext.util.JSON.decode(response.responseText).actionMessages[0]);
					    		//Ext.Msg.alert('Aviso','Borrado exitoso');
					    		//reloadGrid(2);
					    		},
						 failure: function(form, action) {
						         Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),action.result.errorMessages[0]);}
						         //Ext.Msg.alert('Aviso','No se pudo borrar');}
						})
						
						}//Si presiona no, no hace nada         
						});
	}
	
**********************************************/
	

function borrarVolumen(record) {
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
		    {
		        if (btn == "yes")
		        {
         			var _params = {
         						cdDscto: record.get("cdDscto"),
					    		 cdDsctod: record.get("cdDsctod")
         			};
         			execConnection(_ACTION_BORRAR_VOLUMEN_DETALLE, _params, cbkConnectionVolumen);
               }
             })
};

function cbkConnectionVolumen (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid(2)});
	}
};      


	
	
	
	function guardarVolumen(form){
		
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
           							Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                   					//Ext.Msg.alert('Error', 'No se pudo guardar los datos');
                   					band = false;
                   				}else {
		           					Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0],                    					
                   					//Ext.Msg.alert('Confirmación', 'Se ha guardado con éxito los datos', 
            								function(){
            									if (CODIGO_DESCUENTO == "" || CODIGO_DESCUENTO == null) {
	                   								if ((Ext.util.JSON.decode(response.responseText).cdDscto != null) && (Ext.util.JSON.decode(response.responseText).cdDscto != "")) {
	                   									CODIGO_DESCUENTO = Ext.util.JSON.decode(response.responseText).cdDscto;
	                   								}		
                   								} 
            								}
                   					);
                   				}
                   			}
					});
	}
	
	function guardarProducto(form){
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
               					//Ext.Msg.alert('Error', 'No se pudo guardar los datos');
               				}else {
               					Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0], 
        								function(){
        									if (CODIGO_DESCUENTO == "" || CODIGO_DESCUENTO == null) {
	               								if ((Ext.util.JSON.decode(response.responseText).cdDscto != null) && (Ext.util.JSON.decode(response.responseText).cdDscto != "")) {
	               									CODIGO_DESCUENTO = Ext.util.JSON.decode(response.responseText).cdDscto;
	               								}		
               								} 
        								}
               					);
               				}
               			}
				});
			}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400038', helpMap,'El descuento solo puede tener porcentaje o monto, introduzca solo un campo'));	          
				//Ext.Msg.alert('Aviso','El descuento solo puede tener porcentaje o monto, introduzca solo un campo');
				return;
			}
	}
	
	}