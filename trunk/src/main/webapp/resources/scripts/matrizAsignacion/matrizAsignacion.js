Ext.onReady(function(){  
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//VALORES DE INGRESO DE LA BUSQUEDA
var pv_dsproceso_i = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('pv_dsproceso_iId',helpMap,'Tarea'),
        tooltip:getToolTipFromMap('pv_dsproceso_iId',helpMap,'Asunto de la Tarea'),
        hasHelpIcon:getHelpIconFromMap('pv_dsproceso_iId',helpMap),
		Ayuda: getHelpTextFromMap('pv_dsproceso_iId',helpMap),
        id: 'pv_dsproceso_iId', 
        name: 'pv_dsproceso_i',
        allowBlank: true,
        //anchor: '100%',
        width:250
    });
    
var pv_dsformatoorden_i = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('pv_dsformatoorden_i',helpMap,'Formato Solicitud'),
        tooltip:getToolTipFromMap('pv_dsformatoorden_i',helpMap,'Formato de la Solicitud'),
        hasHelpIcon:getHelpIconFromMap('pv_dsformatoorden_i',helpMap),
		Ayuda: getHelpTextFromMap('pv_dsformatoorden_i',helpMap),
        id: 'pv_dsformatoorden_i', 
        name: 'pv_dsformatoorden_i',
        allowBlank: true,
        //anchor: '100%',
        width:250
    });

var pv_dselemen_i = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('pv_dselemen_iId',helpMap,'Cuenta/Grupo'),
        tooltip:getToolTipFromMap('pv_dselemen_iId',helpMap,'Cuenta o Grupo'),
        hasHelpIcon:getHelpIconFromMap('pv_dselemen_iId',helpMap),
		Ayuda: getHelpTextFromMap('pv_dselemen_iId',helpMap),
        id: 'pv_dselemen_iId', 
        name: 'pv_dselemen_i',
        allowBlank: true,
        //anchor: '100%',
        width:250
    });
 
 
 var pv_dsunieco_i = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('pv_dsunieco_iId',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('pv_dsunieco_iId',helpMap,'Nombre de Aseguradora'),
        hasHelpIcon:getHelpIconFromMap('pv_dsunieco_iId',helpMap),
		Ayuda: getHelpTextFromMap('pv_dsunieco_iId',helpMap),
        id: 'pv_dsunieco_iId', 
        name: 'pv_dsunieco_i',
        allowBlank: true,
        //anchor: '100%',
        width:250
    });

var pv_dsramo_i = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('pv_dsramo_iId',helpMap,'Producto'),
        tooltip:getToolTipFromMap('pv_dsramo_iId',helpMap,'Nombre de Producto'),
        hasHelpIcon:getHelpIconFromMap('pv_dsramo_iId',helpMap),
		Ayuda: getHelpTextFromMap('pv_dsramo_iId',helpMap),
        id: 'pv_dsramo_iId', 
        name: 'pv_dsramo_i',
        allowBlank: true,
        //anchor: '100%',
        width:250
    }); 
 
 
 
 
 
     
