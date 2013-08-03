<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->
<jsp:include page="/resources/scripts/jsp/utilities/definicion/clausula/clausulaIE.jsp" flush="true" />
<jsp:include page="/resources/scripts/jsp/utilities/definicion/clausula/clausulaB.jsp" flush="true" />
<jsp:include page="/resources/scripts/jsp/utilities/definicion/periodo/periodoIE.jsp" flush="true" />
<jsp:include page="/resources/scripts/jsp/utilities/definicion/periodo/periodoB.jsp" flush="true" />

<script type="text/javascript">
   Ext.onReady(function(){

    Ext.QuickTips.init();
	Ext.QuickTips.enable();
    // turn on validation errors beside the field globally
    Ext.form.Field.prototype.msgTarget = 'side';

    var bd = Ext.getBody();
    var recordd; 
    var recordClausulas;
      var clave= new Ext.form.TextField({
                    fieldLabel: 'Clave',
                    name: 'clave',
                    anchor:'40%'   
   });
   var descripcion= new Ext.form.TextField({
                    fieldLabel: 'Descripcion',
                    name: 'clausulaDescripcion',
                    anchor:'98%'   
   });
   var htmledit= new Ext.form.HtmlEditor({
    		id:'bio',
            fieldLabel:'Texto',
            name:'linea',
            height:150,
            anchor:'98%'
   });
    var formPanel = new Ext.FormPanel({
    	id:'another-id',
        labelAlign: 'top',
        frame:true,
        reader: new Ext.data.JsonReader({			//el edit aun no esta probado
            root: ''
        }, ['clave','descripcion', 'linea']
        ),
        
        bodyStyle:'padding:5px 5px 0',
        width: 600,
        url:'definicion/AgregarClausula.action',
        items: [
        	clave,
        	descripcion,
        	htmledit
        ]
    });
 	if(selectedId!=null){
 	
		 Ext.getCmp('another-id').getForm().loadRecord(selectedId);  
    }
    // define window and show it in desktop
    var windowClausulas = new Ext.Window({
        title: 'Alta al catalogo de Clausulas',
        width: 600,
        height:400,
        minWidth: 300,
        minHeight: 150,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
		modal:true,
        buttons: [{
            text: 'Guardar', 
            handler: function() {
                // check form value 
                if (formPanel.form.isValid()) {
	 		        formPanel.form.submit({			      
			            waitTitle:'Espere',
			            waitMsg:'Salvando datos...',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('Error','Clausula no agregada');
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('Status', 'Clausula agregada');
						    windowClausulas.close();
						    dataStore.load({params:{start:0, limit:10}});
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Error', 'Favor de llenar datos requeridos');
				}             
	        }
        },{
            text: 'Cancelar',
            handler: function(){windowClausulas.close();}
        }]
    });
   
    function eliminarPeriodo(btn){
        if(btn=='yes'){
    		Ext.MessageBox.alert('Status', 'Periodo eliminado');
        	//DeleteDemouser(store);
        }
    };
	
	function modificarPeriodo(btn){
        if(btn=='yes'){
        	alert("El santo contra las momias de guanajuato"+recordd);
    		Ext.MessageBox.alert('alert', 'Changes saved successfully.');
    		Ext.Msg.alert('alert', 'El santo contra las brujas de tolantongo'+recordd);
        	PeriodoIE(store, recordd);
        }
    };
    function modificarClausula(){
        
        	ClausulaIE(ds, recordClausulas);
        
    };
    
    function showResultText(btn, text){
        Ext.example.msg('Button Click', 'You clicked the {0} button and entered the text "{1}".', btn, text);
    };
    
 //segunda tabla 
  var store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'definicion/PeriodoLista.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'periodos',
            id: 'periodos'
	        },[
           {name: 'periodo', type: 'string',mapping:'codigoPeriodo'},
           {name: 'inicio', type: 'string',mapping:'inicio'},
           {name: 'fin', type: 'string',mapping:'fin'}    
        ]),
		remoteSort: true
    });
       store.setDefaultSort('periodos', 'desc'); 
  
    
    var columnModel = new Ext.grid.ColumnModel([
        {id:'periodo',header: "Periodo", width: 175, sortable: true,dataIndex: 'periodo'},
        {header: "Fecha Inicial", width: 185, sortable: true, dataIndex: 'inicio'},
        {header: "Fecha Final", width: 185, sortable: true, dataIndex: 'fin'}
    ]);
