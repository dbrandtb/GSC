Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

    var cm = new Ext.grid.ColumnModel([
        {       
        header: "cdPerson",
        dataIndex: 'cdPerson',
        hidden : true,
        width: 120
        },{
        header: getLabelFromMap('desPolCmClie',helpMap,'Cliente'),
        tooltip: getToolTipFromMap('desPolCmClie',helpMap,'Cliente'),
        dataIndex: 'dsCliente',
        width: 150,
        sortable: true
        },{
        header: "cdTipCon",
        dataIndex: 'cdTipCon',
        hidden : true,
        width: 120
        },{
        header: getLabelFromMap('desPolCmCon',helpMap,'Concepto'),
        tooltip: getToolTipFromMap('desPolCmCon',helpMap,'Concepto'),
        dataIndex: 'dsTipCon',
        width: 165,
        sortable: true
        },{
        header: "cdRamo",
        dataIndex: 'cdRamo',
        hidden : true,
        width: 1
        },{
        header: getLabelFromMap('desPolCmPro',helpMap,'Producto'),
        tooltip: getToolTipFromMap('desPolCmPro',helpMap, 'Producto'),
        dataIndex: 'dsRamo',
        width: 165,
        sortable: true
    }]);


 function test(){

                store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_DESGLOSE_POLIZAS
                }),

                
                reader: new Ext.data.JsonReader({
            	root:'MDesglosePolizasList',
            	totalProperty: 'totalCount',
	            successProperty : '@success'
	        },[
	        {name: 'cdPerson',  type: 'string',  mapping:'cdPerson'},
	        {name: 'cdTipCon',  type: 'string',  mapping:'cdTipCon'},
	        {name: 'cdRamo',  type: 'string',  mapping:'cdRamo'},
	        {name: 'dsRamo',  type: 'string',  mapping:'dsRamo'},
	        {name: 'dsCliente',  type: 'string',  mapping:'dsCliente'},
	        {name: 'dsTipCon',  type: 'string',  mapping:'dsTipCon'}
			]) 				
        });
       return store;
 	}


    var grid2;



