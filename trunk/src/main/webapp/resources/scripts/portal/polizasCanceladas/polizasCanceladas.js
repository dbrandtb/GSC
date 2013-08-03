Ext.onReady(function(){  
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";   

//VALORES DE INGRESO DE LA BUSQUEDA
	var idRegresar = new Ext.form.Hidden({
        id: 'idRegresar',
        name: 'idRegresar',
        value:''
    });

	var dsAseg = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtDsUniEco',helpMap,'Asegurado'),
        tooltip:getToolTipFromMap('txtDsUniEco',helpMap,'Asegurado'), 
        id: 'dsAseg',
        name: 'dsAseg',
        allowBlank: true,
        anchor: '100%'
        //width:150
    });
 
	var dsRamo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtDsRamo',helpMap,'Producto'),
        tooltip:getToolTipFromMap('txtDsRamo',helpMap,'Producto'), 
        id: 'dsRamoId', 
        name: 'dsRamo',
        allowBlank: true,
        anchor: '100%'
        //width:150
    });

	var storeRazonCancelacion = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: _ACTION_COMBO_RAZON_CANCELACION,
			method: 'POST'
		}),
		reader: new Ext.data.JsonReader({
			root:'comboRazones',
			totalProperty: 'totalCount',
			successProperty : '@success'
		},[
			{name: 'cdRazon',  type: 'string',  mapping:'cdRazon'},
			{name: 'dsRazon',  type: 'string',  mapping:'dsRazon'}
		])
	});

	var comboMotivoCancelacion = new Ext.form.ComboBox({
		xtype: 'combo', 
		labelWidth: 70, 
		fieldLabel: getLabelFromMap('cmbTareaIdOrdSol',helpMap,'Motivo Cancelaci&oacute;n'),
		tooltip:getToolTipFromMap('cmbTareaIdOrdSol',helpMap,'Motivo Cancelaci&oacute;n'),
		tpl: '<tpl for="."><div ext:qtip="{cdRazon}. {dsRazon}" class="x-combo-list-item">{dsRazon}</div></tpl>',
		store: storeRazonCancelacion,
		displayField:'dsRazon',
		valueField:'cdRazon',
		hiddenName: 'cd_Razon', 
		typeAhead: true,
		mode: 'local', 
		triggerAction: 'all', 
		labelAlign: 'top',
		fieldLabel: getLabelFromMap('cmbRazonCancelacion',helpMap,'Raz&oacute;n de Cancelaci&oacute;n'),
		tooltip:getToolTipFromMap('cmbRazonCancelacion',helpMap,'Raz&oacute;n de Cancelaci&oacute;n'), 
		anchor:'100%',
		emptyText:'Seleccione ...',
		selectOnFocus:true, 
		forceSelection:true,
		id: 'comboMotivoCancelacionId'
	});

	var nmInciso = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtInciso',helpMap,'Inciso'),
        tooltip:getToolTipFromMap('txtInciso',helpMap,'Inciso'), 
        id: 'nmIncisoId', 
        name: 'nmInciso',
        allowBlank: true,
        anchor: '100%'
    });
    
	var dsUniEco = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtDsUniEco',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('txtDsUniEco',helpMap,'Aseguradora'), 
        id: 'dsUniEcoId', 
        name: 'dsUniEco',
        allowBlank: true,
        anchor: '100%'
    });
 
	var nmPoliza = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtNmPoliza',helpMap,'P&oacute;liza'),
        tooltip:getToolTipFromMap('txtNmPoliza',helpMap,'P&oacute;liza'), 
        id: 'nmPolizaId', 
        name: 'nmPoliza',
        allowBlank: true,
        anchor: '100%'       
    });
    
	var fechaCnclcnDe=	new Ext.form.DateField({
		name: 'fechaCnclcnDe',
		id: 'fechaCnclcnDeId',
		fieldLabel: getLabelFromMap('edAlerDfUltEnv',helpMap,'Fecha de Cancelaci&oacute;n De'),
		tooltip: getToolTipFromMap('edAlerDfUltEnv',helpMap), 
		format: 'd/m/Y'
	});

	var fechaCnclcnA=	new Ext.form.DateField({
		name: 'fechaCnclcnA',
		id: 'fechaCnclcnAId',
		fieldLabel: getLabelFromMap('edAlerDfUltEnv',helpMap,'Fecha de Cancelaci&oacute;n A'),
		tooltip: getToolTipFromMap('edAlerDfUltEnv',helpMap), 
		format: 'd/m/Y'
	});
    
	var incisosFormPolizas = new Ext.FormPanel({
		id: 'incisosFormPolizas',
		el: 'formBusqueda',
		title: '<span style="color:black;font-size:12px;">'+ getLabelFromMap('24', helpMap, 'P&oacute;lizas Canceladas')+ '</span>',
		iconCls: 'logo',
		url: _ACTION_BUSCAR_POLIZAS_CANCELADAS,
		store: storeGrillaPolizasCanceladas,
		reader: jsonGrillaPolizasCanceladas,
		bodyStyle: 'background: white',
		buttonAlign: "center",
		labelAlign: 'right',
		frame: true,
		width: 500,
		height: 234,
		items: [{
			layout: 'form',
			border: false,
			items: [{
				bodyStyle: 'background: white',
				labelWidth: 100,
				layout: 'form',
				title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
				frame: true,
				baseCls: '',
				buttonAlign: "center",
				items: [{
					layout: 'column',
					border: false,
					columnWidth: 1,
					items: [{
						columnWidth: .4,
						layout: 'form',
						border: false,
						items: [
							idRegresar,
							dsAseg,
							dsRamo,
							comboMotivoCancelacion,
							nmInciso]
					}, {
						columnWidth: .5,
						layout: 'form',
						border: false,
						items: [
							dsUniEco,
							nmPoliza,
							fechaCnclcnDe,
							fechaCnclcnA]
					}]
				}],
				buttons: [{
					text: getLabelFromMap('ntfcnButtonBuscar', helpMap, 'Buscar'),
					tooltip: getToolTipFromMap('ntfcnButtonBuscar', helpMap, 'Busca un Grupo de Polizas a Cancelar'),
					handler: function() {
						if (incisosFormPolizas.form.isValid()) {
							if (grid2 != null) {
								reloadGrid();
							} else {
								createGrid();
							}
						} else {
							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso'), getLabelFromMap('400013', helpMap, 'Complete la informacion requerida'));
						}
					}
				}, {
					text: getLabelFromMap('ntfcnButtonCancelar', helpMap, 'Cancelar'),
					tooltip: getToolTipFromMap('ntfcnButtonCancelar', helpMap, 'Cancelar busqueda de Polizas'),
					handler: function() {
						incisosFormPolizas.form.reset();
					}
				}]
			}]
		}]
	});

