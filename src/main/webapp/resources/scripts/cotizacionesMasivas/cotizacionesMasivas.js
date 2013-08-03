Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//VALORES DE INGRESO DE LA BUSQUEDAssssss
var dsUniEco = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtDsUniEco',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('txtDsUniEco',helpMap,'Aseguradora'), 
        id: 'dsUniEcoId', 
        name: 'dsUniEco',
        allowBlank: true,
        anchor: '100%'
        //width:150
    });
 
var dsRamo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtDsRamo',helpMap,'Producto'),
        tooltip:getToolTipFromMap('txtDsRamo',helpMap,'Producto'), 
        id: 'dsRamoId', 
        name: 'dsRamo',
        allowBlank: true,
        anchor: '100%'
        //width:150
    });
  
var dsCarga = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtCarga',helpMap,'Carga'),
        tooltip:getToolTipFromMap('txtCarga',helpMap,'Carga'), 
        id: 'dsCargaId', 
        name: 'dsCarga',
        allowBlank: true,
        anchor: '100%'
        //width:150
    });
    
  
 
var dsCotizacion = new Ext.form.NumberField({
        fieldLabel: getLabelFromMap('txtDsCotizacion',helpMap,'Cotizaci&oacute;n'),
        tooltip:getToolTipFromMap('txtDsCotizacion',helpMap,'Cotizaci&oacute;n'), 
        id: 'dsCotizacionId', 
        name: 'dsCotizacion',
        allowBlank: true,
        anchor: '100%'       
    });
  
    
    
    var dtVigenciaInicio = new Ext.form.DateField({
       
        fieldLabel: getLabelFromMap('dtVigenciaInicio',helpMap,'Vigencia Inicio'),
	    tooltip: getToolTipFromMap('dtVigenciaInicio',helpMap,'Vigencia Inicio'),                    				    
        id: 'dtVigenciaInicioId', 
        name: 'dtVigenciaInicio',
        format:'d/m/Y'
    });
    
     var dtVigenciaFin = new Ext.form.DateField({
        fieldLabel: getLabelFromMap('dtVigenciaFin',helpMap,'Fin'),
	    tooltip: getToolTipFromMap('dtVigenciaFin',helpMap,'Vigencia Fin'),                    				    
        id: 'dtVigenciaFinId', 
        name: 'dtVigenciaFin',
        format:'d/m/Y'
    });
    
    var dsCotizacion = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtDsCotizacion',helpMap,'Cotizaci&oacute;n'),
        tooltip:getToolTipFromMap('txtDsCotizacion',helpMap,'Cotizaci&oacute;n'), 
        id: 'dsCotizacionId', 
        name: 'dsCotizacion',
        allowBlank: true,
        anchor: '100%'       
    });
    
    
