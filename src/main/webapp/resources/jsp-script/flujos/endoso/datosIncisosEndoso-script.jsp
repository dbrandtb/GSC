<%@ include file="/taglibs.jsp"%>
<script type="text/javascript">


Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//Store para grid de Cobreturas
function createOptionGrid(){  
	url=_ACTION_LISTA_COBERTURAS 
    storeCob = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: url
        }),
        reader: new Ext.data.JsonReader({
            root: 'listCoberturasIncisos',
            id: 'cdGarant'
        },[
                {name: 'cdGarant',        type: 'string',    mapping:'cdGarant'},
                {name: 'dsGarant',        type: 'string',    mapping:'dsGarant'},
               	{name: 'dsSumaAsegurada', type: 'string',    mapping:'dsSumaAsegurada'},
                {name: 'dsDeducible',     type: 'string',    mapping:'dsDeducible'}
        ]),
        remoteSort: true
    });
    storeCob.setDefaultSort('cdGarant', 'desc');
    storeCob.load();
    return storeCob;
}
var storeCob;
//Store para grid de Cobreturas
function createGridAccesorios(){
	var storeA = new Ext.data.Store({
        	proxy: new Ext.data.HttpProxy({
            url: 'flujoendoso/accesoriosIncisos.action?cdUnieco='+idAseguradora.getValue()+'&cdRamo='+idProducto.getValue()+'&estado='+idEstado.getValue()+'&nmPoliza='+idPoliza.getValue()+'&nmSituac='+idNmSituac.getValue()
        }),
        reader: new Ext.data.JsonReader({
            root: 'listAccesoriosIncisos',
            id: 'label'
        },[
                {name: 'label', type: 'string', mapping:'label'},
                {name: 'value', type: 'string', mapping:'value'}
        ]),
        remoteSort: true
    });
    storeA.load();
    return storeA;
}

//DATOS DE APOYO
	var idAseguradora = new Ext.form.Hidden({
	    name:'cdUnieco',
	    value:_cdunieco
	});
	var idProducto = new Ext.form.Hidden({
	    name:'cdRamo',
	    value:_cdramo
	});
	var idPoliza = new Ext.form.Hidden({
	    name:'nmpoliza',
	    value:_nmpoliza
	});
    var idEstado = new Ext.form.Hidden({
        name:'estado',
        value:_estado
    });
    var idNmSituac = new Ext.form.Hidden({
        name:'nmsituac',
        value:_nmsituac
    });
    var idStaus = new Ext.form.Hidden({
        name:'status',
        value:_status
    });
    var idTipSit = new Ext.form.Hidden({
        name:'cdTipsit',
        value:_cdtipsit
    });
	var nombreAseguradora = new Ext.form.TextField({
	    fieldLabel: 'Aseguradora',
	    name:'dsUnieco',
	    value:_aseguradora,
	    disabled:true,
	    width: 200
	});
	var nombreProducto = new Ext.form.TextField({
	    fieldLabel: 'Producto',
	    name:'dsRamo',
	    value:_producto,
	    disabled:true,
	    width: 200
	});
	var numPoliza = new Ext.form.TextField({
	    fieldLabel: 'Poliza',
	    name:'poliza',
	    value:_poliza,
	    disabled:true,
	    width: 200
	});
	var numInciso = new Ext.form.TextField({
	    fieldLabel: 'Inciso',
	    name:'cdInciso',
	    value:_cdinciso,
	    disabled:true,
	    width: 200
	});
	var nombreDescripcion = new Ext.form.TextField({
	    fieldLabel: 'Calculo Pago',
	    name:'dsDescripcion',
	    value:_dsdescripcion,
	    disabled:true,
	    width: 200
	});
    
    var opcionesForm = new Ext.form.FormPanel({    
    	el:'encabezadoInciso',
        //title: '<span style="color:black;font-size:14px;">Inciso</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',   
        frame:true,                         
        width: 680,
        autoHeight: true,
        items: [{
                layout:'form',
                border: false,
                items:[{
                    //title :'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">B&uacute;squeda</span>',
                    bodyStyle:'background: white',
                	labelWidth: 100,                	
                	layout: 'form',
                	frame:true,
                	baseCls: '',
                	buttonAlign: "center",
                        items:[{
                            layout:'column',
                            border:false,
                            //columnWidth: 4,
                            items:[{
                                //columnWidth:4,
                                layout: 'form',                     
                                border: false,                      
                                items:[ 
                                        nombreAseguradora,
                                        nombreProducto,
                                        numPoliza

                                ]
                            },
                            {
                                //columnWidth:4,
                                layout: 'form',                     
                                border: false,                      
                                items:[ 
                                        numInciso,
                                        nombreDescripcion

                                ]
                            }]
                        }]
                }]
		}]  
	});
	opcionesForm.render();

//DATOS INCISOS
    var datosIncisosForm = new Ext.form.FormPanel({    
    	//el:'datosInciso',
        title: '<span style="color:#98012e;font-size:14px;">Datos</span>',
        collapsible: true,
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'center',   
        frame:true,
        width: 670,
        autoHeight: true,
        items: [{
                layout:'form',
                border: false,
                items:[{
                    //title :'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">B&uacute;squeda</span>',
                    bodyStyle:'background: white',
                	labelWidth: 155,                	
                	layout: 'form',
                	frame:true,
                	baseCls: '',
                    buttonAlign: "center",
                        items:[{
                            layout:'column',
                            border:false,
                            columnWidth: 2,
                            items:[{
                                columnWidth:2,
                                layout: 'form',                     
                                border: false,                      
                                items: ElementosExt
                            },
                            {
                             	columnWidth:.5,
                             	layout: 'form'
                            }],
                                buttons:[{
                                    text: 'Guardar',
                                    tooltip: 'Guardar',
                                    handler: function(){
                                        //windowDetalleCobertura.close();
                                    }//handler
                                }]
                        }]
                }]
		}]  
	});
	
//******************DETALLE COBERTURAS******************
var itemsVariables;
var valorCombo;

