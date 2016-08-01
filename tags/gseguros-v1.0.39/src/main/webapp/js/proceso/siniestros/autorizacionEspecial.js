Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
Ext.onReady(function() {
	var panelProveedor;
	Ext.selection.CheckboxModel.override( {
		mode: 'SINGLE',
		allowDeselect: true
	});
	//Declaracion de los modelos 
	
	Ext.define('modelListadoPoliza',{
		extend: 'Ext.data.Model',
		fields: [	{type:'string',		name:'cdramo'},				{type:'string',		name:'cdunieco'},				{type:'string',		name:'estado'},
					{type:'string',		name:'nmpoliza'},			{type:'string',		name:'nmsituac'},				{type:'string',		name:'mtoBase'},
					{type:'string',		name:'feinicio'},			{type:'string',		name:'fefinal'},				{type:'string',		name:'dssucursal'},
					{type:'string',		name:'dsramo'},				{type:'string',		name:'estatus'},				{type:'string',		name:'dsestatus'},
					{type:'string',		name:'nmsolici'},			{type:'string',		name:'nmsuplem'},				{type:'string',		name:'cdtipsit'},
					{type:'string',		name:'dsestatus'},			{type:'string',		name:'vigenciaPoliza'},			{type:'string',		name:'faltaAsegurado'},
					{type:'string',		name:'fcancelacionAfiliado'},{type:'string',	name:'desEstatusCliente'},		{type:'string',		name:'numPoliza'},
					{type:'string',		name:'nombAsegurado'},		{type:'string',		name:'telefono'},				{type:'string',		name:'email'}
		]
	});
	
	Ext.define('modelListadoCobertura',{
		extend: 'Ext.data.Model',
		fields: [	{type:'string',    name:'cdgarant'},			{type:'string',    name:'dsgarant'},              	{type:'string',    name:'ptcapita'}		]
	});
	
	var storeCobertura = Ext.create('Ext.data.Store', {
		model:'modelListadoCobertura',
		autoLoad:false,
		proxy: {
			type: 'ajax',
			url : _URL_LISTA_COBERTURA,
			reader: {
				type: 'json',
				root: 'listaCoberturaPoliza'
			}
		}
	});
	
	var storeListadoPoliza = new Ext.data.Store({
		pageSize	: 5
		,model		: 'modelListadoPoliza'
		,autoLoad	: false
		,proxy		: {
			enablePaging	: true,
			reader			: 'json',
			type			: 'memory',
			data			: []
		}
	});
	
	storeRamos = Ext.create('Ext.data.Store', {
		model:'Generic',
		autoLoad:true,
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CAT_RAMO_SALUD},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeRamos.load();
	
	var storeContraRecibo = Ext.create('Ext.data.Store', {
		model:'Generic',
		autoLoad:false,
		proxy: {
			type: 'ajax',
			url : _URL_LISTADO_CONTRARECIBO,
			reader: {
				type: 'json',
				root: 'datosValidacionGral'  
			}
		}
	});
	
	var storeFacturaCR = Ext.create('Ext.data.Store', {
		model:'Generic',
		autoLoad:false,
		proxy: {
			type: 'ajax',
			url : _URL_LISTADO_FACTNTRAMITE,
			reader: {
				type: 'json',
				root: 'datosValidacionGral'  
			}
		}
	});
	
	var storeAsegurados2 = Ext.create('Ext.data.Store', {
		model:'Generic',
		autoLoad:false,
		proxy: {
			type: 'ajax',
			url : _URL_LISTADO_ASEGURADO_ESP,
			reader: {
				type: 'json',
				root: 'listaAsegurado'
			}
		}
	});
	
	cmbRamos = Ext.create('Ext.form.field.ComboBox',{
		fieldLabel   : 'Producto',			allowBlank     : false,			editable: false,
		displayField : 'value',				valueField: 'key',				forceSelection : false,
		width		 : 300,					queryMode :'local',				name           :'cmbRamos'
		,store : storeRamos,
		listeners : {
			'select' : function(combo, record) {
				storeFacturaCR.removeAll();
				storeAsegurados2.removeAll();
				storeListadoPoliza.removeAll();
				ramoSeleccionado = this.getValue();
				storeContraRecibo.load({
					params:{
						'params.cdramo'	:	ramoSeleccionado,
						'params.ntramite':	null
					}
				});
			}
		}
	});
	
	aseguradoAfectado  = Ext.create('Ext.form.ComboBox',{
		name:'cmbAsegurado',			fieldLabel: 'Asegurado',			queryMode: 'local',			displayField: 'value',
		valueField: 'key',				editable:true,						forceSelection : true,		matchFieldWidth: false,
		queryParam: 'params.cdperson',	minChars  : 2, 						store : storeAsegurados2,	triggerAction: 'all',
		width		 : 300,				allowBlank:false,
		listeners : {
			'select' : function(combo, record) {
				obtieneCDPerson = this.getValue();
				var params = {
						'params.cdperson'	:	obtieneCDPerson,
						'params.cdramo'		:	panelInicialPral.down('combo[name=cmbRamos]').getValue()
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
			}
		}
	});
	
	contraRecibos = Ext.create('Ext.form.ComboBox',{
		name:'txtContraRecibo',			fieldLabel: 'Contrarecibo',			queryMode: 'local',			displayField: 'value',
		valueField: 'key',				editable:true,						forceSelection : true,		matchFieldWidth: false,
		queryParam: 'params.ntramite',	minChars  : 2, 						store : storeContraRecibo,	triggerAction: 'all',
		width		 : 300,				allowBlank:false,
		listeners : {
			'select' : function(combo, record) {
				contraReciboDato = this.getValue();
				storeFacturaCR.removeAll();
				storeAsegurados2.removeAll();
				storeListadoPoliza.removeAll();
				storeFacturaCR.load({
					params:{
						'params.ntramite':	contraReciboDato,
						'params.nfactura':	null
					}
				});
				
				storeAsegurados2.load({
					params:{
						'params.ntramite': contraReciboDato,
						'params.nfactura':   null
					}
				});
			}
		}
	});
	
	facturaCr = Ext.create('Ext.form.ComboBox',{
		name:'txtFactura',				fieldLabel: 'Factura',				queryMode: 'local',			displayField: 'value',
		valueField: 'key',				editable:true,						forceSelection : true,		matchFieldWidth: false,
		queryParam: 'params.nfactura',	minChars  : 2, 						store : storeFacturaCR,		triggerAction: 'all',
		width		 : 300,				allowBlank:false,
		listeners : {
			'select' : function(combo, record) {
				debug("Entra <<<<<>>>>>>>");
				facturaSeleccionada = this.getValue();
				storeAsegurados2.removeAll();
				storeListadoPoliza.removeAll();
				storeAsegurados2.load({
					params:{
						'params.ntramite': panelInicialPral.down('combo[name="txtContraRecibo"]').getValue(),
						'params.nfactura':   facturaSeleccionada
					}
				});
			}
		}
	});
	coberturaAfectada = Ext.create('Ext.form.field.ComboBox',{
		colspan:2,
		displayField : 'dsgarant',		id:'idCobAfectada',		name:'cdgarant',
		valueField   : 'cdgarant',	forceSelection : true,			matchFieldWidth: false,
		queryMode :'local',					store : storeCobertura,		triggerAction: 'all',			editable:false
	});

	gridPolizasAsegurado= Ext.create('Ext.grid.Panel', {
		id			:	'polizaGridAltaTramite',
		store		:	storeListadoPoliza,
		selType		:	'checkboxmodel',
		width		:	800,
		height		:	300,
		columns		:
		[
			{	header		: 'N&uacute;mero de P&oacute;liza',											dataIndex : 'numPoliza',		width		: 200		},
			{	header		: 'Estatus p&oacute;liza ',													dataIndex : 'dsestatus',		width	 	: 100		},
			{	header		: 'Vigencia p&oacute;liza <br/> Fecha inicio \t\t  |  \t\t Fecha fin  ',	dataIndex : 'vigenciaPoliza',	width	    : 200		},
			{	header		: 'Fecha alta <br/> asegurado',												dataIndex : 'faltaAsegurado',	width	    : 100		},
			{	header		: 'Fecha cancelaci&oacute;n <br/> asegurado',								dataIndex : 'fcancelacionAfiliado',	width	: 150		},
			{	header		: 'Estatus<br/> asegurado',													dataIndex : 'desEstatusCliente',width	    : 100		},
			{	header		: 'Producto',																dataIndex : 'dsramo',			width       : 150		},
			{	header		: 'Sucursal',																dataIndex : 'dssucursal',		width       : 150		},
			{	header		: 'Estado',																	dataIndex : 'estado',			width	    : 100		},
			{	header		: 'N&uacute;mero de Situaci&oacute;n',										dataIndex : 'nmsituac',			width	    : 150		}
		],
		bbar : {
			displayInfo	: true,
			store		: storeListadoPoliza,
			xtype		: 'pagingtoolbar'
		},
		listeners: {
			itemclick: function(dv, record, item, index, e){
				//1.- Validamos que el asegurado este vigente
				debug("Valor del Record", record);
				panelInicialPral.down('[name="cdunieco"]').setValue(record.get('cdunieco'));
				panelInicialPral.down('[name="estado"]').setValue(record.get('estado'));
				panelInicialPral.down('[name="cdramo"]').setValue(record.get('cdramo'));
				panelInicialPral.down('[name="nmsituac"]').setValue(record.get('nmsituac'));
				panelInicialPral.down('[name="polizaAfectada"]').setValue(record.get('nmpoliza'));
				panelInicialPral.down('[name="idNmsolici"]').setValue(record.get('nmsolici'));
				panelInicialPral.down('[name="idNmsuplem"]').setValue(record.get('nmsuplem'));
				panelInicialPral.down('[name="idCdtipsit"]').setValue(record.get('cdtipsit'));
				panelInicialPral.down('[name="idNumPolizaInt"]').setValue(record.get('numPoliza'));
				
				storeCobertura.load({
					params:{
						'params.cdunieco':record.get('cdunieco'),
						'params.cdramo':record.get('cdramo'),
						'params.estado':record.get('estado'),
						'params.nmpoliza':record.get('nmpoliza'),
						'params.nmsituac':record.get('nmsituac'),
						'params.nmsuplem':record.get('nmsuplem'),
						'params.cdtipsit':record.get('cdtipsit')
					}
				});
				
				//validamos si no tiene autorizaciones anteriores 
				Ext.Ajax.request({
					url     : _URL_CONF_AUTESPECIAL
					,params:{
						'params.ntramite' : panelInicialPral.down('[name="txtContraRecibo"]').getValue(),
						'params.nfactura' : panelInicialPral.down('[name="txtFactura"]').getValue(),
						'params.cdperson' : panelInicialPral.down('combo[name=cmbAsegurado]').getValue(),
						'params.cdunieco' : record.get('cdunieco'),
						'params.cdramo'   : record.get('cdramo'),
						'params.estado'   : record.get('estado'),
						'params.nmpoliza' : record.get('nmpoliza'),
						'params.nmsuplem' : record.get('nmsuplem'),
						'params.nmsituac' : record.get('nmsituac'),
						'params.cdtipsit' : record.get('cdtipsit')
					}
					,success : function (response) {
						if(Ext.decode(response.responseText).datosValidacion != null){
							var json =Ext.decode(response.responseText).datosValidacion;
							if(json.length > 0){
								panelInicialPral.down('[name="comments"]').setValue(json[0].COMMENTS);
								panelInicialPral.down('[name="nmautespecial"]').setValue(json[0].NMAUTESP);
								if(json[0].VALRANGO > 0){
									panelInicialPral.down('[name="valRango"]').setValue("1");
									Ext.getCmp('genCheckboxA').setValue(true);
								}else{
									panelInicialPral.down('[name="valRango"]').setValue("0");
									Ext.getCmp('genCheckboxA').setValue(false);
								}
								
								if(json[0].VALCOBER > 0){
									Ext.getCmp('genCheckboxB').setValue(true);
									panelInicialPral.down('[name="valGarant"]').setValue("1");
									panelInicialPral.down('combo[name=cdgarant]').setValue(json[0].CDGARANT);
								}else{
									panelInicialPral.down('[name="valGarant"]').setValue("0");
									Ext.getCmp('genCheckboxB').setValue(false);
								}
							}else{
								panelInicialPral.down('[name="valRango"]').setValue("0");
								panelInicialPral.down('[name="valGarant"]').setValue("0");
								panelInicialPral.down('[name="nmautespecial"]').setValue("0");
								Ext.getCmp('genCheckboxA').setValue(false);
								Ext.getCmp('genCheckboxB').setValue(false);
								panelInicialPral.down('[name="comments"]').setValue("");
							}
						}else{
							panelInicialPral.down('[name="nmautespecial"]').setValue("0");
							Ext.getCmp('genCheckboxA').setValue(false);
							Ext.getCmp('genCheckboxB').setValue(false);
							panelInicialPral.down('[name="comments"]').setValue("");
						}
					},
					failure : function (){
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
		}
	});
	gridPolizasAsegurado.store.sort([
		{
			property    : 'nmpoliza',			direction   : 'DESC'
		}
	]);

	comentarios = Ext.create('Ext.form.field.TextArea', {
		colspan:2,fieldLabel: 'Comentarios',			/*labelWidth: 150,*/			width: 700
		,name:'comments',					height: 70,				allowBlank: false
	});
	
	var panelInicialPral= Ext.create('Ext.form.Panel',
    {
        border      : 0,
        id          : 'panelInicialPral',
        renderTo    : 'div_clau',
        bodyPadding : 5,
        //width       : 758,
        layout      : {
			type    : 'table'
			,columns: 2
		},
		defaults 	: {
			style   : 'margin:5px;'
		},
		items       :[
			{    xtype       : 'textfield',			fieldLabel : 'Unieco'				,name       : 'cdunieco'
			    ,labelWidth: 170,					hidden:true
			},
			{    xtype       : 'textfield',			fieldLabel : 'Ramo'					,name       : 'cdramo'
			    ,labelWidth: 170,					hidden:true
			},
			{    xtype       : 'textfield',			fieldLabel : 'estado'				,name       : 'estado'			
			    ,labelWidth: 170,					hidden:true
			},
			{    xtype       : 'textfield',			fieldLabel : 'nmsituac'				,name       : 'nmsituac'
			    ,labelWidth: 170,					hidden:true
			},
			{   xtype       : 'textfield',			fieldLabel : 'P&oacute;liza afectada'
			   ,labelWidth: 170,					name:'polizaAfectada',	readOnly   : true,	hidden:true
			},
			{    xtype       : 'textfield',			fieldLabel : 'nmsolici'				,name       : 'idNmsolici'
			    ,labelWidth: 170,					hidden:true
			},
			{    xtype       : 'textfield',			fieldLabel : 'nmsuplem'				,name       : 'idNmsuplem'
			    ,labelWidth: 170,					hidden:true
			},
			{    xtype       : 'textfield',			fieldLabel : 'cdtipsit'				,name       : 'idCdtipsit'
			    ,labelWidth: 170,					hidden:true
			},
			{    xtype       : 'textfield',			fieldLabel : 'numPolizaInt'			,name       : 'idNumPolizaInt'
			    ,labelWidth: 170,					hidden:true
			},
			{    xtype       : 'textfield',			fieldLabel : 'valRango'				,name       : 'valRango'
			    ,labelWidth: 170,					value:'0'							,hidden:true
			},
			{    xtype       : 'textfield',			fieldLabel : 'valGarant'			,name       : 'valGarant'
			    ,labelWidth: 170,					value:'0'							,hidden:true
			},
			{    xtype       : 'textfield',			fieldLabel : 'No. autorizacion'		,name       : 'nmautespecial'
			    ,labelWidth: 170,					value:'0'							,hidden:true
			},
            cmbRamos,
            contraRecibos,
            facturaCr
			,aseguradoAfectado
			,{	colspan:2
		 		,border: false
		 		,items    :
		 			[
		 			 	gridPolizasAsegurado 
	 			 	]
		 	}
			,
			{
				xtype: 'fieldcontainer',
				colspan:2,
				fieldLabel: 'Motivos',
				defaultType: 'checkboxfield',
				layout : {
					type     : 'table'
					,columns : 2
				},
				defaults : {
					style : 'margin:5px;'
				},
				items: [
				        {xtype: 'checkbox', id: 'genCheckboxA', name: 'genCheckboxA', boxLabel: 'Fuera de vigencia', hideLabel: true, colspan:2,
				        	listeners: {
				        	    change: function() {
				        	    	if (Ext.getCmp('genCheckboxA').getValue() == true) {
				        	    		panelInicialPral.down('[name="valRango"]').setValue("1");   
			                        }else{
			                        	panelInicialPral.down('[name="valRango"]').setValue("0");
			                        }
				        	    }
				        	}
				        },
				        {xtype: 'checkbox', id: 'genCheckboxB', name: 'genCheckboxB', boxLabel: 'Cobertura no amparada', hideLabel: true,
				        	listeners: {
				        	    change: function() {
				        	    	 if (Ext.getCmp('genCheckboxB').getValue() == true) {
				        	    		 panelInicialPral.down('combo[name=cdgarant]').allowBlank = false;
				        	    		 panelInicialPral.down('[name="valGarant"]').setValue("1");
				        	    		 panelInicialPral.down('combo[name=cdgarant]').setDisabled(false);
				                        }else{
				                        	panelInicialPral.down('combo[name=cdgarant]').allowBlank = true;
				                        	panelInicialPral.down('[name="valGarant"]').setValue("0");
				                        	panelInicialPral.down('combo[name=cdgarant]').setDisabled(true);
				                        }
				        	    }
				        	}
				        },
				        coberturaAfectada
			        ]
			},
			comentarios
        ],
        buttonAlign:'center',
        buttons: [{
            id:'botonCotizar',
            icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
            text: 'Confirmar autorizaci&oacute;n',
    		handler: function() {
				var form = this.up('form').getForm();
    			if (form.isValid()){
    				if(Ext.getCmp('genCheckboxA').getValue() == false && Ext.getCmp('genCheckboxB').getValue() == false){
        				Ext.Msg.show({
	                    	title:'Datos incompletos',
	                    	msg: 'Seleccione al menos un motivo para la autorizaci&oacute;n especial.',
	                    	buttons: Ext.Msg.OK,
	                    	icon: Ext.Msg.WARNING
	                	})
        			}else{
        				var submitValues={};
        				var formulario=panelInicialPral.form.getValues();
        				submitValues['params']=formulario;
        				debug(submitValues);
        				Ext.Ajax.request(
						{
							url: _URL_GUARDA_AUT_ESPECIAL,
							jsonData:Ext.encode(submitValues), // convierte a estructura JSON
							success:function(response,opts){
								panelInicialPral.setLoading(false);
								var jsonResp = Ext.decode(response.responseText);
								debug("Valor de respuesta ",jsonResp);
								if(jsonResp.success==true){
									if(panelInicialPral.down('[name="nmautespecial"]').getValue() > 0){
										mensajeCorrecto('Aviso',"Se actualizo la autorizaci&oacute;n especial con el n&uacute;mero : "+Ext.decode(response.responseText).msgResult);
									}else{
										panelInicialPral.down('[name="nmautespecial"]').setValue(Ext.decode(response.responseText).msgResult);
    									mensajeCorrecto('Aviso',"Se registro la autorizaci&oacute;n especial con el n&uacute;mero : "+Ext.decode(response.responseText).msgResult);
									}
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
							failure:function(response,opts){
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
    			}
    			else {
    				Ext.Msg.show({
                    	title:'Datos incompletos',
                    	msg: 'Favor de introducir todos los campos requeridos',
                    	buttons: Ext.Msg.OK,
                    	icon: Ext.Msg.WARNING
                	});
				}
			}
        }
    ]
    });
	
	function TotalRegistros(storeRecibido){
		debug("Valor Recibido ===>",storeRecibido);
		var arr = [];
		var totalRegistro = 0;
		var valorBase=0;
		storeRecibido.each(function(record) {
			arr.push(record.data);
		});
		for(var i = 0; i < arr.length; i++){
			totalRegistro = i;
		}
		return totalRegistro;
	}
});