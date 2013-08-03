function atributosVariablesIntrumentoPago(record){

var itemsPerPage = 20;
	
var storeClientesAtr;
var storeAseguradoraAtr;
var storeProductoAtr;
var storeInstrumentosAtr;
var storeGridAtr;

var comboClientesAtr = Ext.getCmp('comboClientesId').cloneConfig();
var comboAseguradoraAtr = Ext.getCmp('comboAseguradoraId').cloneConfig();
var comboProductoAtr = Ext.getCmp('comboProductoId').cloneConfig();
var comboInstrumentosAtr = Ext.getCmp('comboInstrumentosId').cloneConfig();

comboClientesAtr.setValue(record.get('cdElemento'));
comboAseguradoraAtr.setValue(record.get('cdUnieco'));
comboProductoAtr.setValue(record.get('cdRamo'));
comboInstrumentosAtr.setValue(record.get('cdForPag'));

comboClientesAtr.setDisabled(true);
comboAseguradoraAtr.setDisabled(true);
comboProductoAtr.setDisabled(true);
comboInstrumentosAtr.setDisabled(true);

var gridBusquedaAtr;

	var textAtributo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('',helpMap,'Atributo'),
        tooltip:getToolTipFromMap('',helpMap,'Atributo a buscar'), 
        maxLength: 30,
        width: 150
	});
	


	var formBusquedaAtr = new Ext.form.FormPanel({
		  id:'formBusquedaAtrId',
	      title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('',helpMap,'Atributos Variables por Instrumentos de Pago')+'</span>',
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
	        		labelWidth: 100,
	              	layout: 'form',
					frame:true,
			       	baseCls: '',
			       	buttonAlign: "center",
      		        items:[
      		        	{
      		        	layout:'column',
      		        	html:'<br>',
      		    		items:[
	      		    		{
						    	columnWidth:.5,
		              			layout: 'form', 
		      		        	items:[comboClientesAtr,comboAseguradoraAtr]
							},
							{
								columnWidth:.5,
		              			layout: 'form',
		              			items:[comboProductoAtr,comboInstrumentosAtr]
	              			},{
	              				columnWidth:1,
	              				layout:'column',
	              				html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
	              				items:[
	              					{
	              					columnWidth:.26,	
	              					layout: 'column',
	              					html:'&nbsp;'
	              					},{
	              					columnWidth:.74,	
	              					layout: 'form', 
		      		        		items:[textAtributo]
	              					}
	              					
	              				]
	              			}
	              		 ]
              		}],
			        buttons:[
			        	{
					     text:getLabelFromMap('',helpMap,'Buscar'),
					     tooltip: getToolTipFromMap('',helpMap,'Busca los atributos de este Instrumento de Pago seg&uacute;n los crit&eacute;rios de b&uacute;squeda'),
  						 handler: function(){
  						 	
  						 	storeGridAtr.baseParams = storeGridAtr.baseParams || {};
  						 	storeGridAtr.baseParams['cdInsCte']= record.get('cdInsCte');
							storeGridAtr.baseParams['dsAtribu'] = textAtributo.getValue();
							
							storeGridAtr.load({
									params:{start:0, limit:itemsPerPage},
									callback: function(r, options, success){				
										if(!success){
											Ext.MessageBox.alert('Aviso','No se encontraron registros');
						                    storeGridAtr.removeAll();
										}
									}					
								});
  						 	
  						 }
			      		},
				      	{
					     text:getLabelFromMap('',helpMap,'Cancelar'),
					     tooltip: getToolTipFromMap('',helpMap,'Limpia la busqueda'),
						 handler: function(){formBusquedaAtr.form.reset();}
			      		}
			      		]
			   	}]
			}]
	});
		
		
	var _cm = new Ext.grid.ColumnModel([
		{
        header: getLabelFromMap('',helpMap,'Atributo'),
		dataIndex: 'descripcion',
		sortable: true,
		width: 130
		},
		{
        header: getLabelFromMap('',helpMap,'Lista de Valores'),
		dataIndex: 'descripcionTabla',
		sortable: true,
		width:150
		},
	    {
        header: getLabelFromMap('',helpMap,'Formato'),
	    dataIndex: 'dsFormato',
	    sortable: true,
	    width:130
	    },
	    {
        header: getLabelFromMap('',helpMap,'M&iacute;nimo'),
	    dataIndex: 'minimo',
	    sortable: true,
	    width:100
	    },
	    {
        header: getLabelFromMap('',helpMap,'M&aacute;ximo'),
	    dataIndex: 'maximo',
	    sortable: true,
	    width:100
	    }
	 ]);
		
	var _proxy = new Ext.data.HttpProxy({url: _ACTION_FORMA_BUSQUEDA_ATR});
	
	var _reader = new Ext.data.JsonReader(
		{root: 'atributosInstrumentoPago',totalProperty: 'totalCount',successProperty: '@success'},
		[
       		{name: 'cdInsCte',   type: 'string',  mapping:'cdInsCte'},
       		{name: 'cdAtribu',  type: 'string',  mapping:'cdAtribu'},
       		{name: 'descripcion',  type: 'string',  mapping:'dsAtribu'},
       		{name: 'formato',   type: 'string',  mapping:'swformat'},	//clave del formato
			{name: 'dsFormato',  type: 'string',  mapping:'dsFormat'},		//descripcion del formato
       		{name: 'codigoTabla',  type: 'string',  mapping:'cdTabla'}, 		//clave tabla (lista de valores)
       		{name: 'descripcionTabla', type: 'string',  mapping:'dsTabla'},			//descripcion tabla
       		{name: 'minimo', type: 'string',  mapping:'nmMin'},
       		{name: 'maximo', type: 'string',  mapping:'nmMax'},
       		{name: 'swemisi', type: 'string',  mapping:'swemisi'},
       		{name: 'obligatorio', type: 'string',  mapping:'swemiobl'},
       		{name: 'modificaEmision', type: 'string',  mapping:'swemiupd'},
       		{name: 'apareceEndoso', type: 'string',  mapping:'swendoso'},
       		{name: 'obligatorioEndoso', type: 'string',  mapping:'swendobl'},
       		{name: 'modificaEndoso', type: 'string',  mapping:'swendupd'},
       		{name: 'padre', type: 'string',  mapping:'cdatribu_padre'},
       		{name: 'orden', type: 'string',  mapping:'nmorden'},
       		{name: 'agrupador', type: 'string',  mapping:'nmagrupa'},
       		{name: 'codigoExpresion', type: 'string',  mapping:'cdexpress'},
       		{name: 'condicion', type: 'string',  mapping:'cdcondicvis'},
       		{name: 'datoComplementario', type: 'string',  mapping:'swdatcom'},
       		{name: 'obligatorioComplementario', type: 'string',  mapping:'swcomobl'},
       		{name: 'modificableComplementario', type: 'string',  mapping:'swcomupd'},
       		{name: 'swlegend', type: 'string',  mapping:'swlegend'},
       		{name: 'dslegend', type: 'string',  mapping:'dslegend'}
		]);
		
	storeGridAtr = new Ext.data.Store(
		{
		 proxy: _proxy,
		 id: 'storeGridAtrId',
		 reader: _reader,
		 waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...'),
		 baseParams:{
		 			cdInsCte: record.get('cdInsCte'),
		 			dsAtribu: ''
		 			}
		});

	gridBusquedaAtr = new Ext.grid.GridPanel({
			id:'gridBusquedaAtrId',
	        store:storeGridAtr,
	        buttonAlign:'center',
			border:true,
			loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
			title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
	        cm: _cm,
			buttons:[
	        		{
					text:getLabelFromMap('',helpMap,'Agregar'),
					tooltip: getToolTipFromMap('',helpMap,'Agrega un nuevo Atributo Variable'),
	            		handler:function(){
								agregarAtributoVariable(record.get('cdInsCte'), null , storeGridAtr);
	            			}
	            	},
	        		{
					text:getLabelFromMap('',helpMap,'Editar'),
					id: 'btnRoles',
					tooltip: getToolTipFromMap('',helpMap,'Edita un Atributo Variable'),
	                    handler:function(){
	                    		if(gridBusquedaAtr.getSelectionModel().hasSelection()){
	                    			agregarAtributoVariable(record.get('cdInsCte'),gridBusquedaAtr.getSelectionModel().getSelected(), storeGridAtr);
	                    		}
	                    		else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
	                    	}       		
	            	},
	            	{
					text:getLabelFromMap('',helpMap,'Eliminar'),
					tooltip: getToolTipFromMap('',helpMap,'Elimina un atributo Variable'),
            		handler:function()
            				{
            				if(gridBusquedaAtr.getSelectionModel().hasSelection()){
            					
            					Ext.MessageBox.confirm('Eliminar', 'Se eliminar&aacute; el registro seleccionado', function(btn) {
				                   if (btn == "yes") {
				                   		var conn = new Ext.data.Connection();
				               			conn.request({
										    url: _ACTION_ELIMINA_ATRIBUTO,
										    method: 'POST',
										    params: {
										    	cdInsCte: gridBusquedaAtr.getSelectionModel().getSelected().get('cdInsCte'), 
										    	cdAtribu: gridBusquedaAtr.getSelectionModel().getSelected().get('cdAtribu')
										    },			    		 			 
										    callback: function(options, success, response) {
												if (Ext.util.JSON.decode(response.responseText).success != false) {
													Ext.Msg.alert("Aviso", Ext.util.JSON.decode(response.responseText).mensajeRespuesta , function (){ storeGridAtr.load();	});
													
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
					text:getLabelFromMap('',helpMap,'Exportar'),
					tooltip: getToolTipFromMap('',helpMap,'Exporta los Atributos Variables del Instrumento de Pago actual'),
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
					store: storeGridAtr,
					displayInfo: true,
					displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })
			});
	
			
			
	var windowAtr = new Ext.Window({
	       	width: 700,
	       	height:600,
	       	minWidth: 300,
	       	minHeight: 100,
	       	modal: true,
	       	layout: 'fit',
	       	plain:true,
	       	bodyStyle:'padding:5px;',
	       	buttonAlign:'center',
	       	items: [formBusquedaAtr,gridBusquedaAtr],
	        buttons: [
	           		{
	            	text : 'Cerrar',
	             	handler : function(){ windowAtr.close(); }
	           	}]
	   	});

	   	
	   	windowAtr.show();
	
	
	
}