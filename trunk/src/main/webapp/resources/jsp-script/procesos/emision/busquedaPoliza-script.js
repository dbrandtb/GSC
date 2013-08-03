Ext.onReady(function(){

    Ext.QuickTips.init();
    Ext.QuickTips.enable();
    Ext.form.Field.prototype.msgTarget = 'side';
   
	var idRegresar = new Ext.form.Hidden({
        id: 'idRegresar',
        name: 'idRegresar',
        value: ''
    });

    var storeAseguradoras = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'procesoemision/obtenerAseguradoras.action'
            }),
            reader: new Ext.data.JsonReader({
            root: 'listAseguradoras',
            id: 'value'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}  
             ]),
            remoteSort: true
	});
	storeAseguradoras.setDefaultSort('value', 'desc');
	//storeAseguradoras.load();
        
	var storeProducto = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'procesoemision/obtenerProductos.action',
			method: 'POST'
		}),
		reader: new Ext.data.JsonReader({
			root: 'listProductos',
			id: 'value'
		},[
			{name: 'value', type: 'string', mapping:'value'},
			{name: 'label', type: 'string', mapping:'label'}  
		]),
		remoteSort: true
	});
	storeProducto.setDefaultSort('value', 'desc');
	//storeProducto.load();
        
	var storeStatusPoliza = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'procesoemision/obtenerStatusPoliza.action',
			method: 'POST'
		}),
		reader: new Ext.data.JsonReader({
			root: 'listStatusPoliza',
			id: 'value'
		},[
			{name: 'value', type: 'string', mapping:'value'},
			{name: 'label', type: 'string', mapping:'label'}  
		]),
		remoteSort: true
	});
	storeStatusPoliza.setDefaultSort('value', 'desc');
	//storeStatusPoliza.load();
   
    var lasFilas=new Ext.data.Record.create([
        {name: 'dsAseguradora', type: 'string',  mapping:'aseguradora'},
        {name: 'dsProducto',    type: 'string',  mapping:'producto'},
        {name: 'dsPoliza',      type: 'string',  mapping:'poliza'},
        {name: 'inciso',        type: 'string',  mapping:'inciso'},
        {name: 'dsStatusPoliza',type: 'string',  mapping:'estado'},
        {name: 'dsPeriocidad',  type: 'string',  mapping:'periocidad'},
        {name: 'dsAsegurado',   type: 'string',  mapping:'nombrePersona'},
        {name: 'dsRfc',         type: 'string',  mapping:'rfc'},
        {name: 'dsVigencia',    type: 'string',  mapping:'vigencia'},
        {name: 'dsHasta',       type: 'string',  mapping:'vigencia'},
        {name: 'dsInstPago',    type: 'string',  mapping:'instPago'},
        {name: 'dsPrima',       type: 'string',  mapping:'primaTotal'},
        {name: 'cdRamo',        type: 'string',  mapping:'cdRamo'},
        {name: 'cdUnieco',      type: 'string',  mapping:'cdUnieco'},
        {name: 'nmPoliza',      type: 'string',  mapping:'nmPoliza'},
        {name: 'feEfecto',      type: 'string',  mapping:'feEfecto'},
        {name: 'feVencim',      type: 'string',  mapping:'feVencim'},
        {name: 'cdUniAge',      type: 'string',  mapping:'cdUniAge'},
        {name: 'swEstado',      type: 'string',  mapping:'swEstado'},
        {name: 'status',        type: 'string',  mapping:'status'},
        {name: 'swCancel',      type: 'string',  mapping:'swCancel'}                             
    ]);
    
    var jsonGrilla= new Ext.data.JsonReader({    
            root:'emisiones',
            totalProperty: 'totalCount'
        },
        lasFilas
    );
    
    var cm = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
            {header: "cdRamo",          dataIndex:'cdRamo',         width:20,   sortable:true,  hidden:true},
            {header: "cdUnieco",        dataIndex:'cdUnieco',       width:20,   sortable:true,  hidden:true},
            {header: "nmPoliza",        dataIndex:'nmPoliza',       width:20,   sortable:true,  hidden:true},
            {header: "swCancel",        dataIndex:'swCancel',       width:20,   sortable:true,  hidden:true},
            {id:'Aseguradora', 			header: "Aseguradora",     	dataIndex:'dsAseguradora',  width:80, sortable:true},
            {header: "Producto",        dataIndex:'dsProducto',     width:300,  sortable:true},
            {header: "P&oacute;liza",   dataIndex:'dsPoliza',       width:100,  sortable:true},
            {header: "Inciso",          dataIndex:'inciso',         width:50,  sortable:true},
            {header: "Estatus",			dataIndex:'status',         width:70,   sortable:true},
            {header: "Periodicidad",    dataIndex:'dsPeriocidad',   width:100,  sortable:true},
            {header: "Asegurado",       dataIndex:'dsAsegurado',    width:300,  sortable:true},
            {header: "R.F.C.",          dataIndex:'dsRfc',          width:100,   sortable:true},
            {header: "Vigencia",        dataIndex:'dsVigencia',     width:130,  sortable:true},
            {header: "Inst. de Pago",   dataIndex:'dsInstPago',     width:170,  sortable:true},
            {header: "Prima Total",     dataIndex:'dsPrima',        width:100,  sortable:true,  renderer:Ext.util.Format.usMoney},
            {dataIndex:'feEfecto',  hidden: true},
            {dataIndex:'feVencim',  hidden: true},
            {dataIndex:'cdUniAge',  hidden: true}            
    ]);
    cm.defaultSortable = true;
    
	//var t = filterAreaForm.findById('poliza');
	var storeGrid = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'procesoemision/buscaPolizas.action'+'?store=STORE',
			method: 'POST'
		}),
		reader: jsonGrilla
	});
 
    var storeFecha = new Ext.data.SimpleStore({
        fields: ['id', 'texto'],
        data: [
            ['I', 'Inicio'],
            ['F', 'Fin']
        ]
    });

