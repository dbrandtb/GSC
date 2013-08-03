Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//VALORES DE INGRESO DE LA BUSQUEDA
var dsUnieco = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsUniecoId',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('dsUniecoId',helpMap,'Aseguradora'), 
        hasHelpIcon:getHelpIconFromMap('dsUniecoId',helpMap),
		Ayuda: getHelpTextFromMap('dsUniecoId',helpMap,''),
        id: 'dsUniecoId', 
        name: 'dsUnieco',
        allowBlank: true,
        anchor: '100%'
        //width:14
    });
 
var dsRamo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsRamoId',helpMap,'Producto'),
        tooltip:getToolTipFromMap('dsRamoId',helpMap,'Producto'),
        hasHelpIcon:getHelpIconFromMap('dsRamoId',helpMap),
		Ayuda: getHelpTextFromMap('dsRamoId',helpMap,''),        
        id: 'dsRamoId', 
        name: 'dsRamo',
        allowBlank: true,
        anchor: '100%'
        //width:150
    });
  
var dsElemento = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsElementoId',helpMap,'Cuenta/Cliente'),
        tooltip:getToolTipFromMap('dsElementoId',helpMap,'Cuenta/Cliente'), 
         hasHelpIcon:getHelpIconFromMap('dsElementoId',helpMap),
		Ayuda: getHelpTextFromMap('dsElementoId',helpMap,''),   
        id: 'dsElementoId', 
        name: 'dsElemento',
        allowBlank: true,
        anchor: '100%'
        //width:150
    });
 
var dsProceso = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsProcesoId',helpMap,'Operaci&oacute;n'),
        tooltip:getToolTipFromMap('dsProcesoId',helpMap,'Operaci&oacute;n'), 
         hasHelpIcon:getHelpIconFromMap('dsProcesoId',helpMap),
		Ayuda: getHelpTextFromMap('dsProcesoId',helpMap,''),           
        id: 'dsProcesoId', 
        name: 'dsProceso',
        allowBlank: true,
        anchor: '100%'
        //width:150
    });
    
var dsCampan = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsCampanId',helpMap,'Campaña'),
        tooltip:getToolTipFromMap('dsCampanId',helpMap,'Campaña'), 
          hasHelpIcon:getHelpIconFromMap('dsCampanId',helpMap),
		Ayuda: getHelpTextFromMap('dsCampanId',helpMap,''),   
        id: 'dsCampanId', 
        name: 'dsCampan',
        allowBlank: true,
        anchor: '100%'
        //width:150
    });
    
    
   
var dsModulo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsModuloId',helpMap,'Modulo'),
        tooltip:getToolTipFromMap('dsModuloId',helpMap,'Modulo'), 
        hasHelpIcon:getHelpIconFromMap('dsModuloId',helpMap),
		Ayuda: getHelpTextFromMap('dsModuloId',helpMap,''),   
        id: 'dsModuloId', 
        name: 'dsModulo',
        allowBlank: true,
        anchor: '100%'
    });
  
    

    
var dsEncuesta = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsEncuestaId',helpMap,'Encuesta'),
        tooltip:getToolTipFromMap('dsEncuestaId',helpMap,'Encuesta'), 
         hasHelpIcon:getHelpIconFromMap('dsEncuestaId',helpMap),
		Ayuda: getHelpTextFromMap('dsEncuestaId',helpMap,''),   
        id: 'dsEncuestaId', 
        name: 'dsEncuesta',
        allowBlank: true,
        anchor: '100%'
        //width:150
    });
    
    
