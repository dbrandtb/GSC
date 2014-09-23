Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);

Ext.onReady(function() {
	
	Ext.selection.CheckboxModel.override( {
	    mode: 'SINGLE',
	    allowDeselect: true
	});

	//Models:
	Ext.define('modelFactCtrl', {
	    extend:'Ext.data.Model',
	    fields:['noFactura','fechaFactura','tipoServicio','tipoServicioName','proveedor','proveedorName','importe','tipoMoneda','tipoMonedaName','tasaCambio','importeFactura']
	});
	
	Ext.define('modelListadoProvMedico',{
	    extend: 'Ext.data.Model',
	    fields: [
            {type:'string', name:'cdpresta'},
            {type:'string', name:'nombre'},
            {type:'string', name:'cdespeci'},
            {type:'string',name:'descesp'}
	    ]
	});
	
	Ext.define('modelListadoPoliza',{
	    extend: 'Ext.data.Model',
	    fields: [	{type:'string',    name:'cdramo'},				{type:'string',    name:'cdunieco'},				{type:'string',    name:'estado'},
					{type:'string',    name:'nmpoliza'},			{type:'string',    name:'nmsituac'},				{type:'string',    name:'mtoBase'},
					{type:'string',    name:'feinicio'},			{type:'string',    name:'fefinal'},					{type:'string',    name:'dssucursal'},
					{type:'string',    name:'dsramo'},				{type:'string',    name:'estatus'},					{type:'string',    name:'dsestatus'},
					{type:'string',    name:'nmsolici'},			{type:'string',    name:'nmsuplem'},				{type:'string',    name:'cdtipsit'},
					{type:'string',    name:'dsestatus'},			{type:'string',    name:'vigenciaPoliza'},			{type:'string',    name:'faltaAsegurado'},
					{type:'string',    name:'fcancelacionAfiliado'},{type:'string',    name:'desEstatusCliente'},		{type:'string',    name:'numPoliza'}]
	});
	
	Ext.define('modelListAsegPagDirecto',{
	    extend: 'Ext.data.Model',
	    fields: [
	                {type:'string',    name:'modUnieco'},		{type:'string',    name:'modEstado'},		    {type:'string',    name:'modRamo'},
	                {type:'string',    name:'modNmsituac'},		{type:'string',    name:'modPolizaAfectada'},	{type:'string',    name:'modCdpersondesc'},
	                {type:'string',    name:'modNmsolici'},		{type:'string',    name:'modNmsuplem'},		    {type:'string',    name:'modCdtipsit'},
	                {type:'string',    name:'modNmautserv'},	{type:'string',    name:'modFechaOcurrencia'},	{type:'string',    name:'modCdperson'},
	                {type:'string',    name:'modnumPoliza'}
	            ]
	});
	

	//Stores:
	
	oficinaReceptora = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url:_URL_CATALOGOS,
            extraParams : {
            	catalogo:_CATALOGO_OFICINA_RECEP,
            	'params.idPadre' : '1000'
            },
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
	
	oficinaEmisora = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url:_URL_CATALOGOS,
            extraParams : {
            	catalogo:_CATALOGO_OFICINA_RECEP,
            	'params.idPadre' : '1000'},
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
	var storeFactCtrl=new Ext.data.Store(
	{
	    autoDestroy: true,
	    model: 'modelFactCtrl'
	});

	var storeListAsegPagDirecto=new Ext.data.Store(
	{
	    autoDestroy: true,						model: 'modelListAsegPagDirecto'
	});
	
	var storeTipoAtencion = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_TipoAtencion},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeTipoAtencion.load();
	
	var storeTipoAtencion2 = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_TipoAtencion},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeTipoAtencion2.load();
	var storeAsegurados = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_LISTADO_ASEGURADO,
            reader: {
                type: 'json',
                root: 'listaAsegurado'
            }
        }
    });
	
	var storeAsegurados2 = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_LISTADO_ASEGURADO,
            reader: {
                type: 'json',
                root: 'listaAsegurado'
            }
        }
    });
	
	var storeTipoPago = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_TipoPago},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
    storeTipoPago.load();
    
    var storeTipoMoneda = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_TipoMoneda},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
    storeTipoMoneda.load();
    
    var storeTipoMonedaInterna = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_TipoMoneda},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
    storeTipoMonedaInterna.load();
    
    var storeProveedor = Ext.create('Ext.data.Store', {
        model:'modelListadoProvMedico',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_CATALOGOS,
            extraParams:{
                catalogo         : _CATALOGO_PROVEEDORES,
                catalogoGenerico : true
            },
            reader: {
                type: 'json',
                root: 'listaGenerica'
            }
        }
    });

    var storeListadoPoliza = new Ext.data.Store(
    {
    	pageSize : 5
        ,model      : 'modelListadoPoliza'
        ,autoLoad  : false
        ,proxy     :
        {
            enablePaging : true,
            reader       : 'json',
            type         : 'memory',
            data         : []
        }
    });
    
    storeRamos = Ext.create('Ext.data.Store', {
		model:'Generic',
		autoLoad:true,
		proxy:
		{
			type: 'ajax',
			url:_UR_LISTA_RAMO_SALUD,
			reader:
			{
				type: 'json',
				root: 'listadoRamosSalud'
			}
		}
	});
	
	storeRamos.load();
    
    var cmbOficinaReceptora = Ext.create('Ext.form.field.ComboBox',
	{
	    fieldLabel : 'Oficina receptora',				id:'cmbOficReceptora',			name      : 'cmbOficReceptora',
	    allowBlank : false,							editable   : true,				displayField : 'value',
	    labelWidth : 250,		   					 emptyText:'Seleccione...',		width		 : 500,
	    valueField   : 'key',						forceSelection : true,			queryMode      :'local',
	    store : oficinaReceptora
	});
   
    var cmbOficinaEmisora = Ext.create('Ext.form.field.ComboBox',
	{
	    fieldLabel : 'Oficina emisora',				id:'cmbOficEmisora',			name      : 'cmbOficEmisora',
	    allowBlank : false,							editable   : true,				displayField : 'value',
	    labelWidth : 250,		   					 emptyText:'Seleccione...',		width		 : 500,
	    valueField   : 'key',						forceSelection : true,			queryMode      :'local',
	    store : oficinaEmisora
	});
	
    cmbRamos = Ext.create('Ext.form.field.ComboBox',
	{
		colspan	   :2,			fieldLabel   : 'Producto',			id        : 'cmbRamos',		allowBlank     : false,	
	    editable   : false,		displayField : 'value',			valueField: 'key',			forceSelection : false,
	    width	   :500,		labelWidth   : 250,				queryMode :'local',			name           :'cmbRamos'
	    ,store : storeRamos
	    ,listeners : {
	    	'select' : function(combo, record) {
	    		if(Ext.getCmp('cmbTipoPago').getValue() == _TIPO_PAGO_DIRECTO){ // --> Pago Directo
	    			storeListAsegPagDirecto.removeAll();
	    		}else{
	    			// --> Pago por Reembolso
	    			Ext.getCmp('cmbAseguradoAfectado').setValue("");
					Ext.getCmp('cmbBeneficiario').setValue("");
	    		}
	    	}
	    }
	});
	
	var comboTipoAte= Ext.create('Ext.form.ComboBox',
    {
        id:'cmbTipoAtencion',			   name:'cmbTipoAtencion',		        fieldLabel: 'Tipo atenci&oacute;n',				queryMode:'local',
        displayField: 'value',	   valueField: 'key',					editable:false,							allowBlank : false,
        labelWidth : 250,		   emptyText:'Seleccione...',			width		 : 500,						store: storeTipoAtencion
    });
    
    var tipoPago= Ext.create('Ext.form.ComboBox',
    		{
    	id:'cmbTipoPago',			   name:'cmbTipoPago',		        fieldLabel: 'Tipo pago',				queryMode:'local',
    	displayField: 'value',	   valueField: 'key',					allowBlank:false,						editable:false,
    	labelWidth : 250,		   width		 : 500,					emptyText:'Seleccione...',			store: storeTipoPago,
    	listeners : {
    		
    		change:function(e){
	    		Ext.getCmp('idnombreBeneficiarioProv').setValue('');
    			Ext.getCmp('idnombreAsegurado').setValue('');
				Ext.getCmp('cmbTipoMoneda').hide();
    				
				if(e.getValue() == _TIPO_PAGO_DIRECTO){
    				//PAGO DIRECTO
    				limpiarRegistrosTipoPago(e.getValue());
    				
    			}else{
    				//PAGO POR REEMBOLSO
    				limpiarRegistrosTipoPago(e.getValue());
    			}
    		}
    	}
	});

	aseguradoAfectado = Ext.create('Ext.form.field.ComboBox',
    {
    	fieldLabel : 'Asegurado afectado',      displayField : 'value',         name:'cmbAseguradoAfectado',
    	id:'cmbAseguradoAfectado',              labelWidth: 250,            width:500,                      valueField   : 'key',
    	forceSelection : true,                 matchFieldWidth: false,     queryMode :'remote',            queryParam: 'params.cdperson',
    	minChars  : 2,                          store : storeAsegurados,    triggerAction: 'all',			hideTrigger:true,	allowBlank:false,
    	listeners : {
            'select' : function(combo, record) {
                    obtieneCDPerson = this.getValue();
                    Ext.getCmp('idnombreAsegurado').setValue(aseguradoAfectado.rawValue);
                   
                    var params = {
                            'params.cdperson' : obtieneCDPerson,
                            'params.cdramo' : Ext.getCmp('cmbRamos').getValue()
                    };
                    
                    cargaStorePaginadoLocal(storeListadoPoliza, _URL_CONSULTA_LISTADO_POLIZA, 'listaPoliza', params, function(options, success, response){
                        if(success){
                            var jsonResponse = Ext.decode(response.responseText);
                            if(jsonResponse.listaPoliza == null) {
                                Ext.Msg.show({
                                    title: 'Aviso',
                                    msg: 'No existen p&oacute;lizas para el asegurado elegido.',
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.WARNING
                                });
                                Ext.getCmp('cmbAseguradoAfectado').setValue('');
                                modPolizasAltaTramite.hide();
                                return;
                            }
                            
                        }else{
                            Ext.Msg.show({
                                title: 'Aviso',
                                msg: 'Error al obtener los datos.',
                                buttons: Ext.Msg.OK,
                                icon: Ext.Msg.ERROR
                            });
                        }
                    });
                    
                    modPolizasAltaTramite.show();
                }
            }
    });
    
    var cmbBeneficiario= Ext.create('Ext.form.ComboBox',
	{
        id:'cmbBeneficiario',       name:'cmbBeneficiario',        	fieldLabel: 'Beneficiario',         queryMode:'remote',
        displayField: 'value',    	valueField: 'key',        		editable:true,					 	labelWidth : 250,
        width		 : 500,			forceSelection : true,          matchFieldWidth: false,     		queryParam: 'params.cdperson',
    	minChars  : 2, 				store : storeAsegurados2,    	triggerAction: 'all',				hideTrigger:true,	allowBlank:false,
    	listeners : {
			'select' : function(combo, record) {
				Ext.getCmp('idnombreBeneficiarioProv').setValue(cmbBeneficiario.rawValue);
			}
    	}
    });
    
    
    cmbProveedor = Ext.create('Ext.form.field.ComboBox',
    {
    	fieldLabel : 'Proveedor',		displayField : 'nombre',		name:'cmbProveedor',
    	id:'cmbProveedor',				labelWidth: 250,				width:500,					valueField   : 'cdpresta',
    	forceSelection : true,			matchFieldWidth: false,			queryMode :'remote',		queryParam: 'params.cdpresta',
    	minChars  : 2,					store : storeProveedor,			triggerAction: 'all',		hideTrigger:true,	allowBlank:false,
    	listeners : {
			'select' : function(combo, record) {
				if(this.getValue() =='0'){
					Ext.getCmp('idnombreBeneficiarioProv').setValue('');
					Ext.getCmp('idnombreBeneficiarioProv').show();
				}else{
					Ext.getCmp('idnombreBeneficiarioProv').setValue(cmbProveedor.rawValue);
					Ext.getCmp('idnombreBeneficiarioProv').hide();
					//Se realiza la validación para verificar si es pagado
					validarFacturaPagada(Ext.getCmp('cmbProveedor').getValue(), Ext.getCmp('txtNoFactura').getValue());
				}
				
				
			}
    	}
    });

    cmbTipoMoneda = Ext.create('Ext.form.ComboBox',
    {
        id:'cmbTipoMoneda',			   name:'cmbTipoMoneda',		        fieldLabel: 'Tipo moneda--->',				queryMode:'local',
        displayField: 'value',	   valueField: 'key',					editable:false,		allowBlank:false,
        labelWidth : 250,		   emptyText:'Seleccione...',			width		 : 500,						store: storeTipoMoneda, value:'001'
    });
    
    // PANEL PARA EL PAGO POR REEMBOLSO
    var panelModificacionInsercion= Ext.create('Ext.form.Panel',{
        border  : 0
        ,bodyStyle:'padding:5px;'
        ,items :
        [   
            {	
            	xtype      : 'textfield',			name       : 'noFactInterno',		    	fieldLabel : 'No. Factura',	    		labelWidth: 170,	    		allowBlank:false	
            },
            {
            	name: 'fechaFactInterno',	    	fieldLabel: 'Fecha Factura',    	        xtype: 'datefield',		    	        format: 'd/m/Y',
            	editable: true,						allowBlank:false,			    			labelWidth : 170,			   	        value:new Date()
            },
            {
            	xtype: 'combo',		                name:'tipoServicioInterno',	                valueField: 'key',		                displayField: 'value',
                fieldLabel: 'Tipo de servicio',     store: storeTipoAtencion2,	                queryMode:'local',		                allowBlank:false,
                editable:false,		                width: 500,					                labelWidth : 170,		                emptyText:'Seleccione...',
                value:'8'
            },
            {
            	xtype       : 'combo',            	name        :'proveedorInterno',           	fieldLabel  : 'Proveedor',            	displayField: 'nombre',
            	valueField  : 'cdpresta',          	allowBlank  : false,		            	minChars  : 2,			            	width       : 500,
                forceSelection : true,              matchFieldWidth: false,		                queryMode   :'remote',	                queryParam  : 'params.cdpresta',
                store       : storeProveedor,	    triggerAction  : 'all',		                labelWidth  : 170,		                emptyText   : 'Seleccione...',
                editable    : true,                hideTrigger:true, value:'8',
                listeners : {
					'select' : function(combo, record) {
						validarFacturaPagada(this.getValue(),panelModificacionInsercion.query('[name=noFactInterno]')[0].getValue());
					}
		    	}
            },
    		{
    			xtype       : 'combo',            	name:'cmbTipoMonedaInterna',		        valueField: 'key',				displayField: 'value',
    			fieldLabel: 'Tipo moneda',			store: storeTipoMonedaInterna,				queryMode:'local',				allowBlank:false,
    			editable:false,		                width: 500,					                labelWidth : 170,		        emptyText:'Seleccione...',
    			listeners : {
    				change:function(e){
    					if(e.getValue()!='001')
						{
    						panelModificacionInsercion.query('numberfield[name=importeInternoMext]')[0].show();
    				 		panelModificacionInsercion.query('numberfield[name=tasaCambioMext]')[0].show();
    				 		panelModificacionInsercion.query('numberfield[name=importeInterno]')[0].hide();
						
						}else{
							panelModificacionInsercion.query('numberfield[name=importeInternoMext]')[0].hide();
					 		panelModificacionInsercion.query('numberfield[name=tasaCambioMext]')[0].hide();
					 		panelModificacionInsercion.query('numberfield[name=importeInterno]')[0].show();
						}
    	    		}
    	        }
		    },
		    {
            	xtype      : 'numberfield',	        name       : 'importeInterno',		    	fieldLabel : 'Importe',			    	allowBlank:false,
		    	allowDecimals :true,		    	decimalSeparator :'.',				    	minValue: 0,			                width:500,                labelWidth: 170
    		},
    		{   
            	xtype      : 'numberfield',	        name       : 'importeInternoMext',		    	fieldLabel : 'Importe',			    	allowBlank:false,
		    	allowDecimals :true,		    	decimalSeparator :'.',				    	minValue: 0,			                width:500,
		    	labelWidth: 170,					value:'0',
		    	listeners:{
					change:function(field,value)
					{
						try
						{
							var importeTotal= panelModificacionInsercion.query('numberfield[name=importeInternoMext]')[0].getValue() * panelModificacionInsercion.query('numberfield[name=tasaCambioMext]')[0].getValue();
							panelModificacionInsercion.query('numberfield[name=importeInterno]')[0].setValue(importeTotal);
						}catch(e){}
					}
				}
    		},
    		{   
            	xtype      : 'numberfield',	        name       : 'tasaCambioMext',		    	fieldLabel : 'Tasa de cambio',		allowBlank:false,
		    	allowDecimals :true,		    	decimalSeparator :'.',				    	minValue: 0, 		                width:500,
		    	labelWidth: 170, 					value:'0',
		    	listeners:{
					change:function(field,value)
					{
						try
						{
							var importeTotal= panelModificacionInsercion.query('numberfield[name=importeInternoMext]')[0].getValue() * panelModificacionInsercion.query('numberfield[name=tasaCambioMext]')[0].getValue();
							panelModificacionInsercion.query('numberfield[name=importeInterno]')[0].setValue(importeTotal);
						}catch(e){}
					}
				}
    		}
        ]
    });
    
    /*PANTALLA EMERGENTE PARA LA INSERCION Y MODIFICACION DE LOS DATOS DEL GRID PARA EL PAGO POR REEMBOLSO*/
    var ventanaGrid= Ext.create('Ext.window.Window', {
           title: 'NUEVAS FACTURAS',
           closeAction: 'hide',
           modal: true,
           resizable: false,
           height: 260,
           items:[panelModificacionInsercion],
           buttons:[{
                  text: 'Aceptar',
                  icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
                  handler: function() {
                        if (panelModificacionInsercion.form.isValid()) {
                        	
                        	var datos=panelModificacionInsercion.form.getValues();
                        	var pptimport;
                        	var pptimporta;
                        	var tasaCambio;
                        	
                        	if(datos.cmbTipoMonedaInterna!='001')
                    		{
                        		pptimport= datos.importeInterno;
                    			pptimporta = datos.importeInternoMext;
                    			tasaCambio = datos.tasaCambioMext;
                    		}else{
                    			pptimport= datos.importeInterno;
                    			pptimporta = 0;
                    			tasaCambio = 0;
                    		}
                        	
                        	var rec = new modelFactCtrl({
        	 				  	noFactura: datos.noFactInterno,
        					 	fechaFactura: datos.fechaFactInterno,
        					 	tipoServicio: datos.tipoServicioInterno,
        					 	proveedor: datos.proveedorInterno,
        					 	importe: pptimport,
        					 	tipoMoneda: datos.cmbTipoMonedaInterna,
        					 	proveedorName: panelModificacionInsercion.query('combo[name=proveedorInterno]')[0].rawValue,
        					 	tipoServicioName: panelModificacionInsercion.query('combo[name=tipoServicioInterno]')[0].rawValue,
        					 	tipoMonedaName: panelModificacionInsercion.query('combo[name=cmbTipoMonedaInterna]')[0].rawValue,
        					 	tasaCambio: tasaCambio,
        					 	importeFactura:pptimporta 
		        	 		});
                        	
                        	storeFactCtrl.add(rec);
                        	ventanaGrid.close();
                        	panelModificacionInsercion.getForm().reset();
                        } else {
                            Ext.Msg.show({
                                   title: 'Aviso',
                                   msg: 'Complete la informaci&oacute;n requerida',
                                   buttons: Ext.Msg.OK,
                                   icon: Ext.Msg.WARNING
                               });
                        }
                    }
              },
            {
                  text: 'Cancelar',
                  icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
                  handler: function() {
                      panelModificacionInsercion.getForm().reset();
                      ventanaGrid.close();
                  }
            }
           ]
           });
    
    /*////////////////////////////////////////////////////////////////
    ////////////////   DECLARACION DE EDITOR DE INCISOS  ////////////
    ///////////////////////////////////////////////////////////////*/
    Ext.define('EditorIncisos', {
 		extend: 'Ext.grid.Panel',
	 	requires: [
		 	'Ext.selection.CellModel',
		 	'Ext.grid.*',
		 	'Ext.data.*',
		 	'Ext.util.*',
		 	'Ext.form.*'
	 	],
 		xtype: 'cell-editing',
		id:'editorIncisos',
 		title: 'Alta de facturas',
 		frame: false,

	 	initComponent: function(){
	 		this.cellEditing = new Ext.grid.plugin.CellEditing({
	 		clicksToEdit: 1
	 		});

	 			Ext.apply(this, {
	 			width: 750,
	 			height: 200,
	 			plugins: [this.cellEditing],
	 			store: storeFactCtrl,
	 			columns: 
	 			[
				 	{	
				 		header: 'No. de Factura',			dataIndex: 'noFactura',			flex:2
				 	},
				 	{
				 		header: 'Fecha de Factura',			dataIndex: 'fechaFactura',		flex:2//,			 	renderer: Ext.util.Format.dateRenderer('d M Y')
				 		
				 	},
				 	{
					 	header: 'Tipo de Servicio',			dataIndex: 'tipoServicioName',	 	flex:2	
				 	},
				 	{
					 	header: 'Proveedor',				dataIndex: 'proveedorName',			flex:2
				 	},
				 	{
					 	header: 'Importe Factura', 				dataIndex: 'importeFactura',		 	flex:2,				renderer: Ext.util.Format.usMoney
				 	},
				 	{
					 	header: 'Importe MXN', 					dataIndex: 'importe',		 	flex:2,				renderer: Ext.util.Format.usMoney
				 	}
				 	,
				 	{
					 	header: 'Moneda', 				dataIndex: 'tipoMonedaName',	flex:2
				 	},
				 	{
					 	header: 'Tasa cambio', 				dataIndex: 'tasaCambio',	flex:2
				 	},
				 	{
					 	xtype: 'actioncolumn',
					 	width: 30,
					 	sortable: false,
					 	menuDisabled: true,
					 	items: [{
					 		icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
					 		tooltip: 'Quitar inciso',
					 		scope: this,
					 		handler: this.onRemoveClick
				 		}]
				 	}
		 		],
		 		selModel: {
			 		selType: 'cellmodel'
			 	},
		 		tbar: [{
				 	icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/add.png',
				 	text: 'Agregar Factura',
				 	scope: this,
				 	handler: this.onAddClick
		 		}]
		 	});
 			this.callParent();
	 	},
	 	onAddClick: function(btn, e){
	 		panelModificacionInsercion.query('combo[name=cmbTipoMonedaInterna]')[0].setValue('001');
	 		panelModificacionInsercion.query('combo[name=tipoServicioInterno]')[0].hide();
	 		ventanaGrid.animateTarget=btn;
	 		ventanaGrid.show();
		   },
		   
	 	onRemoveClick: function(grid, rowIndex){
	 		var record=this.getStore().getAt(rowIndex);
	 		this.getStore().removeAt(rowIndex);
	 	}
 	});
    gridIncisos=new EditorIncisos();
    mesConStoreUniAdmin=[];
    
    
    /* PANEL PARA EL PAGO DIRECTO */
    var panelListadoAsegurado= Ext.create('Ext.form.Panel',{
        border  : 0
        ,startCollapsed : true
        ,bodyStyle:'padding:5px;'
        ,items :
        [   			
            {
                xtype      : 'datefield',
                name       : 'dtfechaOcurrencias',
                fieldLabel : 'Fecha ocurrencia',
                allowBlank: false,
                maxValue   :  new Date(),
                format		: 'd/m/Y'
            },{
            	xtype: 'combo',
            	name:'cmbAseguradoAfect',
            	fieldLabel : 'Asegurado',
        	    displayField : 'value',
        	    valueField   : 'key',
        	    allowBlank: false,
        	    width:500,
        	    minChars  : 2,
        	    forceSelection : true,
        	    matchFieldWidth: false,
        	    queryMode :'remote',
        	    queryParam: 'params.cdperson',
        	    store : storeAsegurados,
        	    triggerAction: 'all',
        	    hideTrigger:true,
        	    listeners : {
        	    	
        	        'select' : function(combo, record) {
        	        	
        	        	var params = {'params.cdperson' : this.getValue(),
        	        				  'params.cdramo' : Ext.getCmp('cmbRamos').getValue()};
                        
                        cargaStorePaginadoLocal(storeListadoPoliza, _URL_CONSULTA_LISTADO_POLIZA, 'listaPoliza', params, function(options, success, response){
                            if(success){
                                var jsonResponse = Ext.decode(response.responseText);
                                if(jsonResponse.listaPoliza == null) {
                                    Ext.Msg.show({
                                        title: 'Aviso',
                                        msg: 'No existen p&oacute;lizas para el asegurado elegido.',
                                        buttons: Ext.Msg.OK,
                                        icon: Ext.Msg.WARNING
                                    });
                                    
                                    combo.clearValue();
                                    modPolizasAltaTramite.hide();
                                    return;
                                }
                                
                            }else{
                                Ext.Msg.show({
                                    title: 'Aviso',
                                    msg: 'Error al obtener los datos.',
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.ERROR
                                });
                            }
                        });
                        modPolizasAltaTramite.show();
                    }
        	    }
            }
        ]
    });

   /*PANTALLA EMERGENTE PARA EL PAGO DIRECTO */
   var ventanaAgregarAsegurado= Ext.create('Ext.window.Window', {
        title: 'Asegurados',
        //height: 200,
        closeAction: 'hide',
        modal: true, 
        resizable: false,
        items:[panelListadoAsegurado],
        buttonAlign : 'center',
        buttons:[
            {
                text: 'Aceptar',
                icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
                handler: function() {
                    if (panelListadoAsegurado.form.isValid()) {
                    	
                        var datos=panelListadoAsegurado.form.getValues();
                        var rec = new modelListAsegPagDirecto({
                        	modUnieco: Ext.getCmp('idUnieco').getValue(),
                        	modEstado: Ext.getCmp('idEstado').getValue(),
                        	modRamo: Ext.getCmp('idcdRamo').getValue(),
                        	modNmsituac: Ext.getCmp('idNmSituac').getValue(),
                        	modPolizaAfectada: Ext.getCmp('polizaAfectada').getValue(),
                        	modNmsolici: Ext.getCmp('idNmsolici').getValue(),
                        	modNmsuplem: Ext.getCmp('idNmsuplem').getValue(),
                        	modCdtipsit: Ext.getCmp('idCdtipsit').getValue(),
                        	modNmautserv: "",
                        	modFechaOcurrencia: datos.dtfechaOcurrencias,
                        	modCdperson: datos.cmbAseguradoAfect,
                        	modCdpersondesc: panelListadoAsegurado.query('combo[name=cmbAseguradoAfect]')[0].rawValue,
                        	modnumPoliza:Ext.getCmp('idNumPolizaInt').getValue()
                        });
                        storeListAsegPagDirecto.add(rec);
                        limpiarRegistros();
                        panelListadoAsegurado.getForm().reset();
                        ventanaAgregarAsegurado.close();
                    } else {
                        Ext.Msg.show({
                            title: 'Aviso',
                            msg: 'Complete la informaci&oacute;n requerida',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.WARNING
                        });
                    }
                }
            },
            {
                text: 'Cancelar',
                icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
                handler: function(){
                    panelListadoAsegurado.getForm().reset();
                    ventanaAgregarAsegurado.close();
                }
            }
        ]
    });
    
    
    Ext.define('EditorAsegPagDirecto', {
        extend: 'Ext.grid.Panel',
        requires: [
            //'Ext.selection.CellModel',
            'Ext.grid.*',
            'Ext.data.*',
            'Ext.util.*',
            'Ext.form.*'
        ],
       //xtype: 'cell-editing',
        id:'EditorAsegPagDirecto',
        title: 'Asegurados',
        frame: false,

        initComponent: function(){
            /*this.cellEditing = new Ext.grid.plugin.CellEditing({
            clicksToEdit: 1
            });*/

                Ext.apply(this, {
                width: 750,
                height: 200,
                //plugins: [this.cellEditing],
                store: storeListAsegPagDirecto,
                columns: 
                [
                    {	
                        header: 'Fecha ocurrencia',			dataIndex: 'modFechaOcurrencia',			width:150
                    },
                    {
                        header: 'Asegurado afectado',		dataIndex: 'modCdpersondesc',				width:350
                    },
                    {
                        header: 'N&uacute;mero P&oacute;liza',		dataIndex: 'modnumPoliza',				width:200
                    },
                    {
                        xtype: 'actioncolumn',				width: 30,					 	sortable: false,	 	menuDisabled: true,
                        items: [{
                            icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
                            tooltip: 'Quitar asegurado',
                            scope: this,
                            handler: this.onRemoveClick
                        }]
                    }
                ],
                /*selModel: {
                    selType: 'cellmodel'
                },*/
                tbar: [{
                    icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/add.png',
                    text: 'Agregar Asegurado',
                    scope: this,
                    handler: this.onAddClick
                }]
            });
            this.callParent();
        },
        onAddClick: function(btn, e){
        	ventanaAgregarAsegurado.animateTarget=btn;
            ventanaAgregarAsegurado.show();
           },
        onRemoveClick: function(grid, rowIndex){
            var record=this.getStore().getAt(rowIndex);
            this.getStore().removeAt(rowIndex);
        }
    });

    gridAsegPagDirecto=new EditorAsegPagDirecto();
    
    /* PANEL PARA LA BUSQUEDA DE LA INFORMACIÓN DEL ASEGURADO PARA LA BUSQUEDA DE LAS POLIZAS */
    gridPolizasAltaTramite= Ext.create('Ext.grid.Panel',
	{
	    id            : 'polizaGridAltaTramite',
	    store         : storeListadoPoliza,
	    //collapsible   : true,
	    //titleCollapse : true,
	    selType	      : 'checkboxmodel',
	    width         : 700,
	    height		  : 200,
	    columns       :
	    [
	        {
	             header     : 'N&uacute;mero de P&oacute;liza',		dataIndex : 'numPoliza',		width	 	: 200
	        },
	        /*{
	             header     : 'N&uacute;mero de P&oacute;liza',		dataIndex : 'nmpoliza',		width	 	: 150
	        },*/
	        {
	        	 header     : 'Estatus p&oacute;liza ',							dataIndex : 'dsestatus',	width	 	: 100
	        },
	        {
	             header     : 'Vigencia p&oacute;liza <br/> Fecha inicio \t\t  |  \t\t Fecha fin  ',						dataIndex : 'vigenciaPoliza',		width	    : 200
	        },
	        {
	             header     : 'Fecha alta <br/> asegurado',		dataIndex : 'faltaAsegurado',		width	    : 100
	        },
	        {
	             header     : 'Fecha cancelaci&oacute;n <br/> asegurado',						dataIndex : 'fcancelacionAfiliado',		width	    : 150
	        },
	        {
	             header     : 'Estatus<br/> asegurado',						dataIndex : 'desEstatusCliente',		width	    : 100
	        },
	        {
	             header     : 'Producto',							dataIndex : 'dsramo',		width       : 150
	        },
	        {
	             header     : 'Sucursal',							dataIndex : 'dssucursal',	width       : 150
	        },
	        {
	             header     : 'Estado',								dataIndex : 'estado',		width	    : 100
	        },
	        {
	             header     : 'N&uacute;mero de Situaci&oacute;n',	dataIndex : 'nmsituac',		width	    : 150
	         }
		],
	    bbar :
	    {
	        displayInfo : true,
	        store       : storeListadoPoliza,
	        xtype       : 'pagingtoolbar'
	    },
	    listeners: {
                itemclick: function(dv, record, item, index, e){
                	//1.- Validamos que el asegurado este vigente
                	if(record.get('desEstatusCliente')=="Vigente")
            		{
                		var valorFechaOcurrencia;
                		if(Ext.getCmp('cmbTipoPago').getValue() == _TIPO_PAGO_DIRECTO)
            			{
                			var valorFechaOcu = panelListadoAsegurado.query('datefield[name=dtfechaOcurrencias]')[0].rawValue;
                			valorFechaOcurrencia = new Date(valorFechaOcu.substring(6,10)+"/"+valorFechaOcu.substring(3,5)+"/"+valorFechaOcu.substring(0,2));
            			}else{
            				valorFechaOcurrencia = Ext.getCmp('dtFechaOcurrencia').getValue();
            			}
                		
                		var valorFechaInicial = new Date(record.get('feinicio').substring(6,10)+"/"+record.get('feinicio').substring(3,5)+"/"+record.get('feinicio').substring(0,2));
                		var valorFechaFinal =   new Date(record.get('fefinal').substring(6,10)+"/"+record.get('fefinal').substring(3,5)+"/"+record.get('fefinal').substring(0,2));
                		var valorFechaAltaAsegurado = new Date(record.get('faltaAsegurado').substring(6,10)+"/"+record.get('faltaAsegurado').substring(3,5)+"/"+record.get('faltaAsegurado').substring(0,2));
                		
                		if( (valorFechaOcurrencia <= valorFechaFinal) && (valorFechaOcurrencia >= valorFechaInicial)){
                    		if( valorFechaOcurrencia >= valorFechaAltaAsegurado )
                			{
                    				//cumple la condición la fecha de ocurrencia es menor igual a la fecha de alta de tramite
	                    			Ext.getCmp('idUnieco').setValue(record.get('cdunieco'));
	            					Ext.getCmp('idEstado').setValue(record.get('estado'));
	            					Ext.getCmp('idcdRamo').setValue(record.get('cdramo'));
	            					Ext.getCmp('idNmSituac').setValue(record.get('nmsituac'));
	            					Ext.getCmp('polizaAfectada').setValue(record.get('nmpoliza'));
	            					Ext.getCmp('idNmsolici').setValue(record.get('nmsolici'));
	            					Ext.getCmp('idNmsuplem').setValue(record.get('nmsuplem'));
	            					Ext.getCmp('idCdtipsit').setValue(record.get('cdtipsit'));
	            					Ext.getCmp('idNumPolizaInt').setValue(record.get('numPoliza'));
	            					modPolizasAltaTramite.hide();
                			}else{
                				// No se cumple la condición la fecha de ocurrencia es mayor a la fecha de alta de tramite
                				Ext.Msg.show({
        	                    	title:'Error',
        	                    	msg: 'La fecha de ocurrencia es mayor a la fecha de alta del asegurado',
        	                    	buttons: Ext.Msg.OK,
        	                    	icon: Ext.Msg.ERROR
        	                	});
                				modPolizasAltaTramite.hide();
                    			limpiarRegistros();
                    			if(Ext.getCmp('cmbTipoPago').getValue() == _TIPO_PAGO_DIRECTO)
                    			{
                    				panelListadoAsegurado.query('combo[name=cmbAseguradoAfect]')[0].setValue('');
                    			}else{
                    				Ext.getCmp('cmbAseguradoAfectado').setValue('');
                    			}
                			}
                		}else{
                			// La fecha de ocurrencia no se encuentra en el rango de la poliza vigente
                			Ext.Msg.show({
    	                    	title:'Error',
    	                    	msg: 'La fecha de ocurrencia no se encuentra en el rango de la p&oacute;liza vigente',
    	                    	buttons: Ext.Msg.OK,
    	                    	icon: Ext.Msg.ERROR
    	                	});
                			modPolizasAltaTramite.hide();
                			limpiarRegistros();
                			if(Ext.getCmp('cmbTipoPago').getValue() == _TIPO_PAGO_DIRECTO)
                			{
                				panelListadoAsegurado.query('combo[name=cmbAseguradoAfect]')[0].setValue('');
                			}else{
                				Ext.getCmp('cmbAseguradoAfectado').setValue('');
                			}
                		}
            		}else{
            			// El asegurado no se encuentra vigente
            			Ext.Msg.show({
	                    	title:'Error',
	                    	msg: 'El asegurado de la p&oacute;liza seleccionado no se encuentra vigente',
	                    	buttons: Ext.Msg.OK,
	                    	icon: Ext.Msg.ERROR
	                	});
            			modPolizasAltaTramite.hide();
            			limpiarRegistros();
            			if(Ext.getCmp('cmbTipoPago').getValue() == _TIPO_PAGO_DIRECTO)
            			{
            				panelListadoAsegurado.query('combo[name=cmbAseguradoAfect]')[0].setValue('');
            			}else{
            				Ext.getCmp('cmbAseguradoAfectado').setValue('');
            			}
            		}
                }
            }

	});
    gridPolizasAltaTramite.store.sort([
        {
        	property    : 'nmpoliza',			direction   : 'DESC'
        }
    ]);
    
    
    var modPolizasAltaTramite = Ext.create('Ext.window.Window',
	{
	    title        : 'Listado de P&oacute;liza'
	    ,modal       : true
	    ,resizable   : false
	    ,buttonAlign : 'center'
	    ,closable    : false
	    ,width		 : 710
	    ,minHeight 	 : 100 
	    ,maxheight   : 400
	    ,items       :
	        [
	            gridPolizasAltaTramite
	        ]
	});
    
    var panelInicialPral= Ext.create('Ext.form.Panel',
    	    {
    	        border    : 0
    	        ,title: 'Alta de Tr&aacute;mite'
    	        ,id : 'panelInicialPral'
    	        ,renderTo : 'div_clau'
	        	,bodyPadding: 10
	            ,width: 800
    	        ,items    :
    	        [
					{
					    xtype       : 'textfield',			fieldLabel : 'Unieco'				,id       : 'idUnieco',				name       : 'cdunieco'
					    ,labelWidth: 170,	hidden:true
					},
					{
					    xtype       : 'textfield',			fieldLabel : 'estado'				,id       : 'idEstado',				name       : 'estado'			
					    ,labelWidth: 170,	hidden:true
					},
					{
					    xtype       : 'textfield',			fieldLabel : 'Ramo'				,id       : 'idcdRamo',					name       : 'cdramo'
					    ,labelWidth: 170,	hidden:true
					},	 			
					{
					    xtype       : 'textfield',			fieldLabel : 'nmsituac'				,id       : 'idNmSituac',			name       : 'nmsituac'
					    ,labelWidth: 170,	hidden:true
					},
					{
					   xtype       : 'textfield',			fieldLabel : 'P&oacute;liza afectada'				,id       : 'polizaAfectada'
					   ,labelWidth: 170,				name:'nmpoliza',	readOnly   : true,	hidden:true
					},
					{
					    xtype       : 'textfield',			fieldLabel : 'nmsolici'				,id       : 'idNmsolici',			name       : 'nmsolici'
					    ,labelWidth: 170,	hidden:true
					},
					{
					    xtype       : 'textfield',			fieldLabel : 'nmsuplem'				,id       : 'idNmsuplem',			name       : 'nmsuplem'
					    ,labelWidth: 170,	hidden:true
					},
					{
					    xtype       : 'textfield',			fieldLabel : 'cdtipsit'				,id       : 'idCdtipsit',			name       : 'cdtipsit'
					    ,labelWidth: 170,	hidden:true
					},
					{
					    xtype       : 'textfield',			fieldLabel : 'numPolizaInt'			,id       : 'idNumPolizaInt',	name       : 'numPolizaInt'
					    ,labelWidth: 170,	hidden:true
					},
					{
					    xtype       : 'textfield',			fieldLabel : 'Asegurado'			,id       : 'idnombreAsegurado',	name       : 'idnombreAsegurado'
					    ,labelWidth: 170,	hidden:true
					},
					{
					    xtype       : 'textfield',			fieldLabel : 'NumTramite'			,id       : 'idNumTramite',	name       : 'idNumTramite'
					    ,labelWidth: 170,	hidden:true
					},
    	            {
            			id:'txtContraRecibo'
		                ,xtype      : 'textfield'
		            	,fieldLabel : 'Contra recibo'
		            	,readOnly   : true
		            	,labelWidth : 250
		            	,width		 : 500
		            	,name       : 'txtContraRecibo'
		            },
		            {
		                id: 'txtEstado',
		                name: 'txtEstado',
		                xtype: 'textfield',
		                fieldLabel: 'Estado',
		                readOnly   : true,
		               labelWidth: 250,
		               width		 : 500,
		               value:'PENDIENTE'
		            },
		            cmbRamos
	            	,
		            cmbOficinaReceptora
		            ,
		            cmbOficinaEmisora
	            	,
	            	{
		            	id:'dtFechaRecepcion'
		                ,xtype      : 'datefield'
		            	,fieldLabel : 'Fecha recepci&oacute;n'
		            	,labelWidth : 250
		            	,width		 : 500
		            	,name       : 'dtFechaRecepcion'
	            		,value		: new Date()
		            	,format		: 'd/m/Y'
		            	,readOnly   : true
		            	,allowBlank : false
		            }
	            	,
	            	comboTipoAte,
	        		tipoPago,
        			{
                        id: 'dtFechaOcurrencia',
                        name: 'dtFechaOcurrencia',
                        fieldLabel: 'Fecha ocurrencia',
                        xtype: 'datefield',
                        format: 'd/m/Y',
                        editable: true,
                        labelWidth : 250,
                        width		 : 500,
                        maxValue   :  new Date(),
                        allowBlank : false
                    },
            		aseguradoAfectado
	            	,
	            	cmbBeneficiario
	            	,
	            	{
		            	id:'txtNoFactura'
		                ,xtype      : 'textfield'
		            	,fieldLabel : 'No. Factura'
		            	,labelWidth : 250
		            	,width		: 500
		            	,name       : 'txtNoFactura'
		            	,allowBlank	: false
		            }
		            ,
	            	cmbProveedor
	        	    ,
	        	    {
					    xtype       : 'textfield',			fieldLabel : 'Nombre Proveedor'			,id       : 'idnombreBeneficiarioProv',	name       : 'idnombreBeneficiarioProv'
					    ,labelWidth : 250,width		: 500,	hidden:true,
					    listeners:{
							afterrender: function(){
								this.hide();
							}
						}
					},
                	{
		            	id:'txtImporte'
		                ,xtype      : 'numberfield'
		            	,fieldLabel : 'Importe'
		            	,labelWidth : 250
		            	,width		: 500
		            	,name       : 'txtImporte'
	            		,allowDecimals :true
	    		    	,decimalSeparator :'.'
	    		    	,minValue: 0
	    		    	,allowBlank	: false
		            },
		            cmbTipoMoneda
		            ,
		            {
                        id: 'dtFechaFactura',
                        name: 'dtFechaFactura',
                        fieldLabel: 'Fecha factura',
                        xtype: 'datefield',
                        format: 'd/m/Y',
                        editable: true,
                        labelWidth : 250,
                        width		 : 500,
                        value:new Date(),
                        allowBlank	: false
                    },
                    gridIncisos
                    ,
                    gridAsegPagDirecto
    	        ],
    	        buttonAlign:'center',
    	        buttons: [{
    	            id:'botonCotizar',
    	            icon:_CONTEXT+'/resources/fam3icons/icons/calculator.png',
    	            text: 'Generar Tr&aacute;mite',
            		handler: function() {
            			
            			var form = this.up('form').getForm();
            			if (form.isValid())
    	                {
            				//aqui realizamos el llamado al guardado
            				var respuesta=true;
            				//Llenamos los campos dependiendo si es pago por reembolso
            				if( Ext.getCmp('cmbTipoPago').getValue() == _TIPO_PAGO_DIRECTO) // Pago Directo
        					{
            					Ext.getCmp('cmbAseguradoAfectado').setValue("");
            					Ext.getCmp('dtFechaOcurrencia').setValue("");
            					Ext.getCmp('cmbBeneficiario').setValue("");
            					var obtener = [];
                				storeListAsegPagDirecto.each(function(record) {
                					obtener.push(record.data);
                                });
                				
                				if(obtener.length <= 0){
                					Ext.Msg.show({
    						                title:'Error',
    						                msg: 'Se requiere ingresar al menos un asegurado',
    						                buttons: Ext.Msg.OK,
    						                icon: Ext.Msg.ERROR
    						            });
    						            return false;
                				}else{
	                				if(obtener.length == 1){
	                					Ext.getCmp('idUnieco').setValue(obtener[0].modUnieco);
	                					Ext.getCmp('idEstado').setValue(obtener[0].modEstado);
	                					Ext.getCmp('idcdRamo').setValue(obtener[0].modRamo);
	                					Ext.getCmp('idNmSituac').setValue(obtener[0].modNmsituac);
	                					Ext.getCmp('polizaAfectada').setValue(obtener[0].modPolizaAfectada);
	                					Ext.getCmp('idNmsolici').setValue(obtener[0].modNmsolici);
	                					Ext.getCmp('idNmsuplem').setValue(obtener[0].modNmsuplem);
	                					Ext.getCmp('idCdtipsit').setValue(obtener[0].modCdtipsit);
	                					
	                					storeAsegurados.load({
	        								params:{
	        				                    'params.cdperson':obtener[0].modCdperson
	        								}
	        			    			});
	                					
	                					Ext.getCmp('cmbAseguradoAfectado').setValue(obtener[0].modCdperson);
	                					Ext.getCmp('dtFechaOcurrencia').setValue(obtener[0].modFechaOcurrencia);
	                				}                					
                				}

        					}else{
        						
        						Ext.getCmp('cmbProveedor').setValue("");
        						Ext.getCmp('txtNoFactura').setValue("");
        						Ext.getCmp('txtImporte').setValue("");
        						Ext.getCmp('cmbTipoMoneda').setValue("");
        						Ext.getCmp('dtFechaFactura').setValue("");
        						
        						var obtener = [];
        						storeFactCtrl.each(function(record) {
        							obtener.push(record.data);
                                });
        						
        						if(obtener.length <= 0){
                					Ext.Msg.show({
    						                title:'Error',
    						                msg: 'Se requiere ingresar al menos una factura',
    						                buttons: Ext.Msg.OK,
    						                icon: Ext.Msg.ERROR
    						            });
    						            return false;
                				}else{
	                				if(obtener.length == 1){
	                					Ext.getCmp('cmbProveedor').setValue(obtener[0].proveedor);
	            	    				Ext.getCmp('txtNoFactura').setValue(obtener[0].noFactura);
	            	    				Ext.getCmp('txtImporte').setValue(obtener[0].importe);
	            	    				Ext.getCmp('dtFechaFactura').setValue(obtener[0].fechaFactura);
	        	    				}
                				}
                				
        					}
            				
            				var submitValues={};
            				var formulario=panelInicialPral.form.getValues();
            				submitValues['params']=formulario;
            				
            				var datosTablas = [];
            				
            				// si es pago directo
            				if( Ext.getCmp('cmbTipoPago').getValue() == _TIPO_PAGO_DIRECTO)
        					{
            					storeListAsegPagDirecto.each(function(record,index){
            						datosTablas.push({
    	            					cdperson:record.get('modCdperson'),
    	            					cdtipsit:record.get('modCdtipsit'),
    	            					estado:record.get('modEstado'),
    	            					fechaOcurrencia:record.get('modFechaOcurrencia'),
    	            					nmsituac:record.get('modNmsituac'),
    	            					nmsolici:record.get('modNmsolici'),
    	            					nmsuplem:record.get('modNmsuplem'),
    	            					polizaAfectada:record.get('modPolizaAfectada'),
    	            					ramo:record.get('modRamo'),
    	            					unieco:record.get('modUnieco')
    	            				});
                				});
        					}else{
        						storeFactCtrl.each(function(record,index){
        							datosTablas.push({
    	            					nfactura:record.get('noFactura'),
    	            					ffactura:record.get('fechaFactura'),
    	            					cdtipser:record.get('tipoServicio'),
    	            					cdpresta:record.get('proveedor'),
    	            					ptimport:record.get('importe'),
    	            					cdmoneda:record.get('tipoMoneda'),
    	            					tasacamb:record.get('tasaCambio'),
    	            					ptimporta:record.get('importeFactura')
    	            				});
                				});
    						}
            				
            				submitValues['datosTablas']=datosTablas;
            				panelInicialPral.setLoading(true);
            				Ext.Ajax.request(
    						{
    						    url: _URL_GUARDA_ALTA_TRAMITE,
    						    jsonData:Ext.encode(submitValues), // convierte a estructura JSON
    						    
    						    success:function(response,opts){
    						    	panelInicialPral.setLoading(false);
    						        var jsonResp = Ext.decode(response.responseText);
    						        if(jsonResp.success==true){
    						        	var etiqueta;
    						        	var mensaje;
    						        	if(valorAction == null)
    						        	{
    						        		etiqueta = "Guardado";
    						        		mensaje = "Se gener&oacute; el n&uacute;mero de tr&aacute;mite "+ Ext.decode(response.responseText).msgResult; 
    						        	}else{
    						        		etiqueta = "Modificaci&oacute;n";
    						        		mensaje = "Se modific&oacute; el n&uacute;mero de tr&aacute;mite "+ valorAction.ntramite;
    						        	}
    						            mensajeCorrecto(etiqueta,mensaje,function()
				                		{
				                		    Ext.create('Ext.form.Panel').submit(
				                		    {
				                		        url             : _p12_urlMesaControl
				                		        ,standardSubmit : true
				                		        ,params         :
				                		        {
				                		            'smap1.gridTitle'      : 'Siniestros en espera'
				                		            ,'smap2.pv_cdtiptra_i' : 16
				                		        }
				                		    });
				                		});
    						            panelInicialPral.getForm().reset();
    						            storeFactCtrl.removeAll();
    						            
    						            windowLoader.close();
    						        }
    						        else{
    						            Ext.Msg.show({
    						                title:'Error',
    						                msg: 'Error en el guardado del alta de tr&aacute;mite',
    						                buttons: Ext.Msg.OK,
    						                icon: Ext.Msg.ERROR
    						            });
    						            
    						            respuesta= false;
    						        }
    						    },
    						    failure:function(response,opts)
    						    {
    						        panelInicialPrincipal.setLoading(false);
    						        Ext.Msg.show({
    						            title:'Error',
    						            msg: 'Error de comunicaci&oacute;n',
    						            buttons: Ext.Msg.OK,
    						            icon: Ext.Msg.ERROR
    						        });
    						    }
    						});
    	                }
            			else
        				{
            				Ext.Msg.show({
    	                    	title:'Datos incompletos',
    	                    	msg: 'Favor de introducir todos los campos requeridos',
    	                    	buttons: Ext.Msg.OK,
    	                    	icon: Ext.Msg.WARNING
    	                	});
        				}
    	            }
    	        },
    	        {
    	            text:'Cancelar',
    	            handler:function()
    	            {
    	            	windowLoader.close();
    	            }
    	        }
    	    ]
    	    });
    
    if(valorAction == null)
	{
    	Ext.getCmp('cmbTipoPago').setValue(_TIPO_PAGO_DIRECTO);
    	oficinaEmisora.load();
    	Ext.getCmp('cmbOficEmisora').setValue('1000');
    	storeRamos.load({
            params:{
            	'params.idPadre':Ext.getCmp('cmbOficEmisora').getValue()
            }
		});
    	
	}else{
			storeProveedor.load();
			Ext.Ajax.request(
			{
			    url     : _URL_CONSULTA_ALTA_TRAMITE
			    ,params:{
					'params.ntramite': valorAction.ntramite
                }
			    ,success : function (response)
			    {
			    	if(Ext.decode(response.responseText).listaMesaControl != null)
		    		{
			    		var json=Ext.decode(response.responseText).listaMesaControl[0];
			    		//ASIGNACION DE VALORES GENERALES PARA PAGO DIRECTO Y REEMBOLSO
			    		Ext.getCmp('idNumTramite').setValue(valorAction.ntramite);
			    		Ext.getCmp('txtEstado').setValue('PENDIENTE');
			    		Ext.getCmp('cmbOficReceptora').setValue(json.cdsucdocmc);
			    		oficinaEmisora.load();
			    		Ext.getCmp('cmbOficEmisora').setValue(json.cdsucadmmc);
			    		Ext.getCmp('dtFechaRecepcion').setValue(json.ferecepcmc);
			    		Ext.getCmp('txtContraRecibo').setValue(json.otvalor01mc);
			    		Ext.getCmp('cmbTipoAtencion').setValue(json.otvalor07mc);
			    		Ext.getCmp('cmbTipoPago').setValue(json.otvalor02mc);
			    		storeRamos.load({
		                    params:{
		                    	'params.idPadre':Ext.getCmp('cmbOficEmisora').getValue()
		                    }
						});
						if(json.otvalor20mc == null || json.otvalor20mc==''){
							Ext.getCmp('cmbRamos').setValue("2");
						}else{
							Ext.getCmp('cmbRamos').setValue(json.otvalor20mc);
						}
						
			    		//VALORES DE PAGO DIRECTO
			    		if(Ext.getCmp('cmbTipoPago').getValue() == _TIPO_PAGO_DIRECTO){
			    			Ext.getCmp('cmbProveedor').setValue(json.otvalor11mc);
			    			Ext.getCmp('txtNoFactura').setValue(json.otvalor08mc);
			    			Ext.getCmp('txtImporte').setValue(json.otvalor03mc);
			    			Ext.getCmp('dtFechaFactura').setValue(json.otvalor06mc);
			    			Ext.getCmp('idnombreBeneficiarioProv').setValue(json.otvalor15mc);
			    			if(json.otvalor11mc =='0'){
			    				Ext.getCmp('idnombreBeneficiarioProv').show();
			    			}else{
			    				Ext.getCmp('idnombreBeneficiarioProv').hide();
			    			}
			    		}else{
			    			//VALORES DE PAGO POR REEMBOLSO
			    			Ext.getCmp('idUnieco').setValue(json.cduniecomc);
			    			Ext.getCmp('idEstado').setValue(json.estadomc);
			    			Ext.getCmp('idcdRamo').setValue(json.cdramomc);
			    			//Ext.getCmp('idNmSituac').setValue(json.);
			    			Ext.getCmp('polizaAfectada').setValue(json.nmpolizamc);
			    			Ext.getCmp('idNmsolici').setValue(json.nmsolicimc);
			    			Ext.getCmp('idNmsuplem').setValue(json.nmsuplemmc);
			    			Ext.getCmp('idCdtipsit').setValue(json.cdtipsitmc);
			    			//Ext.getCmp('idNumPolizaInt').setValue(json.);
			    			
			    			Ext.getCmp('dtFechaOcurrencia').setValue(json.otvalor10mc);
			    			Ext.getCmp('idnombreBeneficiarioProv').setValue(json.otvalor15mc);
			    			Ext.getCmp('idnombreAsegurado').setValue(json.nombremc);
			    			
			    			storeAsegurados.load({
								params:{
				                    'params.cdperson':json.otvalor09mc
								}
			    			});
			    			
			    			Ext.getCmp('cmbAseguradoAfectado').setValue(json.otvalor09mc);
			    			
			    			storeAsegurados2.load({
								params:{
				                    'params.cdperson':json.otvalor04mc
								}
			    			});
			    			
			    			Ext.getCmp('cmbBeneficiario').setValue(json.otvalor04mc);
			    		}
			    		
			    		
			    		/*COMO SEGUNDO PUNTO ES OBTENER LA INFORMACION DE LOS GRIDS*/
			    		Ext.Ajax.request(
	    				{
	    				    url     : _URL_CONSULTA_GRID_ALTA_TRAMITE
	    				    ,params:{
	    						'params.ntramite': valorAction.ntramite
	    	                }
	    				    ,success : function (response)
	    				    {
	    	                    if(Ext.decode(response.responseText).listaAltaTramite != null)
	    			    		{
	    				    		var json=Ext.decode(response.responseText).listaAltaTramite;
	    				    		if(Ext.getCmp('cmbTipoPago').getValue() == _TIPO_PAGO_DIRECTO){
	    				    			// PAGO DIRECTO
	    				    			Ext.getCmp('cmbTipoMoneda').setValue(json[0].cdmoneda);
	    				    			for(var i = 0; i < json.length; i++){
		    				    			var rec = new modelListAsegPagDirecto({
		    		                        	modUnieco: json[i].cdunieco,
		    		                        	modEstado: json[i].estado,
		    		                        	modRamo: json[i].cdramo,
		    		                        	modNmsituac: json[i].nmsituac,
		    		                        	modPolizaAfectada: json[i].nmpoliza,
		    		                        	modNmsolici: json[i].nmsolici,
		    		                        	modNmsuplem: json[i].nmsuplem,
		    		                        	modCdtipsit: json[i].cdtipsit,
		    		                        	modNmautserv: "",
		    		                        	modFechaOcurrencia: json[i].feocurre,
		    		                        	modCdperson: json[i].cdperson,
		    		                        	modCdpersondesc: json[i].nombreAsegurado,
		    		                        	modnumPoliza:json[i].nmpoliex
		    		                        });

		    				    			storeListAsegPagDirecto.add(rec);
		    				    		}
	    				    		}else{
	    				    			//PAGO POR REEMBOLSO
	    				    			Ext.getCmp('idNmSituac').setValue(json[0].nmsituac);
	    				    			for(var i = 0; i < json.length; i++){
	    				    				var rec = new modelFactCtrl({
							 				  	noFactura: json[i].nfactura,
											 	fechaFactura: json[i].ffactura,
											 	tipoServicio: json[i].cdtipser,
											 	proveedor: json[i].cdpresta,
											 	importe: json[i].ptimport,
											 	proveedorName: json[i].cdpresta+ " "+json[i].dspresta,
											 	tipoServicioName: json[i].dstipser,
											 	tipoMoneda: json[i].cdmoneda,
											 	tipoMonedaName: json[i].desTipomoneda,
											 	tasaCambio: json[i].tasacamb,
											 	importeFactura:json[i].ptimporta
		    		                        });

		    				    			storeFactCtrl.add(rec);
		    				    		}
	    				    		}
	    			    		}
	    				    },
	    				    failure : function ()
	    				    {
	    				        me.up().up().setLoading(false);
	    				        Ext.Msg.show({
	    				            title:'Error',
	    				            msg: 'Error de comunicaci&oacute;n',
	    				            buttons: Ext.Msg.OK,
	    				            icon: Ext.Msg.ERROR
	    				        });
	    				    }
	    				});
		    		}
			    },
			    failure : function ()
			    {
			        me.up().up().setLoading(false);
			        Ext.Msg.show({
			            title:'Error',
			            msg: 'Error de comunicaci&oacute;n',
			            buttons: Ext.Msg.OK,
			            icon: Ext.Msg.ERROR
			        });
			    }
			});
	}
    
    
    
	function limpiarRegistros()
	{
		Ext.getCmp('idUnieco').setValue('');
		Ext.getCmp('idEstado').setValue('');
		Ext.getCmp('idcdRamo').setValue('');
		Ext.getCmp('idNmSituac').setValue('');
		Ext.getCmp('polizaAfectada').setValue('');
		Ext.getCmp('idNmsolici').setValue('');
		Ext.getCmp('idNmsuplem').setValue('');
		Ext.getCmp('idCdtipsit').setValue('');
		Ext.getCmp('idNumPolizaInt').setValue('');
    	return true;
	}
	
	function limpiarRegistrosTipoPago(tipoPago)
	{
		var pagoDirecto = true;
		var pagoReembolso = true;
		if(tipoPago == _TIPO_PAGO_DIRECTO) // Pago Directo
		{
		    Ext.getCmp('editorIncisos').hide();
		    Ext.getCmp('EditorAsegPagDirecto').show();
		    storeFactCtrl.removeAll();
		    pagoDirecto = false;
		    pagoReembolso = true;
		    Ext.getCmp('dtFechaFactura').setValue(new Date());
		    
		    Ext.getCmp('cmbBeneficiario').hide();
		    Ext.getCmp('cmbAseguradoAfectado').hide();
		    Ext.getCmp('dtFechaOcurrencia').hide();
		    Ext.getCmp('cmbBeneficiario').setValue('');
		    Ext.getCmp('cmbAseguradoAfectado').setValue('');
		    Ext.getCmp('dtFechaOcurrencia').setValue('');
		    Ext.getCmp('cmbProveedor').show();
		    Ext.getCmp('txtNoFactura').show();
		    Ext.getCmp('txtImporte').show();
		    Ext.getCmp('dtFechaFactura').show();
		}else{
		    Ext.getCmp('editorIncisos').show();
		    Ext.getCmp('EditorAsegPagDirecto').hide();
		    storeListAsegPagDirecto.removeAll();
		    pagoDirecto = true;
		    pagoReembolso = false;
		    Ext.getCmp('cmbProveedor').hide();
		    Ext.getCmp('txtNoFactura').hide();
		    Ext.getCmp('txtImporte').hide();
		    Ext.getCmp('dtFechaFactura').hide();
		    Ext.getCmp('cmbProveedor').setValue('');
		    Ext.getCmp('txtNoFactura').setValue('');
		    Ext.getCmp('txtImporte').setValue('');
		    Ext.getCmp('dtFechaFactura').setValue('');
		    Ext.getCmp('cmbBeneficiario').show();
		    Ext.getCmp('cmbAseguradoAfectado').show();
		    Ext.getCmp('dtFechaOcurrencia').show();
		}
		
		Ext.getCmp('cmbBeneficiario').allowBlank = pagoReembolso;
		Ext.getCmp('cmbAseguradoAfectado').allowBlank = pagoReembolso;
		Ext.getCmp('dtFechaOcurrencia').allowBlank = pagoReembolso;
		Ext.getCmp('cmbProveedor').allowBlank = pagoDirecto;
		Ext.getCmp('txtNoFactura').allowBlank = pagoDirecto;
		Ext.getCmp('txtImporte').allowBlank = pagoDirecto;
		Ext.getCmp('dtFechaFactura').allowBlank = pagoDirecto;
    	return true;
	}
	
	
	function validarFacturaPagada(cdpresta,nfactura){
		Ext.Ajax.request(
		{
			url     : _URL_CONSULTA_FACTURA_PAGADA
			,params:{
				'params.nfactura' : nfactura,
				'params.cdpresta' : cdpresta
			}
			,success : function (response)
		    {
		    	if(Ext.decode(response.responseText).factPagada !=null)
		    	{
		    		centrarVentanaInterna(Ext.Msg.show({
 		               title: 'Aviso',
 		               msg: 'La factura '+ nfactura +' ya se encuentra pagada en el tr&aacute;mite '+Ext.decode(response.responseText).factPagada,
 		               buttons: Ext.Msg.OK,
 		               icon: Ext.Msg.WARNING
 		           }));
		    	}
		    },
		    failure : function ()
		    {
		        me.up().up().setLoading(false);
		        centrarVentanaInterna(Ext.Msg.show({
		            title:'Error',
		            msg: 'Error de comunicaci&oacute;n',
		            buttons: Ext.Msg.OK,
		            icon: Ext.Msg.ERROR
		        }));
		    }
		});
	}
});