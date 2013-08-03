Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

    var swOblig = new Ext.grid.CheckColumn({
      id: 'cmCbDetPlanCId',
      header:getLabelFromMap('cmCbDetPlanCId',helpMap,'Obligatorio....'),
      tooltip:getToolTipFromMap('cmCbDetPlanCId',helpMap, 'Tilde si es obligatorio'),
      hasHelpIcon:getHelpIconFromMap('cmHdrDetPlanCClienteId',helpMap),								 
      Ayuda: getHelpTextFromMap('cmHdrDetPlanCClienteId',helpMap),
      dataIndex: 'swOblig',
      width: 80
    });

    // Definicion de las columnas de la grilla
        var cm = new Ext.grid.ColumnModel([
        	{
       		header: 'cdElemento',
       		dataIndex: 'cdElemento',
       		hidden: true
        	},
        	{
       		dataIndex: 'cdRamo',
       		hidden: true
        	},
        	{
       		dataIndex: 'cdPlan',
       		hidden: true
        	},
            {
            header: "Cod Cliente",
            dataIndex: 'cdPerson',
            width: 100,
            hidden :true
            },
            {
            id: 'cmHdrDetPlanCClienteId',
            header: getLabelFromMap('cmHdrDetPlanCClienteId',helpMap,'Cliente'),
            tooltip:getToolTipFromMap('cmHdrDetPlanCClienteId', helpMap,'Cliente del Plan'),
            hasHelpIcon:getHelpIconFromMap('cmHdrDetPlanCClienteId',helpMap),								 
            Ayuda: getHelpTextFromMap('cmHdrDetPlanCClienteId',helpMap),
            dataIndex: 'dsElemen',
            sortable: true,
            width: 100
            },
            {
            header: "Cod Aseguradora",
            dataIndex: 'cdUniEco',
            sortable: true,
            width: 100,
            hidden :true
            },
            {
            id: 'cmHdrDetPlanCAsegurId',
            header: getLabelFromMap('cmHdrDetPlanCAsegurId',helpMap,'Aseguradora'),
            tooltip:getToolTipFromMap('cmHdrDetPlanCAsegurId', helpMap,'Aseguradora'),
            hasHelpIcon:getHelpIconFromMap('cmHdrDetPlanCAsegurId',helpMap),								 
            Ayuda: getHelpTextFromMap('cmHdrDetPlanCClienteId',helpMap),
            dataIndex: 'dsUniEco',
            sortable: true,
            width: 84
            },
            {
            id: 'cmHdrDetPlanCClienteId',
            header: getLabelFromMap('cmHdrDetPlanCProductoId',helpMap,'Producto'),
            tooltip:getToolTipFromMap('cmHdrDetPlanCProductoId', helpMap,'Producto del Plan'),
            hasHelpIcon:getHelpIconFromMap('cmHdrDetPlanCProductoId',helpMap),								 
            Ayuda: getHelpTextFromMap('cmHdrDetPlanCProductoId',helpMap),
            dataIndex: 'dsRamo',
            sortable: true,
            width: 100
            },
            {
            id: 'cmHdrDetPlanCDsAsegurId',
            header: getLabelFromMap('cmHdrDetPlanCDsAsegurId',helpMap,'Plan'),
            tooltip:getToolTipFromMap('cmHdrDetPlanCDsAsegurId', helpMap,'Plan'),
            hasHelpIcon:getHelpIconFromMap('cmHdrDetPlanCDsAsegurId',helpMap),								 
            Ayuda: getHelpTextFromMap('cmHdrDetPlanCDsAsegurId',helpMap),
            
            dataIndex: 'desPlan',
            sortable: true,
            width: 100
            },
            {
            header: "Cod Tipo de Situación",
            dataIndex: 'cdTipSit',
            width: 100,
            hidden :true
            },
            {
            id: 'cmHdrDetPlanCTipSitId',
            header: getLabelFromMap('cmHdrDetPlanCTipSitId',helpMap,'Riesgo'),
            tooltip:getToolTipFromMap('cmHdrDetPlanCTipSitId', helpMap,'Riesgo'),
            hasHelpIcon:getHelpIconFromMap('cmHdrDetPlanCTipSitId',helpMap),								 
            Ayuda: getHelpTextFromMap('cmHdrDetPlanCTipSitId',helpMap),
            
            dataIndex: 'dsTipSit',
            sortable: true,
            width: 100
            },
             {
            header: "Cod Garantía",
            dataIndex: 'cdGarant',
            width: 100,
            hidden :true
            },
            {
            id: 'cmHdrDetPlanCGarId',
            header: getLabelFromMap('cmHdrDetPlanCGarId',helpMap,'Cobertura'),
            tooltip:getToolTipFromMap('cmHdrDetPlanCGarId', helpMap,'Cobertura'),
            hasHelpIcon:getHelpIconFromMap('cmHdrDetPlanCGarId',helpMap),								 
            Ayuda: getHelpTextFromMap('cmHdrDetPlanCGarId',helpMap),
            
            dataIndex: 'dsGarant',
            sortable: true,
            width: 100
            },
            swOblig
        ]);


 function test(){
 			store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_PLANCLIENTE
                }),

                reader: new Ext.data.JsonReader({
            	root:'MDetallePlanXCliente',
            	totalProperty: 'totalCount',
            	id: 'cdperson',
	            successProperty : '@success'
	        },[
	        {name: 'cdPerson',  type: 'string',  mapping:'cdPerson'},
            {name: 'dsElemen',  type: 'string',  mapping:'dsElemen'},
            {name: 'cdUniEco',  type: 'string',  mapping:'cdUniEco'},
            {name: 'dsUniEco',  type: 'string',  mapping:'dsUniEco'},
            {name: 'cdPlan',  type: 'string',  mapping:'cdPlan'},
            {name: 'desPlan',  type: 'string',  mapping:'dsPlan'},
            {name: 'cdRamo',  type: 'string',  mapping:'cdRamo'},
            {name: 'cdTipSit',  type: 'string',  mapping:'cdTipSit'},
            {name: 'dsTipSit',  type: 'string',  mapping:'dsTipSit'},
            {name: 'cdGarant',  type: 'string',  mapping:'cdGarant'},
            {name: 'dsGarant',  type: 'string',  mapping:'dsGarant'},
            {name: 'swOblig',  type: 'bool',  mapping:'swOblig'},
            
	        {name: 'cdElemento',  type: 'string',  mapping:'cdElemento'},
	        {name: 'dsRamo',  type: 'string',  mapping:'dsRamo'}
            
			])
        });
		return store;
 	}

    var grid2;


