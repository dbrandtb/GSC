
Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
	
///////// Inicia items de filterAreaForm

	var itemsPerPage = 20;
	var psm;
	var prow;
	var prec;
	
	var poliza = new Ext.form.TextField({
		id: 'id-textfield-poliza-consulta-presiniestros',
		fieldLabel: 'P&oacute;liza',
		name: 'parametrosConsultaPresiniestros.poliza',
		width: 210
	});
	
	var inciso = new Ext.form.TextField({
		id: 'id-textfield-inciso-consulta-presiniestros',
		fieldLabel: 'Inciso',
		name: 'parametrosConsultaPresiniestros.inciso',
		width: 210
	});
	
	var subinciso = new Ext.form.TextField({
		id: 'id-textfield-subinciso-consulta-presiniestros',
		fieldLabel: 'Subinciso',
		name: 'parametrosConsultaPresiniestros.subinciso',
		width: 210
	});

	var storeEmpresaOCorporativo = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: _ACTION_STORE_EMPRESA,
			method: 'POST'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaEmpresas',
            id: 'listaEmpresas'
	        },[
           {name: 'codigo', type: 'string', mapping:'value'},
           {name: 'descripcion', type: 'string', mapping:'label'}
        ]),
		remoteSort: true
    });
    storeEmpresaOCorporativo.load();

	var comboEmpresaOCorporativo = new Ext.form.ComboBox({
		id: 'id-combo-empresa-o-corporativo-consulta-presiniestros',
		store: storeEmpresaOCorporativo,
		displayField: 'descripcion',
		valueField: 'codigo',
		typeAhead: true,
		mode: 'local',
		triggerAction: 'all',
		emptyText: 'Seleccione Empresa o Corporativo...',
		selectOnFocus: true,
		forceSelection: true,
		width: 210,
		maxHeight: 230,
		fieldLabel: 'Empresa o Corporativo',
		name: 'descripcionEmpresa',
		focusAndSelect : function(record) {
			var index = typeof record === 'number' ? record : this.store.indexOf(record);
			this.select(index, this.isExpanded());
			this.onSelect(this.store.getAt(record), index, this.isExpanded());
		},
		onSelect : function(record, index, skipCollapse){
			if(this.fireEvent('beforeselect', this, record, index) !== false){
				this.setValue(record.data[this.valueField || this.displayField]);
				if( !skipCollapse ) {
					this.collapse();
				}
				this.lastSelectedIndex = index + 1;
				this.fireEvent('select', this, record, index);
			}

			var valor = record.get('codigo');
			Ext.getCmp('id-hidden-codigo-combo-empresa').setValue(valor);
		}
	});

	var asegurado = new Ext.form.TextField({
		id: 'id-textfield-asegurado-consulta-presiniestros',
		fieldLabel: 'Asegurado',
		name: 'parametrosConsultaPresiniestros.asegurado',
		width: 210
	});

	var storeAseguradora = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: _ACTION_STORE_ASEGURADORA,
			method: 'POST'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaAseguradoras',
            id: 'listaAseguradoras'
	        },[
           {name: 'codigo', type: 'string', mapping:'value'},
           {name: 'descripcion', type: 'string', mapping:'label'}
        ]),
		remoteSort: true
    });
    storeAseguradora.load();

	var comboAseguradora =new Ext.form.ComboBox({
		id: 'id-combo-aseguradora-consulta-presiniestros',
		store: storeAseguradora,
		displayField: 'descripcion',
		valueField: 'codigo',
		typeAhead: true,
		mode: 'local',
		triggerAction: 'all',
		emptyText: 'Seleccione Aseguradora...',
		selectOnFocus: true,
		forceSelection: true,
		width: 210,
		maxHeight: 230,
		fieldLabel: 'Aseguradora',
		name: 'descripcionAseguradora',
		focusAndSelect : function(record) {
			var index = typeof record === 'number' ? record : this.store.indexOf(record);
			this.select(index, this.isExpanded());
			this.onSelect(this.store.getAt(record), index, this.isExpanded());
		},
		onSelect : function(record, index, skipCollapse){
			if(this.fireEvent('beforeselect', this, record, index) !== false){
				this.setValue(record.data[this.valueField || this.displayField]);
				if( !skipCollapse ) {
					this.collapse();
				}
				this.lastSelectedIndex = index + 1;
				this.fireEvent('select', this, record, index);
			}

			var valor = record.get('codigo');
			Ext.getCmp('id-hidden-codigo-combo-aseguradora').setValue(valor);
		}
	});

	var storeRamo = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: _ACTION_STORE_RAMO,
			method: 'POST'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaRamos',
            id: 'listaRamos'
	        },[
           {name: 'codigo', type: 'string', mapping:'value'},
           {name: 'descripcion', type: 'string', mapping:'label'}
        ]),
		remoteSort: true
    });
    storeRamo.load();

	var comboRamo =new Ext.form.ComboBox({
		id: 'id-combo-ramo-consulta-presiniestros',
		store: storeRamo,
		displayField: 'descripcion',
		valueField: 'codigo',
		typeAhead: true,
		mode: 'local',
		triggerAction: 'all',
		emptyText: 'Seleccione Ramo...',
		selectOnFocus: true,
		forceSelection: true,
		width: 210,
		maxHeight: 230,
		fieldLabel: 'Ramo',
		name: 'descripcionRamo',
		focusAndSelect : function(record) {
			var index = typeof record === 'number' ? record : this.store.indexOf(record);
			this.select(index, this.isExpanded());
			this.onSelect(this.store.getAt(record), index, this.isExpanded());
		},
		onSelect : function(record, index, skipCollapse){
			if(this.fireEvent('beforeselect', this, record, index) !== false){
				this.setValue(record.data[this.valueField || this.displayField]);
				if( !skipCollapse ) {
					this.collapse();
				}
				this.lastSelectedIndex = index + 1;
				this.fireEvent('select', this, record, index);
			}

			var valor = record.get('codigo');
			Ext.getCmp('id-hidden-codigo-combo-ramo').setValue(valor);
		}
	});

	function consultarPresiniestros(psm) {

		var csm;
		var crow;
		var crec;
		
		function selectGridConsulta(sm, row, rec) {
     		csm  = sm;
			crow = row;
			crec = rec;	
   		}

		var consultaPoliza = new Ext.form.TextField({
			id: 'id-textfield-consultaPoliza',
			fieldLabel: 'P&oacute;liza',
			name: 'parametrosConsultaPresiniestros.poliza',
			width: 180,
			value: psm.getSelected().get('poliza'),
			disabled: true
		});
	
		var consultaInciso = new Ext.form.TextField({
			id: 'id-textfield-consultaInciso',
			fieldLabel: 'Inciso',
			name: 'parametrosConsultaPresiniestros.inciso',
			width: 180,
			value: psm.getSelected().get('inciso'),
			disabled: true
		});
	
		var consultaSubinciso = new Ext.form.TextField({
			id: 'id-textfield-consultaSubinciso',
			fieldLabel: 'Subinciso',
			name: 'parametrosConsultaPresiniestros.subinciso',
			width: 180,
			value: psm.getSelected().get('subinciso'),
			disabled: true
		});
		
		var consultaAsegurado = new Ext.form.TextField({
			id: 'id-textfield-consultaAsegurado',
			fieldLabel: 'Asegurado',
			name: 'parametrosConsultaPresiniestros.asegurado',
			width: 180,
			value: psm.getSelected().get('asegurado'),
			disabled: true
		});

		var consultaAseguradora = new Ext.form.TextField({
			id: 'id-textfield-consultaAseguradora',
			fieldLabel: 'Aseguradora',
			name: 'parametrosConsultaPresiniestros.aseguradora',
			width: 180,
			value: psm.getSelected().get('aseguradora'),
			disabled: true
		});
		
		var consultaRamo = new Ext.form.TextField({
			id: 'id-textfield-consultaRamo',
			fieldLabel: 'Ramo',
			name: 'parametrosConsultaPresiniestros.ramo',
			width: 180,
			value: psm.getSelected().get('ramo'),
			disabled: true
		});
		
		
		var smConsulta = new Ext.grid.RowSelectionModel({
			singleSelect: true,
			listeners: {
				rowselect: function(sm, row, rec) {
					selectGridConsulta(sm, row, rec);
				}
			}
		});

		var cmConsulta = new Ext.grid.ColumnModel([
		{
			header: 'Folio del Presiniestro',
			dataIndex: 'folio',
			width: 272,
			sortable: true
		},{
			header: 'Fecha de Registro del Presiniestro',
			dataIndex: 'fechaRegistro',
			width: 272,
			sortable: true
		}]);
		cmConsulta.defaultSortable = true;

		var storeConsulta = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url: _ACTION_CONSULTAR_PRESINIESTROS,
				method: 'POST'
			}),
			reader: new Ext.data.JsonReader({
				root: 'listaPresiniestrosPorPoliza',
				totalProperty: 'totalCount',
				successProperty : '@success'
			},[
			{name:'folio',			type:'string', 	mapping:'folio'},
			{name:'fechaRegistro',	type:'string', 	mapping:'fechaRegistro'}
			])
		});
		storeConsulta.setDefaultSort('folio','ASC');

		storeConsulta.baseParams['consultarPresiniestro.tipoPresiniestro'] = psm.getSelected().get('tipoPresiniestro');
		storeConsulta.baseParams['consultarPresiniestro.poliza'] = psm.getSelected().get('poliza');
		storeConsulta.baseParams['consultarPresiniestro.cdTipRam'] = psm.getSelected().get('cdTipRam');
		storeConsulta.removeAll();
		storeConsulta.load({
			params:{start: 0, limit: itemsPerPage},
			callback: function(r, options, success) {
				/*if (storeConsulta.getCount() > 0) {
					
				}else if (!success) {
					
				}*/
			}	 
		});

		var gridConsulta = new Ext.grid.GridPanel({
			id: 'id-grid-presiniestros-por-poliza',
			store: storeConsulta,
			stripeRows: true,
			cm: cmConsulta,
			width: 550,
			height: 200,
			buttonAlign: 'center',
			buttons: [{
				id: 'id-button-editar-presiniestro-por-poliza',
				text: 'Editar',
            	tooltip: 'Editar',
            	handler: function(){
					if ( Ext.getCmp('id-grid-presiniestros-por-poliza').getSelectionModel().hasSelection() ) {

						var urlEditarPresiniestro = 
						"?editarPresiniestro.tipoPresiniestro=" + psm.getSelected().get('tipoPresiniestro') +
						"&editarPresiniestro.folio=" + csm.getSelected().get('folio') +
						"&editarPresiniestro.cdunieco=" + psm.getSelected().get('cdunieco') +
						"&editarPresiniestro.cdramo=" + psm.getSelected().get('cdramo') +
						"&editarPresiniestro.estado=" + psm.getSelected().get('estado') +
						"&editarPresiniestro.nmpoliza=" + psm.getSelected().get('nmpoliza') +
						"&editarPresiniestro.poliza=" + psm.getSelected().get('poliza') +
						"&editarPresiniestro.inciso=" + psm.getSelected().get('inciso') +
						"&editarPresiniestro.subinciso=" + psm.getSelected().get('subinciso') +
						"&editarPresiniestro.cdEmpresaOCorporativo=" + psm.getSelected().get('cdEmpresaOCorporativo') +
						"&editarPresiniestro.empresaOCorporativo=" + psm.getSelected().get('empresaOCorporativo') +
						"&editarPresiniestro.cdAseguradora=" + psm.getSelected().get('cdAseguradora') +
						"&editarPresiniestro.aseguradora=" + psm.getSelected().get('aseguradora') +
						"&editarPresiniestro.asegurado=" + psm.getSelected().get('asegurado') +
						"&editarPresiniestro.inicioVigencia=" + psm.getSelected().get('inicioVigencia') +
						"&editarPresiniestro.finVigencia=" + psm.getSelected().get('finVigencia') +
						"&editarPresiniestro.primaTotal=" + psm.getSelected().get('primaTotal') +
						"&editarPresiniestro.formaPago=" + psm.getSelected().get('formaPago') +
						"&editarPresiniestro.instrumentoPago=" + psm.getSelected().get('instrumentoPago') +
						"&editarPresiniestro.cdTipRam=" + psm.getSelected().get('cdTipRam') +
						"&editarPresiniestro.numeroSuplemento=" + psm.getSelected().get('numeroSuplemento');
						window.location.href = _ACTION_EDITAR_PRESINIESTRO + urlEditarPresiniestro;//'/presiniestros/editarPresiniestro.action' + urlEditarPresiniestro;
						
					} else {
						Ext.Msg.alert('Aviso', 'Selecciona un registro para realizar esta operaci&oacute;n');
					}
				}
			},{
				id: 'id-button-exportar-presiniestro-por-poliza',
				text: 'Exportar',
            	tooltip: 'Exporta datos a otros formatos',
            	handler: function(){

            		if ( storeConsulta.getCount() > 0 ) {

						showExportDialog( _ACTION_EXPORTAR_PRESINIESTROS );

					}

				}
			},{
				text: 'Regresar',
            	tooltip: 'Regresar',
            	handler: function(){
					windowConsulta.close();
				}
			}],
			height: 200,
			width: 550,
        	buttonAlign: 'center',
        	sm: smConsulta,
			bbar: new Ext.PagingToolbar({
				pageSize: itemsPerPage,
				store: storeConsulta,									            
				displayInfo: true,
				displayMsg: '<span style="color:white;">Mostrando registros {0} - {1} de {2}</span>',
    	    	emptyMsg: '<span style="color:white;">No hay resultados</span>',
    			beforePageText: 'P&aacute;gina',
    			afterPageText: 'de {0}'
			})        		
		});

		var filterConsultaForm = new Ext.form.FormPanel({
			id: 'id-formpanel-consultar-presiniestros-por-poliza',
			layout: 'form',
			bodyStyle: 'background:white',
			buttonAlign: 'center',
			labelAlign: 'right',
			labelWidth: 70,
			border: false,
			width: 550,
			autoHeight : true,
			items:[{
				layout:'form',
				border:false,
				items:[{
					bodyStyle: 'background:white',
					labelWidth: 70,
					layout: 'form',
					frame: true,
					baseCls: '',
					buttonAlign: 'center',
					items:[{

						id: 'id-form-datos-presiniestros-por-poliza',
						layout: 'form',
						border: false,
						width: 540,
						items:[{
							layout: 'column',
							border: false,
							items:[{
								columnWidth: .5,
								layout: 'form',
								width: 270,
								border: false,
								items: [ consultaPoliza, consultaInciso, consultaSubinciso ]
							},{
								columnWidth: .5,
								layout: 'form',
								width: 270,
								border: false,
								items: [ consultaAsegurado, consultaAseguradora, consultaRamo ]
							}]
						}]

					}]
				}]
			}]
		});

		var windowConsulta = new Ext.Window({
			title: 'Presiniestros por P&oacute;liza',
			width: 580,
			autoHeight: true,
			autoScroll: true,
			y: 60,
			layout: 'fit',
			modal: true,
			plain: true,
			bodyStyle: 'padding:5px;',
			buttonAlign: 'center',
			//items: consultarForm
			items:[filterConsultaForm,gridConsulta]
		});
		windowConsulta.show();

	} // termina function consultarPresiniestros


	
   function selectGrid(sm, row, rec){
     psm  = sm;
     prow = row;
     prec = rec;
   }
	
	var sm2 = new Ext.grid.RowSelectionModel({
		singleSelect: true,
		listeners: {
			rowselect: function(sm, row, rec) {
				selectGrid(sm, row, rec);
			}
		}
	});

	var cm = new Ext.grid.ColumnModel([
	{
		header: 'P&oacute;liza',
		dataIndex: 'poliza',
		width: 100,
		sortable: true
	},{
		header: 'Inciso',
		dataIndex: 'inciso',
		width: 70,
		sortable: true
	},{
		header: 'Subinciso',
		dataIndex: 'subinciso',
		width: 70,
		sortable: true
	},{
		header: 'Empresa o Corporativo',
		dataIndex: 'empresaOCorporativo',
		width: 150,
		sortable: true
	},{		
		header: 'Aseguradora',
		dataIndex: 'aseguradora',
		width: 90,
		sortable: true
	},{
		header: 'Asegurado',
		dataIndex: 'asegurado',
		width: 200,
		sortable: true
	},{
		header: 'Ramo',
		dataIndex: 'ramo',
		width: 200,
		sortable: true
	},{
		header: 'Inicio Vigencia',
		dataIndex: 'inicioVigencia',
		width: 100,
		sortable: true,
		renderer: Ext.util.Format.dateRenderer('d/m/Y')
	},{
		header: 'Fin Vigencia',
		dataIndex: 'finVigencia',
		width: 100,
		sortable: true,
		renderer: Ext.util.Format.dateRenderer('d/m/Y')
	},{
		header: 'Prima Total',
		dataIndex: 'primaTotal',
		width: 80,
		sortable: true,
		renderer: Ext.util.Format.usMoney
	},{
		header: 'Forma de Pago',
		dataIndex: 'formaPago',
		width: 100,
		sortable: true
	},{
		header: 'Instrumento de Pago',
		dataIndex: 'instrumentoPago',
		width: 150,
		sortable: true
	},{
		header: '',
		width: 0,
		dataIndex: 'tipoPresiniestro',
		hidden: true
	},{
		header: '',
		width: 0,
		dataIndex: 'cdEmpresaOCorporativo',
		hidden: true
	},{
		header: '',
		width: 0,
		dataIndex: 'cdAseguradora',
		hidden: true
	},{
		header: '',
		width: 0,
		dataIndex: 'cdunieco',
		hidden: true
	},{
		header: '',
		width: 0,
		dataIndex: 'cdramo',
		hidden: true
	},{
		header: '',
		width: 0,
		dataIndex: 'estado',
		hidden: true
	},{
		header: '',
		width: 0,
		dataIndex: 'nmpoliza',
		hidden: true
	},{
		header: '',
		width: 0,
		dataIndex: 'cdTipRam',
		hidden: true
	},{
		header: '',
		width: 0,
		dataIndex: 'numeroSuplemento',
		hidden: true
	}]);
	cm.defaultSortable = true;

	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: _ACTION_BUSCAR_PRESINIESTROS,
			method: 'POST'
		}),
		reader: new Ext.data.JsonReader({
			root: 'listaConsultaPresiniestros',
			totalProperty: 'totalCount',
			id: 'consultaPresiniestroReader',
			successProperty : '@success'
		},[
		{name:'cdunieco',				type:'string', 		mapping:'cdunieco'},
		{name:'cdramo',					type:'string', 		mapping:'cdramo'},
		{name:'estado',					type:'string', 		mapping:'estado'},
		{name:'nmpoliza',				type:'string', 		mapping:'nmpoliza'},
		{name:'poliza',					type:'string', 		mapping:'poliza'},
		{name:'inciso',					type:'string', 		mapping:'inciso'},		
		{name:'subinciso', 				type:'string', 		mapping:'subinciso'},
		{name:'cdEmpresaOCorporativo',	type:'string', 		mapping:'cdEmpresaOCorporativo'},
		{name:'empresaOCorporativo',	type:'string', 		mapping:'empresaOCorporativo'},
		{name:'cdAseguradora', 			type:'string', 		mapping:'cdAseguradora'},
		{name:'aseguradora', 			type:'string', 		mapping:'aseguradora'},
		{name:'asegurado',				type:'string', 		mapping:'asegurado'},
		{name:'ramo', 					type:'string', 		mapping:'ramo'},
		{name:'inicioVigencia', 		type:'string', 		mapping:'inicioVigencia'},
		{name:'finVigencia', 			type:'string', 		mapping:'finVigencia'},
		{name:'primaTotal', 			type:'float', 		mapping:'primaTotal'},
		{name:'formaPago', 				type:'string', 		mapping:'formaPago'},
		{name:'instrumentoPago', 		type:'string', 		mapping:'instrumentoPago'},
		{name:'tipoPresiniestro', 		type:'string', 		mapping:'tipoPresiniestro'},
		{name:'cdTipRam', 				type:'string', 		mapping:'cdTipRam'},
		{name:'numeroSuplemento', 		type:'string', 		mapping:'numeroSuplemento'}
		])
	});
	
	var grid = new Ext.grid.GridPanel({
		id: 'id-grid-consulta-presiniestro',
		store: store,
		title: '<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
		stripeRows: true,
		cm: cm,
		width: 750,
		buttons: [{
			id: 'id-button-agregar',
			text: 'Agregar',
            tooltip: 'Agregar',
            handler: function(){
				if ( Ext.getCmp('id-grid-consulta-presiniestro').getSelectionModel().hasSelection() ) {

					var urlAgregarPresiniestro = 
					"?agregarPresiniestro.tipoPresiniestro=" + psm.getSelected().get('tipoPresiniestro') +
					"&agregarPresiniestro.cdunieco=" + psm.getSelected().get('cdunieco') +
					"&agregarPresiniestro.cdramo=" + psm.getSelected().get('cdramo') +
					"&agregarPresiniestro.estado=" + psm.getSelected().get('estado') +
					"&agregarPresiniestro.nmpoliza=" + psm.getSelected().get('nmpoliza') +
					"&agregarPresiniestro.poliza=" + psm.getSelected().get('poliza') +
					"&agregarPresiniestro.inciso=" + psm.getSelected().get('inciso') +
					"&agregarPresiniestro.subinciso=" + psm.getSelected().get('subinciso') +
					"&agregarPresiniestro.cdEmpresaOCorporativo=" + psm.getSelected().get('cdEmpresaOCorporativo') +
					"&agregarPresiniestro.empresaOCorporativo=" + psm.getSelected().get('empresaOCorporativo') +
					"&agregarPresiniestro.cdAseguradora=" + psm.getSelected().get('cdAseguradora') +
					"&agregarPresiniestro.aseguradora=" + psm.getSelected().get('aseguradora') +
					"&agregarPresiniestro.asegurado=" + psm.getSelected().get('asegurado') +
					"&agregarPresiniestro.inicioVigencia=" + psm.getSelected().get('inicioVigencia') +
					"&agregarPresiniestro.finVigencia=" + psm.getSelected().get('finVigencia') +
					"&agregarPresiniestro.primaTotal=" + psm.getSelected().get('primaTotal') +
					"&agregarPresiniestro.formaPago=" + psm.getSelected().get('formaPago') +
					"&agregarPresiniestro.instrumentoPago=" + psm.getSelected().get('instrumentoPago') +
					"&agregarPresiniestro.cdTipRam=" + psm.getSelected().get('cdTipRam') +
					"&agregarPresiniestro.numeroSuplemento=" + psm.getSelected().get('numeroSuplemento');
					
					window.location.href = _ACTION_AGREGAR_PRESINIESTRO + urlAgregarPresiniestro;//'/presiniestros/agregarPresiniestro.action' + urlAgregarPresiniestro;
					
				} else {
					Ext.Msg.alert('Aviso', 'Selecciona un registro para realizar esta operaci&oacute;n');
				}
			}
		},{
			id: 'id-button-exportar',
			text: 'Exportar',
   			tooltip: 'Exporta datos a otros formatos',
            handler: function(){

            	if ( store.getCount() > 0 ) {
					var params =
					'?parametrosConsultaPresiniestros.poliza=' + Ext.getCmp('id-textfield-poliza-consulta-presiniestros').getValue() +
					'&parametrosConsultaPresiniestros.inciso=' + Ext.getCmp('id-textfield-inciso-consulta-presiniestros').getValue() +
					'&parametrosConsultaPresiniestros.subinciso=' + Ext.getCmp('id-textfield-subinciso-consulta-presiniestros').getValue() +
					'&parametrosConsultaPresiniestros.empresa=' + Ext.getCmp('id-hidden-codigo-combo-empresa').getValue() + 
					'&parametrosConsultaPresiniestros.asegurado=' + Ext.getCmp('id-textfield-asegurado-consulta-presiniestros').getValue() +
					'&parametrosConsultaPresiniestros.aseguradora=' + Ext.getCmp('id-hidden-codigo-combo-aseguradora').getValue() +
					'&parametrosConsultaPresiniestros.ramo=' + Ext.getCmp('id-hidden-codigo-combo-ramo').getValue();

					showExportDialog( _ACTION_EXPORTAR_BUSQUEDA + params );
            	}

			}
		},{
			id: 'id-button-consultar',
			text: 'Consultar Presiniestros',
            tooltip: 'Consultar Presiniestros',
            handler: function(){
				if ( Ext.getCmp('id-grid-consulta-presiniestro').getSelectionModel().hasSelection() ) {

					consultarPresiniestros(psm);

				} else {
					Ext.Msg.alert('Aviso', 'Selecciona un registro para realizar esta operaci&oacute;n');
				}
			}
		}],
		height: 250,
		width: 750,
        buttonAlign: 'center',        
        sm: sm2,
		bbar: new Ext.PagingToolbar({
			pageSize: itemsPerPage,
			store: store,									            
			displayInfo: true,
			displayMsg: '<span style="color:white;">Mostrando registros {0} - {1} de {2}</span>',
    	    emptyMsg: '<span style="color:white;">No hay resultados</span>',
    		beforePageText: 'P&aacute;gina',
    		afterPageText: 'de {0}'
		})        		
	});

