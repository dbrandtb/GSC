<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<jsp:include page="/resources/scripts/jsp/utilities/expresiones/expresionesNuevaVariableLocal.jsp" flush="true" />
<!-- SOURCE CODE -->
<script type="text/javascript">
    
    
ExpresionesDatosFijos = function(storeDatosFijos,rec,row) {
var codigoExpresion;
if(row!=null){
	//alert("row"+row);
	var record = storeDatosFijos.getAt(row);
    codigoExpresion= record.get('codigoExpresion');
	//alert("cod expres"+codigoExpresion);
	storeDatosFijos.on('load', function(){
                           //var record = storeValidaciones.getAt(row);
                           Ext.getCmp('cabecera-datos-fijos-form').getForm().loadRecord(record);
                           
                           //Ext.getCmp('top').getForm().loadRecord(record);
                           });
    storeDatosFijos.load();

		/************************ 
		*carga expresion
		*************************/
		
		
		
		url='librerias/CargaExpresionReglaNegocio.action?codigoExpresion='+codigoExpresion; 	
		var storeExpresion = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: url
        		}),
        		reader: new Ext.data.JsonReader({
            	root:'listaExpresionesReglaNegocio',   
            	totalProperty: 'totalCount',
            	id: 'descripcion'
            	         	
	        	},[
	        		{name: 'descripcion',  type: 'string',  mapping:'otExpresion'},
	        		{name: 'switchRecalcular',  type: 'string',  mapping:'switchRecalcular'},
	        		{name: 'codigoExpresion',  type: 'string',  mapping:'codigoExpresion'}
	        		            
				]),
			
			remoteSort: true
    	});
    	storeExpresion.setDefaultSort('descripcion', 'desc');
    	
    	storeExpresion.on('load', function(){
                           var rec = storeExpresion.getAt(0);                  
                           var swRecalcular= rec.get('switchRecalcular');                           
                           Ext.getCmp('top-datos-fijos').getForm().loadRecord(rec);
                           if(swRecalcular == 'S'){
                           		Ext.getCmp('switch-recalcular-expresion').setValue(true);
                           }else{
                           		Ext.getCmp('switch-recalcular-expresion').setValue(false);
                           }
                           Ext.getCmp('combo-bloque-value').disable();
                           Ext.getCmp('combo-campo-value').disable();
                           
                           dsComboV.load();
                           
                           });
                           storeExpresion.load();
                           
    	
		//*************************************************************************************

}