var incisosFormPolizas = new Ext.FormPanel({
		id: 'incisosFormPolizas',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('24',helpMap,'Resultados Cotizaciones Masivas')+'</span>',
        iconCls:'logo',
        //store:storeGrillaEncuesta,
        //reader:jsonGrillaEncuesta,
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        //url: _ACTION_BUSCAR_ENCUESTAS,
        width: 700,
        height:220,
        items: [{
        		layout:'form',
				border: false,
 				items:[
                {
        		bodyStyle:'background: white',
		        labelWidth: 100,
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
						    	columnWidth:.4,
                				layout: 'form',
		                		border: false,
        		        		items:[       		        				
        		        				 dsUniEco,
        		        				 dsRamo,
        		        				 dsCarga,
        		        				 dtVigenciaInicio
                                       ]
								},
								{
								columnWidth:.4,
                				layout: 'form',
		                		border: false,
        		        		items:[       		        				
        		        				 
        		        				 dsCotizacion,
        		        				 dtVigenciaFin
                                       ]
                				},{
								columnWidth:.2,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
        							text:getLabelFromMap('ntfcnButtonBuscar',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('ntfcnButtonBuscar',helpMap,'Busca un Grupo de Polizas a Cancelar'),
        							handler: function() {
				               			if (incisosFormPolizas.form.isValid()) {
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
        							tooltip: getToolTipFromMap('ntfcnButtonCancelar',helpMap,'Cancelar busqueda de Polizas'),                              
        							handler: function() {
        								incisosFormPolizas.form.reset();
        							}
        						}]
        					}]
        				}]	            
        
});   


// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmDsUniEco',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('cmDsUniEco',helpMap,'Columna Aseguradora'),
        dataIndex: 'dsUnieco',
        width: 100,
        sortable: true
        
        },{
        
        header: getLabelFromMap('cmDsProducto',helpMap,'Producto'),
        tooltip: getToolTipFromMap('cmDsProducto',helpMap,'Columna Producto'),
        dataIndex: 'dsRamo',
        width: 100,
        sortable: true
        
        },{
        	
        header: getLabelFromMap('cmRazonSocial',helpMap,'Raz&oacute;n Social'),
        tooltip: getToolTipFromMap('cmRazonSocial',helpMap,'Columna Raz&oacute;n Social'),
        dataIndex: 'dsRazonSocial',
        width: 100,
        sortable: true
        
        },{
        header: getLabelFromMap('cmCarga',helpMap,'Carga'),
        tooltip: getToolTipFromMap('cmCarga',helpMap,'Columna Carga'),
        dataIndex: 'dsCarga',
        width: 100,
        sortable: true
        
        },{
        	
        header: getLabelFromMap('cmCotizacion',helpMap,'Cotizaci&oacute;n'),
        tooltip: getToolTipFromMap('cmCotizacion',helpMap,'Columna Cotizaci&oacute;n'),
        dataIndex: 'dsCotizacion',
        width: 100,
        sortable: true
        
        },{
        	
        	 header: getLabelFromMap('cdDtInicio',helpMap,'Inicio'),
        tooltip: getToolTipFromMap('cdDtInicio',helpMap,'Columna Inicio'),
        dataIndex: 'dtInicio',
        width: 100,
        sortable: true
        
        },{
        	
        header: getLabelFromMap('cmDtFin',helpMap,'Fin'),
        tooltip: getToolTipFromMap('cmDtFin',helpMap,'Columna Fin'),
        dataIndex: 'dtFin',
        width: 100,
        sortable: true
        
        },{
        
        header: getLabelFromMap('cmPrimaTotal',helpMap,'Prima Total'),
        tooltip: getToolTipFromMap('cmPrimaTotal',helpMap,'Columna Prima Total'),
        dataIndex: 'dsPrimaTotal',
        width: 100,
        sortable: true
        
        },{
        	 header: getLabelFromMap('cmAprobar',helpMap,'Aprobar'),
        tooltip: getToolTipFromMap('cmAprobar',helpMap,'Columna Aprobar'),
        dataIndex: 'swAprobar',
        width: 100,
        sortable: true
        
        
        }
]);

var grid2;