function createGrid(){
	grid2= new Ext.grid.GridPanel({
		id: 'grid2',
        el:'gridElementos',
        title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
        store:test(),
        buttonAlign:'center',
		border:true,
		loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
        cm: cm,
		buttons:[
        		{
        		text:getLabelFromMap('gridBtnAddPlanCId',helpMap,'Agregar'),
            	tooltip:getToolTipFromMap('gridBtnAddPlanCId',helpMap, 'Agrega un nuevo Plan'),
            	handler:function(){
	                agregar();
	                }
            	},{
        		text:getLabelFromMap('gridBtnEditPlanCId',helpMap,'Editar'),
            	tooltip:getToolTipFromMap('gridBtnEditPlanCId',helpMap, 'Edita un Plan'),
            	handler:function(){
            				var record = getSelectedRecord(grid2);
            				if (record != null) {
            					editar(record);
            	 			}else {Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
            	}
            	},
            	{
        		text:getLabelFromMap('gridBtnDelPlanCId',helpMap,'Eliminar'),
            	tooltip:getToolTipFromMap('gridBtnDelPlanCId',helpMap, 'Elimina un Plan'),
            	
            	handler:function(){
            				var record = getSelectedRecord(grid2);
            				if (record != null) {
				            	borrar(record);
            				}else {Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
            	}
            	},
            	{
        		text:getLabelFromMap('gridBtnExpPlanCId',helpMap,'Exportar'),
            	tooltip:getToolTipFromMap('gridBtnExpPlanCId',helpMap, 'Exporta un Plan'),
                handler: function () {

							var url = _ACTION_PLANESCLIENTE_EXPORT + '?codigoElemento=' + Ext.getCmp('incisosForm').form.findField('codigoElemento').getValue() +
									 '&codigoProducto=' + Ext.getCmp('incisosForm').form.findField(/*'descripcionProducto' -- ANTES*/'codigoProducto').getValue() + '&codigoPlan=' + Ext.getCmp('incisosForm').form.findField(/*'descripcionPlan' -- ANTES*/'codigoPlan').getValue() + '&codigoTipoSituacion=' + Ext.getCmp('incisosForm').form.findField('codigoTipoSituacion').getValue() + 
									 '&codigoCobertura=' + Ext.getCmp('incisosForm').form.findField(/*'garantia' -- ANTES*/'codigoCobertura').getValue() + "&codigoAseguradora=" + Ext.getCmp('incisosForm').form.findField(/*'aseguradora' -- ANTES*/'codigoAseguradora').getValue();
		                 	showExportDialog( url );                		
                		}
            	}/*,
            	{
        		id:'gridBtnBackPlanCId',
        		text:getLabelFromMap('gridBtnBackPlanCId',helpMap,'Regresar'),
            	tooltip:getToolTipFromMap('gridBtnBackPlanCId',helpMap, 'Regresa a la pantalla anterior'),
            	handler: function() {
            			window.location.href = _REGRESAR_BUSCAR_PLANES + "?codigoProducto=" + CODIGO_PRODUCTO + "&codigoPlan=" + CODIGO_PLAN + 
            									"&descripcionProducto=" + DESCRIPCION_PRODUCTO + "&descripcionPlan=" + DESCRIPCION_PLAN;
            	}
            	}*/],
    	width:600,
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

   /*var codigoProducto = new Ext.form.Hidden({
        fieldLabel: 'Codigo Producto',
        name:'codigoProducto',
        value: CODIGO_PRODUCTO
        
    });*/
    
   /* var codigoPlan = new Ext.form.Hidden({
        name:'codigoPlan',
        value: CODIGO_PLAN
        
    });*/

//////// NUEVO

	var storeProductos = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_PRODUCTOS_X_PLAN}),
		reader: new Ext.data.JsonReader({
			root: 'comboProductoPlan',
			id: 'codigo'
		},
		[
			{name: 'codigo', type: 'string',mapping:'codigo'},
			{name: 'descripcion', type: 'string',mapping:'descripcion'}
		])
	});
	storeProductos.load();

	var comboProducto = new Ext.form.ComboBox({
		id: 'cmbBqdaProductoId',
		tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}.{dsElemen}" class="x-combo-list-item">{descripcion}</div></tpl>',
		store: storeProductos,
		forceSelection: true,
		displayField:'descripcion',
		valueField:'codigo',
		hiddenName: 'codigoProducto',
		typeAhead: true,
		mode: 'local',
		triggerAction: 'all',
		fieldLabel: getLabelFromMap('cmbBqdaProductoId',helpMap,'Producto'),
		tooltip: getToolTipFromMap('cmbBqdaProductoId',helpMap, 'Productos'),
		hasHelpIcon:getHelpIconFromMap('cmbBqdaProductoId',helpMap),								 
		Ayuda: getHelpTextFromMap('cmbBqdaProductoId',helpMap),
		width: 300,
		allowBlank: true,
		//blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),                            
		emptyText: 'Seleccione Producto...',
		selectOnFocus:true,
		onSelect : function(record) {
			this.setValue(record.get("codigo"));
			this.collapse();
			storePlanes.removeAll();
			storeAseguradoras.removeAll();
			storeCoberturas.removeAll();

			storePlanes.reload({
				params : {
					cdRamo : record.get("codigo")
				}
			});
			storeAseguradoras.reload({
				params : {
					cdRamo : record.get("codigo")
				}
			});
			storeRiesgos.reload({
				params : {
					cdRamo : record.get("codigo"),
					cdPlan : incisosForm.findById('cmbBqdaPlanId')
							.getValue()
				}
			});
			storeCoberturas.reload({
				params : {
					cdRamo : record.get("codigo"),
					cdTipSit : incisosForm.findById('cmbBqdaRiesgoId')
							.getValue(),
					cdPlan : incisosForm.findById('cmbBqdaPlanId')
							.getValue()
				}
			});

			Ext.getCmp('cmbBqdaPlanId').clearValue();
			Ext.getCmp('cmbBqdaAseguradoraId').clearValue();
			Ext.getCmp('cmbBqdaRiesgoId').clearValue();
			Ext.getCmp('cmbBqdaCoberturaId').clearValue();
		}

	});
        