//   Define the Grid data and create the Grid
	var ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'definicion/ClausulaLista.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'clausulas',
            id: 'clausulas'
	        },[
           {name: 'clave', type: 'string',mapping:'codigoClausula'},
           {name: 'descripcion', type: 'string',mapping:'descripcionClausula'}    
        ]),
		remoteSort: true
    });
       ds.setDefaultSort('clausulas', 'desc'); 

  
    // the DefaultColumnModel expects this blob to define columns. It can be extended to provide
    // custom or reusable ColumnModels
    var colModel = new Ext.grid.ColumnModel([
        {id:'clave',header: "Clave", width: 75, sortable: true,dataIndex: 'clave'},
        {header: "Descripcion", width: 260, sortable: true,  locked:false, dataIndex: 'descripcion'}
    ]);
    
    var dsComboCatalogo = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'definicion/CatalogoClausulas.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'clausulasCatalogo',
            id: 'comboCatalogo'
	        },[
           {name: 'key', type: 'string',mapping:'codigoClausula'},
           {name: 'value', type: 'string',mapping:'descripcionClausula'}
                
        ]),
		remoteSort: true
    });
    dsComboCatalogo.load();
    
    //combo declaracion
     
	//Codigo de los Combos
	    // simple array store
    var storeC = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'definicion/TestCombos.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'combos',
            id: 'combo'
	        },[
           {name: 'clave', type: 'string',mapping:'key'},
           {name: 'descripcion', type: 'string',mapping:'value'},
           {name: 'nick', type: 'string',mapping:'nick'}    
        ]),
		remoteSort: true
    });
       storeC.setDefaultSort('combo', 'desc'); 

