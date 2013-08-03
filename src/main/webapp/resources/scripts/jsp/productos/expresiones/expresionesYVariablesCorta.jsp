<%@ include file="/taglibs.jsp"%>
<!-- SOURCE CODE -->
<jsp:include page="/resources/scripts/jsp/productos/tablaApoyo5Claves/tablaApoyo5Claves.jsp" flush="true" />

<script type="text/javascript">
ExpresionesVentana2 = function(codigoExpresion, codigoExpresionSession){
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
Editar Expresion
****************************************/
/************************************************************************
variables locales de expresion
************************************************************************/
	var dsComboTabla
	var dsComboColumna;
	var dsComboClave;

	//alert(codigoExpresionBase);
	dsComboTabla = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: '<s:url namespace="expresiones" action="ComboTabla" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaTablaJson',
            id: 'comboAbuelo'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}      
        ]),
		remoteSort: true
    });
  	dsComboTabla.load();
  	
  	dsComboColumna = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: '<s:url namespace="expresiones" action="ComboColumna" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaColumnaJson',
            id: 'dsComboColumna'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'},
           {name: 'nick', type: 'string',mapping:'nick'}      
        ]),
		remoteSort: true,		
        baseParams: {tabla:'undefined'}
    });
    //dsComboColumna.load();



    dsComboClave = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: '<s:url namespace="expresiones" action="ComboClave" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaClave',
            id: 'comboHijo'
	        },[
           {name: 'clave', type: 'string',mapping:'clave'},
           {name: 'expresion', type: 'string',mapping:'expresion'},             
           {name: 'switchRecalcular', type: 'boolean',mapping:'switchRecalcualar'},
           {name: 'recalcular', type: 'string',mapping:'recalcular'},
           {name: 'idBase', type: 'string',mapping:'idBase'},
           {name: 'codigoSecuencia', type: 'string',mapping:'codigoSecuencia'}          
        ]),
		remoteSort: true,		
        baseParams: {tabla:'undefined',
         			 columna:'undefined',
         			 nombreVariableLocal:'undefined'}
    });
  	//dsComboClave.load();
  	
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
           {name: 'descripcionColumna', type: 'string',mapping:'descripcionColumna'},
           {name: 'switchFormato', type: 'string',mapping:'switchFormato'},
           
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
					    	//labelAlign: 'top',
					    	allowBlank:false,
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
					                    if(comboVariables.getValue()!=null && comboVariables.getValue()!="" && comboVariables.getValue()!="undefined"){
						                        if( Ext.getCmp('id-forma-variables-expresion')){
						                        	recVariableExpresion = dsComboV.getAt(index);
						                        	Ext.getCmp('id-forma-variables-expresion').getForm().loadRecord(recVariableExpresion);
						                        	var claveVariableLocal = recVariableExpresion.get('nombre');
						                        	//alert('claveVariableLocal1 = ' + claveVariableLocal);
						                        	//claveVariableLocal = this.getValue();
						                        	//alert('claveVariableLocal2 = ' + claveVariableLocal);
										    		Ext.getCmp('hidden-codigo-nombre-variable-local').setValue(claveVariableLocal);  
										    		var claveTabla = recVariableExpresion.get('tabla');
										    		var claveColumna = recVariableExpresion.get('columna');										    		
										    		Ext.getCmp('id-combo-tabla-variable-expresion').setValue(claveTabla);
										    		Ext.getCmp('hidden-codigo-columna-variable-expresion').setValue(claveColumna);										    		
										    		dsComboTabla.load();
										    		dsComboColumna.baseParams['tabla'] = claveTabla;
									                dsComboColumna.load({
								                		callback : function(r,options,success) {
									                        if (!success) {
						                                       	dsComboColumna.removeAll();
								                            }
								                        }
									                });
									                dsComboClave.baseParams['tabla'] = claveTabla;                
									                dsComboClave.baseParams['columna'] = claveColumna;
									                dsComboClave.baseParams['nombreVariableLocal'] = claveVariableLocal;
													dsComboClave.load({
								                		callback : function(r,options,success) {
									                        if (!success) {
						        		                        //Ext.MessageBox.alert('Aviso','No se encontraron registros');  
						                                       	dsComboClave.removeAll();
							                                }
								                        }
									                });
						                        }
       				            	       	}else{
		    							    	Ext.MessageBox.alert('alert', 'no ha seleccionado una variable');
		    							    }  
							}
			});

  	var columnModel = new Ext.grid.ColumnModel([
        {id:'clave',header: "Clave", dataIndex: 'clave'},
        {header: "Expresion", dataIndex: 'expresion'},
        {header: "Recalcular", dataIndex: 'switchRecalcular'}
    ]);

    var recalcular= new Ext.form.Checkbox({
   			id:'recalcular-check-variables-expresion',
            name:'recalcular',
            boxLabel: '<s:text name="productos.variableLoc.recalcular"/>',
          	hideLabel:true
    });

   	var expresion= new Ext.form.TextField({fieldLabel:'Expresion',disabled:true,name:'expresion', maxLength :'240'});
	var comboTabla =new Ext.form.ComboBox({
							id:'id-combo-tabla-variable-expresion',
    	                    tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsComboTabla,
						    displayField:'value',
						    valueField: 'key',
					    	typeAhead: true,
					    	labelAlign: 'top',
					    	mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="productos.valida.variableLoc.tabla"/>',
					    	selectOnFocus:true,
				          	allowBlank:false,
				          	blankText : '<s:text name="productos.valida.variableLoc.tabla.req"/>',
						    fieldLabel: '<s:text name="productos.variableLoc.tabla"/>',
						    listWidth:250,
					    	name:"descripcionTabla",
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
									valor = record.get('key');
									Ext.getCmp('hidden-codigo-tabla-variable-expresion').setValue(valor);	
									comboColumna.reset();
									//comboClave.reset();
									dsComboClave.removeAll();									
									dsComboColumna.baseParams['tabla'] = valor;
					                dsComboColumna.load({
                							callback : function(r,options,success) {
							                        if (!success) {
        		    					                    //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
					                                       	dsComboColumna.removeAll();
                    					            }
					                        }
					                });
					               
							}
			});
	
	var comboColumna =new Ext.form.ComboBox({
							id:'id-combo-columna-variable-expresion',
    	                    tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsComboColumna,
						    displayField:'value',
						    valueField: 'key',
					    	typeAhead: true,
					    	labelAlign: 'top',
				          	allowBlank:false,
				          	blankText : '<s:text name="productos.valida.variableLoc.columna.req"/>',
					    	mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="productos.valida.variableLoc.columna"/>',
					    	selectOnFocus:true,
						    fieldLabel: '<s:text name="productos.variableLoc.columna"/>',
					    	name:"descripcionColumna",
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
									valor = record.get('key');
									Ext.getCmp('hidden-switch-formato-variable-expresion').setValue(record.get('nick'));	
									Ext.getCmp('hidden-codigo-columna-variable-expresion').setValue(valor);		            													
									dsComboClave.baseParams['tabla'] = Ext.getCmp('id-combo-tabla-variable-expresion').getValue();                
						            dsComboClave.baseParams['columna'] = valor;
						            dsComboClave.baseParams['nombreVariableLocal'] = 'undefined';
						            dsComboClave.load({
					                		callback : function(r,options,success) {
		            					            if (!success) {
						       		                        //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
                        					               	dsComboClave.removeAll();
						                            }
					                        }
					                });
					                
							}
					    	
			});

    var checkColumn = new Ext.grid.CheckColumn({
       header: "Recalcular",
       dataIndex: 'switchRecalcular',
       width: 70,
       align:'center'
    });
    
	cmClaves = new Ext.grid.ColumnModel([{
           id:'clave',
           header: "Clave",
           dataIndex: 'clave',
           width: 100,
           editor: new Ext.form.TextField({
               allowBlank: false
           })
        },{
           header: "Expresión",
           dataIndex: 'expresion',
           width: 240,
           editor: new Ext.form.TextField({
               allowBlank: false
           })
        },checkColumn
    ]);
 cmClaves.setEditable(0,false);
 gridEditableClaves = new Ext.grid.EditorGridPanel({
    	//id:'grid-editable-claves',
        store: dsComboClave,
        cm: cmClaves,
        autoScroll:true,
        width: 430,
        height:175,
        autoExpandColumn:'clave',
        title:'<s:text name="productos.variableLoc.subtitulo.claves"/>',
        collapsible:true,
        frame:true,
        plugins:checkColumn,
        clicksToEdit:1,
		viewConfig: {
	        forceFit: true
	    }
    });
    var formPanelVariablesLocales = new Ext.FormPanel({
    	id:'id-forma-variables-expresion',
        //frame:true,
        //region:'center',
        //layout:'column',        
        //bodyStyle:'padding:5px 5px 0',
        heigth:300,
        width: 450,
        frame:true,
        border:false,
        url:'expresiones/AgregarVariable.action',
        items: [{xtype:'hidden', id:'hidden-codigo-tabla-variable-expresion', name:'tabla'},
		        {xtype:'hidden', id:'hidden-codigo-columna-variable-expresion', name:'columna'},
		        {xtype:'hidden', id:'hidden-switch-formato-variable-expresion', name:'switchFormato'},
		        {xtype:'hidden', id:'hidden-codigo-nombre-variable-local', name:'codigoNombreVariableLocal'},
		        {xtype:'hidden', id:'hidden-codigo-edita-variable-expresion', name:'editaVariableLocal'},
		        {
		        	layout:'column',
		        	width: 450,
	    	    	border:false,
	        		items:[{
			        		columnWidth:.68,
    			    		layout:'form',
        					border:false,
        					width:280,
        					items:[comboVariables]       
			    	    },{
    	    				columnWidth:.32,
	        				layout:'form',
        					border:false,
		        			items:[{
	               		        xtype:'button',
   			   				    text:'<s:text name="productos.config.expresion.btn.eliminarVar"/>',
   							    handler:function(){
   							    		var valorCombo=comboVariables.getValue();
   							    		if(comboVariables.isDirty()){
   	           							       
   	           							    var params="codigoExpresion=" + Ext.getCmp('hidden-codigo-expresio-expresion').getValue();
   	           							    params+= "&&claveSeleccionada="+valorCombo;    	       							    			
       							    		var url4delete='expresiones/EliminarVariable.action';    	    	       							    		
      							    		Ext.MessageBox.confirm('Mensaje','Realmente deseas eliminar el elemento?', function(btn) {
									            if(btn == 'yes'){   
												    tab2.form.load({	
														url: url4delete,
														params:params,
													    waitMsg:"Procesando...",
													    failure: function(form, action) {
														    comboVariables.reset();
													    	dsComboV.load();
													    	comboTabla.reset();
													    	dsComboTabla.load();
													    	comboColumna.reset();
												    		dsComboColumna.baseParams['tabla'] = '';
											                dsComboColumna.load({
										                		callback : function(r,options,success) {
											                        if (!success) {
						        		                               	dsComboColumna.removeAll();
								        		                    }
								                		        }
											                });
											                dsComboClave.baseParams['tabla'] = '';                
											                dsComboClave.baseParams['columna'] = '';
											                dsComboClave.baseParams['nombreVariableLocal'] = '';
															dsComboClave.load({
								        		        		callback : function(r,options,success) {
									            		            if (!success) {
						        		                		        //Ext.MessageBox.alert('Aviso','No se encontraron registros');  
						                                       			dsComboClave.removeAll();
									                                }
										                        }
											                });
													    	Ext.MessageBox.alert('Status', 'La variable se ha eliminado correctamente');
														}/*,
														success: function(form, action) {
														    Ext.MessageBox.alert('Status', 'Elemento borrado');						    
														    dsComboV.load();						    
														}*/
													});         	 
												}
											});
           							    }else{
		    	           				   	Ext.MessageBox.alert('Error', 'No ha seleccionado una variable');
		    	           				}
        	       				}					        		     
				            }]       
			    	}]
			    },{
		        	layout:'column',
		        	width: 450,
	    	    	border:false,
	        		items:[{
			        		columnWidth:.68,
    			    		layout:'form',
        					border:false,
        					width:280,
        					items:[comboTabla]       
			    	    },{
    	    				columnWidth:.32,
	        				layout:'form',
        					border:false,
		        			items:[{
		        				xtype:'button',
		        				text:'<s:text name="productos.config.variableLoc.btn.5claves"/>',
		        				handler:function(){
		        					TablasDeApoyo(dsComboTabla);
		        					}
		        			}]       
			    	}]
			    },{
		        	layout:'column',
		        	width: 450,
		        	border:false,
		        	items:[{
			        		columnWidth:.68,
    			    		layout:'form',
        					border:false,
        					width:280,
        					items:[comboColumna]       
			    	    },{
    	    				columnWidth:.32,
	        				layout:'form',
        					border:false,
		        			items:[{
		        				xtype:'button',
		        				text:'Guardar Variable',
		        				handler:function(){
		        					if(formPanelVariablesLocales.form.isValid()){
			        					if(validarClavesObligatoriasExpresion()){
			        						Ext.getCmp('hidden-codigo-nombre-variable-local').setValue(comboVariables.getValue());
			        						//alert(Ext.getCmp('hidden-codigo-nombre-variable-local').getValue());
				        					var params="codigoExpresion=" + Ext.getCmp('hidden-codigo-expresio-expresion').getValue();
				        					var maxlengthRecords=dsComboClave.getTotalCount();
				        					var modifiedRecords = dsComboClave.getModifiedRecords();
							 				for (var i=0; i<maxlengthRecords; i++) {				
							 					var validaModificados = true;					
							 					if(modifiedRecords.length>0){
							 						for(var j=0; j<modifiedRecords.legth;j++){
							 							if(dsComboClave.getAt(i).get('codigoSecuencia') == modifiedRecords[j].get('codigoSecuencia')){
							 								validaModificados = false;
							 								params+="&&listaClave["+i+"].codigoSecuencia="+modifiedRecords[j].get('codigoSecuencia')+
															"&&listaClave["+i+"].clave="+modifiedRecords[j].get('clave')+ 
															"&&listaClave["+i+"].expresion="+modifiedRecords[j].get('expresion')+
															"&&listaClave["+i+"].recalcular="+((modifiedRecords[j].get('switchRecalcular')== true)?'S':'N'); 											
							 							}
							 						}
							 					}
							 					if(validaModificados){
													params+="&&listaClave["+i+"].codigoSecuencia="+dsComboClave.getAt(i).get('codigoSecuencia')+
															"&&listaClave["+i+"].clave="+dsComboClave.getAt(i).get('clave')+ 
															"&&listaClave["+i+"].expresion="+dsComboClave.getAt(i).get('expresion')+
															"&&listaClave["+i+"].recalcular="+((dsComboClave.getAt(i).get('switchRecalcular')== true)?'S':'N'); 											
												}
											}
											//alert(params);
				        					formPanelVariablesLocales.form.submit({			      
									            waitTitle:'<s:text name="ventana.expresiones.proceso.mensaje.titulo"/>',
						            			waitMsg:'<s:text name="ventana.expresiones.proceso.mensaje"/>',
						            			params:params,
									            failure: function(form, action) {
													    Ext.MessageBox.alert('Mensaje de Error', Ext.util.JSON.decode(action.response.responseText).mensajeDelAction);
													    //Ext.MessageBox.alert('Error Message', 'failure');
												},
												success: function(form, action) {
													Ext.MessageBox.alert('Aviso', 'Variable guardada exitosamente');
					        						dsComboClave.commitChanges();
													dsComboV.load();
												}
									        });
								        }
							        }else{
										Ext.MessageBox.alert('Error', 'Favor de llenar datos requeridos');
									} 
		        				}
		        			}]       
			    	}]
			    },gridEditableClaves
				  		]
    				});