/****************************************
ventanota
****************************************/
	var record4editing;
	var detailEl;
	var win;
    var dsComboV = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: '<s:url value="/expresiones/ComboVariables.action"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaComboVariables',
            id: 'comboVariables'
	        },[
           {name: 'codigoExpresion', type: 'float',mapping:'codigoExpresion'},
           {name: 'nombre', type: 'string',mapping:'codigoVariable'},
           {name: 'tabla', type: 'string',mapping:'descripcionTabla'},
           {name: 'columna', type: 'string',mapping:'descripcionColumna'}
           
        ]),
		remoteSort: true
    });
    dsComboV.load(); 

	var afuera;
 	var temporal=-1;
	var comboVariables =new Ext.form.ComboBox({
    	                    tpl: '<tpl for="."><div ext:qtip="{nombre}. {tabla}" class="x-combo-list-item">{nombre}</div></tpl>',
							store: dsComboV,
						    displayField:'nombre',
						    valueField: 'nombre',
					    	typeAhead: true,
					    	labelAlign: 'top',
					    	heigth:'50',
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'Selecciona una variable',
					    	selectOnFocus:true,
					    	fieldLabel: "Variables",
					    	name:"claveSeleccionada",
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
									afuera=index; 
									Ext.getCmp('editar-variable').on('click',function(){
				                        if(afuera!=temporal){
							            temporal=afuera;
					                        if(comboVariables.getValue()!=null && comboVariables.getValue()!="" 
        	       							    		&& comboVariables.getValue()!="undefined"){
				                        		//alert("index="+index);
						                        NuevaVariableExpresiones(dsComboV,index);  
       				            	       	}else{
		    							    	Ext.MessageBox.alert('Error', 'No ha seleccionado una variable');
		    							    }                                                                          
		    							}    
       					         	});	
									temporal=-1;    
							}
							/*,
					    	beforeselect:function(combo,rec,index){
					    			alert(index);
					    			alert(this.getValue());
               				    	Ext.getCmp('editar-variable').on('click',function(){
				                        window.show();
       			            	       	Ext.getCmp('variable-form-panel').getForm().loadRecord(rec);                                                                            
       					         	});					    	
					    	}*/
			});
    var descripcion= new Ext.form.TextArea({
	                        name: "descripcion",
	                        id:'descripcion-expresion',
	                        fieldLabel: "Descripcion",
	                        width: '250',
	                        labelSeparator:'',
	                        //allowBlank: true,
	                        maxLength:'2000'
	        });
	var recalcular = new Ext.form.Checkbox({	
	  		                fieldLabel: 'Last Name',
	  		                id:'switch-recalcular-expresion',
	           		        hideLabel:true,
	                   		boxLabel:'Recalcular siempre',
	                  		name:'switchRecalcular'
		    });

	function detailsUpdate(text){			
			if(!detailEl){
    			var bd = Ext.getCmp('details-panel').body;
    			bd.update('').setStyle('background','#fff');
    			detailEl = bd.createChild(); //create default empty div
    		}
    		detailEl.hide().update("Details:"+text).slideIn('l', {stopFx:true,duration:.2});
	}
	/**************************************************************
	Esta es la cabecera 
	***************************************************************/
	var storeCampo = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'datosFijos/CatalogoCampo.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'catalogoCampo',
            id: 'readerCatalogoCampo'
	        },[
           {name: 'claveCampo', type: 'string',mapping:'key'},
           {name: 'valorCampo', type: 'string',mapping:'value'}    
        ]),
		remoteSort: true
    });
       storeCampo.setDefaultSort('claveCampo', 'desc');
	var storeBloque = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'datosFijos/CatalogoBloque.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'catalogoBloque',
            id: 'readerCatalogoBloque'
	        },[
           {name: 'claveBloque', type: 'string',mapping:'key'},
           {name: 'valorBloque', type: 'string',mapping:'value'}    
        ]),
		remoteSort: true
    });
    storeBloque.setDefaultSort('claveBloque', 'desc');
    storeBloque.load();   
    var comboBloque = new Ext.form.ComboBox({
    		tpl: '<tpl for="."><div ext:qtip="{claveBloque}" class="x-combo-list-item">{valorBloque}</div></tpl>',
    		store: storeBloque,
    		id:'combo-bloque-value',
    		displayField:'valorBloque',
    		valueField:'claveBloque',
    		allowBlank:false,
    		blankText : 'Dato requerido',
    		width:150,
    		typeAhead: true,
    		mode: 'local',
    		triggerAction: 'all',
    		emptyText:'Selecciona un bloque',
    		selectOnFocus:true,
    		fieldLabel: "Bloque",
    		forceSelection:true,
    		name:"descripcionBloque",
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
         	//carga el otro combo
         	params="claveBloque="+this.getValue();
         	//alert(params);
         	
         	var conn = new Ext.data.Connection();
			conn.request ({
					url: 'datosFijos/SubeClaveBloque.action',
					method: 'POST',
					successProperty : '@success',
					params : params,
				    callback: function (options, success, response) {
                    		if (Ext.util.JSON.decode(response.responseText).success == false) {
	                				//Ext.Msg.alert('Error', 'No se pudieron guardar los cambios');                        							
                       		} else {
		               				//Ext.Msg.alert('Status', 'Se han guardado con éxito los cambios');
		                    		storeCampo.load();
    	                    }
	           		},
	              	waitMsg: 'Espere por favor....'
		      	});
			}
		});       
   	  
       
       var comboCampo = new Ext.form.ComboBox({
    		tpl: '<tpl for="."><div ext:qtip="{claveCampo}" class="x-combo-list-item">{valorCampo}</div></tpl>',
    		store: storeCampo,
    		id:'combo-campo-value',
    		displayField:'valorCampo',
    		valueField:'claveCampo',
    		allowBlank:false,
    		blankText : 'Dato requerido',
    		width:150,
    		typeAhead: true,
    		mode: 'local',
    		triggerAction: 'all',
    		emptyText:'Selecciona un campo',
    		selectOnFocus:true,
    		//forceSelection:true,
    		fieldLabel: "Campo",
    		name:"descripcionCampo"
		});   
		         
	var cabecera = new Ext.form.FormPanel({
            	
            	frame:true,
            	url:'datosFijos/InsertarDatoFijo.action',
            	id:'cabecera-datos-fijos-form',
       			bodyStyle:'padding:5px',
        		width:600,
        		height:85,
        		laberAlign:'top',
        		title:'valores por defecto campos fijos',
        		region:'north',
                items: [
						{xtype:'hidden',id:'hidden-combo-bloque-value',name:'claveBloque'},
						{xtype:'hidden',id:'hidden-combo-campo-value',name:'claveCampo'},
						{
 				    		border: false,
 				    		layout: 'form',
				    		items: [{
        								layout:'column',
        								border: false,
            							items:[{
				                			columnWidth:.5,
                							layout: 'form',
                							border: false,
				                				items: [
				                					comboBloque
				                				]
        								},{
               								columnWidth:.5,
			                				layout: 'form',
			                				border: false,
               									items: [
				                					comboCampo    						
               									]
				    					}]        			
		        			}]
		        }]
    });

	/***************************************************************
	Termina la cabecera y empieza la parte de expresiones
	****************************************************************/
    var tab2 = new Ext.FormPanel({
					region:'center',
					labelAlign: 'left',
			        frame: true,
			        margins:'0 0 0 5',
			        url:'<s:url value="/expresiones/AgregarExpresion.action"/>',
			        bodyBorder:false,
			        id:'top-datos-fijos',
			        bodyStyle:'padding:0 0 0',
			        height:500,			        
			        width: 500,
			        items:[{xtype:'hidden',id:'hidden-codigo-expresion',name:'codigoExpresion'},{
		   				layout:'form',
		   				margins:'0 5 5 5',
		   				//height:300,
			        	bodyStyle:'padding:5px 5px 0',
		   				border:false,
		   				frame:false,
		   				title: 'Valor por defecto del atributo',
		   				items: [{
					            layout:'column',
					            border:false,
					            items:[{
						                columnWidth:.7,
					    	            layout: 'form',
					        	        border:false,
					            	    labelAlign: 'top',
					                	items: [descripcion]
						            },{
						                columnWidth:.3,
						                layout: 'form',
						                border:false,
						                items: [{
							                    layout: 'form',
							             		heigth:20,
						    	         		border:false
						        	        },{
						            	        layout: 'form',
						                		border:false,
						                		items: [recalcular,{
							            		        xtype:'button',
		                							    text:'Comprobar Sintaxis'
					        			        }]
					            	    }]
					            }]
					        },{
					        	layout:'column',
					            border:false,
					            items:[{
						                columnWidth:.7,
					    	            layout: 'form',
					        	        border:false,					                
						                items: [comboVariables]
						        	}]
					    	},{
					    	    layout:'column',
					        	border:false,
				    	        items:[{
						                columnWidth:.35,
						                layout: 'form',
						                border:false,					                
						                items: [{
						               	        xtype:'button',
            				   				    text:'Agregar Variable',
               								    handler:function(){
               								    		
                					    				NuevaVariableExpresiones(dsComboV);
				               				    }					        		     
						                }]
						            },{
						                columnWidth:.35,
						                layout: 'form',
						                border:false,
					    	            items: [{
					        	       	        xtype:'button',
		                					    text:'Editar Variable',
		                					    id:'editar-variable'	        		     
						                }]
					            	},{
						                columnWidth:.3,
						                layout: 'form',
					    	            border:false,
					        	        
					            	    items: [{
					               		        xtype:'button',
		           			   				    text:'Eliminar Variable',
        	       							    handler:function(){
        	       							    		var valorCombo=comboVariables.getValue();
        	       							    		if(comboVariables.isDirty()){
			    	           							    comboVariables.reset();
        	       							    			
	        	       							    		var url4delete='/expresiones/EliminarVariable.action?claveSeleccionada='+valorCombo;
    	    	       							    		Ext.MessageBox.confirm('Mensaje','Realmente deseas eliminar el elemento?', function(btn) {
																if(btn == 'yes'){   
																	tab2.form.submit({	
																		url: url4delete,
																	    waitMsg:"Procesando...",
											    						failure: function(form, action) {
																	    	Ext.MessageBox.alert('Status', 'Error al eliminar');
																		},
																		success: function(form, action) {
																		    Ext.MessageBox.alert('Status', 'Elemento eliminado');						    
																		    dsComboV.load();						    
																		}
																	});         	 
																}
															});
			    	           							    //DeleteInDataStoreFromAction(tab2,url4delete,dsComboV);
			    	           							    
;
		    	           							    }else{
		    	           							    	Ext.MessageBox.alert('Error', 'No ha seleccionado una variable');
		    	           							    }
			        	       				    }					        		     
					    	            }]
					        	}]
					    
						    }]
			    	},{layout:'form',hegth:'10'}]
				});
				