//////FORM PARA LOS FILTROS//////////////////////////

 var filterAreaForm = new Ext.form.FormPanel({
        url: 'procesoemision/buscaPolizas.action',
        title: '<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Búsqueda</span>',                        
        layout: 'form',
        id: 'filterArea',
        border: false,
        width: 760,
        bodyStyle: 'padding:5px 5px 0',
        autoHeight: true,   
        items: [{
			border:false,
			frame:false,
			autoHeight: true,
			width: 750,
			buttonAlign : 'center',
			labelAlign:'right',
			items :[{
				layout:'column',
				border:false, 
				items :[{
					columnWidth:.4,
					layout: 'form',
					bodyStyle:'padding:5px 5px 0',
					border: false,
					labelWidth : 60, 
					items: [ idRegresar,
					{
						xtype:"combo",
						id: 'producto',
						emptyText:"Seleccione...",
						store: storeProducto,
						hiddenName: "producto",
						fieldLabel: "Producto",
						name: "producto",
						allowBlank:true,
						mode:'local',
						editable: true,
						triggerAction: "all",
						valueField: "value",
						typeAhead: true,
						displayField:"label"
					},{
						xtype:"textfield",
						fieldLabel: 'Póliza',
						anchor:'84%',
						name:'poliza',
						id: 'poliza'
					},{
						xtype:"textfield",
						fieldLabel: 'Asegurado',
						anchor:'84%',
						name:'nombreAsegurado',
						id: 'asegurado'
					},{
						xtype:"textfield",
						fieldLabel: 'Inciso',
						achor:'84%',
						name:'cdInciso',
						id: 'inciso'
					}]//items columna1
				},{
					columnWidth:.5,
					layout: 'form',
					bodyStyle:'padding:5px 5px 0',
					border: false,
					labelWidth : 100,
					items: [{
						fieldLabel:"Aseguradora",
						emptyText:"Seleccione...",
						xtype:"combo",   
						hiddenName:"aseguradora",     
						name:"aseguradora",
						id:"idAseguradora",
						store: storeAseguradoras,
						displayField:"label",
						valueField:"value", 
						triggerAction: "all",
						anchor:'100%', 
						mode:'local',
						typeAhead: true,
						editable:true
					},{
						xtype:"combo",
						emptyText:"Seleccione...",
						store: storeFecha,
						name: "fechaInicioFin",
						hiddenName:"fechaInicioFin",
						valueField: "id",
						displayField:"texto",
						fieldLabel: "Vigencia",
						width:"50", 
						anchor:"100%",
						allowBlank:false,
						id: "inicioFin",
						mode:'local',
						editable:true,
						triggerAction: "all",
						typeAhead: true,
						value:'I'
					},{
						border:false,
						layout:'column',
						items:[{
							columnWidth:.58,
							layout: 'form',
							border: false,
							labelWidth : 100,
							items:[{
								xtype:'datefield',
								fieldLabel: 'Desde',
								name: 'inicioVigencia',
								width:'120', 
								anchor:'100%',
								allowBlank: true,
								format: 'd/m/Y',
								id: 'inicioVigencia'
							}]
						},{
							columnWidth:.42,
							layout: 'form',
							border:false,
							labelWidth : 40,
							items: [{
								xtype:'datefield',
								fieldLabel: ' Hasta',
								name: 'finVigencia',
								allowBlank:true,
								width:'95', 
								anchor:'100%',
								format: 'd/m/Y',
								id: 'finVigencia'
							}]
						}]   //items fecha
					},{ 
						fieldLabel:"Estatus de Póliza",
						emptyText:"Seleccione...",
						xtype:"combo",        
						name:"statusPoliza",
						hiddenName:"statusPoliza",
						id:"idStatusPoliza",
						store: storeStatusPoliza,
						displayField:"label",
						valueField:"value", 
						triggerAction: "all",
						mode:'local',
						anchor:'100%', 
						typeAhead: true,
						editable:true
					}]//items columna2
				}]//items layout column                   
			}],////end items FieldSet
			buttons:[{                                  
				text:'Buscar',  
				handler: function() {
					if (filterAreaForm.form.isValid()) {
						filterAreaForm.form.submit({      
							waitTitle:'Espere',           
							waitMsg:'Procesando...',
							failure: function(form, action) {
								Ext.MessageBox.alert('Busqueda Finalizada', 'No se encontraron registros');
								//storeGrid.load();
							},
							success: function(form, action) {
								storeGrid.load();
							}
						});                   
					} else {
						Ext.MessageBox.alert('Error', 'Se produjo error');
					}  
				}      //end handler                                                 
			},{
				text:'Cancelar',
				handler: function() {
					filterAreaForm.form.reset();
				}
			}] //end buttons FieldSet
		}]//end items
	});