function detalleCoberturas(cdGarant,dsGarant,dsSumaAsegurada){
    var conn = new Ext.data.Connection();
    conn.request({
         url: 'flujoendoso/datosCoberturas.action?cdUnieco='+idAseguradora.getValue()+'&cdRamo='+idProducto.getValue()+'&estado='+idEstado.getValue()+'&nmPoliza='+idPoliza.getValue()+'&nmSituac='+idNmSituac.getValue()+'&cdGarant='+cdGarant+'&status='+idStaus.getValue(),
         method: 'POST',
         successProperty : '@success',
         callback: function (options, success, response){
                     if(Ext.util.JSON.decode(response.responseText).success == false){
                         Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                     }else{
                         var codeExtResp = Ext.util.JSON.decode(response.responseText).extElements;
                         var codeExtJson = Ext.util.JSON.encode(codeExtResp);
                         
                         if(codeExtResp != ''){
                            var newStartStore = "\"store\":";
                            var newEndStore = ",\"triggerAction\"";
                            var onSelectVar = "";
                            
                            codeExtJson = codeExtJson.replace(/\"store\":\"/gi, newStartStore);
                            codeExtJson = codeExtJson.replace(/\",\"triggerAction\"/gi, newEndStore);
                            codeExtJson = codeExtJson.replace(/,\"onSelect\":null/gi, onSelectVar);
                            
                            var itemsDecode = Ext.util.JSON.decode(codeExtJson);
                            
                            var campoIncis = new Ext.form.TextField({                              
                                name:'inciso',
                                value:idNmSituac.getValue(),
                                fieldLabel: 'Inciso',
                                disabled:true,
                                width: 200
                            });
                            
							var campoGrant = new Ext.form.TextField({								
								name:'garantia',
								value:dsGarant,
								fieldLabel: 'Cobertura',
								disabled:true,
								width: 200
							});
	                     	var campoSuma = new Ext.form.NumberField({	                     	 	
								name:'suma',
								value:dsSumaAsegurada,
								fieldLabel: 'Suma Asegurada',
								width: 200,
								allowDecimals: true,
								allowNegative: false
	                     	});
                             var panelDetalleCoberturaEnc = new Ext.FormPanel({
                                    labelAlign:'right',
                                    border: false,
                                    width: 350,
                                    autoHeight: true,
                                    items:[
                                            //campoIncis,
			                                campoGrant,
                                            campoSuma
			                        ]                                    
                             });
                             
                             
                             var panelDetalleCobertura = new Ext.FormPanel({
                                    labelAlign:'right',
                                    border: false,
                                    width: 350,
                                    autoHeight: true,
                                    items: itemsDecode
                             });
                             
                             /////////////////////////////
                             
                             var windowCobertura = new Ext.Window({
                                title: 'Datos Coberturas',
                                width: 350,
                                autoHeight: true,
                                layout: 'fit',
                                plain:true,
                                modal:true,
                                bodyStyle:'padding:5px;',
                                buttonAlign:'center',
                                items: [panelDetalleCoberturaEnc,panelDetalleCobertura],
                                buttons:[{
                                    text: 'Guardar',
                                    tooltip: 'Guardar',
                                    handler: function(){
                                        //windowCobertura.close();
                                        //windowCobertura.destroy();
                                    }
                                    },{
                                    text: 'Regresar',
                                    tooltip: 'Cierra la ventana',
                                    handler: function(){
                                        windowCobertura.close();
                                        windowCobertura.destroy();
                                    }//handler
                                }]
                            });
                            windowCobertura.show();
                         }else{
                            Ext.Msg.alert('Mensaje', 'No se encontraron datos');
                         }
                      }//else
                   }//function
    });
}

//******************AGREGAR COBERTURAS******************
agregarCobertura = function(storeCob){
							var campoIncis = new Ext.form.TextField({                              
                                name:'inciso',
                                value:_cdinciso,
                                fieldLabel: 'Inciso',
                                disabled:true,
                                width: 200
                            });
                            
                            //////////////////////////// LISTA COBERTURAS
                            var storeComboCoberturasSelect = new Ext.data.Store({
                                    proxy: new Ext.data.HttpProxy({
                                        url: _ACTION_CARGA_COBERTURAS
                                    }),
                                    reader: new Ext.data.JsonReader({
                                        root: 'listaCoberturas'
                                        },[
                                       {name: 'value',      type: 'string', mapping:'value'},
                                       {name: 'label',      type: 'string', mapping:'label'}
                                    ]),
                                    remoteSort: true
                            });
                            storeComboCoberturasSelect.setDefaultSort('label', 'desc');
                            storeComboCoberturasSelect.load();
                            
                            var comboCoberturas = new Ext.form.ComboBox({
                            		id:'combo-coberturas',
                                    tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
                                    store: storeComboCoberturasSelect,
                                    displayField:'label',
                                    valueField: 'value',
                                    typeAhead: true,
                                    labelAlign: 'top',
                                    heigth:'50',
                                    listWidth:'150',        
                                    mode: 'local',
                                    triggerAction: 'all',
                                    emptyText:'Seleccione...',
                                    selectOnFocus:true,
                                    fieldLabel: 'Cobertura',
                                    name:"parameters",
                                    onSelect : function(record, index, skipCollapse){
					               		if(this.fireEvent('beforeselect', this, record, index) !== false){
					                  		this.setValue(record.data[this.valueField || this.displayField]);
					                  		if( !skipCollapse ) {
					                   			this.collapse();
					                  		}
					                  		this.lastSelectedIndex = index + 1;
					                  		this.fireEvent('select', this, record, index);
					               		}
					               							               		
					        			var valor=record.get('value');
					        			var label=record.get('label');
					        			var params="";        			
					        			params  = "claveObjeto="+valor;
					        			params += "&& descripcionObjeto="+label;
					        			params += "&cdRamo="+idProducto.getValue();
					        			params += "&estado="+idEstado.getValue();
					        			params += "&nmPoliza="+idPoliza.getValue();
					        			params += "&status="+idStaus.getValue();
					        			
					        			var conn = new Ext.data.Connection();
					        			var itemsVariablesDecode;
					        			var itemsVariablesEncode;
					        			
					                	conn.request ({
					                		url:_ACTION_CARGA_ITEMS,
					                		method: 'POST',
					                		successProperty : '@success',
					                		params : params,
					                		callback: function (options, success, response) {                		  
					                			itemsVariablesDecode = Ext.util.JSON.decode(response.responseText).extElementsAgregar;
					                			itemsVariablesEncode = Ext.util.JSON.encode(itemsVariablesDecode);
					                			var newStartStore = "\"store\":";
					                            var newEndStore = ",\"triggerAction\"";
					                            var onSelectVar = "";
					                            
					                            itemsVariablesEncode = itemsVariablesEncode.replace(/\"store\":\"/gi, newStartStore);
					                            itemsVariablesEncode = itemsVariablesEncode.replace(/\",\"triggerAction\"/gi, newEndStore);
					                            itemsVariablesEncode = itemsVariablesEncode.replace(/,\"onSelect\":null/gi, onSelectVar);
					                            
					                            itemsVariables = Ext.util.JSON.decode(itemsVariablesEncode);
					                			validacion = Ext.util.JSON.decode(response.responseText).validoElementsAgregar;
					                			valorCombo = Ext.util.JSON.decode(response.responseText).labelCombo;
					                			if(validacion==true){                				
					                				windowCobertura.close();
					                				agregarCobertura(storeCob);
					                				Ext.getCmp('combo-coberturas').setValue(valorCombo);
					                			}else{
					                				Ext.MessageBox.alert('Error', 'No existen datos asociados');					                			
					                			}                		                	
											}
					                	});
					       			}
                            });
                            
                            //////////////////////////////////
							var campoSuma = new Ext.form.NumberField({	                     	 	
								fieldLabel: 'Suma Asegurada',
								width: 200,
								blankText : 'Suma Asegurada ',
				                allowBlank:false,
				                name:'sumaAsegurada' 
	                     	});
	                     	
	                     	var agregarCoberturaForm = new Ext.form.FormPanel({
				                url:_ACTION_GUARDA_COBERTURAS,
				                id:'agregarCoberturaForm',
								boder:false,
								frame:true,
								autoScroll:true,
								method:'post',            	            
				        		width: 540,
				        		buttonAlign: "center",
								baseCls:'x-plain',
								labelWidth:75,				                
				                items:[
										comboCoberturas,
											{  
											layout:'form',                                 	
											border:false,
											frame:true,
											method:'post',  
											baseCls:'x-plain',
											autoScroll:true,							
											items:[campoSuma]												
											},	
											{  
											layout:'form',                                 	
											border:false,
											frame:true,
											method:'post',  
											baseCls:'x-plain',
											autoScroll:true,							
											items: itemsVariables												
											}										
										]
				             });
            
				             itemsVariables = null;
				             
                             var windowCobertura = new Ext.Window({
                                title: 'Agregar Coberturas',
                                width: 460,
						        height:350,
						        autoScroll:true,
						        maximizable:true,
						        minWidth: 440,
						        minHeight: 300,
						        layout: 'fit',
						        modal:true,
						        plain:true,
						        bodyStyle:'padding:5px;',
						        buttonAlign:'center',
                                items: agregarCoberturaForm,
                                buttons:[{
                                    text: 'Guardar',
                                    tooltip: 'Guardar',
                                    handler: function(){
                                        if (agregarCoberturaForm.form.isValid()) {
						                    agregarCoberturaForm.form.submit({
						                        url:'', 
						                        waitTitle:'Espere',
						                        waitMsg:'Procesando...',
						                        failure: function(form, action) {
						                            Ext.MessageBox.alert('Estado','Error agregando Menu');
						                        },
						                        success: function(form, action) {
						                            Ext.MessageBox.alert('Estado','Cobertura Guardada');
						                            windowCobertura.close();
						                            storeCob.load();
						                        }
						                    });                   
						                } else{
						                    Ext.MessageBox.alert('Informacion incompleta', 'Por favor verifique');
						                }      
                                    }
                                    },{
                                    text: 'Regresar',
                                    tooltip: 'Cierra la ventana',
                                    handler: function(){
                                        windowCobertura.close();
                                    }//handler
                                }]
                            });
                            windowCobertura.show();
};

function borrarCobertura(cdGarant){
    alert('cdGarant : ' + cdGarant);
}

//GRID COBERTURAS
	var storeGrid = createOptionGrid();
	var cm = new Ext.grid.ColumnModel([
	    new Ext.grid.RowNumberer(),
	    {header: "cdGarant",       dataIndex:'cdGarant',        width: 120, sortable:true, hidden: true},
	    {header: "Cobertura",      dataIndex:'dsGarant',        width: 120, sortable:true},
	  	{header: "Suma Asegurada", dataIndex:'dsSumaAsegurada', width: 120, sortable:true},
	    {header: "Deducible",      dataIndex:'dsDeducible',     width: 120, sortable:true}
	]);
    var selectedId;

var gridCoberturas = new Ext.grid.GridPanel({
    store:storeGrid,
    border:true,
    buttonAlign: 'center',
    baseCls:' background:white ',
    cm: cm,
	buttons:[{
                id:'detallesCobertura',
                text:'Agregar',
                tooltip:'Agregar',
                handler:function(){
                	agregarCobertura();
                }
            },{
				id:'detallesCobertura',
				text:'Editar',
            	tooltip:'Editar'
            },{
                id:'borrarCobertura',
                text:'Eliminar',
                tooltip:'Eliminar Cobertura'
            }],                                 
    width:660,
    frame:true,
    height:200,
    
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
			rowselect: function(sm, row, rec) { 
		        selectedId = storeGrid.data.items[row].id;                                                                                                                                    
		        Ext.getCmp('detallesCobertura').on('click',function(){
		        	var cdGarant = rec.get('cdGarant');
		        	var dsGarant = rec.get('dsGarant');
		        	var dsSumaAsegurada = rec.get('dsSumaAsegurada');
					detalleCoberturas(cdGarant, dsGarant, dsSumaAsegurada);
		        });
                Ext.getCmp('borrarCobertura').on('click',function(){
                    var cdGarant = rec.get('cdGarant');
                    borrarCobertura(cdGarant);
                });
			}
        }
        
    }),
    viewConfig: {autoFill: true,forceFit:true}, 
    bbar: new Ext.PagingToolbar({
        pageSize:20,
        store: storeGrid,                                               
        displayInfo: true,
        displayMsg: 'Displaying rows {0} - {1} of {2}',
        emptyMsg: "No rows to display"                      
        })
    });


//DETALLE AGRUPADOR
/******************************************************************************************************************************
 *************************************************Se declaran las variables****************************************************
 ******************************************************************************************************************************/
var codigoPersonaUsuario = new Ext.form.Hidden({    
    id:'hidden-codigo-persona-usuario-compra',
    name:'codigoPersonaUsuario'
}); 

var codigoDomicilio = new Ext.form.Hidden({ 
    id:'hidden-codigo-domicilio-compra',
    name:'codigoDomicilio'
});

var codigoInstrumentoPago = new Ext.form.Hidden({   
    id:'hidden-codigo-intrumeto-pago-compra',
    name:'codigoInstrumentoPago'
});

var codigoBanco = new Ext.form.Hidden({
    id:'hidden-codigo-banco-compra',
    name:'codigoBanco'
}); 

var codigoSucursal = new Ext.form.Hidden({  
    id:'hidden-codigo-sucursal-compra',
    name:'codigoSucursal'
});

var codigoTipoTarjeta = new Ext.form.Hidden({  
    id:'hidden-codigo-tipo-tarjeta',
    name:'codigoTipoTarjeta'
});

var numeroCuenta = new Ext.form.Hidden({  
    id:'hidden-numero-cuenta',
    name:'numeroCuenta'
});

/**********************************************
 **          Catalogos Compra                **
 **********************************************/
var storeComboPersonasUsuarioMultiSelect = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujoendoso" action="listaPersonasUsuarioMultiSelect" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaPersonasUsuarioMultiSelect'
            },[
           {name: 'value',      type: 'string', mapping:'value'},
           {name: 'label',      type: 'string', mapping:'label'}
        ]),
        remoteSort: true
});
storeComboPersonasUsuarioMultiSelect.load();
var storeComboPersonasUsuario = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujoendoso" action="listaPersonasUsuario" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaPersonasUsuario'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}
        ]),
        remoteSort: true
});
storeComboPersonasUsuario.load();
var storeComboTipoTarjeta = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujoendoso" action="listaTipoTarjeta" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaTipoTarjeta'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}
        ]),
        remoteSort: true
});
storeComboTipoTarjeta.load();
var storeComboDomicilio = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujoendoso" action="listaDomicilio" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaDomicilio'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}
        ]),
        remoteSort: true
});
storeComboDomicilio.load();
var storeComboInstrumentoDePago = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujoendoso" action="listaFormasDePago" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaIntrumentoPago'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}
        ]),
        remoteSort: true
});
storeComboInstrumentoDePago.load();
var storeBanco = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujoendoso" action="listaBancos" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaBancos'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}
        ]),
        remoteSort: true
});
storeBanco.load();
var storeSucursal = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujoendoso" action="listaSucursal" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaSucursal'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}
        ]),
        baseParams:{codigoBanco:''},
        remoteSort: true
});
//storeSucursal.load();
var comboPersonaUsuario =new Ext.form.ComboBox({
        tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeComboPersonasUsuario,
        displayField:'label',
        editable: true, 
        typeAhead: true,
        labelAlign: 'top',
        heigth:'50',
        listWidth:'150',        
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione...',
        selectOnFocus:true,
        fieldLabel: 'Pagado Por',
        name:"dsNombre",
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
            var valor = record.get('value');        
            Ext.getCmp('hidden-codigo-persona-usuario-compra').setValue(valor);
            //alert(Ext.getCmp('hidden-codigo-persona-usuario-compra').getValue());
        }
});
var comboDomicilio =new Ext.form.ComboBox({
        tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeComboDomicilio,
        displayField:'label',
        valueField: 'value',
        typeAhead: true,
        labelAlign: 'top',
        heigth:'50',
        listWidth:'150',        
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione...',
        selectOnFocus:true,
        fieldLabel: 'Domicilio',
        name:"dsDomicilio",
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
            var valor = record.get('value');
            
            Ext.getCmp('hidden-codigo-domicilio-compra').setValue(valor);
            //alert(Ext.getCmp('hidden-codigo-domicilio-compra').getValue());
        }
});
var comboInstrumentoDePago =new Ext.form.ComboBox({
        tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeComboInstrumentoDePago,
        displayField:'label',
        valueField: 'value',
        typeAhead: true,
        labelAlign: 'top',
        heigth:'50',
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione...',
        selectOnFocus:true,
        fieldLabel: 'Instrumento de pago',
        name:"dsForpag",
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
            var valor = record.get('value');
            //alert(valor);
            if(valor == 3// || valor == "3"
            ){
                Ext.getCmp('id-form-pago-hide').show();
            }else{
                Ext.getCmp('id-form-pago-hide').hide();
            }
            Ext.getCmp('hidden-codigo-intrumeto-pago-compra').setValue(valor);
            //alert(Ext.getCmp('hidden-codigo-intrumeto-pago-compra').getValue());
        }
});
var comboSucursal =new Ext.form.ComboBox({
        id:'descripcion-combo-sucursal-compra',
        tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeSucursal,
        displayField:'label',
        valueField: 'value',
        typeAhead: true,
        labelAlign: 'top',
        heigth:'50',
        listWidth:'150',        
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione...',
        selectOnFocus:true,
        fieldLabel: 'Sucursal',
        name:"descripcionSucursal",
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
            var valor = record.get('value');
            
            Ext.getCmp('hidden-codigo-sucursal-compra').setValue(valor);
            //alert(Ext.getCmp('hidden-codigo-sucursal-compra').getValue());
        }
});
var comboBanco =new Ext.form.ComboBox({
        tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeBanco,
        displayField:'label',
        valueField: 'value',
        typeAhead: true,
        labelAlign: 'top',
        heigth:'50',
        listWidth:'150',        
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione...',
        selectOnFocus:true,
        fieldLabel: 'Banco',
        name:"descripcionBanco",
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
            var valor = record.get('value');
            
            storeSucursal.baseParams['codigoBanco'] = valor;
            storeSucursal.load({
                callback : function(r,options,success) {
                    if (!success) {
                        storeSucursal.removeAll();
                    }
                }
            });
            Ext.getCmp('hidden-codigo-banco-compra').setValue(valor);
            //alert(Ext.getCmp('hidden-codigo-banco-compra').getValue());
        }
});
var comboTipoTarjeta =new Ext.form.ComboBox({
        tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeComboTipoTarjeta,
        displayField:'label',
        valueField: 'value',
        typeAhead: true,
        labelAlign: 'top',
        heigth:'50',
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione...',
        selectOnFocus:true,
        fieldLabel: 'Tipo de Pago',
        name:"descripcionTipoTarjeta",
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
            var valor = record.get('value');
            
            Ext.getCmp('hidden-codigo-tipo-tarjeta').setValue(valor);
        }
});
    var cdUnieco = new Ext.form.TextField({
            fieldLabel: '',
            allowBlank: false,   
            name:'cdUnieco',
            width: 300,
            hiddeParent:true,
            labelSeparator:'',
            hidden:true
    });    
    var cdRamo = new Ext.form.TextField({
            fieldLabel: '',
            allowBlank: false,   
            name:'cdRamo',
            width: 300,
            hiddeParent:true,
            labelSeparator:'',
            hidden:true
    });   
    var estado = new Ext.form.TextField({
            fieldLabel: '',  
            name:'estado',
            width: 300,
            hiddeParent:true,
            labelSeparator:'',
            hidden:true
    });    
    var nmPoliza = new Ext.form.TextField({
            fieldLabel: '',  
            name:'nmPoliza',
            width: 300,
            hiddeParent:true,
            labelSeparator:'',
            hidden:true
    });   
    var nmSuplem = new Ext.form.TextField({
            fieldLabel: '', 
            name:'nmSuplem',
            width: 300,
            hiddeParent:true,
            labelSeparator:'',
            hidden:true
    });    
    var cdAgrupa = new Ext.form.TextField({
            fieldLabel: '', 
            name:'cdAgrupa',
            width: 300,
            hiddeParent:true,
            labelSeparator:'',
            hidden:true
    });   
    var cdBanco = new Ext.form.TextField({
            fieldLabel: '',  
            name:'cdBanco',
            width: 300,
            hiddeParent:true,
            labelSeparator:'',
            hidden:true
    });    
    var cdForpag = new Ext.form.TextField({
            fieldLabel: '',   
            name:'cdForpag',
            width: 300,
            hiddeParent:true,
            labelSeparator:'',
            hidden:true
    });     
    var numeroTarjeta = new Ext.form.NumberField({
        name:'numeroTarjeta',
        fieldLabel:'Numero de Tarjeta',
        allowDecimals :false,
        allowNegative :false
    });
    var vencimiento = new Ext.form.DateField({
        name:'fechaVencimiento',
        fieldLabel:'Fecha de vencimiento',
        format:'d/m/Y'
    });
    var digitoVerificador = new Ext.form.NumberField({
        fieldLabel: 'Dígito Verificador',
        name:'digitoVerificador',
        allowDecimals :false,
        allowNegative :false,
        width:20,
        maxLength:1
    });
    
    var datosAgrupadorForm = new Ext.form.FormPanel({    
        title: '<span style="color:#98012e;font-size:14px;">Datos de Cobro</span>',
        id:'datosAgrupadorForm',
        url:'flujoendoso/editarAgrupador.action',
        collapsible: true,
        iconCls:'logo',
        //bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',   
        frame:true,
        width: 670,
        autoHeight: true,
        items: [{
                layout:'form',
                border: false,
                items:[{
                    bodyStyle:'background: white',
                    labelWidth: 155,                    
                    layout: 'form',
                    frame:true,
                    baseCls: '',
                    buttonAlign: "center",
                        items:[{
                            layout:'column',
                            border:false,
                            columnWidth: 2,
                            items:[{
                                columnWidth:2,
                                layout: 'form',                     
                                border: false,                      
                                items:[ comboPersonaUsuario,codigoPersonaUsuario,
                                            comboDomicilio,codigoDomicilio,
                                            comboInstrumentoDePago,codigoInstrumentoPago,
                                            {
                                            layout:'form',
                                            id:'id-form-pago-hide',
                                            hidden:true,
                                            border:false,
                                            items:[
                                                comboBanco,codigoBanco,
                                                comboSucursal,codigoSucursal,
                                                comboTipoTarjeta,codigoTipoTarjeta,
                                                numeroTarjeta,
                                                vencimiento,
                                                digitoVerificador,
                                                numeroCuenta ]
                                            },
                                            cdUnieco,cdRamo,
                                            estado,nmPoliza,
                                            nmSuplem,cdAgrupa
                                    ]                                  
                            },
                            {
                                columnWidth:.5,
                                layout: 'form'
                            }]
                        }]
                }]
        }]  
    });
    
