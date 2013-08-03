Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//VALORES DE INGRESO DE LA BUSQUEDA
var desTipoArchivo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldAdmAtrDeFax',helpMap,'Tipo de Archivo'),
        tooltip:getToolTipFromMap('txtFldAdmAtrDeFax',helpMap,'Tipo de Archivo a buscar'), 
        id: 'desTipoArchivoId', 
        name: 'desTipoArchivo',
        allowBlank: true,
        anchor: '100%'
    });
    
       
var formAdmAtrFax = new Ext.FormPanel({
		id: 'formAdmAtrFax',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('24',helpMap,'Consulta de Administracion Atributos de Fax')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_ADMINISTRACION_ATRIBUTOS_FAX,
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
        		        				 desTipoArchivo
                                       ]
								},
								{
								columnWidth:.4,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
        							text:getLabelFromMap('admAtrDeFaxButtonBuscar',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('admAtrDeFaxButtonBuscar',helpMap,'Busca un Grupo Atributos de Fax'),
        							handler: function() {
				               			if (formAdmAtrFax.form.isValid()) {
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
        							text:getLabelFromMap('admAtrDeFaxButtonCancelar',helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('admAtrDeFaxButtonCancelar',helpMap,'Cancelar busqueda de Grupo Atributos de Fax'),                              
        							handler: function() {
        								formAdmAtrFax.form.reset();
        							}
        						}]
        					}]
        				}]	            
        
});   


// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmdsAtributoAtrDeFax',helpMap,'Atributo'),
        tooltip: getToolTipFromMap('cmdsAtributoAtrDeFax',helpMap,'Atributo'),
        dataIndex: 'dsAtributo',
        width: 128,
        sortable: true
        },
        {
        header: getLabelFromMap('cmDsFormatoAtrDeFax',helpMap,'Formato'),
        tooltip: getToolTipFromMap('cmDsFormatoAtrDeFax',helpMap,'Formato'),
        dataIndex: 'dsFormato',
        width: 218,
        sortable: true
        },
        {
        header: getLabelFromMap('cmDsMinimoAtrDeFax',helpMap,'Minimo'),
        tooltip: getToolTipFromMap('cmDsMinimoAtrDeFax',helpMap,'Minimo'),
        dataIndex: 'dsMinimo',
        width: 138,
        sortable: true,
        align: 'center'
        },
        {
        header: getLabelFromMap('cmDsMaximoAtrDeFax',helpMap,'Maximo'),
        tooltip: getToolTipFromMap('cmDsMaximoAtrDeFax',helpMap,'Maximo'),
        dataIndex: 'dsMaximo',
        width: 138,
        sortable: true,
        align: 'center'
        },
        {
        header: getLabelFromMap('cmDsTablaAtrDeFax',helpMap,'Tabla'),
        tooltip: getToolTipFromMap('cmDsTablaAtrDeFax',helpMap,'Tabla'),
        dataIndex: 'dsTabla',
        width: 138,
        sortable: true,
        align: 'center'
        },
        {
        header: getLabelFromMap('cmDsObligatorioAtrDeFax',helpMap,'Obligatorio'),
        tooltip: getToolTipFromMap('cmDsObligatorioAtrDeFax',helpMap,'Obligatorio'),
        dataIndex: 'dsObligatorio',
        width: 138,
        sortable: true,
        align: 'center'
        },
        {
        header: getLabelFromMap('cmStatusAtrDeFax',helpMap,'Status'),
        tooltip: getToolTipFromMap('cmStatusAtrDeFax',helpMap,'Status'),
        dataIndex: 'status',
        width: 138,
        sortable: true,
        align: 'center'
        }
        /*,CDTIPOAR, CDATRIBU
		{
        dataIndex: 'cdTipoAr',
        hidden :true
        }, 
        {
        dataIndex: 'cdAtribu',
        hidden :true
        }
              */  
]);

var grid2;

function createGrid(){
       grid2= new Ext.grid.GridPanel({
       		id: 'grid2',
            el:'gridElementos',
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
                  text:getLabelFromMap('gridAtrDeFaxBottonAgregar',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('gridAtrDeFaxBottonAgregar',helpMap,'Crea una Nueva Atributo'),
                  handler:function(){editar()}
                  },
                  {
                  text:getLabelFromMap('gridAtrDeFaxBottonBorrar',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('gridAtrDeFaxBottonBorrar',helpMap,'Eliminar un Atributo'),
                  handler:function(){
						if ((getSelectedKey(grid2, "cdTipoAr") != "")&&(getSelectedKey(grid2, "cdAtribu") != "")) {
                        		borrar(getSelectedRecord(grid2));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                                }
                   }
                  },
                  {
                  text:getLabelFromMap('gridAtrDeFaxBottonEditar',helpMap,'Editar'),
                  tooltip: getToolTipFromMap('gridAtrDeFaxBottonEditar',helpMap,'Editar una Notificacion'),
                  handler:function(){
						if ((getSelectedKey(grid2, "cdTipoAr") != "")&&(getSelectedKey(grid2, "cdAtribu") != "")) {
                        		editar(getSelectedRecord(grid2));
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                	}
                  },
                  {
                  text:getLabelFromMap('gridAtrDeFaxBottonRegresar',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('gridAtrDeFaxBottonRegresar',helpMap,'Volver a la Pantalla Anterior')
                  }
                  ],
            width:500,
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:10,
					store: storeGrillaNoti,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
   grid2.render()
}

  
formAdmAtrFax.render();
createGrid();

function borrar(record) {
        if(record.get('cdTipoAr') != "" && record.get('cdAtribu') != "")
        {
            Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
            {
                if (btn == "yes")
                {
                    var _params = {
                              cdTipoAr: record.get('cdTipoAr'),
                              cdAtribu: record.get('cdAtribu')
                    };
                    execConnection(_ACTION_BORRAR_ADMINISTRACION_ATRIBUTOS_FAX, _params, cbkConnection);
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
       		dsNotificacion: Ext.getCmp('formAdmAtrFax').form.findField('desTipoArchivo').getValue()
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
