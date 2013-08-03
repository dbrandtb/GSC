<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<script type="text/javascript" src="${ctx}/resources/scripts/utilities/vTypes/combo.js"></script>
<!-- SOURCE CODE -->
<jsp:include page="/resources/scripts/jsp/utilities/atributosVariables/listasDeValores/agregarValoresCargaManual.jsp" flush="true" />
<jsp:include page="/resources/scripts/jsp/utilities/atributosVariables/listasDeValores/eliminarValoresCargaManual.jsp" flush="true" />

<script type="text/javascript">
//*************EDITAR**********************
    // pre-define fields in the form
    var selIndex;
    var store;
    var otClave= new Ext.form.TextField({
                name:'otClave',
                fieldLabel: '<s:text name="def.datos.variables.listas.valores.valores.clave"/>',
                allowBlank: false,
                blankText : '<s:text name="def.datos.variables.listas.valores.valida.valor.cve.vacio"/>',
                width: 80
            	});
            
	var otValor = new Ext.form.TextField({
        		name: 'otValor',
        		fieldLabel: '<s:text name="def.datos.variables.listas.valores.valores.descripcion"/>',
        		allowBlank: false,
        		blankText : '<s:text name="def.datos.variables.listas.valores.valida.valor.desc.vacio"/>',
        		width: 160  
    			});   
	
    
        
    var formPanel = new Ext.form.FormPanel({
   		id:"formPanel",
        baseCls: 'x-plain',
        labelWidth: 115,
        url:'atributosVariables/CargaManual.action',
        defaultType: 'textfield',
        //collapsed : false,

        items: [
            otClave,
            otValor
    	]
    });

    // define window and show it in desktop
    var agregarValoresGridWindow = new Ext.Window({
        title: '<s:text name="lista.valores.datos.variables.subtitulo.cargaManual"/>',
       
        width: 350,
        height:150,
        minWidth: 350,
        minHeight: 150,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        modal : true,
  
        items: formPanel,
        

		buttons: [{
            text: '<s:text name="btn.datos.variables.listas.valores.guardar"/>', 
            handler: function() {
                // check form value 
                if (formPanel.form.isValid()) {
	 		        formPanel.form.submit({	
	 		        	url:'atributosVariables/CargaManual.action?selectedId='+selIndex,		      
			            waitTitle:'<s:text name="ventana.datos.variables.listas.valores.proceso.mensaje.titulo"/>',
					    waitMsg:'<s:text name="ventana.datos.variables.listas.valores.proceso.mensaje"/>',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('Status', 'Error al editar elemento');
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('Status', 'Elemento editado');
						    agregarValoresGridWindow.hide();
						    store.load();	    
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Error', 'Favor de llenar datos requeridos.');
				}             
	        }
        },{
            text: '<s:text name="btn.datos.variables.listas.valores.cancelar"/>',
            handler: function(){
            	agregarValoresGridWindow.hide();
            }
        }]
    });
	//**************************************************
	//***********LISTA DE VALORES***********************
   	var LIMPIA_SESION_CARGA_MANUAL="<s:url action='LimpiarSesionCargaManual' namespace='/atributosVariables'/>";
   	
   	var nombre= new Ext.form.TextField({
                name:'nombre',
                fieldLabel: '<s:text name="def.datos.variables.listas.valores.nombre"/>',
                allowBlank: false,
                blankText : '<s:text name="def.datos.variables.listas.valores.valida.nombre"/>',
                width: 80
            });
            
	var descripcion = new Ext.form.TextField({
        name: 'descripcion',
        fieldLabel: '<s:text name="def.datos.variables.listas.valores.descripcion"/>',
        allowBlank: false,
        blankText : '<s:text name="def.datos.variables.listas.valores.valida.descripcion"/>',
        width: 160  
    });   
    
    var numero = new Ext.form.NumberField({
        name: 'numero',
        fieldLabel: '<s:text name="def.datos.variables.listas.valores.num"/>',
        allowDecimals : false,
		allowNegative : false,
		disabled:true,
        //allowBlank: false,
        //blankText : 'Dato numerico requerido',
		width: 30  
    });   
   
	
	var tablaDeErroresCheck= new Ext.form.Checkbox({
                name:'enviaTablaError',
                boxLabel: '<s:text name="def.datos.variables.listas.valores.enviar"/>',
            	hideLabel:true,	
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
            });
            
	var modificarValoresCheck= new Ext.form.Checkbox({
                name:'modificaValor',
                boxLabel: '<s:text name="def.datos.variables.listas.valores.modificar"/>',
            	hideLabel:true,			
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	}
				            }
                 
            });     
            
     var clave = new Ext.form.TextField({
        fieldLabel: '<s:text name="def.datos.variables.listas.valores.clave"/>',
        allowBlank: false,
        blankText : '<s:text name="def.datos.variables.listas.valores.valida.clave"/>',
        name: 'clave',
        width: 160   
    });    
    
     var functionRadioAlfa= new Ext.form.Radio({
            				//allowBlank: false,
            				name:'formatoClave',
            				checked:true,
            				fieldLabel: '<s:text name="def.datos.variables.listas.valores.formato"/>',            				
            				boxLabel: '<s:text name="def.datos.variables.listas.valores.alfanumerico"/>',            				
  	  	       				onClick: function(){			            		
            					if(this.getValue()){					            	            						
            						this.setRawValue("A");
				            	}
			            	}
            });   
            
    var functionRadioNumeric= new Ext.form.Radio({
    						//allowBlank: false,
            				name:'formatoClave',
            				labelSeparator : "",
            				boxLabel: '<s:text name="def.datos.variables.listas.valores.numerico"/>',
            				hideLabel:true,        
  	  	       				onClick: function(){			            		
            					if(this.getValue()){					            	            						
            						this.setRawValue("N");
				            	}
			            	}
            });
         
     var hideTextMaximo= new Ext.form.NumberField({
				            
            				name:'maximoClave',
            				fieldLabel: '<s:text name="def.datos.variables.listas.valores.maximo"/>',
            				allowDecimals : false,
				            allowNegative : false,
        				    allowBlank: false,
        				    blankText : '<s:text name="def.datos.variables.listas.valores.valida.dato.req"/>',            				
            				hideParent:true,
            				width:30	
            							       
           });
           
     var hideTextMinimo= new Ext.form.NumberField({
				            
            				name:'minimoClave',
            				fieldLabel: '<s:text name="def.datos.variables.listas.valores.minimo"/>',
            				allowDecimals : false,
				            allowNegative : false,
        				    allowBlank: false,
        				    blankText : '<s:text name="def.datos.variables.listas.valores.valida.dato.req"/>',            				
            				hideParent:true,            				
            				width:30	
            							       
           });    
         
      var descripcion2 = new Ext.form.TextField({
        name: 'descripcionFormato',
        fieldLabel: '<s:text name="def.datos.variables.listas.valores.desc.descripcion"/>',
        allowBlank: false,
        blankText : '<s:text name="def.datos.variables.listas.valores.valida.descripcion"/>',
        width: 160  
    	   });
    	   
	  var functionRadioAlfa2= new Ext.form.Radio({
	  						//allowBlank: false,
            				name:'formatoDescripcion',
            				fieldLabel: '<s:text name="def.datos.variables.listas.valores.formato"/>',            				
            				boxLabel: '<s:text name="def.datos.variables.listas.valores.alfanumerico"/>',  
            				checked:true,          				
  	  	       				onClick: function(){			            		
            					if(this.getValue()){					            	            						
            						this.setRawValue("A");
				            	}
			            	}
            });   
            
      var functionRadioNumeric2= new Ext.form.Radio({
            				//allowBlank: false,
            				name:'formatoDescripcion',
            				labelSeparator : "",
            				boxLabel: '<s:text name="def.datos.variables.listas.valores.numerico"/>',
            				hideLabel:true,            				
  	  	       				onClick: function(){			            		
            					if(this.getValue()){					            	            						
            						this.setRawValue("N");
				            	}
			            	}
            });
            
       var hideTextMaximo2= new Ext.form.NumberField({
				            
            				name:'maximoDescripcion',
            				fieldLabel: '<s:text name="def.datos.variables.listas.valores.maximo"/>',
            				allowDecimals : false,
				            allowNegative : false,
        				    allowBlank: false,
        				    blankText : '<s:text name="def.datos.variables.listas.valores.valida.dato.req"/>',            				
            				hideParent:true,            				
            				width:30	
            							       
           });
           
     var hideTextMinimo2= new Ext.form.NumberField({
				            
            				name:'minimoDescripcion',
            				fieldLabel: '<s:text name="def.datos.variables.listas.valores.minimo"/>',
            				allowDecimals : false,
				            allowNegative : false,
        				    allowBlank: false,
        				    blankText : '<s:text name="def.datos.variables.listas.valores.valida.dato.req"/>',            				
            				hideParent:true,            				
            				width:30	
            							       
           });     
                	         
         
