Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

var codRazon = new Ext.form.NumberField({
        fieldLabel: getLabelFromMap('codRazon',helpMap,'Clave'),
        tooltip:getToolTipFromMap('codRazon',helpMap,'Clave del motivo de cancelaci&oacute;n. Valor Numerico'),
        hasHelpIcon:getHelpIconFromMap('codRazon',helpMap),
		Ayuda: getHelpTextFromMap('codRazon',helpMap),
        id: 'codRazon', 
        name: 'codRazon',
        allowBlank: true,
        maxLength: 10,
        anchor: '100%'
    });
    
var desRazon = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('desRazon',helpMap,'Raz&oacute;n'),
        tooltip:getToolTipFromMap('desRazon',helpMap,'Raz&oacute;n del motivo de cancelaci&oacute;n '),
         hasHelpIcon:getHelpIconFromMap('desRazon',helpMap),
		Ayuda: getHelpTextFromMap('desRazon',helpMap),
        id: 'desRazon', 
        name: 'desRazon',
        allowBlank: true,
        anchor: '100%'
    });

       
var incisosForm = new Ext.FormPanel({
		id: 'incisosForm',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosForm',helpMap,'Motivos de Cancelaci&oacute;n')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        autoHeight: true,
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_MOTIVOS_CANCELACION,
        width: 500,
        height:150,
        items: [{
        		layout:'form',
				border: false,
				items:[{
        		bodyStyle:'background: white',
		        labelWidth: 100,
                layout: 'form',
				frame:true,
				
		       	baseCls: '',
		       	buttonAlign: "center",
        		        items: [{
        		        	layout:'column',
		 				    border:false,
		 				    html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
		 				    columnWidth: 1,
        		    		items:[{
						    	columnWidth:.6,
                				layout: 'form',
		                		border: false,
        		        		items:[       		        				
        		        				 codRazon,
        		        				 desRazon       		        				
                                       ]
								},
								{
								columnWidth:.4,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
        							text:getLabelFromMap('motCancBtnSeek',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('motCancBtnSeek',helpMap,'Busca un Grupo de Motivos de Cancelaci&oacute;n'),
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
        							
        							
        							
        							text:getLabelFromMap('motCancBtnCanc',helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('motCancBtnCanc',helpMap,'Cancela operaci&oacute;n de Motivos de Cancelaci&oacute;n'),                              
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
        header: getLabelFromMap('motCancCmClav',helpMap,'Clave'),
        tooltip: getToolTipFromMap('motCancCmClav',helpMap,'Clave del motivo de cancelaci&oacute;n'),
        dataIndex: 'cdRazon',
        width: 50,
        sortable: true
        },
        {
        header: getLabelFromMap('motCancCmRaz',helpMap,'Raz&oacute;n'),
        tooltip: getToolTipFromMap('motCancCmRaz',helpMap,'Raz&oacute;n del motivo de cancelaci&oacute;n'),
        dataIndex: 'dsRazon',
        width: 190,
        sortable: true
   
        },
        {
        header: getLabelFromMap('motCancCmReh',helpMap,'Rehabilitaci&oacute;n'),
        tooltip: getToolTipFromMap('motCancCmReh',helpMap,'Rehabilitaci&oacute;n del motivo de cancelaci&oacute;n'),
        dataIndex: 'swReInst',
        width: 115,
        sortable: true,
        align: 'center'
        },
        {
        header: getLabelFromMap('motCancCmVerPag',helpMap,'Ver Pagos'),
        tooltip: getToolTipFromMap('motCancCmVerPag',helpMap,'Ver Pagos del motivo de cancelaci&oacute;n'),
        dataIndex: 'swVerPag',
        width: 110,
        sortable: true,
        align: 'center',
        hidden :true
        }
]);

var lasFilas=new Ext.data.Record.create([
  {name: 'cdRazon',  mapping:'cdRazon',  type: 'int'},
  {name: 'dsRazon',  mapping:'dsRazon',  type: 'string'},
  {name: 'swReInst',  mapping:'swReInst',  type: 'string'},
  {name: 'swVerPag',  mapping:'swVerPag',  type: 'string'}                
]);

var jsonGrilla= new Ext.data.JsonReader(
  {
   root:'mcEstructuraList',
   totalProperty: 'totalCount',
   successProperty : '@success'
  },
 lasFilas  
);

function storeGrilla(){
             store = new Ext.data.Store({
             proxy: new Ext.data.HttpProxy({
             url: _ACTION_BUSCAR_MOTIVOS_CANCELACION,
             			waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
		                }),
             reader:jsonGrilla
             });
             return store;
}


var grid2;

function createGrid(){
       grid2= new Ext.grid.GridPanel({
       		id: 'grid2',
            el:'gridElementos',
            store:storeGrilla(),
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            reader:jsonGrilla,
            border:true,
            cm: cm,
            buttonAlign:'center',
            clicksToEdit:1,
            loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
	        successProperty: 'success',
            buttons:[
                  {
                  text:getLabelFromMap('motCancBtnAdd',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('motCancBtnAdd',helpMap,'Crea un Nuevo Motivo de Cancelaci&oacute;n'),
                  handler:function(){configurar(0)}
                  },
                  {
                  text:getLabelFromMap('motCancBtnEd',helpMap,'Editar'),
                  tooltip: getToolTipFromMap('motCancBtnEd',helpMap,'Edita un Motivo de Cancelaci&oacute;n'),
                  handler:function(){
						if (getSelectedKey(grid2, "cdRazon") != "") {
                        		configurar(getSelectedKey(grid2, "cdRazon"));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                                }
                   }
                  },
                  {
                  text:getLabelFromMap('motCancBtnDel',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('motCancBtnDel',helpMap,'Elimina un Motivo de Cancelaci&oacute;n'),
                  handler:function(){
						if (getSelectedKey(grid2, "cdRazon") != "") {
                        		borrar(getSelectedKey(grid2, "cdRazon"));
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                	}
                  },
                  {
                  text:getLabelFromMap('motCancBtnExp',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('motCancBtnExp',helpMap,'Exporta la B&uacute;squeda Motivos de Cancelaci&oacute;n'),
                  handler:function(){
                        var url = _ACTION_EXPORTAR_MOTIVOS_CANCELACION + '?cdRazon=' + codRazon.getValue()+ '&dsRazon=' + desRazon.getValue();
                	 	showExportDialog( url );
                    }
                  }/*,
                  {
                  text:getLabelFromMap('motCancBtnBack',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('motCancBtnBack',helpMap,'Regresa a la Pantalla Anterior')
                  }*/
                  ],
            width:500,
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize: itemsPerPage,
					store: store,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
   grid2.render()
}

  
incisosForm.render();
createGrid();

codRazon.setValue(FILTRO_CODIGO_RAZON);
desRazon.setValue(DESCRIPCION_RAZON);

if (FILTRO_CODIGO_RAZON != "" || DESCRIPCION_RAZON != "") {
	reloadGrid();
}

function configurar(key)
{
	if (key!=0)
	{
		window.location=_ACTION_IR_AGREGAR_MOTIVOS_CANCELACION+"?cdRazon="+key+"&codRazon="+codRazon.getValue()+"&dsRazon="+desRazon.getValue();
	}
	else
	{
		window.location=_ACTION_IR_AGREGAR_MOTIVOS_CANCELACION+"?codRazon="+codRazon.getValue()+"&dsRazon="+desRazon.getValue();
	}
};

function borrar(key) {
		if(key != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						cdRazon: key
         			};
         			execConnection(_ACTION_BORRAR_MOTIVOS_CANCELACION, _params, cbkConnection);
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
       		cdRazon: Ext.getCmp('incisosForm').form.findField('codRazon').getValue(),
       		dsRazon: Ext.getCmp('incisosForm').form.findField('desRazon').getValue()
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