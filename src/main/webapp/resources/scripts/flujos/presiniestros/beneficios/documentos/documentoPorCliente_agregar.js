function agregarInstrumentoPorCliente(storeGrid){
	
	var storeClientesAgregar = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: _ACTION_COMBO_CLIENTES,
			method: 'POST'
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
	

	var comboClientesAgregar =new Ext.form.ComboBox({
							id:'comboClientesAgregarId',
							allowBlank: false,
    	                    //tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{value}</div></tpl>',
							store: storeClientesAgregar,
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
					    	hiddenName: 'cdElemento',
					    	listeners : {
					    		select: function(combo, record, index){
					    			comboAseguradoraAgregar.clearValue();
									comboProductoAgregar.clearValue();
									comboInstrumentosAgregar.clearValue();
					    			
					    			storeAseguradoraAgregar.load({
					    				params: {
					    					cdElemento: combo.getValue(),
					    					tipoTrans: 'A'
					    				}
					    			});
									storeProductoAgregar.load({
					    				params: {
					    					cdElemento: combo.getValue()
					    				}
					    			});
					    			
									/*storeInstrumentosAgregar.load({
					    				params: {
					    					cdElemento: combo.getValue()
					    				}
					    			});*/
					    		
					    		}
					    	}
	});
	
	
	var storeAseguradoraAgregar = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: _ACTION_COMBO_ASEGURADORAS,
			method: 'POST'
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

	var comboAseguradoraAgregar =new Ext.form.ComboBox({
							id:'comboAseguradoraAgregarId',
							allowBlank: false,
    	                    //tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{value}</div></tpl>',
							store: storeAseguradoraAgregar,
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
					    	hiddenName: 'cdUnieco',
					    	listeners: {
					    		select: function(combo, record, index){
					    			comboProductoAgregar.clearValue();
					    			storeProductoAgregar.load({
					    				params: {
					    					cdElemento: comboClientesAgregar.getValue(),
					    					cdUnieco: combo.getValue()
					    				}
					    			});
					    		
					    		}
					    	}
	});
	
	
	var storeProductoAgregar = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: _ACTION_COMBO_PRODUCTOS,
			method: 'POST'
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
    
    

	 var comboProductoAgregar = new Ext.form.ComboBox({
							id:'comboProductoAgregarId',
							allowBlank: false,
    	                    //tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{value}</div></tpl>',
							store: storeProductoAgregar,
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
	
	
	var storeInstrumentosAgregar = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: _ACTION_COMBO_DOCUMENTOS,
			method: 'POST'
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

	var comboInstrumentosAgregar = new Ext.form.ComboBox({
							id:'comboInstrumentosAgregarId',
							allowBlank: false,
    	                    //tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{value}</div></tpl>',
							store: storeInstrumentosAgregar,
						    displayField:'dsFormaPago',
						    valueField: 'cdFormaPago',
					    	typeAhead: true,
					    	mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'Seleccione un Documento...',
					    	selectOnFocus:true,
					    	forceSelection:true,
					    	width:200,
						    fieldLabel: 'Tipo de Documento',
					    	hiddenName: 'cdForPag'
	});
	
	storeClientesAgregar.load({
		params :{
			tipoTrans: 'A'
		}
	});
	storeAseguradoraAgregar.load({
		params :{
			tipoTrans: 'A'
		}
	});
	storeProductoAgregar.load();
    storeInstrumentosAgregar.load();


	
	
	var formAgregar = new Ext.form.FormPanel({
		  id:'formAgregar',
		  url: _ACTION_AGREGAR_INSCTE,
	      title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('',helpMap,'Agregar un Documento por cliente')+'</span>',
	      bodyStyle:'background: white',
	      buttonAlign: "center",
	      labelAlign: 'right',
	      autoHeight:true,
	      frame:true,
	      width: 500,
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
      		        	html:'<br>',
      		    		items:[
	      		    			{
						    	columnWidth:1,
		              			layout: 'form', 
		      		        	items:[comboClientesAgregar,comboAseguradoraAgregar,comboProductoAgregar,comboInstrumentosAgregar]
								}
	              			
	              			 ]
              			}
              		]
			   	}]
			}]
	});
	
var windowAgregar = new Ext.Window({
	       	width: 400,
	       	height: 230,
	       	minWidth: 300,
	       	minHeight: 100,
	       	modal: true,
	       	layout: 'fit',
	       	plain:true,
	       	bodyStyle:'padding:5px;',
	       	buttonAlign:'center',
	       	items: formAgregar,
	        buttons: [{
               text : 'Guardar',
               handler : function(){
               		if(formAgregar.form.isValid())
                    {
                    formAgregar.form.submit( {
				                            success : function(from, action) {
				                            				Ext.MessageBox.alert('Aviso', Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta, function () {storeGrid.load();});
						                                	windowAgregar.close();
				                                },
				                            failure : function(form, action) {
				                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap, 'Error'), Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta);
				                                },
				                            waitTitle: getLabelFromMap('400021', helpMap, 'Espere...'),
				                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
				                   });
                    }
                   else
                   {
                        Ext.Msg.alert('Aviso', 'Por favor complete la informaci&oacute;n requerida');
                   }
               }
	        },{
	        text: 'Cancelar',
	        	handler: function(){
	        		windowAgregar.close();
	       	 }
	        }]
	   	});
	
	
windowAgregar.show();	
	
	
}