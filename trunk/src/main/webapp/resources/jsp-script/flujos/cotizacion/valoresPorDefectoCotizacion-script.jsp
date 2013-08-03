<script type="text/javascript">

Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
	function obtieneRegistro(){
		url='flujocotizacion/agregaIncisos.action';
		store= new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url: url
	        }),
			reader: new Ext.data.JsonReader({
				root:'gridIncisos',
				totalProperty:'totalCount'
			},<s:component template="despliegaMappingColumn.vm" templateDir="templates" theme="components" >
                <s:param name="listRecords" value="listRecords" />
              </s:component>  
            ),
			remoteSort:true
			});
			store.setDefaultSort('gridIncisos','desc');
			store.load();
			return store;
	}	
	var store;
	
	///////////////////////////////////////
    var simple = new Ext.FormPanel({
        "xtype" : "form",
        "title" : "pagina 1 test",
        "autoScroll" : "true",
        "items" :  ElementosExt,
        "renderTo" : "items",
        "labelWidth" : 120,
        "id" : "simpleForm",
        "bodyStyle" : "padding:5px 5px 0",
        "autoHeight" : true,
        "labelAlign" : "right",
        "layout" : "form",
        "buttons" : [{
            	text:'Agregar',
            	tooltip:'Agregar Elemento',
            	handler: function() {
	                if (simple.form.isValid()) {
		 		        simple.form.submit({	
		 		        	url:'agregaIncisos.action',	
				            waitMsg:'Procesando',
				            failure: function(form, action) {
							    Ext.MessageBox.alert('Estado','Error agregando elemento');
							},
							success: function(form, action) {
							    Ext.MessageBox.alert('Elemento agregado exitósamente', action.result.info);
							    store.load();
							}
				        });                   
	                } else{
						Ext.MessageBox.alert('Error', 'Complete todos los campos Requeridos');
					}             
		        }
            	},{
	            "buttonType" : "reset",
	            "failureAction" : "",
	            "handler" : function() {
	                simple.form.reset();
	            },
	            "id" : "limpiar",
	            "successAction" : "flujocotizacion/limpiar.action",
	            "text" : "Limpiar",
	            "type" : "reset",
	            "url" : "",
	            "xtype" : "button"
        }]
    });
    simple.render();
    ///////////////////////////////////////
	
	/*****se crea la ventana de borrar un elemento***/

    var msgBorrar = new Ext.form.TextField({
        fieldLabel:'¿Esta seguro que desea Eliminar el elemento?',
        labelSeparator:'',
        anchor:'90%',
        hidden:true
    });                   
   
    var cdElemento = new Ext.form.NumberField({
        fieldLabel: '',   
        name:'cdElemento',
        anchor:'90%',
        hiddeParent:true,
        labelSeparator:'',
        hidden:true
    });   
    var borrarForm= new Ext.FormPanel({
        id:'borrarForm',
        labelAlign:'top',
        baseCls: 'x-plain',
        anchor:'100%',
        url:'borrarIncisos.action',
        items:[ msgBorrar, cdElemento]
    });

    var windowDel = new Ext.Window({
        title: 'Eliminar Inciso',
        minHeight: 50,
        minWidth: 250,
        width: 250,
        height:120,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: borrarForm,
        buttons: [{
                text: 'Eliminar', 
                handler: function() {
                    if (borrarForm.form.isValid()) {
                            borrarForm.form.submit({          
                                waitMsg:'Procesando...',
                                failure: function(form, action) {
                                    Ext.MessageBox.alert('Error Eliminando Elemento', action.result.errorInfo);
                                    windowDel.hide();
                                },
                                success: function(form, action) {
                                    Ext.MessageBox.alert('Elemento Eliminado', action.result.info);
                                    windowDel.hide();
                                    gridConf.destroy();
                                    createGrid();
                                    store.load();                                      
                                }
                            });                   
                    }else{
                        Ext.MessageBox.alert('Error', 'Verifique!');
                    }             
                }
            },{
                text: 'Cancelar',
                handler: function(){windowDel.hide();}
            }]
        });
