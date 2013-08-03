Ext.onReady(function(){         
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
//var helpMap=new Map();
//VALORES DE INGRESO DE LA BUSQUEDA
var pv_dsencuesta_i = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('pv_dsencuesta_iId',helpMap,'Encuesta'),
        tooltip:getToolTipFromMap('pv_dsencuesta_iId',helpMap,'Asunto de la Encuesta'), 
        hasHelpIcon:getHelpIconFromMap('pv_dsencuesta_iId',helpMap),
		Ayuda: getHelpTextFromMap('pv_dsencuesta_iId',helpMap),
        id: 'pv_dsencuesta_iId', 
        name: 'pv_dsencuesta_i',
        allowBlank: true,
        anchor: '100%'
    });
        
var incisosFormEncuesta = new Ext.FormPanel({
		id: 'incisosFormEncuesta',
        el:'formBusqueda',
        //title: (CODIGO_RAZON)?getLabelFromMap('100',helpMap,'Editar Configurar Motivos de Cancelaci&oacute;n'):getLabelFromMap('99',helpMap,'Agregar Configurar Motivos de Cancelaci&oacute;n'),
        
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosFormEncuesta',helpMap,'Consulta de Formato de Encuesta')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_ENCUESTAS,
        width: 500,
        height:192,
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
						    	columnWidth:.6,
                				layout: 'form',
		                		border: false,
        		        		items:[       		        				
        		        				 pv_dsencuesta_i     		        				
                                       ]
								},
								{
								columnWidth:.4,
                				layout: 'form'
                				}]
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
        							tooltip: getToolTipFromMap('ntfcnButtonCancelar',helpMap,'Cancela b&uacute;squeda de Configuraciones de Encuesta'),                              
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
        header: getLabelFromMap('cmDsEncuestaColId',helpMap,'Encuesta'),
        tooltip: getToolTipFromMap('cmDsEncuestaColId',helpMap,'Columna Encuesta'),
        dataIndex: 'dsEncuesta',
        width: 400,
        sortable: true
        },{
         dataIndex: 'cdEncuesta',
         hidden :true
        },{
         dataIndex: 'feRegistro',
         hidden :true
        },{
         dataIndex: 'swEstado',
         hidden :true
        }        
]);

var grid2;

function createGrid(){
       grid2= new Ext.grid.EditorGridPanel({
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
                  text:getLabelFromMap('gridNtfcnBottonAgregar',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonAgregar',helpMap,'Crea una Nueva Encuesta'),
                  handler:function(){
                  			agregarEncuesta();
                  }
                  },
                  {
                  text:getLabelFromMap('gridNtfcnBottonEditar',helpMap,'Editar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonEditar',helpMap,'Edita una Encuesta'),
                  handler:function(){
						if (getSelectedKey(grid2, "cdEncuesta") != "") {
                        		agregarEncuesta(getSelectedKey(grid2, "cdEncuesta"));
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                	}
                  },
                   {
                  text:getLabelFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonBorrar',helpMap,'Elimina una Encuesta'),
                  handler:function(){
						if (getSelectedKey(grid2, "cdEncuesta") != "") {
                        		borrar(getSelectedKey(grid2, "cdEncuesta"));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                   }
                  },
                  {
                  text:getLabelFromMap('gridNtfcnBottonExportar',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonExportar',helpMap,'Exporta datos a diversos formatos'),
                    handler:function(){
                        var url = _ACTION_BUSCAR_ENCUESTAS_EXPORTAR + '?pv_dsencuesta_i=' + pv_dsencuesta_i.getValue();
                        showExportDialog(url);
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
            width:500,
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

function borrar(key) {
		if(key != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						pv_cdencuesta_i: key
         			};
         			execConnection(_ACTION_BORRAR_ENCUESTA, _params, cbkConnection);
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
       				dsEncuesta: Ext.getCmp('incisosFormEncuesta').form.findField('pv_dsencuesta_i').getValue()
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