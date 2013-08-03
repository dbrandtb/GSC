
Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
	

var itemsPerPage = 20;

var storeClientes;
var storeAseguradora;
var storeProducto;
var storeInstrumentos;
var storeGrid;

var comboClientes;
var comboAseguradora;
var comboProducto;
var comboInstrumentos;

var gridBusqueda;

	
	storeClientes = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: _ACTION_COMBO_CLIENTES
        }),
        reader: new Ext.data.JsonReader({
            root: 'clientesList',
            id: 'cdCliente'
	        },[
           {name: 'cdElemento', type: 'string',mapping:'cdCliente'},
           {name: 'dsElemento', type: 'string',mapping:'dsCliente'}
        ]),
		remoteSort: true
    });
	

	comboClientes =new Ext.form.ComboBox({
							id:'comboClientesId',
    	                    //tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{value}</div></tpl>',
							store: storeClientes,
						    displayField:'dsElemento',
						    valueField: 'cdElemento',
					    	typeAhead: true,
					    	mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'Seleccione un Cliente...',
					    	selectOnFocus:true,
					    	forceSelection:true,
					    	width:200,
						    fieldLabel: 'Cliente',
					    	hiddenName:'cdElemento',
					    	listeners : {
					    		select: function(combo, record, index){
					    			comboAseguradora.clearValue();
									comboProducto.clearValue();
									comboInstrumentos.clearValue();
					    			
					    			storeAseguradora.load({
					    				params: {
					    					cdElemento: combo.getValue()
					    				}
					    			});
									storeProducto.load({
					    				params: {
					    					cdElemento: combo.getValue()
					    				}
					    			});
					    			
									storeInstrumentos.load({
					    				params: {
					    					cdElemento: combo.getValue()
					    				}
					    			});
					    		
					    		}
					    	}
	});
	
	
	storeAseguradora = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: _ACTION_COMBO_ASEGURADORAS
        }),
        reader: new Ext.data.JsonReader({
            root: 'aseguradorasList',
            id: 'cdUniEco'
	        },[
           {name: 'cdUnieco', type: 'string',mapping:'value'},
           {name: 'dsUnieco', type: 'string',mapping:'label'}
        ]),
		remoteSort: true
    });

	comboAseguradora =new Ext.form.ComboBox({
							id:'comboAseguradoraId',
    	                    //tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{value}</div></tpl>',
							store: storeAseguradora,
						    displayField:'dsUnieco',
						    valueField: 'cdUnieco',
					    	typeAhead: true,
					    	mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'Seleccione una Aseguradora...',
					    	selectOnFocus:true,
					    	forceSelection:true,
					    	width:200,
						    fieldLabel: 'Aseguradora',
					    	hiddenName:'cdUnieco',
					    	listeners: {
					    		select: function(combo, record, index){
					    			comboProducto.clearValue();
					    			storeProducto.load({
					    				params: {
					    					cdElemento: comboClientes.getValue(),
					    					cdUnieco: combo.getValue()
					    				}
					    			});
					    		
					    		}
					    	}
					    	
	});
	
	
	storeProducto = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: _ACTION_COMBO_PRODUCTOS
        }),
        reader: new Ext.data.JsonReader({
            root: 'productosList',
            id: 'productosList'
	        },[
           {name: 'cdRamo', type: 'string',mapping:'value'},
           {name: 'dsRamo', type: 'string',mapping:'label'}
        ]),
		remoteSort: true
    });
    
    

	 comboProducto = new Ext.form.ComboBox({
							id:'comboProductoId',
    	                    //tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{value}</div></tpl>',
							store: storeProducto,
						    displayField:'dsRamo',
						    valueField: 'cdRamo',
					    	typeAhead: true,
					    	mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'Seleccione un Producto...',
					    	selectOnFocus:true,
					    	forceSelection:true,
					    	width:200,
						    fieldLabel: 'Producto',
					    	hiddenName:'cdRamo'
					    	
	});	
	
	
	storeInstrumentos = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: _ACTION_COMBO_DOCUMENTOS
        }),
        reader: new Ext.data.JsonReader({
            root: 'documentosList',
            id: 'cdTipDoc'
	        },[
           {name: 'cdFormaPago', type: 'string',mapping:'cdTipDoc'},
           {name: 'dsFormaPago', type: 'string',mapping:'dsTipDoc'}
        ]),
		remoteSort: true
    });

	comboInstrumentos = new Ext.form.ComboBox({
							id:'comboInstrumentosId',
    	                    //tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{value}</div></tpl>',
							store: storeInstrumentos,
						    displayField:'dsFormaPago',
						    valueField: 'cdFormaPago',
					    	typeAhead: true,
					    	mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'Seleccione un documento...',
					    	selectOnFocus:true,
					    	forceSelection:true,
					    	width:200,
						    fieldLabel: 'Tipo de Documento',
					    	hiddenName:'cdFormPag'
					    	
					    	
	});
	
	storeProducto.load();
    storeInstrumentos.load();
	storeAseguradora.load();
	storeClientes.load();
	
	
	var formBusqueda = new Ext.form.FormPanel({
		  id:'form',
	      el:'formBusqueda',
	      title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('',helpMap,'Tipos de documento por cliente')+'</span>',
	      bodyStyle:'background: white',
	      buttonAlign: "center",
	      labelAlign: 'right',
	      autoHeight:true,
	      frame:true,
	      width: 670,
	      items: [
	      		{
	      		layout:'form',
				border: false,
				items:[
					{
	      			//bodyStyle:'background: white',
	        		labelWidth: 100,
	              	layout: 'form',
					frame:true,
			       	baseCls: '',
			       	buttonAlign: "center",
      		        items:[
      		        	{
      		        	layout:'column',
      		        	html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
      		    		items:[
	      		    		{
						    	columnWidth:.5,
		              			layout: 'form', 
		      		        	items:[comboClientes,comboAseguradora]
							},
							{
								columnWidth:.5,
		              			layout: 'form',
		              			items:[comboProducto,comboInstrumentos]
	              			}
	              		 ]
              		}],
			        buttons:[
			        	{
					     text:getLabelFromMap('',helpMap,'Buscar'),
					     tooltip: getToolTipFromMap('',helpMap,'Busca los Intrumentos Pago por cliente seg&uacute;n los crit&eacute;rios de b&uacute;squeda'),
  						 handler: function(){
  						 	storeGrid.baseParams = storeGrid.baseParams || {};
  						 	storeGrid.baseParams['cdElemento']=comboClientes.getValue();
  						 	storeGrid.baseParams['cdUnieco']=comboAseguradora.getValue();
  						 	storeGrid.baseParams['cdRamo']=comboProducto.getValue();
							storeGrid.baseParams['cdForPag']=comboInstrumentos.getValue();
							
							storeGrid.load({
									params:{start:0, limit:itemsPerPage},
									callback: function(r, options, success){				
										if(!success){
											Ext.MessageBox.alert('Aviso','No se encontraron registros');
						                    storeGrid.removeAll();
										}
									}					
								});
  						 	
  						 }
			      		},
				      	{
					     text:getLabelFromMap('',helpMap,'Cancelar'),
					     tooltip: getToolTipFromMap('',helpMap,'Limpia la busqueda'),
						 handler: function(){formBusqueda.form.reset();}
			      		}
			      		]
			   	}]
			}]
	});
		
		
	var _cm = new Ext.grid.ColumnModel([
		{
        header: getLabelFromMap('',helpMap,'Tipo de Documento'),
		dataIndex: 'dsForPag',
		sortable: true,
		width:200},
		{
        header: getLabelFromMap('',helpMap,'Cliente'),
		dataIndex: 'dsElemento',
		sortable: true,
		width:115},
	    {
        header: getLabelFromMap('',helpMap,'Aseguradora'),
	    dataIndex: 'dsUnieco',
	    sortable: true,
	    width:170},
	    {
        header: getLabelFromMap('',helpMap,'Producto'),
	    dataIndex: 'dsRamo',
	    sortable: true,
	    width:150
	    
	    }
	 ]);
		
	var _proxy = new Ext.data.HttpProxy({url: _ACTION_FORMA_BUSQUEDA});
	
	var _reader = new Ext.data.JsonReader(
		{root: 'instrumentosClienteList',totalProperty: 'totalCount',successProperty: '@success'},
		[
       		{name: 'cdInsCte',   type: 'string',  mapping:'cdInsCte'},
       		{name: 'cdForPag',   type: 'string',  mapping:'cdForPag'},
			{name: 'dsForPag',  type: 'string',  mapping:'dsForPag'},
       		{name: 'cdElemento',  type: 'string',  mapping:'cdElemento'},
       		{name: 'dsElemento', type: 'string',  mapping:'dsElemento'},
       		{name: 'cdUnieco', type: 'string',  mapping:'cdUnieco'},
       		{name: 'dsUnieco', type: 'string',  mapping:'dsUnieco'},
       		{name: 'cdRamo', type: 'string',  mapping:'cdRamo'},
       		{name: 'dsRamo', type: 'string',  mapping:'dsRamo'}
       		
		]);
		
	storeGrid = new Ext.data.Store(
		{
		 proxy: _proxy,
		 id: 'cdInsCte',
		 reader: _reader,
		 waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...'),
		 baseParams:{
		 			cdForPag: '',
		 			cdElemento:'',
		 			cdUnieco:'',
		 			cdRamo:''
		 			}
		});

	gridBusqueda = new Ext.grid.GridPanel({
			id:'grid',
	        el:'grilla',
	        store:storeGrid,
	        buttonAlign:'center',
			border:true,
			loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
			title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
	        cm: _cm,
			buttons:[
	        		{
					text:getLabelFromMap('',helpMap,'Agregar'),
					tooltip: getToolTipFromMap('',helpMap,'Agrega un nuevo Documento por cliente'),
            		handler:function(){agregarInstrumentoPorCliente(storeGrid);}
	            	},
	            	{
					text:getLabelFromMap('',helpMap,'Eliminar'),
					tooltip: getToolTipFromMap('',helpMap,'Elimina un Documento por cliente'),
            		handler:function()
            				{
            				if(gridBusqueda.getSelectionModel().hasSelection()){
            					
            					Ext.MessageBox.confirm('Eliminar', 'Se eliminar&aacute; el registro seleccionado', function(btn) {
				                   if (btn == "yes") {
				                   		var conn = new Ext.data.Connection();
				               			conn.request({
										    url: _ACTION_BORRAR_INSCTE,
										    method: 'POST',
										    params: {
										    	cdInsCte: gridBusqueda.getSelectionModel().getSelected().get('cdInsCte') 
										    },			    		 			 
										    callback: function(options, success, response) {
												if (Ext.util.JSON.decode(response.responseText).success != false) {
													Ext.Msg.alert("Aviso", Ext.util.JSON.decode(response.responseText).mensajeRespuesta, function (){ storeGrid.load();	});
													
												}else {
													Ext.Msg.alert("Error", Ext.util.JSON.decode(response.responseText).mensajeRespuesta);
												}
											}
										});						
				             		}
				              	});
            				}
                			 else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
	                		}
	            	},
	        		{
					text:getLabelFromMap('',helpMap,'Atributos Variables'),
					tooltip: getToolTipFromMap('',helpMap,'Configura los Atributos Variables del registro seleccionado'),
                    handler:function(){
                    	
            				if(gridBusqueda.getSelectionModel().hasSelection()){
            					atributosVariablesIntrumentoPago(gridBusqueda.getSelectionModel().getSelected());
            				}
                			 else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
	                		
                    
                    }       		
	            	},
	            	{
					text:getLabelFromMap('',helpMap,'Exportar'),
					tooltip: getToolTipFromMap('',helpMap,'Exporta los Documentos por cliente'),
                    handler:function(){}
	            	}],
	    	width:670,
	    	frame:true,
			height:320,
			sm: new Ext.grid.RowSelectionModel({
				singleSelect: true
			}),
			
			stripeRows: true,
			collapsible:true,
			bbar: new Ext.PagingToolbar({
					pageSize: itemsPerPage,
					store: storeGrid,
					displayInfo: true,
					displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })
			});
	
	gridBusqueda.render();
	formBusqueda.render();
	
	
	
	
	

});