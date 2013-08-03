<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->
<jsp:include page="/resources/scripts/jsp/utilities/rol/catalogo/agregarCatalogoRoles.jsp" flush="true" />
<jsp:include page="/resources/scripts/jsp/utilities/rol/atributosVariables/agregarAtributoVariable.jsp" flush="true" />
<jsp:include page="/resources/scripts/jsp/utilities/rol/atributosVariables/eliminarAtributoVariable.jsp" flush="true" />
<script type="text/javascript">

//var prueba=false;
var selectedId;
var grid2;
var store;    
var storeVariablesRol;
var afuera;
var temporal=-1;   
Ext.onReady(function() {      
        Ext.form.Field.prototype.msgTarget = "side";

            
    var masDeUnoCheck= new Ext.form.Checkbox({
				            id:'mas-de-uno-check-rol',
            				name:'numeroMaximo',
            				boxLabel: '<s:text name="productos.configRoles.masdeuno"/>',
				            hideLabel:true,
				            onClick:function(){
								if(this.getValue()){
				    			this.setRawValue("2");				            	
				    		}
		}
            							       
           });
           
     var domicilioCheck= new Ext.form.Checkbox({
     						id:'domicilio-check-rol',
				            name:'domicilio',
            				boxLabel: '<s:text name="productos.configRoles.domic"/>',
				            hideLabel:true,
				            onClick:function(){
								if(this.getValue()){
				    			this.setRawValue("S");				            	
				    		}
		}
            							       
           });      
           
     var dsCatalogoRol = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url:'rol/CatalogoDeRoles.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'catalogoRoles',
            id: 'rolCombo'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}       
        ]),
		remoteSort: true
    });
    dsCatalogoRol.setDefaultSort('comboRol', 'desc'); 
  
 
 var codigoRol = new Ext.form.ComboBox({
 	id:'id-catalogo-roles-en-rol',
    tpl: '<tpl for="."><div ext:qtip="{key}" class="x-combo-list-item">{value}</div></tpl>',
    store: dsCatalogoRol,
    displayField:'value',
    valueField:'key',
    //maxLength : '30',
    //maxLengthText : 'Treinta caracteres maximo',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    emptyText:'<s:text name="productos.configRoles.select.rol"/>',
    selectOnFocus:true,
    fieldLabel: '<s:text name="productos.configRoles.rol"/>',
    listWidth: '210',
    allowBlank:false,
    blankText : '<s:text name="productos.configRoles.req.rol"/>',
    //id:'comboRol',
    name:"descripcionRol",
    width:130,
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
								var mStore = grid2.store;
								mStore.baseParams = mStore.baseParams || {};
								mStore.baseParams['codigoRol'] = valor;
								mStore.reload({
				                	  callback : function(r,options,success) {
				                      		if (!success) {
		                				         Ext.MessageBox.alert('No se encontraron registros');
						                         mStore.removeAll();
                							  }
				        		      }

					              });
								Ext.getCmp('hidden-combo-rol').setValue(valor);
								
							}
    
});            
 
    var dsCatalogoObligatorio = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url:'rol/CatalogoDeObligatorio.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'catalogoObligatorio',
            id: 'comboObligatorio'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}       
        ]),
		remoteSort: true
    });
    //dsCatalogoObligatorio.load(); 
    
    var obligatoriedadCombo = new Ext.form.ComboBox({
    		id:'id-combo-obligatoriedad-rol',
    		tpl: '<tpl for="."><div ext:qtip="{value}. {key}" class="x-combo-list-item">{value}</div></tpl>',
	 		store: dsCatalogoObligatorio,
			displayField:'value',
			allowBlank:false,
    		blankText : '<s:text name="productos.configRoles.req.oblig"/>',
			listWidth: '130',
			//valueField: 'key',	    				
			typeAhead: true,
	    	mode: 'local',
			triggerAction: 'all',
			emptyText:'<s:text name="productos.configRoles.select.obligatorio"/>',
    		selectOnFocus:true,
   			fieldLabel: '<s:text name="productos.configRoles.obligatorio"/>',
			name:"descripcionComposicion",
    		width:130,
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
								Ext.getCmp('hidden-composicion-rol').setValue(valor);
								
							}				    			
	});             
   