var windowAgrupadores = new Ext.Window({
        title: 'Editar Agrupador',
        width: 500,
        height:350,
        minWidth: 480,
        minHeight: 320,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: datosAgrupadorForm,

        buttons: [{
            text: 'Actualizar', 
            handler: function() {
                if (datosAgrupadorForm.form.isValid()) {
                    
                    datosAgrupadorForm.form.submit({
                        waitMsg:'Procesando...',
                        failure: function(form, action) {
                            Ext.MessageBox.alert('Estado','Error editando Agrupador');
                        },
                        success: function(form, action) {
                            Ext.MessageBox.alert('Agrupador Editado', action.result.info);
                            windowAgrupadores.hide();
                            storeGridAgrupadores.load();
                        }
                    });                   
                } else{
                    Ext.MessageBox.alert('Informacion incompleta', 'Por favor verifique');
                }             
            }
        },{
            text: 'Regresar',
            handler: function(){windowAgrupadores.hide();}
        }]
    });
/////////////////////////////////////////////////////////////////////////////    

//GRID AGRUPADORES 
var storeGridAgrupadores = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: 'flujoendoso/consultaAgrupador.action'+'?cdUnieco='+idAseguradora.getValue()+'&cdRamo='+idProducto.getValue()+'&estado=W&nmPoliza='+idPoliza.getValue()+'&nmSituac=1'
    }),
    reader: new Ext.data.JsonReader({
    root: 'listMpoliagr'
    },[
       {name: 'dsNombre', type: 'string', mapping:'dsNombre'},
       {name: 'dsBanco', type: 'string', mapping:'dsBanco'},
       {name: 'codigoBanco', type: 'string', mapping:'cdBanco'},
       {name: 'codigoSucursal', type: 'string', mapping:'cdSucursal'},
       {name: 'codigoTipoTarjeta', type: 'string', mapping:'dsTipotarj'},
       {name: 'numeroCuenta', type: 'string', mapping:'nmcuenta'},
       {name: 'codigoPersonaUsuario', type: 'string', mapping:'cdPerson'},
       {name: 'dsForpag', type: 'string', mapping:'dsForpag'},
       {name: 'codigoInstrumentoPago', type: 'string', mapping:'cdForpag'},
       {name: 'codigoDomicilio', type: 'string', mapping:'dsDomicilio'},
       {name: 'cdUnieco', type: 'string', mapping:'cdUnieco'},
       {name: 'cdRamo', type: 'string', mapping:'cdRamo'},
       {name: 'estado', type: 'string', mapping:'estado'},
       {name: 'nmPoliza', type: 'string', mapping:'nmPoliza'},
       {name: 'nmSuplem', type: 'string', mapping:'nmSuplem'},
       {name: 'cdAgrupa', type: 'string', mapping:'cdAgrupa'},
       {name: 'numeroTarjeta', type: 'string', mapping:'numeroTarjeta'},
       {name: 'fechaVencimiento', type: 'string', mapping:'fechaVencimiento'},
       {name: 'digitoVerificador', type: 'string', mapping:'digitoVerificador'},
       {name: 'descripcionPersonaUsuario', type: 'string', mapping:'descripcionPersonaUsuario'},
       {name: 'descripcionDomicilio', type: 'string', mapping:'descripcionDomicilio'}
     ]),
    remoteSort: true
});