//////// NUEVO	

    var descripcionProducto = new Ext.form.TextField({
    	id:'descripcionProductoId',
        fieldLabel: getLabelFromMap('descripcionProductoId',helpMap,'Producto'),
        tooltip: getToolTipFromMap('descripcionProductoId',helpMap, 'Descripci&oacute;n del Producto del Plan'),
        hasHelpIcon:getHelpIconFromMap('descripcionProductoId',helpMap),								 
		Ayuda: getHelpTextFromMap('descripcionProductoId',helpMap),
        name:'descripcionProducto',
        width: 200
    });

//////// NUEVO

	var storePlanes = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: _ACTION_OBTENER_PLANES_PRODUCTO
		}),
		reader: new Ext.data.JsonReader({
			root: 'comboPlanProducto',
			id: 'codigo'
		},
		[
			{name: 'codigo', type: 'string',mapping:'codigo'},
			{name: 'descripcion', type: 'string',mapping:'descripcion'}
		])
	});
        
	var comboPlan = new Ext.form.ComboBox({
		id : 'cmbBqdaPlanId',
		tpl : '<tpl for="."><div ext:qtip="{codigo}.{descripcion}.{dsElemen}" class="x-combo-list-item">{descripcion}</div></tpl>',
		store : storePlanes,
		forceSelection : true,
		displayField : 'descripcion',
		valueField : 'codigo',
		hiddenName : 'codigoPlan',
		typeAhead : true,
		mode : 'local',
		triggerAction : 'all',
		fieldLabel : getLabelFromMap('cmbBqdaPlanId', helpMap, 'Plan'),
		tooltip : getToolTipFromMap('cmbBqdaPlanId', helpMap,
				'Planes'),
		hasHelpIcon : getHelpIconFromMap('cmbBqdaPlanId', helpMap),
		Ayuda : getHelpTextFromMap('cmbBqdaPlanId', helpMap),
		width : 300,
		allowBlank: true,
		//blankText : getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
		emptyText : 'Seleccione Plan...',
		selectOnFocus : true,
		onSelect : function(record) {
			this.setValue(record.get("codigo"));
			this.collapse();
			storeRiesgos.reload({
				params : {
					cdRamo : incisosForm.findById('cmbBqdaProductoId')
							.getValue(),
					cdPlan : record.get("codigo")
				}
			});
			storeCoberturas.reload({
				params : {
					cdRamo : incisosForm.findById('cmbBqdaProductoId')
							.getValue(),
					cdTipSit : incisosForm.findById('cmbBqdaRiesgoId')
							.getValue(),
					cdPlan : record.get("codigo")
				}
			});
			Ext.getCmp('cmbBqdaAseguradoraId').clearValue();
			Ext.getCmp('cmbBqdaRiesgoId').clearValue();
			Ext.getCmp('cmbBqdaCoberturaId').clearValue();
		}
	});
    
