
var storePlanesProducto
//function getStorePlanesProducto(){
	    = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_COMBO_PLAN}),
            reader: new Ext.data.JsonReader(
            		{root: 'comboPlanesProducto'},
            		[
            			{name: 'codigo', type: 'string'},            			
            			{name: 'descripcion', type: 'string'}            			
            		]
            )
        });
	/*storePlanesProducto.load({
		params:{cdRamo: (Ext.getCmp('cdramoHiddenId')!=undefined)?Ext.getCmp('cdramoHiddenId').getValue():""}
	});*/
	
/*	return storePlanesProducto;
} */           
      
var storeFormaPago = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_COMBO_FORMA_PAGO}),
            reader: new Ext.data.JsonReader(
            		{root: 'comboInstrumentoPago'},
            		[
            			{name: 'cdForPag', type: 'string'},            			
            			{name: 'dsForPag', type: 'string'},
            			{name: 'cdMuestra', type: 'string'}             			
            		]
            )
        });
 storeFormaPago.load();       
        
        
var storeMoneda = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_COMBO_MONEDA}),
            reader: new Ext.data.JsonReader(
            		{root: 'comboMonedas'},
            		[
            			{name: 'codigo', type: 'string'},            			
            			{name: 'descripcion', type: 'string'}
            		]
            )
        });
storeMoneda.load();       

function getStoreComboDatosCatalogos(_idLogicTable, _idCombo){
 	var storeComboDatosCatalogos;
 	storeComboDatosCatalogos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_COMBO_DATOS_CATALOGOS_DEP}),
            reader: new Ext.data.JsonReader(
            		{root: 'comboDatosCatalogoDep'},            		
            		[
            			{name: 'codigo', type: 'string',mapping:'id'},            			
            			{name: 'descripcion', type: 'string',mapping:'texto'}            			
            		]
            )
        });
    storeComboDatosCatalogos.load({
			params: {
				cdTabla: _idLogicTable,
				valor1: (Ext.getCmp(_idCombo)!=null)?Ext.getCmp(_idCombo).getValue():""
				//valor2:_dependencyValue2
			}
		});
    return storeComboDatosCatalogos;
 }

