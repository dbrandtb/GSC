Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
	
	//********** CARGA Y DEFINICION DE LOS COMBOS DE LA PANTALLA
	var proxyCliente = new Ext.data.HttpProxy({url: _ACTION_OBTENER_CLIENTES_REN});
	var proxyRamo = new Ext.data.HttpProxy({url: _ACTION_OBTENER_TIPOS_RAMO_REN});
	var proxyAseguradora = new Ext.data.HttpProxy({url: _ACTION_OBTENER_ASEGURADORA_REN});
	var proxyProducto = new Ext.data.HttpProxy({url: _ACTION_OBTENER_PRODUCTOS_REN});
	
	var readerCliente = new Ext.data.JsonReader({
	     		root: 'clientesCorp',
	     		totalProperty: 'totalCount',
	     		id: 'cdElemento'
	     		},
	     		[
		    		{name: 'codElemento', type: 'string',mapping:'cdElemento'},
		    		{name: 'cdPerson', type: 'string',mapping:'cdPerson'},
		    		{name: 'desElemen', type: 'string',mapping:'dsElemen'}
	 			 ]);
		 			
	var readerRamo = new Ext.data.JsonReader({
	     		root: 'elementosComboBox',
	     		totalProperty: 'totalCount',
	     		id: 'codigo'
	     		},
	     		[
		    		{name: 'codigo', type: 'string',mapping:'codigo'},
		    		{name: 'codigoAux', type: 'string',mapping:'codigoAux'},
		    		{name: 'descripcion', type: 'string',mapping:'descripcion'}
	 			 ]);
	
	var readerAseguradora = new Ext.data.JsonReader({
	     		root: 'aseguradoraComboBox',
	     		totalProperty: 'totalCount',
	     		id: 'codigo'
	     		},
	     		[
		    		{name: 'codigo', type: 'string',mapping:'codigo'},
		    		{name: 'descripcion', type: 'string',mapping:'descripcion'}
	 			 ]);
	
	var readerProducto = new Ext.data.JsonReader({
	     		root: 'productosAseguradoraCliente',
	     		totalProperty: 'totalCount',
	     		id: 'codigo'
	     		},
	     		[
		    		{name: 'codigo', type: 'string',mapping:'codigo'},
		    		{name: 'descripcion', type: 'string',mapping:'descripcion'}
	 			 ]);
	  	
	var dsCliente = new Ext.data.Store({
	     	proxy: proxyCliente,
	     	reader: readerCliente
	 	});
	
	var dsRamo = new Ext.data.Store({
	     	proxy: proxyRamo,
	     	reader: readerRamo
	 	});
	 	
	 var dsAseguradora = new Ext.data.Store({
	     	proxy: proxyAseguradora,
	     	reader: readerAseguradora
	 	});
	 	
	 var dsProducto = new Ext.data.Store({
	     	proxy: proxyProducto,
	     	reader: readerProducto
	 	});
	
	var comboCliente = new Ext.form.ComboBox({
           tpl: '<tpl for="."><div ext:qtip="{codElemento}.{cdPerson}.{desElemen}" class="x-combo-list-item">{desElemen}</div></tpl>',
           id:'comboClienteId',
           store: dsCliente,
           displayField:'desElemen',
           valueField:'codElemento',
           hiddenName: 'cdElemento',
           typeAhead: true,
           mode: 'local',
           triggerAction: 'all',
	       fieldLabel: getLabelFromMap('comboClienteId',helpMap,'Cliente'),
           tooltip:getToolTipFromMap('comboClienteId',helpMap,'Elija cliente para renovaci&oacute;n'),
           hasHelpIcon:getHelpIconFromMap('comboClienteId',helpMap),
		   Ayuda: getHelpTextFromMap('comboClienteId',helpMap),
		   width: 200,
           allowBlank : false,
           selectOnFocus:true,
           emptyText:'Seleccione...',
           forceSelection: true,
           labelSeparator:'',
           //resizable = true,
           //handleHeight: 150,
           maxHeight:145, 
           onSelect: function (record){
  						this.setValue(record.get("codElemento"));
          				
          				comboAseguradora.setRawValue("");
          				dsAseguradora.removeAll();
          				
          				comboRamo.setRawValue('');
          				dsRamo.removeAll();
          				
          				comboProducto.setRawValue('');
          				dsProducto.removeAll();
          				
          				dsRamo.load({
          						params: {cdElemento: formSeleccionPolizas.findById('comboClienteId').getValue()}
          					});
          				
						this.collapse();	
          		        }
           });
           
           
	var comboRamo = new Ext.form.ComboBox({
           tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
           id:'comboRamoId',
           store: dsRamo,
           displayField:'descripcion',
           valueField:'codigo',
           hiddenName: 'cdTipRam',
           typeAhead: true,
           mode: 'local',
           triggerAction: 'all',
	       fieldLabel: getLabelFromMap('comboRamoId',helpMap,'Ramo'),
           tooltip:getToolTipFromMap('comboRamoId',helpMap,'Elija ramo para renovaci&oacute;n'),
           hasHelpIcon:getHelpIconFromMap('comboRamoId',helpMap),
		   Ayuda: getHelpTextFromMap('comboRamoId',helpMap),
           width: 200,
           allowBlank : false,
           selectOnFocus:true,
           emptyText:'Seleccione...',
           forceSelection: true,
           labelSeparator:'',
           onSelect: function (record){
   						
   						this.setValue(record.get("codigo"));
 						this.collapse();
 						
          				dsAseguradora.removeAll();
          				comboAseguradora.setRawValue("");
          				
          				dsAseguradora.load({
          						params: {
          						cdElemento: formSeleccionPolizas.findById('comboClienteId').getValue(),
          						cdRamo: record.get("codigoAux")
          						}
          					});	
          		     }
           });
           
	var comboAseguradora = new Ext.form.ComboBox({
           tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
           id:'comboAseguradoraId',
           store: dsAseguradora,
           displayField:'descripcion',
           valueField:'codigo',
           hiddenName: 'cdUniEco',
           typeAhead: true,
           mode: 'local',
           triggerAction: 'all',
	       fieldLabel: getLabelFromMap('comboAseguradoraId',helpMap,'Aseguradora'),
           tooltip:getToolTipFromMap('comboAseguradoraId',helpMap,'Elija aseguradora para renovaci&oacute;n'),
           hasHelpIcon:getHelpIconFromMap('comboAseguradoraId',helpMap),
		   Ayuda: getHelpTextFromMap('comboAseguradoraId',helpMap),
           width: 200,
           allowBlank : false,
           selectOnFocus:true,
           emptyText:'Seleccione...',
           forceSelection: true,
           labelSeparator:'',
           onSelect: function (record){
   						this.setValue(record.get("codigo"));
 						this.collapse();
          				dsProducto.removeAll();
          				comboProducto.setRawValue("");
          				dsProducto.load({
	   						params: {cdUniEco: formSeleccionPolizas.findById('comboAseguradoraId').getValue(),
	   								 cdElemento: formSeleccionPolizas.findById('comboClienteId').getValue()
	   								}
   						});	
          		     }
           });
           
    var comboProducto = new Ext.form.ComboBox({
           tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
           id:'comboProductoId',
           store: dsProducto,
           displayField:'descripcion',
           valueField:'codigo',
           hiddenName: 'cdRamo',
           typeAhead: true,
           mode: 'local',
           triggerAction: 'all',
	       fieldLabel: getLabelFromMap('comboProductoId',helpMap,'Producto'),
           tooltip:getToolTipFromMap('comboProductoId',helpMap,'Elija producto para renovaci&oacute;n'),
           hasHelpIcon:getHelpIconFromMap('comboProductoId',helpMap),
		   Ayuda: getHelpTextFromMap('comboProductoId',helpMap),
           width: 200,
           allowBlank : false,
           selectOnFocus:true,
           forceSelection: true,
           emptyText:'Seleccione...',
           labelSeparator:''
           });                    
	
	var rangoPolizaDesde = new Ext.form.NumberField({
	
			 id: 'rpDesde',
	         fieldLabel: getLabelFromMap('rpDesde',helpMap,'P&oacute;lizas Desde'),
			 tooltip:getToolTipFromMap('rpDesde',helpMap,'P&oacute;lizas de renovaci&oacute;n desde'),
			 hasHelpIcon:getHelpIconFromMap('rpDesde',helpMap),
		   Ayuda: getHelpTextFromMap('rpDesde',helpMap),
		    // allowBlank: false,
		     width: 80,
		     allowDecimals: false
	 	});
	 	
	 var rangoPolizaHasta = new Ext.form.NumberField({
			 id: 'rpHasta',
	         fieldLabel: getLabelFromMap('rpHasta',helpMap,'Hasta'),
			 tooltip:getToolTipFromMap('rpHasta',helpMap,'P&oacute;lizas de renovaci&oacute;n hasta'),
			 hasHelpIcon:getHelpIconFromMap('rpHasta',helpMap),
		     Ayuda: getHelpTextFromMap('rpHasta',helpMap),
		    // allowBlank: false,
		     width: 80,
		     allowDecimals: false
	 	});
	 	
	 var diasVencimiento = new Ext.form.NumberField({
	 		 id: 'diasVencim',
	         fieldLabel: getLabelFromMap('diasVencim',helpMap,'D&iacute;as antes de vencimiento'),
			 tooltip:getToolTipFromMap('diasVencim',helpMap,'D&iacute;as antes de vencimiento de p&oacute;lizas de renovaci&oacute;n '),
			 hasHelpIcon:getHelpIconFromMap('diasVencim',helpMap),
		     Ayuda: getHelpTextFromMap('diasVencim',helpMap),
		     allowBlank: false,
		     width: 100,
		     allowDecimals: false
	 	});
	 	
	var formSeleccionPolizas = new Ext.FormPanel({
			id:'formSeleccionPolizas',
			renderTo: 'formSeleccionPolizas',
	        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formSeleccionPolizas',helpMap,'Selecci&oacute;n de p&oacute;lizas para renovaci&oacute;n')+'</span>',
	        iconCls:'logo',
	        labelSeparator:'',
	        bodyStyle:'background: white',
	        buttonAlign: "center",
	        labelAlign: 'right',
	        frame:true,
	        url: _ACTION_EJECUTAR_SELECCION_POLIZAS,
	        width: 500,
	        height:250,
	        items: [
	        		comboCliente,
            		comboRamo,
            		comboAseguradora,
            		comboProducto,
            		{
           			layout: 'column',
           			items:[
           					{//1ra columna
	           					columnWidth: .5,
	           					layout: 'form',
	           					items: [rangoPolizaDesde]
           				    },
           				    {//2da columna
	           				   	columnWidth: .5,
	           					layout: 'form',
	           					items: [rangoPolizaHasta]
           				    }
           			]        			
           		   },
           		   diasVencimiento
           		   ],          		   
	       		   buttons:[
		       				{
			                text:getLabelFromMap('selPolRenBtnRun',helpMap,'Ejecutar'),
			                tooltip: getToolTipFromMap('selPolRenBtnRun',helpMap,'Ejecutar selecci&oacute;n de p&oacute;lizas para renovaci&oacute;n'),
		       				handler : function(){Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400071', helpMap,'Evento temporalmente deshabilitado'));
			                	if(formSeleccionPolizas.form.isValid()){
			                		if(formSeleccionPolizas.findById('rpDesde').getValue() > formSeleccionPolizas.findById('rpHasta').getValue()){
			                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400072', helpMap,'El rango de p&oacute;lizas no es correcto'));
			                		}
			                		else{
			                			formSeleccionPolizas.form.submit({
					                        url : _ACTION_EJECUTAR_SELECCION_POLIZAS,
					                        success : function(form, action){
					                        	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),action.result.actionMessages[0]);				                        	
					                        },
					                        failure : function(form, action){
					                            Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
					                        },
					                        waitMsg : getLabelFromMap('400073',helpMap,'Selecci&oacute;n ejecutandose...')
				                      });
			                		}				                    
				                 }
				                 else{
				                 	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'))
				                 }
		            		}
					},
					/*{
	                text:getLabelFromMap('selPolRenBtnBack',helpMap,'Regresar'),
	                tooltip: getToolTipFromMap('selPolRenBtnBack',helpMap,'Regresa a la pantalla anterior')
					 //handler : function() {window.location = _ACTION_IR_;}                            
					},*/
					{
	                text:getLabelFromMap('selPolRenBtnCons',helpMap,'Consultar'),
	                tooltip: getToolTipFromMap('selPolRenBtnCons',helpMap,'Consulta renovaci&oacute;n de p&oacute;lizas'),
					handler : function() {window.location= _ACTION_IR_CONSULTAR_POLIZAS+"?cdElemento="+formSeleccionPolizas.findById('comboClienteId').getValue()+"&cdUniEco="+formSeleccionPolizas.findById('comboAseguradoraId').getValue()+"&cdRamo="+formSeleccionPolizas.findById('comboProductoId').getValue();}                            
					//handler : function() {window.parent.frames["bodyFrame"].location.href='flujorenovacion/renovacionPolizas.action';}
					}
					]
        });
        
        dsCliente.load();
        dsRamo.load();
        formSeleccionPolizas.render()
})