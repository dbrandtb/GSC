<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->


<script type="text/javascript">

AgregaSumaAsegurada= function(storeSumaAsegurada,row) {
var sumaAseguradaProductoForm;
//alert("ROW"+row);
if(row!=null){
	//edita=1;
    var rec = storeSumaAsegurada.getAt(row);
 
   storeSumaAsegurada.on('load', function(){ 
   						                        
                           //alert("onload");
                           Ext.getCmp('suma-asegurada-producto-form').getForm().loadRecord(rec);
    					   //var codCapital= rec.get('codigoCapital');   
                           //alert("CODIGOCAP"+codCapital);
                           
                           Ext.getCmp('hidden-codigo-capital').setValue(rec.get('codigoCapital'));
                           Ext.getCmp('hidden-combo-tipo-suma-asegurada').setValue(rec.get('codigoTipoCapital'));
                           Ext.getCmp('hidden-combo-moneda-suma-asegurada').setValue(rec.get('codigoMoneda'));
                           
                           dsTipoSumaAsegurada.load();
                           dsMonedaSumaAsegurada.load();
						
                           });
	storeSumaAsegurada.load(); 
	//edita=0;
                                                 
}


//******* nivel de producto***************
var sumaAseguradaText= new Ext.form.TextField({
				            id:'id-suma-asegurada-producto',
            				name:'sumaAseguradaProducto',
            				fieldLabel: '<s:text name="productos.configProductoSumaAsegurada.sumaAsegurada"/>',            				
        				    allowBlank: false,
        				    blankText : '<s:text name="productos.configProductoSumaAsegurada.sumaAsegurada.req"/>',            				
            				//hideParent:true,
            				labelSeparator:'',
            				width:100	
            							       
           });

var dsTipoSumaAsegurada = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'sumaAsegurada/CatalogoTipoSumaAsegurada.action'+'?catalogo='+'tipoSumaAsegurada'
        }),
        reader: new Ext.data.JsonReader({
            root: 'catalogoTipoSumaAsegurada',
            id: 'tipoSumaAsegurada'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}      
        ]),
		remoteSort: true
    });
  	dsTipoSumaAsegurada.setDefaultSort('tipoSumaAsegurada', 'desc'); 
  	dsTipoSumaAsegurada.load();
  	
var TipoSumaAsegurada =new Ext.form.ComboBox({
							id:'combo-tipo-suma-asegurada',
    	                    tpl: '<tpl for="."><div ext:qtip="{key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsTipoSumaAsegurada,						
						    displayField:'value',
						    listWidth: '250',
						    allowBlank: false,
						    blankText : '<s:text name="productos.configProductoSumaAsegurada.tipoSumaAsegurada.req"/>',
						    valueField: 'key',					    	
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText: '<s:text name="productos.configProductoSumaAsegurada.sumaAsegurada.sel"/>',
					    	selectOnFocus:true,
						    fieldLabel: '<s:text name="productos.configProductoSumaAsegurada.tipoSumaAsegurada"/>',
						    labelSeparator:'',
					    	name:"descripcionTipoSumaAsegurada",
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
								Ext.getCmp('hidden-combo-tipo-suma-asegurada').setValue(valor);
							}
					    	
			});
			
			
var dsMonedaSumaAsegurada = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'sumaAsegurada/CatalogoTipoSumaAsegurada.action'+'?catalogo='+'monedaSumaAsegurada'
        }),
        reader: new Ext.data.JsonReader({
            root: 'catalogoMoneda',
            id: 'monedaSumaAsegurada'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}      
        ]),
		remoteSort: true
    });
  	dsMonedaSumaAsegurada.setDefaultSort('monedaSumaAsegurada', 'desc'); 
  	dsMonedaSumaAsegurada.load();
  	
var MonedaSumaAsegurada =new Ext.form.ComboBox({
							id:'combo-moneda-suma-asegurada',
    	                    tpl: '<tpl for="."><div ext:qtip="{key}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsMonedaSumaAsegurada,						
						    displayField:'value',
						    listWidth: '250',
						    allowBlank: false,
						    blankText : '<s:text name="productos.configProductoSumaAsegurada.monedaSumaAsegurada.req"/>',
						    valueField: 'key',					    	
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText: '<s:text name="productos.configProductoSumaAsegurada.monedaSumaAsegurada.sel"/>',
					    	selectOnFocus:true,
						    fieldLabel: '<s:text name="productos.configProductoSumaAsegurada.monedaSumaAsegurada"/>',
						    labelSeparator:'',
					    	name:"descripcionMonedaSumaAsegurada",
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
								Ext.getCmp('hidden-combo-moneda-suma-asegurada').setValue(valor);
							}
					    	
			});			




			