/*****/

/*****se crea la ventana de cotizar un elemento***/

    var cdElementoCot = new Ext.form.NumberField({
        fieldLabel: '',   
        name:'cdElemento',
        anchor:'90%',
        hiddeParent:true,
        labelSeparator:'',
        hidden:true
    });   
    var cotizarForm= new Ext.FormPanel({
        id:'cotizarForm',
        labelAlign:'top',
        baseCls: 'x-plain',
        anchor:'100%',
        url:'cotizarElemento.action',
        items:[ cdElementoCot]
    });

    var windowCotiz = new Ext.Window({
        title: 'Cotizar',
        minHeight: 50,
        minWidth: 250,
        width: 250,
        height:120,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: cotizarForm,
        buttons: [{
                text: 'Cotizar', 
                handler: function() {
                    if (cotizarForm.form.isValid()) {
                            cotizarForm.form.submit({          
                                waitMsg:'Procesando...',
                                failure: function(form, action) {
                                    Ext.MessageBox.alert('Error Cotizando Elemento', action.result.errorInfo);
                                    windowCotiz.hide();
                                },
                                success: function(form, action) {
                                    Ext.MessageBox.alert('Aviso', action.result.info);
                                    windowCotiz.hide();
                                    gridConf.destroy();
                                    createGrid();
                                    store.load();                                      
                                }
                            });                   
                    }else{
                        Ext.MessageBox.alert('Error', 'Verifique!');
                    }             
                }
            },{
                text: 'Cancelar',
                handler: function(){windowDel.hide();}
            }]
        });
/*****/

	<s:component template="despliegaColumnGridIncisos.vm" templateDir="templates" theme="components" >
        <s:param name="listColumns" value="listColumns" />
    </s:component>
    
	var gridConf;
	var selectedId;
	function createGrid(){
		gridConf = new Ext.grid.GridPanel({
		store: obtieneRegistro(),
		id:'lista-lista',
		border:true,
		baseCls:' background:white',
		cm: cm,
		buttons:[
				{
            	text:'Eliminar',
            	id:'borrar',
            	handler:function(){
                   if(!gridConf.getSelectionModel().getSelected()){
                   	  Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                     }
                   },
            	tooltip:'Elimina Elemento'
            	},{
            	text:'Cotizar',
            	id:'cotizar',
            	handler:function(){
                   if(!gridConf.getSelectionModel().getSelected()){
                   	  Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                     }
                   },
            	tooltip:'Cotiza el Elemento seleccionado'
            	}
            	],
        width:600,
        frame:true,
        height:300,
        buttonAlign:'left',
        title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
        //renderTo:document.body,
    	sm:new Ext.grid.RowSelectionModel({
    	singleSelect:true,
    	listeners: {                            
                        rowselect: function(sm, row, rec) {
                        		selectedId = store.data.items[row].id;
                        		
                                Ext.getCmp('borrar').on('click', function(){
                                	windowDel.show();
                                	Ext.getCmp('borrarForm').getForm().loadRecord(rec);
                                });  
                                
                                Ext.getCmp('cotizar').on('click',function(){
                                	//windowCotiz.show();
							        //Ext.getCmp('cotizarForm').getForm().loadRecord(rec);
							        //OpcionesVentanaCotizacion(store, rec, row);
							        var record = store.getAt(row);
        							var cdElemento = record.get('cdElemento');
        							window.location.href = _CONTEXT + '/flujocotizacion/cotizarElemento.action?cdElemento=' + cdElemento;
								});                                
                        }
                }

    	}),        
    viewConfig: {autoFill: true,forceFit:true},                
	bbar: new Ext.PagingToolbar({
		pageSize:25,
		store: store,									            
		displayInfo: true,
		displayMsg: 'Displaying rows {0} - {1} of {2}',
		emptyMsg: "No rows to display"      		    	
		})
		});
		gridConf.render('gridConfig');
	}
	
	createGrid();
}); 

</script>