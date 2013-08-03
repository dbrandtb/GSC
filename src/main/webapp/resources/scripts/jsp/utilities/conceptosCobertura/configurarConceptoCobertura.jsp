<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->


<script type="text/javascript">

configuraConceptoCobertura= function(storeConceptosCobertura,codigoConceptoCobertura, row) {
var conceptosCoberturaForm;

if(row!=null){
	                      
    var rec = storeConceptosCobertura.getAt(row);
 
   storeConceptosCobertura.on('load', function(){ 
   						                                               
                           Ext.getCmp('conceptos-cobertura-form').getForm().loadRecord(rec);
                           Ext.getCmp('combo-periodo-conceptos-cobertura').disable();                                                     
                           Ext.getCmp('combo-concepto-conceptos-cobertura').disable();
                           Ext.getCmp('id-orden-conceptos-cobertura').disable();
                           Ext.getCmp('hidden-codigo-combo-periodo').setValue(rec.get('codigoPeriodo'));
                           Ext.getCmp('hidden-codigo-combo-concepto').setValue(rec.get('codigoConcepto'));                           
                           Ext.getCmp('hidden-orden').setValue(rec.get('orden'));
                           //Ext.getCmp('hidden-edita').setValue(edita);                                                     
                           dsComportamiento.load();                          
					       dsCondicion.load();
                           });
	storeConceptosCobertura.load();
}


//******* nivel de inciso***************

//**************************************************************	
var dsPeriodo = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'conceptosCobertura/CargaListasConceptosPorCobertura.action?lista=periodos'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaPeriodos',
            id: 'comboPeriodo'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}      
        ]),
		remoteSort: true
    });
  	dsPeriodo.setDefaultSort('comboPeriodo', 'desc'); 
  	dsPeriodo.load();
  	
var comboPeriodo =new Ext.form.ComboBox({
    	                    id:'combo-periodo-conceptos-cobertura',
    	                    tpl: '<tpl for="."><div ext:qtip="{key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsPeriodo,
							//disabled:true,
							typeAhead: true,
							listWidth: '250',
							labelAlign: 'left',	
							//anchor: '90%', 					
						    displayField:'value',
						    allowBlank:false,
						    blankText : '<s:text name="ventana.config.conceptos.cobertura.periodo.req"/>',
						    valueField: 'key',					    	
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="ventana.config.conceptos.cobertura.periodo.sel"/>',
					    	selectOnFocus:true,
						    fieldLabel: '<s:text name="ventana.config.conceptos.cobertura.periodo"/>',
						    //labelSeparator:'',
					    	name:"descripcionPeriodo",
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
								Ext.getCmp('hidden-codigo-combo-periodo').setValue(valor);
							}			    	
			});
			                                                              
/*
var dsCobertura = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'conceptosCobertura/CargaListasConceptosPorCobertura.action?lista=coberturas'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaCoberturas',
            id: 'comboCobertura'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}      
        ]),
		remoteSort: true
    });
  	dsCobertura.setDefaultSort('comboCobertura', 'desc'); 
  	dsCobertura.load();
  	
var comboCobertura =new Ext.form.ComboBox({
    	                    id:'combo-cobertura-conceptos-cobertura',
    	                    tpl: '<tpl for="."><div ext:qtip="{key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsCobertura,
							//disabled:true,
							typeAhead: true,
							listWidth: '250',
							labelAlign: 'left',	
							//anchor: '90%', 					
						    displayField:'value',
						    //allowBlank:false,
						    blankText : '<s:text name="ventana.config.conceptos.cobertura.cobertura.req"/>',
						    valueField: 'key',					    	
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="ventana.config.conceptos.cobertura.cobertura.sel"/>',
					    	selectOnFocus:true,
						    fieldLabel: '<s:text name="ventana.config.conceptos.cobertura.cobertura"/>',
						    //labelSeparator:'',
					    	name:"descripcionCobertura",
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
								Ext.getCmp('hidden-codigo-combo-conceptos-cobertura').setValue(valor);
							}			    	
			});                
*/

