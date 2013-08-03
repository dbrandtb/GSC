<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->


<script type="text/javascript">

configuraReglasValidacion= function(storeReglasValidacion,row) {
var reglasValidacionForm;

if(row!=null){

    var rec = storeReglasValidacion.getAt(row);
 
   storeReglasValidacion.on('load', function(){ 
   						                                               
                           Ext.getCmp('reglas-validacion-form').getForm().loadRecord(rec);
                           Ext.getCmp('combo-bloque-reglas-validacion').disable();                                                     
                           Ext.getCmp('id-secuencia-reglas-validacion').disable();
                           Ext.getCmp('hidden-codigo-combo-bloque').setValue(rec.get('codigoBloque'));
                           Ext.getCmp('hidden-secuencia').setValue(rec.get('secuencia'));
                                                                                
                           dsBloque.load();
                           dsValidacion.load();
					       dsCondicion.load();
                           });
	storeReglasValidacion.load();                               
}


//******* nivel de inciso***************

//**************************************************************	
var dsBloque = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'reglaValidacion/CargaListasReglasDeValidacion.action?lista=bloques'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaBloque',
            id: 'comboBloque'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}      
        ]),
		remoteSort: true
    });
  	dsBloque.setDefaultSort('comboBloque', 'desc'); 
  	dsBloque.load();
  	
var comboBloque =new Ext.form.ComboBox({
    	                    id:'combo-bloque-reglas-validacion',
    	                    tpl: '<tpl for="."><div ext:qtip="{key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsBloque,
							//disabled:true,
							typeAhead: true,
							listWidth: '250',
							labelAlign: 'left',	
							//anchor: '90%', 					
						    displayField:'value',
						    allowBlank:false,
						    blankText : '<s:text name="productos.configReglasValidacion.bloque.req"/>',
						    valueField: 'key',					    	
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="productos.configReglasValidacion.bloque.sel"/>',
					    	selectOnFocus:true,
						    fieldLabel: '<s:text name="productos.configReglasValidacion.bloque"/>',
						    //labelSeparator:'',
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
								var valor=record.get('key');
								Ext.getCmp('hidden-codigo-combo-bloque').setValue(valor);
							}			    	
			});
			                                                              

var dsValidacion = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'reglaValidacion/CargaListasReglasDeValidacion.action?lista=validaciones'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaValidacion',
            id: 'comboValidacion'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}      
        ]),
		remoteSort: true
    });
  	dsValidacion.setDefaultSort('comboValidacion', 'desc'); 
  	dsValidacion.load();
  	
var comboValidacion =new Ext.form.ComboBox({
    	                    id:'combo-validacion-reglas-validacion',
    	                    tpl: '<tpl for="."><div ext:qtip="{key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsValidacion,
							//disabled:true,
							typeAhead: true,
							listWidth: '250',
							labelAlign: 'left',	
							//anchor: '90%', 					
						    displayField:'value',
						    allowBlank:false,
						    blankText : '<s:text name="productos.configReglasValidacion.validacion.req"/>',
						    valueField: 'key',					    	
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="productos.configReglasValidacion.validacion.sel"/>',
					    	selectOnFocus:true,
						    fieldLabel: '<s:text name="productos.configReglasValidacion.validacion"/>',
						    //labelSeparator:'',
					    	name:"descripcionValidacion",
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
								Ext.getCmp('hidden-codigo-combo-validacion').setValue(valor);
							}			    	
			});                