function createGrid(){
	grid2= new Ext.grid.GridPanel({
		id: 'grid2',
        el:'gridDesgloses',
        title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
        store:test(),
		border:true,
		buttonAlign:'center',
		bodyStyle:'background: white',
        cm: cm,
        loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
		buttons:[
        		{
        		text:getLabelFromMap('desPolBtnAdd',helpMap,'Agregar'),
            	tooltip: getToolTipFromMap('desPolBtnAdd',helpMap,'Agrega desglose de conceptos'),
            	handler:function(){
                agregar();
                }
            	},
            	//Editar Removido por pedido de Herbe 07/05/2008 03:19 AM
            	{
            	text:getLabelFromMap('desPolBtnDel',helpMap,'Eliminar'),
            	tooltip: getToolTipFromMap('desPolBtnDel',helpMap,'Elimina desglose de conceptos'),
                handler:function(){
                    if (getSelectedRecord(grid2)!= null) {
                        borrar(getSelectedRecord(grid2));
                       
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe Seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
                }
            	
            	},{
            	text:getLabelFromMap('desPolBtnCopy',helpMap,'Copiar'),
            	tooltip: getToolTipFromMap('desPolBtnCopy',helpMap,'Copia desglose de conceptos'),
            	handler:function(){
                     if (getSelectedRecord(grid2)!= null) {
                        copiar(getSelectedRecord(grid2));
                       
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe Seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
            		}
               },{
               	text:getLabelFromMap('desPolBtnExp',helpMap,'Exportar'),
            	tooltip: getToolTipFromMap('desPolBtnExp',helpMap,'Exporta el contenido del grid'),
            	handler:function(){
                        var url = _ACTION_EXPORT + '?dsCliente=' +Ext.getCmp('dsClienteId').getValue()+ '&dsConcepto=' +Ext.getCmp('dsConceptoId').getValue()+ '&dsProducto=' + Ext.getCmp('dsProductoId').getValue() + '&cdEstruct=' + Ext.getCmp('cdEstructId').getValue();
                	 	showExportDialog( url );
                        }
            	}/*,{
            	text:getLabelFromMap('desPolBtnBack',helpMap,'Regresar'),
            	tooltip: getToolTipFromMap('desPolBtnBack',helpMap)
                }*/
                ],
            	
            	
    	width:500,
    	frame:true,
		height:320,
		
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		stripeRows: true,
		collapsible: true,
		bbar: new Ext.PagingToolbar({
				pageSize: itemsPerPage,
				store: store,
				displayInfo: true,
				displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
		    })
		});

    grid2.render()
}




var dsCliente = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsClienteId',helpMap,'Cliente'),
        tooltip: getToolTipFromMap('dsClienteId',helpMap,'Cliente para Desglose de Conceptos'),
        hasHelpIcon:getHelpIconFromMap('dsClienteId',helpMap),
		Ayuda: getHelpTextFromMap('dsClienteId',helpMap),

        name:'dsCliente',
        id: 'dsClienteId',
         anchor: '95%'
    });


    var dsProducto = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsProductoId',helpMap,'Producto'),
        tooltip: getToolTipFromMap('dsProductoId',helpMap,'Producto para Desglose de Conceptos'),
        hasHelpIcon:getHelpIconFromMap('dsProductoId',helpMap),
		Ayuda: getHelpTextFromMap('dsProductoId',helpMap),
        name:'dsProducto',
        id: 'dsProductoId',
        anchor: '95%'
    });
    
    var dsConcepto = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsConceptoId',helpMap,'Concepto'),
        tooltip: getToolTipFromMap('dsConceptoId',helpMap,'Concepto'),
        hasHelpIcon:getHelpIconFromMap('dsConceptoId',helpMap),
		Ayuda: getHelpTextFromMap('dsConceptoId',helpMap),
        name:'dsConcepto',
        id: 'dsConceptoId',
         anchor: '95%'
    });
    

    var incisosForm = new Ext.form.FormPanel({
    	id: 'incisosForm',
        el:'formBusqueda',
		title: '<span style="color:black;font-size:12px;">'+ getLabelFromMap('incisosForm',helpMap,'Desglose de Conceptos')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        url:_ACTION_BUSCAR_DESGLOSE_POLIZAS,
        width: 500,
        autoHeight: true,
        items: [{
        		layout:'form',
				border: false,
				items:[{
        		bodyStyle:'background: white',
		        labelWidth: 100,
                layout: 'form',
				frame:true,
		       	baseCls: '',
		       	buttonAlign: "center",
        		        items: [{
        		        	layout:'column',
		 				    border:false,
		 				    columnWidth: 1,
		 				    html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
        		    		items:[{
						    	columnWidth:.6,
                				layout: 'form',
		                		border: false,
        		        		items:[
        		        				{xtype: 'hidden', name: 'cdEstruct', value: '', id: 'cdEstructId'},
        		        				dsCliente,
        		        				dsProducto,
        		        				dsConcepto
		       						]
								},{
								columnWidth:.4,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
        							text:getLabelFromMap('desPolBtnFind',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('desPolBtnFind',helpMap,'Busca desglose de conceptos'),
        							handler: function() {
				               			if (incisosForm.form.isValid()) {
                                               if (grid2!=null) {
                                                reloadGrid();
                                               } else {
                                                createGrid();
                                               }
                						} else{
											Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
										}
									}
        							},{
        							text:getLabelFromMap('desPolBtnCan',helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('desPolBtnCan',helpMap,'Cancela la b&uacute;squeda'),
        							handler: function(){incisosForm.form.reset();}
        						}]
        					}]
        				}]
				});

		incisosForm.render();
        createGrid();

        function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
    	}

var store;

   function exportar(){
  	    
  	     Ext.MessageBox.alert('Aviso','La opción está en construcción');
   };
   
   
   function borrar(record) {
        if(record.get('cdPerson') != "" && record.get('cdTipCon') != "" && record.get('cdRamo') != "")
        {


            Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
            {
                if (btn == "yes")
                {
                	var _params = {
				              cdPerson: record.get('cdPerson'),
				              cdTipCon: record.get('cdTipCon'),
				              cdRamo: record.get('cdRamo')
				          }
					execConnection(_ACTION_BORRAR_DESGLOSE_POLIZAS, _params, cbkBorrar);                               
               }
            }
            )
        }else{
                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe Seleccionar un registro para realizar esta operacion'));
    	}
	};

  	function cbkBorrar(_success, _message) {
  		if (!_success) {
  			Ext.Msg.alert('Error', _message);
  		} else {
  			Ext.Msg.alert('Aviso', _message, function() {reloadGrid();});
  		}
  	}
             
        
// Funcion de Agregar Desglose de Polizas
function agregar() {

    var dsClientesCorp = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({
               url: _ACTION_OBTENER_CLIENTE_CORPO
           }),
           reader: new Ext.data.JsonReader({
           root: 'clientesCorp',
           id: 'clientesCorps'
           },[
           {name: 'cdElemento', type: 'string',mapping:'cdElemento'},
           {name: 'cdPerson2', type: 'string',mapping:'cdPerson'},
           {name: 'dsElemen', type: 'string',mapping:'dsElemen'}
       ])
       });


    var desProducto = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PRODUCTOS_AYUDA_CLIENTE
            }),
            reader: new Ext.data.JsonReader({
            root: 'productosComboBox',
            id: 'productos'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ])
    });



    var desConceptoProducto = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_CONCEPTOS_PRODUCTO
            }),
            reader: new Ext.data.JsonReader({
            root: 'conceptosProducto',
            id: 'codigo'
            },[
           {name: 'codTipoCon', type: 'string',mapping:'codigo'},
           {name: 'desTipoCon', type: 'string',mapping:'descripcion'}
        ])
    });