var dsConcepto = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'conceptosCobertura/CargaListasConceptosPorCobertura.action?lista=conceptos'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaConceptos',
            id: 'comboConcepto'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}      
        ]),
		remoteSort: true
    });
  	dsConcepto.setDefaultSort('comboConcepto', 'desc'); 
  	dsConcepto.load();
  	
var comboConcepto =new Ext.form.ComboBox({
    	                    id:'combo-concepto-conceptos-cobertura',
    	                    tpl: '<tpl for="."><div ext:qtip="{key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsConcepto,
							//disabled:true,
							typeAhead: true,
							listWidth: '250',
							labelAlign: 'left',	
							//anchor: '90%', 					
						    displayField:'value',
						    allowBlank:false,
						    blankText : '<s:text name="ventana.config.conceptos.cobertura.concepto.req"/>',
						    valueField: 'key',					    	
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="ventana.config.conceptos.cobertura.concepto.sel"/>',
					    	selectOnFocus:true,
						    fieldLabel: '<s:text name="ventana.config.conceptos.cobertura.concepto"/>',
						    //labelSeparator:'',
					    	name:"descripcionConcepto",
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
								Ext.getCmp('hidden-codigo-combo-concepto').setValue(valor);
							}			    	
			});
			

var dsComportamiento = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'conceptosCobertura/CargaListasConceptosPorCobertura.action?lista=comportamiento'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaComportamientos',
            id: 'comboComportamiento'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}      
        ]),
		remoteSort: true
    });
  	dsComportamiento.setDefaultSort('comboComportamiento', 'desc'); 
  	dsComportamiento.load();
  	
var comboComportamiento =new Ext.form.ComboBox({
    	                    id:'combo-comportamiento-conceptos-cobertura',
    	                    tpl: '<tpl for="."><div ext:qtip="{key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsComportamiento,
							//disabled:true,
							typeAhead: true,
							listWidth: '250',
							labelAlign: 'left',	
							//anchor: '90%', 					
						    displayField:'value',
						    allowBlank:false,
						    blankText : '<s:text name="ventana.config.conceptos.cobertura.comportamiento.req"/>',
						    valueField: 'key',					    	
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="ventana.config.conceptos.cobertura.comportamiento.sel"/>',
					    	selectOnFocus:true,
						    fieldLabel: '<s:text name="ventana.config.conceptos.cobertura.comportamiento"/>',
						    //labelSeparator:'',
					    	name:"descripcionComportamiento",
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
								Ext.getCmp('hidden-codigo-combo-comportamiento').setValue(valor);
							}			    	
			});			

var dsCondicion = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'conceptosCobertura/CargaListasConceptosPorCobertura.action?lista=condiciones'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaCondicion',
            id: 'comboCondicion'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}      
        ]),
		remoteSort: true
    });
  	dsCondicion.setDefaultSort('comboCondicion', 'desc'); 
  	dsCondicion.load();
  	
var comboCondicion =new Ext.form.ComboBox({
    	                    id:'combo-condicion-conceptos-cobertura',
    	                    tpl: '<tpl for="."><div ext:qtip="{key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsCondicion,
							//disabled:true,
							typeAhead: true,
							listWidth: '250',
							labelAlign: 'left',	
							//anchor: '90%', 					
						    displayField:'value',
						    allowBlank:false,
						    blankText : '<s:text name="ventana.config.conceptos.cobertura.condicion.req"/>',
						    valueField: 'key',					    	
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="ventana.config.conceptos.cobertura.condicion.sel"/>',
					    	selectOnFocus:true,
						    fieldLabel: '<s:text name="ventana.config.conceptos.cobertura.condicion"/>',
						    //labelSeparator:'',
					    	name:"descripcionCondicion",
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
								Ext.getCmp('hidden-codigo-combo-condicion-concepto').setValue(valor);
							}			    	
			});


	var ordenText= new Ext.form.NumberField({
				            id:'id-orden-conceptos-cobertura',
            				name:'orden',
            				fieldLabel: '<s:text name="ventana.config.conceptos.cobertura.orden"/>',
            				allowDecimals : false,
				            allowNegative : false,            				
        				    allowBlank: false,
        				    blankText : '<s:text name="ventana.config.conceptos.cobertura.orden.req"/>',            				            				
            				//labelSeparator:'',
            				width:165	
            							       
           });