//////// NUEVO
    
    var descripcionPlan = new Ext.form.TextField({
    	id:'descripcionPlanId',
        fieldLabel: getLabelFromMap('descripcionPlanId',helpMap,'Plan'),
        tooltip: getToolTipFromMap('descripcionPlanId',helpMap, 'Descripci&oacute;n del Plan del cliente'),
        hasHelpIcon:getHelpIconFromMap('descripcionPlanId',helpMap),								 
		Ayuda: getHelpTextFromMap('descripcionPlanId',helpMap),
        name:'descripcionPlan',
        width: 200
    });
    
//////// NUEVO
    
	var storeClientes = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: _ACTION_OBTENER_CLIENTES_CORP
		}),
        reader: new Ext.data.JsonReader({
        	root: 'clientesCorp',
        	id: 'cdElemento'
		},[
			{name: 'cdElemento', type: 'string',mapping:'cdElemento'},
			{name: 'cdPerson', type: 'string',mapping:'cdPerson'},
			{name: 'dsElemen', type: 'string',mapping:'dsElemen'}
        ])
	});
	storeClientes.load();
	
	var comboCliente = new Ext.form.ComboBox({
		id : 'cmbBqdaClienteId',
		tpl : '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
		store : storeClientes,
		forceSelection : true,
		displayField : 'dsElemen',
		valueField : 'cdElemento',
		hiddenName : 'codigoElemento',
		typeAhead : true,
		mode : 'local',
		allowBlank : true,
		//blankText : getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
		triggerAction : 'all',
		fieldLabel : getLabelFromMap('cmbBqdaClienteId', helpMap, 'Cliente'),
		tooltip : getToolTipFromMap('cmbBqdaClienteId', helpMap, 'Cliente del Plan'),
		hasHelpIcon : getHelpIconFromMap('cmbBqdaClienteId', helpMap),
		Ayuda : getHelpTextFromMap('cmbBqdaClienteId', helpMap),
		width : 300,
		emptyText : 'Seleccionar Cliente',
		selectOnFocus : true
	});
    
