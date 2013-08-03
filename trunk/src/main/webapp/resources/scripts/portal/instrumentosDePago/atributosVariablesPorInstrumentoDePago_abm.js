function agregarAtributoVariable(cdInsCte, record, storeGridAtr){

var dataStorePadre;
var tituloForm;

if(record){
	tituloForm = '<span style="color:black;font-size:12px;">'+getLabelFromMap('',helpMap,'Editar Atributo Variable por Instrumentos de Pago')+'</span>'
}else{
	tituloForm = '<span style="color:black;font-size:12px;">'+getLabelFromMap('',helpMap,'Agregar Atributo Variable por Instrumentos de Pago')+'</span>'
}


    var banderaSelectCombo = false;    
    var ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: _ACTION_COMBO_LISTA_VALORES
        }),
        reader: new Ext.data.JsonReader({
            root: 'valoresDeAtributos',
            id: 'valoresDeAtributos'
	        },[
           {name: 'codigoTabla', type: 'string',mapping:'cdTabla'},
           {name: 'descripcionTabla', type: 'string',mapping:'dsTabla'}
        ]),
        baseParams:{limit:'-1'},
		remoteSort: true
    });
       ds.setDefaultSort('valoresDeAtributos', 'desc'); 
       
    var dataStoreCondiciones = new Ext.data.Store({
    	proxy: new Ext.data.HttpProxy({
				url: _ACTION_COMBO_CONDICIONES + '?numeroGrid='+'3'	
        		}),
        		reader: new Ext.data.JsonReader({
            	root:'listaReglaNegocioCondiciones',   
            	id: 'cond'
	        	},[
	        		{name: 'nombreCabecera',  		type: 'string',  	mapping:'nombre'},
	        		{name: 'descripcionCabecera',  	type: 'string',  	mapping:'descripcion'}
			]),
			remoteSort: true
    });  
 		dataStoreCondiciones.setDefaultSort('cond', 'desc');
       
    	dataStorePadre = new Ext.data.Store({
    	proxy: new Ext.data.HttpProxy({
				url: _ACTION_COMBO_PADRE
        		}),
        		reader: new Ext.data.JsonReader({
            	root:'listaPadre',   
            	id: 'pat'
	        	},[
	        		{name: 'clavePadre',  			type: 'string',  	mapping:'key'},
	        		{name: 'descripcionPadre',  	type: 'string',  	mapping:'value'}
			]),
			baseParams:{codigoAtributoP:'',cdTipSituacion:''},
			remoteSort: true
    });  
 		dataStorePadre.setDefaultSort('pat', 'desc');     
       
       
    var comboWithTooltip = new Ext.form.ComboBox({    		
    		tpl: '<tpl for="."><div ext:qtip="{codigoTabla}" class="x-combo-list-item">{descripcionTabla}</div></tpl>',
    		store: ds,
    		forceSelection: true,
    		displayField:'descripcionTabla',
    		valueField :'codigoTabla',
	    	id: 'combo-lista-de-valores-atributos',
    		typeAhead: true,
    		mode: 'local',
    		triggerAction: 'all',
    		emptyText:'Seleccione un valor',
    		selectOnFocus:true,
    		fieldLabel: 'Lista de Valores',
    		listWidth: '250',
    		name:"descripcionTabla",
    		listeners: {
    			select: function(){
    				banderaSelectCombo = true;
	    			Ext.getCmp('hidden-lista-valores-atributo').setValue(Ext.getCmp('combo-lista-de-valores-atributos').getValue());
    			}
    		}
	});
	
	var comboCondicion = new Ext.form.ComboBox({
				id:'combo-condicion-atributos-variables-arbol',
				tpl: '<tpl for="."><div ext:qtip="{nombreCabecera}" class="x-combo-list-item">{descripcionCabecera}</div></tpl>',
    			store : dataStoreCondiciones, 
				listWidth: '250',
    			mode: 'local',
				hiddenName: 'condicion',
    			typeAhead: true,
    			triggerAction: 'all',    		
    			displayField:'descripcionCabecera',
    			valueField :'nombreCabecera',
				fieldLabel: 'Condici\u00F3n',
				emptyText:'Seleccione una Condici\u00F3n',
				selectOnFocus:true
				
	});

	var comboPadre = new Ext.form.ComboBox({  
				id:'combo-padre-atributos-variables-arbol',
				tpl: '<tpl for="."><div ext:qtip="{clavePadre}" class="x-combo-list-item">{descripcionPadre}</div></tpl>',
    			store: dataStorePadre, 
				listWidth: '250',
    			mode: 'local',
				hiddenName: 'padre',
    			typeAhead: true,
    			triggerAction: 'all',
    			displayField:'descripcionPadre',
    			valueField :'clavePadre',
				fieldLabel: 'Padre',
				emptyText:'Seleccione un Padre',
				selectOnFocus:true,
				listeners:{
					collapse: function(){
								validaComboPadre();						
					}
				}
		});     
  
		
		//CARGA DE LOS STORES//
		
		dataStorePadre.load({
			params: {
				cdInsCte: cdInsCte,
				codigoAtributo: record? record.get('cdAtribu'):''
			}
		});
		dataStoreCondiciones.load();
		ds.load();
		
		
		function validaComboPadre(){
				var seccion = Ext.getCmp('combo-padre-atributos-variables-arbol').getValue();
	    		
	              		if(seccion.length==0 || seccion == 'Seleccione un Padre'){
    		            	Ext.getCmp('hidden-forma-padre-condicion-orden-agrupador').hide();
    		            	Ext.getCmp('hidden-forma-padre-condicion-orden-agrupador-boton').hide(); 
    		            	Ext.getCmp('forma-boton-vacia').hide(); 
    		            	Ext.getCmp('forma-boton-catalogo-vacia').show();
    		            	Ext.getCmp('combo-condicion-atributos-variables-arbol').reset();
    		            	Ext.getCmp('id-hidden-text-orden').reset();
    		            	Ext.getCmp('id-hidden-text-agrupador').reset();
	               		}else{
	               			Ext.getCmp('hidden-forma-padre-condicion-orden-agrupador').show();
    		            	Ext.getCmp('hidden-forma-padre-condicion-orden-agrupador-boton').show();
    		            	Ext.getCmp('forma-boton-vacia').show();
    		            	Ext.getCmp('forma-boton-catalogo-vacia').hide();
    		            	
	               		}
		}
            
		
	function validaValorMinMax( valorMin , valorMax ){
  		if( valorMin > valorMax ){
            Ext.MessageBox.alert('Error','el valor M\u00EDnimo debe de ser menor al valor M\u00E1ximo');
            return 0;
        }else{
        	return 1;
        }	
    }		
			
    var hideTextMaximo= new Ext.form.NumberField({
				            id:'id-hidden-text-maximo',
            				name:'maximo',
            				fieldLabel: 'M\u00E1ximo',
            				allowDecimals : false,
				            allowNegative : false,
        				    allowBlank: false,
        				    blankText : 'Dato Num\u00E9rico Requerido',            				
            				hideParent:true,
            				width:25	
            							       
       });
           
     var hideTextMinimo= new Ext.form.NumberField({
				            id:'id-hidden-text-minimo',
            				name:'minimo',
            				fieldLabel: 'M\u00EDnimo',
            				allowDecimals : false,
				            allowNegative : false,
        				    allowBlank: false,
        				    blankText : 'Dato Num\u00E9rico Requerido',            				
            				hideParent:true,
            				width:25	
            							       
	});
           
      var hideOrden = new Ext.form.NumberField({
      						id:'id-hidden-text-orden',
      						name:'orden',
      						fieldLabel:'Orden',
      						allowDecimals : false,
				            allowNegative : false,
        				    blankText : 'Dato Num\u00E9rico Requerido',				
            				width:160
	});
      	   
      var hideAgrupador = new Ext.form.NumberField({
      						id:'id-hidden-text-agrupador',
      						name:'agrupador',
      						fieldLabel:'Agrupador',
      						allowDecimals : false,
				            allowNegative : false,
        				    blankText : 'Dato Num\u00E9rico Requerido',            				
            				width:160
	});
           
     var functionRadioAlfa= new Ext.form.Radio({
     						id:'function-radio-alfa-atributos-variables',
            				name:'formato',
            				fieldLabel:'Formato',
            				checked: true,
            				boxLabel: 'Alfanum\u00E9rico',
  	  	       				onClick: function(){			            		
            						hideTextMaximo.enable();
            						hideTextMinimo.enable();
            						Ext.getCmp('id-hidden-text-maximo').setValue('');
            						Ext.getCmp('id-hidden-text-minimo').setValue('');
            						Ext.getCmp('hidden-radio-atributos-variables').setValue("A");
				            	
			            	}
	});   
            
    var functionRadioNumeric= new Ext.form.Radio({
    						id:'function-radio-numeric-atributos-variables',
            				name:'formato',
            				labelSeparator : "",
            				boxLabel: 'Num\u00E9rico',
            				hideLabel:true,
  	  	       				onClick: function(){			            		
            						hideTextMaximo.enable();
            						hideTextMinimo.enable();
            						Ext.getCmp('id-hidden-text-maximo').setValue('');
            						Ext.getCmp('id-hidden-text-minimo').setValue('');
            						Ext.getCmp('hidden-radio-atributos-variables').setValue("N");
				            	
			            	}
	});  
            
    var functionRadioPercent= new Ext.form.Radio({
            				id:'function-radio-percent-atributos-variables',
            				name:'formato',
            				labelSeparator : "",
            				boxLabel: 'Porcentaje',
  	  	       				onClick: function(){
  	  	       						hideTextMaximo.clearInvalid();            						
				            		hideTextMinimo.clearInvalid();
            						hideTextMaximo.disable();            						
				            		hideTextMinimo.disable();
            						hideTextMaximo.getEl.dom.setAttribute('value', '');            						
				            		hideTextMinimo.getEl.dom.setAttribute('value', '');
				            		Ext.getCmp('hidden-radio-atributos-variables').setValue("P");
				            
			            	}
	}); 
            
    var functionRadioDate= new Ext.form.Radio({
            				id:'function-radio-date-atributos-variables',
            				name: 'formato',
				           	boxLabel: 'Fecha',
				           	labelSeparator : "",
				            hideLabel:true,
  	  	       				onClick: function(){	
  	  	       						hideTextMaximo.clearInvalid();        						
				            		hideTextMinimo.clearInvalid();
            						hideTextMaximo.disable();            						
				            		hideTextMinimo.disable();
  	  	       						hideTextMaximo.getEl.dom.setAttribute('value', '');            						
				            		hideTextMinimo.getEl.dom.setAttribute('value', '');
				            		Ext.getCmp('hidden-radio-atributos-variables').setValue("F");
				            	
			            	}
	});                                
      
    var obligatorioCheck= new Ext.form.Checkbox({
    			id:'obligatorio-check-atributos-variables',
                name:'obligatorio',
                boxLabel: 'Obligatorio',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            		this.setRawValue("S");
//				            		Ext.getCmp('hidden-form-check-complementario').hide();
//				            		Ext.getCmp('hidden-form-check-complementario-obligatorio').hide();				            		
//				            		Ext.getCmp('complementario-check-atributos-variables').reset();
//				            		Ext.getCmp('complementario-obligatorio-check-atributos-variables').reset();
//				            		Ext.getCmp('complementario-modificable-check-atributos-variables').reset();
				            		
				            	}else{
//				            		Ext.getCmp('hidden-form-check-complementario').show();
				            	}
				            }
                 
	});
            
    var emisionCheck= new Ext.form.Checkbox({
    			id:'emision-check-atributos-variables',
                name:'modificaEmision',
                boxLabel: 'Modificable en emision',
            	hideLabel:true,			
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
	});        
            
    var endosoCheck= new Ext.form.Checkbox({
    			id:'endoso-check-atributos-variables',
                name:'modificaEndoso',
                boxLabel: 'Modificable en endosos',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
	});
            
    var retarifacionCheck= new Ext.form.Checkbox({
    			id:'retarificacion-check-atributos-variables',
                name:'retarificacion',
                boxLabel:'Interviene en la Tarificaci\u00F3n / Retarificaci\u00F3n',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
	});      
            
    var cotizadorCheck= new Ext.form.Checkbox({
    			id:'cotizador-check-atributos-variables',
                name:'despliegaCotizador',
                boxLabel: 'Se despliega en el cotizador',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
	});
            
	var complementarioCheck= new Ext.form.Checkbox({
    			id:'complementario-check-atributos-variables',
                name:'datoComplementario',
                boxLabel: 'Dato complementario',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");
				            	Ext.getCmp('hidden-form-check-obligatorio').hide();
				          		Ext.getCmp('obligatorio-check-atributos-variables').reset();
//				          		Ext.getCmp('hidden-form-check-complementario-obligatorio').show();
				            	}else{
				            		Ext.getCmp('hidden-form-check-obligatorio').show();
//				            		Ext.getCmp('hidden-form-check-complementario-obligatorio').hide();
//				            		Ext.getCmp('complementario-obligatorio-check-atributos-variables').reset();
//				            		Ext.getCmp('complementario-modificable-check-atributos-variables').reset();
				            	}
				            }
                 
	});                 

	var obligaComplementarioCheck= new Ext.form.Checkbox({
    			id:'complementario-obligatorio-check-atributos-variables',
                name:'obligatorioComplementario',
                boxLabel: 'Dato complementario obligatorio',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
	});    
            
     var modificableComplementarioCheck= new Ext.form.Checkbox({
    			id:'complementario-modificable-check-atributos-variables',
                name:'modificableComplementario',
                boxLabel: 'Dato complementario modificable',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
	});  
            
	var apareceEndosoCheck= new Ext.form.Checkbox({
    			id:'aparece-endoso-check-atributos-variables',
                name:'apareceEndoso',
                boxLabel: 'Aparece en endoso',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
                	if(this.getValue()){
				    	this.setRawValue("S");
				    	Ext.getCmp('hidden-form-check-endoso-obligatorio').show();
					}else{
						Ext.getCmp('hidden-form-check-endoso-obligatorio').hide();
						Ext.getCmp('obligatorio-endoso-check-atributos-variables').reset();
						Ext.getCmp('endoso-check-atributos-variables').reset();
					}
				}
                 
	});     
            
	var obligatorioEndosoCheck= new Ext.form.Checkbox({
    			id:'obligatorio-endoso-check-atributos-variables',
                name:'obligatorioEndoso',
                boxLabel: 'Obligatorio en Endoso',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
            });                    

	var datosVariablesForm = new Ext.form.FormPanel({
            	id:'id-atributos-variables-form',
            	title: tituloForm,
            	frame:true,
            	url: _ACTION_GUARDA_ATRIBUTO,
        		header:true,
       			bodyStyle:'padding:5px; background: white;',
        		width:600,
        		buttonAlign: 'left',
                items: [{xtype:'hidden',id:'hidden-codigo-expresion-session',name:'codigoExpresionSession', value: 'EXPRESION_ATRIBUTOS_VARIABLES_INSTRUMENTO_PAGO'},
                		{xtype:'hidden',id:'hidden-lista-valores-atributo',name:'codigoTabla'},
                {
                	xtype:'hidden',
		            name: "cdInsCte",
		            value: cdInsCte
                },{
                	xtype:'hidden',
		            name: "cdAtribu",
        		    id: "hidden-clave-campo-atributos-variables"
                },{
                	xtype:'hidden',
		            name: "descripcionHidden",
        		    id: "hidden-descripcion-atributos-variables"
                },{
                	xtype:'hidden',
		            name: "codigoRadioAtributosVariables",
        		    id: "hidden-radio-atributos-variables"
                },{
                	xtype:'hidden',
		            name: "formatoHidden",
        		    id: "hidden-formato-atributos-variables"
                },{
                	xtype:'hidden',                	
                	id:'hidden-valor-defecto-atributos-variables',
		            name: "valorDefecto"      		    
        		 
                },{
                	xtype:'hidden',                	
                	id:'hidden-codigo-expresion-atributos-variables',
		            name: "codigoExpresion"      		    
        		 
                },{
        		    xtype:'textfield',
		            name: "descripcion",
		            allowBlank: false,
		            blankText : 'Descripci\u00F3n Requerida',
		            id:'id-descripcion-atributos-variables',
		            maxLength: 120,
        		    fieldLabel: 'Descripci\u00F3n*',
		            width: 210
        		},{
        			layout:'column',
        			border: false,        			
            				items:[{
				                columnWidth:.4,
                				layout: 'form',
                				border: false,                				
				                items: [
				                	functionRadioAlfa
				                ]
        					},{
               					columnWidth:.12,
			                	layout: 'form',
			                	border: false,			                	
               					items: [
               						functionRadioNumeric
               					]
				    		},{
               					columnWidth:.24,
			                	layout: 'form',
			                	labelAlign:"right",
			                	border: false,			                	
               					items: [
               						hideTextMinimo
               					]
				            },{
               					columnWidth:.24,
               					labelAlign:"right",
			                	layout: 'form',
			                	border: false,			                	
               					items: [               						
               						hideTextMaximo
               					]	
				    		}]
        		
		        },{
		        	layout: 'column',
				   	border: false,
				   	items: [{
                				columnWidth:.35,
				                layout:'form',
				                border: false,
				                width:197,				                    	
				                items:[
				                    	functionRadioPercent
				                ]
				             },{
                				columnWidth:.65,
                				layout:'form',
                				border: false,           						
                				items:[
                						functionRadioDate
                				]
				             }
				     ]				           		
				},{
					layout:'form',
		        	id:'hidden-form-check-obligatorio',
		        	border: false,
		        	items:[		        	
        					obligatorioCheck
		        	]     	
				},{
					layout: 'form',
					id: 'form-check-emision-endoso-cotizador',
					border: false,
					items: [
						emisionCheck/*,
						cotizadorCheck*/
					]
				}/*,{
		        	layout:'form',
		        	id:'hidden-form-attributos-coberturas',
		        	border: false,
		        	items:[		        	
        					retarifacionCheck
		        	]     		
				},{
					layout:'form',
		        	id:'hidden-form-check-complementario',
		        	border: false,
		        	items:[		        	
        					complementarioCheck
		        	]     	
				},{
					layout:'form',
		        	id:'hidden-form-check-complementario-obligatorio',
		        	border: false,
		        	items:[		        	        					
        					obligaComplementarioCheck,
							modificableComplementarioCheck
		        	]     	
				}*/,{
					layout: 'form',
					id: 'form-check-aparece-endoso',
					border: false,
					items: [
						apareceEndosoCheck
					]
				},{
					layout:'form',
		        	id:'hidden-form-check-endoso-obligatorio',
		        	border: false,
		        	items:[		        	        					
        				endosoCheck,
						obligatorioEndosoCheck
		        	]
				},{
 				   layout:'column',
 				   border: false,
            				items:[{
				                	columnWidth:.58,
                					layout: 'form',
                					border: false,
                					width:300,
				                	items: [/*{
				                			layout: 'form',
				                			id:'hidden-forma-padre',
                							border: false,
				                				items:[
				                					comboPadre				                					
				                				]
				                			},*/{
				                			layout: 'form',
				                			id:'hidden-forma-padre-condicion-orden-agrupador',
                							border: false,
				                				items:[
				                					hideAgrupador,
				                					hideOrden,
				                					comboCondicion
				                				]
				                			},
				                			{layout: 'form', border: false},
				                			comboWithTooltip,
				                			new Ext.Button({
										        text:'Limpiar Lista de Valores',  
										        handler: function() {      
										        	Ext.getCmp('combo-lista-de-valores-atributos').setValue('');  
										        	Ext.getCmp('hidden-lista-valores-atributo').setValue('');
										        }
										    
										    })
				                	
				                ]
				            },{
                				columnWidth:.42,
				                layout: 'form',
				                heigth:200,
				                border: false,
				                items:[{
				                		layout: 'form',
				                		id:'hidden-forma-padre-condicion-orden-agrupador-boton',
                						border: false,				            
                						items: [
                								{layout: 'form', height:80, border: false,id:'forma-boton-vacia'},		
                								{xtype:'button', 
                								text: '\u00A0Agregar Condici\u00F3n\u00A0\u00A0', 
                								name: 'AgregarCondicion', 
                								buttonAlign: "right", 
                								handler: function() {
                									var connect = new Ext.data.Connection();
					    							connect.request ({
														url: _ACTION_OBTENER_CODIGO_EXP,
														callback: function (options, success, response) {				   
															codigoExpresion = Ext.util.JSON.decode(response.responseText).codigoExpresion;
															ExpresionesVentana2(codigoExpresion, Ext.getCmp('hidden-codigo-expresion-session').getValue() , dataStoreCondiciones, '3');
														}
											   		});
                								}
                								}]
                								}
                								,{
                								layout: 'form', height:20, border: false},
                								{layout: 'form', height:10, border: false, id:'forma-boton-catalogo-vacia'},	
                								{xtype:'button', 
                								text: 'Agregar al Cat\u00E1logo', 
                								name: 'AgregarAlCatalogo', 
                								buttonAlign: "right", 
                								handler: function() {creaListasDeValores(ds);}}
                									
                						]		
				                
				            }]
		        }]        
    ,
    buttons: [{
	            	text: 'Valor por Defecto',
	            	handler:function(){
	            		if(record && Ext.getCmp('hidden-codigo-expresion-atributos-variables').getValue()!=null 
	            		&& Ext.getCmp('hidden-codigo-expresion-atributos-variables').getValue()!=""
	            		&& Ext.getCmp('hidden-codigo-expresion-atributos-variables').getValue()!="0"
	            		&& Ext.getCmp('hidden-codigo-expresion-atributos-variables').getValue()!="undefined"){
	            			ExpresionesVentana2(Ext.getCmp('hidden-codigo-expresion-atributos-variables').getValue(),Ext.getCmp('hidden-codigo-expresion-session').getValue());
	            		}else{
	            			var connect = new Ext.data.Connection();
						    connect.request ({
								url: _ACTION_OBTENER_CODIGO_EXP,
								callback: function (options, success, response) {				   
									Ext.getCmp('hidden-codigo-expresion-atributos-variables').setValue(Ext.util.JSON.decode(response.responseText).codigoExpresion);
									ExpresionesVentana2(Ext.getCmp('hidden-codigo-expresion-atributos-variables').getValue(),Ext.getCmp('hidden-codigo-expresion-session').getValue());
								}
					   		});
	            		}
	            	}	            	
    		    }]
    });
    
    // Funcion eliminaAtributoVariable
    function eliminarAtributoVariable(datosVariablesForm){
    	datosVariablesForm.form.submit({
			url: _ACTION_ELIMINA_ATRIBUTO,
			waitTitle:'Espere',
			waitMsg:'Eliminando atributo variable...',
			failure: function(form, action) {
				Ext.MessageBox.alert('Error ', Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta);
			},
			success: function(form, action) {
				Ext.MessageBox.alert('Status', Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta);
				action.form.reset();  
				Ext.getCmp('arbol-productos').getRootNode().reload();  		
			}
		});
    }
    
    var params2 = 'codigoExpresionSession='+Ext.getCmp('hidden-codigo-expresion-session').getValue(); 
