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
        	proxy: new Ext.data.HttpProxy({
            url: _COMBO_PRODUCTO, //'flujorenovacion/getComboProducto.action'
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
    
	//******************Store para combo de Cliente******************
    var storeCliente = new Ext.data.Store({
        	proxy: new Ext.data.HttpProxy({
            url: _COMBO_CLIENTE, //'flujorenovacion/getComboCliente.action'
            method: 'POST'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaCliente',
            id: 'storeCliente'
        },[
                {name: 'cdCliente', type: 'string', mapping:'codigoElemento'},
                {name: 'dsCliente', type: 'string', mapping:'nombre'}
        ]),
        remoteSort: true
    });
    
	//******************Store para combo de Aseguradora******************
    var storeAseguradora = new Ext.data.Store({
        	proxy: new Ext.data.HttpProxy({
            url: _COMBO_ASEGURADORA, //'flujorenovacion/getComboAsegradora.action'
            method: 'POST'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaAseguradora',
            id: 'storeAseguradora'
        },[
                {name: 'label', type: 'string', mapping:'label'},
                {name: 'value', type: 'string', mapping:'value'}
        ]),
        remoteSort: true
    });
    
//******************Store para el tipo de la Poliza******************
    var storeTipoPoliza = new Ext.data.Store({
        	proxy: new Ext.data.HttpProxy({
            url: _COMBO_TIPO_POLIZA,
            method: 'POST'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaTiposPoliza',
            id: 'storeTiposPoliza'
        },[
                {name: 'label', type: 'string', mapping:'label'},
                {name: 'value', type: 'string', mapping:'value'}
        ]),
        remoteSort: true
    });
    
    
    var lasFilas=new Ext.data.Record.create([
        {name:'asegurado',   		type:'string', mapping:'asegurado'},
        {name:'cdCia',       		type:'string', mapping:'cdCia'},
        {name:'aseguradora', 		type:'string', mapping:'aseguradora'},
        {name:'cdRamo',      		type:'string', mapping:'cdRamo'},
        {name:'producto',    		type:'string', mapping:'producto'},
        {name:'polizaAnterior',   	type:'string', mapping:'polizaAnterior'},
        {name:'polizaRenovacion', 	type:'string', mapping:'polizaRenovacion'},
        {name:'nmPolizaAnterior', 	type:'string', mapping:'nmPolizaAnterior'},
        {name:'inciso', 	 		type:'string', mapping:'inciso'},
        {name:'prima', 	     		type:'string', mapping:'prima'},
        {name:'cdCliente', 	 		type:'string', mapping:'cdCliente'},
        {name:'cliente', 	 		type:'string', mapping:'cliente'},
        {name:'fechaRenova', 		type:'string', mapping:'fechaRenova'/*, dateFormat:'Y-m-d H:i:s.u'*/},
        {name:'estado',      		type:'string', mapping:'estado'},
        {name:'nmPoliza',    		type:'string', mapping:'nmPoliza'},
        {name:'nmSituac',    		type:'string', mapping:'nmSituac'},
        {name:'cdUnieco',    		type:'string', mapping:'cdUnieco'},
        {name:'nmanno',      		type:'string', mapping:'nmanno'},
        {name:'nmmes',       		type:'string', mapping:'nmmes'},
        {name:'swAprobada',  		type:'string', mapping:'swAprobada'}
    ]);
    
    var jsonGrilla= new Ext.data.JsonReader({    
            root:'listaPolizas',
            totalProperty: 'totalCount'
        },
        lasFilas
    );
    
    var selchk = new Ext.grid.CheckColumn({
    	header: getLabelFromMap('cmSelCheckColId',helpMap,'Sel'),
        tooltip: getToolTipFromMap('cmCnsltEncNmPoliza',helpMap,'Columna de Selecci&oacute;n'),
        dataIndex:'seleccionar',
       	width:55
    });
    var aprobadachk = new Ext.grid.CheckAprobada({
       header: getLabelFromMap('cmAprobadaCheckColId',helpMap,'Aprobada'),
       tooltip: getToolTipFromMap('cmAprobadaCheckColId',helpMap,'Columna Aprobada'),
       dataIndex: 'swAprobada',
       width: 55
    });

	var cm = new Ext.grid.ColumnModel([
	    new Ext.grid.RowNumberer(),
	    selchk,
	    {header:"cdCia",			dataIndex:'cdCia',				hidden:true},
	    {header:"cdRamo",			dataIndex:'cdRamo',				hidden:true},	    
	    {header:"cdCliente",		dataIndex:'cdCliente',			hidden:true},
   	    {header:"estado",			dataIndex:'estado',				hidden:true},
	    {header:"nmPoliza",			dataIndex:'nmPoliza',			hidden:true},
	    {header:"nmPolizaAnterior",	dataIndex:'nmPolizaAnterior',	hidden:true},
	    {header:"nmSituac",			dataIndex:'nmSituac',			hidden:true},
	    {header:"cdUnieco",			dataIndex:'cdUnieco',			hidden:true},
	    {header:"nmanno",			dataIndex:'nmanno',				hidden:true},
	    {header:"nmmes",			dataIndex:'nmmes',				hidden:true},
    	{
			header: getLabelFromMap('cmAseguradoId',helpMap,'Asegurado'),
			tooltip: getToolTipFromMap('cmAseguradoId',helpMap,'Columna Asegurado'),
			dataIndex:'asegurado',   width: 250, sortable:true
		},{
			header: getLabelFromMap('cmAseguradoraId',helpMap,'Aseguradora'),
			tooltip: getToolTipFromMap('cmAseguradoraId',helpMap,'Columna Aseguradora'),
			dataIndex:'aseguradora', width: 170, sortable:true
		},{
			header: getLabelFromMap('cmProductoId',helpMap,'Producto'),
			tooltip: getToolTipFromMap('cmProductoId',helpMap,'Columna Producto'),
			dataIndex:'producto',    width: 170, sortable:true
		},{
			header: getLabelFromMap('cmPolizaAnteriorId',helpMap,'P&oacute;liza Anterior'),
			tooltip: getToolTipFromMap('cmPolizaAnteriorId',helpMap,'Columna P&oacute;liza Anterior'),
			dataIndex:'polizaAnterior',		width: 120, sortable:true
		},{
			header: getLabelFromMap('cmPolizaRenovacionId',helpMap,'P&oacute;liza Renovaci&oacute;n'),
			tooltip: getToolTipFromMap('cmPolizaRenovacionId',helpMap,'Columna P&oacute;liza Renovaci&oacute;n'),
			dataIndex:'polizaRenovacion',	width: 120, sortable:true
		},{
			header: getLabelFromMap('cmIncisoId',helpMap,'Inciso'),
			tooltip: getToolTipFromMap('cmIncisoId',helpMap,'Columna Inciso'),
			dataIndex:'inciso',				width: 120, sortable:true
		},{
			header: getLabelFromMap('cmFechaRenovacionId',helpMap,'Fecha Renovaci&oacute;n'),
			tooltip: getToolTipFromMap('cmPolizaRenovacionId',helpMap,'Columna Fecha Renovaci&oacute;n'),
			dataIndex:'fechaRenova',		width: 120, sortable:true
		},{
			header: getLabelFromMap('cmPrimaId',helpMap,'Prima'),
			tooltip: getToolTipFromMap('cmPrimaId',helpMap,'Columna Prima'),
			dataIndex:'prima',				width: 120, sortable:true
		},
	    aprobadachk
	]);
	cm.defaultSortable = true;
	
	var storeGridResultado;
	
	var url = _OBTIENE_POLIZAS_RENOVADAS; //'flujorenovacion/obtienePolizasRenovadas.action';
	storeGridResultado = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: url,
			method: 'POST'
		}),
		reader: jsonGrilla,
		remoteSort: true
	});
	storeGridResultado.data
	storeGridResultado.setDefaultSort('cdCia', 'desc');
    
	//******************Form Filtros******************
	var asegurado = new Ext.form.TextField({
	    id:'txtAseguradoFormId',
	    fieldLabel: getLabelFromMap('txtAseguradoFormId',helpMap,'Asegurado'),
        tooltip:getToolTipFromMap('txtAseguradoFormId',helpMap,'Asegurado'), 
        hasHelpIcon:getHelpIconFromMap('txtAseguradoFormId',helpMap),
        Ayuda:getHelpTextFromMap('txtAseguradoFormId',helpMap),
	    name:'parametrosBusqueda.Asegurado',
	    value:'',
	    width: 200
	});
    var producto = new Ext.form.ComboBox({ 
        tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeProducto,
        width: 200,
        id:'cmbProductoFormId',
        mode: 'local',
        name: 'parametrosBusqueda.cdRamo',
        hiddenName: 'parametrosBusqueda.cdRamo',
        typeAhead: true,
        labelSeparator: ':',          
        triggerAction: 'all',
        valueField: 'value',
        displayField: 'label',
        fieldLabel: getLabelFromMap('cmbProductoFormId',helpMap,'Producto'),
        tooltip:getToolTipFromMap('cmbProductoFormId',helpMap,'Producto'), 
        hasHelpIcon:getHelpIconFromMap('cmbProductoFormId',helpMap),
        Ayuda:getHelpTextFromMap('cmbProductoFormId',helpMap),
	    emptyText: 'Seleccione...',
        selectOnFocus:true
    });
    var cliente = new Ext.form.ComboBox({ 
        tpl: '<tpl for="."><div ext:qtip="{dsCliente}.{cdCliente}" class="x-combo-list-item">{dsCliente}</div></tpl>',
        store: storeCliente,
        id:'cmbClienteFormId',
        width: 200,
        mode: 'local',
        name: 'parametrosBusqueda.cdElemento',
        hiddenName: 'parametrosBusqueda.cdElemento',
        typeAhead: true,
        labelSeparator: ':',          
        triggerAction: 'all',
        valueField: 'cdCliente',
        displayField: 'dsCliente',
        fieldLabel: getLabelFromMap('cmbClienteFormId',helpMap,'Nivel'),
        tooltip:getToolTipFromMap('cmbClienteFormId',helpMap,'Nivel'), 
        hasHelpIcon:getHelpIconFromMap('cmbClienteFormId',helpMap),
        Ayuda:getHelpTextFromMap('cmbClienteFormId',helpMap),
	    emptyText: 'Seleccione...',
        selectOnFocus:true
    });
    var aseguradora = new Ext.form.ComboBox({ 
        tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeAseguradora,
        id:'cmbAseguradoraFormId',
        width: 200,
        mode: 'local',
        name: 'parametrosBusqueda.cdUnieco',
        hiddenName: 'parametrosBusqueda.cdUnieco',
        typeAhead: true,
        labelSeparator: ':',          
        triggerAction: 'all',
        valueField: 'value',
        displayField: 'label',
        fieldLabel: getLabelFromMap('cmbAseguradoraFormId',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('cmbAseguradoraFormId',helpMap,'Aseguradora'), 
        hasHelpIcon:getHelpIconFromMap('cmbAseguradoraFormId',helpMap),
        Ayuda:getHelpTextFromMap('cmbAseguradoraFormId',helpMap),
        emptyText: 'Seleccione...',
        selectOnFocus:true
    });
    
    var tipoPoliza = new Ext.form.ComboBox({ 
        tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeTipoPoliza,
        id: 'tipPoliza',
        width: 200,
        mode: 'local',
        name: 'parametrosBusqueda.estado',
        hiddenName: 'parametrosBusqueda.estado',
        typeAhead: true,
        labelSeparator: ':',          
        triggerAction: 'all',
        valueField: 'value',
        displayField: 'label',
        fieldLabel: getLabelFromMap('tipPoliza',helpMap,'Estado'),
        tooltip:getToolTipFromMap('tipPoliza',helpMap,'Estado'), 
        hasHelpIcon:getHelpIconFromMap('tipPoliza',helpMap),
        Ayuda:getHelpTextFromMap('tipPoliza',helpMap),
        emptyText: 'Seleccione...',
        selectOnFocus:true,
        allowBlank: false,
        forceSelection: true
    });
	var poliza = new Ext.form.TextField({
		id:'txtPolizaFormId',
	    fieldLabel: getLabelFromMap('txtPolizaFormId',helpMap,'P&oacute;liza'),
        tooltip:getToolTipFromMap('txtPolizaFormId',helpMap,'P&oacute;liza'), 
        hasHelpIcon:getHelpIconFromMap('txtPolizaFormId',helpMap),
        Ayuda:getHelpTextFromMap('txtPolizaFormId',helpMap),
	    name:'parametrosBusqueda.nmPoliEx',
	    value:'',
	    width: 200
	});

    var filtroForm = new Ext.form.FormPanel({    
        title: '<span style="color:#98012e;font-size:12px;">B&uacute;squeda</span>',
        iconCls:'logo',
        url: _OBTIENE_POLIZAS_RENOVADAS, //'flujorenovacion/obtienePolizasRenovadas.action',
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
                            		poliza,
                            		tipoPoliza
                           ]
                        }]
                    }],
                    buttons:[{                                  
                            text:getLabelFromMap('btnBuscarFormId',helpMap,'Buscar'),
        					tooltip: getToolTipFromMap('btnBuscarFormId',helpMap,'Buscar'),
        					handler: function() {                                            
                                if (filtroForm.form.isValid()) {
                                		POLIZAS_RENOVADAS = (Ext.getCmp('tipPoliza').getValue()) == 'M'?true:false;
                                		Ext.getCmp('aprobarRenovar').setDisabled(POLIZAS_RENOVADAS);
                                		Ext.getCmp('seltodasRenovar').setDisabled(POLIZAS_RENOVADAS);
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
                            text:getLabelFromMap('btnCancelarFormId',helpMap,'Cancelar'),
        					tooltip: getToolTipFromMap('btnCancelarFormId',helpMap,'Cancelar'),
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
	var params='';
	var _fechaEfectividadEndoso;
	
		var	gridResultados = new Ext.grid.GridPanel({
			id: 'gridResultadosId',
		    store: storeGridResultado,
		    border:true,
		    buttonAlign:'center',
		    baseCls:'background:white',
		    cm: cm,
		    plugins:[aprobadachk,selchk],
			buttons:[{
						id:'polizaAnterior',
						text:getLabelFromMap('polizaAnterior',helpMap,'Poliza Anterior'),
        				tooltip: getToolTipFromMap('polizaAnterior',helpMap,'Poliza Anterior')
		            },{
						id:'detalleRenovar',
						text:getLabelFromMap('detalleRenovar',helpMap,'Detalle'),
        				tooltip: getToolTipFromMap('detalleRenovar',helpMap,'Detalle')
		            },{
						id:'exportarRenovar',
		            	text:getLabelFromMap('exportarRenovar',helpMap,'Exportar'),
        				tooltip: getToolTipFromMap('exportarRenovar',helpMap,'Exportar'),
		            	handler:function(){                		
		                	showExportDialog(_ACTION_EXPORT);
		                }
		            },{
						id:'seltodasRenovar',
		            	text:getLabelFromMap('seltodasRenovar',helpMap,'Seleccionar Todas'),
        				tooltip: getToolTipFromMap('seltodasRenovar',helpMap,'Seleccionar Todas')
		            },{
						id:'aprobarRenovar',
		            	text:getLabelFromMap('aprobarRenovar',helpMap,'Aprobar'),
        				tooltip: getToolTipFromMap('aprobarRenovar',helpMap,'Aprobar')
		            },{
						id:'recalcularRenovar',
						text:getLabelFromMap('recalcularRenovar',helpMap,'Recalcular'),
        				tooltip: getToolTipFromMap('recalcularRenovar',helpMap,'Recalcular')
		            }],
		    width:656,
		    frame:true,
		    height:340,
		    title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
		    
		    sm: new Ext.grid.RowSelectionModel({
		    	header:'Seleccion', 
		        listeners: {
					rowselect: function(sm, row, rec) { 
				        _cdUnieco    = rec.get('cdCia');
				        _cdRamo      = rec.get('cdRamo');
				        _cdCliente   = rec.get('cdCliente');
				        _estado      = rec.get('estado');
				        _nmPoliza    = rec.get('nmPoliza');				//Numero de la poliza Actual Interna
				        _nmSituac    = rec.get('nmSituac');
				        _poliza      = rec.get('polizaAnterior');		//Numero de la poliza Anterior Externa
				        _producto    = rec.get('producto');
				        _aseguradora = rec.get('aseguradora');
				        _nmPolizaAnterior = rec.get('nmPolizaAnterior');//Numero de la poliza Anterior Interna
				        _polizaRenovacion=rec.get('polizaRenovacion');	//Numero de la poliza Actual Externa (poliza de la renovacion)
				        _fechaEfectividadEndoso=rec.get('fechaRenova');
				        idRegresar.setValue( rec.get('nmPoliza') + "|" +
				        					 rec.get('nmPolizaAnterior') );
				        
					}
		        }
		        
		    }),
		    bbar: new Ext.PagingToolbar({
		        pageSize: itemsPerPage,
		        store: storeGridResultado,                  
		        displayInfo: true,
				displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar'),
				beforePageText: 'P&aacute;gina',
				afterPageText: 'de {0}'
		    })
		});
	Ext.getCmp('polizaAnterior').on('click',function(){
		window.location.href=_CONTEXT+'/procesoemision/detallePoliza.action?cdUnieco=1&cdRamo='+_cdRamo+'&estado=M&nmPoliza='+_nmPolizaAnterior+'&poliza='+_poliza+'&producto='+_producto+'&aseguradora='+_aseguradora+'&volver3=true';
	});
	Ext.getCmp('detalleRenovar').on('click',function(){
		if(gridResultados.getSelectionModel().hasSelection()){
			if(POLIZAS_RENOVADAS){
				window.location.href=_CONTEXT+'/procesoemision/detallePoliza.action?cdUnieco=1&cdRamo='+_cdRamo+'&estado=M&nmPoliza='+_nmPoliza+'&poliza='+_polizaRenovacion+'&producto='+_producto+'&aseguradora='+_aseguradora+'&volver3=true'+"&idRegresar="+idRegresar.getValue();
			}else{
				window.location.href=_CONTEXT+'/flujoendoso/detallePoliza.action?cdUnieco=1&volver3=true'+'&cdRamo='+_cdRamo+'&estado=W&nmPoliza='+_nmPoliza+'&poliza='+_polizaRenovacion+'&producto='+_producto+'&aseguradora='+_aseguradora+'&proc=ren'+'&fechaefectividadendoso='+_fechaEfectividadEndoso+"&idRegresar="+idRegresar.getValue();
			}
		}else{
			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		}
	});
	Ext.getCmp('seltodasRenovar').on('click',function(){
		for(var i=0;i<storeGridResultado.getCount();i++){
	            var record = gridResultados.store.getAt(i);
	            var sel = record.get('seleccionar');
	            record.set('seleccionar', true);
		}
	});
	Ext.getCmp('aprobarRenovar').on('click',function(){
		
		var w=0;
		params="";
		var polizaRenovada;
		for(var i=0;i<storeGridResultado.getCount();i++){
	            polizaRenovada = gridResultados.store.getAt(i);
	            if(polizaRenovada!=undefined && polizaRenovada.get('seleccionar')){

		            	params += "parametrosRenovar["+w+"].cdUnieco="+polizaRenovada.get('cdUnieco')
						+"&&parametrosRenovar["+w+"].cdRamo="+polizaRenovada.get('cdRamo')
						+"&&parametrosRenovar["+w+"].nmPoliza="+polizaRenovada.get('nmPolizaAnterior') //Se tiene que mandar el numero de poliza interno anterior
						+"&&parametrosRenovar["+w+"].swAprobada="+polizaRenovada.get('swAprobada')+"&&";
		            	
						w++;

	            }
		}
		
		if(w>0){
			
			var connect = new Ext.data.Connection();
			var url = 'flujorenovacion/aprobarPolizas.action?cdUnieco='+_cdUnieco+'&cdRamo='+_cdRamo+'&estado='+_estado+'&nmPoliza='+_nmPoliza;
			connect.request ({
					url: url,
					method: 'POST',
					successProperty : '@success',
					params : params,
					callback: function (options, success, response) {
						
						var msgResp = Ext.util.JSON.decode(response.responseText).msgText;
						
						if(Ext.isEmpty(msgResp)) msgResp = getLabelFromMap('400097', helpMap,'Ocurrio un error en la transacci&oacute;n');
						
						if (Ext.util.JSON.decode(response.responseText).success == false) {
							 Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), msgResp);
	                    }else{
	                    	 Ext.MessageBox.alert(getLabelFromMap('400099', helpMap,'Aprobar'), msgResp);
	                    }
	                     
					}
			});
			
		}else{
			Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), getLabelFromMap('400100', helpMap,'No se ha seleccionado ninguna p&oacute;liza.'));
		}
			
		
	});
	Ext.getCmp('recalcularRenovar').on('click',function(){
		if(_cdUnieco==null){
			Ext.Msg.alert('Recalcular', 'Debe seleccionar un registro');
			return;
		}
		var connect = new Ext.data.Connection();
		var url = 'flujorenovacion/validaPoliza.action?cdUnieco='+_cdUnieco+'&cdRamo='+_cdRamo+'&estado='+_estado+'&nmPoliza='+_nmPoliza;
		connect.request ({
				url: url,
				method: 'POST',
				successProperty : '@success',
				params : params,
				callback: function (options, success, response) {
					
					if (Ext.util.JSON.decode(response.responseText).resultadoValidaPoliza=='fail') {
						 Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), getLabelFromMap('400097', helpMap,'Ocurrio un error en la transacci&oacute;n'));
                    }else{
                    	Ext.MessageBox.alert(getLabelFromMap('400101', helpMap,'Recalcular'), getLabelFromMap('400102', helpMap,'P&oacute;liza v&aacute;lida'));
                    }
                     
				}
		});
	});
