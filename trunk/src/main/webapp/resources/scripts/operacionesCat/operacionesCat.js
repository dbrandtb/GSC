Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
		 	
//VALORES DE INGRESO DE LA BUSQUEDA
var dsUniEco = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsUniEcoID',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('dsUniEcoID',helpMap,'Aseguradora'), 
        id: 'dsUniEcoID', 
        name: 'dsUniEco',
        allowBlank: true,
        width: 200
        //anchor: '100%'
    });
    
var dsElemento = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsElementoID',helpMap,'Grupo'),
        tooltip:getToolTipFromMap('dsElementoID',helpMap,'Grupo'), 
        id: 'dsElementoID', 
        name: 'dsElemento',
        allowBlank: true,
        width: 200
        //anchor: '100%'
    });

var dsGuion = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsGuionID',helpMap,'Gui&oacute;n'),
        tooltip:getToolTipFromMap('dsGuionID',helpMap,'Gui&oacute;n'), 
        id: 'dsGuionID', 
        name: 'dsGuion',
        allowBlank: true,
        width: 200
        //anchor: '100%'
    });
       
       
var dsProceso = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsProcesoID',helpMap,'Proceso'),
        tooltip:getToolTipFromMap('dsProcesoID',helpMap,'Proceso'), 
        id: 'dsProcesoID', 
        name: 'dsProceso',
        allowBlank: true,
        width: 200
        //anchor: '100%'
    });       
       
 
var dsProceso = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsOperacionID',helpMap,'Operaci&oacute;n'),
        tooltip:getToolTipFromMap('dsOperacionID',helpMap,'Operaci&oacute;n'), 
        id: 'dsOperacionID', 
        name: 'dsProceso',
        allowBlank: true,
        width: 200
        //anchor: '100%'
    });       
             
var dsTipGuion = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsTipGuionID',helpMap,'Tipo de Gui&oacute;n'),
        tooltip:getToolTipFromMap('dsTipGuionID',helpMap,'Tipo de Gui&oacute;n'), 
        id: 'dsTipGuionID', 
        name: 'dsTipGuion',
        allowBlank: true,
        width: 200
        //anchor: '100%'
    });    

             
var dsRamo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsRamoID',helpMap,'Producto'),
        tooltip:getToolTipFromMap('dsRamoID',helpMap,'Producto'), 
        id: 'dsRamoID', 
        name: 'dsRamo',
        allowBlank: true,
        width: 200
        //anchor: '100%'
    });  
    
    
var incisosForm = new Ext.FormPanel({
		id: 'incisosForm',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosForm',helpMap,'Gui&oacute;n de llamada Entrada/Salida')+'</span>',
        //iconCls:'logo',
        bodyStyle:'background: white',
        //buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_GUIONES,
        width: 700,
        waitMsgTarget : true,
        
        //height:200,
        autoHeight: true,
         items: [{
        		layout:'form',
				border: false,
				items:[{
        		//bodyStyle:'background: white',
		        //labelWidth: 100,
                //layout: 'form',
                title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
				//frame:true,
		       	baseCls: '',
		       	//buttonAlign: "center",
        		        items: [{
        		        	layout:'column',
        		        	baseCls: '',
        		        	
		 				    border:false,
		 				    columnWidth: 1,
        		    		items:[{
						    	columnWidth:.5,
                				layout: 'form',
		                		border: false,
        		        		items:[       		        				
        		        				 dsUniEco,
        		        				 dsElemento,
        		        				 dsGuion    		        				
                                       ]
								},
								{
								columnWidth:.5,
                				layout: 'form',
                				items:[       		        				
        		        				 dsProceso,
        		        				 dsTipGuion,
        		        				 dsRamo    		        				
                                       ]
                				}]
                			}],
                			buttonAlign: "center",
                			buttons:[{
        							text:getLabelFromMap('ntfcnButtonBuscar',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('ntfcnButtonBuscar',helpMap,'Busca un Grupo de Guiones'),
        							handler: function() {
				               			if (incisosForm.form.isValid()) {
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
        							tooltip: getToolTipFromMap('ntfcnButtonCancelar',helpMap,'Cancelar b&uacute;squeda de Guiones'),                              
        							handler: function() {
        								incisosForm.form.reset();
        							}
        						}]
        					}]
        				}]	            
        
});   


// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmDsUniEco',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('cmDsUniEcoNtfcn',helpMap,'Columna Aseguradora'),
        dataIndex: 'dsUniEco',
        width: 100,
        sortable: true
        },
        {
        header: getLabelFromMap('cmDsRamoNtfcn',helpMap,'Producto'),
        tooltip: getToolTipFromMap('cmDsRamoNtfcn',helpMap,'Columna Producto'),
        dataIndex: 'dsRamo',
        width: 95,
        sortable: true
        },
        {
        header: getLabelFromMap('cmDsElementoNtfcn',helpMap,'Grupo'),
        tooltip: getToolTipFromMap('cmDsElementoNtfcn',helpMap,'Columna Grupo'),
        dataIndex: 'dsElemen',
        width: 100,
        sortable: true
        //align: 'center'
        },
        {
        header: getLabelFromMap('cmDsGuionNtfcn',helpMap,'Gui&oacute;n'),
        tooltip: getToolTipFromMap('cmDsGuionNtfcn',helpMap,'Columna Gui&oacute;n'),
        dataIndex: 'dsGuion',
        width: 90,
        sortable: true
        //align: 'center'
        },
         {
        header: getLabelFromMap('cmDsTipGuiNtfcn',helpMap,'Tipo de Gui&oacute;n'),
        tooltip: getToolTipFromMap('cmDsTipGuiNtfcn',helpMap,'Columna Tipo de Gui&oacute;n'),
        dataIndex: 'dsTipGuion',
        width: 100,
        sortable: true
       // align: 'center'
        },
        {
        header: getLabelFromMap('cmDsProcesoNtfcn',helpMap,'Operaci&oacute;n'),
        tooltip: getToolTipFromMap('cmDsProcesoNtfcn',helpMap,'Operaci&oacute;n'),
        dataIndex: 'dsProceso',
        width: 100,
        sortable: true
        //align: 'center'
        },
        {
        header: getLabelFromMap('cmStatusNtfcn',helpMap,'Estado'),
        tooltip: getToolTipFromMap('cmStatusNtfcn',helpMap,'Estado'),
        dataIndex: 'status',
        width: 100,
        sortable: true
        //align: 'center'
        },
        {
        dataIndex: 'cdUniEco',
        hidden :true
        },
        {
        dataIndex: 'cdRamo',
        hidden :true
        },
        {
        dataIndex: 'cdElemento',
        hidden :true
        },
		{
        dataIndex: 'cdProceso',
        hidden :true
        },
		{
        dataIndex: 'cdGuion',
        hidden :true
        }         
]);