//////// NUEVO
    
    var codigoCliente = new Ext.form.TextField({
    	id:'codigoClienteId',
        fieldLabel: getLabelFromMap('codigoClienteId',helpMap,'Cliente'),
        tooltip: getToolTipFromMap('codigoClienteId',helpMap, 'Cliente del Plan'),
        hasHelpIcon:getHelpIconFromMap('codigoClienteId',helpMap),								 
		Ayuda: getHelpTextFromMap('codigoClienteId',helpMap),
        name:'codigoCliente',
        width: 200
    });


    var codigoElemento = new Ext.form.TextField({
    	id:'codigoElementoId',
        fieldLabel: getLabelFromMap('codigoElementoId',helpMap,'Cliente'),
        tooltip: getToolTipFromMap('codigoElementoId',helpMap, 'Descripci&oacute;n del Cliente'),
        hasHelpIcon:getHelpIconFromMap('codigoElementoId',helpMap),								 
		Ayuda: getHelpTextFromMap('codigoElementoId',helpMap),
        name:'codigoElemento',
        width: 200
    });

//////// NUEVO

	var storeRiesgos = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : _ACTION_OBTENER_SITUACION_X_PLAN
		}),
		reader : new Ext.data.JsonReader({
			root : 'comboSituacionPlan',
			id : 'codigo'
		},[
			{name: 'codSituacion', type: 'string',mapping:'codigo'},
			{name: 'descripcionSituacion', type: 'string',mapping:'descripcion'}
        ])
	});
	
	var comboRiesgo = new Ext.form.ComboBox({
		id : 'cmbBqdaRiesgoId',
		tpl : '<tpl for="."><div ext:qtip="{codSituacion}. {descripcionSituacion}" class="x-combo-list-item">{descripcionSituacion}</div></tpl>',
		store : storeRiesgos,
		displayField : 'descripcionSituacion',
		valueField : 'codSituacion',
		forceSelection : true,
		hiddenName : 'codigoTipoSituacion',
		typeAhead : true,
		mode : 'local',
		allowBlank : true,
		//blankText : getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
		triggerAction : 'all',
		fieldLabel : getLabelFromMap('cmbBqdaRiesgoId', helpMap, 'Riesgo'),
		tooltip : getToolTipFromMap('cmbBqdaRiesgoId', helpMap, 'Riesgo'),
		hasHelpIcon : getHelpIconFromMap('cmbBqdaRiesgoId', helpMap),
		Ayuda : getHelpTextFromMap('cmbBqdaRiesgoId', helpMap),
		width : 300,
		lazyRender : true,
		emptyText : 'Seleccionar Riesgo...',
		selectOnFocus : true,
		onSelect : function(record) {
			this.setValue(record.get('codSituacion'));
			this.collapse();
			Ext.getCmp('cmbBqdaCoberturaId').clearValue();
			storeCoberturas.removeAll();

			storeCoberturas.reload({
				params : {
					cdRamo : incisosForm.findById('cmbBqdaProductoId')
							.getValue(),
					cdTipSit : record.get("codSituacion"),
					cdPlan : incisosForm.findById('cmbBqdaPlanId')
							.getValue()
				}
			});
		}
	});
    
    