storeGridAgrupadores.load();

var cm1 = new Ext.grid.ColumnModel([
    new Ext.grid.RowNumberer(),
        {header: "nmCuenta",    dataIndex:'numeroCuenta',   width:120,  sortable:true, hidden:true},
        {header: "Nombre",    dataIndex:'dsNombre',   width:120,  sortable:true},
        {header: "Banco",     dataIndex:'dsBanco',    width:120,  sortable:true},
        {header: "Instrumento de Pago",    dataIndex:'dsForpag',   width:120,  sortable:true},
        {header: "Domicilio",  dataIndex:'codigoDomicilio',    width:120,  sortable:true}
]);

var gridAgrupadores = new Ext.grid.GridPanel({
        store: storeGridAgrupadores,
        border: true,
        //collapsible: true, 
        buttonAlign: 'center',
        baseCls:' background:white ',
        cm: cm1,
        buttons:[{
                    id:'editar',
                    text:'Editar',
                    tooltip:'Editar'                           
                }],                                                     
        width:660,
        frame:true,
        height:200,      
        //title:'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol en la Poliza</span>',
        
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
                    rowselect: function(sm, row, rec){ 
                            selectedId = storeGridAgrupadores.data.items[row].id;                                                                                                                                    
                            Ext.getCmp('editar').on('click',function(){
                                    windowAgrupadores.show();
                                    Ext.getCmp('datosAgrupadorForm').getForm().loadRecord(rec);                                                                            
                                });  
                    }
                }//listeners
        
        }),
        viewConfig: {autoFill: true,forceFit:true},                
        bbar: new Ext.PagingToolbar({
            pageSize:20,
            store: storeGridAgrupadores,                                               
            displayInfo: true,
            displayMsg: 'Displaying rows {0} - {1} of {2}',
            emptyMsg: "No rows to display"                      
            })                                                                                               
        });