/************************************
nueva ventana
***********************************/
	
    // define window and show it in desktop
 var wind = new Ext.Window({
            title: 'Libreria',
            closable:true,
            buttonAlign:'center',
            width:700,
            height:340,
            //border:false,
            plain:true,
            layout: 'border',
            modal:true,
			items:[{
						region:'west',
						heigth:'500',
						width:'200',
						minSize:'175',
						maxSize:'300',
	        		    collapsible: false,
		                margins:'0 5 0 5',
           			    layout:'accordion',
	                    layoutConfig:{
       				            animate:true
	                    },
       				    items: [{    
						        title: 'Funciones',
						        xtype: 'treepanel',
			    			    width: 200,
						        autoScroll: true,
						        split: true,
			    			    loader: new Ext.tree.TreeLoader({
						               dataUrl:'<s:url value="/expresiones/FuncionesArbol.action"/>'
					            }), 
						        root:  new Ext.tree.AsyncTreeNode({
						                text: 'Ext JS', 
						                draggable:false, // disable root node dragging
						                id:'source'
					            }),
						        rootVisible: false,
						        listeners: {
			    				        click: function(n) {
								            //Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');								               
	 										//detailsUpdate(n.attributes.descripcion);
	 										var valor = descripcion.getValue();
							            	if(descripcion.isDirty()){
							            		valor = valor+" ";
							            	}
	 										descripcion.setValue(valor+n.attributes.funcion);
							            }/*,
							            dblclick: function(n){
							            	var valor = descripcion.getValue();
							            	if(descripcion.isDirty()){
							            		valor = valor+" ";
							            	}
	 										descripcion.setValue(valor+n.attributes.funcion);
							           }*/
						        }
						    },{    
						        title: 'VariablesTemporales',
						        xtype: 'treepanel',
						        width: 200,
			    			    autoScroll: true,
						        split: true,
						        loader: new Ext.tree.TreeLoader({
						               dataUrl:'<s:url value="/expresiones/VariablesTemporalesArbol.action"/>'
					            }), 
						        root:  new Ext.tree.AsyncTreeNode({
						                text: 'Ext JS', 
						                draggable:false, // disable root node dragging
						                id:'source'
					            }),
						        rootVisible: false,
						        listeners: {
			    				        click: function(n) {
								                //Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');								                
	 											//detailsUpdate(n.attributes.descripcion);
	 											var valor= descripcion.getValue();
								            	if(descripcion.isDirty()){
									            	valor=valor+" &";
		 										}else{
	 												valor=valor+"&";	 										
	 											}
	 											descripcion.setValue(valor+n.attributes.funcion);
							            }/*,
							            dblclick: function(n){
								            var valor= descripcion.getValue();
							            	if(descripcion.isDirty()){
								            	valor=valor+" &";
	 										}else{
	 											valor=valor+"&";	 										
	 										}
	 										descripcion.setValue(valor+n.attributes.funcion);
							            }*/
						        }
						    }]
		
										
				},tab2,cabecera],
				buttons:[{
  						text:'Guardar',
  						handler: function(){
  							var valorDeLaExpresion = Ext.getCmp('descripcion-expresion').getValue();
  							//alert(valorDeLaExpresion);
  							if(valorDeLaExpresion.length>0) {
					 		        var valorDeComboBloque=Ext.getCmp('combo-bloque-value').getValue();
					 		        var valorDeComboCampo=Ext.getCmp('combo-campo-value').getValue();
   					 		       
					 		        Ext.getCmp('hidden-combo-bloque-value').setValue( valorDeComboBloque );
					 		        Ext.getCmp('hidden-combo-campo-value').setValue( valorDeComboCampo );
					 		    
					 		        Ext.getCmp('top-datos-fijos').getForm().submit({
					 		        		waitMsg:'Salvando datos...',
								            failure: function(form, action) {
												    Ext.MessageBox.alert('Error', 'Datos no agregados');
												    //wind.close();
											},
											success: function(form, action) {
												    Ext.getCmp('cabecera-datos-fijos-form').getForm().submit({			      
											            waitTitle:'Espere',	
											            waitMsg:'Salvando datos...',
											            failure: function(form, action) {
								    			        		alert('debug4 failure');
															    Ext.MessageBox.alert('Error', 'Datos no agregados');
															    //wind.close();
														},
														success: function(form, action) {
															    //Ext.MessageBox.alert('Status', 'Datos agregados');
															    storeDatosFijos.load();						   
															    wind.close();
														}
							        				});
											}
					 		        });
	        		        } else{
	        		        		descripcion.markInvalid("Este dato es requerido");
									Ext.MessageBox.alert('Error', 'Favor de llenar datos requeridos');
							}     
  						}
  					},{
  						text:'Cancelar',
  						handler:function(){  
  							//storeValidaciones.load();						
  							wind.close();
  						}
  				}]
        });
        wind.show();
};
</script>