//////// NUEVO
    
    var codigoTipoSituacion = new Ext.form.TextField({
    	id:'codigoTipoSituacionId',
        fieldLabel: getLabelFromMap('codigoTipoSituacionId',helpMap,'Riesgo'),
        tooltip: getToolTipFromMap('codigoTipoSituacionId',helpMap, 'Riesgo'),
        hasHelpIcon:getHelpIconFromMap('codigoTipoSituacionId',helpMap),								 
		Ayuda: getHelpTextFromMap('codigoTipoSituacionId',helpMap),
        name:'codigoTipoSituacion',
        width: 200
    });

//////// NUEVO
    
	var storeCoberturas = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: _ACTION_OBTENER_COBERTURAS_X_PLAN
		}),
		reader: new Ext.data.JsonReader({
			root: 'comboCoberturaPlan',
			id: 'codigo'
		},[
			{name: 'codGarantia', type: 'string',mapping:'codigo'},
			{name: 'descripcionGarantia', type: 'string',mapping:'descripcion'}
		])
	});

	var comboCobertura = new Ext.form.ComboBox({
		id : 'cmbBqdaCoberturaId',
		tpl : '<tpl for="."><div ext:qtip="{codGarantia}. {descripcionGarantia}" class="x-combo-list-item">{descripcionGarantia}</div></tpl>',
		store : storeCoberturas,
		forceSelection : true,
		displayField : 'descripcionGarantia',
		valueField : 'codGarantia',
		hiddenName : 'codigoCobertura',
		typeAhead : true,
		mode : 'local',
		allowBlank: true,
		//blankText : getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
		triggerAction : 'all',
		fieldLabel : getLabelFromMap('cmbBqdaCoberturaId', helpMap, 'Cobertura'),
		tooltip : getToolTipFromMap('cmbBqdaCoberturaId', helpMap, 'Cobertura del Plan'),
		hasHelpIcon : getHelpIconFromMap('cmbBqdaCoberturaId', helpMap),
		Ayuda : getHelpTextFromMap('cmbBqdaCoberturaId', helpMap),
		width : 300,
		emptyText : 'Seleccionar Tipo de Cobertura...',
		selectOnFocus : true
	});
	
// ////// NUEVO
    
    var garantia = new Ext.form.TextField({
    	id:'garantiaId',
        fieldLabel: getLabelFromMap('garantiaId',helpMap,'Cobertura'),
        tooltip: getToolTipFromMap('garantiaId',helpMap, 'Cobertura'),
        hasHelpIcon:getHelpIconFromMap('garantiaId',helpMap),								 
		Ayuda: getHelpTextFromMap('garantiaId',helpMap),
        name:'garantia',
        width: 200
    });