//GRID ROL DE LA POLIZA 
var storeGridFnPoliza = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: 'flujoendoso/consultaFuncionPoliza.action'
    }),
    reader: new Ext.data.JsonReader({
    root: 'fnPoliza'
    },[
       {name: 'rol', type: 'string', mapping:'rol'},
       {name: 'nombre', type: 'string', mapping:'nombre'},
       {name: 'cdRol', type: 'string', mapping:'cdRol'},
       {name: 'nmSituac', type: 'string', mapping:'nmSituac'}
     ]),
    remoteSort: true
});

storeGridFnPoliza.load();

var cm2 = new Ext.grid.ColumnModel([
    new Ext.grid.RowNumberer(),
        {header: "Rol",    dataIndex:'rol',   width:120,  sortable:true},
        {header: "Nombre",     dataIndex:'nombre',    width:120,  sortable:true},
        {header: "cdRol",      dataIndex:'cdRol',       width:120,  sortable:true, hidden: true},
        {header: "nmSituac",   dataIndex:'nmSituac',    width:120,  sortable:true, hidden: true}
]);

var nmSituac;

var gridFnPoliza = new Ext.grid.GridPanel({
        store: storeGridFnPoliza,
        border: true,
        //collapsible: true, 
        buttonAlign: 'center',
        baseCls:' background:white ',
        cm: cm2,
        buttons:[{
                    id:'fnPolizaAgregar',
                    text:'Agregar',
                    tooltip:'Agregar'                           
                },{
                    id:'fnPolizaEditar',
                    text:'Editar',
                    tooltip:'Editar'                           
                },{
                    id:'fnPolizaBorrar',
                    text:'Eliminar',
                    tooltip:'Eliminar'                           
                }],                                                     
        width:660,
        frame:true,
        height:200,      
        //title:'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol en la Poliza</span>',
        
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
                    rowselect: function(sm, row, rec){ 
                            //selectedId = store.data.items[row].id;
                            nmSituac = rec.get('nmSituac');
                            //var cdRol = rec.get('rol');
                            //var dsNombre = rec.get('nombre');
                            //var dsRol = rec.get('rol');
                    }
                }//listeners
        
        }),
        viewConfig: {autoFill: true,forceFit:true},                
        bbar: new Ext.PagingToolbar({
            pageSize:20,
            store: storeGridFnPoliza,                                               
            displayInfo: true,
            displayMsg: 'Displaying rows {0} - {1} of {2}',
            emptyMsg: "No rows to display"                      
            })                                                                                               
        });

        Ext.getCmp('fnPolizaEditar').on('click',function(){
            datosFnPolizaEditar(nmSituac);                                                                            
        });
        
        Ext.getCmp('fnPolizaAgregar').on('click',function(){
            datosRolesPolizaAgregar();                                                                            
        });
        
        Ext.getCmp('fnPolizaBorrar').on('click',function(){
            datosFnPolizaBorrar(nmSituac);                                                                            
        });

                            