creaListasDeValores = function(dstore,edita,row) {   
 //***********************************************************
 
  
  function test(){        		        				
 			url='atributosVariables/ListaCargaManual.action' 			 		 			
 			store = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: url
        		}),
        		reader: new Ext.data.JsonReader({
            	root:'listaCargaManual',   
            	totalProperty: 'totalCount',
            	id: 'key'
            	         	
	        	},[
	        		{name: 'otClave',  type: 'string',  mapping:'key'},
	        		{name: 'otValor',  type: 'string',  mapping:'value'}
	        		            
				]),
			autoLoad:true,
			remoteSort: true,
			baseParams:{tabla:''}
    	});
    	store.setDefaultSort('otValor', 'desc');
    	//store.load();
		return store;
 	}
 	
 	function toggleDetails(btn, pressed){
       	var view = grid.getView();
       	view.showPreview = pressed;
       	view.refresh();
    	}
		
		var cm = new Ext.grid.ColumnModel([
		    new Ext.grid.RowNumberer(),
			{header: "Valor Clave", 	   dataIndex:'otClave',	width: 120, sortable:true,id:'otClave'},		    
		    {header: "Valor Descripción",   dataIndex:'otValor',	width: 120, sortable:true}
		   
		   	                	
        ]);
        var grid2;
        var selectedId;     
	       	grid2= new Ext.grid.GridPanel({
		
		store:test(),
		id:'grid-lista',
		border:true,
		//baseCls:' background:white ',
		cm: cm,
		sm: new Ext.grid.RowSelectionModel({
		singleSelect: true,
		listeners: {							
        	rowselect: function(sm, row, rec) {	                    		                    	                        	                     	                        
	        	 			selectedId = store.data.items[row].id;
	        	 			var sel = Ext.getCmp('grid-lista').getSelectionModel().getSelected();
	        	 			selIndex = store.indexOf(sel);
	        	 			Ext.getCmp('eliminar-grid').on('click',function(){                            		
                            		DeleteDemouser(store,selectedId,sel,listaValoresForm);
                                                                                                       
                                 });
                                 
                            Ext.getCmp('editar-grid').on('click',function(){                            		
                            		 agregarValoresGridWindow.show();
                                     Ext.getCmp('formPanel').getForm().loadRecord(rec);                                                                            
                                 });     
	                   	 }
	               	}
		}),
		tbar:[{
            text:'<s:text name="btn.datos.variables.listas.valores.agregar"/>',
            tooltip:'Agregar valor',
            iconCls:'add',
            handler: function() {                    	
				     	agregarValoresGrid(store);
				     }
        },'-',{
            text:'<s:text name="btn.datos.variables.listas.valores.eliminar"/>',
            id:'eliminar-grid',
            tooltip:'Eliminar valor',
            iconCls:'remove'
            
        },'-',{
            text:'<s:text name="btn.datos.variables.listas.valores.editar"/>',
            id:"editar-grid",
            tooltip:'Editar valor',
            iconCls:'option'
        },'-',{
            
            text:'<s:text name="btn.datos.variables.listas.valores.cargaAutomatica"/>',
            tooltip:'Carga automatica de valores'
            //iconCls:'option'
        }],      							        	    	    
    	width:600,
        height:190,
    	frame:true,     
		title:'<s:text name="lista.valores.datos.variables.subtitulo.cargaManual"/>',
		bodyStyle:'padding:5px',
		//renderTo:document.body,
		
	
		viewConfig: {autoFill: true,forceFit:true},                
		bbar: new Ext.PagingToolbar({
			pageSize:20,
			store: store,									            
			displayInfo: true,
			displayMsg: 'Mostrando registros {0} - {1} de {2}',
			emptyMsg: "No hay registros"
			})        							        				        							 
		});   
