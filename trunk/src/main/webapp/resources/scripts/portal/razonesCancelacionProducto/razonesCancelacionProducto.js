Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
	
	 //cantidad de descuentos a mostrar por pagina
	// var itemsPerPage=10;
	
	 var cm = new Ext.grid.ColumnModel([
		{
	        header: getLabelFromMap('razCancProdCmProd',helpMap,'Producto'),
	        tooltip: getToolTipFromMap('razCancProdCmProd',helpMap,'Producto de razones de cancelaci&oacute;n'),
		   	dataIndex: 'dsRamo',
		   	sortable: true,
		   	width: 200
		},{
	        header: getLabelFromMap('razCancProdCmRaz',helpMap,'Raz&oacute;n'),
	        tooltip: getToolTipFromMap('razCancProdCmRaz',helpMap,'Raz&oacute;n de cancelaci&oacute;n de productos'),
	       	dataIndex: 'dsRazon',
	       	sortable: true,
		   	width: 105
	     },{
	        header: getLabelFromMap('razCancProdCmMet',helpMap,'M&eacute;todo'),
	        tooltip: getToolTipFromMap('razCancProdCmMet',helpMap,'M&eacute;todo de razones de cancelaci&oacute;n de productos'),
	       	dataIndex: 'dsMetodo',
	       	sortable: true,
		   	width: 160
	     },{
	       	dataIndex: 'cdRamo',
	       	hidden: true
	     },{
	       	dataIndex: 'cdRazon',
	       	hidden: true
	     	}
	 ]);
	
	//Campos (criterios/filtros) de búsqueda.
	 var producto = new Ext.form.TextField({
	 	 id:'razCancProdTxtProd',
         fieldLabel: getLabelFromMap('razCancProdTxtProd',helpMap,'Producto'),
         tooltip:getToolTipFromMap('razCancProdTxtProd',helpMap,'Producto de razones de cancelaci&oacute;n'), 
	     name:'dsRamo',
	     width: 180
	 });
	 var razon = new Ext.form.TextField({
	 	 id:'razCancProdTxtRaz',
	     fieldLabel: getLabelFromMap('razCancProdTxtRaz',helpMap,'Raz&oacute;ón'),
         tooltip:getToolTipFromMap('razCancProdTxtRaz',helpMap,'Razón de cancelaci&oacute;n de productos'), 
	     name:'dsRazon',
	     width: 180
	 });
	 var metodo = new Ext.form.TextField({
	 	 id:'razCancProdTxtMet',
	     fieldLabel: getLabelFromMap('razCancProdTxtMet',helpMap,'M&eacute;todo'),
         tooltip:getToolTipFromMap('razCancProdTxtMet',helpMap,'M&eacute;todo de razones de cancelaci&oacute;n de productos'), 
	     name:'dsMetodo',
	     width: 180
	 });
	
	 function test(){
	 			store = new Ext.data.Store({
	    			proxy: new Ext.data.HttpProxy({
					url: _ACTION_OBTENER_RAZONES_CANCELACION_PRODUCTO
	                }),
	                reader: new Ext.data.JsonReader({
	            	root:'rcEstructuraList',
	            	totalProperty: 'totalCount',
		            successProperty : '@success'
		        },[
		        {name: 'cdRamo',   type: 'string',  mapping:'cdRamo'},
		        {name: 'dsRamo',   type: 'string',  mapping:'dsRamo'},
				{name: 'cdRazon',  type: 'string',  mapping:'cdRazon'},
		        {name: 'dsRazon',  type: 'string',  mapping:'dsRazon'},
		        {name: 'cdMetodo', type: 'string',  mapping:'cdMetodo'},
		        {name: 'dsMetodo', type: 'string',  mapping:'dsMetodo'}
				]),
	            baseParams: {dsRamo:   producto.getValue(),
	            			 dsRazon:  razon.getValue(),
	            			 dsMetodo: metodo.getValue()}
	        });
			return store;
	 	}
	
	var grid2;
	
	function createGrid(){
		grid2= new Ext.grid.GridPanel({
	        el:'gridRazonesCP',
	        store:test(),
	        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
			border:true,
			buttonAlign:'center',
	        cm: cm,
	        loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
			buttons:[
	        		{
 	                 text:getLabelFromMap('razCancProdBtnAdd',helpMap,'Agregar'),
	                 tooltip: getToolTipFromMap('razCancProdBtnAdd',helpMap,'Agrega razones de cancelaci&oacute;n de producto'),
            	 	 handler:function(){
				            			agregar();
            		}
	            	},
	            	{
 	                 text:getLabelFromMap('razCancProdBtnDel',helpMap,'Eliminar'),
	                 tooltip: getToolTipFromMap('razCancProdBtnDel',helpMap,'Elimina una raz&oacute;n de cancelaci&oacute;n de producto'),
            		 handler:function(){
                			if(getSelectedRecord()!=null){
                					borrar(getSelectedRecord());
                			}
                			
                			else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
                		}
	            	},
	            	{
 	                 text:getLabelFromMap('razCancProdBtnExp',helpMap,'Exportar'),
	                 tooltip: getToolTipFromMap('razCancProdBtnExp',helpMap,'Exporta razones de cancelaci&oacute;n de productos'),
                     handler:function(){
                        var url = _ACTION_EXPORT_RAZONES_CANCELACION_PRODUCTO + '?dsRamo=' + producto.getValue()+ '&dsRazon=' + razon.getValue()+ '&dsMetodo=' + metodo.getValue();
                	 	showExportDialog( url );
                        }
	            	}/*,
	            	{
 	                 text:getLabelFromMap('razCancProdBtnBack',helpMap,'Regresar'),
	                 tooltip: getToolTipFromMap('razCancProdBtnBack',helpMap,'Regresa a la pantalla anterior')
	                }*/
	            	],
	    	width:500,
	    	frame:true,
			height:320,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize: itemsPerPage,
					store: store,
					displayInfo: true,
	                displayMsg: 'Mostrando registros {0} - {1} of {2}',
	                emptyMsg: "No hay registros para visualizar"
			    })
			});
	
	    grid2.render()
	}
	
	function reloadGrid(){
	    var mStore = grid2.store;
	    var o = {start: 0};
	    mStore.baseParams = mStore.baseParams || {};
	    mStore.baseParams['dsRamo'] = producto.getValue();
	    mStore.baseParams['dsRazon'] = razon.getValue();
	    mStore.baseParams['dsMetodo'] = metodo.getValue();
	    mStore.reload(
	              {
	                  params:{start:0,limit:itemsPerPage},
	                  callback : function(r,options,success) {
	                      if (!success) {
	                         //Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), getLabelFromMap('400033', helpMap,'No se encontraron registros'));
	                         Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400105', helpMap,'No se encontraron datos'));
	                         mStore.removeAll();
	                      }
	                  }
	              }
	            );
	}
	
	  var incisosForm = new Ext.form.FormPanel({
	      el:'formBusquedaRC',
		  title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formBusquedaRC',helpMap,'Razones de Cancelaci&oacute;n por Producto')+'</span>',
	      iconCls:'logo',
	      bodyStyle:'background: white',
	      buttonAlign: "center",
	      labelAlign: 'right',
	      autoHeight: true,
	      frame:true,
	      url:_ACTION_OBTENER_RAZONES_CANCELACION_PRODUCTO,
	      width: 500,
	      height:170,
	      items: [{
	      		layout:'form',
			border: false,
			items:[{
	      		 bodyStyle:'background: white',
	        labelWidth: 100,
	              layout: 'form',
			frame:true,
	       	baseCls: '',
	       	buttonAlign: "center",
	      		        items: [{
	      		        	layout:'column',
	 				    	border:false,
	 				    	html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
	 				    	columnWidth: 1,
	      		    		items:[{
					    	columnWidth:.6,
	              				layout: 'form',
	                		border: false,
	      		        		items:[
	      		        				producto,
	      		        				razon,
	      		        				metodo
	       						]
							},{
							columnWidth:.4,
	              				layout: 'form'
	              				}]
	              			}],
	              			buttons:[{
				 	                 text:getLabelFromMap('razCancProdBtnSeek',helpMap,'Buscar'),
					                 tooltip: getToolTipFromMap('razCancProdBtnSeek',helpMap,'Busca un grupo de razones de cancelaci&oacute;n de productos'),
	      							 handler: function() {
			               			 if (incisosForm.form.isValid()) {
	                                             if (grid2!=null) {
	                                              reloadGrid();
	                                             } else {
	                                              createGrid();
	                                             }
	              						} else{
	              						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
									}
								}
	      							},{
				 	                 text:getLabelFromMap('razCancProdBtnCanc',helpMap,'Cancelar'),
					                 tooltip: getToolTipFromMap('razCancProdBtnCanc',helpMap,'Cancela la operacion con razones de cancelaci&oacute;n de productos'),
	      							 handler: function() {
	      								incisosForm.form.reset();
	      							}
	      						}]
	      					}]
	      				}]
	});
	
	incisosForm.render();
	createGrid();
	
	function toggleDetails(btn, pressed){
	 	var view = grid.getView();
	 	view.showPreview = pressed;
	 	view.refresh();
	 }
	var store;
	
	function getSelectedRecord(){
    var m = grid2.getSelections();
    if (m.length == 1 ) {
       return m[0];
       }
   }
	
