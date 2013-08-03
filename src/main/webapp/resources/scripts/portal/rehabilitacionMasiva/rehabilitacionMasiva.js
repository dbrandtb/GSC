Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

	var idRegresar = new Ext.form.Hidden({
        id: 'idRegresar',
        name: 'idRegresar',
        value:''
    });

    var storeAseguradora = new Ext.data.Store({
        	proxy: new Ext.data.HttpProxy({
            url: _COMBO_ASEGURADORA,
            method: 'POST'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaAseguradora',
            id: 'storeAseguradora'
        },[
                {name: 'label', type: 'string', mapping:'label'},
                {name: 'value', type: 'string', mapping:'value'}
        ])
    });
    
    var storeProducto = new Ext.data.Store({
        	proxy: new Ext.data.HttpProxy({
            url: _COMBO_PRODUCTO,
            method: 'POST'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaProducto',
            id: 'storeProducto'
        },[
                {name: 'label', type: 'string', mapping:'label'},
                {name: 'value', type: 'string', mapping:'value'}
        ]),
        remoteSort: true
    });
    
	var el_formpanel = new Ext.FormPanel ({
			id: 'el_formpanel',
			renderTo: "formulario",
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('el_formpanel',helpMap,'Rehabilitaci&oacute;n Masiva')+'</span>',
            frame : true,
            width : 700,
            height: 190,
            bodyStyle: 'background:white',
            waitMsgTarget : true,
            successProperty: 'success',
            items: [
            		{
	            	  layout: 'table',
	            	  layoutConfig: { columns: 2},
	            	  labelWidth: 100,
	            	  items: [
	            				{	
			            			layout:'column',
			            			html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
			            			baseCls: '',
			            			colspan: 2
	            				},
	            				{
	            					baseCls: ''
	            				},
			            		{
			            			baseCls: ''
			            		},
	            				{	
		            				baseCls: '',
		            				layout: 'form',
		            				width: 300,
		            				items: [
		            							idRegresar,
			            						{
			            						xtype: 'textfield', 
									            fieldLabel: getLabelFromMap('Asegurado',helpMap,'Asegurado'),
								                tooltip: getToolTipFromMap('Asegurado',helpMap,'Asegurado'),			          		
							                    hasHelpIcon:getHelpIconFromMap('Asegurado',helpMap),
							 					Ayuda:getHelpTextFromMap('Asegurado',helpMap),
			            						width: 170,
			            						id: 'Asegurado'
			            						},
												{
												xtype: 'combo', 
												tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
												store: storeProducto,
												width: 170,
												mode: 'local',
												typeAhead: true,
												triggerAction: 'all',
												valueField: 'label', // antes value
												displayField: 'label',
												fieldLabel: getLabelFromMap('Producto',helpMap,'Producto'),
												tooltip: getToolTipFromMap('Producto',helpMap,'Producto'),
												hasHelpIcon:getHelpIconFromMap('Producto',helpMap),								 
												Ayuda: getHelpTextFromMap('Producto',helpMap),
												emptyText: 'Seleccione...',
												selectOnFocus:true,
												id: 'Producto'
												},
												{
												xtype: 'textfield', 
									            fieldLabel: getLabelFromMap('Inciso',helpMap,'Inciso'),
								                tooltip: getToolTipFromMap('Inciso',helpMap,'Inciso'),			          		
							                    hasHelpIcon:getHelpIconFromMap('Inciso',helpMap),
							 					Ayuda:getHelpTextFromMap('Inciso',helpMap),
												width: 150,
												id: 'Inciso'
												}
		            						]
	            				},
								{
									layout: 'form',
									width: 300,
									items: [
												{
												xtype: 'combo',
												id: 'Aseguradora',
												tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
												store: storeAseguradora,
												width: 170,
												mode: 'local',
												typeAhead: true,
												triggerAction: 'all',
												valueField: 'label', // antes value
												displayField: 'label',
												fieldLabel: getLabelFromMap('Aseguradora',helpMap,'Aseguradora'),
												tooltip: getToolTipFromMap('Aseguradora',helpMap,'Aseguradora'),
												hasHelpIcon:getHelpIconFromMap('Aseguradora',helpMap),
												Ayuda: getHelpTextFromMap('Aseguradora',helpMap),
												emptyText: 'Seleccione...',
												selectOnFocus:true
												},
												{
												xtype: 'textfield', 
									            fieldLabel: getLabelFromMap('Poliza',helpMap,'P&oacute;liza'),
								                tooltip: getToolTipFromMap('Poliza',helpMap,'P&oacute;liza'),			          		
							                    hasHelpIcon:getHelpIconFromMap('Poliza',helpMap),
							 					Ayuda:getHelpTextFromMap('Poliza',helpMap),
							 					width: 150,
												id: 'Poliza'
												}
											]
								}
	            			],
	       			buttonAlign: 'center',
	       			labelAlign: 'right',
	       			buttons: [
	       						{
		       						text: getLabelFromMap('rhbltMnlBtnBsc',helpMap,'Buscar'),
								    tooltip:getToolTipFromMap('rhbltMnlBtnBsc',helpMap,'Busca Rehabilitaci&oacute;n Masiva'), 
	       							handler: function () {
	       										reloadGrid(grid);
	       								}
	       						},
	       						{
		       						text: getLabelFromMap('rhbltMnlBtnCnclr',helpMap,'Cancelar'),
								    tooltip:getToolTipFromMap('rhbltMnlBtnCnclr',helpMap,'Cancela Busqueda Rehabilitaci&oacute;n Masiva'), 
	       					 		handler: function() {
	       					 					el_formpanel.form.reset();
	       					 			}
	       					 	}
							]
					}
				]
	});
	
	var _proxy = new Ext.data.HttpProxy({url: _ACTION_BUSCAR_POLIZAS_A_REHABILITAR, method: 'POST'});
	
	var _reader = new Ext.data.JsonReader(
		{root: 'listaRehabilitacionMasiva',totalProperty: 'totalCount',successProperty: '@success'},
		[
       		{name: 'seleccionar', type: 'string', mapping: 'seleccionar'},
       		{name: 'cdAseguradora',   type: 'string',  mapping:'cdAseguradora'},
       		{name: 'cdMoneda',   type: 'string',  mapping:'cdMoneda'},
       		{name: 'cdPerson',   type: 'string',  mapping:'cdPerson'},
       		{name: 'cdProducto',   type: 'string',  mapping:'cdProducto'},
			{name: 'cdRazon',  type: 'string',  mapping:'cdRazon'},
       		{name: 'comentarios',  type: 'string',  mapping:'comentarios'},
       		{name: 'dsAsegurado', type: 'string',  mapping:'dsAsegurado'},
       		{name: 'dsAseguradora', type: 'string',  mapping:'dsAseguradora'},
       		{name: 'dsProducto', type: 'string',  mapping:'dsProducto'},
       		{name: 'dsRazon', type: 'string',  mapping:'dsRazon'},
       		{name: 'estado', type: 'string',  mapping:'estado'},
       		{name: 'feCancel', type: 'string',  mapping:'feCancel'},
       		{name: 'feEfecto', type: 'string',  mapping:'feEfecto'},
       		{name: 'feVencim', type: 'string',  mapping:'feVencim'},
       		{name: 'nmCancel', type: 'string',  mapping:'nmCancel'},
       		{name: 'nmInciso', type: 'string',  mapping:'nmInciso'},
       		{name: 'nmPoliza', type: 'string',  mapping:'nmPoliza'},
       		{name: 'nmSuplem', type: 'string',  mapping:'nmSuplem'},
       		{name: 'nmPoliex', type: 'string',  mapping:'nmPoliex'},
       		{name: 'incisoext', type: 'string',  mapping:'incisoext'}
       		
		]);
		
	var _store = new Ext.data.Store(
		{
		 proxy: _proxy,
		 reader: _reader
		});
		
		
	var eleccionColumnReha = new Ext.grid.CheckColumn({
      id:'eleccionColumnRehaId',
      header: getLabelFromMap('eleccionColumnRehaId',helpMap,'Sel'),
      tooltip: getToolTipFromMap('eleccionColumnRehaId', helpMap,'Seleccionar Elementos'),
      dataIndex: 'seleccionar',
      value:false,
      width: 55
    });
    
	 var _cm = new Ext.grid.ColumnModel([
		eleccionColumnReha,
		{
		header: getLabelFromMap('cmAsegurado', helpMap, 'Asegurado'),
		tooltip: getToolTipFromMap('cmAsegurado', helpMap,'Asegurado'),
		dataIndex: 'dsAsegurado',
		sortable: true,
		width:190
		},
		{
		header: getLabelFromMap('cmAseguradora', helpMap, 'Aseguradora'),
		tooltip: getToolTipFromMap('cmAseguradora', helpMap,'Aseguradora'),
		dataIndex: 'dsAseguradora',
		sortable: true,
		width:145
		},
	    {
		header: getLabelFromMap('cmProducto', helpMap, 'Producto'),
		tooltip: getToolTipFromMap('cmProducto', helpMap,'Producto'),
	    dataIndex: 'dsProducto',
	    sortable: true,
	    width:145
	    },
	    {
		header: getLabelFromMap('cmPoliza', helpMap, 'P&oacute;liza'),
		tooltip: getToolTipFromMap('cmPoliza', helpMap,'P&oacute;liza'),
	    dataIndex: 'nmPoliex',
	    sortable: true
	    },
	    {
		header: getLabelFromMap('cmInciso', helpMap, 'Inciso'),
		tooltip: getToolTipFromMap('cmInciso', helpMap,'Inciso'),
	    dataIndex: 'incisoext',
	    sortable: true
	    },
	    {
		header: getLabelFromMap('cmFchCnclcn', helpMap, 'Fecha Cancelaci&oacute;n'),
		tooltip: getToolTipFromMap('cmFchCnclcn', helpMap,'Fecha Cancelaci&oacute;n'),
	    dataIndex: 'feCancel',
	    sortable: true, 
	    renderer: renderDates
	    },
	    {
		header: getLabelFromMap('cmFchRhbltcn', helpMap, 'Fecha Rehabilitaci&oacute;n'),
		tooltip: getToolTipFromMap('cmFchRhbltcn', helpMap,'Fecha Rehabilitaci&oacute;n'),
	    dataIndex: '',
	    sortable: true
	    },
	    {
		header: getLabelFromMap('cmComentarios', helpMap, 'Comentarios'),
		tooltip: getToolTipFromMap('cmComentarios', helpMap,'Comentarios'),
	    dataIndex: 'comentarios',
	    sortable: true
	    },
	    {
	    dataIndex: 'estado', 
	    hidden :true
	    },
        {
        dataIndex: 'cdAseguradora', 
        hidden :true
        },
        {
        dataIndex: 'cdProducto', 
        hidden :true
        }
	 ]);
	 
	 function renderDates(value) {
		if (value != "") {
		     return Date.parseDate(value, 'Y-m-d H:i:s.g').format('d/m/Y');
		}
	 	return value;
	 }
	var grid = new Ext.grid.GridPanel({
			id:'grid',
	        renderTo:'grillaResultados',
	        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
	        store:_store,
			border:true,
	        cm: _cm,
	        //loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
	        buttonAlign: 'center',
			buttons:[{
					text: getLabelFromMap('rhbltMnlBtnDtll',helpMap,'Detalle'),
				    tooltip:getToolTipFromMap('rhbltMnlBtnDtll',helpMap,'Detalle'), 
            		handler:function(){
            				    if (getSelectedRecord(grid) != null) {
            				    	record=getSelectedRecord(grid);
            						var _url = _ACTION_DETALLE_POLIZA + '?cdRamo='+record.get('cdProducto')+'&estado='+record.get('estado')+'&nmPoliza='+record.get('nmPoliza')+'&producto='+record.get('dsProducto')+'&aseguradora='+record.get('dsAseguradora')+"&volver4=true"+"&idRegresar="+idRegresar.getValue(); 
            						window.location.href = _url;
            					}else {
		                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
            					}
            		}	            	
	            	},{	            	
					text: getLabelFromMap('rhbltMnlBtnRhbltr',helpMap,'Rehabilitar'),
				    tooltip:getToolTipFromMap('rhbltMnlBtnRhbltr',helpMap,'Rehabilitar'), 
            		handler:function() {
            					rehabilitarPoliza(_store);
                			}
	            	},{
					text: getLabelFromMap('rhbltMnlBtnCrgrArchv',helpMap,'Cargar Archivo'),
				    tooltip:getToolTipFromMap('rhbltMnlBtnCrgrArchv',helpMap,'Cargar Archivo'), 
                    handler:function(){
                        }
	                },{
					text: getLabelFromMap('rhbltMnlBtnExprtr',helpMap,'Exportar'),
				    tooltip:getToolTipFromMap('rhbltMnlBtnExprtr',helpMap,'Exportar Grilla'), 
                    handler:function(){
										var url = _ACTION_EXPORTAR + "?dsAsegurado=" + Ext.getCmp('el_formpanel').form.findField('Asegurado').getValue() +
     																 "&dsAseguradora=" + Ext.getCmp('el_formpanel').form.findField('Aseguradora').getValue() +
     																 "&dsProducto=" + Ext.getCmp('el_formpanel').form.findField('Producto').getValue() +
     																 "&nmPoliza=" + Ext.getCmp('el_formpanel').form.findField('Poliza').getValue() +
     																 "&nmInciso=" + Ext.getCmp('el_formpanel').form.findField('Inciso').getValue();
				                	 	showExportDialog( url );
                                 }
	            	
	            	}],
	                
	    	width:700,
	    	frame:true,
			height:320,
			plugins: [eleccionColumnReha],
			sm: new Ext.grid.RowSelectionModel({
				singleSelect: true,
				listeners: {
					rowselect: function(sm, row, rec) { 
				        _cdUnieco    = rec.get('cdCia');
				        _cdRamo      = rec.get('cdRamo');
				        _cdCliente   = rec.get('cdCliente');
				        _estado      = rec.get('estado');
				        _nmPoliza    = rec.get('nmPoliza');					//Numero de la poliza Actual Interna
				        _nmSituac    = rec.get('nmSituac');
				        _poliza      = rec.get('polizaAnterior');			//Numero de la poliza Anterior Externa
				        _producto    = rec.get('producto');
				        _aseguradora = rec.get('aseguradora');
				        _nmPolizaAnterior = rec.get('nmPolizaAnterior'); 	//Numero de la poliza Anterior Interna
				        _polizaRenovacion=rec.get('polizaRenovacion');		//Numero de la poliza Actual Externa (poliza de la renovacion)
				        _fechaEfectividadEndoso=rec.get('fechaRenova');
				        idRegresar.setValue( rec.get('nmPoliza') + "|" +
				        					 rec.get('feCancel') );
				        
					}
		        }
			}),
			stripeRows: true,
			collapsible:true,
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store: _store,
					displayInfo: true,
	                displayMsg: 'Mostrando registros {0} - {1} de {2}',
	                emptyMsg: "No hay registros para mostrar"
			    })
			});

	el_formpanel.render();
	function getFormattedDate (_date) {
		if (_date) {
			return Date.parseDate(_date, 'Y-m-d H:i:s.g').format('d/m/Y');
		} else return "";
	}
	
	function rehabilitarPoliza (elStore) {
	  var _seleccionados=0; 
	  var dt = new Date();
	  startMask(grid.id, getLabelFromMap('400017',helpMap,'Espere por favor...'));
	  var _params = "";
	  for (var i=0; i<elStore.getCount(); i++) {
	  	if ((elStore.getAt(i).data.seleccionar)== true){
		   _params +=   "rehabGrillaList[" + i + "].cdUnieco=" + elStore.getAt(i).data.cdAseguradora + "&" +
		      "&rehabGrillaList[" + i + "].cdRamo=" + elStore.getAt(i).data.cdProducto + "&" +
		      "&rehabGrillaList[" + i + "].estado=" + elStore.getAt(i).data.estado + "&" +
		      "&rehabGrillaList[" + i + "].nmPoliza=" + elStore.getAt(i).data.nmPoliza + "&" +
		      "&rehabGrillaList[" + i + "].feefecto=" + getFormattedDate(elStore.getAt(i).data.feEfecto) + "&" +
		      "&rehabGrillaList[" + i + "].fevencim=" + getFormattedDate(elStore.getAt(i).data.feVencim) + "&" +
		      "&rehabGrillaList[" + i + "].feCancel=" + getFormattedDate(elStore.getAt(i).data.feCancel) + "&" +
		      "&rehabGrillaList[" + i + "].feproren=" + getFormattedDate(dt.format('Y-m-d H:i:s.g')) + "&" +
		      "&rehabGrillaList[" + i + "].cdRazon=" + elStore.getAt(i).data.cdRazon + "&" +
		      "&rehabGrillaList[" + i + "].cdPerson=" + elStore.getAt(i).data.cdPerson + "&" +
		      "&rehabGrillaList[" + i + "].cdMoneda=" + elStore.getAt(i).data.cdMoneda + "&" +
		      "&rehabGrillaList[" + i + "].nmCancel=" + elStore.getAt(i).data.nmCancel + "&" +
		      "&rehabGrillaList[" + i + "].comentarios=" + elStore.getAt(i).data.comentarios + "&" +
		      "&rehabGrillaList[" + i + "].nmsuplem=" + elStore.getAt(i).data.nmSuplem + "&";
		      	++_seleccionados;
	  	}
	  }
	
	  if (_seleccionados > 0) {
	  	execConnection(_ACTION_REHABILITAR_POLIZA, _params, cbkRehabilitar);
	  	_seleccionados=0;
	  }
	  else
	  {
	  	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));	
	  }
	 }
	 
	function cbkRehabilitar (_success, _message, _response) {
		endMask();
		if (!_success) {
			Ext.Msg.alert('Error', _message);
		}else {
			Ext.Msg.alert('Aviso', _message, function() {reloadGrid(grid)});
		}
	}
	
