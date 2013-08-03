Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
	
	//Campos (criterios/filtros) de búsqueda.
	 var cliente = new Ext.form.TextField({fieldLabel: 'Cliente',name:'dsElemenGral',id:'dsElemenGral',width:150,readOnly:true,disabled:true});
	 var aseguradora = new Ext.form.TextField({fieldLabel: 'Aseguradora',name:'dsUniEcoGral',id:'dsUniEcoGral',width:150,readOnly:true,disabled:true});
	 var tipo = new Ext.form.TextField({fieldLabel: 'Tipo',name:'tipoGral',id:'tipoGral',width:150,readOnly:true,disabled:true});
	 var producto = new Ext.form.TextField({fieldLabel: 'Producto',name:'dsRamoGral',id:'dsRamoGral',width:150,readOnly:true,disabled:true});
	
	 var lyt_cliente = new Ext.Panel({layout:'form',borderStyle:false,items:[cliente]});
	 var lyt_aseguradora = new Ext.Panel({layout:'form',borderStyle:false,items:[aseguradora]});
	 var lyt_tipo = new Ext.Panel({layout:'form',borderStyle:false,items:[tipo]});
	 var lyt_producto = new Ext.Panel({layout:'form',borderStyle:false,items:[producto]});	
	
	var _readerEnc=	new Ext.data.JsonReader({
            root: 'MRangoRenovacionReporteManagerList',
            id: 'dsElemen',
            successProperty:'@success'
            },[
           {name: 'dsElemenGral', type: 'string',mapping:'dsElemen'},
           {name: 'dsUniEcoGral', type: 'string',mapping:'dsUniEco'},
           {name: 'dsRamoGral', type: 'string',mapping:'dsRamo'},
           {name: 'tipoGral', type: 'string',mapping:'tipo'}
        ]);
        
        
	 var dsEncabezadoRangoRenovacion = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_GET_ENCABEZADO_RANGO_RENOVACION
            }),
            reader: _readerEnc,
        remoteSort: true
    });
    
    
	var formBusqueda = new Ext.form.FormPanel({
		  id:'form',
	      el:'formBusqueda',
		  title: '<span style="color:black;font-size:14px;">Rangos de Renovaci&oacute;n para reporte</span>',
	      iconCls:'logo',
	      bodyStyle:'background: white',
	      buttonAlign: "center",
	      labelAlign: 'right',
	      frame:true,
	      url:_ACTION_GET_ENCABEZADO_RANGO_RENOVACION,
	      width: 500,
	      height:100,
	      store:dsEncabezadoRangoRenovacion,
	      reader:_readerEnc,
	      items: [
	      		{
	      		layout:'form',
				border: false,
				items:[
					{
	      			bodyStyle:'background: white',
	        		labelWidth: 70,
	              	layout: 'form',
					frame:true,
			       	baseCls: '',
			       	buttonAlign: "center",
      		        items:[
      		        	{
      		        	layout:'column',
      		    		items:[
	      		    		{
						    	columnWidth:.5,
		              			layout: 'form',
		      		        	items:[lyt_cliente,lyt_tipo]
							},
							{
								columnWidth:.5,
		              			layout: 'form',
		              			items:[lyt_aseguradora,lyt_producto]
	              			}
	              		 ]
              		}]
              		
			   	}]
			}]
		});
	
	 var _cm = new Ext.grid.ColumnModel([
		{header: "Rango",dataIndex: 'dsRango',sortable: true,width:190},
		{header: "Inicio",dataIndex: 'cdInicioRango',sortable: true,width:145, align: 'center'},
	    {header: "Fin",dataIndex: 'cdFinRango',sortable: true,width:145, align: 'center'},
	    {header: "cdRenova",dataIndex: 'cdRenova',hidden:true},
	    {header: "cdRango",dataIndex: 'cdRango',hidden:true}
	 ]);
		
	var _proxy = new Ext.data.HttpProxy({url: _ACTION_BUSCAR_RANGOS_RENOVACION});
	
	var _reader = new Ext.data.JsonReader(
		{root: 'MRangoRenovacionReporteManagerList',totalProperty: 'totalCount',successProperty: '@success'},
		[
       		{name: 'cdRenova',   type: 'string',  mapping:'cdRenova'},
       		{name: 'cdRango',   type: 'string',  mapping:'cdRango'},
			{name: 'dsRango',  type: 'string',  mapping:'dsRango'},
       		{name: 'cdInicioRango',  type: 'string',  mapping:'cdInicioRango'},
       		{name: 'cdFinRango', type: 'string',  mapping:'cdFinRango'}
		]);
		
	var _store = new Ext.data.Store(
		{
		 proxy: _proxy,
		 reader: _reader,
		 baseParams:{
		 			 dsElemen:cliente.getValue(),
		 			 dsUniEco:aseguradora.getValue(),
		 			 dsTipoRenova:tipo.getValue(),
		 			 dsRamo:producto.getValue()
		 			 }
		});

	var grid = new Ext.grid.GridPanel({
			id:'grid',
	        el:'gridRangosRenovacion',
	        title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
	        buttonAlign:'center',
	        store:_store,
			border:true,
	        cm: _cm,
	        loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
			buttons:[{
        			
        			text:'Agregar',
            		tooltip:'Agrega un nuevo rango de renovaci&oacute;n',
            		handler:function(){agregar();}
	            	
	            	},{
	            	
        			text:'Editar',
            		tooltip:'Edita un rango de renovaci&oacute;n',
            		handler:function()
            				{if(getSelectedRecord(grid)!=null){editar(getSelectedRecord(grid));}
                			 else{Ext.Msg.alert('Advertencia', 'Debe seleccionar un rango de renovaci&oacute;n');}
                			}
	            	
	            	},{
	            	
            		text:'Eliminar',
            		tooltip:'Elimina un rango de renovaci&oacute;n',
            		handler:function()
            				{if(getSelectedRecord(grid)!=null){borrar(getSelectedRecord(grid));}
                			 else{Ext.Msg.alert('Advertencia', 'Debe seleccionar un rango de renovaci&oacute;n');}
                			}
	            	},{

                    text:'Exportar',
                    tooltip:'Exporta los rangos de renovaci&oacute;n listados',
                    handler:function(){
                        var url = _ACTION_EXPORT + '?cdRenova=' + CDRENOVA;
                	 	showExportDialog(url);
                        }
	            	},{
            		text:'Regresar',
            		tooltip:'Regresa a la pantalla anterior',
                    handler:function(){
                    					//alert(_ACTION_IR_CONFIGURACION_RANGOS_DE_RENOVACION);
                    					 window.location.href=_ACTION_IR_CONFIGURACION_RANGOS_DE_RENOVACION+'?cdRenova='+CDRENOVA;
	                                  }
	                }],
	                
	    	width:500,
	    	frame:true,
			height:300,
			title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
			buttonAlign:'center',
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible:true,
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: _store,
					displayInfo: true,
	                displayMsg: 'Mostrando registros {0} - {1} of {2}',
	                emptyMsg: "No hay registros para visualizar"
			    })
			});

	formBusqueda.render();
	grid.render();
	
	formBusqueda.form.load({
	                      params:{cdRenova: CDRENOVA},
	                      
	                      success: function(){
	                     
	                         
	                       /*  	formBusqueda.findById("dsElemenGral").setValue(dsEncabezadoRangoRenovacion.reader.jsonData.MRangoRenovacionReporteManagerList[0].dsElemen);
	                          	formBusqueda.findById("dsUniEcoGral").setValue(dsEncabezadoRangoRenovacion.reader.jsonData.MRangoRenovacionReporteManagerList[0].dsUniEco);
	                          	formBusqueda.findById("dsRamoGral").setValue(dsEncabezadoRangoRenovacion.reader.jsonData.MRangoRenovacionReporteManagerList[0].dsRamo);
	                          	formBusqueda.findById("tipoGral").setValue(dsEncabezadoRangoRenovacion.reader.jsonData.MRangoRenovacionReporteManagerList[0].tipo);
	                          	*/
	                      reloadGrid();
	                         
	                      },
	                      failure:function(){
	                      					grid.buttons[0].setDisabled(true);
	                      					grid.buttons[1].setDisabled(true);
	                      					grid.buttons[2].setDisabled(true);
	                      					grid.buttons[4].setDisabled(true);
	                      }
					});
					
					
	 function borrar(record) {
    	if(record.get('cdRenova') != "" && record.get('cdRango') != "" )
				{
					var conn = new Ext.data.Connection();
				
					Ext.MessageBox.confirm('Eliminar', 'Se eliminará el Rango de Renovaci&oacute;n seleccionado, esto afectará el reporte de renovación ',function(btn)
					{
	           	       if (btn == "yes")
	           	        {
		           	        var _params = {cdRenova: record.get('cdRenova'),cdRango:record.get('cdRango')};
		           	        execConnection(_ACTION_BORRAR_RANGO_RENOVACION, _params, cbkBorrar);
	           	    	}
	               	})
				}else{
						Ext.Msg.alert('Aviso', 'Debe seleccionar un registro para realizar esta operaci&oacute;n');
					}
           };
           
           
    function cbkBorrar(_success, _messages) {
    	if (_success) {
    		Ext.Msg.alert('Aviso', _messages, function(){
    					reloadGrid ();
    		});
    	}else {
    		Ext.Msg.alert('Error', _messages);
    	}
    };
    
     function getSelectedRecord(){
             var m = grid.getSelections();
             if (m.length == 1 ) {
                return m[0];
             }
     };         
		
});

function reloadGrid()
	{
		var params = {
				cdRenova: CDRENOVA
			  };
		reloadComponentStore(Ext.getCmp('grid'), params, myCallback);
	};

function myCallback(_rec, _opt, _success, _store)
	{
		if (!_success){
			Ext.Msg.alert('Aviso', _store.reader.jsonData.actionErrors[0]);             
			_store.removeAll();
		}
	};	