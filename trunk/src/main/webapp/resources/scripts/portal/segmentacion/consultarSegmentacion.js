Ext.onReady(function(){   
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//VALORES DE INGRESO DE LA BUSQUEDAssssss
var dsUniEco = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsUniEcoId',helpMap,'Cliente'),
        tooltip:getToolTipFromMap('dsUniEcoId',helpMap,''), 
        hasHelpIcon:getHelpIconFromMap('dsUniEcoId',helpMap),
        Ayuda:getHelpTextFromMap('dsUniEcoId',helpMap),
        id: 'dsUniEcoId', 
        name: 'dsUniEco',
        allowBlank: true,
        //anchor: '95%'
        width:186
    });
 
var dsRamo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsRamoId',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('dsRamoId',helpMap,''), 
        hasHelpIcon:getHelpIconFromMap('dsRamoId',helpMap),
        Ayuda:getHelpTextFromMap('dsRamoId',helpMap),
        id: 'dsRamoId', 
        name: 'dsRamo',
        allowBlank: true,
        //anchor: '95%'
        width:186
    });
  
  
    
var dsModulo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsModuloId',helpMap,'Producto'),
        tooltip:getToolTipFromMap('dsModuloId',helpMap,'Producto'), 
        hasHelpIcon:getHelpIconFromMap('dsModuloId',helpMap),
        Ayuda:getHelpTextFromMap('dsModuloId',helpMap),
        id: 'dsModuloId', 
        name: 'dsModulo',
        allowBlank: true,
        width:186
        //anchor: '95%'
    });
    
var dsProceso = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsProcesoId',helpMap,'Inicio'),
        tooltip:getToolTipFromMap('dsProcesoId',helpMap,'Fin'), 
        hasHelpIcon:getHelpIconFromMap('dsProcesoId',helpMap),
        Ayuda:getHelpTextFromMap('dsProcesoId',helpMap),
        id: 'dsProcesoId', 
        name: 'dsProceso',
        allowBlank: true,
        width:186
        //anchor: '95%'
    });
    
 
var nmPoliza = new Ext.form.NumberField({
        fieldLabel: getLabelFromMap('nmPolizaId',helpMap,'Fin'),
        tooltip:getToolTipFromMap('nmPolizaId',helpMap,'Fin'), 
        hasHelpIcon:getHelpIconFromMap('nmPolizaId',helpMap),
        Ayuda:getHelpTextFromMap('nmPolizaId',helpMap),
        id: 'nmPolizaId', 
        name: 'nmPoliza',
        allowBlank: true,
        width:186
        //anchor: '95%'
    });
    
