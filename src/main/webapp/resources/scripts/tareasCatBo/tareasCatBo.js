Ext.onReady(function(){  
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

var descTarea = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('descTareaId',helpMap,''),//Tarea'),
        tooltip:getToolTipFromMap('descTareaId',helpMap,'Nombre Tarea'),
        id: 'descTareaId', 
        name: 'descTarea',
        allowBlank: true,
        width: 196
    });

var descModulo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('descModuloId',helpMap,''),//M&oacute;dulo'),
        tooltip:getToolTipFromMap('descModuloId',helpMap,'Nombre M&oacute;dulo'),
        id: 'descModuloId', 
        name: 'descModulo',
        allowBlank: true,
        width: 196
    });


/*var codiPrioridad = new Ext.form.NumberField({
        fieldLabel: getLabelFromMap('nmbFldCodPrioridadTrsCtBo',helpMap,'Prioridad'),
        tooltip:getToolTipFromMap('nmbFldDesPrioridadTrsCtBo',helpMap,'Numero de Prioridad'),
        id: 'codiPrioridadId', 
        name: 'codiPrioridad',
        allowBlank: true,
        width: 196
    });
*/


var comboCodiPrioridad = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    store: descPrioridad,
    id:'comboCodiPrioridadId',
    fieldLabel: getLabelFromMap('comboCodiPrioridadId',helpMap,''),//Prioridad'),
    tooltip: getToolTipFromMap('comboCodiPrioridadId',helpMap,'Prioridad'),
    width: 196,
    //anchor:'100%',
    //allowBlank: false,
    displayField:'descripcion',
    valueField: 'codigo',
    //hiddenName: 'codiPrioridad',
    typeAhead: true,
    triggerAction: 'all',
    mode:'local',
    emptyText:'Seleccione Indicador de Prioridad...'//,
    /*selectOnFocus:true,
    forceSelection:true*/
    //onSelect:function(record){alert(record.get("codigo"))}
}
); 

descPrioridad.load(); 
    
var incisosFormTareaCatBo = new Ext.FormPanel({
        id: 'incisosFormTareaCatBo',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosFormTareaCatBo',helpMap,'Tareas CAT-BO')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_TAREAS_CAT_BO,
        width: 520,
        height:180,
        items: [{
                layout:'form',
                border: false,
                items:[{
                    bodyStyle:'background: white',
                    labelWidth: 100,
                    layout: 'form',
                    title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
                    frame:true,
                    baseCls: '',
                    items: [{
                        layout:'column',
                        border:false,
                        columnWidth: 1,
                        items:[{
                            columnWidth:.6,
                            layout: 'form',
                            border: false,
                            items:[                                     
                                     descTarea,    
                                     descModulo,
                                     comboCodiPrioridad                                
                                   ]
                            },
                            {
                            columnWidth:.4,
                            layout: 'form'
                            }
                            ]
                            
                        }],
                    buttonAlign: "center",
                    buttons:[
                            {
                            text:getLabelFromMap('frmtTrsCtBoButtonBuscar',helpMap,'Buscar'),
                            tooltip:getToolTipFromMap('frmtTrsCtBoButtonBuscar',helpMap,'Busca Tareas CAT-BO'),
                            handler: function() {
                                if (incisosFormTareaCatBo.form.isValid()) {
                                       if (gridTareas!=null) {
                                        reloadGrid();
                                       } else {
                                        createGrid();
                                       }
                                } else{
                                    Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                                    }
                              }
                            },
                            {
                            text:getLabelFromMap('frmtTrsCtBoButtonCancelar',helpMap,'Cancelar'),
                            tooltip:getToolTipFromMap('frmtTrsCtBoButtonCancelar',helpMap,'Cancela B&uacute;squeda Tareas CAT-BO'),                             
                            handler: function() {
                                incisosFormTareaCatBo.form.reset();
                            }
                        }]
                }]
            }]              
});   