var incisosFormMatrizAsignacion = new Ext.FormPanel({
		id: 'incisosFormMatrizAsignacion',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:14px;backgroundcolor:#808080;">'+getLabelFromMap('incisosFormMatrizAsignacion',helpMap,'Matriz de Asignaci&oacute;n')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
      labelAlign: 'right',
        //frame:true,   
        url: _ACTION_BUSCAR_MATRICES,
        labelWidth:20,
        width: 600,
        height:250,
        items: [{
        		layout:'form',
        		border: false,
				items:[{
        		bodyStyle:'background: white',
		        labelWidth: 168,
                layout: 'form',
                title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
				//frame:true,
		       	baseCls: '',
		       	buttonAlign: "center",
		       	labelAlign:'right',
        		        items: [{
        		        	
        		        	layout:'column',
        		        	
		 				    border:false,
		 				    //columnWidth: 1,
        		    		items:[/*{
								//columnWidth:.2,
                				layout: 'form'
                				},*/
        		    		   {
						    	//columnWidth:.6,
                				layout: 'form',
                				
		                		border: false,
        		        		items:[       		        				
        		        				 pv_dsproceso_i,
        		        				 pv_dsformatoorden_i,
        		        				 pv_dselemen_i,
        		        				 pv_dsunieco_i,
        		        				 pv_dsramo_i       		        				
                                       ]
								}/*,
								{
								columnWidth:.2,
                				layout: 'form'
                				}*/]
                			}],
                			buttons:[{
        							text:getLabelFromMap('ntfcnButtonBuscar',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('ntfcnButtonBuscar',helpMap,'Busca un Grupo de Asignaciones'),
        							handler: function() {
				               			if (incisosFormMatrizAsignacion.form.isValid()) {
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
        							tooltip: getToolTipFromMap('ntfcnButtonCancelar',helpMap,'Cancela b&uacute;squeda de Asignaciones'),                              
        							handler: function() {
        								incisosFormMatrizAsignacion.form.reset();
        							}
        						}]
        					}]
        				}]	            
        
});   


// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmpv_dsproceso_i',helpMap,'Tarea'),
        tooltip: getToolTipFromMap('cmpv_dsproceso_i',helpMap,'Columna Tarea'),
        dataIndex: 'dsproceso',
        width: 200,
        sortable: true
        },
        {
        header: getLabelFromMap('cmpv_dsformatoorden_i',helpMap,'Formato Solicitud'),
        tooltip: getToolTipFromMap('cmpv_dsformatoorden_i',helpMap,'Columna Formato Solicitud'),
        dataIndex: 'dsformatoorden',
        width: 170,
        sortable: true
        },
        {
        header: getLabelFromMap('cmpv_dselemen_i',helpMap,'Cuenta/Grupo'),
        tooltip: getToolTipFromMap('cmpv_dselemen_i',helpMap,'Columna Cuenta/Grupo'),
        dataIndex: 'dselemen',
        width: 120,
        sortable: true
        },
        {
        header: getLabelFromMap('cmpv_dsunieco_i',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('cmpv_dsunieco_i',helpMap,'Columna Aseguradora'),
        dataIndex: 'dsunieco',
        width: 100,
        sortable: true
        },
        {
        header: getLabelFromMap('cmpv_dsramo_i',helpMap,'Producto'),
        tooltip: getToolTipFromMap('cmpv_dsramo_i',helpMap,'Columna Producto'),
        dataIndex: 'dsramo',
        width: 170,
        sortable: true
        },
        {
        dataIndex: 'cdproceso',
        hidden :true
        },
        {
        dataIndex: 'cdformatoorden',
        hidden :true
        },
        {
        dataIndex: 'cdelemento',
        hidden :true
        },
        {
        dataIndex: 'cdunieco',
        hidden :true
        },
        {
        dataIndex: 'cdramo',
        hidden :true
        },
        {
        dataIndex: 'cdmatriz',
        hidden :true
        }       
]);

var grid2;

function createGrid(){
       grid2= new Ext.grid.GridPanel({
       		id: 'grid2',
       		tooltip: getToolTipFromMap('grid2', helpMap),	
            el:'gridMatrizAsignacion',
            store:storeGrillaMatrizAsignacion,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            border:true,
            loadMask: {msg: getLabelFromMap('400070',helpMap,'Leyendo datos ...'), disabled: false},
            cm: cm,
            clicksToEdit:1,
	        successProperty: 'success',
	        buttonAlign: "center",
            buttons:[
                  {
                  text:getLabelFromMap('gridNtfcnBottonAgregar',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonAgregar',helpMap,'Crea una Nueva Matriz de Asignaci&oacute;n'),
                  handler:function(){
                                      window.location = _ACTION_IR_CONFIGURA_MATRIZ_TAREA;
                  }
                  },
                  {
                  text:getLabelFromMap('gridNtfcnBottonEditar',helpMap,'Editar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonEditar',helpMap,'Editar una Matriz de Asignaci&oacute;n'),
                  handler:function(){
						if (getSelectedKey(grid2, "cdmatriz") != ""){
                        		//editar(getSelectedKey(grid2, "cdmatriz"));
                        		 window.location = _ACTION_IR_CONFIGURA_MATRIZ_TAREA_EDITAR + '?cdmatriz=' + getSelectedKey(grid2, "cdmatriz");;
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                	}
                  }, {
                  text:getLabelFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonBorrar',helpMap,'Elimina una Matriz de Asignaci&oacute;n'),
                  handler:function(){
						if (getSelectedKey(grid2, "cdmatriz") != "") {
                        		borrar(getSelectedKey(grid2, "cdmatriz"));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                                }
                   }
                  },
                  {
                  text:getLabelFromMap('gridNtfcnBottonExportar',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonExportar',helpMap,'Exporta los datos'),
                    handler:function(){
                        var url = _ACTION_EXPORT_MATRICES_ASIGNACION + '?pv_dsproceso_i=' + pv_dsproceso_i.getValue() + '&pv_dsformatoorden_i=' + pv_dsformatoorden_i.getValue() + '&pv_dselemen_i=' + pv_dselemen_i.getValue() + '&pv_dsunieco_i=' + pv_dsunieco_i.getValue() + '&pv_dsramo_i=' + pv_dsramo_i.getValue();
                        showExportDialog( url );
                    } 
                  }
                  /*
                  ,
                  {
                  text:getLabelFromMap('gridNtfcnBottonRegresar',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonRegresar',helpMap,'Volver a la Pantalla Anterior')
                  }
                  */
                  ],
            width:600,
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize: itemsPerPage,
					store: storeGrillaMatrizAsignacion,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    }),
			     listeners: {
						    'rowclick ': function(grid){
						    	//alert(grid.getSelections.record.get('cdproceso'));
						        
						    }
						  }           
        });
   grid2.render();
}

 
incisosFormMatrizAsignacion.render();
createGrid();

function borrar(key) {
		if(key != "")
		{
		   
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						pv_cdmatriz_i: key
         			};
         			execConnection(_ACTION_BORRAR_MATRIZ, _params, cbkConnection);
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
       		pv_dsproceso_i: Ext.getCmp('incisosFormMatrizAsignacion').form.findField('pv_dsproceso_i').getValue(),
       		pv_dsformatoorden_i: Ext.getCmp('incisosFormMatrizAsignacion').form.findField('pv_dsformatoorden_i').getValue(),
       		pv_dselemen_i: Ext.getCmp('incisosFormMatrizAsignacion').form.findField('pv_dselemen_i').getValue(),
       		pv_dsunieco_i: Ext.getCmp('incisosFormMatrizAsignacion').form.findField('pv_dsunieco_i').getValue(),
       		pv_dsramo_i: Ext.getCmp('incisosFormMatrizAsignacion').form.findField('pv_dsramo_i').getValue()
       		
       		
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