/****** */	
	function agregar(){
	    	var dsProducto = new Ext.data.Store({
		     	proxy: new Ext.data.HttpProxy({
		         	url: _ACTION_OBTENER_PRODUCTOS
		     	}),
		     	reader: new Ext.data.JsonReader({
		     		root: 'productosComboBox',
		     		totalProperty: 'totalCount',
		     		id: 'codigo'
		     		},[
		    			{name: 'codigo', type: 'string',mapping:'codigo'},
		    			{name: 'descripcion', type: 'string',mapping:'descripcion'}
		 			  ])
		 	});
		dsProducto.load();
		
		var dsRazon = new Ext.data.Store({
		    proxy: new Ext.data.HttpProxy({
		        url: _ACTION_OBTENER_TODASRAZONES_CANCELACION_PRODUCTO
		    }),
		    reader: new Ext.data.JsonReader({
		    	root: 'comboRazones',
		    	totalProperty: 'totalCount',
		    	id: 'cdRazon'
		    	},[
		   			{name: 'cdRazon',  type: 'string', mapping:'cdRazon'},
		   			{name: 'dsRazon',  type: 'string', mapping:'dsRazon'}
				])
		});
		dsRazon.load();
	
	    var dsMetodo = new Ext.data.Store({
	        proxy: new Ext.data.HttpProxy({
	            url: _ACTION_OBTENER_METODOS_CANCELACION_PRODUCTO
	        }),
	        reader: new Ext.data.JsonReader({
	        	root: 'comboMetodos',
	        	totalProperty: 'totalCount',
	        	id: 'cdMetodo'
	        	},[
	       			{name: 'cdMetodo',   type: 'string',  mapping:'cdMetodo'},
	       			{name: 'dsMetodo',   type: 'string',  mapping:'dsMetodo'}
		   		 ])
	    });
	    dsMetodo.load();
		
		var comboProductos = new Ext.form.ComboBox({
	                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                    id:'comborazCancProdCboProd',
	                    store: dsProducto,
	                    displayField:'descripcion',
	                    valueField:'codigo',
	                    hiddenName: 'cdRamo',
	                    typeAhead: true,
	                    mode: 'local',
	                    triggerAction: 'all',
                        fieldLabel: getLabelFromMap('comborazCancProdCboProd',helpMap,'Producto'),
                        tooltip:getToolTipFromMap('comborazCancProdCboProd',helpMap,'Elija un producto '),
                        hasHelpIcon:getHelpIconFromMap('comborazCancProdCboProd',helpMap),								 
                        Ayuda: getHelpTextFromMap('comborazCancProdCboProd',helpMap),
	                    forceSelection: true,
	                    width: 150,
	                    selectOnFocus:true,
	                    allowBlank:false,
	                    emptyText:'Seleccione Producto...'
	                    //labelSeparator:''
	                    });
	     
	     var comboRazones = new Ext.form.ComboBox({
	                    tpl: '<tpl for="."><div ext:qtip="{cdRazon}.{dsRazon}" class="x-combo-list-item">{dsRazon}</div></tpl>',
	                    id:'comborazCancProdCboRaz',
	                    store: dsRazon,
	                    displayField:'dsRazon',
	                    valueField:'cdRazon',
	                    hiddenName: 'cdRazon',
	                    typeAhead: true,
	                    mode: 'local',
	                    triggerAction: 'all',
                        fieldLabel: getLabelFromMap('comborazCancProdCboRaz',helpMap,'Raz&oacute;n'),
                        tooltip:getToolTipFromMap('comborazCancProdCboRaz',helpMap,'Elija raz&oacute;n '),	 
                        hasHelpIcon:getHelpIconFromMap('comborazCancProdCboRaz',helpMap),								 
                        Ayuda: getHelpTextFromMap('comborazCancProdCboRaz',helpMap),
                        
	                    forceSelection: true,
	                    width: 150,
	                    allowBlank:false,
	                    emptyText:'Seleccione Razón...',
	                    selectOnFocus:true
	                    //labelSeparator:''
	                    });
	                    
		 var comboMetodos = new Ext.form.ComboBox({
	                    tpl: '<tpl for="."><div ext:qtip="{cdMetodo}.{dsMetodo}" class="x-combo-list-item">{dsMetodo}</div></tpl>',
	                    id:'comborazCancProdCboMet',
	                    store: dsMetodo,
	                    displayField:'dsMetodo',
	                    valueField:'cdMetodo',
	                    hiddenName: 'cdMetodo',
	                    typeAhead: true,
	                    mode: 'local',
	                    triggerAction: 'all',
                        fieldLabel: getLabelFromMap('comborazCancProdCboMet',helpMap,'M&eacute;todo'),
                        tooltip:getToolTipFromMap('comborazCancProdCboMet',helpMap,'Elija método '),
                        hasHelpIcon:getHelpIconFromMap('comborazCancProdCboMet',helpMap),								 
                        Ayuda: getHelpTextFromMap('comborazCancProdCboMet',helpMap),
                        
	                    forceSelection: true,
	                    width: 150,
	                    emptyText:'Seleccione Método...',
	                    allowBlank:false,
	                    selectOnFocus:true
	                    //labelSeparator:''
	                    });
		
		var formPanel = new Ext.FormPanel({
		        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formPanelConfId',helpMap,'Configurar Razones de Cancelaci&oacute;n por Productos')+'</span>',
		        iconCls:'logo',
		        bodyStyle:'background: white',
		        buttonAlign: "center",
		        labelAlign: 'right',
		        frame:true,   
		        renderTo: Ext.get('formulario'),
		        url: _ACTION_GUARDAR_RAZON_CANCELACION_PRODUCTO,
		        width: 500,
		        height:200,
		        items: [{
		        		layout:'form',
						border: false,
						items:[{
						bodyStyle:'background: white',
				        labelWidth: 100,
		                layout: 'form',
						frame:true,
				       	baseCls: '',
				       	buttonAlign: "center",
		        		        items: [{
		        		        	layout:'column',
				 				    border:false,
				 				    columnWidth: 1,
		        		    		items:[{
								    	columnWidth:.6,
		                				layout: 'form',
				                		border: false,
		        		        		items:[       		        				
		        		        				 comboProductos,
		        		        				 comboRazones,
		        		        				 comboMetodos       		        				
		                                       ]
										},
										{
										columnWidth:.4,
		                				layout: 'form'
		                				}]
		                			}]
		        					}]
		        				}]	            
		        
				});
	
		
		 var window = new Ext.Window({
        	width: 500,
        	height:200,
        	layout: 'fit',
        	plain:true,
        	modal:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {
                text:getLabelFromMap('razCancProdBtnSave',helpMap,'Guardar'),
                tooltip: getToolTipFromMap('razCancProdBtnSave',helpMap,'Guarda razones de cancelaci&oacute;n de productos'),

                disabled : false,

                handler : function() {

                    if (formPanel.form.isValid()) {

                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_RAZON_CANCELACION_PRODUCTO,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                               // Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400027', helpMap,'Guardando datos...'), function(){reloadGrid();});
                            	 Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),action.result.errorMessages[0]);		//getLabelFromMap('400013', helpMap,'Complete la informacion requerida'),
                            }

                           
						//	 waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                        });

                    } else {

                        Ext.Msg.alert('Aviso', 'Por favor complete la informaci&oacute;n requerida!');

                    }

                }

            }, {
                text:getLabelFromMap('razCancProdBtnCanc',helpMap,'Cancelar'),
                tooltip: getToolTipFromMap('razCancProdBtnCanc',helpMap,'Cancela operaci&oacute;n con razones de cancelaci&oacute;n de productos'),

                handler : function() {
                    window.close();
                }

            }]

    	});

    	window.show();
	}
/****** */	    
	function borrar(record) {
			   Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn){	
                   if (btn == "yes") {
                   		var conn = new Ext.data.Connection();
               			conn.request({
						    url: _ACTION_BORRAR_RAZON_CANCELACION_PRODUCTO,
						    method: 'POST',
						    params: {"cdRazon": record.get('cdRazon'),
			    		 			 "cdRamo": record.get('cdRamo'),
			    		 			 "cdMetodo": record.get('cdMetodo')
			    		 			 },			    		 			 
						    callback: function(options, success, response) {
								if (Ext.util.JSON.decode(response.responseText).success != false) {
									Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0]);
									reloadGrid(); 	
									
								}else {
									Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),Ext.util.JSON.decode(response.responseText).errorMessages[0]);
								}
							}
						});						
             		}
              });
       };
	
});