//******************ACCIONES GRID ROLES POLIZA******************
//AGREGAR
function datosRolesPolizaAgregar(){
    var conn = new Ext.data.Connection();
    var codeExt;
    
	var storeRol = new Ext.data.Store({
        	proxy: new Ext.data.HttpProxy({
            url: 'flujoendoso/getComboRol.action?cdTipsit='+idTipSit.getValue()
        }),
        reader: new Ext.data.JsonReader({
            root: 'datosRol',
            id: 'label'
        },[
                {name: 'label', type: 'string', mapping:'label'},
                {name: 'value', type: 'string', mapping:'value'}
        ]),
        remoteSort: true
    });
    storeRol.load();
    
    var storePersona = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
            url: 'flujoendoso/getComboPersona.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'datosPersona',
            id: 'label'
        },[
                {name: 'label', type: 'string', mapping:'label'},
                {name: 'value', type: 'string', mapping:'value'}
        ]),
        remoteSort: true
    });
    storePersona.load();

    var comboRol =new Ext.form.ComboBox({
            tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
            store: storeRol,
            displayField:'label',
            valueField:'value', 
            editable: true, 
            typeAhead: true,
            listWidth:'150',        
            mode: 'local',
            triggerAction: 'all',
            emptyText:'Seleccione...',
            selectOnFocus:true,
            fieldLabel: 'Rol',
            name:'comboRol',
            hiddenName:'comboRol',
            onSelect : function(record, index, skipCollapse){
                var valor = record.get('value');
                conn.request({
                     url: 'flujoendoso/getPantallaRolPoliza.action?cdUnieco=' + idAseguradora.getValue() + '&cdRamo=' + idProducto.getValue() + '&estado=' + idEstado.getValue() + '&nmPoliza=' + idPoliza.getValue() + '&nmSituac='+idNmSituac.getValue()+'&cdRol='+valor,
                     method: 'POST',
                     successProperty : '@success',
                     callback: function (options, success, response){
                                 if(Ext.util.JSON.decode(response.responseText).success == false){
                                     Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                                 }else{
                                     codeExt = Ext.util.JSON.decode(response.responseText).dext;
                                 }//else
                    }//function
                });
            }
    });
    
    var comboPersona =new Ext.form.ComboBox({
            tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
            store: storePersona,
            displayField:'label',
            valueField:'value', 
            editable: true, 
            typeAhead: true,
            listWidth:'150',        
            mode: 'local',
            triggerAction: 'all',
            emptyText:'Seleccione...',
            selectOnFocus:true,
            fieldLabel: 'Persona',
            name:'comboPersona',
            hiddenName:'comboPersona',
    });

    if(codeExt == ''){
            var sinDatos = new Ext.form.Hidden({
               name:'cdRamo',
               value:_cdramo
            });
            codeExt = sinDatos;
    }
     var panelRol = new Ext.form.FormPanel({
            url:'flujoendoso/agregarAgrupador.action',
            border: false,
            width: 600,
            autoHeight: true,
            items:[{
                //title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol de la P&oacute;liza</span>',
                labelWidth: 250, 
                labelAlign: 'right',         
                layout: 'form',
                frame: true,
                items:[comboRol,comboPersona]
            },{
                //title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol de la P&oacute;liza</span>',
                labelWidth: 250, 
                labelAlign: 'right',         
                layout: 'form',
                frame: true,
                items:codeExt            
            }]
     });
     
     var window = new Ext.Window({
        title: 'Agregar rol en póliza',
        width: 500,
        //height:340,
        autoHeight: true,
        minWidth: 300,
        minHeight: 200,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: [panelRol],
        buttons:[{
                text: 'Guardar', 
                handler: function() {                
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit({               
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                    Ext.MessageBox.alert('Error', 'No se pudo agregar la opcion');
                                    window.hide();
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Opcion Creada', 'Se agrego la opcion');
                                window.hide();
                            }
                        });                   
                    } else{
                        Ext.MessageBox.alert('Error', 'Por favor revise los errores!');
                          }             
                    }
                },{
                text: 'Regresar',
                tooltip: 'Cierra la ventana',
                handler: function(){
                    window.close();
                }//handler
        }]
     });
    
    window.show();
                   
}

