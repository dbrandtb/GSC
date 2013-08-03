<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->
<jsp:include page="/resources/scripts/jsp/utilities/coberturas/catalogo/agregarCoberturas.jsp" flush="true" />

<script type="text/javascript">
Ext.onReady(function(){
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = "side";



var sumaAseguradaCheck= new Ext.form.Checkbox({
				id:"suma-asegurada-check",
                name:'sumaAsegurada',
                boxLabel: '<s:text name="config.coberturas.sumaAsegurada"/>',
            	hideLabel:true,	
                //checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");
				            	Ext.getCmp('hide-form-suma').show();
				            	
				            	}if(!this.getValue()){
				            	Ext.getCmp('hide-form-suma').hide();				            	
				            	
				            	}
				            }
                 
            });
           
var obligatorioCheck= new Ext.form.Checkbox({
                id:'obligatorio-check-cobertura',
                name:'obligatorio',
                boxLabel: '<s:text name="config.coberturas.obligatorio"/>',
            	hideLabel:true,			
                //checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
            });  
            
var insertaCheck= new Ext.form.Checkbox({
                id: 'inserta-check-cobertura',
                name:'inserta',
                boxLabel: '<s:text name="config.coberturas.inserta"/>',
            	hideLabel:true,			
                //checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");
				            	Ext.getCmp('hide-form-condicion').show();
				            	
				            	}if(!this.getValue()){
				            	Ext.getCmp('hide-form-condicion').hide();				            	
				            	
				            	}
				            }
                 
            });
            
var dsComboSumaAsegurada = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'coberturas/CargaCombosCoberturas.action'+'?combo='+'sumaAsegurada'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaSumaAsegurada',
            id: 'comboSumaAsegurada'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}      
        ]),
		remoteSort: true
    });
  	//dsComboSumaAsegurada.load();
  	
var comboSumaAsegurada =new Ext.form.ComboBox({
							id:'combo-suma-asegurada-allowBlank',
    	                    tpl: '<tpl for="."><div ext:qtip="{key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsComboSumaAsegurada,						
						    displayField:'value',
						    listWidth: '250',
						    //allowBlank: false,
						    blankText : '<s:text name="req.config.coberturas.valida.sumaAseg"/>',
						    valueField: 'key',					    	
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="config.coberturas.select.sumaAsegurada"/>',
					    	selectOnFocus:true,
						    //fieldLabel: "Suma",
						    labelSeparator:'',
					    	name:"descripcionCapital",
					    	selectFirst : function() {
         						this.focusAndSelect(this.store.data.first());
       						},
          					focusAndSelect : function(record) {
         						var index = typeof record === 'number' ? record : this.store.indexOf(record);
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
								var valor=record.get('key');
								Ext.getCmp('hidden-combo-suma-asegurada').setValue(valor);
							}
					    	
			});

var dsComboCondicion = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'coberturas/CargaCombosCoberturas.action'+'?combo='+'condicion'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaCondicionCobertura',
            id: 'comboCondicion'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}      
        ]),
		remoteSort: true
    });
  	//dsComboCondicion.load();
  	
var comboCondicion =new Ext.form.ComboBox({
    	                    id:'combo-condicion-allowBlank',
    	                    tpl: '<tpl for="."><div ext:qtip="{key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsComboCondicion,
							//disabled:true,
							typeAhead: true,
							listWidth: '250',
							labelAlign: 'left',	
							anchor: '90%', 					
						    displayField:'value',
						    //allowBlank:false,
						    //blankText : '<s:text name="req.config.coberturas.valida.condicion"/>',
						    valueField: 'key',					    	
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="config.coberturas.select.condicion"/>',
					    	selectOnFocus:true,
						    fieldLabel: '<s:text name="config.coberturas.condicion"/>',
						    //labelSeparator:'',
					    	name:"descripcionCondicion",
					    	selectFirst : function() {
         						this.focusAndSelect(this.store.data.first());
       						},
          					focusAndSelect : function(record) {
         						var index = typeof record === 'number' ? record : this.store.indexOf(record);
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
								var valor=record.get('key');
								Ext.getCmp('hidden-combo-condicion').setValue(valor);
							}			    	
			});
			                                                              

var dsComboCobertura = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'coberturas/CargaCombosCoberturas.action'+'?combo='+'cobertura'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaCoberturas',
            id: 'comboCobertura'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}      
        ]),
		remoteSort: true
    });
  	//dsComboCobertura.load();
  	
