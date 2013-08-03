
Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//VALORES DE INGRESO DE LA BUSQUEDA
var desNotificacion = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('desNotificacion',helpMap,'Notificaci&oacute;n'),
        tooltip:getToolTipFromMap('desNotificacion',helpMap,'Asunto de la Notificaci&oacute;n'), 
        hasHelpIcon:getHelpIconFromMap('desNotificacion',helpMap),
        Ayuda:getHelpTextFromMap('desNotificacion',helpMap),
        id: 'desNotificacion', 
        name: 'desNotificacion',
        allowBlank: true,
        width: 210
    });
    
    
    
    var desRegion = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('desRegion',helpMap,'Regi&oacute;n'),
        tooltip:getToolTipFromMap('desRegion',helpMap,'Regi&oacute;n'), 
        hasHelpIcon:getHelpIconFromMap('desRegion',helpMap),
        Ayuda:getHelpTextFromMap('desRegion',helpMap),
        id: 'desRegion', 
        name: 'desRegion',
        allowBlank: true,
        width: 210
    });
    
var desProcBO = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('desProcBO',helpMap,'Proceso Back Office'),
        tooltip:getToolTipFromMap('desProcBO',helpMap,'Proceso Back Office'), 
        hasHelpIcon:getHelpIconFromMap('desProcBO',helpMap),
        Ayuda:getHelpTextFromMap('desProcBO',helpMap),
        id: 'desProcBO', 
        name: 'desProcBO',
        allowBlank: true,
        width: 210
    });

    
    var desEdoCaso = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('desEdoCaso',helpMap,'Estado del Caso'),
        tooltip:getToolTipFromMap('desEdoCaso',helpMap,'Estado del Caso'), 
        hasHelpIcon:getHelpIconFromMap('desEdoCaso',helpMap),
        Ayuda:getHelpTextFromMap('desEdoCaso',helpMap),
        id: 'desEdoCaso', 
        name: 'desEdoCaso',
        allowBlank: true,
        width: 210
    });
    
var desMetodoEnvio = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('desMetodoEnvio',helpMap,'M&eacute;todo Env&iacute;o'),
        tooltip:getToolTipFromMap('desMetodoEnvio',helpMap,'M&eacute;todo Env&iacute;o de la Notificaci&oacuten'), 
        hasHelpIcon:getHelpIconFromMap('desMetodoEnvio',helpMap),
        Ayuda:getHelpTextFromMap('desMetodoEnvio',helpMap),
        id: 'desMetodoEnvio', 
        name: 'desMetodoEnvio',
        allowBlank: true,
        width: 210
    });
       
var incisosFormNoti = new Ext.FormPanel({
		id: 'incisosFormNoti',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosFormNoti',helpMap,'Consulta de Notificaci&oacute;n')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_NOTIFICACIONES,
        width: 500,
        height:250,
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
						    	columnWidth:.7,
                				layout: 'form',
		                		border: false,
        		        		items:[       		        				
        		        				 desNotificacion,
        		        				 desRegion,
        		        				 desProcBO,
        		        				 desEdoCaso,
        		        				 desMetodoEnvio       		        				
                                       ]
								},
								{
								columnWidth:.3,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
        							text:getLabelFromMap('ntfcnButtonBuscar',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('ntfcnButtonBuscar',helpMap,'Busca un Grupo de Notificaciones'),
        							handler: function() {
        								if (incisosFormNoti.form.isValid()) {
                                               if (grid2!=null) {
                                                reloadGridNoti();
                                               } else {
                                                createGrid();
                                               }
                						} else{
                							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
										}
									}
        							},{
        							text:getLabelFromMap('ntfcnButtonCancelar',helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('ntfcnButtonCancelar',helpMap,'Cancelar busqueda de Notificaciones'),                              
        							handler: function() {
        								incisosFormNoti.form.reset();
        							}
        						}]
        					}]
        				}]	            
        
});   


// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmDsNotificacionNtfcn',helpMap,'Notificaci&oacute;n'),
        tooltip: getToolTipFromMap('cmDsNotificacionNtfcn',helpMap,'Columna Notificaci&oacute;n'),
        dataIndex: 'dsNotificacion',
        width: 128,
        sortable: true
        },
        {
        header: getLabelFromMap('cmDsRegionNtfcn',helpMap,'Regi&oacute;n'),
        tooltip: getToolTipFromMap('cmDsRegionNtfcn',helpMap,'Columna Regi&oacute;n'),
        dataIndex: 'dsRegion',
        width: 218,
        sortable: true
        },
        {
        header: getLabelFromMap('cmDsProcesoNtfcn',helpMap,'Proceso Back Office'),
        tooltip: getToolTipFromMap('cmDsProcesoNtfcn',helpMap,'Columna Proceso Back Office'),
        dataIndex: 'dsProceso',
        width: 218,
        sortable: true
        },
        {
        header: getLabelFromMap('cmDsEstadoNtfcn',helpMap,'Estado del Caso'),
        tooltip: getToolTipFromMap('cmDsEstadoNtfcn',helpMap,'Columna Estado del Caso'),
        dataIndex: 'dsEstado',
        width: 218,
        sortable: true
        },
        {
        header: getLabelFromMap('cmDsMetEnvNtfcn',helpMap,'M&eacute;todo Env&iacute;o'),
        tooltip: getToolTipFromMap('cmDsMetEnvNtfcn',helpMap,'Columna M&eacute;todo Env&iacute;o'),
        dataIndex: 'dsMetEnv',
        width: 138,
        sortable: true,
        align: 'center'
        },
        {
        header:'cdNotificacion',
        dataIndex: 'cdNotificacion',
        hidden :true
        },
        {
        header:'cdMetEnv',
        dataIndex: 'cdMetEnv',
        hidden :true
        },
        {
        header:'cdRegion',
        dataIndex: 'cdRegion',
        hidden :true
        },
        {
        header:'cdEstado',
        dataIndex: 'cdEstado',
        hidden :true
        },
        {
        header:'dsMensaje',
        dataIndex: 'dsMensaje',
        hidden :true
        }    
]);

var grid2;

function createGrid(){
       grid2= new Ext.grid.GridPanel({
       		id: 'grid2',
            el:'gridNotificaciones',
            store:storeGrillaNoti,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            reader:jsonGrillaNoti,
            border:true,
            cm: cm,
            loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
            clicksToEdit:1,
	        successProperty: 'success',
            buttonAlign: "center",
            buttons:[
                  {
                  text:getLabelFromMap('gridNtfcnBottonAgregar',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonAgregar',helpMap,'Crea una Nueva Notificaci&oacute;n'),
                  handler:function(){editar()}
                  },
                 
                  {
                  text:getLabelFromMap('gridNtfcnBottonEditar',helpMap,'Editar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonEditar',helpMap,'Editar una Notificaci&oacute;n'),
                  handler:function(){
						if (getSelectedKey(grid2, "cdNotificacion") != "") {
                        		editar(getSelectedKey(grid2, "cdNotificacion"),getSelectedKey(grid2, "cdRegion"),getSelectedKey(grid2, "cdMetEnv"));
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                        }
                	}
                  },
                   {
                  text:getLabelFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar una Notificaci&oacute;n'),
                  handler:function(){
						if (getSelectedKey(grid2, "cdNotificacion") != "") {
                        		borrar(getSelectedKey(grid2, "cdNotificacion"));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                   }
                  }
                  /*,{
                  	text:getLabelFromMap('gridNtfcnBottonRegresar',helpMap,'Regresar'),
                  	tooltip: getToolTipFromMap('gridNtfcnBottonRegresar',helpMap,'Volver a la Pantalla Anterior')
                  }*/
                  ],
            width:500,
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store: storeGrillaNoti,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
   grid2.render()
}

 
incisosFormNoti.render();
createGrid();

function borrar(key) {
		if(key != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						cdNotificacion: key
         			};
         			execConnection(_ACTION_BORRAR_NOTIFICACIONES, _params, cbkConnection);
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
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400081', helpMap,'Los datos se eliminaron con &eacute;xito'), function(){reloadGridNoti()});
	}
}      
});

function reloadGridNoti(){
	var _params = {
       		dsNotificacion: Ext.getCmp('incisosFormNoti').form.findField('desNotificacion').getValue(),
       		dsRegion: Ext.getCmp('incisosFormNoti').form.findField('desRegion').getValue(),
       		dsProceso: Ext.getCmp('incisosFormNoti').form.findField('desProcBO').getValue(),
       		dsEdoCaso: Ext.getCmp('incisosFormNoti').form.findField('desEdoCaso').getValue(),
       		dsMetEnv: Ext.getCmp('incisosFormNoti').form.findField('desMetodoEnvio').getValue()
	};
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
}
function cbkReload(_r, _options, _success, _store) {
 
     	if (!_success) {
	    _store.removeAll();
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
      	}
  
}