//**************grid******************
	      
    
    function test(){        		        				
 			url='rol/CargaAtributosVariablesRol.action' 			 		 			
 			storeVariablesRol = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: url
        		}),
        		reader: new Ext.data.JsonReader({
	            	root:'listaAtributoVariable',   
    	        	totalProperty: 'totalCount'
        	    	//id: 'descripcionAtributoVariable'            	         	
	        	},[
	        		{name: 'descripcionAtributoVariable',  type: 'string',  mapping:'descripcionAtributoVariable'},
	        		{name: 'descripcionListaDeValores',  type: 'string',  mapping:'descripcionListaDeValores'},
	        		{name: 'switchObligatorio',  type: 'string',  mapping:'switchObligatorio'},
	        		{name: 'codigoAtributoVariable',  type: 'string',  mapping:'codigoAtributoVariable'},
	        		{name: 'codigoListaDeValores',  type: 'string',  mapping:'codigoListaDeValores'},
	        		{name: 'apareceCotizador',  type: 'string',  mapping:'apareceCotizador'},
	        		{name: 'modificaCotizador',  type: 'string',  mapping:'modificaCotizador'},
	        		{name: 'datoComplementario',  type: 'string',  mapping:'datoComplementario'},
	        		{name: 'obligatorioComplementario',  type: 'string',  mapping:'obligatorioComplementario'},
	        		{name: 'modificableComplementario',  type: 'string',  mapping:'modificableComplementario'},
	        		{name: 'apareceEndoso',  type: 'string',  mapping:'apareceEndoso'},
	        		{name: 'obligatorioEndoso',  type: 'string',  mapping:'obligatorioEndoso'},
	        		{name: 'modificaEndoso',  type: 'string',  mapping:'modificaEndoso'}        		            
				]),
			//autoLoad:true,
			remoteSort: true,
            baseParams: {codigoRol:''}
    	});
    	storeVariablesRol.setDefaultSort('descripcionAtributoVariable', 'desc');
    	//store.load();
		return storeVariablesRol;
 	}
 	
 	function toggleDetails(btn, pressed){
       	var view = grid.getView();
       	view.showPreview = pressed;
       	view.refresh();
    	}
		
		var cm = new Ext.grid.ColumnModel([
		    new Ext.grid.RowNumberer(),
			{header: "Atributos Variables",dataIndex:'descripcionAtributoVariable',	width: 190, sortable:true,id:'descripcionAtributoVariable'},
			{header: "Valor Descripción",dataIndex:'descripcionListaDeValores',	width: 190, sortable:true},		    
		    {header: "Obligatorio",   dataIndex:'switchObligatorio',width: 190, sortable:true}
		    /*{header: "Aparece Cotizador",dataIndex:'apareceCotizador',	width: 50, sortable:true},		    
		    {header: "Modifica Cotizador",   dataIndex:'modificaCotizador',width: 50, sortable:true},
		    {header: "Dato Complementario",dataIndex:'datoComplementario',	width: 50, sortable:true},		    
		    {header: "Dato Obligatorio",   dataIndex:'obligatorioComplementario',width: 50, sortable:true},
		    {header: "Dato Modificable",dataIndex:'modificableComplementario',	width: 50, sortable:true},		    
		    {header: "Aparece Endoso",   dataIndex:'apareceEndoso',width: 50, sortable:true},
		    {header: "Endoso Obligatorio",dataIndex:'obligatorioEndoso',	width: 50, sortable:true},		    
		    {header: "Endoso Modificable",   dataIndex:'modificaEndoso',width: 50, sortable:true}
		    */
		   
		   	                	
        ]);
        //var grid2;
        
 		
 	function createGrid(){
	grid2= new Ext.grid.GridPanel({
		store:test(),
		id:'grid-lista-atributos-variable-rol',
		border:true,
		//baseCls:' background:white ',
		cm: cm,
		sm: new Ext.grid.RowSelectionModel({
		singleSelect: true,
		listeners: {							
        	rowselect: function(sm, row, rec) {	                    		                    	                        	                     	                        
	        	 			//selectedId = store.data.items[row].id;
	        	 			//var sel = Ext.getCmp('grid-lista').getSelectionModel().getSelected();
	        	 			//selIndex = store.indexOf(sel);
                            afuera=row;      
	        	 			Ext.getCmp('eliminar-grid-rol').on('click',function(){                            		
                            		if(afuera!=temporal){
                            			temporal=afuera;
                            			eliminaAtributoVariableRol(storeVariablesRol,temporal,rolesForm);
                                    }                                                                  
                                 });
                                 temporal=-1;
                            Ext.getCmp('editar-grid-rol').on('click',function(){                            		
                            		 //agregarValoresGridWindow.show(store);
                                     //Ext.getCmp('formPanel').getForm().loadRecord(rec);
                                     if(afuera!=temporal){
                            		 temporal=afuera;
                            		
                                     seleccionaAtributosVariables(storeVariablesRol,temporal);
                                     }                                                                             
                                 });  
                                 temporal=-1;   
	                   	 }
	               	}
		}),
		tbar:[{
            text:'<s:text name="productos.configRoles.btn.agregar"/>',
            tooltip:'Agrega atributo variable',
            iconCls:'add',
            handler: function() {
            
            			if (Ext.getCmp('hidden-combo-rol') != null 
            				&& Ext.getCmp('hidden-combo-rol').getValue() != null 
            				&& Ext.getCmp('hidden-combo-rol').getValue() != '' 
            				&& Ext.getCmp('hidden-combo-rol').getValue() != 'undefined') {
            				seleccionaAtributosVariables(storeVariablesRol);
            			} else {
            				Ext.MessageBox.alert('Mensaje', 'Debe seleccionar un Rol primero para poder asociar sus atributos variables');
            			}                    	
				     	
				     }
        },'-',{
            text:'<s:text name="productos.configRoles.btn.eliminar"/>',
            id:'eliminar-grid-rol',
            tooltip:'Elimina atributo variable',
            iconCls:'remove'
            
        },'-',{
            text:'<s:text name="productos.configRoles.btn.editar"/>',
            id:"editar-grid-rol",
            tooltip:'Edita atributo variable',
            iconCls:'option'
        }/*,'-',{
            
            text:'<s:text name="productos.configRoles.btn.valDefectoPoliza"/>',
            tooltip:'Valor por defecto del rol'
            //iconCls:'option'
        }*/],      							        	    	    
    	width:620,
        height:190,
    	frame:false,
        title:'<s:text name="productos.configRoles.title.atribroles"/>',
		bodyStyle:'padding:5px',
		//renderTo:document.body,
		
	
		viewConfig: {autoFill: true,forceFit:true},                
		bbar: new Ext.PagingToolbar({
			pageSize:25,
			store: storeVariablesRol,									            
			displayInfo: true,
			displayMsg: 'Mostrando registros {0} - {1} de {1}',
			emptyMsg: "No hay resultados que mostrar"      		    	
			})        							        				        							 
		}); 	
	}
	
	createGrid();
	//store.load();
      
