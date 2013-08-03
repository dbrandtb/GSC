<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->
<jsp:include page="/resources/scripts/jsp/utilities/incisos/catalogo/agregarIncisos.jsp" flush="true" />

<script type="text/javascript">
Ext.onReady(function() {
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = "side";
    var banderaSelectComboIncisos = false;          
   	var ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'incisos/IncisosLista.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'incisos',
            id: 'incisos'
	        },[
           {name: 'clave', type: 'string',mapping:'cdtipsit'},
           {name: 'descripcion', type: 'string',mapping:'dstipsit'}    
        ]),
		remoteSort: true
    });
       ds.setDefaultSort('incisos', 'desc'); 
    
    var obligatorioCheck= new Ext.form.Checkbox({
    			id:'obligatorio-check',
                name:'obligatorio',
                fieldLabel: '<s:text name="productos.configIncisos.obligatorio"/>',
                checked:false,
                onClick:function(){
				            	if(this.getValue()){
				            	this.setRawValue("S");				            	
				            	insertaCheck.setValue(true);
				            	insertaCheck.setRawValue("S");				            	
				            	}
				            }
                });
                
       //obligatorioCheck.setRawValue("N");
	var insertaCheck= new Ext.form.Checkbox({
				id:'inserta-check',
                name:'inserta',
                fieldLabel: '<s:text name="productos.configIncisos.inserta"/>',
                checked:false,                
                onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}
				            }
                });
               // insertaCheck.setRawValue("N");
    // simple array store
//var store = new Ext.data.SimpleStore({
  //  fields: ['clave', 'descripcion', 'nick']
    
//});            
                
    var comboWithTooltip = new Ext.form.ComboBox({
    tpl: '<tpl for="."><div ext:qtip="{clave}" class="x-combo-list-item">{descripcion}</div></tpl>',
    store: ds,
    id:'combo-codigo-inciso-del-producto',
    displayField:'descripcion',
    valueField: 'clave',
    allowBlank:false,
    blankText : '<s:text name="productos.configIncisos.descripcion.req"/>',
    //maxLength : '2',
    //maxLengthText : 'Dos caracteres maximo',
    //typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    emptyText:'<s:text name="productos.configIncisos.descripcion.vacia"/>',
    selectOnFocus:true,
    fieldLabel: '<s:text name="productos.configIncisos.descripcion"/>',
    listWidth:250,
    name:"descripcion",
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
	    	banderaSelectComboIncisos = true;
	    	Ext.getCmp('hidden-clave-combo-inciso').setValue(record.get('clave'));                      	
                   			
    		//alert(banderaSelectCombo);
    }
});            
       
	var incisosForm = new Ext.form.FormPanel({
            	id:'forma-inciso',
            	boder:false,
            	labelAlign: 'left',
            	url:'incisos/asociarInciso.action',
            	labelWidth: 130,
        		title: '<s:text name="productos.configIncisos.title.principal"/>',
       			bodyStyle:'padding:5px',
        		width: 570,
        		buttonAlign: "center",
                items: [new Ext.Panel({
										id: 'details-panel-forma-inciso',
								        border:false,	
								        heigth:'50'
					    	}),{
					    	layout:'form',
					    	border:false,
					    	id:'hidden-form-tipo-inciso',
					    	items:[{
		 				            layout:'column',
 						            border:false,
            						items:[{
							                columnWidth:.73,
            			    				layout: 'form',
                							border:false,
			                				width:300,
							                items: [
				        			        	comboWithTooltip
							                ]
						            	},{
                							columnWidth:.27,
							                layout: 'form',
							                border:false,
                							items: [{
								                    xtype:'button',
								                    text: '<s:text name="btn.agregar"/>',
                								    name: 'AgregarAlCatalogo',
								                    //buttonAlign: "right",
                								    handler: function() {                   				    	             	
					               							creaAltaCatalogoIncisos(ds);//script que tienes afuera	
    			     								}	
						                	}]
				        		   	}]
				        	}]	   	
		        		},obligatorioCheck,
		        		  insertaCheck,
		        
		        		{
    		    		    xtype:'numberfield',
				            name: "agrupador",
				            allowDecimals : false,
				            allowNegative : false,
				            maxLength : '2',
				            maxLengthText : '<s:text name="productos.configIncisos.longitudMax"/>',
        				    allowBlank: false,
        				    blankText : '<s:text name="productos.configIncisos.agrupador.req"/>',
        				    disableKeyFilter : true,
        				    fieldLabel: '<s:text name="productos.configIncisos.agrupador"/>',
				            width: 150
    	    			}/*,{
		        		    xtype:'numberfield',
    	    			    name: "NumeroInciso",
    	    			    allowDecimals : false,
				            allowNegative : false,
				            maxLength : '2',
				            maxLengthText : '<s:text name="productos.configIncisos.LongitudMax"/>,
    	    			    allowBlank: false,
    	    			    blankText : '<s:text name="productos.configIncisos.NoInciso.req"/>',
				            fieldLabel: '<s:text name="productos.configIncisos.NoInciso"/>',
        				    width: 150
		        	},{layout:'form',contentEl:'texto-obligatorios',border:false}*/
		        	,{xtype:'hidden',id:'hidden-clave-combo-inciso',name:'clave'}],
		        	

        		buttons: [{
	            	text: '<s:text name="btn.asocia"/>' ,
	            	handler: function() {
                	// check form value 
                		if (incisosForm.form.isValid()) {
	                		//alert(Ext.getCmp('hidden-clave-combo-inciso').getValue());
							
		 		        	incisosForm.form.submit({			      
					            	waitTitle:'<s:text name="ventana.proceso.mensaje.titulo"/>',
					            	waitMsg:'<s:text name="ventana.proceso.mensaje.asociar"/>',
					            	failure: function(form, action) {
								    Ext.MessageBox.alert('Error', 'Error al asociar inciso');
									},
									success: function(form, action) {
								    //Ext.MessageBox.alert('Inciso asociado', action.result.info);
								    if( Ext.getCmp('hidden-form-tipo-inciso').isVisible()){
								    	incisosForm.form.reset();
								    }
								    Ext.getCmp('arbol-productos').getRootNode().reload();						    		
						    		//action.form.reset();    		
								}
			        		});                   
                		} else{
							Ext.MessageBox.alert('Error', 'Favor de llenar campos requeridos.');
						}             
	        		}
    		    },{
	            	text: '<s:text name="btn.eliminar"/>'
    		    }]        
    	});

//ds.load({params:{start:0, limit:25}});    
Ext.getCmp('details-panel-forma-inciso').hide();
incisosForm.render('centerIncisos');
//bd.createChild({tag: 'h2', html: ' * Datos requeridos'});
}); 

</script>