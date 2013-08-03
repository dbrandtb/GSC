<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->
<jsp:include page="/resources/scripts/jsp/utilities/expresiones/expresionesVariablesTemporales.jsp" flush="true" />

<script type="text/javascript">

//var prueba=false;

       
Ext.onReady(function() {  
	
  
        Ext.form.Field.prototype.msgTarget = "side";
  		var storeComboVariables;
        var grid2;   
        //function comboVariables(){        		        				
 			url4vp="librerias/ListaCatalogoVariablesProducto"; 			 		 			
 			storeComboVariables = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: url4vp
        		}),
        		reader: new Ext.data.JsonReader({
            	root:'listaReglaNegocioVariables',   
            	totalProperty: 'totalCount',
            	id: 'n',
            	successProperty : '@success'
            	         	
	        	},[
	        		{name: 'nombreCabecera',  type: 'string',  mapping:'nombre'},
	        		{name: 'descripcionCabecera',  type: 'string',  mapping:'descripcion'},
	        		{name: 'codigoExpresion',  type: 'string',  mapping:'codigoExpresion'}
	        		            
				]),
			//autoLoad:true,
			remoteSort: true
    	});
    	storeComboVariables.setDefaultSort('descripcionCabecera', 'desc');
    	//storeComboVariables.load();
	//	return storeComboVariables;
 	//}
  
 
 var codigoVariableTemporalProducto = new Ext.form.ComboBox({
 	id:'id-catalogo-variables-temporales-producto',
    tpl: '<tpl for="."><div ext:qtip="{nombreCabecera}" class="x-combo-list-item">{descripcionCabecera}</div></tpl>',
    store: storeComboVariables,
    displayField:'descripcionCabecera',
    valueField:'nombreCabecera',
    //maxLength : '30',
    //maxLengthText : 'Treinta caracteres maximo',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    emptyText:'<s:text name="variablesTemporalesProducto.select.variableTemporal"/>',
    selectOnFocus:true,
    fieldLabel: '<s:text name="variablesTemporalesProducto.variableTemporal"/>',
    listWidth: '210',
    //allowBlank:false,
    blankText : '<s:text name="variablesTemporalesProducto.variableTemporal.req"/>',
    //id:'comboRol',
    name:"descripcionVariableProducto",
    width:160,
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
								var valor=record.get('nombreCabecera');
								Ext.getCmp('hidden-combo-variable-temporal-producto').setValue(valor);
								
							}
    
});            
              
   
//**************grid******************
	var afuera;
 	var temporal=-1;      
    var storeVariablesProducto;
    // function variablesProducto(){        		        				
 			url4vap="<s:url action='librerias/ListaVariablesAsociadasAlProducto'/>"; 			 		 			
 			storeVariablesProducto = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: url4vap
        		}),
        		reader: new Ext.data.JsonReader({
            	root:'listaReglaNegocioVariablesProducto',   
            	totalProperty: 'totalCount',
            	id: 'a'
            	         	
	        	},[
	        		{name: 'nombreCabecera',  type: 'string',  mapping:'nombre'},
	        		{name: 'descripcionCabecera',  type: 'string',  mapping:'descripcion'},
	        		{name: 'codigoExpresion',  type: 'string',  mapping:'codigoExpresion'}
	        		            
				]),
			
			//autoLoad:true,
			remoteSort: true
    	});
    	storeVariablesProducto.setDefaultSort('descripcionCabecera', 'desc');
    
    	//storeVariablesProducto.load();
		//return storeVariablesProducto;
 	//}
 

 	function toggleDetails(btn, pressed){
       	var view = grid.getView();
       	view.showPreview = pressed;
       	view.refresh();
    	}
		
		var cmVariables = new Ext.grid.ColumnModel([
		    new Ext.grid.RowNumberer(),
			{header: "Nombre", 	   dataIndex:'nombreCabecera',	width: 250, sortable:true,id:'nombreCabecera'},		    
		    {header: "Descripción",   dataIndex:'descripcionCabecera',	width: 250, sortable:true}
		   
		   	                	
        ]);
     
 
	var grid2= new Ext.grid.GridPanel({
		store:storeVariablesProducto,
		id:'grid-variables-temporales-producto',
		border:true,
		//baseCls:' background:white ',
		cm: cmVariables,
		sm: new Ext.grid.RowSelectionModel({
		singleSelect: true,
		listeners: {							
        	rowselect: function(sm, row, rec) {	                    		                    	                        	                     	                        
	        	 			//selectedId = storeVariables.data.items[row].id;
	        	 			//var sel = Ext.getCmp('grid-lista1').getSelectionModel().getSelected();
	        	 			//selIndexVariables = storeVariables.indexOf(rec);
                            		 //alert("row"+row);
                            var valor= rec.get('nombreCabecera');
	        	 			Ext.getCmp('eliminar-variables-producto').on('click',function(){                            		
                            		Ext.getCmp('variables-producto-form').getForm().load({
                            				url:'librerias/DesasociarVariableDelProducto.action?codigoVariableProducto='+valor,
                            				failure:function(a,t){
                            					storeVariablesProducto.load();
                            					storeComboVariables.load();
                            				}
                            				});
                                                                                                   
                                 });
                            
	                   	 }
	               	}
		}),
		tbar:[{
            text:'<s:text name="librerias.btn.agregar"/>',
            tooltip:'Agregar variable temporal',
            iconCls:'add',
            handler: function() {                    	
            			if(Ext.getCmp('id-catalogo-variables-temporales-producto').isDirty()){
				     		var valor=Ext.getCmp('hidden-combo-variable-temporal-producto').getValue();
				     		//alert(valor);
				     		Ext.getCmp('variables-producto-form').getForm().submit({
				     						url:'librerias/AsociarVariableAlProducto.action',//?codigoVariableProducto='+valor,
                            				success:function(a,t){
                            					storeVariablesProducto.load();
                            					storeComboVariables.load();
                            				}});
				     	}else{
				     		Ext.MessageBox.alert('Status', 'seleccione una variable');
				     	}
				     }
        },'-',{
            text:'<s:text name="librerias.btn.eliminar"/>',
            id:'eliminar-variables-producto',
            tooltip:'Eliminar variable temporal',
            iconCls:'remove'
            
            
        }],      							        	    	    
    	width:600,
        height:290,
    	frame:true,     
		bodyStyle:'padding:5px',
		viewConfig: {autoFill: true,forceFit:true},                
		bbar: new Ext.PagingToolbar({
			pageSize:20,
			store: storeVariablesProducto,									            
			displayInfo: true,
			displayMsg: 'Mostrando registros {0} - {1} de {2}',
			emptyMsg: "No hay resultados"
			})   
      	}); 	