var bloqueDinamico1 = [
					{
            		html: '<br/><span class="x-form-item" style="font-weight:bold">Orden de Seguro Nuevo</span>',
            		colspan:3
            		},    
            		{
            		layout: 'form',  
            		labelWidth: 250,
            		colspan:3,        			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '11_101_1',
           					cdSeccion:'101',
					        fieldLabel: 'N&uacute;mero de Orden de Trabajo',        
					        name: 'nmOrdenTrabajo',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},  
            		{
            		layout: 'form',  
            		labelWidth: 80, 
            		colspan:3,         			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '11_101_2',
           					cdSeccion:'101',
					        fieldLabel: 'Producto',        
					        name: 'cdRamo',
					        disabled:true,
					        width: 150
					     }
           				]
            		},  
            		{
            		layout: 'form',  
            		labelWidth: 80,
            		colspan:3,        			
           			items: [
           				{
           					xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                       id:'11_101_3',
	                       cdSeccion:'101',
	                       fieldLabel: 'Plan',
	                       store: storePlanesProducto,//getStorePlanesProducto(),
	                       displayField:'descripcion',
	                       valueField:'descripcion',
	                       hiddenName: 'cdPlan',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 150,
	                       allowBlank: false,
	                       emptyText:'...',
	                       selectOnFocus:true,
	                       forceSelection:true
					     }
           				]
            		},            		
            		{
            		layout: 'form',
            		items: [
							{
			                    fieldLabel: 'Vigencia desde',
			                    id: '11_101_4',
           						cdSeccion:'101',
			                    xtype: 'datefield',
			                    format: 'd/m/Y',
			                    name : 'vigenciaDesde',
			                    disabled:true,
			                    width:100
			                }
            			]
            		},            		
            		{
            		layout: 'form',
            		items: [
							{
			                    fieldLabel: 'Vigencia hasta',
			                    id: '11_101_5',
           						cdSeccion:'101',
			                    xtype: 'datefield',
			                    format: 'd/m/Y',
			                    name : 'vigenciaHasta',
			                    width:100,
			                    disabled:true
			                }
            			]
            		},            		
					{
						layout: 'form'						
					},
					{            		
           			layout: 'form',
           			labelWidth: 150,   
           			colspan:3,        			            		
           			items: [
           				{
           					xtype: 'textfield',
           					id: '11_101_6',
           					cdSeccion:'101',
					        fieldLabel: 'Fecha-Hora de Inicio', 	        
					        name: 'feHoIni',
					        disabled:true,
					        width: 150
					     }
           				]
            		},
            		
            		
            		{
            		layout: 'form',  
            		labelWidth: 150,
            		colspan:3,        			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '11_101_7',
           					cdSeccion:'101',
					        fieldLabel: 'Nombre de la Aseguradora',        
					        name: 'dsAseguradora',
					        disabled: true,
					        width: 150
					     }
           				]
            		},            	
            	//FIN DE BLOQUES ORDEN DE SEGURO NUEVO            	
					{
            		html: '<br/><span class="x-form-item" style="font-weight:bold">Datos Generales del asegurado</span>',
            		colspan:3
            		},
            		{            		
           			layout: 'form',
           			labelWidth: 80,           			            		
           			items: [
           				{
           					xtype: 'textfield',
           					id: '11_110_1',
           					cdSeccion:'110',
					        fieldLabel: 'Nombre del Asegurado', 	        
					        name: 'asegurado',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		labelWidth: 80,          			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '11_110_2',
           					cdSeccion:'110',
					        fieldLabel: 'Empresa',        
					        name: 'empresa',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},
            		{
						layout: 'form'						
					},
            		{
            		layout: 'form',
            		labelWidth: 80,
            		items: [
           				{
           					xtype: 'textfield',
           					id: '11_110_3',
           					cdSeccion:'110',
					        fieldLabel: 'Nombre del Empleado',        
					        name: 'empleado',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},					            		
            		{
            		layout: 'form',  
            		labelWidth: 80,          			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '11_110_4',
           					cdSeccion:'110',
					        fieldLabel: 'Direcci&oacute;n',        
					        name: 'direccion',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},
            		{
						layout: 'form'						
					},
					{
            		layout: 'form',  
            		labelWidth: 80,          			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '11_110_5',
           					cdSeccion:'110',
					        fieldLabel: 'Tel&eacute;fono',        
					        name: 'telefono',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		labelWidth: 80,          			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '11_110_6',
           					cdSeccion:'110',
					        fieldLabel: 'Fax',        
					        name: 'fax',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		}, 
            		{
						layout: 'form'						
					},
					{
            		layout: 'form',
            		colspan:3,  
            		labelWidth: 80,          			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '11_110_7',
           					cdSeccion:'110',
					        fieldLabel: 'Email',        
					        name: 'email',
					        allowBlank: false,
					        width: 250
					     }
           				]
            		},
            		//FIN DE BLOQUES DATOS DEL ASEGURADO************************************************************
            		{
            		html: '<br/><span class="x-form-item" style="font-weight:bold">Datos del Veh&iacute;culo</span>',
            		colspan:3
            		},
            		{
            		layout: 'form',  
            		labelWidth: 80, 
            		colspan:3,         			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '11_112_1',
	                        cdSeccion:'112',
					        fieldLabel: 'Descripci&oacute;n',        
					        name: 'descripcion',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},
            		{            		
           			layout: 'form',  
           			labelWidth: 80, 
           			colspan:3,   			            		
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                       id:'11_112_2',
	                       cdSeccion:'112',
	                       fieldLabel: 'Marca',
	                       store: getStoreComboDatosCatalogos('2MARCAS',""),
	                       displayField:'descripcion',
	                       valueField:'descripcion',
	                       hiddenName: 'codigo',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 150,
	                       allowBlank: false,
	                       emptyText:'...',
	                       selectOnFocus:true,
	                       forceSelection:true
	                       
					     }
           				]
            		},
            		{            		
           			layout: 'form',  
           			labelWidth: 80,
           			colspan:3,  			            		
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                       id:'11_112_3',
	                       cdSeccion:'112',
	                       fieldLabel: 'Modelo',
	                       store: getStoreComboDatosCatalogos('2MODELOS','11_112_2'),
	                       displayField:'descripcion',
	                       valueField:'descripcion',
	                       hiddenName: 'cdPrioridad',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 150,
	                       allowBlank: false,
	                       emptyText:'...',
	                       selectOnFocus:true,
	                       forceSelection:true
					     }
           				]
            		},            		
            		{
            		layout: 'form', 
            		colspan:3, 
            		labelWidth: 80,          			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '11_112_4',
	                        cdSeccion:'112',
					        fieldLabel: 'Capacidad',        
					        name: 'capacidad',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},
            		{            		
           			layout: 'form',  
           			labelWidth: 80,
           			colspan:3,  			            		
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                       id:'11_112_5',
	                       cdSeccion:'112',
	                       fieldLabel: 'Servicio',
	                       store: getStoreComboDatosCatalogos('2SERVICI',""),
	                       displayField:'descripcion',
	                       valueField:'descripcion',
	                       hiddenName: 'codigo',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 150,
	                       allowBlank: false,
	                       emptyText:'...',
	                       selectOnFocus:true,
	                       forceSelection:true
					     }
           				]
            		},
            		{            		
           			layout: 'form',
           			colspan:3,  
           			labelWidth: 80,       			            		
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                       id:'11_112_6',
	                       cdSeccion:'112',
	                       fieldLabel: 'Uso',
	                       store: getStoreComboDatosCatalogos('2USOS',""),
	                       displayField:'descripcion',
	                       valueField:'descripcion',
	                       hiddenName: 'codigo',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 150,
	                       allowBlank: false, 
	         			   //blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	                       emptyText:'Seleccione Uso...',
	                       selectOnFocus:true,
	                       forceSelection:true
					     }
           				]
            		},            		
            		{            		
           			layout: 'form',
           			colspan:3,  
           			labelWidth: 80,       			            		
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                       id:'11_112_7',
	                       cdSeccion:'112',
	                       fieldLabel: 'Tipo',
	                       store: getStoreComboDatosCatalogos('2TIPOS', ""),
	                       displayField:'descripcion',
	                       valueField:'descripcion',
	                       hiddenName: 'codigo',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 150,
	                       allowBlank: false, 
	                       emptyText:'Seleccione Uso...',
	                       selectOnFocus:true,
	                       forceSelection:true
					     }
           				]
            		},
            		{
            		layout: 'form',
            		colspan:3,  
            		labelWidth: 80,          			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '11_112_8',
	                       cdSeccion:'112',
					        fieldLabel: 'Serie',        
					        name: 'serie',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},
            		{
            		layout: 'form',
            		colspan:3,
            		labelWidth: 80,          			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '11_112_9',
	                       cdSeccion:'112',
					        fieldLabel: 'Motor',        
					        name: 'motor',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},   
            		{
            		layout: 'form',  
            		labelWidth: 80,
            		colspan:3,          			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '11_112_10',
	                       cdSeccion:'112',
					        fieldLabel: 'Placa',        
					        name: 'placa',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},
            		{
            		html: '<br/><span class="x-form-item" style="font-weight:bold">M&eacute;todo de Pago</span>',
            		colspan:3
            		},
            		{            		
           			layout: 'form',  
           			labelWidth: 100,
           			colspan:3,      			            		
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{cdForPag}. {dsForPag}" class="x-combo-list-item">{dsForPag}</div></tpl>',
	                       id:'11_115_1',
	                       cdSeccion:'115',
	                       fieldLabel: 'Forma de Pago',
	                       store: storeFormaPago,
	                       displayField:'dsForPag',
	                       valueField:'dsForPag',
	                       hiddenName: 'cdForPag',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 150,
	                       allowBlank: false, 
	                       emptyText:'...',
	                       selectOnFocus:true,
	                       forceSelection:true
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		labelWidth: 100,    
            		colspan:3,      			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '11_115_2',
	                       cdSeccion:'115',
					        fieldLabel: 'Descuento',        
					        name: 'servicio',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},
            		{            		
           			layout: 'form',  
           			labelWidth: 100,
           			colspan:3,      			            		
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                       id:'11_115_3',
	                       cdSeccion:'115',
	                       fieldLabel: 'Moneda',
	                       store: storeMoneda,
	                       displayField:'descripcion',
	                       valueField:'descripcion',
	                       hiddenName: 'moneda',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 150,
	                       allowBlank: false, 
	                       emptyText:'...',
	                       selectOnFocus:true,
	                       forceSelection:true
					     }
           				]
            		}
            		];
            		
