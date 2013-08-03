<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<jsp:include page="/resources/scripts/jsp/utilities/expresiones/expresionesNuevaVariableLocal.jsp" flush="true" />
<!-- SOURCE CODE -->
<script type="text/javascript">
ExpresionesVentana = function(codigoExpresion, codigoExpresionSession){
var dsComboV;
var record4editing;
var detailEl;
var win;
var urlEditarExpresion='expresiones/EditarExpresion.action'; 	
var temporal = -1;
//var urlEditarExpresion='librerias/CargaExpresionReglaNegocio.action';
//alert(codigoExpresionSession);
/****************************************
Editar Expresion
****************************************/

	if(codigoExpresion!=null && codigoExpresion!='' && codigoExpresion!="undefined"){
		urlEditarExpresion+='?codigoExpresion='+codigoExpresion;					
	}else{
		urlEditarExpresion+='?codigoExpresion=0';					
	}
	/*
	if(codigoExpresionSession!=null && codigoExpresionSession!='' && codigoExpresionSession!="undefined"){
		urlEditarExpresion+='?codigoExpresionSession='+codigoExpresionSession;					
	}else{
		urlEditarExpresion+='codigoExpresionSession=EXPRESION';					
	}*/
	editaExpresion();
function editaExpresion(){
		//alert("edit expresion + urlEditarExpresion="+urlEditarExpresion);
		var storeExpresion = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: urlEditarExpresion
        		}),
        		reader: new Ext.data.JsonReader({
            	root:'editarExpresion',   
            	totalProperty: 'totalCount',
            	id: 'editarExpresion'            	         	
	        	},[
	        		{name: 'descripcion',  type: 'string',  mapping:'otExpresion'},
	        		{name: 'switchRecalcular',  type: 'string',  mapping:'switchRecalcular'},
	        		{name: 'codigoExpresion',  type: 'string',  mapping:'codigoExpresion'}	        		            
				]),			
			remoteSort: true
    	});
    	storeExpresion.setDefaultSort('descripcion', 'desc');
    	    	
    	storeExpresion.on('load', function(){   
    						//alert(codigoExpresion);
    						//alert(Ext.getCmp('hidden-codigo-expresio-expresion').getValue());
    						if(storeExpresion.getTotalCount()>0){                        
		                           var rec = storeExpresion.getAt(0);                  
        		                   var swRecalcular= rec.get('switchRecalcular');
                		           Ext.getCmp('forma-expresiones').getForm().loadRecord(rec);
                        		   if(swRecalcular == 'S'){
		                           		Ext.getCmp('switch-recalcular-expresion-simple').setValue(true);
        		                   		Ext.getCmp('switch-recalcular-expresion-simple').setRawValue("S");
                		           }if(swRecalcular == 'N'){
                        		   		Ext.getCmp('switch-recalcular-expresion-simple').setValue(false);
                           				//Ext.getCmp('switch-recalcular-expresion-simple').setRawValue("N");
		                           }
        		                   dsComboV.load();
        		                   //alert(Ext.getCmp('hidden-codigo-expresio-expresion').getValue());
                	        }else{
                	        	Ext.getCmp('forma-expresiones').getForm().reset();
                	        }
		});
        storeExpresion.load();
        urlEditarExpresion='expresiones/EditarExpresion.action'; 
}
/****************************************
Expresiones
*****************************************/

    dsComboV = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'expresiones/ComboVariables.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaComboVariables',
            id: 'comboVariables'
	        },[
           {name: 'codigoExpresion', type: 'float',mapping:'codigoExpresion'},
           {name: 'nombre', type: 'string',mapping:'codigoVariable'},
           {name: 'tabla', type: 'string',mapping:'tabla'},
           {name: 'descripcionTabla', type: 'string',mapping:'descripcionTabla'},
           {name: 'columna', type: 'float',mapping:'columna'},
           {name: 'descripcionColumna', type: 'string',mapping:'descripcionColumna'}
           
        ]),
         baseParams: {codigoExpresion:codigoExpresion},
		remoteSort: true
    });
    dsComboV.load(); 

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
						    emptyText:'<s:text name="productos.valida.expresion.variables"/>',
					    	selectOnFocus:true,
					    	fieldLabel: '<s:text name="productos.config.expresion.variables"/>',
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
									alert("afuera fuera = "+afuera+"mas temporal fuera = "+temporal);
									Ext.getCmp('editar-variable').on('click',function(){
										alert("afuera dentro =" + afuera + " mas temporal dentro ="+temporal);
					                        if(comboVariables.getValue()!=null && comboVariables.getValue()!="" 
        	       							    		&& comboVariables.getValue()!="undefined"){
				                        		//alert("index="+index);
						                        NuevaVariableExpresiones(Ext.getCmp('hidden-codigo-expresio-expresion').getValue(),dsComboV,index);  
       				            	       	}else{
		    							    	Ext.MessageBox.alert('alert', 'no ha seleccionado una variable');
		    							    }                                                                          
				                        if(afuera!=temporal){
				                        	alert("temporal != afuera");
							            	temporal=afuera;
							            
		    							}else{
		    								alert("temporal == afuera");							            
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
	                        fieldLabel: '<s:text name="productos.config.expresion.descripcion"/>',
	                        width: '250',
	                        labelSeparator:'',
	                        //allowBlank: true,
	                        maxLength:'2000'
	        });


	function detailsUpdate(text){			
			if(!detailEl){
    			var bd = Ext.getCmp('details-panel').body;
    			bd.update('').setStyle('background','#fff');
    			detailEl = bd.createChild(); //create default empty div
    		}
    		detailEl.hide().update("Details:"+text).slideIn('l', {stopFx:true,duration:.2});
	}

    var tab2 = new Ext.FormPanel({
					region:'center',
					labelAlign: 'left',
			        frame: true,
			        margins:'0 0 0 5',
			        url:'expresiones/AgregarExpresion.action',
			        bodyBorder:false,
			        id:'forma-expresiones',
			        bodyStyle:'padding:0 0 0',
			        height:500,			        
			        width: 500,
			        items:[ {xtype:'hidden', id:'hidden-expresion-session-expresion',name:'codigoExpresionSession',value:codigoExpresionSession},
			        		{xtype:'hidden', id:'hidden-codigo-expresio-expresion',name:'codigoExpresion'},{
		   				layout:'form',
		   				margins:'0 5 5 5',
		   				//height:300,
			        	bodyStyle:'padding:5px 5px 0',
		   				border:false,
		   				frame:false,
		   				title: '<s:text name="productos.expresiones.subtitulo.valorDefecto"/>',
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
						                		items: [{
						                				id:'switch-recalcular-expresion-simple',
								    	                xtype:'checkbox',
							    		                fieldLabel: 'Last Name',
							            		        hideLabel:true,
							                    		boxLabel:'<s:text name="productos.config.expresion.recalcula"/>',
							                    		name:'switchRecalcular'
		        	        						},{
							            		        xtype:'button',
		                							    text:'<s:text name="productos.config.expresion.btn.comprobarSintaxis"/>',
		                							    handler: function () {
		                							    	tab2.form.submit({
		                							    		url: 'expresiones/ValidarExpresion.action',			      
								            					failure: function(form, action) {
												    				Ext.MessageBox.alert('Error Message', Ext.util.JSON.decode(action.response.responseText).mensajeValidacion);
																},
																success: function(form, action) {
												    				//Ext.MessageBox.alert('Confirm', action.result.info);	
												    				//alert("Exito!!");
												    				//Ext.getCmp('hidden-valor-defecto-atributos-variables').setValue("-1");					   
												    				Ext.MessageBox.alert('Mensaje', Ext.util.JSON.decode(action.response.responseText).mensajeValidacion);
																}
							        						});
		                							    }
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
            				   				    text:'<s:text name="productos.config.expresion.btn.agregarVar"/>',
               								    handler:function(){   
               								    		//alert(Ext.getCmp('hidden-codigo-expresio-expresion').getValue());            								    		
                					    				NuevaVariableExpresiones(Ext.getCmp('hidden-codigo-expresio-expresion').getValue(), dsComboV);
				               				    }					        		     
						                }]
						            },{
						                columnWidth:.35,
						                layout: 'form',
						                border:false,
					    	            items: [{
					        	       	        xtype:'button',
		                					    text:'<s:text name="productos.config.expresion.btn.editarVar"/>',
		                					    id:'editar-variable'		     
						                }]
					            	},{
						                columnWidth:.3,
						                layout: 'form',
					    	            border:false,
					        	        
					            	    items: [{
					               		        xtype:'button',
		           			   				    text:'<s:text name="productos.config.expresion.btn.eliminarVar"/>',
        	       							    handler:function(){
        	       							    		var valorCombo=comboVariables.getValue();
        	       							    		if(comboVariables.isDirty()){
			    	           							    comboVariables.reset();        	       							    			
	        	       							    		var url4delete='expresiones/EliminarVariable.action?claveSeleccionada='+valorCombo;    	    	       							    		
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
		    	           							    }else{
		    	           							    	Ext.MessageBox.alert('Error', 'No ha seleccionado una variable');
		    	           							    }
			        	       				    }					        		     
					    	            }]
					        	}]
					    
						    }]
			    	},{layout:'form',hegth:'10'},new Ext.Panel({
										id: 'details-panel',
								        title: '<s:text name="productos.expresiones.subtitulo.detalles"/>',
								        margins:'5 0 0 0',
								        border:true,	
								        heigth:'100',        
								        //region: 'south',
							    	    bodyStyle: 'padding-bottom:15px;background:#eee;',
										autoScroll: true,
										html: '<p class="details-info">Al seleccionar uno de los elementos del árbol aquí aparecerá su detalle.<br><br> Al darle doble click al elemento se agregará al campo de descripción </p>'
					    	})]
				});
				

