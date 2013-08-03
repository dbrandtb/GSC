Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

	var idRegresar = new Ext.form.Hidden({
        id: 'idRegresar',
        name: 'idRegresar',
        value:''
    });

	//******************Store para combo de Producto******************
    var storeProducto = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : _COMBO_PRODUCTO, // 'flujorenovacion/getComboProducto.action'
			method : 'POST'
		}),
		reader : new Ext.data.JsonReader({
			root : 'listaProducto',
			id : 'storeProducto'
		},
		[{
			name : 'label', type : 'string', mapping : 'label'
		},{
			name : 'value', type : 'string', mapping : 'value'
		}]),
		remoteSort : true
	});
    
	// ******************Store para combo de Cliente******************
    var storeCliente = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : _COMBO_CLIENTE, // 'flujorenovacion/getComboCliente.action'
			method : 'POST'
		}),
		reader : new Ext.data.JsonReader({
			root : 'listaCliente',
			id : 'storeCliente'
		},
		[{
			name : 'cdCliente', type : 'string', mapping : 'codigoElemento'
		},{
			name : 'dsCliente', type : 'string', mapping : 'nombre'
		}]),
		remoteSort : true
	});

	// ******************Store para combo de Aseguradora******************
    var storeAseguradora = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : _COMBO_ASEGURADORA, // 'flujorenovacion/getComboAsegradora.action'
			method : 'POST'
		}),
		reader : new Ext.data.JsonReader({
			root : 'listaAseguradora',
			id : 'storeAseguradora'
		},
		[{
			name : 'label', type : 'string', mapping : 'label'
		},{
			name : 'value', type : 'string', mapping : 'value'
		}])
	});

    var lasFilas=new Ext.data.Record.create([
        {name:'asegurado',   type:'string', mapping:'asegurado'},
        {name:'cdCia',       type:'string', mapping:'cdCia'},
        {name:'aseguradora', type:'string', mapping:'aseguradora'},
        {name:'cdRamo',      type:'string', mapping:'cdRamo'},
        {name:'producto',    type:'string', mapping:'producto'},
        {name:'poliza',      type:'string', mapping:'poliza'},
        {name:'inciso', 	 type:'string', mapping:'inciso'},
        {name:'cdCliente', 	 type:'string', mapping:'cdCliente'},
        {name:'cliente', 	 type:'string', mapping:'cliente'},
        {name:'fechaRenova', type:'string', mapping:'fechaRenova'/*, dateFormat:'Y-m-d H:i:s.u'*/},
        {name:'estado',      type:'string', mapping:'estado'},
        {name:'nmPoliza',    type:'string', mapping:'nmPoliza'},
        {name:'nmSituac',    type:'string', mapping:'nmSituac'},
        {name:'renovar',     type:'string', mapping:'renovar'},
        {name:'nmanno',      type:'string', mapping:'nmanno'},
        {name:'nmmes',       type:'string', mapping:'nmmes'}
    ]);
    
    var jsonGrilla= new Ext.data.JsonReader({    
            root:'listaPolizas',
            totalProperty: 'totalCount'
        },
        lasFilas
    );
    
    var checkRenovar = new Ext.grid.CheckRenovar({
       header: getLabelFromMap('cmRenovar',helpMap,'Renovar'),
       tooltip: getToolTipFromMap('cmRenovar',helpMap,'Renovar'),
       dataIndex: 'renovar',
       value:false,
       width: 55
    });

    var selColumn = new Ext.grid.CheckColumn({
       header: getLabelFromMap('cmSeleccionar',helpMap,'Sel'),
       tooltip: getToolTipFromMap('cmSeleccionar',helpMap,'Seleccionar'),
       dataIndex: 'seleccionar',
       value:false,
       width: 55
    });

	var cm = new Ext.grid.ColumnModel([
	    new Ext.grid.RowNumberer(),
	    selColumn,
	    {header:"cdCia",        dataIndex:'cdCia',       hidden:true},
	    {header:"cdRamo",		dataIndex:'cdRamo',      hidden:true},	    
	    {header:"cdCliente",    dataIndex:'cdCliente',   hidden:true},
   	    {header:"estado",       dataIndex:'estado',      hidden:true},
	    {header:"nmPoliza",     dataIndex:'nmPoliza',    hidden:true},
	    {header:"nmSituac",     dataIndex:'nmSituac',    hidden:true},
	    {header:"nmanno",       dataIndex:'nmanno',      hidden:true},
	    {header:"nmmes",        dataIndex:'nmmes',       hidden:true},
    	{
    		header: getLabelFromMap('cmAsegurado',helpMap,'Asegurado'),
        	tooltip: getToolTipFromMap('cmAsegurado',helpMap,'Asegurado'),
    		dataIndex:'asegurado',   
    		width: 250, 
    		sortable:true
    	},{
    		header: getLabelFromMap('cmAseguradora',helpMap,'Aseguradora'),
        	tooltip: getToolTipFromMap('cmAseguradora',helpMap,'Aseguradora'),
	    	dataIndex:'aseguradora', 
	    	width: 170, 
	    	sortable:true
	    },{
    		header: getLabelFromMap('cmProducto',helpMap,'Producto'),
        	tooltip: getToolTipFromMap('cmProducto',helpMap,'Producto'),
  	  		dataIndex:'producto',    
  	  		width: 170, 
  	  		sortable:true
  	  	},{
    		header: getLabelFromMap('cmPoliza',helpMap,'Poliza'),
        	tooltip: getToolTipFromMap('cmPoliza',helpMap,'Poliza'),
	    	dataIndex:'poliza',      
	    	width: 120, 
	    	sortable:true
	    },{
    		header: getLabelFromMap('cmInciso',helpMap,'Inciso'),
        	tooltip: getToolTipFromMap('cmInciso',helpMap,'Inciso'),
	    	dataIndex:'inciso',      
	    	width: 120, 
	    	sortable:true
	    },
	    checkRenovar,
	    {
    		header: getLabelFromMap('cmFechaRenova',helpMap,'Fecha de Renovacion'),
        	tooltip: getToolTipFromMap('cmFechaRenova',helpMap,'Fecha de Renovacion'),
	    	dataIndex:'fechaRenova', 
	    	width: 120, 
	    	sortable:true
	}]);
	cm.defaultSortable = true;
	
	var storeGridResultado;
	
	var url = _OBTENER_POLIZAS_A_RENOVAR; //'flujorenovacion/obtienePolizas.action';
	storeGridResultado = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: url,
			method: 'POST'
		}),
		reader: jsonGrilla,
		remoteSort: false
	});
	storeGridResultado.setDefaultSort('asegurado', 'asc');
    
	//******************Form Filtros******************
	var asegurado = new Ext.form.TextField({
	    fieldLabel: getLabelFromMap('aseguradoId',helpMap,'Asegurado'),
        tooltip: getToolTipFromMap('aseguradoId',helpMap,'Asegurado'),
        hasHelpIcon:getHelpIconFromMap('aseguradoId',helpMap),								 
        Ayuda: getHelpTextFromMap('aseguradoId',helpMap),
	    name:'parametrosBusqueda.Asegurado',
	    value:'',
	    id: 'aseguradoId',
	    width: 200
	});
    var producto = new Ext.form.ComboBox({ 
    	id:'productoId',
        tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeProducto,
        width: 200,
        mode: 'local',
        name: 'parametrosBusqueda.cdRamo',
        hiddenName: 'parametrosBusqueda.cdRamo',
        typeAhead: true,
        labelSeparator: ':',          
        triggerAction: 'all',
        valueField: 'value',
        displayField: 'label',
   	    fieldLabel: getLabelFromMap('productoId',helpMap,'Producto'),
        tooltip: getToolTipFromMap('productoId',helpMap,'Producto'),
        hasHelpIcon:getHelpIconFromMap('productoId',helpMap),								 
        Ayuda: getHelpTextFromMap('productoId',helpMap),
        emptyText: 'Seleccione...',
        selectOnFocus:true
    });
    var cliente = new Ext.form.ComboBox({ 
    	id:'clienteId',
        tpl: '<tpl for="."><div ext:qtip="{dsCliente}.{cdCliente}" class="x-combo-list-item">{dsCliente}</div></tpl>',
        store: storeCliente,
        width: 200,
        mode: 'local',
        name: 'parametrosBusqueda.cdElemento',
        hiddenName: 'parametrosBusqueda.cdElemento',
        typeAhead: true,
        labelSeparator: ':',          
        triggerAction: 'all',
        valueField: 'cdCliente',
        displayField: 'dsCliente',
		fieldLabel: getLabelFromMap('clienteId',helpMap,'Nivel'),
        tooltip: getToolTipFromMap('clienteId',helpMap,'Nivel'),
        hasHelpIcon:getHelpIconFromMap('clienteId',helpMap),								 
        Ayuda: getHelpTextFromMap('clienteId',helpMap),        
        emptyText: 'Seleccione...',
        selectOnFocus:true
    });
    var aseguradora = new Ext.form.ComboBox({ 
    	id:'aseguradoraId',
        tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeAseguradora,
        width: 200,
        mode: 'local',
        name: 'parametrosBusqueda.cdUnieco',
        hiddenName: 'parametrosBusqueda.cdUnieco',
        typeAhead: true,
        labelSeparator: ':',          
        triggerAction: 'all',
        valueField: 'value',
        displayField: 'label',
		fieldLabel: getLabelFromMap('aseguradoraId',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('aseguradoraId',helpMap,'Aseguradora'),
        hasHelpIcon:getHelpIconFromMap('aseguradoraId',helpMap),								 
        Ayuda: getHelpTextFromMap('aseguradoraId',helpMap),        
        emptyText: 'Seleccione...',
        selectOnFocus:true
    });
	var poliza = new Ext.form.TextField({
		fieldLabel: getLabelFromMap('polizaId',helpMap,'Poliza'),
        tooltip: getToolTipFromMap('polizaId',helpMap,'Poliza'),
        hasHelpIcon:getHelpIconFromMap('polizaId',helpMap),								 
        Ayuda: getHelpTextFromMap('polizaId',helpMap),        
	    name:'parametrosBusqueda.nmPoliEx',
	    value:'',
	    id: 'polizaId',
	    width: 200
	});
	
    var filtroForm = new Ext.form.FormPanel({    
        title: '<span style="color:#98012e;font-size:12px;">B&uacute;squeda</span>',
        iconCls:'logo',
        
        /*NOTA: 
         * Buscar la forma de cambiar la notacion ../ de la url
         * ya que se tuvo que hacer de esta forma porque al regresar del boton detalle y si se volvia a dar click en Buscar
         * no direcciona a /flujorenovacion si no a /procesoemision
        */
        
        url: '../flujorenovacion/obtienePolizas.action', 
        buttonAlign: "center",
        labelAlign: 'right',
        collapsible: true,
        frame:true,                         
        width: 668,
        autoHeight: true,
        items: [{
                layout:'form',
                border: false,
                items:[{
                	labelWidth: 100,                	
                	layout: 'form',
                	frame:true,
                	baseCls: '',
                	buttonAlign: "center",
                    items:[{
                        layout:'column',
                        border:false,
                        items:[{
                            layout: 'form',                     
                            border: false,                      
                            items:[
                            		idRegresar,
                            		asegurado,
                            		producto,
                            		cliente
                            ]
                        },{
                            layout: 'form',                     
                            border: false,                      
                            items:[ 
                            		aseguradora,
                            		poliza
                            ]
                        }]
                    }],
                    buttons:[{                                  
                            text:getLabelFromMap('btnCnsPlzRnvrBuscar',helpMap,'Buscar'),
					        tooltip: getToolTipFromMap('btnCnsPlzRnvrBuscar',helpMap,'Buscar'),
                            handler: function() {                                            
                                if (filtroForm.form.isValid()) {
                                        filtroForm.form.submit({ 
                                        	waitTitle:'Espere',
                                            waitMsg:'Procesando...',
                                            failure: function(form, action) {
                                            	Ext.MessageBox.alert(getLabelFromMap('400000',helpMap,'Aviso'),getLabelFromMap('400105', helpMap,'No se encontraron datos'));
                                                storeGridResultado.load();
                                            },
                                            success: function(form, action) {
                                            	storeGridResultado.load();
                                            }
                                        });                   
                                } else{
                                    Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
                                }  
                            }                                                       
                            },{
                            text:getLabelFromMap('btnCnsPlzRnvrCancelar',helpMap,'Cancelar'),
					        tooltip: getToolTipFromMap('btnCnsPlzRnvrCancelar',helpMap,'Cancelar'),
                            handler: function() {
                                filtroForm.form.reset();
                            }
                    }]
                }]
		}]  
	});


	var _cdUnieco;
	var _cdRamo;
	var _cdCliente;
	var _estado;
	var _nmPoliza;
	var _nmSituac;
	var _poliza;
	var _producto;
	var _aseguradora;
	var _renovar;
	var params="";
	var selectedId="";

	var gridResultados = new Ext.grid.GridPanel({
		store : storeGridResultado,
		border : true,
		buttonAlign : 'center',
		bodyStyle : 'background:white',
		cm : cm,
		plugins : [checkRenovar, selColumn],
		clicksToEdit : 1,
		buttons : [{
			id : 'detallesRenovar',
			text : getLabelFromMap('detallesRenovar', helpMap, 'Detalles'),
			tooltip : getToolTipFromMap('detallesRenovar', helpMap, 'Detalles')
		},{
			id : 'guardarRenovar',
			text : getLabelFromMap('guardarRenovar', helpMap, 'Guardar'),
			tooltip : getToolTipFromMap('guardarRenovar', helpMap, 'Guardar')
		},{
			id : 'selTodasARenovar',
			text : getLabelFromMap('selTodasARenovar', helpMap, 'Seleccionar Todas'),
			tooltip : getToolTipFromMap('selTodasARenovar', helpMap, 'Seleccionar Todas')
		},{
			id : 'borradorRenovar',
			text : getLabelFromMap('borradorRenovar', helpMap, 'Generar borrador'),
			tooltip : getToolTipFromMap('borradorRenovar', helpMap, 'Generar borrador')
		},{
			id : 'renovarRenovar',
			text : getLabelFromMap('renovarRenovar', helpMap, 'Renovar'),
			tooltip : getToolTipFromMap('renovarRenovar', helpMap, 'Renovar')
		},{
			id : 'exportarRenovar',
			text : getLabelFromMap('exportarRenovar', helpMap, 'Exportar'),
			tooltip : getToolTipFromMap('exportarRenovar', helpMap, 'Exportar'),
			handler : function() {
				var url = _NUEVO_ACTION_EXPORT + 
						'?Asegurado=' + Ext.getCmp('aseguradoId').getValue()
						+ '&cdRamo=' + Ext.getCmp('productoId').getValue()
						+ '&cdElemento=' + Ext.getCmp('clienteId').getValue()
						+ '&cdUnieco=' + Ext.getCmp('aseguradoraId').getValue()
						+ '&nmPoliEx=' + Ext.getCmp('polizaId').getValue();
				showExportDialog(url);
			}
		}],
		width : 656,
		frame : true,
		height : 340,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true,
			listeners : {
				rowselect : function(sm, row, rec) { 
					selectedId = storeGridResultado.data.items[row].id;
					_cdUnieco = rec.get('cdCia');
					_cdRamo = rec.get('cdRamo');
					_cdCliente = rec.get('cdCliente');
					_estado = rec.get('estado');
					_nmPoliza = rec.get('nmPoliza');
					_nmSituac = rec.get('nmSituac');
					_poliza = rec.get('poliza');
					_producto = rec.get('producto');
					_aseguradora = rec.get('aseguradora');
					_renovar = rec.get('renovar');
					idRegresar.setValue( rec.get('nmPoliza') + "|" + 
										 rec.get('cdCia') + "|" + 
										 rec.get('cdRamo') + "|" +
										 rec.get('nmSituac') );
				} 
			}
		}),
		bbar : new Ext.PagingToolbar({
			pageSize : 20,
			store : storeGridResultado,
			displayInfo : true,
			displayMsg : getLabelFromMap('400009', helpMap, 'Mostrando registros {0} - {1} of {2}'),
			emptyMsg : getLabelFromMap('400012', helpMap, 'No hay registros para visualizar'),
			beforePageText : 'P&aacute;gina',
			afterPageText : 'de {0}'
		})
	});
		
	Ext.getCmp('detallesRenovar').on('click',function(){
		if(selectedId!=""){
			window.location.href=_CONTEXT+'/procesoemision/detallePoliza.action?cdUnieco='+CDUNIECO+'&cdRamo='+_cdRamo+'&volver2=true'+'&estado='+_estado+'&nmPoliza='+_nmPoliza+'&poliza='+_poliza+'&producto='+_producto+'&aseguradora='+_aseguradora+"&idRegresar="+idRegresar.getValue();
		}else{
			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		}
	});

	Ext.getCmp('borradorRenovar').on('click', function() {
		var w = 0;
		params = "";
		var polizaRenovar;
		for (var i = 0; i < storeGridResultado.getTotalCount(); i++) {

			polizaRenovar = storeGridResultado.getAt(i);
			if (polizaRenovar != undefined && polizaRenovar.get('seleccionar')) {

				params += "parametrosRenovar[" + w + "].cdCia=" + polizaRenovar.get('cdCia')
						+ "&&parametrosRenovar[" + w + "].asegurado=" + polizaRenovar.get('asegurado')
						+ "&&parametrosRenovar[" + w + "].cdRamo=" + polizaRenovar.get('cdRamo')
						+ "&&parametrosRenovar[" + w + "].cdCliente=" + polizaRenovar.get('cdCliente')
						+ "&&parametrosRenovar[" + w + "].nmPoliza=" + polizaRenovar.get('nmPoliza')
						+ "&&parametrosRenovar[" + w + "].seleccionar=" + polizaRenovar.get('seleccionar')
						+ "&&parametrosRenovar[" + w + "].nmanno=" + polizaRenovar.get('nmanno')
						+ "&&parametrosRenovar[" + w + "].nmmes=" + polizaRenovar.get('nmmes')
						+ "&&parametrosRenovar[" + w + "].nmSituac=" + polizaRenovar.get('nmSituac')
						+ "&&parametrosRenovar[" + w + "].estado=" + polizaRenovar.get('estado')
						+ "&&parametrosRenovar[" + w + "].renovar=" + polizaRenovar.get('renovar');
				w++;

			}

		}

		if (w > 0) {

			var connect = new Ext.data.Connection();
			connect.request({
				url : 'flujorenovacion/borradorPolizas.action',
				method : 'POST',
				successProperty : '@success',
				params : params,
				callback : function(options, success, response) {

					if (Ext.util.JSON.decode(response.responseText).success == false) {
						Ext.MessageBox.alert(getLabelFromMap('400010', helpMap, 'Error'), getLabelFromMap('400097', helpMap, 'Ocurrio un error en la transacci&oacute;n'));
					} else {
						storeGridResultado.load();
						Ext.MessageBox.alert(getLabelFromMap('400103', helpMap, 'Renovar'), getLabelFromMap('400104', helpMap, 'Se han generado el borrador de los datos'));
					}

				}

			});

		} else {
			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso'), getLabelFromMap('400011', helpMap, 'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		}

	});
	
	
	Ext.getCmp('renovarRenovar').on('click', function() {

		var i = 0;
		params = "";
		var polizaRenovar;
		for (var j = 0; j < storeGridResultado.getTotalCount(); j++) {

			polizaRenovar = storeGridResultado.getAt(j);
			if (polizaRenovar != undefined && polizaRenovar.get('seleccionar')) {

				if (polizaRenovar.get('renovar')) {
					params += "parametrosRenovar[" + i + "].cdRamo=" + polizaRenovar.get('cdRamo')
							+ "&&parametrosRenovar[" + i + "].cdCliente=" + polizaRenovar.get('cdCliente')
							+ "&&parametrosRenovar[" + i + "].nmPoliza=" + polizaRenovar.get('nmPoliza')
							+ "&&parametrosRenovar[" + i + "].nmSituac=" + polizaRenovar.get('nmSituac')
							+ "&&parametrosRenovar[" + i + "].estado=" + polizaRenovar.get('estado');
					i++;
				}

			}
		}

		if (i > 0) {

			var connect = new Ext.data.Connection();
			connect.request({
				url : 'flujorenovacion/renovarPolizas.action',
				method : 'POST',
				successProperty : '@success',
				params : params,
				callback : function(options, success, response) {

					var mensaje = Ext.util.JSON.decode(response.responseText).msgId;
					var titulo = Ext.util.JSON.decode(response.responseText).msgTitle;

					if (Ext.util.JSON.decode(response.responseText).success == 'true') {
						storeGridResultado.load();
						Ext.Msg.alert(getLabelFromMap('400103', helpMap, 'Renovar'), Ext.util.JSON.decode(response.responseText).msgText);
					} else {
						Ext.Msg.alert(getLabelFromMap('400010', helpMap, 'Error'), Ext.util.JSON.decode(response.responseText).msgText);
					}

				}
			});

		} else {
			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso'), getLabelFromMap('400011', helpMap, 'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		}

	});

	Ext.getCmp('guardarRenovar').on('click', function() {

		var i = 0;
		params = "";
		var polizaGuardar;

		for (var j = 0; j < storeGridResultado.getTotalCount(); j++) {

			polizaGuardar = storeGridResultado.getAt(j);
			if (polizaGuardar != undefined && polizaGuardar.get('seleccionar')) {

				params += "parametrosRenovar[" + i + "].cdCia=" + polizaGuardar.get('cdCia')
						+ "&&parametrosRenovar[" + i + "].cdRamo=" + polizaGuardar.get('cdRamo')
						+ "&&parametrosRenovar[" + i + "].nmPoliza=" + polizaGuardar.get('nmPoliza')
						+ "&&parametrosRenovar[" + i + "].nmSituac=" + polizaGuardar.get('nmSituac')
						+ "&&parametrosRenovar[" + i + "].renovar=" + polizaGuardar.get('renovar');
				i++;

			}

		}

		if (i > 0) {
			var connect = new Ext.data.Connection();
			connect.request({
				waitMsg : 'Procesando...',
				url : 'flujorenovacion/guardaRenovarPolizas.action',
				method : 'POST',
				successProperty : '@success',
				params : params,
				callback : function(options, success, response) {

					if (Ext.util.JSON.decode(response.responseText).success == "true") {
						Ext.Msg.alert(getLabelFromMap('400000', helpMap, 'Aviso'), Ext.util.JSON.decode(response.responseText).msgText);
					} else {
						Ext.Msg.alert(getLabelFromMap('400010', helpMap, 'Error'), Ext.util.JSON.decode(response.responseText).msgText);
					}

				}
			});
		} else {
			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso'), getLabelFromMap('400011', helpMap, 'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		}
	});
	
	Ext.getCmp('selTodasARenovar').on('click', function() {
		for (var i = 0; i < storeGridResultado.getCount(); i++) {
			var record = gridResultados.store.getAt(i);
			var sel = record.get('seleccionar');
			record.set('seleccionar', true);
		}
	});
	
	// ******************PANEL GRID RESULTADOS******************
    var resultadosPanelGrid = new Ext.form.FormPanel({
		el : 'listadoRenovacion',
		title : '<span style="color:#98012e;font-size:12px;">Listado</span>',
		iconCls : 'logo',
		buttonAlign : "center",
		labelAlign : 'right',
		collapsible : true,
		frame : true,
		width : 668,
		autoHeight : true,
		items : [{
			layout : 'form',
			border : false,
			items : [{
				labelWidth : 100,
				layout : 'form',
				frame : true,
				baseCls : '',
				buttonAlign : "center",
				items : [gridResultados]
			}]
		}]
	});

	// ******************PANEL GRID MAIN******************
	var mainPanelGrid = new Ext.Panel({
		border : false,
		width : 680,
		autoHeight : true,
		items : [{
			title : '<span style="color:black;font-size:14px;">P&oacute;lizas a Generar Prerenovaci&oacute;n</span>',
			labelAlign : 'center',
			layout : 'form',
			frame : true,
			buttonAlign : 'center',
			items : [filtroForm, resultadosPanelGrid]
		}]
	});
	mainPanelGrid.render('mainRenovacion');


