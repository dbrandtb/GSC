Ext.onReady(function(){ 
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//VALORES DE INGRESO DE LA BUSQUEDA
var dsUniEco = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsUniEcoId',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('dsUniEcoId',helpMap,'Aseguradora'), 
        hasHelpIcon:getHelpIconFromMap('dsUniEcoId',helpMap),
        Ayuda:getHelpTextFromMap('dsUniEcoId',helpMap),
        id: 'dsUniEcoId', 
        name: 'dsUniEco',
        width: 216
});
 
var dsRamo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsRamoId',helpMap,'Producto'),
        tooltip:getToolTipFromMap('dsRamoId',helpMap,'Producto'), 
        hasHelpIcon:getHelpIconFromMap('dsRamoId',helpMap),
        Ayuda:getHelpTextFromMap('dsRamoId',helpMap),
        id: 'dsRamoId', 
        name: 'dsRamo',
        width: 216
            
    });
  
var dsEstado = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsEstadoId',helpMap,'Estado'),
        tooltip:getToolTipFromMap('dsEstadoId',helpMap,'Estado'), 
        hasHelpIcon:getHelpIconFromMap('dsEstadoId',helpMap),
        Ayuda:getHelpTextFromMap('dsEstadoId',helpMap),
        id: 'dsEstadoId', 
        name: 'dsEstado',
        width: 216
    });
    
    
    
var nmPoliza = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('nmPolizaId',helpMap,'P&oacute;liza'),
        tooltip:getToolTipFromMap('nmPolizaId',helpMap,'P&oacute;liza'), 
        hasHelpIcon:getHelpIconFromMap('nmPolizaId',helpMap),
        Ayuda:getHelpTextFromMap('nmPolizaId',helpMap),
        id: 'nmPolizaId', 
        name: 'nmPoliza',
        width: 216
    });
  
    
var dsPerson = new Ext.form.TextField({
	    fieldLabel: getLabelFromMap('dsPersonId',helpMap,'Cliente'),
        tooltip:getToolTipFromMap('dsPersonId',helpMap,'Cliente'), 
        hasHelpIcon:getHelpIconFromMap('dsPersonId',helpMap),
        Ayuda:getHelpTextFromMap('dsPersonId',helpMap),
        id: 'dsPersonId', 
        name: 'dsPerson',
        width: 216  
       
    });
    
var dsUsuari = new Ext.form.TextField({
	    fieldLabel: getLabelFromMap('dsUsuarioId',helpMap,'Usuario'),
        tooltip:getToolTipFromMap('dsUsuarioId',helpMap,'Usuario'), 
        hasHelpIcon:getHelpIconFromMap('dsUsuarioId',helpMap),
        Ayuda:getHelpTextFromMap('dsUsuarioId',helpMap),
        id: 'dsUsuarioId', 
        name: 'dsUsuari',
        width: 216
    });
    



var incisosFormEncuesta = new Ext.FormPanel({
		id: 'incisosFormEncuesta',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosFormEncuesta',helpMap,'Consulta de Asignaci&oacute;n de Encuestas')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_ASIGNACION_ENCUESTAS_2,
        width: 500,
        height:200,
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
        		        				 //dsUniEco,
        		        				 //dsRamo,
        		        				 //dsEstado,
        		        				 nmPoliza,
        		        				 dsPerson,
        		        				 dsUsuari
                                       ]
								},
								{
								columnWidth:.3,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
        							text:getLabelFromMap('asgncnEncstButtonBuscar',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('asgncnEncstButtonBuscar',helpMap,'Busca un Grupo de Configuraciones para Encuesta'),
        							handler: function() {
				               			if (incisosFormEncuesta.form.isValid()) {
                                               if (grid2!=null) {
                                                reloadGrid();
                                                //incisosFormEncuesta.form.reset();
                                               } else {
                                                createGrid();
                                               }
                						} else{
                							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
										}
									}
        							},{
        							text:getLabelFromMap('asgncnEncstButtonCancelar',helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('asgncnEncstButtonCancelar',helpMap,'Cancela busqueda de Configuraciones de Encuesta'),                              
        							handler: function() {
        								incisosFormEncuesta.form.reset();
        							}
        						}]
        					}]
        				}]	            
        
});   


// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
       /* {
        
        header: getLabelFromMap('cmDsUniEco',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('cmDsUniEco',helpMap,'Columna Aseguradora'),
        dataIndex: 'dsUnieco',
        width: 100,
        sortable: true
        
        },{
        
        header: getLabelFromMap('cmDsProducto',helpMap,'Producto'),
        tooltip: getToolTipFromMap('cmDsProducto',helpMap,'Columna Producto'),
        dataIndex: 'dsRamo',
        width: 100,
        sortable: true
        
        },{
        
        header: getLabelFromMap('cmDsEstado',helpMap,'Estado'),
        tooltip: getToolTipFromMap('cmDsEstado',helpMap,'Columna Estado'),
        dataIndex: 'estado',
        id:'estado',
        width: 70,
        sortable: true
        
        },
        {
        header: getLabelFromMap('cmAsgEncNmPoliza',helpMap,'Poliza'),
        tooltip: getToolTipFromMap('cmAsgEncNmPoliza',helpMap,'Columna Poliza'),
        dataIndex: 'nmPoliza',
        width: 100,
        sortable: true
        },*/{
        header: getLabelFromMap('cmAsgEncNmPoliza',helpMap,'Poliza'),
        tooltip: getToolTipFromMap('cmAsgEncNmPoliza',helpMap,'Columna Poliza'),
        dataIndex: 'nmpoliex',
        width: 100,
        sortable: true
        },{
        header: getLabelFromMap('cmAsgEncDsPerson',helpMap,'Cliente'),
        tooltip: getToolTipFromMap('cmAsgEncDsPerson',helpMap,'Columna Cliente'),
        dataIndex: 'dsPerson',
        width: 185,
        sortable: true
        },{
        header: getLabelFromMap('cmAsgEncDsUsuari',helpMap,'Usuario Responsable'),
        tooltip: getToolTipFromMap('cmCdUsuario',helpMap,'Columna Usuario Responsable'),
        dataIndex: 'dsUsuari',
        width: 150,
        sortable: true
        },{
        dataIndex: 'nmConfig',
        hidden :true
       },{
        dataIndex: 'cdUnieco',
        hidden :true
       },{
        dataIndex: 'cdRamo',
        hidden :true
       },{
        dataIndex: 'estado',
        hidden :true
       },{
        dataIndex: 'cdPerson',
        hidden :true
       },{
        dataIndex: 'status',
        hidden :true
       } ,{
        dataIndex: 'cdModulo',
        hidden :true
       } ,{
        dataIndex: 'cdUsuari',
        hidden :true
       }                        
]);

var grid2;

function createGrid(){
       grid2= new Ext.grid.GridPanel({
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
                 /* {
                  text:getLabelFromMap('gridNtfcnBottonAgregar',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonAgregar',helpMap,'Crea una Nueva Encuesta'),
                  /*handler:function(){
                  	if (getSelectedKey(grid2, "nmConfig") != "") {
                  			agregarAsignacionEncuesta(getSelectedRecord(grid2));
                  	} else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                  }
                  },*/
                  {
                  text:getLabelFromMap('gridAsgncnEncstBottonEditar',helpMap,'Editar'),
                  tooltip: getToolTipFromMap('gridAsgncnEncstBottonEditar',helpMap,'Edita una Encuesta'),
                  handler:function(){
						if (getSelectedKey(grid2, "nmConfig") != "") {
                  			agregarAsignacionEncuesta(getSelectedRecord(grid2));
                  	} else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                	}
                  },
                   {
                  text:getLabelFromMap('gridAsgncnEncstBottonBorrar',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('gridAsgncnEncstBottonBorrar',helpMap,'Eliminar una Encuesta'),
                  handler:function(){
						if (getSelectedKey(grid2, "nmConfig") != "") {
                        		borrar(getSelectedRecord(grid2));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                   }
                  },
                  {
                  text:getLabelFromMap('gridAsgncnEncstBottonExportar',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('gridAsgncnEncstBottonExportar',helpMap,'Exportar datos a diversos formatos'),
                    handler:function(){
                        var url = _ACTION_OBTENER_ASIGNACION_ENCUESTA_EXPORTAR + '?nmPoliza=' + nmPoliza.getValue() + '&dsPerson=' + dsPerson.getValue() + '&dsUsuari=' + dsUsuari.getValue();
                        showExportDialog( url );
                    } 
                  
                  }
                  
                  /*  {
               
                    handler:function(){
                        var url = _ACTION_EXPORTAR_CONFIGURACION_ENCUESTAS + '?pv_dsunieco_i=' + dsUnieco.getValue() + '&pv_dsramo_i=' + dsRamo.getValue() + '&pv_dselemento_i=' + dsElemento.getValue() + '&pv_dsproceso_='+ dsProceso.getValue() +'&pv_dscampan_i=' + dsCampan.getValue() +'&pv_dsmodulo_i=' + dsModulo.getValue() + '&pv_dsencuesta_i=' + dsEncuesta.getValue();
                        showExportDialog( url );
                    } 
                  
                  }, */
                  /*
                  ,
                  {
                  text:getLabelFromMap('gridAsgncnEncstBottonRegresar',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('gridAsgncnEncstBottonRegresar',helpMap,'Volver a la Pantalla Anterior')
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

function borrar(record) {
		if(record.get('nmConfig') != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						pv_nmconfig_i: record.get('nmConfig'),
         						pv_cdunieco_i:record.get('cdUnieco'),
         						pv_cdramo_i:record.get('cdRamo'),
         						pv_estado_i:record.get('estado'),
         						pv_nmpoliza_i:record.get('nmPoliza'),
         						pv_cdperson_i:record.get('cdPerson'),
         						pv_cdusuario_i:record.get('dsUsuari')
         						
         						
         			};
         			execConnection(_ACTION_BORRAR_ASIGNACION_ENCUESTA, _params, cbkConnection);
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


function reloadGrid(){ // nmPoliza, nombreCliente, nombreUsuario,
	var _params = {
       				//pv_dsunieco_i:Ext.getCmp('incisosFormEncuesta').form.findField('dsUniEco').getValue(), 
       				//pv_dsramo_i:Ext.getCmp('incisosFormEncuesta').form.findField('dsRamo').getValue(),
       				//pv_estado_i:Ext.getCmp('incisosFormEncuesta').form.findField('dsEstado').getValue(),
       				nmPoliza: Ext.getCmp('incisosFormEncuesta').form.findField('nmPoliza').getValue(),
       				dsPerson: Ext.getCmp('incisosFormEncuesta').form.findField('dsPerson').getValue(),
       				 dsUsuari: Ext.getCmp('incisosFormEncuesta').form.findField('dsUsuari').getValue()
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