var incisosFormEncuesta = new Ext.FormPanel({
		id: 'incisosFormEncuesta',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('',helpMap,'Consultar Segmentacion')+'</span>',
        iconCls:'logo',
       // store:storeGrillaEncuesta,
        //reader:jsonGrillaEncuesta,
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_ENCUESTAS,
        width: 600,
        height:220,
        items: [{
        		layout:'form',
				border: false,
 				items:[
                {
        		bodyStyle:'background: white',
		        labelWidth: 80,
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
        		        		items:[       		        				
        		        				 dsUniEco,
        		        				 dsRamo
        		        			//	 dsEncuesta,
        		        				// dsCampan
                                       ]
								},
								{
								columnWidth:.5,
                				layout: 'form',
		                		border: false,
        		        		items:[       		        				
        		        				 dsModulo,
        		        				 dsProceso,
        		        				 nmPoliza
                                       ]
                				},{
								columnWidth:.2,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
        							text:getLabelFromMap('cnsltEncButtonBuscar',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('cnsltEncButtonBuscar',helpMap,'Busca un Grupo de Configuraciones para Encuesta'),
        							handler: function() {
				               			if (incisosFormEncuesta.form.isValid()) {
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
        							text:getLabelFromMap('cnsltEncButtonCancelar',helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('cnsltEncButtonCancelar',helpMap,'Cancela busqueda de Configuraciones de Encuesta'),                              
        							handler: function() {
        								incisosFormEncuesta.form.reset();
        							}
        						}]
        					}]
        				}]	            
        
});   


// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
       {
        header: getLabelFromMap('cmCnsltEncDsEncuesta',helpMap,'Encuesta'),
        tooltip: getToolTipFromMap('cmCnsltEncDsEncuesta',helpMap,'Columna Encuesta'),
        dataIndex: 'dsEncuesta',
        width: 105,
        sortable: true
        },
        {
        header: getLabelFromMap('cmCnsltEncDsUniEco',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('cmCnsltEncDsUniEco',helpMap,'Columna Aseguradora'),
        dataIndex: 'dsUnieco',
        width: 105,
        sortable: true
        },{
        header: getLabelFromMap('cmCnsltEncDsProducto',helpMap,'Producto'),
        tooltip: getToolTipFromMap('cmCnsltEncDsProducto',helpMap,'Columna Producto'),
        dataIndex: 'dsRamo',
        width: 105,
        sortable: true
        },{
        header: getLabelFromMap('cmCnsltEncDsCampan',helpMap,'Inicio'),
        tooltip: getToolTipFromMap('cmCnsltEncDsCampan',helpMap,'Columna Inicio'),
        dataIndex: 'dsCampan',
        width: 105,
        sortable: true
        },{
        header: getLabelFromMap('cmCnsltEncDsModulo',helpMap,'Fin'),
        tooltip: getToolTipFromMap('cmCnsltEncDsModulo',helpMap,'Columna Fin'),
        dataIndex: 'dsModulo',
        width: 105,
        sortable: true
         }
                   
]);

var grid2;

function createGrid(){
       grid2= new Ext.grid.GridPanel({
       		id: 'grid2',
            el:'gridEncuestas',
            store:storeGrillaEncuesta,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            reader:jsonGrillaEncuesta,
            border:true,
            cm: cm,
            loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
            clicksToEdit:1,
	        successProperty: 'success',
            buttonAlign: "center",
            buttons:[
                  
                   {
                  text:getLabelFromMap('gridCnsltEncBottonAgregar',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('gridCnsltEncBottonAgregar',helpMap,'Agrega una Encuesta'),
                  handler:function(){
						agregarConfiguracionEncuesta();
                	}
                  },
                  {
                  text:getLabelFromMap('gridCnsltEncBottonEditar',helpMap,'Editar'),
                  tooltip: getToolTipFromMap('gridCnsltEncBottonEditar',helpMap,'Edita una Encuesta'),
                  handler:function(){
						if (getSelectedRecord(grid2) != null) {
                        		
                        		editarEncuestas(getSelectedRecord(grid2));
                        		
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                	}
                  },
                   {
                  text:getLabelFromMap('gridCnsltEncBottonBorrar',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('gridCnsltEncBottonBorrar',helpMap,'Eliminar una Encuesta'),
                  handler:function(){
						if (getSelectedKey(grid2, "nmConfig") != "") {
                        		borrar(getSelectedRecord(grid2));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                   }
                  },
                  {
                  text:getLabelFromMap('gridCnsltEncBottonCopiar',helpMap,'Copiar'),
                  tooltip: getToolTipFromMap('gridCnsltEncBottonCopiar',helpMap,'Copiar una Segmentacion'),
                  handler:function(){
						if (getSelectedKey(grid2, "nmConfig") != "") {
                        	//	borrar(getSelectedRecord(grid2));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                   }
                  },
                  {
                  text:getLabelFromMap('gridCnsltEncBottonExportar',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('gridCnsltEncBottonExportar',helpMap,'Exporta datos a diversos formatos'),
                    handler:function(){
                      //  var url = _ACTION_BUSCAR_ENCUESTAS_EXPORT + '?pv_dsunieco_i=' + dsUniEco.getValue() + '&pv_dsramo_i='+ dsRamo.getValue() + '&pv_dsencuesta_i=' + dsEncuesta.getValue() + '&pv_dscampana_i=' + dsCampan.getValue() + '&pv_dselemento_i=' + dsModulo.getValue() + '&pv_dsproceso_i=' + dsProceso.getValue() + '&pv_nmpoliex_i=' + nmPoliza.getValue();
                       // showExportDialog( url );
                    } 
                  
                  }
                 
                  ],
            width:600,
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					// pageSize:20,
					pageSize: itemsPerPage,
					store:storeGrillaEncuesta,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
   grid2.render()
}

  
incisosFormEncuesta.render();
createGrid();

function borrar(record) {
		if(record.get('nmConfig') != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminara el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						pv_nmconfig_i:record.get('nmConfig'),
         						pv_cdunieco_i:record.get('cdUnieco'),
         						pv_cdramo_i:record.get('cdRamo'),
         						pv_nmpoliex_i:record.get('nmPoliza'),
         						pv_estado_i:record.get('estado'),
         						pv_cdencuesta_i:record.get('cdEncuesta')
         			};
         			//execConnection(_ACTION_BORRAR_ENCUESTAS, _params, cbkConnection);
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
       				pv_dsunieco_i:Ext.getCmp('incisosFormEncuesta').form.findField('dsUniEco').getValue(),
       				pv_dsramo_i:Ext.getCmp('incisosFormEncuesta').form.findField('dsRamo').getValue(), 
       				pv_dsencuesta_i:Ext.getCmp('incisosFormEncuesta').form.findField('dsEncuesta').getValue(), 
       				pv_dscampana_i:Ext.getCmp('incisosFormEncuesta').form.findField('dsCampan').getValue(), 
       				pv_dselemento_i:Ext.getCmp('incisosFormEncuesta').form.findField('dsModulo').getValue(), 
       				pv_dsproceso_i:Ext.getCmp('incisosFormEncuesta').form.findField('dsProceso').getValue(), 
       				pv_nmpoliex_i:Ext.getCmp('incisosFormEncuesta').form.findField('nmPoliza').getValue()
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