///// DATOS - BOTON REGRESAR

	if ( _SESSION_PARAMETROS_REGRESAR ) {
		
		Ext.getCmp('aseguradoId').setValue( _SESSION_PARAMETROS_REGRESAR.asegurado );
		Ext.getCmp('polizaId').setValue( _SESSION_PARAMETROS_REGRESAR.poliza );
		storeAseguradora.load({
			callback: function(r,options,success) {
				Ext.getCmp('aseguradoraId').setValue( _SESSION_PARAMETROS_REGRESAR.aseguradora );
			}
		});
		storeCliente.load({
			callback: function(r,options,success) {
				Ext.getCmp('clienteId').setValue( _SESSION_PARAMETROS_REGRESAR.nivel );
			}
		});
		storeProducto.load({
			callback: function(r,options,success) {
				Ext.getCmp('productoId').setValue( _SESSION_PARAMETROS_REGRESAR.producto );
			}
		});

		storeGridResultado.removeAll();
		storeGridResultado.baseParams['idRegresar'] = _SESSION_PARAMETROS_REGRESAR.idRegresar;
		storeGridResultado.baseParams['parametrosBusqueda.Asegurado'] = _SESSION_PARAMETROS_REGRESAR.asegurado;
		storeGridResultado.baseParams['parametrosBusqueda.cdUnieco'] = _SESSION_PARAMETROS_REGRESAR.aseguradora;
		storeGridResultado.baseParams['parametrosBusqueda.cdRamo'] = _SESSION_PARAMETROS_REGRESAR.producto;
		storeGridResultado.baseParams['parametrosBusqueda.cdElemento'] = _SESSION_PARAMETROS_REGRESAR.nivel;
		storeGridResultado.baseParams['parametrosBusqueda.nmPoliEx'] = _SESSION_PARAMETROS_REGRESAR.poliza;
		storeGridResultado.load({
			callback: function(r,options,success) {
				function buscaRecord(stringIdRegresar) {
					var array = stringIdRegresar.split("|");
					var _nmpoliza = array[0];
					var _cdcia = array[1];
					var _cdramo = array[2];
					var _nmsituac = array[3];
					var index = -1;
					var bandera = true;
					do {
						index = storeGridResultado.find('nmPoliza', _nmpoliza, index + 1);
						var record = storeGridResultado.getAt(index);
						if ( record.get('cdCia') == _cdcia && record.get('cdRamo') == _cdramo && record.get('nmSituac') == _nmsituac ) {
							var record0 = storeGridResultado.getAt(0);
							// Swap de Ext.Data.Record
							storeGridResultado.remove(record0);
							storeGridResultado.remove(record);
							storeGridResultado.insert(0, record);
							storeGridResultado.insert(index, record0);
							bandera = false;
						}
					} while (bandera);
					return;
				}
				if ( storeGridResultado.getCount() > 1 )
					buscaRecord(_SESSION_PARAMETROS_REGRESAR.idRegresar);
				gridResultados.getSelectionModel().selectRow(0);
			}
		});


	} else {
		storeAseguradora.load({
    		callback:function(){
    			Ext.getCmp("aseguradoraId").setValue(COD_ASEGURADORA);
	    	}
    	});
    	storeCliente.load({
    		callback:function(){
    			Ext.getCmp("clienteId").setValue(COD_CLIENTE);
	    	}
    	});
    	storeProducto.load({
    		callback:function(){
    			Ext.getCmp("productoId").setValue(COD_PRODUCTO);
	    	}
    	});
	}

