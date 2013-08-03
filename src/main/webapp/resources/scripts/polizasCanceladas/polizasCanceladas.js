Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//VALORES DE INGRESO DE LA BUSQUEDAssssss
var dsAseg = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtDsUniEco',helpMap,'Asegurado'),
        tooltip:getToolTipFromMap('txtDsUniEco',helpMap,'Asegurado'), 
        id: 'dsAseg', 
        name: 'dsAseg',
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
 
var comboMotivoCancelacion = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{cdformatoorden}. {cdmatriz}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    id:'acCmbTareaId',
    fieldLabel: getLabelFromMap('cmbTareaIdOrdSol',helpMap,'Tarea'),
    tooltip:getToolTipFromMap('cmbTareaIdOrdSol',helpMap,'Tarea'),
    //store: storeTareas,
    displayField:'descripcion',
    valueField:'cdformatoorden',
    hiddenName: 'cdformatoorden',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    //width: 210,
    anchor: '100%',
    allowBlank: false,
    emptyText:'Seleccione Motivo...',
    selectOnFocus:true,
    forceSelection:true
}
);

var nmInciso = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtInciso',helpMap,'Inciso'),
        tooltip:getToolTipFromMap('txtInciso',helpMap,'Inciso'), 
        id: 'dsEncuestaId', 
        name: 'dsEncuesta',
        allowBlank: true,
        anchor: '100%'
        //width:150
    });
    
    
    
var dsUniEco = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtDsUniEco',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('txtDsUniEco',helpMap,'Aseguradora'), 
        id: 'dsUniEcoId', 
        name: 'dsUniEco',
        allowBlank: true,
        anchor: '100%'
        //width:150
    });
  
    

 
var nmPoliza = new Ext.form.NumberField({
        fieldLabel: getLabelFromMap('txtNmPoliza',helpMap,'Poliza'),
        tooltip:getToolTipFromMap('txtNmPoliza',helpMap,'Poliza'), 
        id: 'nmPolizaId', 
        name: 'nmPoliza',
        allowBlank: true,
        anchor: '100%'       
    });
    
var incisosFormPolizas = new Ext.FormPanel({
		id: 'incisosFormPolizas',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('24',helpMap,'P&oacute;lizas Canceladas')+'</span>',
        iconCls:'logo',
        //store:storeGrillaEncuesta,
        //reader:jsonGrillaEncuesta,
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        //url: _ACTION_BUSCAR_ENCUESTAS,
        width: 500,
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
        		        				 dsAseg,
        		        				 dsRamo,
        		        				 comboMotivoCancelacion,
        		        				 nmInciso
                                       ]
								},
								{
								columnWidth:.4,
                				layout: 'form',
		                		border: false,
        		        		items:[       		        				
        		        				 dsUniEco,
        		        				 nmPoliza
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
        
        header: getLabelFromMap('cmDsAseg',helpMap,'Asegurado'),
        tooltip: getToolTipFromMap('cmDsAseg',helpMap,'Columna Asegurado'),
        dataIndex: 'dsAseg',
        width: 100,
        sortable: true
        
        },{
        
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
        	
        header: getLabelFromMap('cmNmPoliza',helpMap,'P&oacute;liza'),
        tooltip: getToolTipFromMap('cmNmPoliza',helpMap,'Columna P&oacute;liza'),
        dataIndex: 'nmPoliza',
        width: 100,
        sortable: true
        
        },{
        header: getLabelFromMap('cmNmPoliza',helpMap,'Inciso'),
        tooltip: getToolTipFromMap('cmNmPoliza',helpMap,'Columna P&oacute;liza'),
        dataIndex: 'nmPoliza',
        width: 100,
        sortable: true
        
        },{
        	
        header: getLabelFromMap('cmFechaCancela',helpMap,'Fecha de Cancelaci&oacute;n'),
        tooltip: getToolTipFromMap('cmFechaCancela',helpMap,'Columna Fecha de Cancelaci&oacute;n'),
        dataIndex: 'nmdtCancela',
        width: 100,
        sortable: true
        
       
        
        },{
        	
        	header: getLabelFromMap('cmPrimaNoDevengada',helpMap,'Prima no Devengada'),
        tooltip: getToolTipFromMap('cmPrimaNoDevengada',helpMap,'Columna Prima no Devengada'),
        dataIndex: 'swPrimaDevengada',
        width: 100,
        sortable: true
        
       
        
        },{
        	
        header: getLabelFromMap('cmMotivoCancela',helpMap,'Motivo de Cancelaci&oacute;n'),
        tooltip: getToolTipFromMap('cmMotivoCancela',helpMap,'Columna Motivo de Cancelaci&oacute;n'),
        dataIndex: 'cdMotivoCancela',
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
                        		//editar(getSelectedKey(grid2, "cdNotificacion"));
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                        }
                	}
                  },
                   {
                  text:getLabelFromMap('gridNtfcnBottonRehabilitar',helpMap,'Rehabilitar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonRehabilitar',helpMap,'Rehabilitar'),
                  handler:function(){
						if (getSelectedKey(grid2, "nmConfig") != "") {
                        		borrar(getSelectedRecord(grid2));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                   }
                  },
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
                  }
                  ],
            width:500,
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