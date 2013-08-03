Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//VALORES DE INGRESO DE LA BUSQUEDA


var aseg_store = new Ext.data.Store ({
		proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_ASEGURADORA}),
		reader: new Ext.data.JsonReader({
		root: 'aseguradoraComboBox',
			//	id: 'cdUniEco',
		successProperty: '@success'
		}, [
			{name: 'codigo', type: 'string', mapping: 'codigo'},
			{name: 'descripcion', type: 'string', mapping: 'descripcion'} 
			])
									});
				
			
var dsNivelCorpo = new Ext.data.Store({
 	proxy: new Ext.data.HttpProxy({
    url: _ACTION_OBTENER_NIVEL_CORPO
 	}),
    reader: new Ext.data.JsonReader({
    root: 'comboClientesCorpBO',
        //id: 'cdElemento',
    successProperty: '@success'
    }, [
      	{name: 'cdElemento', type: 'string', mapping: 'cdElemento'},
        {name: 'cdPerson', type: 'string', mapping: 'cdPerson'},
        {name: 'dsElemen', type: 'string', mapping: 'dsElemen'} 
    ])
    
});			 			
				
var desProducto = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
            url: _ACTION_OBTENER_PRODUCTOS
            }),
            reader: new Ext.data.JsonReader({
            root: 'productosComboBox'
         //   id: 'cdRamo'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
      		  ])
  									 });		
   	
				
 var dsPago = new Ext.data.Store ({
			proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_ISTRUMENTO_PAGO}),
			reader: new Ext.data.JsonReader({
			root: 'comboProcesosCat',
		//	id: 'cdProceso',
			successProperty: '@success'
			}, [
				{name: 'cdProceso', type: 'string', mapping: 'cdProceso'},
				{name: 'dsProceso', type: 'string', mapping: 'dsProceso'}
				]),
			remoteSort: true
								});		

  
    