//ds del los combos
   	var dsCatalogoTipoProducto = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'definicion/CatalogoTipoProducto.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'catalogoTipoProducto',
            id: 'comboPadre'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}       
        ]),
		remoteSort: true
    });
  dsCatalogoTipoProducto.load(); 
   
   	var dsCatalogoTipoRamo = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'definicion/CatalogoTipoRamo.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'catalogoTipoRamo',
            id: 'catalogoTipoRamo'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}           
        ]),
		remoteSort: true
    });    
    dsCatalogoTipoRamo.load(); 
       
    var dsCatalogoTipoPoliza = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'definicion/CatalogoTipoPoliza.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'catalogoTipoPoliza',
            id: 'catalogoTipoPoliza'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}      
        ]),
		remoteSort: true
    });
    
    dsCatalogoTipoPoliza.load(); 
    
    var dsCatalogoTipoSeguro = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'definicion/CatalogoTipoSeguro.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'catalogoTipoSeguro',
            id: 'catalogoTipoSeguro'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}      
        ]),
		remoteSort: true
    });
    dsCatalogoTipoSeguro.load();
    //definicion de catalogos
    
		 	var claveCatalogoClausula = new Ext.form.ComboBox({
    						tpl: '<tpl for="."><div ext:qtip="{value}. {key}" class="x-combo-list-item">{value}</div></tpl>',
					 		store: dsComboCatalogo,
							displayField:'value',
	    					//valueField: 'key',	    				
							typeAhead: true,
					    	mode: 'local',
		    				triggerAction: 'all',
							emptyText:'Seleccione una clausula',
    						selectOnFocus:true,
   							fieldLabel: "Clave",
			    			name:"claveCatalogoClausulas",
    						width:200,
	    					height:20				    			
			});  
    		var codigoTipoParametro =new Ext.form.ComboBox({
    	                    tpl: '<tpl for="."><div ext:qtip="{value}. {key}" class="x-combo-list-item">{value}</div></tpl>',
					    	store: dsCatalogoTipoProducto,
						    displayField:'value',
						    valueField: 'key',
					    	typeAhead: true,
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'Seleccione un tipo de producto',
					    	selectOnFocus:true,
						    fieldLabel: "Tipo de Producto",
					    	name:"valorTipoProducto",	
							allowBlank:false,							
					    	focusAndSelect : function(record) {
									var index = typeof record === 'number' ? record : this.store.indexOf(record);
									
									Ext.getCmp('clave-tipo-producto').setValue(record.get('key'));
									this.select(index, this.isExpanded());
									this.onSelect(this.store.getAt(record), index, this.isExpanded());
							},
					    	onSelect : function(record, index, skipCollapse){
									if(this.fireEvent('beforeselect', this, record, index) !== false){
											this.setValue(record.data[this.valueField || this.displayField]);
											if( !skipCollapse ) {
												this.collapse();
											}
											this.lastSelectedIndex = index + 1;
											this.fireEvent('select', this, record, index);
									}									
							}				    	
			});
			var codigoTipoRamo =new Ext.form.ComboBox({
    	                    tpl: '<tpl for="."><div ext:qtip="{value}. {key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsCatalogoTipoRamo,
						    displayField:'value',
						    valueField: 'key',
					    	typeAhead: true,
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'Seleccione un tipo de ramo',
					    	selectOnFocus:true,
						    fieldLabel: "Tipo de Ramo",
					    	name:"valorTipoRamo",
							allowBlank:false,
							focusAndSelect : function(record) {
									var index = typeof record === 'number' ? record : this.store.indexOf(record);
									
									Ext.getCmp('clave-tipo-ramo').setValue(record.get('key'));
									this.select(index, this.isExpanded());
									this.onSelect(this.store.getAt(record), index, this.isExpanded());
							},
					    	onSelect : function(record, index, skipCollapse){
									if(this.fireEvent('beforeselect', this, record, index) !== false){
											this.setValue(record.data[this.valueField || this.displayField]);
											if( !skipCollapse ) {
												this.collapse();
											}
											this.lastSelectedIndex = index + 1;
											this.fireEvent('select', this, record, index);
									}									
							}		
			});
			var codigoTipoPoliza =new Ext.form.ComboBox({
    	                    tpl: '<tpl for="."><div ext:qtip="{value}. {key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsCatalogoTipoPoliza,
						    displayField:'value',
						    valueField: 'key',
					    	typeAhead: true,
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'Seleccione un tipo de poliza',
					    	selectOnFocus:true,
						    fieldLabel: "Tipo de Poliza",
					    	name:"valorTipoPoliza",
							allowBlank:false,
							focusAndSelect : function(record) {
									var index = typeof record === 'number' ? record : this.store.indexOf(record);
									
									Ext.getCmp('clave-tipo-poliza').setValue(record.get('key'));
									this.select(index, this.isExpanded());
									this.onSelect(this.store.getAt(record), index, this.isExpanded());
							},
					    	onSelect : function(record, index, skipCollapse){
									if(this.fireEvent('beforeselect', this, record, index) !== false){
											this.setValue(record.data[this.valueField || this.displayField]);
											if( !skipCollapse ) {
												this.collapse();
											}
											this.lastSelectedIndex = index + 1;
											this.fireEvent('select', this, record, index);
									}									
							}		
			});
			var codigoTipoSeguro =new Ext.form.ComboBox({
    	                    tpl: '<tpl for="."><div ext:qtip="{value}. {key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsCatalogoTipoSeguro,
						    displayField:'value',
						    valueField: 'key',
					    	typeAhead: true,
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'Seleccione un tipo de seguro',
					    	selectOnFocus:true,
						    fieldLabel: "Tipo de Seguro",
					    	name:"valorTipoSeguro",
							allowBlank:false,
							focusAndSelect : function(record) {
									var index = typeof record === 'number' ? record : this.store.indexOf(record);
									
									Ext.getCmp('clave-tipo-seguro').setValue(record.get('key'));
									this.select(index, this.isExpanded());
									this.onSelect(this.store.getAt(record), index, this.isExpanded());
							},
					    	onSelect : function(record, index, skipCollapse){
									if(this.fireEvent('beforeselect', this, record, index) !== false){
											this.setValue(record.data[this.valueField || this.displayField]);
											if( !skipCollapse ) {
												this.collapse();
											}
											this.lastSelectedIndex = index + 1;
											this.fireEvent('select', this, record, index);
									}									
							}		
			});

    //primera tabla
    		
   		//definicion datos principales de producto 		 
    		 var codigoRamo= new Ext.form.NumberField({
    		 				fieldLabel: 'Producto',
							name: 'codigoRamo',
							allowDecimals : false,
			                allowNegative : false,                
            			    labelSeparator:'',
            			    maxValue:999,
							width:50,
							allowBlank:false
    		 });
    		 var descripcionRamo= new Ext.form.TextField({
    		 				hideLabel: true,												                    
							fieldLabel: 'Descripcion ramo',
							name: 'descripcionRamo',
							labelSeparator:'',
							width:200,
							allowBlank:false
    		 });
    		 var descripcionP= new Ext.form.TextArea({
	                        name: "descripcion",
	                        fieldLabel: "Descripcion",
	                        width: 250,
	                        labelSeparator:'',
	                        allowBlank: false
	        });

    		//definicion attributos de producto
    		 var tipoDagnos= new Ext.form.Radio({		 				
    		 				name:'switchTipo',
    		 				boxLabel:'Daños',
    		 				hideLabel:true,
    		 				hideParent:true
    		 });
    		 var tipoOtros= new Ext.form.Radio({		 				
    		 				name:'switchTipo',
    		 				boxLabel:'Otros',
    		 				hideLabel:true,
    		 				hideParent:true
    		 });
    		 /**var switchSuscripcion= new Ext.form.Checkbox({
            				name:'switchSuscripcion',
            				boxLabel: 'Suscripción',
            				hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            }
            });
           
            var switchClausulasNoTipificadas= new Ext.form.Checkbox({
            				name:'switchClausulasNoTipificadas',
            				boxLabel: 'Clausulas no tipificadas',
            				hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            }
            });
            
             var switchPrimasPeriodicas= new Ext.form.Checkbox({
            				name:'switchPrimasPeriodicas',
            				boxLabel: 'Primas Periódicas',
            				hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            }
            });**/
            var switchPermisoPagosOtrasMonedas= new Ext.form.Checkbox({
            				name:'switchPermisoPagosOtrasMonedas',
            				boxLabel: 'Permiso Pagos otras monedas',
            				hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            }
            });
            /**var switchReaseguro= new Ext.form.Checkbox({
            				name:'switchReaseguro',
            				boxLabel: 'Reaseguro',
            				hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            }
            });
            var switchSiniestros= new Ext.form.Checkbox({
            				name:'switchSiniestros',
            				boxLabel: 'Desactivar Incisos con Siniestro',
            				hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            }			
            });
            var switchTarifa= new Ext.form.Checkbox({
            				name:'switchTarifa',
            				boxLabel: 'Usará Fecha de Referencia',
            				hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            }			
            });**/
            var switchReinstalacionAutomatica= new Ext.form.Checkbox({
            				name:'switchReinstalacionAutomatica',
            				boxLabel: 'Reinstalación Automática',
            				hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            }			
            });
            /**var switchPrimasUnicas= new Ext.form.Checkbox({
            				name:'switchPrimasUnicas',
            				boxLabel: 'Primas Únicas',
            				hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            }			
            });**/
            var switchDistintasPolizasPorAsegurado= new Ext.form.Checkbox({
            				name:'switchDistintasPolizasPorAsegurado',
            				boxLabel: 'Distintas Pólizas para un Asegurado',
            				hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            }			
            });/**
            var switchPolizasDeclarativas= new Ext.form.Checkbox({
            				name:'switchPolizasDeclarativas',
            				boxLabel: 'Permite Pólizas Declarativas',
            				hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            }			
            });
            
            var switchPreavisoCartera= new Ext.form.Checkbox({
            				name:'switchPreavisoCartera',
            				boxLabel: 'Preaviso de Cartera',
            				hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            }			
            });
			**/
			var renovable = new Ext.form.Checkbox({
							name:'renovable',
							boxLabel: 'Renovable',
							hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            }			
			});
			
			var temporal = new Ext.form.Checkbox({
							name:'temporal',
	        				boxLabel: 'Temporal',
            				hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            }			
			});
			
			
			var vidaEntera = new Ext.form.Checkbox({
							name:'vidaEntera',
	           				boxLabel: 'Vida Entera',
            				hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            }		
			});
               /**  
            var switchTarifaDireccionalTotal = new Ext.form.Checkbox({
							name:'switchTarifaDireccionalTotal',
	        				boxLabel: 'Tarifa Direccional Total',
            				hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            }			
			});
			**/
			var switchSubincisos = new Ext.form.Checkbox({
							name:'switchSubincisos',
	        				boxLabel: 'Subincisos',
            				hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            }			
			});
			/**
			var cantidadDiasReclamacion= new Ext.form.NumberField({                
	                		name:'cantidadDiasReclamacion',
    	    		        fieldLabel: 'Cantidad Días de Reclamación',
			                allowDecimals : false,
            	    		allowNegative : false,                
        			        labelSeparator:'',
        			        maxValue:500,
		            	    width:50                          
            });**/
			var mesesBeneficios= new Ext.form.NumberField({
            				name:'mesesBeneficios',
         				    fieldLabel: 'Antigüedad Mínima (Meses)',
         				    hideParent:true,
			                allowDecimals : false,
			                allowNegative : false,                
            			    labelSeparator:'',
            			    maxValue:500,
			                width:50   		
            }); 
             var tipoCalculoPolizasTemporales= new Ext.form.NumberField({
            				name:'tipoCalculoPolizasTemporales',
         				    fieldLabel: 'Tipo Cálculo Pólizas Temporales',
         				    hideParent:true,
			                allowDecimals : false,
			                allowNegative : false,                
            			    labelSeparator:'',
    		 				labelAlign:'left',
    		 				maxValue:500,
			                width:50   		
            }); 
            
             var switchRehabilitacion= new Ext.form.Checkbox({
            				name:'switchRehabilitacion',
            				boxLabel: 'Rehabilitación',
            				hideLabel:true,
            				onClick: function(){			            		
            					if(this.getValue()){	
            						 this.setRawValue("S");				            	
            						 mesesBeneficios.enable();
				            	}else{      
				            		this.setRawValue("N");      						
				            		 mesesBeneficios.disable();            						
				            	}
			            	}			
            });
 
             mesesBeneficios.disable();
            
        
