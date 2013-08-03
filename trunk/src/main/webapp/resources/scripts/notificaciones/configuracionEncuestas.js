Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//VALORES DE INGRESO DE LA BUSQUEDA
var desAseguradora = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldDesAseguradora',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('txtFldDesAseguradora',helpMap,'Aseguradora'), 
        id: 'desAseguradora', 
        name: 'desAseguradora',
        allowBlank: true,
        anchor: '100%'
    });
    
var desProducto = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldDesProducto',helpMap,'Producto'),
        tooltip:getToolTipFromMap('txtFldDesProducto',helpMap,'Producto'), 
        id: 'desProducto', 
        name: 'desProducto',
        allowBlank: true,
        anchor: '100%'
    });

var desCuentaCliente = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldDesCuentaCliente',helpMap,'CuentaCliente'),
        tooltip:getToolTipFromMap('txtFldDesCuentaCliente',helpMap,'Cliente Corporativo'), 
        id: 'desCuentaCliente', 
        name: 'desCuentaCliente',
        allowBlank: true,
        anchor: '100%'
    });
    
var desOperacion = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldDesOperacion',helpMap,'Operaci&oacute;n'),
        tooltip:getToolTipFromMap('txtFldDesOperacion',helpMap,'Operaci&oacute;n'), 
        id: 'desOperacion', 
        name: 'desOperacion',
        allowBlank: true,
        anchor: '100%'
    });
       
var desCampana = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldDesCampana',helpMap,'Campa&endop;a'),
        tooltip:getToolTipFromMap('txtFldDesCampana',helpMap,'Campa&endop;a'), 
        id: 'desCampana', 
        name: 'desCampana',
        allowBlank: true,
        anchor: '100%'
    });
    
 var desModulo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldDesModulo',helpMap,'Modulo'),
        tooltip:getToolTipFromMap('txtFldDesModulo',helpMap,'Modulo'), 
        id: 'desModuloId', 
        name: 'desModulo',
        allowBlank: true,
        anchor: '100%'
    });
    
var desEncuesta = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldDesEncuesta',helpMap,'Encuesta'),
        tooltip:getToolTipFromMap('txtFldDesEncuesta',helpMap,'Encuesta'), 
        id: 'desEncuestaId', 
        name: 'desEncuesta',
        allowBlank: true,
        anchor: '100%'
    });
    
var incisosFormNoti = new Ext.FormPanel({
		id: 'incisosFormNoti',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('24',helpMap,'Configuraci&oacute;n de Encuestas')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_CONFIGURACION_ENCUESTAS,
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
        		        				 desNotificacion,
        		        				 desFormato,
        		        				 desMetodoEnvio       		        				
                                       ]
								},
								{
								columnWidth:.4,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
        							text:getLabelFromMap('ntfcnButtonBuscar',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('ntfcnButtonBuscar',helpMap,'Busca Configuaracion de Encuestas'),
        							handler: function() {
				               			if (incisosFormNoti.form.isValid()) {
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
        							tooltip: getToolTipFromMap('ntfcnButtonCancelar',helpMap,'Cancelar busqueda de Configuracion de Encuestas'),                              
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
        header: getLabelFromMap('cmDsFormatoOrdenNtfcn',helpMap,'Formato'),
        tooltip: getToolTipFromMap('cmDdsFormatoNtfcn',helpMap,'Columna Formato'),
        dataIndex: 'dsFormatoOrden',
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
        dataIndex: 'cdNotificacion',
        hidden :true
        },
        {
        dataIndex: 'cdFormatoOrden',
        hidden :true
        },
        {
        dataIndex: 'cdMetEnv',
        hidden :true
        },
		{
        dataIndex: 'cdProceso',
        hidden :true
        }        
]);

var grid2;

function createGrid(){
       grid2= new Ext.grid.EditorGridPanel({
       		id: 'grid2',
            el:'gridNotificaciones',
            store:storeGrillaNoti,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            reader:jsonGrillaNoti,
            border:true,
            cm: cm,
            clicksToEdit:1,
	        successProperty: 'success',
            buttonAlign: "center",
            buttons:[
                  {
                  text:getLabelFromMap('gridNtfcnBottonAgregar',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonAgregar',helpMap,'Crea una Nueva Notificacion'),
                  handler:function(){editar()}
                  },
                  {
                  text:getLabelFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar una Notificacion'),
                  handler:function(){
						if (getSelectedKey(grid2, "cdNotificacion") != "") {
                        		borrar(getSelectedKey(grid2, "cdNotificacion"));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                   }
                  },
                  {
                  text:getLabelFromMap('gridNtfcnBottonEditar',helpMap,'Editar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonEditar',helpMap,'Editar una Notificacion'),
                  handler:function(){
						if (getSelectedKey(grid2, "cdNotificacion") != "") {
                        		editar(getSelectedKey(grid2, "cdNotificacion"));
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                        }
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

function borrarConfigEncuestas(key) {
		if(key != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						cdNotificacion: key
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
       		dsNotificacion: Ext.getCmp('incisosFormNoti').form.findField('desNotificacion').getValue(),
       		dsFormatoOrden: Ext.getCmp('incisosFormNoti').form.findField('desFormato').getValue(),
       		dsMetEnv: Ext.getCmp('incisosFormNoti').form.findField('desMetodoEnvio').getValue()
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