//////FORM PARA RESULTADOS//////////////////////////

 var resultAreaForm = new Ext.form.FormPanel({
        layout:'form',
        id:'resultArea',
        border: false,
        bodyStyle:'padding:5px 5px 0',
        autoHeight : true,
		width:740,
		items:[{
			xtype: 'grid',
			id:'gridResultado',
			store: storeGrid,
			title: '<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
			stripeRows: true,
			cm: cm,
			height:250,
			width: 730,
			sm: new Ext.grid.RowSelectionModel({
				singleSelect: true,
				//frame:true,
				listeners: {                            
					rowselect: function(sm, row, rec) { 
						var cdUnieco = rec.get('cdUnieco');
						var cdRamo = rec.get('cdRamo');
						var estado = rec.get('dsStatusPoliza');
						var nmPoliza = rec.get('nmPoliza');                        
						var aseguradora = rec.get('dsAseguradora');
						var producto = rec.get('dsProducto');
						var poliza = rec.get('dsPoliza'); 
						var dsNombre = rec.get('dsAsegurado');
						var cdUniAge = rec.get('cdUniAge');
						var myFecha = rec.get('feEfecto');
						var fechas= myFecha.slice(0,10);
						var fecha= fechas.split("-");
						var inciso = rec.get('inciso');
						var status = rec.get('status');
						var swCancel = rec.get('swCancel');

						var feEfecto = fecha[2]+'/'+fecha[1]+'/'+fecha[0];
						myFecha= rec.get('feVencim');
						var fechas= myFecha.slice(0,10);
						var fecha= fechas.split("-");

						var feVencim = fecha[2]+'/'+fecha[1]+'/'+fecha[0];
						
						idRegresar.setValue( rec.get('nmPoliza') + "|" +
											 rec.get('inciso') + "|" +
											 rec.get('status') );

						Ext.getCmp('detalle').on('click', function(){
							window.location.href = _CONTEXT+"/procesoemision/detallePoliza.action?"+"cdUnieco="+cdUnieco+"&cdRamo="+cdRamo+"&estado="+estado+"&nmPoliza="+nmPoliza+"&poliza="+poliza+"&producto="+producto+"&aseguradora="+aseguradora+"&idRegresar="+idRegresar.getValue();
						});

						Ext.getCmp('emisionEndoso').on('click',function(){    
							if (sm.getSelected().get('status') == 'CANCELADA' ||
									sm.getSelected().get('status') == 'Cancelada'){
								Ext.MessageBox.alert('Aviso', 'La p&oacute;liza ya est&aacute; cancelada');						                                                                                    
							} else {
								var params  = "cdRamo="+cdRamo;
								var conn = new Ext.data.Connection();
								conn.request ({
									url:_ACTION_VALIDA_ENDOSO,
									method: 'POST',
									successProperty : '@success',
									params : params,
									callback: function (options, success, response) {    
										validacion = Ext.util.JSON.decode(response.responseText).validaEndoso;
										if( validacion==true ){                				
											window.location.href = _CONTEXT+"/flujoendoso/detallePoliza.action?cdUnieco="+cdUnieco+"&cdRamo="+cdRamo+"&estado="+estado+"&nmPoliza="+nmPoliza+"&poliza="+poliza+"&producto="+producto+"&aseguradora="+aseguradora+"&inciso="+inciso+"&proc=end"+"&idRegresar="+idRegresar.getValue();
										}else{
											Ext.MessageBox.alert('Aviso', 'Para el producto seleccionado no se permiten endosos');					                			
										}                		                	
									}
								});														    
							}
						});

						Ext.getCmp('cancelar').on('click',function(){
							if (sm.getSelected().get('status') != 'CANCELADA'){
								if (sm.getSelected().get('swCancel') != 'S'){
									Ext.MessageBox.alert('Atenci&oacute;n', 'El producto no permite la cancelaci&oacute;n de p&oacute;lizas');
								} else {
									window.location = _ACTION_IR_CANCELACION_MANUAL+ '?nombreAsegurado=' + dsNombre + '&cdUnieco=' + cdUnieco +'&dsUnieco=' + aseguradora +'&cdRamo=' + cdRamo + '&dsRamo=' + producto +'&nmPoliza='+ nmPoliza +'&poliza=' + poliza + '&feEfecto=' + feEfecto +'&feVencim='+ feVencim +'&cdUniAge='+ cdUniAge;
								}
							} else { 
								Ext.MessageBox.alert('P&oacute;liza cancelada', 'La p&oacute;liza ya est&aacute; cancelada');
							}
						});
					}
				}
			}), //termina sm
			buttonAlign: 'center',
			buttons:[{
				id:'detalle',
				text:'Detalle',
				tooltip:'Detalle de la opcion seleccionada'                           
			},{
				text:'Exportar',
				tooltip:'Exporta opciones',
				handler: exportButton(_ACTION_EXPORT)
			},{
				id:'emisionEndoso',
				text:'Endoso',
				tooltip:'Endoso'
			},{
				id: 'cancelar',
				text:'Cancelar',
				tooltip:'Cancelaci&oacute;n Manual'
			}],
			bbar: new Ext.PagingToolbar({
				pageSize: 20,
				store: storeGrid,
				displayInfo: true,
				displayMsg: 'Registros mostrados {0} - {1} de {2}',
				emptyMsg: 'No hay registros para mostrar',
				beforePageText: 'P&aacute;gina',
				afterPageText: 'de {0}'
			})
		}]//items
	});

