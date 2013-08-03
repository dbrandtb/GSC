
Ext.onReady(function(){ 
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";


var desStatus = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('desStatusId',helpMap,'Estado'),
        tooltip:getToolTipFromMap('desStatusId',helpMap,'Nombre Estado'),
        hasHelpIcon:getHelpIconFromMap('desStatusId',helpMap),								 
        Ayuda: getHelpTextFromMap('desStatusId',helpMap,''),	
        id: 'desStatusId', 
        name: 'desStatus',
        allowBlank: true,
        anchor: '100%'
    });


      
var incisosFormEstatus = new Ext.FormPanel({
        id: 'incisosFormEstatus',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosFormEstatus',helpMap,'Administrar Estado de Casos')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_STATUS_CASOS,
        width: 520,
        height:150,
        items: [{
                layout:'form',
                border: false,
                items:[{
                labelWidth: 100,
                layout: 'form',
                
                title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
                frame:true,
                baseCls: '',
                buttonAlign: "center",
                        items: [
                            {
                                layout:'column',
                                border:false,
                                columnWidth: 1,
                                items:[
                                    {
                                        columnWidth:.1,
                                        layout: 'form'
                                    },
                                    {
                                        columnWidth:.6,
                                        layout: 'form',
                                        border: false,
                                        items:[                                     
                                                desStatus                                    
                                            ]
                                    },
                                    {
                                        columnWidth:.2,
                                        layout: 'form'
                                    }
                                ]
                                
                            }],
                            buttons:[{
                                    text:getLabelFromMap('frmtEsttCsButtonBuscar',helpMap,'Buscar'),
                                    tooltip:getToolTipFromMap('frmtEsttCsButtonBuscar',helpMap,'Busca Estado'),
                                    handler: function() {
                                        if (incisosFormEstatus.form.isValid()) {
                                               if (gridEstatus!=null) {
                                                reloadGrid();
                                               } else {
                                                createGrid();
                                               }
                                        } else{
                                            Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                                        }
                                    }
                                    },{
                                    text:getLabelFromMap('frmtEsttCsButtonCancelar',helpMap,'Cancelar'),
                                    tooltip:getToolTipFromMap('frmtEsttCsButtonCancelar',helpMap,'Cancela B&uacute;squeda Estado'),                             
                                    handler: function() {
                                        incisosFormEstatus.form.reset();
                                    }
                                }]
                            }]
                        }]              
        
});   

// Definicion de las columnas de la grilla
var cmEstatus = new Ext.grid.ColumnModel([
        {
        dataIndex: 'codStatus',
        hidden :true
        },
        {
        header:getLabelFromMap('cmDsEttsEsttCs',helpMap,'Estado'),
        tooltip:getToolTipFromMap('cmDsEttsEsttCs',helpMap,'Nombre del formato documento'),
        dataIndex: 'desStatus',
        sortable: true,
        width: 320
        },
        {
        header:getLabelFromMap('cmDsIndAviso',helpMap,'Indicador de Aviso'),
        tooltip:getToolTipFromMap('cmDsIndAviso',helpMap,'Indicador de Aviso'),
        dataIndex: 'desIndAviso',
        sortable: true,
        width: 200
        }
]);

var gridEstatus;

function createGrid(){
       gridEstatus= new Ext.grid.GridPanel({
            id: 'grid2',
            el:'gridElementos',
            store:storeGrillaStatus,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            reader:jsonGrillaStatus,
            border:true,
            cm: cmEstatus,
            loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},            
            clicksToEdit:1,
            successProperty: 'success',
            buttonAlign: "center",
            buttons:[
                  {
                  text:getLabelFromMap('gridEsttCsBottonAgregar',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('gridEsttCsBottonAgregar',helpMap,'Crea un Estado'),
                  handler:function(){editarSttsCs("")}
                  },
                  {
                  text:getLabelFromMap('gridEsttCsBottonEditar',helpMap,'Editar'),
                  tooltip: getToolTipFromMap('gridEsttCsBottonEditar',helpMap,'Editar un Estado'),
                  handler:function(){
                        if (getSelectedKey(gridEstatus, "codStatus") != "") {
                                editarSttsCs(getSelectedKey(gridEstatus, "codStatus"));
                        } else {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                        }
                    }
                  },
                  {
                  text:getLabelFromMap('gridEsttCsBottonBorrar',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('gridEsttCsBottonBorrar',helpMap,'Eliminar un Estado'),
                  handler:function(){
                        if (getSelectedKey(gridEstatus, "codStatus") != "") {
                                borrar(getSelectedKey(gridEstatus, "codStatus"));
                        } else {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                   }
                  },
                  {
                  text:getLabelFromMap('gridEsttCsButtonExportar',helpMap,'Exportar'),
                  tooltip:getToolTipFromMap('gridEsttCsButtonExportar',helpMap,'Exportar el Estado'),
                  handler:function(){
                        var url = _ACTION_EXPORTAR_STATUS_CASOS + '?dsStatus=' + desStatus.getValue();
                        showExportDialog( url );
                    }                 
                  }
                  /*
                  ,
                  {
                  text:getLabelFromMap('gridEsttCsBottonRegresar',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('gridEsttCsBottonRegresar',helpMap,'Volver a la Pantalla Anterior'),
                  handler:function(){}
                  }
                  */
                  ,
                  {
                  text:getLabelFromMap('gridEsttCsBottonTiempo',helpMap,'Validar Tiempo'),
                  tooltip: getToolTipFromMap('gridEsttCsBottonTiempo',helpMap,'Validar Compra de Tiempo por Estado de Caso'),
                  handler:function(){
                        if (getSelectedKey(gridEstatus, "codStatus") != "") {
                                statusCasosTareas(getSelectedKey(gridEstatus, "codStatus"));
                        } else {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                        }
                  }                  
                  }                  
                  ],
            width:520,
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
            stripeRows: true,
            collapsible: true,
            bbar: new Ext.PagingToolbar({
                    pageSize:20,
                    store: storeGrillaStatus,
                    displayInfo: true,
                    displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
                    emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
                })          
        });
   gridEstatus.render()
}

incisosFormEstatus.render();
createGrid();

function borrar(key) {
		if(key != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						cdStatus: key
         			};
         			execConnection(_ACTION_BORRAR_STATUS_CASOS, _params, cbkConnection);
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
       		dsStatus: Ext.getCmp('incisosFormEstatus').form.findField('desStatusId').getValue()
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