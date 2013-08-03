<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->

<jsp:include page="/resources/scripts/jsp/productos/tablaApoyo5Claves/tablaApoyo5Claves.jsp" flush="true" />

<!-- SOURCE CODE -->
<script type="text/javascript">    
NuevaVariableExpresiones= function(variableDataStore, row) {
	var dsComboTabla
	var dsComboColumna;
	var dsComboClave;
	if(row!=null){
		var recVariableExpresion = variableDataStore.getAt(row);
                           
	    variableDataStore.on('load', function(){ 
	    		var claveTabla = recVariableExpresion.get('tabla');
	    		var claveColumna = recVariableExpresion.get('columna');
	    		dsComboTabla.load();
	    		dsComboColumna.baseParams['tabla'] = claveTabla;
                dsComboColumna.load({
                		callback : function(r,options,success) {
		                        if (!success) {
        		                        //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
                                       	dsComboColumna.removeAll();
                                }
                        }
                });
                dsComboClave.baseParams['tabla'] = claveTabla;                
                dsComboClave.baseParams['columna'] = claveColumna;
                dsComboClave.load({
                		callback : function(r,options,success) {
		                        if (!success) {
        		                        //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
                                       	dsComboClave.removeAll();
                                }
                        }
                });
			    Ext.getCmp('id-forma-variables-expresion').getForm().loadRecord(recVariableExpresion);                                                               
    	});
        variableDataStore.load();      
	}   
	
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
           {name: 'value', type: 'string',mapping:'value'}      
        ]),
		remoteSort: true,		
        baseParams: {tabla:'undefined'}
    });
    dsComboColumna.load();
   
    dsComboClave = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: '<s:url namespace="expresiones" action="ComboClave" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaClaveJson',
            id: 'comboHijo'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}      
        ]),
		remoteSort: true,		
        baseParams: {tabla:'undefined',
         			 columna:'undefined'}
    });
  	dsComboClave.load();

  	var columnModel = new Ext.grid.ColumnModel([
        {id:'clave',header: "Clave", dataIndex: 'clave'},
        {header: "Expresion", dataIndex: 'expresion'},
        {header: "Recalcular", dataIndex: 'recalcular'}
    ]);

    var recalcular= new Ext.form.Checkbox({
   			id:'recalcular-check-variables-expresion',
            name:'recalcular',
            boxLabel: 'Recalcular',
          	hideLabel:true
    });
   
   	var nombre= new Ext.form.TextField({   					
                    fieldLabel: 'Nombre',
                    name: 'nombre',
                    width:'150' 
   	});
   	
   	var expresion= new Ext.form.TextField({fieldLabel:'Expresion',disabled:true,name:'expresion', maxLength :'240'});
	var comboTabla =new Ext.form.ComboBox({
							id:'id-combo-tabla-variable-expresion',
    	                    tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsComboTabla,
						    displayField:'value',
						    valueField: 'key',
					    	typeAhead: true,
					    	mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'Selecciona un inciso',
					    	selectOnFocus:true,
						    fieldLabel: "Tabla",
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
									comboColumna.reset();
									comboClave.reset();
									dsComboClave.removeAll();
									expresion.reset();
									expresion.disable();
									recalcular.reset();
									recalcular.disable();
									dsComboColumna.baseParams['tabla'] = this.getValue();
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
					    	mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'Selecciona un inciso',
					    	selectOnFocus:true,
						    fieldLabel: "Columna",
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
									comboClave.reset();
									clave.reset();
									expresion.reset();
									expresion.disable();
									recalcular.reset();
									recalcular.disable();			            													
									dsComboClave.baseParams['tabla'] = Ext.getCmp('id-combo-tabla-variable-expresion').getValue();                
						            dsComboClave.baseParams['columna'] = this.getValue();
						            dsComboClave.load({
					                		callback : function(r,options,success) {
		            					            if (!success) {
						       		                        //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
                        					               	dsComboClave.removeAll();
						                            }
					                        }
					                });
					                /*
									formPanel.form.submit({	
											url:'expresiones/Test.action',		      
								            //waitMsg:'Salvando datos...',
								            success:function(form,action){
										            comboClave.reset();
													dsComboClave.load({params:{start:0, limit:10}});
													clave.reset();
													expresion.reset();
													expresion.disable();
													recalcular.reset();
													recalcular.disable();			            
								            }
								   });*/
							}
					    	
			});
			
	var comboClave =new Ext.form.ComboBox({
    	                    tpl: '<tpl for="."><div ext:qtip="{value}." class="x-combo-list-item">{value}</div></tpl>',
							store: dsComboClave,
						    displayField:'value',
						    valueField:'key',
					    	typeAhead: true,
					    	mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'Selecciona un inciso',
					    	selectOnFocus:true,
						    fieldLabel: "Clave",
					    	name:"clave",
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
									//clave.setValue(this.getValue());		
									expresion.reset();
									expresion.setDisabled(false);
									recalcular.reset();
									recalcular.setDisabled(false);
							}
					    	
			});

    var formPanel = new Ext.FormPanel({
    	id:'id-forma-variables-expresion',
        labelAlign: 'right',
        frame:true,
        region:'center',
        layout:'column',        
        bodyStyle:'padding:5px 5px 0',
        width: 570,
        //url:'expresiones/AgregarVariable.action',
        items: [{xtype:'hidden', id:'hidden-codigo-tabla-variable-expresion', name:'tabla'},
		        {xtype:'hidden', id:'hidden-codigo-columna-variable-expresion', name:'columna'},
		        {
		        	layout:'form',
		        	border:false,
		        	items:[{
		        		columnWith:.5,
    		    		layout:'form',
        				labelAlign: 'right',
        				border:false,
        				items:[
	        				nombre,
	   		    			comboTabla,
	   		    	        comboColumna,
			    		    comboClave,
			    		    expresion]       
			    	    },{
    	    			columnWith:.5,
	        			layout:'form',
    	    			labelAlign: 'right',
        				border:false,
	        			items:[recalcular]       
			    	}]
			    },{
		        	layout:'form',
		        	border:false,
		        	items:[{
		        			columnWith:.4,
	    		    		layout:'form',
	    		    		width:50,
    	    				border:false
        				},{
    		    			columnWith:.5,
	        				layout:'form',
    	    				labelAlign: 'right',
        					border:false,
	        				items:[{layout:'form',border:false,height:'10'},{xtype:'button',text:'tabla 5 claves',handler:function(){TablasDeApoyo(dsComboTabla);}}]       
			    	}]
			    }
  		]
    });
 	
    // define window and show it in desktop
 var wind = new Ext.Window({
            title: 'Expresiones',
            closable:true,
            buttonAlign:'center',
            width:430,
            height:250,
            //border:false,
            plain:true,
            layout: 'border',
            modal:true,
			items:[formPanel],
  			buttons:[{
  					text:'Asociar clave',
  					handler: function(){  							
					 		        formPanel.form.load({	
					 		        		url:'expresiones/AgregarClave.action?clave='+comboClave.getRawValue()+'&&expresion='+expresion.getValue()+'&&recalcular='+recalcular.getValue(),	      
								            waitMsg:'Salvando datos...',
								            failure: function(form, action) {
												    //Ext.MessageBox.alert('Error Message', 'cheiser');
											},
											success: function(form, action) {
												    //Ext.MessageBox.alert('Confirm',"rifas chavo");						   												    
											}
							        });                   	        		             
  						}
  					},{
  					text:'Guardar',
  					handler: function(){
  							var boolean1=true;
  							var boolean2=true;
  							var boolean3=true;
  							var boolean4=true;
		  					if(nombre.getValue().length==0) {
		  						nombre.markInvalid("Este dato es requerido");
		  						boolean1=false;
		  					}
		  					if(comboTabla.getValue().length==0) {
		  						comboTabla.markInvalid("Este dato es requerido");
		  						boolean2=false;
		  					}
		  					if(comboColumna.getValue().length==0) {
		  						comboColumna.markInvalid("Este dato es requerido");
		  						boolean3=false;
		  					}
		  					if(comboClave.getValue().length==0) {
		  						comboClave.markInvalid("Este dato es requerido");
		  						boolean4=false;
		  					}
  							if(boolean1&&boolean2&&boolean3&&boolean4) {
					 		        Ext.getCmp('hidden-codigo-tabla-variable-expresion').setValue(Ext.getCmp('id-combo-tabla-variable-expresion').getValue());
					 		        Ext.getCmp('hidden-codigo-columna-variable-expresion').setValue(Ext.getCmp('id-combo-columna-variable-expresion').getValue());
					 		        
					 		        formPanel.form.submit({	
							 		        url:'expresiones/AgregarVariable.action',		      
								            waitMsg:'Salvando datos...',
								            failure: function(form, action) {
												    Ext.MessageBox.alert('Error Message', Ext.util.JSON.decode(action.response.responseText).mensajeDelAction);
											},
											success: function(form, action) {
												    //Ext.MessageBox.alert('Confirm', action.result.info);						   
												    variableDataStore.load({params:{start:0, limit:10}});
												    wind.close();
											}
							        });                  
	        		        } else{
									Ext.MessageBox.alert('Errors', 'Favor de llenar todos los datos requeridos');
							}     
  						}
  					},{
  						text:'Cancelar',
  						handler:function(){  						
  							wind.close();
  						}
  				}]
        });


        wind.show();
};
</script>