var incisosFormEncuesta = new Ext.FormPanel({
		id: 'incisosFormEncuesta',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosFormEncuesta',helpMap,'Consulta Configuraci&oacute;n de Encuestas ')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_CONFIGURACION_ENCUESTAS,
        width: 550,
        height:220,
        items: [{
        		layout:'form',
				border: false,
 				items:[
                {
        		bodyStyle:'background: white',
		        labelWidth: 90,
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
        		        				 dsUnieco,
        		        				 dsRamo,
        		        				 dsElemento,
        		        				 dsProceso
                                       ]
								},
								
								{
								labelWidth: 90,
								columnWidth:.45,
                				layout: 'form',
		                		border: false,
        		        		items:[       		        				
        		        				 dsCampan,
        		        				 dsModulo,
        		        				 dsEncuesta
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
        							tooltip: getToolTipFromMap('ntfcnButtonBuscar',helpMap,'Busca un Grupo de Configuraciones para Encuesta'),
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
        							text:getLabelFromMap('ntfcnButtonCancelar',helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('ntfcnButtonCancelar',helpMap,'Cancelar busqueda de Configuraciones de Encuesta'),                              
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
        
        header: getLabelFromMap('cmdsUnieco',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('cmdsUnieco',helpMap,'Columna Aseguradora'),
         hasHelpIcon:getHelpIconFromMap('cmdsUnieco',helpMap),
		Ayuda: getHelpTextFromMap('cmdsUnieco',helpMap,''),   
        dataIndex: 'dsUnieco',
        width: 80,
        sortable: true
        
        },{
        
        header: getLabelFromMap('cmDsProducto',helpMap,'Producto'),
        tooltip: getToolTipFromMap('cmDsProducto',helpMap,'Columna Producto'),
        hasHelpIcon:getHelpIconFromMap('cmDsProducto',helpMap),
		Ayuda: getHelpTextFromMap('cmDsProducto',helpMap,''),     
        dataIndex: 'dsRamo',
        width: 70,
        sortable: true
        
        },{
        
        header: getLabelFromMap('cmdsElemento',helpMap,'Cuenta/Cliente'),
        tooltip: getToolTipFromMap('cmdsElemento',helpMap,'Columna Cuenta/Cliente'),
        hasHelpIcon:getHelpIconFromMap('cmdsElemento',helpMap),
		Ayuda: getHelpTextFromMap('cmdsElemento',helpMap,''),          
        dataIndex: 'dsElemen',
        width: 100,
        sortable: true
        
        },{

        header: getLabelFromMap('cmdsProceso',helpMap,'Operaci&oacute;n'),
        tooltip: getToolTipFromMap('cmdsProceso',helpMap,'Columna Operaci&oacute;n'),
        hasHelpIcon:getHelpIconFromMap('cmdsProceso',helpMap),
		Ayuda: getHelpTextFromMap('cmdsProceso',helpMap,''),      
        dataIndex: 'dsProceso',
        width: 70,
        sortable: true
        
        },{
        
        	
        header: getLabelFromMap('cmDsCampan',helpMap,'Campaña'),
        tooltip: getToolTipFromMap('cmDsCampan',helpMap,'Columna Campaña'),
        hasHelpIcon:getHelpIconFromMap('cmDsCampan',helpMap),
		Ayuda: getHelpTextFromMap('cmDsCampan',helpMap,''),  
        dataIndex: 'dsCampan',
        width: 70,
        sortable: true
        
        },{
        	
        header: getLabelFromMap('cmDsModulo',helpMap,'M&oacute;dulo'),
        tooltip: getToolTipFromMap('cmDsModulo',helpMap,'Columna M&oacute;dulo'),
        hasHelpIcon:getHelpIconFromMap('cmDsModulo',helpMap),
		Ayuda: getHelpTextFromMap('cmDsModulo',helpMap,''),  
        dataIndex: 'dsModulo',
        width: 70,
        sortable: true
        
        },{
        	
        header: getLabelFromMap('cmDsEncuesta',helpMap,'Encuesta'),
        tooltip: getToolTipFromMap('cmDsEncuesta',helpMap,'Columna Encuesta'),
        hasHelpIcon:getHelpIconFromMap('cmDsEncuesta',helpMap),
		Ayuda: getHelpTextFromMap('cmDsEncuesta',helpMap,''),  
        dataIndex: 'dsEncuesta',
        width: 100,
        sortable: true
        
        },{
        	
        dataIndex: 'nmConfig',
        hidden :true
        
       },{
        	
        dataIndex: 'cdCampan',
        hidden :true
        
       },{
        	
        dataIndex: 'cdRamo',
        hidden :true
        
       },{
        	
        dataIndex: 'cdUnieco',
        hidden :true
        
       },{
        	
        dataIndex: 'cdProceso',
        hidden :true
        
       },{
        	
        dataIndex: 'cdModulo',
        hidden :true
        
       },{
        	
        dataIndex: 'cdEncuesta',
        hidden :true
        
       }
       
]);

var grid2;

function createGrid(){
       grid2= new Ext.grid.GridPanel({
       		id: 'grid2',
            el:'gridConfiguracionEncuestas',
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
                  text:getLabelFromMap('gridNtfcnBottonAgregar',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonAgregar',helpMap,'Crea una Nueva Encuesta'),
                  handler:function(){
                  agregarConfiguracionEncuesta();
                  }
                  },
                  {
                  text:getLabelFromMap('gridNtfcnBottonEditar',helpMap,'Editar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonEditar',helpMap,'Editar una Encuesta'),
                  handler:function(){
						if (getSelectedKey(grid2, "nmConfig") != "") {
                        		editarConfiguracionEncuesta(getSelectedRecord(grid2));
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                        }
                	}
                  },
                   {
                  text:getLabelFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar una Encuesta'),
                  handler:function(){
						if (getSelectedKey(grid2,"nmConfig") != "") {
                        		borrarConfigEncuesta(getSelectedRecord(grid2));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                   }
                  },
                  {
                  text:getLabelFromMap('gridNtfcnBottonExportar',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonExportar',helpMap,'Exportar datos a diversos formatos'),
                    handler:function(){
                        var url = _ACTION_EXPORTAR_CONFIGURACION_ENCUESTAS + '?pv_dsunieco_i=' + dsUnieco.getValue() + '&pv_dsramo_i=' + dsRamo.getValue() + '&pv_dselemento_i=' + dsElemento.getValue() + '&pv_dsproceso_='+ dsProceso.getValue() +'&pv_dscampan_i=' + dsCampan.getValue() +'&pv_dsmodulo_i=' + dsModulo.getValue() + '&pv_dsencuesta_i=' + dsEncuesta.getValue();
                        showExportDialog( url );
                    } 
                  
                  }, {
                  text:getLabelFromMap('gridNtfcnBottonTiempo',helpMap,'Tiempo'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonTiempo',helpMap,'Tiempo'),
                  handler:function(){
                 
                  	if (getSelectedKey(grid2, "cdCampan") != "") {
                        		 administrarTiempo(getSelectedRecord(grid2));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                  }
                  }/*,
                  {
                  text:getLabelFromMap('gridNtfcnBottonRegresar',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonRegresar',helpMap,'Volver a la Pantalla Anterior')
                  }*/
                  ],
            width:550,
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
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

function borrarConfigEncuesta(record) {
		if(record.get('nmConfig') != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						pv_nmconfig_i:record.get('nmConfig'),
         						pv_cdproceso_i:record.get('cdProceso'),
         						pv_cdcampan_i:record.get('cdCampan'),
         						pv_cdmodulo_i:record.get('cdModulo'),
         						pv_cdencuesta_i:record.get('cdEncuesta')
         			};
         			execConnection(_ACTION_BORRAR_CONFIGURACION_ENCUESTAS, _params, cbkConnection);
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
		            pv_dsunieco_i: Ext.getCmp('incisosFormEncuesta').form.findField('dsUnieco').getValue(),
       				pv_dsramo_i: Ext.getCmp('incisosFormEncuesta').form.findField('dsRamo').getValue(),
       				pv_dselemento_i: Ext.getCmp('incisosFormEncuesta').form.findField('dsElemento').getValue(),
       				pv_dsproceso_: Ext.getCmp('incisosFormEncuesta').form.findField('dsProceso').getValue(),
       				pv_dscampan_i: Ext.getCmp('incisosFormEncuesta').form.findField('dsCampan').getValue(),
       				pv_dsmodulo_i: Ext.getCmp('incisosFormEncuesta').form.findField('dsModulo').getValue(),
       				pv_dsencuesta_i: Ext.getCmp('incisosFormEncuesta').form.findField('dsEncuesta').getValue()
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