//	}
	
	//******************PANEL GRID RESULTADOS******************
    var resultadosPanelGrid = new Ext.form.FormPanel({    
    	el:'listadoRenovacion',
        title: '<span style="color:#98012e;font-size:12px;">Listado</span>',
        iconCls:'logo',
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
                    items:[gridResultados]
                }]
		}]  
	});

	//******************PANEL GRID MAIN******************
	var mainPanelGrid = new Ext.Panel({
	    border: false,
	    width: 680,
	    autoHeight: true,
	    items:[{
	        title:'<span style="color:black;font-size:14px;">P&oacute;lizas en proceso de renovaci&oacute;n</span>',
	        labelAlign: 'center',
	        layout:'form',
	        frame: true,
	        buttonAlign:'center',
	        items:[filtroForm,resultadosPanelGrid]
	   }]
	});
	mainPanelGrid.render('mainRenovacion');
	
/// DATOS - BOTON REGRESAR

	if ( _SESSION_PARAMETROS_REGRESAR ) {
		
		Ext.getCmp('txtAseguradoFormId').setValue( _SESSION_PARAMETROS_REGRESAR.asegurado );
		Ext.getCmp('txtPolizaFormId').setValue( _SESSION_PARAMETROS_REGRESAR.poliza );
		storeAseguradora.load({
			callback: function(r,options,success) {
				Ext.getCmp('cmbAseguradoraFormId').setValue( _SESSION_PARAMETROS_REGRESAR.aseguradora );
			}
		});
		storeCliente.load({
			callback: function(r,options,success) {
				Ext.getCmp('cmbClienteFormId').setValue( _SESSION_PARAMETROS_REGRESAR.nivel );
			}
		});
		storeProducto.load({
			callback: function(r,options,success) {
				Ext.getCmp('cmbProductoFormId').setValue( _SESSION_PARAMETROS_REGRESAR.producto );
			}
		});
    	storeTipoPoliza.load({
			callback: function(r, options, success){
				Ext.getCmp('tipPoliza').setValue( _SESSION_PARAMETROS_REGRESAR.estado );
			}
		});
		
		storeGridResultado.removeAll();
		storeGridResultado.baseParams['idRegresar'] = _SESSION_PARAMETROS_REGRESAR.idRegresar;
		storeGridResultado.baseParams['parametrosBusqueda.Asegurado'] = _SESSION_PARAMETROS_REGRESAR.asegurado;
		storeGridResultado.baseParams['parametrosBusqueda.cdUnieco'] = _SESSION_PARAMETROS_REGRESAR.aseguradora;
		storeGridResultado.baseParams['parametrosBusqueda.cdRamo'] = _SESSION_PARAMETROS_REGRESAR.producto;
		storeGridResultado.baseParams['parametrosBusqueda.cdElemento'] = _SESSION_PARAMETROS_REGRESAR.nivel;
		storeGridResultado.baseParams['parametrosBusqueda.nmPoliEx'] = _SESSION_PARAMETROS_REGRESAR.poliza;
		storeGridResultado.baseParams['parametrosBusqueda.estado'] = _SESSION_PARAMETROS_REGRESAR.estado;
		storeGridResultado.load({
			callback: function(r,options,success) {
				function buscaRecord(stringIdRegresar) {
					var array = stringIdRegresar.split("|");
					var _nmpoliza = array[0];
					var _nmpolant = array[1];
					var index = -1;
					var bandera = true;
					do {
						index = storeGridResultado.find('nmPoliza', _nmpoliza, index + 1);
						var record = storeGridResultado.getAt(index);
						if ( record.get('nmPolizaAnterior') == _nmpolant ) {
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
		storeAseguradora.load();
    	storeCliente.load();
    	storeProducto.load();
    	storeTipoPoliza.load({
			callback: function(r, options, success){
				Ext.getCmp('tipPoliza').setValue(r[0].get('value'));
			}
		});
	}

/////

});

	Ext.grid.CheckAprobada = function(config){
    	Ext.apply(this, config);
    	if(!this.id){
        	this.id = Ext.id();
    	}
    	this.renderer = this.renderer.createDelegate(this);
	};

	Ext.grid.CheckAprobada.prototype ={
    init : function(grid){
        this.grid = grid;
        this.grid.on('render', function(){
            var view = this.grid.getView();
            view.mainBody.on('mousedown', this.onMouseDown, this);
        }, this);
    },
    onMouseDown : function(e, t){
    	if(!POLIZAS_RENOVADAS){
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
    	}
    },
    renderer : function(v, p, record){
        p.css += ' x-grid3-check-col-td'; 
        return '<div class="x-grid3-check-col'+(v=='S'?'-on':'')+' x-grid3-cc-'+this.id+'">&#160;</div>';
    }
	};
	
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
	        if(!POLIZAS_RENOVADAS){
		        if(t.className && t.className.indexOf('x-grid3-cc-'+this.id) != -1){
		            e.stopEvent();
		            var index = this.grid.getView().findRowIndex(t);
		            var record = this.grid.store.getAt(index);
		            record.set(this.dataIndex, !record.data[this.dataIndex]);
		        }
	        }
	    },
	
	    renderer : function(v, p, record){
	        p.css += ' x-grid3-check-col-td'; 
	        if(v=='undefined'){
	        	v=false;
	        	record.set(this.dataIndex, v);
	        }
	        return '<div class="x-grid3-check-col'+(v?'-on':'')+' x-grid3-cc-'+this.id+'">&#160;</div>';
	    }
	};