//*************grid*********************      
      var variablesProductoForm = new Ext.form.FormPanel({
            	
            	frame:false,
            	//labelAlign: 'right',
        		title:'<s:text name="variablesTemporalesProducto.titulo.forma"/>',
        		url:'librerias/GuardarVariablesTemporalesDelProducto.action',        		
        		id: 'variables-producto-form',
        		width:620,
        		//buttonAlign: "center",
                items: [{
                	layout:'form',
                	border:false,
                	items:[{
                		layout:'form',
                		border:false
	                	},{
    	    			layout:'column',
    	    			border:false,
            				items:[{
				                columnWidth:.4,
                				layout: 'form',
                				labelAlign: 'right',
                				labelWidth: 110,
                				width:300,
                				border:false,
                				items: [{xtype:'hidden',id:'hidden-combo-variable-temporal-producto',name:'codigoVariableProducto'},
					                	codigoVariableTemporalProducto
				                ]
        					},{
				                columnWidth:.3,
                				layout: 'form',
                				border:false,
                				width:165,
                				labelAlign: 'right',
				                items: [{
				                    xtype:'button',
				                    text: '<s:text name="variablesTemporalesProducto.btn.agregarCatalogo"/>',
                				    name: 'AgregarAlCatalogo',
				                    buttonAlign: "left",
                				    handler: function() {                    	
								     	ExpresionesVentanaVariablesTemporales(storeComboVariables);
								     }	
				                }]
        					}]
				    }]		
        		
		        },grid2],
		         buttons: [{
	            	text: '<s:text name="variablesTemporalesProducto.btn.guardar"/>',
	            	handler: function() {
                	// check form value 
                		//if (variablesProductoForm.form.isValid()) {
		 		        	variablesProductoForm.form.submit({			      
					            	waitTitle:'<s:text name="ventana.variablesTemporalesProducto.proceso.mensaje.titulo"/>',
					            	waitMsg:'<s:text name="ventana.variablesTemporalesProducto.proceso.mensaje"/>',
					            	failure: function(form, action) {
								    Ext.MessageBox.alert('Status', 'no se pudo asociar la variable');
									},
									success: function(form, action) {
								    Ext.MessageBox.alert('Status', 'Las variables se asociaron correctamente');		
								    form.reset();  				    		
						    		//variablesProductoForm.form.load({url:'variablesTemporalesProducto/LimpiarSesionVariablesAsociadas.action'});
	            					storeVariablesProducto.load();
						    		//Ext.getCmp('arbol-productos').getRootNode().reload();
								}
			        		});                   
                		//} else{
							//Ext.MessageBox.alert('Error', 'Favor de llenar los campos requeridos.');
						//}             
	        		}
    		    },{
	            	text: '<s:text name="variablesTemporalesProducto.btn.cancelar"/>',
	            	handler: function(){
	            		variablesProductoForm.form.load({
	            			url:'RemueveCosasDeSessionEnLibrerias.action',
	            			failure:function(){
			            		storeVariablesProducto.load();
	            			}
	            		});
	            	}
    		    }]      
    });
    
    variablesProductoForm.render('centerVariablesTemporalesProducto');
  	
});
       
    </script>