/// DATOS - BOTON REGRESAR

	if ( _SESSION_PARAMETROS_REGRESAR ) {

		Ext.getCmp('poliza').setValue( _SESSION_PARAMETROS_REGRESAR.poliza );
		Ext.getCmp('asegurado').setValue( _SESSION_PARAMETROS_REGRESAR.asegurado );
		Ext.getCmp('inciso').setValue( _SESSION_PARAMETROS_REGRESAR.inciso );
		Ext.getCmp('inicioVigencia').setValue( _SESSION_PARAMETROS_REGRESAR.desde );
		Ext.getCmp('finVigencia').setValue( _SESSION_PARAMETROS_REGRESAR.hasta );
		Ext.getCmp('inicioFin').setValue( _SESSION_PARAMETROS_REGRESAR.vigencia );
		storeProducto.load({
			callback: function(r,options,success) {
				Ext.getCmp('producto').setValue( _SESSION_PARAMETROS_REGRESAR.producto );
			}
		});
		storeAseguradoras.load({
			callback: function(r,options,success) {
				Ext.getCmp('idAseguradora').setValue( _SESSION_PARAMETROS_REGRESAR.aseguradora );
			}
		});
    	storeStatusPoliza.load({
			callback: function(r, options, success){
				Ext.getCmp('idStatusPoliza').setValue( _SESSION_PARAMETROS_REGRESAR.estatus );
			}
		});
		

		storeGrid.baseParams['idRegresar'] = _SESSION_PARAMETROS_REGRESAR.idRegresar;
		storeGrid.baseParams['producto'] = _SESSION_PARAMETROS_REGRESAR.producto;
		storeGrid.baseParams['poliza'] = _SESSION_PARAMETROS_REGRESAR.poliza;
		storeGrid.baseParams['nombreAsegurado'] = _SESSION_PARAMETROS_REGRESAR.asegurado;
		storeGrid.baseParams['cdInciso'] = _SESSION_PARAMETROS_REGRESAR.inciso;
		storeGrid.baseParams['aseguradora'] = _SESSION_PARAMETROS_REGRESAR.aseguradora;
		storeGrid.baseParams['fechaInicioFin'] = _SESSION_PARAMETROS_REGRESAR.vigencia;
		storeGrid.baseParams['inicioVigencia'] = _SESSION_PARAMETROS_REGRESAR.desde;
		storeGrid.baseParams['finVigencia'] = _SESSION_PARAMETROS_REGRESAR.hasta;
		storeGrid.baseParams['statusPoliza'] = _SESSION_PARAMETROS_REGRESAR.estatus;
		storeGrid.load({
			callback: function(r,options,success) {
				function buscaRecord(stringIdRegresar) {
					var array = stringIdRegresar.split("|");
					var _nmpoliza = array[0];
					var _inciso = array[1];
					var _estatus = array[2];
					var index = -1;
					var bandera = true;
					do {
						index = storeGrid.find('nmPoliza', _nmpoliza, index + 1);
						var record = storeGrid.getAt(index);
						if ( record.get('inciso') == _inciso && record.get('estatus') == _estatus ) {
							var record0 = storeGrid.getAt(0);
							// Swap de Ext.Data.Record
							storeGrid.remove(record0);
							storeGrid.remove(record);
							storeGrid.insert(0, record);
							storeGrid.insert(index, record0);
							bandera = false;
						}
					} while (bandera);
					return;
				}
				if ( storeGrid.getCount() > 1 )
					buscaRecord(_SESSION_PARAMETROS_REGRESAR.idRegresar);
				Ext.getCmp('gridResultado').getSelectionModel().selectRow(0);
			}
		});


	} else {
		storeAseguradoras.load();
    	storeProducto.load();
    	storeStatusPoliza.load();
	}

/////
	
  
/*************************************************************
** Panel
**************************************************************/ 
	var panelPrincipal = new Ext.Panel({
        region: 'north',
        title: 'Consulta de pólizas',
        autoHeight : true ,
        width: 760,
        id:'panelPrincipal',
        items: [filterAreaForm, resultAreaForm ] 
    });
	panelPrincipal.render('items');

});