function reload(){
	ds.load({params:{start:0, limit:10}});
}     
var selectedId;
/*
 *	Here is where we create the Form
 */

   
    var tab2 = new Ext.FormPanel({
        labelAlign: 'right',
        title: 'Definicion de Productos',
        bodyStyle:'padding:5px',
        url:"definicion/InsertarProducto.action",
        border:true,
        width: 800,
        height:510,
        autoScroll:true,
        items: [{
						            layout:'column',
						            border:false,
						            bodyStyle:'padding:5px 5px 0',
						            items:[{
						            		columnWidth:.5,
							                layout:'form',							                
									        border:false,
									        bodyBorder:true,
								    	    labelAlign:"left",
								        	width: 310,
									        items: [{
							  			            layout:'column',
										            border:false,
										            items:[{
											                columnWidth:.4,
											                layout: 'form',
											                border:false,
											                items: [codigoRamo]
											            },{
										               		columnWidth:.6,
											                layout: 'form',
											                border:false,
											                items: [descripcionRamo]
											            }]
										        },descripcionP]
							                              
								         },{
							                columnWidth:.5,
							                layout:'form',							                
									        border:false,
									        bodyBorder:true,
								    	    labelAlign:"left",
								        	width: 310,
									        items: [{
							  			            layout:'column',
										            border:false,
										            items:[{
										                layout:'form',
										                defaultType: 'textfield',
														border: false,
										                items: [codigoTipoParametro,
   										                        codigoTipoRamo,
   										                        codigoTipoPoliza,
   										                        codigoTipoSeguro
											                ]
           											}]
           								}]
           							}]
							   },{
            xtype:'tabpanel',
            plain:true,
            //deferredRender:false,
            
            activeTab: 0,
            height:320,
            defaults:{bodyStyle:'padding:10px'},
            items:[{
       				title:'Definicion',
			        layout:'form',					       
			        border:false,					        
			        items: [{
				            layout:'column',
				            border:false,
				            items:[{						            		
					                columnWidth:.3,
					                layout: 'form',
					                labelAlign : "right" ,
					                border:false,							               
					                items: [//switchSuscripcion,
					                		//switchClausulasNoTipificadas,							                									        
									        switchRehabilitacion,
									        mesesBeneficios
									              		 
              		 						]
					            },{
					                columnWidth:.4,
					                layout: 'form',
					        		labelAlign : "left" ,
					                border:false,
					                items: [
					                		//switchPrimasPeriodicas,
					                		switchPermisoPagosOtrasMonedas,					                		
					                		//switchReaseguro,
					                		//switchSiniestros,
					                		//switchTarifa,
					                		tipoCalculoPolizasTemporales,
					                		switchReinstalacionAutomatica,
			        		        		//switchPrimasUnicas,
			        		        		switchDistintasPolizasPorAsegurado//,
					                		//switchPolizasDeclarativas
				                		 ]
					            },{
					                columnWidth:.3,
					                layout: 'form',
					                border:false,					               
					                labelAlign : "left" ,
					                items: [	
					                		
					                		//switchPreavisoCartera,						                		
			        		        		{
					  			            layout:'column',
								            border:false,
								            labelAlign : "left" ,
								            items:[{
									                columnWidth:.5,
									                layout: 'form',
									                border:false,											               
									                items: [tipoDagnos]
									            },{
								               		columnWidth:.5,
									                layout: 'form',
									                border:false,										                
									                items: [tipoOtros]
									            }]
								        	},{
								                layout:'form',								                
												border: true,
												title:'Temporalidad de las Pólizas',
								                items: [
						        	                    renovable,
								                        temporal,
											            vidaEntera
									                ]
   											}//,switchTarifaDireccionalTotal
   											/**,{
								                layout:'form',
								                defaultType: 'textfield',
												border: false,
												labelAlign : "top" ,
												items: [cantidadDiasReclamacion]
   											}**/,switchSubincisos]
				            	}]
       
		 	  	     	}]
					        
		    },{
		        layout: 'form',
		        title: 'Clasulas',
		        labelAlign: 'top',
	    	    frame:false,        
	    	    bodyStyle:'padding:5px 5px 0',
    	    	width: 780,
	        	border:false,
    		    items: [
    		    		claveCatalogoClausula,
    		    		{
    		    			xtype:'button',
    		    			text:'Agregar al catalogo',
    		    			handler: function(){
    		    				ClausulaIE(ds,selectedId); 
    		    			}
    		    		},{
		        		layout: 'form',
		        		border:false,
		        		height:10
		        		},{
	    		        xtype: 'grid',
	    		        id:'grid-clausulas',
	            		ds: ds,
			            cm: colModel,
	    		        autoExpandColumn: 'clave',
	            		height: 200,
			            width:760,                    
	            		border: true,
	            		sm: new Ext.grid.RowSelectionModel({
							singleSelect: true,
							listeners: {							
					        	rowselect: function(grid2, row, rec) {	
					        		//selectedId=row.getSelected();                    		                    	                        	                     	                        
					        		//alert(selectedId);
					        		var sel = Ext.getCmp('grid-clausulas').getSelectionModel().getSelected();
							        var selIndex = ds.indexOf(sel);
							        alert(selIndex);
					            	recordClausulas=rec;
				            	    Ext.getCmp('editar-clausula').on('click',function(){
				                        windowClausulas.show();
                                    	Ext.getCmp('another-id').getForm().loadRecord(rec);                                                                            
                                	});
                                	Ext.getCmp('eliminar-clausula').on('click',function(){
										DeleteDemouser(ds,selIndex,sel);
                                                                          
                                	});
	    					   	 }
	            		   	}
						}),
			            tbar:[{
 					           text:'Agregar',
    	    				   tooltip:'Agregar un nuevo periodo de valides',
					           iconCls:'add',
					           handler:function asociarClausula(){
				  					 tab2.form.load({url:'definicion/AsociarClausula.action?claveCatalogoClausulas='+claveCatalogoClausula.getValue(), 
								     waitMsg:'Loading'}); 
								     ds.reload();
								     dsComboCatalogo.load();
							   }
		        			}, '-', {
		        				id:'editar-clausula',
	    		    		    text:'Editar',
    	        				tooltip:'Editar el periodo seleccionado',
		        	    		iconCls:'option',
				        	    handler:function(){
				        	    	    //Ext.MessageBox.confirm('Confirm', 'Esta seguro que desea modificar el registro?', modificarPeriodo);
				        	    	    //modificarClausula();
    							}
		    			    },'-',{
		    			    	id:'eliminar-clausula',
				    	        text:'Eliminar',
        				    	tooltip:'Eliminar el periodo seleccionado',
            					iconCls:'remove'
            				
					    }],
	    		        bbar: new Ext.PagingToolbar({
		        		   store:ds,
		        	    	items:[
				                '-', {
	        			        pressed: true,    	        	    
		        	        	text: 'Actualizar',
	    		    	        cls: 'x-btn-text-icon details',
    	        			    toggleHandler: function(){}
        		  	  		}]	
		        		})		  
        		}]
			},{
        		layout: 'form',
		        frame: false,
        		title: 'Periodos',
		        bodyStyle:'padding:5px',
        		buttonAlign:'center',
		        width: 780,
		                  
        		items: [{
			            xtype: 'grid',
			            id:'last-chance',
	    		        ds: store,
	            		cm: columnModel,
			            autoExpandColumn: 'periodo',
	    		        height: 200,
	            		width:760,	            
			            border: true,
	    		        sm: new Ext.grid.RowSelectionModel({
							singleSelect: true,
							listeners: {							
					        	rowselect: function(grid2, row, rec) {	                    		                    	                        	                     	                        
					        		//Ext.MessageBox.confirm('Buu','el santo contra los vampiros'+row, modificarPeriodo);
	    					    	recordd=rec;	                  	                    
	    					    	//Ext.Msg.alert('alert', 'El santo contra las brujas de tolantongo'+recordd);  	   
              
			                   	 }
	            		   	}
						}),
		        		buttonAlign:'center',
			            tbar:[{
 					            text:'Agregar',
    	    				    tooltip:'Agregar un nuevo periodo de valides',
								iconCls:'add',
								handler: function() {
		                  			PeriodoIE(store); 
		    	    			}
        					}, '-', {
			        		    text:'Editar',
    			        		tooltip:'Editar el periodo seleccionado',
		        			    iconCls:'option',
				        	    handler:function(){
				        	    	    Ext.MessageBox.confirm('Confirm', 'Esta seguro que desea modificar el registro?', modificarPeriodo);
    							}
		    			    },'-',{
				    	        text:'Eliminar',
        				    	tooltip:'Eliminar el periodo seleccionado',
            					iconCls:'remove',
		            			handler:function(){
							        Ext.MessageBox.confirm('Confirm', 'Esta seguro que desea eliminar el registro?', eliminarPeriodo);
    							}
					    }],
	    		        bbar: new Ext.PagingToolbar({
		        		    store:store,
							items:[
				                '-', {
	        			        pressed: true,    	        	    
        	        			text: 'Actualizar',
			        	        cls: 'x-btn-text-icon details',
    			        	    toggleHandler: function(){}
          	  				}]
		        		})			  
        		}]        
		    }]
       },{xtype:'hidden',id:'clave-tipo-producto',name:'claveTipoProducto'}
       ,{xtype:'hidden',id:'clave-tipo-ramo',name:'claveTipoRamo'}
       ,{xtype:'hidden',id:'clave-tipo-poliza',name:'claveTipoPoliza'}
       ,{xtype:'hidden',id:'clave-tipo-seguro',name:'claveTipoSeguro'}],

       buttons: [{
            text: 'Guardar', 
            handler: function() {
                // check form value 
                if (tab2.form.isValid()) {
                	//alert(codigoTipoParametro.getValue());
                	Ext.getCmp('clave-tipo-producto').setValue(codigoTipoParametro.getValue());
                	Ext.getCmp('clave-tipo-ramo').setValue(codigoTipoRamo.getValue());
                	Ext.getCmp('clave-tipo-poliza').setValue(codigoTipoPoliza.getValue());
                	Ext.getCmp('clave-tipo-seguro').setValue(codigoTipoSeguro.getValue());                	
	 		        tab2.form.submit({			      
			            waitTitle:'Espere',
			            waitMsg:'Salvando datos...',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('Error', "Producto ya existe");
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('Status', "Producto guardado correctamente");
						    //window.location.replace("<s:url value="/"/>");
						    //window.location = String.format("<s:url value="/"/>");
						   	//window.location.href  = "<s:url value='/Redireccionate.action'/>";
					   		//window.location.href = 'Redireccionate.action';
					   		//window.location.refresh();
					   		//recargarPagina();
					   		Ext.getCmp('arbol-productos').getRootNode().reload();
						    //dataStore.load({params:{start:0, limit:10}});
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Error', 'Favor de llenar datos requeridos');
				}             
	        }
       },{
            text: 'Cancelar',
            handler: function() {
                    tab2.form.load({			      
			            waitMsg:'Salvando datos...',
			            url:'definicion/LimpiarSessionProducto.action',
						success: function(form, action) {
							ds.load({params:{start:0, limit:25}});
    						store.load({params:{start:0, limit:25}}); 
						   action.form.reset();
						   //dataStore.load({params:{start:0, limit:10}});
						}
			        });                   
                
	        }
       }]
    });  
	function recargarPagina(){
		window.location.reload();
	}
    ds.load({params:{start:0, limit:25}});
    store.load({params:{start:0, limit:25}}); 
    tab2.render('center');
    
});
</script>