<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->
<jsp:include page="/resources/scripts/jsp/utilities/rol/catalogo/atributosVariablesPersona.jsp" flush="true" />
<jsp:include page="/resources/scripts/jsp/utilities/atributosVariables/listasDeValores/listasDeValores.jsp" flush="true" />
<jsp:include page="/resources/scripts/jsp/utilities/expresiones/expresiones.jsp" flush="true" />
<script type="text/javascript">
   seleccionaAtributosVariables = function(store,row) {
	var edita=0;
	var formPanel;
	//alert("row"+row);
if(row!=null){
	edita=1;
    var rec = store.getAt(row);
 
   store.on('load', function(){ 
   						                        
                           
                           Ext.getCmp('atributo-rol-form').getForm().loadRecord(rec);
                           //alert("desc"+ rec.get('descripcionAtributoVariable'));
                           
                           //alert(Ext.getCmp('hidden-atributo-variable-rol').getValue());                                  
                           
                           if(rec.get('switchObligatorio')=="S"){
                				Ext.getCmp('switch-obligatorio-rol').setValue(true);
                				Ext.getCmp('switch-obligatorio-rol').setRawValue("S");
			                }else{
            			    	Ext.getCmp('switch-obligatorio-rol').setValue(false);
                				Ext.getCmp('switch-obligatorio-rol').setRawValue("N");
                			}
                           //**********
                           if(rec.get('apareceCotizador')=="S"){
                				Ext.getCmp('aparece-cotizador-check-atributos-rol').setValue(true);
                				Ext.getCmp('aparece-cotizador-check-atributos-rol').setRawValue("S");
			                }else{
            			    	Ext.getCmp('aparece-cotizador-check-atributos-rol').setValue(false);
                				Ext.getCmp('aparece-cotizador-check-atributos-rol').setRawValue("N");
                			}
                			if(rec.get('modificaCotizador')=="S"){
                				Ext.getCmp('modificable-cotizador-check-atributos-rol').setValue(true);
                				Ext.getCmp('modificable-cotizador-check-atributos-rol').setRawValue("S");
			                }else{
            			    	Ext.getCmp('modificable-cotizador-check-atributos-rol').setValue(false);
                				Ext.getCmp('modificable-cotizador-check-atributos-rol').setRawValue("N");
                			}
                			if(rec.get('datoComplementario')=="S"){
                				Ext.getCmp('complementario-check-atributos-rol').setValue(true);
                				Ext.getCmp('complementario-check-atributos-rol').setRawValue("S");
                			}else{
                				Ext.getCmp('complementario-check-atributos-rol').setValue(false);
                				Ext.getCmp('complementario-check-atributos-rol').setRawValue("N");
                			}               
                			if(rec.get('obligatorioComplementario')=="S"){
                				Ext.getCmp('complementario-obligatorio-check-atributos-rol').setValue(true);
                				Ext.getCmp('complementario-obligatorio-check-atributos-rol').setRawValue("S");
                			}else{
                				Ext.getCmp('complementario-obligatorio-check-atributos-rol').setValue(false);
                				Ext.getCmp('complementario-obligatorio-check-atributos-rol').setRawValue("N");
                			}
                			if(rec.get('modificableComplementario')=="S"){
                				Ext.getCmp('complementario-modificable-check-atributos-rol').setValue(true);
                				Ext.getCmp('complementario-modificable-check-atributos-rol').setRawValue("S");
                			}else{
                				Ext.getCmp('complementario-modificable-check-atributos-rol').setValue(false);
                				Ext.getCmp('complementario-modificable-check-atributos-rol').setRawValue("N");
                			}
                			if(rec.get('apareceEndoso')=="S"){
                				Ext.getCmp('aparece-endoso-check-atributos-rol').setValue(true);
                				Ext.getCmp('aparece-endoso-check-atributos-rol').setRawValue("S");
                			}else{
                				Ext.getCmp('aparece-endoso-check-atributos-rol').setValue(false);
                				Ext.getCmp('aparece-endoso-check-atributos-rol').setRawValue("N");
                			}
                			if(rec.get('obligatorioEndoso')=="S"){
                				Ext.getCmp('obligatorio-endoso-check-atributos-rol').setValue(true);
                				Ext.getCmp('obligatorio-endoso-check-atributos-rol').setRawValue("S");
                			}else{
                				Ext.getCmp('obligatorio-endoso-check-atributos-rol').setValue(false);
                				Ext.getCmp('obligatorio-endoso-check-atributos-rol').setRawValue("N");
                			}
                            if(rec.get('modificaEndoso')=="S"){
                				Ext.getCmp('modificable-endoso-check-atributos-rol').setValue(true);
                				Ext.getCmp('modificable-endoso-check-atributos-rol').setRawValue("S");
                			}else{
                				Ext.getCmp('modificable-endoso-check-atributos-rol').setValue(false);
                				Ext.getCmp('modificable-endoso-check-atributos-rol').setRawValue("N");
                			}
                           //**********
                           Ext.getCmp('hidden-atributo-variable-rol').setValue(rec.get('codigoAtributoVariable'));
                           Ext.getCmp('combo-atributo-variable-rol').disable();
                           Ext.getCmp('hide-button').hide();
                           Ext.getCmp('hidden-edita-rol').setValue(edita);
                           dsCatalogoAtributos.load();
                           ds.load();
						
                           });
	store.load(); 	
                                                 
}
    // pre-define fields in the form
	var dsCatalogoAtributos = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url:'rol/CatalogoDeAtributosVariables.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'catalogoAtributosVariables',
            id: 'comboAtributosVariables'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}       
        ]),
		remoteSort: true
    });
    dsCatalogoAtributos.load(); 
	
	 var atributosVariablesCombo = new Ext.form.ComboBox({
    		tpl: '<tpl for="."><div ext:qtip="{key}" class="x-combo-list-item">{value}</div></tpl>',
	 		store: dsCatalogoAtributos,
			displayField:'value',
			id:'combo-atributo-variable-rol',
			listWidth: '230',
			valueField: 'key',	    				
			typeAhead: true,
	    	mode: 'local',
			triggerAction: 'all',
			emptyText:'<s:text name="productos.configRoles.select.atribVariable"/>',
    		selectOnFocus:true,
   			fieldLabel: '<s:text name="productos.configRoles.atributoVariable"/>',
   			allowBlank:false,
   			blankText : '<s:text name="productos.configRoles.atributoVariable.req"/>',
			name:"descripcionAtributoVariable",
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
				Ext.getCmp('hidden-atributo-variable-rol').setValue(valor);
			}
    			    			
	}); 
	
	var ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'atributosVariables/ListaValoresAtributos.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'valoresDeAtributos',
            id: 'valoresDeAtributos'
	        },[
           {name: 'codigoTabla', type: 'string',mapping:'cdTabla'},
           {name: 'descripcionTabla', type: 'string',mapping:'dsTabla'}    
        ]),
		remoteSort: true
    });
       ds.setDefaultSort('valoresDeAtributos', 'desc'); 
       ds.load();
       
	var comboWithTooltip = new Ext.form.ComboBox({
		id:'id-descripcion-tabla-combo',
    	tpl: '<tpl for="."><div ext:qtip="{codigoTabla}" class="x-combo-list-item">{descripcionTabla}</div></tpl>',
    	store: ds,
    	displayField:'descripcionTabla',
    	valueField:'codigoTabla',
    	//allowBlank:false,
    	//blankText : '<s:text name="productos.configRoles.listaValor.req"/>',
    	//maxLength : '30',
    	//maxLengthText : 'Treinta caracteres maximo',
    	typeAhead: true,
    	mode: 'local',
    	triggerAction: 'all',
    	emptyText:'<s:text name="productos.configRoles.select.listaValor"/>',
    	selectOnFocus:true,
    	fieldLabel: '<s:text name="productos.configRoles.listaValores"/>',
    	listWidth: 200,
    	name:"descripcionListaDeValores",
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
			var valor=record.get('codigoTabla');
			Ext.getCmp('hidden-lista-valores-rol').setValue(valor);
		}
	});                
	
	var obligatorioCheck = new Ext.form.Checkbox({
        //fieldLabel: '<s:text name="productos.configRoles.oblig"/>',
        boxLabel: '<s:text name="productos.configRoles.oblig"/>',
        hideLabel:true,	 
        checked:false,       
        id:'switch-obligatorio-rol',
        name: 'obligatorio',
        onClick:function(){
					if(this.getValue()){
				    	this.setRawValue("S");				            	
				    }
		}
    });  
    
    //**************
    var apareceCotizadorCheck= new Ext.form.Checkbox({
    			id:'aparece-cotizador-check-atributos-rol',
                name:'apareceCotizador',
                boxLabel: '<s:text name="productos.configRoles.apareceCotizador"/>',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
            });
            
    var modificableCotizadorCheck= new Ext.form.Checkbox({
    			id:'modificable-cotizador-check-atributos-rol',
                name:'modificaCotizador',
                boxLabel: '<s:text name="productos.configRoles.modificaCotizador"/>',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
            });        
            
    var complementarioCheck= new Ext.form.Checkbox({
    			id:'complementario-check-atributos-rol',
                name:'datoComplementario',
                boxLabel: '<s:text name="productos.configRoles.complementario"/>',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
            });                 

	var obligaComplementarioCheck= new Ext.form.Checkbox({
    			id:'complementario-obligatorio-check-atributos-rol',
                name:'obligatorioComplementario',
                boxLabel: '<s:text name="productos.configRoles.obligatorioComplementario"/>',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
            });    
            
     var modificableComplementarioCheck= new Ext.form.Checkbox({
    			id:'complementario-modificable-check-atributos-rol',
                name:'modificableComplementario',
                boxLabel: '<s:text name="productos.configRoles.modificableComplementario"/>',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
            });  
            
      var apareceEndosoCheck= new Ext.form.Checkbox({
    			id:'aparece-endoso-check-atributos-rol',
                name:'apareceEndoso',
                boxLabel: '<s:text name="productos.configRoles.apareceEndoso"/>',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
            });     
            
       var obligatorioEndosoCheck= new Ext.form.Checkbox({
    			id:'obligatorio-endoso-check-atributos-rol',
                name:'obligatorioEndoso',
                boxLabel: '<s:text name="productos.configRoles.obligatorioEndoso"/>',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
            });

		var modificableEndosoCheck= new Ext.form.Checkbox({
    			id:'modificable-endoso-check-atributos-rol',
                name:'modificaEndoso',
                boxLabel: '<s:text name="productos.configRoles.modificaEndoso"/>',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
            });            
            
    //**************    
    	formPanel = new Ext.form.FormPanel({
  		
  		frame:true,
        baseCls: 'x-plain',
        labelWidth: 115,
        id: 'atributo-rol-form',
        //bodyStyle:'padding:5px',
        url:'rol/AgregaAtributoVariableRol.action',
        //defaultType: 'textfield',
        //collapsed : false,
		items: [{xtype:'hidden',id:'hidden-atributo-variable-rol',name:'codigoAtributoVariable'},
				{xtype:'hidden',id:'hidden-lista-valores-rol',name:'codigoListaDeValores'},
				{xtype:'hidden',id:'hidden-codigo-expresion-rol-ventana',name:'codigoExpresion'},
				{xtype:'hidden',id:'hidden-codigo-expresion-session-rol-ventana',name:'codigoExpresionSession', value:'EXPRESION_ATRIBUTO_VARIABLE_ROL'},
				{xtype:'hidden',id:'hidden-edita-rol',name:'edita'},
				{
        			layout:'column',
        			border: false,
			        baseCls: 'x-plain',
        			
            				items:[{
				                columnWidth:.7,
                				layout: 'form',
                				border: false,
                				baseCls: 'x-plain',
        
				                items: [
				                	atributosVariablesCombo
				                ]
        					},{
                				columnWidth:.3,
				                layout: 'form',
				                border: false,
				                baseCls: 'x-plain',
        
                				items: [{
				                    xtype:'button',
				                    text: '<s:text name="productos.configRoles.btn.agregarCatalogo"/>',
                				    //name: 'AgregarAlCatalogo',
				                    buttonAlign: "right",
                				    id:'hide-button',
            						handler: function(){
            							creaAtributosVariables(dsCatalogoAtributos);
            						}	
				                }]
				            }]
        		
		        },
					{
        			layout:'column',
        			border: false,
			        baseCls: 'x-plain',
        			
            				items:[{
				                columnWidth:.7,
                				layout: 'form',
                				border: false,
                				baseCls: 'x-plain',
        
				                items: [
				                	comboWithTooltip
				                ]
        					},{
                				columnWidth:.3,
				                layout: 'form',
				                border: false,
				                baseCls: 'x-plain',
        
                				items: [{
				                    xtype:'button',
				                    text: '<s:text name="productos.configRoles.btn.agregarCatalogo"/>',
				                    width:30,
				                    id:'hide-button-lista',
                				    //name: 'AgregarAlCatalogo',
				                    buttonAlign: "right",
                				    handler: function() {                    	
				             			creaListasDeValores(ds);//window listasDeValores
               			   				
         							}		
				                }]
				            }]
        		
		        },{
        			layout:'column',
        			border: false,
			        baseCls: 'x-plain',
        			
            				items:[{
				                columnWidth:.5,
                				layout: 'form',
                				border: false,
                				baseCls: 'x-plain',
        
				                items: [
				                	obligatorioCheck,
            						apareceCotizadorCheck,
            						modificableCotizadorCheck,
            						complementarioCheck,
									obligaComplementarioCheck
				                ]
        					},{
                				columnWidth:.5,
				                layout: 'form',
				                border: false,
				                baseCls: 'x-plain',
        
                				items: [
                					modificableComplementarioCheck,
									apareceEndosoCheck,
									obligatorioEndosoCheck,
									modificableEndosoCheck
                				]
				            }]
        		
		        }
		        /*
            obligatorioCheck,
            apareceCotizadorCheck,
            modificableCotizadorCheck,
            complementarioCheck,
			obligaComplementarioCheck,
			modificableComplementarioCheck,
			apareceEndosoCheck,
			obligatorioEndosoCheck,
			modificableEndosoCheck*/
    	]
    });

    // define window and show it in desktop
    var atributosWindow = new Ext.Window({
        title: '<s:text name="productos.configRoles.title.atribroles"/>',
        width: 450,
        height:240,
        //minWidth: 300,
        //minHeight: 150,
        layout: 'fit',
        plain:true,
        //bodyStyle:'padding:5px;',
        buttonAlign:'center',
        modal : true,
  
        items: formPanel,
        

		buttons: [{
            text: '<s:text name="productos.configRoles.btn.guardar"/>', 
            handler: function() {
                // check form value 
                if (formPanel.form.isValid()) {
                //Ext.getCmp('hidden-lista-valores-rol').setValue(Ext.getCmp('id-descripcion-tabla-combo').getValue());
                //Ext.getCmp('hidden-atributo-variable-rol').setValue(Ext.getCmp('combo-atributo-variable').getValue());
                //alert('rol/AgregaAtributoVariableRol.action?edita='+edita);
	 		        formPanel.form.submit({		
	 		        	url:'rol/AgregaAtributoVariableRol.action?edita='+edita,	      
			            waitTitle:'<s:text name="ventana.configRoles.proceso.mensaje.titulo"/>',
					    waitMsg:'<s:text name="ventana.configRoles.proceso.mensaje"/>',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('Status', 'Atributo variable no agregado');
						},
						success: function(form, action) {
						    //Ext.MessageBox.alert('Status', 'Atributo Variable agregado');
						    store.load();
						    atributosWindow.close();
						    
						    
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Error', 'Favor de llenar los campos requeridos.');
				}             
	        }
        },{
            text: '<s:text name="productos.configRoles.btn.cancelar"/>',
            handler: function(){atributosWindow.close();}
        },{
            text:'<s:text name="productos.configRoles.btn.valDefectoPoliza"/>',
            tooltip:'Valor por defecto del rol',
            handler:function(){
            	if(Ext.getCmp('hidden-codigo-expresion-rol-ventana').getValue()!=null 
	            		&& Ext.getCmp('hidden-codigo-expresion-rol-ventana').getValue()!=""
	            		&& Ext.getCmp('hidden-codigo-expresion-rol-ventana').getValue()!="0"
	            		&& Ext.getCmp('hidden-codigo-expresion-rol-ventana').getValue()!="undefined"){
	            			ExpresionesVentana2(Ext.getCmp('hidden-codigo-expresion-rol-ventana').getValue(), Ext.getCmp('hidden-codigo-expresion-session-rol-ventana').getValue());
	            		}else{
	            			var connect = new Ext.data.Connection();
						    connect.request ({
								url:'atributosVariables/ObtenerCodigoExpresion.action',
								callback: function (options, success, response) {				   
									//alert(Ext.util.JSON.decode(response.responseText).codigoExpresion);
									Ext.getCmp('hidden-codigo-expresion-rol-ventana').setValue(Ext.util.JSON.decode(response.responseText).codigoExpresion);
									ExpresionesVentana2(Ext.getCmp('hidden-codigo-expresion-rol-ventana').getValue(),Ext.getCmp('hidden-codigo-expresion-session-rol-ventana').getValue());
								}
					   		});
	            			
	            				            		
	            		}
            }
        }]
    });

	atributosWindow.show();
};

</script>