//************FORMA****************
	 conceptosCoberturaForm = new Ext.form.FormPanel({
            	id:'conceptos-cobertura-form',
            	baseCls: 'x-plain',
            	//boder:false,
            	frame:true,
            	labelAlign: 'left',
            	url:'conceptosCobertura/AgregarConceptosCobertura.action?codigoCobertura='+codigoConceptoCobertura,
            	//labelWidth: 100,
        		//title: '<s:text name="productos.configSumaAsegurada.titulo"/>',
       			//bodyStyle:'padding:5px',
        		width:300,
        		//height:250, 
        		buttonAlign: "center",
                items: [{xtype:'hidden',id:'hidden-codigo-combo-periodo',name:'codigoPeriodo'},
                		//{xtype:'hidden',id:'hidden-codigo-combo-cobertura',name:'codigoCobertura'},
                		{xtype:'hidden',id:'hidden-codigo-combo-concepto',name:'codigoConcepto'},
                		{xtype:'hidden',id:'hidden-codigo-combo-comportamiento',name:'codigoComportamiento'},
                		{xtype:'hidden',id:'hidden-codigo-combo-condicion-concepto',name:'codigoCondicion'},
                		{xtype:'hidden',id:'hidden-orden',name:'ordenHidden'},                		             		
                		{
                		layout:'form',
                		baseCls: 'x-plain',                		                		
                		border: false,
                		labelAlign:'left',
                		items:[
				    	 	comboPeriodo,
				    	 	ordenText,
				    	 	//comboCobertura,
				    	 	comboConcepto,
				    	 	comboComportamiento,
				    	 	comboCondicion
				    	 	]      		
			        	}]
			});
		    
		    // define window and show it in desktop
    var conceptosCoberturaWindow = new Ext.Window({
        title: '<s:text name="config.conceptos.cobertura.titulo.configuracion"/>',
        width: 320,
        height:205,
        //minWidth: 300,
        //minHeight: 150,
        layout: 'fit',
        plain:true,
        //bodyStyle:'padding:5px;',
        buttonAlign:'center',
        modal : true,
  
        items: conceptosCoberturaForm,
		        
		buttons: [{
	            	text: '<s:text name="config.coberturas.btn.guardar"/>',
	            	handler: function() {                	
                			 
                		if (conceptosCoberturaForm.form.isValid()) {
                			
		 		        	conceptosCoberturaForm.form.submit({			      
					            	waitTitle:'<s:text name="ventana.configCobertura.proceso.mensaje.titulo"/>',
					            	waitMsg:'<s:text name="ventana.configCobertura.proceso.mensaje"/>',
					            	failure: function(form, action) {
						   				Ext.MessageBox.alert('Status', 'Concepto por cobertura no asociado');
									},
									success: function(form, action) {
						    			Ext.MessageBox.alert('Status', 'Concepto por cobertura asociado');
						    			conceptosCoberturaWindow.close();
						    			storeConceptosCobertura.load();
						    				
						    		
						}
			        		});                   
                		} else{
					Ext.MessageBox.alert('Error', 'Favor de llenar campos requeridos');
						}             
	        		}
    		    },{
	            	text: '<s:text name="config.coberturas.btn.cancelar"/>',
	            	handler: function(){
	            		conceptosCoberturaWindow.close();
	            	}
    		    }]
    	});    	    	
	
	conceptosCoberturaWindow.show();
    };



</script>