//*************grid*********************      
      var rolesForm = new Ext.form.FormPanel({
            	
            	frame:false,
            	//labelAlign: 'right',
        		title:'<s:text name="productos.configRoles.title.principal"/>',
        		url:'rol/InsertaRolInciso.action',
        		id: 'roles-form',
        		width:620,
        		//iconCls:'icon-grid',
        		//buttonAlign: "center",
                items: [{xtype:'hidden',id:'hidden-combo-rol',name:'codigoRol'},
                		{xtype:'hidden',id:'hidden-composicion-rol',name:'codigoComposicion'},
                	{
                	layout:'form',
                	border:false,
                	items:[{
                		layout:'form',
                		border:false
	                	},{
    	    			layout:'column',
    	    			border:false,
            				items:[{
				                columnWidth:.3,
                				layout: 'form',
                				labelAlign: 'right',
                				labelWidth: 25,
                				width:185,
                				border:false,
                				items: [//{xtype:'hidden',id:'hidden-combo-rol',name:'codigoRol'},
				                	codigoRol
				                ]
        					},{
				                columnWidth:.28,
                				layout: 'form',
                				border:false,
                				width:165,
                				labelAlign: 'right',
				                items: [{
				                    xtype:'button',
				                    text: '<s:text name="productos.configRoles.btn.agregarCatalogo"/>',
                				    name: 'AgregarAlCatalogo',
				                    buttonAlign: "left",
                				    handler: function() {                    	
				               			creaCatalogoDeRoles(dsCatalogoRol);//script que tienes afuera
               			   				
         							}	
				                }]
        					},{
               					columnWidth:.42,
			                	layout: 'form',
			                	width:260,
			                	border:false,
			                	labelAlign: 'right',
               					items: [//{xtype:'hidden',id:'hidden-composicion-rol',name:'codigoComposicion'},
               						obligatoriedadCombo
               					]
				    		}]
				    },{
    	    			layout:'column',
    	    			border:false,
            				items:[{
				                columnWidth:.25,
                				layout: 'form',
                				labelAlign: 'right',
                				labelWidth: 20,
                				border:false,
                				items: [
				                	masDeUnoCheck
				                ]
        					},{
				                columnWidth:.2,
                				layout: 'form',
                				border:false,
                				labelAlign: 'right',
				                items: [
				                	domicilioCheck
				                ]
        					}]
				    }]		
        		
		        },grid2],
		         buttons: [{
	            	text: '<s:text name="productos.configRoles.btn.guardar"/>',
	            	handler: function() {
                	// check form value 
                		if (rolesForm.form.isValid()) {
		 		        	rolesForm.form.submit({			      
					            	waitTitle:'<s:text name="ventana.configRoles.proceso.mensaje.titulo"/>',
					            	waitMsg:'<s:text name="ventana.configRoles.proceso.mensaje"/>',
					            	failure: function(form, action) {
								    Ext.MessageBox.alert('Status', 'El Rol no pudo ser guardado');
									},
									success: function(form, action) {
								    Ext.MessageBox.alert('Status', 'El Rol fue guardado con exito');		
								    form.reset();  				    		
						    		rolesForm.form.load({url:'rol/LimpiarSesionAtributosVariablesRol.action'});
	            					storeVariablesRol.load();
						    		Ext.getCmp('arbol-productos').getRootNode().reload();
								}
			        		});                   
                		} else{
							Ext.MessageBox.alert('Error', 'Favor de llenar los campos requeridos.');
						}             
	        		}
    		    },{
	            	text: '<s:text name="productos.configRoles.btn.cancelar"/>',
	            	handler: function(){
	            		rolesForm.form.load({url:'rol/LimpiarSesionAtributosVariablesRol.action'});
	            		
	            	}
    		    }]      
    });
    
  // Esta linea carga el arbol en el success cuando se agrega un rol ==>Ext.getCmp('arbol-productos').getRootNode().reload();
    rolesForm.render('center4rolls');
  	//grid2.render('center4rolls');
  	//dsCatalogoRol.load({params:{start:0, limit:25}}); 
        });

       
    </script>