var comboCobertura =new Ext.form.ComboBox({
							id:'combo-cobertura-valor',
    	                    tpl: '<tpl for="."><div ext:qtip="{key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsComboCobertura,						
						    displayField:'value',
						    listWidth: '250',
						    allowBlank:false,
						    blankText : '<s:text name="req.config.coberturas.valida.cobertura"/>',
						    valueField: 'key',					    	
						    mode: 'local',
					    	triggerAction: 'all',						    
					    	selectOnFocus:true,
					    	emptyText: '<s:text name="config.coberturas.select.cobertura"/>',
						    fieldLabel: '<s:text name="config.coberturas.cobertura"/>',
						    //labelSeparator:'',
					    	name:"descripcionCobertura",
					    	selectFirst : function() {
         						this.focusAndSelect(this.store.data.first());
       						},
          					focusAndSelect : function(record) {
         						var index = typeof record === 'number' ? record : this.store.indexOf(record);
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
								var valor=record.get('key');
								Ext.getCmp('hidden-combo-cobertura').setValue(valor);
							}
					    	
			});
            var coberturaForm = new Ext.form.FormPanel({
            	id:'cobertura-form',
            	boder:false,
            	frame:false,
            	labelAlign: 'left',
            	url:'coberturas/AsociaCobertura.action',
            	labelWidth: 60,
        		title: '<s:text name="config.coberturas.titulo"/>',
       			bodyStyle:'padding:5px',
        		width:465,
        		height:120, 
        		buttonAlign: "center",
                items: [{xtype:'hidden',id:'hidden-combo-cobertura',name:'claveCobertura'},new Ext.Panel({
										id: 'details-panel-forma-coberturas',
								        border:false,	
								        heigth:'50'
					    	}),{
                	layout:'form',
                	id:'id-hidden-form-coberturas',
                	border:false,
                	items:[{
        				layout:'column',
	        			border: false,
            				items:[{
				                columnWidth:.9,
                				layout: 'form',
                				border: false,
                				width:250,
				                items: [
				                	comboCobertura
				                ]
        					},{
				                columnWidth:.1,
                				layout: 'form',
                				border: false,
				                items: [{
				                    xtype:'button',
				                    text: '<s:text name="config.coberturas.btn.agregarCatalogo"/>',
                				    name: 'AgregarAlCatalogo',
				                    //buttonAlign: "left",
                				    handler: function() {                    	
				               			creaAltaCatalogoCoberturas(dsComboCobertura);//script que tienes afuera
               			   				
         							}	
				                }]
        					}]        		
		        	}]
		        },{
        			layout:'column',
        			border: false,
            				items:[{
				                columnWidth:.4,
                				layout: 'form',
                				border: false,
				                items: [
				                	sumaAseguradaCheck
				                ]
        					},{
				                columnWidth:.1,
                				layout: 'form',
                				width:70,
                				labelAlign:'right',
                				border: false,
				                items: [
				                	{
                    				xtype:'textfield',                    				
                    				labelSeparator:'',
                    				hidden:true,
                    				name: 'first',
                    				anchor:'95%'
                					}
				                ]
        					},{
				                columnWidth:.5,
                				layout: 'form',                				
                				width:'260',
                				labelAlign:'right',
                				border: false,
				                items: [
				                	{layout:'form',
				                	id:'hide-form-suma',
				                	border:false,
				                	items:[{xtype:'hidden',id:'hidden-combo-suma-asegurada',name:'codigoCapital'},
				                	comboSumaAsegurada
				                	]}
				                ]
        					}]
        		
		        },
		        
		        obligatorioCheck,{
        			layout:'column',
        			border: false,
            				items:[{
				                columnWidth:.4,
                				layout: 'form',
                				border: false,
				                items: [
				                	insertaCheck
				                ]
        					},{
				                columnWidth:.1,
                				layout: 'form',
                				width:'116',
                				labelAlign:'right',
                				border: false,
				                items: [
				                	{
                    				xtype:'textfield',                    				
                    				labelSeparator:'',
                    				hidden:true,
                    				name: 'first',
                    				anchor:'95%'
                					}
				                ]
        					},{
				                columnWidth:.5,
                				layout: 'form',                				
                				width:'260',
                				labelAlign:'right',
                				border: false,
				                items: [
				                	{layout:'form',
				                	id:'hide-form-condicion',
				                	border: false,
				                	items:[{xtype:'hidden',id:'hidden-combo-condicion',name:'codigoCondicion'},
				                	comboCondicion
				                	]}
				                ]
        					}]
        		
		        }],buttons: [{
	            	text: '<s:text name="config.coberturas.btn.asociar"/>',
	            	handler: function() {
                	// check form value
                		var bandera =true;
                		//alert(Ext.getCmp('hidden-combo-cobertura').getValue());
                		if(Ext.getCmp('suma-asegurada-check').getValue()){	
                			if(Ext.getCmp('combo-suma-asegurada-allowBlank').getValue()==0){
                				Ext.getCmp('combo-suma-asegurada-allowBlank').markInvalid();
                				bandera=false;
                			}	
                		}
                		if(Ext.getCmp('inserta-check-cobertura').getValue()){	                			
                			if(Ext.getCmp('combo-condicion-allowBlank').getValue()==0){
                				Ext.getCmp('combo-condicion-allowBlank').markInvalid();
                				bandera=false;
                			}	
                		}
                		
                			 
                		if (coberturaForm.form.isValid() && bandera) {
                			
		 		        	coberturaForm.form.submit({			      
					            	waitTitle:'<s:text name="ventana.configCobertura.proceso.mensaje.titulo"/>',
					            	waitMsg:'<s:text name="ventana.configCobertura.proceso.mensaje"/>',
					            	failure: function(form, action) {
						   				Ext.MessageBox.alert('Status', 'Cobertura no asociada');
									},
									success: function(form, action) {
						    			Ext.MessageBox.alert('Status', 'Cobertura Asociada');
						    			if(Ext.getCmp('id-hidden-form-coberturas').isVisible()){
						    				//alert('is visible');
						    				coberturaForm.form.reset();	
						    				Ext.getCmp('suma-asegurada-check').setValue(false);
						    				Ext.getCmp('inserta-check-cobertura').setValue(false);
						    			}
						    			
						    			Ext.getCmp('hide-form-condicion').hide();	   
										Ext.getCmp('hide-form-suma').hide();				    			
			    						Ext.getCmp('arbol-productos').getRootNode().reload();
						}
			        		});                   
                		} else{
					Ext.MessageBox.alert('Error', 'Favor de llenar campos requeridos');
						}             
	        		}
    		    },{
	            	text: '<s:text name="config.coberturas.btn.eliminar"/>'
    		    }]
    	});    	    	
	Ext.getCmp('hide-form-condicion').hide();	   
	Ext.getCmp('hide-form-suma').hide();
	Ext.getCmp('details-panel-forma-coberturas').hide();
	coberturaForm.render('centerCoberturas');
    });



</script>