// ////// NUEVO

	var storeAseguradoras = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: _ACTION_OBTENER_ASEGURADORAS
		}),
		reader: new Ext.data.JsonReader({
			root: 'comboAseguradoraPorProducto',
			id: 'codigo'
		},[
			{name: 'codAseguradora', type: 'string',mapping:'codigo'},
			{name: 'descripcionAseguradora', type: 'string',mapping:'descripcion'}
		])
	});
    
	var comboAseguradora = new Ext.form.ComboBox({
		id : 'cmbBqdaAseguradoraId',
		tpl : '<tpl for="."><div ext:qtip="{codAseguradora}. {descripcionAseguradora}" class="x-combo-list-item">{descripcionAseguradora}</div></tpl>',
		store : storeAseguradoras,
		forceSelection : true,
		displayField : 'descripcionAseguradora',
		valueField : 'codAseguradora',
		hiddenName : 'codigoAseguradora',
		typeAhead : true,
		mode : 'local',
		allowBlank : true,
		//blankText : getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
		triggerAction : 'all',
		fieldLabel : getLabelFromMap('cmbBqdaAseguradoraId', helpMap, 'Aseguradora'),
		tooltip : getToolTipFromMap('cmbBqdaAseguradoraId', helpMap, 'Aseguradora del Plan'),
		hasHelpIcon : getHelpIconFromMap('cmbBqdaAseguradoraId', helpMap),
		Ayuda : getHelpTextFromMap('cmbBqdaAseguradoraId', helpMap),
		width : 300,
		emptyText : 'Seleccionar Aseguradora...',
		selectOnFocus : true
	});
	