var dsCondicion = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'reglaValidacion/CargaListasReglasDeValidacion.action?lista=condiciones'
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
    	                    id:'combo-condicion-reglas-validacion',
    	                    tpl: '<tpl for="."><div ext:qtip="{key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsCondicion,
							//disabled:true,
							typeAhead: true,
							listWidth: '250',
							labelAlign: 'left',	
							//anchor: '90%', 					
						    displayField:'value',
						    allowBlank:false,
						    blankText : '<s:text name="productos.configReglasValidacion.condicion.req"/>',
						    valueField: 'key',					    	
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="productos.configReglasValidacion.condicion.sel"/>',
					    	selectOnFocus:true,
						    fieldLabel: '<s:text name="productos.configReglasValidacion.condicion"/>',
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
								Ext.getCmp('hidden-codigo-combo-condicion').setValue(valor);
							}			    	
			});


	var secuenciaText= new Ext.form.NumberField({
				            id:'id-secuencia-reglas-validacion',
            				name:'secuencia',
            				fieldLabel: '<s:text name="productos.configReglasValidacion.secuencia"/>',
            				allowDecimals : false,
				            allowNegative : false,            				
        				    allowBlank: false,
        				    blankText : '<s:text name="productos.configReglasValidacion.secuencia.req"/>',            				            				
            				//labelSeparator:'',
            				width:165	
            							       
           });





//************FORMA****************
	 reglasValidacionForm = new Ext.form.FormPanel({
            	id:'reglas-validacion-form',
            	baseCls: 'x-plain',
            	//boder:false,
            	frame:true,
            	labelAlign: 'left',
            	url:'reglaValidacion/AsociarReglaValidacion.action',
            	//labelWidth: 100,
        		//title: '<s:text name="productos.configSumaAsegurada.titulo"/>',
       			//bodyStyle:'padding:5px',
        		width:300,
        		//height:250, 
        		buttonAlign: "center",
                items: [{xtype:'hidden',id:'hidden-codigo-combo-bloque',name:'codigoBloque'},
                		{xtype:'hidden',id:'hidden-codigo-combo-validacion',name:'codigoValidacion'},
                		{xtype:'hidden',id:'hidden-codigo-combo-condicion',name:'codigoCondicion'},
                		{xtype:'hidden',id:'hidden-secuencia',name:'secuenciaHidden'},                		
                		{
                		layout:'form',
                		baseCls: 'x-plain',                		                		
                		border: false,
                		labelAlign:'left',
                		items:[
				    	 	comboBloque,
				    	 	secuenciaText,
				    	 	comboValidacion,
				    	 	comboCondicion
				    	 	]      		
			        	}]
			});
		    
		    // define window and show it in desktop
    var reglasValidacionWindow = new Ext.Window({
        title: '<s:text name="productos.configReglasValidacion.titulo.configuracion"/>',
        width: 320,
        height:190,
        //minWidth: 300,
        //minHeight: 150,
        layout: 'fit',
        plain:true,
        //bodyStyle:'padding:5px;',
        buttonAlign:'center',
        modal : true,
  
        items: reglasValidacionForm,
		        
		buttons: [{
	            	text: '<s:text name="productos.configReglasValidacion.btn.guardar"/>',
	            	handler: function() {                	
                			 
                		if (reglasValidacionForm.form.isValid()) {
                			
		 		        	reglasValidacionForm.form.submit({			      
					            	waitTitle:'<s:text name="ventana.configReglasValidacion.proceso.mensaje.titulo"/>',
					            	waitMsg:'<s:text name="ventana.configReglasValidacion.proceso.mensaje"/>',
					            	failure: function(form, action) {
						   				Ext.MessageBox.alert('Status', 'Regla de validacion no asociada');
									},
									success: function(form, action) {
						    			Ext.MessageBox.alert('Status', 'Regla de validacion asociada');
						    			reglasValidacionWindow.close();
						    			storeReglasValidacion.load();
						    				
						    		
						}
			        		});                   
                		} else{
					Ext.MessageBox.alert('Error', 'Favor de llenar campos requeridos');
						}             
	        		}
    		    },{
	            	text: '<s:text name="productos.configReglasValidacion.btn.cancelar"/>',
	            	handler: function(){
	            		reglasValidacionWindow.close();
	            	}
    		    }]
    	});    	    	
	
	reglasValidacionWindow.show();
    };



</script>