/****************************************************************************************
variables locales de expresion
****************************************************************************************/

/****************************************
Expresiones
*****************************************/

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
					//region:'center',
					labelAlign: 'left',
			        frame: true,
			        //margins:'0 0 0 5',
			        url:'expresiones/AgregarExpresion.action',
			        bodyBorder:false,
			        border:false,
			        id:'forma-expresiones',
			        //bodyStyle:'padding:0 0 0',
			        height:120,			        
			        width: 450,
			        items:[ {xtype:'hidden', id:'hidden-expresion-session-expresion',name:'codigoExpresionSession',value:codigoExpresionSession},
			        		{xtype:'hidden', id:'hidden-codigo-expresio-expresion',name:'codigoExpresion'},{
		   				layout:'form',
		   				//margins:'0 5 5 5',
		   				//height:300,
			        	//bodyStyle:'padding:5px 5px 0',
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
												    				Ext.MessageBox.alert('Mensaje de Error', Ext.util.JSON.decode(action.response.responseText).mensajeValidacion);
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
					        }]
			    	}]
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
            height:580,
            //border:false,
            plain:true,
            layout: 'border',
            modal:true,
			items:[{
                    region:'south',
                    split:true,
                    height: 100,
                    minSize: 100,
                    maxSize: 200,
                    collapsible: true,
                    frame:true,
                    title:'<s:text name="productos.expresiones.subtitulo.detalles"/>',
                    margins:'0 0 0 0',
		            items:[new Ext.Panel({
										id: 'details-panel',
								        //title: '<s:text name="productos.expresiones.subtitulo.detalles"/>',
								        margins:'5 0 0 0',
								        border:true,	
								        autoHeigth:true,
								        //heigth:'100',        
								        //region: 'south',
							    	    bodyStyle: 'padding-bottom:15px;background:#eee;',
										autoScroll: true,
										html: '<p class="details-info">Al seleccionar uno de los elementos del árbol aquí aparecerá su detalle.<br><br> Al darle doble click al elemento se agregará al campo de descripción </p>'
					    	})]
	                    
	                },{
						region:'west',
						heigth:'400',
						width:'200',
						minSize:'175',
						maxSize:'300',
						border:false,
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
		
										
				},{layout:'form',region:'center',boder:false,frame:true,items:[tab2,formPanelVariablesLocales]}],
				buttons:[{
  						text:'<s:text name="productos.config.expresion.btn.guardar"/>',
  						handler: function(){//alert(Ext.getCmp('hidden-expresion-session-expresion').getValue());
  							if(descripcion.getValue().length>0) {
					 		        tab2.form.submit({			      
								            waitTitle:'<s:text name="ventana.expresiones.proceso.mensaje.titulo"/>',
					            			waitMsg:'<s:text name="ventana.expresiones.proceso.mensaje"/>',
								            failure: function(form, action) {
												    Ext.MessageBox.alert('Mensaje de Error', Ext.util.JSON.decode(action.response.responseText).mensajeDelAction);
												    //Ext.MessageBox.alert('Error Message', 'failure');
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
        
   		function validarClavesObligatoriasExpresion(){
	    	var boleanoGE=false;
	    	//alert("validarClavesObligatoriasExpresion + dsComboClave.getTotalCount()="+dsComboClave.getTotalCount());
	    	if(dsComboClave.getTotalCount() != 0){
	    		var validaPrimero = false;
		    	var cuztomizedMessage='Favor de llenar las expresiones de: ';
		    	var maxlengthRecords=dsComboClave.getTotalCount();	    								
				for(var irec=0;irec<maxlengthRecords;irec++){				
						if(dsComboClave.getAt(irec).get('expresion').length==0 ){
							if(validaPrimero){
								cuztomizedMessage+=', ';								
							}
							boleanoGE=true;
							cuztomizedMessage+=dsComboClave.getAt(irec).get("clave") ;
							validaPrimero = true;
						}
				}
				if(boleanoGE){
					Ext.MessageBox.alert('Errors', cuztomizedMessage);
				}
			}
			return !boleanoGE;				
		}
};
</script>