// ////// NUEVO
    
    var aseguradora = new Ext.form.TextField({
    	id:'aseguradoraId',
        fieldLabel: getLabelFromMap('aseguradoraId',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('aseguradoraId',helpMap, 'Descripci&oacute;n de la Aseguradora'),
        hasHelpIcon:getHelpIconFromMap('aseguradoraId',helpMap),								 
		Ayuda: getHelpTextFromMap('aseguradoraId',helpMap),
        name:'aseguradora',
        width: 200
    });
    
	///////// FORMPANEL

    var incisosForm = new Ext.form.FormPanel({
    	id: 'incisosForm',
        el:'formBusqueda',
		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosForm',helpMap,' Productos por Cliente')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        url:_ACTION_BUSCAR_PLANCLIENTE,
        width: 600,
        height:275,
        items: [{
        		layout:'form',
        		border: false,
				items:[{
        		bodyStyle:'background: white',
        		title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
		        labelWidth: 100,
                layout: 'form',
				frame:true,
		       	baseCls: '',
		       	layout: 'column',
		       	buttonAlign: "center",
        		        items: [{
        		        	layout:'form',
		 				    border:false,
		 				    columnWidth: 1,
        		    		items:[{
						    	columnWidth:.9,
                				layout: 'form',
		                		border: false,
        		        		items:[
        		        			/*
                                        descripcionProducto,
                                        descripcionPlan,
                                        codigoElemento,
                                        codigoTipoSituacion,
                                        garantia,
                                        aseguradora
									*/
        		        				comboProducto,
        		        				comboPlan,
        		        				comboCliente,
        		        				comboRiesgo,
        		        				comboCobertura,
        		        				comboAseguradora
		       						]
								},{
								columnWidth:.4,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
        							text:getLabelFromMap('btnAddDetPlanCId',helpMap,'Buscar'),
        							tooltip:getLabelFromMap('btnAddDetPlanCId',helpMap, 'Busca Plan del Cliente' ),
        							handler: function() {
				               			if (incisosForm.form.isValid()) {
                                               if (grid2!=null) {
                                                reloadGrid();
                                               } else {
                                                createGrid();
                                               }
                						} else{
											Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
										}
									}
        							},{
        							text:getLabelFromMap('btnCancelDetPlanCId',helpMap,'Cancelar'),
        							tooltip:getLabelFromMap('btnCancelDetPlanCId',helpMap, 'Cancela Plan'),  
        							handler: function() {
        								incisosForm.form.reset();
        							}
        						}]
        					}]
        				}]
				});

		incisosForm.render();


        function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
    	}
    var store;    

 function borrar (record) {
   			if(record.get('cdPlan') != "")
			{
				Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
				{
           	       if (btn == "yes")
           	        {
           	        	var _params = {
				    			//codigoProducto: CODIGO_PRODUCTO,
				    			//codigoPlan: CODIGO_PLAN,
				    			codigoProducto: record.get('cdRamo'),
				    			codigoPlan: record.get('cdPlan'),
				    			codigoCliente: record.get('cdPerson'),
				    			codigoSituacion: record.get('cdTipSit'),
				    			codigoGarantia: record.get('cdGarant'),
				    			codigoAseguradora: record.get('cdUniEco'),
				    			codigoObligatorio: record.get('swOblig'),
				    			codigoElemento: record.get('cdElemento')
           	        	};
           	        	execConnection(_ACTION_BORRAR_PLANCLIENTE, _params, cbkConnection);
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
           
           
if (grid2!=null) {
          reloadGrid();
   } else {
         createGrid();
}
   
});

function reloadGrid(){
	var _params = {
    		codigoElemento: Ext.getCmp('incisosForm').form.findField(/*'codigoElemento' -- ANTES*/'codigoElemento').getValue(),
    		codigoProducto:  Ext.getCmp('incisosForm').form.findField(/*'descripcionProducto' -- ANTES*/'codigoProducto').getValue(),
    		codigoPlan:  Ext.getCmp('incisosForm').form.findField(/*'descripcionPlan' -- ANTES*/'codigoPlan').getValue(),
    		codigoTipoSituacion: Ext.getCmp('incisosForm').form.findField(/*'codigoTipoSituacion' -- ANTES*/'codigoTipoSituacion').getValue(),
    		codigoCobertura: Ext.getCmp('incisosForm').form.findField(/*'garantia' -- ANTES*/'codigoCobertura').getValue(),
    		codigoAseguradora: Ext.getCmp('incisosForm').form.findField(/*'aseguradora' -- ANTES*/'codigoAseguradora').getValue()
	};
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
}
function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}	
}
Ext.getUrlParam = function (param) {
	var params = Ext.urlDecode(location.search.substring(1));
	return param ? params[param]:params;
}
Ext.form.Label = Ext.extend(Ext.BoxComponent, {
    onRender : function(ct, position){
        if(!this.el){
            this.el = document.createElement('label');
            this.el.innerHTML = this.text ? Ext.util.Format.htmlEncode(this.text) : (this.html || '');
            if(this.forId){
                this.el.setAttribute('htmlFor', this.forId);
            }
            if (!this.el.id) {
            	this.el.id = this.getId();
            }
        }
        Ext.form.Label.superclass.onRender.call(this, ct, position);
    }
});
Ext.grid.CheckColumn = function(config){
    Ext.apply(this, config);
    if(!this.id){
        this.id = Ext.id();
    }
    this.renderer = this.renderer.createDelegate(this);
};

Ext.grid.CheckColumn.prototype ={
    init : function(grid){
        this.grid = grid;
        this.grid.on('render', function(){
            var view = this.grid.getView();
            view.mainBody.on('mousedown', this.onMouseDown, this);
        }, this);
    },

    onMouseDown : function(e, t){
        if(t.className && t.className.indexOf('x-grid3-cc-'+this.id) != -1){
            e.stopEvent();
            var index = this.grid.getView().findRowIndex(t);
            var record = this.grid.store.getAt(index);
            record.set(this.dataIndex, !record.data[this.dataIndex]);
        }
    },

    renderer : function(v, p, record){
        p.css += ' x-grid3-check-col-td';
        return '<div class="x-grid3-check-col'+(v?'-on':'')+' x-grid3-cc-'+this.id+'">&#160;</div>';
    }
};