// Definicion de las columnas de la grilla
	var cm = new Ext.grid.ColumnModel([{
		header : getLabelFromMap('cmDsAseg', helpMap, 'Asegurado'),
		tooltip : getToolTipFromMap('cmDsAseg', helpMap, 'Columna Asegurado'),
		dataIndex : 'asegurado',
		width : 100,
		sortable : true
	},{
		header : getLabelFromMap('cmDsUniEco', helpMap, 'Aseguradora'),
		tooltip : getToolTipFromMap('cmDsUniEco', helpMap, 'Columna Aseguradora'),
		dataIndex : 'dsUnieco',
		width : 100,
		sortable : true
	},{
		header : getLabelFromMap('cmDsProducto', helpMap, 'Producto'),
		tooltip : getToolTipFromMap('cmDsProducto', helpMap, 'Columna Producto'),
		dataIndex : 'dsRamo',
		width : 100,
		sortable : true
	},{
		header : getLabelFromMap('cmNmPoliza', helpMap, 'P&oacute;liza'),
		tooltip : getToolTipFromMap('cmNmPoliza', helpMap, 'Columna P&oacute;liza'),
		dataIndex : 'nmPoliex',
		width : 100,
		sortable : true
	},{
		header : getLabelFromMap('cmNmPoliza', helpMap, 'Inciso'),
		tooltip : getToolTipFromMap('cmNmPoliza', helpMap, 'Columna P&oacute;liza'),
		dataIndex : 'nmSituac',
		width : 100,
		sortable : true
	},{
		header : getLabelFromMap('cmFechaCancela', helpMap, 'Fecha de Cancelaci&oacute;n'),
		tooltip : getToolTipFromMap('cmFechaCancela', helpMap, 'Columna Fecha de Cancelaci&oacute;n'),
		dataIndex : 'feCancel',
		width : 100,
		sortable : true
	},{
		header : getLabelFromMap('cmPrimaNoDevengada', helpMap, 'Prima no Devengada'),
		tooltip : getToolTipFromMap('cmPrimaNoDevengada', helpMap, 'Columna Prima no Devengada'),
		dataIndex : 'primaNoDevengada',
		width : 100,
		sortable : true
	},{
		header : getLabelFromMap('cmMotivoCancela', helpMap, 'Motivo de Cancelaci&oacute;n'),
		tooltip : getToolTipFromMap('cmMotivoCancela', helpMap, 'Columna Motivo de Cancelaci&oacute;n'),
		dataIndex : 'dsRazon',
		width : 100,
		sortable : true
	},
	{dataIndex : 'nmPoliza', 		hidden : true},
	{dataIndex : 'cdUniage', 		hidden : true},
	{header    : 'cdUnieco', 		dataIndex : 'cdUnieco', hidden : true},
	{dataIndex : 'cdRamo', 			hidden : true},
	{dataIndex : 'cdRazon',			hidden : true},
	{dataIndex : 'tipoCancel', 		hidden : true},
	{dataIndex : 'nmsuplem', 		hidden : true},
	{dataIndex : 'estado',			hidden : true},
	{header    : 'feefecto',		dataIndex : 'feefecto', hidden : true},
	{header    : 'fevencim',		dataIndex : 'fevencim',	hidden : true},
	{dataIndex : 'comentarios',		hidden : true},
	{dataIndex : 'nmCancel',		hidden : true},
	{dataIndex : 'cdPerson',		hidden : true},
	{dataIndex : 'cdMoneda',		hidden : true}
	]);

	var grid2;

	function createGrid(){
       grid2= new Ext.grid.GridPanel({
       		id: 'grid2',
            el:'gridElementos',
            store: storeGrillaPolizasCanceladas,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            reader:jsonGrillaPolizasCanceladas,
            border:true,
            cm: cm,
            clicksToEdit:1,
	        successProperty: 'success',
            buttonAlign: "center",
            buttons: [{

            	text: getLabelFromMap('gridNtfcnBottonDetalle', helpMap, 'Detalle'),
				tooltip: getToolTipFromMap('gridNtfcnBottonDetalle', helpMap, 'Detalle de P&oacute;liza Cancelada'),
				handler: function() {
					if (getSelectedKey(grid2, "cdUnieco") != "") {
						record = getSelectedRecord(grid2);
						window.location.href = _CONTEXT + '/procesoemision/detallePoliza.action?'
								+ 'cdUnieco=' + record.get('cdUnieco')
								+ '&cdRamo=' + record.get('cdRamo')
								+ '&estado=' + record.get('estado')
								+ '&nmPoliza=' + record.get('nmPoliza')
								+ '&poliza=' + record.get('nmPoliex')
								+ '&producto=' + record.get('dsRamo')
								+ '&aseguradora=' + record.get('dsUnieco')
								+ '&volver1=' + "true"
								+ '&plzacancelada=' + "1"
								+ "&idRegresar=" + idRegresar.getValue();
					} else {
						Ext.MessageBox.alert( getLabelFromMap('400000', helpMap, 'Aviso'), getLabelFromMap('400011', helpMap, 'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
					}
				}

			},{

				text: getLabelFromMap('gridNtfcnBottonRehabilitar', helpMap, 'Rehabilitar'),
				tooltip: getToolTipFromMap('gridNtfcnBottonRehabilitar', helpMap, 'Rehabilitar'),
				handler: function() {
					if (getSelectedKey(grid2, "cdUniage") != "") {
						var _rec;
						var _reha;
						_rec = getSelectedRecord(grid2);
						_reha = _rec.get('reha');

						if (_reha == 'N') {
							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso'), getLabelFromMap('400011', helpMap, 'La raz&oacute;n de la cancelaci&oacute;n de la p&oacute;liza no permite rehabilitaci&oacute;n'));
						} else {
							var params = "cdRamo=" + _rec.get('cdRamo');
							var conn = new Ext.data.Connection();

							conn.request({
								url : _ACTION_VALIDA_ENDOSO,
								method : 'POST',
								successProperty : '@success',
								params : params,
								callback : function(options, success, response) {
									validacion = Ext.util.JSON.decode(response.responseText).validaEndoso;
									if (validacion == true) {
										window.location = _ACTION_IR_REHABILITACION_MANUAL
												+ '?cdUnieco=' + _rec.get('cdUnieco')
												+ '&cdRamo=' + _rec.get('cdRamo')
												+ '&dsRamo=' + _rec.get('dsRamo')
												+ '&dsUnieco=' + _rec.get('dsUnieco')
												+ '&nmPoliza=' + _rec.get('nmPoliza')
												+ '&asegurado=' + _rec.get('asegurado')
												+ '&nmSituac=' + _rec.get('nmSituac')
												+ '&cdRazon=' + _rec.get('cdRazon')
												+ '&feCancel=' + _rec.get('feCancel')
												+ '&dsRazon=' + _rec.get('dsRazon')
												+ '&feEfecto=' + _rec.get('feefecto')
												+ '&feVencim=' + _rec.get('fevencim')
												+ '&nmsuplem=' + _rec.get('nmsuplem')
												+ '&comentarios=' + _rec.get('comentarios')
												+ '&estado=' + _rec.get('estado')
												+ '&nmCancel=' + _rec.get('nmCancel')
												+ '&cdPerson=' + _rec.get('cdPerson')
												+ '&cdMoneda=' + _rec.get('cdMoneda')
												+ '&cdUniage=' + _rec.get('cdUniage')
												+ '&nmPoliex=' + _rec.get('nmPoliex');
									} else {
										Ext.MessageBox.alert('Aviso', 'Para el producto seleccionado no se permite rehabilitaci&oacute;n');
									}
								}
							});
						}

					} else {
						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso'), getLabelFromMap('400011', helpMap, 'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
					}
				}

			},{

				text: getLabelFromMap('gridNtfcnBottonRevertir',helpMap,'Revertir'),
				tooltip: getToolTipFromMap('gridNtfcnBottonRevertir',helpMap,'Revertir'),
				handler: function(){
					if (getSelectedKey(grid2, "cdUnieco") != "") {
						revertir(getSelectedRecord(grid2));
					} else {
						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
					}
				}

			},{

				text:getLabelFromMap('gridNtfcnBottonExportar',helpMap,'Exportar'),
				tooltip: getToolTipFromMap('gridNtfcnBottonExportar',helpMap,'Exportar datos a diversos formatos'),
				handler:function(){
					var url = _ACTION_EXPORTAR_POLIZAS_CANCELADAS + '?pv_asegurado_i=' + dsAseg.getValue() + '&pv_dsuniage_i='+ dsUniEco.getValue() + '&pv_dsramo_i=' + dsRamo.getValue() + '&pv_nmpoliza_i=' + nmPoliza.getValue() + '&pv_nmsituac_i=' + nmInciso.getValue() + '&pv_dsrazon_i=' + comboMotivoCancelacion.getValue() + '&pv_fecancel_ini_i=' + fechaCnclcnDe.getValue() + '&pv_fecancel_fin_i=' + fechaCnclcnA.getValue();
					showExportDialog( url );
				} 

			}],
            width: 500,
            frame: true,
            height: 320,
            sm: new Ext.grid.RowSelectionModel({
            	singleSelect: true,
				listeners: {
					rowselect: function(sm, row, rec) {
						idRegresar.setValue( rec.get('nmCancel') );
					}
				}
            }),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
				pageSize:20,
				store:storeGrillaPolizasCanceladas,
				displayInfo: true,
				displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			})          
        });
		grid2.render()
	}

	incisosFormPolizas.render();
	createGrid();