// Definicion de las columnas de la grilla
var cmTareas = new Ext.grid.ColumnModel([
        {
        dataIndex: 'codProceso',
        hidden :true
        },
        {
        header:getLabelFromMap('cmDesProcesoTrsCtBo',helpMap,'Tarea'),
        tooltip:getToolTipFromMap('cmDesProcesoTrsCtBo',helpMap,'Nombre de la Tarea'),
        dataIndex: 'desProceso',
        sortable: true,
        width: 320
        },
        {
        header:getLabelFromMap('cmDesModuloTrsCtBo',helpMap,'M&oacute;dulo'),
        tooltip:getToolTipFromMap('cmDesModuloTrsCtBo',helpMap,'Nombre M&oacute;dulo'),
        dataIndex: 'desModulo',
        sortable: true,
        width: 120
        },
        {
        header:getLabelFromMap('cmCodPrioridadTrsCtBo',helpMap,'Prioridad'),
        tooltip:getToolTipFromMap('cmCodPrioridadTrsCtBo',helpMap,'Indicador de Prioridad'),
        dataIndex: 'desPriord',
        sortable: true,
        width: 120
        }
]);

var gridTareas;

function createGrid(){
       gridTareas= new Ext.grid.GridPanel({
            id: 'grid2',
            el:'gridElementos',
            store: storeGrillaTareas,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            reader: jsonGrillaTareas,
            border: true,
            cm: cmTareas,
            loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
            clicksToEdit:1,
            successProperty: 'success',
            buttonAlign: "center",
            buttons:[
                  {
                  text:getLabelFromMap('gridTrsCtBoBottonAgregar',helpMap,''),//Agregar'),
                  tooltip: getToolTipFromMap('gridTrsCtBoBottonAgregar',helpMap,'Crea una Tarea CAT-BO'),
                  handler:function(){editarTrsCtBo("")}
                  },
                  {
                  text:getLabelFromMap('gridTrsCtBoBottonEditar',helpMap,''),//Editar'),
                  tooltip: getToolTipFromMap('gridTrsCtBoBottonEditar',helpMap,'Edita una Tarea CAT-BO'),
                  handler:function(){
                        if (getSelectedKey(gridTareas, "codProceso") != "") {
                                editarTrsCtBo(getSelectedKey(gridTareas, "codProceso"));
                        } else {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                    }
                  },
                  {
                  text:getLabelFromMap('gridTrsCtBoBottonBorrar',helpMap,''),//Borrar'),
                  tooltip: getToolTipFromMap('gridTrsCtBoBottonBorrar',helpMap,'Elimina una Tarea CAT-BO'),
                  handler:function(){
                        if (getSelectedKey(gridTareas, "codProceso") != "") {
                                borrar(getSelectedKey(gridTareas, "codProceso"));
                        } else {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                                }
                   }
                  },
                  {
                  text:getLabelFromMap('gridTrsCtBoButtonExportar',helpMap,'Exportar'),
                  tooltip:getToolTipFromMap('gridTrsCtBoButtonExportar',helpMap,'Exporta una Tarea CAT-BO'),
                  handler:function(){
                        //alert(comboCodiPrioridad.getValue());
                        var url = _ACTION_EXPORTAR_TAREAS_CAT_BO + '?dsProceso=' + descTarea.getValue()+'&dsModulo='+ descModulo.getValue()+'&cdPriord='+ comboCodiPrioridad.getValue();
                        showExportDialog( url );
                    }                 
                  }
                  /*
                  ,
                  
                  {
                  text:getLabelFromMap('gridTrsCtBoBottonRegresar',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('gridTrsCtBoBottonRegresar',helpMap,'Regresar'),
                  handler:function(){}
                  }
                    */             
                  ],
            width:520,
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
            stripeRows: true,
            collapsible: true,
            bbar: new Ext.PagingToolbar({
                    pageSize:itemsPerPage,
                    store: storeGrillaTareas,
                    displayInfo: true,
                    displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
                    emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
                })          
        });
   gridTareas.render()
}

incisosFormTareaCatBo.render();
createGrid();

function borrar(key) {
		if(key != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminara el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						cdProceso: key
         			};
         			execConnection(_ACTION_BORRAR_TAREAS_CAT_BO, _params, cbkConnection);
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
	if(Ext.getCmp('comboCodiPrioridadId').getRawValue()==""){Ext.getCmp('comboCodiPrioridadId').setValue("")}
	var _params = {
       		dsProceso: Ext.getCmp('incisosFormTareaCatBo').form.findField('descTareaId').getValue(),
            dsModulo: Ext.getCmp('incisosFormTareaCatBo').form.findField('descModuloId').getValue(),
            cdPriord: Ext.getCmp('incisosFormTareaCatBo').form.findField('comboCodiPrioridadId').getValue()            
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