//se define el formulario
var formPanel = new Ext.FormPanel ({
            labelWidth : 100,
            bodyStyle : 'padding:5px 5px 0',
            width : 350,
            defaults : {width : 200 },
            waitMsgTarget : true,
            defaultType : 'textfield',
            //se definen los campos del formulario
            items : [
                {
                    xtype: 'hidden',
                    name : 'cdPerson',
                    id: 'cdPerson'
                },

                {
                    xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson2}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    store: dsClientesCorp,
                    displayField:'dsElemen',
                    valueField: 'cdElemento',
                    hiddenName: 'cdElemento',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    id:'desPolCmbCli',
                    fieldLabel: getLabelFromMap('desPolCmbCli',helpMap,'Cliente'),
                    tooltip: getToolTipFromMap('desPolCmbCli',helpMap,'Cliente'),
                    hasHelpIcon:getHelpIconFromMap('desPolCmbCli',helpMap),
					Ayuda: getHelpTextFromMap('desPolCmbCli',helpMap),
					width: 300,
                    emptyText:'Seleccione Cliente ...',
                    selectOnFocus:true,
                    forceSelection:true,
                    id:'cdClienteId',
                    allowBlank : false,
                    onSelect: function (record) {
                        
                        desProducto.removeAll();
                        desConceptoProducto.removeAll();
                        Ext.getCmp("comboProducto").clearValue();
                        Ext.getCmp("comboConcepto").clearValue();
                        
                    	this.setValue(record.get("cdElemento"));
                        formPanel.findById(('cdPerson')).setValue(record.get("cdPerson2"));
                        
                        desProducto.load({
		                       			params:{cdElemento: Ext.getCmp("cdClienteId").getValue()}
		                       		});
                        
                        this.collapse();
                            }
                },

                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: desProducto,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'cdRamo',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    id:'desPolCmbPro',
                    fieldLabel: getLabelFromMap('desPolCmbPro',helpMap,'Producto'),
                    tooltip: getToolTipFromMap('desPolCmbPro',helpMap,'Producto'),
                    hasHelpIcon:getHelpIconFromMap('desPolCmbPro',helpMap),
					Ayuda: getHelpTextFromMap('desPolCmbPro',helpMap),
                    width: 300,
                    emptyText:'Seleccione Producto...',
                    selectOnFocus:true,
                    allowBlank : false,
                    forceSelection:true,
                    id:'comboProducto',
                    onSelect: function (record) {
                        
                        desConceptoProducto.removeAll();
                        Ext.getCmp("comboConcepto").clearValue();
                    	this.setValue(record.get("codigo"));
                      
                        this.collapse();
                        
                        	desConceptoProducto.load({
		                       			params:{cdRamo: Ext.getCmp("comboProducto").getValue()}
		                       		});
                            
                            }
                } ,
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codTipoCon}. {desTipoCon}" class="x-combo-list-item">{desTipoCon}</div></tpl>',
                    store: desConceptoProducto,
                    displayField:'desTipoCon',
                    valueField:'codTipoCon',
                    hiddenName: 'cdTipCon',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    id:'desPolCmbCon',
                    fieldLabel: getLabelFromMap('desPolCmbCon',helpMap,'Concepto'),
                    tooltip: getToolTipFromMap('desPolCmbCon',helpMap,'Concepto'),
                    hasHelpIcon:getHelpIconFromMap('desPolCmbCon',helpMap),
					Ayuda: getHelpTextFromMap('desPolCmbCon',helpMap),
                    width: 300,
                    emptyText:'Seleccione Concepto...',
                    allowBlank : false,
                    selectOnFocus:true,
                    forceSelection:true,
                    id:'comboConcepto'
                }


            ]
        });


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        //title: getLabelFromMap('81',helpMap,'Agregar Desglose de Conceptos'),
        title: getLabelFromMap('wndwAgregDesConcId', helpMap,'Agregar Desglose de Conceptos'),
        id:'wndwAgregDesConcId',
        width: 500,
        height:200,
        layout: 'fit',
        plain:true,
        modal: true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
            buttons : [ {
                text : getLabelFromMap('desPolBtnGuardarAgre',helpMap,'Guardar'),
                tooltip: getToolTipFromMap('desPolBtnGuardarAgre',helpMap,'Guarda conceptos'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            url : _ACTION_AGREGAR_DESGLOSE_POLIZAS,
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                                },
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),action.result.errorMessages[0]);
                                },
                           // waitTitle: getLabelFromMap('400017', helpMap,'Espere...'),
                            waitMsg :getLabelFromMap('400027', helpMap,'Guardando de datos...') 
                        });
                     } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                     }
                }
            },
             {
                 text : getLabelFromMap('desPolBtnCancelarAgre',helpMap,'Cancelar'),
                tooltip: getToolTipFromMap('desPolBtnCancelarAgre',helpMap,'Cancela conceptos' ),
                 handler : function() {
                 window.close();
                    }
            }]
    	});




    dsClientesCorp.load({
    	callback:function(){
    		desProducto.load({
    			callback:function(){
    				desConceptoProducto.load();
    			}
    		});
    	}
    });     



    window.show();

};