///// DATOS - BOTON REGRESAR

	if ( _SESSION_PARAMETROS_REGRESAR ) {
		
		Ext.getCmp('dsAseg').setValue( _SESSION_PARAMETROS_REGRESAR.asegurado );
		Ext.getCmp('dsUniEcoId').setValue( _SESSION_PARAMETROS_REGRESAR.aseguradora );
		Ext.getCmp('dsRamoId').setValue( _SESSION_PARAMETROS_REGRESAR.producto );
		Ext.getCmp('nmPolizaId').setValue( _SESSION_PARAMETROS_REGRESAR.poliza );
		storeRazonCancelacion.load({
			callback: function(r,options,success) {
				Ext.getCmp('comboMotivoCancelacionId').setValue( _SESSION_PARAMETROS_REGRESAR.razon );
			}
		});
		Ext.getCmp('fechaCnclcnDeId').setValue( _SESSION_PARAMETROS_REGRESAR.fecha_inicio );
		Ext.getCmp('fechaCnclcnAId').setValue( _SESSION_PARAMETROS_REGRESAR.fecha_fin );
		Ext.getCmp('nmIncisoId').setValue( _SESSION_PARAMETROS_REGRESAR.inciso );

		storeGrillaPolizasCanceladas.removeAll();
		storeGrillaPolizasCanceladas.baseParams['idRegresar'] = _SESSION_PARAMETROS_REGRESAR.idRegresar;
		storeGrillaPolizasCanceladas.baseParams['pv_asegurado_i'] = _SESSION_PARAMETROS_REGRESAR.asegurado;
		storeGrillaPolizasCanceladas.baseParams['pv_dsuniage_i'] = _SESSION_PARAMETROS_REGRESAR.aseguradora;
		storeGrillaPolizasCanceladas.baseParams['pv_dsramo_i'] = _SESSION_PARAMETROS_REGRESAR.producto;
		storeGrillaPolizasCanceladas.baseParams['pv_nmpoliza_i'] = _SESSION_PARAMETROS_REGRESAR.poliza;
		storeGrillaPolizasCanceladas.baseParams['pv_nmsituac_i'] = _SESSION_PARAMETROS_REGRESAR.inciso;
		storeGrillaPolizasCanceladas.baseParams['pv_dsrazon_i'] = _SESSION_PARAMETROS_REGRESAR.razon;
		storeGrillaPolizasCanceladas.baseParams['pv_fecancel_ini_i'] = _SESSION_PARAMETROS_REGRESAR.fecha_inicio;
		storeGrillaPolizasCanceladas.baseParams['pv_fecancel_fin_i'] = _SESSION_PARAMETROS_REGRESAR.fecha_fin;
		storeGrillaPolizasCanceladas.load({
			callback: function(r,options,success) {
				function buscaRecord(stringIdRegresar) {
					var _nmcancel = stringIdRegresar;
					var index = -1;
					var bandera = true;
					do {
						index = storeGrillaPolizasCanceladas.find('nmCancel', _nmcancel, index + 1);
						var record = storeGrillaPolizasCanceladas.getAt(index);
						var record0 = storeGrillaPolizasCanceladas.getAt(0);
						// Swap de Ext.Data.Record
						storeGrillaPolizasCanceladas.remove(record0);
						storeGrillaPolizasCanceladas.remove(record);
						storeGrillaPolizasCanceladas.insert(0, record);
						storeGrillaPolizasCanceladas.insert(index, record0);
						bandera = false;
					} while (bandera);
					return;
				}
				if ( storeGrillaPolizasCanceladas.getCount() > 1 )
					buscaRecord(_SESSION_PARAMETROS_REGRESAR.idRegresar);
				Ext.getCmp('grid2').getSelectionModel().selectRow(0);
			}
		});


	} else {
		storeRazonCancelacion.load();
	}