//EDITAR
function datosRolesPolizaEditar(cdRol, dsRol, dsNombre){
    var conn = new Ext.data.Connection();
    conn.request({
         url: 'procesoemision/datosRol.action'+'?cdUnieco=' + cdUnieco.getValue() + '&cdRamo=' + cdRamo.getValue() + '&estado=' + estado.getValue() + '&nmPoliza=' + nmPoliza.getValue() + '&cdRol=' + cdRol + '&dsRol=' + dsRol + '&dsNombre=' + dsNombre + '&nmSituac='+nmSituac,
         method: 'POST',
         successProperty : '@success',
         callback: function (options, success, response){
                     if (Ext.util.JSON.decode(response.responseText).success == false) {
                         Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                     }else{
                         var codeExt = Ext.util.JSON.decode(response.responseText).dext;
                         
                         if(codeExt != ''){
                             var panelRol = new Ext.form.FormPanel({
                                    url:'flujoendoso/editarAgrupador.action',
                                    border: false,
                                    width: 600,
                                    autoHeight: true,
                                    items:[{
                                        //title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol de la P&oacute;liza</span>',
                                        labelWidth: 250, 
                                        labelAlign: 'right',         
                                        layout: 'form',
                                        frame: true,
                                        items:codeExt
                                   }]
                              });
                             
                             var window = new Ext.Window({
                                title: 'Editar rol en póliza',
                                width: 500,
                                //height:340,
                                autoHeight: true,
                                minWidth: 300,
                                minHeight: 200,
                                layout: 'fit',
                                plain:true,
                                modal:true,
                                bodyStyle:'padding:5px;',
                                buttonAlign:'center',
                                items: [panelRol],
                                buttons:[{
                                        text: 'Guardar', 
                                        handler: function(){
                                            if (formPanel.form.isValid()) {
                                                formPanel.form.submit({
                                                    waitMsg:'Procesando...',
                                                    failure: function(form, action) {
                                                        Ext.MessageBox.alert('Error', 'No se pudo editar la opción');
                                                        window.hide();
                                                    },
                                                    success: function(form, action) {
                                                        Ext.MessageBox.alert('Opcion Creada', 'Se editó la opción');
                                                        window.hide();
                                                    }
                                                });
                                            }else{
                                                Ext.MessageBox.alert('Error', 'Por favor revise los errores!');
                                            }
                                                 }
                                        },{
                                        text: 'Regresar',
                                        tooltip: 'Cierra la ventana',
                                        handler: function(){
                                            window.close();
                                        }//handler
                                }]
                             });
                             window.show();
                         }
                         else{
                            Ext.Msg.alert('Mensaje', 'No se encontraron datos');
                         }
                      }//else
                   }//function
    });
}
//BORRAR
function datosRolesPolizaBorrar(){
    var conn = new Ext.data.Connection();
    conn.request({
         url: 'procesoemision/datosRol.action'+'?cdUnieco=' + cdUnieco.getValue() + '&cdRamo=' + cdRamo.getValue() + '&estado=' + estado.getValue() + '&nmPoliza=' + nmPoliza.getValue() + '&cdRol=' + cdRol + '&dsRol=' + dsRol + '&dsNombre=' + dsNombre + '&nmSituac='+nmSituac,
         method: 'POST',
         successProperty : '@success',
         callback: function (options, success, response){
                     if (Ext.util.JSON.decode(response.responseText).success == false) {
                         Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                     }else{
                         var codeExt = Ext.util.JSON.decode(response.responseText).dext;
                         
                         if(codeExt != ''){
                            var msgBorrar = new Ext.form.TextField({
                                fieldLabel:'¿Esta seguro que desea eliminar la opción?',
                                labelSeparator:'',
                                anchor:'90%',
                                hidden:true
                            });
                            
                             var panelRol = new Ext.form.FormPanel({
                                    url:'flujoendoso/borrarAgrupador.action',
                                    border: false,
                                    width: 600,
                                    autoHeight: true,
                                    items:[{
                                            labelWidth: 250, 
                                            labelAlign: 'right',         
                                            layout: 'form',
                                            frame: true,
                                            items:msgBorrar
                                          },{
                                            labelWidth: 250, 
                                            labelAlign: 'right',         
                                            layout: 'form',
                                            frame: true,
                                            hidden:true,
                                            items:codeExt
                                          }]
                             });
                             
                             var window = new Ext.Window({
                                title: 'Eliminar rol en póliza',
                                width: 500,
                                //height:340,
                                autoHeight: true,
                                minWidth: 300,
                                minHeight: 200,
                                layout: 'fit',
                                plain:true,
                                modal:true,
                                bodyStyle:'padding:5px;',
                                buttonAlign:'center',
                                items: [panelRol],
                                buttons:[{
                                        text: 'Eliminar', 
                                        handler: function() {
                                            if (formPanel.form.isValid()) {
                                                formPanel.form.submit({
                                                    waitMsg:'Procesando...',
                                                    failure: function(form, action) {
                                                        Ext.MessageBox.alert('Error', 'No se pudo eliminar la opción');
                                                        window.hide();
                                                    },
                                                    success: function(form, action) {
                                                        Ext.MessageBox.alert('Opcion Creada', 'Se eliminó la opción');
                                                        window.hide();
                                                    }
                                                });
                                            }else{
                                                Ext.MessageBox.alert('Error', 'Por favor revise los errores!');
                                            }             
                                                 }
                                        },{
                                        text: 'Cancelar',
                                        tooltip: 'Cierra la ventana',
                                        handler: function(){
                                            window.close();
                                        }//handler
                                }]
                             });                            
                            window.show();
                         }
                         else{
                            Ext.Msg.alert('Mensaje', 'No se encontraron datos');
                         }
                      }//else
                   }//function
    });
}

//GRID ROL DE LA POLIZA 
var storeGridFnPoliza = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: 'flujoendoso/consultaFuncionPoliza.action'
    }),
    reader: new Ext.data.JsonReader({
    root: 'fnPoliza'
    },[
       {name: 'rol', type: 'string', mapping:'rol'},
       {name: 'nombre', type: 'string', mapping:'nombre'},
       {name: 'cdRol', type: 'string', mapping:'cdRol'},
       {name: 'nmSituac', type: 'string', mapping:'nmSituac'}
     ]),
    remoteSort: true
});

storeGridFnPoliza.load();

var cm2 = new Ext.grid.ColumnModel([
    new Ext.grid.RowNumberer(),
        {header: "Rol",    dataIndex:'rol',   width:120,  sortable:true},
        {header: "Nombre",     dataIndex:'nombre',    width:120,  sortable:true},
        {header: "cdRol",      dataIndex:'cdRol',       width:120,  sortable:true, hidden: true},
        {header: "nmSituac",   dataIndex:'nmSituac',    width:120,  sortable:true, hidden: true}
]);

var gridFnPoliza = new Ext.grid.GridPanel({
        store: storeGridFnPoliza,
        border: true,
        //collapsible: true, 
        buttonAlign: 'center',
        baseCls:' background:white ',
        cm: cm2,
        buttons:[{
                    id:'fnPolizaAgregar',
                    text:'Agregar',
                    tooltip:'Agregar'                           
                },{
                    id:'fnPolizaEditar',
                    text:'Editar',
                    tooltip:'Editar'                           
                },{
                    id:'fnPolizaBorrar',
                    text:'Eliminar',
                    tooltip:'Eliminar'                           
                }],                                                     
        width:660,
        frame:true,
        height:200,      
        //title:'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol en la Poliza</span>',
        
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
                    rowselect: function(sm, row, rec){ 
                            //selectedId = store.data.items[row].id;
                            var nmSituac = rec.get('nmSituac');
                            var cdRol = rec.get('cdRol');
                            var dsNombre = rec.get('nombre');
                            var dsRol = rec.get('rol');
                            
                            Ext.getCmp('fnPolizaEditar').on('click',function(){
                                datosFnPolizaEditar(cdRol, dsRol, nombre);                                                                            
                            });
                            
                            Ext.getCmp('fnPolizaAgregar').on('click',function(){
                                datosFnPolizaAgregar();                                                                            
                            });
                            
                            Ext.getCmp('fnPolizaBorrar').on('click',function(){
                                datosFnPolizaBorrar();                                                                            
                            });
                    }
                }//listeners
        
        }),
        viewConfig: {autoFill: true,forceFit:true},                
        bbar: new Ext.PagingToolbar({
            pageSize:20,
            store: storeGridFnPoliza,                                               
            displayInfo: true,
            displayMsg: 'Displaying rows {0} - {1} of {2}',
            emptyMsg: "No rows to display"                      
            })                                                                                               
        });
        