if(edita){
		//alert('entro');
		
		var rec = dstore.getAt(row);
		var numeroTabla= rec.get('numeroTabla');
		var tabla = rec.get('codigoTabla');    
		//alert("NUMEROTABLA"+numeroTabla);
					
		url='atributosVariables/EditarListaDeValoresCabecera.action?nmTabla='+numeroTabla;
		var storeListaValores = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: url
        		}),
        		reader: new Ext.data.JsonReader({
            	root:'editaListaDeValores'
	        	},[
	        		//{name: 'ottipoac',  type: 'string',  mapping:'ottipoac'},
	        		//{name: 'ottipotb',  type: 'string',  mapping:'ottipotb'},	        		
	        		//{name: 'clnatura',  type: 'string',  mapping:'clnatura'},
	        		{name: 'nombre',  type: 'string',  mapping:'nombre'},
	        		{name: 'descripcion',  type: 'string',  mapping:'descripcion'},
	        		{name: 'numero',  type: 'string',  mapping:'numeroTabla'},
	        		{name: 'enviarTablaErrores',  type: 'string',  mapping:'enviarTablaErrores'},
	        		{name: 'modificaValores',  type: 'string',  mapping:'modificaValores'},
	        		{name: 'clave',  type: 'string',  mapping:'clave'},
	        		{name: 'formatoClave',  type: 'string',  mapping:'formatoClave'},
	        		{name: 'minimoClave',  type: 'string',  mapping:'minimoClave'},
	        		{name: 'maximoClave',  type: 'string',  mapping:'maximoClave'},	        		
	        		{name: 'cdAtribu',  type: 'string',  mapping:'cdAtribu'},
	        		{name: 'descripcionFormato',  type: 'string',  mapping:'descripcionFormato'},
	        		{name: 'formatoDescripcion',  type: 'string',  mapping:'formatoDescripcion'},
	        		{name: 'minimoDescripcion',  type: 'string',  mapping:'minimoDescripcion'},
	        		{name: 'maximoDescripcion',  type: 'string',  mapping:'maximoDescripcion'},	        		
	        		{name: 'cdCatalogo1',  type: 'string',  mapping:'cdCatalogo1'},
	        		{name: 'catalogo1',  type: 'string',  mapping:'dsCatalogo1'},
	        		{name: 'cdCatalogo2',  type: 'string',  mapping:'cdCatalogo2'},
	        		{name: 'catalogo2',  type: 'string',  mapping:'dsCatalogo2'},
	        		{name: 'claveDependencia',  type: 'string',  mapping:'claveDependencia'}
	        		            
				]),
			
			remoteSort: true
    	});
    	
    	storeListaValores.on('load', function(){
    				//alert('load');
					if(storeListaValores.getTotalCount()>0){
    						var recLV= storeListaValores.getAt(0);
    						var recLVETE = recLV.get('enviarTablaErrores');
    						var recLVMV = recLV.get('modificaValores');
    						
                           	Ext.getCmp('listas-form').getForm().loadRecord(recLV);                           
                           	if(recLVETE=="S"){
                           		Ext.getCmp('id-envia-tabla-error-lista-valores').setValue(true);
                          		Ext.getCmp('id-envia-tabla-error-lista-valores').setRawValue("S");
                           	}else{
	                           	Ext.getCmp('id-envia-tabla-error-lista-valores').setValue(false);
                          		Ext.getCmp('id-envia-tabla-error-lista-valores').setRawValue("N");
                           	}
                           	if(recLVMV=="S"){
                           		Ext.getCmp('id-modifica-valor-lista-valores').setValue(true);
                          		Ext.getCmp('id-modifica-valor-lista-valores').setRawValue("S");
                           	}else{
	                           	Ext.getCmp('id-modifica-valor-lista-valores').setValue(false);
                          		Ext.getCmp('id-modifica-valor-lista-valores').setRawValue("N");
                           	}                                                    	
                           	//alert("formatoClave"+recLV.get('formatoClave'));
                           	//alert("formatoDescripcion"+recLV.get('formatoDescripcion'));
                           	if(recLV.get('formatoClave') == 'A'){                           		
                				Ext.getCmp('id-formato-alfanumerico-clave-lista-valores').setValue(true);
                				Ext.getCmp('hidden-radio-clave-lista-valor').setValue('A');                	
                			}
                			if(recLV.get('formatoClave') == 'N'){                           		
                				Ext.getCmp('id-formato-numerico-clave-lista-valores').setValue(true);
                				Ext.getCmp('hidden-radio-clave-lista-valor').setValue('N');                	
                			}else{
                				Ext.getCmp('id-formato-alfanumerico-clave-lista-valores').setValue(true);
                				Ext.getCmp('hidden-radio-clave-lista-valor').setValue('A');
                			}
                			if(recLV.get('formatoDescripcion') == 'A'){                           		
                				Ext.getCmp('id-formato-alfanumerico-descripcion-lista-valores').setValue(true);
                				Ext.getCmp('hidden-radio-descripcion-lista-valor').setValue('A');                	
                			}
                			if(recLV.get('formatoDescripcion') == 'N'){                           		
                				Ext.getCmp('id-formato-numerico-descripcion-lista-valores').setValue(true);
                				Ext.getCmp('hidden-radio-descripcion-lista-valor').setValue('N');                	
                			}else{
                				Ext.getCmp('id-formato-alfanumerico-descripcion-lista-valores').setValue(true);
                				Ext.getCmp('hidden-radio-descripcion-lista-valor').setValue('A');       
                			}
                			
                           	//alert("numeroTabla"+recLV.get('numero'));							
                           	Ext.getCmp('hidden-numero-tabla-lista-valor').setValue(recLV.get('numero'));
                           	Ext.getCmp('hidden-tipo-transaccion-lista-valor').setValue('2');
                           	//Ext.getCmp('hidden-catalogo2-lista-valor').setValue(recLV.get('cdCatalogo2'));
                           	//Ext.getCmp('hidden-codigo-atributo-lista-valor').setValue(recLV.get('cdAtribu'));
                           	                       	                           	
							var conn2 = new Ext.data.Connection();
							conn2.request ({
								url:LIMPIA_SESION_CARGA_MANUAL,
								method: 'POST',
								callback: function (options, success, response) {														                       								
									var mStore = Ext.getCmp('grid-lista').getStore();
									mStore.baseParams = mStore.baseParams || {};								
									mStore.baseParams['tabla'] = tabla;	
									mStore.reload({
							   			callback : function(r,options,success) {
							    			//alert("callback");
											if (!success) {
								      			//Ext.MessageBox.alert('No se encontraron registros');
								     			mStore.removeAll();
											}
										}
									});                           
									//Ext.getCmp('grid-lista').getStore().load();
								}
							});    
							
					}
        });
		storeListaValores.load();

}
     var ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'atributosVariables/CargaCatalogos.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaDeValores',
            id: 'listaDeValores'
	        },[
           {name: 'codigoTabla', type: 'string',mapping:'cdCatalogo1'},
           {name: 'descripcionTabla', type: 'string',mapping:'dsCatalogo1'}    
        ]),
		remoteSort: true
    });
       ds.setDefaultSort('listaDeValores', 'desc'); 
       
     var comboCatalogo1 = new Ext.form.ComboBox({
    	tpl: '<tpl for="."><div ext:qtip="{codigoTabla}" class="x-combo-list-item">{descripcionTabla}</div></tpl>',
    	store: ds,
    	displayField:'descripcionTabla',
    	//valueField: 'codigoTabla',
    	//maxLength : '30',
    	//maxLengthText : 'Treinta caracteres maximo',
	    typeAhead: true,
    	mode: 'local',
    	triggerAction: 'all',
    	emptyText:'<s:text name="def.datos.variables.listas.valores.valida.dependencia"/>',
    	selectOnFocus:true,
    	fieldLabel: '<s:text name="def.datos.variables.listas.valores.catalogo1"/>',
    	listWidth:'250',
    	id:'catalogo1',
    	name:"catalogo1",
    	vtypeText:'<s:text name="def.datos.variables.listas.valores.valida.catalogo1"/>',
        vtype: 'validaCatalogo',
        initialPassField: 'catalogo2'
    
	  });      
 /*
 	var ds2 = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'atributosVariables/CargaCatalogos2.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaDeValores2',
            id: 'listaDeValores2'
	        },[
           {name: 'codigoTabla', type: 'string',mapping:'cdCatalogo1'},
           {name: 'descripcionTabla', type: 'string',mapping:'dsCatalogo1'}    
        ]),
		remoteSort: true
    });
       ds.setDefaultSort('listaDeValores2', 'desc'); 
   */    
	var comboCatalogo2 = new Ext.form.ComboBox({
    	tpl: '<tpl for="."><div ext:qtip="{codigoTabla}" class="x-combo-list-item">{descripcionTabla}</div></tpl>',
    	store: ds,
    	displayField:'descripcionTabla',
    	//maxLength : '30',
    	//maxLengthText : 'Treinta caracteres maximo',
    	typeAhead: true,
    	mode: 'local',
    	triggerAction: 'all',
    	emptyText:'<s:text name="def.datos.variables.listas.valores.valida.dependencia"/>',
    	selectOnFocus:true,
    	fieldLabel: '<s:text name="def.datos.variables.listas.valores.catalogo2"/>',
    	listWidth:'250',
    	id: 'catalogo2',
    	name:"catalogo2",
    	vtypeText:'<s:text name="def.datos.variables.listas.valores.valida.catalogo2"/>',
        vtype: 'validaCatalogo',
        initialPassField: 'catalogo1'
    
	}); 
	
	var claveDependencia = new Ext.form.TextField({
        fieldLabel: '<s:text name="def.datos.variables.listas.valores.dependencia.clave"/>',
        name: 'claveDependencia',
        width: 165   
    });           
 

