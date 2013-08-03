<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<script type="text/javascript" src="${ctx}/resources/scripts/utilities/vTypes/date.js"></script>
<!-- SOURCE CODE -->
<jsp:include page="/resources/scripts/jsp/utilities/definicion/clausula/clausulaIE.jsp" flush="true" />
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

/**********************************************************************
crea la ventana de periodos
***********************************************************************/
/*function periodoIE() {  
    var formPanelPeriodos = new Ext.form.FormPanel({
  		id:'load-record-period',
        baseCls: 'x-plain',
        
        url:'definicion/AsociarPeriodo.action',
        defaultType: 'datefield',
		reader: new Ext.data.JsonReader({		//el edit aun no esta probado
            root: '' 
        }, ['inicio','fin']
        ),
        items: [{xtype:'hidden',id:'id-hidden-codigo-periodo',name:'codigoPeriodo'},{
                xtype:'datefield',
                fieldLabel: '<s:text name="def.productos.fechaIni"/>',
		        name: 'inicio',
		        id: 'startdt',
		        vtype: 'daterange',
		        allowBlank:false,
		        blankText : '<s:text name="insert.periodo.dateinicio"/>',
                endDateField: 'enddt',
                format:'d/m/Y'
		    
        	},{
        		xtype:'datefield',
                allowBlank:false,
                blankText : '<s:text name="insert.periodo.datefin"/>',
                fieldLabel: '<s:text name="def.productos.fechaFin"/>',
		        name: 'fin',
		        id: 'enddt',
        		vtype: 'daterange',
		        startDateField: 'startdt',
		        format:'d/m/Y'
            
        	}
		]
    }); 
    // define window and show it in desktop
    var windowPeriodos = new Ext.Window({
        title: '<s:text name="def.alta.periodo.title"/>',
        width: 230,
        height:150,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanelPeriodos,
		modal:true,
        buttons: [{
            text: '<s:text name="def.productos.btn.guardar"/>', 
            handler: function() {
                // check form value 
                if (formPanelPeriodos.form.isValid()) {
		 		    //    alert(Ext.getCmp('id-hidden-codigo-periodo').getValue());		      
	 		        formPanelPeriodos.form.submit({	
			            waitTitle:'<s:text name="ventana.proceso.clausula.mensaje.titulo"/>',
			            waitMsg:'<s:text name="ventana.proceso.clausula.mensaje.proceso"/>',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('Error al Guardar el Periodo', "La fecha inicial de un periodo no puede ser anterior <br />a la fecha final del ultimo periodo agregado");
						},
						success: function(form, action) {
						    //Ext.MessageBox.alert('Confirm', action.result.info);
						   
						    windowPeriodos.close();
						    store.load({params:{start:0, limit:10}});
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Errors', 'Favor de llenar todos los datos requeridos');
				}             
	        }
        },{
            text: '<s:text name="def.productos.btn.cancelar"/>',
            handler: function(){windowPeriodos.close();}
        }]
    });

    windowPeriodos.show();
}   
    function eliminarPeriodo(btn){
        if(btn=='yes'){
    		Ext.MessageBox.alert('alert', 'Changes saved successfully.');
        	//DeleteDemouser(store);
        }
    };
	
	function modificarPeriodo(btn){
        if(btn=='yes'){
        	//alert("El santo contra las momias de guanajuato"+recordd);
    		//Ext.MessageBox.alert('alert', 'Changes saved successfully.');
    		//Ext.Msg.alert('alert', 'El santo contra las brujas de tolantongo'+recordd);
        	PeriodoIE(store, recordd);
        }
    };*/
    function modificarClausula(){
        
        	ClausulaIE(ds, recordClausulas);
        
    };
    
    function showResultText(btn, text){
        Ext.example.msg('Button Click', 'You clicked the {0} button and entered the text "{1}".', btn, text);
    };
    
 //segunda tabla 
  var store = new Ext.data.Store({
		storeId:'store-periodos-valides',
        proxy: new Ext.data.HttpProxy({
			url: 'definicion/PeriodoLista.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'periodos',
            id: 'periodos'
	        },[
           {name: 'codigoPeriodo', type: 'string',mapping:'codigoPeriodo'},
           {name: 'inicio', type: 'string',mapping:'inicioFormato'},
           {name: 'fin', type: 'string',mapping:'finFormato'}    
        ]),
		remoteSort: true
    });
       store.setDefaultSort('periodos', 'desc'); 
  
    
    var columnModel = new Ext.grid.ColumnModel([
        {id:'codigoPeriodo',header: "Periodo", width: 175, sortable: true,dataIndex: 'codigoPeriodo'},
        {header: "Fecha Inicial", width: 185, sortable: true, dataIndex: 'inicio'},
        {header: "Fecha Final", width: 185, sortable: true, dataIndex: 'fin'}
    ]);