var grid2;


function createGrid(){
       grid2= new Ext.grid.GridPanel({
       		id: 'grid2',
            el:'gridGuiones',
            store:storeGrilla,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            //title: '<span style="height:10"></span>',
            reader:jsonGrilla,
            border:true,
            buttonAlign:'center',
            loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
            cm: cm,
            clicksToEdit:1,
	        successProperty: 'success',
            buttons:[
                  {
                  text:getLabelFromMap('gridNtfcnBottonAgregar',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonAgregar',helpMap,'Crear un Nuevo Gui&oacute;n'),
                  handler:function(){agregar()}
                  },
                  {
                  text:getLabelFromMap('gridNtfcnBottonEditar',helpMap,'Editar'),
                  tooltip: getToolTipFromMap('motCancBtnEditar',helpMap,'Editar una Notificaci&oacute;n'),
                  handler:function(){
						if (getSelectedRecord()!=null) {
                        		editar(getSelectedRecord(grid2));
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                	}
                  },
                  {
                  text:getLabelFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar un Gui&oacute;n'),
                  handler:function(){
						if(getSelectedRecord()!=null){
                					borrar(getSelectedRecord());
                					reloadGrid();
                			}
                			else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
                   }
                  },
                  
                  {
                  text:getLabelFromMap('gridNtfcnBottonExportar',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonExportar',helpMap,'Exportar datos en diversos Formatos'),
                   handler:function(){
                        var url = _ACTION_EXPORTAR_GUION + '?dsUniEco=' + dsUniEco.getValue() + '&dsElemento=' + dsElemento.getValue() + '&dsGuion=' + dsGuion.getValue() + '&dsProceso=' + dsProceso.getValue() + '&dsTipGuion=' + dsTipGuion.getValue() + '&dsRamo=' + dsRamo.getValue();
                        showExportDialog( url );
                    }   
                  }/*,
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
					pageSize:itemsPerPage,
					store: storeGrilla,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
   grid2.render()
}

  
incisosForm.render();
createGrid();

function borrar(record) {
			var conn = new Ext.data.Connection();
		
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
          	       if (btn == "yes")
          	        {
          	        conn.request({
				    url: _ACTION_BORRAR_GUION,
				    method: 'POST',
				    params: {
				    			"cdUniEco": record.get("cdUniEco"),
				    			"cdRamo": record.get("cdRamo"),
				    			"cdElemento": record.get("cdElemento"),
				    			"cdProceso": record.get("cdProceso"),
				    			"cdGuion": record.get("cdGuion")
				    },
				    success: function() {
				    	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),'Gui&oacute;n eliminado.');
				    	reloadGrid();
				    },
			    	failure: function() {
			         Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),'Problemas al eliminar');
			     	}
					});
               	}
			})
         };   
function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()});
	}
}
      
});


function getSelectedRecord(){
    var m = Ext.getCmp('grid2').getSelections();
    if (m.length == 1 ) {
       return m[0];
       }
   };
   
function reloadGrid(){
	var _params = {
       		dsUniEco: Ext.getCmp('incisosForm').form.findField('dsUniEco').getValue(),
       		dsElemento: Ext.getCmp('incisosForm').form.findField('dsElemento').getValue(),
       		dsGuion: Ext.getCmp('incisosForm').form.findField('dsGuion').getValue(),
       		dsProceso: Ext.getCmp('incisosForm').form.findField('dsProceso').getValue(),
       		dsTipGuion: Ext.getCmp('incisosForm').form.findField('dsTipGuion').getValue(),
       		dsRamo: Ext.getCmp('incisosForm').form.findField('dsRamo').getValue()
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


/* ******************************************************************************************************************/
function reloadGridDialogo(_code){
var _params = {cdGuion: _code};
	
reloadComponentStore(Ext.getCmp('gridDialogosId'), _params, cbkReloadDialog);
/*gridDialogo.store.load({params : {  
                                                                          cdUniEco: record.get("cdUniEco"),
                                                                          cdGuion: record.get("cdGuion"),
                                                                          cdElemento: record.get("cdElemento"),
                                                                          cdRamo: record.get("cdRamo"),
                                                                          cdProceso: record.get("cdProceso")
                                                                      }*//*,
                     callback: function(r, options, success){
						if(!success){
					        Ext.MessageBox.alert('Buscar',gridDialogo.reader.jsonData.mensajeError);
		                    gridDialogo.store.removeAll();
		                    gridDialogo.getBottomToolbar().updateInfo();
							}
		}*/
                                                       
                                                       
 function cbkReloadDialog(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}                                                     //});
}                                                       
/* ********************************************************************************************************************/