/* 		
 	function createGrid(){
	grid2= new Ext.grid.GridPanel({
		
		store:test(),
		id:'grid-lista',
		border:true,
		//baseCls:' background:white ',
		cm: cm,
		sm: new Ext.grid.RowSelectionModel({
		singleSelect: true,
		listeners: {							
        	rowselect: function(sm, row, rec) {	                    		                    	                        	                     	                        
	        	 			selectedId = store.data.items[row].id;
	        	 			var sel = Ext.getCmp('grid-lista').getSelectionModel().getSelected();
	        	 			selIndex = store.indexOf(sel);
	        	 			Ext.getCmp('eliminar-grid').on('click',function(){                            		
                            		DeleteDemouser(store,selectedId,sel,listaValoresForm);
                                                                                                       
                                 });
                                 
                            Ext.getCmp('editar-grid').on('click',function(){                            		
                            		 agregarValoresGridWindow.show(store);
                                     Ext.getCmp('formPanel').getForm().loadRecord(rec);                                                                            
                                 });     
	                   	 }
	               	}
		}),
		tbar:[{
            text:'<s:text name="btn.datos.variables.listas.valores.agregar"/>',
            tooltip:'Agregar valor',
            iconCls:'add',
            handler: function() {                    	
				     	agregarValoresGrid(store);
				     }
        },'-',{
            text:'<s:text name="btn.datos.variables.listas.valores.eliminar"/>',
            id:'eliminar-grid',
            tooltip:'Eliminar valor',
            iconCls:'remove'
            
        },'-',{
            text:'<s:text name="btn.datos.variables.listas.valores.editar"/>',
            id:"editar-grid",
            tooltip:'Editar valor',
            iconCls:'option'
        },'-',{
            
            text:'<s:text name="btn.datos.variables.listas.valores.cargaAutomatica"/>',
            tooltip:'Carga automatica de valores'
            //iconCls:'option'
        }],      							        	    	    
    	width:600,
        height:190,
    	frame:true,     
		title:'<s:text name="lista.valores.datos.variables.subtitulo.cargaManual"/>',
		bodyStyle:'padding:5px',
		//renderTo:document.body,
		
	
		viewConfig: {autoFill: true,forceFit:true},                
		bbar: new Ext.PagingToolbar({
			pageSize:25,
			store: store,									            
			displayInfo: true,
			displayMsg: 'Displaying rows {0} - {1} of {1}',
			emptyMsg: "No rows to display"      		    	
			})        							        				        							 
		}); 	
	}
	
	createGrid();*/
	store.load();
	
 //***********************************************************      
        


      var listasDeValoresForm = new Ext.form.FormPanel({
            	
            	frame:true,
            	baseCls: 'x-plain',
            	id:'listas-form',
            	labelAlign: 'left',
            	url:'atributosVariables/GuardarListaDeValores.action',
            	labelWidth: 80,
        		//title: 'Creacion De Listas De Valores',
       			bodyStyle:'padding:5px',
        		width:600,
        		buttonAlign: "center",
                
                items: [{xtype:'hidden',id:'hidden-numero-tabla-lista-valor',name:'nmTabla'},
                		{xtype:'hidden',id:'hidden-tipo-transaccion-lista-valor',name:'tipoTransaccion'},
                		{xtype:'hidden',id:'hidden-radio-clave-lista-valor',name:'hiddenRadioClave'},
                		{xtype:'hidden',id:'hidden-radio-descripcion-lista-valor',name:'hiddenRadioDescripcion'},
                		{xtype:'hidden',id:'radio-clave-lista-valor',name:'radioClave'},
                		{xtype:'hidden',id:'radio-descripcion-lista-valor',name:'radioDescripcion'},
                	
                	{
        			layout:'column',
        			baseCls: 'x-plain',
        			border: false,
            				items:[{
				                columnWidth:.32,
                				layout: 'form',
                				baseCls: 'x-plain',
                				border: false,
				                items: [
				                	new Ext.form.TextField({
                						name:'nombre',
               							fieldLabel: '<s:text name="def.datos.variables.listas.valores.nombre"/>',
                						allowBlank: false,
                						blankText : '<s:text name="def.datos.variables.listas.valores.valida.nombre"/>',
                						width: 80
                						})
				                ]
        					},{
               					columnWidth:.45,
			                	layout: 'form',
			                	baseCls: 'x-plain',
			                	border: false,
               					items: [
               						new Ext.form.TextField({
        								name: 'descripcion',
        								fieldLabel: '<s:text name="def.datos.variables.listas.valores.descripcion"/>',
        								allowBlank: false,
        								blankText : '<s:text name="def.datos.variables.listas.valores.valida.descripcion"/>',
        								width: 160  
    								})   
               					]
				    		},{
               					columnWidth:.23,
			                	layout: 'form',
			                	baseCls: 'x-plain',			                	
			                	border: false,
               					items: [
               						new Ext.form.TextField({
        							name: 'numero',
        							fieldLabel: '<s:text name="def.datos.variables.listas.valores.num"/>',       		 						
									disabled:true,
        							width: 30  
    								})
               					]
				            }]
        		
		        },{
        			layout:'column',
        			border: false,
        			baseCls: 'x-plain',
            				items:[{
				                columnWidth:.32,
                				layout: 'form',
                				baseCls: 'x-plain',
                				border: false,
				                items: [
				                	new Ext.form.Checkbox({
				                	id:'id-envia-tabla-error-lista-valores',
                					name:'enviaTablaError',
                					boxLabel: '<s:text name="def.datos.variables.listas.valores.enviar"/>',
            						hideLabel:true,	
                					checked:false,
                					onClick:function(){
				            			if(this.getValue()){
				            				this.setRawValue("S");				            	
				            			}
				            		}                 
            						})
				                ]
        					},{
               					columnWidth:.45,
			                	layout: 'form',
			                	baseCls: 'x-plain',
			                	border: false,
               					items: [
				                	new Ext.form.Checkbox({
				                	id:'id-modifica-valor-lista-valores',
                					name:'modificaValor',
                					boxLabel: '<s:text name="def.datos.variables.listas.valores.modificar"/>',
            						hideLabel:true,			
                					checked:false,
                					onClick:function(){
				            			if(this.getValue()){
				            				this.setRawValue("S");				            	
				            			}
				            		}                 
            						})               						
               					]
				    		},{
               					columnWidth:.23,
			                	layout: 'form',
			                	baseCls: 'x-plain',			                	
			                	border: false
               					
				            }]
        		
		        },{
            xtype:'fieldset',
            title: 'Clave',
            collapsible: true,
            autoHeight:true,            
            items :[{
                    layout:'form',
                    baseCls: 'x-plain',
		        	border: false,
		        	items:[{
				   		
				   		border: false,				   		
				        layout:'form',
				        baseCls: 'x-plain',
				        border: false,
				        items:[
				           		new Ext.form.TextField({
				           		id:'id-descripcion-clave-lista-valores',
        						fieldLabel: '<s:text name="def.datos.variables.listas.valores.clave"/>',
        						allowBlank: false,
        						blankText : '<s:text name="def.datos.variables.listas.valores.valida.clave"/>',
        						name: 'clave',
        						width: 160   
    							})
				      	]
				                	
				    }]     		
                },{
        			layout:'column',
        			border: false,
        			baseCls: 'x-plain',
            				items:[{
				                columnWidth:.32,
                				layout: 'form',
                				baseCls: 'x-plain',
                				border: false,
				                items: [				                	
				                	new Ext.form.Radio({
		            				//allowBlank: false,
            						id:'id-formato-alfanumerico-clave-lista-valores',
        							name:'formatoClave',
            						fieldLabel: '<s:text name="def.datos.variables.listas.valores.formato"/>',            				
            						boxLabel: '<s:text name="def.datos.variables.listas.valores.alfanumerico"/>',
            						checked:true,            				
  	  	       						onClick: function(){			            		
            							if(this.getValue()){					            	            						            								
            								Ext.getCmp('radio-clave-lista-valor').setValue('A');                		
				            			}
			            			}
            						})
				                ]
        					},{
               					columnWidth:.22,
			                	layout: 'form',
			                	baseCls: 'x-plain',
			                	border: false,
               					items: [
               						new Ext.form.Radio({
    								//allowBlank: false,
            						id:'id-formato-numerico-clave-lista-valores',
        							name:'formatoClave',
            						labelSeparator : "",
            						boxLabel: '<s:text name="def.datos.variables.listas.valores.numerico"/>',
            						hideLabel:true,        
  	  	       						onClick: function(){			            		
            							if(this.getValue()){					            	            						
            								Ext.getCmp('radio-clave-lista-valor').setValue('N');  
				            			}
			            			}
            						})
               					]
				    		},{
               					columnWidth:.23,               					
			                	layout: 'form',
			                	baseCls: 'x-plain',
			                	border: false,
               					items: [
               						new Ext.form.NumberField({				            
               						id:'id-minimo-clave-lista-valores',        						
            						name:'minimoClave',
            						fieldLabel: '<s:text name="def.datos.variables.listas.valores.minimo"/>',
            						allowDecimals : false,
				            		allowNegative : false,
        				    		allowBlank: false,
        				    		blankText : '<s:text name="def.datos.variables.listas.valores.valida.dato.req"/>',            				
            						hideParent:true,            				
            						width:30	            							       
           							})
               					]	
				    		},{
               					columnWidth:.23,
			                	layout: 'form',
			                	baseCls: 'x-plain',			                	
			                	border: false,
               					items: [
               						new Ext.form.NumberField({				            
            						id:'id-maximo-clave-lista-valores',
        							name:'maximoClave',
            						fieldLabel: '<s:text name="def.datos.variables.listas.valores.maximo"/>',
            						allowDecimals : false,
				            		allowNegative : false,
        				    		allowBlank: false,
        				    		blankText : '<s:text name="def.datos.variables.listas.valores.valida.dato.req"/>',            				
            						hideParent:true,
            						width:30	            							       
           							})
               					]
				            }]
        		
		        }]
        	},{
            xtype:'fieldset',
            title: 'Descripcion',
            collapsible: true,
            autoHeight:true,
            
            items :[{
                    layout:'form',
                    baseCls: 'x-plain',
		        	border: false,
		        	items:[{
				   		
				   		border: false,				   	
				        layout:'form',
				        baseCls: 'x-plain',
				        border: false,
				        items:[
				           		new Ext.form.TextField({
        						name: 'descripcionFormato',
        						fieldLabel: '<s:text name="def.datos.variables.listas.valores.desc.descripcion"/>',
        						allowBlank: false,
        						width: 160  
    	   						})
				        ]
				                	
				    }]     		
                },{
        			layout:'column',
        			border: false,
        			baseCls: 'x-plain',
            				items:[{
				                columnWidth:.32,
                				layout: 'form',
                				baseCls: 'x-plain',
                				border: false,
				                items: [				                	
				                	new Ext.form.Radio({
	  								allowBlank: false,
		            				name:'formatoDescripcion',
		            				id:'id-formato-alfanumerico-descripcion-lista-valores',
            						fieldLabel: '<s:text name="def.datos.variables.listas.valores.formato"/>',            				
            						boxLabel: '<s:text name="def.datos.variables.listas.valores.alfanumerico"/>',
            						checked:true,             				
  	  	       						onClick: function(){			            		
		            					if(this.getValue()){					            	            						
            								Ext.getCmp('radio-descripcion-lista-valor').setValue('A');            								                		
				            			}
			            			}
            						})
				                ]
        					},{
               					columnWidth:.22,
			                	layout: 'form',
			                	baseCls: 'x-plain',
			                	border: false,
               					items: [
               						new Ext.form.Radio({
            						allowBlank: false,
            						name:'formatoDescripcion',
            						id:'id-formato-numerico-descripcion-lista-valores',
            						labelSeparator : "",
            						boxLabel: '<s:text name="def.datos.variables.listas.valores.numerico"/>',
            						hideLabel:true,            				
  	  	       						onClick: function(){			            		
            							if(this.getValue()){					            	            						
            								Ext.getCmp('radio-descripcion-lista-valor').setValue('N'); 
				            			}
			            			}
            						})
               					]
				    		},{
               					columnWidth:.23,               					
			                	layout: 'form',
			                	baseCls: 'x-plain',
			                	border: false,
               					items: [
               						new Ext.form.NumberField({				            
            						name:'minimoDescripcion',
            						fieldLabel: '<s:text name="def.datos.variables.listas.valores.minimo"/>',
            						allowDecimals : false,
				            		allowNegative : false,
        				    		allowBlank: false,
        				    		blankText : '<s:text name="def.datos.variables.listas.valores.valida.dato.req"/>',               				
            						hideParent:true,            				
            						width:30	 							       
           							})
               					]	
				    		},{
               					columnWidth:.23,
			                	layout: 'form',	
			                	baseCls: 'x-plain',		                	
			                	border: false,
               					items: [
               						new Ext.form.NumberField({				            
            						name:'maximoDescripcion',
            						fieldLabel: '<s:text name="def.datos.variables.listas.valores.maximo"/>',
            						allowDecimals : false,
				            		allowNegative : false,
        				    		allowBlank: false,
        				    		blankText : '<s:text name="def.datos.variables.listas.valores.valida.dato.req"/>',            				
            						hideParent:true,            				
            						width:30	
            							       
           })
               					]
				            }]
        		
		        }
            ]
        },{
            xtype:'fieldset',
            title: 'Dependencias',
            collapsible: true,
            autoHeight:true,
            
            items :[{
                    layout:'form',
                    baseCls: 'x-plain',
		        	border: false,
		        	items:[{
				   		
				   		border: false,				   		                			
				        layout:'form',
				        baseCls: 'x-plain',
				        border: false,
				        items:[
				           		comboCatalogo1,
				           		comboCatalogo2,
				           		claveDependencia
				        ]
				                	
					}]     		
            }]
        },grid2]

        
    });
    
    var listaValoresForm = new Ext.form.FormPanel({
            	
            	frame:true,
            	url:'atributosVariables/EliminaCargaManual.action',
       			bodyStyle:'padding:5px',
        		width:600,
        		baseCls: 'x-plain',
                items: [{
 				    border: false	
		        }]
    });
    store.load({params:{start:0, limit:25}});   
    
    
    // define window and show it in desktop
    var window = new Ext.Window({
        title: '<s:text name="lista.valores.datos.variables.titulo"/>',
        width: 650,
        height:400,
        minWidth: 650,
        minHeight: 400,
        layout: 'fit',
        plain:true,
        //bodyStyle:'padding:5px;',
        buttonAlign:'center',
        modal : true,
        
  
        items: [{
        		layout:'form',
        		autoScroll:true,
        		items:[
        				listasDeValoresForm,
 		       			//grid2,
        				listaValoresForm]
        		}],

        buttons: [{
	            	text: '<s:text name="btn.datos.variables.listas.valores.guardar"/>',
	            	handler: function() {
                	// check form value 
                		if (listasDeValoresForm.form.isValid()) {
		 		        	listasDeValoresForm.form.submit({			      
					            	waitTitle:'<s:text name="ventana.datos.variables.listas.valores.proceso.mensaje.titulo"/>',
					            	waitMsg:'<s:text name="ventana.datos.variables.listas.valores.proceso.mensaje"/>',
					            	failure: function(form, action) {
								    Ext.MessageBox.alert('Error', 'Lista de valores no agregada');
									},
									success: function(form, action) {
								    Ext.MessageBox.alert('Status', 'Lista de valores agregada');						    		
						    		listasDeValoresForm.form.load({url:'atributosVariables/LimpiarSesionCargaManual.action'});
	            					window.close();
						    		dstore.load();
						    		//action.form.reset();    		
								}
			        		});                   
                		} else{
							Ext.MessageBox.alert('Error', 'Favor de llenar datos requeridos.');
						}             
	        		}
    		    },{
	            	text: '<s:text name="btn.datos.variables.listas.valores.cancelar"/>',
	            	handler: function(){
	            		listasDeValoresForm.form.load({url:'atributosVariables/LimpiarSesionCargaManual.action'});
	            		window.close();
	            	}
    		    }]    


    });

	window.show();
    ds.load({params:{start:0, limit:25}});    
    //ds2.load({params:{start:0, limit:25}});  

};	


</script>