// ##########################################################################################################################################
// ##########################################################################################################################################
// ##########################################################################################################################################
// ##########################################################################################################################################
// ##########################################################################################################################################
            		
var bloqueDinamico2 = [
					{
            		html: '<br/><span class="x-form-item" style="font-weight:bold">Orden de Cotizaci&oacute;n</span>',
            		colspan:3
            		},
            		{
            		layout: 'form',  
            		labelWidth: 150,
            		colspan:3,        			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '10_100_1',
           					cdSeccion:'100',
					        fieldLabel: 'N&uacute;mero de Orden de Trabajo',        
					        name: 'nmOrdenTrabajo',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		labelWidth: 80,
            		colspan:3,        			
           			items: [
           				{
           				   xtype: 'textfield',
	                       id:'10_100_2',
	                       cdSeccion:'100',
	                       fieldLabel: 'Producto',
	                       width: 150,
	                       name:'dsRamo',
	                       disabled:true
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		labelWidth: 80,
            		colspan:3,        			
           			items: [
           				{
           					xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                       id:'10_100_3',
	                       cdSeccion:'100',
	                       fieldLabel: 'Plan',
	                       store: storePlanesProducto,//getStorePlanesProducto(),
	                       displayField:'descripcion',
	                       valueField:'descripcion',
	                       hiddenName: 'cdPlan',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 150,
	                       allowBlank: false, 
	                       emptyText:'...',
	                       selectOnFocus:true,
	                       forceSelection:true
					     }
           				]
            		},
            		{
            		layout: 'form',
            		items: [
							{
			                    fieldLabel: 'Vigencia desde',
			                    id: '10_100_4',
           						cdSeccion:'100',
			                    xtype: 'datefield',
			                    format: 'd/m/Y',
			                    name : 'vigenciaDesde',
			                    disabled:true,
			                    width: 100
			                }
            			]
            		},
            		{
            		layout: 'form',
            		items: [
							{
			                    fieldLabel: 'Vigencia hasta',
			                    id: '10_100_5',
           						cdSeccion:'100',
			                    xtype: 'datefield',
			                    format: 'd/m/Y',
			                    name : 'vigenciaHasta',
			                    disabled:true,
			                    width: 100
			                }
            			]
            		},
            		{
						layout: 'form'						
					},    
            		{            		
           			layout: 'form',
           			colspan:3,
           			labelWidth: 120,         			            		
           			items: [
           				{
           					xtype: 'textfield',
           					id: '10_100_6',
           					cdSeccion:'100',
					        fieldLabel: 'Fecha y Hora de Inicio', 	        
					        name: 'feHoIni',
					        allowBlank: false,
					        width: 120
					     }
           				]
            		},    		
					            		            		            		
            		{
            		layout: 'form',  
            		labelWidth: 70,
            		colspan:3,        			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '10_100_7',
           					cdSeccion:'100',
					        fieldLabel: 'Aseguradora',        
					        name: 'dsAseguradora',
					        disabled: true,
					        width: 200
					     }
           				]
            		},            	
            	//FIN DE BLOQUE ORDEN DE COTIZACION
					{
            		html: '<br/><span class="x-form-item" style="font-weight:bold">Datos Generales del asegurado</span>',
            		colspan:3
            		},
            		{            		
           			layout: 'form',
           			labelWidth: 80,           			            		
           			items: [
           				{
           					xtype: 'textfield',
           					id: '10_110_1',
           					cdSeccion:'110',
					        fieldLabel: 'Nombre del Asegurado', 	        
					        name: 'asegurado',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		labelWidth: 80,          			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '10_110_2',
           					cdSeccion:'110',
					        fieldLabel: 'Empresa',        
					        name: 'empresa',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},
            		{
						layout: 'form'						
					},
            		{
            		layout: 'form',
            		labelWidth: 80,
            		items: [
           				{
           					xtype: 'textfield',
           					id: '10_110_3',
           					cdSeccion:'110',
					        fieldLabel: 'Nombre del Empleado',        
					        name: 'empleado',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},					            		
            		{
            		layout: 'form',  
            		labelWidth: 80,          			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '10_110_4',
           					cdSeccion:'110',
					        fieldLabel: 'Direcci&oacute;n',        
					        name: 'direccion',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},
            		{
						layout: 'form'						
					},
					{
            		layout: 'form',  
            		labelWidth: 80,          			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '10_110_5',
           					cdSeccion:'110',
					        fieldLabel: 'Tel&eacute;fono',        
					        name: 'telefono',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		labelWidth: 80,          			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '10_110_6',
           					cdSeccion:'110',
					        fieldLabel: 'Fax',        
					        name: 'fax',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},  
            		{
						layout: 'form'						
					},
					{
            		layout: 'form',
            		colspan:3,  
            		labelWidth: 80,          			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '10_110_7',
           					cdSeccion:'110',
					        fieldLabel: 'Email',        
					        name: 'email',
					        allowBlank: false,
					        width: 250
					     }
           				]
            		},            		            		          	
					
            		//FIN DE BLOQUES DATOS DEL ASEGURADO************************************************************
            		{
            		html: '<br/><span class="x-form-item" style="font-weight:bold">Datos del Veh&iacute;culo</span>',
            		colspan:3
            		},            		
            		{            		
           			layout: 'form',  
           			colspan:3,
           			labelWidth: 80, 			            		
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                       id:'10_112_1',
	                       cdSeccion:'112',
	                       fieldLabel: 'Marca',
	                       store: getStoreComboDatosCatalogos('2MARCAS',""),
	                       displayField:'descripcion',
	                       valueField:'descripcion',
	                       hiddenName: 'descripcion',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 150,
	                       allowBlank: false, 
	                       emptyText:'...',
	                       selectOnFocus:true,
	                       forceSelection:true
					     }
           				]
            		},
            		{
            		layout: 'form', 
            		colspan:3, 
            		labelWidth: 80,          			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '10_112_2',
           					cdSeccion:'112',
					        fieldLabel: 'Descripci&oacute;n',        
					        name: 'descripcion',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},
            		{            		
           			layout: 'form',  
           			labelWidth: 80,
           			colspan:3, 			            		
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                       id:'10_112_3',
	                       cdSeccion:'112',
	                       fieldLabel: 'Modelo',
	                       store: getStoreComboDatosCatalogos('2MODELOS', '10_112_1'),
	                       displayField:'descripcion',
	                       valueField:'descripcion',
	                       hiddenName: 'descripcion',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 150,
	                       allowBlank: false, 
	                       emptyText:'...',
	                       selectOnFocus:true,
	                       forceSelection:true
					     }
           				]
            		},            		
            		{
            		layout: 'form',  
            		labelWidth: 80,
            		colspan:3,         			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '10_112_4',
           					cdSeccion:'112',
					        fieldLabel: 'Capacidad',        
					        name: 'capacidad',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},
            		{            		
           			layout: 'form',  
           			labelWidth: 80,
           			colspan:3,			            		
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                       id:'10_112_5',
	                       cdSeccion:'112',
	                       fieldLabel: 'Servicio',
	                       store: getStoreComboDatosCatalogos('2SERVICI', ""),
	                       displayField:'descripcion',
	                       valueField:'descripcion',
	                       hiddenName: 'descripcion',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 150,
	                       allowBlank: false, 
	                       emptyText:'...',
	                       selectOnFocus:true,
	                       forceSelection:true
					     }
           				]
            		},
            		{            		
           			layout: 'form',  
           			colspan:3,
           			labelWidth: 80,   			            		
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                       id:'10_112_6',
	                       cdSeccion:'112',
	                       fieldLabel: 'Uso',
	                       store: getStoreComboDatosCatalogos('2USOS', ""),
	                       displayField:'descripcion',
	                       valueField:'descripcion',
	                       hiddenName: 'descripcion',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 150,
	                       allowBlank: false,
	                       emptyText:'Seleccione Uso...',
	                       selectOnFocus:true,
	                       forceSelection:true
					     }
           				]
            		},
            		{            		
           			layout: 'form',
           			colspan:3,  
           			labelWidth: 80,  			            		
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                       id:'10_112_7',
	                       cdSeccion:'112',
	                       fieldLabel: 'Tipo',
	                       store: getStoreComboDatosCatalogos('2TIPOS', ""),
	                       displayField:'descripcion',
	                       valueField:'descripcion',
	                       hiddenName: 'descripcion',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 150,
	                       allowBlank: false, 
	                       emptyText:'...',
	                       selectOnFocus:true,
	                       forceSelection:true
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		colspan:3,
            		labelWidth: 80,         			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '10_112_8',
           					cdSeccion:'112',
					        fieldLabel: 'Serie',        
					        name: 'serie',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},
					{
            		layout: 'form',
            		labelWidth: 80,
            		colspan:3,          			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '10_112_9',
           					cdSeccion:'112',
					        fieldLabel: 'Motor',        
					        name: 'motor',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		colspan:3,
            		labelWidth: 80,          			
           			items: [
           				{
           					xtype: 'textfield',
           					id: '10_112_10',
           					cdSeccion:'112',
					        fieldLabel: 'Placas',        
					        name: 'placa',
					        allowBlank: false,
					        width: 150
					     }
           				]
            		},            							
					//FIN DE BLOQUE DATOS DEL VEHICULO************************************************************            		
            	{
            		html: '<br/><span class="x-form-item" style="font-weight:bold">M&eacute;todo de Pago</span>',
            		colspan:3
            		},
            		{            		
           			layout: 'form',  
           			labelWidth: 100,
           			colspan:3,      			            		
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{cdForPag}. {dsForPag}" class="x-combo-list-item">{dsForPag}</div></tpl>',
	                       id:'10_115_1',
	                       cdSeccion:'115',
	                       fieldLabel: 'Forma de Pago',
	                       store: storeFormaPago,
	                       displayField:'dsForPag',
	                       valueField:'dsForPag',
	                       hiddenName: 'dsForPag',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 150,
	                       allowBlank: false, 
	                       emptyText:'...',
	                       selectOnFocus:true,
	                       forceSelection:true
					     }
           				]
            		},
            		{            		
           			layout: 'form',  
           			labelWidth: 100,  
           			colspan:3,     			            		
           			items: [
           				{
           				   xtype: 'textfield',
           				   id:'10_115_2',
	                       cdSeccion:'115',
	                       fieldLabel: 'Descuento',
	                       width: 150,
	                       allowBlank: false,
	                       name:'descuento'
					     }
           				]
            		},
            		{            		
           			layout: 'form',  
           			labelWidth: 100,
           			colspan:3,      			            		
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                       id:'10_115_3',
	                       cdSeccion:'115',
	                       fieldLabel: 'Moneda',
	                       store: storeMoneda,
	                       displayField:'descripcion',
	                       valueField:'descripcion',
	                       hiddenName: 'moneda',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 150,
	                       allowBlank: false, 
	                       emptyText:'...',
	                       selectOnFocus:true,
	                       forceSelection:true
					     }
           				]
            		}]            		