/************************************
nueva ventana
***********************************/
	
    // define window and show it in desktop
 var wind = new Ext.Window({
            title: '<s:text name="productos.expresiones.titulo.principal"/>',
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
						        title: '<s:text name="productos.expresiones.subtitulo.funciones"/>',
						        xtype: 'treepanel',
			    			    width: 200,
						        autoScroll: true,
						        split: true,
			    			    loader: new Ext.tree.TreeLoader({
						               dataUrl:'<s:url value="expresiones/FuncionesArbol.action"/>'
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
	 										detailsUpdate(n.attributes.descripcion);
							            },
							            dblclick: function(n){
							            	var valor = descripcion.getValue();
							            	if(descripcion.isDirty()){
							            		valor = valor+" ";
							            	}
	 										descripcion.setValue(valor+n.attributes.funcion);
	 									}
						        }
						    },{    
						        title: '<s:text name="productos.expresiones.subtitulo.variableTemporal"/>',
						        xtype: 'treepanel',
						        width: 200,
			    			    autoScroll: true,
						        split: true,
						        loader: new Ext.tree.TreeLoader({
						               dataUrl:'<s:url value="expresiones/VariablesTemporalesArbol.action"/>'
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
							            },
							            dblclick: function(n){
							            	 var valor= descripcion.getValue();
							            	if(descripcion.isDirty()){
								            	valor=valor+" &";
	 										}else{
	 											valor=valor+"&";	 										
	 										}
	 										descripcion.setValue(valor+n.attributes.funcion); 
	 									}
						        }
						    }]
		
										
				},tab2],
				buttons:[{
  						text:'<s:text name="productos.config.expresion.btn.guardar"/>',
  						handler: function(){//alert(Ext.getCmp('hidden-expresion-session-expresion').getValue());
  							if(descripcion.getValue().length>0) {
					 		        tab2.form.submit({			      
								            waitTitle:'<s:text name="ventana.expresiones.proceso.mensaje.titulo"/>',
					            			waitMsg:'<s:text name="ventana.expresiones.proceso.mensaje"/>',
								            failure: function(form, action) {
												    //Ext.MessageBox.alert('Error Message', Ext.util.JSON.decode(action.response.responseText).mensajeDelAction);
												    Ext.MessageBox.alert('Mensaje de Error', 'failure!');
											},
											success: function(form, action) {
												    //Ext.MessageBox.alert('Confirm', action.result.info);	
												    //alert("Exito!!");
												    //Ext.getCmp('hidden-valor-defecto-atributos-variables').setValue("-1");					   
												    wind.close();
											}
							        });                   
	        		        } else{
	        		        		descripcion.markInvalid('<s:text name="productos.valida.expresion.descripcion"/>');
									Ext.MessageBox.alert('Error', 'Favor de llenar los datos requeridos');
							}     
  						}
  					},{
  						text:'<s:text name="productos.config.expresion.btn.cancelar"/>',
  						handler:function(){ 
  						//alert(Ext.getCmp('hidden-expresion-session-expresion').getValue()); 						
  							wind.close();
  						}
  				}]
        });


        wind.show();
};
</script>