/////	

});


//Plugin Externo para controlar el checkbox del grid
	Ext.grid.CheckColumn = function(config) {
		Ext.apply(this, config);
		if (!this.id) {
			this.id = Ext.id();
		}
		this.renderer = this.renderer.createDelegate(this);
	};
	
	Ext.grid.CheckColumn.prototype = {
		init : function(grid) {
			this.grid = grid;
			this.grid.on('render', function() {
						var view = this.grid.getView();
						view.mainBody.on('mousedown', this.onMouseDown, this);
					}, this);
		},
	
		onMouseDown : function(e, t) {
			if (t.className && t.className.indexOf('x-grid3-cc-' + this.id) != -1) {
				e.stopEvent();
				var index = this.grid.getView().findRowIndex(t);
				var record = this.grid.store.getAt(index);
				record.set(this.dataIndex, !record.data[this.dataIndex]);
			}
		},
	
		renderer : function(v, p, record) {
			p.css += ' x-grid3-check-col-td';
			return '<div class="x-grid3-check-col' + (v ? '-on' : '')
					+ ' x-grid3-cc-' + this.id + '">&#160;</div>';
		}
	};
	
//Plugin para combo de renovar
	Ext.grid.CheckRenovar = function(config){
    	Ext.apply(this, config);
    	if(!this.id){
        	this.id = Ext.id();
    	}
    	this.renderer = this.renderer.createDelegate(this);
	};

	Ext.grid.CheckRenovar.prototype ={
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
            var value = record.data[this.dataIndex];

            if(value=='S'){
            	value = 'N';
            }else{
            	value = 'S';
            }
            record.set(this.dataIndex, value);
        }
    },
    renderer : function(v, p, record){
        p.css += ' x-grid3-check-col-td';        
        return '<div class="x-grid3-check-col'+(v=='S'?'-on':'')+' x-grid3-cc-'+this.id+'">&#160;</div>';
    }
	};
	