//    Ext.getCmp('hidden-form-check-complementario-obligatorio').hide();
    Ext.getCmp('hidden-form-check-endoso-obligatorio').hide();
    
    var windowAgregarAtr = new Ext.Window({
	       	width: 670,
	       	height: 400,
	       	minWidth: 300,
	       	minHeight: 100,
	       	modal: true,
	       	layout: 'fit',
	       	plain:true,
	       	bodyStyle:'padding:5px;',
	       	buttonAlign:'center',
	       	items: [datosVariablesForm],
	        buttons: [{
	            	text: 'Guardar',
	            	handler: function() {        	
	            		
                	if (datosVariablesForm.form.isValid()) {
                       
                	   var validaMaxMin = 1; 	
                	  if(  Ext.getCmp('id-hidden-text-maximo').isVisible() &&
	                       Ext.getCmp('id-hidden-text-minimo').isVisible()
	                    ){
                	        var valorMax =  Ext.getCmp('id-hidden-text-maximo').getValue();
            			    var valorMin =  Ext.getCmp('id-hidden-text-minimo').getValue();
	                        validaMaxMin =   validaValorMinMax(valorMin, valorMax);
	                    }
	                    
                	    if( validaMaxMin ){ 
                	
                   		  if(record){
                   		  }else{
                   			Ext.getCmp('hidden-lista-valores-atributo').setValue(Ext.getCmp('combo-lista-de-valores-atributos').getValue());                      	
                   		  }
                   		
                   		 
                   		  banderaSelectCombo=false;          		
		 		        	datosVariablesForm.form.submit({			      
					            	waitTitle:'Espere',
					            	waitMsg:'Guardando atributo variable...',
					            	failure: function(form, action) {
					            		var mensajeRespuesta = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
					            		Ext.MessageBox.alert('Error', Ext.isEmpty(mensajeRespuesta) ? 'Error al agregar atributos variables' : mensajeRespuesta);
									},
									success: function(form, action) {
								    	var mensajeRespuesta = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
										Ext.MessageBox.alert('Status', Ext.isEmpty(mensajeRespuesta) ? 'El atributo variable se ha guardado con &eacute;xito' : mensajeRespuesta, function () { storeGridAtr.load();});
										windowAgregarAtr.close();
								}
			        		}); 
			        		
			        		
                	    }// cierra valida Max Min 	   
			        		
                		} else{
							Ext.MessageBox.alert('Error', 'Favor de llenar campos requeridos.');
						}             
	        		}
    		    },{
	            	text : 'Cancelar',
	             	handler : function(){ windowAgregarAtr.close(); }
	           	}]
	   	});

	windowAgregarAtr.show(null,function(){
		if(record){
			    
                Ext.getCmp('id-atributos-variables-form').getForm().loadRecord(record);                 
                if(record.get('formato') == 'A'){                           		
                	Ext.getCmp('function-radio-alfa-atributos-variables').setValue(true);
                	Ext.getCmp('hidden-radio-atributos-variables').setValue('A');
                	Ext.getCmp('id-hidden-text-maximo').enable();
           			Ext.getCmp('id-hidden-text-minimo').enable();

                }
                if(record.get('formato') == 'N'){                           		
                	Ext.getCmp('function-radio-numeric-atributos-variables').setValue(true);
                	Ext.getCmp('hidden-radio-atributos-variables').setValue('N');
                	Ext.getCmp('id-hidden-text-maximo').enable();
           			Ext.getCmp('id-hidden-text-minimo').enable();
                }
                if(record.get('formato') == 'P'){                           		
                	Ext.getCmp('function-radio-percent-atributos-variables').setValue(true);
                	Ext.getCmp('hidden-radio-atributos-variables').setValue('P');
                	Ext.getCmp('id-hidden-text-maximo').reset();
       				Ext.getCmp('id-hidden-text-minimo').reset();					            	
            		Ext.getCmp('id-hidden-text-maximo').disable();            						
	        		Ext.getCmp('id-hidden-text-minimo').disable();
                }
                if(record.get('formato') == 'F'){                           		
                	Ext.getCmp('function-radio-date-atributos-variables').setValue(true);
                	Ext.getCmp('hidden-radio-atributos-variables').setValue('F');
                	Ext.getCmp('id-hidden-text-maximo').reset();
       				Ext.getCmp('id-hidden-text-minimo').reset();					            	
            		Ext.getCmp('id-hidden-text-maximo').disable();            						
	        		Ext.getCmp('id-hidden-text-minimo').disable();
				            		
                }
                if(record.get('obligatorio')=="S"){
                	Ext.getCmp('obligatorio-check-atributos-variables').setValue(true);
                	Ext.getCmp('obligatorio-check-atributos-variables').setRawValue("S");
                	
//                	Ext.getCmp('hidden-form-check-complementario').hide();
//				    Ext.getCmp('hidden-form-check-complementario-obligatorio').hide();				            		
				    //Ext.getCmp('complementario-check-atributos-variables').reset();
//				    Ext.getCmp('complementario-obligatorio-check-atributos-variables').reset();
//				    Ext.getCmp('complementario-modificable-check-atributos-variables').reset();
                }else{
                	Ext.getCmp('obligatorio-check-atributos-variables').setValue(false);
                	Ext.getCmp('obligatorio-check-atributos-variables').setRawValue("N");
//                	Ext.getCmp('hidden-form-check-complementario').show();
                }
                if(record.get('modificaEndoso')=="S"){
                	Ext.getCmp('endoso-check-atributos-variables').setValue(true);
                	Ext.getCmp('endoso-check-atributos-variables').setRawValue("S");
                }else{
                	Ext.getCmp('endoso-check-atributos-variables').setValue(false);
                	Ext.getCmp('endoso-check-atributos-variables').setRawValue("N");
                }               
                if(record.get('modificaEmision')=="S"){
                	Ext.getCmp('emision-check-atributos-variables').setValue(true);
                	Ext.getCmp('emision-check-atributos-variables').setRawValue("S");
                }else{
                	Ext.getCmp('emision-check-atributos-variables').setValue(false);
                	Ext.getCmp('emision-check-atributos-variables').setRawValue("N");
                }
                /*
                if(record.get('retarificacion')=="S"){
                	Ext.getCmp('retarificacion-check-atributos-variables').setValue(true);
                	Ext.getCmp('retarificacion-check-atributos-variables').setRawValue("S");
                }else{
                	Ext.getCmp('retarificacion-check-atributos-variables').setValue(false);
                	Ext.getCmp('retarificacion-check-atributos-variables').setRawValue("N");
                }
                if(record.get('despliegaCotizador')=="S"){
                	Ext.getCmp('cotizador-check-atributos-variables').setValue(true);
                	Ext.getCmp('cotizador-check-atributos-variables').setRawValue("S");
                }else{
                	Ext.getCmp('cotizador-check-atributos-variables').setValue(false);
                	Ext.getCmp('cotizador-check-atributos-variables').setRawValue("N");
                }*/
                //*******************************
                
                if(record.get('datoComplementario')=="S"){
//                	Ext.getCmp('complementario-check-atributos-variables').setValue(true);
//                	Ext.getCmp('complementario-check-atributos-variables').setRawValue("S");
                	Ext.getCmp('hidden-form-check-obligatorio').hide();
				    Ext.getCmp('obligatorio-check-atributos-variables').reset();
//				    Ext.getCmp('hidden-form-check-complementario').show();
//				    Ext.getCmp('hidden-form-check-complementario-obligatorio').show();
                }else{
//                	Ext.getCmp('complementario-check-atributos-variables').setValue(false);
//                	Ext.getCmp('complementario-check-atributos-variables').setRawValue("N");
                	Ext.getCmp('hidden-form-check-obligatorio').show();
//				    Ext.getCmp('hidden-form-check-complementario-obligatorio').hide();
//				    Ext.getCmp('complementario-obligatorio-check-atributos-variables').reset();
//				    Ext.getCmp('complementario-modificable-check-atributos-variables').reset();
                }
                if(record.get('obligatorioComplementario')=="S"){
//                	Ext.getCmp('complementario-obligatorio-check-atributos-variables').setValue(true);
//                	Ext.getCmp('complementario-obligatorio-check-atributos-variables').setRawValue("S");
                }else{
//                	Ext.getCmp('complementario-obligatorio-check-atributos-variables').setValue(false);
//                	Ext.getCmp('complementario-obligatorio-check-atributos-variables').setRawValue("N");
                }               
                if(record.get('modificableComplementario')=="S"){
//                	Ext.getCmp('complementario-modificable-check-atributos-variables').setValue(true);
//                	Ext.getCmp('complementario-modificable-check-atributos-variables').setRawValue("S");
                }else{
//                	Ext.getCmp('complementario-modificable-check-atributos-variables').setValue(false);
//                	Ext.getCmp('complementario-modificable-check-atributos-variables').setRawValue("N");
                }
                if(record.get('apareceEndoso')=="S"){
                	Ext.getCmp('aparece-endoso-check-atributos-variables').setValue(true);
                	Ext.getCmp('aparece-endoso-check-atributos-variables').setRawValue("S");
                	Ext.getCmp('hidden-form-check-endoso-obligatorio').show();
                }else{
                	Ext.getCmp('aparece-endoso-check-atributos-variables').setValue(false);
                	Ext.getCmp('aparece-endoso-check-atributos-variables').setRawValue("N");
                	Ext.getCmp('hidden-form-check-endoso-obligatorio').hide();
                	Ext.getCmp('obligatorio-endoso-check-atributos-variables').reset();
                	Ext.getCmp('endoso-check-atributos-variables').reset();
                }
                if(record.get('obligatorioEndoso')=="S"){
                	Ext.getCmp('obligatorio-endoso-check-atributos-variables').setValue(true);
                	Ext.getCmp('obligatorio-endoso-check-atributos-variables').setRawValue("S");
                }else{
                	Ext.getCmp('obligatorio-endoso-check-atributos-variables').setValue(false);
                	Ext.getCmp('obligatorio-endoso-check-atributos-variables').setRawValue("N");
                }
                
                //*******************************
                Ext.getCmp('hidden-clave-campo-atributos-variables').setValue(record.get('cdAtribu'));
                Ext.getCmp('hidden-descripcion-atributos-variables').setValue(record.get('descripcion'));
                Ext.getCmp('hidden-formato-atributos-variables').setValue(record.get('formato'));
                Ext.getCmp('hidden-lista-valores-atributo').setValue(record.get('codigoTabla'));
                Ext.getCmp('combo-lista-de-valores-atributos').setValue(record.get('descripcionTabla'));
                Ext.getCmp('hidden-codigo-expresion-atributos-variables').setValue(record.get('codigoExpresion'));
                
				Ext.getCmp('combo-condicion-atributos-variables-arbol').setValue(record.get('condicion'));
				Ext.getCmp('combo-padre-atributos-variables-arbol').setValue(record.get('padre'));
				Ext.getCmp('id-hidden-text-orden').setValue(record.get('orden'));
				Ext.getCmp('id-hidden-text-agrupador').setValue(record.get('agrupador'));
				if(record.get('padre') == ""){
					Ext.getCmp('hidden-forma-padre-condicion-orden-agrupador').hide();
    		        Ext.getCmp('hidden-forma-padre-condicion-orden-agrupador-boton').hide(); 
    		        Ext.getCmp('forma-boton-vacia').hide();
    		        Ext.getCmp('forma-boton-catalogo-vacia').show();
    		        Ext.getCmp('combo-condicion-atributos-variables-arbol').reset();
    		        Ext.getCmp('id-hidden-text-orden').reset();
    		        Ext.getCmp('id-hidden-text-agrupador').reset();
				}else{ 
					Ext.getCmp('hidden-forma-padre-condicion-orden-agrupador').show();
    		        Ext.getCmp('hidden-forma-padre-condicion-orden-agrupador-boton').show();
    		        Ext.getCmp('forma-boton-vacia').show();
    		        Ext.getCmp('forma-boton-catalogo-vacia').hide();
				}
				
        
			
			
	   	}else {
	 		var connAV = new Ext.data.Connection();
            connAV.request ({url: _ACTION_LIMPIAR_SESSION_EXP});
           
            Ext.getCmp('hidden-valor-defecto-atributos-variables').setValue("");
            Ext.getCmp('hidden-radio-atributos-variables').setValue("A");
                	
	 		Ext.getCmp('obligatorio-check-atributos-variables').setValue(false);
			Ext.getCmp('obligatorio-check-atributos-variables').setRawValue("N");
			
			Ext.getCmp('emision-check-atributos-variables').setValue(false);
			Ext.getCmp('emision-check-atributos-variables').setRawValue("N");
			
			Ext.getCmp('endoso-check-atributos-variables').setValue(false);
			Ext.getCmp('endoso-check-atributos-variables').setRawValue("N");
			
			/*Ext.getCmp('retarificacion-check-atributos-variables').setValue(false);
			Ext.getCmp('retarificacion-check-atributos-variables').setRawValue("N");*/
		
			/*Ext.getCmp('cotizador-check-atributos-variables').setValue(false);
			Ext.getCmp('cotizador-check-atributos-variables').setRawValue("N");*/
			
			//****************
//			Ext.getCmp('complementario-check-atributos-variables').setValue(false);
//			Ext.getCmp('complementario-check-atributos-variables').setRawValue("N");
//			Ext.getCmp('hidden-form-check-complementario').show();//mostrar layout
			
//			Ext.getCmp('complementario-obligatorio-check-atributos-variables').setValue(false);
//			Ext.getCmp('complementario-obligatorio-check-atributos-variables').setRawValue("N");
			
//			Ext.getCmp('complementario-modificable-check-atributos-variables').setValue(false);
//			Ext.getCmp('complementario-modificable-check-atributos-variables').setRawValue("N");
			
			Ext.getCmp('aparece-endoso-check-atributos-variables').setValue(false);
			Ext.getCmp('aparece-endoso-check-atributos-variables').setRawValue("N");
			Ext.getCmp('hidden-form-check-endoso-obligatorio').hide();
			Ext.getCmp('obligatorio-endoso-check-atributos-variables').reset();
			Ext.getCmp('endoso-check-atributos-variables').reset();
		
			Ext.getCmp('obligatorio-endoso-check-atributos-variables').setValue(false);
			Ext.getCmp('obligatorio-endoso-check-atributos-variables').setRawValue("N");
			//*****************
			//alert('disable 1 : ' + Ext.getCmp('id-descripcion-atributos-variables').disabled);
			Ext.getCmp('id-descripcion-atributos-variables').enable();
			//alert('disable 2 : ' + Ext.getCmp('id-descripcion-atributos-variables').disabled);
			Ext.getCmp('id-descripcion-atributos-variables').setDisabled(false);
			//alert('disable 3 : ' + Ext.getCmp('id-descripcion-atributos-variables').disabled);
			Ext.getCmp('hidden-forma-padre-condicion-orden-agrupador').hide();
           	Ext.getCmp('hidden-forma-padre-condicion-orden-agrupador-boton').hide();  
			
	   	}
	});
	
    
}