/////

	function borrar(record) {
		if (record.get('cdUniage') != "") {

			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap, 'Aviso'), getLabelFromMap('400031', helpMap, 'Se eliminar&aacute; el registro seleccionado'),
				function(btn) {
					if (btn == "yes") {
						var _params = {
							key : key
						};
						execConnection(_ACTION_BORRAR_POLIZA, _params, cbkConnection);
					}
				})

		} else {

			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso'), getLabelFromMap('400011', helpMap, 'Debe seleccionar un registro para realizar esta operaci&oacute;n'));

		}
	};

	function revertir(record) {
		if(record.get('cdUnieco') != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se revertir&aacute; el registro seleccionado'),function(btn){
		        if (btn == "yes")
		        {
         			var _params = {
         					cdUnieco:record.get('cdUnieco'),
         					cdRamo:record.get('cdRamo'),
         					estado:record.get('estado'),
         					nmPoliza:record.get('nmPoliza'),
         					supLogi:"",
         					nmsuplem:record.get('nmsuplem')
         			};
         			execConnection(_ACTION_REVERTIR_POLIZAS_CANCELADAS, _params, cbkConnection);
               }
			})

		}else{
			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		}
	};


	function cbkConnection(_success, _message) {
		if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap, 'Error'), _message, function() {
				reloadGrid()
			});
		} else {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap, 'Aviso'), _message, function() {
				reloadGrid()
			});
		}
	}

});


function reloadGrid(){
	
	var _params = {
		idRegresar: Ext.getCmp('idRegresar').getValue(),
		pv_asegurado_i: Ext.getCmp('dsAseg').getValue(),
		pv_dsuniage_i: Ext.getCmp('dsUniEcoId').getValue(),
		pv_dsramo_i: Ext.getCmp('dsRamoId').getValue(),
		pv_nmpoliza_i: Ext.getCmp('nmPolizaId').getValue(),
		pv_nmsituac_i: Ext.getCmp('nmIncisoId').getValue(),
		pv_dsrazon_i: Ext.getCmp('comboMotivoCancelacionId').getValue(),
		pv_fecancel_ini_i: Ext.getCmp('fechaCnclcnDeId').getRawValue(),
		pv_fecancel_fin_i: Ext.getCmp('fechaCnclcnAId').getRawValue()
	};

	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);

}


function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}