function createGrid(){
       grid2= new Ext.grid.EditorGridPanel({
       		id: 'grid2',
            el:'gridElementos',
            store:storeGrillaPolizasCancelar,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            reader:jsonGrillaPolizasCancelar,
            border:true,
            cm: cm,
            clicksToEdit:1,
	        successProperty: 'success',
            buttonAlign: "center",
            buttons:[
                  
                  {
                  text:getLabelFromMap('gridNtfcnBottonDetalle',helpMap,'Detalle'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonDetalle',helpMap,'Detalle'),
                  handler:function(){
                         
                         
						if (getSelectedKey(grid2, "cdNotificacion") != "") {
                        		// muestraDetalle(getSelectedKey(grid2, "cdNotificacion"));
                        		//editar(getSelectedKey(grid2, "cdNotificacion"));
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                        }
                	}
                  },
                   {
                  text:getLabelFromMap('gridNtfcnBottonComprar',helpMap,'Comprar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonComprar',helpMap,'Comprar'),
                  handler:function(){
						if (getSelectedKey(grid2, "nmConfig") != "") {
                        		borrar(getSelectedRecord(grid2));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                   }
                  }, {
                  text:getLabelFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar'),
                  handler:function(){
						if (getSelectedKey(grid2, "nmConfig") != "") {
                        		borrar(getSelectedRecord(grid2));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                   }
                  },{
                  text:getLabelFromMap('gridNtfcnBottonImprimir',helpMap,'Imprimir'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonImprimir',helpMap,'Imprimir'),
                  handler:function(){
						if (getSelectedKey(grid2, "nmConfig") != "") {
                        		borrar(getSelectedRecord(grid2));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                   }
                  },{
                  text:getLabelFromMap('gridNtfcnBottonNuevaCotizacion',helpMap,'Nueva Cotizaci&oacute;n'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonNuevaCotizacion',helpMap,'Nueva Cotizaci&oacute;n'),
                  handler:function(){
						if (getSelectedKey(grid2, "nmConfig") != "") {
                        		borrar(getSelectedRecord(grid2));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                   }
                  },{
                  text:getLabelFromMap('gridNtfcnBottonEnviarCorreo',helpMap,'Enviar Correo'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonEnviarCorreo',helpMap,'Enviar Correo'),
                  handler:function(){
						if (getSelectedKey(grid2, "nmConfig") != "") {
                        		borrar(getSelectedRecord(grid2));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                   }
                  }/*,
                  {
                  text:getLabelFromMap('gridNtfcnBottonExportar',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonExportar',helpMap,'Exportar datos a diversos formatos'),
                    handler:function(){
                        var url = _ACTION_BUSCAR_ENCUESTAS_EXPORT + '?pv_dsunieco_i=' + dsUniEco.getValue() + '&pv_dsramo_i='+ dsRamo.getValue() + '&pv_dsencuesta_i=' + dsEncuesta.getValue() + '&pv_dscampana_i=' + dsCampan.getValue() + '&pv_dsmodulo_i=' + dsModulo.getValue() + '&pv_dsproceso_i=' + dsProceso.getValue() + '&pv_nmpoliza_i=' + nmPoliza.getValue();
                        showExportDialog( url );
                    } 
                  
                  },
                  {
                  text:getLabelFromMap('gridNtfcnBottonRegresar',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonRegresar',helpMap,'Volver a la Pantalla Anterior')
                  }*/
                  ],
            width:700,
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store:storeGrillaPolizasCancelar,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
   grid2.render()
}

  
incisosFormPolizas.render();
createGrid();

function borrar(record) {
		if(record.get('nmConfig') != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						pv_nmconfig_i:record.get('nmConfig'),
         						pv_cdunieco_i:record.get('cdUnieco'),
         						pv_cdramo_i:record.get('cdRamo'),
         						pv_nmpoliza_i:record.get('nmPoliza'),
         						pv_estado_i:record.get('estado'),
         						pv_cdencuesta_i:record.get('cdEncuesta')
         			};
         			execConnection(_ACTION_BORRAR_ENCUESTAS, _params, cbkConnection);
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


function reloadGrid(){
	var _params = {
       				pv_dsunieco_i:Ext.getCmp('incisosFormPolizas').form.findField('dsUniEco').getValue(),
       				pv_dsramo_i:Ext.getCmp('incisosFormPolizas').form.findField('dsRamo').getValue(), 
       				pv_dsencuesta_i:Ext.getCmp('incisosFormPolizas').form.findField('dsEncuesta').getValue(), 
       				pv_dscampana_i:Ext.getCmp('incisosFormPolizas').form.findField('dsCampan').getValue(), 
       				pv_dsmodulo_i:Ext.getCmp('incisosFormPolizas').form.findField('dsModulo').getValue(), 
       				pv_dsproceso_i:Ext.getCmp('incisosFormPolizas').form.findField('dsProceso').getValue(), 
       				pv_nmpoliza_i:Ext.getCmp('incisosFormPolizas').form.findField('nmPoliza').getValue()
       			  };
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
}
function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}