var formPanel = new Ext.FormPanel({
		id: 'formPanel',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formPanel',helpMap,'Atributos variables por instrumento de pago ')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
     url: _ACTION_BUSCAR_INSTRUMENTO_PAGO,
        width: 700,
        height:180,
        items: [{
        		layout:'form',
				border: false,
 				items:[
                {
        		bodyStyle:'background: white',
		        labelWidth: 130,
                layout: 'form',
                title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
				frame:true,
		       	baseCls: '',
		       	buttonAlign: "center",
        		        items: [{
        		        	layout:'column',
		 				    border:false,
		 				    columnWidth: 1,
        		    		items:[{
						    	columnWidth:.5,
                				layout: 'form',
		                		border: false,
        		        		items:[         				  {
                    xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson2}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    store: dsNivelCorpo,
                    displayField:'dsElemen',
                    valueField: 'cdElemento',
                    hiddenName: 'cdPerson',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cdClienteId',helpMap,'Nivel'),
        			tooltip:getToolTipFromMap('cdClienteId',helpMap,'Nivel'),
        			hasHelpIcon:getHelpIconFromMap('cdClienteId',helpMap,'HELP'),
            		Ayuda: getHelpTextFromMap('cdClienteId',helpMap,'HELP'),
                    //fieldLabel: "Cliente",
                    forceSelection: true,
                     //anchor:'100%',
                     width:185,
                    emptyText:'Seleccione Nivel ...',
                    selectOnFocus:true,
                  //  allowBlank: false,
                    mode:'local',
                    //labelSeparator:'',
                    id:'cdClienteId',
                    onSelect: function (record) {
                    	          this.setValue(record.get("cdElemento"));
                                  formPanel.findById(('cdClienteId')).setValue(record.get("cdElemento"));
                                  
                                   
                                           				formPanel.findById(('cdUniEcoId')).setValue("");
                                          				aseg_store.removeAll();
                                          				
                                          				aseg_store.load({
	                            											params: {cdElemento: record.get('cdElemento')},
	                            											failure: aseg_store.removeAll()
	                            										});
	                            										
	                            						formPanel.findById(('cdRamoId')).setValue("");				
	                            					    desProducto.removeAll();
	                            					    desProducto.load({
	                            											params: {cdunieco: formPanel.findById(('cdUniEcoId')).getValue(),
	                            											         cdElemento: record.get('cdElemento'),
	                            											         cdRamo: ""
	                            											         },
	                            											failure: aseg_store.removeAll()
	                            										});
	                              this.collapse();
                            }
                }
        			       
        			       	
        			       	    		
		            		,{
                    xtype: 'combo', tpl: '<tpl for="."><div ext:qtip="{cdProceso}. {dsProceso}" class="x-combo-list-item">{dsProceso}</div></tpl>',
                    store: dsPago,
                    displayField:'dsProceso',
                    valueField:'cdProceso',
                    hiddenName: 'cdProceso',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: "Instrumento de Pago",
                    header: getLabelFromMap('cmbOperacion',helpMap,'Instrumento de Pago'),
                    tooltip: getToolTipFromMap('cmbOperacion',helpMap,'Seleccione Instrumento de Pago'),                   
                    hasHelpIcon:getHelpIconFromMap('cmbOperacion',helpMap,'HELP'),
	              	Ayuda: getHelpTextFromMap('cmbOperacion',helpMap,'HELP'), 
                    forceSelection: true,
                    width: 185,
                    emptyText:'Seleccione Instrumento de Pago...',
                    selectOnFocus:true,
                    // labelSeparator:'',
                  //  allowBlank : false,
                    id:'cdPagoId'
                }     		        	
                
                                                  ]
								},
								
								{
								labelWidth: 100,
								columnWidth:.45,
                				layout: 'form',
		                		border: false,
        		        		items:[   {
        		        		 xtype: 'combo',labelWidth: 70, tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                            			store: aseg_store,
	                            			displayField:'descripcion',
	                            			valueField:'codigo',
	                            			id:'cdUniEcoId', 
	                            			hiddenName: 'codAseguradora', 
	                            			typeAhead: true,
	                            			// allowBlank:false,
	                            			mode: 'local', 
	                            			triggerAction: 'all',  /*anchor:'100%',*/
	                            			width:185, 
	                            			emptyText:'Seleccione Aseguradora...',
	                            			forceSelection:true,
	                            			selectOnFocus:true,
	                            			loadMask: true,
	                            			mode:'local',
	                            			fieldLabel: getLabelFromMap('cdUniEcoId',helpMap,'Aseguradora'),
	                            			tooltip:getToolTipFromMap('cdUniEcoId',helpMap,'Aseguradora'),
	                            			hasHelpIcon:getHelpIconFromMap('cdUniEcoId',helpMap,'HELP'),
											Ayuda: getHelpTextFromMap('cdUniEcoId',helpMap,'HELP'),

	                            			onSelect: function (record) {
	                            				
	                            				
                                  
	                            				this.setValue(record.get('codigo'));
	                            				formPanel.findById(('cdRamoId')).setValue("");				
	                            					    desProducto.removeAll();
	                            					    desProducto.load({
	                            											params: {cdunieco: record.get('codigo'),
	                            											         cdElemento: formPanel.findById(('cdClienteId')).getValue(),
	                            											         cdRamo: ""
	                            											         },
	                            											failure: aseg_store.removeAll()
	                            										});
	                            				
	                            				this.collapse();
	                            				
	                            		
	                                       }
		            			       //]
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
                    fieldLabel: getLabelFromMap('cdRamoId',helpMap,'Producto'),
	                tooltip:getToolTipFromMap('cdRamoId',helpMap,'Producto'),
	                hasHelpIcon:getHelpIconFromMap('cdRamoId',helpMap,'HELP'),
					Ayuda: getHelpTextFromMap('cdRamoId',helpMap,'HELP'),
                    //fieldLabel: "Producto",
                    forceSelection: true,
                    mode:'local',
                     //anchor:'100%',
                    width:185,
                    emptyText:'Seleccione Producto...',
                    selectOnFocus:true,
                    id:'cdRamoId'
                 //   allowBlank:false//,
                    //labelSeparator:''
                }         		        				 
                                       ]
                				},
                				
         
                				{
								columnWidth:.1,
                				layout: 'form'
                				}
                				]
                			}],
                			buttons:[{
        							text:getLabelFromMap('ntfcnButtonBuscar',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('ntfcnButtonBuscar',helpMap,'Busca atributos variables por instrumento de pago'),
        							handler: function() {
				               			if (formPanel.form.isValid()) {
                                               if (grid2!=null) {
                                                reloadGrid();
                                               } else {
                                                createGrid();
                                               }
                						} else{
                							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
										}
									}
        							},{
        							text:getLabelFromMap('ntfcnButtonCancelar',helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('ntfcnButtonCancelar',helpMap,'Cancela la b&uacute;squeda'),                              
        							handler: function() {
        								formPanel.form.reset();
        							}
        						}]
        					}]
        				}]	            
        
});   


// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
     {
        
        header: getLabelFromMap('cmDsProducto',helpMap,'Instrumento de Pago'),
        tooltip: getToolTipFromMap('cmDsProducto',helpMap,'Columna Instrumento de Pago'),
        hasHelpIcon:getHelpIconFromMap('cmDsProducto',helpMap),
		Ayuda: getHelpTextFromMap('cmDsProducto',helpMap,''),     
        dataIndex: 'dsPago',
        width: 180,
        sortable: true
        
        },{
        
        header: getLabelFromMap('cmdsElemento',helpMap,'Nivel'),
        tooltip: getToolTipFromMap('cmdsElemento',helpMap,'Columna Nivel'),
        hasHelpIcon:getHelpIconFromMap('cmdsElemento',helpMap),
		Ayuda: getHelpTextFromMap('cmdsElemento',helpMap,''),          
        dataIndex: 'dsElemen',
        width: 120,
        sortable: true
        
        }, {
        
        header: getLabelFromMap('cmdsUnieco',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('cmdsUnieco',helpMap,'Columna Aseguradora'),
         hasHelpIcon:getHelpIconFromMap('cmdsUnieco',helpMap),
		Ayuda: getHelpTextFromMap('cmdsUnieco',helpMap,''),   
        dataIndex: 'dsUnieco',
        width: 150,
        sortable: true
        
        },{
        
        header: getLabelFromMap('cmDsProducto',helpMap,'Producto'),
        tooltip: getToolTipFromMap('cmDsProducto',helpMap,'Columna Producto'),
        hasHelpIcon:getHelpIconFromMap('cmDsProducto',helpMap),
		Ayuda: getHelpTextFromMap('cmDsProducto',helpMap,''),     
        dataIndex: 'dsRamo',
        width: 125,
        sortable: true
        
        },{
        
        	
        header: getLabelFromMap('cmDsCampan',helpMap,'Atributo'),
        tooltip: getToolTipFromMap('cmDsCampan',helpMap,'Columna Atributo'),
        hasHelpIcon:getHelpIconFromMap('cmDsCampan',helpMap),
		Ayuda: getHelpTextFromMap('cmDsCampan',helpMap,''),  
        dataIndex: 'dsCampan',
        width: 125,
        sortable: true
        
        },{
        	
        dataIndex: 'cdPago',
        hidden :true
        
       },{
        	
        dataIndex: 'cdElemen',
        hidden :true
        
       },{
        	
        dataIndex: 'cdUnieco',
        hidden :true
        
       },{
        	
        dataIndex: 'cdRamo',
        hidden :true
        
       },{
        	
        dataIndex: 'cdatribu',
        hidden :true
        
       }
       
]);