//******************DETALLE ACCESORIOS******************
function detalleAccesorios(tipo, monto){

    var conn = new Ext.data.Connection();
    conn.request({
         url: 'flujoendoso/datosAccesorios.action?cdUnieco='+idAseguradora.getValue()+'&cdRamo='+idProducto.getValue()+'&estado='+idEstado.getValue()+'&nmPoliza='+idPoliza.getValue()+'&nmSituac='+idNmSituac.getValue()+'&nmObjeto=',
         method: 'POST',
         successProperty : '@success',
         callback: function (options, success, response){
                     if (Ext.util.JSON.decode(response.responseText).success == false) {
                         Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                     }else{
                         var codeExt = Ext.util.JSON.decode(response.responseText).dext;
                         if(codeExt != ''){
                            var campoIncis = new Ext.form.TextField({                              
                                name:'inciso',
                                value:idNmSituac.getValue(),
                                fieldLabel: 'Inciso',
                                disabled:true,
                                width: 200
                            });
							var campoTipo = new Ext.form.TextField({
								name:'tipo',
								value:tipo,
								fieldLabel:'Tipo',
								//disabled:true,
								width: 200
							});
	                     	var campoMonto = new Ext.form.TextField({
								name:'monto',
								value:monto,
								fieldLabel:'Monto',
								//disabled:true,
								width: 200
	                     	});
                            var panelDetalleAccesoriosEnc = new Ext.FormPanel({
						            labelAlign:'right',
						            border: false,
						            width: 350,
						            autoHeight:true,
						            //title:'Encabezado',
                                    items:[
			                             	campoTipo,
			                              	campoMonto
			                        ]
                            });
                            var panelDetalleAccesorios = new Ext.FormPanel({
						            labelAlign:'right',
						            border: false,
						            width: 350,
						            autoHeight:true,
						            //title: 'Datos',
                                    items: codeExt
                            });
                             
                            var windowAccesorios = new Ext.Window({
								title: 'Datos Accesorios',
								width: 350,
								autoHeight: true,
								layout: 'fit',
								plain:true,
								modal:true,
								bodyStyle:'padding:5px;',
								buttonAlign:'center',
								items: [panelDetalleAccesoriosEnc,panelDetalleAccesorios],
								buttons:[{
                                    text: 'Guardar',
                                    tooltip: 'Guardar cambios',
                                        handler: function(){
                                            windowAccesorios.close();
                                        }
                                    },{
								    text: 'Regresar',
								    tooltip: 'Cierra la ventana',
    								    handler: function(){
    								        windowAccesorios.close();
    								    }//handler
								}]
                            });
                            
                            windowAccesorios.show();
                         }else{
                            Ext.Msg.alert('Mensaje', 'No se encontraron datos');
                         }
                      }//else
                   }//function
    });
}


function borrarAccesorios(cdGarant){
    alert('cdGarant : ' + cdGarant);
}

//ACCESORIOS
var storeAccesoriosGrid = createGridAccesorios();
var cm3 = new Ext.grid.ColumnModel([
    new Ext.grid.RowNumberer(),
    {header: "Tipo", dataIndex:'label', width: 120, sortable:true},
  	{header: "Costo", dataIndex:'value', width: 120, sortable:true}
]);
var selectedId;

var gridAccesorios = new Ext.grid.GridPanel({
    store:storeAccesoriosGrid,
    border:true,
    baseCls:' background:white ',
    cm: cm3,
    buttonAlign: 'center',
	buttons:[{
                id:'accesoriosDetalles',
                text:'Agregar',
                tooltip:'Agregar'
            },{
				id:'accesoriosDetalles',
				text:'Editar',
            	tooltip:'Editar'
            },{
                id:'borrarAccesorios',
                text:'Eliminar',
                tooltip:'Eliminar Accesorios'
            }],                                 
    width:660,
    frame:true,
    height:200,
    
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
                        rowselect: function(sm, row, rec) { 
                                selectedId = storeAccesoriosGrid.data.items[row].id;                                                                                                                                    
                                Ext.getCmp('accesoriosDetalles').on('click',function(){
                                    detalleAccesorios();                                                                          
                                });
                                Ext.getCmp('borrarAccesorios').on('click',function(){
                                    borrarAccesorios();                                                                          
                                });
                        }
                }
        
        }),
    viewConfig: {autoFill: true,forceFit:true}, 
    bbar: new Ext.PagingToolbar({
        pageSize:20,
        store: storeAccesoriosGrid,                                               
        displayInfo: true,
        displayMsg: 'Displaying rows {0} - {1} of {2}',
        emptyMsg: "No rows to display"                      
        })
    });

//PANEL GRID COBERTURA
var coberturaPanelGrid = new Ext.Panel({
    border: false,
    width: 670,
    autoHeight: true,
    autoScroll: true,
    items:[{
        title :'<span style="color:#98012e;font-size:14px;">Coberturas</span>',
        layout: 'form',
        collapsible: true,
        frame: true,
        items:[gridCoberturas]
   }]
});

//PANEL GRID ROL DE LA POLIZA
var panelFnPolizaPanelGrid = new Ext.Panel({
    border: false,
    width: 670,
    //autoHeight: true,
    //frame: true,
    items:[{
        title :'<span style="color:#98012e;font-size:14px;">Rol de la P&oacute;liza</span>',
        //bodyStyle:'background: white',
        //labelWidth: 100,             
        layout: 'form',
        collapsible: true,
        frame: true,
        //bodyStyle:'padding:5px 5px 0',
        items:[gridFnPoliza]
   }]
});

//PANEL GRID AGRUPADORES
var panelAgrupadoresPanelGrid = new Ext.Panel({
    border: false,
    width: 670,
    //autoHeight: true,
    //frame: true,
    items:[{
        title :'<span style="color:#98012e;font-size:14px;">Datos de Cobro</span>',
        //bodyStyle:'background: white',
        //labelWidth: 100,             
        layout: 'form',
        collapsible: true,
        frame: true,
        //bodyStyle:'padding:5px 5px 0',
        items:[gridAgrupadores]
   }]
});

//PANEL GRID ACCESORIOS
var accesoriosPanelGrid = new Ext.Panel({
    border: false,
    width: 670,
    autoHeight: true,
    autoScroll: true,
    items:[{
        title :'<span style="color:#98012e;font-size:14px;">Accesorios</span>',
        layout: 'form',
        collapsible: true,
        frame: true,
        items:[gridAccesorios]
   }]
});

//PANEL GRID MAIN
var mainPanelGrid = new Ext.Panel({
    border: false,
    width: 680,
    autoHeight: true,
    buttonAlign:'left',
    items:[{
        title :'<span style="color:black;font-size:14px;">Inciso</span>',
        layout: 'form',
        //collapsible: true,
        frame: true,
        items:[datosIncisosForm,panelAgrupadoresPanelGrid,coberturaPanelGrid,panelFnPolizaPanelGrid,accesoriosPanelGrid],
        buttons:[{
				text:'Regresar',
            	tooltip:'Regresar a detalles poliza'
        }]
   }]
});
mainPanelGrid.render('pantallaInciso');
});

</script>