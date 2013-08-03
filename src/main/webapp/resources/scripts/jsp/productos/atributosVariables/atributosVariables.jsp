<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->
<jsp:include page="/resources/scripts/jsp/utilities/atributosVariables/listasDeValores/listasDeValores.jsp" flush="true" />
<jsp:include page="/resources/scripts/jsp/utilities/expresiones/expresiones.jsp" flush="true" />

<script type="text/javascript">

Ext.onReady(function(){
Ext.QuickTips.init();
var dataStorePadre;
Ext.form.Field.prototype.msgTarget = "side";

	
		    		       	
    var banderaSelectCombo = false;    
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
           //{name: 'claveCampo', type: 'string',mapping:'codigoAtributo'}    
        ]),
		remoteSort: true
    });
       ds.setDefaultSort('valoresDeAtributos', 'desc'); 
       
    var dataStoreCondiciones = new Ext.data.Store({
    	proxy: new Ext.data.HttpProxy({
				url:'librerias/CargaReglaNegocio.action'+'?numeroGrid='+'3'	
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
				url:'atributosVariables/CargaComboPadre.action'
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
       	//dataStorePadre.load(); 
       
       
    var comboWithTooltip = new Ext.form.ComboBox({    		
    		tpl: '<tpl for="."><div ext:qtip="{codigoTabla}" class="x-combo-list-item">{descripcionTabla}</div></tpl>',
    		store: ds,
    		displayField:'descripcionTabla',
    		valueField :'codigoTabla',
	    	id: 'combo-lista-de-valores-atributos',
    		//allowBlank:false,
    		//blankText : 'Dato requerido',
    		//maxLength : '30',
    		//maxLengthText : 'Treinta caracteres maximo',
    		typeAhead: true,
    		mode: 'local',
    		triggerAction: 'all',
    		emptyText:'<s:text name="productos.configAtributoVariable.ListaValor.vacia"/>',
    		selectOnFocus:true,
    		fieldLabel: '<s:text name="productos.configAtributoVariable.ListaValor"/>',
    		listWidth: '250',
    		name:"descripcionTabla",
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
	    		banderaSelectCombo = true;
	    		Ext.getCmp('hidden-lista-valores-atributo').setValue(Ext.getCmp('combo-lista-de-valores-atributos').getValue());                      	
    			//alert(banderaSelectCombo);
    	}
    	//applyTo: 'local-descripcion-with-qtip'
	});
	
	
	
	/**Agregado sErGiO*/
	
	var comboCondicion = new Ext.form.ComboBox({
				id:'combo-condicion-atributos-variables-arbol',
				tpl: '<tpl for="."><div ext:qtip="{nombreCabecera}" class="x-combo-list-item">{descripcionCabecera}</div></tpl>',
    			store : dataStoreCondiciones, 
				listWidth: '250',
    			mode: 'local',
				name: 'condicion',
    			typeAhead: true,
				//labelSeparator:'',
				//allowBlank:false,
    			triggerAction: 'all',    		
    			displayField:'descripcionCabecera',
				forceSelection: true,
				fieldLabel: '<s:text name="productos.configAtributoVariable.condicion"/>',
				emptyText:'<s:text name="productos.configAtributoVariable.ListaCondicion.vacia"/>',
				selectOnFocus:true
		});     
	var comboPadre = new Ext.form.ComboBox({
				id:'combo-padre-atributos-variables-arbol',
				tpl: '<tpl for="."><div ext:qtip="{clavePadre}" class="x-combo-list-item">{descripcionPadre}</div></tpl>',
    			store: dataStorePadre, 
				listWidth: '250',
    			mode: 'local',
				name: 'padre',
    			typeAhead: true,
				//labelSeparator:'',
				//allowBlank:false,
    			triggerAction: 'all',    		
    			displayField:'descripcionPadre',
				forceSelection: true,
				fieldLabel: '<s:text name="productos.configAtributoVariable.padre"/>',
				emptyText:'<s:text name="productos.configAtributoVariable.ListaPadre.vacia"/>',
				selectOnFocus:true
		});     
  
 /*finish*/
            
    var hideTextMaximo= new Ext.form.NumberField({
				            id:'id-hidden-text-maximo',
            				name:'maximo',
            				fieldLabel: '<s:text name="productos.configAtributoVariable.maximo"/>',
            				allowDecimals : false,
				            allowNegative : false,
        				    allowBlank: false,
        				    blankText : '<s:text name="productos.configAtributoVariable.dato.requerido"/>',            				
            				hideParent:true,
            				//labelSeparator:'',
            				width:25	
            							       
           });
           
     var hideTextMinimo= new Ext.form.NumberField({
				            id:'id-hidden-text-minimo',
            				name:'minimo',
            				fieldLabel: '<s:text name="productos.configAtributoVariable.minimo"/>',
            				allowDecimals : false,
				            allowNegative : false,
        				    allowBlank: false,
        				    blankText : '<s:text name="productos.configAtributoVariable.dato.requerido"/>',            				
            				hideParent:true,
            				//labelSeparator:'',
            				width:25	
            							       
           });
           /*Agregado sErGiO*/
           
      var hideOrden = new Ext.form.NumberField({
      						id:'id-hidden-text-orden',
      						name:'orden',
      						fieldLabel:'<s:text name="productos.configAtributoVariable.orden"/>',
      						allowDecimals : false,
				            allowNegative : false,
        				    //allowBlank: false,
        				    blankText : '<s:text name="productos.configAtributoVariable.dato.requerido"/>',				
            				width:160
      	   });
      	   
      var hideAgrupador = new Ext.form.NumberField({
      						id:'id-hidden-text-agrupador',
      						name:'agrupador',
      						fieldLabel:'<s:text name="productos.configAtributoVariable.agrupador"/>',
      						allowDecimals : false,
				            allowNegative : false,
        				    //allowBlank: false,
        				    blankText : '<s:text name="productos.configAtributoVariable.dato.requerido"/>',            				
            				width:160
      	   });
              /*SeRgIo*/
              
           
     var functionRadioAlfa= new Ext.form.Radio({
     						id:'function-radio-alfa-atributos-variables',
            				name:'formato',
            				fieldLabel: '<s:text name="productos.configAtributoVariable.formato"/>',
            				checked: true,
            				//labelSeparator : "",
            				boxLabel: '<s:text name="productos.configAtributoVariable.alfaNumerico"/>',
            				//width: 10,
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
            				boxLabel: '<s:text name="productos.configAtributoVariable.numerico"/>',
            				hideLabel:true,
            				//width: 10,
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
            				boxLabel: '<s:text name="productos.configAtributoVariable.porcentaje"/>',
            				//hideLabel:true,
  	  	       				onClick: function(){			            		
            						hideTextMaximo.disable();            						
				            		hideTextMinimo.disable();
  	  	       						Ext.getCmp('id-hidden-text-maximo').setValue('');
            						Ext.getCmp('id-hidden-text-minimo').setValue('');
				            		Ext.getCmp('hidden-radio-atributos-variables').setValue("P");
			            	}
            }); 
            
    var functionRadioDate= new Ext.form.Radio({
            				id:'function-radio-date-atributos-variables',
            				name: 'formato',
				           	boxLabel: '<s:text name="productos.configAtributoVariable.fecha"/>',
				           	labelSeparator : "",
				            hideLabel:true,
  	  	       				onClick: function(){			            		
            						hideTextMaximo.disable();            						
				            		hideTextMinimo.disable();
  	  	       						Ext.getCmp('id-hidden-text-maximo').setValue('');
            						Ext.getCmp('id-hidden-text-minimo').setValue('');
				            		Ext.getCmp('hidden-radio-atributos-variables').setValue("F");
			            	}
            });                                
      
    var obligatorioCheck= new Ext.form.Checkbox({
    			id:'obligatorio-check-atributos-variables',
                name:'obligatorio',
                boxLabel: '<s:text name="productos.configAtributoVariable.obligatorio"/>',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
            });
            
    var emisionCheck= new Ext.form.Checkbox({
    			id:'emision-check-atributos-variables',
                name:'modificaEmision',
                boxLabel: '<s:text name="productos.configAtributoVariable.emision"/>',
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
                boxLabel: '<s:text name="productos.configAtributoVariable.endoso"/>',
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
                boxLabel: '<s:text name="productos.configAtributoVariable.retarificacion"/>',
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
                boxLabel: '<s:text name="productos.configAtributoVariable.cotizador"/>',
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
                boxLabel: '<s:text name="productos.configAtributoVariable.complementario"/>',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
            });                 

	var obligaComplementarioCheck= new Ext.form.Checkbox({
    			id:'complementario-obligatorio-check-atributos-variables',
                name:'obligatorioComplementario',
                boxLabel: '<s:text name="productos.configAtributoVariable.obligatorioComplementario"/>',
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
                boxLabel: '<s:text name="productos.configAtributoVariable.modificableComplementario"/>',
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
                boxLabel: '<s:text name="productos.configAtributoVariable.apareceEndoso"/>',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
            });     
            
       var obligatorioEndosoCheck= new Ext.form.Checkbox({
    			id:'obligatorio-endoso-check-atributos-variables',
                name:'obligatorioEndoso',
                boxLabel: '<s:text name="productos.configAtributoVariable.obligatorioEndoso"/>',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
            });                    
            //hideTextMaximo.disable();               
            //hideTextMinimo.disable();

            var datosVariablesForm = new Ext.form.FormPanel({
            	id:'id-atributos-variables-form',
            	frame:false,
            	//labelAlign: 'left',
            	url:'atributosVariables/GuardarAtributosVariables.action',
            	//labelWidth: 95,
        		header:true,
        		//title: '',
       			bodyStyle:'padding:5px',
        		width:615,
        		buttonAlign: "center",
                items: [{xtype:'hidden',id:'hidden-codigo-expresion-session',name:'codigoExpresionSession', value:'EXPRESION_ATRIBUTOS_VARIABLES'},
                		{xtype:'hidden',id:'hidden-lista-valores-atributo',name:'codigoTabla'},
                		//{xtype:'hidden',id:'hidden-codigo-atributo',name:'codigoAtributo'},
                {
                	xtype:'hidden',
		            name: "claveCampo",
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
		            blankText : '<s:text name="productos.configAtributoVariable.descripcion.req"/>',
		            id:'id-descripcion-atributos-variables',
		            maxLength: 120,
        		    fieldLabel: '<s:text name="productos.configAtributoVariable.descripcion"/>',
		            width: 150
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
				},
				    obligatorioCheck,
				    emisionCheck,
				    endosoCheck,
				    cotizadorCheck,
				{
		        	layout:'form',
		        	id:'hidden-form-attributos-coberturas',
		        	border: false,
		        	items:[		        	
        					retarifacionCheck
        					//cotizadorCheck
		        	]     		
				},
					complementarioCheck,
					obligaComplementarioCheck,
					modificableComplementarioCheck,
					apareceEndosoCheck,
					obligatorioEndosoCheck,
				{
 				   layout:'column',
 				   border: false,
            				items:[{
				                	columnWidth:.58,
                					layout: 'form',
                					border: false,
                					width:300,
				                	items: [{
				                			layout: 'form',
				                			id:'hidden-forma-padre-condicion-orden-agrupador',
                							border: false,
				                				items:[
				                					comboPadre,
				                					hideAgrupador,
				                					hideOrden,
				                					comboCondicion
				                				]
				                			},
				                			{layout: 'form', border: false},
				                			comboWithTooltip
				                	
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
                								{layout: 'form', height:80, border: false},		
                								{xtype:'button', 
                								text: '<s:text name="btn.AgregarCondicion"/>', 
                								name: 'AgregarCondicion', 
                								buttonAlign: "right", 
                								handler: function() {ExpresionesVentanaCondiciones(dataStoreCondiciones);}
                								}]
                								}
                								,{
                								layout: 'form', height:20, border: false},
                								{xtype:'button', 
                								text: '<s:text name="btn.agregarAlCatalogo"/>', 
                								name: 'AgregarAlCatalogo', 
                								buttonAlign: "right", 
                								handler: function() {creaListasDeValores(ds);}}
                									
                						]		
				                
				            }]
		        }],

        buttons: [{
	            	text: '<s:text name="btn.ValoresPorDefecto"/>',
	            	handler:function(){
	            		if(Ext.getCmp('hidden-codigo-expresion-atributos-variables').getValue()!=null 
	            		&& Ext.getCmp('hidden-codigo-expresion-atributos-variables').getValue()!=""
	            		&& Ext.getCmp('hidden-codigo-expresion-atributos-variables').getValue()!="0"
	            		&& Ext.getCmp('hidden-codigo-expresion-atributos-variables').getValue()!="undefined"){
	            			ExpresionesVentana2(Ext.getCmp('hidden-codigo-expresion-atributos-variables').getValue(),Ext.getCmp('hidden-codigo-expresion-session').getValue());
	            		}else{
	            			var connect = new Ext.data.Connection();
						    connect.request ({
								url:'atributosVariables/ObtenerCodigoExpresion.action',
								callback: function (options, success, response) {				   
									//alert(Ext.util.JSON.decode(response.responseText).codigoExpresion);
									Ext.getCmp('hidden-codigo-expresion-atributos-variables').setValue(Ext.util.JSON.decode(response.responseText).codigoExpresion);
									ExpresionesVentana2(Ext.getCmp('hidden-codigo-expresion-atributos-variables').getValue(),Ext.getCmp('hidden-codigo-expresion-session').getValue());
								}
					   		});
	            		}
	            	}	            	
    		    },{
	            	text: '<s:text name="btn.guardar"/>',
	            	handler: function() {                	
                	// check form value
                	//alert(Ext.getCmp('hidden-codigo-expresion-session').getValue());
	            		
                	if (datosVariablesForm.form.isValid()) {
                	
                		//alert("hidden="+Ext.getCmp('hidden-lista-valores-atributo').getValue());
                		//alert("combo="+Ext.getCmp('combo-lista-de-valores-atributos').getValue());
                		//alert("codigo atributo="+Ext.getCmp('hidden-clave-campo-atributos-variables').getValue());
                		//alert("isDirty="+Ext.getCmp('combo-lista-de-valores-atributos').isDirty());
                   		//alert("banderaSelectCombo="+banderaSelectCombo);
                   		if((Ext.getCmp('hidden-clave-campo-atributos-variables').getValue()!="undefined" 
                 			&& Ext.getCmp('hidden-clave-campo-atributos-variables').getValue()!=""
                 			&& Ext.getCmp('hidden-clave-campo-atributos-variables').getValue()!=null) 
                 			&& !banderaSelectCombo){
                 			
                 			//alert("into the if, variable attributes");
                      		//alert(Ext.getCmp('hidden-lista-valores-atributo').getValue());
                   		}else{
                   			//alert("into the else, variable attributes");
                   			Ext.getCmp('hidden-lista-valores-atributo').setValue(Ext.getCmp('combo-lista-de-valores-atributos').getValue());                      	
                   			//alert(Ext.getCmp('hidden-lista-valores-atributo').getValue());
                   		}       
                   		banderaSelectCombo=false;          		
		 		        	datosVariablesForm.form.submit({			      
					            	waitTitle:'<s:text name="ventana.configAtributoVariable.proceso.mensaje.titulo"/>',
					            	waitMsg:'<s:text name="ventana.configAtributoVariable.proceso.mensaje"/>',
					            	failure: function(form, action) {
								    Ext.MessageBox.alert('Error ', 'Error al agregar atributos variables');
									},
									success: function(form, action) {
								    Ext.MessageBox.alert('Status', 'El atributo variable se ha guardado con &eacute;xito');						    		
						    		//action.form.reset();  
						    		 Ext.getCmp('arbol-productos').getRootNode().reload();  		
								}
			        		});                   
                		} else{
							Ext.MessageBox.alert('Error', 'Favor de llenar campos requeridos.');
						}             
	        		}
    		    },{
	            	text: '<s:text name="btn.cancelar"/>'
    		    }]        
    });
    var params2 = 'codigoExpresionSession='+Ext.getCmp('hidden-codigo-expresion-session').getValue();
    /*var conn = new Ext.data.Connection();
    conn.request ({
				url:'<s:url namespace="/expresiones" value="EliminarExpresionSession.action"/>',
				method: 'POST',
				params:params2
   	});*/
    //ds.load({params:{start:0, limit:25}});
    //dataStoreCondiciones.load({params:{start:0, limit:25}});
    
    datosVariablesForm.render('centerAtributos');
  	
        });
</script>