// Funcion de Copiar Desglose de Polizas
function copiar(record) {

      var dsClientesCorp = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({
               url: _ACTION_OBTENER_CLIENTE_CORPO
           }),
           reader: new Ext.data.JsonReader({
           root: 'clientesCorp',
           id: 'clientesCorps'
           },[
           {name: 'cdElemento', type: 'string',mapping:'cdElemento'},
           {name: 'cdPerson2', type: 'string',mapping:'cdPerson'},
           {name: 'dsElemen', type: 'string',mapping:'dsElemen'}
       ])
       });

       var desProducto = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PRODUCTOS_AYUDA_CLIENTE
            }),
            reader: new Ext.data.JsonReader({
            root: 'productosComboBox',
            id: 'productos'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ])
    });



    
		//se define el formulario
var formPanel = new Ext.FormPanel ({
		    frame : true,

       		bodyStyle:'background: white',
            width : 500,
            labelAlign: 'right',
            waitMsgTarget : true,
            defaults: {labelWidth: 70},
            layout: 'column',
            layoutConfig: {columns: 2},
            items : [
                 {
                   layout:'form',
                   columnWidth: .45,
                   items:[{
                     xtype: 'textfield',
                     fieldLabel: getLabelFromMap('desPolTexClieCop',helpMap,'Cliente'),
					 tooltip: getToolTipFromMap('desPolTexClieCop',helpMap,'Cliente Origen'),
					 hasHelpIcon:getHelpIconFromMap('desPolTexClieCop',helpMap),
		             Ayuda: getHelpTextFromMap('desPolTexClieCop',helpMap),
                     name: 'dsCliente', 
                     id: 'desPolTexClieCop',
                     width:'120',
                     allowBlank: false,
                     readOnly:true,
                     disabled:true
                   },{
                     xtype: 'hidden', 
                     fieldLabel: 'Cliente id', 
                     name: 'cdPerson', 
                     id: 'cdPerson',
                     allowBlank: false,
                     readOnly:true
                   },{
                     xtype: 'hidden', 
                     fieldLabel: 'producto id', 
                     name: 'cdRamo', 
                     id: 'cdRamo',
                     allowBlank: false,
                     readOnly:true
                   }
                   ]},
                   {
                   layout: 'form',
                   columnWidth: .55, 
                   items: [{
                       xtype: 'combo',
                       tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson2}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                       store: dsClientesCorp,
                       displayField:'dsElemen',
                       valueField: 'cdElemento',
                       hiddenName: 'dsPerson',
                       typeAhead: true,
                       mode: 'local',
                       triggerAction: 'all',
                       id:'desPolCmbClienCop',
                       fieldLabel: getLabelFromMap('desPolCmbClienCop',helpMap,'Cliente'),
					   tooltip: getToolTipFromMap('desPolCmbClienCop',helpMap,'Cliente'),
					   hasHelpIcon:getHelpIconFromMap('desPolCmbClienCop',helpMap),
		               Ayuda: getHelpTextFromMap('desPolCmbClienCop',helpMap),
                       width: 160,
                       emptyText:'Seleccione Cliente ...',
                       selectOnFocus:true,
                       forceSelection:true,
                       id:'cdClienteId',
                       onSelect: function (record) {
                                 desProducto.removeAll();
                                 Ext.getCmp("comboProducto").clearValue();
                           
                                   this.setValue(record.get("cdElemento"));
                                   //formPanel.findById("dsPerson").setValue(record.get("cdPerson2"));
                                   this.collapse();
                                     
                                 desProducto.load({
		                       			params:{cdElemento: Ext.getCmp("cdClienteId").getValue()}
		                       		});
                                
                       }
            		}]
   			     },{
                   layout:'form',
                   columnWidth: .45,
                   items:[{
                     xtype: 'textfield', 
                     fieldLabel: getLabelFromMap('desPolCmbProdCop',helpMap,'Producto'),
					 tooltip: getToolTipFromMap('desPolCmbProdCop',helpMap,'Producto origen'),
					 hasHelpIcon:getHelpIconFromMap('desPolCmbProdCop',helpMap),
		             Ayuda: getHelpTextFromMap('desPolCmbProdCop',helpMap),
                     name: 'dsProducto', 
                     id: 'desPolCmbProdCop',
                     width:'120',
                     allowBlank: false,       		
                     readOnly:true,
                     disabled:true                     
                   }]
                   },
                 
                   {
                   layout: 'form',
                   columnWidth: .55, 
                   items: [
                   {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: desProducto,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'dsRamo',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    id:'desPolCmbProd2Cop',
                    fieldLabel: getLabelFromMap('desPolCmbProd2Cop',helpMap,'Producto'),
					tooltip: getToolTipFromMap('desPolCmbProd2Cop',helpMap,'Producto'),
					hasHelpIcon:getHelpIconFromMap('desPolCmbProd2Cop',helpMap),
		            Ayuda: getHelpTextFromMap('desPolCmbProd2Cop',helpMap),
                    width: 160,
                    emptyText:'Seleccione Producto...',
                    selectOnFocus:true,
                    forceSelection:true,
                    id:'comboProducto'
                    
                   }
            		]}
            		                                                    
            ]});


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        	//title: getLabelFromMap('82',helpMap,'Copiar Desglose de P&oacute;lizas'),
			title: getLabelFromMap('wndwDesgPoliCopId', helpMap,'Copiar Desglose de P&oacute;lizas'),
			id:'wndwDesgPoliCopId',
            width: 500,
            modal: true,
            buttonAlign:'center',
        	items: formPanel,
            buttons : [ {
                text : getLabelFromMap('desPolBtnCopyCop',helpMap,'Copiar'),
				tooltip: getToolTipFromMap('desPolBtnCopyCop',helpMap,'Copiar p&oacute;lizas'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            url : _ACTION_COPIAR_DESGLOSE_POLIZAS,
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();                                     
                            },
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),action.result.errorMessages[0]);
                           },
                            waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
                            waitMsg : getLabelFromMap('400034',helpMap,'copiando datos ...')
                        });

                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                    }
                }
            }, {
                text : getLabelFromMap('desPolBtnCancelCop',helpMap,'Cancelar'),
				tooltip: getToolTipFromMap('desPolBtnCancelCop',helpMap,'Cancela p&oacute;lizas'),
                handler : function() {
                window.close();
                }
            }]
});


window.show();
 

    if (record != null && record.get('dsCliente') != "") {
		formPanel.findById('desPolTexClieCop').setValue(record.get('dsCliente'));
	}
	if (record != null && record.get('dsProducto') != "") {
		formPanel.findById('desPolCmbProdCop').setValue(record.get('dsRamo'));
	}
	if (record != null && record.get('cdPerson') != "") {
		formPanel.findById('cdPerson').setValue(record.get('cdPerson'));
	}
	if (record != null && record.get('cdRamo') != "") {
		formPanel.findById('cdRamo').setValue(record.get('cdRamo'));
	}
      
      
   dsClientesCorp.load({
   		callback:function(){
   			desProducto.load();
   		}
   });   
       

};

function reloadGrid(){
	var _params = {
    		dsCliente: dsCliente.getValue(),
    		dsProducto: dsProducto.getValue(),
    		dsConcepto: dsConcepto.getValue(),
    		cdEstruct: Ext.getCmp('cdEstructId').getValue()
	};
	reloadComponentStore(grid2, _params, cbkReload);
}
function cbkReload (_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}
});