///////// Inicia filterAreaForm
  
	var filterAreaForm = new Ext.form.FormPanel({
		id: 'id-formpanel-busqueda-consulta-presiniestros',
		title: '<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">B&uacute;squeda</span>',
		layout: 'form',
		bodyStyle: 'padding:5px 5px 0',
		buttonAlign: 'center',
		labelAlign: 'right',
		labelWidth: 150,
		border: false,
		width: 760,
		autoHeight : true,
		items:[ 
			{xtype:'hidden', id:'id-hidden-codigo-combo-aseguradora', name:'parametrosConsultaPresiniestros.aseguradora'},
			{xtype:'hidden', id:'id-hidden-codigo-combo-ramo', name:'parametrosConsultaPresiniestros.ramo'},
			{xtype:'hidden', id:'id-hidden-codigo-combo-empresa', name:'parametrosConsultaPresiniestros.empresa'},
			{
				layout:'form',
				border:false,
				items:[{
					bodyStyle: 'background:white',
					labelWidth: 150,
					layout: 'form',
					frame: true,
					baseCls: '',
					buttonAlign: 'center',
					items:[{	

						id: 'id-form-busqueda-presiniestros',
						layout: 'form',
						border: false,
						width: 720,
						items:[{
							layout: 'column',
							border: false,
							items:[{
								columnWidth: .51,
								layout: 'form',
								width: 370,
								border: false,
								items: [ poliza, inciso, subinciso, comboEmpresaOCorporativo ]
							},{
								columnWidth: .49,
								layout: 'form',
								width: 350,
								labelWidth: 100,
								border: false,
								items: [ asegurado, comboAseguradora, comboRamo ]
							}]
						}]
					}],
					buttons:[{
						text:'Buscar',
						handler:function(){

							if ( filterAreaForm.form.isValid() ) {

								store.baseParams['parametrosConsultaPresiniestros.poliza']= Ext.getCmp('id-textfield-poliza-consulta-presiniestros').getValue();
								store.baseParams['parametrosConsultaPresiniestros.inciso']= Ext.getCmp('id-textfield-inciso-consulta-presiniestros').getValue();
								store.baseParams['parametrosConsultaPresiniestros.subinciso']= Ext.getCmp('id-textfield-subinciso-consulta-presiniestros').getValue();
								store.baseParams['parametrosConsultaPresiniestros.empresa']= Ext.getCmp('id-hidden-codigo-combo-empresa').getValue();
								store.baseParams['parametrosConsultaPresiniestros.asegurado']= Ext.getCmp('id-textfield-asegurado-consulta-presiniestros').getValue();
								store.baseParams['parametrosConsultaPresiniestros.aseguradora']= Ext.getCmp('id-hidden-codigo-combo-aseguradora').getValue();
								store.baseParams['parametrosConsultaPresiniestros.ramo']= Ext.getCmp('id-hidden-codigo-combo-ramo').getValue();
								store.removeAll();
								store.load({
									params:{start: 0, limit: itemsPerPage},
									callback: function(r, options, success) {
										if ( store.getCount() == 0 ) {
											Ext.MessageBox.alert('Aviso', 'No se encontraron registros');
                  							store.removeAll();
										} else if ( !success ) {
											var mensajeRespuesta = Ext.util.JSON.decode(response.responseText).mensajeRespuesta;
											Ext.MessageBox.alert('Error', mensajeRespuesta);
										}
									}
								});
								Ext.getCmp('id-grid-consulta-presiniestro').getBottomToolbar().updateInfo();

							} else {
								Ext.MessageBox.alert('Error', 'Favor de llenar los campos requeridos');
							}
						}
					},{
						text:'Cancelar',
						handler: function(){
							filterAreaForm.form.reset();
						}
					}]
				}]
			}]
		});						
	
	var resultAreaForm = new Ext.form.FormPanel({
		layout: 'form',
		id: 'resultArea',
        border: false,
        bodyStyle: 'padding:5px 5px 0',
        autoHeight: true,
		width: 760,
		items: [grid]
	});

	var panelPrincipal = new Ext.Panel({
        region: 'north',
        title: 'Presiniestros',
        autoHeight : true,
        width: 760,
        id:'panelPrincipal',
        items: [ filterAreaForm, resultAreaForm ] 
    
    });
	panelPrincipal.render('items');
	
	
});
