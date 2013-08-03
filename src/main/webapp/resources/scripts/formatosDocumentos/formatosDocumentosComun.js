
var desNomFormato = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('desNomFormato',helpMap,'Formatos'),
        tooltip:getToolTipFromMap('desNomFormato',helpMap,'Nombre del formato del Documentos'),
        hasHelpIcon:getHelpIconFromMap('desNomFormato',helpMap),
        Ayuda:getHelpTextFromMap('desNomFormato',helpMap),
        id: 'desNomFormato', 
        name: 'desNomFormato',
        allowBlank: true,
        width: 250
    });


// Definicion de las columnas de la grilla
var cmComun = new Ext.grid.ColumnModel([
        {
        dataIndex: 'cdFormatoDoc',
        hidden :true
        },
        {
        header:getLabelFromMap('cmDsNomFormatoDoc',helpMap,'Nombre Formato'),
        tooltip:getToolTipFromMap('cmDsNomFormatoDoc',helpMap,'Nombre del formato documento'),
        dataIndex: 'dsNomFormatoDoc',
        sortable: true,
        width: 420
        }/*,
        {
        header:getLabelFromMap('cmDsFormatoDoc',helpMap,'Desc Formato'),
        tooltip:getToolTipFromMap('cmDsFormatoDoc',helpMap,'Contenido del formato'),
        dataIndex: 'dsFormatoDoc',
        sortable: true,
        width: 180
        }*/
]);
var gridComun;

function createGridFrmDoc(){
    if (gridComun != null && gridComun != undefined) return;
       gridComun= new Ext.grid.GridPanel({
            id: 'grid22',
            el:'gridElementos',
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            store:storeGrillaFrmDoc,
            reader:jsonGrillaFrmDoc,
            border:true,
            cm: cmComun,
            clicksToEdit:1,
            successProperty: 'success',
            buttonAlign:'center',
            buttons:[
                  {
                  text:getLabelFromMap('gridFrmDocButtonAgregar',helpMap,'Agregar'),
                  tooltip:getToolTipFromMap('gridFrmDocButtonAgregar',helpMap,'Agregar un nuevo formato de Documento'),
                  handler:function(){ editarFrmDoc()}
                  },
                 
                
                  {
                  text:getLabelFromMap('gridFrmDocButtonEditar',helpMap,'Editar'),
                  tooltip:getToolTipFromMap('gridFrmDocButtonEditar',helpMap,'Editar un formato de Documento'),
                  handler:function(){
                        if (getSelectedKey(gridComun, "cdFormatoDoc") != "") {
                                editarFrmDoc(getSelectedKey(gridComun, "cdFormatoDoc"));
                        } else {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                        }
                   }
                  },
                    {
                  text:getLabelFromMap('gridFrmDocButtonBorrar',helpMap,'Eliminar'),
                  tooltip:getToolTipFromMap('gridFrmDocButtonBorrar',helpMap,'Eliminar un formato de Documento'),
                  handler:function(){
                        if (getSelectedKey(gridComun, "cdFormatoDoc") != "") {
                            borrarFrmDoc(getSelectedKey(gridComun, "cdFormatoDoc"));
                        }
                        else{
                            Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                        }
                    }
                  },
                   {
                  text:getLabelFromMap('gridFrmDocButtonRegresar',helpMap,'Regresar'),
                  tooltip:getToolTipFromMap('gridFrmDocButtonRegresar',helpMap,'Regresar a la pantalla anterior'),
                  handler:function(){
                        if (vistaTipo!=1){
                        	win_flotante.hide();
                         }
                   }                  
                  },
                  {
                  text:getLabelFromMap('gridFrmDocButtonExportar',helpMap,'Exportar'),
                  tooltip:getToolTipFromMap('gridFrmDocButtonExportar',helpMap,'Exportar un formato de Documento'),
                  handler:function(){
                        var url = _ACTION_EXPORTAR_FORMATO_DOCUMENTOS + '?dsNomFormato=' + desNomFormato.getValue();
                        showExportDialog( url );
                    }                 
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
                    store: storeGrillaFrmDoc,
                    displayInfo: true,
                    displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
                    emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
                })          
        });
   gridComun.render();
};
       
var incisosForm = new Ext.FormPanel({
		id: 'incisosForm',
        el:'formDocumentos',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosForm',helpMap,'Formato de Documentos')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_FORMATOS_DOCUMENTOS,
        width: 505,
        height:150,
        items: [{
        		layout:'form',
				border: false,
				items:[{
                		bodyStyle:'background: white',
        		        labelWidth: 100,
        		         title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
                        layout: 'form',
        				frame:true,
        		       	baseCls: '',
        		       	buttonAlign: "center",
                		items: [{
            		        	layout:'column',
        	 				    border:false,
        	 				    columnWidth: 1,
            		    		items:[{
        					    	columnWidth:.8,
                    				layout: 'form',
        	                		border: false,
            		        		items:[       		        				
            		        				 desNomFormato       		        				
                                           ]
        							},
        							{
        							columnWidth:.2,
                    				layout: 'form'
                    				}]
                    			}],
                        buttons:[{
        						text:getLabelFromMap('frmtDcmntButtonBuscar',helpMap,'Buscar'),
        						tooltip:getToolTipFromMap('frmtDcmntButtonBuscar',helpMap,'Buscar formato de documentos'),
        						handler: function() {
        	               			if (incisosForm.form.isValid()) {
                                           if (gridComun!=null) {
                                            reloadGridFrmDoc();
                                           } else {
                                            createGridFrmDoc();
                                           }
            						} else{
        								Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
        							}
        						}
        						},{
        						text:getLabelFromMap('frmtDcmntButtonCancel',helpMap,'Cancelar'),
        						tooltip:getToolTipFromMap('frmtDcmntButtonCancel',helpMap,'Cancelar Buscar formato de documentos'),                             
        						handler: function() {
        							incisosForm.form.reset();
        					           }
                				}]
            			}]
        		}]
});   


function borrarFrmDoc (key) {
	if(key != "")
	{
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
		{
	        if (btn == "yes")
	        {
				var _params = {
						cdFormato: key
				};
				execConnection(_ACTION_BORRAR_FORMATOS_DOCUMENTOS, _params, cbkConnection);
           }
		}
		)
	}else{
			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
	}
};

function cbkConnection(_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message,function(){reloadGrid();});
		
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400081', helpMap,'El formato se eliminó con éxito'),function(){reloadGridFrmDoc();});
		
		
	}
};

function reloadGridFrmDoc(){
		var _params = {
        		dsNomFormato: Ext.getCmp('incisosForm').form.findField('desNomFormato').getValue()
		};
		reloadComponentStore(Ext.getCmp('grid22'), _params, cbkReloadFrmDoc);
};

function cbkReloadFrmDoc(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}else{
		dsNombreFormato.reload({
           // params: { dsNomFormato:action.result.data.dsFormatoOrden},
            callback: function (r, o, success) {
            		if (success) {
            			//comboFormato.setValue(cdFormato_Orden);
            		}
            }
        });
	}
};