//   Define the Grid data and create the Grid
	var ds = new Ext.data.Store({
		storeId:'store-clausulas-asociadas',
        proxy: new Ext.data.HttpProxy({
			url: 'definicion/ClausulaLista.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'clausulas',
            id: 'clausulas'
	        },[
           {name: 'clave', type: 'string',mapping:'codigoClausula'},
           {name: 'clausulaDescripcion', type: 'string',mapping:'descripcionClausula'}    
        ]),
		remoteSort: true
    });
       ds.setDefaultSort('clausulas', 'desc'); 

  
    // the DefaultColumnModel expects this blob to define columns. It can be extended to provide
    // custom or reusable ColumnModels
    var colModel = new Ext.grid.ColumnModel([
        {id:'clave',header: "Clave", width: 75, sortable: true,dataIndex: 'clave'},
        {header: "Descripción", width: 260, sortable: true,  locked:false, dataIndex: 'clausulaDescripcion'}
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
           {name: 'clausulaDescripcion', type: 'string',mapping:'value'},
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
		 					id:'id-combo-clausulas-definicion',
    						tpl: '<tpl for="."><div ext:qtip="{value}. {key}" class="x-combo-list-item">{value}</div></tpl>',
					 		store: dsComboCatalogo,
							displayField:'value',
	    					valueField: 'key',	    				
							typeAhead: true,
					    	mode: 'local',
		    				triggerAction: 'all',
							emptyText:'<s:text name="def.productos.valida.clausula"/>',
    						selectOnFocus:true,
   							fieldLabel: '<s:text name="def.productos.clave"/>',
			    			name:"claveCatalogoClausulas"				    			
			});  
    		var codigoTipoParametro =new Ext.form.ComboBox({
    						id:'id-combo-tipo-parametro-definicion',
    	                    tpl: '<tpl for="."><div ext:qtip="{value}. {key}" class="x-combo-list-item">{value}</div></tpl>',
					    	store: dsCatalogoTipoProducto,
						    displayField:'value',
						    valueField: 'key',
					    	typeAhead: true,
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="def.productos.valida.producto"/>',
					    	selectOnFocus:true,
						    fieldLabel: '<s:text name="def.productos.principal.tipo.producto"/>',
					    	name:"valorTipoProducto",	
							allowBlank:false,							
							blankText : '<s:text name="TipoProducto.requerido"/>',
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
							id:'id-combo-tipo-ramo-definicion',
    	                    tpl: '<tpl for="."><div ext:qtip="{value}. {key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsCatalogoTipoRamo,
						    displayField:'value',
						    valueField: 'key',
					    	typeAhead: true,
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="def.productos.valida.ramo"/>',
					    	selectOnFocus:true,
						    fieldLabel: '<s:text name="def.productos.principal.tipo.ramo"/>',
					    	name:"valorTipoRamo",
							allowBlank:false,
							blankText : '<s:text name="TipoRamo.requerido"/>',
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
							id:'id-combo-tipo-poliza-definicion',
    	                    tpl: '<tpl for="."><div ext:qtip="{value}. {key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsCatalogoTipoPoliza,
						    displayField:'value',
						    valueField: 'key',
					    	typeAhead: true,
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="def.productos.valida.poliza"/>',
					    	selectOnFocus:true,
						    fieldLabel: '<s:text name="def.productos.principal.tipo.poliza"/>',
					    	name:"valorTipoPoliza",
							allowBlank:false,
							blankText : '<s:text name="TipoPoliza.requerido"/>',
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
							id:'id-combo-tipo-seguro-definicion',
    	                    tpl: '<tpl for="."><div ext:qtip="{value}. {key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsCatalogoTipoSeguro,
						    displayField:'value',
						    valueField: 'key',
					    	typeAhead: true,
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="def.productos.valida.seguro"/>',
					    	selectOnFocus:true,
						    fieldLabel: '<s:text name="def.productos.principal.tipo.seguro"/>',
					    	name:"valorTipoSeguro",
							allowBlank:false,
							blankText : '<s:text name="TipoSeguro.requerido"/>',
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
									
									if(this.getValue() == "2"){
										Ext.getCmp('switch-subincisos').setDisabled(false);																			
									}
									if(this.getValue() == "1"){
										Ext.getCmp('switch-subincisos').setDisabled(true);
										Ext.getCmp('switch-subincisos').reset();										
									}
									if(this.getValue() == "3"){
										Ext.getCmp('switch-subincisos').setDisabled(true);
										Ext.getCmp('switch-subincisos').reset();										
									}									
							}		
			});

    //primera tabla
    		
   		//definicion datos principales de producto 		 
    		 var codigoRamo= new Ext.form.NumberField({
    		 				id:'codigo-ramo-producto',
    		 				fieldLabel: '<s:text name="def.productos.principal.producto"/>',
							name: 'codigoRamo',
							allowDecimals : false,
			                allowNegative : false,                
            			    labelSeparator:'',
            			    maxValue:999,
            			    maxText:'<s:text name="cdRamo.longitudMaxima"/>',
							width:44,
							allowBlank:false,
							blankText : '<s:text name="cdRamo.requerido"/>'
    		 });
    		 var descripcionRamo= new Ext.form.TextField({
    		 				hideLabel: true,												                    
							fieldLabel: '<s:text name="def.productos.principal.descripcionProducto"/>',
							name: 'descripcionRamo',
							labelSeparator:'',
							width:150,
							allowBlank:false,
							blankText : '<s:text name="dsRamo.requerido"/>',
							maxLength:'30'
    		 });
    		 var descripcionP= new Ext.form.TextArea({
	                        name: "descripcion",
	                        fieldLabel: '<s:text name="def.productos.principal.descripcion"/>',
	                        width: 200,
	                        labelSeparator:'',
	                        allowBlank: false,
	                        blankText : '<s:text name="descripcion.requerida"/>'
	        });

    		//definicion attributos de producto
    		var tipoDagnos= new Ext.form.Radio({	
    		 				id:'tipo-dagnos',	 				
    		 				name:'switchTipo',
    		 				boxLabel:'',//'<s:text name="def.productos.danios"/>',
    		 				hideLabel:true,
    		 				hideParent:true,
    		 				checked:true,
    		 				hidden:true,
    		 				onClick:function(){
				            	Ext.getCmp('hidden-codigo-switch-tipo').setValue("1");
				            	if(this.getValue()){
				            		Ext.getCmp('switch-reinstalacion-automatica').setDisabled(false);
				            	}
				            }	
    		 });
    		 var tipoVida= new Ext.form.Radio({		 				
				    		id:'tipo-vida', 
    		 				name:'switchTipo',
    		 				boxLabel:'',//'<s:text name="def.productos.vida"/>',
    		 				hideLabel:true,
    		 				hideParent:true,
    		 				hidden:true,
    		 				onClick:function(){
				            	Ext.getCmp('hidden-codigo-switch-tipo').setValue("3");
				            	if(this.getValue()){				            		
				            		Ext.getCmp('switch-reinstalacion-automatica').setDisabled(true);
				            		Ext.getCmp('switch-reinstalacion-automatica').reset();				            		
				            	}
				            }
    		 });
    		 var tipoGastosMedicos= new Ext.form.Radio({	
    		 				id:'tipo-gastos-medicos',	 				
    		 				name:'switchTipo',
    		 				boxLabel:'',//'<s:text name="def.productos.gastosMedicos"/>',
    		 				hideLabel:true,
    		 				hideParent:true,
    		 				hidden:true,
    		 				onClick:function(){
				            	Ext.getCmp('hidden-codigo-switch-tipo').setValue("2");
				            	if(this.getValue()){
				            		Ext.getCmp('switch-reinstalacion-automatica').setDisabled(false);
				            	}
				            }
    		 });
    		 var tipoOtros= new Ext.form.Radio({		 				
				    		id:'tipo-otros', 
    		 				name:'switchTipo',
    		 				boxLabel:'',//'<s:text name="def.productos.otros"/>',
    		 				hideLabel:true,
    		 				hideParent:true,
    		 				hidden:true,
    		 				onClick:function(){
				            	Ext.getCmp('hidden-codigo-switch-tipo').setValue("4");
				            	if(this.getValue()){
				            		Ext.getCmp('switch-reinstalacion-automatica').setDisabled(false);				            		
				            	}
				            }
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
            				id:'switch-permiso-pagos-otras-monedas',
            				name:'switchPermisoPagosOtrasMonedas',
            				boxLabel: '',//'<s:text name="def.productos.monedas"/>',
            				hideLabel:true,
            				hidden:true,
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
            				id:'switch-reinstalacion-automatica',
            				name:'switchReinstalacionAutomatica',
            				boxLabel: '<s:text name="def.productos.reinstAut"/>',
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
            				id:'switch-distintas-polizas-por-asegurado',
            				name:'switchDistintasPolizasPorAsegurado',
            				boxLabel: '',//'<s:text name="def.productos.distintasPolReaseguro"/>',
            				hideLabel:true,
            				hidden:true,
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
							id:'switch-renovable',
							name:'renovable',
							boxLabel: '<s:text name="def.productos.tipoPolPermit.renovable"/>',
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
							id:'switch-temporal',
							name:'temporal',
	        				boxLabel: '<s:text name="def.productos.tipoPolPermit.temporal"/>',
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
							id:'switch-vidaEntera',
							name:'vidaEntera',
	           				boxLabel: '<s:text name="def.productos.tipoPolPermit.vidaEntera"/>',
            				hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");
				            		Ext.getCmp('switch-renovable').setDisabled(true);
				            		Ext.getCmp('switch-temporal').setDisabled(true);
				            		Ext.getCmp('switch-renovable').reset();
				            		Ext.getCmp('switch-temporal').reset();
				            		Ext.getCmp('tipo-dagnos').setDisabled(true);
				            		Ext.getCmp('tipo-otros').setValue(true);				            	
				            	}else{      
				            		this.setRawValue("N");				            		
				            		Ext.getCmp('switch-renovable').setDisabled(false);
				            		Ext.getCmp('switch-temporal').setDisabled(false); 
				            		Ext.getCmp('tipo-dagnos').setDisabled(false);     						
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
							id:'switch-subincisos',
							name:'switchSubincisos',
	        				boxLabel: '',//'<s:text name="def.productos.subIncisos"/>',
            				hideLabel:true,
            				hidden:true, 
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
            var mesesBeneficiosDummy= new Ext.form.NumberField({            				
         				    fieldLabel: '',//'Antigüedad Mínima <br>(Meses)',
         				    labelAlign:'left',
    		 				hidden:true 		
            }); 
			var mesesBeneficios= new Ext.form.NumberField({
							id:'meses-beneficios',
            				name:'mesesBeneficios',
         				    fieldLabel: '',//'<s:text name="def.productos.mesesBeneficios"/>',
         				    hideParent:true,
			                allowDecimals : false,
			                allowNegative : false,                
            			    labelSeparator:'',
            			    maxValue:500,
            			    maxText:'<s:text name="mesesBeneficio.longitudMaxima"/>',
			                width:50,
    		 				hideLabel:true,
    		 				hidden:true 		
            }); 
             var tipoCalculoPolizasTemporalesDummy= new Ext.form.NumberField({
            				fieldLabel: '',//'Tipo Cálculo Pólizas Temporales',         				    
    		 				labelAlign:'left',
    		 				hidden:true
    		 				
            }); 
             var tipoCalculoPolizasTemporales= new Ext.form.NumberField({
            				name:'tipoCalculoPolizasTemporales',
         				    fieldLabel: '',//'<s:text name="def.productos.tipoCalcPolTemp"/>',
         				    hideParent:true,
			                allowDecimals : false,
			                allowNegative : false,                
            			    labelSeparator:'',
    		 				labelAlign:'top',
    		 				maxValue:500,
    		 				maxText:'<s:text name="mesesBeneficio.longitudMaxima"/>',
			                width:50,
    		 				hideLabel:true,
    		 				hidden:true	
            }); 
            
             var switchRehabilitacion= new Ext.form.Checkbox({
             				id:'switch-rehabilitacion',
            				name:'switchRehabilitacion',
            				boxLabel: '<s:text name="def.productos.rehabilitacion"/>',
            				hideLabel:true,
            				onClick: function(){			            		
            					if(this.getValue()){	
            						 this.setRawValue("S");	 
            						 //mesesBeneficios.reset();
            						 //Ext.getCmp('id4test-hidding').show();          						 			            	
				            	}else{      
				            		this.setRawValue("N");	
			            			//Ext.getCmp('id4test-hidding').hide(); 		            					
				            		//mesesBeneficios.disable();            						
				            	}
			            	}			
            });
 
 
 
 		/*var switchCancelacion= new Ext.form.Checkbox({
             				id:'switch-cancelacion',
            				name:'switchCancelacion',
            				boxLabel: 'Cancelacion',
            				hideLabel:true,
            				onClick: function(){			            		
            					if(this.getValue()){	
            						 this.setRawValue("S");	 
            						 mesesBeneficios.reset();
            						 Ext.getCmp('id4test-hidding').show();          						 			            	
				            	}else{      
				            		this.setRawValue("N");	
			            			Ext.getCmp('id4test-hidding').hide(); 		            					
				            		//mesesBeneficios.disable();            						
				            	}
			            	}			
            });*/
 
 
            
 	var formaAnidada= new Ext.FormPanel({
    		    		layout:'form',
    		    		border:false, 
    		    		width:800,   		
    		    		items:[{
			    		    	layout:'column',
	    				    	border:false,
    					    	items:[{
    			    					columnWith:.7,
    			    					layout:'form',
		    		    				border:false,
		    		    				width:200,
		    		    				labelAlign:'top',
    				    				items:[
	    				    			claveCatalogoClausula]
		    		    			},{
    			    					columnWith:.3,
		    			    			border:false,
    					    			layout:'form',
    					    			width:130,
    					    			items:[{
								        		layout: 'form',
								        		border:false,
								        		height:18
		        							},{
		    					    			border:false,
    							    			layout:'form',
	    						    			width:130,
    							    			items:[{
				    					    			xtype:'button',
				    			    					text:'<s:text name="def.productos.btn.agregarCatalogo"/>',
    								    				handler: function(){
    								    						ClausulaIE(dsComboCatalogo,selectedId); 
    		    										}
	    		    							}]
				    			    	}]
	    				    	}]
		    			}]		
	});         
	formaAnidada.render('center-anidado');  /*
    var formaAnidadaPeriodos= new Ext.FormPanel({
    		    		layout:'form',
    		    		border:false, 
    		    		width:800,   
    		    		url:'definicion/AsociarPeriodo.action',		
    		    		items:[{xtype:'hidden', id:'hidden-id-periodo', name:'codigoPeriodo'},{
			    		    	layout:'column',
	    				    	border:false,
    					    	items:[{
    			    					columnWith:.2,
    			    					layout:'form',
		    		    				border:false,
		    		    				width:110,
		    		    				labelAlign:'top',
    				    				items:[{
								                xtype:'datefield',
								                fieldLabel: 'Fecha Inicial',
										        name: 'inicio',
										        id: 'startdt',
										        vtype: 'daterange',
										        allowBlank:false,
								                endDateField: 'enddt',
								                format:'d/m/Y'		    
							        	}]
		    		    			},{
    			    					columnWith:.2,
    			    					layout:'form',
		    		    				border:false,
		    		    				width:110,
		    		    				labelAlign:'top',
    				    				items:[{
								        		xtype:'datefield',
									            allowBlank:false,
								                fieldLabel: 'Fecha Final',
										        name: 'fin',
										        id: 'enddt',
								        		vtype: 'daterange',
										        startDateField: 'startdt',
										        format:'d/m/Y'            
							        	}]
		    		    			},{
    			    					columnWith:.3,
		    			    			border:false,
    					    			layout:'form',
    					    			width:70,
    					    			items:[{
								        		layout: 'form',
								        		border:false,
								        		height:18
		        							},{
		    					    			border:false,
    							    			layout:'form',
	    						    			width:60,
    							    			items:[{
				    					    			xtype:'button',
				    			    					text:'Nuevo',
    								    				handler: function(){
    								    						//Ext.getCmp('hidden-id-periodo').setValue('-1');
    								    						//Ext.getCmp('startdt').setValue('09/09/08');
    								    						//Ext.getCmp('enddt').setValue('09/09/08');
    		    										}
	    		    							}]
				    			    	}]
	    				    	},{
    			    					columnWith:.3,
		    			    			border:false,
    					    			layout:'form',
    					    			width:130,
    					    			items:[{
								        		layout: 'form',
								        		border:false,
								        		height:18
		        							},{
		    					    			border:false,
    							    			layout:'form',
	    						    			width:120,
    							    			items:[{
				    					    			xtype:'button',
				    			    					text:'Guardar',
    								    				handler: function(){
    								    				/*
    								    					formaAnidadaPeriodos.form.submit({			      
														            waitMsg:'Salvando datos...',
														            failure: function(form, action) {
																	    Ext.MessageBox.alert('Error al Guardar el Periodo', "La fecha inicial de un periodo no puede ser anterior <br />a la fecha final del ultimo periodo agregado");
																	},
																	success: function(form, action) {
																	    //Ext.MessageBox.alert('Confirm', action.result.info);						   
																	    store.load({params:{start:0, limit:10}});
																	}
													        });                   	/
    		    										}
	    		    							}]
				    			    	}]
	    				    	}]
		    			}]		
	});         
	formaAnidadaPeriodos.render('center-anidado-periodos');      */
function reload(){
	ds.load({params:{start:0, limit:10}});
}     
var selectedId;
/*
 *	Here is where we create the Form
 */

   
    var tab2 = new Ext.FormPanel({
        labelAlign: 'right',
        id:'reload-products',
        title: '<s:text name="def.productos.title"/>',
        bodyStyle:'padding:5px',
        url:"definicion/InsertarProducto.action",
        border:true,
        width: 650,
        height:470,
        autoScroll:true,
        items: [{
						            layout:'column',
						            border:false,
						            bodyStyle:'padding:5px 5px 0',
						            items:[{
						            		columnWidth:.55,
							                layout:'form',							                
									        border:false,
									        bodyBorder:true,
								    	    labelAlign:"left",
								        	width: 310,
									        items: [{
							  			            layout:'column',
										            border:false,
										            items:[{
											                columnWidth:.45,
											                layout: 'form',
											                border:false,
											                items: [codigoRamo]
											            },{
										               		columnWidth:.55,
											                layout: 'form',
											                border:false,
											                items: [descripcionRamo]
											            }]
										        },descripcionP]
							                              
								         },{
							                columnWidth:.45,
							                layout:'form',							                
									        border:false,
									        bodyBorder:true,
								    	    labelAlign:"left",
								        	labelWidth:110,
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
            deferredRender:false,            
            activeTab: 0,
            height:280,
            defaults:{bodyStyle:'padding:10px'},
            items:[{
       				title:'<s:text name="def.productos.tabs.title.definicion"/>',
			        layout:'form',					       
			        border:false,					        
			        items: [{
				            layout:'column',
				            border:false,
				            items:[{						            		
					                columnWidth:.09,
					                border:false
					            },{						            		
					                columnWidth:.45,
					                layout: 'form',
					                labelAlign : "top" ,
					                border:false,							               
					                items: [//switchSuscripcion,
					                		//switchClausulasNoTipificadas,	
					                		//switchCancelacion,						                									        
									        switchRehabilitacion,
									        {
									        layout:'form',
									        border:false,
									        width:250,
									        id:'id4test-hidding',
									        items:[{
					  			            layout:'column',
								            border:false,
								            labelAlign : "left" ,
								            items:[{
									                columnWidth:.5,
									                layout: 'form',
									                border:false,	
									                width:138,										               
									                items: [mesesBeneficiosDummy]
									            },{
								               		columnWidth:.5,
									                layout: 'form',
									                border:false,										                
									                width:60,
									                items: [mesesBeneficios]
									            }]
									        }]	
								        	},									        
									        switchPermisoPagosOtrasMonedas,
									        {
							  			            layout:'column',
										            border:false,
										            labelAlign : "left" ,
										            items:[{
											                columnWidth:.5,
									    		            layout: 'form',
									    		            width:50,
									            		    border:false,											               
											                items: [tipoCalculoPolizasTemporalesDummy]
											            },{
										               		columnWidth:.5,
											                layout: 'form',
											                width:30,
											                border:false,										                
									    		            items: [tipoCalculoPolizasTemporales]
									            		}]
									            	
								        	},
					                		switchReinstalacionAutomatica ,
					                		switchDistintasPolizasPorAsegurado  		 
              		 						]
					            },{
					                columnWidth:.35,
					                layout: 'form',
					                border:false,					               
					                labelAlign : "top" ,
					                items: [	
					                		
					                		 						                		
			        		        		{
					  			            layout:'column',
								            border:false,
								            labelAlign : "left" ,
								            items:[{//width:'70',
								            		layout:'form',
								            		border:false,
								            		items:[{											               
											                layout: 'form',
											                border:false,											               
											                items: [tipoDagnos]
											            },{								        		       		
									            		    layout: 'form',
											                border:false,										                
											                items: [tipoVida]
										            }]
									            },{ 
								            		layout:'form',
								            		border:false,
						        		    		items:[{											                
											                layout: 'form',
											                border:false,											               
											                items: [tipoGastosMedicos]
											            },{								            		   		
									                		layout: 'form',
											                border:false,										                
											                items: [tipoOtros]
										            }]
								            }]
							        	},{
								                layout:'form',
								                heigth:'2',
												border: false
												
   											},{
								                layout:'form',								                
												border: true,
												title:'Temporalidad de las Pólizas',
								                items: [
						        	                    renovable,
								                        temporal,
											            vidaEntera
									                ]
   											},{
								                layout:'form',
								                heigth:'2',
												border: false
												
   											},switchSubincisos]
				            	}]
       
		 	  	     	}]
					        
		    },{
		        layout: 'form',
		        title: '<s:text name="def.productos.tabs.title.clausulas"/>',
		        labelAlign: 'top',
	    	    frame:false,        
	    	    bodyStyle:'padding:5px 5px 0',
    	    	width: 680,
	        	border:false,
    		    items: [
    		    		{
		        		layout: 'form',
		        		border:false,
		        		contentEl:'center-anidado'
		        		},{
	    		        xtype: 'grid',
	    		        id:'grid-clausulas',
	            		ds: ds,
			            cm: colModel,
	    		        autoExpandColumn: 'clave',
	            		height: 180,
			            width:560,                    
	            		border: true,
	            		sm: new Ext.grid.RowSelectionModel({
							singleSelect: true,
							listeners: {							
					        	rowselect: function(grid2, row, rec) {	
					        		//selectedId=row.getSelected();                    		                    	                        	                     	                        
					        		//alert(selectedId);
					        		var sel = Ext.getCmp('grid-clausulas').getSelectionModel().getSelected();
							        var selIndex = ds.indexOf(sel);
							        //alert(selIndex);
					            	
                                	Ext.getCmp('eliminar-clausula').on('click',function(){
										if(rec!=null){
											urlEliminarClausula='definicion/EliminarClausula.action?claveCatalogoClausulas='+rec.get('clave');
											//urlEliminarClausula='definicion/EliminarClausula.action';
											Ext.MessageBox.confirm('Mensaje','Realmente deseas eliminar el elemento?', function(btn) {
									           if(btn == 'yes'){   
												     formaAnidada.form.submit({	
														    url: urlEliminarClausula,
														    waitMsg:"Procesando...",
														    failure: function(form, action) {
									 					    	Ext.MessageBox.alert('Status', 'Error al eliminar');
															},
															success: function(form, action) {
															    Ext.MessageBox.alert('Status', 'Elemento eliminado');						    
															    ds.load();						    
															}
														});         	 
												}
											});
											
				                        }                                                                         
                                	});
	    					   	 }
	            		   	}
						}),
			            tbar:[{
 					           text:'<s:text name="def.productos.btn.agregar"/>',
    	    				   tooltip:'Asocia la clausula seleccionada en el combo',
					           iconCls:'add',
					           handler:function(){				  					 
				  					 formaAnidada.form.submit({url:'definicion/AsociarClausula.action',
								     waitTitle:'<s:text name="ventana.proceso.clausula.mensaje.titulo"/>',
								     waitMsg:'<s:text name="ventana.proceso.clausula.mensaje.proceso"/>',
								     success:function(a,t){
								     ds.reload();
								     dsComboCatalogo.load();
								     }}); 								     
							   }
		        			},'-',{
		    			    	id:'eliminar-clausula',
				    	        text:'<s:text name="def.productos.btn.eliminar"/>',
        				    	tooltip:'Eliminar el periodo seleccionado',
            					iconCls:'remove'
            				
					    }]	  
        		}]
			},{
        		layout: 'form',
		        frame: false,
        		title: '<s:text name="def.productos.tabs.title.periodos"/>',
		        bodyStyle:'padding:5px',
        		buttonAlign:'center',
		        width: 680,
		                  
        		items: [/*{
		        		layout: 'form',
		        		border:false,
		        		contentEl:'center-anidado-periodos'
		        		},*/{
						            xtype: 'grid',
						            id:'grid-periodos',
	    		        			ds: store,
				            		cm: columnModel,
						            autoExpandColumn: 'codigoPeriodo',
	    		        			height: 210,
				            		width:560,	            
						            border: true,
	    		        			sm: new Ext.grid.RowSelectionModel({
											singleSelect: true,
											listeners: {							
										        	rowselect: function(grid2, row, rec) {	                    		                    	                        	                     	                        
									        				//Ext.MessageBox.confirm('Buu','el santo contra los vampiros'+row, modificarPeriodo);
  	    									    			  
	    									    			//formaAnidadaPeriodos.form.loadRecord(recordd);	                  	                    
			    									    	//Ext.Msg.alert('alert', 'El santo contra las brujas de tolantongo'+recordd);
			    									    	  afuera=row; 
			    									    	  Ext.getCmp('eliminar-periodo').on('click',function(){																	 
																	if(afuera!=temporal){
																	temporal=afuera;
																	if(rec!=null){
																			urlEliminarPeriodo='definicion/EliminarPeriodo.action?codigoPeriodo='+temporal;
																			Ext.MessageBox.confirm('Mensaje','Esta seguro que desea eliminar este elemento?', function(btn) {
																		           if(btn == 'yes'){   
																					    formaAnidada.form.submit({	
																							url: urlEliminarPeriodo,
																						    waitMsg:"Procesando...",
																						    failure: function(form, action) {
									 													    	Ext.MessageBox.alert('Status', 'Error al eliminar');
																							},
																							success: function(form, action) {
																							    Ext.MessageBox.alert('Status', 'Elemento Eliminado');						    
																							    store.load();						    
																							}
																						});         	 
																					}
																			});
																			//DeleteInDataStoreFromAction(formaAnidada,urlEliminarPeriodo,store); 
											                        }
											                       	}                                                                         
							                                	});		
							                                	
							                                	Ext.getCmp('editar-periodo').on('click',function(){
																	if(afuera!=temporal){
							                            				temporal=afuera;
							                            				recorddP=rec;
                            											PeriodoIE(store,temporal);
									                                   	//Ext.getCmp('load-record-period').getForm().loadRecord(recorddP); 											                       
                            										}																	                                                                         
							                                	});	
							                                	temporal=-1;		    									    	           	      
							        		       	}
			            				   	}
									}),
		    			    		buttonAlign:'center',
			            			tbar:[{
 								           text:'<s:text name="def.productos.btn.agregar"/>',
    	    							   tooltip:'Agregar un nuevo Periodo',
					    			       iconCls:'add',
								           handler:function (){				  					 
								  					PeriodoIE(store);						     
										   }
					        			}, '-', {
					        				id:'editar-periodo',
	    					    		    text:'<s:text name="def.productos.btn.editar"/>',
			    	        				tooltip:'Editar el periodo seleccionado',
		        	    					iconCls:'option'
					    			    },'-',{
							    	        text:'<s:text name="def.productos.btn.eliminar"/>',
							    	        id:'eliminar-periodo',
        							    	tooltip:'Eliminar el periodo seleccionado',
			            					iconCls:'remove'
								    }]		  
			        		
        		}]		       
		    }]
       },{xtype:'hidden',id:'hidden-codigo-switch-tipo',name:'codigoSwitchTipo'}
       ,{xtype:'hidden',id:'clave-tipo-producto',name:'claveTipoProducto'}
       ,{xtype:'hidden',id:'clave-tipo-ramo',name:'claveTipoRamo'}
       ,{xtype:'hidden',id:'clave-tipo-poliza',name:'claveTipoPoliza'}
       ,{xtype:'hidden',id:'clave-tipo-seguro',name:'claveTipoSeguro'}
       ,{xtype:'hidden',id:'hidden-bandera-editar',name:'banderaEditar'}
       ,{xtype:'hidden',id:'hidden-codigo-ramo-editar',name:'codigoRamoEditar'}],

       buttons: [{
            text: '<s:text name="def.productos.btn.guardar"/>', 
            handler: function() {
                // check form value 
                if (tab2.form.isValid()) {
                	//alert(codigoTipoParametro.getValue());
                	Ext.getCmp('clave-tipo-producto').setValue(codigoTipoParametro.getValue());
                	Ext.getCmp('clave-tipo-ramo').setValue(codigoTipoRamo.getValue());
                	Ext.getCmp('clave-tipo-poliza').setValue(codigoTipoPoliza.getValue());
                	Ext.getCmp('clave-tipo-seguro').setValue(codigoTipoSeguro.getValue());                	
	 		        tab2.form.submit({			      
			            waitTitle:'<s:text name="ventana.proceso.clausula.mensaje.titulo"/>',
						waitMsg:'<s:text name="ventana.proceso.clausula.mensaje.proceso"/>',
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
            text: '<s:text name="def.productos.btn.cancelar"/>',
            handler: function() {
                    tab2.form.load({			      
			            waitTitle:'<s:text name="ventana.proceso.clausula.mensaje.titulo"/>',
						waitMsg:'<s:text name="ventana.proceso.clausula.mensaje.proceso"/>',
			            url:'definicion/LimpiarSessionProducto.action',
						success: function(form, action) {
							ds.load({params:{start:0, limit:25}});
    						store.load({params:{start:0, limit:25}}); 
						    action.form.reset();
						    Ext.getCmp('reload-products').getForm().reset();
								  					Ext.getCmp('switch-permiso-pagos-otras-monedas').setValue(false);
					                      			Ext.getCmp('switch-permiso-pagos-otras-monedas').setRawValue("N");
					                      			Ext.getCmp('switch-reinstalacion-automatica').setValue(false);
				                          			Ext.getCmp('switch-reinstalacion-automatica').setRawValue("N");
       			                           			Ext.getCmp('switch-distintas-polizas-por-asegurado').setValue(false);
				                           			Ext.getCmp('switch-distintas-polizas-por-asegurado').setRawValue("N");
													Ext.getCmp('switch-rehabilitacion').setValue(false);
				                           			Ext.getCmp('switch-rehabilitacion').setRawValue("N");
				                           			Ext.getCmp('id4test-hidding').hide();    	                       		
       			                           			Ext.getCmp('switch-subincisos').setValue(false);       			                           			
				                           			Ext.getCmp('switch-subincisos').setRawValue("N");    	        
				                           			Ext.getCmp('switch-renovable').setValue(false);
				                           			Ext.getCmp('switch-renovable').setRawValue("N"); 
                				           			Ext.getCmp('switch-temporal').setValue(false);
                           							Ext.getCmp('switch-temporal').setRawValue("N");
													Ext.getCmp('switch-vidaEntera').setValue(false);
				                           			Ext.getCmp('switch-vidaEntera').setRawValue("N");
						   //dataStore.load({params:{start:0, limit:10}});
						}
			        });                   
                
	        }
       }]
    }); 
    Ext.getCmp('id4test-hidding').hide();  
	function recargarPagina(){
		window.location.reload();
	}
	/*
	tab2.on('beforerender',function(){
		url4Product='definicion/EditarProducto.action'; 	
		var storeProducto = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: url4Product
        		}),
        		reader: new Ext.data.JsonReader({
            	root:'editarProducto',   
            	totalProperty: 'totalCount',
            	id: 'descripcion'
            	         	
	        	},[
	        		{name: 'codigoRamo',  type: 'string',  mapping:'codigoRamo'},
	        		{name: 'descripcion',  type: 'string',  mapping:'descripcion'},
	        		{name: 'descripcionRamo',  type: 'string',  mapping:'descripcionRamo'},
	        		{name: 'valorTipoProducto',  type: 'string',  mapping:'codigoTipoParametro'},
	        		{name: 'valorTipoRamo',  type: 'string',  mapping:'codigoTipoRamo'},
   	        		{name: 'valorTipoPoliza',  type: 'string',  mapping:'codigoTipoPoliza'},
	        		{name: 'valorTipoSeguro',  type: 'string',  mapping:'codigoTipoSeguro'},	        			        		
	        		{name: 'switchTipo',  type: 'string',  mapping:'switchTipo'},
	        		{name: 'switchPermisoPagosOtrasMonedas',  type: 'string',  mapping:'switchPermisoPagosOtrasMonedas'},
	        		{name: 'switchReinstalacionAutomatica',  type: 'string',  mapping:'switchReinstalacionAutomatica'},
   	        		{name: 'switchDistintasPolizasPorAsegurado',  type: 'string',  mapping:'switchDistintasPolizasPorAsegurado'},
	        		{name: 'switchSubincisos',  type: 'string',  mapping:'switchSubincisos'},	        		
	        		{name: 'mesesBeneficios',  type: 'string',  mapping:'mesesBeneficios'},
	        		{name: 'tipoCalculoPolizasTemporales',  type: 'string',  mapping:'tipoCalculoPolizasTemporales'},
	        		{name: 'switchRehabilitacion',  type: 'string',  mapping:'switchRehabilitacion'},
	        		{name: 'temporal',  type: 'string',  mapping:'temporal'},
	        		{name: 'renovable',  type: 'string',  mapping:'renovable'},
	        		{name: 'vidaEntera',  type: 'string',  mapping:'vidaEntera'}
	        		
				]),
			
			remoteSort: true
    	});
    	storeProducto.setDefaultSort('descripcion', 'desc');
    	
    	storeProducto.on('load', function(){
                           var rec = storeProducto.getAt(0); 
                            tab2.form.loadRecord(rec);   
                            Ext.getCmp('grid-clausulas').getStore().load();
                            Ext.getCmp('grid-periodos').getStore().load();
                            Ext.getCmp('hidden-bandera-editar').setValue("0");
                            Ext.getCmp('codigo-ramo-producto').enable();   
                            
                            	tab2.form.reset();
                            
                            if(rec.get('switchTipo') == '0'){
    	                       		Ext.getCmp('tipo-dagnos').setValue(true);
	                        }else if(rec.get('switchTipo') == '1'){
                           			Ext.getCmp('tipo-otros').setValue(true);
	                        }else{
	                        		Ext.getCmp('tipo-otros').reset();
	                        }
	                        if(rec.get('switchPermisoPagosOtrasMonedas') == 'S'){
    	                       		Ext.getCmp('switch-permiso-pagos-otras-monedas').setValue(true);
    	                       		Ext.getCmp('switch-permiso-pagos-otras-monedas').setRawValue("S");
	                        }else{
                           			Ext.getCmp('switch-permiso-pagos-otras-monedas').setValue(false);
	                      			Ext.getCmp('switch-permiso-pagos-otras-monedas').setRawValue("N");
	                        }
	                        if(rec.get('switchReinstalacionAutomatica') == 'S'){
    	                       		Ext.getCmp('switch-reinstalacion-automatica').setValue(true);
    	                       		Ext.getCmp('switch-reinstalacion-automatica').setRawValue("S");
    	                    }else{
                           			Ext.getCmp('switch-reinstalacion-automatica').setValue(false);
                          			Ext.getCmp('switch-reinstalacion-automatica').setRawValue("N");
	                        }
	                        if(rec.get('switchDistintasPolizasPorAsegurado') == 'S'){
    	                       		Ext.getCmp('switch-distintas-polizas-por-asegurado').setValue(true);
    	                       		Ext.getCmp('switch-distintas-polizas-por-asegurado').setRawValue("S");
	                        }else{
                           			Ext.getCmp('switch-distintas-polizas-por-asegurado').setValue(false);
                           			Ext.getCmp('switch-distintas-polizas-por-asegurado').setRawValue("N");
	                        }
	                        if(rec.get('switchRehabilitacion') == 'S'){
    	                       		Ext.getCmp('switch-rehabilitacion').setValue(true);    	                       		
    	                       		Ext.getCmp('switch-rehabilitacion').setRawValue("S");
    	                       		Ext.getCmp('id4test-hidding').show();
    	                       		var recMesesBeneficios =rec.get('mesesBeneficios');
    	                       		Ext.getCmp('meses-beneficios').setValue(recMesesBeneficios);
    	                    		
	                        }else{
                           			Ext.getCmp('switch-rehabilitacion').setValue(false);
                           			Ext.getCmp('switch-rehabilitacion').setRawValue("N");
                           			Ext.getCmp('id4test-hidding').hide();
    	                       		Ext.getCmp('meses-beneficios').reset();
    	                       		
	                        }    
	                        if(rec.get('switchSubincisos') == 'S'){
    	                       		Ext.getCmp('switch-subincisos').setValue(true);
    	                       		Ext.getCmp('switch-subincisos').setRawValue("S");    	                       		
	                        }else{
                           			Ext.getCmp('switch-subincisos').setValue(false);
                           			Ext.getCmp('switch-subincisos').setRawValue("N");    	                       		
	                        }     
	                        if(rec.get('temporal') == 'S'){
    	                       		Ext.getCmp('switch-temporal').setValue(true);
    	                       		Ext.getCmp('switch-temporal').setRawValue("S");    	                       		
	                        }else{
                           			Ext.getCmp('switch-temporal').setValue(false);
                           			Ext.getCmp('switch-temporal').setRawValue("N");    	                       		
	                        }
	                        if(rec.get('renovable') == 'S'){
    	                       		Ext.getCmp('switch-renovable').setValue(true);
    	                       		Ext.getCmp('switch-renovable').setRawValue("S");    	                       		
	                        }else{
                           			Ext.getCmp('switch-renovable').setValue(false);
                           			Ext.getCmp('switch-renovable').setRawValue("N");    	                       		
	                        }
	                        if(rec.get('vidaEntera') == 'S'){
    	                       		Ext.getCmp('switch-vidaEntera').setValue(true);
    	                       		Ext.getCmp('switch-vidaEntera').setRawValue("S");    	                       		
	                        }else{
                           			Ext.getCmp('switch-vidaEntera').setValue(false);
                           			Ext.getCmp('switch-vidaEntera').setRawValue("N");    	                       		
	                        }                            
                           });
                           storeProducto.load();
	});
	*/
    ds.load({params:{start:0, limit:25}});
    store.load({params:{start:0, limit:25}}); 
    tab2.render('center');
    
});
</script>