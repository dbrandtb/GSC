Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
	
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
	        url: _ACTION_OBTENER_RAZONES_CANCELACION_PRODUCTO
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
                    id:'comboProductosId',
                    store: dsProducto,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'cdRamo',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('razCancProdConfCboProd',helpMap,'Producto'),
                    tooltip:getToolTipFromMap('razCancProdConfCboProd',helpMap,'Elija producto '),
                    width: 150,
                    selectOnFocus:true,
                    forceSelection: true,
                    emptyText:'Selecione Producto...',
                    labelSeparator:''
                    });
     
     var comboRazones = new Ext.form.ComboBox({
                    tpl: '<tpl for="."><div ext:qtip="{cdRazon}.{dsRazon}" class="x-combo-list-item">{dsRazon}</div></tpl>',
                    id:'comboRazonesId',
                    store: dsRazon,
                    displayField:'dsRazon',
                    valueField:'cdRazon',
                    hiddenName: 'cdRazon',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('razCancProdConfCboRaz',helpMap,'Razón'),
                    tooltip:getToolTipFromMap('razCancProdConfCboRaz',helpMap,'Elija Razón '),
                    width: 150,
                    emptyText:'Selecione Razon...',
                    forceSelection: true,
                    selectOnFocus:true,
                    labelSeparator:''
                    });
                    
	 var comboMetodos = new Ext.form.ComboBox({
                    tpl: '<tpl for="."><div ext:qtip="{cdMetodo}.{dsMetodo}" class="x-combo-list-item">{dsMetodo}</div></tpl>',
                    id:'comboMetodoId',
                    store: dsMetodo,
                    displayField:'dsMetodo',
                    valueField:'cdMetodo',
                    hiddenName: 'cdMetodo',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('razCancProdConfCboMet',helpMap,'Método'),
                    tooltip:getToolTipFromMap('razCancProdConfCboMet',helpMap,'Elija Método '),
                    width: 150,
                    emptyText:'Selecione Metodo...',
                    forceSelection: true,
                    selectOnFocus:true,
                    labelSeparator:''
                    });
	
	var form = new Ext.FormPanel({
	        renderTo:'formCombosRCP',
	        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('104',helpMap,'Configurar Razones de Cancelaci&oacute;n por Productos')+'</span>',
	        iconCls:'logo',
	        bodyStyle:'background: white',
	        buttonAlign: "center",
	        labelAlign: 'right',
	        frame:true,   
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
	                			}],
	                			buttons:[
	                					{
									    text:getLabelFromMap('razCancProdConfBtnSave',helpMap,'Guardar'),
									    tooltip: getToolTipFromMap('razCancProdConfBtnSave',helpMap,'Guarda una nueva raz&oacute;n de cancelaci&oacute;n por producto'),
					            		handler : function() {
						                    if (form.form.isValid()) {
						                        form.form.submit( {
						                            url : _ACTION_GUARDAR_RAZON_CANCELACION_PRODUCTO,
						                            success : function(form, action) {
						                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400027', helpMap,'Guardando datos...'));
						                                },
						                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
						                            failure : function(form, action) {
						                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),getLabelFromMap('400000', helpMap,'Aviso') + action.result.errorMessages[0]);
						                                },
						                            //mensaje a mostrar mientras se guardan los datos
						                            waitMsg : getLabelFromMap('400022', helpMap,'Guardando Actualizaci&oacute;n de datos...')
						                        });
						                     } else {
						                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
						                     }
						                }
	        							},
	        							{
									    text:getLabelFromMap('razCancProdConfBtnBack',helpMap,'Regresar'),
									    tooltip: getToolTipFromMap('razCancProdConfBtnBack',helpMap,'Regresa a la pantalla anterior'),
           							    handler : function() {regresar();}                            
	        							}
	        						]
	        					}]
	        				}]	            
	        
	});

	
	function guardar()
	{
			var conn = new Ext.data.Connection();
           	conn.request({
			    url: _ACTION_GUARDAR_RAZON_CANCELACION_PRODUCTO,
			    method: 'POST',
			    params: {cdRazon: form.findById('comboRazonesId').getValue(),
			    		 cdRamo: form.findById('comboProductosId').getValue(),
			    		 cdMetodo: form.findById('comboMetodosId').getValue()},
			    success: function() {
						  Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),'La raz&oacute;n se guard&oacute; con &eacute;xito');
				    },
				 failure: function() {
				         Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),'La raz&oacute;n no se guard&oacute;');
				     }
			});
    }
    
    form.render();
});
	