var grid2;

function createGrid(){
       grid2= new Ext.grid.GridPanel({
       		id: 'grid2',
            el:'gridConfiguracionEncuestas',
            store:storeGrillaInstrumentoPago,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            reader:jsonGrillaAtributosVblesInstPago,
            border:true,
            cm: cm,
            loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
            clicksToEdit:1,
	        successProperty: 'success',
            buttonAlign: "center",
            buttons:[
                  {
                  text:getLabelFromMap('btnAgregarId',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('btnAgregarId',helpMap,'Lleva a la pantalla Agregar Instrumento de Pago'),
                  handler:function(){
									
                					window.location = _ACTION_IR_INSTRUMENTO_PAGO_AGREGAR;
                			         }
                  },
                  {
                  text:getLabelFromMap('gridNtfcnBottonEditar',helpMap,'Editar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonEditar',helpMap,'Editar atributo variable por instrumento de pago'),
                  handler:function(){                  		
						if (getSelectedRecord()!=null) {
                        		window.location=_ACTION_IR_INSTRUMENTO_PAGO_EDITAR+'?cdunica='+getSelectedRecord().get('cdunica')+'&cdatribu='+getSelectedRecord().get('cdatribu')+'&limit=999'+'&edit=0';
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                	}
                  				
                  	/*{
						if (getSelectedKey(grid2, "cdatribu") != "") {
                        		editarConfiguracionEncuesta(getSelectedRecord(grid2));
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                        }
                	}*/
                	
                	/*VER matrizAsingaion.js 
                	  {
						if (getSelectedKey(grid2, "cdmatriz") != ""){
                        		//editar(getSelectedKey(grid2, "cdmatriz"));
                        		 window.location = _ACTION_IR_CONFIGURA_MATRIZ_TAREA_EDITAR + '?cdmatriz=' + getSelectedKey(grid2, "cdmatriz");;
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                	}*/
                	
                	
                	 },
                   {
                  text:getLabelFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar un atributo variable por instrumento de pago'),
                  handler:function(){
						if (getSelectedKey(grid2,"cdatribu") != "") {
                        		// borrarConfigEncuesta(getSelectedRecord(grid2));
                        		borrar(getSelectedKey(grid2, "cdatribu"));
                        		
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                   }
                  },
                  
                                    
                  {
                  text:getLabelFromMap('gridNtfcnBottonExportar',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonExportar',helpMap,'Exportar datos a diversos formatos'),
                    handler:function(){
                        var url = _ACTION_EXPORTAR_INSTRUMENTOS_PAGO+ '?aseguradora=' + Ext.getCmp('cdUniEcoId').getRawValue() +'&nivel=' + Ext.getCmp('cdClienteId').getRawValue() +'&pago=' + Ext.getCmp('cdPagoId').getRawValue()+ '&producto=' + Ext.getCmp('cdRamoId').getRawValue();
                        showExportDialog( url );
                    } 
                  
                  }
                  ],
            width:700,
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store:storeGrillaInstrumentoPago,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
   grid2.render()
}

  
formPanel.render();
//aseg_store.load({params: {cdElemento:'6337'}});
dsNivelCorpo.load();
createGrid();
dsPago.load();
/*function borrarConfigEncuesta(record) {
		if(record.get('nmConfig') != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se borrara el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						pv_nmconfig_i:record.get('nmConfig'),
         						pv_cdproceso_i:record.get('cdProceso'),
         						pv_cdcampan_i:record.get('cdCampan'),
         						pv_cdmodulo_i:record.get('cdModulo'),
         						pv_cdencuesta_i:record.get('cdElemento')
         			};
         			execConnection(_ACTION_BORRAR_INSTRUMENTO_PAGO, _params, cbkConnection);
               }
			})
		}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
		}
};

function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()});
	}
}  



});
*/



function borrar(key) {
		if(key != "")
		{
		   
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						pv_cdatribu_i: key
         			};
         			execConnection(_ACTION_BORRAR_INSTRUMENTO_PAGO, _params, cbkConnection);
               }
			})
		}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		}
};

function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()});
	}
}      
});


function reloadGrid(){
	var _params = {
		            nivel: Ext.getCmp('formPanel').form.findField('cdClienteId').getValue(),
       				pago: Ext.getCmp('formPanel').form.findField('cdPagoId').getValue(),
       				aseguradora: Ext.getCmp('formPanel').form.findField('cdUniEcoId').getValue(),
       				producto: Ext.getCmp('formPanel').form.findField('cdRamoId').getValue()
       			
       			  };
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
}
function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),'No se encontraron dastos'); //_store.reader.jsonData.actionErrors[0]);
	}
}