//**************************************************************	

//************FORMA****************
	 sumaAseguradaProductoForm = new Ext.form.FormPanel({
            	id:'suma-asegurada-producto-form',
            	//boder:false,
            	baseCls: 'x-plain',
            	frame:true,
            	labelAlign: 'left',
            	url:'sumaAsegurada/AgregaSumaAsegurada.action?nivel=producto',
            	//labelWidth: 100,
        		//title: '<s:text name="productos.configSumaAsegurada.titulo"/>',
       			//bodyStyle:'padding:5px',
        		width:300,
        		//height:250, 
        		buttonAlign: "center",
                items: [{xtype:'hidden',id:'hidden-combo-tipo-suma-asegurada',name:'codigoTipoSumaAsegurada'},
                		{xtype:'hidden',id:'hidden-combo-moneda-suma-asegurada',name:'codigoMonedaSumaAsegurada'},                		                	
                		{xtype:'hidden',id:'hidden-codigo-capital',name:'codigoCapital'},
                		{
                		layout:'form',
                		baseCls: 'x-plain',                		
                		//id:'id-hidden-form-suma-asegurada-producto',
                		border: false,
                		labelAlign:'top',
                		items:[{
	        				layout:'column',
	        				baseCls: 'x-plain',
		        			border: false,
        	    				items:[{
					                columnWidth:.5,
                					layout: 'form',
                					baseCls: 'x-plain',
                					border: false,
                					width:170,
				            	    items: [
				                		TipoSumaAsegurada
				                	]
        						},{
					                columnWidth:.5,
    	            				layout: 'form',
    	            				baseCls: 'x-plain',
        	        				border: false,
					                items: [
					                	sumaAseguradaText
				    	            ]
        						}]        		
		        		}]
		        		},{
        				layout:'form',
        				//id:'id-hidden-form-suma-asegurada-inciso',
        				baseCls: 'x-plain',        				
        				border: false,
            			items:[{
            				layout:'column',
            				border:false,
            				baseCls: 'x-plain',
            				labelAlign:'top',
            				items:[{
            					columnWidth:.5,
                				layout: 'form',
                				baseCls: 'x-plain',
                				border: false,
                				width:170,
				            	items: [
				                	MonedaSumaAsegurada
				                ]
            				},{
					            columnWidth:.5,
    	            			layout: 'form',
    	            			baseCls: 'x-plain',
        	        			border: false,
        	        			width:170
					            
        					}]
        				}]      		
			        	}
			    	]
			});
		    
		    // define window and show it in desktop
    var sumaAseguradaWindow = new Ext.Window({
        title: '<s:text name="productos.configSumaAsegurada.titulo.producto"/>',
        width: 380,
        height:200,
        //minWidth: 300,
        //minHeight: 150,
        layout: 'fit',
        plain:true,
        //bodyStyle:'padding:5px;',
        buttonAlign:'center',
        modal : true,
  
        items: sumaAseguradaProductoForm,
		        
		buttons: [{
	            	text: '<s:text name="productos.configSumaAsegurada.btn.guardar"/>',
	            	handler: function() {                	
                			 
                		if (sumaAseguradaProductoForm.form.isValid()) {
                			
		 		        	sumaAseguradaProductoForm.form.submit({			      
					            	waitTitle:'<s:text name="ventana.configSumaAsegurada.proceso.mensaje.titulo"/>',
					            	waitMsg:'<s:text name="ventana.configSumaAsegurada.proceso.mensaje"/>',
					            	failure: function(form, action) {
						   				Ext.MessageBox.alert('Status', 'Suma asegurada no agregada');
									},
									success: function(form, action) {
						    			Ext.MessageBox.alert('Status', 'Suma asegurada agregada');
						    			sumaAseguradaWindow.close();
						    			storeSumaAsegurada.load();
						    				
						    		
						}
			        		});                   
                		} else{
					Ext.MessageBox.alert('Error', 'Favor de llenar campos requeridos');
						}             
	        		}
    		    },{
	            	text: '<s:text name="productos.configSumaAsegurada.btn.cancelar"/>',
	            	handler: function(){
	            		sumaAseguradaWindow.close();
	            	}
    		    }]
    	});    	    	
	
	sumaAseguradaWindow.show();
    };



</script>