///// DATOS - BOTON REGRESAR

	if ( _SESSION_PARAMETROS_REGRESAR ) {
		
		Ext.getCmp('Asegurado').setValue( _SESSION_PARAMETROS_REGRESAR.asegurado );
		Ext.getCmp('Poliza').setValue( _SESSION_PARAMETROS_REGRESAR.poliza );
		Ext.getCmp('Inciso').setValue( _SESSION_PARAMETROS_REGRESAR.inciso );
		storeAseguradora.load({
			callback: function(r,options,success) {
				Ext.getCmp('Aseguradora').setValue( _SESSION_PARAMETROS_REGRESAR.aseguradora );
			}
		});
		storeProducto.load({
			callback: function(r,options,success) {
				Ext.getCmp('Producto').setValue( _SESSION_PARAMETROS_REGRESAR.producto );
			}
		});

		_store.removeAll();
		_store.baseParams['idRegresar'] = _SESSION_PARAMETROS_REGRESAR.idRegresar;
		_store.baseParams['dsAsegurado'] = _SESSION_PARAMETROS_REGRESAR.asegurado;
		_store.baseParams['dsAseguradora'] = _SESSION_PARAMETROS_REGRESAR.aseguradora;
		_store.baseParams['dsProducto'] = _SESSION_PARAMETROS_REGRESAR.producto;
		_store.baseParams['nmInciso'] = _SESSION_PARAMETROS_REGRESAR.inciso;
		_store.baseParams['nmPoliza'] = _SESSION_PARAMETROS_REGRESAR.poliza;
		_store.load({
			callback: function(r,options,success) {
				function buscaRecord(stringIdRegresar) {
					var array = stringIdRegresar.split("|");
					var _nmpoliza = array[0];
					var _feCancel = array[1];
					var index = -1;
					var bandera = true;
					do {
						index = _store.find('nmPoliza', _nmpoliza, index + 1);
						var record = _store.getAt(index);
						if (record.get('feCancel') == _feCancel) {
							var record0 = _store.getAt(0);
							// Swap de Ext.Data.Record
							_store.remove(record0);
							_store.remove(record);
							_store.insert(0, record);
							_store.insert(index, record0);
							bandera = false;
						}
					} while (bandera);
					return;
				}
				if ( _store.getCount() > 1 )
					buscaRecord(_SESSION_PARAMETROS_REGRESAR.idRegresar);
				grid.getSelectionModel().selectRow(0);
			}
		});


	} else {
		storeAseguradora.load();
    	storeProducto.load();
	}

/////
	
});

function reloadGrid(grilla){
	var _params = {
			idRegresar: Ext.getCmp('el_formpanel').form.findField('idRegresar').getValue(),
     		dsAsegurado: Ext.getCmp('el_formpanel').form.findField('Asegurado').getValue(),
     		dsAseguradora: Ext.getCmp('el_formpanel').form.findField('Aseguradora').getValue(),
     		dsProducto: Ext.getCmp('el_formpanel').form.findField('Producto').getValue(),
     		nmPoliza: Ext.getCmp('el_formpanel').form.findField('Poliza').getValue(),
     		nmInciso: Ext.getCmp('el_formpanel').form.findField('Inciso').getValue()
	};
	reloadComponentStore(grilla, _params, cbkReload);
}
function cbkReload (_r, _o, _success, _store) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
		_store.removeAll();
	}
}